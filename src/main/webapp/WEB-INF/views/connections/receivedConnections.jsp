<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<html lang="en">
<head>
 <script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>
<div class="success_pop" id="selector4" style="display: none;">
	<i class="fa fa-check-circle"></i><span id="inviteMessage"></span>
</div>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Connections</h2>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Conections</div>
  <div id="loadingConnReceivedDiv"></div>
</section>


<div class="conneWrap">
<input type="hidden" id="noOrgsList" name="noOrgsList"/>
 <input type="hidden" name="orgOffset" id="orgOffset" value="0"/>
	<div class="conneWrapHead clearfix">
		<ul class="clearfix connection-count" >
			
			<li><a href="<c:url value='${appPath}/loadConnectedList?status=connected'/>">Connected<i id="connectedCountVal"></i></a></li>
			<li><a href="${appPath}/loadSentConnections?status=sent">Sent<i id="sentCountVal"></i></a></li>
			<li  class="current"><a href="<c:url value='${appPath}/loadReceivedConnections?status=received'/>">Received<i id="receivedCountVal"></i></a></li>
			<li><a href="<c:url value='${appPath}/loadFindConnections?status=find'/>">Find</a></li>
		</ul>
		<span><input class="immigraTabSerach" type="text" id="searchFindConnId" placeholder="Name/Email/Role/Company"></span>
		<div class="conneWrapHeadBtn">
            <a href="javascript:void(0)" id="acceptAllRequest" class="color">Accept all</a>
            <a href="javascript:void(0)" id="ignoreAllRequest">Ignore all</a>
        </div>
	</div>
	<div class="conHeadgap"></div>
	<div class="conneWrapInn clearfix" id="findConnectionListId"></div>
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

</section>
<jsp:include page="../masterFooter.jsp" />
<script type="text/javascript">
$(document).scroll(function(e){
    // grab the scroll amount and the window height
    var scrollAmount = $(window).scrollTop();
    var documentHeight = $(document).height();
    offset = $("#orgOffset").val();
    var UsersCount = $("#totalUsersCount").val(); 
    // calculate the percentage the user has scrolled down the page
   // if($(window).scrollTop() == $(document).height() - $(window).height()) {
	if($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
    	var avilableCount = $("#orgOffset").val();
    	
    	avilableCount = parseInt(avilableCount) + parseInt(18); 
    	$("#orgOffset").val(avilableCount);
    	//searchUser("scroll");
    	 getConnectionsList("scroll");
    }
    
});

window.onload = function () {
	getConnectionsList('manual');
 };
 
$("#ignoreAllRequest").click(function(e){
	if(receivedConnUuidVal.length != 0){
	 $.ajax({ 
	 		type : "POST",
	 		data : {
				connectionsUerIdList : receivedConnUuidVal
	 		},
			url : sourceleadUrl+"/withdrawAllValues",
	     	success: function(json) {
	     		if(json == 'success'){
	     			eduMsgFadeinFunction(receivedConnUuidVal.length+" invitations removed")
	     			getConnectionsList('manual');
	     		}
	     	}
		});
	}else{
		e.preventDefault();
	}
 });

 $("#acceptAllRequest").click(function(e){
	 if(receivedConnUuidVal.length != 0){
	 $.ajax({ 
	 		type : "POST",
	 		data : {
				connectionsUerIdList : receivedConnUuidVal
	 		},
			url : sourceleadUrl+"/acceptAllValues",
	     	success: function(json) {
	     		if(json == 'success'){
	     			eduMsgFadeinFunction("You're connected with "+receivedConnUuidVal.length+" users");
	     			getConnectionsList('manual');
	     		}
	     	}
		});
	 }else{
		e.preventDefault();
	}
 });
 
</script>
 <script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>
