<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link rel="stylesheet" href="resources/css/jquery.timepicker.css">
<script src="resources/js/jquery.timepicker.js"></script>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 
<style>
.dashboard-widget {
/* min-height: 400px !important; */
}
.dashboard-widget.dashboard-widget-icons{
min-height: 225px !important;
}
.dashboard-widget .blank-pages{
padding: 0;
min-height: 100px;
}
.side-bar .side-nav ul li span {
display: inline-block !important;
float: none !important;
}
.custom-datatble table{
 margin-top: 20px !important;
 }
 .custom-datatble .dataTables_filter{
   top: -35px !important;
    padding: 0 !important;
 }
 .dashboard-datepkr{
       position:relative;
}
.main-header span.logo i{
position: relative;
top:10px;
}
.ui-timepicker-wrapper {
z-index: 999999;
width: 11.5%;
}
/* .dashboard-datepkr input[type="text"]{
       width:115px;
       border:1px solid #ccc;
       margin-right:5px;
}
.dashboard-datepkr .fa{
   position: absolute;
   right: 10px;
   top: 4px;
} */
</style>

 <style type="text/css">
.clients_wrap_table {
overflow-x: auto;
}
</style>

 <script type="text/javascript">
 $( document ).ready(function() {
	 tasksList('myTask');
	});
</script>
  
