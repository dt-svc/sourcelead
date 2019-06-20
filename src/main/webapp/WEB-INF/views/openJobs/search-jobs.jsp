<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link href="https://fonts.googleapis.com/css?family=Arima+Madurai:400,500,700" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/resources/source-css/reset.css?staticTime=${staticTime}">
<link href="/resources/css/jobs/css/source-style.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/jobs/css/source-responsive.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jobs/css/chosen.min.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/jobs/css/bootstrap-select.min.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/jobs/css/sourcelead-main.css?staticTime=${staticTime}">
<link href="/resources/css/jobs/css/main-style.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<link href="/resources/css/dataTablePagination.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<link href="/resources/css/jquery-ui.css?staticTime=${staticTime}" rel="stylesheet">
<link rel="stylesheet" href="/resources/css/jquery.timepicker.css?staticTime=${staticTime}">
<link rel="stylesheet" href="/resources/css/timesheet.css?staticTime=${staticTime}" type="text/css" >

<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-fun.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery.timepicker.js?staticTime=${staticTime}"></script>
<script src="/resources/js/themeswitcher.js?staticTime=${staticTime}"></script>
<script type="text/javascript" src="/resources/js/static-web/modernizr.custom.86080.js?staticTime=${staticTime}"></script>
<script src="/resources/js/static-web/jquery-1.12.4.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/static-web/jquery.sldr.js?staticTime=${staticTime}"></script>
<script src="/resources/js/static-web/masonry.pkgd.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/bootstrap.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/bootstrap-select.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/controls.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/jquery-1.11.3.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/chosen.jquery.min.js?staticTime=${staticTime}"></script>


<script type="text/javascript">
var sourceleadUrl = '${sourceleadUrl}';
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
// 		if('${requirementUUID}' != null && '${requirementUUID}' != '' && '${requirementUUID}' != 'undefined'){
// 			getOpenJobsRequirementSummary('${requirementUUID}');
// 			getDropDownCount();
// 		}else{
// 		getOpenJobsELKList();
		getDropDownCount();
// 	  }
	});
</script>

<!-- Content Area -->
<jsp:include page="../staticHeader.jsp" />	
<section class="asMask" onclick="adSearchClose();"></section>

<div class="background background-searchjob slide">
	<ul class="slider hidden">
	  <li class="para" style="background-image:url(/resources/static-web/images/jobSearch.jpg);"></li>
	  <li class="para" style="background-image:url(/resources/static-web/images/jobSearch.jpg);"></li>
	</ul> 
<content>
		<div style="margin-top:200px;">
		<p><i class="fa fa-search"></i>Search for vacancies</p>
		<section class="searchWrap">
			<span><i class="fa fa-search"></i><input type="text" placeholder="Search" id= "openJobsRequirementTitleId" value="${openJobsTitle}" class="searchHome"></span>
			<span><i class="fa fa-map-marker"></i><input type="text" placeholder="Location" value="${openJobsLocation}" id="openJobsLocationId" class="searchHome"></span>
			<span><i class="fa fa-briefcase"></i> <p id="dropDownCountId" value="${openJobsExperience}"></p></span>
			<span><input type="button" value="Search" class="searchBtn" onclick="redirectPage();"></span>
			<!-- <h3><b id="ADS">Advanced Search</b></h3> -->
			<div class="clear"></div>
		</section>
		</div>
	</content>		
	
</div>



<!-- <div class="clearfix searchjob-banner"> -->
<%-- <form class="searchprofile-search jobsearch"> --%>
<!--    <p class="text-center">Search Job with us...</p> -->
<!--    <div class="row clearfix search-tools"> -->
<!--     <div class="col-md-5 col-xs-12"> -->
<%--       <input type="text" class="form-control" id= "openJobsRequirementTitleId" value="${openJobsTitle}" placeholder="Search" name="pwd"> --%>
<!--     </div> -->
<!--         <div class="col-md-4 col-xs-12"> -->
<%--       <input type="text" class="form-control" id="openJobsLocationId" value="${openJobsLocation}" placeholder="Location" name="pwd"> --%>
<!--     </div> -->
<!--     <div class="col-md-2 col-xs-12 dropdown-select"> -->
<!--       <select class="form-control"> -->
<%--   <option selected="openJobsExperienceId" value="${openJobsExperience}">--Select Experience--</option> --%>
<!--   <option value="1">1</option> -->
<!--   <option value="2">2</option> -->
<!--   <option value="3">3</option> -->
<!--   <option value="4">4</option> -->
<!--   <option value="5">5</option> -->
<!--   <option value="6">6</option> -->
<!--   <option value="7">7</option> -->
<!--   <option value="8">8</option> -->
<!--   <option value="9">9</option> -->
<!--   <option value="10">10</option> -->
<!--   <option value="11">11</option> -->
<!--   <option value="12">12</option> -->
<!--   <option value="13">13</option> -->
<!--   <option value="14">14</option> -->
<!--   <option value="15">15</option> -->
<!--   <option value="16">16</option> -->
<!--   <option value="17">17</option> -->
<!--   <option value="18">18</option> -->
<!--   <option value="19">19</option> -->
<!--   <option value="20">20</option> -->
<!--   <option value="21">21</option> -->
<!--   <option value="22">22</option> -->
<!--   <option value="23">23</option> -->
<!--   <option value="24">24</option> -->
<!--   <option value="25">25</option> -->
<!--       </select> -->
<!--     </div> -->
<!--      <div class="col-md-1 col-xs-12"> -->
<!--     <input type="button" value="Search" class="searchBtn" onclick="redirectPage();"><i class="fa fa-search" aria-hidden="true"></i> -->
<!-- 		</a> -->
<!-- 			</div> -->
<!-- 		</div> -->
		
