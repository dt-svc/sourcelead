<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Vendor On-board</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Vendor On-board
		</div>
	</section>
	
	<div id="loading"></div>
	<div class="newPrDetails newPrDetailsPMO"> 
		<div class="newPrTabs">
		<ul class="newPrTabsMenu">
				<li class="current" onclick="getVendorOnboardDetails();"><a href="#tab-a" data-toggle="tab">Vendor Details</a></li>
				<li><a href="#tab-b" data-toggle="tab" id="vendorDocuments">Documents</a></li>
				<li><a href="#tab-c" data-toggle="tab" id="vendorTasks">Tasks</a></li>
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
											<p class="tbldata-dotdot-me" id="clientName"></p>
									</div>
									
									<div class="form-group">
										<label class="col-md-4">Website :</label>
										<p class="tbldata-dotdot-me" id="website"></p>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Location :</label>
										<p class="tbldata-dotdot-me" id="location"></p>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Address :</label>
										<p class="tbldata-dotdot-me" id="address"></p>
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
											<p class="tbldata-dotdot-me" id="clientContactName"></p>
									</div>
									
									<div class="form-group">
										<label class="col-md-4">Email :</label>
										<p class="tbldata-dotdot-me" id="clientContactEmail"></p>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Phone :</label>
										<p class="tbldata-dotdot-me" id="clientContactPhone"></p>
								    </div>
								
								</div>
							</div>
						</div>
					</div>
					
					
					<div class="clearfix form-horizontal inner-content req-dtls myForm">
						<div class="row">
							<div class="col-lg-12">
					          <h3>Vendor Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
								
								<div class="col-xs-12 col-sm-9 col-md-9">
									<div class="form-group">
											<label class="col-md-4">Vendor Name :</label>
											<p class="tbldata-dotdot-me" id="vendorName"></p>
									</div>
									
									<div class="form-group">
										<label class="col-md-4">Website :</label>
										<p class="tbldata-dotdot-me" id="vendorWebsite"></p>
								    </div>
								    
								    <div class="form-group">
										<label class="col-md-4">Location :</label>
										<p class="tbldata-dotdot-me" id="vendorLocation"></p>
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
					          <h3>Company Details</h3>
					        </div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
									<div class="form-group">
										<label class="col-md-3">Nature Of Business</label>
										<div class="col-md-9">
										<select  class="chosen-select form-control" name="industryName" id="industryName" field-name="Industry" data-validation-optional="true">
										<option value="">--Select Industry--</option>
										 <c:forEach var="industry" items="${industryList}">
							                     <option value="${industry.name}">${industry.name}</option>
						                </c:forEach>  
						                 </select>
										</div>
								    </div>
								
									<div class="form-group">
										<label class="col-md-3">Payment Term</label>
										<div class="col-md-9">
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
										<div class="col-md-9">
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
										<div class="col-md-9">
											<select class="form-control" id="currency" name="currency">
											    <option value="">--Select Currency--</option>
												<option value="INR">INR</option>
												<option value="AUD">AUD</option>
												<option value="USD">USD</option>
												<option value="GBP">GBP</option>
												<option value="SGD">SGD</option>
												<option value="EUR">EUR</option>
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
												data-validation="validate_Space"
												data-validation-optional="true"></textarea>
										</div>
									</div>
			
									<div class="form-group">
										<label class="col-md-3">Billing Address</label>
										<div class="col-md-9">
											<textarea id="vendorBillingAddress" rows="4" name="vendorBillingAddress"
												field-name="Billing Address" class="form-control"
												data-validation="validate_Space"
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
							             <input type="text" id="accountNumber" name="accountNumber" field-name="Account Number"   data-validation="required validate_Space validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
						           <div class="form-group">
						         	<label class="col-md-3">Bank Name<span class="text-danger">&nbsp;*</span></label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="bankName" name="bankName" field-name="Bank Name"   data-validation="required validate_Space validate_length length1-500"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
							         <label class="col-md-3">Branch Number(Routing Code)</label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="branchNumber" name="branchNumber" field-name="Branch Number"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
							         <label class="col-md-3">SWIFT Bank Identifier Code (BIC)</label>
							        <div class="col-md-9 validLabel">
							             <input type="text" id="bicNumber" name="bicNumber" field-name="BIC Code"  data-validation-optional="true"  data-validation="validate_Space validate_length length1-100"  value="" class="form-control" >
							         </div>
							       </div>
							       
							       <div class="form-group">
										<label class="col-md-3">Building No/Name & Street</label>
										<div class="col-md-9">
											<textarea id="bankAddress" name="bankAddress" rows="4"
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
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	if(onboardSubmittedStatus == 'N'){
		var vendorOnboardId = '${vendorOnboardId}';
		var notificationUUID = '${notificationUUID}';
		 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getVendorOnboardDetailsByVendorOnboardId?${_csrf.parameterName}=${_csrf.token}&vendorOnboardId="+vendorOnboardId+"&notificationUUID="+notificationUUID, 
		        success: function(json) {
		        	$("#clientName").html(json.clientName);
		        	if(json.clientWebsite != null){
		        		$("#website").html(json.clientWebsite);
		        	}else{
		        		$("#website").html("-------");
		        	}
		        	
		        	$("#location").html(json.clientLocation);
		        	
		        	if(json.clientAddress != null){
		        		$("#address").html(json.clientAddress);
		        	}else{
		        		$("#address").html("-------");
		        	}
		        	
		        	$("#vendorName").html(json.vendorName);
		        	if(json.vendorWebsite != null){
		        		$("#vendorWebsite").html(json.vendorWebsite);
		        	}else{
		        		$("#vendorWebsite").html("-------");
		        	}
		        	
		        	$("#vendorLocation").html(json.vendorLocation);
		        	

		        	$("#clientContactName").html(json.clientContactName);
		        	$("#clientContactEmail").html(json.clientContactEmail);
		        	if(json.clientContactPhone != null){
		        		$("#clientContactPhone").html(json.clientContactPhone);
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
	  	var formData = $($('#vendor_onboard_form')[0]).serialize();
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(sourceleadUrl+"/acceptAndSaveVendorOnboard?"+tokenParamName+"="+tokenValue+"&vendorOnboardId="+vendorOnboardId, formData, function(data) {
					 $("#loading").removeAttr("style");
					 var vendorDocuments = document.getElementById("vendorDocuments");
					 $("#vendorDocuments").removeClass("disabledLink");
					 vendorDocuments.href="#tab-b"; 
					 
					 vendorDocuments.addEventListener("click", function() {
						 getOnboardDocuments();
					});
					 
					 vendorDocuments.click();
					 
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