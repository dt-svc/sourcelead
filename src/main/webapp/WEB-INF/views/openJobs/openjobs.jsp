<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet">
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


<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/masterHeaderAction.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/formvalidator.js"></script>
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
	if(${requirementUUID != null}){
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
}
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
			<span><i class="fa fa-search"></i><input type="text" placeholder="Search" id= "openJobsRequirementTitleId" class="searchHome"></span>
			<span><i class="fa fa-map-marker"></i><input type="text" placeholder="Location" id="openJobsLocationId" class="searchHome"></span>
			<span><i class="fa fa-briefcase"></i> <p id = "dropDownCountId"></p></span>
			
			<span><input type="button" value="Search" class="searchBtn" onclick="getOpenJobsELKList();"></span>
			<h3><b id="ADS">Advanced Search</b></h3>
			<div class="clear"></div>
		</section>
	</div>
	
<section class="innerContent">
		<div class="filter">
			<h2>Fliter your search</h2>
			<div class="filterCta" id="dateFilterId">
				<h3>Fliter Header<i class="fa fa-caret-down arrAc"></i></h3>
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
						<label class="control-radio control--checkbox-ra">3 day ago
						  <input type="radio" name="dateFilter" value="3 Days Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">1 week ago
						  <input type="radio" name="dateFilter" value="1 Week Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">2 Weeks Ago 
						  <input type="radio" name="dateFilter" value="2 Weeks Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">1 Month Ago
						  <input type="radio" name="dateFilter" value="1 Month Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>
					<li class="control-group">
						<label class="control-radio control--checkbox-ra">3 Months Ago
						  <input type="radio" name="dateFilter" value="3 Months Ago" onclick="requirementsFilter();"/><div class="control__indicator_ra"></div>
						</label>
					</li>		
				</ul>
			</div>
			
			
			<div class="filterCta" id="dateFilterId">
				<h3>Country <i class="fa fa-caret-down arrAc"></i></h3>
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

			<div class="filterCta">
				<h3>Employment Type Fliter<i class="fa fa-caret-down"></i></h3>
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
			
			<div id= "openJobsRequirementsList"></div>
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
			
				<div class="LettSelec">
					<a onclick="getOrgsList('none','sub');">ALL</a>
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
                  <label>Requirement Title</label>
                  <div class="clearfix">
                    <input type="text" id="requirementTitle" name="requirementTitle" class="form-control input-sm" placeholder="Requirement Title">
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
                      <option>4 Months</option>
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


<!-- Footer Area -->
<%-- <jsp:include page="../masterFooter.jsp" /> --%>
<!-- Footer Area -->

<script>
function clearcheck(){
	$('#remainingOrgs').find('input[type=checkbox]:checked').removeAttr('checked');
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
function requirementsFilter(){
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
	 $.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl+"/getOpenJobsELK?${_csrf.parameterName}=${_csrf.token}&offset="+offset+"&reqType="+reqType+"&orgLocationIds="+orgLocationIds+"&dates="+dates+"&country="+country,
	    success: function(data) {
	    	prepareOpenJobsElkList(data,sourceleadUrl);
	    }
	 }); 
}
</script>


<script>
function getOpenJobsELKList(){
	var openJobsRequirementTitle = document.getElementById("openJobsRequirementTitleId").value;
	var openJobsLocation = document.getElementById("openJobsLocationId").value;
	var openJobsExperience = $("#openJobsExperienceId").val();
	var sourceleadUrl = '${sourceleadUrl}';
	var offset = 0;
	 $.ajax({ 
	   	type: "POST",
	   	url: sourceleadUrl+"/getOpenJobsELK?${_csrf.parameterName}=${_csrf.token}&offset="+offset+"&openJobsRequirementTitle="+openJobsRequirementTitle+"&openJobsLocation="+openJobsLocation+"&openJobsExperience="+openJobsExperience,
	    success: function(data) {
	    	prepareOpenJobsElkList(data,sourceleadUrl);
	    }
	 }); 
 }
</script>

