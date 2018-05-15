<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>${sourceleadTicket.ticketTitle}</h2>
      </div>
         <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='/app/sourceleadTicketList'/>"> Ticket List</a> &raquo; View Ticket</div>
    </section>
   <div class="clearfix">
		<div class="clearfix form-horizontal">

			<div class="project_side_wrap_inn_tabs">
				<ul class="project-tabs-menu">
					<li class="current" id="detailsTab"><a href="#tab-1"
						onclick="">Ticket Details</a></li>
					<li class="" id=""><a href="#tab-2" onclick="getSourceleadTicketCommets('${sourceleadTicket.id}');">Comments</a></li>
				</ul>
				<div class="clearfix"></div>
				<div class="project_tabs modal-body">
			<!--  Tab1 -->
			 <div class="project_tabs_inn" id="tab-1">
			    <div class="require_one">
			    <c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_ADMIN' || SourceLeadWeb_user.id == sourceleadTicket.userId.id || SourceLeadWeb_user.id == sourceleadTicket.createdBy.id}">
	               <div class="clearfix">
	               	<a href="${appPath}/addSourceleadTicket?ticketUUID=${sourceleadTicket.ticketUUID}" class="pull-right anchor-black"><i class="fa fa-pencil paddingRight" title="Edit"></i>Edit</a>
	               </div>
	              </c:if> 
                <div class="clearfix"></div>
                <div class="require_wrap_details_cta width100percnt">
                 <label><strong>Title :</strong></label> ${sourceleadTicket.ticketTitle}
                </div>
                
                <div class="require_wrap_details_cta width100percnt">
                 <label><strong>Type :</strong></label> ${sourceleadTicket.ticketType}
                </div>
                
                <div class="require_wrap_details_cta width100percnt">
                 <label><strong>Module :</strong></label> ${sourceleadTicket.tabId.tabName}
                </div>
                
                <div class="require_wrap_details_cta width100percnt">
                 <label><strong>Requested on :</strong></label> ${createddateStr}
                </div>
                
                <div class="require_wrap_details_cta width100percnt">
                 <label><strong>Ticket Status :</strong></label>  ${sourceleadTicket.ticketStatus}
                </div>
                
                <div class="require_wrap_details_cta width100percnt">
                 <label><strong>URL :</strong></label>
                  ${sourceleadTicket.ticketURL}
                </div>
                
                 <div class="width100percnt clearfix">
				  <label><strong>Descripton:</strong></label>
                   <div class="clearfix">
                   <div class="lineHeight small">
							${sourceleadTicket.description}
                   </div>
                   </div>
                </div>
                
                <div class="clearfix"></div>
                <div class="require_wrap_details_cta width100percnt">
                 <label><strong>Attachment:</strong></label>
                 <div class="clearfix">
                  <span>
                   <c:if test="${!empty sourceleadTicketsAttachmentsList}">
			          <div id="timesheetDocumentsId">
				         <div class="timesheet_week_comm invoiceDocs invoiceDocsInput">
					         <span class="pull-left">
						         <c:forEach var="attachmentList" items="${sourceleadTicketsAttachmentsList}">
							         <strong id='ticketDocument${attachmentList.id}'>
					    		     <i class=''><a   class='glyphicon glyphicon-save' href="${attachmentList.fileURL}" title='download' download></a></i>
									 <input type='button' title="${attachmentList.fileName}"   value='${attachmentList.fileName}' style="cursor:initial !important">
									  <a href="javascript:void(0)" class="fa fa-eye pull-left paddingLeft ml5 mt anchorBlack" title="view attachment" onclick="viewFile('${attachmentList.fileURL}','${attachmentList.fileName}','${attachmentList.fileType}')"></a>
									  </strong>
						         </c:forEach>
					         </span>
				         </div>
			         </div>
		         </c:if>
                  
                  </span>
				  </div>
                </div>
                <div class="clearfix"></div>
              </div>
					
					</div>
					<!--  Tab1 Ends -->


					<!--  Tab2 starts -->
					<div class="project_tabs_inn" id="tab-2">
					<div class="clearfix minHeight40">
					<button type="button" id="addComment" class="btn btn-primary pull-right btn-sm">Add Comment</button>
					<div class="add-ticketcomment" style="display: none;" id="add-comment">	   
					<form role="form" id="sourceleadTicketComment_form" name="sourceleadTicketComment_form" method="post">    
					<textarea rows="3" class="form-control clearfix mb10" placeholder="Comments here..." id="ticketComment" name="ticketComment" field-name="Comment" data-validation="required validate_length length1-5000"></textarea>  
					  <span class="clearfix pull-right">     
					   <input type="button" id="submit_comment" value="Add" class="btn btn-sm btn-success" onclick="addTicketComments('${sourceleadTicket.id}','${sourceleadTicket.ticketUUID}')">  
					<input type="button" value="Cancel" class="btn btn-sm btn-default" onclick="closeTicketComment()">       
					</span>	       
					</form>
					</div>
						
					</div>
						 <div id="ticketCommentListDivId"></div>
					</div>
					<!--  Tab2 ends -->

				</div>
			</div>
		</div>
	</div>
	
	
	
 
  </section>
