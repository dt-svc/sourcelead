  <!--   HTML   -->	  
<jsp:include page="../staticHeader.jsp" /> 



<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="/resources/css/reset.css?staticTime=${staticTime}">
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

 <script src="https://maps.google.com/maps/api/js?libraries=places&v=3&key=AIzaSyDzIM1uth14u-kSIN_jZ75pRelInyGnYWU" type="text/javascript"></script>
<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/masterHeaderAction.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-fun.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery.dataTables.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery.timepicker.js?staticTime=${staticTime}"></script>
<script src="/resources/js/themeswitcher.js?staticTime=${staticTime}"></script>
<script type="text/javascript" src="/resources/js/modernizr.custom.86080.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/controls.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery-1.12.4.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jquery.sldr.js?staticTime=${staticTime}"></script>
<script src="/resources/js/masonry.pkgd.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/bootstrap.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/bootstrap-select.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/jquery-1.11.3.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/jobs/js/chosen.jquery.min.js?staticTime=${staticTime}"></script>

<div class="background slide">
	<ul class="slider">
	  <li class="para" style="background-image:url(resources/css/static-web/images/bg1.jpg)"></li>
	  <li class="para" style="background-image:url(resources/css/static-web/images/bg2.jpg)"></li>
	  <li class="para" style="background-image:url(resources/css/static-web/images/bg3.jpg)"></li>
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
			<span><input type="button" value="Search" class="searchBtn" onclick="redirectPageStatic();"></span>
			<!-- <h3><b id="ADS">Advanced Search</b></h3> -->
			<div class="clear"></div>
		</section>
	</content>		
	
</div>

<script type="text/javascript">
function redirectPageStatic(){
	var title = document.getElementById("openJobsRequirementTitleId").value;
	var location = document.getElementById("openJobsLocationId").value;
	var experience = document.getElementById("dropDownCountId").value;
	window.location.href = sourceleadUrl+"app/openRequirementsRedirect?&title="+title+"&location="+location+"&experience="+experience;
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
<script type="text/javascript">
	$(window).load(function() {
		if(requirementUUID != null){
			getOpenJobsRequirementSummary('${requirementUUID}');
			getDropDownCount();
		}else{
		getOpenJobsELKList();
		getDropDownCount();
	  }
	});
</script>
<script>
function getOpenJobsELKList(){
	var openJobsRequirementTitle = document.getElementById("openJobsRequirementTitleId").value;
	var openJobsLocation = document.getElementById("openJobsLocationId").value;
	var openJobsExperience = $("#openJobsExperienceId").val();
	
	var offset = 0;
	 $.ajax({ 
	   	type: "POST",
	   	url:"/getOpenJobsELK?${_csrf.parameterName}=${_csrf.token}&offset="+offset+"&openJobsRequirementTitle="+openJobsRequirementTitle+"&openJobsLocation="+openJobsLocation+"&openJobsExperience="+openJobsExperience,
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
<jsp:include page="../staticFooter.jsp" /> 