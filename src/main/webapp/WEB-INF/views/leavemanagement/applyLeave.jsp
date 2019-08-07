<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>	
	  
<script type="text/javascript">
window.onload = function() { 
	
	$("#approverUser").removeAttr("data-validation");

	var leaveUUID = '${leaveUUID}';
	if(leaveUUID != null && leaveUUID != '' && leaveUUID != 'undefined'){
// 		getLeaveDetails();
		getUsersBasedOnHierarchy('edit');
		var checkFromEdit = '${leave.fromHalfDay}';
		var checkToEdit = '${leave.toHalfDay}';
		if(checkFromEdit == 'YES'){
			$("#fromTimeDiv").show();
			$("#toTimeDiv").show();
		}
		if(checkToEdit == 'YES'){
			$("#toDateFromTimeDiv").show();
			$("#toDateTimeDiv").show();
		}
// 		validateDate();
	}
	
	getPieChartBasedOnUser();
	getApproversList();
   
	if(leaveUUID != null && leaveUUID != '' && leaveUUID != 'undefined'){
		setTimeout(function(){ getLeaveDetails(); }, 1000);
	}
   var isSuperUser = '${SourceLeadWeb_isSuperUser}';
   if(isSuperUser){
	   $("#approvalDiv").hide();
	   $("#notifyToOthersDiv").hide();
   }else{
	   $("#approvalDiv").show();
	   $("#notifyToOthersDiv").show();
   }
}
</script>

<!-- Side Nav Bar --> 
    <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Time Off</h2>
      </div>
      <div class="pull-right brud-crum"><a href="/app/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="/pms/leaveManagementReports">Leave Management</a> &raquo; Apply Leave</div>
    </section>
    <div class="content-area content-new clearfix">
     <div id="leaveLoading"></div> 
    <form:form id="apply_leave_form" name="apply_leave_form" method="post">
    <input type="hidden" id ="overDrawLeaveType" value=""/>
    <input type="hidden" id ="leaveTypeName" value=""/>
      <div class="clearfix form-horizontal inner-content">
          <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-1">
          <sec:authorize access="hasRole('ROLE_APPLY_BEHALF_LEAVE')"> 
          	<c:if test="${empty leaveUUID}">
          		<div class="form-group">
                   <label class="pull-left ml checkBox">
					<input id="checkbox-1" class="onbehalf" name="checkbox-1" type="checkbox" onClick="onbehalf(); getApproversList();">
                  </label>
                  <span class="pull-left mt5">On behalf of</span>
              </div>
          	</c:if>
          </sec:authorize> 
               <div class="form-group" style="display: none" id="onbehalf">
              <label class="col-md-3">Select Employee<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9 dropdown-select">
                <select class="form-control chosen-select" id="user" name="user" field-name="Employee" onchange="getPieChartBasedOnUser('onbehalf'); getApproversList();">
                   <option value="">--Select Employee--</option>
                	<c:forEach var="user" items="${usersLis}">
	                     <option value="${user.id}">${user.firstName} ${user.lastName}</option>
                	</c:forEach>
                </select>
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3">Leave Type<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9 dropdown-select" id="removeErrMsg">
              <div id="leaveTypeIdDiv"></div>
              </div>
             </div>
              
            <div class="form-group">
              <label class="col-md-3">From<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-md-7 paddingRight" id="fromDateDiv">
                  <div class="input-group errorInptgroup">
                    <input type="text" class="form-control calendar" id="fromDate" value="${fromDate}" name="fromDate" placeholder="From Date" field-name="From Date" data-validation="required" readonly>
                    <span class="input-group-addon" onClick="clearFromDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                   </div> 
                  </div>
                  <c:if test="${! empty leave.fromHalfDay}">
                  <div class="col-md-5 paddingLeft"  id="fromHalfDayDiv">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left mr10">
					<input id="checkbox-2" checked class="pull-left" name="fromHalfDay"  type="checkbox" onclick="checkFromHalfDay();validateFromHalfDay();">
                      <span class="pull-left mt5">Half a Day</span></label>
                  </div>
                  </c:if>
                  
                  <c:if test="${empty leave.fromHalfDay}">
                  <div class="col-md-5 paddingLeft"  id="fromHalfDayDiv">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left mr10">
					<input id="checkbox-2" class="pull-left" name="fromHalfDay"  type="checkbox" onclick="checkFromHalfDay();validateFromHalfDay();">
                      <span class="pull-left mt5">Half a Day</span></label>
                  </div>
                  </c:if>
                   <div class="clearfix"></div>
                  <div id="fromTimeDiv" class="col-md-4 mt5 clearfix errorInptgroup" style="display:none;">
                <div class="clearField errorInptgroup" id="startTimeDiv">
        			<input type="text" class="timepicker form-control" id="fromDateHalfTime" name="fromDateFromHalfTime" placeholder="From-Time" field-name="From-Time" value="${leave.fromDateFromHalfTime}" onblur="validateFromTimings()">
        				<i class="fa fa-times text-muted small"></i>
        		</div>
        		</div>
				<div id="toTimeDiv" class="mt5 col-md-4 clearfix errorInptgroup" style="display:none;">
                  <div class="clearField" id="toEndTimeDiv">
        			<input type="text" class="timepicker form-control" id="toDateHalfTime" name="fromDateToHalfTime" placeholder="To-Time" field-name="To-Time" value="${leave.fromDateToHalfTime}" onblur="validateToTimings()" data-validation-optional = "true">
        				<i class="fa fa-times text-muted small"></i>
        		</div>
                  </div>
        		
                </div>
              </div>
            </div>
              <div class="form-group" id="halfDayHidingDiv" style="display: block;">
              <label class="col-md-3">To<span class="text-danger paddingLeft">*</span></label>
              <div class="col-md-9">
                <div class="row">
                  <div class="col-md-7 paddingRight" id="toDateDiv">
                    <div class="input-group errorInptgroup">
                     <input type="text" class="form-control calendar" id="toDate" name="toDate" value="${toDate}" placeholder="To Date" field-name="To Date" data-validation="required" readonly>
                     <span class="input-group-addon" onClick="clearToDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
                    </div>
                  </div>
                   <c:if test="${! empty leave.toHalfDay}">
                  <div class="col-md-5 paddingLeft" id="toHalfDayDiv">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left">
					<input id="checkbox-3" checked class="pull-left" name="toHalfDay" type="checkbox" onclick="checkToHalfDay();validateToHalfDay();">
                  </label>
                      <span class="pull-left mt5">Half a Day</span></label>
                  </div>
                  </c:if>
                  <c:if test="${empty leave.toHalfDay}">
                  <div class="col-md-5 paddingLeft" id="toHalfDayDiv">
                    <label class="checkBox mt5">
                      <label class="checkBox pull-left">
					<input id="checkbox-3" class="pull-left" name="toHalfDay" type="checkbox" onclick="checkToHalfDay();validateToHalfDay();">
                  </label>
                      <span class="pull-left mt5">Half a Day</span></label>
                  </div>
                  </c:if>
                   
                  <div class="clearfix"></div>
                  <div id="toDateFromTimeDiv" class="mt10 clearfix errorInptgroup col-md-4" style="display:none;">
                <div class="clearField errorInptgroup" id="toDateStartTime">
        			<input type="text" class="timepicker form-control" id="toDateFromHalfTime" name="toDateFromHalfTime" placeholder="From-Time" field-name="From-Time" value="${leave.toDateFromHalfTime}" onblur="validateToDateFromTimings()" data-validation-optional = "true">
        				<i class="fa fa-times text-muted small"></i>
        		</div>
        		</div>
        		
				<div id="toDateTimeDiv" class="col-md-4 clearfix errorInptgroup mt10" style="display:none;">
                  <div class="clearField" id="toDateToTime">
        			<input type="text" class="timepicker form-control" id="toDateToHalfTime" name="toDateToHalfTime" placeholder="To-Time" field-name="To-Time" value="${leave.toDateToHalfTime}" onblur="validateToDateToTimings()" data-validation-optional = "true">
        				<i class="fa fa-times text-muted small"></i>
        		</div>
                  </div>
        		
                </div>
              </div>
            </div>
              
            <div class="form-group" id="durationDiv" style="display:none">
             	<label class="col-md-12" id="durationId" name="durationHrs"></label>
            </div>
              
            <div class="form-group">
              <label class="col-md-3" id="reasonIdY" style="display:block">Reason<span class="text-danger paddingLeft" >*</span></label>
              <label class="col-md-3" id="reasonIdN" style="display:none">Reason</label>
              <div class="col-md-9" id="reasonId">
                <textarea class="form-control" rows="2" placeholder="Reason" id="reason" name="reason" field-name="Reason" data-validation="required validate_length length1-500">${leave.reason}</textarea>
              </div>
            </div>
              
              <div class="form-group">
              <label class="col-md-3" id="attachmentY" style="display:block">Attachment<span class="text-danger paddingLeft" >*</span></label>
              <label class="col-md-3" id="attachmentN" style="display:none">Attachment</label>
              <div class="col-md-9 errorInptgroup" id="attachmentId">
                  <div class="positionRelative clearfix"><i class="clearUpload" title="clear" onClick="removedocfile();"></i>
                  
                  <div class="input-group ratetype col-md-12 pull-left" title="" id="uploadFileGroup">
	                      <label class="input-group-btn"> 
	                        <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Choose File
	                          <input type="file" id="uploadBtn" style="display: none;" class="upload">
	                        </span> 
	                      </label>
		                      <input type="text" class="form-control" readonly id="uploadFile" value="${leave.fileName}" placeholder="no file chosen" field-name="Attachment" data-validation="required">
	              </div>
                  </div>
              </div>
            </div>
            
