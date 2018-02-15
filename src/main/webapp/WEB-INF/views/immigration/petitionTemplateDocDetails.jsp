<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="addImmiWrap">
	<div class="addImmiInnWrap">
		<div class="immiStepsHead clearfix">
			<h2>Document Details</h2>
			<ul class="addImmiSteps">
				<li class="active">1</li>
				<li class="active">2</li>
				<li>3</li>
				<li>4</li>
				<b></b>
				<div class="clearfix"></div>
			</ul>
		</div>
		<form id="PetitionTemplateDocDetails" name="PetitionTemplateDocDetails">
		<div class="addImmiContent clearfix">
			<div class="immiTemp clearfix" id="templateDocDivStatic">
				<div id="templateDocDivDynamic" class="col-md-12 clearfix noPad mt">
				  <div class="col-md-3">
					<select class="form-control chosen-select" 
									name="candidateEducation" title='Document Type' field-name="Select Document" data-validation="required" id="candidateEducation">
						<option value="" selected>--Select Document--</option>
						<c:forEach var="edu" items="${educationsList}">
										<option value="${edu.id}">${edu.name}</option>
									</c:forEach>
					</select>
				  </div>
				  <div class="col-md-7 noPad">
						<input type="text" name="description" title='Description' class="form-control" placeholder="Description" />
				  </div>
				  <div class="col-md-2 clearfix">
					  <div class="col-md-4 noPad">
						<label class="switch mt5 text-center">
						<input checked="" id="requiredCheck" name="requiredCheck" title="Is Required" type="checkbox">
						<div class="slider round"></div>
						</label>
					  </div>
					   <div class="col-md-4">
					   		<div class="mt5 ml5 clearfix">
						   <input id="checkbox-1" title="Allow multiple Docs" class="checkbox-custom fmcheckbox" name="checkbox-1" type="checkbox">
						   <label for="checkbox-1" class="checkbox-custom-label fmcheckbox-lable"></label>
						   </div>
					   </div>						  
					  <div id="divButton" class="col-md-4">
					  		<button type="button" id="btnClone" onclick="appendTempDocDiv();" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
					  </div>				  					  					  						  	
				  </div>
				</div>
				
				<%-- <div class="col-md-12 clearfix noPad mt">
				  <div class="col-md-3">
						<select class="form-control chosen-select">
							<option value="" selected>--Select Document--</option>
							<c:forEach var="edu" items="${educationsList}">
								<option value="${edu.id}">${edu.name}</option>
							</c:forEach>
						</select>
					</div>
				  <div class="col-md-7 noPad">
						<input type="text" class="form-control" placeholder="Description" />
				  </div>
				  <div class="col-md-2 clearfix">
					  <div class="col-md-4 noPad">
						<label class="switch mt5 text-center">
						<input checked="" id="" name="" type="checkbox">
						<div class="slider round"></div>
						</label>
					  </div>
					   <div class="col-md-4">
					   		<div class="mt5 ml5 clearfix">
						   <input id="checkbox-2" class="checkbox-custom fmcheckbox" name="checkbox-1" type="checkbox">
						   <label for="checkbox-2" class="checkbox-custom-label fmcheckbox-lable"></label>
						   </div>
					   </div>						  
					  <div class="col-md-4">
					  		<button type="button" class="btn btm-sm"><i class="fa fa-remove text-danger"></i></button>
					  </div>				  					  					  						  	
				  </div>
				</div> --%>
								  					  	
			</div>
 		</div>
   </form>
		<div class="addImmiFoot">
			<a href="javascript:void(0)" onclick="immigration.savePageAndContinuePetitionTemplate('one')" class="pull-left">Prev</a>
			<a href="javascript:void(0)" onclick="immigration.savePageAndContinuePetitionTemplate('three')" class="ml10">Next</a>
			<a href="javascript:void(0)" onclick="immigration.savePetitionDocDetailsAndContinue()" class="nextBtn">Save &amp; Continue</a>
			<a href="javascript:void(0)">Reset</a>
			<div class="clearfix"></div>
		</div>   
   </div>
   </section>
   <script type="text/javascript">
   function appendTemplateDocDetails(rowId){
	   var templateDocDetails = "";
		templateDocDetails = templateDocDetails+ "<div id='dynamicRow"+rowId+"' class='col-md-12 clearfix noPad mt'>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-3'>";
		templateDocDetails = templateDocDetails+ "<select class='form-control chosen-select' title='Document Type' field-name='Select Document' data-validation='required' name='candidateEducation' id='candidateEducation"+rowId+"' data-validation='validate_Space'>";
		templateDocDetails = templateDocDetails+ "<option value='' selected>--Select Document--</option>";
		<c:forEach var='edu' items='${educationsList}'>
		templateDocDetails = templateDocDetails+ "<option value='${edu.id}'>${edu.name}</option>";
		</c:forEach>
		templateDocDetails = templateDocDetails+ "</select></div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-7 noPad'>";
		templateDocDetails = templateDocDetails+ "<input type='text' title='Description' id='description"+rowId+"' class='form-control' placeholder='Description' /></div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-2 clearfix'>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-4 noPad'>";
		templateDocDetails = templateDocDetails+ "<label class='switch mt5 text-center'>";
		templateDocDetails = templateDocDetails+ "<input checked='' title='Is Required' id='requiredCheck"+rowId+"' name='' type='checkbox'>";
		templateDocDetails = templateDocDetails+ "<div class='slider round'></div></label>";
		templateDocDetails = templateDocDetails+ "</div><div class='col-md-4'><div class='mt5 ml5 clearfix'>";
		templateDocDetails = templateDocDetails+ "<input id='checkbox-1' title='Allow multiple Docs' id='multipleCheck"+rowId+"' class='checkbox-custom fmcheckbox' name='checkbox-1' type='checkbox'>";
		templateDocDetails = templateDocDetails+ "<label for='checkbox-1' class='checkbox-custom-label fmcheckbox-lable'></label></div></div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-4'><button type='button' class='btn btm-sm' id='btnDynamicRow"+rowId+"' onclick='removeTempDocDiv("+rowId+")' ><i class='fa fa-remove text-danger'></i></button></div></div></div>";
		return templateDocDetails 
   }
	
   /* $(document).ready(function(){
	   $("#templateDocDivStatic").append(appendTemplateDocDetails());
   }); */
   var rowId=0;
   function appendTempDocDiv(){
	   $("#templateDocDivStatic").append(appendTemplateDocDetails(rowId++));
   }
   
   function removeTempDocDiv(id){
	   $("#dynamicRow"+id).remove();
   }
   	</script>
   
   