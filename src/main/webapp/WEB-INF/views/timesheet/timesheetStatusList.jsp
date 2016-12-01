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

<style type="text/css">
.glyphicon-none:before {
    content: "\2122";
    color: transparent !important;
}
.timesheet-content {
background: #fff;
min-height: 100px;
clear:both;
display: inline-block;
width:100%;
}
.timesheet-content #example_filter {
position: relative !important;
top:0px !important;
}
</style>
<%-- <div class="wraper">
	<!-- Content Area -->
	<section class="content-wraper">
		<section class="content-header clearfix">
			<div class="pull-left">
				<h2>${status} List</h2>
			</div>
							    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; ${status} List</div>
			
		</section>

		
		
		  <div id="projectFilterList"></div>
		    <div class="timesheet_appr_table">
		    				<table id="timesheetApprList" class="display" cellspacing="0" width="100%">
							</table>
			</div>	  

</section>
</div> --%>
<div id="TimeshgeetStatusListId"></div>
<script type="text/javascript">
window.onload = function () {
	filter();
 };
</script>

<script>
function pagination(){ 
    $('#example').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
    		"sSearch": ""
    	}
    } );
}
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
		               		if( data.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	}
		               		else if (data.indexOf('exception') >= 0) {
		               			 location.replace(sourceleadUrl+"/errorPage");
		               		}
		               		else{
					    		 $("#timesheetApprList").removeAttr("style");
			                	 $("#timesheetApprList").empty();
			                     $("#timesheetApprList").append(data); 
			                     pagination();
					    	}
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
<!-- for dynamic timesheets copied from timesheetapproval.jsp -->
<script type="text/javascript">
function dynamicTimesheet(navigation ,startDate,projectMemberUserId,memberId,employeeId){ 
	      
			var sourceleadUrl ='${sourceleadUrl}';  
			$("#dynamic").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/dynamicTimesheet?${_csrf.parameterName}=${_csrf.token}&navigation="+navigation+"&startDate="+startDate+"&memberId="+memberId+"&projectMemberUserId="+projectMemberUserId+"&employeeId="+employeeId, 	   
				    success: function(data) {
				    	if( data.indexOf('Sign In') >= 0){
				    		location.replace(sourceleadUrl);
				    	}
	               		else if (data.indexOf('exception') >= 0) {
	               			 location.replace(sourceleadUrl+"/errorPage");
	               		}else{
	               			var uniqueSepartor = startDate.replace('-','').replace('-','');
	               			if('${param.status}' != 'REJECTED' && '${param.status}' != 'DRAFT'){
		               		    var dateFrequencyTdContent =  $("#datefrequency"+projectMemberUserId+uniqueSepartor).html();
					    		$("#row"+projectMemberUserId+uniqueSepartor).empty();
						        $("#row"+projectMemberUserId+uniqueSepartor).append(data);
						        $("#datefrequency"+projectMemberUserId+uniqueSepartor).html(dateFrequencyTdContent);
	               			}
	               			else{
	               			   var table = $('#example').DataTable();
	   	        		       table.row('#row'+projectMemberUserId+uniqueSepartor+'').remove().draw( false );
	               			}
	               			
				    	}
				        
				    }
		    });
}
</script>	


<!-- Common Scripts for approval timesheets and dashboard count links separated in to timesheetApprovalCommonScripts.jsp   -->
<jsp:include page="timesheetApprovalCommonScripts.jsp" />

<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>
              ${status == 'SUBMITTED' ? 'Waiting For Approval' : (status == 'DRAFT') ? 'Draft Timesheets' :(status == 'APPROVED') ? 'Approved Timesheets' : (status == 'REJECTED') ? 'Rejected Timesheets'  : ''} 
             </h2>
          </div>
            <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;  ${status == 'SUBMITTED' ? 'Waiting For Approval' : (status == 'DRAFT') ? 'Draft Timesheets' :(status == 'APPROVED') ? 'Approved Timesheets' : (status == 'REJECTED') ? 'Rejected Timesheets'  : ''}</div>
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
</div></div>
		
</section>
</body>
<!-- this script is for dynamicTimesheetpopUp when click on count -->	










 