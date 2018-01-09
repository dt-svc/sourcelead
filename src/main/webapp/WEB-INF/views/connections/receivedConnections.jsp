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
</section>


<div class="conneWrap">
<input type="hidden" id="noOrgsList" name="noOrgsList"/>
 <input type="hidden" name="orgOffset" id="orgOffset" value="0"/>
	<div class="conneWrapHead clearfix">
		<ul class="clearfix">
			<li><a href="<c:url value='${appPath}/loadFindConnections'/>">Find</a></li>
			<li><a href="<c:url value='${appPath}/loadConnectedList'/>">Connected</a></li>
			<li><a href="${appPath}/loadSentConnections">Sent</a></li>
			<li  class="current"><a href="<c:url value='${appPath}/loadReceivedConnections'/>">Received</a></li>
		</ul>
		<span><input class="immigraTabSerach" type="text" id="searchFindConnId"></span>
	</div>
	<div class="conHeadgap"></div>
	<div class="conneWrapInn" id="findConnectionListId"></div>
	<div class="clearfix"></div>
	<div id="loadMoreDiv"></div> 	
</div>
			

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
    if($(window).scrollTop() == $(document).height() - $(window).height()) {
    	var avilableCount = $("#orgOffset").val();
    	
    	avilableCount = parseInt(avilableCount) + parseInt(12); 
    	$("#orgOffset").val(avilableCount);
    	//searchUser("scroll");
    	 getConnectionsList("scroll");
    }
    
});

window.onload = function () {
	getConnectionsList('manual');
 };

</script>
 <script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>
<script type="text/javascript">
function eduMsgFadeinFunction(message) {
	$("#selector4").fadeIn(200);
	$("#inviteMessage").text(message);
    $('#selector4').delay(1000).fadeOut('slow');
}
var count = 0; 
	function getConnectionsList(type){
		$.post("check-session", {}, function(data) {
			if(data === "YES") {
		var sourceleadUrl = '${sourceleadUrl}';
		 var searchValue = $("#searchFindConnId").val();
		 if(type == "scroll"){
			offset = $("#orgOffset").val();
    	}
		if(type == "manual"){
			$('#findConnectionListId').empty();
    		$("#orgOffset").val(0);
   		    offset = 0;
    	} 
		//$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 $.ajax({ 
	    	type : "GET",
			url : sourceleadUrl+"/getConnectionsList?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue+"&offset="+offset+"&status=received",
            success: function(json) {
	        var result = "";
	        if(json != null && json.length>0){
	        	count++;
	        }
	        if(json != null && json.length > 0)
	   		{
	    		 $('#loadMoreDiv').empty();
	 			for(var i=0; i<json.length; i++){
	 				var user = json[i];
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
	 				result = result + "<a href='javascript:void(0)' class='conUnder'><p>"+mutCountStatus+"</p></a>";
	 				result = result + '</div><div class="connCtaBtn"><a href="#" onclick="acceptConnection(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink green">Accept</a><a href="#" onclick="ignoreConnection(\''+user.userCode+'\', \''+user.userName+'\')" id="connection'+user.userCode+'" class="connlink orange">Ignore</a></div></div></div>';
	   		    }
	 			
	 			if(type == "manual"){
            		$('#findConnectionListId').append(result);
            	}else{
            		$('#findConnectionListId').append(result);
            	}
	 			$("#noOrgsList").val("")
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
	else {
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
		         	getConnectionsList("manual");
		         	eduMsgFadeinFunction(userName+"'\s invitation removed");
	      },error: function(e){
	        }
		 }); 
		} else {
			location.href='/login';
		}
	}); 
 }
 
function acceptConnection(userCode,userName){
	alert(123)
	$.post("check-session", {}, function(data) {
		if(data === "YES") {
			 $.ajax({ 
			    	type : "GET",
					url : sourceleadUrl+"/ws/acceptConnection?${_csrf.parameterName}=${_csrf.token}&userCode="+userCode,
		        	success: function(json) {
				        	$("#selector4").removeAttr("style");
							$("#selector4").css({ display: "block" });
				        	getConnectionsList("manual");
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