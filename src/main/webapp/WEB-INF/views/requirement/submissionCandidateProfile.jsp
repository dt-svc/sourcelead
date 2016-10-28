<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
 
<script>
function getCandidateProfile(candidateId){
	var sourceleadUrl = '${sourceleadUrl}';
	  var skillsList = '${skillsList}'; 
	  
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
							var result ="";
							
							result = result+'<div id="removeCandidateAlert" ></div>  ';
							result = result + '<div id="profileEditPopup"></div>';
							result = result +'<div class="require_wrap_details">';	 
							result = result +'		<h1>Summary:</h1>';
							result = result +'	    <textarea placeholder="Enter description" readonly>'+json.summary+'</textarea>';
							
						    result = result +'<div class="clearfix mt dashboard-widget">';
							result = result +'<ul class="nav nav-tabs">';
							result = result +' <li class="active"><a data-toggle="tab" href="#skills">General View</a></li>';
							//result = result +'  <li><a data-toggle="tab" href="#milestone">Milestone View</a></li>';
							result = result +'  </ul>';
							result = result +'<div class="tab-content">';
							result = result +'  <div id="skills" class="tab-pane fade in active">';
							result = result +'  <div class="skills-summary">';
							result = result +'<div class="design-view"> ';
							        
							     // Select Skills
							          
							    result = result +'      <h2 class="education">Skills:</h2>';
							    result = result +'      <div class="select-skills">';
							    result = result +'       <div class="divskill col-md-12"> ';
							     result = result + '<div id="candidateSkillList"></div> '; 
								result = result +'           </div>';
								result = result +'          </div>';
								//Select Skills 
								
								result = result +'        </div>';
								
								// Experience Div created on 26th Oct, 2016 
								
								result = result + '<div id="employeeSubmissionProfile"></div> '; 
								
								
								/* result = result +'        <div class="clearfix design-view">';
								result = result +'          <h2 class="education"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;Experience</h2>';
								result = result +'          <div class="row">';
								result = result +'            <div class="col-md-12">';
								result = result +'              <ul class="list-unstyled personal-info">';
								result = result +'                <li>';
								result = result +'                  <div class="clearfix">';
								result = result +'                    <h3>Business Analyst</h3>';
								result = result +'                    <p>April 2014 to July 2016,(2 Years, 2 Months)</p>';
								result = result +'                    <p>Business Intelli Solltions, Hyderabad, India</p>';
								result = result +'                  </div>';
								result = result +'                </li>';
								result = result +'              </ul>';
								result = result +'            </div>';
								result = result +'          </div>';
								result = result +'        </div>'; */
								
								// Experience Div created on 26th Oct, 2016 
								
								// Certification Div created on 26th Oct, 2016 
								
								result = result + '<div id="educationSubmissionProfile"></div> ';
								
								/* 
								result = result +'        <div class="clearfix design-view">';
								result = result +'          <h2 class="education"><i class="fa fa-graduation-cap"></i>&nbsp;&nbsp;Education</h2>';
								result = result +'          <div class="row">';
								result = result +'            <div class="col-md-12">';
								result = result +'              <ul class="list-unstyled personal-info">';
								result = result +'                <li>';
								result = result +'                  <div class="clearfix">';
								result = result +'                    <h3>MBA <span class="small text-muted">&nbsp;(Finance)</span></h3>';
								result = result +'                    <p>St.Xaviers college, Palayamkottai, 2010</p>';
								result = result +'                  </div>';
								result = result +'                </li>';
								result = result +'              </ul>';
								result = result +'            </div>';
								result = result +'          </div>';
								result = result +'        </div>'; */
								
								 //Certification Div created on 26th Oct, 2016  
								
								// Education Div created on 26th Oct, 2016
								
						/* 		result = result +'        <div class="clearfix design-view">';
								result = result +'          <h2 class="education"><i class="fa fa-certificate"></i>&nbsp;&nbsp;Certification</h2>';
								result = result +'          <div class="row">';
								result = result +'            <div class="col-md-12">';
								result = result +'              <ul class="list-unstyled personal-info">';
								result = result +'                <li>';
								result = result +'                  <div class="clearfix">';
								result = result +'                    <h3>Certified Scrum Master</h3>';
								result = result +'                    <p>August 2010 – August 2012</p>';
								result = result +'                  </div>';
								result = result +'                </li>';
								result = result +'              </ul>';
								result = result +'            </div>';
								result = result +'          </div>';
								result = result +'        </div>'; */
								
								result = result + '<div id="certificationSubmissionProfile"></div> ';
								
								
								// Education Div created on 26th Oct, 2016
								
								result = result +'      </div>';
								result = result +'    </div>';
								result = result +'    <div id="milestone" class="tab-pane fade">';
								result = result +'      <div class="skills-graph">';
								result = result +'        <h2 class="education">Skills Level</h2>';
								result = result +'        <textarea placeholder="Enter description">Typesetting industry.</textarea>';
								result = result +'      </div>';
								result = result +'    </div>';
								result = result +'  </div>';
								result = result +'</div>'; 
							
							
							result = result + '    </div>';
							result = result + '  </div>';
							$("#tabb-1").empty();
							$("#tabb-2").empty(); 
							$("#tabb-2").append(result);  
							    
							candidateSkillsList(candidateId);
							employeeSubmissionProfile(candidateId);
							educationSubmissionProfile(candidateId);
							certificationSubmissionProfile(candidateId);
								
								$(".btn-addskills").click(function(){
									$(".add-skills").css({"display":"block","opacity":"1"});
									$("#skillName").val('');
									 
									/* skill auto complete */
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
												
									
									});
								$(".close-div").click(function(){
									$(".add-skills").removeClass('add-skills-active');
									});
								$(".add-skills").click(function(e){
									 e.stopPropagation();
								});
								
								 $(".addUser-org").click(function(){
									 $(".edit-details").toggleClass("edit-details-active");
									 $(".collapse-div").removeClass("edit-details-active");
								 });
								 $(".close-div").click(function(){
									 $(this).parent().toggleClass("edit-details-active");
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
							     event.preventDefault();
								   
          		 }
            }
             },
               
          }); 
}
</script>

