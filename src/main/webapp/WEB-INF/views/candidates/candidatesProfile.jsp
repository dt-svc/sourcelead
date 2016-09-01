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
            			    var result = '';
							result = result + '		    <div class="edit-details col-md-8 edit-candaite"> <i class="fa fa-close close-div"></i>';
							result = result + '		      <div class="form-horizontal clearfix">';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';    
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="First Name">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="Last Name">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="Email">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="Mobile">';
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
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="City">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="Skills">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-12">';
							result = result + '		            <textarea class="form-control input-sm" placeholder="Summary" rows="2" cols="0">';
							result = result + '		                  </textarea>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mr">';
							result = result + '		          <div class="pull-right">';
							result = result + '		            <button type="submit" class="btn btn-sm btn-primary mr" onClick="saveCandidate();">Save</button>';
							result = result + '		            <button type="submit" class="btn btn-sm btn-danger">Cancel</button>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		      </div>';
							result = result + '		    </div>';
							result = result + '		  </div>';
							
							$("#profileEditPopup").empty(); 
							$("#profileEditPopup").append(result); 
							
							$(".edit-details").toggleClass("edit-details-active");
							 $(".collapse-div").removeClass("edit-details-active");
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
							result = result + '<div class="project_tabs_inn_head addUser-org"> <a href="javascript:void(0)" class="mr"><i class="glyphicon glyphicon-pencil"></i>Edit</a> <a href="javascript:void(0)"><i class="glyphicon glyphicon-trash"></i>Remove</a> ';
							
							result = result + '		    <div class="edit-details col-md-8 edit-candaite"> <i class="fa fa-close close-div"></i>';
							result = result + '		      <div class="form-horizontal clearfix">';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="First Name">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="Last Name">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="Email">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="Mobile">';
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
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="City">';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="text" class="form-control input-sm" value="" placeholder="Skills">';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-12">';
							result = result + '		            <textarea class="form-control input-sm" placeholder="Summary" rows="2" cols="0">';
							result = result + '		                  </textarea>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mr">';
							result = result + '		          <div class="pull-right">';
							result = result + '		            <button type="submit" class="btn btn-sm btn-primary mr">Save</button>';
							result = result + '		            <button type="submit" class="btn btn-sm btn-danger">Cancel</button>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		      </div>';
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
								
								$(".addSkills").click(function(){
									$(".add-skills").toggleClass('add-skills-active');
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
 function saveSkils(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var skillName = $("#skillName").val();
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
 }  
</script> 
<script>  
function removeSkillPopUp(candidateId){           
		$(".add-skills").removeClass('add-skills-active');
}
</script>             
            
<script>
function candidateSkillsList(candidateId){  
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidatesSkillsBycandidateID?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
               if(data != null && data != 'undefined' && data != ""){
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
            