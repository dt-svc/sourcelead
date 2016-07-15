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


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">

        <h2>Users List</h2>
      
      <div class="pull-right brud-crum">Home &raquo; General Form &raquo; Users Dashboard</div>

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
				<option value="Administrator" >Administrator</option>
				<option value="User">User</option>
				</select>
		   </li>
		  
		  <li>
			  <a href="javascript:void(0)">
			  <i class="fa fa-user"></i>&nbsp;Emp&nbsp;&nbsp;</a>
				<input type="text" id="searchByName" ><span class="action-search" onclick="getUsersList();"><i class="fa fa-search"></i></span>

          <li><a href="javascript:void(0)"><i class="fa fa-th"></i>&nbsp;All</a></li>
          <li><a href="javascript:void(0)"><i class="fa fa-check" aria-hidden="true"></i>&nbsp;Role&nbsp;</a>
			<select>
				<option>Administrator</option>
				<option>User</option>
				<option>Developer</option>
			</select>
		  </li>
		  <li>
		  <a href="javascript:void(0)">
		  <i class="fa fa-user"></i>&nbsp;Emp&nbsp;&nbsp;</a>
			<span class="action-search"><input type="text" value=""><i class="fa fa-search"></i></span>

		  </li>
		  <li><a href="<c:url value='/userCreation'/>"><i class="fa fa-user-plus"></i>&nbsp;Create</a></li>
		   <li><a href="<c:url value='/uploadForm'/>"><i class="fa fa-upload"></i>&nbsp;Bulk Imports</a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">

         <div class="row">
          <div id="usersListBody">
        </div> 

        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
		  <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
				<div class="user-profile">
            <div class="col-sm-4">
                <div class="text-center image">
                    <img src="resources/images/userprofile-img.jpg" class="img-circle m-t-xs img-responsive" alt="image">
                    <span>Administrtor</span>
                </div>
            </div>
            <div class="col-sm-8">
                <h4><strong>John Smith</strong></h4>
                <p><label>johnsmith@businessintelli.com</lable></p>
                <p><label><i class="fa fa-phone"></i>&nbsp;140-345-367</label></p>
                <p><label>Projects</label><span class="label label-default">5</span></p>
				<p><label>Approved Timesheets</label> <span class="label label-success">20</span></p>
                <p><label>Rejected Timesheets</label> <span class="label label-danger">10</span></p>
                <p><label>Waiting for Timesheets</label> <span class="label label-primary">10</span> </p>
                <p><label>Open Timesheets</label> <span class="label label-info">10</span> </p>
				</div>
            <div class="clearfix"></div>
    </div>
		  </div>
        </div>

      </div>
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
	        
	    	if(json.length > 0)
	   		{
	 			for(var i=0; i<json.length; i++){
		       	 
	 				var userRole = json[i];
	 			    var mobilePhone = '';
	 			    if(userRole.user.mobilePhone != null){
	 			    	mobilePhone = userRole.user.mobilePhone;
	 			    }else{
	 			    	mobilePhone = "NO DATA"
	 			    }
	 				 				
	       	    	var result = "<div class='col-xs-12 col-sm-12 col-md-6 col-lg-4'>";
	       	    	result = result +"<div class='user-profile'>";
	       	    	result = result +"<div class='col-sm-4'>";
	       	    	result = result +"<div class='text-center image'>";
	       	    	result = result +"<img onerror='predefineProfileImage(this);' class='img-circle m-t-xs img-responsive' src='"+sourceleadUrl+"/displayUserImage/"+userRole.user.id+"' alt='image'>";
	       	    	result = result +"<span> "+userRole.userRoleName+" </span>";
	       	    	result = result +"</div>";
	       	    	result = result +"</div>";
	       	    	result = result +"<div class='col-sm-8'>";
	       	    	result = result +"<h4><strong> "+userRole.user.firstName+" "+userRole.user.lastName+"  </strong></h4>";
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
				   }
	   		    } 
	    	
	    	$('#usersListBody').empty(); 
          	$('#usersListBody').append(result);
          	$("#searchByName").val("");

			 },
	        error: function(e){
	        }
	    }); 
	}
</script>


      <!-- Footer Area -->
      <footer class="footer-area clearfix">
        <div class="copyright"> @ Copyright : Source Lead, All right reserved </div>
        <div class="powered-by"> Powered by: businessintelli.com </div>
      </footer>
      <!-- Footer Area --> 

  

    