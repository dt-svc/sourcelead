<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->
<%-- 
     @author : Pavan Kumar Jampana.
     this script is for quick view of Candidate.
--%>


<%-- 
     @author : sarath kumar.
     this script is for edit Candidate.
     1.usage     :  used in candidateList.jsp.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function candidateQuickViewProfile(candidateId){
	  var sourceleadUrl = '${sourceleadUrl}';
	  var skillsList = '${skillsList}'; 
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getCandidateById?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
         success :function(data) {  
              if(data != null && data != 'undefined' && data != ""){  
              	var json = JSON.parse(data);
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
							
// 					<!-- resumeView Starts -->
					result = result+'  <div class="resumeView">                                                                                                                                    ';
					result = result+'                                                                                                                                                              ';
					result = result+'      <!-- Candiate Header -->                                                                                                                                ';
					result = result+'      <div class="row">                                                                                                                                       ';
					result = result+'          <div class="resume-header clearfix">                                                                                                                ';
					result = result+'        <div class="col-md-7">                                                                                                                                ';
					result = result+'            <div class="media">                                                                                                                               ';
					result = result+'                <div class="media-body">                                                                                                                      ';
					result = result+'                    <h4 class="media-heading text-warning">'+json.firstName+' '+lastName+'<small>'+json.designationType+'</small></h4>';
					if(json.summary != null && json.summary != '' && json.summary != 'undifined'){
						result = result+'<p>'+json.summary+'</p>';
					}else{
						result = result+'<p>---</p>';
					}
					
					result = result+'                    <div class="text-info">Rate Per: <span class="paddingRight"></span>'+expectedRate+' '+rateType+''+expectedRateCurrencey+'</div>';
					result = result+'                </div>                                                                                                                                        ';
					result = result+'                <div class="media-right">                                                                                                                     ';
					result = result+'<img  id="userImage" onerror="predefineProfileImage(this);" src="<c:url value="/displayCandidateImage/'+candidateId+'"/>" alt="Profile Image" class="media-object" style="width:65px">';
					result = result+'                </div>                                                                                                                                        ';
					result = result+'            </div>                                                                                                                                            ';
					result = result+'        </div>                                                                                                                                                ';
					result = result+'          <div class="col-md-5">                                                                                                                              ';
					result = result+'              <div class="pull-right">                                                                                                                        ';
					result = result+'                  <ul class="list-unstyled">                                                                                                                  ';
					result = result+'                      <li><i class="fa fa fa-envelope paddingRight text-warning" title="'+json.email+'"></i>'+json.email+'';
					result = result+'                      </li>                                                                                                                                   ';
					result = result+'                      <li><i class="fa fa-phone paddingRight text-warning" title="'+mobilePhone+'"></i>'+mobilePhone+'</li>                                              ';
					result = result+'                      <li><i class="fa fa-map-marker paddingRight text-warning" title="'+city+''+state+''+country+'"></i>'+city+''+state+''+country+'</li>';
// 					result = result+'                      <li><i class="fa fa-skype paddingRight text-info" title="Skype"></i>candidate.sky</li>                                                  ';
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
					result = result+'          <div class="col-md-6">                                                                                                                              ';
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
					result = result+'          <div class="col-md-5 col-md-offset-1">                                                                                                              ';
					result = result+'              <div class="row">                                                                                                                               ';
					result = result+'              <div class="col-md-12">                                                                                                                         ';
					result = result+'                  <h4>Skills &amp; Competences</h4>                                                                                                           ';
					result = result+'                  <div class="skillsView row">                                                                                                                ';
					result = result+'                        <!-- Skills Progress View -->                                                                                                         ';
					result = result+'                        <div class="clearfix">                                                                                                                ';
					result = result+'                          <div class="col-md-4 text-right"> Java </div>                                                                                       ';
					result = result+'                          <div class="col-md-8">                                                                                                              ';
					result = result+'                            <div class="progress">                                                                                                            ';
					result = result+'                              <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:80%" title="Expert"></div>';
					result = result+'                            </div>                                                                                                                            ';
					result = result+'                          </div>                                                                                                                              ';
					result = result+'                        </div>                                                                                                                                ';
					result = result+'                        <div class="clearfix">                                                                                                                ';
					result = result+'                          <div class="col-md-4 text-right">J2EE Application</div>                                                                             ';
					result = result+'                          <div class="col-md-8">                                                                                                              ';
					result = result+'                            <div class="progress">                                                                                                            ';
					result = result+'                              <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:60%" title="Expert"></div>';
					result = result+'                            </div>                                                                                                                            ';
					result = result+'                          </div>                                                                                                                              ';
					result = result+'                        </div>                                                                                                                                ';
					result = result+'                        <div class="clearfix">                                                                                                                ';
					result = result+'                          <div class="col-md-4 text-right"> Hibernate </div>                                                                                  ';
					result = result+'                          <div class="col-md-8">                                                                                                              ';
					result = result+'                            <div class="progress">                                                                                                            ';
					result = result+'                              <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:50%" title="Expert"></div>';
					result = result+'                            </div>                                                                                                                            ';
					result = result+'                          </div>                                                                                                                              ';
					result = result+'                        </div>                                                                                                                                ';
					result = result+'                        <div class="clearfix">                                                                                                                ';
					result = result+'                          <div class="col-md-4 text-right"> MySQL </div>                                                                                      ';
					result = result+'                          <div class="col-md-8">                                                                                                              ';
					result = result+'                            <div class="progress">                                                                                                            ';
					result = result+'                              <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:40%" title="Expert"></div>';
					result = result+'                            </div>                                                                                                                            ';
					result = result+'                          </div>                                                                                                                              ';
					result = result+'                        </div>                                                                                                                                ';
					result = result+'                      <div class="clearfix">                                                                                                                  ';
					result = result+'                          <div class="col-md-4 text-right"> Java </div>                                                                                       ';
					result = result+'                          <div class="col-md-8">                                                                                                              ';
					result = result+'                            <div class="progress">                                                                                                            ';
					result = result+'                              <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:80%" title="Expert"></div>';
					result = result+'                            </div>                                                                                                                            ';
					result = result+'                          </div>                                                                                                                              ';
					result = result+'                        </div>                                                                                                                                ';
					result = result+'                        <div class="clearfix">                                                                                                                ';
					result = result+'                          <div class="col-md-4 text-right">J2EE Application</div>                                                                             ';
					result = result+'                          <div class="col-md-8">                                                                                                              ';
					result = result+'                            <div class="progress">                                                                                                            ';
					result = result+'                              <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width:60%" title="Expert"></div>';
					result = result+'                            </div>                                                                                                                            ';
					result = result+'                          </div>                                                                                                                              ';
					result = result+'                        </div>                                                                                                                                ';
					result = result+'                        <!-- Skills Progress View Ends -->                                                                                                    ';
					result = result+'                      </div>                                                                                                                                  ';
					result = result+'               </div>                                                                                                                                         ';
					
					//Certification Div
					result = result + '<div id="qvcertificationSubmission"></div> ';
					
					result = result+'              </div>                                                                                                                                          ';
					result = result+'          </div>                                                                                                                                              ';
					result = result+'           <!-- Skills, Achievements &amp; Certificates BLock -->                                                                                             ';
					result = result+'      </div>                                                                                                                                                  ';
					result = result+'      <!-- Work Experience and Skills -->                                                                                                                     ';
					result = result+'  </div>                                                                                                                                                      ';
// 						      <!-- resumeView -->
						
					$("#candidateQuickViewDiv").empty();
  				    $("#candidateQuickViewDiv").append(result); 
  				    
  				    qvemployeeSubmission(candidateId);
  				    qveducationSubmission(candidateId);
					qwikCertificates(candidateId);
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
               },
                 
            }); 
}
</script>

