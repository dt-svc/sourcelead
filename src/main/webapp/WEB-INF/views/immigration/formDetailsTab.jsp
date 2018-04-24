<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="immigraTabsHead">			  	
		  	<a href="javascript:void(0)" id="addForm"><i class="glyphicon glyphicon-plus"></i> Add Form</a>
		  	<ul>
		  	<li id='formZipfile'>Download All Documents</li> 
		  	</ul> 
		  	<span><input class="immigraTabSerach" id="immigraFormTabSerach" placeholder="Form Name/Status" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Form Name</th>
				<th>File</th>
				<th>Uploaded File</th>
				<th>Status</th>
				<th>Action</th>
			  </tr>
			  <c:forEach var="items" items="${formsList}" varStatus="rows">
			  <tr formid ="${items.id}" savedFormDocName = "${items.uploadFileName}">
				<td><div class="tbldata-dotdot-me">${items.formName}</div></td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">${items.fileName}</span>
				<a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile('${items.fileUrl}','${items.fileName}','${items.fileContentType}');" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="Download" download href="${items.fileUrl}"></a></div>
				</td>
				<td><div class="tbldata-dotdot-me"><span class="fileName">${!empty items.uploadFileName ? items.uploadFileName : '---'}</span>
				<c:if test="${not empty items.uploadFileName}">
				<a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile('${items.uploadedFileUrl}','${items.uploadFileName}','${items.uploadedFileContentType}');" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="Download" download href="${items.uploadedFileUrl}"></a></c:if></div>
				</td>
				<td><div class="tbldata-dotdot-sm">${items.formStatus}</div></td>
				 <td><div>
				 <a title="Upload" class="fa fa-upload uploadForm" onclick=""></a>
				 <c:if test="${items.isTemplateConfigured =='N'}">
				 <i id="edit" class="glyphicon glyphicon-pencil ml10" onclick="immigration.editImmigrationCandidateForm(${items.id});"></i>
				 <i id="delete" class="glyphicon glyphicon-trash ml10" onclick="immigration.deleteImmigrationCandidateForm(${items.id});" ></i>
				 </c:if>
				 </div>
				 </td>
			  </tr>  
			 </c:forEach>			  			  
			</tbody>
		  </table>
		</div>
		
		<!-- <div class="immigraPagi clearfix">
			<div class="immiPagiText">Showing 1 to 6 of 6 entries</div>
			  <ul class="pagination">
				<li><a href="#"> << </a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li class="disabled"><a href="#">6</a></li>
				<li data-page="last"><a href="#"> >> </a></li>
			  </ul>
		</div> -->
		<!--  popup code start-->
		<form name ="formSave_Form" id ="formSave_Form">
		<div class="popUpOut" id="viewFormpop">
		<input type = "hidden" id = "immigrationCandidateFormId" name = "immigrationCandidateFormId"/>
		
		<div class="popupHead">
			<h2>Add Form</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="closeFormPopup();"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Form name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<input type="text" class="form-control" name="formName" id="formName"  field-name="Form Name"  data-validation = "required validate_length length2-100" placeholder="Form name" data-name="formName"/>
				</div>
			</div>	      
			   
          <div class="col-sm-12 noPad mt clearfix" id="immigrationDocDiv">
				<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12 validation_p" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtnForm"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" name="docFile" id ="uploadFileForm" readonly placeholder="Attachment" field-name="Attachment" data-validation="required">
				  </div>
				</div>
			</div>	
			<div class="col-sm-12 noPad mt clearfix" id="immigrationEditDocDiv" style="display: none">
			<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
			 <div class="col-md-8">
				  <label id="immigrationDocumentName" name="immigrationDocumentName" class="text-primary tbldata-dotdot-200 pull-left" title="" >hh</label>&nbsp;&nbsp;<span id="delete_immiDocFile" onclick="removeImmigDoc();"><i class="fa fa-trash removeIcon text-danger cursorPointer pull-right"></i></span>
				<input type="hidden" id="immigrationDocumentExisitngFile" name="immigrationDocumentExisitngFile" value="">
				  </div>			          	
			</div>
		
		<!-- <div class="col-md-5 noPad">
		
                  <input type="text" class="form-control" name="formDescription" id="formDescription" placeholder="Description" data-validation = "validate_length length2-100" data-validation-optional="true"/>
            </div> -->
             <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Description</label>
              	<div class="col-md-8 padding_right">
					<textarea rows="2" class="form-control" id= "formDescription" name = "formDescription"></textarea>
				</div>
			</div>	
		<div class="col-sm-12 noPad mt clearfixclearfix">
           <div class="col-md-3 pull-right" >
             <label class="switch mt5 text-center pull-right">
               <input checked id="isRequired" name="isRequired" title="Is Required" type="checkbox" onclick="isReqCheckForm();" value="Y">
                  <div class="slider round"  title="Is Required" ></div>
                 </label>
              </div>				  
        </div>
        </div>
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" onclick="immigration.saveCandidateForms();">
            <input value="Cancel" class="popCan" type="button" onclick="closeFormPopup();">
          </div>		
	</div>
	</form>
	<form id = "uploadRequestForm_Form" name = "uploadRequestForm_Form" >
	<input type= "hidden" id ="uploadFormIdSave" value=""/>
	<div class="popUpOut" id="uploadFormPop">
		<div class="popupHead">
			<h2>Upload file</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="removeFormPopUp();"></a>
		</div>
		
		<div class="popupMid popupMidHeight clearfix">
         
          <div class="mt row clearfix" id = "uploadFormDiv">
				<label class="col-md-2">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-10">
					<div class="input-group col-md-12 validation_p" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadFormBtn"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFormFile" field-name = "Document" placeholder="Upload Form ..." data-validation="required">
				  </div>
				</div>
			</div>		
			
			<div class="col-sm-12 noPad mt clearfix" id="uploadFormDivEditDocDiv" style="display: none">
			<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
			 <div class="col-md-8">
				  <label id="uploadFormDocumentName" name="uploadFormDocumentName" class="text-primary tbldata-dotdot-200 pull-left" title=""></label>&nbsp;&nbsp;<span id="delete_immiDocFile" onclick="removeImmigFormDoc1();"><i class="fa fa-trash removeIcon text-danger cursorPointer pull-right"></i></span>
				<input type="hidden" id="uploadFormDocumentNameDocumentExisitngFile" name="uploadFormDocumentNameDocumentExisitngFile" value="">
				  </div>			          	
			</div>			          	
		
		</div>
		<div class="immiPopFoot">
            <input value="Save" id="uploadFormSave"  class="" type="button" onclick="uploadRequestForm();">
            <input value="Cancel" class="popCan" type="button" onclick="removeFormPopUp();">
          </div>		
	</div>	<!-- popup code end -->
	</form>
		<script>
		$(function() {
			$("#uploadBtnForm").change(function() {
			fileUpload("uploadBtnForm", "immigrationCandidateForms", "formSave_Form","immigrationFormFileInfo");
		});
		});
		
		$(function() {
			$("#uploadFormBtn").change(function() {
			fileUpload("uploadFormBtn", "immigrationCandidateForms", "uploadRequestForm_Form","immigrationFormFileInfo");
		});
		});
		
		document.getElementById("uploadBtnForm").onchange = function () {
		 	$('#uploadFileForm').val($(this).val().replace(/C:\\fakepath\\/i, ''))

		}; 
		document.getElementById("uploadFormBtn").onchange = function () {
		 	$('#uploadFormFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))

		}; 
		
		 function isReqCheckForm() {
		        var checked = document.getElementById("isRequired").checked
		        console.log("checked value is ..->"+checked);
		        if (checked == true) {
		            $("#isRequired").val("Y");
		        } else {
		            $("#isRequired").val("N");
		        }
		    }
		// close the popup.
			function closeFormPopup() {
				$(".popMask").fadeOut(200);
				$("#viewFormpop").fadeOut(100);
				$("#formSave_Form")[0].reset();
				$("#immigrationCandidateFormId").val('');
				$("#formDescription").val('');
				//$("#isRequired").prop("checked",false);
				$('#formSave_Form input[name="isRequired"]').prop('checked', false);
				//$('#candidate_Immigration_Doc input[name="isRequired"]').prop('checked', false);
				removeImmigDoc();
				
			}
			// Add Form PopUp
			$("#addForm").click(function() {
				$(".popMask").fadeIn(200);
				$("#viewFormpop").fadeIn(100);
			});
			// upload Form PopUp
			$(".uploadForm").click(function() {
				$(".popMask").fadeIn(200);
				$("#uploadFormPop").fadeIn(100);
			});
			$(".uploadForm").click(function(){
				$(".popMask").fadeIn(200);
				$("#uploadFormPop").fadeIn(100);
				
				$("#uploadFormIdSave").val($(this).closest('tr').attr("formid"));
				var fileName = $(this).closest('tr').attr("savedFormDocName");
				$("#uploadFormDocumentName").text(fileName);
				if(fileName !='' && fileName !=null){
					$("#uploadFormDiv").hide();
					$("#uploadFormDivEditDocDiv").show();
				}else{
					$("#uploadFormDiv").show();
					$("#uploadFormDivEditDocDiv").hide();
				}
								
			});
			function removeFormPopUp(){
				$(".popMask").fadeOut(200);
				$("#uploadFormPop").fadeOut(100);
			}
			
			$("#immigraFormTabSerach").keyup(function (e) {
			    if (e.which === 13) {
			    	var searchVal = $("#immigraFormTabSerach").val();
			    	immigration.displayImmigrationCandidateForms(candidateId,templateId,immigrationId,searchVal);
			    }
			});
			</script>
			
			
<script type="text/javascript">
function removeImmigDoc(){
	$("#immigrationEditDocDiv").hide();
	$("#immigrationDocDiv").show();
	$('#uploadFileForm').attr("data-validation", "required");
}

function removeImmigFormDoc1(){
	
	$("#uploadFormDivEditDocDiv").hide();
	$("#uploadFormDiv").show();
	$('#uploadFormFile').attr("data-validation", "required");
}


$("#formZipfile").click(function() {
	if(immigrationId == 0)
		return;
	$.post("downloadImmigrationFormZip",{immigrationId: immigrationId}, function(data) {//  passing empty value for docId to do immigration level zip download
		if(data !== "" && data.indexOf("Documents Found") === -1){
			location.href = '/app/downloadFile.html?file='+encodeURI(data); // encodeURI used for encoding file path in request parameter.
		}
		else if(data !== "" && data.indexOf("immigrationId Found") !== -1){
			alert(data);
		}
		else{
			alert("Unable to locate logs for this upload");
		}
	});
});
</script>