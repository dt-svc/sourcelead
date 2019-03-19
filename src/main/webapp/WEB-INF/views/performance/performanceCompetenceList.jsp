<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script> 
	$(document).ready(function(){
		
         
	});
</script>

<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Performance Competence</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">Performance Management</a> &raquo; Performance Competence <span id = "fname"></a> </div>
</section>

<div class="clearfix">
           <ul class="project-tabs-menu" id="tab_bar">
            
	            <li class="current" id="competence">
	            	<a href="#"  onclick="getCompetenceList();" title="Current offboard count">
	              		Competence 
	              	</a> 
	            </li>
	            
	             <li id="tagCompetence">
	            	<a href="#"  onclick="getTagCompetenceList();" title="Current onboard count">
	              		Tag Competence 
	              	</a> 
	            </li>
          </ul>
 </div>
 
 
  <div class="content-area content-new clearfix backWhite">
 
 
 <div class="positionRelative reportHead clearfix">
      <ul class="pull-right list-unstyled list-inline">
      
            <li>
				<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
				          <a onclick="performanceManagement.addNewCompetence();" title="Create offboard">Add Competence</a>
				          <a onclick="performanceManagement.tagCompetence();" title="Create onboard">Tag Competence</a> 
					</span>
				</div>
			</li>
		  <li id="onboardSearch"><input type="text" class="repSearchBar" placeholder='Search' id='onboardDataTableSearch'/><span class="repSearchIcon" onClick="onboardDataTableList();"></span></li>
		  <li id="offboardSearch" style="display: none;"><input type="text" class="repSearchBar" placeholder='Search' id='offboardDataTableSearch'/><span class="repSearchIcon" onClick="offboardDataTableList();"></span></li>
        </ul>
      </div>
          
        <div class="clearfix"></div>  
          
          
          <div class="candidate_wrap_table datatable_filter positionRelative" style='position:relative; top:-20px;'>
	          <div id="onboardingLoading"></div>
	         
		    <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
		    	<div id="onboardingCandidatesList" class="datatable_filter"></div>
			</div>	  
	         
	        <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">
		    	<div id="offboardingCandidatesList" class="datatable_filter"></div>
			</div>	
	          <div class="vendor_wrap_mask"></div>
          </div>
        
      </div>
      
      
      <jsp:include page="../performance/addNewPerformanceCompetence.jsp" />  
 
 
 <script>
 function getCompetenceList(){
	    $("#tagCompetence").removeClass("current");
	    $("#competence").addClass("current");
 }
 </script>
 
 
  <script>
 function getTagCompetenceList(){
	    $("#competence").removeClass("current");
	    $("#tagCompetence").addClass("current");
 }
 </script>
 
 
<script>
	function getActions(){
		$(".action-pop").slideToggle(200);
		$(".newActPop").slideUp(200);
	}	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
	});
</script>
 
 