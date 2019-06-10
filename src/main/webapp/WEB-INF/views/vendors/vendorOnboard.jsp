<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Onboarding</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Vendor On-board
		</div>
	</section>
	
	<div id="loading"></div>
	<div class="newPrDetails newPrDetailsPMO"> 
		<div class="newPrTabs">
		<div id="popup"></div>
		<ul class="newPrTabsMenu">
				<li class="current" onclick="getVendorOnboardDetails();"><a href="#tab-a" data-toggle="tab">Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="vendorDocuments" onclick="getOnboardDocuments();">Documents</a></li>
				<li><a href="#tab-c" data-toggle="tab" id="vendorTasks" onclick="getVendorOnboardTasksList();">Tasks</a></li>
				<div class="clearfix mb"></div>
			  </ul>
			  <div class="clearfix"></div>
			  
		  <div class="project_tabs">
			 <div class="clearfix form-area form-horizontal toggle-div remove-div  newPrTabs_inn mt" id="tab-a">
			 	
			 		<div class="clearfix form-horizontal inner-content req-dtls myForm">
						<div class="row">
							<div class="col-lg-12">
					          <h3>Client Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
								
								<div class="col-xs-12 col-sm-9 col-md-9">
									<div class="form-group">
											<label class="col-md-4">Client Name :</label>
											<div class="pull-left" id="clientName"></div>
									</div>
									
									<div class="form-group">
										<label class="col-md-4">Website :</label>
										<div class="pull-left"  id="website"></div>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Location :</label>
										<div class="pull-left" id="location"></div>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Address :</label>
										<div class="pull-left" id="address"></div>
								    </div>
								
								</div>
							</div>
						</div>
					</div>
					
					
					<div class="clearfix form-horizontal inner-content req-dtls myForm">
						<div class="row">
							<div class="col-lg-12">
					          <h3>Client Contact Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
								
								<div class="col-xs-12 col-sm-9 col-md-9">
									<div class="form-group">
											<label class="col-md-4">Contact Name :</label>
											<div class="pull-left" id="clientContactName"></div>
									</div>
									
									<div class="form-group">
										<label class="col-md-4">Email :</label>
										<div class="pull-left" id="clientContactEmail"></div>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Phone :</label>
										<div class="pull-left" id="clientContactPhone"></div>
								    </div>
								
								</div>
							</div>
						</div>
					</div>
					
					
					<div class="clearfix form-horizontal inner-content req-dtls myForm">
						<div class="row">
							<div class="col-lg-12">
					          <h3>Company Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
								
								<div class="col-xs-12 col-sm-9 col-md-9">
									<div class="form-group">
											<label class="col-md-4">Vendor Name :</label>
											<div class="pull-left" id="vendorName"></div>
									</div>
									
									<div class="form-group">
										<label class="col-md-4">Website :</label>
										<div class="pull-left" id="vendorWebsite"></div>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Location :</label>
										<div class="pull-left" id="vendorLocation"></div>
								    </div>
								    
								    <!-- <div class="form-group">
										<label class="col-md-4">Address :</label>
										<p class="tbldata-dotdot-me" id="vendorAddress"></p>
								    </div> -->
								
								</div>
							</div>
						</div>
					</div>
					
					
			 	
			 	
			 	 <form:form id="vendor_onboard_form" name="vendor_onboard_form" method="post">
			 		<div class="clearfix form-horizontal inner-content req-dtls myForm">
						<div class="row">
							<div class="col-lg-12">
					          <h3>Additional Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
									<div class="form-group">
										<label class="col-md-3">Nature Of Business</label>
										<div class="col-md-9">
										<select  class="chosen-select form-control" name="industryName" id="industryName" field-name="Industry" data-validation-optional="true">
										<option value="">--Select Industry--</option>
										 <c:forEach var="industry" items="${industryList}">
							                     <option value="${industry.name}" ${industry.name  == 'Recruitment / Staffing' ? 'selected' : ''}>${industry.name}</option>Recruitment / Staffing
						                </c:forEach>  
						                 </select>
										</div>
								    </div>
								
									<div class="form-group">
										<label class="col-md-3">Payment Term</label>
										<div class="col-md-9 dropdown-select">
											<select class="form-control" id="paymentTermId"
												name="paymentTerm">
												<option selected="selected" disabled value="">Select
													Payment Term</option>
												<option value="10 days">10 days</option>
												<option value="15 days">15 days</option>
												<option value="30 days">30 days</option>
												<option value="45 days">45 days</option>
												<option value="60 days">60 days</option>
												<option value="90 days">90 days</option>
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3">Tier</label>
										<div class="col-md-9 dropdown-select">
											<select class="form-control" id="tier" name="tier" data-validation="required" field-name="Tier">
												<option value="Tier-1">Tier-1</option>
												<option value="Tier-2">Tier-2</option>
												<option value="Tier-3"  selected="selected">Tier-3</option>
												<option value="Tier-4">Tier-4</option>
												<option value="Tier-5">Tier-5</option>
											</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-md-3">Currency</label>
										<div class="col-md-9 dropdown-select">
											<select class="form-control" id="currency" name="currency">
											    <option value="">--Select Currency--</option>
												<option value="INR" ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}>INR</option>
												<option value="AUD" ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}>AUD</option>
												<option value="USD" ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}>USD</option>
												<option value="GBP" ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}>GBP</option>
												<option value="SGD" ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}>SGD</option>
												<option value="EUR" ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}>EUR</option>
											</select>
										</div>
									</div>
									
								  <div class="form-group">
							         <label class="col-md-3">SIC Code(US Only)</label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="sicCode" name="sicCode" field-name="SIC Code"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
							         <label class="col-md-3">NAICS Code</label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="naicsCode" name="naicsCode" field-name="NAICS Code"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-50"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
							         <label class="col-md-3">EIN Code</label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="einCode" name="einCode" field-name="EIN Code"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-50"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
							         <label class="col-md-3">Tax Reg Number</label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="taxRegNumber" name="taxRegNumber" field-name="Tax Reg Number"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
										<label class="col-md-3">Address</label>
										<div class="col-md-9">
											<textarea id="vendorAddress" name="vendorAddress" rows="4"
												field-name="Address" class="form-control"
												data-validation="validate_length length2-250"
												data-validation-optional="true"></textarea>
										</div>
									</div>
			
									<div class="form-group">
										<label class="col-md-3">Billing Address</label>
										<div class="col-md-9">
											<textarea id="vendorBillingAddress" rows="4" name="vendorBillingAddress"
												field-name="Billing Address" class="form-control"
												data-validation="validate_length length2-250"
												data-validation-optional="true"></textarea>
										</div>
									</div>
									
									<div class="clearfix"></div>
									<div class="form-group">
							         <label class="col-md-3 mt">DUNS Number</label>
							        <div class="col-md-9 validLabel mt">
							             <input type="text" id="dunsNumber" name="dunsNumber" field-name="DUNS Number"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
							</div>
						</div>
						
						
						
						
						<div class="row clearfix mt">
							<div class="col-lg-12">
					          <h3>Bank Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
									
							       <div class="form-group">
							         <label class="col-md-3">Account Name<span class="text-danger">&nbsp;*</span></label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="accountName" name="accountName" field-name="Account Name"   data-validation="required validate_Space validate_length length1-500"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
							         <label class="col-md-3">Account Number<span class="text-danger">&nbsp;*</span></label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="accountNumber" name="accountNumber" field-name="Account Number"   data-validation="required validate_Space validate_int validate_length length1-50"  value="" class="form-control" >
							         </div>
							       </div>
							       
						           <div class="form-group">
						         	<label class="col-md-3">Bank Name<span class="text-danger">&nbsp;*</span></label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="bankName" name="bankName" field-name="Bank Name"   data-validation="required validate_Space validate_length length1-500"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
							         <label class="col-md-3">Branch Number(Routing Code)<span class="text-danger">&nbsp;*</span></label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="branchNumber" name="branchNumber" field-name="Branch Number"   data-validation="required validate_Space validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
							         <label class="col-md-3">SWIFT Bank Identifier Code (BIC)</label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="bicNumber" name="bicNumber" field-name="BIC Code"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       
									
									<div class="form-group">
							         <label class="col-md-3">Email Address For PO</label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="poEmail" name="poEmail" field-name="PO Email" data-validation-optional="true"  data-validation="validate_Space validate_email validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       
							       <div class="form-group">
							         <label class="col-md-3">Email Address For Payment Remittance<span class="text-danger">&nbsp;*</span></label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="paymentRemittanceEmail" name="paymentRemittanceEmail" field-name="Payment Remittance Email"   data-validation="required validate_Space validate_email validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
										<label class="col-md-3">Building No/Name & Street</label>
										<div class="col-md-9">
											<textarea id="bankAddress" name="bankAddress" rows="3"
												field-name="Address" class="form-control"
												data-validation="validate_Space"
												data-validation-optional="true"></textarea>
										</div>
									</div>
									
									
							</div>
						</div>
					</div>
				   <div class="clearfix mt"></div>
				 	<div class="form-footer clearfix">
						<div class="col-lg-12">
							<div class="pull-right">
								<input type="button" value="Save & Continue" id="save_vendor"
									onclick="saveVendorOnboard();" class="btn btn-primary">
							</div>
						</div>
					</div>
			 	</form:form> 
			 </div>
			  <jsp:include page="onboardVendorDocuments.jsp" />
	   		  <jsp:include page="onboardVendorTasks.jsp" />
		 </div>
		</div>
	</div>
