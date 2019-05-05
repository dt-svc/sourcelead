<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script>
$(document).ready(function(){
	var startingTime = '${shift.timeIn}';
	
	if(startingTime != null && startingTime != "" && startingTime != "undefined"){
		$("#shiftTimeOut").timepicker('option', 'minTime',startingTime);
	}
	
});
</script>

<!-- Content Area -->
   <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Add Shift</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a href="<c:url value='${appPath}/leaveConfiguration'/>"> Leave Configuration</a> &raquo;<a href="<c:url value='${appPath}/viewShift'/>">Shift List</a> &raquo; Add Shift</div>
    </section>
    <div class="content-area content-new clearfix">
    <div id="loading"></div>
    <form:form id="addshift_form" name="addshift_form">
      <div class="clearfix form-horizontal inner-content">
        <div class="row">
        
        
         
 <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
              
    <div class="form-group">
       <label class="col-md-3">Shift Name<span
							class="text-danger">&nbsp;*</span></label>
       <div class="col-md-9" id="shiftNameDiv">
       <input type="hidden" name="shiftId" id="shiftId" value="${shift.id}"/>
       <input type="text" name="shiftName" placeholder="Shift Name" id="shiftName"  value="${shift.shiftName}" onblur="validateShiftName();"  field-name="Shift Name" class="form-control" data-validation="required validate_length length1-100">
       </div>
    </div>
    <div class="form-group">
       <label class="col-md-3">Time<span class="text-danger paddingLeft">*</span>
       <div class="text-muted small tbldata-dotdot-vsm" id="shipTimeZone">${timezone}</div>
       </label>
        <div class="col-md-9">
        <div class="row">
        	<div class="col-md-6 clearField" id="startTimeDiv">
        	<input type="text" class="timepicker form-control" id="shiftTimeIn"
               name="timeIn" placeholder="In-Time" value="${shift.timeIn}" field-name="Time in" onblur="validateShiftEndTime();" data-validation = "required">
        	<i class="fa fa-times text-muted small"></i>
        	</div>
        	<div class="col-md-6 clearField" id="endTimeDiv">
        	<input type="text" class="timepicker form-control" id="shiftTimeOut"
               name="timeOut" placeholder="Out-Time" value="${shift.timeOut}" field-name="Time Out" onblur="validateShiftEndTime();" data-validation = "required">
        	<i class="fa fa-times text-muted small"></i>
        	</div>
        	<div id="timeDiv"></div>
        </div>
       
       </div>
    </div>
    
    <!--  hours -->
    <div class="form-group">
       <label class="col-md-3">Hours 
       </label>
        <div class="col-md-9">
        <div class="row">
        	<div class="col-md-6">
        	<input type="text" class="timepicker form-control" id="totalHours"
               name="totalHours" placeholder="Total Hours" value="${shift.totalHours}" field-name="Total Hours" readonly>
        	</div>
        	<div class="col-md-6" id="emptyID">
        	<select title="Minimum Hours" class="selectpicker form-control sel" id="minimumHours" name="minimumHours" data-live-search="true">
        	<option ${shift.minmumHours == '0 mins' ? 'selected' : ''}>0 mins</option>
        	<option ${shift.minmumHours == '15 mins' ? 'selected' : ''}>15 mins</option>
        	<option ${shift.minmumHours == '30 mins' ? 'selected' : ''}>30 mins</option>
        	<option ${shift.minmumHours == '45 mins' ? 'selected' : ''}>45 mins</option>
        	<option ${shift.minmumHours == '1 hr' ? 'selected' : ''}>1 hr</option>
        	<option ${shift.minmumHours == '1 hr 15 mins' ? 'selected' : ''}>1 hr 15 mins</option>
        	<option ${shift.minmumHours == '1 hr 30 mins' ? 'selected' : ''}>1 hr 30 mins</option>
        	<option ${shift.minmumHours == '1 hr 45 mins' ? 'selected' : ''}>1 hr 45 mins</option>
        	<option ${shift.minmumHours == '2 hrs' ? 'selected' : ''}>2 hrs</option>
        	<option ${shift.minmumHours == '2 hr 15 mins' ? 'selected' : ''}>2 hr 15 mins</option>
        	<option ${shift.minmumHours == '2 hrs 30 mins' ? 'selected' : ''}>2 hrs 30 mins</option>
        	<option ${shift.minmumHours == '2 hrs 45 mins' ? 'selected' : ''}>2 hrs 45 mins</option>
        	<option ${shift.minmumHours == '3 hrs' ? 'selected' : ''}>3 hrs</option>
        	<option ${shift.minmumHours == '3 hrs 15 mins' ? 'selected' : ''}>3 hrs 15 mins</option>
        	<option ${shift.minmumHours == '3 hrs 30 mins' ? 'selected' : ''}>3 hrs 30 mins</option>
        	<option ${shift.minmumHours == '3 hrs 45 mins' ? 'selected' : ''}>3 hrs 45 mins</option>
        	<option ${shift.minmumHours == '4 hrs' ? 'selected' : ''}>4 hrs</option>
        	<option ${shift.minmumHours == '4 hrs 15 mins' ? 'selected' : ''}>4 hrs 15 mins</option>
        	<option ${shift.minmumHours == '4 hrs 30 mins' ? 'selected' : ''}>4 hrs 30 mins</option>
        	<option ${shift.minmumHours == '4 hrs 45 mins' ? 'selected' : ''}>4 hrs 45 mins</option>
        	<option ${shift.minmumHours == '5 hrs' ? 'selected' : ''}>5 hrs</option>
        	<option ${shift.minmumHours == '5 hrs 15 mins' ? 'selected' : ''}>5 hrs 15 mins</option>
        	<option ${shift.minmumHours == '5 hrs 30 mins' ? 'selected' : ''} >5 hrs 30 mins</option>
        	<option ${shift.minmumHours == '5 hrs 45 mins' ? 'selected' : ''} >5 hrs 45 mins</option>
        	<option ${shift.minmumHours == '6 hrs' ? 'selected' : ''} >6 hrs</option>
        	<option ${shift.minmumHours == '6 hrs 15 mins' ? 'selected' : ''} >6 hrs 15 mins</option>
        	<option ${shift.minmumHours == '6 hrs 30 mins' ? 'selected' : ''} >6 hrs 30 mins</option>
        	<option ${shift.minmumHours == '6 hrs 45 mins' ? 'selected' : ''} >6 hrs 45 mins</option>
        	<option ${shift.minmumHours == '7 hrs' ? 'selected' : ''} >7 hrs</option>
        	<option ${shift.minmumHours == '7 hrs 15 mins' ? 'selected' : ''} >7 hrs 15 mins</option>
        	<option ${shift.minmumHours == '7 hrs 30 mins' ? 'selected' : ''} >7 hrs 30 mins</option>
        	<option ${shift.minmumHours == '7 hrs 45 mins' ? 'selected' : ''} >7 hrs 45 mins</option>
        	<option ${shift.minmumHours == '8 hrs' ? 'selected' : ''} >8 hrs</option>
        	<option ${shift.minmumHours == '8 hrs 15 mins' ? 'selected' : ''} >8 hrs 15 mins</option>
        	<option ${shift.minmumHours == '8 hrs 30 mins' ? 'selected' : ''} >8 hrs 30 mins</option>
        	<option ${shift.minmumHours == '8 hrs 45 mins' ? 'selected' : ''} >8 hrs 45 mins</option>
        	<option ${shift.minmumHours == '9 hrs' ? 'selected' : ''} >9 hrs</option>
        	<option ${shift.minmumHours == '9 hrs 15 mins' ? 'selected' : ''} >9 hrs 15 mins</option>
        	<option ${shift.minmumHours == '9 hrs 30 mins' ? 'selected' : ''} >9 hrs 30 mins</option>
        	<option ${shift.minmumHours == '9 hrs 45 mins' ? 'selected' : ''} >9 hrs 45 mins</option>
        	<option ${shift.minmumHours == '10 hrs' ? 'selected' : ''} >10 hrs</option>
        	<option ${shift.minmumHours == '10 hrs 15 mins' ? 'selected' : ''} >10 hrs 15 mins</option>
        	<option ${shift.minmumHours == '10 hrs 30 mins' ? 'selected' : ''} >10 hrs 30 mins</option>
        	<option ${shift.minmumHours == '10 hrs 45 mins' ? 'selected' : ''} >10 hrs 45 mins</option>
        	<option ${shift.minmumHours == '11 hrs' ? 'selected' : ''} >11 hrs</option>
        	<option ${shift.minmumHours == '11 hrs 15 mins' ? 'selected' : ''} >11 hrs 15 mins</option>
        	<option ${shift.minmumHours == '11 hrs 30 mins' ? 'selected' : ''} >11 hrs 30 mins</option>
        	<option ${shift.minmumHours == '11 hrs 45 mins' ? 'selected' : ''} >11 hrs 45 mins</option>
        	<option ${shift.minmumHours == '12 hrs' ? 'selected' : ''} >12 hrs</option>
        	<option ${shift.minmumHours == '12 hrs 15 mins' ? 'selected' : ''}>12 hrs 15 mins</option>
        	<option ${shift.minmumHours == '12 hrs 30 mins' ? 'selected' : ''}>12 hrs 30 mins</option>
        	<option ${shift.minmumHours == '12 hrs 45 mins' ? 'selected' : ''}>12 hrs 45 mins</option>
        	<option ${shift.minmumHours == '13 hrs' ? 'selected' : ''}>13 hrs</option>
        	<option ${shift.minmumHours == '13 hrs 15 mins' ? 'selected' : ''}>13 hrs 15 mins</option>
        	<option ${shift.minmumHours == '13 hrs 30 mins' ? 'selected' : ''}>13 hrs 30 mins</option>
        	<option ${shift.minmumHours == '13 hrs 45 mins' ? 'selected' : ''}>13 hrs 45 mins</option>
        	<option ${shift.minmumHours == '14 hrs' ? 'selected' : ''}>14 hrs</option>
        	<option ${shift.minmumHours == '14 hrs 15 mins' ? 'selected' : ''}>14 hrs 15 mins</option>
        	<option ${shift.minmumHours == '14 hrs 30 mins' ? 'selected' : ''}>14 hrs 20 mins</option>
        	<option ${shift.minmumHours == '14 hrs 45 mins' ? 'selected' : ''}>14 hrs 45 mins</option>
        	<option ${shift.minmumHours == '15 hrs' ? 'selected' : ''}>15 hrs</option>
        	<option ${shift.minmumHours == '15 hrs 15 mins' ? 'selected' : ''}>15 hrs 15 mins</option>
        	<option ${shift.minmumHours == '15 hrs 30 mins' ? 'selected' : ''}>15 hrs 30 mins</option>
        	<option ${shift.minmumHours == '15 hrs 45 mins' ? 'selected' : ''}>15 hrs 45 mins</option>
        	<option ${shift.minmumHours == '16 hrs' ? 'selected' : ''}>16 hrs</option>
        	<option ${shift.minmumHours == '16 hrs 15 mins' ? 'selected' : ''}>16 hrs 15 mins</option>
        	<option ${shift.minmumHours == '16 hrs 30 mins' ? 'selected' : ''}>16 hrs 30 mins</option>
        	<option ${shift.minmumHours == '16 hrs 45 mins' ? 'selected' : ''}>16 hrs 45 mins</option>
        	<option ${shift.minmumHours == '17 hrs' ? 'selected' : ''}>17 hrs</option>
        	<option ${shift.minmumHours == '17 hrs 15 mins' ? 'selected' : ''}>17 hrs 15 mins</option>
        	<option ${shift.minmumHours == '17 hrs 30 mins' ? 'selected' : ''}>17 hrs 30 mins</option>
        	<option ${shift.minmumHours == '17 hrs 45 mins' ? 'selected' : ''}>17 hrs 45 mins</option>
        	<option ${shift.minmumHours == '18 hrs' ? 'selected' : ''}>18 hrs</option>
        	<option ${shift.minmumHours == '18 hrs 15 mins' ? 'selected' : ''}>18 hrs 15 mins</option>
        	<option ${shift.minmumHours == '18 hrs 30 mins' ? 'selected' : ''}>18 hrs 30 mins</option>
        	<option ${shift.minmumHours == '18 hrs 45 mins' ? 'selected' : ''}>18 hrs 45 mins</option>
        	<option ${shift.minmumHours == '19 hrs' ? 'selected' : ''}>19 hrs</option>
        	<option ${shift.minmumHours == '19 hrs 15 mins' ? 'selected' : ''}>19 hrs 15 mins</option>
        	<option ${shift.minmumHours == '19 hrs 30 mins' ? 'selected' : ''}>19 hrs 30 mins</option>
        	<option ${shift.minmumHours == '19 hrs 45 mins' ? 'selected' : ''}>19 hrs 45 mins</option>
        	<option ${shift.minmumHours == '20 hrs' ? 'selected' : ''}>20 hrs</option>
        	<option ${shift.minmumHours == '20 hrs 15 mins' ? 'selected' : ''}>20 hrs 15 mins</option>
        	<option ${shift.minmumHours == '20 hrs 30 mins' ? 'selected' : ''}>20 hrs 30 mins</option>
        	<option ${shift.minmumHours == '20 hrs 45 mins' ? 'selected' : ''}>20 hrs 45 mins</option>
        	<option ${shift.minmumHours == '21 hrs' ? 'selected' : ''}>21 hrs</option>
        	<option ${shift.minmumHours == '21 hrs 15 mins' ? 'selected' : ''}>21 hrs 15 mins</option>
        	<option ${shift.minmumHours == '21 hrs 30 mins' ? 'selected' : ''}>21 hrs 30 mins</option>
        	<option ${shift.minmumHours == '21 hrs 45 mins' ? 'selected' : ''}>21 hrs 45 mins</option>
        	<option ${shift.minmumHours == '22 hrs' ? 'selected' : ''}>22 hrs</option>
        	<option ${shift.minmumHours == '22 hrs 15 mins' ? 'selected' : ''}>22 hrs 15 mins</option>
        	<option ${shift.minmumHours == '22 hrs 30 mins' ? 'selected' : ''}>22 hrs 30 mins</option>
        	<option ${shift.minmumHours == '22 hrs 45 mins' ? 'selected' : ''}>22 hrs 45 mins</option>
        	<option ${shift.minmumHours == '23 hrs' ? 'selected' : ''}>23 hrs</option>
        	<option ${shift.minmumHours == '23 hrs 15 mins' ? 'selected' : ''}>23 hrs 15 mins</option>
        	<option ${shift.minmumHours == '23 hrs 30 mins' ? 'selected' : ''}>23 hrs 30 mins</option>
        	<option ${shift.minmumHours == '23 hrs 45 mins' ? 'selected' : ''}>23 hrs 45 mins</option>
        	</select>
        	</div>
        	<div id="minHoursDiv"></div>
        </div>
       
       </div>
    </div>
    <!--  hours  -->
    
    <div class="form-group">
       <label class="col-md-3">Shift Description</label>
       <div class="col-md-9">
 			<textarea rows="2" class="form-control" id="shiftDescription"
            name="shiftDescription" placeholder="Description" data-validation="validate_length length1-500" data-validation-optional="true" field-name="Shift Description">${shift.shiftDescription}</textarea>
       </div>
    </div>
    <div class="clearfix"></div>
    <div class="form-group">
       <div class="col-md-12">
       <button type="button" class="btn btn-success btnSuccess btn-sm" data-toggle="collapse" data-target="#showDetails_div">Additional Details</button>
       <div class="clearfix"></div>
	       <div id="showDetails_div" class="collapse mt">
				<table style="width:100%" class="table">
				<thead>
				  <tr>
				    <th rowspan="0" colspan="1" class="text-center" style="vertical-align: middle">Days</th>
				    <th rowspan="1" colspan="2" class="text-center">Hours</th>
				  </tr>
				    <tr>
				    <th class="text-center">In time</th>
				    <th class="text-center">Out time</th>
				  </tr>
				  </thead>
				  <tbody>
				  <tr>
				    <td><div class="tblminwidth-125">Monday</div></td>
				    <td>
				    <div class="positionRelative clearField-small">
				    	<input type="text" class="form-control input-sm" value="${shift.mondayInTime}" id="mondayInTime" name="mondayInTime">
				    	</div>
				    </td>
				    <td>
				    <div class="positionRelative clearField-small">
					    <input type="text" class="form-control input-sm" value="${shift.mondayOutTime}" id="mondayOutTime" name="mondayOutTime">
				    </div>
				    </td>
				  </tr>
				  <tr>
				    <td><div class="tblminwidth-125">Tuesday</div></td>
				    <td>
				    <div class="positionRelative clearField-small">
				    	<input type="text" class="form-control input-sm" value="${shift.tuesdayInTime}" id="tuesdayInTime" name="tuesdayInTime">
				    	</div>
				    </td>
				    <td>
				    <div class="positionRelative clearField-small">
					    <input type="text" class="form-control input-sm" value="${shift.tuesdayOutTime}" id="tuesdayOutTime" name="tuesdayOutTime">
				    </div>
				    </td>
				  </tr>
				  <tr>
				    <td><div class="tblminwidth-125">Wednesday</div></td>
				    <td>
				    <div class="positionRelative clearField-small">
				    	<input type="text" class="form-control input-sm" value="${shift.tuesdayOutTime}" id="wednesdayInTime" name="wednesdayInTime">
				    	</div>
				    </td>
				    <td>
				    <div class="positionRelative clearField-small">
					    <input type="text" class="form-control input-sm" value="${shift.wednesdayOutTime}" id="wednesdayOutTime" name="wednesdayOutTime">
				    </div>
				    </td>
				  </tr>
				  <tr>
				    <td><div class="tblminwidth-125">Thursday</div></td>
				    <td>
				    <div class="positionRelative clearField-small">
				    	<input type="text" class="form-control input-sm" value="${shift.thursdayInTime}" id="thursdayInTime" name="thursdayInTime">
				    	</div>
				    </td>
				    <td>
				    <div class="positionRelative clearField-small">
					    <input type="text" class="form-control input-sm" value="${shift.thursdayOutTime}" id="thursdayOutTime" name="thursdayOutTime">
				    </div>
				    </td>
				  </tr>
				  <tr>
				    <td><div class="tblminwidth-125">Friday</div></td>
				    <td>
				    <div class="positionRelative clearField-small">
				    	<input type="text" class="form-control input-sm" value="${shift.fridayInTime}" id="fridayInTime" name="fridayInTime">
				    	</div>
				    </td>
				    <td>
				    <div class="positionRelative clearField-small">
					    <input type="text" class="form-control input-sm" value="${shift.fridayOutTime}" id="fridayOutTime" name="fridayOutTime">
				    </div>
				    </td>
				  </tr>
				  <tr>
				    <td><div class="tblminwidth-125">Saturday</div></td>
				    <td>
				    <div class="positionRelative clearField-small">
				    	<input type="text" class="form-control input-sm" value="${shift.saturdayInTime}" id="saturdayInTime" name="saturdayInTime">
				    	</div>
				    </td>
				    <td>
				    <div class="positionRelative clearField-small">
					    <input type="text" class="form-control input-sm" value="${shift.saturdayOutTime}" id="saturdayOutTime" name="saturdayOutTime">
				    </div>
				    </td>
				  </tr>
				  <tr>
				    <td><div class="tblminwidth-125">Sunday</div></td>
				    <td>
				    <div class="positionRelative clearField-small">
				    	<input type="text" class="form-control input-sm" value="${shift.sundayInTime}" id="sundayInTime" name="sundayInTime">
				    	</div>
				    </td>
				    <td>
				    <div class="positionRelative clearField-small">
					    <input type="text" class="form-control input-sm" value="${shift.sundayOutTime}" id="sundayOutTime" name="sundayOutTime">
				    </div>
				    </td>
				  </tr>
				  </tbody>
				</table>
		   </div>
       </div>
    </div>
