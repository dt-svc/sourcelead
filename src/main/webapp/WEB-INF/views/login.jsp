<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1">
 
 <link rel="icon" type="/resources/image/png" href="/resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/resources/css/font-awesome.min.css?staticTime=${staticTime}">
    <link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="/resources/css/login.css?staticTime=${staticTime}" rel="stylesheet">
    <link href="/resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet">
     <script src="/resources/js/jquery-2.1.4.min.js"></script>
     
     <script src="/resources/js/sourcelead.common.js"></script> 

 
   <%
   
   
    if (request.getRequestURL().toString().indexOf("www.") == -1
            && request.getRequestURL().toString().indexOf("localhost") == -1
            && request.getRequestURL().toString().indexOf("qa.") == -1
            && request.getRequestURL().toString().indexOf("staging.") == -1
            && request.getRequestURL().toString().indexOf("dev.") == -1
            && request.getRequestURL().toString().indexOf("demo.") == -1) {
        response.sendRedirect("https://www.sourcelead.com/login");
    } %>
 
 <script>
$(document).ready(function(){
    if(window.location.href.indexOf("login") == -1) {
        window.location.href = '/login';
     }
});
</script>
<script>
window.onload = function() {
	var userName = '${username}';
	var password = '';
	var sourceleadUrl ='${sourceleadUrl}';
	var userRoleId = "";
	if("${redirect_to_login_2_Step_Process}" != ''){
		validateUserAlreadyAuthenticated(userRoleId,userName,password);
	}
}
</script>

<script>
$(document).ready(function(){
    if(window.location.href.indexOf("login") == -1) {
        window.location.href = '/login';
     }
});
</script>
<script>
$(document).ready(function(){
	var location = window.location.origin;
	 $.ajax({
        url: location+"/getScheduleStatus",
        success: function(data) {
        	if(data !== ""){
        		$("#maintenanceStatus").append(data.mainMessage);
        		 $(".marQ").css({ display: "block" });
        	}
        }
	}); 
});
</script>
 
 
 <div id="slideit" class="bgImg" style="background-image: url(/resources/images/login-psd.jpg)"></div>
 <div class="marQ">
        <marquee behavior="scroll" direction="left">
        <p id="maintenanceStatus"></p>
        </marquee>
    </div>
 <div class="signWrap">
<!--  <div id="loading"></div> -->
 <c:if test="${!empty login_2_Step_Process_Enabled}">
  <jsp:include page="twoStepVerification.jsp"></jsp:include>
</c:if>

<div id="signinDiv">
<c:if test="${empty login_2_Step_Process_Enabled}">
    <jsp:include page="SignIn.jsp"></jsp:include>
</c:if>
</div>


<div id="forgotPasswordDiv" style="display:none">
<jsp:include page="forgotPasswords.jsp"></jsp:include>
<!--  Forgot Pass  -->		
<%-- <div class="signin_wrap ForPasSlide">
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
	            <div class="cancel_for"><a href="<c:url value='/login'/>">Cancel</a></div>  
            </form:form>     
  </div>  --%>
<!--  Forgot Pass  -->	
</div>

<div id="directtwoStepAuthDiv" style="display:none">
   <jsp:include page="otpVerification.jsp"></jsp:include>
</div>

 </div>
 
 <script>
 
function validateUserAlreadyAuthenticated(userRoleId,userName,password){
	var userName = '${username}';
	var password = '';
	var sourceleadUrl ='${sourceleadUrl}';
	var userRoleId = 0;
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/ws/validateUserAlreadyAuthenticated?userRoleId="+userRoleId+"&name="+userName,        
	       success :function(result) {
	    	 if(result == true){
	    		 redirectToDashbord(userRoleId,userName,password);
	    	 }else{
	    		 directLoginValidateWhiteListIp(userRoleId,userName,password);
	    	 }
	      },
	  }); 
 }
 
