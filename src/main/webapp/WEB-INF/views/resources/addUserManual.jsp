<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/summernote-new.css" rel="stylesheet" type="text/css">
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/summernote.min.js"></script>

<script>
$(function(){
	levelFun();
// 	getLevel2Titles();
});

</script>

<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2> Add User Manuals</h2>
          </div>
         <%--  <a href="${appPath}/addUserManuals" class="pull-right btn btn-primary btn-sm mb">Add</a> --%>
          <%-- <a href="${appPath}/applicantDeclineReports" title="Grid View" id="GView" class="btn btn-default " ><i class="fa fa-pie-chart"></i></a>
          --%> 
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; User Manuals </div>
        </section>
        
        <div class="content-area clearfix">
        <div id="userManualsloading"></div>
     <div class="row clearfix mt">
         <div class="clearfix addFields">
         <form:form id="user_manuals_form" name="user_manuals_form"  method="post" >
         
			<div class="clearfix">

			<div class="col-md-6">
			<div class="row">
			<label  class="col-md-3">Level <span class="text-danger paddingLeft">*</span></label>
			<div class="dropdown-select col-md-9">
			<c:choose>
			<c:when test="${!empty levelName}">
			<select onChange="levelFun();" id="levelID" name="levelName" field-name="level" data-validation="required">
				<option value="">-- Select Level --</option>
				<option value='Level-1' ${levelName == 'Level-1' ? 'selected' : ''}>Level-1</option>
				<option  value='Level-2' ${levelName == 'Level-2' ? 'selected' : ''}>Level-2</option>
				<option   value='Level-3'${levelName == 'Level-3' ? 'selected' : ''}>Level-3</option>
				</select>
			</c:when>
			<c:otherwise>
			<c:if test = "${empty levelName}">
				<select onChange="levelFun();" id="levelID" name="levelName" field-name="level" data-validation="required">
				<option value="">-- Select Level --</option>
				<option vlaue="level-1">Level-1</option>
				<option vlaue="level-2">Level-2</option>
				<option vlaue="level-3">Level-3</option>
				</select>
				</c:if>
			</c:otherwise>
			</c:choose>
			</div>
				</div>	
			</div>
			<div class="col-md-6">
			<div class="row">
				<label class="col-md-3">Title <span class="text-danger paddingLeft">*</span></label>
				<div class="col-md-9" id="errMsg">
				<c:choose>
				<c:when test="${!empty userManualLevel.title}">
				        <input type="text" name="title" id="title" class="form-control" field-name="title" onblur="validateUserManualTitle();" data-validation="required validate_length length1-500" value="${userManualLevel.title}">
				 </c:when>
				    <c:otherwise>
				        <input type="text" name="title" id="title" class="form-control" field-name="title" onblur="validateUserManualTitle();" data-validation="required validate_length length1-500" value="">
				 </c:otherwise>
				 </c:choose>
					</div>
				</div>
			</div>
			
			<!--  Level2 div Starts  -->
			
				<div class="col-md-6 mt" id="level2Div"  style="display:none;">
				<div class="row">
              <label class="col-md-3">Level1<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 chooRadius" id="errorClient">
                  <select class="chosen-select form-control" id="level1Title" onchange="getLevel2Titles();" name="level1Title" data-validation="required"  field-name="level1Title">
                    <option value=''>Select Level</option>
                  </select>
              </div>
          </div>                      
              </div> 
			
			<!--  Level2 div ends  -->
			
			
			<!--  Level3 div Starts  -->
			<div class="clearfix"></div>
			<div class="mt" id="level3Div" style="display:none;">
			
			<div class="col-md-6">
			<div class="row form-group">
				<label class="col-md-3">Level2<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-9 chooRadius" id="errorselect2">
                  <select class="chosen-select form-control" id="level2Title"  name="level2Title" data-validation="required"  field-name="level2Title">
                    <option value=''>Select Level</option>
                  </select>
          </div>
				</div>
			</div>
			
			<div class="col-md-6">
			<div class="row form-group">
				<label class="col-md-3">Video URL </label>
				<div class="col-md-9">
				          <input type="text" name="urlName" id="youTubeUrl" onblur="validateYouTubeUrl();" value="${uploadUrl}" class="form-control">
							<h6 id="youtubeUrlId" style="display:none; color:red"> Invalid URL</h6>
				</div>
				</div>
			</div>

			<%-- <div class="col-md-6">
			<div class="row form-group">
				<label class="col-md-3">Publish On <span class="text-danger paddingLeft">*</span></label>
				<div class="col-md-9">
					<input type="text" name="publishOn"  value="${publishon}" class="form-control calendar" id="publishOn" >
				</div>
				</div>
			</div> --%>
			
			<div class='clearfix'></div>
			<div class="col-md-6">
			<div class="form-group row">
		     <label class="col-md-3">Attachments</label>
		     <div class="col-md-9" >
                  <input type="hidden" name="UserManualAttchMaxRowNumber"  id="UserManualAttchMaxRowNumber" value="1" />
		          <table width="100%" width="100%" cellpadding="0" cellspacing="0"  id="UserManualAttchTb1">
		          <tbody>
		          <tr id="divAttachmentTsRow0">
		          <td>
		          <c:if test="${!empty userManualAttachmentList}">
		         <div id="timesheetDocumentsId">
			         <div class="timesheet_week_comm invoiceDocs invoiceDocsInput">
				         <span>
					         <c:forEach var="attachmentList" items="${userManualAttachmentList}">
						         <strong id='ticketDocument${attachmentList.id}'>
				    		     <i class=''><a class='glyphicon glyphicon-save' href="${attachmentList.fileUrl}" title='download'></a></i>
								 <input type='button' value='${attachmentList.fileName}'>
								 <input class='dele' type='button' onClick='deleteDocument("${attachmentList.id}");' >
								 </strong>
					         </c:forEach>
				         </span>
			         </div>
		         </div>
		         </c:if> 
		          <div class="timesheet_week_comm slticket uploadfileError positionRelative" >
		          <div id="0ticketdocFileuploadFileGroup"> 
		          <span id="docErrorMsg">
		          <input type="file"  id="0ticketdocFile" field-name="Attachment"  class="timesheetAttachmentValidation" onchange="attachFile(0);" >
		          <!-- data-validation="validate_Space validateimage" -->
		          <i class="fa fa-refresh faRefresh mt5" mouseOver="Refresh" title="Clear" onClick="refershDocument()"></i>
		          <i class="fa fa-plus" mouseOver="Add" title="Add" onClick="addTicketAttachment()"></i>
		          </span>
		           </div>
		          </div>
		          </td>
		          </tr>
		          </tbody>
		          </table>
         </div>
          </div>
          
			<div class="clearfix"></div>
			</div>
			</div>
			<!--  Level3 Div Ends -->

			
			<div class="clearfix"></div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
			<div class="form-group row">
				<label class="col-md-12">Description</label>
				<div class="col-md-12">
				
				<c:choose>
				<c:when test="${!empty userManualLevel.description}">
				        <textarea rows="2" class="form-control" name="description" id="summernote" field-name="Description" >${userManualLevel.description}</textarea></c:when>
				    <c:otherwise>
				        <textarea rows="2" class="form-control" name="description" id="summernote" field-name="Description" ></textarea> </c:otherwise>
				 </c:choose>
					<div id="editor"></div>
				</div>
			</div>  
		</div>
			
			</div>
			
			
			</div>
			</div>
			
      		<div class="clearfix"></div>
      		<div class="mt clearfix mb">
      			<div class="pull-right">
      			<input type="hidden" name="id" id="id" value="${userManualLevel.id}"/>
      				<button type="button" name="save"  value="save" id="save_title" class="btn btn-primary btnsStyles" onClick="saveLevels('save');">Save</button>
      				 <button type="button" class="btn btn-primary btnsStyles" data-dismiss="modal" onClick="javascript:window.location.href = '<c:url value='userManuals'/>'">Cancel</button>
					<button type="button" name="publish" value="publish" id="saveandcontinue" class="btn btn-primary btnsStyles" onClick="saveLevels('publish');">Save &amp; Publish</button>	
      			</div>
      		</div>
      			
      			
      </form:form>
      </div>
	</div>
        </div>
        <div id="userManualDeletePopUp"></div>
 </section>


