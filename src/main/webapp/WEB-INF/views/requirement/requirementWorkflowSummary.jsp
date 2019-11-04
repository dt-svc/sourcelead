<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/requirement-summary.css?staticTime=${staticTime}" type="text/css" rel="stylesheet"/>
<script>
var atsUrl = '${SourceLeadWeb_atsUrl}';
var sourceleadUrl ='${sourceleadUrl}';
$(document).ready(function() {
	var requirementId = '${requirementId}';
	var submissionId = '${submissionId}';
	var candidateId = '${candidateId}';
	 var submissionStatus='${submissionStatus}'
	var type = '${type}';
	 getJobsTagsList(requirementId)
	 if(type != null && type != '' && type != 'undefined'){
		 sideBar(requirementId,type);
		 requirementWorkflowStatusBar(requirementId);
	 }
	 
 if(submissionId != null && submissionId != '' && submissionId != 'undefined' && candidateId != null && candidateId != '' && candidateId != 'undefined'){
	 if(type == 'applicants'){
		 type = "all";
	 }
	 requirementPipeline(requirementId,type);
	 }
  else if(submissionStatus == 'all'){
	 $("#detailsTab").removeClass("current");
	 $('li[name=applicantNotification]').addClass("current");
	 requirementWorkflowStatusBar(requirementId);
	 requirementPipeline(requirementId,"all");
	 }  
 else if(submissionId != null && submissionId != '' && submissionId != 'undefined' && requirementId != null && requirementId != '' && requirementId != 'undefined'){
	 requirementPipeline(requirementId,type);
		 }
});	
</script>
<script>
//to remove console errors added temporarily 
function requirementRecommendations(requirementId, requirementSubmissionStatus){


}
</script>

<script>
function prepareAllCounts(requirementId){
	$.ajax({
	       type : "POST",
	        url: atsUrl+"/getSubmissionsCountBySubmissionStatus?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&submissionType=ALL",        
	        success :function(data) { 
	       	 var json = data.object;
	       	 $("#applicantsCount").html(json.applicantsCount);
	       	 $("#requestSent").html(json.requestSent);
	       	 $("#shortlistCount").html(json.shortlistCount);
	       	 $("#submitToClientCount").html(json.submitToClientCount);
	       	 $("#interviewCount").html(json.interviewCount);
	       	 $("#offersCount").html(json.offersCount);
	       	 $("#joinedCount").html(json.joinedCount);
	       	 $("#rejectedCount").html(json.rejectedCount);
	       	 $("#backoutCount").html(json.backoutCount);
	        },
		  });
	
	$('#duplicataplctli').load(duplicateCount(requirementId));
    
	
}
</script>
<script>
function getWorkflowStageColumnAndCountIds(){
	var stageColumnsMap = new Map();
	$('b[shortCode]').each(function(){
		  stageColumnsMap.set(this.id, $(this).attr("shortCode")); // preparing map with id and shortcode.
		});
	return stageColumnsMap;
}
</script>
<script>
function prepareAllWorkflowCounts(requirementId){
	var stages = new Array(); 
	<c:forEach var="stage" items="${stagesList}">
		stages.push('${stage.stageShortCode}');
	 </c:forEach>
	 
	$.ajax({
	       type : "POST",
	        url: atsUrl+"/getWorkflowSubmissionsCountBySubmissionStatus?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&submissionType=ALL"+"&workflowStages="+stages,        
	        success :function(data) {
	        var stageColumnsMap = getWorkflowStageColumnAndCountIds();
		      if(data != null){
		       // key means id of the element to updated and value is the count, returned from the service by stage wise.
				 for (var [key, value] of stageColumnsMap) { 
					 $("#"+key).html(data[value]);
					}
		       }
	       },
	 });
	
	$('#duplicataplctli').load(duplicateCount(requirementId));
    
	
}
</script>

<script>
function duplicateCount(requirementId){
	
	  $.ajax({
      type : "POST",
       url: atsUrl+"/getDuplicateSubmissionsCountByRequirementId?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId,        
       success :function(data) { 
      	 var json = JSON.parse(data);
      	 $("#duplicateApplicantsCount").html(json);
       },
	  }); 
}
</script>

<!-- Content Area -->
<section class="content-wraper" onclick="popClose(); piplineexportClose();">


<div class="success_pop" id="candSelector" style="display: none;"> 
    	 <i class="fa fa-check-circle"></i><span id="candMsgDiv"></span> 
   		</div>
   		
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Job Summary</h2>
    </div>
    <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo;<a href="${SourceLeadWeb_atsUrl}/jobsList"> Jobs List  </a>&raquo; Job Details</div>
  </section>
  

 
  <div class="success_pop tagStyles" id="addJobTagAlert">
		<i class="fa fa-check-circle"></i>
   		<span></span>
   </div>
   <div class="remove_pop tagStyles" id="deleteJobTagAlert">
		<i class="fa fa-times"></i>
   		<span></span>
   </div>
   
  <!-- <div class="timesheet_out"> -->
  <div class="clearfix">
    
    
     <div class="clients_side_wrap_inn no_pad">
     <div class="project_wrap">
        <div id="requirementSideDiv"> <jsp:include page="requirementWorkflowQuickView.jsp" /> </div>
          <div id="loading"></div>
          </div>
          
  <div class="cearfix">
	  <div class="newTagsWrap">
			<ul class="clearfix tagsWrap tagsWrapBor">
			<select multiple class="chosen-select form-control" name="tagName" id="jobsTagName" data-placeholder="Add Tags" data-validation="validate_Space"  data-validation-optional="true">
			</select>
			</ul>
			<div class="clearfix"></div>
	  </div>
 </div>          
          
        <input type="hidden" id="emptyPipelinelist" name="emptyPipelinelist">
       <!--  <div id="requirementStatusDiv"> </div>  -->
      </div>   
      
      <!-- Addede by Rajashekar -->
      <!-- <div id="tab-3" class="project_wrap_table project_tabs project_tabs_inn">
          <div id="recommendationsList"></div>
       </div>  -->           
            
          </div>
    
    <!-- <div class="side_mask">
      <div class="proj_close"></div>
    </div> -->
      
      <!-- End Raj --> 
    
    
<div class="side_mask">
  <div class="proj_close" onClick="activeTabs();"></div>
</div>
   
</section>
 
<div class="success_pop" id="selector11" style="display: none;">
    <i class="fa fa-check-circle"></i><span id="msgDiv"></span>
</div> 

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

 
<script>
var select, chosen;
select = $("#jobsTagName");
select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
chosen = select.data('chosen');
chosen.search_field.on('keyup', function(e)
{
	var anyKeyword = this.value.replace(",","");
    if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
    {
    	var par = this.parent;
        var option = $("<option>").val(anyKeyword).text(anyKeyword);
        select.append(option);
        select.find(option).prop('selected', true);
        $("#jobsTagName option").each(function(){
              $(this).siblings("[value='"+ this.value+"']").remove();
        });
		$("#jobsTagName").trigger("chosen:updated");
		saveJobsTags("add");
    }
});

$('.chosen-select').chosen().on('change', function(evt, params) {
	if (params.selected && params.selected != undefined) {
		saveJobsTags("add");
	}
	if (params.deselected && params.deselected != undefined) {
		saveJobsTags("delete");
	}
	
});
$("#jobsTagName_chosen").find('ul.chosen-choices > li input').attr("placeholder", "Add Tag")
</script>
<script>
function getJobsTagsList(requirementId){
	$.ajax({  
        type : "POST",
        url: atsUrl+"/getJobsTags?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId,        
        success :function(json) {
        	var result = "";
			$.each( json.tagsList, function( i, tags ) {
			  var existed = 0;
                if(json.jobsTags != null && json.jobsTags != 'undefined' && json.jobsTags != '') {
	                var res = [];
	                res = json.jobsTags.split(",");
	                res.forEach(function(item) {
	                	if(tags == item){
	                		existed = 1;
	                		result = result + ' <option selected value="'+tags+'">'+tags+'</option>';
	                	}
	                });
                }
                if(existed == 0){
                	result = result + ' <option value="'+tags+'">'+tags+'</option>';
                }
			});
            
            $("#jobsTagName").empty();
            $("#jobsTagName").append(result);
            $("#jobsTagName").chosen().trigger("chosen:updated")
        },
	});
}
</script>
<script>
function saveJobsTags(type){
	 var requirementId = '${requirementId}';
	 var tagName = $("#jobsTagName").val();
	 var jsonData = encodeURIComponent(tagName);
	 var loginUserId = '${SourceLeadWeb_userId}';
	 if(tagName != null && tagName != '' && tagName !='undefined') {
		 Urlcheck = atsUrl+"/saveOrUpdateTagsByJobId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&requirementId="+requirementId+"&tagName="+jsonData
	 } else {
		 Urlcheck = atsUrl+"/saveOrUpdateTagsByJobId?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&requirementId="+requirementId+"&tagName="+""
	 }
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(result) {
        	$("#editRoleLoading").removeAttr('style');
			if(result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
        		showError(result.responseMessage);
        	} else if (result.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (result.response === "SUCCESS") {
        		if(type == "add"){
        			showSuccessStatusPopup("Tag added successfully");
        		}else if(type == "delete"){
        			showDeleteStatusPopup("Tag deleted successfully");
        		}
        		getJobsTagsList(requirementId);
        	} else if (result.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
       },
    }); 
}
function showSuccessStatusPopup(status) {
	$("#addJobTagAlert").css("display","block");
	$("#addJobTagAlert span").html(status);
    $('#addJobTagAlert').delay(2000).fadeOut('slow');
}
function showDeleteStatusPopup(status) {
	$("#deleteJobTagAlert").css("display","block");
	$("#deleteJobTagAlert span").html(status);
    $('#deleteJobTagAlert').delay(2000).fadeOut('slow');
}
</script>


