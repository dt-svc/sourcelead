<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- end files for autocomplete with tagit -->
<%-- 
     @author : Pavan Kumar Jampana.
     this script is for quick view of Candidate.
--%>
<script>
function applicantQuickViewProfile(submissionId,candidateId){
	  var skillsList = '${skillsList}'; 
	  
	  $("#quickLoading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getSubmissionDetails?"+tokenParamName+"="+tokenValue+"&submissionId="+submissionId,        
         success :function(data) {  
        	 if(data != null){  
              var json = JSON.parse(data);
 	    	   
 	    	   if(json.designationType != null && json.designationType != '' && json.designationType != 'undefined'){
			 		designationType =json.designationType+',';
   			 	}else{
   			 		designationType = '';
   			 	}
 	    	   
 	    	  if(json.requirementSubmissionTemplateFields.expectedCTC != null && json.requirementSubmissionTemplateFields.expectedCTC != '' && json.requirementSubmissionTemplateFields.expectedCTC !='undefined'){
 	    		 expectedRate = json.requirementSubmissionTemplateFields.expectedCTC; 
  	    	   }else{
  	    		  expectedRate= " ";
  	    	   } 
 	    	 
 	    	  if(json.requirementSubmissionTemplateFields.expectedRateType != null && json.requirementSubmissionTemplateFields.expectedRateType != '' && json.requirementSubmissionTemplateFields.expectedRateType !='undefined'){
 	    		 expectedRateType = json.requirementSubmissionTemplateFields.expectedRateType; 
 	    	   }else{
 	    		  expectedRateType = " ";
 	    	   } 
 	    	   
 	    	   if(json.requirementSubmissionTemplateFields.expectedRatePer != null && json.requirementSubmissionTemplateFields.expectedRatePer != '' && json.requirementSubmissionTemplateFields.expectedRatePer !='undefined'){
 	    		  expectedRatePer = ' / ' +json.requirementSubmissionTemplateFields.expectedRatePer; 
 	    	   }else{
 	    		  expectedRatePer = " ";
 	    	   }
 	    	   
 	    	   if(json.requirementSubmissionTemplateFields.state != null && json.requirementSubmissionTemplateFields.state != '' && json.requirementSubmissionTemplateFields.state !='undefined'){
 	    		   state = json.requirementSubmissionTemplateFields.state+',';
 	    	   }else{
 	    		   state = " ";
 	    	   }
 	    	   
 	    	   if(json.requirementSubmissionTemplateFields.city != null && json.requirementSubmissionTemplateFields.city != '' && json.requirementSubmissionTemplateFields.city !='undefined'){
 	    		   city =json.requirementSubmissionTemplateFields.city+','; 
 	    	   }else{
 	    		   city = " ";
 	    	   }
 	    	   
 	    	  if(json.requirementSubmissionTemplateFields.firstName != null && json.requirementSubmissionTemplateFields.firstName != '' && json.requirementSubmissionTemplateFields.firstName != 'undefined'){
 	    		 firstName = json.requirementSubmissionTemplateFields.firstName;
	    	   }else{
	    		   firstName = "";
	    	   }
 	    	   
 	    	   if(json.requirementSubmissionTemplateFields.lastName != null && json.requirementSubmissionTemplateFields.lastName != '' && json.requirementSubmissionTemplateFields.lastName != 'undefined'){
 	    		   lastName = json.requirementSubmissionTemplateFields.lastName;
 	    	   }else{
 	    		   lastName = "";
 	    	   }
 	    	   if(json.requirementSubmissionTemplateFields.country != null && json.requirementSubmissionTemplateFields.country != null && json.requirementSubmissionTemplateFields.country != 'undefined'){
 	    		   country = json.requirementSubmissionTemplateFields.country;
 	    	   }else{
 	    		   country = "";
 	    	   }
              	
 	    	  if(json.requirementSubmissionTemplateFields.emailId != null && json.requirementSubmissionTemplateFields.emailId != null && json.requirementSubmissionTemplateFields.emailId != 'undefined'){
 	    		 emailId = json.requirementSubmissionTemplateFields.emailId;
	    	   }else{
	    		   emailId = "";
	    	   }
              
 	    	 if(json.requirementSubmissionTemplateFields.phoneNumber != null && json.requirementSubmissionTemplateFields.phoneNumber != null && json.requirementSubmissionTemplateFields.phoneNumber != 'undefined'){
 	    		phoneNumber = json.requirementSubmissionTemplateFields.phoneNumber;
	    	   }else{
	    		   phoneNumber = "";
	    	   }
 	    	if(json.skillsList != null && json.skillsList != 'undefined' && json.skillsList != ''){
	                var skill = json.skillsList;
	                var qvSkills = "";
			 		var res = [];
			 		res = skill.split(',');
			 		var minSkills = res.slice(1,20);
			 		var totalSkills = res.length;
 			 		if(totalSkills > 20){
 			 			var remainingSkillsCount = totalSkills - 20;
 			 		}
			 		
	                qvSkills = "<ul>";
	                for(i = 0; i<=minSkills.length ; i++){
	                		if(res[i] != null && res[i] != '' && res[i] != 'undefined'){
		                		qvSkills += "<li>" + res[i] + "</li>";
		                	}
	                		if(totalSkills > 20 && i == minSkills.length){
	                			qvSkills += "<li style='background-color: #d0d0d0;'><i class='fa fa-plus'></i> " + remainingSkillsCount + " more</li>";
	                		}
	                }
	                qvSkills += "</ul>"; 
			 	}else{
			 		qvSkills = "--";
			 	}
 	    	 
		 	     if(json.candidateSummary != null && json.candidateSummary != '' && json.candidateSummary != 'undefined'){
		       	  candidateSummary = json.candidateSummary;
		         }else{
		       	  candidateSummary = "--";
		         }	 
 	    	   
 	    	 		var result ="";
	//						<!-- resumeView Starts -->
					result = result+'  <div class="resumeView">                                                                                                                                    ';
					result = result+'                                                                                                                                                              ';
					result = result+'      <!-- Candiate Header -->                                                                                                                                ';
					result = result+'      <div class="row">                                                                                                                                       ';
					result = result+'          <div class="resume-header clearfix">                                                                                                                ';
					result = result+'        <div class="col-md-7">                                                                                                                                ';
					result = result+'            <div class="media">                                                                                                                               ';
					result = result+'                <div class="media-body">                                                                                                                      ';
					result = result+'                    <h4 class="media-heading text-warning">'+firstName+' '+lastName+'<small> '+designationType+'</small></h4>';
					result = result+'      <div class="trimTxt">';
					result = result+'<p title="'+candidateSummary+'"><i class="fa fa-quote-left text-muted"></i>'+candidateSummary+'</p>';
					result = result+'      </div>';
					result = result+'                </div>                                                                                                                                        ';
					result = result+'                <div class="media-right positionRelative">                                                                                                                     ';
					  if(json.candidate.fileUrl != null && json.candidate.fileUrl !='' && json.candidate.fileUrl !="undefined"){
					result = result+'<img  data-id="profileImage" id="userImage" onerror="predefineProfileImage(this);" src="'+json.candidate.fileUrl+'" alt="Profile Image" class="media-object" style="width:65px">';
					  }else{
					result = result+'<img  data-id="profileImage" id="userImage" onerror="predefineProfileImage(this);" src="/resources/images/userprofileImg.jpg" alt="Profile Image" class="media-object" style="width:65px">';
  
					  } 
					if(json.candidateScore != null && json.candidateScore != '' && json.candidateScore != 'undefined'){
						  if(json.candidateScore >=50){ 
						 	 result = result +'                    <i class="valid-profile fa fa-check-square text-success ProImgPosit"></i>';        
						  }
					  }
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
					
					result = result+'                </div> ';
					result = result+'            </div>                                                                                                                                            ';
					result = result+'        </div>                                                                                                                                                ';
					result = result+'          <div class="col-md-5">                                                                                                                              ';
					result = result+'              <div class="pull-right">                                                                                                                        ';
					result = result+'                  <ul class="list-unstyled">                                                                                                                  ';
					result = result+'                      <li><i class="fa fa fa-envelope paddingRight text-warning" title="Email"></i>'+emailId+'</li>';
					result = result+'                      <li><i class="fa fa-phone paddingRight text-warning" title="Mobile"></i>'+phoneNumber+'</li>                                              ';
					result = result+'                      <li><i class="fa fa-map-marker paddingRight text-warning" title="Location"></i>'+city+''+state+''+country+'</li>';
					result = result+'                    <li><i class="fa fa-usd paddingRight text-warning" title="Rate"></i>'+expectedRate+' '+expectedRateType+''+expectedRatePer+'</li>';
					result = result+'                  </ul>                                                                                                                                       ';
					result = result+'              </div>                                                                                                                                          ';
					result = result+'          </div>                                                                                                                                              ';
					result = result+'              <hr>                                                                                                                                            ';
					result = result+'              </div>                                                                                                                                          ';
					result = result+'      </div>                                                                                                                                                  ';
					result = result+'        <!-- Candiate Header -->                                                                                                                              ';
					result = result+'                                                                                                                                                              ';
					result = result+'      <!-- Work Experience and Skills -->                                                                                                                     ';
					result = result+'      <div class="row">                                                                                                                                       ';
					result = result+'                                                                                                                                                              ';
					result = result+'          <!-- Work Experience and Education BLock -->                                                                                                        ';
					result = result+'          <div class="col-md-6 paddingLeft">                                                                                                                              ';
					result = result+'              <div class="row">                                                                                                                               ';
					//Employee Div
					result = result + '			<div id="reqQvemployeeSubmission"></div> '; 
					//Education Div
					result = result + '			<div id="reqQveducationSubmission"></div> ';
					result = result+'              </div>                                                                                                                                          ';
					result = result+'          </div>                                                                                                                                              ';
					result = result+'          <!-- Work Experience and Education BLock -->                                                                                                        ';
                    result = result+'                                                                                                                                                              ';
					result = result+'          <!-- Skills, Achievements &amp; Certificates BLock -->                                                                                              ';
					result = result+'          <div class="col-md-5 col-md-offset-1 paddingLeft">                                                                                                              ';
					result = result+'              <div class="row">                                                                                                                               ';
					if(json.skillsList != null && json.skillsList != 'undefined' && json.skillsList != ''){
					result = result+'              <div class="col-md-12">                                                                                                                         ';
					result = result+'                  <h4>Skills</h4>                                                                                                           ';
					result = result+'                  <div class="skillsView row">                                                                                                                ';
					result = result+'                  <div class="panel panel-default">                                                                                                                ';
					result = result+'                  <div class="panel-body" id="skillsView"> '+qvSkills+'';
					result = result+'                  </div>';
					result = result+'                  </div>';
					result = result+'                  </div>';
					result = result+'               </div>';
					}
					//Certification Div
					result = result + '<div id="reqQvcertificationSubmission"></div> ';
					
					result = result+'              </div>                                                                                                                                          ';
					result = result+'          </div>                                                                                                                                              ';
					result = result+'           <!-- Skills, Achievements &amp; Certificates BLock -->                                                                                             ';
					result = result+'      </div>                                                                                                                                                  ';
					result = result+'      <!-- Work Experience and Skills -->                                                                                                                     ';
					result = result+'  </div>                                                                                                                                                      ';
// 						      <!-- resumeView -->
						
					$("#applicantQuickViewDiv").empty();
  				    $("#applicantQuickViewDiv").append(result); 
  				    $("#recapplicantQuickViewDiv").empty();
				    $("#recapplicantQuickViewDiv").append(result);
  				 reqQvemployeeSubmission(candidateId);
  				 reqQveducationSubmission(candidateId);
  				reqQwikCertificates(candidateId);
  				 $("#quickLoading").removeAttr("style");
              }else{
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
      					$("#applicantQuickViewDiv").empty();
      				    $("#applicantQuickViewDiv").append(result); 
      				    $("#recapplicantQuickViewDiv").empty();
    				    $("#recapplicantQuickViewDiv").append(result);
                      }
               },
                 
            }); 
}
</script>

