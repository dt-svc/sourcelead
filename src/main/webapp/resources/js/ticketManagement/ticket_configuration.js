$(document).ready(function() {
	$("#addPop").click(function() {
		$("#ticketTypeFormHead").html("Add Type");
		$("#ticketCategoryFormHead").html("Add Category");
		$("#ticketPriorityFormHead").html("Add Priority");
		$("#ticketStatusFormHead").html("Add Status");
		$(".mask, #modalTasktype").fadeIn();
	});
	$(".popClose").click(function() {
		cancelTicketType();
		cancelTicketCategory();
		cancelTicketPriority();
		cancelTicketStatus();
//		cancelTicket();
	});
	if(document.getElementById("uploadBtn") !== null) {
		document.getElementById("uploadBtn").onchange = function () {
		    $('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
		};
	}
	$("#ticketTypeDataTableSearch").keyup(function(e) {
		if(e.which === 13) {
			getTicketTypeList();
		}
	});
	$("#ticketCategoryDataTableSearch").keyup(function(e) {
		if(e.which === 13) {
			getTicketCategoryList();
		}
	});
	$("#ticketPriorityDataTableSearch").keyup(function(e) {
		if(e.which === 13) {
			getTicketPriorityList();
		}
	});
	$("#ticketStatusDataTableSearch").keyup(function(e) {
		if(e.which === 13) {
			getTicketStatusList();
		}
	});
});

var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
        };

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

function getTicketTypeList() {
	$("#ticketTypeParentList").empty();
	var text = "<table style='width: 100%;' id='ticketManagementTypeTable'>";
	text += " <thead>";
	text += "    <tr>";
	text += "      	<th>Id</th>";
	text += "      	<th>Type</th>";
	text += "      	<th>Description</th>";
	text += "      	<th>Action</th>";
	text += "    </tr>";
	text += "  </thead>";
	text += "  <tbody id='ticketTypeList'>";
	text += "  </tbody>";
	text += "</table>";
	$("#ticketTypeParentList").append(text);
	ticketManagementTypePagination(sourceleadUrl + "/getTicketTypeList?searchValue=" + $("#ticketTypeDataTableSearch").val());
}

