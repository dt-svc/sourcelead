<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<!-- <script src="js/jquery-1.12.0.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/bootstrap-select.min.js"></script> 
<script src="js/chosen.jquery.min.js"></script>  -->
<link rel="stylesheet" href="/resources/css/bootstrap-clockpicker.min.css">
<script src="/resources/js/bootstrap-clockpicker.min.js"></script>


<script type="text/javascript">
//reminderFun
function reminderFun() { 
	document.getElementById("reminder").style.display="block";
}
$("#employeeonLeave").click(function() {
	$("#employeeonLeaveDiv").toggle();
});
$("#approveLeave").click(function() {
	$("#approveLeaveDiv").toggle();
});
</script>

<script>
function reminderJobClosingFun() { 
	document.getElementById("jobClosingReminder").style.display="block";
}
$("#employeeonLeave").click(function() {
	$("#employeeonLeaveDiv").toggle();
});
$("#approveLeave").click(function() {
	$("#approveLeaveDiv").toggle();
});

</script>

<script>
function hideandshow(){
	$("#employeeonLeaveDiv").toggle();
}

function hideandshowjobclosing(){
	$("#approveLeaveDiv").toggle();
}

</script>


<!-- 
<script>
	function stroeJobReminderConfiguration(){
		alert("Hello! I am an alert box!!");
	

		 /* $.ajax({
			 type : "POST",
			 data: formData,
	         processData: false,
	         contentType:false, 
			  url : sourceleadUrl+"/storeJobConfigureEmail?"+tokenParamName+"="+tokenValue+"&uuid="+uuid,
			  	success : function(result) {
			  		if(result.object === 'MissingData'){
			  			showError("Invalid data");
			  		}else{
			  			if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(sourceleadUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
						  	location.replace(sourceleadUrl+"/emailreminderConfigurationDashboard");
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
			  		}
			  	},
		}); */
	
		/* return true; */
  	}else{ 
		/* return false; */
}  
  	} */
</script> -->


