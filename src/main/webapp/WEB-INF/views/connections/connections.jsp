
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
<div id="loadingConnectionDiv"></div>
</section>


<div class="conneWrap">
<input type="hidden" id="noOrgsList" name="noOrgsList"/>
 <input type="hidden" name="orgOffset" id="orgOffset" value="0"/>
	<div class="conneWrapHead clearfix">
		<ul class="clearfix connection-count">
			<li><a href="<c:url value='${appPath}/loadConnectedList?status=connected'/>">Connected <i id="connectedCountVal"></i></a></li>
			<li><a href="${appPath}/loadSentConnections?status=sent">Sent<i id="sentCountVal"></i></a></li>
			<li><a href="<c:url value='${appPath}/loadReceivedConnections?status=received'/>">Received<i id="receivedCountVal"></i></a></li>
			<li class="current"><a href="#" onclick="getConnectionListOnClick()">Find</a></li>
		</ul>
		<span><input class="immigraTabSerach" id="searchFindConnId" placeholder="Name/Email/Role/Org" type="text"></span>
		
		<div class="conneWrapHeadBtn">
            <a href="javascript:void(0)" class="color" onclick="inviteAllMutualSearch();">Invite all</a>
        </div>
		
	</div>
	<div class="conHeadgap"></div>
	
	<h5 class="connHeadText">People You May Know</h5>
	<div class="conneWrapInn clearfix" id="findConnectionListId">
	</div>
	<div id="loadMoreDiv"></div> 
</div>

<!--  PopUp  -->
<div class="popMask" id="popupMaskShade"></div>
<div class="conePop" id="popupMaskLight">
    <div class="conePopHead">
        <h2>Mutual Connections</h2>
        <a href="javascript:void(0)" class="popClo"><i class="fa fa-times"></i></a>
    </div>
    <div class="conePopInn" id="mutualConnDynamic">
    </div>
</div>
<!--  PopUp  -->

<script>

	$(window).scroll(function(){
    // grab the scroll amount and the window height
    var scrollAmount = $(window).scrollTop();
    var documentHeight = $(document).height();
    offset = $("#orgOffset").val();
    // calculate the percentage the user has scrolled down the page
   	if($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
    	var avilableCount = $("#orgOffset").val();
    	avilableCount = parseInt(avilableCount) + parseInt(18); 
    	$("#orgOffset").val(avilableCount);
    	 getConnectionsList("scroll");
    }
});  


function inviteAllMutualSearch(){
	  $.ajax({ 
   		type : "POST",
   		data : {
			connectionsUerIdList : mutualConnUsers
   		},
		url : sourceleadUrl+"/inviteAllMutualAndSearch",
       	success: function(json) {
       		if(json == 'success'){
       			eduMsgFadeinFunction("Invitation successfully sent to "+mutualConnUsers.length);
       			getConnectionsList('manual');
       		}
       	}
	  });
}

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
	var userName = null;
	
