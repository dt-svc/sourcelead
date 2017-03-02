<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Account Settings</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/professionalDashboard'/>">Home</a> &raquo; Account Settings</div>
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
			<div id="profile" class="tab-pane fade in active">
		<form:form class="login-form clearfix"  id="profileSettings_form"  name="profileSettings_form" method="post" >
			  
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
					<label class="col-md-4">A/C Holder Name</label>
					<div class="col-md-8">
					  <input type="text" name="accountHolderName" value="${SourceLeadWeb_user.firstName}" field-name="First Name" class="form-control" data-validation="required validate_Alphanumeric validate_Space validate_length length2-100"  >
					</div>
					</div>
				   <div class="form-group">
					<label class="col-md-4">Country</label>
					<div class="col-md-8">
					  <select class="form-control select-bg" name="country" id="country" field-name="Country" onchange="getCurrency();" data-validation="required" value="${country}" >
		                <option value="">Select Location</option>
		                <option value="India">India</option>
		                <option value="United States">United States</option>
		              </select>
					</div>
				   </div>
				  <div class="form-group">
					<label class="col-md-4">Currency</label>
					<div class="col-md-8">
					<input type="text" readonly="readonly"  id="currency" name="currency" class="form-control">
					</div>
				  </div>
		
				</div>
		
				<div class="col-md-6">
						  <div class="form-group">
							<label class="col-md-4">Bank Name</label>
							<div class="col-md-8">
							  <input type="text" name="bankName" field-name="Designation" value="${SourceLeadWeb_designation}" class="form-control" data-validation-optional="true" data-validation="validate_length length2-100" />
							</div>
						  </div>
						  <div class="form-group">
							<label class="col-md-4">Account Number</label>
							<div class="col-md-8">
							  <input type="text" name="accountNumber" field-name="Mobile Phone" value="${SourceLeadWeb_user.mobilePhone}" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_length length9-20 validate_phone" />
							</div>
						  </div>
						  <div class="form-group">
							<label class="col-md-4" id="code">Code</label>
							<div class="col-md-8">
							   <input type="text" name="bankCode" id="bankCode" field-name="Bank Code" value="${SourceLeadWeb_user.mobilePhone}" class="form-control" data-validation-optional="true" data-validation="validate_Space validate_length length9-20 validate_phone" />
							</div>
						</div>
					</div>
			
				
					 <div class="col-md-12">
						 <hr>
							<div class="pull-right">
							  <input type="submit" class="btn btn-primary mr" id="profile_submit" value="Save">
							  <input type="button" class="btn btn-default" value="Cancel" onClick="self.location='/sourcelead/dashboard'" >
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

<script type="text/javascript">
function getCurrency(){
	var country = document.getElementById("country").value;
	 if(country == "India"){
		$('#currency').val('INR');
		$('#code').html("IFSC Code");
	}else if(country == "United States"){
		$('#currency').val('USD');
		$('#code').html("Routing Number");
	}else{
		$('#currency').val('');
		$('#code').html("Code");
	} 
	 
	 $('#bankCode').val('');
	 
}
</script>



