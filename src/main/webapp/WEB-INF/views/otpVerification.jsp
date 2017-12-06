<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test='${empty SourceLeadWeb_sourceleadUrl}'>
<script src="/resources/js/jquery-1.11.1.min.js"></script>
<script src="/resources/js/jquery-ui.js"></script>
<script src="/resources/js/jquery-fun.js"></script>
<script src="/resources/js/jquery.timepicker.js"></script>
</c:if>

<style>

.timer{
    color: #1B232F;
    font-family: Verdana, Arial, sans-serif;
    font-size: 18px;
    font-weight: bold;
    text-decoration: none;
}


</style>
<!--  OTP Wrap  -->
    <div class="otp_wrap" style="display:block">
    
<%--     <c:if test='${empty SourceLeadWeb_sourceleadUrl}'> --%>
	    <div class="backBtn" id="gobackBtn">
			<a id="backAnch" onclick="goToLocations();"><u class="fa fa-arrow-left OtpBack"></u></a>
		</div>
<%--     </c:if> --%>
             
		<img src="/resources/images/otp.png" alt=""/>
		<div class="otpMail">
			<span class="clearfix">
				<input type="hidden" name="userRoleId" id="userRoleId" value="${userRoleId}">
				<input type="hidden" name="username" id="username" value="${username}">
				<input type="hidden" name="password" id="password" value="${password}">
				<input type="hidden" name="userId" id="userId" value="${userId}">
				<input type="radio" checked name="radio" id="email" value="${emailId}"><label for="email" id="emailLabel">${emailId}</label>
			</span>
			<c:if test="${!empty mobilePhone}">
				<span class="clearfix" id="mobileSpan">
					<input type="radio" name="radio" id="mobile" value="${mobilePhone}"><label for="mobile" id="mobileLabel">${mobilePhone}</label>
				</span>
			</c:if>
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
<!--  OTP Wrap  -->

<script>
function sendOtp() {
	var userId = $("#userId").val();
	var userRoleId = $("#userRoleId").val();
	var username = $("#username").val();
	var password = $("#password").val();
	var email = '${emailId}';
	if(email != null && email != '' && email != 'undefined')
		email = $("#email").val();
	var mobile = '${mobilePhone}';
	if(mobile != null && mobile != '' && mobile != 'undefined')
		mobile = $("#mobile").val();
	$("#resendOTP").removeAttr("onclick");
	var sourceleadUrl ='${sourceleadUrl}';
	var type = "email";
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/sendOtpToUser?id="+userId+"&type="+type,        
	       success :function(data) {
	    	   if(data != null){
	    		    $("#twoStepAuthDiv").empty();
	    		    $("#twoStepAuthDiv").append(data);
	    		   	$("#userId").val(userId);
	    			$("#userRoleId").val(userRoleId);
	    			$("#username").val(username);
	    			$("#password").val(password);
	    			$("#emailLabel").html(email);
	    			$("#mobileLabel").html(mobile);
	    			$("#email").val(email);
	    			$("#mobile").val(mobile);
	    		   	$("#sendOTP").attr("style","display:none");
	    		   	$("#resendOTP").attr("style","display:block");
	    		   	$("#otpText").html("OTP has been sent to your registered "+type);
	    		   	countdown( "resendOTP", 0, 30 );
	    	   }
	       },
	  }); 
}

function verifyOTP(){
	var enteredOTP = $("#enteredOTP").val();
	var userRoleId = $("#userRoleId").val();
	var username = $("#username").val();
	var password = $("#password").val();
	var sourceleadUrl ='${sourceleadUrl}';
	var type = "email";
	if(enteredOTP == '${authToken}'){
		$("#errorDiv").removeAttr("style");
		$("#errorDiv").attr("style","display:none");
		$.ajax({
		      type : "POST",
		       url: sourceleadUrl+"/verificationDone?userName="+username+"&password="+password+"&userRoleId="+userRoleId+"&verfDone=Y",        
		       success :function(result) {
		    	 if(result == "true"){
		    		 location.replace(sourceleadUrl+"/dashboard?&userRoleId="+userRoleId);   
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
