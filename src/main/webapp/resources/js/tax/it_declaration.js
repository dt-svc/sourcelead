/**
 * @author Mahaveer Manoj
 * @source admin
 */

var uploadCount = 1;
$(document).ready(function() {
	$("#employeeSelect").change(function() {
		$("#associateName").val($("#employeeSelect :selected").data("name"));
		$("#associateId").val($("#employeeSelect :selected").data("associate_id"));
		$("#associatePAN").val($("#employeeSelect :selected").data("pan"));
		$("#associateDOB").val($("#employeeSelect :selected").data("dob"));
	});
	$("#saveBtn, #submitBtn").click(function() {
		if ($('#itDeclarationForm').validate(false, validationSettings)) {
			var flag = true;
			$("#documentsTable tr").each(function() {
				var docNameInput = $(this).find("input[data-name='docName']");
				var fileNameInput = $(this).find("input[data-name='fileName']");
				/*if(docNameInput.val() === "" && fileNameInput.val() === "") {
					$(docNameInput).parent().append("<p class='jquery_form_error_message'>Document Name is required</p>");
					$(fileNameInput).parent().append("<p class='jquery_form_error_message'>Document File is required</p>");
					flag = false;
				} else*/ 
				if(docNameInput.val() !== "" && fileNameInput.val() === "") {
					$(fileNameInput).parent().append("<p class='jquery_form_error_message'>Document File is required</p>");
					flag = false;
				} else if(docNameInput.val() === "" && fileNameInput.val() !== "") {
					$(docNameInput).parent().append("<p class='jquery_form_error_message'>Document Name is required</p>");
					flag = false;
				}
			});
			if(flag) {
				$("#saveBtn").attr("disabled","disabled");
				$("#submitBtn").attr("disabled","disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
				var formData = getFormData("itDeclarationForm");
				$.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/itDeclaration/save",
					success : function(response) {
						if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		if (response.object > 0) {
			        			$(".success_pop").css("display","block");
				        		$(".success_pop span").html("ITDeclaration added successfully....");
				        		setTimeout(function() {
				        			location.href = pmsUrl + "/runIndianPayRoll?stab=forms";
						 		}, 100);
			        		}
			        	} else if (response.response === "INVALID_DATA" || response.response === "DUPLICATE_TAX_RECORD") {
			        		showError(response.responseMessage);
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
						$("#saveBtn").removeAttr("disabled");
						$("#submitBtn").removeAttr("disabled");
						$("#loading").removeAttr("style");
					},
				});	
			}
			return true;
		} else
			return false;
	});
});
function fileUploadEvent(ele) {
	var id = $(ele).attr("id");
	var files = document.getElementById(id).files;
	var filesSize = files.length;
	$(ele).parent().parent().next().val(filesSize > 1 ? (filesSize + " files selected") : $(ele).val());

	var data = new FormData();
	for(var i=0 ; i < filesSize; i++){
		data.append("taxDocuments",files[i]);
	}
	data.append("type","taxDocuments");
	$.ajax({
		url : sourceleadUrl + "/ws/uploadTaxDocs",
		data : data,
		processData : false,
		type : 'POST',
		contentType : false,
		success : function(data) {
			if (data && data.length > 0) {
				var parentDiv = $(ele).parents("tr[data-index='" + $(ele).data("index") + "']");
				var attachmentDiv = $(parentDiv).find("div[class='attachments']");
				$(attachmentDiv).empty();
				var filesText = "";
				for(var e in data) {
					filesText += "<div data-index=" + e + ">";
					var fileName = data[e].fileURL.substring(data[e].fileURL.lastIndexOf("/") + 1 , data[e].fileURL.length);
					filesText += "<i onclick='removeAttachment(this);' class='text-danger fa fa-remove cursorPointer'></i>";
					filesText += fileName;
					filesText += "<i onclick='viewFile(\"" + data[e].fileURL.replace("taxDocuments","temp") + "\",\"" + fileName + "\",\"" + data[e].contentType + "\");' title='preview' class='fa fa-eye cursorPointer'></i>";
					filesText += "</div>";
					$(parentDiv).find("input[data-name='updateFile']").val(true);
					var text = "<input name='documentList[" + $(attachmentDiv).data('index') + "].attachmentList[" + e + "].attachment' data-name='documentFileNames' type='hidden' value='" + fileName + "'>";
					text += "<input name='documentList[" + $(attachmentDiv).data('index') + "].attachmentList[" + e + "].fileURL' type='hidden' value='" + data[e].fileURL + "'>";
					text += "<input name='documentList[" + $(attachmentDiv).data('index') + "].attachmentList[" + e + "].contentType' type='hidden' value='" + data[e].contentType + "'>";
					$(attachmentDiv).append(text);
				}
				var uploadedFilesDiv = $(parentDiv).find(".uploadedFiles");
				uploadedFilesDiv.html(filesText);
				enableDisableRefresh(ele);
			}
		},
		error : function(err) {
			alert(err);
		}
	});
}
function applyKeyToDocument(ele) {
	$(ele).parent().find("input[data-name='documentName']").val($(ele).val());
}
function enableDisableRefresh(ele) {
	var flag = true;
	if ($(ele).attr("type") === "file") {
		if (document.getElementById($(ele).attr("id")).files.length === 0)
			flag = false;
	} else {
		if ($(ele).val() === "")
			flag = false;
	}
	if (flag)
		$(ele).parents("tr").find(".fa-refresh").removeClass("disableBtn");
	else
		$(ele).parents("tr").find(".fa-refresh").addClass("disableBtn");
}
function addDocumentRow() {
	/*++uploadCount;*/
	var row = "<tr data-index=" + uploadCount + "><td>";
	row += "<div style='display: none;' class='attachments' data-index=" + uploadCount + "></div>";
	row += "<input type='hidden' name='documentList[" + uploadCount + "].name' data-name='documentName' />";
	row += "<input type='hidden' name='documentList[" + uploadCount + "].updateFile' data-name='updateFile' value='false'/>";
	row += "<input data-name='docName' type='text' class='form-control input-sm' placeholder='Enter Document Name' field-name='Document Name' onchange='enableDisableRefresh(this);applyKeyToDocument(this);' onkeyup='enableDisableRefresh(this);applyKeyToDocument(this);'>";
	row +="</td>";
	row += "<td>";
	row += "<div class='clearfix attchments'>";
	row += "<div class='input-group'>";
	row += "<label class='input-group-btn'>";
	row += "<span class='btn btn-primary'>Browse File ";
	row += "<input data-name='docFiles' style='visibility: hidden; height: 0; width: 10px; overflow: hidden' multiple='multiple' id='uploadBtn" + (uploadCount)
			+ "' class='upload' type='file' onchange='fileUploadEvent(this)' data-index=" + uploadCount + ">";
	row += "</span>";
	row += "</label>";
	row += "<input class='form-control' readonly='readonly' data-name='fileName' id='uploadFile" + uploadCount + "' placeholder='Select File(s)' type='text' field-name='Document File'>";
	row += "</div>";
	row += "</div>";
	row += "<div class='uploadedFiles' data-id=" + uploadCount + "></div>";
	row += "</td>";
	row += "<td>";
	row += "<div class='tblminwidth-75 text-center'>";
	row += "<i class='fa fa-trash text-danger mr cursorPointer' onclick='removeDocumentRow(this)'></i>";
	row += "<i class='fa fa-refresh cursorPointer disableBtn' onclick='refreshDocumentRow(this)'></i>";
	row += "</div>";
	row += "</td></tr>";
	$("#documentsTable").append(row);
	uploadCount++;
}

