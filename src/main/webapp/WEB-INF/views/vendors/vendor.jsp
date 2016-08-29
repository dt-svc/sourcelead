<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
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
 <script>
	$(document).ready(function() {
		document.getElementById("client_form").reset();
	});
</script>
 
<style>
.clsDatePicker {
	z-index: 100000 !important;
}
</style>

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Vendors</h2>
		</div>
		<div class="pull-right brud-crum"><a href="dashboard">Home</a>&raquo;<a href="clientDashboard"> Client List</a>
			&raquo; Client</div>
	</section>
	
	<div class="content-area content-new clearfix">
	<form:form id="client_form" name="client_form" method="post">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           <h3>Vednor Details</h3>
            <div class="form-group" ><label class="col-md-3" for="Project Id">Vendor Name<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9" id="clientNameDiv">
					<input type="text" class="form-control" id="orgName" name="orgName" value="${orgName}" onblur="validateClientName();" field-name="Client Name" data-validation="required validate_Space validate_length length2-250" value="">
					<span class="hidden">Enter Client Name</span>
				</div>
			</div>
            
			<div class="form-group " id="companyWebsiteDiv">
				<label class="col-md-3">Website</label>
				<div class="col-md-9">
					<input type="text" class="form-control" id="webSite"
						name="webSite" onblur="validateWebsite();" field-name="Website"
						data-validation="validate_Space validate_length length2-50 validate_domain"
						data-validation-optional="true" value="${webSite}"> <span
						class="hidden">Website</span>
				</div>
			</div>
						
			<div class="form-group">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
					<input type="hidden" class="form-control" id="CountryAutocomplete" name="country"
						name="country" field-name="Client Country" value="${loginCountryName}" data-validation="validate_Space" data-validation-optional="true"/> 
						<ul id="country" class="form-control" onkeypress="getAllCountries('${sourceleadUrl}');"></ul>
				</div>
			</div>
			
			
			<div class="form-group">
			<label class="col-md-3">State</label>
			<div class="col-md-9">
				<input type="hidden" id="stateAutoComplete" name="state"
					field-name="Client State" class="form-control" value="" data-validation="validate_Space" data-validation-optional="true">
			
			<ul id="state"  onkeypress="getStates('${sourceleadUrl}');"></ul>
			</div>
		 </div>
		
		<div class="form-group">
			<label class="col-md-3">City</label>
			<div class="col-md-9">
				<input type="hidden" id="cityAutoComplete" name="city" field-name="Client State" class="form-control" value="" data-validation="validate_Space" data-validation-optional="true">
			    <ul id="city"  onkeypress="getCities('${sourceleadUrl}');"></ul>
			</div>
		</div>
				
         
            <div class="form-group">
              <label class="col-md-3">Level</label>
              <div class="col-md-9">
                  <select class="form-control selectpicker" id="level" name="level">
                    <option value="Level-1">Level-1</option>
                    <option value="Level-2">Level-2</option>
                    <option value="Level-3">Level-3</option>
                    <option value="Level-4">Level-4</option>
                    <option selected="selected" value="Level-5">Level-5</option>
                  </select>
              </div>
            </div>
          </div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <h3>Contact Details</h3>
            
            <div class="form-group">
              <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input class="form-control error" id="firstName" name="firstName" value="" field-name="First Name" data-validation="required validate_Space validate_length length2-20" type="text">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Last Name</label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="lastName" name="lastName" value="">
              </div>
            </div>
            
            <div class="form-group" id="EmailIdDiv">
				<label class="col-md-3" for="Project Manager">Email<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9">
					<input type="text" class="form-control" id="email" name="email" value="${emailId}" field-name="Email Id" data-validation="required validate_Space validate_email" onKeyUp="validateClientEmail();">
	                            <span class="hidden">Valid Email</span>
				</div>
			</div>
			
            <div class="form-group">
              <label class="col-md-3">Phone Number</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" value="">
              </div>
            </div>
            <div class="form-group">
              <label class="col-md-3">Status</label>
              <div class="col-md-9">
                <select class="form-control selectpicker" id="spocStatus" name="spocStatus">
                  <option value="Active">Active</option>
                  <option value="In-Active">In-Active</option>
                  <option value="New">New</option>
                  <option value="Negotiation">Negotiation</option>
                  <option value="Contracted">Contracted</option>
                </select>
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Owner<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <select class="chosen-select form-control" name="owner" id="owner" multiple="true">
                <c:forEach var="user" items="${userList}">
	                     <option value="${user.id}" ${user.id  == SourceLeadWeb_userId ? 'selected' : ''} >${user.firstName} ${user.lastName}</option>
                </c:forEach>
                </select>
              </div>
            </div>
            
            </div>
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
            <div class="form-group">
              <label class="col-md-3"></label>
              <div class="col-md-9 checkbox">
                 <label> <input type="checkbox" value="Y"
							name="isMailSent" checked="checked"> Send Invitation
				</label>
              </div>
            </div>
               <div class="form-group">
                  <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
                  <div class="col-md-9">
                         <select class="form-control select-bg" id="role" name="roleType" field-name="Role" data-validation="required">
		                        <option value="">-Select Role-</option>
									<option value="Admin">Admin</option>
									<option value="Approver">Approver/Project Manager</option>
									<option value="HR Manager">HR Manager</option>
									<option value="Account Manager">Account Manager</option>
									<option value="Recruiter">Recruiter</option>
			              </select>
                  </div>
                </div>
          </div>
        </div>
      </div>
      <div class="form-footer clearfix">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" id="save_client" onclick="saveVendor(); flushSaveClient()" class="btn btn-primary"> 
			<input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/clientDashboard'/>'" class="btn btn-default ml">
          </div>
        </div>
      </div>
      </form:form>
      </div>
      
	
