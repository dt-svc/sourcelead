function cscInit(){
	 $('#country').tagit({
	       singleField: true,
	       singleFieldNode: $('#CountryAutocomplete')
	       ,tagLimit:1
	       ,placeholderText:""
           ,afterTagRemoved: function(evt, ui){
                   $('#multiState').tagit('removeAll');
                   $('#multiCity').tagit('removeAll');
                   $('#state').tagit('removeAll');
                   $('#city').tagit('removeAll');
                   return false;
           }
	   });
	 
	 
	 $('#multiState').tagit({
	       singleField: true,
	       singleFieldNode: $('#multiStateAutoComplete')
	       ,placeholderText:""
	       ,afterTagRemoved: function(evt, ui){
                   $('#multiCity').tagit('removeAll');
                   return false;
           }
	   });
	 
	 $('#multiCity').tagit({
	       singleField: true,
	       singleFieldNode: $('#multiCityAutoComplete')
	       ,placeholderText:""
	 });
	 
	 
	 $('#state').tagit({
	       singleField: true,
	       singleFieldNode: $('#stateAutoComplete')
	       ,tagLimit:1
	       ,placeholderText:""
	       ,afterTagRemoved: function(evt, ui){
                 $('#city').tagit('removeAll');
                 return false;
         }
	   });
	 
	 $('#city').tagit({
	       singleField: true,
	       singleFieldNode: $('#cityAutoComplete')
	       ,tagLimit:1
	       ,placeholderText:""
	 });
	 
	 $('#location').tagit({
	       singleField: true,
	       singleFieldNode: $('#locationAutoComplete')
	       ,tagLimit:1
	       ,placeholderText:""
	   });
};

