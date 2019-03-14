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
function ticketManagementCategoryPagination(url){
	var colIndex = 0;
    $('#ticketManagementCategoryTable').DataTable( {
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
           {"data": "category"},
           {"data": "description"},
           {"data": "assignTo"},
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
	            "data": "category",
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
	            "data": "assignTo",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            	var assignTo = "";
	                for(var a in opt.assignedTo) {
	                  assignTo += (assignTo === "" ? "" : ",") + opt.assignedTo[a];
	                }
	            	var result = "";
           		   if(assignTo !== ""){
	     	        	result = result + '<td><div  class="tbldata-dotdot-sm">' + assignTo + '</div></td>';
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
	            	result +='		<a href="javascript:void(0)" title="Edit TicketCategory" onclick="editTicketCategory(' + opt.id + ')">';
	            	result +='		<i class="fa fa-pencil"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='	<div class="text-center">';
	            	if(opt.status === 0) {
	            		result +='		<a href="javascript:void(0)" title="Active TicketCategory" onclick="showDeletePopup(' + opt.id + ', 1)">';
	            		result +='		<i class="fa fa-edit"></i>';
	            	} else {
	            		result +='		<a href="javascript:void(0)" title="In-active TicketCategory" onclick="showDeletePopup(' + opt.id + ', 0)">';
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
	    		row.setAttribute('id',"ticketCategory_"+data.id);
    	    },
	    	"initComplete": function( settings, json ) {
	    		 $('#ticketCategoryDataTableSearch').removeAttr('disabled');
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
   	 	        body.highlight($("#ticketCategoryDataTableSearch").val());   
   	 			
   	 			}, 1000);
   	      });
   	   	}
   });
   $("#ticketManagementCategoryTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   $("input[type=search]").hide();
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

function ticketManagementPriorityPagination(url){
	var colIndex = 0;
    $('#ticketManagementPriorityTable').DataTable( {
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
           {"data": "icon"},
           {"data": "priority"},
           {"data": "description"},
           {"data": "color"},
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
	            "data": "icon",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.fileURL != null && opt.fileURL != '' && opt.fileURL != 'undefined') {
           			   result = result + '<div class="priorityImage"><img src=' + opt.fileURL + ' alt="' + opt.fileName + '" title="Priority Image"></img></div>';
           		   } else {
						result = result +"<td><div>-----</div></td>";
           	       }
		           		return result;
	             }
	        },
	        {
	            "targets": colIndex++,
	            "data": "priority",
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
	            "sortable": true,
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
	            "data": "color",
	            "sortable": false,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
           		   if(opt.colorCode != null && opt.colorCode != '' && opt.colorCode != 'undefined'){
	     	        	result = result + '<div class="tblminwidth-75"><small class="colorBox" id="colorBox" style="background:' + opt.colorCode + '"></small></div>';
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
	            	result +='		<a href="javascript:void(0)" title="Edit TicketPriority" onclick="eidtTicketPriority(' + opt.id + ')">';
	            	result +='		<i class="fa fa-pencil"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='	<div class="text-center">';
	            	if(opt.status === 0) {
	            		result +='		<a href="javascript:void(0)" title="Active TicketPriority" onclick="showDeletePopup(' + opt.id + ', 1)">';
	            		result +='		<i class="fa fa-edit"></i>';
	            	} else {
	            		result +='		<a href="javascript:void(0)" title="In-active TicketPriority" onclick="showDeletePopup(' + opt.id + ', 0)">';
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
	    		row.setAttribute('id',"ticketPriority_"+data.id);
    	    },
	    	"initComplete": function( settings, json ) {
	    		 $('#ticketPriorityDataTableSearch').removeAttr('disabled');
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
   	 	        body.highlight($("#ticketPriorityDataTableSearch").val());   
   	 			
   	 			}, 1000);
   	      });
   	   	}
   });
   $("#ticketManagementPriorityTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   $("input[type=search]").hide();
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

function ticketManagementStatusPagination(url){
	var colIndex = 0;
    $('#ticketManagementStatusTable').DataTable( {
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
           {"data": "status"},
           {"data": "description"},
           {"data": "comment"},
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
	            "data": "status",
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
	            "sortable": true,
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
	            "data": "comment",
	            "sortable": true,
	            "render": function ( data, type, opt, meta ) {
	            		var result = "";
	            		result = result + '<td><div  class="tbldata-dotdot-sm">' + (opt.commentRequired ? "YES" : "NO") + '</div></td>';
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
	            	result +='		<a href="javascript:void(0)" title="Edit TicketStatus" onclick="editTicketStatus(' + opt.id + ')">';
	            	result +='		<i class="fa fa-pencil"></i>';
	            	result +='		</a>';
	            	result +='	</div>';
	            	result +='	<div class="text-center">';
	            	if(opt.status === 0) {
	            		result +='		<a href="javascript:void(0)" title="Active TicketStatus" onclick="showDeletePopup(' + opt.id + ', 1)">';
	            		result +='		<i class="fa fa-edit"></i>';
	            	} else {
	            		result +='		<a href="javascript:void(0)" title="In-active TicketStatus" onclick="showDeletePopup(' + opt.id + ', 0)">';
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
	    		row.setAttribute('id',"ticketStatus_"+data.id);
    	    },
	    	"initComplete": function( settings, json ) {
	    		 $('#ticketStatusDataTableSearch').removeAttr('disabled');
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
   	 	        body.highlight($("#ticketStatusDataTableSearch").val());   
   	 			
   	 			}, 1000);
   	      });
   	   	}
   });
   $("#ticketManagementStatusTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>");
   $("input[type=search]").hide();
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