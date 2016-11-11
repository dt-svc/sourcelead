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
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<script type="text/javascript">
window.onload = function () {
	getAllCountries();
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

.ui-front {
    z-index: 9999999;
}

</style>




<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Create Client</h2>
		</div>
		<div class="pull-right brud-crum"><a href="dashboard">Home</a>&raquo;<a href="clientDashboard"> Client List</a>
			&raquo; Client</div>
	</section>
	
	<div class="content-area content-new clearfix">
	<div id="loading"></div> 
	<form:form id="client_form" name="client_form" method="post">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
           <h3>Client Details</h3>
            <div class="form-group" ><label class="col-md-3" for="Project Id">Client Name<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9" id="clientNameDiv">
					<input type="text" class="form-control" id="orgName" name="orgName" value="${orgName}" onclick="getOrgName();"  onblur="getOrgDetails(); validateClientName(); getRolesList();" field-name="Client Name" data-validation="required validate_Space validate_length length2-250" value="">
					<input type="hidden" id="orgLocationId" />
					<span class="hidden">Enter Client Name</span>
				</div>
			</div>
            
			<div class="form-group " id="companyWebsiteDiv">
				<label class="col-md-3">Website</label>
				<div class="col-md-9">
				<input type="text" class="form-control" id="webSite" name="webSite" value="" field-name="Client Website" data-validation="validate_Space validate_domain validate_length length2-100" data-validation-optional="true">
				</div>
			</div>
						
			<div class="form-group" id="countryDiv" style="display: block;">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				 <select class="chosen-select form-control select-bg" name="country" id="country" field-name="Country"  >
                 </select>
				</div>
			</div>
			
			<div class="form-group" id="orgCountryDiv" style="display: none;">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				<input type="text" class="form-control" id="orgCountry" name="orgCountry" value="" readonly="readonly">
				</div>
			</div>
			
			
		 <div class="form-group" id="stateDiv" style="display: block;">
			<label class="col-md-3">State</label>
			<div class="col-md-9">
			     <select  class="chosen-select form-control" name="state" id="state" field-name="State"  >
                 </select>
			</div>
		 </div>
		 
		 <div class="form-group" id="orgStateDiv" style="display: none;">
				<label class="col-md-3">State</label>
				<div class="col-md-9">
				<input type="text" class="form-control" id="orgState" name="orgState" value="" readonly="readonly">
				</div>
		 </div>
		
		<div class="form-group" id="cityDiv" style="display: block;">
			<label class="col-md-3">City</label>
			<div class="col-md-9">
			     <select  class="chosen-select form-control" name="city" id="city" field-name="City"  ></select>
                 
			</div>
		</div>
		
		<div class="form-group" id="orgCityDiv" style="display: none;">
				<label class="col-md-3">City</label>
				<div class="col-md-9">
				<input type="text" class="form-control" id="orgCity" name="orgCity" value="" readonly="readonly">
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
            
            <div class="form-group" id="EmailIdDiv">
				<label class="col-md-3" for="Project Manager">Email<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9">
					<input type="text" class="form-control" id="email" name="email" value="${emailId}" field-name="Email Id" data-validation="required validate_Space validate_length length2-100 validate_email" onblur="validateEmail();getSpocUserDetails();">
	                            <span class="hidden">Valid Email</span>
				</div>
			</div>
            
            <div class="form-group">
              <label class="col-md-3">First Name<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9">
                <input class="form-control error" id="firstName" name="firstName" value="" field-name="First Name" data-validation="required validate_Space validate_AlphaNumber validate_length length2-100" type="text">
              </div>
            </div>
            
            <div class="form-group">
              <label class="col-md-3">Last Name</label>
              <div class="col-md-9">
                <input type="text" class="form-control" id="lastName" name="lastName" data-validation="validate_Space validate_AlphaNumber validate_length length2-100" data-validation-optional="true">
              </div>
            </div>
			
            <div class="form-group">
              <label class="col-md-3">Phone Number</label>
              <div class="col-md-9">
                <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" data-validation="validate_NumberAlphabet validate_phone validate_length length9-20" data-validation-optional="true">
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
                <select multiple class="chosen-select form-control" name="owner" id="owner" field-name="Owner"  data-validation="required">
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
            
            <!-- These are the default sourelead roles we are providing for spoc in client creation -->
              <div class="form-group" id="roleDiv" style="display: block;">
                  <label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
                  <div class="col-md-9">
                         <select class="form-control select-bg" id="role" name="roleType" field-name="Role" data-validation="required">
		                        <option value="">-Select Role-</option>
			              </select>
                  </div>
                </div>
                
                <div class="form-group" id="userRoleDiv" style="display: none;">
					<label class="col-md-3">Role <span class="text-danger">&nbsp; *</span></label>
					<div class="col-md-9">
					<input type="text" class="form-control" id="userRoleText"  readonly="readonly">
					<input type="hidden" id="userRole" name="roleTypeId" value="" />
					
				   </div>
		        </div>
		 
          </div>
        </div>
      </div>
      <div class="form-footer clearfix">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" id="save_client" onclick="saveClient(); flushSaveClient()" class="btn btn-primary"> 
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
function getOrgName(){
	var orgCode = '${SourceLeadWeb_orgCode}';
	var sourceleadUrl = '${sourceleadUrl}';
	
	
	$( "#orgName" ).val( '' );
	$( "#orgLocationId" ).val( '' );
	
	$("#webSite").attr("disabled", false);
	$("#webSite" ).val('');
	
	$("#orgCountry").val('');
	$("#orgState").val('');
	$("#orgCity").val('');
	
	getAllCountries();
	$('#state').empty();
	$('#state').trigger("chosen:updated"); 
	$('#city').empty();
	$('#city').trigger("chosen:updated");
	
	
	
	document.getElementById("countryDiv").style.display="";
	document.getElementById("orgCountryDiv").style.display="none";
	
	document.getElementById("stateDiv").style.display="";
	document.getElementById("orgStateDiv").style.display="none";
	
	document.getElementById("cityDiv").style.display="";
	document.getElementById("orgCityDiv").style.display="none";
	
	$("#role" ).val('');
	$("#role").attr("data-validation", "required");
	document.getElementById("roleDiv").style.display="";
	document.getElementById("userRoleDiv").style.display="none";
	
	$.ajax({                           
    	type: "POST",
    	url : sourceleadUrl+"/getOrgs?${_csrf.parameterName}=${_csrf.token}&orgCode="+orgCode,
        success: function(data) {
        	var json = JSON.parse(data);
        	var org = [];
        	for(var i=0;i<json.length;i++){
       		 var label = json[i][0]; 
       		 var value = json[i][1];
       	     tmp = {
       	        'label': label,
       	        'value': value
       	      };
       	  	org.push(tmp);
       		}
        	$("#orgName").autocomplete({
                
                minLength: 0,
                source: org,
                focus: function( event, ui ) {
                       $( "#orgName" ).val( ui.item.label );
                       $( "#orgLocationId" ).val( ui.item.value );
                       return false;
                   },
                select: function( event, ui ) {
                    return false;
                }  
             })
        },
        error: function(e){
        }
    });
}
</script>


<script type="text/javascript">
function getOrgDetails()
{
	var orgName = document.getElementById("orgName").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(orgName && orgLocationId){
   	 $.ajax({
   	    	type: "POST",
   	    	url : sourceleadUrl+"/getOrgDetails?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
   	        contentType: "application/json; charset=utf-8",
   	        dataType: "json",
   	        success: function(data) {
   	        	if(data != null && data != ''){
   	        		var json = JSON.parse(data);
   	        		if(json[0] != null && json[0] != ''){
   	        			document.getElementById("countryDiv").style.display="none";
   	        			document.getElementById("orgCountryDiv").style.display="";
   	        		    $("#orgCountry").val(json[0]);
   	        		     getStates();
   	        		}
   	        		
   	        		if(json[1] != null && json[1] != ''){
   	        			document.getElementById("stateDiv").style.display="none";
   	        			document.getElementById("orgStateDiv").style.display="";
   	        		    $("#orgState").val(json[1]);
   	        		     getCities();
   	        		}
   	        		
   	        		if(json[2] != null && json[2] != ''){
   	        			document.getElementById("cityDiv").style.display="none";
   	        			document.getElementById("orgCityDiv").style.display="";
   	        		    $("#orgCity").val(json[2]);
   	        		}
		   	        
			      	if(json[3] != null && json[3] != ''){
			      		$("#webSite").val(json[3]);
			      		$("#webSite").attr("disabled", true);
			      	}
			      	 
   	        	}
   			 },
   	        error: function(e){
   	        }
   	    });
   	 
   validateEmail(); 
   getSpocUserDetails();
	
	}
	
}
</script>


<script type="text/javascript">
function getRolesList(){
	var orgName = document.getElementById("orgName").value;
	var orgLocationId = document.getElementById("orgLocationId").value;
	var sourceleadUrl ='${sourceleadUrl}';
	
	if(orgName){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getRolesList?${_csrf.parameterName}=${_csrf.token}&orgName="+orgName+"&orgLocationId="+orgLocationId+"&moduleType=VMS", 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	var output = "";
	        	output = output + "<option value=''>-- Select Role --</option>"; 
	        	for (var i=0; i<json.length; i++)
				{
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.displayName+"</option>";
				}
	        	$("#role").empty();
		        $("#role").append(output);
		        $('#role').trigger("chosen:updated");
		        $("#loading").removeAttr("style"); 
	        },
	    });
	}else{
		var output = "";
    	 output = output + "<option value=''>-- Select Role --</option>"; 
    	 $("#role").empty();
	     $("#role").append(output);
	     $('#role').trigger("chosen:updated");
	}
	 
}
</script>


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
	function saveClient() {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#client_form').validate(false, validationSettings)) {
			var orgLocationId = document.getElementById("orgLocationId").value;
			var frm = $('#client_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			  //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 		$.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/saveClient?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&orgLocationId="+orgLocationId+"&responderType=Client",
						success : function(result) {
							if (result == 'clientDashboard') {
								location.replace(sourceleadUrl
										+ "/clientDashboard");
							}
						},
					}); 
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#client_form', function() {
		$("#client_form").showHelpOnFocus();
		$("#client_form").validateOnBlur(false, validationSettings);
	});