<%--             <c:if test="${SourceLeadWeb_isSuperUser != true}"> --%>
            	<div class="form-group" id="approvalDiv">
	              <label class="col-md-3">Approval By<span class="text-danger paddingLeft">*</span></label>
	              <div class="col-md-9 dropdown-select" id="approverUserDiv">
	                  <select  multiple data-placeholder="Select Approver" class="chosen-select form-control" id="approverUser" name="approverUser" field-name="Approver" data-validation ="required">
	                  </select>
	              </div>
            	</div>
<%--             </c:if> --%>

		<div class="clearfix"></div>
		 <sec:authorize access="hasAnyRole('ROLE_LEAVE_NOTIFY_TO_OTHTERS')"> 
			<c:if test="${!empty leave.notifyToOthers}">
	         <div class="form-group" id="notifyToOthersDiv">
					<div class="ml">
								<input  type="checkbox" checked name="notifyToOthers" id="notifyOthersId" value="Y" onclick="getUsersBasedOnHierarchy();">
						<label>&nbsp;&nbsp;Notify others
							&nbsp;&nbsp;
						</label>
					</div>
				</div>
				</c:if>
				
			<c:if test="${empty leave.notifyToOthers}">
	         <div class="form-group" id="notifyToOthersDiv">
					<div class="ml">
								<input  type="checkbox" name="notifyToOthers" id="notifyOthersId" value="Y" onclick="getUsersBasedOnHierarchy();">
						<label>&nbsp;&nbsp;Notify others
							&nbsp;&nbsp;
						</label>
					</div>
				</div>
			</c:if>
			</sec:authorize>
			    <div class="form-group clearfix" id="notifyOtherDiv" style="display:none;">
              <label class="col-md-3">&nbsp;&nbsp;</label>
              <div class="col-md-9">
              	<select  multiple data-placeholder="Select Approver" class="chosen-select form-control" id="approverId" name="otherApproverUser" field-name="Approver" data-validation-optional = "true">
	                  </select>
              </div>
             </div>
		
              
          </div>
          <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4" oncontextmenu="return false" onkeydown="return false;" onmousedown="return false;">
            <div class="well-sm well well-custom clearfix amchartAnchor-Hide">
                 <h5>Leaves Status</h5>
                <div id="leavestatsu" style="width: 100%; height: 150px;"></div>
                <div class="row">
                <div id="leaveStatusIdDiv"></div>
                </div>
                
            </div>
          </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Apply" id="ApplyLeave" class="btn btn-primary ml mr" onclick="applyLeave();">
            <input type="button" value="Cancel" class="btn btn-default" onclick="cancel();">
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends-->
      </form:form> 
    </div>
  </section>
  
 <script>
 function removedocfile(){
	 $("#uploadFile").val('');
 }
 </script> 
 <script>
 function checkFromHalfDay(){
	 
    $("#checkbox-2").val('YES');
    
	   if($("#checkbox-2").is(':checked')){
		   $("#fromTimeDiv").show();
		   $("#toTimeDiv").show();
	   }else{
		   $("#fromTimeDiv").find('p.jquery_form_error_message').remove();
		   $("#toTimeDiv").find('p.jquery_form_error_message').remove();
    	   $("#fromTimeDiv").hide();
    	   $("#toTimeDiv").hide();
    	   $("#fromDateHalfTime").val('');
    	   $("#toDateHalfTime").val('');
//     	   $("#toDateHalfTime").val('');
   	   }	
    
 }
 
 function checkToHalfDay(){
	
	 $("#checkbox-3").val('YES');
	 
	 if($("#checkbox-3").is(':checked')){
		   $("#toDateFromTimeDiv").show();
		   $("#toDateTimeDiv").show();
	   }else{
		   $("#toDateFromTimeDiv").find('p.jquery_form_error_message').remove();
		   $("#toDateTimeDiv").find('p.jquery_form_error_message').remove();
	  	   $("#toDateFromTimeDiv").hide();
	  	   $("#toDateTimeDiv").hide();
	  	   $("#toDateFromHalfTime").val('');
	  	 $("#toDateToHalfTime").val('');
// 	  	   $("#toDateHalfTime").val('');
 	   } 
 }
  function validateFromHalfDay()
  {
// 	  $("#toHalfDayDiv").hide();
		 var fromDate = $("#fromDate").val();
		 var toDate = $("#toDate").val();
		 if(fromDate.length !=0 && toDate.length!=0)
	    {
		 if(fromDate == toDate)
		 {
			 $("#toHalfDayDiv").hide();
			 $("#toDateFromTimeDiv").hide();
			 $("#toDateTimeDiv").hide();
			 $("#toDateFromHalfTime").val('');
			 $("#toDateToHalfTime").val('');
		 }
		else
			{
			 $("#toHalfDayDiv").show();
			}
		 
		
	    }
	  
  }
  function validateToHalfDay()
  {
// 	  $("#fromHalfDayDiv").hide();
		 var fromDate = $("#fromDate").val();
		 var toDate = $("#toDate").val();

		 if(fromDate.length !=0 && toDate.length !=0)
		    {
		 if(fromDate == toDate)
		 {
			$("#fromHalfDayDiv").hide();
			$("#fromTimeDiv").hide();
			 $("#toTimeDiv").hide();
			 $("#fromDateHalfTime").val('');
			 $("#toDateHalfTime").val('');
			
		  }
		   else
			{
				$("#fromHalfDayDiv").show();
			}
		 
		
		    } 
   }
 function checkFromDate(){
	 var fromDate = $("#fromDate").val();
	 var toDate = $("#toDate").val();
	 
	 if(fromDate.length !=0 && toDate.length!=0)
	    {
		 if(fromDate == toDate && ($("#checkbox-3").is(':checked')) && ($("#checkbox-2").is(':checked'))){
			 $("#checkbox-3").prop('checked', false);
			 $("#toHalfDayDiv").hide();
			 $("#toDateFromTimeDiv").hide();
			 $("#toDateTimeDiv").hide();
			 $("#toDateFromHalfTime").val('');
			 $("#toDateToHalfTime").val('');
		 }else{
			 if(fromDate == toDate && ($("#checkbox-3").is(':checked')) )
			 {
				 $("#fromHalfDayDiv").hide();
			 }
			else
				{
				$("#fromHalfDayDiv").show();
				}
			 if(fromDate == toDate && ($("#checkbox-2").is(':checked')) )
			 {
				 $("#toHalfDayDiv").hide();
			 }
			else
				{
				$("#toHalfDayDiv").show();
				}
		 }
	    }
	 
 }
  function checkToDate()
  {
	  
	  var fromDate = $("#fromDate").val();
	  var toDate = $("#toDate").val();
	  if(fromDate.length !=0 && toDate.length!=0)
	  {
		 if(fromDate == toDate && ($("#checkbox-3").is(':checked')) && ($("#checkbox-2").is(':checked'))){
			 $("#checkbox-3").prop('checked', false);
			 $("#toHalfDayDiv").hide();
			 $("#toDateFromTimeDiv").hide();
			 $("#toDateTimeDiv").hide();
			 $("#toDateFromHalfTime").val('');
			 $("#toDateToHalfTime").val('');
	  }
	 else
	 {
		 if(fromDate == toDate && ($("#checkbox-2").is(':checked')))
		  {
			 $("#toHalfDayDiv").hide();
		   }
		  else
		  {
			$("#toHalfDayDiv").show();
		  }
		 if(fromDate == toDate && ($("#checkbox-3").is(':checked')))
		 {
			 $("#fromHalfDayDiv").hide();
		 }
		 else
		 {
			$("#fromHalfDayDiv").show();
		 }
	 }
   } 
	  
	  
  }
 </script>
  
  
  <script>
	$(function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEndDate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatEndDate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatEndDate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatEndDate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatEndDate = 'yy-dd-mm'; 
		  }
		//to set from date to current month
	       var currentTime = new Date()
	       var minDate = new Date(currentTime.getFullYear(), currentTime.getMonth(), +1);
		$('#fromDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true,
// 			minDate: 0,
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate());
			$("#toDate").datepicker("option", "minDate", dt);
			removeStartValid();
			checkFromDate();
			calculateDuration();
