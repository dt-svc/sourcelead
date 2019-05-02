<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 
<link rel="icon" type="image/png" href="/resources/images/fav.png" />
<title>Welcome to Source Lead :: Documents Request</title>
<!-- Bootstrap -->
<link href="/resources/css/bootstrap.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/font-awesome.min.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/sourcelead-main.css?staticTime=${staticTime}" rel="stylesheet">
<link href="/resources/css/timesheet.css?staticTime=${staticTime}" rel="stylesheet">
<script src="/resources/js/InterViewFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script>
<script src="/resources/js/interViewForm-html-generator.js?staticTime=${staticTime}" type="text/javWascript" ></script> 
<script src="/resources/js/jquery-1.11.1.min.js?staticTime=${staticTime}"></script>
<script src="/resources/js/bootstrap.min.js?staticTime=${staticTime}"></script> 
</head>
 
<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" />
<script src="/resources/js/jquery-ui.js?staticTime=${staticTime}"></script>
<link rel="stylesheet" href="/resources/css/jquery-ui.css?staticTime=${staticTime}" type="text/css" >
<body>
<script type="text/javascript">
var sourceleadUrl ='${sourceleadUrl}';
</script>
<div class="clearfix"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="/resources/images/SourceLead-logo.png" alt="Source Lead"> </i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
   
   <c:if test="${isDocsUploaded == 'N'}">
	   <form:form class="login-form clearfix reqDocuments reqDocuments_customs container"  id="requestForDocs_form"  name="requestForDocs_form">
	   <div id="loading"></div>
	   <input type="hidden" name="requestDocUUID" id="requestDocUUID" value="${requestDocUUID}">
	   <input  type="hidden" name="requestDocType" id="requestDocType" value="${docs.documentType}"/>
		   <div class="clearfix clients_wrap_table">
		   	<h4>${orgName}</h4>
		   	<table class="" width="100%">
		   		<thead>
		   			<tr>
		   				<th>Document Name</th>
		   				<th>Description</th>
		   				<th>Action</th>
		   			</tr>
		   		</thead>
		   		<tbody>
		   				<c:if test="${candidateDocumentsListSize > 0}">
							   <c:forEach var="docs" items="${candidateDocumentsList}">
							   <tr>
							        <td>
							        <div class="tbldata-dotdot-sm">${docs.documentType}
							        <c:if test="${docs.requestedDocStatus == 'Rejected' && !empty docs.requestedDocRejectedReason}">
							        	<i class="glyphicon-custom glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status edit-status-custom"><p>${docs.requestedDocRejectedReason}</p></div></i>
							        </c:if>
							        </div>
							        </td>
					   				
					   				<td>
					   				<c:if test="${!empty docs.description}">
					   				<div class="tbldata-dotdot-big" title="'${docs.description}'">${docs.description}</div>
					   				</c:if>
					   				<c:if test="${empty docs.description}">
					   				<div class="tbldata-dotdot-big">-------</div>
					   				</c:if>
					   				</td>
								    <td class="text-center">
								    <div class="width300px mt5" id="attachmentDiv${docs.id}">
								        <c:if test="${docs.isRequestedDocRequired == 'Y'}">
								        	<input type="file"  class="form-control welFile"  id="uploadDocFile${docs.id}"    placeholder="Upload Resume ..." field-name="Document" data-validation="required validate_docimgpdf" onchange="uploadFileReqDoc(${docs.id});">
								        </c:if>
								        
								        <c:if test="${docs.isRequestedDocRequired == 'N'}">
								        	<input type="file"  class="form-control welFile"  id="uploadDocFile${docs.id}"   placeholder="Upload Resume ..." field-name="Document" data-validation="validate_docimgpdf" data-validation-optional="true" onchange="uploadFileReqDoc(${docs.id});">
								        </c:if>
								     </div>
								    </td>
						       </tr>
							   </c:forEach>
							  
			  			 </c:if>
		   		</tbody>
		   	</table>
		   	<div class="clearfix mt mb">
		   	<div class="pull-right">
		   		<button type="button" id="submit_docs" onclick="requestDocSave();" class="btn btn-primary">Upload</button>
		   	</div>
		   	</div>
		   </div>
	   
		   
	   </form:form>
   </c:if>
   
    <c:if test="${isDocsUploaded == 'Y'}">
        <div class="clearfix reqDocuments container">
       		 <div class='text-center success-message'><i class='fa fa-handshake-o'></i>Thanks for uploading documents.</div>
        </div>
    </c:if>
   
<script type="text/javascript">
   function requestDocSave(){
	   var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
        }; 
		if ($("#requestForDocs_form").validate(false, validationSettings)){
		  	var requestDocUUID = '${requestDocUUID}';
		    var formData = getFormData1("requestForDocs_form");
			$("#submit_docs").attr("disabled", "disabled");
			$("#submit_docs" ).addClass('button').val('Processing..');
			$.ajax({
				type : "POST",
				data: formData,
			    processData: false,
			    contentType:false, 
				url : sourceleadUrl+"/ws/uploadRequestedDocuments?"+tokenParamName+"="+tokenValue+"",
				success : function(response) {
					if (response && response.response === "INVALID_DATA") {
					    showError(response.responseMessage);
					} else if (response.response === "AWKWARD") {
					    location.href = "/errorPage";
					} else if (response.response === "SUCCESS") {
						location.replace(sourceleadUrl+"/ws/requestedDocuments?requestDocUUID="+requestDocUUID); 
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
	$('body').on('blur', '#requestForDocs_form', function() {
		$("#requestForDocs_form").showHelpOnFocus();
		$("#requestForDocs_form").validateOnBlur(false, validationSettings);
	});
</script>
  <script type="text/javascript">
var tokenParamName = "${_csrf.parameterName}";
var tokenValue = "${_csrf.token}";

function tagItBykeyForEmails(e){
	if(e.which == 0 || e.which == 13 || e.which == 9 || e.which == 32 || e.which == 188){
		 $(document).click();
		 return true;
	}
}
</script>
<script>
function uploadFileReqDoc(id){
	fileUpload("uploadDocFile"+id+"", "requestedDocuments", "requestForDocs_form", '', '${orgId}', '${orgLocationId}');
	$('#attachmentDiv'+id).find('p.jquery_form_error_message').remove();
}
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
</script>
   
   <footer class="clearfix stickyfooter">
   <div class="container">
    <div class="pull-left"> @ Copyright : Source Lead, All right reserved </div>
    <div class="pull-right"> Powered by: businessintelli.com </div>
    </div>
  </footer>
  <script type="text/javascript" src='/resources/js/slead-common.js'></script>
</div>
</body>