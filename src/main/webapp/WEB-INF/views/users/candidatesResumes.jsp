<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%-- 
     @author : Venkat Kesav
     this script is for get candidate resumes list.
     1.usage     : onload
     2.parameter : candidateId.
--%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
var atsUrl ='${SourceLeadWeb_atsUrl}';
function candidateResume(candidateId){
	var candidateType = '${candidateType}';
	var result ="";
	
	result = result+'	    <div class="project_tabs_inn_head addUser-org noPad">';
	
	result  = result + '<div class="success_pop" id="selector2" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="resMsgDiv"></span>';
    result  = result + '</div>';
    
	result = result +' <div id="msgDiv3" ></div>';
	
	if(window.candidateStatusGlobal == '0'){
		if(candidateType == "Candidates"){
			'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\',\'ROLE_CROWDSOURCING_CANDIDATES\',\'ROLE_ADD_ASSOCIATE_RESUMES\')">'; 
			  result = result+'<a href="javascript:void(0)" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
			'</sec:authorize>';
		}else{
			'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_RESUMES\')">'; 
			  result = result+'<a href="javascript:void(0)" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
			'</sec:authorize>';
		}
		
	}else{
		if(candidateType == "Candidates"){
			'<sec:authorize access="hasAnyRole(\'ROLE_ADD_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			   result = result+'<a href="javascript:void(0)" onClick="refreshResumeList('+candidateId+'); addResume('+candidateId+');" class="addUser-org" id="addCandidateResume"><i class="glyphicon glyphicon-plus"></i>Add Resume</a>';
			'</sec:authorize>';
		}else{
			'<sec:authorize access="hasRole(\'ROLE_ADD_ASSOCIATE_RESUMES\')">';
			   /* result = result+'<a href="javascript:void(0)" onClick="refreshResumeList('+candidateId+'); addResume('+candidateId+');" class="addUser-org" id="addCandidateResume"><i class="glyphicon glyphicon-plus"></i>Add Resumesxcx</a>'; */
			   result = result+'<div class="reportHead clearfix">';
			   result = result+'<ul class="pull-right list-unstyled list-inline">';
			   result = result+'<li><a href="javascript:void(0)" onClick="refreshResumeList('+candidateId+'); addResume('+candidateId+');" class="newAdBtn" id="addCandidateResume">+ Resume</a></li>';
			   result = result+'</ul>';
			   result = result+'</div>	';		   
			'</sec:authorize>';
		}
		
	}

		result = result  +' <div id="popUpDivResume" class="edit-details col-md-4"> ';
		result = result  +' </div>  ';
		result  = result + '<div class="project_tabs_inn_table vendor_wrap_inn_table vendor_wrap_inn_table_last_child">';
		result  = result + ' <div id="resumesList" ></div>';
		result = result+'</div>';

	
    $("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty();  
    $("#tab-5").empty();  
    $("#tab-6").empty();  
    $("#tab-2").append(result); 
   
    
}
</script>

<!-- Show success message after form submission script -->
<script>
	function resMsgFadeinResumes() {
		$("#selector2").fadeIn(200);
        $('#selector2').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->


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

<div id="deleteResumePopUp"></div> 



<%-- 
     @author : Venkat Kesav
     this script is for add resume popup
     1.usage     : onload
     2.parameter : candidateId.
--%>
<script>
function addResume(candidateId) {	
	var result = "";
	result = result+'	           <form id="candidateResume_form" name="candidateResume_form" action="upload" method="post">';
	result = result+'	           <i class="fa fa-close close-div" onClick="closeResumePopup();"></i>';
	result = result+'	                <div class="form-horizontal clearfix" id="newResumeDiv">';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <input type="text" class="form-control input-sm" id="title" name="title" data-validation="validate_length length2-100" data-validation-optional="true" field-name="Title" value="" placeholder="Title">';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Description" value="" id="description" name="description" data-validation="validate_length length2-250" data-validation-optional="true" field-name="Description" class="form-control"></textarea>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	result = result+'	                  <div class="clearfix mb10" id="uploadResumeDiv">';
	result = result+'	                    <div class="col-md-12">';
	result = result+'							<div class="input-group clearUpload uploadFileGroup_error_message" id="uploadFileGroup" onClick="clearUpload()">';
	result = result+'	                          <label class="input-group-btn"> <span class="btn btn-primary" onchange="uploadFile();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'	                            <input type="file" id="uploadBtn" style="display: none;" class="upload" >';
	result = result+'	                            </span> </label>';
	result = result+'	                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Resume..." field-name="Resume" data-validation="required validate_Space validatedoc">';
	result = result+'	                        </div>';
	result = result+'	                    </div>  ';  
	result = result+'	                  </div>   ';  
	
	
	
	result = result+'	                  <div class="clearfix mb10" id="resumeNameDiv" style="display: none;">';
	result = result+'	                    <div class="col-md-12 mt">';
	result = result+'	                      <label id="resumeName" name="resumeName" class="text-primary tbldata-dotdot-vsm pull-left"></label>&nbsp;&nbsp;<span id="delete_resume" onclick="removeResumeNameDiv();"><i class="fa fa-trash removeIcon_styles"></i></span>';
	result = result+'	                    </div>';
	result = result+'	                  </div>';
	
	result = result+'	                  <div class="clearfix mr mt" id="resumeUpdateButton" style="display: none;">';
	result = result+'	                    <button type="button" id="update_resume" class="btn btn-sm btn-primary pull-right">Update</button>';
	result = result+'	                  </div>';
	
	
	
	
	
	
	
	result = result+'	                  <div class="clearfix mr mt" id="resumeAddButton">';
	result = result+'	                    <button type="button" id="add_resume" class="btn btn-sm btn-primary pull-right" onclick="storeCandidateResume('+candidateId+',null);">Add</button>';
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
	 
	 	/* upload file script to show selected file name*/
	    document.getElementById("uploadBtn").onchange = function () {
		   //$("#uploadFile").dir(this.files[0]);
	    	//document.getElementById("uploadFile").value = this.value;
	    	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	  
	   }; 
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
	function storeCandidateResume(candidateId,resumeId){
		var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#candidateResume_form').validate(false, validationSettings)){

				var formData =  getFormData('candidateResume_form'); 
				$("#candidateResume_form").attr("disabled", "disabled");
			    $("#add_resume").attr("disabled", "disabled");

				$.ajax({
					type : "POST",
	        	    data: formData,
					url : atsUrl+"/saveCandidateResume?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&resumeId="+resumeId,
					success : function(response) {
						if (response && response.response === "INVALID_DATA") {
						    showError(response.responseMessage);
						} else if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							var result = response.object;
							$('#perVal').attr('aria-valuenow', result).css('width',result+'%');
		          			 if(result<50){
			          			   $("#perVal").removeClass('progress-bar-success');
			          			   $("#perVal").addClass("progress-bar-danger");
			          			 }
			          			 if(result>=50){
			          				 $("#perVal").removeClass('progress-bar-danger');
			          				 $("#perVal").addClass("progress-bar-success");
			          			 }

	            			$("#disVal").empty();
	            			$("#disVal").append(result+'%');
	            			// for quick view bar update
	            			$('#quickPerVal').attr('aria-valuenow', result).css('width',result+'%');
		          			 if(result<50){
			          			   $("#quickPerVal").removeClass('progress-bar-success');
			          			   $("#quickPerVal").addClass("progress-bar-danger");
			          			 }
			          			 if(result>=50){
			          				 $("#quickPerVal").removeClass('progress-bar-danger');
			          				 $("#quickPerVal").addClass("progress-bar-success");
			          			 }

	            			$("#quickDisVal").empty();
	            			$("#quickDisVal").append(result+'%');
	            			
	            			
						 	$("#popUpDivResume").removeClass("edit-details-active");
	             			$("#selector2").removeAttr("style");
	             			$("#selector2").css({ display: "block" });
	             			if(resumeId != null){
	             				$("#resMsgDiv").html("Resume details updated");
	             			}else{
	             				$("#resMsgDiv").html("Success!! New Resume added");
	             			}		             			
					 		candidateResumesList(candidateId);
					 		resMsgFadeinResumes();
					 		setTimeout(function(){
					 			candidateList();					 			
					 		},100);
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
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
	  var candidateId = candidateId;
	  var candidateType = '${candidateType}';
	  var result = "";
	  var resumesCount = 0;
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  candResumeListDate = "MM-DD-YYYY";
	  if(locationList.match("yyyy-MM-dd")){
		  candResumeListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  candResumeListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  candResumeListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  candResumeListDate = 'YYYY-DD-MM'; 
	  }
	  $.ajax({
			type : "POST",
			url : atsUrl+"/candidateResumesListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,
			success : function(json) {
// 				if (json != null && json != 'undefined' && json != "" && json != '[]') {
// 					var json = JSON.parse(data);
				if(json.data.length > 0 ){
							result = result+'	      <table>';
							result = result+'	        <tbody>';
							result = result+'	          <tr>';
							result = result+'	            <th>Title</th>';
							result = result+'	            <th>Attachment</th>';
							result = result+'	            <th>Description</th>';
							result = result+'	            <th>Created By</th>';
							result = result+'	            <th>Updated Date<br>(<small class="custom-small">'+candResumeListDate+'</small>)</th>';
							
							result = result+'	            <th>Action</th>';
							result = result+'	          </tr>';
							
							
								 for (var i = 0; i < json.data.length; i++) {
					            	 	resumesCount = json.data.length;
						            	var opt = json.data[i];
						            	console.log(opt);
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
						            		description = '----';
						            	}
						            	if(opt.createdBy != null && opt.createdBy != 'undefined' && opt.createdBy != ""){
						            		createdBy = opt.createdBy;
						            	}else{
						            		createdBy = '----';
						            	}
						            	
						            	if(opt.candidateResumeUpdateDateToString != null && opt.candidateResumeUpdateDateToString != 'undefined' && opt.candidateResumeUpdateDateToString != ""){
						            		candidateResumeUpdateDateToString = opt.candidateResumeUpdateDateToString; 
						            	}else{
						            		candidateResumeUpdateDateToString = "---";
						            	} 
						            	
						            	result = result+'<tr>';
						           		result = result+'<td><div><span title = "'+title+'">'+title+'</span></div></td>';
						           		result = result+'<td><div><span title ="'+opt.fileName+'">'+opt.fileName+'</span></div></td>';
						           		result = result+'<td><div><span title="'+description+'">'+description+'</span></div></td>';
						           		result = result+'<td><div><span>'+createdBy+'</span></div></td>';

										result = result+'<td><div><span>'+candidateResumeUpdateDateToString+'</span></div></td>';
						           		result = result+'<td><div class="tblicons_4"> <a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+opt.fileURL+'\" download title="Download"></a>';
						           		result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+opt.fileURL+'\',\''+opt.fileName+'\',\''+opt.fileContentType+'\');"title="preview"></a>';		           						          
						           		if(window.candidateStatusGlobal == 0){
						           			result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
							           		result = result+'<i class="glyphicon glyphicon-trash" href="javascript:void(0)"></i>';
						           		}else{
						           			if(candidateType == "Candidates"){
						           				result = result+'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\')">  <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addResume('+candidateId+'); getResume('+candidateId+', '+opt.id+');"title="Edit"></a></sec:authorize>';
						           				result = result+'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_DELETE_CANDIDATE_RESUMES\',\'ROLE_CROWDSOURCING_CANDIDATES\')"><i class="glyphicon glyphicon-trash" onclick="removeresumePopUp('+candidateId+','+opt.id+');" title="Remove"></i></sec:authorize>';
						           			}else{
						           				result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_EDIT_ASSOCIATE_RESUMES\')">  <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addResume('+candidateId+'); getResume('+candidateId+', '+opt.id+');"title="Edit"></a></sec:authorize>';
						           				result = result+'<sec:authorize access="hasRole(\'ROLE_VIEW_DELETE_ASSOCIATE_RESUMES\')"><i class="glyphicon glyphicon-trash" onclick="removeresumePopUp('+candidateId+','+opt.id+');" title="Remove"></i></sec:authorize>';
						           			}
						           		}
						           		result = result+'</div></td>';
						           		result = result+'</tr>';
					             }
							result = result+'	          </tbody>';
				       		result = result+'	      </table>';
							}else{
								result = result + "   <div class='dataTables_empty'>";
				 				result = result + "  </div> ";
							}
				            
				            
					if(resumesCount>4){
						$("#addCandidateResume").hide();
						closeResumePopup();
					}else if(resumesCount<=4){
						$("#addCandidateResume").show();
					}
					$("#resumesList").empty();
					$("#resumesList").append(result);
// 				}else{
// 				result = result + "   <div class='row'>";
// 				result = result + "	<div class='col-lg-12'>";
// 				result = result + "		<div class='content-area clearfix'>";
// 				result = result + "			<div class='blank-pages'>";
// 				result = result + "			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
// 				result = result + "			        <p>No resumes Found</p>";
// 				result = result + "			 </div>";
// 				result = result + "		</div>";
// 				result = result + "	</div>";
// 				result = result + "  </div> ";
// 				$("#resumesList").empty();
// 				$("#resumesList").append(result);
// 			}
	
				},
		});
  }
</script>

<div class="ifraMask"></div>
<div class="framViePop"> <i class="fa fa-times fraClose" onclick="fraclose();"></i>
<iframe id="myFrame" width="100%" height="100%" frameborder="0" allowfullscreen></iframe>
</div>

<%-- 
     @author : Venkat Kesav
     this script is for terminate resume (inactive status)
     1.usage     : onclick
     2.parameter : candidateId, resumeId
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script type="text/javascript">
function getCandiResume(fileURL,title){
	if(fileURL != null && fileURL != ""){
		window.open(atsUrl+'/viewDocumentsForCan/'+title+'?docFile='+encodeURI(fileURL),title,"height=600,width=600");
	}  
}
function getIframeDocument(element)
{
	var doc = element.contentDocument;
        if (doc == undefined || doc == null)
        {
         doc = element.contentWindow.document;
        }

	return doc;
} 
function fraclose(){
	$(".ifraMask").fadeOut(200);
	$(".framViePop").hide();
	
/* var win = window.open();
win.document.write('<iframe width="560" height="315" src="//www.youtube.com/embed/mTWfqi3-3qU" frameborder="0" allowfullscreen></iframe>') */

}

function deleteResumePopup(){
	$("#remove_user_pop_resume, .project_inn_mask").fadeOut();
}
</script>

<div id="deleteEduPopUp"></div> 

<script type="text/javascript">
function removeresumePopUp(candidateId, resumeId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop_resume' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You are choosing to delete the Resume</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteResume("+candidateId+","+resumeId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteResumePopup();'></b>";
	result = result +"	</div>";
	$("#deleteEduPopUp").empty();
	$("#deleteEduPopUp").append(result);
	$("#remove_user_pop_resume").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>
<script>
function deleteResume(candidateId, resumeId){
	var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$.ajax({
		   	type: "POST",
		    url: atsUrl+"/terminateResume?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&resumeId="+resumeId, 	   
   		    success: function(response) {
   		    	if (response && response.response === "INVALID_DATA") {
   		    	    showError(response.responseMessage);
   		    	} else if (response.response === "AWKWARD") {
   		    	    location.href = "/errorPage";
   		    	} else if (response.response === "SUCCESS") {
   		    		var data = response.object;
   		    		$('#perVal').attr('aria-valuenow', data).css('width',data+'%');
	    			if(data<50){
	       			  	/* $("#perVal").removeClass("progress-bar-success"); */
	       				$("#perVal").removeClass('progress-bar-success');
	       				$("#perVal").addClass("progress-bar-danger");
	       			}
	       			if(data>=50){
	       				$("#perVal").removeClass('progress-bar-danger');
	       				$("#perVal").addClass("progress-bar-success");
	       			}
   			 	$("#disVal").empty();
   				$("#disVal").append(data+'%');
   				// for quick view bar update
       			$('#quickPerVal').attr('aria-valuenow', data).css('width',data+'%');
       			 	if(data<50){
         			   $("#quickPerVal").removeClass('progress-bar-success');
         			   $("#quickPerVal").addClass("progress-bar-danger");
         			 }
         			 if(data>=50){
         				 $("#quickPerVal").removeClass('progress-bar-danger');
         				 $("#quickPerVal").addClass("progress-bar-success");
         			 }
       			$("#quickDisVal").empty();
       			$("#quickDisVal").append(data+'%');
       			$("#selector2").removeAttr("style");
        		$("#selector2").css({ display: "block" });
        		$("#resMsgDiv").html("Resume deleted");
       			$("#popUpDivResume").removeClass("edit-details-active");
       		    candidateResumesList(candidateId);
       		    $("#remove_user_pop_resume, .project_inn_mask").fadeOut();
       		    resMsgFadeinResumes();
       		    setTimeout(function(){
					candidateList();					 			
				},100);
   		    	} else if (response.indexOf('Sign In') !== -1) {
   		    		location.replace(sourceleadUrl);
   		    	} else {
   		    	    showError("Operation failed, please try again");
   		    	}
   		    },
	     });

	
	
}
</script>



<%-- 
     @author : Venkat Kesav
     this script is for get resume details in edit mode
     1.usage     : onclick
     2.parameter : candidateId, resumeId
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script>
function getResume(candidateId, resumeId){
	
		$.ajax({
		   	type: "POST",
		    url: atsUrl+"/getResume?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&resumeId="+resumeId, 	   
   		    success: function(response) {
   		    	if (response.response === "AWKWARD") {
   		    	    location.href = "/errorPage";
   		    	} else if (response.response === "SUCCESS") {
   		    		 var json = response.object;
	       			 $("#uploadResumeDiv").hide();
	       			 $("#resumeAddButton").hide();
	       			 $("#uploadFile").removeAttr("data-validation");
	       			 $("#resumeNameDiv").show();
	       			 $("#resumeName").append(json.fileName);
	       			 $("#resumeUpdateButton").show();
	       			 
	       			 if(json.title != null && json.title != "undefined" && json.title != ""){
	       				 document.getElementById("title").value = json.title;
	       			 }
	       			 if(json.description != null && json.description != "undefined" && json.description != ""){
	       				 $("textarea#description").val(json.description);
	       			 }
	       			 $("#update_resume").attr("onclick", "storeCandidateResume("+json.candidateId.id+","+json.id+");");
   		    	} else if (response.indexOf('Sign In') !== -1) {
   		    		location.replace(sourceleadUrl);
   		    	} else {
   		    	    showError("Operation failed, please try again");
   		    	}
   		    }
	     });
}


<%-- 
@author : Venkat Kesav
this script is for remove Resume in edit mode to upload new resume
1.usage     : onclick
--%>
function removeResumeNameDiv(){
	var resTitle = $("#title").val();
	var resDocName =  $("#resumeName").text();
	$("#resumeNameDiv").hide();
	$("#uploadResumeDiv").show();
	$("#uploadFile").attr("data-validation", "required validate_Space validatedoc");
	if(resTitle == resDocName){
		$("#title").val("");
	}
}


</script>
<script>
function uploadFile(){
	fileUpload("uploadBtn", "resumes", "candidateResume_form");
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

<script>
	function clearUpload(){
		$("#uploadFile").val("");
	}
</script>