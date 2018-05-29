
<!DOCTYPE html>
 <jsp:include page="staticHeader.jsp" /> 
<html>
<head>
     <meta charset="utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
     <meta name="viewport" content="width=device-width, initial-scale=1">
    
     <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@page session="true"%>
    
    <c:if test="${staticTime == null || staticTime == 0 }">
       <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
     </c:if>
    <title>::. Verify Your Account .::</title>
    <link rel="icon" type="/resources/image/png" href="/resources/images/fav.png" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:400,300italic,300,400italic,500,700,900" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="/resources/css/font-awesome.min.css?staticTime=${staticTime}">
	<link rel="stylesheet" href="/resources/css/timesheet.css" type="text/css" >   
	<link href="/resources/css/jquery-ui.css" rel="stylesheet"> 
</head>

<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-fun.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery.timepicker.js?staticTime=${staticTime}"></script>

<script type="text/javascript">
window.onload = function () {
	var sourceleadUrl ='${sourceleadUrl}';
	var status = '${status}';
	 if(status == "success"){
		 $("#emailVerifiedDiv").removeAttr("style");
	 }else if(status == "already Verified" || status == "home"){
		 window.location.href = "/login";
	 }else if(status == "expired"){
		 $("#urlExpiredDiv").removeAttr("style");
	 }else{
		 $("#thanksForRegisterDiv").removeAttr("style");
	 }
 };
</script>

<body>


<div class="success_pop" id="selector1" style="display: none;">
	<i class="fa fa-check-circle iConSize"></i><strong id="msgDiv"></strong>
</div>

<section class="emailBg">
    <section class="emailVeri emailverification-contnet" id="thanksForRegisterDiv" style="display:none">
<!--         <span><img src="/resources/images/email-veri.png" alt=""/></span> -->
        <h2>Thank You! For Registering into Sourcelead.</h2>
		<p>You are almost done! We have emailed you a verification link on your registered email. Kindly click on the link.</p>
		<p>If you have not received the email, click on the link below to resend the verification mail.</p>
        <p><a onclick="resendVerf();">Resend Verification Mail</a></p>
    </section>
    
    <section class="emailVeri emailverification-contnet" id="urlExpiredDiv" style="display:none">
        <span><img src="/resources/images/email-veri.png" alt=""/></span>
        <h2>Sorry your verification Link has been expired</h2>
		<p>Your Verification link has expired,kindly click on the link below to resend the verification mail.</p>
        <p><a onclick="resendVerf();">Resend Verification Mail</a></p>
    </section>
    
    <section class="emailVeri emailverification-contnet" id="emailVerifiedDiv" style="display:none">
        <span><img src="/resources/images/email-veri.png" alt=""/></span>
        <h2>Congratulations !! </h2><p>Your Sourcelead account was successfully verified. You may start using your account.</p>
        <p>Click to <a onclick="redirect();">Login</a></p>
    </section>
</section>

</body>

<script>
function resendVerf(){
	var sourceleadUrl ='${sourceleadUrl}';
	var userId = '${userId}';
	if(userId == '')
		userId = '${SourceLeadWeb_profUserId}';
		
	 $.ajax({
       type : "GET",
        url: sourceleadUrl+"/ws/resendVerificationEmail?userId="+userId,        
        success :function(data) {
        	if(data == "success"){
        		$("#selector1").removeAttr("style");
    			$("#selector1").css({ display: "block" });
    	     	$("#msgDiv").html("Verification link sent to your registered mail..");
        		msgFadein();
        	}else{
        		$("#selector1").removeAttr("style");
    			$("#selector1").css({ display: "block" });
    			$("#selector1").removeClass("success_pop");
    			$("#selector1").addClass("remove_pop");
    	     	$("#msgDiv").html("Your email was verified already, please continue with login..");
    	     	$("#urlExpiredDiv").attr("style","display: none");
    	     	$("#alreadyVerifiedDiv").removeAttr("style");
        		msgFadein();
        	}
       },
   });
}

function redirect(){
	var sourceleadUrl ='${sourceleadUrl}';
	var token = '${token}';
	window.location.href = "/login?&token="+token
}
</script>

<script>
	function msgFadein() {
		$("#selector1").fadeIn(200);
        $('#selector1').delay(2000).fadeOut('slow');
	}
</script>

<jsp:include page="staticFooter.jsp" />
</html>
