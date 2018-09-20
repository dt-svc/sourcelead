<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="/resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="/resources/js/jquery.simplePagination.js"></script>
<script>
//description script
	$(document).ready(function(){ 
		maskme();
	});
 </script>
			
					<%-- <div class="clearfix mt mb">
	                    <c:if test="${not empty projectmember}">
		                    <!-- <div class="clearfix">
		                    	<button type="button" class="btn btn-primary btn-sm pull-right mr" onclick="addTimeEntry()">Add Time Entry</button>
		                    </div> -->
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
					                </div> --%>
					
					                
<div class="timesheet_head minHeM">
	        	 <ul>
	        	  <c:if test="${not empty projectmember}">
	        	 	<li class="noPad"><button type="button" class="btn btn-primary btn-sm pull-right mr" onclick="addTimeEntry()">Add Time Entry</button></li>
	        	  </c:if>
	            </ul> 
		<div class="remove_search">
       					<input type="text" placeholder="Search..." value="" id="projectTaskSearch">
        			</div>	            
	            <div class="clearfix"></div>
	        </div>
	        
<!-- TimeEntry Modal Pop -->
					<form id="time_entry_form" name="time_entry_form">
					<div class="modal-Time clearfix" id="contentDivTimeSheetId">
					    <!-- Modal content-->
					      <div class="clearfix">
					      <input type="hidden" name="taskUUID" value="${taskUUID}">
					      <input type="hidden" name="projectId" value="${projectId}">
					      <input type="hidden" id="editTask" name="editTask" value="">
					      
					          <button type="button" class="close" onclick="closeTimeEntryTasks()">&times;</button> 
					          <p><strong>Time Entry</strong></p>
					      </div>
					        <div class="row clearfix">
					          <div class="col-md-4">
					            <div class="row">
					              <div class="col-md-12">
					                <input type="hidden" id="projectTaskMemberId" name="projectTaskMemberId" value="${projectmember.id}"/>
					                <input type="text" data-validation="required" class="form-control input-sm calendar" field-name="Date" name="timesheetDate"   id="timesheetDate" placeholder="Date">
					                	<input type="hidden" value="${startDate}" id="projectAssignedDate">
					                	<input type="hidden" value="${endDate}" id="projectTerminationDate">
					              </div>
					            </div>
					          </div>
					          <div class="col-md-2">
					            <div class="row">
					              <div class="col-md-12" id="projectTaskErrorMsgDiv">
					                <input type="text" data-validation="required" class="form-control input-sm timeicon editMe" field-name="Hours" name="hours" onblur="editMe(this);getTaskEntriesBydate();" id= "timesheetHours"  placeholder="HH:MM">
					              </div>
					            </div>
					          </div>
					          <div class="clearfix mb"></div>
					          <div class="col-md-12 mb10">
					                <textarea class="form-control" id="timesheetComments" rows="2" id="entryComment" name="comments" field-name="Comments" data-validation-optional="true" placeholder="Comments"></textarea>
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
                     
					 <div id="tableContent" class="clearfix project_wrap_table overflow_Auto">
			                    <table>
			                        <thead>
			                            <tr>
			                                <th>Emp Name</th>
			                                <th>Date</th>
			                                <th>Hours</th>
			                                <th>Comments</th>
			                                <th>Status</th>
			                                <th>Edit</th>
			                            </tr>
			                        </thead>
			                        <c:set var="projectMemID" value="${projectmember.id}" scope="session"/>
			                        <tbody id="timeDayEntriesData">
			                        		<jsp:include page="projectTaskTableData.jsp"></jsp:include>
			                        </tbody>
			                        <!-- <tfoot style="border-top: 1px solid #909090;">
			                        <tr>
			                            <td colspan="4">Total Hours</td>
			                            <td>100 Hours</td>
			                            </tr>
			                        </tfoot> -->
					          </table>
					        </div>    
	<div id= "paginationDiv" class="clearfix">
		<div class="col-md-6">
			<div class="pull-left mt5">
				<label class="pull-left"><span class="pull-left mt10 mr5">Show</span>
					<div class="dropdown-select pull-left mr5 tblminwidth-50">
						<select id="pageSize" onchange="getTableDataByPageSize();">
							<option value="5" >5</option>
							<option value="10" >10</option>
							<option value="15" >15</option>
							<option value="20" >20</option>
						</select>
					</div> <span class="pull-left mt10">entries</span> </label>
					 <span class="pull-left mt10 ml" id="pageMsg"></span>
			</div>
		</div>
		<div id="paginationholder" class="col-md-6 pagiPad">
			<ul id="pagination-demo" class="pagination-sm pull-right pagination"></ul>
		</div>
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
				  			if(data.response === "SESSION_TIMEDOUT") {
					    			location.replace(sourceleadUrl);
					    	 } else if (data.response === "AWKWARD") {
			                        location.href = "/errorPage";
					    	 } else if (data.response === "SUCCESS") {
						  			getSavedTaskDetails();
						  			closeTimeEntryTasks();
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
	function getTaskEntriesBydate(){
		var projectMember = '${projectmember.id}';
		var timesheetHour =  $('#timesheetHours').val();
		var timeDate =  $('#timesheetDate').val();
		var taskUUID = '${taskUUID}';
		var editTaskTime = $('#editTask').val();
		$("#projectTaskErrorMsgDiv").find('p.jquery_form_error_message').remove();
		if(timesheetHour != null && timesheetHour != 'undefined' && timesheetHour != '' && timeDate != null && timeDate != 'undefined' && timeDate != ''){
			$("#projectTaskErrorMsgDiv").find('p.jquery_form_error_message').remove();
			$.ajax({
				 type : "POST",
				 	 url : sourceleadUrl+"/ProjectTasks/calculateTotalTaskHoursForADay?&timeDate="+timeDate+"&projectMember="+projectMember+"&timesheetHour="+timesheetHour+"&editTaskTime="+editTaskTime,
				  		success : function(data) {
				  			if(data == "false"){   
				  				$("#projectTaskErrorMsgDiv").append("<p class='jquery_form_error_message'>Day hours should not be more than 24</p>");
				  				$('#savetask').attr('disabled','disabled');
				  			 }else if(data =="noDate"){
				  				$("#projectTaskErrorMsgDiv").append("<p class='jquery_form_error_message'>Please Select Date.</p>");
				  				//$('#timesheetHours').val('');
				  				$('#savetask').attr('disabled','disabled');
				  			}else if(data =="true"){
				  				$("#projectTaskErrorMsgDiv").find('p.jquery_form_error_message').remove();
				  			    $('#savetask').removeAttr('disabled','disabled');
				  			}
				  			
				},
			});	
		}else if(timesheetHour != null && (timeDate == '' || timeDate == 'undefined' || timeDate == null)){
			    $("#projectTaskErrorMsgDiv").append("<p class='jquery_form_error_message'>Please Select Date.</p>");
				$('#savetask').attr('disabled','disabled');
		}
		 else if(timeDate  != null && (timesheetHour == '' || timesheetHour == 'undefined' || timesheetHour == null)){
		     		editProjectTaskTimesheetDayEntry();
			     }
			}
</script>


<script>
function editProjectTaskTimesheetDayEntry(timesheetDate,type){
	var projectMember = '${projectmember.id}';
	$('#editTask').val(type);
	if(projectMember!= null && projectMember !='' && projectMember!='undefined'){
	var timeDate = '';
	if(timesheetDate != null && timesheetDate !='' && timesheetDate !='undefined'){
		 $(".modal-Time").fadeIn();
		 var timeDate =  $('#timesheetDate').val(timesheetDate);
		timeDate = timesheetDate;	
	}else{
		timeDate =  $('#timesheetDate').val();	
	}
	var taskUUID = '${taskUUID}';
	$.ajax({
		 type : "POST",
		 	 url : sourceleadUrl+"/ProjectTasks/editProjectTaskTimesheetDayEntry?&timeDate="+timeDate+"&projectMember="+projectMember+"&taskUUID="+taskUUID,
		  		success : function(data) {
		  			if(data != null && data !='' && data !='undefined'){
		  				$('#timesheetHours').val('');
		  				$('#timesheetHours').val(data.totalHours);
		  				$('textarea#timesheetComments').val(data.comments);
		  			}else{
		  				$('#timesheetHours').val('');
		  				$('textarea#timesheetComments').val('');
		  			}
				},
			});
	}
}
</script>


<script>
function deleteProjectTaskEntry(timesheetEntryId){
$.ajax({
	 type : "POST",
	 	 url : sourceleadUrl+"/ProjectTasks/deleteProjectTaskTimesheetDayEntry?&timesheetEntryId="+timesheetEntryId,
	  		success : function(data) {
	  			getSavedTaskDetails();
	  		},
		});
}
</script>


<script>
function getSavedTaskDetails(){
	var taskUUID = '${taskUUID}';
	var searchData = $('#projectTaskSearch').val();
	var searchvalue = '';
    if(searchData != null && searchData !='' && searchData!='undefined'){
    	searchValue = searchData;
    }else{
    	searchValue = '';
    }
    $("#projectTaskDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
		 type : "POST",
		 	 url : sourceleadUrl+"/ProjectTasks/getSavedTaskDetails?&taskUUID="+taskUUID+"&searchValue="+searchValue,
		  		success : function(data) {
		  			$('#timeDayEntriesData').empty();
		  			$('#timeDayEntriesData').append(data);
		  			preparePagination(1);
		  			
		},
	});
}

