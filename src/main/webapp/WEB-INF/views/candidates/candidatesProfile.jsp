<%-- 
     @author : David Raju.
     this script is for edit Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<style type="text/css">
.require_wrap_details{
text-align:left;
}
</style>
<script>
function editCandidate(candidateId){
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
							var result ='';
							result = result + '		    <div class="edit-details col-md-8 edit-candaite"> <i class="fa fa-close close-div"></i>';
							result  = result + '         <form id="candidateProfileEdit_form" name="candidateProfileEdit_form" method="post">';
							result = result + '		      <div class="form-horizontal clearfix">';
							
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="firstName" value="'+json.firstName+'" placeholder="First Name">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="lastName" value="'+json.lastName+'" placeholder="Last Name">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="email" value="'+json.email+'" placeholder="Email">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="mobilePhone"  value="'+json.mobilePhone+'" placeholder="Mobile">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6 custom-fields">';
							result = result + '		            <select class="form-control select-arrow select-bg">';
							result = result + '		              <option>India</option>';
							result = result + '		              <option>United States</option>';
							result = result + '		              <option>UK</option>';
							result = result + '		            </select>';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6 custom-fields select-arrow">';
							result = result + '		            <select class="form-control input-sm">';
							result = result + '		              <option>India</option>';
							result = result + '		              <option>United States</option>';
							result = result + '		              <option>UK</option>';
							result = result + '		            </select>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="hidden"  name="candidateId" value="'+candidateId+'" >';
							result = result + '		            <input type="text" class="form-control input-sm"  name="city" value="'+json.city+'" placeholder="City">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-12">';
							result = result + '		            <textarea class="form-control input-sm" placeholder="Summary"  name="summary" value="'+json.summary+'" rows="2" cols="0">';
							result = result + '		                  </textarea>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mr">';
							result = result + '		          <div class="pull-right">';
							result = result + '		            <button type="button"  onClick ="upDateCandidate('+candidateId+');" class="btn btn-sm btn-primary mr">Save</button>';
							result = result + '		            <button type="button" class="btn btn-sm btn-danger" onClick ="cancelEdit();">Cancel</button>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		      </div>';
							result  = result + '              </form>';
							result = result + '		    </div>';
							result = result + '		  </div>';
							
							
							$("#profileEditPopup").empty(); 
							$("#profileEditPopup").append(result); 
							
							$(".edit-details").toggleClass("edit-details-active");
							$(".collapse-div").removeClass("edit-details-active");
            		 }
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
        					$("#profileEditPopup").empty();
        				    $("#profileEditPopup").append(result); 
                        }
                 },
                   
              }); 
}
</script> 


<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate.
     1.usage     :  used in candidateProfile.
     2.parameter : csrf token, candidateId
     3.ajax url implementation in CandidateListRestController.
--%>

<script>
function deleteCandidate(candidateId){
	  var sourceleadUrl ='${sourceleadUrl}';
	  var reason = document.getElementById("rejectReason").value;
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+"/deleteCandidateByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&reason="+reason,        
         success :function(data) {
              if(data != null && data != 'undefined' && data != ""){
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
            			 location.replace(sourceleadUrl+"/candidatesList");
            		 }
                }
                 },
              }); 
}
</script>




