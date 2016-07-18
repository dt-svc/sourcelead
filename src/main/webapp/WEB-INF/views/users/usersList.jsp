<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
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

  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Users List</h2>
      </div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="clearfix section-action-btns">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="javascript:void(0)"><i class="fa fa-check" aria-hidden="true"></i>&nbsp;Users&nbsp;</a>
               <select id="userState" onchange="getUsersList();">
				<option value="">--Select Status--</option>
				<option value="ACTIVE" >Active</option>
				<option value="INACTIVE" >In Active</option>
				<option value="NOT_LOGGEDIN">Not Logged IN</option>
			   </select>
          
          </li>
          
		   <li><a href="javascript:void(0)"><i class="fa fa-check" aria-hidden="true"></i>&nbsp;Role&nbsp;</a>
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
			  <i class="fa fa-user"></i>&nbsp;Emp&nbsp;&nbsp;</a>
				<input type="text" id="searchByName" ><span class="action-search" onclick="getUsersList();"><i class="fa fa-search"></i></span>
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
  </section>
      <!-- Content Area Ends--> 

      <jsp:include page="../masterFooter.jsp" />
      
      
      
<script type="text/javascript">
	function getUsersList(){
		
		var sourceleadUrl = '${sourceleadUrl}';
		var userRole = document.getElementById("userRole").value;
		var searchByName = document.getElementById("searchByName").value;
		var userState = document.getElementById("userState").value;
		$.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getUsersList?${_csrf.parameterName}=${_csrf.token}&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState,
	    			 
            success: function(data) {
	        var json = JSON.parse(data);
	        $('#usersListBody').empty();
	        $('#usersListEmptyBody').empty();
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
	       	    	result = result +"<span> "+userRole.roles.displayName+" </span>";
	       	    	
	       	    	if(userRole.user.isMailSent != null && userRole.user.isMailSent != "" && userRole.user.isMailSent == 'N' && userRole.user.userState != 'INACTIVE'){
	       	    	  result = result +"<br/><span id='user"+userRole.user.id+"'><i class='fa fa-envelope' aria-hidden='true'></i><a href='#'  onClick='resendInvitation("+userRole.user.id+");'>&nbsp;Invite</a></span>";
	 			    }else if((userRole.user.isMailSent != null && userRole.user.isMailSent != "" && userRole.user.isMailSent == 'Y') &&
	 			    		(userRole.user.userState != null && userRole.user.userState != "" && userRole.user.userState == 'NOT_LOGGEDIN') ){
	 			    	result = result +"<br/><span><i class='fa fa-envelope' aria-hidden='true'></i><a href='#' onClick='resendInvitation("+userRole.user.id+");'>&nbsp;Resend</a></span>";
	 			    }else if(userRole.user.userState != null && userRole.user.userState != "" && userRole.user.userState == 'ACTIVE'){
	 			    	result = result +"<br/><span class='label label-success'>Active</span>";
	 			    }else{
	 			    	result = result +"<br/><span class='label label-danger'>Inactive</span>";
	 			    }
	       	    	
	       	    	result = result +"</div>";
	       	    	result = result +"</div>";
	       	    	result = result +"<div class='col-sm-8'>";
	       	    	result = result +"<h4><strong><a href='<c:url value='/userSummary/"+userRole.user.id+"'/>''> "+userRole.user.firstName+" "+userRole.user.lastName+" </a> </strong></h4>";
	       	    	result = result +"<p><label> "+userRole.user.email+" </lable></p>";
	       	    	result = result +"<p><label><i class='fa fa-phone'></i>&nbsp; "+mobilePhone+" </label></p>";
	       	    	result = result +"<p><label>Projects</label><span class='label label-default'>5</span></p>";
	       	    	result = result +"<p><label>Approved Timesheets</label> <span class='label label-success'>20</span></p>";
	       	    	result = result +"<p><label>Rejected Timesheets</label> <span class='label label-danger'>10</span></p>";
	       	    	result = result +"<p><label>Waiting for Timesheets</label> <span class='label label-primary'>10</span> </p>";
	       	    	result = result +"<p><label>Open Timesheets</label> <span class='label label-info'>10</span> </p>";
	       	    	result = result +"</div>";
	       	    	result = result +"<div class='clearfix'></div>";
	       	    	result = result +"</div>";
	       	    	result = result +"</div>";
	       	    	
	       	        
	       	 	    $('#usersListBody').append(result);
	       	 	    $("#searchByName").val("");
				   }
	   		    }
	    	else{
	    		result = "<div class='content-area mt text-center text-muted'><i class='mt fa fa-5x fa-user'></i><h3>Users List is Empty</h3></div>";
	    		$('#usersListBody').empty();
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
			        	$("#user"+userId).html("<i class='fa fa-envelope' aria-hidden='true'></i><a href='#'  onClick='resendInvitation("+userId+");'>&nbsp;Resend</a>");
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

    