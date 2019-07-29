<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
var atsUrl = '${SourceLeadWeb_atsUrl}';
function assetNotes(assetId,assetUUID){
		var result = "";
		result = result  +'<div class="clearfix timesheetHead">';
		result = result  +'    <div class="project_tabs_inn_head addUser-org"> '; 
		result  = result + '<div class="success_pop" id="clientNotesSelector" style="display: none;">';
	    result  = result + '<i class="fa fa-check-circle"></i><span id="clientNotesMsgDiv"></span>';
	    result  = result + '</div>';
		result = result + '<ul class="padding_right">';
		result = result  +'    	<a href="javascript:void(0)" onClick="addAssetNotesNew('+assetId+',\''+assetUUID+'\');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Note</a>                                                            ';
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result = result  +'  <div class="clearfix"></div> ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="asetsNotesListDiv" ></div>';
		result  = result + '   </div>'; 
		result = result+'	    </div>';
		result = result+'	    </div>'; 
	    //$("#tab-1").empty();
	    $("#tab-1").empty(); 
	    $("#tab-2").empty();
	    $("#tab-3").empty();
	    $("#tab-4").empty();
	    $("#tab-2").append(result);  
	    assetNotesList(assetUUID);    
}
</script>
<script>
function addAssetNotesNew(assetId,assetUUID) {	
	var result = "";
	result = result  +'          <form role="form"  id="notes_Asset" name="notes_Asset" method="post" > ';
	result = result  +'          <input type="hidden" id="assetId" name="assetId" value="'+assetId+'"> ';
	result = result  +'          <input type="hidden" id="noteType" name="noteType" value="Asset"> ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closeDocPopup();"></i> ';
	
	result = result  +'                <div class="row clearfix"> ';
	result = result+'	                  <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Note*" value="" id="noteDescription" name="noteDescription" data-validation="required validate_length length1-5000"  field-name="Note" class="form-control" ></textarea>';
	result = result+'	                  </div>';
	result = result+' <div class="col-md-9 checkBox"> ';
	result = result+'   <input type="checkbox" class="pull-left" value= "Y" id="allowAll" name = "allowAll" checked="checked">';
	result = result + '	                    <p class="pull-left mt5">Allow all users</p>';
	result = result  +'                  </div>';     
	result = result  +'                  <div class="clearfix mt5" id="docAddButton"> ';
	result = result  +'                  <div class="pull-right mt5"> ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_note" class="btn btn-sm btn-primary mr" onClick ="storeReqNotes('+assetId+',\''+assetUUID+'\');" />'; 
	result = result  +'                  </div> ';
	result = result  +'                  </div> ';
	result = result  +'                </div>';
	
	result = result  +'                </form>';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
			
			$(".edit-details").toggleClass("edit-details-active");
			 $(".close-div").click(function(){ 
				 $(this).parent("div.edit-details").removeClass("edit-details-active");
				 });
			 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details, .ui-datepicker-prev, .ui-datepicker-next").click(function(e){
				 e.stopPropagation();
			 });
	
	}
