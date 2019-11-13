<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%-- <link href="/resources/css/register-style.css?staticTime=${staticTime}"
	rel="stylesheet">
<link href="/resources/css/register-style.css?staticTime=${staticTime}"
	rel="stylesheet"> --%>
<script src="/resources/js/summernote.js?staticTime=${staticTime}"></script>
<link href="/resources/css/summernote.css?staticTime=${staticTime}"
	rel="stylesheet" />
<script src="/resources/js/editor.js?staticTime=${staticTime}"></script>
<link href="/resources/css/editor.css?staticTime=${staticTime}"
	type="text/css" rel="stylesheet" />

<!-- Content Area -->
<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Add Offer Letter Template</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>
			&raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo;
			         <a href="${SourceLeadWeb_sourceleadUrl}/viewOfferLetterTemplate"> Offer Letter
				Template List</a> &raquo; Add Offer Letter Template
		</div>
	</section>


	<div id="loading"></div>
	<div class="content-area content-new clearfix">
		<div class="clearfix form-horizontal">
			<form:form id="offerLetter_template_form" name="offerLetter_template_form" method="post">
				<input type="hidden" name="templateUUID" id="templateUUID"	value="${offerLetterTemplate.templateUUID}" />
				<div class="row newContrWrap">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">

						<div class="form-group">
							<label class="col-md-4">Template Title<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8" id="templateDiv">
								<input type="text" id="templateName" name="templateName"
									value="${offerLetterTemplate.templateName}"
									field-name="Template Name"
									data-validation="required validate_Space validate_length length1-250"
									onblur="validateOfferLetterTemplateName();" type="text"
									class="form-control">
							</div>
						</div>

						<div class="form-group">
			              <label class="col-md-4">Choose Letter Head</label>
			              <div class="col-md-8">
			                  <select class="form-control selectpicker" data-live-search="true" title="Select Letter Head" name="letterHead" field-name="Letterhead" >
<!-- 			                    <option value="">>>> Select Letterhead <<< </option> -->
			                    <c:forEach var="letter" items="${letterHeadList}">
							    <c:if test = "${letter.id == offerLetterTemplate.letterHeadId.id}">
							         <option selected value="${letter.letterHeadUUID}">${letter.letterHeadName}</option>
							    </c:if>
							    <c:if test = "${letter.id != offerLetterTemplate.letterHeadId.id}">
							         <option value="${letter.letterHeadUUID}">${letter.letterHeadName}</option>
							    </c:if>
							     
			                    </c:forEach>
			                  </select>
			            </div>
			            </div>
					</div>
					 
					 
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<label class="col-md-2">Description</label>
						<div class="col-md-10">
							<textarea id="description" name="description" rows="4"
								field-name="description" class="form-control"
								data-validation="validate_length length1-250"
								data-validation-optional="true">${offerLetterTemplate.description}</textarea>
						</div>
					</div>
				</div>

				<div class="row newContrWrap">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<div class="col-md-9 txtEditor_p txtEditorP">
							<textarea id="txtEditor" name="templateContent" field-name="Template Content" value="" class="form-control newContrtextarea" data-validation="required validate_length length2-5000" >${offerLetterTemplate.templateContent}</textarea>
						</div>

						<div class="col-md-3 contrCus">
							<h3>Custom Fields</h3>
							<ul id="clientmsa" style="display: block">
								<li onclick="copyToClipboard('text_element1')"><i></i>
									<p class="copyText" id="text_element1">[Job id]</p></li>
								<li onclick="copyToClipboard('text_element2')"><i></i>
									<p class="copyText" id="text_element2">[Job title]</p></li>
								<li onclick="copyToClipboard('text_element3')"><i></i>
									<p class="copyText" id="text_element3">[Applicant Name]</p></li>
								<li onclick="copyToClipboard('text_element4')"><i></i>
									<p class="copyText" id="text_element4">[Salary]</p></li>
								<li onclick="copyToClipboard('text_element5')"><i></i>
									<p class="copyText" id="text_element5">[Employment Type]</p></li>
								<li onclick="copyToClipboard('text_element6')"><i></i>
									<p class="copyText" id="text_element6">[Company Address]</p></li>
								<li onclick="copyToClipboard('text_element7')"><i></i>
									<p class="copyText" id="text_element7">[Date of Join]</li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
									<p class="copyText" id="text_element8">[Job Location]</p></li>
								<li onclick="copyToClipboard('text_element9')"><i></i>
								<p class="copyText" id="text_element9">[Job Description]</li>
								  <li onclick="copyToClipboard('text_element10')"><i></i>
								<p class="copyText" id="text_element10">[Offer Expire Date]</li>
							</ul>
						</div>
					</div>
				</div>

				<div class="form-footer clearfix contrFoot">
					<div class="col-lg-12">
						<div class="pull-right">
						<c:if test="${!empty offerLetterTemplate.templateUUID}">
							<input type="button" value="Update" id="save_template" onclick="saveOfferLetterTemplate(); " class="btn btn-primary"> 
						</c:if>	
						<c:if test="${empty offerLetterTemplate.templateUUID}">
							<input type="button" value="Save" id="save_template" onclick="saveOfferLetterTemplate(); " class="btn btn-primary"> 
						</c:if>	
						<input type="button" value="Cancel" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/viewOfferLetterTemplate'" class="btn btn-default ml10">
						</div>
					</div>
				</div>



				<!-- Content Area Ends-->
			</form:form>
		</div>
	</div>
