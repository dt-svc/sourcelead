<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


 <style type="text/css">
 .overflow-data {
  min-height: 250px;
 }
.show-tooltip {
	position:relative;
	font-size:13px !important;
}
.show-tooltip .tooltip_div {
	color:#333;
	background: #e1e6fa;
    border-radius: 3px;
    display:none;
    padding:5px;
    position: absolute;
    top: 25px;
	left:-48px;
    width: 450px;
	box-sizing:border-box;
	z-index:999;
	transition: all 0.5s ease;
}
.show-tooltip .tooltipint_detilas {
	color:#333;
	background: #e1e6fa;
    border-radius: 3px;
     display:none;
    padding:5px;
    position: absolute;
    top: 20px;
	right:0;
    width: auto;
    min-height:50px;
    max-height:150px;
    overflow-y:scroll;
	box-sizing:border-box;
	z-index:999;
	transition: all 0.5s ease;
}
.tooltipint_detilas table tr th, .tooltipint_detilas table tr td {
height: 25px;
}
.show-tooltip .tooltip_div:after{
	bottom: 100%;
	left: 15%;
	border: solid transparent;
	content: " ";
	height: 0;
	width: 0;
	position: absolute;
	pointer-events: none;
	border-color: rgba(136, 183, 213, 0);
	border-bottom-color: #e1e6fa;
	border-width: 10px;
	margin-left: -20px;
}
.show-tooltip .tooltipint_detilas:after{
	bottom: 100%;
	right: 5%;
	border: solid transparent;
	content: " ";
	height: 0;
	width: 0;
	position: absolute;
	pointer-events: none;
	border-color: rgba(136, 183, 213, 0);
	border-bottom-color: #e1e6fa;
	border-width: 10px;
	margin-left: -20px;
}
.show-tooltip:hover .tooltip_div{
	display:block;
}
.show-tooltip:hover .tooltipint_detilas{
	display:block;
}
.tooltip_div table {
	background:#fff;
}
.tooltip_div table thead {
	background:#375d81;
	color:#fff;
}
.tooltip_div table tr th, .tooltip_div table tr td{
	padding:5px 10px;
	height: auto !important;
	font-size: 13px !important;
    border-bottom: none !important;
}
.tooltip_div table tr td span{
	text-overflow:ellipsis;
	overflow:hidden;
	white-space:nowrap;
	display:block;
	width:175px;
	padding-right:5px;
	margin-bottom:0;
}
.project_tabs_inn_table table tr td div i {
color:#333 !important;
}
.remove_user_pop textarea {
width: 100% !important; margin:10px 0 0 0;
}
#tab-2 {
overflow-y:hidden;
}
.table-overflow-hide {
overflow-y:inherit !important;
}
.require_wrap_table {
overflow-y:inherit !important;

.require_wrap_details_cta.require_uploadfile span{
width:auto !important;
}
.require_wrap_details_cta.require_uploadfile a {
margin-left:10px;
float: left !important;
}
.require_wrap_details_cta.require_uploadfile .glyphicon-download-alt {
color:blue;
}

</style>
 
<script>
$(document).ready(function() {
	 var requirementId = '${requirementId}'; 
	 var type = '${type}';
	 if(type != null && type != '' && type != 'undefined'){
			 sideBar(requirementId,type);
	 }else{
		 sideBar(requirementId); 
	 } 
});	
</script>
<script>

</script>

<script>
function prepareAllCounts(requirementId){
    $('#applicantli').load(allApplicantsCount(requirementId)); 
    $('#duplicataplctli').load(duplicateCount(requirementId));
    $('#requestSentli').load(requestSentCount(requirementId));
    $('#shortListli').load(shortListCount(requirementId));
    $('#submitToClientli').load(submitToClientCount(requirementId));
    $('#interviewli').load(interviewsCount(requirementId));
    $('#offerMadeli').load(offerMadeCount(requirementId));
    $('#rejectedli').load(rejectedCount(requirementId)); 
    $('#backoutli').load(backoutCount(requirementId)); 
}
</script>



<script>
function allApplicantsCount(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/getSubmissionsCountBySubmissionStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType=APPLICANTS",        
        success :function(data) { 
       	 var json = JSON.parse(data);
       	 $("#applicantsCount").html(json);
        },
	  });
}
</script>

<script>
function duplicateCount(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
      type : "POST",
       url: sourceleadUrl+"/getDuplicateSubmissionsCountByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
       success :function(data) { 
      	 var json = JSON.parse(data);
      	 $("#duplicateApplicantsCount").html(json);
       },
	  }); 
}
</script>

<script>
function requestSentCount(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
      type : "POST",
       url: sourceleadUrl+"/getSubmissionsCountBySubmissionStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType=REQUESTSENT",        
       success :function(data) { 
      	    var json = JSON.parse(data);
      	    $("#requestSent").html(json);
         },
	  });
}
</script>

<script>
function shortListCount(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
     type : "POST",
      url: sourceleadUrl+"/getSubmissionsCountBySubmissionStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType=SHORTLISTED",        
      success :function(data) { 
     	    var json = JSON.parse(data);
     	    $("#shortlistCount").html(json);
        },
	  });
}
</script>

<script>
function submitToClientCount(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
     type : "POST",
     url: sourceleadUrl+"/getSubmissionsCountBySubmissionStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType=SUBMIT_TO_CLIENT",        
     success :function(data) { 
    	    var json = JSON.parse(data);
    	    $("#submitToClientCount").html(json);
       },
	  });
}
</script>

<script>
function interviewsCount(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
      type : "POST",
      url: sourceleadUrl+"/getSubmissionsCountBySubmissionStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType=INTERVIEWED",        
      success :function(data) { 
    	    var json = JSON.parse(data);
    	    $("#interviewCount").html(json);
       },
	  });
}
</script>

<script>
function offerMadeCount(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
      type : "POST",
      url: sourceleadUrl+"/getSubmissionsCountBySubmissionStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType=OFFERED",        
      success :function(data) { 
    	    var json = JSON.parse(data);
    	    $("#offersCount").html(json);
       },
	  });
}
</script>

<script>
function rejectedCount(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
      type : "POST",
      url: sourceleadUrl+"/getSubmissionsCountBySubmissionStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType=REJECTED",        
      success :function(data) { 
    	    var json = JSON.parse(data);
    	    $("#rejectedCount").html(json);
       },
	  });
}
</script>

<script>
function backoutCount(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
      type : "POST",
      url: sourceleadUrl+"/getSubmissionsCountBySubmissionStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType=BACKOUT",        
      success :function(data) { 
    	    var json = JSON.parse(data);
    	    $("#backoutCount").html(json);
       },
	  });
}
</script>

<!-- Content Area -->
<section class="content-wraper" onclick="popClose(); piplineexportClose();">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Job Summary</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/requirementList'/>">Jobs List  </a>&raquo; Job Details</div>
  </section>
  <div class="timesheet_out">
    
    
     <div class="clients_side_wrap_inn no_pad">
        <div id="requirementSideDiv"> </div>
          <div id="loading"></div>
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
  <div class="proj_close" onClick="activeTab();"></div>
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
</script>  -->

