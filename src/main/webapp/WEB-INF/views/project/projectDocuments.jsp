<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div>  
<!-- this script is for Tasks list functionality at Projects screen -->
<script>
	function projectDocuments(projectId, status) {
		var userId = '${SourceLeadWeb_userId}';
		var result = "";
		$("#tasksLoadingImg").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var status = status;
		$.ajax({
					type : "POST",
					url : pmsUrl	+ "/getProjectDocumentsFolderList?${_csrf.parameterName}=${_csrf.token}&projectId="+ projectId+"&userId="+userId,
					success : function(json) {
						var result = "";
						result = result + '<input type="hidden" id="projectId" name="projectId" value="'+projectId+'">';
						result = result + '                                                                                        ';
						result = result + '	 	<div class="fileManager_details">                                                  ';
						
						result = result + '    	<div class="clearfix mb">                                                          ';
						result = result + '    		<div class="pull-right">                                                       ';
						result = result + '    			<button type="button" class="btn btn-primary btn-sm mr cancel" data-toggle="modal" data-target="#uploadFile_fm">UPLOAD</button>    ';
						result = result + '    			<button type="button" class="btn btn-primary btn-sm cancel" onclick="createFolder();" >CREATE FOLDER</button> ';
						result = result + '    		</div>                                                                                    ';
						result = result + '    	</div>                                                                                        ';
						
						result = result + '    	<div  class="row">                                                                            ';
						result = result + '	 		<div class="col-md-4">                                                                    ';
						result = result + '	 		  <ul class="nav nav-tabs fmtabs-left">                                                   ';
						result = result + '	 		  <li class="active">                                                                     ';
						result = result + '	 			<a href="#id-1" data-toggle="tab" onclick="getdocumentListById();">                                                    ';
						result = result + '	 				<i class="fa fa-folder-o mr"></i>All Folders                                      ';
						result = result + '	 		    </a>                                                                                  ';
						result = result + '	 			</li>                                                                                 ';
						for (var i=0; i<json.length; i++)
		   				{
							var report = json[i];
							result = result + '	 			<li> ';     
							result = result + '	 			<a href="#id-1" data-toggle="tab" onclick="getdocumentListById('+report.id+',\''+report.foderName+'\');">                                    ';
							result = result + '	 				<i class="fa fa-folder-o mr"></i>'+report.foderName+'  (<span>'+report.count+'</span>)';
							result = result + '	 		    </a>                                                                  ';
							if(report.permissionAccess == 0){
							}else{
								result = result + '	 				<span class="fmsettings-dropdown dropdown">                       ';
								result = result + '	 					<span class="dropdown-toggle" data-toggle="dropdown">         ';
								result = result + '	 					<i class="fa fa-gear text-muted cursorPointer"></i>           ';
								result = result + '	 					</span>                                                       ';
								result = result + '	 					<ul class="dropdown-menu dropdown-menu-right fmdropdown-menu">         ';
								result = result + '	 						<li><a href="#" data-toggle="modal" onclick="renameFolder();folederReName('+report.id+',\''+report.foderName+'\');"><i class="fa fa-pencil mr10"></i>Rename</a></li>   ';
								result = result + '	 						<li><a href="#"  onclick="deleteFolderPopUp('+report.id+');"><i class="fa fa-trash-o mr10" ></i>Delete</a></li>  ';
								result = result + '	 						<li><a href="#" onclick="permissionPopUp('+report.id+');"><i class="fa fa-key mr10"></i>Permission</a></li>  ';
								result = result + '	 					</ul>                                                                  ';
								result = result + '	 				</span>   ';
							}
							                                                                
							result = result + '	 			</li>     ';
							                                                                                                               
		   				}
						
						//append floder name in uploadFile
						output = "";
						output = output + '<option value="">-- Select Folder --</option>';
						for (var i=0; i<json.length; i++)
		   				{
							var report = json[i];
							output = output + '<option value="'+report.id+'">'+report.foderName+'</option>';
							                                                                                                               
		   				}
						$("#projectFolderId").empty();
						$("#projectFolderId").append(output);
						
						
						result = result + '	 		  </ul>    ';
						result = result + '	 		</div>     ';
						result = result + '	 		<div id="documentListDivId"/>    ';
						
						result = result + '<div id="deleteFolderPopUpMsgDiv"></div>';
						result = result + '<div id="deleteDocumentPopUpMsgDiv"></div>';
						result = result + '  <div class="clearfix"></div>                    ';
						result = result + '      </div>                                      ';
						result = result + '   </div>';
								          	
						$("#tab-1").empty();
						$("#tab-3").empty();
						$("#tab-2").empty();
						$("#tab-4").empty();
						$("#tasksLoadingImg").empty();
						$("#tab-3").append(result);
						$("#tab-3").show();
						 getdocumentListById();
						$(".project-tabs-menu a").click(
								function(event) {
									event.preventDefault();
									$(this).parent().addClass("current");
									$(this).parent().siblings().removeClass(
											"current");
									var tab = $(this).attr("href");
									$(".project_tabs_inn").not(tab).css(
											"display", "none");
									$(tab).fadeIn();
								});
					}
				});

	}
	

	//get the document list
	function getdocumentListById(folderId,folderName){
		if(folderName != null && folderName != "" && folderName != 'undefined'){
			folderName 
		}else{
			folderName = "All Directory";
		}
		var projectId = $("#projectId").val();
		if(folderId != null && folderId != '' && folderId != 'undefined'){
			folderId 
		}else{
			folderId = "";
		}
		var userId = '${SourceLeadWeb_userId}';
		$.ajax({
			type : "POST",
			url : pmsUrl	+ "/getEachProjectDocumentsFolderList?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&folderId="+folderId+"&userId="+userId,
			success : function(json) {
				var result = "";
				result = result + '	 		<div class="col-md-8">                                                                    ';
				result = result + '	 		  <div class="tab-content fm-tabcontent">                                                 ';
				result = result + '	 			<div class="tab-pane active" id="id-1">                                               ';
				result = result + '	 				<table class="fmTable" width="100%;">                                             ';
				result = result + '	 					<tbody>                                                                       ';
				result = result + '	 						<tr>                                                                      ';
				result = result + '	 							<td>                                                                  ';
				result = result + '	 							<div class="clearfix">                                                ';
				result = result + '	 								<div class="pull-left mr10">                                      ';
				result = result + '	 									<input id="documentCheckbox" class="checkbox-custom fmcheckbox" name="documentCheckbox" type="checkbox" onclick="toggleDocuments(this);getAllCheckBoxes();">  ';
				result = result + '	 									<label for="documentCheckbox" class="checkbox-custom-label fmcheckbox-lable"></label>               ';
				result = result + '	 								</div>                                                                                            ';
				result = result + '	 								<div class="col-md-95">                                                                           ';
				result = result + '	 								<span class="pull-left"><i class="fa fa-folder-o tblminwidth-30 mr10"></i>'+folderName+' (<span id="allFolderCount"></span>)</span>                 ';
				result = result + '	 								<ul class="pull-right list-unstyled list-inline extrafields" style="display:none" id="checkBoxCheckedId">                                     ';
				result = result + '	 										<li><a href="javascript:void(0)" data-toggle="modal" data-target="#fmEmail" onclick="mailAllDocuments();"><i class="fa fa-envelope"></i></a></li>                  ';
				result = result + '	 										<li><a href="javascript:void(0)" onclick="getAllDocumentDownload();"><i class="fa fa-download"></i></a></li>                  ';
				result = result + '	 										<li><a href="javascript:void(0)" onclick="deleteAllDcouments();"><i class="fa fa-trash"></i></a></li>                     ';
				result = result + '	 									</ul>                                                                                         ';
				result = result + '	 								</div>                                                                                            ';
				result = result + '	 								</div>                                                                                            ';
				result = result + '	 							</td>                                                                                                 ';
				result = result + '	 						</tr>                                                                                                     ';
				for (var i=0; i<json.length; i++)
   				{
					var report = json[i];
					result = result + '	 						<tr>                                                                                                      ';
					result = result + '	 							<td>                                                                                                  ';
					result = result + '	 							<div class="clearfix">                                                                                ';
					result = result + '	 								<div class="pull-left mr10">                                                                      ';
					result = result + '	 									<input id="checkbox'+report.id+'" value="'+report.id+'" name="documentIds[]"  class="checkbox-custom fmcheckbox"  type="checkbox" onclick="getAllCheckBoxes()">   ';
					result = result + '	 									<label for="checkbox'+report.id+'" class="checkbox-custom-label fmcheckbox-lable"></label>                ';
					result = result + '	 								</div>                                                                                             ';
					result = result + '	 								<div class="col-md-95">                                                                            ';
					result = result + '	 								<div class="pull-left mr">';
					if(report.fileFormat == 'doc' || report.fileFormat == 'docx'){
						result = result + '	 									<i class="fa fa-2x text-primary fa-file-word-o"></i>                                                          ';
					}else if(report.fileFormat == 'xlsx'){
						result = result + '	 									<i class="fa fa-2x text-success fa-file-excel-o"></i>                                                          ';
					}else if(report.fileFormat == 'pdf'){
						result = result + '	 									<i class="fa fa-2x text-danger fa-file-pdf-o"></i>                                                          ';
					}else if(report.fileFormat == 'jpg' || report.fileFormat == 'jpeg' || report.fileFormat == 'png' || report.fileFormat == 'tif' || report.fileFormat == 'gif' || report.fileFormat == 'PNG'){
						result = result + '	 									<i class="fa fa-2x text-warning fa-file-image-o"></i>                                                          ';
					}else if(report.fileFormat == 'txt'){
						result = result + '	 									<i class="fa fa-2x text-stylish fa-file-text-o"></i>                                                          ';
					}else if(report.fileFormat == 'java' || report.fileFormat == 'sql' || report.fileFormat == 'js' || report.fileFormat == 'css'){
						result = result + '	 									<i class="fa fa-2x text-stylish fa-file-code-o"></i>';
					}else{
						result = result + '	 									<i class="fa fa-2x text-stylish fa-file-o"></i>';
					}
					
					result = result + '	 								</div>                                                                                            ';
					result = result + '	 								<div class="pull-left">                                                                         ';
					result = result + '	 										<label>'+report.fileName+'</label>                                                         ';
					result = result + '	 										<p class="small text-muted mb5"><strong>Created by:</strong> '+report.createdByName+', '+report.createdDateStr+' </p>';
					result = result + '	 								</div>                                                                                            ';
					if(report.permissionAccess == 0){
						result = result + '	 								<ul class="list-unstyled list-inline text-center extrafields">                                     ';
						result = result + '	 										<li>                                                                                       ';
						result = result + '	 										<a href=\"'+report.fileUrl+'\" download> ';
						result = result + '	 										<i class="fa fa-download"></i> <br>                                                             ';
						result = result + '	 										Download                                                                                       ';
						result = result + '	 										</a></li>                                                                                  ';
						result = result + '	 										<li>                                                                                       ';
						result = result + '	 										<a href="javascript:void(0)" onClick="viewFile(\''+report.fileUrl+'\',\''+report.fileName+'\',\''+report.fileType+'\');">                                                              ';
						result = result + '	 										<i class="fa fa-eye"></i> <br> View   ';
						result = result + '	 										</a></li>                                                                                  ';
						
						result = result + '	 										<li>  <a href="javascript:void(0)" data-toggle="modal" data-target="#fmEmail" onclick="sendMailPopUp('+report.id+');">               ';
						result = result + '	 												<i class="fa fa-envelope-o"></i> <br> Email    ';
						result = result + '	 											</a>  </li>       ';
						
						result = result + '	 										<li>     ';
						 
						result = result + '	 									</ul>        ';
					}else{
						result = result + '	 								<ul class="list-unstyled list-inline text-center extrafields">                                     ';
						result = result + '	 										<li>                                                                                       ';
						result = result + '	 										<a href=\"'+report.fileUrl+'\" download> ';
						result = result + '	 										<i class="fa fa-download"></i> <br>                                                             ';
						result = result + '	 										Download                                                                                       ';
						result = result + '	 										</a></li>                                                                                  ';
						result = result + '	 										<li>                                                                                       ';
						result = result + '	 										<a href="javascript:void(0)" onClick="viewFile(\''+report.fileUrl+'\',\''+report.fileName+'\',\''+report.fileType+'\');">                                                              ';
						result = result + '	 										<i class="fa fa-eye"></i> <br> View   ';
						result = result + '	 										</a></li>                                                                                  ';
						
						result = result + '	 										<li>  <a href="javascript:void(0)" data-toggle="modal" data-target="#fmEmail" onclick="sendMailPopUp('+report.id+');">               ';
						result = result + '	 												<i class="fa fa-envelope-o"></i> <br> Email    ';
						result = result + '	 											</a>  </li>       ';
						
						result = result + '	 										<li>     ';
						result = result + '	 											<a href="javascript:void(0)" data-toggle="modal" data-target="#fmRename"  value="'+report.id+'" onclick="renameFilePop('+report.id+',\''+report.fileName+'\');">';
						result = result + '	 												<i class="fa fa-pencil-square-o"></i> <br>  Rename                                                                   ';
						result = result + '	 											</a> </li>                                                                            ';
						
						result = result + '	 										<li>                                                                             ';
						result = result + '	 											<a href="javascript:void(0)" onclick="deleteEachDcoumentsPopUp('+report.id+');">                                                ';
						result = result + '	 												<i class="fa fa-trash"></i> <br>       Delete ';
						result = result + '	 											</a> </li>          ';
						
						result = result + '	 										<li>  <a href="#" onclick="permissionDocumentPopUp('+report.projectFolderId.id+','+report.id+')"> ';
						result = result + '	 												<i class="fa fa-key"></i> <br> Permission  ';
						result = result + '	 										</a>  </li>    ';
						result = result + '	 									</ul>        ';
					}
					
					result = result + '	 								</div>           ';
					result = result + '	 								</div>           ';
					result = result + '	 							</td>                ';
					result = result + '	 						</tr>                    ';
					
   				}
				result = result + '	 					</tbody>                     ';
				result = result + '	 				</table>                         ';
				result = result + '	 			</div>                               ';

				result = result + '	 		  </div>                                 ';
				result = result + '	 		</div>                                   ';
				
				
				$("#documentListDivId").empty();
				$("#documentListDivId").append(result);
				$("#allFolderCount").append(json.length);
				
			}
		});
		
	}
	 
	//Get all check box checked script started
	function getAllCheckBoxes(){
		var documentIds = [];
		 $("input[name='documentIds[]']:checked").each( function (i) {
			 documentIds[i] = $(this).val();
		});
		 
		 if(documentIds.length > 0){
			 $("ul#checkBoxCheckedId").show();
		 }else{
			 $("ul#checkBoxCheckedId").hide();
		 }
		 
	}
	function toggleDocuments(source) {
	    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	    for (var i = 0; i < checkboxes.length; i++) {
	        if (checkboxes[i] != source)
	            checkboxes[i].checked = source.checked;
	        
	    }
	$('input:checkbox:not(#documentCheckbox)').click(function() {
	    if(!this.checked) {
	        $('#documentCheckbox').removeAttr('checked');
	    }
	});
	}
	//Get all check box checked script ends
 	
	function deleteAllDcouments(){
		var documentIds = [];
		 $("input[name='documentIds[]']:checked").each( function (i) {
			 documentIds[i] = $(this).val();
		});
		 $.ajax({
				type : "POST",
				url : pmsUrl+"/deleteDocuments?${_csrf.parameterName}=${_csrf.token}&documentIds="+documentIds,
				success : function(data) {
					getdocumentListById();
				}
		 });
		 
	}
	
	function getAllDocumentDownload(){
		var sourceleadUrl = '${sourceleadUrl}';
		var projectId = $("#projectId").val();
		var documentIds = [];
		 $("input[name='documentIds[]']:checked").each( function (i) {
			 documentIds[i] = $(this).val();
		}); 	 
		 $.ajax({
				type : "POST",
				url : pmsUrl+"/getAllDocumentDownload?${_csrf.parameterName}=${_csrf.token}&documentIds="+documentIds,
				success : function(data) {
					 if(data && data.endsWith(".zip")){
						location.href=sourceleadUrl+"/downloadFile.html?file="+data;
						projectDocuments(projectId);
					}  
				}
		 });
	}
	
	function deleteEachDcoumentsPopUp(documentId){
		 var result = "";
	 		result = result  +'	  <div class="comMask"></div>';
		 	 result = result  +'	  <div class="modal deleteModal" id="deleteModal">';
		 	 result = result  +'	    <div class="modal-dialog modal-sm">';
		 	 result = result  +'	      <div class="modal-content">';
		 	 result = result  +'	        <div class="modal-header">';
		 	 result = result  +'	          <h5 class="modal-title text-center text-danger">Do you want to delete a document ? </h5>';
		 	 result = result  +'	        </div>';
		 	 result = result  +'	        <div class="modal-footer">';
		 	 result = result  +'	          <button type="button" class="btn btn-danger btn-sm" data-dismiss="modal" onclick="deleteEachDcouments('+documentId+');">Ok</button>';
		 	 result = result  +'	          <button type="button" class="btn btn-default btn-sm cancel" onClick="closeDeleteModal();">Cancel</button>';
		 	 result = result  +'	        </div>';
		 	 result = result  +'	      </div>';
		 	 result = result  +'	    </div>';
		 	 result = result  +'	  </div>';
		 	$("#deleteDocumentPopUpMsgDiv").empty();
		 	$("#deleteDocumentPopUpMsgDiv").append(result);
		 	$(".modal.deleteModal").fadeIn();
		 	$(".comMask").fadeIn();
	}
	
	function deleteEachDcouments(documentId){
		var projectId = $("#projectId").val();
 			 $.ajax({
					type : "POST",
	 				url : pmsUrl+"/eachDeleteDocuments?${_csrf.parameterName}=${_csrf.token}&documentId="+documentId,
					success : function(data) {
						closeDeleteModal();
						projectDocuments(projectId);
					}
			 });
	}
	
	
 	function deleteFolderPopUp(folderId){
 		 var result = "";
 		result = result  +'	  <div class="comMask"></div>';
	 	 result = result  +'	  <div class="modal deleteModal" id="deleteModal">';
	 	 result = result  +'	    <div class="modal-dialog modal-sm">';
	 	 result = result  +'	      <div class="modal-content">';
	 	 result = result  +'	        <div class="modal-header">';
	 	 result = result  +'	          <h5 class="modal-title text-center text-danger">Do you want to delete a folder ? </h5>';
	 	 result = result  +'	        </div>';
	 	 result = result  +'	        <div class="modal-footer">';
	 	 result = result  +'	          <button type="button" class="btn btn-danger btn-sm" data-dismiss="modal" onclick="deleteFolder('+folderId+');">Ok</button>';
	 	 result = result  +'	          <button type="button" class="btn btn-default btn-sm cancel" onClick="closeDeleteModal();">Cancel</button>';
	 	 result = result  +'	        </div>';
	 	 result = result  +'	      </div>';
	 	 result = result  +'	    </div>';
	 	 result = result  +'	  </div>';
	 	$("#deleteFolderPopUpMsgDiv").empty();
	 	$("#deleteFolderPopUpMsgDiv").append(result);
	 	$(".modal.deleteModal").fadeIn();
	 	$(".comMask").fadeIn();
	}
 	function closeDeleteModal() {
 		$(".modal.deleteModal").fadeOut();
 		$(".comMask").fadeOut();
 	}
 	function deleteFolder(folderId){
 		var projectId = $("#projectId").val();
 		var userId = '${SourceLeadWeb_userId}';
 		 $.ajax({
 				type : "POST",
 				url : pmsUrl+"/deleteProjectDcoumentFolder?${_csrf.parameterName}=${_csrf.token}&folderId="+folderId+"&projectId="+projectId+"&userId="+userId,
 				success : function(data) {
 					closeDeleteModal();
 					projectDocuments(projectId);
 				}
 		 });
 	}
	function folederReName(folderId,folderName){
	    var result = "";
		 result = result  +'<form role="form"  id="reNameFolder_form" name="reNameFolder_form" method="post" >';
		 result = result  +'	<div class="popupHead">';
		 result = result  +'		<h2>Rename Folder</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="closeFolder();"></a>';
		 result = result  +'	</div>';
		 result = result  +'	<div class="popupMid clearfix minHeight100">';
		 result = result  +'      <div class="col-sm-12 noPad mt clearfix">';
		 result = result  +'			<label class="col-md-4 padding_left">Create Folder<span class="text-danger">&nbsp;*</span></label>';
		 result = result  +'          	<div class="col-md-8 padding_right" id="rmoveErrMsgEditCF">';
		 result = result  +'          	   <input type="text" class="form-control input-sm" placeholder="Create Folder" value="'+folderName+'" id="folderReName" name="folderName" field-name="Folder Name" data-validation="required validate_AlphaNumber validate_length length2-100" onkeyUp="validateRenameFolderName('+folderId+');"  onPaste="" onkeydown="if (event.keyCode == 13) { return false;}">';
		 result = result  +'			</div>';
		 result = result  +'		</div>';	         
		 result = result  +'	</div>';
		 result = result  +'	<div class="immiPopFoot">';
		 result = result  +'        <input value="Save" class="" type="button" id="save_reNmaeFolder"  onclick="saveReNameFolder('+folderId+');">';
		 result = result  +'        <input value="Cancel" class="popCan" type="button" onclick="closeFolder();">';
		 result = result  +'      </div>	';	
		 result = result  +'  </form>';
		$("#renameFolderDivId").empty();
		$("#renameFolderDivId").append(result); 
		
	}