<script>
	function reqQvemployeeSubmission(candidateId) {
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId=" + candidateId,
					success : function(response) {
						if (response && response.response === "INVALID_DATA") {
						    showError(response.responseMessage);
						} else if (response && response.response === "INVALID_USER") {
						    showError(response.responseMessage);
						} else if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							$("#reqQvemployeeSubmission").empty();
							var result = "";
							result = result +'            <div class="col-md-12">';
							result = result +' 				<h4>Work Experience</h4>';

							for (var i = 0; i < response.data.length; i++) {
								if(i < 4){
								var opt = response.data[i];
								if (opt.name != null && opt.name != '' && opt.name != 'undefined') {
									name = opt.name;
								} else {
									name = " ";
								}
								if (opt.designation != null	&& opt.designation!='' && opt.designation != 'undefined') {
									designation = opt.designation;
								} else {
									designation = " "; 
								}
								
								
								/*  if (opt.startMonth != null && opt.startMonth != '' && opt.startMonth != 'undefined') {
									startMonth=   opt.startMonth +' - ';
								}else{
									startMonth = " ";
								} */
								
								if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined' && opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
									startYear=   opt.startYear  +' To '+opt.endYear;
								}else if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined'){
									startYear = opt.startYear;
								}else if(opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
									startYear = opt.endYear;
								}else{
									startYear=" ";
								}
								
								/* if (opt.endMonth != null	&& opt.endMonth != '' && opt.endMonth != 'undefined') {
									endMonth = opt.endMonth +' - ';  
								} else {
									endMonth = " ";
								} */
								
								if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
									endYear = opt.endYear;  
								} else {
									endYear = " ";
								}
								if (opt.address != null && opt.address != '' && opt.address != 'undefined') {
									address=   opt.address;
								}else{
									address = " ";
								}
								result = result +'<div class="resume-widget">';
								result = result +'  <p><small>'+startYear+'</small>';
								result = result +'  <small class="pull-right address" title="'+address+'">'+address+'</small></p>';
								result = result +'  <h4>'+designation+'</h4>';
								result = result +'  <h5>'+name+'</h5>';
								result = result +'</div>';
							}
					}