<!-- Show success message after form submission script -->
<script>
	function msgFadein() {
		$("#selector11").fadeIn(200);
        $('#selector11').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->


  <script>
	   function candMsgFadein() {
			$("#candSelector").fadeIn(200);
		    $('#candSelector').delay(2000).fadeOut('slow');
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



<script>
$( "#pri-ch li").click(function(){
   var m = $(this).text();
    var mid = $(this).attr('id');
    $("#pri-val").text(m);
     if('h' === $(this).attr("id")){
          $("#pri-val").removeClass("text-warning text-primary").addClass("text-danger");
     }else if('m' === $(this).attr("id")){
        $("#pri-val").removeClass("text-danger text-warning").addClass("text-primary");
     }else{
      $("#pri-val").removeClass("text-danger text-primary").addClass("text-warning");
     }   
});
</script> 
<script>
$(document).ready(function(){
	// Levels adding script
$(".add-offer").click(function(){
	 $(".addOffer-Req").toggleClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-interview").click(function(){
	 $(".addInterview-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addApplicant-Re").removeClass("show-div");
 });
$(".add-applicant").click(function(){
	 $(".addApplicant-Re").toggleClass("show-div");
	 $(".addOffer-Req").removeClass("show-div");
	 $(".addInterview-Re").removeClass("show-div");
 });
$(".reminder").click(function(){
	 $(".reminder-div").toggleClass("reminder-div-active");
 });

 $(".close-div").click(function(){
	 $(this).parent().toggleClass("show-div");
	 });
 $(".div-close").click(function(){
	 $(this).parent().toggleClass("showDiv");
	 });

 $(".reminder").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $("").removeClass("show-div");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   });
</script>
<!-- <script type="text/javascript">
            $(function () {
                $('#datetimepicker5').datetimepicker({
					locale: 'ru'
                });
            });
</script> -->
</body>
</html>

<script type="text/javascript">
function openTopDiv(){
	 $(".hide-row").slideToggle(200);
	  $(".profile_details h4 i").toggleClass('icon-add');
}  
 /*  $("#id1").click(function(){ 
	  $(".hide-row").slideToggle(200);
	  $(".profile_details h4 i").toggleClass('icon-add');
});	  */ 
 </script>
<%-- 
     @author : sarath kumar.
     this script is for show side bar when you click on the candidate name.
     1.usage     : called at candidateList() function.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function sideBar(requirementId,type){
		var atsUrl = '${SourceLeadWeb_atsUrl}';
	    if(type != null && type != '' && type != 'undefined'){
	    	$("#details-div" ).hide();
			 if(type == 'SHORTLIST'){
				 	requirementPipeline(requirementId,type);
					requirementStatusBar(requirementId);
					tabEnable('','shortListli');
			 }
			 else if(type == 'SUBMIT_TO_CLIENT'){
				 	requirementPipeline(requirementId,type);
					requirementStatusBar(requirementId);
					tabEnable('','submitToClientli');
			 }
			 else if(type == 'INTERVIEW'){
				 	requirementPipeline(requirementId,type);	
					requirementStatusBar(requirementId);
					tabEnable('','interviewli');
			 }else if(type == 'JOINED'){
				 	requirementPipeline(requirementId,type);
					requirementStatusBar(requirementId);
					tabEnable('','joinedli');
			 }
			 else if(type == 'OFFERMADE'){
				 	requirementPipeline(requirementId,type);
					requirementStatusBar(requirementId);
					tabEnable('','offerMadeli');
			 }else if(type == 'APPLICANTS' || type == 'applicants'){
				    
				    requirementPipeline(requirementId,'all');
					requirementStatusBar(requirementId,type);
					
					 var candidateId = '${candidateId}';
					 var submissionId = '${submissionId}';
					 
					 if(candidateId != null && candidateId != '' && candidateId != 'undefined' && 
							 submissionId != null && submissionId != '' && submissionId != 'undefined'){
						 applicantQuickViewProfile(submissionId,candidateId); 
						 getSubmissionDetailsSideBar(submissionId,candidateId,requirementId); 
						 document.getElementById('detailedView').click();
					 }
			 }
			 else{
				    requirementPipeline(requirementId,'all');
					requirementStatusBar(requirementId,type);
					
					 var candidateId = '${candidateId}';
					 var submissionId = '${submissionId}';
					 if(candidateId != null && candidateId != '' && candidateId != 'undefined' && 
							 submissionId != null && submissionId != '' && submissionId != 'undefined'){
						 applicantQuickViewProfile(submissionId,candidateId); 
						 getSubmissionDetailsSideBar(submissionId,candidateId,requirementId); 
						 document.getElementById('detailedView').click();
					 }
			 }
		 
	    }
	    else{
	    	$("#details-div" ).show();
	    	$("#actionsDiv" ).hide();
	    	$("#tab-2").empty();
			$("#tab-3").empty();
			$("#tab-4").empty();
			$("#status-div").empty();
		    $("#status-data").empty();
		    $("#tab-1").show();
	    }
	     $("#detailsLoading").removeAttr("style");
                
				
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
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>


<script>
function viewJobSummaryDocument(jobId,title){
	var atsUrl = '${SourceLeadWeb_atsUrl}';
		if(jobId != null && jobId != ""){
			window.open(atsUrl+'/viewJobSummaryDocumentDocView/'+title+'?jobId='+jobId,title,"height=600,width=600");
		}  
}

</script>

<%-- 
     @author : sarath kumar.
     this script is for update Requirement.
     1.usage     :  used in Requirement jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function updateReuqirementStatus(status){
	
	
	 var requirementId = '${requirementId}';
	 var isLoginUserAsAdmin = '${isAdmin}';
	 var requirementUUID = '${requirementUUID}';
	 
	 var reason;
	 if ($('#rejectReason').length)
	 {
		 reason = document.getElementById("rejectReason").value; 
	 }
	 else
	 {
		 reason = '';
	 }
     $("#approverbut").attr("disabled", "disabled");
 	
	$('#reasonDiv').empty();
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
 	$.ajax({
      type : "POST",
      url: atsUrl+"/requirementAcceptOrReject?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status+"&reason="+reason+"&isLoginUserAsAdmin="+isLoginUserAsAdmin, 
    		  success :function(data) {  
            if(data != null && data != 'undefined' && data != ""){  
          		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
          		 else if (data.indexOf('exception') >= 0) {
          			 location.replace(atsUrl+"/errorPage");
          		 }
          		 else{ 
          			 $("#selector11").removeAttr("style");
           			 $("#selector11").css({ display: "block" });
          			 if(status == "APPROVED"){
          				$("#msgDiv").html("Job approved Successfully");
          			 }else{
          				$("#msgDiv").html("Job rejected Successfully");
         			   }
          			location.replace(atsUrl+"/requirementSummary?requirementUUID="+requirementUUID);
          			$("#selector11").fadeOut(3000);
          			sideBar(requirementId);
             			msgFadein();
               	 }
            }
       },
	  }); 
	 
}
</script>

<script type="text/javascript">
function cancelAlert12(){ 
	$("#rejectRequirementAlert").empty();
	$(".project_inn_mask").fadeOut(200);
	$(".remove_user_pop").hide();
	$('#approverbut').show();
	$('#rejectedbut').show();
	
}
</script>

<%-- 
     @author : Venkat Kesav
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function rejectRequirementAlertMessage(status){
	var result = "";
/* 	result = result +"	<div class='project_inn_mask'></div>"; */
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-5000' name='rejectReason' value='' placeholder='Reject Reason' rows='2'></textarea>";
	result = result +"	<div id='reasonDiv'/>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Reject' onClick ='updateReuqirementStatusReject(\""+status+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert12();'></b>";
	result = result +"	</div>";
	result = result +"	</div>";
	$("#rejectRequirementAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function updateReuqirementStatusReject(status){
	
	
	var requirementId = '${requirementId}';
	var isLoginUserAsAdmin = '${isAdmin}';
	 
	 var reason = document.getElementById("rejectReason").value;
	  if(reason.match(/^[^\s-](.*)*$/) != null){
	$('#reasonDiv').empty();
 	$.ajax({
      type : "POST",
      url: atsUrl+"/requirementAcceptOrReject?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&status="+status+"&reason="+reason+"&isLoginUserAsAdmin="+isLoginUserAsAdmin, 
    		  success :function(data) {  
            if(data != null && data != 'undefined' && data != ""){  
          		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
          		 else if (data.indexOf('exception') >= 0) {
          			 location.replace(atsUrl+"/errorPage");
          		 }
          		 else{ 
          			 $("#selector11").removeAttr("style");
           			 $("#selector11").css({ display: "block" });
          			 if(status == "APPROVED"){
          				$("#msgDiv").html("JOb approved Successfully");
          			 }else{
          				$("#msgDiv").html("Job rejected Successfully");
          			   }
          			$("#selector11").fadeOut(3000);
          			$("#rejectRequirementAlert").empty();
          			$(".project_inn_mask").fadeOut(200);
          			$(".remove_user_pop").hide();
          			sideBar(requirementId);
             			msgFadein();
               	 }
            }
       },
	  }); 
	  }
	  else{
		  if(reason){
				$("#reasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
			}else{
				$("#reasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
			}
	  }
	 
}
</script>


<%-- 
     @author : Venkat Kesav
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
	function getColumnsMapbyWorkflowStage(requirementId, submissionType) {
	//	 submissionType = 'SCRNG'; 
		 var workflowId ='${workflowId}';
		    $("#actionsDiv" ).show();
			$("#emptyPipelinelist").val('');
			var atsUrl = '${SourceLeadWeb_atsUrl}';
			var orgLocationId = '${SourceLeadWeb_orgLocationId}';
			var userId = '${SourceLeadWeb_userId}';
			var loginUserRole = '${SourceLeadWeb_loginUserRole}';
			var userCode = '${SourceLeadWeb_userCode}';
			var searchValue = $("#submissionDataTableSearch").val();
			var clientSubmissions = '${clientSubmissions}';
			searchValue = searchValue ? searchValue : "";
			
		$.ajax({
			type : "POST",
			url : sourceleadUrl + "/getColumnsMapbyWorkflowStage?shortCode="+submissionType+"&workflowId="+ workflowId,
			 success : function(columnsMap) {
				//alert(columnsMap)
				var columnsArr=[];
				$.map( columnsMap, function( colProp, colName ) {
				//	console.log(colProp);
					columnsArr.push(colProp);
	             });
				//submissionType = 'ONHOLD';
				var url = atsUrl+"/loadWorkflowSubmissionsListByRequirementId?searchValue="+searchValue+"&requirementId="+requirementId+"&submissionType="+submissionType+"&orgLocationId="+orgLocationId+"&userId="+userId+"&loginUserRole="+loginUserRole+"&userCode="+userCode+"&clientSubmissions="+clientSubmissions+"&workflowSatgeColumns="+columnsArr+"&workFlowId="+workflowId;
				 requirementWorkflowPipelineList(url, requirementId, submissionType, columnsMap);
				
			}
		});
		
		
	}
</script>
<script>  
function requirementWorkflowPipeline(requirementId,workflowShortCode){
	//var url = atsUrl+"/loadWorkflowSubmissionsListByRequirementId?searchValue="+searchValue+"&requirementId="+requirementId+"&submissionType="+submissionType+"&orgLocationId="+orgLocationId+"&userId="+userId+"&loginUserRole="+loginUserRole+"&userCode="+userCode+"&clientSubmissions="+clientSubmissions;
	getColumnsMapbyWorkflowStage(requirementId, workflowShortCode);
	prepareAllWorkflowCounts(requirementId);
	}
</script>
<script>
/* var tableHeaders = '';
var columnProps=[];

function requirementWorkflowPipelinePipelineList(url, requirementId, submissionType, columnsMap){

		 $.map( columnsMap, function( val, key ) {
			 tableHeaders = tableHeaders +", "+ key
		  console.log("val is "+val);
		  columnProps.push(val);
		});
		 console.log("**********TABLE HEADERS***************************************");
		 console.log(tableHeaders);
		 console.log("**************COLUMN MAPPINGS***********************************************");
		 console.log(columnProps);
} */


</script>
<script>  
function requirementPipeline(requirementId,submissionType){
	$("#actionsDiv" ).show();
	$("#emptyPipelinelist").val('');
	 var workflowId ='${workflowId}';
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_userId}';
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	var userCode = '${SourceLeadWeb_userCode}';
	var searchValue = $("#submissionDataTableSearch").val();
	var clientSubmissions = '${clientSubmissions}';
	searchValue = searchValue ? searchValue : "";
	var url = atsUrl+"/loadSubmissionsListByRequirementId?searchValue="+searchValue+"&requirementId="+requirementId+"&submissionType="+submissionType+"&orgLocationId="+orgLocationId+"&userId="+userId+"&loginUserRole="+loginUserRole+"&userCode="+userCode+"&workFlowId="+workflowId+"&clientSubmissions="+clientSubmissions;
	requirementPipelineList(url, requirementId, submissionType);
	}
</script>

<script>
function requirementPipelineList(url, requirementId, submissionType){
	var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $("#status-data").removeAttr("style");
	  $("#status-data").empty();
	  
	  var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  requirementPipelineListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  requirementPipelineListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  requirementPipelineListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  requirementPipelineListDate = 'YYYY-DD-MM'; 
	  } 
 	 var result = "";
 	 
 	result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
	 	result = result +'<span class="applicant_export mr">';
		 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
	      result = result +'<a title="Export" onClick="piplineexport();"class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i></a>';
	      result = result +'<div class="export_pipline" style="display:none">';
	      result = result +'<ul>';
	      result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');"><i class="fa fa-file-pdf-o text-danger"></i>&nbsp;&nbsp;PDF</li>';
	      result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');"><i class="fa fa-file-excel-o text-primary"></i>&nbsp;&nbsp;Excel</li>';
	      result = result +' </ul>';
	      result = result +'</div>';
	    '</sec:authorize>';
	    result = result +'</span>';
 	
	  result = result + '<div class="clearfix"></div>';
	  result = result + '<div class="require_wrap_table">';
	  result = result + '<div class="clerfix">';
	  result = result + " <div class='remove_search searchCustom'>";
		result = result
				+ " <input type='text' placeholder='Search' id='submissionDataTableSearch'/>";
		result = result + "</div>";
	  result = result + '<table id="requirementsPagination" class="display" cellspacing="0" width="100%"">';
	  result = result + '<thead>';
	  result = result + '<tr>';
	  result = result + '<th>Name</th>';
	  result = result + '<th>Email</th>';
	  if(submissionType != 'REJECTED' && submissionType != 'BACKOUT'){
	  result = result + '<th>Ph. No</th>';
	  }
	  result = result + '<th>Resume</th>';
	  if(submissionType == 'OFFERMADE'){
		  result = result + '<th>Offer Join Date <br>(<small class="custom-small">'+requirementPipelineListDate+'</small>)</th>';
	  }
	  if(submissionType == 'OFFERMADE'){
		  result = result + '<th>Position Type</th>';
	  }
	  if(submissionType == 'OFFERMADE'){
		  result = result + '<th>Offer Rate</th>';
	  }
	  if(submissionType == 'OFFERMADE'){
		  result = result + '<th>Attachment</th>';
	  }
	  if(submissionType != 'INTERVIEW' && submissionType != 'OFFERMADE'){
	  result = result + '<th>Applied on  <br>(<small class="custom-small">'+requirementPipelineListDate+'</small>)</th>';
	  }
	  if(submissionType != 'INTERVIEW' && submissionType != 'OFFERMADE' && submissionType != 'SUBMIT_TO_CLIENT' && submissionType != 'JOINED'){
	  result = result + '<th>Rate</th>';
	  }
	  if(submissionType == 'REJECTED' || submissionType == 'BACKOUT' || submissionType == 'SUBMIT_TO_CLIENT'){
	  result = result + '<th>Sub. Rate</th>';
	  }
	  if(submissionType != 'INTERVIEW' && submissionType != 'OFFERMADE'){
	  result = result + '<th>Source</th>';
	  }
	  if(submissionType == 'JOINED'){
		  result = result + '<th>Join Date <br>(<small class="custom-small">'+requirementPipelineListDate+'</small>)</th>';
	  }
	  if(submissionType == 'REJECTED'){
		  result = result + '<th>Rejected On <br>(<small class="custom-small">'+requirementPipelineListDate+'</small>)</th>';
	  }
	  if(submissionType == 'REJECTED'){
		  result = result + '<th>Rejected By</th>';
	  }
	  if(submissionType == 'BACKOUT'){
		  result = result + '<th>Back Out On <br>(<small class="custom-small">'+requirementPipelineListDate+'</small>)</th>';
	  }
	  if(submissionType == 'BACKOUT'){
		  result = result + '<th>Back Out By</th>';
	  }
	  if(submissionType == 'SHORTLIST'){
	      result = result + '<th>Shortlisted By</th>';
	  }
	  if(submissionType != 'INTERVIEW' && submissionType != 'OFFERMADE' && submissionType != 'REJECTED' && submissionType != 'BACKOUT' && submissionType != 'JOINED'){
	  result = result + '<th>Score</th>';
	  }
	  if(submissionType != 'INTERVIEW' && submissionType != 'OFFERMADE' && submissionType != 'REJECTED' && submissionType != 'BACKOUT' && submissionType != 'JOINED'){
	  result = result + '<th>Exp</th>';
	  }
	  if(submissionType == 'INTERVIEW'){
		  result = result + '<th>No.of Interviews</th>';
	  }
	  result = result + '<th>Status</th>';
	  result = result + '         </tr>';
	  result = result + '       </thead>';
	  result = result + '       <tbody>';
    $("#status-data").append(result);
    if(submissionType == 'SHORTLIST'){
    	requirementPipelinePaginationSL(url, requirementId, submissionType);
    }else if(submissionType == 'INTERVIEW'){
    	requirementPipelinePaginationIN(url, requirementId, submissionType);
    }else if(submissionType == 'OFFERMADE'){
    	requirementPipelinePaginationOffer(url, requirementId, submissionType);
    }else if(submissionType == 'REJECTED'){
    	requirementPipelinePaginationRejected(url, requirementId, submissionType);
    }else if(submissionType == 'BACKOUT'){
    	requirementPipelinePaginationBackOut(url, requirementId, submissionType);
    }else if(submissionType == 'SUBMIT_TO_CLIENT'){
    	requirementPipelinePaginationSC(url, requirementId, submissionType);
    }else if(submissionType == 'JOINED'){
    	requirementPipelinePaginationJoined(url, requirementId, submissionType);
    }else{
    	requirementPipelinePagination(url, requirementId, submissionType);
    }
    result = result + '       </tbody>';
    result = result + '     </table>';
    result = result + '   </div>';
    result = result + '   </div>';
    prepareAllWorkflowCounts(requirementId);
}

</script>

<script>
function viewOfferDocument(offerID,title){
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	if(offerID != null && offerID != ""){
		window.open(atsUrl+'/viewOfferDocumentDocView/'+title+'?offerID='+offerID,title,"height=600,width=600");
	}  
}
</script>


<!-- <div class='project_inn_mask projectMask-zindex'></div> -->
<div id="popUp"></div>
<div class="project_inn_mask"></div>
<div id="customizePopUp"></div>
<script>
function rejectApplicant(submissionId,requirementId,submissionType){
	//reject refresh
	$("#reasonForApplicantDiv").empty();
    $("#reasonForReject").val("");
    $("#designationType").val('');
    //$("#rejectedCategory").val("");
    $("#rejectedCategory").selectpicker('refresh');
    
    //backOut refresh
    $("#backOutForApplicantDiv").empty();
    $("#backOutForReject").val("");
    //$("#rejectedCategory").val("");
    $("#backOutCategory").selectpicker('refresh');
    var subId = submissionId;
    var reqId = requirementId;
    var subType = submissionType;
    var selectBox = $('#rejectApplicantSelectspocState'+submissionId).val();
     if(selectBox == "BACKOUT"){
        var result = "";
        //result = result +"    <div class='project_inn_mask'></div>";
        result = result +"       <div id='backOutApplicant"+submissionId+"' class='remove_user_pop remove_user_pop_a'>";
        result = result +"<form id='backOut_form' name='backOut_form'>";
        result = result +"    <div class='col-md-12 inputOther mt10 inputOther mb'>";
        result = result +"    <div class='dropdown-select' id='backOutCategorytDiv'>";
        result = result +"<select id='backOutCategory' name='backOutCategory' class='form-control' onchange='getBackOutCategory()' data-validation='required' field-name='Category' >";
        result = result +"<option selected value=''>--Select Category--</option>";
        result = result +"<option value='Current Company'>Current Company</option>";
        result = result +"<option value='Compensation'>Compensation</option>";
        result = result +"<option value='Better Offer'>Better Offer</option>";
        result = result +"<option value='Cold Feet'>Cold Feet</option>";
        result = result +"<option value='Personal'>Personal</option>";
        result = result +"<option value='Other'>Other</option>";
        result = result +"</select>";
        result = result +"    </div>";
        
        result = result +" <div id='backOutOtherCategoryDiv' style='display: none' class='mt10'>";
        result = result +" <input type='text' id='backOutCategoryOther' name='backOutCategoryOther' class='form-control' data-validation-optional='true' field-name='Other Type'>";
        result = result +" </div>";
        
        //result = result +"        <p>Applicant reject message here</p>";
        result = result +"    <div class='clearfix'>";
        result = result +"        <textarea class='form-control' id='reasonForbackOut' name='reasonForbackOut'  placeholder='Comments here' data-validation='required validate_Space validate_length length1-500' field-name='Reason'   onkeypress='reqRemoveSpacesForBackOutReason();'></textarea>";
        result = result +"    </div>";
        result = result +" </div>";
        result = result + ' <div id="backOutForApplicantDiv"/>';
        result = result +"        <input id='saveBackOut' type='button' class='btn' value='Submit' onclick='backOutApplicantSave("+submissionId+","+requirementId+",\""+submissionType+"\");'><input class='u_can_color' type='button' id='saveReasonCancel' value='Cancel' onClick='cancelBackOutApplicant("+submissionId+","+requirementId+",\""+submissionType+"\");'>";
        result = result +"</form>";
        result = result +"    </div>";
        $("#popUp").append(result);
        $("#backOutApplicant"+submissionId).slideDown(200);
        $(".project_inn_mask").fadeIn(200);
    }else if(selectBox == "REJECTED"){
        var result = "";
        //result = result +"    <div class='project_inn_mask'></div>";
        result = result +"       <div id='rejectApplicant"+submissionId+"' class='remove_user_pop remove_user_pop_a'>";
        result = result +"<form id='rejected_form' name='rejected_form'>";
        result = result +"    <div class='col-md-12 inputOther mt10 inputOther mb'>";
        result = result +"    <div class='dropdown-select' id='rejectedCategorytDiv'>";
        result = result +"<select id='rejectedCategory' name='rejectedCategory' class='form-control' onchange='getRejectedCategory()' data-validation='required' field-name='Category' >";
        result = result +"<option selected value=''>--Select Category--</option>";
        result = result +"<option value='Wrong Skill Sets'>Wrong Skill Sets</option>";
        result = result +"<option value='Unsuitable Personality'>Unsuitable Personality</option>";
        result = result +"<option value='Bad Fit'>Bad Fit</option>";
        result = result +"<option value='Salary'>Salary</option>";
        result = result +"<option value='Interview Rejection'>Interview Rejection</option>";
        result = result +"<option value='Other'>Other</option>";
        result = result +"</select>";
        result = result +"    </div>";
        
        result = result +" <div id='rejectOtherCategoryDiv' style='display: none' class='mt10'>";
        result = result +" <input type='text' id='rejectedCategoryOther' name='rejectedCategoryOther' class='form-control' data-validation-optional='true' field-name='Other Type'>";
        result = result +" </div>";
        
        //result = result +"        <p>Applicant reject message here</p>";
        result = result +"    <div class='clearfix'>";
        result = result +"        <textarea class='form-control' id='reasonForReject' name='reasonForReject'  placeholder='Comments here' data-validation='required validate_Space validate_length length2-100' field-name='Reason'   onkeypress='reqRemoveSpacesForRejectReason();'></textarea>";
        result = result +"    </div>";
        result = result +" </div>";
        result = result + ' <div id="reasonForApplicantDiv"/>';
        result = result +"        <input id='save_Reason' type='button' class='btn' value='Submit' onclick='saveRejectApplicantReason("+subId+","+reqId+",\""+subType+"\");'><input class='u_can_color' type='button' id='saveReasonCancel' value='Cancel' onClick='cancelRejectApplicant("+subId+","+reqId+",\""+subType+"\");'>";
        result = result +"</form>";
        result = result +"    </div>";
        $("#popUp").append(result);
        $("#rejectApplicant"+submissionId).slideDown(200);
        $(".project_inn_mask").fadeIn(200);
    }else if(selectBox == "JOINED"){
    	$.ajax({
    	      type : "POST",
    	      url: atsUrl+"/getRequirementOfferSubmissionById?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId, 
    	    	success :function(data) { 
    	    		if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		var obj = data.object;
    	            	var result = "";
    	                result = result +"<div id='joinedApplicant"+submissionId+"' class='remove_user_pop remove_user_pop_a'>";
    	                result = result +"<form id='joinedApplicant_form' name='joinedApplicant_form'>";
    	                result = result +"	<div class='col-md-12 mt10 mb'>";
    	                result = result +"    <div class='clearfix inputfiledColor'>";
    	                if(obj != null && obj != 'undefined' && obj != ""){
    	                	result = result +"        <input class='width100percnt form-control input-sm Datepicker calendar' placeholder='Joined Date' name='joinDate' id='joinDate"+submissionId+"'  readonly='readonly' value='"+obj.updatedDateStr+"' type='text' field-name='Join Date'>";
    	                }else{
    	                	result = result +"        <input class='width100percnt form-control input-sm Datepicker calendar' placeholder='Joined Date' name='joinDate' id='joinDate"+submissionId+"'  readonly='readonly' value='' type='text' field-name='Join Date'>";
    	                }
    	                result = result +"    </div>";
    	                result = result +"    <div id='joinDateErrorDiv'></div>";
    	                result = result +"	</div>";
    	                result = result +" <input id='saveJoinedRequest' type='button' class='btn' value='Submit' onclick='saveJoinedRequestForApplicant("+submissionId+","+requirementId+",\""+submissionType+"\");'><input class='u_can_color' type='button' id='joinedRequestCancel' value='Cancel' onClick='cancelJoinedRequest("+subId+","+reqId+",\""+subType+"\");'>";
    	                result = result +"</form>";
    	                result = result +"</div>";
    	                $("#popUp").append(result);
    	                $("#joinedApplicant"+submissionId).slideDown(200);
    	                $(".project_inn_mask").fadeIn(200);
    	                var locationDate ='${SourceLeadWeb_dateFormat}'; 
    	            	  var jsDateFormat= '';
    	            	  if(locationDate.match("yyyy-MM-dd")){
    	            		  jsDateFormat = 'yy-mm-dd';
    	            	  }if(locationDate.match("dd-MM-yyyy")){
    	            		  jsDateFormat = 'dd-mm-yy'; 
    	            	  }if(locationDate.match("MM-dd-yyyy")){
    	            		  jsDateFormat = 'mm-dd-yy'; 
    	            	  }if(locationDate.match("yyyy-dd-MM")){
    	            		  jsDateFormat = 'yy-dd-mm'; 
    	            	  }
    	            	  $('#joinDate'+submissionId).datepicker({
    	            	      dateFormat: jsDateFormat,
    	            	      changeMonth: true,
    	            	  	  changeYear: true,
    	            	  });
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
  				},
    		  });
    }else if(selectBox == "SelectedOnInterview"){
    	$("tr").find("td.sorting_1").removeClass("sorting_1");
    	$("#job_"+submissionId).addClass("row-success");
    	
    	$.ajax({
  	      type : "POST",
  	      url: atsUrl+"/saveSelectedOnInterviewStatusForApplicant?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId, 
  	    	success :function(data) {
  	    		if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
		    		$("tr").removeClass("row-success");
		    	}else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
  	    	},
		  });
    	
    }else if(selectBox == "SHORTLIST"){
    	$.ajax({
    	      type : "POST",
    	      url: atsUrl+"/saveShortlistStatusForApplicant?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId, 
    	    	success :function(data) {
    	    		if (data.response === "AWKWARD") {
  		    		location.href = "/errorPage";
  		    	} else if (data.response === "SUCCESS") {
  		    		$("tr").removeClass("row-success");
  		    		 requirementPipeline(requirementId,submissionType);
  		    	}else if (data.indexOf('Sign In') !== -1) {
  		    		location.replace(sourceleadUrl);
  		    	} else {
  		    		showError("Operation failed, please try again");
  		    	}
    	    	},
  		  });
    }
   
    // below line commented for saving any stage for dynamic workflow name. updated by SRINIVAS SABBU.
   // else if(selectBox == "On Hold" || selectBox == "Status" || selectBox == "Interview" || selectBox == "Selected on Interview" || selectBox == "Offer Made" || selectBox == "NoResponse"){
	 else if(selectBox){ // skip stage name check for dynamic workflow stages.
		 $.ajax({
    	      type : "POST",
    	      url: atsUrl+"/saveOnHoldStatusForApplicant?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&status="+selectBox, 
    	    	success :function(data) {
    	    		if (data.response === "AWKWARD") {
  		    		location.href = "/errorPage";
  		    	} else if (data.response === "SUCCESS") {
  		    		$("tr").removeClass("row-success");
  		    		// below funtion is used to check whether the questinniare is assigned to stage or not.
  		    		 getQuestionnaireIdAssignedToStage(selectBox, submissionId);  
  		    		 requirementPipeline(requirementId,submissionType);
  		    	}else if (data.indexOf('Sign In') !== -1) {
  		    		location.replace(sourceleadUrl);
  		    	} else {
  		    		showError("Operation failed, please try again");
  		    	}
    	    	},
  		  });
    }  
	 // Here need to test any questionnaire is assigned to stage if the initiator confirms need to send the questionnaire to canditate.
   // getQuestionnaireIdAssignedToStage(selectBox, submissionId);
}
 function getQuestionnaireIdAssignedToStage(stageName, submissionId){
	 var workflowId ='${workflowId}';
	 $.ajax({
	      type : "GET",
	      url: sourceleadUrl+"/getQuestionnaireAssignedToStage?workflowId="+workflowId+"&stageName="+stageName, 
	    	success :function(responseData) {
	    		if (responseData==null) {
	    			return;
		    	} else if (responseData.questionnaireId ) {
		    		$("tr").removeClass("row-success");
		    		var result = "";
		    		$("#popUp").empty();
					result = result +" <div class='project_inn_mask'></div>"; 
					result = result +" <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		    		result = result +"<div class='padding15 textBold'> Do you want to assign "+responseData.questionnaireName;
		    		if(responseData.tasksCount!= null && responseData.tasksCount!='0' ){
		    			result = result+" and "+responseData.tasksCount +" task(s) ";
		    		}
		    		result =result+"for the "+responseData.stageName +"</div>";
		    		result = result +' <b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="sendEmailQuestionaireAndCopyTasksToCandidate(\''+responseData.questionnaireId+'\',\''+submissionId+'\',\''+responseData.stageName+'\', \''+responseData.stageId+'\', \''+responseData.questionnaireUuid+'\' , \''+responseData.stageUuid+'\');"><input class="u_can_color" type="button" value="Cancel" onClick="cancelTermination();"></b>';
		    		result = result +" </div>";
					$("#popUp").append(result);
					$("#popUp").show();
					$("#remove_user_pop").slideDown(200);
					$(".project_inn_mask").fadeIn(200);
		    		
		    	}else if (questionaireId.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
	    	},
		  });
    
 }
 function cancelTermination(){
	 $("#popUp").empty();
     $(".project_inn_mask").fadeOut(200);
     $("#popUp").hide();
}    
</script>

<script>
function showSuccessMsg(msg){
	$('#selector11').show();
	if(msg == 'Failed'){
		msg= "Mail Sent Failed."
		$('#selector11').css("background-color","#a21e23");
	}else if(msg == 'Success'){
		msg= "Mail Sent Successfully."
	}
	$('#msgDiv').text(msg)
	setTimeout(function(){ $('#selector11').hide();$('#msgDiv').text("") }, 1000);
}

   function sendEmailQuestionaireAndCopyTasksToCandidate(questionnaireId, submissionId, stageName, stageId, questionnaireUuid, stageUuid) {
	   $("#popUp").hide(); $(".project_inn_mask").fadeOut(200);
	  var jobTitle =  $('#id1').text().trim(); // for the rquirement name .
	   $.ajax({
		      type : "GET",
		      url: sourceleadUrl+"/sendEmailQuestionniareAndCopyTasks?quesionnaireId="+questionnaireId+"&submissionId="
		    		            +submissionId+"&stageName="+stageName+"&stageId="+stageId+"&questionnaireUuid="+questionnaireUuid+"&jobTitle="+jobTitle+"&stageUuid="+stageUuid, 
		    	success :function(respone) {
		    		showSuccessMsg(respone);
		    	  }
			  });
   }
   
</script>
<script>
   function reqRemoveSpacesForRejectReason() {
	    var output = document.getElementById("reasonForReject").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#reasonForReject").removeAttr("data-validation");
	    }
	}
</script>

<script>
   function reqRemoveSpacesForBackOutReason() {
	    var output = document.getElementById("reasonForbackOut").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#reasonForbackOut").removeAttr("data-validation");
	    }
	}
</script>

<script>
function getRejectedCategory(){
	var rejectedCategory = document.getElementById("rejectedCategory").value;
	if(rejectedCategory == 'Other'){
		 document.getElementById("rejectOtherCategoryDiv").style.display="block";
	    $("#rejectedCategoryOther").removeAttr("data-validation-optional");
		$('#rejectedCategoryOther').attr("data-validation","required validate_Space validate_length length2-100 "); 
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}else{
		$("#rejectedCategoryOther").attr("data-validation-optional","true");
		$("#rejectedCategoryOther").removeAttr("data-validation");
		$("#rejectedCategoryOther").val('');
		document.getElementById("rejectOtherCategoryDiv").style.display="none";
		$('#rejectOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}
}
</script>

<script>
function getBackOutCategory(){
	var backOutCategory = document.getElementById("backOutCategory").value;
	if(backOutCategory == 'Other'){
		 document.getElementById("backOutOtherCategoryDiv").style.display="block";
	    $("#backOutCategoryOther").removeAttr("data-validation-optional");
		$('#backOutCategoryOther').attr("data-validation","required validate_Space validate_length length2-100 "); 
		$('#backOutOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}else{
		$("#backOutCategoryOther").attr("data-validation-optional","true");
		$("#backOutCategoryOther").removeAttr("data-validation");
		$("#backOutCategoryOther").val('');
		document.getElementById("backOutOtherCategoryDiv").style.display="none";
		$('#backOutOtherCategoryDiv').find('p.jquery_form_error_message').remove(); 
	}
}
</script>

<script type="text/javascript">
function cancelRejectApplicant(submissionId,requirementId,submissionType){ 
	//script for reject
	$("#rejectApplicant"+submissionId).fadeOut(200);    
	$(".project_inn_mask").fadeOut(200);
	 $("#rejectedCategorytDiv").find('p.jquery_form_error_message').remove();
	 document.getElementById("rejectOtherCategoryDiv").style.display="none";
	 $("#rejectedCategory").val("");
	 
	 if(document.getElementById("backOutOtherCategoryDiv")!==null){
		 //script for backOut
		 $("#backOutApplicant"+submissionId).fadeOut(200);    
		 $("#backOutCategorytDiv").find('p.jquery_form_error_message').remove();
		 document.getElementById("backOutOtherCategoryDiv").style.display="none";
		 $("#backOutCategory").val("");
	 }
	pipelineBySubmissionType(requirementId,submissionType);
};

function cancelBackOutApplicant(submissionId,requirementId,submissionType){ 
	 //script for backOut
	 $("#backOutApplicant"+submissionId).fadeOut(200);
	 $(".project_inn_mask").fadeOut(200);
	 $("#backOutCategorytDiv").find('p.jquery_form_error_message').remove();
	 document.getElementById("backOutOtherCategoryDiv").style.display="none";
	 $("#backOutCategory").val("");
	 pipelineBySubmissionType(requirementId,submissionType);
};

function cancelJoinedRequest(submissionId,requirementId,submissionType){ 
	 $("#joinedApplicant"+submissionId).fadeOut(200);
	 $(".project_inn_mask").fadeOut(200);
	 $("#joinDate"+submissionId+"").val("");
	 pipelineBySubmissionType(requirementId,submissionType);
};

function pipelineBySubmissionType(requirementId,submissionType){
	if(submissionType == 'all'){
		requirementPipeline(requirementId,submissionType);
	}else if(submissionType == 'DUPLICATE_APPLICANTS'){
		requirementPipelineDA(requirementId,submissionType);
	}else if(submissionType == 'REQUEST_SENT'){
		requirementPipelineRS(requirementId,submissionType);
	}else if(submissionType == 'SHORTLIST'){
		requirementPipelineSL(requirementId,submissionType);
	}else if(submissionType == 'INTERVIEW'){
		requirementPipelineIN(requirementId,submissionType);
	}else if(submissionType == 'OFFERMADE'){
		requirementPipelineOffer(requirementId,submissionType);
	}else if(submissionType == 'REJECTED'){
		requirementPipelineRejected(requirementId,submissionType);
	}else if(submissionType == 'SUBMIT_TO_CLIENT'){
		requirementPipelineSC(requirementId,submissionType);
	}else if(submissionType == 'BACKOUT'){
		requirementPipelineBackout(requirementId,submissionType);
	}else if(submissionType == 'JOINED'){
		requirementPipelineJoined(requirementId,submissionType);
	}
}
</script>


<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };
</script> 
<script>
function saveRejectApplicantReason(submissionId,requirementId,submissionType){
	if ($('#rejected_form').validate(false, validationSettings)) {
		
		var formData =  getFormData('rejected_form'); 
		$('#save_Reason').attr("disabled","disabled");
		$.ajax({
			type : "POST",
    	    data: formData,
		    url: atsUrl+"/rejectApplicant?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId,	    	   
		    success: function(result) {
		    	$("#popUp").empty();
		    	$(".project_inn_mask").fadeOut(200);
		    	if(result.response === "FAILED") {
					showError("Invalid data");
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
					requirementPipeline(requirementId,submissionType);
				}else if (result.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
		    }
	   });
		return true;
	}else{
		return false;
	}
}
$('body').on('blur', '#rejected_form', function() {
	$("#rejected_form").showHelpOnFocus();
	$("#rejected_form").validateOnBlur(false, validationSettings);
});

function backOutApplicantSave(submissionId,requirementId,submissionType){
	if ($('#backOut_form').validate(false, validationSettings)) {
		var formData =  getFormData('backOut_form'); 
		$("#saveBackOut").attr("disabled", true);
		$.ajax({
			type : "POST",
    	    data: formData,
		    url: atsUrl+"/backoutApplicant?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId,	    	   
		    success: function(data) {
		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
   			    requirementPipeline(requirementId,submissionType);
		    }
	   });
		return true;
	}else{
		return false;
	}
}
$('body').on('blur', '#backOut_form', function() {
	$("#backOut_form").showHelpOnFocus();
	$("#backOut_form").validateOnBlur(false, validationSettings);
});

function saveJoinedRequestForApplicant(submissionId,requirementId,submissionType){
	if ($('#joinedApplicant_form').validate(false, validationSettings)) {
		if($('#joinDate'+submissionId+'').val() != null && $('#joinDate'+submissionId+'').val() != ''){
		var formData =  getFormData('joinedApplicant_form'); 
		$("#saveBackOut").attr("disabled", true);
		$.ajax({
			type : "POST",
    	    data: formData,
		    url: atsUrl+"/saveJoinedRequestForApplicant?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId,	    	   
		    success: function(data) {
		    	if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
		    		$("#popUp").empty();
	   			 	$(".project_inn_mask").fadeOut(200);
	   			    requirementPipeline(requirementId,submissionType);
		    	}else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
		    }
	   });
		return true;
	}else{
		$('#joinDateErrorDiv').append('<p class="jquery_form_error_message">Join Date is required</p>');
		return false;
	}
		
	}else{
		return false;
	}
}
$('body').on('blur', '#joinedApplicant_form', function() {
	$("#joinedApplicant_form").showHelpOnFocus();
	$("#joinedApplicant_form").validateOnBlur(false, validationSettings);
});
</script>



<!-- <script>
function requirementSubmissionCount(requirementId){  
	
	 $.ajax({
        type : "POST",
         url: atsUrl+"/getSubmissionsCountBySubmissionStatus?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId,        
         success :function(data) { 
        	 var json = JSON.parse(data);
        	 var report1 = json[0];
        	 
        	 //$("#applicantsCount").html(report1.numOfSubmissions);
        	 $("#shortlistCount").html(report1.numOfShortList);
        	 $("#submitToClientCount").html(report1.numOfSubmitToClient);
        	 $("#interviewCount").html(report1.numOfInterview);
        	 $("#offersCount").html(report1.numOfOffers);
        	 $("#joinedCount").html(report1.numOfOffers);
        	 $("#rejectedCount").html(report1.numOfRejected);
        	 $("#requestSent").html(report1.numofrequestsent);
        	 //$("#duplicateApplicantsCount").html(report1.duplicateCount);
         },
	  });
}
</script> -->

<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
function assignedMemberList(requirementId){
	var url = atsUrl+"/getRequirementAssignedListByRequirementId?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId;
	
		var result = '';
		result = result +'     <div class="clearfix mt5 mb project_wrap_table datatable_filter dataTablesPaginate">';
		  /* result = result + " <div class='remove_search searchCustom'>";
			result = result
					+ " <input type='text' placeholder='Search' id='assignedToDataTableSearch'/>";
			result = result + "</div>"; */
	    result = result +'         <table id="requirementAssignedToPagination" class="clearfix mb">';
	    result = result +'         <thead>';
	    result = result +'         <tr>';
	    result = result +'         <th>Name</th>';
	    result = result +'         <th>Role</th>';
	    result = result +'         <th>Team</th>';
	    result = result +'         <th>No. of Applicants</th>';
	    result = result +'         </tr>';
	    result = result +'         </thead>';
	    result = result +'         <tbody>';
	    $("#assignedMemberList").empty();
		$("#assignedMemberList").append(result);
		requirementAssignedMembersList(url, requirementId);
}
</script>

<script>
function requirementNotes(requirementId){
		var result = "";
		result = result  +'<div class="clearfix timesheetHead">';
		result = result  +'    <div class="project_tabs_inn_head addUser-org"> '; 
		result  = result + '<div class="success_pop" id="clientNotesSelector" style="display: none;">';
	    result  = result + '<i class="fa fa-check-circle"></i><span id="clientNotesMsgDiv"></span>';
	    result  = result + '</div>';
		result = result + '<ul class="padding_right">';
		result = result  +'    	<a href="javascript:void(0)" onClick="addReqNotes('+requirementId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Note</a>                                                            ';
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result = result  +'  <div class="clearfix"></div> ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="clientNotesListDiv" ></div>';
		result  = result + '   </div>'; 
		result = result+'	    </div>';
		result = result+'	    </div>'; 
	    //$("#tab-1").empty();
	    $("#tab-2").empty(); 
	    $("#tab-3").empty();  
	    $("#tab-4").empty();
	    $("#status-div").empty(); 
	    $("#actionsDiv").hide(); 
	    $("#status-data").empty(); 
	    $("#tab-4").append(result);  
	    reqNotes(requirementId);    
}
</script>

<script>
function addReqNotes(requirementId) {	
	var result = "";
	result = result  +'          <form role="form"  id="clinetNotes_form" name="clinetNotes_form" method="post" > ';
	result = result  +'          <input type="hidden" id="requirementId" name="requirementId" value="'+requirementId+'"> ';
	result = result  +'          <input type="hidden" id="noteType" name="noteType" value="requirement"> ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closeDocPopup();"></i> ';
	
	result = result  +'                <div class="row clearfix"> ';
	result = result+'	                  <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Note" value="" id="noteDescription" name="noteDescription" data-validation="required validate_length length1-5000"  field-name="Note" class="form-control" ></textarea>';
	result = result+'	                  </div>';
	result = result+' <div class="col-md-9 checkBox"> ';
	result = result+'   <input type="checkbox" class="pull-left" value= "Y" id="allowAll" name = "allowAll" checked="checked">';
	result = result + '	                    <p class="pull-left mt5">Allow all users</p>';
	result = result  +'                  </div>';     
	
	result = result  +'                  <div class="clearfix mt5" id="docAddButton"> ';
	result = result  +'                  <div class="pull-right mt5"> ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_note" class="btn btn-sm btn-primary mr" onClick ="storeReqNotes('+requirementId+');" />'; 
	result = result  +'                  </div> ';
	result = result  +'                  </div> ';
	result = result  +'                </div>';
	
	result = result  +'                </form>';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
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
	function educationStartDate(){
	}
	
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){ 
		 $(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details, .ui-datepicker-prev, .ui-datepicker-next").click(function(e){
		 e.stopPropagation();
	 });

	}
</script>
<script>
function closeDocPopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>
<script>
function reqNotes(requirementId){
	var urlForm = "";
	    urlForm = "/getRequirementNotes?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId;
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
		 success :function(result) {
			 var json = result.data;
	      	   if(result.response === "SESSION_TIMEDOUT") {
						location.replace(sourceleadUrl);
					} else if (result.response === "AWKWARD") {
						location.href = "/errorPage";
					} else if (result.response === "SUCCESS") {
						var output = "";
					    if(json != null && json != 'undefined' && json != "" && json != '[]'){
					    output  = output + '   <div class="project_tabs_inn_head addUser-org"> ';
					    output  = output + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
				     	for(var i=0;i<json.length;i++){
				       	var report = json[i];
				    	if(report.checkBox == 'Y' || (report.checkBox == 'N' && '${SourceLeadWeb_userId}' == report.userIdStr)){
				   	  	output = output+'<div class="comments-div comments-inner commentsNotes clearfix">';
				   	    output = output+'    <div class="commentsimg">';
				   	    output = output+'    <i class="fa fa-comments"></i>';
				   	    output = output+'        <div class="commentsimg-inner">';
				   	    if(report.commentAuthorImage != null && report.commentAuthorImage !='' && report.commentAuthorImage !='undefined'){
				   	    output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="'+report.commentAuthorImage+'"/>';
				   	    }else{
				   	   	output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/userprofileImg.jpg"/>';
				   	    }
				   	    output = output+'        </div>';
				   	    output = output+'    </div>';
				   	    output = output+'     <div class="comments-content">';
				   	    output = output+'         <div class="commentsNotes_inner">';
				   	    output = output+'         <p class="comment-by">';
				   	    output = output+'            By <span class="text-danger" title = "'+report.userName+'">'+report.userName+'</span>';
				   	    if(report.requirementTitle !=null && report.requirementTitle != 'undefined'){
				   		 output = output+'          Job: <span class="text-danger" title = "'+report.requirementTitle+'"> '+report.requirementTitle+'</span>';
				   	    }
				   	    
				   	    else if(report.contactName !=null && report.contactName != 'undefined'){
				   	    output = output+'          Contact Name: <span class="text-danger" title = "'+report.contactName+'">'+report.contactName+'</span>';
				   	    }
				   	 	else if(report.contract !=null && report.contract != 'undefined'){
				   		output = output+'         Contract:  <span class="text-danger" title = "'+report.contract+'">'+report.contract+'</span>';
				   	    }
				   	    
				   	    else if(report.invoiceId !=null && report.invoiceId != 'undefined'){
				   		output = output+'         Invoice Id:  <span class="text-danger" title = "'+report.invoiceId+'">'+report.invoiceId+'</span>';
				   	    }
				   	    output = output+'            <span class="small pull-right">'+report.createdDateString+'</span></p>';
				   	    output = output+'         <p class="comment_text pull-left">'+report.comment+'</p>';
				   	    output = output+'         </div>';
				   	    output = output+'     </div>';
				   	    output = output+'</div>';
				    	}
				     }
					}else{
				     var output ="";
				     output = output+"   <div class='dataTables_empty'>"; 
					 output = output+"  </div> ";
					}
					output  = output + '              </div>';
					output  = output + '        </div>';
					
					$("#clientNotesListDiv").empty(); 
				    $("#clientNotesListDiv").append(output);
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
	function storeReqNotes(requirementId){
		if ($('#clinetNotes_form').validate(false, validationSettings)){
				var frm = $('#clinetNotes_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				//con = con.replace(/[{}]/g, "");
				var form = $('#clinetNotes_form')[0];
	      		var formData = new FormData(form);
	      		formData.append("json",con);
	      		
	      		var userId = '${SourceLeadWeb_userId}';
	      		formData.append("userId",userId);
	      	    var locationCode ='${SourceLeadWeb_locationCode}'; 
	      	    formData.append("locationCode",locationCode);
	      	    
				
				$("#save_note").attr("disabled", "disabled");
         		$("#save_note" ).addClass('button').val('Processing..');
		        var jsonData = encodeURIComponent(con);
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeNotes?"+tokenParamName+"="+tokenValue+"",
						success : function(result) {
							closeDocPopup();
							if(result.response === "FAILED") {
								showError("Invalid data");
							} else if (result.response === "AWKWARD") {
								location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
								reqNotes(requirementId);
							}else if (result.indexOf('Sign In') !== -1) {
					    		location.replace(sourceleadUrl);
					    	} else {
					    		showError("Operation failed, please try again");
					    	}
						},
					});
					$('.add-comment').toggle(200);
					return true;
		}else {
			return false;	
		}
		}
		$('body').on('blur', '#clinetNotes_form', function() {
		$("#clinetNotes_form").showHelpOnFocus();
		$("#clinetNotes_form").validateOnBlur(false, validationSettings);
		
	});
</script>

<script>
function getActions(){
	$(".action-pop").toggle();
 	$(".export_pipline").hide();
}
function popClose(){
	$(".action-pop").hide();
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
 	});	
}
</script>

<script>
var candidateStatusGlobal ="";
function clientSideBar(candidateId,recScore){
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	  $.ajax({
          type : "POST",
           url: atsUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
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
							result = result+'   <img  id="userImage" data-id="profileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/userprofileImg.jpg" alt="Profile Image" class=" img-responsive center-block img-circle">';						 
		   
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
							/*  result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif are not support</p>';
							 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Size should not more than 200kb</p>';
							 */ 
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
				             result = result+'   <div class="project_side_wrap_inn_tabs ">';
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
	                }else{
	               	 $("#candidateSideDiv").removeAttr("style");
	 	    	         var result ="";
			    	     result = result+"   <div class='row'>"; 
						 result = result+"	<div class='col-lg-12'>"; 
						 result = result+"		<div class='content-area clearfix'>"; 
						 result = result+"			<div class='blank-pages'>";
						 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
						 result = result+"			        <p>No Candidates Found</p>";
						 result = result+"			 </div>";
						 result = result+"		</div>"; 
						 result = result+"	</div>"; 
						 result = result+"  </div> "; 
						$("#candidateSideDiv").empty();
					    $("#candidateSideDiv").append(result); 
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
function requirementWorkflowStatusBar(requirementId){
	  var result = "";
	    var all = 'all';
	    var requestSent ='REQUEST_SENT'; //fixed codes workflow stages not providing editable to user.
	    var duplicateApplicantCount = 'DUPLICATE_APPLICANTS';
	    var shortList = 'SHORTLIST'; 
	    var submission='SUBMISSION';
	    var interview = 'INTERVIEW';
	    var offerMade = 'OFFERMADE'; 
	    var joined = 'JOINED';
	    var rejected = 'REJECTED';
	    var backout = 'BACKOUT';
	    var onhold = 'ON_HOLD';
        var submitToClient = 'SUBMIT_TO_CLIENT';
        var clientSubmissions = '${clientSubmissions}';
        var isSpocActive = '${isSpocActive}';
	       
        result = result + '<div class="reportsCount reportsCountNew reportsCountNewBig reportsCountCustom" id="customLi">';
		result = result + '<ul id = "stageColumns">';
		result = result + '<li title="Applicants" id="applicantli" onclick="requirementPipeline('+requirementId+',\''+all+'\');tabEnable(this);" class="reqAct">';
		result = result + '	<span><i class="fa fa-users"></i></span><b id="applicantsCount" shortCode="APPLICANTS">0</b>';
		result = result + '<div class="clearfix small mt10"><label shortCode="applicants" class="mb0 fontSmall tooltip-pipeline">Applicants</label></div>';
		result = result + '</li>';
		result = result + '<li title="Duplicate Applicants" class="" id="duplicataplctli" onclick="requirementPipeline('+requirementId+',\''+duplicateApplicantCount+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-files-o"></i></span><b id="duplicateApplicantsCount" >0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Duplicate Applicants</label></div>';
		result = result + '</li>';
		result = result + '<li title="Request Sent" id="requestSentli" onclick="requirementPipeline('+requirementId+',\''+requestSent+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-check"></i></span><b id="requestSent" shortCode="REQUEST_SENT">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Request Sent</label></div>';
		result = result + '</li>';
		// Start here getting dynamically configured stages for the workflow. 
		 <c:forEach var="stage" items="${stagesList}">
			<c:choose>
				 <c:when test = "${stage.stageShortCode =='SUBMISSION'}">
					result = result + '<li title="${stage.stageName}" id="Submission" onclick="requirementPipeline('+requirementId+',\''+submission+'\');tabEnable(this);">';
					result = result + '	<span><i class="fa fa-check-square-o"></i></span><b id="submissionsCount" shortCode="${stage.stageShortCode}">0</b>';
					result = result + '<div class="clearfix fontSmall mt10"><label  class="mb0 small">${stage.stageName}</label></div>';
					result = result + '</li>';
				</c:when>
				<c:when test = "${stage.stageShortCode =='SHORTLIST'}">
				    result = result + '<li title="Shortlisted" class="" id="shortListli" onclick="requirementPipeline('+requirementId+',\''+shortList+'\');tabEnable(this);">';
				    result = result + '	<span><i class="fa fa-list-ul"></i></span><b id="shortlistCount" shortCode="${stage.stageShortCode}" >0</b>';
				    result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Shortlisted</label></div>';
				    result = result + '</li>';
				 </c:when>
				  <c:when test = "${stage.stageShortCode =='INTERVIEW'}">
				    result = result + '<li title="Interviews" class="" id="interviewli" onclick="requirementPipeline('+requirementId+',\''+interview+'\');tabEnable(this);">';
					result = result + '	<span><i class="fa fa-handshake-o"></i></span><b id="interviewCount" shortCode="${stage.stageShortCode}">0</b>';
					result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Interviews</label></div>';
					result = result + '</li>';
				 </c:when>
			   <c:when test = "${stage.stageShortCode =='OFFERMADE'}">
				    result = result + '<li title="Offers" id="offerMadeli" onclick="requirementPipeline('+requirementId+',\''+offerMade+'\');tabEnable(this);">';
					result = result + '	<span><i class="fa fa-envelope-open-o"></i></span><b id="offersCount" shortCode="${stage.stageShortCode}" >0</b>';
					result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Offers</label></div>';
					result = result + '</li>';
				</c:when>
			   <c:when test = "${stage.stageShortCode =='JOINED'}">
					result = result + '<li title="Joined" id="joinedli" onclick="requirementPipeline('+requirementId+',\''+joined+'\');tabEnable(this);">';
					result = result + '	<span><i class="fa fa-id-badge"></i></span><b id="joinedCount" shortCode="${stage.stageShortCode}">0</b>';
					result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Joined</label></div>';
					result = result + '</li>';
			  </c:when>
			  <c:when test = "${stage.stageShortCode =='REJECTED'}">
			 	result = result + '<li title="Rejected" class="" id="rejectedli" onclick="requirementPipeline('+requirementId+',\''+rejected+'\');tabEnable(this);">';
				result = result + '	<span class="rejColor"><i class="fa fa-ban"></i></span><b id="rejectedCount" shortCode="${stage.stageShortCode}">0</b>';
				result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Rejected</label></div>';
				result = result + '</li>';
		     </c:when>
		     <c:when test = "${stage.stageShortCode =='BACKOUT'}">
		        result = result + '<li title="Back-Out" id="backoutli" onclick="requirementPipeline('+requirementId+',\''+backout+'\');tabEnable(this);">';
				result = result + '	<span class="outColor"><i class="fa fa-arrow-circle-left"></i></span><b id="backoutCount" shortCode="${stage.stageShortCode}">0</b>';
				result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Back-Out</label></div>';
				result = result + '</li>';
		     </c:when>
		     <c:when test = "${stage.stageShortCode =='ON_HOLD'}">
		        result = result + '<li title="On-hold" id="fa fa-pause-circle-o" onclick="requirementPipeline('+requirementId+',\''+onhold+'\');tabEnable(this);">';
				result = result + '	<span class="outColor"><i class="fa fa-arrow-circle-left"></i></span><b id="OnholdCount" shortCode="${stage.stageShortCode}">0</b>';
				result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">On-hold</label></div>';
				result = result + '</li>';
		     </c:when>
		     <c:when test = "${stage.stageShortCode =='SUBMIT_TO_CLIENT'}"> 
			 if(clientSubmissions == 'Y' && isSpocActive == 'Y'){
					result = result + '<li title="Submit to Client" id="submitToClientli" onclick="requirementPipeline('+requirementId+',\''+submitToClient+'\');tabEnable(this);">';
					result = result + '	<span class="cliColor"><i class="fa fa-send"></i></span><b id="submitToClientCount" shortCode="${stage.stageShortCode}">0</b>';
					result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Submit to Client</label></div>';
					result = result + '</li>';
			}
		  </c:when>
			  <c:otherwise>
				result = result + '<li title="${stage.stageName}" id="${stage.stageShortCode}" onclick=requirementWorkflowPipeline('+requirementId+',\'${stage.stageShortCode}\');tabEnable(this);>';
				result = result + '	<span><i class="fa fa-sitemap"></i></span><b id="${stage.stageShortCode}Count" shortCode="${stage.stageShortCode}">0</b>';
				result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">${stage.stageName}</label></div>';
				result = result + '</li>';
			  </c:otherwise>
		 </c:choose>
		</c:forEach>
		// End here getting dynamically configured stages for the workflow. 
		result = result + '<div class="clearfix"></div>';
	    result = result + '</ul>';
	    result = result + '</div>';
	    result = result +' <div> <i class="ratepop rate_pop" id="ratepop"></div>';
	    
	    $("#status-div").empty();
	    $("#status-div").prepend(result);
	   
}
function requirementStatusBar(requirementId){
	var result = "";
	    var all = 'all';
	   var requestSent ='REQUEST_SENT';
       var shortList = 'SHORTLIST';
  	   var interview = 'INTERVIEW';
  	   var rejected = 'REJECTED';
  	   var backout = 'BACKOUT';
       var submitToClient = 'SUBMIT_TO_CLIENT';
       var clientSubmissions = '${clientSubmissions}';
       var isSpocActive = '${isSpocActive}';
       var duplicateApplicantCount = 'DUPLICATE_APPLICANTS';
       var offerMade = 'OFFERMADE';
       var joined = 'JOINED';
       
        result = result + '<div class="reportsCount reportsCountNew reportsCountNewBig reportsCountCustom" id="customLi">';
		result = result + '<ul>';
		result = result + '<li title="Applicants" id="applicantli" onclick="requirementPipeline('+requirementId+',\''+all+'\');tabEnable(this);" class="reqAct">';
		result = result + '	<span><i class="fa fa-users"></i></span><b id="applicantsCount">0</b>';
		result = result + '<div class="clearfix small mt10"><label class="mb0 fontSmall tooltip-pipeline">Applicants</label></div>';
		result = result + '</li>';
		result = result + '<li title="Duplicate Applicants" class="" id="duplicataplctli" onclick="requirementPipeline('+requirementId+',\''+duplicateApplicantCount+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-files-o"></i></span><b id="duplicateApplicantsCount">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Duplicate Applicants</label></div>';
		result = result + '</li>';
		result = result + '<li title="Request Sent" id="requestSentli" onclick="requirementPipeline('+requirementId+',\''+requestSent+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-check"></i></span><b id="requestSent">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Request Sent</label></div>';
		result = result + '</li>';
		result = result + '<li title="Shortlisted" class="" id="shortListli" onclick="requirementPipeline('+requirementId+',\''+shortList+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-list-ul"></i></span><b id="shortlistCount">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Shortlisted</label></div>';
		result = result + '</li>';
		if(clientSubmissions == 'Y' && isSpocActive == 'Y'){
		result = result + '<li title="Submit to Client" id="submitToClientli" onclick="requirementPipeline('+requirementId+',\''+submitToClient+'\');tabEnable(this);">';
		result = result + '	<span class="cliColor"><i class="fa fa-send"></i></span><b id="submitToClientCount">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Submit to Client</label></div>';
		result = result + '</li>';
		}
		result = result + '<li title="Interviews" class="" id="interviewli" onclick="requirementPipeline('+requirementId+',\''+interview+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-handshake-o"></i></span><b id="interviewCount">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Interviews</label></div>';
		result = result + '</li>';
		result = result + '<li title="Offers" id="offerMadeli" onclick="requirementPipeline('+requirementId+',\''+offerMade+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-envelope-open-o"></i></span><b id="offersCount">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Offers</label></div>';
		result = result + '</li>';
		result = result + '<li title="Joined" id="joinedli" onclick="requirementPipeline('+requirementId+',\''+joined+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-id-badge"></i></span><b id="joinedCount">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Joined</label></div>';
		result = result + '</li>';
		result = result + '<li title="Rejected" class="" id="rejectedli" onclick="requirementPipeline('+requirementId+',\''+rejected+'\');tabEnable(this);">';
		result = result + '	<span class="rejColor"><i class="fa fa-ban"></i></span><b id="rejectedCount">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Rejected</label></div>';
		result = result + '</li>';
		result = result + '<li title="Back-Out" id="backoutli" onclick="requirementPipeline('+requirementId+',\''+backout+'\');tabEnable(this);">';
		result = result + '	<span class="outColor"><i class="fa fa-arrow-circle-left"></i></span><b id="backoutCount">0</b>';
		result = result + '<div class="clearfix fontSmall mt10"><label class="mb0 small">Back-Out</label></div>';
		result = result + '</li>';
		result = result + '<div class="clearfix"></div>';
	    result = result + '</ul>';
        result = result + '</div>';
        result = result +' <div> <i class="ratepop rate_pop" id="ratepop"></div>';
         
      $("#status-div").empty();
      $("#status-div").prepend(result);
      if(clientSubmissions == 'Y' && isSpocActive == 'Y'){
  		$("#customLi").removeClass("reportsCountCustom");
      }
     // $("#tab-2").prepend(status-div);
}
</script>

<script>
function tabEnable(local,typeId){
	$('.reqAct').each(function() {
		 var localId = this.id;
		 $("#"+localId).removeClass("reqAct");
    });
	if(typeId == null || typeId == '' || typeId == 'undefined'){
		var tabId = local.id;
		$("#"+tabId).addClass("reqAct");
	}
	else{
		$("#"+typeId).addClass("reqAct");
	}
}
</script>



<script>
//piplineexportpop 
function piplineexport(){
	$(".export_pipline").toggle();
	 $(".action-pop").slideUp(200);	
};

function piplineexportClose(){
	$(".export_pipline").hide();
	$(".Clo1, .export_pipline").click(function(e){
	 	e.stopPropagation();
 	});	
}
</script>
<script>
//inline-edit pop
function editFun(){
$('.edit-status').toggle();
}
</script>

<script>
function getRateListDiv(submissionId, candidateId){
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  
	  var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  rateListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  rateListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  rateListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  rateListDate = 'YYYY-DD-MM'; 
	  }  
	  
	  $.ajax({
        type : "POST",
         url: atsUrl+"/getRateHistoryBySubmissionId?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&candidateId="+candidateId+"&orgLocationId="+orgLocationId,        
         success :function(data) {
        	 
        	 if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
        	 
		        	 if(data.data != null && data.data != 'undefined' && data.data != "" && data.data != '[]'){
		            	 var json = data.data;
		          			var expectedRate = json[0].expectedRateRN;
		          			if(expectedRate != null && expectedRate != '' && expectedRate != 'undefined'){
		          				expectedRate = expectedRate;
		          			}else{
		          				expectedRate = ""
		          			}
		          			var expectedRateType =json[0].expectedRateTypeRN;
		          			var toggleER = "";
		          			if(expectedRateType != null && expectedRateType != '' && expectedRateType != 'undefined'){
		          				expectedRateType = expectedRateType;
		          			}else{
		          				expectedRateType ="";
		          				toggleER = 'style="display: none"';
		          			}
		          			var expectedRatePer =json[0].expectedRatePerRN;
		          			if(expectedRatePer != null && expectedRatePer != '' && expectedRatePer != 'undefined'){
		          				expectedRatePer = expectedRatePer;
		          			}else{
		          				expectedRatePer = "";
		          			}
		          			     var result = "";
		      			         result = result + '<i class="fa fa-times text-danger close-ratepop" title="closediv" onClick="closeratediv();"></i>';
		      			         result = result + '<p><strong>Rate Negotiation History </strong> <span class="pull-right paddingRight" '+toggleER+'><strong>Expected Rate</strong>: '+expectedRateType+'&nbsp;'+expectedRate+'&nbsp;'+expectedRatePer+'<span></p>';
		         			     result = result + '<section class="tbl-scroll require_wrap_table">';
			                     result = result + '  <table>';
			                     result = result + '    <thead>';
			                     result = result + '      <tr>';
			                     result = result + '        <th>By</th>';
			                     result = result + '        <th>Rate</th>';
			                     result = result + '        <th>Rate Per Hourly</th>';
			                     result = result + '        <th>Comment</th>';
			                     result = result + '        <th>Date <br>(<small class="custom-small">'+rateListDate+'</small>)</th>';
			                     result = result + '        <th>Action</th>';
			                     result = result + '      </tr>';
			                     result = result + '    </thead>';
			                     result = result + '    <tbody>';
			                     var divState = "";
			                     var isLastNegotiator = "";
			                     for (var i=0; i<json.length; i++)
					   				{
							    	 var report = json[i];
							    	 if(i == json.length-1){
				                    	 divState = report.rateInitiatorType;
				                    	 if(report.rateInitiatorType != "CANDIDATE" && report.initiatorOrgLocationIdStr==orgLocationId && report.createdBy!='' && report.createdBy!='undefined' && report.createdBy!=null){
				                    		 isLastNegotiator = "true";
				                    	 }else{
				                    		 isLastNegotiator = "false";
				                    	 }
			                    	 }
							    	     
								    	 if(report.rateAndType != null && report.rateAndType != 'undefined' && report.rateAndType !=''){
								    		var resultDtata = getRatePerHourly(report.rateAndType);
								    		 rateAndType = report.rateAndType;
								    	 }else{
								    		 rateAndType = "---";
								    	 }
								    	 /*  if(report.rateAndType != null && report.rateAndType != '' && report.rateAndType != 'undefined'){  */
						                     result = result + '      <tr class="redtxt">';
						                     if(report.proposeByName != null && report.proposeByName != 'undefined' && report.proposeByName !=''){
						                    	 result = result + '        <td><div class="tbldata-dotdot-sm">'+report.proposeByName+'</div></td>';
						                     }else{
						                    	 result = result + '        <td><div class="tbldata-dotdot-sm">---</div></td>';
						                     }
						                    
						                     result = result + '        <td><div class="tblminwidth-75">'+rateAndType+'</div></td>';
						                     if(resultDtata != null && resultDtata != 'undefined' && resultDtata !=''){
						                    	 result = result + '        <td><div class="tblminwidth-75">'+resultDtata+'</div></td>';
						                     }else{
						                    	 result = result + '        <td><div class="tblminwidth-75">---</div></td>';
						                     }
						                    
						                     
						                     if(report.rateReason != null && report.rateReason != 'undefined' && report.rateReason !=''){
						                    	 result = result + '        <td><div class="tbldata-dotdot" title="'+report.rateReason+'">'+report.rateReason+'</div></td>';
						                     }else{
						                    	 result = result + '        <td><div class="tbldata-dotdot">---</div></td>';
						                     }
						                     
						                     if(report.createdDateStr != null && report.createdDateStr != 'undefined' && report.createdDateStr !=''){
						                    	 result = result + '        <td><div class="tblminwidth-75">'+report.createdDateStr+'</div></td>';
						                     }else{
						                    	 result = result + '        <td><div class="tblminwidth-75">---</div></td>';
						                     }
						                     result = result + '        <td id="loading">';
						                     
						                     if(i == json.length-1 && report.rateStage == 'NEWRATE' && isLastNegotiator=="false"){
						                    	 var Accepted = 'Accepted';
						                    	 var Rejected = 'Rejected';
						                    	 if(null != report.submissionRateStatus && '' != report.submissionRateStatus && 'undefined' != report.submissionRateStatus && 'REJECTED' != report.submissionRateStatus){
						                    		 if(report.rateAndType != null && report.rateAndType != 'undefined' && report.rateAndType !=''){
						                    			 result = result + '<a href="javascript:void(0)" class="text-primary" title="Accept" onclick="acceptRejectRate('+submissionId+',\''+Accepted+'\');"><i class="fa fa-check"></i></a> <a href="javascript:void(0)" class="text-danger" title="Reject" onclick="acceptRejectRate('+submissionId+',\''+Rejected+'\');"><i class="fa fa-times"></i></a>';	 
						                    		 }else{
						                    			 result = result + '--';
						                    		 }
						                    		 
						                    	 }
						                     	
						                     }else{
						                    	 if(report.rateStage == 'ACCEPTED'){
						                     		result = result + '<b class="text-primary"> Accepted </b>';
						                     	}else if(report.rateStage == 'REJECTED'){
						                     		result = result + '<b class="text-danger"> Rejected </b>';
						                     	}
						                  /*    } */
						                     result = result + '		</td>';
						                     result = result + '      </tr>';
								    	  } 
					   				}
			                     result = result + '    </tbody>';
			                     result = result + '  </table>';
			                     result = result + '</section>';
			                     if(report.submissionStatus != "REJECTED"){
			                    	 /* if(report.isNeedApplicantPermit=="N" || (null != report.submissionRateStatus && '' != report.submissionRateStatus && 'undefined' != report.submissionRateStatus && 'REJECTED' != report.submissionRateStatus)){ */
			                    	 '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
				                     result = result + '<div class="clearfix row formgroup-minheight">';
				                     result = result + '<form name="rate_form" id="rate_form" method="post">';
				                     result = result + '  <div class="form-group col-md-6">';
				                     result = result + '    <input type="text" class="form-control input-sm" placeholder="Rate" id="rateProposal" name="rateProposal" field-name="Rate" data-validation="required  validate_Space validate_float validate_length length1-8">';
				                     result = result + '  </div>';
				                     
				                     result = result + '<div class="form-group col-md-6 select-field">';
				                     result = result + ' <select class="form-control input-sm" id="rateType" name="rateType" field-name="Rate Per" data-validation="required">';
				                     result = result + '	<option value="">Rate Per</option>';
				                     result = result + '    <option>Hourly</option>';
				                     result = result + '    <option>Weekly</option>';
				                     result = result + '    <option>Bi-weekly</option>';
				                     result = result + '    <option>Semi-Monthly</option>';
				                     result = result + '    <option>Monthly</option>';
				                     result = result + '    <option>Bi-Monthly</option>';
				                     result = result + '    <option>Yearly</option>';
				                     result = result + ' </select>';
				                     result = result + ' </div>';
				                     
				                     result = result + '  <div class="form-group col-md-12">';
				                     result = result + '    <textarea rows="2" class="form-control" placeholder="Comment" id="rateComment" name="rateComment" data-validation-optional="true" data-validation="validate_length length2-500" field-name="Reason"></textarea>';
				                     result = result + '  </div>';
				                     result = result + '  <div class="form-group col-md-12">';
				                     result = result + '    <div class="pull-right">';
				                     result = result + '      <input type="button" value="Add" class="btn btn-primary btn-sm mr" id="add_rate" onClick="proposeNewRate('+submissionId+','+candidateId+');">';
				                     result = result + '      <button type="button" value="Cancel" class="btn btn-default btn-sm" onClick="closeratediv();">Cancel</button>';
				                     result = result + '    </div>';
				                     result = result + '  </div>';
				                     result = result + '</form>';
				                     result = result + '</div>';
				                     '</sec:authorize>';
			                    	/*  } */
			                     }
			                     result = result + '</i>';
			                     $("#ratepop").empty();  
			      	 		     $("#ratepop").append(result); 
			      	 		 	 $("#ratepop").fadeIn(); 
		        	 	}else{
		        	 		$("#ratepop").removeAttr("style");
				    	     var result ="";
				    	     result = result + '<i class="fa fa-times text-danger close-ratepop" title="closediv" onClick="closeratediv();"></i>';
				    	     result = result + '<p><strong>Rate Negotiation History</strong></p>';
				    	     result = result+"   <div class='row'>"; 
							 result = result+"	<div class='col-lg-12'>"; 
							 result = result+"		<div class='clearfix'>"; 
							 result = result+"			<div class='blank-data'>";
							 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
							 result = result+"			        <p>No rate history found</p>";
							 result = result+"			 </div>";
							 result = result+"		</div>"; 
							 result = result+"	</div>"; 
							 result = result+"  </div> ";
							 if(report.submissionStatus != "REJECTED"){
							 /* if(null != report.submissionRateStatus && '' != report.submissionRateStatus && 'undefined' != report.submissionRateStatus && 'REJECTED' != report.submissionRateStatus){ */
							 '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';	 
				    	     result = result + '<div class="clearfix row">';
				    	     result = result + '<form name="rate_form" id="rate_form" method="post">';
		                     result = result + '  <div class="form-group col-md-6">';
		                     result = result + '    <input type="text" class="form-control input-sm" placeholder="Rate" id="rateProposal" name="rateProposal" field-name="Rate" data-validation="required">';
		                     result = result + '  </div>';
		                     
		                     result = result + '<div class="form-group col-md-6 select-field">';
		                     result = result + ' <select class="form-control input-sm" id="rateType" name="rateType" field-name="Rate Per" data-validation="required">';
		                     result = result + '	<option value="">Rate Per</option>';
		                     result = result + '    <option>Hourly</option>';
		                     result = result + '    <option>Weekly</option>';
		                     result = result + '    <option>Bi-weekly</option>';
		                     result = result + '    <option>Semi-Monthly</option>';
		                     result = result + '    <option>Monthly</option>';
		                     result = result + '    <option>Bi-Monthly</option>';
		                     result = result + '    <option>Yearly</option>';
		                     result = result + ' </select>';
		                     result = result + ' </div>';
		                     
		                     result = result + '  <div class="form-group col-md-12">';
		                     result = result + '    <textarea rows="2" class="form-control" placeholder="Comment" id="rateComment" name="rateComment" field-name="Reason" data-validation="required"></textarea>';
		                     result = result + '  </div>';
		                     result = result + '  <div class="form-group col-md-12">';
		                     result = result + '    <div class="pull-right">';
		                     result = result + '      <input type="button" value="Add" class="btn btn-primary btn-sm mr" id="add_rate" onClick="proposeNewRate('+submissionId+','+candidateId+');">';
		                     result = result + '      <input type="button" value="Cancel" class="btn btn-default btn-sm" onClick="closeratediv();">';
		                     result = result + '    </div>';
		                     result = result + '  </div>';
		                     result = result + '</form>';
		                     result = result + '</div>';
		                     '</sec:authorize>';
		        	 		 /* } */
							 }
		                     result = result + '</i>';
		                     $("#ratepop").empty();  
			      	 		 $("#ratepop").append(result); 
			      	 		 $("#ratepop").fadeIn(); 
		            	}
		    	}else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
         	},
      	}); 
	};
</script>


<script type="text/javascript">
	function proposeNewRate(submissionId, candidateId) {
		 if ($('#rate_form').validate(false, validationSettings)){
			 var atsUrl = '${SourceLeadWeb_atsUrl}';
			var formData = getFormData("rate_form");
		    $("#add_rate").attr("disabled", "disabled");
			$.ajax({
			type: "POST",
			data :formData,
			url: atsUrl+"/proposeRate?submissionId="+submissionId+"&candidateId="+candidateId,
				success: function(data) {
					if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		$("#ratepop").fadeOut();
			    	}else if (json.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
				},
			});
			  return true;
			 }else{
			  return false;
			 }
	}
	$('body').on('blur', '#rate_form', function() {
		$("#rate_form").showHelpOnFocus();;
		$("#rate_form").validateOnBlur(false, validationSettings);  
	});
</script>

<script type="text/javascript">
	function acceptRejectRate(submissionId,status) {
		$("#loading").empty();
		$("#loading").prepend('<i class="fa fa-cog fa-spin"></i>');
		var atsUrl = '${SourceLeadWeb_atsUrl}';
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
			type: "POST",
			url: atsUrl+"/acceptRejectRate?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId+"&status="+status,
				success: function(data) {
					if (data.response === "FAILED") {
			    		showError(data.responseMessage);
			    	} else if (data.response === "AWKWARD") {
			    		location.href = "/errorPage";
			    	} else if (data.response === "SUCCESS") {
			    		$("#ratepop").fadeOut();
			    	}else if (data.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    		showError("Operation failed, please try again");
			    	}
				},
			});
			  return true;
	}
</script>

<script>
 $('.showRate').click(function(){
	$(this).next().fadeIn(200);
});
function closeratediv(){
  $("#ratepop").fadeOut();
}
</script>
<script>
		// Accept function
	function acceptFun(){
		$('#accpet').fadeIn();
	};
	$('html').click(function(){
		$('#accpet').fadeOut();
	});
	$("#accpet, .acceptreject-pop, .labelsuccess").click(function(e){
	 e.stopPropagation();
 });
</script>
	
<script>
// Add rate div
	function addrate(){
		$("#addrate").toggle();
	}
</script>

<script>
function exportPipelineToExcel(requirementId,submissionType){
	$('.export_pipline').hide();
	 var workflowId ='${workflowId}';  
	  var clientSubmissions = '${clientSubmissions}';
	  var emptypilpeline = $("#emptyPipelinelist").val();
      if(emptypilpeline > 0){
    	  if(submissionType != 'all' && submissionType != 'DUPLICATE_APPLICANTS' && submissionType !='REQUEST_SENT' && submissionType != 'SUBMISSION' && 
  	 			submissionType !='SHORTLIST' && submissionType != 'INTERVIEW' && submissionType !='OFFERMADE' && submissionType !='NORESPONSE' &&
  	 			submissionType != 'ONHOLD' && submissionType !='JOINED' && submissionType !='REJECTED' && submissionType !='BACKOUT'){
          window.location.href = atsUrl+"/getWorkflowSubmissionsExportByRequirementId?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&submissionType="+submissionType+"&workflowId="+workflowId+"&exportType=xls";
	  }else {
		  window.location.href = atsUrl+"/getSubmissionsExportByRequirementId?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&submissionType="+submissionType+"&exportType=xls";
	  }
      }else{
		  var result = "";
			result = result +"	   <div id='pipeline_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#pipeline_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	  }

}

</script>


 <script>
function exportPipelineToPdf(requirementId,submissionType){
	$('.export_pipline').hide();
	 var workflowId ='${workflowId}';  
	  var clientSubmissions = '${clientSubmissions}';
	  var emptypilpeline = $("#emptyPipelinelist").val();
	  if(emptypilpeline > 0 ){
	 	 if(submissionType != 'all' && submissionType != 'DUPLICATE_APPLICANTS' && submissionType !='REQUEST_SENT' && submissionType != 'SUBMISSION' && 
	 			submissionType !='SHORTLIST' && submissionType != 'INTERVIEW' && submissionType !='OFFERMADE' && submissionType !='NORESPONSE' &&
	 			submissionType != 'ONHOLD' && submissionType !='JOINED' && submissionType !='REJECTED' && submissionType !='BACKOUT'){
	 		  window.location.href = atsUrl+"/getWorkflowSubmissionsExportByRequirementId?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&submissionType="+submissionType+"&workflowId="+workflowId+"&exportType=pdf";
	 	  }else{
	 		  window.location.href = atsUrl+"/getSubmissionsExportByRequirementId?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&submissionType="+submissionType+"&exportType=pdf";
	 	  }

	  }else{
		  var result = "";
			/* result = result +"	<div class='project_inn_mask'></div>"; */
			result = result +"	   <div id='pipeline_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#pipeline_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	  }
      
}

</script>


<script>
	// Close Advanced Search
		function closeCustomizePop(){
			$("div.cand-advsrch-popup").hide();
			$("#customizeScoreDiv").hide();
		}
		function openPop(requirementId){
			//document.getElementById("updateCustomize_form").reset();
			customizeScore(requirementId);
		}
	</script>  

<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script type="text/javascript">
function customizeScore(requirementId){
	multicities = {}; 
	$("#customizeScoreDiv").show();
	var apiLocation = "apiLocation";
	 $.ajax({
		   	type: "GET",
		    url: atsUrl+"/getCustomizeScore?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId, 	   
		    success: function(data) {
		    	var json = data.object;
		    	if (data.response === "FAILED") {
		    		showError(data.responseMessage);
		    	} else if (data.response === "AWKWARD") {
		    		location.href = "/errorPage";
		    	} else if (data.response === "SUCCESS") {
			    	if(json.customizeMinExperience != null && json.customizeMinExperience != '' && json.customizeMinExperience != 'undefiend'){
			    		customizeMinExperience = json.customizeMinExperience;
			    	}else{
			    		customizeMinExperience = "";
			    	}
			    	if(json.customizeMaxExperience != null && json.customizeMaxExperience != '' && json.customizeMaxExperience != 'undefiend'){
			    		customizeMaxExperience = json.customizeMaxExperience;
			    	}else{
			    		customizeMaxExperience = "";
			    	}
			    	if(json.customizeRate != null && json.customizeRate != '' && json.customizeRate != 'undefiend'){
			    		customizeRate = json.customizeRate;
			    	}else{
			    		customizeRate = "";
			    	}
			    	if(json.customizeCountry != null && json.customizeCountry !='' && json.customizeCountry !='undefined'){
			    		var customizeCountry = json.customizeCountry;
					}
					if(json.customizeState != null && json.customizeState !='' && json.customizeState !='undefined'){
						var customizeState = json.customizeState;
					}
					if(json.customizeCity != null && json.customizeCity !='' && json.customizeCity !='undefined'){
						var customizeCity = json.customizeCity;
					}
			    	//lookupDB skills
			    	var dbskills = new Array();
			    	if(json.dbSkills != null && json.dbSkills !='' && json.dbSkills !='undefined'){
				    	var lookupSkills = json.dbSkills;
				    	dbskills = lookupSkills.split(",");
			    	}
			    	
			    	var result = "";
			    	//result = result +'		          <div class="cand-advsrch-popup customizescore-pop customscorePop">  ';
			    	
			    	result = result +'		          <div class="sideMask" style="display:block;"></div>';
			    	result = result +'		          <div class="modal" style="display:block; z-index:9999">  ';
			    	result = result +'		          <div class="modal-dialog modal-lg">  ';
			    	result = result +'		          <div class="modal-content">  ';
			    	result = result	+ ' <form id="updateCustomize_form"  name="updateCustomize_form" method="post">';
			    	result = result +'		            <div class="row clearfix modal-body"> ';
			        result = result +'		                <div class="clearfix mt">  ';
			        
			        result = result +'		                <div class="col-md-10 lblup paddingRight skills-chosen">    ';
			        result = result +'		                   <label>Skills</label>';
			        result = result +'		                    <select class="chosen-select" data-placeholder="Skills" multiple id="customizeSkills" name="customizeSkills"> ';
			        
			      //requitrement skills
			    	if(json.customizeSkills != null  && json.customizeSkills != '' && json.customizeSkills != 'undefiend'){
			    		var requirmentSkills = json.customizeSkills;
				    	var customizeSkills = new Array();
				    	customizeSkills = requirmentSkills.split(",");
				        for (var i = 0; i < customizeSkills.length; i++) { 
				        	 var customSkill = customizeSkills[i];
				        	 result = result +'	<option selected value=\''+customSkill+'\'>'+customSkill+'</option> ';
				        }
			    	}
			         for (var i = 0; i < dbskills.length; i++) { 
			        	 var skill1 = dbskills[i];
			        	 result = result +'	<option  value=\''+skill1+'\'>'+skill1+'</option> ';
			        } 
			        result = result +'		                    </select> ';
			        result = result +'		                  </div>   ';
			        
			        result = result +'		                   <div class="col-md-2">';
			        result = result +'		                    <select class="chosen-select" data-placeholder="Skills" id="skillScore" name="skillScore"> ';
			        result = result +'		                      <option disabled>Skills</option>  ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "0" ? "selected" : '') +' value="0">0%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "10" ? "selected" : '') +' value="10">10%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "20" ? "selected" : '') +' value="20">20%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "30" ? "selected" : '') +' value="30">30%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "40" ? "selected" : '') +' value="40">40%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "50" ? "selected" : '') +' value="50">50%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "60" ? "selected" : '') +' value="60">60%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "70" ? "selected" : '') +' value="70">70%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "80" ? "selected" : '') +' value="80">80%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "90" ? "selected" : '') +' value="90">90%</option> ';
			        result = result +'		                      <option '+ (json.customizeSkillScore == "100" ? "selected" : '') +' value="100">100%</option>';
			        result = result +'		                    </select>              ';
			        result = result +'		                  </div>                   ';
			        result = result +'		           <div class="clearfix"></div>';
			        //result = result +'		                </div> ';
			      
			        result = result +'		                <div class="clearfix mt">   ';
			        result = result +'		                    <div class="col-md-3 paddingRight lblup ">   ';
			        result = result +'		                   <label>Choose City</label>';
			        result = result +'                              <input type="text" class="form-control positionRelative"  multiple data-placeholder="select city" multiple name="apiLocation"  id="apiLocation">';
			        result = result +'                              <i class="fa fa-refresh btn btn-primary refresh-icon mr2 ref-top" title="Refresh" onClick="refreshGoogleApiCity(\''+apiLocation+'\');"></i>';
			        result = result +'		                  </div>  ';
			        
			        result = result +'		                  <div class="col-md-7 paddingRight lblup"> ';   
			        result = result +'		                   <label>Selected City</label>'; 
			        result = result +'		                    <select class="chosen-select" multiple data-placeholder="City" name="customizedCity" id="city" value="'+customizeCity+'" readonly="readonly">    ';
			     	if(json.customizeCity != null  && json.customizeCity != '' && json.customizeCity != 'undefiend'){
			    		var cites = json.customizeCity;
				    	var customizeCites = new Array();
				    	customizeCites = cites.split(",");
				        for (var i = 0; i < customizeCites.length; i++) { 
				        	 var customCity = customizeCites[i];
				        	 result = result +'	<option selected value=\''+customCity+'\'>'+customCity+'</option> ';
				        }
			    	}
			        result = result +'		                    </select>                  ';
			        result = result +'		                  </div>                       ';
			        
			        result = result +'                      <div class="col-md-2"> ';
			        result = result +'		                    <select class="chosen-select" data-placeholder="Location" id="locationScore" name="locationScore">';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "0" ? "selected" : '') +' value="0">0%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "10" ? "selected" : '') +' value="10">10%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "20" ? "selected" : '') +' value="20">20%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "30" ? "selected" : '') +' value="30">30%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "40" ? "selected" : '') +' value="40">40%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "50" ? "selected" : '') +' value="50">50%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "60" ? "selected" : '') +' value="60">60%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "70" ? "selected" : '') +' value="70">70%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "80" ? "selected" : '') +' value="80">80%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "90" ? "selected" : '') +' value="90">90%</option> ';
			        result = result +'		                      <option '+ (json.customizeLocationScore == "100" ? "selected" : '') +' value="100">100%</option>';
			        result = result +'		                    </select>                 ';
			        result = result +'		                  </div>                      ';
			        result = result +'		           <div class="clearfix"></div>';
	// 		        result = result +'		                </div>                        ';
			        
			        result = result +'				        <div class="clearfix mt"> ';
			        result = result +'		                  <div class="col-md-10 lblup paddingRight">    ';
			        result = result +'		                   <label>Notice Period</label>';
			        result = result +'		                      <select class="chosen-select" data-placeholder="Notice" multiple id="noticeperiod" name="noticeperiod">';
			      //split the noticeperiod
			      var noticeperiod = new Array();
			    	if(json.customizeNoticeperiod != null && json.customizeNoticeperiod != '' && json.customizeNoticeperiod != 'undefined'){
			    		var customizeNoticeperiod = json.customizeNoticeperiod;
				    	noticeperiod =  customizeNoticeperiod.split(",");
			    	}
		        	 result = result  +' <option value="Immediately Available" '+(noticeperiod.indexOf("Immediately Available")>=0 ? "selected" : '') +'>Immediately available</option>';
		        	 result = result  +' <option value="15 Days" '+(noticeperiod.indexOf("15 Days")>=0 ? "selected" : '') +'>15 Days</option>';
		        	 result = result  +' <option value="1 Month" '+(noticeperiod.indexOf("1 Month")>=0 ? "selected" : '') +'>1 Month<s/option>';
		        	 result = result  +' <option value="2 Months" '+(noticeperiod.indexOf("2 Months")>=0 ? "selected" : '') +'>2 Months</option>';
		        	 result = result  +' <option value="3 Months" '+(noticeperiod.indexOf( "3 Months")>=0 ? "selected" : '') +'>3 Months</option>';
		        	 $("#noticeperiod").empty();
		        	 $("#noticeperiod").append(result);
	   			     $("#noticeperiod").trigger('chosen:update');
			        result = result +'		                    </select> ';
			        result = result +'		                    </div>  ';
			        
			        result = result +'		                    <div class="col-md-2">  ';
			        result = result +'		                    <select class="chosen-select" data-placeholder="Rate" id="noticeperiodScore" name="noticeperiodScore">';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "0" ? "selected" : '') +' value="0">0%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "10" ? "selected" : '') +' value="10">10%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "20" ? "selected" : '') +' value="20">20%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "30" ? "selected" : '') +' value="30">30%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "40" ? "selected" : '') +' value="40">40%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "50" ? "selected" : '') +' value="50">50%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "60" ? "selected" : '') +' value="60">60%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "70" ? "selected" : '') +' value="70">70%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "80" ? "selected" : '') +' value="80">80%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "90" ? "selected" : '') +' value="90">90%</option> ';
			        result = result +'		                      <option '+ (json.customizeNoticeperiodScore == "100" ? "selected" : '') +' value="100">100%</option>';
			        result = result +'		                    </select>               ';
			        result = result +'		                  </div>                    ';
			        result = result +'		                </div>                      ';
			        
			        result = result +'		                <div class="clearfix mt">';
			        result = result +'		                  <div class="col-md-5 lblup"> ';
			        result = result +'		                   <label>Experience</label>';
			        result = result + '<select  class="chosen-select input-sm form-control" data-placeholder="Min Experience" name="minExperience"  id="minExperience" onblur="minMaxExperience();" field-name="Experience Years">';
					result = result + '<option value="">Min Experience</option>';
					<c:forEach var="i" begin="0" end="24" >
						result = result  +'<option value="${i}" '  + (customizeMinExperience == "${i}" ? "selected" : '') +  '>${i}</option>';
					</c:forEach>
					result = result + '</select>';
			        result = result +'		                    </div> ';
			        
			        result = result +'		                  <div class="col-md-5 paddingRight"> ';
			        result = result + '<select  class="chosen-select input-sm form-control" data-placeholder="Max Experience" name="maxExperience"  onblur="minMaxExperience();" id="maxExperience" field-name="Experience Years">';
					result = result + '<option value="">Max Experience</option>';
					<c:forEach var="i" begin="0" end="25" >
						result = result  +'<option value="${i}" '  + (customizeMaxExperience == "${i}" ? "selected" : '') +  '>${i}</option>';
					</c:forEach>
					result = result + '</select>';
			        result = result +'		                    </div>  ';
			        
			        result = result +'		                    <div class="col-md-2"> ';
			        result = result +'		                    <select class="chosen-select" data-placeholder="Experience" id="experienceScore" name="experienceScore"> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "0" ? "selected" : '') +' value="0">0%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "10" ? "selected" : '') +' value="10">10%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "20" ? "selected" : '') +' value="20">20%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "30" ? "selected" : '') +' value="30">30%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "40" ? "selected" : '') +' value="40">40%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "50" ? "selected" : '') +' value="50">50%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "60" ? "selected" : '') +' value="60">60%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "70" ? "selected" : '') +' value="70">70%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "80" ? "selected" : '') +' value="80">80%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "90" ? "selected" : '') +' value="90">90%</option> ';
			        result = result +'		                      <option '+ (json.customizeExperienceScore == "100" ? "selected" : '') +' value="100">100%</option>';
			        result = result +'		                    </select>              ';
			        result = result +'		                  </div>                   ';
			        result = result +'<p class="jquery_form_error_message" id="experiencevalidation"> </p>';
			        
			        result = result +'<div id="expId">';
			        result = result +'<input type="hidden" name="expValidation" id="expValidation">';
			        result = result +'</div>';		        
			        result = result +'		                </div>                     ';
			        
			        result = result +'		                  <div class="clearfix mt"> ';
			        result = result +'		                  <div class="col-md-10 lblup paddingRight">   ';
			        result = result +'		                   <label>Visa Type</label>';
			        result = result +'		                     <select class="chosen-select" data-placeholder="Visa Type" multiple id="visaType" name="visaType">';
			        if(json.customizeVisatype != null  && json.customizeVisatype != '' && json.customizeVisatype != 'undefiend'){
			    		var visaType = json.customizeVisatype;
				    	var customizeVisaType = new Array();
				    	customizeVisaType = visaType.split(",");
				        for (var i = 0; i < customizeVisaType.length; i++) { 
				        	 var customizeVisaTypes = customizeVisaType[i];
				        	 result = result +'	<option selected value=\''+customizeVisaTypes+'\'>'+customizeVisaTypes+'</option> ';
				        }
			    	}
			        var dbVisaType = new Array();
			        if(json.dbVisaType != null && json.dbVisaType !='' && json.dbVisaType !='undefined'){
				        var lookupVisaType = json.dbVisaType;
				    	dbVisaType = lookupVisaType.split(",");
			        }
			    	
			    	 for (var i = 0; i < dbVisaType.length; i++) { 
			        	 var visatype = dbVisaType[i];
			        	 result = result +'	<option  value=\''+visatype+'\'>'+visatype+'</option> ';
			        } 
			        result = result +'	</select> ';
			  
			        result = result +'		                    </div>  ';
			        result = result +'		                    <div class="col-md-2">  ';
			        result = result +'		                    <select class="chosen-select" data-placeholder="Visa Type Score" id="visaTypeScore" name="visaTypeScore" > ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "0" ? "selected" : '') +' value="0">0%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "10" ? "selected" : '') +' value="10">10%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "20" ? "selected" : '') +' value="20">20%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "30" ? "selected" : '') +' value="30">30%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "40" ? "selected" : '') +' value="40">40%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "50" ? "selected" : '') +' value="50">50%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "60" ? "selected" : '') +' value="60">60%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "70" ? "selected" : '') +' value="70">70%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "80" ? "selected" : '') +' value="80">80%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "90" ? "selected" : '') +' value="90">90%</option> ';
			        result = result +'		                      <option '+ (json.customizeVisatypecore == "100" ? "selected" : '') +' value="100">100%</option>';
			        result = result +'		                    </select> ';
			        result = result +'		                  </div>  ';
			        result = result +'		                </div>  ';
			      
			        result = result +'		                  <div class="clearfix mt"> ';
			        result = result +'		                  <div class="col-md-10 lblup paddingRight">   ';
			        result = result +'		                   <label>Education</label>';
			        result = result +'		                     <select class="chosen-select" data-placeholder="Education" multiple id="education" name="education">';
			        if(json.customizeEducation != null  && json.customizeEducation != '' && json.customizeEducation != 'undefiend'){
			    		var education = json.customizeEducation;
				    	var customizeEducation = new Array();
				    	customizeEducation = education.split(",");
				        for (var i = 0; i < customizeEducation.length; i++) { 
				        	 var customizeEducations = customizeEducation[i];
				        	 result = result +'	<option selected value=\''+customizeEducations+'\'>'+customizeEducations+'</option> ';
				        }
			    	}
			        var dbEducation = new Array();
			        if(json.dbEducation != null && json.dbEducation !='' && json.dbEducation !='undefined'){
				        var lookupEducation = json.dbEducation;
				    	dbEducation = lookupEducation.split(",");
			        }
			    	
			    	 for (var i = 0; i < dbEducation.length; i++) { 
			        	 var education = dbEducation[i];
			        	 result = result +'	<option  value=\''+education+'\'>'+education+'</option> ';
			        } 
			        result = result +'		                    </select> ';
			        result = result +'		                    </div>  ';
			        result = result +'		                    <div class="col-md-2">  ';
			        result = result +'		                    <select class="chosen-select" data-placeholder="Education" id="educationScore" name="educationScore" > ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "0" ? "selected" : '') +' value="0">0%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "10" ? "selected" : '') +' value="10">10%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "20" ? "selected" : '') +' value="20">20%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "30" ? "selected" : '') +' value="30">30%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "40" ? "selected" : '') +' value="40">40%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "50" ? "selected" : '') +' value="50">50%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "60" ? "selected" : '') +' value="60">60%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "70" ? "selected" : '') +' value="70">70%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "80" ? "selected" : '') +' value="80">80%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "90" ? "selected" : '') +' value="90">90%</option> ';
			        result = result +'		                      <option '+ (json.customizeEducationScore == "100" ? "selected" : '') +' value="100">100%</option>';
			        result = result +'		                    </select> ';
			        result = result +'		                  </div>  ';
			        result = result +'		                </div>  ';
			      
			        
			        result = result +'		           <div class="clearfix mt">     ';
			        
			        
			        result = result +'		                  <div class="col-md-5 lblup">   ';
			        result = result +'		                   <label>Rate</label>';
			        result = result +'		                    <input type="text" placeholder="Rate" field-name="Rate" class="form-control input-sm" value="'+customizeRate+'" id="customizeRate" name="customizeRate" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">  ';
			        result = result +'		                    </div>   ';
			        
			        result = result +' <div class="col-md-5 paddingRight">   ';
			        result = result + '<select  class="input-sm form-control" name="currentRateType"  id="currentRateType" field-name="Rate Type">';
					result = result + '<option value="">Rate Per</option>';
					result = result + ' <option '+(json.customizeRatetype == "Hourly" ? "selected" : '') +'>Hourly</option>';
					result = result + '<option  '+ (json.customizeRatetype == "Weekly" ? "selected" : '') +'>Weekly</option>';
					result = result + '<option  '+(json.customizeRatetype == "Bi-weekly" ? "selected" : '') +'>Bi-weekly</option>';
					result = result + '<option  '+(json.customizeRatetype == "Semi-Monthly" ? "selected" : '') +'>Semi-Monthly</option>';
					result = result + '<option  '+(json.customizeRatetype == "Monthly" ? "selected" : '') +'>Monthly</option>';
					result = result + '<option  '+(json.customizeRatetype == "Bi-Monthly" ? "selected" : '') +'>Bi-Monthly</option>';
					result = result + '<option '+(json.customizeRatetype == "Yearly" ? "selected" : '') +'>Yearly</option>';
					result = result + ' </select>';
			        result = result +'	  </div>   ';
			        
			        result = result +'		                    <div class="col-md-2">      ';
			        result = result +'		                    <select class="chosen-select" data-placeholder="Rate" id="rateScore" name="rateScore"> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "0" ? "selected" : '') +' value="0">0%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "10" ? "selected" : '') +' value="10">10%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "20" ? "selected" : '') +' value="20">20%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "30" ? "selected" : '') +' value="30">30%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "40" ? "selected" : '') +' value="40">40%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "50" ? "selected" : '') +' value="50">50%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "60" ? "selected" : '') +' value="60">60%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "70" ? "selected" : '') +' value="70">70%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "80" ? "selected" : '') +' value="80">80%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "90" ? "selected" : '') +' value="90">90%</option> ';
			        result = result +'		                      <option '+ (json.customizeRateScore == "100" ? "selected" : '') +' value="100">100%</option>';
			        result = result +'		                    </select>               ';
			        result = result +'		                  </div>                    ';
			        result = result +'		                </div>                      ';
			       
			        result = result +'		                  <div class="clearfix mt"> ';
			        result = result +'		                  <div class="col-md-10 lblup paddingRight">   ';
			        result = result +'		                   <label>Keywords</label>';
			        result = result +'		                     <select class="chosen-select" data-placeholder="Keywords" multiple id="anyKeyWord" name="anyKeyWord">';
			      //Split the keyWords
			    	if(json.customizeKeywords != null && json.customizeKeywords != '' && json.customizeKeywords != 'undefined'){
			    		var customizeKeywords = json.customizeKeywords;
				    	var Keywords = new Array();
				    	Keywords =  customizeKeywords.split(",");
				        for (var i = 0; i < Keywords.length; i++) { 
				        	 var customKeywords = Keywords[i];
				        	 result = result +'	<option selected value='+customKeywords+'>'+customKeywords+'</option> ';
				        }
			    	}
			        result = result +'		                    </select> ';
			        result = result +'		                    </div>  ';
			        result = result +'		                    <div class="col-md-2">  ';
			        result = result +'		                    <select class="chosen-select" data-placeholder="KeyWords" id="keywordScore" name="keywordScore" > ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "0" ? "selected" : '') +' value="0">0%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "10" ? "selected" : '') +' value="10">10%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "20" ? "selected" : '') +' value="20">20%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "30" ? "selected" : '') +' value="30">30%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "40" ? "selected" : '') +' value="40">40%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "50" ? "selected" : '') +' value="50">50%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "60" ? "selected" : '') +' value="60">60%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "70" ? "selected" : '') +' value="70">70%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "80" ? "selected" : '') +' value="80">80%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "90" ? "selected" : '') +' value="90">90%</option> ';
			        result = result +'		                      <option '+ (json.customizeKeywordsScore == "100" ? "selected" : '') +' value="100">100%</option>';
			        result = result +'		                    </select> ';
			        result = result +'		                  </div>  ';
			        result = result +'		                </div>  ';
			        result = result +'		                </div>  ';
			        result = result +'		                </div>  ';
			      
			        
	// 		        =========================================================================================================================
			        
			        
			        result = result +'		              </div>                          ';
			        
			        result = result +'		            <div class="form-footer clearfix">                                              ';

	
			        result = result +'		              <div class="pull-right mr"> <a href="javascript:void(0)" title="Submit" class="btn btn-sm btn-primary" onclick="updateCutomize('+requirementId+');">Apply</a> <a href="javascript:void(0)" title="Cancel" class="btn btn-sm btn-default ml10" onclick="closeCustomizePop();">Cancel</a> </div>';

			        result = result +'		            </div>'; 
			        result = result	+ '               <form>';
			        result = result +'		            </div>'; 
			        
			        $("#customizeScoreDiv").empty();
			    	$("#customizeScoreDiv").append(result);
	// 		    	getAllCountries();
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
			    	
			            $("#city").chosen();
			            
			         /*    $("#country").on('change', function (e) {
			            	  getcites('apiLocation','city');
					    	  $("#apiLocation").val('');
					    	  $("#city").val('').trigger("chosen:updated");
			            }); */
						getcites('apiLocation','city','recommendation');
						
			    			$('.chosen-select').each(function() {
			    				var $this = $(this);
			    				$this.next().css({'width': '100%'});
			    		   });
	
			    			 // here call experience validation
			 		       $("#maxExperience").on('change', function (e) {
			 		    	   minMaxExperience();
			 		       });
			 		       
			 		       $("#minExperience").on('change', function (e) {
			 		    	   minMaxExperience();
			 		       });
			 		       
				          //Adding Customized Skills Keyword to the list 
			 		      tagIt("customizeSkills", 'Press Enter-Key Or Comma(,) To Add New Skill:', 'tagItBykeyForSkills','skills','Skills');
				          
			    		  //Adding Customized Educaton to the list 
			 		      tagIt("education", 'Press Enter-Key Or Comma(,) Or Tab To Add New Education:', 'tagItBykeyForSkills','skills','Education');
	
			 		       
			    		  //Adding Customized Any Of Keyword to the list 
			    		  tagIt("anyKeyWord", 'Press Enter-Key Or Comma(,) To Add Keyword:', 'tagItBykeyForSkills','skills','Keywords');
			    		     
			   		     tagIt("city", 'Press Enter-Key Or Comma(,) Or Space To Add New Cites:', 'tagItBykeyForSkills','skills','sletected cites');
		    	}else if (data.indexOf('Sign In') !== -1) {
		    		location.replace(sourceleadUrl);
		    	} else {
		    		showError("Operation failed, please try again");
		    	}
		    }
	 });
	
}
</script>

