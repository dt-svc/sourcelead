<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- Content Area Starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
    	<div class="pull-left">
	      <c:if test="${empty requirementId}"><h2>Add Job</h2></c:if>
	      <c:if test="${!empty requirementId}"><h2>Update Job</h2></c:if>
	    </div>
    	<c:if test="${!empty msg}">
			<div class="success_pop" id="selectorReleaseMsg" style="display: block;">
				<i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
		</c:if>
      
      <div class="success_pop" id="selector" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
     <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a>&raquo;<a href="<c:url value='${appPath}/jobs'/>"> Jobs List  </a>&raquo; <c:if test="${empty requirementId}">Add Job</c:if> <c:if test="${!empty requirementId}">Update Job</c:if> </div>
    </section>
    
    <div class="newPrDetails newPrDetailsPMO"> 
    <div class="newPrTabs">
    <ul class="newPrTabsMenu">
				<li><a href="<c:url value='${appPath}/requirement?requirementUUID=${requirementUUID}'/>">Job Details</a></li>
				<li><a href="<c:url value='${appPath}/jobReleaseManagement?requirementUUID=${requirementUUID}'/>">Job Release</a></li>
				<li class="current"><a href="<c:url value='${appPath}/jobRecommendations?requirementUUID=${requirementUUID}'/>">Recommendations</a></li>
				<div class="clearfix mb"></div>
			  </ul>
	   <div class="clearfix"></div>
    </div>
    </div>
    <div class="content-area clearfix">
    <jsp:include page="jobRecommendationCandidtesListTable.jsp"></jsp:include>
    
    <div class="form-footer clearfix row">
              <div class="col-lg-12">
                <div class="pull-right">
                  <input type="button" value="Go to list"  class="btn btn-primary ml" onClick="javascript:window.location.href = '<c:url value='${appPath}/jobs'/>'"  class="btn btn-default ml">
                </div>
              </div>
    </div>
    
    </div>
    
<div class="side_mask">
  <div class="proj_close" onClick="activeTabs();"></div>
</div>
    
  </section>
<!-- Content Area Starts -->

