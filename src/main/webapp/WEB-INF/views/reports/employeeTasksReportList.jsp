<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="/resources/amcharts/amcharts.js"></script>
<script src="/resources/amcharts/serial.js"></script>
<script src="/resources/amcharts/plugins/export/export.min.js"></script>
<link rel="stylesheet" href="/resources/amcharts/plugins/export/export.css" type="text/css" media="all" />

<!-- Content Area -->
<script>
 $(document).ready(function() {
	getEmployeeTaskRepotList();    
 });
 
 </script> 
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee Tasks</h2>
      </div>
      <div class="pull-right brud-crum">
        <a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a>
        &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>"> Report Dashboard </a>
        &raquo; <a href="<c:url value='${appPath}/projectReportList'/>"> Project Report </a>
        &raquo;  Employee Tasks</div>
    </section>
    <div class="candidate_wrap_table clearfix project_wrap">
      <div class="" id="payrollRegister" style="display: block;">
      
        <div class="clearfix positionRelative">
          <div class="positionRelative reportHead clearfix">
          
			  <span class="pull-left refreIcon mb5">
				  <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a>
			  </span>
          </div>
          <div id="loading"></div>
          <div class="datatable_filter dataSeaTop" id="employeeReportListDivId">
          
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>
 
 <script>
function getEmployeeTaskRepotList(){
	 var sourceleadUrl = '${sourceleadUrl}';
	 $("#loading").attr('style','position:absolute; width:100%; height:210%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
			type : "POST",
			url : sourceleadUrl+ "/getEmployeeTaskRepotList?${_csrf.parameterName}=${_csrf.token}",
			success : function(json) {
				var result = "";
			    result = result +'<table id="employeeTaskPagination">';
				result = result +' <thead>';
				result = result +'   <tr>';
				result = result +'     <th>Employee Name</th>';
				for (var i=0; i<1; i++)
   				{
					var report = json[i];
					if(report != null && report != "" && report != "undefined"){
						for(var k in report.assignMembersMap){
							result = result +'     <th>'+k+'</th>';
						}
					}
					
   				}
				result = result +'   </tr>';
				result = result +' </thead>';
				result = result +' <tbody>';
				for (var i=0; i<json.length; i++)
   				{
   		    		var report = json[i];
   		    		
   		    		result = result +'<tr>';
   		    		result = result +' <td> ';
   		            result = result +'<div class="contactUsername text-left cursorPointer" ><a href="<c:url value="/app/employeeTasksReportSummary?candidateId='+report.candidateId+'"/>"">';
   		            if(report.fileUrl != null && report.fileUrl != "" && report.fileUrl != "undefined"){
   				        result = result +'	  <i class="userImage inlineBlock"><img src='+report.fileUrl+' alt="image" onerror="predefineProfileImage(this);"></i>';
   		            }else{
   				        result = result +'	  <i class="userImage inlineBlock"><img src="images/client-img.png" alt="image" onerror="predefineProfileImage(this);"></i>';
   		            }
			        result = result +'	   '+report.firstName+' '+report.lastName+'';
			        result = result +' </a></div>';
			        result = result +' </td> ';	   
			        for(var k in report.assignMembersMap){
			        	result = result +' <td><div class="tblminwidth-50 cursorPointer text-primary"><a href="<c:url value="/app/employeeTasksReportSummary?candidateId='+report.candidateId+'&taskType='+k+'"/>"">'+report.assignMembersMap[k]+'</a></div></td>';	
			        }
   		    		result = result +'</tr>';
				}
				result = result +' </tbody>';
				result = result +'</table>';
				$("#employeeReportListDivId").empty();
				$("#employeeReportListDivId").append(result);
				$("#loading").removeAttr('style');
				employeeTaskPagination();
			}
		})
			
}
 
</script>
<script>
 function resetFilter(){
	 getEmployeeTaskRepotList();
 }
 </script>
 <script>
 function employeeTaskPagination(){
		$('#employeeTaskPagination').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		"sZeroRecords": "", "sEmptyTable": ""
	     	}
	    } );
	}
 </script>
   

<jsp:include page="userUsageReportsGraph.jsp" />
<jsp:include page="../masterFooter.jsp" />