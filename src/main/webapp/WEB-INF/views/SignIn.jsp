<!DOCTYPE html>
<html lang="en">
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@page session="true"%>
    <c:if test="${staticTime == null || staticTime == 0 }">
    	<c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
    </c:if>
    <title>::. Sign In .::</title>
    <%-- <link rel="icon" type="/resources/image/png" href="/resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="resources/css/login.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="resources/css/register-style.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet"> --%>
	
	<style>
.success_pop {
    position:fixed; text-align:left; margin:0px auto; left:0px; right:0px; top:54px; display:none;
    background-color:#34c244; z-index:10000; padding:3px 15px; width:350px; border-radius:4px; font-size:15px; font-weight:500;
    color:#FFF; box-shadow:0px 0px 5px rgba(0,0,0,0.3); word-wrap: break-word;
    }	
    .iConSize {
	position: relative; top: 5px; font-size: 30px !important;
}
	</style>
</head>
<body>


<div class="signin_wrap SignSlide">
<%   String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }
        %>
        
	<a href="/"><img src="/resources/images/SourceLead-logo.png" alt="SourceLead Logo" title="SourceLead Logo" class="img-responsive"/></a>
		<h1>Sign In</h1>
		<div id="loginWhiteListDiv" class="orgErrorMes"></div>
	       	<c:if test="${not empty error}">
				<div class="error">${error}</div>
			</c:if>
			
			<c:if test="${!empty login_blocked_error}">
					 <div class="error">Your user account is locked. Please contact your customer relationship manager</div>
	      	</c:if>
	      	
	      	<c:if test="${!empty login_offboardUser_error}">
	      			<div class="error"><font color="red">You are off boarded user</font></div>
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
	    	<c:if test="${!empty email_notVerified_error}">
	         		<div class="error"><font color="red">Your Email is not verified.   <a class="pull-right" id="resend" onclick="resend();" title="Resend Verification Link">Resend</a></font></div>
	    	</c:if> 
	    	<c:if test="${!empty invalid_IP_Address}">
	         		<div class="error"><font color="red">You are not allowed to access this location</font></div>
	    	</c:if> 
	    	<c:if test="${not empty msg}">
		        <div id="selector">
			         <span><font color="green">${msg}</font></span>
				</div>
           </c:if> 
           <form class="login-form clearfix" action="<c:url value='/login' />" method='POST'>
		<span>
			<i class="fa fa-user"></i>
			<input type="text" name="username" id="usernameval" placeholder="User name" value="${username}" class="signin_fild" required />
			<b></b>
<!-- 			<p>User name incorrect.</p> -->
		</span>
		<span>
			<i class="fa fa-lock si-pass"></i>
			<input type="Password"  name="password"  value="${password}" placeholder="Password"  class="signin_fild" required />
			<b></b>
			<!--<p>Password incorrect</p>-->
		</span>
		<input type="hidden" name="ipaddr" value="<%=ip%>" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="UUID" value="${UUID}" />
        <input type="hidden" name="spocUUID" value="${spocUUID}" />
        <input type="hidden" id="requirementUUID" name="requirementUUID" value="${requirementUUID}" />
        <input type="hidden" name="referUUID" value="${referUUID}" />
	            
		<div class="for-pas-text"><strong id="FgPas" onclick="forgotPassword();">Forgot Password</strong><i class="fa fa-question"></i></div>
<!-- 		<input type="button" value="Login" class="sign_btn" id="OrgVi" /> -->
		<input type="submit" value="Submit" class="sign_btn" />
		
		<c:if test="${!empty requirementUUID && empty referUUID}">
         <div class="siUpText">Don't have an account? &nbsp<a href="<c:url value='/register?requirementUUID=${requirementUUID}'/>">Sign up</a></div>
        </c:if>
        
        <c:if test="${!empty requirementUUID && !empty referUUID}">
         <div class="siUpText">Don't have an account? &nbsp<a href="<c:url value='/register?requirementUUID=${requirementUUID}&referUUID=${referUUID}'/>">Sign up</a></div>
        </c:if>
        
        <c:if test="${empty requirementUUID && empty referUUID}">
         <div class="siUpText">Don't have an account? &nbsp<a href="<c:url value='/register'/>">Sign up</a></div>
        </c:if>
		
		</form>
  </div> 




 <%-- <div class="regis_bg">
	<div class="regis_logo">
	  <img src="resources/images/SourceLead-logo.png" alt="SourceLead Logo" title="SourceLead Logo" class="img-responsive"/>
	</div>
    <div class="regis_wrap">
    
    <div class="success_pop" id="selector1" style="display: none;">
		<i class="fa fa-check-circle iConSize"></i><strong id="msgDiv"></strong>
	</div>
    
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
	    	<c:if test="${!empty email_notVerified_error}">
	         		<div class="error"><font color="red">Your Email is not verified.   <a class="pull-right" id="resend" onclick="resend();" title="Resend Verification Link">Resend</a></font></div>
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
	                <input type="text" name="username" id="usernameval" value="${username}" class="signin_fild" required />
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
  </div>   --%>
  
	<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-fun.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery.timepicker.js?staticTime=${staticTime}"></script>
    
</body>

<script>

/* $(".sign_btn").click(function(){
	$(".signWrap").addClass("orgOutWrap");
	$(".SignSlide").hide();
	$(".orgWrap").show();
}); */


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
function resend() {
	var sourceleadUrl ='${sourceleadUrl}';
	 var userName = document.getElementById("usernameval").value; 
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/ws/resendVerificationEmail?userName="+userName,        
       success :function(data) {
       if(data == "success"){
       		$("#selector1").removeAttr("style");
   			$("#selector1").css({ display: "block" });
   	     	$("#msgDiv").html("Verification link sent to your registered mail..");
       		msgFadein();
       	}
      },
  }); 
};

function msgFadein() {
	$("#selector1").fadeIn(200);
    $('#selector1').delay(2000).fadeOut('slow');
}
</script>

<script>
function forgotPassword(){
	$(".signWrap").removeClass("orgOutWrap");
	$(".signWrap").addClass("forPassOut");
	$("#signinDiv").removeAttr("style");
	$("#signinDiv").attr("style","display:none");
	$("#forgotPasswordDiv").removeAttr("style");
	$(".ForPasSlide").attr("style","display:block");
}
</script>

<script>
//   $('#selector').delay(2000).fadeOut('slow');
</script>


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





 

</html>
