<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">

<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/chosen.jquery.min.js"></script> 

<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

<jsp:include page="requirementDetails.jsp"></jsp:include>
<jsp:include page="requirementClientDetails.jsp"></jsp:include>
<jsp:include page="requirementInternalDetails.jsp"></jsp:include>


<script type="text/javascript">
		window.onload = function () {
			getAllCountries();
			$(".cd").click(function(){  
		        $(".clientDetails").toggleClass('hidden', 500);
		    });
		 };
</script>

 <script>
		$(document).ready(function() {
			document.getElementById("requirement_form").reset();
		});
	</script>





<!-- <!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Source Lead :: Requirement</title>
Bootstrap
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/bootstrap-select.min.css">
<link rel="stylesheet" href="css/chosen.min.css">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
<link href="css/sourcelead-main.css" rel="stylesheet">
</head>
<body>
<div class="wraper">
Header Area
<header class="main-header"> <span class="logo"> <a href="javascript:void(0)" title="Source Lead"> <i> <img src="images/logo-sourceld.png" alt="Source Lead"> </i>
  <h1>Source Lead</h1>
  </a> </span>
  <nav class="header-nav navbar"> <a href="javascript:void(0)" class="side-menu"> <i class="fa fa-bars pull-left"></i> </a>
    <div class="custom-navbar">
      <div class="search-area">
        <div class="dropdown hidden">
          <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown"> <i class="fa fa-list-ul"></i> <span class="caret"></span></button>
          <ul class="dropdown-menu">
            <li><a href="javascript:void(0)">Candiate</a></li>
            <li><a href="javascript:void(0)">Professional</a></li>
          </ul>
        </div>
        <div class="search-area-txt"> <i class="fa fa-search"></i>
          <input type="text" placeholder="Search ..." class="form-control">
        </div>
      </div>
      login User details
      <div class="login-user"> <span class="image"> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="images/pratap-bis.jpg"> </span> <span class="pull-left login-user-xs"> Pratap Reddy </span>
        <div class="user-details">
          <div class="user-media">
            <div class="logn-image"> <img class="img-circle" title="Pratap Reddy" alt="Admin" src="images/pratap-bis.jpg"> </div>
            <div class="user-media-body">
              <p><span>Pratap Reddy</span></p>
              <p>Admin</p>
            </div>
          </div>
          <ul class="list-unstyled">
            <li><a href="javascript:void(0)"><span><i class="fa fa fa-user"></i></span>&nbsp;View Profile</a></li>
            <li><a href="javascript:void(0)"><span><i class="fa fa-sign-out"></i></span>&nbsp;Log out</a></li>
          </ul>
        </div>
      </div>
      login User details
      <ul class="list-inline list-unstyled pull-left custom-navbar-inner">
        <li class="srch-btn-xs"> <a title="Search"><i class="fa fa-search"></i></a></li>
        <li> <a href="javascript:void(0)" title="Quick Links" id="qlinks"> <i class="fa fa-link"></i> </a>
          <div class="qlinks-area">
            <ul class="list-unstyled">
              <li><a href="javascript:void(0)">Quick link1</a></li>
              <li><a href="javascript:void(0)">Quick link1</a></li>
              <li><a href="javascript:void(0)">Quick link1</a></li>
            </ul>
          </div>
        </li>
        <li> <a href="#" title="Notifications" id="notification"> <i class="fa fa-envelope"></i> <span class="label label-success">4</span> </a>
          <div class="header-widget note-area">
            <ul class="list-unstyled">
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img src="images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                  <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                    1hr ago </span> </div>
                </div>
              </li>
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img src="images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                  <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                    1hr ago </span> </div>
                </div>
              </li>
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img src="images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                  <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                    1hr ago </span> </div>
                </div>
              </li>
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img src="images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                  <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                    1hr ago </span> </div>
                </div>
              </li>
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img src="images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                  <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                    1hr ago </span> </div>
                </div>
              </li>
            </ul>
            <div class="headerwidget-footer"> <a href="javascript:void(0)">View All</a> </div>
          </div>
        </li>
        <li> <a href="#" title="Alerts" id="alert"> <i class="fa fa-bell bell"></i> <span class="label label-warning">4</span></a>
          <div class="header-widget alert-area">
            <ul class="list-unstyled">
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="images/pratap-bis.jpg"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                </div>
              </li>
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="images/pratap-bis.jpg"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                </div>
              </li>
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="images/pratap-bis.jpg"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                </div>
              </li>
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="images/pratap-bis.jpg"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                </div>
              </li>
              <li>
                <div class="clearfix msg">
                  <div class="msg-img"> <span> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="images/pratap-bis.jpg"> </span> </div>
                  <div class="msg-content">
                    <p>Earlier this week, I was doing some work on a project that was using</p>
                    <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                </div>
              </li>
            </ul>
            <div class="headerwidget-footer"> <a href="javascript:void(0)">View All</a> </div>
          </div>
        </li>
        <li> <a href="#" title="Settings" class="settings"> <i class="fa fa-cog fa-spin fa-fw"></i></a>
          <div class="setting-area">
            <ul class="list-unstyled">
              <li><a href="#"><i class="fa fa-key" aria-hidden="true"></i> Change Password</a></li>
              <li><a href="#"><i class="fa fa-eye" aria-hidden="true"></i> View Profile</a></li>
              <li><a href="#"><i class="fa fa-sign-out" aria-hidden="true"></i>Log Out</a></li>
            </ul>
          </div>
        </li>
        <li  class="hidden-xs"> <a href="javascript:void(0)" title="log out"><i class="fa fa-power-off"></i></a> </li>
      </ul>
    </div>
  </nav>