<script>
function deleteDocument(documentId){ 
	var result = "";
	result = result +" <div class='project_inn_mask'></div>"; 
	result = result +" <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +" <p style='text-align: center;'>Do you want to delete a document ?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +' <b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteManualAttachment('+documentId+')"><input class="u_can_color" type="button" value="Cancel" onClick="cancelTermination();"></b>';
	result = result +" </div>";
	$("#userManualDeletePopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
	}
	
	function deleteManualAttachment(documentId) {
		$.ajax({
					type : "POST",
					url : sourceleadUrl + "/deleteManualAttachment?${_csrf.parameterName}=${_csrf.token}&attachmentId="+ documentId,
					success : function(response) {
						
						if (response && (response.response === "INVALID_DATA" || response.response === "INVALID_USER")) {
			    		    showError(response.responseMessage);
			    	  	} else if (response.response === "AWKWARD") {
			    		    location.href = "/errorPage";
			    		} else if (response.response === "SUCCESS") {
			    			$("#ticketDocument" + documentId).remove();
							$("#userManualDeletePopUp").empty();
							$(".project_inn_mask").fadeOut(200);
			    		}else if (response.indexOf('Sign In') !== -1) {
			    			location.replace(sourceleadUrl);
			    		} else {
			    		    showError("Operation failed, please try again");
			    		}
						
					},
				});
	}
	function cancelTermination() {
		$("#userManualDeletePopUp").empty();
		$(".project_inn_mask").fadeOut(200);
	}
</script>


<script>

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
	
$("#publishOn").datetimepicker(); 

$('#summernote').summernote();
$('.note-editor').css({"min-height":"200px"});
$('.note-editable').css({"min-height":"200px"});
</script>
<script>
function levelFun() {
	
	document.getElementById("saveandcontinue").style.display="none";
	
	var levelValue  = $("#levelID").val();
	 if(levelValue != null && levelValue != 'undefined' && levelValue == "" ){
		 levelValue="${levelName}";
	 }
	//level2Div
	$('#level1Title').removeAttr('data-validation');
	 $('#level1Title').attr('data-validation-optional',"true");
	 
	 $('#level2Title').removeAttr('data-validation');
	 $('#level2Title').attr('data-validation-optional',"true");
	 
	 $('#0ticketdocFile').removeAttr('data-validation');
	 $('#0ticketdocFile').attr('data-validation-optional',"true");
	 
	 
	 
	if(levelValue == 'Level-2'){
		$("#level2Div").show();
		$('#level1Title').removeAttr('data-validation-optional');
		$('#level1Title').attr('data-validation',"required");
		
		 $('#0ticketdocFile').val('');
		 $('#docErrorMsg').find('p.jquery_form_error_message').remove();

		removeDocumets();
		getLevel1Titles();
	}else {
		$("#level2Div").hide();
	}
	
	if(levelValue == 'Level-3'){
		$("#level2Div").show();
		$("#level3Div").show();
		$("#saveandcontinue").show();
		document.getElementById("saveandcontinue").style.display="inline-block";
		getLevel1Titles();
		if('${levelOneValue}' != null && '${levelOneValue}' != '' && '${levelOneValue}' != 'undefined')
			getLevel2Titles();
		
		$('#level1Title').removeAttr('data-validation-optional');
		$('#level1Title').attr('data-validation',"required");
		
		$('#level2Title').removeAttr('data-validation-optional');
		$('#level2Title').attr('data-validation',"required");
		
		//$('#0ticketdocFile').removeAttr('data-validation-optional');
		$('#0ticketdocFile').attr('data-validation'," validateImg");
		//$('#0ticketdocFile').attr('data-validation',"validateimage");
		
	}else {
		$("#level3Div").hide();
	}
	
}

function removeDocumets(){
	var tableHeaderRowCount = 1;
	var table = document.getElementById('UserManualAttchTb1');
	var rowCount = table.rows.length;
	for (var i = tableHeaderRowCount; i < rowCount; i++) {
	    table.deleteRow(tableHeaderRowCount);
	}
}
</script>
<!-- <script type="text/javascript">

function saveLevels(type){
	alert("type "+type)
	var ee = $("#summernote").val($('.note-editable').html()); 
	if ($('#user_manuals_form').validate(false, validationSettings)) {
		var formData = $($('#user_manuals_form')[0]).serialize();
// 		$("#userManualsloading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	//$.post(sourceleadUrl+"/addEmailAccount?${_csrf.parameterName}=${_csrf.token}", formData, function(data)
 		$.post(sourceleadUrl + "/storeUserManuals?${_csrf.parameterName}=${_csrf.token}&type="+type, formData, function(data) {
 			if(data == true){
				location.replace(sourceleadUrl+ "/userManuals");
			}
		});
		
	}else {
		return false;
	}	
}

</script>  -->

<<script type="text/javascript">

function saveLevels(type){
	
	var ee = $("#summernote").val($('.note-editable').html()); 
	if ($('#user_manuals_form').validate(false, validationSettings)) {
		
		if(type == "save"){
			$("#save_title").attr("disabled", "disabled");
			$("#save_title").addClass('button').val('Processing..');
		}else{
			$("#saveandcontinue").attr("disabled", "disabled");
			$("#saveandcontinue").addClass('button').val('Processing..');
		}
		//var formData = $($('#user_manuals_form')[0]).serialize();
		var formData = getFormData1("user_manuals_form");
		$.ajax({
			 type : "POST",
			 data: formData,
		      processData: false,
		      contentType:false, 
			  url : sourceleadUrl+"/storeUserManuals?"+tokenParamName+"="+tokenValue+"&save="+type,
			  	success : function(result) {
			  		if (result && result.response === "INVALID_DATA") {
		    		    showError(result.responseMessage);
		    		} else if (result.response === "AWKWARD") {
		    		    location.href = "/errorPage";
		    		} else if (result.response === "SUCCESS") {
		    			location.replace(sourceleadUrl+ "/userManuals");			 			
		    		} else if (result.indexOf('Sign In') !== -1) {
		    			location.replace(sourceleadUrl);
		    		} else {
		    		    showError("Operation failed, please try again");
		    		}
				  		

			},
		});
		
	}else {
		return false;
	}
}


</script>


<script>
function validateUserManualTitle(){
	var title = document.getElementById("title").value;
	var levelValue  = $("#levelID").val();
	if(levelValue.trim() != "Level-3"){
		$('#errMsg').find('p.jquery_form_error_message').remove();
		$.ajax({
			type : "GET",
			url : sourceleadUrl+ "/validateOrgLocationUserManTitle?${_csrf.parameterName}=${_csrf.token}&title="+title+"&levelValue="+levelValue,
			success : function(data) {
				if (data == true) {
					$("input#title").after("<p class='jquery_form_error_message'>"+ title+ " is Already Existed</span>");
					document.getElementById("title").setAttribute('record-exist', 'yes');
					document.getElementById("title").setAttribute('record-exist-errorMsg',title+ ' is Already Existed');
					$('input#title').removeAttr( "record-exist record-exist-errorMsg");
					$("#save_title").attr("disabled", "disabled");
					$("#saveandcontinue").attr("disabled", "disabled");
				} else {
					$('input#title').removeAttr("record-exist record-exist-errormsg");
					$("#save_title").removeAttr("disabled");
					$("#saveandcontinue").removeAttr("disabled");
				}
			},
		});
	}
	
}
</script>

<script>
function getLevel1Titles(){
	var sourceleadUrl ='${sourceleadUrl}';
	$('#errorClient').find('p.jquery_form_error_message').remove();
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getLevelOneUserManTitles?${_csrf.parameterName}=${_csrf.token}",
		success : function(json) {
			var output = "";
			output = output + "<option value=''>Select Level</option>";
			for (var i=0; i<json.length; i++)
			{
			var opt = json[i];
			if(opt.id == '${levelOneValue}'){
				output = output + "<option value='" +opt.id+ "' selected>"+opt.title+" </option>";
			}else{
				output = output + "<option value='" +opt.id+ "'>"+opt.title+" </option>";
			}
			}
			$("#level1Title").empty();
			$("#level1Title").append(output);
			$('#level1Title').trigger("chosen:updated");
		}
	});
}
</script>