<script type="text/javascript">
function eduMsgFadeinFunction(message) {
	$("#selector4").fadeIn(200, function(){
		$("#inviteMessage").text(message);
		$(this).delay(200).fadeOut('slow');
	});
}
	var receivedConnUuidVal = [];
	function getConnectionsList(type){
		$.post("check-session", {}, function(data) {
			if(data === "YES") {
		var sourceleadUrl = '${sourceleadUrl}';
		 var searchValue = $("#searchFindConnId").val();
		 if(type == "scroll"){
			offset = $("#orgOffset").val();
    	}
		if(type == "manual"){
			localStorage.setItem("receivedCount", 0);
			if(isNaN(localStorage.getItem("sentCount"))){
				localStorage.setItem("sentCount", 0);	
			}
			if(isNaN(localStorage.getItem("connectedCount"))){
				localStorage.setItem("connectedCount", 0);
			}
			$('#findConnectionListId').empty();
    		$("#orgOffset").val(0);
   		    offset = 0;
    	} 
		receivedConnUuidVal = [];
		$("#loadingConnReceivedDiv").attr('style','position:fixed; width:auto; height:100%; background-color:rgba(255,255,255,0.8); top:52px; left:220px; right:30px; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/getConnectionsList?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue+"&offset="+offset+"&status=received",
            success: function(json) {
	        var result = "";
			localStorage.setItem("sentCount", json[0].sentCount);
			localStorage.setItem("receivedCount", json[0].receivedCount);
			localStorage.setItem("connectedCount", json[0].connectedCount);
			if(json[0].countFlag == null){
	        if(json != null && json.length > 0)
	   		{
	    		 $('#loadMoreDiv').empty();
	 			for(var i=0; i<json.length; i++){
	 				var user = json[i];
	 				var mutCountStatus = null;
	 				receivedConnUuidVal.push(user.connectionUUID);
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
	 				result = result + "</span><a href='javascript:void(0)'><h2>"+user.userName+"</h2>";
	 				result = result + "<p>"+user.userType+" <b> "+orgNameValue+" </b></p></a>";
	 				result = result + mutCountStatus;
	 				result = result + '</div><div class="connCtaBtn" id="connectionState'+user.userCode+'"><a href="javascript:void(0)" onclick="acceptConnection(\''+user.userCode+'\',\''+user.connectionUUID+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink green">Accept</a><a href="javascript:void(0)" onclick="ignoreConnection(\''+user.userCode+'\', \''+user.userName+'\',\''+user.connectionUUID+'\')" id="connection'+user.userCode+'" class="connlink orange">Ignore</a></div></div></div>';
	   		    }
	 			$('#findConnectionListId').append(result);
        		if(json.length < 18){
	 				$('#loadMoreDiv').append('<div class="loadWrap"> No More Records..</div>');
        		}else if(json != null && json.length >= 18 && json.length != 0 ){
	 				$('#loadMoreDiv').append('<div class="loadWrap"><i class="fa fa-spinner fa-spin"></i> More Records..</div>');
	 			}
	   		}
	   		}else if(type === 'manual' && json[0].countFlag == 'true'){
	   			$('.connHeadText').empty();
	   			$('#loadMoreDiv').empty();
	   			$('#loadMoreDiv').append('<div class="noDate"></div>');
	   		}
	        $("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount"));
	        $("#loadingConnReceivedDiv").removeAttr("style");
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
	$(".popClo").click(function(){
    	$("#popupMaskShade").hide();
        $("#popupMaskLight").hide();
    });
</script>
<script>
function ignoreConnection(userCode,userName,connectionUUID){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
			$("#loadingConnReceivedDiv").attr('style','position:fixed; width:auto; height:100%; background-color:rgba(255,255,255,0.8); top:52px; left:220px; right:30px; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({ 
		    	type : "GET",
				url : sourceleadUrl+"/withdrawConnection?${_csrf.parameterName}=${_csrf.token}&connectionUUID="+connectionUUID,
	         success: function(json) {
		        	 $("#selector4").removeAttr("style");
		 			$("#selector4").css({ display: "block" });
		 			$("#loadingConnReceivedDiv").removeAttr("style");
		 			var receivedCount =  parseInt(localStorage.getItem("receivedCount"));
	    			receivedCount = receivedCount-1;
	    			localStorage.setItem("receivedCount", receivedCount);
	    			$("#sentCountVal").empty().append(localStorage.getItem("sentCount"));
	    	        $("#receivedCountVal").empty().append(localStorage.getItem("receivedCount"));
	    	        $("#connectedCountVal").empty().append(localStorage.getItem("connectedCount"));
		 			$("#connectionState"+userCode).empty();
	            	$("#connectionState"+userCode).append('<b class="connlink gray">Ignored</b>');
		         	eduMsgFadeinFunction(userName+"'\s invitation removed");
	      },error: function(e){
	        }
		 }); 
		} else {
			location.href='/login';
		}
	}); 
 }
 
function acceptConnection(userCode,connectionUUID,userName){
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
			$("#loadingConnReceivedDiv").attr('style','position:fixed; width:auto; height:100%; background-color:rgba(255,255,255,0.8); top:52px; left:220px; right:30px; z-index:10000; background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.ajax({ 
			    	type : "GET",
					url : sourceleadUrl+"/ws/acceptConnection?${_csrf.parameterName}=${_csrf.token}&connectionUUID="+connectionUUID,
		        	success: function(json) {
				        	$("#selector4").removeAttr("style");
							$("#selector4").css({ display: "block" });
							$("#loadingConnReceivedDiv").removeAttr("style");
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
			            	$("#connectionState"+userCode).append('<b class="connlink gray">Accepted</b>');
				        	eduMsgFadeinFunction("You're connected with "+userName);
		     		},
		     		error: function(e){
			        }
			 });
		} else {
			location.href='/login';
		}
	});
	}
</script>
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
</body>
</html>