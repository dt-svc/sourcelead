<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
 
 <style type="text/css">
.show-tooltip {
	position:relative;
	font-size:13px !important;
}
.show-tooltip .tooltip_div {
	color:#333;
	background: #e1e6fa;
    border-radius: 3px;
    display: none;
    padding:5px;
    position: absolute;
    top: 25px;
	left:-48px;
    width: 415px;
	box-sizing:border-box;
	z-index:999;
	transition: all 0.5s ease;
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
.show-tooltip:hover .tooltip_div{
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
width: 92% !important;
margin-top:10px;
}
#tab-2 {
overflow-y:hidden;
}
.table-overflow-hide {
overflow-y:inherit !important;
}
.require_wrap_table {
overflow-y:visible !important;

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
	 sideBar(requirementId); 
});	
</script>

<!-- Content Area -->
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement Summary</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/requirementList'/>">Requirement List  </a>&raquo; Requirement Details</div>
  </section>
  <div class="timesheet_out">
    
    
     <div class="clients_side_wrap_inn no_pad">
        <div id="requirementSideDiv"> </div>
      </div>   
      
      <!-- Addede by Rajashekar -->
      <div id="tab-3" class="project_wrap_table project_tabs project_tabs_inn">
          <div id="recommendationsList"></div>
       </div>            
            
          </div>
    
    <!-- <div class="side_mask">
      <div class="proj_close"></div>
    </div> -->
      
      <!-- End Raj --> 
    
    
<div class="side_mask">
      <div class="proj_close"></div>
    </div>
  </div>
