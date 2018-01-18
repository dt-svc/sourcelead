<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
function assetNotes(assetId){
		var result = "";
		result = result  +'<div class="clearfix timesheetHead">';
		result = result  +'    <div class="project_tabs_inn_head addUser-org"> '; 
		result  = result + '<div class="success_pop" id="clientNotesSelector" style="display: none;">';
	    result  = result + '<i class="fa fa-check-circle"></i><span id="clientNotesMsgDiv"></span>';
	    result  = result + '</div>';
		result = result + '<ul class="padding_right">';
		result = result  +'    	<a href="javascript:void(0)" onClick="addAssetNotesNew('+assetId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Note</a>                                                            ';
		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result = result  +'  <div class="clearfix"></div> ';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '   <div id="asetsNotesListDiv" ></div>';
		result  = result + '   </div>'; 
		result = result+'	    </div>';
		result = result+'	    </div>'; 
	    //$("#tab-1").empty();
	    $("#tab-1").empty(); 
	    $("#tab-2").empty();
	    $("#tab-2").append(result);  
	    assetNotesList(assetId);    
}
</script>
<script>
function addAssetNotesNew(assetId) {	
	var result = "";
	result = result  +'          <form role="form"  id="notes_Asset" name="notes_Asset" method="post" > ';
	result = result  +'          <input type="hidden" id="assetId" name="assetId" value="'+assetId+'"> ';
	result = result  +'          <input type="hidden" id="noteType" name="noteType" value="Asset"> ';
	result = result  +'          <i class="fa fa-close close-div"  onClick="closeDocPopup();"></i> ';
	
	result = result  +'                <div class="row clearfix"> ';
	result = result+'	                  <div class="col-md-12">';
	result = result+'	                      <textarea rows="2" placeholder="Note" value="" id="noteDescription" name="noteDescription" data-validation="required validate_length length1-5000"  field-name="Note" class="form-control" ></textarea>';
	result = result+'	                  </div>';
	result = result+' <div class="col-md-9 checkBox"> ';
	result = result+'   <input type="checkbox" class="pull-left" value= "Y" id="allowAll" name = "allowAll" checked="checked">';
	result = result + '	                    <p class="pull-left mt5">Allow all users</p>';
	result = result  +'                  </div>';     
	result = result  +'                  <div class="clearfix mt5" id="docAddButton"> ';
	result = result  +'                  <div class="pull-right mt5"> ';
    result = result +'                     <input type="button" value="Add" name="btnAddPr" id="save_note" class="btn btn-sm btn-primary mr" onClick ="storeReqNotes('+assetId+');" />'; 
	result = result  +'                  </div> ';
	result = result  +'                  </div> ';
	result = result  +'                </div>';
	
	result = result  +'                </form>';
	$("#popUpDiv").empty();
	$("#popUpDiv").append(result);
	
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
			
			$(".edit-details").toggleClass("edit-details-active");
			 $(".close-div").click(function(){ 
				 $(this).parent("div.edit-details").removeClass("edit-details-active");
				 });
			 $(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details, .ui-datepicker-prev, .ui-datepicker-next").click(function(e){
				 e.stopPropagation();
			 });
	
	}