<script>
function getLevel2Titles(){
	$('#errorselect2').find('p.jquery_form_error_message').remove();
	
	var level1Id = $("#level1Title").val();
	if(level1Id == null || level1Id == '' || level1Id == 'undefined')
		level1Id = '${levelOneValue}';
	
	
	if("" != level1Id){
		var sourceleadUrl ='${sourceleadUrl}';
		$.ajax({
			type : "GET",
			url : sourceleadUrl+ "/getLevelTwoUserManTitles?${_csrf.parameterName}=${_csrf.token}&level1Id="+level1Id,
			success : function(json) {
				var output = "";
				output = output + "<option value=''>Select level</option>";
				for (var i=0; i<json.length; i++)
				{
				var opt = json[i];
				if(opt.id == '${levelTwoValue}'){
					output = output + "<option value='" +opt.id+ "' selected>"+opt.title+" </option>";
				}else{
					output = output + "<option value='" +opt.id+ "'>"+opt.title+" </option>";
				}
				}
				$("#level2Title").empty();
				$("#level2Title").append(output);
				$('#level2Title').trigger("chosen:updated");
			}
		});
	}
	
}
</script>




<div id="divTicketAttachmentTd1" style="display: none; float: left;">
	<div class="timesheet_week_comm slticket uploadfileError positionRelaive">
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
				var checkMaxTsRowNum = $("#UserManualAttchMaxRowNumber").val();
				var tbl = document.getElementById("UserManualAttchTb1");
				var maxRowNum = parseInt($("input[name = 'UserManualAttchMaxRowNumber']").val());
				$("input[name = 'UserManualAttchMaxRowNumber']").val(maxRowNum + 1);
				
				var row = tbl.insertRow(maxRowNum);
				row.setAttribute("id", "divAttachmentTicketRow" + maxRowNum);
				
				var cell0 = row.insertCell(0);
				var divAttachmentTdStr = document.getElementById("divTicketAttachmentTd1").innerHTML;
				divAttachmentTdStr = divAttachmentTdStr.replace(/REPLACEROWID/g, (maxRowNum));
				cell0.innerHTML = divAttachmentTdStr;
				
				
	}			
