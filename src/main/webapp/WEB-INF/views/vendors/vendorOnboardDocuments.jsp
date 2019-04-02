<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div id="tab-b" class="newPrTabs_inn">
 <div id="choicepopUp"></div>
  <div id="vendorDocumentsLoading"></div>
	<form:form id="vendorDocuments_form" name="vendorDocuments_form" method="post">
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
	<input type="hidden" name="vendorOnboardId" id="onboardId" value=""/>
	<div class="clearfix"></div>	
	<div class="form-footer clearfix miniFoot">
	  <div class="col-lg-12">
		<div class="pull-right">
		  <input type="button" value="Cancel"  onClick="javascript:window.location.href = '${SourceLeadWeb_vmsUrl}/vendorsList'"  class="btn btn-danger">
		  <input type="button" value="Save & Continue" id="save_continue_doc_button" onClick="validateSavedDocuments();" class="btn btn-primary ml10">
		  <!-- <input type="button" value="Save & Continue" id="save_continue_doc_button" onClick="validateSavedDocuments(); saveDocuments()" class="btn btn-primary ml10"> -->
		</div>
	  </div>
	</div>
	</form:form>
</div>

<div id="divContractsNameTd1" style="display: none; float: left;">
	  			<input type="text"  placeholder='Document Name' onblur='validateDocumentName(REPLACEROWID)' class='form-control input-sm vendorinput' id='REPLACEROWIDcontractsdocFileName' name="vendorOnboardDocumentsFileInfo[REPLACEROWID].docFileName"  data-validation="required" field-name="Document Name" > 
</div>


<div id="divDocsTd3" style="display: none; float: left;">
<label class="switch pull-left ml-mr switchvendor-custom">
       <input type="checkbox" checked id="REPLACEROWIDchecked" name="vendorOnboardDocumentsFileInfo[REPLACEROWID].checked" onclick="checkDocument(REPLACEROWID)" value="Y">
       <div class="slider round"></div>
       </label>
</div>   

<div id="divDocsTd4" style="display: none; float: left;">
   <i class="fa fa-times" onclick="removeContractsAttachmentRow(REPLACEROWID);"></i> 
</div> 



<script>
function validateDocumentName(id){
	var checkMaxTsRowNum = $("#contractsAttcahmentMaxRowNumber").val();
	var flag = true;
	var j = "";
	 var documentName = $("#"+id+"contractsdocFileName").val();
	 for (var i = 0; i < checkMaxTsRowNum; i++) { 
		
		 if(id != i){
			 if(documentName != "undefined"){
				 var docName = $("#"+i+"contractsdocFileName").val();
				 if(documentName.localeCompare(docName) == 0){
					 flag = false;
					 break;
				 } 
			 }
		 }
		
		
	 }
	 
	 var savedDoc = $("#vendorDocumentsList strong[id*=contractsdocument]").length;
	 for (var i = 0; i <= savedDoc; i++) { 
		 var docName = $("#"+i+"savedDoc").val();
		 if(documentName.localeCompare(docName) == 0){
			 flag = false;
			 break;
		 }
	 }
	 
	 if(flag == false){
		  alertMessage(documentName);
		  var addDocRow = document.getElementById("addDocRow");
		  $("#addDocRow").removeAttr("onclick");
		  $("#save_continue_doc_button").attr("disabled", "disabled");
	 }else{
		  $("#save_continue_doc_button").removeAttr("disabled"); 
		  var addDocRow = document.getElementById("addDocRow");
		  $("#addDocRow").attr("onclick","addContractsAttachment()");
	 }
	
	
	
	
}
</script>

<script>
function validateSavedDocuments(){
	 var flag = true;
	 var docNames = [];
	 var savedDoc = $("#vendorDocumentsList strong[id*=contractsdocument]").length;
	 for (var i = 0; i <= savedDoc; i++) { 
		 var saveDoc = $("#"+i+"savedDoc").val();
		 if(typeof  saveDoc !== "undefined"){
			 docNames.push($("#"+i+"savedDoc").val());
		 }
	 }
	 
	 var documentName = "";
	 for (var i = 0; i < docNames.length; i++) 
     {
         for (var j = 0; j < docNames.length; j++) 
         {
             if (i != j) 
             {
                 if (docNames[i] == docNames[j]) 
                 {
                	 flag = false; 
                	 documentName = docNames[i];
                	 break;
                 }
             }
         }
     }
	 
	 if(flag == false){
		  alertMessage(documentName);
		  $("#save_continue_doc_button").attr("disabled", "disabled");
	 }else{
		  $("#save_continue_doc_button").removeAttr("disabled");
		  saveDocuments();
	 }
	
}
</script>

