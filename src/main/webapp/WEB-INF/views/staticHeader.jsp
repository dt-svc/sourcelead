<!DOCTYPE html>
<html lang="en">
<head>
<title>${title}</title>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="google-site-verification" content="lRRPwOWi0M3yAREOGoDPAjQ4cj4otBXHl_uTWJd93rI" />
<meta name="msvalidate.01" content="D6B1571D75BB0A86C42A01713002F6FF" />
<meta name="p:domain_verify" content="e1f8f19c0866ae573868239c1f7893bc"/>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no, user-scalable=no">
<meta property='og:title' content="sourcelead.com"/> 
<meta property='og:url' content="https://www.sourcelead.com/"/> 
<meta property='og:description' content='Sourcelead is a global integrated recruitment solution for job seekers and the employers. We carry our expertise for more than a decade, and have expanded to become a global provider of full array of recruitment, vendor management, billing, and client management products and services.'/> 
<meta property="og:type" content="article" /> 
<meta property="og:image" content="https://www.sourcelead.com/resources/images/fav.png"/> 
<meta name="description" content="${description}">
<meta name="keywords" content="${keywords}">
<meta name="robots" content="index, follow">
	
	<link rel="stylesheet" href="/resources/css/bootstrap-select.min.css">
	<link href="/resources/css/jquery-ui.css" rel="stylesheet">
	<link rel="stylesheet" href="/resources/css/jquery.timepicker.css">
	
	<link rel="stylesheet" href="/resources/static-web/css/prettyPhoto.css" type="text/css"/>
	<link rel="stylesheet" href="/resources/static-web/css/owl.carousel.min.css">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link href="/resources/static-web/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="/resources/static-web/css/animate.css" rel="stylesheet">
	<link href="/resources/static-web/css/bootstrap.min.css" rel="stylesheet">
	<link href="/resources/static-web/css/slwebsite.css" rel="stylesheet">
	<link href="/resources/static-web/css/slwebsite-res.css" rel="stylesheet">
	<link rel="icon" type="image/png" href="/resources/images/fav.png" />
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- <script src="/resources/js/jquery-2.1.4.min.js"></script> --> 
    <!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-115098165-1"></script>
<script>
window.dataLayer = window.dataLayer || [];
function gtag(){dataLayer.push(arguments);}
gtag('js', new Date());

gtag('config', 'UA-115098165-1');
</script>
</head>
<body>
<div class="marQ">
        <marquee behavior="scroll" direction="left">
        <p id="maintenanceStatus"></p>
        </marquee>
    </div>
<nav class="navbar navbar-default customNavBar" id="navHeader-scroll">
	  <div class="container-fluid">
		
	    <div class="navbar-header">
		  <a class="navbar-brand" href="/"><img src="/resources/images/SourceLead-logo.png"></a>
		</div>

		<!-- Mobile Nav -->
		<div onClick="mobileNavfun()" style="float:right" id="mobileNav">
			<div class="bar1"></div>
			<div class="bar2"></div>
			<div class="bar3"></div>
		</div>
		<!-- Mobile Nav -->

	<div class="customNavBar-inner deskMenu">
	<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
	<ul class="">
	<li><a href="/" id="indexpageID">Home</a></li>
	 <li class="dropdown dropdown-featureshome">
	 <a data-target="#featureshome" href="javascript:void(0)" id="featuresID">Features
			  <span class="fa fa-angle-down"></span></a>
			<ul id="featureshome" class="collapse list-unstyled dropdownmenu-custom dropdownmenucustom_defualt">
<!--
				<li><a href="features.html">Features</a></li>
-->
				<li><a href="/ats.html" id="atsID">ATS</a></li>
				<li><a href="/vms.html" id="vmsID">VMS</a></li>
				<li><a href="/crms.html" id="crmID">CRM</a></li>
				<li><a href="/pms.html" id="pmsID">PMS</a></li>
				<li><a href="/hrms.html" id="hrmsID">HRMS/Contracts</a></li>
				<li><a href="/ims.html" id="imsID">IMS</a></li>
				<li><a href="/immigration.html" id="immigrationID">Immigration</a></li>
				<li><a href="/mergers-acquisition.html" id="maId">Mergers &amp; Acquisition</a></li>
			</ul>
	</li>
	<li><a href="/search-jobs.html" id="searchjobID">Search Job</a></li>
	<li><a href="/market-place.html" id="marketplaceID">Marketplace</a></li>
	<li><a href="/about-us.html" id="aboutusID">About Us</a></li>
	<!--<li><a href="helpManuals.html">Help</a></li>-->

	<!-- <li><a href="/blog/all.html" id="blogID">Blog</a></li> -->

	<li class="dropdown dropdown-featureshome">
	 <a data-target="#featureshome" href="javascript:void(0)" id="resourcesID">Resources
			  <span class="fa fa-angle-down"></span></a>
			<ul id="featureshome" class="collapse list-unstyled dropdownmenu-custom dropdownmenucustom_defualt">
				<li><a class="helpbtn" href="/help.html">Help Manuals</a></li>
				<li><a href="/blog/all.html" id="blogID">Blog</a></li>
				<li><a href="/blog/allReleaseNote.html" id="releaseNoteID">Release Note</a></li>
			</ul>
	</li>

	<li><a href="/contact.html" id="contactID">Contact</a></li>
	<!-- <li><a href="/register" title="Login">Sign Up</a></li> -->
		 <li><a class="loginbtn" href="/login" title="Login">Login</a></li>
	
	</ul>
		</div>
		
	  </div>
	</nav>