</script>

<script>
function saveReNameFolder(folderId){
	var projectId = $("#projectId").val();
	var folderName = $("#folderReName").val();
	if ($('#reNameFolder_form').validate(false, validationSettings)){
		$.ajax({
			type : "POST",
				url : pmsUrl+"/projectDocumentFolderRename?${_csrf.parameterName}=${_csrf.token}&folderId="+folderId+"&folderName="+folderName,
			success : function(data) {
				closeFolder();
				projectDocuments(projectId);
			}
	 }); 
		return true;
	 }else{ 
			return false;
	 } 
}
$('body').on('blur', '#reNameFolder_form', function() {
	$("#reNameFolder_form").showHelpOnFocus();
	$("#reNameFolder_form").validateOnBlur(false, validationSettings);
});
</script>


<div class="advancedsearch-mask"></div>

    <div class="popUpOut" id="renameFolder" role="dialog">
      <div id="renameFolderDivId"></div>
    </div>
  
  
 <!-- Create Folder Popup Modal-->
 
  <div class="modal folderPermission" id="folderPermission" role="dialog">
    <div class="modal-dialog modal-lg">
      <div id="foderPermissionDivId">
         <%-- <jsp:include page="projectPermissions.jsp"></jsp:include> --%>
      </div>
    </div>
  </div>
 