<script type="text/javascript">
function alertMessage(documentName){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Duplicate Document <strong>"+documentName+"</strong></p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script>
function saveDocuments(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  
	   if ($('#vendorDocuments_form').validate(false, validationSettings)){ 
	   $("#save_continue_doc_button").attr("disabled", "disabled");
	   var formData = getFormData("vendorDocuments_form");
	   $("#vendorDocumentsLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
		      type : "POST",
    	      data: formData,
	    	  url: vmsUrl+"/saveVendorOnboardingDocuments?${_csrf.parameterName}=${_csrf.token}",		
		      success :function(result) {
		    	  
		    	     var vendorTasks = document.getElementById("vendorTasks");
					 document.getElementById("vendorTasks").removeAttribute("class");
					 vendorTasks.href="#tab-c";
					/*  vendorTasks.addEventListener("click", function() {
						 getVendorOnboardTasksList();
					  }); */
					 $("#vendorTasks").click();
// 					 vendorTasks.click();
					 
	      	      $("#save_continue_doc_button").removeAttr("disabled");
		    	  $("#vendorDocumentsLoading").removeAttr("style");
		    	  
		      }
	    	  
	  });
		 return true
	  }else{
		  return false
	  }
	  
	  $('body').on('blur', '#vendorDocuments_form', function() {
			$("#vendorDocuments_form").showHelpOnFocus();
			$("#vendorDocuments_form").validateOnBlur(false, validationSettings);  
	   });
	
	
}
</script>