</script>
<script>
function closeDocPopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>
<script>
function assetNotesList(assetUUID){
	var urlForm = "";
	    urlForm = "/getAssetNotes?"+tokenParamName+"="+tokenValue+"&assetUUID="+assetUUID;
		
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
         success :function(data) {
		var output = "";
	    if(data != null && data != 'undefined' && data != "" && data != '[]'){
	    	var json = JSON.parse(data);
	    output  = output + '   <div class="project_tabs_inn_head addUser-org"> ';
	    output  = output + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
     	for(var i=0;i<json.length;i++){
       	var report = json[i];
    	if(report.checkBox == 'Y' || (report.checkBox == 'N' && '${SourceLeadWeb_userId}' == report.userIdStr)){
   	  	output = output+'<div class="comments-div comments-inner commentsNotes clearfix">';
   	    output = output+'    <div class="commentsimg">';
   	    output = output+'    <i class="fa fa-comments"></i>';
   	    output = output+'        <div class="commentsimg-inner">';
   	    if(report.commentAuthorImage != null && report.commentAuthorImage !='' && report.commentAuthorImage !='undefined'){
   	    output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="'+report.commentAuthorImage+'"/>';
   	    }else{
   	   	output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="${SourceLeadWeb_sourceleadDomainUrl}/resources/images/userprofileImg.jpg"/>';
   	    }
   	    output = output+'        </div>';
   	    output = output+'    </div>';
   	    output = output+'     <div class="comments-content">';
   	    output = output+'         <div class="commentsNotes_inner">';
   	    output = output+'         <p class="comment-by">';
   	    output = output+'            By <span class="text-danger" title = "'+report.userName+'">'+report.userName+'</span>';
   	    if(report.assetName !=null && report.assetName != 'undefined'){
   		 output = output+'          Serial  Number: <span class="text-danger" title = "'+report.assetName+'"> '+report.assetName+'</span>';
   	    }
   	    
   	    else if(report.contactName !=null && report.contactName != 'undefined'){
   	    output = output+'          Contact Name: <span class="text-danger" title = "'+report.contactName+'">'+report.contactName+'</span>';
   	    }
   	 	else if(report.contract !=null && report.contract != 'undefined'){
   		output = output+'         Contract:  <span class="text-danger" title = "'+report.contract+'">'+report.contract+'</span>';
   	    }
   	    
   	    else if(report.invoiceId !=null && report.invoiceId != 'undefined'){
   		output = output+'         Invoice Id:  <span class="text-danger" title = "'+report.invoiceId+'">'+report.invoiceId+'</span>';
   	    }
   	    output = output+'            <span class="small pull-right">'+report.createdDateString+'</span></p>';
   	    output = output+'         <p class="comment_text pull-left">'+report.comment+'</p>';
   	    output = output+'         </div>';
   	    output = output+'     </div>';
   	    output = output+'</div>';
    	}
     }
	}else{
		var output ="";
     output = output+"   <div class='dataTables_empty'>"; 
	 output = output+"  </div> ";
}
	output  = output + '              </div>';
	output  = output + '        </div>';
	$("#asetsNotesListDiv").empty(); 
    $("#asetsNotesListDiv").append(output);
 },
      });
}
</script>


<script type="text/javascript">
	function storeReqNotes(assetId,assetUUID){
		if ($('#notes_Asset').validate(false, validationSettings)){
				var frm = $('#notes_Asset').serializeFormJSON();
				var con = JSON.stringify(frm);
				//con = con.replace(/[{}]/g, "");
				var form = $('#notes_Asset')[0];
	      		var formData = new FormData(form);
	      		formData.append("json",con);
	      		
	      		var userId = '${SourceLeadWeb_userId}';
	      		formData.append("userId",userId);
	      	    var locationCode ='${SourceLeadWeb_locationCode}'; 
	      	    formData.append("locationCode",locationCode);
	      	    
				
				$("#save_note").attr("disabled", "disabled");
         		$("#save_note" ).addClass('button').val('Processing..');
		        var jsonData = encodeURIComponent(con);
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeNotes?"+tokenParamName+"="+tokenValue+"",
						success : function(result) {
							/* closeDocPopup();
							assetNotesList(assetId); */
							if (result && result.response === "INVALID_DATA") {
							    showError(result.responseMessage);
							} else if (result.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (result.response === "SUCCESS") {
								closeDocPopup();
								assetNotesList(assetUUID);
							} else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
							    showError("Operation failed, please try again");
							}
							
						},
					});
					$('.add-comment').toggle(200);
					return true;
		}else {
			return false;	
		}
		}
		$('body').on('blur', '#notes_Asset', function() {
		$("#notes_Asset").showHelpOnFocus();
		$("#notes_Asset").validateOnBlur(false, validationSettings);
		
	});
