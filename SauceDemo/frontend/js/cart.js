async function renderCart() {
    const container = document.getElementById("cart-content");
    const sessionId = getSessionId();

    try {
        const items = await getCart(sessionId);

        if (items.length === 0) {
            container.innerHTML = "<p>Tu carrito está vacío.</p>";
            return;
        }

        const total = items.reduce((sum, item) => sum + item.product.price * item.quantity, 0);

        const rowsHtml = items.map((item) => `
            <div class="cart-row" data-item-id="${item.id}">
                <div class="cart-row-info">
                    <h3>${item.product.name}</h3>
                    <p>${formatCOP(item.product.price)} c/u</p>
                </div>
                <div class="cart-row-controls">
                    <input type="number" class="cart-qty-input" min="1" value="${item.quantity}" data-item-id="${item.id}">
                    <button class="btn btn-danger cart-remove-btn" data-item-id="${item.id}">Eliminar</button>
                </div>
                <p class="cart-row-subtotal">${formatCOP(item.product.price * item.quantity)}</p>
            </div>
        `).join("");

        container.innerHTML = `
            <div class="cart-list">${rowsHtml}</div>
            <div class="cart-total">
                <span>Total:</span>
                <span>${formatCOP(total)}</span>
            </div>
            <a class="btn" href="checkout.html">Ir a pagar</a>
        `;

        container.querySelectorAll(".cart-qty-input").forEach((input) => {
            input.addEventListener("change", async (event) => {
                const itemId = event.target.dataset.itemId;
                const quantity = Math.max(1, parseInt(event.target.value, 10) || 1);
                try {
                    await updateCartItem(itemId, quantity);
                    renderCart();
                } catch (error) {
                    console.error(error);
                }
            });
        });

        container.querySelectorAll(".cart-remove-btn").forEach((button) => {
            button.addEventListener("click", async (event) => {
                const itemId = event.target.dataset.itemId;
                try {
                    await removeCartItem(itemId);
                    renderCart();
                } catch (error) {
                    console.error(error);
                }
            });
        });
    } catch (error) {
        container.innerHTML = "<p>No se pudo cargar el carrito.</p>";
        console.error(error);
    }
}

document.addEventListener("DOMContentLoaded", renderCart);
