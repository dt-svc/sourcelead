 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="icon" type="resources/image/png" href="resources/images/fav.png" />
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 

<script>
function assetDetails(assetId) {
		var sourceleadUrl = '${sourceleadUrl}';
		
		$.ajax({
			type : "POST",
			url : sourceleadUrl	+ "/getAssetById?${_csrf.parameterName}=${_csrf.token}&assetId="+ assetId,
			success : function(data) {
				var json = JSON.parse(data);
				    
				    var make = "NO DATA";
				    var model = "NO DATA";
				    var name = "NO DATA";
				    var memory = "NO DATA";
				    var cpu = "NO DATA";
				    var workingStatus = "NO DATA";
				    var hardDisk = "NO DATA";
				    var monitor = "NO DATA";
				    var description = "NO DATA";
				    var shiftAEmpName = " ";
				    var shiftBEmpName = " ";
					
					if (json.make != null && json.make != '' && json.make != 'undefined') {
						make = json.make;
					} 
					
					if (json.model != null && json.model != '' && json.model != 'undefined') {
						model = json.model;
					} 
					
					if (json.name != null && json.name != '' && json.name != 'undefined') {
						name = json.name;
					}  
					
					if (json.memory != null && json.memory != '' && json.memory != 'undefined') {
						memory = json.memory;
					} 
					
					if (json.cpu != null && json.cpu != '' && json.cpu != 'undefined') {
						cpu = json.cpu;
					} 
					
					if (json.workingStatus != null && json.workingStatus != '' && json.workingStatus != 'undefined') {
						workingStatus = json.workingStatus;
					}
					
					if (json.hardDisk != null && json.hardDisk != '' && json.hardDisk != 'undefined') {
						hardDisk = json.hardDisk;
					}
					
					if (json.monitor != null && json.monitor != '' && json.monitor != 'undefined') {
						monitor = json.monitor;
					}
					
					if (json.description != null && json.description != '' && json.description != 'undefined') {
						description = json.description;
					} 
					
					if (json.shiftAEmpName != null && json.shiftAEmpName != '' && json.shiftAEmpName != 'undefined') {
						shiftAEmpName = json.shiftAEmpName;
					}else{
						shiftAEmpName = "NO DATA";
					}
					
					if (json.shiftBEmpName != null && json.shiftBEmpName != '' && json.shiftBEmpName != 'undefined') {
						shiftBEmpName = json.shiftBEmpName;
					}else{
						shiftBEmpName = "NO DATA";
					}
					
					var result = "";
					result = result + '<div class="require_wrap_details">';
					result = result + '<h1>Asset Management details: <a id="edit-user" href="javascript:editAssetManagement('+assetId+');">Edit<i class="glyphicon glyphicon-pencil"></i></a></h1>';
					result = result + '<div class="require_one">';
					
					result = result + '<div class="require_wrap_details_cta"> <b>S. No.:</b> <span>'+json.slno+'</span>';
					result = result + '<div class="clearfix"></div>';
					result = result + ' </div>';
					
					result = result + '<div class="require_wrap_details_cta"> <b>Make:</b> <span>'+make+'</span>';
					result = result + '  <div class="clearfix"></div>';
					result = result + '</div>';
					
					result = result + ' <div class="require_wrap_details_cta"> <b>Model:</b> <span>'+model+'</span>';
					result = result + '  <div class="clearfix"></div>';
					result = result + ' </div>';
					
					result = result + ' <div class="require_wrap_details_cta"> <b>Name:</b> <span>'+name+'</span>';
					result = result + '  <div class="clearfix"></div>';
					result = result + ' </div>'; 
					
					if (json.fileName != null && json.fileName != '' && json.fileName != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b>Attched File:</b> <span>'+json.fileName+'<a href=\"'+sourceleadUrl+'/downloadAssetFile?fileType=file&assetId='+json.id+'\"><i class="fa fa-download"></i></a></span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
					}else{
						result = result + ' <div class="require_wrap_details_cta"> <b>Attched File:</b> <span>NO DATA</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
					}
					
					result = result + ' <div class="require_wrap_details_cta"> <b>Description:</b> <span>'+description+'</span>';
					result = result + ' <div class="clearfix"></div>';
					result = result + '</div>';
					
					result = result + '<div class="require_wrap_details_cta"> <b>Working Status:</b> <span>'+workingStatus+'</span>';
					result = result + ' <div class="clearfix"></div>';
					result = result + ' </div>';
					
					//this block not visible to others category assets
					if (json.assetCategory != null && json.assetCategory != '' && json.assetCategory != 'undefined' && json.assetCategory.id != 3) {
						result = result + '<div class="require_wrap_details_cta"> <b>Memory:</b> <span>'+memory+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="require_wrap_details_cta"> <b>CPU:</b> <span>'+cpu+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
						
						result = result + ' <div class="require_wrap_details_cta"> <b>Hard Disk:</b> <span>'+hardDisk+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
						
						if (json.assetCategory != null && json.assetCategory != '' && json.assetCategory != 'undefined' && json.assetCategory.id == 2) {
							result = result + ' <div class="require_wrap_details_cta"> <b>Monitor:</b> <span>'+monitor+'</span>';
							result = result + ' <div class="clearfix"></div>';
							result = result + ' </div>';
						}
						
						result = result + ' <div class="require_wrap_details_cta"> <b>Shfit A:</b> <span>'+shiftAEmpName+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + '</div>';
						
						if (json.shiftAFileName != null && json.shiftAFileName != '' && json.shiftAFileName != 'undefined') {
							result = result + ' <div class="require_wrap_details_cta"> <b>Shfit A Attched File:</b> <span>'+json.shiftAFileName+'<a href=\"'+sourceleadUrl+'/downloadAssetFile?fileType=shiftAfile&assetId='+json.id+'\"><i class="fa fa-download"></i></a></span>';
							result = result + '   <div class="clearfix"></div>';
							result = result + '  </div>';
						}
						
						result = result + '  <div class="require_wrap_details_cta"> <b>Shfit B:</b> <span>'+shiftBEmpName+'</span>';
						result = result + '   <div class="clearfix"></div>';
						result = result + '  </div>';
						
						if (json.shiftBFileName != null && json.shiftBFileName != '' && json.shiftBFileName != 'undefined') {
							result = result + ' <div class="require_wrap_details_cta"> <b>Shfit B Attched File:</b> <span>'+json.shiftBFileName+'<a href=\"'+sourceleadUrl+'/downloadAssetFile?fileType=shiftBfile&assetId='+json.id+'\"><i class="fa fa-download"></i></a></span>';
							result = result + '   <div class="clearfix"></div>';
							result = result + '  </div>';
						}
					}
					//this block not visible to others category assets
					
					result = result + '  <div class="clearfix"></div>';
					result = result + '<div class="user_summary_edit client_summary_edit" id="editUserDiv" ></div>';
					result = result + '  <div class="clearfix"></div>';
					result = result + '  </div>';
					$("#tab-1").empty();
					$("#tab-1").append(result);
					$("#tab-1").show();
			}
		});
}
</script>	

