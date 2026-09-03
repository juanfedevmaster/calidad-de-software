const API_BASE_URL = "http://localhost:8080/api";

function formatCOP(value) {
    return new Intl.NumberFormat("es-CO", {
        style: "currency",
        currency: "COP",
        maximumFractionDigits: 0,
    }).format(value);
}

async function getProducts() {
    const response = await fetch(`${API_BASE_URL}/products`);
    if (!response.ok) {
        throw new Error("Error al obtener los productos");
    }
    return response.json();
}

async function getProductById(id) {
    const response = await fetch(`${API_BASE_URL}/products/${id}`);
    if (!response.ok) {
        throw new Error("Producto no encontrado");
    }
    return response.json();
}

async function getCart(sessionId) {
    const response = await fetch(`${API_BASE_URL}/cart?sessionId=${sessionId}`);
    if (!response.ok) {
        throw new Error("Error al obtener el carrito");
    }
    return response.json();
}

async function addToCart(sessionId, productId, quantity) {
    const response = await fetch(`${API_BASE_URL}/cart`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ sessionId, productId, quantity }),
    });
    if (!response.ok) {
        throw new Error("Error al agregar al carrito");
    }
    return response.json();
}

async function updateCartItem(itemId, quantity) {
    const response = await fetch(`${API_BASE_URL}/cart/${itemId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ quantity }),
    });
    if (!response.ok) {
        throw new Error("Error al actualizar el carrito");
    }
    return response.json();
}

async function removeCartItem(itemId) {
    const response = await fetch(`${API_BASE_URL}/cart/${itemId}`, {
        method: "DELETE",
    });
    if (!response.ok) {
        throw new Error("Error al eliminar del carrito");
    }
}
