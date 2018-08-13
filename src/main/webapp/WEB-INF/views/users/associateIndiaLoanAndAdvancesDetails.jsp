 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-4" class="tabsMenu_tabs_inn clearfix">
  <div id="loanAndAdvancesDetailsLoading"></div>
    <form:form id="associate_loanAndAdvance_form" name="associate_loanAndAdvance_form" method="post">
	 	  <input type="hidden" name="associateUserId" id="loanAndAdvAssociateUserId"/>
	      <input type="hidden" name="loanAndAdvId" id="loanAndAdvId"/>
	      <input type="hidden" id="loanAndAdvMaxAmount"/>
	      <input type="hidden" id="loanAndAdvMinAmount"/>
	      <input type="hidden" id="loanAndAdvInterstRate"/>
	      
	      <div class="clearfix row minHeightcol5">
	         <div class="clearfix col-md-10 noPad mb">
	         <div class="col-md-4">
                  <label>Type<span class="text-danger">&nbsp;*</span></label>
                  <div class="dropdown-select">
                    <select class="form-control" data-placeholder="Select Type" id="loanAndAdvType" onchange="getLoanAndAdvNames();" name="loanAndAdvType" data-validation="required" field-name="Type">
                      <option value="">-- Select Type --</option>
                      <option value="Loan">Loan</option>
                      <option value="Amount">Amount</option>
                    </select>
                  </div>
              </div>
              
               <div class="col-md-4">
                  <label>Name<span class="text-danger">&nbsp;*</span></label>
                  <div class="dropdown-select">
                    <select class="form-control chosen-select" name="loanAndAdvName" id="loanAndAdvName" onchange="getLoanAndAdvDetails();" field-name="Name" data-validation="required" field-name="Name">
                        <option value="">-- Select Name --</option>
                      </select>
                  </div>
              </div>
              
              <div class="col-md-4">
                  <label>Status<span class="text-danger">&nbsp;*</span></label>
                  <div class="dropdown-select">
                    <select class="form-control" data-placeholder="Select Type" id="loanAndAdvStatus" onchange="getLoanAndAdvStatus();getLoanAndAdvDetails();" name="loanAndAdvStatus" data-validation="required" field-name="Status">
                      <option value="">-- Select Status --</option>
                      <option value="Requested">Requested</option>
                      <option value="Outstanding">Outstanding</option>
                      <option value="Completed">Completed</option>
                    </select>
                  </div>
              </div>
              </div>
              
              
            <div class="clearfix">
	         
	         <div class="clearfix col-md-10 noPad mb">
	         
	           <div class="clearfix" id="statusTypeDiv" style="display: none;">
			         <div class="col-md-4 minHeight">
		                  <label>Amount<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="loanAmount" id="requestedAmount"  onblur="getDetuctAmountDetails();" field-name="Amount">
		              </div>
		              
		               <div class="col-md-4 minHeight">
		                  <label>Scheduler<span class="text-danger">&nbsp;*</span></label>
		                  <div class="dropdown-select">
		                    <select class="form-control" data-placeholder="Select Type" id="scheduler" onchange="getDetuctAmountDetails();" name="scheduler"  field-name="Scheduler">
		                      <option value="">-- Select Scheduler --</option>
		                      <option value="Every Pay">Every Pay</option>
		                      <option value="Every Quarter">Every Quarter</option>
		                      <option value="Half Yearly">Half-Yearly</option>
		                      <option value="Annually">Annually</option>
		                    </select>
		                  </div>
		              </div>
		              
		              <div class="col-md-4 minHeight" id="effectiveDateDiv">
		                  <label>Effective Date<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control calendar" name="effectiveDate"  id="effectiveDate" field-name="Effective Date"  readonly="readonly" >
		              </div>
		              
		               <div class="col-md-4 minHeight" id="issuedOnDiv" style="display: none;">
		                  <label>Issued On<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control calendar" name="issuedDate"  id="issuedDate" field-name="Issued On"  readonly="readonly" >
		               </div>
		               
		               <div class="col-md-4 minHeight" id="paidAmountDiv" style="display: none;">
		                  <label>Paid Amount<span class="text-danger">&nbsp;*</span></label>
		                  <input type="text" class="form-control" name="paidAmount" id="paidAmount" onblur="getDetuctAmountDetails();" field-name="Paid Amount">
		               </div>
		               
		               <div class="col-md-4 minHeight">
		                  <label>No Of Schedules<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="noOfSchedules" id="noOfSchedules" onblur="getDetuctAmountDetails();" field-name="No Of Schedules">
		              </div>
		               
		               <div class="col-md-4 minHeight" id="pendingAmountDiv" style="display: none;">
		                  <label>Pending Amount<span class="text-danger">&nbsp;*</span></label>
		                  <input type="text" class="form-control" name="pendingAmount" id="pendingAmount" field-name="Pending Amount">
		               </div>
		              
		               <div class="col-md-4 minHeight">
		                  <label>End Date<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control calendar" name="endDate"  id="endDate" field-name="End Date"  readonly="readonly" >
		              </div>
		              
		              <div class="col-md-4 minHeight">
		                  <label>Deduct Amount<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="deductAmount" id="deductAmount" readonly="readonly" field-name="Deduct Amount">
		              </div>
		              
		              <div class="col-md-4 minHeight">
		                  <label>Approver<span class="text-danger">&nbsp;*</span></label>
		                   <select class="form-control chosen-select" name="loanAndAdvApprover" id="loanAndAdvApprover"  field-name="Approver" field-name="Approver">
                        		<option value="">-- Select Approver --</option>
                   		   </select>
		              </div>
		              
		               <div class="col-md-8 minHeight">
		                  <label>Description</label>
		                  <textarea rows="4" class="form-control" id="description"
									name="description" field-name="Description"
									data-validation=" validate_length length2-5000"
									data-validation-optional="true"></textarea>
		              </div>
             
              </div>
        </div>      
              </div>
              
	      </div>
	      
	             <div class="clearfix"></div>
                 <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                <input type="button" value="Previous" onclick="shiftToInformationTab();" class="btn btn-default">
			     </div>
			     
	                 <div class="col-md-6">
	                   <div class="pull-right">
	                    <button type="button" class="btn btn-success btnSuccess" id="saveLoan&AdvButton" onclick="saveLoanAndAdvanceDetails();" >Save & Continue</button>
                        <input value="Cancel" class="btn btn-default ml10" type="button">
	                    </div>
	                 </div>
                </div>
	      
     </form:form>
  
