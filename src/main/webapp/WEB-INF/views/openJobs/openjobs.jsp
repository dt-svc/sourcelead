<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${staticTime == null || staticTime == 0 }">
	<c:set var='staticTime' value="<%=new java.util.Date().getTime()%>"
		scope="application"></c:set>
</c:if>

<!-- <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet"> -->
<link href="/resources/css/login.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/resources/css/chosen.min.css?staticTime=${staticTime}">
<link
	href="/resources/css/font-awesome.min.css?staticTime=${staticTime}"
	rel="stylesheet" type="text/css">
<%-- <link rel="stylesheet"
	href="/resources/css/static-web/reset.css?staticTime=${staticTime}"> --%>
<link
	href="/resources/css/jobs/css/source-style.css?staticTime=${staticTime}"
	rel="stylesheet">
<link
	href="/resources/css/jobs/css/source-responsive.css?staticTime=${staticTime}"
	rel="stylesheet">
<link rel="stylesheet"
	href="/resources/css/jobs/css/chosen.min.css?staticTime=${staticTime}">
<link rel="stylesheet"
	href="/resources/css/jobs/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link rel="stylesheet"
	href="/resources/css/jobs/css/sourcelead-main.css?staticTime=${staticTime}">
<link
	href="/resources/css/jobs/css/main-style.css?staticTime=${staticTime}"
	rel="stylesheet">
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}"
	rel="stylesheet" type="text/css">
<link
	href="/resources/css/dataTablePagination.css?staticTime=${staticTime}"
	rel="stylesheet" type="text/css">
<link href="/resources/css/jquery-ui.css?staticTime=${staticTime}"
	rel="stylesheet">
<link rel="stylesheet"
	href="/resources/css/jquery.timepicker.css?staticTime=${staticTime}">
<link rel="stylesheet"
	href="/resources/css/timesheet.css?staticTime=${staticTime}"
	type="text/css">
<style type="text/css">
.jobDesc b {
display: inline-block;
}
</style>
<script
	src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=${SourceLeadWeb_googleMapsLocationApiKey}"
	type="text/javascript"></script>
<script
	src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-fun.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/jquery.timepicker.js?staticTime=${staticTime}"></script>
<script src="/resources/js/themeswitcher.js?staticTime=${staticTime}"></script>
<script type="text/javascript"
	src="/resources/js/static-web/modernizr.custom.86080.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/controls.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/static-web/jquery-1.12.4.min.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/static-web/jquery.sldr.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/static-web/masonry.pkgd.min.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/jobs/js/bootstrap.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/jobs/js/bootstrap-select.min.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/jobs/js/jquery-1.11.3.min.js?staticTime=${staticTime}"></script>
<script
	src="/resources/js/jobs/js/chosen.jquery.min.js?staticTime=${staticTime}"></script>
<!-- <script src="/resources/staic-web/js/customscripts.js" type="text/javascript"></script> -->
<div class="location-alert alert alert-danger alert-dismissable zInPop" id="locationAlert">
    <a href="#" class="loc-alertclose close">&times;</a>
    <strong>Alert !</strong> <span>Your are not able to Save Required data is Missing.</span>
  </div>
 <title>.:: Jobs ::.</title>
 <style>
 .navbar-default {
 background: #fff !important; border-bottom: 1px solid #ededed !important;
 }
 </style>
<!--  Modal Pop for apply Jobs -->
  <!-- Modal -->
  <div class="comMask"></div>
  <div class="modal modalApplyjob" id="applyModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <div id="gustLoginId"></div>
          <button type="button" class="close" onclick="closeApplyJob();">&times;</button>
        </div>
        <div class="modal-body">
          <div class="clearfix"></div>
          <div class="clearfix mt" id="signinDiv">
          	<jsp:include page="../SignIn.jsp"></jsp:include>
          </div>
          
          <div class="clearfix mt" id="forgotPasswordDiv" style="display:none">
          	<jsp:include page="../forgotPasswords.jsp"></jsp:include>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" onclick="closeApplyJob();">Close</button>
        </div>
      </div>
      
    </div>
  </div>

<!--  Modal Ends -->
<script src="/resources/js/chosen.jquery.min.js?staticTime=${staticTime}"></script>
<script type="text/javascript">

window.onload = function () {
	if('${requirementUUID}' == null && '${requirementUUID}' == '' && '${requirementUUID}' == 'undefined'){
	$("#noResultsDiv").css("display", "block");
	}
	setTimeout(function() {
		$(".pageLoad").fadeOut();
	}, 1000);
	if('${requirementUUID}' != null && '${requirementUUID}' != '' && '${requirementUUID}' != 'undefined'){
		 if('${requirementReleaseStatus}' == 'Y'){
			 $("#filterDiv").hide();
			 $(".jobResults").addClass("jobResults_New");
			getOpenJobsRequirementSummary('${requirementUUID}');
			getDropDownCount();
		}else{
			$("#noResultsDiv").css("display", "block");
		} 
		/* getOpenJobsRequirementSummary('${requirementUUID}');
		getDropDownCount(); */
	}else{
		getDropDownCount();
		getOpenJobsELKList('','manual');
		
	}
	if('${companies}' != null && '${companies}' != '' && '${companies}' != 'undefined'){
		$("#orgList").show();
		$("#selectedOrg").html('${postedOrgName}');
		$("#companiesListDiv").find("i").toggleClass("arrAc");
		getOrgsList('none','main');
	}
 };
 $(".loc-alertclose").click(function(){
	 $(".location-alert").fadeOut();
})	
</script>



<script>


$(document).scroll(function(e){
    // grab the scroll amount and the window height
  
    var scrollAmount = $(window).scrollTop();
    var documentHeight = $(document).height();
    var searchType = $("#searchType").val();
//     offset = $("#orgOffset").val();
    // calculate the percentage the user has scrolled down the page
    if($(window).scrollTop() == $(document).height() - $(window).height()) {
    	var avilableCount = $("#openJobsOffset").val();
    	avilableCount = parseInt(avilableCount) + parseInt(10); 
    	$("#openJobsOffset").val(avilableCount);
    	 //getConnectionsList("scroll");
    	 //requirementsFilter("scroll");
	if(('${requirementUUID}' == null || '${requirementUUID}' == '' || '${requirementUUID}' == 'undefined') &&(searchType !='advancesearch')){
    	 getOpenJobsELKList('',"scroll")
	}
	else if(searchType == 'mainsearch'){
		getOpenJobsELKList('',"scroll")
	}
    }
    
});
</script>



<script>
 function socialPostSucess(service,status){
	 msgFadein();
     $("#msgDiv").html(" Job is Successfully Posted on Your "+service+" Wall.");
 }
</script>

<script type="text/javascript">
var postdatavalues = { title: "", link: "", picture: "", description: "", comment: "" };
function setPostValues(title,link,picture,description,comment){
	 if (title) {
         postdatavalues.title = title;
     }
     if (link) {
         postdatavalues.link = link;
     }
     if (picture) {
         postdatavalues.picture = picture;
     }
     if (description) {
         postdatavalues.description = description;
     }
     if (description) {
         postdatavalues.comment = description;
     }
};

function getPostValueDetails(){
	 return postdatavalues;
  }

</script>

<script>
	function msgFadein() {
		$("#selector").fadeIn(200);
        $('#selector').delay(2000).fadeOut('slow');
	}
</script>
<style>
.footer-area {
	display: none;
}

a.alertBtn {
	padding: 7px 15px !important; float: left; margin: 0px 5px 0px 0px;
}

.ratetype p {
	left: -45px !important;
	position: absolute;
	bottom: -14px;
}

.adSearch .chosen-container .search-field input[type="text"] {
	width: 100% !important;
}

.jobResultsCta.job_container {
	cursor: inherit !important;
}

.jobResultsCta.job_container h3 {
	cursor: pointer;
	font-size: 18px;
	width: auto;
	clear: both;
	float: left;
}

.jobResultsCta.job_container h4 {
	cursor: pointer;
	font-size: 16px;
	width: auto;
	clear: both;
	float: left;
}

.jobDetails .jobResultsInn h3 {
	cursor: pointer;
	font-size: 18px;
	width: auto;
	clear: both;
	float: left;
}

.jobResultsInn.job_ResultsInn h3 {
	cursor: inherit;
	font-size: 18px;
	width: auto;
	clear: both;
	float: left;
}

.jobResultsInn.job_ResultsInn h4 {
	cursor: pointer;
	font-size: 16px;
	width: auto;
	clear: both;
	float: left;
}

.profilerefresh-icon {
	position: absolute;
	right: 16px;
	top: 1px;
	height: 28px;
	line-height: 14px;
}

.pac-container {
	z-index: 1000000000 !important;
}

.pac-container::after {
	display: none;
}
.jobResults_New {
	float: none!important; width: 100%;
}
.jobResults_New .jobDetails {
	width: 100% !important;
}
.jobResults_New .innerContent {
	padding: 0px 0px 30px !important;
}
.modal {
	z-index: 10500 !important;
}
.ref-top{
top: 21px;height: 32px; line-height: 18px;
}
</style>


<!-- Content Area -->
<jsp:include page="../staticHeader.jsp" />
<div class="innerPageSearch" style="padding-top: 75px;"> 
<c:if test="${!empty guestusermsg}">
	        <div class="success_pop" id="guestselector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${guestusermsg}</span>
			</div>
	     </c:if>
	<section class="searchWrap" id="orgProfile" style="display:block">
		<span><i class="fa fa-search"></i><input type="text"
			placeholder="Search" id="openJobsRequirementTitleId"
			value="${openJobsTitle==null?'':openJobsTitle}" class="searchHome"></span>
		<span><i class="fa fa-map-marker"></i><input type="text"
			placeholder="Location" value="${openJobsLocation}"
			id="openJobsLocationId" class="searchHome"></span> 
			<span><i class="fa fa-briefcase"></i>
			<p id="dropDownCountId"></p>
			<%-- <select class="searchHome searchHomeE" id="openJobsExperienceId">
			<option value="">--Select Experience--</option>
				<c:forEach var="exp" items="${totalexperience}">
					<option value="${exp}">${exp}</option>
				</c:forEach>
			</select> --%>
			</span> 
			
			<span><input type="button"
			value="Search" class="searchBtn"
			onclick="getOpenJobsELKListJOBS();"></span>
		<h3>
			<b id="ADS">Advanced Search</b>
		</h3>
		<div class="clear"></div>
		
	</section>
	
</div>

<section class="compaBann" id="orgImage" style="display:none">
	<span><img src="/resources/images/company-banner.jpg" alt=""></span>
	<div class="compaBannLogo" style="background-image: url(/resources/images/company-logo.jpg)"></div>
	<div class="compaBannLogoText"><b id="selectedOrg"></b></div>
</section>

