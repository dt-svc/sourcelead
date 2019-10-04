<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 
     @author : Pavan Kumar Jampana.
     this script is for quick view of Candidate.
--%>

<script>
function candidateQuickViewProfile(candidateId){
	  var skillsList = '${skillsList}'; 
	  $.ajax({
        type : "POST",
         url: atsUrl +"/getCandidateGlobalById?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
         success :function(result) {  
        	 var json = result.object;
      	     if(result.response === "SESSION_TIMEDOUT") {
					location.replace(sourceleadurl);
				} else if (result.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (result.response === "SUCCESS") {
              		if(json != null){  
							var result ="";
							
							if(json.country != null && json.country != '' && json.country != 'undefined'){
	         			 		country =json.country;
	             			 	}else{
	             			 		country = '';
	             			 	}
	         			 	
	         			 	if(json.state != null && json.state != '' && json.state != 'undefined'){
	         			 		state =json.state+',';
	             			 	}else{
	             			 		state = '';
	             			 	}
	         			 	
	         			 	if(json.city != null && json.city != '' && json.city != 'undefined'){
	         			 		city =json.city+',';
	             			 	}else{
	             			 		city = '';
	             			 	}	
	         			 	
	         			 	if(json.lastName != null && json.lastName != '' && json.lastName != 'undefined'){
	         			 		lastName =json.lastName;
	             			 	}else{
	             			 		lastName = '';
	             			 	}
	         			 	if(json.middleName != null && json.middleName !='' && json.middleName != 'undefined'){
	         			 		middleName = json.middleName;
	         			 	}else{
	         			 		middleName = "";
	         			 	}
	         			 	if(json.mobilePhone != null && json.mobilePhone != '' && json.mobilePhone != 'undefined'){
	         			 		mobilePhone =json.mobilePhone;
	             			 	}else{
	             			 		mobilePhone = '';
	             			 	}
	         			 	if(json.designationType != null && json.designationType != '' && json.designationType != 'undefined'){
	         			 		designationType =json.designationType+',';
	             			 	}else{
	             			 		designationType = '';
	             			 	}
	         			 	
	         			 	if(json.expectedRate != null && json.expectedRate != '' && json.expectedRate != 'undefined'){
	         			 		expectedRate = json.expectedRate;
	         			 		expectedRateCurrencey = '/'+json.expectedRateCurrencey;
	         			 	}else{
	         			 		expectedRate = '---';
	         			 		expectedRateCurrencey = '';
	         			 	}
	         			 	
	         			 	if(json.rateType != null && json.rateType != '' && json.rateType != 'undefined'){
	         			 		rateType = json.rateType;
	         			 	}else{
	         			 		rateType = '';
	         			 	}
	         			 	if(json.skillsName != null && json.skillsName != 'undefined' && json.skillsName != ''){
				               var skill = json.skillsName;
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
	         			 		qvSkills = "";
	         			 	}
							
// 					<!-- resumeView Starts -->
					result = result+'  <div class="resumeView">                                                                                                                                    ';
					result = result+'                                                                                                                                                              ';
					result = result+'      <!-- Candiate Header -->                                                                                                                                ';
					result = result+'      <div class="row">';
					result = result+'          <div class="resume-header clearfix">                                                                                                                ';
					result = result+'        <div class="col-md-7">                                                                                                                                ';
					result = result+'            <div class="media">                                                                                                                               ';
					result = result+'                <div class="media-body">                                                                                                                      ';
					result = result+'                    <h4 class="media-heading text-warning">'+json.firstName+' '+middleName+' '+lastName+'<small> '+designationType+'</small></h4>';
					if(json.summary != null && json.summary != '' && json.summary != 'undifined'){
						
						result = result+'      <div class="trimTxt">';
						result = result+'<p title="'+json.summary+'"><i class="fa fa-quote-left text-muted"></i>'+json.summary+'</p>';
						result = result+'      </div>';
					}else{
						result = result+'<p>---</p>';
					}
					
					result = result+'                </div>                                                                                                                                        ';
					result = result+'                <div class="media-right">                                                                                                                     ';
					if(json.fileUrl != null && json.fileUrl !='' && json.fileUrl !="undefined"){
					result = result+'<img data-id="profileImage" id="userImage" src="'+json.fileUrl+'" alt="Profile Image" class="media-object" style="width:65px">';
					}else{
					result = result+'<img data-id="profileImage" id="userImage" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/userprofileImg.jpg" alt="Profile Image" class="media-object" style="width:65px">';
					} 
					result = result+'  <div class="mb mt5 UsProBar">';                                                                                                                                        
						result = result+' <div class="progress nomr">  ';
					if(json.profilePercentage>=50){ 
						result = result+'   <div class="progress-bar  progress-bar-success  ';
					}
					else{
						result = result+'   <div class="progress-bar  progress-bar-danger  ';
					}
					result = result+' progress-bar-striped active" role="progressbar" id="quickPerVal" aria-valuenow="'+json.profilePercentage+'" aria-valuemin="0" aria-valuemax="100" style="width:'+json.profilePercentage+'%">  ';
					result = result+'     <p id="quickDisVal">'+json.profilePercentage+'%</p>                                                                                                                                                                      ';
					result = result+'   </div>  ';
					result = result+' </div>                 ';                                                                                                                                                      
					result = result+' </div> ';
					result = result+'                </div>                                                                                                                                        ';
					
					result = result+'            </div>                                                                                                                                            ';
					result = result+'        </div>                                                                                                                                                ';
					result = result+'          <div class="col-md-5">                                                                                                                              ';
					result = result+'              <div class="pull-right">                                                                                                                        ';
					result = result+'                  <ul class="list-unstyled">                                                                                                                  ';
					result = result+'                      <li><i class="fa fa fa-envelope paddingRight text-warning" title="Email"></i>'+json.email+'';
					result = result+'                      </li>                                                                                                                                   ';
					result = result+'                      <li><i class="fa fa-phone paddingRight text-warning" title="Mobile"></i>'+mobilePhone+'</li>                                              ';
					result = result+'                      <li><i class="fa fa-map-marker paddingRight text-warning" title="Location"></i>'+city+''+state+''+country+'</li>';
					result = result+'                    <li><i class="fa fa-usd paddingRight text-warning" title="Rate"></i>'+expectedRate+' '+rateType+''+expectedRateCurrencey+'</li>';
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
					result = result + '			<div id="qvemployeeSubmission"></div> '; 
					//Education Div
					result = result + '			<div id="qveducationSubmission"></div> ';
					result = result+'              </div>                                                                                                                                          ';
					result = result+'          </div>                                                                                                                                              ';
					result = result+'          <!-- Work Experience and Education BLock -->                                                                                                        ';
                    result = result+'                                                                                                                                                              ';
					result = result+'          <!-- Skills, Achievements &amp; Certificates BLock -->                                                                                              ';
					result = result+'          <div class="col-md-5 col-md-offset-1 paddingLeft">                                                                                                              ';
					result = result+'              <div class="row">                                                                                                                               ';
					if(json.skillsName != null && json.skillsName != 'undefined' && json.skillsName != ''){
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
					result = result + '<div id="qvcertificationSubmission"></div> ';
					
					result = result+'              </div>                                                                                                                                          ';
					result = result+'          </div>                                                                                                                                              ';
					result = result+'           <!-- Skills, Achievements &amp; Certificates BLock -->                                                                                             ';
					result = result+'      </div>                                                                                                                                                  ';
					result = result+'      <!-- Work Experience and Skills -->                                                                                                                     ';
					result = result+'  </div>                                                                                                                                                      ';
						
					$("#candidateQuickViewDiv").empty();
  				    $("#candidateQuickViewDiv").append(result); 
  				    qvemployeeSubmission(candidateId);
  				    qveducationSubmission(candidateId);
					qwikCertificates(candidateId);
					console.log(result);
					candidateSideBar(candidateId); 
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
      					$("#candidateQuickViewDiv").empty();
      					
      				    $("#candidateQuickViewDiv").append(result); 
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
	function qvemployeeSubmission(candidateId) {
		$.ajax({
					type : "POST",
					url : atsUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?&candidateId=" + candidateId,
					success : function(data) {
						if (data && data.response === "INVALID_DATA") {
						    $("#update_candidate").removeAttr("disabled");
						    $("#update_candidate").removeAttr('button').val('Save');
						    showError(result.responseMessage);
						} else if (data.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (data.response === "SUCCESS") {
							if(data.data.length > 0){
								
							
							$("#qvemployeeSubmission").empty();
							var result = "";
							result = result +'<div class="col-md-12">';
							result = result +'<h4>Work Experience</h4>';
							for (var i = 0; i < data.data.length; i++) {
								if(i < 4) {
									var opt = data.data[i];
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
									if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
										startYear=   opt.startYear;
									} else{
										startYear = " ";
									}
									if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
										endYear = opt.endYear;  
									} else {
										endYear = " ";
									}
									if (opt.address != null && opt.address != '' && opt.address != 'undefined') {
										address=   opt.address;
									} else {
										address = " ";
									}
									result = result +'<div class="resume-widget">';
									if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined' && opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined'){
										result = result +'  <p><small>'+startYear +' To '+endYear+'</small>';
								    } else if(opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
								    	result = result +'  <p><small>'+startYear +'</small>';
								    } else if(opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
								    	result = result +'  <p><small>'+endYear+'</small>';
								    }
									result = result +'  <small class="pull-right address" title="'+address+'">'+address+'</small></p>';
									result = result +'  <h4>'+designation+'</h4>';
									result = result +'  <h5>'+name+'</h5>';
									result = result +'</div>';
								}
							}
							result = result +'</div>';
							$("#qvemployeeSubmission").append(result);
							} else {
							 	var result = '';
				        	  	result = result +'<div class="col-md-12" style="display:none">';
				        	  	result = result +' <h4>Work Experience</h4>';
				        	  	result = result +' <div>---</div>';
				        	  	result = result +'</div>';
				        	  	$("#qvemployeeSubmission").empty();
				        	  	$("#qvemployeeSubmission").append(result);
							}
						} else if (data.indexOf('Sign In') !== -1) {
							/* location.replace(sourceleadurl); */
						} else {
						    showError("Operation failed, please try again");
						}
					},
				});
	}
</script>

<script>
function qveducationSubmission(candidateId){
	 $.ajax({  
          type : "GET",
          url: atsUrl+"/getCandidateEducationListByCandidateId?&candidateId="+candidateId,        
        		  success :function(data) {
								if(data && data.response === "INVALID_DATA") {
       								showError(data.responseMessage);
       							} else if (data.response === "AWKWARD") {
       	    						location.href = "/errorPage";
					       		} else if (data.response === "SUCCESS") {
					       			$("#qveducationSubmission").empty();
				                	 var result = "";
										result = result +'            <div class="col-md-12">';
										result = result +'              <h4>Education</h4>';
					                     for (var i = 0; i < data.data.length; i++) {
					                    	 if(i < 3) {
						                    	 var opt = data.data[i];  
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
						                    	 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
						                    		 EducationstartDateStr = opt.startDateStr;
						                    	 }else{
						                    		 EducationstartDateStr = " ";
						                    	 }
						                    	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
						                    		 EducationendDateStr = opt.endDateStr;
						                    	 }else{
						                    		 EducationendDateStr = " ";
						                    	 }
						                    	 
													result = result +'<div class="resume-widget">';
													 if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined' && opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
														 result = result +'<p><small>'+EducationstartDateStr+' To '+EducationendDateStr+'</small></p>';
								                    	    }else if(opt.startDateStr != null && opt.startDateStr != '' && opt.startDateStr != 'undefined'){
								                    	    	result = result +'<p><small>'+EducationstartDateStr+'</small></p>';
								                    	    }else if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){
								                    	    	result = result +'<p><small>'+EducationendDateStr+'</small></p>';
								                    	    }
//			 										result = result +'<p><small>'+EducationstartDateStr+' '+EducationendDateStr+'</small></p>';
													result = result +'<h5>'+EducationName+' '+specialization+'</h5>';
													result = result +'</div>';
					                     	}
					                     }
										
										result = result +'        </div>';
				                     $("#qveducationSubmission").append(result); 
				                     
				                     
				                     /* var result = '';
				               	  result = result +'            <div class="col-md-12 " style="display:none">';
				               	  result = result +' <h4>Education</h4>';
				               	  result = result +' <div>---</div>';
				               	  result = result +'            </div>';
				               	  $("#qveducationSubmission").empty();
				               	  $("#qveducationSubmission").append(result); */
					       		} else if (data.indexOf('Sign In') !== -1) {
        							location.replace(sourceleadurl);
					        	} else {
					        	    showError("Operation failed, please try again");
					        	}
        },
     }); 
}
</script> 

<script>
function qwikCertificates(candidateId){
	 $.ajax({  
          type : "POST",
          url: atsUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        
          success :function(response) {
        	  	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			if(response.data.length>0) {
        				 $("#qvcertificationSubmission").empty();
	                	 var result = "";
	                	 result = result +'<div class="col-md-12">';
						 result = result +' <h4>Certifications</h4>';
// 							var json = JSON.parse(data);
							for (var i = 0; i < response.data.length; i++) {
								if(i < 3) {
		                    	var opt = response.data[i];
								result = result +'<div class="resume-widget resumewidget">';
								
								if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != "" && opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
									result = result +'  <p><small>'+opt.candidatevalidFromDateToString+' To '+opt.candidatevalidToDateToString+'</small></p>';
								}else if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != '' && opt.candidatevalidFromDateToString !='undefined'){
									result = result +'  <p><small>'+opt.candidatevalidFromDateToString+'</small></p>';
								}else if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != '' && opt.candidatevalidToDateToString !='undefined'){
									result = result +'  <p><small>'+opt.candidatevalidToDateToString+'</small></p>';
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
							$("#qvcertificationSubmission").append(result); 
        			} else {
        				 var result = '';
        	        	  result = result +'            <div class="col-md-12 " style="display:none">';
        	        	  result = result +' <h4>Certifications</h4>';
        	        	  result = result +' <div>---</div>';
        	        	  result = result +'            </div>';
        	        	  $("#qvcertificationSubmission").empty();
        	        	  $("#qvcertificationSubmission").append(result); 
        			}
        		} else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadurl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
        },
     });
}
</script>
