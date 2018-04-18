	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<div class="immigraTabsHead">
		<!-- <a href="javascript:void(0)" id="immDocu"><i class="glyphicon glyphicon-plus"></i> Request Document</a> 		 -->	  	
		  	<a href="javascript:void(0)" id="addDocu"><i class="glyphicon glyphicon-plus"></i> Add Document</a> 
		  	<span><input class="immigraTabSerach" id="immigraDocTabSerach" placeholder="Document Type/Description/Status" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
		  <table>
			<tbody>
			  <tr>
				<th>Document Type</th>
				<th>Required</th>
				<th>Document</th>
				<th>Status</th>
				<th>Description</th>
				<th>Action</th>
			  </tr>
			  <c:forEach var="canDocList" items="${immigrationCanDocList}" varStatus="rows">			  			  
			<tr docid="${canDocList.id}" allowMultpleDocs= "${canDocList.allowMultiple}">
				<td><div class="tbldata-dotdot-vsm">${canDocList.documentTypeName}</div></td>
				<td><div class="tbldata-dotdot-vsm">${canDocList.isRequired == 'Y' ? "YES":"NO"}</div></td>
				<c:if test="${!empty canDocList.upldedFileName}">
				<td><div class="tbldata-dotdot-me"><span class="fileName">${canDocList.upldedFileName}</span>
					<a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile('${canDocList.uplodedFileUrl}','${canDocList.upldedFileName}','${canDocList.upldedFileContentType}');" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" download href="${canDocList.uplodedFileUrl}" title="	Download"></a></div>
				</td>
				</c:if>
				<c:if test="${empty canDocList.upldedFileName}">
				<td><div class="tbldata-dotdot-me"><span class="fileName">--</span>
					</div>
				</td>
				</c:if>
				<td><div class="tbldata-dotdot-vsm">${canDocList.documentStatus}</div></td>
				<td><div class="tbldata-dotdot-big">${canDocList.description}</div></td>
				<!-- <td class="immigraAction">
					<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
					<div class="mmigraActionInn">
						<span>
							<a href="javascript:void(0)" title="Upload" class="fa fa-upload uploadForm"></a>
							<a title="Upload" class="fa fa-upload uploadForm"></a>
							<a href="javascript:void(0)" title="Remove" class="fa fa-trash"></a>
							<a href="javascript:void(0)" title="Edit" class="fa fa-pencil"></a>
						</span>
					</div>
				</td> -->
				<td>
				<div class="tblminwidth-90">
					<a id= "upload" title="Upload" class="fa fa-upload uploadForm"></a>
					<c:if test="${canDocList.isTemplateConfigured eq 'N'}">
					<i id="edit" class="glyphicon glyphicon-pencil ml10" onclick="editDocumentDetailsByRowId(${canDocList.id});"></i>
					<i id="delete" class="glyphicon glyphicon-trash ml10" onclick="deleteDocumentDetailsByRowId(${canDocList.id});" ></i>
				 </c:if>
					</div>
				</td>
			  </tr>  
		</c:forEach>
			  						
			</tbody>
		  </table>
		</div>
		
		
		<!--  popup code start -->
		<form id= "candidate_Immigration_Doc" name = "candidate_Immigration_Doc">
		<div class="popUpOut" id="docuView">
		 <input type = "hidden" id= "immigrationCandidateDocId" name="immigrationCandidateDocId"/> 
	 	<%-- <input type = "text" id= "immigrationcandidateId" name = "immigrationcandidateId" value = "${candidateId}">
		<input type = "text" id= "immigrationId" name = "immigrationId" value = "${immigrationId}">
		<input type = "text" id= "immigrationTempplateId" name = "immigrationTempplateId" value = "${templateId}"> --%>
		<div class="popupHead">
			<h2>Add Document</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="removeDocPopUp();"></a>
		</div>
		
		<div class="popupMid clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Document Type<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="chosen-select form-control" name = "documentType" field-name="Document Type" data-validation="required" id = "documentType">
					  <option value = "">Select Document Type</option>
					   <c:forEach var="docs" items="${documentsList}">
											<option value="${docs.id}">${docs.name}</option>
										</c:forEach>
					</select>
				</div>
			</div>
          		          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Description</label>
              	<div class="col-md-8 padding_right">
					<textarea rows="2" class="form-control" id= "description1" name = "description"></textarea>
				</div>
			</div>	
			
						   <div class="col-md-4 col-md-offset-4">
						   	<div class="mt5 clearfix">
							   <input id="multipleCheckBox123" title="Allow multiple Docs" name="multiple"  type="checkbox" value = "Y" onclick = "allowMultipleDocs();">
							   <label class="padding_left">Allow Multiple</label>
							</div>
						   </div>
						   <div class="clearfix"></div>
						   <div class="col-sm-12 noPad mt clearfixclearfix">
						   <div class="col-md-3 pull-right">
							<label class="switch mt5 text-center pull-right">
							<input id="requiredCheck" name="isRequired" title="Is Required" type="checkbox" value = "Y" onclick = "imgDocReq();">
							<div class="slider round" title="Is Required"></div>
							</label>
						  </div>	
						  </div>
						  
				  <!-- <div class="col-md-4">
				<div class="mt5 ml5 clearfix">
				  <input id="multipleCheck" title="Allow multiple Docs" name="multiple" value = "N" onclick = "tempDocCheckBox()" type="checkbox">
				  </div>
				 </div> -->	
			
			<!-- <div class="col-sm-12 noPad mt clearfixclearfix">
				<label class="col-md-4 padding_left">Document<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<div class="input-group col-md-12" style="margin:0 auto">
					<label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn"  class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Resume ...">
				  </div>
				</div>
			</div>	 -->			
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" onclick="saveCandidateDocs();">
            <input value="Cancel" class="popCan" onclick="removeDocPopUp();" type="button">
          </div>		
	</div>
	</form>
	<!-- <div class="popUpOut popUpOutBig" id="docuImportView">
		<div class="popupHead">
			<h2>Request Document</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix mt">		
			<table class="table table-responsive table-bordered table-hover noMar">
				<tbody>
					<tr>
						<td width="200px">
							<select class="chosen-select form-control">
							  <option>Select Document Type</option>
							  <option>Document-1</option>
							  <option>Document-2</option>
							  <option>Document-3</option>
							</select>
						</td>
						<td width="auto"><textarea rows="2" placeholder="Description" class="form-control"></textarea></td>
						<td width="120px">
							<label class="switch mt10 ml10 pull-left"><input checked="" type="checkbox"><div class="slider round"></div></label>
							<div class="pull-right"><button type="button" class="btn btm-sm pull-right mt5"><i class="fa fa-plus text-primary"></i></button></div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" onclick="saveCandidateDocs();">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div> -->
	<form id = "uploadRequestDoc_Form" name = "uploadRequestDoc_Form" >
	<input type= "hidden" id ="uploadIdSave" value=""/>
	<div class="popUpOut" id="uploadCanDocPop">
		<div class="popupHead">
			<h2>Upload file</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="removeDocPopUp12();"></a>
		</div>
		
		<div class="popupMid popupMidHeight clearfix">
 
          <div class="row mt clearfix" id ="dynamicUplodDiv">
				<label class="col-md-2">File<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-10">
					<div class="row">
					<div class="col-md-10 paddingRight">
					<div class="input-group">
					<label class="input-group-btn"> <span class="btn btn-primary" onchange="uploadRerqDocsList(0);"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn0" onchange="uploadBtnFilesList(0);" class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile0" name = "docFile0" placeholder="Upload Resume ...">
					 </div>
					 </div>
					 <div id="divAddButton" class="col-md-2" style="display:none">
						<button type="button" id="btnDynamicRow0" onclick="appendDynamicUploaRow()" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
				  </div>
				  </div>
				</div>
			</div>					          	
	
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" id="uploadDocSave" class="" type="button" onclick="uploadRequestDoc();">
            <input value="Cancel" class="popCan"  onclick="removeDocPopUp12();" type="button">
          </div>		
	</div>	<!--  popup code end  -->
	</form>	
	<div class="comMask"></div>
		<script>
		
			 // Import Document PopUp
			$(".uploadForm").click(function(){
				$("#uploadCanDocPop").fadeIn(100);
				$(".comMask").fadeIn(100);

                 var allowMultiple = $(this).closest('tr').attr("allowMultpleDocs");
				$("#uploadIdSave").val($(this).closest('tr').attr("docid"));
				console.log("allowMultiple ---">+allowMultiple);
				if(allowMultiple=="Y"){
					$('#divAddButton').show();
				}else{
					$('#divAddButton').hide();
				}
				
				
				
			});
			 // Add Document PopUp
			$("#addDocu").click(function(){
				$(".popMask").fadeIn(200);
				$("#docuView").fadeIn(100);
			});
			
		</script>
		
