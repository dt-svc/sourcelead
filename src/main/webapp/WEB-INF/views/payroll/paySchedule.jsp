<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<script>
$(document).ready(function(){
	var payrollScheduleUUID = "${payScheduleUUID}";
	/* getApproverList(); */
	getPayrollSchedule(payrollScheduleUUID);
	$("#endDateDIV").hide();
});

</script>


 <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Pay Schedule</h2>
      </div>
        <div class="success_pop" id="selector2" style="display: none;">
          <i class="fa fa-check-circle"></i><span id="msgFadeDiv"></span>
        </div>
        <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>  &raquo;
          <a href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo;
          <a href="<c:url value='${appPath}/payrollScheduleList'/>"> Pay Schedule List</a>&raquo;Add Pay Schedule
        </div>
    </section>
    <div class="clearfix"></div>
    <div class="timesheet_out">
      <div class="tablelist-wrap clearfix">
          <div class="row mt">
		  <div class="clearfix">
		  <div id="loaderDIV"></div>
		    <form:form id="add_payroll_schedule_form" name="add_payroll_schedule_form" method="post">
            <div class="col-md-6">
            <div class="form-group clearfix">
			 <label class="col-md-4">Pay Frequency</label>
              <div class="col-md-8">
                <div class="dropdown-select" id="scheduleFreDIV">
                  <select class="sel" id="payFrequency" name="payFrequency" data-validation="required" onchange="weekOnChange();" field-name="Pay Frequency">
                    <option value="">Select Frequency</option>
                    <option value="Weekly">Weekly</option> 
                    <option value="Bi-Weekly">Bi-Weekly</option>
                    <option value="Monthly">Monthly</option>
                  </select>
                </div>
              </div>
              </div>
            
			  <input type="hidden" value="${payScheduleUUID}" name="payrollScheduleUUID"/>
			  <div style="display: block;">
          <div class="form-group clearfix" id="frequencyBy">
				<label class="col-md-4">Pay Period Start Date</label>
              <div class="col-md-8" id="payperiodDiv1">
                <input class="form-control calendar" placeholder="Date" type="text" id="payPeriodStartDate" name="payPeriodStartDate"  field-name="Pay Period Start Date">
              	<div class="clearfix mt5"></div>
              	
              </div>
			  <div class="clearfix"></div>
          </div>
          
		<div id="frequencyByMonthly" style="display: none;">
          <div class="form-group clearfix">
				<label class="col-md-4">Next Pay Month</label>
              <div class="col-md-8" id="nextPayrollMonth">
				  <select class="sel" id="nextPayMonth" name="nextPayMonth"  field-name="Next Pay Month">
				  </select>
              </div>
			  <div class="clearfix"></div>
          </div>
          </div>
          </div>
          <div class="form-group">
              <label class="col-md-4">Notify Before (day's)</label>
				<div class="col-md-8">
					<input type="text" data-validation="required validate_int validate_Space validate_length  length1-2" value="0" id="notifyMember" name="notifyMember" class="form-control" field-name="Notify Before" >
				</div>
            </div>
          </div>
          
          
          <div class="col-md-6">
			 <h5 class="col-md-12 clearfix margin-top0">Pay Period Frequency</h5>
			  <div class="clearfix"></div>
			  
			  <div class="form-group clearfix mt text-primary textBold" id="endDateDIV">
				<label class="col-md-4">Pay Period End Date</label>
              <div class="col-md-8">
              		<span id="payPeriodEndDate"></span>
              </div>
			  <div class="clearfix"></div>
            </div>
			  
			  <div class="form-group clearfix">
				<label class="col-md-4">Run Payroll</label> 
              <div class="col-md-8" id=runPayrollDateDiv>
                <input class="form-control calendar" placeholder="Date" type="text" id="runPayrollDate" name="runPayDate" data-validation="required" field-name="Run Payroll Date">
              </div>
			  <div class="clearfix"></div>
            </div>
            
            <div class="form-group clearfix mt">
				<label class="col-md-4">Pay Date</label>
              <div class="col-md-8" id=payCheckDateDiv>
                <input class="form-control calendar" placeholder="Date" type="text" id="payCheckDate" name="payCheckDate" data-validation="required" field-name="Pay Check Date">
              </div>
			  <div class="clearfix"></div>
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
			  
			  <div class="col-md-12 mb mt">
			  	<div class="pull-right mr">
					<button type="button" class="btn btn-primary mr" id="save_pay_schedule_button" name="save_pay_schedule_button" onclick="savePayrollSchedule();">Save</button> 
					<button type="button" class="btn btn-default" id="" name="save_pay_schedule_button" onclick="javascript:window.location.href = '/app/payrollScheduleList'">Cancel</button>  
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
	        	  $.post(sourceleadUrl+"/savePayrollSchedule?"+tokenParamName+"="+tokenValue, formData, function(result) {
	        		  if (result && result.response === "INVALID_DATA") {
		                    $("#save_pay_schedule_button").removeAttr("disabled");
		                    $("#save_pay_schedule_button").removeAttr('button').val('Save');
		                    showError(result.responseMessage);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	    $("#loaderDIV").removeAttr("style");
		                	    location.replace(sourceleadUrl+ "/payrollScheduleList"); 
			        		    $("#selector2").removeAttr("style");
				     			 $("#selector2").css({ display: "block" });
				     			 $("#msgFadeDiv").html("Added Successfully");
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
$('body').on('blur', '#add_payroll_schedule_form', function() {
	$("#add_payroll_schedule_form").showHelpOnFocus();
	$("#add_payroll_schedule_form").validateOnBlur(false, validationSettings);  
});
</script>
  



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
    		onSelect : function(selected) {
    			var dt = $(this).datepicker('getDate', '+1d');
    			dt.setDate(dt.getDate()-1);
    			$("#runPayrollDate").datepicker("option", "maxDate", dt);
// 			removeStartValid();
			removepPayCheckDate();
			}
});

$('#runPayrollDate').datepicker({
    dateFormat: finalDateFormat,
    changeMonth: true,
	  changeYear: true, 
    //minDate: 0  this line for to restrict selection of previous dates
    onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate()+1);
			$("#payCheckDate").datepicker("option", "minDate", dt);
// 			removeEndValid();
			removeRunPayrollDate();
	}
});

