<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet">
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>

<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png"
	href="resources/images/fav.png" />
<link
	href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/register-style.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>



<div id="TimeshgeetStatusListId"></div>
 <body  onclick="bodyClick();">
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Timesheet approval</h2>
          </div>
          <div class="clearfix"></div>
          		
            <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; TimeSheet List</div>
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
			
			<div class="timesheet_appr_table">
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








 