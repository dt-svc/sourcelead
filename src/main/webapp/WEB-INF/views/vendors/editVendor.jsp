<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<!-- these files for autocomplete with tagit -->
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>

<!-- end files for autocomplete with tagit -->

<script type="text/javascript">
window.onload = function () {
	cscInit();
 };
</script>
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
						result = result + ' <form id="editClient_form"  name="editClient_form" method="post">';
						result = result + '<div class="user_summary_edit_cta" id="vendorNameDiv">';
						result = result + '<label>Client Name <span	class="text-danger">*</span></label>';
						result = result + '<input type="text"  id="vendorName" name="vendorName" value="'+json.orgName+'" placeholder="vendor name" field-name="vendor name" data-validation="required validate_Space validate_length length2-250" onblur="flushVendor(); validateClientName(\''+json.orgName+'\',\''+orgRelationId+'\');" >';
						result = result + '</div>';
						result = result + '<div class="user_summary_edit_cta" id="companyWebsiteDiv">';
						result = result + '<label>Website</label>';
						result = result + '<input type="text"  id="companyWebsite" name="companyWebsite" field-name="Website" data-validation="required validate_Space validate_length length2-50 validate_domain" data-validation-optional="true"  value="'+json.webSite+'" placeholder="client website" ></div>';
						result = result + '</div>';
						
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>City</label>';
						if(json.city != null && json.city !='' && json.city !='undefined'){
							result = result + '<input type="text"  id="vendorCity" name="vendorCity" value="'+json.city+'" placeholder="vendor city" ></div>';
						}else{
							result = result + '<input type="text"  id="vendorCity" name="vendorCity"  placeholder="vendor city" value=""></div>';
						}
						
						result = result + '</div> ';
						
			 			result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>State</label>';
						if(json.state != null && json.state !='' && json.state !='undefined'){
							result = result + '<input type="text"  id="vendorState" id="vendorState" name="vendorState" value="'+json.state+'" placeholder="vendor state" ></div>';
						}else{
							result = result + '<input type="text"  id="vendorState" id="vendorState" name="vendorState" placeholder="vendor state" ></div>';
						}
						result = result + '</div> ';
						
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Country</label>';
						/* if(json.country != null && json.country !='' && json.country !='undefined'){
							result = result + '<input type="text"  id="vendorCountry" name="vendorCountry" value="'+json.country+'" placeholder="vendor country" ></div>';
						}else{
							result = result + '<input type="text"  id="vendorCountry" name="vendorCountry"  placeholder="vendor country" ></div>';
						}  */
						result = result + '<div class="col-md-9">';
						result = result + '	<input type="hidden" class="form-control" id="CountryAutocomplete" name="country"';
						result = result + ' field-name="Client Country" value="india" data-validation="validate_Space" data-validation-optional="true"/>'; 
						result = result + '<ul id="country" class="form-control" onkeypress="getAllCountries('+sourceleadUrl+');"></ul>';
						result = result + '</div>';
						result = result + '</div>  ';
						
					
						//level
						result = result + ' <div class="user_summary_edit_cta">';
						result = result + ' <label>Level</label>';
						result = result + ' <select class="rate_sele" id="level" name="level" >';
						result = result + ' <option '+selected1+'>Level-1</option>';
						result = result + ' <option '+selected2+'>Level-2</option>';
						result = result + ' <option '+selected3+'>Level-3</option>';
						result = result + ' <option '+selected4+'>Level-4</option>';
						result = result + ' <option '+selected5+'>Level-5</option>';
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
					},
				}); 
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
<script>
function getAllCountries(sourceleadUrl){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: sourceleadUrl+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').tagit({
        		allowSpaces: false,
        		availableTags: json
        		
	       	 	});
        },
        error: function(e){
        }
    });
}
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
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
</script> 