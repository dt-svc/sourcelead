	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<c:set var ="allDocsUploaded" value ="No"/>
		<div class="immigraTabsHead">
		<!-- <a href="javascript:void(0)" id="immDocu"><i class="glyphicon glyphicon-plus"></i> Request Document</a> 
				 -->
			<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <a href="javascript:void(0)" id="addDocu"><i class="glyphicon glyphicon-plus"></i> Add Document</a>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <a href="javascript:void(0)" id="addDocu"><i class="glyphicon glyphicon-plus"></i> Add Document</a>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
			</c:choose>	  	
		  	<span id='docZipfile' class="cursorPointer"><i class="glyphicon glyphicon-download-alt paddingRight"></i>Download All Documents</span> 
		  	
		  	<span><input class="immigraTabSerach" id="immigraDocTabSerach" placeholder="Document Type/Description/Status" type="text"></span>
		</div>
		
	  	<div class="immigraTable overflow_Auto">
	  	<!-- <ul>
		<li id='docZipfile'>Download All Documents</li> 
	</ul> -->
		  <table id="documentsTbl">
			<tbody>
			  <tr>
				<th>Document Type</th>
				<th>Description</th>
				<th>Required</th>
				<th>Document</th>
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
			  <c:forEach var="canDocList" items="${immigrationCanDocList}" varStatus="rows">			  			  
		    	<tr docid="${canDocList.id}" allowMultpleDocs= "${canDocList.allowMultiple}" docsHistoryList = "${fn:escapeXml(canDocList.docsHistoryList)}">
					
				<c:choose>
				  <c:when test="${myImmigrationPage == 'Y'}">
					<td title="${canDocList.documentTypeName}"><div class="tbldata-dotdot-vsm">${canDocList.documentTypeName}</div></td>
					<td title = "${canDocList.description}"><div class="tbldata-dotdot-me">${canDocList.description}</div></td>
				</c:when>
				
				 <c:otherwise>
					<td title="${canDocList.documentTypeName}"><div class="tbldata-dotdot-vsm">${canDocList.documentTypeName}</div></td>
					<td title = "${canDocList.description}"><div class="tbldata-dotdot-me">${canDocList.description}</div></td>
				</c:otherwise>
				
				</c:choose>
				
				<%-- <c:if test = "${petitionerDocs == 'PetitionerCategory'}">      
					<td title="${canDocList.documentTypeName}"><div class="tbldata-dotdot-vsm">${canDocList.documentTypeName}</div></td>
					<td title = "${canDocList.description}"><div class="tbldata-dotdot-me">${canDocList.description}</div></td>
				</c:if>
				
				<c:if test = "${attorneyDocs == 'AttorneyCategory'}">      
					<td title="${canDocList.documentTypeName}"><div class="tbldata-dotdot-vsm">${canDocList.documentTypeName}</div></td>
					<td title = "${canDocList.description}"><div class="tbldata-dotdot-me">${canDocList.description}</div></td>
				</c:if> --%>
				
				<td title = "${canDocList.isRequired == 'Y' ? 'YES':'NO'}"><div class="tblminwidth-50">${canDocList.isRequired == 'Y' ? "YES":"NO"}</div></td>
				<c:if test="${!empty canDocList.upldedFileName}">
				<td>
					<c:choose>
					<c:when test = "${fn:contains(canDocList.docsHistoryList, ',')}">
					<div class="tbldata-dotdot-me"><span class="fileName" title="More Records">${canDocList.upldedFileName}.....</span>
					<a class="fa fa-file-archive-o text-warning" onclick ="downlaodZipfileByDocId(${canDocList.id});" title="Download Zip File"></a></div>
					</c:when>
					<c:otherwise>
					<div class="tbldata-dotdot-me"><span class="fileName">${canDocList.upldedFileName}</span>
					<a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile('${canDocList.uplodedFileUrl}','${canDocList.upldedFileName}','${canDocList.upldedFileContentType}');" title="View"></a>
					<a class="glyphicon glyphicon-download-alt" download href="${canDocList.uplodedFileUrl}" title="Download"></a></div>
					</c:otherwise>
					</c:choose>
				</td>
				</c:if>
				<c:if test="${empty canDocList.upldedFileName}">
				<td><div class="tbldata-dotdot-me"><span class="fileName">--</span>
					</div>
				</td>
				</c:if>
				<td title ="${canDocList.documentStatus}"><div class="tblminwidth-50">${canDocList.documentStatus}</div></td>
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
				
				<c:choose>
				    <c:when test="${myImmigrationPage == 'N'}">
				        <td>
							<div class="tblminwidth-90">
								<a id= "upload" title="Upload" class="fa fa-upload uploadForm"></a>
								<c:if test="${canDocList.isTemplateConfigured eq 'N'}">
								<i id="edit" class="glyphicon glyphicon-pencil ml10 cursorPointer" onclick="immigration.editDocumentDetailsByRowId(${canDocList.id});"></i>
								<i id="delete" class="glyphicon glyphicon-trash ml10 cursorPointer" onclick="deleteImmigrationRecord('immigration.deleteDocumentDetailsByRowId',${canDocList.id});" ></i>
							 </c:if>
								</div>
							</td>
							<c:if test="${canDocList.isRequired == 'Y' and not empty canDocList.uplodedFileUrl}">
								 <c:set var ="allDocsUploaded" value ="Yes"/>
								 </c:if>
				    </c:when>
				    <c:otherwise>
				        <c:choose>
						    <c:when test="${curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications'}">
						        <td>
									<div class="tblminwidth-90">
										<a id= "upload" title="Upload" class="fa fa-upload uploadForm"></a>
										<c:if test="${canDocList.isTemplateConfigured eq 'N'}">
										<i id="edit" class="glyphicon glyphicon-pencil ml10 cursorPointer" onclick="immigration.editDocumentDetailsByRowId(${canDocList.id});"></i>
										<i id="delete" class="glyphicon glyphicon-trash ml10 cursorPointer" onclick="deleteImmigrationRecord('immigration.deleteDocumentDetailsByRowId',${canDocList.id});" ></i>
									 </c:if>
										</div>
									</td>
									<c:if test="${canDocList.isRequired == 'Y' and not empty canDocList.uplodedFileUrl}">
										 <c:set var ="allDocsUploaded" value ="Yes"/>
										 </c:if>
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
				    </c:otherwise>
				</c:choose>
			  </tr>  
		</c:forEach>
			<c:if test="${empty immigrationCanDocList}">
			<tr>
				<td colspan="5" class="dataTables_empty" valign="top"></td>
			</tr>
		   </c:if>				
			</tbody>
		  </table>
		</div>
		
		
		<!--  popup code start -->
		<form  name="candidate_Immigration_document_form"  id="candidate_Immigration_document_form"  method="POST">
		<div class="popUpOut" id="docuView">
		 <input type = "hidden" id= "immigrationCandidateDocId" name="immigrationCandidateDocId"/> 
	 	<%-- <input type = "text" id= "immigrationcandidateId" name = "immigrationcandidateId" value = "${candidateId}">
		<input type = "text" id= "immigrationId" name = "immigrationId" value = "${immigrationId}">
		<input type = "text" id= "immigrationTempplateId" name = "immigrationTempplateId" value = "${templateId}"> --%>
		<div class="popupHead">
			<h2>Add Document</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="removeDocPopUp();"></a>
		</div>
		
		<div class="clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Document Type<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right">
					<select class="selectpicker width100percnt" name = "documentType" field-name="Document Type" data-validation="required" data-live-search="true" id="documentType" onchange="getDocumentTypeDiv(this);">
					  <option value = "">Select Document Type</option>
					   <c:forEach var="docs" items="${documentsList}">
											<option value="${docs.id}">${docs.name}</option>
										</c:forEach>
					</select>
				<div class="clearfix"></div>
			<div class="clearfix mt" id="documentOtherTypeDiv" style="display:none">
				<input type="text" class="form-control" name="otherType"  id="documentOtherType" value="" placeholder="Document Other Type" field-name="Document Other Type" >
			</div>
				</div>
			</div>
			
			
          		          
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Description</label>
              	<div class="col-md-8 padding_right">
					<textarea rows="2" class="form-control" id= "description1" name = "description" field-name = "Description" data-validation="required validate_Space validate_length length2-250" data-validation-optional="true"></textarea>
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
				
		
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id = "canDocSave_Button1" onclick="immigration.saveCandidateDocs();">
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
				
              	<div class="col-md-10" id= "uploadDocSaveDiv0">
              	<div class="row">
              	<label class="col-md-2">File<span class="text-danger">&nbsp;*</span></label>
					<div class="col-md-10 paddingRight">
					<div class="input-group validation_p">
					<label class="input-group-btn"> <span class="btn btn-primary" onchange="uploadRerqDocsList(0);"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
					  <input type="file" style="display: none;" multiple id="uploadBtn0" onchange="uploadBtnFilesList(0);" class="upload">
					  </span> </label>
					<input type="text" class="form-control" readonly id="uploadFile0" name = "docFile0" field-name ="Document" data-validation = "required validatepdf" placeholder="Upload Document ...">
					 </div>
					 </div>
					 </div>
				</div>
				
				<div class="col-md-10" id="uploadDocEditDiv0" style="display: none">
				<div class="row">
			<label class="col-md-2">File<span class="text-danger">&nbsp;*</span></label>
			 <div class="col-md-10 paddingRight">
				  <label id="uploadDocumentName0" name="uploadDocumentName0" class="text-primary tbldata-dotdot-custom pull-left" title=""></label>&nbsp;&nbsp;<span id="delete_immiDocFile" onclick="removeImmigDocumrntDynamic(0);"><i class="fa fa-trash removeIcon text-danger cursorPointer pull-right"></i></span>
				<input type="hidden" id="uploadDocumentNameExisitngFile" name="uploadDocumentNameExisitngFile" value="">
				
				  </div>
				  </div>			          	
			</div>	
			 <input type="hidden" id="docHisoryId0" name="immigrationCandidateDocHistoryId[0]" value="">
			<div id="divAddButton" class="col-md-2" style="display:none">
						<button type="button" id="btnDynamicRow0" onclick="appendDynamicUploaRow()" class="btn btm-sm"><i class="fa fa-plus text-primary"></i></button>
				  </div>
			</div>					          	
	
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" id="uploadDocSave_Button" class="" type="button" onclick="immigration.uploadRequestDoc();">
            <input value="Cancel" class="popCan"  onclick="removeDocPopUp12();" type="button">
          </div>		
	</div>	<!--  popup code end  -->
	<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#formsTab');" class="btn btn-default btn-sm">
             	<!-- <input type="button" value="Skip" onclick="goToSummaryTab('#notesTab');" class="btn btn-default btn-sm">  -->
             	<c:if test ="${myImmigrationPage =='Y' and (curUscisMyImmi == 'Request to fill' || curUscisMyImmi == 'Request for modifications')}">
              	<input type="button" value="Submit" onclick=" checkRequiredDocAndContinue();" class="btn btn-primary btn-sm ml10">
              	</c:if> 
            </div>
		</div>
	</form>	
	<div class="comMask"></div>
	
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
			
