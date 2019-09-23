<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <!-- <script>
	$(document).ready(function() {
		  <c:forEach var="section" items="${sourceleadSectionListForProjectTask}" varStatus="sectionOrder">
		  var sectionId = '${section.id}';
		  getCustomDependentFieldProjectTask(sectionId);
		 </c:forEach> 
	});
</script> -->
 

      <!-- Content Area -->
       <section class="content-wraper">
        <section class="content-header clearfix">
         <h2 id="projectNameById" class="pull-left"></h2>
        <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector" style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/projectDashboard'/>">Projects List</a> &raquo; Project Summary</div>
        </section>
	   
<div id="projectSideDiv" class="project_wrap"></div>   	   
	   
	   <div class="cearfix">
		  <div class="newTagsWrap">
			 <ul class="clearfix tagsWrap tagsWrapBor">
				<select multiple class="chosen-select form-control" name="tagName" id="projectTagName" data-placeholder="Add Tags" data-validation="validate_Space" data-validation-optional="true">
				</select>
			</ul>
			 <div class="clearfix"></div>
		  </div>
     </div>
     <div class="success_pop tagStyles" id="addProjectTagAlert">
		<i class="fa fa-check-circle"></i>
   		<span></span>
   </div>
   <div class="remove_pop tagStyles" id="deleteProjectTagAlert">
		<i class="fa fa-times"></i>
   		<span></span>
   </div>
 
		     
			<div class="project_error">
				<p>Employee delete message here ....</p>
				<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
			</div>	
	   </section>
 	   <div id="popUp"></div>
 	   <div id="teampopUp"></div>
 	   <div id="projectMemberDetailsPopUp"></div>
 	   <div id="projectMemberRateHistoryPopUp"></div>
            <!--   Popup's   -->	
            <jsp:include page="employee.jsp" />
            <jsp:include page="projectTeam.jsp" />
            <jsp:include page="projectDetails.jsp" />
            <jsp:include page="projectTasks.jsp" />
            <jsp:include page="projectDocuments.jsp"/>
           <jsp:include page="projectReports.jsp"/>
            <jsp:include page="../masterFooter.jsp" />	



<!--  ======================== Tags Related Scripts written by Laxman Start =============================== -->
 <script>
var select, chosen;
select = $("#projectTagName");
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
        $("#projectTagName option").each(function(){
              $(this).siblings("[value='"+ this.value+"']").remove();
        });
		$("#projectTagName").trigger("chosen:updated");
		saveProjectsTags("add");
    }
});

$('.chosen-select').chosen().on('change', function(evt, params) {
	if (params.selected && params.selected != undefined) {
		saveProjectsTags("add");
	}
	if (params.deselected && params.deselected != undefined) {
		saveProjectsTags("delete");
	}
	
});
$("#projectTagName_chosen").find('ul.chosen-choices > li input').attr("placeholder", "Add Tag")
</script>
<script>
function getProjectsTagsList(projectId){
	$.ajax({  
        type : "POST",
        url: sourceleadUrl+"/getProjectsTags?"+tokenParamName+"="+tokenValue+"&projectId="+projectId,        
        success :function(json) {
        	var result = "";
			$.each( json.tagsList, function( i, tags ) {
			  var existed = 0;
                if(json.projectsTags != null && json.projectsTags != 'undefined' && json.projectsTags != '') {
	                var res = [];
	                res = json.projectsTags.split(",");
	                res.forEach(function(item) {
	                	if(tags == item){
	                		existed = 1;
	                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
	                	}
	                });
                }
                if(existed == 0){
                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
                }
			});
            $("#projectTagName").empty();
            $("#projectTagName").append(result);
            $("#projectTagName").chosen().trigger("chosen:updated")
        },
	});
}
</script>
<script>
function saveProjectsTags(type){
	 var projectId = '${projectId}';
	 var tagName = $("#projectTagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = sourceleadUrl+"/saveOrUpdateTagsByProjectId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&projectId="+projectId+"&tagName="+jsonData
	 } else {
		 Urlcheck = sourceleadUrl+"/saveOrUpdateTagsByProjectId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&projectId="+projectId+"&tagName="+""
	 } 
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		if(type == "add"){
        			showSuccessStatusPopup("Tag added successfully");
        		}else if(type == "delete"){
        			showDeleteStatusPopup("Tag deleted successfully");
        		}
        		getProjectsTagsList(projectId)
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
       },
    }); 
}
function showSuccessStatusPopup(status) {
	$("#addProjectTagAlert").css("display","block");
	$("#addProjectTagAlert span").html(status);
    $('#addProjectTagAlert').delay(2000).fadeOut('slow');
}
function showDeleteStatusPopup(status) {
	$("#deleteProjectTagAlert").css("display","block");
	$("#deleteProjectTagAlert span").html(status);
    $('#deleteProjectTagAlert').delay(2000).fadeOut('slow');
}
</script>
<!--  ======================== Tags Related Scripts written by Laxman Ends =============================== -->


	
	
