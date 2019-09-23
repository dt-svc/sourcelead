<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
    <div id="onboardingLoading"></div>
    <section class="content-header clearfix">
          <div class="pull-left"> <h2 class="pull-left">Templates</h2></div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/settings'/>">Settings</a> &raquo; Templates</div>
 		<div class="clearfix"></div>
    </section>
    
 
<section class="landTemp reportNewDash">
	<div class="landTempCta">
		<div class="pull-left mr10">
           <i class="fa fa-address-book-o fa-2x text-primary"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-primary">Applicant</h2>
		<p>Manage Your Applicant Templates</p>
		<span>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE')"> 
			<a class="btn btn-info btn-sm mr10" href="<c:url value="${appPath}/orgSubmissionTemplate?type=addTemplate" />">Add</a>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE')"> 
			<a class="btn btn-primary btn-sm" href="<c:url value="${appPath}/orgSubmissionTemplate" />">View</a>
			</sec:authorize>
		</span>
		</div>
	</div>
	<div class="landTempCta">
	<div class="pull-left mr10">
           <i class="fa fa-object-ungroup fa-2x text-info"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-info">Contract</h2>
		<p>Manage Your Contract Templates</p>
		<span>
			<sec:authorize access="hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE')">
			<a class="btn btn-info btn-sm mr10" href="<c:url value="${appPath}/addContractTemplate" />">Add</a> 
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE')"> 
			<a class="btn btn-primary btn-sm" href="<c:url value="${appPath}/orgContractTemplate" />">View</a>
			</sec:authorize>
		</span>
	</div>
	</div>
	<div class="landTempCta nomr">
	<div class="pull-left mr10">
           <i class="fa fa-handshake-o fa-2x text-success"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-success">Onboard</h2>
		<p>Manage Your Onboard Templates</p>
		<span>
			<sec:authorize access="hasRole('ROLE_CREATE_ON_BOARD_TEMPLATE')"> 
			<a class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/addNewOnboardTemplate'/>">Add</a> 
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE')"> 
			<a class="btn btn-primary btn-sm" href="<c:url value="${appPath}/onboardingTemplatesList" />">View</a>
			</sec:authorize>
		</span>
		</div>
	</div>
	<div class="landTempCta">
	<div class="pull-left mr10">
           <i class="fa fa-sign-out fa-2x text-danger"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-danger">Offboard</h2>
		<p>Manage Your Offboard Templates</p>
		<span>
			<sec:authorize access="hasRole('ROLE_CREATE_OFF_BOARD_TEMPLATE')"> 
			<a class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/addNewOffboardTemplate'/>">Add</a>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE')"> 
			<a class="btn btn-primary btn-sm" href="<c:url value="${appPath}/offboardingTemplatesList" />">View</a>
			</sec:authorize>
		</span>
		</div>
	</div>
	<div class="landTempCta nomr">
	<div class="pull-left mr10">
           <i class="fa fa-suitcase fa-2x text-warning"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-warning">Vendor Onboard</h2>
		<p>Manage Your Vendor Onboard Templates</p>
		<span>
			<a  class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/addNewVendorOnboardTemplate'/>">Add</a> 
			<a  class="btn btn-primary btn-sm" href="<c:url value="${appPath}/vendorOnboardTemplateList" />">View</a>
		</span>
		</div>
	</div>
	
	
	<div class="landTempCta nomr">
	<div class="pull-left mr10">
           <i class="fa fa-briefcase fa-2x text-success"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-success">Job</h2>
		<p>Manage Your Job Templates</p>
		<span>
		<sec:authorize access="hasAnyRole('ROLE_CREATE_REQUIREMENT_TEMPLATE','ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE','ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE')">
			<a  class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/addTemplate'/>">Add</a> 
			<a  class="btn btn-primary btn-sm" href="<c:url value="${appPath}/template" />">View</a>
			</sec:authorize>
		</span>
		</div>
	</div>
	<div class="landTempCta nomr">
	<div class="pull-left mr10">
           <i class="fa fa-address-card-o fa-2x text-info"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-info">Interview</h2>
		<p>Manage Your Interview Templates</p>
		<span>
		<sec:authorize access="hasAnyRole('ROLE_CREATE_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_VIEW_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_EDIT_INTEVIEW_FEEDBACK_TEMPLATE')">
			<a  class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/interViewFeedBackTemplate?type=addInterViewFeedBackTemplate'/>">Add</a> 
			<a  class="btn btn-primary btn-sm" href="<c:url value="${appPath}/interViewFeedBackTemplate" />">View</a>
			</sec:authorize>
		</span>
		</div>
	</div>
	
	<sec:authorize access="hasAnyRole('ROLE_ADD_PETITION_TEMPLATE','ROLE_EDIT_VIEW_PETITION_TEMPLATE','ROLE_DELETE_PETITION_TEMPLATE')">
		<div class="landTempCta nomr">
		<div class="pull-left mr10">
	           <i class="fa fa-plane fa-2x text-info"></i>
	        </div>
			<div class="pull-left">
			<h2 class="text-info">Immigration</h2>
			<p>Manage Your Immigration Templates</p>
			<span>
			 
				<a  class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/immigration/addNewPetitionTemplate'/>">Add</a> 
				<a  class="btn btn-primary btn-sm" href="<c:url value='${appPath}/immigration/viewImmigrationTemplateList'/>">View</a>
				
			</span>
			</div>
		</div>
	</sec:authorize>
	
	<div class="landTempCta nomr">
	<div class="pull-left mr10">
           <i class="fa fa-file fa-2x text-success"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-success">Letterhead</h2>
		<p>Manage your org letterheads</p>
		<span>
			<a  class="btn btn-info btn-sm mr10" href="<c:url value="${appPath}/letterHead"/>">Add</a> 
			<a  class="btn btn-primary btn-sm" href="<c:url value="${appPath}/letterHeadList" />">View</a>
		</span>
		</div>
	</div>
	
	<div class="landTempCta nomr">
	<div class="pull-left mr10">

           <i class="fa fa-check fa-2x text-success"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-success">Employment Verification</h2>
		<p>Manage Employment Verification Templates</p>
		<span>
			<a  class="btn btn-info btn-sm mr10" href="<c:url value='${appPath}/addEmploymentVerificationTemplate'/>">Add</a> 
			<a  class="btn btn-primary btn-sm" href="<c:url value="${appPath}/employmentVerificationTemplateList" />">View</a>
		</span>
		</div>
	</div>
	
	<div class="landTempCta nomr">
	<div class="pull-left mr10">
           <i class="fa fa-envelope fa-2x text-info"></i>
    </div>
		<div class="pull-left">
		<h2 class="text-info">Email</h2>
		<p>Manage Your Email Templates</p>
		<span>
		
		
        	<a  class="btn btn-primary btn-sm" href="<c:url value="${appPath}/emailTemplates" />">View</a>
        
			

		</span>
		</div>
	</div>
	
	<div class="landTempCta">
		<div class="pull-left mr10">
	           <i class="fa fa-envelope-open-o fa-2x text-info"></i>
	        </div>
			<div class="pull-left">
			<h2 class="text-info">Offer Letter</h2>
			<p>Manage Your Offer Letter Templates</p>
			<span>
				<a class="btn btn-info btn-sm mr10" href="${SourceLeadWeb_sourceleadUrl}/addOfferLetterTemplate">Add</a> 
				<a class="btn btn-primary btn-sm" href="${SourceLeadWeb_sourceleadUrl}/viewOfferLetterTemplate">View</a>
			</span>
		</div>
		</div> 
	<div class="clearfix"></div>
</section>
    
        
</section>






<jsp:include page="masterFooter.jsp" />