<!-- Show success message after form submission script -->
<script>
	function msgFadein() {
		$("#selector11").fadeIn(200);
        $('#selector11').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->

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
 $('html').click(function(){
	$(".edit-details").removeClass("edit-details-active");
   });	
   $("#endDate").click(function(){
	   $(".endDate").toggle(200);
   });
   event.preventDefault();
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
<script type="text/javascript">
            $(function () {
                $('#datetimepicker5').datetimepicker({
					locale: 'ru'
                });
            });
</script>
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
	var sourceleadUrl ='${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  
	  var isDetailsTabActive = '';
	  var isApplicantsTabActive = '';
	  if(type == null || type == '' || type == 'undefined'){
		  isDetailsTabActive = 'current'
	  }else{
		  isApplicantsTabActive = 'current'
	  }
	 
	  $("#detailsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
           success :function(data) {  
                if(data != null && data != 'undefined' && data != ""){  
                	 var json = JSON.parse(data);
              		
              			var result = '';
              			
              			var jobType = '';
              			if(json.jobType != null && json.jobType != '' && json.jobType != 'undefined'){
              				jobType = '-'+json.jobType;
             			 	}
              			
              			
              			result = result+'    <div class="profile_details requirement_details clearfix reqrementsummary">';
              			if(json.jobId != '' && json.jobId != null && json.jobId != 'undefined' && json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobType != 'Other'){
    				        result = result+'<a href="#" onClick="openTopDiv();"><h4 id="id1"><span title="'+json.jobTitle+'-'+json.jobId+'-'+json.jobType+'">'+json.jobTitle+'-'+json.jobId+'-'+json.jobType+'</span><i></i></h4></a>';
               			}else if(json.jobId != '' && json.jobId != null && json.jobId != 'undefined' && json.jobType != 'Other'){
    				        result = result+'<a href="#" onClick="openTopDiv();"><h4 id="id1"><span title="'+json.jobTitle+'-'+json.jobId+'">'+json.jobTitle+'-'+json.jobId+'</span><i></i></h4></a>';
               			}
               			else if(json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobType != 'Other'){
     				     result = result+'<a href="#" onClick="openTopDiv();"><h4 id="id1"><span title="'+json.jobTitle+'-'+json.jobType+'">'+json.jobTitle+'-'+json.jobType+'</span><i></i></h4></a>';
               			}else if(json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobId != '' && json.jobId != null && json.jobId != 'undefined' && json.jobType == 'Other' &&  json.otherJobType != '' && json.otherJobType != null && json.otherJobType != 'undefined'){
           			     result = result+'<a href="#" onClick="openTopDiv();"><h4 id="id1"><span title="'+json.jobTitle+'-'+json.jobId+'-'+json.jobType+'-'+json.otherJobType+'">'+json.jobTitle+'-'+json.jobId+'-'+json.jobType+'-'+json.otherJobType+'</span><i></i></h4></a>';
               			}else if(json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobType == 'Other' &&  json.otherJobType != '' && json.otherJobType != null && json.otherJobType != 'undefined'){
              			 result = result+'<a href="#" onClick="openTopDiv();"><h4 id="id1"><span title="'+json.jobTitle+'-'+json.jobType+'-'+json.otherJobType+'">'+json.jobTitle+'-'+json.jobType+'-'+json.otherJobType+'</span><i></i></h4></a>';
               			}else if(json.jobType != '' && json.jobType != null && json.jobType != 'undefined' && json.jobType == 'Other'){
                 		result = result+'<a href="#" onClick="openTopDiv();"><h4 id="id1"><span title="'+json.jobTitle+'-'+json.jobType+'">'+json.jobTitle+'-'+json.jobType+'</span><i></i></h4></a>';
               			}else{
               				result = result+'<a href="#" onClick="openTopDiv();"><h4 id="id1"><span title="'+json.jobTitle+'">'+json.jobTitle+'</span><i></i></h4></a>';

               			}
				        result = result+'<div class="hide-row">';
				       /*result = result+'    <div class="profile_details requirement_details clearfix">'; */
				        result = result+'<div class="col-md-12">';
				        result = result+'<div class="row">';
				        result = result+'<div class="col-md-3">';
				        result = result+'<div class="company-logo">';
				        result = result+'<div class="imageParent"> <img onerror="predefineOrgImage(this);" src="${sourceleadUrl}/displayOrgImage/'+json.orgLocationId.org.orgCode+'" alt="Company" title="Company Logo" class="img-responsive"> </div>';
				        result = result+'<span class="hidden"> TATA Consulting Private Limited, Hyderebad. </span> </div>';
				        result = result+'</div>';
				        result = result+'<div class="col-md-9">';
				        result = result+'<div class="canDetails clearfix">';
				        result = result+'<div class="row">';
				        result = result+'<div class="col-md-5">';
				        result = result+'<ul class="list-unstyled can-perdt">';
				        
				        if(json.maxExp != null && json.maxExp != '' && json.maxExp !='undefined' && json.minExp != null && json.minExp != '' && json.minExp != 'undefined')
			        	{
			       			result = result+'<li><i class="fa fa-black-tie" title="Experience"></i><span title="'+json.minExp+'-'+json.maxExp+'"> '+json.minExp+'-'+json.maxExp+' years</span></li>';
			        	}else if(json.maxExp != null && json.maxExp != '' && json.maxExp !='undefined'){
			        		result = result+'<li><i class="fa fa-black-tie" title="Experience"></i><span title="'+json.maxExp+'"> '+json.maxExp+' years</span></li>';
			        	}else if(json.minExp != null && json.minExp != '' && json.minExp != 'undefined'){
			        		result = result+'<li><i class="fa fa-black-tie" title="Experience"></i><span title="'+json.minExp+'"> '+json.minExp+' years</span></li>';
			        	}else{
				   			result = result+'<li><i class="fa fa-black-tie" title="Experience"></i><span> -- </span></li>';
			        	}
			        
			        if(json.currencyCode == 'INR' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined' && json.maxRate != '' && json.maxRate != null && json.maxRate != 'undefined')
			    	   {
			        result = result+' <li><i class="fa fa-rupee" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' - '+json.maxRate+' / '+json.rateType+'</span></li>';
			    	   }
			        else if(json.currencyCode == 'INR' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined'){
				    result = result+' <li><i class="fa fa-rupee" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' / '+json.rateType+'</span></li>';
			        }
			       else if(json.currencyCode  == 'USD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined' && json.maxRate != '' && json.maxRate != null && json.maxRate != 'undefined'){
				   result = result+'<li><i class="fa fa-dollar" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' - '+json.maxRate+' / '+json.rateType+'</span></li>';

			       }
			       else if(json.currencyCode == 'USD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined'){
					    result = result+' <li><i class="fa fa-rupee" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' / '+json.rateType+'</span></li>';
				        }
			       else if(json.currencyCode  == 'AUD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rate != null && json.rate != 'undefined' && json.maxRate != '' && json.maxRate != null && json.maxRate != 'undefined'){
				   result = result+'<li><i class="fa fa-dollar" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' - '+json.maxRate+' / '+json.rateType+'</span></li>';
			       }
			       else if(json.currencyCode == 'AUD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined'){
					    result = result+' <li><i class="fa fa-rupee" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' / '+json.rateType+'</span></li>';
				        }
			       else if(json.currencyCode  == 'SGD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined' && json.maxRate != '' && json.maxRate != null && json.maxRate != 'undefined'){
				   result = result+'<li><i class="fa fa-dollar" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' - '+json.maxRate+' / '+json.rateType+'</span></li>';

			       }
			       else if(json.currencyCode == 'SGD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined'){
					    result = result+' <li><i class="fa fa-rupee" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' / '+json.rateType+'</span></li>';
				        }
			       else if(json.currencyCode  == 'EUR' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined' && json.maxRate != '' && json.maxRate != null && json.maxRate != 'undefined'){
				   result = result+'<li><i class="fa fa-eur" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' - '+json.maxRate+' / '+json.rateType+'</span></li>';

			       }
			       else if(json.currencyCode == 'EUR' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined'){
					    result = result+' <li><i class="fa fa-rupee" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' / '+json.rateType+'</span></li>';
				        }
			       else if(json.currencyCode  == 'GBP' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined' && json.maxRate != '' && json.maxRate != null && json.maxRate != 'undefined'){
				   result = result+'<li><i class="fa fa-gbp" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' - '+json.maxRate+' / '+json.rateType+'</span></li>';

			       }
			       else if(json.currencyCode == 'GBP' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' &&json.rate != null && json.rate != 'undefined'){
					    result = result+' <li><i class="fa fa-rupee" title="Job Rate"></i><span>'+json.currencyCode+' '+json.rate+' / '+json.rateType+'</span></li>';
				        }
			       else{
				   result = result+'<li><i class="fa fa-money" title="Job Rate"></i><span> --- </span></li>';

			       }
			       
			        if(json.state != null && json.state !='' && json.state != 'undefined' && json.city !=null && json.city !='' && json.city != 'undefined')
			        	{
			        result = result+'<li><i class="fa fa-map-marker" title="Location"></i><span title="'+json.city+', '+json.state+', '+json.country+'">'+json.city+', '+json.state+', '+json.country+'</span></li>';
			        	}
			        else if(json.state != null && json.state !='' && json.state != 'undefined')
			        	{
				    result = result+' <li><i class="fa fa-map-marker" title="Location"></i><span title="'+json.state+', '+json.country+'">'+json.state+', '+json.country+'</span></li>';

			        	}
			        else{
				     result = result+' <li><i class="fa fa-map-marker" title="Location"></i><span title="'+json.country+'">'+json.country+'</span></li>';
			        }if(json.travelAllowance != null && json.travelAllowance !=" " && json.travelAllowance != 'undefined'){
			        result = result+' <li><i class="fa fa-cab" title="Travel allowance"></i><span title="'+json.travelAllowance+'">'+json.travelAllowance+'</span></li>';
			        }else{
				    result = result+' <li><i class="fa fa-cab" title="Travel allowance"></i><span> --- </span></li>';
			        }
			        result = result+'</ul>';
			        result = result+'</div>';
			        result = result+'<div class="col-md-7">';
			        result = result+'<ul class="list-unstyled can-perdt">';
			        if(json.manager != null && json.manager != '' && json.manager != 'undefined'){
			        result = result+'<li> <i class="fa fa-user" title="Manager"></i><span id="canName">'+json.manager+'</span></li>';
			        }
			        else{
			        	 result = result+'<li> <i class="fa fa-user" title="Manager"></i><span id="canName"> --- </span></li>';	
			        }
			        var mydate = json.requirementCreatedDateToString;
			        /* var str = mydate.split("-");
			        var strFormatedDate = (str[2]+"-"+str[1]+"-"+str[0]); */
			        result = result+'<li><i class="fa fa-clock-o" title="Job Posted Date"></i><span>'+mydate+'</span></li>';
			        
			        if(json.closedDateStringFormat != null && json.closedDateStringFormat != '' && json.closedDateStringFormat != 'undefined'){
				        result = result+'<li><i class="fa fa-clock-o" title="Job Closed Date"></i><span>'+json.closedDateStringFormat+'</span>';
				        }
				        else{
					    result = result+'<li><i class="fa fa-clock-o" title="Job Closed Date"></i><span> --- </span>';
				        }
			        
			        
			        if((json.projectDurationMonths != null && json.projectDurationMonths != '' && json.projectDurationMonths != 'undefined') && (json.projectDurationYears != null && json.projectDurationYears != '' && json.projectDurationYears != 'undefined')){
				        result = result+'<li><i class="fa fa-clock-o" title="ProjectDuration"></i><span>'+json.projectDurationYears+' Years '+json.projectDurationMonths+' Months</span>';
				        }
			        
			        
			        else  if(json.projectDurationYears != null && json.projectDurationYears != '' && json.projectDurationYears != 'undefined'){
				        result = result+'<li><i class="fa fa-clock-o" title="ProjectDuration"></i><span>'+json.projectDurationYears+' Years</span>';
				        }
			        else  if(json.projectDurationMonths != null && json.projectDurationMonths != '' && json.projectDurationMonths != 'undefined'){
				        result = result+'<li><i class="fa fa-clock-o" title="ProjectDuration"></i><span>'+json.projectDurationMonths+' Months</span>';
				        }
				        else{
					    result = result+'<li><i class="fa fa-clock-o" title="ProjectDuration"></i><span> --- </span>';
				        }
			        
				        if(json.priority != null && json.priority != '' && json.priority != 'undefined'){
				        result = result+'<li><i class="fa fa-flag" title="Priority"></i><span>'+json.priority+'</span>';
				        }
				        else{
					    result = result+'<li><i class="fa fa-flag" title="Priority"></i><span> --- </span>';
				        }
				        if(json.noticePeriod != null && json.noticePeriod != '' && json.noticePeriod != 'undefined'){
					        var tempNoticePeriod = json.noticePeriod;
					        var strNoticeArray = tempNoticePeriod.split(", ");
					        var arrNotice = new Array();
					        for(var i=0; i<strNoticeArray.length; i++){
					        	arrNotice[i] = strNoticeArray[i] +" ";
					        }
					        result = result+'<li class="noticePeriod"><i class="fa fa-bell" title="Notice Period"></i>'+arrNotice+'</li>';
				        }
					    else{
						result = result+'<li><i class="fa fa-bell" title="Notice Period"></i><span> --- </span>';
					    }
				         result = result+'</li>';
				        result = result+'</ul>';
				        result = result+'</div>';
				        result = result+'</div>';
				        result = result+'</div>';
				        result = result+'<div class="pro-detials">';
				        result = result+'<div class="professinal-details">';
				        result = result+'<ul class="list-inline list-unstyled">';
				        var skills = [];
			           if(skills != null && skills != '' && skills != 'undefined'){
			        	   skills = json.skiills.split(",");
					        result = result+'                <li>'+skills[0]+'</li>';
					        result = result+'                <li>'+skills[1]+'</liall>';
					        result = result+'                <li>'+skills[2]+'</li>';
					        result = result+'                <li>'+skills[3]+'</li>';
					        result = result+'                <li>'+skills[4]+'</li>';
					        result = result+'              </ul>';
			           }
				        result = result+'            </div>';
				        result = result+'          </div>';
				        result = result+'        </div>';
				        result = result+'      </div>';
				        result = result+'    </div>';
				        result = result+'  </div>';
				        result = result+'</div>';
				        
					    result = result+'  <div class="proj_close"></div>';
					    result = result+'  <div class="project_side_wrap_inn_tabs">';
					    result = result+'    <ul class="project-tabs-menu">';
					    result = result+'      <li class="'+isDetailsTabActive+'" id="detailsTab"><a href="#tab-1" onclick="sideBar('+requirementId+');">Details</a></li>';
					   
					    /* if(json.requirementSubmissionStatus != 'FILLEDCLOSED'){
                        var all = 'all';
                        '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\',\'ROLE_VIEW_APPLICANT\',\'ROLE_ADD_INTERVIEW\',\'ROLE_VIEW_INTERVIEW\',\'ROLE_VIEW_CANCEL_INTERVIEW\',\'ROLE_RATE_NEGOTIATION\',\'ROLE_SHORTLIST\',\'ROLE_SUBMIT_TO_CLIENT\',\'ROLE_CREAE_OFFER\',\'ROLE_VIEW_OFFER\',\'ROLE_VIEW_ACCEPT_OFFER\',\'ROLE_VIEW_REJECT_OFFER\')">';
				    	result = result+'      <li class="'+isApplicantsTabActive+'" id="applicantsTab"><a href="#tab-2" onclick="requirementPipeline('+requirementId+',\''+all+'\');requirementStatusBar('+requirementId+');">Applicants</a></li>';
				    	'</sec:authorize>';
				    	'<sec:authorize access="hasAnyRole(\'ROLE_RECOMMENDATIONS\')">'; 
                         result = result+'      <li id="recommendationsTab"><a href="#tab-3" onclick="requirementRecommendations('+requirementId+');">Recommendations</a></li>';
                         '</sec:authorize>';
                    } */if(json.requirementSubmissionStatus == 'CLOSED' && json.isreceivingApplicants == "Y"){
                    	 var all = 'all'; 
                    	'<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\',\'ROLE_VIEW_APPLICANT\',\'ROLE_ADD_INTERVIEW\',\'ROLE_VIEW_INTERVIEW\',\'ROLE_VIEW_CANCEL_INTERVIEW\',\'ROLE_RATE_NEGOTIATION\',\'ROLE_SHORTLIST\',\'ROLE_SUBMIT_TO_CLIENT\',\'ROLE_CREAE_OFFER\',\'ROLE_VIEW_OFFER\',\'ROLE_VIEW_ACCEPT_OFFER\',\'ROLE_VIEW_REJECT_OFFER\')">';
					    	result = result+'      <li><a href="#tab-2" onclick="requirementPipeline('+requirementId+',\''+all+'\');requirementStatusBar('+requirementId+');">Applicants</a></li>';
					    	'</sec:authorize>';
					    	'<sec:authorize access="hasAnyRole(\'ROLE_RECOMMENDATIONS\')">'; 
                          result = result+'      <li><a href="#tab-3" onclick="requirementRecommendations('+requirementId+');">Recommendations</a></li>';
                          '</sec:authorize>';
                    }else{
                    	var all = 'all';
                        '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\',\'ROLE_VIEW_APPLICANT\',\'ROLE_ADD_INTERVIEW\',\'ROLE_VIEW_INTERVIEW\',\'ROLE_VIEW_CANCEL_INTERVIEW\',\'ROLE_RATE_NEGOTIATION\',\'ROLE_SHORTLIST\',\'ROLE_SUBMIT_TO_CLIENT\',\'ROLE_CREAE_OFFER\',\'ROLE_VIEW_OFFER\',\'ROLE_VIEW_ACCEPT_OFFER\',\'ROLE_VIEW_REJECT_OFFER\')">';
				    	result = result+'      <li class="'+isApplicantsTabActive+'" id="applicantsTab"><a href="#tab-2" onclick="requirementPipeline('+requirementId+',\''+all+'\');requirementStatusBar('+requirementId+');">Applicants</a></li>';
				    	'</sec:authorize>';
				    	'<sec:authorize access="hasAnyRole(\'ROLE_RECOMMENDATIONS\')">'; 
                         result = result+'      <li id="recommendationsTab"><a href="#tab-3" onclick="requirementRecommendations('+requirementId+');">Recommendations</a></li>';
                         '</sec:authorize>';	
                    }
					    result = result+'      <li><a href="#tab-4" onclick="requirementNotes('+requirementId+');">Notes</a></li>';

					    result = result+'      <div class="clearfix"></div>';
					    result = result+'    </ul>';
					    result = result+'    <div class="project_tabs">';
						    result = result+' <div id="tab-1" class="project_tabs_inn">';
						    result = result+'</div>';
						    result = result +'<div id="detailsLoading"></div>';
						    result = result+' <div id="tab-2" class="project_tabs_inn">';
						    result = result+'</div>';
						    result = result +'<div id="pipelineLoading"></div>';
						    result = result +'<div id="status-div"></div>';
						    
						    //#####################  actions div @prasad salina ################################################
						    //For Add Applicant div
							result = result + '<div class="new_side_wrap">';
							result = result + '<div class="new_side_wrap_inn">';
							result = result + '<div class="new_side_wrap_cta">';
							result = result +'<div id="addApplicantDiv"></div>';
							result = result +'</div>';
							result = result +'</div>';
							result = result +'</div>';
							
							//For Add Interview Div
							result = result + '<div class="add_inter_wrap">';
							result = result +      '<div class="new_side_wrap_inn">';
							result = result +              '<div id="addInterviewDiv"></div>';
							result = result +       '</div>';
							result = result +'</div>'; 
							//end Add Interview Div
							
							//For Add Offer Div
							result = result + '<div class="add_offer_wrap">';
							result = result +      '<div class="new_side_wrap_inn">';
							result = result +              '<div id="addOfferDiv"></div>';
							result = result +       '</div>';
							result = result +'</div>'; 
							//end Add Offer Div  
							
							
							//For Submit to Client Div
							result = result +'<div class="submitnew-wrap">';
							result = result +' <div class="new_side_wrap_inn">';
							result = result +'<div class="add_inter">';
							result = result + '<div id="submitToClientDiv"></div>';
							result = result + '<div id="emailTemplateDiv"></div>';
							result = result +'</div>';
							result = result +'</div>';
							result = result +'</div>';
							//For Submit to Client Div
							
							result = result +'<div class="shortlist-wrap">';
							result = result +'<div class="new_side_wrap_inn">';
							result = result +'<divsubStatus class="add_inter">';
							result = result + '<div id="shortlistDiv"></div>';
							result = result +'</div>';
							result = result +'</div>';
							result = result +'</div>';
							
                             //Candidate submissions details Div
							 result = result +' <div class="clients_side_wrap quickviewTabs">';
							 
							 result = result + '<ul class="nav nav-tabs">';
							 result = result +' <li class="active" id="pipelineQuickviewTabsqv"><a data-toggle="tab" href="#quickview">Quickview</a></li>';
							 result = result +'<li id="pipelineQuickviewTabsdetails"><a data-toggle="tab" id="detailedView" href="#detailview">Detailed View</a></li>';
							 result = result +'</ul>';
							 
							 
						     result = result +'<div class="tab-content">';
						     result = result +'<div id="quickLoading"></div>';
							 result = result +'<div id="quickview" class="tab-pane fade in active">';
							 result = result +'<div id="applicantQuickViewDiv"></div>';
							 
							 result = result +'</div>';
							 
							 result = result +'<div id="detailViewLoading"></div>';
							 result = result +'<div id="detailview" class="tab-pane fade">';
							
							 result = result +'<div class="clients_side_wrap_inn">';
							 result = result +' <div class="project_side_wrap_inn_tabs"> ';
							 result = result + '<div id="viewCandidateDiv"></div>';
							 result = result +'</div>';
							 result = result +'</div>';
							 
							 result = result +'</div>';
							 
							 result = result +'</div>';
							 result = result +'</div>';
							 
							 var submitToClient = '';
						     if(clientSubmissions == 'Y' && isSpocActive == 'Y'){
	           				    submitToClient = '<a href="javascript:void(0)" title="" onclick="submitToClient('+requirementId+');" class="submitTo"><i class="fa fa-plus"></i>&nbsp;Submit to client</a>';
	           			     }
							
				            result = result +'<div id="actionsDiv" class="timesheet_head timesheetHead minHeight candidateHead recruitmentHead headersummary clearfix zindex" style="display: none;">';
				            result = result +'  <ul>';
				            result = result +'    <li class="li-vendor">';
				            if(json.requirementSubmissionStatus != 'SUBMITTED' && json.requirementSubmissionStatus != 'CLOSED' && json.requirementSubmissionStatus != 'REJECTED' &&  json.requirementSubmissionStatus != 'CANCEL' && json.requirementSubmissionStatus != 'DRAFT' && json.requirementSubmissionStatus != 'ONHOLD' && json.requirementSubmissionStatus != 'FILLEDCLOSED'  && json.requirementSubmissionStatus != 'UNFILLEDCLOSED'){
				            result = result +' <li ><a onclick="getActions();" href="javascript:void(0)" class="action"> Action <i  class="fa fa-caret-down" ></i></a>';
				            }
				            result = result +'<div class="action-pop">';
				            result = result +'	<span class="width175">';
				            '<sec:authorize access="hasAnyRole(\'ROLE_ADD_APPLICANT\')">';
				            	result = result +'	<a href="javascript:void(0)" title="" onclick="getAddApplicant('+requirementId+');" class="add-applicant"><i class="fa fa-plus"></i>&nbsp;Add Applicant</a>';
				            '</sec:authorize>';
				            '<sec:authorize access="hasAnyRole(\'ROLE_SHORTLIST\')">';
				            	result = result +'    <a href="javascript:void(0)" title="" onclick="candidateShortList('+requirementId+');" class="shortlistClick"><i class="fa fa-plus"></i>&nbsp;Shortlist</a>';
				            '</sec:authorize>';
				            '<sec:authorize access="hasAnyRole(\'ROLE_SUBMIT_TO_CLIENT\')">';
				            	result = result + submitToClient;
				            '</sec:authorize>';
				            '<sec:authorize access="hasAnyRole(\'ROLE_ADD_INTERVIEW\')">';
				            	result = result +'   <a href="javascript:void(0)" title="" onclick="addInterview('+requirementId+');" class="add-interview"><i class="fa fa-plus"></i>&nbsp;Schedule Interview</a>';
				            '</sec:authorize>';
				            '<sec:authorize access="hasAnyRole(\'ROLE_CREAE_OFFER\')">';
				            	result = result +'    <a href="javascript:void(0)" title=""  onclick="addOffer('+requirementId+');" class="add-offer"><i class="fa fa-plus"></i>&nbsp;Generate Offer</a>';
				            '</sec:authorize>';
				            result = result +'    </span>';
				            result = result +' </div>';	
				            result = result +' </li>';
				            
				            
				           result = result + '<div class="clearfix"></div>';
				           result = result + '</div>';
				           //#####################  actions div @prasad salina ################################################
						    
						    
							result = result +'<div id="status-data" class="datatable_filter"></div>';
						   
						    result = result+'<div id="tab-3" class="project_tabs_inn overflow-data datatable_filter">';
						    
						    result = result+'</div> ';
						    result = result +'<div id="recommendationsLoading"></div>'; 
						    
 							result = result+'<div id="tab-4" class="project_tabs_inn overflow-data datatable_filter">';
						    
						    result = result+'</div> ';
						    
					    result = result+'</div>';
					    result = result+'</div>';
					    
			             $("#requirementSideDiv").empty();  
			 		     $("#requirementSideDiv").append(result);  
			 		     
			 		    if(type != null && type != '' && type != 'undefined'){
			 		    	 $("#actionsDiv" ).show();
							 if(type == 'SHORTLIST'){
								    filterReqSL(requirementId,type);
									requirementStatusBar(requirementId);
									tabEnable('','shortListli');
							 }
							 else if(type == 'SUBMIT_TO_CLIENT'){
								    filterReqSC(requirementId,type);
									requirementStatusBar(requirementId);
									tabEnable('','submitToClientli');
							 }
							 else if(type == 'INTERVIEW'){
								 filterReqIN(requirementId,type);
									requirementStatusBar(requirementId);
									tabEnable('','interviewli');
							 }
							 else if(type == 'OFFERMADE'){
								 filterReqOffer(requirementId,type);
									requirementStatusBar(requirementId);
									tabEnable('','offerMadeli');
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
			 		    }else{
			 		    	requirementDetails(requirementId,data);
			 		    }
			 		    
			 		     $("#detailsLoading").removeAttr("style");
                }else{
               	 $("#requirementSideDiv").removeAttr("style");
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
					$("#requirementSideDiv").empty();
				    $("#requirementSideDiv").append(result); 
                }
         },
           
      }); 
				
}
</script>

<script type="text/javascript">
function activeTab(){
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
		$(imageId).attr('src','resources/images/office-image.jpg');
	}
</script>


<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
function requirementDetails(requirementId,data){ 
	$("#actionsDiv" ).hide();
	  var sourceleadUrl = '${sourceleadUrl}';
	  
	  var isLoginUserAsTeamMember = '${isTeamMember}';
	  var isLoginUserAsManager = '${isManager}';
	  var isLoginUserAsAdmin = '${isAdmin}';
	  var requirementUUID = '${requirementUUID}'
	  
	  $("#detailsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  
	   if(data != null && data != 'undefined' && data != ""){   
        	var json = JSON.parse(data);
						var result ="";
			            result = result +' <div class="require_wrap_details">';
			           
			            
			            if(json.requirementSubmissionStatus == 'REJECTED'){
			            	result = result +'   <div class="anchor-inline">';
			            	result = result +'   <div class="rejected_Reason">';
			            	result = result +'   <h5>Rejected Reason:</h5>'; 
			            	if(json.rejectReasonForRequirement != null && json.rejectReasonForRequirement != "undefined"){
				            	result = result +'   <div class="mb">'+json.rejectReasonForRequirement+'</div>';
				            	}else{
					            	result = result +'   <div class="mb"> No Data</div>';
				            	}
			            	result = result +'   </div>';
			            	result = result +'   </div>';
			          	   }
			            
			            
			            result = result+'<div id="rejectRequirementAlert" ></div>  ';
			            
			           
			            result = result +' <div class="clearfix">';
			            
			            result = result +' <div class="pull-left">';
			            result = result +' <h2>Assigned Members</h2>'; 
			            result = result +' </div>';
			            
			            result = result +' <div class="pull-right">';
			            
			            if( (json.requirementSubmissionStatus =='NEW'  || json.requirementSubmissionStatus =='DRAFT' || json.requirementSubmissionStatus =='SUBMITTED'  || json.requirementSubmissionStatus  == "APPROVED"  || json.requirementSubmissionStatus =='REJECTED'  ||  json.requirementSubmissionStatus  == "OPEN" ||  json.requirementSubmissionStatus  == "ONHOLD" ) && (isLoginUserAsAdmin == "true" || json.isLoginUserAsApprover  == "true" || isLoginUserAsManager  == "true" || json.isLoginUserAsInitiator == "true") ){
			            	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_REQUIREMENT\')">'; 
			            		result = result +' <a href="<c:url value="/requirement?requirementUUID='+requirementUUID+'"/>"><button  class="btn btn-primary btn-sm" type="button">Edit</button></a>';
			            	'</sec:authorize>';
			            }
			            var APPROVED = "APPROVED";     
			            var REJECTED = "REJECTED";

			             if((isLoginUserAsAdmin == "true" && json.requirementSubmissionStatus  !='DRAFT' && json.requirementSubmissionStatus != 'ONHOLD'  && json.requirementSubmissionStatus != 'CANCEL' &&  json.requirementSubmissionStatus !='APPROVED' &&  json.requirementSubmissionStatus != 'REJECTED'  &&  json.requirementSubmissionStatus != 'OPEN' && json.requirementSubmissionStatus  !='NEW' && json.requirementSubmissionStatus  !='UNFILLEDCLOSED' && json.requirementSubmissionStatus  !='FILLEDCLOSED' )  || (json.requirementSubmissionStatus  !='DRAFT' && json.requirementSubmissionStatus != 'ONHOLD'  && json.requirementSubmissionStatus != 'CANCEL' &&  json.requirementApproverHierarchyStatus !='APPROVED' &&  json.requirementApproverHierarchyStatus != 'REJECTED'  &&  json.requirementApproverHierarchyStatus != 'OPEN'  && json.requirementApproverHierarchyStatus !='' && json.requirementApproverHierarchyStatus !='undefined' && json.requirementApproverHierarchyStatus !=null )){
         					 
			            	 result = result +'<button  class="btn btn-success mr ml btn-sm" type="button" id = "approverbut" onClick ="updateReuqirementStatus(\''+ APPROVED+ '\');"  >Approve</button>';
         		             result = result +'<button  class="btn btn-danger btn-sm" type="button" onClick ="rejectRequirementAlertMessage(\''+ REJECTED+ '\');">Reject</button>';
				           	 }
			             
			             
			             result = result +'     </div>';
			             result = result +'     </div>';
			            
			             result = result +'     <div class="clearfix">';
			             
			            result = result +'     <div class="clearfix row">';
				        result = result +'     <div class="col-md-12 divskill divsAssigned pull-left">';
				        
				        result = result + '<div id="assignedMemberList"></div>';
				        
			            result = result +'   <h1>Description:</h1>';
			            if(json.description != null && json.description != '' && json.description != 'undefined'){
				           	 result = result +'   <div class="RDdetails">'+json.description+'</div>';
				         }
				         else{
				           	 result = result +'   <div class="RDdetails"> NO DATA</div>';
			    	        }
			            
			            result = result +'   <h1>Skills:</h1>';
			            if(json.skills != null && json.skills != '' && json.skills != 'undefined'){
				            result = result +'<div class="RDdetails">'+json.skills+'</div>';
				            }
				            else{
				            result = result +'<div class="RDdetails"> NO Skills</div>';	
				            }
			            
			            
			            result = result +'   <h2 class="RPadT"> Job Details:</h2>';
			            result = result +'   <div class="require_one">';
			            if(json.positions != null && json.positions != '' && json.positions != 'undefined'){
			            result = result +'     <div class="require_wrap_details_cta"> <b>No. of positions:</b><span>'+json.positions+'</span>';
			            }
			            else{
			            result = result +'<div class="require_wrap_details_cta"> <b>No. of positions:</b> <span> -- </span>';	
			            }
			            result = result +'       <div class="clearfix"></div>';
			            result = result +'     </div>';
			            if(json.industryName != null && json.industryName != '' && json.industryName != 'undefined'){
				            result = result +'     <div class="require_wrap_details_cta"> <b>Industry:</b><span>'+json.industryName+'</span>';
				            }
				            else{
				            result = result +'<div class="require_wrap_details_cta"> <b>Industry:</b> <span> -- </span>';	
				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
			            if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
				            	result = result +'<div class="require_wrap_details_cta require_wrap_details_view require_uploadfile "><b>Attachment:</b><span>'+json.fileName+'</span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadRequirementAttachment?requirementId='+json.id+'\"></a><a class="fa fa-eye ml10" onclick="viewJobSummaryDocument(\''+json.id+'\',\''+json.fileName+'\')"></a> ';
				            }
				            else{
				            result = result +'<div class="require_wrap_details_cta"> <b>Attachment:</b> <span> -- </span>';	
				            }				            
			            result = result +'       <div class="clearfix"></div>';
			            result = result +'     </div>';
			            
			            if(json.education != null && json.education != '' && json.education != 'undefined'){
				            result = result +'<div class="require_wrap_details_cta"> <b>Education:</b><span>'+json.education+'</span>';
				            }else{
				           	result = result +'<div class="require_wrap_details_cta "> <b>Education:</b> <span> -- </span>';
				            }
			            result = result +'       <div class="clearfix"></div>';
			            result = result +'     </div>';
			            
			            if(json.reqCoolingOffPeriod != null && json.reqCoolingOffPeriod != '' && json.reqCoolingOffPeriod != 'undefined'){
				            result = result +'<div class="require_wrap_details_cta"> <b>Cooling Off Period:</b>'+json.reqCoolingOffPeriod+'';
				            }else{
				           	result = result +'<div class="require_wrap_details_cta "> <b>Cooling Off Period:</b> <span> -- </span>';
				            }
			            result = result +'       <div class="clearfix"></div>';
			            result = result +'     </div>';
			           
			            result = result +'     <div class="require_wrap_details_cta"> <b>Created By:</b>'+json.createdByName+'';
			            result = result +'     <div class="clearfix"></div>';
			            result = result +'   </div>';
			            if(json.visaType != null && json.visaType != '' && json.visaType != 'undefined'){
			            result = result +'     <div class="require_wrap_details_cta"> <b>Visa:</b><span>'+json.visaType+'</span>';
			            }else{
			            	result = result +'     <div class="require_wrap_details_cta"> <b>Visa:</b><span>--</span>';
			            	}
			            
			            result = result +'     <div class="clearfix"></div>';
			            result = result +'   </div>';				            
			            
			            result = result +'     <div class="clearfix"></div>';
			            result = result +'     </div>';

			            if(json.isClientJob  == 'Y')
			            	{
			            	
			            result = result +'   <h2>Client Details:</h2>';
			            result = result +'   <div class="require_one">';
			            
			            if(json.spocName != '' && json.spocName !=null && json.spocName != 'undefined'){
			            	
			            result = result +'     <div class="require_wrap_details_cta"> <b>Client Contact:</b> <span class="pull-left"> <span>'+json.spocName+'</span>';
			            }
			            else{
				        result = result +'     <div class="require_wrap_details_cta "> <b>Client contact:</b> <span class="pull-left"> -- </span>';

			            }
			            result = result +'       <div class="clearfix"></div>';
			            result = result +'     </div>';
			            if(json.clientName != '' && json.clientName !=null && json.clientName != 'undefined'){
				        result = result +'     <div class="require_wrap_details_cta"> <b>Client Name:</b> <span class="pull-left"> '+json.clientName+' </span>';
			            }
			            else{
					        result = result +'     <div class="require_wrap_details_cta "> <b>Client Name:</b> <span class="pull-left"> ---  </span>';
			            }
			            result = result +'       <div class="clearfix"></div>';
			            result = result +'     </div>'; 
			            
			            
			            if(json.parentRequirement == null || json.parentRequirement == "" || json.parentRequirement == "undefined"){
				        	 if(json.clientCurrencyCode == 'INR' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined' && null !=json.clientRate && null !=json.clientRateType && json.clientRateType != 'undefined' && json.clientRateType != ''){
					        result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i> ' +json.clientRate+'  '+json.clientRateType+'</span>';
						    }
						    else if(json.clientCurrencyCode == 'EUR' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined' && null !=json.clientRate && null !=json.clientRateType && json.clientRateType != 'undefined' && json.clientRateType != ''){

						    result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-eur"></i>' +json.clientRate+'  '+json.clientRateType+'</span>';
							}
						    else if(json.clientCurrencyCode == 'GBP' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined' && null !=json.clientRate && null !=json.clientRateType && json.clientRateType != 'undefined' && json.clientRateType != ''){

							result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-gbp"></i>' +json.clientRate+'  '+json.clientRateType+'</span>';
							}
						    else if(json.clientCurrencyCode  == 'USD' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined' && null !=json.clientRate && null !=json.clientRateType && json.clientRateType != 'undefined' && json.clientRateType != ''){
							result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i>' +json.clientRate+'  '+json.clientRateType+'</span>';
							}
						    else if(json.clientCurrencyCode  == 'AUD'  && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' &&json.clientRate != '' && json.clientRate != 'undefined' && null !=json.clientRate && null !=json.clientRateType && json.clientRateType != 'undefined' && json.clientRateType != ''){
							result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i>' +json.clientRate+'  '+json.clientRateType+'</span>';
	
						    }
						    else if(json.clientCurrencyCode  == 'SGD' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined' && null !=json.clientRate && null !=json.clientRateType && json.clientRateType != 'undefined' && json.clientRateType != ''){
							result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i>' +json.clientRate+'  '+json.clientRateType+'</span>';
	
						    }
						    else{
							result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span> --- </span>';
						    }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				          }else{
				        	  if(json.currencyCode == 'INR' && json.currencyCode  !='' && json.currencyCode  !='undefined' &&  json.rate != '' && json.rate != 'undefined' && json.rate != null){
							        result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i> ' +json.rate+'  '+json.rateType+'</span>';
								    }
								    else if(json.currencyCode == 'EUR' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rate != 'undefined'){

								    result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-eur"></i>' +json.rate+'  '+json.rateType+'</span>';
									}
								    else if(json.currencyCode == 'GBP' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rate != 'undefined'){

									result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-gbp"></i>' +json.rate+'  '+json.rateType+'</span>';
									}
								    else if(json.currencyCode  == 'USD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rate != 'undefined'){
									result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i>' +json.rate+'  '+json.rateType+'</span>';
									}
								    else if(json.currencyCode  == 'AUD'  && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rate != 'undefined'){
									result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i>' +json.rate+'  '+json.rateType+'</span>';
			
								    }
								    else if(json.currencyCode  == 'SGD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rate != 'undefined'){
									result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i>' +json.rate+'  '+json.rateType+'</span>';
			
								    }
								    else{
									result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span> --- </span>';
								    }
						            result = result +'       <div class="clearfix"></div>';
						            result = result +'     </div>';
				          }
			            if(json.clientSubmissionTemplate != '' && json.clientSubmissionTemplate != null && json.clientSubmissionTemplate != 'undefined'){
				            result = result +'     <div class="require_wrap_details_cta"> <b>Submission Form:</b>'+json.clientSubmissionTemplate+'';
				            }
				            else{
					        result = result +'     <div class="require_wrap_details_cta"> <b>Submission Form:</b> <span> --- </span>';
	
				            }				            
			            result = result +'       <div class="clearfix"></div>';
			            result = result +'     </div>';
			            	}
			            
			            
			            result = result +'     <div class="clearfix"></div>';
			          
			           
			            
			            
			            
			            result = result +'   <h2>Internal Details:</h2>';
			            result = result +'   <div class="require_one">';
			            //result = result +'     <div class="require_wrap_details_cta"> <b>Manager:</b> <span>Manager</span>';
			            if(json.isClientJob  == 'N'){
			            if(json.clientSubmissionTemplate != '' && json.clientSubmissionTemplate != null && json.clientSubmissionTemplate != 'undefined'){
				            result = result +'     <div class="require_wrap_details_cta"> <b>Application form:</b>'+json.clientSubmissionTemplate+'';
				            }
				            else{
					        result = result +'     <div class="require_wrap_details_cta"> <b>Application form:</b> <span> --- </span>';
	
				            }
			            }
			               result = result +'</div>';
	          	           
				           result = result +'<div class="clearfix"></div>';
				           
				           if(json.releaseJobToAllUsers  == 'Y'){
						        result = result +'     <div class="require_wrap_details_cta"> <b>Release to all Users:</b><span> YES </span></div>';
				            }else{
						        result = result +'     <div class="require_wrap_details_cta"> <b>Release to all Users:</b><span> NO </span></div>';
				            }
			          	           
						   result = result +'<div class="clearfix"></div>';
			           	            
			            
			            //  Checking  for approval mechanism type  Parallel or Sequential
			            if(json.approvalMechanismType != null && json.approvalMechanismType !='' && json.approvalMechanismType !='undefined'){
			           
				            if(json.approvalMechanismType == 'PARALLEL'){
				            	result = result +'<div class="require_wrap_details_cta"> <b>Approvers:</b> <span>';
				            	for (var i=0; i<json.approverNames.length; i++)
				   				{
				            	    if(json.approverNames[i] == json.approverNames[0]){
				            	    	result = result +json.approverNames[i]; 
				            	    }else{
				            	    	result = result +","+json.approverNames[i]; 
				            	    }
				   				}
				            	result = result +'</span>';
				            	result = result +'</div>';
				            	
				            }else{
				            	for (var i=0; i<json.approverNames.length; i++)
				   				{
				            		var level = 1;
					            	for (var i=0; i<json.approverNames.length; i++)
					   				{
					            		result = result +'<div class="require_wrap_details_cta"> <b>Approvers '+level+':</b> <span>'; 
					            		result = result +json.approverNames[i];
					            		result = result +'</span>';
					            		result = result +'</div>';
					            		level = level+1;
					   				}
				   				}
				            }
			            }  
			            
				            result = result +'       <div class="clearfix"></div>';				            
				            result = result +'   </div>';							            
			            
			            result = result +'       <div class="clearfix"></div>';
			            result = result +'     </div>';
			            result = result +'     <div class="clearfix"></div>';
			            
			            result = result +'     </div>';	
			            result = result +'   </div>';
			            
			            result = result +'   </div>';
			            result = result +' </div>';
			            
						$("#tab-1").empty();
						$("#tab-2").empty();
						$("#tab-3").empty();
						$("#tab-4").empty();
						$("#status-div").empty();
					    $("#status-data").empty();
						$("#tab-1").append(result);
						
						assignedMemberList(requirementId);
						$("#tab-1").show();
						$(".project-tabs-menu a").click(function(event) {
						     event.preventDefault();
						     $(this).parent().addClass("current");
						     $(this).parent().siblings().removeClass("current");
						     var tab = $(this).attr("href");
						     $(".project_tabs_inn").not(tab).css("display", "none");
						     $(tab).fadeIn();
						 });
						$("#detailsLoading").removeAttr("style");
         }else{
               	 $("#requirementSideDiv").removeAttr("style");
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
					$("#requirementSideDiv").empty();
				    $("#requirementSideDiv").append(result); 
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

<%-- 
     @author : sarath kumar.
     this script is for update Requirement.
     1.usage     :  used in Requirement jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function updateReuqirementStatus(status){
	
	var sourceleadUrl = '${sourceleadUrl}';
	var requirementId = '${requirementId}';
	 var isLoginUserAsAdmin = '${isAdmin}';
	 
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
 	$.ajax({
      type : "POST",
      url: sourceleadUrl+"/requirementAcceptOrReject?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status+"&reason="+reason+"&isLoginUserAsAdmin="+isLoginUserAsAdmin, 
    		  success :function(data) {  
            if(data != null && data != 'undefined' && data != ""){  
          		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
          		 else if (data.indexOf('exception') >= 0) {
          			 location.replace(sourceleadUrl+"/errorPage");
          		 }
          		 else{ 
          			 $("#selector11").removeAttr("style");
           			 $("#selector11").css({ display: "block" });
          			 if(status == "APPROVED"){
          				$("#msgDiv").html("Requirement approved Successfully");
          			 }else{
          				$("#msgDiv").html("Requirement rejected Successfully");
          			   }
          			sideBar(requirementId)
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
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"	<div id='reasonDiv'/>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='updateReuqirementStatusReject(\""+ status+ "\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert12();'></b>";
	result = result +"	</div>";
	result = result +"	</div>";
	$("#rejectRequirementAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function updateReuqirementStatusReject(status){
	
	var sourceleadUrl = '${sourceleadUrl}';
	var requirementId = '${requirementId}';
	var isLoginUserAsAdmin = '${isAdmin}';
	 
	 var reason = document.getElementById("rejectReason").value;
	  if(reason.match(/^[^\s-](.*)*$/) != null){
	$('#reasonDiv').empty();
 	$.ajax({
      type : "POST",
      url: sourceleadUrl+"/requirementAcceptOrReject?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status+"&reason="+reason+"&isLoginUserAsAdmin="+isLoginUserAsAdmin, 
    		  success :function(data) {  
            if(data != null && data != 'undefined' && data != ""){  
          		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
          		 else if (data.indexOf('exception') >= 0) {
          			 location.replace(sourceleadUrl+"/errorPage");
          		 }
          		 else{ 
          			 $("#selector11").removeAttr("style");
           			 $("#selector11").css({ display: "block" });
          			 if(status == "APPROVED"){
          				$("#msgDiv").html("Requirement approved Successfully");
          			 }else{
          				$("#msgDiv").html("Requirement rejected Successfully");
          			   }
          			$("#rejectRequirementAlert").empty();
          			$(".project_inn_mask").fadeOut(200);
          			$(".remove_user_pop").hide();
          			sideBar(requirementId)
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
     this script is for Delete Candidate alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>



<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
function requirementPipeline(requirementId,submissionType){
	$("#actionsDiv" ).show();
	
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $("#pipelineLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  requirementPipelineListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  requirementPipelineListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  requirementPipelineListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  requirementPipelineListDate = 'YYYY-DD-MM'; 
	  }  
	
	  
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType,        
         success :function(data) {  
        	 var result =""; 
        	 if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		
            			 var submitToClient = '';
            			
            			   
							 result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
							 result = result +'<span class="applicant_export">';
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
				           result = result + '<div class="require_wrap_table overflow-data filter-position">';
				           result = result + '<div class="clerfix">';
				           result = result + '<table id="requirementsPagination" class="display" cellspacing="0" width="100%"">';
				           result = result + '<thead>';
				           result = result + '<tr>';
				           result = result + '<th>Name</th>';
				           result = result + '<th>Email</th>';
				           result = result + '<th>Ph. No</th>';
				           result = result + '<th>Resume</th>';
				           result = result + '<th>Applied on  <br>(<small class="custom-small">'+requirementPipelineListDate+'</small>)</th>';
					       result = result + '<th>Rate</th>';
					         if(clientSubmissions == 'Y' && submissionType == 'SUBMIT_TO_CLIENT'){
					           result = result + '<th>Sub. Rate</th>';
					         }
					       result = result + '<th>Source</th>';
		        		   result = result + '<th>Score</th>';
				           result = result + '<th>Exp</th>';
				           result = result + '<th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		var report = json[i];
			   	        		var colorHint = "";
			   	        		if(report.lastRateStage != null && report.lastRateIniLocId != null && report.lastRateIniType != null && report.lastRateStage != "undefined" && report.lastRateIniLocId != "undefined" && report.lastRateIniType != "undefined" && report.lastRateStage != "" && report.lastRateIniLocId != "" && report.lastRateIniType != ""){
			   	        			if(report.lastRateStage == "rejected"){
				   	        			colorHint = "textDanger";
				   	        		}else if(report.lastRateStage == "accepted"){
				   	        			colorHint = "textSuccess";
				   	        		}else if(orgLocationId != report.lastRateIniLocId && report.lastRateIniUserCode != ""){
				   	        			colorHint = "textWarning";
				   	        		}else if(orgLocationId == report.lastRateIniLocId && report.lastRateIniUserCode == ""){
				   	        			colorHint = "textWarning";
				   	        		}
			   	        		}
			   	        		
			   	        		if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}
			   	        		
			   	        		
			   	        		if(report.candidateSource != null && report.candidateSource != '' && report.candidateSource != 'undefined'){
			   	        			candidateSource = "("+report.candidateSource+")";
			   	        		}else{
			   	        			candidateSource = "";
			   	        		}
						           result = result + '       <tr>';
						           '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '<td><div onclick="applicantQuickViewProfile('+report.id+','+report.candidateId+'); getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show positionRelative"><span class="pull-left" title ="'+report.candidateName+'">'+report.candidateName+'</span><i class="fa fa-binoculars"></i>';
						           		if(report.candidateScore != null && report.candidateScore != '' && report.candidateScore != 'undefined'){
						  				  if(report.candidateScore >=50){ 
						  				 	 result = result +'<small class="valid-profile validProfile fa fa-check-square text-success"></small>';        
						  				  }
						  			  } 
						  		    result = result +' </div></td>';
						           	'</sec:authorize>';
						           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '<td><div class="tbldata-dotdot proj_show positionRelative"><span class="pull-left">'+report.candidateName+'</span>';
						           		if(report.candidateScore != null && report.candidateScore != '' && report.candidateScore != 'undefined'){
						  				  if(report.candidateScore >=50){ 
						  				 	 result = result +'<small class="valid-profile validProfile fa fa-check-square text-success"></small>';        
						  				  }
						  			  } 
						  		    result = result +' </div></td>';
						           	'</sec:authorize>';
						           
						           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '<td><div><span title ="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
							           }
							       else{
							        	result = result + '<td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.phoneNumber+'</span></div></td>';
							           }
						           
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.resumeId != null && report.resumeId != '' && report.resumeId != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''+report.resumeUUID+'\',\''+report.filename+'\')"></a></span></div></td>';
							           }
							       else{
							    	   result = result + '         <td><div>--</div></td>';
						    	     }
						           
						        	   
					        	    if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdDateStr+'</span></div></td>';
							           }
							        else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
							        
						            if(report.vendorLocationId != null && report.sourceSubmission != null){
						        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
						        		   '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
						        		   		result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
								       }else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
							    	     }
						            }else{
						        	   if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined'){
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
								       }else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
												'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
							    	     }
						            }
							          
							      if(clientSubmissions == 'Y' && submissionType == 'SUBMIT_TO_CLIENT'){
							           if((report.vendorLocationId == null || report.vendorLocationId == '' || report.vendorLocationId == 'undefined') && (report.sourceSubmission == null || report.sourceSubmission == '' || report.sourceSubmission == 'undefined')){
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
													'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.clientSubmissionId+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }else{
							        		   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.clientSubmissionId+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate" >'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        		   
							        	   }
							           }else{
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }else{
							        		   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }
							           }
							      }   
						           if(report.createdUser != null && report.createdUser != '' && report.createdUser != 'undefined'){
						        	   result = result + '         <td><div><span title="'+report.createdUser+''+orgName+''+candidateSource+'">'+report.createdUser+''+orgName+'<br/>'+candidateSource+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     } 
						          
						        	   
				        		   if(report.candidateScore != null){
				        			   result = result + '         <td><div><span>'+report.candidateScore+' %</span></div></td>';
							           }
							       else{
							    	   result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.workExperience != null && report.workExperience != '' && report.workExperience != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.workExperience+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           if(report.requirementCurrentStatus != 'SUBMITTED' && report.requirementCurrentStatus != 'CLOSED' && report.requirementCurrentStatus != 'REJECTED' &&  report.requirementCurrentStatus != 'CANCEL' && report.requirementCurrentStatus != 'DRAFT' && report.requirementCurrentStatus != 'ONHOLD' && report.requirementCurrentStatus != 'FILLEDCLOSED' && report.requirementCurrentStatus != 'UNFILLEDCLOSED'){
							          if(report.submissionStatus == 'REJECTED'){
							        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
							        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined' &&  report.rejectedCategoryType != null && report.rejectedCategoryType != 'Other'){
							        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+"<font color='red'>Category</font>: "+report.rejectedCategoryType+'<br>'+"Reason: "+report.rejectedReason+'</p></div></i></div></td>';
							        	   }
							        	   
							        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined' && report.rejectedCategoryType == null){
							        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+"<font color='red'>Category</font>: "+"Bad Fit"+'<br>'+"Reason: "+report.rejectedReason+'</p></div></i></div></td>';
							        	   }
							        	   
							        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined' &&  report.rejectedCategoryType != null && report.rejectedCategoryType == 'Other'){
							        		   result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+"<font color='red'>Category</font>:"+report.rejectedCategoryType+'<br>'+"Other: "+report.rejectedCategoryOther+'<br>'+"Reason: "+report.rejectedReason+'</p></div></i></div></td>';
							        	   }		  
							           }else{
							        	   result = result + '<td><div class="tblwidth-150">';
							        	   result = result + '<select id="rejectApplicantSelectspocState'+report.id+'" onChange="rejectApplicant('+report.id+','+requirementId+',\''+submissionType+'\')";>';
							        	   result = result + "<option value='Status'>"+report.submissionStatus+"</option><option value='Reject'>Reject</option>";
							        	   if(report.submissionStatus == 'OFFERACCEPTED' || report.submissionStatus == 'CLIENT_OFFERACCEPTED'){
							        		   result = result + '<option value="backout">Back-Out</option>';
							        	   }
							        	   result = result + '</select>';
								           result = result + '</div></td>';
							           }
						           }else{
						        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
						           }
						           result = result + '</div></td>';
							          
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				           
				           
							$("#tab-1").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#pipelineLoading").removeAttr("style");
							$("#status-data").empty();
							$("#status-data").append(result);
						
							$("#tab-2").append(status-data);
							$("#loading").removeAttr("style"); 
							$("#emptyPipelinelist").val(json.length);
							
							documentpagination();
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
							
							prepareAllCounts(requirementId);
							tabEnable('','applicantli');
							return true;
							
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result);
      				  tabEnable('','applicantli');
      				  return true;
                      }
               },
                 
            }); 
	  return false;
	 
}
</script>


<script>
function viewResumeRequirementSummary(resumeUUID,title){
	var sourceleadUrl ='${sourceleadUrl}';
	if(resumeUUID != null && resumeUUID != ""){
		window.open(sourceleadUrl+'/viewProfessionalCandidateResume/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
	}  
}
</script>

<script>
function documentpagination(){  
    $('#requirementsPagination').DataTable( { 
    	"scrollCollapse": false,
//     	"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"aaSorting": [[7,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#requirementsPagination").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  
}
</script>

<!-- script for Duplicate Applicants --> 
<script>  
function requirementPipelineDA(requirementId,submissionType){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $("#pipelineLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType,        
         success :function(data) {  
        	 var result =""; 
        	 if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		
            			   
						   result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
						   
						   result = result +'<span class="applicant_export">';
							 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
					            result = result +'<a title="Export" onClick="piplineexport(); " class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>';
					            result = result +'<div class="export_pipline" style="display:none">';
					            result = result +'<ul>';
					            result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');">PDF &nbsp;&nbsp;<i class="fa fa-file-pdf-o"></i></li>';
					            result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');">Excel &nbsp;&nbsp;<i class="fa fa-file-excel-o"></i></li>';
					            result = result +' </ul>';
					            result = result +'</div>';
					          '</sec:authorize>';
					       result = result +'</span>';
					       
				           result = result + '<div class="require_wrap_table overflow-data filter-position">';
				           result = result + '<div class="clerfix">';
				           result = result + '<table id="requirementsPaginationDA" class="display" cellspacing="0" width="100%"">';
				           result = result + '<thead>';
				           result = result + '<tr>';
				           result = result + '<th>Name</th>';
				           result = result + '<th>Email</th>';
				           result = result + '<th>Ph. No</th>';
				           result = result + '<th>Resume</th>';
				           result = result + '<th>Applied on</th>';
					       result = result + '<th>Rate</th>';
/* 					         if(clientSubmissions == 'Y'){
					           result = result + '<th>Sub. Rate</th>';
					         }
 */					       result = result + '<th>Source</th>';
		        		   result = result + '<th>Score</th>';
				           /* result = result + '<th>Exp</th>'; */
				           result = result + '<th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		var report = json[i];
			   	        		var colorHint = "";
			   	        		if(report.lastRateStage != "" && report.lastRateIniLocId != "" && report.lastRateIniType != "" && report.lastRateStage != null && report.lastRateIniLocId != null && report.lastRateIniType != null){
				   	        		if(report.lastRateStage == "rejected"){
				   	        			colorHint = "textDanger";
				   	        		}else if(report.lastRateStage == "accepted"){
				   	        			colorHint = "textSuccess";
				   	        		}else if(orgLocationId != report.lastRateIniLocId && report.lastRateIniUserCode != ""){
				   	        			colorHint = "textWarning";
				   	        		}else if(orgLocationId == report.lastRateIniLocId && report.lastRateIniUserCode == ""){
				   	        			colorHint = "textWarning";
				   	        		}
			   	        		}
			   	        		if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}
			   	        		
			   	        		if(report.candidateSource != null && report.candidateSource != '' && report.candidateSource != 'undefined'){
			   	        			candidateSource = "("+report.candidateSource+")";
			   	        		}else{
			   	        			candidateSource = "";
			   	        		}

						           result = result + '       <tr>';
						           '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div onclick="applicantQuickViewProfile('+report.id+','+report.candidateId+'); getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show"><span class="pull-left" title ="'+report.candidateName+'">'+report.candidateName+'</span><i class="fa fa-binoculars"></i></div></td>';
						           '</sec:authorize>';
						           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div class="tbldata-dotdot proj_show"><span class="pull-left">'+report.candidateName+'</span></div></td>';
						           '</sec:authorize>';
						           
						           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '         <td><div><span title ="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.phoneNumber+'</span></div></td>';
							        }  else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	    }
						           
						           if(report.resumeId != null && report.resumeId != '' && report.resumeId != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''+report.resumeUUID+'\',\''+report.filename+'\')"></a></span></div></td>';
							           }
							       else{
							    	   result = result + '         <td><div>--</div></td>';
						    	     }
						           
						        	   
					        	   if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
						        	   result = result + '         <td><div class="tbldata-dotdot-sm">'+report.createdDateStr+', '+report.createdTimeStr+'</div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
							           
							    	     
						           if(report.vendorLocationId != null && report.sourceSubmission != null){
						        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
						        		   '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
						        		   		result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
							    	     }
						            }else{
						        	   if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined'){
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
												'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
							    	     }
						            }
						           
							          
							     /* if(clientSubmissions == 'Y'){
							           if((report.vendorLocationId == null || report.vendorLocationId == '' || report.vendorLocationId == 'undefined') && (report.sourceSubmission == null || report.sourceSubmission == '' || report.sourceSubmission == 'undefined')){
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
							        		   result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.clientSubmissionId+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }else{
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
							        		   result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }
							       } */
							           
						           if(report.createdUser != null && report.createdUser != '' && report.createdUser != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdUser+''+orgName+'<br/>'+candidateSource+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     } 
						        	   
				        		   if(report.candidateScore != null){
				        			   result = result + '         <td><div><span>'+report.candidateScore+' %</span></div></td>';
							           }
							       else{
							    	   result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           /* if(report.workExperience != null && report.workExperience != '' && report.workExperience != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.workExperience+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     } */
						    	     if(report.requirementCurrentStatus != 'SUBMITTED' && report.requirementCurrentStatus != 'CLOSED' && report.requirementCurrentStatus != 'REJECTED' &&  report.requirementCurrentStatus != 'CANCEL' && report.requirementCurrentStatus != 'DRAFT' && report.requirementCurrentStatus != 'ONHOLD' && report.requirementCurrentStatus != 'FILLEDCLOSED' && report.requirementCurrentStatus != 'UNFILLEDCLOSED'){
							           if(report.submissionStatus == 'REJECTED'){
							        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
							        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
							        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectedReason+'</p></div></i></div></td>';
							        	   }		  
							           }else{
							        	   result = result + '<td><div class="tblwidth-150">';
							        	   result = result + '<select id="rejectApplicantSelectspocState'+report.id+'" onChange="rejectApplicant('+report.id+','+requirementId+',\''+submissionType+'\')";>';
							        	   result = result + "<option value='Status'>"+report.submissionStatus+"</option><option value='Reject'>Reject</option>";
							        	   if(report.submissionStatus == 'OFFERACCEPTED' || report.submissionStatus == 'CLIENT_OFFERACCEPTED'){
							        		   result = result + '<option value="backout">Back-Out</option>';
							        	   }
							        	   result = result + '</select>';
								           result = result + '</div></td>';
							           }
						    	     }else{
							          result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
	 
						    	     }
						           result = result + '</div></td>';
							           
							          
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				           
				           
							$("#tab-1").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#pipelineLoading").removeAttr("style");
							$("#status-data").empty();
							$("#status-data").append(result);
							$("#tab-2").append(status-data);
							$("#loading").removeAttr("style"); 
							$("#emptyPipelinelist").val(json.length);
							
							documentpaginationDA();
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
							prepareAllCounts(requirementId);
							
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
	 
}
</script>

<script>
function documentpaginationDA(){  
    $('#requirementsPaginationDA').DataTable( { 
    	"scrollCollapse": false,
    	//"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"aaSorting": [[7,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#requirementsPaginationDA").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>


<!-- Script for Request Sent List -->
<script>  
function requirementPipelineRS(requirementId,submissionType){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $("#pipelineLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType,        
         success :function(data) {  
        	 var result =""; 
        	 if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		
						   result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
						   
						   result = result +'<span class="applicant_export">';
							 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
					            result = result +'<a title="Export" onClick="piplineexport();" class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>';
					            result = result +'<div class="export_pipline" style="display:none">';
					            result = result +'<ul>';
					            result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');">PDF &nbsp;&nbsp;<i class="fa fa-file-pdf-o"></i></li>';
					            result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');">Excel &nbsp;&nbsp;<i class="fa fa-file-excel-o"></i></li>';
					            result = result +' </ul>';
					            result = result +'</div>';
					          '</sec:authorize>';
					       result = result +'</span>';
						   
						   result = result + '<div class="require_wrap_table overflow-data filter-position">';
				           result = result + '<div class="clerfix">';
				           result = result + '<table id="requirementsPaginationRS" class="display" cellspacing="0" width="100%"">';
				           result = result + '<thead>';
					           result = result + '<tr>';
					           result = result + '<th>Name</th>';
					           result = result + '<th>Email</th>';
					           result = result + '<th>Ph. No</th>';
					           result = result + '<th>Resume</th>';
					           result = result + '<th>Applied on</th>';
					           result = result + '<th>Rate</th>';
					          /*  if(clientSubmissions == 'Y'){
						           result = result + '<th>Sub. Rate</th>';
						       } */
					           result = result + '<th>Source</th>';
			        		   result = result + '<th>Score</th>';
					           result = result + '<th>Exp</th>';
					           result = result + '<th>Status</th>';
					           result = result + '</tr>';
				           result = result + '</thead>';
				           
				           result = result + '<tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		var report = json[i];
			   	        		var colorHint = "";
			   	        		if(report.lastRateStage != "" && report.lastRateIniLocId != "" && report.lastRateIniType != "" && report.lastRateStage != null && report.lastRateIniLocId != null && report.lastRateIniType != null){
				   	        		if(report.lastRateStage == "rejected"){
				   	        			colorHint = "textDanger";
				   	        		}else if(report.lastRateStage == "accepted"){
				   	        			colorHint = "textSuccess";
				   	        		}else if(orgLocationId != report.lastRateIniLocId && report.lastRateIniUserCode != ""){
				   	        			colorHint = "textWarning";
				   	        		}else if(orgLocationId == report.lastRateIniLocId && report.lastRateIniUserCode == ""){
				   	        			colorHint = "textWarning";
				   	        		}
			   	        		}
			   	        		if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}
			   	        		
			   	        		if(report.candidateSource != null && report.candidateSource != '' && report.candidateSource != 'undefined'){
			   	        			candidateSource = "("+report.candidateSource+")";
			   	        		}else{
			   	        			candidateSource = "";
			   	        		}

						           result = result + '<tr>';
						           '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div onclick="applicantQuickViewProfile('+report.id+','+report.candidateId+'); getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show"><span class="pull-left" title ="'+report.candidateName+'">'+report.candidateName+'</span><i class="fa fa-binoculars"></i></div></td>';
						           '</sec:authorize>';
						           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div class="tbldata-dotdot proj_show"><span class="pull-left">'+report.candidateName+'</span></div></td>';
						           '</sec:authorize>';
						           
						            if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '         <td><div><span title ="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
							        }else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	    }
						           
						           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.phoneNumber+'</span></div></td>';
							       }else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	   }
						           
						           if(report.resumeId != null && report.resumeId != '' && report.resumeId != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''+report.resumeUUID+'\',\''+report.filename+'\')"></a></span></div></td>';
							       }else{
							    	   result = result + '         <td><div>--</div></td>';
						    	   }
						        	   
					        	   if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdDateStr+'</span></div></td>';
							       }else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	   }
							    	     
					        	   if(report.vendorLocationId != null && report.sourceSubmission != null){
						        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
						        		   '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
						        		   		result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
							    	     }
						            }else{
						        	   if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined'){
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
												'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
							    	     }
						            }
							          
							    	/* if(clientSubmissions == 'Y'){
							           if((report.vendorLocationId == null || report.vendorLocationId == '' || report.vendorLocationId == 'undefined') && (report.sourceSubmission == null || report.sourceSubmission == '' || report.sourceSubmission == 'undefined')){
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
							        		   result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.clientSubmissionId+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }else{
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
							        		   result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }
							        } */
							           
						           if(report.createdUser != null && report.createdUser != '' && report.createdUser != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdUser+''+orgName+'<br/>'+candidateSource+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	   } 
						        	   
				        		   if(report.candidateScore != null){
				        			   result = result + '         <td><div><span>'+report.candidateScore+' %</span></div></td>';
							           }
							       else{
							    	   result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.workExperience != null && report.workExperience != '' && report.workExperience != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.workExperience+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           if(report.requirementCurrentStatus != 'SUBMITTED' && report.requirementCurrentStatus != 'CLOSED' && report.requirementCurrentStatus != 'REJECTED' &&  report.requirementCurrentStatus != 'CANCEL' && report.requirementCurrentStatus != 'DRAFT' && report.requirementCurrentStatus != 'ONHOLD' && report.requirementCurrentStatus != 'FILLEDCLOSED' && report.requirementCurrentStatus != 'UNFILLEDCLOSED'){
							           if(report.submissionStatus == 'REJECTED'){
							        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
							        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
							        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectedReason+'</p></div></i></div></td>';
							        	   }		  
							           }else{
							        	   result = result + '<td><div class="tblwidth-150">';
							        	   result = result + '<select id="rejectApplicantSelectspocState'+report.id+'" onChange="rejectApplicant('+report.id+','+requirementId+',\''+submissionType+'\')";>';
							        	   result = result + "<option value='Status'>"+report.submissionStatus+"</option><option value='Reject'>Reject</option>";
							        	   if(report.submissionStatus == 'OFFERACCEPTED' || report.submissionStatus == 'CLIENT_OFFERACCEPTED'){
							        		   result = result + '<option value="backout">Back-Out</option>';
							        	   }
							        	   result = result + '</select>';
								           result = result + '</div></td>';
							           }
						           }else{
						        	result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
  
						           }
						           
						           result = result + '</div></td>';
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				           
				           
							$("#tab-1").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#pipelineLoading").removeAttr("style");
							$("#status-data").empty();
							$("#status-data").append(result);
							$("#tab-2").append(status-data);
							$("#loading").removeAttr("style"); 
							$("#emptyPipelinelist").val(json.length);
							
							documentpaginationRS();
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
							prepareAllCounts(requirementId);
							
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
	 
}
</script>

