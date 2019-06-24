<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var ="allFormsUploaded" value ="No"/>
		<div class="immigraTabsHead">
			<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <a href="javascript:void(0)" id="addForm"><i class="glyphicon glyphicon-plus"></i> Add Form</a>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <a href="javascript:void(0)" id="addForm"><i class="glyphicon glyphicon-plus"></i> Add Form</a>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
			</c:choose>			  	
		  	<span id='formZipfile' class="cursorPointer"><i class="glyphicon glyphicon-download-alt paddingRight"></i>Download All Documents</span> 
		  	<span><input class="immigraTabSerach" id="immigraFormTabSerach" placeholder="Form Name/Status" type="text"></span>
		</div>
	  	<div class="immigraTable">
		  <table id="formsTbl">
			<tbody>
			  <tr>
				<th>Form Name</th>
				<th>Required</th>
				<th>File</th>
				<th>Uploaded File</th>
				<th>Status</th>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <th>Action</th>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <th>Action</th>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
			  </tr>
			  <c:forEach var="items" items="${formsList}" varStatus="rows">
			  <tr formid ="${items.id}" savedFormDocName = "${items.uploadFileName}">
				<td  title="${items.formName}"><div class="tbldata-dotdot-vsm">${items.formName}</div></td>
				<td title="${items.isRequired == 'Y' ? 'YES':'NO'}"><div class="tblminwidth-50">${items.isRequired == 'Y' ? "YES":"NO"}</div></td>
				<td title="${items.fileName}"><div class="tbldata-dotdot-vsm"><span class="fileName">${items.fileName}</span>
				<a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile('${items.fileUrl}','${items.fileName}','${items.fileContentType}');" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="Download" download href="${items.fileUrl}"></a></div>
				</td>
				<td title="${items.uploadFileName}"><div class="tbldata-dotdot-vsm"><span class="fileName">${!empty items.uploadFileName ? items.uploadFileName : '---'}</span>
				<c:if test="${not empty items.uploadFileName}">
				<a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile('${items.uploadedFileUrl}','${items.uploadFileName}','${items.uploadedFileContentType}');" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" title="Download" download href="${items.uploadedFileUrl}"></a></c:if></div>
				</td>
				<td title="${items.formStatus}"><div class="tblminwidth-50">${items.formStatus}</div></td>
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <td><div>
							 <a title="Upload" class="fa fa-upload uploadForm" onclick=""></a>
							 <c:if test="${items.isTemplateConfigured =='N'}">
							 <i id="edit" class="glyphicon glyphicon-pencil ml10 cursorPointer" onclick="immigration.editImmigrationCandidateForm(${items.id});"></i>
							<i id="delete" class="glyphicon glyphicon-trash ml10 cursorPointer" onclick="deleteImmigrationRecord('immigration.deleteImmigrationCandidateForm',${items.id});" ></i>
							 </c:if>
							 </div>
							 <c:if test="${items.isRequired == 'Y' and not empty items.uploadedFileUrl}">
							 <c:set var ="allFormsUploaded" value ="Yes"/>
							 </c:if>
							 </td>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <td><div>
								 <a title="Upload" class="fa fa-upload uploadForm" onclick=""></a>
								 <c:if test="${items.isTemplateConfigured =='N'}">
								<i id="edit" class="glyphicon glyphicon-pencil ml10 cursorPointer" onclick="immigration.editImmigrationCandidateForm(${items.id});"></i>
							   <i id="delete" class="glyphicon glyphicon-trash ml10 cursorPointer" onclick="deleteImmigrationRecord('immigration.deleteImmigrationCandidateForm',${items.id});" ></i>
								
								 </c:if>
								 </div>
								 <c:if test="${items.isRequired == 'Y' and not empty items.uploadedFileUrl}">
								 <c:set var ="allFormsUploaded" value ="Yes"/>
								 </c:if>
								 </td>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>				
			  </tr>  
			 </c:forEach>
			  <c:if test="${empty formsList}">
				<tr>
				 <td colspan="6" valign="top" class="dataTables_empty"></td>
				</tr>
				</c:if>			  			  
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

          <div class="col-sm-12 noPad mt clearfix" id="errformNameDiv">
				<label class="col-md-4 padding_left">Form name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<input type="text" class="form-control" name="formName" id="formName"  field-name="Form Name"  data-validation = "required validate_length length2-100" placeholder="Form name" data-name="formName" onchange="immigration.validateImmigrationCandidateForms();" onkeyup ="immigration.validateImmigrationCandidateForms();"/>
				</div>
			</div>	      
			   
          <div class="col-sm-12 noPad mt clearfix" id="immigrationDocDiv">
				<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12 validation_p" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtnForm"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" name="docFile" id ="uploadFileForm" readonly placeholder="Attachment" field-name="Attachment" data-validation="required validatepdf">
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
					<textarea rows="2" class="form-control" id= "formDescription" field-name = "Description" name = "formDescription" data-validation="required validate_Space validate_length length2-250" data-validation-optional="true"></textarea>
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
            <input value="Save" class="" type="button" id ="canFormSave_Button" onclick="immigration.saveCandidateForms();">
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
					<input type="text" class="form-control" readonly id="uploadFormFile" field-name = "Document" placeholder="Upload Form ..." data-validation="required validatepdf">
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
            <input value="Save" id="uploadFormSave_Button"  class="" type="button" onclick="immigration.uploadRequestForm();">
            <input value="Cancel" class="popCan" type="button" onclick="removeFormPopUp();">
          </div>		
	</div>	<!-- popup code end -->
	<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#familiesTab');" class="btn btn-default btn-sm">
             	<!-- <input type="button" value="Skip" onclick="goToSummaryTab('#docsTab');" class="btn btn-default btn-sm">  -->
              	<input type="button" value="Continue" onclick="goToSummaryNextTab('#docsTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>
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
				$("#formDescription").text('');
				//$("#isRequired").prop("checked",false);
				$('#formSave_Form input[name="isRequired"]').prop('checked', false);
				//$('#candidate_Immigration_Doc input[name="isRequired"]').prop('checked', false);
				$('#errformNameDiv').find('p.jquery_form_error_message').remove(); 
				$('#immigrationDocDiv').find('p.jquery_form_error_message').remove(); 
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
				$('#uploadRequestForm_Form').find('p.jquery_form_error_message').remove(); 
			}
			
			$("#immigraFormTabSerach").keyup(function (e) {
			    if (e.which === 13) {
			    	var searchFormVal = $("#immigraFormTabSerach").val();
					if(searchFormVal) searchFormVal = searchFormVal.trim();
			    	immigration.displayImmigrationCandidateForms(candidateId,templateId,immigrationId,searchFormVal,'${curUscisMyImmi}');
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
	$('#uploadFormFile').attr("data-validation", "required validatepdf");
}


$("#formZipfile").click(function() {
	if(immigrationId == 0)
		return;
	$.post("downloadImmigrationFormZip",{immigrationId: immigrationId}, function(data) {//  passing empty value for docId to do immigration level zip download
		if(data !== "" && data.indexOf("Documents Found") === -1){
			location.href = '${SourceLeadWeb_sourceleadUrl}/downloadFile.html?file='+encodeURI(data); // encodeURI used for encoding file path in request parameter.
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
<script>
//$(document).ready(function(){
  var allFormsUploaded = '<c:out value ="${allFormsUploaded}"/>';
  console.log("allFormsUploaded -->"+allFormsUploaded);
	if(allFormsUploaded == 'Yes'){
	        $('#allFormsfilled').show() // show and hides circle icon.
	 }
    else {
	        $('#allFormsfilled').hide()
     }
//});	
</script>