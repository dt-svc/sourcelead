<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="/resources/js/loanAndAdvances.js"></script>


<div  class="tabsMenu_tabs_inn project_wrap noPad" style="display: block;">
 <div id="loanAndAdvChoicepopUp"></div>
<div class="reportHead clearfix">
	<ul class="pull-right list-unstyled list-inline">
		<li><a  onclick="showAssociateLoanAndAdvancePopup();"  class="newAdBtn mr5">+ Add</a></li>
	</ul>
</div>

<div class="clearfix project_tabs_inn_table overflow_Auto">
	<table width="100%">
		<thead>
			<tr>
			  <th>Type</th>   
			  <th>Name</th>        
			  <th>Status</th>         
			  <th>Amount</th>     
			  <th>Scheduler</th>     
			  <th>No Of Schedules</th> 
			  <th>Effective Date</th>
			  <th>Deduct Amount</th>         
			  <th>Approver</th>         
			  <th>Action</th>                  
			</tr>
		</thead>
		
		<tbody>
			<c:if test="${loanAdvListSize > 0}">
			 <c:forEach var="loanAdv" items="${loanAdvList}">
				 <tr>
				   <td><div class="tblminwidth-50" title="${loanAdv.loanAdvanceType}">${loanAdv.loanAdvanceType}</div></td>
				   <td><div class="tblminwidth-50" title="${loanAdv.payrollLoanAndAdvanceId.name}">${loanAdv.payrollLoanAndAdvanceId.name}</div></td>
				   <td><div class="tblminwidth-50" title="${loanAdv.loanAndAdvanceStatus}">${loanAdv.loanAndAdvanceStatus}</div></td>
				   <td><div class="tblminwidth-50" title="${loanAdv.amount}">${SourceLeadWeb_orgLocations.country == 'United States' ? '$' : ''}   ${loanAdv.amount}</div></td>
				   <td><div class="tblminwidth-50" title="${loanAdv.scheduler}">${loanAdv.scheduler}</div></td>
				   <td><div class="tblminwidth-30" title="${loanAdv.noOfSchedules}">${loanAdv.noOfSchedules}</div></td>
				   <td><div class="tblminwidth-50" title="${loanAdv.effectiveDateStr}">${loanAdv.effectiveDateStr}</div></td>
				   <td><div class="tblminwidth-50" title="${loanAdv.deductAmount}">$ ${loanAdv.deductAmount}</div></td>
				   <td><div class="tblminwidth-50" title="${loanAdv.approverName}">${loanAdv.approverName}</div></td>
				   <td><div class="tblminwidth-50"><a href="javascript:void(0)" onclick="editLoanAndAdvance('${loanAdv.id}')">Edit</a>
				     |&nbsp;<a href="javascript:void(0)" onclick="deleteLoanAndAdvance('${loanAdv.id}')">Delete</a>
				   </div></td>
				 </tr>
			 </c:forEach>
			</c:if>
		</tbody>
	</table>
	<c:if test="${loanAdvListSize == 0}">
		 <div class='row'> 
			 <div class='col-lg-12'> 
				 <div class='dataTables_empty'>
				 </div>
			 </div>
		 </div>
	</c:if>
</div>
</div>