function removeImmigDocumrntDynamic(id){
	$("#uploadDocSaveDiv"+id).show();
	$("#uploadDocEditDiv"+id).hide();
	$('#uploadDocumentName'+id).val('');
	$("#uploadFile"+id).removeAttr("data-validation");
	$("#uploadFile"+id).removeAttr("data-validation-optional");
	$("#uploadFile"+id).attr("data-validation", "required validatepdf");
	
}
</script>
		<script>
		
			 // Import Document PopUp
			$(".uploadForm").click(function(){
				$("#uploadCanDocPop").fadeIn(100);
				$(".comMask").fadeIn(100);
				 var fileNames ='';
                 var allowMultiple = $(this).closest('tr').attr("allowMultpleDocs");
				$("#uploadIdSave").val($(this).closest('tr').attr("docid"));
				 var jsonfileNamesObj =  $(this).closest('tr').attr("docsHistoryList"); 
				/*  
				if(fileNamesString!=''){
				  fileNames = fileNamesString.split(",");
			    } */
			    var dataMap = JSON.parse(jsonfileNamesObj);
			    var fileUploadRow=0;
			    if(Object.keys(dataMap[0]).length >0){
			     if(allowMultiple =='Y'){
			     $.each( dataMap[0], function( key, value ) {
			    		  if(fileUploadRow == 0){
			    			  $("#uploadDocSaveDiv0").hide();
								$("#uploadDocEditDiv0").show();
								 $('#uploadDocumentName0').text(value);  
								 $("#uploadFile0").attr("data-validation-optional","true");
								 $('#docHisoryId0').val(key);
			    		  }else{
			    			  appendDynamicUploaRow();	
	    					  $("#uploadDocSaveDiv"+fileUploadRow).hide();
	    					  $("#uploadFile"+fileUploadRow).attr("data-validation-optional","true");
			    			  $("#uploadDocEditDiv"+fileUploadRow).show();
			    			  $('#uploadDocumentName'+fileUploadRow).text(value);
			    			  $('#docHisoryId'+fileUploadRow).val(key);
			    		  }
			    	  
			    	  console.log("row id value is "+rowId +", fileUploadRow --> "+fileUploadRow);
			    	   $('#uploadFile'+fileUploadRow).attr("docId", key);
			    	  $('#uploadDocumentName'+fileUploadRow).attr("fileName", value); 
			    	  fileUploadRow =fileUploadRow+1;
			    	}); 
			     }else{
			    	 var docHistoryId =Object.keys(dataMap[0]);
			    	var fileName = Object.values(dataMap[0]);
			    	$('#uploadDocumentName0').text(fileName);
			    	$("#uploadDocSaveDiv0").hide();// uploadDocSaveDiv used for browse file div id 
					$("#uploadDocEditDiv0").show(); // uploadDocEditDiv used for show file name div id
					$('#docHisoryId0').val(docHistoryId);
			     }
			    }else{
			    	$("#uploadDocSaveDiv0").show();
					$("#uploadDocEditDiv0").hide();
			    }
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


$("#docZipfile").click(function() {
	if(immigrationId == 0)
		return;
	$.post("downloadImmigrationDocsZip",{immigrationId: immigrationId, docId: ''}, function(data) {//  passing empty value for docId to do immigration level zip download
		if(data !== "" && data.indexOf("Documents Found") === -1)
			location.href = '${SourceLeadWeb_sourceleadUrl}/downloadFile.html?file='+encodeURI(data); // encodeURI used for encoding file path in request parameter.
		else if(data !== "" && data.indexOf("immigrationId Found") !== -1)
			alert(data);
		else
			alert("Unable to locate logs for this upload");
	});
});

</script>
		
<script type="text/javascript">

function removeDocPopUp12(){
	 immigration.displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,"");
}

function removeDocPopUp(){
	$(".popMask").fadeOut(200);
	$("#docuView").fadeOut(100);
	$("#candidate_Immigration_document_form")[0].reset();
	$("#description1").text('');
	$("#immigrationCandidateDocId").val('');
	$("#documentType").selectpicker("refresh");
	$('#candidate_Immigration_document_form input[name="multiple"]').prop('checked', false);
	$('#candidate_Immigration_document_form input[name="isRequired"]').prop('checked', false);
	$("#docuView").find('p.jquery_form_error_message').remove();
	$("#canDocSave_Button1").removeAttr("disabled");
	
	
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
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<div class='col-md-10 paddingLeft'>";
	  
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<div class='row'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+" <input type='hidden' id='docHisoryId"+rowId+"' name='immigrationCandidateDocHistoryId["+rowId+"]' value=''>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<div class='col-md-10' id= 'uploadDocSaveDiv"+rowId+"'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"	<div class='input-group validation_p'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"	<label class='input-group-btn'> <span class='btn btn-primary' onchange='uploadRerqDocsList("+rowId+")'><i class='fa fa-folder-open-o'></i>&nbsp;Browse File";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"	<input type='file' style='display:none'; onchange='uploadBtnFilesList(" + rowId + ");' multiple id='uploadBtn"+rowId+"'  class='upload'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"		  </span> </label>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"		<input type='text' class='form-control' readonly id='uploadFile"+rowId+"' data-validation ='required' field-name ='Document' name = 'docFile"+rowId+"' placeholder='Upload Document ...'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"			  </div>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"			  </div>";
	  
	dynamicUploadRowHtml= dynamicUploadRowHtml+'<div class="col-md-10" id="uploadDocEditDiv'+rowId+'" style="display: none"> ';
	//dynamicUploadRowHtml=dynamicUploadRowHtml+'	<label class="col-md-4 padding_left">File<span class="text-danger">&nbsp;*</span></label> ';
	dynamicUploadRowHtml=dynamicUploadRowHtml+'	 <div class="clearfix"> ';
	dynamicUploadRowHtml=dynamicUploadRowHtml+'	  <label id="uploadDocumentName'+rowId+'" name="uploadDocumentName'+rowId+'" class="text-primary tbldata-dotdot-custom pull-left" title=""></label>';
	dynamicUploadRowHtml=dynamicUploadRowHtml+'	<input type="hidden" id="uploadDocumentNameExisitngFile" name="uploadDocumentNameExisitngFile" value="">';
	dynamicUploadRowHtml=dynamicUploadRowHtml+'	  </div>';		          	
	dynamicUploadRowHtml=dynamicUploadRowHtml+'	</div>	';

	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<div id='divAddButton' class='col-md-2 paddingLeft'>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"<button type='button' class='btn btm-sm' id='btnDynamicRow"+rowId+"' onclick='removeDynamicUploaRow("+rowId+");' ><i class='fa fa-remove text-danger'></i></button>";
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"</div>";
	  
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"</div>";
	  
	  
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"</div>"; 
	  dynamicUploadRowHtml =dynamicUploadRowHtml+"</div>";
	 return dynamicUploadRowHtml;
}
function appendDynamicUploaRow(){
	   $("#dynamicUplodDiv").append(prepareDynamicUploadRow(rowId++)); 
}
function removeDynamicUploaRow(id){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	  var  recordDelete = $("#uploadFile"+id).attr("docid");
	   
	  if(typeof recordDelete != "undefined" && recordDelete != null && recordDelete !='' ){
	  $.ajax({
          url: vmsUrl + immiModulePath + "/deleteDocumentHistoryRecordById?recordDelete="+recordDelete,
          type: "GET",
          success: function (result) {
          if(result=='success'){
          	console.log(" record successfully delete id  --> "+recordDeletes);
          	 
          	} 
          }
      });
	 
	  }
	  $("#dynamicRow"+id).remove();     
	   //rowId--;// decrement rowid when deleted in UI.
}
$("#immigraDocTabSerach").keyup(function (e) {
    if (e.which === 13) {
    	var searchDocVal = $("#immigraDocTabSerach").val();
    	if(searchDocVal) searchDocVal = searchDocVal.trim();
    	immigration.displayImmigrationCandidateDocs(candidateId,templateId,immigrationId,searchDocVal,'${curUscisMyImmi}');
    }
});
function downlaodZipfileByDocId(docId){
	if(docId == "")
		return;
	$.post("downloadImmigrationDocsZip",{immigrationId: immigrationId, docId: docId}, function(data) { //  passing empty value for immigrationId to do doc level zip download
		if(data !== "" && data.indexOf("Documents Found") === -1)
			location.href = '${SourceLeadWeb_sourceleadUrl}/downloadFile.html?file='+encodeURI(data); // encodeURI used for encoding file path in request parameter.
		else
			alert("Unable to locate Documents.");
	});
	
}
</script>	


<script>

function getDocumentTypeDiv(documentTypes){
	 var existingDocumentType = $('#documentType').val(); 
	 
	 var selText= $(documentTypes).find("option:selected").text();
	if(selText == 'Other'){
		  document.getElementById("documentOtherTypeDiv").style.display="block";
		  $('#documentOtherType').attr("data-validation","required validate_Space validate_length length1-250"); 
		  $("#documentOtherTypeDiv").attr("data-validation-optional", "false");
		  $("#documentOtherTypeDiv").attr("data-validation", "required validate_Space validate_length length2-250");
	}else{
		  document.getElementById("documentOtherTypeDiv").style.display="none";
		  $("#documentOtherTypeDiv").attr("data-validation-optional", "true");
		  $("#documentOtherTypeDiv").removeAttr("data-validation"); 
		  $("#documentOtherType").find('p.jquery_form_error_message').remove();
		  if(existingDocumentType == null || existingDocumentType == "" || existingDocumentType == "undefined" || existingDocumentType != 'Other'){
			  $("#documentOtherType").val('');
		  }
	}
}
function checkRequiredDocAndContinue(){
	 var multiWarnMsg="Please Upload Below Required Docs!..." 
		    var missedDocs="";
			$("table#documentsTbl > tbody > tr").each(function(row, tr) {
				var docName = ($('td:eq(0)', this).text()); // first column form name text
				var required = ($('td:eq(2)', this).text());// third  column required column text
				var status = ($('td:eq(4)', this).text()); // fifth  column status column text

				if (required.toUpperCase() == 'YES'	&& status.toUpperCase() == 'REQUESTED') {
					if (missedDocs == '') {
						missedDocs = missedDocs + docName;
					} else {
						missedDocs = missedDocs + ", " + docName;
					}
				}
						
			});
			
			if(missedDocs!=''){  // if missed required docs upload
				multiWarnMsg = multiWarnMsg + '\n' + missedDocs;
			   showWarningPopup("", multiWarnMsg);
			return;
			}
		// calling submit server action if no errors found.
	immigration.updateImmigrationStatus(immigrationId);
			
	
}
//chosen script
//$("#documentType").chosen();
//$("#documentType").trigger("chosen:updated");
</script>

<script>
//$(document).ready(function(){
  var allDocsUploaded = '<c:out value ="${allDocsUploaded}"/>';
  console.log("allDocsUploaded -->"+allDocsUploaded);
	if(allDocsUploaded == 'Yes'){
	        $('#allDocsfilled').show() // show and hides circle icon.
	 }
    else {
	        $('#allDocsfilled').hide()
     }
//});	
$("#documentType").selectpicker("refresh");
</script>