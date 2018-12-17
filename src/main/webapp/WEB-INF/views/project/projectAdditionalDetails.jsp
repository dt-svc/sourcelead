
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

  <script type="text/javascript">
		window.onload = function () {
			initLocationAuto('projectLocation','','','','','','');
		 };
	</script>

<div id="tab-c" class="newPrTabs_inn">
	
	<form:form id="project_additional_form" name="project_additional_form" method="post" >
	<section class="projQueWrap">
	<div id="additionalDetailsLoading"></div>
		<div class="projQueCta">
			<h2>1. Project location <span class="text-danger">*</span></h2>
			<div class="projQueCtaInn wid50">
				<input type="text" name="projectLocation" id="projectLocation" data-validation="required" field-name="Project Location" class="form-control" />
			</div>
		</div>
		<div class="projQueCta">
			<h2>2. Industry</h2>
			<div class="projQueCtaInn wid50">
			  <select class="form-control chosen-select" name="industry" id="industry" onchange="getProjectIndustry()">
				<option value="">--Select Industry--</option>
				<c:forEach var="industry" items="${industriesList}">
	                     <option value="${industry.name}">${industry.name}</option>
                </c:forEach>
				<option value='Others'>Others</option>
			  </select>
			</div>
		</div>
		
		<div class="projQueCta" id="otherProjectIndustry" style="display: none;">
			<div class="projQueCtaInn wid50">
				<input type="text" placeholder="Other Industry" field-name="Other Industry" data-validation-optional="true" name="otherIndustry" id="otherIndustry" class="form-control" />
			</div>
		</div>	
				
		<div class="projQueCta">
			<h2>3. What is the scope of this project</h2>
			<div class="projQueCtaInn">
				<div class="proRadio" id="projectScopes">
					<input id="radio1" type="radio" name="scope" id="scope" value="One time project" ><label for="radio1" class="Radi">One time project</label>
					<input id="radio2" type="radio" name="scope" id="scope" value="On going"><label for="radio2" class="Radi">On-going</label>
					<input id="radio3" type="radio" name="scope" id="scope" value="POC"><label for="radio3" class="Radi">POC</label>
					<input id="radio4" type="radio" name="scope" id="scope" value="Client Driven"><label for="radio4" class="Radi">Client Driven</label>
					<input id="radio5" type="radio" name="scope" id="scope" value="Others"><label for="radio5" class="Radi">Others</label>
					<div class="clearfix"></div>					
				</div>
			</div>
		</div>
		
		<div class="projQueCta" id="otherProjectScope" style="display: none;">
			<div class="projQueCtaInn wid50">
				<input type="text" placeholder="Other Scope" field-name="Other Scope" data-validation-optional="true" name="otherScope" id="otherScope" class="form-control" />
			</div>
		</div>
		
		<div class="projQueCta">
			<h2>4. Allowed employment category</h2>
			<div class="projQueCtaInn">
				<div class="proCheck">
					<input id="check1" type="checkbox" name="category" id="onsite" value="On site"><label for="check1" class="Chec">On-site</label>
					<input id="check2" type="checkbox" name="category" id="offsite" value="Off site"><label for="check2" class="Chec">Off-site</label>
					<input id="check3" type="checkbox" name="category" id="offshore" value="Off shore"><label for="check3" class="Chec">Off-shore </label>
					<input id="check4" type="checkbox" name="category" id="nearshore" value="Near shore"><label for="check4" class="Chec">Nearshore</label>
					<input id="check5" type="checkbox" name="category" id="hybird" value="hybrid"><label for="check5" class="Chec">Hybrid</label>
					<div class="clearfix"></div>					
				</div>
			</div>
		</div>		
		<div class="projQueCta">
			<h2>5. Team Size</h2>
			<div class="projQueCtaInn wid50">
				<input type="text" id="teamSize" name="teamSize" class="form-control" data-validation-optional="true" data-validation="validate_int validate_length length1-8" field-name="Team Size" />
			</div>
		</div>	
		<div class="projQueCta">
			<h2>6. Front end technologies</h2>
			<div class="projQueCtaInn wid50">
			  <select multiple="" class="chosen-select form-control" id="frontEnd" name="frontEnd">
				<c:forEach var="skill" items="${skillsList}">
	                     <option value="${skill.name}">${skill.name}</option>
                </c:forEach>
			  </select>
			</div>
		</div>
		<div class="projQueCta">
			<h2>7. Back end technologies</h2>
			<div class="projQueCtaInn wid50">
			  <select multiple="" class="chosen-select form-control" id="backEnd" name="backEnd">
				<c:forEach var="skill" items="${skillsList}">
	                     <option value="${skill.name}">${skill.name}</option>
                </c:forEach>
			  </select>
			</div>
		</div>	
		<div class="projQueCta">
			<h2>8. Database</h2>
			<div class="projQueCtaInn wid50">
			  <select multiple="" class="chosen-select form-control" id="projectDatabase" name="projectDatabase">
				<c:forEach var="skill" items="${skillsList}">
	                     <option value="${skill.name}">${skill.name}</option>
                </c:forEach>
			  </select>
			</div>
		</div>
		<div class="projQueCta">
			<h2>9. Other technologies</h2>
			<div class="projQueCtaInn wid50">
			  <select multiple="" class="chosen-select form-control" id="otherTechnologies" name="otherTechnologies">
				<c:forEach var="skill" items="${skillsList}">
	                     <option value="${skill.name}">${skill.name}</option>
                </c:forEach>
			  </select>
			</div>
		</div>
		<div class="projQueCta">
			<h2>10. Test management</h2>
			<div class="projQueCtaInn wid50">
			  <select multiple="" class="chosen-select form-control" id="testManagement" name="testManagement">
				<c:forEach var="testMgmt" items="${testManagementList}">
	                     <option value="${testMgmt.name}">${testMgmt.name}</option>
                </c:forEach>
			  </select>
			</div>
		</div>
		<div class="projQueCta">
			<h2>11. Project execution model(SDLC)</h2>
			<div class="projQueCtaInn" id="projectExecuitionModel">
				<div class="proRadio">
					<input id="radio11" type="radio" name="executionModel" id="executionModel" value="Agile"><label for="radio11" class="Radi">Agile</label>
					<input id="radio22" type="radio" name="executionModel" id="executionModel" value="Waterfall"><label for="radio22" class="Radi">Waterfall</label>
					<input id="radio33" type="radio" name="executionModel" id="executionModel" value="V model"><label for="radio33" class="Radi">V model</label>
					<div class="clearfix"></div>					
				</div>
			</div>
		</div>
		<div class="projQueCta" style="display: none;" id="agileProjectMethodologies">
			<h2>Agile Methodologies</h2>
			<div class="projQueCtaInn wid50">
			  <select multiple="" class="chosen-select form-control" id="agileMethodologies" name="agileMethodologies">
				<c:forEach var="agileMethods" items="${agileMethodologiesList}">
	                     <option value="${agileMethods.name}">${agileMethods.name}</option>
                </c:forEach>
			  </select>
			</div>
		</div>																		
	</section>

