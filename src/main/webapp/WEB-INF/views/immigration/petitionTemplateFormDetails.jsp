<section class="addImmiWrap">
	<div class="addImmiInnWrap">
		<div class="immiStepsHead clearfix">
			<h2>Form Details</h2>
			<ul class="addImmiSteps">
				<li class="active">1</li>
				<li class="active">2</li>
				<li class="active">3</li>
				<li>4</li>
				<b></b>
				<div class="clearfix"></div>
			</ul>
		</div>
		<form id="petitionTemplateFormDetails" name="petitionTemplateFormDetails">
		<div class="addImmiContent clearfix">
			<div class="immiTemp clearfix" id="templateFormDivStatic">
				<div class="col-md-12 clearfix noPad mt">
				  <div class="col-md-2 padding_right"><input type="text" name="immigrationFormList[0].formName" id="formName0" class="form-control" field-name="Form Name"  data-validation = "required validate_length length2-100" placeholder="Form name" /></div>
				  <div class="col-md-3">
				   <input type="hidden" name="id" id="templateId"/>
					<div class="input-group col-md-12 errorInptgroup" style="margin:0 auto">
						<label class="input-group-btn"> <span class="btn btn-primary" onchange="uploadFormDocs(0);"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
						  <input style="display: none;" onchange="uploadBtnFile(0);"  id="uploadBtn0"  class="upload" type="file">
						  </span></label>
						   <input type="text" class="form-control" readonly id="uploadFile0" name = "immigrationFormList[0].immigrationFormFileInfo[0].docFile"  placeholder="Attachment" field-name="Attachment" data-validation="required">
<!-- 						<input class="form-control" id="uploadFile0" placeholder="Attachment" type="text"> -->
					  </div>				  	
				  </div>
				  <div class="col-md-5 noPad">
						<input type="text" class="form-control" name="immigrationFormList[0].formDescription" id="formDescription0" placeholder="Description" data-validation = "validate_length length2-100" data-validation-optional="true"/>
				  </div>
				  <div class="col-md-2 clearfix">
					  <div class="col-md-6 noPad">
						<label class="switch mt5 text-center">
						<input checked id="requiredCheck0" name="immigrationFormList[0].isRequired" title="Is Required" type="checkbox" onclick="checkIsReqDoc(0)" value="Y">

						<div class="slider round"></div>
						</label>
					  </div>						  
					  <div id="divFormButton" class="col-md-4">
					  		<button type="button" id="divFormButtonDynamicRow0" onclick="appendFormDetailsDiv();" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
					  </div>			  					  					  						  	
				  </div>
				</div>
				
				
								  					  	
			</div>
 		</div>
     </form>
		<div class="addImmiFoot">
			<a href="javascript:void(0)" onclick="immigration.gotoTemplatePreviousPage('templateDocs')" class="pull-left">Prev</a>
			<a href="javascript:void(0)" onclick="immigration.savePetitionFormDetails('saveContinue')" class="nextBtn">Save &amp; Continue</a>
			<a href="javascript:void(0)" onclick="immigration.savePetitionFormDetails('save')" class="nextBtn">Save</a>
			<a href="javascript:void(0)" onclick="immigration.resetFormData('petitionTemplateFormDetails')">Reset</a>
			<div class="clearfix"></div>
		</div>   
   </div>
   </section>
   
 <script>
 function uploadBtnFile(id){
		  	$('#uploadFile'+id).val($("#uploadBtn"+id).val().replace(/C:\\fakepath\\/i, ''));
 }
 
 </script> 

   <script>
function checkIsReqDoc(id){
	var checked = document.getElementById("requiredCheck"+id).checked
	if(checked == true){
		$("#requiredCheck"+id).val("Y");
	}else{
		$("#requiredCheck"+id).val("N");
	}
}
</script>
   
<script>
function uploadFormDocs(id){
	fileUpload("uploadBtn"+id+"", "ImmigrationFormDocs", "petitionTemplateFormDetails","immigrationFormList["+id+"].immigrationFormFileInfo");
}

</script>
   
   <script type="text/javascript">
   function appendTemplateFormDetails(rowId){
	   var result = "";
		result = result+ "<div id='dynamicFormRow"+rowId+"' class='col-md-12 clearfix noPad mt'>";
		result = result+ "<div class='col-md-2 padding_right'>";
		result = result+ "<input type='text' id= 'formName"+rowId+"' name = 'immigrationFormList["+rowId+"].formName' class='form-control' field-name='Form Name' placeholder='Form name' data-validation = 'required validate_length length2-100' />";
		result = result+ "</div>";
		
		result = result+ "<div class='col-md-3'>";
		result = result+ " <div class='input-group col-md-12 errorInptgroup' style='margin:0 auto'>";
		result = result+ " <label class='input-group-btn'> <span class='btn btn-primary' onchange='uploadFormDocs("+rowId+");'><i class='fa fa-folder-open-o'></i>&nbsp;Browse File";
		result = result+ " <input style='display: none;' multiple onchange='uploadBtnFile("+rowId+");' id='uploadBtn"+rowId+"' class='upload' type='file'>";
		result = result+ " </span></label>";
 		result = result+ '<input type="text" class="form-control" readonly id="uploadFile'+rowId+'" name = "immigrationFormList['+rowId+'].immigrationFormFileInfo[0].docFile"  placeholder="Attachment" field-name="Attachment" data-validation="required">';
		result = result+ "</div>";		
	  	result = result+ "</div>";		
		result = result+ "<div class='col-md-5 noPad'>";
		result = result+ "<input type='text' title='Description' name='immigrationFormList["+rowId+"].formDescription' id='formDescription"+rowId+"' class='form-control' placeholder='Description' data-validation = 'validate_length length2-500' data-validation-optional='true' /></div>";
		result = result+ "<div class='col-md-2 clearfix'>";
		result = result+ "<label class='switch mt5 text-center'>";
		result = result+ "<input checked title='Is Required' onclick='checkIsReqDoc("+rowId+")' value='Y' id='requiredCheck"+rowId+"' name='immigrationFormList["+rowId+"].isRequired' type='checkbox'>";
		result = result+ "<div class='slider round'></div></label>";
		result = result+ "<div class='col-md-6 pull-right'><button type='button' class='btn btm-sm' id='btnDynamicRow"+rowId+"' onclick='removeTempFormRow("+rowId+")' ><i class='fa fa-remove text-danger'></i></button></div></div>";
		return result 
   }
	
   /* $(document).ready(function(){
	   $("#templateDocDivStatic").append(appendresult());
   }); */
   var rowId=0;
   function appendFormDetailsDiv(){
	   $("#templateFormDivStatic").append(appendTemplateFormDetails(++rowId));
   }
   
   function removeTempFormRow(id){
	   $("#dynamicFormRow"+id).remove();
   }
   	</script>
   	
   	   	<script>
$(document).ready(function(){
	$("#templateId").val(jsonObj.id);
	console.log(" getting the temp id value"+$('#templateId').val());
});
</script> 