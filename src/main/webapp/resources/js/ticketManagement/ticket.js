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
		            text += "<td><select onChange='changeTicketStatus(this, \"" + response.data[e].id + "\")'>";
		            for(var s in statusList) {
		            	text += "<option data-comment=" + statusList[s].commentRequired + " value='" + statusList[s].id + "' " + (statusList[s].name === response.data[e].ticketStatus ? "selected" : "") + ">" + statusList[s].name + "</option>";
		            }
		            text += "</select></td>";
		            /*text+= "     <td><div class='tbldata-dotdot-vsm'>" + response.data[e].ticketStatus + "</div></td>";*/
		            text+= "   	<td><div class='tblminwidth-75' style='color:" + (response.data[e].ticketPriorityTextColor != null && response.data[e].ticketPriorityTextColor !== "" ? response.data[e].ticketPriorityTextColor : "") + "'>" + (response.data[e].ticketPriorityImage !== null && response.data[e].ticketPriorityImage !== "" ? "<img src=\"" + response.data[e].ticketPriorityImage + "\" style='height:20px;' class='mr10'/>" : "") + response.data[e].ticketPriority + "</div></td>";
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