<!-- Create Folder Popup ends -->
<script>
function permissionDocumentPopUp(folderId,documentId){
	$("#folderPermission").fadeIn();
	$(".advancedsearch-mask").fadeIn();
	var projectId = $("#projectId").val();
	var values = "Active";
	$.ajax({
    	type: "POST",
        url: pmsUrl+"/getProjectDocumentPermissions?folderId="+folderId+"&projectId="+projectId+"&status="+values+"&documentId="+documentId, 
        success: function(data) {
        	$("#foderPermissionDivId").empty();
        	$("#foderPermissionDivId").append(data);
        	prepareChosenScripts();
        }
	});	
}
function permissionPopUp(folderId){
	$("#folderPermission").fadeIn();
	$(".advancedsearch-mask").fadeIn();
	var projectId = $("#projectId").val();
	var documentId = "";
	var values = "Active";
	$.ajax({
    	type: "POST",
        url: pmsUrl+"/getProjectDocumentPermissions?folderId="+folderId+"&projectId="+projectId+"&status="+values+"&documentId="+documentId, 
        success: function(data) {
        	$("#foderPermissionDivId").empty();
        	$("#foderPermissionDivId").append(data);
        	prepareChosenScripts();
        }
	});	
	/* var result = "";
	result = result  +'<form role="form"  id="projectFolderPermission_form" name="projectFolderPermission_form">';
	result = result  +'	<div class="modal-content">';
	result = result  +'    <div class="modal-header">';
	result = result  +'      <button type="button" class="close" data-dismiss="modal">&times;</button>';
	result = result  +'      <h4 class="modal-title">Permissions</h4>';
	result = result  +'    </div>';
	result = result  +'    <div class="modal-body">';
	result = result  +'    <div class="row">';
	result = result  +'        <div class="col-md-6">';
	result = result  +'          <label><strong>Roles Based</strong></label>';
	result = result  +'		          <div class="panel panel-default">';
	result = result  +'			          <div class="panel-body">';
	result = result  +'				          <div class="row minHeight40 mb">';
	result = result  +'				          	<label class="col-md-3">View</label>';
	result = result  +'				          	<div class="col-md-9 dropdown-select">';
	result = result  +'				          		<select class="form-control chosen-select choose" multiple  id="readRoleIds" name="readRoleIds" data-placeholder="Select read Roles">';
	result = result  +'						        </select>';
	result = result  +'				          	</div>';
	result = result  +'				          </div>';
	result = result  +'				          <div class="row minHeight40 mb">';
	result = result  +'				          	<label class="col-md-3">Owner</label>';
	result = result  +'				          	<div class="col-md-9 dropdown-select">';
	result = result  +'				               <select class="form-control chosen-select choose" multiple  id="writeRoleIds" name="writeRoleIds" data-placeholder="Select write Roles">';
	result = result  +'						        </select>';
	result = result  +'				          	</div>';
	result = result  +'				          </div>';
	result = result  +'			          </div>';
	result = result  +'		          </div>';
	result = result  +'           </div>';
	result = result  +'		   <div class="col-md-6">';
	result = result  +'			<label><strong>Member Based</strong></label>';
	result = result  +'				<div class="panel panel-default">';
	result = result  +'					<div class="panel-body">';
	result = result  +'					<div class="row minHeight40 mb">';
	result = result  +'						<label class="col-md-3">View</label>';
	result = result  +'						<div class="col-md-9 dropdown-select">';
	result = result  +'							<select class="form-control chosen-select choose" multiple  id="readOtherMemberIds" name="readOtherMemberIds" data-placeholder="Select Read Project Members" >';
	result = result  +'					</select>';
	result = result  +'						</div>';
	result = result  +'					</div>';
	result = result  +'					<div class="row minHeight40 mb">';
	result = result  +'						<label class="col-md-3">Owner</label>';
	result = result  +'						<div class="col-md-9 dropdown-select">';
	result = result  +'					     <select class="form-control chosen-select choose" multiple  id="writeOtherMemberIds" name="writeOtherMemberIds" data-placeholder="Select Write Project Members" >';
	result = result  +'					</select>';
	result = result  +'						</div>';
	result = result  +'					</div>';
	result = result  +'					</div>';
	result = result  +'				</div>';
	result = result  +'		</div>';
	result = result  +'      </div>';
	result = result  +'    </div>';
	result = result  +'    <div class="modal-footer">';
	result = result  +'     <button type="button" class="btn btn-primary mr10" onclick="saveFolderNamePermission('+folderId+');">Apply</button>';
	result = result  +'      <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>';
	result = result  +'    </div>';
	result = result  +'  </div>';
	result = result  +'  </form>'; */
	/* $("#foderPermissionDivId").empty();
	$("#foderPermissionDivId").append(result);
	
	getUserRoleList();
	getProjectMemberlist(); */
}

