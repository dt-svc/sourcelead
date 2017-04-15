
<script src="resources/js/formvalidator.js"></script>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<!-- these files for autocomplete with tagit -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet">
    <link href="resources/css/font-awesome.min.css" rel="stylesheet">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/css/reset.css">
    <link href="resources/css/source-style.css" rel="stylesheet">
    <link href="resources/css/source-responsive.css" rel="stylesheet">
    
    <link rel="stylesheet" href="resources/css/chosen.min.css">
    <link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
    <link href="resources/css/sourcelead-main.css" rel="stylesheet">
    
	<link href="resources/css/main-style.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<!-- Content Area -->
  
  <div class="clear"></div>

<section class="menuGapInpage"></section>


	<!-- <div class="innerPageSearch">
		<section class="searchWrap">
			<span><i class="fa fa-search"></i><input type="text" placeholder="Search" class="searchHome"></span>
			<span><i class="fa fa-map-marker"></i><input type="text" placeholder="Location" class="searchHome"></span>
			<span><i class="fa fa-briefcase"></i>
				<select class="searchHome searchHomeE">
					<option>Exp (Years)</option>
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
					<option>5</option>
					<option>6</option>
				</select>
			</span>
			<span><input type="button" value="Search" class="searchBtn" onclick="window.location.href='job-results.html'"></span>
			<h3><b id="ADS">Advanced Search</b></h3>
			<div class="clear"></div>
		</section>
	</div> -->
	
<section class="innerContent">
	  <div class="jobDetails">
		<div class="jobResultsInn">
			<h3>${requirementDetails.jobTitle}</h3>
			<h4>${requirementDetails.orgLocationId.org.orgName}</h4>
			
			<div class="skills"><strong>Skills :</strong>
				<span>${requirementDetails.skills}</span>
			</div>					
			<span>
			  <b><i class="fa fa-clock-o"></i>${requirementDetails.jobType}</b>
			  <b><i class="fa fa-briefcase"></i>${requirementDetails.minExp} to ${requirementDetails.maxExp}</b>
			  <b><i class="fa fa-map-marker"></i>${requirementDetails.country} ${requirementDetails.state} ${requirementDetails.city}</b>
			</span>		
		</div>			
		<div class="jobDetailsLogo">
			<!-- <span><img src="images/compLogo.jpg" alt=""/></span> -->
			<a href="${sourceleadUrl}/login">Apply</a>
			<c:if test="${requirementDetails.isCrowdSourcing== 'Y'}">
			<a href="${sourceleadUrl}/login" class="btn2">Refer</a>
			</c:if>
			<div class="clear"></div>

			<div class="share mt10">
				<a href="javascript:void(0)">Share</a>
				<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>
				<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>				  
				<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>
				<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>					  	
			</div>			
			
		</div>
		<div class="clear"></div>
		
	<div class="jobDesc">
		<h3>Job Descriptions :</h3>
		<p>${requirementDetails.description}</p> 

		<h4>Other details:</h4>
		<b><i class="fa fa-caret-right"></i>Work Location : </b>	
		<b><i class="fa fa-caret-right"></i><label>No. of positions :</label> ${requirementDetails.positions}</b>
		<b><i class="fa fa-caret-right"></i><label>Education :</label> ${requirementDetails.education}</b>
		<b><i class="fa fa-caret-right"></i><label>Posted :</label>${requirementCreatedDate}</b>
		
		<!-- <div class="jobBtns">
			<a href="http://www.sourcelead.com/sourcelead/">Apply</a>
			<a href="http://www.sourcelead.com/sourcelead/" class="btn2">Refer</a>
		</div> -->

		</div>		
  	
		<div class="jobShare">
			<div class="posted"><i class="fa fa-thumb-tack"></i><label>Posted :</label>${requirementCreatedDate}</div>
			<div class="refer" title="Refer your friend"><i class="fa fa-user" aria-hidden="true"></i></div>
			<div class="share">
				<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>
				<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>				  
				<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>
				<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>					  	
			</div>
			<div class="clear"></div>
		</div>  	
  	
	  </div>
		<div class="clear"></div>		
		
	</section>	


<!-- Content Area Ends-->

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->


