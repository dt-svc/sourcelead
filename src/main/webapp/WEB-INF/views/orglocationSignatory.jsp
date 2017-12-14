
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="orglocationSignatory" class="tab-pane fade form-horizontal">
  <div class="accountsettings-tab">
	<div class="tab-content panel-body">
		
		<form:form class="login-form clearfix"  id="signatory_form"  name="signatory_form" method="post" > 
			  	 <div class="comLogContent">
			  	 <div id="loading"></div> 
			  	 <div class="side_mask"></div>
			  	 <div id="popUp"></div> 
				     <div class="compSignatoryHelpMes">
						Person who can sign legal binding documents on behalf of your organisation.
					</div>
				    
				      <c:if test="${!empty signatory}">
				      <div class="clearfix"> 
					      <h4>Legal Signer Details</h4>
					      <div class="col-md-6">
						      <div class="form-group clearfix">
						         <c:if test="${!empty signatory.userId.lastName}">
						         <label class="col-md-3 noPad">User Name : </label> ${signatory.userId.firstName} ${signatory.userId.lastName}
						         </c:if>
						         
						         <c:if test="${empty signatory.userId.lastName}">
						         <label class="col-md-3 noPad">User Name : </label> ${signatory.userId.firstName} 
						         </c:if>
						      </div>
					      </div>
					      
					      <div class="col-md-6">
						      <div class="form-group clearfix">
						         <label class="col-md-3 noPad">Email : </label> ${signatory.userId.email}
						      </div>
					      </div>
					      
					      <div class="col-md-6">
						      <div class="form-group clearfix">
						         <label class="col-md-3 noPad">Role : </label> ${role}
						      </div>
					      </div>
					      
					      <c:if test="${!empty signatory.userId.mobilePhone}">
					      <div class="col-md-6">
						      <div class="form-group clearfix">
						         <label class="col-md-3 noPad">Phone Number : </label> ${signatory.userId.mobilePhone}
						      </div>
					      </div>
					      </c:if>
					      
					      <c:if test="${!empty signatory.userId.ssn}">
					      <div class="col-md-6">
						      <div class="form-group clearfix">
						         <label class="col-md-3 noPad">SSN : </label> ${signatory.userId.ssn}
						      </div>
					      </div>
					      </c:if>
					      
					      <c:if test="${!empty signatory.designationOtherType}">
					      <div class="col-md-6">
						      <div class="form-group clearfix">
						         <label class="col-md-3 noPad">Designation : </label> ${signatory.designationOtherType}
						      </div>
					      </div>
					      </c:if>
					      
					      <c:if test="${!empty signatory.designation && empty signatory.designationOtherType}">
					      <div class="col-md-6">
						      <div class="form-group clearfix">
						         <label class="col-md-3 noPad">Designation : </label> ${signatory.designation}
						      </div>
					      </div>
					      </c:if>
					      
				      </div>
				       <div class="clearfix mt10"></div> 
				     </c:if> 
				      
				      <div class="clearfix"> 
					     		<div class="form-group clearfix">
									<div class="col-md-8 padding_right">
				                <input id="legalSigner" name="legalSigner"  class="checkbox-custom"  value="Y" onclick="validateLegalSigner();"  type="checkbox">
		              			<label for="legalSigner" class="checkbox-custom-label">&nbsp;Make me as legal signer</label>
								</div>
					     </div> 
				     </div>
				     
					     <div id="legalUserFormDiv"></div>
						     
						<div class="clearfix">
							<div class="pull-right">
							<input type="submit" value="Save" id="save_signatory_button" class="btn btn-primary mr">
							</div>
						</div>
				     </div>
				     
				  
	  </form:form>
		
	</div>
  </div>
</div>


<script type="text/javascript">
 function getUserByUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 var sourceleadUrl = '${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByUserEmail?&EmailId="+EmailId, 
		        success: function(json) {
		        		
		        		if(json.firstName != null && json.firstName != ''){
		        			$("#firstName").val(json.firstName);
				        	$("#firstName").attr("readonly", "readonly");
				        	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
		        		}else{
		        			 $("#firstName").removeAttr("readonly", "readonly");
		        		}
		        		
		        		if(json.lastName != null && json.lastName != ''){
		        			$("#lastName").attr("readonly", "readonly");
		        			$("#lastName").val(json.lastName);
		        		}else{
		        			 $("#lastName").removeAttr("readonly", "readonly");
		        		}
		        		
		        		if(json.mobilePhone != null && json.mobilePhone != ''){
		        			$("#phone").val(json.mobilePhone);
				        	$("#phone").attr("readonly", "readonly");
		        		}else{
		        			 $("#phone").removeAttr("readonly", "readonly");
		        		}
		        		
		        },
		    });
	 }else{
		 $("#firstName").attr("disabled", false);
		 $("#firstName").val('');
		 $("#firstName").removeAttr("readonly", "readonly");
		 
		 $("#lastName").attr("disabled", false);
		 $("#lastName").val('');
		 $("#lastName").removeAttr("readonly", "readonly");
		 
		 $("#phone").attr("disabled", false);
		 $("#phone").val('');
		 $("#phone").removeAttr("readonly", "readonly");
	 }
 }
 </script>  
 
 
 <script type="text/javascript">
 function validateUserEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl = '${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $("#save_signatory_button").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateUserEmail?&EmailId="+EmailId, 
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
                         $("#save_signatory_button").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>  
 
  
  <script>
  function validateLegalSigner(){
	  
	  
	  if('${signatoryUser}' == '${SourceLeadWeb_user.id}'){
		  $("#legalSigner").attr("checked", "checked");

	  } 
	  
	  if (document.getElementById('legalSigner').checked) {
		  $("#legalUserFormDiv").empty();
	  }else{
		  loadLeagelSignerForm();
	  }
  }
  </script>
 
 <script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>