</script>
<script>
function assetDetails(assetUUID) {
	$.ajax({
			type : "POST",
			url : atsUrl	+ "/getAssetById?"+tokenParamName+"="+tokenValue+"&assetUUID="+assetUUID,
			success : function(json) {
				    var name = "--";

				    var result = "";
					result = result + '<div class="require_wrap_details">';
					result = result + '<div class="clearfix"><h4 class="pull-left mr margin-top0">'+json.categoryName+' Asset details:</h5>';
					'<sec:authorize access="hasRole(\'ROLE_EDIT_ASSET\')">' 
						result = result + '<a class="pull-left glyphicon glyphicon-edit" title="Edit" onclick="summaryEditAssetAssign(\''+assetUUID+'\');"></a>';
					'</sec:authorize>';
					result = result + '</div>';
					result = result + '<div class="require_one clearfix">';
					
					result = result + '<div class="require_wrap_details_cta"> <b><strong>Category Name:</strong></b> <span class = "wordWrap-breakword">'+json.categoryName+'</span>';
					result = result + '<div class="clearfix"></div>';
					result = result + ' </div>';
					
					if(json.name != null && json.name != "" && json.name != "undefined"){
						result = result + '<div class="require_wrap_details_cta"> <b><strong>Name:</strong></b> <span class = "wordWrap-breakword">'+json.name+'</span>';
						result = result + '<div class="clearfix"></div>';
						result = result + ' </div>';	
					}
					
					if(json.sno != null && json.sno != "" && json.sno != "undefined"){
						result = result + '<div class="require_wrap_details_cta"> <b><strong>Serial Number:</strong></b> <span class = "wordWrap-breakword">'+json.sno+'</span>';
						result = result + '<div class="clearfix"></div>';
						result = result + ' </div>';	
					}

					if (json.make != null && json.make != '' && json.make != 'undefined') {
						result = result + '<div class="require_wrap_details_cta"> <b><strong>Make:</strong></b> <span class = "wordWrap-breakword">'+json.make+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + '</div>';
					}
					
					if (json.model != null && json.model != '' && json.model != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Model:</strong></b> <span class = "wordWrap-breakword">'+json.model+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + ' </div>';
					} 
					
					
					if (json.fileName != null && json.fileName != '' && json.fileName != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Attched File:</strong></b> <span class = "wordWrap-breakword">'+json.fileName+'</span><a download class="glyphicon glyphicon-download-alt mr10" target="_blank" href=\"'+json.fileUrl+'\" download title="Download"></a>';
		           		result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+json.fileUrl+'\',\''+json.fileName+'\',\''+json.fileContentType+'\');"title="preview"></a>';		           						          
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
					} 
					
					if(json.monitor != null && json.monitor != '' && json.monitor != 'undefined'){
					result = result + ' <div class="require_wrap_details_cta"> <b><strong>Monitor:</strong></b> <span class = "wordWrap-breakword">'+json.monitor+'</span>';
					result = result + ' <div class="clearfix"></div>';
					result = result + '</div>';
					}
					
					if(json.workingStatus != null && json.workingStatus != '' && json.workingStatus != 'undefined'){
						result = result + '<div class="require_wrap_details_cta"> <b><strong>Working Status:</strong></b> <span class = "wordWrap-breakword">'+json.workingStatus+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					
					
					//this block not visible to others category assets
					if (json.categoryName != null && json.categoryName != '' && json.categoryName != 'undefined' && (json.categoryId == 1 || json.categoryId ==2)) {
						if(json.memory != null && json.memory != '' && json.memory != 'undefined'){
							result = result + '<div class="require_wrap_details_cta"> <b><strong>Memory:</strong></b> <span class = "wordWrap-breakword">'+json.memory+'</span>';
							result = result + ' <div class="clearfix"></div>';
							result = result + '</div>';
						}
						
						
						if(json.cpu != null && json.cpu != '' && json.cpu != 'undefined'){
							result = result + '<div class="require_wrap_details_cta"> <b><strong>CPU:</strong></b> <span class = "wordWrap-breakword">'+json.cpu+'</span>';
							result = result + ' <div class="clearfix"></div>';
							result = result + ' </div>';
						}
						if(json.harddisk != null && json.harddisk != '' && json.harddisk != 'undefined'){
							result = result + ' <div class="require_wrap_details_cta"> <b><strong>Hard Disk:</strong></b> <span class = "wordWrap-breakword">'+json.harddisk+'</span>';
							result = result + ' <div class="clearfix"></div>';
							result = result + ' </div>';
						}
						if(json.macAddress != null && json.macAddress != '' && json.macAddress != 'undefined'){
							result = result + ' <div class="require_wrap_details_cta"> <b><strong>MAC Address:</strong></b> <span class = "wordWrap-breakword">'+json.macAddress+'</span>';
							result = result + ' <div class="clearfix"></div>';
							result = result + ' </div>';
						}
						
						if (json.monitor != null && json.monitor != '' && json.monitor != 'undefined') {
							result = result + ' <div class="require_wrap_details_cta"> <b><strong>Monitor:</strong></b> <span class = "wordWrap-breakword">'+json.monitor+'</span>';
							result = result + ' <div class="clearfix"></div>';
							result = result + ' </div>';
						}
						
					}
					
					//Adding By Laxman Start
					if (json.operatingSystem != null && json.operatingSystem != '' && json.operatingSystem != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Operating System:</strong></b> <span class = "wordWrap-breakword">'+json.operatingSystem+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					
					
					if (json.osKey != null && json.osKey != '' && json.osKey != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>OS Key:</strong></b> <span class = "wordWrap-breakword">'+json.osKey+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					
					if (json.antiVirus != null && json.antiVirus != '' && json.antiVirus != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Anti-Virus:</strong></b> <span class = "wordWrap-breakword">'+json.antiVirus+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					
					'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_ASSET_ADDITIONAL_DETAILS\')">';
					if (json.issueDate != null && json.issueDate != '' && json.issueDate != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Manufactured Date:</strong></b> <span>'+json.issueDate+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					
					if (json.purchaseDate != null && json.purchaseDate != '' && json.purchaseDate != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Purchase Date:</strong></b> <span>'+json.purchaseDate+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					
					if (json.cost != null && json.cost != '' && json.cost != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Cost:</strong></b> <span>'+json.cost+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					
					if (json.depreciationDate != null && json.depreciationDate != '' && json.depreciationDate != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Depreciation Assessment Date:</strong></b> <span>'+json.depreciationDate+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					if (json.depreciationAmount != null && json.depreciationAmount != '' && json.depreciationAmount != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Depreciation Amount:</strong></b> <span>'+json.depreciationAmount+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					
					
					if (json.vendorDetails != null && json.vendorDetails != '' && json.vendorDetails != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Vendor Details:</strong></b> <span>'+json.vendorDetails+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + ' </div>';
					}
					'</sec:authorize>';
					
					//End
						if (json.description != null && json.description != '' && json.description != 'undefined') {
							result = result + ' <div class="require_wrap_details_cta width100percnt description80"> <b><strong>Description:</strong></b> <span class = "wordWrap-breakword">'+json.description+'</span>';
							result = result + ' <div class="clearfix"></div>';
							result = result + ' </div>';
						}
						
						result = result + ' </div>';
					    result = result + '<div class="require_one clearfix">';
						
						if(json.assetAssignList.length > 0){
						result = result + ' <div class="row mt">';
						result = result + ' <div class="col-md-12 styleTables">';
						result = result + ' <h4>Assign asset details</h4>';
						result = result + ' <table class="table table-bordered">';
						result = result + ' <thead>';
						result = result + ' <tr>';
						result = result + ' <th>Shift Name</th>';
						result = result + ' <th>Employee Name</th>';
						result = result + ' <th>Document</th>';
						result = result + ' <th>Assigned On</th>';
						result = result + ' <th>Returned On</th>';
						result = result + ' </tr>';
						result = result + ' </thead>';
						result = result + ' <tbody>';
						
						for(var i=0;i<json.assetAssignList.length;i++){
							var report = json.assetAssignList[i];
							result = result + ' <tr>';
							result = result + ' <td><div class="tbldata-dotdot" title="'+report.shift.shiftName+'">'+report.shift.shiftName+'</div></td>';
							result = result + ' <td><div class="tbldata-dotdot" title="'+report.userRole.user.fullName+'">'+report.userRole.user.fullName+'</div></td>';
							if(report.fileName != null && report.fileName != "" && report.fileName != "undefined"){
								result = result + ' <td><div class="pull-left"><div class="tbldata-dotdot">'+report.fileName+'</div></div><ul class="ml list-unstyled list-inline pull-left" ><li><a download class="fa fa-download paddingRight cursorPointer" href="'+report.fileURL+'"></a><a class="fa fa-eye paddingLeft cursorPointer" onClick="viewFile(\''+report.fileURL+'\',\''+report.fileName+'\',\''+report.fileType+'\');"></a></li></ul></td>';
							}else{
								result = result + ' <td><div class="pull-left">--</div></td>';
							}
							
							if(report.assignedOnStr != null && report.assignedOnStr !="" && report.assignedOnStr != "undefined"){
		   	   		    		result = result +'<td><div class="tbldata-dotdot_80" title="'+report.assignedOnStr+'">'+report.assignedOnStr+'</div></td>';
		   		    		}else{
		   	   		    		result = result +'<td><div class="tbldata-dotdot_80">--</div></td>';
		   		    		}
		   		    		if(report.returnedOnStr != null && report.returnedOnStr !="" && report.returnedOnStr != "undefined"){
		   	   		    		result = result +'<td><div class="tbldata-dotdot_80" title="'+report.returnedOnStr+'">'+report.returnedOnStr+'</div></td>';
		   		    		}else{
		   	   		    		result = result +'<td><div class="tbldata-dotdot_80">--</div></td>';
		   		    		}
							
							
							result = result + ' </tr>';
						}
					
						result = result + ' </tbody>';
						result = result + ' </table>';
						
						result = result + '</div>';
						result = result + '</div>';
						}
						
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
					
					result = result + '  <div class="clearfix"></div>';
					result = result + '<div class="user_summary_edit client_summary_edit" id="editUserDiv" ></div>';
					result = result + '  <div class="clearfix"></div>';
					result = result + '  </div>';
					$("#tab-1").empty();
					$("#tab-2").empty();
					$("#tab-3").empty();
					$("#tab-4").empty();
					$("#tab-1").append(result);
					$("#tab-1").show();
			}
		});
}
</script>	
<script>
function uploadFile(){
	fileUpload("attachment", "assetDocuments", "addAsset_form","attachmentUpload");
	$('#uploadFileAttachment').val($('#attachment').val().replace(/C:\\fakepath\\/i, ''))
}
</script>
<script>
function uploadFileAttachment(){
	fileUpload("fileAttachment", "assetDocuments", "addAsset_form","attachmentUpload");
	$('#dbUploadFileAttachment').val($('#fileAttachment').val().replace(/C:\\fakepath\\/i, ''))
}

</script>
<script>
function deleteDocument(assetId,url){
	document.getElementById("normalUploadFile").style.display="inline-table";
	document.getElementById("dbUploadFile").style.display="none";
	 $.ajax({
   	  type: "POST",
      url: atsUrl+"/deleteAssetDocument?"+tokenParamName+"="+tokenValue+"&assetId="+assetId+"&url="+url, 	   
 	  success: function(data) {  },
	   });	
	}
</script>
<div class="comMask"></div>
  		<div class="modal assetManagementPopup" id="assetManagementPopup" style="display: none;">
 		<jsp:include page="addAssetPopup.jsp"></jsp:include> 
 	</div>
  <script>
  function summaryEditAssetAssign(assetUUID){
	  $.ajax({
	    	type: "POST",
	    	url: atsUrl+"/addAndEditAssetAssign?&assetUUID="+assetUUID, 
	        success: function(result) {
	        	$(".assetManagementPopup, .comMask").fadeIn();
	        	$('#assetManagementPopup').empty();
	        	$('#assetManagementPopup').append(result);
// 	        	issueDateScript();
	        	populateDropdownValues();
	        	<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
	  		 	 var sectionId = '${section.id}';
	  		  	 getCustomDependentField(sectionId);
	  		 	</c:forEach>
		        
	        },
	    }); 
  }
  </script>
  <script>
function closeassetManagementPopup() {
	$(".assetManagementPopup, .comMask").fadeOut();
	document.getElementById("addAsset_form").reset();
	$(".selectpicker").selectpicker('refresh');
}
function setFilePath(shiftIndex){
	$('#shiftFile'+shiftIndex).val($('#uploadBtn-dskA'+shiftIndex).val().replace(/C:\\fakepath\\/i, ''));
}
function uploadShiftFile(shiftIndex){
	fileUpload("uploadBtn-dskA"+shiftIndex, "assetDocuments", "addAsset_form","assignAsset["+shiftIndex+"].fileInfo","","");
}
</script>
<script type="text/javascript">
  function storeAsset(assetUUID) {
			var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
			   };  
			   if ($('#addAsset_form').validate(false, validationSettings)){
        	        var form = $('#addAsset_form')[0];
	           		var formData = new FormData(form);
	           		
	          		 $("#add_asset").attr("disabled", "disabled");
	          		 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
	          	      $.ajax({
	     		    	type: "POST",
	     		    	data: formData,
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
	     		    	 url: atsUrl+"/addCategoryAssets?"+tokenParamName+"="+tokenValue+"", 
	     		        success: function(result) {
	     		        	
	     		       	if (result && result.response === "INVALID_DATA") {
						    showError(result.responseMessage);
						    $("#loading").hide();
						} else if (result.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (result.response === "SUCCESS") {
							assetDetails(assetUUID);
							closeassetManagementPopup();
						} else if (result.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
	     			        
	     		        },
	     		    }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
         }
         
         $('body').on('blur', '#addAsset_form', function() {
     		$("#addAsset_form").showHelpOnFocus();
     		$("#addAsset_form").validateOnBlur(false, validationSettings);
     	});
</script> 

<script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
function clearDate(){
$('#issueDate').val('');	
}
</script>
 	