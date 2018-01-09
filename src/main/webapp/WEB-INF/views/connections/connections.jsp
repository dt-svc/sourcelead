
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 
 <script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>
<script type="text/javascript">
window.onload = function () {
	getConnectionsList('manual');
 };
</script>

<!-- Content Area -->
<div class="success_pop" id="selector4" style="display: none;">
	<i class="fa fa-check-circle"></i><span id="inviteMessage"></span>
</div>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Connections</h2>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Conections</div>
</section>


<div class="conneWrap">
<input type="hidden" id="noOrgsList" name="noOrgsList"/>
 <input type="hidden" name="orgOffset" id="orgOffset" value="0"/>
	<div class="conneWrapHead clearfix">
		<ul class="clearfix">
			<li class="current"><a href="#" onclick="getConnectionListOnClick()">Find</a></li>
			<li><a href="<c:url value='${appPath}/loadConnectedList'/>">Connected</a></li>
			<li><a href="${appPath}/loadSentConnections">Sent</a></li>
			<li><a href="<c:url value='${appPath}/loadReceivedConnections'/>">Received</a></li>
		</ul>
		<span><input class="immigraTabSerach" id="searchFindConnId" type="text"></span>
	</div>
	<div class="conneWrapInn" id="findConnectionListId"></div>
	<div class="clearfix"></div>
	<div id="loadMoreDiv"></div> 
</div>
<script>


$(document).scroll(function(e){
    // grab the scroll amount and the window height
    var scrollAmount = $(window).scrollTop();
    var documentHeight = $(document).height();
    offset = $("#orgOffset").val();
    var UsersCount = $("#totalUsersCount").val(); 
    // calculate the percentage the user has scrolled down the page
    if($(window).scrollTop() == $(document).height() - $(window).height()) {
    	var avilableCount = $("#orgOffset").val();
    	avilableCount = parseInt(avilableCount) + parseInt(6); 
    	$("#orgOffset").val(avilableCount);
    	//searchUser("scroll");
    	 getConnectionsList("scroll");
    }
    
});
</script>

<script type="text/javascript">

$("#searchFindConnId").keydown(function(e) {
	if(e.which == 13) {
		getConnectionsList('manual');
	}
});
	function getConnectionListOnClick(){
		getConnectionsList('manual')
		$('#findConnectionListId').empty();
	}
	var count = 0;
	var userName = null;
	function getConnectionsList(type){
		var sourceleadUrl = '${sourceleadUrl}';
		 var searchValue = $("#searchFindConnId").val();
		 if(type == "scroll"){
			offset = $("#orgOffset").val();
			//var searchValue =  document.getElementById("searchOrgElement").innerHTML
    	}
		if(type == "manual"){
			$('#findConnectionListId').empty();
    		$("#orgOffset").val(0);
   		    offset = 0;
    	} 
		//$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({ 
	    	type : "POST",
			url : sourceleadUrl+"/getUserListForConnections?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue+"&offset="+offset,
            success: function(json) {
	        var result = "";
	        if($("#searchFindConnId").val().length !== 0){
				$('#findConnectionListId').empty();
			}
	        if(json.length>0){
	        	count++;
	        }
	        if(json.length > 0)
	   		{
	    		 $('#loadMoreDiv').empty();
	 			for(var i=0; i<json.length; i++){
	 				var user = json[i];
	 				var state = null;
	 				if(user.userState == 'Request_sent'){
	 					state = '<a href="#" onclick="getWithdrawConnections(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink">Withdraw</a>';
	 				}else if(user.userState == 'Request_received'){
	 					state = '<a href="#" onclick="acceptConnection(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink">Accept</a><a href="#" onclick="ignoreConnection(\''+user.userCode+'\')" id="connection'+user.userCode+'" class="connlink">Ignore</a>';
	 				}else if(user.userState == 'connected'){
	 					state = '<a href="#" onclick="getDisconnectConnection(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink">Disconnect</a>';
	 				}else{
	 					state = '<a href="#" onclick="getConnection(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink">Connect</a>';
	 				}
	 				var mutCountStatus = null;
	 				if(user.mutualCount != 0){
	 					mutCountStatus = user.mutualCount + " Mutual Connections";
	 				}else{
	 					mutCountStatus = "No Mutual Connections";
	 				}
	 				var orgNameValue = null;
	 				if(user.orgName == ""){
	 					orgNameValue = "";
	 				}else{
	 					orgNameValue = "at " + user.orgName;
	 				}
	 				result = result + "<div class='connCta'><div class='connCtaInn'><div class='connCtaInnText'>";
	 				result = result + "<span><img data-id= 'userProfileImage' onerror='predefineProfileImage(this);' src='"+user.fileUrl+"' class='m-t-xs'   alt='image'>";
	 				result = result + "</span><a href='javascript:void(0)'><h2>"+user.userName+"</h2>";
	 				result = result + "<p>"+user.userRoleName+" <b> "+orgNameValue+" </b></p></a>";
	 				result = result + "<a href='javascript:void(0)' class='conUnder'><p>"+mutCountStatus+"<p></a>";
	 				result = result + '</div><div class="connCtaBtn" id="connectionState'+user.userCode+'">'+state+'</div></div></div>';
	   		    }
	 			if(type == "manual"){
            		$('#findConnectionListId').append(result);
            	}else{
            		$('#findConnectionListId').append(result);
            	}
	   		}else if(type == "scroll" && result === "" && count > 0){
            	$('#loadMoreDiv').empty();
	         	result = result + '<div class="loadWrap"> No More Records..</div>';
	         	$('#loadMoreDiv').append(result);
            }else if(type === 'manual' && result === "" && count == 0){
            	$("#loadMoreDiv").empty(); 
            	$('#findConnectionListId').hide();
	         	result = result + '<div class="noDate"></div>';
	         	$('#loadMoreDiv').append(result);
            }
			 },
	        error: function(e){
	        }
	    });  
	}