</section>
<!-- Content Area Ends-->

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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
	function saveVendor() {
		/* var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#client_form').validate(false, validationSettings)) { */
			var frm = $('#client_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_client").attr("disabled", "disabled");
			$("#save_client").addClass('button').val('Processing..');
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/saveClient?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&responderType=Vendor",
						success : function(result) {
								location.replace(sourceleadUrl+ "/vendorsList");
						},
					});
			/* return true;
		} else {
			return false;
		} */
	}
	$('body').on('blur', '#client_form', function() {
		$("#client_form").showHelpOnFocus();
		$("#client_form").validateOnBlur(false, validationSettings);
	});
</script>


<!-- <script type="text/javascript">
	function saveClient() {
			var frm = $('#client_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			var owner = document.getElementById("owner").value; 
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_client").attr("disabled", "disabled");
			$("#save_client").addClass('button').val('Processing..');
			$
				.ajax({
						type : "POST",
						url : sourceleadUrl+ "/saveClient?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&responderType=Client",
						success : function(result) {
							if (result == 'clientDashboard') {
								location.replace(sourceleadUrl+ "/clientDashboard");
							}

						},
					}); 
		
	}
		
	$('body').on('blur', '#client_form', function() {
		$("#client_form").showHelpOnFocus();
		$("#client_form").validateOnBlur(false, validationSettings);
	});	
		
</script> -->



<script type="text/javascript">
function validateClientName(){
	var clientName = document.getElementById("orgName").value;
	var sourceleadUrl = '${sourceleadUrl}';
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (clientName != null && clientName != '') {
		//$("#save_client").attr("disabled", "disabled");
		// $( "#save_client" ).addClass('button').val('Processing..');
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/ws/isClientNameValid?${_csrf.parameterName}=${_csrf.token}&clientName="+clientName,
			success : function(data) {
				var result = data;
				if (result == true) {
					//duplicate client
					$("input#orgName").after(
							"<p class='jquery_form_error_message'>"
									+ clientName
									+ " is Already Existed</span>");
					document.getElementById("orgName").setAttribute(
							'record-exist', 'yes');
					document.getElementById("orgName").setAttribute(
							'record-exist-errorMsg',
							clientName +"  already existed");
				} else {
					//New WebSite class="form-control"
					//$("#save_client").removeAttr("disabled");
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_client").click(function() {
						if ($(this).validate(false, validationSettings)) {
						}
					});
				}
			},

		});
	}
}
</script>


<script type="text/javascript">
 function validateClientEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		/*  $("#save_client").attr("disabled", "disabled");
         $("#save_client" ).addClass('button').val('Processing..'); */
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
		        		 $("#save_client" ).addClass('button').val('Save');
                         $("#save_client").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>     

 
 
 <script type="text/javascript">
function validateWebsite(){
      var website = document.getElementById("webSite").value; 
     var sourceleadUrl ='${sourceleadUrl}';
     var sourceleadBLockedwebsites ='${sourceLeadBlockedSocialDomains}';
	 $('input#webSite').removeAttr( "record-exist record-exist-errormsg");
	 if(website != null && website != ''){
		
		 $("#register_button").attr("disabled", "disabled");
		 $( "#register_button" ).addClass('button').val('Processing..');
			 $.ajax({
				    type: "GET",
       	            url: sourceleadUrl+"/ws/validateOrgWebsite?website="+website+"&sourceleadBLockedwebsites="+sourceleadBLockedwebsites,
			        success: function(data) {
			        	 var result = data;
			        		if (result == "Website Duplicated" || result == "Invalid Website") {
		        			//existed website
		    	    		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove(); 
		    	    		$("input#webSite").closest('b').after("<p class='jquery_form_error_message'> "+website+" "+result+"</p>");
		    				$('input#webSite').attr('record-exist','yes');
		    				$('input#webSite').attr('record-exist-errorMsg',website+' '+result+''); 
		    				$('input#webSite').addClass('form-control');
			        	}
			        	else{
			        		//New WebSite
			        		$("#save_client").removeAttr("disabled");
				    		$('input#webSite').removeAttr( "class record-exist record-exist-errormsg");
				    		$('input#webSite').addClass('form-control');
				    		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
							$( "#save_client" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
			        	}  
			       },
			        
		   }); 
	}
}
</script>

<script type="text/javascript">
	function flushSaveClient() {
		$("#save_client").removeAttr("disabled");
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

<script>
function getStates(sourceleadUrl){
	var countryName = document.getElementById("CountryAutocomplete").value;
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').tagit({
	        		allowSpaces: false,
	        		availableTags: json
	        		
		       	 	});
	        },
	        error: function(e){
	        }
	    });
	}
}
</script> 


<script>
function getCities(sourceleadUrl){
	var countryName = document.getElementById("CountryAutocomplete").value;
	var stateName = document.getElementById("stateAutoComplete").value;
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').tagit({
	        		allowSpaces: false,
	        		availableTags: json
	        		
		       	 	});
	        },
	        error: function(e){
	        }
	    });
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