<%-- </form> --%>
<!--   </div> -->







<div class="container-fluid container-area-white">
	<div class="clearfix">
			<div class="container">
			<h2 class="text-center mb">Best Places to Work</h2>
			<div class="contnetarea">
				<p class="text-center" style="text-align: center !important;">
					If you're looking for a job change or a career change, you can find it on Sourcelead.<br>You can take a look at our Popular Job Locations and Job Titles. Find your perfect career fit for today.<br>Whatever type of job you're looking for, you can find it on SourceLead.
				</p>
			</div>
			<div class="row hidden">
				<div class="col-md-3 text-center candidate-job">
				<i class="material-icons">desktop_windows</i>
									<div class="clearfix"></div>
					<h4>Information Technology</h4>
									<p>2000 Jobs</p>

				</div>
					<div class="col-md-3 text-center candidate-job2">
					<i class="material-icons">donut_large</i>
					<div class="clearfix"></div>
						<h4>Manufacturing</h4>
						<p>2000 Jobs</p>
					</div>
					<div class="col-md-3 text-center candidate-job">
										<i class="material-icons">access_alarm</i>
					<div class="clearfix"></div>
						<h4>Services</h4>
						<p>2000 Jobs</p>
					</div>
					<div class="col-md-3 text-center candidate-job2">
										<i class="material-icons">language</i>
					<div class="clearfix"></div>
						<h4>All Jobs</h4>
						<p>2000 Jobs</p>
					</div>
				</div>
			</div>
	</div>
<div class="clearfix"></div>
</div>

<section class="homewidget-benefitsdiv text-center">
  <div class="container-fluid bounceIn animated">
 		<div class="col-md-12">
 			<h2>Benefits of SourceLead</h2>
 			<p>Integration which provides a one-stop solution to entire recruitment life cycle with a single login to Clients, Vendors and Candidates.</p>
 		</div>
  		<div class="clearfix">
  			<div class="col-xs-12 col-sm-12 col-md-3 col-md-offset-1 col-lg-2 col-lg-offset-3">
  			<a href="/register" class="btn btn-custom mr" target="_blank">Register with Us</a>
  			</div>
  			<div class="col-xs-12 col-sm-12 col-md-3 col-lg-2 text-center">
  				<span class="mt">--- OR ---</span>
  				</div>
  				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-2 text-center">
  			<a href="/register" class="btn btn-custom mr" target="_blank">Upload your CV</a>
  			</div>
  		</div>
  </div>
</section>
<!-- Clients Div -->
<section class="container-fluid container-area-white">
  <!-- Clients Div -->
   <section class="clearfix clientsdiv-inner">
    <div class="container">
       <div class="clearfix text-center animated bounceIn clientsdiv-hdr">
	    <h2 class="text-center animated bounceIn">Our Clients</h2>
	  </div>
	  <section id="owl-carousel-div" class="mt">
      <div class="row">
        <div class="large-12 columns">
          <div class="owl-carousel owl-theme owlcarousel-custom">
            <div class="itemowl">
              <img src="/resources/static-web/images/businessintelli_revised.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/soft.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/cloudious.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/sarian_logo.png" alt="Client Image" class="img-responsive">
            </div>
             <div class="itemowl">
              <img src="/resources/static-web/images/businessintelli_revised.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/soft.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/cloudious.png" alt="Client Image" class="img-responsive">
            </div>
            <div class="itemowl">
              <img src="/resources/static-web/images/sarian_logo.png" alt="Client Image" class="img-responsive">
            </div>
          </div>
          </div>
          </div>
    </section>
	    
	        
    
  </div>
