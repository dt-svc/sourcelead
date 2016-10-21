
<!-- client details View starts-->
<script type="text/javascript">
	function clientDetails(clientLocationId,orgRelationID) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
					type : "POST",
					url : sourceleadUrl+"/getClientAndVendorOrgDeatilsById?${_csrf.parameterName}=${_csrf.token}&vendorLocationId="+clientLocationId+"&orgRelationId="+orgRelationID,
					success : function(data) {
						var json = JSON.parse(data);
						var result = "";
						var city = "";
						var state = "";
						var country = "";
						
						result = result+ '<div class="project_tabs_inn_table">';
                        
						result = result + '<div class="project_details">';
						result = result + ' <div class="useredit useredit_a">';
						result = result + '<a id="edit-client" href="javascript:editClientDetails('+clientLocationId+','+orgRelationID+')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
						result = result + ' </div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client name:</strong>';
						
						if(json.orgName !=null && json.orgName !='' && json.orgName !='undefined'){
						result = result + '<span>'+json.orgName+'</span>';
						}
						
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="project_details_cta">';
						
						result = result + '<strong>Website:</strong>';
						if(json.webSite !=null && json.webSite !='' && json.webSite !='undefined'){
						result = result + '<span>'+json.webSite+'</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
					
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client Country:</strong>';
						if(json.country != null && json.country !='' && json.country !='undefined'){
							result = result + '<span>'+json.country+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span>No Data</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client State:</strong>';
						if(json.state != null && json.state !='' && json.state !='undefined'){
							result = result + '<span>'+json.state+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span>No Data</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client City:</strong>';
						if(json.city != null && json.city !='' && json.city !='undefined'){
							result = result + '<span>'+json.city+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span>No Data</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Created By :</strong>';
						if(json.vendorCreateduserName != null && json.vendorCreateduserName !='' && json.vendorCreateduserName !='undefined'){
							result = result + '<span>'+json.vendorCreateduserName+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span>No Data</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Level :</strong>';
						if(json.level != null && json.level !='' && json.level !='undefined'){
							result = result + '<span>'+json.level+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span>No Data</span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="clearfix"></div>';
						result = result + ' <div class="user_summary_edit user_summary_edit_a" id="editClientDetailsDiv"></div>';
						result = result + '</div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						
						$("#tab-4-addTemplate").empty();
			    		$("#tab-4").empty();
			    		$("#tab-3").empty();
						$("#tab-1").empty();
						$("#tab-1").append(result);
						$("#tab-1").show();
						$("#testDiv").hide();
						
						$(".project-tabs-menu a").click(
								function(event) {
									event.preventDefault();
									$(this).parent().addClass("current");
									$(this).parent().siblings().removeClass(
											"current");
									var tab = $(this).attr("href");
									$(".project_tabs_inn").not(tab).css(
											"display", "none");
									$(tab).fadeIn();
								});

					}
				});
	}
</script>
<!--  client details ends -->



<script type="text/javascript">
	function editClientDetails(clientLocationId,orgRelationID) {
		var sourceleadUrl = '${sourceleadUrl}';
		      $.ajax({
						type : "POST",
						url : sourceleadUrl+"/getClientAndVendorOrgDeatilsById?${_csrf.parameterName}=${_csrf.token}&vendorLocationId="+clientLocationId+"&orgRelationId="+orgRelationID,
						success : function(data) {
						var json = JSON.parse(data);
						
						var result = "";
						
					    var selected1 = '';
						var selected2 = '';
						var selected3 = '';
						var selected4 = '';
						var selected5 = '';
						
						if(json.level == 'Level-1'){
								selected1 = 'selected';
							} 
						
						if(json.level == 'Level-2'){
								selected2 = 'selected';
							} 
						
						if(json.level == 'Level-3'){
								selected3 = 'selected';
							}
						
						if(json.level == 'Level-4'){
								selected4 = 'selected';
						}
						
						if(json.level == 'Level-5'){
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
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Client Name<span class="text-danger">&nbsp; *</span> </label>';
								result = result + '<input type="text"  id="clientName" name="clientName" value="'+json.orgName+'" readonly="readonly" field-name="client name" placeholder="client name">';
								result = result + '</div>';
								
								result = result + '<div class="user_summary_edit_cta" id="companyWebsiteDiv">';
								result = result + '<label>Website</label>';
								result = result + '<input type="text"  id="companyWebsite" name="companyWebsite" field-name="Website" readonly="readonly" value="'+json.webSite+'" placeholder="client website" ></div>';
								result = result + '</div>';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Country</label>';
								result = result + '<input type="text" name="clientCountry" id="existingCountry" value="'+country+'" readonly="readonly">';
								result = result + '</div>  ';
								
								result = result + '<div class="user_summary_edit_cta" id="stateDiv">';
								result = result + '<label>State</label>';
								result = result + '<input type="text" name="clientState" id="existingState" value="'+state+'" readonly="readonly" >';
								result = result + '</div> ';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>City</label>';
								result = result + '<input type="text" name="clientCity" id="existingCity" value="'+city+'" readonly="readonly">';
								result = result + '</div> ';
						}else{
								result = result + ' <form id="editClient_form"  name="editClient_form" method="post">';
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Client Name<span class="text-danger">&nbsp; *</span> </label>';
								result = result + '<input type="text"  id="clientName" name="clientName" value="'+json.orgName+'" data-validation="required validate_Space validate_length length2-250" field-name="client name" placeholder="client name">';
								result = result + '</div>';
								
								result = result + '<div class="user_summary_edit_cta" id="companyWebsiteDiv">';
								result = result + '<label>Website</label>';
								result = result + '<input type="text"  id="companyWebsite" name="companyWebsite" field-name="Website" data-validation="validate_Space validate_length length2-100 validate_domain" data-validation-optional="true"  value="'+json.webSite+'" placeholder="client website" ></div>';
								result = result + '</div>';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Country</label>';
								result = result + ' <select class="chosen-select form-control" name="clientCountry" id="country" field-name="Country"  >';
								result = result + '</select>';
								result = result + '<input type="hidden"  id="existingCountry" value="'+country+'" >';
								result = result + '</div>  ';
								
								result = result + '<div class="user_summary_edit_cta" id="stateDiv">';
								result = result + '<label>State</label>';
								result = result + '<select  class="chosen-select form-control" name="clientState" id="state" field-name="State"  > ';
								result = result + ' </select> ';
								result = result + '<input type="hidden"  id="existingState" value="'+state+'" >';
								result = result + '</div> ';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>City</label>';
								result = result + '<select  class="chosen-select form-control" name="clientCity" id="city" field-name="City"  > ';
								result = result + ' </select> ';
								result = result + '<input type="hidden"  id="existingCity" value="'+city+'" >';
								result = result + '</div> ';
						}
						
						
						//level
                        result = result + ' <div class="user_summary_edit_cta">';
                        result = result + ' <label>Level</label>';
                        result = result + ' <select id="level" name="level" >';
                        result = result + ' <option '+selected1+'>Level-1</option>';
                        result = result + ' <option '+selected2+'>Level-2</option>';
                        result = result + ' <option '+selected3+'>Level-3</option>';
                        result = result + ' <option '+selected4+'>Level-4</option>';
                        result = result + ' <option '+selected5+'>Level-5</option>';
                        result = result + ' </select>';
                        result = result + ' </div>';   
						
						result = result + '<div class="clearfix"></div>';
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<input class="us-btn" id="save_client" type="button" value="Save" onclick="storeClientDetails('+orgRelationID+','+clientLocationId+')"/> <input class="us-btn us-btn_a" type="button" value="Cancel" onclick="clientDetails('+clientLocationId+','+orgRelationID+')"/>';
						result = result + '</div>';		
						result = result + '<div class="clearfix"></div>';
						result = result + ' </form>';
						
						$(".user_summary_edit").slideToggle(100);
						$("#editClientDetailsDiv").empty();
				        $("#editClientDetailsDiv").append(result);
				        
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
	function storeClientDetails(orgRelationId,clientLocationId) {
         if ($('#editClient_form').validate(false, validationSettings)){
    		 var frm = $('#editClient_form').serializeFormJSON();
             var con = JSON.stringify(frm);
             con = con.replace(/[{}]/g, "");
             //It will escape all the special characters
             var jsonData = encodeURIComponent(con);
             var sourceleadUrl ='${sourceleadUrl}';
	 	 $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/clientDetailsUpdateUsingId?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&vendorLocationId="+clientLocationId+"&orgRelationId="+orgRelationId,
	   		    success: function(data) {
	   		    	clientDetails(clientLocationId,orgRelationId);
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
						$("#save_client").attr("disabled", "disabled");
						
					} else {
						//New WebSite class="form-control"
						 $("#save_client").removeAttr("disabled");
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

<script type="text/javascript">
 function validateClientEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getClientEmailVerification?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
		        	}
		        },
		    });
	 }
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
