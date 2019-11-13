<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<!-- these files for autocomplete with tagit -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.addUser-org {
	overflow-x: auto;
}
</style>
<%-- 
     @author : prasadK.
     this script is for display candidate education tab data.
     1.usage     : used at clientSideBar().
--%>

<div id="rejectedReasonDiv"></div>
<!-- Show success message after form submission script -->
<script>
	function docMsgFadein() {
		$("#selector6").fadeIn(200);
        $('#selector6').delay(2000).fadeOut('slow');
	}
</script>
<!-- End -->
<script>
function professionalCandidateDocuments(candidateId){
	var candidateType = '${candidateType}';
	var result = "";
	
	if(window.candidateStatusGlobal == '0'){
		result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt25">  ';
	}else{
		result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt10">  ';
	}
	result  = result + '<div class="success_pop" id="selector6" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="docMsgDiv"></span>';
    result  = result + '</div>';
	if(window.candidateStatusGlobal == '0'){
		if(candidateType =="Candidates"){
			result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
		}else{
			result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
		}
		
	}else{
	    var docUUID = '';
		result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onClick="addDocument('+candidateId+',\''+docUUID+'\');refreshDocList('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Document</a></span>';
	}
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter">';
		
		result  = result + '   <div id="candidateDocumentsList" ></div>';
		result  = result + '   </div>';
	    result = result+'	    </div>';
	  

	$("#tab-1").empty();
	$("#tab-2").empty();
	$("#tab-3").empty();
	$("#tab-4").empty();
	$("#tab-5").empty();
	$("#tab-6").empty();
	
    $("#tab-6").append(result); 
    candDocumentsList(candidateId);
}
</script>


