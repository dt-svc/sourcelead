<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div id="tab-b" class="newPrTabs_inn">
 <div id="choicepopUp"></div>
	<form:form id="vendorOnboardUploadedDocuments_form" name="vendorOnboardUploadedDocuments_form" method="post">
		 <div class="form-group">
		  <label class="col-md-12 mt10">Required documents for onboarding :</label>
			<div id="timesheetDocumentsId" class="timesheetDocs timesheetDocs-custom">
                      <div class="col-md-8 commentFile timesheet_week_comm onboardDocs">
				        <span class="uploadAttachments"> 
			              <div id="vendorDocumentsList"></div>
				        </span>
                      </div>
                      </div>
			
		</div>
	<div class="clearfix"></div>	
	<div class="form-footer clearfix miniFoot">
	  <div class="col-lg-12">
		<div class="pull-right">
		  <input type="button" value="Save & Continue" id="save_continue_doc_button" onClick="saveDocuments()" class="btn btn-primary ml10">
		</div>
	  </div>
	</div>
	</form:form>
</div>



<script>
function saveDocuments(){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  
	   if ($('#vendorOnboardUploadedDocuments_form').validate(false, validationSettings)){ 
	   $("#save_continue_doc_button").attr("disabled", "disabled");
	   var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	   var formData = getFormData("vendorOnboardUploadedDocuments_form");
	   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
		$.ajax({
		      type : "POST",
  	      data: formData,
	    	  url: vmsUrl+"/saveVendorOnboardSubmittedDocuments?${_csrf.parameterName}=${_csrf.token}",		
		      success :function(result) {
		    	  
		    	  var vendorTasks = document.getElementById("vendorTasks");
					 $("#vendorTasks").removeClass("disabledLink");
					 vendorTasks.href="#tab-c";
					/*  vendorTasks.addEventListener("click", function() {
						 getVendorOnboardTasksList();
					  }); */
					  $("#vendorTasks").click();
		    	  
	      	      $("#save_continue_doc_button").removeAttr("disabled");
		    	  $("#loading").removeAttr("style");
		    	  
		      }
	    	  
	  });
		 return true
	  }else{
		  return false
	  }
	  
	  $('body').on('blur', '#vendorOnboardUploadedDocuments_form', function() {
			$("#vendorOnboardUploadedDocuments_form").showHelpOnFocus();
			$("#vendorOnboardUploadedDocuments_form").validateOnBlur(false, validationSettings);  
	   });
	
	
}
</script>


<script>
function getOnboardDocuments(){
	var vendorOnboardId = '${vendorOnboardId}';
	 var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	 $.ajax({
	    	type: "GET",
	    	 url: vmsUrl+"/getVendorOnboardDocumentByVendorOnboardId?${_csrf.parameterName}=${_csrf.token}&vendorOnboardId="+vendorOnboardId, 
	        success: function(json) {
	        	
     	       var result = "";
     	      result = result +"<table class='timesheetAttachment'  id='contractsAttachmentTable' cellpadding='0' cellspacing='0'>";
	         	 
     	     if(json.length > 0){ 
     	      for (var i = 0; i < json.length; i++) { 
	         		 var opt = json[i]; 
	         		 var datavalidation = '';
	         		 var requiredAstrik = '';
	         		 if(opt.isRequired == 'Y'){
	         			datavalidation = "data-validation='validatedoc required'";
	         			requiredAstrik = "<span class='text-danger astrick'>&nbsp;*</span>";
	         		 }else{
	         			datavalidation = "data-validation-optional='true' data-validation='validatedoc'"
	         		 }
                	 result = result +"<tr>";
                	 result = result +"<td><input type='text' readonly='readonly' class='form-control input-sm vendorinput' value='"+opt.documentName+"'></td>";
                	 if(opt.uploadedFileUrl != null){
                		 result = result +"<td class='inputpadding positionRelative'>";
                		 result = result +"<i class=''><a class='downloadbtn glyphicon glyphicon-save' target='_blank' href=\""+opt.uploadedFileUrl+"\"></a></i>";
           				 result = result +"<input type='button' value='"+opt.uploadedFileName+"' class='tbldata-dotdot'>";
           				 result = result +"</td>";
           				 result = result +"<td><i class='fa fa-times'  onClick='deleteVendorUploadedDocument("+opt.id+","+i+");'></i></td>";
                	 }else{
                		 result = result +"<td><div class='positionRelative'><input type='file' id='"+i+"vendorDocFile' field-name='Attachment' "+datavalidation+"  onchange='uploadVendorDocument("+i+")'  class='timesheetAttachmentValidation'>"+requiredAstrik+"</div></td>";
                    	 result = result +"<td><input type='hidden' value="+opt.id+"  name='vendorDocumentsFileInfo["+i+"].docId'></td>";
                    	 
                	 }
                	 
                	 result = result +"</tr>";
	         	 }
     	     }else{
	        		result = result +'<div><h4>&nbsp;&nbsp;&nbsp;No Documents Found</h4></div>';
	        	}
         	 result = result +"</table>";
      	
      	 $("#vendorDocumentsList").empty();
    	     $("#vendorDocumentsList").append(result);
	        },
	    });
	
}
</script>

<script>
function uploadVendorDocument(id){
	fileUpload(""+id+"vendorDocFile", "Vendor-Onboard-Submitted-Documents", "vendorOnboardUploadedDocuments_form","vendorDocumentsFileInfo@"+id+"");
}
</script>


<script>
function deleteVendorUploadedDocument(documentId,id){
	 var vmsUrl = '${SourceLeadWeb_vmsUrl}';
	 $.ajax({
	    	type: "POST",
	    	 url: vmsUrl+"/deleteVendorUploadedOnboardDocument?${_csrf.parameterName}=${_csrf.token}&documentId="+documentId, 
	         success: function(result) {
	        	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        	 getOnboardDocuments();
	        	 $("#vendorOnboardUploadedDocuments_form input[data-id*="+id+"vendorDocFile]").remove();
	        	 
	        	 $("#loading").removeAttr("style");
	        }
	 });
}
</script>