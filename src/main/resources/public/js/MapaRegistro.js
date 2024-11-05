// Inicializar el mapa
var map = L.map('map').setView([-34.61, -58.38], 13); // Centrar en Buenos Aires

// Añadir el "tile layer" del mapa (estilo y proveedor del mapa)
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
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

// Opcional: Centrar el mapa en la ubicación del usuario
if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function (position) {
        var lat = position.coords.latitude;
        var lng = position.coords.longitude;
        map.setView([lat, lng], 13); // Centrar en la ubicación del usuario
    });
}
+
// Agregar un evento de click al mapa
map.on('click', function (e) {
    var lat = e.latlng.lat;
    var lng = e.latlng.lng;

    document.getElementById('lat').value = lat;
    document.getElementById('lng').value = lng;

    var botonRecomendarPuntos = '<button class="btn btn-primary">Recomendacion de Puntos</button>';

    L.popup()
        .setLatLng(e.latlng)
        .setContent(botonRecomendarPuntos)
        .openOn(map);
});