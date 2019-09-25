
<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Settings Dashboard</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home </a> &raquo; Settings</div>
    </section>
	<div class="reportNewDash clearfix">
			
	<sec:authorize access="!hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')"> 
     <sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG','ROLE_ENROLL_NEW_ROLE','ROLE_VIEW_EDIT_ORG_SETTINGS','ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE','ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE','ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE','ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE','ROLE_ADD_EXPENSE_TYPE','ROLE_VIEW_EDIT_EXPENSE_TYPE','ROLE_DELETE_EXPENSE_TYPE')"> 
	            
         
		<!--  Grid View  jsp code-->
         
			          <div class="settingConfigure newRepGrid clerfix">
			          
			     <sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG')">  
			         <div class="col-md-2 reportNewCta">
					     <a href="${SourceLeadWeb_sourceleadUrl}/loadScheduleMaintenance">
						 <div class="clearfix text-center mb10"><i class="leaveConfiguration	"></i></div>
					     <div class="clearfix text-center">
						 <h4>Schedule Maintenance</h4>
					</div>
					</a>
				    </div>	
				</sec:authorize>
						  
			          
			          <c:if test="${SourceLeadWeb_isSuperAdmin == 'Y'}">
			          
			             <div class="col-md-2 reportNewCta">
			                 <a href="${SourceLeadWeb_sourceleadUrl}/orgLicenseSubscriptionList">
			                  <div class="clearfix text-center mb10"><i class="upgradeLicese"></i></div>
			                  <div class="clearfix text-center">
			                      <h4>License Upgrade </h4>
			                  </div>
			                  </a>
			              </div>
			              </c:if>
			              
			              
			             <sec:authorize access="!hasRole('ROLE_ENROLL_ORG')"> 
			             <div class="col-md-2 reportNewCta">
			                 <!-- DropDown -->
							<div class="dropdown pull-right report-dropdown">
								<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu2" data-toggle="dropdown"><span class="caret cursorPointer"></span></span>
								<ul class="dropdown-menu minHeight50 maxHeight250" role="menu" aria-labelledby="menu2">
								<li><a href="${SourceLeadWeb_sourceleadUrl}/bulk-resume-import.html">Bulk Import</a></li>
								 <li><a data-toggle="modal" href="javascript:void(0)" id='bulkImportGrid'>Process File</a></li>
								 
								</ul>
							</div>
			                 <!-- Dropdown -->
			                    <a href="javascript:void(0)">
			                  <div class="clearfix text-center mb10"><i class="bulkImport"></i></div>
			                  <div class="clearfix text-center">
			                      <h4>Bulk Import</h4>
			                  </div>
			                  </a>
			              </div>
			              </sec:authorize>
			              
			                  
			             <div class="col-md-2 reportNewCta">
			                <a href="${SourceLeadWeb_sourceleadUrl}/orgChart"> 
						 <div class="clearfix text-center mb10"><i class="orgChartSVG"></i></div>
						  <div class="clearfix text-center">
							  <h4>Org Chart</h4>
						  </div>
						  </a>
						 </div>
			              
			            <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')"> 
				             <div class="col-md-2 reportNewCta">
				             <a href="${SourceLeadWeb_sourceleadUrl}/orgRolesList">
				                  <div class="clearfix text-center mb10"><i class="roleconfigSVG"></i></div>
				                  <div class="clearfix text-center">
									  <h4>Role Configuration</h4>
				                  </div>
				                  </a>
				              </div>
			             </sec:authorize>         
			                      
			             <sec:authorize access="hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')"> 
				             <div class="col-md-2 reportNewCta">
					                 <a href="${SourceLeadWeb_sourceleadUrl}/orgSettings">
									<div class="clearfix text-center mb10"><i class="controlsSVG"></i></div>
					                  <div class="clearfix text-center">
										  <h4>Org Settings</h4>
					                  </div>
					                  </a>
				            </div>
			             </sec:authorize>
			               
			     <sec:authorize access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE','ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE','ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE','ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE')">          
						 <div class="col-md-2 reportNewCta">
							<a href="${SourceLeadWeb_sourceleadUrl}/orgTemplates">
									<div class="clearfix text-center mb10"><i class="templateSVG"></i></div>
							<div class="clearfix text-center">
								<h4>Templates</h4>
							</div>
							</a>
						</div>
			       </sec:authorize>  
			        
			       <sec:authorize access="hasAnyRole('ROLE_ADD_EXPENSE_TYPE','ROLE_VIEW_EDIT_EXPENSE_TYPE','ROLE_DELETE_EXPENSE_TYPE')">            
						 <div class="col-md-2 reportNewCta">
							<a href="${SourceLeadWeb_pmsUrl}/expenseCategory">
							<div class="clearfix text-center mb10"><i class="expensesSVG"></i></div>
							<div class="clearfix text-center">
								<h4>Expense Category</h4>
							</div>
							</a>
						  </div>                        
			      	 </sec:authorize>
			      	  
						  <div class="col-md-2 reportNewCta">
								<a href="${SourceLeadWeb_atsUrl}/bankAccountDetails">
								<div class="clearfix text-center mb10"><i class="bankacountsSVG"></i></div>
								<div class="clearfix text-center">
									<h4>Bank Accounts</h4>
								</div>
								</a>
							
							</div>
				
				   <sec:authorize access="hasRole('ROLE_LEAVE_CONFIGURE')"> 
							<div class="col-md-2 reportNewCta">
								<a href="${SourceLeadWeb_pmsUrl}/leaveConfiguration">
										<div class="clearfix text-center mb10"><i class="leaveConfiguration"></i></div>
								<div class="clearfix text-center">
									<h4>Leave Configuration</h4>
								</div>
								</a>
							</div>
				  </sec:authorize>
				
				  <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')"> 
					      <div class="col-md-2 reportNewCta">
							<a href="${SourceLeadWeb_sourceleadUrl}/job-boards">
									<div class="clearfix text-center mb10"><i class="jobConfigureSVG"></i></div>
							<div class="clearfix text-center">
								<h4>Job Board Configuration</h4>
							</div>
							</a>
						</div>
				</sec:authorize>
				
			    <sec:authorize access="!hasRole('ROLE_ENROLL_ORG')"> 
						<div class="col-md-2 reportNewCta">
							<a href="${SourceLeadWeb_sourceleadUrl}/customFields">
									<div class="clearfix text-center mb10"><i class="sortingSVG"></i></div>
							<div class="clearfix text-center">
								<h4>Custom Fields</h4>
							</div>
							</a>
						</div>
				</sec:authorize>
			
			     <sec:authorize access="hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_JOB_CAMPAIGN')">	
						<div class="col-md-2 reportNewCta">
								<!-- DropDown -->
								<div class="dropdown pull-right report-dropdown">
								<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" id="menu12" data-toggle="dropdown">
									<span class="caret cursorPointer"></span>
								</span>
								
									<ul class="dropdown-menu minHeight50 maxHeight250" role="menu" aria-labelledby="menu12">
									<li><a href="${SourceLeadWeb_sourceleadUrl}/campaignSendersList">Senders</a></li>
									<li><a href="${SourceLeadWeb_sourceleadUrl}/recipientsList">Recipients</a></li>
									</ul>
								</div>
								<!-- Dropdown -->
								<a href="#">
								<div class="clearfix text-center mb10"><i class="campaingingSVG"></i></div>
								<div class="clearfix text-center">
									<h4>Campaigns</h4>
								</div>
								</a>
								
							</div>
				</sec:authorize>
					
						  
							<div class="col-md-2 reportNewCta">
									<a href="${SourceLeadWeb_sourceleadUrl}/tagList"title="Tags" data-toggle="modal" id="tagsPop">
											<div class="clearfix text-center mb10"><i class="tagSVG"></i></div>
									<div class="clearfix text-center">
										<h4>Tags</h4>
									</div>
									</a>
								</div>
				
				
				   <sec:authorize access="hasRole('ROLE_PAYROLL_CONFIGURATION')">
				   
								<div class="col-md-2 reportNewCta">
								
								<a href="${SourceLeadWeb_pmsUrl}/payrollConfiguration">
									<c:if test="${SourceLeadWeb_country == 'United States'}">
										<div class="clearfix text-center mb10"><i class="payrollSVG"></i></div>
										<div class="clearfix text-center">
											<h4>Payroll Configuration</h4>
										</div>
									</c:if>
								</a>
								
								<a href="${SourceLeadWeb_pmsUrl}/indPayrollConfiguration">
									<c:if test="${SourceLeadWeb_country == 'India'}">

										<div class="clearfix text-center mb10"><i class="payrollSVG"></i></div>
										<div class="clearfix text-center">
											<h4>Payroll Configuration</h4>
										</div>
									</c:if>
								</a>
								
								
							</div>			  
				  </sec:authorize>
				 
			      	   
			     <div class="col-md-2 reportNewCta">			
				            <a href="${SourceLeadWeb_pmsUrl}/projectAttributes">
							<div class="clearfix text-center mb10"><i class="projectAttributesSVG"></i></div>
					<div class="clearfix text-center">
						<h4>Project Attributes</h4>
					</div>
				</div>	
				
				     	   
			     <div class="col-md-2 reportNewCta">			
					<a href="${SourceLeadWeb_sourceleadUrl}/emailreminderConfigurationDashboard">
							<div class="clearfix text-center mb10"><i class="reminderconfigSVG"></i></div>
					<div class="clearfix text-center">
						<h4>Reminder Configuration</h4>
					</div>
				</div>	
		
		 <sec:authorize access="hasRole('ROLE_ADD_PERFORMANCE_APPRAISAL_CONFIGURATION')">
		 <div class="col-md-2 reportNewCta">			
					<a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">
							<div class="clearfix text-center mb10"><i class="roleconfigSVG"></i></div>
					<div class="clearfix text-center">
						<h4>Performance Management</h4>
					</div>
					</a>
				</div>	
		 </sec:authorize>		
					
				
				<sec:authorize access="hasRole('ROLE_TICKET_CONFIGURATION')">
					<div class="col-md-2 reportNewCta">
						<a href="/app/ticketManagementTypeList">
							<div class="clearfix text-center mb10"><i class="ticketManagementSVG"></i></div>
							<div class="clearfix text-center">
								<h4>Ticket Management</h4>
							</div>
						</a>
					</div>
				</sec:authorize>
				
				
				<!--  workflow settings configuration -->
			<sec:authorize access="hasAnyRole('ROLE_ADD_WORKFLOW','ROLE_EDIT_VIEW_WORKFLOW','ROLE_DELETE_WORKFLOW','ROLE_VIEW_MY_WORKFLOW')">
				<div class="col-md-2 reportNewCta">
					<a href="${SourceLeadWeb_sourceleadUrl}/workflowDashboard">
						<div class="clearfix text-center mb10">
							<i class="workflowSVG" title="Work Flow"></i>
						</div>
						<div class="clearfix text-center">
							<h4>Workflow</h4>
						</div>
					</a>
				</div>
				</sec:authorize>
				<!-- Candidate Configuration -->
				 <div class="col-md-2 reportNewCta"> 
	            <!-- DropDown -->
	            <div class="dropdown pull-right report-dropdown"> <span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu14" data-toggle="dropdown"> <span class="caret cursorPointer"></span> </span>
	              <ul class="dropdown-menu minHeight50 maxHeight250">
	                <li><a href="${SourceLeadWeb_atsUrl}/candidateSourceList">Source</a></li>
	                <li><a href="${SourceLeadWeb_atsUrl}/candidateDuplicateVerification">Duplicate Verification</a></li>
	              </ul>
	            </div>
	            <!-- Dropdown -->
	            <div class="clearfix text-center mb10"><i class="userconfigSVG"></i></div>
	            <div class="clearfix text-center">
	              <h4>Candidate</h4>
	            </div>
	          </div>
				
				<div class="col-md-2 reportNewCta">
					<a href="${SourceLeadWeb_sourceleadUrl}/clockInOutConfig">
							<div class="clearfix text-center mb10"><i class="clockAni clockIcon"></i></div>
					<div class="clearfix text-center">
						<h4>Clock In - Clock Out</h4>
					</div>
				</div>
				
				 <%-- <div class="col-md-2 reportNewCta">
					<a href="${SourceLeadWeb_sourceleadUrl}/configureCustomDashBoard">
							<div class="clearfix text-center mb10"><i class="inviteSVG"></i></div>
					<div class="clearfix text-center">
						<h4>Custom DashBoard</h4>
					</div>
				</div>  --%>
				
				 <sec:authorize access="hasRole('ROLE_ENROLL_ORG')"> 
					<div class="col-md-2 reportNewCta">
						<a href="${SourceLeadWeb_sourceleadUrl}/guestOrgActionRoles">
								<div class="clearfix text-center mb10"><i class="guestactionroleSVG"></i></div>
						<div class="clearfix text-center">
							<h4>Guest Action Roles</h4>
						</div>
					</div>	
				</sec:authorize>
				
			  <sec:authorize access="hasRole('ROLE_ENROLL_ORG')"> 
				<div class="col-md-2 reportNewCta">
					<a href="${SourceLeadWeb_sourceleadUrl}/emailTemplates">
							<div class="clearfix text-center mb10"><i class="inviteSVG"></i></div>
					<div class="clearfix text-center">
						<h4>Email Templates</h4>
					</div>
				</div>
				
				
				
				
				<div class="col-md-2 reportNewCta">
							<a href="${SourceLeadWeb_sourceleadUrl}/orgBulkUploads">
								<div class="clearfix text-center mb10">
									<i class="bulkUploadSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Bulk Uploads</h4>
								</div>
							</a>
						</div>
						
			</sec:authorize>		  		  	  
			      	   
      	  </div>
      	  
      	   </sec:authorize>
         </sec:authorize>
         
        <!-- for recruiters -->
        
        
        
        <!-- 	for recruiter login -->
        
        
		<div class="newRepList clearfix settingsRepList">
			 <sec:authorize access="hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')">
			     <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}"> 
				   <div class="col-md-2">
					   <h5>Bulk Import</h5>
					   <ul class="repSearchList list-unstyled">
					   <li><a href="${SourceLeadWeb_sourceleadUrl}/bulk-resume-import.html" title="Tags">Bulk Import</a></li>
					   </ul>
					   <div class="noDate noDateSm" style="display: none;"></div>
			       </div>
			     </c:if>		
						
				    <div class="col-md-2">
				       <h5>Bank Account</h5>
				        <ul class="repSearchList list-unstyled">
					    <li><a href="${SourceLeadWeb_atsUrl}/bankAccountDetails" title="Tags">Bank Account</a></li>
				        </ul>
				        <div class="noDate noDateSm" style="display: none;"></div>
			        </div>
										
				    <div class="col-md-2">
				       <h5>Alerts</h5>
				       <ul class="repSearchList list-unstyled">
					   <li><a href="${SourceLeadWeb_atsUrl}/jobAlertList" title="Tags">Alerts</a></li>
				       </ul>
				       <div class="noDate noDateSm" style="display: none;"></div>
			        </div>
			</sec:authorize>
			</div>
             
        
            <div class="settingConfigure newRepGrid clerfix">
        
           <sec:authorize access="hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')">
           
            <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}"> 
           	<div class="col-md-2 reportNewCta">
				<a href="${SourceLeadWeb_sourceleadUrl}/bulk-resume-import.html">
							<div class="clearfix text-center mb10"><i class="bulkImport"></i></div>
					<div class="clearfix text-center">
						<h4>Bulk Import</h4>
					</div>
						</a>
				</div>		
				</c:if>	  
			      	
			      	  
			   	<div class="col-md-2 reportNewCta">				
					<a href="${SourceLeadWeb_atsUrl}/bankAccountDetails">		
							<div class="clearfix text-center mb10"><i class="bankacountsSVG"></i></div>
					<div class="clearfix text-center">
						<h4>Bank Account</h4>
					</div>
						</a>
				</div>			  
			      	  
			      	  
			      <div class="col-md-2 reportNewCta">
					<a href="${SourceLeadWeb_atsUrl}/jobAlertList">
							<div class="clearfix text-center mb10"><i class="reminderconfigSVG"></i></div>
					<div class="clearfix text-center">
						<h4> Alerts</h4>
					</div>
					</a>
				</div>			   
           
           </sec:authorize>
           </div>
      	  
    </div>
    
  </section>
  
  
  
  
  
  <div class="popupWrap" id="modalBulkImport" role="dialog">
	<div class="popupHead"><span>Bulk Import</span><i class="closePop pull-right popClose"></i></div>
        <form:form id="bulkImportForm" name="bulkImportForm" method="post">
        <div class="popupContent">
            <div class="clearfix mb">
                <label class="noPad">Folder Path <b class="valStar">*</b></label>
                <div class="clearfix">
                    <input type="text" class="form-control input-sm" placeholder="Folder Path" id="folderPath" name="folderPath" field-name="Folder Path" data-validation="required validate_Space">
                </div>
            </div>
            <div id='bulkImportStatus' class="form-group clearfix">
            </div>
       </div>
        
        <div class="popupFoot text-right">
          <button type="button" class="slBtn md colorBtn" onclick="processCopiedFiles();">Send</button>
          <button type="button" class="slBtn md popClose" onclick="flushBulkImportPath();"> Cancel</button>
        </div>        
        
     </form:form>
   </div>
  
  
  <script>
  $("#bulkImportList").click(function(){
	  $("#modalBulkImport").fadeIn(100);
	  $(".mask").show();
	  });
  $("#bulkImportGrid").click(function(){
	  $("#modalBulkImport").fadeIn(100);
	  $(".mask").show();
	  });
  $(".close, .popClose").click(function(){
	  $("#modalInvite").fadeOut(100);
	  $("#modalBulkImport").fadeOut(100);
	  $(".mask").hide();
	  flushInviteCompany();
	  flushBulkImportPath();
 });

  
  function flushBulkImportPath() {
	  $("#folderPath").val('');
	  $("#bulkImportStatus").empty();
	  $("#bulkImportForm").find('p.jquery_form_error_message').remove();
  }
  
  
  function processCopiedFiles() {
	  if ($('#bulkImportForm').validate(false, validationSettings)) {
		  var val = $("#folderPath").val().trim();
		  $.post(sourceleadUrl+"/process-uploaded-files", {path: val}, function(data) {
			  if(data && data.indexOf("Session timedout") !== -1) {
				  setTimedout('location.href=${sourceleadLoginUrl}', 3000);
			  } else if(data) {
				  $("#bulkImportStatus").html(data)
				  if(data.indexOf("Invalid") === -1) {
					  $("#folderPath").val('');
				  }
			  }
		  });
	  } else {
			return false;
		}
	 
  }
  
  </script>
  
  
  <script>
    
