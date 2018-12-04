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
 <script src="/resources/js/slead-common.js"></script> 
   <%
    if (request.getRequestURL().toString().indexOf("www.") == -1
            && request.getRequestURL().toString().indexOf("localhost") == -1
            && request.getRequestURL().toString().indexOf("qa.") == -1
            && request.getRequestURL().toString().indexOf("staging.") == -1
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
	else if("${login_2_Step_Process_Enabled}" != ''){
		getUserEnrolledLoctions();
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
 <style>
	.timer{
	    color: #1B232F;
	    font-family: Verdana, Arial, sans-serif;
	    font-size: 18px;
	    font-weight: bold;
	    text-decoration: none;
	}
</style>
 <div id="slideit" class="bgImg" style="background-image: url(/resources/images/login-psd.jpg)"></div>
 <div class="marQ">
        <marquee behavior="scroll" direction="left">
        <p id="maintenanceStatus"></p>
        </marquee>
    </div>
 <div class="signWrap">
 <c:if test="${!empty login_2_Step_Process_Enabled}">
     <div id="enrolledLocDiv" style="display:none">
        <!-- ################################## UserEnrolled Locations ############################################### -->
		<div class="orgWrap">
			<div class="orgBack">
				<a href="<c:url value='/logout'/>"><u class="fa fa-arrow-left OrBak"></u></a>
			</div>	
			<div class="orgInn">
			<div id="whiteListerrDiv" class="orgErrorMes"></div>
			    <div id="enrolledLoctionsDiv"></div>
			     
			</div>
		</div>
		<!-- ################################## UserEnrolled Locations ############################################### -->
    </div>
</c:if>

<div id="signinDiv">
<c:if test="${empty login_2_Step_Process_Enabled}">
            <!-- ################################## SignIn Page ############################################### -->
               <div class="signin_wrap SignSlide">
				<%   
				        String ip = request.getHeader("X-Forwarded-For");  
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
			</span>
			<span>
				<i class="fa fa-lock si-pass"></i>
				<input type="Password"  name="password"  value="${password}" placeholder="Password"  class="signin_fild" required />
				<b></b>
			</span>
			<input type="hidden" name="ipaddr" value="<%=ip%>" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	        <input type="hidden" name="UUID" value="${UUID}" />
	        <input type="hidden" name="spocUUID" value="${spocUUID}" />
	        <input type="hidden" id="requirementUUID" name="requirementUUID" value="${requirementUUID}" />
	        <input type="hidden" name="referUUID" value="${referUUID}" />
		   <div class="for-pas-text"><strong id="FgPas" onclick="forgotPassword();">Forgot Password</strong><i class="fa fa-question"></i></div>
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
     <!-- ################################## SignIn Page ###############################################-->
</c:if>
</div>


<div id="forgotPasswordDiv" style="display:none">
      <!--##################################  Forgot Pass  ##################################-->		
			<div class="signin_wrap ForPasSlide">
				<div class="backBtn">
					<a onclick="backToLogin();"><u class="fa fa-arrow-left OrBak"></u></a>
				</div>
					<h1>Forgot Password</h1>
					<form:form class="login-form clearfix"  id="forgetPassword_form"  name="forgetPassword_form" method="post" >
				            <div id="forgotPasswordEmailDiv">
				            <span>
				                <i class="fa fa-user"></i>
				                <input type="text" name="email" placeholder="Mail id"  value="${email}" id="email" class="signin_fild" required />
				            </span>
				            </div>
				            <input type="submit" value="Submit" id="submit_button" class="sign_btn sign_btn_for" />
			            </form:form>     
			  </div> 
        <!--##################################  Forgot Pass  ##################################-->	
</div>

<div id="directtwoStepAuthDiv" style="display:none">
			  <!--##################################  OTP Wrap  ##################################-->
			    <div class="otp_wrap" style="display:block">
			    <div id="otpLoading"></div>
			    	<div class="otpBackNew" style="display: none;"><a onclick="otpClose();" id="otpBackNewId" ><i class="fa fa-close"></i></a></div>
				    <div class="backBtn" id="gobackBtn">
						<u class="fa fa-arrow-left OtpBack" id="backAnch" onclick="goToLocations();"></u>
					</div>
					<img src="/resources/images/otp.png" alt=""/>
					<div class="otpMail">
						<span class="clearfix">
							<input type="hidden" name="userRoleId" id="otpuserRoleId" value="${userRoleId}">
							<input type="hidden" name="fromType" id="fromType" value="">
							<input type="hidden" name="auth_token" id="auth_token" value="">
							<input type="hidden" name="username" id="otpusername" value="${username}">
							<input type="hidden" name="password" id="otppassword" value="${password}">
							<input type="hidden" name="userId" id="otpuserId" value="${userId}">
							<input type="radio" checked name="radio" id="otpemail" value="${emailId}"><label for="email" id="emailLabel">${emailId}</label>
						</span>
						 <div class="mobileRadioDiv" style="display:none">
							<span class="clearfix" id="mobileSpan">
								<input type="radio" name="radio" id="otpmobile" value="${mobilePhone}"><label for="mobile" id="mobileLabel">${mobilePhone}</label>
							</span>
						</div>
						<span class="clearfix" style="display:none">
							<input type="radio" name="radio" id="both" ><label for="both" id="bothLabel">Both</label>
						</span>
						<span class="clearfix">
							<button class="resendBtn" id="sendOTP" onclick="sendOtp();">Send</button>
							<button class="resendBtn timer" id="resendOTP"  style="display:none"> </button>
						</span>									
					</div>
			   		<h2>Enter OTP</h2>
			   		<p id="otpText"></p>
			   		<input type="text" id="enteredOTP" class="otpfeild" placeholder="Enter OTP"/>
					<button id="verifyOTP" onclick="verifyOTP();">Verify</button>	
			   		<p class="text-danger" id="errorDiv" style="display:none"></p>
			   		
			    </div>               
			<!--##################################  OTP Wrap  ##################################-->
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
	       url: sourceleadUrl+"/ws/getUserDetailsByUsernameAndRoleId?id="+id+"&userName="+userName,        
	       success :function(data) {
	    	   if(data != null){
	    		   $("#signinDiv").removeAttr("style");
		    	   $("#signinDiv").attr("style","display : none");
	    		   $("#directtwoStepAuthDiv").removeAttr("style");
		    	   $("#directtwoStepAuthDiv").attr("style","display : block");
		    	   
		    	   $("#otpuserRoleId").empty();
		    	   $("#otpusername").empty();
		    	   $("#otpuserId").empty();
		    	   $("#otppassword").empty();
		    	   $("#otpemail").empty();
		    	   $("#emailLabel").empty();
		    	   $("#auth_token").empty();
		    	   
		    	   
		    	   
		    	   $("#otpuserRoleId").val(data.userRoleId);
		    	   $("#otpusername").val(data.username);
		    	   $("#otpuserId").val(data.userId);
		    	   $("#otppassword").val(data.password);
		    	   $("#otpemail").val(data.emailId);
		    	   $("#emailLabel").html(data.emailId);
		    	   $("#auth_token").val(data.authToken);
		    	   if(data.mobilePhone != null && data.mobilePhone != '' && data.mobilePhone != 'undefined'){
		    		   $("#mobileRadioDiv").removeAttr("style");
			    	   $("#mobileRadioDiv").attr("style","display : block");
			    	   $("#otpmobile").empty();
			    	   $("#mobileLabel").empty();
			    	   
			    	   $("#otpmobile").val(data.mobilePhone);
			    	   $("#mobileLabel").html(data.mobilePhone);
		    	   }
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
function logout(url){
	location.href = "/logout";
}
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

<!--########################### Forgot Password ################################# -->
<script type="text/javascript">
        $('#forgetPassword_form')
           .submit(function(e) {
        	   e.preventDefault(); 
        	  var formData = getFormData("forgetPassword_form");
        		var email = $("#email").val();
        		var sourceleadUrl ='${sourceleadUrl}';
        		$('#forgotPasswordEmailDiv').find('span.jquery_form_error_message').remove();
                $.ajax({
            	      type: "POST",
            	      data :formData,
            	      contentType : 'application/json; charset=utf-8',
            	      dataType : 'json',
                  	  url: "app/ws/resetPasswordWithUserName?${_csrf.parameterName}=${_csrf.token}&email="+email,
                    	success: function(json) {
                    		if(json.status == 'Invalid'){
	                   			 $('#forgotPasswordEmailDiv').find('span.jquery_form_error_message').remove();
	             		         document.getElementById("email").setAttribute('class','error signin_fild');
	             		         $("input#email").after("<span class='jquery_form_error_message'> <font color='red'>Invalid User Name</font></span>");
	             				 document.getElementById("email").setAttribute('pass-exist','yes');
	             				 document.getElementById("email").setAttribute('pass-exist-errorMsg', ' Invalid User Name'); 
	             				 $("input#email").removeAttr("style");
    			        	}else if(json.status == 'Inactive'){
    			        		 $('#forgotPasswordEmailDiv').find('span.jquery_form_error_message').remove();
	             		         document.getElementById("email").setAttribute('class','error signin_fild');
	             		         $("input#email").after("<span class='jquery_form_error_message'> <font color='red'>User is Inactive</font></span>");
	             				 document.getElementById("email").setAttribute('pass-exist','yes');
	             				 document.getElementById("email").setAttribute('pass-exist-errorMsg', 'User is Inactive'); 
	             				 $("input#email").removeAttr("style");
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

<script>
function backToLogin(){
	$(".signWrap").removeClass("forPassOut");
	$("#signinDiv").removeAttr("style");
	$("#signinDiv").attr("style","display:block");
	$("#forgotPasswordDiv").attr("style","display:none");
	$(".ForPasSlide").attr("style","display:none");
}
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
</script>
<!--########################## Forgot Password ######################-->
<!--########################## Otp Verification #################### -->
<script>
function sendOtp() {
	var userId = $("#otpuserId").val();
	var userRoleId = $("#otpuserRoleId").val();
	var username = $("#otpusername").val();
	var password = $("#otppassword").val();
	var email = '${emailId}';
	var fromType = $("#fromType").val();
	if(email != null && email != '' && email != 'undefined')
		email = $("#otpemail").val();
	var mobile = '${mobilePhone}';
	if(mobile != null && mobile != '' && mobile != 'undefined')
		mobile = $("#otpmobile").val();
	$("#resendOTP").removeAttr("onclick");
	var sourceleadUrl ='${sourceleadUrl}';
	var type = "email";
	var backSwitchType = $("#otpBackNewId").val();
	$("#otpLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/ws/sendOtpToUser?id="+userId+"&type="+type,        
	       success :function(data) {
	    	   var status = data.status;
	    	   if(data != null){
	    		   $("#otpuserRoleId").empty();
		    	   $("#otpusername").empty();
		    	   $("#otpuserId").empty();
		    	   $("#otppassword").empty();
		    	   $("#otpemail").empty();
		    	   $("#emailLabel").empty();
		    	   $("#auth_token").empty();
		    	   $("#otpuserRoleId").val(userRoleId);
		    	   $("#otpusername").val(username);
		    	   $("#otpuserId").val(userId);
		    	   $("#otppassword").val(password);
		    	   $("#otpemail").val(data.emailId);
		    	   $("#emailLabel").html(data.emailId);
		    	   $("#auth_token").val(data.authToken);
		    	   if(data.mobilePhone != null && data.mobilePhone != '' && data.mobilePhone != 'undefined'){
		    		   $("#mobileRadioDiv").removeAttr("style");
			    	   $("#mobileRadioDiv").attr("style","display : block");
			    	   $("#otpmobile").empty();
			    	   $("#mobileLabel").empty();
			    	   
			    	   $("#otpmobile").val(data.mobilePhone);
			    	   $("#mobileLabel").html(data.mobilePhone);
		    	   }
	    			countdown( "resendOTP", 0, 30 );
	    			if(fromType != null && fromType != '' && fromType != 'undefined' && fromType == 'login'){
    					$("#backAnch").prop("onclick",null);
	 	    		   $("#fromType").val("login");
	 	    		   $("#backAnch").attr("onclick","logout('"+data.sourceleadUrl+"');");
	    			}else if(backSwitchType == "switchOtp"){
	    				$("#gobackBtn").hide();
	 	    		    $(".otpBackNew").show();
	    			}
	    		   	$("#sendOTP").attr("style","display:none");
	    		   	$("#resendOTP").attr("style","display:block");
	    		   	$("#otpText").html("OTP has been sent to your registered "+type);
	    		   	$("#otpLoading").removeAttr("style"); 
	    	   }
	       },
	  }); 
}

function verifyOTP(){
	var enteredOTP = $("#enteredOTP").val();
	var userRoleId = $("#otpuserRoleId").val();
	var username = $("#otpusername").val();
	var password = $("#otppassword").val();
	var sourceleadUrl ='${sourceleadUrl}';
	var authToken = $("#auth_token").val();
	var type = "email";
	
	if((enteredOTP != null && enteredOTP != '' && enteredOTP !='undefined') && (enteredOTP == authToken)){
		$("#errorDiv").removeAttr("style");
		$("#errorDiv").attr("style","display:none");
		$.ajax({
		      type : "POST",
		       url: sourceleadUrl+"/ws/verificationDone?userName="+username+"&password="+password+"&userRoleId="+userRoleId+"&verfDone=Y",        
		       success :function(result) {
		    	 if(result == "true"){
		    		 location.replace(sourceleadUrl+"/dashboard");  
		    	 }
		      },
		  });
	}else{
		$("#errorDiv").removeAttr("style");
		$("#errorDiv").attr("style","display:block");
		$("#errorDiv").html("Incorrect OTP");
	}
	
}
</script>

<script>
function countdown( elementName, minutes, seconds )
{
    var element, endTime, hours, mins, msLeft, time;

    function twoDigits( n )
    {
        return (n <= 9 ? "0" + n : n);
    }

    function updateTimer()
    {
        msLeft = endTime - (+new Date);
        if ( msLeft < 1000 ) {
        	$("#resendOTP").removeAttr("onclick");
        	$("#resendOTP").attr("onclick","sendOtp()");
            element.innerHTML = "Resend";
        } else {
            time = new Date( msLeft );
            hours = time.getUTCHours();
            mins = time.getUTCMinutes();
            element.innerHTML =  twoDigits( mins ) + ':' + twoDigits( time.getUTCSeconds() );
            setTimeout( updateTimer, time.getUTCMilliseconds() + 500 );
        }
    }

    element = document.getElementById( elementName );
    endTime = (+new Date) + 1000 * (60*minutes + seconds) + 500;
    updateTimer();
}
</script>
<!--########################## Otp Verification #################### -->
<!-- ######################## UserEnrolled Locations ############### -->
<script>
function getUserEnrolledLoctions() {
	var userName = '${username}';
	var password = '${password}';
	var sourceleadUrl ='${sourceleadUrl}';
	var requirementUUID ='${requirementUUID}';
	userName = userName.trim();
	$.ajax({
	      type : "POST",
	       url: sourceleadUrl+"/ws/userEnrolledOrgLocationsFromLogin?userName="+userName+"&password="+password+"&requirementUUID="+requirementUUID,        
	       success :function(result) {
	    	   $("#enrolledLocDiv").removeAttr("style");
	    	   $("#enrolledLocDiv").attr("style","display : block");
	    	   var output="";
	    	   if(result != null && result.userSpocRolesList != null){
	    		    output = output+"<input type='hidden'  id='alreadyVerifiedBefore' value='"+result.alreadyVerifiedBefore+"'/>";
	   	     	    output = output+"<input type='hidden'  id='requirementUUID' value='"+result.requirementUUID+"'/>";
	    		   for (var i=0; i<result.userSpocRolesList.length; i++)
	   				{  
		   	     	    var report = result.userSpocRolesList[i];
		   	     	    output = output+"<a onclick='selectLoc("+report.userRoleId+","+report.orgLocationsId+","+report.userId+");'>";
		   	     	    output = output+"<input type='hidden' id='isTwoStepEnabled"+report.userRoleId+"' name='isTwoStepEnabled"+report.userRoleId+"' value='"+report.orgLocationLoginSecurity+"'/>";
			   	     	    output = output+"<div class='orgCta clearfix'>";
					   	     	    output = output+"<input type='hidden'  id='userLoginSecurity"+report.userRoleId+"' value='"+report.userLoginSecurity+"'/>";
					   	     	    
					   	     	    output = output+"<div class='raBtn' id='locDiv"+report.userRoleId+"'>";
					   	     	    output = output+"<input id='userRoleId"+report.userRoleId+"' name='userRoleId' type='radio' class='with-font' value='"+report.userRoleId+"'/>";
					   	     	    output = output+"<label for='userRoleId'></label>";
					   	    output = output+"</div>";
					   	    output = output+"<span><img onerror=\"this.src='/resources/images/office-image.jpg'\" data-id='orgImage' src='"+report.orgFileUrl+"'></span>";
					   	    output = output+"<div class='orgtext'>";
					   	         output = output+"<b>"+report.orgLocationName+"</b>";
					   	         output = output+"<p><i class='fa fa-map-marker'></i> "+report.orgLocation+"</p>";
					   	         output = output+"<p><i class='fa fa-user'></i> "+report.designation+"</p>";
					   	   output = output+"</div>";
					   	 output = output+"</div>";
					  output = output+"</a>";
	   				}
	    	   }
	    	   $("#enrolledLoctionsDiv").empty();
	    	   $("#enrolledLoctionsDiv").append(output);
	    	   $(".signWrap").addClass("orgOutWrap");
	    	   $(".SignSlide").hide();
	    	   $(".orgWrap").show();
	      },
	  }); 
}
</script>
<script>
function selectLoc(id,orgLocationId,userId){
	$("div.raBtnActive").children().removeAttr("checked");
 	$(".orgCta").parent().find('div.raBtnActive').removeClass('raBtnActive');
 	$("#locDiv"+id).addClass("raBtnActive");
 	$("#userRoleId"+id).prop('checked', true);
 	validateWhiteListIp(id);
 	requirementSubmissionsCount("today",userId,orgLocationId);
}
</script>
<script>
function validateWhiteListIp(userRoleId){
	var userName = '${username}';
	var password = '${password}';
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/ws/validateWhitelistIp?userRoleId="+userRoleId,        
	       success :function(result) {
	    	 if(result == true){
	    		 $('#whiteListerrDiv').find('p.jquery_form_error_message').remove();
	    		 validateTwoStepAuth(userRoleId);
	    	 }else{
	    		$('#whiteListerrDiv').find('p.jquery_form_error_message').remove();
	 			$("#whiteListerrDiv").append( "<p class='jquery_form_error_message'>You are not allowed to access this location</span>");
	    	 }
	      },
	  }); 
}
</script>
<script>
function validateTwoStepAuth(userRoleId) {
	var userName = '${username}';
	var password = '${password}';
	var sourceleadUrl ='${sourceleadUrl}';
	var requirementUUID =$("#requirementUUID").val();
	var userLoginSecurity = $("#userLoginSecurity"+userRoleId).val();
	var isTwoStepEnabled = $("#isTwoStepEnabled"+userRoleId).val();
	var alreadyVerifiedBefore = $("#alreadyVerifiedBefore").val();
	 if(alreadyVerifiedBefore == 'true'){
		$.ajax({
		      type : "POST",
		       url: sourceleadUrl+"/ws/verificationDone?userName="+userName+"&password="+password+"&userRoleId="+userRoleId+"&verfDone=Y",        
		       success :function(result) {
		    	 if(result == "true" && requirementUUID == ''){
		    		 location.replace(sourceleadUrl+"/dashboard");   
		    	 }
		    	 else{
		    		 location.replace(sourceleadUrl+"/dashboard"+"?requirementUUID="+requirementUUID);
		    	 }
		      },
		  });
	}else{
		if((isTwoStepEnabled == null || isTwoStepEnabled == '' || isTwoStepEnabled == 'undefined' || isTwoStepEnabled  == 'N') && (userLoginSecurity == null || userLoginSecurity == '' || userLoginSecurity == 'undefined' || userLoginSecurity  == 'N')){
			$.ajax({
			      type : "POST",
			       url: sourceleadUrl+"/ws/verificationDone?userName="+userName+"&userRoleId="+userRoleId,        
			       success :function(result) {
			    	 if(result == "true"){
			    		 location.replace(sourceleadUrl+"/dashboard");   
			    	 }
			      },
			  }); 
		}else{ 
			$("#enrolledLocDiv").removeAttr("style");
	   	    $("#enrolledLocDiv").attr("style","display : none");
			$("#twoStepVerfDiv").removeAttr("style");
	 	    $("#twoStepVerfDiv").attr("style","display : block");
	 	    $(".signWrap").addClass("orgOutWrap");
	 	   getUserDetailsByUserRoleIdfromLogin(userRoleId,userName,password);
		}
	}
}
</script>
<script type="text/javascript">
function requirementSubmissionsCount(daterange,userId,orgLocationId){
	$("#dateActiveTab").val(daterange);
	$("#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:5; background-image:url("/resources/images/Spinner.gif"); background-position:center; background-repeat:no-repeat; background-size:60px;');
    var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getRequirementSubmissionsCount?${_csrf.parameterName}=${_csrf.token}&daterange="+daterange+"&userId="+userId+"&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			        	var json = data.object;
			        	$("#applicantsCountLoading, #submissionsCountLoading, #interviewsCountLoading, #offersCountLoading").removeAttr("style");
			        	 var  submissionsCount = json.submissionCount;
			        	 var  applicantsCount =json.applicantsCount;
			        	 var  interviewsCount =json.interviewsCount;
			        	 var  offersCount = json.offersCount; 
			        	  sessionStorage.clear();
			        	  var jobCounts = [];
			        	  jobCounts.push(submissionsCount);
			        	  jobCounts.push(applicantsCount);
			        	  jobCounts.push(interviewsCount);
			        	  jobCounts.push(offersCount);
			        	  sessionStorage.setItem('jobsCount',jobCounts);
			        	  
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
		 		}
		  });
		 
	}
</script>
<!-- ######################## UserEnrolled Locations ############### -->
<script type="text/javascript" src="https://cdn.ywxi.net/js/1.js" async></script>
