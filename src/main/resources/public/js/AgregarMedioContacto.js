    let contactCount = 1; // Contador de medios de contacto

    document.querySelector('.add-contact').addEventListener('click', function() {
        if (contactCount < 3) {
            contactCount++;

            // Crear nuevos campos para el medio de contacto
            const newContactRow = document.createElement('div');
            newContactRow.classList.add('row');
            newContactRow.innerHTML = `
                <input type="text" list="nombreContactoOptions" id="nombreContacto${contactCount}" name="nombreContacto${contactCount}" placeholder="Tipo de Medio de Contacto">
                <input type="text" id="contacto${contactCount}" name="contacto${contactCount}" placeholder="Dato de Contacto" required>
                <button type="button" class="remove-contact" onclick="removeContact(this)">Eliminar</button>
            `;

            document.getElementById('contact-fields').appendChild(newContactRow);
        }
    });

    // Función para eliminar un medio de contacto
    function removeContact(button) {
        const contactRow = button.parentElement;
        contactRow.remove();
        contactCount--;
    }