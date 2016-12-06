<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
 <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="resources/css/jquery-ui.css" rel="stylesheet">

<script src="resources/js/formvalidator.js"></script>



<%-- 
     @author : p.sarath kumar
     this script is for candidate Activation
--%>
<script>
  function noAccess(id){ 	
	 
	    var result = "";
		result = result + '	  <div class="blank-pages-tech">';
		result = result + '   <p class="text-center text-danger permissondenied mb">';
		result = result + '       <img src="resources/images/sourcelead-load.png" alt="Source Lead" title="Source Lead">';
		result = result + '   </p>';
		result = result + '   <p class="big-txt mt">PERMISSION DENIED</p>';
		result = result + '   <p class="text-muted">You dont have permission to access this page.</p>';
		result = result + '   <p class="positionRelative move-btn btn-group mt">';
		result = result + '   <i class="fa fa-home btn btn-primary"></i>';
		result = result + '   <a href="<c:url value='/dashboard'/>" type="button" class="btn btn-primary">Take me home<span>&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
		result = result + '   </span></a>';
		result = result + '   </p>';
		result = result + ' </div>';
		    
	   $("#tab-"+id).empty();
	   $("#tab-"+id).append(result);
  }
</script>

<jsp:include page="candidatesProfile.jsp"></jsp:include>
<jsp:include page="candidatesResumes.jsp"></jsp:include>
<jsp:include page="candidatesExperience.jsp"></jsp:include>
<jsp:include page="candidatesEducation.jsp"></jsp:include>
<jsp:include page="candidatesCertificates.jsp"></jsp:include>
<jsp:include page="candidatesActivities.jsp"></jsp:include>

 <sec:authorize access="hasRole(\'ROLE_POST_COMMENTS\')">
<jsp:include page="candidatesCommentsList.jsp"></jsp:include>
 </sec:authorize>
<script>
 window.onload = function() {
 	 candidateList();
 	 $("#upcandidateStatus").val('');
};
</script>

<style type="text/css">
.candidate_wrap_table table tr td:first-child {
    padding: 0;
    text-align: center;
    width: 200px;
    padding-right: 5px;
}
</style>



<%-- 
     @author : sarath kumar.
     this script is for get candidate list.
     1.usage     : onload.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<input type="hidden" id="noCandidates" name="noCandidates"/>
