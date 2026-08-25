let cartItemsCache = [];

async function renderCheckout() {
    const container = document.getElementById("checkout-content");
    const sessionId = getSessionId();

    try {
        const items = await getCart(sessionId);
        cartItemsCache = items;

        if (items.length === 0) {
            container.innerHTML = "<p>Tu carrito está vacío. <a href=\"catalog.html\">Volver al catálogo</a></p>";
            return;
        }

        const total = items.reduce((sum, item) => sum + item.product.price * item.quantity, 0);

        const rowsHtml = items.map((item) => `
            <div class="checkout-row">
                <span>${item.product.name} × ${item.quantity}</span>
                <span>${formatCOP(item.product.price * item.quantity)}</span>
            </div>
        `).join("");

        container.innerHTML = `
            <div class="checkout-summary">${rowsHtml}</div>
            <div class="cart-total">
                <span>Total:</span>
                <span>${formatCOP(total)}</span>
            </div>
            <button id="confirm-order-btn" class="btn">Confirmar pedido</button>
        `;

        document.getElementById("confirm-order-btn").addEventListener("click", confirmOrder);
    } catch (error) {
        container.innerHTML = "<p>No se pudo cargar el resumen del pedido.</p>";
        console.error(error);
    }
}

async function confirmOrder() {
    const container = document.getElementById("checkout-content");
    const button = document.getElementById("confirm-order-btn");
    button.disabled = true;
    button.textContent = "Procesando...";

    try {
        await Promise.all(cartItemsCache.map((item) => removeCartItem(item.id)));
        container.innerHTML = `
            <div class="order-confirmation">
                <h3>¡Pedido confirmado!</h3>
                <p>Gracias por tu compra. Este es un checkout simulado, no se realizó ningún cobro real.</p>
                <a class="btn" href="catalog.html">Volver al catálogo</a>
            </div>
        `;
    } catch (error) {
        container.innerHTML = "<p>No se pudo confirmar el pedido. Intenta de nuevo.</p>";
        console.error(error);
    }
}

document.addEventListener("DOMContentLoaded", renderCheckout);
