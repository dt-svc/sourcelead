<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet"> -->
<link href="resources/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/reset.css">
<link href="resources/css/jobs/css/source-style.css" rel="stylesheet">
<link href="resources/css/jobs/css/source-responsive.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jobs/css/chosen.min.css">
<link rel="stylesheet" href="resources/css/jobs/css/bootstrap-select.min.css">
<link rel="stylesheet" href="resources/css/jobs/css/sourcelead-main.css">
<link href="resources/css/jobs/css/main-style.css" rel="stylesheet">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<link href="resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="resources/css/dataTablePagination.css" rel="stylesheet" type="text/css">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery.timepicker.css">
<link rel="stylesheet" href="resources/css/timesheet.css" type="text/css" >

 <script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=AIzaSyDzIM1uth14u-kSIN_jZ75pRelInyGnYWU" type="text/javascript"></script>
<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/masterHeaderAction.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/jquery-ui.js"></script>
<script src="resources/js/jquery-fun.js"></script>
<script src="resources/js/jquery.dataTables.min.js"></script>
<script src="resources/js/jquery.timepicker.js"></script>
<script src="resources/js/themeswitcher.js"></script>
<script type="text/javascript" src="resources/js/modernizr.custom.86080.js"></script>
<script src="resources/js/jobs/js/controls.js"></script>
<script src="resources/js/jquery-1.12.4.min.js"></script>
<script src="resources/js/jquery.sldr.js"></script>
<script src="resources/js/masonry.pkgd.min.js"></script>
<script src="resources/js/jobs/js/bootstrap.js"></script>
<script src="resources/js/jobs/js/bootstrap-select.min.js"></script>
<script src="resources/js/jobs/js/jquery-1.11.3.min.js"></script>
<script src="resources/js/jobs/js/chosen.jquery.min.js"></script>


<script type="text/javascript">
window.onload = function () {
	
	setTimeout(function() {
		$(".pageLoad").fadeOut();
	}, 1000);
	
	if('${requirementUUID}' != null && '${requirementUUID}' != '' && '${requirementUUID}' != 'undefined'){
		getOpenJobsRequirementSummary('${requirementUUID}');
		getDropDownCount();
	}else{
		getOpenJobsELKList();
		getDropDownCount();
	}
 };
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
display: none;}


a.alertBtn {
    padding: 8px 15px !important;
}
.ratetype p {
    left: -45px !important;
    position: absolute;
    bottom: -14px;
}
.adSearch .chosen-container .search-field input[type="text"] {
width:100% !important;
}
a.alertBtn {
    padding: 8px 15px !important;
}
.jobResultsCta.job_container {
cursor: inherit !important;
}
.jobResultsCta.job_container h3{
	cursor: pointer; font-size: 18px; width: auto; clear: both; float: left;
}
.jobResultsCta.job_container h4{
	cursor: pointer; font-size: 16px; width: auto; clear: both; float: left;
}
.jobDetails .jobResultsInn h3  {
	cursor: pointer; font-size: 18px; width: auto; clear: both; float: left;
}
.jobResultsInn.job_ResultsInn h3 {
cursor: inherit; font-size: 18px; width: auto; clear: both; float: left; 
}
.jobResultsInn.job_ResultsInn h4 {
cursor: pointer; font-size: 16px; width: auto; clear: both; float: left;
}
.profilerefresh-icon {
	position: absolute; right:16px; top:1px; height: 28px; line-height: 14px;
}
.pac-container {
z-index: 1000000000 !important;
}
.pac-container::after {
	display:none;
}
</style>


<!-- Content Area -->
<div  id="loadTime"></div> 
<header class="headerInpage">
<section class="headTop headTopFix">
	<div class="headTopInn">
    	<ul>
        	<li><a href="tel:(214) 295-5535"><i class="fa fa-phone"></i> (214) 295-5535</a></li>
            <li><a href="mailto:info@sourcelead.com"><i class="fa fa-envelope-o"></i>info@sourcelead.com</a></li>
            <li class="hideres"><a href="${sourceleadUrl}/login"><i class="fa fa-user-o"></i> Login</a></li>
            <div class="clear"></div>
        </ul>
    	<div class="clear"></div>    
    </div>
</section>
<div class="head_inn">
    <div class="success_pop" id="selector" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
  <div class="logo"><a href="home.html"><img src="resources/css/jobs/images/logo.png" alt="SourceLead" title="SourceLead"/></a></div>
  <nav>
  	<ul class="menu">
    	<li><a href="home.html"><i class="fa fa-home"></i></a></li>
		<li><a href="${sourceleadUrl}/openRequirements" class="rcolor">Jobs</a></li>
        <li><a href="javascript:void(0)" class="ma">What we do<i class="fa fa-caret-down"></i></a>
			<ul>
				<li><a href="../crowdsourcing.html">Crowdsourcing</a></li>
            	<li><a href="../ats.html">ATS</a></li>
                <li><a href="../vms.html">VMS</a></li>
                <li><a style="border-bottom:none;" href="../timesheet.html">Time Management</a></li>
            </ul>
        </li>
        <li><a href="javascript:void(0)" class="ma">Resources<i class="fa fa-caret-down"></i></a>
        	<ul>
            	<li><a href="../demo.html">Product Videos</a></li>
                <li><a href="javascript:void(0)" class="requestDemo">Request Personal Demo</a></li>
                <li><a style="border-bottom:none;" href="faqs.html">FAQs</a></li>
            </ul>
        </li>
        <li><a href="javascript:void(0)">About SourceLead<i class="fa fa-caret-down"></i></a>
        	<ul>
            	<li><a href="../about-us.html">About SourceLead</a></li>
                <li><a href="../team.html">Our team</a></li>
                <!--<li><a href="about-us.html#client">Clienef="testimonial.html">Testimonial</a></li>
                <!--<li><a href="careers.html">Careers</a></li>-->
                <li><a style="border-bottom:none;" href="../contact.html">Contact</a></li>
            </ul>        
        </li>
        <li><a href="blog4.html">Blogs</a></li>
        <li><a href="javascript:void(0)" class="freeTril freeTrail">Free Trial</a></li>
        <div class="clear"></div>
    </ul>
  </nav>
  
  <section class="responsiveMenu">
  		<a href="javascript:void(0)" class="freeTril freeTrail">Free Trial</a>
       <span id="resSe" class=""><i class="fa fa-search"></i></span>
        <i id="menuIcon" class="fa fa-bars"></i>
  </section>
  
  <div class="clear"></div>
