<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<script src="/resources/js/editor.js"></script>
	<link href="/resources/css/editor.css" type="text/css" rel="stylesheet"/>
<script>
$(function() { 
    $("#txtEditor").Editor();
	$("#txtEditor").find(".Editor-editor").data-validation('required');
});
</script>
  <script type="text/javascript">
		window.onload = function () {
			getTicketDescription('${sourceleadTicket.ticketUUID}');
			getUserModules();
			var ticketUUID = '${ticketUUID}';
			if(ticketUUID != '' && ticketUUID != 'undefined' && ticketUUID != null){
				getUserList();
			}
		 };
	</script>

		
<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Add New Issue</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;<a href="<c:url value='/app/sourceleadTicketList'/>"> Issue List</a> &raquo; Add New Issue</div>
  </section>
  <div class="content-area content-new clearfix">
  <form:form id="sourceleadTicket_form" name="sourceleadTicket_form" method="post">
    <div class="row form-horizontal mt myForm">
    <input type="hidden" id="ticketUUID" name="ticketUUID" value="${sourceleadTicket.ticketUUID}">
           <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
		        <div class="form-group">
		          <label class="col-md-3">Issue Title<span class="text-danger paddingLeft">&nbsp;*</span></label>
		          <div class="col-md-9">
		            <input type="text" class="form-control" id="ticketTitle" placeholder="Issue Title" value="${sourceleadTicket.ticketTitle}" name="ticketTitle" field-name="Issue Title" data-validation="required validate_Space validate_length length1-250" >
		          </div>
		        </div>
		      <div class="form-group">
		          <label class="col-md-3">Type<span class="text-danger paddingLeft">&nbsp;*</span></label>
		          <div class="col-md-9">
		            <select class="form-control"  id="titcketType" name="titcketType" field-name="Type" data-validation="required">
		            	<option value="Information" ${sourceleadTicket.ticketType == 'Information' ? 'selected' : ''}>Information</option>
		            	<option value="Bug" ${sourceleadTicket.ticketType == 'Bug' ? 'selected' : ''}>Bug</option>
		            	<option value="Enhancement" ${sourceleadTicket.ticketType == 'Enhancement' ? 'selected' : ''}>Enhancement</option>
		            </select>
		          </div>
		        </div>
                 
                 
                 <c:if test="${SourceLeadWeb_orgLocationId == 1}">
		        <div class="form-group">
		          <label class="col-md-3">Company<span class="text-danger paddingLeft">&nbsp;*</span></label>
		          <div class="col-md-9">
		            <select class="form-control selectpicker" data-live-search="true" id="orgLocationsId" name="orgLocationsId" data-validation="required" field-name="Company" onchange="getUserList();">
		            <option selected value="">Select Company</option>
			            <c:forEach var="org" items="${orgLocationList}">
		                    <option value="${org.id}" ${sourceleadTicket.orgLocationId.id  == org.id ? 'selected' : ''}>${org.orgLocationName}</option>
	                     </c:forEach>
		            </select>
		          </div>
		        </div>
		        
		        <div class="form-group">
		          <label class="col-md-3">User<span class="text-danger paddingLeft">&nbsp;*</span></label>
		          <div class="col-md-9">
		            <select class="form-control selectpicker" placeholder="Select User" data-live-search="true" id="userId" name="userId" data-validation="required" field-name="User" onchange="getUserModules();">
		           <option selected  value="">Select User</option>
		            </select>
		          </div>
		        </div>
		     </c:if>  
                 
                 <div class="form-group">
		          <label class="col-md-3">Module<span class="text-danger paddingLeft">&nbsp;*</span></label>
		          <div class="col-md-9">
		            <select class="form-control selectpicker" data-live-search="true" id="ticketTabs" name="ticketTabs" field-name="Module" data-validation="required">
		            <option selected value="">Select Module</option>
		            </select>
		          </div>
		        </div>
		        <div class="form-group">
		          <label class="col-md-3">URL</label>
		          <div class="col-md-9">
		            <input type="text" class="form-control"  placeholder="URL" value="${sourceleadTicket.ticketURL}" id="ticketUrl" name="ticketUrl" field-name="Url" data-validation="validate_Space" data-validation-optional="true">
		          </div>
		        </div>

			 
		     <div class="form-group">
		     <label class="col-md-3">Attachments</label>
		     <div class="col-md-9">
                  <input type="hidden" name="ticketAttcahmentMaxRowNumber"  id="ticketAttcahmentMaxRowNumber" value="1" />
		          <table width="100%" width="100%" cellpadding="0" cellspacing="0"  id="ticketAttachmentTbl">
		          <tbody>
		          <tr id="divAttachmentTsRow0">
		          <td>
		          <c:if test="${!empty sourceleadTicketsAttachmentsList}">
		         <div id="timesheetDocumentsId">
			         <div class="timesheet_week_comm invoiceDocs invoiceDocsInput">
				         <span>
					         <c:forEach var="attachmentList" items="${sourceleadTicketsAttachmentsList}">
						         <strong id='ticketDocument${attachmentList.id}'>
				    		     <i class=''><a class='glyphicon glyphicon-save' href="${attachmentList.fileURL}" title='download'></a></i>
								 <input type='button' title="${attachmentList.fileName}" value='${attachmentList.fileName}'>
								 <input class='dele' type='button' onClick='deleteDocument("${attachmentList.id}");' >
								 </strong>
					         </c:forEach>
				         </span>
			         </div>
		         </div>
		         </c:if>
		          <div class="timesheet_week_comm slticket width100percnt" >
		          <div id="0ticketdocFileuploadFileGroup"> 
		          <span>
		          <input type="file"  id="0ticketdocFile" field-name="Attachment"  class="timesheetAttachmentValidation" onchange="attachFile(0);">
		          <i class="fa fa-refresh faRefresh mt5" mouseOver="Refresh" title="Clear" onClick="refershDocument()"></i>
		          <i class="fa fa-plus" mouseOver="Add" title="Add" onClick="addTicketAttachment()"></i>
		          </span> </div>
		          </div>
		          </td>
		          </tr>
		          </tbody>
		          </table>
         </div>
          </div>
        </div>
        </div>
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="form-group row">
				<label class="col-md-12">Description<span class="text-danger paddingLeft">&nbsp;*</span></label>
				<div class="col-md-12 txtEditor_p">
                        <textarea id="txtEditor" name="description"   class="form-control newContrtextarea" field-name="Description" data-validation="required"></textarea>
				</div>
			</div>  
		</div>
		<div class="clearfix"></div>
    <div class="form-footer clearfix mt">
      <div class="col-lg-12">
        <div class="pull-right">
          <input type="button" value="Report" class="btn btn-primary" id="ticket_save" onclick="saveTickt();">
          <input type="button" value="Cancel" class="btn btn-default ml" onClick="javascript:window.location.href = '<c:url value='${appPath}/sourceleadTicketList'/>'">
        </div>
      </div>
    </div>
    </form:form>
    </div>
    <div id="ticketDeletePopUp"></div>