// 			validateDate();
			getLeaveDetails(finalDateFormatEndDate);
			}
		});
		
	  $('#toDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate());
			$("#fromdate").datepicker("option", "maxDate", dt);
			removeEndValid();
			checkToDate();
			getLeaveDetails(finalDateFormatEndDate);
// 			 $('#leaveType').prop('disabled', false).trigger("chosen:updated");
			}
		});
	});
</script>

<script>
function removeStartValid(){
	$('#fromDateDiv').find('p.jquery_form_error_message').remove();
}

function removeEndValid(){
	$('#toDateDiv').find('p.jquery_form_error_message').remove();
}

function calculateDuration(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	if(toDate == ""){
		toDate = $("#fromDate").val();
	}
	var leaveTypeUUID = $("#leaveType").val();
	var ckbox = $('#checkbox-1');
	if (ckbox.is(':checked')) {
		 userId = $("#user").val();
	 }else{
		 userId = '${SourceLeadWeb_userId}';
	 }
	
	var fromHalfcheck = $('#checkbox-2');
	var toHalfcheck = $('#checkbox-3');
	var fromHalfDay = "";
	var toHalfDay = "";
	if (fromHalfcheck.is(':checked')) {
		fromHalfDay = "Yes";
	}
	if (toHalfcheck.is(':checked')) {
		toHalfDay = "Yes";
	}
	var fromDateFromHalfTime = $("#fromDateHalfTime").val();
	var fromDateToHalfTime = $("#toDateHalfTime").val();  
	var toDateFromHalfTime = $("#toDateFromHalfTime").val();
	var toDateToHalfTime = $("#toDateToHalfTime").val();
	
	if(fromDate != null && fromDate != '' && fromDate != 'undefined' && leaveTypeUUID != null && leaveTypeUUID != '' && leaveTypeUUID != 'undefined'){
		$.get(pmsUrl+"/calculateLeaveDuration?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&leaveType="+leaveTypeUUID+"&userId="+userId+
				"&fromHalfDay="+fromHalfDay+"&toHalfDay="+toHalfDay+"&fromDateFromHalfTime="+fromDateFromHalfTime+
				"&fromDateToHalfTime="+fromDateToHalfTime+"&toDateFromHalfTime="+toDateFromHalfTime+
				"&toDateToHalfTime="+toDateToHalfTime,  function(result) {
			if(result.object != null){
				$("#durationDiv").show();
				$("#durationId").html("Duration : "+result.object+" day(s)");
				validateOverDrawLeaves(result.object);
			}
		}); 
	}else{
		$("#durationDiv").hide();
		$("#durationId").html("Duration : 0 day(s)");
	}
	
}
</script>


