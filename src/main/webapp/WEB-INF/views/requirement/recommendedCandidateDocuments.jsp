<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
.addUser-org {
    overflow-x: auto;
}
.project_tabs_inn_table table tr td div i.glyphicon-trash{
color:red !important;
}
</style>
<%-- 
     @author : prasadK.
     this script is for display candidate documents tab data.
     1.usage     : used at clientSideBar().
--%>
<script>
function candidateDocuments(candidateId){
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
		result = result  +'<a href="javascript:void(0)"  class="addUser-org" ></a> ';
	}else{
		var docUUID = '';
		result = result  +'<span class="pad-sea"><a href="javascript:void(0)"  onClick="addDocument('+candidateId+',\''+docUUID+'\');refreshDocList('+candidateId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Document</a></span>';
	}
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		
		result  = result + '   <div id="candidateDocumentsList" class="datatable_filter"></div>';
		result  = result + '   </div>';
	    result = result+'	    </div>';
	  
    $("#tab-11").empty();
    $("#tab-12").empty(); 
    $("#tab-13").empty();  
    $("#tab-14").empty();  
    $("#tab-15").empty();  
    $("#tab-16").empty();  
    $("#tab-17").empty(); 
    $("#history").empty();
    $("#tab-17").append(result);
    
    candDocumentsList(candidateId);
}
</script>


<script>
function candDocumentsList(candidateId){
	  var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getCandidateDocumentsListByCandidateId?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId,        
          success :function(data) {
           
             			 var json = JSON.parse(data); 
             			 
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
	                     
	                     for (var i = 0; i < json.length; i++) { 
	                    	 var opt = json[i];  
	                    	 result  = result + ' <tr>';
	                    	 if(opt.documentType != null && opt.documentType != '' && opt.documentType != "undefined")
	    	             	 {
	    	            	  if(opt.documentOtherType !=null && opt.documentOtherType !='' && opt.documentOtherType !="undefined"){
	    	                   result = result +"  <td><div>"+opt.documentOtherType+"</div></td>";
	    	             	 }else{
	    	             		result = result +"  <td><div>"+opt.documentType+"</div></td>"; 
	    	             	 }
	    	             	 }else{
	    	             		 result = result +"  <td><div>----</div></td>"; 
	    	             	 }
	                    	 
	                    	 if(opt.fileName != null && opt.fileName != '' && opt.fileName != 'undefined'){
	                    	 result  = result + ' <td><div><span title="'+opt.fileName+'">'+opt.fileName+'</span></div></td>';
	                    	 }else{
	                    		 result  = result + ' <td><div><span>----</span></div></td>';
	                    	 }
	                    	 
	                    	 if(opt.description != null && opt.description != '' && opt.description != 'undefined'){
		                    	 result  = result + ' <td><div><span title="'+opt.description+'">'+opt.description+'</span></div></td>';
		                    	 }else{
		                    		 result  = result + ' <td><div><span>----</span></div></td>';
		                    	 }
		                    	
		                   
		                   result = result+'<td><div class="tblicons"> <a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadDoc?documentUUID='+opt.documentUUID+'\"></a>';
			           		if(window.candidateStatusGlobal == 0){
			           			result = result+'<a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
				           		result = result+'<i class="glyphicon glyphicon-trash" href="javascript:void(0)"></i>';
			           		}else{
			           			var docUUID = opt.documentUUID;
			           			result = result+' <a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="addDocument('+candidateId+',\''+docUUID+'\'); getDocument('+candidateId+', '+opt.id+');"></a>';
				           		result = result+'<i class="glyphicon glyphicon-trash" onclick="deleteDocument('+candidateId+','+opt.id+');"></i>';
			           		}
			           		result = result+'</div></td>';
	                    	 result  = result + '</tr>';
	                      } 
	                     result = result +" </tbody>";
	                     result = result +"</table>";
	                     $("#candidateDocumentsList").append(result); 
	                     documentsPagination();
          }
          });     
}
</script>