<script>
function candDocumentsList(candidateId){
	var candidateType = '${candidateType}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateDocumentsListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
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
                    result  = result + '<th>Document Type</th>';
                 	result  = result + '<th>Document</th>';
                 	result  = result + '<th>Description</th>';
                 	result  = result + '<th>Action</th>';
                    result = result +"  </tr>"; 
                    result = result +"</thead>";
                    result = result +"<tbody>"; 
                     
                    for (var i = 0; i < response.data.length; i++) {
                    	var opt = response.data[i]; 
                    	if(opt.isConfidential == 'N' || (opt.isConfidential == 'Y' && '${SourceLeadWeb_user.userCode}' == opt.userIdStr)) {
	                    	result  = result + ' <tr>';
							if(opt.documentType != null && opt.documentType != '' && opt.documentType != "undefined") {
	    	            		if(opt.documentOtherType !=null && opt.documentOtherType !='' && opt.documentOtherType !="undefined"){
	    	                   		result = result +"  <td><div title='"+opt.documentOtherType+"' class='tbldata-dotdot-me'>"+opt.documentOtherType+"</div></td>";
	    	             	 	} else {
	    	             			result = result +"  <td><div title='"+opt.documentType+"'>"+opt.documentType+"</div></td>"; 
	    	             	 	}
	    	             	} else {
	    	             		result = result +"  <td><div>----</div></td>"; 
	    	             	}
	                    	 
	                    	if(opt.fileName != null && opt.fileName != '' && opt.fileName != 'undefined') {
	                    		result  = result + ' <td><div> <span title="'+opt.fileName+'">'+opt.fileName+'</span><a class="fa fa-eye" onClick="viewFile(\''+opt.fileURL+'\',\''+opt.fileName+'\',\''+opt.fileContentType+'\');" title="View"></a><a download class="glyphicon glyphicon-download-alt" target="_blank" download href=\"'+opt.fileURL+'\" title="Download"></a></div></td>';
	                    	} else {
	                    		result  = result + ' <td><div><span>----</span></div></td>';
	                    	}
	                    	 
	                    	if(opt.description != null && opt.description != '' && opt.description != 'undefined') {
		                   		result  = result + ' <td><div><span title="'+opt.description+'">'+opt.description+'</span></div></td>';
		                    } else {
		                    	result  = result + ' <td><div><span>----</span></div></td>';
		                    }
		                    	
		                   	result = result+'<td><div class="tblicons">';
			           		if(window.candidateStatusGlobal == 0) {
			           			result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
				           		result = result+'<i class="glyphicon glyphicon-trash" href="javascript:void(0)"></i>';
			           		} else {
			           			var docUUID = opt.documentUUID;
			           			result = result+' <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addDocument('+candidateId+',\''+docUUID+'\'); getDocument('+candidateId+', '+opt.id+');" title="Edit"></a>';
			           			if(candidateType =="Candidates") {
				           			result = result+'<i class="glyphicon glyphicon-trash" onclick="removeDocument('+candidateId+','+opt.id+');" title= "Remove"></i>';
			           			} else {
				           			result = result+'<i class="glyphicon glyphicon-trash" onclick="removeDocument('+candidateId+','+opt.id+');" title= "Remove"></i>';
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
function viewProfessionalCandidateDocument(documentUUID,title){
	window.open(sourceleadUrl+'/viewProfessionalCandidateDocument/'+title+'?documentUUID='+documentUUID,title,"height=600,width=600");
}
</script>



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
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 3
			}],
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
function closePopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>

<%-- 
     @author : prasad K.
     this script is for shown popUp.
     1.usage   : used in candidateDocument() function.
--%>
<script>
function addDocument(candidateId, documentUUID) {
	
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'<form role="form"  id="documentType_form" name="documentType_form" method="post" > ';
	result = result  +'<i class="fa fa-close close-div"  onClick="closePopup();"></i> ';
	result = result  +'<div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	
	result = result  +'<div class="clearfix mb10 col-md-12" id="nameDocIdDiv"> ';
	
	result = result  +'	<select label=" " class="chosen-select form-control" field-name="Document Type" data-placeholder = "Select Document Type" name="name" id="docuName" data-validation="required" onchange="getDocumentType(this);">';
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
	result = result+'<div class="col-md-12 validation_p validationPara">';
	result = result+'<div class="input-group" id="uploadFileGroup">';
	result = result+'<label class="input-group-btn"   onchange="uploadFileDoc();removeValidationProfCanDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'<input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">';
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
	
	result = result + '<div class="clearfix mb10">';
	result = result + '<div class="clearfix checkbox">';
	result = result + '<label>';
	result = result	+ '<label> <input type="checkbox" id="isConfidential" value="Y" onClick ="" name="isConfidential">Is Confidential</label>';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result+'<div class="clearfix mr mt" id="docsUpdateButton" style="display: none;">';
	result = result+'<button type="button" id="updates_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
	result = result+'</div>';
	
	result = result  +'<div class="clearfix mr" id="docAddButton">';
    result = result +'<input type="button" value="Upload" name="btnAddPr" id="save_doc" class="btn btn-sm btn-primary pull-right" onClick ="storeCandidateDocument('+candidateId+', \''+documentUUID+'\');" />'; 
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
	    
	 <%-- Document Type Autocomplete Scripts Starts Here --%>
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
		<%-- Document Type Autocomplete Scripts Ends Here --%>
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
		  $("#nameDocIdDiv").find('p.jquery_form_error_message').remove();
		  $("#documentOtherType").attr("data-validation", "required validate_Space validate_length length1-250");
	}else{
		document.getElementById("documentTypeDiv").style.display="none";
		  $("#documentOtherType").attr("data-validation-optional", "true");
		  $("#documentOtherType").find('p.jquery_form_error_message').remove();
		  $("#nameDocIdDiv").find('p.jquery_form_error_message').remove();
		  //$("#errorvendorContact").find("p.jquery_form_error_message").remove();
		  
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
function storeCandidateDocument(candidateId, documentUUID) {
	var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
	}; 
	if ($('#documentType_form').validate(false, validationSettings)){
		var form = $('#documentType_form')[0];
		var formData = new FormData(form);
		$("#save_doc").attr("disabled", "disabled");
        	$("#save_doc" ).addClass('button').val('Processing..');
        	$.ajax({
			type : "POST",
        	data: formData,
    	    processData: false,
    	    contentType:false,
			url : sourceleadUrl+"/saveCandidateDocument?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&documentUUID="+documentUUID,
			success : function(response) {
							if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
					    		showError(response.responseMessage);
							} else if (response.response === "AWKWARD") {
							    location.href = "/errorPage";
							} else if (response.response === "SUCCESS") {
								var result = response.object;
								closePopup();
								$('#perVal').attr('aria-valuenow', result).css('width',result+'%');
			          			if(result<50) {
			          				$("#perVal").removeClass('progress-bar-success');
			          			   	$("#perVal").addClass("progress-bar-danger");
			          			}
			          			if(result>=50){
			          				$("#perVal").removeClass('progress-bar-danger');
			          				$("#perVal").addClass("progress-bar-success");
			          			}
			          			$("#disVal").empty();
			          			$("#disVal").append(result+'%');
								$("#popUpDiv").removeClass("edit-details-active");
		            			$("#selector6").removeAttr("style");
		            			$("#selector6").css({ display: "block" });
		            			if(documentUUID != null && documentUUID !="" && documentUUID !="undefined")
	            					$("#docMsgDiv").html("Document updated");
		            			else
		            				$("#docMsgDiv").html("Success!! New Document added");
		             			candDocumentsList(candidateId);
						 		docMsgFadein();
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
}
$('body').on('blur', '#documentType_form', function() {
	$("#documentType_form").showHelpOnFocus();
	$("#documentType_form").validateOnBlur(false, validationSettings);
});		
</script>

<script>
function uploadFileDoc(){
	fileUpload("uploadBtn", "candidateDocuments", "documentType_form");
}
</script>

<%-- 
     @author : Prasad K
     this script is for get document details in edit mode
     1.usage     : onclick
     2.parameter : candidateId, documentId
     3.ajax url implementation in CandidateDocumentRestController.
--%>
<script>
function getDocument(candidateId, documentId){
	  $("#docsName").attr("data-validation-optional", "true");
	  $("#documentOtherType").attr("data-validation-optional", "true");
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getDocument?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&documentId="+documentId, 	   
   		    success: function(response) {
   		    	if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
   		    	    showError(response.responseMessage);
   		    	} else if (response.response === "AWKWARD") {
   		    	    location.href = "/errorPage";
   		    	} else if (response.response === "SUCCESS") {
   		    		var data = response.object;
   		    		$("#uploadDocDiv").hide();
       			 	$("#docAddButton").hide();
	       			$("#uploadFile").removeAttr("data-validation");
	       			$("#docNameDiv").show();
	       			$("#docsName").append(data.fileName);
	       			$("#docsName").prop('title', data.fileName);
	       			$("#docsUpdateButton").show();
	       			if(data.documentType != null && data.documentType != "undefined" && data.documentType != "") {
		            	$("select#docuName").val(data.documentType);
		                $("select#docuName").trigger('chosen:updated');
		                //here documentType is Other then documentOtherType  also display in Edit.
		                if(data.documentType == 'Other') {
			        		if(data.documentOtherType !=null && data.documentOtherType !='' && data.documentOtherType !="undefined") {
			        			document.getElementById("documentTypeDiv").style.display="block";
			        			$("#documentOtherType").val(data.documentOtherType);
				    		}
		        		}
	       			}
	       			$("#updates_doc").attr("onclick", "storeCandidateDocument("+data.candidateId.id+", \""+data.documentUUID+"\");");
	       			if(data.description != null && data.description != "undefined" && data.description != "") {
	       				$("textarea#description").val(data.description);
	       			}
	       			if(data.isConfidential == "Y") {
	       				$("#isConfidential").attr('checked', true)
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
function deleteDocumentPopup(){
	$("#remove_user_pop_documents, .project_inn_mask").fadeOut();
}
</script>

<div id="deleteDocumentPopUp"></div>

<script type="text/javascript">
function removeDocument(candidateId, documentId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop_documents' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>You are choosing to delete the Documents</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteDocument("+candidateId+","+documentId+");'><input class='u_can_color' type='button'  value='Cancel' onclick='deleteDocumentPopup();'></b>";
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
function deleteDocument(candidateId, documentId){
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	//var message = confirm("You are choosing to delete the document");
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateDoc?${_csrf.parameterName}=${_csrf.token}&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&documentId="+documentId, 	   
   		    success: function(response) {
   		    	if (response && response.response === "INVALID_DATA") {
   		    	    showError(response.responseMessage);
   		    	} else if (response && response.response === "INVALID_USER") {
   		    	    showError(response.responseMessage);
   		    	} else if (response.response === "AWKWARD") {
   		    	    location.href = "/errorPage";
   		    	} else if (response.response === "SUCCESS") {
   		    		var data = response.object;
   		    		$('#perVal').attr('aria-valuenow', data).css('width',data+'%');
       			 if(data<50){
       			  /* $("#perVal").removeClass("progress-bar-success"); */
       			   $("#perVal").find('progress-bar-success').remove();
       			   $("#perVal").addClass("progress-bar-danger");
       			 }
       			 if(data>=50){
       				 $("#perVal").find('progress-bar-danger').remove();
       				 $("#perVal").addClass("progress-bar-success");
       			 }
       			 $("#disVal").empty();
       			$("#disVal").append(data+'%');
       			 $("#selector6").removeAttr("style");
        			 $("#selector6").css({ display: "block" });
        			 $("#docMsgDiv").html("Document deleted");
       			 $("#popUpDiv").removeClass("edit-details-active");
       			 $("#remove_user_pop_documents, .project_inn_mask").fadeOut();
       			 candDocumentsList(candidateId);
       		     docMsgFadein();
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

<%-- 
     @author : Prasad K
     this script is for add document popup
     1.usage     : onload
--%>
<script>
function refreshDocList(candidateId){
	candDocumentsList(candidateId);
}
</script>

<script type="text/javascript">
 function validateDocName(candidateId) {
	  var name = document.getElementById("docuName").value; 
 	  $('#nameDocIdDiv').find('p.jquery_form_error_message').remove();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateDocName?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&name="+name, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		if(response.object == true) {
			        		  $('#nameDocIdDiv').find('p.jquery_form_error_message').remove();
							  $("#docuName").after("<p class='jquery_form_error_message'>"+name+" is Already Existed</span>");
							  $("#save_doc").attr('disabled','disabled');
							  $("#updates_doc").attr('disabled','disabled');
			        	} else{
			        		 $('#nameDocIdDiv').removeAttr( "record-exist record-exist-errormsg");
			        		 $('#nameDocIdDiv').find('p.jquery_form_error_message').remove(); 
	                         $("#save_doc").removeAttr("disabled");
			        	}
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    });
 }
 </script>
 <script type="text/javascript">
 function removeValidationProfCanDoc(){
	 $("#uploadDocDiv").find('p.jquery_form_error_message').remove(); 
 }
 
</script>
 <!-- <script>
	function clearUpload(){
		$("#uploadFile").val("");
	}
</script> -->
