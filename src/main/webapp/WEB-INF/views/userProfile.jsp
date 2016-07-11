<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>


  <!-- Content Area -->
  <!-- Tab for Profile setting-area starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Account Settings</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Account Settings</div>
    </section>
    <div class="content-area clearfix">
      <section class="action-btns clearfix">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
        <div class="row">
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		    <!-- Tab for Profile setting-area -->
		  <div class="accountsettings-tab">
		  <ul class="nav nav-tabs">
			<li class="active"><a data-toggle="tab" href="#profile">Profile</a></li>
			<li><a data-toggle="tab" href="#password">Password</a></li>
		  </ul>

		  <div class="tab-content panel-body">
			<div id="profile" class="tab-pane fade in active">
			  <div class="col-xs-12 col-sm-3 col-md-3 text-center well">
				  <p><strong>${SourceLeadWeb_user.firstName} ${SourceLeadWeb_user.lastName}</strong></p>
				  <div class="profile-image mt mb">
				    <img onerror="predefineProfileImage(this);" src=" " alt="Profile Image" class="img-circle">
				  </div>
				  <div class="btn-group mt">
				  <button type="button" class="btn btn-primary">
				  <i class="fa fa-upload"></i></button>
				  <button type="button" class="btn btn-primary">Upload / Change</button>
				  </div>
			  </div>

			<div class="col-xs-12 col-sm-9 col-md-9">
				  <div class="row mt">

 <form:form class="login-form clearfix"  id="profileSettings_form"  name="profileSettings_form" method="post" >
		<div class="col-md-6">
			<div class="form-group">
			<label class="col-md-4">First Name</label>
			<div class="col-md-8">
			  <input type="text" value="${SourceLeadWeb_user.firstName}" class="form-control" required>
			</div>
			</div>
		   <div class="form-group">
			<label class="col-md-4">Last Name</label>
			<div class="col-md-8">
			  <input type="text" value="${SourceLeadWeb_user.lastName}" class="form-control" required>
			</div>
		   </div>
		  <div class="form-group">
			<label class="col-md-4">Email</label>
			<div class="col-md-8">
			<input type="text" readonly="readonly" value="${SourceLeadWeb_user.email}" class="form-control">
			</div>
		  </div>

		</div>

		<div class="col-md-6">
				  <div class="form-group">
					<label class="col-md-4">Job Title</label>
					<div class="col-md-8">
					  <input type="text" value="${SourceLeadWeb_user.jobTitle}" class="form-control" required>
					</div>
				  </div>
				  <div class="form-group">
					<label class="col-md-4">Phone</label>
					<div class="col-md-8">
					  <input type="text" value="${SourceLeadWeb_user.mobilePhone}" class="form-control" required>
					</div>
				  </div>
				  <div class="form-group">
					<label class="col-md-4">Location</label>
					<div class="col-md-8">
					  <textarea rows="1" value="${SourceLeadWeb_user.location}" class="form-control" required></textarea>
					</div>
				</div>
			</div>
				 <div class="col-md-12">
				 <hr>
					<div class="pull-right">
					  <input type="submit" class="btn btn-primary mr" value="Save">
					  <input type="submit" class="btn btn-default" value="Cancel">
					</div>
				  </div>
	</form:form>			  
				  
	 </div>
			</div>
			</div>
			<div id="password" class="tab-pane fade">
			  <div class="col-xs-12 col-sm-3 col-md-3 text-center well">
				  <div class="password-icon mt mb text-center">
				    <i class="fa fa-lock" aria-hidden="true"></i>
				  </div>
			  </div>
			<div class="col-xs-12 col-sm-9 col-md-9">
				  <div class="row mt">
					<div class="col-md-8">
						<div class="form-group">
						<label class="col-md-4">Curret Password</label>
						<div class="col-md-6">
						  <input type="text" value="" class="form-control">
						</div>
						</div>
						<div class="form-group">
					<label class="col-md-4">New Password</label>
					<div class="col-md-6">
					  <input type="text" value="" class="form-control">
					</div>
				  </div>
				  <div class="form-group">
					<label class="col-md-4">Confirm Password</label>
					<div class="col-md-6">
					<input type="text" value="" class="form-control">
					</div>
				  </div>
					</div>
				 <div class="col-md-7">
				 <hr class="mr">
					<div class="pull-right mr">
					  <input type="button" class="btn btn-primary mr" value="Save">
					  <input type="button" class="btn btn-default" value="Cancel">
					</div>
				  </div>
				 </div>
			</div>
			</div>
		   </div>
		</div>
			<!-- Tab for Profile setting-area ends -->
		  </div>
		  </div>
		  </div>
		  </div>
  </section>
  <!-- Tab for Profile setting-area starts Ends -->
  <!-- Content Area Ends--> 

<jsp:include page="masterFooter.jsp" />
