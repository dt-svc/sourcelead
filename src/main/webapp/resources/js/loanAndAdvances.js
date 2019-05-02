var loanAdv = new LoanAndAdvances();

function LoanAndAdvances(){
this.saveLoanAndAdvanceDetails  = function (){
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
		        		 var loanAndAdv = response.object;
	                	 $("#loanAndAdvId").val(loanAndAdv.id);
	                	 var indSalaryDetails = document.getElementById("indSalaryDetails");
						 document.getElementById("indSalaryDetails").removeAttribute("class");
						 indSalaryDetails.href="#tab-5";
						 $("#indSalaryDetails").click();
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


this.getLoanAndAdvNames = function(loanAndAdvId){
	 var loanAndAdvType = document.getElementById("loanAndAdvType").value; 
	 var associateUserId = document.getElementById("loanAndAdvAssociateUserId").value; 
	 if(loanAndAdvId == 'undefined' || loanAndAdvId == '' || loanAndAdvId == null){
		 loanAndAdvId = '';
	 }
	 if(loanAndAdvType != null && loanAndAdvType != "" && loanAndAdvType != 'undefined'){
		 $("#loanAndAdvancesDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getNamesByLoanAndAdvanceType?loanAndAdvType="+loanAndAdvType+"&associateUserId="+associateUserId+"&loanAndAdvId="+loanAndAdvId, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Name --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
		        		var selected = "";
		        		var opt = json[i];
		        		if(loanAndAdvId != null && loanAndAdvId != 'undefined' && loanAndAdvId != '' && loanAndAdvId == opt.id){
		        			selected = "selected";
		        		}
		        		output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.name+"</option>";
			        		
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
	 $("#effectiveDate").val("");
	 $("#issuedDate").val("");
	 $("#endDate").val("");
}


this.getLoanAndAdvDetails = function(){
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
	/*$("#effectiveDate").val("");
	$("#issuedDate").val("");
	$("#endDate").val("");*/
	
}

this.getLoanAndAdvApprovers =  function(userId){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getLoanAndAdvanceApprovers?", 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Name --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
		        		var opt = json[i];
		        		var selected = '';
		        		if(userId != null && userId != 'undefined' && userId != '' && userId == opt.id){
		        			selected = "selected";
		        		}
		        		output = output + "<option value='" +opt.id+ "' "+selected+">"+opt.userName+"&nbsp;("+opt.role+")</option>";
			        		
					}
		        	$("#loanAndAdvApprover").empty();
			        $("#loanAndAdvApprover").append(output);
			        $('#loanAndAdvApprover').trigger("chosen:updated");
			        
		        },
		    });
	
}