<section class="innerContent">
	<input type="hidden" name="openJobsOffset" id="openJobsOffset"
		value="0" />
	<div class="filter" id="filterDiv">
		<h2>
			Filter your search <i class="fa fa-refresh" aria-hidden="true"
				onclick="clearFilters(); getOpenJobsELKList('','manual');"
				style="cursor: pointer;"></i>
		</h2>
		<div class="filterCta" id="dateFilterId">
			<h3>
				Posted on<i class="fa fa-caret-down arrAc"></i>
			</h3>
			<ul id="show">
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">Today <input
						type="radio" name="dateFilter" value="today"
						${postedOn=="today"?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">1 day ago <input
						type="radio" name="dateFilter" value="1 Day Ago"
						${postedOn=="1-day-ago"?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">3 days ago <input
						type="radio" name="dateFilter" value="3 Days Ago"
						${postedOn=="3-days-ago"?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">1 Week ago <input
						type="radio" name="dateFilter" value="1 Week Ago"
						${postedOn=="1-week-ago"?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">2 Weeks ago <input
						type="radio" name="dateFilter" value="2 Weeks Ago"
						${postedOn=="2-weeks-ago"?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">1 Month ago <input
						type="radio" name="dateFilter" value="1 Month Ago"
						${postedOn=="1-month-ago"?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">3 Months ago <input
						type="radio" name="dateFilter" value="3 Months Ago"
						${postedOn=="3-months-ago"?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator_ra"></div>
				</label></li>
			</ul>
		</div>


		<div class="filterCta" id="countryFilterId">
			<h3>
				Location <i class="fa fa-caret-down"></i>
			</h3>
			<ul>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">Australia <input
						type="radio" name="countryFilter" value="Australia"
						${locationType=="australia"?"checked=checked":""}
						onclick="requirementsFilter(); getOrgsList('none','main');" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">France <input
						type="radio" name="countryFilter" value="France"
						${locationType=="france"?"checked=checked":""}
						onclick="requirementsFilter(); getOrgsList('none','main');" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">India <input
						type="radio" name="countryFilter" value="India"
						${locationType=="india"?"checked=checked":""}
						onclick="requirementsFilter(); getOrgsList('none','main');" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">Singapore <input
						type="radio" name="countryFilter" value="Singapore"
						${locationType=="singapore"?"checked=checked":""}
						onclick="requirementsFilter(); getOrgsList('none','main');" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">United Kingdom <input
						type="radio" name="countryFilter" value="United Kingdom"
						${locationType=="united-kingdom"?"checked=checked":""}
						onclick="requirementsFilter(); getOrgsList('none','main');" />
						<div class="control__indicator_ra"></div>
				</label></li>
				<li class="control-group"><label
					class="control-radio control--checkbox-ra">United States <input
						type="radio" name="countryFilter" value="United States"
						${locationType=="united-states"?"checked=checked":""}
						onclick="requirementsFilter(); getOrgsList('none','main');" />
						<div class="control__indicator_ra"></div>
				</label></li>
			</ul>
		</div>

		<div class="filterCta" id="empTypediv">
			<h3>
				Employment Type<i class="fa fa-caret-down"></i>
			</h3>
			<ul>
				<li class="control-group"><label
					class="control control--checkbox">C2C <input
						type="checkbox" name="typeFilter" value="C2C"
						${employementType!=null && employementType!="" && employementType.indexOf("c2c")!=-1?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator"></div>
				</label></li>
				<li class="control-group"><label
					class="control control--checkbox">W-2 <input
						type="checkbox" name="typeFilter" value="W-2"
						${employementType!=null && employementType!="" && employementType.indexOf("w-2")!=-1?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator"></div>
				</label></li>
				<li class="control-group"><label
					class="control control--checkbox">C2H <input
						type="checkbox" name="typeFilter" value="C2H"
						${employementType!=null && employementType!="" && employementType.indexOf("c2h")!=-1?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator"></div>
				</label></li>
				<li class="control-group"><label
					class="control control--checkbox">Contract <input
						type="checkbox" name="typeFilter" value="Contract"
						${employementType!=null && employementType!="" && employementType.indexOf("contract")!=-1?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator"></div>
				</label></li>
				<li class="control-group"><label
					class="control control--checkbox">Part Time <input
						type="checkbox" name="typeFilter" value="Part Time"
						${employementType!=null && employementType!="" && employementType.indexOf("part-time")!=-1?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator"></div>
				</label></li>
				<li class="control-group"><label
					class="control control--checkbox">Full Time <input
						type="checkbox" name="typeFilter" value="Full Time"
						${employementType!=null && employementType!="" && employementType.indexOf("full-time")!=-1?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator"></div>
				</label></li>
				<li class="control-group"><label
					class="control control--checkbox">Other <input
						type="checkbox" name="typeFilter" value="Other"
						${employementType!=null && employementType!="" && employementType.indexOf("other")!=-1?"checked=checked":""}
						onclick="requirementsFilter();" />
						<div class="control__indicator"></div>
				</label></li>
			</ul>
		</div>

		<div id="companiesListDiv" class="filterCta">
				<h3 onclick="getOrgsList('none','main');">Companies<i class="fa fa-caret-down"></i></h3>
				<ul id="orgList">
				</ul>
			</div>	

		<!-- <div class="filLoad">
				<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>
				<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>
				<h2 class="gradi"></h2>
			</div> -->

	</div>

	<div class="jobResults">
	
	
				<!-- <div class="jobResultsHead" id="jobResultHeadId" style="display:none">
							<p>Show results of 10 of 100</p>
							<span>
								<a class="act" href="javascript:void(0)">Relevance</a>
								<a href="javascript:void(0)">Freshness</a>
							</span>
							<div class="clear"></div>
						<div class="filLoad filLoad_a" style="display: none;"><h2 class="gradi"></h2></div>
				</div> -->
		<!--		
			<div class="fliterHead">
				<div>
					<span>Fliter 1 <i class="fa fa-remove"></i></span>
					<span>Fliter 2 <i class="fa fa-remove"></i></span>
					<span>Fliter 3 <i class="fa fa-remove"></i></span>
					<span>Fliter 4 <i class="fa fa-remove"></i></span>
					<span>Fliter 5 <i class="fa fa-remove"></i></span>
					<span>Fliter 6 <i class="fa fa-remove"></i></span>
					<span>Fliter 7 <i class="fa fa-remove"></i></span>
					<span>Fliter 8 <i class="fa fa-remove"></i></span>
				</div>
			</div>-->

		<!--			<div class="jobResultsHead">
				<p>Show results of 10 of 100</p>
				<span>
					<a class="act" href="javascript:void(0)">Relevance</a>
					<a href="javascript:void(0)">Freshness</a>
				</span>
				<div class="clear"></div>
				
			<div class="filLoad filLoad_a"><h2 class="gradi"></h2></div>
				
			</div>-->
		<!-- 			<div class="noResults" id="noResultsDiv" style="display: none;"></div> -->
		<div id="loadinggif"></div>
		<div id="openJobsRequirementsList"></div>
		<div class="jobResultsNoRecords" id="noResultsDiv"></div>
		<div id="loadMoreDiv"></div>
		<div class="loadJob" onClick="loadMoreReq();" id="loadMoreReq"
			style="display: none; cursor: pointer;">View more</div>
		<div id="openJobsRequirementsSummary"></div>



	</div>
	<div class="clear"></div>

	<!-- for popup -->

	<div class="filPop">
		<div class="filPopHead clearfix">
			<span>Companies</span>
			<!-- 				<span><input type="text" placeholder="Search" class="filSearch"></span> -->
			<!-- <span class="selectCountry">
					<select class="chosen-select" data-placeholder="Employers" id="orgCountry" onchange="getOrgsList('none','sub');">
					  <option value="">--Select Country--</option>
					  <option value="Australia">Australia</option>
	                  <option value="France">France</option>
	                  <option value="India">India</option>
	                  <option value="Singapore">Singapore</option>
	                  <option value="United Kingdom">United Kingdom</option>
	                  <option value="United States">United States</option>
					</select>			
				</span> -->
			<i class="fa fa-times filPopClose CP" onclick="clearcheck();"></i>
		</div>
		<div class="filPopInn">

			<div class="LettSelec" id="selectLetter">
				<a id="all" onclick="getOrgsList('none','sub');">ALL</a> <a
					onclick="getOrgsList('A','sub');">A</a> <a
					onclick="getOrgsList('B','sub');">B</a> <a
					onclick="getOrgsList('C','sub');">C</a> <a
					onclick="getOrgsList('D','sub');">D</a> <a
					onclick="getOrgsList('E','sub');">E</a> <a
					onclick="getOrgsList('F','sub');">F</a> <a
					onclick="getOrgsList('G','sub');">G</a> <a
					onclick="getOrgsList('H','sub');">H</a> <a
					onclick="getOrgsList('I','sub');">I</a> <a
					onclick="getOrgsList('J','sub');">J</a> <a
					onclick="getOrgsList('K','sub');">K</a> <a
					onclick="getOrgsList('L','sub');">L</a> <a
					onclick="getOrgsList('M','sub');">M</a> <a
					onclick="getOrgsList('N','sub');">N</a> <a
					onclick="getOrgsList('O','sub');">O</a> <a
					onclick="getOrgsList('P','sub');">P</a> <a
					onclick="getOrgsList('Q','sub');">Q</a> <a
					onclick="getOrgsList('R','sub');">R</a> <a
					onclick="getOrgsList('S','sub');">S</a> <a
					onclick="getOrgsList('T','sub');">T</a> <a
					onclick="getOrgsList('U','sub');">U</a> <a
					onclick="getOrgsList('V','sub');">V</a> <a
					onclick="getOrgsList('W','sub');">W</a> <a
					onclick="getOrgsList('X','sub');">X</a> <a
					onclick="getOrgsList('Y','sub');">Y</a> <a
					onclick="getOrgsList('Z','sub');">Z</a> <a
					onclick="getOrgsList('Others','sub');">Others</a>
			</div>

			<ul id="remainingOrgs" class="clearfix">
			</ul>
		</div>
		<div class="filPopBot clearfix">
			<div class="pull-right">
				<!-- 					<input type="button" class="btn btn-danger CP" value="Cancel"/> -->
				<input type="button" class="btn btn-primary" onclick="clearcheck();"
					value="Clear All" /> <input type="button"
					class="btn btn-success CP" onclick="requirementsFilter();"
					value="Search" />
			</div>
		</div>
	</div>

</section>
<section class="asMask" onclick="adSearchClose();"></section>


<section class="adSearch">

	<form:form id="openJobsAdvancedForm" name="openJobsAdvancedForm"
		enctype='multipart/form-data'>
		<div class="cand-advsrch-popup">
			<a href="javascript:void(0)" class="close-div"
				onClick="adSearchClose();loadOpenJobsChosenRequirementScripts();"><i
				class="fa fa-close"></i></a>
			<div class="row">
				<div class="col-md-8">
					<label>Keyword</label>
						<div class="clearfix">
						<select class="chosen-select form-control input-sm" multiple id="anyKeyWord" name="anyKeyWord"></select>
						</div>
				</div>
				<div class="col-md-4">
						<label>Experience</label>
						<div class="clearfix row">
							<div class="col-md-6">
								<select class="chosen-select" id="minExp" name="minExp"
									data-placeholder="Min Years">
									<option value="" selected>Min Years</option>
									<c:forEach begin="0" end="24" varStatus="loop">
										<option value="${loop.index}">${loop.index}Years</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-6">
								<select class="chosen-select" id="maxExp" name="maxExp"
									data-placeholder="Max Years">
									<option value="" selected>Max Years</option>
									<c:forEach begin="0" end="24" varStatus="loop">
										<option value="${loop.index}">${loop.index}Years</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
			</div>
			<div class="row">
				<div class="clearfix">
					<div class="col-md-8">
						<div class="row">
							<div class="col-md-6">
								<label>Rate</label>
								<div class="clearfix jobAddSear">
										<select class="form-control input-sm btn-primary" id="currencyCode"
											name="currencyCode">
											<option selected disabled>INR</option>
											<option>AUD</option>
											<option>USD</option>
											<option>GBP</option>
											<option>SGD</option>
											<option>EUR</option>
										</select>
									<input type="text" class="form-control input-sm" name="minRate"
										id="minRate" value="" placeholder="Min Rate">
								</div>
							</div>
							<div class="col-md-6">
								<label>&nbsp;</label>
								<div class="input-group clearfix jobAddSear">
									<input type="text" class="form-control input-sm" value=""
										name="maxRate" id="maxRate" placeholder="Max Rate">
										<select class="form-control input-sm btn-primary">
											<option selected disabled>Per</option>
											<option>Hourly</option>
											<option>Weekly</option>
											<option>Bi-weekly</option>
											<option>Semi-Monthly</option>
											<option>Monthly</option>
											<option>Bi-Monthly</option>
											<option>Yearly</option>
										</select>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<label>Notice Period</label>
						<div class="clearfix">
							<select class="chosen-select" name="noticePeriod"
								id="noticePeriod" data-placeholder="Notice Period" multiple>
								<option>Immediately available</option>
								<option>15 Days</option>
								<option>1 Month</option>
								<option>2 Months</option>
								<option>3 Months</option>
							</select>
						</div>
					</div>
				</div>
				<div class="clearfix">
				</div>
				<%-- <div class="clearfix">
					<div class="col-md-4">
						<label>Country</label>
						<div class="clearfix adv-srh-chosen">
							<select class="chosen-select" name="country" id="country"
								field-name="Country" onchange="getStates();">
								<option value="">--select Country--</option>
								<c:forEach var="cntry" items="${countryList}">
									<option value="${cntry.name}"
										${cntry.name  == loginCountryName ? 'selected' : ''}>${cntry.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<label>State</label>
						<div class="clearfix adv-srh-chosen">
							<select class="chosen-select" name="state" id="state"
								field-name="State" onchange="getCities();">
								<option value="">---Select State---</option>
								<c:forEach var="state" items="${statesList}">
									<option value="${state.stateName}">${state.stateName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-4">
						<label>City</label>
						<div class="clearfix adv-srh-chosen">
							<select class="chosen-select" name="city" id="city"
								field-name="City">
								<option value="">---Select City---</option>
							</select>
						</div>
					</div>
				</div> --%>

				<div class="clearfix">
					<div class="col-md-4">
						<label>Organizations <i class="fa fa-info-circle"
							title="Organizations"></i></label>
						<div class="clearfix">
							<select class="chosen-select form-control input-sm" multiple
								data-placeholder="Organizations" id="organizations"
								name="organizations"></select>
						</div>

					</div>

					<div class="col-md-8">
						<label>Skills</label>
						<div class="clearfix">
							<select class="chosen-select" id="skills" name="skills" multiple
								data-placeholder="Select Skills">
								<c:forEach var="skill" items="${skillsList}">
									<c:if
										test="${fn:contains(requirement.reqSkillsListString, skill)}">
										<option selected value="${skill.name}">${skill.name}</option>
									</c:if>
									<c:if
										test="${!fn:contains(requirement.reqSkillsListString, skill)}">
										<option value="${skill.name}">${skill.name}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				
				<div class="clearfix">
						<%-- <div class="col-md-4">
								<label>Country</label>
								<div class="clearfix adv-srh-chosen">
									<select class="chosen-select" name="country" id="country" field-name="Country">
										<option value="">---select Country---</option>
										<c:forEach var="cntry" items="${countryList}">
											<option value="${cntry.name},${cntry.code2}">${cntry.name}</option>
										</c:forEach>
									</select>
								</div>
							</div> --%>
							
						   <div class="col-md-4">
								<label>Choose City</label>
								<div class="clearfix">
								<div class="col-md-14">
							      <input type="text" class="form-control positionRelative"  multiple data-placeholder="select city" multiple name="apiLocation"  id="apiLocation">
								  <i class="fa fa-refresh btn btn-primary refresh-icon mr ref-top" title="Refresh" onClick="refreshGoogleApiCity('apiLocation');"></i>
								 </div>
								</div>
							</div>
							
							<div class="col-md-8">
								<label>Selected Cities</label>
								<div class="clearfix">
								   <select class="chosen-select form-control input-sm" multiple
										data-placeholder="sletected cites" id="city" name="city">
									</select>
									
								</div>
							</div>
							
						</div>
			</div>
			<div class="row form-footer">
				<div class="pull-right mr">
					<input type="button" value="Search" id="advancedOpenJobsSearch"
						onclick="openJobSearchRequirement();loadOpenJobsChosenRequirementScripts();"
						class="btn btn-sm btn-primary" /> <input type="button"
						value="Cancel"
						onClick="adSearchClose();loadOpenJobsChosenRequirementScripts();"
						class="btn btn-sm btn-default ml10" />
				</div>
			</div>
		</div>
	</form:form>
</section>
<!-- Content Area Ends-->
<footer>
    <!-- <div class="footerInn">
        <div class="footerInnCta resFoot">
            <h3>SourceLead</h3>
            <ul>
                <li><a href="/about-us.html"><i class="fa fa-caret-right"></i>About
                        us</a></li>
                <li><a href="/team.html"><i class="fa fa-caret-right"></i>Our
                        team</a></li>
                <li><a href="about-us.html#client"><i class="fa fa-caret-right"></i>Clients</a></li>
                <li><a href="/testimonial.html"><i
                        class="fa fa-caret-right"></i>Testimonial</a></li>
                <li><a href="careers.html"><i class="fa fa-caret-right"></i>Careers</a></li>
                <li><a href="/contact.html"><i class="fa fa-caret-right"></i>Contact
                        us</a></li>
            </ul>
        </div>
        <div class="footerInnCta resFoot">
            <h3>What we do</h3>
            <ul>
                <li><a href="/crowdsourcing.html"><i
                        class="fa fa-caret-right"></i>Crowdsourcing</a></li>
                <li><a href="/ats.html"><i class="fa fa-caret-right"></i>ATS</a></li>
                <li><a href="/vms.html"><i class="fa fa-caret-right"></i>VMS</a></li>
                <li><a href="/timesheet.html"><i class="fa fa-caret-right"></i>Time
                        Management</a></li>
            </ul>
        </div>
        <div class="footerInnCta">
            <div class="hBlogs">
                <a href="/blog4.html"> <span>Loyal Relationships with
                        Recruiters</span> <b>FEB 20, 2017</b>
                </a> <a href="/blog3.html"> <span>Recruiting Through Social
                        Media Platform</span> <b>FEB 15, 2017</b>
                </a> <a href="/blog.html"> <span>Existing Process without
                        using any Tool</span> <b>DEC 20, 2016</b>
                </a> <a href="/blog4.html">Viwe all</a>
            </div>
        </div>
        <div class="footerInnCta">
            <h3>Get in touch</h3>
            <a href="mailto:info@sourcelead.com;"><b><i
                    class="fa fa-envelope-o"></i>info@sourcelead.com</b></a> <a
                href="tel:(214) 295-5535"><b><i class="fa fa-phone"></i>USA
                    : (214) 295-5535</b></a> <a href="tel:(972) 559-3641"><b><i
                    class="fa fa-fax"></i>FAX : (972) 559-3641</b></a> <a
                href="tel:040-6700-7676"><b><i class="fa fa-phone"></i>IND :
                    +91. 40-6700-7676</b></a>
            <h4>Go social</h4>
            <div class="socila">
                <a href="javascript:void(0)"><img
                    src="/resources/css/jobs/images/in.png" alt="" /></a> <a
                    href="javascript:void(0)"><img
                    src="/resources/css/jobs/images/fb.png" alt="" /></a> <a
                    href="javascript:void(0)"><img
                    src="/resources/css/jobs/images/tw.png" alt="" /></a> <a
                    href="javascript:void(0)"><img
                    src="/resources/css/jobs/images/gp.png" alt="" /></a>
            </div>
        </div>
        <div class="clear"></div>
    </div> -->
    <jsp:include page="../masterFooter.jsp" />
    <div class="copyrightOut">
        <div class="copyright">
            <b>� 2018 SourceLead. All rights reserved.</b> <span> <a
                href="/privacy-policy.html">Privacy Policy</a> <a href="/terms-of-use.html">Terms
                    of Use</a>
            </span>
            <div class="clear"></div>
        </div>
    </div>
</footer>

<section class="pageLoad"></section>
<div class="backtotop">
	<img src="/resources/css/jobs/images/backtotop.png" alt="" />
</div>

<!-- Footer Area -->
<%-- <jsp:include page="../masterFooter.jsp" /> --%>
<!-- Footer Area -->

<!-- this script is for load more...  -->
<script>
$("#guestselector").fadeIn(200);
$('#guestselector').delay(2000).fadeOut('slow');
	/* function loadMoreReq(){
    	var avilableCount = $("#openJobsOffset").val();
    	avilableCount = parseInt(avilableCount) + parseInt(10); 
    	$("#openJobsOffset").val(avilableCount);
    	requirementsFilter("more");
	} */
</script>



<script>
function clearcheck(){
	$('#remainingOrgs').find('input[type=checkbox]:checked').removeAttr('checked');
	$(".LettSelec a").removeClass("actL");
}

function clearFilters(){
	var sourceleadUrl ='${sourceleadUrl}';
	sourceleadUrl = sourceleadUrl.split("/app")[0];
	window.location.href = sourceleadUrl+"/jobs";
	$('#remainingOrgs').find('input[type=checkbox]:checked').removeAttr('checked');
	$('#dateFilterId').find('input[type=radio]:checked').removeAttr('checked');
	$('#countryFilterId').find('input[type=radio]:checked').removeAttr('checked');
	$('#empTypediv').find('input[type=checkbox]:checked').removeAttr('checked');
	$('#orgList').find('input[type=checkbox]:checked').removeAttr('checked');
	$(".LettSelec a").removeClass("actL");
}
</script>


<script>
	function getDropDownCount() {
		var selectedExperience = '${openJobsExperience}';
		var i = "";
		result = "";
		result = result
				+ '<select class="searchHome searchHomeE" id="openJobsExperienceId">';
		result = result
				+ '("<option selected value="">--Select Experience--</option>")';
		for (var i = 0; i <= 25; i++) {
			if(selectedExperience != null && selectedExperience != '' && selectedExperience != 'undefined' && (parseInt(i) == parseInt(selectedExperience))){
				result = result + '("<option selected value='+i+'>' + i + '</option>")';
			}
			else{
				result = result + '("<option value='+i+'>' + i + '</option>")';
			}
			
		}
		result = result + '</select>';
		$('#dropDownCountId').empty();
		$('#dropDownCountId').append(result);
	}
</script>

<script>
function requirementsFilter(type){
	getDropDownCount();
	var title = document.getElementById("openJobsRequirementTitleId").value;
	var location = document.getElementById("openJobsLocationId").value;
	var experience = document.getElementById("openJobsExperienceId").value;
	var dates = $('input:radio[name=dateFilter]:checked').val();
	
	var country = $('input:radio[name=countryFilter]:checked').val();
	var reqType = "";
	$('input:checkbox[name=typeFilter]:checked').each(function() 
    {
// 		reqType.push($(this).val());
		reqType	+=	(reqType==""?"":"--") + $(this).val();
    });
	var orgLocationIds = [];
	$('input:checkbox[name=orgName]:checked').each(function() 
    {
 		orgLocationIds.push($(this).val());
// 		orgLocationIds	+=	(orgLocationIds==""?"":"--") + $(this).val();
    });
	
	var offset = 0;
	if(type == "scroll"){
		offset = $("#openJobsOffset").val();
	}
	
	var postedOrgName = "";
	if(type == "postedOrg"){
		postedOrgName = $("#postedOrg").val();
	}
	
	 $("#loadinggif").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;'); 
	
	var url = "?";
	 if(dates!=undefined && dates!=""){
		var temp = url.substring(0, 1);
		url+=(url.indexOf("=")>0?"&":"")+"postedOn="+dates.toString().toLowerCase().replace(/ /g ,"-");
	} 
	if(country!=undefined && country!=""){
		var temp = url.substring(0, 1);
		url+=(url.indexOf("=")>0?"&":"")+"locationType="+country.toString().toLowerCase().replace(/ /g ,"-");
	}
	if(reqType!=undefined && reqType!=""){
		var temp = url.substring(0, 1);
		url+=(url.indexOf("=")>0?"&":"")+"employementType="+reqType.toString().toLowerCase().replace(/ /g ,"-");
	}
	if(orgLocationIds!=undefined && orgLocationIds!=""){
		var temp = url.substring(0, 1);
		url+=(url.indexOf("=")>0?"&":"")+"companies="+orgLocationIds.toString().toLowerCase().replace(/ /g ,"-");
	}
	
	if(postedOrgName!=undefined && postedOrgName!=""){
		var temp = url.substring(0, 1);
		url+=(url.indexOf("=")>0?"&":"")+"postedOrgName="+postedOrgName.toString().toLowerCase().replace(/ /g ,"-");
	}
	
	if(offset!=undefined && offset!=""){
// 		var temp = url.substring(0, 1);
		url+=(url.indexOf("=")>0?"&":"")+"offset="+offset.toString();
	}
	
	window.location.href = url;
	
	var sourceleadUrl ='${sourceleadUrl}';
	if(sourceleadUrl.indexOf("/"+title)!=-1){
		sourceleadUrl = sourceleadUrl.split(""/"+title")[0];
	}
	if(sourceleadUrl.indexOf("/app")!=-1){
		sourceleadUrl = sourceleadUrl.split("/app")[0];
	}
	window.location.href = sourceleadUrl+"/jobs"+url;
}
</script>


<script>
function getOpenJobsELKListJOBS(){
		var sourceleadUrl = '${sourceleadUrl}';
		var str = sourceleadUrl;
		var sourceleadUrl1 = str.replace("app", "");
		var url = "";
		var openJobsRequirementTitle = document
				.getElementById("openJobsRequirementTitleId").value;
		if (openJobsRequirementTitle != null && openJobsRequirementTitle != ''
				&& openJobsRequirementTitle != 'undefined') {
			url += "/" + openJobsRequirementTitle;
		} else {
			url += "/all";
		}
		var openJobsLocation = document.getElementById("openJobsLocationId").value;
		if (openJobsLocation != null && openJobsLocation != ''
				&& openJobsLocation != 'undefined') {
			url += "/" + openJobsLocation;
		} else {
			url += "/all";
		}
		var openJobsExperience = document
				.getElementById("openJobsExperienceId").value;
		if (openJobsExperience != null && openJobsExperience != ''
				&& openJobsExperience != 'undefined') {
			url += "/" + openJobsExperience;
		}

		if ((openJobsRequirementTitle == null || openJobsRequirementTitle == '' || openJobsRequirementTitle == 'undefined')
				&& (openJobsLocation == null || openJobsLocation == '' || openJobsLocation == 'undefined')
				&& (openJobsExperience == null || openJobsExperience == '' || openJobsExperience == 'undefined')) {
			location.replace(sourceleadUrl1 + "jobs");
		} else if ((openJobsRequirementTitle != null
				&& openJobsRequirementTitle != '' && openJobsRequirementTitle != 'undefined')
				&& (openJobsLocation == null || openJobsLocation == '' || openJobsLocation == 'undefined')
				&& (openJobsExperience == null || openJobsExperience == '' || openJobsExperience == 'undefined')) {
			location.replace(sourceleadUrl1 + "jobs/"
					+ openJobsRequirementTitle);
		} else {
			location.replace(sourceleadUrl1 + "jobs" + url);
		}

	}
</script>

<script>
function getOpenJobsELKList(orgLocationId,type){
	
	var sourceleadUrl ='${sourceleadUrl}';
	var openJobsRequirementTitle = document.getElementById("openJobsRequirementTitleId").value;
	
	var search = openJobsRequirementTitle.replace(/ & /g, '-and-').replace(/&/g, '-and-').replace(/'/g, '-or-').replace(/ /g, "-").replace(/\//g, "-or-");
	var openJobsLocation = document.getElementById("openJobsLocationId").value;
	var openJobsExperience = $("#openJobsExperienceId").val();
    var dates = $('input:radio[name=dateFilter]:checked').val();
	var country = $('input:radio[name=countryFilter]:checked').val();
	var companies= ""; 
	
	var companies11 ='${companies}';
	if(companies11 ==null && companies11 =='' && companies11 =='undefined') {
		companies = $('input:checkbox[name=typeFilter]:checked').val();
	}
	
	else{
		companies=companies11;
	}
	var reqType = "";
	$('input:checkbox[name=typeFilter]:checked').each(function() 
    {
// 		reqType.push($(this).val());
		reqType	+=	(reqType==""?"":"--") + $(this).val();
    });
	
	var orgLocationIds = [];
	$('input:checkbox[name=orgName]:checked').each(function() 
    {
		orgLocationIds.push($(this).val());
// 		orgLocationIds	+=	(orgLocationIds==""?"":"--") + $(this).val();
    });
	var offset = 0;
	if(type === "scroll"){
		offset = $("#openJobsOffset").val();
	}
	if(type === "manual"){
		$("#openJobsRequirementsList").empty();
		$("#openJobsOffset").val(0);
		    offset = 0;
	}
// 	if(orgLocationId != null){
	var orgListType = '${specificCompany}';
		if(orgListType !=null && orgListType !='' && orgListType !='undefined' && orgListType == 'specificCompany') {
		orgLocationIds = orgLocationId
		 document.getElementById("orgProfile").style.display="none";
		 document.getElementById("orgImage").style.display="block";
		 $("#companiesListDiv").hide();
// 		 document.getElementById("jobResultHeadId").style.display="block";
	}
// 	var offset = '${offset}';
// 	if(offset == ''){
// 		offset = 0;
// 	}else{
// 		$("#openJobsOffset").val(offset)
// 	}
	
	var url =sourceleadUrl + "/ws/getOpenJobsELK?${_csrf.parameterName}=${_csrf.token}&minOffset=0&offset="+offset;
	if(openJobsRequirementTitle)
		url+="&openJobsRequirementTitle="+openJobsRequirementTitle;
	if(openJobsLocation)
		url+="&openJobsLocation="+openJobsLocation;
	if(openJobsExperience)
		url+="&openJobsExperience="+openJobsExperience;
	if(dates)	
		url+="&dates="+dates;
	if(country)
		url+="&country="+country;
	if(companies)
		url+="&companies="+companies;
	if(reqType)
		url+="&reqType="+reqType;
	if(orgLocationIds)
		url+="&orgLocationIds="+orgLocationIds;
	if('${postedOrgName}' != '')
		url+="&postedOrgName="+'${postedOrgName}';
	$("#loadinggif").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
// 	location.href = "app/getOpenJobsELK/"+title+"/"+location+"/"+experience;
	  $.ajax({ 
	   	type: "GET",
	   	url: url,
	    success: function(json) {
// 	    	var json = JSON.parse(data);
			var data = json.data;
	    	if(data != null && data !='undefined' && data !=null &&  data != [] && data.length >0){
				var newOffset = parseInt(offset) + 10;
				if(data.length< 10){
					$('#loadMoreDiv').empty();
					$('#loadMoreDiv').append('<div class="loadWrap"> No More Records..</div>');
	 			}else if(data.length != null && data.length >= 10 && data.length > offset){
	 				$('#loadMoreDiv').empty();
	 				$('#loadMoreDiv').append('<div class="loadWrap"><i class="fa fa-spinner fa-spin"></i> More Records..</div>');
	 			}else{
	 				$('#loadMoreDiv').empty();
	 				$('#loadMoreDiv').append('<div class="loadWrap">No More Records..</div>');
	 			}
	    		prepareOpenJobsElkList(json,offset);
	    		$("#loadinggif").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    	}else{
	    		
	    		$("#loadinggif").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	$("#openJobsRequirementTitleId").val('');
				$("#openJobsLocationId").val('');
				$("#openJobsExperienceId").val('');
				$("#loadMoreReq").css("display", "none");
				$("#noResultsDiv").css("display", "block");
	
	        }
	    }
	 });  
 }
</script>
<script>
function prepareOpenJobsElkList(response,offset){
	$("#openJobsRequirementsList").show();
	$("#openJobsRequirementsSummary").hide();
	var sourceleadUrl ='${sourceleadUrl}';
	if(sourceleadUrl.indexOf("/app")!=-1){
		sourceleadUrl = sourceleadUrl.split("/app")[0];
	}
    var json = response.data;
  	 var experience ="";
  	 var location = "";
    if(response != null && response !='undefined' && response !=null && json.length >0){ 	
  	  if(json.length > 0)
  		{
  		var result = "";
			for(var i=0; i<json.length; i++){
				var requirements = json[i];
				var title=requirements.jobTitle;
				var link="";
				var picture="";
				var description='';
				var rate=''
				var rateType= '';
				var currencyType = '';
				description =  description+'Job Title:'+requirements.jobTitle +";";
 				description =  description+' Posted By :'+requirements.postedOrg+";";
				if(requirements.rate != null && requirements.rate != '' && requirements.rate != 'undefined' && requirements.maxRate != null && requirements.maxRate != '' && requirements.maxRate != 'undefined'){
					rate = requirements.rate+" - "+requirements.maxRate;
				}else if(requirements.rate != null && requirements.rate != '' && requirements.rate != 'undefined'){
					rate = requirements.rate;
				}else{
					rate="--";
				}
				
				if(requirements.rateType != null && requirements.rateType != '' && requirements.rateType != 'undefined'){
					currencyType = requirements.rateType;
				}else{
					currencyType ="--";
				}
				
				if(requirements.currentRateCurrency != null && requirements.currentRateCurrency != '' && requirements.currentRateCurrency != 'undefined' && requirements.rateType != null && requirements.rateType != '' && requirements.rateType != 'undefined' && requirements.rate != null && requirements.rate != '' && requirements.rate != 'undefined' && requirements.maxRate != null && requirements.maxRate != '' && requirements.maxRate != 'undefined'){
					rateType = requirements.currentRateCurrency;
				}else{
					rateType ="--";
				}
				if(requirements.minExperience != null && requirements.minExperience != '' && requirements.minExperience != 'undefined' && requirements.maxExperience != null && requirements.maxExperience != '' && requirements.maxExperience != 'undefined'){
					experience = requirements.minExperience+" to "+requirements.maxExperience+" Years";
				}else if(requirements.minExperience != null && requirements.minExperience != '' && requirements.minExperience != 'undefined'){
					experience = requirements.minExperience+" Years";
				}else if(requirements.maxExperience != null && requirements.maxExperience != '' && requirements.maxExperience != 'undefined'){
					experience = "0-"+requirements.maxExperience+" Years";
				}else{
					experience = 'No Data';
				}
	
				if(requirements.state != null && requirements.state != '' && requirements.state != 'undefined'){
					location = requirements.state+", "+requirements.country;
				}else{
					location = requirements.country;
				}
				result = result +'	<div class="jobResultsCta job_container">';
				result = result +'	<div class="jobResultsInn">';
				result = result +'<input type="hidden" id="maxJobCount" value="'+json.length+'"/>';
				result = result +'<input type="hidden" id="searchType" value="mainsearch"/>';
// 				result = result +'	<h3 style="color:#368ee0;" onclick="getOpenJobsRequirementSummary(\''+requirements.requirementUUID+'\');">'+requirements.jobTitle+'</h3>';
				result = result +'	<a target="_blank" href="'+sourceleadUrl+'/job/'+requirements.url+'-'+requirements.requirementId+'"><h3 style="color:#368ee0;">	'+requirements.jobTitle+'</h3></a>';
				description =  description+'Job Title:'+requirements.jobTitle+";";
				
				
// 				sourceleadUrl+"/"+jobTitle+"-"+id
				
// 				link = sourceleadUrl+"/openJobsPublicPage?requirementUUID="+requirements.requirementUUID;
				
				if(requirements.postedOrg != null && requirements.postedOrg != '' && requirements.postedOrg !='undefined'){
				result = result +'<input type="hidden" id="postedOrg" value=""/>';
				result = result +'<input type="hidden" id="'+i+'locationId" value="'+requirements.orgLocationId+'"/>';
				result = result +'<div class="clearfix"></div>';
				result = result +'<a target="_blank" href="'+sourceleadUrl+'/app/ws/'+requirements.postedOrg+'-'+requirements.orgLocationId+'"><h4 class="orgLink">'+requirements.postedOrg+'</h4></a>';	
				result = result +'<div class="clearfix"></div>';
				description =  description+'Posted By :'+requirements.postedOrg+";";
				}else{
				result = result +'	<h4>--</h4>';
				}
				
				result = result +'<strong class="pull-left mr10">Location(s) : </strong>';
				result = result +'<ul class="joblocations-li list-unstyled">';
 				if(requirements.locationsList != null){
 					for(var k=0; k<requirements.locationsList.length; k++){
 						result = result +'<li>'+requirements.locationsList[k]+'</li>';
 					}
 				}
 				result = result +'</ul>';
 				result = result +'<div class="clearfix"></div>';
 				
				result = result +'	<div class="skills">';
				result = result +'	<strong>Skills :</strong>';
				if(requirements.skills != null && requirements.skills != '' && requirements.skills !='undefined'){
				result = result +'  <span>'+requirements.skills+'</span>';
				description =  description+'Skills :'+requirements.skills+";";
				}else{
				result = result +'  <span>--</span>';	
				}
				result = result +'	</div>';					
				result = result +'	<span>';
				if(requirements.jobType != null && requirements.jobType != '' && requirements.jobType !='undefined'){
				result = result +'	<b><i class="fa fa-clock-o"></i>'+requirements.jobType+'</b>';
				description =  description+'Job Type:'+requirements.jobType+";";
				}else{
				result = result +'	<b><i class="fa fa-clock-o"></i>--</b>';
				}
				if(experience != null && experience != '' && experience !='undefined'){
				result = result +'	<b><i class="fa fa-briefcase"></i>'+experience+'</b>';
				if(experience != null && experience != 'No Data'){
					description =  description+'Experience :'+experience+";";
				}
				
				}else{
				result = result +'	<b><i class="fa fa-briefcase"></i>--</b>';	
				}
				/* if(location != null && location != '' && location !='undefined'){
				result = result +'	<b><i class="fa fa-map-marker"></i>'+location+'</b>';
				description =  description+'Job Location: '+location+";";
				}else{
				result = result +'	<b><i class="fa fa-map-marker"></i>--</b>';	
				} */
				
				if(rate != null && rate != '' && rate !='undefined'){
				result = result +'	<b><i class="fa fa-rupee"></i>'+rate+'-'+rateType+'/'+currencyType+'</b>';
				description =  description+'Rate: '+rate+";";
				}else{
				result = result +'	<b><i class="fa fa-rupee"></i>--</b>';	
				}
				
				if(requirements.noticePeriod != null && requirements.noticePeriod != '' && requirements.noticePeriod !='undefined'){
				result = result +'	<b><i class="fa fa-bell"></i>'+requirements.noticePeriod+'</b>';
				description =  description+'Notice Period :'+requirements.noticePeriod+";";
				}else{
				result = result +'	<b><i class="fa fa-bell"></i>--</b>';	
				}
				if(requirements.visaTypes != null && requirements.visaTypes != '' && requirements.visaTypes !='undefined'){
				result = result +'	<b><i class="fa fa-plane"></i>'+requirements.visaTypes+'</b>';
				description =  description+'Visa Type :'+requirements.visaTypes+";";
				}else{
				result = result +'	<b><i class="fa fa-plane"></i>--</b>';	
				}
				result = result +'	</span>';
				result = result +'	</div>';
				result = result +'	<div class="jobShare">';
				if(requirements.createdDate != null && requirements.createdDate != '' && requirements.createdDate !='undefined'){
				var creadtedDateString = requirements.createdDate;
				var creadtedDateStringArray = creadtedDateString.split("-");
				var convertedDate = creadtedDateStringArray[2]+'-'+creadtedDateStringArray[1]+'-'+creadtedDateStringArray[0];
				result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i>'+convertedDate+'</div>';
				description =  description+'Posted Date :'+convertedDate+";";
				}else{
				result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i>--</div>';	
				}
				result = result +'	<div class="share">';			  
				result = result +' <div class="addthis_inline_share_toolbox" data-url="http://qa.sourcelead.com/job/9th-date-job-5-for-social-networks-10918" addthis:url="http://qa.sourcelead.com/job/9th-date-job-5-for-social-networks-10918" addthis:title="'+requirements.jobTitle+'" data-title="'+requirements.jobTitle+'" addthis:description="'+requirements.jobTitle+'" data-description="'+requirements.jobTitle+'" data-media="https://www.sourcelead.com/resources/images/SourceLead-logo.png" addthis:media="https://www.sourcelead.com/resources/images/SourceLead-logo.png"></div>';
				result = result +'	</div>';
				result = result +'	<div class="clear"></div>';
				result = result +'	</div>';
				result = result +'	<div class="jobApply"><a title="Create Alert" href="javascript:void(0)" class="alertBtn" onClick="alertFunction();createAlert(\''+requirements.requirementUUID+'\');"><i class="fa fa-bell-o"></i></a>';
				result = result +'   <a onClick="applyJob(\''+requirements.requirementUUID+'\');">Apply</a>';
				result = result +'	<a href="/login" style="background-color:#eb821c;">Refer</a></div>';
				result = result +'	</div>';
				result = result +'	</div>';
			}
			$("#openJobsRequirementsSummary").empty();
			if(json.length > 0){
				$("#openJobsRequirementsList").empty();
// 				$('#loadMoreDiv').empty(); 
				$("#openJobsRequirementsList").append(result); 
			}else{
				$("#openJobsRequirementsList").empty();
				$("#openJobsRequirementsList").append(result); 
			}
			
			window.setTimeout(function() {
				addthis.toolbox(".addthis_inline_share_toolbox_g28q");
				addthis.init(".addthis_inline_share_toolbox_g28q");
				addthis.layers.refresh();
	        }, 500);
			
			
			//commented by sathish -- due to populating the values in main search fields
// 			$("#openJobsRequirementTitleId").val('');
// 			$("#openJobsLocationId").val('');
// 			$("#openJobsExperienceId").val('');
			
			}
		}
    socialInviterScripts();
}
</script>

<script>
function getPostedOrgJobs(id){
	var locationId ="";
	if(id !=null && id !='undefined' && id>=0){
		locationId = $("#"+id+"locationId").val();
		 $("#postedOrg").val(locationId);
		 
	}else{
		locationId = $("#locationId").val();
		 $("#postedOrg").val(locationId);
		 
	}
	$("#companiesListDiv").hide();
	getOpenJobsELKList(locationId,'manual');
// 	requirementsFilter("postedOrg");
}
</script>

<script>
function getOrgsList(alphabet,type){
	var sourceleadUrl ='${sourceleadUrl}';
	var country = "";
	$('input:radio[name=countryFilter]:checked').each(function() 
    {
		country = ($(this).val());
    });
	var companies = '${companies}';
	var selectedCompanies = companies.split(",");
	 $.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl + "/ws/getOrgsListforOpenJobs?${_csrf.parameterName}=${_csrf.token}&alphabet="+alphabet+"&country="+country+"&type="+type,
	    success: function(data) {
	    	var json = data.data;
	    	var result = "";
	    	
	    	if(type != 'main'){
	    		var org = "";    	
				for(var i=0; i<json.length; i++){
					var opt = json[i];
					org = org +'	<li class="control-group">';
					org = org +'		<label class="control control--checkbox"><span>'+opt.orgName+'</span>';
					org = org +'		  <input type="checkbox" name="orgName" value="'+opt.id+'"  /><div class="control__indicator"></div>';
					org = org +'	</label>';
					org = org +'	</li>';
				}
				$("#remainingOrgs").empty();
				$("#remainingOrgs").append(org);
	    	}else{
	    		for(var i=0; i<5; i++){
		    		var opt = json[i];
		    		if(opt != null && opt !='undefined'){ 
		    			if(selectedCompanies != '' && selectedCompanies != 'undfined' && selectedCompanies != null){
		    				var selected = "";
		    				for(var j=0; j <= selectedCompanies.length; j++){
						    	if(selectedCompanies[j] == opt.id){
						    		selected = "checked";
						    	}
				    		}
		    				result = result +'<li class="control-group">';
					    	result = result +'<label class="control control--checkbox" title="'+opt.orgName+'"><span>'+opt.orgName+'</span>';
//	 				    	result = result +'  <input type="checkbox" name="orgName" value="'+opt.id+'" ${companies.indexOf("'+opt1.id+'")!=-1?"checked=checked":""} onclick="requirementsFilter();"/><div class="control__indicator"></div>';
					    	result = result +'  <input type="checkbox" name="orgName" value="'+opt.id+'" '+selected+' onclick="requirementsFilter();"/><div class="control__indicator"></div>';
					    	result = result +'</label>';                                                 
					    	result = result +'</li>';
		    			}else{
		    				result = result +'<li class="control-group">';
					    	result = result +'<label class="control control--checkbox" title="'+opt.orgName+'"><span>'+opt.orgName+'</span>';
//	 				    	result = result +'  <input type="checkbox" name="orgName" value="'+opt.id+'" ${companies.indexOf("'+opt1.id+'")!=-1?"checked=checked":""} onclick="requirementsFilter();"/><div class="control__indicator"></div>';
					    	result = result +'  <input type="checkbox" name="orgName" value="'+opt.id+'" onclick="requirementsFilter();"/><div class="control__indicator"></div>';
					    	result = result +'</label>';                                                 
					    	result = result +'</li>';
		    			}
		    			
		    		}
		    	}
// 	    		if(json.length > 5){
// 	    			var orgCount = json.length - 5 ;

				if(opt.noOfDays != '' && opt.noOfDays != 'undfined' && opt.noOfDays != null){
			    	result = result +'<a onclick="openPopup(); getOrgsList(\'none\',\'sub\');" class="popOpen">+'+opt.noOfDays+' More</a>';

				}
				var org = "";    	
				for(var i=0; i<json.length; i++){
					var opt1 = json[i];
					org = org +'	<li class="control-group">';
					org = org +'		<label class="control control--checkbox" title="'+opt1.orgName+'" ><span>'+opt1.orgName+'</span>';
					org = org +'		  <input type="checkbox" name="orgName" value="'+opt1.id+'" ${companies.indexOf("'+opt1.id+'")!=-1?"checked=checked":""} /><div class="control__indicator"></div>';
					org = org +'	</label>';
					org = org +'	</li>';
				}
		    	$("#orgList").empty();
				$("#orgList").append(result);
				$("#remainingOrgs").empty();
				$("#remainingOrgs").append(org);
	    	}
	    }
	 }); 
}


function openPopup(){
	$(".filPop").fadeIn(100);
	$("#all").toggleClass("actL");
};
</script>
<script type="text/javascript" src='/resources/js/slead-common.js?staticTime=${staticTime}'></script>
<script>

/* if("${uuid}"!=null){
	getOpenJobsRequirementSummary("${uuid}");
} */

function getOpenJobsURL(requirementUUID){
	var sourceleadUrl ='${sourceleadUrl}';
	if(sourceleadUrl.indexOf("/app")!=-1){
		sourceleadUrl = sourceleadUrl.split("/app")[0];
	}
	window.location.href = sourceleadUrl+"/"+jobTitle+"-"+id;
} 

function getOpenJobsRequirementSummary(requirementsUUID){
	var sourceleadUrl ='${sourceleadUrl}';
	$("#loadMoreReq").css("display", "none");
	$("#openJobsRequirementsSummary").show();
	$("#openJobsRequirementsList").hide(); 
	$("#loadMoreDiv").hide(); 
	
	 $.ajax({
		 type: "POST",
		 url: sourceleadUrl + "/ws/openRequirementsSummary?${_csrf.parameterName}=${_csrf.token}&requirementsUUID="+requirementsUUID,
		    success: function(data) {
	    	if(data != null && data !='undefined' && data !=null){ 	
	    		
	    	  var json = data.object;
	    	 
		      var result = "";

		      var title=json.jobTitle;
				var link="";
				var picture="";
				var description='';
				var comment='';	
				var rate=''
			    var rateType= '';
				var currencyType = '';
					
					if(json.rate != null && json.rate != '' && json.rate != 'undefined' && json.maxRate != null && json.maxRate != '' && json.maxRate != 'undefined'){
						rate = json.rate+" - "+json.maxRate;
					}else if(json.rate != null && json.rate != '' && json.rate != 'undefined'){
						rate = json.rate;
					}else{
						rate="--";
					}
					
					if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
						currencyType = json.rateType;
					}else{
						currencyType ="--";
					}
					
					if(json.currencyCode != null && json.currencyCode != '' && json.currencyCode != 'undefined' && json.rateType != null && json.rateType != '' && json.rateType != 'undefined' && json.rate != null && json.rate != '' && json.rate != 'undefined' && json.maxRate != null && json.maxRate != '' && json.maxRate != 'undefined'){
						rateType = json.currencyCode;
					}else{
						rateType ="--";
					}
				
    
		      result = result +' <div class="beard clearfix">';
// 		      result = result +'	<a href="javascript:void(0)" onclick="backtoList();"><i class="fa fa-arrow-left text-muted paddingRight"></i>Back to List</a>';
		      result = result +'</div>';
		      
				result = result +'	<section class="innerContent">';
				result = result +'  <div class="jobDetails positionRelative">';
				result = result +'	<div class="jobResultsInn job_ResultsInn width100Percnt">';
		    	result = result +'	<h3>'+json.jobTitle+'</h3>';
		    	if(json.requirementOrgName != null && json.requirementOrgName != '' && json.requirementOrgName !='undefined'){
				result = result +'<input type="hidden" id="locationId" value="'+json.orgLocationId.id+'"/>';
				/* result = result +'	<h4 onClick="getPostedOrgJobs();">'+json.requirementOrgName+'</h4>'; */
				result = result +' <div class="clearfix"></div>';
				result = result +'<a target="_blank" href="'+sourceleadUrl+'/ws/'+json.requirementOrgName+'-'+json.orgLocationId.id+'"><h4 class="orgLink">'+json.requirementOrgName+'</h4></a>';
				description =  description+'Posted By :'+json.requirementOrgName+";";
		    	}else{
		    		result = result +'	<h4>--</h4>';	
		    	}
		    	result = result +'	<div class="clearfix"></div>';
		    	result = result +'	<div class="skills clearfix"><strong class="pull-left mr10">Locations :</strong>';
		    	result = result +'<ul class="joblocations-li list-unstyled pull-left">';
 				if(json.locationsList != null){
 					for(var k=0; k<json.locationsList.length; k++){
 						result = result +'<li>'+json.locationsList[k]+'</li>';
 					}
 				}
 				result = result +'</ul>';
				result = result +'	</div>';
				
		    	result = result +'	<div class="clearfix"></div>';
		    	result = result +'	<div class="skills clearfix"><strong>Skills :</strong>';
				if(json.skills != null && json.skills != '' && json.skills !='undefined'){
				result = result +'	<span>'+json.skills+'</span>';
				description =  description+'Skills :'+json.skills+";";
				}else{
				result = result +'	<span>--</span>';	
				}
				result = result +'	</div>';					
				result = result +'	<span>';
				
// 				link = "/openJobsPublicPage?requirementUUID="+json.requirementUUID;
				description =  description+'Job Title:'+json.jobTitle+";";
				
				if(json.jobType != null && json.jobType != '' && json.jobType !='undefined'){
				result = result +'	<b><i class="fa fa-clock-o"></i>'+json.jobType+'</b>';
				description =  description+'Job Type:'+json.jobType+";";
				}else{
				result = result +'	<b><i class="fa fa-clock-o"></i>--</b>';	
				}
				if((json.minExp != null && json.minExp != '' && json.minExp !='undefined') &&(json.maxExp != null && json.maxExp != '' && json.maxExp !='undefined')){
				result = result +'	<b><i class="fa fa-briefcase"></i>'+json.minExp+' to '+json.maxExp+'</b>';
				}else if((json.minExp != null && json.minExp != '' && json.minExp !='undefined')){
				result = result +'	<b><i class="fa fa-briefcase"></i>'+json.minExp+'</b>';	
				}else{
		        result = result +'	<b><i class="fa fa-briefcase"></i>--</b>';		
				}
			/* 	if(null != json.city && json.city!='undefined'){
				result = result +'	<b><i class="fa fa-map-marker"></i>'+json.country+','+json.state +','+json.city+'</b>';
				}else if(null != json.state && json.state!='undefined'){
				result = result +'	<b><i class="fa fa-map-marker"></i>'+json.country+','+json.state +'</b>';	
				}else{
				result = result +'	<b><i class="fa fa-map-marker"></i>'+json.country+'</b>';	
				} */
				if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod !='undefined'){
				result = result +'	<b><i class="fa fa-bell"></i>'+json.noticePeriod+'</b>';
				description =  description+'Notice Period :'+json.noticePeriod+";";
				}else{
				result = result +'	<b><i class="fa fa-bell"></i>--</b>';	
				}
				
				
				if(rate != null && rate != '' && rate !='undefined'){
				result = result +'	<b><i class="fa fa-rupee"></i>'+rate+'-'+rateType+'/'+currencyType+'</b>';
				description =  description+'Rate: '+rate+";";
				}else{
				result = result +'	<b><i class="fa fa-rupee"></i>--</b>';	
				}
				
				result = result +'	</span>';
				result = result +'    </div>';			
				result = result +'  <div class="jobApply">';
				result = result +'	<a title="Create Alert" href="javascript:void(0)" class="alertBtn" onClick="alertFunction();createAlert(\''+json.requirementUUID+'\');"><i class="fa fa-bell-o"></i></a>';
				result = result +'   <a onClick="applyJob(\''+json.requirementUUID+'\');">Apply</a>';
				result = result +'	<a href="/login" class="btn2" style="background-color:#eb821c;">Refer</a>';
				result = result +'	<div style="clear:both;"></div>';
				/* result = result +'	<div class="share hidden">';
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'linkedin\');"><i class="fa fa-linkedin-square"></i></a>';
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'twitter\');"><i class="fa fa-twitter-square"></i></a>';			  
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'facebook\');"><i class="fa fa-facebook-square"></i></a>';
				result = result +'	</div>'; */
				result = result +'    </div>';
				result = result +'    <div style="clear:both;"></div>';
				result = result +'	<div class="jobDesc">';
				result = result +'	<h3>Job Descriptions :</h3>';
				if(json.description != null && json.description != '' && json.description !='undefined'){
				result = result +'	<p>'+json.description+'</p>'; 
				}else{
				result = result +'	<p></p>'; 	
				}
				result = result +'	<h4>Other details:</h4>';
			/* 	if(json.location != null && json.location != '' && json.location !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Work Location: </label>&nbsp;'+json.location+'</b>';
				description =  description+'Job Location: '+json.location+";";
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Work Location: </label>--</b>';
	
				} */
				if(json.positions != null && json.positions != '' && json.positions !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>No. of positions:</label>'+json.positions+'</b>';
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>No. of positions:</label>--</b>';	
				}
				
				if(json.education != null && json.education != '' && json.education !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Education:</label>'+json.education+'</b>';
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Education:</label>--</b>';
				}
				if(json.requirementCreatedDateToString != null && json.requirementCreatedDateToString != '' && json.requirementCreatedDateToString !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Posted:'+json.requirementCreatedDateToString+'</label></b>';
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Posted:</label>--</b>';	
				}
				if(json.visaType != null && json.visaType != '' && json.visaType !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Immigration Type:'+json.visaType+'</label></b>';
				description =  description+'Visa Type :'+json.visaTypes+";";
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Immigration Type:</label>--</b>';	
				}
				if(json.fileURL != null && json.fileURL != '' && json.fileURL !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Attachment:'+json.fileName+'</label><a download class="glyphicon glyphicon-download-alt ml10" href="'+json.fileURL+'"></a></b>';
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Attachment:</label>--</b>';	
				}
				if(json.experienceLevel != null && json.experienceLevel != '' && json.experienceLevel !='undefined'){
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Experience Level:</label>'+json.experienceLevel.name+'</b>';
					}else{
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Experience Level:</label>--</b>';	
					}
				if(json.medicalClearence != null && json.medicalClearence != '' && json.medicalClearence !='undefined'){
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Medical Clearence:</label>'+json.medicalClearence+'</b>';
					}else{
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Medical Clearence:</label>--</b>';	
					}
				
				if(json.securityClearance != null && json.securityClearance != '' && json.securityClearance !='undefined'){
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Security Clearence:</label>'+json.securityClearance+'</b>';
					}else{
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Security Clearence:</label>--</b>';	
					}
				
				if(json.overTime != null && json.overTime != '' && json.overTime !='undefined'){
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Over Time:</label>'+json.overTime+'</b>';
					}else{
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Over Time:</label>--</b>';	
					}
				
				if(json.backgroundCheck != null && json.backgroundCheck != '' && json.backgroundCheck !='undefined'){
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Background Check:</label>'+json.backgroundCheck+'</b>';
					}else{
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Background Check:</label>--</b>';	
					}
				
				if(json.gender != null && json.gender != '' && json.gender !='undefined'){
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Gender:</label>'+json.gender+'</b>';
					}else{
					result = result +'	<b><i class="fa fa-caret-right"></i><label>Gender:</label>--</b>';	
					}
				result = result +'	</div>';
				result = result +'	<div class="jobShare">';
				if(json.requirementCreatedDateToString != null && json.requirementCreatedDateToString != '' && json.requirementCreatedDateToString !='undefined'){
				result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i><label>Posted:'+json.requirementCreatedDateToString+'</label></div>';
				description =  description+'Posted Date :'+json.requirementCreatedDateToString+";";
				}else{
					result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i><label>Posted:</label>--</div>';
				}

				result = result +'	<div class="share">';
				    var sourceleadShareUrl = sourceleadUrl ;
					sourceleadShareUrl = sourceleadShareUrl.split("/app")[0];
					var jobTitle = json.jobTitle;
					jobTitle = jobTitle.replace(/[^a-zA-Z0-9]+/gi, '-').toLowerCase();
					 if (jobTitle.startsWith("-")){
						jobTitle = jobTitle.substring(1);
				    }
					if (jobTitle.endsWith("-")){
						jobTitle = jobTitle.substring(0, jobTitle.length() - 1);
				    } 
					
				result = result +' <div class="addthis_inline_share_toolbox" data-url="'+(sourceleadShareUrl+'/job/'+jobTitle+'-'+json.id)+'" addthis:url="'+(sourceleadShareUrl+'/job/'+jobTitle+'-'+json.id)+'" addthis:title="'+json.jobTitle+'" data-title="'+json.jobTitle+'" addthis:description="'+json.jobTitle+'" data-description="'+json.jobTitle+'"></div>';
				result = result +'	</div>';
				result = result +'	<div style="clear:both;"></div>';
				result = result +'	</div>';  	
				result = result +'	</div>';
				result = result +'	<div style="clear:both;"></div>';		
				result = result +'	</section>'; 
				document.getElementById("orgProfile").style.display="none";
				$("#openJobsRequirementsList").hide();
				$("#openJobsRequirementsSummary").empty();

				window.setTimeout(function() {
					addthis.toolbox(".addthis_inline_share_toolbox_g28q");
					addthis.init(".addthis_inline_share_toolbox_g28q");
					addthis.layers.refresh();
		        }, 500);
				
				$("#openJobsRequirementsSummary").append(result);
// 				if(requirementUUID != null){
// 				$("#noResultsDiv").css("display", "block");
// 				}
				
				
	   	   	   }
	   	   	}
		});  
	 socialInviterScripts();
}

function backtoList(){
	getOpenJobsELKList('','manual');
	$("#openJobsRequirementsList").show();
	$("#openJobsRequirementsSummary").hide();
	if(maxJobCount > 10){
		$("#loadMoreDiv").show(); 
		$("#loadMoreReq").css("display", "block");
	}
}

</script>

<script>
function viewJobSummaryDocument(jobId,title){
		if(jobId != null && jobId != ""){
			window.open('/viewJobSummaryDocumentDocView/'+title+'?jobId='+jobId,title,"height=600,width=600");
		}  
}

</script>
<script>
function openJobSearchRequirement(){
	/* if ($('#openJobsAdvancedForm').validate(false, validationSettings)){   */
	
			var frm = $('#openJobsAdvancedForm').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#openJobsAdvancedForm')[0];
   		    var formData = new FormData(form);
   		 if(sourceleadUrl.indexOf("/app")!=-1){
				sourceleadUrl = sourceleadUrl.split("/app")[0];
			}
			//It will escape all the special characters
			$("#advancedOpenJobsSearch").attr("disabled", "disabled");
			$("#advancedOpenJobsSearch").addClass('button').val('Processing..');
			$.ajax({
						type : "GET",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl + "/app/ws/openJobsRequiremetsAdvancedSearch?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(data) {
							 $("#advancedOpenJobsSearch").addClass('button').val('Search');
							 $("#advancedOpenJobsSearch").removeAttr("disabled");
							 adSearchClose();
							 $("#openJobsRequirementsList").empty();
						     var json = JSON.parse(data);
					       	 var experience ="";
					       	 var location = "";
						   	 if(json.length > 0)	
						   		{
						 			    for(var i=0; i<json.length; i++){
						 				var requirements = json[i];
						 				if(requirements.minExperience != '' && requirements.maxExperience == ''){
						 					experience = requirements.minExperience+" Years";
						 				}else if(requirements.minExperience == '' && requirements.maxExperience != ''){
						 					experience = requirements.maxExperience+" Years";
						 				}else if(requirements.minExperience != '' && requirements.maxExperience != ''){
						 					experience = requirements.minExperience+" to "+requirements.maxExperience+" Years";
						 				}else{
						 					experience = 'No Data';
						 				}
						 				
						 				if(requirements.state != null && requirements.state != '' && requirements.state != 'undefined'){
						 					location = requirements.state+", "+requirements.country;
						 				}else{
						 					location = requirements.country;
						 				}
						 				
						 				if(requirements.skills != null && requirements.skills != '' && requirements.skills != 'undefined'){
						 					skills = requirements.skills;
						 				}else{
						 					skills = "----"
						 				}
						 				
						 				if(requirements.jobType != null && requirements.jobType != '' && requirements.jobType != 'undefined'){
						 					jobType = requirements.jobType;
						 				}else{
						 					jobType = "----"
						 				}
						 				
						 				
						 				var result = "";
						 				var title="";
						 				var link="";
						 				var picture="";
						 				var description='';
						 				var comment='';
						 				result = result +'<input type="hidden" id="searchType" value="advancesearch"/>';
						 				result = result +'	<div class="jobResultsCta">';
										result = result +'	<div class="jobResultsInn">';
										result = result +'	<a target="_blank" class="block" href="'+sourceleadUrl+'/job/'+requirements.url+'-'+requirements.requirementId+'"><h3 style="color:#368ee0;">	'+requirements.jobTitle+'</h3></a>';
// 										result = result +'	<a href="<c:url value="/app/ws/openRequirementsSummary?requirementUUID='+requirements.requirementUUID+'"/>"><h3>'+requirements.jobTitle+'</h3>';
										result = result +'<a target="_blank" class="block" href="'+sourceleadUrl+'/app/ws/'+requirements.postedOrg+'-'+requirements.orgLocationId+'"><h4 class="orgLink">'+requirements.postedOrg+'</h4></a>';
// 										result = result +'	<h4>'+requirements.postedOrg+'</h4>';

										result = result +'	<div class="clearfix"></div>';
								    	result = result +'	<div class="skills clearfix"><strong class="pull-left mr10">Locations :</strong>';
								    	result = result +'<ul class="joblocations-li pull-left list-unstyled">';
						 				if(requirements.locationsList != null){
						 					for(var k=0; k<requirements.locationsList.length; k++){
						 						result = result +'<li>'+requirements.locationsList[k]+'</li>';
						 					}
						 				}
						 				result = result +'</ul>';
										result = result +'	</div>';
										
										result = result +'	<div class="skills">';
										result = result +'	<strong>Skills :</strong>';
										result = result +'  <span>'+skills+'</span>';
										result = result +'	</div>';					
										result = result +'	<span>';
										result = result +'	<b><i class="fa fa-clock-o"></i>'+jobType+'</b>';
										if(experience == 'undefined'){
											result = result +'	<b><i class="fa fa-briefcase"></i>'+experience+'</b>';
										}else{
											result = result +'	<b><i class="fa fa-briefcase"></i>--</b>';
										}
										result = result +'	</span>';
										result = result +'	</div>';
										

										link = "/openJobsPublicPage?requirementUUID="+requirements.requirementUUID;
										description =  description+'Job Title:'+requirements.jobTitle+";";
										
										result = result +'	<div class="jobShare">';
										result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i>'+requirements.createdDate+'</div>';

// 										result = result +'	<div class="refer" title="Refer your friend"><i class="fa fa-user" aria-hidden="true"></i></div>';

										result = result +'	<div class="share">';
										result = result +' <div class="addthis_inline_share_toolbox" data-url="'+(sourceleadUrl+'/job/'+requirements.url+'-'+requirements.requirementId)+'" addthis:url="'+(sourceleadUrl+'/job/'+requirements.url+'-'+requirements.requirementId)+'" addthis:title="'+requirements.jobTitle+'" data-title="'+requirements.jobTitle+'" addthis:description="'+requirements.jobTitle+'" data-description="'+requirements.jobTitle+'"></div>';
										result = result +'  </div>';
										result = result +'	<div class="clear"></div>';
										result = result +'	</div>';
										
										result = result +'	<div class="jobApply"><a title="Create Alert" href="javascript:void(0)" class="alertBtn" onClick="alertFunction();createAlert(\''+requirements.requirementUUID+'\');"><i class="fa fa-bell-o"></i></a>';
										result = result +'   <a onClick="applyJob(\''+requirements.requirementUUID+'\');">Apply</a>';
// 										result = result +'   <a href="/login?requirementUUID='+requirements.requirementUUID+'">Apply123</a>';
										result = result +'	<a href="/login" style="background-color:#eb821c;">Refer</a></div>';
													
										result = result +'	</div>';
										$("#openJobsRequirementsSummary").empty();
										$("#openJobsRequirementsList").append(result); 
										$('#loadMoreDiv').empty();
						 				$('#loadMoreDiv').append('<div class="loadWrap">No More Records..</div>');
								}
							}
						   	else{
							   	$('#loadMoreDiv').empty();
				 				$('#loadMoreDiv').append('<div class="loadWrap">No More Records..</div>');
							   	 }
					}
				});
			}
	
				 /* window.setTimeout(function() {
						addthis.toolbox(".addthis_inline_share_toolbox_g28q");
						addthis.init(".addthis_inline_share_toolbox_g28q");
						addthis.layers.refresh();
			        }, 500); */
				$('body').on('blur','#openJobsAdvancedForm',function() {
				$("#openJobsAdvancedForm").showHelpOnFocus();
				$("#openJobsAdvancedForm").validateOnBlur(false, validationSettings);
			});
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

 


<script type="text/javascript">
$.noConflict();
    	   var config = {
    				  '.chosen-select'           : {},
    				  '.chosen-select-deselect'  : {allow_single_deselect:true},
    				  '.chosen-select-no-single' : {disable_search_threshold:10},
    				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    				  '.chosen-select-width'     : {width:"95%"}
    	   }
			//Adding Customized Any Of Keyword to the list 
		    var select, chosen;
		    select = $("#anyKeyWord");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keydown keyup', function(e)
		    {
		    	var anyofKeyword = this.value.replace(",","");
		        if (tagItBykeyForSkills(e) && (anyofKeyword != null  && anyofKeyword != '' && anyofKeyword != 'undefined'))
		        {
		        	preventTabkey(e); 
		        	var par = this.parent;
		            var option = $("<option>").val(anyofKeyword).text(anyofKeyword);
		            select.append(option);
		            select.find(option).prop('selected', true);
                    $("#anyKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#anyKeyWord").trigger("chosen:updated");
	        		
		        }
		    });
		   
		    /*  var selectone, chosenone;
		    selectone = $("#allReqKeyWord");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keydown keyup', function(e1)
		    {
		    	var allofKeyWord = this.value.replace(",","");
		        if (tagItBykeyForSkills(e1) && (allofKeyWord != null  && allofKeyWord != '' && allofKeyWord != 'undefined'))
		        {
		        	preventTabkey(e1); 
		        	var par = this.parent;
		            var option = $("<option>").val(allofKeyWord).text(allofKeyWord);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
                    $("#allReqKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#allReqKeyWord").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selecttwo, chosentwo;
		    selecttwo = $("#excludeReqKeyWord");
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosentwo = selecttwo.data('chosen');
		    chosentwo.search_field.on('keydown keyup', function(e2)
		    {
		    	var excludeKeyWord = this.value.replace(",","");
		        if (tagItBykeyForSkills(e2) && (excludeKeyWord != null  && excludeKeyWord != '' && excludeKeyWord != 'undefined'))
		        {
		        	preventTabkey(e2); 
		        	var par = this.parent;
		            var option = $("<option>").val(excludeKeyWord).text(excludeKeyWord);
		            selecttwo.append(option);
		            selecttwo.find(option).prop('selected', true);
                    $("#excludeReqKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#excludeReqKeyWord").trigger("chosen:updated");
	        		
		        }
		    }); 
		    
		    
		/*     var selecttwo, chosentwo;
		    selecttwo = $("#requirementTitle");
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosentwo = selecttwo.data('chosen');
		    chosentwo.search_field.on('keydown keyup', function(e2)
		    {
		    	var excludeKeyWord = this.value.replace(",","");
		        if (tagItBykeyForSkills(e2) && (excludeKeyWord != null  && excludeKeyWord != '' && excludeKeyWord != 'undefined'))
		        {
		        	preventTabkey(e2); 
		        	var par = this.parent;
		            var option = $("<option>").val(excludeKeyWord).text(excludeKeyWord);
		            selecttwo.append(option);
		            selecttwo.find(option).prop('selected', true);
                    $("#requirementTitle option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#requirementTitle").trigger("chosen:updated");
	        		
		        }
		    }); */
		    
		    
		    var selectthree, chosenthree;
		    selectthree = $("#organizations");
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenthree = selectthree.data('chosen');
		    chosenthree.search_field.on('keydown keyup', function(e3)
		    {
		    	var organizations = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (organizations != null  && organizations != '' && organizations != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(organizations).text(organizations);
		            selectthree.append(option);
		            selectthree.find(option).prop('selected', true);
                    $("#organizations option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#organizations").trigger("chosen:updated");
	        		
		        }
		    });
		    
		  //Adding Customized Skills to the list
		       var selectfour, chosenfour;
			    selectfour = $("#skills");
			    selectfour.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Email:' });
			    chosenfour = selectfour.data('chosen');
			    chosenfour.search_field.on('keyup', function(e)
			    {
			    	   var skillsArray = this.value.split(",");
				    	 if(skillsArray.length > 1){
			        		 for (var i=0; i < skillsArray.length; i++) {
			        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
			        			 selectfour.append(option);
			 		             selectfour.find(option).prop('selected', true);
			 		             selectfour.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
			 	        		 $("#skills").trigger("chosen:updated");
			                 }
				    	 }else{
			    			var newSkill = this.value.replace(","," ");
			    			if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
			                {
								 var isNewSkill = true;
								 $("#skills option:selected").each(function(){
									 if(this.value.toUpperCase() === newSkill.toUpperCase()){
										 isNewSkill = false;
										 return false;
									 }
			                    
			                   });
								if(isNewSkill){
			    				 var option = $("<option>").val(newSkill).text(newSkill);
								 selectfour.append(option);
			                     selectfour.find(option).prop('selected', true);
								}
				               $("#skills").trigger("chosen:updated");
			                }
					       
		    	         }
			    });
		    
			  tagIt("city", 'Press Enter-Key Or Comma(,) Or Space To Add New Cites:', 'tagItBykeyForSkills','skills','sletected cites');

				   
		      /* $("#country").on('change', function (e) {
		    	  getcites('apiLocation','city');
		    	  $("#apiLocation").val('');
		    	  $("#city").val('').trigger("chosen:updated");
		    	   getStates();
		       }); */
		      getcites('apiLocation','city','recommendation');

				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
</script>



<!--  Forgot password -->
<script>
	$("#forpass").hide();
	$("#pass").click(function() {
		$("#forpass").show();
		$("#signin").hide();
	});
	$(".canbtn").click(function() {
		$("#forpass").hide();
		$("#signin").show();
	});
</script>


<!--   Responsive Menu   -->
<script>
	$("#menuIcon").click(function() {
		$(".mask").fadeIn(200);
		$(".resMenu").addClass("resMenuAct");
		$("body").css("overflow", "hidden");
		$(".menuCl").fadeIn(100);
	});
	$(".mask").click(function() {
		$(".mask").fadeOut(200);
		$(".resMenu").removeClass("resMenuAct");
		$("body").css("overflow", "auto");
		$(".menuCl").fadeOut(100);
	});

	$(function() {
		$('.accordion-toggle .accordion-link').click(
				function() {
					if (!$(this).hasClass('accordion-on'))
						$('.accordion-toggle .accordion-link').removeClass('accordion-on');
					$(this).toggleClass('accordion-on');
					$(this).next(".accordion-panel").slideToggle().siblings(
							".accordion-panel:visible").slideToggle();
				})
	});
</script>

<!--  Blog Responsive  -->
<script>
$(".blogWrapRight h2").click(function(){
	$(".resSlide").slideToggle(200);
});
</script>

<!--   FreeTrial Pop   -->
<script>
	$(document).ready(function() {
		$(".freeTrail").click(function() {
			$("#freeTrial").addClass("activePop");
			$(".clPop").addClass("clPopA");
			$("body").css("overflow", "hidden");
		});
		$(".requestDemo").click(function() {
			$("#requestDemo").addClass("activePop");
			$(".clPop").addClass("clPopA");
			$("body").css("overflow", "hidden");
		});
		$(".clPop").click(function() {
			$("#freeTrial").removeClass("activePop");
			$("#requestDemo").removeClass("activePop");
			$(".clPop").removeClass("clPopA");
			$("body").css("overflow", "auto");
		});
	});
	// Key Function
	$(document).bind('keydown', function(e) {
		if (e.which === 27) {
			$("#freeTrial").removeClass("activePop");
			$("#requestDemo").removeClass("activePop");
			$("body").css("overflow", "auto");
			$(".clPop").removeClass("clPopA");
		}
	});
</script>



<!-- <script type="text/javascript">
			var config = {
			'.chosen-select'           : {},
			'.chosen-select-deselect'  : {allow_single_deselect:true},
			'.chosen-select-no-single' : {disable_search_threshold:10},
			'.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			'.chosen-select-width'     : {width:"95%"}
			}
			
			    for (var selector in config) {
			    $(selector).chosen(config[selector]);
			    }
			    $('.chosen-select').each(function() {
			    var $this = $(this);
			    $this.next().css({'width': '100%'});
			    });
			    $('#priority').trigger('chosen:updated');
			    $('#anyKeyWord').trigger('chosen:updated');
			    $('#anyKeyWord').chosen();
			    
</script>  -->


<script>
function loadOpenJobsChosenRequirementScripts(){
	 $('#country').val('${loginCountryName}'); 
     $('#state').val(''); 
     $('#city').val('');
     $("#anyKeyWord").val('').trigger("chosen:updated");
	 $("#allReqKeyWord").val('').trigger("chosen:updated");
	 $("#excludeReqKeyWord").val('').trigger("chosen:updated");
	 $("#requirementTitle").val('').trigger("chosen:updated");
	 $("#rate").val('').trigger("chosen:updated");
	 $("#minExp").val('').trigger("chosen:updated");
	 $("#maxExp").val('').trigger("chosen:updated");
	 $("#skills").val('').trigger("chosen:updated"); 
	 $("#noticePeriod").val('').trigger("chosen:updated"); 
	 $("#organizations").val('').trigger("chosen:updated");
	 $("#anyKeyWord").empty();
	 $("#allReqKeyWord").empty();
	 $("#excludeReqKeyWord").empty();
}
</script>



<script>
				// Loading data
				$(window).load(function() {
				    setTimeout(function() {
				        $(".filLoad").fadeOut();
				    }, 0);
				    
				});	  
						  
				// Adve Search
				$("#ADS").click(function(){
					$(".asMask").fadeIn(200);
					$(".adSearch").fadeIn(100);
				});
				// Remove or hide functions
				function adSearchClose(){
					$(".asMask").fadeOut(200);
					$(".adSearch").fadeOut(100);
				}
				
				$(".filterCta h3").click(function(){
					$(this).find("i").toggleClass("arrAc");
					$(this).next().slideToggle(200);
				});	
				
				$(".CP").click(function(){
					$(".filPop").fadeOut(100);
				});

				$(".Rcolor").click(function(){
					$(this).text('Referred');
				});
				
/* 				$(".asMask, .close-div").click(function(){
					$(".filter").removeClass("filAc");
					$(".asMask").fadeOut(200);
					$(".adSearch").fadeOut(100);
				});	 */	  
</script>

<script>
$(".LettSelec a").click(function(){
	$(".LettSelec a").removeClass("actL");
	$(this).toggleClass("actL");
});	  
</script>
<!-- Create Alert modal Popup -->
<div class="pop_mask"></div>
<!-- Modal -->
<div class="modal_createalert" id="myModal" role="dialog" >
	<div id="createAlertPopUpDiv"></div>
</div>
<!-- Create Alert modal Popup Ends -->
<script>
//Create Alert Poppup
function alertFunction() {
	$(".pop_mask").fadeIn();
	$(".modal_createalert").addClass("modal");
	$(".modal.modal_createalert").fadeIn();
}
function alertClose(){
	$(".pop_mask").fadeOut();
	$(".modal_createalert").removeClass("modal");
	$(".modal.modal_createalert").fadeOut();
	document.getElementById("myModal").style.display="none";
	
}
</script>
<!-- <script src="/resources/js/jobs/js/bootstrap-select.min.js"></script> -->
<script>
function createAlert(requirementUUID){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
        type : "GET",
        url: sourceleadUrl + "/ws/getJobDetailsForJobAlert?${_csrf.parameterName}=${_csrf.token}&requirementUUID="+requirementUUID,    
         success :function(data) {
        	 
        	 var json = JSON.parse(data);
        	 var experience = json['maxExp'];
        	  var state = '';
        	  var location ='';
        	  var country = '';
        	  var city = '';
        	  var zipCode ='';
        	  var rate = '';
        	  if(json['location'] != null && json['location'] != '' && json['location'] != 'undefined'){
        		  location = json['location'];
        	  }
        	  if(json['country'] != null && json['country'] != '' && json['country'] != 'undefined'){
        		  country = json['country'];
        	  }
        	  if(json['state'] != null && json['state'] != '' && json['state'] != 'undefined'){
        		  state = json['state'];
        	  }
        	  if(json['city'] != null && json['city'] != '' && json['city'] != 'undefined'){
        		  city = json['city'];
        	  }
        	  if(json['zipCode'] != null && json['zipCode'] != '' && json['zipCode'] != 'undefined'){
        		  zipCode = json['zipCode'];
        	  }
        	  if(json['maxRate'] != null && json['maxRate'] != '' && json['maxRate'] != 'undefined'){
        		  rate = json['maxRate']
        	  }
        	 result = "";
        	 result = result +' <form id="create_alert_form" name="create_alert_form" method="post">';
        	 result = result+ '<input type = "hidden" name="tabType" value="openjobsForm"/>';
        	 result = result +'	 <div class="modal-dialog modal-lg">';
        	 result = result +'     <div class="modal-content">';
        	 result = result +'       <div class="modal-header">';
        	 result = result +'         <button type="button" class="close" data-dismiss="modal" onClick="alertClose();">&times;</button>';
        	 result = result +'         <h4 class="modal-title">Job Alert</h4>';
        	 result = result +'       </div>';
        	 result = result +'       <div class="modal-body myForm clearfix">';
        	 result = result +'         <div class="form-group clearfix">';
        	 result = result +'         	<label class="col-md-3">Alert Title<span class="text-danger paddingLeft">*</span></label>';
        	 result = result +'         	<div class="col-md-9">';
        	 result = result +'         		<input type="text" class="form-control input-sm" value="'+json['jobTitle']+'"  placeholder="Alert Title" name="alertTitle" field-name="Alert Title" data-validation="required validate_length length2-100">';
        	 result = result +'         	</div>';
        	 result = result +'         </div>';
        	 result = result +'         <div class="form-group clearfix" id="emailDiv">';
        	 result = result +'         	<label class="col-md-3">Email<span class="text-danger paddingLeft">*</span></label>';
        	 result = result +'         	<div class="col-md-9">';
        	 result = result +'         		<input type="text" class="form-control input-sm" placeholder="Email" id="email" name="email" field-name="Email" onblur="validateAlertUserEmail();" data-validation="required validate_Space validate_length length2-100 validate_email">';
        	 result = result +'         	</div>';
        	 result = result +'         </div>';
        	 
        	 result = result +'         <div class="form-group clearfix">';
			 result = result +'         	<label class="col-md-3">Location</label>';
			 result = result +'         	<div class="col-md-9">';
			 result = result + '             <input type="text" class="form-control input-sm" name="location"  id="jobLocation" value="'+location+'" placeholder="Location" field-name="Locatin" >';
			 result = result + '                <i class="fa fa-refresh btn btn-primary profilerefresh-icon" title="Refresh" onclick="refreshGoogleApiLocation(\'jobLocation\',\'jobCountry\',\'jobState\',\'jobCity\',\'jobZipCode\');"></i>';
			 result = result +'         	</div>';
			 result = result +'         </div>';
			 
			 result = result +'         <div class="form-group clearfix">';
			 result = result +'         	<label class="col-md-3">Country</label>';
			 result = result +'         	<div class="col-md-9">';
			 result = result +'         		<input type="text" class="form-control" placeholder="Country" id="jobCountry" name="country" value="'+country+'" readonly>';
			 result = result +'         	</div>';
			 result = result +'         </div>';
			 
			 result = result +'         <div class="form-group clearfix">';
			 result = result +'         	<label class="col-md-3">State</label>';
			 result = result +'         	<div class="col-md-9">'; 
			 result = result +'         		<input type="text" class="form-control" placeholder="State" id="jobState" name="state" value="'+state+'" readonly>';
			 result = result +'         	</div>';
			 result = result +'         </div>';
			 
			 
			 result = result +'         <div class="form-group clearfix">';
			 result = result +'         	<label class="col-md-3">City</label>';
			 result = result +'         	<div class="col-md-9">';
			 result = result +'         		<input type="text" class="form-control" placeholder="City" id="jobCity" name="city" value="'+city+'" readonly>';
			 result = result +'         	</div>';
			 result = result +'         </div>';
			 
			 result = result +'         <div class="form-group clearfix">';
			 result = result +'         	<label class="col-md-3">Zip Code</label>';
			 result = result +'         	<div class="col-md-9">';
			 result = result +'         		<input type="text" class="form-control" placeholder="Zip Code" id="jobZipCode" name="zipcode" value="'+zipCode+'">';
			 result = result +'         	</div>';
			 result = result +'         </div>';
			 
        	 
        	 result = result +'         <div class="form-group clearfix">';
        	 result = result +'         	<label class="col-md-3">Experience</label>';
        	 result = result +'         	<div class="col-md-9">';
        	 result = result +'         		<select class="form-control chosen-select" id="experience" name="experience">';
        	 result = result +'         			<option  value="">Select Experience</option>';
        	 result = result +'								 <c:forEach begin="0" end="30" varStatus="loop">';
        	
        	 
        	 if(experience ==  "${loop.index}"){
    			 result = result +' <option value="${loop.index}" selected>${loop.index}</option>';
    		 }else{
    			 result = result +' <option value="${loop.index}">${loop.index}</option>';
    		 }
        	 
        	 
        	 result = result +'										</c:forEach>';
        	 result = result +'         		</select>';
        	 result = result +'         	</div>';
        	 result = result +'         </div>';
        	 result = result +'         <div class="form-group clearfix">';
        	 result = result +'         	<label class="col-md-3">Salary/Rate</label>';
        	 result = result +'	<div class="col-md-9">';
        	 result = result +'            <div class="input-group er ratetype">';
        	 result = result +'               <div class="input-group-btn tblminwidth-75">';
        	 result = result +'                 <select class="btn-primary select_picker form-control" id="rateCurrency" name="rateCurrency">';
        	 
        	 
        	 if(json['currencyCode'] == "INR"){
    			 result = result +'<option selected>INR</option>';
    		 }else{
    			 result = result +'<option>INR</option>';
    		 }
    		 
    		 if(json['currencyCode'] == "AUD"){
    			 result = result +'<option selected>AUD</option>';
    		 }else{
    			 result = result +'<option>AUD</option>';
    		 }
    		 
    		 if(json['currencyCode'] == "USD"){
    			 result = result +'<option selected>USD</option>';
    		 }else{
    			 result = result +'<option>USD</option>';
    		 }
    		 
    		 if(json['currencyCode'] == "GBP"){
    			 result = result +'<option selected>GBP</option>';
    		 }else{
    			 result = result +'<option>GBP</option>';
    		 }
    		 
    		 if(json['currencyCode'] == "SGD"){
    			 result = result +'<option selected>SGD</option>';
    		 }else{
    			 result = result +'<option>SGD</option>';
    		 }
    		 
    		 if(json['currencyCode'] == "EUR"){
    			 result = result +'<option selected>EUR</option>';
    		 }else{
    			 result = result +'<option>EUR</option>';
    		 }
        	 
        	 result = result +'               </select>';
        	 result = result +'               </div>';
        	 
        	 result = result +'              <input type="text" id="currentRate" name="rate" field-name="rate" data-validation="validate_Space validate_float validate_length length2-8" onblur="getRate();" data-validation-optional="true" value="'+rate+'" class="form-control" placeholder="Rate">';
        	 result = result +'               <div class="input-group-btn tblminwidth-75 ratetype">';
        	 result = result +'                 <select class="btn-primary select_picker form-control" id="rateType" name="rateType" field-name="Rate Type" data-validation-optional="true">';
        	 result = result +'                   <option value="">Rate Per</option>';
        	 
        	 if(json['rateType'] == "Hourly"){
    			 result = result +'<option selected>Hourly</option>'; 
    		 }else{
    			 result = result +'<option>Hourly</option>'; 
    		 }
    		 
    		 if(json['rateType'] == "Weekly"){
    			 result = result +'<option selected>Weekly</option>'; 
    		 }else{
    			 result = result +'<option>Weekly</option>'; 
    		 }
    		 
    		 if(json['rateType'] == "Bi-weekly"){
    			 result = result +'<option selected>Bi-weekly</option>'; 
    		 }else{
    			 result = result +'<option>Bi-weekly</option>'; 
    		 }
    		 
    		 if(json['rateType'] == "Semi-Monthly"){
    			 result = result +'<option selected>Semi-Monthly</option>'; 
    		 }else{
    			 result = result +'<option>Semi-Monthly</option>'; 
    		 }
    		 
    		 if(json['rateType'] == "Monthly"){
    			 result = result +'<option selected>Monthly</option>'; 
    		 }else{
    			 result = result +'<option>Monthly</option>'; 
    		 }
    		 
    		 if(json['rateType'] == "Bi-Monthly"){
    			 result = result +'<option selected>Bi-Monthly</option>'; 
    		 }else{
    			 result = result +'<option>Bi-Monthly</option>'; 
    		 }
    		 
    		 if(json['rateType'] == "Yearly"){
    			 result = result +'<option selected>Yearly</option>'; 
    		 }else{
    			 result = result +'<option>Yearly</option>'; 
    		 }
    		
        	 
        	 result = result +'                 </select>';
        	 result = result +'               </div>';
        	 result = result +'                <div class="clearfix"></div> <div class="clearfix"></div>';
        	 result = result +'             </div>';
        	 result = result +'           </div>';
        	 result = result +'         </div>';
        	 result = result +'         <div class="form-group clearfix">';
        	 result = result +'         	<label class="col-md-3">Industry</label>';
        	 result = result +'         	<div class="col-md-9">';
        	 result = result +'         		<select class="form-control chosen-select" data-placeholder="industryName" id="industry" name="industry">';
        	 result = result +'         		<option value="">--Select Industry--</option>';
        	 result = result +'                   <c:forEach var="industry" items="${industryList}">';
        	 if(json['industryName'] == "${industry}"){
    			 result = result +'<option value="${industry}" selected>${industry}</option>';
    		 }else{
    			 result = result +'<option value="${industry}">${industry}</option>';
    		 }
        	 result = result +'						</c:forEach>;'
        	 result = result +'         		</select>';
        	 result = result +'         	</div>';
        	 result = result +'         </div>';
        	
        	 result = result +'         <div class="form-group clearfix">';
        	 result = result +'         	<label class="col-md-3">Alert Frequency<span class="text-danger paddingLeft">*</span></label>';
        	 result = result +'         	<div class="col-md-9 dropdown-select">';
        	 result = result +'         		<select class="form-control" id="duration" name="duration" field-name="Alert Frequency" data-validation="required">';
        	 result = result +'         			<option selected value="">---Select Alert Frequency---</option>';
        	 result = result +'         			<option>Daily</option>';
        	 result = result +'         			<option>Weekly</option>';
        	 result = result +'         			<option>Monthly</option>';
        	 result = result +'         		</select>';
        	 result = result +'         	</div>';
        	 result = result +'         </div>';
        	 result = result +'         <div class="form-group clearfix">';
        	 result = result +'         	<label class="col-md-3">Keywords / Skills<span class="text-danger paddingLeft">*</span></label>';
        	 result = result +'         	<div class="col-md-9">';
        	 result = result +'			<select class="form-control chosen-select" data-placeholder="Keywords/Skills" multiple field-name="Keywords/Skills" id="candidateSkills" data-validation="required" name="skills">';
        	 result = result +'         		<c:forEach var="skill" items="${skillsList}">';
        	 result = result + "<option value='${skill.name}'>${skill.name}</option>";
        	 result = result +'						</c:forEach>;'
        	 result = result +'         		</select>';
        	 result = result +'         	</div>';
        	 result = result +'         </div>';
        	 result = result +'       </div>';
        	 result = result +'       <div class="modal-footer">';
        	 result = result +'         <button type="submit" id="alert_button"  class="btn btn-primary btn-sm">Save</button>';
        	 result = result +'         <button type="button" class="btn btn-default btn-sm ml10" data-dismiss="modal" onClick="alertClose();">Cancel</button>';
        	 result = result +'       </div>';
        	 result = result +'     </div>';
        	 result = result +'   </div>';	
        	 result = result +'</form>';
         $("#createAlertPopUpDiv").empty();
         $("#createAlertPopUpDiv").append(result);
         publicPageLocationAuto('jobLocation','jobCountry','jobState','jobCity','jobZipCode');
         var config = {
        			  '.chosen-select'           : {},
        			  '.chosen-select-deselect'  : {allow_single_deselect:true},
        			  '.chosen-select-no-single' : {disable_search_threshold:10},
        			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
        			  '.chosen-select-width'     : {width:"95%"}
         }
         for (var selector in config) {
        	    $(selector).chosen(config[selector]);
        	    }
        	    $('.chosen-select').each(function() {
        	    var $this = $(this);
        	    $this.next().css({'width': '100%'});
        	    })
        	    
        	    var select, chosen;
        	    select = $("#candidateSkills");
        	    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
        	    chosen = select.data('chosen');
        	    
        	    $("#candidateSkills").chosen().change(function(event) {
        	    	var allSkills = $(event.target).val();
        	    	$.ajax({
        		    	type: "POST",
        		        url: sourceleadUrl + "/ws/profileBySkillSet?${_csrf.parameterName}=${_csrf.token}&allSkills="+allSkills, 
        		        success: function(response) {
        		        	if (response.response === "AWKWARD") {
        		        	    location.href = "/errorPage";
        		        	} else if (response.response === "SUCCESS") {
        		        		var primarySkill = response.object;
      		        	      	$("#primarySkill").val(primarySkill);
        		        	} else if (response.indexOf('Sign In') !== -1) {
        		        		location.replace(sourceleadUrl);
        		        	} else {
        		        	    showError("Operation failed, please try again");
        		        	}
        		        },
        		    });
        	    });
        	    
        	  if(json['skills'] != 'undefined' && json['skills'] != null && json['skills'] != ''){
        		  
        		  var alertSkillsList = json['skills'].split(',');
               	  if(alertSkillsList.length > 1){
               		 for (var i=0; i < alertSkillsList.length; i++) {
    	        			 var option = $("<option>").val(alertSkillsList[i].replace(",","")).text(alertSkillsList[i].replace(",",""));
    	        			 select.append(option);
    	 		             select.find(option).prop('selected', true);
    	 		             select.find(option).siblings("[value='"+ alertSkillsList[i].replace(",","")+"']").remove();
    	 	        		 $("#candidateSkills").trigger("chosen:updated");
    	                 }
               	  }else{
               		 var option = $("<option>").val(alertSkillsList).text(alertSkillsList);
          			 select.append(option);
    		             select.find(option).prop('selected', true);
    		             select.find(option).siblings("[value='"+ alertSkillsList+"']").remove();
    	        		 $("#candidateSkills").trigger("chosen:updated");
               	  }
        	  }
        	 
            
               chosen.search_field.on('keyup', function(e)
        		    {
        		    	   var skillsArray = this.value.split(",");
        			    	 if(skillsArray.length > 1){
        		        		 for (var i=0; i < skillsArray.length; i++) {
        		        			 if(skillsArray[i] == '' || skillsArray[i] == 'undefined' || skillsArray[i] == null)
        		        		     continue;
        		        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
        		        			 select.append(option);
        		 		             select.find(option).prop('selected', true);
        		 		             select.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
        		 	        		 $("#candidateSkills").trigger("chosen:updated");
        		                 }
        			    	 }else{
        		    			var newSkill = this.value.replace(","," ");
        		    			 if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
        		                {
        		    				 var option = $("<option>").val(newSkill).text(newSkill);
        		                     select.append(option);
        		                     select.find(option).prop('selected', true);
        		                     $("#candidateSkills option").each(function(){
        		                         $(this).siblings("[value='"+ this.value+"']").remove();
        		                   });
        		                   $("#candidateSkills").trigger("chosen:updated");
        		                }
        				       
        	    	         }
        		    });
         }
	 });
	
	 
}


</script>


<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
var sourceleadUrl ='${sourceleadUrl}';
         $('body').on('submit', '#create_alert_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		
        		e.preventDefault();
        	    var formData = getFormData1("create_alert_form");
        	    
        		$.ajax({
		        		    type : "POST",
							data: formData,
			  		        processData: false,
			  		        contentType:false, 
        					url : sourceleadUrl + "/ws/storeJobAlert?${_csrf.parameterName}=${_csrf.token}",
        					success : function(result) {
        						if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        							showError(result.responseMessage);
        							 $("#loadinggif").removeAttr('style');
        	        	    		}
        	                  		else if (result.response === "AWKWARD") {
        	                  			location.href = "/errorPage";
        	        	    		}
        	                  		else if(result.response === "SUCCESS"){
        	                  			 $("#loadinggif").removeAttr('style');
        	                  			location.replace("/jobs"); 
        				        	    }
        	                  		else if (result.indexOf('Sign In') !== -1) {
        	                  			location.replace(sourceleadUrl);
        	        				} else {
        	        					showError("Operation failed, please try again");
        	        					 $("#loadinggif").removeAttr('style');
        	        				}
        					},
        				});
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
        
         $('body').on('blur', '#create_alert_form', function() {
        		$("#create_alert_form").showHelpOnFocus();
        		$("#create_alert_form").validateOnBlur(false, validationSettings);  
        	});
         
         
     
	</script>


<script type="text/javascript">
jQuery.noConflict( true );
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
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


<script>
var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
     }; 
</script>

<script>
///////////////////// Back To Top 
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
function getRate(){
	var rate = document.getElementById("currentRate").value;
	if(rate != 'undefined' && rate !=  null && rate != ''){
		$("#rateType").removeAttr("data-validation-optional");
		$("#rateType").attr("data-validation", "required");
	}else{
		$("#rateType").removeAttr("data-validation");
		$("#rateType").attr("data-validation-optional", "true");
	}
}
</script>

<script>
function validateAlertUserEmail(){
	var email = document.getElementById("email").value;
	var sourceleadUrl ='${sourceleadUrl}';
	if(email != null && email != ''){
		$("#alert_button").attr("disabled", "disabled");
		$.ajax({
		   	type: "GET",
		       url: sourceleadUrl + "/ws/validateAlertUserEmail?${_csrf.parameterName}=${_csrf.token}&email="+email,
		       success: function(data) {
		    		if(data != null && data != ''){
					     if(data == 'Invalid'){
					    	    $('#emailDiv').find('p.jquery_form_error_message').remove(); 
					    	    $("input#email").after("<p class='jquery_form_error_message'>You can configure maximum 5 job alerts</span>");
			    				$('input#email').attr('record-exist','yes');
			    				$('input#email').attr('record-exist-errorMsg','You can configure maximum 5 job alerts'); 
					     } 
					     
				       	else{
				       		 $("#alert_button").removeAttr("disabled");
							 $('input#email').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
							 $( "#alert_button" ).removeAttr("style");
							 $( "#alert_button" ).click(function() {
							 if ($(this).validate(false, validationSettings)){
							 }
							});
				       	}
			       	}
		    	   
		      },
		       
		});
	}
}

</script>

<script>
function publicPageLocationAuto(locationId,countryId,stateId,cityId,zipCodeId,type) {
    initialize(locationId,countryId,stateId,cityId,zipCodeId,type);
    geolocate();
}
function initialize(locationId,countryId,stateId,cityId,zipCodeId,type) {
    if(type == "invite"){
    	 var input = (document.getElementById(locationId));
    	    var options = {
    	        types: ['geocode'],
    	        componentRestrictions: {
    	        	country: ["IN","US","AU","SG","GB","FR"]
    	        }
    	    };
    	    autocomplete = new google.maps.places.Autocomplete(input, options);
    }else{
    	autocomplete = new google.maps.places.Autocomplete((document.getElementById(locationId)), {
            types: ['geocode'],
      
        });
    }
   
    
    
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
  place = autocomplete.getPlace();
  var address = {};
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    switch (addressType) {
      case "locality":
        address.city = place.address_components[i].long_name;
        break;
      case "administrative_area_level_1":
        address.state = place.address_components[i].long_name;
        break;
      case "country":
        address.country = place.address_components[i].long_name;
        break;
      case "postal_code":
        address.zipcode = place.address_components[i].long_name;
        
    }
  }
  
  $("#"+countryId).val(address.country).change();
  $("#"+stateId).val(address.state).change();
  $("#"+cityId).val(address.city).change();
  $("#"+zipCodeId).val(address.zipcode).change();
  if(type == "invite"){
	  getTimezonesList();
  }
});
}
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var geolocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            document.getElementById("latitude").value = latitude;
            document.getElementById("longitude").value = longitude;
            autocomplete.setBounds(new google.maps.LatLngBounds(geolocation, geolocation));
        });
    }
}
</script>
<script>
function refreshGoogleApiLocation(locationId,countryId,stateId,cityId,zipCodeId){
	$("#"+locationId).val('');
	$("#"+countryId).val('');
	$("#"+stateId).val('');
	$("#"+cityId).val('');
	$("#"+zipCodeId).val('');
}
</script>
<script type="text/javascript">
function tagItBykeyForSkills(e){
	if(e.which == 0 || e.which == 13 || e.which == 9 || e.which == 188){
		 $(document).click();
		 return true;
	}
}
</script>
<script type="text/javascript">
function preventTabkey(e){
	 if(e.which == 9){
		 e.preventDefault();
	 }
}
</script>