<section class="content-wraper">
	    <section class="content-header clearfix">
	      <div class="pull-left">
	        <h2>Dashboard</h2>
	      </div>
	     <!--  <div class="pull-right brud-crum">Dashboard</div> -->
	    </section>
	    
	    <div class="clearfix row mb">
	      <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')"> 
			<%-- <c:if test = "${SourceLeadWeb_timesheet == 'Y'}">     --%>  
			   <jsp:include page="dashboardTimesheets.jsp"></jsp:include>  
			<%-- </c:if> --%>
	      </sec:authorize>
			 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
			<%-- <c:if test = "${SourceLeadWeb_userType != 'Employee'}"> --%>
				<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
					<jsp:include page="dashboardRequirement.jsp"></jsp:include> 
				</c:if>
			<%-- </c:if> --%>
		     </sec:authorize>
	      	 <%-- <jsp:include page="dashboardTasks.jsp"></jsp:include> --%>
	    </div>
    	<%-- <c:if test = "${SourceLeadWeb_userType != 'Employee'}"> --%>
	    	<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
	    			<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
					<jsp:include page="dashboardAssignedRequirements.jsp"></jsp:include> 
					</sec:authorize>
			    <div class="clearfix row mb"> 
			    	 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
			   	 	 <jsp:include page="dashboardRequirementApprovals.jsp"></jsp:include>
			   	 	 </sec:authorize>
			   	 	  <sec:authorize access="hasAnyRole('ROLE_VIEW_INTERVIEW')">
			    	 <jsp:include page="dashboardInterviews.jsp"></jsp:include>
			    	 </sec:authorize>
			    </div>
		    </c:if>
		     <div class="clearfix row mb"> 
		    	 <c:if test = "${isManagerWithSubmissionRateStatusAsRequestOrAccept == true}">  
		     		<jsp:include page="dashboardApplicants.jsp"></jsp:include>
		     	</c:if>
		     </div>
	    <%-- </c:if> --%>
	    
	   			 <sec:authorize access="!hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS','ROLE_VIEW_REQUIREMENT','ROLE_VIEW_INTERVIEW')">
	   				  <div class='row'>
						<div class='col-lg-12'> 
						<div class='content-area clearfix'>
							<div class='blank-pages'>
							        <p><i class='fa fa-5x fa-bar-chart'></i></p>
						        	<p>No Records Found</p>
							 </div>
						</div>
						</div>
					  </div> 
				 </sec:authorize> 
				 
	    <div class="clearfix row mb"> 
	   	 <%--  <jsp:include page="dashboardTimesheetsApprovals.jsp"></jsp:include> --%>
	    </div>
	    
	    
	      <!-- Add Task Div -->
		 <div class="addTask"> <span class="icon-addTask" title="Add Task"> <i class="fa fa-gears"></i> </span>
		    <div class="clearfix col-xs-12 col-md-12 addTask-widget"> 
		      <!-- Assign Task Widget tabs -->
		      <div class="clearfix require_wrap_table dashboard-data" id="myTask">
		        <div class="addtsk-btn"> <span class="btn btn-success btn-sm" onClick="showtaskdiv()"><i class="fa fa-plus"></i>&nbsp;&nbsp;Add Task</span> </div>
		        <ul class="nav nav-tabs">
		          <li class="active"><a data-toggle="tab" href="#my-task" onclick="tasksList('myTask');">My Tasks</a></li>
		          <li><a data-toggle="tab" href="#other-task" onclick="tasksList('assignedTasks');">Assigned Tasks</a></li>
		          <li><a data-toggle="tab" href="#closed-task" onclick="tasksList('closedTasks');">Closed Task</a></li>
		        </ul>
				        <div class="tab-content clearfix">
				        <div id="other-task" class="tab-pane fade">
				            <div class="clearfix require_wrap_table">
				              <div id="assignedTasksId"></div>
				            </div>
				          </div>
				          
				          <div id="my-task" class="tab-pane fade in active">
				            <div class="clearfix require_wrap_table">
				               <div id="myTasksId"></div>
				              
				            </div>
				          </div>
				          
				          <div id="closed-task" class="tab-pane fade">
				            <div class="clearfix require_wrap_table">
				               <div id="closedTasksId"></div>
				            </div>
				          </div>
				        </div>
		      </div>
		      <!-- Assign Task Widget tabs Ends --> 
		      
		      <!-- Add New Task Widget -->
			      <div class="newTask-widget clearfix">
			      <form:form id="task_form" name="task_form" method="post">
			        <div class="col-md-12"> <a class="pull-right text-danger" onClick="taskwidget();"><i class="fa fa-times"></i></a>
			          <form class="form-horizontal clearfix">
			            <h4>Add Task</h4>
			            <div class="row">
			              <div class="col-md-6">
			                <label>Assign To <span class="text-danger">*</span></label>
			                <select class="chosen-select" multiple data-placeholder="Select Priority" id="assignTo" name="assignTo" field-name="AssignTo"  data-validation="required">
				                 <c:forEach var="user" items="${userList}">
					                     <option value="${user.id}" >${user.firstName} ${user.lastName}</option>
				                </c:forEach>
			                </select>
			              </div>
			              <div class="col-md-6 dropdown-select">
			                <label>Priority</label>
			                <select class="form-control" id="priority" name="priority">
			                  <option selected disabled>-- Select Priority --</option>
				              <option>Urgent</option>
							  <option>High</option>
							  <option>Normal</option>
							  <option>Low</option>
			                </select>
			              </div>
			              <div class="col-md-6">
			                  <div class="row">
				                  <div class="col-md-6">
					                <label>Date</label>
						                <div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						                  <input class="form-control" value="" name="taskDatpicker" id="taskDatpicker" type="text" >
						                </div>
				                   </div>
				                  <div class="col-md-6">
					                <label>Time</label>
						                <div class="input-group"> <span class="input-group-addon" ><i class="fa fa-clock-o"></i></span>
						                  <input class="form-control"  name="timepicker" id="timepicker"  type="text">
						                </div>
				                  </div>
			                  </div>
			              </div>
			                
			                <div class="col-md-6 dropdown-select">
			                <label>Repeat</label>
			                <select class="form-control input-sm" name="repeat" id="repeat">
			                  <option selected disabled>-- Select Repeat --</option>
			                  <option>Never</option>
			                  <option>Daily</option>
			                  <option>Weekly</option>
			                  <option>Monthly</option>
			                  <option>Yearly</option>
			                </select>
			              </div>
			              <div class="col-md-6">
			                <label>Description</label>
			                <textarea rows="2" placeholder="Description" class="form-control" name="description" id="description" data-validation="validate_length length10-500" data-validation-optional="true"></textarea>
			              </div>
			                <div class="col-md-6">
			                <label>Subject <span class="text-danger">*</span></label>
			                <input type="text" class="form-control input-sm" placeholder="Subject"  id="subject" name="subject" data-validation="required validate_Space validate_length length2-100" field-name="Subject">
			              </div>
			              <div class="col-md-6">
			                <label class="checkbox-inline">
			                	<input type="checkbox" value="Email" name="email">Email
			                 </label>
			                <label class="checkbox-inline">
			                    <input type="checkbox" name="notification" value="Notification">Notification
			                </label>
			                 <!-- <label class="checkbox-inline">
			                  <input type="checkbox" name="chkbox">Pop-up WIndow
			                </label> -->
			              </div>
			              <div class="clearfix"></div>
			              <div class="clearfix mt col-md-12">
			                <div class="pull-right">
			                  <input type="button" class="btn btn-primary btn-sm" value="Submit" onclick="saveTask();">
			                  <input type="button" class="btn btn-default btn-sm ml10" value="Cancel"  onclick="taskwidget();">
			                </div>
			              </div>
			            </div>
			          </form>
			        </div>
			       </form:form>
			      </div>
		      <!-- Add New Task Widget --> 
		    </div>
		  </div>