<!-- Content Area --> 
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Jobs</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/jobs'/>"> Jobs</a></div>
  </section>
   <form:form id="job_ConfigueEmail_form" name="jobConfigueEmail_form"> 
  <div class="content-area content-new clearfix" style="min-height: 50px;">
    <div class="col-xs-12 col-sm-6 col-md-6 mt mb">
      <div class="row">
        <div class="col-md-3 textBold">Applicant Joining</div>
        <div class="col-md-9">
          <div class="checkbox-new">
            <input id="employeeonLeave" class="checkbox-custom" name="paidComponent" type="checkbox" onclick="hideandshow();">
            <label for="employeeonLeave" class="checkbox-custom-label"></label>
          </div>
        </div>
        <div class="clearfix"></div>
      </div>
      <div class="clearfix mt" id="employeeonLeaveDiv" style="display: none;">
        <div class="clearfix form-horizontal">
          <div class="row">
            <div class="clerfix"></div>
            <div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
              <label class="col-md-3 paddingRight">Send<sup>1st</sup> Reminder</label>
              <div class="col-md-9">
                <div class="form-icon">
                  <input type="text" class="form-control" name="firstReminder" id="firstReminder" value="">
                  <span title="Add Reminder" onClick="reminderFun();"> <i class="fa fa-plus btn btn-default formic1"></i> </span> </div>
              </div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb" id="reminder" style="display: none;">
              <label class="col-md-3 paddingRight">Send<sup>2nd</sup> Reminder</label>
              <div class="col-md-9">
                <div class="form-icon">
                  <input type="text" class="form-control" name="secondReminder" id="secondReminder" value="">
                  <span title="Add Reminder"> <i class="fa fa-times colorRed btn btn-default formic1"></i> </span> </div>
              </div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
              <label class="col-md-3 paddingRight">Time</label>
              <div class="col-md-9 clockpicker">
                <input type="text" class="form-control timeicon" name="" id="clockID" value="">
              </div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
              <label class="col-md-3">Reminder To</label>
              <div class="col-md-9">
                <select class="form-control chosen-select" data-placeholder="Select" multiple>
                  <option>Reporting Manager</option>
                  <option>Project Manager</option>
                  <option>Team Leader</option>
                </select>
              </div>
            </div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb">
              <label class="col-md-3">Notify To</label>
              <div class="col-md-9">
                <select class="form-control chosen-select" data-placeholder="Select" multiple>
                  <option>Reporting Manager</option>
                  <option>Project Manager</option>
                  <option>Team Leader</option>
                </select>
              </div>
            </div>
          
        </div>
      </div>
      <div class="clearfix mt"></div>
		<div class="row">
        <div class="col-md-3 textBold">Jobs Closing</div>
        <div class="col-md-9">
          <div class="checkbox-new">
            <input id="approveLeave" class="checkbox-custom" name="paidComponent" type="checkbox" onclick="hideandshowjobclosing();">
            <label for="approveLeave" class="checkbox-custom-label"></label>
          </div>
        </div>
        <div class="clearfix"></div>
        <div id="approveLeaveDiv" class="mt" style="display: none;">
          <div class="minHeight50 mb">
            <label class="col-md-3 paddingRight">Send<sup>1st</sup> Reminder</label>
            <div class="col-md-9">
              <div class="form-icon">
                <input type="text" class="form-control" name="firstReminder" id="firstReminder" value="">
                <span title="Add Reminder" onClick="reminderJobClosingFun();"> <i class="fa fa-plus btn btn-default formic1"></i> </span> </div>
            </div>
          </div>
           <div class="col-xs-12 col-sm-12 col-md-12 minHeight50 mb" id="jobClosingReminder" style="display: none;">
              <label class="col-md-3 paddingRight">Send<sup>2nd</sup> Reminder</label>
              <div class="col-md-9">
                <div class="form-icon">
                  <input type="text" class="form-control" name="secondReminder" id="secondReminder" value="">
                  <span title="Add Reminder"> <i class="fa fa-times colorRed btn btn-default formic1"></i> </span> </div>
              </div>
            </div>
          <div class="minHeight50 mb">
            <label class="col-md-3 paddingRight">Time</label>
            <div class="col-md-9 clockpicker">
              <input type="text" class="form-control timeicon" name="time" id="time" value="">
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="clearfix"></div>
    <div class="form-footer clearfix mt">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Save" id="saveJobConfigureBtn" class="btn btn-primary" onclick="stroeJobReminderConfiguration();"/>
          <input type="button" value="Cancel" class="btn btn-default ml10" />
        </div>
      </div>
    </div>
  </div>
  </form:form>

 
<script>
function stroeJobReminderConfiguration(){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         };
	if ($('#job_ConfigueEmail_form').validate(false, validationSettings)){
   	 $('#saveJobConfigureBtn').prop('disabled', true);
			var formData = getFormData1("job_ConfigueEmail_form");
			var uuid = '${uuid}';
	
	$.ajax({
		 type : "POST",
		 data: formData, 
		  url : sourceleadUrl+"/storeJobConfigureEmail?"+tokenParamName+"="+tokenValue+"&uuid="+uuid,
		  	success : function(result) {
		  		if(result.object === 'MissingData'){
		  			showError("Invalid data");
		  		}else{
		  			if (result.response === "SESSION_TIMEDOUT") {
		  				location.replace(sourceleadUrl);
	                } else if (result.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (result.response === "SUCCESS") {
					  	location.replace(sourceleadUrl+"/emailreminderConfigurationDashboard");
	                } else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
	                    showError("Operation failed, please try again");
	                }
		  		}
		  	},
	}); 

	 return true; 
	}else{ 
	 return false; 
} 
}
</script>

<script>
$('.clockpicker').clockpicker({
    placement: 'bottom',
    align: 'left',
    donetext: 'Done',
	//autoclose: true,
    twelvehour: true
});

</script>
<jsp:include page="../masterFooter.jsp" />