</script>
<script>
function closeDocPopup(){
	 $("#popUpDiv").removeClass("edit-details-active");
}
</script>
<script>
function assetNotesList(assetId){
	var urlForm = "";
	    urlForm = "/getAssetNotes?"+tokenParamName+"="+tokenValue+"&assetId="+assetId;
		
	var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
        type : "POST",
         url: sourceleadUrl+urlForm,        
         success :function(data) {
		var output = "";
	    if(data != null && data != 'undefined' && data != "" && data != '[]'){
	    	var json = JSON.parse(data);
	    output  = output + '   <div class="project_tabs_inn_head addUser-org"> ';
	    output  = output + '              <div class="project_tabs_inn_table vendor_wrap_inn_table">';
     	for(var i=0;i<json.length;i++){
       	var report = json[i];
    	if(report.checkBox == 'Y' || (report.checkBox == 'N' && '${SourceLeadWeb_user.id}' == report.userIdStr)){
   	  	output = output+'<div class="comments-div comments-inner commentsNotes clearfix">';
   	    output = output+'    <div class="commentsimg">';
   	    output = output+'    <i class="fa fa-comments"></i>';
   	    output = output+'        <div class="commentsimg-inner">';
   	    if(report.commentAuthorImage != null && report.commentAuthorImage !='' && report.commentAuthorImage !='undefined'){
   	    output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="'+report.commentAuthorImage+'"/>';
   	    }else{
   	   	output = output+'        	<img data-id= "userProfileImage" onerror="predefineProfileImage(this);" src="/resources/images/userprofileImg.jpg"/>';
   	    }
   	    output = output+'        </div>';
   	    output = output+'    </div>';
   	    output = output+'     <div class="comments-content">';
   	    output = output+'         <div class="commentsNotes_inner">';
   	    output = output+'         <p class="comment-by">';
   	    output = output+'            By <span class="text-danger" title = "'+report.userName+'">'+report.userName+'</span>';
   	    if(report.assetName !=null && report.assetName != 'undefined'){
   		 output = output+'          Serial / Port Number: <span class="text-danger" title = "'+report.assetName+'"> '+report.assetName+'</span>';
   	    }
   	    
   	    else if(report.contactName !=null && report.contactName != 'undefined'){
   	    output = output+'          Contact Name: <span class="text-danger" title = "'+report.contactName+'">'+report.contactName+'</span>';
   	    }
   	 	else if(report.contract !=null && report.contract != 'undefined'){
   		output = output+'         Contract:  <span class="text-danger" title = "'+report.contract+'">'+report.contract+'</span>';
   	    }
   	    
   	    else if(report.invoiceId !=null && report.invoiceId != 'undefined'){
   		output = output+'         Invoice Id:  <span class="text-danger" title = "'+report.invoiceId+'">'+report.invoiceId+'</span>';
   	    }
   	    output = output+'            <span class="small pull-right">'+report.createdDateString+'</span></p>';
   	    output = output+'         <p class="comment_text pull-left">'+report.comment+'</p>';
   	    output = output+'         </div>';
   	    output = output+'     </div>';
   	    output = output+'</div>';
    	}
     }
	}else{
		var output ="";
     output = output+"   <div class='dataTables_empty'>"; 
	 output = output+"  </div> ";
}
	output  = output + '              </div>';
	output  = output + '        </div>';
	$("#asetsNotesListDiv").empty(); 
    $("#asetsNotesListDiv").append(output);
 },
      });
}
</script>


<script type="text/javascript">
	function storeReqNotes(assetId){
		if ($('#notes_Asset').validate(false, validationSettings)){
				var frm = $('#notes_Asset').serializeFormJSON();
				var con = JSON.stringify(frm);
				//con = con.replace(/[{}]/g, "");
				var form = $('#notes_Asset')[0];
	      		var formData = new FormData(form);
	      		formData.append("json",con);
	      		
	      		var userId = '${SourceLeadWeb_user.id}';
	      		formData.append("userId",userId);
	      	    var locationCode ='${SourceLeadWeb_locationCode}'; 
	      	    formData.append("locationCode",locationCode);
	      	    
				
				$("#save_note").attr("disabled", "disabled");
         		$("#save_note" ).addClass('button').val('Processing..');
		        var jsonData = encodeURIComponent(con);
				$.ajax({
						type : "POST",
		        	    data: formData,
		        	    contentType : 'application/json; charset=utf-8',
		        	    enctype: 'multipart/form-data',
		    	        processData: false,
		    	        contentType:false,
		        	    dataType : 'json',
						url : sourceleadUrl+"/storeNotes?"+tokenParamName+"="+tokenValue+"",
						success : function(result) {
							closeDocPopup();
							assetNotesList(assetId);
							
						},
					});
					$('.add-comment').toggle(200);
					return true;
		}else {
			return false;	
		}
		}
		$('body').on('blur', '#notes_Asset', function() {
		$("#notes_Asset").showHelpOnFocus();
		$("#notes_Asset").validateOnBlur(false, validationSettings);
		
	});
