<!-- <script src="/resources/js/jquery-1.11.1.min.js"></script> -->
<!-- <script src="/resources/js/jquery-ui.js"></script> -->
<!-- <script src="/resources/js/jquery-fun.js"></script> -->
<!-- <script src="/resources/js/jquery.timepicker.js"></script> -->


<div id="enrolledLocDiv" style="display:none">
   <jsp:include page="userEnrolledLocations.jsp"></jsp:include>
</div>

<script>
window.onload = function() {
	var userName = '${username}';
	var password = '${password}';
	var sourceleadUrl ='${sourceleadUrl}';
	userName = userName.trim();
// 	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	      type : "POST",
	       url: sourceleadUrl+"/ws/userEnrolledOrgLocations?userName="+userName,        
	       success :function(data) {
// 	    	   $("#loading").removeAttr('style');
	    	   $("#enrolledLocDiv").removeAttr("style");
	    	   $("#enrolledLocDiv").attr("style","display : block");
	    	   $("#enrolledLocDiv").empty();
	    	   $("#enrolledLocDiv").append(data);
	    	   $(".signWrap").addClass("orgOutWrap");
	    	   $(".SignSlide").hide();
	    	   $(".orgWrap").show();
// 	    	   $(".signWrap").addClass("orgOutWrap");
	      },
	  }); 
}
</script>

<script>
function getUserDetailsByUserRoleId(id,userName,password){
	var sourceleadUrl ='${sourceleadUrl}';
	$.ajax({
	      type : "POST",
	       url: sourceleadUrl+"/ws/getUserDetailsByRoleId?id="+id+"&userName="+userName,        
	       success :function(data) {
	    	   if(data != null){
	    		   $("#twoStepAuthDiv").removeAttr("style");
		    	   $("#twoStepAuthDiv").attr("style","display : block");
	    		   $("#twoStepAuthDiv").empty();
	    		   $("#twoStepAuthDiv").append(data);
	    		   $("#gobackBtn").attr("style","display:block");
	    		   $("#resendOTP").attr("style","display:none");
	    		   $("#sendOTP").attr("style","display:block");
	    	   }
	       },
	  }); 
}
</script>

<div id="twoStepAuthDiv" style="display:none">
   <jsp:include page="otpVerification.jsp"></jsp:include>
</div>


<script>
function goToLocations(){
	$("#enrolledLocDiv").removeAttr("style");
    $("#enrolledLocDiv").attr("style","display : block");
	$("#twoStepAuthDiv").removeAttr("style");
    $("#twoStepAuthDiv").attr("style","display : none");
//     $("#loading").removeAttr("style");
    $(".signWrap").addClass("orgOutWrap");
    $("div.raBtnActive").children().removeAttr("checked");
 	$(".orgCta").parent().find('div.raBtnActive').removeClass('raBtnActive');
}
</script>