<script>
function editAssetManagement(assetId){
 var sourceleadUrl ='${sourceleadUrl}';
 
 var sourceleadUrl = '${sourceleadUrl}';
	
	$.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/getAssetById?${_csrf.parameterName}=${_csrf.token}&assetId="+ assetId,
		success : function(data) {
			var json = JSON.parse(data);
			
			var make = "";
		    var model = "";
		    var name = "";
		    var memory = "";
		    var cpu = "";
		    var workingStatus = "";
		    var hardDisk = "";
		    var monitor = "";
		    var description = "";
		    var shiftAEmpName = "";
		    var shiftBEmpName = "";
		    var fileName = "";
		    var shiftAFileName = "";
		    var shiftBFileName = "";
		    var workingStatus = "";
		    var selected1 = '';
			var selected2 = '';
			var empA = "";
			var empB = "";
			
			if (json.make != null && json.make != '' && json.make != 'undefined') {
				make = json.make;
			} 
			
			if (json.workingStatus != null && json.workingStatus != '' && json.workingStatus != 'undefined') {
				workingStatus = json.workingStatus;
			}
			
			if (json.model != null && json.model != '' && json.model != 'undefined') {
				model = json.model;
			} 
			
			if (json.name != null && json.name != '' && json.name != 'undefined') {
				name = json.name;
			}  
			
			if (json.monitor != null && json.monitor != '' && json.monitor != 'undefined') {
				monitor = json.monitor;
			}
			
			if (json.memory != null && json.memory != '' && json.memory != 'undefined') {
				memory = json.memory;
			} 
			
			if (json.cpu != null && json.cpu != '' && json.cpu != 'undefined') {
				cpu = json.cpu;
			} 
			
			if (json.workingStatus != null && json.workingStatus != '' && json.workingStatus != 'undefined') {
				workingStatus = json.workingStatus;
			}
			
			if (json.hardDisk != null && json.hardDisk != '' && json.hardDisk != 'undefined') {
				hardDisk = json.hardDisk;
			}
			
			if (json.description != null && json.description != '' && json.description != 'undefined') {
				description = json.description;
			} 
			
			if (json.fileName != null && json.fileName != '' && json.fileName != 'undefined') {
				fileName = json.fileName;
			}
			
			if (json.shiftAFileName != null && json.shiftAFileName != '' && json.shiftAFileName != 'undefined') {
				shiftAFileName = json.shiftAFileName;
			}
			
			if (json.shiftBFileName != null && json.shiftBFileName != '' && json.shiftBFileName != 'undefined') {
				shiftBFileName = json.shiftBFileName;
			}
			
			if(json.workingStatus == 'Working'){
				selected1 = 'selected';
			} 
		
			if(json.workingStatus == 'Out Of Service'){
				selected2 = 'selected';
			} 
			
           var result = "";
           result = result + '<div id="updateLoading"></div>';
           result = result + '<div class="form-horizontal req-dtls myForm row">';
           result = result + '<form id="updateAsset_form"  name="updateAsset_form" enctype="multipart/form-data"> ';
           result = result	+ '<input type="hidden" id="assetIdVal" value="'+assetId+'">';
           result = result + '<div class="col-md-12 edit-category edit-amd">';
           result = result + '<div class="clearfix assetaddpop-ul col-md-6 category">';
           result = result + '<div class="row">';
          
           result = result + '</div>';
           result = result + '</div>';
			result = result + '<div class="clearfix" id="laptop-edit">';
			result = result + '<div class="form-horizontal clearfix">';
			
			result = result + '<div class="col-md-6">';
			result = result + ' <input type="text" class="form-control input-sm" name="sno" value="'+json.slno+'" field-name="Sl No" data-validation="required validate_Space validate_length length2-200" placeholder="Serial Number *">';
			result = result + '</div>';
			
			result = result + '<div class="assetaddpop-ul col-md-6" id="workingStatusDiv">';
			result = result + '<select class="chosen-select" id="workingStatus" name="workingStatus" data-validation="required" field-name="Working Status">';
			result = result + '<option selected value="">-Select Working Status-</option>';
			result = result + ' <option '+selected1+'>Working</option>';
			result = result + ' <option '+selected2+'>Out Of Service</option>';
			result = result + '</select>';	
			result = result + ' </div>';
			
			result = result + '<div class="col-md-6">';
			result = result + '  <input type="text" class="form-control input-sm" name="make" value="'+make+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="Make">';
			result = result + '</div>';
			
			result = result + ' <div class="col-md-6">';
			result = result + '  <input type="text" class="form-control input-sm" name="model" value="'+model+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="Model">';
			result = result + ' </div>';
			
			result = result + ' <div class="col-md-6">';
			result = result + '  <input type="text" class="form-control input-sm" name="name" value="'+name+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="Name">';
			result = result + ' </div>';
			
			
			//this block not visible to others category assets
			if (json.assetCategory != null && json.assetCategory != '' && json.assetCategory != 'undefined' && json.assetCategory.id != 3) {
			
				result = result + ' <div class="col-md-6">';
				result = result + '  <input type="text" class="form-control input-sm" name="memory" value="'+memory+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-50" placeholder="Memory">';
				result = result + '</div>';
				
				result = result + '<div class="col-md-6">';
				result = result + ' <input type="text" class="form-control input-sm" name="cpu" value="'+cpu+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="CPU">';
				result = result + ' </div>';
				
				result = result + ' <div class="col-md-6">';
				result = result + '<input type="text" class="form-control input-sm" name="harddisk" value="'+hardDisk+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="Hard Disk">';
				result = result + '</div>';
				
				if (json.assetCategory != null && json.assetCategory != '' && json.assetCategory != 'undefined' && json.assetCategory.id == 2) {
					result = result + ' <div class="col-md-6">';
					result = result + '<input type="text" class="form-control input-sm" name="monitor" value="'+monitor+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="Monitor">';
					result = result + '</div>';
				}
				
				result = result + '<div class="clearfix">';
				result = result + '<h4 class="col-md-12">Shift A</h4>';
				result = result + '</div>';
				result = result + '<div class="col-md-6">';
				result = result + ' <select class="form-control chosen-select" id="shiftAEmp" name="shiftAEmp">';
				result = result + '<option selected value="">-Select Employee-</option>';
					for (var i=0; i<json.employeesList.length; i++)
					{
						empA = json.employeesList[i];
		 	    	   var selected = '';
		 	    	  if(json.shiftAEmployee != null && json.shiftAEmployee != "" && json.shiftAEmployee != 'undefined' && json.shiftAEmployee.id == empA.id){
		 	    		 selected = 'selected';
		 	    	 }
		 	    	result = result+"<option value='"+empA.id+"' "+selected+">"+empA.user.firstName+" "+empA.user.lastName+"</option>"; 
					};
				result = result + '   </select>';
				result = result + ' </div>';
				
				 result = result + '<div class="col-md-6" id="shiftAFileAttachmentDiv">';
				 result = result + ' <div class="input-group">';
				 result = result + '   <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
				 result = result + '     <input type="file" style="display: none;" multiple name="uploadBtn-dskA" id="uploadBtnShiftA" class="upload">';
				 result = result + '          </b> </label>';
				 result = result + '  <input type="text" class="form-control" readonly name="shiftAUploadFile" id="shiftAUploadFile" placeholder="Files are not selected...">';
				 result = result + ' </div>';
				 result = result + '</div>';
				 
				 result = result+'  <div class="col-md-6" id="shiftADocNameDiv" style="display: none;">';
				 result = result+'  <label id="shiftADocumentName" name="shiftADocumentName" class="text-primary tbldata-dotdot-vsm pull-left" title="'+shiftAFileName+'" >'+shiftAFileName+'</label>&nbsp;&nbsp;<span id="delete_shiftAFile" onclick="removeShiftAFileNameDiv();"><i class="fa fa-trash removeIcon"></i></span>';
				 result = result+ '<input type="hidden" id="shiftAExisitngFile" name="shiftAExisitngFile" value="'+shiftAFileName+'">';
				 result = result+'  </div>';
				 
				
				result = result + '<div class="clearfix">';
				result = result + ' <h4 class="col-md-12">Shift B</h4>';
				result = result + '</div>';
				result = result + '<div class="col-md-6">';
				result = result + '<select class="form-control chosen-select" id="shiftBEmp" name="shiftBEmp">';
				result = result + '<option selected value="">-Select Employee-</option>';
					for (var i=0; i<json.employeesList.length; i++)
					{
						empB = json.employeesList[i];
		 	    	   var selected = '';
		 	    	  if(json.shiftBEmployee != null && json.shiftBEmployee != "" && json.shiftBEmployee != 'undefined' && json.shiftBEmployee.id == empB.id){
		 	    		 selected = 'selected';
		 	    	 }
		 	    	result = result+"<option value='"+empB.id+"' "+selected+">"+empB.user.firstName+" "+empB.user.lastName+"</option>"; 
					};
				result = result + '</select>';
				result = result + '</div>';
				
				result = result + '<div class="col-md-6" id="shiftBFileAttachmentDiv">';
				result = result + ' <div class="input-group">';
				result = result + '   <label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
				result = result + '     <input type="file" style="display: none;" multiple name="uploadBtn-dskB" id="uploadBtnShiftB" class="upload">';
				result = result + '    </b> </label>';
				result = result + '  <input type="text" class="form-control" readonly id="shiftBUploadFile" placeholder="Files are not selected...">';
				result = result + '</div>';
				result = result + '</div>';
				
				 result = result+'  <div class="col-md-6" id="shiftBDocNameDiv" style="display: none;">';
				 result = result+'  <label id="shiftBDocumentName" name="shiftBDocumentName" class="text-primary tbldata-dotdot-vsm pull-left" title="'+shiftBFileName+'" ></label>&nbsp;&nbsp;<span id="delete_resume" onclick="removeShiftBFileNameDiv();"><i class="fa fa-trash removeIcon"></i></span>';
				 result = result+ '<input type="hidden" id="shiftBExisitngFile" name="shiftBExisitngFile" value="'+shiftBFileName+'">';
				 result = result+'  </div>';
				 result = result+'  </div>';
			}
			//this block not visible to others category assets
			result = result + ' <div class="col-md-6">';
			result = result + '  <textarea class="form-control" rows="2" name="description" placeholder="Description" data-validation-optional="true" data-validation="validate_Space">'+description+'</textarea>';
			result = result + ' </div>';
			
			result = result + ' <div class="col-md-6" id="fileAttachmentDiv">';
			result = result + '<div class="input-group" >';
			result = result + '<label class="input-group-btn"> <b class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
			result = result + ' <input type="file" name="attachment" id="fileAttachment"  class="hidden upload">';
			result = result + '</b> </label>';
			result = result + '<input type="text" class="form-control" name="attachment" readonly id="uploadFile1Attachment" placeholder="Files are not selected...">';
			result = result + ' </div>';
			result = result + ' </div>';
			
			result = result+'  <div class="col-md-6" id="docNameDiv" style="display: none;">';
			result = result+'  <label id="documentName"  class="text-primary tbldata-dotdot-vsm pull-left" title="'+fileName+'" ></label>&nbsp;&nbsp;<span id="delete_file" onclick="removeFileNameDiv();"><i class="fa fa-trash removeIcon"></i></span>';
			result = result	+ '<input type="hidden" id="generalExisitingFile" name="generalExisitingFile" value="'+fileName+'">';
			result = result+'  </div>';
			
			
			
			result = result + '<div class="clearfix mr col-md-12">';
			result = result + '<div class="pull-right">';
			result = result + ' <button type="submit" id="update_asset" onclick="checkGeneralAttachment();checkShiftAFileAttachment();checkShiftBFileAttachment();" class="btn btn-sm btn-primary">Add</button>';
			result = result + ' <input class="btn btn-sm btn-default ml10" value="Cancel" onClick="editAssetManagement('+assetId+');" type="button">';
			
			result = result + '</div>';
			result = result + '</div>';
			result = result + '</div>';
			result = result + '</div>';
			
			
			result = result + '</div>';
				  
			result = result + ' </div>';
			result = result  +'</form> ';         
			result = result + '</div>';
			
			$(".user_summary_edit").slideToggle(100);
			$(".error").removeClass("error");
			$('#editUserDiv').find('p.jquery_form_error_message').remove();
			$("#editUserDiv").empty(); 
			$("#editUserDiv").append(result);  
			
			

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

         });
		manageDocs(fileName,shiftAFileName,shiftBFileName);
		
		//upload file script -Desktop-shiftA
		 document.getElementById("uploadBtnShiftA").onchange = function () {
		 $('#shiftAUploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
		 };

		//upload file script -Desktop-shiftB
		 document.getElementById("uploadBtnShiftB").onchange = function () {
		 $('#shiftBUploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
		 };
		 
		 document.getElementById("fileAttachment").onchange = function () {
			 $('#uploadFile1Attachment').val($(this).val().replace(/C:\\fakepath\\/i, ''))
		  };
			
		
		
	    },
	});

}
</script>		

