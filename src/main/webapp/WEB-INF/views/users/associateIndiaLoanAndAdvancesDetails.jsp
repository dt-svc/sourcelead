 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="/resources/js/loanAndAdvances.js"></script>

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
                    <select class="form-control" data-placeholder="Select Type" id="loanAndAdvType" onchange="loanAdv.getLoanAndAdvNames();loanAdv.getDetuctAmountDetails();" name="loanAndAdvType" data-validation="required" field-name="Type">
                      <option value="">-- Select Type --</option>
                      <option value="Loan">Loan</option>
                      <option value="Amount">Amount</option>
                    </select>
                  </div>
              </div>
              
               <div class="col-md-4">
                  <label>Name<span class="text-danger">&nbsp;*</span></label>
                  <div class="dropdown-select">
                    <select class="form-control chosen-select" name="loanAndAdvName" id="loanAndAdvName" onchange="loanAdv.getLoanAndAdvDetails();loanAdv.getDetuctAmountDetails();" field-name="Name" data-validation="required" field-name="Name">
                        <option value="">-- Select Name --</option>
                      </select>
                  </div>
              </div>
              
              <div class="col-md-4">
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
			         <div class="col-md-4 minHeight">
		                  <label>Amount<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="loanAmount" id="requestedAmount"  onblur="loanAdv.getDetuctAmountDetails();" field-name="Amount">
		              </div>
		              
		               <div class="col-md-4 minHeight">
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
		                   <input type="text" class="form-control calendar" name="effectiveDate"  id="effectiveDate" field-name="Effective Date"  readonly="readonly" >
		              </div>
		              
		               <div class="col-md-4 minHeight" id="issuedOnDiv" style="display: none;">
		                  <label>Issued On<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control calendar" name="issuedDate"  id="issuedDate" field-name="Issued On"  readonly="readonly" >
		               </div>
		               
		               <div class="col-md-4 minHeight" id="paidAmountDiv" style="display: none;">
		                  <label>Paid Amount<span class="text-danger">&nbsp;*</span></label>
		                  <input type="text" class="form-control" name="paidAmount" id="paidAmount" onblur="loanAdv.getDetuctAmountDetails();" field-name="Paid Amount">
		               </div>
		               
		               <div class="col-md-4 minHeight">
		                  <label>No Of Schedules<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="noOfSchedules" id="noOfSchedules" onblur="loanAdv.getDetuctAmountDetails();" field-name="No Of Schedules">
		              </div>
		               
		               <div class="col-md-4 minHeight" id="pendingAmountDiv" style="display: none;">
		                  <label>Pending Amount<span class="text-danger">&nbsp;*</span></label>
		                  <input type="text" class="form-control" name="pendingAmount" id="pendingAmount" field-name="Pending Amount" readonly="readonly">
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
	                    <button type="button" class="btn btn-success btnSuccess" id="saveLoan&AdvButton" onclick="loanAdv.saveLoanAndAdvanceDetails();" >Save & Continue</button>
                        <button type="button" class="btn btn-default ml10" onclick="skipToLeaveTypeTab();" >Skip</button>
	                    </div>
	                 </div>
                </div>
	      
     </form:form>
  
</div>
<script>
function shiftToInformationTab(){
	 var indInformationDetails = document.getElementById("indInformationDetails");
	 document.getElementById("indInformationDetails").removeAttribute("class");
	 indInformationDetails.href="#tab-3";
	 $("#indInformationDetails").click();
}
</script>


<script>
function skipToLeaveTypeTab(){
	var indSalaryDetails = document.getElementById("indLeaveTypeDetails");
	 document.getElementById("indLeaveTypeDetails").removeAttribute("class");
	 indSalaryDetails.href="#tab-6";
	 $("#indLeaveTypeDetails").click();
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
	        	loanAdv.getDetuctAmountDetails();
	        	loanAdv.clearDateValidations();
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
});
</script>