var mutualConnUsers = [];
function getConnectionsList(type){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
		var sentCount = 0;
		var receivedCount = 0;
		var sourceleadUrl = '${sourceleadUrl}';
		 var searchValue = $("#searchFindConnId").val();
		 if(type === "scroll"){
			offset = $("#orgOffset").val();
    	}
		if(type === "manual"){
			localStorage.setItem("sentCount", 0);
			localStorage.setItem("receivedCount", 0);
			localStorage.setItem("connectedCount", 0);
			$("#findConnectionListId").empty();
    		$("#orgOffset").val(0);
   		    offset = 0;
    	}
		if($("#searchFindConnId").val()!=""){
			mutualConnUsers = [];
		}
		$("#loadingConnectionDiv").attr('style','position:fixed; width:auto; height:100%; background-color:rgba(255,255,255,0.8); top:52px; left:220px; right:30px; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({ 
	    	type : "POST",
			url : sourceleadUrl+"/getUserListForConnections?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue+"&offset="+offset,
            success: function(json) {
	        var result = "";
	        var mutualResult = "";
	        if(json[0].sentCount != 0){
					localStorage.setItem("sentCount", json[0].sentCount);
				}
				if(json[0].receivedCount != 0){
					localStorage.setItem("receivedCount", json[0].receivedCount);
				}
				if(json[0].connectedCount != 0){
					localStorage.setItem("connectedCount", json[0].connectedCount);
				}
				if(json[0].countFlag == null){
	        if(json !=null && json.length > 0)
	   		{
	    		 $('#loadMoreDiv').empty();
	    		 $('.connHeadText').show();
	 			for(var i=0; i<json.length; i++){
	 				var user = json[i];
	 				var state = null;
	 				if(user.requestSent == 1){
	 					state = '<a href="javascript:void(0)" onclick="getWithdrawConnections(\''+user.userCode+'\', \''+user.userName+'\', \''+user.connectionUUID+'\')" id="connection'+user.userCode+'" class="connlink orange">Withdraw</a>';
	 				}else if(user.requestRecieved == 1){
	 					state = '<a href="javascript:void(0)" onclick="acceptConnection(\''+user.userCode+'\', \''+user.userName+'\', \''+user.connectionUUID+'\')" id="connection'+user.userCode+'" class="connlink green">Accept</a><a href="javascript:void(0)" onclick="ignoreConnection(\''+user.userCode+'\', \''+user.userName+'\', \''+user.connectionUUID+'\')" id="connection'+user.userCode+'" class="connlink orange">Ignore</a>';
	 				}else{
						if($("#searchFindConnId").val()!=""){
							mutualConnUsers.push(user.userCode);
						}else{
		 					if(user.count != 0){
								mutualConnUsers.push(user.userCode);
		 					}
						}
	 					state = '<a href="javascript:void(0)" onclick="getConnection(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink">Connect</a>';
	 				}
	 				var mutCountStatus = null;
	 				if(user.count != 0){
	 					mutCountStatus ="<a href='javascript:void(0)' onclick='getMututalConnections("+user.id+");' class='conUnder'><p>"+user.count + " Mutual Connections</p></a>";
		 			}else{
		 				mutCountStatus = "<p>No Mutual Connections</p>";
	 				}
	 				var orgNameValue = null;
	 				if(user.orgName == ""){
	 					orgNameValue = "";
	 				}else{
	 					orgNameValue = "at " + user.orgName;
	 				}
	 				var fileUrl = "";
	 				if(user.fileUrl != null){
	 					fileUrl = user.fileUrl;
	 				}
	 				result = result + "<div class='connCta'><div class='connCtaInn'><div class='connCtaInnText'>";
	 				result = result + "<span><img data-id= 'userProfileImage' onerror='predefineProfileImage(this);' src='"+fileUrl+"' class='m-t-xs'   alt='image'>";
	 				result = result + "</span><a href='javascript:void(0)'><h2>"+(user.userName == null ? "---" : user.userName)+"</h2>";       //  change
	 				result = result + "<p>"+user.userType+" <b> "+orgNameValue+" </b></p></a>";
	 				result = result + mutCountStatus;
	 				result = result + '</div><div class="connCtaBtn" id="connectionState'+user.userCode+'">'+state+'</div></div></div>';
	   		    }
	 			if(result !== ""){
        			$("#findConnectionListId").append(result);
	 			}
	 			if(json.length < 18){
	 				$('#loadMoreDiv').append('<div class="loadWrap"> No More Records..</div>');
	 			}else if(json != null && json.length >= 18 && json.length != 0 ){
	 				$('#loadMoreDiv').append('<div class="loadWrap"><i class="fa fa-spinner fa-spin"></i> More Records..</div>');
	 			}
	   		}
	   		}else if(type === 'manual' && json[0].countFlag == 'true'){
	   			$('.connHeadText').hide(); 
	   			$('#loadMoreDiv').empty();
	   			$('#loadMoreDiv').append('<div class="noDate"></div>');
	   		}
	        $("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount"));
	        $("#loadingConnectionDiv").removeAttr("style"); 
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

function getMututalConnections(userId){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
	 $.ajax({ 
		type : "GET",
		url : sourceleadUrl+"/getMututalConnections?${_csrf.parameterName}=${_csrf.token}&userId="+userId,
        success: function(json) {
        	if(json !=null && json.length > 0){
	        	$("#popupMaskShade").show();
	            $("#popupMaskLight").show();
	            var mutualPopVal = "<ul>";
	        	for(var i=0; i<json.length; i++){
	 				var user = json[i];
	 				var orgNameValue = null;
	 				if(user.orgName == ""){
	 					orgNameValue = "";
	 				}else{
	 					orgNameValue = "at " + user.orgName;
	 				}
	 				var fileUrl = "";
	 				if(user.fileUrl != null){
	 					fileUrl = user.fileUrl;
	 				}
	 				mutualPopVal = mutualPopVal + '<li class="clearfix"><div class="UsImg"><img data-id="userProfileImage" onerror="predefineProfileImage(this);" src="'+fileUrl+'" alt="" title="'+user.userName+'"/></div>';
	 				mutualPopVal = mutualPopVal + '<div class="UsText"><h2>'+user.userName+'</h2><p>'+user.userType+' '+orgNameValue+'</p></div></li>';
	        	}
	        	mutualPopVal = mutualPopVal + '</ul>';
	        	$("#mutualConnDynamic").empty().append(mutualPopVal);
        	}
        }
	});
	}else {
		location.href='/login';
	}
	});
}