</section>


<jsp:include page="masterFooter.jsp" />


<script type="text/javascript">
	function saveTask() {
		if ($('#task_form').validate(false, validationSettings)) {
			var frm = $('#task_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
						type : "POST",
						url : sourceleadUrl+ "/storeTasks?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData,
						success : function(result) {
							taskwidget();
							tasksList('assignedTasks');
						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#task_form', function() {
		$("#task_form").showHelpOnFocus();
		$("#task_form").validateOnBlur(false, validationSettings);
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

<script type="text/javascript">
$('#taskDatpicker').datepicker({
	dateFormat : 'dd-mm-yy',
	 changeMonth: true,
	changeYear: true, 
	minDate : 0,
	onSelect : function(selected) {
		var dt = new Date(selected);
		dt.setDate(dt.getDate() + 1);
		$("#taskDatpicker").datepicker("option", "minDate", dt);
	}
	
	});
$('#timepicker').timepicker();
</script>

<script>
//Collapse div
$(document).ready(function() {
$(".collpase-div").click(function(){
	$(this).next().slideToggle();
});
});

//Add Task Div
 $(".icon-addTask").click(function(){
	$(".addTask-widget").toggleClass("addTask-widget-active");
		$(".newTask-widget").hide();
	$("#myTask").show();
 });
 
 // Close New Task div
 function taskwidget(){
	 $(".newTask-widget").hide();
	  $("#myTask").show();
 };
 function showtaskdiv(){
	 $("#myTask").hide();
	 $(".newTask-widget").fadeIn(200);
	 $(".newTask-widget").val();
	 $('.newTask-widget').find('p.jquery_form_error_message').remove();	
	 document.getElementById("task_form").reset();
	 
 };
</script>
<script src="resources/js/chosen.jquery.min.js"></script> 
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
				});
			$('#assignTo').trigger('chosen:updated');
</script>
<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>
<!-- <script type="text/javascript">
function assignedTasks(){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getAssignedTasksList?${_csrf.parameterName}=${_csrf.token}", 
        success: function(data) {
        	 var json = JSON.parse(data);
        	 var result = "";
        	result = result + '	 <table id="assignedTasksPagination">'; 
        	result = result + '     <thead>';
        	result = result + '       <tr>';
        	result = result + '         <th>Subject</th>';
        	result = result + '         <th>Date &amp; Time</th>';
        	result = result + '         <th>Owner</th>';
        	result = result + '         <th>Assigned to</th>';
        	result = result + '         <th>Description</th>';
        	result = result + '         <th>Mode</th>';
        	result = result + '         <th>Status</th>';
        	result = result + '       </tr>';
        	result = result + '     </thead>';
        	result = result + '     <tbody>';
        	
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        		var selected1 = '';
        		var selected2 = '';
        		
        		if(report.taskStatus == 'Open'){
        			 selected1 = 'selected';
				}
        		if(report.taskStatus == 'Close'){
					 selected2 = 'selected';
				}
        		
        		result = result + '       <tr>';
        		result = result + '         <td><div title="'+report.subject+'"><span>'+report.subject+'</span></div></td>';
            	result = result + '         <td><div class="text-center">1'+report.taskDateStr+'  '+report.taskTime+'</div></td>';
            	result = result + '         <td><div><span>'+report.taskOwnerName+'</span></div></td>';
            	result = result + '         <td><div><span>'+report.assignedName+'</span></div></td>';
            	result = result + '         <td><div><span>'+report.description+'</span></div></td>';
            	result = result + '         <td>'+report.alertType+'</td>';
            	result = result + '         <td><div class="dropdown-select tblminwidth-100">';
            	result = result + '             <select id="taskState'+report.id+'" name="taskState" onChange="updateTaskStatus(\''+report.id+'\');">';
            	result = result + '               <option '+selected1+'>Open</option>';
            	result = result + '               <option '+selected2+'>Close</option>';
            	result = result + '             </select>';
            	result = result + '           </div></td>';
            	result = result + '       </tr>';
			}
        	result = result + '     </tbody>';
        	result = result + '   </table>';
        	 $("#assignedTasksId").empty();
        	 $("#assignedTasksId").append(result); 
        	 assignedTasksPagination();
        }
	});
	 
}
</script> -->