</section>
<div class="side_mask"></div>
<div class="prevPop">
	<div class="prevPopClose" onclick="previewclose();">
		<i class="fa fa-times"></i>
	</div>
</div>

 

<script>
	function previewshow() {
		$(".side_mask").fadeIn(200);
		$(".prevPop").fadeIn(100);
	}
	function previewclose() {
		$(".side_mask").fadeOut(200);
		$(".prevPop").fadeOut(100);
	}
</script>

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

 

<script type="text/javascript">
	function saveOfferLetterTemplate() {
		var ee = $("#txtEditor").val($('.Editor-editor').html());
		var sourceleadUrl ='${sourceleadUrl}';
		if ($('#offerLetter_template_form').validate(false, validationSettings)) {
			$("#save_template").addClass('button').val('Processing..');
			var formData = getFormData("offerLetter_template_form");
			$.post(sourceleadUrl + "/saveOfferLetterTemplate?${_csrf.parameterName}=${_csrf.token}",
					formData, function(data) {
						if (data != null) {
							location.replace(sourceleadUrl	+ "/viewOfferLetterTemplate");
						}
					});
		 
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#offerLetter_template_form', function() {
		$("#offerLetter_template_form").showHelpOnFocus();
		$("#offerLetter_template_form").validateOnBlur(false, validationSettings);
	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		var templateUUID = '${offerLetterTemplate.templateUUID}';
		getTemplateContenet(templateUUID);
	});
</script>

<script>
function getTemplateContenet(templateUUID){
	var sourceleadUrl ='${sourceleadUrl}';
	if(templateUUID != ''){
	$.ajax({
		type : "POST",
		url : sourceleadUrl	+ "/getOfferLetterTemplateContent?&templateUUID="+templateUUID,
		success : function(data) {
			$(".Editor-container").find('.Editor-editor').html(data.templateContent); 
		},
	});
}
}
</script>
<script>
function validateOfferLetterTemplateName(){
	var templateUUID = '${offerLetterTemplate.templateUUID}';
	var sourceleadUrl = '${sourceleadUrl}';
	var templateName = $("#templateName").val();
	$('#templateDiv').find('p.jquery_form_error_message').remove();
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/validateOfferLetterTemplateName?${_csrf.parameterName}=${_csrf.token}&templateUUID="+templateUUID+"&templateName="+templateName,
		success : function(data) {
			 if (data == true) {
				$("input#templateName").after("<p class='jquery_form_error_message'>"+ templateName+ " is Already Existed</span>");
				document.getElementById("templateName").setAttribute('record-exist', 'yes');
				document.getElementById("templateName").setAttribute('record-exist-errorMsg',templateName+ ' is Already Existed');
				$('input#templateName').removeAttr( "record-exist record-exist-errorMsg");

			} else {
				$('input#templateName').removeAttr("record-exist record-exist-errormsg");
			} 
		},
	});
}
</script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->


<script>
	$(document).ready(
			function() {
				$("#txtEditor").Editor();
				//$("#txtEditor").find(".Editor-editor").data	- validation('validate_length length2-5000');
				//$("#txtEditor").find(".Editor-editor").data - validation - optional('rule-required', true);
			});
</script>

