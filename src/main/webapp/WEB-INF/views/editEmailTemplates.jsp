<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%-- <script src="/resources/js/InterViewFormBuilder.js?staticTime=${staticTime}" type="text/javascript" ></script>
<script src="/resources/js/interViewForm-html-generator.js?staticTime=${staticTime}" type="text/javWascript" ></script> 
<link rel="stylesheet" href="/resources/css/style.css?staticTime=${staticTime}" /> --%>



<section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2> Edit ${emailScenario.entityType} Template</h2>
          </div>
         <%--  <a href="${appPath}/addUserManuals" class="pull-right btn btn-primary btn-sm mb">Add</a> --%>
          <%-- <a href="${appPath}/applicantDeclineReports" title="Grid View" id="GView" class="btn btn-default " ><i class="fa fa-pie-chart"></i></a>
          --%> 
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; <a href="<c:url value='${appPath}/orgTemplates'/>">Templates</a>&raquo; <a href="<c:url value='${appPath}/emailTemplates'/>">EmailTemplatesList</a>&raquo; Email Templates </div>
        </section>
        
        <div class="content-area clearfix">
        <div id="emailTemplateLoading"></div>
     <div class="row clearfix mt">
         <div class="clearfix addFields">
         <form:form id="email_template_form" name="email_template_form"  method="post" >
         
			<div class="clearfix">

			<div class="col-md-6">
			<div class="row">
				<label class="col-md-3">Template Title <span class="text-danger paddingLeft">*</span></label>
				<div class="col-md-9" id="errMsg">
				        <input type="text" name="scenarioName" id="senarioName" class="form-control" field-name="title"  data-validation="required validate_length length1-500" value="${emailScenario.scenarioName}">
					</div>
				</div>
			</div>
			
			</div>

			
			<div class="clearfix"></div>
			
			<c:if test="${emailScenario.entityType != 'Notification'}">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
			<div class="form-group row">
			<label class="col-md-12">Subject</label>
				<div class="col-md-12">
				        <textarea rows=4 class="form-control" name="subject" id="summernote" field-name="Subject" >${emailScenario.subject}</textarea>
						<div id="editor"></div>
				</div>
			</div>  
			</div>
			</c:if>
			
		<div class="clearfix"></div>
		<label class="col-md-12">Body</label>
			<div class="row newContrWrap">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<div class="col-md-9 txtEditor_p txtEditorP">
							<textarea name="body" field-name="Template Content" value="" class="form-control newContrtextarea teHeight" data-validation="required" >${emailScenario.body}</textarea>
						    <p class="text-danger small" style="font-size: 13px;"><i class="fa fa-exclamation-triangle"></i>&nbsp;Don't change any class fields</p>
						</div>
						

						<div class="col-md-3 contrCus">
						<h3>Class Fields</h3>
							<ul id="clientmsa" style="display: block">
							 <c:if test="${!empty classFieldList}">
					            <c:forEach var="classField" items="${classFieldList}" >
					            <li onclick="copyToClipboard('text_element${classField}')" class="copyText" id="text_element${classField}">${classField}</li>
					           
					            </c:forEach>
					   		</c:if>
							</ul>							

						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			
			<c:if test="${emailScenario.entityType != 'Notification'}">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt">
			<div class="form-group row">
			<label class="col-md-12">Signature</label>
				<div class="col-md-12">
				        <textarea rows=5 name="signature" id="summernote" class="form-control" field-name="Signature" >${emailScenario.signature}</textarea>
						<div id="editor"></div>
				</div>
			</div>  
			</div>
			</c:if>
					
			</div>
			
			
			</div>
			</div>
			
      		<div class="clearfix"></div>
	<div class="mt clearfix mb">
		<div class="pull-right">
			<input type="hidden" name="id" id="id" value="${emailScenario.id}" />
			<button type="button" name="save" value="save" id="save_title" class="btn btn-primary btnsStyles" onClick="editEmailTemplates();">Save</button>
			<c:if test="${!empty emailScenario.parentId.id}">
			<button type="button" name="restore" value="save" id="save_title" class="btn btn-primary btnsStyles" onClick="javascript:window.location.href = '<c:url value='editEmailTemplate?templateID=${emailScenario.parentId.id}'/>'">Restore</button>
			</c:if>
		<button type="button" class="btn btn-primary btnsStyles" data-dismiss="modal" onClick="javascript:window.location.href = '<c:url value='emailTemplates'/>'">Cancel</button>
		</div>
	</div>
      			
      			
      </form:form>
      </div>
	</div>
        </div>
        <div id="userManualDeletePopUp"></div>
 </section>
<script>
	function copyToClipboard(elementId) {
		var aux = document.createElement("input");
		aux.setAttribute("value", "");
		aux.setAttribute("value", document.getElementById(elementId).innerHTML);
		document.body.appendChild(aux);
		aux.select();
		document.execCommand("copy");
		document.body.removeChild(aux);
	}
	function log() {
		console.log('---')
	}
</script>


<script>
function editEmailTemplates() {
	if ($('#email_template_form').validate(false, validationSettings)) {
		$("#save_title").attr("disabled", "disabled");
		$("#save_title").addClass('button').val('Processing..');
	
	var sourceleadUrl = '${sourceleadUrl}';
	var formData = getFormData("email_template_form");
	$.post(sourceleadUrl+"/storeEmailTemplates?", formData, function(data) {
		if(data == "success"){
			location.replace(sourceleadUrl+ "/emailTemplates");
		}
	});
	
	}else {
		return false;
	}
				
			
	}
$('body').on('blur', '#email_template_form', function() {
	$("#email_template_form").showHelpOnFocus();
	$("#email_template_form").validateOnBlur(false, validationSettings);
});

</script>

<jsp:include page="masterFooter.jsp" />	