<script>
	function editSkill(candidateId){
		$("#skillName").prop('disabled', false).trigger("chosen:updated");
		$("#editSkillBtn").remove();
		$("#editSkillBtnSpan").hide();
		window.skillCountBefore = $("#skillName :selected").length;
		var result = '';
		result = result + '<button type="button" id="saveSkillBtn" name="saveSkillBtn" class="btn btn-primary btn-sm" onclick="saveSkill('+candidateId+');">Save Skill</button>';
		$("#saveSkillBtnSpan").empty();
		$("#saveSkillBtnSpan").append(result);
		$("#saveSkillBtnSpan").show();
		
		//Adding Customized Skills to the list
        var select, chosen;
	    select = $("#skillName");
	    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add New Skill:' });
	    chosen = select.data('chosen');
	    chosen.search_field.on('keyup', function(e)
	    {
	    	var newSkill = this.value.replace(",","");
	        if ((e.which == 13 || e.which == 188) && (newSkill != null  && newSkill != '' && newSkill != 'undefined'))
	        {
	        	var par = this.parent;
	            var option = $("<option>").val(newSkill).text(newSkill);
	            select.append(option);
	            select.find(option).prop('selected', true);
	          //to doesn't allow duplicates
                $("#skillName option").each(function(){
                      $(this).siblings("[value='"+ this.value+"']").remove();
                });
                //end
        		$("#skillName").trigger("chosen:updated");
        		
	        }
	    });
	    //Adding Customized Skills to the list
	}

	function saveSkill(candidateId){
		$("#skillName").prop('disabled', true).trigger("chosen:updated");
		window.skillCountAfter = $("#skillName :selected").length;
		if(skillCountBefore != skillCountAfter){
			saveSkils(candidateId);
		}
		$("#saveSkillBtn").remove();
		$("#saveSkillBtnSpan").hide();
		var result = '';
		result = result + '<button type="button" id="editSkillBtn" name="editSkillBtn" class="btn btn-primary btn-sm" onclick="editSkill('+candidateId+');">Edit Skill</button>';
		$("#editSkillBtnSpan").empty();
		$("#editSkillBtnSpan").append(result);
		$("#editSkillBtnSpan").show();
		skillCountAfter = skillCountBefore = 0;
	}
