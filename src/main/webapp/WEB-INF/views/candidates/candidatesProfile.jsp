<script>
		$(document).ready(function() {
			cscInit();
		});
</script>

<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="resources/css/chosen.min.css">

<!-- these files for autocomplete with tagit -->

<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	  var loginCountryName = '${loginCountryName}';
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
							result = result + '		    <div class="edit-details col-md-8 edit-candaite"> <i class="fa fa-close close-div" onclick="cancelEdit();"></i>';
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
							
						    /* 	  
							result = result + '  <div class="col-md-9">';
							result = result + '    <input type="hidden" class="form-control" id="CountryAutocomplete" name="country"';
							result = result + '				name="country" field-name="Country" value="'+loginCountryName+'" data-validation="validate_Space" disabled="disabled" data-validation-optional="true" />'; 
							result = result + '	<ul id="country" class="form-control" onkeypress="getAllCountries('+sourceleadUrl+');"></ul>';
							result = result + '  </div>'; */
					        
					        
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6 custom-fields">';
								result = result + '    <input type="hidden" class="form-control" id="CountryAutocomplete" name="country"';
								result = result + '				name="country" field-name="Country" value="'+json.country+'" data-validation="validate_Space" disabled="disabled" data-validation-optional="true" />'; 
								result = result + '	<ul id="country" class="form-control" onkeypress="getAllCountries(\''+sourceleadUrl+'\');"></ul>';
							result = result + '		          </div>';
							result = result + '		          <div class="col-md-6 custom-fields select-arrow">';
								result = result + '	<input type="hidden" id="stateAutoComplete" name="state"';
								result = result + '	field-name="State" class="form-control" value="'+json.state+'" data-validation="validate_Space" data-validation-optional="true">';
								result = result + '	 <ul id="state"  onkeypress="getStates(\''+sourceleadUrl+'\');"></ul>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-6">';
							result = result + '		            <input type="hidden"  name="candidateId" value="'+candidateId+'" >';
							/* result = result + '		            <input type="text" class="form-control input-sm"  name="city" value="'+json.city+'" placeholder="City">'; */
								result = result + '	 <input type="hidden" id="cityAutoComplete" name="city" field-name="State" class="form-control" value="'+json.city+'" data-validation="validate_Space" data-validation-optional="true">';
								result = result + '	   <ul id="city"  onkeypress="getCities(\''+sourceleadUrl+'\');"></ul>';
							result = result + '		          </div>';
							result = result + '		        </div>';
							result = result + '		        <div class="clearfix mb10">';
							result = result + '		          <div class="col-md-12">';
							result = result + '		            <textarea class="form-control input-sm" placeholder="Summary"  name="summary" rows="2" cols="0">';
							result = result + '		                 '+json.summary+'</textarea>';
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
							
							
							 $('#country').tagit({
							       singleField: true,
							       singleFieldNode: $('#CountryAutocomplete')
							       ,tagLimit:1
							       ,placeholderText:""
						           ,afterTagRemoved: function(evt, ui){
						                   $('#multiState').tagit('removeAll');
						                   $('#multiCity').tagit('removeAll');
						                   $('#state').tagit('removeAll');
						                   $('#city').tagit('removeAll');
						                   return false;
						           }
							   });
							 
							  $('#state').tagit({
							       singleField: true,
							       singleFieldNode: $('#stateAutoComplete')
							       ,tagLimit:1
							       ,placeholderText:""
							       ,afterTagRemoved: function(evt, ui){
						                 $('#city').tagit('removeAll');
						                 return false;
						         }
							   });
							 
							  $('#city').tagit({
							       singleField: true,
							       singleFieldNode: $('#cityAutoComplete')
							       ,tagLimit:1
							       ,placeholderText:""
							  });
							 
							  
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

<!-- Country,State,City Script Starts Here -->

<script>
function getAllCountries(sourceleadUrl){
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: sourceleadUrl+"/countryAutoComplete?${_csrf.parameterName}=${_csrf.token}",
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#country').tagit({
        		  allowSpaces: false,
        		  availableTags: json
	       	}); 
        },
        error: function(e){
        }
    }); 
}
</script> 

<script>
function getStates(sourceleadUrl){
	var countryName = document.getElementById("CountryAutocomplete").value;
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').tagit({
	        		allowSpaces: false,
	        		availableTags: json
	        		
		       	 	});
	        },
	        error: function(e){
	        }
	    });
	}
}
</script> 


