
<script>
function candidateResume(candidateId){
	var result ="";
	result = result+'	    <div class="project_tabs_inn_head">';
	result = result+'	    	<a href="#" onClick="flushResume(); addResume();" class="addUser-org"><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
	result = result+'	          <div class="edit-details col-md-4"> <i class="fa fa-close close-div"></i>';
	result = result+'	                <div class="form-horizontal clearfix" id="newResumeDiv">';
	result = result+'	                <form id="candidateResume_form" name="candidateResume_form" method="post">';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <input type="text" class="form-control input-sm" id="title"  name="title"  value="" placeholder="Title">';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Description" value="" id="description" name="description" class="form-control"></textarea>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'							<div class="input-group">';
	result = result+'	                          <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'	                            <input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">';
	result = result+'	                            </span> </label>';
	result = result+'	                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Files are not selected..." field-name="Resume" data-validation="required validate_Space">';
	result = result+'	                        </div>';
	result = result+'	                    </div>  ';  
	result = result+'	                  </div>   ';                         
	result = result+'	                  <div class="clearfix mr">';
	result = result+'	                    <button type="button" id="add_resume" class="btn btn-sm btn-primary pull-right" onclick="storeCandidateResume('+candidateId+');">Add</button>';
	result = result+'	                  </div>';
	result = result+'	                </form>';
	result = result+'	                </div>';
	result = result+'	              </div>';
	result = result+'	    </div>';
	
	result = result+'   <div id="resumesList"></div>'; 
	
    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-2").append(result); 
    
    /* upload file script */
    document.getElementById("uploadBtn").onchange = function () {
    document.getElementById("uploadFile").value = this.value;
    };
}
</script>

<script>
function addResume() {	
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
        
<script type="text/javascript">
	function storeCandidateResume(candidateId){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#candidateResume_form').validate(false, validationSettings)){
				var frm = $('#candidateResume_form').serializeFormJSON();
				var con = JSON.stringify(frm);
				con = con.replace(/[{}]/g, "");
				var form = $('#candidateResume_form')[0];
	      		var formData = new FormData(form); 
				var sourceleadUrl = '${sourceleadUrl}';
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/saveCandidateResume?${_csrf.parameterName}=${_csrf.token}&json="+con+"&candidateId="+candidateId,
						success : function(result) {
							candidateResumesList(candidateId);
						},
					});
					return true;
			 }else{ 
					return false;
			 } 
		}
</script>

<script>
  function candidateResumesList(candidateId){ 	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  var candidateId = candidateId;
	  var result = "";
	  $.ajax({
			type : "POST",
			url : sourceleadUrl+"/candidateResumesListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,
			success : function(data) {
				if(data == null && data == 'undefined' && data == ""){
						result = result+"   <div class='row'>"; 
					 	result = result+"	<div class='col-lg-12'>"; 
					 	result = result+"		<div class='content-area clearfix'>"; 
					 	result = result+"			<div class='blank-pages'>";
					 	result = result+"			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 	result = result+"			        <p>No Resumes Found</p>";
					 	result = result+"			 </div>";
					 	result = result+"		</div>"; 
					 	result = result+"	</div>"; 
					 	result = result+"  </div> "; 
					}else{
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
						
						 var json = JSON.parse(data);
			             for (var i = 0; i < json.length; i++) {
				            	var opt = json[i];
				            	var title = "";
				            	var description = "";
				            	if(opt.title != null && opt.title != 'undefined' && opt.title != ""){
				            		title = opt.title;
				            	}else{
				            		title = opt.fileName;
				            	}
				            	if(opt.description != null && opt.description != 'undefined' && opt.description != ""){
				            		description = opt.description;
				            	}else{
				            		description = 'NO DATA';
				            	}
				            	
				            	result = result+'	          <tr>';
				           		result = result+'	            <td><div><span>'+title+'</span></div></td>';
				           		result = result+'	            <td><div><span>'+opt.fileName+'</span></div></td>';
				           		result = result+'	            <td><div><span>'+description+'</span></div></td>';
				           		result = result+'	            <td><div><span>'+opt.createdBy+'</span></div></td>';
				           		result = result+'	            <td><div><a class="glyphicon glyphicon-eye-open" href=\"'+sourceleadUrl+'/downloadResume?resumeId='+opt.id+'\"></a><i class="glyphicon glyphicon-trash" onclick="deleteResume('+candidateId+','+opt.id+');"></i></div></td>';
				           		result = result+'	          </tr>';
			             }
			            result = result+'	          </tbody>';
			       		result = result+'	      </table>';
			       		result = result+'	    </div>';
					}
				$("#resumesList").append(result); 
				},
		});
  }
</script>

<script type="text/javascript">
function flushResume(){
     $('#title').val('');
     $('#description').val('');
	 $('#uploadBtn').val('');
	 $('#uploadFile').val('');
	 $(".error").removeClass("error");
	 $('#newResumeDiv').find('p.jquery_form_error_message').remove();
}
</script>

<script>
function deleteResume(candidateId, resumeId){
	var sourceleadUrl ='${sourceleadUrl}';
	var r = confirm("You are choosing to delete the resume");
	if (r == true) {
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateResume?${_csrf.parameterName}=${_csrf.token}&resumeId="+resumeId, 	   
   		    success: function(data) {
   		    	candidateResumesList(candidateId);
   		    }
	     });
	}
}
</script>

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