</div>
        </div>
      </div>
      <div class="form-footer clearfix mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <input type="button" value="Save" id="save_shift" onclick="saveShift();" class="btn btn-primary" >
            <input type="button" value="Cancel" class="btn btn-default ml10" id="cancel_shift" onclick="cancelShift();">
          </div>
        </div>
      </div>
      </form:form>
<!--       Content Area Ends  -->
    </div>
  </section> 
  
<script type="text/javascript">
	function validateShiftEndTime() {
		 	$("#timeDiv").find('p.jquery_form_error_message').remove();
// 		 	refreshTime();
			var startTime = $("#shiftTimeIn").val();
			var endTime = $("#shiftTimeOut").val();
			if(endTime != null && endTime != '' && endTime != 'undefined'){
				if(startTime == endTime){
					$("#timeDiv").find('p.jquery_form_error_message').remove();
			        $("#save_shift").removeAttr("disabled");
			        $("#save_shift").attr("disabled", "disabled");
			        $("#timeDiv").append("<p class='jquery_form_error_message'>End Time should greater than Start Time</span>");
			    }else{
			         $("#timeDiv").find('p.jquery_form_error_message').remove();
			         $("#save_shift").removeAttr("disabled");
			   	}
			}
			
			var startTime1 = $("shiftTimeIn").val();
			var endTime1 = $("#shiftTimeOut").val();
			if(endTime1 != null && endTime1 != '' && endTime1 != 'undefined'){
				if(startTime1 == endTime1){
					 $("#save_shift").attr("disabled", "disabled");
				}
			}
		}
	
