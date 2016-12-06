<!-- <link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet"> -->
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

 <style>
 
 .clsDatePicker {
   z-index: 100000 !important;
}
.side-bar .side-nav ul li span{
  display: inline-block;
  float: none;
}
/* .project_wrap_table span{
display: inline;
} */
 </style>
<style type="text/css">
.project_wrap {
overflow-x: auto;
}
</style>
<body>
      <!-- Content Area -->
       <section class="content-wraper">
        <section class="content-header clearfix">
        
        <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector" style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
          <div class="pull-left">
            <h2>Projects</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;ProjectList</div>
        </section>

        
	   <div class="timesheet_out">
	   <%-- <c:if test="${empty projectsList}">
			<div class="project_wrap">
        <div class="timesheet_head">
        	<ul>
				<li><i class="fa fa-filter"></i><span>Client</span><i class="glyphicon glyphicon-play"  id="filter1ApprovePop"></i>|
                	<div id="filter1ApprovedPop" class="approved_pop">
                    	<ul>
                    	  <c:forEach var="report" items="${add_client}">  
                        	<li><input  id="clientName${report.id}" name="clientNameById" value ="${report.id}"  onclick="filter();"  type="checkbox"><label for="clientName${report.id}">${report.clientName}</label></li>
						  </c:forEach>
                        </ul>
                    </div>                	
                </li>
                <li><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" id="filter2ApprovePop"></i>|
                	<div id="filter2ApprovedPop" class="approved_pop">
                    	<ul>
                        	<li><input id="c5" type="checkbox" name="projectStatus" value ="All" onclick="filter();"  ><label for="c5">All</label></li>
							<li><input id="c6" type="checkbox" name="projectStatus" value ="STARTED"  onclick="filter();"><label for="c6">STARTED</label></li>
							<li><input id="c7" name="projectStatus" type="checkbox" value ="INPROGRESS"  onclick="filter();"><label for="c7">INPROGRESS</label></li>
							<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="COMPLETED"  onclick="filter();"><label for="c8">COMPLETED</label></li>
               				<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="ONHOLD"  onclick="filter();"><label for="c8">ONHOLD</label></li>
                        </ul>
                    </div>                	
                </li>
                <sec:authorize access="hasAnyRole('ROLE_CREATE_PROJECT')"> 
	                   	<li><a href="<c:url value='/project'/>"><i class="fa fa-plus"></i>Create Project</a></li>		
	             </sec:authorize>	
                
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
			
		    <div id="projectFilterList"></div>
		    <div class="project_wrap_table">
		    				<table id="example" class="display" cellspacing="0" width="100%">
							</table>
			</div>	   
		</div>
		</c:if> --%>
		<%-- <c:if test="${!empty projectsList}"> --%>
		<div class="project_wrap">
        <div class="timesheet_head">
        	<ul>
				<li><i class="fa fa-filter"></i><span>Client</span><i class="glyphicon glyphicon-play"  id="filter1ApprovePop"></i>|
                	<div id="filter1ApprovedPop" class="approved_pop">
                    	<ul>
                    	  <c:forEach var="report" items="${add_client}">  
                        	<li><input  id="clientName${report.id}" name="clientNameById" value ="${report.id}"  onclick="filter();"  type="checkbox"><label for="clientName${report.id}">${report.clientName}</label></li>
						  </c:forEach>
                        </ul>
                    </div>                	
                </li>
                <li><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" id="filter2ApprovePop"></i>|
                	<div id="filter2ApprovedPop" class="approved_pop">
                    	<ul>
                        	<li><input id="c5" type="checkbox" name="projectStatus" value ="All" onclick="filter();"  ><label for="c5">All</label></li>
							<li><input id="c6" type="checkbox" name="projectStatus" value ="STARTED"  onclick="filter();"><label for="c6">STARTED</label></li>
							<li><input id="c7" name="projectStatus" type="checkbox" value ="INPROGRES-S"  onclick="filter();"><label for="c7">INPROGRESS</label></li>
							<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="COMPLETED"  onclick="filter();"><label for="c8">COMPLETED</label></li>
               				<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="ONHOLD"  onclick="filter();"><label for="c8">ONHOLD</label></li>
                        </ul>
                    </div>                	
                </li>
				<sec:authorize access="hasAnyRole('ROLE_CREATE_PROJECT')"> 
	                   	<li><a href="<c:url value='/project'/>"><i class="fa fa-plus"></i>Create Project</a></li>		
	             </sec:authorize>	
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
			
		    <div id="projectFilterList"></div>
		    <div class="project_wrap_table">
		    				<table id="example" class="display" cellspacing="0" width="100%">
							</table>
			</div>	   
				
		</div>
		<%-- </c:if> --%>
		<div class="side_mask"><div class="proj_close"><i></i></div></div>
		<div class="project_side_wrap">
			<div class="project_side_wrap_inn">
		    <h1 id="projectNameById"></h1> <div class="proj_close"><i></i></div>
		     <div id="projectSideDiv">
		     </div>   
			<div class="project_error">
				<p>Employee delete message here ....</p>
				<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
			</div>	
		    </div>
		  </div> 
		   </div>		
	   </section>
	   <div id="popUp"></div>
	  </body>
