<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="resources/css/login.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet">
 
 <div id="slideit" class="bgImg" style="background-image: url(resources/images/1.jpg)"></div>
 
 <div class="signWrap">
 <div id="loading"></div>
 <c:if test="${!empty login_2_Step_Process_Enabled}">
  <jsp:include page="twoStepVerification.jsp"></jsp:include>
</c:if>

<c:if test="${empty login_2_Step_Process_Enabled}">
    <jsp:include page="SignIn.jsp"></jsp:include>
</c:if>

 </div>
 