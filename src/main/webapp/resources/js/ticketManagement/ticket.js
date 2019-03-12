var statusOptions = [];
var statusList = [];
$(document).ready(function() {
	
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

function getTicketManagementList() {
	$("#ticketManagementList").empty();
	$.post(sourceleadUrl + "/getTicketList", {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.data.length > 0) {
				for(var e in response.data) {
					var text = "<tr>";
		            text+= "      <td><div class='tblminwidth-150'>" + response.data[e].generatedId + "</div></td>";
		            text+= "      <td><div class='tbldata-dotdot-vsm'><a href='/app/ticketSummary?uuid=" + response.data[e].uuid + "' title='" + response.data[e].title + "'>" + response.data[e].title + "</a></div></td>";
		            /*text+= "     <td><div class='tbldata-dotdot-vsm'>" + response.data[e].ticketCategory + "</div></td>";*/
		            text += "<td><select class='sel' onChange='changeTicketStatus(this, \"" + response.data[e].id + "\")'>";
		            for(var s in statusList) {
		            	text += "<option data-comment=" + statusList[s].commentRequired + " value='" + statusList[s].id + "' " + (statusList[s].name === response.data[e].ticketStatus ? "selected" : "") + ">" + statusList[s].name + "</option>";
		            }
		            text += "</select></td>";
		            /*text+= "     <td><div class='tbldata-dotdot-vsm'>" + response.data[e].ticketStatus + "</div></td>";*/
		            text+= "   	<td><div class='tblminwidth-75' style='color:" + (response.data[e].ticketPriorityTextColor != null && response.data[e].ticketPriorityTextColor !== "" ? response.data[e].ticketPriorityTextColor : "") + "'>" + (response.data[e].ticketPriorityImage !== null && response.data[e].ticketPriorityImage !== "" ? "<div class='priorityImage pull-left mr10'><img src=\"" + response.data[e].ticketPriorityImage + "\" class='mr10'/></div>" : "<div class='blank-div mr10'></div>") + "<div class='pull-left'>" + response.data[e].ticketPriority + "</div></div></td>";
		            text+= "    <td><div class='tbldata-dotdot-vsm'>" + response.data[e].createdBy + "</div></td>";
		            var assignTo = "";
					for(var a in response.data[e].assignedTo) {
						assignTo += (assignTo === "" ? "" : ",") + response.data[e].assignedTo[a];
					}
					text += "    <td><div class='tbldata-dotdot'>" + (assignTo === "" ? "--" : assignTo) + "</div></td>";
		            text+= "      <td>";
					text+= "		  <ul class=list-inline list-unstyled'>";
					text+= "			  <li><i class='fa fa-pencil cursorPointer' onClick='editTicketManagement(\"" + response.data[e].uuid + "\");'></i></li>";
					text+= "		  <li><i class='fa fa-trash cursorPointer' onclick='showDeletePopup("+response.data[e].id+", 1);'></i></li>";
					text+= "	  </ul>";
					text+= "		</td>";
		            text+= "   </tr>";
		    		$("#ticketManagementList").append(text);
				}
			} else {
				alert("Empty list returned....");
			}
			ticketManagementListPagination();
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

/*function validateTicketId(ticketId) {
	$.ajax({
		type : "POST",
	url : sourceleadUrl+ "/validateTicketId?ticketId="+id,
		success : function(response) {
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
	  }
  });
}*/


function ticketManagementListPagination(){
	 $('#ticketManagementListTable').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		"sZeroRecords": "", "sEmptyTable": ""
	     	}
	    } );
}

function editTicketManagement(uuid) {
	location.href = sourceleadUrl + "/editTicket?uuid=" + uuid;
}

function changeTicketStatus(ele, ticketId) {
	document.getElementById("ticketStatusForm").reset();
	var statusId = $(ele).val();
	var commentRequired = $(ele).find(':selected').data("comment");
	if(commentRequired) {
		showChangeStatusPopup(ticketId, statusId);
	} else {
		changeTicketStatusFinal(false, ticketId, statusId);
	}
}

function changeTicketStatusFinal(popupFlag, ticketId, statusId) {
	var comment = "";
	var flag = !popupFlag ? true : false;
	if(popupFlag && $('#ticketStatusForm').validate(false, validationSettings)) {
		ticketId = $("#confirmStatusId").data("ticket_id");
		statusId = $("#confirmStatusId").data("status_id");
		comment = $("#statusComment").val();
		flag = true;
	}
	if(flag) {
		alert(ticketId + " : " + statusId + (comment ? " : " + comment : ""));
		$.post(sourceleadUrl + "/changeTicketStatusType?ticketId="+ ticketId + "&statusId=" + statusId + "&comment=" + comment, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object === true) {
					closeChangeStatusPopup();
					getTicketManagementList();
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
}

function showChangeStatusPopup(ticketId, statusId) {
	$("#confirmStatusId").data("ticket_id", ticketId);
	$("#confirmStatusId").data("status_id", statusId);
	$("#changeStatusPopup").slideDown(200);
	$(".mask").fadeIn(200);
}

function closeChangeStatusPopup() {
	$("#changeStatusPopup").slideUp(200);
	$(".mask").fadeOut(200);
}

function showDeletePopup(id, status) {
	$("#confirmId").data("id", id);
	$("#confirmId").data("status", status);
	var name = $("#changeStatusHeading").data("name");
	$("#changeStatusHeading").html("Are are sure you want to " + (status === 0 ? "disable " : "enable ") + name +"?");
	$("#changeStatusPopup").slideDown(200);
	$(".mask").fadeIn(200);
}

function closeDeletePopup() {
	$("#changeStatusPopup").slideUp(200);
	$(".mask").fadeOut(200);
}

function changeTicketManagementStatus(ticketId) {
	var id = $("#confirmId").data("id");
	var status = $("#confirmId").data("status");
	$.post(sourceleadUrl + "/changeTicketManagementStatusDataById?id="+ id + "&status=" + status, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				//alert(response.responseMessage);
				closeDeletePopup();
				getTicketManagementList();
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
	
}

//	================================	TicketComment		==============================================
function getTicketCommetList(ticketId) {
	$("#ticketCommentListDivId").empty();
	$.post(sourceleadUrl + "/getTicketCommentList?ticketId="+ticketId, {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
        	if(response.data.length > 0 ){
        		 for (var e in response.data) {
        			var text = "";
                    text += "<div class='comments-div comments-inner clearfix'>";
                    text += "    <div class='commentsimg'> <i class='fa fa-comments'></i>";
                    text += "   <div class='commentsimg-inner'>";
					text += "		<img onerror='predefineProfileImage(this);' src='../images/userprofile-img.png'></div>";
                    text += "  </div>";
                    text += "    <div class='customCommentarea'>";
                    text += "    <div class='row'>";
                    text += "        <div class='col-md-12'>";
                    text += "        <p class='comment-by'> By <span class='text-danger'>" + response.data[e].createdByName + "</span><span class='small mr'>" + response.data[e].createdDate + "</span> <span class='small'>" + response.data[e].createdDateString + "</span> </p>";
                    text += "        <div class='commentText commentText-styles'>" + response.data[e].comment + "</div>";
                    text += "      </div>";
                    text += "      </div>";
                    text += "  </div>";
                    text += "  </div>";
		    		$("#ticketCommentListDivId").append(text);
				}
			} else {
				alert("Empty list returned....");
			}
			
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function saveTicketComment(ticketId){
	if ($("#ticketComment_form").validate(false, validationSettings)){
		var formData = getFormData("ticketComment_form");
		$.post(sourceleadUrl + "/saveTicketComment?ticketId="+ticketId, formData, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object === true) {
					alert(response.responseMessage);
					getTicketCommetList(ticketId);
					closeTicketComment();
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
}
function closeTicketComment() {
	$("#add-comment").fadeOut();
	 document.getElementById("ticketComment_form").reset();
	 $("ticketComment_form").validate().resetForm();
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
