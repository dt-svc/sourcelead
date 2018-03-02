 <script>
function googleApiLocation(locationId,countryId) {
    initialize(locationId,countryId);
}
function initialize(locationId,countryId) {
    	autocomplete = new google.maps.places.Autocomplete((document.getElementById(locationId)), {
    		types: ['geocode'],
      
        });
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
  place = autocomplete.getPlace();
  var address = {};
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    switch (addressType) {
       case "route":
    	 address.name=place.address_components[i].long_name;
    	 break;
      case "country":
        address.country = place.address_components[i].long_name;
        
    }
  }
  
  $("#"+countryId).val(address.country).change();
  $("#"+countryId).selectpicker('refresh');
  $("#"+addressId).val($("#"+locationId).val().replace(", "+$("#"+cityId).val()+", "+$("#"+stateId).val()+", "+$("#"+countryId).val(), ""));
  });
}
</script>