<script>
function manageDocs(fileName,shiftAFileName,shiftBFileName){
	if (fileName != null && fileName != '' && fileName != 'undefined') {
		 $("#uploadFile1Attachment").removeAttr("data-validation");
		 $('#uploadFile1Attachment').attr("data-validation-optional","true");
		 $("#fileAttachmentDiv").hide();
		 $("#docNameDiv").show();
		 $("#documentName").append(fileName);
	}
	
	if (shiftAFileName != null && shiftAFileName != '' && shiftAFileName != 'undefined') {
		 $("#shiftAUploadFile").removeAttr("data-validation");
		 $('#shiftAUploadFile').attr("data-validation-optional","true"); 
		 $("#shiftAFileAttachmentDiv").hide();
		 $("#shiftADocNameDiv").show();
		 $("#shiftADocumentName").append(shiftAFileName);
	}
	
	if (shiftBFileName != null && shiftBFileName != '' && shiftBFileName != 'undefined') {
		 $("#shiftBUploadFile").removeAttr("data-validation");
		 $('#shiftBUploadFile').attr("data-validation-optional","true");
		 $("#shiftBFileAttachmentDiv").hide();
		 $("#shiftBDocNameDiv").show();
		 $("#shiftBDocumentName").append(shiftBFileName);
	}
}

