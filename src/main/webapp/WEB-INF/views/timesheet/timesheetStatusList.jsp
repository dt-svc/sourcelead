<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />


<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- <link href="resources/css/jquery-ui.css" rel="stylesheet"> -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.3/jquery-ui.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/jquery-fun.js"></script>
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" > -->
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>
<script src="resources/js/formvalidator.js"></script>

<!-- <link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/chosen.jquery.min.js"></script>  -->

<!-- <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> -->
<!-- <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>  -->



<div id="TimeshgeetStatusListId"></div>
 <body  onclick="bodyClick();">
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2> ${status == 'SUBMITTED' ? 'Waiting For Approval' : (status == 'APPROVED' ? 'Approved List' :(status == 'REJECTED' ? 'Rejected List' :(status == 'DRAFT' ? 'Draft List' :'Timesheet Approval')))}</h2>
          </div>
            <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  ${status == 'SUBMITTED' ? 'Wait For Approval' : (status == 'APPROVED' ? 'Approved List' :(status == 'REJECTED' ? 'Rejected List' :(status == 'DRAFT' ? 'Draft List' :'Timesheet Approval')))}</div>
            <div class="clearfix"></div>
        </section>
	<div class="timesheet-content">
	<div class="timesheet_out">
		<div class="timesheet_appr_wrap" >
			<div class="timesheet_appr_wrap">
			<div class="timesheet_head">
	        	 <ul>
	            </ul> 
	            <div class="clearfix"></div>
	        </div> 
			
			<div class="timesheet_appr_table datatable_filter pt5">
					<div id="timesheetApprList">   </div>
		    </div>
		</div>
		
	</div>
	</div>
	
	</div>
</section>
</body>
<script type="text/javascript">
window.onload = function () {
	filter();
 };
</script>
 <script type="text/javascript">
function filter(){
	var status = '${status}';
	 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getTimesheetListByStatus?${_csrf.parameterName}=${_csrf.token}&status="+status,
		        success: function(data) {
		     /*    $("#timesheetApprList").empty();  
		   	    $("#timesheetApprList").append(data);
		    	pagination(); */
		        	  if(data != null && data != 'undefined' && data != ""){
					    		 $("#timesheetApprList").removeAttr("style");
			                	 $("#timesheetApprList").empty();
			                     $("#timesheetApprList").append(data); 
			                     pagination();
		                 }else{
		                	 $("#timesheetApprList").removeAttr("style");
		  	    	         var result ="";
				    	     result = result+"   <div class='row'>"; 
							 result = result+"	<div class='col-lg-12'>"; 
							 result = result+"		<div class='content-area clearfix'>"; 
							 result = result+"			<div class='blank-pages'>";
							 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
							 result = result+"			        <p>No Timesheets Found</p>";
							 result = result+"			 </div>";
							 result = result+"		</div>"; 
							 result = result+"	</div>"; 
							 result = result+"  </div> "; 
							$("#timesheetApprList").empty();
						    $("#timesheetApprList").append(result);
		                 }
		                
		        },
		    });
} 
</script>
<jsp:include page="TimesheetApprovalCommonScripts.jsp" />

<jsp:include page="../masterFooter.jsp" />








 