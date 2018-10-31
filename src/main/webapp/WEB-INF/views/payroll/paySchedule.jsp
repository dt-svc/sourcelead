<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<script>
$(document).ready(function(){
	getApproverList();
});

</script>


 <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Pay Schedule</h2>
      </div>
        <div class="success_pop" id="selector2" style="display: none;">
          <i class="fa fa-check-circle"></i><span id="messageFadeDIV "></span>
        </div>
      <div class="pull-right brud-crum">Home &raquo; Pay Schedule</div>
    </section>
    <div class="clearfix"></div>
    <div class="timesheet_out">
      <div class="tablelist-wrap clearfix">
          <div class="row">
		  <div class="col-md-6 mt">
		  <div id="loaderDIV"></div>
		    <form:form id="add_payroll_schedule_form" name="add_payroll_schedule_form" method="post">
            <div class="form-group clearfix">
				<label class="col-md-4">Pay Frequency</label>
              <div class="col-md-6">
                <div class="dropdown-select">
                  <select class="sel" id="payFrequency" name="payFrequency" data-validation="required" field-name="Pay Frequency">
                    <option value="">Select Frequency</option>
                    <option value="Weekly">Weekly</option>
                    <option value="Bi-Weekly">Bi-Weekly</option>
                    <option value="Monthly">Monthly</option>
                  </select>
                </div>
              </div>
            </div>
			  
			  <div style="display: block;">
          <div class="form-group clearfix" id="frequencyBy">
				<label class="col-md-4">Pay Period Start Date</label>
              <div class="col-md-6">
                <input class="form-control calendar" placeholder="Date" type="text" id="payPeriodStartDate" name="payPeriodStartDate" data-validation="required" field-name="Pay Period Start Date">
              </div>
			  <div class="clearfix"></div>
          </div>
		<div id="frequencyByMonthly" style="display: none;">
          <div class="form-group clearfix">
				<label class="col-md-4">Next Pay Month</label>
              <div class="col-md-6">
				  <select class="sel" id="nextPayMonth" name="nextPayMonth">
				  	<option value="">Slect Month</option>
				  	<option value="JANUARY">January</option>
				  	<option value="FEBRUARY">February</option>
				  	<option value="MARCH">March</option>
				  	<option value="APRIL">April</option>
				  	<option value="MAY">May</option>
				  	<option value="JUNE">June</option>
				  	<option value="JULY">July</option>
				  	<option value="AUGUST">August</option>
				  	<option value="SEPTEMBER">September</option>
				  	<option value="OCTOBER">October</option>
				  	<option value="NOVEMBER">November</option>
				  	<option value="DECEMBER">December</option>
					  
				  </select>
              </div>
			  <div class="clearfix"></div>
          </div>
          </div>
			 <h5 class="col-md-12 clearfix">Pay Period Frequency</h5>
			  <div class="clearfix"></div>
			  <div class="form-group clearfix mt">
				<label class="col-md-4">Pay Date</label>
              <div class="col-md-6">
                <input class="form-control calendar" placeholder="Date" type="text" id="payCheckDate" name="payCheckDate" data-validation="required" field-name="payCheckDate">
              </div>
			  <div class="clearfix"></div>
            </div>
			  <div class="form-group clearfix">
				<label class="col-md-4">Run Payroll</label>
              <div class="col-md-6">
                <input class="form-control calendar" placeholder="Date" type="text" id="runPayrollDate" name="runPayDate" data-validation="required" field-name="Run Payroll Date">
              </div>
			  <div class="clearfix"></div>
            </div>
            
            <div class="form-group">
              <label class="col-md-12 paddingRight">Notify to Members</label>
				<div class="col-md-12">
					<select class="form-control chosen-select" multiple data-placeholder="Notify Members" id="notifyMember" name="notifyMember">
					</select>
				</div>
            </div>
          <!--   <div class="form-group clearfix">
				<label class="col-md-4">Notify to Members</label>
              <div class="col-md-6">
                <input class="form-control calendar" placeholder="Notify Members" type="text" id="notifyMembers" name="notifyMembers">
              </div>
			  <div class="clearfix"></div>
            </div> -->
			  </div>
			  
			  <div class="clearfix"></div>
			  
			  <div class="col-md-6 col-md-offset-4 mb">
			  	<div class="pull-right">
					<button type="button" class="btn btn-primary" id="save_pay_schedule_button" name="save_pay_schedule_button" onclick="savePayrollSchedule();">Save</button>  
				</div>
			  </div>
			  </form:form>
		  </div>

        <div class="col-md-6">
        	<div class="clearfix mt customEvents-Calendar mt25">
        		<div class="calendarEvent" id="calendar-oneweek"></div>
        	</div>
        </div>
		  </div>
        
      </div>
    </div>
  </section>
  <!-- Content Area Ends--> 
  
  
  <script>
	function msgFadein() {
		$("#selector2").fadeIn(200);
        $('#selector2').delay(2000).fadeOut('slow');
	}
