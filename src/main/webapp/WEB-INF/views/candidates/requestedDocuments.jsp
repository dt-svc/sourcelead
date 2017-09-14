<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="resources/images/fav.png" />
<title>Welcome to Source Lead :: Documents Request</title>
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link href="resources/css/timesheet.css" rel="stylesheet">
<script src="resources/js/InterViewFormBuilder.js" type="text/javascript" ></script>
<script src="resources/js/interViewForm-html-generator.js" type="text/javWascript" ></script> 
<script src="resources/js/jquery-1.11.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script> 
</head>
 
<script src="resources/js/formvalidator.js"></script>
<link rel="stylesheet" href="resources/css/style.css" />
<script src="resources/js/jquery-ui.js"></script>
<link rel="stylesheet" href="resources/css/jquery-ui.css" type="text/css" >
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
.reqDocuments {
	padding-top: 10px; border: 1px solid #ededed; margin-top: 75px;
    margin-bottom: 15px; min-height: 150px; border-radius:5px;
}
.reqDocuments table {
border:1px solid #ddd; width: 100%;
}
.reqDocuments .clients_wrap_table {
border-bottom:0;
}
.clients_wrap_table .glyphicon {
    color: red;
    cursor: pointer;
}
.edit-status {
top: 20%; right: auto; left: 40%;
}
</style>

<body>
<div class="clearfix"> 
  <!-- Header Area -->
  <header class="main-header">
   <span class="logo">
   	 	<a href="javascript:void(0)" title="Source Lead"> <i> <img src="resources/images/SourceLead-logo.png" alt="Source Lead"> </i>
    </a>
    </span>
    <nav class="header-nav navbar">
    </nav>
   </header>
   
   <c:if test="${isDocsUploaded == 'N'}">
	   <form:form class="login-form clearfix reqDocuments container"  id="requestForDocs_form"  name="requestForDocs_form">
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
							        	<i class="glyphicon glyphicon-comment ml10 edit-reason"><div class="edit-status"><p>${docs.requestedDocRejectedReason}</p></div></i>
							        </c:if>
							        </div>
							        </td>
					   				
					   				<td>
					   				<c:if test="${!empty docs.description}">
					   				<div class="tbldata-dotdot-big" title="${docs.description}">${docs.description}</div>
					   				</c:if>
					   				<c:if test="${empty docs.description}">
					   				<div class="tbldata-dotdot-big">-------</div>
					   				</c:if>
					   				</td>
								    <td class="text-center">
								    <div class="width300px mt5">
								        <c:if test="${docs.isRequestedDocRequired == 'Y'}">
								            <%-- <input type="file"  class="form-control welFile" readonly id="uploadDocFile${docs.id}" name="docFile${docs.id}"   placeholder="Upload Resume ..." field-name="Document" data-validation="required validate_docimgpdf" onchange="uploadFileReqDoc();"> --%>
								        	<input type="file"  class="form-control welFile" readonly id="uploadDocFile${docs.id}"    placeholder="Upload Resume ..." field-name="Document" data-validation="required validate_docimgpdf" onchange="uploadFileReqDoc(${docs.id});">
								        </c:if>
								        
								        <c:if test="${docs.isRequestedDocRequired == 'N'}">
								           <%--  <input type="file"  class="form-control welFile" readonly id="uploadDocFile${docs.id}" name="docFile${docs.id}"   placeholder="Upload Resume ..." field-name="Document" data-validation="validatedoc" data-validation-optional="true" onchange="uploadFileReqDoc();"> --%>
								        	<input type="file"  class="form-control welFile" readonly id="uploadDocFile${docs.id}"   placeholder="Upload Resume ..." field-name="Document" data-validation="validatedoc" data-validation-optional="true" onchange="uploadFileReqDoc(${docs.id});">
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
		   		<button type="submit" id="submit_docs" class="btn btn-primary">Upload</button>
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
   var validationSettings = {
	        errorMessagePosition : 'element',
	        borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
        }; 
$('body').on('submit', '#requestForDocs_form', function(e){
	
	  if ($("#requestForDocs_form").validate(false, validationSettings)){
      e.preventDefault();
     
     /*  var frm = $('#requestForDocs_form').serializeFormJSON();
      var con = JSON.stringify(frm);
      con = con.replace(/[{}]/g, "");
      
      //It will escape all the special characters
      var jsonData = encodeURIComponent(con);
      
      var form = $('#requestForDocs_form')[0];
	  var formData = new FormData(form);  */
	  var requestDocUUID = '${requestDocUUID}';
	    var formData = getFormData1("requestForDocs_form");
		var sourceleadUrl = '${sourceleadUrl}';
		$("#save_doc").attr("disabled", "disabled");
		$("#save_doc" ).addClass('button').val('Processing..');
		
		$.ajax({
				 type : "POST",
				 data: formData,
		         processData: false,
		         contentType:false, 
			url : sourceleadUrl+"/uploadRequestedDocuments?${_csrf.parameterName}=${_csrf.token}",
			success : function(data) {
				 location.replace(sourceleadUrl+"/requestedDocuments?requestDocUUID="+requestDocUUID); 

			},
		});
		
	/* 	
      $.post(sourceleadUrl+"/uploadRequestedDocuments?${_csrf.parameterName}=${_csrf.token}", formData,processData: false,contentType:false, function(data) {
    	  location.replace(sourceleadUrl+"/requestedDocuments?requestDocUUID="+requestDocUUID); 
      }); */
     
      return true; 
    }
    else{
    	return false;	
       }
  });

	$('body').on('blur', '#requestForDocs_form', function() {
		$("#requestForDocs_form").showHelpOnFocus();
		$("#requestForDocs_form").validateOnBlur(false, validationSettings);
	});
  </script>
  
<script>
function uploadFileReqDoc(id){
	var sourceleadUrl ='${sourceleadUrl}';
	fileUpload(sourceleadUrl, "uploadDocFile"+id+"", "requestedDocuments", "requestForDocs_form", '', '${orgId}', '${orgLocationId}');
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
  <script type="text/javascript" src='resources/js/slead-common.js'></script>
</div>
</body>