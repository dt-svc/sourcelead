<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Onboard Dashboard</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Employee onboarding</div>
 </section>
 
 <div class="content-area content-new clearfix">
      <div class="onboardDashboard_counts">
        <div class="tabbable positionRelative">
          <div class="onboardcreateButtons"> 
	          <a href="<c:url value='/candidateOnBoardCreation'/>" class="btn btn-success btn-sm mr" title="Create onboard">
	          	<i class="fa fa-plus paddingRight"></i>Create onboard
	          </a> <a href="offboard_candidate.html" class="btn btn-primary btn-sm" title="Create offboard">
	          	<i class="fa fa-plus paddingRight"></i>Create offboard
	          </a>
          </div>
          <ul class="nav nav-tabs" id="tab_bar">
            
            <li class="active"> <a href="#" data-toggle="tab" class="onboardCount" title="Current onboard count">
              <div class="count"> 1 </div>
              <h4>Current onboard</h4>
              </a> 
            </li>
            
            <li> <a href="#documents" data-toggle="tab" title="Current offboard count">
              <div class="count"> 10 </div>
              <h4>Current offboard</h4>
              </a> 
            </li>
          </ul>
          </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
 </section>
    
 <jsp:include page="../masterFooter.jsp" />