</script>

<script>
function savePayrollSchedule(){
	if ($('#add_payroll_schedule_form').validate(false, validationSettings)){  
// 	 	    var formData = getFormData("add_payroll_schedule_form");
	 	   var formData = $($('#add_payroll_schedule_form')[0]).serialize();
	        	  $("#loaderDIV").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	  $.post(sourceleadUrl+"/savePayrollSchedule?"+tokenParamName+"="+tokenValue, formData, function(data) {
	        		  
	        		  if (result && result.response === "INVALID_DATA") {
		                    $("#save_pay_schedule_button").removeAttr("disabled");
		                    $("#save_pay_schedule_button").removeAttr('button').val('Save');
		                    showError(result.responseMessage);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	    $("#loaderDIV").removeAttr("style");
			        		 /*  location.replace(sourceleadUrl+ "/payrollDeductionList"); */
			        		    $("#selector2").removeAttr("style");
				     			 $("#selector2").css({ display: "block" });
				     			 $("#messageFadeDIV ").html("Added Successfully");
				     			msgFadein();
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    $("#save_pay_schedule_button").removeAttr("disabled");
		                    $("#save_pay_schedule_button").addClass('button').val('Save');
		                    showError("Operation failed, please try again");
		                }
	        		  
	  			   }); 
	    	         return true; 
     }else{
    		return false;
    	  } 
}
</script>
  
<!--   <script type="text/javascript">
$(function() {
	$("#payCheckDate").datepicker();
	$("#runPayrollDate").datepicker();
	$("#payPeriodStartDate").datepicker();
})
 -->



<script>
var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
// written common method in slead-common.js for date formats start
var finalDateFormat = getJSDateFormat(locationDate);
//end
$('#payCheckDate').datepicker({
    dateFormat: finalDateFormat,
    changeMonth: true,
	  changeYear: true, 
    //minDate: 0  this line for to restrict selection of previous dates
});

$('#runPayrollDate').datepicker({
    dateFormat: finalDateFormat,
    changeMonth: true,
	  changeYear: true, 
    //minDate: 0  this line for to restrict selection of previous dates
});

$('#payPeriodStartDate').datepicker({
    dateFormat: finalDateFormat,
    changeMonth: true,
	  changeYear: true, 
	  onSelect : function(selected) {
		//addValidClass();
	}
    //minDate: 0  this line for to restrict selection of previous dates
});
	 
	
	/* function addValidClass(){
		 $('#payrollPayDateDivId').find('p.jquery_form_error_message').remove(); 
	} */
</script>
	
	<script>
$("#payFrequency").change(function() {
	var frequency = $(this).val();
	if(frequency != "Monthly"){
		$("#frequencyBy").show();
		$("#frequencyByMonthly").hide();
	}else {
		$("#frequencyBy").hide();
		$("#frequencyByMonthly").show();
	}
})	
</script>


<script>
  function getApproverList(){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/findPayrollApprover?"+tokenParamName+"="+tokenValue, 
		        success: function(data) {
		        var output = "";
				for (var i=0; i<data.length; i++)
				{
				var opt = data[i];
				 output = output + "<option value='" +opt.id+ "'>"+opt.fullName+"  ("+opt.role+")</option>";
				}
				$("#notifyMember").empty();
				$("#notifyMember").append(output);
				$('#notifyMember').trigger("chosen:updated");
				
		        },
		    });
  }
  </script>

<jsp:include page="../masterFooter.jsp" />


 		