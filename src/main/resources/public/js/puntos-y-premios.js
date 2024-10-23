// Función para filtrar los productos por rubro/categoría
function filterProducts() {
    const selectedCategory = document.getElementById("inputState").value;
    const products = document.querySelectorAll(".product-card");

    products.forEach(product => {
        const productCategory = product.getAttribute("data-category");
        if (selectedCategory === "all" || productCategory === selectedCategory) {
            product.style.display = "block";  // Mostrar producto
        } else {
            product.style.display = "none";  // Ocultar producto
        }
    });
}