<script>
function loadLeagelSignerForm(){
	$("#legalUserFormDiv").empty();
	
	var result = "";
	result = result +"<div class='clearfix row'>";
	result = result +"<h4 class='col-md-8 padding_left'>Create New Signer</h4>";
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix' id='firstNameDiv'>";
	result = result +"<label class='col-md-4'>First Name<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='firstName' id='firstName' field-name='First Name'  pattern='[A-Za-z\s]*' maxlength='100' data-validation='required validate_Space validate_AlphaNumber validate_length length1-100'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4'>Last Name</label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='lastName' id='lastName' field-name='Last Name' data-validation-optional='true' pattern='[A-Za-z\s]*' maxlength='100' data-validation='validate_Space validate_AlphaNumber validate_length length1-100'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	result = result +"<div class='col-md-6' id='EmailIdDiv'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4'>Email<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='email' id='EmailId'  field-name='Email' data-validation='required validate_Space validate_length length2-100 validate_email'  onblur='getUserByUserEmail(); validateUserEmail();' onKeyUp='validateUserEmail();'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4'>Phone Number</label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='phoneNumber' id='phone'  field-name='Phone Number' data-validation='validate_Space validate_phone validate_length length9-20' data-validation-optional='true'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4'>SSN<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='ssn' id='ssn' field-name='SSN'  maxlength='9' data-validation='required validate_Space validate_int validate_length length9-9'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4'>Role<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<select class='chosen-select form-control' id='role' name='roleType' field-name='Role' data-validation='required'>";
	result = result +"<option value=''>--Select Role--</option>";
		<c:forEach var="option" items="${rolesList}">
		 result = result +'<option value="${option.id}">${option.displayName}</option>';
		</c:forEach>
	result = result +"</select>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	
	result = result +"<div class='col-md-6'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4'>Designation</label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<select class='chosen-select form-control' id='designation' name='designationType' field-name='Designation' data-validation-optional='true' onchange='getDesignationType()'>";
	result = result +"<option value=''>--Select Designation--</option>";
	result = result +'<option value="CEO">CEO</option>';
	result = result +'<option value="CFO">CFO</option>';
	result = result +'<option value="COO">COO</option>';
	result = result +'<option value="President">President</option>';
	result = result +'<option value="Other">Other</option>';
	result = result +"</select>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	
	result = result +"<div class='col-md-6' id='designationOtherDiv'  style='display:none'>";
	result = result +"<div class='form-group clearfix'>";
	result = result +"<label class='col-md-4'>Designation Other Type<span class='text-danger'>&nbsp;*</span></label>";
	result = result +"<div class='col-md-8 padding_right'>";
	result = result +"<input class='form-control' type='text' name='designationOtherType' id='designationOtherType' field-name='Designation Other Type' data-validation-optional='true'>";
	result = result +"</div>";
	result = result +"</div>";
	result = result +"</div>";
	
	
	result = result +"</div>";
	
	$("#legalUserFormDiv").append(result);
	
	
}
</script>


<script>
function getDesignationType(){
	var designation = document.getElementById("designation").value;
	if(designation == 'Other'){
		 document.getElementById("designationOtherDiv").style.display="";
		 $("#designationOtherType").removeAttr("data-validation-optional");
		 $("#designationOtherType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
	}else{
		 document.getElementById("designationOtherDiv").style.display="none";
		$("#designationOtherType").removeAttr("data-validation-optional");
		 $("#designationOtherType").attr("data-validation-optional","true");
		 $("#designationOtherType").removeAttr("data-validation");
		 $("#designationOtherType").attr("data-validation","required validate_Space validate_AlphaNumber validate_length length1-100");
	}
}
</script>


<script type="text/javascript">
$('body').on('submit', '#signatory_form', function(e){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	if ($('#signatory_form').validate(false, validationSettings)){
      e.preventDefault();
      var sourceleadUrl ='${sourceleadUrl}';
      var formData = getFormData("signatory_form");
      
      if (document.getElementById('legalSigner').checked) {
    	  saveOrgLocationSignatory();
      }else{
        var isMailSent = "Y";
   	 	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	       $.ajax({
		    	type: "POST",
		    	data : formData,
		    	  url: sourceleadUrl+"/createUser?${_csrf.parameterName}=${_csrf.token}&isMailSent="+isMailSent,		
		        success: function(result) {
		        	
		        	saveOrgLocationSignatory(formData);
		        },
		    });   
		    
        saveOrgLocationSignatory(formData);
      }
		    
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#signatory_form', function() {
		$("#signatory_form").showHelpOnFocus();
		$("#signatory_form").validateOnBlur(false, validationSettings);
	});
  </script>
  
  
  <script>
  function saveOrgLocationSignatory(formData){
		var sourceleadUrl = '${sourceleadUrl}';
		var uuid = '';
		if('${signatory.uuid}' != 'undefined'){
			uuid = '${signatory.uuid}'
		}
		
		   $.ajax({
		    	type: "POST",
		    	data : formData,
		    	 url: sourceleadUrl+"/saveOrgLocationSignatory?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid, 
		        success: function(data) {
		        	if(data == 'success'){
		        		location.replace(sourceleadUrl+"/orgSettings");
		        		$("#loading").removeAttr("style");
		        	}
		        },
		    });
  }
  </script>
