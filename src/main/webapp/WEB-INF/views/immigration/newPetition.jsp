<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/resources/js/immigration.js?staticTime=${staticTime}"></script>
 <section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix addImmiHead">
      <div class="pull-left positionRelative"><h2>New Petition: <span id="H1B Visa" class="text-muted paddingLeft">H1B Visa</span></h2>
      </div>
      <c:choose>
	   		<c:when test="${myImmigrationPage =='Y'}">
	   			<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="/app/immigration/myImmigration">My Immigrations List</a> &raquo; New Petition</div>
			</c:when>
			<c:otherwise>
				<div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="/app/immigration/viewImmigrationList">Petition List</a>&raquo; New Petition</div>
			</c:otherwise>
		</c:choose>
      
	<div class="clearfix"></div>
    </section>
    <div id="loadingPetitionDiv"></div>
    <div id="newPetitionTemplateDiv"></div>
     <!-- Content Area -->
  <c:set var = "immigrationIdCode" scope = "page" value = '<%= session.getAttribute("immigrationIdCode")%>'/>
  <script type="text/javascript">
  $( document ).ready(function() {
	  $("#newPetitionTemplateDiv").load("/app/immigration/loadNewPetitionPage?forward=petitionDetails&immigrationUUID=${immigrationUUID}");
	  console.info("inside new petition template jsp------>");
	  /* $("#newPetitonPageTwoDiv").hide(); */
	
	});
  </script>
  <jsp:include page="../masterFooter.jsp" />
