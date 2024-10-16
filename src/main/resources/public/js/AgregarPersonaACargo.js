let cargoCount = 1; // Contador de personas a cargo

document.querySelector('.add-cargo').addEventListener('click', function() {
    if (cargoCount < 5) { // Puedes cambiar el límite de personas a cargo si es necesario
        cargoCount++;

        // Crear nuevos campos para la persona vulnerable a cargo
        const newCargoRow = document.createElement('div');
        newCargoRow.classList.add('row');
        newCargoRow.innerHTML = `
            <input type="text" id="nombrePersonaACargo${cargoCount}" name="nombrePersonaACargo${cargoCount}" placeholder="Nombre Persona a Cargo">
            <input type="text" id="apellidoPersonaACargo${cargoCount}" name="apellidoPersonaACargo${cargoCount}" placeholder="Apellido Persona a Cargo">
            <button type="button" class="remove-cargo" onclick="removeCargo(this)">Eliminar</button>
        `;

        document.getElementById('cargo-fields').appendChild(newCargoRow);
    }
});

// Función para eliminar una persona a cargo
function removeCargo(button) {
    const cargoRow = button.parentElement;
    cargoRow.remove();
    cargoCount--;
}
