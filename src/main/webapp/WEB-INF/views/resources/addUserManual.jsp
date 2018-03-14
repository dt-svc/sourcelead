<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/resources/css/summernote-new.css" rel="stylesheet" type="text/css">
<script src="/resources/js/summernote.min.js"></script>

<script>
$(function(){
	levelFun();
// 	getLevel2Titles();
});

</script>

<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>User Manuals</h2>
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
			<label  class="col-md-3">Level</label>
			<div class="dropdown-select col-md-9">
			<div class="row">
			<c:choose>
			<c:when test="${!empty levelName}">
			<select onChange="levelFun();" id="levelID" name="levelName">
				<option selected>-- Select Level --</option>
				<option value='Level-1' ${levelName == 'Level-1' ? 'selected' : ''}>Level-1</option>
				<option  value='Level-2' ${levelName == 'Level-2' ? 'selected' : ''}>Level-2</option>
				<option   value='Level-3'${levelName == 'Level-3' ? 'selected' : ''}>Level-3</option>
				</select>
			</c:when>
			<c:otherwise>
			<c:if test = "${empty levelName}">
				<select onChange="levelFun();" id="levelID" name="levelName">
				<option selected>-- Select Level --</option>
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
				<div class="col-md-9">
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
			
				<div class="col-md-6" id="level2Div"  style="display:none;">
				<div class="row">
              <label class="col-md-3">Level1<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-9 chooRadius" id="errorClient">
               <div class="input-group col-md-10 pull-left errorInptgroup">
                  <select class="chosen-select form-control" id="level1Title" onchange="getLevel2Titles();" name="level1Title"  field-name="level1Title">
                    <option value=''>Select title</option>
                  </select>
              </div>
              </div>
          </div>                      
              </div> 
			
			<!--  Level2 div ends  -->
			
			
			<!--  Level3 div Starts  -->
			<div class="clearfix"></div>
			<div class="mt" id="level3Div" style="display:none;">
			
			<div class="col-md-6">
			<div class="row form-group">
				<label class="col-md-4">Level2<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-8 chooRadius" id="errorselect2">
               <div class="input-group col-md-10 pull-left errorInptgroup">
                  <select class="chosen-select form-control" id="level2Title"  name="level2Title"  field-name="level2Title">
                    <option value=''>Select title</option>
                  </select>
              </div>
          </div>
				</div>
			</div>
			
			<div class="col-md-6">
			<div class="row form-group">
				<label class="col-md-3">URL <span class="text-danger paddingLeft">*</span></label>
				<div class="col-md-9">
				          <input type="text" name="urlName" value="${uploadUrl}" class="form-control">
				</div>
				</div>
			</div>

			<div class="col-md-6">
			<div class="row form-group">
				<label class="col-md-3">Publish On <span class="text-danger paddingLeft">*</span></label>
				<div class="col-md-9">
					<input type="text" name="publishOn"  value="${publishon}" class="form-control calendar" id="publishOn" >
				</div>
				</div>
			</div>
			
			
			<div class="col-md-6">
			<div class="form-group">
		     <label class="col-md-3">Attachments</label>
		     <div class="col-md-9">
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
		          <div class="timesheet_week_comm slticket" >
		          <div id="0ticketdocFileuploadFileGroup"> 
		          <span>
		          <input type="file"  id="0ticketdocFile" field-name="Attachment"  class="timesheetAttachmentValidation" onchange="attachFile(0);">
		          <i class="fa fa-times" mouseOver="Clear" title="Clear" onClick="remove()"></i>
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
			<div class="clearfix"></div>
			</div>
			<!--  Level3 Div Ends -->

			
			<div class="clearfix"></div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="form-group row">
				<label class="col-md-12">Description <span class="text-danger paddingLeft">&nbsp;*</span></label>
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
      				<button type="button" class="btn btn-default btnsStyles mr">Reset</button>
      				<button type="button" id="save_title" class="btn btn-primary btnsStyles" onClick="saveLevels();">Add</button>
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
					success : function(data) {
						$("#ticketDocument" + documentId).remove();
						$("#userManualDeletePopUp").empty();
						$(".project_inn_mask").fadeOut(200);
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
	var levelValue  = $("#levelID").val();
	 if(levelValue != null && levelValue != 'undefined' && levelValue == "" ){
		 levelValue="${levelName}";
	 }
	//level2Div
	
	if(levelValue == 'Level-2'){
		$("#level2Div").show();
		getLevel1Titles();
	}else {
		$("#level2Div").hide();
	}
	
	if(levelValue == 'Level-3'){
		$("#level2Div").show();
		$("#level3Div").show();
		getLevel1Titles();
		if('${levelOneValue}' != null && '${levelOneValue}' != '' && '${levelOneValue}' != 'undefined')
			getLevel2Titles();
		
	}else {
		$("#level3Div").hide();
	}
	
}
</script>
<script type="text/javascript">

function saveLevels(){
	var ee = $("#summernote").val($('.note-editable').html()); 
	if ($('#user_manuals_form').validate(false, validationSettings)) {
		var formData = $($('#user_manuals_form')[0]).serialize();
		
// 		$("#userManualsloading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
 		$.post(sourceleadUrl + "/storeUserManuals", formData, function(data) {
 			if(data == true){
				location.replace(sourceleadUrl+ "/userManuals");
			}
		});
		
	}else {
		return false;
	}	
}

</script> 
<!-- <script type="text/javascript">
	function saveLevels() {
		if ($('#user_manuals_form').validate(false, validationSettings)){ 
			var leve2Value  = $("#levelID").val();
		    var formData = getFormData("user_manuals_form");
			$("#user_manuals_form").attr("disabled", "disabled");
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            $( "#user_manuals_form" ).addClass('button').val('Processing..');
			$.post(sourceleadUrl+ "/storeUserManuals?${_csrf.parameterName}=${_csrf.token}", formData, function(data) {
				location.replace(sourceleadUrl+"/userManuals");
			});
			
	        return true;
		
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#user_manuals_form', function() {
		$("#user_manuals_form").showHelpOnFocus();
		$("#user_manuals_form").validateOnBlur(false, validationSettings);  
   });
</script> -->

<script>
function validateUserManualTitle(){
	var title = document.getElementById("title").value;
	
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/validateOrgLocationUserManTitle?${_csrf.parameterName}=${_csrf.token}&title="+title,
		success : function(data) {
			if (data == true) {
				
				$("input#title").after("<p class='jquery_form_error_message'>"+ title+ " is Already Existed</span>");
				document.getElementById("title").setAttribute('record-exist', 'yes');
				document.getElementById("title").setAttribute('record-exist-errorMsg',title+ ' is Already Existed');
				$('input#title').removeAttr( "record-exist record-exist-errorMsg");
				$("#save_title").attr("disabled", "disabled");
			} else {
				$('input#title').removeAttr("record-exist record-exist-errormsg");
				$("#save_title").removeAttr("disabled");
			}
		},
	});
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
			output = output + "<option value=''>Select Client Contact</option>";
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
	
	alert('${levelTwoValue}')
	alert("hello")
	
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
				output = output + "<option value=''>Select Client Contact</option>";
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
	<div class="timesheet_week_comm slticket">
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
	}
 </script>
 
<jsp:include page="../masterFooter.jsp" />