</script>


<%-- 
     @author : sarath kumar.
     @author : venkat kesav (modified on 02/18/2017)
     this script is for save Skils in Candidate.
     1.usage     :  used in editCandidate() function  line 13
     2.parameter : csrf token ,candidateId.
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
 function saveSkils(candidateId){
	 var skillName = $("#skillName").val();
	 var jsonData = encodeURIComponent(skillName);
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(skillName != null && skillName != '' && skillName !='undefined'){
		 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlySkillBycandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&skillName="+jsonData
	 }else{
		 Urlcheck = sourceleadUrl+"/saveOrUpdateOnlySkillBycandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&skillName="+""
	 }
	 
	 $.ajax({  
         type : "POST",
         url: Urlcheck,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != "" && data == "success"){
           	  //candidateProfile(candidateId);
              }
       },
         
    }); 
	 
	 
	 
/* 	 if(skillName != null && skillName != '' && skillName !='undefined'){
		 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({  
	          type : "POST",
	          url: sourceleadUrl+"/saveOrUpdateOnlySkillBycandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&skillName="+jsonData,        
	          success :function(data) {
	               if(data != null && data != 'undefined' && data != "" && data == "success"){
	            	  //candidateProfile(candidateId);
	               }
	        },
	          
	     }); 
	 }else{
		 
	 } */
	 
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
  
<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in editCandidate() function  line 78
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>         
<script type="text/javascript">
	function upDateCandidate(candidateId) {
		
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
		
		rateValidations();
		
		 if ($('#candidateProfileEdit_form').validate(false, validationSettings)){ 
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#candidateProfileEdit_form').serializeFormJSON();
			
			 var con = JSON.stringify(frm);
			 con = con.replace(/[{}]/g, "");
				
			  //It will escape all the special characters
			var jsonData = encodeURIComponent(con);
			
			var form = $('#candidateProfileEdit_form')[0];
      		var formData = new FormData(form); 
			
			$("#update_candidate").attr("disabled", "disabled");
			$("#update_candidate").addClass('button').val('Processing..');
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/updateCandidate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(result) {
							/* location.replace(sourceleadUrl+"/candidatesList");  */
							candidateSideBar(candidateId);
						},
					});
			return true;
		 }else{ 
				return false;
		 } 
	}
		 $('body').on('blur','#candidateProfileEdit_form',
					function() {
						$("#candidateProfileEdit_form").showHelpOnFocus();
						$("#candidateProfileEdit_form").validateOnBlur(false,validationSettings);
			});
	 