<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script> 
 		
 		
 <script type="text/javascript">
 function getPieChartBasedOnUser(onbehalf){
	 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	 var leaveId = '${leave.leaveType.id}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 var userId ='';
	 if(onbehalf !=null && onbehalf !='' && onbehalf !='undefined'){
		 userId = $("#user").val();
	 }else{
		 userId = '${SourceLeadWeb_userId}';
	 }
	 if(userId != null && userId !='' && userId !='undefined'){
	 $.ajax({
         type : "GET",
          url: pmsUrl+"/getPieChartForLeaveStatus?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId,    
          success :function(json) {
        	  var output = "";
        	  output = output +'<select class="form-control" id="leaveType" name="leaveType" field-name="Leave Type" data-validation="required" onchange="getLeaveDetails();validateMaxLeave(this);">';
        	  output = output +' <option value="">--Select Leave Type--</option>';
        	  for (var i = 0; i < json.length; i++) {
        		  var leave = json[i];
        		  if(leave.leaveTypeBalanceLeaveCount < 0 && leave.overdrawLeaves == 'N'){
        			 
        		  }else{
        			  if(leave.id == leaveId){
            			  output = output +'<option selected value='+leave.leaveTypeUUID+'>'+leave.name+'</option>';
            		  }else{
            			  output = output +'<option value='+leave.leaveTypeUUID+'>'+leave.name+'</option>';
            		  }
        		  }
        		  
        	/* 	  if((leave.leaveTypeBalanceLeaveCount > 0 && leave.overdrawLeaves == 'N') || (leave.leaveTypeBalanceLeaveCount <= 0 && leave.overdrawLeaves == 'Y') || (leave.leaveTypeBalanceLeaveCount > 0 && leave.overdrawLeaves == 'Y')){
     			         if(leave.id == leaveId){
     			            	output = output +'<option selected value='+leave.leaveTypeUUID+'>'+leave.name+'</option>';
     			         }else{
     			            	output = output +'<option value='+leave.leaveTypeUUID+'>'+leave.name+'</option>';
     			         }
        	  	  } */
        	  }
        	  output = output +'</select>';
        	  $("#leaveTypeIdDiv").empty();
        	  
        	  $("#leaveTypeIdDiv").append(output);
        	  $('#leaveType').chosen();
        	  $("#leaveType").trigger("chosen:updated");
//         	  $('#leaveType').prop('disabled', true).trigger("chosen:updated");
        	  
        	  if(json.length > 0){
        		  var counts = json[json.length-1];
            	  var totalBalanceCounts = counts.totalBalanceLeaveCounts;
            	  var totalPendingCounts = counts.pendingLeaveCounts;
            	  var totalTakenCounts = counts.takenLeaves;
        		  getpieChar(totalBalanceCounts.toFixed(2),totalPendingCounts,totalTakenCounts);
        		  
        		  
        		  var result = "";
    	    	  result = result +' <ul class="list-unstyled leavelist col-md-10 mt25 col-md-offset-1 list-group">';
    	    	  result = result +'			<label>Balance Leaves :</label>';
    	          for (var i = 0; i < json.length; i++) { 
    	        		  var leaveType = json[i];
    	        		  if(leaveType.visibleInBalanceLeaves == 'Y'){
    	        			  result = result +'	<li>';
      				  		result = result +'			<label>'+leaveType.name+'</label>';
      				  		var leaveTypeName = leaveType.name.replace(/ /g,"_");
      				  		result = result +'			<span class="label label-info pull-right" id="'+leaveTypeName+'Span" style="background-color: '+leaveType.colorCode+';" >'+leaveType.leaveTypeBalanceLeaveCount.toFixed(2)+'</span>';
      				  		result = result +'	</li>'; 
    	        		  }
    				       
    	        	  }
   	        	  result = result +'	</ul>'; 
   	        	  $("#leaveStatusIdDiv").empty();
   	        	  $("#leaveStatusIdDiv").append(result);
        		  
        	  }else{
        		  var res = ""
	        		  res = res +'<div class="text-center mt25 well"><i class="fa fa-calendar-minus-o fa-5x text-muted"></i>';
	        		  res = res +'<p class="clearfix mt text-muted small">No Active Leave Types Found !</p>';
	        		  res = res +'</div>';
        		  $("#leavestatsu").empty();
        		  $("#leavestatsu").append(res);
        	  }
        	  
         },
          
     });
	}
	 
function getpieChar(totalBalanceCounts,totalPendingCounts,totalTakenCounts){
	//ApplyLeves piechart
	//
	AmCharts.makeChart("leavestatsu",
		{
			"type": "pie",
			"balloonText": "[[title]]<br><span style='font-size:14px'><b>[[value]]</b></span>",
			"innerRadius": "40%",
			"minRadius": 0,
			"pieX": "50%",
			"pieY": "50%",
			"pullOutRadius": "0%",
			"startRadius": "0%",
			"colors": [],
			"colorField": "color",
			"labelsEnabled": false,
			"marginBottom": 0,
			"marginLeft": 2,
			"marginTop": 0,
			"maxLabelWidth": 0,
			"titleField": "category",
			"valueField": "column-1",
			"allLabels": [],
			"balloon": {},
			"titles": [],
			"dataProvider": [
				{
					"category": "Pending Leaves",
					"column-1": totalPendingCounts,
					"color"	  : "#337ab7"
				},
				{
					"category": "Balance Leaves",
					"column-1": totalBalanceCounts,
					"color"	  : "#5bc0de"
				},
				{
					"category": "Taken Leaves",
					"column-1": totalTakenCounts,
					"color"	  : "#5cb85c"
				}
			]
		}				
	);
}
 }