function refreshDocumentInputIndexes() {
	var count = 0;
	$("#documentsTable tr").each(function () {
		$(this).data("index", count);
		$(this).find(".attachments").data("index", count);
		var attachmentCount = 0;
		$(this).find(".attachments").find("input").each(function () {
			$(this).prop("name", "documentList[" + count + "].attachmentList[" + attachmentCount + "].attachment");
			attachmentCount++;
		});
		$(this).find("input[data-name='documentName']").prop("name", "documentList[" + count + "].name");
		$(this).find("input[data-name='updateFile']").prop("name", "documentList[" + count + "].updateFile");
		count++;
	});
}

function refreshDocumentRow(ele) {
	$(ele).parents("tr").find("input").val("");
	$(ele).parents("tr").find("input[data-name='updateFile']").val("false");
	$(ele).parents("tr").find("p").remove();
	$(ele).parents("tr").find(".uploadedFiles").html("");
	$(ele).parents("tr").find(".fa-refresh").addClass("disableBtn");
}

function removeDocumentRow(ele) {
	var parentDiv = $(ele).parents("tr");
	$(ele).parents("tr").remove();
	uploadCount = 1;
	$(".upload").each(function() {
		$(this).attr("id", "uploadBtn" + (uploadCount++));
		document.getElementById($(this).attr("id")).value = "";
	});
	var documentId = $(parentDiv).find("input[data-name='documentId']").val();
	if(documentId && documentId > 0) {
		var documentRemoveIds = $("#documentRemoveIds").val();
		$("#documentRemoveIds").val(documentRemoveIds + (documentRemoveIds === "" ? "" : ",") + documentId);
	}
	refreshDocumentInputIndexes();
}

