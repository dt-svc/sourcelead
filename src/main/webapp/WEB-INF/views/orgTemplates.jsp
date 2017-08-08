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
		<h2>Submission Template</h2>
		<p>Manage your Submission Templates</p>
		<span>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE')"> 
			<a href="<c:url value="/orgSubmissionTemplate" />">Add Template</a> | &nbsp; 
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_APPLICANT_TEMPLATE','ROLE_VIEW_APPLICANT_TEMPLATE','ROLE_VIEW_EDIT_APPLICANT_TEMPLATE')"> 
			<a href="<c:url value="/orgSubmissionTemplate" />">View List</a>
			</sec:authorize>
		</span>
	</div>
	<div class="landTempCta">
		<h2>Contract Template</h2>
		<p>Manage your Contract Templates</p>
		<span>
			<sec:authorize access="hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE')">
			<a href="<c:url value="/addContractTemplate" />">Add Template</a> | &nbsp; 
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE','ROLE_VIEW_EDIT_CONTRACT_TEMPLATE')"> 
			<a href="<c:url value="/orgContractTemplate" />">View List</a>
			</sec:authorize>
			
		</span>
	</div>
	<div class="landTempCta nomr">
		<h2>Onboard Template</h2>
		<p>Manage your Onboard Templates</p>
		<span>
			<sec:authorize access="hasRole('ROLE_CREATE_ON_BOARD_TEMPLATE')"> 
			<a href="<c:url value='/addNewOnboardTemplate'/>">Add Template</a> | &nbsp; 
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_TEMPLATE','ROLE_VIEW_EDIT_ON_BOARD_TEMPLATE')"> 
			<a href="<c:url value="/onboardingTemplatesList" />">View List</a>
			</sec:authorize>
		</span>
	</div>
	<div class="landTempCta">
		<h2>Offboard Template</h2>
		<p>Manage your Offboard Templates</p>
		<span>
			<sec:authorize access="hasRole('ROLE_CREATE_OFF_BOARD_TEMPLATE')"> 
			<a href="<c:url value='/addNewOffboardTemplate'/>">Add Template</a> | &nbsp; 
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_CREATE_OFF_BOARD_TEMPLATE','ROLE_VIEW_EDIT_OFF_BOARD_TEMPLATE')"> 
			<a href="<c:url value="/offboardingTemplatesList" />">View List</a>
			</sec:authorize>
		</span>
	</div>
	
	<div class="clearfix"></div>
</section>
    
        
</section>






<jsp:include page="masterFooter.jsp" />