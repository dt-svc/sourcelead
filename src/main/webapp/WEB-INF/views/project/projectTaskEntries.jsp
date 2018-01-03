<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>
<script>
//description script
	$(document).ready(function(){ 
		maskme();
	});
 </script>
			
					<div class="clearfix mt mb">
	                    <c:if test="${not empty projectmember}">
		                    <div class="clearfix">
		                    	<button type="button" class="btn btn-primary btn-sm pull-right mr" onclick="addTimeEntry()">Add Time Entry</button>
		                    </div>
	                    </c:if>
	                     <div class="clearfix">
	                        <!-- <div class="col-md-3 paddingRight">
	                            <input type="text" class="form-control input-sm" placeholder="Estimated Hours">
	                        </div>
	                        <div class="col-md-2 paddingLeft">
	                            <button type="button" class="btn btn-primary btn-sm">
	                                <i class="fa fa-pencil paddingRight"></i>Edit
	                            </button>
	                        </div>
	                        <div class="col-md-3 paddingRight">
	                            <input type="text" class="form-control input-sm" placeholder="Actual Hours">
	                        </div> -->
	
	                    </div> 
					                </div>
					<!-- TimeEntry Modal Pop -->
					<form id="time_entry_form" name="time_entry_form">
					<div class="modal-Time clearfix">
					    <!-- Modal content-->
					      <div class="clearfix">
					      <input type="hidden" name="taskUUID" value="${taskUUID}">
					      <input type="hidden" name="projectId" value="${projectId}">
					          <button type="button" class="close" onclick="closeTimeEntryTasks()">&times;</button> 
					          <p><strong>Time Entry</strong></p>
					      </div>
					        <div class="row clearfix">
					          <div class="col-md-4">
					            <div class="row">
					              <div class="col-md-12">
					                <input type="hidden" id="projectTaskMemberId" name="projectTaskMemberId" value="${projectmember.id}"/>
					                <input type="text" data-validation="required" class="form-control input-sm calendar" field-name="Date" name="timesheetDate"  id="timesheetDate" placeholder="Date">
					                	<input type="hidden" value="${startDate}" id="projectAssignedDate">
					                	<input type="hidden" value="${endDate}" id="projectTerminationDate">
					              </div>
					            </div>
					          </div>
					          <div class="col-md-2">
					            <div class="row">
					              <div class="col-md-12" id="projectTaskErrorMsgDiv">
					                <input type="text" data-validation="required" class="form-control input-sm timeicon editMe" field-name="Hours" name="hours" onblur="editMe(this);" id= "timesheetHours"  placeholder="HH:MM">
					              </div>
					            </div>
					          </div>
					          <div class="clearfix mb"></div>
					          <div class="col-md-12 mb10">
					                <textarea class="form-control" rows="2" id="entryComment" name="comments" field-name="Comments" data-validation-optional="true" placeholder="Comments"></textarea>
					          </div>
					        </div>
					      <div class="modalFooter mt5">
					      <div class="pull-right">
					        <button type="button" class="btn btn-primary btn-sm" data-dismiss="modal" id="savetask" onclick="saveTaskDetails();">Save</button>
					        <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" onclick="closeTimeEntryTasks()">Cancel</button>
					       </div>
					      </div>
					</div>
					</form>
					                <!-- TimeEntry Modal Pop Ends -->
					 <div class="clearfix project_wrap_table">
			                    <table>
			                        <thead>
			                            <tr>
			                                <th>Date</th>
			                                <th>SubTask</th>
			                                <th>Description</th>
			                                <th>Hours</th>
			                            </tr>
			                        </thead>
			                        <tbody>
			                            <tr>
			                                <td><div><span>23/04/2017</span></div></td>
			                                <td><div class="tbldata-dotdot-vsm">New Task</div></td>
			                                <td>
			                                    <div class="tbldata-dotdot-me">
			                                    Lorem Ipsum is simply dummy text of the printing and typesetting industry.
			                                    </div></td>
			                                <td><div><span>100 Hours</span></div></td>
			                            </tr>
			                        </tbody>
			                        <tfoot style="border-top: 1px solid #909090;">
			                        <tr>
			                            <td colspan="3">Total Hours</td>
			                            <td>100 Hours</td>
			                            </tr>
			                        </tfoot>
					          </table>
					        </div>    



<script>
function saveTaskDetails(){
   if ($('#time_entry_form').validate(false, validationSettings)){  
  		   var form = $('#time_entry_form')[0];
   	       var formData = new FormData(form); 
	         $.ajax({
				 type : "POST",
				 data: formData,
  		         processData: false,
  		         contentType:false, 
				 url : sourceleadUrl+"/addTimeSheetDayEntries",
				  		success : function(data) {
				  					alert("hi"+data);
				},
			});
			return true; 
	    }else{ 
			return false;
	 }   
}
</script>

<script>
	var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>