<script type="text/javascript">
window.onload = function () {
	var projectId = '${projectId}';
	var projectName = '${projectName}';
	var projectStartDate = '${projectStartDate}';
	var projectEndDate = '${projectEndDate}';
	var projectStatus = '${projectStatus}';
	var tabType = '${tabType}';
	getProjectsTagsList(projectId);
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
		  	         result = result + '<li id="requestResources"><a href="#tab-2" id="empTab" onClick="projectEmployee('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Planning</a></li>';
				  	  result = result + '<li class="" id="projectTeam"><a href="#tab-6" id="teamTab" onClick="projectTeam('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Team</a></li>';
		  	         '</sec:authorize>';
		  	    '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\')">'
			  	    result = result + '<li class=""><a href="#tab-2" onClick="projectEmployee('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Planning</a></li>';
			  	    result = result + '<li class="" id="projectTeam"><a href="#tab-6" onClick="projectTeam('+projectId+',\''+startDateStr+'\',\''+endDateStr+'\',\''+status+'\',\''+projectName+'\');" >Team</a></li>';
			  	    '</sec:authorize>';
		  	  '</sec:authorize>';
		  	
		  	 /* if('${isClientOrgActive}' == "N" || '${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}'){  */  
		  		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">' ; 
				  	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">' 
				      	 result = result + '<li id="projectTaskTab"><a href="#tab-3" id="taskTab" onClick="projectTask('+projectId+',\''+status+'\');">Tasks</a></li>';
				  	'</sec:authorize>';
				  	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_PROJECT\',\'ROLE_VIEW_EDIT_PROJECT\',\'ROLE_VIEW_PROJECT_EMPLOYEES\',\'ROLE_VIEW_EDIT_PROJECT_EMPLOYEES\',\'ROLE_VIEW_DELETE_PROJECT_EMPLOYEES\')">' 
				  	     result = result + '<li class="current"><a href="#tab-3" onClick="projectTask('+projectId+',\''+status+'\');">Tasks</a></li>';
				  	'</sec:authorize>';
			  	'</sec:authorize>';
		  	 /* } */
		  	 
		  	'<sec:authorize access="hasAnyRole(\'ROLE_ADD_DOCUMENTS\',\'ROLE_VIEW_EDIT_DOCUMENTS\')">'; 
		  	result = result + ' <li><a href="#tab-4" onClick="projectDocuments('+projectId+');" >Documents</a></li>';
		  	'</sec:authorize>';
		  	if(('${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}') || ('${postedSharedProjectOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}')){
				//this condition is for hiding the report subtab for client project in vendor login
				 result = result + ' <li><a href="#tab-5" onClick="projectReports('+projectId+');getDayWiseReports('+projectId+');" >Reports</a></li>';
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
		  	
			result = result + '<div id="tab-5" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '<div id="tab-6" class="project_tabs_inn">';
		  	result = result + '</div>';
		  	
		  	result = result + '</div>'; 
		  	$("#projectSideDiv").empty();  
		    $("#projectSideDiv").append(result);  
		    $("#projectNameById").empty(); 
		    $("#projectNameById").append(projectName); 
		  	if(tabType != null && tabType != '' && tabType != 'undefined'){
				 if(tabType == "tasks"){
					 if('${isClientOrgActive}' == "N" || '${postedOrgLocationId}' == '${SourceLeadWeb_orgLocations.id}'){   
				  		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_PROJECT_TASKS\',\'ROLE_VIEW_EDIT_PROJECT_TASKS\')">' ; 
				  		 			if(document.getElementById('projectTaskTab') != null){
	 									document.getElementById('projectTaskTab').className = 'current'
				  		 			}
									projectTask(projectId, status);
					  	'</sec:authorize>';
				  	 }
				 }else  if(tabType == "projectMembers"){
					 document.getElementById('requestResources').className = 'current'
				      $( "#details" ).removeClass( "current" );
					 projectEmployee(projectId,startDateStr,endDateStr,status,projectName);
					 document.getElementById('projectMembersTab').click();
				 }else if(tabType == "resources") {
					 document.getElementById('requestResources').className = 'current'
					 projectEmployee(projectId,startDateStr,endDateStr,status,projectName);
					 document.getElementById('resourceTab').click();
				 }else if(tabType == "team"){
					 document.getElementById('projectTeam').className = 'current'
						 projectTeam(projectId,startDateStr,endDateStr,status,projectName);
				 }
				 if(document.getElementById("details") != null)
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
				 
// 				'<sec:authorize access="hasAnyRole(\'ROLE_ADD_DOCUMENTS\',\'ROLE_VIEW_EDIT_DOCUMENTS\')">'; 
// 			  	projectDocuments(projectId)
// 			  	'</sec:authorize>';
			  	
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