</script>
<script type="text/javascript">
	function removeTicketAttachmentRow(rowId) {
		$("#user_manuals_form input[data-id*="+rowId+"ticketdocFile]").remove();
		 document.getElementById("divAttachmentTicketRow"+rowId).style.display = 'none';
		 document.getElementById("divAttachmentTicketRow"+rowId).value= '';
		 var  rId = "divAttachmentTicketRow"+rowId;
		  var r = document.getElementById("UserManualAttchTb1").rows[rId];
          var count = 2*(r.cells.length);
		    for(i=0;i<=count;i++){
		        r.deleteCell(0);
		    } 
		 
	}
	
 function attachFile(id){
		fileUpload(""+id+"ticketdocFile", "userManuals", "user_manuals_form","fileinfo");
		$('#'+id+'ticketdocFile').attr('data-validation'," validateImg");
	}
 </script>
 
 <script>
 function validateYouTubeUrl() {
     var url = $('#youTubeUrl').val();
         if (url != undefined && url != "") {
             var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=|\?v=)([^#\&\?]*).*/;
             var match = url.trim().match(regExp);
             if (match && match[2].length == 11) {
            	 document.getElementById("youtubeUrlId").style.display="none";
            	 $("#save_title").removeAttr("disabled");
            	 $("#saveandcontinue").removeAttr("disabled");
                 $('#ytplayerSide').attr('src', 'https://www.youtube.com/embed/' + match[2] + '?autoplay=0');
             }
             else {
        		 document.getElementById("youtubeUrlId").style.display="block";
        		 $("#save_title").attr("disabled", "disabled");
        		 $("#saveandcontinue").attr("disabled", "disabled");
             }
         }else{
        	 document.getElementById("youtubeUrlId").style.display="none";
        	 $("#save_title").removeAttr("disabled");
        	 $("#saveandcontinue").removeAttr("disabled");
         }
 }
 
 </script>
 <script>
	function refershDocument(){
		$("#0ticketdocFile").val('');
		$("#user_manuals_form input[data-id*=0ticketdocFile]").remove();
		$("#0ticketdocFileuploadFileGroup").find("p.jquery_form_error_message").hide();
	}
</script>
 
<jsp:include page="../masterFooter.jsp" />