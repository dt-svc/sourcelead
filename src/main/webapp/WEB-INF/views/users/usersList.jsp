<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style type="text/css">
#loadMoreDiv{
text-align: center;
font-size: 18px;
font-weight: bold;
vertical-align: middle;
padding: 30px 15px;
}
#loadMoreDiv .fa-spinner{
margin-right: 15px;
}
</style>
<script type="text/javascript">
window.onload = function () {
	getUsersList('manual');
 };
</script>

<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofileImg.jpg');
	}
</script>
<input type="hidden" id="noUsers" name="noUsers"/>
<div class="success_pop" id="mailMsg" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Invitation sent successfully</span>
</div>

  <!-- Content Area -->
  
  <section class="content-wraper">
  <div class="content-dashboard clearfix">
   <div id="loading"></div>
    <section class="content-header clearfix">
         <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
      <div class="pull-left">
        <h2>Users List</h2>
      </div>
      
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Users List </div>
    </section>
      <section class="clearfix section-action-btns">
        <ul class="pull-right list-unstyled list-inline">
        <input type="hidden" name="usersOffset" id="usersOffset" value="0"/>
          <li><i class="fa fa-filter"></i>&nbsp;
               <select id="userState" onchange="getUsersList('manual');">
				<option value="">--Select Status--</option>
				<option value="ACTIVE" >Active</option>
				<option value="INACTIVE" >In Active</option>
				<option value="NOT_LOGGEDIN">Not Logged IN</option>
			   </select>
          
          </li>
          
		   <li><i class="fa fa-filter"></i>&nbsp;
				<select id="userRole" onchange="getUsersList('manual');">
				<option value="">&nbsp;--Select Role--&nbsp;&nbsp;&nbsp;</option>
				<!-- <option value="All" >All Roles</option> -->
				<c:forEach var="roles" items="${rolesList}">
	                 <option value="${roles.id}">${roles.displayName}</option>
                </c:forEach>
				</select>
		   </li>
		   
		 <li>
			  <a href="javascript:void(0)">
			  <i class="fa fa-user"></i></a>
				<span class="action-search"><input type="text" placeholder="Name / Email" id="searchByName"></span>
				<span class="action-search" onclick="getUsersList('manual');"><i class="fa fa-search"></i></span>
		  </li> 
		
		   <sec:authorize access="hasAnyRole('ROLE_CREATE_USER')"> 
	           <li><a href="<c:url value='/userCreation'/>"><i class="fa fa-user-plus"></i>&nbsp;Create</a></li>
	       </sec:authorize>
		  
		   <sec:authorize access="hasRole('ROLE_EXPORT_USERS')"> 
		   <li><a  id="export-pop"><i class="glyphicon glyphicon-export"></i>&nbsp;Export</a>
			    <div class="export_pop">
				   <ul>
				   <li onclick="exportToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp; PDF</li>
				   <li onclick="exportUserData();"><i class="fa fa-file-excel-o"></i>&nbsp; Excel</li>
				   </ul>
				</div>
		   </li>
		    </sec:authorize>
		   
		     <sec:authorize access="hasAnyRole('ROLE_USER_BULK_IMPORTS')"> 
	                      <li><a href="<c:url value='/uploadForm'/>"><i class="fa fa-upload"></i>&nbsp;Bulk Imports</a></li>
	         </sec:authorize>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
      <div class="row">
          <div id="usersListBody"></div> 
          <div id="loadMoreDiv"></div> 
      </div>
      <div id="usersListEmptyBody"></div>
      <div id="popUp"></div>
      </div>
      </div>
  </section>
  
      <!-- Content Area Ends--> 

      <jsp:include page="../masterFooter.jsp" />
      <div class="backtotop" style="display: none;"><img src="resources/images/backtotop.png"></div>  
  
 <!--  Rajashkar
  SCript is used to export user data as xls format -->    
