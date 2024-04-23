$(document).ready(function () {

  var map = L.map('map', {
    center: [-34.59845,-58.42018],
    zoom: 17
  });

  L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
  }).addTo(map);

  var popup = L.popup();

  function onMapClick(e) {
    popup
      .setLatLng(e.latlng)
      .setContent("You clicked the map at " + e.latlng.toString())
      .openOn(map);
  }

  map.on('click', onMapClick);

  var marker = L.marker([-34.59864, -58.41995]).addTo(map);
  marker.bindPopup("<b>UTN BA</b><br> Medrano.").openPopup();

});