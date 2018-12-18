<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

    <section class="content-header clearfix">
      <div class="pull-left"><h2>Add Appraisal Cycle</h2></div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/performanceManagementConfiguration'/>">Configuration</a> &raquo; <a onclick="performanceManagement.viewAppraisalCycle();">Appraisal Cycle List</a> &raquo; Add Appraisal Cycle</div>
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
			          <div class="col-md-9"><input type="text" class="form-control" name="name" id="appraisalName" field-name="Name" data-validation="required validate_Space validate_AlphaNumber validate_length length1-250"></div>
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
					<div class="form-group">
					  <label class="col-md-3">Department</label>
					  <div class="col-md-9">
							<select multiple class="chosen-select form-control"
											name="appraisalDepartment" id="appraisalDepartment" data-placeholder="Select Department"
											data-validation-optional="true">
							</select>
							          	
					  </div>
					</div> 
					<div class="form-group">
					  <label class="col-md-3">Roles</label>
					  <div class="col-md-9">
							<select  multiple class="chosen-select form-control"
											name="appraisalRoles" data-placeholder="Select Roles"
											id="appraisalRoles" data-validation-optional="true">
							</select>          	
					  </div>
					</div> 
					<div class="form-group">
					  <label class="col-md-3">Employee Type</label>
					  <div class="col-md-9">
							<select  multiple class="chosen-select form-control"
											name="appraisalEmpType" data-placeholder="Employee Type"
											id="appraisalEmpType" data-validation-optional="true">
							</select>         	
					  </div>
					</div>
					
					<div class="form-group">
			          <label class="col-md-3">Self Appraisal</label>
			          <div class="col-md-9">
						  			  <div class="row">
							  <div class="col-md-6" id="selfAppraisalStartDateDiv">
								  <div class="input-group">
									  <input type="text" class="form-control calendar" value="" id="selfAppraisalStartDate" name="selfAppraisalStartDate" field-name="Self Appraisal Start Date" data-validation-optional="true" readonly="readonly" >
									  <span class="input-group-addon" onClick="performanceManagement.clearSelfAppraisalStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
								  </div>
							  </div>
							  <div class="col-md-6" id="selfAppraisalEndDateDiv">
								   <div class="input-group">
								     <input type="text" class="form-control calendar" disabled value="" id="selfAppraisalEndDate" name="selfAppraisalEndDate" field-name="Self Appraisal End Date" data-validation-optional="true" readonly="readonly" >
								     <span class="input-group-addon" onClick="performanceManagement.clearSelfAppraisalEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
								   </div>
							  </div>
						  </div>
			
					  </div>
			        </div>
			        
			        <div class="form-group">
			          <label class="col-md-3">Approving From</label>
			          <div class="col-md-9">
						  <div class="row">
							 <div class="col-md-6" id="appraisalApprovingStartDateDiv">
								  <div class="input-group">
									 <input type="text" class="form-control calendar" value="" id="appraisalApprovingStartDate" name="appraisalApprovingStartDate" field-name="Self Appraisal Start Date" data-validation-optional="true" readonly="readonly" >
								     <span class="input-group-addon" onClick="performanceManagement.clearAppraisalApprovingStartDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
								  </div>
							 </div>
							  <div class="col-md-6" id="appraisalApprovingEndDateDiv">
							  <div class="input-group">
							  	<input type="text" class="form-control calendar" disabled value="" id="appraisalApprovingEndDate" name="appraisalApprovingEndDate" field-name="Self Appraisal End Date" data-validation-optional="true" readonly="readonly" >
							    <span class="input-group-addon" onClick="performanceManagement.clearAppraisalApprovingEndDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
							  </div>
							  </div>
						  </div>
					  </div>
			        </div>
			
				<div class="form-group">
						<span class="radioStyles mr">
						<input id="applicableForAll" name="applicableFor" checked  value="All" type="radio" onClick="performanceManagement.applicableType();">
						<label for="applicableForAll">All</label>
						</span>
					<span class="radioStyles">
						<input id="experienceRadio" name="applicableFor"  value="Experienced" type="radio" onClick="performanceManagement.applicableType();">
						<label for="experienceRadio">Experienced</label>
					</span>
				</div>
			
			<div class="form-group" style="display: none" id="experienceDiv">
			 <label class="col-md-3">Experience<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix">
               <div class="row">
				   <div class="col-md-4" id="experienceYearsDiv">
				   	 <div class="clearfix dropdown-select">
					  <select name="experienceYears" id="experienceYears" name="experienceYears" data-validation-optional="true" field-name="Years">
				   	  <option selected value=''>Select Years</option>
				   	  </select>
				     </div> 
				   </div>  	
				   <div class="col-md-4" id="experienceMonthsDiv">
				   	 <div class="clearfix dropdown-select">
					  <select name="experienceMonths" id="experienceMonths" name="experienceMonths" data-validation-optional="true" field-name="Months">
				   	  <option selected value=''>Select Months</option>
				   	  </select>
				     </div> 
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
							<textarea id="txtEditor" name="description"   value="" class="form-control newContrtextarea"  data-validation-optional="true"></textarea>
						</div>
				</div>
			</div>
			
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
	   		  <h2>Add Categories</h2>
				<div class="col-md-6">
				  <label class="col-md-3">Categories<span class="text-danger paddingLeft">&nbsp;*</span></label>
				  <div class="col-md-9">
						<select  multiple class="chosen-select form-control"
												name="appraisalCategories" data-placeholder="Categories"
												id="appraisalCategories" field-name="Categories" data-validation="required">
						</select>
				  </div>
				  <input type="hidden" name="jsonCategoriesString" id="jsonCategoriesString"/>
				</div> 
				
			<div class="clearfix"></div>
			<div class="col-md-6">
			<div class="clearfix" id="selectedTemplatesDiv">
			</div>
			</div>
			<div class="row mxrateerror" id="percentSumDiv">
										<input type="hidden" name="percentTotalSum"
											id="percentTotalSum"/>
									</div>
		</div> 
		  <div class="clearfix"></div>       
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="savePerformanceBudget" onclick="performanceManagement.saveAppraisalCycleDetails();" >Save</button>
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
    
    $('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
	  })
});
</script>
