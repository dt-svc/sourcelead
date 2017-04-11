<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="resources/js/formvalidator.js"></script>

<section class="content-wraper">
    <div id="loading"></div>
    <section class="content-header clearfix">
          <div class="pull-left">
            <h2>On-board Template</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='onboardingTemplatesList'/>">Templates List</a> &raquo; Create User </div>
    </section>
    
    <div class="content-area content-new clearfix">
      <div class="applicant_accept">
        <!-- <div class="clearfix onboardBtns">
          <div class="btn-group pull-right">
            <button class="btn" id="prevtab" type="button"><i class="fa fa-angle-double-left paddingRight"></i>Prev</button>
            <button class="btn" id="nexttab" type="button">Next<i class="fa fa-angle-double-right paddingLeft"></i></button>
          </div>
        </div> -->
        <div class="tabbable">
          <ul class="nav nav-tabs" id="tab_bar">
            <li class="active" onclick="getTemplateDetails();"> <a href="#details" data-toggle="tab"> <i class="fa fa-th-list paddingRight"></i> <span>Details</span> <br>
              <small>Template details</small> </a> </li>
            <li><a href="#documents" data-toggle="tab" id="templateDocs"> <i class="fa fa-files-o paddingRight"></i> <span>Documents</span> <br>
              <small>Acceptance &amp; Policy Documents</small> </a> </li>
            <li> <a href="#tasks" data-toggle="tab" id="templateTasks"> <i class="fa fa-check-square-o paddingRight"></i> <span>Taks</span> <br>
              <small>List of tasks</small> </a></li>
          </ul>
          <input type="hidden" name="templateId" id="templateId" value=""/>
          <div class="tab-content"> 
            
            <!-- Employee Details -->
            <div class="tab-pane active" id="details">
              <div class="form-horizontal assignTemplate">
                <h4 class="clearfix">Template Details</h4>
                <form:form class="login-form clearfix"  id="onboardTemplate_form"  name="onboardTemplate_form" method="post" > 
                <div class="form-group">
                  <div class="col-md-6" id="templateNameDiv">
                    <label>Template Name <span class="text-danger paddingLeft">*</span></label>
                    <input type="text" class="form-control input-sm" id="templateName" name="templateName" onblur="validateTemplateName();" field-name="Template Name" data-validation="required validate_Space validate_length length2-100">
                    <input type="hidden" name="templateId" id="onboardtemplateId" value=""/>
                  </div>
                  <div class="col-md-6">
                    <label>Description <span class="text-danger paddingLeft">*</span></label>
                    <textarea rows="1" class="form-control input-sm" name="description" field-name="Description" data-validation="required"></textarea>
                  </div>
                </div>
                <div class="form-footer clearfix mt">
                  <div class="pull-right mr">
                    <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='/sourcelead/dashboard/${SourceLeadWeb_orgLocationId}'">
                    <input type="button" value="Save" id="save_button"  onclick="createTemplate('save');" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" id="save_continue_button" onclick="createTemplate('saveAndContinue');" class="btn btn-sm btn-primary">
                  </div>
                </div>
                </form:form>
              </div>
            </div>
            
            <!-- TAB 2 -->
            <div class="tab-pane" id="documents">
              <div class="form-horizontal">
                <form id='templateDocuments_form' name='templateDocuments_form' enctype='multipart/form-data' >
                 <input type="hidden" name="templateId" id="documentstemplateId" value=""/>
                <p><strong>Acceptance Documents</strong> <br>
                  <small class="text-muted">These documents need to be accepted by the new hire as part of acceptance step  - Learn more</small> </p>
                
                <div class="form-group">
                  <label class="col-md-12"> <strong>Policies</strong> <br>
                  </label>
                  <label class="col-md-4 control-label mt10">These policies will be added to this offer:</label>
                     <div id="timesheetDocumentsId" class="timesheetDocs">
                      <div class="col-md-7 commentFile timesheet_week_comm">
				        <span> 
				          <input type='hidden' name='policiesAttcahmentMaxRowNumber'  id='policiesAttcahmentMaxRowNumber' value='1' />
			             <table class='timesheetAttachment' id='policiesAttachmentTable' cellpadding='0' cellspacing='0'>
				          <tr id='divAttachmentTsRow0'>
				             <td><div id='0docFileuploadFileGroup'><input type='file' name='0docFile' id='0docFile' field-name='Attachment' onchange='validateUploadFile(0)'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addPoliciesAttachment()'></i></div></td>
					      </tr>
				        </table>
				        </span>
                      </div>
                      </div>
                </div>
                <div class="clearfix"></div>
                <p><strong>Supporting Information</strong> <br>
                  <small class="text-muted">This is great place to upload a position description, commission structures, photo of the team , their desk or even a view from their new office.</small> </p>
                <div class="form-group">
                  <label class="col-md-4 control-label mt10">These files will be available in the portal:</label>
                  <div id="timesheetDocumentsId" class="timesheetDocs">
                      <div class="col-md-7 commentFile timesheet_week_comm">
				        <span> 
				          <input type='hidden' name='supportingAttcahmentMaxRowNumber'  id='supportingAttcahmentMaxRowNumber' value='1' />
			             <table class='timesheetAttachment' id='supportingAttachmentTable' cellpadding='0' cellspacing='0'>
				          <tr id='divAttachmentTsRow0'>
				             <td><div id='0SupportingdocFileuploadFileGroup'><input type='file' name='0supportingdocFile' id='0supportingdocFile' field-name='Attachment' onchange='validateUploadFile(0)'  class='timesheetAttachmentValidation'><i class='fa fa-plus' onClick='addSupportingAttachment()'></i></div></td>
					      </tr>
				        </table>
				        </span>
                      </div>
                      </div>
                </div>
                <div class="form-footer">
                  <div class="pull-right mr">
                     <input type="button" value="Cancel" class="btn btn-sm btn-default" onClick="self.location='/sourcelead/dashboard/${SourceLeadWeb_orgLocationId}'">
                    <input type="button" value="Save" id="save_button"  onclick="createTemplateDocs('save');" class="btn btn-sm btn-success">
                    <input type="button" value="Save and Continue" id="save_continue_button" onclick="createTemplateDocs('saveAndContinue');" class="btn btn-sm btn-primary">
                  </div>
                </div>
                </form>
              </div>
            </div>
            <div class="tab-pane" id="tasks">
              <div class="clearfix">
                <h4 class="pull-left">Task List</h4>
                <div class="pull-right">
                  <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTask"><i class="fa fa-plus paddingRight"></i>Add Task</button>
                  <!-- Modal -->
                  <div id="addTask" class="modal fade" role="dialog">
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">
                        <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal">&times;</button>
                          <h4 class="modal-title">Add Tasks</h4>
                        </div>
                        <div class="modal-body clearfix"> 
                          
                          <!-- Add New Task Widget -->
                          <div class="col-md-12 clearfix">
                            <form class="form-horizontal clearfix task-widget">
                              <div class="row">
                                <div class="col-md-6">
                                  <label>Subject <span class="text-danger paddingLeft">*</span></label>
                                  <input type="text" class="form-control input-sm" placeholder="Subject">
                                </div>
                                <div class="col-md-6">
                                  <label>Assign To <span class="text-danger paddingLeft">*</span></label>
                                  <select class="chosen-select" multiple data-placeholder="Select Priority" id="priority">
                                    <option>High</option>
                                    <option>Low</option>
                                    <option>Mediam</option>
                                  </select>
                                </div>
                                <div class="col-md-6 dropdown-select">
                                  <label>Priority</label>
                                  <select class="form-control">
                                    <option selected disabled>-- Select Priority --</option>
                                    <option>High</option>
                                    <option>Low</option>
                                    <option>Mediam</option>
                                  </select>
                                </div>
                                <div class="col-md-6">
                                  <div class="row">
                                    <div class="col-md-12">
                                      <label><strong>Due after:</strong><span class="text-danger paddingLeft">*</span></label>
                                      <div class="input-group hidden"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input class="form-control" value="" name="Date" id="datepicker2" type="text">
                                      </div>
                                      
                                      <!-- Form inline Due after -->
                                      
                                      <div class="row">
                                        <div class="col-md-3 paddingRight">
                                          <label class="small">Months</label>
                                          <br>
                                          <input class="form-control input-sm" type="text">
                                        </div>
                                        <div class="col-md-3 paddingRight paddingLeft">
                                          <label class="small">Days</label>
                                          <br>
                                          <input class="form-control input-sm" type="text">
                                        </div>
                                        <div class="col-md-3 paddingRight paddingLeft">
                                          <label class="small">Hours</label>
                                          <br>
                                          <input type="text" class="form-control input-sm">
                                        </div>
                                        <div class="col-md-3 paddingRight paddingLeft">
                                          <label class="small">Minutes</label>
                                          <br>
                                          <input type="text" class="form-control input-sm">
                                        </div>
                                      </div>
                                      
                                      <!-- Form inline Due after --> 
                                      
                                    </div>
                                    <div class="col-md-6 hidden">
                                      <label>Time</label>
                                      <div class="input-group"> <span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
                                        <input class="form-control" value="" name="Time" id="example" type="text">
                                      </div>
                                    </div>
                                  </div>
                                </div>
                                <div class="col-md-6 dropdown-select">
                                  <label>Repeat</label>
                                  <select class="form-control input-sm">
                                    <option selected disabled>-- Select Repeat --</option>
                                    <option>Never</option>
                                    <option>Daily</option>
                                    <option>Weekly</option>
                                    <option>Monthly</option>
                                    <option>Yearly</option>
                                  </select>
                                </div>
                                <div class="col-md-6">
                                  <div class="mt">
                                    <label class="checkbox-inline">
                                      <input type="checkbox" name="chkbox">
                                      Email </label>
                                    <label class="checkbox-inline">
                                      <input type="checkbox" name="chkbox">
                                      Notification </label>
                                  </div>
                                </div>
                                <div class="col-md-6">
                                  <label>Description</label>
                                  <textarea rows="2" placeholder="Description" class="form-control"></textarea>
                                </div>
                                <div class="clearfix"></div>
                                <div class="clearfix mt col-md-12">
                                  <div class="pull-right">
                                    <input type="button" class="btn btn-primary btn-sm" value="Save">
                                    <input type="button" class="btn btn-default btn-sm ml10" value="Cancel">
                                  </div>
                                </div>
                              </div>
                            </form>
                          </div>
                          <!-- Add New Task Widget --> 
                          
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="clearfix taskwidget_inner">
                <div class="media-body">
                  <div class="clearfix taskheder">
                    <label> <span class="paddingRight">Subject:</span> <strong class="text-primary">Account Setup</strong> </label>
                    <label class="small pull-right text-muted"> <span>Due after 15 days</span> </label>
                  </div>
                  <ul class="list-inline list-description">
                    <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                    <li><span>Priority:</span> High</li>
                    <li><span>Repeat:</span> Never</li>
                  </ul>
                </div>
              </div>
              <div class="clearfix taskwidget_inner">
                <div class="media-body">
                  <div class="clearfix taskheder">
                    <label> <span class="paddingRight">Subject:</span> <strong class="text-primary">Desktop arriange</strong> </label>
                    <label class="small pull-right text-muted"> <span>Due after 20 days</span> </label>
                  </div>
                  <ul class="list-inline list-description">
                    <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                    <li><span>Priority:</span> High</li>
                    <li><span>Repeat:</span> Never</li>
                  </ul>
                </div>
              </div>
              <div class="clearfix taskwidget_inner">
                <div class="media-body">
                  <div class="clearfix taskheder">
                    <label> <span class="paddingRight">Subject:</span> <strong class="text-primary">Providing access</strong> </label>
                    <label class="small pull-right text-muted"> <span>Due after 1 month</span> </label>
                  </div>
                  <ul class="list-inline list-description">
                    <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                    <li><span>Priority:</span> High</li>
                    <li><span>Repeat:</span> Never</li>
                  </ul>
                </div>
              </div>
              <div class="clearfix taskwidget_inner">
                <div class="media-body">
                  <div class="clearfix taskheder"> 
                    <!--<label>Account Setup, Desk top Arriange, Providing access to the Candidate</label>-->
                    <label> <span class="paddingRight">Subject:</span> <strong class="text-primary">Account Setup</strong> </label>
                    <label class="small pull-right text-muted"> <span>Due after 2 months</span> </label>
                  </div>
                  <ul class="list-inline list-description">
                    <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                    <li><span>Priority:</span> High</li>
                    <li><span>Repeat:</span> Never</li>
                  </ul>
                </div>
              </div>
              <div class="clearfix taskwidget_inner">
                <div class="media-body">
                  <div class="clearfix taskheder"> 
                    <!--<label>Account Setup, Desk top Arriange, Providing access to the Candidate</label>-->
                    <label> <span class="paddingRight">Subject:</span> <strong class="text-primary">Account Setup</strong> </label>
                    <label class="small pull-right text-muted"> <span>Due after 10 days</span> </label>
                  </div>
                  <ul class="list-inline list-description">
                    <li><span>Assign To:</span> Pramodkumar Mahagen</li>
                    <li><span>Priority:</span> High</li>
                    <li><span>Repeat:</span> Never</li>
                  </ul>
                </div>
              </div>
              <div class="form-footer clearfix mt">
                <div class="pull-right mr">
                  <input type="button" value="Cancel" class="btn btn-sm btn-default">
                  <input type="button" value="Finish" class="btn btn-sm btn-primary">
                </div>
              </div>
            </div>
          </div>
          <!-- STYLED IN FORMS.CSS --> 
        </div>
      </div>
    </div>
  </section>
  
  
