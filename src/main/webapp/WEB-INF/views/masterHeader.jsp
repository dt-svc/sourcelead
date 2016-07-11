<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Welcome to Source Lead :: The Recruitment Application</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<jsp:include page="masterJs.jsp" />
</head>


<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>

<body>
<div class="wraper"> 
  <!-- Header Area -->
  <header class="main-header">
  <span class="logo"> <a href="javascript:void(0)" title="Source Lead">
  <i>
 <img src="resources/images/logo-sourceld.png" alt="Source Lead"> </i>
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
        <!-- login User details -->
        <div class="login-user">
        <span class="image"> <img class="img-circle" alt="${SourceLeadWeb_userType}" title="${SourceLeadWeb_userName}" onerror="predefineProfileImage(this);"  src=""> </span> <span class="pull-left login-user-xs"> ${SourceLeadWeb_userName} </span>
          <div class="user-details">
            <div class="user-media">
              <div class="logn-image">
              <img class="img-circle" title="${SourceLeadWeb_userName}" alt="${SourceLeadWeb_userType}" onerror="predefineProfileImage(this);" src=""> </div>
              <div class="user-media-body">
                <p><span>${SourceLeadWeb_userName}</span></p>
                <p>${SourceLeadWeb_userType}</p>
              </div>
            </div>
            <ul class="list-unstyled">
              <li><a href="javascript:void(0)"><span><i class="fa fa fa-user"></i></span>&nbsp;View Profile</a></li>
              <li><a href="<c:url value='/logout'/>"><span><i class="fa fa-sign-out"></i></span>&nbsp;Log out</a></li>
            </ul>
          </div>
        </div>
        <!-- login User details -->
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
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                    <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                      1hr ago </span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                    <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                      1hr ago </span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                    <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                      1hr ago </span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                    <div class="msg-time"> <span> <i class="fa fa-clock-o"></i><br>
                      1hr ago </span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img src="resources/images/pratap-bis.jpg" title="Pratap Reddy" alt="Admin" class="img-circle"> </span> </div>
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
                    <div class="msg-img"> <span>
                    <img class="img-circle" alt="Admin" title="Pratap Reddy" src="resources/images/pratap-bis.jpg"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="resources/images/pratap-bis.jpg"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="resources/images/pratap-bis.jpg"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="resources/images/pratap-bis.jpg"> </span> </div>
                    <div class="msg-content">
                      <p>Earlier this week, I was doing some work on a project that was using</p>
                      <span>3 days ago at 7:58 pm - 2.07.2016</span> </div>
                  </div>
                </li>
                <li>
                  <div class="clearfix msg">
                    <div class="msg-img"> <span> <img class="img-circle" alt="Admin" title="Pratap Reddy" src="resources/images/pratap-bis.jpg"> </span> </div>
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
                <li><a href="<c:url value="/login" />"><i class="fa fa-sign-out" aria-hidden="true"></i>Log Out</a></li>
              </ul>
            </div>
          </li>
          <li  class="hidden-xs"> <a href="<c:url value="/login" />" title="log out"><i class="fa fa-power-off"></i></a> </li>
        </ul>
      </div>
    </nav>
  </header>
  <!-- Header Area --> 