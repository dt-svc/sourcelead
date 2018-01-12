
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
			<li><a href="<c:url value='${appPath}/loadConnectedList'/>">Connected(<i id="connectedCountVal"></i>)</a></li>
			<li><a href="${appPath}/loadSentConnections">Sent(<i id="sentCountVal"></i>)</a></li>
			<li><a href="<c:url value='${appPath}/loadReceivedConnections'/>">Received(<i id="receivedCountVal"></i>)</a></li>
		</ul>
		<span><input class="immigraTabSerach" id="searchFindConnId" placeholder="Name/Email/Role/Company" type="text"></span>
	</div>
	<div class="conHeadgap"></div>
	
	<h5 class="connHeadText">People You May Know</h5>
	<div class="conneWrapInn clearfix" id="mutualConnList"></div>
	
	<div class="conneWrapInn clearfix" id="findConnectionListId">
	</div>
	<div id="loadMoreDiv"></div> 
</div>
<script>


$(document).scroll(function(e){
    // grab the scroll amount and the window height
    var scrollAmount = $(window).scrollTop();
    var documentHeight = $(document).height();
    offset = $("#orgOffset").val();
    // calculate the percentage the user has scrolled down the page
    if($(window).scrollTop() == $(document).height() - $(window).height()) {
    	var avilableCount = $("#orgOffset").val();
    	avilableCount = parseInt(avilableCount) + parseInt(18); 
    	$("#orgOffset").val(avilableCount);
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
	}
	var count = 0;
	var userName = null;
	
	function getConnectionsList(type){
		$.post("check-session", {}, function(data) {
			if(data === "YES") {
		var sourceleadUrl = '${sourceleadUrl}';
		 var searchValue = $("#searchFindConnId").val();
		 if(type === "scroll"){
			offset = $("#orgOffset").val();
    	}
		if(type === "manual"){
			$("#mutualConnList").empty();
			$("#findConnectionListId").empty();
    		$("#orgOffset").val(0);
   		    offset = 0;
    	} 
		 $.ajax({ 
	    	type : "POST",
			url : sourceleadUrl+"/getUserListForConnections?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue+"&offset="+offset,
            success: function(json) {
	        var result = "";
	        var mutualResult = "";
	        var sentCount = 0;
	        var receivedCount = 0;
	        var connectedCount = 0;
	        if(json !=null && json.length>0){
	        	count++;
	        }
	        if(json !=null && json.length > 0)
	   		{
	    		 $('#loadMoreDiv').empty();
	 			for(var i=0; i<json.length; i++){
	 				var user = json[i];
	 				var state = null;
	 				if(user.userState == 'Request_sent'){
	 					state = '<a href="javascript:void(0)" onclick="getWithdrawConnections(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink orange">Withdraw</a>';
	 					sentCount++;
	 				}else if(user.userState == 'Request_received'){
	 					receivedCount++;
	 					state = '<a href="javascript:void(0)" onclick="acceptConnection(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink green">Accept</a><a href="javascript:void(0)" onclick="ignoreConnection(\''+user.userCode+'\')" id="connection'+user.userCode+'" class="connlink orange">Ignore</a>';
	 				}else if(user.userState == 'connected'){
	 					connectedCount++;
	 					continue;
	 					/* state = '<a href="javascript:void(0)" onclick="getDisconnectConnection(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink red">Disconnect</a>'; */
	 				}else{
	 					state = '<a href="javascript:void(0)" onclick="getConnection(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink">Connect</a>';
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
	 				if(user.mutualCount != 0){
	 					mutualResult = mutualResult + result;
	 					result = "";
	 				}
	   		    }
	 			if(mutualResult !== ""){
 					$("#mutualConnList").append(mutualResult);
 				}else{
 					$("#mutualConnList").hide();
 				}
	 			if(result !== ""){
        			$("#findConnectionListId").append(result);
	 			}
	 			if(json.length < 18){
	 				$('#loadMoreDiv').append('<div class="loadWrap"> No More Records..</div>');
	 			}else if(json.length >= 18){
	 				$('#loadMoreDiv').append('<div class="loadWrap"><i class="fa fa-spinner fa-spin"></i> More Records..</div>');
	 			}
	   		}else if(type === 'manual' && json.length == 0){
	   			$('.connHeadText').empty();
	   			$('#loadMoreDiv').append('<div class="noDate"></div>');
	   		}
	        localStorage.setItem("sentCount", sentCount);
	        localStorage.setItem("receivedCount", receivedCount);
	        localStorage.setItem("connectedCount", connectedCount);
	        $("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount"));
			 },
	        error: function(e){
	        }
	    });  
	}else {
		location.href='/login';
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
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/addNewConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode,
            success: function(json) {
	            	$("#selector4").removeAttr("style");
	    			$("#selector4").css({ display: "block" });
	    			var sentCount = parseInt(localStorage.getItem("sentCount"));
	    			sentCount = sentCount+1;
	    			localStorage.setItem("sentCount", sentCount);
	    			$("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
	    	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
	    	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount"));
	    			$("#connectionState"+userCode).empty();
	    			$("#connectionState"+userCode).append('<a href="javascript:void(0)" onclick="getWithdrawConnections(\''+userCode+'\', \''+userName+'\')" id="connection'+userCode+'" class="connlink orange">Withdraw</a>');
	            	eduMsgFadeinFunction("Invitation successfully sent to "+userName);
            },
         error: function(e){
	        }
	 });  
    }else {
		location.href='/login';
	}
	});
}
</script>
<script>
function getWithdrawConnections(userCode,userName){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode+"&statusCode=withdraw",
            success: function(json) {
	            	$("#selector4").removeAttr("style");
	    			$("#selector4").css({ display: "block" });
	    			var sentCount =  parseInt(localStorage.getItem("sentCount"));
	    			sentCount = sentCount-1;
	    			localStorage.setItem("sentCount", sentCount);
	    			$("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
	    	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
	    	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount"));
	    			$("#connectionState"+userCode).empty();
	            	$("#connectionState"+userCode).append('<a href="javascript:void(0)" onclick="getConnection(\''+userCode+'\', \''+userName+'\')" id="connection'+userCode+'" class="connlink">Connect</a>');
	            	eduMsgFadeinFunction(userName+"'\s invitation withdrawn ");
         },
         error: function(e){
	        }
	 });  
    }else {
		location.href='/login';
	}
	});
	}