</script>



<script type="text/javascript">
function validateEmail(){
	var email = document.getElementById("email").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(email != null){
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/validateClientOrVendorEmail?${_csrf.parameterName}=${_csrf.token}&email="+email,
			success : function(data) {
				var result = data;
				if(result == 'Invalid Email') {
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					$("input#email").after( "<p class='jquery_form_error_message'>" + email + " should not your login email</span>");
					document.getElementById("email").setAttribute( 'record-exist', 'yes');
					document.getElementById("email").setAttribute( 'record-exist-errorMsg', email +"  should not your login email");
					
				}else if(result == 'Valid Email') {
					$('input#email').removeAttr("class record-exist record-exist-errormsg");
					$('input#email').attr('class','valid form-control');
					$('#EmailIdDiv').find('p.jquery_form_error_message').remove();
					
				}
			},

		});
	}
}
</script>

 
<script type="text/javascript">
function getSpocUserDetails(){
	var email = document.getElementById("email").value;
	var sourceleadUrl = '${sourceleadUrl}';
	var orgLocationId = document.getElementById("orgLocationId").value;
	if(email){
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/getSpocUserDetails?${_csrf.parameterName}=${_csrf.token}&email="+email+"&orgLocationId="+orgLocationId,
	        contentType: "application/json; charset=utf-8",
	        dataType: "json",
	        success: function(data) {
	        	if(data != null && data != ''){
	        		var json = JSON.parse(data);
	        		
	        		 if(json[0] != null && json[0] != ''){
	        			$("#firstName").attr("disabled", true);
	        		    $("#firstName").val(json[0]);
	        		 }else{
	        			 $("#firstName").attr("disabled", false);
	        			 $("#firstName").val('');
	        		 }
	        		 
	        		 if(json[1] != null && json[1] != ''){
		        			$("#lastName").attr("disabled", true);
		        		    $("#lastName").val(json[1]);
	        		 }else{
	        			    $("#lastName").attr("disabled", false);
	        			    $("#lastName").val('');
	        		 }
		        		
	        		 if(json[2] != null && json[2] != ''){
		        			$("#phoneNumber").attr("disabled", true);
		        		    $("#phoneNumber").val(json[2]);
	        		 }else{
	        			    $("#phoneNumber").attr("disabled", false);
	        			    $("#phoneNumber").val('');
	        		 }
	        		 
	        		 
	        		 if(json[3] != null && json[3] != ''){
		        			$("#role").removeAttr("data-validation");
		        			$("#role" ).val('');
	        			    document.getElementById("roleDiv").style.display="none";
	   	        			document.getElementById("userRoleDiv").style.display="";
	   	        			$("#userRole").val(json[3]);
	   	        			$("#userRoleText").val(json[4]);
		        		 }else{
		        			 $("#role").attr("data-validation", "required");
		        			 $("#userRole" ).val('');
		        			 document.getElementById("roleDiv").style.display="";
		        			 document.getElementById("userRoleDiv").style.display="none";
		        		 }
	        	}
			 },
	        error: function(e){
	        }
	    });
	}else{
		$("#firstName").attr("disabled", false);
	    $("#firstName").val('');
	    
	    $("#lastName").attr("disabled", false);
	    $("#lastName").val('');
	    
	    $("#phoneNumber").attr("disabled", false);
	    $("#phoneNumber").val('');
	    
	    $("#role").attr("data-validation", "required");
	    $("#userRole" ).val('');
	    document.getElementById("roleDiv").style.display="";
		document.getElementById("userRoleDiv").style.display="none";
	   
	}
	
}
</script>
 
   <script type="text/javascript">