</script>
		
		
    <script>
    function approvedlevel(){
        $(".approvedlevel").toggle();
    }
    </script>

<script>
function onbehalf() { 
	var ckbox = $('#checkbox-1');
	if (ckbox.is(':checked')) {
		$("#onbehalf").show();
	    $("#user").attr('data-validation',"required");
	    $("#fromDate").val('');
	    $("#toDate").val('');
	}else{
		$("#onbehalf").hide();
		$("#user").val('');
		$('#user').trigger("chosen:updated");
	    $("#user").removeAttr('data-validation');
	    $("#fromDate").val('');
	    $("#toDate").val('');
	    getPieChartBasedOnUser();
	    var isSuperUser = '${SourceLeadWeb_isSuperUser}';
	    if(isSuperUser){
	 	   $("#approvalDiv").hide();
	    }else{
	 	   $("#approvalDiv").show();
	    }
	}
    
}
</script>


<script>
var selectedFile = document.getElementById("uploadBtn");
selectedFile.addEventListener("change", function() {
	 $("#uploadFile").focus();
	 if(validate_fileupload(this.value)){
		 var docSize =  $("#uploadBtn")[0].files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
// 	    	  $('#leaveLoading').removeAttr('style');
	    	  document.getElementById("uploadFile").value = '';
	    	  $('#uploadFileGroup').find('p.jquery_form_error_message').remove();
			  $("input#uploadFile").after("<p class='jquery_form_error_message'>File size should be less then 2MB </span>");
			  document.getElementById("uploadFile").setAttribute('record-exist','yes');
			  document.getElementById("uploadFile").setAttribute('record-exist-errorMsg','File size should be less then 2MB');
			  document.getElementById("uploadFile").value="";
	      }else{
	    	 $('input#uploadFile').removeAttr( "record-exist record-exist-errormsg");
  		 $('#uploadFileGroup').find('p.jquery_form_error_message').remove(); 
	    }
	 }
});

/* upload file script */
document.getElementById("uploadBtn").onchange = function () {
	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''));
};

</script>
<script type="text/javascript">
function validate_fileupload(fileName)
{
    var allowed_extensions = new Array("docx","doc","pdf","rtf");
    var file_extension = fileName.split('.').pop(); // split function will split the filename by dot(.), and pop function will pop the last element from the array which will give you the extension as well. If there will be no extension then it will return the filename.

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i]==file_extension)
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>

<script>
$(function() {
	$("#uploadBtn").change(function() {
		fileUpload("uploadBtn", "leaveDocuments", "apply_leave_form");
	});
 });
</script>

<script type="text/javascript">
function applyLeave(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	if($("#checkbox-2").is(':checked')){
		var fromDateFromHalfTime = $("#fromDateHalfTime").val();
		if(fromDateFromHalfTime != null && fromDateFromHalfTime != '' && fromDateFromHalfTime != 'undefined'){
			$("#fromDateHalfTime").removeAttr('data-validation');
		}else{
			$("#fromDateHalfTime").removeAttr("data-validation-optional");
			$("#fromDateHalfTime").attr("data-validation","required");
		}
		
		var toDateHalfTime = $("#toDateHalfTime").val();
		if(toDateHalfTime != null && toDateHalfTime != '' && toDateHalfTime != 'undefined'){
			$("#toDateHalfTime").removeAttr('data-validation');
		}else{
			$("#toDateHalfTime").removeAttr("data-validation-optional");
			$("#toDateHalfTime").attr("data-validation","required");
		}
	}
	if($("#checkbox-3").is(':checked')){
		var toDateFromHalfTime = $("#toDateFromHalfTime").val();
		if(toDateFromHalfTime != null && toDateFromHalfTime != '' && toDateFromHalfTime != 'undefined'){
			$("#toDateFromHalfTime").removeAttr('data-validation');
		}else{
			$("#toDateFromHalfTime").removeAttr("data-validation-optional");
			$("#toDateFromHalfTime").attr("data-validation","required");
		}
		
		var toDateToHalfTime = $("#toDateToHalfTime").val();
		if(toDateToHalfTime != null && toDateToHalfTime != '' && toDateToHalfTime != 'undefined'){
			$("#toDateToHalfTime").removeAttr('data-validation');
		}else{
			$("#toDateToHalfTime").removeAttr("data-validation-optional");
			$("#toDateToHalfTime").attr("data-validation","required");
		}
	}
		var leaveId = '${leave.id}';
		var currentStage = '${currentStage}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var userId = '${SourceLeadWeb_userId}';
		if ($('#apply_leave_form').validate(false, validationSettings)) {
// 	  	var formData = $($('#apply_leave_form')[0]).serialize();
	  	var formData = getFormData('apply_leave_form');
		$("#leaveLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.post(pmsUrl+"/saveApplyLeave?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&leaveId="+leaveId+"&currentStage="+currentStage, formData, function(result) {
			if (result && result.response === "INVALID_DATA") {
    		    showError(result.responseMessage);
    		} else if (result.response === "AWKWARD") {
    		    location.href = "/errorPage";
    		} else if (result.response === "SUCCESS") {
    			location.replace(pmsUrl+ "/leaveManagementReports");
    		} else if (result.indexOf('Sign In') !== -1) {
    			location.replace(pmsUrl);
    		} else {
    		    showError("Operation failed, please try again");
    		}
		}); 
		return true;
		} else {
			return false;
		}
}
$('body').on('blur', '#apply_leave_form', function() {
	$("#apply_leave_form").showHelpOnFocus();
	$("#apply_leave_form").validateOnBlur(false, validationSettings);
});
</script>

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 

