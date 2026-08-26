async function renderCatalog() {
    const container = document.getElementById("catalog");
    if (!container) {
        return;
    }

    try {
        const products = await getProducts();
        container.innerHTML = "";

        products.forEach((product) => {
            const card = document.createElement("a");
            card.className = "product-card";
            card.href = `product.html?id=${product.id}`;
            card.innerHTML = `
                <img class="product-image" src="${product.imageUrl}" alt="${product.name}" loading="lazy" onerror="this.onerror=null;this.classList.add('product-image-error');">
                <h3>${product.name}</h3>
                <p>${product.description}</p>
                <p class="price">${formatCOP(product.price)}</p>
            `;
            container.appendChild(card);
        });
    } catch (error) {
        container.innerHTML = "<p>No se pudieron cargar los productos.</p>";
        console.error(error);
    }
}

document.addEventListener("DOMContentLoaded", renderCatalog);