</div>
</header>
<section class="menuGapInpage"></section>

	<div class="innerPageSearch">
		<section class="searchWrap">
			<span><i class="fa fa-search"></i><input type="text" placeholder="Search" id= "openJobsRequirementTitleId" value="${openJobsTitle}" class="searchHome"></span>
			<span><i class="fa fa-map-marker"></i><input type="text" placeholder="Location" value="${openJobsLocation}" id="openJobsLocationId" class="searchHome"></span>
			<span><i class="fa fa-briefcase"></i> <p id = "dropDownCountId" value="${openJobsExperience}"></p></span>
			<span><input type="button" value="Search" class="searchBtn" onclick="getOpenJobsELKList(); clearFilters();"></span>
			<h3><b id="ADS">Advanced Search</b></h3>
			<div class="clear"></div>
		</section>
	</div>
	
<section class="innerContent">
<input type="hidden" name="openJobsOffset" id="openJobsOffset" value="0"/>
		<div class="filter">
			<h2>Fliter your search <i class="fa fa-refresh" aria-hidden="true" onclick="clearFilters(); getOpenJobsELKList();" style="cursor: pointer;"></i></h2>
			<div class="filterCta" id="dateFilterId">
				<h3>Posted on<i class="fa fa-caret-down arrAc"></i></h3>
				<ul id="show">
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">Today
						  <input type="radio" name="dateFilter" value="today" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">1 day ago
						  <input type="radio" name="dateFilter" value="1 Day Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">3 days ago
						  <input type="radio" name="dateFilter" value="3 Days Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">1 Week ago
						  <input type="radio" name="dateFilter" value="1 Week Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">2 Weeks ago 
						  <input type="radio" name="dateFilter" value="2 Weeks Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">1 Month ago
						  <input type="radio" name="dateFilter" value="1 Month Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">3 Months ago
						  <input type="radio" name="dateFilter" value="3 Months Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>		
				</ul>
			</div>
			
			
			<div class="filterCta" id="countryFilterId">
				<h3>Location <i class="fa fa-caret-down"></i></h3>
				<ul>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">Australia
						  <input type="radio" name="countryFilter" value="Australia" onclick="requirementsFilter(); getOrgsList('none','main');"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">France
						  <input type="radio" name="countryFilter" value="France" onclick="requirementsFilter(); getOrgsList('none','main');"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">India
						  <input type="radio" name="countryFilter" value="India" onclick="requirementsFilter(); getOrgsList('none','main');"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">Singapore
						  <input type="radio" name="countryFilter" value="Singapore" onclick="requirementsFilter(); getOrgsList('none','main');"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">United Kingdom
						  <input type="radio" name="countryFilter" value="United Kingdom" onclick="requirementsFilter(); getOrgsList('none','main');"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">United States
						  <input type="radio" name="countryFilter" value="United States" onclick="requirementsFilter(); getOrgsList('none','main');"/><div class="control__indicator_ra"></div>
						</label>
					</li>
				</ul>
			</div>

			<div class="filterCta" id="empTypediv">
				<h3>Employment Type<i class="fa fa-caret-down"></i></h3>
				<ul>
					<li class="control-group">
						<label class="control control--checkbox">C2C
						  <input type="checkbox" name="typeFilter" value="C2C" onclick="requirementsFilter();"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">W-2
						  <input type="checkbox" name="typeFilter" value="W-2" onclick="requirementsFilter();"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">C2H
						  <input type="checkbox" name="typeFilter" value="C2H" onclick="requirementsFilter();"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Contract
						  <input type="checkbox" name="typeFilter" value="Contract" onclick="requirementsFilter();"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Part Time
						  <input type="checkbox" name="typeFilter" value="Part Time" onclick="requirementsFilter();"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Full Time
						  <input type="checkbox" name="typeFilter" value="Full Time" onclick="requirementsFilter();"/><div class="control__indicator"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control control--checkbox">Other
						  <input type="checkbox" name="typeFilter" value="Other" onclick="requirementsFilter();"/><div class="control__indicator"></div>
						</label>
					</li>
				</ul>
			</div>
			
			<div class="filterCta">
				<h3 onclick="getOrgsList('none','main');">Companies<i class="fa fa-caret-down"></i></h3>
				<ul id="orgList"></ul>
			</div>														
			
			<!-- <div class="filLoad">
				<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>
				<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>
				<h2 class="gradi"></h2>
			</div> -->
			
		</div>
		
		<div class="jobResults jobResults_New">
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
			<div id= "openJobsRequirementsList"></div>
			<div class="jobResultsNoRecords" id="noResultsDiv"></div>
			<div id="loadMoreDiv"></div> 
			<div class="loadJob" onClick="loadMoreReq();" id="loadMoreReq" style="display: none; cursor: pointer;">View more</div>
			<div id= "openJobsRequirementsSummary"></div>
			
		
			
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
					<a id="all" onclick="getOrgsList('none','sub');">ALL</a>
					<a onclick="getOrgsList('A','sub');">A</a>
					<a onclick="getOrgsList('B','sub');">B</a>
					<a onclick="getOrgsList('C','sub');">C</a>
					<a onclick="getOrgsList('D','sub');">D</a>
					<a onclick="getOrgsList('E','sub');">E</a>
					<a onclick="getOrgsList('F','sub');">F</a>
					<a onclick="getOrgsList('G','sub');">G</a>
					<a onclick="getOrgsList('H','sub');">H</a>
					<a onclick="getOrgsList('I','sub');">I</a>
					<a onclick="getOrgsList('J','sub');">J</a>
					<a onclick="getOrgsList('K','sub');">K</a>
					<a onclick="getOrgsList('L','sub');">L</a>
					<a onclick="getOrgsList('M','sub');">M</a>
					<a onclick="getOrgsList('N','sub');">N</a>
					<a onclick="getOrgsList('O','sub');">O</a>
					<a onclick="getOrgsList('P','sub');">P</a>
					<a onclick="getOrgsList('Q','sub');">Q</a>
					<a onclick="getOrgsList('R','sub');">R</a>
					<a onclick="getOrgsList('S','sub');">S</a>
					<a onclick="getOrgsList('T','sub');">T</a>
					<a onclick="getOrgsList('U','sub');">U</a>
					<a onclick="getOrgsList('V','sub');">V</a>
					<a onclick="getOrgsList('W','sub');">W</a>
					<a onclick="getOrgsList('X','sub');">X</a>
					<a onclick="getOrgsList('Y','sub');">Y</a>
					<a onclick="getOrgsList('Z','sub');">Z</a>
					<a onclick="getOrgsList('Others','sub');">Others</a>
				</div>
				
				<ul id="remainingOrgs" class="clearfix">
				</ul>		
			</div>
			<div class="filPopBot clearfix">
				<div class="pull-right">
