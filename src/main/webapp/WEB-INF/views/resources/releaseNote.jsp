<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
	getReleaseNoteList();
});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Release Note</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='/app/dashboard'/>"> Home </a>  &raquo; Release Note List</div>
    </section>
    
    <div class="project_wrap">
    
    <div class="positionRelative  timesheet_head clearfix">
          <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh"></a></span>
      
          <ul class="pull-right list-unstyled list-inline">
            <li><a class="action newAdBtn mr5" href="${appPath}/addReleaseNote">+ Add</a></li>
<!--           <li class=""><input class="repSearchBar" placeholder="Search" type="text"><span class="repSearchIcon"></span></li> -->
        </ul>
      </div>   
      
        <div class="project_tabs_inn_table datatable_filter">
         <div id="releaseNoteListDiv"></div>
        </div>
                
    </div>
  </section>

<script>
 function getReleaseNoteList(){
	 var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "GET",
			url : sourceleadUrl+ "/getReleaseNoteList?${_csrf.parameterName}=${_csrf.token}",
			success : function(json) {
   	        	var result = "";
   	        	result = result +'<table id="releaseNotePagination">';
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Name</th>';
   		    	result = result +'    <th>Release Date</th>'; 
   		    	result = result +'     <th>Publish Date</th>';
   		    	result = result +'     <th>Action</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{
   		    		var report = json[i];
   		    		result = result +'<tr>';
	                result = result +'<td>'+report.releaseName+'</td>';
	                result = result +'<td>'+report.releaseDateStr+'</td>';
	                result = result +'<td>'+report.publishDateStr+'</td>';
	                result = result +'<td>';
	                result = result +' <div class="">';
	                result = result +'  <a href="<c:url value="${appPath}/viewReleaseNote?releaseUUID='+report.releaseUUID+'"/>" class="text-warning"> <i class="fa fa-eye mr5" title="View File"></i></a>';
	                result = result +'   <a href="<c:url value="${appPath}/addReleaseNote?releaseUUID='+report.releaseUUID+'"/>" class="text-warning" title="Edit"><i class="fa fa-pencil mr5" title="edit file"></i></a>';
	                result = result +'   <i class="fa fa-trash" title="Delete" onclick="deleteReleaseNote(\''+report.releaseUUID+'\');"></i> ';                               
	                result = result +' </div>';
	                result = result +'  </td>';    
	                result = result +'</tr>  ';
   				}
   		    	$("#releaseNoteListDiv").empty();  
   	    	    $("#releaseNoteListDiv").append(result);
   	    	    releaseNotePagination();
			},
		});
 }
 </script>
 
 <script>
 function deleteReleaseNote(releaseUUID){
	 var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "GET",
			url : sourceleadUrl+ "/deleteReleaseNote?${_csrf.parameterName}=${_csrf.token}&releaseUUID="+releaseUUID,
			success : function(result) {
				 if (result && result.response === "INVALID_DATA") {
					    showError(result.responseMessage);
					} else if (result.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						location.replace(sourceleadUrl+"/releaseNote");
					} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
			}
		});
 }
 </script>
 
 <script>
function releaseNotePagination(){  
    $('#releaseNotePagination').DataTable( { 
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



<jsp:include page="../masterFooter.jsp" />