<script>
var candidateStatusGlobal ="";
function clientSideBar(candidateId,recScore){
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
           success :function(result) {
        	   var json = result.object;
        	   if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
	                if(json != null){ 
	              			 candidateStatusGlobal = json.status;
		                	 $("#candidateSideDiv").empty();
				             
				             var country = '';
					         var state = '';
					         var city = '';
				             
				             if(json.country != null && json.country != '' && json.country != 'undefined'){
		         			 		country = json.country;
		             			 	}else{
		             			 		country = '';
		             			 	}
		         			 	
		         			 	if(json.state != null && json.state != '' && json.state != 'undefined'){
		         			 		state =json.state +',';
		             			 	}else{
		             			 		state = '';
		             			 	}
		         			 	
		         			 	if(json.city != null && json.city != '' && json.city != 'undefined'){
		         			 		city =json.city+',';
		             			 	}else{
		             			 		city = '';
		             			 	}
		         			 	
		         			 	if(json.currentRate != null && json.currentRate != '' && json.currentRate != 'undefined'){
		         			 		currentRate = json.currentRate;
		         			 	}else{
		         			 		currentRate = '';
		         			 	}
		         			 	
		         			 	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
		         			 		rateType = json.rateType;
		         			 	}else{
		         			 		rateType = '';
		         			 	}
		         			 	
		         			 	if(json.currentRateType != null && json.currentRateType != '' && json.currentRateType != 'undefined'){
		         			 		currentRateType = json.currentRateType;
		         			 	}else{
		         			 		currentRateType = '';
		         			 	}
		         			 	
		         			 	if(json.currentRateCurrency != null && json.currentRateCurrency != '' && json.currentRateCurrency != 'undefined'){
		         			 		currentRateCurrency = json.currentRateCurrency;
		         			 	}else{
		         			 		currentRateCurrency = '';
		         			 	}
		         			 	
		         			 	if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
		         			 		expectedRate = json.expectedRate;
		         			 	}else{
		         			 		expectedRate = '';
		         			 	}
	
		         			 	if(json.expectedRateCurrencey != null && json.expectedRateCurrencey != '' && json.expectedRateCurrencey != 'undefined'){
		         			 		expectedRateCurrencey = json.expectedRateCurrencey;
		         			 	}else{
		         			 		expectedRateCurrencey = '';
		         			 	}
		         			 	
		         			 	if(json.lastName != null && json.lastName != '' && json.lastName != 'undefiend'){
			                		 lastName  = json.lastName;
			                	 }else{
			                		 lastName = "";
			                	 }
				             if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
				            	 mobilePhone = json.mobilePhone;
				             }else{
				            	 mobilePhone ="--";
				             }
		                	 var result = "";
		                	 result = result+'<div id="popUp" ></div>  ';
		                	 result = result+'<form id="candidate_form" name="candidate_form" method="post" >';
				             result = result+'  <div class="profile_details clearfix" style="margin-bottom:20px;">';
				             result = result+'               <div class="col-md-6">';
				             result = result+'                   <div class="media">';
				             
				             result = result+'<div class="col-xs-12 col-sm-5 col-md-5">';
				             result = result+'<div id="candidateImageId">';
				             result = result+'<div class="well well-sm text-center clearfix positionRelative">';
							 result = result+' <div class="profile-img-container profile-image text-center">';
				               if(json.fileUrl != null && json.fileUrl !='' && json.fileUrl !="undefined"){
							result = result+'   <img  id="userImage" data-id="profileImage" onerror="predefineProfileImage(this);" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';						 
				               }else{
							result = result+'   <img  id="userImage" data-id="profileImage" onerror="predefineProfileImage(this);" src="/resources/images/userprofileImg.jpg" alt="Profile Image" class=" img-responsive center-block img-circle">';						 
		   
				               }
							if(recScore != null && recScore != '' && recScore != 'undefined'){
								  if(recScore >=50){ 
								 	 result = result +'                    <i class="valid-profile fa fa-check-square text-success"></i>';        
								  }
							  }
							 result = result+'  <div class="btn-group uploadfile uploadbtn-custom">';
							 result = result+'  <button type="button" class="btn btn-sm btn-primary">';
							 result = result+'  <i class="fa fa-upload"></i></button>';
							 result = result+'  <label class="btn btn-sm btn-primary">';
							 result = result+'    Upload / Change<input type="file" name="file" id="uploadedImage"  accept="image/*" onchange="cropImage(this,\'candidate_form\','+candidateId+');"  style="display: none;">';					     result = result+'  </label>';
						     if(json.fileUrl != null && json.fileUrl != '' && json.fileUrl != 'undefined'){
							     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
								 result = result+'  <i class="fa fa-trash"></i></button>';
						     } 
						     
							 result = result+'  </div>';
							 result = result+'  </div>';
							 result = result+'  </div>';
							 
							 
							 result = result+'  <div class="mb mt5 UsProBar">';                                                                                                                                        
								result = result+' <div class="progress nomr">  ';
							if(json.profilePercentage>=50){ 
								result = result+'   <div class="progress-bar  progress-bar-success  ';
							}
							else{
								result = result+'   <div class="progress-bar  progress-bar-danger  ';
							}
							result = result+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%">  ';
							result = result+'     <p id="disVal">'+json.profilePercentage+'%</p>                                                                                                                                                                      ';
							result = result+'   </div>  ';
							result = result+' </div>                 ';                                                                                                                                                      
							result = result+' </div> ';
							
							 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
							 result = result+' </div>';
							 result = result+' </div>';
							 result = result+'</form>';
				             result = result+'                     <div class="media-body candidate-details candidateDetails">';
				             
				             result = result+' <h4 class="media-heading">'+json.firstName+' '+lastName+'</h4>';
				            
				             result = result+'<ul class="list-unstyled">';
				             result = result+'<li>Type - '+json.candidateType+'</li>';
				             
				             if(json.designation != null && json.designation !='' && json.designation !="undefined"){
							 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Designation</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title= '+json.designation+'> '+json.designation+'</span></li>';
				             }else{
		                   	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Designation</span><span class="col-md-7 padding_left padding_right">---</span></li>';  
	
				             }
				             if(json.designationType != null && json.designationType !='' && json.designationType !="undefined"){
	
							 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Employment Type</span><span class="col-md-7 padding_left padding_right">'+json.designationType+'</span></li>';
				             }else{
			                  result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Employment Type</span><span class="col-md-7 padding_left padding_right">---</span></li>';  
				             }
							 
		         			 if(json.vendorName != null && json.vendorName != '' && json.vendorName != 'undefined'){
	
							 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Vendor</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title='+json.vendorName+'>'+json.vendorName+'</span></li>';
		         			 }
		         			 
		         			if(json.vendorContactName != null && json.vendorContactName != '' && json.vendorContactName != 'undefined'){
	
							result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Vendor Contact</span><span class="col-md-7 padding_left padding_right tbldata-dotdot-sm" title = '+json.vendorContactName+'>'+json.vendorContactName+'</span></li>';
			         		}
				             
				              if(json.experienceYears != null && json.experienceYears != '' && json.experienceYears != 'undefined' && json.experienceYears != '0'){
				            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Experience</span><span class="col-md-7 padding_left padding_right">'+json.experienceYears+' Year(s) '+json.experienceMonths+' Month(s)</span></li>';
	              		       }else{
	                		   		 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Experience</span><span class="col-md-7 padding_left padding_right">---</span></li>';  
	              		       }
				            
				             
				              if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod != 'undefined'){
				                    	 
				             result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Notice Period</span><span class="col-md-7 padding_left padding_right">'+json.noticePeriod+'(s)'+'</span></li>';
				             
	             		       }else{
	             		                                                	  
	                   		     result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Notice Period</span><span class="col-md-7 padding_left padding_right">---</span></li>';  
	             		     
	             		       }
				              result = result+'</ul>';
				              result = result+'<div class="clearfix"></div>';
				              result = result+'<div class="clearfix">';
				              result = result+'<ul class="list-inline list-unstyled">';
					             
					             if(json.primarySkill != null && json.primarySkill != '' && json.primarySkill != 'undefined'){
					            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Primary Skill</span><span class="padding_left padding_right">'+json.primarySkill+'</span></li>';
					             }else{
					            	 result = result+'<li><span class="col-md-5 padding_left paddingRight text-muted">Primary Skill</span><span class="col-md-7 padding_left padding_right">---</span</li>';  
					             }
				             result = result+'                       </ul>';
				             result = result+'                       </div>';
				             result = result+'                 <div class="clearfix">';
				             result = result+'                 <div class="socialnetwork-details">';
				             result = result+'                 <ul class="list-inline list-unstyled">';
				             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.facebook != null && json.consultantUser.facebook !="undefined" && json.consultantUser.facebook != ""){
					             result = result+'<li class="bg-facebook" title="Facebook"><a target="_blank" href=\"'+json.consultantUser.facebook+'\" > <i class="fa fa-facebook-square" ></i></a></li>';
					             }
					             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.twitter != null && json.consultantUser.twitter !="undefined" && json.consultantUser.twitter != ""){
					             result = result+'<li class="bg-twitter" title="Twitter"><a target="_blank" href=\"'+json.consultantUser.twitter+'\"> <i class="fa fa-twitter-square" ></i></a></li>';
					             }
					             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.googlePlus != null && json.consultantUser.googlePlus !="undefined" && json.consultantUser.googlePlus != ""){
					             result = result+'<li class="bg-google " title="Google-Plus"><a target="_blank" href=\"'+json.consultantUser.googlePlus+'\"> <i class="fa fa-google-plus-square" ></i></a></li>';
					             }
					             if(json.consultantUser !=null && json.consultantUser.id != null && json.consultantUser.linkedIn != null && json.consultantUser.linkedIn !="undefined" && json.consultantUser.linkedIn != ""){
					             result = result+'<li class="bg-linked" title="Linkedin"><a target="_blank" href=\"'+json.consultantUser.linkedIn+'\"> <i class="fa fa-linkedin-square" ></i></a></li>';
					             }
				             
				             result = result+'   </ul>';
				             result = result+'                 </div>';
				             result = result+'                 </div>';
				             result = result+'               </div>';
				             result = result+'                   </div>';
				             result = result+'                 </div>';
				             result = result+'               <div class="col-md-6">';
				             result = result+'                 <ul class="list-unstyled can-perdt">';
				             result = result+'                   <li title="'+json.email+'"><i class="fa fa-envelope-o" title="Email"></i>'+json.email+'</li>';
				             result = result+'                   <li><i class="fa fa-phone" title="PhoneNo"></i>'+mobilePhone+'</li>';
				                                                  
				             result = result+'                   <li title="'+city+''+state+''+country+'"><i class="fa fa-location-arrow" title="Location"></i>'+city+''+state+''+country+'</li>';
				             
				             result = result+'                   <li><i class="fa fa-money" title="Current Rate"></i><b>Current Rate:</b>' +currentRate+'  '+currentRateType+' / '+currentRateCurrency+'</li>';
				             
				             result = result+'                   <li><i class="fa fa-money" title="Expected rate"></i><b>Expected Rate:</b>'+expectedRate+'  '+rateType+' / '+expectedRateCurrencey+'</li>';
				             result = result+'                 </ul>';
				             result = result+'               </div>';
				             result = result+'			</div>';
				             result = result+'   <div class="proj_close"></div>';
				             result = result+'   <div class="project_side_wrap_inn_tabs">';
				             result = result+'     <ul class="canreq-tabs-menu">';
				             result = result+'       <li class="current"><a href="#tab-11" onclick="candidateProfile('+candidateId+');">Profile</a></li>';
				             result = result+'       <li><a href="#tab-12" onclick="candidateResumesList('+candidateId+'); candidateResume('+candidateId+');" >Resume</a></li>';
				             result = result+'       <li><a href="#tab-13" onclick="candidateExperience('+candidateId+');">Employers</a></li>';
				             result = result+'       <li><a href="#tab-14" onclick="candidateEducation('+candidateId+');">Education</a></li>';
				             result = result+'       <li><a href="#tab-15" onclick="candidateCertificate('+candidateId+');">Certifications</a></li>';
				             
				             result = result+'<li><a href="#tab-17" onclick="candidateDocuments('+candidateId+');">Documents</a></li>';
							 result = result+'<li><a href="#tab-18" onclick="candidateReferences('+candidateId+');">References</a></li>';
				             
				             //result = result+'       <li><a href="#tab-16" onclick="candidateActivities('+candidateId+');">Activities</a></li>'; 
				             result = result+'       <li><a href="#history" onclick="candidateHistory('+candidateId+');">History</a></li>';
				             result = result+'       <div class="clearfix"></div>';
				             result = result+'     </ul>';
				             result = result+'     <div class="project_tabs">';
					         result = result + ' <div id="tab-11" class="canreq-tabs-menu_inn">';
							 result = result + '</div>';
							 
							 result = result + '<div id="tab-12" class="canreq-tabs-menu_inn">';
							 result = result + '</div>';
							 
							 result = result + '<div id="tab-13" class="canreq-tabs-menu_inn">';
							 result = result + '</div>';
							 
							 result = result + ' <div id="tab-14" class="canreq-tabs-menu_inn">';
							 result = result + '</div>';
							 
							 result = result + '<div id="tab-15" class="canreq-tabs-menu_inn">';
							 result = result + '</div>';
							 
							 result = result + '<div id="tab-17" class="canreq-tabs-menu_inn">';
							 result = result + '</div>';
							 
							 result = result + '<div id="tab-18" class="canreq-tabs-menu_inn">';
							 result = result + '</div>';
							 
							 result = result + '<div id="tab-16" class="canreq-tabs-menu_inn">';
							 result = result + '</div>';
							 
							 result = result + '<div id="history" class="canreq-tabs-menu_inn">';
							 result = result + '</div>';
							 
				             result = result+'     </div>';
				             result = result+'   </div>';
				             
				             $("#candidateSideDiv").empty();  
				 		     $("#candidateSideDiv").append(result);  
					 		 $(".clients_side_wrap").addClass("clients_side_wrap_active");
							 $(".side_mask").fadeIn(200);
							 $("body").css('overflow-y', 'hidden');
							 candidateProfile(candidateId);
							 hilightTab();
	                }
				} else if (result.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
         },
      }); 
}
</script>