function validateClientName(){
	var clientName = document.getElementById("orgName").value;
	var sourceleadUrl = '${sourceleadUrl}';
	var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
	if(clientName.match(/^[^\s-](.*)*$/) != null){
		$('#clientNameDiv').find('p.jquery_form_error_message').remove();
	if (clientName != null && clientName != '') {
		//$("#save_client").attr("disabled", "disabled");
		// $( "#save_client" ).addClass('button').val('Processing..');
		$.ajax({
			type : "GET",
			url : sourceleadUrl + "/ws/isClientNameValid?${_csrf.parameterName}=${_csrf.token}&clientName="+clientName+"&responderType=Client",
			success : function(data) {
				var result = data;
				if (result == true) {
					//duplicate client
					$("input#orgName").after( "<p class='jquery_form_error_message'>" + clientName + " is already existed as client</span>");
					document.getElementById("orgName").setAttribute( 'record-exist', 'yes');
					document.getElementById("orgName").setAttribute( 'record-exist-errorMsg', clientName +"  already existed as client");
					$('input#orgName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else {
					//New WebSite class="form-control"
					//$("#save_client").removeAttr("disabled");
					$('input#orgName').removeAttr("class record-exist record-exist-errormsg");
					$('input#orgName').attr('class','valid form-control');
					$('#clientNameDiv').find('p.jquery_form_error_message').remove();
					$("#save_client").click(function() {
						
					});
				}
			},

		});
	}
  }
}
</script>   


<!-- <script type="text/javascript">
 function validateClientEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		/*$("#save_client").attr("disabled", "disabled");
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
 -->
 
 
 <script type="text/javascript">
	function validateWebsite() {
		var website = document.getElementById("webSite").value;
		var sourceleadUrl = '${sourceleadUrl}';
		if(website.match(/^[^\s-](.*)*$/) !== null){
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
						$("input#webSite").after("<p class='jquery_form_error_message'>"+ website+ " is "+result+"</span>");
						document.getElementById("webSite").setAttribute('record-exist', 'yes');
						document.getElementById("webSite").setAttribute('record-exist-errorMsg',website + ' is '+result);
						$('input#webSite').removeAttr( "record-exist record-exist-errormsg");
						
					} else {
						//New WebSite class="form-control"
						$('input#webSite').removeAttr("class record-exist record-exist-errormsg");
						$('input#webSite').attr('class','valid form-control');
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
				
						$("#save_client").click(function() {
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

<script type="text/javascript">
function getAllCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/sourceLeadCountryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---select---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${loginCountryName}'){
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
} 
</script>


<script>
function getStates(){
	var countryName = $("#orgCountry").val();
	if(countryName == null || countryName == 'undefined' || countryName == ''){
		countryName = $("#country").val();
	}
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
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').trigger("chosen:updated");
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
	
	var countryName = $("#orgCountry").val();
	if(countryName == null || countryName == 'undefined' || countryName == ''){
		countryName = $("#country").val();
	}
	
	var stateName = $("#orgState").val();
	if(stateName == null || stateName == 'undefined' || stateName == ''){
		stateName = $("#state").val();
	}
	
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
	            	$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
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
<script type="text/javascript">
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
	       $("#owner").chosen();
	       
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
</script>
