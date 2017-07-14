<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="https://fonts.googleapis.com/css?family=Arima+Madurai:400,500,700" rel="stylesheet">
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
<script src="resources/js/jquery-1.12.4.min.js"></script>
<script src="resources/js/jquery.sldr.js"></script>
<script src="resources/js/masonry.pkgd.min.js"></script>
<script src="resources/js/jobs/js/bootstrap.js"></script>
<script src="resources/js/jobs/js/bootstrap-select.min.js"></script>
<script src="resources/js/jobs/js/controls.js"></script>
<script src="resources/js/jobs/js/jquery-1.11.3.min.js"></script>
<script src="resources/js/jobs/js/chosen.jquery.min.js"></script>
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
     if (comment) {
         postdatavalues.comment = comment;
     }
}
function getPostValueDetails(){
	 return postdatavalues;
  }

</script>

<script type="text/javascript">
	$(window).load(function() {
		if(${requirementUUID != null} ){
			getOpenJobsRequirementSummary('${requirementUUID}');
			getDropDownCount();
		}else{
		getOpenJobsELKList();
		getDropDownCount();
	  }
	});
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

	
<section class="asMask" onclick="adSearchClose();"></section>

<div class="background slide">
	<ul class="slider">
	  <li class="para" style="background-image:url(/sourcelead/resources/css/jobs/images/bg1.jpg);"></li>
	  <li class="para" style="background-image:url(/sourcelead/resources/css/jobs/images/bg3.jpg);"></li>
	</ul> 
<content>
		<h1>
			Welcome to<span>SourceLead job portal</span>
		</h1>
		<p><i class="fa fa-search"></i>Search for vacancies</p>
		<section class="searchWrap">
			<span><i class="fa fa-search"></i><input type="text" placeholder="Search" id= "openJobsRequirementTitleId" value="${openJobsTitle}" class="searchHome"></span>
			<span><i class="fa fa-map-marker"></i><input type="text" placeholder="Location" value="${openJobsLocation}" id="openJobsLocationId" class="searchHome"></span>
			<span><i class="fa fa-briefcase"></i> <p id = "dropDownCountId" value="${openJobsExperience}"></p></span>
			<span><input type="button" value="Search" class="searchBtn" onclick="redirectPage();"></span>
			<!-- <h3><b id="ADS">Advanced Search</b></h3> -->
			<div class="clear"></div>
		</section>
	</content>		
	
</div>
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
              <input type="button" value="Search" id="advancedOpenJobsSearch" onclick="openJobSearchRequirement();loadOpenJobsChosenRequirementScripts();redirectPage();" class="btn btn-sm btn-primary"/>
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


<script> 
$(function(){
	$('.slider li').hide().filter(':first').show();
	setInterval(slideshow, 8000);
});

function slideshow() {
	$('.slider li:first').fadeOut(500).next().fadeIn(500).end().appendTo('.slider');
}
	
function simpleParallax() {
    var scrolled = $(window).scrollTop() + 2;
    $('.para').css('background-position', '0' + -(scrolled * 0.3) + 'px');
}
$(window).scroll(function (e) {
    simpleParallax();
});	
	
</script>

<!-- Footer Area -->
<%-- <jsp:include page="../masterFooter.jsp" /> --%>
<!-- Footer Area -->

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
	 				var title="";
	 				var link="";
	 				var picture="";
	 				var description='';
	 				var comment='';
	 				result = result +'	<div class="jobResultsCta">';
					result = result +'	<div class="jobResultsInn">';
					result = result +'	<a href="#" onclick="getOpenJobsRequirementSummary(\''+requirements.requirementUUID+'\');"><h3 style="font-size:20px;color:#368ee0;">'+requirements.jobTitle+'</h3></a>';
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
					
					link = sourceleadUrl+"/openJobsPublicPage?requirementUUID="+requirements.requirementUUID;
					description =  description+'Job Title:'+requirements.jobTitle+";";
					
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
	 }); 
 }
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
		      var title="";
		      var link="";
		      var picture="";
		      var description='';
		      var comment='';
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
				
				link = sourceleadUrl+"/openJobsPublicPage?requirementUUID="+json.requirementUUID;
				description =  description+'Job Title:'+json.jobTitle+";";
				
				result = result +'    <div class="jobDetailsLogo">';
				result = result +'	<a href="'+sourceleadUrl+'/login">Apply</a>';
				if (json.isCrowdSourcing == 'Y'){
				result = result +'	<a href="'+sourceleadUrl+'/login" class="btn2" style="background-color:#eb821c;">Refer</a>';
				}
				result = result +'	<div style="clear:both;"></div>';
				result = result +'	<div class="share mt10">';
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
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'linkedin\');"><i class="fa fa-linkedin-square"></i></a>';
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'twitter\');"><i class="fa fa-twitter-square"></i></a>';			  
				result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'facebook\');"><i class="fa fa-facebook-square"></i></a>';
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
										result = result +'  <span>'+requirements.skills+'</span>';
										result = result +'	</div>';					
										result = result +'	<span>';
										result = result +'	<b><i class="fa fa-clock-o"></i>'+requirements.jobType+'</b>';
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
										result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'twitter\');"><i class="fa fa-twitter-square"></i></a>';			  
										result = result +'	<a href="javascript:setPostValues(\''+title+'\',\''+link+'\',\''+picture+'\',\''+description+'\',\''+comment+'\');socialinviter.socialpost.auth(\'facebook\');"><i class="fa fa-facebook-square"></i></a>';
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


<script type="text/javascript">
function redirectPage(){
	var sourceleadUrl = '${sourceleadUrl}';
	var openJobsTitle = document.getElementById("openJobsRequirementTitleId").value;
	var openJobsLocation = document.getElementById("openJobsLocationId").value;
	var openJobsExperience = document.getElementById("dropDownCountId").value;
	window.location.href = sourceleadUrl+"/openRequirementsRedirect?${_csrf.parameterName}=${_csrf.token}&openJobsTitle="+openJobsTitle+"&openJobsLocation="+openJobsLocation+"&openJobsExperience="+openJobsExperience;
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
/* 				$(".asMask, .close-div").click(function(){
					$(".filter").removeClass("filAc");
					$(".asMask").fadeOut(200);
					$(".adSearch").fadeOut(100);
				});	 */	  
</script>
<jsp:include page="../socialinviterscripts.jsp" />