</div>



<script>
 function saveLoanAndAdvanceDetails(){
	 var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
	 
	 if ($('#associate_loanAndAdvance_form').validate(false, validationSettings)){ 
	        var formData = getFormData("associate_loanAndAdvance_form");
	        $("#loanAndAdvancesDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	         $.ajax({
		    	type: "POST",
		    	data: formData,
		    	 url: sourceleadUrl+"/saveAssociateLoanAndAdvanceDetails?",
		        success: function(response) {
		        	$("#loanAndAdvancesDetailsLoading").removeAttr('style');
		        	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
      	    		    showError(response.responseMessage);
      	    		}
		        	else if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		 var esipf = response.object;
	                	 $("#pfesiId").val(esipf.id);
		        		
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });  
	        return true;
	  } else {
		return false;
	  }
 }
 
 $('body').on('blur', '#associate_loanAndAdvance_form', function() {
		$("#associate_loanAndAdvance_form").showHelpOnFocus();
		$("#associate_loanAndAdvance_form").validateOnBlur(false, validationSettings);  
	});
 </script>

<script>
function getLoanAndAdvNames(){
	 var loanAndAdvType = document.getElementById("loanAndAdvType").value; 
	 if(loanAndAdvType != null && loanAndAdvType != "" && loanAndAdvType != 'undefined'){
		 $("#loanAndAdvancesDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getNamesByLoanAndAdvanceType?loanAndAdvType="+loanAndAdvType, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Name --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.name+"</option>";
			        		
					}
		        	$("#loanAndAdvName").empty();
			        $("#loanAndAdvName").append(output);
			        $('#loanAndAdvName').trigger("chosen:updated");
			        $("#loanAndAdvancesDetailsLoading").removeAttr('style');
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Name --</option>"; 
     	 $("#loanAndAdvName").empty();
	     $("#loanAndAdvName").append(output);
	     $('#loanAndAdvName').trigger("chosen:updated");
	     $("#loanAndAdvancesDetailsLoading").removeAttr('style');
	 }
	 
	
}