</section>	




<script>
	$(document).ready(function() {
		var vendorDocuments = document.getElementById("vendorDocuments");
		if(vendorDocuments){
			vendorDocuments.href = "#";
			vendorDocuments.className = "disabledLink";
		}
		
		var vendorTasks = document.getElementById("vendorTasks");
		if(vendorTasks){
			vendorTasks.href = "#";
			vendorTasks.className = "disabledLink";
		}
		getVendorOnboardDetails();
		
		$("#tab-a").show()
	    $(".newPrTabsMenu a").click(function(event) {
	        event.preventDefault();
	        $(this).parent().addClass("current");
	        $(this).parent().siblings().removeClass("current");
	        var tab = $(this).attr("href");
	        $(".newPrTabs_inn").not(tab).css("display", "none");
	        $(tab).fadeIn();
	    });
		
	});
</script>

<script>
function getVendorOnboardDetails(){
	var onboardSubmittedStatus = '${onboardSubmittedStatus}';
	var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	if(onboardSubmittedStatus == 'N'){
		var vendorOnboardId = '${vendorOnboardId}';
		var notificationUUID = '${notificationUUID}';
		 $.ajax({
		    	type: "POST",
		    	 url: vmsUrl+"/getVendorOnboardDetailsByVendorOnboardId?${_csrf.parameterName}=${_csrf.token}&vendorOnboardId="+vendorOnboardId+"&notificationUUID="+notificationUUID, 
		        success: function(json) {
		        	$("#clientName").html("<p title='"+json.clientName+"' class='tbldata-dotdot-me'>"+json.clientName+"</p>");
		        	if(json.clientWebsite != null && json.clientWebsite != 'undefined' && json.clientWebsite != ''){
		        		$("#website").html("<p title='"+json.clientWebsite+"'>"+json.clientWebsite+"</p>");
		        	}else{
		        		$("#website").html("-------");
		        	}
		        	
		        	if(json.clientLocation != null && json.clientLocation != 'undefined' && json.clientLocation != ''){
		        		$("#location").html("<p title='"+json.clientLocation+"' class='tbldata-dotdot-me'>"+json.clientLocation+"</p>");
		        	}else{
		        		$("#location").html("-------");
		        	}
		        
		        	
		        	if(json.clientAddress != null && json.clientAddress != 'undefined' && json.clientAddress != ''){
		        		$("#address").html("<p title='"+json.clientAddress+"' class='tbldata-dotdot-me'>"+json.clientAddress+"</p>");
		        	}else{
		        		$("#address").html("-------");
		        	}
		        	
		        	$("#vendorName").html("<p title='"+json.vendorName+"'>"+json.vendorName+"</p>");
		        	if(json.vendorWebsite != null){
		        		$("#vendorWebsite").html("<p title='"+json.vendorWebsite+"' class='tbldata-dotdot-me'>"+json.vendorWebsite+"</p>");
		        	}else{
		        		$("#vendorWebsite").html("-------");
		        	}
		        	
		        	if(json.vendorLocation != null && json.vendorLocation != "" && json.vendorLocation != "undefined"){
		        		$("#vendorLocation").html("<p title='"+json.vendorLocation+"' class='tbldata-dotdot-me'>"+json.vendorLocation+"</p>");
		        	}else{

		        		$("#vendorLocation").html("<p title='"+json.vendorLocation+"'>--</p>");

		        	}
		        	
		        	$("#clientContactName").html("<p title='"+json.clientContactName+"' class='tbldata-dotdot-me'>"+json.clientContactName+"</p>");
		        	$("#clientContactEmail").html("<p title='"+json.clientContactEmail+"' class='tbldata-dotdot-me'>"+json.clientContactEmail+"</p>");
		        	if(json.clientContactPhone != null){
		        		$("#clientContactPhone").html("<p title='"+json.clientContactPhone+"' class='tbldata-dotdot-me'>"+json.clientContactPhone+"</p>");
		        	}else{
		        		$("#clientContactPhone").html("-------");
		        	}
		        	
		        	if(json.paymentTerm != null && json.paymentTerm != 'undefined' && json.paymentTerm != ''){
		      			   $("select[name=paymentTerm]").val(json.paymentTerm);
		      		  }
		        	
		        	
		        	$("select[name=tier]").val(json.tier);
		        	
		        	if(json.industryName != null && json.industryName != 'undefined' && json.industryName != ''){
		        		$("select[name=industryName]").val(json.industryName);
			        	$("#industryName").trigger("chosen:updated");
		        	}
		        	
		        	if(json.sicCode != null && json.sicCode != 'undefined' && json.sicCode != ''){
		        		$("input[name=sicCode]").val(json.sicCode);
		        	}
		        	
		        	
		        	if(json.naicsCode != null && json.naicsCode != 'undefined' && json.naicsCode != ''){
		        		$("input[name=naicsCode]").val(json.naicsCode);
		        	}
		        	
		        	
		        	if(json.einCode != null && json.einCode != 'undefined' && json.einCode != ''){
		        		$("input[name=einCode]").val(json.einCode);
		        	}
		        	
		        	if(json.currency != null && json.currency != 'undefined' && json.currency != ''){
		        		 $("select[name=currency]").val(json.currency);
		        	}
		        	
		        	if(json.taxRegNumber != null && json.taxRegNumber != 'undefined' && json.taxRegNumber != ''){
		        		 $("input[name=taxRegNumber]").val(json.taxRegNumber);
		        	}
		        	
		        	if(json.dunsNumber != null && json.dunsNumber != 'undefined' && json.dunsNumber != ''){
		        		 $("input[name=dunsNumber]").val(json.dunsNumber);
		        	}
		        	
		        	$("input[name=accountName]").val(json.accountName);
		        	$("input[name=accountNumber]").val(json.accountNumber);
		        	$("input[name=bankName]").val(json.bankName);
		        	
		        	if(json.branchNumber != null && json.branchNumber != 'undefined' && json.branchNumber != ''){
		        		$("input[name=branchNumber]").val(json.branchNumber);
		        	}
		        	
		        	if(json.bicNumber != null && json.bicNumber != 'undefined' && json.bicNumber != ''){
		        		$("input[name=bicNumber]").val(json.bicNumber);
		        	}
		        	
		        	if(json.poEmail != null && json.poEmail != 'undefined' && json.poEmail != ''){
		        		$("input[name=poEmail]").val(json.poEmail);
		        	}
		        	
		        	if(json.paymentRemittanceEmail != null && json.paymentRemittanceEmail != 'undefined' && json.paymentRemittanceEmail != ''){
		        		$("input[name=paymentRemittanceEmail]").val(json.paymentRemittanceEmail);
		        	}
		        	
		        	if(json.bankAddress != null && json.bankAddress != 'undefined' && json.bankAddress != ''){
		        		 $("textarea[name=bankAddress]").val(json.bankAddress);
		        	}
		        	
		        	if(json.vendorAddress != null && json.vendorAddress != 'undefined' && json.vendorAddress != ''){
			        		 $("textarea[name=vendorAddress]").val(json.vendorAddress);
		        	}
		        	
		        	if(json.vendorBillingAddress != null && json.vendorBillingAddress != 'undefined' && json.vendorBillingAddress != ''){
		        		 $("textarea[name=vendorBillingAddress]").val(json.vendorBillingAddress);
		        	}
		        },
		    }); 
		 $("#loading").removeAttr("style");
		 
	}else{
		location.replace(sourceleadUrl+"/dashboard/${SourceLeadWeb_orgLocationId}");
	}

  }
</script>


<script>
function saveVendorOnboard(){
	
	if ($('#vendor_onboard_form').validate(false, validationSettings)) {
		var vendorOnboardId = '${vendorOnboardId}';
		var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	  	var formData = $($('#vendor_onboard_form')[0]).serialize();
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(vmsUrl+"/acceptAndSaveVendorOnboard?"+tokenParamName+"="+tokenValue+"&vendorOnboardId="+vendorOnboardId, formData, function(data) {
					 $("#loading").removeAttr("style");
					 var vendorDocuments = document.getElementById("vendorDocuments");
					 $("#vendorDocuments").removeClass("disabledLink");
					 vendorDocuments.href="#tab-b"; 
					 
					/*  vendorDocuments.addEventListener("click", function() {
						 getOnboardDocuments();
					}); */
					 
					 $("#vendorDocuments").click();
					 
				}); 
		return true;
	} else {
		return false;
	}
	
}
$('body').on('blur', '#vendor_onboard_form', function() {
	$("#vendor_onboard_form").showHelpOnFocus();
	$("#vendor_onboard_form").validateOnBlur(false, validationSettings);
});
</script>


<jsp:include page="../masterFooter.jsp" />