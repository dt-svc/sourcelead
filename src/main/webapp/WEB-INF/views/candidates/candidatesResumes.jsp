
<script>
function candidateResume(){
	var result ="";
	result = result+'	    <div class="project_tabs_inn_head">';
	result = result+'	    	<a href="#" onClick="addResume();" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
	result = result+'	          <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>';
	result = result+'	                <div class="form-horizontal clearfix">';
	result = result+'	                <form id="candidateResume_form" name="candidateResume_form" method="post">';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <input type="text" class="form-control input-sm"   name="title"  value="" placeholder="Title">';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Description"  value="" name="description" class="form-control"></textarea>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'							<div class="input-group">';
	result = result+'	                          <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'	                            <input type="file" style="display: none;"  multiple="" id="uploadBtn" class="upload">';
	result = result+'	                            </span> </label>';
	result = result+'	                          <input type="text" class="form-control" readonly=""  name="uploadFile" id="uploadFile" placeholder="Files are not selected...">';
	result = result+'	                        </div>';
	result = result+'	                    </div>  ';  
	result = result+'	                  </div>   ';                         
	result = result+'	                  <div class="clearfix mr">';
	result = result+'	                    <button type="button" id="add_resume" class="btn btn-sm btn-primary pull-right" onclick="storeCandidateResume();">Add</button>';
	result = result+'	                  </div>';
	result = result+'	                </form>';
	result = result+'	                </div>';
	result = result+'	              </div>';
	result = result+'	    </div>';
	result = result+'	    <div class="project_tabs_inn_table vendor_wrap_inn_table">';
	result = result+'	      <table>';
	result = result+'	        <tbody>';
	result = result+'	          <tr>';
	result = result+'	            <th>Title</th>';
	result = result+'	            <th>Attachment</th>';
	result = result+'	            <th>Description</th>';
	result = result+'	            <th>Created By</th>';
	result = result+'	            <th>Action</th>';
	result = result+'	          </tr>';
	result = result+'	          <tr>';
	result = result+'	            <td><div><span>javaresume</span></div></td>';
	result = result+'	            <td><div><span>javaresume.doc</span></div></td>';
	result = result+'	            <td><div><span>javaresume description here</span></div></td>';
	result = result+'	            <td><div><span>Sujith S</span></div></td>';
	result = result+'	            <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>';
	result = result+'	          </tr>';
	result = result+'	          <tr>';
	result = result+'	            <td><div><span>javaresume</span></div></td>';
	result = result+'	            <td><div><span>javaresume.doc</span></div></td>';
	result = result+'	            <td><div><span>javaresume description here</span></div></td>';
	result = result+'	            <td><div><span>Sujith S</span></div></td>';
	result = result+'	            <td><div><i class="glyphicon glyphicon-eye-open"></i><i class="glyphicon glyphicon-trash"></i></div></td>';
	result = result+'	          </tr>';
	result = result+'	        </tbody>';
	result = result+'	      </table>';
	result = result+'	    </div>';
	result = result+'	    <nav>';
	result = result+'	      <ul id="pagingg" class="pagination">';
	result = result+'	        <li><a href="#">Previous</a></li>';
	result = result+'	        <li><a href="#">2</a></li>';
	result = result+'	        <li><a href="#">3</a></li>';
	result = result+'	        <li class="disabled"><a href="#">6</a></li>';
	result = result+'	        <li data-page="last"><a href="#">Next</a></li>';
	result = result+'	      </ul>';
	result = result+'	      <div class="clearfix"></div>';
	result = result+'	    </nav>';
	
    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-2").append(result);  
}
</script>

<script>
/* function addResume(event){
		 $(".collapse-div-adduser").toggleClass("show-div");
		 $(".collapse-div").removeClass("show-div");
		 event.preventDefault();
} */
//script for scroll Popups:: Add or Edit details

function addResume() {	
	/* $(".addUser-org").click(function(){
		 $(".edit-details").toggleClass("edit-details-active");
		 $(".collapse-div").removeClass("edit-details-active");
	 }); */
	$(".edit-details").toggleClass("edit-details-active");
	$(".close-div").click(function(){
		$(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 $('html').click(function(){
		$(".edit-details").removeClass("edit-details-active");
	   });	
	   
	}
</script>	 
        
<!-- 
<script type="text/javascript">
	function storeCandidateResume(){
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		alert("ki");
		if ($('#candidateResume_form').validate(false, validationSettings)) {

			 alert("t");
		}
	}
	
</script> -->