<script>
//Function to validate if maximum exp value is more the minimum exp value
function minMaxExperience(){
    var maxExp = ($('#maxExperience').val());
    var minExp = ($('#minExperience').val());
    if (parseFloat(maxExp) <= parseFloat(minExp)){
        $('#expId').find('p.jquery_form_error_message').remove(); 
		$('input#expValidation').attr('class','error signin_fild');
		$("input#expValidation").after("<p class='jquery_form_error_message'>Max Experience should be greater than Min Experience</p>");
		$('input#expValidation').attr('record-exist','yes');
		$('input#expValidation').attr('record-exist-errorMsg','Max Experience should be greater than Min Experience'); 
    }  else{
    	$('#expId').find('p.jquery_form_error_message').remove(); 
    	$('input#expValidation').removeAttr( "class record-exist record-exist-errormsg");
		$('#expId').find('p.jquery_form_error_message').remove();
    }
}

</script>


<script>
function updateCutomize(requirementId){
	minMaxExperience();
	
	var RatePer = document.getElementById("currentRateType").value; 
	if(RatePer != null && RatePer != ""){
		$('#customizeRate').removeAttr('data-validation-optional');
		$('#customizeRate').attr("data-validation","required validate_Space validate_float validate_length length1-8"); 
	}else{
		$('#customizeRate').removeAttr('data-validation');
		$('#customizeRate').attr("data-validation-optional","true"); 
	}
	 
	var Rate = document.getElementById("customizeRate").value; 
	if(Rate != null && Rate != ""){
		$('#currentRateType').removeAttr('data-validation-optional');
		$('#currentRateType').attr("data-validation","required");
		$('#customizeRate').attr("data-validation","required validate_Space validate_float validate_length length1-8");
	}else{
		$('#currentRateType').removeAttr('data-validation');
		$('#currentRateType').attr("data-validation-optional","true"); 
	}
	
    var skillPerc = parseInt($("#skillScore").val());
    var expPerc = parseInt($("#experienceScore").val());
    var ratePerc = parseInt($("#rateScore").val());
    var noticePerc = parseInt($("#noticeperiodScore").val());
    var kewordsPerc = parseInt($("#keywordScore").val());
    var locationScore = parseInt($("#locationScore").val());
    var educationScore = parseInt($("#educationScore").val());
    var visaTypwScore = parseInt($("#visaTypeScore").val());
    var totalscore = (skillPerc+expPerc+noticePerc+ratePerc+locationScore+kewordsPerc+educationScore+visaTypwScore);
    
    
    if(totalscore>100){
        var result = "";
		//result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='greater_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Score is greater than 100</p>";
		result = result +"		<p><strong>Score must be equal to 100</strong></p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b><input type='button' value='Ok' onclick='cancelCustomize();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelCustomize();'></b>";
		result = result +"	</div>";
		$("#customizePopUp").append(result);
		$("#customizePopUp").show();
		$("#greater_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
		
        }else if(totalscore<100){
        	var result = "";
    		//result = result +"	<div class='project_inn_mask'></div>";
    		result = result +"	   <div id='less_user_pop' class='remove_user_pop remove_user_pop_a'>";
    		result = result +"		<p>Score is less than 100</p>";
    		result = result +"		<p><strong>Score must be equal to 100</strong></p>";
    		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
    		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelCustomize();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelCustomize();'></b>";
    		result = result +"	</div>";
    		$("#customizePopUp").append(result);
    		$("#customizePopUp").show();
    		$("#less_user_pop").slideDown(200);
    		$(".project_inn_mask").fadeIn(200);
        }else if(totalscore=100){
           	if ($('#updateCustomize_form').validate(false, validationSettings)){
        	var frm = $('#updateCustomize_form').serializeFormJSON();
    		var con = JSON.stringify(frm);
    		con = con.replace(/[{}]/g, "");
    		//It will escape all the special characters
    		var jsonData = encodeURIComponent(con);
    		 $.ajax({
    					type : "POST",
    					url : atsUrl + "/updateCustomizeScore?"+tokenParamName+"="+tokenValue+"&json="+ jsonData + "&requirementId=" + requirementId,
    					success : function(data) {
    						if (data.response === "FAILED") {
    				    		showError(data.responseMessage);
    				    	} else if (data.response === "AWKWARD") {
    				    		location.href = "/errorPage";
    				    	} else if (data.response === "SUCCESS") {
	    						if(data.object == "success"){
	    							$("div.cand-advsrch-popup").hide();
	    							requirementRecommendations(requirementId);	
	    						}
    				    	}else if (data.indexOf('Sign In') !== -1) {
    				    		location.replace(sourceleadUrl);
    				    	} else {
    				    		showError("Operation failed, please try again");
    				    	}
    					}
    				}); 
    		 return true;
       	  }else{ 
			return false;
		 } 
       } 
	
}
$('body').on('blur', '#updateCustomize_form', function() {
	$("#updateCustomize_form").showHelpOnFocus();
	$("#updateCustomize_form").validateOnBlur(false, validationSettings);
});
</script>
<script>
function cancelCustomize(){
	$(".project_inn_mask").fadeOut();
	$("#customizePopUp").toggle();
}
</script>

<!-- Script for Requirement recommendation profile saving , deleting -->

<script type="text/javascript">
function loadFile(event,candidateId){
		var output = document.getElementById('userImage');
	    var uploadedImage = document.getElementById('uploadedImage').value;
	    var sizeinbytes = document.getElementById('uploadedImage').files[0].size;
	    document.getElementById("errorImage").style.display="none"; 
	    
	    var extension = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1).toLowerCase();
	    
	    if(extension != 'jpg'){
	    	output.src = URL.createObjectURL(event.target.files[0]);
	    	
	    	var extensionName = uploadedImage.substring(uploadedImage.lastIndexOf(".")+1);
	 		var msg = "."+extensionName+" extension not supported";
	 		recommendationAlertMessage(msg,candidateId);
	    }else if(sizeinbytes > 200000){
	    	output.src = URL.createObjectURL(event.target.files[0]);
	 		var msg = "Image size should be less than 200 KB";
	 		recommendationAlertMessage(msg,candidateId);
	    }else{
	    	output.src = URL.createObjectURL(event.target.files[0]);
	    	var msg = "Do you want to save the picture.";
	    	saveOrCancel(msg,candidateId);
	   } 
}
</script>

