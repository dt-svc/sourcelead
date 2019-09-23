<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- these files for autocomplete with tagit -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- 
     @author : prasadK.
     this script is for display candidate education tab data.
     1.usage     : used at clientSideBar().
--%>

<div id="rejectedReasonDiv"></div>

<script>
function candidateDocuments(candidateId,candType){
	
	$("#candidateProfileTab").removeClass("current");
	$("#candidateDocumentsTab").addClass("current");
	$(".project_tabs_inn").css("display", "block");
	
	var candidateType = '${candidateType}';
	var result = "";
	
	if(window.candidateStatusGlobal == '0'){
		result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt25">  ';
	}else{
		result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt10">  ';
	}
	result  = result + '<div class="success_pop" id="selector9" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="docMsgDiv"></span>';
    result  = result + '</div>';
	if(window.candidateStatusGlobal == '0'){
		if(candidateType =="Candidates"){
			'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_CANDIDATE_DOCUMENT\',\'ROLE_CROWDSOURCING_CANDIDATES\')">'; 
			result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
			'</sec:authorize>';
		}else{
			'<sec:authorize access="hasRole(\'ROLE_CREATE_ASSOCIATE_DOCUMENT\')">'; 
			result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
			'</sec:authorize>';
		}
		
	}else{
		var docUUID = '';
		if(candidateType =="Candidates"){
			'<sec:authorize access="hasAnyRole(\'ROLE_CREATE_CANDIDATE_DOCUMENT\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
			result = result  +'<span class="" style="display : none" id="rejectDoc"><a href="javascript:void(0)"  class="addUser-org"><i class="glyphicon glyphicon-remove text-danger" onClick="rejectRequestedDocs('+candidateId+',\''+candType+'\');" title="Reject"></i></a></span>';
			result = result  +'<span class="" style="display : none" id="acceptDoc"><a href="javascript:void(0)"  class="addUser-org"><i class="glyphicon glyphicon-ok text-success" onClick="acceptRequestedDocs('+candidateId+',\''+candType+'\');" title="Accept"></i></a></span>';
			result = result  +'<span class=""><a href="javascript:void(0)"  onClick="requestDoc();createDocsType('+candidateId+',\''+candType+'\');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Request Documents</a></span>';
			result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onClick="addDocument('+candidateId+',\''+docUUID+'\',\''+candType+'\');refreshDocList('+candidateId+',\''+candType+'\');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Document</a></span>';
		'</sec:authorize>';
		}else{
		 '<sec:authorize access="hasRole(\'ROLE_CREATE_ASSOCIATE_DOCUMENT\')">';
		    result = result  +'<span class="" style="display : none" id="rejectDoc"><a href="javascript:void(0)"  class="addUser-org"><i class="glyphicon glyphicon-remove text-danger" onClick="rejectRequestedDocs('+candidateId+',\''+candType+'\');" title="Reject"></i></a></span>';
			result = result  +'<span class="" style="display : none" id="acceptDoc"><a href="javascript:void(0)"  class="addUser-org"><i class="glyphicon glyphicon-ok text-success" onClick="acceptRequestedDocs('+candidateId+',\''+candType+'\');" title="Accept"></i></a></span>';
			result = result  +'<span class=""><a href="javascript:void(0)"  onClick="requestDoc();createDocsType('+candidateId+',\''+candType+'\');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Request Documents</a></span>';
			result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onClick="addDocument('+candidateId+',\''+docUUID+'\',\''+candType+'\');refreshDocList('+candidateId+',\''+candType+'\');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Document</a></span>';
		'</sec:authorize>';
		
		'<sec:authorize access="!hasRole(\'ROLE_CREATE_ASSOCIATE_DOCUMENT\')">';
		result = result  +'<span class="pad-sea"><a href="javascript:void(0)"   class="addUser-org" ></a></span>';
	'</sec:authorize>';
		}
	
	}
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		
		result  = result + '   <div id="candidateDocumentsList" class="datatable_filter"></div>';
		result  = result + '   </div>';
	    result = result+'	    </div>';
	  

	$("#tab-1").empty();
	$("#tab-2").empty();
	$("#tab-3").empty();
	$("#tab-4").empty();
	$("#tab-5").empty();
	$("#tab-6").empty();
	$("#tab-7").empty();
	$("#tab-8").empty();
    $("#tab-9").empty();
    $("#tab-9").append(result); 
    candDocumentsList(candidateId,candType);
}
</script>