</section>
<!-- Clients Div -->
</section>
<!-- Clients Div -->
<!-- Benefits of sourcelead  -->

<jsp:include page="../staticFooter.jsp" />

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
				+ '("<option selected value="">--Select Experience--</option>")';
		for (var i = 1; i <= 25; i++) {
			result = result + '("<option value='+i+'>' + i + '</option>")';
		}
		result = result + '</select>';
		$('#dropDownCountId').empty();
		$('#dropDownCountId').append(result);
	}
</script>



<!-- <script>
function getOpenJobsELKList(){
	var openJobsRequirementTitle = document.getElementById("openJobsRequirementTitleId").value;
	var openJobsLocation = document.getElementById("openJobsLocationId").value;
	var openJobsExperience = $("#openJobsExperienceId").val();
	
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
</script> -->




  <script>
function getOpenJobsRequirementSummary(requirementsUUID){
	 
	 var locationCode ='${SourceLeadWeb_locationCode}'; 
	 $.ajax({
		 type: "POST",
		   	url: sourceleadUrl+"/ws/openRequirementsSummary?${_csrf.parameterName}=${_csrf.token}&requirementsUUID="+requirementsUUID+"&locationCode="+locationCode,
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
				result = result +'	<a href="/login">Apply</a>';
				if (json.isCrowdSourcing == 'Y'){
				result = result +'	<a href="/login" class="btn2" style="background-color:#eb821c;">Refer</a>';
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


<!-- <script>
function openJobSearchRequirement(){
 			/* if ($('#openJobsAdvancedForm').validate(false, validationSettings)){   */
			
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
						url : sourceleadUrl+"/ws/openJobsRequiremetsAdvancedSearch?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
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
										result = result +'	<a href="<c:url value="/app/ws/openRequirementsSummary?requirementUUID='+requirements.requirementUUID+'"/>"><h3>'+requirements.jobTitle+'</h3>';
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
</script> -->



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

<!-- <script>
function getStates(){
	var countryName = $("#country").val();
	
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
</script>  -->


<!-- <script>
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
</script> -->


<script type="text/javascript">
    	   var config = {
    				  '.chosen-select'           : {},
    				  '.chosen-select-deselect'  : {allow_single_deselect:true},
    				  '.chosen-select-no-single' : {disable_search_threshold:10},
    				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    				  '.chosen-select-width'     : {width:"95%"}
    	   }
			/* //Adding Customized Any Of Keyword to the list 
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
		    }); */
		   
		    /* var selectone, chosenone;
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
				}) */
</script> 



<!--  Forgot password -->
<!-- <script>
	$("#forpass").hide();
	$("#pass").click(function() {
		$("#forpass").show();
		$("#signin").hide();
	});
	$(".canbtn").click(function() {
		$("#forpass").hide();
		$("#signin").show();
	});
</script> -->


<!--   Responsive Menu   -->
<!-- <script>
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
</script> -->

<!--  Blog Responsive  -->
<script>
$(".blogWrapRight h2").click(function(){
	$(".resSlide").slideToggle(200);
});
</script>    

<!--   FreeTrial Pop   -->
<!-- <script>
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
</script> -->



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


<!-- <script>
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
</script> -->


<script type="text/javascript">
function redirectPage(){
	var url = "";
	var title = document.getElementById("openJobsRequirementTitleId").value;
	if(title != null && title != '' && title != 'undefined'){
		url += "/"+title;
	}else{
		url += "/all";
	}
	var location = document.getElementById("openJobsLocationId").value;
	if(location != null && location != '' && location != 'undefined'){
		url += "/"+location;
	}else{
		url += "/all";
	}
	var experience = document.getElementById("openJobsExperienceId").value;
	if(experience != null && experience != '' && experience != 'undefined'){
		url += "/"+experience;
	}
	
	if((title == null || title == '' || title == 'undefined') && (location == null || location == '' || location == 'undefined') && (experience == null || experience == '' || experience == 'undefined')){
		window.location.href = "jobs";
	}else if((title != null && title != '' && title != 'undefined') && (location == null || location == '' || location == 'undefined') && (experience == null || experience == '' || experience == 'undefined')){
		window.location.href = "jobs/"+title;
	}else{
		window.location.href = "jobs"+url;
	}
	
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
<script type="text/javascript">
window.onload = function() {
	$("#searchjobID").addClass("slactive");
}
</script>