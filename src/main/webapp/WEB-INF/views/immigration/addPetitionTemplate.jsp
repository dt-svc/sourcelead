<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<script type="text/javascript">
  var vmsUrl ='${SourceLeadWeb_vmsUrl}';
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${SourceLeadWeb_vmsUrl}/resources/js/immigration.js?staticTime=${staticTime}"></script>
<section class="content-wraper content-wraperNew">
   
    <section class="content-header clearfix cusFieldHead">
      <div class="pull-left positionRelative"><h2>Add Petition Templates</h2></div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; 
      <a href="${SourceLeadWeb_vmsUrl}/immigration/viewImmigrationTemplateList">Petition Templates</a> &raquo; Add Templates</div>
	<div class="clearfix"></div>
    </section>
    <div id="addNewPetitionTemplateDiv"></div>
   <div class="success_pop" id="selector"  style="display: none;">
		         <i class="fa fa-check-circle"></i><span></span>
			</div>
    
</section>
  <!-- Content Area -->
  <script type="text/javascript">
  $( document ).ready(function() {
	  $("#addNewPetitionTemplateDiv").load("${SourceLeadWeb_vmsUrl}/immigration/addPetitionTemplate?forwardTo=tempDetails");
	  /* $("#newPetitonPageTwoDiv").hide(); */
	});
  
  function showTemplateMsg(msg){
		$('#selector').show();
		$('#selector').text(msg)
		setTimeout(function(){ $('#selector').hide();$('#selector').text("") }, 1000);
	}
     
  </script>
  <jsp:include page="../masterFooter.jsp" />