</script>
<script>
function ignoreConnection(userCode,userName){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode+"&statusCode=ignore",
         	success: function(json) {
	        	$("#selector4").removeAttr("style");
	 			$("#selector4").css({ display: "block" });
	 			var receivedCount =  parseInt(localStorage.getItem("receivedCount"));
    			receivedCount = receivedCount-1;
    			localStorage.setItem("receivedCount", receivedCount);
    			$("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
    	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
    	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount"));
	 			$("#connectionState"+userCode).empty();
	         	$("#connectionState"+userCode).append('<a href="javascript:void(0)" onclick="getConnection(\''+userCode+'\', \''+userName+'\')" id="connection'+userCode+'" class="connlink">Connect</a>');
	         	eduMsgFadeinFunction(userName+"'\s invitation removed");
      },
      error: function(e){
      }
	 }); 
 }else {
		location.href='/login';
	}
	});
	}
 
function acceptConnection(userCode,userName){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/ws/acceptConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode,
       		success: function(json) {
	        	$("#selector4").removeAttr("style");
				$("#selector4").css({ display: "block" });
				var connectedCount =  parseInt(localStorage.getItem("connectedCount"));
    			connectedCount = connectedCount+1;
    			localStorage.setItem("connectedCount", connectedCount);
    			var receivedCount =  parseInt(localStorage.getItem("receivedCount"));
    			receivedCount = receivedCount-1;
    			localStorage.setItem("receivedCount", receivedCount);
    			$("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
    	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
    	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount"));
				$("#connectionState"+userCode).empty();
	        	$("#connectionState"+userCode).append('<b class="connlink gray">Connected</b>');
	        	eduMsgFadeinFunction("You're connected with "+userName);
     },
     error: function(e){
     }
	 }); 
}		else {
			location.href='/login';
		}
		});
		}
</script>
<script>
function getDisconnectConnection(userCode,userName){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode+"&statusCode=connected",
            success: function(json) {
	            	$("#selector4").removeAttr("style");
	    			$("#selector4").css({ display: "block" });
	    			var connectedCount =  parseInt(localStorage.getItem("connectedCount"));
	    			connectedCount = connectedCount-1;
	    			localStorage.setItem("connectedCount", connectedCount);
	    			$("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
	    	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
	    	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount"));
	    			$("#connectionState"+userCode).empty();
	            	$("#connectionState"+userCode).append('<a href="javascript:void(0)" onclick="getConnection(\''+userCode+'\', \''+userName+'\')" id="connection'+userCode+'" class="connlink">Connect</a>');
	            	eduMsgFadeinFunction(userName+" removed from your connections");
         },
         error: function(e){
	        }
	 });  
    }
		else {
			location.href='/login';
		}
		});
		}
</script>
</section>

<script>
$(document).ready(function(){
    $(window).scroll(function() {
        if ($(this).scrollTop() > 50) {
            $('.conneWrapHead').addClass('conneWrapHeadFixed');
            $(".conHeadgap").css("height", "44px");
        } else {
               $('.conneWrapHead').removeClass('conneWrapHeadFixed');
            $(".conHeadgap").css("height", "0px");
        }
    });
});
</script>

<jsp:include page="../masterFooter.jsp" />
 