</script>

<script>
function flushTimeEntryTasks(){
	 $('#timesheetDate').val('');
	 $('#timesheetHours').val();
	 $('#entryComment').val('');
	 $(".modal-Time").fadeOut();
	 $('#time_entry_form').find('p.jquery_form_error_message').remove();
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
$("#projectTaskSearch").keyup(function(event) {
    if (event.keyCode === 13) {
    	var searchData = $('#projectTaskSearch').val();
    	getSavedTaskDetails();
    	$('#projectTaskSearch').val(searchData);
    }
});
</script>


<script>
      function getTableDataByPageSize(){
    	//  $('#pagination-demo').twbsPagination('destroy'); 
    	  getTableData(1);
      }
	  function getTableData(pageNo) {
		  var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		  var searchData = $('#projectTaskSearch').val();
		  var taskUUID = '${taskUUID}';
          var pageSize = $("#pageSize").val();
          var start = parseInt(pageNo*pageSize)-parseInt(pageSize);
          var searchvalue = '';
          if(searchData != null && searchData !='' && searchData!='undefined'){
          	searchValue = searchData;
          }else{
          	searchValue = '';
          }
          $("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	      	$.ajax({
	      		 type : "POST",
	      		 	 url : sourceleadUrl+"/ProjectTasks/getSavedTaskDetails?&taskUUID="+taskUUID+"&pageSize="+pageSize+"&pageNo="+pageNo+"&start="+start+"&searchValue="+searchValue,
	      		  		success : function(data) {
	      		  			$('#timeDayEntriesData').empty();
	      		  			$('#timeDayEntriesData').append(data);
	      		  			preparePagination(pageNo);
	      		  			
	      		},
	      	});  
      	}
	  function preparePagination(pageNo) {
		  var totalRows =  $('#totalRows').val();
		  var pageMsg = '';
		  var searchData = '';
		  var searchData = $('#searchInputValue').val();
		  if(totalRows !=null && totalRows != '' && totalRows != 'undefined'){
			  $('#paginationDiv').show();
			  var pageCount = $('#pageCount').val();
			  var pageSize = $("#pageSize").val();
			  $('#projectTaskSearch').val('') ;
			  $('#projectTaskSearch').val(searchData); 
			  var startingRecord = parseInt((pageNo*pageSize-pageSize)+1);
			  var endingRecord = parseInt(pageNo*pageSize);
			  if(endingRecord > totalRows){
				  endingRecord = totalRows;
			  }
			  var options = {
					      currentPage:pageNo,
					      items: totalRows,
			              itemsOnPage: pageSize,
			              ellipsePageSet :false,
			              cssStyle: 'light-theme',
			               onPageClick: function (pageNumber, event) {
			                 getTableData(""+pageNumber+"");
			              }
		      };
		      $("#pagination-demo").pagination(options);
		      pageMsg = " Showing "+startingRecord+" to "+endingRecord+" of "+totalRows+" entries ";
		  }
		  else{
			  $("#tableContent").empty();
	    	  $("#tableContent").append('<div class="dataTables_empty">  <input type="hidden" id="hideExportApproval" value="EmptyList">  </div>');
			  $('#projectTaskSearch').val('') ;
			  $('#projectTaskSearch').val(searchData);
			  $('#paginationDiv').hide();
			  
			  
		  }
	      $('#pageMsg').html('');
	      $('#pageMsg').html(pageMsg);
	 }
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
	  var projectTerminationDate = $("#projectTerminationDate").val();
	  $('#timesheetDate').datepicker({
	      dateFormat: jsDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	  minDate:projectAssignedDate,
		  maxDate:projectTerminationDate,
		  onSelect: function (selected) {
			  editProjectTaskTimesheetDayEntry();
	        }
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
			 checkHours(); 
		 }
		 if(isNaN(hours1) && !isNaN(hours2) && isNaN(min1) && isNaN(min2)){
			 $("#"+id).val("0"+hours2+":00");
			 checkHours(); 
		 }
		 else if(isNaN(hours)  && !isNaN(min1) && isNaN(min2)){
			 $("#"+id).val("00:"+min1+"0");
			 checkHours(); 
		 }
		 else if(isNaN(hours)  && isNaN(min1) && !isNaN(min2)){
			 $("#"+id).val("00:0"+min2);
			 checkHours(); 
		 }
		 else if(!isNaN(hours)  && isNaN(min1) && isNaN(min2)){
			 $("#"+id).val(hours+":00");
			 checkHours(); 
		 }
		 else if(!isNaN(hours)&& !isNaN(min1) && isNaN(min2)){
			 $("#"+id).val(hours+":"+"0"+min1);
			 checkHours(); 
		 }
		 else if(!isNaN(hours)&& isNaN(min1) && !isNaN(min2)){
			 $("#"+id).val(hours+":"+"0"+min2);
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
			  checkHours(); 
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
}

</script>
<script>
function flushTimeValue(){
	$('#timesheetHours').val();
	$("#projectTaskErrorMsgDiv").find('p.jquery_form_error_message').remove();
	$('#savetask').removeAttr('disabled','disabled');
}
</script>
					   
