<!-- <link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet"> -->
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="resources/js/jquery.maskedinput-1.3.min.js"></script>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

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

<script>
	function predefineProfileImage(imageId,userId){
			$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<script>
$(document).ready(function() {
      approvalList();
});
</script>

<script>
  function approvalList(){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#timesheetApprList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	
	  var employeeId = [];
		$('input:checkbox[name=employeeById]:checked').each(function() 
		{
			employeeId.push($(this).val());
	   });
		
	  $.ajax({
           type : "POST",
            url: sourceleadUrl+"/getArrovalSubmissions?${_csrf.parameterName}=${_csrf.token}&employeeId="+employeeId,        
            success :function(data) {
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
                
          }
            
       }); 
  } 
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
				    		$("#row"+projectMemberUserId).empty();
					        $("#row"+projectMemberUserId).append(data);
					        /* pagination(); */
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
            <h2>Timesheet approval</h2>
          </div>
            <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; TimeSheet List</div>
        </section>
        
<div class="timesheet-content">
<div class="timesheet_out">
	<div class="timesheet_appr_wrap" >
		<div class="timesheet_appr_wrap">
		<div class="timesheet_head">
        	 <ul>
        	 <%-- <li><i class="fa fa-filter"></i><span>Employee</span><i class="glyphicon glyphicon-play" id="filter3ApprovePop"></i>|
                	<div id="filter3ApprovedPop" class="approved_pop">
                    	<ul>
                    	 <c:forEach var="report" items="${thslist}">  
                        	<li><input  id="employee${report.employeeId}" name="employeeById" value ="${report.employeeId}"  onclick="approvalList();"  type="checkbox"><label for="employee${report.employeeId}">${report.fullName}</label></li>
						  </c:forEach>
                        </ul>
                    </div>                	
                </li> --%>	
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
<jsp:include page="../masterFooter.jsp" />	