</section>
 
 

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>


  
<script type="text/javascript">
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
    $(".`	").click(function(){
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
function sideBar(requirementId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
          type : "POST",
           url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
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
              			var result = '';
              			
              			result = result+'    <div class="profile_details requirement_details clearfix">';
				        result = result+'<a href="#" onClick="openTopDiv();"><h4 id="id1">'+json.jobTitle+'-'+json.jobType+'<i></i></h4></a>';
				        result = result+'<div class="hide-row">';
				        
				       /*  result = result+'    <div class="profile_details requirement_details clearfix">'; */
				        result = result+'<div class="col-md-9">';
				        result = result+'<div class="row">';
				        result = result+'<div class="col-md-3">';
				        result = result+'<div class="company-logo">';
				        result = result+'<div class="imageParent"> <img onerror="predefineOrgImage(this);" src="${sourceleadUrl}/displayOrgImage/'+json.orgLocationId.org.orgCode+'" alt="Company" title="Company Logo" class="img-responsive"> </div>';
				        result = result+'<span class="hidden"> TATA Consulting Private Limited, Hyderebad. </span> </div>';
				        result = result+'</div>';
				        result = result+'<div class="col-md-9">';
				        result = result+'<div class="canDetails clearfix">';
				        result = result+'<h4></h4>';
				        result = result+'<div class="row">';
				        result = result+'<div class="col-md-6">';
				        result = result+'<ul class="list-unstyled can-perdt">';
				        if(json.maxExp != null && json.maxExp != '' && json.maxExp !='undefined' && json.minExp != null && json.minExp != '' && json.minExp != 'undefined')
				        	{
				       	result = result+'<li><i class="fa fa-black-tie"></i><span>Experience: '+json.minExp+'Years'+json.maxExp+' Months</span></li>';
				        	}
					   
				        else{
					    result = result+'<li><i class="fa fa-black-tie"></i><span>Experience : --</span></li>';
				        }
				        
				        if(json.currencyCode == 'INR' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != '')
				    	   {
				        result = result+'                  <li><i class="fa fa-rupee"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
				    	   }
				       else if(json.currencyCode  == 'USD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != '' ){
					   result = result+'                  <li><i class="fa fa-dollar"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
   
				       }
				       else if(json.currencyCode  == 'AUD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != '' ){
					   result = result+'                  <li><i class="fa fa-dollar"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
				       }
				       else if(json.currencyCode  == 'SGD' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != ''){
					   result = result+'                  <li><i class="fa fa-dollar"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
   
				       }
				       else if(json.currencyCode  == 'EUR' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != ''){
					   result = result+'                  <li><i class="fa fa-eur"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
   
				       }
				       else if(json.currencyCode  == 'GBP' && json.currencyCode  !='' && json.currencyCode  !='undefined' && json.rate != '' && json.rateType != ''){
					   result = result+'                  <li><i class="fa fa-gbp"></i><span>'+json.rate+', '+json.rateType+'</span></li>';
  
				       }
				       else{
					   result = result+'                  <li><i class="fa fa-money"></i><span> --- </span></li>';
  
				       }
				       
				        if(json.state !='' && json.state != 'undefined' && json.city !='' && json.city != 'undefined')
				        	{
				        result = result+'                  <li><i class="fa fa-map-marker"></i><span>'+json.country+', '+json.state+', '+json.city+'</span></li>';
				        	}
				        else if(json.state !='' && json.state != 'undefined')
				        	{
					    result = result+'                  <li><i class="fa fa-map-marker"></i><span>'+json.country+', '+json.state+'</span></li>';

				        	}
				        else{
					     result = result+' <li><i class="fa fa-map-marker"></i><span>'+json.country+'</span></li>';

				        }
				        result = result+'                </ul>';
				        result = result+'              </div>';
				        result = result+'              <div class="col-md-6">';
				        result = result+'                <ul class="list-unstyled can-perdt">';
				        if(json.manager != null && json.manager != '' && json.manager != 'undefined'){
				        result = result+'<li> <i class="fa fa-user"></i><span id="canName">'+json.manager+'</span></li>';
				        }
				        else{
				        	 result = result+'<li> <i class="fa fa-user"></i><span id="canName"> --- </span></li>';	
				        }
				        result = result+'<li><i class="fa fa-clock-o"></i><span>'+json.requirementCreatedDateToString+'</span></li>';
				        if(json.priority != null && json.priority != '' && json.priority != 'undefined'){
				        result = result+'<li><i class="fa fa-flag"></i><span>'+json.priority+' Priority</span>';
				        }
				        else{
					    result = result+'<li><i class="fa fa-flag"></i><span> --- </span>';
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
					        result = result+'                <li>'+skills[1]+'</li>';
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
				        result = result+'    <div class="col-md-3">';
				        result = result+'      <select class="selectpicker pull-right">';
				        result = result+'        <option>Open</option>';
				        result = result+'        <option>Draft</option>';
				        result = result+'        <option>Filled Closed</option>';
				        result = result+'        <option>Unfilled</option>';
				        result = result+'        <option>On Hold</option>';
				        result = result+'        <option>Cancel</option>';
				        result = result+'      </select>';
				        result = result+'    </div>';
				        result = result+'  </div>';
				        result = result+'</div>';
				        
					    result = result+'  <div class="proj_close"></div>';
					    result = result+'  <div class="project_side_wrap_inn_tabs">';
					    result = result+'    <ul class="project-tabs-menu">';
					    result = result+'      <li class="current"><a href="#tab-1" onclick="requirementDetails('+requirementId+');">Details</a></li>';
					   
					    if(json.requirementSubmissionStatus != 'SUBMITTED'){
					    	result = result+'      <li><a href="#tab-2" onclick="requirementPipeline('+requirementId+');">Pipeline</a></li>';
					    	 result = result+'      <li><a href="#tab-3" onclick="requirementRecommendations('+requirementId+');">Recommendations</a></li>';
					    }
					   
					    result = result+'      <div class="clearfix"></div>';
					    result = result+'    </ul>';
					    result = result+'    <div class="project_tabs">';
						    result = result+' <div id="tab-1" class="project_tabs_inn">';
						    result = result+'</div>';
						    result = result+' <div id="tab-2" class="project_tabs_inn">';
						    result = result+'</div>';
					    result = result+'</div>';
					    result = result+'</div>';
					    
			             $("#requirementSideDiv").empty();  
			 		     $("#requirementSideDiv").append(result);  
			 		     requirementDetails(requirementId);
				 		 
			 		     /*  $(".clients_side_wrap").addClass("clients_side_wrap_active");
						 $(".side_mask").fadeIn(200);
						 $("body").css('overflow-y', 'hidden');
						 requirementDetails(requirementId);
						 $(".proj_close, .side_mask").click(function(){
								$(".clients_side_wrap").removeClass("clients_side_wrap_active");
								$(".side_mask").fadeOut(200);
								$("body").css('overflow-y', 'auto');
						 });	 */
			 			 
			    	} 
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
function requirementDetails(requirementId){ 
	  var sourceleadUrl = '${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getRequirementById?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
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
							var result ="";
				            result = result +' <div class="require_wrap_details">';
				           
				            result = result+'<div id="rejectRequirementAlert" ></div>  ';
				            
				            result = result +' <div class="clearfix">';
				            
				            result = result +' <div class="pull-left">';
				            result = result +' <h2>Assigned Members</h2>'; 
				            result = result +' </div>';
				            
				            result = result +' <div class="pull-right">';
				            
				          /*   if(((json.requirementSubmissionStatus  == "DRAFT" && json.createdBy == '${SourceLeadWeb_userCode}') || (json.requirementSubmissionStatus  == "SUBMITTED" ||  json.requirementSubmissionStatus  == "OPEN"   ||  json.requirementSubmissionStatus  == "NEW"   || json.createdBy == '${SourceLeadWeb_userCode}') && (json.requirementApproverHierarchyStatus =='APPROVED' || json.requirementApproverHierarchyStatus =='REJECTED')) ){ 
                            	result = result +' <a href="<c:url value="/requirement?requirementId='+requirementId+'"/>"><button  class="btn btn-primary" type="button">Edit</button></a>';
				            }else if((json.requirementSubmissionStatus  != 'DRAFT') && (json.requirementApproverHierarchyStatus =='APPROVED'  || json.requirementSubmissionStatus  == "SUBMITTED"  || json.requirementApproverHierarchyStatus =='REJECTED'  ||  json.requirementApproverHierarchyStatus  == "NEW" ) ){ 
				            	 result = result +' <a href="<c:url value="/requirement?requirementId='+requirementId+'"/>"><button  class="btn btn-primary" type="button">Edit</button></a>';
				            }
				             */
				            
				            if( (json.requirementSubmissionStatus =='NEW'  || json.requirementSubmissionStatus =='DRAFT' || json.requirementSubmissionStatus =='SUBMITTED'  || json.requirementSubmissionStatus  == "APPROVED"  || json.requirementSubmissionStatus =='REJECTED'  ||  json.requirementSubmissionStatus  == "OPEN" ||  json.requirementSubmissionStatus  == "ONHOLD" ) &&  json.isLoginUserAsTeamMember == "false"){ 
				            	 result = result +' <a href="<c:url value="/requirement?requirementId='+requirementId+'"/>"><button  class="btn btn-primary" type="button">Edit</button></a>';
				            }
				            
				            
				            var APPROVED = "APPROVED";     
				            var REJECTED = "REJECTED";

				             if(json.requirementSubmissionStatus  !='DRAFT'  &&  json.requirementApproverHierarchyStatus !='APPROVED' &&  json.requirementApproverHierarchyStatus != 'REJECTED' && json.requirementApproverHierarchyStatus !='' && json.requirementApproverHierarchyStatus !='undefined' && json.requirementApproverHierarchyStatus !=null){
               					 result = result +'<button  class="btn btn-success mr" type="button" onClick ="updateReuqirementStatus(\''+ APPROVED+ '\');"  >Approve</button>';
               		             result = result +'<button  class="btn btn-danger mr" type="button" onClick ="rejectRequirementAlertMessage(\''+ REJECTED+ '\');">Reject</button>';
   				           	 }else if(json.requirementApproverHierarchyStatus == 'APPROVED'  && json.requirementApproverHierarchyStatus !='' && json.requirementApproverHierarchyStatus !='undefined' && json.requirementApproverHierarchyStatus !=null){
      						              result = result +'<button  id="requirementStatus" class="btn btn-success pull-right mr" type="button">'+json.requirementApproverHierarchyStatus+'</button>';
    				         }else if( json.requirementSubmissionStatus  !='DRAFT'  && json.requirementApproverHierarchyStatus == 'REJECTED'  && json.requirementApproverHierarchyStatus !='' && json.requirementApproverHierarchyStatus !='undefined' && json.requirementApproverHierarchyStatus !=null){
					              result = result +'<button  id="requirementStatus" class="btn btn-danger pull-right mr" type="button">'+json.requirementApproverHierarchyStatus+'</button>';
		                     }
				             
				             
				             result = result +'     </div>';
				             result = result +'     </div>';
				             
				            result = result +'     <div class="clearfix row">';
					        result = result +'     <div class="col-md-10 divskill divsAssigned">';
					        
					        result = result + '<div id="assignedMemberList"></div>';
					        
				            result = result +'   <h1>Description:</h1>';
				            if(json.description != null && json.description != '' && json.description != 'undefined'){
					           	 result = result +'   <textarea placeholder="Enter description" readonly>'+json.description+'</textarea>';
					         }
					         else{
					           	 result = result +'   <textarea readonly> NO DATA</textarea>';
				    	        }
				            
				            result = result +'   <h1>Skills:</h1>';
				            if(json.skills != null && json.skills != '' && json.skills != 'undefined'){
					            result = result +'     <textarea placeholder="Enter description" readonly>'+json.skills+'</textarea>';
					            }
					            else{
					            result = result +'<textarea readonly> NO Skills</textarea>';	
					            }
				            
				            
				            result = result +'   <h2> Requirement Details:</h2>';
				            result = result +'   <div class="require_one">';
				            if(json.positions != null && json.positions != '' && json.positions != 'undefined'){
				            result = result +'     <div class="require_wrap_details_cta"> <b>No. of positions:</b> <span>'+json.positions+'</span>';
				            }
				            else{
				            result = result +'<div class="require_wrap_details_cta"> <b>No. of positions:</b> <span> -- </span>';	
				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.education != null && json.education != '' && json.education != 'undefined'){
					            result = result +'     <div class="require_wrap_details_cta"> <b>Education:</b> <span>'+json.education+'</span>';
					            }else{
					           	result = result +'<div class="require_wrap_details_cta"> <b>Education:</b> <span> -- </span>';
					            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.fileName != null && json.fileName != '' && json.fileName != 'undefined'){
					            	result = result +'<div class="require_wrap_details_cta require_uploadfile"><b>Attachment:</b> <span>'+json.fileName+'</span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadRequirementAttachment?requirementId='+json.id+'\"></a> ';
					            }
					            else{
					            result = result +'<div class="require_wrap_details_cta"> <b>Attachment:</b> <span> -- </span>';	
					            }				            
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            				   
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            if(json.isClientJob  == 'Y')
				            	{
				            result = result +'   <h2>Client details:</h2>';
				            result = result +'   <div class="require_one">';
				            
				            if(json.spocName != '' && json.spocName !=null && json.spocName != 'undefined'){
				            	
				            result = result +'     <div class="require_wrap_details_cta"> <b>Client Contact:</b> <span class="pull-left"> '+json.spocName+' </span>';
				            }
				            else{
					        result = result +'     <div class="require_wrap_details_cta"> <b>Client contact:</b> <span class="pull-left"> -- </span>';

				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.clientName != '' && json.clientName !=null && json.clientName != 'undefined'){
					        result = result +'     <div class="require_wrap_details_cta"> <b>Client Name:</b> <span class="pull-left"> '+json.clientName+' </span>';
				            }
				            else{
						        result = result +'     <div class="require_wrap_details_cta"> <b>Client Name:</b> <span class="pull-left"> ---  </span>';
				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>'; 
				            /* if(json.spocEmail != '' && json.spocEmail !=null && json.spocEmail != 'undefined'){
						    result = result +'     <div class="require_wrap_details_cta"> <b>Email:</b> <span>'+json.spocEmail+'</span>';
				            }
				            else{
							result = result +'     <div class="require_wrap_details_cta"> <b>Email:</b> <span> --- </span>';
	
				            }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>'; 
				            if(json.spocMobilePhone != '' && json.spocMobilePhone !=null && json.spocMobilePhone != 'undefined'){
					        result = result +'     <div class="require_wrap_details_cta"> <b>Phone No:</b> <span>'+json.spocMobilePhone+'</span>';
				            }
				            else{
						    result = result +'     <div class="require_wrap_details_cta"> <b>Phone No:</b> <span> --- </span>';

				            } 
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            */
				            if(json.clientCurrencyCode == 'INR' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' &&  json.clientRate != '' && json.clientRate != 'undefined' && json.clientRate != null){
					        result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-inr"></i> ' +json.clientRate+'</span>';
						    }
						    else if(json.clientCurrencyCode == 'EUR' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){

						    result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-eur"></i> ' +json.clientRate+'</span>';
							}
						    else if(json.clientCurrencyCode == 'GBP' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){

							result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span><i class="fa fa-gbp"></i> ' +json.clientRate+'</span>';
							}
						    else if(json.clientCurrencyCode  == 'USD' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){
							result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ' +json.clientRate+'</span>';
							}
						    else if(json.clientCurrencyCode  == 'AUD'  && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){
							result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ' +json.clientRate+'</span>';
	
						    }
						    else if(json.clientCurrencyCode  == 'SGD' && json.clientCurrencyCode  !='' && json.clientCurrencyCode  !='undefined' && json.clientRate != '' && json.clientRate != 'undefined'){
							result = result +'     <div class="require_wrap_details_cta" > <b>Bill rate/salary:</b> <span><i class="fa fa-dollar"></i> ' +json.clientRate+'</span>';
	
						    }
						    else{
							result = result +'     <div class="require_wrap_details_cta"> <b>Bill rate/salary:</b> <span> --- </span>';
						    }
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.clientSubmissionTemplate != '' && json.clientSubmissionTemplate != null && json.clientSubmissionTemplate != 'undefined'){
					            result = result +'     <div class="require_wrap_details_cta"> <b>Submission Form:</b> <span>'+json.clientSubmissionTemplate+'</span>';
					            }
					            else{
						        result = result +'     <div class="require_wrap_details_cta"> <b>Submission Form:</b> <span> --- </span>';
	  	
					            }				            
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            	}
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            
				            result = result +'   <h2>Internal details:</h2>';
				            result = result +'   <div class="require_one">';
				            //result = result +'     <div class="require_wrap_details_cta"> <b>Manager:</b> <span>Manager</span>';
				            result = result +'       <div class="clearfix"></div>';
				            if(json.isClientJob  == 'N'){
				            if(json.clientSubmissionTemplate != '' && json.clientSubmissionTemplate != null && json.clientSubmissionTemplate != 'undefined'){
					            result = result +'     <div class="require_wrap_details_cta"> <b>Application form:</b> <span>'+json.clientSubmissionTemplate+'</span>';
					            }
					            else{
						        result = result +'     <div class="require_wrap_details_cta"> <b>Application form:</b> <span> --- </span>';
	  	
					            }
				            }
				            result = result +'     </div>';
				            if(json.highestLevel == '1'){
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
				            
				            
				           /*  if(json.approverNames != null && json.approverNames != '' && json.approverNames != 'undefined'){
					            result = result +'     <div class="require_wrap_details_cta"> <b>Approver1:</b> <span>'+json.approverNames+'</span>';
					            }
					            else{
					            result = result +'<div class="require_wrap_details_cta"> <b>Approver1:</b> <span> -- </span>';	
					            }	
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            if(json.approverNames != null && json.approverNames != '' && json.approverNames != 'undefined'){
					            result = result +'     <div class="require_wrap_details_cta"> <b>Approver2:</b> <span>'+json.approverNames+'</span>';
					            }
					            else{
					            result = result +'<div class="require_wrap_details_cta"> <b>Approver2:</b> <span> -- </span>';	
					            }	 */
				            
				            
				            
				            
				            result = result +'       <div class="clearfix"></div>';
				            result = result +'     </div>';
				            result = result +'     <div class="clearfix"></div>';
				            result = result +'   </div>';
				            result = result +' </div>';
							$("#tab-1").empty();
							$("#tab-2").empty(); 
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
            		 }
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
	 
	 var reason;
	 if ($('#rejectReason').length)
	 {
		 reason = document.getElementById("rejectReason").value; 
	 }
	 else
	 {
		 reason = '';
	 }
	$.ajax({
      type : "POST",
      url: sourceleadUrl+"/requirementAcceptOrReject?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&status="+status+"&reason="+reason, 
    		  success :function(data) {  
            if(data != null && data != 'undefined' && data != ""){  
          		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
          		 else if (data.indexOf('exception') >= 0) {
          			 location.replace(sourceleadUrl+"/errorPage");
          		 }
          		 else{ 
          			requirementDetails(requirementId)
          		 }
            }
       },
	  }); 
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
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick ='updateReuqirementStatus(\""+ status+ "\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	result = result +"	</div>";
	$("#rejectRequirementAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>


<script type="text/javascript">
function cancelAlert(){ 
	$("#rejectRequirementAlert").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function cancelAlert(){ 
	$("#rejectRequirementAlert").empty();
	$(".project_inn_mask").fadeOut(200);
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
function requirementPipeline(requirementId){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var clientSubmissions = '${clientSubmissions}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
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
            			 
            			 var result ="";
            			
            			 var submitToClient = '';
            			 if(clientSubmissions == 'Y'){
            				 submitToClient = '<a href="javascript:void(0)" title="" onclick="submitToClient('+requirementId+');" class="submitTo"><i class="fa fa-plus"></i>&nbsp;Submit to Client</a>';
            			 }
							
							 result = result +'<div class="side_mask"><div class="viewCandidate_close addApplicant_close addInterview_close submitToClient_close addOffer_close"></div></div>';
							 
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
							result = result +'</div>';
							result = result +'</div>';
							result = result +'</div>';
							//For Submit to Client Div
							
							
							
							result = result +'<div class="shortlist-wrap">';
							result = result +'<div class="new_side_wrap_inn">';
							result = result +'<div class="add_inter">';
							result = result + '<div id="shortlistDiv"></div>';
							result = result +'</div>';
							result = result +'</div>';
							result = result +'</div>';
							
                             //Candidate submissions details Div
							 result = result +' <div class="clients_side_wrap">';
							 result = result +'<div class="clients_side_wrap_inn">';
							 result = result +' <div class="project_side_wrap_inn_tabs"> ';
							 result = result + '<div id="viewCandidateDiv"></div>';
							 result = result +'</div>';
							 result = result +'</div>';
							 result = result +'</div>';

							 
							
				           // result = result +'<div class="graf_icon"><i class="fa fa-area-chart"></i>Report</div>';
				            result = result +'<div class="pipeline_graf"><img src="images/bargraph.jpg" alt=""/></div>';
				            result = result +'<div class="timesheet_head candidateHead recruitmentHead headersummary">';
				            result = result +'  <ul>';
				            result = result +'    <li class="li-vendor">';
				          //  result = result +'      <input type="text" class="vendorfild" placeholder="Search">';
				          // result = result +'      <i class="fa fa-search"></i></li>';
				            result = result +'    <li class="hidden"><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>';
				            result = result +'      <div class="export_pop">';
				            result = result +'        <ul>';
				            result = result +'          <li>PDF<i class="fa fa-file-pdf-o"></i></li>';
				            result = result +'          <li>Excel<i class="fa fa-file-excel-o"></i></li>';
				            result = result +'          <li>Word<i class="fa fa-file-word-o"></i></li>';
				            result = result +'        </ul>';
				            result = result +'      </div>';
				            result = result +'    </li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-envelope"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-mail-forward"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-upload"></i></a></li>';
				            result = result +'    <li><a href="javascript:void(0)" title=""><i class="fa fa-download"></i></a></li>';
				           
				            
				            result = result +' <li><a href="javascript:void(0)" class="action"> Action <i onclick="getActions();" class="fa fa-caret-down"></i></a>';
				            result = result +'<div class="action-pop">';
				            result = result +'	<span>';
				            result = result +'	<a href="javascript:void(0)" title="" onclick="getAddApplicant('+requirementId+');" class="add-applicant"><i class="fa fa-plus"></i>&nbsp;Add Applicant</a>';
				            result = result +'    <a href="javascript:void(0)" title="" onclick="candidateShortList('+requirementId+');" class="shortlistClick"><i class="fa fa-plus"></i>&nbsp;Shortlist</a>';
				            result = result + submitToClient
				            result = result +'   <a href="javascript:void(0)" title="" onclick="addInterview('+requirementId+');" class="add-interview"><i class="fa fa-plus"></i>&nbsp;Add Interview</a>';
				            result = result +'    <a href="javascript:void(0)" title=""  onclick="addOffer('+requirementId+');" class="add-offer"><i class="fa fa-plus"></i>&nbsp;Add Offer</a>';
				            result = result +'    </span>';
				            result = result +' </div>';	
				            result = result +' </li>';
				            
				            
				            
				            result = result +'    <div class="clearfix"></div>';
				           result = result + '   </div>';
				           result = result + '   <div class="require_wrap_table overflow-data">';
				           result = result + '   <div class="table-overflow-hide">';
				           result = result + '     <table id="requirementsPagination" class="display" cellspacing="0" width="100%"">';
				           result = result + '       <thead>';
				           result = result + '         <tr>';
				           result = result + '           <th>Name</th>';
				           result = result + '           <th>Email</th>';
				           result = result + '           <th>Ph. No</th>';
				           result = result + '           <th>Resume</th>';
				           result = result + '           <th>Applied on</th>';
				           result = result + '           <th>Rate</th>';
				           result = result + '           <th>Source</th>';
				           result = result + '           <th>Score</th>';
				           result = result + '           <th>Experience</th>';
				           result = result + '           <th>Status</th>';
				           result = result + '         </tr>';
				           result = result + '       </thead>';
				           result = result + '       <tbody>';
				           for (var i=0; i<json.length; i++)
			   				{  
			   	        		
			   	        		var report = json[i];
						           result = result + '       <tr>';
						           result = result + '         <td><div onclick="getSubmissionDetailsSideBar('+report.id+','+report.candidateId+','+requirementId+');" class="tbldata-dotdot proj_show"><span class="pull-left">'+report.candidateName+'</span><i class="fa fa-binoculars"></i></div></td>';
						           
						           if(report.candidateEmail != null && report.candidateEmail != '' && report.candidateEmail != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.candidateEmail+'</span></div></td>';
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
						           
						           if(report.resumeName != null && report.resumeName != '' && report.resumeName != 'undefined'){
						        	   result = result + '         <td><div><span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadResume?resumeId='+report.id+'\"></a>&nbsp;&nbsp;'+report.resumeName+'</span></div></td>';
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
						           
						           if(report.expectedCTC != null && report.expectedCTC != '' && report.expectedCTC != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.expectedCTC+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.userName != null && report.userName != '' && report.userName != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.userName+'</span></div></td>';
							           }
							       else{
							        	result = result + '         <td><div><span>--</span></div></td>';
						    	     }
						           
						           if(report.score != null && report.score != '' && report.score != 'undefined'){
						        	   result = result + '         <td><div><span>'+report.score+'</span></div></td>';
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
						           
						           result = result + '<td><div><span>'+report.submissionStatus+'</span></div></td>';
						           
						           
						           result = result + '       </tr>';
			   				}
				           
				           
				           result = result + '       </tbody>';
				           result = result + '     </table>';
				           result = result + '   </div>';
				           result = result + '   </div>';
				            
				            
							$("#tab-1").empty();
							$("#tab-2").empty(); 
							$("#tab-2").append(result);  
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
            		 }
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

<script>
function documentpagination(){  
    $('#requirementsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
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
              if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{ 
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
						    result = result +'         <td><div class="tbldata-dotdot-sm">020</div></td>';
						    result = result +'         </tr>';
			   				}
						    /* result = result +'         </tbody>';
						    result = result +'         </table>'; */
						    /* result = result +'     </div>';
						    result = result +'     </div>'; */
						     result = result +' </div> '; 
						    
						    $("#assignedMemberList").empty();
						    $("#assignedMemberList").append(result);
							
            		 }
              }else{
                     	 $("#assignedMemberList").removeAttr("style");
       	    	         var result ="";
      		    	     result = result+"   <div class='row'>"; 
      					 result = result+"	<div class='col-lg-12'>"; 
      					 result = result+"		<div class='content-area clearfix'>"; 
      					 result = result+"			<div class='blank-pages'>";
      					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
      					 result = result+"			        <p>No AssignedMember Found</p>";
      					 result = result+"			 </div>";
      					 result = result+"		</div>"; 
      					 result = result+"	</div>"; 
      					 result = result+"  </div> "; 
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
     url: sourceleadUrl+"/getRequirementRecommendationsByRequirementId?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId,        
     success :function(data) {
    	 var json = JSON.parse(data);
    	    var result = '';
    	    
    	     result = result +' <div class="clients_side_wrap">';
			 result = result +'<div class="clients_side_wrap_inn">';
			 result = result +' <div class="project_side_wrap_inn_tabs"> ';
			 result = result + '<div id="candidateSideDiv"></div>';
			 result = result +'</div>';
			 result = result +'</div>';
			 result = result +'</div>';
    	    
    	    //--------------------------------
    	    result = result +'<div class="timesheet_head candidateHead recruitmentHead">';
		    result = result +'<div class="rec_head"></div>';
		    result = result +'</div>';
		    result = result +'<div class="project_tabs_inn_table">';
		    result = result +'<table id="recommendationTable" class="display" cellspacing="0" width="100%"">';
		    result = result +'<thead>';
		    result = result +'<tr>';
		    result = result +'<th>Name</th>';
		    result = result +'<th>Ph. No</th>';
		    result = result +'<th>Resume</th>';
		    result = result +'<th>Number of req</th>';
		    result = result +'<th>Score</th>';
		    result = result +'<th>Action</th>';
		    result = result +'</tr>';
		    result = result +'</thead>';
		    result = result +'<tbody>';
    	 for (var i=0; i<json.length; i++)
			{
		    var report = json[i];
		    result = result +'<tr>';
		    result = result + '<td><div onclick="clientSideBar('+report.id+');" class="proj_show"><span>'+report.firstName+'</span><i class="fa fa-binoculars"></i></div></td>';
/* 		    result = result +'<td><div class="proj_show"><span>'+report.firstName+'</span><i class="fa fa-binoculars"></i></div></td>'; */
		    if(report.mobilePhone != null && report.mobilePhone !='' && report.mobilePhone !='undefined'){
		    	result = result +'<td><div><span>'+report.mobilePhone+'</span></div></td>';
		    }else{
		    	result = result +'<td><div><span>--</span></div></td>';
		    }
		    result = result +'<td><div><span><a href=\"'+sourceleadUrl+'/downloadCandidateResume?candidateId='+report.id+'\"><i class="glyphicon glyphicon-list-alt"></i>&nbsp;&nbsp;'+report.candidateResume+'</a></span></div></td>';
		    result = result +'<td><div class="show-tooltip"><span>'+report.noOfReq+'</span>';
		    if(report.requirementSubmisstions.length > 0){
		    	result = result +'<div class="tooltip_div">';
			    result = result +'<table class="table-bordered table-hover table-striped table-responsive" width="100%;">';
			    result = result +' <thead><tr><th>Requirement Name</th> <th>Stage</th></tr></thead>';
			    result = result +'<tbody>';
			    for(var j=0;j<report.requirementSubmisstions.length;j++){
			    	 var sub = report.requirementSubmisstions[j];
			    	 if(sub.requirementName != null && sub.requirementName !='' && sub.requirementName !='undefined' && sub.requirementStage != null && sub.requirementStage !='' && sub.requirementStage !='undefined'){
			    		 result = result +'<tr><td><span>'+sub.requirementName+'</span></td> <td><span>'+sub.requirementStage+'</span></td></tr>'; 
			    	 }else{
			    		 result = result +'<tr><td><span>--</span></td> <td><span>--</span></td></tr>';
			    	 }
			          
			    } 
			    result = result +'</tbody>';
			    result = result +' </table>';
			    result = result +' </div>'; 
		    }
		    result = result +' </div></td>';
		    result = result +'<td><div><span>'+report.candidateScore+'%</span></div></td>';
		    result = result +'<td><div><span>';
		    if(report.isCandidateAdedd == true){
		    	result = result +'<input type="button" value="Added" class="btn btn-primary btn-sm">';
		    }else{
		    	result = result +'<input type="button" value="--" class="btn btn-primary btn-sm">';
		    }
		    
		   /*  result = result +'<select>';
		    result = result +'<option>Interview</option>';
		    result = result +'<option>Submit</option>';
		    result = result +' <option>Joined</option>';
		    result = result +'<option>Screening</option>';
		    result = result +'<option>Offer</option>';
		    result = result +'</select>' */;
		    result = result +'</span></div>';
		    result = result +'</td>';
		    result = result +'</tr>';
		   
			}
    	    result = result +'</tbody>';
		    result = result +' </table>';
		    result = result +'</div>';
		    $("#recommendationsList").empty();
			$("#recommendationsList").append(result); 
			recommendationpagination();
           },
             
        }); 
}
</script>

<script>
function recommendationpagination(){  
    $('#recommendationTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>

<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
 	});	

}
</script>

<script>
var candidateStatusGlobal ="";
function clientSideBar(candidateId){
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
	                	 var skillsList = json.skillsList;
			             var skills = [];
			             skills = skillsList.split(",");
			             
	                	 var result = "";
	                	 
	                	 result = result+'<div id="popUp" ></div>  ';
	                	
	                	 
	                	 result = result+'<form id="candidate_form" name="candidate_form" method="post" >';
			             result = result+'  <div class="profile_details clearfix" style="margin-bottom:20px;">';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                   <div class="media">';
			             
			             result = result+'<div class="col-xs-12 col-sm-5 col-md-5">';
			             result = result+'<div id="candidateImageId">';
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
						 /*  result = result+'  <div class="pull-right" style="position:relative; top:10px; right:-10px;">';
						 result = result+'  <a href="#" title="Save"><i class="fa fa-check-circle" onClick="saveCandidateImage('+candidateId+');" ></i></a>';
						 result = result+'  <a href="#" title="Close"><i class="fa fa-close text-danger"></i></a>';
						 result = result+'  </div>'; */
						 result = result+'  </div>';
						 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not supported</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>';
						 result = result+' </div>';
						 result = result+' </div>';
						 result = result+'</form>';
						  
						  
			             /*  result = result+'                     <div class="media-left image-parent">';
			             result = result+'                     <div class="imageParent">'; */
			             /* result = result+'                     	<img src="./images/24.jpg" alt="candidate" title="Candidate">'; */
			             /* result = result+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayUserImage/${SourceLeadWeb_userId}"/>"  alt="Profile Image" class="img-circle img-responsive">';
			             result = result+'                     </div>';
			             result = result+'                     </div>'; */
			             result = result+'                     <div class="media-body candidate-details">';
			             
			             result = result+'                       <h4 class="media-heading">'+json.firstName+' '+json.lastName+'</h4>';
			            
			             result = result+'                       <ul class="list-unstyled">';
			                                                       if(json.experienceYears != null && json.experienceYears != '' && json.experienceYears != 'undefined'){
			             result = result+'                         <li>Experience - '+json.experienceYears+' years '+json.experienceMonths+' months </li>';
              		                                                  }else{
              		                                                	  
              		     result = result+'                          <li>Experience - ----</li>';  
              		                                                  }
			             result = result+'                       </ul>';
			             
			             
			             result = result+'                 <div class="clearfix">';
			             result = result+'                 <div class="professinal-details">';
			             
			            
			             result = result+'<ul class="list-inline list-unstyled">';
			             if(json.skillsList != null && json.skillsList != '' && json.skillsList != 'undefined'){
			             for (i=0;i < skills.length;i++)
			             {
			            	 result = result+'<li>'+skills[i]+'</li>';
			             }
			             }
			             result = result+'</ul>';
			             
			             result = result+'                 </div>';
			             result = result+'                 <div class="socialnetwork-details">';
			             result = result+'                 <ul class="list-inline list-unstyled">';
			             result = result+'                                 <li class="bg-facebook"><a href="javascript:void(0)"> <i class="fa fa-facebook-square"></i></a></li>';
			             result = result+'                                 <li class="bg-twitter"><a href="javascript:void(0)"> <i class="fa fa-twitter-square"></i></a></li>';
			             result = result+'                                 <li class="bg-google "><a href="javascript:void(0)"> <i class="fa fa-google-plus-square"></i></a></li>';
			             result = result+'                                 <li class="bg-linked"><a href="javascript:void(0)"> <i class="fa fa-linkedin-square"></i></a></li>';
			             result = result+'                                 <li class="bg-web"><a href="javascript:void(0)"> <i class="fa fa-globe"></i></a></li>';
			             result = result+'                               </ul>';
			             result = result+'                 </div>';
			             result = result+'                 </div>';
			             result = result+'               </div>';
			             result = result+'                   </div>';
			             result = result+'                 </div>';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                 <ul class="list-unstyled can-perdt">';
			             result = result+'                   <li><i class="fa fa-envelope-o"></i><a href="#" title="click here">'+json.email+'</a></li>';
			                                                  if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
			             result = result+'                   <li><i class="fa fa-phone"></i>+'+json.mobilePhone+'</li>';
			                                                  }else{
			                                                	  
			             result = result+'                   <li><i class="fa fa-phone"></i>----</li>';         	  
			                                                  }
			                                                  if(json.address != null && json.address != '' && json.address != 'undefined'){
			             result = result+'                   <li><i class="fa fa-location-arrow"></i>'+json.address+'</li>';
			                                                  }else{
			             result = result+'                   <li><i class="fa fa-location-arrow"></i>----</li>';                                  	  
			                                                  }
			                                                  if(json.currentRate != null && json.currentRate != '' && json.currentRate != 'undefined'){
			             result = result+'                   <li><i class="fa fa-rupee"></i>'+json.currentRate+'/Per Hour - Current Rate / Salary</li>';
			                                                  }else{
			             result = result+'                   <li><i class="fa fa-rupee"></i>----</li>';                                    
			                                                  }
			                                                  if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
			             result = result+'                   <li><i class="fa fa-rupee"></i>'+json.expectedRate+'/Per Hour - Expected Rate / Salary</li>';
			                                                  }else{
			             result = result+'                   <li><i class="fa fa-rupee"></i>----</li>';                                  	  
			                                                  }
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
			             result = result+'       <li><a href="#tab-16" onclick="candidateActivities('+candidateId+');">Activities</a></li>'; 
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
						 
						 result = result + '<div id="tab-16" class="canreq-tabs-menu_inn">';
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
<jsp:include page="recommendCandidatesActivities.jsp"></jsp:include> 