<script>
function getOnboardDocuments(){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var orgRelationId = document.getElementById("orgRelationId").value;
	var vendorOnboardId = document.getElementById("vendorOnboardId").value;
	var documentType = "Documents"
	if(orgRelationId && vendorOnboardId){
		$("#onboardId").val(vendorOnboardId);
		
		 $.ajax({
		    	type: "POST",
		    	 url: vmsUrl+"/getVendorOnboardTemplateDocumentByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&vendorOnboardId="+vendorOnboardId+"&documentType="+documentType, 
		        success: function(json) {
		        	
	        	 var result = "";
	        		
		         	 for (var i = 0; i < json.length; i++) { 
		         		 var opt = json[i]; 
		         		 var checked = '';
		         		 if(opt.isRequired == 'Y'){
		         			 checked = "checked";
		         		 }
		         		 result = result +"<strong id='contractsdocument"+opt.id+"'>";
		         		 
		         		 result = result +"<input type='text' id='"+i+"savedDoc' value='"+opt.documentName+"'  name='vendorTemplateDocumentsFileInfo["+i+"].documentName' data-validation='required' field-name='Document Name' class='form-control input-sm vendorinput'></input>";
		         		
		         		 
		   				 result = result +"<label class='switch switchvendor-custom pull-left ml-mr'>";
		           	     result = result +"<input type='checkbox' "+checked+" id='"+opt.id+"docChecked' name='vendorTemplateDocumentsFileInfo["+i+"].docChecked' onclick='alertRequiredMessage(\""+opt.documentUUID+"\"\,"+opt.id+")'>"
		           	     result = result +'<div class="slider round"></div>';
	          		     result = result +'</label>';
		   				
	          		    if(opt.documentType == 'Documents'){
	          		    	 result = result +"<input class='dele' type='button' onClick='deleteTemplateDocument("+opt.id+");' >";
	          		    }else if(opt.documentType == 'Vendor Documents'){
	          		    	 result = result +"<input class='dele' type='button' onClick='deleteVendorDocument(\""+opt.documentUUID+"\","+opt.id+");' >";
	          		    } 
		   				
	   				    result = result +"</strong>";
		   				result = result +"<input type='hidden' name='vendorTemplateDocumentsFileInfo["+i+"].documentType'  value='"+opt.documentType+"' />";
	   				    result = result +"<input type='hidden' name='vendorTemplateDocumentsFileInfo["+i+"].docId' id='vendorDocuments"+opt.id+"' value='"+opt.id+"' />";
		         	 }
		         
		         result = result +"<input type='hidden' name='contractsAttcahmentMaxRowNumber'  id='contractsAttcahmentMaxRowNumber' value='1' />";
		         result = result +"<input type='hidden' id='addedDocumentsRowCount' value='1' />";
            	 result = result +"<table class='timesheetAttachment'  id='contractsAttachmentTable' cellpadding='0' cellspacing='0'>";
            	 result = result +"<tr id='divAttachmentTsRow0'>";
            	 result = result +"<td><input type='text' placeholder='Document Name' onblur='validateDocumentName(0)' class='form-control input-sm vendorinput'  data-validation-optional='true' field-name='Document Name' name='vendorOnboardDocumentsFileInfo[0].docFileName' id='0contractsdocFileName'></td>";
            	 result = result +"<td>"; 
            	 result = result +"<label class='switch pull-left ml-mr switchvendor-custom'>";
            	 result = result +"<input type='checkbox'  checked id='0checked' name='vendorOnboardDocumentsFileInfo[0].checked' onclick='checkDocument(0)' value='Y'>"
            	 result = result +'<div class="slider round"></div>';
           		 result = result +'</label>';
            	 result = result +"</td>";
            	 result = result +"<td><i class='fa fa-plus' id='addDocRow' onclick='addContractsAttachment()'></i></td>";
            	 result = result +"</tr>";
            	 result = result +"</table>";
	         	
	         	 $("#vendorDocumentsList").empty();
	       	     $("#vendorDocumentsList").append(result);
		        },
		    });
	}
	
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
function deleteTemplateDocument(documentId){
	
	document.getElementById("contractsdocument"+documentId).remove();
	$("#vendorDocuments"+documentId).val('');
	 $("#save_continue_doc_button").removeAttr("disabled"); 
	 
}
</script>


<script>
function deleteVendorDocument(documentUUID,documentId){
	   var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	   $.ajax({
	    	type: "POST",
	    	 url: vmsUrl+"/deleteVendorTemplateDocumentByDocumentUUID?${_csrf.parameterName}=${_csrf.token}&documentUUID="+documentUUID, 
	        success: function(data) {
	        	if(data == 'success'){
	        		document.getElementById("contractsdocument"+documentId).remove();
	        	}
	        },
	    });
	 
}
</script>


<script>
function addContractsAttachment(){  
			var checkMaxTsRowNum = $("#contractsAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("contractsAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'contractsAttcahmentMaxRowNumber']").val());
			$("input[name = 'contractsAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			$("input[id = 'addedDocumentsRowCount']").val(maxRowNum + 1);
			
			
			 var documentsRow = document.getElementById("addedDocumentsRowCount").value;
			 if(documentsRow > 1){
				 $("#0contractsdocFileName").removeAttr("data-validation-optional");
				 $("#0contractsdocFileName").removeAttr("data-validation");
				 $("#0contractsdocFileName").attr("data-validation", "required");
			 }
			 
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentContractsRow" + maxRowNum);
			
			
			
			
			var cell0 = row.insertCell(0);
			var divDocTypeTdStr = document.getElementById("divContractsNameTd1").innerHTML;
			divDocTypeTdStr = divDocTypeTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divDocTypeTdStr;
			
			var cell2 = row.insertCell(1);
			var divDocCheckTdStr = document.getElementById("divDocsTd3").innerHTML;
			divDocCheckTdStr = divDocCheckTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell2.innerHTML = divDocCheckTdStr;
			
			var cell3 = row.insertCell(2);
			var divDocRemoveTdStr = document.getElementById("divDocsTd4").innerHTML;
			divDocRemoveTdStr = divDocRemoveTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell3.innerHTML = divDocRemoveTdStr;
}	

</script>


<script type="text/javascript">
	function removeContractsAttachmentRow(rowId) {
		
		 var addedDocumentsRowCount = document.getElementById("addedDocumentsRowCount").value;
		 $("input[id = 'addedDocumentsRowCount']").val(parseInt(addedDocumentsRowCount) - 1);
		 
		 var documentsRow = document.getElementById("addedDocumentsRowCount").value;
		 if(documentsRow == 1){
			 $("#0contractsdocFileName").removeAttr("data-validation");
			 $("#0contractsdocFileName").attr("data-validation-optional", "true");
		 }
		 
		 document.getElementById("divAttachmentContractsRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentContractsRow"+rowId).value= '';
		 $("#templateDocuments_form input[data-id='"+rowId+"contractsdocFile']").remove();
		 var  rId = "divAttachmentContractsRow"+rowId;
		  var r = document.getElementById("contractsAttachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
	}
</script>

<script type="text/javascript">
function alertRequiredMessage(documentUUID,id){
	var checked = document.getElementById(id+"docChecked").checked
	var message = '';
	if(checked == true){
		message = "Are you sure to make required?";
	}else{
		message = "Are you sure to make not required?";
	}
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='docIsRequiredOrNot(\""+id+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel(\""+id+"\");'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script>
function docIsRequiredOrNot(id){
	
	var checked = document.getElementById(id+"docChecked").checked
	if(checked == true){
		document.getElementById(id+"docChecked").checked = true;
	}else{
		document.getElementById(id+"docChecked").checked = false;
	}
	   $("#choicepopUp").empty();
	   $(".project_inn_mask").fadeOut(200);
}
</script>

<script type="text/javascript">
function cancel(id){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	var checked = document.getElementById(id+"docChecked").checked
	if(checked == false){
		document.getElementById(id+"docChecked").checked = true;
	}else{
		document.getElementById(id+"docChecked").checked = false;
	}
	
}
</script>