function removeAttachment(ele) {
	var parentDiv = $(ele).parents("tr");
	$(ele).parent().remove();
	var filesCount = $(parentDiv).find("i[data-name='attachment']").length;
	$("#uploadFile" + $(parentDiv).data("index")).val(filesCount > 1 ? (filesCount + " files selected") : filesCount === 1 ? "1 file selected" : "");
	var attachmentId = $(ele).data("id");
	if(attachmentId && attachmentId > 0) {
		var attachmentRemoveIds = $("#attachmentRemoveIds").val();
		$("#attachmentRemoveIds").val(attachmentRemoveIds + (attachmentRemoveIds === "" ? "" : ",") + attachmentId);
	}
	if(filesCount === 0) {
		var documentId = $(parentDiv).find("input[data-name='documentId']").val();
		if(documentId && documentId > 0) {
			var documentRemoveIds = $("#documentRemoveIds").val();
			$("#documentRemoveIds").val(documentRemoveIds + (documentRemoveIds === "" ? "" : ",") + documentId);
		}
	}
}

function approveOrRejectForm(formId, status) {
	$("#remove_user_pop p").html("You are choosing to make this form as "+ (status === 1 ? "Approve": "Reject"));
	$("#confirmId").data("id", formId);
	$("#confirmId").data("status", status);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function changeFormStatus() {
	var formId = $("#confirmId").data("id");
	var status = $("#confirmId").data("status");
	$.post(sourceleadUrl + "/itDeclaration/changeStatus", {id: $("#confirmId").data("id"), status: $("#confirmId").data("status")}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			closeChangeStatusPopup();
			$(".success_pop").css("display","block");
			$(".success_pop span").html("Changed form status as " + (status === 1 ? "Approved" : "Rejected" ));
    		setTimeout(function() {
    			window.location.reload();
    			$(".success_pop").css("display","none");
	 		},500);
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function closeChangeStatusPopup() {
	$("#remove_user_pop").slideUp(200);
	$(".project_inn_mask").fadeOut(200);
}

function getItDeclarationSubmissionList(uuid, financialYear, pageNo) {
	var pageSize = $("#pageSize").val();
	var searchValue = $("#formSearch").val();  
	$("#formList").empty();
	$.post(sourceleadUrl + "/itDeclaration/list", {uuid:uuid, financialYear:financialYear,pageNo:pageNo, pageSize:pageSize,searchValue:searchValue}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			var formList = response.data;
			if(formList.length > 0) {
				for(var e in formList) {
					var text = "<tr>";
					text += "	<td>" + formList[e].employeeId + "</td>";
					text += "	<td>";
					text += "		<div class='tblminwidth proj_show'>";
					text += "			<a href='viewForm?uuid=" + uuid + "&formUUID=" + formList[e].uuid + "'><span>" + formList[e].userName + "</span> <i class='fa fa-binoculars'></i></a>";
					text += "		</div>";
					text += "	</td>";
					text += "	<td><div class='tbldata-dotdot-verybig'>" + formList[e].financialYear + "</div></td>";
					text += "	<td>" + formList[e].submittedAmount + "</td>";
					text += "	<td><div>" + formList[e].statusText + "</div></td>";
					text += "	<td>";
					text += "		<ul class='list-unstyled list-inline mb0'>";
			    //	text += "			<li><a href='viewForm?uuid=${uuid}&formUUID=" + formList[e].uuid + "' title='View' class='anchor-black'><i class='fa fa-eye'></i></a></li>";
					text += "			<li><a href='javascript:void()' class='text-danger' title='ExportToPDF' onclick='exportFormToPDF(" + formList[e].id + ")'><i class='fa fa-file-pdf-o'></i></a></li>";
					text += "			<li><a href='javascript:void()' class='text-danger' title='ExportToEXCEL' onclick='exportFormToExcel(" + formList[e].id + ")'><i class='fa fa-file-excel-o'></i></a></li>";
					if(formList[e].status === 0) {
						text += "				<li><a href='" + sourceleadUrl + "/forms/editForm?uuid=" + uuid + "&formUUID=" + formList[e].uuid + "' title='Edit' class='anchor-black'><i class='fa fa-pencil'></i></a></li>";
						text += "				<li><a href='javascript:void()' title='Approve' onclick='approveOrRejectForm(" + formList[e].id + ", 1)'><i class='fa fa-check'></i></a></li>";
						text += "				<li><a href='javascript:void()' title='Reject' onclick='approveOrRejectForm(" + formList[e].id + ", -1)' class='text-danger'><i class='fa fa-close'></i></a></li>";
					}
					text += "		</ul>";
					text += "	</td>";
					text += "</tr>";
					$("#formList").append(text);
				}
				var paginator = response.paginator;
				var options = {
						currentPage : paginator.pageNo + 1,
						items : paginator.totalRows,
						itemsOnPage : paginator.pageSize,
						ellipsePageSet : false,
						cssStyle : 'light-theme',
						onPageClick : function(pageNumber, event) {
							getItDeclarationSubmissionList(uuid, financialYear, pageNumber-1);
						}
				};
				$("#pagination-demo").pagination(options);
				$("#pageMsg").html("Showing " + ((paginator.pageNo * paginator.pageSize) + 1) + " to " + (((paginator.pageNo + 1) * paginator.pageSize) > paginator.totalRows ? paginator.totalRows : ((paginator.pageNo + 1) * paginator.pageSize)) + " of " + paginator.totalRows + " results");
			
				$("#ResultsDiv").show();
				$("#paginationDiv").show();
				$("#emptyRecordsDiv").hide();
			} else {
				$("#ResultsDiv").hide();
				$("#paginationDiv").hide();
				$("#emptyRecordsDiv").show();
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}
function exportFormToPDF(formId) {
	var exportType = "pdf";
	/*$.ajax({
		type : "POST",
		 url : sourceleadUrl+"/itDeclartionSubmissionExport?&formId="+formId+"&exportType="+exportType,
	});*/
	window.location.href = sourceleadUrl+"/itDeclartionSubmissionExport?formId="+formId+"&exportType="+exportType;
}
function exportFormToExcel(formId) {
	var exportType = "excel";
	/*$.ajax({
		type : "POST",
		 url : sourceleadUrl+"/itDeclartionSubmissionExport?&formId="+formId+"&exportType="+exportType,
	})*/
	window.location.href = sourceleadUrl+"/itDeclartionSubmissionExport?formId="+formId+"&exportType="+exportType;
}