$(document).ready(function() {
	$("#addPop").click(function() {
		$("#ticketTypeFormHead").html("Edit Type");
		$("#ticketCategoryFormHead").html("Edit Type");
		$("#ticketPriorityFormHead").html("Edit Type");
		$("#ticketStatusFormHead").html("Edit Type");
		$(".mask, #modalTasktype").fadeIn();
	});
	$(".popClose").click(function() {
		cancelTicketType();
		cancelTicketCategory();
		cancelTicketPriority();
		cancelTicketStatus();
		cancelTicket();
		
		/*$(".mask, #modalTasktype").fadeOut();
		document.getElementById("addTicketTypeForm").reset();*/
	});
	if(document.getElementById("uploadBtn") !== null) {
		document.getElementById("uploadBtn").onchange = function () {
		    $('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
		};
	}
});

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
	$("#ticketTypeList").empty();
	$.post(sourceleadUrl + "/getTicketTypeList", {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.data.length > 0) {
				for(var e in response.data) {
					var text = "<tr>";
					text+= "  <td><div class='tblminwidth-150'>" + response.data[e].name + "</div></td>";
					text+= "  <td><div class='tbldata-dotdot-big'>" + response.data[e].description + "</div></td>";
					text+= "  <td>";
					text+= "	  <ul class='list-inline list-unstyled'>";
					text+= "		  <li><i class='fa fa-pencil cursorPointer' onclick='editTicketType("+response.data[e].id+");'></i></li>";
					if(response.data[e].status === 0)
						text+= "		  <li><i class='fa fa-edit cursorPointer' onclick='showDeletePopup("+response.data[e].id+", 1);'></i></li>";
					else 
						text+= "		  <li><i class='fa fa-trash cursorPointer' onclick='showDeletePopup("+response.data[e].id+", 0);'></i></li>";
					text+= "	  </ul>";
					text+= "	</td>";
					text+= "</tr>";
		    		$("#ticketTypeList").append(text);
				}
			} else {
				alert("Empty list returned....");
			}
			ticketManagementTypePagination();
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function ticketManagementTypePagination(){
	 $('#ticketManagementTypeTable').DataTable( { 
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

function saveTicketType() {
	if ($('#addTicketTypeForm').validate(false, validationSettings)){
		var formData = getFormData("addTicketTypeForm");
		$.post(sourceleadUrl + "/saveTicketType", formData, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object === true) {
					alert(response.responseMessage);
					cancelTicketType();
					getTicketTypeList();
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
}

function cancelTicketType() {
	if(document.getElementById("addTicketTypeForm") !== null)
		document.getElementById("addTicketTypeForm").reset();
	$(".mask, #modalTasktype").fadeOut();
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
	alert("ajax call " + id)
	$.post(sourceleadUrl + "/changeTicketTypeStatusDataById?id="+ id + "&status=" + status, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				alert(response.responseMessage);
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
	$("#ticketCategoryList").empty();
	$.post(sourceleadUrl + "/getTicketCategoryList", {},function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if (response.data.length > 0) {
				for ( var e in response.data) {
					var text = "<tr>";
					text += "     <td><div class='tblminwidth-150'>" + response.data[e].name + "</div></td>";
					text += "    <td><div class='tbldata-dotdot-big'>" + response.data[e].description + "</div></td>";
					var assignTo = "";
					for(var a in response.data[e].assignedTo) {
						assignTo += (assignTo === "" ? "" : ",") + response.data[e].assignedTo[a];
					}
					text += "    <td><div class='tbldata-dotdot'>" + (assignTo === "" ? "--" : assignTo) + "</div></td>";
					text += "    <td>";
					text += " 	  <ul class='list-inline list-unstyled'>";
					text += " 		  <li><i class='fa fa-pencil cursorPointer' onclick='editTicketCategory(" + response.data[e].id + ");'></i></li>";
					
					if(response.data[e].status === 0)
						text+= "		  <li><i class='fa fa-edit cursorPointer' onclick='showDeletePopup("+response.data[e].id+", 1);'></i></li>";
					else 
						text+= "		  <li><i class='fa fa-trash cursorPointer' onclick='showDeletePopup("+response.data[e].id+", 0);'></i></li>";
					text += " 	  </ul>";
					text += " 	</td>";
					text += "  </tr>";
					$("#ticketCategoryList").append(text);
					
				}
			} else {
				alert("Empty list returned....");
			}
			ticketManagementCategoryPagination();
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}
function ticketManagementCategoryPagination(){
	 $('#ticketManagementCategoryTable').DataTable( { 
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
function cancelTicketCategory() {
	if(document.getElementById("addTicketCategoryForm") !== null)
		document.getElementById("addTicketCategoryForm").reset();
	$(".mask, #modalTasktype").fadeOut();
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
		  		if (response && response.response === "INVALID_DATA") {
					showError(response.responseMessage);
				} else if (response.response === "AWKWARD") {
					location.href = "/errorPage";
				} else if (response.response === "SUCCESS") {
					if(response.object === true) {
						alert(response.responseMessage);
						cancelTicketCategory();
						getTicketCategoryList();
					}
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
			},
		});
	}
}

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
				$("#ticketCategoryFormHead").html("Edit Type");
				$("#ticketCategoryId").val(data.id);
				$("#ticketCategoryName").val(data.name);
				$("#ticketCategoryDesc").val(data.description);
				for(var e in data.assignedTo) {
					$("#assignedTo option[value='" + data.assignedTo[e] + "']").attr("selected", true);
				}
				$("#assignedTo").chosen();
	     	    $("#assignedTo").trigger("chosen:updated");
	     	    
				$("#notifyTo").html(data.notifyTo);
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
	//alert("ajax call " + id)
	$.post(sourceleadUrl + "/changeTicketCategoryStatusDataById?id="+ id + "&status=" + status, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				alert(response.responseMessage);
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
	$("#ticketTypeList").empty();
	$.post(sourceleadUrl + "/getTicketPriorityList", {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.data.length > 0) {
				for(var e in response.data) {
					
					var text = "<tr>";
							    text += ' <td><div class="tblminwidth-150">' + (response.data[e].fileName !== null && response.data[e].fileName !== "" ? response.data[e].fileName : "--") + '</i></div></td>';
							    text +='<td><div class="tblminwidth-150">' + response.data[e].name+ '</div></td>';
							    text += '<td><div class="tblminwidth-150">' + response.data[e].description + '</div></td>';
							    text += '<td>';
							    text += '<div class="tblminwidth-75"><small class="colorBox" id="colorBox" style="background:'+response.data[e].colorCode+'"></small></div>';
							    text += '</td>';
							    text += '<td>';
							    text +='<ul class="list-inline list-unstyled">';
							    text += '<li><i class="fa fa-pencil cursorPointer" onclick="eidtTicketPriority('+ response.data[e].id +');"></i></li>';
							    if(response.data[e].status === 0)
									text+= '	  <li><i class="fa fa-edit cursorPointer" onclick="showDeletePopup('+response.data[e].id+', 1);"></i></li>';
								else 
									text+= '		  <li><i class="fa fa-trash cursorPointer" onclick="showDeletePopup('+response.data[e].id+', 0);"></i></li>';
							    text += '</ul>';
							    text +='</td>';
							    text += '</tr>';
								$("#ticketPriorityList").append(text);
						}
					  } else {
								alert("Empty list returned....");
					   }
			ticketManagementPriorityPagination();	
				} else if (response.indexOf('Sign In') !== -1) {
					location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
		});
}

function ticketManagementPriorityPagination(){
	 $('#ticketManagementPriorityTable').DataTable( { 
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

function saveTicketPriority() {
	if ($('#addTicketPriorityForm').validate(false, validationSettings)){
		var formData = getFormData("addTicketPriorityForm");
		$.post(sourceleadUrl + "/saveTicketPriority", formData, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object === true) {
					//alert(response.responseMessage);
					cancelTicketPriority();
					getTicketPriorityList();
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
}
function cancelTicketPriority() {
	if(document.getElementById("addTicketPriorityForm") !== null)
		document.getElementById("addTicketPriorityForm").reset();
	$(".mask, #modalTasktype").fadeOut();
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
				$("#ticketPriorityFormHead").html("Edit Type");
				$("#ticketPriorityId").val(data.id);
				$("#ticketPriorityName").val(data.name);
				$("#favcolorP").val(data.colorCode);
				$("#favcolor").val(data.colorCode);
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
	//alert("ajax call " + id)
	$.post(sourceleadUrl + "/changeTicketPriorityStatusDataById?id="+ id + "&status=" + status, function(response){
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				alert(response.responseMessage);
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


		//=============================	Ticket Management Status	===============================================


function getTicketStatusList() {
	$("#ticketStatusList").empty();
	$.post(sourceleadUrl + "/getTicketStatusList", {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.data.length > 0) {
				for(var e in response.data) {
					var text = "<tr>";
			           text+= " <td><div class='tblminwidth-150'>" + response.data[e].name + "</div></td>";
			           text+= " <td><div class='tbldata-dotdot-big'>" + response.data[e].description + "</div></td>";
			           text+= " <td><div class='tbldata-dotdot-big'>" + response.data[e].description + "</div></td>";
			           text+= " <td>";
						text+= "<ul class='list-inline list-unstyled'>";
						text+= "			  <li><i class='fa fa-pencil cursorPointer' onclick='editTicketStatus("+response.data[e].id+");'></i></li>";
						if(response.data[e].status === 0)
							text+= "		  <li><i class='fa fa-edit cursorPointer' onclick='showDeletePopup("+response.data[e].id+", 1);'></i></li>";
						else 
							text+= "		  <li><i class='fa fa-trash cursorPointer' onclick='showDeletePopup("+response.data[e].id+", 0);'></i></li>";
						text+= "		  </ul>";
						text+= "		</td>";
			             text+= "   </tr>";
		    		$("#ticketStatusList").append(text);
				}
			} else {
				alert("Empty list returned....");
			}
			ticketManagementStatusPagination();
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function ticketManagementStatusPagination(){
	 $('#ticketManagementStatusTable').DataTable( { 
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


function saveTicketStatus() {
	if ($('#addTicketStatusForm').validate(false, validationSettings)){
		var formData = getFormData("addTicketStatusForm");
		$.post(sourceleadUrl + "/saveTicketStatus", formData, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object === true) {
					alert(response.responseMessage);
					cancelTicketStatus();
					getTicketStatusList();
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
}

function cancelTicketStatus() {
	if(document.getElementById("addTicketStatusForm") !== null)
		document.getElementById("addTicketStatusForm").reset();
	$(".mask, #modalTasktype").fadeOut();
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
				$("#ticketStatusFormHead").html("Edit Type");
				$("#ticketStatusId").val(data.id);
				$("#ticketStatusName").val(data.name);
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
	//alert("ajax call " + id)
	$.post(sourceleadUrl + "/changeTicketStatusDataById?id="+ id + "&status=" + status, function(response){
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			if(response.object === true) {
				alert(response.responseMessage);
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
	/*$(".file_mask").show();
	
	var data = new FormData();
	var files = document.getElementById("uploadBtn").files;
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
		$("#addTicketPriorityForm input[data-id*=uploadBtn]").remove();
		$(".file_mask").hide();
		alert("Please select valid file (zip files and rar files are not allowed)");
		return;
	}
	
	$.ajax({ 
		url: sourceleadUrl + ("/ws/upload/ticketPriority"), 
		data: data, 
		processData: false, 
		type: 'POST', 
		contentType: false, 
		success: function (data) {
			if(data && data.length > 0) {
				var fileUrl = data[0].fileURL.replace("ticketPriority","temp");
				var fileName = data[0].fileName;
				$("#uploadFile").val(fileName);
				$(".file_mask").hide();
			}
		}, 
		error: function (err) { 
			 $(".file_mask").hide();
		} 
	});*/
}