</script>

<script>
function getLoanAndAdvDetails(){
	var loanAndAdvId = document.getElementById("loanAndAdvName").value;
	var loanAndAdvStatus = document.getElementById("loanAndAdvStatus").value;
	
	if(loanAndAdvId && loanAndAdvStatus){
		 $("#loanAndAdvancesDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');		
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getLoanAndAdvanceDetailsByLoanAndAdvanceId?loanAndAdvId="+loanAndAdvId, 
		        success: function(json) {
		        	
		        	if(json.minAmount != null && json.minAmount != '' && json.minAmount != 'undefined'){
		        		$("#loanAndAdvMinAmount").val(json.minAmount);
		        	}
		        	
		        	if(json.maxAmount != null && json.maxAmount != '' && json.maxAmount != 'undefined'){
		        		$("#loanAndAdvMaxAmount").val(json.maxAmount);
		        	}
		        	
		        	if(json.interestRate != null && json.interestRate != '' && json.interestRate != 'undefined'){
		        		$("#loanAndAdvInterstRate").val(json.interestRate);
		        	}
		        	
	        	  $("#loanAndAdvancesDetailsLoading").removeAttr('style');   
		        },
		    });
		 
	}
	
}
</script>


<script>
function getLoanAndAdvApprovers(){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getLoanAndAdvanceApprovers?", 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Name --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
		        		var opt = json[i];
		        		output = output + "<option value='" +opt.id+ "'>"+opt.userName+"&nbsp;("+opt.role+")</option>";
			        		
					}
		        	$("#loanAndAdvApprover").empty();
			        $("#loanAndAdvApprover").append(output);
			        $('#loanAndAdvApprover').trigger("chosen:updated");
			        
		        },
		    });
	
}
</script>


