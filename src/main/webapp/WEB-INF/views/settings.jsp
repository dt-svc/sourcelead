<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Settings Dashboard</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Settings
		</div>
	</section>
	<div class="reportNewDash clearfix">
		<div class="reqListBtn clearfix mb10" style="display: none;">
			<div class="pull-left">
				<span class="GView rAct" title="Graphical View">
					<i class="fa fa-th-large"></i>
				</span>
				<span class="LView" title="List View">
					<i class="fa fa-list"></i>
				</span>
			</div>
			<h4 class="pull-left text-muted" id="SettingsHeader"
				style="display: none">Settings</h4>
			<div class="repSearch">
				<input class="innSeaFld" id="repSearchaa"
					onKeyUp="myManagersFunction();" type="text" placeholder="Search">
			</div>
		</div>

		<sec:authorize access="!hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')">
			<sec:authorize
				access="hasAnyRole('ROLE_ENROLL_ORG','ROLE_ENROLL_NEW_ROLE','ROLE_VIEW_EDIT_ORG_SETTINGS','ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE','ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE','ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE','ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE','ROLE_ADD_EXPENSE_TYPE','ROLE_VIEW_EDIT_EXPENSE_TYPE','ROLE_DELETE_EXPENSE_TYPE')">
				<!-- 	List view jsp code -->
				<div class="newRepList clearfix settingsRepList">
					<div class="clearfix"></div>
					<sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2">
							<h5>Schedule Maintenance</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="${sourceleadUrl}/loadScheduleMaintenance" title="License Upgrade">Schedule Maintenance</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>

					<c:if test="${SourceLeadWeb_isSuperAdmin == 'Y'}">
						<div class="col-md-2">
							<h5>License Upgrade</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="/app/orgLicenseSubscriptionList" title="License Upgrade">License Upgrade</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</c:if>

					<sec:authorize access="!hasRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2">
							<h5>Bulk Import</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="/app/bulk-resume-import.html" title="License Upgrade">Bulk Import</a></li>
								<li><a data-toggle="modal" href="javascript:void(0)" id='bulkImportList'>Process File</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>


					<div class="col-md-2">
						<h5>Org Chart</h5>
						<ul class="repSearchList list-unstyled">
							<li><a href="/app/orgChart" title="License Upgrade">Org Chart</a></li>
						</ul>
						<div class="noDate noDateSm" style="display: none;"></div>
					</div>

					<sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')">
						<div class="col-md-2">
							<h5>Role Configuration</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="/app/orgRolesList" title="License Upgrade">Role Configuration</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>

					<sec:authorize
						access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE','ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE','ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE','ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE')">
						<div class="col-md-2">
							<h5>Templates</h5>
							<ul class="repSearchList list-unstyled ">
								<li><a href="/app/orgTemplates" title="License Upgrade">Templates</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>

					<sec:authorize access="hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')">
						<div class="col-md-2">
							<h5>Org Settings</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="/app/orgSettings" title="License Upgrade">Org Settings</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>


					<div class="clearfix"></div>
					<sec:authorize
						access="hasAnyRole('ROLE_ADD_EXPENSE_TYPE','ROLE_VIEW_EDIT_EXPENSE_TYPE','ROLE_DELETE_EXPENSE_TYPE')">
						<div class="col-md-2">
							<h5>Expense Category</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="/app/expenseCategory" title="License Upgrade">Expense Category</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>

					<div class="col-md-2">
						<h5>Bank Accounts</h5>
						<ul class="repSearchList list-unstyled">
							<li><a href="/app/bankAccountDetails" title="License Upgrade">Bank Accounts</a></li>
						</ul>
						<div class="noDate noDateSm" style="display: none;"></div>
					</div>

					<sec:authorize access="hasRole('ROLE_LEAVE_CONFIGURE')">
						<div class="col-md-2">
							<h5>Leave Configuration</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="/app/leaveConfiguration" title="License Upgrade">Leave Configuration</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>


					<sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')">
						<div class="col-md-2">
							<h5>Job Board Configuration</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="/app/job-boards" title="Job Board Configuration">Job Board Configuration</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>


					<sec:authorize access="!hasRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2">
							<h5>Custom Fields</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="/app/customFields" title="Custom Fields">Custom Fields</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>

					<sec:authorize
						access="hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_JOB_CAMPAIGN')">
						<div class="col-md-2">
							<h5>Campaigns</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="${appPath}/campaignSendersList"
									title="Campaigns">Senders</a></li>
								<li><a href="${appPath}/recipientsList" title="Campaigns">Recipients</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>

					<sec:authorize access="hasRole('ROLE_PAYROLL_CONFIGURATION')">
						<div class="col-md-2">
							<h5>Payroll Configuration</h5>
							<ul class="repSearchList list-unstyled">
								<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
									<li><a href="/app/payrollConfiguration" title="Campaigns">Payroll Configuration</a></li>
								</c:if>
								<c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
									<li><a href="${appPath}/indPayrollConfiguration" title="Campaigns">Payroll Configuration</a></li>
								</c:if>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>

					<!-- Dont remove this comment. Work in progress -->

					<div class="col-md-2">
						<h5>Tags</h5>
						<ul class="repSearchList list-unstyled">
							<li><a href="${sourceleadUrl}/tagList" title="Tags">Tags</a></li>
						</ul>
						<div class="noDate noDateSm" style="display: none;"></div>
					</div>

					<div class="col-md-2">
						<h5>Project Attributes</h5>
						<ul class="repSearchList list-unstyled">
							<li><a href="/app/projectAttributes" title="Tags">Project Attributes</a></li>
						</ul>
						<div class="noDate noDateSm" style="display: none;"></div>
					</div>

					<div class="col-md-2">
						<h5>Reminder Configuration</h5>
						<ul class="repSearchList list-unstyled">
							<li><a href="/app/emailreminderConfigurationDashboard" title="Tags">Reminder Configuration</a></li>
						</ul>
						<div class="noDate noDateSm" style="display: none;"></div>
					</div>
					
					<sec:authorize access="hasRole('ROLE_TICKET_CONFIGURATION')">
						<div class="col-md-2">
							<h5>Ticket Management</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="/app/ticketManagementTypeList" title="Tags">Ticket Management</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>

					
					<sec:authorize access="hasRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2">
							<h5>Guest Action Roles</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="${sourceleadUrl}/guestOrgActionRoles" title="Tags">Guest Action Roles</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>

					<sec:authorize access="hasRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2">
							<h5>Email Templates</h5>
							<ul class="repSearchList list-unstyled">
								<li><a href="${sourceleadUrl}/emailTemplates" title="Tags">Email Templates</a></li>
							</ul>
							<div class="noDate noDateSm" style="display: none;"></div>
						</div>
					</sec:authorize>
				</div>
				<!--  Grid View  jsp code-->

				<div class="settingConfigure newRepGrid clerfix">

					<sec:authorize access="hasAnyRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2 reportNewCta">
							<a href="${sourceleadUrl}/loadScheduleMaintenance">
								<div class="clearfix text-center mb10">
									<i class="leaveConfiguration"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Schedule Maintenance</h4>
								</div>
							</a>
						</div>
					</sec:authorize>

					<c:if test="${SourceLeadWeb_isSuperAdmin == 'Y'}">

						<div class="col-md-2 reportNewCta">
							<!-- DropDown -->
							<a href="/app/orgLicenseSubscriptionList">
								<div class="clearfix text-center mb10">
									<i class="upgradeLicese"></i>
								</div>
								<div class="clearfix text-center">
									<h4>License Upgrade</h4>
								</div>
							</a>
						</div>
					</c:if>

					<sec:authorize access="!hasRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2 reportNewCta">
							<!-- DropDown -->
							<div class="dropdown pull-right report-dropdown">
								<span class="dropdown-toggle btn btn-sm btn-default repDropBtn" type="button" id="menu2" data-toggle="dropdown">
									<span class="caret cursorPointer"></span>
								</span>
								<ul class="dropdown-menu minHeight50 maxHeight250" role="menu" aria-labelledby="menu2">
									<li><a href="/app/bulk-resume-import.html">Bulk Import</a></li>
									<li><a data-toggle="modal" href="javascript:void(0)" id='bulkImportGrid'>Process File</a></li>
								</ul>
							</div>
							<!-- Dropdown -->
							<a href="javascript:void(0)">
								<div class="clearfix text-center mb10">
									<i class="bulkImport"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Bulk Import</h4>
								</div>
							</a>
						</div>
					</sec:authorize>


					<div class="col-md-2 reportNewCta">
						<!-- DropDown -->
						<a href="/app/orgChart">
							<div class="clearfix text-center mb10">
								<i class="orgChartSVG"></i>
							</div>
							<div class="clearfix text-center">
								<h4>Org Chart</h4>
							</div>
						</a>
					</div>
					
					<sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')">
						<div class="col-md-2 reportNewCta">
							<a href="/app/orgRolesList">
								<div class="clearfix text-center mb10">
									<i class="roleconfigSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Role Configuration</h4>
								</div>
							</a>
						</div>
					</sec:authorize>

					<sec:authorize access="hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')">
						<div class="col-md-2 reportNewCta">
							<!-- DropDown -->
							<a href="/app/orgSettings">
								<div class="clearfix text-center mb10">
									<i class="controlsSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Org Settings</h4>
								</div>
							</a>
						</div>
					</sec:authorize>

					<sec:authorize
						access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE','ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE','ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE','ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE')">
						<div class="col-md-2 reportNewCta">
							<!-- DropDown -->
							<a href="/app/orgTemplates">
								<div class="clearfix text-center mb10">
									<i class="templateSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Templates</h4>
								</div>
							</a>
						</div>
					</sec:authorize>

					<sec:authorize
						access="hasAnyRole('ROLE_ADD_EXPENSE_TYPE','ROLE_VIEW_EDIT_EXPENSE_TYPE','ROLE_DELETE_EXPENSE_TYPE')">
						<div class="col-md-2 reportNewCta">
							<!-- DropDown -->
							<a href="/app/expenseCategory">
								<div class="clearfix text-center mb10">
									<i class="expensesSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Expense Category</h4>
								</div>
							</a>
						</div>
					</sec:authorize>

					<div class="col-md-2 reportNewCta">
						<!-- DropDown -->
						<a href="/app/bankAccountDetails">
							<div class="clearfix text-center mb10">
								<i class="ind_bankacountSVG"></i>
							</div>
							<div class="clearfix text-center">
								<h4>Bank Accounts</h4>
							</div>
						</a>
					</div>

					<sec:authorize access="hasRole('ROLE_LEAVE_CONFIGURE')">
						<div class="col-md-2 reportNewCta">
							<!-- DropDown -->
							<a href="/app/leaveConfiguration">
								<div class="clearfix text-center mb10">
									<i class="leaveConfiguration"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Leave Configuration</h4>
								</div>
							</a>
						</div>
					</sec:authorize>

					<sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')">
						<div class="col-md-2 reportNewCta">
							<!-- DropDown -->
							<a href="/app/job-boards">
								<div class="clearfix text-center mb10">
									<i class="jobConfigureSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Job Board Configuration</h4>
								</div>
							</a>
						</div>
					</sec:authorize>

					<sec:authorize access="!hasRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2 reportNewCta">
							<!-- DropDown -->
							<a href="/app/customFields">
								<div class="clearfix text-center mb10">
									<i class="sortingSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Custom Fields</h4>
								</div>
							</a>
						</div>
					</sec:authorize>

					<sec:authorize
						access="hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_JOB_CAMPAIGN')">
						<div class="col-md-2 reportNewCta">
							<!-- DropDown -->
							<div class="dropdown pull-right report-dropdown">
								<span class="dropdown-toggle btn btn-sm btn-default repDropBtn"
									id="menu12" data-toggle="dropdown"> <span
									class="caret cursorPointer"></span>
								</span>

								<ul class="dropdown-menu minHeight50 maxHeight250" role="menu"
									aria-labelledby="menu12">
									<li><a href="${appPath}/campaignSendersList">Senders</a></li>
									<li><a href="${appPath}/recipientsList">Recipients</a></li>
								</ul>
							</div>
							<!-- Dropdown -->
							<a href="#">
								<div class="clearfix text-center mb10">
									<i class="campaingingSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Campaigns</h4>
								</div>
							</a>

						</div>
					</sec:authorize>


					<div class="col-md-2 reportNewCta">
						<!-- DropDown -->
						<a href="<c:url value='${sourceleadUrl}/tagList'/>" title="Tags"
							data-toggle="modal" id="tagsPop">
							<div class="clearfix text-center mb10">
								<i class="tagSVG"></i>
							</div>
							<div class="clearfix text-center">
								<h4>Tags</h4>
							</div>
						</a>
					</div>


					<sec:authorize access="hasRole('ROLE_PAYROLL_CONFIGURATION')">

						<div class="col-md-2 reportNewCta">
							<a href="/app/payrollConfiguration"> 
								<c:if test="${SourceLeadWeb_orgLocations.country == 'United States'}">
									<div class="clearfix text-center mb10">
										<i class="payrollSVG"></i>
									</div>
									<div class="clearfix text-center">
										<h4>Payroll Configuration</h4>
									</div>
								</c:if>
							</a> 
							<a href="${appPath}/indPayrollConfiguration">
								 <c:if test="${SourceLeadWeb_orgLocations.country == 'India'}">
									<div class="clearfix text-center mb10">
										<i class="ind_payrollSVG"></i>
									</div>
									<div class="clearfix text-center">
										<h4>Payroll Configuration</h4>
									</div>
								</c:if>
							</a>
						</div>
					</sec:authorize>

					<div class="col-md-2 reportNewCta">
						<a href="/app/projectAttributes">
							<div class="clearfix text-center mb10">
								<i class="projectAttributesSVG"></i>
							</div>
							<div class="clearfix text-center">
								<h4>Project Attributes</h4>
							</div>
						</a>
					</div>


					<div class="col-md-2 reportNewCta">
						<a href="/app/emailreminderConfigurationDashboard">
							<div class="clearfix text-center mb10">
								<i class="reminderconfigSVG"></i>
							</div>
							<div class="clearfix text-center">
								<h4>Reminder Configuration</h4>
							</div>
						</a>
					</div>
					
						<div class="col-md-2 reportNewCta">
								<a href="${SourceLeadWeb_sourceleadUrl}/clockInOutConfig">
								<div class="clearfix text-center mb10"><i class="timeSettIcon"></i></div>
								<div class="clearfix text-center">
								<h4>Clock In - Clock Out</h4>
								</div>
						</div>
						
					<%-- <div class="col-md-2 reportNewCta">			
							<a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">
									<div class="clearfix text-center mb10"><i class="roleconfigSVG"></i></div>
							<div class="clearfix text-center">
								<h4>Performance Management</h4>
							</div>
					</div> --%>
								
					<%-- <div class="col-md-2 reportNewCta">
						<a href="${SourceLeadWeb_sourceleadUrl}/workflowDashboard">
							<div class="clearfix text-center mb10">
								<i class="workflowSVG" title="Work Flow"></i>
							</div>
							<div class="clearfix text-center">
								<h4>Workflow</h4>
							</div>
						</a>
					</div> --%>
								
								
					<sec:authorize access="hasRole('ROLE_TICKET_CONFIGURATION')">
						<div class="col-md-2 reportNewCta">
							<a href="/app/ticketManagementTypeList">
								<div class="clearfix text-center mb10">
									<i class="ticketManagementSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Ticket Management</h4>
								</div>
							</a>
						</div>
					</sec:authorize>

					<sec:authorize access="hasRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2 reportNewCta">
							<a href="${sourceleadUrl}/guestOrgActionRoles">
								<div class="clearfix text-center mb10">
									<i class="guestactionroleSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Guest Action Roles</h4>
								</div>
						</div>
					</sec:authorize>


					<sec:authorize access="hasRole('ROLE_ENROLL_ORG')">
						<div class="col-md-2 reportNewCta">
							<a href="${sourceleadUrl}/emailTemplates">
								<div class="clearfix text-center mb10">
									<i class="inviteSVG"></i>
								</div>
								<div class="clearfix text-center">
									<h4>Email Templates</h4>
								</div>
							</a>
						</div>
						
						
						<div class="col-md-2 reportNewCta">
							<a href="${sourceleadUrl}/orgBulkUploads">
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
							<li><a href="/app/bulk-resume-import.html" title="Tags">Bulk Import</a></li>
						</ul>
						<div class="noDate noDateSm" style="display: none;"></div>
					</div>
				</c:if>

				<div class="col-md-2">
					<h5>Bank Account</h5>
					<ul class="repSearchList list-unstyled">
						<li><a href="/app/bankAccountDetails" title="Tags">Bank Account</a></li>
					</ul>
					<div class="noDate noDateSm" style="display: none;"></div>
				</div>

				<div class="col-md-2">
					<h5>Alerts</h5>
					<ul class="repSearchList list-unstyled">
						<li><a href="/app/jobAlertList" title="Tags">Alerts</a></li>
					</ul>
					<div class="noDate noDateSm" style="display: none;"></div>
				</div>
			</sec:authorize>
		</div>


		<div class="settingConfigure newRepGrid clerfix">

			<sec:authorize access="hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')">
				<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
					<div class="col-md-2 reportNewCta">
						<a href="/app/bulk-resume-import.html">
							<div class="clearfix text-center mb10">
								<i class="bulkImport"></i>
							</div>
							<div class="clearfix text-center">
								<h4>Bulk Import</h4>
							</div>
						</a>
					</div>
				</c:if>


				<div class="col-md-2 reportNewCta">
					<a href="/app/bankAccountDetails">
						<div class="clearfix text-center mb10">
							<i class="bankacountsSVG"></i>
						</div>
						<div class="clearfix text-center">
							<h4>Bank Account</h4>
						</div>
					</a>
				</div>


				<div class="col-md-2 reportNewCta">
					<a href="/app/jobAlertList">
						<div class="clearfix text-center mb10">
							<i class="reminderconfigSVG"></i>
						</div>
						<div class="clearfix text-center">
							<h4>Alerts</h4>
						</div>
					</a>
				</div>
			</sec:authorize>
		</div>
	</div>