</header>
Header Area 

Side Nav Bar
<aside>
  <div class="side-bar">
    <div class="side-bar-inner">
      <div class="user-panel">
        <div class="media">
          <div class="media-body">
            <div class="image"> <img src="images/compLogo.jpg" alt="Admin" title="admin"> </div>
            <div class="text-logo hidden"> <span>Tech Mahindra - Hyderabad</span> </div>
          </div>
        </div>
      </div>
      <div class="side-nav">
        <ul class="list-unstyled">
          <li><a href="dashboard.html"><span><i class="fa fa-dashboard"></i></span>Dashboard</a></li>
          <li><a href="users-dashboard.html"><span><i class="fa fa-users"></i></span>Users</a></li>
          <li><a href="projects.html"><span><i class="fa fa-bar-chart"></i></span>Projects</a></li>
          <li><a href="client-list.html"><span><i class="fa fa-user-plus"></i></span>Clients</a></li>
          <li> 
            tree-view 
            <a href="#" class="tree-view-new"> <span><i class="fa fa-clock-o"></i></span> Timesheets <i class="fa fa-fw fa-caret-down"></i> </a> <a href="#" class="tree-view"> <span><i class="fa fa-clock-o"></i></span> Timesheets <i class="fa fa-fw fa-caret-down"></i> </a>
            <ul class="collapses">
              <li><a href="timesheet.html">My Timesheet</a></li>
              <li><a href="timesheet-approval.html">Timesheet Approval</a></li>
            </ul>
          </li>
          <li><a href="vendors-list.html" title="Vendors"><span><i class="fa fa-suitcase"></i></span>Vendors</a></li>
          <li><a href="candidate-list.html" title="candiate"><span><i class="fa fa-user"></i></span>Candidate</a></li>
          <li class="side-nav-active"><a href="requirement-list.html" title="candiate"><span><i class="fa fa-check-square"></i></span>Requirement</a></li>
          <li><a href="mailbox.html"><span><i class="fa fa-envelope"></i></span>Mailbox</a></li>
        </ul>
      </div>
    </div>
  </div>
  <ul class="timesheet-menu list-unstyled">
    <li><a href="timesheet.html">My Timesheet</a></li>
    <li><a href="timesheet-approval.html">Timesheet Approval</a></li>
  </ul>
</aside>
Side Nav Bar 
 -->
<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement Details</h2>
    </div>
    <div class="pull-right brud-crum">Home &raquo; Requirement &raquo; Requirement Details</div>
  </section>
  <div class="content-area content-new clearfix">
   <form:form id="requirement_form" name="requirement_form"  enctype='multipart/form-data' >
    <div class="form-horizontal req-dtls myForm row">
      <div class="clearfix">
        <div class="col-lg-12">
          <h3>Add Requirement Details</h3>
        </div>
  <div class="col-md-12 clearfix">
  <div class="col-md-12">
          <div style="margin:0 auto" class="input-group col-md-6">
            <label class="input-group-btn">
            	<span class="btn btn-primary">Select Template
              </span> </label>
            <select class="selectpicker form-control">
                    <option value="w2" disabled>Select Tempalte</option>
                    <option value="C2H">C2H</option>
                    <option value="Part">Part</option>
                    <option value="Time">Time</option>
                    <option value="Other">Other</option>
                  </select>
          </div>
          <div class="text-center clearfix mt text-muted">
          	<div class="clearfix text-center mb">
          <span class="text-info">
          	<strong>
            <b class="hidden-xs">---------------------------------</b><span class="r-circle">OR</span>
            <b class="hidden-xs">------------------------------</b>
            </strong>
            </span>
          </div>
          </div>
