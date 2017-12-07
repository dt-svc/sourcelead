<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/jquery-ui.js"></script>
<script src="resources/js/jquery-fun.js"></script>
<script src="resources/js/jquery.timepicker.js"></script>



 <!-- orgWrap -->
<div class="orgWrap" style="display:block">
	
	<div class="orgBack">
		<a href="<c:url value='/logout'/>"><u class="fa fa-arrow-left OrBak"></u></a>
	</div>	
	<div class="orgInn">
	<c:forEach var="userRole" items="${roleList}">
	   <input type="hidden" id="isTwoStepEnabled${userRole.id}" name="isTwoStepEnabled${userRole.id}" value="${userRole.orgLocations.loginSecurity}"/>
		<div class="orgCta clearfix">
		<input type="hidden" value="${userRole.user.loginSecurity}" id="userLoginSecurity"/>
		<c:if test="${userRole.isPrimary == 1}">
			<div class="raBtn raBtnActive" id="locDiv${userRole.id}">
				   <input id="userRoleId${userRole.id}" name="userRoleId" type="radio" class="with-font" value="${userRole.id}" checked />
				 <label for="userRoleId" onclick="selectLoc(${userRole.id});"></label>
			</div>
		</c:if>
		<c:if test="${userRole.isPrimary != 1}">
			<div class="raBtn" id="locDiv${userRole.id}">
				   <input id="userRoleId${userRole.id}" name="userRoleId" type="radio" class="with-font" value="${userRole.id}"/>
				 <label for="userRoleId" onclick="selectLoc(${userRole.id});"></label>
			</div>
		</c:if>
					
			<span></span>
			<div class="orgtext">
				<b>${userRole.orgLocations.orgLocationName}</b>
				<p><i class="fa fa-map-marker"></i> ${!empty userRole.orgLocations.city ? userRole.orgLocations.city : userRole.orgLocations.country}</p>
				<p><i class="fa fa-user"></i> ${userRole.roles.displayName}</p>
				<p><i class="flag-image"><a><img src="/resources/images/${userRole.orgLocations.country}.gif" title="${userRole.orgLocations.country}"></a></i></p>
			</div>
	    </div>
	</c:forEach>
	</div>
	<div class="orgCon">
		<a href="javascript:validateTwoStepAuth();">Continue</a>
	</div>
</div>

<script>
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
	if(isTwoStepEnabled != null && isTwoStepEnabled != '' && isTwoStepEnabled != 'undefined' && isTwoStepEnabled  == 'N' && userLoginSecurity == 'N'){
		$.ajax({
		      type : "POST",
		       url: sourceleadUrl+"/verificationDone?userName="+userName+"&password="+password+"&userRoleId="+userRoleId,        
		       success :function(result) {
		    	 if(result == "true"){
		    		 location.replace(sourceleadUrl+"/dashboard?&userRoleId="+userRoleId);   
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
}

</script>

<!-- orgWrap  -->