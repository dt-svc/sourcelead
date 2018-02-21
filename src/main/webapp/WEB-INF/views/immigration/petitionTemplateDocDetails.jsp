<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var = "docsLst" scope = "request" value = '<%= session.getAttribute("templateDocList") %>'/>
<script>   var rowId=0; </script>
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
		<input type="hidden" name="id" id="templateId"/>
		<div class="addImmiContent clearfix">
			<div class="immiTemp clearfix" id="templateDocDivStatic">
    			<c:choose>
    				<c:when  test="${empty docsLst}"> 
    				
				<div id="templateDocDivDynamic" class="col-md-12 clearfix noPad mt">
				  <div class="col-md-3">
				  
					<select class="form-control chosen-select" 
									name="immigrationDocList[0].documentType" title='Document Type' field-name="Select Document" data-validation="required" id="candidateEducation0">
						<option value="" selected>--Select Document--</option>
						<c:forEach var="edu" items="${educationsList}">
										<option value="${edu.id}">${edu.name}</option>
									</c:forEach>
					</select>
				  </div>
				  <div class="col-md-7 noPad">
						<input type="text" name="immigrationDocList[0].description" id="description0" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
				  </div>
				  <div class="col-md-2 clearfix">
					  <div class="col-md-4 noPad">
						<label class="switch mt5 text-center">
						<input checked id="requiredCheck0" name="immigrationDocList[0].isRequired" title="Is Required" type="checkbox" value = "Y" onclick = "tempDocReq(0);">
						<div class="slider round"></div>
						</label>
					  </div>
					   <div class="col-md-4">
					   		<div class="mt5 ml5 clearfix">
						   <input id="multipleCheck0" title="Allow multiple Docs" name="immigrationDocList[0].multiple" class="" type="checkbox">
						   <!-- <label for="checkbox-1" class="checkbox-custom-label fmcheckbox-lable"></label> -->
						   </div>
					   </div>						  
					  <div id="divButton" class="col-md-4">
					  		<button type="button" id="btnDynamicRow0" onclick="appendTempDocDiv();" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
					  </div>				  					  					  						  	
				  </div>
				</div>
				<script>  rowId=rowId+1;  console.log(" inside for emoty record -->"+rowId);</script>
				</c:when> 
				
				<c:otherwise>		
				<c:forEach var="items" items="${docsLst}" varStatus="rows">
				
				 <c:choose>
				<c:when test="${rows.count == 1}">
				<div id="templateDocDivDynamic" class="col-md-12 clearfix noPad mt">
				  <div class="col-md-3">
				  <input type="hidden" name="immigrationDocList[${rows.count-1}].id" value="${items.id}" id="templateId"/>
				  
					<select class="form-control chosen-select" 
									name="immigrationDocList[${rows.count-1}].documentType" title='Document Type' field-name="Select Document" data-validation="required" id="candidateEducation0">
						<option value="" selected>--Select Document--</option>
						<c:forEach var="edu" items="${educationsList}">
										<option ${edu.id == items.documentType? 'selected': ''} value="${items.documentType}">${edu.name}</option>
									</c:forEach>
					</select>
				  </div>
				  <div class="col-md-7 noPad">
						<input type="text" name="immigrationDocList[${rows.count-1}].description" id="description0" value ="${items.description}" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
				  </div>
				  <div class="col-md-2 clearfix">
					  <div class="col-md-4 noPad">
						<label class="switch mt5 text-center">
						<input checked id="requiredCheck0" name="immigrationDocList[${rows.count-1}].isRequired" title="Is Required" type="checkbox" value = "Y" onclick = "tempDocReq(0);">
						<div class="slider round"></div>
						</label>
					  </div>
					   <div class="col-md-4">
					   		<div class="mt5 ml5 clearfix">
						   <input id="multipleCheck0" title="Allow multiple Docs" name="immigrationDocList[${rows.count-1}].multiple" class="" type="checkbox">
						   <!-- <label for="checkbox-1" class="checkbox-custom-label fmcheckbox-lable"></label> -->
						   </div>
					   </div>						  
					  <div id="divButton" class="col-md-4">
					  		<button type="button" id="btnDynamicRow0" onclick="appendTempDocDiv();" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
					  </div>				  					  					  						  	
				  </div>
				</div>
				</c:when>
				<c:otherwise>
				<div id="templateDocDivDynamic" class="col-md-12 clearfix noPad mt">
				  <div class="col-md-3">
				  <input type="hidden"  name="immigrationDocList[${rows.count-1}].id" value="${items.id}" id="templateId"/>
					<select class="form-control chosen-select" 
									name="immigrationDocList[${rows.count-1}].documentType" title='Document Type' field-name="Select Document" data-validation="required" id="candidateEducation0">
						
						<option value="" >--Select Document--</option>
						<c:forEach var="edu" items="${educationsList}">
										<option ${edu.id == items.documentType? 'selected': ''} value="${items.documentType}">${edu.name}</option>
										
									</c:forEach>
									
					</select>
				  </div>
				  <div class="col-md-7 noPad">
						<input type="text" name="immigrationDocList[${rows.count-1}].description" value ="${items.description}" id="description0" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
				  </div>
				  <div class="col-md-2 clearfix">
					  <div class="col-md-4 noPad">
						<label class="switch mt5 text-center">
						<input checked id="requiredCheck0" name="immigrationDocList[${rows.count-1}].isRequired" title="Is Required" type="checkbox" value = "Y" onclick = "tempDocReq(0);">
						<div class="slider round"></div>
						</label>
					  </div>
					   <div class="col-md-4">
					   		<div class="mt5 ml5 clearfix">
						   <input id="multipleCheck0" title="Allow multiple Docs" name="immigrationDocList[${rows.count-1}].multiple" class="" type="checkbox">
						   <!-- <label for="checkbox-1" class="checkbox-custom-label fmcheckbox-lable"></label> -->
						   </div>
					   </div>						  
					  <div id="divButton" class="col-md-4">
					  		<button type='button' class='btn btm-sm' id='btnDynamicRow"+rowId+"' onclick='removeTempDocDiv("+rowId+")' ><i class='fa fa-remove text-danger'></i></button>
					  </div>				  					  					  						  	
				  </div>
				</div>
				</c:otherwise>
				</c:choose>
				<script>  rowId=rowId+1;  console.log(" inside for each rowId -->"+rowId);</script>
				</c:forEach>
				</c:otherwise>	
			   	</c:choose>
			</div>
 		</div>
   </form>
		<div class="addImmiFoot">
			<a href="javascript:void(0)" onclick="immigration.gotoTemplatePreviousPage('templateDetails')" class="pull-left">Prev</a>
			<a href="javascript:void(0)" onclick="immigration.savePetitionDoc('saveContinue')" class="ml10 nextBtn">Save &amp; Continue</a>
			<a href="javascript:void(0)" onclick="immigration.savePetitionDoc('save')" class="nextBtn">Save</a>
			<a href="javascript:void(0)" onclick="immigration.resetFormData('PetitionTemplateDocDetails')">Reset</a>
			<div class="clearfix"></div>
		</div>   
   </div>
   </section>
   <script type="text/javascript">
   function appendTemplateDocDetails(rowId){
	   console.log(" inside appendTemplateDocDetails function  rowId -->"+rowId);
	   var templateDocDetails = "";
		templateDocDetails = templateDocDetails+ "<div id='dynamicRow"+rowId+"' class='col-md-12 clearfix noPad mt'>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-3'>";
		templateDocDetails = templateDocDetails+ "<select class='form-control chosen-select' title='Document Type' field-name='Select Document' data-validation='required' name='immigrationDocList["+rowId+"].documentType' id='candidateEducation"+rowId+"' data-validation='validate_Space'>";
		templateDocDetails = templateDocDetails+ "<option value='' selected>--Select Document--</option>";
		<c:forEach var='edu' items='${educationsList}'>
		templateDocDetails = templateDocDetails+ "<option value='${edu.id}'>${edu.name}</option>";
		</c:forEach>
		templateDocDetails = templateDocDetails+ "</select></div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-7 noPad'>";
		templateDocDetails = templateDocDetails+ "<input type='text' title='Description' name='immigrationDocList["+rowId+"].description' id='description"+rowId+"' class='form-control' placeholder='Description' data-validation='validate_length length2-500' data-validation-optional='true'/></div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-2 clearfix'>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-4 noPad'>";
		templateDocDetails = templateDocDetails+ "<label class='switch mt5 text-center'>";
		templateDocDetails = templateDocDetails+ "<input checked title='Is Required' value = 'Y' onclick = 'tempDocReq("+rowId+");' id='requiredCheck"+rowId+"' name='immigrationDocList["+rowId+"].isRequired' type='checkbox'>";
		templateDocDetails = templateDocDetails+ "<div class='slider round'></div></label>";
		templateDocDetails = templateDocDetails+ "</div><div class='col-md-4'><div class='mt5 ml5 clearfix'>";
		templateDocDetails = templateDocDetails+ "<input title='Allow multiple Docs' id='multipleCheck"+rowId+"' class='' name='immigrationDocList["+rowId+"].multiple' type='checkbox'>";
		templateDocDetails = templateDocDetails+ "<label for='checkbox-1' class='checkbox-custom-label fmcheckbox-lable'></label></div></div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-4'><button type='button' class='btn btm-sm' id='btnDynamicRow"+rowId+"' onclick='removeTempDocDiv("+rowId+")' ><i class='fa fa-remove text-danger'></i></button></div></div></div>";
		return templateDocDetails 
   }
	
 
   function appendTempDocDiv(){
	   console.log(" before rowId -->"+rowId);
	   $("#templateDocDivStatic").append(appendTemplateDocDetails(rowId++));
   }
   
   function removeTempDocDiv(id){
	   $("#dynamicRow"+id).remove();
	   rowId--;
   }
   	</script>
   	
   	<script type="text/javascript">
function tempDocReq(id){
	var checked = document.getElementById("requiredCheck"+id).checked
	if(checked == true){
		$("#requiredCheck"+id).val("Y");
	}else{
		$("#requiredCheck"+id).val("N");
	}
}
</script>

   	<script>
$(document).ready(function(){
	$("#templateId").val(jsonObj.id);
});
</script> 
   