</script>

<script>
function cancelEdit(){
	$("#profileEditPopup").empty();
}
</script>



<%-- 
     @author : sarath Kumar.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidateProfile() function  line 258
     2.parameter : csrf token candidateId.
     3.ajax url implementation in CandidateListRestController.
--%>  
<script>
function candidateSkillsList(candidateId){  
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidatesSkillsBycandidateID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != "" && data != '[]'){
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
	                    	    result = result + '<tr>';
		                		result = result + '<td><p class="form-control-static">'+opt.name+'</p></td>';
		                		
		                		if(window.candidateStatusGlobal == '0'){
		                			result = result + '            <td><select id="scale" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);" >';
		                			if(opt.scale == 'undefined'|| opt.scale==null || opt.scale== 0){
			                			 result = result + '<option selected=""disabled="">select</option>'; 
				                	 }else{
		                			result = result + '<option selected="" value="'+opt.scale+'" disabled="">'+opt.scale+'</option>';
				                	 }
		                		}else{
		                		
		                		result = result + '<td><select id="scale" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);" >';
		                		result = result + '<option selected="" value="'+opt.scale+'" disabled="">Select</option>';
		                		
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
		                		}
		                		result = result + '              </select></td>';
		                		
		                		 if(window.candidateStatusGlobal == '0'){ 
		                			 result = result + '<td><select id="totalExperience" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);">';
				                	 if(opt.totalExperience == 'undefined'|| opt.totalExperience==null || opt.totalExperience== 0){
			                			 result = result + ' <option selected="">select</option>'; 
				                	 }else{
		                			 result = result + '<option selected="" value="'+opt.totalExperience+'" >'+opt.totalExperience+'</option>'; 
		                		 }
				                }else{
		                		result = result + '<td><select id="totalExperience" class="tbldata-dotdot-me width100percnt" onChange="fieldUpdateCandidate('+opt.id+',this);">';
		                		result = result + '<option selected="" >Select</option>';
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
		                		}
		                		result = result + '              </select></td>';
		                		
		                		 if(window.candidateStatusGlobal == '0'){ 
				                result = result + '            <td><div class="text-center"><a href="javascript:void(0)"> <i class="fa fa-trash"></i></a></div></td>';
		                		 }else{
		                		result = result + '            <td><div class="text-center"><a onclick="removeSkillById('+candidateId+', '+opt.id+');" href="javascript:void(0)"> <i class="fa fa-trash"></i></a></div></td>';
		                		 }
		                		result = result + '          </tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateSkillList").append(result); 
	                     
	                     $("#candidateSkills").trigger("chosen:updated");
	                     
	                     
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



<%-- 
     @author : Venkat Kesav
     this script is for Remove Candidate Skill.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token, candidateId, Skill Name
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function removeSkillById(candidateId, skillId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/removeSkillBySkillId?${_csrf.parameterName}=${_csrf.token}&skillId="+skillId,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != ""){
            			 candidateSkillsList(candidateId);
                }
                 },
		}); 
}
</script>
    
     
          
<%-- 
     @author : david sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in upDateCandidate() function  line 379
--%>         
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






<%-- 
     @author : sarath kumar.
     this script is for inline edit saving .
     1.usage     :  used in  skillList(); 
     2.parameter : csrf token ,name , value.
     3.ajax url implementation in CandidateListRestController.
--%>         
<script type="text/javascript">
	function fieldUpdateCandidate(candidateSkillId,local) {
			var sourceleadUrl = '${sourceleadUrl}'; 
			var value = local.value;
			var name = local.id;
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/fieldUpdateCandidate?${_csrf.parameterName}=${_csrf.token}&candidateSkillId="+candidateSkillId+"&name="+name+"&value="+value,
						success : function(result) {
							 
						},
			}); 	 	
	} 
</script>

           
           