<!--   Popup's   -->


               
               <!--   Popup's   -->	
               <jsp:include page="employee.jsp" />
               <jsp:include page="projectDetails.jsp" />
               <jsp:include page="projectTasks.jsp" />
<jsp:include page="../masterFooter.jsp" />	
	
	
	
<script type="text/javascript">
window.onload = function () {
	$('input[name=projectStatus]').attr('checked',false);
	$('input[name=clientNameById]').attr('checked',false);
	filter();
 };
</script>



<script>
//project side bar
$(document).ready(function() {
     $(".proj_close, .side_mask").click(function(){
    	$(".project_side_wrap").removeClass("project_side_wrap_active");
 		$(".side_mask").fadeOut(200);
 		$("body").css('overflow-y', 'auto');
	});	
});
//project side bar tabs
$(document).ready(function() {
	$("#tab-1").show();
 $(".project-tabs-menu a").click(function(event) {
     event.preventDefault();
     $(this).parent().addClass("current");
     $(this).parent().siblings().removeClass("current");
     var tab = $(this).attr("href");
     $(".project_tabs_inn").not(tab).css("display", "none");
     $(tab).fadeIn();
 });
});

//project active dropdown
$(document).ready(function() {
 $("#ac-drop").click(function(){
		$(".project_details_cta ul").slideToggle(200);
	});
 $("html").click(function() {
     $(".project_details_cta ul").slideUp(200);
 });
 $(".project_details_cta ul, #ac-drop").click(function(e) {
     e.stopPropagation();
 });
});