$('#payPeriodStartDate').datepicker({
    dateFormat: finalDateFormat,
    changeMonth: true,
	  changeYear: true, 
	  onSelect : function(selected) {
		  var dt = $(this).datepicker('getDate', '-1d');
		  var frequency = $("#payFrequency").val();
			if(frequency == "Weekly"){
				dt.setDate(dt.getDate()+6);
			}if(frequency == "Bi-Weekly"){
				dt.setDate(dt.getDate()+13);
			}
			var dd = new Date(finalDateFormat);
			dd.setDate(dt);
			var strDate = dt.toString();
			var ed = strDate.split(" ");
			var endDate = ed[0]+" "+ed[1]+" "+ed[2]+" "+ed[3];
			$("#payPeriodEndDate").html(endDate);
			$("#endDateDIV").show();
			$("#payCheckDate").datepicker("option", "minDate", dt);
			removePayPeriodStartDate();
	}
    //minDate: 0  this line for to restrict selection of previous dates  Bi-Weekly
});
	 
	
	/* function addValidClass(){
		 $('#payrollPayDateDivId').find('p.jquery_form_error_message').remove(); 
	} */
</script>

<script>
function removeStartValid(){
	$('#payCheckDate').find('p.jquery_form_error_message').remove();
}

function removeEndValid(){
	$('#runPayrollDate').find('p.jquery_form_error_message').remove();
}
</script>
<script>
function weekOnChange() {
	var frequency = $("#payFrequency").val();
	if(frequency != "Monthly"){
		$("#frequencyBy").show();
		$("#frequencyByMonthly").hide();
		$('#nextPayMonth').removeAttr('data-validation');
	    $('#nextPayMonth').attr('data-validation-optional',"true");
	    $('#nextPayrollMonth').find('p.jquery_form_error_message').remove();
	    $('#payPeriodStartDate').removeAttr('data-validation-optional');
	    $('#payPeriodStartDate').attr('data-validation',"required");
	}else {
		$("#frequencyBy").hide();
		$("#frequencyByMonthly").show();
		$('#payPeriodStartDate').removeAttr('data-validation');
	    $('#payPeriodStartDate').attr('data-validation-optional',"true");
	    $('#nextPayMonth').removeAttr('data-validation-optional');
	    $('#nextPayMonth').attr('data-validation',"required");
	    $('#payperiodDiv1').find('p.jquery_form_error_message').remove();
		
	}
	
	checkDuplicatePaySchedule(frequency);
}	
</script>
<script>
function removePayPeriodStartDate(){
	$('#payperiodDiv1').find('p.jquery_form_error_message').remove();
}