<script type="text/javascript">
function removeAlertMessage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteCandidate("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
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
function candidateProfile(candidateId){
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
							var result ="";
							
							result = result+'<div id="popUp" ></div>  ';
							
							result = result + '<div class="project_tabs_inn_head "> <a href="#" onClick="editCandidate('+candidateId+');" class="mr addUser-org"><i class="glyphicon glyphicon-pencil"></i>Edit</a> <a href="javascript:void(0)" onClick="removeAlertMessage('+candidateId+');"><i class="glyphicon glyphicon-trash"></i>Remove</a> ';
							
							result = result + '<div id="profileEditPopup"></div>';
							/* result = result + '		    <div class="edit-details col-md-8 edit-candaite"> <i class="fa fa-close close-div"></i>';
							result  = result + '         <form id="candidateProfileEdit_form" name="candidateProfileEdit_form" method="post">';
							result = result + '		      <div class="form-horizontal clearfix">';
							
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="firstName" value="'+json.firstName+'" placeholder="First Name">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="lastName" value="'+json.lastName+'" placeholder="Last Name">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="email" value="'+json.email+'" placeholder="Email">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" name="mobilePhone"  value="'+json.mobilePhone+'" placeholder="Mobile">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6 custom-fields">';
							result = result + '		            <select class="form-control select-arrow select-bg">';
							result = result + '		              <option>India</option>';
							result = result + '		              <option>United States</option>';
							result = result + '		              <option>UK</option>';
							result = result + '		            </select>';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6 custom-fields select-arrow">';
							result = result + '		            <select class="form-control input-sm">';
							result = result + '		              <option>India</option>';
							result = result + '		              <option>United States</option>';
							result = result + '		              <option>UK</option>';
							result = result + '		            </select>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="hidden"  name="candidateId" value="'+candidateId+'" >';
							result = result + '		            <input type="text" class="form-control input-sm"  name="city" value="'+json.city+'" placeholder="City">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-12">';
							result = result + '		            <textarea class="form-control input-sm" placeholder="Summary"  name="summary" value="'+json.summary+'" rows="2" cols="0">';
							result = result + '		                  </textarea>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mr">';
							result = result + '		          <div class="pull-right">';
							result = result + '		            <button type="submit"  onClick ="upDateCandidate();" class="btn btn-sm btn-primary mr">Save</button>';
							result = result + '		            <button type="submit" class="btn btn-sm btn-danger">Cancel</button>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		      </div>';
							result  = result + '              </form>';
							result = result + '		    </div>';
							result = result + '		  </div>';
							
						    result = result +'<div class="require_wrap_details">';	 
							result = result +'		<h1>Summary:</h1>';
							result = result +'	    <textarea placeholder="Enter description">'+json.summary+'</textarea>';
							result = result +'		<h2>Skills:</h2>';
							
							result = result + '	<div class="row select-skills">';
							result = result + '    <div class="divskill col-md-8"> ';
							result = result + '      <div class="addSkills"> <a href="#" class="btn-addskills" title="Add Skills"> <i class="fa fa-code"></i>Add Skills </a>';
							result = result + '        <div class="col-md-7 add-skills"> <i class="fa fa-close close-div"></i>';
							result = result + '          <div class="form-horizontal clearfix">';
							result = result + '            <div class="clearfix div-padding">';
							result = result + '              <div class="col-md-10">';
							result = result + '                <input type="text" id="skillName" placeholder="Skill..." value="" class="form-control input-sm">';
							result = result + '              </div>';
							result = result + '              <div class="col-md-2">';
							result = result + '                <button class="btn btn-sm btn-primary pull-right" type="submit" onClick="saveSkils('+candidateId+');" >Add</button>';
							result = result + '              </div>';
							result = result + '            </div>';
							result = result + '          </div>';
							result = result + '        </div>';
							result = result + '      </div>'; */
							
							 result = result +'<div class="require_wrap_details">';	 
							result = result +'		<h1>Summary:</h1>';
							result = result +'	    <textarea placeholder="Enter description">'+json.summary+'</textarea>';
							result = result +'		<h2>Skills:</h2>';
							
							result = result + '	<div class="row select-skills">';
							result = result + '    <div class="divskill col-md-8"> ';
							result = result + '      <div class="addSkills"> <a href="#" class="btn-addskills" title="Add Skills"> <i class="fa fa-code"></i>Add Skills </a>';
							result = result + '        <div class="col-md-7 add-skills" style="display:none;"> <i class="fa fa-close close-div" onClick ="removeSkillPopUp('+candidateId+');"></i>';
							result = result + '          <div class="form-horizontal clearfix">';
							result = result + '            <div class="clearfix div-padding">';
							result = result + '              <div class="col-md-10">';
							result = result + '                <input type="text" id="skillName" placeholder="Skill..." value="" class="form-control input-sm">';
							result = result + '              </div>';
							result = result + '              <div class="col-md-2">';
							result = result + '                <button class="btn btn-sm btn-primary pull-right" type="submit" onClick="saveSkils('+candidateId+');" >Add</button>';   
							result = result + '              </div>';
							result = result + '            </div>';
							result = result + '          </div>';
							result = result + '        </div>';
							result = result + '      </div>';
							
							
							result = result + '<div id="candidateSkillList"></div> ';
							
							result = result + '      <a href="javascript:void(0)">more <i class="fa fa-angle-right"></i></a>';
							result = result + '    </div>';
							result = result + '  </div>';
							$("#tab-1").empty();
							$("#tab-2").empty(); 
							$("#tab-3").empty();  
							$("#tab-4").empty();  
							$("#tab-5").empty();  
							$("#tab-6").empty();  
							$("#tab-1").append(result);  
							    
							candidateSkillsList(candidateId);
							
							$("#tab-1").show();
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


<%-- 
     @author : sarath kumar.
     this script is for save Skils in Candidate.
     1.usage     :  used in editCandidate() function  line 13
     2.parameter : csrf token ,candidateId.
     3.ajax url implementation in CandidateListRestController.
--%>
<script>  
 function saveSkils(candidateId){
	 var skillName = $("#skillName").val();
	 if(skillName != null && skillName != '' && skillName !='undefined'){
		 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({  
	          type : "POST",
	          url: sourceleadUrl+"/saveSkilsBycandidateID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&skillName="+skillName,        
	          success :function(data) {
	               if(data != null && data != 'undefined' && data != ""){
	            	   candidateSkillsList(candidateId);
	            	   removeSkillPopUp(candidateId);
	               }
	        },
	          
	     }); 
	 }else{
		 alert("Enter Skill");
	 }
	 
 }  
</script>
 
 
 <%-- 
     @author : sarath kumar.
     this script is for remove Skill PopUp.
     1.usage     :  used in saveSkils() function line number 339
     2.parameter : candidateId  .
--%>
<script>  
function removeSkillPopUp(candidateId){           
		/* $(".add-skills").removeClass('add-skills-active'); */
		$(".add-skills").css({"display":"none","opacity":"0"});
}
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
		 if ($('#candidateProfileEdit_form').validate(false, validationSettings)){ 
			
			var frm = $('#candidateProfileEdit_form').serializeFormJSON();
			
			 var con = JSON.stringify(frm);
				 con = con.replace(/[{}]/g, "");
				var jsonData = encodeURIComponent(con);
			  //It will escape all the special characters
			var sourceleadUrl = '${sourceleadUrl}';
			$.ajax({
						type : "POST",
						url : sourceleadUrl+"/updateCandidate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success : function(result) {
							/* location.replace(sourceleadUrl+"/candidatesList");  */
							clientSideBar(candidateId);
						},
					});
			return true;
		 }else{ 
				return false;
		 } 
		 $('body').on('blur','#candidateProfileEdit_form',
					function() {
						$("#candidateProfileEdit_form").showHelpOnFocus();
						$("#candidateProfileEdit_form").validateOnBlur(false,validationSettings);
					});
	} 
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
		                		result = result + '            <td><select class="tbldata-dotdot-me width100percnt">';
		                		result = result + '                <option selected="" disabled="">Select</option>';
		                		result = result + '                <option>Beginner</option>';
		                		result = result + '                <option>Familiar</option>';
		                		result = result + '                <option>proficient</option>';
		                		result = result + '                <option>Expert</option>';
		                		result = result + '                <option>Master</option>';
		                		result = result + '              </select></td>';
		                		result = result + '            <td><select class="tbldata-dotdot-me width100percnt">';
		                		result = result + '                <option selected="" disabled="">Select</option>';
		                		result = result + '                <option>0</option>';
		                		result = result + '                <option>1</option>';
		                		result = result + '                <option>2</option>';
		                		result = result + '                <option>3</option>';
		                		result = result + '                <option>4</option>';
		                		result = result + '                <option>5</option>';
		                		result = result + '                <option>6</option>';
		                		result = result + '              </select></td>';
		                		result = result + '            <td><div class="text-center"> <i class="fa fa-trash"></i> </div></td>';
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
           
           
            