<script>
function exportUserData(){
	var userListEmpty = $("#noUsers").val();
	var exportType = "xls";
	var sourceleadUrl = '${sourceleadUrl}';
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    var userRole = document.getElementById("userRole").value;
	var searchByName = document.getElementById("searchByName").value;
	var userState = document.getElementById("userState").value;
	if(userListEmpty != 'nousers'){
		window.location.href = sourceleadUrl + "/getExportUsers?&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&exportType=xls";
	}else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Users List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	

}
</script> 
<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
  
 <!--  Rajashkar
  SCript is used to export user data as pdf format --> 
<script>
function exportToPdf(){
	var userListEmpty = $("#noUsers").val();
	var exportType = "pdf";
	var sourceleadUrl = '${sourceleadUrl}';
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    var userRole = document.getElementById("userRole").value;
	var searchByName = document.getElementById("searchByName").value;
	var userState = document.getElementById("userState").value;
	if(userListEmpty != 'nousers'){
	window.location.href = sourceleadUrl + "/getExportUsers?&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&exportType=pdf";
	}else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Users List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	
}
</script>
   
   
   
   
<script>
	$(document).scroll(function(e){
	    // grab the scroll amount and the window height
	    var scrollAmount = $(window).scrollTop();
	    var documentHeight = $(document).height();
	    offset = $("#usersOffset").val();
	    var UsersCount = $("#totalUsersCount").val(); 
	    // calculate the percentage the user has scrolled down the page
	    if($(window).scrollTop() == $(document).height() - $(window).height()) {
	    	var avilableCount = $("#usersOffset").val();
	    	
	    	avilableCount = parseInt(avilableCount) + parseInt(10); 
	    	$("#usersOffset").val(avilableCount);
	    	getUsersList("scroll");
	    }
	});
</script>
   
   
      