<script>
	function qvemployeeSubmission(candidateId) {
		var sourceleadUrl = '${sourceleadUrl}';
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId=" + candidateId,
					success : function(data) {
						if (data != null && data != 'undefined' && data != "" && data != '[]') {
								$("#qvemployeeSubmission").empty();
								var result = "";
								var json = JSON.parse(data);
								result = result +'            <div class="col-md-12">';
								result = result +' 				<h4>Work Experience</h4>';

								for (var i = 0; i < json.length; i++) {
									var opt = json[i];
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
									
									if (opt.address != null && opt.address != '' && opt.address != 'undefined') {
										address=   opt.address;
									}else{
										address = " ";
									}
									result = result +'<div class="resume-widget">';
									result = result +'  <p><small>'+startMonth +''+startYear +' '+endMonth +''+endYear+'</small>';
									result = result +'  <small class="pull-right">'+address+'</small></p>';
									result = result +'  <h4>'+designation+'</h4>';
									result = result +'  <h5>'+name+'</h5>';
									result = result +'</div>';
								}
// 								result = result +'          </div>';
								result = result +'        </div>';
								$("#qvemployeeSubmission").append(result);
						}
					},

				});
	}
</script>

<script>
function qveducationSubmission(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateEducationListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
        	  if (data != null && data != 'undefined' && data != "" && data != '[]') {
	                	 $("#qveducationSubmission").empty();
	                	 var result = "";
	                	 
							result = result +'            <div class="col-md-12">';
							result = result +'              <h4>Education</h4>';
							
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
		                    	 
									result = result +'<div class="resume-widget">';
									result = result +'<p><small>'+EducationstartDateStr+' '+EducationendDateStr+'</small></p>';
									result = result +'<h5>'+EducationName+' '+specialization+'</h5>';
									result = result +'</div>';
		                     }
							
							result = result +'        </div>';
	                     $("#qveducationSubmission").append(result); 
        	  }
        },
          
     }); 
}
</script> 

<script>
function qwikCertificates(candidateId){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateCertificationListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
	                	 $("#qvcertificationSubmission").empty();
	                	 var result = "";
	                	 
	                	 result = result +'<div class="col-md-12">';
						 result = result +' <h4>Achievements &amp; Certificates</h4>';
							var json = JSON.parse(data);
							for (var i = 0; i < json.length; i++) { 
		                    	var opt = json[i];  
		                    	/* if(opt.score != null && opt.score != 'undefined' && opt.score != ""){
				            		score =' ( ' + opt.score;
				            	}else{
				            		score = " ";
				            	}
				            	if(opt.scoreType != null && opt.scoreType != 'undefined' && opt.scoreType != ""){
				            		scoreType = opt.scoreType +' ) ';
				            	}else{
				            		scoreType = " ";
				            	}
				            	alert(scoreType)
				            	
				            	if(opt.candidatevalidFromDateToString != null && opt.candidatevalidFromDateToString != 'undefined' && opt.candidatevalidFromDateToString != ""){
				            		candidatevalidFromDateToString = opt.candidatevalidFromDateToString;
				            	}else{
				            		candidatevalidFromDateToString = " ";
				            	}
				            	alert(candidatevalidFromDateToString)
				            	if(opt.candidatevalidToDateToString != null && opt.candidatevalidToDateToString != 'undefined' && opt.candidatevalidToDateToString != ""){
				            		candidatevalidToDateToString = "To "opt.candidatevalidToDateToString;
				            	}else{
				            		candidatevalidToDateToString = " ";
				            	}
				            	alert(opt.provider) */
								result = result +'                  <div class="resume-widget resumewidget">';
								result = result +'  <p><small>'+opt.candidatevalidFromDateToString+' - '+opt.candidatevalidToDateToString+'</p>';
								result = result +' <h4>'+opt.name+' ('+opt.score+''+opt.scoreType+') </h4>';
								result = result +'<div class="description">';
								result = result +'  <p>'+opt.provider+'</p>';
								result = result +'</div>';  
								result = result +'                  </div>';
							} 
							result = result +'        </div>';
							
							$("#qvcertificationSubmission").append(result);  
        },
          
     });
}
</script>
<script>
$('.selectpicker').selectpicker('refresh');
</script>    
     