this.getDetuctAmountDetails = function(){
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
		
			var loanAndAdvStatus = document.getElementById("loanAndAdvStatus").value;
			if(loanAndAdvStatus == 'Outstanding'){
				var paidAmount = $("#paidAmount").val();
				if(paidAmount != null && paidAmount != '' && paidAmount != 'undefined'){
					totalAmount =  parseInt(totalAmount) - paidAmount;
					$("#pendingAmount").val(totalAmount.toFixed(3));
				}
			}
			var detuctAmount = eval(totalAmount / noOfSchedules);
			$("#deductAmount").val(detuctAmount.toFixed(2));
			$("#rateOfInterest").val(interstRate);
			$("#outstandingAmount").val(interestAmount);
			
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

this.shiftToInformationTab = function(){
	var indInformationDetails = document.getElementById("indInformationDetails");
	 document.getElementById("indInformationDetails").removeAttribute("class");
	 indInformationDetails.href="#tab-3";
	 $("#indInformationDetails").click();
}

this.getIndAssociateLoanAndAdvancesDetails = function(){
	 var userId = $("#associateId").val();
  if(userId != null && userId != 'undefined' && userId != ''){        
	 $("#loanAndAdvAssociateUserId").val(userId);
    $.ajax({
	    	type: "POST",
	    	url: sourceleadUrl+"/getAssociateLoanAndAdvanceDetails?userId="+userId,
	        success: function(json) {
	        	if(json != null && json.id != '0'){
	        		 $("#loanAndAdvId").val(json.id);
	        		 $("#loanAndAdvType").val(json.loanAndAdvType);
	        		 loanAdv.getLoanAndAdvNames(json.loanAndAdvName);
	        		 $("#loanAndAdvStatus").val(json.loanAndAdvStatus);
	        		 loanAdv.getLoanAndAdvStatus();
	        		 $("#requestedAmount").val(json.loanAmount);
	        		 $("select[name=scheduler]").val(json.scheduler);
	        		 
	        		 if(json.issuedDate != null && json.issuedDate != '' && json.issuedDate != 'undefined'){
	        			 $("input[name=issuedDate]").val(json.issuedDate);
	        		 }
	        		 
	        		 if(json.paidAmount != null && json.paidAmount != '' && json.paidAmount != 'undefined'){
	        			 $("#paidAmount").val(json.paidAmount);
	        		 }
	        		
	        		 $("#noOfSchedules").val(json.noOfSchedules);
	        		
	        		 if(json.pendingAmount != null && json.pendingAmount != '' && json.pendingAmount != 'undefined'){
	        			 $("#pendingAmount").val(json.pendingAmount); 
	        		 }
	        		
	        		 $("input[name=endDate]").val(json.endDate);
	        		 $("#deductAmount").val(json.deductAmount);
	        		 
	        		 $("input[name=effectiveDate]").val(json.effectiveDate);
	        		 loanAdv.getLoanAndAdvApprovers(json.loanAndAdvApprover);
	        		 loanAdv.getLoanAndAdvDetails();
	        		 
	        	}else{
	        		loanAdv.getLoanAndAdvApprovers();
	        	}
	        	
	        },
	    });
  }
}

this.getLoanAndAdvStatus =  function(){
	var loanAndAdvStatus = document.getElementById("loanAndAdvStatus").value;
	if(loanAndAdvStatus){
		 var statusTypeDiv = document.getElementById("statusTypeDiv");
		 statusTypeDiv.style.display = "block";	
		 var loanAndAdvId = $("#loanAndAdvId").val();
		 /*if(loanAndAdvId == null || loanAndAdvId == '' || loanAndAdvId == 'undefined'){
			 loanAdv.clearLoanAndAdvances();
		 }*/
		
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
	    loanAdv.clearLoanAndAdvances();
	}
	$("#effectiveDate").val("");
	$("#issuedDate").val("");
	$("#endDate").val("");
}


this.clearLoanAndAdvances = function(){
 	
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
	    
	    $("#loanAndAdvStatus").val("");
	    $("#loanAndAdvType").val("");
	    $("#loanAndAdvName").empty();
	    var output = output + "<option value=''>-- Select Name --</option>"; 
        $("#loanAndAdvName").append(output);
        $('#loanAndAdvName').trigger("chosen:updated");
	    var statusTypeDiv = document.getElementById("statusTypeDiv");
		statusTypeDiv.style.display = "none";	
		
		
		  $("#requestedAmountDiv").find('p.jquery_form_error_message').remove();
		  $("#schedulerDiv").find('p.jquery_form_error_message').remove();
		  $("#effectiveDateDiv").find('p.jquery_form_error_message').remove();
		  $("#deductAmountDiv").find('p.jquery_form_error_message').remove();
		  $("#noOfSchedulesDiv").find('p.jquery_form_error_message').remove();
		  $("#endDateDiv").find('p.jquery_form_error_message').remove();
		  $("#descriptionDiv").find('p.jquery_form_error_message').remove();
		  $("#issuedDateDiv").find('p.jquery_form_error_message').remove();
		  $("#paidAmountDiv").find('p.jquery_form_error_message').remove();
		  $("#pendingAmountDiv").find('p.jquery_form_error_message').remove();
		  $("#loanAndAdvApproverDiv").find('p.jquery_form_error_message').remove();
		  $("#loanAndAdvStatusDiv").find('p.jquery_form_error_message').remove();
		  $("#loanAndAdvTypeDiv").find('p.jquery_form_error_message').remove();
		  $("#loanAndAdvNameDiv").find('p.jquery_form_error_message').remove();
}





this.clearDateValidations = function(){
	$('#effectiveDateDiv').find('p.jquery_form_error_message').remove();
	$('#issuedOnDiv').find('p.jquery_form_error_message').remove();
}

}