<div class="form-footer clearfix miniFoot">
  <div class="col-lg-12">
	  <span class="pull-left">
	  	<input type="checkbox"  onclick="checkMailToTeamMembers();" name="mailToTeamMembers" id="mailToTeamMembers" value="Y" checked>&nbsp; Mail to team members
	    &nbsp;&nbsp;&nbsp;<input type="checkbox"  onclick="checkMailToManagers();" name="mailToManagers" id="mailToManagers" value="Y" checked>&nbsp; Mail to managers
	  </span>
	  
	<div class="pull-right">
	  <input type="button" value="Cancel"  onClick="javascript:window.location.href = '<c:url value='${appPath}/projectDashboard'/>'" class="btn btn-danger">
<!-- 	  <input type="button" value="Save" id="save_project" onclick="saveAdditionalDetails('Save');"  class="btn btn-primary ml10"> -->
	  <input type="button" value="Save & Finish" id="save_Finish_project" onclick="saveAdditionalDetails('Finish');" class="btn btn-success ml10">
	</div>
  </div>
</div>
</form:form>
</div> 

<script>
function checkMailToTeamMembers(){
	var checked = document.getElementById("mailToTeamMembers").checked
	if(checked == true){
		$("#mailToTeamMembers").val("Y");
	}else{
		$("#mailToTeamMembers").val("N");
	}
}
</script>