<!-- 					<input type="button" class="btn btn-danger CP" value="Cancel"/> -->
					<input type="button" class="btn btn-primary" onclick="clearcheck();" value="Clear All"/>
					<input type="button" class="btn btn-success CP" onclick="requirementsFilter();" value="Search"/>
				</div>
			</div>
		</div>
		
</section>	
<section class="asMask" onclick="adSearchClose();"></section>


<section class="adSearch">
		
          <form:form id="openJobsAdvancedForm" name="openJobsAdvancedForm"  enctype='multipart/form-data' >
          <div class="cand-advsrch-popup"> <a href="javascript:void(0)" class="close-div" onClick="adSearchClose();loadOpenJobsChosenRequirementScripts();"><i class="fa fa-close"></i></a>
            <div class="row">
              <div class="col-md-4">
                <label>Any of Keyword <i class="fa fa-info-circle" title="Any of Keyword"></i></label>
                
                <div class="clearfix">
				 <select class="chosen-select form-control input-sm" multiple data-placeholder="Any Keyword" id="anyKeyWord" name="anyKeyWord"></select>
				 </div>
              </div>
              <div class="col-md-4">
                <label>All Keywords <i class="fa fa-info-circle" title="All Keywords"></i></label>
                <div class="clearfix">
				 <select class="chosen-select form-control input-sm" multiple data-placeholder="All KeyWord" id="allReqKeyWord" name="allKeyWord"></select>
				 </div>
                
              </div>
              <div class="col-md-4">
                <label>Exclude Keyword <i class="fa fa-info-circle" title="Exclude Keyword"></i></label>
                <div class="clearfix">
				 <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude KeyWord" id="excludeReqKeyWord" name="excludeKeyWord"></select>
				 </div>
                
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <h4>Details:</h4>
              </div>
              <div class="clearfix">
                <div class="col-md-4">
                  <label>Job Title</label>
                  <div class="clearfix">
                    <input type="text" id="requirementTitle" name="requirementTitle" class="form-control input-sm" placeholder="Job Title">
                  </div>
                </div>
                <div class="col-md-8">
                  <div class="row">
                    <div class="col-md-6">
                      <label>Rate</label>
                      <div class="input-group">
                        <div class="input-group-btn selp-custom-min custommin">
                          <select class="selectpicker" id="currencyCode" name="currencyCode" >
                            <option selected disabled>INR</option>
                            <option>AUD</option>
                            <option>USD</option>
                            <option>GPB</option>
                            <option>SGD</option>
                            <option>EUR</option>
                          </select>
                        </div>
                        <input type="text" class="form-control input-sm" name="minRate" id="minRate" value="" placeholder="Min Rate">
                      </div>
                    </div>
                    <div class="col-md-6">
                      <label>&nbsp;</label>
                      <div class="input-group">
                        <input type="text" class="form-control input-sm" value="" name="maxRate" id="maxRate" placeholder="Max Rate">
                        <div class="input-group-btn selp-custom-min custommin">
                          <select class="selectpicker">
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
                </div>
              </div>
              <div class="clearfix">
                <div class="col-md-4">
                  <label>Experience</label>
                  <div class="clearfix row">
                    <div class="col-md-6">
                      <select class="chosen-select" id="minExp" name="minExp"  data-placeholder="Min Years">
                       <option value="" selected>Min Years</option>
                       <c:forEach begin="0" end="24" varStatus="loop">
									    <option value="${loop.index}">${loop.index} Years</option>
									</c:forEach>
                      </select>
                    </div>
                    <div class="col-md-6">
                       <select class="chosen-select" id="maxExp" name="maxExp" data-placeholder="Max Years">
                         <option value="" selected>Max Years</option>
                         <c:forEach begin="0" end="24" varStatus="loop">
									    <option value="${loop.index}">${loop.index} Years</option>
									</c:forEach>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="col-md-4">
                  <label>Notice Period</label>
                  <div class="clearfix">
                    <select class="chosen-select" name="noticePeriod" id="noticePeriod" data-placeholder="Notice Period" multiple>
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
                <div class="col-md-4">
							  <label>Country</label>
							  <div class="clearfix adv-srh-chosen">
								 <select class="chosen-select" name="country" id="country" field-name="Country"  >
								  <option value="">--select Country--</option>
								     <c:forEach var="cntry" items="${countryList}">
							                     <option value="${cntry.name}" ${cntry.name  == loginCountryName ? 'selected' : ''}>${cntry.name}</option>
						                </c:forEach>
				                 </select>
							  </div>
						  </div>
                <div class="col-md-4"> 
							  <label>State</label>
							  <div class="clearfix adv-srh-chosen">
								  <select  class="chosen-select" name="state" id="state" field-name="State"  >
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
					  <select  class="chosen-select" name="city" id="city" field-name="City"  >
					      <option value="">---Select City---</option>
                               </select>
				  </div>
			  </div>
              </div>
              
              <div class="clearfix">
                <div class="col-md-4">
                <label>Organizations <i class="fa fa-info-circle" title="Organizations"></i></label>
                <div class="clearfix">
				 <select class="chosen-select form-control input-sm" multiple data-placeholder="Organizations" id="organizations" name="organizations"></select>
				 </div>
                
              </div>
              
                <div class="col-md-8">
                  <label>Skills</label>
                  <div class="clearfix">
                     <select class="chosen-select"  id="skills" name="skills" multiple data-placeholder="Select Skills">
                      <c:forEach var="skill" items="${skillsList}">
             				 <c:if test="${fn:contains(requirement.reqSkillsListString, skill)}">
	                		  	 <option  selected value="${skill.name}">${skill.name}</option>
	                		 </c:if>
	                		 <c:if test="${!fn:contains(requirement.reqSkillsListString, skill)}">
	                		  	 <option  value="${skill.name}">${skill.name}</option>
	                		 </c:if>
                    </c:forEach>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <div class="row form-footer">
              <div class="pull-right mr"> 
              <input type="button" value="Search" id="advancedOpenJobsSearch" onclick="openJobSearchRequirement();loadOpenJobsChosenRequirementScripts();" class="btn btn-sm btn-primary"/>
               <input type="button" value="Cancel" onClick="adSearchClose();loadOpenJobsChosenRequirementScripts();" class="btn btn-sm btn-default ml10"/> 
              </div>
            </div>
          </div>
   </form:form>
	</section>        
