<div class="immigraTabsHead">
		  	<a href="javascript:void(0)" id="addNote"><i class="glyphicon glyphicon-plus"></i> Add Note</a> 
		  	<span><input class="immigraTabSerach" type="text"></span>
		</div>
		
	  	<div class="immigraTable">
			
		  <div class="immiNote clearfix">
				<img src="images/23.jpg" alt=""/>
				<p>
					<b>By <span>Vannam Mahesh</span><u>(21 min ago)</u></b>
					Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever
				</p>
				<div class="removeNote"><i class="fa fa-trash"></i></div>
			</div>
			<div class="noRecordFound">
		    	<img src="images/noReco.png" alt=""/><p>No. Records found...</p>
		    </div>
		</div>
		<!--popcode start  -->
		<form id ="notesDetails_form" name = "notesDetails_form" method = "Post">
		<div class="popUpOut" id="popNoteView">
		<input type="hidden" id= "candidateId"  value = "${candidateId}">	
		<input type="hidden" id= "immigrationId" value = "${immigrationId}">	
		<div class="popupHead">
			<h2>Add Note</h2><a href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		
		<div class="popupMid clearfix">
			<div class="col-sm-12 noPad mt clearfix"><textarea class="form-control" rows="3" id= "noteDescription" name = "noteDescription"></textarea></div>						
		</div>
		
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="save_Button" onclick="saveNotesDatails('save');">
            <input value="Cancel" class="popCan" type="button">
          </div>		
	</div>	<!-- popcode end -->
	</form>
		<script>
			 // Add addNote Div   
			$("#addNote").click(function(){
				$(".popMask").fadeIn(200);
				$("#popNoteView").fadeIn(100);
			});	
		</script>