<!--   Free trial pop   -->
 
<div class="clPop"><img src="/resources/static-web/images/menu-close.png" alt=""/></div> 
<section class="freeTrialPop" id="requestDemo">
	<div class="Rdemo">
    	<img src="/resources/static-web/images/demo-1.png" alt=""/>
    	<h3>Request a Demo</h3>
    	<p>We are so glad for your interest in SourceLead.
Please mail us at </p>
		<a href="mailto:info@sourcelead.com"><i class="fa fa-envelope"></i>info@sourcelead.com</a>
		<span>We will get back to you As soon as possible</span>
    </div>
</section>
<section class="freeTrialPop" id="freeTrial">

	<div class="Rdemo">
    	<img src="/resources/static-web/images/demo-1.png" alt=""/>
    	<h3>Free Trial</h3>
    	<p>We are so glad for your interest in SourceLead.
Please mail us at </p>
		<a href="mailto:info@sourcelead.com"><i class="fa fa-envelope"></i>info@sourcelead.com</a>
		<span>We will get back to you As soon as possible</span>
    </div>    
</section>

<script>
function applyJob(requirementUUID){
	$("#requirementUUID").val(requirementUUID);
	/*  if('${SourceLeadWeb_user.id}' == null && '${SourceLeadWeb_user.id}' == ''){
		 $(".modal.modalApplyjob").fadeIn();
		$(".comMask").fadeIn();
		loginPopup(requirementUUID)
	 } */	
    if('${SourceLeadWeb_user.id}' != null && '${SourceLeadWeb_user.id}' != ''){
    	location.replace(sourceleadUrl+"/dashboard"+"?requirementUUID="+requirementUUID);  
    }else{
    	$(".modal.modalApplyjob").fadeIn();
        $(".comMask").fadeIn();
        loginPopup(requirementUUID)
    }

}
function closeApplyJob(){
	$(".modal.modalApplyjob").fadeOut();
	$(".comMask").fadeOut();
	$(".signWrap").addClass("orgOutWrap");
	$(".signWrap").removeClass("forPassOut");
	$(".ForPasSlide").attr("style","display:none");
	$("#signinDiv").attr("style","display:block");
}
</script>
 
 <!--   Free trial pop   --> 
 <script>