</div></div>

          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
           <div class="form-group">
	         <label class="col-md-3">Title</label>
	         <div class="col-md-9">
	          <div class="input-group">
	             <input type="text" id="jobTitle" name="jobTitle" value="${jobTitle}" field-name="Job Title"  data-validation="validate_Space validate_length length2-250" data-validation-optional="true"  value="" class="form-control">
	             <div class="input-group-btn selp-custom-min">
	             <select class="selectpicker" id="designationType" name="designationType">
	                 <option><a href="#">C2C</a></option>
	                 <option><a href="#">W-2</a></option>
	                 <option><a href="#">Part Time</a></option>
	                 <option><a href="#">Full Time</a></option>
	                 <option><a href="#">Other</a></option>
	             </select>
	             </div>
	           </div>
	         </div>
	       </div>
          
          
      
          
          
          <div class="form-group">
            <label class="col-md-3">Id</label>
            <div class="col-md-9">
              <input type="text" class="form-control" id="jobId" name="jobId" value="${jobId}"  field-name="JobId" data-validation="validate_Space validate_length length2-100" data-validation-optional="true"  value="">
            </div>
          </div>
          
          <div class="form-group">
            <label class="col-md-3">No. of Positions</label>
            <div class="col-md-9">
               <input type="text" class="form-control" id="positions" name="positions" value="${positions}"  field-name="Positions" data-validation="validate_Space validate_length length2-10" data-validation-optional="true"  value="">
            </div>
          </div>
          
          <div class="form-group">
              <label class="col-md-3">Education</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control" name="candidateEducation" id="candidateEducation" data-validation="validate_Space" data-validation-optional="true">
                <c:forEach var="edu" items="${educationsList}">
	                     <option value="${edu.name}">${edu.name}</option>
                </c:forEach>
                </select>
              </div>
            </div>
            
          <div class="form-group">
            <label class="col-md-3">Experince</label>
            <div class="col-md-9">
              <div class="row">
                <div class="col-md-6">
                   <input type="text" class="form-control" id="minExp" name="minExp" value="${minExp}"  field-name="MinExp" data-validation="validate_Space validate_length length2-10" data-validation-optional="true"  value="">
                </div>
                <div class="col-md-6">
                  <input type="text" class="form-control" id="maxExp" name="maxExp" value="${maxExp}"  field-name="MinExp" data-validation="validate_Space validate_length length2-10" data-validation-optional="true"  value="">
                </div>
              </div>
            </div>
          </div>
          
          <div class="form-group">
            <label class="col-md-3">Pay Rate/Salary</label>
            <div class="col-md-9">
              <div class="input-group">
                <div class="input-group-btn selp-custom-min">
                  <select class="selectpicker">
                    <option value="c2c">INR</option>
                    <option value="w2">200</option>
                    <option value="c2c">300</option>
                    <option value="c2c">350</option>
                    <option value="c2c">400</option>
                    <option value="c2c">Other</option>
                  </select>
                </div>
                <input type="text" class="form-control" id="rate" name="rate" value="${rate}" data-validation="validate_Space validate_length length2-10" data-validation-optional="true"  value="">
                <div class="input-group-btn selp-custom-min">
                  <select class="selectpicker">
                    <option value="c2c">Per</option>
                    <option value="w2">1hr</option>
                    <option value="c2c">2hr</option>
                    <option value="c2c">3hr</option>
                    <option value="c2c">4hr</option>
                    <option value="c2c">Other</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          
        
          
          <div class="form-group">
				<label class="col-md-3">Country</label>
				<div class="col-md-9">
				 <select class="chosen-select form-control select-bg" name="country" id="country" field-name="Country"  >
                 </select>
				</div>
		</div>
			
          
          <div class="form-group">
				<label class="col-md-3">State</label>
				<div class="col-md-9">
				<select  class="chosen-select form-control select-bg" name="state" id="state" field-name="State"  >
                 </select>
				</div>
		 </div>
		 
		 <div class="form-group">
			<label class="col-md-3">City</label>
			<div class="col-md-9">
			     <select  class="chosen-select form-control select-bg" name="city" id="city" field-name="City"  >
                 </select>
			</div>
		</div>
		
         <div class="form-group">
              <label class="col-md-3">Skills</label>
              <div class="col-md-9">
                <select multiple class="chosen-select form-control" name="candidateSkills" id="candidateSkills" data-validation="validate_Space" data-validation-optional="true">
                <c:forEach var="skill" items="${skillsList}">
	                     <option value="${skill.name}">${skill.name}</option>
                </c:forEach>
                </select>
              </div>
            </div>
         
          <div class="form-group">
            <label class="col-md-3">Attachment</label>
            <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
            <div class="col-md-9">
              <div class="input-group">
                <label class="input-group-btn">
                <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                      <input type="file" style="visibility:hidden;height:0;width:10px;overflow:hidden" multiple id="uploadBtn"  class="upload">
                  </span>
                </label>
                <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">
              </div>
            </div>
            <!-- upload file html need to add bottom JavaScript otherwise it will not work --> 
          </div>
           <div class="form-group">
            <label class="col-md-3">Description</label>
            <div class="col-md-9">
             <textarea rows="2" class="form-control" id="description" name="description" value="${description}" field-name="Description" data-validation="validate_Space validate_length length2-5000" data-validation-optional="true"></textarea>
            </div>
          </div>
        </div>
      </div>
      <div class="clearfix mt">
        <div class="col-xs-12 col-sm-6 col-md-12 col-lg-12">
	      <div class="ml">
            <input type="checkbox" name="cd" class="cd">
            <label>&nbsp;&nbsp;<strong>Is Client Job</strong>
            &nbsp;&nbsp;
            <i class="fa fa-question-circle-o text-info" title="Click the Checkbox"></i></label>
		  </div>
          <div class="clientDetails hidden">
            <div class="clearfix">
              <h3>Client Details</h3>
            </div>
            <div class="clearfix">
              <div class="form-group">
                <label class="col-md-3">Client <span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control select-bg">
                      <option>Select Client</option>
                      <option>Client-1</option>
                      <option>Client-2</option>
                      <option>Client-3</option>
                    </select>
                    <i data-target="#addClient" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i>
                     <div id="addClient" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Client Details</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                            <div class="col-xs-12 col-md-12 col-lg-12">
                              <div class="form-group">
                                <label class="col-lg-3">Client Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Website</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              
                              <div class="form-group">
                                <label class="col-lg-3">First Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Last Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                               <div class="form-group">
                                <label class="col-lg-3">Email</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Role</label>
                                <div class="col-lg-9">
                                <select class="chosen-select form-control select-bg ">
                                 <option disabled>-- Select Role --</option>
                                 <option>Admin</option>
                                 <option>Account Manager</option>
                                 <option>Recriting Manager</option>
                                </select>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="modal-footer">
                           <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                           <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                    </div>
                  <div role="dialog" id="addLevel" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Level</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                            <div class="col-xs-12 col-md-12 col-lg-12">
                              <div class="form-group">
                                <label class="col-lg-3">Level Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Description </label>
                                <div class="col-lg-9">
                                  <textarea class="form-control" rows="2"></textarea>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="modal-footer">
                          <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Client Contact <span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control">
                      <option>Select Client Contact</option>
                      <option>Client Contact-1</option>
                      <option>Client Contact-2</option>
                      <option>Client Contact-3</option>
                    </select>
                    <i data-target="#addContact" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i> </div>
                  <div id="addContact" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Client Contact</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                            <div class="col-xs-12 col-md-12 col-lg-12">
                              <div class="form-group">
                                <label class="col-lg-3">First Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Last Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Email</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Role</label>
                                <div class="col-lg-9">
                                <select class="selectpicker form-control">
                                 <option disabled>-- Select Role --</option>
                                 <option>Admin</option>
                                 <option>Account Manager</option>
                                 <option>Recriting Manager</option>
                                </select>
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3"></label>
                                <div class="col-lg-9">
                                <label class="check-box checkBox">
                                 <input type="checkbox" name="Role"> Send Invitation
                                </label>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="modal-footer">
                          <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Bill Rate / Salary</label>
                <div class="hidden">
                <div class="col-md-9">
                  <div class="input-group">
                    <div class="input-group-btn selp-custom-med">
                      <select class="selectpicker">
                        <option value="c2c">INR</option>
                        <option value="w2">200</option>
                        <option value="c2c">300</option>
                        <option value="c2c">350</option>
                        <option value="c2c">400</option>
                        <option value="c2c">Other</option>
                      </select>
                    </div>
                    <div class="input-group-btn selp-custom-med">
                      <select class="selectpicker">
                        <option value="c2c">Per</option>
                        <option value="w2">1hr</option>
                        <option value="c2c">2hr</option>
                        <option value="c2c">3hr</option>
                        <option value="c2c">4hr</option>
                        <option value="c2c">Other</option>
                      </select>
                    </div>
                    <div class="input-group-btn selp-custom-med">
                      <select class="selectpicker">
                        <option value="c2c">Per</option>
                        <option value="w2">1hr</option>
                        <option value="c2c">2hr</option>
                        <option value="c2c">3hr</option>
                        <option value="c2c">4hr</option>
                        <option value="c2c">Other</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-md-9">
              <div class="input-group">
                <div class="input-group-btn selp-custom-min">
                  <select class="selectpicker">
                    <option value="c2c">INR</option>
                    <option value="w2">200</option>
                    <option value="c2c">300</option>
                    <option value="c2c">350</option>
                    <option value="c2c">400</option>
                    <option value="c2c">Other</option>
                  </select>
                </div>
                <input type="text" class="form-control" value="">
                <div class="input-group-btn selp-custom-min">
                  <select class="selectpicker">
                    <option value="c2c">Per</option>
                    <option value="w2">1hr</option>
                    <option value="c2c">2hr</option>
                    <option value="c2c">3hr</option>
                    <option value="c2c">4hr</option>
                    <option value="c2c">Other</option>
                  </select>
                </div>
              </div>
            </div>
              </div>
              <div class="form-group">
                <label class="col-md-3">Submission Form<span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select class="chosen-select form-control">
                      <option>Select Submission Form</option>
                      <option>Submission Form-1</option>
                      <option>Submission Form-2</option>
                      <option>Submission Form-3</option>
                    </select>
                    <i data-target="#addSubmissonForm" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i> </div>
                  <div id="addSubmissonForm" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Submission Form</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                          <div class="row">
                            <div class="col-xs-12 col-md-12 col-lg-12">
								<div class="clearfix">
                    <div class="form-horizontal clearfix custom-fields">
                      <div class="clearfix mb">
                        <div class="col-md-8">
                          <div class="row">
                            <label class="col-md-3">Title</label>
                            <div class="col-md-8">
                              <input type="text" class="form-control input-sm" value="" placeholder="Title">
                            </div>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <input type="button" class="btn btn-primary btn-sm" value="Add Field">
                        </div>
                      </div>
                      <div class="clearfix mb">
                        <div class="col-md-8">
                          <div class="row">
                            <label class="col-md-3">
								<input type="text" placeholder="Field Name" class="form-control input-sm">
                            </label>
                            <div class="col-md-8">
                              <select class="form-control">
                                <option>Option Value-1</option>
                                <option>Option Value-2</option>
                                <option>Option Value-3</option>
                              </select>
                            </div>
                          </div>
                        </div>
                                                    <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>

                      </div>
                      <div class="form-custom">
                      <div class="form-header clearfix mb col-md-12">
                      <div class="col-sm-3">
                       <label>Field</label>
                      </div>
                      <div class="col-sm-5">
                        <label>Type</label>
                      </div>
                      <div class="col-sm-4">
                         <label>Is Mandator</label>
                      </div>
                      </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Text Field</label>
                              <div class="col-md-8">
                                <input type="text" class="form-control input-sm" value="" placeholder="Text Field">
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>

                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Number Field</label>
                              <div class="col-md-8">
                                <input type="text" class="form-control input-sm" value="" placeholder="Number Field">
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Select Field</label>
                              <div class="col-md-8">
                                <select class="form-control">
                                  <option>Select Value-1</option>
                                  <option>Select Value-2</option>
                                  <option>Select Value-3</option>
                                  <option>Select Value-4</option>
                                </select>
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Browse File</label>
                              <div class="col-md-8">
                                    <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
                                            <div class="input-group">
                                            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                                            <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
                                            </span> </label>
                                            <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">
                                        </div>
                                    <!-- upload file html need to add bottom JavaScript otherwise it will not work --> 
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Radio btn</label>
                              <div class="col-md-8 toggle-button">
                                <label class="switch">
                                <input type="checkbox" checked>
                                <div class="slider round"></div>
                                </label>
                                </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Checkbox</label>
                              <div class="col-md-8 toggle-button">
                                <label class="switch">
                                <input type="checkbox">
                                <div class="slider round"></div>
                                </label>
                                </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Textarea</label>
                              <div class="col-md-8">
                                <textarea rows="1" class="form-control" placeholder="Textarea"></textarea>
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                            </div>
                          </div> </div>
                        </div>
                        <div class="modal-footer">
                          <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="clearfix">
        <div class="col-lg-12">
          <h3>Internal Details</h3>
        </div>
        <div class="col-xs-12 col-sm-6 col-md-12 col-lg-12  myForm">
         <div class="form-group">
            <label class="col-md-3">Manager<span class="text-danger">*</span></label>
            <div class="col-md-9">
            
              <select multiple class="chosen-select form-control select-bg">
                <option>Select Manager</option>
                <option>Manager-1</option>
                <option>Manager-2</option>
                <option>Manager-3</option>
              </select>
            </div>
          </div>
         <div class="form-group">
                <label class="col-md-3">Approver<span class="text-danger">*</span></label>
                <div class="col-md-9">
                  <div class="form-icon">
                    <select multiple class="chosen-select form-control select-bg">
                      <option disabled>Select Client</option>
                      <option>Client-1</option>
                      <option>Client-2</option>
                      <option>Client-3</option>
                    </select>
                    Add Level
                    
                     <div id="addClient" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Client Details</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                            <div class="col-xs-12 col-md-12 col-lg-12">
                              <div class="form-group">
                                <label class="col-lg-3">Client Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Website</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              
                              <div class="form-group">
                                <label class="col-lg-3">First Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Last Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                               <div class="form-group">
                                <label class="col-lg-3">Email</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Role</label>
                                <div class="col-lg-9">
                                <select class="selectpicker form-control">
                                 <option disabled>-- Select Role --</option>
                                 <option>Admin</option>
                                 <option>Account Manager</option>
                                 <option>Recriting Manager</option>
                                </select>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="modal-footer">
                          <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                    </div>
                  <div role="dialog" id="addLevel" class="modal fade">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                          <h4 class="modal-title">Add Level</h4>
                        </div>
                        <div class="modal-body clearfix">
                          <div class="form-horizontal custom-form clearfix">
                            <div class="col-xs-12 col-md-12 col-lg-12">
                              <div class="form-group">
                                <label class="col-lg-3">Level Name</label>
                                <div class="col-lg-9">
                                  <input type="text" class="form-control" value="">
                                </div>
                              </div>
                              <div class="form-group">
                                <label class="col-lg-3">Description </label>
                                <div class="col-lg-9">
                                  <textarea class="form-control" rows="2"></textarea>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="modal-footer">
                          <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
          
          <div class="form-group">
            <label class="col-md-3">Assign To <span class="text-danger">*</span></label>
            <div class="col-md-9">
                <div class="form-icon"> <span class="icon-inside"> <i class="fa fa-search-plus"></i>
                  <input type="text" value="Assing Role" class="form-control" data-target="#assignTeam" data-toggle="modal">
                  </span> <span data-target="#addTeam" data-toggle="modal"> <i class="fa fa-plus btn btn-default formic1"></i> </span> </div>
                <span class="hidden">Select Team</span> 
                <!-- Modal content Assign To -->
                <div role="dialog" id="assignTeam" class="modal fade">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">×</button>
                        <h4 class="modal-title">Assign Role</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal row custom-form">
                        <div class="form-group">
                          <div class="col-xs-12 col-md-8 col-lg-8">
                            <div class="input-group">
                              <input type="search" class="form-control" value="">
                              <span class="input-group-addon bt-primary"><i class="fa fa-search"></i></span> </div>
                          </div>
                          <div class="col-xs-12 col-md-4 col-lg-4">
                             <select class="selectpicker">
                              <option value="All">All</option>
                              <option value="Admin">Admin</option>
                              <option value="Account Manager">Account Manager</option>
                              <option value="Recruiting Manager">Recruiting Manager</option>
                              <option value="Hiring Manager">Hiring Managerr</option>
                              <option value="Teams">Teams</option>
                             </select>
                        </div>
                        </div>
                        <div class="modal-body-custom mt clearfix">
                          <table cols="0" class="table table-bordered table-striped  clearfix">
                            <tbody>
                              <tr>
                                <th><input type="checkbox"></th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td><span class="tbldata-dotdot">Murali Krishna</span></td>
                                <td><span class="tbldata-dotdot" title="muraraikumarmahessrinivas@businessintelli.com">muraraikumarmahessrinivas@businessintelli.com</span></td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control clsDatePicker" id="datepicker5">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                              <tr>
                                <td><input type="checkbox" name="team"></td>
                                <td>Murali Krishna</td>
                                <td>murali@businessintelli.com</td>
                                <td>Senior Recruiter</td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                                <td><div class="input-group"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" name="Date" value="" class="form-control">
                                  </div></td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-primary" type="button">Assign</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div> </div>
                <!-- Assign To modal popup ends -->
                <!-- Modal content Demo Popup: Need to Update -->
                <div role="dialog" id="addTeam" class="modal fade">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button data-dismiss="modal" class="close" type="button">×</button>
                        <h4 class="modal-title">Demo Popup: Need to Update</h4>
                      </div>
                      <div class="modal-body clearfix">
                        <div class="form-horizontal custom-form clearfix">
                          <div class="col-xs-12 col-md-12 col-lg-12">
                            <div class="form-group">
                            <label class="col-lg-3">Frist Name<span class="text-danger">*</span></label>
                            <div class="col-lg-9">
                              <input type="text" class="form-control" value="">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-3">Last Name</label>
                            <div class="col-lg-9">
                              <input type="text" class="form-control" value="">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-3">Email <span class="text-danger">*</span></label>
                            <div class="col-lg-9">
                              <input type="text" class="form-control" value="">
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-3">Invoice Frequency<span class="text-danger">*</span></label>
                            <div class="col-lg-9">
                              <select class="form-control">
                              	<option>Invoice-1</option>
                                <option>Invoice-2</option>
                                <option>Invoice-3</option>
                              </select>
                              </div>
                            </div>
                            <div class="form-group">
                            <label class="col-lg-3"></label>
                            <div class="col-lg-9">
                              <div class="checkbox">
                              <label>
                                <input type="checkbox" value="" checked>
                                Send Invitation</label>
               					 </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Modal content Demo Popup: Need to Update ends --> 
              </div>
          </div>
          <div class="form-group">
            <label class="col-md-3">Priority<span class="text-danger">*</span></label>
            <div class="col-md-9">
              <select class="selectpicker form-control">
                <option>Select Priority</option>
                <option>Priority-1</option>
                <option>Priority-2</option>
                <option>Priority-3</option>
              </select>
            </div>
          </div>
          <div class="form-group hidden">
            <label class="col-md-3">Application Form<span class="text-danger">*</span></label>
            <div class="col-md-9">
              <div class="form-icon">
                <select class="chosen-select form-control">
                  <option>Select Client</option>
                  <option>Client-1</option>
                  <option>Client-2</option>
                  <option>Client-3</option>
                </select>
                <i data-target="#addApplicationForm" data-toggle="modal" class="fa fa-plus btn btn-default formic1-new"></i> </div>
              <div role="dialog" id="addApplicationForm" class="modal fade">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button data-dismiss="modal" class="close" type="button"><i class="fa fa-close"></i></button>
                      <h4 class="modal-title">Application Form</h4>
                    </div>
                    <div class="modal-body clearfix">
                                                <div class="form-horizontal custom-form clearfix">
                          <div class="row">
                            <div class="col-xs-12 col-md-12 col-lg-12">
								<div class="clearfix">
                    <div class="form-horizontal clearfix custom-fields">
                      <div class="clearfix mb">
                        <div class="col-md-8">
                          <div class="row">
                            <label class="col-md-3">Title</label>
                            <div class="col-md-8">
                              <input type="text" class="form-control input-sm" value="" placeholder="Title">
                            </div>
                          </div>
                        </div>
                        <div class="col-md-4">
                          <input type="button" class="btn btn-primary btn-sm" value="Add Field">
                        </div>
                      </div>
                      <div class="clearfix mb">
                        <div class="col-md-8">
                          <div class="row">
                            <label class="col-md-3">
								<input type="text" placeholder="Field Name" class="form-control input-sm">
                            </label>
                            <div class="col-md-8">
                              <select class="form-control">
                                <option>Option Value-1</option>
                                <option>Option Value-2</option>
                                <option>Option Value-3</option>
                              </select>
                            </div>
                          </div>
                        </div>
                                                    <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>

                      </div>
                      <div class="form-custom">
                      <div class="form-header clearfix mb col-md-12">
                      <div class="col-sm-3">
                       <label>Field</label>
                      </div>
                      <div class="col-sm-5">
                        <label>Type</label>
                      </div>
                      <div class="col-sm-4">
                         <label>Is Mandator</label>
                      </div>
                      </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Text Field</label>
                              <div class="col-md-8">
                                <input type="text" class="form-control input-sm" value="" placeholder="Text Field">
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>

                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Number Field</label>
                              <div class="col-md-8">
                                <input type="text" class="form-control input-sm" value="" placeholder="Number Field">
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Select Field</label>
                              <div class="col-md-8">
                                <select class="form-control">
                                  <option>Select Value-1</option>
                                  <option>Select Value-2</option>
                                  <option>Select Value-3</option>
                                  <option>Select Value-4</option>
                                </select>
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Browse File</label>
                              <div class="col-md-8">
                                    <!-- upload file html need to add bottom JavaScript otherwise it will not work -->
                                            <div class="input-group">
                                            <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
                                            <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
                                            </span> </label>
                                            <input type="text" class="form-control" readonly id="uploadFile" placeholder="Files are not selected...">
                                        </div>
                                    <!-- upload file html need to add bottom JavaScript otherwise it will not work --> 
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Radio btn</label>
                              <div class="col-md-8 toggle-button">
                                <label class="switch">
                                <input type="checkbox" checked>
                                <div class="slider round"></div>
                                </label>
                                </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Checkbox</label>
                              <div class="col-md-8 toggle-button">
                                <label class="switch">
                                <input type="checkbox">
                                <div class="slider round"></div>
                                </label>
                                </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox">
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                        <div class="clearfix mb">
                          <div class="col-md-8">
                            <div class="row">
                              <label class="col-md-3">Textarea</label>
                              <div class="col-md-8">
                                <textarea rows="1" class="form-control" placeholder="Textarea"></textarea>
                              </div>
                            </div>
                          </div>
                            <div class="col-md-4 toggle-button">
                                <label class="switch">
                                    <input type="checkbox" checked>
                                    <div class="slider round"></div>
                                </label>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                            </div>
                          </div> </div>
                    </div>
                    <div class="modal-footer">
                      <button data-dismiss="modal" class="btn btn-primary" type="button">Save</button>
                      <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </form:form>
  <div class="form-footer clearfix">
    <div class="col-lg-12">
      <div class="pull-right">
        <input type="button" value="Release" class="btn btn-danger">
        <input type="button" value="Save" id="store_requirement" onclick="storeRequirement();" class="btn btn-primary">
        <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/requirementList'/>'" class="btn btn-default ml">
      </div>
    </div>
  </div>
  
  <!-- Content Area Ends-->
  </div>