<script>
function candidateSkillsList(candidateId){  
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidatesSkillsBycandidateID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != "" && data != '[]'){
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#candidateSkillList").empty();
	                	 var result = "";
                	     result = result + '      <table class="table table-responsive table-hover clearfix">';
                		 result = result + '        <thead>';
                		 result = result + '          <tr>';
                		 result = result + '            <th>Skills</th>';
                		 result = result + '            <th>Level</th>';
                		 result = result + '            <th>Experience</th>';
                		 result = result + '            <th>Action</th>';
                		 result = result + '          </tr>';
                		 result = result + '        </thead>';
                		 result = result + '        <tbody>';
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.length; i++) {  
	                    	 var opt = json[i];   
	                    	    result = result + '          <tr>';
		                		result = result + '            <td><p class="form-control-static">'+opt.name+'</p></td>';
		                		result = result + '            <td><select id="scale" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);" >';
		                		result = result + '                <option selected="" disabled="">Select</option>';
		                		
		                		if(opt.scale == "Beginner"){
		                			result = result + '<option selected>Beginner</option>';
		                		}else{
		                			result = result + '<option>Beginner</option>';
		                		}
		                		if(opt.scale == "Familiar"){
		                		    result = result +'<option selected>Familiar</option>';
		                		}else{
		                			result = result +'<option>Familiar</option>';
		                		}
		                		if(opt.scale == "proficient"){
		                			result = result + '<option selected>proficient</option>';
		                		}else{
		                			result = result + '<option>proficient</option>';
		                		}
		                		if(opt.scale == "Expert"){
		                			result = result + '<option selected>Expert</option>';
		                		}else{
		                			result = result + '<option>Expert</option>';
		                		}
		                		if(opt.scale == "Master"){
		                			result = result + '<option selected>Master</option>';
		                		}else{
		                			result = result + '  <option>Master</option>';
		                		}
		                		result = result + '              </select></td>';
		                		result = result + '            <td><select id="totalExperience" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);">';
		                		result = result + '                <option selected="" disabled="">Select</option>';
		                		if(opt.totalExperience == "0"){
		                			result = result + '<option selected>0</option>';   
		                		}else{
		                			result = result + '<option>0</option>';
		                		}
		                		if(opt.totalExperience == "1"){
		                			result = result + '<option selected>1</option>';   
		                		}else{
		                			result = result + '<option>1</option>';
		                		}
		                		if(opt.totalExperience == "2"){
		                			result = result + '<option selected>2</option>';   
		                		}else{
		                			result = result + '<option>2</option>';
		                		}
		                		if(opt.totalExperience == "3"){
		                			result = result + '<option selected>3</option>';   
		                		}else{
		                			result = result + '<option>3</option>';
		                		}
		                		if(opt.totalExperience == "4"){
		                			result = result + '<option selected>4</option>';   
		                		}else{
		                			result = result + '<option>4</option>';
		                		}
		                		if(opt.totalExperience == "5"){
		                			result = result + '<option selected>5</option>';   
		                		}else{
		                			result = result + '<option>5</option>';
		                		}
		                		if(opt.totalExperience == "6"){
		                			result = result + '<option selected>6</option>';   
		                		}else{
		                			result = result + '<option>6</option>';
		                		}
		                		result = result + '              </select></td>';
		                		result = result + '            <td><div class="text-center"><a onclick="removeSkillById('+candidateId+', '+opt.id+');" href="javascript:void(0)"> <i class="fa fa-trash"></i></a></div></td>';
		                		result = result + '          </tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateSkillList").append(result); 
			    	} 
               }else{
	    	         var result ="";
		    	     result = result+"   <div class='row'>"; 
					 result = result+"	<div class='col-lg-12'>"; 
					 result = result+"		<div class='content-area clearfix'>"; 
					 result = result+"			<div class='blank-pages'>";
					 result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"			        <p>No Skills Found</p>";
					 result = result+"			 </div>";
					 result = result+"		</div>"; 
					 result = result+"	</div>"; 
					 result = result+"  </div> "; 
					$("#candidateSkillList").empty();
				    $("#candidateSkillList").append(result); 
               }
        },
          
     }); 
}
</script>



