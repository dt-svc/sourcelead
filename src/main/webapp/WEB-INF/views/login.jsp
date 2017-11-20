
<!DOCTYPE html>
<html lang="en">

<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@page session="true"%>
    <c:if test="${staticTime == null || staticTime == 0 }">
    	<c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
    </c:if>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>::. Sign In .::</title>
    <link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="resources/css/register-style.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet">
	
</head>
<body>
<div class="regis_bg">
	<div class="regis_logo">
	  <img src="resources/images/SourceLead-logo.png" alt="SourceLead Logo" title="SourceLead Logo" class="img-responsive"/>
	</div>
    <div class="regis_wrap">
    	<div class="signin_wrap" id="signin">
        	<h1>Sign In</h1>
	       	<c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
			
			<c:if test="${!empty login_blocked_error}">
					 <div class="error">Your user account is locked. Please contact your customer relationship manager</div>
	      	</c:if>
	      		
	      	<c:if test="${!empty login_inActive_error}">
	      			<div class="error"><font color="red">You are In active user</font></div>
	    	</c:if>
	        <c:if test="${!empty login_unRegistered_error}">
	         		<div class="error"><font color="red">You are not registered user. Please get register.</font></div>
	    	</c:if> 
	       <c:if test="${!empty login_password_error}">
	         		<div class="error"><font color="red">Invalid Username or Password.</font></div>
	    	</c:if> 
	    	<c:if test="${!empty login_blocked_user_error}">
	         		<div class="error"><font color="red">Your User has blocked. Please contact your admin</font></div>
	    	</c:if>
	    	<c:if test="${not empty msg}">
		        <div id="selector">
			         <span><font color="green">${msg}</font></span>
				</div>
           </c:if> 
        	 <form class="login-form clearfix" action="<c:url value='/login' />" method='POST'>
	            <span>
	            	<label>User name</label>
	                <i class="fa fa-user"></i>
	                <input type="text" name="username"  value="${username}" class="signin_fild" required />
	                <b></b>
	            </span>
	            <span>
	            	<label>Password</label>
	                <i class="fa fa-key si-pass"></i>
	                <input type="Password"  name="password"  value="${password}"  class="signin_fild" required />
	                <b></b>
	            </span>
	            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	            <input type="hidden" name="UUID" value="${UUID}" />
	            <input type="hidden" name="spocUUID" value="${spocUUID}" />
	            <input type="hidden" name="requirementUUID" value="${requirementUUID}" />
	            <input type="hidden" name="referUUID" value="${referUUID}" />
	            
	            <div class="for-pas-text"><a href="<c:url value='/forgotPassword'/>">Forgot Password</a></div>
	            <input type="submit" value="Submit" class="sign_btn" />
	            <div class= "clearfix"></div>
	            
	            <c:if test="${!empty requirementUUID && empty referUUID}">
	             <div class="for-pas-text pull-left"><h5>Don't have an account? &nbsp<a href="<c:url value='/register?requirementUUID=${requirementUUID}'/>">Sign up</a></h5></div>
	            </c:if>
	            
	            <c:if test="${!empty requirementUUID && !empty referUUID}">
	             <div class="for-pas-text pull-left"><h5>Don't have an account? &nbsp<a href="<c:url value='/register?requirementUUID=${requirementUUID}&referUUID=${referUUID}'/>">Sign up</a></h5></div>
	            </c:if>
	            
	            <c:if test="${empty requirementUUID && empty referUUID}">
	             <div class="for-pas-text pull-left"><h5>Don't have an account? &nbsp<a href="<c:url value='/register'/>">Sign up</a></h5></div>
	            </c:if>
	            
            </form>          
        </div>
    </div>
</div>  
  
	<%-- <script src="resources/js/jquery.min.js?staticTime=${staticTime}"></script>
    <script src="resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
    <script src="resources/js/bootstrap.min.js?staticTime=${staticTime}"></script>
    <script src="resources/js/jquery-fun.js?staticTime=${staticTime}"></script>  --%>
    
</body>

<script>
$(function () {
     applyInputSlide();
});

function applyInputSlide() {
    $(".signin_wrap span input").each(function (i) {
        if ($(this).val().trim() !== "")
            $(this).parent(".signin_wrap span").find('label').addClass('test');
    });
    $(".signin_wrap span input").on('focus', function () {
        $(this).parent(".signin_wrap span").find('label').addClass('test');
    });
    $(".signin_wrap span input").on('blur', function () {
        if ($(this).val().trim() === "")
            $(this).parent(".signin_wrap span").find('label').removeClass('test');
    });
}
</script>

<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 

</html>