function removeRunPayrollDate(){
	$('#runPayrollDateDiv').find('p.jquery_form_error_message').remove();
}

function removepPayCheckDate(){
	$('#payCheckDateDiv').find('p.jquery_form_error_message').remove();
}
</script>


<script>
  function checkDuplicatePaySchedule(payFrequency){
	  var payrollScheduleUUID = "${payScheduleUUID}";
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/checkDuplicatePaySchedule?"+tokenParamName+"="+tokenValue+"&payFrequency="+payFrequency+"&payrollScheduleUUID="+payrollScheduleUUID, 
		        success: function(data) {
		      	  if (!data) {
		                	$('#scheduleFreDIV').find('p.jquery_form_error_message').remove();
		                	$("#save_pay_schedule_button").attr("disabled", false);
		            }  else {
							$('#scheduleFreDIV').find('p.jquery_form_error_message').remove();
		      			  	$("#payFrequency").after("<p class='jquery_form_error_message'>"+payFrequency+" already exists..!!</span>");
		      				$("#save_pay_schedule_button").attr("disabled", true);
		                }
	/* 	        var output = "";
				for (var i=0; i<data.length; i++)
				{
				var opt = data[i];
				 output = output + "<option value='" +opt.id+ "'>"+opt.fullName+"  ("+opt.role+")</option>";
				}
				$("#notifyMember").empty();
				$("#notifyMember").append(output);
				$('#notifyMember').trigger("chosen:updated"); */
				
		        },
		    });
  }
  </script>
  
  <script>
  function getPayrollSchedule(payrollScheduleUUID){
	  
	  $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/findPayrollScheduleByUUID?"+tokenParamName+"="+tokenValue+"&payrollScheduleUUID="+payrollScheduleUUID, 
	        success: function(json) {
	               
	        	   var data = json.approvers;
	        	   var monthly = json.month;
					var res = "";
					res = res + "<option value=''>Select Month</option>";
					  for (var j=0; j<monthly.length; j++)
					{
						res = res + "<option selected value='" +monthly[j]+ "'>"+monthly[j]+"</option>";
					}  
					$("#nextPayMonth").empty();
					$("#nextPayMonth").append(res);  
	        	
	        $("#payFrequency").val(json.payFrequency);
	        weekOnChange();
	        $("#payPeriodStartDate").val(json.payPeriodStartDate); 
	        $("#payCheckDate").val(json.payCheckDate);
	        $("#runPayrollDate").val(json.runPayDate); 
	        $("#nextPayMonth").val(json.nextPayMonth); 
	        $("#notifyMember").val(json.notifyMember)
	          
	        	/*   var output = "";
			for (var i=0; i<data.length; i++)
			{
			var opt = data[i];
			 output = output + "<option value='" +opt.id+ "'>"+opt.fullName+"  ("+opt.role+")</option>";
			}
			$("#notifyMember").empty();
			$("#notifyMember").append(output);
			$('#notifyMember').trigger("chosen:updated"); */
			
	        },
	    });
	  
  }
  </script>

<jsp:include page="../masterFooter.jsp" />


 		