function showDetails(){
	var inTime = $("#shiftTimeIn").val();
	var outTime = $("#shiftTimeOut").val();
	if(inTime != null && inTime != "undefined" && outTime != null && outTime != "undefined"){
		$("#mondayInTime").val(inTime);
		$("#mondayOutTime").val(outTime);
		$("#tuesdayInTime").val(inTime);
		$("#tuesdayOutTime").val(outTime);
		$("#wednesdayInTime").val(inTime);
		$("#wednesdayOutTime").val(outTime);
		$("#thursdayInTime").val(inTime);
		$("#thursdayOutTime").val(outTime);
		$("#fridayInTime").val(inTime);
		$("#fridayOutTime").val(outTime);
		$("#saturdayInTime").val(inTime);
		$("#saturdayOutTime").val(outTime);
		$("#sundayInTime").val(inTime);
		$("#sundayOutTime").val(outTime);
	}
}
function flushShowDeatils(){
	$("#mondayInTime").val('');
	$("#mondayOutTime").val('');
	$("#tuesdayInTime").val('');
	$("#tuesdayOutTime").val('');
	$("#wednesdayInTime").val('');
	$("#wednesdayOutTime").val('');
	$("#thursdayInTime").val('');
	$("#thursdayOutTime").val('');
	$("#fridayInTime").val('');
	$("#fridayOutTime").val('');
	$("#saturdayInTime").val('');
	$("#saturdayOutTime").val('');
	$("#sundayInTime").val('');
	$("#sundayOutTime").val('');
}
 