</script>
<script>
function assetDetails(assetId) {
	$.ajax({
			type : "POST",
			url : sourceleadUrl	+ "/getAssetById?"+tokenParamName+"="+tokenValue+"&assetId="+ assetId,
			success : function(json) {
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
					if (json.harddisk != null && json.harddisk != '' && json.harddisk != 'undefined') {
						hardDisk = json.harddisk;
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
					result = result + '<h1>'+json.categoryName+' Asset details: <a id="edit-user" href="javascript:editAssetManagement('+assetId+');">Edit<i class="glyphicon glyphicon-pencil"></i>&nbsp;&nbsp</a></h1>';
					result = result + '<div class="require_one">';
					
					result = result + '<div class="require_wrap_details_cta"> <b><strong>Category Name:</strong></b> <span>'+json.categoryName+'</span>';
					result = result + '<div class="clearfix"></div>';
					result = result + ' </div>';
					
					result = result + '<div class="require_wrap_details_cta"> <b><strong>Serial Number:</strong></b> <span>'+json.sno+'</span>';
					result = result + '<div class="clearfix"></div>';
					result = result + ' </div>';
					
					result = result + '<div class="require_wrap_details_cta"> <b><strong>Make:</strong></b> <span>'+make+'</span>';
					result = result + '  <div class="clearfix"></div>';
					result = result + '</div>';
					
					result = result + ' <div class="require_wrap_details_cta"> <b><strong>Model:</strong></b> <span>'+model+'</span>';
					result = result + '  <div class="clearfix"></div>';
					result = result + ' </div>';
					
					if (json.fileName != null && json.fileName != '' && json.fileName != 'undefined') {
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Attched File:</strong></b> <span class="tbldata-dotdot-me mr10">'+json.fileName+'</span><a download class="glyphicon glyphicon-download-alt mr10" target="_blank" href=\"'+json.fileUrl+'\" download title="Download"></a>';
		           		result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+json.fileUrl+'\',\''+json.fileName+'\',\''+json.fileContentType+'\');"title="preview"></a>';		           						          
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
					}else{
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Attched File:</strong></b> <span>NO DATA</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
					}
					if(json.monitor != null && json.monitor != '' && json.monitor != 'undefined'){
					result = result + ' <div class="require_wrap_details_cta"> <b><strong>MotherBoard:</strong></b> <span>'+json.monitor+'</span>';
					result = result + ' <div class="clearfix"></div>';
					result = result + '</div>';
					}
					
					result = result + '<div class="require_wrap_details_cta"> <b><strong>Working Status:</strong></b> <span>'+workingStatus+'</span>';
					result = result + ' <div class="clearfix"></div>';
					result = result + ' </div>';
					
					//this block not visible to others category assets
					if (json.categoryName != null && json.categoryName != '' && json.categoryName != 'undefined' && (json.categoryId == 1 || json.categoryId ==2)) {
						result = result + '<div class="require_wrap_details_cta"> <b><strong>Memory:</strong></b> <span>'+memory+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
						
						result = result + '<div class="require_wrap_details_cta"> <b><strong>CPU:</strong></b> <span>'+cpu+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
						
						result = result + ' <div class="require_wrap_details_cta"> <b><strong>Hard Disk:</strong></b> <span>'+hardDisk+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + ' </div>';
						
						if (json.categoryName != null && json.categoryName != '' && json.categoryName != 'undefined' && json.categoryId == 2) {
							result = result + ' <div class="require_wrap_details_cta"> <b><strong>Monitor:</strong></b> <span>'+monitor+'</span>';
							result = result + ' <div class="clearfix"></div>';
							result = result + ' </div>';
						}
						
					}
					//this block not visible to others category assets
					
					result = result + ' <div class="require_wrap_details_cta"> <b><strong>Shfit A:</strong></b> <span>'+shiftAEmpName+'</span>';
						result = result + '  <div class="clearfix"></div>';
						result = result + '</div>';
						
						if (json.shiftAFileUrl != null && json.shiftAFileUrl != '' && json.shiftAFileUrl != 'undefined') {
							result = result + ' <div class="require_wrap_details_cta"> <b><strong>Shfit A Attched File:</strong></b> <span class="tbldata-dotdot-me mr10">'+json.shiftAFileName+'</span><a download class="glyphicon glyphicon-download-alt mr10" target="_blank" href=\"'+json.shiftAFileUrl+'\" download title="Download"></a>';
							result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+json.shiftAFileUrl+'\',\''+json.shiftAFileName+'\',\''+json.shiftAFileContentType+'\');"title="preview"></a>';		           						          
							result = result + '   <div class="clearfix"></div>';
							result = result + '  </div>';
						}
						
						result = result + '  <div class="require_wrap_details_cta"> <b><strong>Shfit B:</strong></b> <span>'+shiftBEmpName+'</span>';
						result = result + '   <div class="clearfix"></div>';
						result = result + '  </div>';
						
						if (json.shiftBFileUrl != null && json.shiftBFileUrl != '' && json.shiftBFileUrl != 'undefined') {
							result = result + ' <div class="require_wrap_details_cta"> <b><strong>Shfit B Attched File:</strong></b> <span class="tbldata-dotdot-me mr10">'+json.shiftBFileName+'</span><a download class="glyphicon glyphicon-download-alt mr10" target="_blank" href=\"'+json.shiftBFileUrl+'\" download title="Download"></a>';
			           		result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+json.shiftBFileUrl+'\',\''+json.shiftBFileName+'\',\''+json.shiftBFileContentType+'\');"title="preview"></a>';		           						          
							result = result + '   <div class="clearfix"></div>';
							result = result + '  </div>';
						}
						
						result = result + ' <div class="require_wrap_details_cta width100percnt description80"> <b><strong>Description:</strong></b> <span>'+description+'</span>';
						result = result + ' <div class="clearfix"></div>';
						result = result + '</div>';
					
					result = result + '  <div class="clearfix"></div>';
					result = result + '<div class="user_summary_edit client_summary_edit" id="editUserDiv" ></div>';
					result = result + '  <div class="clearfix"></div>';
					result = result + '  </div>';
					$("#tab-1").empty();
					$("#tab-2").empty();
					$("#tab-1").append(result);
					$("#tab-1").show();
			}
		});
}
</script>	

<script>
function editAssetManagement(assetId){
	
	$.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/getAssetById?"+tokenParamName+"="+tokenValue+"&assetId="+ assetId,
		success : function(json) {
			
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
			
			if (json.harddisk != null && json.harddisk != '' && json.harddisk != 'undefined') {
				hardDisk = json.harddisk;
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
			
			result = result + '<div class="col-md-3 minHeight50">';
			result = result + ' <input type="text" class="form-control input-sm" name="sno" value="'+json.sno+'" field-name="Serial / Port Number" data-validation="required validate_Space validate_NumberAndAlphabet validate_length length2-200" placeholder="Serial / Port Number *">';
			result = result + '</div>';
			
			result = result + '<div class="assetaddpop-ul col-md-3 minHeight50" id="workingStatusDiv">';
			result = result + '<select class="chosen-select" id="workingStatus" name="workingStatus" data-validation="required" field-name="Working Status">';
			result = result + '<option selected value="">-Select Working Status-</option>';
			result = result + ' <option '+selected1+'>Working</option>';
			result = result + ' <option '+selected2+'>Out Of Service</option>';
			result = result + '</select>';	
			result = result + ' </div>';
			
			result = result + '<div class="col-md-3 minHeight50">';
			result = result + '  <input type="text" class="form-control input-sm" name="make" value="'+make+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="Make">';
			result = result + '</div>';
			
			result = result + ' <div class="col-md-3 minHeight50">';
			result = result + '  <input type="text" class="form-control input-sm" name="model" value="'+model+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="Model">';
			result = result + ' </div>';
			//this block not visible to others category assets
			
			if (json.categoryName != null && json.categoryName != '' && json.categoryName != 'undefined' && (json.categoryId == 1 || json.categoryId ==2)) {
			
				result = result + ' <div class="col-md-3 minHeight50">';
				result = result + '  <input type="text" class="form-control input-sm" name="memory" value="'+memory+'" data-validation-optional="true" data-validation="validate_Space validate_NumberAndAlphabet validate_length length2-50" placeholder="Memory">';
				result = result + '</div>';
				
				result = result + '<div class="col-md-3 minHeight50">';
				result = result + ' <input type="text" class="form-control input-sm" name="cpu" value="'+cpu+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="CPU">';
				result = result + ' </div>';
				
				result = result + ' <div class="col-md-3 minHeight50">';
				result = result + '<input type="text" class="form-control input-sm" name="harddisk" value="'+hardDisk+'" data-validation-optional="true" data-validation="validate_Space validate_NumberAndAlphabet validate_length length2-100" placeholder="Hard Disk">';
				result = result + '</div>';
				
				if (json.categoryName != null && json.categoryName != '' && json.categoryName != 'undefined' && json.categoryId == 2) {
					result = result + ' <div class="col-md-3">';
					result = result + '<input type="text" class="form-control input-sm" name="monitor" value="'+monitor+'" data-validation-optional="true" data-validation="validate_Space validate_length length2-100" placeholder="MotherBoard">';
					result = result + '</div>';
				}
				
			}
			//this block not visible to others category assets
			
			result = result + '<div class="clearfix">';
				result = result + '<h4 class="col-md-12 margin-top0">Shift A</h4>';
				result = result + '</div>';
				result = result + '<div class="col-md-3">';
				result = result + ' <select class="form-control chosen-select" id="shiftAEmp" name="shiftAEmp">';
				result = result + '<option selected value="">-Select Employee-</option>';
					for (var i=0; i<json.employeesList.length; i++)
					{
						empA = json.employeesList[i];
		 	    	   var selected = ''; 
		 	    	   
		 	    	  if(json.shiftAEmployee != null && json.shiftAEmployee != "" && json.shiftAEmployee != 'undefined' && json.shiftAEmployee == empA.id){
		 	    		 selected = 'selected';
		 	    	 }
		 	    	result = result+"<option value='"+empA.id+"' "+selected+">"+empA.user.firstName+" "+empA.user.lastName+"</option>"; 
					};
				result = result + '   </select>';
				result = result + ' </div>';
				
				 result = result + '<div class="col-md-4 minHeight50" id="shiftAFileAttachmentDiv">';
				 result = result + ' <div class="input-group attachfile-error">';
				 result = result + '   <label class="input-group-btn"> <b class="btn btn-primary" onchange="uploadFileShiftAUpdate();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
				 result = result + '     <input type="file" style="display: none;" multiple name="uploadBtn-dskA" id="uploadBtnShiftA" class="upload">';
				 result = result + '          </b> </label>';
				 result = result + '  <input type="text" class="form-control" readonly name="shiftAUploadFile" id="shiftAUploadFile" placeholder="Files are not selected...">';
				 result = result + ' </div>';
				 result = result + '</div>';
				 var blockA = "A";
				 result = result+'  <div class="col-md-4 minHeight50" id="shiftADocNameDiv" style="display: none;">';
				 result = result+'  <label id="shiftADocumentName" name="shiftADocumentName" class="text-primary tbldata-dotdot-vsm pull-left" title="'+shiftAFileName+'" >'+shiftAFileName+'</label>&nbsp;&nbsp;<span id="delete_shiftAFile" onclick="removeDoc('+assetId+',\''+json.shiftAFileUrl+'\',\''+blockA+'\');"><i class="fa fa-trash removeIcon"></i></span>';
				 result = result+ '<input type="hidden" id="shiftAExisitngFile" name="shiftAExisitngFile" value="'+shiftAFileName+'">';
				 result = result+'  </div>';
				 
				
				result = result + '<div class="clearfix">';
				result = result + ' <h4 class="col-md-12">Shift B</h4>';
				result = result + '</div>';
				result = result + '<div class="col-md-3 minHeight50">';
				result = result + '<select class="form-control chosen-select" id="shiftBEmp" name="shiftBEmp">';
				result = result + '<option selected value="">-Select Employee-</option>';
					for (var i=0; i<json.employeesList.length; i++)
					{
						empB = json.employeesList[i];
		 	    	   var selected = '';
		 	    	  if(json.shiftBEmployee != null && json.shiftBEmployee != "" && json.shiftBEmployee != 'undefined' && json.shiftBEmployee == empB.id){
		 	    		 selected = 'selected';
		 	    	 }
		 	    	result = result+"<option value='"+empB.id+"' "+selected+">"+empB.user.firstName+" "+empB.user.lastName+"</option>"; 
					};
				result = result + '</select>';
				result = result + '</div>';
				
				result = result + '<div class="col-md-4 minHeight50" id="shiftBFileAttachmentDiv">';
				result = result + ' <div class="input-group attachfile-error">';
				result = result + '   <label class="input-group-btn"> <b class="btn btn-primary" onchange="uploadFileShiftBUpdate();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
				result = result + '     <input type="file" style="display: none;" name="uploadBtn-dskB" id="uploadBtnShiftB" class="upload">';
				result = result + '    </b> </label>';
				result = result + '  <input type="text" class="form-control" readonly id="shiftBUploadFile" placeholder="Files are not selected...">';
				result = result + '</div>';
				result = result + '</div>';
				var blockB = "B";
				 result = result+'  <div class="col-md-4" id="shiftBDocNameDiv" style="display: none;">';
				 result = result+'  <label id="shiftBDocumentName" name="shiftBDocumentName" class="text-primary tbldata-dotdot-vsm pull-left" title="'+shiftBFileName+'" ></label>&nbsp;&nbsp;<span id="delete_resume" onclick="removeDoc('+assetId+',\''+json.shiftBFileUrl+'\',\''+blockB+'\');"><i class="fa fa-trash removeIcon"></i></span>';
				 result = result+ '<input type="hidden" id="shiftBExisitngFile" name="shiftBExisitngFile" value="'+shiftBFileName+'">';
				 result = result+'  </div>';
				 result = result+'  </div>';
			result = result + ' <div class="col-md-8">';
			result = result + '  <textarea class="form-control" rows="2" name="description" placeholder="Description" data-validation-optional="true" data-validation="validate_Space">'+description+'</textarea>';
			result = result + ' </div>';
			
			result = result + ' <div class="col-md-4" id="fileAttachmentDiv">';
			result = result + '<div class="input-group attachfile-error" >';
			result = result + '<label class="input-group-btn"> <b class="btn btn-primary" onchange="uploadFileUpdate();"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File';
			result = result + ' <input type="file" name="attachment" id="fileAttachment" style="display: none;" class="upload">';
			result = result + '</b> </label>';
			result = result + '<input type="text" class="form-control" name="attachment" readonly id="uploadFile1Attachment" placeholder="Files are not selected...">';
			result = result + ' </div>';
			result = result + ' </div>';
			var blockC = "C";
			result = result+'  <div class="col-md-4" id="docNameDiv" style="display: none;">';
			result = result+'  <label id="documentName"  class="text-primary tbldata-dotdot-vsm pull-left" title="'+fileName+'" ></label>&nbsp;&nbsp;<span id="delete_file" onclick="removeDoc('+assetId+',\''+json.fileUrl+'\',\''+blockC+'\');"><i class="fa fa-trash removeIcon"></i></span>';
			result = result	+ '<input type="hidden" id="generalExisitingFile" name="generalExisitingFile" value="'+fileName+'">';
			result = result+'  </div>';
			
			
			
			result = result + '<div class="clearfix mr col-md-12">';
			result = result + '<div class="pull-right">';
			result = result + ' <input type="button" value="Add" id="update_asset" onclick="updateAsset();" class="btn btn-sm btn-primary">';
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
function removeDoc(assetId,url,block){
	var loginUserId = '${SourceLeadWeb_userId}';
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 		if(block == 'A'){
		    $("#shiftADocNameDiv").hide();
		 	$("#shiftAFileAttachmentDiv").show();
		 	$("#shiftAExisitngFile").val('');
		 	$('#shiftAUploadFile').attr("data-validation-optional","true"); 
		 	$("#shiftAUploadFile").attr("data-validation", "validatedoc"); 
		    }if(block == 'B'){
		    $("#shiftBDocNameDiv").hide();
		 	$("#shiftBFileAttachmentDiv").show();
		 	$("#shiftBExisitngFile").val('');
		 	$('#shiftBUploadFile').attr("data-validation-optional","true");
		 	$("#shiftBUploadFile").attr("data-validation", "validatedoc");
		    }
		 	if(block == 'C'){
		 	$("#docNameDiv").hide();
			$("#fileAttachmentDiv").show();
			$("#generalExisitingFile").val('');
			$('#uploadFile1Attachment').attr("data-validation-optional","true");
			$("#uploadFile1Attachment").attr("data-validation", "validatedoc");
		    }
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/deleteDoc?"+tokenParamName+"="+tokenValue+"&assetId="+assetId+"&url="+url, 	   
   		    success: function(data) {
   		   
   		    },
   		    });		
		}
</script>		 


<!-- <script type="text/javascript">
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
</script> -->


<!-- <script type="text/javascript">
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
 -->

<!-- <script type="text/javascript">
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
</script> -->

<script type="text/javascript">
function updateAsset() {
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
        	 if ($('#updateAsset_form').validate(false, validationSettings)){
        	        var form = $('#updateAsset_form')[0];
	           		var formData = new FormData(form);
	           		var assetId = $("#assetIdVal").val();
	          		 $("#update_asset").attr("disabled", "disabled");
	          		 $("#updateLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
	          	      $.ajax({
	     		    	type: "POST",
	     		    	data: formData,
          	            processData: false,
          	            contentType:false,
              	        dataType : 'json',
	     		    	 url: sourceleadUrl+"/addCategoryAssets?"+tokenParamName+"="+tokenValue+"&assetId="+assetId, 
	     		        success: function(result) {
	     		        	if(result == 'home'){
	     		        		assetDetails(assetId);
	     		        		getAssetList();
	     		        	}
	     			        
	     		        },
	     		    }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
         }
        	 $('body').on('blur', '#addAsset_form', function() {
          		$("#addAsset_form").showHelpOnFocus();
          		$("#addAsset_form").validateOnBlur(false, validationSettings);
          	});
     
</script> 
<script>
function uploadFileUpdate(){
	fileUpload("fileAttachment", "assetDocuments", "updateAsset_form","attachmentUpload");
}
</script>

<script>
function uploadFileShiftAUpdate(){
	fileUpload("uploadBtnShiftA", "assetDocuments", "updateAsset_form","uploadBtndskAUpload");
}
</script>

<script>
function uploadFileShiftBUpdate(){
	fileUpload("uploadBtnShiftB", "assetDocuments", "updateAsset_form","uploadBtndskBUpload");
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