</script>

<script>
function removeFileNameDiv(){
	$("#docNameDiv").hide();
	$("#fileAttachmentDiv").show();
	$("#generalExisitingFile").val('');
	$('#uploadFile1Attachment').attr("data-validation-optional","true");
	$("#uploadFile1Attachment").attr("data-validation", "validatedoc");
}
</script>

<script>
function removeShiftAFileNameDiv(){
	$("#shiftADocNameDiv").hide();
	$("#shiftAFileAttachmentDiv").show();
	$("#shiftAExisitngFile").val('');
	$('#shiftAUploadFile').attr("data-validation-optional","true"); 
	$("#shiftAUploadFile").attr("data-validation", "validatedoc");
}
</script>

<script>
function removeShiftBFileNameDiv(){
	$("#shiftBDocNameDiv").hide();
	$("#shiftBFileAttachmentDiv").show();
	$("#shiftBExisitngFile").val('');
	$('#shiftBUploadFile').attr("data-validation-optional","true");
	$("#shiftBUploadFile").attr("data-validation", "validatedoc");
}
</script>

<script type="text/javascript">
  function checkGeneralAttachment() {
    
    var uploadedFile = document.getElementById('fileAttachment').value;
    if(uploadedFile){
    	var sizeinbytes = document.getElementById('fileAttachment').files[0].size;
        if(sizeinbytes > 500000){
        	 $("input#fileAttachment").after("<p class='jquery_form_error_message'>");
         	   document.getElementById("fileAttachment").setAttribute('record-exist', 'yes');
         	   document.getElementById("fileAttachment").setAttribute('record-exist-errorMsg',"Invalid file size. Should less than 500 kb.");
     	  	 $("#updateAsset_form").click(function() {
     	 		if ($(this).validate(false, validationSettings)) {
     	 		}
     	 	});
        	
        }else{
        	$('#fileAttachment').find('p.jquery_form_error_message').remove();
        	$('input#fileAttachment').removeAttr( "class record-exist record-exist-errormsg");
        	$('input#fileAttachment').attr('class','hidden upload');
        }
    }
    
   
  };