<script type="text/javascript">
function activeTabs(){
	document.getElementById("tab-1").style.display="none";
	$("#quickviewTabsdetails").removeClass("active");
	$("#quickviewTabsqv").addClass("active");
	$("#quickresume_view").addClass("active in");
	$("#detailrecommondations").removeClass("active in");
	
	$("#pipelineQuickviewTabsdetails").removeClass("active");
	$("#pipelineQuickviewTabsqv").addClass("active");
	$("#quickview").addClass("active in");
	$("#detailview").removeClass("active in");
}
</script>

<script>

function hilightTab(){
	$("#tab-11").show();	
    $(".canreq-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        
        var tab = $(this).attr("href");
        $(".canreq-tabs-menu_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });	
}
</script>

<script>

// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
		 $(".action-pop").slideUp(200);
		
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	

// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
		
		clearCandAdvSearch();
		$(".submission_side_wrap").removeClass("submission_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
		
		$(".import_submission_side_wrap").removeClass("import_submission_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
	
});

// project side bar tabs
$(document).ready(function() {
	$("#tabb-1").show()
    $(".new-tabs a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".new-tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

// script for scroll Popups:: Add or Edit details
$(document).ready(function(event) {	
$(".addUser-org").click(function(){
	 $(".edit-details").toggleClass("edit-details-active");
	 $(".collapse-div").removeClass("edit-details-active");
 });
 $(".close-div").click(function(){
	 $(this).closest(".edit-details").toggleClass("edit-details-active");
	 });
 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
	 e.stopPropagation();
 });
/*  $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	 */
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
  // event.preventDefault();
});

$(".addSkills").click(function(){
	$(".add-skills").toggleClass('add-skills-active');
	});
$(".close-div").click(function(){
	$(".add-skills").removeClass('add-skills-active');
	});
$(".add-skills").click(function(e){
	 e.stopPropagation();
 });

// Bar chat pop
$(document).ready(function() {
    $(".graf_icon").click(function(){
		$(".pipeline_graf").slideToggle(200);
	});
$( "ul#canName-ch li").click(function(){
   var m = $(this).text();
  $("#canName").text(m);
});
});
</script> 

<jsp:include page="recommendCandidatesQuickView.jsp"></jsp:include>
<jsp:include page="recommendCandidatesProfile.jsp"></jsp:include>
<jsp:include page="recommendCandidatesResumes.jsp"></jsp:include>
<jsp:include page="recommendCandidatesExperience.jsp"></jsp:include>
<jsp:include page="recommendCandidatesEducation.jsp"></jsp:include>
<jsp:include page="recommendCandidatesCertificates.jsp"></jsp:include>
<jsp:include page="recommendedCandidateDocuments.jsp"></jsp:include>
<jsp:include page="candidateReferences.jsp"></jsp:include>
<jsp:include page="recommendCandidatesHistory.jsp"></jsp:include>
<jsp:include page="../masterFooter.jsp" />