function eduMsgFadeinFunction(message) {
	$("#selector4").fadeIn(200, function(){
		$("#inviteMessage").text(message);
		$(this).delay(200).fadeOut('slow');
	});
	
	/* message = "";
    $('#selector4').delay(200).fadeOut('slow',function(){
    	$("#inviteMessage").text("");
    }); */
}


function getConnection(userCode,userName){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
			$("#loadingConnectionDiv").attr('style','position:fixed; width:auto; height:100%; background-color:rgba(255,255,255,0.8); top:52px; left:220px; right:30px; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/addNewConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode,
            success: function(json) {
	            	$("#selector4").removeAttr("style");
	    			$("#selector4").css({ display: "block" });
	    			var sentCount = parseInt(localStorage.getItem("sentCount"));
	    			sentCount = sentCount+1;
	    			localStorage.setItem("sentCount", sentCount);
	    			$("#loadingConnectionDiv").removeAttr("style");
	    			$("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
	    	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
	    	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount")); 
	    			$("#connectionState"+userCode).empty();
	    			$("#connectionState"+userCode).append('<a href="javascript:void(0)" onclick="getWithdrawConnections(\''+userCode+'\', \''+userName+'\',\''+json+'\')" id="connection'+userCode+'" class="connlink orange">Withdraw</a>');
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
function getWithdrawConnections(userCode,userName,connectionUUID){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
			$("#loadingConnectionDiv").attr('style','position:fixed; width:auto; height:100%; background-color:rgba(255,255,255,0.8); top:52px; left:220px; right:30px; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&connectionUUID="+connectionUUID,
            success: function(json) {
	            	$("#selector4").removeAttr("style");
	    			$("#selector4").css({ display: "block" });
	    			$("#loadingConnectionDiv").removeAttr("style");
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
function ignoreConnection(userCode,userName,connectionUUID){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
			$("#loadingConnectionDiv").attr('style','position:fixed; width:auto; height:100%; background-color:rgba(255,255,255,0.8); top:52px; left:220px; right:30px; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&connectionUUID="+connectionUUID,
         	success: function(json) {
	        	$("#selector4").removeAttr("style");
	 			$("#selector4").css({ display: "block" });
	 			$("#loadingConnectionDiv").removeAttr("style");
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
 
function acceptConnection(userCode,userName,connectionUUID){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
			$("#loadingConnectionDiv").attr('style','position:fixed; width:auto; height:100%; background-color:rgba(255,255,255,0.8); top:52px; left:220px; right:30px; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/ws/acceptConnection?${_csrf.parameterName}=${_csrf.token}&connectionUUID="+connectionUUID,
       		success: function(json) {
	        	$("#selector4").removeAttr("style");
				$("#selector4").css({ display: "block" });
				$("#loadingConnectionDiv").removeAttr("style");
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
/* function getDisconnectConnection(userCode,userName){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
			$("#loadingConnectionDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode+"&statusCode=connected",
            success: function(json) {
	            	$("#selector4").removeAttr("style");
	    			$("#selector4").css({ display: "block" });
	    			$("#loadingConnectionDiv").removeAttr("style");
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
		} */
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
    
    // POPUP
    $(".popClo").click(function(){
    	$("#popupMaskShade").hide();
        $("#popupMaskLight").hide();
    });    
});
</script>

<jsp:include page="../masterFooter.jsp" />
 