</script>
<script type="text/javascript">
    $(function () {
        $("#minimumHours").change(function () {
        	$("#minHoursDiv").find('p.jquery_form_error_message').remove();
            var minimumHours = $(this).val();
        	var splitMinimumHours = minimumHours.replace("hrs", "").replace("hr", "").replace("mins", "").replace("  ", " ").split(" ");
        	var minHrs = 0;
        	if(parseInt(splitMinimumHours[1]) != null && splitMinimumHours[1] != ""){
        		minHrs = parseInt(splitMinimumHours[0]) * 60;
        		minHrs += parseInt(splitMinimumHours[1]);
        	}else{
        		if(parseInt(splitMinimumHours[0]) > 24 || minimumHours == "15 mins"){
        			minHrs += parseInt(splitMinimumHours[0]);
        		}else{
        			minHrs += parseInt(splitMinimumHours[0]) * 60;
        		}
        	}
			
			var totalHours = $("#totalHours").val();
			var splitTotalHours = totalHours.replace("hrs", "").replace("hr", "").replace("mins", "").replace("  ", " ").split(" ");
			var totalHrs = 0;
			if(parseInt(splitTotalHours[1]) != null && splitTotalHours[1] != ""){
				totalHrs = parseInt(splitTotalHours[0]) * 60;
				totalHrs += parseInt(splitTotalHours[1]);
        	}else{
        		if(parseInt(splitTotalHours[0]) > 24 || totalHours == "15 mins"){
        			totalHrs += parseInt(splitTotalHours[0]);
        		}else{
        			totalHrs += parseInt(splitTotalHours[0]) * 60;
        		}
        	}
			
			if(totalHrs >= minHrs){
				  $("#save_shift").removeAttr("disabled");
		        $("#minHoursDiv").find('p.jquery_form_error_message').remove();
			}else{
				$("#minHoursDiv").append("<p class='jquery_form_error_message'>Minimum Hours should less than Total Hours</span>");
			     $("#save_shift").attr("disabled", "disabled");
			}
			
			 
        });
    });