<script>
  function candidateList(){ 	 
	  
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  var candFilterRadio = $('input[name=candidateStatus]');
	  var candidateStatus = candFilterRadio.filter(':checked').val();
	  
	  if(candidateStatus == 1){
          $("#canDiv").text("Active Candidates");
      }else if(candidateStatus == 0){
    	  $("#canDiv").text("In Active Candidates");
      }else if(candidateStatus == 2){
    	  $("#canDiv").text("All Candidates");
      }else{
          $("#canDiv").removeAttr("onClick");
      }

	var upcandidateStatus =   $('input[name=upcandidateStatus]:checked').val();
	  
	  
	  $("#candidateList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 
	  $.ajax({
           type : "POST",
            url: sourceleadUrl+"/getCandidatesListByOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateStatus="+candidateStatus+"&updatedDateRange="+upcandidateStatus,       
            success :function(data) {
                 if(data != null && data != 'undefined' && data != "" && data != '[]'){
               		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			    	     $("#candidateList").removeAttr("style");
	                	 $("#candidateList").empty();
	                	 var result = "";
	                	 result = result +"<table id='candidatePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                    /*  result = result +"    <th><input type='checkbox' id='checkAll' onclick='toggle(this);'</th>";   */
	                     result = result +"    <th>Name</th>"; 
	                     result = result +"    <th>Designation</th>"; 
	                     result = result +"    <th>Position type</th>"; 
	                     result = result +"    <th>Email</th>"; 
	                     result = result +"    <th>Mobile</th>"; 
	                     result = result +"    <th>Location</th>"; 
	                     result = result +"    <th>Last Update</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i]; 
	                    	 var mobilePhone = "";
	                    	 var designation ="";
	                    	 var address="";
	                    	 if(opt.mobilePhone != null && opt.mobilePhone != 'undefined' && opt.mobilePhone != ""){
	                    		 mobilePhone = opt.mobilePhone;
				            	}else{
				            		mobilePhone = "----";
				            	}
				            	if(opt.designation != null && opt.designation != 'undefined' && opt.designation != ""){
				            		designation = opt.designation;
				            	}else{
				            		designation = "----";
				            	}
				            	if(opt.address != null && opt.address != 'undefined' && opt.address != ""){
				            		address = opt.address;
				            	}else{
				            		address = "----";
				            	}
				            	if(opt.candidateUpdateDateToString != null && opt.candidateUpdateDateToString != 'undefined' && opt.candidateUpdateDateToString != ""){
				            		candidateUpdateDateToString = opt.candidateUpdateDateToString; 
				            	}else{
				            		candidateUpdateDateToString = "---";
				            	}
		                     result = result +"<tr>";

			                 /* result = result +"  <td><input type='checkbox' id='checkbox12'/></td>"; */
			                
			                 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CANDIDATE\',\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_VIEW_DELETE_CANDIDATE\')">'; 
			                   result = result +"  <td><div class='proj_show' onclick='candidateSideBar("+opt.id+");'><span title='"+opt.firstName+" "+opt.lastName+"'>"+opt.firstName+" "+opt.lastName+"</span><i class='fa fa-binoculars'></i></div></td>";
			                 '</sec:authorize>';
			                 
			                 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CANDIDATE\',\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_VIEW_DELETE_CANDIDATE\')">'; 
			                  result = result +"  <td><div class='proj_show' onclick='#'><span title='"+opt.firstName+" "+opt.lastName+"'>"+opt.firstName+" "+opt.lastName+"</span></div></td>";
			                 '</sec:authorize>';

			                 result = result +"  <td><div><span>"+designation+"</span></div></td>";
			                 if(opt.designationType != null && opt.designationType != '' && opt.designationType != "undefined")
			                	 {
			                      result = result +"  <td><div>"+opt.designationType+"</div></td>";
			                	 }else{
			                		 result = result +"  <td><div>----</div></td>"; 
			                	 }
			                 result = result +"  <td><div ><span title='"+opt.email+"'>"+opt.email+"</span></div></td>";
			                 result = result +"  <td><div>"+mobilePhone+"</div></td>";
			                 if(opt.state !='' && opt.state != 'undefined' && opt.city !='' && opt.city != 'undefined'){
	                             result = result +"  <td><div><span title='"+opt.country+", "+opt.state+", "+opt.city+"'>"+opt.country+", "+opt.state+", "+opt.city+"</span></div></td>";
	                              } else if(opt.state !='' && opt.state != 'undefined')
	                            {
	                             result = result +"  <td><div><span title='"+opt.country+", "+opt.state+"'>"+opt.country+", "+opt.state+"</span></div></td>";
	                            }
	                            else{
	                            result = result +"  <td><div><span title='"+opt.country+"'>"+opt.country+"</span></div></td>";
	                            }

			                 result = result +"  <td><div>"+candidateUpdateDateToString+"</div></td>";
			                 result = result +"</tr>"; 
	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#noCandidates").val('');
	                     $("#candidateList").append(result);
	                     cpagination(); 
			    	} 
                 }else{
                	 $("#candidateList").removeAttr("style");
  	    	         var result ="";
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Candidates Found</p>";
					 result = result+"			 </div>";
					$("#noCandidates").val("nocandidate");
					$("#candidateList").empty();
				    $("#candidateList").append(result); 
                 }
          },
            
       }); 
  } 
</script>

<%-- 
     @author : sarath kumar.
     this script is for prepare pagination to candidateList.
     1.usage     : used in candidateList() function.
--%>

<!-- <script>
function cpagination(){  
    $('#candidatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     	"sSearch": ""
     	}
    } );
}
</script>
 -->