<script>
function checkMailToManagers(){
	var checked = document.getElementById("mailToManagers").checked
	if(checked == true){
		$("#mailToManagers").val("Y");
	}else{
		$("#mailToManagers").val("N");
	}
}
</script>
<script type="text/javascript">
function getProjectIndustry(){
	var industry = document.getElementById("industry").value;
	if(industry == 'Others'){
		document.getElementById("otherProjectIndustry").style.display="";
		$("#otherIndustry").removeAttr("data-validation-optional");
		$("#otherIndustry").attr("data-validation", "required validate_AlphaNumber validate_Space validate_length length1-100");
	}else{
		document.getElementById("otherProjectIndustry").style.display="none";
		$("#otherIndustry").val('');
		$("#otherIndustry").removeAttr("data-validation-optional");
		$("#otherIndustry").attr("data-validation-optional", "true");
	}
}
</script>

<script>
$('#projectScopes input').on('change', function() {
	   var projectScope = $('input[name="scope"]:checked', '#projectScopes').val();
	   if(projectScope == 'Others'){
		   document.getElementById("otherProjectScope").style.display="";
			$("#otherScope").removeAttr("data-validation-optional");
			$("#otherScope").attr("data-validation", "required validate_AlphaNumber validate_Space validate_length length1-100");
		   
	   }else{
		   document.getElementById("otherProjectScope").style.display="none";
			$("#otherScope").val('');
			$("#otherScope").removeAttr("data-validation-optional");
			$("#otherScope").attr("data-validation-optional", "true");
		}
	});
</script>

<script>
$('#projectExecuitionModel input').on('change', function() {
	 var executionModel = $('input[name="executionModel"]:checked', '#projectExecuitionModel').val();
	 if(executionModel == 'Agile'){
		 document.getElementById("agileProjectMethodologies").style.display="";
	 }else{
		 document.getElementById("agileProjectMethodologies").style.display="none";
		 $("#agileMethodologies").val('');
		 $('#agileMethodologies').trigger("chosen:updated");
	 }
});
</script>