<!-- Content Area Ends-->

<footer>
	<div class="footerInn">
    	<div class="footerInnCta resFoot">
        	<h3>SourceLead</h3>
            <ul>
            	<li><a href="about-us.html"><i class="fa fa-caret-right"></i>About us</a></li>
                <li><a href="team.html"><i class="fa fa-caret-right"></i>Our team</a></li>
                <!--<li><a href="about-us.html#client"><i class="fa fa-caret-right"></i>Clients</a></li>-->
                <li><a href="testimonial.html"><i class="fa fa-caret-right"></i>Testimonial</a></li>
                <!--<li><a href="careers.html"><i class="fa fa-caret-right"></i>Careers</a></li>-->
                <li><a href="contact.html"><i class="fa fa-caret-right"></i>Contact us</a></li>
            </ul>
        </div>
    	<div class="footerInnCta resFoot">
        	<h3>What we do</h3>
            <ul>
				<li><a href="../crowdsourcing.html"><i class="fa fa-caret-right"></i>Crowdsourcing</a></li>
            	<li><a href="../ats.html"><i class="fa fa-caret-right"></i>ATS</a></li>
                <li><a href="../vms.html"><i class="fa fa-caret-right"></i>VMS</a></li>
                <li><a href="../timesheet.html"><i class="fa fa-caret-right"></i>Time Management</a></li>
            </ul>
        </div> 
    	<div class="footerInnCta">
<div class="hBlogs">
            	<a href="../blog4.html">
                	<span>Loyal Relationships with Recruiters</span>
                    <b>FEB 20, 2017</b>
                </a>            
            	<a href="../blog3.html">
                	<span>Recruiting Through Social Media Platform</span>
                    <b>FEB 15, 2017</b>
                </a>            
            	<a href="../blog.html">
                	<span>Existing Process without using any Tool</span>
                    <b>DEC 20, 2016</b>
                </a>
            	<a href="../blog4.html">Viwe all</a>                                                
            </div>
        </div>  
    	<div class="footerInnCta">
        	<h3>Get in touch</h3>
            <a href="mailto:info@sourcelead.com;"><b><i class="fa fa-envelope-o"></i>info@sourcelead.com</b></a>
			<a href="tel:(214) 295-5535"><b><i class="fa fa-phone"></i>USA : (214) 295-5535</b></a>
			<a href="tel:(972) 559-3641"><b><i class="fa fa-fax"></i>FAX : (972) 559-3641</b></a>
			<a href="tel:040-6700-7676"><b><i class="fa fa-phone"></i>IND : +91. 40-6700-7676</b></a>   
          <h4>Go social</h4>
            <div class="socila">
            	<a href="javascript:void(0)"><img src="resources/css/jobs/images/in.png" alt=""/></a>
                <a href="javascript:void(0)"><img src="resources/css/jobs/images/fb.png" alt=""/></a>
                <a href="javascript:void(0)"><img src="resources/css/jobs/images/tw.png" alt=""/></a>
                <a href="javascript:void(0)"><img src="resources/css/jobs/images/gp.png" alt=""/></a>
            </div>
        </div>                        
    <div class="clear"></div>
    </div>
    <jsp:include page="../masterFooter.jsp" />
    <div class="copyrightOut">
    	<div class="copyright">
    	<b>© 2016 SourceLead. All rights reserved.</b>
        <span>
        	<a href="../privacy.html">Privacy Policy</a>
            <a href="../terms.html">Terms of Use</a>
        </span><div class="clear"></div>
        </div>
    </div>  
</footer> 


<section class="pageLoad"></section> 
<div class="backtotop">
	<img src="resources/css/jobs/images/backtotop.png" alt="" />
</div>  

<!-- Footer Area -->
<%-- <jsp:include page="../masterFooter.jsp" /> --%>
<!-- Footer Area -->

<!-- this script is for load more...  -->
<script>
	function loadMoreReq(){
    	var avilableCount = $("#openJobsOffset").val();
    	avilableCount = parseInt(avilableCount) + parseInt(10); 
    	$("#openJobsOffset").val(avilableCount);
    	requirementsFilter("more");
	}
</script>



<script>
function clearcheck(){
	$('#remainingOrgs').find('input[type=checkbox]:checked').removeAttr('checked');
	$(".LettSelec a").removeClass("actL");
}

function clearFilters(){
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
		var i = "";
		result = "";
		result = result
				+ '<select class="searchHome searchHomeE" id="openJobsExperienceId">';
		result = result
				+ '("<option selected disabled>Experience(Years)</option>")';
		for (var i = 1; i <= 25; i++) {
			result = result + '("<option value='+i+'>' + i + '</option>")';
		}
		result = result + '</select>';
		$('#dropDownCountId').empty();
		$('#dropDownCountId').append(result);
	}
</script>

