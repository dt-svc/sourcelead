<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>

<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">


<link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="resources/css/bootstrap-select.css?staticTime=${staticTime}" rel="stylesheet">
<link href="resources/css/jquery.dataTables.min.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.css?staticTime=${staticTime}">
<link rel="stylesheet" href="resources/css/chosen.min.css?staticTime=${staticTime}">
<link href="resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet" id="myStyleSheet">
<link href="resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<script type="text/javascript" src='resources/js/slead-common.js?staticTime=${staticTime}'></script>

<div class="wraper"> 
	<section class="comLogBg">
			<section class="comLogInn">
				<div class="comLogHead">
				 <div class="comLogLogoText">${SourceLeadWeb_orgName}</div>
				  <div class="welSiO onboard-logoutbtn" onclick="logoutFormSubmit();" title="Sign Out"><i class="fa fa-sign-out mt5"></i></div>
				       <ul>
						  <li class="actLi">Organization Details<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Locations<i class="fa fa-check-circle"></i></li>
						  <li class="actLi">Federal & state taxes<i class="fa fa-check-circle"></i></li>
						  <li class="hoverLi">Banking details</li>
						  <li>Documents</li>
						  <li>Signatory</li>
						  <div class="clearfix"></div>
					  </ul>
				  <div class="clearfix"></div>
			  </div>
			  
			<form:form class="login-form clearfix"  id="bankingDetails_form"  name="bankingDetails_form" method="post" > 	  
			  <div class="comLogContent">
			  <div id="loading"></div> 
			  <h2>Organization Banking Details</h2>
				  <div class="row clearfix"> 
				  	<div class="col-md-6">
					  	<div class="form-group clearfix">
						<label class="col-md-4 noPad">Account type<span class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8 padding_right">
							<select class="chosen-select form-control" name="accountType" id="accountType" data-validation="required" field-name="Account Type">
								<option value="">--Select Account type--</option>
								<option ${SourceLeadWeb_bankingDetails.accountType  == 'Savings' ? 'selected' : ''} value="Savings">Savings</option>
								<option ${SourceLeadWeb_bankingDetails.accountType  == 'Checking' ? 'selected' : ''} value="Checking">Checking</option>
							  </select>
							</div>
						</div>
						
						<div class="form-group clearfix">
								<label class="col-md-4 noPad">Account Name<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-8 padding_right">
								 <input class=" form-control" type="text" name="accountName" id="accountName" value='${SourceLeadWeb_bankingDetails.accountName}' field-name="Account Name"  data-validation="required validate_space validate_length length1-250" >
								</div>
						</div>
						
						<div class="form-group clearfix">
								<label class="col-md-4 noPad">Routing Number<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-8 padding_right">
								 <input class=" form-control" type="text" name="routingNumber" id="routingNumber" field-name="Routing Number" value='${SourceLeadWeb_bankingDetails.routingNumber}' data-validation="required validate_space validate_length length9-9" maxlength="9" >
								</div>
						</div>
						
						<div class="form-group clearfix">
								<label class="col-md-4 noPad">Account Number<span class="text-danger">&nbsp;*</span></label>
								<div class="col-md-8 padding_right">
								 <input class=" form-control" type="text" name="accountNumber" id="accountNumber" field-name="Account Number" value='${SourceLeadWeb_bankingDetails.accountNumber}' data-validation="required validate_space validate_length length9-15"  >
								</div>
						</div>
						
				  	</div>
				  </div>
					<div class="welBtns welBtnsA">
						<input type="button" value="Prev" onClick="javascript:window.location.href = '<c:url value='/companyOnboardFederalAndStateTaxes'/>'"><input type="submit" value="Save & Continue" class="Brig" id="save_button">
						<input type="button" value="Skip this step" class="skipBtn" onClick="javascript:window.location.href = '<c:url value='/companyOnboardDocuments'/>'"></a>
						<div class="clearfix"></div>
					</div>
			  </div>
			  </form:form>
			</section>
	</section>
</div>



 <script type="text/javascript" src="resources/js/jquery-1.11.0.min.js?staticTime=${staticTime}"></script>
 <script type="text/javascript" src="resources/js/jquery.liquidcarousel.pack.js?staticTime=${staticTime}"></script>	
	
  <script src="resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/moment.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/jquery-ui.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/bootstrap-select.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
  <script src="resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script> 
 <script src="resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script> 
 <script src="resources/js/formvalidator.js"></script>
 
 
 
 
 <script type="text/javascript">
$('body').on('submit', '#bankingDetails_form', function(e){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	if ($('#bankingDetails_form').validate(false, validationSettings)){
      e.preventDefault();
      var sourceleadUrl ='${sourceleadUrl}';
      var formData = getFormData("bankingDetails_form");
      var uuid = '';
      if('${uuid}' != null && '${uuid}' != '' && '${uuid}' != 'undefined'){
    	   uuid = '${uuid}';
      }
     alert("hi");
	 	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	       $.ajax({
		    	type: "POST",
		    	data : formData,
		    	  url: sourceleadUrl+"/saveOrgLocationsBankingDetails?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,		
		        success: function(result) {
		        	
		        	if(result == 'success'){
		        		location.replace(sourceleadUrl+"/companyOnboardDocuments"); 
		        	}else{
		        		$("#loading").removeAttr("style"); 
		        	}
		        },
		    });    
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#bankingDetails_form', function() {
		$("#bankingDetails_form").showHelpOnFocus();
		$("#fedralAndTax_form").validateOnBlur(false, validationSettings);
	});
  </script>

<script>
		function logoutFormSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>


<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" id="logoutForm">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>