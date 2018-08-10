 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-4" class="tabsMenu_tabs_inn clearfix">
  <div id="loanAndAdvancesDetailsLoading"></div>
    <form:form id="associate_informationDetails_form" name="associate_informationDetails_form" method="post">
	 	  <input type="hidden" name="associateUserId" id="loanAndAdvAssociateUserId"/>
	      <input type="hidden" name="loanAndAdvId" id="loanAndAdvId"/>
	      
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
                    <select class="form-control chosen-select" name="loanAndAdvName" id="loanAndAdvName"  field-name="Name" data-validation="required" field-name="Name">
                        <option value="">-- Select Name --</option>
                      </select>
                  </div>
              </div>
              
              <div class="col-md-4">
                  <label>Status<span class="text-danger">&nbsp;*</span></label>
                  <div class="dropdown-select">
                    <select class="form-control" data-placeholder="Select Type" id="loanAndAdvStatus" onchange="getLoanAndAdvStatus();" name="loanAndAdvStatus" data-validation="required" field-name="Status">
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
		                   <input type="text" class="form-control" name="requestedAmount" id="requestedAmount"  field-name="Requested Amount">
		              </div>
		              
		               <div class="col-md-4 mb">
		                  <label>Scheduler<span class="text-danger">&nbsp;*</span></label>
		                  <div class="dropdown-select">
		                    <select class="form-control" data-placeholder="Select Type" id="scheduler" name="scheduler"  field-name="Scheduler">
		                      <option value="">-- Select Scheduler --</option>
		                      <option value="Every Pay">Every Pay</option>
		                      <option value="First Of Every Month">First Of Every Month</option>
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
		                  <label>Deduct Amount<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="deductAmount" id="deductAmount"  field-name="Deduct Amount">
		              </div>
		              
		              <div class="col-md-4 mb">
		                  <label>No Of Schedules<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control" name="noOfSchedules" id="noOfSchedules"  field-name="No Of Schedules">
		              </div>
		              
		              <div class="col-md-4 mb">
		                  <label>End Date<span class="text-danger">&nbsp;*</span></label>
		                   <input type="text" class="form-control calendar" name="endDate"  id="endDate" field-name="End Date"  readonly="readonly" >
		              </div>
             </div>
             
              </div>
              </div>
              
	      </div>
     </form:form>
  
</div>

<script>
function getLoanAndAdvNames(){
	 var loanAndAdvType = document.getElementById("loanAndAdvType").value; 
	 if(loanAndAdvType != null && loanAndAdvType != "" && loanAndAdvType != 'undefined'){
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
		        },
		    });
	 }else{
		 var output = "";
     	 output = output + "<option value=''>-- Select Name --</option>"; 
     	 $("#loanAndAdvName").empty();
	     $("#loanAndAdvName").append(output);
	     $('#loanAndAdvName').trigger("chosen:updated");
	 }
}
</script>

<script>
function getIndAssociateLoanAndAdvancesDetails(){
	 var userId = $("#associateId").val();
     $("#loanAndAdvAssociateUserId").val(userId);
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
	    
	}else{
		var requestedDiv = document.getElementById("requestTypeDiv");
		requestedDiv.style.display = "none";	
	    $("#requestedAmount").removeAttr("data-validation");
	    $("#scheduler").removeAttr("data-validation");
	    $("#effectiveDate").removeAttr("data-validation");
	    $("#deductAmount").removeAttr("data-validation");
	    $("#noOfSchedules").removeAttr("data-validation");
	    $("#endDate").removeAttr("data-validation");
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
	  
	  $('#effectiveDate, #endDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			 
		});
	  
	
});
</script>