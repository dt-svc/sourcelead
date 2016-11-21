

<%-- 
<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
    	 <c:if test="${not empty msg}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
      <div class="pull-left">
        <h2>Dashboard</h2>
      </div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="clearfix section-action-btns hidden">
        <ul class="pull-right list-unstyled list-inline">
          <li><i class="fa fa-filter" aria-hidden="true"></i>&nbsp;Filers&nbsp;</a>
			<select>
			<option>All</option>
				<option>This Week</option>
				<option>Month</option>
				<option>Year</option>
			</select>
		  </li>
        </ul>
	</section>
        <div class="clearfix row db-widget-sm">

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-orange">
				<i class="fa fa-floppy-o" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/NEW" >Saved</a></p>
              <p class="info-box-number"><span id="savedTimesheetsSubmissionsCount"></span></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-blue">
				<i class="fa fa-clock-o" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/SUBMITTED" >Waiting</a></p>
              <p class="info-box-number"><span  id="waitingTimesheetsSubmissionsCount"></span></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-green">
				<i class="fa fa-check-circle-o" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/APPROVED" >Approved</a></p>
              <p class="info-box-number"><span id="approvalTimesheetsSubmissionsCount"></span></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->

		<!-- /.info-box Ends -->
		<div class="col-md-3 col-sm-6 col-xs-12">
		<div class="info-box">
            <span class="info-box-icon bg-red">
				<i class="fa fa-ban" aria-hidden="true"></i>
			</span>
            <div class="info-box-content text-center">
              <p class="info-box-text"><a href="/sourcelead/totalTimesheetList/REJECTED" >Rejected</a></p>
              <p class="info-box-number"><span id="rejectedTimesheetsSubmissionsCount"></span></p>
            </div>
          </div>
        </div>
          <!-- /.info-box Ends -->
		  
		</div>
		
		<!-- Pie chart -->
			<!-- <div class="clearfix row">
				<section class="clearfix">
				 <div class="dropdown col-xs-2">
					<button class="btn btn-primary dropdown-toggle  btn-block" type="button" data-toggle="dropdown">Select Client
					&nbsp;&nbsp;<span class="caret"></span></button>
					<ul class="dropdown-menu">
					  <li><a href="#">Client</a></li>
					  <li><a href="#">Client</a></li>
					  <li><a href="#">Client</a></li>
					</ul>
				  </div>
				  
				 
				  <div class="dropdown col-xs-2">
					<button class="btn btn-success dropdown-toggle  btn-block" type="button" data-toggle="dropdown">Select Projects
					&nbsp;&nbsp;<span class="caret"></span></button>
					<ul class="dropdown-menu">
					  <li><a href="#">Project-1</a></li>
					  <li><a href="#">Project-2</a></li>
					  <li><a href="#">Project-3</a></li>
					</ul>
				  </div>
				</section>
				Highchart
				<div class="col-md-12">
				<section class="clearfix piechart-area mt">
						<h3>Timesheet Highchart</h3>
				</section></div>
				Highchart Ends
			</div> -->
				
				
			</div>
		<!-- Pie chart -->
  </section>
  <!-- Content Area Ends-->
 
  
  <script> 
$(document).ready(function(){
	
	timeSheetSubmissionStatusCount();
	
});
</script>
  

<script type="text/javascript">


function timeSheetSubmissionStatusCount(){
    var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getTimeSheetSubmissionStatus", 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	
		        	var  savedTimesheetsSubmissionsCount = json.savedTimesheetSubmissionCount;
		        	var  waitingTimesheetsSubmissionsCount =json.waitingForApprovalTimesheetSubmissionCount;
		        	var  approvalTimesheetsSubmissionsCount =json.approvedTimesheetSubmissionCount;
		        	var  rejectedTimesheetsSubmissionsCount = json.rejectedTimesheetSubmissionCount; 
		        	
		        	if(savedTimesheetsSubmissionsCount>0){
	                      $("#savedTimesheetsSubmissionsCount").text(savedTimesheetsSubmissionsCount);
	                  }else{
	                      $("#savedTimesheetsSubmissionsCount").html("0");
	                      $("#savedTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	if(waitingTimesheetsSubmissionsCount>0){
	                      $("#waitingTimesheetsSubmissionsCount").text(waitingTimesheetsSubmissionsCount);
	                  }else{
	                      $("#waitingTimesheetsSubmissionsCount").html("0");
	                      $("#waitingTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	if(approvalTimesheetsSubmissionsCount>0){
	                      $("#approvalTimesheetsSubmissionsCount").text(approvalTimesheetsSubmissionsCount);
	                  }else{
	                      $("#approvalTimesheetsSubmissionsCount").html("0");
	                      $("#approvalTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	if(rejectedTimesheetsSubmissionsCount>0){
	                      $("#rejectedTimesheetsSubmissionsCount").text(rejectedTimesheetsSubmissionsCount);
	                  }else{
	                      $("#rejectedTimesheetsSubmissionsCount").html("0");
	                      $("#rejectedTimesheetsSubmissionsCount").removeAttr("href");
	                  }
		        	
		 		}
		  });
		 
	}

</script>


<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 

<jsp:include page="masterFooter.jsp" /> --%>


<style>
.dashboard-widget {
min-height: 400px !important;
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

 <jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="resources/css/jquery-ui.css" rel="stylesheet">


<section class="content-wraper">
	    <section class="content-header clearfix">
	      <div class="pull-left">
	        <h2>Dashboard</h2>
	      </div>
	     <!--  <div class="pull-right brud-crum">Dashboard</div> -->
	    </section>
	    
	    <div class="clearfix row mb">
	    <c:forEach var="report" items="${SourceLeadWeb_tabsList}">  
			<c:if test = "${report.tabName == 'Timesheets'}">      
			   <jsp:include page="dashboardTimesheets.jsp"></jsp:include>  
			</c:if>
		 </c:forEach>
		 <jsp:include page="dashboardRequirement.jsp"></jsp:include> 
	      	 <%-- <jsp:include page="dashboardTasks.jsp"></jsp:include> --%>
	    </div>
    
    	<jsp:include page="dashboardAssignedRequirements.jsp"></jsp:include>
	    <div class="clearfix row mb"> 
	   	 	 <jsp:include page="dashboardRequirementApprovals.jsp"></jsp:include>
	    	 <jsp:include page="dashboardInterviews.jsp"></jsp:include>
	    </div>
	    
	    <div class="clearfix row mb"> 
	   	 <%--  <jsp:include page="dashboardTimesheetsApprovals.jsp"></jsp:include> --%>
	    </div>
</section>
<jsp:include page="masterFooter.jsp" />

