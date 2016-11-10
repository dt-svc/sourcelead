
<!-- for Vendor edit -->
<script type="text/javascript">
	function editVendorDetails(vendorLocationId,orgRelationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		      $.ajax({
						type : "POST",
						url : sourceleadUrl+"/getClientAndVendorOrgDeatilsById?${_csrf.parameterName}=${_csrf.token}&vendorLocationId="+vendorLocationId+"&orgRelationId="+orgRelationId,
						success : function(data) {
						var json = JSON.parse(data);
						var result = "";
						
					    var selected1 = '';
						var selected2 = '';
						var selected3 = '';
						var selected4 = '';
						var selected5 = '';
						
						if(json.level == 'Tier-1'){
								selected1 = 'selected';
							} 
						
						if(json.level == 'Tier-2'){
								selected2 = 'selected';
							} 
						
						if(json.level == 'Tier-3'){
								selected3 = 'selected';
							}
						
						if(json.level == 'Tier-4'){
								selected4 = 'selected';
						}
						
						if(json.level == 'Tier-5'){
								selected5 = 'selected';
						}
						
						 var country = '';
						 var state = '';
						 var city = '';
						
						if(json.country != null && json.country !='' && json.country !='undefined'){
							country = json.country;
						}
						if(json.state != null && json.state !='' && json.state !='undefined'){
							state = json.state;
						}
						if(json.city != null && json.city !='' && json.city !='undefined'){
							city = json.city;
						}
						
						result = result + ' <form id="editClient_form"  name="editClient_form" method="post">';
						if(json.orgType == 'Company'){
							result = result + '<div class="user_summary_edit_cta" id="vendorNameDiv">';
							result = result + '<label>Client Name <span	class="text-danger">*</span></label>';
							result = result + '<input type="text"  id="vendorName" name="vendorName" value="'+json.orgName+'" placeholder="vendor name" readonly="readonly" field-name="vendor name" >';
							result = result + '</div>';
							
							result = result + '<div class="user_summary_edit_cta" id="companyWebsiteDiv">';
							result = result + '<label>Website</label>';
							result = result + '<input type="text"  id="companyWebsite" name="companyWebsite" field-name="Website" readonly="readonly" value="'+json.webSite+'" placeholder="website" ></div>';
							result = result + '</div>';
							
							
							result = result + '<div class="user_summary_edit_cta">';
							result = result + '<label>Country</label>';
							result = result + '<input type="text" name="vendorCountry" id="existingCountry" value="'+country+'" placeholder="Country" readonly="readonly" >';
							result = result + '</div>  ';
							
							result = result + '<div class="user_summary_edit_cta" id="stateDiv">';
							result = result + '<label>State</label>';
							result = result + '<input type="text" name="vendorState" id="existingState" value="'+state+'" placeholder="State" readonly="readonly" >';
							result = result + '</div> ';
							
							result = result + '<div class="user_summary_edit_cta">';
							result = result + '<label>City</label>';
							result = result + '<input type="text" name="vendorCity" id="existingCity" value="'+city+'" placeholder="City" readonly="readonly" >';
							result = result + '</div> ';
						}else{
							result = result + '<div class="user_summary_edit_cta" id="vendorNameDiv">';
							result = result + '<label>Client Name <span	class="text-danger">*</span></label>';
							result = result + '<input type="text"  id="vendorName" name="vendorName" value="'+json.orgName+'" placeholder="vendor name" field-name="vendor name" data-validation="required validate_Space validate_length length2-250" onblur="flushVendor(); validateClientName(\''+json.orgName+'\',\''+orgRelationId+'\');" >';
							result = result + '</div>';
							
							result = result + '<div class="user_summary_edit_cta" id="companyWebsiteDiv">';
							result = result + '<label>Website</label>';
							result = result + '<input type="text"  id="companyWebsite" name="companyWebsite" field-name="Website" data-validation="required validate_Space validate_length length2-50 validate_domain" data-validation-optional="true"  value="'+json.webSite+'" placeholder="website" ></div>';
							result = result + '</div>';
							
							
							result = result + '<div class="user_summary_edit_cta">';
							result = result + '<label>Country</label>';
							result = result + ' <select class="chosen-select form-control" name="vendorCountry" id="country" field-name="Country"  >';
							result = result + '</select>';
							result = result + '<input type="hidden"  id="existingCountry" value="'+country+'" >';
							result = result + '</div>  ';
							
							result = result + '<div class="user_summary_edit_cta" id="stateDiv">';
							result = result + '<label>State</label>';
							result = result + '<select  class="chosen-select form-control" name="vendorState" id="state" field-name="State"  > ';
							result = result + ' </select> ';
							result = result + '<input type="hidden"  id="existingState" value="'+state+'" >';
							result = result + '</div> ';
							
							result = result + '<div class="user_summary_edit_cta">';
							result = result + '<label>City</label>';
							result = result + '<select  class="chosen-select form-control" name="vendorCity" id="city" field-name="City"  > ';
							result = result + ' </select> ';
							result = result + '<input type="hidden"  id="existingCity" value="'+city+'" >';
							result = result + '</div> ';
						}
						
						//level
						result = result + ' <div class="user_summary_edit_cta">';
						result = result + ' <label>Tier</label>';
						result = result + ' <select id="level" name="level" >';
						result = result + ' <option '+selected1+'>Tier-1</option>';
						result = result + ' <option '+selected2+'>Tier-2</option>';
						result = result + ' <option '+selected3+'>Tier-3</option>';
						result = result + ' <option '+selected4+'>Tier-4</option>';
						result = result + ' <option '+selected5+'>Tier-5</option>';
						result = result + ' </select>';
						result = result + ' </div>';	
						
						 result = result + '<div class="clearfix"></div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<input class="us-btn" id="save_vendor" type="button" value="Save" onclick="storeVendorDetails('+orgRelationId+','+vendorLocationId+')"/> <input class="us-btn us-btn_a" type="button" value="Cancel" onclick="vendorDetailsbyLocationId('+vendorLocationId+','+orgRelationId+');"/>';
						result = result + '</div>';		
						result = result + '<div class="clearfix"></div>';
						result = result + ' </form>'; 
						$(".user_summary_edit").slideToggle(100);
						$("#editVendorDetailsDiv").empty();
				        $("#editVendorDetailsDiv").append(result);
				        
				        $(function(){
				            var config = {
				        		  '.chosen-select'           : {},
				        		  '.chosen-select-deselect'  : {allow_single_deselect:true},
				        		  '.chosen-select-no-single' : {disable_search_threshold:10},
				        		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				        		  '.chosen-select-width'     : {width:"95%"}
				        		}
				            $("#country").chosen();
				            $("#state").chosen();
				            $("#city").chosen();
				            
				            $("#country").on('change', function (e) {
				         	   getStates();
				            });
				           
				            $("#state").on('change', function (e) {
				         	   getCities();
				            });
				           
				            $('.chosen-select').each(function() {
				        			var $this = $(this);
				        			$this.next().css({'width': '100%'});
				        	  })
				        });
					}
				}); 
		      getAllCountries();
	}