<script type="text/javascript">
	function allowMultipleDocs(){
		var checked = document.getElementById("multipleCheckBox").checked
		if(checked == true){
			$("#multipleCheckBox").val("Y");
		}else{
			$("#multipleCheckBox").val("N");
		}
	}
	
	function uploadBtnFilesList(id) {
	 	$('#uploadFile'+id).val($("#uploadBtn"+id).val().replace(/C:\\fakepath\\/i, ''))
		
	}
	

	function imgDocReq(){
		var checked = document.getElementById("requiredCheck").checked
		if(checked == true){
			$("#requiredCheck").val("Y");
		}else{
			$("#requiredCheck").val("N");
		}
	}

	</script>		
<!-- <script type="text/javascript">
function uploadDoc(id){
	uploadRequestDoc(id);
	alert("ggggg"+id)
}
</script> -->
<script type="text/javascript">
function uploadRerqDocsList(id) {
	fileUpload("uploadBtn"+id+"", "immigrationCandidateDocuments", "uploadRequestDoc_Form");
}
</script>
		
<script type="text/javascript">

function removeDocPopUp12(){
	$(".popMask").fadeOut(200);
	$("#uploadCanDocPop").fadeOut(100);
	$(".comMask").fadeOut(100);
}

function removeDocPopUp(){
	$(".popMask").fadeOut(200);
	$("#docuView").fadeOut(100);
	$("#candidate_Immigration_Doc")[0].reset();
	$("#description1").val('');
	$("#immigrationCandidateDocId").val('');
	$('#candidate_Immigration_Doc input[name="multiple"]').prop('checked', false);
	$('#candidate_Immigration_Doc input[name="isRequired"]').prop('checked', false);
	
}
/* $(function() {
	$("#uploadBtn"+id).change(function() {
	fileUpload("uploadBtn"+id, "immigrationCandidateDocuments", "uploadRequestDoc_Form");
});
}); */
var rowId =1;
function prepareDynamicUploadRow(rowId){
	
	  var dynamicUploadRowHtml = "";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<div class='clearfix'></div>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<div id='dynamicRow"+rowId+"' class='mt clearfix'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<label class='col-md-2'>File<span class='text-danger'>&nbsp;*</span></label>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<div class='col-md-10'>";
	  
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<div class='row'>";

	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<div class='col-md-10 paddingRight'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"	<div class='input-group'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"	<label class='input-group-btn'> <span class='btn btn-primary' onchange='uploadRerqDocsList("+rowId+")'><i class='fa fa-folder-open-o'></i>&nbsp;Browse File";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"	<input type='file' style='display:none'; onchange='uploadBtnFilesList(" + rowId + ");' multiple id='uploadBtn"+rowId+"'  class='upload'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"		  </span> </label>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"		<input type='text' class='form-control' readonly id='uploadFile"+rowId+"' name = 'docFile"+rowId+"' placeholder='Upload Resume ...'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"			  </div>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"			  </div>";
	  
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"				 <div id='divAddButton' class='col-md-2'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<button type='button' class='btn btm-sm' id='btnDynamicRow"+rowId+"' onclick='removeDynamicUploaRow("+rowId+")' ><i class='fa fa-remove text-danger'></i></button>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"			  </div>";
	  
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"			  </div>";
	  
	  
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"	  </div>"; 
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"	  </div>";
	  console.log(dynamicUploadRowHtml);
	 return dynamicUploadRowHtml;
}
function appendDynamicUploaRow(){
	   $("#dynamicUplodDiv").append(prepareDynamicUploadRow(rowId++)); 
}
function removeDynamicUploaRow(id){
	   $("#dynamicRow"+id).remove();
	   rowId--;// decrement rowid when deleted in UI.
}
$("#immigraDocTabSerach").keyup(function (e) {
    if (e.which === 13) {
    	var searchVal = $("#immigraDocTabSerach").val();
    	displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,searchVal);
    }
});

</script>		