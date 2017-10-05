<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
<link rel="stylesheet" href="resources/css/style.css?staticTime=${staticTime}" />

  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Account Settings</h2>
      </div>
      
     <c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
       <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Account Settings</div>
     </c:if>
     
      <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
      <div class="pull-right brud-crum"><a href="<c:url value='/professionalDashboard'/>">Home</a> &raquo; Account Settings</div>
      </c:if>
      
       <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
      <div class="pull-right brud-crum"><a href="<c:url value='/recruiterDashboard'/>">Home</a> &raquo; Account Settings</div>
      </c:if>
    </section>
    <div class="content-area clearfix">
      <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
        <div id="loading"></div> 
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab">
		  <ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#account">Account</a></li>
		  </ul>

		  <div class="tab-content panel-body">
		  <div id="loading"></div>
			<div id="profile" class="tab-pane fade in active">
		<form:form class="login-form clearfix"  id="bankAccount_form"  name="bankAccount_form" method="post" >
			  
			  <div class="col-xs-12 col-sm-3 col-md-3 text-center well">
				  <div class="password-icon mt mb text-center">
				    <i class="fa fa-lock" aria-hidden="true"></i>
				  </div>
			  </div>
			 <div id="popUp" ></div>  
			  
		   <div class="col-xs-12 col-sm-9 col-md-9">
			   <div class="row mt">
				<div class="col-md-6">
					<div class="form-group">
					<label class="col-md-4">A/C Holder Name<span
							class="text-danger">&nbsp;*</span></label>
					<div class="col-md-8">
					  <input type="text" name="accountHolderName" value="${bankAccounts.accountHolderName}" field-name="A/C Holder Name" class="form-control" data-validation="required validate_Alphanumeric validate_Space validate_length length2-100"  >
					</div>
					</div>
				   <div class="form-group">
					<label class="col-md-4">Country</label>
					<div class="col-md-8">
					  <select class="form-control select-bg" name="country" id="country" field-name="Country" onchange="getCurrency();" data-validation="required" value="${country}" >
		                <option value="">Select Location</option>
		                <c:if test="${empty bankAccounts.country}">
			                <option ${SourceLeadWeb_country == 'India' ? 'selected' : ''} >India</option>
			                <option ${SourceLeadWeb_country == 'United States' ? 'selected' : ''} >United States</option>
		                </c:if>
		                
	                 	<c:if test="${!empty bankAccounts.country}">
			                <option ${bankAccounts.country == 'India' ? 'selected' : ''} >India</option>
			                <option ${bankAccounts.country == 'United States' ? 'selected' : ''} >United States</option>
		                </c:if>
		              </select>
					</div>
				   </div>
				  <div class="form-group">
					<label class="col-md-4">Currency</label>
					<div class="col-md-8">
					<input type="text" readonly="readonly"  id="currency"  value="${bankAccounts.currency}" name="currency" field-name="Currency" class="form-control">
					</div>
				  </div>
		
				</div>
		
				<div class="col-md-6">
						  <div class="form-group">
							<label class="col-md-4">Bank Name<span
							class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8">
							  <input type="text" name="bankName" field-name="Bank Name" value="${bankAccounts.bankName}" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_Alphanumeric validate_length length2-100" />
							</div>
						  </div>
						  <div class="form-group">
							<label class="col-md-4">Account Number<span
							class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8">
							  <input type="text" name="accountNumber" field-name="Account Number" value="${bankAccounts.accountNumber}" class="form-control" data-validation="required validate_Space validate_int validate_length length2-100" />
							</div>
						  </div>
						  <div class="form-group">
							<label class="col-md-4" id="code">Code<span
							class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8">
							   <input type="text" name="bankCode" id="bankCode" field-name="Bank Code" value="${bankAccounts.bankCode}" class="form-control"  />
							</div>
						</div>
					</div>
			
				
					 <div class="col-md-12">
						 <hr>
							<div class="pull-right">
							  <input type="submit" class="btn btn-primary mr" id="profile_submit" value="Save">
							  
							  <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL'}">
							    <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='/sourcelead/professionalDashboard'" >
							  </c:if>
							  
							   <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
							    <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='/sourcelead/recruiterDashboard'" >
							   </c:if>
							  
							</div>
					  </div>
			 </div>
			</div>
	</form:form>	
			</div>
		   </div>
		</div>
		  </div>
		  </div>
		  </div>
		  </div>
  </section>
  
<jsp:include page="../masterFooter.jsp" />


<script>
$(document).ready(function() {
	getCurrency();
});
</script>

<script type="text/javascript">
function getCurrency(){
	var country = document.getElementById("country").value;
	 if(country == "India"){
		$('#currency').val('INR');
		$('#code').html("IFSC Code");
		$("#bankCode").removeAttr("data-validation");
		$("#bankCode").attr("data-validation", "required validate_Alphanumeric validate_length length11-20");
		
	}else if(country == "United States"){
		$('#currency').val('USD');
		$('#code').html("Routing Number");
		$("#bankCode").removeAttr("data-validation");
		$("#bankCode").attr("data-validation", "required validate_int validate_length length9-20");
	}else{
		$('#currency').val('');
		$('#code').html("Code");
		$('#bankCode').val('');
		$("#bankCode").removeAttr("data-validation");
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
         $('body').on('submit', '#bankAccount_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		
        		e.preventDefault();
         		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		var formData = getFormData1("bankAccount_form");
  
         		$.ajax({
          	      type: "POST",
          	      data: formData,
 		          processData: false,
 		          contentType:false, 
                	  url: sourceleadUrl+"/saveBankAccountDetails?"+tokenParamName+"="+tokenValue+"",
                  	success: function(result) {
                  		location.replace(sourceleadUrl+"/bankAccountDetails");
			        },  
               }); 
            	  return true;
            }
            else{
            	return false;	
               }
            });
            
         $('body').on('blur', '#bankAccount_form', function() {
     		$("#bankAccount_form").showHelpOnFocus();
     		$("#bankAccount_form").validateOnBlur(false, validationSettings);
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
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 