<script>
	function employeeSubmissionProfile(candidateId) {
		var sourceleadUrl = '${sourceleadUrl}';
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId=" + candidateId,
					success : function(data) {
						if (data != null && data != 'undefined' && data != "" && data != '[]') {
							if (data.indexOf('Sign In') >= 0) {
								location.replace(sourceleadUrl);
							} else if (data.indexOf('exception') >= 0) {
								location.replace(sourceleadUrl + "/errorPage");
							} else {
								$("#employeeSubmissionDetailsList").empty();
								var result = "";
								result = result +'        <div class="clearfix design-view">';
								result = result +'          <h2 class="education"><i class="fa fa-black-tie"></i>&nbsp;&nbsp;Experience</h2>';
								result = result +'          <div class="row">';
								result = result +'            <div class="col-md-12">';
								result = result +'              <ul class="list-unstyled personal-info">';
								var json = JSON.parse(data);
								for (var i = 0; i < json.length; i++) {
									var opt = json[i];
									if (opt.name != null && opt.name != '' && opt.name != 'undefined') {
										name = opt.name;
									} else {
										name = " ";
									}
									if (opt.designation != null	&& opt.designation && opt.designation != 'undefined') {
										designation = opt.designation;
									} else {
										designation = " "; 
									}
									
									
									 if (opt.startMonth != null && opt.startMonth != '' && opt.startMonth != 'undefined') {
										startMonth=   opt.startMonth +' - ';
									}else{
										startMonth = " ";
									}
									
									if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
										startYear=   opt.startYear  +' TO ';
									}else{
										startYear = " ";
									}
									
									if (opt.endMonth != null	&& opt.endMonth != '' && opt.endMonth != 'undefined') {
										endMonth = opt.endMonth +' - ';  
									} else {
										endMonth = " ";
									}
									
									if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
										endYear = opt.endYear;  
									} else {
										endYear = " ";
									}
									result = result +'   <li>';
									result = result +'   <div class="clearfix">';
									result = result +' <h3>'+name+'</h3>';
									result = result +' <p>'+designation+'</p>';
									result = result +' <p>';
								    result = result + startMonth +''+startYear +' '+endMonth +''+endYear; 
									result = result +'  </p>';
									result = result +'  </div>';
									result = result +'</li>';
								}
								result = result +'              </ul>';
								result = result +'            </div>';
								result = result +'          </div>';
								result = result +'        </div>';
								$("#employeeSubmissionProfile").append(result);
							}
						}
					},

				});
	}