<script> 
	$(document).ready(function(){
		document.getElementById("templateDocs").disabled = true;
		document.getElementById("templateTasks").disabled = true;
		
		var templateId = '${templateId}';
		if(templateId != null && templateId != '' && templateId != 'undefined'){
			$("#templateId").val(templateId);
  	        $("#onboardtemplateId").val(templateId);
  	        $("#documentstemplateId").val(templateId);
  	        
  	      document.getElementById("templateDocs").disabled = false;
		}
		
		getTemplateDetails();
	});
</script>

<div id="divAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDdocFileuploadFileGroup'>
	  			<input type='file' name='REPLACEROWIDdocFile' id='REPLACEROWIDdocFile' class="timesheetAttachmentValidation" onchange='validateUploadFile(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removePoliciesAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>

<div id="divSupportingAttachmentTd1" style="display: none; float: left;">
			<div id='REPLACEROWIDSupportingdocFileuploadFileGroup'>
	  			<input type='file' name='REPLACEROWIDsupportingdocFile' id='REPLACEROWIDsupportingdocFile' class="timesheetAttachmentValidation" onchange='validateSupportingUploadFile(REPLACEROWID)'> 
	  			<i class="fa fa-times" onclick="removeSupportingAttachmentRow(REPLACEROWID);"></i>
	  		</div>