</script>


<script type="text/javascript">
  function checkShiftAFileAttachment() {
    
    var uploadedFile = document.getElementById('uploadBtnShiftA').value;
    if(uploadedFile){
    var sizeinbytes = document.getElementById('uploadBtnShiftA').files[0].size;
    if(sizeinbytes > 500000){
    	 $("input#uploadBtnShiftA").after("<p class='jquery_form_error_message'>");
     	   document.getElementById("uploadBtnShiftA").setAttribute('record-exist', 'yes');
     	   document.getElementById("uploadBtnShiftA").setAttribute('record-exist-errorMsg',"Invalid file size. Should less than 500 kb.");
 	  	 $("#updateAsset_form").click(function() {
 	 		if ($(this).validate(false, validationSettings)) {
 	 		}
 	 	});
    	
    }else{
    	$('#uploadBtnShiftA').find('p.jquery_form_error_message').remove();
    	$('input#uploadBtnShiftA').removeAttr( "class record-exist record-exist-errormsg");
    	$('input#uploadBtnShiftA').attr('class','hidden upload');
    }
    }
   
  };
</script>


<script type="text/javascript">
  function checkShiftBFileAttachment() {
    
    var uploadedFile = document.getElementById('uploadBtnShiftB').value;
    if(uploadedFile){
    var sizeinbytes = document.getElementById('uploadBtnShiftB').files[0].size;
    if(sizeinbytes > 500000){
    	 $("input#uploadBtnShiftB").after("<p class='jquery_form_error_message'>");
     	   document.getElementById("uploadBtnShiftB").setAttribute('record-exist', 'yes');
     	   document.getElementById("uploadBtnShiftB").setAttribute('record-exist-errorMsg',"Invalid file size. Should less than 500 kb.");
 	  	 $("#updateAsset_form").click(function() {
 	 		if ($(this).validate(false, validationSettings)) {
 	 		}
 	 	});
    	
    }else{
    	$('#uploadBtnShiftB').find('p.jquery_form_error_message').remove();
    	$('input#uploadBtnShiftB').removeAttr( "class record-exist record-exist-errormsg");
    	$('input#uploadBtnShiftB').attr('class','hidden upload');
    }
    }
   
  };