<!-- Show success message after form submission script -->
<script>
	function eduMsgFadein() {
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
	
	result = result  +'<div class="clearfix mb10 col-md-12"> ';
	result = result  +'	<select label=" " class="chosen-select form-control" field-name="Document Type" data-placeholder = "Select Document Type" name="name" id="name" data-validation="required" onchange="getDocumentType(this);">';
					   <c:forEach var="doctype" items="${documentsList}">
    result = result +'<option value="" disabled selected></option>';										 
	result = result + '<option value="${doctype.name}">${doctype.name}</option>';
	                   </c:forEach>  
    result = result + '</select>';
	result = result  +' </div> ';
	result = result  +'';
	
	result = result + '<div class="col-md-12 documentTypeDiv mb10" id="documentTypeDiv" style="display:none">';
	result = result + '<input type="text" class="form-control input-sm" name="documentOtherType"  id="documentOtherType" value="" placeholder="Document Other Type" field-name="Document Other Type" data-validation="validate_Space validate_length length2-50" data-validation-optional="true">';
	result = result + '</div>'; 
	
	result = result+'<div class="clearfix mb10" id="uploadDocDiv">';
	result = result+'<div class="col-md-12">';
	result = result+'<div class="input-group positionRelative" id="uploadFileGroup">';
	result = result+'<label class="input-group-btn" onChange="uploadFileDoc();"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
	result = result+'<input type="file"  id="uploadBtn" style="display: none;"  multiple="" class="upload">';
	result = result+'</span> </label>';
	result = result+'<input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload Document..." field-name="Document Type" data-validation="validate_Space validatedoc">';
	result = result+'</div>';
	result = result+'</div>';  
	result = result+'</div>'; 
	
	result = result+'<div class="clearfix mb10" id="docNameDiv" style="display: none;">';
	result = result+'<div class="col-md-12 mt">';
	result = result+'<label id="docName" name="docName" class="text-primary tbldata-dotdot-vsm pull-left"></label>&nbsp;&nbsp;<span id="delete_doc" onclick="removeDocNameDiv();"><i class="fa fa-trash removeIcon"></i></span>';
	result = result+'</div>';
	result = result+'</div>';
	
	result = result+'<div class="clearfix mb10 mt25">';
	result = result+'<div class="col-md-12">';
	result = result+'<textarea rows="2" placeholder="Description" value="" id="description" name="description" data-validation="validate_length length2-250" data-validation-optional="true" field-name="Description" class="form-control"></textarea>';
	result = result+'</div>';
	result = result+'</div>';  
	
	result = result+'<div class="clearfix mr mt" id="docUpdateButton" style="display: none;">';
	result = result+'<button type="button" id="update_doc" class="btn btn-sm btn-primary pull-right">Update</button>';
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
	    var selectedFile = document.getElementById("uploadBtn");
	    selectedFile.addEventListener("change", function() {
	      var docSize = this.files[0].size;
	      $("#uploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  document.getElementById("uploadFile").value = '';
	    	  $("#uploadFileGroup").append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
	      }
	    });
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
	 var existingDocumentType = $('#name').val(); 
	if(documentType.value == 'Other'){
		 document.getElementById("documentTypeDiv").style.display="block";
		  $("#documentOtherType").attr("data-validation-optional", "true");
		  $("#documentOtherType").attr("data-validation", "validate_Space validate_length length2-250");
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
	function storeCandidateDocument(candidateId, documentUUID){
			var validationSettings = {
			        errorMessagePosition : 'element',
			        borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
			 if ($('#documentType_form').validate(false, validationSettings)){
				 
				    var sourceleadUrl = '${sourceleadUrl}';
			        var frm = $('#documentType_form')[0];
					var formData = new FormData(frm);
					$("#save_doc").attr("disabled", "disabled");
	         		$("#save_doc" ).addClass('button').val('Processing..');
	         		
	         		$.ajax({
						type : "POST",
		        	    data: formData,
		    	        processData: false,
		    	        contentType:false,
		        	   	url : sourceleadUrl+"/saveCandidateDocument?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&documentUUID="+documentUUID,
						success : function(result) {
								closePopup();
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
			          			
								$("#popUpDiv").removeClass("edit-details-active");
		            			$("#selector9").removeAttr("style");
		            			$("#selector9").css({ display: "block" });
	            				$("#docMsgDiv").html("Success!! New Candidate Document added");
		             			candDocumentsList(candidateId);
		             			resMsgFadein();
		            		 
						},
					}); 
				
					return true;
			 }else{ 
					return false;
			 } 
		}
		$('body').on('blur', '#documentType_form', function() {
		$("#documentType_form").showHelpOnFocus();
		$("#documentType_form").validateOnBlur(false, validationSettings);
	});
		
</script>


<%-- 
     @author : Prasad K
     this script is for get document details in edit mode
     1.usage     : onclick
     2.parameter : candidateId, documentId
     3.ajax url implementation in CandidateResumeRestController.
--%>
<script>
function getDocument(candidateId, documentId){
	var sourceleadUrl ='${sourceleadUrl}';
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getDocument?${_csrf.parameterName}=${_csrf.token}&candidateId="+candidateId+"&documentId="+documentId, 	   
   		    success: function(data) {
        			 $("#uploadDocDiv").hide();
        			 $("#docAddButton").hide();
        			 $("#uploadFile").removeAttr("data-validation");
        			 $("#docNameDiv").show();
        			 $("#docName").append(data.fileName);
        			 $("#docUpdateButton").show();
        			if(data !=null){
        				 if(data.documentType != null && data.documentType != "undefined" && data.documentType != ""){
        					 
            				 $("select#name").val(data.documentType);
            				 $("select#name").trigger("chosen:updated");
    	                     //here documentType is Other then documentOtherType  also display in Edit.
    	                     
    	                     if(data.documentType == 'Other'){
    		        				 if(data.documentOtherType !=null && data.documentOtherType !='' && data.documentOtherType !="undefined"){
    		        					 document.getElementById("documentTypeDiv").style.display="block";
    		        					 $("#documentOtherType").val(data.documentOtherType);
    			    	             }
    	        			 }

             			 }
            			 
            			 $("#update_doc").attr("onclick", "storeCandidateDocument("+data.candidateId.id+", \""+data.documentUUID+"\");");
            			 
            			 if(data.description != null && data.description != "undefined" && data.description != ""){
            				 $("textarea#description").val(data.description);
            			 }	
        			}
        		
        			 
   		    }
	     });
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
	
	var sourceleadUrl ='${sourceleadUrl}';
	var loginUserId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var message = confirm("You are choosing to delete the resume");
	if (message == true) {
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateDoc?${_csrf.parameterName}=${_csrf.token}&loginUserId="+loginUserId+"&orgLocationId="+orgLocationId+"&candidateId="+candidateId+"&documentId="+documentId, 	   
   		    success: function(data) {
   		    	if(data != null && data != 'undefined' && data != ""){
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
        			 candDocumentsList(candidateId);
        		     resMsgFadein();
   		    	}
   		    },
	     });
}
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
	$("#uploadFile").attr("data-validation", "required validate_Space validatedoc");
}
</script>

<!-- Show success message after form submission script -->
<script>
	function resMsgFadein() {
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
function refreshDocList(candidateId){
	candDocumentsList(candidateId);
}
</script>

<script>
function uploadFileDoc(){
	var sourceleadUrl ='${sourceleadUrl}';
	fileUpload(sourceleadUrl, "uploadBtn", "candidateDocuments", "documentType_form");
}
</script>