<script>
function cpagination(){ 
    $('#candidatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
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
     this script is for show side bar when you click on the candidate name.
     1.usage     : called at candidateList() function.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
var candidateStatusGlobal ="";
function candidateSideBar(candidateId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  var loginUserId = '${SourceLeadWeb_userId}';
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
			             result = result+'  <div class="profile_details clearfix mb">';
			             result = result+'               <div class="col-md-6">';
			             result = result+'                   <div class="media">';
			             
			             result = result+'<div class="col-xs-12 col-sm-5 col-md-5">';
			             result = result+'<div id="candidateImageId">';
			             result = result+'<div class="well well-sm text-center clearfix">';
						 result = result+' <div class="profile-img-container profile-image text-center" style="position:relative;">';
						 result = result+'   <img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+candidateId+'"/>" alt="Profile Image" class=" img-responsive center-block img-circle">';
						 result = result+'  <div class="btn-group uploadfile uploadfile-candidate">';
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
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif are not support</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Size should not more than 200kb</p>';
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
			             
			             result = result+'</div>';
			             result = result+'<div class="socialnetwork-details">';
			             result = result+'<ul class="list-inline list-unstyled">';
			             result = result+'<li class="bg-facebook"><a href="javascript:void(0)"> <i class="fa fa-facebook-square"></i></a></li>';
			             result = result+'<li class="bg-twitter"><a href="javascript:void(0)"> <i class="fa fa-twitter-square"></i></a></li>';
			             result = result+'<li class="bg-google "><a href="javascript:void(0)"> <i class="fa fa-google-plus-square"></i></a></li>';
			             result = result+'<li class="bg-linked"><a href="javascript:void(0)"> <i class="fa fa-linkedin-square"></i></a></li>';
			             result = result+'<li class="bg-web"><a href="javascript:void(0)"> <i class="fa fa-globe"></i></a></li>';
			             result = result+'</ul>';
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
			                                                	  
			             result = result+'<li><i class="fa fa-phone"></i>----</li>';         	  
			                                                  }
			                                                  if(json.address != null && json.address != '' && json.address != 'undefined'){
			             result = result+' <li><i class="fa fa-location-arrow"></i>'+json.address+'</li>';
			                                                  }else{
			             result = result+'<li><i class="fa fa-location-arrow"></i>----</li>';                                  	  
			                                                  }
			             result = result+'<li><i class="fa fa-rupee"></i><b>Current Rate:</b>  ';
			             
		             	 if(json.currentRate != null && json.currentRate != '' && json.currentRate != 'undefined'){
			             	result = result+''+json.currentRate+' '+json.currentRateType+'';
			             }else{
			             	result = result+'---- ';                                    
			             }
		             	 result = result+'				     <b>Expected:</b> ';
			             if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
			             	result = result+''+json.expectedRate+' '+json.rateType+'';
			             }else{
			             	result = result+'----';                                  	  
			             }
			             result = result+'</li>'; 
			             result = result+'                 </ul>';
			             
			            /* New comments div creation Nov 8th, 2016 */ 
			            if(json.status == '1' ){
			            '<sec:authorize access="hasAnyRole(\'ROLE_POST_COMMENTS\')">'; 
			            result = result+'<div class="comments-div clearfix" id="latestMessage">';
			            result = result+'    <div class="commentsimg">';
			            result = result+'    <i class="fa fa-comments"></i>';
			            result = result+'        <div class="commentsimg-inner">';
			            
			            result = result+'        	<img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+loginUserId+'"/>';
			            result = result+'        </div>';
			            result = result+'    </div>';
			            result = result+'     <div class="comments-content">';
			            result = result+'         <div class="pull-left">';
			            result = result+'         <p class="comment-by"></p>';
			            result = result+'         <p class="comment-text"></p>';
			            result = result+'         </div>';
			            result = result+'         <div class="pull-right cmt-button">';
			            result = result+'          <span class="comments-button">';
			            
			            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+')">';
			            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
			            result = result+'            </button>';
			            result = result+'            <div class="add-comment">';
			            result = result+'            </div>';
			            result = result+'          </span>';
			            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+');">View more <span class="label label-warning">0</span></a>';
			 			result = result+'		</div>';
			            result = result+'     </div>';
			            result = result+'</div>';
			            '</sec:authorize>';
			            }
			 		    /* New comments div creation Nov 8th, 2016 */
			             
			             
			             
			             result = result+'               </div>';
			             result = result+'			</div>';
			             result = result+'   <div class="proj_close"></div>';
			             result = result+'   <div class="project_side_wrap_inn_tabs">';
			             result = result+'     <ul class="project-tabs-menu">'; 
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CANDIDATE\',\'ROLE_VIEW_EDIT_CANDIDATE\',\'ROLE_VIEW_DELETE_CANDIDATE\')">';
			              result = result+'<li id="candidateProfileTab" class="current"><a href="#tab-1" onclick="candidateProfile('+candidateId+');">Profile</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_RESUMES\',\'ROLE_VIEW_CANDIDATE_RESUMES\',\'ROLE_VIEW_EDIT_CANDIDATE_RESUMES\',\'ROLE_VIEW_DELETE_CANDIDATE_RESUMES\')">';
			               result = result+'<li id="candidateResumeTab"><a href="#tab-2" onclick="candidateResumesList('+candidateId+'); candidateResume('+candidateId+','+json.status+');" >Resume</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_EMPLOYEER\',\'ROLE_VIEW_EMPLOYEER\',\'ROLE_VIEW_EDIT_EMPLOYEER\',\'ROLE_VIEW_DELETE_EMPLOYEER\')">';
			               result = result+'<li id="candidateEmployersTab"><a href="#tab-3" onclick="candidateExperience('+candidateId+');">Employers</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_EDUCATION\',\'ROLE_VIEW_CANDIDATE_EDUCATION\',\'ROLE_VIEW_EDIT_CANDIDATE_EDUCATION\',\'ROLE_DELETE_CANDIDATE_EDUCATION\')">';
			              result = result+'<li id="candidateEducationTab"><a href="#tab-4" onclick="candidateEducation('+candidateId+');">Education</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_EDIT_CANDIDATE_CERTIFICATION\',\'ROLE_VIEW_DELETE_CANDIDATE_CERTIFICATION\')">';
			               result = result+'<li id="candidateCertificationsTab"><a href="#tab-5" onclick="candidateCertificate('+candidateId+');">Certifications</a></li>';
			             '</sec:authorize>';
			             
			             '<sec:authorize access="hasAnyRole(\'ROLE_POST_COMMENTS\')">';
			                result = result+'<li id="candidateCommentTab"><a href="#tab-7" onclick="candidateCommentsList('+candidateId+');">Comments</a></li>';
			              '</sec:authorize>';
			              
			             /* result = result+'       <li><a href="#tab-6" onclick="candidateActivities('+candidateId+');">Activities</a></li>'; */
			             result = result+'       <div class="clearfix"></div>';
			             result = result+'     </ul>';
			             result = result+'     <div class="project_tabs">';
				         result = result + ' <div id="tab-1" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-2" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-3" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + ' <div id="tab-4" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-5" class="project_tabs_inn">';
						 result = result + '</div>';
						 
						 result = result + '<div id="tab-6" class="project_tabs_inn">';
						 result = result + '</div>';
						 result = result + '<div id="tab-7" class="project_tabs_inn">';
						 result = result + '</div>';
			             result = result+'     </div>';
			             result = result+'   </div>';
			             
			             $("#candidateSideDiv").empty();  
			 		     $("#candidateSideDiv").append(result);  
				 		 $(".clients_side_wrap").addClass("clients_side_wrap_active");
						 $(".side_mask").fadeIn(200);
						 $("body").css('overflow-y', 'hidden');
						 candidateProfile(candidateId);
			 			 
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
	  
	  savedCommentAndCount(candidateId);
				
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for show side bar when you click on the candidate name.
     1.usage     : called at saveOrCancel() function. line number 595.
     2.parameter : csrf token , candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function saveCandidateImage(candidateId){
	            		
          		var frm = $('#candidate_form').serializeFormJSON();
              	var con = JSON.stringify(frm);
  			    con = con.replace(/[{}]/g, ""); 
  			    
  		        var jsonData = encodeURIComponent(con);
          		
          		var sourceleadUrl ='${sourceleadUrl}';
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
	                	      
	                      	  url: sourceleadUrl+"/saveCandidateProfile?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,
	                          success: function(result) {
	                        	  cancel(candidateId);
	    			          },  
	            });
}
</script> 