<script>
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var jsDateFormat= '';
	  if(locationDate.match("yyyy-MM-dd")){
		  jsDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  jsDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  jsDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  jsDateFormat = 'yy-dd-mm'; 
	  }
	  var projectAssignedDate = $("#projectAssignedDate").val();
	  alert(projectAssignedDate);
	  var projectTerminationDate = $("#projectTerminationDate").val();
	  alert(projectTerminationDate);
	  
	  $('#timesheetDate').datepicker({
	      dateFormat: jsDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	  minDate:projectAssignedDate,
		  maxDate:projectTerminationDate,
	  });
</script>
					  
<script>
function maskme(){
	$.mask.definitions['H'] = "[0-9]";
    $.mask.definitions['h'] = "[0-9]";
    $.mask.definitions['M'] = "[0-9]";
    $.mask.definitions['m'] = "[0-9]";
    $.mask.definitions['P'] = "[AaPp]";
    $.mask.definitions['p'] = "[Mm]";
    $(".editMe").mask("Hh:Mm"); 
}
</script>

<script>
function editMe(local){
	     var id = local.id;
	     var inputValue =  $("#"+id).val();
		 var split = inputValue.split(':');
		 var hours = split[0];
		 var minutes = split[1];
		 var hours1 = hours.charAt(0);
		 var hours2 = hours.charAt(1);
		 var min1 = minutes.charAt(0);
		 var min2 = minutes.charAt(1);
		 if(!isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
			 $("#"+id).val("0"+hours1+":00");
			 //setTotals();
			 checkHours(); 
		 }
		 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
			 $("#"+id).val("0"+hours2+":00");
			 //setTotals();
			 checkHours(); 
		 }
		 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
			 $("#"+id).val("00:"+min1+"0");
			 //setTotals();
			 checkHours(); 
		 }
		 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
			 $("#"+id).val("00:0"+min2);
			 //setTotals();
			 checkHours(); 
		 }
		 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
			 $("#"+id).val(hours+":00");
			 //setTotals();
			 checkHours(); 
		 }
		 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
			 $("#"+id).val(hours+":"+"0"+min1);
			 //setTotals();
			 checkHours(); 
		 }
		 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
			 $("#"+id).val(hours+":"+"0"+min2);
			 //setTotals();
			 checkHours(); 
		 }
		 else if(!isNaN(hours)&& !isNaN(minutes)){
			  var hrmin= hours * 60;
			  var totalMin = parseInt(hrmin) + parseInt(minutes);
			  hours = Math.floor(totalMin / 60); 
			  minutes = totalMin % 60;
			  if(hours<=9){
				  hours = "0"+hours;
			  }
			  if(minutes<=9){
				  minutes = "0"+minutes;
			  }
			  $("#"+id).val(hours+":"+minutes);
			 // setTotals();
			  checkHours(); 
		 }else if(isNaN(hours1) && isNaN(hours2) && isNaN(min1) && isNaN(min2)){
		     $("#"+id).val("00:00");
		    // setTotals();
		 }
		 
}

function checkHours(){ 
	 var result = "";		
	 var total; 
	 var bsum = 0;
	 var nbsum = 0;
	 var total = 0;
   	 var btemp = $("#timesheetHours").val();
     if (btemp !== undefined && btemp !=='####' && btemp !=='') {
    	 var hours = btemp;
    	 var split = hours.split(':');
    	 var first = split[0];
    	 var second = split[1];
		 var minute = first * 60;
		 var minute1 = second;
		 var finalmin = parseInt(minute) + parseInt(minute1);
	     bsum = parseFloat(bsum) + parseInt(finalmin);
	}
	    var t = bsum; 
		var hours = Math.floor(t / 60); 
		var minutes = t % 60;
		total = hours +":"+ minutes;
	 	var time = total.split(':'); 
	 	if(!isNaN(time[0])){
	 	if(time.length === 2 && parseInt(time[0],10)>=0 && parseInt(time[0],10)<=23  && parseInt(time[1],10)>=0  && parseInt(time[1],10)<=59){
	 	}else{
	    		result = result+"Day";
	 	}
  }
	    if(result != "" && result != null && result != 'undefined'){   
			var outPut = "<p>"+result+" hours should not be more than 24<b onclick='flushTimeValue();'>X</b></p>";
			$("#projectTaskErrorMsg").removeAttr('style');
			$("#projectTaskErrorMsg").attr('style','display:block;');
			$("#projectTaskErrorMsg").empty();
			$("#projectTaskErrorMsg").append(outPut);
			$("#projectTaskErrorMsgDiv").append("<p class='jquery_form_error_message'>Day hours should not be more than 24</p>");
			$('#savetask').attr('disabled','disabled');
		 }else{
		    $("#projectTaskErrorMsg").removeAttr('style');
		    $("#projectTaskErrorMsg").attr('style','display:none;');
			$("#projectTaskErrorMsg").empty();
			$("#hideError").empty();
		} 
		
		$(".time_pop b").click(function(){
			$("#projectTaskErrorMsg").removeAttr('style');
			$("#projectTaskErrorMsg").attr('style','display:none;');
		    $("#projectTaskErrorMsg").empty();
		}); 
	
}

</script>
<script>
function flushTimeValue(){
	$('#timesheetHours').val('00:00');
	$("#projectTaskErrorMsgDiv").find('p.jquery_form_error_message').remove();
	$('#savetask').removeAttr('disabled','disabled');
}
</script>
					   