<script>
function updateTaskStatus(taskId){
    var taskStatus = $('#taskState'+taskId).val();
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeTaskStatus?${_csrf.parameterName}=${_csrf.token}&taskId="+taskId+"&taskStatus="+taskStatus,	    	   
	    success: function(data) {
	    	assignedTasks();
	    }
   });
}
</script>

<!-- 
<script type="text/javascript">
function myTasks(){
	var sourceleadUrl ='${sourceleadUrl}';
	var myTasksurl = sourceleadUrl+"/getMyTasksList?${_csrf.parameterName}=${_csrf.token}";
	$.ajax({
    	type: "POST",
        url: myTasksurl,
        success: function(data) {
        	 var json = JSON.parse(data);
        	 var result = "";
        	result = result + '	 <table id="myTasksPagination">'; 
        	result = result + '     <thead>';
        	result = result + '       <tr>';
        	result = result + '         <th>Subject</th>';
        	result = result + '         <th>Date &amp; Time</th>';
        	result = result + '         <th>Owner</th>';
        	result = result + '         <th>Assigned to</th>';
        	result = result + '         <th>Description</th>';
        	result = result + '         <th>Mode</th>';
        	result = result + '         <th>Status</th>';
        	result = result + '       </tr>';
        	result = result + '     </thead>';
        	result = result + '     <tbody>';
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        		var selected1 = '';
        		var selected2 = '';
        		
        		if(report.taskStatus == 'Open'){
        			 selected1 = 'selected';
				}
        		if(report.taskStatus == 'Close'){
					 selected2 = 'selected';
				}
        		
        		result = result + '       <tr>';
        		result = result + '         <td><div title="'+report.subject+'"><span>'+report.subject+'</span></div></td>';
            	result = result + '         <td><div class="text-center">1'+report.taskDateStr+'  '+report.taskTime+'</div></td>';
            	result = result + '         <td><div><span>'+report.taskOwnerName+'</span></div></td>';
            	result = result + '         <td><div><span>'+report.assignedName+'</span></div></td>';
            	result = result + '         <td><div><span>'+report.description+'</span></div></td>';
            	result = result + '         <td>'+report.alertType+'</td>';
            	result = result + '         <td><div class="dropdown-select tblminwidth-100">';
            	result = result + '             <select id="taskState'+report.id+'" name="taskState" onChange="updateTaskStatus(\''+report.id+'\');">';
            	result = result + '               <option '+selected1+'>Open</option>';
            	result = result + '               <option '+selected2+'>Close</option>';
            	result = result + '             </select>';
            	result = result + '           </div></td>';
            	result = result + '       </tr>';
			}
        	result = result + '     </tbody>';
        	result = result + '   </table>';
        	 $("#myTasksId").empty();
        	 $("#myTasksId").append(result); 
        	 myTasksPagination();
        }
	});
	 
}
</script>
 -->