<div class="backtotop" style="display: block;"><img src="/resources/images/backtotop.png"></div>
<script>
$(function(){
$("#addComment").click(function() {
	$("#add-comment").toggle();
})
});
function closeTicketComment() {
	$("#add-comment").fadeOut();
	 document.getElementById("sourceleadTicketComment_form").reset();
	 $("sourceleadTicketComment_form").validate().resetForm();
}
</script>
<script>

// Back To Top 
	    $(window).scroll(function() {
	        if ($(this).scrollTop() > 200) {
	            $('.backtotop').fadeIn('slow');
	        } else {
	            $('.backtotop').fadeOut('slow');
	        }
	    });
	    $('.backtotop').click(function() {
	        $("html, body").animate({scrollTop: 0}, 500);
	        return false;
	    });


</script>

<script>
//project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
	
});
</script>

<script>
function getSourceleadTicketCommets(ticketId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		 type : "POST",
		 url : sourceleadUrl+ "/getSourceleadTicketComments?${_csrf.parameterName}=${_csrf.token}&id="+ticketId,
		 success : function(json) {
	        	var result = "";
	        	if(json.length > 0 ){
	        		 for (var i=0; i<json.length; i++)
	    				{
	 	        		 var report = json[i];
	 			        result = result +'	 <div class="comments-div comments-inner clearfix">';
	 					result = result +'		<div class="commentsimg">';
	 					result = result +'			<i class="fa fa-comments"></i>';
	 					if(report.userImage != null && report.userImage != "" && report.userImage != "undefined"){
	 						result = result +'			<div class="commentsimg-inner"> <img onerror="predefineProfileImage(this);" src="'+report.userImage+'"></div>';
	 					}else{
	 						result = result +'			<div class="commentsimg-inner"> <img onerror="predefineProfileImage(this);" src="/resources/images/userprofile-img.png"></div>';

	 					}
	 					result = result +'		</div>';
	 					result = result +'		<div class="customCommentarea">';
	 					result = result +'		<div class="row">';
	 					result = result +'			<div class="col-md-12">';
	 					result = result +'				<p class="comment-by">';
	 					result = result +'					By <span class="text-danger">'+report.userName+'</span>';
	 					result = result +'					 <span class="small mr">'+report.createdDateTime+'</span>';
	 					result = result +'					 <span class="small">('+report.createdDateStr+')</span>';
	 					result = result +'				</p>';
	 					result = result +'				<div class="commentText commentText-styles">'+report.description+'</div>';
	 					result = result +'			</div>';
	 					result = result +'			</div>';
	 					result = result +'		</div>';
	 					result = result +'	</div>';
	    				} 
	        	}else{
	        		result = result +'<div class="dataTables_empty">  </div>';
	        	}
	        	$("#ticketCommentListDivId").empty();
	        	$("#ticketCommentListDivId").append(result);
		},
	});
}

</script>


<script>
function addTicketComments(id,ticketUUID){
	if ($('#sourceleadTicketComment_form').validate(false, validationSettings)) {
		var sourceleadUrl = '${sourceleadUrl}';
		var ticketComment = $("#ticketComment").val();
		var formData = getFormData("sourceleadTicketComment_form")[0];
		$.ajax({
			 type : "POST",
			 data: formData,
			 url : sourceleadUrl+ "/saveSourceleadTicketComment?${_csrf.parameterName}=${_csrf.token}&ticketUUID="+ticketUUID+"&ticketComment="+ticketComment,
			 success : function(result) {
				 getSourceleadTicketCommets(id);
				 closeTicketComment(); 
			},
		});
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#sourceleadTicketComment_form', function() {
	$("#sourceleadTicketComment_form").showHelpOnFocus();
	$("#sourceleadTicketComment_form").validateOnBlur(false, validationSettings);
});
</script>


 <jsp:include page="masterFooter.jsp" />	