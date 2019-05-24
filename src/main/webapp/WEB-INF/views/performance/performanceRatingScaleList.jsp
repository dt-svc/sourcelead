<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
.competencePaddingRight{
padding: 0px 240px 8px 0px;
}
</style>


<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Performance Competence</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
      <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo;
      <a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">Performance Management</a> &raquo; Performance Competence <span id = "fname"></a> </div>
</section>

<div class="clearfix">
           <ul class="project-tabs-menu" id="tab_bar">
            
	            <li class="current" id="ratingScaleList">
	            	<a href="#"  onclick="getRatingScaleList();" >
	              		Rating Scale List
	              	</a> 
	            </li>
	            
	             <li id="newRatingScale">
	            	<a href="#"  onclick="getRatingScaleLevelsList();">
	              		Rating Levels List
	              	</a> 
	            </li>
          </ul>
 </div>
 
 
<div class="content-area content-new clearfix backWhite">
 
 <div id="comptenceClassId">
      <ul class="pull-right list-unstyled list-inline">
      
            <li>
				<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
				          <a onclick="performanceManagement.addRatingScale();" title="Create offboard">Add Rating Scale</a>
				          <a onclick="performanceManagement.performanceRatingScaleList();" title="Create onboard">Rating Scale List</a> 
					</span>
				</div>
			</li>
		  <li id="competenceListSearch"><input type="text" class="repSearchBar"  id='competenceDataTableSearch'/><span class="repSearchIcon" onClick="competenceDataTableList();"></span></li>
        </ul>
      </div>
          
        <div class="clearfix"></div>  
          
          
          <div class="candidate_wrap_table datatable_filter positionRelative" style='position:relative; top:-25px;'>
	          <div id="competenceLoading"></div>
	         
		    <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
		    	<div id="competenceList" class="datatable_filter"></div>
			</div>	  
			
		    <div class="project_wrap_table datatable_filter dataSeaTop mt10"  style='position:relative; top:-5px;'>
	    	<table id="competenceTaggingList" class="display" cellspacing="0" width="100%"></table>
		   </div>
		
          </div>
        
      </div>
      
     <jsp:include page="../performance/addNewRatingScale.jsp" />    
      
      
      
      
      
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
 