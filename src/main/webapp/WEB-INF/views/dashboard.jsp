<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- <link href="resources/css/jquery-ui.css" rel="stylesheet"> -->



<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style>
.dashboard-widget {
/* min-height: 400px !important; */
}
.dashboard-widget.dashboard-widget-icons{
min-height: 125px !important;
}
.dashboard-widget .blank-pages{
padding: 0;
min-height: 100px;
}
/* .side-bar .side-nav ul li span {
display: inline-block !important;
float: none !important;
} */
.custom-datatble table{
 margin-top: 20px !important;
 }
 #requirementList {
	border-bottom: 1px solid #eae6e6;
}
 .custom-datatble .dataTables_filter{
   top: -35px !important;
    padding: 0 !important;
 }
 .dashboard-datepkr{
       position:relative;
}
/* .main-header span.logo i{
position: relative;
top:10px;
} */
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

 <!--  <script type="text/javascript">
 $( document ).ready(function() {
	 assignTasksList();
	});
</script> -->
  
<section class="content-wraper">
	    <section class="content-header clearfix">
	      
	      <c:if test="${!empty param.invitation}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>You accepted invitation successfully.</span>
			</div>
	     </c:if>
	     
	     <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector1"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
	    
	      <div class="pull-left">
	        <h2>Dashboard</h2>
	      </div>
	    </section>
	    
	    <div class="clearfix row mb">
	      <sec:authorize access="hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')"> 
			 <c:if test = "${SourceLeadWeb_timesheet == 'Y'}">       
			   <jsp:include page="dashboardTimesheets.jsp"></jsp:include>  
			 </c:if> 
	      </sec:authorize>
			 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
				<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
					<jsp:include page="dashboardRequirement.jsp"></jsp:include> 
				</c:if>
		     </sec:authorize>
		     
	    </div>
	 <sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES','ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">   
	    <div class="clearfix row mb">
		     <jsp:include page="expensesDashboardCounts.jsp"></jsp:include>
	    </div>
	 </sec:authorize>  	
	 
	    	<c:if test = "${SourceLeadWeb_ats == 'Y' || SourceLeadWeb_vms == 'Y'}">
	    	
	    			<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
					<jsp:include page="dashboardAssignedRequirements.jsp"></jsp:include> 
					</sec:authorize>
					
					<sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_USER','ROLE_VIEW_EDIT_ON_BOARD_USER')">
					<jsp:include page="onboardingCandidatesList.jsp"></jsp:include> 
					</sec:authorize>
					
			    <div class="clearfix row mb"> 
			    	 <sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
			   	 	 <jsp:include page="dashboardRequirementApprovals.jsp"></jsp:include>
			   	 	 </sec:authorize>
			   	 	 <%--  <sec:authorize access="hasAnyRole('ROLE_VIEW_INTERVIEW')">
			    	 <jsp:include page="dashboardInterviews.jsp"></jsp:include>
			    	 </sec:authorize> --%>
			    </div>
		    </c:if>
		     <div class="clearfix row mb"> 
		     		<sec:authorize access="hasAnyRole('ROLE_VIEW_REQUIREMENT')">
		     		<jsp:include page="dashboardApplicants.jsp"></jsp:include>
		     		</sec:authorize>
		     </div>
	    
	    
	   
	    
	    <!-- <div class="row clearfix"> -->
	    <!-- <div class="col-md-6 mb">

        <div class="clients_wrap_table dashboard-widget overflow_inherint"  id="assignTaskListDiv" style="display: none;">

           <h4 class="clearfix">Tasks
        <b class="dashboard-datepkr"><input class="input-sm" id="dasboardTaskDatpicker" readonly="readonly" type="text"><i class="fa fa-calendar"></i></b>  
          </h4>	
          <div class="clearfix require_wrap_table dashboard-data Tasks overflow_inherint">
            <div id="assignTaskList"></div>
            
          </div>
        </div>
        Pagenation Ends 
      </div> 
      </div>-->
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
	    
	    <div class="clearfix"></div> 
	    
	     	<jsp:include page="dashboardEvents.jsp"></jsp:include>	    
	  
</section>



<jsp:include page="masterFooter.jsp" />
<script type="text/javascript">
$('#dasboardTaskDatpicker').datepicker({
	dateFormat : 'dd-mm-yy',
	changeMonth: true,
	changeYear: true, 
	onSelect : function(selected) {
		assignTasksList();
	   }
	});
</script>

	
<script type="text/javascript">
	function assignTasksList() {
		var sourceleadUrl ='${sourceleadUrl}';
		var date = $("#dasboardTaskDatpicker").val();
		var status =  "myTask";
		
		$.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getTasksList?${_csrf.parameterName}=${_csrf.token}&status="+status+"&date="+date,
	        success: function(data) {
	        	 var json = JSON.parse(data);
	        	 var result = "";
	        	 result = result + '<table id="dasboardTasksPagination">'; 
	        	 result = result + '<thead>';
	         	result = result + '<tr>';
	         	result = result + '<th>Subject</th>';
	         	result = result + '<th>Date &amp; Time</th>';
	         	result = result + '<th>Priority</th>';
	         	result = result + '<th>Status</th>';
	         	result = result + ' </tr>';
	        	result = result + ' </thead>';
	        	result = result + ' <tbody>';
	        	for (var i=0; i<json.length; i++)
				{
	        		var report = json[i];
	        		
	        		if(report.taskDateStr != null && report.taskDateStr != '' && report.taskDateStr != 'undefined'){
	        			taskDateStr = report.taskDateStr
	        		}else{
	        			taskDateStr = "--";
	        		}
	        		if(report.taskTime != null && report.taskTime != '' && report.taskTime != 'undefined'){
	        			taskTime = report.taskTime;
	        		}else{
	        			taskTime = "";
	        		}
	        		if(report.priority != null && report.priority != '' &&  report.priority != 'undefined'){
	        			priority = report.priority;
	        		}else{
	        			priority = "--";
	        		}
	        		
	        		result = result + '<tr>';
	        		result = result + ' <td><div title="'+report.subject+'"><span>'+report.subject+'</span></div></td>';
	        		result = result + '<td><div class="text-center">'+taskDateStr+'  '+taskTime+'</div></td>';
	        		result = result + '<td>'+priority+'</td>';
	        		result = result + ' <td>'+report.taskStatus+'</td>';
	        		result = result + ' </tr>';
	               
				}
	        	 result = result + '     </tbody>';
	        	 result = result + '   </table>';
	        	 $("#assignTaskList").empty();
	           	 $("#assignTaskList").append(result); 
	           	 if(json.length > 0){
	           		$("#assignTaskListDiv").css("display", "block");
	           	 }
	           	 
	           	dasboardTasksPagination();
	        }
		});
		
	}

	</script>
	
	<script>
function dasboardTasksPagination(){
    $('#dasboardTasksPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
  $("#selector").fadeIn(300);
  $('#selector').delay(3000).fadeOut('slow');
</script> 

<script>
  $("#selector1").fadeIn(300);
  $('#selector1').delay(3000).fadeOut('slow');
</script> 