</script>
<script type="text/javascript">
         function prepareChosenScripts(){ 
        	      var config = {
        			  '.chosen-select'           : {},
        			  '.chosen-select-deselect'  : {allow_single_deselect:true},
        			  '.chosen-select-no-single' : {disable_search_threshold:10},
        			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
        			  '.chosen-select-width'     : {width:"95%"}
        			};
        		   for (var selector in config) {
        			  $(selector).chosen(config[selector]);
        			};
        			$('.chosen-select').each(function() {
        			var $this = $(this);
        			$this.next().css({'width': '100%'});
        			})
           };
         
</script>
<script type="text/javascript">
	function saveFolderNamePermission(folderId,documentId){
		 if ($('#projectFolderPermission_form').validate(false, validationSettings)){
			 var projectId = $("#projectId").val();
			//It will escape all the special characters
	        var formData =  getFormData1('projectFolderPermission_form'); 
			$.ajax({
					type : "POST",
	        	    data: formData,
	  		        processData: false,
	  		        contentType:false, 
					url : pmsUrl+"/saveProjectNamePermissions?"+tokenParamName+"="+tokenValue+"&folderId="+folderId+"&documentId="+documentId,
					success : function(result) {
	                        $("#folderPermission").fadeOut();
							$(".advancedsearch-mask").fadeOut();
							projectDocuments(projectId);
					},
				}); 
				return true;
		 }else{ 
				return false;
		 } 
		}
		$('body').on('blur', '#projectFolderPermission_form', function() {
		$("#projectFolderPermission_form").showHelpOnFocus();
		$("#projectFolderPermission_form").validateOnBlur(false, validationSettings);
	});
		
