var commentUsers = [], selectedUsers = [];
var commentConfig = {};

$(document).ready(function() {
	$.post(sourceleadUrl + "/getUserListByLoginOrgLocationId", {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
        	if(response.data.length > 0 ){
        		// Define data source for At.JS.
        		commentUsers = response.data;
        		initializeFroalaEditor();
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
	
});

function initializeFroalaEditor() {
	/*$('#ticketComment').froalaEditor('destroy');*/
    // Define config for At.JS.
    commentConfig = {
      at: "@",
      data: commentUsers,
      displayTpl: '<li data-id="${id}">${fullName} <small>${email}</small></li>',
      limit: 200
    }
    
	// Initialize editor.
    /*$('#ticketComment')
      .on('froalaEditor.initialized', function (e, editor) {
        editor.$el.atwho(commentConfig);

        editor.$el.on('inserted.atwho', function (event, $li, bEvent) {
          editor.$el.find('.atwho-inserted').removeAttr('contenteditable');
          console.log("inserted.atwho : " + $($li).data("id") + " : " + $($li).find("small").html());
          selectedUsers.push($($li).data("id"));
        })
 
      	editor.events.on('keydown', function (e) {
          if (e.which == $.FroalaEditor.KEYCODE.ENTER && editor.$el.atwho('isSelecting')) {
            return false;
          }
        }, true);

      })
      .froalaEditor()
      
      selectedUsers.splice($.inArray(3164, selectedUsers), 1);

      $(".fr-toolbar").hide();*/
//    $("#ticketComment").Editor();
    
    $('#ticketComment').atwho(commentConfig);
    $('#ticketComment').on('inserted.atwho', function (event, $li, bEvent) {
        //editor.$el.find('.atwho-inserted').removeAttr('contenteditable');
//        console.log("inserted.atwho : " + $($li).data("id") + " : " + $($li).find("small").html());
        selectedUsers.push($($li).data("id"));
      }).on('reposition.atwho', function (event, offset) {
    	  var removeUser = $("#ticketComment").find("span[class='atwho-query']").data("id");
    	  if(removeUser) {
	    	  selectedUsers.splice($.inArray(removeUser, selectedUsers), 1);
	    	  /*console.log("reposition.atwho : " + removeUser);
	    	  console.log("selected Users : " + selectedUsers.toString());*/
    	  }
        })
}

//================================	TicketComment		==============================================
function getTicketCommentUserList() {
	$.post(sourceleadUrl + "/getUserListByLoginOrgLocationId", {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
        	if(response.data.length > 0 ){
        		commentUsers = response.data;
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

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
                    /*text += "<div class='comments-div comments-inner clearfix'>";
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
                    text += "  </div>";*/
        			
        			text += "	<li>";
        			text += "		<section class='clearfix mt mb goals-list'>";
        			text += "			<div class='col-md-12 mt'>";
        			text += "				<div class='media-left goalsImg mr'>";
        			var src = "/images/userprofile-img.jpg";
        			if(response.data[e].createdByImageURL !== null && response.data[e].createdByImageURL !== "") {
        				src = response.data[e].createdByImageURL;
        			}
        			text += "					<img src='" + src + "' class='media-object'>";
        			text += "				</div>";
        			text += "				<div class='media-body'>";
        			text += "					<div class='row'>";
        			text += "						<div class='col-md-10'>";
        			text += "								<div class='clearfix'>";
        			text += "										<a href='javascript:void(0)'>";
        			text += "											<h5 class='textBold tbldata-dotdot-big inlineBlock'>" + response.data[e].createdByName + "</h5>";
        			text += "										</a>";
        			text += "										<div class='clearfix'></div>";
        			text += "										<p class='text-muted small textBold'>" + response.data[e].createdDate + "</p>";
        			text += "									</div>";
        			text += "									<div class='clearfix'>";
        			text += "										<p class='clearfix'>" + response.data[e].comment + "</p>";
        			text += "									</div>";
        			text += "						</div>";
        			if(parseInt(loggedInUserId) === response.data[e].createdById) {
	        			text += "						<div class='col-md-2'>";
	        			text += "							<ul class='list-unstyled list-inline pull-right goalsEditDelete'>";
	        			text += "								<li><i class='fa fa-pencil cursorPointer' title='Edit Comment' onclick='editTicketComment(" + response.data[e].id + ");'></i></li>";
	        			text += "								<li><i class='fa fa-trash cursorPointer' title='Delete Comment' onclick='showDeleteCommentPopup(" + response.data[e].id + ", " + response.data[e].ticketId + ");'></i></li>";
	        			text += "							</ul>";
	        			text += "						</div>";
        			}
        			text += "					</div>";
        			text += "				</div>";
        			text += "			</div>";
        			text += "		</section>";
        			text += "	</li>";
        			
		    		$("#ticketCommentListDivId").append(text);
				}
			}
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
        };
function saveTicketComment(ticketId) {
	 if ($('#ticketComment_form').validate(false, validationSettings)) {
		var formData = getFormData("ticketComment_form");
		$.post(sourceleadUrl + "/saveTicketComment?ticketId="+ticketId,formData, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				if(response.object === true) {
					$(".success_pop span").html("Ticket Comment Saved successfully...");
					$(".success_pop").show();
					setTimeout(function() {
						$(".success_pop").hide();
			 		}, 1000);
					getTicketCommetList(ticketId);
					closeTicketComment();
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
		return true;
	}else{
			return false;
		  } 
}
$('body').on('blur', '#ticketComment_form', function() {
	$("#ticketComment_form").showHelpOnFocus();
	$("#ticketComment_form").validateOnBlur(false, validationSettings);  
});
function closeTicketComment(){
	var ticketIdEdit = $("#ticketCommentId").val();
	$('#ticketComment_form').find('p.jquery_form_error_message').remove();
	if(ticketIdEdit ==null || ticketIdEdit =="" || ticketIdEdit =="undefined"){
	$("#ticketComment").val("");
	}
	$("#ticketComment").val("");
	$('.fr-element.fr-view').html("");
	$("#ticketCommentPopup, .mask").hide();
}

function editTicketComment(ticketId) {
	selectedUsers = [];
	$.post(sourceleadUrl + "/getTicketComment?ticketCommentId="+ticketId, {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			var comment = response.object;
			$("#ticketCommentId").val(comment.id);
			$("#ticketComment").html(comment.comment);
			$('.fr-element.fr-view').html(comment.comment);
			if(comment.mentionedUserIds !== null && comment.mentionedUserIds !== "") {
				for(var e in comment.mentionedUserIds.split(",")) {
					selectedUsers.push(parseInt(comment.mentionedUserIds.split(",")[e]));
				}
			}
			$("#ticketCommentPopup, .mask").show();
			/*initializeFroalaEditor();*/
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function deleteTicketComment() {
	var id = $("#confirmDeleteCommentId").data("id");
	var ticketId = $("#confirmDeleteCommentId").data("ticket_id");
	$.post(sourceleadUrl + "/deleteTicketComment?ticketCommentId="+id, {}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			$(".success_pop span").html("Ticket comment deleted successfully...");
			$(".success_pop").show();
			setTimeout(function() {
				$(".success_pop").hide();
	 		}, 1000);
			getTicketCommetList(ticketId);
			closeDeleteCommentPopup();
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function showDeleteCommentPopup(commentId, ticketId) {
	$("#confirmDeleteCommentId").data("id", commentId);
	$("#confirmDeleteCommentId").data("ticket_id", ticketId);
	$("#deleteCommentPopup").slideDown(200);
	$(".mask").fadeIn(200);
}

function closeDeleteCommentPopup() {
	$("#deleteCommentPopup").slideUp(200);
	$(".mask").fadeOut(200);
}