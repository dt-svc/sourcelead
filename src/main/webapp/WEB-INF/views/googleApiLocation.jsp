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


 <script>
 var options = {
 		types: [],
     componentRestrictions: {
     	country:[]
     }
 };
function orgSubscriptionApiLocation(locationId,countryId,stateId,cityId,zipCodeId,type,addressId) {
	var country = type.split(",");
	var subscriptionCountries = '';
	for(var i=0; i<country.length; i++){
		country[i] = '' + country[i] + ''
	}
	
	initializeLocation(locationId,countryId,stateId,cityId,zipCodeId,country,addressId);
	
}
function initializeLocation(locationId,countryId,stateId,cityId,zipCodeId,country,addressId) {
	 var input = (document.getElementById(locationId));
	 var countries =  "country:["+country+"]";
	 options.types=['geocode'];
	 options.componentRestrictions.country=country; 
	    autocomplete = new google.maps.places.Autocomplete(input, options);
  
 google.maps.event.addListener(autocomplete, 'place_changed', function() {
 place = autocomplete.getPlace();
 var address = {};
 for (var i = 0; i < place.address_components.length; i++) {
   var addressType = place.address_components[i].types[0];
   switch (addressType) {
      case "route":
   	 address.name=place.address_components[i].long_name;
   	 break;
     case "locality":
       address.city = place.address_components[i].long_name;
       break;
     case "administrative_area_level_1":
       address.state = place.address_components[i].long_name;
       break;
     case "country":
       address.country = place.address_components[i].long_name;
       break;
     case "postal_code":
       address.zipcode = place.address_components[i].long_name;
       
   }
 }

 $("#"+stateId).val(address.state).change();
 $("#"+cityId).val(address.city).change();
 $("#"+zipCodeId).val(address.zipcode).change();
 $("#"+addressId).val($("#"+locationId).val().replace(", "+$("#"+cityId).val()+", "+$("#"+stateId).val()+", "+$("#"+countryId).val(), ""));
 $("#"+countryId).val(address.country).change();
 $("#"+countryId).trigger("chosen:updated");
getTimezonesList();

  });
}
</script>