</script>

<script>
function validateShiftName(){
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var name = $("#shiftName").val();
	var id = $("#shiftId").val();	
	if(name != null && name != '' && name != 'undefined'){
		if(name.length < 100){
			$.get(sourceleadUrl+"/validateName?&orgLocationId="+locationId+"&from=Shift&name="+name+"&id="+id, function(data) {
	          	if(data == true){	
	          		$('#shiftNameDiv').find('p.jquery_form_error_message').remove();
	          		$("#save_shift").removeAttr("disabled");
			        $("#save_shift").attr("disabled", "disabled");
					$("input#shiftName").after( "<p class='jquery_form_error_message'>" + name + " is already existed as Shift</span>");
	                 }else{
	                	$("#save_shift").removeAttr("disabled");
	                	$('#shiftNameDiv').find('p.jquery_form_error_message').remove();
	                 }
	            });
		}
		}
	}
</script>

<script type="text/javascript">
	function saveShift() {
		var userId = '${SourceLeadWeb_user.id}';
		var shiftUUID = '${shift.shiftUUID}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		 if ($('#addshift_form').validate(false, validationSettings)) {
			var formData = getFormData("addshift_form");
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:999;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.post( sourceleadUrl+ "/saveShift?$ "+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&userId="+userId+"&shiftUUID="+shiftUUID, formData, function(result) {
				if (result && result.response === "INVALID_DATA") {
	    		    showError(result.responseMessage);
	    		   } else if (result.response === "AWKWARD") {
	    		    location.href = "/errorPage";
	    		   }  else if (result.response === "SUCCESS") {     
				
				         $("#loading").removeAttr("style");
                    	   location.replace(sourceleadUrl+"/viewShift");
                       }else if (result.indexOf('Sign In') !== -1) {
               			location.replace(sourceleadUrl);
               		} else {
               		    showError("Operation failed, please try again");
               		}
				$("#loading").hide();
				$('#save_shift').attr('disabled','disabled');
                    });
            