</script>

<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#updateAsset_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var frm = $('#updateAsset_form').serializeFormJSON();
        	        var con = JSON.stringify(frm);
        	        con = con.replace(/[{}]/g, "");
        	        
        	        var assetId = $("#assetIdVal").val();
        	        
        	        //It will escape all the special characters
        	        var jsonData = encodeURIComponent(con);
        	        var form = $('#updateAsset_form')[0];
	           		var formData = new FormData(form);
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		
	          		
	          		 $("#update_asset").attr("disabled", "disabled");
	          		 $("#updateLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	          		
	          	      $.ajax({
	     		    	type: "POST",
	     		    	data: formData,
              	        contentType : 'application/json; charset=utf-8',
              	        enctype: 'multipart/form-data',
          	            processData: false,
          	            contentType:false,
              	        dataType : 'json',
	     		    	 url: sourceleadUrl+"/addCategoryAssets?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&assetId="+assetId, 
	     		        success: function(result) {
	     		        	if(result == 'home'){
	     		        		assetDetails(assetId);
	     		        	}
	     			        
	     		        },
	     		    }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#updateAsset_form').showHelpOnFocus();
         $('#updateAsset_form').validateOnBlur(false, validationSettings); 
            
     
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

<!-- <script>
	$("#edit-user").click(function(){
		$(".user_summary_edit").slideToggle(100);
	});
    $("html, #canBTN, .us-btn_a").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
    $("#edit-user, .user_summary_edit").click(function(e) {
        e.stopPropagation();
    });		
</script>	 -->		