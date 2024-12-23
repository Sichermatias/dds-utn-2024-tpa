let contactCount = 1; // Contador de medios de contacto

document.querySelector('.add-contact').addEventListener('click', function() {
    if (contactCount < 3) {
        contactCount++;

        // Crear nuevos campos para el medio de contacto
        const newContactRow = document.createElement('div');
        newContactRow.classList.add('form-row', 'mb-3'); // Agregar clases de Bootstrap
        newContactRow.innerHTML = `
            <div class="form-group col-md-6">
                <select class="form-control" id="nombreContacto[]" name="nombreContacto[]" required>
                <option value="" disabled selected>Selecciona Medio de Contacto</option>
                <option value="E-MAIL">E-MAIL</option>
                <option value="WHATSAPP">WHATSAPP</option>
                <option value="TELEFONO LINEA">TELEFONO LINEA</option>
                </select>
            </div>
            <div class="form-group col-md-6">
                <input type="text" class="form-control" id="contacto[]" name="contacto[]" placeholder="Dato de Contacto" required>
            </div>
            <div class="form-group col-md-6">
                <button type="button" class="btn btn-outline-danger remove-contact" onclick="removeContact(this)">Eliminar</button>
            </div>
        `;

        document.getElementById('contact-fields').appendChild(newContactRow);
    }
});

// Función para eliminar un medio de contacto
function removeContact(button) {
    const contactRow = button.closest('.form-row'); // Cambiado a closest para obtener el div correcto
    contactRow.remove();
    contactCount--;
}