// 			$("addshift_form").validate().resetForm();
			return true;
		} else {
// 			$('#submissionOffersListDiv').find('p.text-primary').remove();
			return false;
		}
	}
	$('body').on('blur', '#addshift_form', function() {
		$("#addshift_form").showHelpOnFocus();
		$("#addshift_form").validateOnBlur(false, validationSettings);
	}); 
</script>

<script>
function cancelShift(){
	location.replace(sourceleadUrl+"/viewShift");
}
</script>

<script>
	  $("#shiftTimeIn").timepicker({
			'showDuration': true,
			'disableTextInput': true,
			 'step': 15
		}).on('changeTime', function() {
			$('#totalHours').val('');
			$("#shiftTimeOut").val('');
			$("#minimumHours").val('');
			$("#minimumHours").selectpicker('refresh');
			flushShowDeatils();
	        $("#shiftTimeOut").timepicker('option', 'minTime', $(this).val());
	  });
	  
		
	 $("#shiftTimeOut").timepicker({
			'showDuration': true,
			'disableTextInput': true,
			 'step': 15
		}).on('changeTime', function() {
			$("#minHoursDiv").find('p.jquery_form_error_message').remove();
			var hours = $(".ui-timepicker-with-duration ul li.ui-timepicker-selected span").text();
			var totalHours = hours.substring(hours.indexOf('(') + 1, hours.indexOf(')'))
			$('#totalHours').val(totalHours);
			showDetails();
			$("#minimumHours").val(totalHours)
			$("#minimumHours").selectpicker('refresh');
	        $("#shiftTimeIn").timepicker('option', 'maxTime', $(this).val());
	  }); 
	 
	 
