<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
    <div id="onboardingLoading"></div>
    <section class="content-header clearfix">
          <div class="pull-left">
      <!-- <h2>Submission Templates</h2> -->
      <h2 class="pull-left">Templates</h2>
				
    </div>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Templates</div>
 <div class="clearfix"></div>
    </section>
    
    
 
<section class="landTemp">
	<div class="landTempCta">
		<div class="pull-left mr10">
           <i class="fa fa-address-book-o fa-2x text-primary"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-primary">Applicant Template</h2>
		<p>Manage Your Applicant Templates</p>
		<span>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE')"> 
			<a class="btn btn-info btn-sm mr10" href="<c:url value="/orgSubmissionTemplate" />">Add</a>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE')"> 
			<a class="btn btn-primary btn-sm" href="<c:url value="/orgSubmissionTemplate" />">View</a>
			</sec:authorize>
		</span>
		</div>
	</div>
	<div class="landTempCta">
	<div class="pull-left mr10">
           <i class="fa fa-object-ungroup fa-2x text-info"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-info">Contract Template</h2>
		<p>Manage Your Contract Templates</p>
		<span>
			<sec:authorize access="hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE')">
			<a class="btn btn-info btn-sm mr10" href="<c:url value="/addContractTemplate" />">Add</a> 
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE')"> 
			<a class="btn btn-primary btn-sm" href="<c:url value="/orgContractTemplate" />">View</a>
			</sec:authorize>
		</span>
	</div>
	</div>
	<div class="landTempCta nomr">
	<div class="pull-left mr10">
           <i class="fa fa-handshake-o fa-2x text-success"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-success">Onboard Template</h2>
		<p>Manage Your Onboard Templates</p>
		<span>
			<sec:authorize access="hasRole('ROLE_CREATE_ON_BOARD_TEMPLATE')"> 
			<a class="btn btn-info btn-sm mr10" href="<c:url value='/addNewOnboardTemplate'/>">Add</a> 
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE')"> 
			<a class="btn btn-primary btn-sm" href="<c:url value="/onboardingTemplatesList" />">View</a>
			</sec:authorize>
		</span>
		</div>
	</div>
	<div class="landTempCta">
	<div class="pull-left mr10">
           <i class="fa fa-sign-out fa-2x text-danger"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-danger">Offboard Template</h2>
		<p>Manage Your Offboard Templates</p>
		<span>
			<sec:authorize access="hasRole('ROLE_CREATE_OFF_BOARD_TEMPLATE')"> 
			<a class="btn btn-info btn-sm mr10" href="<c:url value='/addNewOffboardTemplate'/>">Add</a>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE')"> 
			<a class="btn btn-primary btn-sm" href="<c:url value="/offboardingTemplatesList" />">View</a>
			</sec:authorize>
		</span>
		</div>
	</div>
	<div class="landTempCta nomr">
	<div class="pull-left mr10">
           <i class="fa fa-suitcase fa-2x text-warning"></i>
        </div>
		<div class="pull-left">
		<h2 class="text-warning">Vendor Onboard Template</h2>
		<p>Manage Your Vendor Onboard Templates</p>
		<span>
			<a  class="btn btn-info btn-sm mr10" href="<c:url value='/addNewVendorOnboardTemplate'/>">Add</a> 
			<a  class="btn btn-primary btn-sm" href="<c:url value="/vendorOnboardTemplateList" />">View</a>
		</span>
		</div>
	</div>
	
	<div class="clearfix"></div>
</section>
    
        
</section>






<jsp:include page="masterFooter.jsp" />