<script>
function requirementsFilter(type){
	var reqType = [];
	$('input:checkbox[name=typeFilter]:checked').each(function() 
    {
		reqType.push($(this).val());
    });
	
	var orgLocationIds = [];
	$('input:checkbox[name=orgName]:checked').each(function() 
    {
		orgLocationIds.push($(this).val());
    });
	
	var dates = "";
	$('input:radio[name=dateFilter]:checked').each(function() 
    {
		dates = ($(this).val());
    });
	
	var country = "";
	$('input:radio[name=countryFilter]:checked').each(function() 
    {
		country = ($(this).val());
    });
	
	
	var sourceleadUrl = '${sourceleadUrl}';
	var offset = 0;
	if(type == "more"){
		offset = $("#openJobsOffset").val();
	}
	
	var postedOrgName = "";
	if(type == "postedOrg"){
		postedOrgName = $("#postedOrg").val();
	}
	
	$("#loadinggif").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl+"/getOpenJobsELK?${_csrf.parameterName}=${_csrf.token}&offset="+offset+"&reqType="+reqType+"&orgLocationIds="+orgLocationIds+"&dates="+dates+"&country="+country+"&postedOrgName="+postedOrgName,
	    success: function(data) {
	    	var json = JSON.parse(data);
	    	if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){
	    		if(json.length > 10){
	    			$("#loadMoreReq").css("display", "block");
	    		}
	    		$("#noResultsDiv").css("display", "none");
	    		prepareOpenJobsElkList(data,sourceleadUrl,offset);
	    		$("#loadinggif").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    	}else{
	    		$("#loadinggif").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	$("#openJobsRequirementTitleId").val('');
				$("#openJobsLocationId").val('');
				$("#openJobsExperienceId").val('');
	        	$('#openJobsRequirementsList').empty();
	        	$("#openJobsRequirementsList").show();
	        	$("#openJobsRequirementsSummary").hide();
	        	$("#loadMoreReq").css("display", "none");
	        	$("#noResultsDiv").css("display", "block");
	        	/* var result ="";
	         	result = result + '<div>No Records Found...</div>';
	         	$('#loadMoreReq').append(result); */
	        }
	    }
	 }); 
}
</script>


