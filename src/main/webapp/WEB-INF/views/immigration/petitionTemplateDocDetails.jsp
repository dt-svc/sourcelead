<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var = "docsLst" scope = "page" value = '<%= session.getAttribute("templateDocList") %>'/>
<c:set var="rowIndex" value="0" scope="page" /><!-- used for dynamic row index  rowindex starts from 0-->
<div id="popUp"></div>
<section class="addImmiWrap">
	<div class="addImmiInnWrap">
		<div class="immiStepsHead clearfix">
			<h2>Document Details</h2>
			 <c:if test="${empty docsLst}">
			<ul class="addImmiSteps">
				<li class="active page-updated">1</li>
				<li class="active">2</li>
				<li>3</li>
				<li>4</li>
				<b></b>
				<div class="clearfix"></div>
			</ul>
			</c:if>
			<c:if test="${!empty docsLst}">
			<ul class="addImmiSteps">
				<li class="active page-updated">1</li>
				<li class="active page-updated">2</li>
				<li>3</li>
				<li>4</li>
				<b></b>
				<div class="clearfix"></div>
			</ul>
			</c:if>
		</div>
		<form id="PetitionTemplateDocDetails" name="PetitionTemplateDocDetails" >
		<input type="hidden" name="id" id="templateId"/>
		<div class="addImmiContent clearfix">
			<div class="immiTemp clearfix" id="templateDocDivStatic">
    			<c:choose>
    			   <c:when  test="${empty docsLst}"> <!--  when list is empty loads default row -->
				      
				      <div id="templateDocDivDynamic" class="col-md-12 clearfix noPad mt">
				      
				      <div class="col-md-2">
						<select class="form-control selectpicker" 
										name="immigrationDocList[0].documentCategory" id="documentCategory0" onchange="getDocumentType(this,'0');"  title='Document Category' field-name="Select Document Category" data-validation="required">
									<option value="" selected>--Select Document Category--</option>
									<option value="PetitionerCategory" >Petitioner</option>
									<option value="Document Type" >Beneficiary</option>
									<option value="AttorneyCategory" >Attorney</option>
									
						</select>
					   </div>
				      
				  		<div class="col-md-3">
					  
						<select class="form-control selectpicker" 
										name="immigrationDocList[0].documentType"  onchange="updateUIforOthers(0);" field-name="Select Document" data-validation="required" id="candidateEducation0" data-live-search="true">
							<%-- <option value="" selected>--Select Document--</option>
							<c:forEach var="edu" items="${educationsList}">
											<option value="${edu.id}">${edu.name}</option>
							</c:forEach> --%>
						</select>
					   </div>
					   <div class="col-md-4 noPad">
					   <div id="customOther-defaults0">
					  
							<input type="text" name="immigrationDocList[0].description" id="description0" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
				
					   </div>
					   <div class="row" id="customOther0" style="display:none;">
						   	<div class="col-md-5">
						   	<input type="text" id="otherType0" name="immigrationDocList[0].otherType" title='Name' class="form-control" placeholder="Name"  field-name="Name"  data-validation="validate_length length2-200" data-validation-optional="true"/>
						   	</div>
						   	 <div class="col-md-7">
						   	<input type="text" name="immigrationDocList[0].descriptionOther" id="description0" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
						   	</div> 
					   </div>
					   
					   
					   
					    </div>
					  <div class="col-md-2 clearfix">
						  <div class="col-md-4 noPad" id="currentRow0">
							<label class="switch mt5 text-center">
							<input checked id="requiredCheck0" name="immigrationDocList[0].isRequired" title="Is Required" type="checkbox" value = "Y" onclick = "tempDocReq(0);">
							<div class="slider round" title="Is Required"></div>
							</label>
						  </div>
						   <div class="col-md-4">
						   		<div class="mt5 ml5 clearfix">
							   <input id="multipleCheck0" title="Allow multiple Docs" name="immigrationDocList[0].multiple"  type="checkbox" value = "N" onclick = "tempDocCheckBox(0)">
							   </div>
						   </div>						  
						  <div id="divButton" class="col-md-4">
						  		<button type="button" id="btnDynamicRow0" onclick="appendTempDocDiv();" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
						  </div>				  					  					  						  	
					  </div>
					</div>
					<c:set var="rowIndex" value="${rowIndex + 1}" scope="page"/><!--setting rowIndex as 1 when default row is loaded -->
				 </c:when>
				<c:otherwise> <!--  when list item returned after save or navigation of page -->		
				<c:forEach var="items" items="${docsLst}" varStatus="rows">
					<c:if test="${rows.count == 1}"> <!--adding add row div for first row-->
					<div id="templateDocDivDynamic" class="col-md-12 clearfix noPad mt">
					  </c:if>
					  <c:if test="${rows.count > 1}"><!--when rowIndex is 1 default(first) row is loaded with add button-->
					    <div id="dynamicRow${rows.count-1}" class="col-md-12 clearfix noPad mt"><!--when rowIndex is 1, 2 row onwards loaded with close button-->
					  </c:if>
					  
					  <div class="col-md-2">
					  
						<select class="form-control selectpicker" 
									name="immigrationDocList[${rows.count-1}].documentCategory" id="documentCategory${rows.count-1}" title='Document Category' field-name="Select Document Category" data-validation="required" onchange="getDocumentType(this,${rows.count-1});" >
							            <option value="" >--Select Document Category--</option>
							        	<option ${items.documentCategory == "PetitionerCategory" ? 'selected': ''} value="PetitionerCategory">Petitioner</option>
							        	<option ${items.documentCategory == "Document Type" ? 'selected': ''} value="Document Type">Beneficiary</option>
							        	<option ${items.documentCategory == "AttorneyCategory" ? 'selected': ''} value="AttorneyCategory">Attorney</option>
						</select>
					  </div>
					  
					  
					  <div class="col-md-3">
					  <input type="hidden" name="immigrationDocList[${rows.count-1}].id" value="${items.id}" id="templateId${rows.count-1}"/>
					  	<%-- 	category::::<c:out value ="${docCat.documentCategory }"/>c --%>
						<select class="form-control selectpicker" 
								onchange="updateUIforOthers(${rows.count-1});"		name="immigrationDocList[${rows.count-1}].documentType" field-name="Select Document" data-validation="required" id="candidateEducation${rows.count-1}" data-live-search="true">
							
							
							<option value="" >--Select Document--</option>
							
					
							<c:choose>
							
							<c:when test="${items.documentCategory eq 'Document Type'}">
							
							<c:forEach var="edu" items="${educationsList}">
								<option ${edu.id == items.documentType? 'selected': ''} value="${edu.id}">${edu.name}</option>
							</c:forEach>
							</c:when>
							
							<c:when test="${items.documentCategory eq 'PetitionerCategory'}">
							<%-- Petitioner <c:out value ="${docCat.documentCategory }"/> --%>
							<c:forEach var="pet" items="${petitionerDocsList}">
							 
								<option ${pet.id == items.documentType? 'selected': ''} value="${pet.id}">${pet.name}</option>
							</c:forEach>
							</c:when>
							
							<c:otherwise>
							<c:forEach var="attor" items="${attorneyDocsList}">
								<option ${attor.id == items.documentType? 'selected': ''} value="${attor.id}">${attor.name}</option>
							</c:forEach>
						    </c:otherwise>
							</c:choose>
							
						</select>
						
					  </div>
					
					<div class="col-md-4 noPad" id="currentRow${rows.count-1}">
					<c:choose>
    			   <c:when  test="${empty items.otherType}">
					   <div id="customOther-defaults${rows.count-1}">
					  
							<input type="text" name="immigrationDocList[${rows.count-1}].description" id="description0"  value ="${items.description}" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
				
					   </div>
					   <div class="row" id="customOther${rows.count-1}" style="display:none;">
						   	<div class="col-md-5">
						   	<input type="text" id="otherType${rows.count-1}"    name="immigrationDocList[${rows.count-1}].otherType"  value ="" title='Name' class="form-control" placeholder="Name"  field-name="Name" />
						   	</div>
						   	 <div class="col-md-4">
						   <input type="text" name="immigrationDocList[${rows.count-1}].descriptionOther"   id="description${rows.count-1}" value ="" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
						   	</div> 
					   </div>
					   </c:when>
					   <c:otherwise>
					    <div id="customOther-defaults${rows.count-1}" style="display:none;">
					  
							<input type="text" name="immigrationDocList[${rows.count-1}].description" id="description0"  value ="" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
				
					   </div>
					   <div class="row" id="customOther${rows.count-1}" >
						   	<div class="col-md-5">
						   	<input type="text" id="otherType${rows.count-1}" name="immigrationDocList[${rows.count-1}].otherType"  value ="${items.otherType}" title='Name' class="form-control" placeholder="Name"  field-name="Name"  data-validation="validate_length length2-200"  data-validation-optional="true"/>
						   	</div>
						   	 <div class="col-md-7">
						   <input type="text" name="immigrationDocList[${rows.count-1}].descriptionOther" id="description${rows.count-1}"    value ="${items.descriptionOther}" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
						   	</div> 
					   </div>
					    </c:otherwise>
					   </c:choose>
					    </div>
					 <%--  <div class="col-md-7 noPad">
							<input type="text" name="immigrationDocList[${rows.count-1}].description" id="description0" value ="${items.description}" title='Description' class="form-control" placeholder="Description" data-validation="validate_length length2-500" data-validation-optional="true"/>
					  </div> --%>
					  
					  
					  
					  <div class="col-md-2 clearfix">
						  <div class="col-md-4 noPad">
							<label class="switch mt5 text-center">
							<input id="requiredCheck${rows.count-1}" name="immigrationDocList[${rows.count-1}].isRequired" title="Is Required" type="checkbox" value = "${items.isRequired}" onclick = "tempDocReq('${rows.count-1}');" ${items.isRequired == 'Y' ? 'checked' : '' }>
							<div class="slider round"></div>
							</label>
						  </div>
						   <div class="col-md-4">
						   		<div class="mt5 ml5 clearfix">
							   <input id="multipleCheck${rows.count-1}" title="Allow multiple Docs" name="immigrationDocList[${rows.count-1}].multiple" value = "${items.multiple}" onclick = "tempDocCheckBox('${rows.count-1}');" type="checkbox" ${items.multiple == 'Y' ? 'checked' : '' }>
							   </div>
						   </div>						  
						<c:if test="${rows.count == 1}"><!--Adding 'add row' div for the first row-->
						  <div id="divButton" class="col-md-4">
						  		<button type="button" id="btnDynamicRow0" onclick="appendTempDocDiv();" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
						  </div>				  					  					  						  	
					
						</c:if>
					  <c:if test="${rows.count > 1}"><!--adding 'remove div' for 2 row onwards-->
							  <div id="divButton" class="col-md-4">
							  		<button type="button" class="btn btm-sm" id="btnDynamicRow${rows.count-1}" onclick="removeTempDocDiv('${rows.count-1}')"><i class='fa fa-remove text-danger'></i></button>
							  </div>				  					  					  						  	
					  </c:if>
					  </div>
					</div>
				<c:set var="rowIndex" value="${rowIndex + 1}" scope="page"/><!--increment the rowIndex  for dynamic rows after save or page navigation  -->
				</c:forEach>
			</c:otherwise>	
		   </c:choose>
			</div>
 		</div>
   </form>
		<div class="addImmiFoot">
			<a onclick="immigration.gotoTemplatePreviousPage('templateDetails')" class="pull-left">Back</a>
			<a onclick="immigration.savePetitionDoc('saveContinue')" class="ml10 nextBtn">Save &amp; Continue</a>
			<a onclick="immigration.savePetitionDoc('save')" class="nextBtn">Save</a>
			<a onclick="immigration.resetFormData('PetitionTemplateDocDetails')">Reset</a>
			<div class="clearfix"></div>
		</div>   
   </div>
   </section>