<script>
function documentpaginationRS(){  
    $('#requirementsPaginationRS').DataTable( { 
    	"scrollCollapse": false,
    	//"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"aaSorting": [[7,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#requirementsPaginationRS").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>

<!-- Script for Short List -->
<script>  
function requirementPipelineSL(requirementId,submissionType){
	var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $("#pipelineLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType,        
         success :function(data) {  
        	 var result =""; 
        	 if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		
						   result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
				           
						   result = result +'<span class="applicant_export">';
							 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
					            result = result +'<a title="Export" onClick="piplineexport();" class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i></a>';
					            result = result +'<div class="export_pipline" style="display:none">';
					            result = result +'<ul>';
					            result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');">PDF &nbsp;&nbsp;<i class="fa fa-file-pdf-o"></i></li>';
					            result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');">Excel &nbsp;&nbsp;<i class="fa fa-file-excel-o"></i></li>';
					            result = result +' </ul>';
					            result = result +'</div>';
					          '</sec:authorize>';
					       result = result +'</span>';
						   
						   result = result + '<div class="require_wrap_table overflow-data filter-position">';
				           result = result + '<div class="clerfix">';
				           result = result + '<table id="requirementsPaginationSL" class="display" cellspacing="0" width="100%"">';
				           result = result + '<thead>';
				           result = result + '<tr>';
				           result = result + '<th>Name</th>';
				           result = result + '<th>Email</th>';
				           result = result + '<th>Ph. No</th>';
				           result = result + '<th>Resume</th>';
			        	   result = result + '<th>Applied on</th>';
					       result = result + '<th>Rate</th>';
					       /* if(clientSubmissions == 'Y'){
					           result = result + '<th>Sub. Rate</th>';
					       } */
					       result = result + '<th>Source</th>';
				           result = result + '<th>Shortlisted By</th>'; 
		        		   result = result + '<th>Score</th>';
				           result = result + '<th>Exp</th>';
				           result = result + '<th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		var report = json[i];
			   	        		var colorHint = "";
			   	        		if(report.lastRateStage != "" && report.lastRateIniLocId != "" && report.lastRateIniType != "" && report.lastRateStage != null && report.lastRateIniLocId != null && report.lastRateIniType != null){
				   	        		if(report.lastRateStage == "rejected"){
				   	        			colorHint = "textDanger";
				   	        		}else if(report.lastRateStage == "accepted"){
				   	        			colorHint = "textSuccess";
				   	        		}else if(orgLocationId != report.lastRateIniLocId && report.lastRateIniUserCode != ""){
				   	        			colorHint = "textWarning";
				   	        		}else if(orgLocationId == report.lastRateIniLocId && report.lastRateIniUserCode == ""){
				   	        			colorHint = "textWarning";
				   	        		}
			   	        		}
			   	        		if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}
			   	        		
			   	        		if(report.candidateSource != null && report.candidateSource != '' && report.candidateSource != 'undefined'){
			   	        			candidateSource = "("+report.candidateSource+")";
			   	        		}else{
			   	        			candidateSource = "";
			   	        		}

						           result = result + '       <tr>';
						           '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div onclick="applicantQuickViewProfile('+report.id+','+report.candidateId+'); getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show"><span class="pull-left" title ="'+report.candidateName+'">'+report.candidateName+'</span><i class="fa fa-binoculars"></i></div></td>';
						           '</sec:authorize>';
						           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div class="tbldata-dotdot proj_show"><span class="pull-left">'+report.candidateName+'</span></div></td>';
						           '</sec:authorize>';
						           
						           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '         <td><div><span title ="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.phoneNumber+'</span></div></td>';
							           }
						           
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.resumeId != null && report.resumeId != '' && report.resumeId != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''+report.resumeUUID+'\',\''+report.filename+'\')"></a></span></div></td>';
							        }
							       else{
							    	   result = result + '         <td><div>--</div></td>';
						    	     }
						           
					        	   if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdDateStr+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
							          
							    	     
					        	   if(report.vendorLocationId != null && report.sourceSubmission != null){
						        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
						        		   '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
						        		   		result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
							    	     }
						            }else{
						        	   if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined'){
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
												'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
							    	     }
						            }
							          
							    	   /* if(clientSubmissions == 'Y'){
							           if((report.vendorLocationId == null || report.vendorLocationId == '' || report.vendorLocationId == 'undefined') && (report.sourceSubmission == null || report.sourceSubmission == '' || report.sourceSubmission == 'undefined')){
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
							        		   result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.clientSubmissionId+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }else{
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
							        		   result = result + '         <td><div><span class="showRate" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }
							           } */
							           
							           if(report.createdUser != null && report.createdUser != '' && report.createdUser != 'undefined'){
							        	   result = result + '         <td><div><span>'+report.createdUser+''+orgName+'<br/>'+candidateSource+'</span></div></td>';
								           }
								       else{
								        	result = result + '         <td><div><span>--</span></div></td>';
							    	     } 
						           
						          
						        	   if(report.updatedUser != null && report.updatedUser != '' && report.updatedUser != 'undefined'){ 
						        	   result = result + '<td><div><span>'+report.updatedUser+'</span></div></td>';
						        	   }else{
						        		   result = result + '<td><div><span>--</span></div></td>';
						        	   }
						        	
					        		   if(report.candidateScore != null){
					        			   result = result + '         <td><div><span>'+report.candidateScore+' %</span></div></td>';
								           }
								       else{
								    	   result = result + '         <td><div><span>--</span></div></td>';
							    	     }
							           
							           if(report.workExperience != null && report.workExperience != '' && report.workExperience != 'undefined'){
							        	   result = result + '         <td><div><span>'+report.workExperience+'</span></div></td>';
								           }
								       else{
								        	result = result + '         <td><div><span>--</span></div></td>';
							    	     }
							           if(report.requirementCurrentStatus != 'SUBMITTED' && report.requirementCurrentStatus != 'CLOSED' && report.requirementCurrentStatus != 'REJECTED' &&  report.requirementCurrentStatus != 'CANCEL' && report.requirementCurrentStatus != 'DRAFT' && report.requirementCurrentStatus != 'ONHOLD' && report.requirementCurrentStatus != 'FILLEDCLOSED' && report.requirementCurrentStatus != 'UNFILLEDCLOSED'){
								           if(report.submissionStatus == 'REJECTED'){
								        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
								        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
								        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectedReason+'</p></div></i></div></td>';
								        	   }		  
								           }else{
								        	   result = result + '<td><div class="tblwidth-150">';
								        	   result = result + '<select id="rejectApplicantSelectspocState'+report.id+'" onChange="rejectApplicant('+report.id+','+requirementId+',\''+submissionType+'\')";>';
								        	   result = result + "<option value='Status'>"+report.submissionStatus+"</option><option value='Reject'>Reject</option>";
								        	   if(report.submissionStatus == 'OFFERACCEPTED' || report.submissionStatus == 'CLIENT_OFFERACCEPTED'){
								        		   result = result + '<option value="backout">Back-Out</option>';
								        	   }
								        	   result = result + '</select>';
									           result = result + '</div></td>';
								           }
							           }else{
							        	result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
							           }
							           result = result + '</div></td>';
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				           
				           
							$("#tab-1").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#pipelineLoading").removeAttr("style");
							$("#status-data").empty();
							$("#status-data").append(result);
							$("#tab-2").append(status-data);
							$("#loading").removeAttr("style"); 
							$("#emptyPipelinelist").val(json.length);
							
							documentpaginationSL();
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
							prepareAllCounts(requirementId);
							
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
	 
}
</script>