//								result = result +'          </div>';
							result = result +'        </div>';
							$("#reqQvemployeeSubmission").append(result);
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					}
				});
	}
</script>

<script>
function reqQveducationSubmission(candidateId){
	 $.ajax({  
          type : "GET",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && response.response === "INVALID_DATA") {
        		    showError(response.responseMessage);
        		} else if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        				$("#reqQveducationSubmission").empty();
               	 		var result = "";
						result = result +'            <div class="col-md-12">';
						result = result +'              <h4>Education</h4>';
	                     for (var i = 0; i < response.data.length; i++) {
	                    	 if(i < 3){
		                    	 var opt = response.data[i];  
		                    	 if(opt.name != null && opt.name != '' && opt.name != 'undefined'){
	                    		        EducationName = opt.name;
		                    	    }else{
		                    	    	EducationName = " ";
		                    	    }
			                     if(opt.specialization != null && opt.specialization != '' && opt.specialization != 'undefined'){
			                    	 specialization= '(' + opt.specialization +')';
		                    	  }else{
		                    		  specialization= " ";
		                       	 }
			                     if(opt.institute != null && opt.institute != '' && opt.institute != 'undefined'){
			                    	 institute = opt.institute;
		                    	 }else{
		                    		 institute = " ";
		                    	 }
			                     if(opt.grade != null && opt.grade != '' && opt.grade  != 'undefined'){
			                    	 Educationgrade= opt.grade + ' , ';
		                    	 }else{
		                    		 Educationgrade = ""; 
		                    	 }
		                    	 if(opt.gradeType != null && opt.gradeType != '' && opt.grgradeTypeade  != 'undefined'){
		                    		 EducationgradeType  = opt.gradeType + ' - ';
				                 }else{
				                	 EducationgradeType = " "; 
				                 }
		                    	 if(opt.address != null && opt.address != '' && opt.address != 'undefined'){
		                    		 Educationaddress = ' , ' +opt.address;
		                    	 }else{
		                    		 Educationaddress = " ";
		                    	 }
		                    	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined' && opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
		                    		 EducationstartDateStr = opt.startDateStr +' To '+opt.endDateStr;
		                    	 }else if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
		                    		 EducationstartDateStr = opt.startDateStr;
		                    	 }else if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
		                    		 EducationstartDateStr = opt.endDateStr;
		                    	 }else{
		                    		 EducationstartDateStr = " ";
		                    	 }
									result = result +'<div class="resume-widget">';
									result = result +'<p><small>'+EducationstartDateStr+'</small></p>';
									result = result +'<h5>'+EducationName+' '+specialization+'</h5>';
									result = result +'</div>';
	                     	}
	                     }
						result = result +'        </div>';
                    $("#reqQveducationSubmission").append(result);
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
function reqQwikCertificates(candidateId){
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#reqQvcertificationSubmission").empty();
               	 	var result = "";
               	 	result = result +'<div class="col-md-12">';
					 result = result +' <h4>Certifications</h4>';
						for (var i = 0; i < response.data.length; i++) {
							if(i < 3){
	                    	var opt = response.data[i];  
							result = result +'<div class="resume-widget resumewidget">';
							
							if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "" && opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
								result = result +'  <p><small>'+opt.candidatevalidFromDateToString+' - '+opt.candidatevalidToDateToString+'</small></p>';
							}else if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != '' && opt.candidatevalidFromDateToString !='undefined'){
								result = result +'  <p><small>'+opt.candidatevalidFromDateToString+'</small></p>';
							}else if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != '' && opt.candidatevalidToDateToString !='undefined'){
								result = result +'  <p><small>'+opt.candidatevalidToDateToString+'</small></p>';
							}else{
								result=' ';
							}
							
							if(opt.name != null && opt.name != 'undefined' && opt.name != ""){
								name = opt.name;
							}else{
								name = '';
							}
							if(opt.scoreType == 'Percentage'){
								result = result +' <h4>'+name+' ('+opt.score+' %) </h4>';
							}else{
								result = result +' <h4>'+name+' ('+opt.score+' - '+opt.scoreType+') </h4>';
							}
							/* result = result +'<div class="description">';
							result = result +'  <p>'+opt.provider+'</p>';
							result = result +'</div>';   */
							result = result +'                  </div>';
						} 
   	  				}
						result = result +'        </div>';
						$("#reqQvcertificationSubmission").append(result);
        		} else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
        },
     });
}
</script>
