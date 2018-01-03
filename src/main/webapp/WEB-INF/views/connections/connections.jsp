
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
<title>Insert title here</title>
 
 <script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
	$("#connectId").click(function(){
		alert("welcome")
		$("#mailMsg").fadeIn(200);
	    $('#mailMsg').delay(2000).fadeOut('slow');	
	})
	
</script>

<!-- Content Area -->
<div class="success_pop" id="mailMsg" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Invitation sent successfully</span>
</div>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Connections</h2>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Conections</div>
</section>


<div class="conneWrap">
	<div class="conneWrapHead clearfix">
		<ul class="clearfix">
			<li class="current"><a href="<c:url value='${appPath}/findConnections'/>">Find</a></li>
			<li><a href="<c:url value='${appPath}/connectedList'/>">Connected</a></li>
			<li><a href="${appPath}/connectionSent">Sent</a></li>
			<li><a href="<c:url value='${appPath}/connectionReceived'/>">Received</a></li>
		</ul>
		<span><input class="immigraTabSerach" type="text"></span>
	</div>
	<div class="conneWrapInn">	
	<c:forEach items="${findConnList}" var="employee">	
		<div class="connCta">
			<div class="connCtaInn">
				<div class="connCtaInnText">
				   <span><img data-id= 'userProfileImage' onerror='predefineProfileImage(this);' src='${employee.fileUrl}' class='m-t-xs'   alt='image'></span>
					<a href="javascript:void(0)"><h2>${employee.firstName}&nbsp;${employee.lastName}</h2>
					<p><c:out value="${employee.userType}"/> <b> at ${employee.orgName} </b>${employee.city} </p></a>
					<a href="javascript:void(0)" class="conUnder"><p>${employee.mutCount  == '0' ? 'No Mutual Connections' : employee.mutCount+' mutual connections'}</p></a>
				</div>
				<div class="connCtaBtn">
					<a href="${appPath}/clickConnect/${employee.id}" id="connectId" class="connlink" >Connect</a>
				</div>
			</div>
		</div>
		</c:forEach>
		<div class="clearfix"></div>
	</div>
</div>
			

</section>
<jsp:include page="../masterFooter.jsp" />
 