function directLoginValidateWhiteListIp(userRoleId,userName,password){
	var userName = '${username}';
	var password = '';
	var sourceleadUrl ='${sourceleadUrl}';
	var userRoleId = 0;
// 		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/ws/validateWhitelistIp?userRoleId="+userRoleId+"&name="+userName,        
	       success :function(result) {
	    	 if(result == true){
	    		 $('#loginWhiteListDiv').find('p.jquery_form_error_message').remove();
	    		 $(".signWrap").addClass("orgOutWrap");
	    		 getUserDetailsByUserRoleIdfromLogin(userRoleId,userName,password);
	    	 }else{
	    		$('#loginWhiteListDiv').find('p.jquery_form_error_message').remove();
	 			$("#loginWhiteListDiv").append( "<p class='jquery_form_error_message'>You are not allowed to access this location</span>");
// 	 			$("#loading").removeAttr('style');
	    	 }
	      },
	  }); 
}

function redirectToDashbord(userRoleId,userName,password){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		      type : "POST",
		       url: sourceleadUrl+"/ws/verificationDone?userName="+userName+"&password="+password+"&userRoleId="+userRoleId+"&verfDone=Y",        
		       success :function(result) {
		    	 if(result == "true"){
		    		 location.replace(sourceleadUrl+"/dashboard");  
		    	 }
		      },
		  });
}
</script>
 
 <script>
function getUserDetailsByUserRoleIdfromLogin(id,userName,password){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	      type : "POST",
	       url: sourceleadUrl+"/ws/getUserDetailsByRoleId?id="+id+"&userName="+userName,        
	       success :function(data) {
	    	   if(data != null){
	    		   $("#signinDiv").removeAttr("style");
		    	   $("#signinDiv").attr("style","display : none");
	    		   $("#directtwoStepAuthDiv").removeAttr("style");
		    	   $("#directtwoStepAuthDiv").attr("style","display : block");
	    		   $("#directtwoStepAuthDiv").empty();
	    		   $("#directtwoStepAuthDiv").append(data);
	    		   $("#resendOTP").attr("style","display:none");
	    		   $("#backAnch").prop("onclick",null);
	    		   $("#fromType").val("login");
	    		   $("#backAnch").attr("onclick","logout('"+sourceleadUrl+"');");
	    		   $("#sendOTP").attr("style","display:block");
	    	   }
	       },
	  }); 
}
</script>

<script>
/* function backToLogin(){
	$(".signWrap").removeClass("forPassOut");
	$("#signinDiv").removeAttr("style");
	$("#signinDiv").attr("style","display:block");
	$("#forgotPasswordDiv").attr("style","display:none");
	$(".ForPasSlide").attr("style","display:none");
} */
function logout(url){
	location.href = "/logout";
}
</script>

<!-- <script type="text/javascript">
        $('#forgetPassword_form')
           .submit(function(e) {
        	    e.preventDefault(); 
        		var form = $('#forgetPassword_form').serializeFormJSON();
        		var sourceleadUrl ='${sourceleadUrl}';
        		$('#forgotPasswordDiv').find('span.jquery_form_error_message').remove();
//         		  $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); 
                $.ajax({
            	      type: "POST",
            	      data :JSON.stringify(form),
            	      contentType : 'application/json; charset=utf-8',
            	      dataType : 'json',
                  	  url: "app/ws/resetPasswordWithUserName?${_csrf.parameterName}=${_csrf.token}",
                    	success: function(json) {
                    		if(json.status == 'Invalid'){
	                   			 $('#forgotPasswordDiv').find('span.jquery_form_error_message').remove();
	             		         document.getElementById("email").setAttribute('class','error signin_fild');
	             		         $("input#email").after("<span class='jquery_form_error_message'> <font color='red'>Invalid User Name</font></span>");
	             				 document.getElementById("email").setAttribute('pass-exist','yes');
	             				 document.getElementById("email").setAttribute('pass-exist-errorMsg', ' Invalid User Name'); 
	             				 $("input#email").removeAttr("style");
// 	             				 $("#loading").removeAttr("style");
    			        	}
                    		else if(json.status == 'login'){
    			        		  location.replace("/login"); 
    			        	}
                    		else{
                    			  location.replace("/resetPassword"); 
    			        	}
			        },  
                });
            });
</script>


<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script> -->

<script type="text/javascript" src="https://cdn.ywxi.net/js/1.js" async></script>