</script>
 
<script>
function getUserRoleList(){
	var projectId = $("#projectId").val();
	var readRoleIds = $("#readRoleIds").val();
	var values = "Active";
	$.ajax({
    	type: "POST",
        url: pmsUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values, 
        success: function(json) {
        	var output = "";
        	for (var i=0; i<json.length; i++)
			{
        		    var opt = json[i];
	        		output = output + "<option value='"+opt.userRoleId+"'>"+opt.userRoleType+"</option>";
			}
        	$("#readRoleIds").chosen();
        	$("#readRoleIds").empty();
     		$("#readRoleIds").append(output);
     	    $("#readRoleIds").trigger("chosen:updated");
     	    
     	    $("#writeRoleIds").chosen();
       	    $("#writeRoleIds").empty();
    		$("#writeRoleIds").append(output);
    	    $("#writeRoleIds").trigger("chosen:updated");
        }
	});	
}
</script>
<script>
function getProjectMemberlist(){
	var projectId = $("#projectId").val();
	var values = "Active";
	$.ajax({
    	type: "POST",
        url: pmsUrl+"/getProjectEmployeeListById?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&status="+values, 
        success: function(json) {
        	var output = "";
        	for (var i=0; i<json.length; i++)
			{
        		    var opt = json[i];
	        		output = output + "<option value='"+opt.id+"'>"+opt.empName+"</option>";
			}
        	$("#readOtherMemberIds").chosen();
        	$("#readOtherMemberIds").empty();
     		$("#readOtherMemberIds").append(output);
     	    $("#readOtherMemberIds").trigger("chosen:updated");
     	    
        	$("#writeOtherMemberIds").chosen();
        	$("#writeOtherMemberIds").empty();
     		$("#writeOtherMemberIds").append(output);
     	    $("#writeOtherMemberIds").trigger("chosen:updated");
        }
	});	
}
</script>