//project error message
$(document).ready(function() {
 $(".project_tabs_inn_table i").click(function(){
		$(".project_error").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	});
 $(".project_error a").click(function() {
     $(".project_error").slideUp(200);
		$(".project_inn_mask").fadeOut(200);
 });
});
</script> 
<!-- this script is for side navigation functionality at Projects screen -->
 <script>
 function projectSideBar(projectId,projectName,startDateStr,endDateStr, status){ 
	  	    var result = "";
	  	    result = result + '<input type="hidden" name="projectId" id="projectId" value="'+projectId+'" >';
	  	    result = result + '<div class="project_side_wrap_inn_tabs">';
		  	result = result + '<ul class="project-tabs-menu">';
		  	
			'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'; 
		  	   result = result + ' <li class="current"><a href="#tab-1" onClick="projectDetails('+projectId+');" >Details</a></li>';
			'</sec:authorize>';
		  	
		  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'; 
		  	    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">' 
		  	         result = result + '<li><a href="#tab-2" onClick="projectEmployee('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Employees</a></li>';
		  	    '</sec:authorize>';
		  	    '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'
			  	    result = result + '<li class="current"><a href="#tab-2" onClick="projectEmployee('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Employees</a></li>';
			  	'</sec:authorize>';
		  	  '</sec:authorize>';
		  	
		  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">' ; 
			  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">' 
			      	 result = result + '<li><a href="#tab-3" onClick="projectTask('+projectId+',\''+status+'\');">Tasks</a></li>';
			  	'</sec:authorize>';
			  	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">' 
			  	     result = result + '<li class="current"><a href="#tab-3" onClick="projectTask('+projectId+',\''+status+'\');">Tasks</a></li>';
			  	'</sec:authorize>';
		  	'</sec:authorize>';
			
		  	result = result + ' <div class="clearfix"></div>';
		  	result = result + ' </ul>';
		  	 
		  	result = result + '<div class="project_tabs">';
		  	result = result + ' <div id="tab-1" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '<div id="tab-2" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '<div id="tab-3" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '</div>'; 
		  	$("#projectSideDiv").empty();  
		    $("#projectSideDiv").append(result);  
		    $("#projectNameById").empty(); 
		    $("#projectNameById").append(projectName); 
		    
		    $(".project_side_wrap").addClass("project_side_wrap_active");
		    $(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
			
		    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'; 
		        projectDetails(projectId);
			'</sec:authorize>';
			
			'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'; 
			    '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'; 
			       projectEmployee(projectId,startDateStr,endDateStr,status,projectName);
			    '</sec:authorize>';
			'</sec:authorize>';
			
			'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">' ; 
			 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'; 
			      projectTask(projectId, status)
			  '</sec:authorize>';
		  	'</sec:authorize>';
 }
 </script> 
 



<script>
$(document).ready(function() {
    $("#filter1ApprovePop").click(function(){
		$("#filter1ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
  
    
}); 

</script>

<!-- this script is for Projects Filter functionality at Projects screen -->

<script>
function pagination(){  
    $('#example').DataTable( { 
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
function filter(){
	
	var sourceleadUrl ='${sourceleadUrl}';
	var values = [];
	$('input:checkbox[name=projectStatus]:checked').each(function() 
    {
			values.push($(this).val());
    });
	
	var clients = [];
	$('input:checkbox[name=clientNameById]:checked').each(function() 
		    {
		clients.push($(this).val());
		    });
	
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getProjectsByStatusAndClient?${_csrf.parameterName}=${_csrf.token}&values="+values+"&clients="+clients, 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Id</th>';
   		    	result = result +'    <th>Name</th>'; 
   		    	result = result +'     <th>Manager</th>';
   		    	result = result +'     <th>Date</th>';
   		    	result = result +'      <th>Client</th>';
   		    	result = result +'       <th>Status</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{   
   		    		var report = json[i];
   		    		var projectSequeneNumber;
   		    		if(report.projectSequeneNumber != null && report.projectSequeneNumber != '' && report.projectSequeneNumber !='undefined'){
   		    				projectSequeneNumber = report.projectSequeneNumber;
   		    		}else{
   		    			projectSequeneNumber = "---";
   		    		}
   		    		result = result +'<tr>';
   		    		result = result +'<td data-title="Id"><div><span>'+projectSequeneNumber+'</span></div></td>';

   		    		
   		    		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'
   		    		  result = result +'<td data-title="Name"><div class="proj_show" onclick="projectSideBar('+report.id+',\''+report.projectName+'\',\''+report.employeeStartDateStr+'\',\''+report.employeeEndDateStr+'\',\''+report.projectStatus+'\');"><span>'+report.projectName+'</span><i class="fa fa-binoculars"></i></div></td>';
   		    		'</sec:authorize>';
   		    		
   		    		'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'
 		    		  result = result +'<td data-title="Name"><div class="proj_show"><span>'+report.projectName+'</span></div></td>';
 		    		'</sec:authorize>';

   		    		/*result = result +'<td data-title="Name"><div class="proj_show" onclick="projectSideBar('+report.id+',\''+report.projectName+'\',\''+report.employeeStartDateStr+'\',\''+report.employeeEndDateStr+'\',\''+report.projectStatus+'\');"><span class="tbldata-dotdot-vsm">'+report.projectName+'</span><i class="fa fa-binoculars"></i></div></td>';*/

   		    		result = result +'<td data-title="Manager">';
   		    		result = result +'<div><span>';
   		    		if(report.managerLevel1 !=null && report.managerLevel1 !='' && report.managerLevel1 !='undefined'){
   		    			result = result +report.managerLevel1;
   		    		}
   		    		if(report.managerLevel1 !=null && report.managerLevel1 !='' && report.managerLevel1 !='undefined' && report.managerLevel2!=null && report.managerLevel2!='' && report.managerLevel2 !='undefined'){
   		    			result = result + " , ";
   		    		}
   		    		if(report.managerLevel2!=null && report.managerLevel2!='' && report.managerLevel2 !='undefined'){
   		    			result = result +report.managerLevel2;
   		    		} 
   		    		result = result +'</span></div>';
   		    		result = result +'</td>';
   		    		result = result +'<td data-title="Date"><div>'+report.startDateStr+' ';
   		    		if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
   	   		    		result = result +'- '+report.endDateStr+' </div></td>';
   		    		}
   		    		if(report.client != null && report.client != '' && report.client != 'undefined'){
   		    			result = result +'<td data-title="Client"><div><span>'+report.client+'</span></div></td>';  
   		    		}else{
   		    			result = result +'<td data-title="Client"><div>---</div></td>';  
   		    		}
   		    		result = result +'<td data-title="Status"><div>';
   		    		if(report.projectStatus == 'STARTED'){
	    			  result = result +'<b>'+report.projectStatus+'</b>';
	    			}
   		    		if(report.projectStatus == 'INPROGRESS'){
  	    			  result = result +'<b class="proj_process">'+report.projectStatus+'</b>';
  	    			}
   		    		if(report.projectStatus == 'COMPLETED'){
  	    			  result = result +'<b class="proj_active">'+report.projectStatus+'</b>';
  	    			}
   		    		if(report.projectStatus == 'ONHOLD'){
  	    			  result = result +'<b class="proj_deactivate">'+report.projectStatus+'</b>';
  	    			}
   		    		
   		    		result = result +'</div></td>';
   		    		
                    result = result +'</tr>';
   				}
                result = result +'  </tbody>';
                result = result +' </table>';
                result = result +'</div>';
                $("#example").empty();  
   	    	    $("#example").append(result);
   	    	    pagination();	
   		    }
	 }); 
	
	
}	
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
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
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


<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 
 <script>
// Edit pop
$(document).ready(function() {	
	
    $("#edit-user, #btnClose").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
		
});
</script> 
    
</html>