function forgotPassword(){
	$(".signWrap").removeClass("orgOutWrap");
	$(".signWrap").addClass("forPassOut");
	$("#signinDiv").removeAttr("style");
	$("#signinDiv").attr("style","display:none");
	$("#forgotPasswordDiv").removeAttr("style");
	$(".ForPasSlide").attr("style","display:block");
}
</script>
 <script>
 function loginPopup(requirementUUID){
	 var result = "";
	 result = result +'<div class="pull-left">';
	 result = result +'<button type="button" class="btn btn-primary btn-sm mr">Login</button><button type="button" onclick="guestUserRest(\''+requirementUUID+'\');" class="btn btn-warning btn-sm">Apply</button>';
	 result = result +'</div>';
	 $("#gustLoginId").empty();
	 $("#gustLoginId").append(result);
 } 
 </script>
 <script>
 function guestUserRest(requirementUUID){
	 var sourceleadUrl ='${sourceleadUrl}';
		window.location.href = "/guestApplyToJob?&requirementUUID="+requirementUUID;
 }
 </script>                                                                                     
   <!-- <script type="text/javascript" src="http://s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5a60382a93e935c2"></script> -->
	<script type="text/javascript" src="http://s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5a6034e610465d87"></script>
	   
   <script src="/resources/js/formvalidator.js"></script>
<jsp:include page="../socialinviterscripts.jsp" />