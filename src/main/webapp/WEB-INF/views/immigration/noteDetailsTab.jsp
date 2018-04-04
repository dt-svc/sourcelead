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
			<div class="col-sm-12 noPad mt clearfix"><textarea class="form-control" rows="3" id= "noteDescription" name = "noteDescription"></textarea></div>						
		</div>
		
		 <div>
				<input type="checkbox" class="pull-left" value= "Y" id="allowAll" name = "allowAll" checked="checked">
		        <p class="pull-left mt5">Allow all users</p>
		 </div>
		 
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="save_Button" onclick="saveNotesDatails('save');">
            <input value="Cancel" class="popCan" type="button" onclick="refreshNotes();">
          </div>		
	</div>	<!-- popcode end -->
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
			 }
		</script>