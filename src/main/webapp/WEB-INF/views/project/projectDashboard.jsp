<!-- <link href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet"> -->
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!-- <link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>  -->


<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

 <style>
 
 .clsDatePicker {
   z-index: 100000 !important;
}
/* .side-bar .side-nav ul li span{
  display: inline-block;
  float: none;
} */
/* .project_wrap_table span{
display: inline;
} */
 </style>
<style type="text/css">
.project_wrap {
overflow-x: auto;
}
.proj_show span {
	padding: 0px 15px 0px 0px;
}
.proj_show i {
	position: absolute; right: 0px;
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
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;Projects</div>
        </section>

        
	   <div class="timesheet_out timesheet_background">
		
        <div class="timesheet_head topfilters">
        	<ul>
				<li id="filter1ApprovePop"><i class="fa fa-filter"></i><span>Client</span><i class="glyphicon glyphicon-play"  ></i>|
                	<div id="filter1ApprovedPop" class="approved_pop">
                    	<ul>
                    	  <c:forEach var="report" items="${add_client}">  
                        	<li><input  id="clientName${report.id}" name="clientNameById" value ="${report.id}"  onclick="filter();"  type="checkbox"><label for="clientName${report.id}">${report.clientName}</label></li>
						  </c:forEach>
                        </ul>
                    </div>                	
                </li>
                <li id="filter2ApprovePop"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" ></i>|
                	<div id="filter2ApprovedPop" class="approved_pop">
                    	<ul>
                        	<li><input id="c5" type="checkbox" name="projectStatus" value ="All" onclick="filter();"  ><label for="c5">All</label></li>
                        	<li><input id="c7" name="projectStatus" type="checkbox" value ="INPROGRESS"  onclick="filter();"><label for="c7">In-Progress</label></li>
<!-- 							<li><input id="c6" type="checkbox" name="projectStatus" value ="STARTED"  onclick="filter();"><label for="c6">Started</label></li> -->
							<li><input id="c9" type="checkbox" name="projectStatus" value ="NOTSTARTED"  onclick="filter();"><label for="c8">Not Started</label></li>
							<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="COMPLETED"  onclick="filter();"><label for="c8">Completed</label></li>
               				<li><input id="c8" type="checkbox"  name="projectStatus"  type="checkbox" value ="ONHOLD"  onclick="filter();"><label for="c8">On-Hold</label></li>
               				<li><input id="c10" type="checkbox"  name="projectStatus"  type="checkbox" value ="DROPPED"  onclick="filter();"><label for="c8">Dropped</label></li>
                        </ul>
                    </div>                	
                </li>
				<sec:authorize access="hasAnyRole('ROLE_CREATE_PROJECT')"> 
	                   	<li><a href="<c:url value='/project'/>"><i class="fa fa-plus"></i>Add Project</a></li>		
	             </sec:authorize>	
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
			
		    <div id="projectFilterList"></div>
		    <div class="project_wrap_table datatable_filter" onclick="removeErroeMsg();">
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
               
	
<script type="text/javascript">
window.onload = function () {
		projectsList("${searchValue}","${searchResult}","${projectId}","${projectName}","${startDate}","${endDate}","${status}","${projectKeyword}");
 };
</script>
<script type="text/javascript">
function projectsList(searchValue,searchresult,projectId,projectName,startDate,endDate,status,projectKeyword){ 
	if(searchresult=="universalProjectsList")	 {
		var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
	         type : "POST",
	          url: sourceleadUrl+"/getUniversalProjectsListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue,       
	          success :function(data) {
	        	  prepareProjectsList(data);
	         },
	          
	     }); 
	  }if(projectKeyword == "projectSearch"){
		  projectSideBar(projectId,projectName,startDate,endDate,status);
		  filter();
		  
	  }else{
		  	$('input[name=projectStatus]').attr('checked',false);
		  	$("#c6").prop("checked", true);
		  	$("#c7").prop("checked", true);
			$('input[name=clientNameById]').attr('checked',false);
			filter();
		/* searchProject(); */
		}
}
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
		  	         result = result + '<li><a href="#tab-2" id="empTab" onClick="projectEmployee('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Employees</a></li>';
		  	    '</sec:authorize>';
		  	    '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'
			  	    result = result + '<li class="current"><a href="#tab-2" onClick="projectEmployee('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Employees</a></li>';
			  	'</sec:authorize>';
		  	  '</sec:authorize>';
		  	
		  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">' ; 
			  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">' 
			      	 result = result + '<li><a href="#tab-3" id="taskTab" onClick="projectTask('+projectId+',\''+status+'\');">Tasks</a></li>';
			  	'</sec:authorize>';
			  	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">' 
			  	     result = result + '<li class="current"><a href="#tab-3" onClick="projectTask('+projectId+',\''+status+'\');">Tasks</a></li>';
			  	'</sec:authorize>';
		  	'</sec:authorize>';
		  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'; 
		  	   result = result + ' <li><a href="#tab-4" onClick="projectDocuments('+projectId+',\''+status+'\');" >Documents</a></li>';
			'</sec:authorize>';
			
			
			
		  	result = result + ' <div class="clearfix"></div>';
		  	result = result + ' </ul>';
		  	 
		  	result = result + '<div class="project_tabs">';
		  	result = result + ' <div id="tab-1" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '<div id="tab-2" class="project_tabs_inn">';
		  	result = result + '<p> "hi" </p>';
		  	result = result + '</div>';
		  	
		  	result = result + '<div id="tab-3" class="project_tabs_inn datatable_filter">';
		  	result = result + '</div>';
		  	result = result + '<div id="tab-4" class="project_tabs_inn">';
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
		 $("#filter2ApprovedPop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
		$("#filter1ApprovedPop").slideUp(200);
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
	 $.fn.dataTable.moment( 'DD-MM-YYYY' );
    $('#example').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
		"aaSorting": [[4,'desc']],
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

<script>
function removeErroeMsg(){
	$(".dataTables_empty").html("");
	$(".odd").td(empty);
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
   		    	result = result +'    <th>Name111</th>'; 
   		    	result = result +'     <th>Manager</th>';
   		    	result = result +'     <th>Invoice Frequency</th>';
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
   		    		if(report.employeeEndDateStr != null && report.employeeEndDateStr != '' && report.employeeEndDateStr != 'undefined'){
   		    			employeeEndDateStr = report.employeeEndDateStr;
   		    		}else{
   		    			employeeEndDateStr = "";
   		    		}
   		    		result = result +'<tr>';
   		    		result = result +'<td data-title="Id"><div><span title='+projectSequeneNumber+'>'+projectSequeneNumber+'</span></div></td>';

   		    		
   		    		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'
   		    		  result = result +'<td data-title="Name"><div><a href="'+sourceleadUrl+'/projectSummary?projectUUID='+report.projectUUID+'"><span title="'+report.projectName+'">'+report.projectName+'</span></a></div></td>';
   		    		'</sec:authorize>';
   		    		
   		    		'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'
 		    		  result = result +'<td data-title="Name"><div class="proj_show"><span title="'+report.projectName+'">'+report.projectName+'</span></div></td>';
 		    		'</sec:authorize>';

   		    		/*result = result +'<td data-title="Name"><div class="proj_show" onclick="projectSideBar('+report.id+',\''+report.projectName+'\',\''+report.employeeStartDateStr+'\',\''+report.employeeEndDateStr+'\',\''+report.projectStatus+'\');"><span class="tbldata-dotdot-vsm">'+report.projectName+'</span><i class="fa fa-binoculars"></i></div></td>';*/

   		    		result = result +'<td data-title="Manager">';
   		    		result = result +'<div>';
   		    		if(report.managerLevel1 !=null && report.managerLevel1 !='' && report.managerLevel1 !='undefined'){
   		    			result = result +'<span title="'+report.managerLevel1+'">'+report.managerLevel1+'</span>';
   		    		}
   		    		if(report.managerLevel1 !=null && report.managerLevel1 !='' && report.managerLevel1 !='undefined' && report.managerLevel2!=null && report.managerLevel2!='' && report.managerLevel2 !='undefined'){
   		    			result = result + " , ";
   		    		}
   		    		if(report.managerLevel2!=null && report.managerLevel2!='' && report.managerLevel2 !='undefined'){
   		    			result = result +'<span title="'+report.managerLevel2+'">'+report.managerLevel2+'</span>'; 
   		    		} 
   		    		result = result +'</div>';
   		    		result = result +'</td>';
   		    		
   		    		
   		    		result = result +'<td data-title="Invoice Frequency">';
   		    		result = result +'<div><span title="'+report.invoiceFrequency+'">';
   		    		if(report.invoiceFrequency !=null && report.invoiceFrequency !='' && report.invoiceFrequency !='undefined'){
   		    			result = result +report.invoiceFrequency;
   		    		}
   		    		
   		    		result = result +'</span></div>';
   		    		result = result +'</td>';
   		    		var endDateStr = report.endDateStr;
   		    		if(report.endDateStr == null || report.endDateStr == '' || report.endDateStr == 'undefined'){
   		    			endDateStr = "";
   		    		}else{
   		    			endDateStr = ' - '+ endDateStr;
   		    		}
   		    		result = result +'<td data-title="Date"><div><span title=" '+report.startDateStr+endDateStr+'">'+report.startDateStr+'';
   		    		if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
   	   		    		result = result +' - '+report.endDateStr;
   		    		}
   		    		result = result +'</span></div>';
   		    		result = result +'</td>';
   		    		
   		    		if(report.client != null && report.client != '' && report.client != 'undefined'){
   		    			result = result +'<td data-title="Client"><div><span title="'+report.client+'">'+report.client+'</span></div></td>';  
   		    		}else{
   		    			result = result +'<td data-title="Client"><div>---</div></td>';  
   		    		}
   		    		result = result +'<td data-title="Status"><div>';
   		    		if(report.projectStatus == 'INPROGRESS'){
  	    			  result = result +'<b class="proj_process">In-Progress</b>';
  	    			}
   		    		if(report.projectStatus == 'COMPLETED'){
  	    			  result = result +'<b class="proj_active">Completed</b>';
  	    			}
   		    		if(report.projectStatus == 'ONHOLD'){
  	    			  result = result +'<b class="proj_deactivate">On-Hold</b>';
  	    			}
   		    		if(report.projectStatus == 'NOTSTARTED'){
   	    			  result = result +'<b class="proj_deactivate">Not-Started</b>';
   	    			}
   		    		if(report.projectStatus == 'DROPPED'){
   	    			  result = result +'<b class="proj_deactivate">Dropped</b>';
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
<script>
	// Close Advanced Search
	function closePop(){
		$("div.cand-advsrch-popup").hide();
	}
	function clearKeys(){
		clearSearchKeys();
	}
	function openPop(){
	  $("div.cand-advsrch-popup").toggle();
	  
	}
</script>
<script type="text/javascript">	
	function clearSearchKeys(){
	   /*  $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated"); */
	    $("#projectIdAdvanced").val('').trigger("chosen:updated");
	    $("#projectNameAdvanced").val('').trigger("chosen:updated");
	    $("#projectManagerAdvanced").val('').trigger("chosen:updated");
	    $("#invoiceFrequencyAdvanced").val('').trigger("chosen:updated");
	    $("#clientsAdvanced").val('').trigger("chosen:updated");
	    $("#statusAdvanced").val('').trigger("chosen:updated");
	    $("#employeeNameAdvanced").val('').trigger("chosen:updated");
	    $("#employeeEmailAdvanced").val('').trigger("chosen:updated");
	    
	    $("#projectStartDateAdvanced").val('');
	    
	    /* $("#anyOfKeyword").empty();
	    $("#allKeywords").empty();
	    $("#excludeKeyWord").empty(); */
	    $("#projectIdAdvanced").empty();
	    $("#projectNameAdvanced").empty();
	    $("#projectManagerAdvanced").empty();
	    $("#clientsAdvanced").empty();
	    $("#employeeNameAdvanced").empty();
	    $("#employeeEmailAdvanced").empty();
	}
</script>

	
<script type="text/javascript">
    	   var config = {
    				  '.chosen-select'           : {},
    				  '.chosen-select-deselect'  : {allow_single_deselect:true},
    				  '.chosen-select-no-single' : {disable_search_threshold:10},
    				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    				  '.chosen-select-width'     : {width:"95%"}
    	   }
			//Adding Customized Any Of Keyword to the list 
		   /*  var select, chosen;
		    select = $("#anyOfKeyword");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keyup', function(e)
		    {
		    	var anyKeyword = this.value.replace(",","");
		        if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(anyKeyword).text(anyKeyword);
		            select.append(option);
		            select.find(option).prop('selected', true);
                    $("#anyOfKeyword option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#anyOfKeyword").trigger("chosen:updated");
	        		
		        }
		    }); */
		    
		  //Adding Customized Any Of Keyword to the list
		  /*   var selectone, chosenone;
		    selectone = $("#allKeywords");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keyup', function(e1)
		    {
		    	var allKeyword = this.value.replace(",","");
		        if ((e1.which == 13 || e1.which == 188) && (allKeyword != null  && allKeyword != '' && allKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(allKeyword).text(allKeyword);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
                    $("#allKeywords option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#allKeywords").trigger("chosen:updated");
	        		
		        }
		    }); */
		    
		  /*   var selecttwo, chosentwo;
		    selecttwo = $("#excludeKeyWord");
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosentwo = selecttwo.data('chosen');
		    chosentwo.search_field.on('keyup', function(e2)
		    {
		    	var excludeKeyword = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (excludeKeyword != null  && excludeKeyword != '' && excludeKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(excludeKeyword).text(excludeKeyword);
		            selecttwo.append(option);
		            selecttwo.find(option).prop('selected', true);
                    $("#excludeKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#excludeKeyWord").trigger("chosen:updated");
	        		
		        }
		    }); */
		    
		    
		    var selectthree, chosenthree;
		    selectthree = $("#projectIdAdvanced");
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenthree = selectthree.data('chosen');
		    chosenthree.search_field.on('keyup', function(e2)
		    {
		    	var projectIdAdvanced = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (projectIdAdvanced != null  && projectIdAdvanced != '' && projectIdAdvanced != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(projectIdAdvanced).text(projectIdAdvanced);
		            selectthree.append(option);
		            selectthree.find(option).prop('selected', true);
                    $("#projectIdAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#projectIdAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selectfour, chosenthree;
		    selectfour = $("#projectNameAdvanced");
		    selectfour.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenthree = selectfour.data('chosen');
		    chosenthree.search_field.on('keyup', function(e2)
		    {
		    	var projectNameAdvanced = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (projectNameAdvanced != null  && projectNameAdvanced != '' && projectNameAdvanced != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(projectNameAdvanced).text(projectNameAdvanced);
		            selectfour.append(option);
		            selectfour.find(option).prop('selected', true);
                    $("#projectNameAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#projectNameAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selectfive, chosenfive;
		    selectfive = $("#projectManagerAdvanced");
		    selectfive.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenfive = selectfive.data('chosen');
		    chosenfive.search_field.on('keyup', function(e2)
		    {
		    	var projectManagerAdvanced = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (projectManagerAdvanced != null  && projectManagerAdvanced != '' && projectManagerAdvanced != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(projectManagerAdvanced).text(projectManagerAdvanced);
		            selectfive.append(option);
		            selectfive.find(option).prop('selected', true);
                    $("#projectManagerAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#projectManagerAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    /* var selectsix, chosensix;
		    selectsix = $("#invoiceFrequencyAdvanced");
		    selectsix.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosensix = selectsix.data('chosen');
		    chosensix.search_field.on('keyup', function(e2)
		    {
		    	var invoiceFrequencyAdvanced = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (invoiceFrequencyAdvanced != null  && invoiceFrequencyAdvanced != '' && invoiceFrequencyAdvanced != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(invoiceFrequencyAdvanced).text(invoiceFrequencyAdvanced);
		            selectsix.append(option);
		            selectsix.find(option).prop('selected', true);
                    $("#invoiceFrequencyAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#invoiceFrequencyAdvanced").trigger("chosen:updated");
	        		
		        }
		    }); */
		    
		    
		    var selectseven, chosenseven;
		    selectseven = $("#clientsAdvanced");
		    selectseven.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenseven = selectseven.data('chosen');
		    chosenseven.search_field.on('keyup', function(e2)
		    {
		    	var clientsAdvanced = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (clientsAdvanced != null  && clientsAdvanced != '' && clientsAdvanced != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(clientsAdvanced).text(clientsAdvanced);
		            selectseven.append(option);
		            selectseven.find(option).prop('selected', true);
                    $("#clientsAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#clientsAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    /* 
		    var selecteight, choseneight;
		    selecteight = $("#statusAdvanced");
		    selecteight.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    choseneight = selecteight.data('chosen');
		    choseneight.search_field.on('keyup', function(e2)
		    {
		    	var statusAdvanced = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (statusAdvanced != null  && statusAdvanced != '' && statusAdvanced != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(statusAdvanced).text(statusAdvanced);
		            selecteight.append(option);
		            selecteight.find(option).prop('selected', true);
                    $("#statusAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#statusAdvanced").trigger("chosen:updated");
	        		
		        }
		    }); */
		    
		    
		    var selectnine, chosennine;
		    selectnine = $("#employeeNameAdvanced");
		    selectnine.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosennine = selectnine.data('chosen');
		    chosennine.search_field.on('keyup', function(e2)
		    {
		    	var employeeNameAdvanced = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (employeeNameAdvanced != null  && employeeNameAdvanced != '' && employeeNameAdvanced != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(employeeNameAdvanced).text(employeeNameAdvanced);
		            selectnine.append(option);
		            selectnine.find(option).prop('selected', true);
                    $("#employeeNameAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#employeeNameAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selectten, chosenten;
		    selectten = $("#employeeEmailAdvanced");
		    selectten.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenten = selectten.data('chosen');
		    chosenten.search_field.on('keyup', function(e2)
		    {
		    	var employeeEmailAdvanced = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (employeeEmailAdvanced != null  && employeeEmailAdvanced != '' && employeeEmailAdvanced != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(employeeEmailAdvanced).text(employeeEmailAdvanced);
		            selectten.append(option);
		            selectten.find(option).prop('selected', true);
                    $("#employeeEmailAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#employeeEmailAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
</script>
<script>
	$('#projectStartDateAdvanced').datepicker({
		dateFormat : 'yy-mm-dd',
		//dateFormat : 'dd-mm-yy',
	      changeMonth: true,
	      changeYear: true,
	      showButtonPanel:true,
	        closeText:'Clear',
	        beforeShow: function( input ) {
	            setTimeout(function () {
	                $(input).datepicker("widget").find(".ui-datepicker-current").hide();
	                var clearButton = $(input ).datepicker( "widget" ).find( ".ui-datepicker-close");
	                clearButton.unbind("click").bind("click",function(){$.datepicker._clearDate( input );});
	            }, 1 );
	        },
		onSelect : function(selected) {
			var dt = new Date(selected);
			dt.setDate(dt.getDate() + 1);
		}
	});
	$('#projectStartDateAdvanced').attr('readonly', true);
</script> 
<script type="text/javascript">
	function searchProject() {
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		
		 if ($('#projectlist_form').validate(false, validationSettings)){ 
			
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#projectlist_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#projectlist_form')[0];
      		var formData = new FormData(form); 
			//It will escape all the special characters
			$("#search_project").attr("disabled", "disabled");
			$("#search_project").addClass('button').val('Processing..');

			$.ajax({
						type : "GET",
						url : sourceleadUrl+"/searchProject?json="+jsonData,
						success : function(data) {
							$("#search_project").addClass('button').val('Search');
							$("#search_project").removeAttr("disabled");
							closePop();
							prepareProjectsList(data);
						},
					}); 
			return true;
		 }else{ 
				return false;
		 } 
	} 
	
	$('body').on('blur', '#projectlist_form', function() {
		$("#projectlist_form").showHelpOnFocus();
		$("#projectlist_form").validateOnBlur(false, validationSettings);
	});
</script>

<script>
  function prepareProjectsList(data){
	  if(data != null && data != 'undefined' && data != "" && data != '[]'){
		    	var json = JSON.parse(data);
	        	var result = "";
		    	result = result +'<thead>';
		    	result = result +'	<tr>';
		    	result = result +'	<th>Id</th>';
		    	result = result +'    <th>Name</th>'; 
		    	result = result +'     <th>Manager</th>';
		    	result = result +'     <th>Invoice Frequency</th>';
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
		    		result = result +'<td data-title="Id"><div><span title='+projectSequeneNumber+'>'+projectSequeneNumber+'</span></div></td>';
	
		    		
		    		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'
		    			result = result +'<td data-title="Name"><div class="proj_show" onclick="projectSideBar('+report.id+',\''+report.projectName+'\',\''+report.startDate+'\',\''+report.endDate+'\',\''+report.projectStatus+'\');"><span title="'+report.projectName+'">'+report.projectName+'</span><i class="fa fa-binoculars"></i></div></td>';
		    		'</sec:authorize>';
		    		
		    		'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'
	    		  		result = result +'<td data-title="Name"><div class="proj_show"><span title="'+report.projectName+'">'+report.projectName+'</span></div></td>';
	    			'</sec:authorize>';
	
		    		result = result +'<td data-title="Manager">';
		    		result = result +'<div><span title="'+report.level1ManagerName+' , '+report.level2ManagerName+'">';
		    		if(report.level1ManagerName !=null && report.level1ManagerName !='' && report.level1ManagerName !='undefined'){
		    			result = result +report.level1ManagerName;
		    		}
		    		if(report.level1ManagerName !=null && report.level1ManagerName !='' && report.level1ManagerName !='undefined' && report.level2ManagerName!=null && report.level2ManagerName!='' && report.level2ManagerName !='undefined'){
		    			result = result + " , ";
		    		}
		    		if(report.level2ManagerName!=null && report.level2ManagerName!='' && report.level2ManagerName !='undefined'){
		    			result = result +report.level2ManagerName;
		    		}
		    		result = result +'</span></div>';
		    		result = result +'</td>';
		    		
		    		result = result +'<td data-title="Invoice Frequency">';
		    		result = result +'<div><span title="'+report.invoiceFrequency+'">';
		    		if(report.invoiceFrequency !=null && report.invoiceFrequency !='' && report.invoiceFrequency !='undefined'){
		    			result = result +report.invoiceFrequency;
		    		}
		    		
		    		result = result +'</span></div>';
		    		result = result +'</td>';
		    		
		    		result = result +'<td data-title="Date"><div><span title="'+report.startDate+'">'+report.startDate+'</span>';
		    		if(report.endDate != null && report.endDate != '' && report.endDate != 'undefined'){
	   		    		result = result +'- <span title="'+report.endDate+'">'+report.endDate+'</span> </div></td>';
		    		}
		    		if(report.clientName != null && report.clientName != '' && report.clientName != 'undefined'){
		    			result = result +'<td data-title="Client"><div><span title="'+report.clientName+'">'+report.clientName+'</span></div></td>';  
		    		}else{
		    			result = result +'<td data-title="Client"><div>---</div></td>';  
		    		}
		    		result = result +'<td data-title="Status"><div>';
		    		 
		    		if(report.projectStatus == 'INPROGRESS'){
		   			  result = result +'<b class="proj_process">'+report.projectStatus+'</b>';
		   			}
		    		if(report.projectStatus == 'COMPLETED'){
		   			  result = result +'<b class="proj_active">'+report.projectStatus+'</b>';
		   			}
		    		if(report.projectStatus == 'ONHOLD'){
		   			  result = result +'<b class="proj_deactivate">'+report.projectStatus+'</b>';
		   			}
		    		if(report.projectStatus == 'NOTSTARTED'){
		    			result = result +'<b class="proj_deactivate">'+report.projectStatus+'</b>';
		    		}
			    	if(report.projectStatus == 'DROPPED'){
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
	  }else{
     	 $("#example").removeAttr("style");
	         var result ="";
			 result = result+"			<div class='blank-pages'>";
			 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
			 result = result+"			        <p>No Projects Found</p>";
			 result = result+"			 </div>";
			$("#example").empty();
		    $("#example").append(result); 
		    $(".dataTables_info").empty();
		    $(".dataTables_paginate").empty();
      }
  }
</script>
</html>

<jsp:include page="../masterFooter.jsp" />	