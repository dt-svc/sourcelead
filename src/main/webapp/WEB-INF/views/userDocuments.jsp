
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div id="myDocuments" class="tab-pane fade">
			  
</div>

<script>
function candidateDocuments(){
	var candidateType = '${candidateType}';
	var candidateId = '${SourceLeadWeb_candidateId}';
	var result = "";
	
	if(window.candidateStatusGlobal == '0'){
		result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt25">  ';
	}else{
		result = result  +'    <div class="project_tabs_inn_head addUser-org overflow_inherint mt10">  ';
	}
	result  = result + '<div class="success_pop" id="selector9" style="display: none;">';
    result  = result + '<i class="fa fa-check-circle"></i><span id="docMsgDiv"></span>';
    result  = result + '</div>';
    var docUUID = '';
    result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onClick="addDocument('+candidateId+',\''+docUUID+'\');getDocType();" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Document</a></span>';
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
	    result = result+'	    </div>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table datatable_filter">';
		
		result  = result + '   <div id="candidateDocumentsList"></div>';
		result  = result + '   </div>';
	  

    $("#myDocuments").empty();
    $("#myDocuments").append(result); 
    candDocumentsList(candidateId);
}
</script>


<script>
function candDocumentsList(candidateId){
	var candidateType = '${candidateType}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateDocumentsListByCandidateId?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId,        

          success :function(response) {
        	  if (response && response.response === "INVALID_DATA") {
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
                   	 	result  = result + ' <tr>';
	                   	/* if(opt.requestedDocumentsId != null && opt.requestedDocStatus != "Rejected" && opt.requestedDocStatus != "Accepted"){
	                   		result = result +'<td><input id="'+opt.id+'" value="'+opt.id+'"  onclick="deleteRequestedDoc();" type="checkbox" name="documents[]" class="myCheckbox" >';
	                   	} else {
	                   		result = result +"  <td><div>--</div></td>"; 
	                   	} */
	                   	
	                   	if(opt.isConfidential == 'N' || opt.docUploadStatus =="Requested"|| (opt.isConfidential == 'Y' && '${SourceLeadWeb_user.userCode}' == opt.userIdStr)) {
                      		result  = result + ' <tr>';
                      	if(opt.requestDocumentId != null && opt.requestedDocStatus == "Submitted"){
                      		result = result +'<td><input id="'+opt.id+'" value="'+opt.id+'"  onclick="deleteRequestedDoc();" type="checkbox" name="documents[]" class="myCheckbox" >';
                      	} else {
                      		result = result +"  <td><div>--</div></td>"; 
                      		}
	                   	}
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
// 			           	if(window.candidateStatusGlobal == 0) {
// 			           		result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
// 				           	result = result+'<i class="glyphicon glyphicon-trash" href="javascript:void(0)"></i>';
// 			           	} else {
			           		var docUUID = opt.documentUUID;
			           		result = result+' <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="getDocType();getDocument('+candidateId+', '+opt.id+');addDocument('+candidateId+',\''+docUUID+'\');" title="Edit"></a>';
// 			           		if(candidateType =="Candidates") {
// 			           			'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CANDIDATE_DOCUMENT\',\'ROLE_CROWDSOURCING_CANDIDATES\')">';
// 			           				result = result+'<i class="glyphicon glyphicon-trash" onclick="removeDocument('+candidateId+','+opt.id+');" title= "Remove"></i>';
// 			           			'</sec:authorize>';
// 			           		} else {
// 			           			'<sec:authorize access="hasRole(\'ROLE_DELETE_ASSOCIATE_DOCUMENT\')">';
			           				result = result+'<i class="glyphicon glyphicon-trash" onclick="removeDocument('+candidateId+','+opt.id+');" title= "Remove"></i>';
// 			           			'</sec:authorize>';
// 			           		}
// 			           	}
			           	result = result+'</div></td>';
	                   	result  = result + '</tr>';
	                    }
	                    result = result +" </tbody>";
	                    result = result +"</table>";
	                    $("#candidateDocumentsList").append(result); 
	                    documentsPagination();
        		} else if (json.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
        		} else {
        		    showError("Operation failed, please try again");
        		}
          	}
	});     
}
</script>	