<script>
function getCities(sourceleadUrl){
	var countryName = document.getElementById("CountryAutocomplete").value;
	var stateName = document.getElementById("stateAutoComplete").value;
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: sourceleadUrl+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').tagit({
	        		allowSpaces: false,
	        		availableTags: json
	        		
		       	 	});
	        },
	        error: function(e){
	        }
	    });
	}
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


<%-- 
     @author : Venkat Kesav
     this script is for Delete Candidate alert
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function cancelAlert(){ 
	$("#removeCandidateAlert").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>



<%-- 
     @author : Venkat Kesav
     this script is for close alert popup.
     1.usage     : used in saveOrCancel() / alertMessage removeAlertMessage()
--%>
<script type="text/javascript">
function removeCandidateAlertMessage(candidateId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	    <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<textarea id='rejectReason' class='form-control' field-name='Reject Reason' data-validation-optional='true' data-validation='validate_Space validate_Description validate_length length2-250' name='rejectReason' value='' placeholder='Terminate Reason' rows='2'></textarea>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteCandidate("+candidateId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelAlert();'></b>";
	result = result +"	</div>";
	$("#removeCandidateAlert").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>



<%-- 
     @author :Sammeta David Raju
    
     1.usage     : used in reActivateCandidateAlertMessage() / candidateProfile Line number 388
     2.parameters : candidateId
--%>

<script type="text/javascript">
function reActivateCandidateAlertMessage(candidateId){
	var result = "";
	var sourceleadUrl ='${sourceleadUrl}';
	var message = confirm("You are choosing to ReActivate the Candidate");
	if (message == true) {
		var reactivate = '1';
		$.ajax({
	        type : "POST",
	         url: sourceleadUrl+"/reActivateCandidateByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&reActivate="+reactivate,        
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
							if(json.status == 0){
								result = result + '<div class="project_tabs_inn_head"><a href="#" class="positionRelative showInfo"><i class="glyphicon glyphicon-info-sign"></i>Terminated<span class="infoContent col-xs-12 col-md-5"> '+json.terminateReason+' </span></a><a href="javascript:void(0)" onClick="reActivateCandidateAlertMessage('+candidateId+');"><i class="glyphicon glyphicon-trash"></i>ReActivate</a> ';
							}else{
								result = result + '<div class="project_tabs_inn_head "> <a href="#" onClick="editCandidate('+candidateId+');" class="mr addUser-org"><i class="glyphicon glyphicon-pencil"></i>Edit</a> <a href="javascript:void(0)" onClick="removeCandidateAlertMessage('+candidateId+');"><i class="glyphicon glyphicon-trash"></i>Remove</a> ';

							}
							result = result + '<div id="profileEditPopup"></div>';
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
							
							result = result + '<select multiple class="chosen-select form-control" name="skillName" id="skillName" data-validation="validate_Space" data-validation-optional="true">';
			                <c:forEach var="skill" items="${skillsList}">
			              			  result = result + ' <option value="${skill.name}">${skill.name}</option>';
			                </c:forEach>
			                result = result + '</select>';
							/* result = result + '                <input type="text" id="skillName" placeholder="Skill..." value="" class="form-control input-sm">'; */
							result = result + '              </div>';
							result = result + '              <div class="col-md-2">';
							result = result + '                <button class="btn btn-sm btn-primary pull-right" type="submit" onClick="saveSkils('+candidateId+');" >Add</button>';   
							result = result + '              </div>';
							result = result + '            </div>';
							result = result + '          </div>';
							result = result + '        </div>';
							result = result + '      </div>';
							
							
							result = result + '<div id="candidateSkillList"></div> ';
							
							/* result = result + '      <a href="javascript:void(0)">more <i class="fa fa-angle-right"></i></a>'; */
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
            		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
            		 else if (data.indexOf('exception') >= 0) {
            			 location.replace(sourceleadUrl+"/errorPage");
            		 }
            		 else{
            			 candidateSkillsList(candidateId);
            		 }
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


<!-- Eductions and Skills Autocomplete Scripts Starts Here -->
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
				})
				
</script> 
<!-- Eductions and Skills Autocomplete Scripts End Here -->




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

           
           
  