<%-- 
     @author : sarath kumar.
     this script is for hide side bar when you click on the cross button on popup.
--%>

<script> 
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
</script> 

<%-- 
     @author : sarath kumar.
     this script is for load predefined image for candidate.
     1.usage     : called at candidateSideBar() function. line number 166.
--%>

<script>
	function predefineProfileImage(imageId){   
		$(imageId).attr('src','resources/images/userprofile-img.png');
	}
</script>



<%-- 
     @author : sarath kumar.
     this script is for delete image for candidate.
     1.usage     : this script is used to delete image
--%>
<script type="text/javascript">
function deleteImage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Are you sure ?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='deleteProfileImage("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel("+candidateId+");'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for delete image for candidate.
     1.usage : this script is used to delete image
--%>
<script type="text/javascript">
function deleteProfileImage(candidateId){   
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		    type: "GET",
            url: sourceleadUrl+"/deleteCandidateImage?candidateId="+candidateId,
	        success: function(data) {
	        	 var result = data;
	        	 $("#popUp").empty();
	        	 $(".project_inn_mask").fadeOut(200);
	        	 refreshImageBlock(candidateId);
	       },
	        
     }); 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for refresh image block
     1.usage     : 
--%>
<script>
function refreshImageBlock(candidateId){   
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
						 result = result+' <p class="text-danger small" style="font-size: 11px; display: none;" id="errorImage" >Invalid Image/Size</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;png, svg and gif images are not supported</p>';
						 result = result+' <p class="text-muted small" style="font-size: 11px;"><i class="fa fa-info-circle"></i>&nbsp;Image size should not more than 200 KB</p>';
						 result = result+' </div>';
						 $("#candidateImageId").append(result);
			    	}
                }
         	},
      }); 
   }