</section>


  <div id="divTicketAttachmentTd1" style="display: none; float: left;">
	<div class="timesheet_week_comm slticket width100percnt">
		<div class="" id="REPLACEROWIDticketdocFileuploadFileGroup">
			<span> <input type="file" name='REPLACEROWIDticketdocFile'
				id='REPLACEROWIDticketdocFile'
				onchange='attachFile(REPLACEROWID);'> <i
				class="fa fa-times" mouseOver="Delete" title="Delete"
				onclick="removeTicketAttachmentRow(REPLACEROWID);"></i>
			</span>
		</div>
	</div>
</div>
<script>
	function addTicketAttachment(){  
				var checkMaxTsRowNum = $("#ticketAttcahmentMaxRowNumber").val();
				var tbl = document.getElementById("ticketAttachmentTbl");
				var maxRowNum = parseInt($("input[name = 'ticketAttcahmentMaxRowNumber']").val());
				$("input[name = 'ticketAttcahmentMaxRowNumber']").val(maxRowNum + 1);
				
				var row = tbl.insertRow(maxRowNum);
				row.setAttribute("id", "divAttachmentTicketRow" + maxRowNum);
				
				var cell0 = row.insertCell(0);
				var divAttachmentTdStr = document.getElementById("divTicketAttachmentTd1").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell0.innerHTML = divAttachmentTdStr;
				
	}
	function refershDocument(){
		$("#0ticketdocFile").val('');
		$("#sourceleadTicket_form input[data-id*=0ticketdocFile]").remove();

	}
