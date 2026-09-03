async function renderProductDetail() {
    const container = document.getElementById("product-detail");
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (!id) {
        container.innerHTML = "<p>Producto no especificado.</p>";
        return;
    }

    try {
        const product = await getProductById(id);
        container.innerHTML = `
            <img class="product-image large" src="${product.imageUrl}" alt="${product.name}" onerror="this.onerror=null;this.classList.add('product-image-error');">
            <div class="product-info">
                <h2>${product.name}</h2>
                <p>${product.detailDescription}</p>
                <p class="price">${formatCOP(product.price)}</p>
                <div class="add-to-cart-controls">
                    <input type="number" id="quantity-input" value="1" min="1">
                    <button id="add-to-cart-btn" class="btn">Agregar al carrito</button>
                </div>
                <p id="add-to-cart-message" class="feedback-message"></p>
                <a class="btn btn-secondary" href="catalog.html">Volver al catálogo</a>
            </div>
        `;

        document.getElementById("add-to-cart-btn").addEventListener("click", async () => {
            const quantity = Math.max(1, parseInt(document.getElementById("quantity-input").value, 10) || 1);
            const messageEl = document.getElementById("add-to-cart-message");
            try {
                await addToCart(getSessionId(), product.id, quantity);
                messageEl.textContent = "Producto agregado al carrito.";
                messageEl.className = "feedback-message success";
            } catch (error) {
                messageEl.textContent = "No se pudo agregar el producto.";
                messageEl.className = "feedback-message error";
                console.error(error);
            }
        });
    } catch (error) {
        container.innerHTML = "<p>No se pudo cargar el producto.</p>";
        console.error(error);
    }
}

document.addEventListener("DOMContentLoaded", renderProductDetail);
