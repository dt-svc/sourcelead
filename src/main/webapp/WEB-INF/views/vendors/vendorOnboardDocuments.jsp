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
	<form:form id="vendorDocuments_form" name="vendorDocuments_form" method="post">
		 <div class="form-group">
		 <label class="col-md-12 mt10">These documents will be added for vendor on-boarding:</label>
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
		  <input type="button" value="Cancel"  onClick="javascript:window.location.href = '<c:url value='/projectDashboard'/>'"  class="btn btn-danger">
		  <input type="button" value="Save & Continue" onClick="saveDocuments()" class="btn btn-primary ml10">
		</div>
	  </div>
	</div>
	</form:form>
</div>

<div id="divContractsNameTd1" style="display: none; float: left;">
	  			<input type="text"  placeholder='Document Name' class='form-control input-sm vendorinput' id='REPLACEROWIDcontractsdocFileName' name="vendorDocumentsFileInfo[REPLACEROWID].docFileName"  data-validation="required" field-name="Document" > 
</div>


<div id="divDocsTd3" style="display: none; float: left;">
<label class="switch pull-left ml-mr switchvendor-custom">
       <input type="checkbox" checked id="REPLACEROWIDchecked" name="vendorDocumentsFileInfo[REPLACEROWID].checked" onclick="checkDocument(REPLACEROWID)" value="Y">
       <div class="slider round"></div>
       </label>
</div>   

<div id="divDocsTd4" style="display: none; float: left;">
   <i class="fa fa-times" onclick="removeContractsAttachmentRow(REPLACEROWID);"></i> 
</div> 


<script>
function getOnboardDocuments(){
	
	var orgRelationId = document.getElementById("orgRelationId").value;
	var documentType = "Documents"
	if(orgRelationId){
		 $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/getVendorOnboardTemplateDocumentByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&documentType="+documentType, 
		        success: function(json) {
		        	
	        	 var result = "";
	        		
		         if(json != null){
		         	 for (var i = 0; i < json.length; i++) { 
		         		 var opt = json[i]; 
		         		 var checked = '';
		         		 if(opt.isRequired == 'Y'){
		         			 checked = "checked";
		         		 }
		         		 result = result +"<strong id='contractsdocument"+opt.id+"'>";
		         		 result = result +"<input type='text' value='"+opt.documentName+"' readonly class='form-control input-sm vendorinput'></input>";
		         		 
		   				 result = result +"<label class='switch switchvendor-custom pull-left ml-mr'>";
		           	     result = result +"<input type='checkbox' "+checked+" id='"+opt.id+"docChecked' onclick='alertRequiredMessage(\""+opt.documentUUID+"\"\,"+opt.id+")'>"
		           	     result = result +'<div class="slider round"></div>';
	          		     result = result +'</label>';
		   				
		   				 result = result +"<input class='dele' type='button' onClick='deleteTemplateDocument(\""+opt.documentUUID+"\","+opt.id+",\""+documentType+"\");' >";
		   				 result = result +"</strong>";
		   				 
		   				 result = result +"<input type='hidden' name='onboardVendorDocuments' id='vendorDocuments"+opt.id+"' value='"+opt.id+"' />";
		         	 }
		         } 
		         
		         result = result +"<input type='hidden' name='contractsAttcahmentMaxRowNumber'  id='contractsAttcahmentMaxRowNumber' value='1' />";
            	 result = result +"<table class='timesheetAttachment'  id='contractsAttachmentTable' cellpadding='0' cellspacing='0'>";
            	 result = result +"<tr id='divAttachmentTsRow0'>";
            	 result = result +"<td><input type='text' placeholder='Document Name' class='form-control input-sm vendorinput'  data-validation='required' field-name='Document' name='vendorDocumentsFileInfo[0].docFileName' id='0contractsdocFileName'></td>";
            	 result = result +"<td>"; 
            	 result = result +"<label class='switch pull-left ml-mr switchvendor-custom'>";
            	 result = result +"<input type='checkbox' checked id='0checked' name='vendorDocumentsFileInfo[0].checked' onclick='checkDocument(0)' value='Y'>"
            	 result = result +'<div class="slider round"></div>';
           		 result = result +'</label>';
            	 result = result +"</td>";
            	 result = result +"<td><i class='fa fa-plus' onClick='addContractsAttachment()'></i></td>";
            	 result = result +"</tr>";
            	 result = result +"</table>";
	         	
	         	 $("#vendorDocumentsList").empty();
	       	     $("#vendorDocumentsList").append(result);
	       	    // $("#onboardTasksList").empty();
			        
		        },
		    });
	}
	
}
</script>


<script>
function addContractsAttachment(){  
			var checkMaxTsRowNum = $("#contractsAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("contractsAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'contractsAttcahmentMaxRowNumber']").val());
			$("input[name = 'contractsAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
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