<script>
function cancel(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	location.replace(pmsUrl+ "/leaveManagementReports");
}
</script>

 <!-- Modal -->
  <div class="terms-conditions modal" id="termsModel" style="display:none;">
    <div class="modal-dialog modal-lg">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Terms &amp; Conditions</h4>
        </div>
        <div class="modal-body">
          <p id="termsAndConditions"></p>
        </div>
      </div>
      
    </div>
  </div>
  
 <script>
$(function(){
$(".close").click(function(){
	$(".terms-conditions.modal").fadeOut();
});
  });
</script>

<script>
function getLeaveDetails(finalDateFormatEndDate){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
// 	var editLeaveTypeUUID = '${leave.leaveType.leaveTypeUUID}';
	var leaveTypeUUID = '';
// 	if(editLeaveTypeUUID != null && editLeaveTypeUUID != '' && editLeaveTypeUUID != 'undefined'){
// 		 leaveTypeUUID = editLeaveTypeUUID;
// 	}else{
		 leaveTypeUUID = $("#leaveType").val();
// 	}
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var skipPolicy = false;
	var policy = $("#termsAndConditions").text();
	if(policy != null && policy != '' && policy !='undefined'){
		skipPolicy = true;
	}
	var isSuperUser = '${SourceLeadWeb_isSuperUser}';
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
	errorMessage = "";
	if(leaveTypeUUID !=null && leaveTypeUUID != '' && leaveTypeUUID !='undefined'){
		$.ajax({
		   	type: "POST",
		   	url: pmsUrl+"/leaveTypeSummary?&leaveTypeUUID="+leaveTypeUUID+"&loginUserId="+userId,	 
		    success: function(result) {
		    	if (result && result.response === "INVALID_DATA") {
          		    showError(result.responseMessage);
          		} else if (result.response === "AWKWARD") {
          		    location.href = "/errorPage";
          		} else if (result.response === "SUCCESS") {
          			var report = result.object;
          			
          			var duration = report.duration;
          			var splitDuration = duration.split(",");
          			
          			if(splitDuration[0] == "Full Day" && splitDuration[1] == "Half Day"){
    		    		$("#fromHalfDayDiv").show();
    		    		$("#toHalfDayDiv").show();
//     		    		$("#checkbox-2").removeAttr("checked");
//     		    		$("#checkbox-3").removeAttr("checked");
    		    		$("#toDate").attr('data-validation');
    		    		document.getElementById("halfDayHidingDiv").style.display = 'block';
    		    		$("#toDate").attr('data-validation',"required");
    		    	}else if(duration == "Half Day"){
    		    		$("#fromHalfDayDiv").show();
    		    		$("#toHalfDayDiv").show();
    		    		$("#checkbox-2").prop("checked",true);
    		    		$("#checkbox-2").prop('disabled', true);
    		    		checkFromHalfDay();
    		    		document.getElementById("halfDayHidingDiv").style.display = 'none';
    		    		$("#toDate").removeAttr('data-validation');
    		    	}else if(duration == "Full Day"){
    		    		$("#checkbox-2").removeAttr("checked");
    		    		$("#checkbox-3").removeAttr("checked");
    		    		$("#fromHalfDayDiv").hide();
    		    		$("#toHalfDayDiv").hide();
    		    		$("#fromTimeDiv").hide();
     		 		    $("#toTimeDiv").hide();
     		 		    $("#toDateFromTimeDiv").hide();
     					$("#toDateTimeDiv").hide();
     					document.getElementById("halfDayHidingDiv").style.display = 'block';
     					$("#toDate").attr('data-validation',"required");
    		    	} 
    		    	
    		    	if(report.reasonRequired == "Y"){
    			 		$("#reason").attr('data-validation',"required");
    			 		document.getElementById("reasonIdY").style.display = 'block';
    			 		document.getElementById("reasonIdN").style.display = 'none';
    		    	}else{
    		    		document.getElementById("reasonIdY").style.display = 'none';
    			 		document.getElementById("reasonIdN").style.display = 'block';
    		    		$("#reason").removeAttr('data-validation');
    		    	}
    		    	
    		    	if(report.attachmentRequired == "Y"){
    			 		$("#uploadFile").attr('data-validation',"required");
    			 		document.getElementById("attachmentY").style.display = 'block';
    			 		document.getElementById("attachmentN").style.display = 'none';
    		    	}else{
    		    		document.getElementById("attachmentY").style.display = 'none';
    		    		document.getElementById("attachmentN").style.display = 'block';
    		    		$("#uploadFile").removeAttr('data-validation');
    		    	}
    		    	
    		    	if(report.userGender == "Not Allow"){
    		    		errorMessage += "Selected LeaveType is not allowed to apply for your Gender";
    		    	}
    		    	
    		    	if(report.autoApprove == "Y"){
    		    		$("#approvalDiv").hide();
    		    		$("#notifyToOthersDiv").hide();
    		    		
    		    	}else{
    		    		if(userId === '${SourceLeadWeb_userId}'){
    		    			if(isSuperUser == 'true'){
    	   	   		    		   $("#approvalDiv").hide();
    	   	   		    			$("#notifyToOthersDiv").hide();
    	   	   		    	   }else{
    	   	   		    		   $("#approvalDiv").show();
    	   	   		    		   $("#notifyToOthersDiv").show();
    	   	   		    	   }
    		    		}else{
	   	   		    		 $("#approvalDiv").show();
    		    			 $("#notifyToOthersDiv").show();
	   	   		    	   }
    		    	}
    		    	
    		    	if(report.leaveDuration == "Y" && fromDate != "" && toDate != ""){
    		    		validateMinMaxDays(report.minDays, report.maxDays);
    		    	}
    		    	$("#overDrawLeaveType").val(report.overdrawLeaves);
    		    	$("#leaveTypeName").val(report.name);
//     		    	alert(report.overdrawLeaves)
//     		    	if(report.overdrawLeaves != null && report.overdrawLeaves != '' && report.overdrawLeaves != 'undefined' && report.overdrawLeaves == "N"){
//     		    		validateOverDrawLeaves(report.name);
//     		    	}
    		    	
    		    	if(report.priorLeaveApplying == "Y" && fromDate != ""){
    		    		validatePriorDays(report.priorDays,finalDateFormatEndDate);
    		    	}
    		    	
    		    	
    		    	if(skipPolicy != true){
    		    		if(report.policyDescription !=null && report.policyDescription !='' && report.policyDescription !='undefined'){
    		 				$("#termsAndConditions").html(report.policyDescription);
    		 				document.getElementById("termsModel").style.display="block";
    		 			}
    		    	}
          		} else if (result.indexOf('Sign In') !== -1) {
          			location.replace(pmsUrl);
          		} else {
          		    showError("Operation failed, please try again");
          		}
		    	
		    	
		    }
		});
	}
	calculateDuration();
	setTimeout(function(){ validateDate(); checkToDate();}, 800);
		
	
}

