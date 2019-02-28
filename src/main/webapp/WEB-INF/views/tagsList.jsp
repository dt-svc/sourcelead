<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
$(document).ready(function(){
	getAllTags('ASSOCIATES');
});
</script>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Tags</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>  &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; Tags List </div>
</section>


<div class="project_wrap clearfix">

<div class="project_side_wrap_inn_tabs">
	<ul class="project-tabs-menu">
	<c:forEach var="userTabs" items="${SourceLeadWeb_tabsList}">
		<c:if test="${userTabs.tabName == 'Associates'}">
				<li class="current" id="details"><a href="#tab-1" onclick="getAllTags('ASSOCIATES');">Associates</a></li>
		</c:if>
		
		<c:if test="${userTabs.tabName == 'Jobs'}">
			<li><a href="#tab-2" onclick="getAllTags('JOBS');">Jobs</a></li>
		</c:if>
		
		<c:if test="${userTabs.tabName == 'Candidates'}">
			<li><a href="#tab-3" onclick="getAllTags('CANDIDATES');">Candidates</a></li>
		</c:if>
		
		<c:if test="${userTabs.tabName == 'Projects'}">
			<li><a href="#tab-4" onclick="getAllTags('PROJECTS');">Projects</a></li>
			<li><a href="#tab-5" onclick="getAllTags('TASKS');">Tasks</a></li>
		</c:if>
		
		<c:if test="${userTabs.tabName == 'Asset Management'}">
			<li><a href="#tab-6" onclick="getAllTags('ASSETMANAGEMENT');">Asset</a></li>
		</c:if>
		
		<c:if test="${userTabs.tabName == 'Receivable'}">
			<li><a href="#tab-7" onclick="getAllTags('INVOICES');">Invoices</a></li>
		</c:if>
		
		<c:if test="${userTabs.tabName == 'Payables'}">
			<li><a href="#tab-8" onclick="getAllTags('BILLS');">Bills</a></li>
		</c:if>
		
	 </c:forEach>
	 	<div class="clearfix"></div>
	</ul>
</div>
	<div class="project_tabs">
			<div id="tab-1" class="project_tabs_inn" style="display: block;">
				<div id="assocaiteTags"></div>
			</div>

			<div id="tab-2" class="project_tabs_inn">
				<div id="jobsTags"></div>
			</div>

			<div id="tab-3" class="project_tabs_inn">
				<div id="candidateTags"></div>
			</div>

			<div id="tab-4" class="project_tabs_inn">
				<div id="projectTags"></div>
			</div>

			<div id="tab-5" class="project_tabs_inn">
				<div id="taskTags"></div>
			</div>
			
			<div id="tab-6" class="project_tabs_inn">
				<div id="assetTags"></div>
			</div>
			
			<div id="tab-7" class="project_tabs_inn">
				<div id="invoiceTags"></div>
			</div>
			
			<div id="tab-8" class="project_tabs_inn">
				<div id="billTags"></div>
			</div>

		</div>
		</div>

		 
  <div id="deleteTasgPopUp"></div>
  </section>
  
