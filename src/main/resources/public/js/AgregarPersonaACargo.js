let cargoCount = 1; // Contador de personas a cargo

document.querySelector('.add-cargo').addEventListener('click', function() {
    if (cargoCount < 5) { // Límite de 5 personas a cargo (puedes ajustarlo)
        cargoCount++;

        // Crear nuevos campos para la persona a cargo
        const newCargoRow = document.createElement('div');
        newCargoRow.classList.add('cargo-row', 'p-3', 'bg-light', 'rounded', 'mb-3', 'shadow-sm');
        newCargoRow.innerHTML = `
            <h6 class="text-secondary mb-3">Persona a Cargo ${cargoCount}</h6>

            <!-- Fila: Nombre y Apellido -->
            <div class="form-row">
                <div class="form-group col-md-6">
                    <input type="text" class="form-control" name="nombrePersonaACargo[]" placeholder="Nombre Persona a Cargo" required>
                </div>
                <div class="form-group col-md-6">
                    <input type="text" class="form-control" name="apellidoPersonaACargo[]" placeholder="Apellido Persona a Cargo" required>
                </div>
            </div>

            <!-- Fila: Tipo de Documento y Número de Documento -->
            <div class="form-row">
                <div class="form-group col-md-6">
                    <select class="form-control" name="tipoDocumento[]" required>
                        <option value="" disabled selected>Selecciona Tipo de Documento</option>
                        <option value="DNI">DNI</option>
                        <option value="LC">LC</option>
                        <option value="LE">LE</option>
                    </select>
                </div>
                <div class="form-group col-md-6">
                    <input type="text" class="form-control" name="nroDocumento[]" placeholder="Número de documento" required>
                </div>
            </div>

            <!-- Fila: Fecha de Nacimiento -->
            <div class="form-row">
                <div class="form-group col-md-6">
                    <input type="date" class="form-control" name="fechaNacimiento[]" required>
                </div>
                <div class="form-group col-md-6 d-flex align-items-end">
                    <button type="button" class="btn btn-danger remove-cargo">Eliminar</button>
                </div>
            </div>
            <hr>
        `;

        document.getElementById('cargo-fields').appendChild(newCargoRow);

        // Agregar evento para eliminar el elemento
        newCargoRow.querySelector('.remove-cargo').addEventListener('click', function() {
            newCargoRow.remove();
            cargoCount--;
        });
    }
});