</section>

<script type="text/javascript">
	function storeRequirement() {
		
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		
		  if ($('#requirement_form').validate(false, validationSettings)){  
			 alert("saasa");
			var frm = $('#requirement_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			
			var form = $('#requirement_form')[0];
      		var formData = new FormData(form); 
			  //It will escape all the special characters
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeRequirement?${_csrf.parameterName}=${_csrf.token}&json="+con,
						success : function(result) {
								location.replace(sourceleadUrl+"/requirementList");
						},
					});
			return true;
		  }else{ 
				return false;
		 }  
	} 
</script>

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<!-- Country,State,City Script Starts Here -->

<script type="text/javascript">
function getAllCountries(){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').empty();
        	$('#country').append('<option value="">---select---</option>');
            for(var i=0;i<json.length;i++){
            	if(json[i] == '${loginCountryName}'){
            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}
            	else{
            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            	
            }
            $('#country').trigger("chosen:updated");
            getStates();
        },
        error: function(e){
        }
    });
} 
</script>


<script>
function getStates(){
	var countryName = $("#country").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>



<!-- Country,State,City Script Ends Here -->


<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			
		  $("#country").chosen();
	       $("#state").chosen();
	       $("#city").chosen();
	       
	       $("#country").on('change', function (e) {
	    	   getStates();
	       });
	       $("#state").on('change', function (e) {
	    	   getCities();
	       });
		       
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
			var $this = $(this);
			$this.next().css({'width': '100%'});
			})
				
				
</script> 
<!-- Eductions and Skills Autocomplete Scripts End Here -->


<!-- </body>
</html> -->
<jsp:include page="../masterFooter.jsp" />