//Monday timepicker
 $("#mondayInTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#mondayOutTime").timepicker('option', 'minTime', $(this).val());
  });
	
 $("#mondayOutTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#mondayInTime").timepicker('option', 'maxTime', $(this).val());
  }); 
	 
 
//Tuesday timepicker
 $("#tuesdayInTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#tuesdayOutTime").timepicker('option', 'minTime', $(this).val());
  });
	
 $("#tuesdayOutTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#tuesdayInTime").timepicker('option', 'maxTime', $(this).val());
  }); 
	 
 
//Wednesday timepicker
 $("#wednesdayInTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#wednesdayOutTime").timepicker('option', 'minTime', $(this).val());
  });
	
 $("#wednesdayOutTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#wednesdayInTime").timepicker('option', 'maxTime', $(this).val());
  }); 
	 
//Thursday timepicker
 $("#thursdayInTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#thursdayOutTime").timepicker('option', 'minTime', $(this).val());
  });
	
 $("#thursdayOutTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#thursdayInTime").timepicker('option', 'maxTime', $(this).val());
  }); 
	 
	  
//Friday timepicker
 $("#fridayInTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#fridayOutTime").timepicker('option', 'minTime', $(this).val());
  });
	
 $("#fridayOutTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
        $("#fridayInTime").timepicker('option', 'maxTime', $(this).val());
  }); 
 
 
//Saturday timepicker
$("#saturdayInTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
       $("#saturdayOutTime").timepicker('option', 'minTime', $(this).val());
 });
	
$("#saturdayOutTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
       $("#saturdayInTime").timepicker('option', 'maxTime', $(this).val());
 }); 
 
//Sunday timepicker
$("#sundayInTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
       $("#sundayOutTime").timepicker('option', 'minTime', $(this).val());
 });
	
$("#sundayOutTime").timepicker({
		'showDuration': true,
		'disableTextInput': true,
		 'step': 15
	}).on('changeTime', function() {
       $("#sundayInTime").timepicker('option', 'maxTime', $(this).val());
 }); 
</script>

<jsp:include page="../masterFooter.jsp" />