<script>
function documentpaginationSL(){  
    $('#requirementsPaginationSL').DataTable( { 
    	"scrollCollapse": false,
    	//"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"aaSorting": [[8,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#requirementsPaginationSL").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>

<!-- script for Submit to client -->
<script>  
function requirementPipelineSC(requirementId,submissionType){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $("#pipelineLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType,        
         success :function(data) {  
        	 var result =""; 
        	 if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            			   
						   result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
						   
						   result = result +'<span class="applicant_export">';
							 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
					            result = result +'<a title="Export" onClick="piplineexport();" class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i></a>';
					            result = result +'<div class="export_pipline" style="display:none">';
					            result = result +'<ul>';
					            result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');">PDF &nbsp;&nbsp;<i class="fa fa-file-pdf-o"></i></li>';
					            result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');">Excel &nbsp;&nbsp;<i class="fa fa-file-excel-o"></i></li>';
					            result = result +' </ul>';
					            result = result +'</div>';
					          '</sec:authorize>';
					       result = result +'</span>';
						   
						   result = result + '<div class="require_wrap_table overflow-data filter-position">';
				           result = result + '<div class="clerfix">';
				           result = result + '<table id="requirementsPaginationSC" class="display" cellspacing="0" width="100%"">';
				           result = result + '<thead>';
				           result = result + '<tr>';
				           result = result + '<th>Name</th>';
				           result = result + '<th>Email</th>';
				           result = result + '<th>Ph. No</th>';
				           result = result + '<th>Resume</th>';
				           result = result + '<th>Applied on</th>';
				           if(clientSubmissions == 'Y'){
				             result = result + '<th>Sub. Rate</th>';
				           }
				           result = result + '<th>Source</th>';
		        		   result = result + '<th>Score</th>';
				           result = result + '<th>Exp</th>';
				           result = result + '<th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		var report = json[i];
			   	        		var colorHint = "";
			   	        		if(report.lastRateStage != "" && report.lastRateIniLocId != "" && report.lastRateIniType != "" && report.lastRateStage != null && report.lastRateIniLocId != null && report.lastRateIniType != null){
				   	        		if(report.lastRateStage == "rejected"){
				   	        			colorHint = "textDanger";
				   	        		}else if(report.lastRateStage == "accepted"){
				   	        			colorHint = "textSuccess";
				   	        		}else if(orgLocationId != report.lastRateIniLocId && report.lastRateIniUserCode != ""){
				   	        			colorHint = "textWarning";
				   	        		}else if(orgLocationId == report.lastRateIniLocId && report.lastRateIniUserCode == ""){
				   	        			colorHint = "textWarning";
				   	        		}
			   	        		}
			   	        		if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}
			   	        		
			   	        		if(report.candidateSource != null && report.candidateSource != '' && report.candidateSource != 'undefined'){
			   	        			candidateSource = "("+report.candidateSource+")";
			   	        		}else{
			   	        			candidateSource = "";
			   	        		}

						           result = result + '       <tr>';
						           '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div onclick="applicantQuickViewProfile('+report.id+','+report.candidateId+'); getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show"><span class="pull-left" title ="'+report.candidateName+'">'+report.candidateName+'</span><i class="fa fa-binoculars"></i></div></td>';
						           '</sec:authorize>';
						           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div class="tbldata-dotdot proj_show"><span class="pull-left">'+report.candidateName+'</span></div></td>';
						           '</sec:authorize>';
						           
						           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '         <td><div><span title ="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.phoneNumber+'</span></div></td>';
							           }
						           
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.resumeId != null && report.resumeId != '' && report.resumeId != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''+report.resumeUUID+'\',\''+report.filename+'\')"></a></span></div></td>';
							           }
							       else{
							    	   result = result + '         <td><div>--</div></td>';
						    	     }
						           
						        	   
					        	   if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdDateStr+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
							          
							      if(clientSubmissions == 'Y'){
							           if((report.vendorLocationId == null || report.vendorLocationId == '' || report.vendorLocationId == 'undefined') && (report.sourceSubmission == null || report.sourceSubmission == '' || report.sourceSubmission == 'undefined')){
							        	   if(report.submitToClientRate != null && report.submitToClientRate != '' && report.submitToClientRate != 'undefined'){
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.clientSubmissionId+','+report.candidateId+');">'+report.submitToClientRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.submitToClientRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }else{
							        		   var expexted = (report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined' && report.proposedSubmissionRate != null) ? report.proposedSubmissionRate : '<i class="glyphicon fa fa-money"></i>';
							        		   '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.clientSubmissionId+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }
							           }else{
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }else{
							        		   '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');"><i class="glyphicon fa fa-money"></i></span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'"><i class="glyphicon fa fa-money"></i></span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }
							           }
							        }
							           
						           if(report.createdUser != null && report.createdUser != '' && report.createdUser != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdUser+''+orgName+'<br/>'+candidateSource+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     } 
						        	   
				        		   if(report.candidateScore != null){
				        			   result = result + '         <td><div><span>'+report.candidateScore+' %</span></div></td>';
							           }
							       else{
							    	   result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.workExperience != null && report.workExperience != '' && report.workExperience != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.workExperience+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           if(report.requirementCurrentStatus != 'SUBMITTED' && report.requirementCurrentStatus != 'CLOSED' && report.requirementCurrentStatus != 'REJECTED' &&  report.requirementCurrentStatus != 'CANCEL' && report.requirementCurrentStatus != 'DRAFT' && report.requirementCurrentStatus != 'ONHOLD' && report.requirementCurrentStatus != 'FILLEDCLOSED' && report.requirementCurrentStatus != 'UNFILLEDCLOSED'){ 
							           if(report.submissionStatus == 'REJECTED'){
							        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
							        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
							        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectedReason+'</p></div></i></div></td>';
							        	   }		  
							           }else{
							        	   result = result + '<td><div class="tblwidth-150">';
							        	   result = result + '<select id="rejectApplicantSelectspocState'+report.id+'" onChange="rejectApplicant('+report.id+','+requirementId+',\''+submissionType+'\')";>';
							        	   result = result + "<option value='Status'>"+report.submissionStatus+"</option><option value='Reject'>Reject</option>";
							        	   if(report.submissionStatus == 'OFFERACCEPTED' || report.submissionStatus == 'CLIENT_OFFERACCEPTED'){
							        		   result = result + '<option value="backout">Back-Out</option>';
							        	   }
							        	   result = result + '</select>';
								           result = result + '</div></td>';
							           }
						           }else{
						        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
						           }
						           result = result + '</div></td>';
							           
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				           
				           
							$("#tab-1").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#pipelineLoading").removeAttr("style");
							$("#status-data").empty();
							$("#status-data").append(result);
							$("#tab-2").append(status-data);
							$("#loading").removeAttr("style"); 
							$("#emptyPipelinelist").val(json.length);
							
							documentpaginationSC();
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
							prepareAllCounts(requirementId);
							
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
	 
}
</script>

<script>
function documentpaginationSC(){  
    $('#requirementsPaginationSC').DataTable( { 
    	"scrollCollapse": false,
//     	"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"aaSorting": [[7,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#requirementsPaginationSC").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>



<!-- script for Interviews List -->
<script>  
function requirementPipelineIN(requirementId,submissionType){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  
	  $("#pipelineLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType,        
         success :function(data) {  
        	 var result =""; 
        	 if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            			   
						   result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
						   
						   result = result +'<span class="applicant_export">';
							 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
					            result = result +'<a title="Export" onClick="piplineexport();" class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i></a>';
					            result = result +'<div class="export_pipline" style="display:none">';
					            result = result +'<ul>';
					            result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');">PDF &nbsp;&nbsp;<i class="fa fa-file-pdf-o"></i></li>';
					            result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');">Excel &nbsp;&nbsp;<i class="fa fa-file-excel-o"></i></li>';
					            result = result +' </ul>';
					            result = result +'</div>';
					          '</sec:authorize>';
					       result = result +'</span>';
						   
						   result = result + '<div class="require_wrap_table overflow-data filter-position">';
				           result = result + '<div class="clerfix">';
				           result = result + '<table id="requirementsPaginationIN" class="display" cellspacing="0" width="100%"">';
				           result = result + '<thead>';
				           result = result + '<tr>';
				           result = result + '<th>Name</th>';
				           result = result + '<th>Email</th>';
				           result = result + '<th>Ph. No</th>';
				           result = result + '<th>Resume</th>';
						   result = result + '<th>No.of Interviews</th>'; 
				           result = result + '<th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		var report = json[i];
			   	        		
			   	        		if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}

						           result = result + '       <tr>';
						           '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div onclick="applicantQuickViewProfile('+report.id+','+report.candidateId+'); getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show"><span class="pull-left" title ="'+report.candidateName+'">'+report.candidateName+'</span><i class="fa fa-binoculars"></i></div></td>';
						           '</sec:authorize>';
						           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div class="tbldata-dotdot proj_show"><span class="pull-left">'+report.candidateName+'</span></div></td>';
						           '</sec:authorize>';
						           
						           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '         <td><div><span title ="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.phoneNumber+'</span></div></td>';
							       }else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.resumeId != null && report.resumeId != '' && report.resumeId != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''+report.resumeUUID+'\',\''+report.filename+'\')"></a></span></div></td>';
							           }
							       else{
							    	   result = result + '         <td><div>--</div></td>';
						    	     }
						        	   
						           if(report.totalNoOfInterviews != null && report.totalNoOfInterviews != '' && report.totalNoOfInterviews != 'undefined'){
						        		result = result +'<td><div><span>'+report.totalNoOfInterviews+'</span></div></td>';
						           }else{
						        	   result = result + '<td><div><span>--</span></div></td>'; 
						           }
						           if(report.requirementCurrentStatus != 'SUBMITTED' && report.requirementCurrentStatus != 'CLOSED' && report.requirementCurrentStatus != 'REJECTED' &&  report.requirementCurrentStatus != 'CANCEL' && report.requirementCurrentStatus != 'DRAFT' && report.requirementCurrentStatus != 'ONHOLD' && report.requirementCurrentStatus != 'FILLEDCLOSED' && report.requirementCurrentStatus != 'UNFILLEDCLOSED'){
							           if(report.submissionStatus == 'REJECTED'){
							        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
							        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
							        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectedReason+'</p></div></i></div></td>';
							        	   }		  
							           }else{
							        	   result = result + '<td><div class="tblwidth-150">';
							        	   result = result + '<select id="rejectApplicantSelectspocState'+report.id+'" onChange="rejectApplicant('+report.id+','+requirementId+',\''+submissionType+'\')";>';
							        	   result = result + "<option value='Status'>"+report.submissionStatus+"</option><option value='Reject'>Reject</option>";
							        	   if(report.submissionStatus == 'OFFERACCEPTED' || report.submissionStatus == 'CLIENT_OFFERACCEPTED'){
							        		   result = result + '<option value="backout">Back-Out</option>';
							        	   }
							        	   result = result + '</select>';
								           result = result + '</div></td>';
							           }
						           }else{
						        	result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
   
						           }
						           result = result + '</div></td>';
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				           
				           
							$("#tab-1").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#pipelineLoading").removeAttr("style");
							$("#status-data").empty();
							$("#status-data").append(result);
							$("#tab-2").append(status-data);
							$("#loading").removeAttr("style"); 
							$("#emptyPipelinelist").val(json.length);
							
							documentpaginationIN();
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
							prepareAllCounts(requirementId);
							
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
	 
}
</script>

<script>
function documentpaginationIN(){  
    $('#requirementsPaginationIN').DataTable( { 
    	"scrollCollapse": false,
//     	"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	/* "aaSorting": [[8,'desc']], */
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#requirementsPaginationIN").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>

<!-- script for offers List -->
<script>  
function requirementPipelineOffer(requirementId,submissionType){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  
	  $("#pipelineLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType,        
         success :function(data) {  
        	 var result =""; 
        	 if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            			   
						   result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
						   
						   result = result +'<span class="applicant_export">';
							 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
					            result = result +'<a title="Export" onClick="piplineexport();" class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i></a>';
					            result = result +'<div class="export_pipline" style="display:none">';
					            result = result +'<ul>';
					            result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');">PDF &nbsp;&nbsp;<i class="fa fa-file-pdf-o"></i></li>';
					            result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');">Excel &nbsp;&nbsp;<i class="fa fa-file-excel-o"></i></li>';
					            result = result +' </ul>';
					            result = result +'</div>';
					          '</sec:authorize>';
					       result = result +'</span>';
						   
						   result = result + '<div class="require_wrap_table overflow-data filter-position">';
				           result = result + '<div class="clerfix">';
				           result = result + '<table id="requirementsPaginationOffer" class="display" cellspacing="0" width="100%"">';
				           result = result + '<thead>';
				           result = result + '<tr>';
				           result = result + '<th>Name</th>';
				           result = result + '<th>Email</th>';
				           result = result + '<th>Ph. No</th>';
				           result = result + '<th>Resume</th>';
						   result = result + '<th>Offer Join Date</th>'; 
						   result = result + '<th>Position Type</th>'; 
						   result = result + '<th>Offer Rate</th>'; 
						   result = result + '<th>Attachment</th>'; 
				           result = result + '<th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		var report = json[i];
			   	        		
			   	        		if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}

						           result = result + '       <tr>';
						           '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div onclick="applicantQuickViewProfile('+report.id+','+report.candidateId+'); getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show"><span class="pull-left" title ="'+report.candidateName+'">'+report.candidateName+'</span><i class="fa fa-binoculars"></i></div></td>';
						           '</sec:authorize>';
						           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div class="tbldata-dotdot proj_show"><span class="pull-left">'+report.candidateName+'</span></div></td>';
						           '</sec:authorize>';
						           
						           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '         <td><div><span title ="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.phoneNumber != null && report.phoneNumber != '' && report.phoneNumber != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.phoneNumber+'</span></div></td>';
							        }else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	    }
						           
						           if(report.resumeId != null && report.resumeId != '' && report.resumeId != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''+report.resumeUUID+'\',\''+report.filename+'\')"></a></span></div></td>';
							       }
							       else{
							    	   result = result + '         <td><div>--</div></td>';
						    	     }
						           
					        	   if(report.offerJoinDate != null && report.offerJoinDate != '' && report.offerJoinDate != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.offerJoinDate+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     } 
					        	   if(report.positionType != null && report.positionType != '' && report.positionType != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.positionType+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     } 
					        	   if(report.offerRate != null && report.offerRate != '' && report.offerRate != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.offerRate+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     } 
					        	   if(report.attachment != null && report.attachment != '' && report.attachment != 'undefined'){
					        		   result = result + '         <td title='+report.attachment+'><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadOfferAttachment?offerId='+report.offerId+'\"></a><a class="fa fa-eye ml10" onclick="viewOfferDocument(\''+report.offerId+'\',\''+report.attachment+'\')"></a></span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     } 
					        	   if(report.requirementCurrentStatus != 'SUBMITTED' && report.requirementCurrentStatus != 'CLOSED' && report.requirementCurrentStatus != 'REJECTED' &&  report.requirementCurrentStatus != 'CANCEL' && report.requirementCurrentStatus != 'DRAFT' && report.requirementCurrentStatus != 'ONHOLD' && report.requirementCurrentStatus != 'FILLEDCLOSED' && report.requirementCurrentStatus != 'UNFILLEDCLOSED'){

							           if(report.submissionStatus == 'REJECTED'){
							        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
							        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
							        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectedReason+'</p></div></i></div></td>';
							        	   }		  
							           }else{
							        	   result = result + '<td><div class="tblwidth-150">';
							        	   result = result + '<select id="rejectApplicantSelectspocState'+report.id+'" onChange="rejectApplicant('+report.id+','+requirementId+',\''+submissionType+'\')";>';
							        	   result = result + "<option value='Status'>"+report.submissionStatus+"</option><option value='Reject'>Reject</option>";
							        	   if(report.submissionStatus == 'OFFERACCEPTED' || report.submissionStatus == 'CLIENT_OFFERACCEPTED'){
							        		   result = result + '<option value="backout">Back-Out</option>';
							        	   }
							        	   result = result + '</select>';
								           result = result + '</div></td>';
							           }
					        	   }else{
						        	result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
  
					        	   }
						           result = result + '</div></td>';
							          
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				           
				           
							$("#tab-1").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#pipelineLoading").removeAttr("style");
							$("#status-data").empty();
							$("#status-data").append(result);
							$("#tab-2").append(status-data);
							$("#loading").removeAttr("style"); 
							$("#emptyPipelinelist").val(json.length);
							
							documentpaginationOffer();
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
							prepareAllCounts(requirementId);
							
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
	 
}
</script>

<script>
function viewOfferDocument(offerID,title){
	var sourceleadUrl ='${sourceleadUrl}';
	if(offerID != null && offerID != ""){
		window.open(sourceleadUrl+'/viewOfferDocumentDocView/'+title+'?offerID='+offerID,title,"height=600,width=600");
	}  
}
</script>

<script>
function documentpaginationOffer(){  
    $('#requirementsPaginationOffer').DataTable( { 
    	"scrollCollapse": false,
//     	"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"aaSorting": [[8,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#requirementsPaginationOffer").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>

<script>  
function requirementPipelineRejected(requirementId,submissionType){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $("#pipelineLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType,        
         success :function(data) {  
        	 var result =""; 
        	 if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		
						   result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
						  
						   result = result +'<span class="applicant_export">';
							 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
					            result = result +'<a title="Export" onClick="piplineexport();" class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i></a>';
					            result = result +'<div class="export_pipline" style="display:none">';
					            result = result +'<ul>';
					            result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');">PDF &nbsp;&nbsp;<i class="fa fa-file-pdf-o"></i></li>';
					            result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');">Excel &nbsp;&nbsp;<i class="fa fa-file-excel-o"></i></li>';
					            result = result +' </ul>';
					            result = result +'</div>';
					          '</sec:authorize>';
					       result = result +'</span>';
						   
						   result = result + '<div class="require_wrap_table overflow-data filter-position">';
				           result = result + '<div class="clerfix">';
				           result = result + '<table id="requirementsPaginationRejected" class="display" cellspacing="0" width="100%"">';
				           result = result + '<thead>';
				           result = result + '<tr>';
				           result = result + '<th>Name</th>';
				           result = result + '<th>Email</th>';
				           result = result + '<th>Resume</th>';
				           result = result + '<th>Applied on</th>';
					       result = result + '<th>Rate</th>';
					         if(clientSubmissions == 'Y'){
					           result = result + '<th>Sub. Rate</th>';
					         }
					       result = result + '<th>Source</th>';
			        	   result = result + '<th>Rejected On</th>';
			        	   result = result + '<th>Rejected By</th>';
				          /*  result = result + '<th>Rejected Stage</th>'; */
				           result = result + '<th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		var report = json[i];
			   	        		var colorHint = "";
			   	        		if(report.lastRateStage != "" && report.lastRateIniLocId != "" && report.lastRateIniType != "" && report.lastRateStage != null && report.lastRateIniLocId != null && report.lastRateIniType != null){
				   	        		if(report.lastRateStage == "rejected"){
				   	        			colorHint = "textDanger";
				   	        		}else if(report.lastRateStage == "accepted"){
				   	        			colorHint = "textSuccess";
				   	        		}else if(orgLocationId != report.lastRateIniLocId && report.lastRateIniUserCode != ""){
				   	        			colorHint = "textWarning";
				   	        		}else if(orgLocationId == report.lastRateIniLocId && report.lastRateIniUserCode == ""){
				   	        			colorHint = "textWarning";
				   	        		}
			   	        		}
			   	        		if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}
			   	        		
			   	        		if(report.candidateSource != null && report.candidateSource != '' && report.candidateSource != 'undefined'){
			   	        			candidateSource = "("+report.candidateSource+")";
			   	        		}else{
			   	        			candidateSource = "";
			   	        		}

						           result = result + '       <tr>';
						           '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div onclick="applicantQuickViewProfile('+report.id+','+report.candidateId+'); getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show"><span class="pull-left" title ="'+report.candidateName+'">'+report.candidateName+'</span><i class="fa fa-binoculars"></i></div></td>';
						           '</sec:authorize>';
						           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div class="tbldata-dotdot proj_show"><span class="pull-left">'+report.candidateName+'</span></div></td>';
						           '</sec:authorize>';
						           
						           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '         <td><div><span title ="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.resumeId != null && report.resumeId != '' && report.resumeId != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''+report.resumeUUID+'\',\''+report.filename+'\')"></a></span></div></td>';
							        }
							       else{
							    	   result = result + '         <td><div>--</div></td>';
						    	     }
						           
						        	   
					        	   if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdDateStr+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
							           
						           
						           if(report.vendorLocationId != null && report.sourceSubmission != null){
						        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
						        		   '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
						        		   		result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
							    	     }
						            }else{
						        	   if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined'){
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
												'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
							    	     }
						            }
						           
							          
							      if(clientSubmissions == 'Y'){
							           if((report.vendorLocationId == null || report.vendorLocationId == '' || report.vendorLocationId == 'undefined') && (report.sourceSubmission == null || report.sourceSubmission == '' || report.sourceSubmission == 'undefined')){
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.clientSubmissionId+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }else{
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }
							        }
						           if(report.createdUser != null && report.createdUser != '' && report.createdUser != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdUser+''+orgName+'<br/>'+candidateSource+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	   } 
						           result = result + '<td><div class="tblwidth-150"><span>'+report.upDatedStr+'</span>';
						           if(report.updatedUser != null && report.updatedUser != '' && report.updatedUser != 'undefined'){
						        	   result = result + '<td><div class="tblwidth-150"><span>'+report.updatedUser+'</span>';
						           }
					        	  /*  result = result + '<td><div class="tblwidth-150"><span>'+report.previousSubmissionStatus+'</span>'; */
					        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
					        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined' && report.rejectedCategoryType != null && report.rejectedCategoryType != 'Other'){
					        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+"<font color='red'>Category</font>: "+report.rejectedCategoryType+'<br>'+report.rejectedReason+'</p></div></i></div></td>';
					        	   }
					        	   
					        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined' && report.rejectedCategoryType == null){
					        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+"<font color='red'>Category</font>: "+"Bad Fit"+'<br>'+"Reason: "+report.rejectedReason+'</p></div></i></div></td>';
					        	   }
					        	   
					        	   if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined' && report.rejectedCategoryType != null && report.rejectedCategoryType == 'Other'){
					        		   result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+"<font color='red'>Category</font>: "+report.rejectedCategoryType+'<br>'+"Other: "+report.rejectedCategoryOther+'<br>'+report.rejectedReason+'</p></div></i></div></td>';
					        	   }		  
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				           
				           
							$("#tab-1").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#pipelineLoading").removeAttr("style");
							$("#status-data").empty();
							$("#status-data").append(result);
							$("#tab-2").append(status-data);
							$("#loading").removeAttr("style"); 
							$("#emptyPipelinelist").val(json.length);
							
							documentpaginationRejected();
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
							prepareAllCounts(requirementId);
							
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
	 
}
</script>

<script>
function documentpaginationRejected(){  
    $('#requirementsPaginationRejected').DataTable( { 
    	"scrollCollapse": false,
//     	"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#requirementsPaginationRejected").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>

<script>  
function requirementPipelineBackout(requirementId,submissionType){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var isSpocActive = '${isSpocActive}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  $("#pipelineLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType,        
         success :function(data) {  
        	 var result =""; 
        	 if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		
						   result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
						  
						   result = result +'<span class="applicant_export">';
							 '<sec:authorize access="hasAnyRole(\'ROLE_EXPORT_PIPELINE\')">';
					            result = result +'<a title="Export" onClick="piplineexport();" class="Clo1"><i class="glyphicon glyphicon-export expIcon"></i></a>';
					            result = result +'<div class="export_pipline" style="display:none">';
					            result = result +'<ul>';
					            result = result +'<li onclick="exportPipelineToPdf('+requirementId+',\''+submissionType+'\');">PDF &nbsp;&nbsp;<i class="fa fa-file-pdf-o"></i></li>';
					            result = result +' <li onclick="exportPipelineToExcel('+requirementId+',\''+submissionType+'\');">Excel &nbsp;&nbsp;<i class="fa fa-file-excel-o"></i></li>';
					            result = result +' </ul>';
					            result = result +'</div>';
					          '</sec:authorize>';
					       result = result +'</span>';
						   
						   result = result + '<div class="require_wrap_table overflow-data filter-position">';
				           result = result + '<div class="clerfix">';
				           result = result + '<table id="requirementsPaginationBackout" class="display" cellspacing="0" width="100%"">';
				           result = result + '<thead>';
				           result = result + '<tr>';
				           result = result + '<th>Name</th>';
				           result = result + '<th>Email</th>';
				           result = result + '<th>Resume</th>';
				           result = result + '<th>Applied on</th>';
					       result = result + '<th>Rate</th>';
					         if(clientSubmissions == 'Y'){
					           result = result + '<th>Sub. Rate</th>';
					         }
					       result = result + '<th>Source</th>';
			        	   result = result + '<th>Back-Out On</th>';
			        	   result = result + '<th>Back-Out By</th>';
				          /*  result = result + '<th>Rejected Stage</th>'; */
				           result = result + '<th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		var report = json[i];
			   	        		var colorHint = "";
			   	        		if(report.lastRateStage != "" && report.lastRateIniLocId != "" && report.lastRateIniType != "" && report.lastRateStage != null && report.lastRateIniLocId != null && report.lastRateIniType != null){
				   	        		if(report.lastRateStage == "rejected"){
				   	        			colorHint = "textDanger";
				   	        		}else if(report.lastRateStage == "accepted"){
				   	        			colorHint = "textSuccess";
				   	        		}else if(orgLocationId != report.lastRateIniLocId && report.lastRateIniUserCode != ""){
				   	        			colorHint = "textWarning";
				   	        		}else if(orgLocationId == report.lastRateIniLocId && report.lastRateIniUserCode == ""){
				   	        			colorHint = "textWarning";
				   	        		}
			   	        		}
			   	        		if(report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
			   	        			orgName = "("+report.orgName+")";
			   	        		}else{
			   	        			orgName = "";
			   	        		}
			   	        		
			   	        		if(report.candidateSource != null && report.candidateSource != '' && report.candidateSource != 'undefined'){
			   	        			candidateSource = "("+report.candidateSource+")";
			   	        		}else{
			   	        			candidateSource = "";
			   	        		}

						           result = result + '       <tr>';
						           '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div onclick="applicantQuickViewProfile('+report.id+','+report.candidateId+'); getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show"><span class="pull-left" title ="'+report.candidateName+'">'+report.candidateName+'</span><i class="fa fa-binoculars"></i></div></td>';
						           '</sec:authorize>';
						           '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_APPLICANT\')">';
						           		result = result + '         <td><div class="tbldata-dotdot proj_show"><span class="pull-left">'+report.candidateName+'</span></div></td>';
						           '</sec:authorize>';
						           
						           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '         <td><div><span title ="'+report.candidateEmail+'">'+report.candidateEmail+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.resumeId != null && report.resumeId != '' && report.resumeId != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeUUID='+report.resumeUUID+'\"></a><a class="fa fa-eye ml10" onclick="viewResumeRequirementSummary(\''+report.resumeUUID+'\',\''+report.filename+'\')"></a></span></div></td>';
							        }
							       else{
							    	   result = result + '         <td><div>--</div></td>';
						    	     }
						           
						        	   
					        	   if(report.createdDateStr != null && report.createdDateStr != '' && report.createdDateStr != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdDateStr+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
							           
						           
						           if(report.vendorLocationId != null && report.sourceSubmission != null){
						        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
						        		   '<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
						        		   		result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
												result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
						        		   '</sec:authorize>';
							    	     }
						            }else{
						        	   if(report.expectedRate != null && report.expectedRate != '' && report.expectedRate != 'undefined'){
											'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
											'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
													result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.expectedRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		'</sec:authorize>';
								           }
								       else{
								    	   var expexted = (report.expectedCTC != '' && report.expectedCTC != 'undefined' && report.expectedCTC != null) ? report.expectedCTC : '<i class="glyphicon fa fa-money"></i>';
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
												'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+expexted+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
							        		   '</sec:authorize>';
							    	     }
						            }
						           
							          
							      if(clientSubmissions == 'Y'){
							           if((report.vendorLocationId == null || report.vendorLocationId == '' || report.vendorLocationId == 'undefined') && (report.sourceSubmission == null || report.sourceSubmission == '' || report.sourceSubmission == 'undefined')){
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.clientSubmissionId+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }else{
							        	   if(report.proposedSubmissionRate != null && report.proposedSubmissionRate != '' && report.proposedSubmissionRate != 'undefined'){
												'<sec:authorize access="hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'" onclick="getRateListDiv('+report.id+','+report.candidateId+');">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
													'<sec:authorize access="!hasAnyRole(\'ROLE_RATE_NEGOTIATION\')">';
														result = result + '         <td><div><span class="showRate '+colorHint+'">'+report.proposedSubmissionRate+'</span><i class="ratepop rate_pop" id="ratepop"></div></td>';
								        		   '</sec:authorize>';
							        	   }else{
							        		   result = result + '         <td><div><span>--</span></div></td>';
							        	   }
							           }
							        }
						           if(report.createdUser != null && report.createdUser != '' && report.createdUser != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.createdUser+''+orgName+'<br/>'+candidateSource+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	   } 
						           result = result + '<td><div class="tblwidth-150"><span>'+report.upDatedStr+'</span>';
						           if(report.updatedUser != null && report.updatedUser != '' && report.updatedUser != 'undefined'){
						        	   result = result + '<td><div class="tblwidth-150"><span>'+report.updatedUser+'</span>';
						           }
					        	  /*  result = result + '<td><div class="tblwidth-150"><span>'+report.previousSubmissionStatus+'</span>'; */
					        	   result = result + '<td><div class="tblwidth-150"><span>'+report.submissionStatus+'</span>';
					        	   /* if(report.rejectedReason != null && report.rejectedReason != '' && report.rejectedReason != 'undefined'){
					        	 		  result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+report.rejectedReason+'</p></div></i></div></td>';
					        	   } */		  
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				           
				           
							$("#tab-1").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#pipelineLoading").removeAttr("style");
							$("#status-data").empty();
							$("#status-data").append(result);
							$("#tab-2").append(status-data);
							$("#loading").removeAttr("style"); 
							$("#emptyPipelinelist").val(json.length);
							
							documentpaginationBackout();
							$("#tab-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
							prepareAllCounts(requirementId);
							
              }else{
                     	 $("#requirementSideDiv").removeAttr("style");
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
      					$("#requirementSideDiv").empty();
      				    $("#requirementSideDiv").append(result); 
                      }
               },
                 
            }); 
	 
}
</script>

<script>
function documentpaginationBackout(){  
    $('#requirementsPaginationBackout').DataTable( { 
    	"scrollCollapse": false,
//     	"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#requirementsPaginationBackout").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>
<div class='project_inn_mask projectMask-zindex'></div>
<div id="popUp"></div>
<div id="customizePopUp"></div>
<script>
function rejectApplicant(submissionId,requirementId,submissionType){
    $("#reasonForApplicantDiv").empty();
    $("#reasonForReject").val("");
    $("#designationType").val('');
    //$("#rejectedCategory").val("");
    $("#rejectedCategory").selectpicker('refresh');
    var subId = submissionId;
    var reqId = requirementId;
    var subType = submissionType;
    var selectBox = $('#rejectApplicantSelectspocState'+submissionId).val();
    if(selectBox == "backout"){
        backOutApplicant(submissionId,requirementId,submissionType);
    }else if(selectBox == "Reject"){
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
        result = result +"        <input id='saveReason' type='button' value='Submit' onclick='saveRejectApplicantReason("+subId+","+reqId+",\""+subType+"\");'><input class='u_can_color' type='button' id='saveReasonCancel' value='Cancel' onClick='cancelRejectApplicant("+subId+","+reqId+",\""+subType+"\");'>";
        result = result +"</form>";
        result = result +"    </div>";
        $("#popUp").append(result);
        $("#rejectApplicant"+submissionId).slideDown(200);
        $(".project_inn_mask").fadeIn(200);
    }
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

<script type="text/javascript">
function cancelRejectApplicant(submissionId,requirementId,submissionType){ 
	$("#rejectApplicant"+submissionId).fadeOut(200);
	$(".project_inn_mask").fadeOut(200);
	 $("#rejectedCategorytDiv").find('p.jquery_form_error_message').remove();
	 document.getElementById("rejectOtherCategoryDiv").style.display="none";
	 $("#rejectedCategory").val("");
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
	}
	
};
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
		var sourceleadUrl = '${sourceleadUrl}';
		var frm = $('#rejected_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var form = $('#rejected_form')[0];
  		var formData = new FormData(form); 
		var jsonData = encodeURIComponent(con);
		$.ajax({
			type : "POST",
    	    data: formData,
    	    contentType : 'application/json; charset=utf-8',
    	    enctype: 'multipart/form-data',
	        processData: false,
	        contentType:false,
    	    dataType : 'json',
		    url: sourceleadUrl+"/rejectApplicant?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&submissionId="+submissionId,	    	   
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
$('body').on('blur', '#rejected_form', function() {
	$("#rejected_form").showHelpOnFocus();
	$("#rejected_form").validateOnBlur(false, validationSettings);
});
</script>
<script>
function backOutApplicant(submissionId,requirementId,submissionType){
	var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/backoutApplicant?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId,	    	   
		    success: function(data) {
   			    requirementPipeline(requirementId,submissionType);
		    }
	   });
}
</script>

<script>
function requirementSubmissionCount(requirementId){  
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsCountBySubmissionStatus?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
         success :function(data) { 
        	 var json = JSON.parse(data);
        	 var report1 = json[0];
        	 
        	 //$("#applicantsCount").html(report1.numOfSubmissions);
        	 $("#shortlistCount").html(report1.numOfShortList);
        	 $("#submitToClientCount").html(report1.numOfSubmitToClient);
        	 $("#interviewCount").html(report1.numOfInterview);
        	 $("#offersCount").html(report1.numOfOffers);
        	 $("#rejectedCount").html(report1.numOfRejected);
        	 $("#requestSent").html(report1.numofrequestsent);
        	 //$("#duplicateApplicantsCount").html(report1.duplicateCount);
         },
	  });
}
</script>


<script>
function filterReq(requirementId,submissionType){
	requirementPipeline(requirementId,submissionType);
}
</script>

<script>
function filterReqDA(requirementId,submissionType){
	requirementPipelineDA(requirementId,submissionType);
}
</script>

<script>
function filterReqRS(requirementId,submissionType){
	requirementPipelineRS(requirementId,submissionType);
}
</script>

<script>
function filterReqSL(requirementId,submissionType){
	requirementPipelineSL(requirementId,submissionType);
}
</script>

<script>
function filterReqIN(requirementId,submissionType){
	requirementPipelineIN(requirementId,submissionType);
}
</script>

<script>
function filterReqOffer(requirementId,submissionType){
	requirementPipelineOffer(requirementId,submissionType);
}
</script>

<script>
function filterReqRejected(requirementId,submissionType){
	requirementPipelineRejected(requirementId,submissionType);
}
</script>

<script>
function filterReqSC(requirementId,submissionType){
	requirementPipelineSC(requirementId,submissionType);
}
</script>

<script>
function filterReqBackout(requirementId,submissionType){
	requirementPipelineBackout(requirementId,submissionType);
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
function assignedMemberList(requirementId){
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getRequirementAssignedListByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
         success :function(data) {  
        	 if(data != null && data != 'undefined' && data != "" && data != '[]'){
           				   	var json = JSON.parse(data);
							var result = '';
							result = result +'     <div class="heightScroll mt10 mb">'; 
						    result = result +'         <table class="table table-responsive table-hover clearfix mb">';
						    result = result +'         <thead>';
						    result = result +'         <tr>';
						    result = result +'         <th>Name</th>';
						    result = result +'         <th>Role</th>';
						    result = result +'         <th>Team</th>';
						    result = result +'         <th>No. of Applicants</th>';
						    result = result +'         </tr>';
						    result = result +'         </thead>';
						    result = result +'         <tbody>';
						    for (var i=0; i<json.length; i++)
			   				{
						    var report = json[i];
						    result = result +'         <tr>';
						    result = result +'         <td><div class="tbldata-dotdot-me">'+report.assignedUserName+'</div></td>';
						    if(report.assignedUserRole != null && report.assignedUserRole !='' && report.assignedUserRole !='undefined'){
						    	result = result +'         <td><div class="tbldata-dotdot-sm">'+report.assignedUserRole+'</div></td>';
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
						     
						     if(report.teamName != null && report.teamName !='' && report.teamName !='undefined'){
						    	 result = result +'         <td><div class="tbldata-dotdot-sm">'+report.teamName+'</div></td>';
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
						    result = result +'         <td><div class="tbldata-dotdot-sm">'+report.applicantCount+'</div></td>';
						    result = result +'         </tr>';
			   				}
						    /* result = result +'         </tbody>';
						    result = result +'         </table>'; */
						    /* result = result +'     </div>';
						    result = result +'     </div>'; */
						     result = result +' </div> '; 
						    
						    $("#assignedMemberList").empty();
						    $("#assignedMemberList").append(result);
                 }else{
						$("#assignedMemberList").removeAttr("style");
       	    	          var result ="";
       	    	       /*  result = result+"   <div class='row'>"; 
      					 result = result+"	<div class='col-lg-12'>"; 
      					 result = result+"		<div class='content-area clearfix'>"; 
      					 result = result+"			<div class='blank-pages'>";
      					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
      					 result = result+"			        <p>No AssignedMember Found</p>";
      					 result = result+"			 </div>";
      					 result = result+"		</div>"; 
      					 result = result+"	</div>"; 
      					 result = result+"  </div> ";  */
      					 result = result+"   <div class='dataTables_empty'>"; 
      					$("#assignedMemberList").empty();
      				    $("#assignedMemberList").append(result); 
                      }
               },
                 
            }); 
}
</script>



<script type="text/javascript">
function requirementRecommendations(requirementId){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
         type : "POST",
          url: sourceleadUrl+"/getRecommendationsCountBasedOnRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,   
          success :function(data) {
        	  for(var i = 0 ; i<= data ; i++){
        		  requirementRecommendationsUsingDataTablesPagination(i,requirementId);
        	  }
         },
     }); 
	 
	
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
	    $("#tab-1").empty();
	    $("#tab-2").empty(); 
	    $("#tab-3").empty();  
	    $("#tab-4").empty();
	    $("#status-div").empty(); 
	    $("#actionsDiv").empty(); 
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
	    urlForm = "/getRequirementNotes?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId;
		
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
         success :function(data) {
		var output = "";
	    if(data != null && data != 'undefined' && data != "" && data != '[]'){
    	var json = JSON.parse(data);
	    output  = output + '   <div class="project_tabs_inn_head addUser-org"> ';
	    output  = output + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
     	for(var i=0;i<json.length;i++){
       	var report = json[i];
    	if(report.checkBox == 'Y' || (report.checkBox == 'N' && '${SourceLeadWeb_user.id}' == report.userIdStr)){
   	  	output = output+'<div class="comments-div comments-inner commentsNotes clearfix">';
   	    output = output+'    <div class="commentsimg">';
   	    output = output+'    <i class="fa fa-comments"></i>';
   	    output = output+'        <div class="commentsimg-inner">';
   	    output = output+'        	<img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+report.commentAuthorUserId+'"/>';
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
	      		
	      		var userId = '${SourceLeadWeb_user.id}';
	      		formData.append("userId",userId);
	      	    var locationCode ='${SourceLeadWeb_locationCode}'; 
	      	    formData.append("locationCode",locationCode);
	      	    
				var sourceleadUrl = '${sourceleadUrl}';
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
						url : sourceleadUrl+"/storeNotes?${_csrf.parameterName}=${_csrf.token}",
						success : function(result) {
							closeDocPopup();
							reqNotes(requirementId);
							
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

<script type="text/javascript">
function requirementRecommendations(requirementId){
	$("#actionsDiv" ).hide();
	  var sourceleadUrl = '${sourceleadUrl}';
	$("#recommendationsLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  requirementRecommendationsDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  requirementRecommendationsDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  requirementRecommendationsDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  requirementRecommendationsDate = 'YYYY-DD-MM'; 
	  }  
	
	
	$.ajax({
    type : "POST",
     //url: sourceleadUrl+"/getRequirementRecommendationsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&count="+count,        
     url: sourceleadUrl+"/getRequirementRecommendationsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,
     success :function(data) {
    	 var json = JSON.parse(data);
    	    var result = '';
    	    
    	     result = result +' <div class="clients_side_wrap">';
			 result = result +'<div class="clients_side_wrap_inn quickviewTabs">';
			 result = result +'<div id="candidateCompare" ></div>';
			 result = result + '<ul class="nav nav-tabs">';
			 result = result +' <li id="quickviewTabsqv" class="active"><a data-toggle="tab" href="#quickresume_view">Quickview</a></li>';
			 result = result +'<li id="quickviewTabsdetails"><a data-toggle="tab" href="#detailrecommondations">Detailed view</a></li>';
			 result = result +'</ul>';
			 
			 
		     result = result +'<div class="tab-content">';
			 
			 result = result +'<div id="quickresume_view" class="tab-pane fade in active">';
			 
			 result = result +'<div id="recapplicantQuickViewDiv"></div>';
			 
			 result = result +'</div>';
			 
			 result = result +'<div id="detailrecommondations" class="tab-pane fade">';			 
			 
			 result = result +' <div class="project_side_wrap_inn_tabs"> ';
			 result = result + '<div id="candidateSideDiv"></div>';
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'</div>';

			 result = result +'</div>';
			 result = result +'</div>';
    	    
    	    //--------------------------------
    	    //result = result +'<div class="timesheet_head candidateHead recruitmentHead">';
    	    result = result +'<div class="clearfix timesheetHead">';
		    result = result +'<div class="rec_head">';

		    result = result +'		   <div class="pull-left cand-advsrch">																							';
		    result = result +'		    <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="Customize score" onClick="openPop('+requirementId+')">Customize Score </a>            ';
		    if(json.length>0){
		    	result = result +'			<a class="comTextLi" title="Compare" onclick="compareRecommendedUsers('+requirementId+');" >Compare</a>            ';
		    }
		    result = result +'                   <div id="customizeScoreDiv"></div>';
		    result = result +'		        </div>';

		    
		    
		    result = result +'</div>';
		    result = result +'</div>';
		    result = result +'<div class="project_tabs_inn_table  clients_wrap_table">';
		    result = result +'<table id="recommendationTable" class="display" cellspacing="0" width="100%"">';
		    result = result +'<thead>';
		    result = result +'<tr>';
		    result = result +'<th>Name</th>';
		    result = result +'<th>Email</th>';
		    result = result +'<th>City</th>';
		    result = result +'<th>Phone</th>';
		    result = result +'<th style="width:50px;">Resume</th>';
		    result = result +'<th>Number Of Jobs</th>';
		    result = result +'<th>Updated Date <br>(<small class="custom-small">'+requirementRecommendationsDate+'</small>)</th>';
		    result = result +'<th>Score</th>';
		    result = result +'<th>Action</th>';
		    result = result +'</tr>';
		    result = result +'</thead>';
		    result = result +'<tbody>';
    	 for (var i=0; i<json.length; i++)
			{
		    var report = json[i];
		    if(report.firstName != null && report.firstName != '' && report.firstName != 'undefiend'){
		    	firstName = report.firstName;
		    }else{
		    	firstName = "";
		    } 
		    if(report.lastName != null && report.lastName != '' && report.lastName != 'undefiend'){
		    	lastName = report.lastName;
		    }else{
		    	lastName = "";
		    } 
		    result = result +'<tr>';
		    result = result + '<td><div onclick="recomendcandidateQuickViewProfile('+report.candidateId+','+report.finalScore+'); clientSideBar('+report.candidateId+','+report.finalScore+');" class="proj_show tbldata-dotdot"><span title="'+firstName+" "+lastName+'">'+firstName+" "+lastName+'</span><i class="fa fa-binoculars"></i>';
		    if(report.finalScore != null && report.finalScore != '' && report.finalScore != 'undefined'){
				  if(report.finalScore >=50){ 
				 	 result = result +'<small class="valid-profile validProfile fa fa-check-square text-success"></small>';        
				  }
			  } 
		    result = result +' </div></td>';
/* 		    result = result +'<td><div class="proj_show"><span>'+report.firstName+'</span><i class="fa fa-binoculars"></i></div></td>'; */
		    if(report.email != null && report.email !='' && report.email !='undefined'){
		    	result = result +'<td><div title='+report.email+' class="tbldata-dotdot-sm">'+report.email+'</div></td>';
		    }else{
		    	result = result +'<td><div><span>--</span></div></td>';
		    }
		    
		    if(report.city != null && report.city !='' && report.city !='undefined'){
		    	result = result +'<td><div><span>'+report.city+'</span></div></td>';
		    }else{
		    	result = result +'<td><div><span>--</span></div></td>';
		    }
		    
		    if(report.mobilePhone != null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
		    	result = result +'<td><div><span>'+report.mobilePhone+'</span></div></td>';
		    }else{
		    	result = result +'<td><div><span>--</span></div></td>';
		    }
		   
		  if(report.currentResumeId != null && report.currentResumeId !='' && report.currentResumeId !='undefined' ){
			 var file =  report.currentResumeName;
			  var fileName = file.substr(0,5);
		  	   result = result +'<td class="text-center"><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadCandidateResume?candidateId='+report.candidateId+'\"></a><a class="fa fa-eye ml10" onclick="viewAssociateCandidateResumeDocView('+report.candidateId+',\''+report.currentResumeName+'\')"></a></span></div></td>';
			  }else{
				result = result +'<td class="text-center"><div><span>--</span></div></td>';
			} 
		   result = result +'<td class="text-center"><div class="show-tooltip"><span>'+report.noOfReq+'</span>';
		    result = result +' </div></td>';
		    
		    if(report.updatedDate != null && report.updatedDate !='' && report.updatedDate !='undefined'){
		    	result = result +'<td><div class="tblminwidth-100"><span>'+report.updatedDate+'</span></div></td>';
		    }else{
		    	result = result +'<td><div><span>--</span></div></td>';
		    }
		    
		    result = result +'<td class="text-center"><div class="show_scroe"><span>'+report.finalScore+'%</span>';
		    result = result +'<div class="scroe">';
		    
		    result = result +'<div class="clearfix row">';
		    
		    result = result +'<div class="col-md-6 paddingRight">';
		    result = result +'<label>Skills</label>';
		    result = result +'<div class="progress">';
		    var skillGraph = (100 * report.skillPercentage)/report.totalSkillPercentage;
		    result = result +'<div class="progress-bar progress-bar-success" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+skillGraph+'%"> '+report.skillPercentage+'%';
   	        result = result +'</div>';
   	        result = result +'</div>';
		    result = result +' </div>';
		    
		    result = result +'<div class="col-md-6 paddingLeft">';
		    result = result +'<label>Location</label>';
		    result = result +'<div class="progress">';
		    var locationGraph = (100 * report.locationPercentage)/report.totalLocationPercentage;
		    result = result +'<div class="progress-bar progress-bar-warning" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+locationGraph+'%"> '+report.locationPercentage+'% ';
		    result = result +'</div>';
		    result = result +'</div>';
		    result = result +' </div>';
		    
		    result = result +' </div>';
		    
		    
		    result = result +'<div class="clearfix row">';
		    
		    result = result +'<div class="col-md-6 paddingRight">';
		    result = result +'<label>Experience</label>';
		    result = result +'<div class="progress">';
		    var exGraph = (100 * report.expPercentage)/report.totalExpPercentage;
		    result = result +'<div class="progress-bar progress-bar-info" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+exGraph+'%"> '+report.expPercentage+'% ';
   	        result = result +'</div>';
		    result = result +' </div>';
		    result = result +' </div>';
		    
		    result = result +'<div class="col-md-6 paddingLeft">';
		    result = result +'<label>Rate</label>';
		    result = result +'<div class="progress">';
		    var rateGraph =  (100 * report.ratePercentage)/report.totalRatePercentage;
		    result = result +'<div class="progress-bar progress-bar-warning" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+rateGraph+'%"> '+report.ratePercentage+'% ';
   	        result = result +'</div>';
		    result = result +' </div>';
		    result = result +' </div>';
		    
		    result = result +' </div>';
		    
		    result = result +'<div class="clearfix row">';
		    
		    result = result +'<div class="col-md-6 paddingRight">';
		    result = result +'<label>Notice Period</label>';
		    result = result +'<div class="progress">';
		    var noticePeriodGraph = (100 * report.noticePeriodPercentage)/report.totalNoticePeriodPercentage;
		    result = result +'<div class="progress-bar progress-bar-danger" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+noticePeriodGraph+'%"> '+report.noticePeriodPercentage+'% ';
   	        result = result +'</div>';
		    result = result +' </div>';
		    result = result +' </div>';
		    
		    result = result +'<div class="col-md-6 paddingLeft">';
		    result = result +'<label>Keyword</label>';
		    result = result +'<div class="progress">';
		    var keywordGraph = (100 * report.totalKeyWordPerPercentage)/report.keyWordPerPercentage;
		    result = result +'<div class="progress-bar progress-bar-primary" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+keywordGraph+'%"> '+report.totalKeyWordPerPercentage+'% ';
   	        result = result +'</div>';
		    result = result +' </div>';
		    result = result +' </div>';
		    
		    result = result +' </div>';
		    
		    
		    
 			result = result +'<div class="clearfix row">';
		    
		    result = result +'<div class="col-md-6 paddingRight">';
		    result = result +'<label>Visa Type</label>';
		    result = result +'<div class="progress">';
		    var visaTypeGraph = (100 * report.visaTypePercentage)/report.totalVisaTypePercentage;
		    result = result +'<div class="progress-bar progress-bar-success" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+visaTypeGraph+'%"> '+report.visaTypePercentage+'% ';
   	        result = result +'</div>';
		    result = result +' </div>';
		    result = result +' </div>';
		    
		    result = result +'<div class="col-md-6 paddingLeft">';
		    result = result +'<label>Education</label>';
		    result = result +'<div class="progress">';
		    var educationGraph = (100 * report.educationPercentage)/report.totalEducationPercentage;
		    result = result +'<div class="progress-bar progress-bar-primary" role="progressbar"  aria-valuemin="0" aria-valuemax="100" style="width:'+educationGraph+'%"> '+report.educationPercentage+'% ';
   	        result = result +'</div>';
		    result = result +' </div>';
		    result = result +' </div>';
		    
		    result = result +' </div>';
		  
		    
		   
		    result = result +'</div>';		    
		    result = result +'</div></td>';
		    
		    if(report.isCandidateSubmitted == 'N'){
		    	result = result +'<td class="text-center"><div id="recommendedCandidate'+report.candidateId+'"><a href="javascript:void(0)" title="" onclick="submitRecomendedApplicant('+requirementId+','+report.candidateId+');" class="add-applicant">Add</a></div>';
		    }else{
		    	result = result +'<td class="text-center"><div class="text-success">Added</div>';
		    }
		    
		  
		    
		    /*  result = result +'<td><div><span>';
		    if(report.isCandidateAdedd == true){
		    	result = result +'<input type="button" value="Added" class="btn btn-primary btn-sm">';
		    }else{
		    	result = result +'<input type="button" value="--" class="btn btn-primary btn-sm">';
		    } */
		    
		   /*  result = result +'<select>';
		    result = result +'<option>Interview</option>';
		    result = result +'<option>Submit</option>';
		    result = result +' <option>Joined</option>';
		    result = result +'<option>Screening</option>';
		    result = result +'<option>Offer</option>';
		    result = result +'</select>' */;
		   /*  result = result +'</span></div>';
		    result = result +'</td>'; */
		    result = result +'</tr>';
		   
			}
    	    result = result +'</tbody>';
		    result = result +' </table>';
		    result = result +'</div>';
		    $("#tab-1").empty();
			$("#tab-2").empty(); 
		    $("#tab-3").empty();
		    $("#tab-4").empty();
		    $("#recommendationsLoading").removeAttr("style");
		    $("#status-div").empty();
		    $("#status-data").empty();
			$("#tab-3").append(result);
			recommendationpagination();
			$("#tab-3").show();
			$(".project-tabs-menu a").click(function(event) {
			     event.preventDefault();
			     $(this).parent().addClass("current");
			     $(this).parent().siblings().removeClass("current");
			     var tab = $(this).attr("href");
			     $(".project_tabs_inn").not(tab).css("display", "none");
			     $(tab).fadeIn();
			 });
           },
             
        }); 
}
</script>

<script>
function viewAssociateCandidateResumeDocView(candidateId,title){
	var sourceleadUrl ='${sourceleadUrl}';
	if(candidateId != null && candidateId != ""){
		window.open(sourceleadUrl+'/viewAssociateCandidateResumeDocView/'+title+'?candidateId='+candidateId,title,"height=600,width=600");
	}  
}
</script>

<script>
function recommendationpagination(){  
    $('#recommendationTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"aaSorting": [[7,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    //	$("#recommendationTable").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
</script>

<script>
function getActions(){
	$(".action-pop").toggle();
	/* $(".export_pipline").slideUp(200);
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
 	}); */
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
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getCandidateById?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
           success :function(data) { 
                if(data != null && data != 'undefined' && data != ""){  
                	 var json = JSON.parse(data);
              		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
              		 else if (data.indexOf('exception') >= 0) {
              			 location.replace(sourceleadUrl+"/errorPage");
              		 }
              		 else{
              			 candidateStatusGlobal = json.status;
	                	 $("#candidateSideDiv").empty();
	                	/*  var skillsList = json.skillsList;
			             var skills = [];
			             skills = skillsList.split(","); */
			             
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
						 result = result+'   <img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+candidateId+'"/>" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 if(recScore != null && recScore != '' && recScore != 'undefined'){
							  if(recScore >=50){ 
							 	 result = result +'                    <i class="valid-profile fa fa-check-square text-success"></i>';        
							  }
						  }
						 result = result+'  <div class="btn-group uploadfile">';
						 result = result+'  <button type="button" class="btn btn-sm btn-primary">';
						 result = result+'  <i class="fa fa-upload"></i></button>';
						 result = result+'  <label class="btn btn-sm btn-primary">';
					     result = result+'    Upload / Change<input type="file" name="file" id="uploadedImage"  onchange="loadFile(event,\''+candidateId+'\')" style="display: none;">';
					     result = result+'  </label>';
					     if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
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
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif are not support</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Size should not more than 200kb</p>';
						 result = result+' </div>';
						 result = result+' </div>';
						 result = result+'</form>';
			             result = result+'                     <div class="media-body candidate-details">';
			             
			             result = result+' <h4 class="media-heading">'+json.firstName+' '+lastName+'</h4>';
			            
			             result = result+'<ul class="list-unstyled">';
			             result = result+'<li>Type - '+json.candidateType+'</li>';
			             
			                                                       if(json.experienceYears != null && json.experienceYears != '' && json.experienceYears != 'undefined'){
			             result = result+'<li>Experience - '+json.experienceYears+' years '+json.experienceMonths+' months </li>';
              		                                                  }else{
              		                                                	  
              		     result = result+'<li>Experience - ----</li>';  
              		                                                  }
                                                                   if(json.primarySkill != null && json.primarySkill != '' && json.primarySkill != 'undefined'){
                	     result = result+'  <li>Primary Skill - '+json.primarySkill+'</li>';
                              		                                   }else{
                              		                                                	  
                         result = result+'<li>Primary Skill - ----</li>';  
                              		                                    }
			             result = result+'                       </ul>';
			             result = result+'                 <div class="clearfix">';
			             result = result+'                 <div class="socialnetwork-details">';
			             result = result+'                 <ul class="list-inline list-unstyled">';
			             if(json.facebookUrl != null && json.facebookUrl != '' && json.facebookUrl != 'undefined'){
			            	 result = result+'<li class="bg-facebook"><a href="'+json.facebookUrl+'"target="_blank"> <i class="fa fa-facebook-square"></i></a></li>';
			            	 }
			             if(json.linkedUrl != null && json.linkedUrl != '' && json.linkedUrl != 'undefined'){
			            	 result = result+'<li class="bg-linked"><a href="'+json.linkedUrl+'"target="_blank"> <i class="fa fa-linkedin-square"></i></a></li>';
			            	 }
			             if(json.twitterUrl != null && json.twitterUrl != '' && json.twitterUrl != 'undefined'){
			            	 result = result+'<li class="bg-twitter"><a href="'+json.twitterUrl+'"target="_blank"> <i class="fa fa-twitter-square"></i></a></li>';
			            	 }
// 			             if(json.web != null && json.web != '' && json.web != 'undefined'){
// 			            	 result = result+'<li class="bg-web"><a href="'+json.twitterUrl+'"target="_blank"> <i class="fa fa-globe"></i></a></li>';
// 			            	 }
			             
			             result = result+'                               </ul>';
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
			             
			             result = result+'                   <li><i class="fa fa-money" title="Current Rate"></i>'+currentRate+'  '+currentRateType+' / '+currentRateCurrency+'</li>';
			             
			             result = result+'                   <li><i class="fa fa-money" title="Expected rate"></i>'+expectedRate+'  '+rateType+' / '+expectedRateCurrencey+'</li>';
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
			             
			             result = result+'       <li><a href="#tab-16" onclick="candidateActivities('+candidateId+');">Activities</a></li>'; 
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
       
       result = result + '<div class="reportsCount reportsCountNew">';
		result = result + '<ul>';
		result = result + '<li title="Applicants" id="applicantli" onclick="filterReq('+requirementId+',\''+all+'\');tabEnable(this);" class="reqAct">';
		result = result + '	<span><i class="fa fa-users"></i></span><b id="applicantsCount">0</b>';
		result = result + '</li>';
		result = result + '<li title="Duplicate Applicants" class="colorTwo" id="duplicataplctli" onclick="filterReqDA('+requirementId+',\''+duplicateApplicantCount+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-files-o"></i></span><b id="duplicateApplicantsCount">0</b>';
		result = result + '</li>';
		result = result + '<li title="Request Sent" id="requestSentli" onclick="filterReqRS('+requirementId+',\''+requestSent+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-check"></i></span><b id="requestSent">0</b>';
		result = result + '</li>';
		result = result + '<li title="Shortlisted" class="colorTwo" id="shortListli" onclick="filterReqSL('+requirementId+',\''+shortList+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-list-ul"></i></span><b id="shortlistCount">0</b>';
		result = result + '</li>';
		if(clientSubmissions == 'Y' && isSpocActive == 'Y'){
		result = result + '<li title="Submit to Client" id="submitToClientli" onclick="filterReqSC('+requirementId+',\''+submitToClient+'\');tabEnable(this);">';
		result = result + '	<span class="cliColor"><i class="fa fa-send"></i></span><b id="submitToClientCount">0</b>';
		result = result + '</li>';
		}
		result = result + '<li title="Interviews" class="colorTwo" id="interviewli" onclick="filterReqIN('+requirementId+',\''+interview+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-handshake-o"></i></span><b id="interviewCount">0</b>';
		result = result + '</li>';
		result = result + '<li title="Offers" id="offerMadeli" onclick="filterReqOffer('+requirementId+',\''+offerMade+'\');tabEnable(this);">';
		result = result + '	<span><i class="fa fa-envelope-open-o"></i></span><b id="offersCount">0</b>';
		result = result + '</li>';
		result = result + '<li title="Rejected" class="colorTwo" id="rejectedli" onclick="filterReqRejected('+requirementId+',\''+rejected+'\');tabEnable(this);">';
		result = result + '	<span class="rejColor"><i class="fa fa-ban"></i></span><b id="rejectedCount">0</b>';
		result = result + '</li>';
		result = result + '<li title="Back-Out" id="backoutli" onclick="filterReqBackout('+requirementId+',\''+backout+'\');tabEnable(this);">';
		result = result + '	<span class="outColor"><i class="fa fa-arrow-circle-left"></i></span><b id="backoutCount">0</b>';
		result = result + '</li>';
		result = result + '<div class="clearfix"></div>';
	    result = result + '</ul>';
        result = result + '</div>';
       
        /* result = result + '  <div class="clearfix pipeline-count">';
		 result = result + '    <ul class="list-unstyled list-inline">';
		 result = result + '     <li id="applicantli" class="pipeline-active tabActivation" onclick="filterReq('+requirementId+',\''+all+'\');tabEnable(this);"> <a href="javascript:void(0)" title="Applicants">';
		 result = result + '       <label><strong>Applicants</strong></label>';
		 result = result + '       </a> <span class="label label-default" id="applicantsCount">0</span> </li>';
		 
		 result = result + '     <li id="duplicataplctli" class="tabActivation" onclick="filterReqDA('+requirementId+',\''+duplicateApplicantCount+'\');tabEnable(this);"> <a href="javascript:void(0)" title="Duplicate Applicants">';
		 result = result + '       <label><strong>Duplicate Applicants</strong></label>';
		 result = result + '       </a> <span class="label label-default" id="duplicateApplicantsCount">0</span> </li>';
		 
		 result = result + '   <li id="requestSentli" class="tabActivation" onclick="filterReqRS('+requirementId+',\''+requestSent+'\');tabEnable(this);"> <a href="javascript:void(0)" title="RequestSent">';
         result = result + '     <label><strong>Request Sent</strong></label>';
         result = result + '      </a><span class="label label-warning" id="requestSent">0</span></li>';
		 
		 result = result + '      <li id="shortListli" class="tabActivation" onclick="filterReqSL('+requirementId+',\''+shortList+'\');tabEnable(this);"> <a href="javascript:void(0)" title="Shortlisted">';
		 result = result + '       <label><strong>Shortlisted</strong></label>';
		 result = result + '     </a> <span class="label label-warning" id="shortlistCount">0</span></li>';
		if(clientSubmissions == 'Y' && isSpocActive == 'Y'){
		 result = result + '    <li id="submitToClientli" class="tabActivation" onclick="filterReqSC('+requirementId+',\''+submitToClient+'\');tabEnable(this);"> <a href="javascript:void(0)" title="Submit to Client">';
		 result = result + '       <label><strong>Submit to Client</strong></label>';
		 result = result + '       </a> <span class="label label-info" id="submitToClientCount">0</span> </li>';
		}
		 result = result + '    <li id="interviewli" class="tabActivation" onclick="filterReqIN('+requirementId+',\''+interview+'\');tabEnable(this);"> <a href="javascript:void(0)" title="Interviews">';
		 result = result + '      <label><strong>Interviews</strong></label>';
		 result = result + '      </a><span class="label label-primary" id="interviewCount">0</span></li>';
		 result = result + '     <li id="offerMadeli" class="tabActivation" onclick="filterReqOffer('+requirementId+',\''+offerMade+'\');tabEnable(this);"> <a href="javascript:void(0)" title="Offers">';
		 result = result + '      <label><strong>Offers</strong></label>';
       	 result = result + '      </a><span class="label label-success" id="offersCount">0</span> </li>';
         result = result + '   <li id="rejectedli" class="tabActivation" onclick="filterReqRejected('+requirementId+',\''+rejected+'\');tabEnable(this);"> <a href="javascript:void(0)" title="Rejected">';
         result = result + '     <label><strong>Rejected</strong></label>';
         result = result + '      </a><span class="label label-warning" id="rejectedCount">0</span></li>';
         result = result + '   <li id="backoutli" class="tabActivation" onclick="filterReqBackout('+requirementId+',\''+backout+'\');tabEnable(this);"> <a href="javascript:void(0)" title="Back-Out">';
         result = result + '     <label><strong>Back-Out</strong></label>';
         result = result + '      </a><span class="label label-warning" id="backoutCount">0</span></li>';
         
         result = result + '   </ul>';
        
         result = result + ' </div>';  */
         result = result +' <div> <i class="ratepop rate_pop" id="ratepop"></div>';
         
      $("#status-div").empty();
      $("#status-div").prepend(result);
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
	  var sourceleadUrl ='${sourceleadUrl}';
	  var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	  
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
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
         url: sourceleadUrl+"/getRateHistoryBySubmissionId?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&candidateId="+candidateId+"&orgLocationId="+orgLocationId,        
         success :function(data) {
        	 if(data != null && data != 'undefined' && data != "" && data != '[]'){
            	 var json = JSON.parse(data);
          		 if( data.indexOf('Sign In') >= 0){
		    		location.replace(sourceleadUrl);
		    	 }
          		 /* else if (data.indexOf('exception') >= 0) {
          			 location.replace(sourceleadUrl+"/errorPage");
          		 } */else{
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
				                     
				                    /*  if(report.rateAndType != null && report.rateAndType != 'undefined' && report.rateAndType !=''){
				                    	 result = result + '        <td><div class="tblminwidth-75">'+report.rateAndType+'</div></td>';
				                     }else if(report.expectedRate != null && report.expectedRate != 'undefined' && report.expectedRate !=''){
				                    	 result = result + '        <td><div class="tblminwidth-75">'+report.expectedRate+'</div></td>';
				                     }else{
				                    	 result = result + '        <td><div class="tblminwidth-75">---</div></td>';
				                     } */
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
		                     result = result + '<div class="clearfix row">';
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
		                     result = result + '      <input type="button" value="Cancel" class="btn btn-default btn-sm" onClick="closeratediv();">';
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
          		 	}
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
         	},
      	}); 
	};
</script>


<script type="text/javascript">
	function proposeNewRate(submissionId, candidateId) {
		 if ($('#rate_form').validate(false, validationSettings)){
			var sourceleadUrl ='${sourceleadUrl}';
			var frm = $('#rate_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			//It will escape all the special characters
		    var jsonData = encodeURIComponent(con);
		    $("#add_rate").attr("disabled", "disabled");
			$.ajax({
			type: "POST",
			url: sourceleadUrl+"/proposeRate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&submissionId="+submissionId+"&candidateId="+candidateId,
				success: function(data) {
					 $("#ratepop").fadeOut();
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
			var sourceleadUrl ='${sourceleadUrl}';
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
			type: "POST",
			url: sourceleadUrl+"/acceptRejectRate?${_csrf.parameterName}=${_csrf.token}&submissionId="+submissionId+"&status="+status,
				success: function(data) {
					 $("#ratepop").fadeOut();
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
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var emptypilpeline = $("#emptyPipelinelist").val();
      if(emptypilpeline > 0 ){
          window.location.href = sourceleadUrl+"/getSubmissionsExportByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType+"&exportType=xls";
	  }else{
		  var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
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
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  var emptypilpeline = $("#emptyPipelinelist").val();
	  if(emptypilpeline > 0 ){
		  window.location.href = sourceleadUrl+"/getSubmissionsExportByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&submissionType="+submissionType+"&exportType=pdf";
	  }else{
		  var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
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
		function closePop(){
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
	$("#customizeScoreDiv").show();
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/getCustomizeScore?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId, 	   
		    success: function(data) {
		    	var json = JSON.parse(data);
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
		    	var lookupSkills = json.dbSkills;
		    	var dbskills = new Array();
		    	dbskills = lookupSkills.split(",");
		    	
		    	
		    	var result = "";
		    	result = result +'		          <div class="cand-advsrch-popup customizescore-pop customscorePop">  ';
		    	result = result	+ ' <form id="updateCustomize_form"  name="updateCustomize_form" method="post">';
		    	result = result +'		            <div class="row clearfix minDiv"> ';
		        result = result +'		                <div class="clearfix mt">  ';
		        
		        result = result +'		                <div class="col-md-10 lblup paddingRight">    ';
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
		        result = result +'		                </div> ';
		        
		        result = result +'		                <div class="clearfix mt">   ';
		        result = result +'		                  <div class="col-md-3 paddingRight lblup">    ';
		        result = result +'		                   <label>Location</label>';
		        result = result +'		                    <select class="chosen-select" data-placeholder="Country" id="country" name="customizeCountry" value="'+customizeCountry+'" readonly="readonly"> ';
		        result = result +'		                    </select>                ';
		        result = result + '                        <input type="hidden"  id="existingCountry" value="'+customizeCountry+'" >';
		        result = result +'		                  </div>                     ';
		        
		        
		        result = result +'		                    <div class="col-md-4 paddingLeft paddingRight">   ';
		        result = result +'		                    <select class="chosen-select" data-placeholder="State" name="customizeState" id="state" value="'+customizeState+'" readonly="readonly">';
		        result = result +'		                    </select>                    ';
		        result = result + '                         <input type="hidden"  id="existingState" value="'+customizeState+'" >';
		        result = result +'		                  </div>  ';
		        
		        
		        result = result +'		                  <div class="col-md-3 paddingLeft paddingRight">         ';
		        result = result +'		                    <select class="chosen-select" data-placeholder="City" name="customizedCity" id="city" value="'+customizeCity+'" readonly="readonly">    ';
		        result = result +'		                    </select>                  ';
		        result = result +'                           <input type="hidden"  id="existingCity" value="'+customizeCity+'" >';
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
		        
		        result = result +'		                </div>                        ';
		        
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
	        	 result = result  +' <option value="Immediately Available" '+(noticeperiod.includes("Immediately Available") ? "selected" : '') +'>Immediately available</option>';
	        	 result = result  +' <option value="15 Days" '+(noticeperiod.includes("15 Days") ? "selected" : '') +'>15 Days</option>';
	        	 result = result  +' <option value="1 Month" '+(noticeperiod.includes("1 Month") ? "selected" : '') +'>1 Month<s/option>';
	        	 result = result  +' <option value="2 Months" '+(noticeperiod.includes("2 Months") ? "selected" : '') +'>2 Months</option>';
	        	 result = result  +' <option value="3 Months" '+(noticeperiod.includes( "3 Months") ? "selected" : '') +'>3 Months</option>';
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
		        
		        result = result +'		           <div class="clearfix mt">     ';
		        
		        
		        result = result +'		                  <div class="col-md-5 lblup">   ';
		        result = result +'		                   <label>Rate</label>';
		        result = result +'		                    <input type="text" placeholder="Rate" class="form-control input-sm" value="'+customizeRate+'" id="customizeRate" name="customizeRate" data-validation="validate_Space validate_float validate_length length1-8" data-validation-optional="true">  ';
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
		      
		        
		        
		        
// 		        =========================================================================================================================
		        
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
		        
		        var lookupVisaType = json.dbVisaType;
		    	var dbVisaType = new Array();
		    	dbVisaType = lookupVisaType.split(",");
		    	
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
		        
		        var lookupEducation = json.dbEducation;
		    	var dbEducation = new Array();
		    	dbEducation = lookupEducation.split(",");
		    	
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
		      
		        result = result +'		              </div>                          ';
		        
		        result = result +'		            <div class="row form-footer mt10">                                              ';
		        result = result +'		              <div class="pull-right mr"> <a href="javascript:void(0)" title="Submit" class="btn btn-sm btn-primary" onclick="updateCutomize('+requirementId+');">Customize</a> <a href="javascript:void(0)" title="Cancel" class="btn btn-sm btn-default ml10" onclick="closePop();">Cancel</a> </div>';
		        result = result +'		            </div>'; 
		        result = result	+ '               <form>';
		        result = result +'		            </div>'; 
		        
		        $("#customizeScoreDiv").empty();
		    	$("#customizeScoreDiv").append(result);
		    	getAllCountries();
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
		    	
		    	    $("#country").chosen();
		            $("#state").chosen();
		            $("#city").chosen();
		            
		            $("#country").on('change', function (e) {
		         	   getStates();
		            });
		           
		            $("#state").on('change', function (e) {
		         	   getCities();
		            });
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
		 		       
		    			   //Adding Customized Skills to the list
		    	        var select, chosen;
		    		    select = $("#customizeSkills");
		    		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
		    		    chosen = select.data('chosen');
		    		   
		    		    chosen.search_field.on('keydown keyup', function(e)
		    				    {
		    				    	   var skillsArray = this.value.split(",");
		    					    	 if(skillsArray.length > 1){
		    				        		 for (var i=0; i < skillsArray.length; i++) {
		    				        			 var option = $("<option>").val(skillsArray[i].replace(",","")).text(skillsArray[i].replace(",",""));
		    				        			 select.append(option);
		    				 		             select.find(option).prop('selected', true);
		    				 		             select.find(option).siblings("[value='"+ skillsArray[i].replace(",","")+"']").remove();
		    				 	        		 $("#customizeSkills").trigger("chosen:updated");
		    				                 }
		    					    	 }else{
		    				    			var newSkill = this.value.replace(","," ");
		    				    			 if (tagItBykeyForSkills(e) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
		    				                {
		    				    				 preventTabkey(e);
		    				    				 var isNewSkill = true;
		    				                     $("#customizeSkills option:selected").each(function(){
		    										 if(this.value.toUpperCase() === newSkill.toUpperCase()){
		    												isNewSkill = false;
		    												return false;
		    										 }
		    				                   });
		    								   if(isNewSkill){
		    										select.append(option);
		    								   }
		    				    				 
		    				                   $("#customizeSkills").trigger("chosen:updated");
		    				                }
		    						       
		    			    	         }
		    		    	});
		    		    
		    		    //Adding Customized Skills to the list
		    		    
		    		    
		    		  //Adding Customized Any Of Keyword to the list 
		    		     var selectone, chosenone;
		    		     selectone = $("#anyKeyWord");
		    		     selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    		    chosenone = selectone.data('chosen');
		    		    chosenone.search_field.on('keydown keyup', function(e5)
		    		    {
		    		    	var anyofKeyword = this.value.replace(",","");
		    		        if (tagItBykeyForSkills(e5) && (anyofKeyword != null  && anyofKeyword != '' && anyofKeyword != 'undefined'))
		    		        {
		    		        	 preventTabkey(e5);
		    		        	var par = this.parent;
		    		            var option = $("<option>").val(anyofKeyword).text(anyofKeyword);
		    		            selectone.append(option);
		    		            selectone.find(option).prop('selected', true);
		                        $("#anyKeyWord option").each(function(){
		                              $(this).siblings("[value='"+ this.value+"']").remove();
		                        });
		    	        		$("#anyKeyWord").trigger("chosen:updated");
		    	        		
		    		        }
		    		    }); 
		    		    
		    		    
		    		    //Adding Customized Educations to the list 
		    		    var selectTwo, chosenTwo;
		    		    selectTwo = $("#education");
		    		    selectTwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Education:' });
		    		    chosenTwo = selectTwo.data('chosen');
		    		    chosenTwo.search_field.on('keydown keyup', function(e1)
		    		    {
		    		    	var newEducation = this.value.replace("\'","");
		    		    	   if (tagItBykeyForSkills(e1) && (newEducation != null  && newEducation != '' && newEducation != 'undefined'))
		    			        {
		    		    		   preventTabkey(e1); 
		    		    		   var par = this.parent;
		    			            var option = $("<option>").val(newEducation).text(newEducation);
		    			            selectTwo.append(option);
		    			            selectTwo.find(option).prop('selected', true);
		    			            
		    			            $("#education option").each(function(){
		    	                         $(this).siblings("[value='"+ this.value+"']").remove();
		    	                   });
		    			            
		    			            $("#education").trigger("chosen:updated");
		    			           
		    			        }
		    		    
		    		    });
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

<script type="text/javascript">
function getAllCountries(){
    setTimeout(function(){ 
		var countryName = $("#existingCountry").val();
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/sourceLeadCountryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#country').empty();
	        	$('#country').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == countryName){
	            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}else if(json[i] == '${loginCountryName}'){
	            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            	
	            }
	            getStates();
	            $('#country').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
    }, 75);
} 
</script>


<script>
function getStates(){
	var countryName = $("#country").val();
	
	//to set existing state value(Auto populate)
	var stateName = $("#state").val();
	if(stateName == null || stateName == 'undefined' || stateName == ''){
		stateName = document.getElementById("existingState").value;
	}
	//end state Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == stateName){
	            		$('#state').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#state').trigger("chosen:updated");
	            getCities();
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	//to set existing city value(Auto populate)
	var cityName = $("#city").val();
	if(cityName == null || cityName == 'undefined' || cityName == ''){
		cityName = document.getElementById("existingCity").value;
	}
	//end city Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == cityName){
	            		$('#city').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>

<script>
function updateCutomize(requirementId){
	minMaxExperience();
    var skillPerc = parseInt($("#skillScore").val());
    var expPerc = parseInt($("#experienceScore").val());
    var ratePerc = parseInt($("#rateScore").val());
    var noticePerc = parseInt($("#noticeperiodScore").val());
    var kewordsPerc = parseInt($("#keywordScore").val());
    var locationScore = parseInt($("#locationScore").val());
    var educationScore = parseInt($("#educationScore").val());
    var visaTypwScore = parseInt($("#visaTypeScore").val());
    var totalscore = (skillPerc+expPerc+noticePerc+ratePerc+locationScore+kewordsPerc+educationScore+visaTypwScore);
    var sourceleadUrl = '${sourceleadUrl}';
    
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
    					url : sourceleadUrl + "/updateCustomizeScore?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData + "&requirementId=" + requirementId,
    					success : function(data) {
    						if(data == "success"){
    							$("div.cand-advsrch-popup").hide();
    							requirementRecommendations(requirementId);	
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
	result = result +"	<div class='project_inn_mask'></div>";
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
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getCandidateById?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
           success :function(data) {   
                if(data != null && data != 'undefined' && data != ""){  
                	 var json = JSON.parse(data);
	                	 $("#candidateImageId").empty();
	                	 var result = '';
			             result = result+'<div class="well text-center clearfix">';
						 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
						 result = result+'   <img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+candidateId+'"/>" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 result = result+'  <div class="btn-group uploadfile">';
						 result = result+'  <button type="button" class="btn btn-sm btn-primary">';
						 result = result+'  <i class="fa fa-upload"></i></button>';
						 result = result+'  <label class="btn btn-sm btn-primary">';
					     result = result+'    Upload / Change<input type="file" name="file" id="uploadedImage"  onchange="loadFile(event,\''+candidateId+'\')" style="display: none;">';
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
         	},
      }); 
   }
</script>

<script type="text/javascript">
function deleteImage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask  projectMask-zindex'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure ?</p>";
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
	var sourceleadUrl ='${sourceleadUrl}';
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteCandidateImage?candidateId="+candidateId+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId,
	        success: function(data) {
	        	 var result = data;
	        	 $("#popUp").empty();
	        	 $(".project_inn_mask").fadeOut(200);
	        	 refreshImageBlock(candidateId);
	       },
	        
     }); 
}
</script>

<script type="text/javascript">
function saveOrCancel(message,candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
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
          		
          		var sourceleadUrl ='${sourceleadUrl}';
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
	                	      
	                      	  url: sourceleadUrl+"/saveCandidateProfile?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId,
	                          success: function(result) {
	                        	  recommendationAlertCancel(candidateId);
	    			          },  
	            });
}
</script> 

<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
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

<jsp:include page="addApplicant.jsp" />
<jsp:include page="addInterview.jsp" />
<jsp:include page="submitToClient.jsp" />

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




