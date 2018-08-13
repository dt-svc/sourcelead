 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-4" class="tabsMenu_tabs_inn clearfix">
  <div id="loanAndAdvancesDetailsLoading"></div>
    <form:form id="associate_informationDetails_form" name="associate_informationDetails_form" method="post">
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
                      <option value="Completed">Completed</option>
                      <option value="Outstanding">Outstanding</option>
                    </select>
                  </div>
              </div>
              </div>
              
              
            <div class="clearfix">
	         
	         <div class="clearfix col-md-10 noPad mb">
	           <div class="clearfix" id="requestTypeDiv" style="display: none;">
			         <div class="col-md-4 mb">
		                  <label>Requested Amount<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="requestedAmount" id="requestedAmount"  onblur="getDetuctAmountDetails();" field-name="Requested Amount">
		              </div>
		              
		               <div class="col-md-4 mb">
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
		              
		              <div class="col-md-4 mb">
		                  <label>Effective Date<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control calendar" name="effectiveDate"  id="effectiveDate" field-name="Effective Date"  readonly="readonly" >
		              </div>
		            
		              
	                  <div class="col-md-4 mb">
		                  <label>No Of Schedules<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="noOfSchedules" id="noOfSchedules" onblur="getDetuctAmountDetails();" field-name="No Of Schedules">
		              </div>
		              
		               <div class="col-md-4 mb">
		                  <label>End Date<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control calendar" name="endDate"  id="endDate" field-name="End Date"  readonly="readonly" >
		              </div>
		              
		              <div class="col-md-4 mb">
		                  <label>Deduct Amount<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="deductAmount" id="deductAmount" readonly="readonly" field-name="Deduct Amount">
		              </div>
		              
		              <div class="col-md-4 mb">
		                  <label>Approver<span class="text-danger">&nbsp;*</span></label>
		                   <select class="form-control chosen-select" name="loanAndAdvApprover" id="loanAndAdvApprover"  field-name="Approver" field-name="Approver">
                        		<option value="">-- Select Approver --</option>
                   		   </select>
		              </div>
		              
		               <div class="col-md-8 mb">
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
	if(loanAndAdvStatus == 'Requested'){
	    var requestedDiv = document.getElementById("requestTypeDiv");
	    requestedDiv.style.display = "block";	
	    $("#requestedAmount").attr("data-validation", "required validate_int validate_length length1-12");
	    $("#scheduler").attr("data-validation", "required");
	    $("#effectiveDate").attr("data-validation", "required");
	    $("#deductAmount").attr("data-validation", "required validate_int validate_length length1-12");
	    $("#noOfSchedules").attr("data-validation", "required validate_int validate_length length1-5");
	    $("#endDate").attr("data-validation", "required");
	    $("#loanAndAdvApprover").attr("data-validation", "required");
	    
	    
	}else{
		var requestedDiv = document.getElementById("requestTypeDiv");
		requestedDiv.style.display = "none";	
	    $("#requestedAmount").removeAttr("data-validation");
	    $("#scheduler").removeAttr("data-validation");
	    $("#effectiveDate").removeAttr("data-validation");
	    $("#deductAmount").removeAttr("data-validation");
	    $("#noOfSchedules").removeAttr("data-validation");
	    $("#endDate").removeAttr("data-validation");
	    $("#loanAndAdvApprover").removeAttr("data-validation");
	    
	    $("#requestedAmount").val("");
	    $("#scheduler").val("");
	    $("#effectiveDate").val("");
	    $("#deductAmount").val("");
	    $("#noOfSchedules").val("");
	    $("#endDate").val("");
	    $("#description").val("");
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
			}
			 
		});
	  
	
});
</script>