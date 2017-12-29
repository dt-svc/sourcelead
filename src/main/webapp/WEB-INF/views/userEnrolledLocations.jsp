<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <script src="/resources/js/jquery-1.11.1.min.js"></script> -->
<!-- <script src="/resources/js/jquery-ui.js"></script> -->
<!-- <script src="/resources/js/jquery-fun.js"></script> -->
<!-- <script src="/resources/js/jquery.timepicker.js"></script> -->



 <!-- orgWrap -->
<div class="orgWrap" style="display:block">
	
	<div class="orgBack">
		<a href="<c:url value='/logout'/>"><u class="fa fa-arrow-left OrBak"></u></a>
	</div>	
	<div class="orgInn">
	<div id="whiteListerrDiv" class="orgErrorMes"></div>
	<c:forEach var="userRole" items="${roleList}">
	<a onclick="selectLoc(${userRole.id});">
	   <input type="hidden" id="isTwoStepEnabled${userRole.id}" name="isTwoStepEnabled${userRole.id}" value="${userRole.orgLocations.loginSecurity}"/>
		<div class="orgCta clearfix">
		<input type="hidden" value="${userRole.user.loginSecurity}" id="userLoginSecurity"/>
			<div class="raBtn" id="locDiv${userRole.id}">
				   <input id="userRoleId${userRole.id}" name="userRoleId" type="radio" class="with-font" value="${userRole.id}"/>
				 <label for="userRoleId"></label>
			</div>
					
			<span><img onerror="this.src='/resources/images/office-image.jpg'" src="${sourceleadUrl}/displayOrgImage/${userRole.orgLocations.org.orgCode}"></span>
			<div class="orgtext">
				<b>${userRole.orgLocations.orgLocationName}</b>
				<p><i class="fa fa-map-marker"></i> ${!empty userRole.orgLocations.city ? userRole.orgLocations.city : ""} ${userRole.orgLocations.country}</p>
				<p><i class="fa fa-user"></i> ${userRole.roles.displayName}</p>
<%-- 				<p><i class="flag-image"><a><img src="/resources/images/${userRole.orgLocations.country}.gif" title="${userRole.orgLocations.country}"></a></i></p> --%>
			</div>
	    </div>
	    </a>
	</c:forEach>
	</div>
<!-- 	<div class="orgCon"> -->
<!-- 		<a href="javascript:validateWhiteListIp();">Continue</a> -->
<!-- 	</div> -->
</div>

<script>
function validateWhiteListIp(){
	var userName = '${username}';
	var password = '${password}';
	var sourceleadUrl ='${sourceleadUrl}';
	var userRoleId = '';
	$(".with-font").each(function () {
	    if ( $(this).prop("checked") ) {
	    	userRoleId = $(this).val();
	    }
	});
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	      type : "GET",
	       url: sourceleadUrl+"/validateWhitelistIp?userRoleId="+userRoleId,        
	       success :function(result) {
	    	 if(result == true){
	    		 $('#whiteListerrDiv').find('p.jquery_form_error_message').remove();
	    		 validateTwoStepAuth();
	    	 }else{
	    		$('#whiteListerrDiv').find('p.jquery_form_error_message').remove();
	 			$("#whiteListerrDiv").append( "<p class='jquery_form_error_message'>You are not allowed to access this location</span>");
	 			$("#loading").removeAttr('style');
	    	 }
	      },
	  }); 
}

function validateTwoStepAuth() {
	var userName = '${username}';
	var password = '${password}';
	var sourceleadUrl ='${sourceleadUrl}';
	var userRoleId = '';
	$(".with-font").each(function () {
	    if ( $(this).prop("checked") ) {
	    	userRoleId = $(this).val();
	    }
	});
	var userLoginSecurity = $("#userLoginSecurity").val();
	var isTwoStepEnabled = $("#isTwoStepEnabled"+userRoleId).val();
	if((isTwoStepEnabled == null || isTwoStepEnabled == '' || isTwoStepEnabled == 'undefined' || isTwoStepEnabled  == 'N') && (userLoginSecurity == null || userLoginSecurity == '' || userLoginSecurity == 'undefined' || userLoginSecurity  == 'N')){
		$.ajax({
		      type : "POST",
		       url: sourceleadUrl+"/verificationDone?userName="+userName+"&password="+password+"&userRoleId="+userRoleId,        
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
 	    getUserDetailsByUserRoleId(userRoleId,userName,password);
	}
}


function selectLoc(id){
	$("div.raBtnActive").children().removeAttr("checked");
 	$(".orgCta").parent().find('div.raBtnActive').removeClass('raBtnActive');
 	$("#locDiv"+id).addClass("raBtnActive");
 	$("#userRoleId"+id).prop('checked', true);
 	validateWhiteListIp();
}

</script>

<!-- orgWrap  -->