function saveTicketType() {
	if ($('#addTicketTypeForm').validate(false, validationSettings)){
		var formData = getFormData("addTicketTypeForm");
		$.post(sourceleadUrl + "/saveTicketType", formData, function(response) {
			if (response && (response.response === "INVALID_DATA" || response.response === "DUPLICATE_NAME")) {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object > 0) {
					$(".success_pop span").html("TicketType saved successfully...");
					$(".success_pop").show();
					cancelTicketType();
					setTimeout(function() {
						getTicketTypeList();
						$(".success_pop").hide();
			 		}, 1000);
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
		return true
	}else{
		return false;
	  } 
}
$('body').on('blur', '#addTicketTypeForm', function() {
	$("#addTicketTypeForm").showHelpOnFocus();
	$("#addTicketTypeForm").validateOnBlur(false, validationSettings);  
});

function cancelTicketType() {
	$("#ticketTypeId").val(0);
	if(document.getElementById("addTicketTypeForm") !== null)
		document.getElementById("addTicketTypeForm").reset();
	$(".mask, #modalTasktype").fadeOut();
	$(".location-alert").fadeOut();
}

function editTicketType(id){
	$.ajax({
		type : "POST",
 		url : sourceleadUrl+ "/getTicketTypeDataById?ticketTypeId="+id,
		success : function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var data = response.object;
				$("#ticketTypeFormHead").html("Edit Type");
				$("#ticketTypeId").val(data.id);
				$("#ticketTypeName").val(data.name);
				$("#ticketTypeDesc").val(data.description);
				$(".mask, #modalTasktype").fadeIn();
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		}
	});
}

function changeTicketTypeStatus() {
	var id = $("#confirmId").data("id");
	var status = $("#confirmId").data("status");
	$.post(sourceleadUrl + "/changeTicketTypeStatusDataById?id="+ id + "&status=" + status, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				closeDeletePopup();
				getTicketTypeList();
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

//================================== Ticket Management Category  =================== 

function getTicketCategoryList() {
	$("#ticketCategoryParentList").empty();
	var text = "<table style='width: 100%;' id='ticketManagementCategoryTable'>";
	text += "    <thead>";
	text += "      <tr>";
	text += "        <th>Id</th>";
	text += "        <th>Category</th>";
	text += "        <th>Description</th>";
	text += "        <th>Assign To</th>";
	text += "        <th>Action</th>";
	text += "      </tr>";
	text += "    </thead>";
	text += "    <tbody id='ticketCategoryList'>";
	text += "    </tbody>";
	text += "  </table>";
	text += "</div>";
	$("#ticketCategoryParentList").append(text);
	ticketManagementCategoryPagination(sourceleadUrl + "/getTicketCategoryList?searchValue=" + $("#ticketCategoryDataTableSearch").val());
}

function cancelTicketCategory() {
	$("#ticketCategoryId").val(0);
	if(document.getElementById("addTicketCategoryForm") !== null) {
		document.getElementById("addTicketCategoryForm").reset();
		$("#notifyTo").val("");
		$("#assignedTo option").removeAttr("selected");
		$("#assignedTo").chosen();
		$("#assignedTo").trigger("chosen:updated");
	}
	$(".mask, #modalTasktype").fadeOut();
	$(".location-alert").fadeOut();
}
function saveTicketCategory() {
	if ($('#addTicketCategoryForm').validate(false, validationSettings)){
		if ($("#notifyTo").val() !== "") {
			for (var e in $("#notifyTo").val().split(",")) {
				if (!jQueryFormUtils.validateEmail($("#notifyTo").val().split(",")[e].replace(/\s+/g, ''))) {
					flag = false;
					$("#notifyTo").parent().append("<p class='jquery_form_error_message'>Enter valid email id</p>");
					return;
				}
			}
		}
		var formData = getFormData1("addTicketCategoryForm");
		$.ajax({
			 type : "POST",
			 data: formData,
		     processData: false,
		     contentType:false, 
		     url : sourceleadUrl+"/saveTicketCategory",
			 success : function(response) {
		  		if (response && (response.response === "INVALID_DATA" || response.response === "DUPLICATE_NAME")) {
					showError(response.responseMessage);
				} else if (response.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					if(response.object > 0) {
						$(".success_pop span").html("TicketCategory saved successfully...");
						$(".success_pop").show();
						cancelTicketCategory();
						setTimeout(function() {
						getTicketCategoryList();
						$(".success_pop").hide();
				 		}, 1000);
					}
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
			},
		});
		return true
	}else{
		return false;
	}
}
$('body').on('blur', '#addTicketCategoryForm', function() {
	$("#addTicketCategoryForm").showHelpOnFocus();
	$("#addTicketCategoryForm").validateOnBlur(false, validationSettings);  
});

function editTicketCategory(id){
	$("#assignedTo option").removeAttr("selected");
	$("#assignedTo").chosen();
	$("#assignedTo").trigger("chosen:updated");
	$.ajax({
		type : "POST",
 		url : sourceleadUrl+ "/getTicketCategoryDataById?ticketCategoryId="+id,
		success : function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var data = response.object;
				$("#ticketCategoryFormHead").html("Edit Category");
				$("#ticketCategoryId").val(data.id);
				$("#ticketCategoryName").val(data.name);
				$("#ticketCategoryDesc").val(data.description);
				for(var e in data.assignedTo) {
					$("#assignedTo option[value='" + data.assignedTo[e] + "']").prop("selected", true);
				}
				$("#assignedTo").chosen();
	     	    $("#assignedTo").trigger("chosen:updated");
	     	    
				$("#notifyTo").val(data.notifyTo);
				$(".mask, #modalTasktype").fadeIn();
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		}
	});
}

function changeTicketCategoryStatus() {
	var id = $("#confirmId").data("id");
	var status = $("#confirmId").data("status");
	$.post(sourceleadUrl + "/changeTicketCategoryStatusDataById?id="+ id + "&status=" + status, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				closeDeletePopup();
				getTicketCategoryList();
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}



//===================================   Ticket Management Priority  =======================
function getTicketPriorityList(){
	$("#ticketPriorityParentList").empty();
	var text = "<table style='width: 100%;' id='ticketManagementPriorityTable'>";
	text += "  <thead>";
	text += "    <tr>";
	text += "        <th>Id</th>";
	text += "        <th>Icon</th>";
	text += "        <th>Priority</th>";
	text += "        <th>Description</th>";
	text += "        <th>Color</th>";
	text += "        <th>Action</th>";
	text += "      </tr>";
	text += "  </thead>";
	text += "  <tbody id='ticketPriorityList'>";
	text += "  </tbody>";
	text += "</table>";
	$("#ticketPriorityParentList").append(text);
	ticketManagementPriorityPagination(sourceleadUrl + "/getTicketPriorityList?searchValue=" + $("#ticketPriorityDataTableSearch").val());
}

function saveTicketPriority() {
	if ($('#addTicketPriorityForm').validate(false, validationSettings)){
		var formData = getFormData("addTicketPriorityForm");
		$.post(sourceleadUrl + "/saveTicketPriority", formData, function(response) {
			if (response && (response.response === "INVALID_DATA" || response.response === "DUPLICATE_NAME")) {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object > 0) {
					$(".success_pop span").html("TicketPriority saved successfully...");
					$(".success_pop").show();
					cancelTicketPriority();
					setTimeout(function() {
					getTicketPriorityList();
					$(".success_pop").hide();
			 		}, 1000);
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
		return true
	}else{
		return false;
	}
}
$('body').on('blur', '#addTicketPriorityForm', function() {
	$("#addTicketPriorityForm").showHelpOnFocus();
	$("#addTicketPriorityForm").validateOnBlur(false, validationSettings);  
});
function cancelTicketPriority() {
	$("#ticketPriorityId").val(0);
	if(document.getElementById("addTicketPriorityForm") !== null) {
		document.getElementById("addTicketPriorityForm").reset();
		$("#addTicketPriorityForm").find("input[data-id='uploadBtn']").remove();
		$("#addTicketPriorityForm").find("p[class='jquery_form_error_message']").remove();
		$("#refreshImage").val(false);
	}
	$(".mask, #modalTasktype").fadeOut();
	$(".location-alert").fadeOut();
}
function eidtTicketPriority(id){
	$.ajax({
		type : "POST",
 		url : sourceleadUrl+ "/getTicketPriorityDataById?ticketPriorityId="+id,
		success : function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var data = response.object;
				$("#ticketPriorityFormHead").html("Edit Priority");
				$("#ticketPriorityId").val(data.id);
				$("#ticketPriorityName").val(data.name);
				$("#favcolorP").val(data.colorCode);
				$("#favcolor").val(data.colorCode);
				$("#uploadFile").val(data.fileName);
				$("#ticketPriorityDesc").val(data.description);
				$(".mask, #modalTasktype").fadeIn();
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		}
	});
}

function changeTicketPriorityStatus() {
	var id = $("#confirmId").data("id");
	var status = $("#confirmId").data("status");
	$.post(sourceleadUrl + "/changeTicketPriorityStatusDataById?id="+ id + "&status=" + status, function(response){
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				closeDeletePopup();
				getTicketPriorityList();
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function refreshPriorityImage() {
	$("#refreshImage").val(true);
	$("#addTicketPriorityForm").find("input[data-id='uploadBtn']").remove();
	$("#uploadFile").val("");
}

		//=============================	Ticket Management Status	===============================================


function getTicketStatusList() {
	$("#ticketStatusParentList").empty();
	var text = "<table style='width: 100%;' id='ticketManagementStatusTable'>";
	text += "		<thead>";
	text += "			<tr>";
	text += "				<th>Id</th>";
	text += "				<th>Status</th>";
	text += "				<th>Description</th>";
	text += "				<th>Comment</th>";
	text += "				<th>Action</th>";
	text += "			</tr>";
	text += "		</thead>";
	text += "		<tbody id='ticketStatusList'>";

	text += "		</tbody>";
	text += "	</table>";
	text += "</div>";
	$("#ticketStatusParentList").append(text);
	ticketManagementStatusPagination(sourceleadUrl + "/getTicketStatusList?searchValue=" + $("#ticketStatusDataTableSearch").val());
}

function saveTicketStatus() {
	if ($('#addTicketStatusForm').validate(false, validationSettings)){
		var formData = getFormData("addTicketStatusForm");
		$.post(sourceleadUrl + "/saveTicketStatus", formData, function(response) {
			if (response && (response.response === "INVALID_DATA" || response.response === "DUPLICATE_NAME")) {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object > 0) {
					$(".success_pop span").html("TicketStatus saved successfully...");
					$(".success_pop").show();
					cancelTicketStatus();
					setTimeout(function() {
					getTicketStatusList();
					$(".success_pop").hide();
			 		}, 1000);
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
		return true
	}else{
		return false;
	}
}
$('body').on('blur', '#addTicketStatusForm', function() {
	$("#addTicketStatusForm").showHelpOnFocus();
	$("#addTicketStatusForm").validateOnBlur(false, validationSettings);  
});


function cancelTicketStatus() {
	$("#ticketStatusId").val(0);
	if(document.getElementById("addTicketStatusForm") !== null)
		document.getElementById("addTicketStatusForm").reset();
	$(".mask, #modalTasktype").fadeOut();
	$(".location-alert").fadeOut();
}

function editTicketStatus(id){
	$.ajax({
		type : "POST",
 		url : sourceleadUrl+ "/getTicketStatusDataById?ticketStatusId="+id,
		success : function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var data = response.object;
				$("#ticketStatusFormHead").html("Edit Status");
				$("#ticketStatusId").val(data.id);
				$("#ticketStatusName").val(data.name);
				$("#ticketCommentRequired").val(data.commentRequired ? "1" : "0");
				$("#ticketStatusDesc").val(data.description);
				$(".mask, #modalTasktype").fadeIn();
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		}
	});
}

function changeTicketStatus() {
	var id = $("#confirmId").data("id");
	var status = $("#confirmId").data("status");
	$.post(sourceleadUrl + "/changeTicketStatusDataById?id="+ id + "&status=" + status, function(response){
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				closeDeletePopup();
				getTicketStatusList();
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
	
}

function uploadPriorityImage() {
	fileUpload('uploadBtn', 'ticketPriority', 'addTicketPriorityForm');
	$("#refreshImage").val(false);
}