<script>
function getOpenJobsELKList(type){
	var openJobsRequirementTitle = document.getElementById("openJobsRequirementTitleId").value;
	var openJobsLocation = document.getElementById("openJobsLocationId").value;
	var openJobsExperience = $("#openJobsExperienceId").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var offset = 0;
	if(type == "more"){
		offset = $("#openJobsOffset").val();
	}
	$("#loadinggif").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl+"/getOpenJobsELK?${_csrf.parameterName}=${_csrf.token}&offset="+offset+"&openJobsRequirementTitle="+openJobsRequirementTitle+"&openJobsLocation="+openJobsLocation+"&openJobsExperience="+openJobsExperience,
	    success: function(data) {
	    	var json = JSON.parse(data);
	    	if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){
	    		if(json.length > 10){
	    			$("#loadMoreReq").css("display", "block");
	    		}
	    		prepareOpenJobsElkList(data,sourceleadUrl,offset);
	    		$("#loadinggif").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    	}else{
	    		$("#loadinggif").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
function prepareOpenJobsElkList(data,sourceleadUrl,offset){
	$("#openJobsRequirementsList").show();
	$("#openJobsRequirementsSummary").hide();
    var json = JSON.parse(data);
  	 var experience ="";
  	 var location = "";
    if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 	
  	  if(json.length > 0)
  		{
  		var result = "";
			for(var i=0; i<json.length; i++){
				var requirements = json[i];
				var title="";
				var link="";
				var picture="";
				var description='';
				var comment='';
				var rate=''
				var rateType= '';
				var currencyType = '';
				
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
				}else if(requirements.maxExperience != null && requirements.maxExperience != '' && requirements.maxExperience != 'undefined'){
					experience = requirements.maxExperience+" Years";
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
				result = result +'	<h3 style="color:#368ee0;" onclick="getOpenJobsRequirementSummary(\''+requirements.requirementUUID+'\');">'+requirements.jobTitle+'</h3>';
				description =  description+'Job Title:'+requirements.jobTitle+";";
				
				link = sourceleadUrl+"/openJobsPublicPage?requirementUUID="+requirements.requirementUUID;
				
				if(requirements.postedOrg != null && requirements.postedOrg != '' && requirements.postedOrg !='undefined'){
				result = result +'<input type="hidden" id="postedOrg" value=""/>';
				result = result +'	<h4 onClick="getPostedOrgJobs(this);" class="orgLink" title="'+requirements.postedOrg+'">'+requirements.postedOrg+'</h4>';	
				result = result +'<div class="clearfix"></div>';
				description =  description+'Posted By :'+requirements.postedOrg+";";
				}else{
				result = result +'	<h4>--</h4>';
				}
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
				if(location != null && location != '' && location !='undefined'){
				result = result +'	<b><i class="fa fa-map-marker"></i>'+location+'</b>';
				description =  description+'Job Location: '+location+";";
				}else{
				result = result +'	<b><i class="fa fa-map-marker"></i>--</b>';	
				}
				
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
				result = result +'	<div class="refer" title="Refer your friend"><i class="fa fa-user" aria-hidden="true"></i></div>';
				result = result +'  <div class="addthis_inline_share_toolbox" data-url='+link+' data-title='+title+'></div>';
				result = result +'	<div class="clear"></div>';
				result = result +'	</div>';
				result = result +'	<div class="jobApply"><a title="Create Alert" href="javascript:void(0)" class="alertBtn" onClick="alertFunction();createAlert(\''+requirements.requirementUUID+'\');"><i class="fa fa-bell-o"></i></a>';
				result = result +'   <a href="'+sourceleadUrl+"/login?requirementUUID="+requirements.requirementUUID+'">Apply</a>';
				result = result +'	<a href="'+sourceleadUrl+'/login" style="background-color:#eb821c;">Refer</a></div>';
				result = result +'	</div>';
				result = result +'	</div>';
			}
			$("#openJobsRequirementsSummary").empty();
			if(offset > 0){
// 				$('#loadMoreDiv').empty(); 
				$("#loadMoreDiv").append(result); 
			}else{
				$("#openJobsRequirementsList").empty();
				$("#openJobsRequirementsList").append(result); 
			}
			$("#openJobsRequirementTitleId").val('');
			$("#openJobsLocationId").val('');
			$("#openJobsExperienceId").val('');
			
			}
		}
    socialInviterScripts();
}
</script>

<script>
function getPostedOrgJobs(id){
	$("#postedOrg").val(id.innerHTML);
	requirementsFilter("postedOrg");
}
</script>

<script>
function getOrgsList(alphabet,type){
	var sourceleadUrl = '${sourceleadUrl}';
	var country = "";
	$('input:radio[name=countryFilter]:checked').each(function() 
    {
		country = ($(this).val());
    });
	
	 $.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl+"/getOrgsListforOpenJobs?${_csrf.parameterName}=${_csrf.token}&alphabet="+alphabet+"&country="+country,
	    success: function(data) {
	    	var json = JSON.parse(data);
	    	var result = "";
	    	if(type != 'main'){
	    		var org = "";    	
				for(var i=0; i<json.length; i++){
					var opt = json[i];
					org = org +'	<li class="control-group">';
					org = org +'		<label class="control control--checkbox"><span>'+opt.orgName+'</span>';
					org = org +'		  <input type="checkbox" name="orgName" value="'+opt.id+'"/><div class="control__indicator"></div>';
					org = org +'	</label>';
					org = org +'	</li>';
				}
				$("#remainingOrgs").empty();
				$("#remainingOrgs").append(org);
	    	}else{
	    		for(var i=0; i<5; i++){
		    		var opt = json[i];
		    		if(opt != null && opt !='undefined'){ 	
		    			result = result +'<li class="control-group">';
				    	result = result +'<label class="control control--checkbox" title="'+opt.orgName+'"><span>'+opt.orgName+'</span>';
				    	result = result +'  <input type="checkbox" name="orgName" value="'+opt.id+'" onclick="requirementsFilter();"/><div class="control__indicator"></div>';
				    	result = result +'</label>';
				    	result = result +'</li>';
		    		}
		    	}
	    		if(json.length > 5){
	    			var orgCount = json.length - 5 ;
			    	result = result +'<a onclick="openPopup(); getOrgsList(\'none\',\'sub\');" class="popOpen">+'+orgCount+' More</a>';
	    		}
				var org = "";    	
				for(var i=0; i<json.length; i++){
					var opt1 = json[i];
					org = org +'	<li class="control-group">';
					org = org +'		<label class="control control--checkbox" title="'+opt1.orgName+'" ><span>'+opt1.orgName+'</span>';
					org = org +'		  <input type="checkbox" name="orgName" value="'+opt1.id+'"/><div class="control__indicator"></div>';
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

<script>
function getOpenJobsRequirementSummary(requirementsUUID){
	$("#loadMoreReq").css("display", "none");
	$("#openJobsRequirementsSummary").show();
	$("#openJobsRequirementsList").hide(); 
	$("#loadMoreDiv").hide(); 
	 var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
		 type: "POST",
		   	url: sourceleadUrl+"/openRequirementsSummary?${_csrf.parameterName}=${_csrf.token}&requirementsUUID="+requirementsUUID,
		    success: function(data) {
	    	if(data != null && data !='undefined' && data !=null &&  data != [] ){ 	
	    	  var json = JSON.parse(data);
		      var result = "";

		      var title="";
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
		      result = result +'	<a href="javascript:void(0)" onclick="backtoList();"><i class="fa fa-arrow-left text-muted paddingRight"></i>Back to List</a>';
		      result = result +'</div>';
		      
				result = result +'	<section class="innerContent">';
				result = result +'  <div class="jobDetails positionRelative">';
				result = result +'	<div class="jobResultsInn job_ResultsInn">';
		    	result = result +'	<h3>'+json.jobTitle+'</h3>';
		    	if(json.requirementOrgName != null && json.requirementOrgName != '' && json.requirementOrgName !='undefined'){
				result = result +'	<h4 onClick="getPostedOrgJobs(this);">'+json.requirementOrgName+'</h4>';
		    	}else{
		    		result = result +'	<h4>--</h4>';	
		    	}
				
		    	result = result +'	<div class="clearfix"></div>';
		    	result = result +'	<div class="skills clearfix"><strong>Skills :</strong>';
				if(json.skills != null && json.skills != '' && json.skills !='undefined'){
				result = result +'	<span>'+json.skills+'</span>';
				}else{
				result = result +'	<span>--</span>';	
				}
				result = result +'	</div>';					
				result = result +'	<span>';
				
				link = sourceleadUrl+"/openJobsPublicPage?requirementUUID="+json.requirementUUID;
				description =  description+'Job Title:'+json.jobTitle+";";
				
				if(json.jobType != null && json.jobType != '' && json.jobType !='undefined'){
				result = result +'	<b><i class="fa fa-clock-o"></i>'+json.jobType+'</b>';
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
				if(null != json.city && json.city!='undefined'){
				result = result +'	<b><i class="fa fa-map-marker"></i>'+json.country+','+json.state +','+json.city+'</b>';
				}else if(null != json.state && json.state!='undefined'){
				result = result +'	<b><i class="fa fa-map-marker"></i>'+json.country+','+json.state +'</b>';	
				}else{
				result = result +'	<b><i class="fa fa-map-marker"></i>'+json.country+'</b>';	
				}
				if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod !='undefined'){
				result = result +'	<b><i class="fa fa-bell"></i>'+json.noticePeriod+'</b>';
				}else{
				result = result +'	<b><i class="fa fa-bell"></i>--</b>';	
				}
				
				
				if(rate != null && rate != '' && rate !='undefined'){
				result = result +'	<b><i class="fa fa-rupee"></i>'+rate+'-'+rateType+'/'+currencyType+'</b>';
				}else{
				result = result +'	<b><i class="fa fa-rupee"></i>--</b>';	
				}
				
				result = result +'	</span>';
				result = result +'    </div>';			
				result = result +'  <div class="jobApply">';
				result = result +'	<a title="Create Alert" href="javascript:void(0)" class="alertBtn" onClick="alertFunction();createAlert(\''+json.requirementUUID+'\');"><i class="fa fa-bell-o"></i></a>';
				result = result +'  <a href="'+sourceleadUrl+"/login?requirementUUID="+json.requirementUUID+'">Apply</a>';
				result = result +'	<a href="'+sourceleadUrl+'/login" class="btn2" style="background-color:#eb821c;">Refer</a>';
				result = result +'	<div style="clear:both;"></div>';
				result = result +'	<div class="share hidden">';
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'linkedin\');"><i class="fa fa-linkedin-square"></i></a>';
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'twitter\');"><i class="fa fa-twitter-square"></i></a>';			  
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'facebook\');"><i class="fa fa-facebook-square"></i></a>';
				result = result +'	</div>';
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
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Work Location: </label></b>';
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
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Immigration Type:</label>--</b>';	
				}
				
				if(json.fileName != null && json.fileName != '' && json.fileName !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Attachment:'+json.fileName+'</label><a class="glyphicon glyphicon-download-alt ml10" href=\"'+sourceleadUrl+'/downloadRequirementAttachment?requirementId='+json.id+'\"></a><a class="fa fa-eye ml10" onclick="viewJobSummaryDocument(\''+json.id+'\',\''+json.fileName+'\');"></a></b>';
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Attachment:</label>--</b>';	
				}
				
				result = result +'	</div>';
				result = result +'	<div class="jobShare">';
				if(json.requirementCreatedDateToString != null && json.requirementCreatedDateToString != '' && json.requirementCreatedDateToString !='undefined'){
				result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i><label>Posted:'+json.requirementCreatedDateToString+'</label></div>';
				}else{
					result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i><label>Posted:</label>--</div>';
				}
				result = result +'	<div class="refer" title="Refer your friend"><i class="fa fa-user" aria-hidden="true"></i></div>';
				result = result +'	<div class="share">';
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'linkedin\');"><i class="fa fa-linkedin-square"></i></a>';
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'twitter\');"><i class="fa fa-twitter-square"></i></a>';			  
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'facebook\');"><i class="fa fa-facebook-square"></i></a>';
				result = result +'	</div>';
				result = result +'	<div style="clear:both;"></div>';
				result = result +'	</div>';  	
				result = result +'	</div>';
				result = result +'	<div style="clear:both;"></div>';		
				result = result +'	</section>'; 
				$("#openJobsRequirementsList").hide();
				$("#openJobsRequirementsSummary").empty();
				$("#openJobsRequirementsSummary").append(result);  
				
	   	   	   }
	   	   	}
		});  
}

function backtoList(){
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
	 var sourceleadUrl ='${sourceleadUrl}';
		if(jobId != null && jobId != ""){
			window.open(sourceleadUrl+'/viewJobSummaryDocumentDocView/'+title+'?jobId='+jobId,title,"height=600,width=600");
		}  
}

</script>
<script>
function openJobSearchRequirement(){
 			/* if ($('#openJobsAdvancedForm').validate(false, validationSettings)){   */
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#openJobsAdvancedForm').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#openJobsAdvancedForm')[0];
   		    var formData = new FormData(form);
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
						url : sourceleadUrl+"/openJobsRequiremetsAdvancedSearch?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(data) {
							 $("#advancedOpenJobsSearch").addClass('button').val('Search');
							 $("#advancedOpenJobsSearch").removeAttr("disabled");
							 adSearchClose();
							 $("#openJobsRequirementsList").empty();
						     var json = JSON.parse(data);
					       	 var experience ="";
					       	 var location = "";
						     if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 	
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
						 				result = result +'	<div class="jobResultsCta">';
										result = result +'	<div class="jobResultsInn">';
										result = result +'	<a href="<c:url value="/openRequirementsSummary?requirementUUID='+requirements.requirementUUID+'"/>"><h3>'+requirements.jobTitle+'</h3>';
										result = result +'	<h4>'+requirements.postedOrg+'</h4>';
										result = result +'	<div class="skills">';
										result = result +'	<strong>Skills :</strong>';
										result = result +'  <span>'+skills+'</span>';
										result = result +'	</div>';					
										result = result +'	<span>';
										result = result +'	<b><i class="fa fa-clock-o"></i>'+jobType+'</b>';
										result = result +'	<b><i class="fa fa-briefcase"></i>'+experience+'</b>';
										result = result +'	<b><i class="fa fa-map-marker"></i>'+location+'</b>';
										result = result +'	</span>';
										result = result +'	</div>';
										

										link = sourceleadUrl+"/openJobsPublicPage?requirementUUID="+requirements.requirementUUID;
										description =  description+'Job Title:'+requirements.jobTitle+";";
										
										result = result +'	<div class="jobShare">';
										result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i>'+requirements.createdDate+'</div>';
										result = result +'	<div class="refer" title="Refer your friend"><i class="fa fa-user" aria-hidden="true"></i></div>';
										result = result +'	<div class="share">';
										result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'linkedin\');"><i class="fa fa-linkedin-square"></i></a>';
										result = result +'  <a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'twitter\');"><i class="fa fa-twitter-square"></i></a>';				  
										result = result +'  <a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'facebook\');"><i class="fa fa-facebook-square"></i></a>';
										result = result +'  </div>';
										result = result +'	<div class="clear"></div>';
										result = result +'	</div>';
										
										result = result +'	<div class="jobApply"><a title="Create Alert" href="javascript:void(0)" class="alertBtn" onClick="alertFunction();createAlert(\''+requirements.requirementUUID+'\');"><i class="fa fa-bell-o"></i></a>';
										result = result +'   <a href="'+sourceleadUrl+"/login?requirementUUID="+requirements.requirementUUID+'">Apply</a>';
										result = result +'	<a href="'+sourceleadUrl+'/login" style="background-color:#eb821c;">Refer</a></div>';
													
										result = result +'	</div>';
										$("#openJobsRequirementsSummary").empty();
										$("#openJobsRequirementsList").append(result); 
								}
							}
						}
					}
				});
			}
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