<script type="text/javascript">
	function saveFolderName(){
		var projectId = $("#projectId").val();
			 if ($('#projectFolder_form').validate(false, validationSettings)){
				$("#save_folder").attr("disabled", "disabled");
				//It will escape all the special characters
		        var formData =  getFormData('projectFolder_form'); 
				$.ajax({
						type : "POST",
		        	    data: formData,
						url : pmsUrl+"/saveProjectDocumentFolder?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId,
						success : function(result) {
							    closeFolder();
								$("#save_folder").removeAttr("disabled");
								$("#projectDocumentType_form")[0].reset();
								$("#projectFolder_form")[0].reset();
								projectDocuments(projectId);
								
						},
					}); 
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#projectFolder_form', function() {
		$("#projectFolder_form").showHelpOnFocus();
		$("#projectFolder_form").validateOnBlur(false, validationSettings);
	});
		
</script>



 <!-- Upload file Popup Modal-->
 <form role="form"  id="projectDocumentType_form" name="projectDocumentType_form" method="post" >
		<div class="modal fade" id="uploadFile_fm" role="dialog">
		    <div class="modal-dialog fm-modaldialog">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		          <h4 class="modal-title">Upload File</h4>
		        </div>
		        <div class="modal-body mt mb">
		          <div class="row">
		          <div class="clearfix mb minHeight40" id="uploadDocDiv"> 
		            <label class="col-md-3">Upload File<span class="text-danger">&nbsp;*</span></label>
					  <div class="col-md-9"> 
						  <div class="input-group positionRelative errorInptgroup" id="uploadFileGroup"> 
							  <label class="input-group-btn"> <span class="btn btn-primary" onchange="uploadProjectDocument();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File 
							  <input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload"> 
							  </span> </label> 
						  		<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Document..." field-name="Document" data-validation="required"> 
						  </div> 
						  <div class="clearfix"></div> 
					  </div> 
					</div> 
					<div class="clearfix mb minHeight40 dropdown-select">
					<label class="col-md-3">Select Folder<span class="text-danger">&nbsp;*</span></label>
		           	<div class="col-md-9">
    				   <select class="form-control" name="projectFolderId" id="projectFolderId"  field-name="Folder" data-validation="required">
		           	  </select>
		          	</div>
		          	</div>
		          </div>
		        </div>
		        <div class="modal-footer">
		        <input type="button" value="Upload" name="btnAddPr" id="save_doc" class="btn btn-primary mr10" onClick ="storeProjectDocument();" />
		          <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
		        </div>
		      </div>
		    </div>
		  </div>
</form>
<!-- Upload file Popup ends -->




<!-- Rename Popup Modal-->
  <div class="modal fade" id="fmRename" role="dialog">
    <div class="modal-dialog fm-modaldialog">
      <div id="renameFileDivId"></div>
    </div>
  </div>
<!-- Create Folder Popup ends -->
<script>
function renameFilePop(documentId,fileName){
	var result = "";
	result = result  +'<form role="form"  id="reNameFile_form" name="reNameFile_form" method="post" > ';
	result = result +'<div class="modal-content">';
	result = result +'  <div class="modal-header">';
	result = result +'    <button type="button" class="close" data-dismiss="modal">&times;</button>';
	result = result +'    <h4 class="modal-title">Rename File <span class="text-danger">&nbsp;*</span></h4>';
	result = result +'  </div>';
	result = result +'  <div class="modal-body mt mb">';
	result = result +'    <div class="row">';
	result = result +'    	<label class="col-md-4">Enter a New Name</label>';
	result = result +'    	<div class="col-md-8" id="errMsgReDocument">';
	result = result +'    		<input type="text" class="form-control input-sm" placeholder="Enter a New Name" id="reNamefile" name ="fileNmae" value="'+fileName+'" field-name="File Name" data-validation="required validate_length length2-100" onkeyUp="validateDocumentName();">';
	result = result +'    	</div>';
	result = result +'    </div>';
	result = result +'  </div>';
	result = result +'  <div class="modal-footer">';
	result = result +'   <button type="button" class="btn btn-primary mr10" id="save_rename" onclick="saveRenameFile('+documentId+');">Save</button>';
	result = result +'    <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>';
	result = result +'  </div>';
	result = result +'</div>';
	result = result  +'</form>';
  	$("#renameFileDivId").empty();
  	$("#renameFileDivId").append(result);
}
function saveRenameFile(documentId){
	var projectId = $("#projectId").val();
	var fileName = $("#reNamefile").val();
	 if ($('#reNameFile_form').validate(false, validationSettings)){
			//It will escape all the special characters
	        var formData =  getFormData('reNameFile_form'); 
			$.ajax({
					type : "POST",
	        	    data: formData,
					url : pmsUrl+"/saveReNameProjectDocument?${_csrf.parameterName}=${_csrf.token}&documentId="+documentId+"&fileName="+fileName,
					success : function(result) {
						$("#fmRename").modal("hide");
						projectDocuments(projectId);
					},
				}); 
				return true;
		 }else{ 
				return false;
		 } 
}
$('body').on('blur', '#reNameFile_form', function() {
	$("#reNameFile_form").showHelpOnFocus();
	$("#reNameFile_form").validateOnBlur(false, validationSettings);
});
</script>