</div>

<script>
function addPoliciesAttachment(){  
			var checkMaxTsRowNum = $("#policiesAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("policiesAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'policiesAttcahmentMaxRowNumber']").val());
			$("input[name = 'policiesAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentPoliciesRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}			
</script>
	
<script type="text/javascript">
	function removePoliciesAttachmentRow(rowId) {
		 document.getElementById("divAttachmentPoliciesRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentPoliciesRow"+rowId).value= '';
		 var  rId = "divAttachmentPoliciesRow"+rowId;
		  var r = document.getElementById("policiesAttachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>


<script>
function addSupportingAttachment(){  
			var checkMaxTsRowNum = $("#supportingAttcahmentMaxRowNumber").val();
			var tbl = document.getElementById("supportingAttachmentTable");
			var maxRowNum = parseInt($("input[name = 'supportingAttcahmentMaxRowNumber']").val());
			$("input[name = 'supportingAttcahmentMaxRowNumber']").val(maxRowNum + 1);
			
			
			var row = tbl.insertRow(maxRowNum);
			row.setAttribute("id", "divAttachmentSupportingRow" + maxRowNum);
			
			
			var cell0 = row.insertCell(0);
			var divAttachmentTdStr = document.getElementById("divSupportingAttachmentTd1").innerHTML;
			divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
			cell0.innerHTML = divAttachmentTdStr;
			
			
}			
</script>
	
<script type="text/javascript">
	function removeSupportingAttachmentRow(rowId) {
		 document.getElementById("divAttachmentSupportingRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentSupportingRow"+rowId).value= '';
		 var  rId = "divAttachmentSupportingRow"+rowId;
		  var r = document.getElementById("supportingAttachmentTable").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
</script>


<script type="text/javascript">
function validate_fileupload(fileName)
{

    var allowed_extensions = new Array("ppt", "pptx", "pps", "ppsx", "doc", "docx", "pdf", "rtf", "odt", "xls", "xlsx", "csv", "zip", "txt", "jpg","jpeg","bmp","png","gif","eps","tiff","TGA","psd","svg","msg","wpd","wps");
    var file_extension = fileName.split('.').pop(); 

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i] != null && allowed_extensions[i] != '' && allowed_extensions[i] != 'undefined' && allowed_extensions[i]==file_extension.toLowerCase())
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>

<script type="text/javascript">
function validateSupportingUploadFile(id)
{
	 var selectedFile = $('#'+id+'supportingdocFile').val();
	  if(validate_fileupload(selectedFile)){
		   var docSize = $('#'+id+'supportingdocFile')[0].files[0].size;
	       $("#"+id+"SupportingdocFileuploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  $('#'+id+'SupportingdocFileuploadFileGroup').find('p.jquery_form_error_message').remove();
	    	  $('#'+id+'SupportingdocFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  $('#'+id+'supportingdocFile').attr('record-exist','yes');
			  $('#'+id+'supportingdocFile').attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#'+id+'supportingdocFile').removeAttr( "record-exist record-exist-errormsg");
   		 $('#'+id+'SupportingdocFileuploadFileGroup').find('p.jquery_form_error_message').remove(); 
	      }
	  }
	  else{
		      $('#'+id+'SupportingdocFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		      $('#'+id+'SupportingdocFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
			  $('#'+id+'supportingdocFile').attr('record-exist','yes');
			  $('#'+id+'supportingdocFile').attr('record-exist-errorMsg','Invalid Format'); 
	  }
	
}
</script>


<script type="text/javascript">
function validate_fileupload(fileName)
{

    var allowed_extensions = new Array("ppt", "pptx", "pps", "ppsx", "doc", "docx", "pdf", "rtf", "odt", "xls", "xlsx", "csv", "zip", "txt", "jpg","jpeg","bmp","png","gif","eps","tiff","TGA","psd","svg","msg","wpd","wps");
    var file_extension = fileName.split('.').pop(); 

    for(var i = 0; i <= allowed_extensions.length; i++)
    {
        if(allowed_extensions[i] != null && allowed_extensions[i] != '' && allowed_extensions[i] != 'undefined' && allowed_extensions[i]==file_extension.toLowerCase())
        {
            return true; // valid file extension
        }
    }

    return false;
}
</script>

<script type="text/javascript">
function validateUploadFile(id)
{
	 var selectedFile = $('#'+id+'docFile').val();
	  if(validate_fileupload(selectedFile)){
		   var docSize = $('#'+id+'docFile')[0].files[0].size;
	       $("#"+id+"docFileuploadFileGroup").find('p.jquery_form_error_message').remove();
	      if(docSize > 2097152){
	    	  $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
	    	  $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
			  $('#'+id+'docFile').attr('record-exist','yes');
			  $('#'+id+'docFile').attr('record-exist-errorMsg','Limit is exceeded than 2MB'); 
	      }else{
	    	 $('input#'+id+'docFile').removeAttr( "record-exist record-exist-errormsg");
   		 $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove(); 
	      }
	  }
	  else{
		      $('#'+id+'docFileuploadFileGroup').find('p.jquery_form_error_message').remove();
		      $('#'+id+'docFileuploadFileGroup').find('i').after("<p class='jquery_form_error_message'>Invalid Format</span>");
			  $('#'+id+'docFile').attr('record-exist','yes');
			  $('#'+id+'docFile').attr('record-exist-errorMsg','Invalid Format'); 
	  }
	
}
</script>

<script>
function createTemplate(saveType){
	
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  if ($('#onboardTemplate_form').validate(false, validationSettings)){ 
		    var frm = $('#onboardTemplate_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        
	        var sourceleadUrl ='${sourceleadUrl}';
	        
	       
	        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	        $.ajax({
		    	type: "POST",
		    	 url: sourceleadUrl+"/createNewOnBoardingTemplate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(templateId) {
		        	
		        	if(saveType == 'saveAndContinue'){
		        		var $tabs = $('.tabbable li');
		     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
		     	        document.getElementById("templateDocs").disabled = false;
		        	}
		        	
	     	        $("#templateId").val(templateId);
	     	        $("#onboardtemplateId").val(templateId);
	     	        $("#documentstemplateId").val(templateId);
	     	        $("#loading").removeAttr("style");
		        },
		    }); 
	        return true;
		
	  }else{
		return false;
	  }
}

$('body').on('blur', '#onboardTemplate_form', function() {
	$("#onboardTemplate_form").showHelpOnFocus();
	$("#onboardTemplate_form").validateOnBlur(false, validationSettings);  
});
</script>

<script>
function createTemplateDocs(saveType){
	var validationSettings = {
	        errorMessagePosition : 'element',
	       borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
         }; 
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  var frm = $('#templateDocuments_form').serializeFormJSON();
      var con = JSON.stringify(frm);
      con = con.replace(/[{}]/g, "");
      
      //It will escape all the special characters
      var jsonData = encodeURIComponent(con);
      
	   var form = $('#templateDocuments_form')[0];
	   var formData = new FormData(form); 
	
   $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	$.ajax({
        type : "POST",
	      data: formData,
	      contentType : 'application/json; charset=utf-8',
	      enctype: 'multipart/form-data',
        processData: false,
        contentType:false,
	      dataType : 'json',
    	  url: sourceleadUrl+"/saveOnboardingTemplateDocuments?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,		
	      success :function(result) {
	    	  
	    	  if(saveType == 'saveAndContinue'){
	        		var $tabs = $('.tabbable li');
	     	        $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
	     	        document.getElementById("templateTasks").disabled = false;
	        	}
	    	  $("#loading").removeAttr("style");
	    	  
	      }
    	  
  });
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

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 


<script>
function getTemplateDetails(){
	var templateId = document.getElementById("templateId").value;
	if(templateId){
		var sourceleadUrl ='${sourceleadUrl}';
        $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getOnboardTemplateById?${_csrf.parameterName}=${_csrf.token}&templateId="+templateId, 
	        success: function(data) {
	        	 var json = JSON.parse(data);
	        	
	        	 $("input[name=templateName]").val(json['templateName']);
	        	 $("textarea[name=description]").val(json['description']);
		        
	        },
	    }); 
	}
}
</script>


<script>
function validateTemplateName(){
	var templateId = document.getElementById("templateId").value;
	var templateName = document.getElementById("templateName").value;
	
	if(templateName){
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
	    	type: "POST",
	    	url : sourceleadUrl+"/validateOnboardingTemplateName?${_csrf.parameterName}=${_csrf.token}&templateName="+templateName+"&templateId="+templateId, 
	        success: function(result) {
	        	if (result == 'invalid') {
	        		$("#save_button").attr("disabled", "disabled");
	        		$("#save_continue_button").attr("disabled", "disabled");
	        		$('#templateNameDiv').find('p.jquery_form_error_message').remove();
	        		$("input#templateName").after( "<p class='jquery_form_error_message'>" + templateName + " is Already Existed</span>");
					document.getElementById("templateName").setAttribute( 'record-exist', 'yes');
					document.getElementById("templateName").setAttribute( 'record-exist-errorMsg', templateName +"  already existed");
	        	}else{
	        		$('input#templateName').removeAttr("class record-exist record-exist-errormsg");
					$('input#templateName').attr('class','valid form-control');
					$('#templateNameDiv').find('p.jquery_form_error_message').remove();
					
	        		$("#save_button").removeAttr("disabled")
	        		$("#save_continue_button").removeAttr("disabled")
	        		
	        	}
	        }
		 });
	}
}
</script>

  
  
  <script>
  $(function() {
    $( "#startDate" ).datepicker({
	  changeMonth: true,
      changeYear: true,
    });
  });
    $(function() {
    $( "#endDate" ).datepicker({
	  changeMonth: true,
      changeYear: true,
    });
  });
    $(function() {
    $( "#dueDate" ).datepicker({
	  changeMonth: true,
      changeYear: true,
    });
  });
    
    
    // upload file script
    document.getElementById("uploadBtn").onchange = function () {
    document.getElementById("uploadFile").value = this.value;
	};
  </script> 
<script type="text/javascript">
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
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script> 
<script>
    $("#uasrch").selectpicker('refresh');
</script> 


<!-- <script>
    // Script for tabs next pre button
var $tabs = $('.tabbable li');

$('#prevtab').on('click', function() {
    $tabs.filter('.active').prev('li').find('a[data-toggle="tab"]').tab('show');
});

$('#nexttab').on('click', function() {
    $tabs.filter('.active').next('li').find('a[data-toggle="tab"]').tab('show');
});
</script> --> 

<script>
    //File upload
    $('#file-fr').fileinput({
        language: 'fr',
        uploadUrl: '#',
        allowedFileExtensions: ['jpg', 'png', 'gif']
    });
    $('#file-es').fileinput({
        language: 'es',
        uploadUrl: '#',
        allowedFileExtensions: ['jpg', 'png', 'gif']
    });
    $("#file-0").fileinput({
        'allowedFileExtensions': ['jpg', 'png', 'gif']
    });
    $("#file-1").fileinput({
        uploadUrl: '#', // you must set a valid URL here else you will get an error
        allowedFileExtensions: ['jpg', 'png', 'gif'],
        overwriteInitial: false,
        maxFileSize: 1000,
        maxFilesNum: 10,
        //allowedFileTypes: ['image', 'video', 'flash'],
        slugCallback: function (filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
    });
    /*
     $(".file").on('fileselect', function(event, n, l) {
     alert('File Selected. Name: ' + l + ', Num: ' + n);
     });
     */
    $("#file-3").fileinput({
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-primary btn-lg",
        fileType: "any",
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        overwriteInitial: false,
        initialPreviewAsData: true,
        initialPreview: [
            "http://lorempixel.com/1920/1080/transport/1",
            "http://lorempixel.com/1920/1080/transport/2",
            "http://lorempixel.com/1920/1080/transport/3",
        ],
        initialPreviewConfig: [
            {caption: "transport-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1},
            {caption: "transport-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
            {caption: "transport-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3},
        ],
    });
    $("#file-4").fileinput({
        uploadExtraData: {kvId: '10'}
    });
    $(".btn-warning").on('click', function () {
        var $el = $("#file-4");
        if ($el.attr('disabled')) {
            $el.fileinput('enable');
        } else {
            $el.fileinput('disable');
        }
    });
    $(".btn-info").on('click', function () {
        $("#file-4").fileinput('refresh', {previewClass: 'bg-info'});
    });
    /*
     $('#file-4').on('fileselectnone', function() {
     alert('Huh! You selected no files.');
     });
     $('#file-4').on('filebrowse', function() {
     alert('File browse clicked for #file-4');
     });
     */
    $(document).ready(function () {
        $("#test-upload").fileinput({
            'showPreview': false,
            'allowedFileExtensions': ['jpg', 'png', 'gif'],
            'elErrorContainer': '#errorBlock'
        });
        $("#kv-explorer").fileinput({
            'theme': 'explorer',
            'uploadUrl': '#',
            overwriteInitial: false,
            initialPreviewAsData: true,
            initialPreview: [
                "http://lorempixel.com/1920/1080/nature/1",
                "http://lorempixel.com/1920/1080/nature/2",
                "http://lorempixel.com/1920/1080/nature/3",
            ],
            initialPreviewConfig: [
                {caption: "nature-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1},
                {caption: "nature-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
                {caption: "nature-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3},
            ]
        });
        /*
         $("#test-upload").on('fileloaded', function(event, file, previewId, index) {
         alert('i = ' + index + ', id = ' + previewId + ', file = ' + file.name);
         });
         */
    });

    </script>

  <jsp:include page="../masterFooter.jsp" />