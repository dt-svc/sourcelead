
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


	<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Template Details</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
      <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo;
      <a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">Performance Management</a> &raquo; Appraisals List <span id = "fname"></a> </div>
    </section>
    
    <div class="project_wrap">
     <div id="appraisalDetailsLoading"></div>
     <div class="clearfix newAddWidth">
     <div class="form-horizontal mt myForm">
     	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt"> 
     	     <div class="form-group">
	     	     <label class="col-md-4 paddingRight">Appraisal Name<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="appraisalName"></div>
     	     </div>
     	     
     	      <div class="form-group">
     	      	<label class="col-md-4 paddingRight">Appraisal Start Date<span class="pull-right paddingLeft">:</span></label>
     	     	<div class="col-md-5" id="appraisalStartDate"></div>
     	      </div>
     	     
     	     <div class="form-group">
	     	     <label class="col-md-4 paddingRight">Appraisal End Date<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="appraisalEndDate"></div>
     	     </div>
     	     
     	</div>
     	
     	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt"> 
     	<h2>Applicable For</h2>
     	 <div class="form-group">
     	     <label class="col-md-4 paddingRight">Department<span class="pull-right paddingLeft">:</span></label>
     	     <div class="col-md-5" id="appraisalDepartments"></div>
     	 </div>    
     	  <div class="form-group"> 
     	     <label class="col-md-4 paddingRight">Roles<span class="pull-right paddingLeft">:</span></label>
     	     <div class="col-md-5" id="appraisalRoles"></div>
     	 </div>
     	 <div class="form-group"> 
   			 <label class="col-md-4 paddingRight">Employee Type<span class="pull-right paddingLeft">:</span></label>
     	     <div class="col-md-5" id="appraisalEmlployementType"></div>
     	 </div>     
     	</div>
     	
     <div class="clearfix"></div>		
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
			<h2>Template Description</h2>
				<div class="form-group">
	     	     <label class="col-md-4 paddingRight">Display To Employees<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="descriptionDisplay"></div>
	     	   </div>  
	      <div class="clearfix"></div>	   
	     	     <label class="col-md-4 paddingRight">Description<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-12" id="appraisalDescription"></div>
		</div>	
		
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
		<h2>Appraisal Period</h2>
			  <div class="form-group">
     	      	<label class="col-md-5 paddingRight">Process Period Start Date<span class="pull-right paddingLeft">:</span></label>
     	     	<div class="col-md-5" id="appraisalProcessStartDate"></div>
     	      </div>
     	     
     	     <div class="form-group">
	     	     <label class="col-md-5 paddingRight">Process Period End Date<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="appraisalProcessEndDate"></div>
     	     </div>
     	     
     	     <div class="form-group">
     	      	<label class="col-md-5 paddingRight">Self Appraisal Start Date<span class="pull-right paddingLeft">:</span></label>
     	     	<div class="col-md-5" id="selfAppraisalStartDate"></div>
     	      </div>
     	     
     	     <div class="form-group">
	     	     <label class="col-md-5 paddingRight">Self Appraisal End Date<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="selfAppraisalEndDate"></div>
     	     </div>
     	     
     	      <div class="form-group">
     	      	<label class="col-md-5 paddingRight">Peer Review Start Date<span class="pull-right paddingLeft">:</span></label>
     	     	<div class="col-md-5" id="peerReviewStartDate"></div>
     	      </div>
     	     
     	     <div class="form-group">
	     	     <label class="col-md-5 paddingRight">Peer Review End Date<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="peerReviewEndDate"></div>
     	     </div>
     	     
     	     <div class="form-group">
     	      	<label class="col-md-5 paddingRight">Resource Manager Review Start Date<span class="pull-right paddingLeft">:</span></label>
     	     	<div class="col-md-5" id="resourceManagerReviewStartDate"></div>
     	      </div>
     	     
     	     <div class="form-group">
	     	     <label class="col-md-5 paddingRight">Resource Manager Review End Date<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="resourceManagerReviewEndDate"></div>
     	     </div>
     	     
     	     <div class="form-group">
     	      	<label class="col-md-5 paddingRight">Reporting Manager Review Start Date<span class="pull-right paddingLeft">:</span></label>
     	     	<div class="col-md-5" id="reportingManagerReviewStartDate"></div>
     	      </div>
     	     
     	     <div class="form-group">
	     	     <label class="col-md-5 paddingRight">Reporting Manager Review End Date<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="reportingManagerReviewEndDate"></div>
     	     </div>
     	     
		</div>
		
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
		  <h2>Feed back Categories</h2>
		  
   	      <div class="col-md-5" id="appraisalFeedbackCategories"></div>
   	      
   	      <div class="form-group">
	     	     <label class="col-md-4 paddingRight">Goals Weightage<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="appraisalGoalsWeightage"></div>
   	      </div>
   	      
   	      <div class="form-group">
	     	     <label class="col-md-4 paddingRight">Tasks Weightage<span class="pull-right paddingLeft">:</span></label>
	     	     <div class="col-md-5" id="appraisalTasksWeightage"></div>
   	      </div>
   	      
   	       <!-- <label class="paddingRight"><strong>Performance Competencies</strong></label></label>
   	       <div class="col-md-5" id="appraisalPerformanceCompetencies"></div> -->
     	     
	     	     
		</div>
		
		
     </div>
     </div>
    </div>