<script type="text/javascript">
function tasksList(status){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/getTasksList?${_csrf.parameterName}=${_csrf.token}&status="+status,
        success: function(data) {
        	 var json = JSON.parse(data);
        	 var result = "";
       		if(status == "myTask"){
       			result = result + '	 <table id="myTasksPagination">'; 
       		}else if(status == "assignedTasks"){
       			result = result + '	 <table id="assignedTasksPagination">'; 
       		}else if(status == "closedTasks"){
       			result = result + '	 <table id="closedTasksPagination">'; 
       		}
        	result = result + '     <thead>';
        	result = result + '       <tr>';
        	result = result + '         <th>Subject</th>';
        	result = result + '         <th>Date &amp; Time</th>';
        	result = result + '         <th>Owner</th>';
        	result = result + '         <th>Assigned to</th>';
        	result = result + '         <th>Description</th>';
        	result = result + '         <th>Mode</th>';
        	result = result + '         <th>Status</th>';
        	result = result + '       </tr>';
        	result = result + '     </thead>';
        	result = result + '     <tbody>';
        	for (var i=0; i<json.length; i++)
			{
        		var report = json[i];
        	 
        		if(report.alertType != null && report.alertType != '' && report.alertType != 'undefined'){
        			alertType = report.alertType;
        		}else{
        			alertType = "---";
        		}
        		if(report.taskDateStr != null && report.taskDateStr != '' && report.taskDateStr != 'undefined'){
        			taskDateStr = report.taskDateStr
        		}else{
        			taskDateStr = "---";
        		}
        		if(report.taskTime != null && report.taskTime != '' && report.taskTime != 'undefined'){
        			taskTime = report.taskTime;
        		}else{
        			taskTime = "---";
        		}
        		if(report.description != null && report.description != '' && report.description != 'undefined'){
        			description = report.description;
        		}else{
        			description = "---";
        		}
        		result = result + '       <tr>';
        		result = result + '         <td><div title="'+report.subject+'"><span>'+report.subject+'</span></div></td>';
            	result = result + '         <td><div class="text-center">'+taskDateStr+'  '+taskTime+'</div></td>';
            	result = result + '         <td><div title="'+report.taskOwnerName+'"><span>'+report.taskOwnerName+'</span></div></td>';
            	result = result + '         <td><div title="'+report.assignedName+'"><span>'+report.assignedName+'</span></div></td>';
            	result = result + '         <td><div title="'+description+'"><span>'+description+'</span></div></td>';
            	result = result + '         <td>'+alertType+'</td>';
            	result = result + '         <td><div class="dropdown-select tblminwidth-100">';
            	
            		if(status == "myTask" || status == "assignedTasks" ){
            			result = result + '             <select id="taskState'+report.id+'" name="taskState" onChange="updateTaskStatus(\''+report.id+'\');">';
                    	if (report.taskStatus == 'Open') {
        					result = result	+ '<option selected >'+report.taskStatus+'</option>';
        				} else {
        					result = result + '<option>Open</option>';
        				}
                    	if (report.taskStatus == 'Closed') {
        					result = result	+ '<option selected >'+report.taskStatus+'</option>';
        				} else {
        					result = result + '<option>Closed</option>';
        				}
                    	result = result + '             </select>';
                    	 
            		}else if(status == "closedTasks"){
            			result = result + '             <select disabled>';
        					result = result	+ '<option>'+report.taskStatus+'</option>';
                    	result = result + '             </select>';
            		}
            	
            	result = result + '           </div></td>';
            	result = result + '       </tr>';
			}
        	result = result + '     </tbody>';
        	result = result + '   </table>';
        	
        	if(status == "myTask"){
	        	 $("#myTasksId").empty();
	           	 $("#myTasksId").append(result); 
	           	 myTasksPagination();
        	}else if(status == "assignedTasks"){
        		 $("#assignedTasksId").empty();
	           	 $("#assignedTasksId").append(result); 
	           	 assignedTasksPagination();
        	}else if(status == "closedTasks"){
        		 $("#closedTasksId").empty();
	           	 $("#closedTasksId").append(result); 
	           	 closedTasksPagination();
       		}
        	
        	 
        }
	});
	 
}
</script>

<script>
function myTasksPagination(){
    $('#myTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>

<script>
function assignedTasksPagination(){
    $('#assignedTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>
<script>
function closedTasksPagination(){
    $('#closedTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>