<div class="modal paymentaccount-modal" id="paymentAccountPopup" role="dialog">
    <div class="modal-dialog modal-lg">
     <div id="loanAndAdvancesDetailsLoading"></div>
      <!-- Modal content-->
      <form:form id="associate_loanAndAdvance_form" name="associate_loanAndAdvance_form" method="post">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onclick="closeAssociateLoanAndAdvancePopup();" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Loan / Advance</h4>
        </div>
        <div class="modal-body minHeightcol5">
		  <input type="hidden" name="associateUserId" value="${userId}" id="loanAndAdvAssociateUserId"/>
	      <input type="hidden" name="loanAndAdvId" id="loanAndAdvId"/>
	      <input type="hidden" id="loanAndAdvMaxAmount"/>
	      <input type="hidden" id="loanAndAdvMinAmount"/>
	      <input type="hidden" id="loanAndAdvInterstRate"/>
	      
		   <div class="clearfix row minHeightcol5">
		   
		   <div class="clearfix col-md-10 noPad mb">
	         <div class="col-md-4" id="loanAndAdvTypeDiv">
                  <label>Type<span class="text-danger">&nbsp;*</span></label>
                  <div class="dropdown-select">
                    <select class="form-control" data-placeholder="Select Type" id="loanAndAdvType" onchange="loanAdv.getLoanAndAdvNames();loanAdv.getDetuctAmountDetails();" name="loanAndAdvType" data-validation="required" field-name="Type">
                      <option value="">-- Select Type --</option>
                      <option value="Loan">Loan</option>
                      <option value="Advance">Advance</option>
                    </select>
                  </div>
              </div>
              
               <div class="col-md-4" id="loanAndAdvNameDiv">
                  <label>Name<span class="text-danger">&nbsp;*</span></label>
                  <div class="dropdown-select">
                    <select class="form-control chosen-select" name="loanAndAdvName" id="loanAndAdvName" onchange="loanAdv.getLoanAndAdvDetails();loanAdv.getDetuctAmountDetails();" field-name="Name" data-validation="required" field-name="Name">
                        <option value="">-- Select Name --</option>
                      </select>
                  </div>
              </div>
              
              <div class="col-md-4" id="loanAndAdvStatusDiv">
                  <label>Status<span class="text-danger">&nbsp;*</span></label>
                  <div class="dropdown-select">
                    <select class="form-control" data-placeholder="Select Type" id="loanAndAdvStatus" onchange="loanAdv.getLoanAndAdvStatus();loanAdv.getLoanAndAdvDetails();loanAdv.getDetuctAmountDetails();" name="loanAndAdvStatus" data-validation="required" field-name="Status">
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
			         <div class="col-md-4 minHeight" id="requestedAmountDiv">
		                  <label>Amount<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="loanAmount" id="requestedAmount"  onblur="loanAdv.getDetuctAmountDetails();" field-name="Amount">
		              </div>
		              
		               <div class="col-md-4 minHeight" id="schedulerDiv">
		                  <label>Scheduler<span class="text-danger">&nbsp;*</span></label>
		                  <div class="dropdown-select">
		                    <select class="form-control" data-placeholder="Select Type" id="scheduler" onchange="loanAdv.getDetuctAmountDetails();" name="scheduler"  field-name="Scheduler">
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
		                   <input type="text" class="form-control calendar" onmouseenter="getDatePicker();" name="effectiveDate"  id="effectiveDate" field-name="Effective Date"  readonly="readonly" >
		              </div>
		              
		               <div class="col-md-4 minHeight" id="issuedOnDiv" style="display: none;">
		                  <label>Issued On<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control calendar" onmouseenter="getDatePicker();" name="issuedDate"  id="issuedDate" field-name="Issued On"  readonly="readonly" >
		               </div>
		               
		               <div class="col-md-4 minHeight" id="paidAmountDiv" style="display: none;">
		                  <label>Paid Amount<span class="text-danger">&nbsp;*</span></label>
		                  <input type="text" class="form-control" name="paidAmount" id="paidAmount" onblur="loanAdv.getDetuctAmountDetails();" field-name="Paid Amount">
		               </div>
		               
		               <div class="col-md-4 minHeight" id="noOfSchedulesDiv">
		                  <label>No Of Schedules<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="noOfSchedules" id="noOfSchedules" onblur="loanAdv.getDetuctAmountDetails();" field-name="No Of Schedules" data-validation="validate_int">
		              </div>
		               
		               <div class="col-md-4 minHeight" id="pendingAmountDiv" style="display: none;">
		                  <label>Pending Amount<span class="text-danger">&nbsp;*</span></label>
		                  <input type="text" class="form-control" name="pendingAmount" id="pendingAmount" field-name="Pending Amount" readonly="readonly">
		               </div>
		              
		               <div class="col-md-4 minHeight" id="endDateDiv">
		                  <label>End Date<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control calendar" onmouseenter="getDatePicker();" name="endDate"  id="endDate" field-name="End Date"  readonly="readonly" >
		              </div>
		              
		              <div class="col-md-4 minHeight" id="deductAmountDiv">
		                  <label>Deduct Amount<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="deductAmount" id="deductAmount" readonly="readonly" field-name="Deduct Amount">
		              </div>
		              <input type="hidden" name="rateOfInterest" id="rateOfInterest"/>
		              <input type="hidden" name="outstandingAmount" id="outstandingAmount"/>
		              <div class="col-md-4 minHeight" id="loanAndAdvApproverDiv">
		                  <label>Approver<span class="text-danger">&nbsp;*</span></label>
		                   <select class="form-control chosen-select" name="loanAndAdvApprover" id="loanAndAdvApprover"  field-name="Approver" field-name="Approver">
                        		<option value="">-- Select Approver --</option>
                   		   </select>
		              </div>
		              
		               <div class="col-md-8 minHeight" id="descriptionDiv">
		                  <label>Description</label>
		                  <textarea rows="4" class="form-control" id="description"
									name="description" field-name="Description"
									data-validation="validate_length length2-5000"
									data-validation-optional="true"></textarea>
		              </div>
             
              </div>
        </div> 
		          
		            <div class="clearfix"></div>
		  </div>
          
        </div>
        <div class="clearfix"></div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success btnSuccess" id="savePaymentButton" onclick="saveAssociateLoanAndAdvanceDetails();" >Save</button>
          <button type="button" class="btn btn-default ml10" onclick="closeAssociateLoanAndAdvancePopup();" >Cancel</button>
        </div>
      </div>
       </form:form>
    </div>
  </div>
  
