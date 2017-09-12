<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <style>
 .clsDatePicker {
   z-index: 100000 !important;
}
.user_summary_edit_cta {
margin: 0.1% 2%;
}
 </style>

      <!-- Content Area -->
       <section class="content-wraper">
        <section class="content-header clearfix">
         <h2 id="projectNameById" class="pull-left"></h2>
        <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector" style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/projectDashboard'/>">Projects List</a> &raquo;Project Summary</div>
        </section>
	   
		   
		     <div id="projectSideDiv">
		     </div>   
			<div class="project_error">
				<p>Employee delete message here ....</p>
				<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
			</div>	
	   </section>
 	   <div id="popUp"></div>
            <!--   Popup's   -->	
            <jsp:include page="employee.jsp" />
            <jsp:include page="projectDetails.jsp" />
            <jsp:include page="projectTasks.jsp" />
            <jsp:include page="projectDocuments.jsp"/>
           
            <jsp:include page="../masterFooter.jsp" />	

	
	
<script type="text/javascript">
window.onload = function () {
	var projectId = '${projectId}';
	var projectName = '${projectName}';
	var projectStartDate = '${projectStartDate}';
	var projectEndDate = '${projectEndDate}';
	var projectStatus = '${projectStatus}';
	
	var tabType = '${tabType}';
	projectSideBar(projectId,projectName,projectStartDate,projectEndDate,projectStatus);
 };
</script>

 
 <script>
 function projectSideBar(projectId,projectName,startDateStr,endDateStr, status){ 
		    var tabType = '${tabType}';
	  	    var result = "";
	  	    result = result + '<input type="hidden" name="projectId" id="projectId" value="'+projectId+'" >';
	  	    result = result + '<div class="project_side_wrap_inn_tabs">';
		  	result = result + '<ul class="project-tabs-menu">';
		  	
			'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'; 
		  	   result = result + ' <li class="current" id="details"><a href="#tab-1" onClick="projectDetails('+projectId+');" >Details</a></li>';
			'</sec:authorize>';
		  	
		  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">'; 
		  	    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">' 
		  	         result = result + '<li id="requestResources"><a href="#tab-2" id="empTab" onClick="projectEmployee('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Employees</a></li>';
		  	    '</sec:authorize>';
		  	    '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'
			  	    result = result + '<li class="current"><a href="#tab-2" onClick="projectEmployee('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Employees</a></li>';
			  	'</sec:authorize>';
		  	  '</sec:authorize>';
		  	
		  	
		  	  if(${postedOrgLocationId} == ${SourceLeadWeb_orgLocations.id}){
		  		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">' ; 
				  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">' 
				      	 result = result + '<li id="projectTaskTab"><a href="#tab-3" id="taskTab" onClick="projectTask('+projectId+',\''+status+'\');">Tasks</a></li>';
				  	'</sec:authorize>';
				  	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">' 
				  	     result = result + '<li class="current"><a href="#tab-3" onClick="projectTask('+projectId+',\''+status+'\');">Tasks</a></li>';
				  	'</sec:authorize>';
			  	'</sec:authorize>';
			  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'; 
			  	   result = result + ' <li><a href="#tab-4" onClick="projectDocuments('+projectId+');" >Documents</a></li>';
				'</sec:authorize>';
		  	  }
		  	 
			
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
		  	if(tabType != null && tabType != '' && tabType != 'undefined'){
				 if(tabType == "tasks"){
					 document.getElementById('projectTaskTab').className = 'current'
					 projectTask(projectId, status)
				 }
				 if(tabType == "projectMembers"){
					 document.getElementById('requestResources').className = 'current'
					 projectEmployee(projectId,startDateStr,endDateStr,status,projectName);
					 document.getElementById('projectMembersTab').click();
				 }
				 else{
					 document.getElementById('requestResources').className = 'current'
					 projectEmployee(projectId,startDateStr,endDateStr,status,projectName);
					 document.getElementById('resourceTab').click();
				 }
			     document.getElementById("details").removeAttribute("class")
			}else{
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
 }
 </script> 
 

<script>
function removeErroeMsg(){
	$(".dataTables_empty").html("");
	$(".odd").td(empty);
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
    	   var config = {
    				  '.chosen-select'           : {},
    				  '.chosen-select-deselect'  : {allow_single_deselect:true},
    				  '.chosen-select-no-single' : {disable_search_threshold:10},
    				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    				  '.chosen-select-width'     : {width:"95%"}
    	   }
		    
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

</html>