# Bugs Inyectados — ParaBank

Documento de referencia para el instructor. Lista todos los defectos inyectados intencionalmente para las pruebas de caja negra.

---

## Backend

### BUG-BE-01 — `changePassword` acepta contraseña nueva igual a la actual

| Campo | Detalle |
|-------|---------|
| **Archivo** | `BackEnd/src/main/java/com/parabank/backend/application/service/impl/UserServiceImpl.java` |
| **Método** | `changePassword()` |
| **Tipo** | Validación de negocio incorrecta |
| **Severidad** | Media |

**Descripción:** Se agregó una validación que compara `request.getNewPassword()` (texto plano) contra `user.getPasswordHash()` (hash BCrypt). Como nunca son iguales, la validación nunca bloquea — el sistema acepta cambiar la contraseña por la misma sin error.

**Comportamiento esperado:** `PUT /api/users/me/password` con `newPassword` igual a la contraseña actual debe devolver `400 Bad Request`.

**Comportamiento actual:** Devuelve `204 No Content` (éxito) aunque `newPassword` sea idéntica a la contraseña actual.

**Técnica para detectarlo:** Error guessing — probar `currentPassword == newPassword`.

---

### BUG-BE-02 — Login intermitente: primer intento falla, segundo pasa

| Campo | Detalle |
|-------|---------|
| **Archivo** | `BackEnd/src/main/java/com/parabank/backend/application/service/impl/AuthServiceImpl.java` |
| **Método** | `login()` |
| **Tipo** | Defecto de estado interno |
| **Severidad** | Alta |

**Descripción:** Se introdujo un `Set` estático `pendingRetry` que registra el primer intento de login por usuario. En el primer intento con credenciales correctas, el username se agrega al set y se lanza `InvalidCredentialsException`. En el segundo intento, el username ya está en el set, se remueve y el login procede normalmente. El ciclo se repite en cada login exitoso.

**Comportamiento esperado:** `POST /api/auth/login` con credenciales correctas debe devolver `200 OK` con token JWT en el primer intento.

**Comportamiento actual:** El primer intento devuelve `401 Unauthorized`. El segundo intento con las mismas credenciales devuelve `200 OK`.

**Técnica para detectarlo:** Prueba de repetición — enviar la misma petición dos veces seguidas y comparar respuestas.

---

### BUG-BE-03 — Login falla si el username tiene espacios al inicio o al final

| Campo | Detalle |
|-------|---------|
| **Archivo** | `BackEnd/src/main/java/com/parabank/backend/application/service/impl/AuthServiceImpl.java` |
| **Método** | `login()` |
| **Tipo** | Sanitización de input faltante |
| **Severidad** | Baja |

**Descripción:** Se eliminó el `.trim()` del username en el login. El username solo se convierte a minúsculas pero no se eliminan los espacios en blanco al inicio o al final. El registro sí normaliza correctamente, por lo que `"     ana.torres"` no coincide con `"ana.torres"` en la base de datos.

**Comportamiento esperado:** `POST /api/auth/login` con `"  ana.torres  "` debe autenticar correctamente.

**Comportamiento actual:** Devuelve `401 Unauthorized` si el username tiene espacios al inicio o al final.

**Técnica para detectarlo:** Análisis de valores límite / partición de equivalencia — probar username con espacios en blanco.

---

## Frontend (UI)

### BUG-FE-01 — Registro usa `confirmPassword` en lugar de `password`

| Campo | Detalle |
|-------|---------|
| **Archivo** | `UI/src/components/Register.jsx` |
| **Función** | `handleSubmit()` |
| **Tipo** | Lógica de envío incorrecta |
| **Severidad** | Alta |

**Descripción:** Se eliminó la validación que compara `password` con `confirmPassword`. Además, el campo enviado al backend es `confirmPassword` en lugar de `password`. Si el usuario escribe contraseñas distintas en ambos campos, queda registrado con la contraseña del segundo campo sin saberlo.

**Comportamiento esperado:** El sistema debe validar que ambos campos coincidan antes de registrar. El campo `password` del formulario es el que se envía al backend.

**Comportamiento actual:** No hay validación de coincidencia. El backend recibe el valor de `confirmPassword` como contraseña. Hacer login con el valor de `password` falla con `401`.

**Técnica para detectarlo:** Registrarse con contraseñas distintas en ambos campos e intentar login con la primera — fallará. Login con la segunda — tendrá éxito.

---

### BUG-FE-02 — Cerrar sesión no limpia `localStorage`: la sesión reaparece al refrescar

| Campo | Detalle |
|-------|---------|
| **Archivo** | `UI/src/context/AuthContext.jsx` |
| **Función** | `logout()` y `useEffect` de validación de token |
| **Tipo** | Defecto de gestión de sesión |
| **Severidad** | Alta |

**Descripción:** Se eliminó `clearSession()` de la función `logout()` y se vació el bloque `.catch()` del `useEffect` que valida el token al cargar. Como consecuencia:
- `logout()` limpia el estado React (va al Login) pero no elimina `parabank_token` ni `parabank_user` del `localStorage`.
- Al refrescar, la app carga los datos del `localStorage`, llama al backend para validar el token y, si este aún es válido, vuelve al Dashboard.
- Si el token ya expiró, el `.catch()` no hace nada y el usuario también queda en el Dashboard con datos desactualizados.

**Comportamiento esperado:** Al hacer clic en "Cerrar sesión" y refrescar la página, el usuario debe permanecer en la pantalla de Login.

**Comportamiento actual:** Al refrescar después de cerrar sesión, el usuario vuelve al Dashboard.

**Técnica para detectarlo:** Hacer login → cerrar sesión → refrescar la página → verificar pantalla resultante.

---

## Resumen

| ID | Capa | Severidad | Técnica que lo detecta |
|----|------|-----------|------------------------|
| BUG-BE-01 | Backend | Media | Error guessing |
| BUG-BE-02 | Backend | Alta | Prueba de repetición |
| BUG-BE-03 | Backend | Baja | Valores límite / partición de equivalencia |
| BUG-FE-01 | Frontend | Alta | Error guessing / flujo de registro |
| BUG-FE-02 | Frontend | Alta | Prueba de gestión de sesión |