</section>

<div class="popupWrap" id="modalBulkImport" role="dialog">
	<div class="popupHead">
		<span>Bulk Import</span><i class="closePop pull-right popClose"></i>
	</div>
	<form:form id="bulkImportForm" name="bulkImportForm" method="post">
		<div class="popupContent">
			<div class="clearfix mb">
				<label class="noPad">Folder Path <b class="valStar">*</b></label>
				<div class="clearfix">
					<input type="text" class="form-control input-sm" placeholder="Folder Path" id="folderPath" name="folderPath"
						field-name="Folder Path" data-validation="required validate_Space">
				</div>
			</div>
			<div id='bulkImportStatus' class="form-group clearfix"></div>
		</div>

		<div class="popupFoot text-right">
			<button type="button" class="slBtn md colorBtn" onclick="processCopiedFiles();">Send</button>
			<button type="button" class="slBtn md popClose" onclick="flushBulkImportPath();">Cancel</button>
		</div>

	</form:form>
</div>


<script>
	$("#bulkImportList").click(function() {
		$("#modalBulkImport").fadeIn(100);
		$(".mask").show();
	});
	$("#bulkImportGrid").click(function() {
		$("#modalBulkImport").fadeIn(100);
		$(".mask").show();
	});
	$(".close, .popClose").click(function() {
		$("#modalInvite").fadeOut(100);
		$("#modalBulkImport").fadeOut(100);
		$(".mask").hide();
		flushInviteCompany();
		flushBulkImportPath();
	});

	function flushBulkImportPath() {
		$("#folderPath").val('');
	}

	function processCopiedFiles() {
		if ($('#bulkImportForm').validate(false, validationSettings)) {
			var val = $("#folderPath").val().trim();
			$.post(sourceleadUrl + "/process-uploaded-files", {
				path : val
			}, function(data) {
				if (data && data.indexOf("Session timedout") !== -1) {
					setTimedout('location.href=${sourceleadLoginUrl}', 3000);
				} else if (data) {
					$("#bulkImportStatus").html(data)
					if (data.indexOf("Invalid") === -1) {
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
	$(".GView").click(function() {
		$(".newRepGrid").show();
		$(".newRepList").hide();
		$(".GView").addClass("rAct");
		$(".LView").removeClass("rAct");
		$(".repSearch").hide();
	});
	$(".LView").click(function() {
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
		$(".repSearchList").each(function() {
			var count = 0;
			$(this).find("li").each(function() {
				if ($(this).find("a").html().toUpperCase()
						.indexOf(search) !== -1) {
					$(this).show();
					count++;
				} else {
					$(this).hide();
				}
			});
			if (count === 0) {
				$(this).hide();
				$(this).parent().find(".noDate").show();
			} else {
				$(this).show();
				$(this).parent().find(".noDate").hide();
			}
		});
	}
</script>
<jsp:include page="masterFooter.jsp" />
<!-- Content Area Ends-->