<!-- <div class="success-Message text-center">
	<i class="fa fa-check-circle fa-2x paddingRight pull-left"></i><span class="pull-left">Success fully Folder Created</span>
</div> -->



	<div class="popMask"></div>
	<form role="form"  id="projectFolder_form" name="projectFolder_form" method="post" >
    <div class="popUpOut" id="createFolder">
		<div class="popupHead">
			<h2>Create Folder</h2><a href="javascript:void(0)" class="fa fa-close popCan" onclick="closeFolder();"></a>
		</div>
		<div class="popupMid clearfix minHeight100">
          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-4 padding_left">Create Folder<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-8 padding_right" id="rmoveErrMsgCF">
              	   <input type="text" class="form-control" placeholder="Create Folder" id="folderName" name="folderName" field-name="Folder Name" data-validation="required validate_AlphaNumber validate_length length2-100" onkeyUp="validateFolderName()"  onPaste="" onkeydown="if (event.keyCode == 13) { return false;}">
				</div>
			</div>	         
		</div>
		<div class="immiPopFoot">
            <input value="Save" class="" type="button" id="save_folder"  onclick="saveFolderName();">
            <input value="Cancel" class="popCan" type="button" onclick="closeFolder();">
          </div>		
	  </div>
	  </form>
	
	 
  
<script>
function createFolder(){
	$(".popMask").fadeIn(200);
	$("#createFolder").fadeIn(100);
}
function closeFolder(){
	$(".popMask").fadeOut(200);
	$("#createFolder, #renameFolder").fadeOut(100);
	 $('#rmoveErrMsgCF').find('p.jquery_form_error_message').remove();
}

function renameFolder(){
	$(".popMask").fadeIn(200);
	$("#renameFolder").fadeIn(100);
}

</script>

 <script>
 
 function validateDocumentName(){
	 var documentName = document.getElementById("reNamefile").value.trim();
	 var projectId = $("#projectId").val();
	 $('#errMsgReDocument').find('p.jquery_form_error_message').remove();
	 $.ajax({
			type : "GET",
			url : pmsUrl+ "/validateDocumentName?${_csrf.parameterName}=${_csrf.token}&documentName="+documentName+"&projectId="+projectId,
			success : function(data) {
				 if (data == true) {
					 $("#save_rename").attr("disabled", "disabled");
					 $("input#reNamefile").after("<p class='jquery_form_error_message'>"+ documentName+ " is Already Existed</span>");
					document.getElementById("reNamefile").setAttribute('record-exist', 'yes');
					document.getElementById("reNamefile").setAttribute('record-exist-errorMsg',documentName+ ' is Already Existed');
				} else {
					$('input#reNamefile').removeAttr("record-exist record-exist-errormsg");
					$("#save_rename").removeAttr("disabled");
				} 
			},
		});
	 
 }
 function validateFolderName(){
	 var FolderName = document.getElementById("folderName").value.trim();
	 var projectId = $("#projectId").val();
	 $('#rmoveErrMsgCF').find('p.jquery_form_error_message').remove();
		$.ajax({
			type : "GET",
			url : pmsUrl+ "/validateFolderName?${_csrf.parameterName}=${_csrf.token}&folderName="+FolderName+"&projectId="+projectId,
			success : function(data) {
				 if (data == true) {
					 $("#save_folder").attr("disabled", "disabled");
					 $("input#folderName").after("<p class='jquery_form_error_message'>"+ FolderName+ " is Already Existed</span>");
					document.getElementById("folderName").setAttribute('record-exist', 'yes');
					document.getElementById("folderName").setAttribute('record-exist-errorMsg',FolderName+ ' is Already Existed');
				} else {
					$('input#folderName').removeAttr("record-exist record-exist-errormsg");
					$("#save_folder").removeAttr("disabled");
				} 
			},
		});
 }
 function validateRenameFolderName(folderId){
	 var FolderName = document.getElementById("folderReName").value.trim();
	 var projectId = $("#projectId").val();
	 $('#rmoveErrMsgEditCF').find('p.jquery_form_error_message').remove();
		$.ajax({
			type : "GET",
			url : pmsUrl+ "/validateFolderName?${_csrf.parameterName}=${_csrf.token}&folderName="+FolderName+"&projectId="+projectId+"&folderId="+folderId,
			success : function(data) {
				 if (data == true) {
					 $("#save_reNmaeFolder").attr("disabled", "disabled");
					 $("input#folderReName").after("<p class='jquery_form_error_message'>"+ FolderName+ " is Already Existed</span>");
					document.getElementById("folderReName").setAttribute('record-exist', 'yes');
					document.getElementById("folderReName").setAttribute('record-exist-errorMsg',FolderName+ ' is Already Existed');
				} else {
					$('input#folderReName').removeAttr("record-exist record-exist-errormsg");
					$("#save_reNmaeFolder").removeAttr("disabled");
				} 
			},
		});
 }
 </script> 
<script>
$(".close, .cancel").click(function(){
	$("#save_folder").removeAttr("disabled");
	//Recet the form
	$("#projectDocumentType_form")[0].reset();
	$("#projectFolder_form")[0].reset();
	
	$("projectDocumentType_form").validate().resetForm();
	$("projectFolder_form").validate().resetForm();
	
});
function permissionClose(){
	$("#folderPermission").fadeOut();
	$(".advancedsearch-mask").fadeOut();
}
</script>
 