</script>
<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script>

<script type="text/javascript">
	function storeVendorDetails(orgRelationId,vendorLocationId) {
         if ($('#editClient_form').validate(false, validationSettings)){
    		 var frm = $('#editClient_form').serializeFormJSON();
             var con = JSON.stringify(frm);
             con = con.replace(/[{}]/g, "");
             //It will escape all the special characters
             var jsonData = encodeURIComponent(con);
             var sourceleadUrl ='${sourceleadUrl}';
	 	 $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/vendorDetailsUpdateUsingId?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&vendorLocationId="+vendorLocationId+"&orgRelationId="+orgRelationId, 	   
	   		    success: function(data) {
	   		    	vendorDetailsbyLocationId(vendorLocationId,orgRelationId);
	   		    }
		 }); 
	     	return true;
         }else{
             return false;
           }
		
    }
	$('body').on('blur', '#editClient_form', function() {
        $("#editClient_form").showHelpOnFocus();
        $("#editClient_form").validateOnBlur(false, validationSettings);  
    });
</script>

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script>
<script type="text/javascript">
	function validateWebsite() {
		var website = document.getElementById("companyWebsite").value;
		var sourceleadUrl = '${sourceleadUrl}';
		if(website.match(/^[^\s-](.*)*$/) != null){
		var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
		
		if (website != null && website != '') {
			//$("#save_client").attr("disabled", "disabled");
			// $( "#save_client" ).addClass('button').val('Processing..');
			$.ajax({
				type : "GET",
				url : sourceleadUrl + "/ws/validateClientWebsite?website="+ website + "&sourceleadBLockedwebsites="+ sourceleadBLockedwebsites,
				success : function(data) {
					var result = data;
					if (result == "Website Duplicated" || result == "Invalid Website") {
						//existed website
						$("input#companyWebsite").after("<p class='jquery_form_error_message'>"+ website+ " is "+result+"</span>");
						document.getElementById("companyWebsite").setAttribute('record-exist', 'yes');
						document.getElementById("companyWebsite").setAttribute('record-exist-errorMsg',website + ' is '+result);
						$('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
						$("#save_vendor").attr("disabled", "disabled");
						
					} else {
						//New WebSite class="form-control"
						 $("#save_vendor").removeAttr("disabled");
						$('input#companyWebsite').removeAttr("class record-exist record-exist-errormsg");
						$('input#companyWebsite').attr('class','valid form-control');
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
				
					}
				},

			});
		  }
		}
	}
</script>

<!-- for Vendor edit Ends -->
<script type="text/javascript">
function flushVendor(){
	$('#vendorNameDiv').find('p.jquery_form_error_message').remove();
	$('input#vendorName').removeAttr("class record-exist record-exist-errormsg");
}
</script>

<script type="text/javascript">
function validateClientName(vName,orgRelationId){
	var vendorName = document.getElementById("vendorName").value;
	var sourceleadUrl = '${sourceleadUrl}';
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	$('#vendorNameDiv').find('p.jquery_form_error_message').remove();
	if(vendorName != vName){
	if (vendorName != null && vendorName != '') {
		//$("#save_client").attr("disabled", "disabled");
	//	$( "#save_client" ).addClass('button').val('Processing..');
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/ws/isClientNameValid?${_csrf.parameterName}=${_csrf.token}&clientName="+vendorName+"&orgRelationId="+orgRelationId,
			success : function(data) {
				var result = data;
				if (result == true) {
					//duplicate client
					$("input#vendorName").after("<p class='jquery_form_error_message'>"+ vendorName+ " is Already Existed</span>");
					document.getElementById("vendorName").setAttribute('record-exist', 'yes');
					document.getElementById("vendorName").setAttribute('record-exist-errorMsg',vendorName +"  already existed");
				} else {
					//New WebSite class="form-control"
					//$("#save_client").removeAttr("disabled");
					$('input#vendorName').removeAttr("class record-exist record-exist-errormsg");
					$('input#vendorName').attr('class','valid form-control');
					$('#vendorNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_vendor").click(function() {
						if ($(this).validate(false, validationSettings)) {
						}
					});
				}
			},

		});
	}
	}
}
</script>

<script type="text/javascript">
function getAllCountries(){
	setTimeout(function(){ 
		var countryName = $("#existingCountry").val();
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/sourceLeadCountryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#country').empty();
	        	$('#country').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == countryName){
	            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}else if(json[i] == '${loginCountryName}'){
	            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            	
	            }
	            
	            $('#country').trigger("chosen:updated");
	            getStates();
	        },
	        error: function(e){
	        }
	    });
	}, 100);
} 
</script>


<script>
function getStates(){
	var countryName = $("#country").val();
	
	//to set existing state value(Auto populate)
	var stateName = $("#state").val();
	if(stateName == null || stateName == 'undefined' || stateName == ''){
		stateName = document.getElementById("existingState").value;
	}
	//end state Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == stateName){
	            		$('#state').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#state').trigger("chosen:updated");
	            getCities();
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	//to set existing city value(Auto populate)
	var cityName = $("#city").val();
	if(cityName == null || cityName == 'undefined' || cityName == ''){
		cityName = document.getElementById("existingCity").value;
	}
	//end city Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == cityName){
	            		$('#city').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>