<!--    ====================== searching for the tags scripts Start ============================= -->
  <script>
  function searchTags(entityType){
	  var input, filter, ul, li, a, i;
	    input = document.getElementById(""+entityType+"SerachTag");
	    filter = input.value.toUpperCase();
	    ul = document.getElementById(""+entityType+"Tags");
	    li = ul.getElementsByTagName("li");
	    for (i = 0; i < li.length; i++) {
	        a = li[i].getElementsByTagName("span")[0];
	        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
	            li[i].style.display = "";
	        } else {
	            li[i].style.display = "none";

	        }
	    }  
  }
  </script>
  <!--    ====================== searching for the tags scripts Ends ============================= -->
  
  <script>
  function getAllTags(entitype){
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $.post(sourceleadUrl+"/getTagsListByEntitype?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&entitype="+entitype,{},function(data){
		 var result ="";
		 var json = data.object;
			result = result + '	 <div class="project_tabs_inn_table">';
			result = result + '		<div class="project_details">';
			result = result + '			<div class="clearfix"></div>';
			result = result + '			<div class="timesheet_head table_head">';
			result = result + '				<div class="clearfix"></div>';
			result = result + '				<div class="remove_search pull-right">';
			result = result + '					<input type="text" id="'+entitype+'SerachTag" placeholder="Search" onkeyup="searchTags(\''+entitype+'\')">';
			result = result + '				</div>';
			result = result + '			</div>';
			result = result + '			<div class="clearfix"></div>';
			result = result + '			<ul class="list-unstyled list-inline tagslist" id="'+entitype+'Tags">';
			 for(var tag in json){
				  result = result + '<li><span class="tagItem pull-left">'+tag+'</span><span class="ml10 pull-left count">'+json[tag]+'</span><i class="fa fa-times" onclick="deleteTagPop(\''+entitype+'\',\''+tag+'\')"></i></li>'
			}
			result = result + '			</ul>';
			result = result + '		</div>';
			result = result + '	</div>';
			
		 
		   //Associate Tags
		    if(entitype == "ASSOCIATES"){
		    	if(data.response === "SUCCESS"){
		    		$("#assocaiteTags").empty();
					 $("#assocaiteTags").append(result);
		    	}else{
		    		$("#assocaiteTags").empty();
					 $("#assocaiteTags").append("<div class='dataTables_empty'></div>");
		    	}
		    }
		   
		    //Jobs Tags
		    if(entitype == "JOBS"){
		    	if(data.response === "SUCCESS"){
		    		 $("#jobsTags").empty();
					 $("#jobsTags").append(result);
		    	}else{
		    		$("#jobsTags").empty();
			    	$("#jobsTags").append("<div class='dataTables_empty'></div>");
		    	}
		    } 

		    //Candidate Tags
		    if(entitype == "CANDIDATES"){
		    	if(data.response === "SUCCESS"){
		    		$("#candidateTags").empty();
					$("#candidateTags").append(result);
		    	}else{
		    		$("#candidateTags").empty();
					$("#candidateTags").append("<div class='dataTables_empty'></div>");
		    	}
		    }
		    
		    //Projects Tags
		    if(entitype == "PROJECTS"){
		    	if(data.response === "SUCCESS"){
		    		$("#projectTags").empty();
					$("#projectTags").append(result);
		    	}else{
		    		$("#projectTags").empty();
					$("#projectTags").append("<div class='dataTables_empty'></div>");
		    	}
		    }
		    
		    //Tasks Tags
		    if(entitype == "TASKS"){
		    	if(data.response === "SUCCESS"){
		    		$("#taskTags").empty();
					$("#taskTags").append(result);
		    	}else{
		    		$("#taskTags").empty();
					$("#taskTags").append("<div class='dataTables_empty'></div>");
		    	}
		    }
		    
		    //Asset Tags
		    if(entitype == "ASSETMANAGEMENT"){
		    	if(data.response === "SUCCESS"){
		    		$("#assetTags").empty();
				    $("#assetTags").append(result);
		    	}else{
		    		$("#assetTags").empty();
					$("#assetTags").append("<div class='dataTables_empty'></div>");
		    	}
		    }
		    
		    //invoice Tags
		    if(entitype == "INVOICES"){
		    	if(data.response === "SUCCESS"){
		    		$("#invoiceTags").empty();
					$("#invoiceTags").append(result);	
		    	}else{
		    		$("#invoiceTags").empty();
					$("#invoiceTags").append("<div class='dataTables_empty'></div>");
		    	}
		    }
		    
		    //Bill Tags
		    if(entitype == "BILLS"){
		    	if(data.response === "SUCCESS"){
		    		$("#billTags").empty();
					$("#billTags").append(result);
		    	}else{
		    		$("#billTags").empty();
					$("#billTags").append("<div class='dataTables_empty'></div>");
		    	}
		    }
		    
		});
	  
  }
  </script>
  
  <script>
  function deleteTagPop(entitype,tagName){
	  var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop_resume' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>You are choosing to delete the Tag</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +'<b><input id="confirm-id" type="button" value="Ok" onclick="deleteTag(\''+tagName+'\',\''+entitype+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="deleteResumePopup();"></b>';
		result = result +"	</div>";
		$("#deleteTasgPopUp").empty();
		$("#deleteTasgPopUp").append(result);
		$("#remove_user_pop_resume").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
  }
  function deleteResumePopup(){
		$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
	}
  function deleteTag(tagName,entityType){
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$.post(sourceleadUrl+"/deleteTag?"+tokenParamName+"="+tokenValue+"&orgLocationId="+orgLocationId+"&tagName="+tagName+"&entityType="+entityType,{},function(result){
			$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
			getAllTags(entityType);
		});
  }
  
  // Tabs script
  $("#tab-1").show();
		$(".project-tabs-menu a").click(function(event) {
		     event.preventDefault();
		     $(this).parent().addClass("current");
		     $(this).parent().siblings().removeClass("current");
		     var tab = $(this).attr("href");
		     $(".project_tabs_inn").not(tab).css("display", "none");
		     $(tab).fadeIn();
		 });

  </script>
 <jsp:include page="masterFooter.jsp" />	
 
  