<script>
function getDetuctAmountDetails(){
	var requestedAmount = $("#requestedAmount").val();
	var noOfSchedules = $("#noOfSchedules").val();
	var effectiveDate = $("#effectiveDate").val();
	var scheduler = $("#scheduler").val();
	if(requestedAmount && noOfSchedules && effectiveDate && scheduler){
		
		var loanAndAdvInterstRate = $("#loanAndAdvInterstRate").val();
		if(loanAndAdvInterstRate != null && loanAndAdvInterstRate != '' && loanAndAdvInterstRate != 'undefined'){
			 $("#loanAndAdvancesDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			var interstRate = eval(loanAndAdvInterstRate / 100);
			var interestAmount = (requestedAmount * interstRate);
			var totalAmount = parseInt(requestedAmount) + parseInt(interestAmount);
			var paidAmount = $("#paidAmount").val();
			if(paidAmount != null && paidAmount != '' && paidAmount != 'undefined'){
				totalAmount =  parseInt(totalAmount) - paidAmount;
				$("#pendingAmount").val(totalAmount.toFixed(3));
			}
			var detuctAmount = eval(totalAmount / noOfSchedules);
			$("#deductAmount").val(detuctAmount.toFixed(3));
			
		    $.ajax({
				type : "GET",
				url: sourceleadUrl+"/getLoanAndAdvanceEndDateByEffectiveDate?effectiveDate="+effectiveDate+"&noOfSchedules="+noOfSchedules+"&scheduler="+scheduler,
				success :function(endDateStr) {
					
					$("#endDate").val(endDateStr);
					$("#loanAndAdvancesDetailsLoading").removeAttr('style');   
				}
			});
			
		}
	
	}
}
</script>

<script>
function shiftToInformationTab(){
	var indInformationDetails = document.getElementById("indInformationDetails");
	 document.getElementById("indInformationDetails").removeAttribute("class");
	 indInformationDetails.href="#tab-3";
	 $("#indInformationDetails").click();
}
</script>

<script>
function getIndAssociateLoanAndAdvancesDetails(){
	 var userId = $("#associateId").val();
     $("#loanAndAdvAssociateUserId").val(userId);
     getLoanAndAdvApprovers();
}
</script>


<script>
function getLoanAndAdvStatus(){
	var loanAndAdvStatus = document.getElementById("loanAndAdvStatus").value;
	if(loanAndAdvStatus){
		 var statusTypeDiv = document.getElementById("statusTypeDiv");
		 statusTypeDiv.style.display = "block";	
		 clearLoanAndAdvances();
		$("#requestedAmount").attr("data-validation", "required validate_float validate_length length1-12");
	    $("#scheduler").attr("data-validation", "required");
	    $("#effectiveDate").attr("data-validation", "required");
	    $("#deductAmount").attr("data-validation", "required");
	    $("#noOfSchedules").attr("data-validation", "required validate_int validate_length length1-5");
	    $("#endDate").attr("data-validation", "required");
	    $("#loanAndAdvApprover").attr("data-validation", "required");
	    
	    if(loanAndAdvStatus == 'Outstanding'){
		    var paidAmountDiv = document.getElementById("paidAmountDiv");
		    paidAmountDiv.style.display = "block";	
		    $("#paidAmount").attr("data-validation", "required  validate_float validate_length length1-12");
		    
		    var pendingAmountDiv = document.getElementById("pendingAmountDiv");
		    pendingAmountDiv.style.display = "block";	
		    $("#pendingAmount").attr("data-validation", "required  validate_float validate_length length1-12");
	    }else{
		    var paidAmountDiv = document.getElementById("paidAmountDiv");
		    paidAmountDiv.style.display = "none";	
		    $("#paidAmount").removeAttr("data-validation");
		    
		    var pendingAmountDiv = document.getElementById("pendingAmountDiv");
		    pendingAmountDiv.style.display = "none";	
		    $("#pendingAmount").removeAttr("data-validation");
	    }
	    
	    if(loanAndAdvStatus == 'Completed' || loanAndAdvStatus == 'Outstanding'){
	    	
	    	var issuedOnDiv = document.getElementById("issuedOnDiv");
	    	issuedOnDiv.style.display = "block";	
		    $("#issuedDate").attr("data-validation", "required");
		    
	    }else{
	    	var issuedOnDiv = document.getElementById("issuedOnDiv");
	    	issuedOnDiv.style.display = "none";	
		    $("#issuedDate").removeAttr("data-validation");
	    }
	
	}else{
	    var statusTypeDiv = document.getElementById("statusTypeDiv");
	    statusTypeDiv.style.display = "none";
		clearLoanAndAdvances();
	}
	
	function clearLoanAndAdvances(){
		 	
		   $("#requestedAmount").removeAttr("data-validation");
		    $("#scheduler").removeAttr("data-validation");
		    $("#effectiveDate").removeAttr("data-validation");
		    $("#deductAmount").removeAttr("data-validation");
		    $("#noOfSchedules").removeAttr("data-validation");
		    $("#endDate").removeAttr("data-validation");
		    $("#loanAndAdvApprover").removeAttr("data-validation");
		    $("#issuedDate").removeAttr("data-validation");
		    $("#paidAmount").removeAttr("data-validation");
		    $("#pendingAmount").removeAttr("data-validation");
		    
		    $("#requestedAmount").val("");
		    $("#scheduler").val("");
		    $("#effectiveDate").val("");
		    $("#deductAmount").val("");
		    $("#noOfSchedules").val("");
		    $("#endDate").val("");
		    $("#description").val("");
		    $("#issuedDate").val("");
		    $("#paidAmount").val("");
		    $("#pendingAmount").val("");
		    $("#loanAndAdvApprover").val("");
		    $("#loanAndAdvApprover").trigger("chosen:updated");
	}
	
}
</script>

<script>
$(function() {
	var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatProjectSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatProjectSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatProjectSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatProjectSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatProjectSave = 'yy-dd-mm'; 
	  }
	  
	  $('#effectiveDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
		    onClose : function(selected) {
	        	var dt = $(this).datepicker('getDate', '+1d');
	        	getDetuctAmountDetails();
	        	clearDateValidations();
			}
			 
		});
	  
	  $('#issuedDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
		    onClose : function(selected) {
	        	var dt = $(this).datepicker('getDate', '+1d');
	        	clearDateValidations();
			}
			 
		});
	
});


function clearDateValidations(){
	$('#effectiveDateDiv').find('p.jquery_form_error_message').remove();
	$('#issuedOnDiv').find('p.jquery_form_error_message').remove();
}
</script>