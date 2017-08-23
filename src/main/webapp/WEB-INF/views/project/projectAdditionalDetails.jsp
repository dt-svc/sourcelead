
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

  <script type="text/javascript">
		window.onload = function () {
			initLocationAuto('projectLocation','','','','','','');
		 };
	</script>

<div id="tab-c" class="newPrTabs_inn">
	
	<form:form id="project_additional_form" name="project_additional_form" method="post" enctype="multipart/form-data">
	<section class="projQueWrap">
		<div class="projQueCta">
			<h2>1. Project location</h2>
			<div class="projQueCtaInn wid50">
				<input type="text" placeholder="Location" name="projectLocation" id="projectLocation" data-validation="required" field-name="Project Location" class="form-control" />
			</div>
		</div>
		<div class="projQueCta">
			<h2>2. Industry</h2>
			<div class="projQueCtaInn wid50">
			  <select class="form-control chosen-select" name="industry" id="industry" onchange="getProjectIndustry()">
				<option value=''>--Select Industry--</option>
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
				<input type="text" placeholder="Team Size" id="teamSize" name="timeSize" class="form-control" />
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
			  <select multiple="" class="chosen-select form-control" id="database" name="database">
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
				<option value=''>Select Option</option>
				<option value='Manual Testing'>Manual Testing</option>
				<option value='Automation'>Automation </option>
				<option value='Jmetter'>Jmetter </option>
				<option value='Selenium'>Selenium </option>
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
				 <option value="Scrum">Scrum</option>
				 <option value="Scrum">Kanban</option>
				 <option value="Scrum">Scrumban</option>
				 <option value="Scrum">XP</option>
			  </select>
			</div>
		</div>																		
	</section>

<div class="form-footer clearfix miniFoot">
  <div class="col-lg-12">
	<div class="pull-right">
	  <input type="button" value="Cancel"  onClick="javascript:window.location.href = '<c:url value='/projectDashboard'/>'" class="btn btn-danger">
	  <input type="button" value="Save" id="save_project" onclick="saveAdditionalDetails('Save');" class="btn btn-primary ml10">
	  <input type="button" value="Save & Finish" id="save_Finish_project" onclick="saveAdditionalDetails('Finish');" class="btn btn-success ml10">
	</div>
  </div>
</div>
</form:form>

</div> 



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
			var frm = $('#project_additional_form').serializeFormJSON();
			 
			var jsonData = JSON.stringify(frm);

			var sourceleadUrl = '${sourceleadUrl}';
     		//$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');

	        var userId = '${SourceLeadWeb_user.id}';
      		var orgLocationId = '${SourceLeadWeb_orgLocations.id}';
      		
			$.ajax({
						 type : "POST",
						 data: jsonData,
						 contentType : 'application/json',
		  	    	     dataType : 'json',
						 url : sourceleadUrl+ "/storeProjectAdditionalDetails?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&userId="+userId+"&orgLocationId="+orgLocationId,
						 success : function() {
								

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