<script>
function documentsPagination(){  
    $('#candidateDocumentsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<script>
	function toggleAllDocuments(source) {
	    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
	    for (var i = 0; i < checkboxes.length; i++) {
	        if (checkboxes[i] != source)
	            checkboxes[i].checked = source.checked;
	        
	    }
	$('input:checkbox:not(#checkAllDocuments)').click(function() {
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

<script>
function addDocument(candidateId, documentUUID) {
	
	var result = "";
	result = result  +'<!-- Popup Div -->';
	result = result  +'<form role="form"  id="documentType_form" name="documentType_form" method="post" > ';
	result = result  +'<i class="fa fa-close close-div"  onClick="closeAddDocPopup();"></i> ';
	result = result  +'<div class="form-horizontal clearfix"> ';
	result = result  +'   ';
	
	result = result  +'<div class="clearfix mb10 col-md-12"> ';
	result = result  +'	<select label=" " class="chosen-select form-control" field-name="Document Type" data-placeholder = "Select Document Type" name="name" id="docName" data-validation="required" onchange="getDocumentType(this);">';
    result = result + '</select>';
	result = result  +' </div> ';
	result = result  +'';
	
	result = result + '<div class="col-md-12 documentTypeDiv mb10" id="documentTypeDiv" style="display:none">';
	result = result + '<input type="text" class="form-control input-sm" name="documentOtherType"  id="documentOtherType" value="" placeholder="Document Other Type" field-name="Document Other Type">';
	result = result + '</div>'; 
	
	result = result+'<div class="clearfix mb10" id="uploadDocDiv">';
	result = result+'<div class="col-md-12 validation_p">';
	result = result+'<div class="input-group clearUpload" id="uploadFileGroup" onClick="clearUpload()">';
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
    result = result +'<input type="button" value="Upload" name="btnAddPr" id="save_doc" class="btn btn-sm btn-primary pull-right" onClick ="storeCandidateDocument('+candidateId+',\''+documentUUID+'\');" />'; 
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

<script>
function uploadFileDoc(){
	fileUpload("uploadBtn", "candidateDocuments", "documentType_form");
}
</script>


<script type="text/javascript">
	function storeCandidateDocument(candidateId,documentUUID) {
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#documentType_form').validate(false, validationSettings)){
				 if(document.getElementById("uploadBtn") !== null){
		        var frm = $('#documentType_form')[0];
				var formData = new FormData(frm);
				$("#save_doc").attr("disabled", "disabled");
         		$("#save_doc").addClass('button').val('Processing..');
         		
         		$.ajax({
					type : "POST",
	        	    data: formData,
	    	        processData: false,
	    	        contentType:false,
	        	   	url : sourceleadUrl+"/saveCandidateDocument?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&documentUUID="+documentUUID,
					success : function(response) {
						if (response && response.response === "INVALID_DATA") {
						    showError(response.responseMessage);
						} else if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							closeAddDocPopup();
							var result = response.object;
							$('#perVal').attr('aria-valuenow', result).css('width',result+'%');
		          			if(result < 50) {
		          				$("#perVal").removeClass('progress-bar-success');
		          			   	$("#perVal").addClass("progress-bar-danger");
		          			}
		          			if(result >= 50) {
		          				$("#perVal").removeClass('progress-bar-danger');
		          				$("#perVal").addClass("progress-bar-success");
		          			}
		          			$("#disVal").empty();
		          			$("#disVal").append(result+'%');
		          			
		          		// for quick view bar update
	            			$('#quickPerVal').attr('aria-valuenow', result).css('width',result+'%');
		          			if(result < 50) {
			          			$("#quickPerVal").removeClass('progress-bar-success');
			          			$("#quickPerVal").addClass("progress-bar-danger");
			          		}
			          		if(result >= 50) {
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
	       }else {
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
function closeAddDocPopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>

<script>
function getDocument(candidateId, documentId){
	 $("#docsName").attr("data-validation-optional", "true");
	  $("#documentOtherType").attr("data-validation-optional", "true");
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getDocument?"+tokenParamName+"="+tokenValue+"&candidateId="+candidateId+"&documentId="+documentId, 	   
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
       			 	if(data.fileName != null && data.fileName !="undefined" && data.fileName != "") {
	       				$("#docNameDiv").show();
       			 	}else{
       			 	$("#uploadDocDiv").show();
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
       			 	
       			 	
       			 		$("#update_doc").attr("onclick", "storeCandidateDocument("+data.candidateId.id+",\""+data.documentUUID+"\");");
       			 
		       			if(data.description != null && data.description != "undefined" && data.description != "") {
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

<script>
function getDocType(){
	$.get(sourceleadUrl+"/getUserDocumentList", function(result) {
		var json = result.data;
		var output = "";
		output = output +' <option value="">--Select Document Type--</option>';
    	for (var i = 0; i < json.length; i++) { 
    		  var documentList = json[i];
    		  if(documentList != null){
    			  output = output +'<option value="'+documentList.name+'">'+documentList.name+'</option>';
    		  }
    	  }
    	$("#docName").empty();
    	$("#docName").append(output);
    	$('#docName').trigger("chosen:updated");
	}); 
	
}
</script>


<script>
function removeDocNameDiv(){
	$("#docNameDiv").hide();
	$("#uploadDocDiv").show();
	$("#uploadFile").attr("data-validation", "required validate_Space validate_docimgpdf");
}
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

<script>
	function docMsgFadein() {
		$("#selector9").fadeIn(200);
        $('#selector9').delay(2000).fadeOut('slow');
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
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='deleteDocument("+candidateId+","+documentId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='deleteDocumentPopup();'></b>";
	result = result +"	</div>";
	$("#deleteDocumentPopUp").empty();
	$("#deleteDocumentPopUp").append(result);
	$("#remove_user_pop_documents").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script>
function deleteDocument(candidateId, documentId){
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	//var message = confirm("You are choosing to delete the document");
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateDoc?"+tokenParamName+"="+tokenValue+"&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&documentId="+documentId, 	   
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
       			 $("#selector9").removeAttr("style");
        			 $("#selector9").css({ display: "block" });
        			 $("#docMsgDiv").html("Document deleted");
       			 $("#popUpDiv").removeClass("edit-details-active");
       			 $("#remove_user_pop_documents, .project_inn_mask").fadeOut();
       			 candDocumentsList(candidateId);
       			 docMsgFadein();
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

<script type="text/javascript">
function deleteDocumentPopup(){
	$("#remove_user_pop_documents, .project_inn_mask").fadeOut();
}
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