<script>
function getAdditionalDetails(){
	var projectId = document.getElementById("projectId").value;
	if(projectId != null && projectId != 'unedfined' && projectId != ''){
		$("#additionalDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl ='${sourceleadUrl}';
			 $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getProjectQuestionnaireDetails?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId, 
			        success: function(json) {
			        	if(json != null){
			        		if(json.projectLocation != null && json.projectLocation != 'undefined'){
			        			 $("input[name=projectLocation]").val(json.projectLocation);
			        		}
			        		
			        		if(json.industry != null && json.industry != 'undefined'){
			        			 $("select[name=industry]").val(json.industry);
			        			 $("#industry").trigger("chosen:updated");
			        		}
			        		
			        		if(json.industryOtherType != null && json.industryOtherType != 'undefined'){
			        			 document.getElementById("otherProjectIndustry").style.display="";
			        			 $("input[name=otherIndustry]").val(json.industryOtherType);
			        		}
			        		
			        		
			        		if(json.projectScope != null && json.projectScope != 'undefined'){
			        			$(":radio[value='"+json.projectScope+"']").attr('checked',true);
			        		}
			        		
			        		if(json.projectOtherScope != null && json.projectOtherScope != 'undefined'){
			        			 document.getElementById("otherProjectScope").style.display="";
			        			 $("input[name=otherScope]").val(json.projectOtherScope);
			        		}
			        		
			        		if(json.employmentCategory != null && json.employmentCategory != 'undefined'){
			        			var category = json.employmentCategory.split(',');
			        			for (var i=0; i<category.length; i++){
			        				$(":checkbox[value='"+category[i]+"']").prop("checked","true");
			        			}
			        		}
			        		
			        		if(json.teamSize != null && json.teamSize != 'undefined'){
			        			$("input[name=teamSize]").val(json.teamSize);
			        		}
			        		
			        		
			        		if(json.frontEnd != null && json.frontEnd != 'undefined'){
			        			$("#frontEnd").empty(); 
			        			
			        			var internalOutput = "";
			        			 
			        			var frontEndTech = json.frontEnd;
			        			<c:forEach var="skill" items="${skillsList}">
			        			var existed = 0;
			        			if(frontEndTech != null && frontEndTech != 'undefined' && frontEndTech != ''){
				        			var str_array = frontEndTech.split(',');
				        			for(var i = 0; i < str_array.length; i++) {
					        			str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
					        			if(str_array[i] == "${skill.name}"){
						        			existed = 1;
						        			internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
					        			}
				        			}
			        			}
			        			if(existed == 0){
			        			internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
			        			}
			        			</c:forEach>
	    	                     
	     							
	     						$("#frontEnd").append(internalOutput);
	     		        		$("#frontEnd").trigger("chosen:updated");
			        		}
			        		
			        		if(json.backEnd != null && json.backEnd != 'undefined'){
			        			$("#backEnd").empty(); 
			        			
			        			var internalOutput = "";
			        			 
			        			var backEndTech = json.backEnd;
			        			<c:forEach var="skill" items="${skillsList}">
			        			var existed = 0;
			        			if(backEndTech != null && backEndTech != 'undefined' && backEndTech != ''){
				        			var str_array = backEndTech.split(',');
				        			for(var i = 0; i < str_array.length; i++) {
					        			str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
					        			if(str_array[i] == "${skill.name}"){
						        			existed = 1;
						        			internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
					        			}
				        			}
			        			}
			        			if(existed == 0){
			        			internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
			        			}
			        			</c:forEach>
	    	                     
	     							
	     						$("#backEnd").append(internalOutput);
	     		        		$("#backEnd").trigger("chosen:updated");
			        		}
			        		
			        
			        		if(json.dataBase != null && json.dataBase != 'undefined'){
			        			$("#projectDatabase").empty(); 
			        			
			        			var internalOutput = "";
			        			 
			        			var dataBase = json.dataBase;
			        			<c:forEach var="skill" items="${skillsList}">
			        			var existed = 0;
			        			if(dataBase != null && dataBase != 'undefined' && dataBase != ''){
				        			var str_array = dataBase.split(',');
				        			for(var i = 0; i < str_array.length; i++) {
					        			str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
					        			if(str_array[i] == "${skill.name}"){
						        			existed = 1;
						        			internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
					        			}
				        			}
			        			}
			        			if(existed == 0){
			        			internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
			        			}
			        			</c:forEach>
	    	                     
	     							
	     						$("#projectDatabase").append(internalOutput);
	     		        		$("#projectDatabase").trigger("chosen:updated");
			        		}
			        		
			        		if(json.otherTechnologies != null && json.otherTechnologies != 'undefined'){
			        			$("#otherTechnologies").empty(); 
			        			
			        			var internalOutput = "";
			        			 
			        			var otherTech = json.otherTechnologies;
			        			<c:forEach var="skill" items="${skillsList}">
			        			var existed = 0;
			        			if(otherTech != null && otherTech != 'undefined' && otherTech != ''){
				        			var str_array = otherTech.split(',');
				        			for(var i = 0; i < str_array.length; i++) {
					        			str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
					        			if(str_array[i] == "${skill.name}"){
						        			existed = 1;
						        			internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
					        			}
				        			}
			        			}
			        			if(existed == 0){
			        			internalOutput = internalOutput+"<option value='${skill.name}'>${skill.name}</option>";
			        			}
			        			</c:forEach>
	    	                     
	     							
	     						$("#otherTechnologies").append(internalOutput);
	     		        		$("#otherTechnologies").trigger("chosen:updated");
			        		}
			        		
			        		if(json.testManagement != null && json.testManagement != 'undefined'){
			        			$("#testManagement").empty(); 
			        			
			        			var internalOutput = "";
			        			 
			        			var testManagement = json.testManagement;
			        			<c:forEach var="testMgmt" items="${testManagementList}">
			        			var existed = 0;
			        			if(testManagement != null && testManagement != 'undefined' && testManagement != ''){
				        			var str_array = testManagement.split(',');
				        			for(var i = 0; i < str_array.length; i++) {
					        			str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
					        			if(str_array[i] == "${testMgmt.name}"){
						        			existed = 1;
						        			internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
					        			}
				        			}
			        			}
			        			if(existed == 0){
			        			internalOutput = internalOutput+"<option value='${testMgmt.name}'>${testMgmt.name}</option>";
			        			}
			        			</c:forEach>
	    	                     
	     							
	     						$("#testManagement").append(internalOutput);
	     		        		$("#testManagement").trigger("chosen:updated");
			        		}
			        		
			        		
			        		if(json.executionModel != null && json.executionModel != 'undefined'){
			        			$(":radio[value='"+json.executionModel+"']").attr('checked',true);
			        		}
			        		
			        		
			        		if(json.agileMethodologies != null && json.agileMethodologies != 'undefined'){
			        			 document.getElementById("agileProjectMethodologies").style.display="";
			        			$("#agileMethodologies").empty(); 
			        			
			        			var internalOutput = "";
			        			 
			        			var methodologies = json.agileMethodologies;
			        			<c:forEach var="methods" items="${agileMethodologiesList}">
			        			var existed = 0;
			        			if(methodologies != null && methodologies != 'undefined' && methodologies != ''){
				        			var str_array = methodologies.split(',');
				        			for(var i = 0; i < str_array.length; i++) {
					        			str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
					        			if(str_array[i] == "${methods.name}"){
						        			existed = 1;
						        			internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
					        			}
				        			}
			        			}
			        			if(existed == 0){
			        			internalOutput = internalOutput+"<option value='${methods.name}'>${methods.name}</option>";
			        			}
			        			</c:forEach>
	    	                     
	     							
	     						$("#agileMethodologies").append(internalOutput);
	     		        		$("#agileMethodologies").trigger("chosen:updated");
			        		}
			        		
			        		
			        		if(json.mailToProjectMembers == "Y"){
			        			document.getElementById("mailToTeamMembers").checked = true;
			        			$("#mailToTeamMembers").val("Y");
			        		}else{
			        			document.getElementById("mailToTeamMembers").checked = false;
			        			$("#mailToTeamMembers").val("N");
			        		}
			        		
			        		
			        		if(json.mailToProjectManagers == "Y"){
			        			document.getElementById("mailToManagers").checked = true;;
			        			$("#mailToManagers").val("Y");
			        		}else{
			        			document.getElementById("mailToManagers").checked = false;
			        			$("#mailToManagers").val("N");
			        		}
			        		
			        		
			        		
			        	}
			        }
			        
			 });
			 
			 $("#additionalDetailsLoading").removeAttr("style");
	}
}
</script>