</script>

<script>
function validateOverDrawLeaves(totalNumberOfDays){
	var leaveTypeName = $("#leaveTypeName").val();
	var overDrawLeaveType = $("#overDrawLeaveType").val();
	var from = $("#fromDate").val();
	var to = $("#toDate").val();
  	var one_day=1000*60*60*24;
	
	var date1 = new Date(from);
	var date2 = new Date(to);
	
	
// 	var date1 = new Date(parts[2], parts[1] - 1, parts[0]); 
	console.log(date1.getTime());
// 	var date2 = new Date(parts1[2], parts1[1] - 1, parts1[0]); 
	console.log(date2.getTime());
	
	// Convert both dates to milliseconds
	var date1_ms = date1.getTime();
	var date2_ms = date2.getTime();

	// Calculate the difference in milliseconds
	var difference_ms = date2_ms - date1_ms;
	    
	// Convert back to days and return
	var days = Math.round(difference_ms/one_day);
	
	var typeName = leaveTypeName.replace(/ /g,"_");
	var typeIdBalCount = parseFloat($("#"+typeName+"Span").text());
	if(totalNumberOfDays > typeIdBalCount){
		errorMessage += "You Have Not Enough Leaves To Apply.";
		$("#ApplyLeave").attr('disabled','disabled');
	}
	
	
	
/* 	
	if(to != ""){
// 		var timeDiff = Math.abs(date2.getTime() - date1.getTime());
// 		var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
		var typeName = leaveTypeName.replace(/ /g,"_");
		var typeIdBalCount = parseFloat($("#"+typeName+"Span").text());
		if(((days+1) >= typeIdBalCount) || typeIdBalCount ==  0){
			errorMessage += "You Have Not Enough Leaves To Apply.";
		}
	} */
}
</script>
<script>
var errorMessage = "";
function validatePriorDays(priorDays,finalDateFormatEndDate){
	var currentDate = new Date();
	
	$( "#fromDate" ).datepicker({ dateFormat: finalDateFormatEndDate });
	var start = $('#fromDate').datepicker('getDate');
    var diffDays = parseInt((start - currentDate) / (1000 * 60 * 60 * 24)); 
    if(priorDays > diffDays){
    	errorMessage += "You need to apply this Leave Type "+priorDays+" earlier.";
    }
}

</script>

<script>
function validateMinMaxDays(minDays, maxDays){
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
    var convertFromDate = parseInt(fromDate);
    var convertToDate = parseInt(toDate);
    var dayDiff= (convertToDate - convertFromDate+1);
    if(!(maxDays >= dayDiff && dayDiff >= minDays)){
    	errorMessage += "You need to apply this Leave Type for atleast Min "+minDays+"days and Max "+maxDays+"days.";
    }
}

</script>

<script>
function validateMaxLeave(data){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	$('.jquery_form_error_message cOrange').hide();
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var leaveTypeId = $("#leaveType").val();
	if(userId != null && userId !='' && userId !='undefined'){
	if(leaveTypeId !=null && leaveTypeId != '' && leaveTypeId !='undefined'){
		var leaveTypeName = data.options[data.selectedIndex].innerHTML;
		$.get(pmsUrl+"/validateMaxLeave?"+tokenParamName+"="+tokenValue+"&leaveTypeId="+leaveTypeId+"&orgLocationId="+orgLocationId+"&userId="+userId+"&fromDate="+fromDate+"&toDate="+toDate, function(data) {
			if(data == true){
				$('.jquery_form_error_message').remove();
				$("#leaveTypeIdDiv").after("<p class='jquery_form_error_message cOrange'>You don't have balance for the '"+leaveTypeName+"'.</span>");
			}else{
				$('.jquery_form_error_message').hide();
			}
		}); 
	}
	
  }
}
</script>

<script>
function validateCarryForwardLeaves(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	$('.jquery_form_error_message cOrange').hide();
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var leaveTypeId = $("#leaveType").val();
	if(userId != null && userId !='' && userId !='undefined'){
	if(leaveTypeId !=null && leaveTypeId != '' && leaveTypeId !='undefined'){
		$.get(pmsUrl+"/validateCarryForwardLeaves?"+tokenParamName+"="+tokenValue+"&leaveTypeId="+leaveTypeId+"&userId="+userId+"&fromDate="+fromDate+"&toDate="+toDate, function(data) {
			if(data!=null&&data == "you cannot apply two leaves on same day.!"){ 
				$("#toDate").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span></span>"+data); 
				$("#ApplyLeave").attr('disabled','disabled'); 
				$(".comMask").fadeIn(); 
				}else if(data!=null){ 
				$("#toDate").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span></span>"+data); 
				$("#ApplyLeave").removeAttr('disabled'); 
				$(".comMask").fadeIn();	
				} 
		}); 
	}
	
  }
	
}
</script>