<script>
 function disableMonths() {
	
         if ($("#experienceYears").val() == "25"){
             $("#experienceMonths").attr("disabled", true);
             $("#experienceMonths").val("disabled", true);
         }else{
        	 $("#experienceMonths").attr("disabled", false); 
		 }    
 }
</script>
  
<!-- Employment Type equals Other then Text Box will Open script here -->
<script>
function updateDesignationOtherType(typeVal){
	 var candidateType = $('#designationType1').val(); 
	
	if(candidateType == 'Other' &&  candidateType != '' && candidateType !='undefined'){
		  document.getElementById("designationTypeDiv").style.display="block";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationOtherType").attr("data-validation", "validate_Space validate_length length2-250");
	}else{
		   document.getElementById("designationTypeDiv").style.display="none";
		  $("#designationOtherType").attr("data-validation-optional", "true");
		  $("#designationTypeDiv").find('p.jquery_form_error_message').remove();
	} 
	 
}
</script>


<!-- Candidate Email validation script starts here -->

<script type="text/javascript">
 function validateCandidateEmail() {
	 var email = $('#email').val(); 
	 var candidateType = $('#candidateType').val(); 
	 $('#emailDiv').find('p.jquery_form_error_message').remove();
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(email != null && email != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateCandidateEmail?${_csrf.parameterName}=${_csrf.token}&email="+email+"&candidateType="+candidateType, 
		        success: function(data) {
		        	if(data == 'true'){
		        		
		        		  $('#candidateType').val(); 
						  $("input#email").after("<p class='jquery_form_error_message'>"+email+" is Already Existed</span>");
						  $("#update_candidate").attr("disabled", "disabled");
						  $("#update_candidate").addClass('button').val('Processing..');
		        	}
		        	else{
		        		
		        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#emailDiv').find('p.jquery_form_error_message').remove(); 
		        		 $("#update_candidate" ).addClass('button').val('Save');
                         $("#update_candidate").removeAttr("disabled");
		        	}
		        },
		    });
	 }
 }
 </script>     
 
 <!-- Candidate Email validation script ends here -->
 
 
<!-- This script for Current Rate ,Rate Type,Expected Rate, Expected Rate Type validation checks -->
<script type="text/javascript">

function rateValidations(){
	
var rateType = document.getElementById("expectedRateType").value; 
if(rateType != null && rateType != ""){
	 $('#expectedRate').removeAttr('data-validation-optional');
	 $('#expectedRate').attr("data-validation","required"); 
}else{
	 $('#expectedRate').removeAttr('data-validation');
	 $('#expectedRate').attr("data-validation-optional","true");
	 $('#expectedRateType').removeAttr('data-validation');
	 $('#expectedRateType').attr("data-validation-optional","true"); 
}

var expectedRate = document.getElementById("expectedRate").value; 
if(expectedRate != null && expectedRate != ""){
	 $('#expectedRateType').removeAttr('data-validation-optional');
	 $('#expectedRateType').attr("data-validation","required"); 
}else{
$('#expectedRateType').removeAttr('data-validation');
$('#expectedRateType').attr("data-validation-optional","true"); 
}

var currentRateType = document.getElementById("currentRateType").value; 
	if(currentRateType != null && currentRateType != ""){
		$('#currentRate').removeAttr('data-validation-optional');
		$('#currentRate').attr("data-validation","required"); 
	}else{
		$('#currentRate').removeAttr('data-validation');
	 	$('#currentRate').attr("data-validation-optional","true"); 
		$('#currentRateType').removeAttr('data-validation');
	 	$('#currentRateType').attr("data-validation-optional","true"); 
	}
	
var currentRate = document.getElementById("currentRate").value; 
	 if(currentRate != null && currentRate != ""){
		
		 $('#currentRateType').removeAttr('data-validation-optional');
		 $('#currentRateType').attr("data-validation","required"); 
	 }else{
	 $('#currentRateType').removeAttr('data-validation');
	 $('#currentRateType').attr("data-validation-optional","true"); 
	 }
}
</script>