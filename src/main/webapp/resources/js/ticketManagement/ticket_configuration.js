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

function ticketManagementTypePagination(url){
	var colIndex = 0;
    $('#ticketManagementTypeTable').DataTable( {
    	"processing": true,
       "serverSide": true,
       "searching": true,
       "searchHighlight": true,
       "ajax": $.fn.dataTable.pipeline( {
           url: url,
           method: 'POST',
           pages: 1
       }),
       "columns": [
    	   {"data": "id"},
           {"data": "name"},
           {"data": "description"},
           {"data": "actions" }
       	],
	       "columnDefs": [
    	   {
	            "targets": colIndex++,
	            "data": "id",
	            "visible": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.id != null && opt.id != '' && opt.id != 'undefined') {
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.id+'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		           		return result;
	             }
	        },
            {
	            "targets": colIndex++,
	            "data": "name",
	            "sortable": true,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.name != null && opt.name != '' && opt.name != 'undefined') {
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.name+'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		           		return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "description",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.description != null && opt.description != '' && opt.description != 'undefined'){
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">'+opt.description+'</div></td>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		                return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "actions",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            	var result = '<div class=" widthIcon widthIcon-three">';
	            	result +='	<div class="text-center">';
	            	result +='		<a href="javascript:void(0)" title="Edit TicketType" onclick="editTicketType(' + opt.id + ', -1)">';
	            	result +='		<i class="fa fa-pencil"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	
	            	
	            	result +='	<div class="text-center">';
	            	if(opt.status === 0) {
	            		result +='		<a href="javascript:void(0)" title="Active TicketType" onclick="showDeletePopup(' + opt.id + ', 1)">';
	            		result +='		<i class="fa fa-edit"></i>';
	            	} else {
	            		result +='		<a href="javascript:void(0)" title="In-active TicketType" onclick="showDeletePopup(' + opt.id + ', 0)">';
	            		result +='		<i class="fa fa-trash"></i>';
	            	}
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='</div>';
           	        return result;
	             }
	        }
           ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"createdRow": function ( row, data, index ) {
	    		row.setAttribute('id',"ticketType_"+data.id);
    	    },
	    	"initComplete": function( settings, json ) {
	    		 $('#ticketTypeDataTableSearch').removeAttr('disabled');
	    	},
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
	     drawCallback: function(){
   	      $('a.paginate_button', this.api().table().container()).on('click', function(){
   	    	setTimeout(function () {
   	  		var body = $( table.table().body() );
   	 	        body.unhighlight();
   	 	        body.highlight($("#ticketTypeDataTableSearch").val());   
   	 			
   	 			}, 1000);
   	      });
   	   	}
   });
   
  
   $("#ticketManagementTypeTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   $("input[type=search]").hide();
   
   $("#ticketTypeDataTableSearch").keyup(function(e) {
   	   if(e.which === 13) {
   		getTicketTypeList();
   	   }
   	});
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
	 $('#ticketManagementCategoryTable').DataTable({
		 	"aaSorting": [[1,'desc']],
		 	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		"sZeroRecords": "", "sEmptyTable": ""
	     	}
	 });
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
				$("#ticketCategoryFormHead").html("Edit Category");
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
							    text += ' <td><div class="tblminwidth-150">' + (response.data[e].fileURL !== null && response.data[e].fileURL !== "" ? "<div class='priorityImage'><img src='" + response.data[e].fileURL + "' alt='images' title='Priority Images'></div>" : "--") + '</i></div></td>';
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
		 	"aaSorting": [[1,'desc']],
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
				$("#ticketPriorityFormHead").html("Edit Priority");
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
			           text+= " <td><div class='tbldata-dotdot-big'>" + (response.data[e].commentRequired ? "YES" : "NO") + "</div></td>";
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
		 	"aaSorting": [[1,'desc']],
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
				$("#ticketStatusFormHead").html("Edit Status");
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