<script type="text/javascript">
	function getUsersList(type){
		
		var sourceleadUrl = '${sourceleadUrl}';
		var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	    var userRole = document.getElementById("userRole").value;
		
		var searchByName = document.getElementById("searchByName").value;
		var userState = document.getElementById("userState").value;
		
		offset = $("#usersOffset").val();
		
		if(type == "manual"){
    		$('#usersListBody').empty();
    	}
		
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		
		 $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getUsersList?${_csrf.parameterName}=${_csrf.token}&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&offset="+offset,
	    			 
            success: function(data) {
            	 var json = JSON.parse(data);
            	 var result = "";
          if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 	
	       	
	    	if(json.length > 0)	
	   		{
	 			for(var i=0; i<json.length; i++){
	 				
			 				var userRole = json[i];
			 				var mobilePhone = ""
			 			    
			 			    if(userRole.user.mobilePhone != null && userRole.user.mobilePhone != ''){
			 			    	mobilePhone = userRole.user.mobilePhone;
			 			    }else{
			 			    	mobilePhone = "No Data"
			 			    }
			 				
			       	    	result = "<div class='col-xs-12 col-sm-12 col-md-6 col-lg-4'>";
			       	    	result = result +"<div class='user-profile'>";
			       	    	result = result +"<div class='col-xs-4 col-sm-4'>";
			       	    	result = result +"<div class='text-center image-parent'>";
			       	    	result = result +"<div class='imageParent'>";
			       	    	result = result +"<img onerror='predefineProfileImage(this);' class='m-t-xs' src='"+sourceleadUrl+"/displayUserImage/"+userRole.user.id+"' alt='image'>";
			       	    	result = result +"</div>";
			       	    	
			       	    	
			       	    	result = result +"</div>";
			       	    	result = result +"</div>";
			       	    	result = result +"<div class='col-xs-8 col-sm-8'>";
			       	    	
			       	    	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_USER\',\'ROLE_VIEW_EDIT_USER\',\'ROLE_VIEW_DELETE_USER\')">'
			       	         	result = result +"<h4><a href='<c:url value='/userSummary/"+userRole.user.id+"'/>''> "+userRole.user.firstName+" "+userRole.user.lastName+" </a> </h4>";
			       	    	'</sec:authorize>';
			       	    	
			       	    	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_USER\',\'ROLE_VIEW_EDIT_USER\',\'ROLE_VIEW_DELETE_USER\')">'
		       	         	   result = result +"<h4><a href='#'> "+userRole.user.firstName+" "+userRole.user.lastName+" </a> </h4>";
		       	    	    '</sec:authorize>';
			       	    	
			       	    	result = result +"<p><label><i class='fa fa-envelope-o' aria-hidden='true'></i>&nbsp; "+userRole.user.email+" </lable></p>";
			       	    	result = result +"<p><label><i class='fa fa-phone'  aria-hidden='true'></i>&nbsp; "+mobilePhone+" </label></p>";
			       	    	result = result +"<p><label><i class='fa fa-user'  aria-hidden='true'></i>&nbsp; "+userRole.userRoleName+" </label></p>";
			       	        
			       	    	if(userRole.user.isMailSent != null && userRole.user.isMailSent != "" && userRole.user.isMailSent == 'N' && userRole.userState != 'INACTIVE'){
			       	    	  result = result +"<p><label id='user"+userRole.user.id+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.user.id+");'>&nbsp;&nbsp;Invite</a></label></p>";
			 			    }else if((userRole.user.isMailSent != null && userRole.user.isMailSent != "" && userRole.user.isMailSent == 'Y') &&
			 			    		(userRole.userState != null && userRole.userState != "" && userRole.userState == 'NOT_LOGGEDIN') ){
			 			    	 result = result +"<p><label id='user"+userRole.user.id+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.user.id+");'>&nbsp;&nbsp;Resend</a></label></p>";
			 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'ACTIVE'){
			 			    	result = result +"<p><label class='label label-success'><i class='fa fa-check'></i>&nbsp;&nbsp;Active</label></p>";
			 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'INACTIVE'){
			 			    	result = result +"<p><label class='label label-danger'><i class='fa fa-ban'></i>&nbsp;&nbsp;In-active</label></p>";
			 			    } 
			       	    	
			       	    	result = result +"</div>";
			       	    	result = result +"<div class='clearfix'></div>";
			       	    	result = result +"</div>";
			       	    	result = result +"</div>";
			       	    	$("#loading").removeAttr("style"); 
			       	    	if(type == "manual"){
			            		$('#usersListBody').append(result);
			            		 $("#searchByName").val("");
			            	}else{
			            		$('#usersListBody').append(result);
			            		 $("#searchByName").val("");
			            	}

				   }
	 			result = result + '<div class="clear"></div>';
	   		    }else{
	   		    	$("#loading").removeAttr("style");
	   		    	$('#loadMoreDiv').empty(); 
		         	result = result + '<div id="loadMoreDiv" ><i class="fa fa-spinner"></i>No More Records..</div>';
		         	$('#loadMoreDiv').append(result);
	   		    }
            }else{
            	$('#loadMoreDiv').empty(); 
            	$("#loading").removeAttr("style");
	         	result = result + '<div id="loadMoreDiv" ><i class="fa fa-spinner"></i>No More Records..</div>';
	         	$('#loadMoreDiv').append(result);
            }
	    	
			 },
	        error: function(e){
	        }
	    });  
	}
</script>


<script type="text/javascript">
function resendInvitation(userId){
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendInvitationEmail?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
			        success: function(result) {
			        	$("#user"+userId).html("<i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)'  onClick='resendInvitation("+userId+");'>&nbsp;Resend</a>");
			        	$("#loading").removeAttr("style"); 
			        	$("#mailMsg").fadeIn(200);
		   		        $('#mailMsg').delay(2000).fadeOut('slow');
			        },
			    });
		 }
	}
  
</script>


<script type="text/javascript">
function getUserSummary(userId){
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != ""){
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/userSummary?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
			        success: function(result) {
			        	 
			        },
			    });
		 }
	}
  
</script>

<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script> 
   

<script>
  $('#selector').delay(2000).fadeOut('slow');
  $(document).ready(function() {
	   $("#export-pop").click(function(){
	               $(".export_pop").slideToggle(200);
	       });
	   $("html").click(function() {
	       $(".export_pop").slideUp(200);
	   });
	   $(".export_pop, #export-pop").click(function(e) {
	       e.stopPropagation();
	   });        
	});  
</script> 

    