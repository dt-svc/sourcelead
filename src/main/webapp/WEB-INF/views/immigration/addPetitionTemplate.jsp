<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/resources/js/immigration.js"></script>
<section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix cusFieldHead">
      <div class="pull-left positionRelative"><h2>Add Petitation Templates</h2></div>
      <div class="pull-right brud-crum">Home &raquo; Petitation Templates &raquo; Add Templates</div>
	<div class="clearfix"></div>
    </section>
    <div id="addNewPetitionTemplateDiv"></div>
</section>
  <!-- Content Area -->
  <script type="text/javascript">
  $( document ).ready(function() {
	  $("#addNewPetitionTemplateDiv").load("/app/loadAddPetitionTemplatePage?status=one");
	  /* $("#newPetitonPageTwoDiv").hide(); */
	  registerFunc();
	});
  </script>
  <jsp:include page="../masterFooter.jsp" />