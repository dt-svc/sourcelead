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
    <div id="otpLoading"></div>
    	<div class="otpBackNew" style="display: none;"><a onclick="otpClose();" id="otpBackNewId" ><i class="fa fa-close"></i></a></div>
<%--     <c:if test='${empty SourceLeadWeb_sourceleadUrl}'> --%>
	    <div class="backBtn" id="gobackBtn">
			<u class="fa fa-arrow-left OtpBack" id="backAnch" onclick="goToLocations();"></u>
		</div>
<%--     </c:if> --%>
             
		<img src="/resources/images/otp.png" alt=""/>
		<div class="otpMail">
			<span class="clearfix">
				<input type="hidden" name="userRoleId" id="otpuserRoleId" value="${userRoleId}">
				<input type="hidden" name="fromType" id="fromType" value="">
				<input type="hidden" name="username" id="otpusername" value="${username}">
				<input type="hidden" name="password" id="otppassword" value="${password}">
				<input type="hidden" name="userId" id="otpuserId" value="${userId}">
				<input type="radio" checked name="radio" id="otpemail" value="${emailId}"><label for="email" id="emailLabel">${emailId}</label>
			</span>
			<c:if test="${!empty mobilePhone}">
				<span class="clearfix" id="mobileSpan" style="display:none">
					<input type="radio" name="radio" id="otpmobile" value="${mobilePhone}"><label for="mobile" id="mobileLabel">${mobilePhone}</label>
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
	    	   var status = '${status}';
	    	   if(data != null){
	    		    /* $("#twoStepAuthDiv").empty();
	    		    $("#twoStepAuthDiv").append(data); */
	    		    $("#directtwoStepAuthDiv").empty();
	    		    $("#directtwoStepAuthDiv").append(data);
	    		   	$("#otpuserId").val(userId);
	    			$("#otpuserRoleId").val(userRoleId);
	    			$("#otpusername").val(username);
	    			$("#otppassword").val(password);
	    			$("#emailLabel").html(email);
	    			$("#mobileLabel").html(mobile);
	    			$("#otpemail").val(email);
	    			$("#otpmobile").val(mobile);
	    			countdown( "resendOTP", 0, 30 );
	    			if(fromType != null && fromType != '' && fromType != 'undefined' && fromType == 'login'){
    					$("#backAnch").prop("onclick",null);
	 	    		   $("#fromType").val("login");
	 	    		   $("#backAnch").attr("onclick","logout('"+sourceleadUrl+"');");
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
	var type = "email";
	
	if((enteredOTP != null && enteredOTP != '' && enteredOTP !='undefined') && (enteredOTP == '${authToken}')){
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