</script>


<%-- 
     @author : sarath kumar.
     this script is for validate image for candidate.
     1.usage     : used in candidateSideBar() function line number :171.
--%>
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
	 		alertMessage(msg,candidateId);
	    }else if(sizeinbytes > 200000){
	    	output.src = URL.createObjectURL(event.target.files[0]);
	 		var msg = "Image size should be less than 200 KB";
	 		alertMessage(msg,candidateId);
	    }else{
	    	output.src = URL.createObjectURL(event.target.files[0]);
	    	var msg = "Do you want to save the picture.";
	    	saveOrCancel(msg,candidateId);
	   } 
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for show  alert message for candidate image format.
     1.usage     : used in loadFile() function line number :422.
--%>

<script type="text/javascript">
function alertMessage(message,candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	refreshImageBlock(candidateId);

}
</script>

<%-- 
     @author : sarath kumar.
     this script is for conformation message to save file / not .
     1.usage     : used in loadFile() function line number :439.
--%>
<script type="text/javascript">
function saveOrCancel(message,candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onClick='saveCandidateImage("+candidateId+");' ><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<%-- 
     @author : sarath kumar.
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage function line number : 435 / 439.
--%>
<script type="text/javascript">
function cancel(candidateId){ 
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	refreshImageBlock(candidateId);
}
</script>


<!--HTML CONTENT -->

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Candidate List</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo;Candidate List</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head">
        <ul>
          <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
            <div class="export_pop">
              <ul>
                <li onclick="exportToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp; PDF</li>
                <li onclick="exportToExcel();"><i class="fa fa-file-excel-o"></i>&nbsp; Excel</li>
              </ul>
            </div>
          </li>
	      
           <li><i class="fa fa-filter"></i><span id="canDiv"> Status </span><i class="glyphicon glyphicon-play" id="filter2ApprovePop"></i>|
              	<div id="filter2ApprovedPop" class="approved_pop">
                  	<ul>
	                    <li><input id="candidateAll" type="radio" name="candidateStatus" checked  value ="2" onclick="candidateList();"  ><label for="c5">All</label></li>
						<li><input id="candidateActive" type="radio" name="candidateStatus" value ="1"  onclick="candidateList();"><label for="c6">Active</label></li>
						<li><input id="candidateInActive" name="candidateStatus" type="radio" value ="0"  onclick="candidateList();"><label for="c7">In Active</label></li>
                    </ul>
                  </div>                	
          </li>
          
            <li><i class="fa fa-filter"></i><span id="candidateUpdiv"> UpDated Candidates </span><i class="glyphicon glyphicon-play" id="filter1ApprovePop"></i>|
              	<div id="filter1ApprovedPop" class="approved_pop">
                  	<ul>
	                    <li><input id="candidateDayAgo" type="radio" name="upcandidateStatus" value ="1"  onclick="candidateList();"  ><label for="c5">1 Day ago</label></li>
						<li><input id="candidate3DayAgo" type="radio" name="upcandidateStatus" value ="3"  onclick="candidateList();"><label for="c6">3 Day ago</label></li>
						<li><input id="candidate1WeekAgo" name="upcandidateStatus" type="radio" value ="7"  onclick="candidateList();"><label for="c7">1 Week ago</label></li>
						<li><input id="candidate3WeekAgo" name="upcandidateStatus" type="radio" value ="21"  onclick="candidateList();"><label for="c7">2 Week ago</label></li>
						<li><input id="candidateMonthAgo" name="upcandidateStatus" type="radio" value ="30"  onclick="candidateList();"><label for="c7">Month ago</label></li>
	                    <li><input id="candidate3Months"  name="upcandidateStatus" type="radio" value ="90"  onclick="candidateList();"><label for="c7">3 Months</label></li>
                    </ul>
                  </div>                	
          </li>  
          
          
           <sec:authorize access="hasAnyRole('ROLE_CREATE_CANDIDATE')">
              <li><a href="<c:url value='/candidate'/>" title=""><i class="fa fa-plus"></i>Add Candidate</a></li>
           </sec:authorize>
         
          <div class="clearfix"></div>
        </ul>
        
        <div class="clearfix"></div>
      </div>
      <div class="candidate_wrap_table">
        <div id="candidateList"></div>
        <div id="emptyPopup"></div>
      </div>
    </div>
    
    <!-- Compnay details -->
    <div class="side_mask">
       <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
	    <div class="clients_side_wrap_inn">
		             <div id="candidateSideDiv">
				     </div>  
	    </div>			     
    </div>
    <!-- Compnay details Ends --> 
  </div>
</section>

<!--   Error Message   -->
  <div class="vendor_wrap_mask"></div>
  <div class="vendor_error">
  <p>Delete conformation message....</p>
  <textarea placeholder="Enter message"></textarea>
  <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>
<!--   Error Message   --> 
 
<!-- BELOW SCRIPTS ARE USED TO SHOW / HIDE POPUP'S --> 
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
		$(this).next(".export_pop").toggle();
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
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

	// Levels adding script
	/*  $(".addUser-org").click(function(event){  
		 $(".collapse-div-adduser").toggleClass("showMydiv");
		 $(".collapse-div").removeClass("showMydiv");
		 event.preventDefault();
	 }); */
	/*  $(".close-div").click(function(){
		 $(this).parent().toggleClass("showMydiv");
		 }); */
	/*  $(".assign-org").click(function(){
		 $(".collapse-div").toggleClass("show-div");
		 $(".collapse-div-adduser").removeClass("show-div");
	 }); */
	/*  $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div").click(function(e){
		 e.stopPropagation();
	 }); */
	 /* $('html').click(function(){
	    $(".collapse-div-adduser").removeClass("show-div");
		$(".collapse-div").removeClass("show-div");
	   });	
	   $("#endDate").click(function(){
		   $(".endDate").toggle(200);
	   }); */
});
</script>	