<script type="text/javascript">
	function saveAdditionalDetails(saveType) {
		var projectId = document.getElementById("projectId").value;
		
		
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		
		if ($('#project_additional_form').validate(false, validationSettings)) {
		  	
// 			var formData = $($('#project_additional_form')[0]).serialize();
			var formData = getFormData("project_additional_form");
			var sourceleadUrl = '${sourceleadUrl}';
     		$("#additionalDetailsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');

	        var userId = '${SourceLeadWeb_user.id}';
      		var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
      		
			$.ajax({
						 type : "POST",
						 data: formData,
						 url : sourceleadUrl+ "/storeProjectAdditionalDetails?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&userId="+userId+"&orgLocationId="+orgLocationId+"&saveType="+saveType,
						 success : function(result) {
							 if (result && result.response === "INVALID_DATA") {
	  								$("#additionalDetailsLoading").removeAttr("style");
	  			                    showError(result.responseMessage);
	  			                } else if (result.response === "AWKWARD") {
	  			                    location.href = "/errorPage";
	  			                } else if (result.response === "SUCCESS") {
	  			                	 if(saveType == "Save"){
	  									 $("#additionalDetailsLoading").removeAttr("style");
	  									 getAdditionalDetails();
	  								 }else{
	  									 location.replace(sourceleadUrl+"/projectDashboard");  
	  								 }
	  			                } else if (result.indexOf('Sign In') !== -1) {
	  								location.replace(sourceleadUrl);
	  							} else {
	  			                    showError("Operation failed, please try again");
	  			                }

						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#project_additional_form', function() {
		$("#project_additional_form").showHelpOnFocus();
		$("#project_additional_form").validateOnBlur(false, validationSettings);
	});
</script>


<script>
function projectLocation(){
	$("#userLocation").val('');
}
</script>


<!-- <script type="text/javascript">
	       
//Adding Customized Front End Technologies to the list 
var selectone, chosenone;
selectone = $("#frontEnd");
selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Front End Technologies:' });
chosenone = selectone.data('chosen');
chosenone.search_field.on('keyup', function(e1)
{
	var frontEndTech = this.value.replace(","," ");
	   if ((e1.which == 13 || e1.which == 188) && (frontEndTech != null  && frontEndTech != '' && frontEndTech != 'undefined'))
        {
		   var par = this.parent;
            var option = $("<option>").val(frontEndTech).text(frontEndTech);
            selectone.append(option);
            selectone.find(option).prop('selected', true);
            
            $("#frontEnd option").each(function(){
                 $(this).siblings("[value='"+ this.value+"']").remove();
           });
            
            $("#frontEnd").trigger("chosen:updated");
           
        }

});



//Adding Customized Back End Technologies to the list 
var selecttwo, chosentwo;
selecttwo = $("#backEnd");
selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Back End Technologies:' });
chosentwo = selecttwo.data('chosen');
chosentwo.search_field.on('keyup', function(e2)
{
	var backEndTech = this.value.replace(","," ");
	   if ((e2.which == 13 || e2.which == 188) && (backEndTech != null  && backEndTech != '' && backEndTech != 'undefined'))
        {
		   var par = this.parent;
            var option = $("<option>").val(backEndTech).text(backEndTech);
            selecttwo.append(option);
            selecttwo.find(option).prop('selected', true);
            
            $("#backEnd option").each(function(){
                 $(this).siblings("[value='"+ this.value+"']").remove();
           });
            
            $("#backEnd").trigger("chosen:updated");
           
        }

});





//Adding Customized Database to the list 
var selectthree, chosenthree;
selectthree = $("#projectDatabase");
selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Database:' });
chosenthree = selectthree.data('chosen');
chosenthree.search_field.on('keyup', function(e3)
{
	var projectDatabase = this.value.replace(","," ");
	   if ((e3.which == 13 || e3.which == 188) && (projectDatabase != null  && projectDatabase != '' && projectDatabase != 'undefined'))
        {
		   var par = this.parent;
            var option = $("<option>").val(projectDatabase).text(projectDatabase);
            selectthree.append(option);
            selectthree.find(option).prop('selected', true);
            
            $("#projectDatabase option").each(function(){
                 $(this).siblings("[value='"+ this.value+"']").remove();
           });
            
            $("#projectDatabase").trigger("chosen:updated");
           
        }

});




//Adding Customized Other Technologies to the list 
var selectfour, chosenfour;
selectfour = $("#otherTechnologies");
selectfour.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Other Technologies:' });
chosenfour = selectfour.data('chosen');
chosenfour.search_field.on('keyup', function(e4)
{
	var otherTechnologies = this.value.replace(","," ");
	   if ((e4.which == 13 || e4.which == 188) && (otherTechnologies != null  && otherTechnologies != '' && otherTechnologies != 'undefined'))
        {
		   var par = this.parent;
            var option = $("<option>").val(otherTechnologies).text(otherTechnologies);
            selectfour.append(option);
            selectfour.find(option).prop('selected', true);
            
            $("#otherTechnologies option").each(function(){
                 $(this).siblings("[value='"+ this.value+"']").remove();
           });
            
            $("#otherTechnologies").trigger("chosen:updated");
           
        }

});

//Adding Customized Other Technologies to the list 
var selectfive, chosenfive;
selectfive = $("#testManagement");
selectfive.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Test Management:' });
chosenfive = selectfive.data('chosen');
chosenfive.search_field.on('keyup', function(e5)
{
	var testManagement = this.value.replace(","," ");
	   if ((e5.which == 13 || e5.which == 188) && (testManagement != null  && testManagement != '' && testManagement != 'undefined'))
        {
		   var par = this.parent;
            var option = $("<option>").val(testManagement).text(testManagement);
            selectfive.append(option);
            selectfive.find(option).prop('selected', true);
            
            $("#testManagement option").each(function(){
                 $(this).siblings("[value='"+ this.value+"']").remove();
           });
            
            $("#testManagement").trigger("chosen:updated");
           
        }

});

//Adding Customized Other Technologies to the list 
var selectsix, chosensix;
selectsix = $("#agileMethodologies");
selectsix.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add New Agile Methdologies:' });
chosensix = selectsix.data('chosen');
chosensix.search_field.on('keyup', function(e6)
{
	var agileMethodologies = this.value.replace(","," ");
	   if ((e6.which == 13 || e6.which == 188) && (agileMethodologies != null  && agileMethodologies != '' && agileMethodologies != 'undefined'))
        {
		   var par = this.parent;
            var option = $("<option>").val(agileMethodologies).text(agileMethodologies);
            selectsix.append(option);
            selectsix.find(option).prop('selected', true);
            
            $("#agileMethodologies option").each(function(){
                 $(this).siblings("[value='"+ this.value+"']").remove();
           });
            
            $("#agileMethodologies").trigger("chosen:updated");
           
        }

});


				
</script>  -->