<script>
function candDocumentsList(candidateId,candType){
	var candidateType = '${candidateType}';
	 $.ajax({  
          type : "POST",
          url: atsUrl+"/getCandidateDocumentsListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&candidateType="+candType,        
          success :function(response) {
        	  if (response && response.response === "INVALID_USER") {
        		    showError(response.responseMessage);
        		} else if (response.response === "AWKWARD") {
        		    location.href = "/errorPage";
        		} else if (response.response === "SUCCESS") {
        			$("#candidateDocumentsList").empty();
              		var result = "";
              		result = result +"<table id='candidateDocumentsPagination' class='display' cellspacing='0' width='100%' >"; 
                  	result = result +"<thead>"; 
                  	result = result +"  <tr>"; 
                  	result = result  + '<th><input type="checkbox" id="checkAllDocuments" onclick="toggleAllDocuments(this);deleteRequestedDoc();"></th>';
                  	result  = result + '<th>Document Type</th>';
               		result  = result + '<th>Document</th>';
               		result  = result + '<th>Description</th>';
               		result  = result + '<th>Upload Status</th>';
                  	result  = result + '<th>Document Status</th>';
               		result  = result + '<th>Action</th>';
                  	result = result +"  </tr>"; 
                  	result = result +"</thead>";
                  	result = result +"<tbody>"; 
                   
                  	for (var i = 0; i < response.data.length; i++) { 
                  		var opt = response.data[i];  
                  		if(opt.isConfidential == 'N' || opt.docUploadStatus =="Requested"|| (opt.isConfidential == 'Y' && '${SourceLeadWeb_userCode}' == opt.userIdStr)) {
                      		result  = result + ' <tr>';
                      	if(opt.requestDocumentId != null && opt.requestedDocStatus == "Submitted"){
                      		result = result +'<td><input id="'+opt.id+'" value="'+opt.id+'"  onclick="deleteRequestedDoc();" type="checkbox" name="documents[]" class="myCheckbox" >';
                      	} else {
                      		result = result +"  <td><div>--</div></td>"; 
                      	}
                      	 
                      	if(opt.documentType != null && opt.documentType != '' && opt.documentType != "undefined") {
      	            		if(opt.documentOtherType !=null && opt.documentOtherType !='' && opt.documentOtherType !="undefined") {
      	                   		result = result +"  <td><div title='"+opt.documentOtherType+"' class='tbldata-dotdot-me'>"+opt.documentOtherType+"</div></td>";
    	    	            } else {
    	    	            	result = result +"  <td><div title='"+opt.documentType+"'>"+opt.documentType+"</div></td>"; 
    	    	            }
      	             	 } else {
      	             		 result = result +"  <td><div>----</div></td>"; 
      	             	 }
                      	 
                      	if(opt.fileName != null && opt.fileName != '' && opt.fileName != 'undefined') {
    						result = result+'<td><div><span title ="'+opt.fileName+'">'+opt.fileName+'</span><a class="fa fa-eye" onClick="viewFile(\''+opt.fileURL+'\',\''+opt.fileName+'\',\''+opt.fileContentType+'\');"></a><a download class="glyphicon glyphicon-download-alt" target="_blank" href=\"'+opt.fileURL+'\" download title="Download"></a></div></td>';
                      	} else {
                      		result  = result + ' <td><div><span>----</span></div></td>';
                      	}
                      	 
    					if(opt.description != null && opt.description != '' && opt.description != 'undefined') {
    	                	result  = result + ' <td><div><span title="'+opt.description+'">'+opt.description+'</span></div></td>';
    					} else {
    	                	result  = result + ' <td><div><span>----</span></div></td>';
    					}
                      	 
    					result = result +"  <td><div title='"+opt.docUploadStatus+"'>"+opt.docUploadStatus+"</div></td>"; 
                      	if(opt.requestedDocStatus != null && opt.requestedDocStatus != '' && opt.requestedDocStatus != 'undefined') {
                      		result = result +"<td>";
                      		result = result +"<div title='"+opt.requestedDocStatus+"'>"+opt.requestedDocStatus+" "; 
                      		if(opt.requestedDocStatus == "Rejected" && opt.requestedDocRejectedReason != null && opt.requestedDocRejectedReason != '' && opt.requestedDocRejectedReason != 'undefined') {
    				        	result = result + '<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>'+"Reason : "+opt.requestedDocRejectedReason+'</p></div></i>';
    				        }
                      		result = result +"</div>";
                      		result = result +"</td>";
                      	 } else {
                      		result = result +"  <td><div>----</div></td>"; 
                      	 }

                      	result = result+'<td><div class="tblicons">';
    		           	if(window.candidateStatusGlobal == 0) {
    		           		result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
    			           	result = result+'<i class="glyphicon glyphicon-trash" href="javascript:void(0)"></i>';
    		           	} else {
    		           		var docUUID = opt.documentUUID;
    		           		result = result+' <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addDocument('+candidateId+',\''+docUUID+'\',\''+candType+'\'); getDocument('+candidateId+', '+opt.id+',\''+candType+'\');" title="Edit"></a>';
    		           		if(candidateType =="Candidates"){
    		           			'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CANDIDATE_DOCUMENT\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
    		           				result = result+'<i class="glyphicon glyphicon-trash" onclick="removeDocument('+candidateId+','+opt.id+',\''+candType+'\');" title= "Remove"></i>';
    		           			'</sec:authorize>';
    		           		} else {
    		           			'<sec:authorize access="hasRole(\'ROLE_DELETE_ASSOCIATE_DOCUMENT\')">';
    		           				result = result+'<i class="glyphicon glyphicon-trash" onclick="removeDocument('+candidateId+','+opt.id+',\''+candType+'\');" title= "Remove"></i>';
    		           			'</sec:authorize>';
    		           		}
    		           	}
    		           	result = result+'</div></td>';
                      	result  = result + '</tr>';
                  	 }
                   } 
                   result = result +" </tbody>";
                   result = result +"</table>";
                   $("#candidateDocumentsList").append(result); 
                   documentsPagination();
        		} else if (response.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
          }
	});     
}
</script>

<script>
function candidateDocumentView(documentUUID,title) {
	if(documentUUID != null && documentUUID != "") {
		window.open(atsUrl+'/viewProfessionalCandidateDocument/'+title+'?documentUUID='+documentUUID,title,"height=600,width=600");
	}
}

</script>

<script>
	function toggleAllDocuments(source) {
		 var checkboxes = document.querySelectorAll('input[class="myCheckbox"]');
	    for (var i = 0; i < checkboxes.length; i++) {
	        if (checkboxes[i] != source)
	            checkboxes[i].checked = source.checked;
	        
	    }
		 $('input[class="myCheckbox"]').each(function(i) {
	    if(!this.checked) {
	        $('#checkAllDocuments').removeAttr('checked');
	    }
	});
	}
</script>


<script>
function deleteRequestedDoc(){
	
	 var documentsIds = [];
	 $("input[name='documents[]']:checked").each( function (i) {
		 documentsIds[i] = $(this).val();
	});
	 
	 if(documentsIds.length > 0){
		 document.getElementById("rejectDoc").style.display="";
		 document.getElementById("acceptDoc").style.display="";
	 }else{
		 document.getElementById("rejectDoc").style.display="none";
		 document.getElementById("acceptDoc").style.display="none";
	 }
	 
}
</script> 

<script>
 function rejectRequestedDocs(candidateId,candType){
	
	 var result = "";
		result = result + '	<div class="settPop">';
		result = result + '	<div id="docsrejectedLoading"></div>';
		result = result + '<form id="docrejectedReason_form" name="docrejectedReason_form" method="post">';
		result = result + '	<h3>Documents Rejected Reason<i class="fa fa-times" onclick="cancelDocumentRejectedReason();"></i></h3>  ';
		result = result + '	<div class="settPopInn"> ';
		
		result = result + '		<div class="clearfix">  ';
		result = result + '      <input type="hidden" name="rejectedDocsCandidateId"  id="rejectedDocsCandidateId" value='+candidateId+' />';
		result = result + '      <input type="hidden" name="rejectedDocsCandidateType"  id="rejectedDocsCandidateType" value='+candType+' />';
		result = result + '		  <label class="col-md-12" for="Rejected Reason">Reason</label> ';
		result = result + '		  <div class="col-md-12 taskdesp mt10">   ';
		result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
		result = result + '			<span class="hidden"></span> </div>  ';
		result = result + '		</div>';
		result = result + '		<div class="col-md-12 mt5 clearfix"> ';
		result = result + '		<div class="pull-right">  ';
		result = result + '		  <input type="submit" value="Save" id="submit_rejectedReason"  class="btn btn-primary" >';
		result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelDocumentRejectedReason();">';
		result = result + '		</div>';
		result = result + '		</div>';
		result = result + '		<div class="clearfix"></div>   ';
		result = result + '	</div>  ';
		result = result + '</form>';
		result = result + '	</div>';
	    $("#rejectedReasonDiv").empty();
	    $("#rejectedReasonDiv").append(result);
	    
	    $(".settPop").slideDown(200);
		$(".vendor_wrap_mask").fadeIn(200);
	    
	    
 }
 </script> 
 
 <script>
 function acceptRequestedDocs(candidateId,candType){
	 var documentsIds = [];
 	 $("input[name='documents[]']:checked").each( function (i) {
 		 documentsIds[i] = $(this).val();
 	});
 	var userId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	   $.ajax({
	    	type: "POST",
	    	url: atsUrl+"/acceptRequestedDocuments?"+tokenParamName+"="+tokenValue+"&documentsIds="+documentsIds+"&userId="+userId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&candidateType="+candType,
	        success: function(response) {
	        	if (response && response.response === "INVALID_DATA") {
	        	    showError(response.responseMessage);
	        	} else if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		candidateDocuments(candidateId,candType);
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        },
	    });
 }
 </script>
 
 
<script>
function cancelDocumentRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>  

<!-- Show success message after form submission script -->
<script>
	function docMsgFadein() {
		$("#selector9").fadeIn(200);
        $('#selector9').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->


<%-- 
     @author : prasadK.
     this script is for get candidateDocumentsList.
     1.usage     : used in candidateDocument().
     2.parameter : csrf token,candidateId .
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
function candidateDocumentsList(candidateId){
	 var sourceleadUrl ='${sourceleadUrl}';
	
}
</script>

<%-- 
     @author : Prasad K.
     this script is for prepare pagination.
     1.usage     : used in candidateDocumentsList().
--%>

<script>
function documentsPagination(){  
    $('#candidateDocumentsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>


<%-- 
     @author : Prasad K.
     this script is for remove popUp.
     1.usage   : used in addDocument() function.
--%>

<script>
function closePopup1(){
	 $("#popUpDiv").removeClass("edit-details-active");
	 $(".location-alert").fadeOut();
}
</script>


<div class="requestDocSideMask"></div>
<div class="modal  requestDocuments">
  <div class="modal-dialog modal-lg"> 
    <div id="documentLoading"></div>
    <div id="documentsTypeDiv"></div>
    </div>
  </div>
  
  <div id="divDocsTd1" style="display: none; float: left;">
  <div class="dropdown-select paddingRight">
              <select class="width100percnt form-control selectborder" data-validation="required" onchange="getSelectedDocumentType(REPLACEROWID);" field-name="Document Type" id="REPLACEROWIDdocType" name="requestDocumentList[REPLACEROWID].docType">
                <option selected value="">--Document Type--</option>
                <c:forEach var="doctype" items="${documentsList}">
					<option value="${doctype.name}" >${doctype.name}</option>
    			</c:forEach> 
              </select>
  </div>
  
 <div class="clearfix mt5" id="REPLACEROWIDotherType" name="REPLACEROWIDotherType" style="display: none;">
	<input type="text" class="form-control input-sm" placeholder="Other Type" data-validation-optional="true" field-name="Other Type" name="requestDocumentList[REPLACEROWID].otherDocType" id="REPLACEROWIDotherDocType">
 </div>
 </div>
  
  <div id="divDocsTd2" style="display: none; float: left;">
  <div>
              <textarea rows="2" placeholder="Description" class="form-control" id="REPLACEROWIDdescription" name="requestDocumentList[REPLACEROWID].description"></textarea>
   </div>
  </div>
  
  <div id="divDocsTd3" style="display: none; float: left;">
  <div>
     <label class="switch mt10 ml10 pull-left">
       <input type="checkbox" checked id="REPLACEROWIDchecked" name="requestDocumentList[REPLACEROWID].checked" onclick="checkDocument(REPLACEROWID)" value="Y">
       <div class="slider round"></div>
       </label>
         <div class="pull-right">
       <button type="button" class="btn btm-sm pull-right mt5" onclick="removeDocType(REPLACEROWID)"><i class="fa fa-remove text-danger"></i></button>
             </div>
   </div>
</div>       


<%-- 
     @author : prasad K.
     this script is for shown popUp.
     1.usage   : used in candidateDocument() function.
--%>
<script>
function addDocument(candidateId, documentUUID, candType) {
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'<form role="form"  id="documentType_form" name="documentType_form" method="post" > ';
	result = result  +'<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	result = result  +'<div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	
	result = result  +'<div class="clearfix mb10 col-md-12"> ';
	result = result  +'	<select label=" " class="chosen-select form-control" field-name="Document Type" data-placeholder = "Select Document Type" name="name" id="docName" data-validation="required" onchange="getDocumentType(this);">';
					   
	
	                   <c:forEach var="doctype" items="${documentsList}">
    result = result +'<option value="" disabled selected></option>';	
	result = result + '<option value="${doctype.name}" >${doctype.name}</option>';
	                   </c:forEach>  
    result = result + '</select>';
	result = result  +' </div> ';
	result = result  +'';
	
	result = result + '<div class="col-md-12 documentTypeDiv mb10" id="documentTypeDiv" style="display:none">';
	result = result + '<input type="text" class="form-control input-sm" name="documentOtherType"  id="documentOtherType" value="" placeholder="Document Other Type" field-name="Document Other Type" data-validation="required validate_Space validate_length length1-250">';
	result = result + '</div>'; 
	
	result = result+'<div class="clearfix mb10" id="uploadDocDiv">';
	result = result+'<div class="col-md-12 validation_p">';
	result = result+'<div class="input-group" id="uploadFileGroup">';
	result = result+'<label class="input-group-btn" onchange="uploadFileDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'<input type="file"  id="uploadBtn" style="display: none;"  multiple="" class="upload">';
	result = result+'</span> </label>';
	result = result+'<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Document..." field-name="Document" data-validation="required validate_Space validate_docimgpdf">';
	result = result+'</div>';
	result = result+'</div>';  
	result = result+'</div>'; 
	
	result = result+'<div class="clearfix mb10" id="docNameDiv" style="display: none;">';
	result = result+'<div class="col-md-12 mt">';
	result = result+'<label id="docsName" name="docName" class="text-primary tbldata-dotdot-vsm pull-left"></label>&nbsp;&nbsp;<span id="delete_doc" onclick="removeDocNameDiv();"><i class="fa fa-trash removeIcon"></i></span>';
	result = result+'</div>';
	result = result+'</div>';
	
	result = result+'<div class="clearfix mb10 mt25">';
	result = result+'<div class="col-md-12">';
	result = result+'<textarea rows="2" placeholder="Description" value="" id="description" name="description" data-validation-optional="true" data-validation="validate_length length1-250"  field-name="Description" class="form-control"></textarea>';
	result = result+'</div>';
	result = result+'</div>';  
	
	result = result+'<div class="clearfix mr mt" id="docUpdateButton" style="display: none;">';
	result = result+'<button type="button" id="update_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
	result = result+'</div>';
	
	result = result  +'<div class="clearfix mr" id="docAddButton">';
    result = result +'<input type="button" value="Upload" name="btnAddPr" id="save_doc" class="btn btn-sm btn-primary pull-right" onClick ="storeCandidateDocument('+candidateId+', \''+documentUUID+'\',\''+candType+'\');" />'; 
	result = result  +'</div>';
	result = result  +'</div>';
	result = result  +'</form>';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
	$(".edit-details").toggleClass("edit-details-active");
	 $(".close-div").click(function(){ 
		 $(this).parent("div.edit-details").removeClass("edit-details-active");
		 });
	 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details").click(function(e){
		 e.stopPropagation();
	 });
	 
	 /* upload file script to show selected file name*/
	    document.getElementById("uploadBtn").onchange = function () {
	    	$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	   }; 
	    
	    /* upload file script for size validation 2MB*/
	   
	 <%-- Eductions and Skills Autocomplete Scripts Starts Here --%>
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
		<%-- Eductions and Skills Autocomplete Scripts Ends Here --%>
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
function getDocumentType(documentType){
	 var existingDocumentType = $('#docsName').val(); 
	if(documentType.value == 'Other'){
		 document.getElementById("documentTypeDiv").style.display="block";
		  $("#documentOtherType").attr("data-validation-optional", "false");
		  $("#documentOtherType").attr("data-validation", "required validate_Space validate_length length2-250");
	}else{
		document.getElementById("documentTypeDiv").style.display="none";
		  $("#documentOtherType").attr("data-validation-optional", "true");
		  $("#documentOtherType").find('p.jquery_form_error_message').remove();
		  if(existingDocumentType ==null || existingDocumentType == "" || existingDocumentType == "undefined" || existingDocumentType != 'Other'){
			  $("#documentOtherType").val('');
		  }
	}
}
</script>

    
 <%-- 
     @author : prasad K
     this script is for saving document
     1.usage     : onclick
     2.parameter : candidateId.
     3.ajax url implementation in CandidateDocumentRestController.
--%>
<script type="text/javascript">
function storeCandidateDocument(candidateId, documentUUID, candType) {
	var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
	}; 
	if ($('#documentType_form').validate(false, validationSettings)) {
		if(document.getElementById("uploadBtn") !== null){
// 		if(document.getElementById("uploadBtn") !== null && (document.getElementById("uploadBtn").files.length > 0 || $("#docsName").html()!=="")) {
			var frm = $('#documentType_form')[0];
			var formData = new FormData(frm);
			$("#save_doc").attr("disabled", "disabled");
			$("#save_doc" ).addClass('button').val('Processing..');
			$.ajax({
				type : "POST",
		      	data: formData,
		  		processData: false,
		  	    contentType:false,
		      	url : atsUrl+"/saveCandidateDocument?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&documentUUID="+documentUUID+"&candidateType="+candType,
				success : function(result) {
					if (result && (result.response === "INVALID_DATA" || result.response === "INVALID_USER")) {
		    		    showError(result.responseMessage);
		    		} else if (result.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (result.response === "SUCCESS") {
		    			closePopup();
						$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
	        				if(result.object < 50) {
	        			   		$("#perVal").removeClass('progress-bar-success');
	        			   		$("#perVal").addClass("progress-bar-danger");
	        			 	}
	        			 	if(result.object >= 50) {
	        					$("#perVal").removeClass('progress-bar-danger');
	        				 	$("#perVal").addClass("progress-bar-success");
	        			 	}
	        			 	$("#disVal").empty();
	        				$("#disVal").append(result.object+'%');
	         			// for quick view bar update
	   	      			$('#quickPerVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
	       	 			if(result.object < 50) {
	         				$("#quickPerVal").removeClass('progress-bar-success');
	         			   	$("#quickPerVal").addClass("progress-bar-danger");
	         			}
	         			if(result.object >= 50) {
	         				$("#quickPerVal").removeClass('progress-bar-danger');
	         				$("#quickPerVal").addClass("progress-bar-success");
	         			}
	         			$("#quickDisVal").empty();
	         			$("#quickDisVal").append(result+'%');
						$("#popUpDiv").removeClass("edit-details-active");
	          			$("#selector9").removeAttr("style");
	          			$("#selector9").css({ display: "block" });
	          			if(documentUUID != null && documentUUID !="" && documentUUID !="undefined")
	          				$("#docMsgDiv").html("Document updated");
	           			else
	           				$("#docMsgDiv").html("Success!! New Document added");
	           			candDocumentsList(candidateId,candType);
	           			docMsgFadein();
	           			setTimeout(function(){
	      		 			candidateList();					 			
	      		 		},100);
		    		} else if (result.indexOf('Sign In') !== -1) {
						location.replace(sourceleadUrl);
					} else {
					    showError("Operation failed, please try again");
					}
				},
			}); 
			return true;
		} else {
			showError("Document file is missing");
		}
	}
	return false;
}
$('body').on('blur', '#documentType_form', function() {
	$("#documentType_form").showHelpOnFocus();
	$("#documentType_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
function uploadFileDoc() {
	fileUpload("uploadBtn", "candidateDocuments", "documentType_form");
}
</script>

<script>
function refreshDocList(candidateId,candType){
	candDocumentsList(candidateId,candType);
}
</script>

<%-- 
     @author : Prasad K
     this script is for get document details in edit mode
     1.usage     : onclick
     2.parameter : candidateId, documentId
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script>
function getDocument(candidateId, documentId,candType){
	 $("#docsName").attr("data-validation-optional", "true");
	  $("#documentOtherType").attr("data-validation-optional", "true");
		$.ajax({
		   	type: "POST",
		    url: atsUrl+"/getDocument?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&documentId="+documentId, 	   
   		    success: function(response) {
   		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")){
   		    	    showError(response.responseMessage);
   		    	} else if (response.response === "AWKWARD") {
   		    	    location.href = "/errorPage";
   		    	} else if (response.response === "SUCCESS") {
   		    		var data = response.object;
   		    		$("#uploadDocDiv").hide();
       			 	$("#docAddButton").hide();
       			 	$("#uploadFile").removeAttr("data-validation");
       			 	if(data.fileName != null && data.fileName !="undefined" && data.fileName != "") {
       			 		$("#docNameDiv").show();
       			 	}
       			 	$("#docsName").append(data.fileName);
       			 	$("#docsName").prop('title', data.fileName);
       			 	$("#docUpdateButton").show();
       			 	if(data !=null) {
       			 		if(data.documentType != null && data.documentType != "undefined" && data.documentType != "") {
	                    	$("select#docName").val(data.documentType);
	                     	$("select#docName").trigger("chosen:updated");
	                   
	                    //here documentType is Other then documentOtherType  also display in Edit.
	                   
	                     	if(data.documentType == 'Other') {
		        				if(data.documentOtherType !=null && data.documentOtherType !='' && data.documentOtherType !="undefined") {
		        					document.getElementById("documentTypeDiv").style.display="block";
		        					$("#documentOtherType").val(data.documentOtherType);
			    	            }
	        			 	}
       			 		}
       			 		
       			 	if(candType == 'candidateGlobal'){
	       				 $("#update_doc").attr("onclick", 'storeCandidateDocument('+data.candidateGlobalId.id+',\''+data.documentUUID+'\',\''+candType+'\');');
	       			 }else{
	       			     $("#update_doc").attr("onclick", 'storeCandidateDocument('+data.candidateId.id+',\''+data.documentUUID+'\',\''+candType+'\');');
	       			 }
       			 		
       			 
	    	   			if(data.description != null && data.description != "undefined" && data.description != ""){
	       					 $("textarea#description").val(data.description);
	       				}
       			 	}
   		    	} else if (response.indexOf('Sign In') !== -1) {
   		    		location.replace(sourceleadUrl);
   		    	} else {
   		    	    showError("Operation failed, please try again");
   		    	}
   		    }
	});
}
</script>

<script type="text/javascript">
function deleteDocumentPopup() {
	$("#remove_user_pop_documents, .project_inn_mask").fadeOut();
}
</script>

<div id="deleteDocumentPopUp"></div> 

<script type="text/javascript">
function removeDocument(candidateId, documentId, candType) {
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop_documents' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You are choosing to delete the Documents</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="deleteDocument('+candidateId+','+documentId+',\''+candType+'\');"><input class="u_can_color" type="button"  value="Cancel" onClick="deleteDocumentPopup();"></b>';
	result = result +"	</div>";
	$("#deleteDocumentPopUp").empty();
	$("#deleteDocumentPopUp").append(result);
	$("#remove_user_pop_documents").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>
<%-- 
     @author : prasad K
     this script is for terminate Doc (inactive status)
     1.usage     : onclick
     2.parameter : candidateId, documentId
     3.ajax url implementation in CandidateDocumentRestController.
--%>
<script>
function deleteDocument(candidateId, documentId, candType) {
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	//var message = confirm("You are choosing to delete the document");
	$.ajax({
	   	type: "POST",
	    url: atsUrl+"/terminateDoc?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&documentId="+documentId+"&candidateType="+candType, 	   
  		success: function(result) {
  			if (result && result.response === "INVALID_DATA") {
			    showError(result.responseMessage);
			} else if (result.response === "AWKWARD") {
			    location.href = "/errorPage";
			} else if (result.response === "SUCCESS") {
				$('#perVal').attr('aria-valuenow', result.object).css('width',result.object+'%');
	   			if(result.object < 50){
	   				/* $("#perVal").removeClass("progress-bar-success"); */
	   				$("#perVal").find('progress-bar-success').remove();
	   				$("#perVal").addClass("progress-bar-danger");
	   			}
	   			if(result.object >= 50){
	   				$("#perVal").find('progress-bar-danger').remove();
	   				$("#perVal").addClass("progress-bar-success");
	   			}
	   			$("#disVal").empty();
	   			$("#disVal").append(result.object+'%');
	   			// for quick view bar update
	  			$('#quickPerVal').attr('aria-valuenow', result.object).css('width',result.object +'%');
	    		if(result.object < 50){
	     			$("#quickPerVal").removeClass('progress-bar-success');
	     			$("#quickPerVal").addClass("progress-bar-danger");
	     		}
	     		if(result.object >= 50){
	     			$("#quickPerVal").removeClass('progress-bar-danger');
	     			$("#quickPerVal").addClass("progress-bar-success");
	     		}
	  			$("#quickDisVal").empty();
	  			$("#quickDisVal").append(result.object+'%');
	  			$("#selector9").removeAttr("style");
   				$("#selector9").css({ display: "block" });
   				$("#docMsgDiv").html("Document deleted");
  				$("#popUpDiv").removeClass("edit-details-active");
  				$("#remove_user_pop_documents, .project_inn_mask").fadeOut();
  				candDocumentsList(candidateId,candType);
  				docMsgFadein();
  				setTimeout(function(){
					candidateList();					 			
	     		},100);	
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
			    showError("Operation failed, please try again");
			}
  		},
	});
}
</script>

<%-- 
@author : Prasad K
this script is for remove Document in edit mode to upload new document
1.usage     : onclick
--%>
<script>
function removeDocNameDiv(){
	$("#docNameDiv").hide();
	$("#uploadDocDiv").show();
	$("#uploadFile").attr("data-validation", "required validate_Space validate_docimgpdf");
}
</script>

<!-- Show success message after form submission script -->
<script>
	function docMsgFadein() {
		$("#selector9").fadeIn(200);
        $('#selector9').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->


<%-- 
     @author : Prasad K
     this script is for add document popup
     1.usage     : onload
--%>


<script>

//Request Documents
    function requestDoc(){
    $(".requestDocuments").fadeIn();
    $(".requestDocSideMask").fadeIn();
    }
    function closeRD(){
    $(".requestDocuments").fadeOut();
    $(".requestDocSideMask").fadeOut();
    }
</script>

<script>
function createDocsType(candidateId,candType){
	
	$("#documentsTypeDiv").empty();
	
	var result = "";
	result = result +'<form id="requestForDocuments_form" name="requestForDocuments_form" method="post">';
	result = result +'<div class="modal-content">';
	result = result +'<div class="modal-header">';
	result = result +'<button type="button" class="close" data-dismiss="modal" onClick="closeRD();">&times;</button>';
	result = result +'<h4 class="modal-title">';
	result = result +'Request Documents';
	result = result +'</h4>';
	result = result +'</div>';
	result = result +'<div class="modal-body">';
	result = result +'<div class="form-horizontal clearfix mb">';
	result = result +'<div class="clearfix"></div>';
        
        
	result = result +'<div class="clearfix mb10 mt5 requestdocmntTable">';
	result = result +'<input type="hidden" name="requestedCandidateId"  id="requestedCandidateId" value='+candidateId+' />';
	result = result +'<input type="hidden" name="candidateType"  id="requestedCandidateType" value='+candType+' />';
	result = result +'<input type="hidden" name="docuemntMaxRowNumber"  id="docuemntMaxRowNumber" value="1" />';
	result = result +'<table class="table table-responsive table-bordered table-hover" width="100%" id="requestedDocumentsTable" border="0">';
	result = result +'<tbody>';
	result = result +'<tr id="requestedDocumentsTsRow0">';
	result = result +'<div id="0requestedDocumentsFileGroup">';
	result = result +'<td width="200px">';
	result = result +'<div class="dropdown-select">';
	result = result +'<select class="width100percnt form-control selectborder" data-validation="required" field-name="Document Type" onchange="getSelectedDocumentType(0);" id="0docType" name="requestDocumentList[0].docType">';
	result = result +'<option selected value="">--Document Type--</option>';
	
    <c:forEach var="doctype" items="${documentsList}">
		result = result + '<option value="${doctype.name}" >${doctype.name}</option>';
    </c:forEach> 
    
	result = result +'</select>';
	result = result +'<div class="clearfix mt5" id="0otherType" name="0otherType" style="display: none;">';
	result = result +'<input type="text" class="form-control input-sm" placeholder="Other Type" data-validation-optional="true" field-name="Other Type" name="requestDocumentList[0].otherDocType" id="0otherDocType">';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</td>';
	result = result +'<td width="auto">';
	result = result +'<div class="">';
	result = result +'<textarea rows="2" placeholder="Description" class="form-control" id="description" name="requestDocumentList[0].description" field-name="Description" data-validation-optional="true" data-validation="validate_length length1-500"></textarea>';
	result = result +'</div>';
	result = result +'</td>';
	result = result +'<td width="150px">';
	result = result +'<div class="">';
	result = result +'<label class="switch mt10 ml10 pull-left">';
	result = result +'<input type="checkbox" checked id="0checked" name="requestDocumentList[0].checked" onclick="checkDocument(0)" value="Y">';
	result = result +'<div class="slider round"></div>';
	result = result +'</label>';
	result = result +'<div class="pull-right">';
	result = result +'<button type="button" class="btn btm-sm pull-right mt5" onclick="addDocumentRow();"><i class="fa fa-plus text-primary"></i></button>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</td>';
	result = result +'</div>';
	result = result +'</tr>';
	result = result +'</tbody>';
	result = result +'</table>';
	result = result +'</div>';
	result = result +'</div>';
	result = result +'</div>';
    
	result = result +'<div class="modal-footer clearfix">';
	result = result +'<button type="submit" id="submit_docs" class="btn btn-sm btn-primary mr10">Send</button>';
	result = result +'<button type="button" class="btn btn-sm btn-default" onclick="closeRD();">Cancel</button>';
	result = result +' </div>';
	result = result +'</div>';
	result = result + '</form>';
	
	$("#documentsTypeDiv").append(result);
	
}
</script>

<script>
function checkDocument(id){
	var checked = document.getElementById(id+"checked").checked
	if(checked == true){
		$("#"+id+"checked").val("Y");
	}else{
		$("#"+id+"checked").val("N");
	}
}
</script>

<script>
function getSelectedDocumentType(id){
	 var selectedDocType = document.getElementById(id+"docType").value; 
	if(selectedDocType == 'Other'){
		  document.getElementById(id+"otherType").style.display="block";
		  $("#"+id+"otherDocType").removeAttr("data-validation-optional");
		  $("#"+id+"otherDocType").attr("data-validation", "required validate_Space validate_length length2-250");
	}else{
		  document.getElementById(id+"otherType").style.display="none";
		  $("#"+id+"otherDocType").removeAttr("data-validation");
		  $("#"+id+"otherDocType").attr("data-validation-optional", "true");
		  $("#"+id+"otherType").find('p.jquery_form_error_message').remove();
		  if(selectedDocType ==null || selectedDocType == "" || selectedDocType == "undefined" || selectedDocType != 'Other'){
			  $("#"+id+"otherDocType").val('');
		  }
	}
}
</script>

<script type="text/javascript">
$('body').on('submit', '#requestForDocuments_form', function(e){
	var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	if ($('#requestForDocuments_form').validate(false, validationSettings)){
		e.preventDefault();
		var requestedCandidateId = document.getElementById("requestedCandidateId").value;
		var candType = document.getElementById("requestedCandidateType").value;
     /*  var form = $('#requestForDocuments_form')[0];
      var formData = new FormData(form); */
      	var formData = getFormData("requestForDocuments_form");
        $("#submit_docs").attr("disabled", "disabled");
		$("#submit_docs" ).addClass('button').val('Processing..');
		$("#documentLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({
			    type: "POST",
			    data : formData,
			    //processData: false,
		    	//  contentType:false,
			    url: atsUrl+"/requestCandidateForDocuments?"+tokenParamName+"="+tokenValue+"", 
			    success: function(response) {
			    	if (response.response === "AWKWARD") {
			    	    location.href = "/errorPage";
			    	} else if (response.response === "SUCCESS") {
			    		$("#documentLoading").removeAttr("style"); 
				        closeRD();
				        candidateDocuments(requestedCandidateId,candType);
			    	} else if (response.indexOf('Sign In') !== -1) {
			    		location.replace(sourceleadUrl);
			    	} else {
			    	    showError("Operation failed, please try again");
			    	}
				},
		    });   
      		return true; 
    	} else {
    		return false;	
       }
  });

	$('body').on('blur', '#requestForDocuments_form', function() {
		$("#requestForDocuments_form").showHelpOnFocus();
		$("#requestForDocuments_form").validateOnBlur(false, validationSettings);
	});
</script>

<script>
function addDocumentRow(){
	var checkMaxTsRowNum = $("#docuemntMaxRowNumber").val();
	var tbl = document.getElementById("requestedDocumentsTable");
	var maxRowNum = parseInt($("input[name = 'docuemntMaxRowNumber']").val());
	$("input[name = 'docuemntMaxRowNumber']").val(maxRowNum + 1);
	
	
	var row = tbl.insertRow(maxRowNum);
	row.setAttribute("id", "divRequestedDocumentsRow" + maxRowNum);
	
	
	var cell0 = row.insertCell(0);
	var divDocTypeTdStr = document.getElementById("divDocsTd1").innerHTML;
	divDocTypeTdStr = divDocTypeTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell0.innerHTML = divDocTypeTdStr;
	
	var cell1 = row.insertCell(1);
	var divDocDescTdStr = document.getElementById("divDocsTd2").innerHTML;
	divDocDescTdStr = divDocDescTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell1.innerHTML = divDocDescTdStr; 
	
	var cell2 = row.insertCell(2);
	var divDocDescTdStr = document.getElementById("divDocsTd3").innerHTML;
	divDocDescTdStr = divDocDescTdStr.replace(/REPLACEROWID/g, (maxRowNum));
	cell2.innerHTML = divDocDescTdStr; 
}
</script>

<script type="text/javascript">
	function removeDocType(rowId) {
		 document.getElementById("divRequestedDocumentsRow"+rowId).style.display = 'none';
		 document.getElementById("divRequestedDocumentsRow"+rowId).value= '';
		 var  rId = "divRequestedDocumentsRow"+rowId;
		  var r = document.getElementById("requestedDocumentsTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>
 
<script type="text/javascript">
$('body').on('submit', '#docrejectedReason_form', function(e) {
	  if ($(this).validate(false, validationSettings)) {
      	e.preventDefault();
     
     	var frm = $('#docrejectedReason_form').serializeFormJSON();
      	var con = JSON.stringify(frm);
      	con = con.replace(/[{}]/g, "");
      
      	//It will escape all the special characters
      	var jsonData = encodeURIComponent(con);
      	var rejectedDocsCandidateId = document.getElementById("rejectedDocsCandidateId").value;
      	var candType = document.getElementById("rejectedDocsCandidateType").value;
      	var documentsIds = [];
	 	$("input[name='documents[]']:checked").each( function (i) {
	 		documentsIds[i] = $(this).val();
	 	});
	 	 
       	$("#submit_rejectedReason").attr("disabled", "disabled");
       	$("#docsrejectedLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
  	   	$.ajax({
	    	type: "POST",
	    	url: atsUrl+"/rejectRequestedDocuments?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&documentsIds="+documentsIds+"&candidateType="+candType, 
	        success: function(response) {
	        	if (response && response.response === "INVALID_DATA") {
	        	    showError(response.responseMessage);
	        	} else if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		cancelDocumentRejectedReason();
		        	$("#docsrejectedLoading").removeAttr("style"); 
		        	candidateDocuments(rejectedDocsCandidateId,candType);
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        },
	    });    
      return true; 
    }
    else {
    	return false;	
	}
});

$('body').on('blur', '#docrejectedReason_form', function() {
	$("#docrejectedReason_form").showHelpOnFocus();
	$("#docrejectedReason_form").validateOnBlur(false, validationSettings);
});
</script>
 
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
});		
	
</script>  

<!-- <script>
	function clearUpload(){
		$("#uploadFile").val("");
	}
</script>
 -->