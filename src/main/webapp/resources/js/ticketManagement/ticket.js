$(document).ready(function() {
	
});

//============================== Ticket Management List 	=============================================
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
	            text+= "      <td><div class='tblminwidth-150'><a href='/app/ticketSummary?uuid=" + response.data[e].uuid + "' title='Ticket Management Summary'>" + response.data[e].generatedId + "</a></div></td>";
	            text+= "      <td><div class='tbldata-dotdot-vsm'>" + response.data[e].title + "</div></td>";
	            text+= "     <td><div class='tbldata-dotdot-vsm'>" + response.data[e].ticketCategory + "</div></td>";
	            text+= "     <td><div class='tbldata-dotdot-vsm'>" + response.data[e].ticketStatus + "</div></td>";
	            text+= "   	<td><div class='tblminwidth-75'>" + response.data[e].ticketPriority + "</div></td>";
	            text+= "    <td><div class='tbldata-dotdot-vsm'>" + response.data[e].createdById + "</div></td>";
	            var assignTo = "";
				for(var a in response.data[e].assignedTo) {
					assignTo += (assignTo === "" ? "" : ",") + response.data[e].assignedTo[a];
				}
				text += "    <td><div class='tbldata-dotdot'>" + (assignTo === "" ? "--" : assignTo) + "</div></td>";
	            text+= "      <td>";
				text+= "		  <ul class=list-inline list-unstyled'>";
				text+= "			  <li><i class='fa fa-pencil cursorPointer'></i></li>";
				text+= "			  <li><i class='fa fa-trash cursorPointer'></i></li>";
				text+= "		  </ul>";
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