<script type="text/javascript">
   function appendTemplateDocDetails(rowId){
	   var templateDocDetails = "";
		templateDocDetails = templateDocDetails+ "<div id='dynamicRow"+rowId+"' class='col-md-12 clearfix noPad mt'>";
		
		templateDocDetails = templateDocDetails+ "<div class='col-md-2'>";
		
		templateDocDetails = templateDocDetails+ "<select class='form-control selectpicker' title='Document Type'  field-name='Select Document Category' data-validation='required' name='immigrationDocList["+rowId+"].documentCategory' id='documentCategory"+rowId+"' onchange='getDocumentType(this,"+rowId+");'  data-validation='validate_Space'>";
		templateDocDetails = templateDocDetails+ "<option value='' selected>--Select Document Category--</option>";
		templateDocDetails = templateDocDetails+ "<option value='PetitionerCategory'>Petitioner</option>";
		templateDocDetails = templateDocDetails+ "<option value='Document Type'>Beneficiary</option>";
		templateDocDetails = templateDocDetails+ "<option value='AttorneyCategory'>Attorney</option>";
		templateDocDetails = templateDocDetails+ "</select></div>";
		
		templateDocDetails = templateDocDetails+ "<div class='col-md-3'>";
		templateDocDetails = templateDocDetails+ "<select class='form-control selectpicker' onchange='updateUIforOthers("+rowId+")' field-name='Select Document' data-validation='required' name='immigrationDocList["+rowId+"].documentType' id='candidateEducation"+rowId+"' data-validation='validate_Space' data-live-search='true'>";
		templateDocDetails = templateDocDetails+ "<option value='' selected>--Select Document--</option>";
		/* <c:forEach var='edu' items='${educationsList}'>
		templateDocDetails = templateDocDetails+ "<option value='${edu.id}'>${edu.name}</option>";
		</c:forEach> */
		templateDocDetails = templateDocDetails+ "</select></div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-4 noPad' id='currentRow"+rowId+"'>";
		templateDocDetails = templateDocDetails+ "	<div id='customOther-defaults"+rowId+"'>";
		templateDocDetails = templateDocDetails+ "<input type='text' title='Description' name='immigrationDocList["+rowId+"].description' id='description"+rowId+"' class='form-control' placeholder='Description' data-validation='validate_length length2-500' data-validation-optional='true'/>";	
		templateDocDetails = templateDocDetails+ "	</div>";
		templateDocDetails = templateDocDetails+ "<div class='row' id='customOther"+rowId+"' style='display:none;'>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-5'>";
		templateDocDetails = templateDocDetails+ "<input type='text' id='otherType"+rowId+"' name='immigrationDocList["+rowId+"].otherType' title='Name' class='form-control' placeholder='Name' field-name='Name' data-validation='validate_length length2-200'/>";
		templateDocDetails = templateDocDetails+ "</div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-7'>";
		templateDocDetails = templateDocDetails+ "<input type='text' name='immigrationDocList["+rowId+"].descriptionOther' id='description"+rowId+"' title='Description' class='form-control' placeholder='Description' data-validation='validate_length length2-500' data-validation-optional='true'/>";
		templateDocDetails = templateDocDetails+ "	</div>";
		templateDocDetails = templateDocDetails+ "	</div>";
		templateDocDetails = templateDocDetails+ "	</div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-2 clearfix'>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-4 noPad'>";
		templateDocDetails = templateDocDetails+ "<label class='switch mt5 text-right'>";
		templateDocDetails = templateDocDetails+ "<input checked title='Is Required' value = 'Y' onclick = 'tempDocReq("+rowId+");' id='requiredCheck"+rowId+"' name='immigrationDocList["+rowId+"].isRequired' type='checkbox'>";
		templateDocDetails = templateDocDetails+ "<div class='slider round'></div></label>";
		templateDocDetails = templateDocDetails+ "</div><div class='col-md-4'><div class='mt5 ml5 clearfix'>";
		templateDocDetails = templateDocDetails+ "<input title='Allow multiple Docs' id='multipleCheck"+rowId+"' class='' name='immigrationDocList["+rowId+"].multiple' type='checkbox' value = 'N' onclick = 'tempDocCheckBox("+rowId+");'>";
		templateDocDetails = templateDocDetails+ "<label for='checkbox-1' class='checkbox-custom-label fmcheckbox-lable'></label></div></div>";
		templateDocDetails = templateDocDetails+ "<div class='col-md-4'><button type='button' class='btn btm-sm' id='btnDynamicRow"+rowId+"' onclick='removeTempDocDiv("+rowId+")' ><i class='fa fa-remove text-danger'></i></button></div></div></div>";
		
		return templateDocDetails 
   }
	
   var rowId ='<c:out value ="${rowIndex}"/>'// set the value for dyamic row when clicked on add button in UI.
   function appendTempDocDiv(){
	   console.log("added rowId --->"+rowId)
	   $("#templateDocDivStatic").append(appendTemplateDocDetails(rowId++)); 
// 	   $('.selectpicker_oncreate').selectpicker('refresh');
	   $('.selectpicker').selectpicker('refresh');
   }
   var deletedDocListIds = new Array();
   function removeTempDocDiv(id){
	   console.log("deleted id  --->"+id)
	   if($("#templateId"+id).val() !==  undefined ){
		   deletedDocListIds.push($("#templateId"+id).val());   
	   }
	   $("#dynamicRow"+id).remove();
	   //rowId--;// decrement rowid when deleted in UI.
   }
		
		$('.selectpicker').selectpicker('refresh');
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
	
	<script type="text/javascript">
	function tempDocCheckBox(id){
		var checked = document.getElementById("multipleCheck"+id).checked
		if(checked == true){
			$("#multipleCheck"+id).val("Y");
		}else{
			$("#multipleCheck"+id).val("N");
		}
	}
	function updateUIforOthers(id){
		console.log("rowId is "+id);
	  if($("#candidateEducation"+id+"  :selected").text() =="Other"){
		  $("#customOther"+id).show();
		  $("#otherType"+id).attr("data-validation","required");
		  $("#customOther-defaults"+id).hide();
	    }else{
		     $("#customOther"+id).hide();
		    $("#customOther-defaults"+id).show();
		     $("#otherType"+id).removeAttr("data-validation");
	    }
	 
	  // reset the values of all input elements when changed value in dropdown 
	  $("#currentRow"+id).find('input:text').val('');
	  
	}
	</script>
	
	<script>
	$(document).ready(function(){
		$("#templateId").val(immTemplId);
	});

	/* Document Category Selection  in immigration template creation */
	function getDocumentType(id,rowId){
		var docType = id.value;
		$('#candidateEducation'+rowId).empty();
		
		 $.ajax({
	            type: "POST",
	            url:sourceleadUrl+immiModulePath +"/getDcoumentType?"+tokenParamName +"="+tokenValue+"&docType="+docType,
	            success: function (json) {
	            	 $('#candidateEducation'+rowId).append('<option selected value="">--Select Document--</option>');
	            	 for (var i = 0; i < json.length; i++) {
	                     var report = json[i];
	                     $('#candidateEducation'+rowId).append('<option value='+report.id +'>'+report.name+'</option>');
	                 }
	            	
	            	 $('#candidateEducation'+rowId).selectpicker('refresh');
	            },
	        });
	}

	/* End */
	</script>