</script>
<script type="text/javascript">
	function removeTicketAttachmentRow(rowId) {
		$("#sourceleadTicket_form input[data-id*="+rowId+"ticketdocFile]").remove();
		 document.getElementById("divAttachmentTicketRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentTicketRow"+rowId).value= '';
		 var  rId = "divAttachmentTicketRow"+rowId;
		  var r = document.getElementById("ticketAttachmentTbl").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
	
 function attachFile(id){
		fileUpload(""+id+"ticketdocFile", "sourceleadTickets", "sourceleadTicket_form","fileInfo");
	}
 

 function deleteDocument(documentId){ 
 	var result = "";
 	result = result +"	<div class='project_inn_mask'></div>"; 
 	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
 	result = result +"		<p style='text-align: center;'>Do you want to delete a document ?</p>";
 	result = result + ' <div id="reasonForProjectDiv"/>';
 	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteTicketAttachment('+documentId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
 	result = result +"	</div>";
 	$("#ticketDeletePopUp").append(result);
 	$("#remove_user_pop").slideDown(200);
 	$(".project_inn_mask").fadeIn(200);
 }
 
 function deleteTicketAttachment(documentId){
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateTicketAtteachment?${_csrf.parameterName}=${_csrf.token}&attachmentId="+documentId, 	   
			    success: function(result) {
			    	if (result && result.response === "INVALID_DATA") {
		    		    showError(result.responseMessage);
		    		} else if (result.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (result.response === "SUCCESS") {
		    			$("#ticketDocument"+documentId).remove();
				    	$("#ticketDeletePopUp").empty();
					 	$(".project_inn_mask").fadeOut(200);			 			
		    		} else if (result.indexOf('Sign In') !== -1) {
		    			location.replace(sourceleadUrl);
		    		} else {
		    		    showError("Operation failed, please try again");
		    		}
			    },
	     });
 }
 function cancelTermination(){
		$("#ticketDeletePopUp").empty();
		 $(".project_inn_mask").fadeOut(200);
	}
</script>

<script>
function saveTickt(){
	var ee = $("#txtEditor").val($('.Editor-editor').html()); 
	if ($('#sourceleadTicket_form').validate(false, validationSettings)) {
		var sourceleadUrl = '${sourceleadUrl}';
		var formData = getFormData1("sourceleadTicket_form");
		$("#ticket_save").attr("disabled", "disabled");
		$("#ticket_save").addClass('button').val('Processing..');
		$.ajax({
			 type : "POST",
			 data: formData,
	         processData: false,
	         contentType:false, 
			url : sourceleadUrl+ "/storeSourceleadTicket?${_csrf.parameterName}=${_csrf.token}",
			success : function(result) {
				if (result && result.response === "INVALID_DATA") {
	    		    showError(result.responseMessage);
	    		} else if (result.response === "AWKWARD") {
	    		    location.href = "/errorPage";
	    		} else if (result.response === "SUCCESS") {
	    			location.replace(sourceleadUrl+"/sourceleadTicketList");			 			
	    		} else if (result.indexOf('Sign In') !== -1) {
	    			location.replace(sourceleadUrl);
	    		} else {
	    		    showError("Operation failed, please try again");
	    		}
				
			},
		});
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#sourceleadTicket_form', function() {
	$("#sourceleadTicket_form").showHelpOnFocus();
	$("#sourceleadTicket_form").validateOnBlur(false, validationSettings);
});
</script>
<script>
function getUserList(){
	var orgLocationId = $("#orgLocationsId").val();
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getAllUserListByOrgLocationId?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId, 	   
		    success: function(json) {
		    	var result = "";
		    	result = result +'<option  value="">Select User</option>';
	        	  for (var i=0; i<json.length; i++)
  				{
	        		 var report = json[i];
	        		 var userId = '${sourceleadTicket.userId.id}';
	        		 result = result +'<option value='+report.userId+' '+ (userId == report.userId ? "selected" : '') +'>'+report.userName+' ('+report.userRoleName+')</option>';
  				}
		    	 $("#userId").empty();
	        	 $("#userId").append(result);
	        	 $("#userId").selectpicker('refresh');
	        	 getUserModules();
	        	 
		    },
     });
	
}
</script>

<script>
function getUserModules(){
	var orgLocationId = '';
	var userId = '';
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	if(loginUserRole == 'ROLE_SOURCELEAD_ADMIN'){
		orgLocationId = $("#orgLocationsId").val();
		userId = $("#userId").val();
	}
	
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/getSourceleadTicketModules?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&userId="+userId, 	   
		    success: function(json) {
		    	var result = "";
		    	result = result +'<option value="">Select Module</option>';
	        	  for (var i=0; i<json.length; i++)
  				{
	        		 var report = json[i];
	        		 var tabId = '${sourceleadTicket.tabId.id}';
	        		 result = result +'<option value='+report.id+' '+ (tabId == report.id ? "selected" : '') +'>'+report.tabName+'</option>';
  				}
		    	 $("#ticketTabs").empty();
	        	 $("#ticketTabs").append(result);
	        	 $("#ticketTabs").selectpicker('refresh');
	        	 
		    },
     });
	
}
</script>
<script>
function getTicketDescription(ticketUUID){
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getTicketDescription?${_csrf.parameterName}=${_csrf.token}&ticketUUID="+ticketUUID, 	   
			    success: function(result) {
			    	$('#txtEditor').html(result.description);
			    	$('.Editor-editor').html(result.description);
			    }
			    	 
	     });
}
</script>
 <jsp:include page="masterFooter.jsp" />	