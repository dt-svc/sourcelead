<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="/resources/css/login.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="/resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet">

 
<script>
window.onload = function() {
	var userName = '${username}';
	var password = '${password}';
	var sourceleadUrl ='${sourceleadUrl}';
	var userRoleId = "";
	if("${redirect_to_login_2_Step_Process}" != ''){
		$("#directtwoStepAuthDiv").removeAttr("style");
 	    $("#directtwoStepAuthDiv").attr("style","display : block");
 	    $(".signWrap").addClass("orgOutWrap");
 	    getUserDetailsByUserRoleIdfromLogin(userRoleId,userName,password);
	}
}
</script>
 
 
 <div id="slideit" class="bgImg" style="background-image: url(resources/images/sign-inbg.jpg)"></div>
 
 <div class="signWrap">
 <div id="loading"></div>
 <c:if test="${!empty login_2_Step_Process_Enabled}">
  <jsp:include page="twoStepVerification.jsp"></jsp:include>
</c:if>

<div id="signinDiv">
<c:if test="${empty login_2_Step_Process_Enabled}">
    <jsp:include page="SignIn.jsp"></jsp:include>
</c:if>
</div>


<div id="forgotPasswordDiv" style="display:none">
<!--  Forgot Pass  -->		
<div class="signin_wrap ForPasSlide">
	<div class="backBtn">
		<a onclick="backToLogin();"><u class="fa fa-arrow-left OrBak"></u></a>
	</div>
		<h1>Forgot Password</h1>
		<form:form class="login-form clearfix"  id="forgetPassword_form"  name="forgetPassword_form" method="post" >
	            <div id="forgotPasswordDiv">
	            <span>
	                <i class="fa fa-user"></i>
	                <input type="text" name="email" placeholder="Mail id"  value="${email}" id="email" class="signin_fild" required />
	            </span>
	            </div>
	            <input type="submit" value="Submit" id="submit_button" class="sign_btn sign_btn_for" />
<%-- 	            <div class="cancel_for"><a href="<c:url value='/login'/>">Cancel</a></div>   --%>
            </form:form>     
  </div> 
<!--  Forgot Pass  -->	
</div>

<div id="twoStepAuthDiv" style="display:none">
   <jsp:include page="otpVerification.jsp"></jsp:include>
</div>

 </div>
 
 
 <script>
function getUserDetailsByUserRoleIdfromLogin(id,userName,password){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/ws/getUserDetailsByRoleId?id="+id+"&userName="+userName+"&password="+password,        
	       success :function(data) {
	    	   if(data != null){
	    		   $("#signinDiv").removeAttr("style");
		    	   $("#signinDiv").attr("style","display : none");
	    		   $("#twoStepAuthDiv").removeAttr("style");
		    	   $("#twoStepAuthDiv").attr("style","display : block");
	    		   $("#twoStepAuthDiv").empty();
	    		   $("#twoStepAuthDiv").append(data);
	    		   $("#resendOTP").attr("style","display:none");
	    		   $("#backAnch").prop("onclick",null);
	    		   $("#backAnch").attr("onclick","logout('"+sourceleadUrl+"');");
	    		   $("#sendOTP").attr("style","display:block");
	    	   }
	       },
	  }); 
}
</script>

<script>
function backToLogin(){
	$(".signWrap").removeClass("forPassOut");
	$("#signinDiv").removeAttr("style");
	$("#signinDiv").attr("style","display:block");
	$("#forgotPasswordDiv").attr("style","display:none");
	$(".ForPasSlide").attr("style","display:none");
}
function logout(url){
	location.href = "/logout";
}
</script>


