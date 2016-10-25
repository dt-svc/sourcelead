<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 
 
<script>
function getCandidateProfile(applicantId){
	var sourceleadUrl = '${sourceleadUrl}';
	  var skillsList = '${skillsList}'; 
	  
	  $.ajax({
      type : "POST",
       url: sourceleadUrl+"/getCandidateById?${_csrf.parameterName}=${_csrf.token}&candidateId="+applicantId,        
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
							result = result +'		<h2>Skills:</h2>';
							
							result = result + '	<div class="row select-skills">';
							result = result + '    <div class="divskill col-md-8"> ';
							result = result + '      <div class="addSkills">';
							result = result + '        <div class="col-md-7 add-skills" style="display:none;"> <i class="fa fa-close close-div" onClick ="removeSkillPopUp('+applicantId+');"></i>';
							result = result + '          <div class="form-horizontal clearfix">';
							result = result + '            <div class="clearfix div-padding">';
							result = result + '              <div class="col-md-10">';
							
							result = result + '<select multiple class="chosen-select form-control" name="skillName" id="skillName" data-validation="validate_Space" data-validation-optional="true">';
			                <c:forEach var="skill" items="${skillsList}">
			              			  result = result + ' <option value="${skill.name}">${skill.name}</option>';
			                </c:forEach>
			                result = result + '</select>';
							/* result = result + '                <input type="text" id="skillName" placeholder="Skill..." value="" class="form-control input-sm">'; */
							result = result + '              </div>';
							result = result + '              <div class="col-md-2">';
							result = result + '                <button class="btn btn-sm btn-primary pull-right" type="submit" onClick="saveSkils('+applicantId+');" >Add</button>';   
							result = result + '              </div>';
							result = result + '            </div>';
							result = result + '          </div>';
							result = result + '        </div>';
							result = result + '      </div>';
							
							
							result = result + '<div id="candidateSkillList"></div> ';
							
							/* result = result + '      <a href="javascript:void(0)">more <i class="fa fa-angle-right"></i></a>'; */
							result = result + '    </div>';
							result = result + '  </div>';
							$("#tabb-1").empty();
							$("#tabb-2").empty(); 
							$("#tabb-2").append(result);  
							    
							candidateSkillsList(applicantId);
							$("#tabb-2").show();
							$(".project-tabs-menu a").click(function(event) {
							     event.preventDefault();
							     $(this).parent().addClass("current");
							     $(this).parent().siblings().removeClass("current");
							     var tab = $(this).attr("href");
							     $(".project_tabs_inn").not(tab).css("display", "none");
							     $(tab).fadeIn();
							 });
								
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
function candidateSkillsList(applicantId){  
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidatesSkillsBycandidateID?${_csrf.parameterName}=${_csrf.token}&candidateId="+applicantId,        
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
		                		result = result + '            <td><div class="text-center"><a onclick="removeSkillById('+applicantId+', '+opt.id+');" href="javascript:void(0)"> <i class="fa fa-trash"></i></a></div></td>';
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