<script>
function prepareOpenJobsElkList(data,sourceleadUrl){
	$("#openJobsRequirementsList").empty();
    var json = JSON.parse(data);
  	 var experience ="";
  	 var location = "";
    if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 	
  	  if(json.length > 0)
  		{
			for(var i=0; i<json.length; i++){
				var requirements = json[i];
				var title="";
				var link="";
				var picture="";
				var description='';
				var comment='';
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
				var result = "";
				result = result +'	<div class="jobResultsCta">';
				result = result +'	<div class="jobResultsInn">';
				result = result +'	<h3 style="font-size:20px;color:#368ee0;" onclick="getOpenJobsRequirementSummary(\''+requirements.requirementUUID+'\');">'+requirements.jobTitle+'</h3>';
				description =  description+'Job Title:'+requirements.jobTitle+";";
				
				link = sourceleadUrl+"/openJobsPublicPage?requirementUUID="+requirements.requirementUUID;
				
				if(requirements.postedOrg != null && requirements.postedOrg != '' && requirements.postedOrg !='undefined'){
				result = result +'	<h4>'+requirements.postedOrg+'</h4>';	
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
				if(requirements.noticePeriod != null && requirements.noticePeriod != '' && requirements.noticePeriod !='undefined'){
				result = result +'	<b><i class="fa fa-bell"></i>'+requirements.noticePeriod+'</b>';
				description =  description+'Notice Period :'+requirements.noticePeriod+";";
				}else{
				result = result +'	<b><i class="fa fa-bell"></i>--</b>';	
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
				result = result +'	<div class="share">';
				
				/* result = result +'	<div class="socialinviter holder break" type="socialpost"></div>'; */ 
			    result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'linkedin\');"><i class="fa fa-linkedin-square"></i></a>';
				result = result +'  <a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'twitter\');"><i class="fa fa-twitter-square"></i></a>';				  
				result = result +'  <a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'facebook\');"><i class="fa fa-facebook-square"></i></a>';
				result = result +'  </div>'
				result = result +'	<div class="clear"></div>';
				result = result +'	</div>';
				result = result +'	<div class="jobApply"><a href="'+sourceleadUrl+'/login">Apply</a>';
				if(requirements.isCrowdSourcingJob == 'Y'){
				result = result +'	<a href="'+sourceleadUrl+'/login" style="background-color:#eb821c;">Refer</a></div>';
				}
/* 					result = result +'	<div class="filLoad">';
				result = result +'	<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>';
				result = result +'	<p class="gradi"></p>';
				result = result +'	</div>'; */				
				result = result +'	</div>';
				if((json.length -1) == i ){
					result = result +' <section class="loadJob"><i class="fa fa-spinner fa-spin"></i>No More Data</section>';	
				}
				$("#openJobsRequirementsSummary").empty();
				$("#openJobsRequirementsList").append(result); 
				$("#openJobsRequirementTitleId").val('');
				$("#openJobsLocationId").val('');
				$("#openJobsExperienceId").val('');
			}
			}
		}else{
		   	$("#openJobsRequirementTitleId").val('');
			$("#openJobsLocationId").val('');
			$("#openJobsExperienceId").val('');
		}
    socialInviterScripts();
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
		    		result = result +'<li class="control-group">';
			    	result = result +'<label class="control control--checkbox" ><span>'+opt.orgName+'</span>';
			    	result = result +'  <input type="checkbox" name="orgName" value="'+opt.id+'" onclick="requirementsFilter();"/><div class="control__indicator"></div>';
			    	result = result +'</label>';
			    	result = result +'</li>';
		    	}
	    		if(json.length > 5){
	    			var orgCount = json.length - 5 ;
			    	result = result +'<a onclick="openPopup();" class="popOpen">+'+orgCount+' More</a>';
	    		}
				var org = "";    	
				for(var i=0; i<json.length; i++){
					var opt1 = json[i];
					org = org +'	<li class="control-group">';
					org = org +'		<label class="control control--checkbox" ><span>'+opt1.orgName+'</span>';
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
};
</script>

<script>
function getOpenJobsRequirementSummary(requirementsUUID){
	 var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
		 type: "POST",
		   	url: sourceleadUrl+"/openRequirementsSummary?${_csrf.parameterName}=${_csrf.token}&requirementsUUID="+requirementsUUID,
		    success: function(data) {
	    	if(data != null && data !='undefined' && data !=null &&  data != [] ){ 	
	    	  var json = JSON.parse(data);
		      var result = "";
				result = result +'	<section class="innerContent">';
				result = result +'  <div class="jobDetails">';
				result = result +'	<div class="jobResultsInn">';
		    	result = result +'	<h3>'+json.jobTitle+'</h3>';
		    	if(json.requirementOrgName != null && json.requirementOrgName != '' && json.requirementOrgName !='undefined'){
				result = result +'	<h4>'+json.requirementOrgName+'</h4>';
		    	}else{
		    		result = result +'	<h4>--</h4>';	
		    	}
				result = result +'	<div class="skills"><strong>Skills :</strong>';
				if(json.skills != null && json.skills != '' && json.skills !='undefined'){
				result = result +'	<span>'+json.skills+'</span>';
				}else{
				result = result +'	<span>--</span>';	
				}
				result = result +'	</div>';					
				result = result +'	<span>';
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
				result = result +'	</span>';
				result = result +'    </div>';			
				result = result +'    <div class="jobDetailsLogo">';
				result = result +'	<a href="'+sourceleadUrl+'/login">Apply</a>';
				if (json.isCrowdSourcing == 'Y'){
				result = result +'	<a href="'+sourceleadUrl+'/login" class="btn2" style="background-color:#eb821c;">Refer</a>';
				}
				result = result +'	<div style="clear:both;"></div>';
				result = result +'	<div class="share mt10">';
				result = result +'	<a href="javascript:void(0)">Share</a>';
				result = result +'	<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>';
				result = result +'	<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>';			  
				result = result +'	<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>';
				result = result +'	<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>';				  	
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
				result = result +'	<b><i class="fa fa-caret-right"></i>Work Location : </b>';
				if(json.positions != null && json.positions != '' && json.positions !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>No. of positions :</label>'+json.positions+'</b>';
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>No. of positions :</label>--</b>';	
				}
				if(json.education != null && json.education != '' && json.education !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Education :</label>'+json.education+'</b>';
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Education :</label>--</b>';
				}
				if(json.requirementCreatedDateToString != null && json.requirementCreatedDateToString != '' && json.requirementCreatedDateToString !='undefined'){
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Posted :'+json.requirementCreatedDateToString+'</label></b>';
				}else{
				result = result +'	<b><i class="fa fa-caret-right"></i><label>Posted :</label>--</b>';	
				}
				result = result +'	</div>';
				result = result +'	<div class="jobShare">';
				if(json.requirementCreatedDateToString != null && json.requirementCreatedDateToString != '' && json.requirementCreatedDateToString !='undefined'){
				result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i><label>Posted :'+json.requirementCreatedDateToString+'</label></div>';
				}else{
					result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i><label>Posted :</label>--</div>';
				}
				result = result +'	<div class="refer" title="Refer your friend"><i class="fa fa-user" aria-hidden="true"></i></div>';
				result = result +'	<div class="share">';
				result = result +'	<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>';
				result = result +'	<a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>';				  
				result = result +'	<a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>';
				result = result +'	<a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>';					  	
				result = result +'	</div>';
				result = result +'	<div style="clear:both;"></div>';
				result = result +'	</div>';  	
				result = result +'	</div>';
				result = result +'	<div style="clear:both;"></div>';		
				result = result +'	</section>'; 
				$("#openJobsRequirementsList").empty();
				$("#openJobsRequirementsSummary").empty();
				$("#openJobsRequirementsSummary").append(result);  
				
	   	   	   }
	   	   	}
		});  
}
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
						 				var result = "";
						 				result = result +'	<div class="jobResultsCta">';
										result = result +'	<div class="jobResultsInn">';
										result = result +'	<a href="<c:url value="/openRequirementsSummary?requirementUUID='+requirements.requirementUUID+'"/>"><h3>'+requirements.jobTitle+'</h3>';
										result = result +'	<h4>'+requirements.postedOrg+'</h4>';
										result = result +'	<div class="skills">';
										result = result +'	<strong>Skills :</strong>';
										result = result +'  <span>'+requirements.skills+'</span>';
										result = result +'	</div>';					
										result = result +'	<span>';
										result = result +'	<b><i class="fa fa-clock-o"></i>'+requirements.jobType+'</b>';
										result = result +'	<b><i class="fa fa-briefcase"></i>'+experience+'</b>';
										result = result +'	<b><i class="fa fa-map-marker"></i>'+location+'</b>';
										result = result +'	</span>';
										result = result +'	</div>';
										result = result +'	<div class="jobShare">';
										result = result +'	<div class="posted"><i class="fa fa-thumb-tack"></i>'+requirements.createdDate+'</div>';
										result = result +'	<div class="refer" title="Refer your friend"><i class="fa fa-user" aria-hidden="true"></i></div>';
										result = result +'	<div class="share">';
										result = result +'	<a href="javascript:void(0)"><i class="fa fa-linkedin-square"></i></a>';
										result = result +'  <a href="javascript:void(0)"><i class="fa fa-twitter-square"></i></a>';				  
										result = result +'  <a href="javascript:void(0)"><i class="fa fa-facebook-square"></i></a>';
										result = result +'  <a href="javascript:void(0)"><i class="fa fa-envelope-square"></i></a>';					  	
										result = result +'  </div>';
										result = result +'	<div class="clear"></div>';
										result = result +'	</div>';
										result = result +'	<div class="jobApply"><a href="'+sourceleadUrl+'/login">Apply</a>';
										if(requirements.isCrowdSourcingJob == 'Y'){
										result = result +'	<a href="'+sourceleadUrl+'/login" style="background-color:#eb821c;">Refer</a></div>';
										}
										/*result = result +'	<div class="filLoad">';
										result = result +'	<h2 class="gradi"></h2><p class="gradi"></p><p class="gradi"></p><p class="gradi"></p><br>';
										result = result +'	<p class="gradi"></p>';
										result = result +'	</div>'; */				
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
<jsp:include page="../socialinviterscripts.jsp" />