<div class='comMask'></div>
<script>
function validateDate(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
// 	$('.warMess').hide();
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var roleId = '${SourceLeadWeb_loginUserRoleId}';
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
	if(userId != null && userId != '' && userId != 'undefined' && fromDate != null && fromDate != '' && fromDate != 'undefined' ){
		$.get(pmsUrl+"/validateRestrictDate?"+tokenParamName+"="+tokenValue+"&fromDate="+fromDate+"&toDate="+toDate+"&orgLocationId="+orgLocationId+"&userId="+userId+"&errorMessage="+errorMessage, function(data) {
			if(data !=null && data.validateType == 'SAMEDAY'){
				errorMessage += data.errorMessage;
				/* $("#leaveTypeIdDiv").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span>"+errorMessage+"</p> ");
// 				$("#toDate").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span></span>"+data.errorMessage);
				$("#ApplyLeave").attr('disabled','disabled');
				$(".warMess").fadeIn();
				$(".comMask").fadeIn(); */
			}else if(data != null && data.validateType == 'HOLIDAY'){
				errorMessage += data.errorMessage;
// 				$("#leaveTypeIdDiv").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span>"+errorMessage+"</p> ");
// // 				$("#toDate").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span></span>"+data.errorMessage);
// 				$("#ApplyLeave").removeAttr('disabled');
// 				$(".comMask").fadeIn();	
			}
			if(data.errorMessage != "N/A" && data.errorMessage != null){
				if(data.validateType == 'SAMEDAY'){
					$("#ApplyLeave").attr('disabled','disabled');
				}
		    	$("#leaveTypeIdDiv").after("<p class='warMess'><span id='dataclosePop' class='fa fa-times text-danger' onclick='closedate();'></span>"+data.errorMessage+"</p> ");
		    	errorMessage = "";
		    	$(".warMess").fadeIn();
		    	$(".comMask").fadeIn();
		    }else{
				$("#ApplyLeave").removeAttr('disabled');
		    }
		});
	}
	 
};

function closedate(){
$(".warMess").fadeOut();
$(".comMask").fadeOut();
$(".warMess").remove();
};
$(".comMask").click(function()
{
	$(".comMask").fadeOut();
	$(".warMess").fadeOut();
	$(".warMess").remove();
		});


</script>

<script>
function getApproversList(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
   if(userId != null && userId !='' && userId !='undefined'){
	$.get(pmsUrl+"/getApproversForLeave?&userId="+userId, function(data) {
		if(data !=null){
			var result = "";
			var opt = data[0];
			result += '<input type="text" class="form-control" id="approverUser" readOnly value="'+opt.firstName+' '+opt.lastName+' ('+opt.userType+')"/>';
			result += '<input type="hidden" class="form-control" name="approverUser" readOnly value="'+opt.id+'"/>';
			$("#approvalDiv").show();
			$("#notifyToOthersDiv").show();
			$("#approverUserDiv").empty();
			$("#approverUserDiv").append(result);
	// 		$('#approverUser').trigger("chosen:updated");
		}
		});
   }
}
</script>

<script>
function clearFromDate(){
	$('#toDate').attr('value', '');
	$('#toDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#fromDate").val("");
	};
	
function clearToDate(){
	$('#fromDate').attr('value', '');
	$('#fromDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#toDate").val("");
	};
</script>


<script>
$("#fromDateHalfTime").timepicker({
	'showDuration': true,
	'disableTextInput': true,
	 'step': 15
}).on('changeTime', function() {
	 $("#toDateHalfTime").timepicker('option', 'minTime', $(this).val());
});

$("#toDateHalfTime").timepicker({
	'showDuration': true,
	'disableTextInput': true,
	 'step': 15
}).on('changeTime', function() {
    $("#fromDateHalfTime").timepicker('option', 'maxTime', $(this).val());
    calculateDuration();
    validateDate();
}); 

$("#toDateFromHalfTime").timepicker({
	'showDuration': true,
	'disableTextInput': true,
	 'step': 15
}).on('changeTime', function() {
    $("#toDateToHalfTime").timepicker('option', 'minTime', $(this).val());
});

$("#toDateToHalfTime").timepicker({
	'showDuration': true,
	'disableTextInput': true,
	 'step': 15
}).on('changeTime', function() {
    $("#toDateFromHalfTime").timepicker('option', 'maxTime', $(this).val());
    calculateDuration();
    validateDate();
}); 


</script>

<script>
function getUsersBasedOnHierarchy(type){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	$("#notifyOtherDiv").hide();
	if($("#notifyOthersId").is(':checked')){
		$("#notifyOtherDiv").show();
		$("#approverId").removeAttr("data-validation-optional");
		$("#approverId").attr("data-validation","required");
	}else{
		$("#approverId").removeAttr("data-validation");
	}
	var behalf = $("#checkbox-1");
	var userId = "";
	if(behalf.is(':checked')){
		userId = $("#user").val();
	}else{
		userId = '${SourceLeadWeb_userId}';
	}
   if(userId != null && userId !='' && userId !='undefined'){
	$.get(pmsUrl+"/getApproversForLeave?&userId="+userId+"&notifyToOthers=Y", function(data) {
		if(data !=null){
			var result = '';
			for(var i=0; i<data.length; i++){
			var approver = data[i];
			if(type == 'edit'){
				<c:forEach var="user" items="${approversList}">
					if("${user.approverUserId.id}" == approver.id){
						result = result +'<option selected value='+approver.id+'>'+approver.firstName+' '+approver.lastName+'</option>';
					}
				</c:forEach>
			}else{
				result = result +'<option value='+approver.id+'>'+approver.firstName+' '+approver.lastName+'</option>';
			}
			}
			$("#approverId").empty();
			$("#approverId").append(result);
			$('#approverId').trigger("chosen:updated");
		}
		});
   }
}
</script>


<jsp:include page="../masterFooter.jsp" />
