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

document.addEventListener('DOMContentLoaded', () => {
    const confirmModal = document.getElementById('canjearPremioModal');
    const puntajeDestacado = confirmModal.querySelector('#puntaje-destacado');

    confirmModal.addEventListener('show.bs.modal', (event) => {
        // Botón que activó la modal
        const button = event.relatedTarget;

        // Obtén los puntos desde el data attribute
        const puntos = button.getAttribute('data-puntos');

        // Actualiza el contenido del marcador con estilos
        puntajeDestacado.textContent = puntos;
    });
});
