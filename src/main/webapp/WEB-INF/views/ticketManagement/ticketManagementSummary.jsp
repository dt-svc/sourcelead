<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<jsp:include page="../ticketManagement/ticketPopups.jsp" flush="true"></jsp:include>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<link type="text/css" rel="stylesheet" href="/resources/css/comment/froala_editor.css"/>
<link type="text/css" rel="stylesheet" href="/resources/css/comment/jquery.atwho.css"/>


<script type="text/javascript" src="/resources/js/editor.js"></script>
<link href="/resources/css/editor.css" type="text/css" rel="stylesheet" />


<!-- <script type="text/javascript" src="/resources/js/comment/jquery.atwho.min.js"></script> -->
<script type="text/javascript" src="/resources/js/comment/jquery.atwho.js"></script>
<script type="text/javascript" src="/resources/js/comment/jquery.caret.min.js"></script>
<!-- <script type="text/javascript" src="/resources/js/comment/froala_editor.min.js"></script> -->
<script type="text/javascript" src="/resources/js/ticketManagement/ticket.js"></script>
<script type="text/javascript" src="/resources/js/ticketManagement/ticket_comment.js"></script>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Ticket Summary</h2>
      </div>
	<div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/ticketManagementList'/>">Ticket List</a> &raquo; Ticket Summary</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix">
        <div class="project_side_wrap_inn_tabs">
          <ul class="project-tabs-menu project-tabs-menu-custom">
            <li class="current"><a href="#tab-1">Details</a></li>
            <li class=""><a href="#tab-2" onclick="getTicketCommetList('${ticketSummary.id}');">Comment</a></li>
            <div class="clearfix"></div>
          </ul>
          <div class="project_tabs project_wrap">
            <div id="tab-1" class="project_tabs_inn" style="display: block;">
              <div class="project_tabs_inn_table">
                <div class="project_details col2Custom">
					<div class="useredit">
						<div class="width150px">
							<select class="sel" onchange="changeTicketStatus(this, ${ticketSummary.id}, ${ticketSummary.ticketStatusId});">
								<c:forEach items="${statusList}" var="status">
									<option data-comment="${status.commentRequired}" value="${status.id}" ${status.name == ticketSummary.ticketStatus ? "selected" : ""}>${status.name}</option>
								</c:forEach>
							</select>
						</div>
						<sec:authorize access="hasRole(\'ROLE_ADD_TICKET\')">
							<a href="${SourceLeadWeb_sourceleadUrl}/editTicket?uuid=${ticketSummary.uuid}"><i class="fa fa-pencil"></i>Edit</a>
						</sec:authorize>
						<div class="clearfix"></div>
					</div>
					<div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Ticket Id<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-5">${ticketSummary.generatedId}</div>
                  </div>
                  <div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Ticket Title<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-5">${ticketSummary.title}</div>
                  </div>
                  <div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Category<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-5">${ticketSummary.ticketCategory}</div>
                  </div>
                  <div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Type<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-5">${ticketSummary.ticketType}</div>
                  </div>
                  <div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Priority<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-10">
                    	<c:if test="${ticketSummary.ticketPriorityImage != null}">
	                     	<div class="priorityImage pull-left mr10"><img class="mr10" src="${ticketSummary.ticketPriorityImage}"></div>
                    	</c:if>
                    	<div class="pull-left">${ticketSummary.ticketPriority}</div>
                     </div>
                  </div>
                  <div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Assign To<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-10">
                      <div class="row">
                        <c:forEach items="${ticketSummary.ticketAssignToList}" var="assignTo">
	                        <div class="col-md-4 mb10">
	                          <div class="assigNeeImg mr10"> <img src="${assignTo.userImageURL != null && assignTo.userImageURL != '' ? assignTo.userImageURL : '/resources/images/userprofile-img.png'}" alt="" title="${assignTo.userName}"> </div>
	                          <label>${assignTo.userName}</label>
	                        </div>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                  <div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Notify To<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-10 professinal-details">
                      <ul class="list-unstyled list-inline mb0">
                      	<c:forEach items="${ticketSummary.notifyTo.split(',')}" var="notifyTo">
	                        <li>${notifyTo}</li>
                      	</c:forEach>
                      </ul>
                    </div>
                  </div>
                  <div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Description<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-10">${ticketSummary.description}</div>
                  </div>
                  <div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Requested By<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-10">${ticketSummary.createdBy}</div>
                  </div>
                  <div class="form-group clearfix">
                    <label class="col-md-2 paddingRight">Requested On<span class="pull-right paddingLeft">:</span></label>
                    <div class="col-md-10">${ticketSummary.createdDate}</div>
                  </div>
                  <c:if test="${ticketSummary.attachmentList.size() > 0}">
	                  <div class="form-group clearfix">
	                    <label class="col-md-2 paddingRight">Attachment(s)<span class="pull-right paddingLeft">:</span></label>
	                    <div class="col-md-10">
		                    <div class="row">
		                    	<c:forEach items="${ticketSummary.attachmentList}" var="attachment">
		                    		<div class="col-md-4 mb">
			                    		<div class="row">
			                    			<div class="col-md-8 tbldata-dotdot-200 inlineBlock">${attachment.fileName}</div>
			                    			<ul class="list-unstyled list-inline col-md-4 text-center">
			                    				<li><a href="javascript:void(0)" onclick="viewFile('${attachment.fileUrl}','${attachment.fileName}','${attachment.fileType}');"><i class="fa fa-eye cursorPointer" title="View"></i></a></li>
			                    				<li><a href="${attachment.fileUrl}" download="" class="glyphicon glyphicon-download-alt" title="Download"></a></li>
			                    			</ul>
			                    		</div>
		                    		</div>
		                    	</c:forEach>
		                    </div>
	                    </div>
	                  </div>
                  </c:if>
                </div>
              </div>
            </div>
			
			<!-- Comments Section -->
             <div id="tab-2" class="project_tabs_inn">
              <div class="project_tabs_inn_table clearfix">
                <div class="clearfix col-md-12">
                  <div class="clearfix mt">
                    <button type="button" id="addComment" class="btn btn-primary pull-right btn-sm mr">Add Comment</button>
                    <%-- <div class="add-ticketcomment" style="display: none;" id="add-comment">
                      <form role="form" id="ticketComment_form" name="sourceleadTicketComment_form" method="post">
	                        <textarea rows="3" class="form-control clearfix mb10 valid error" placeholder="Comments here..." id="ticketComment" name="comment" field-name="Comment" data-validation="required validate_length length1-5000" style="border-color: rgb(204, 204, 204);"></textarea>
	                        <span class="clearfix pull-right">
	                        <input type="button" id="submit_comment" value="Add" class="btn btn-sm btn-success" onclick="saveTicketComment('${ticketSummary.id}');" style="border-color: rgb(204, 204, 204);">
	                        <input type="button" value="Cancel" class="btn btn-sm btn-default" onclick="closeTicketComment()" style="border-color: rgb(204, 204, 204);">
	                        </span>
                      </form>
                    </div> --%>
					</div>
					<!-- Comments Section -->
					<!-- <div id="ticketCommentListDivId"></div> -->
					<div>
						<ul id="ticketCommentListDivId" class="list-unstyled goalscomments-list"></ul>
						<div id="emptyCommentLis"></div>
					</div>
					<!-- Comments Section Ends -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  
  <div class="popupWrap" id="ticketCommentPopup" role="dialog">
	 <form role="form" id="ticketComment_form" name="sourceleadTicketComment_form" method="post"> 
		<input type="hidden" id="ticketCommentId" name="id" value="0" data-validation="required validate_length length1-1000"/>
		<div class="ticketCommentCont">
			<div class="clearfix">
				<!-- <textarea rows="8" class="form-control clearfix mb10 valid error" placeholder="Comments here..." id="ticketComment" name="comment" field-name="Comment" data-validation="required validate_length length1-5000" style="border-color: rgb(204, 204, 204);"></textarea> -->
				<div contenteditable="true" id="ticketComment" style="height: 150px; border: 1px solid #CECECE;"></div>
			</div>
			<p id="ticketCommentvalidation" class="mt10 mb0 colorRed" style="display:none">Please Enter Comment</p>
		</div>
		<div class="popupFoot text-right">
			<button type="button" class="slBtn md colorBtn"  id="save_ticket_comment" onclick="saveTicketComment('${ticketSummary.id}');">Save</button>
			<button type="button" class="slBtn md popClose" onclick="closeTicketComment();">Cancel</button>
		</div>
	 </form> 
</div>
					
  <script> 
      $("#tab-1").show();
      $(".project-tabs-menu a").click(function(event) {
          event.preventDefault();
          $(this).parent().addClass("current");
          $(this).parent().siblings().removeClass("current");
          var tab = $(this).attr("href");
          $(".project_tabs_inn").not(tab).css("display", "none");
          $(tab).fadeIn();
      });
      var loggedInUserId = "${loggedInUserId}";
</script>
<script>
$(document).ready(function(){
	$(".add-ticketcomment").attr('style', "right:30px !important; top:45px");
});
$("#addComment").click(function(){
	$("#ticketCommentId").val(0);
	$("#ticketComment").html("");
	$("#ticketCommentPopup, .mask").show();
});
function closeTicketComment(){
	var ticketIdEdit = $("#ticketCommentId").val();
	$('#ticketComment_form').find('p.jquery_form_error_message').remove();
	if(ticketIdEdit ==null || ticketIdEdit =="" || ticketIdEdit =="undefined"){
	$("#ticketComment").val("");
	}
	$('.fr-element.fr-view').html("");
	$("#ticketCommentPopup, .mask").hide();
	$("#ticketCommentvalidation").hide();
}
</script>
   <jsp:include page="../masterFooter.jsp" />