
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="bankingDetails" class="tab-pane fade form-horizontal">

<div class="accountsettings-tab">
	<div class="tab-content panel-body">
		<form:form class="login-form clearfix"  id="bankingDetails_form"  name="bankingDetails_form" method="post" > 	  
			  <div class="comLogContent">
			  <div id="loading"></div> 
				  <div class="clearfix"> 
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
				  
		  	<div class="clearfix">
				<div class="pull-right">
				    <input type="submit" value="Save" class="btn btn-primary mr" id="save_banking_button">
				</div>
			</div>
					
			  </div>
			  </form:form>
	</div>
</div>

</div>


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
	 	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	       $.ajax({
		    	type: "POST",
		    	data : formData,
		    	  url: sourceleadUrl+"/saveOrgLocationsBankingDetails?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid,		
		        success: function(result) {
		        	
		        	$("#loading").removeAttr("style");
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
		$("#bankingDetails_form").validateOnBlur(false, validationSettings);
	});
  </script>