<script>
$(document).ready(function() {
	
	
    $("#filter1ApprovePop").click(function(){
		$("#filter1ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
  
    
}); 

</script>

<script>
function toggle(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAll)').click(function() {
    if(!this.checked) {
        $('#checkAll').removeAttr('checked');
    }
});
}
</script>

<script>
function exportToPdf(){
	var isCandidatesAvailable= $("#noCandidates").val();
	 var sourceleadUrl ='${sourceleadUrl}';
	  var candFilterRadio = $('input[name=candidateStatus]');
	  var candidateStatus = candFilterRadio.filter(':checked').val();
	  if(candidateStatus == 1){
        $("#canDiv").text("Active Candidates");
    }else if(candidateStatus == 0){
  	  $("#canDiv").text("In Active Candidates");
    }else if(candidateStatus == 'ALL'){
  	  $("#canDiv").text("All Candidates");
    }else{
        $("#canDiv").removeAttr("onClick");
    }
	  if(isCandidatesAvailable == ''){
	  window.location.href = sourceleadUrl+"/getCandidatesExportList?&candidateStatus="+candidateStatus+"&exportType=pdf";	
	  }else{
		  var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Candidate List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	  }
}

</script>
<script type="text/javascript">
function candidtaeCancel(){
	$("#emptyPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<script>
function exportToExcel(){
	var isCandidatesAvailable= $("#noCandidates").val();
	 var sourceleadUrl ='${sourceleadUrl}';
	  var candFilterRadio = $('input[name=candidateStatus]');
	  var candidateStatus = candFilterRadio.filter(':checked').val();
	  if(candidateStatus == 1){
         $("#canDiv").text("Active Candidates");
     }else if(candidateStatus == 0){
   	  $("#canDiv").text("In Active Candidates");
     }else if(candidateStatus == 'ALL'){
   	  $("#canDiv").text("All Candidates");
     }else{
         $("#canDiv").removeAttr("onClick");
     }
	  if(isCandidatesAvailable == ''){
	  window.location.href = sourceleadUrl+"/getCandidatesExportList?&candidateStatus="+candidateStatus+"&exportType=xls";	
	  }else{
		  var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Candidate List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	  }
}
</script>

<script>
function showComment(candidateId){
	var result = "";
		result = result+'	        <form role="form" id="candidateComments_form" name="candidateComments_form" method="post">';
	    result = result+'              <textarea rows="1" class="form-control clearfix mb10" placeholder="Comments here..." id="comment" name="comment" data-validation="required validate_length length2-500" field-name="Comment"></textarea>';
	    result = result+'              <span class="clearfix pull-right">';
	    result = result+'              		<input type="button" id="submit_comment" value="Add" class="btn btn-sm btn-success" onClick="addComment('+candidateId+')">';
	    result = result+'              		<input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="closeComment()">';
	    result = result+'              </span>';
		result = result+'	        </form>';
	$('.add-comment').empty();
	$('.add-comment').append(result);
	$('.add-comment').toggle(200);
}
function closeComment(){
	$('.add-comment').toggle(200);
}
</script>


<%-- 
     @author : Venkat Kesav
     this script is for saving Comment
     1.usage     : onclick
     2.parameter : candidateId.
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script type="text/javascript">
	function addComment(candidateId){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#candidateComments_form').validate(false, validationSettings)){
				var frm = $('#candidateComments_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				var form = $('#candidateComments_form')[0];
	      		var formData = new FormData(form); 
				var sourceleadUrl = '${sourceleadUrl}';
				$("#submit_comment").attr("disabled", "disabled");
         		$("#submit_comment" ).addClass('button').val('Processing..');
				//It will escape all the special characters
		        var jsonData = encodeURIComponent(con);
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/saveCandidateComment?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&candidateId="+candidateId,
						success : function(result) {
							savedCommentAndCount(candidateId);
							 /* if( result.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	 }
		            		 else if (result.indexOf('exception') >= 0) {
		            			 location.replace(sourceleadUrl+"/errorPage");
		            		 }
		            		 else{
						 		$("#popUpDivResume").removeClass("edit-details-active");
						 		candidateResumesList(candidateId);
		            		 } */
						},
					});
					$('.add-comment').toggle(200);
					return true;
			 }else{ 
					return false;
			 }
		}
		$('body').on('blur', '#candidateComments_form', function() {
		$("#candidateComments_form").showHelpOnFocus();
		$("#candidateComments_form").validateOnBlur(false, validationSettings);
		
	});
