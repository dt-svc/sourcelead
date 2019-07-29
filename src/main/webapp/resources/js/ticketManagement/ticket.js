var statusOptions = [];
var statusList = [];
$(document).ready(function() {
	$("#ticketListSearch").keyup(function(e) {
		if(e.which === 13) {
			getTicketManagementList();
		}
	});
});

//============================== Ticket Management List 	=============================================

function getTicketStatusList() {
	$.post(sourceleadUrl + "/getTicketStatusList", {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.data.length > 0) {
				statusList = response.data;
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function editTicketManagement(uuid) {
	location.href = sourceleadUrl + "/editTicket?uuid=" + uuid;
}
var activeElement = null, previousStatusId = "";
function changeTicketStatus(ele, ticketId, previousStatus) {
	activeElement = ele;
	previousStatusId = previousStatus;
	var statusId = $(ele).val();
	var commentRequired = $(ele).find(':selected').data("comment");
	if(commentRequired) {
//		showChangeStatusPopup(ticketId, statusId);
		document.getElementById("ticketStatusForm").reset();
		$("#confirmStatusWithCommentId").data("ticket_id", ticketId);
		$("#confirmStatusWithCommentId").data("status_id", statusId);
		$("#changeStatusPopupWithComment").slideDown(200);
	} else {
//		changeTicketStatusFinal(false, ticketId, statusId);
		$("#confirmStatusWithoutCommentId").data("ticket_id", ticketId);
		$("#confirmStatusWithoutCommentId").data("status_id", statusId);
		$("#changeStatusPopupWithoutComment").slideDown(200);
	}
	$(".mask").fadeIn(200);
}

function changeTicketStatusFinal(popupFlag, ticketId, statusId) {
	var comment = "";
	var flag = !popupFlag ? true : false;
	if(popupFlag && $('#ticketStatusForm').validate(false, validationSettings)) {
		ticketId = $("#confirmStatusWithCommentId").data("ticket_id");
		statusId = $("#confirmStatusWithCommentId").data("status_id");
		comment = $("#statusComment").val();
		flag = true;
	} else {
		ticketId = $("#confirmStatusWithoutCommentId").data("ticket_id");
		statusId = $("#confirmStatusWithoutCommentId").data("status_id");
	}
	if(flag) {
		$.post(sourceleadUrl + "/changeTicketStatusType?ticketId="+ ticketId + "&statusId=" + statusId + "&comment=" + comment, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object === true) {
					closeChangeStatusPopup(false);
					location.reload();
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
}

function closeChangeStatusPopup(cancelFlag) {
	if(cancelFlag) {
		$(activeElement).val(previousStatusId);
	}
	$("#changeStatusPopupWithoutComment").slideUp(200);
	$("#changeStatusPopupWithComment").slideUp(200);
	$(".mask").fadeOut(200);
}

function showDeletePopup(id, status) {
	$("#confirmDeleteId").data("id", id);
	$("#confirmDeleteId").data("status", status);
	$("#deletePopup").slideDown(200);
	$(".mask").fadeIn(200);
}

function deleteTicket() {
	var id = $("#confirmDeleteId").data("id");
	var status = $("#confirmDeleteId").data("status");
	$.post(sourceleadUrl + "/changeTicketManagementStatus?ticketId=" + id + "&status=" + status, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				closeDeletePopup();
				location.reload();
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function closeDeletePopup() {
	$("#deletePopup").slideUp(200);
	$(".mask").fadeOut(200);
}

/* ============================ Add Ticket ================================= */
function getTicketManagementDescription(ticketUUID){
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getTicketManagementDescription?uuid="+ticketUUID, 	   
		    success: function(result) {
		    	$('#txtEditor').html(result.description);
		    	$('.Editor-editor').html(result.description);
		    }
    });
}

function getCategoryAssignToUsers() {
	$('#assignedTo').val('').trigger('chosen:updated');
	var categoryId = $("#ticketCategory").val();
	$.post(sourceleadUrl + "/getCategoryAssignToListByTicketCategoryId?ticketCategoryId=" + categoryId, {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			for(var e in response.data) {
				$('#assignedTo').find("option[value='" + response.data[e].id + "']").prop("selected", true).end().trigger('chosen:updated');
			}
			$("#assignedTo").trigger('chosen:update');
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function validateTicketId(ticketId) {
	 var generatedId = $("#ticketId").val(); 
	 if(ticketId !=null && ticketId !='' && ticketId !='undefined') {
		 generatedId = ticketId;
	 }
	 if(generatedId != null && generatedId != "") {
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateTicketId?generatedId="+generatedId, 
		        success: function(data) {
		        	if(data == true){
		        		$('#ticketDivId').find('p.jquery_form_error_message').remove();
		        		$("input#ticketId").after ("<p class='jquery_form_error_message'>"+generatedId+" is Already Existed</span>");
						document.getElementById("ticketId").setAttribute('record-exist','yes');
						document.getElementById("ticketId").setAttribute('record-exist-errorMsg',generatedId+' is Already Existed'); 
		        	} else {
		        		$('input#ticketId').removeAttr( "record-exist record-exist-errormsg"); 
		        		$('#ticketDivId').find('p.jquery_form_error_message').remove(); 
		        		$("#ticketId").removeAttr("disabled");
		        	}
		        },
		    });
	 }
}

function saveTicket() {
	var ee = $("#txtEditor").val($('.Editor-editor').html()); 
	if ($('#addTicketManagementForm').validate(false, validationSettings)) {
		if ($("#notifyTo").val() !== "") {
			for (var e in $("#notifyTo").val().split(",")) {
				if (!jQueryFormUtils.validateEmail($("#notifyTo").val().split(",")[e].replace(/\s+/g, ''))) {
					flag = false;
					$("#notifyTo").parent().append("<p class='jquery_form_error_message'>Enter valid email id</p>");
					return;
				}
			}
		}
		var formData = getFormData1("addTicketManagementForm");
		document.getElementById("addTicket").disabled = true;
		$.ajax({
			 type : "POST",
			 data: formData,
		     processData: false,
		     contentType:false, 
		     url : sourceleadUrl+"/saveTicket",
			 success : function(response) {
		  		if (response && response.response === "INVALID_DATA") {
					showError(response.responseMessage);
				} else if (response.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
						$(".success_pop span").html("Ticket saved successfully...");
						$(".success_pop").show();
						setTimeout(function() {
							location.replace(sourceleadUrl+"/ticketManagementList");
							/* getTicketManagementList(); */
							$(".success_pop").hide();
				 		}, 1000);  
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
			},
		});
		return true;
	} else {
		return false;
	}
}

function addAttachmentRow() {
	var text = "<div class='row mb' data-index=" + attachmentCount + ">";
	text += "	<div class='input-group col-md-11 pull-left'>";
	text += "    	<label class='input-group-btn'>";
	text += "            <span class='btn btn-primary'>";
	text += "            	<i class='fa fa-folder-open-o'></i>&nbsp;Browse File";
	text += "            	<input type='file' style='display: none;' id='uploadFile" + attachmentCount + "' data-name='file' data-index=" + attachmentCount + " class='upload' onchange='validateUploadFile(this, " + attachmentCount + ")'>";
	text += "            </span>";
	text += "       	</label>";
	text += "    	<input type='text' class='form-control fileInput' readonly id='uploadFileInput" + attachmentCount + "' data-name='input' placeholder='Upload File ...'>";
	text += "	</div>";
	text += "    <div class='input-group col-md-1 colorRed text-center'>";
	text += "    	<i class='fa fa-times cursorPointer mt10' data-name='delete' onclick='removeAttachmentRow(this, " + attachmentCount + ")'></i>";
	text += "    </div>";
	text += "</div>";
	$("#attachmentsDiv").append(text);
	attachmentCount++;
}

function removeAttachmentRow(ele, index) {
	$(ele).parent().parent().remove();
	$("input[data-id='uploadFile" + index + "']").remove();
	refreshAttachmentRows();
}

function refreshAttachmentRows() {
	var count = 0;
	$("#attachmentsDiv").find("div[class='row mb']").each(function() {
		var index = $(this).data("index");
		
		$("input[name='fileInfo[" + index + "].fileModified']").data("id", "uploadFile" + count);
		$("input[name='fileInfo[" + index + "].fileModified']").attr("name", "fileInfo[" + (count) + "].fileModified");
		
		$("input[name='fileInfo[" + index + "].fileDbId']").data("id", "uploadFile" + count);
		$("input[name='fileInfo[" + index + "].fileDbId']").attr("name", "fileInfo[" + (count) + "].fileDbId");
		
		$("input[name='fileInfo[" + index + "].fileName']").data("id", "uploadFile" + count);
		$("input[name='fileInfo[" + index + "].fileName']").attr("name", "fileInfo[" + (count) + "].fileName");
		
		$("input[name='fileInfo[" + index + "].contentType']").data("id", "uploadFile" + count);
		$("input[name='fileInfo[" + index + "].contentType']").attr("name", "fileInfo[" + (count) + "].contentType");
		
		$("input[name='fileInfo[" + index + "].fileURL']").data("id", "uploadFile" + count);
		$("input[name='fileInfo[" + index + "].fileURL']").attr("name", "fileInfo[" + (count) + "].fileURL");

		var temp = count;
		$(this).data("index", temp);
		$(this).find("input[data-name='file']").attr("id", "uploadFile" + temp);
		$(this).find("input[data-name='file']").removeAttr("onchange");
		$(this).find("input[data-name='file']").off("change").on("change", function() { validateUploadFile(this, temp); });
		$(this).find("input[data-name='input']").attr("id", "uploadFileInput" + temp);
//		$(this).find("i[data-name='delete']").data("index", temp);
		if($(this).find("i[data-name='delete']").length > 0) {
			$(this).find("i[data-name='delete']").removeAttr("onclick");
			$(this).find("i[data-name='delete']").off('click').on("click", function() { removeAttachmentRow(this, temp); });
		}
		count++;
	});
	attachmentCount = count;
}

function testAttachments() {
	$("input[type='hidden']").each(function () {
		if($(this).attr("name").indexOf("fileInfo") !== -1) {
			console.log("Name : " + $(this).attr("name") + " <> data-id : " + $(this).data("id") + " <> value : " + $(this).val());
		}
	});
}
function validateUploadFile(ele, index) {
	var selectedFile = $(ele).val();
	$(ele).parents(":eq(3)").closest(".row").find('p.jquery_form_error_message').remove();
	if(validate_fileupload(selectedFile)) {
		uploadTicketFile($(ele).attr("id"), "ticket", "addTicketManagementForm", index);
		var fileName = document.getElementById($(ele).attr("id")).files[0].name;
		$("#uploadFileInput" + index).val(fileName);
	} else {
		$(ele).parents(":eq(3)").closest(".row").append("<div class='clear'></div><p class='jquery_form_error_message'>Invalid Format</span>");
	}
}

function validate_fileupload(fileName) {
    var allowed_extensions = new Array("ppt", "pptx", "pps", "ppsx", "doc", "docx", "pdf", "rtf", "odt", "xls", "xlsx", "csv", "zip", "txt", "jpg","jpeg","bmp","png","gif","eps","tiff","TGA","psd","svg","msg","wpd","wps");
	var file_extension = fileName.split('.').pop(); 
    for(var i = 0; i <= allowed_extensions.length; i++) {
        if(allowed_extensions[i] != null && allowed_extensions[i] != '' && allowed_extensions[i] != 'undefined' && allowed_extensions[i]==file_extension.toLowerCase()) {
            return true; // valid file extension
        }
    }
    return false;
}

function uploadTicketFile(id, type, formId, index) {
	$(".file_mask").show();
	if(name == null || name == 'undefined' || name == ''){
		name = 'fileInfo';
	}
	
	var data = new FormData();
	var files = document.getElementById(id).files;
	var invalidFiles = false;
	var count = 0;
	expr = /.zip/;
	ecpr1=/.rar/;
	for(var e in files) {
		if(!$.isNumeric(e))
			continue;
		if(files[e].name.toLowerCase().match(expr) || files[e].name.toLowerCase().match(ecpr1))
			continue;
		data.append('file', files[e]);
		count ++;
	}
	if(count === 0) {
		$("#"+formId+" input[data-id*="+id+"]").remove();
		$(".file_mask").hide();
		alert("Please select valid file (zip files and rar files are not allowed)");
		return;
	}
	
	$.ajax({ 
		url: sourceleadUrl + ("/ws/upload/" + type), 
		data: data, 
		processData: false, 
		type: 'POST', 
		contentType: false, 
		success: function (data) {
			if(data && data.length > 0) {
				var exists = false;
				if($("input[name='fileInfo[" + index + "].fileURL']").length > 0) {
					exists = true;
				} 
				for(var e in data) {
					if(exists) {
						//get the existed fileInfo input's and update the value
						$("input[name='fileInfo[" + index + "].fileURL']").val(data[e].fileURL);
						$("input[name='fileInfo[" + index + "].fileName']").val(data[e].fileName);
						$("input[name='fileInfo[" + index + "].contentType']").val(data[e].contentType);
						$("input[name='fileInfo[" + index + "].fileModified']").val(true);
					} else {
						//add new fileInfo input's to form
						var html = "<input type='hidden' name='"+name+"["+index+"].fileURL' data-id="+id+" value='"+data[e].fileURL+"'/>";
						html += "<input type='hidden' name='"+name+"["+index+"].fileName' data-id="+id+" value='"+data[e].fileName+"'/>";
						html += "<input type='hidden' name='"+name+"["+index+"].contentType' data-id="+id+" value='"+data[e].contentType+"'/>";
						$("#"+formId).append(html);
					}
				}
				$(".file_mask").hide();
			}
		}, 
		error: function (err) { 
			 $(".file_mask").hide();
		} 
	});
}