<script>
function saveAssociateLoanAndAdvanceDetails(){
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
		        		closeAssociateLoanAndAdvancePopup();
		        		var candidateId = '${candidateId}';
		        		associateLoanAndAdvanceDetails(candidateId);
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

<script type="text/javascript">
function loanAndAdvAlertMessage(loanAdvId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure to delete?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteSavedLoanAndAdvance("+loanAdvId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelDeleteLoanAndAdvance();'></b>";
	result = result +"	</div>";
	$("#loanAndAdvChoicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script>
function deleteLoanAndAdvance(loanAdvId){
	loanAndAdvAlertMessage(loanAdvId);
}


function deleteSavedLoanAndAdvance(loanAdvId){
	$.ajax({
    	type: "POST",
    	url: sourceleadUrl+"/deleteAssociateLoanAndAdvanceDetailsById?loanAdvId="+loanAdvId,
        success: function(json) {
        	var candidateId = '${candidateId}';
    		associateLoanAndAdvanceDetails(candidateId);
        },
    });
}

function cancelDeleteLoanAndAdvance(){
	$("#loanAndAdvChoicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>



<script>
function editLoanAndAdvance(loanAdvId){
	$(".modal.paymentaccount-modal").fadeIn();
	$(".comMask").fadeIn();
	 $("#loanAndAdvancesDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');	
	$.ajax({
    	type: "POST",
    	url: sourceleadUrl+"/getAssociateLoanAndAdvanceDetailsById?loanAdvId="+loanAdvId,
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
        		    setTimeout(function () {
	               		 loanAdv.getLoanAndAdvDetails();
        	 			}, 1000);
        		  
        		    setTimeout(function () {
        		    	loanAdv.getDetuctAmountDetails();
       	 			}, 2000);
        		    
        		    
        		 
        		 
        	}else{
        		loanAdv.getLoanAndAdvApprovers();
        	}
        	$("#loanAndAdvancesDetailsLoading").removeAttr('style');
        },
    });
}
</script>
  
<script>
var sourceleadUrl = '${sourceleadUrl}';
function showAssociateLoanAndAdvancePopup() {
	loanAdv.clearLoanAndAdvances();
	loanAdv.getLoanAndAdvNames();
    $(".modal.paymentaccount-modal").fadeIn();
    $(".comMask").fadeIn();
}
</script>

<script>
function closeAssociateLoanAndAdvancePopup() {
	loanAdv.clearLoanAndAdvances();
    $(".modal.paymentaccount-modal").fadeOut();
    $(".comMask").fadeOut();
}
</script>

<script>
function getDatePicker(){
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
	        	loanAdv.getDetuctAmountDetails();
	        	loanAdv.clearDateValidations();
	        	verifyLoanEligibility();
			}
			 
		});
	  
	  $('#issuedDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
		    onClose : function(selected) {
	        	var dt = $(this).datepicker('getDate', '+1d');
	        	loanAdv.clearDateValidations();
			}
			 
		});
}

function verifyLoanEligibility(){
	var effectiveDate = $('#effectiveDate').val();
	var associateUserId = $('#loanAndAdvAssociateUserId').val();
	var loanAndAdvName = $('#loanAndAdvName').val();
	if(effectiveDate != 'undefined' && effectiveDate != '' && effectiveDate != null){
		$.ajax({
	    	type: "POST",
	    	url: sourceleadUrl+"/verifyLoanEligibilityByAssociateIdAndLoanAdv?loanAndAdvName="+loanAndAdvName+"&associateUserId="+associateUserId+"&effectiveDate="+effectiveDate,
	        success: function(json) {
	        	if(!json.object){
	        		$('#savePaymentButton').attr('disabled', 'disabled');
	        		var loginUserId = '${SourceLeadWeb_userId}';
	        		if(loginUserId == associateUserId){
	        			$('#effectiveDateDiv').append('<p class="jquery_form_error_message">You are ineligible to apply for this '+$('#loanAndAdvType').val()+'</p>');
	        		}else{
	        			$('#effectiveDateDiv').append('<p class="jquery_form_error_message">Associate ineligible to apply for this '+$('#loanAndAdvType').val()+'</p>');
	        		}
	        	}else{
	        		$('#savePaymentButton').removeAttr('disabled');
	        		$('#effectiveDateDiv').find('p').emplty();
	        	}
	        },
	    });
	}
}
</script>