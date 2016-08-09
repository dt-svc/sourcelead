function cscInit(){
	 $('#country').tagit({
	       singleField: true,
	       singleFieldNode: $('#CountryAutocomplete')
	       ,tagLimit:1
	       ,placeholderText:""
	   });
};

 
 
 
/* function getAllCountries(sourceleadUrl){
	 alert("kii"+sourceleadUrl);
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#country').tagit({
	        		availableTags: json
		       	 	});
	        },
	        error: function(e){
	        }
	    });
		
}*/
 
 
 
 
 
 
 
 
 
 