<script>
function getStates(){
	var countryName = $("#country").val();
	var sourceleadUrl = '${sourceleadUrl}';
	if(countryName != null && countryName !='' && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	         success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---Select State---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').append('<option value="">---Select City---</option>');
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---Select State---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---Select City---</option>');
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
	        	$('#city').append('<option value="">---Select City---</option>');
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
    	$('#city').append('<option value="">---select city---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>


<script type="text/javascript">
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
		    chosen.search_field.on('keyup', function(e)
		    {
		    	var anyofKeyword = this.value.replace(",","");
		        if ((e.which == 13 || e.which == 188) && (anyofKeyword != null  && anyofKeyword != '' && anyofKeyword != 'undefined'))
		        {
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
		   
		    var selectone, chosenone;
		    selectone = $("#allReqKeyWord");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keyup', function(e1)
		    {
		    	var allofKeyWord = this.value.replace(",","");
		        if ((e1.which == 13 || e1.which == 188) && (allofKeyWord != null  && allofKeyWord != '' && allofKeyWord != 'undefined'))
		        {
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
		    chosentwo.search_field.on('keyup', function(e2)
		    {
		    	var excludeKeyWord = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (excludeKeyWord != null  && excludeKeyWord != '' && excludeKeyWord != 'undefined'))
		        {
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
		    
		    var selectthree, chosenthree;
		    selectthree = $("#organizations");
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenthree = selectthree.data('chosen');
		    chosenthree.search_field.on('keyup', function(e3)
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
  <div class="modal modal_createalert" id="myModal" role="dialog">
    <div id="createAlertPopUpDiv"></div>
  </div>
  <!-- Create Alert modal Popup Ends -->
<script>
//Create Alert Poppup
function alertFunction() {
	$(".pop_mask").fadeIn();
	$(".modal.modal_createalert").fadeIn();
}
function alertClose(){
	$(".pop_mask").fadeOut();
	$(".modal.modal_createalert").fadeOut();
}
</script>
<!-- <script src="resources/js/jobs/js/bootstrap-select.min.js"></script> -->
<script>
function createAlert(requirementUUID){
	 
	
	var sourceleadUrl ='${sourceleadUrl}';
	
	 $.ajax({
        type : "GET",
        url: sourceleadUrl+"/getJobDetailsForJobAlert?${_csrf.parameterName}=${_csrf.token}&requirementUUID="+requirementUUID,    
         success :function(data) {
        	 
        	 var json = JSON.parse(data);
        	 var experience = json['maxExp'];
        	  var state = '';
        	  var location ='';
        	  var country = '';
        	  var city = '';
        	  var zipCode ='';
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
        	 result = "";
        	 result = result +' <form id="create_alert_form" name="create_alert_form" method="post">';
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
        	 
        	 result = result +'              <input type="text" id="currentRate" name="rate" field-name="rate" data-validation="validate_Space validate_float validate_length length2-8" onblur="getRate();" data-validation-optional="true" value="'+json['maxRate']+'" class="form-control" placeholder="Rate">';
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
        	 result = result +'         	<div class="col-md-9">';
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
        	 result = result +'  					<option value="${skill.name}">${skill.name}</option>';
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
        	    	 var sourceleadUrl ='${sourceleadUrl}';
        	    	var allSkills = $(event.target).val();
        	    	$.ajax({
        		    	type: "POST",
        		        url: sourceleadUrl+"/profileBySkillSet?${_csrf.parameterName}=${_csrf.token}&allSkills="+allSkills, 
        		        success: function(data) {
        		        	var primarySkill = data;
        		        	      $("#primarySkill").val(primarySkill);
        		        	
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
         $('body').on('submit', '#create_alert_form', function(e){
        	  
        	 
        	  if ($(this).validate(false, validationSettings)){
        		
        		e.preventDefault();
        		var sourceleadUrl = '${sourceleadUrl}';
        		var frm = $('#create_alert_form').serializeFormJSON();
        		var con = JSON.stringify(frm);
        		con = con.replace(/[{}]/g, ""); 
        		//It will escape all the special characters
        	    var jsonData = encodeURIComponent(con);

        		$.ajax({
        					type : "POST",
        					url : sourceleadUrl+"/storeJobAlert?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
        					success : function(result) {
        							location.replace(sourceleadUrl+"/openRequirementsRedirect");
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

<script type="text/javascript">
function getAllCountriesJObalert(country){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#countryjobalert').empty();
        	$('#countryjobalert').append('<option value="">---Select country---</option>');
            for(var i=0;i<json.length;i++){
            	
            	if(country != 'undefined' && country != null && country != '' && json[i] == country){
            		$('#countryjobalert').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
            	}else{
            		$('#countryjobalert').append('<option value="'+json[i]+'">'+json[i]+'</option>');
            	}
            		
           	}
            
            $('#countryjobalert').trigger("chosen:updated");
            if(country == "undefined" || country == null){
            	 getStatesjobalert();
            }
           
        },
        error: function(e){
        }
    });
} 
</script>


<script>
function getStatesjobalert(country,state){
	
	var countryName = '';
	if(country != null && country != '' && country != 'undefined'){
		countryName = country;
	}else{
	   countryName = $("#countryjobalert").val();
	}
	
	
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#statejobalert').empty();
	        	$('#statejobalert').append('<option value="">---Select State---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(state != null && state != '' && state != 'undefined' && json[i] == state){
	            		$('#statejobalert').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}else{
	            		$('#statejobalert').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	}
	            	
	            	
	            }
	            $('#statejobalert').trigger("chosen:updated");
	            
	            $('#cityjobalert').empty();
	            $('#cityjobalert').append('<option value="">---Select City---</option>');
	            $('#cityjobalert').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#statejobalert').empty();
    	$('#statejobalert').append('<option value="">---Select State---</option>');
    	$('#statejobalert').trigger("chosen:updated");
    	$('#cityjobalert').empty();
    	$('#cityjobalert').append('<option value="">---Select City---</option>');
        $('#cityjobalert').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCitiesjobalert(country,state,city){
	
	var countryName = '';
	var stateName = '';
	
	if(country != null && country != '' && country != 'undefined'){
		countryName = country;
	}else{
	   countryName = $("#countryjobalert").val();
	}
	
	if(state != null && state != '' && state != 'undefined'){
		stateName = state;
	}else{
		stateName = $("#statejobalert").val();
	}
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#cityjobalert').empty();
	        	$('#cityjobalert').append('<option value="">---Select City---</option>');
	            for(var i=0;i<json.length;i++){
	            	
	            	if(city != null && city != '' && state != 'undefined' && json[i] == city){
	            		$('#cityjobalert').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}else{
	            		$('#cityjobalert').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	}
	            	
	            	
	            }
	            $('#cityjobalert').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#cityjobalert').empty();
    	$('#cityjobalert').append('<option value="">---Select City---</option>');
    	$('#cityjobalert').trigger("chosen:updated");
	}
}
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
		       url: sourceleadUrl+"/validateAlertUserEmail?${_csrf.parameterName}=${_csrf.token}&email="+email,
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
<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-596e031897dcfb4a"></script>
<script src="resources/js/formvalidator.js"></script>

<jsp:include page="../socialinviterscripts.jsp" />