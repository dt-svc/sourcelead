<%-- 
     @author : Venkat Kesav
     this script is for get candidate resumes list.
     1.usage     : onload
     2.parameter : candidateId.
--%>


<style>
#uploadFileGroup p.jquery_form_error_message{
	position: absolute;
	left:0;
	bottom:-16px;
	}
</style>

<script>
function candidateResume(candidateId){
	var result ="";
	result = result+'	    <div class="project_tabs_inn_head addUser-org">';
		result = result+'	    	<a href="javascript:void(0)" onClick="refreshResumeList('+candidateId+'); addResume('+candidateId+');" class="addUser-org" id="addCandidateResume"><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
		result = result  +'     <div id="popUpDivResume" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="resumesList" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	
    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-2").append(result); 
    
}
</script>


<%-- 
     @author : Venkat Kesav
     this script is for refresh Resume List
     1.usage     : onclick
--%>
<script>
function closeResumePopup(){
	 $("#popUpDivResume").removeClass("edit-details-active");
}
</script>


<%-- 
     @author : Venkat Kesav
     this script is for add resume popup
     1.usage     : onload
--%>
<script>
function refreshResumeList(candidateId){
	candidateResumesList(candidateId);
}
</script>



<%-- 
     @author : Venkat Kesav
     this script is for add resume popup
     1.usage     : onload
     2.parameter : candidateId.
--%>
<script>
function addResume(candidateId) {	
	var result = "";
	result = result+'	           <form id="candidateResume_form" name="candidateResume_form" action="upload" enctype="multipart/form-data" method="post">';
	result = result+'	           <i class="fa fa-close close-div" onClick="closeResumePopup();"></i>';
	result = result+'	                <div class="form-horizontal clearfix" id="newResumeDiv">';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <input type="text" class="form-control input-sm" id="title" name="title" data-validation="validate_Space validate_Description validate_length length2-100" data-validation-optional="true" field-name="Title" value="" placeholder="Title">';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Description" value="" id="description" name="description" data-validation="validate_Space validate_Description validate_length length2-250" data-validation-optional="true" field-name="Description" class="form-control"></textarea>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'							<div class="input-group positionRelative" id="uploadFileGroup">';
	result = result+'	                          <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'	                            <input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">';
	result = result+'	                            </span> </label>';
	result = result+'	                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="File not selected..." field-name="Resume" data-validation="required validate_Space validatedoc">';
	result = result+'	                        </div>';
	result = result+'	                    </div>  ';  
	result = result+'	                  </div>   ';                         
	result = result+'	                  <div class="clearfix mr mt">';
	result = result+'	                    <button type="button" id="add_resume" class="btn btn-sm btn-primary pull-right" onclick="storeCandidateResume('+candidateId+');">Add</button>';
	result = result+'	                  </div>';
	result = result+'	                </div>';
	result = result+'	                </form>';
	
	$("#popUpDivResume").empty();
	$("#popUpDivResume").append(result);
	
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
	 
	 	/* upload file script to show selected file name*/
	    document.getElementById("uploadBtn").onchange = function () {
	    document.getElementById("uploadFile").value = this.value;
	    };
	    
	    /* upload file script for size validation 2MB*/
	    var selectedFile = document.getElementById("uploadBtn");
	    selectedFile.addEventListener("change", function() {
	      var docSize = this.files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadFile").value = '';
	    	  $("#uploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
	      }
	    });
	}
</script>	 
  
<%-- 
     @author : Venkat Kesav
     this script is for saving resume
     1.usage     : onclick
     2.parameter : candidateId.
     3.ajax url implementation in CandidateResumeRestController.
