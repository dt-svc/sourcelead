<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript" src="jquery-1.2.6.min.js"></script>
<jsp:include page="assetManagementDetails.jsp" />
<script>
$(document).ready(function() {
	assetDetails('${asset.id}');
});	
</script>

<section class="content-wraper">
	<section class="content-header clearfix">
	  <div class="pull-left">
	    <h2>Asset Management Summary</h2>
	  </div>
	  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/assetManagement'/>">Assets List</a> &raquo; Asset Summary</div>
	</section>

	<!-- Asset Management Summary tabs starts here  -->
	<div class="clearfix"></div>
	<div class="project_side_wrap_inn_tabs">
		<ul class="project-tabs-menu">
			<li class="current"><a href="#tab-1" onclick="assetDetails('${asset.id}');">Details</a></li>
			<li><a href="#tab-2" onclick="assetNotes('${asset.id}');">Notes</a></li>
		</ul>
		<div class="clearfix"></div>
			<div class="project_tabs">
				<div id="tab-1" class="project_tabs_inn">
					<div class="panel-body" >Asset Management Details</div>
				</div>
				<div id="tab-2" class="project_tabs_inn">
				</div>
			</div>
	</div>
	<!-- Asset Management Summary tabs starts here  -->

</section>

<script>
$("#tab-1").show();
$(".project-tabs-menu a").click(function(event) {
    event.preventDefault();
    $(this).parent().addClass("current");
    $(this).parent().siblings().removeClass("current");
    var tab = $(this).attr("href");
    $(".project_tabs_inn").not(tab).css("display", "none");
    $(tab).fadeIn();
});

// script for scroll Popups:: Add or Edit details
$(document).ready(function(event) {	
$(".addUser-org").click(function(){
	 $(".edit-details").toggleClass("edit-details-active");
	 $(".collapse-div").removeClass("edit-details-active");
 });
 $(".close-div").click(function(){
	 $(this).closest(".edit-details").toggleClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
/*  $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	 */
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   event.preventDefault();
});
</script>

<jsp:include page="../masterFooter.jsp" />