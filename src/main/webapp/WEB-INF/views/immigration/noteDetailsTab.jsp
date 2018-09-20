<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addNote"><i class="glyphicon glyphicon-plus"></i> Add Note</a> 
		  	<!-- <span><input class="immigraTabSerach" type="text"></span> -->
		</div>
		
	  	 <div class="immigraTable">
			<c:forEach var="notesitems" items="${candidatesNotesList}" varStatus="rows"> 
			<c:if test="${notesitems.checkBox =='Y' || (notesitems.checkBox =='N' && SourceLeadWeb_user.id == notesitems.userIdStr)}">
		   <div class="immiNote clearfix">
				<img src="images/23.jpg" alt=""/>
				<c:if test="${!empty notesitems.commentAuthorImage}">
				<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="${notesitems.commentAuthorImage}"/>
				</c:if>
				<c:if test="${empty notesitems.commentAuthorImage}">
				<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="/resources/images/userprofileImg.jpg"/>
				</c:if>
				<p>
					<b>By <span>${notesitems.userName}</span><u>${notesitems.createdDateString}</u></b>
					${notesitems.comment}
				</p>
 			</div> 
 			 </c:if>
			</c:forEach>
			
		</div> 
		    
		<!--popcode start  -->
		<form id ="notesDetails_form" name = "notesDetails_form" method = "Post">
		<div class="popUpOut" id="popNoteView">
		<input type="hidden" id= "candidateId"  value = "${candidateId}">	
		<input type="hidden" id= "immigrationId" value = "${immigrationId}">	
		<div class="popupHead">
			<h2>Add Note</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="refreshNotes();"></a>
		</div>
		
		
		<div class="popupMid clearfix">
			<div class="col-sm-12 noPad mt clearfix"><textarea class="form-control" rows="3" id= "noteDescription" placeholder="Note *" name = "noteDescription" field-name="Note" data-validation="required validate_Space validate_length length2-250"></textarea></div>						
		</div>
		
		 <div>
				<input type="checkbox" class="pull-left" value= "Y" id="allowAll" name = "allowAll" checked="checked">
		        <p class="pull-left mt5">Allow all users</p>
		 </div>
		 
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="notesSave_Button" onclick="immigration.saveNotesDatails('save');">
            <input value="Cancel" class="popCan" type="button" onclick="refreshNotes();">
          </div>		
	</div>	<!-- popcode end -->
	<!-- <div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#docsTab');" class="btn btn-default btn-sm">
              	<input type="button" value="Continue" onclick="goToSummaryTab('#activitiesTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div> -->
	</form>
		<script>
			 // Add addNote Div   
			$("#addNote").click(function(){
				$(".popMask").fadeIn(200);
				$("#popNoteView").fadeIn(100);
			});	
			function refreshNotes(){
				 $(".popMask").fadeOut(200);
				$("#popNoteView").fadeOut(100);
				$("#notesDetails_form")[0].reset();
				$("#popNoteView").find('p.jquery_form_error_message').remove();
				$("#notesSave_Button").removeAttr("disabled")
			 }
		</script>