--%>
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
				$("#candidateResume_form").attr("disabled", "disabled");
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
							 if( result.indexOf('Sign In') >= 0){
					    		location.replace(sourceleadUrl);
					    	 }
		            		 else if (result.indexOf('exception') >= 0) {
		            			 location.replace(sourceleadUrl+"/errorPage");
		            		 }
		            		 else{
						 		$("#popUpDivResume").removeClass("edit-details-active");
						 		candidateResumesList(candidateId);
		            		 }
						},
					});
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#candidateResume_form', function() {
		$("#candidateResume_form").showHelpOnFocus();
		$("#candidateResume_form").validateOnBlur(false, validationSettings);
	});
</script>

<%-- 
     @author : Venkat Kesav
     this script is for prepare list of resumes
     1.usage     : onload
     2.parameter : candidateId.
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script>
  function candidateResumesList(candidateId){ 	 
	  var sourceleadUrl ='${sourceleadUrl}';
	  var candidateId = candidateId;
	  var result = "";
	  var resumesCount = 0;
	  $.ajax({
			type : "POST",
			url : sourceleadUrl+"/candidateResumesListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,
			success : function(data) {
				var json = JSON.parse(data);
				if( data.indexOf('Sign In') >= 0){
		    		location.replace(sourceleadUrl);
		    	 }
        		 else if (data.indexOf('exception') >= 0) {
        			 location.replace(sourceleadUrl+"/errorPage");
        		 }
        		 else{
					if(json.length<=0 || (data == null && data == 'undefined' && data == "")){
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
							result = result+'	      <table>';
							result = result+'	        <tbody>';
							result = result+'	          <tr>';
							result = result+'	            <th>Title</th>';
							result = result+'	            <th>Attachment</th>';
							result = result+'	            <th>Description</th>';
							result = result+'	            <th>Created By</th>';
							result = result+'	            <th>Action</th>';
							result = result+'	          </tr>';
							
							
				             for (var i = 0; i < json.length; i++) {
				            	 	resumesCount = json.length;
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
					            	if(opt.createdBy != null && opt.createdBy != 'undefined' && opt.createdBy != ""){
					            		createdBy = opt.createdBy;
					            	}else{
					            		createdBy = 'NO DATA';
					            	}
					            	
					            	result = result+'	          <tr>';
					           		result = result+'	            <td><div><span>'+title+'</span></div></td>';
					           		result = result+'	            <td><div><span>'+opt.fileName+'</span></div></td>';
					           		result = result+'	            <td><div><span title="'+description+'">'+description+'</span></div></td>';
					           		result = result+'	            <td><div><span>'+createdBy+'</span></div></td>';
					           		result = result+'	            <td><div><a class="glyphicon glyphicon-eye-open" href=\"'+sourceleadUrl+'/downloadResume?resumeId='+opt.id+'\"></a><i class="glyphicon glyphicon-trash" onclick="deleteResume('+candidateId+','+opt.id+');"></i></div></td>';
					           		result = result+'	          </tr>';
				             }
				            result = result+'	          </tbody>';
				       		result = result+'	      </table>';
						}
					if(resumesCount>4){
						$("#addCandidateResume").hide();
						closeResumePopup();
					}
					$("#resumesList").empty();
					$("#resumesList").append(result);
        		}
				},
		});
  }
</script>


<%-- 
     @author : Venkat Kesav
     this script is for terminate resume (inactive status)
     1.usage     : onclick
     2.parameter : candidateId, resumeId
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script>
function deleteResume(candidateId, resumeId){
	var sourceleadUrl ='${sourceleadUrl}';
	var message = confirm("You are choosing to delete the resume");
	if (message == true) {
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateResume?${_csrf.parameterName}=${_csrf.token}&resumeId="+resumeId, 	   
   		    success: function(data) {
   		    	if( data.indexOf('Sign In') >= 0){
		    		location.replace(sourceleadUrl);
		    	 }
        		 else if (data.indexOf('exception') >= 0) {
        			 location.replace(sourceleadUrl+"/errorPage");
        		 }
        		 else{
        			 candidateResume(candidateId);
        		     candidateResumesList(candidateId);
        		 }
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
