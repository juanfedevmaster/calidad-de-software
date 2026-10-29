package com.parabank.backend.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
 
import java.io.FileWriter;
import java.time.Duration;
import java.util.List;
 
/**
 * Scraping del Dashboard de ParaBank UI.
 * Selectores tomados del código fuente (Login.jsx y Dashboard.jsx).
 *
 * Credenciales por variables de entorno:
 *   PARABANK_USER, PARABANK_PASS
 */
@Tag("e2e")
class ParaBankScraperTest {
 
    private static final String URL = "https://parabank-ui.onrender.com/";
 
    // Login.jsx
    private static final By USUARIO = By.cssSelector("input[autocomplete='username']");
    private static final By PASSWORD = By.cssSelector("input[autocomplete='current-password']");
    private static final By BTN_LOGIN = By.cssSelector("form button.btn-primary");
    private static final By ERROR_LOGIN = By.cssSelector("p.form-error");
 
    // Dashboard.jsx
    private static final By NOMBRE_HEADER = By.cssSelector(".dashboard-username");
    private static final By SALDO = By.cssSelector(".balance-amount");
    private static final By DETALLES = By.cssSelector(".account-details > div");
 
    private WebDriver driver;
    private WebDriverWait wait;
 
    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        // Render (plan gratuito) puede tardar en despertar el backend
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }
 
    @Test
    void scrapearDashboard() throws Exception {
        driver.get(URL);
 
        wait.until(ExpectedConditions.visibilityOfElementLocated(USUARIO))
            .sendKeys(System.getenv("PARABANK_USER"));
        driver.findElement(PASSWORD).sendKeys(System.getenv("PARABANK_PASS"));
        driver.findElement(BTN_LOGIN).click();
 
        // BUG-BE-02: el primer login correcto devuelve 401; el segundo pasa.
        // Si aparece el error, se reintenta una vez con los mismos datos.
        WebElement saldo = esperarDashboardOError();
        if (saldo == null) {
            System.out.println("Primer intento fallido (BUG-BE-02). Reintentando...");
            wait.until(ExpectedConditions.elementToBeClickable(BTN_LOGIN)).click();
            saldo = wait.until(ExpectedConditions.visibilityOfElementLocated(SALDO));
        }
 
        // Extracción
        String nombre = driver.findElement(NOMBRE_HEADER).getText();
        String saldoTexto = saldo.getText();
 
        try (FileWriter csv = new FileWriter("parabank.csv")) {
            csv.write("campo,valor\n");
            csv.write("Nombre (header)," + nombre + "\n");
            csv.write("Saldo,\"" + saldoTexto + "\"\n");
 
            List<WebElement> filas = driver.findElements(DETALLES);
            for (WebElement fila : filas) {
                String campo = fila.findElement(By.tagName("dt")).getText();
                String valor = fila.findElement(By.tagName("dd")).getText();
                csv.write(campo + "," + valor + "\n");
                System.out.println(campo + ": " + valor);
            }
        }
 
        System.out.println("Saldo: " + saldoTexto);
        Assertions.assertFalse(saldoTexto.isBlank(), "No se pudo leer el saldo");
    }
 
    /** Devuelve el saldo si entró al Dashboard, o null si apareció el mensaje de error. */
    private WebElement esperarDashboardOError() {
        wait.until(ExpectedConditions.or(
            ExpectedConditions.visibilityOfElementLocated(SALDO),
            ExpectedConditions.visibilityOfElementLocated(ERROR_LOGIN)));
        List<WebElement> saldo = driver.findElements(SALDO);
        return saldo.isEmpty() ? null : saldo.get(0);
    }
 
    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
 