</script>




<script>
function savedCommentAndCount(candidateId){
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getAllCommentsOnCandidateByCandidateIdAndOrgLocation?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
         success :function(data) {
        	 var result = "";
              if(data != null && data != 'undefined' && data != ""){
              	 	 var json = JSON.parse(data);
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
            			 var totalComments = 0;
            			 if(json.length>0){
            				totalComments = json.length;
         	            	var report = json[0];
	 			            
	 			            result = result+'    <div class="commentsimg">';
				            result = result+'    <i class="fa fa-comments"></i>';
				            result = result+'        <div class="commentsimg-inner">';
				            
				            result = result+'        	<img onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+report.commentAuthorUserId+'"/>';
				            result = result+'        </div>';
				            result = result+'    </div>';
				            result = result+'     <div class="comments-content">';
				            result = result+'         <div class="pull-left" id="latestMessage">';
				            result = result+'         <p class="comment-by">';
				            result = result+'            By <span class="text-danger">'+report.commentAuthor+'</span>';
				            result = result+'            <span class="small">('+report.commentTime+')</span></p>';
				            result = result+'         <p class="comment-text">'+report.comment+'</p>';
				            result = result+'         </div>';
				            result = result+'         <div class="pull-right cmt-button">';
				            result = result+'          <span class="comments-button">';
				            
				            result = result+'          	<button type="button" class="btn btn-sm btn-primary" onClick="showComment('+candidateId+')">';
				            result = result+'            	<i class="fa fa-plus"></i>&nbsp;Comments';
				            result = result+'            </button>';
				            result = result+'            <div class="add-comment">';
				            result = result+'            </div>';
				            result = result+'          </span>';
				            result = result+'          <a href="javascript:void(0)" class="comments-view" onclick="candidateCommentsList('+candidateId+');">View more <span class="label label-warning">'+totalComments+'</span></a>';
				 			result = result+'		</div>';
				            result = result+'     </div>';
	 			           
	 			           $("#latestMessage").empty();
	 			           $("#latestMessage").append(result);
            			 }
            			 
            		 }
              }
			},
      });
}
</script>
    
<jsp:include page="../masterFooter.jsp" />