</script>

<script>
function educationSubmissionProfile(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
        	  if (data != null && data != 'undefined' && data != "" && data != '[]') {
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#educationSubmissionDetailsList").empty();
	                	 var result = "";
	                	 
	                	 
	                	     result = result +'        <div class="clearfix design-view">';
							result = result +'          <h2 class="education"><i class="fa fa-graduation-cap"></i>&nbsp;&nbsp;Education</h2>';
							result = result +'          <div class="row">';
							result = result +'            <div class="col-md-12">';
							result = result +'              <ul class="list-unstyled personal-info">';
							
							
							 var json = JSON.parse(data);
		                     for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i];  
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
		                    		 EducationstartDateStr = opt.startDateStr +' TO ';
		                    	 }else{
		                    		 EducationstartDateStr = " ";
		                    	 }
		                    	 if(opt.endDateStr != null && opt.endDateStr != '' && opt.endDateStr != 'undefined'){ 
		                    		 EducationendDateStr = opt.endDateStr;
		                    	 }else{
		                    		 EducationendDateStr = " ";
		                    	 }
			                        result = result +'                <li>';
									result = result +'                  <div class="clearfix">';
		                    	    result = result +'                    <h3>'+EducationName+' <span class="small text-muted">&nbsp;'+specialization+'</span></h3>';
		                    	    result = result +'                    <p>'+EducationgradeType + ' '+Educationgrade+' '+institute+' '+Educationaddress+'</p>';
									result = result +'                    <p>'+EducationstartDateStr+' '+EducationendDateStr+'</p>';
									result = result +'                  </div>';
									result = result +'                </li>';
		                     }
							
							
							result = result +'              </ul>';
							result = result +'            </div>';
							result = result +'          </div>';
							result = result +'        </div>';
	                     $("#educationSubmissionProfile").append(result); 
			    	} 
        	  }
        },
          
     }); 
}
</script> 

<script>
function certificationSubmissionProfile(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
        	  if (data != null && data != 'undefined' && data != "" && data != '[]') {
             		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
             		 else if (data.indexOf('exception') >= 0) {
             			 location.replace(sourceleadUrl+"/errorPage");
             		 }
             		 else{
	                	 $("#certificationSubmissionProfile").empty();
	                	 var result = "";
	                	 
	                	 result = result +'        <div class="clearfix design-view">';
							result = result +'          <h2 class="education"><i class="fa fa-certificate"></i>&nbsp;&nbsp;Certification</h2>';
							result = result +'          <div class="row">';
							result = result +'            <div class="col-md-12">';
							result = result +'              <ul class="list-unstyled personal-info">';
							 var json = JSON.parse(data);
							for (var i = 0; i < json.length; i++) { 
		                    	var opt = json[i];  
		                    	if(opt.score != null && opt.score != 'undefined' && opt.score != ""){
				            		score =' ( ' + opt.score;
				            	}else{
				            		score = " ";
				            	}
				            	if(opt.scoreType != null && opt.scoreType != 'undefined' && opt.scoreType != ""){
				            		scoreType = opt.scoreType +' ) ';
				            	}else{
				            		scoreType = " ";
				            	}
				            	if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != ""){
				            		candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
				            	}else{
				            		candidatevalidFromDateToString = " ";
				            	}
				            	if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
				            		candidatevalidToDateToString = opt.candidatevalidToDateToString;
				            	}else{
				            		candidatevalidToDateToString = " ";
				            	}
				            	
								result = result +'                <li>';
								result = result +'                  <div class="clearfix">';
								result = result +'                    <h3>'+opt.name+' <span class="small text-muted">&nbsp;'+score+'  '+scoreType+'</span></h3>';
								result = result +'                    <p>'+opt.provider+' </p>';
								result = result +'                    <p> '+candidatevalidFromDateToString+' TO '+candidatevalidToDateToString+'</p>';
								result = result +'                  </div>';
								result = result +'                </li>';
							}
							result = result +'              </ul>';
							result = result +'            </div>';
							result = result +'          </div>';
							result = result +'        </div>';
							
							$("#certificationSubmissionProfile").append(result); 
			    	} 
        	  }
        },
          
     }); 
}
</script>