<script type="text/javascript">
function recommendationAlertMessage(message,candidateId){
	var result = "";
	/* result = result +"	<div class='project_inn_mask'></div>"; */
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='recommendationAlertCancel("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='recommendationAlertCancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	refreshImageBlock(candidateId);

}
</script>

<script>
function refreshImageBlock(candidateId){  
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	  $.ajax({
          type : "POST",
           url: atsUrl+"/getCandidateById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
           success :function(result) { 
        	   var json = result.object;
        	   if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadUrl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
	                	if(json != null && json != 'undefined' && json != ""){  
		                	 $("#candidateImageId").empty();
		                	 var result = '';
				             result = result+'<div class="well text-center clearfix">';
							 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
							 if(json.fileUrl != null && json.fileUrl !='' && json.fileUrl !="undefined"){
							 result = result+'   <img  id="userImage" data-id="profileImage" onerror="predefineProfileImage(this);" src="'+json.fileUrl+'" alt="Profile Image" class=" img-responsive center-block img-circle">';						 
							 }else{
							 result = result+'   <img  id="userImage" data-id="profileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/userprofileImg.jpg" alt="Profile Image" class=" img-responsive center-block img-circle">';						 
		 
							 }
							 result = result+'  <div class="btn-group uploadfile">';
							 result = result+'  <button type="button" class="btn btn-sm btn-primary">';
							 result = result+'  <i class="fa fa-upload"></i></button>';
							 result = result+'  <label class="btn btn-sm btn-primary">';
							 result = result+'    Upload / Change<input type="file" name="file" id="uploadedImage"  accept="image/*" onchange="cropImage(this,\'candidate_form\','+candidateId+');" style="display: none;">';					     
							 result = result+'  </label>';
						     if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
							     result = result+'  <button type="button" class="btn btn-sm btn-danger" onclick="deleteImage('+candidateId+')">';
								 result = result+'  <i class="fa fa-trash"></i></button>';
						     } 
							 result = result+'  </div>';
							 result = result+'  </div>';
							 
							 
							 result = result+'  </div>';
							 result = result+' <div class="mb mt5 UsProBar">';
							 result = result+' <div class="progress nomr"> ';
							 if(json.profilePercentage>=50){
							 result = result+' <div class="progress-bar progress-bar-success ';
							 }
							 else{
							 result = result+' <div class="progress-bar progress-bar-danger ';
							 }
							 result = result+' progress-bar-striped active" role="progressbar" id="perVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%"> ';
							 result = result+' <p id="disVal">'+json.profilePercentage+'%</p> ';
							 result = result+' </div> ';
							 result = result+' </div> ';
							 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
							 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not supported</p>';
							 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>';
							 result = result+' </div>';
							 $("#candidateImageId").append(result);
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
function deleteImage(candidateId){
	var result = "";
	/* result = result +"	<div class='project_inn_mask  projectMask-zindex'></div>"; */
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure you want to delete this image ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='recommendationAlertCancel("+candidateId+");'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script type="text/javascript">
function deleteProfileImage(candidateId){   
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var submissionId = '${submissionId}';
	 $.ajax({
		    type: "GET",
            url: atsUrl+"/deleteCandidateImage?candidateId="+candidateId+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId,
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
		        	 var result = response.object;
		        	 $("#popUp").empty();
		        	 $(".project_inn_mask").fadeOut(200);
		        	 refreshImageBlock(candidateId);
		        	 applicantQuickViewProfile(candidateId,submissionId);
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	       },
	        
     }); 
}
</script>

<script type="text/javascript">
function saveOrCancel(message,candidateId){
	var result = "";
	/* result = result +"	<div class='project_inn_mask'></div>"; */
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='saveCandidateImage("+candidateId+");' ><input class='u_can_color' type='button'  value='Cancel' onClick='recommendationAlertCancel("+candidateId+");'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function saveCandidateImage(candidateId){
	            		
	var frm = $('#candidate_form').serializeFormJSON();
	var con = JSON.stringify(frm);
	con = con.replace(/[{}]/g, ""); 

   var jsonData = encodeURIComponent(con);
 		
   var atsUrl = '${SourceLeadWeb_atsUrl}';
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var form = $('#candidate_form')[0];
	var formData = new FormData(form);
    		
    $.ajax({
    	type: "POST",
    	data: formData,
    	contentType : 'application/json; charset=utf-8',
    	enctype: 'multipart/form-data',
	    processData: false,
	    contentType:false,
    	dataType : 'json',
        url: atsUrl+"/saveCandidateProfile?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId,
        success: function(response) {
        	if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
        		recommendationAlertCancel(candidateId);        		
        	} else if (response.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
     	},  
    });
}
</script> 

<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','/resources/images/userprofile-img.png');
	}
</script>

<script type="text/javascript">
function recommendationAlertCancel(candidateId){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	refreshImageBlock(candidateId);
}
</script>

<script>
function getRatePerHourly(rateAnDrateType){
var result ='';
var reg = /^-?\d+(?:\.\d{0,2})?/;
var rateValue = rateAnDrateType.split(/\s+/);
if(rateValue[1] == 'Monthly'){
	var rateMontlyToHourly = (rateValue[0]/184);
	result = parseFloat(rateMontlyToHourly).toString().match(reg)[0];
}
if(rateValue[1] == 'Hourly'){
	var rateMontlyToHourly = (rateValue[0]);
	result = parseFloat(rateMontlyToHourly).toString().match(reg)[0];
}
if(rateValue[1] == 'Weekly'){
	var rateMontlyToHourly = (rateValue[0]/40);
	result = parseFloat(rateMontlyToHourly).toString().match(reg)[0];
}
if(rateValue[1] == 'Bi-weekly'){
	var rateMontlyToHourly = (rateValue[0]/80);
	result = parseFloat(rateMontlyToHourly).toString().match(reg)[0];
}
if(rateValue[1] == 'Bi-Monthly'){
	var rateMontlyToHourly = (rateValue[0]/368);
	result = parseFloat(rateMontlyToHourly).toString().match(reg)[0];
}
if(rateValue[1] == 'Semi-Monthly'){
	var rateMontlyToHourly = (rateValue[0]/87);
	result = parseFloat(rateMontlyToHourly).toString().match(reg)[0];
}
if(rateValue[1] == 'Yearly'){
	var rateMontlyToHourly = (rateValue[0]/2088);
	result = parseFloat(rateMontlyToHourly).toString().match(reg)[0];
}
return result;
}
</script>
 
<!-- Ends Script for Requirement recommendation profile saving , deleting -->


<div class="submission_side_wrap ">
	<div class="new_side_wrap_inn">
		<div class="new_side_wrap_cta">
		 <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop datatable_filter">
		    <div id="addJobApplicantsDivLoading"></div>
			<div id="addJobApplicantsDiv"></div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="candidatesListForSubmission.jsp" />
<jsp:include page="addApplicant.jsp" />
<jsp:include page="addInterview.jsp" />
<jsp:include page="submitToClient.jsp" />

<jsp:include page="requirementPipelinePagination.jsp" />
<jsp:include page="requirementWorkflowPipelinePagination.jsp" />
<jsp:include page="requirementPipelinePaginationBackOut.jsp" />
<jsp:include page="requirementPipelinePaginationIN.jsp" />
<jsp:include page="requirementPipelinePaginationOffer.jsp" />
<jsp:include page="requirementPipelinePaginationRejected.jsp" />
<jsp:include page="requirementPipelinePaginationSC.jsp" />
<jsp:include page="requirementPipelinePaginationJoined.jsp" />
<jsp:include page="requirementPipelinePaginationSL.jsp" />


<jsp:include page="addOffer.jsp" />
<jsp:include page="shortlist.jsp" />
<jsp:include page="submissionDetails.jsp" />
<jsp:include page="recommendCandidatesProfile.jsp"></jsp:include>
 <jsp:include page="recommendCandidatesResumes.jsp"></jsp:include>
<jsp:include page="recommendCandidatesExperience.jsp"></jsp:include>
<jsp:include page="recommendCandidatesEducation.jsp"></jsp:include>
<jsp:include page="recommendCandidatesCertificates.jsp"></jsp:include>
<jsp:include page="recommendedCandidateDocuments.jsp"></jsp:include>
<jsp:include page="candidateReferences.jsp"></jsp:include>
<jsp:include page="recommendCandidatesHistory.jsp"></jsp:include>
<jsp:include page="recommendCandidatesQuickView.jsp"></jsp:include>
<jsp:include page="applicantQuickView.jsp"></jsp:include>
<jsp:include page="compareCandidatesProfile.jsp"></jsp:include>
<jsp:include page="requirementAssignedToList.jsp"></jsp:include>
 <jsp:include page="recommendationListTable.jsp"></jsp:include>