$(".GView").click(function(){
    $(".newRepGrid").show();
    $(".newRepList").hide();
    $(".GView").addClass("rAct");
    $(".LView").removeClass("rAct");
    $(".repSearch").hide();
});
$(".LView").click(function(){
    $(".newRepGrid").hide();
    $(".newRepList").show();
    $(".LView").addClass("rAct");
    $(".GView").removeClass("rAct");
    $(".repSearch").show();
});        
    </script>
    
    
<script>
    
function myManagersFunction() {
	 var search = $("#repSearchaa").val().toUpperCase();
	 $(".repSearchList").each(function(){
		var count = 0;
		$(this).find("li").each(function() {
			if($(this).find("a").html().toUpperCase().indexOf(search) !== -1) {
				$(this).show();
				count++;
			} else {
				$(this).hide();
			}
		});
		if(count === 0) {
			$(this).hide();
			$(this).parent().find(".noDate").show();
		} else {
			$(this).show();
			$(this).parent().find(".noDate").hide();
		}
	 });
	
	
	
    
   /*  var input, filter, ul, li, a, i;
    input = document.getElementById("repSearchaa");
    filter = input.value.toUpperCase();
    $(".repSearchList").each(function(){
        var length = 0;
        var liList = $(this).find("li");
        for(var i=0; i < liList.length; i++){
            var li = liList[i];
            a = $(li).find('a').html().toUpperCase();
            if (a.indexOf(filter) > -1) {
                li.style.display = "";
                length++;
            } else {
                li.style.display = "none";
                //$("ul.repSearchList").hide();
            }    
        }
        if(length == 0) {
        	$(this).hide();
            $(this).parent().parent().find(".noDate").show();
        } else {
        	$(this).show();
            $(this).parent().parent().find(".noDate").hide();
        }
    }); */
    
    
    
}
    
</script>

 
  <jsp:include page="masterFooter.jsp" />	
<!-- Content Area Ends-->   