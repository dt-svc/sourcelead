<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>



<script type="text/javascript">
window.onload = function () {
	getUsersList();
 };
</script>

<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<div class="success_pop" id="mailMsg" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Invitation sent successfully</span>
</div>

  <!-- Content Area -->
  
  <section class="content-wraper">
  <div class="content-dashboard clearfix">
   <div id="loading"></div>
    <section class="content-header clearfix">
         <c:if test="${not empty msg}">
	        <div class="success_pop" id="selector">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
      <div class="pull-left">
        <h2>Users List</h2>
      </div>
      
    <div class="pull-right brud-crum"><a href="dashboard">Home</a> &raquo; Users List </div>
    </section>
      <section class="clearfix section-action-btns">
        <ul class="pull-right list-unstyled list-inline">
          <li><i class="fa fa-filter"></i>&nbsp;
               <select id="userState" onchange="getUsersList();">
				<option value="">--Select Status--</option>
				<option value="ACTIVE" >Active</option>
				<option value="INACTIVE" >In Active</option>
				<option value="NOT_LOGGEDIN">Not Logged IN</option>
			   </select>
          
          </li>&nbsp;&nbsp;|&nbsp;
          
		   <li><i class="fa fa-filter"></i>&nbsp;
				<select id="userRole" onchange="getUsersList();">
				<option value="">--Select Role--</option>
				<option value="All" >All Roles</option>
				<c:forEach var="roles" items="${rolesList}">
	                 <option value="${roles.id}">${roles.displayName}</option>
                </c:forEach>
				</select>
		   </li>
		   
		 <li>
			  <a href="javascript:void(0)">
			  <i class="fa fa-user"></i></a>
				<span class="action-search"><input type="text" id="searchByName"></span>
				<span class="action-search" onclick="getUsersList();"><i class="fa fa-search"></i></span>
		  </li> 
		
		 
		  <li><a href="<c:url value='/userCreation'/>"><i class="fa fa-user-plus"></i>&nbsp;Create</a></li>
		  <li><a href="<c:url value='/uploadForm'/>"><i class="fa fa-upload"></i>&nbsp;Bulk Imports</a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
      <div class="row">
          <div id="usersListBody"></div> 
      </div>
      <div id="usersListEmptyBody"></div>
      </div>
      </div>
  </section>
  
      <!-- Content Area Ends--> 

      <jsp:include page="../masterFooter.jsp" />
      <div class="backtotop" style="display: block;"><img src="resources/images/backtotop.png"></div>  
      
      
      
<script type="text/javascript">
	function getUsersList(){
		
		var sourceleadUrl = '${sourceleadUrl}';
		var loginUserRole = '${SourceLeadWeb_loginUserRole}';
		if(loginUserRole == 'ROLE_ADMIN'){
			var userRole = document.getElementById("userRole").value;
	   	}
		var searchByName = document.getElementById("searchByName").value;
		var userState = document.getElementById("userState").value;
		
	    $('#usersListBody').empty();
        $('#usersListEmptyBody').empty();
		
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		
		 $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getUsersList?${_csrf.parameterName}=${_csrf.token}&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole,
	    			 
            success: function(data) {
	        var json = JSON.parse(data);
	        var result = "";
	        
	    	if(json.length > 0)
	   		{
	    		
	 			for(var i=0; i<json.length; i++){
		       	 
	 				var userRole = json[i];
	 				var mobilePhone = ""
	 			    
	 			    if(userRole.user.mobilePhone != null && userRole.user.mobilePhone != ''){
	 			    	mobilePhone = userRole.user.mobilePhone;
	 			    }else{
	 			    	mobilePhone = "NO DATA"
	 			    }
	 				
	       	    	result = "<div class='col-xs-12 col-sm-12 col-md-6 col-lg-4'>";
	       	    	result = result +"<div class='user-profile'>";
	       	    	result = result +"<div class='col-sm-4'>";
	       	    	result = result +"<div class='text-center image'>";
	       	    	result = result +"<img onerror='predefineProfileImage(this);' class='img-circle m-t-xs img-responsive' src='"+sourceleadUrl+"/displayUserImage/"+userRole.user.id+"' alt='image'>";
	       	    	
	       	    	if(userRole.user.isMailSent != null && userRole.user.isMailSent != "" && userRole.user.isMailSent == 'N' && userRole.user.userState != 'INACTIVE'){
	       	    	  result = result +"<span id='user"+userRole.user.id+"'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.user.id+");'>&nbsp;Invite</a></span>";
	 			    }else if((userRole.user.isMailSent != null && userRole.user.isMailSent != "" && userRole.user.isMailSent == 'Y') &&
	 			    		(userRole.user.userState != null && userRole.user.userState != "" && userRole.user.userState == 'NOT_LOGGEDIN') ){
	 			    	result = result +"<span><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.user.id+");'>&nbsp;Resend</a></span>";
	 			    }else if(userRole.user.userState != null && userRole.user.userState != "" && userRole.user.userState == 'ACTIVE'){
	 			    	result = result +"<span class='label label-success'>Active</span>";
	 			    }else{
	 			    	result = result +"<span class='label label-danger'>Inactive</span>";
	 			    }
	       	    	
	       	    	
	       	    	result = result +"</div>";
	       	    	result = result +"</div>";
	       	    	result = result +"<div class='col-sm-8'>";
	       	    	result = result +"<h4><strong><a href='<c:url value='/userSummary/"+userRole.user.id+"'/>''> "+userRole.user.firstName+" "+userRole.user.lastName+" </a> </strong></h4>";
	       	    	result = result +"<p><label><i class='fa fa-envelope-o' aria-hidden='true'></i>&nbsp; "+userRole.user.email+" </lable></p>";
	       	    	result = result +"<p><label><i class='fa fa-phone'  aria-hidden='true'></i>&nbsp; "+mobilePhone+" </label></p>";
	       	    	result = result +"<p><label><i class='fa fa-user'  aria-hidden='true'></i>&nbsp; "+userRole.userRoleName+" </label></p>";
	       	    	result = result +"</div>";
	       	    	result = result +"<div class='clearfix'></div>";
	       	    	result = result +"</div>";
	       	    	result = result +"</div>";
	       	    	
	       	    	$("#loading").removeAttr("style"); 
	       	 	    $('#usersListBody').append(result);
	       	 	    $("#searchByName").val("");
				   }
	   		    }
	    	else{
	    		result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-user'></i><h3>Users List is Empty</h3></div>";
	    		$('#usersListBody').empty();
	    		$("#loading").removeAttr("style");
	    		$('#usersListEmptyBody').append(result);
       	 	    $("#searchByName").val("");
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
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendInvitationEmail?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
			        success: function(result) {
			        	$("#user"+userId).html("<i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)'  onClick='resendInvitation("+userId+");'>&nbsp;Resend</a>");
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
			        	alert("hiii"+result);
			        	 
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
</script> 

    