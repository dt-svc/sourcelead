<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

    <section class="content-header clearfix">
      <div class="pull-left"><h2>Add Appraisal Cycle</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration'/>">Configuration</a> &raquo; <a onclick="performanceManagement.viewAppraisalCycle();">Appraisal Cycle List</a> &raquo; Add Appraisal Cycle</div>
    </section>
    
 <div class="project_wrap">
  <div id="appraisalDetailsLoading"></div>
	  <div class="clearfix newAddWidth">
		    <div class="form-horizontal mt myForm">
		    <form:form id="createAppraisal_form"  name="createAppraisal_form" method="post">
		        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt"> 
		        <input type="hidden" name="appraisalTemplateId" id="appraisalTemplateId"/>
			        <div class="form-group" id="appraisalNameDiv">
			          <label class="col-md-3">Name<span class="text-danger paddingLeft">&nbsp;*</span></label>
			          <div class="col-md-9"><input type="text" class="form-control" name="name" id="appraisalName" onblur="performanceManagement.validateAppraisalTemplateName()" field-name="Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-250"></div>
			        </div>
			        
			        <div class="form-group" id="appraisalStartDateDiv">
			          <label class="col-md-3">Start Date<span class="text-danger paddingLeft">&nbsp;*</span></label>
			         <div class="col-md-9">
			         <div class="input-group errorInptgroup">
			         <input type="text" class="form-control calendar" value="" id="appraisalStartDate" name="appraisalStartDate" field-name="Appraisal Start Date" data-validation="required" readonly="readonly" >
			         <span class="input-group-addon" onClick="performanceManagement.clearAppraisalStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
			         </div>
			         </div>
			        </div>
			        
			        <div class="form-group" id="appraisalEndDateDiv">
			          <label class="col-md-3">End Date<span class="text-danger paddingLeft">&nbsp;*</span></label>
			         <div class="col-md-9">
			          <div class="input-group errorInptgroup">
			           <input type="text" class="form-control calendar" disabled value="" id="appraisalEndDate" name="appraisalEndDate" field-name="Appraisal End Date" data-validation="required" readonly="readonly" >
			           <span class="input-group-addon" onClick="performanceManagement.clearAppraisalEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
			          </div>
			         </div>
			        </div>
		        
		        </div>
		        
		        
	        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
		   			<h2>Applicable For</h2>
					
					<div class="row">
					
					<div class="col-md-6">
						<div class="form-group width100percnt">
						  <label class="col-md-3">Department</label>
						  <div class="col-md-9">
								<select multiple class="chosen-select form-control"
												name="appraisalDepartment" id="appraisalDepartment" data-placeholder="Select Department"
												data-validation-optional="true">
								</select>
								          	
						  </div>
						</div> 
						<div class="form-group width100percnt">
						  <label class="col-md-3">Roles</label>
						  <div class="col-md-9">
								<select  multiple class="chosen-select form-control"
												name="appraisalRoles" data-placeholder="Select Roles"
												id="appraisalRoles" data-validation-optional="true">
								</select>          	
						  </div>
						</div> 
					</div>
					
				<div class="col-md-6">
					<div class="form-group width100percnt">
						  <label class="col-md-3">Employee Type</label>
						  <div class="col-md-9">
								<select  multiple class="chosen-select form-control"
												name="appraisalEmpType" data-placeholder="Employee Type"
												id="appraisalEmpType" data-validation-optional="true">
								</select>         	
						  </div>
						</div>
			        
			        
            	</div>
            </div>
            
            </div>
            
	<div class="clearfix"></div>		
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
				<div class="clearfix">
						<label class="col-md-12"><span class="pull-left mr">Description</span>
						  	<div class="checkbox-new pull-left">
								<input id="descriptionDisplay" class="checkbox-custom" name="descriptionDisplay" value="N" onclick="performanceManagement.changeDescriptionDisplayStatus();" type="checkbox"/>
								<label for="descriptionDisplay" class="checkbox-custom-label"/>
								<span class="ml10 text-muted">Display to Employees </span>
							</div>
						</label>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12  txtEditor_p">
							<textarea id="txtEditor" name="description"   value="" class="form-control newContrtextarea"  data-validation-optional="true validate_length length1-5000"></textarea>
						</div>
				</div>
			</div>
			
			
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
	
		<h2>Appraisal Period <span><i class='fa fa-exclamation-triangle text-danger'></i>Please check at least one review type</span></h2>
		
		<div class="row">
				<div class="col-md-9">
					
					<div class="form-group width100percnt">
			          <label class="col-md-3">Process Period</label>
			          <div class="col-md-9">
						  <div class="row">
							 <div class="col-md-6 valMsgPosition" id="appraisalProcessStartDateDiv">
								  <div class="input-group">
									 <input type="text" class="form-control calendar" value="" onclick="performanceManagement.getProcessPeriodCalendars();" id="appraisalProcessStartDate" name="appraisalProcessStartDate" field-name="Process Start Date" data-validation="required" readonly="readonly" >
								     <span class="input-group-addon" onClick="performanceManagement.clearAppraisalProcessStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
								  </div>
							 </div>
							  <div class="col-md-6 valMsgPosition" id="appraisalProcessEndDateDiv">
							  <div class="input-group">
							  	<input type="text" class="form-control calendar" disabled value="" id="appraisalProcessEndDate" name="appraisalProcessEndDate" field-name="Process End Date" data-validation="required" readonly="readonly" >
							    <span class="input-group-addon" onClick="performanceManagement.clearAppraisalProcessEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
							  </div>
							  </div>
						  </div>
					  </div>
			        </div>
		         </div>
				
				<div class="col-md-6">
					<div class="form-group width100percnt">
					<div class="col-md-1 noPad">
						<input id="isSelfAppraisalRequired" class="checkbox-custom" name="isSelfAppraisalRequired" value="N" onclick="performanceManagement.enableSelfAppraisalPeriod();" type="checkbox"/>
						<label for="isSelfAppraisalRequired" class="checkbox-custom-label pull-right"/>
					</div>
		            <label class="col-md-3 noPad">Self Appraisal </label>
			          <div class="col-md-8">
						  			  <div class="row">
							  <div class="col-md-6 valMsgPosition" id="selfAppraisalStartDateDiv">
								  <div class="input-group">
									  <input type="text" class="form-control calendar" disabled value="" onclick="performanceManagement.getAppraisalTypesCalendars();" id="selfAppraisalStartDate" name="selfAppraisalStartDate" field-name="Self Appraisal Start Date" data-validation-optional="true" readonly="readonly" >
									  <span class="input-group-addon" onClick="performanceManagement.clearSelfAppraisalStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
								  </div>
							  </div>
							  <div class="col-md-6 valMsgPosition" id="selfAppraisalEndDateDiv">
								   <div class="input-group">
								     <input type="text" class="form-control calendar" disabled value="" onclick="performanceManagement.getAppraisalTypesCalendars();"  id="selfAppraisalEndDate" name="selfAppraisalEndDate" field-name="Self Appraisal End Date" data-validation-optional="true" readonly="readonly" >
								     <span class="input-group-addon" onClick="performanceManagement.clearSelfAppraisalEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
								   </div>
							  </div>
						  </div>
			
					  </div>
			        </div>
			        
            </div>
            
            <div class="col-md-6">
            <div class="form-group width100percnt">
            <div class="col-md-1 noPad">
						<input id="isReportingManagerAppraisalRequired" class="checkbox-custom" name="isReportingManagerAppraisalRequired" value="N" onclick="performanceManagement.enableReportingManagerAppraisalPeriod();" type="checkbox"/>
						<label for="isReportingManagerAppraisalRequired" class="checkbox-custom-label pull-right"/>
					  </div>
			          <label class="col-md-3 noPad">Reporting Manager Review</label>
			          <div class="col-md-8">
						  <div class="row">
							 <div class="col-md-6 valMsgPosition" id="appraisalApprovingStartDateDiv">
								  <div class="input-group">
									 <input type="text" class="form-control calendar" disabled value="" onclick="performanceManagement.getAppraisalTypesCalendars();"  id="appraisalApprovingStartDate" name="appraisalApprovingStartDate" field-name="Reporting Manager Review Start Date" data-validation-optional="true" readonly="readonly" >
								     <span class="input-group-addon" onClick="performanceManagement.clearAppraisalApprovingStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
								  </div>
							 </div>
							  <div class="col-md-6 valMsgPosition" id="appraisalApprovingEndDateDiv">
							  <div class="input-group">
							  	<input type="text" class="form-control calendar" disabled value="" onclick="performanceManagement.getAppraisalTypesCalendars();"  id="appraisalApprovingEndDate" name="appraisalApprovingEndDate" field-name="Reporting Manager Review End Date" data-validation-optional="true" readonly="readonly" >
							    <span class="input-group-addon" onClick="performanceManagement.clearAppraisalApprovingEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
							  </div>
							  </div>
						  </div>
					  </div>
			        </div>
            
            </div>
            
            
            <div class="col-md-6">
            <div class="form-group width100percnt">
            <div class="col-md-1 noPad">
						<input id="isPeerReviewRequired" class="checkbox-custom" name="isPeerReviewRequired" value="N" onclick="performanceManagement.enablePeerReviewPeriod();" type="checkbox"/>
						<label for="isPeerReviewRequired" class="checkbox-custom-label pull-right"/>
					  </div>
			          <label class="col-md-3 noPad">Peer Review</label>
			          <div class="col-md-8">
						  <div class="row">
							 <div class="col-md-6 valMsgPosition" id="peerReviewStartDateDiv">
								  <div class="input-group">
									 <input type="text" class="form-control calendar" disabled value="" onclick="performanceManagement.getAppraisalTypesCalendars();"  id="peerReviewStartDate" name="peerReviewStartDate" field-name="Peer Review Start Date" data-validation-optional="true" readonly="readonly" >
								     <span class="input-group-addon" onClick="performanceManagement.clearPeerReviewStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
								  </div>
							 </div>
							  <div class="col-md-6 valMsgPosition" id="peerReviewEndDateDiv">
							  <div class="input-group">
							  	<input type="text" class="form-control calendar" disabled value="" onclick="performanceManagement.getAppraisalTypesCalendars();"  id="peerReviewEndDate" name="peerReviewEndDate" field-name="Peer Review End Date" data-validation-optional="true" readonly="readonly" >
							    <span class="input-group-addon" onClick="performanceManagement.clearPeerReviewEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
							  </div>
							  </div>
						  </div>
					  </div>
			        </div>
            
            </div>
            
            
            <div class="col-md-6">
            <div class="form-group width100percnt">
            <div class="col-md-1 noPad">
						<input id="isResourceManagerAppraisalRequired" class="checkbox-custom" name="isResourceManagerAppraisalRequired" value="N" onclick="performanceManagement.enableResourceManagerAppraisalPeriod();" type="checkbox"/>
						<label for="isResourceManagerAppraisalRequired" class="checkbox-custom-label pull-right"/>
					  </div>
			          <label class="col-md-3 noPad">Resource Manager Review</label>
			          <div class="col-md-8">
						  <div class="row">
							 <div class="col-md-6 valMsgPosition" id="resourceManagerReviewStartDateDiv">
								  <div class="input-group">
									 <input type="text" class="form-control calendar" disabled value="" onclick="performanceManagement.getAppraisalTypesCalendars();"  id="resourceManagerReviewStartDate" name="resourceManagerReviewStartDate" field-name="Resource Manager Review Start Date" data-validation-optional="true" readonly="readonly" >
								     <span class="input-group-addon" onClick="performanceManagement.clearResourceManagerReviewStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
								  </div>
							 </div>
							  <div class="col-md-6 valMsgPosition" id="resourceManagerReviewEndDateDiv">
							  <div class="input-group">
							  	<input type="text" class="form-control calendar" disabled value="" onclick="performanceManagement.getAppraisalTypesCalendars();"  id="resourceManagerReviewEndDate" name="resourceManagerReviewEndDate" field-name="Resource Manager Review End Date" data-validation-optional="true" readonly="readonly" >
							    <span class="input-group-addon" onClick="performanceManagement.clearResourceManagerReviewEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
							  </div>
							  </div>
						  </div>
					  </div>
			        </div>
            
            </div>
            
           </div>
           </div>
            
	<div class="clearfix"></div>	
			
			
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
   		  <h2>Weightage</h2>
				
		 <div class="clearfix mb">
            <div class="col-md-12">
              <div class="checkbox-new pull-left mt10">
                <input id="feedbackCategory" class="checkbox-custom" disabled  name="feedbackCategory" type="checkbox" onclick="performanceManagement.checkFeedbackCategories();">
                <label for="feedbackCategory" class="checkbox-custom-label"></label>
                <span class="ml10 text-muted">Feed back Categories</span>
              </div>
            </div>
          </div>
				
		<div class="clearfix mb">		
		<div class="col-md-6" id="fedbackCategoriesDiv" style="display: none;">
		  <label class="col-md-3">Categories<span class="text-danger paddingLeft"></span></label>
		  <div class="col-md-9">
				<select  multiple class="chosen-select form-control"
										name="appraisalCategories" data-placeholder="Categories"
										id="appraisalCategories" field-name="Categories" data-validation-optional="true">
				</select>
		  </div>
		  <input type="hidden" name="jsonCategoriesString" id="jsonCategoriesString"/>
		<div class="clearfix"></div>
		</div> 
		</div>
		
		<div class="clearfix mb">
			<div class="">
				<div class="clearfix" id="selectedTemplatesDiv">
				</div>
			</div>
		</div>
		<div class="clearfix"></div>	
			
	  <div class="clearfix mb form-group">
        <div class="col-md-7">
          <div class="checkbox-new pull-left mt10">
            <input id="appraisalGoals" class="checkbox-custom" name="appraisalGoals" type="checkbox" value="N" onclick="performanceManagement.changeAppraisalGoalsStatus();" >
            <label for="appraisalGoals" class="checkbox-custom-label"></label>
            <span class="ml10 text-muted">Goals</span>
          </div>
        </div>
        <div class="col-md-5" id="goalsWeightageDiv">
          <input type="text" class="form-control" name="goalsWeightage"  id="goalsWeightage" field-name="Goals Weightate" onblur="performanceManagement.validatePercentageTotalSumValue();" disabled placeholder="Weightage" data-validation-optional="true">
        </div>
      </div>
      <div class="clearfix"></div>
      <div class="clearfix mb form-group">
          <div class="col-md-7">
            <div class="checkbox-new pull-left mt10">
              <input id="appraisalProjectTasks" class="checkbox-custom" name="appraisalProjectTasks" type="checkbox" value="N" onclick="performanceManagement.changeAppraisalProjectTasksStatus();">
              <label for="appraisalProjectTasks" class="checkbox-custom-label"></label>
              <span class="ml10 text-muted">Tasks</span>
            </div>
          </div>
          <div class="col-md-5" id="projectTasksWeightageDiv">
            <input type="text" class="form-control" name="projectTasksWeightage"  id="projectTasksWeightage" onblur="performanceManagement.validatePercentageTotalSumValue();" disabled field-name="Project Task Weightate" placeholder="Weightage" data-validation-optional="true">
          </div>
        </div>
        
       	<div class="clearfix"></div>
       	
   		 <!--<div class="clearfix mb">
            <div class="col-md-12">
              <div class="checkbox-new pull-left mt10">
                <input id="performanceComptencies" class="checkbox-custom" name="performanceComptencies" type="checkbox" onclick="performanceManagement.checkPerformanceComptencies();">
                <label for="performanceComptencies" class="checkbox-custom-label"></label>
                <span class="ml10 text-muted">Performance Competencies</span>
              </div>
            </div>
          </div>
				
		 <div class="clearfix mb">		
		<div class="col-md-6" id="performanceComptenciesDiv" style="display: none;">
		  <label class="col-md-3">Competency<span class="text-danger paddingLeft"></span></label>
		  <div class="col-md-9">
				<select  multiple class="chosen-select form-control"
										name="comptencyCategories" data-placeholder="Categories"
										id="comptencyCategories" field-name="Categories" data-validation-optional="true">
				</select>
		  </div>
		  <input type="hidden" name="jsonComptencyCategoriesString" id="jsonComptencyCategoriesString"/>
		<div class="clearfix"></div>
		</div> 
		</div> -->
		
		<div class="clearfix mb">
			<div class="col-md-6">
				<div class="clearfix" id="selectedComptenciesDiv">
				</div>
			</div>
		</div>
		<div class="clearfix"></div>	
		
		<div class="row mxrateerror" id="percentSumDiv">
									<input type="hidden" name="percentTotalSum"
										id="percentTotalSum"/>
		</div>
		
		</div> 
		  <div class="clearfix"></div>       
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="savePerformanceBudget" onclick="performanceManagement.validatePercentageTotalSumValue(); performanceManagement.saveAppraisalCycleDetails();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="performanceManagement.viewAppraisalCycle();" >Cancel</button>
	        </div>
	        
		    </form:form>
		    </div>
		    </div>
	  </div>
 
 <script>
 $(function(){
    var config = {
		  '.chosen-select'           : {},
		  '.chosen-select-deselect'  : {allow_single_deselect:true},
		  '.chosen-select-no-single' : {disable_search_threshold:10},
		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		  '.chosen-select-width'     : {width:"95%"}
		}
    
    $('#appraisalDepartment').chosen();
    $('#appraisalRoles').chosen();
    $('#appraisalEmpType').chosen();
    $('#appraisalCategories').chosen();
    $('#comptencyCategories').chosen();
    
    $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
});
</script>