</script>	


<script>
function eduMsgFadeinFunction(message) {
	$("#selector4").fadeIn(200);
	$("#inviteMessage").text(message);
	message = "";
    $('#selector4').delay(200).fadeOut('slow');
}
function getConnection(userCode,userName){
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/addNewConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode,
            success: function(json) {
            	$("#selector4").removeAttr("style");
    			$("#selector4").css({ display: "block" });
    			$("#connectionState"+userCode).empty();
    			$("#connectionState"+userCode).append('<a href="#" onclick="getWithdrawConnections(\''+userCode+'\', \''+userName+'\')" id="connection'+userCode+'" class="connlink">Withdraw</a>')
            	/* getConnectionsList("manual"); */
            	eduMsgFadeinFunction("Invitation successfully sent to "+userName);
         },
	 });  
    }
</script>
<script>
function getWithdrawConnections(userCode,userName){
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode+"&statusCode=withdraw",
            success: function(json) {
            	$("#selector4").removeAttr("style");
    			$("#selector4").css({ display: "block" });
    			$("#connectionState"+userCode).empty();
            	/* getConnectionsList("manual"); */
            	$("#connectionState"+userCode).append('<a href="#" onclick="getConnection(\''+userCode+'\', \''+userName+'\')" id="connection'+userCode+'" class="connlink">Connect</a>');
            	eduMsgFadeinFunction(userName+"'\s invitation withdrawn ");
         },
	 });  
    }
</script>
<script>
function ignoreConnection(userCode,userName){
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode+"&statusCode=ignore",
         success: function(json) {
        	$("#selector4").removeAttr("style");
 			$("#selector4").css({ display: "block" });
         	/* getConnectionsList("manual"); */
         	$("#connectionState"+userCode).empty();
         	$("#connectionState"+userCode).append('<a href="#" onclick="getConnection(\''+userCode+'\', \''+userName+'\')" id="connection'+userCode+'" class="connlink">Connect</a>');
         	eduMsgFadeinFunction(userName+"'\s invitation removed");
      },
	 }); 
 }
 
function acceptConnection(userCode,userName){
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/acceptConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode,
        success: function(json) {
        	$("#selector4").removeAttr("style");
			$("#selector4").css({ display: "block" });
			$("#connectionState"+userCode).empty();
        	/* getConnectionsList("manual"); */
        	$("#connectionState"+userCode).append('<a href="#" onclick="getDisconnectConnection(\''+userCode+'\', \''+userName+'\')" id="connection'+userCode+'" class="connlink">Disconnect</a>');
        	eduMsgFadeinFunction("You're connected with "+userName);
     },
	 }); 
}
</script>
<script>
function getDisconnectConnection(userCode,userName){
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode+"&statusCode=connected",
            success: function(json) {
            	$("#selector4").removeAttr("style");
    			$("#selector4").css({ display: "block" });
    			$("#connectionState"+userCode).empty();
            	/* getConnectionsList("manual"); */
            	$("#connectionState"+userCode).append('<a href="#" onclick="getConnection(\''+userCode+'\', \''+userName+'\')" id="connection'+userCode+'" class="connlink">Connect</a>');
            	eduMsgFadeinFunction(userName+" removed from your connections");
         },
	 });  
    }
</script>
</section>
<jsp:include page="../masterFooter.jsp" />
 