<script>
document.getElementById("uploadBtn").onchange = function () {
	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
};
/* upload file script for size validation 2MB*/
   /* var selectedFile = document.getElementById("uploadBtn");
    selectedFile.addEventListener("change", function() {
  var docSize = this.files[0].size;
  $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
  if(docSize > 2097152){
	  document.getElementById("uploadFile").value = '';
	  $("#uploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
  }
}); */
function uploadProjectDocument(){
	fileUpload("uploadBtn", "ProjectDoument", "projectDocumentType_form");
}
</script>
 

<script type="text/javascript">
	function storeProjectDocument(){
		var projectId = $("#projectId").val();
			 if ($('#projectDocumentType_form').validate(false, validationSettings)){
				$("#save_doc").attr("disabled", "disabled");
         		$("#save_doc" ).addClass('button').val('Processing..');
         		
				//It will escape all the special characters
		        var formData =  getFormData('projectDocumentType_form'); 
				$.ajax({
						type : "POST",
		        	    data: formData,
						url : pmsUrl+"/saveProjectDocument?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId,
						success : function(result) {
							//$(".modal-backdrop, .modal").fadeOut();
							$('#uploadFile_fm').modal('hide');
							$("#save_doc").removeAttr("disabled", "disabled");
			         		$("#save_doc" ).removeClass('button').val('Upload');
							projectDocuments(projectId);
							$("#projectDocumentType_form")[0].reset();
							$("#projectFolder_form")[0].reset();
							
							
						},
					}); 
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#projectDocumentType_form', function() {
		$("#projectDocumentType_form").showHelpOnFocus();
		$("#projectDocumentType_form").validateOnBlur(false, validationSettings);
	});
		
</script>




<!-- Email Popup Modal-->

  <div class="modal fade" id="fmEmail" role="dialog">
    <div class="modal-dialog fm-modaldialog">
      <div id="sendEmailPopupDivId"></div>
    </div>
  </div>

 
 <script>
 function sendMailPopUp(documentId){
	 var documentId = "'"+documentId+"'";
	   var result = "";
	    result = result  +'<form role="form"  id="sendEmail_form" name="sendEmail_form" method="post" >';
		result = result  +'	 <div class="modal-content">';
		result = result  +'     <div class="modal-header">';
		result = result  +'       <button type="button" class="close" data-dismiss="modal">&times;</button>';
		result = result  +'       <h4 class="modal-title">Email</h4>';
		result = result  +'     </div>';
		result = result  +'     <div class="modal-body mt mb">';
		result = result  +'       <div class="row">';
		result = result  +'       	<label class="col-md-3">Send To <span class="text-danger">*</span></label>';
		result = result  +'       	<div class="col-md-9">';
		result = result  +'       		<input type="text" class="form-control input-sm" placeholder="Send To" name="sendTo" field-name="Email" data-validation="required validate_Space validate_length length2-100 validate_email">';
		result = result  +'       	</div>';
		result = result  +'       </div>';
		result = result  +'       <div class="row mt mb">';
		result = result  +'       	<label class="col-md-3">Subject<span class="text-danger">*</span></label>';
		result = result  +'       	<div class="col-md-9">';
		result = result  +'       		<input type="text" class="form-control input-sm" placeholder="Subject" name="subject" field-name="Subject" data-validation="required validate_Space validate_length length2-500">';
		result = result  +'       	</div>';
		result = result  +'       </div>';
		result = result  +'      <div class="row">';
		result = result  +'       	<label class="col-md-3">Message<span class="text-danger">*</span></label>';
		result = result  +'       	<div class="col-md-9">';
		result = result  +'       		<textarea rows="2" cols="50" placeholder="Message" class="form-control input-sm" name="message" field-name="Message" data-validation="required validate_length length2-500"></textarea>';
		result = result  +'       	</div>';
		result = result  +'       </div>';
		result = result  +'     </div>';
		result = result  +'     <div class="modal-footer">';
		result = result  +'      <button type="button" class="btn btn-primary mr10" onclick="sendEmail('+documentId+');">Email</button>';
		result = result  +'       <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>';
		result = result  +'     </div>';
		result = result  +'   </div>';
		result = result  +'  </form>';
		$("#sendEmailPopupDivId").empty();
		$("#sendEmailPopupDivId").append(result);
 }
 </script>
 <script type="text/javascript">
	function sendEmail(documentId){
		var projectId = $("#projectId").val();
			 if ($('#sendEmail_form').validate(false, validationSettings)){
				//It will escape all the special characters
		        var formData =  getFormData('sendEmail_form'); 
				
				$.ajax({
						type : "POST",
		        	    data: formData,
						url : pmsUrl+"/projectDocumentMailSending?${_csrf.parameterName}=${_csrf.token}&documentId="+documentId,
						success : function(result) {
							   $("#fmEmail").modal("hide");
								projectDocuments(projectId);
						},
					}); 
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#sendEmail_form', function() {
		$("#sendEmail_form").showHelpOnFocus();
		$("#sendEmail_form").validateOnBlur(false, validationSettings);
	});
		
		function mailAllDocuments(){
			var projectId = $("#projectId").val();
			var documentId = [];
			 $("input[name='documentIds[]']:checked").each( function (i) {
				 documentId[i] = $(this).val();
			});
			 if(documentId != null && documentId != '' &&  documentId != 'undefined'){
				 sendMailPopUp(documentId);
			 }else{
				 alert("please check the checkBox");
			 }
		}
</script>

<!-- Email Folder Popup ends -->



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
var validationSettings = {
        errorMessagePosition : 'element',
        borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
     }; 


function closePopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>
<script>
function removeDocNameDiv(){
	document.getElementById("uploadDocDiv").style.display="block";
	document.getElementById("docNameDiv").style.display="none";
	$("#uploadFile").attr("data-validation", "required validate_Space validate_docimgpdf");  
}
</script>

<script> 	
// Edit pop
<script type="text/javascript">
$(document).ready(function() {
    $(".glyphicon-edit").click(function(){
		$(".settPop").slideDown(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".settPop i, .btn-default").click(function(){
		$(".settPop").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
	
	$("input:text").keypress(function(event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            return false;
        }
    });
});		
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
 