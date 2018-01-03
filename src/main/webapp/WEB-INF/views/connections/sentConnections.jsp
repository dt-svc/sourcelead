<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Source Lead :: Recruitment-Add Vendor</title>
<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>
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
			<li><a href="<c:url value='${appPath}/findConnections'/>">Find</a></li>
			<li><a href="<c:url value='${appPath}/connectedList'/>">Connected</a></li>
			<li class="current"><a href="${appPath}/connectionSent">Sent</a></li>
			<li><a href="<c:url value='${appPath}/connectionReceived'/>">Received</a></li>
		</ul>
		<span><input class="immigraTabSerach" type="text"></span>
	</div>
	<div class="conneWrapInn">
	<c:forEach items="${sentConnections}" var="employee">	
		<div class="connCta">
			<div class="connCtaInn">
				<div class="connCtaInnText">
					<span><img data-id= 'userProfileImage' onerror='predefineProfileImage(this);' src='${employee.fileUrl}' class='m-t-xs'   alt='image'></span>
					<a href="javascript:void(0)"><h2>${employee.firstName}&nbsp;${employee.lastName}</h2>
					<p><c:out value="${employee.userType}"/> <b> at ${employee.orgName} </b>${employee.city} </p></a>
					<a href="javascript:void(0)" class="conUnder"><p>${employee.mutCount  == '0' ? 'No Mutual Connections' : employee.mutCount+' mutual connections'}</p></a>
				</div>
				<div class="connCtaBtn">
					<a href="${appPath}/withdrawRequest/${employee.id}" class="connlink orange">Withdraw</a>
				</div>
			</div>
		</div>
		</c:forEach>
		</div>	
	<div class="conneWrapInn">
		</div>															
		<div class="clearfix"></div>
</div>
			

</section>
<jsp:include page="../masterFooter.jsp" />
</body>
</html>
