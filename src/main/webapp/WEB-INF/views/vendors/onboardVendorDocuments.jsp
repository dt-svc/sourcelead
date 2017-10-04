<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<style>
.timesheet_week_comm strong input.dele  {
width : 10px !important;
}
.timesheetDocs.timesheetDocs-custom .timesheet_week_comm strong input.dele {
width : 20px !important;
}
.timesheetDocs.timesheetDocs-custom {
width: 65%;
}
.timesheetDocs.timesheetDocs-custom .timesheetAttachment {
width: auto !important;
}
.timesheetDocs.timesheetDocs-custom .timesheet_week_comm span input {
width: 275px !important; padding-left: 15px !important; text-indent:0; text-align: left;
}
.timesheetDocs.timesheetDocs-custom .timesheet_week_comm label input[type="checkbox"] {
width: auto !important;
}
.timesheetDocs.timesheetDocs-custom .timesheet_week_comm input.vendorinput {
	background-color: #fff; color: #333; border: 1px solid #ddd; width: 250px !important;
	margin-right: 15px;
}
.timesheetDocs.timesheetDocs-custom .timesheetAttachment td {
width: auto !important;
}
.timesheetDocs.timesheetDocs-custom .timesheet_week_comm label.switch {
position: relative; background-color: transparent; width: 35px; overflow: inherit; padding: 5px 10px;
}
.ml-mr {
margin-left: 15px !important; margin-right: 15px !important; margin-top: 5px !important;
}
.switch.switchvendor-custom input[type="checkbox"]{
background: #f60; position: relative;top:0; top: -15px; opacity: 0;
filter: alpha(opacity=0);  -moz-opacity: 0; -khtml-opacity: 0;
}
.timesheetDocs.timesheetDocs-custom .timesheet_week_comm span .inputpadding input[type="button"] {
	padding-left: 35px !important;
}
</style>

<div id="tab-b" class="newPrTabs_inn">
 <div id="choicepopUp"></div>
  <div id="vendorDocumentsLoading"></div>
	<form:form id="vendorOnboardUploadedDocuments_form" name="vendorOnboardUploadedDocuments_form" method="post">
		 <div class="form-group">
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
	   var formData = getFormData("vendorOnboardUploadedDocuments_form");
	   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
		$.ajax({
		      type : "POST",
  	      data: formData,
	    	  url: sourceleadUrl+"/saveVendorOnboardSubmittedDocuments?${_csrf.parameterName}=${_csrf.token}",		
		      success :function(result) {
		    	  
		    	  var vendorTasks = document.getElementById("vendorTasks");
					 $("#vendorTasks").removeClass("disabledLink");
					 vendorTasks.href="#tab-c";
					 vendorTasks.addEventListener("click", function() {
						 getVendorOnboardTasksList();
					  });
					 vendorTasks.click();
		    	  
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
	 $.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getVendorOnboardDocumentByVendorOnboardId?${_csrf.parameterName}=${_csrf.token}&vendorOnboardId="+vendorOnboardId, 
	        success: function(json) {
	        	
     	       var result = "";
     	      result = result +"<table class='timesheetAttachment'  id='contractsAttachmentTable' cellpadding='0' cellspacing='0'>";
	         	 for (var i = 0; i < json.length; i++) { 
	         		 var opt = json[i]; 
	         		 var datavalidation = '';
	         		 if(opt.isRequired == 'Y'){
	         			datavalidation = "data-validation='validatedoc required'"
	         		 }else{
	         			datavalidation = "data-validation-optional='true' data-validation='validatedoc'"
	         		 }
                	 result = result +"<tr>";
                	 result = result +"<td><input type='text' readonly='readonly' class='form-control input-sm vendorinput' value='"+opt.documentName+"'></td>";
                	 result = result +"<td><div><input type='file' id='"+i+"vendorDocFile' field-name='Attachment' "+datavalidation+"  onchange='uploadVendorDocument("+i+")'  class='timesheetAttachmentValidation'></div></td>";
                	 result = result +"<td><input type='hidden' value="+opt.id+"  name='vendorDocumentsFileInfo["+i+"].docId'></td>";
                	 result = result +"</tr>";
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