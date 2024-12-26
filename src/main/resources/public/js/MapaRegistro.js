


// Inicializar el mapa


var map = L.map('map').setView([-34.61, -58.38], 13); // Centrar en Buenos Aires

// Añadir el "tile layer" del mapa (estilo y proveedor del mapa)
L.tileLayer('https://{s}.google.com/vt/lyrs=r&x={x}&y={y}&z={z}', {
    maxZoom: 20,
    subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
}).addTo(map);

// Crear un geocoder para buscar direcciones
var geocoder = L.Control.Geocoder.nominatim();

// Agregar el control de geocodificación al mapa
L.Control.geocoder({
    defaultMarkGeocode: false, // No agregar automáticamente un marcador
    position: 'topright',
    placeholder: 'Buscar dirección',
    errorMessage: 'No se encontró la dirección'
}).on('markgeocode', function (e) {
    // Obtener el resultado
    var result = e.geocode;
    var lat = result.center.lat;
    var lng = result.center.lng;
    var address = result.name;  // La dirección completa

    // Colocar un marcador en la ubicación seleccionada
    L.marker(result.center).addTo(map)
        .bindPopup(address)
        .openPopup();

    // Actualizar los campos ocultos con latitud, longitud y otros detalles
    document.getElementById('lat').value = lat;
    document.getElementById('lng').value = lng;
    document.getElementById('direccion').value = address;
    // Centrar el mapa en la ubicación seleccionada
    map.setView(result.center, 13);
}).addTo(map);

// Agregar un evento de click al mapa
var currentMarker = null;

map.on('click', function (e) {
    var lat = e.latlng.lat;
    var lng = e.latlng.lng;

    console.log('Click detectado en:', lat, lng);

    document.getElementById('lat').value = lat;
    document.getElementById('lng').value = lng;

    if (currentMarker) {
        map.removeLayer(currentMarker);
    }

    reverseGeocode(lat, lng, function (address) {
        console.log('Dirección obtenida:', address);
        document.getElementById('direccion').value = address;

        currentMarker = L.marker([lat, lng])
            .addTo(map)
            .bindPopup('<p>' + address + '</p>')
            .openPopup();

        console.log('Marcador añadido en:', lat, lng);
    });
});

function reverseGeocode(lat, lng, callback) {
    var url = `https://nominatim.openstreetmap.org/reverse?lat=${lat}&lon=${lng}&format=json`;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data && data.display_name) {
                callback(data.display_name);
            } else {
                callback('Dirección no encontrada');
            }
        })
        .catch(error => {
            console.error('Error en la geocodificación inversa:', error);
            callback('Error al obtener la dirección');
        });
}

