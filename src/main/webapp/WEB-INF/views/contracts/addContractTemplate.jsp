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
			<h2>Add Contract Template</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>
			&raquo;<a href="${SourceLeadWeb_vmsUrl}/orgContractTemplate"> Contract
				Template List</a> &raquo; Add Contract Template
		</div>
	</section>


	<div id="loading"></div>
	<div class="content-area content-new clearfix">
		<div class="clearfix form-horizontal">
			<form:form id="template_form" name="template_form" method="post">
				<div class="row newContrWrap">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">

						<div class="form-group">
							<label class="col-md-4">Template Title<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8" id="templateDiv">
								<input type="text" id="templateName" name="templateName"
									value="${contractsTemplate.templateName}"
									field-name="Template Name"
									data-validation="required validate_Space validate_length length1-250"
									onblur="validateContractTemplateName();" type="text"
									class="form-control">
							</div>
							<input type="hidden" name="templateId" id="templateId"
								value="${contractsTemplate.id}" />
						</div>

						<div class="form-group">
							<label class="col-md-4">Template Type<span
								class="text-danger">&nbsp;*</span></label>
							<div class="col-md-8">
								<select class="chosen-select form-control" id="contractType"
									field-name="Template Type" name="contractType"
									onchange="contractTemplateType(); validateContractTemplateName();">
									<option
										${contractsTemplate.templateType  == 'Client MSA' ? 'selected' : ''}>Client MSA</option>
									<option
										${contractsTemplate.templateType  == 'Vendor MSA' ? 'selected' : ''}>Vendor MSA</option>
									<option
										${contractsTemplate.templateType  == 'Client SOW' ? 'selected' : ''}>Client SOW</option>
									<option
										${contractsTemplate.templateType  == 'Vendor SOW' ? 'selected' : ''}>Vendor SOW</option>
									<option
										${contractsTemplate.templateType  == 'NDA' ? 'selected' : ''}>NDA</option>
									<option
										${contractsTemplate.templateType  == 'NSA' ? 'selected' : ''}>NSA</option>
									<option
										${contractsTemplate.templateType  == 'W-4 Form' ? 'selected' : ''}>W-4 Form</option>
									<option
										${contractsTemplate.templateType  == 'I-9 Form' ? 'selected' : ''}>I-9 Form</option>
									<option
										${contractsTemplate.templateType  == 'Full Time Offer' ? 'selected' : ''}>Full Time Offer</option>


								</select>
							</div>
						</div>


					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
					 <div class="form-group">
              <label class="col-md-4">Choose Letter Head<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                  <select class="form-control selectpicker" data-live-search="true" title="Select Letterhead" name="letterHead" field-name="Letterhead" data-validation="required">
                    <c:forEach var="letter" items="${letterHeadList}">
                  <%--  <c:if test = "${letter.id == letter.letterHead.id}">
				         <option selected value="${letter.letterHeadUUID}">${letter.letterHeadName}</option>
				    </c:if> --%>
				    
				    
				    <c:if test = "${letter.id == contractsTemplate.letterHead.id}">
				         <option selected value="${letter.letterHeadUUID}">${letter.letterHeadName}</option>
				    </c:if>
				    <c:if test = "${letter.id != contractsTemplate.letterHead.id}">
				         <option value="${letter.letterHeadUUID}">${letter.letterHeadName}</option>
				    </c:if>
				    
				    
<%-- 				    <c:if test = "${letter.id != letter.letterHead.id}"> --%>
<%-- 				         <option value="${letter.letterHeadUUID}">${letter.letterHeadName}</option> --%>
<%-- 				         </c:if> --%>
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
								data-validation-optional="true">${contractsTemplate.description}</textarea>
						</div>
					</div>



				</div>

				<div class="row newContrWrap">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 myForm">
						<div class="col-md-9 txtEditor_p txtEditorP">
							<textarea id="txtEditor" name="templateContent" field-name="Template Content" value="" class="form-control newContrtextarea" data-validation="required" ></textarea>
						</div>


						<div class="col-md-3 contrCus">
							<h3>Custom Fields</h3>
							<ul id="clientmsa" style="display: block">
								<li onclick="copyToClipboard('text_element1')"><i></i>
									<p class="copyText" id="text_element1">[Client Name]</p></li>
								<li onclick="copyToClipboard('text_element2')"><i></i>
									<p class="copyText" id="text_element2">[Client Contact]</p></li>
								<li onclick="copyToClipboard('text_element3')"><i></i>
									<p class="copyText" id="text_element3">[Client location]</p></li>
								<li onclick="copyToClipboard('text_element4')"><i></i>
									<p class="copyText" id="text_element4">[Company Location]</p></li>
								<li onclick="copyToClipboard('text_element5')"><i></i>
									<p class="copyText" id="text_element5">[Company Name]</p></li>
								<li onclick="copyToClipboard('text_element6')"><i></i>
									<p class="copyText" id="text_element6">[Start Date]</p></li>
								<li onclick="copyToClipboard('text_element7')"><i></i>
									<p class="copyText" id="text_element7">[End date]</li>
								<li onclick="copyToClipboard('text_element8')"><i></i>
									<p class="copyText" id="text_element8">[State Of Jurisdiction]</p></li>
								<!--    <li onclick="copyToClipboard('text_element9')"><i></i><p class="copyText" id="text_element9">[Offer Rate]</li> -->
								<!-- 								<li onclick="copyToClipboard('text_element10')"><i></i> -->
								<!-- <p class="copyText" id="text_element10">[Billing Rate]</li>
								<li onclick="copyToClipboard('text_element11')"><i></i>
								<p class="copyText" id="text_element11">[Overtime]</li>
								<li onclick="copyToClipboard('text_element12')"><i></i>
								<p class="copyText" id="text_element12">[Payment Term]</li> -->
							</ul>

							<ul id="vendormsa" style="display: none">
								<li onclick="copyToClipboard('text_element13')"><i></i>
									<p class="copyText" id="text_element13">[Vendor Name]</p></li>
								<li onclick="copyToClipboard('text_element14')"><i></i>
									<p class="copyText" id="text_element14">[Vendor Contact]</p></li>
								<li onclick="copyToClipboard('text_element15')"><i></i>
									<p class="copyText" id="text_element15">[Vendor location]</p></li>
								<li onclick="copyToClipboard('text_element16')"><i></i>
									<p class="copyText" id="text_element16">[Company Location]</p></li>
								<li onclick="copyToClipboard('text_element17')"><i></i>
									<p class="copyText" id="text_element17">[Company Name]</p></li>
								<li onclick="copyToClipboard('text_element18')"><i></i>
									<p class="copyText" id="text_element18">[Start Date]</p></li>
								<li onclick="copyToClipboard('text_element19')"><i></i>
									<p class="copyText" id="text_element19">[End date]</li>
								<li onclick="copyToClipboard('text_element20')"><i></i>
									<p class="copyText" id="text_element20">[State Of Jurisdiction]</p></li>
								<!--  <li onclick="copyToClipboard('text_element21')"><i></i><p class="copyText" id="text_element21">[Offer Rate]</li> -->
								<!-- <li onclick="copyToClipboard('text_element22')"><i></i>
								<p class="copyText" id="text_element22">[Billing Rate]</li>
								<li onclick="copyToClipboard('text_element23')"><i></i>
								<p class="copyText" id="text_element23">[Overtime]</li>
								<li onclick="copyToClipboard('text_element24')"><i></i>
								<p class="copyText" id="text_element24">[Payment Term]</li> -->
							</ul>
							<ul id="clientsow" style="display: none">
								<li onclick="copyToClipboard('text_element25')"><i></i>
									<p class="copyText" id="text_element25">[Client Name]</p></li>
								<li onclick="copyToClipboard('text_element26')"><i></i>
									<p class="copyText" id="text_element26">[Client Contact]</li>
								<li onclick="copyToClipboard('text_element27')"><i></i>
									<p class="copyText" id="text_element27">[Associate Name]</li>
								<li onclick="copyToClipboard('text_element28')"><i></i>
									<p class="copyText" id="text_element28">[Client Location]</li>
								<li onclick="copyToClipboard('text_element29')"><i></i>
									<p class="copyText" id="text_element29">[Job Title]</li>
								<!--  <li onclick="copyToClipboard('text_element31')"><i></i><p class="copyText" id="text_element31">[Offer Rate]</li> -->
								<li onclick="copyToClipboard('text_element32')"><i></i>
									<p class="copyText" id="text_element32">[Billing Amount]</li>
								<li onclick="copyToClipboard('text_element33')"><i></i>
									<p class="copyText" id="text_element33">[Overtime Hours]</li>
								<li onclick="copyToClipboard('text_element34')"><i></i>
									<p class="copyText" id="text_element34">[Payment Frequency]</li>
								<li onclick="copyToClipboard('text_element35')"><i></i>
									<p class="copyText" id="text_element35">[Start Date]</p></li>
								<li onclick="copyToClipboard('text_element36')"><i></i>
									<p class="copyText" id="text_element36">[End date]</li>
								<li onclick="copyToClipboard('text_element37')"><i></i>
									<p class="copyText" id="text_element37">[Company Location]</p></li>
								<li onclick="copyToClipboard('text_element38')"><i></i>
									<p class="copyText" id="text_element38">[Company Name]</p></li>
								<li onclick="copyToClipboard('text_element107')"><i></i>
									<p class="copyText" id="text_element107">[State Of Jurisdiction]</p></li>
								<li onclick="copyToClipboard('text_element111')"><i></i>
									<p class="copyText" id="text_element111">[Offer Rate]</p></li>

							</ul>

							<ul id="vendorsow" style="display: none">
								<li onclick="copyToClipboard('text_element39')"><i></i>
									<p class="copyText" id="text_element39">[Vendor Name]</p></li>
								<li onclick="copyToClipboard('text_element40')"><i></i>
									<p class="copyText" id="text_element40">[Vendor Contact]</li>
								<li onclick="copyToClipboard('text_element41')"><i></i>
									<p class="copyText" id="text_element41">[Associate Name]</li>
								<li onclick="copyToClipboard('text_element42')"><i></i>
									<p class="copyText" id="text_element42">[Vendor Location]</li>
								<li onclick="copyToClipboard('text_element43')"><i></i>
									<p class="copyText" id="text_element43">[Job Title]</li>
								<!--  <li onclick="copyToClipboard('text_element45')"><i></i><p class="copyText" id="text_element45">[Offer Rate]</li> -->
								<li onclick="copyToClipboard('text_element46')"><i></i>
									<p class="copyText" id="text_element46">[Billing Amount]</li>
								<li onclick="copyToClipboard('text_element47')"><i></i>
									<p class="copyText" id="text_element47">[Overtime Hours]</li>
								<li onclick="copyToClipboard('text_element48')"><i></i>
									<p class="copyText" id="text_element48">[Payment Frequency]</li>
								<li onclick="copyToClipboard('text_element88')"><i></i>
									<p class="copyText" id="text_element88">[Start Date]</p></li>
								<li onclick="copyToClipboard('text_element49')"><i></i>
									<p class="copyText" id="text_element49">[End date]</li>
								<li onclick="copyToClipboard('text_element50')"><i></i>
									<p class="copyText" id="text_element50">[Company Location]</p></li>
								<li onclick="copyToClipboard('text_element51')"><i></i>
									<p class="copyText" id="text_element51">[Company Name]</p></li>
								<li onclick="copyToClipboard('text_element108')"><i></i>
									<p class="copyText" id="text_element108">[State Of Jurisdiction]</p></li>
								<li onclick="copyToClipboard('text_element114')"><i></i>
									<p class="copyText" id="text_element114">[Offer Rate]</p></li>

							</ul>
							<ul id="nda" style="display: none">
								<li onclick="copyToClipboard('text_element52')"><i></i>
									<p class="copyText" id="text_element52">[Vendor Name]</li>
								<li onclick="copyToClipboard('text_element53')"><i></i>
									<p class="copyText" id="text_element53">[Vendor Contact]</li>
								<li onclick="copyToClipboard('text_element54')"><i></i>
									<p class="copyText" id="text_element54">[Vendor Location]</li>
								<li onclick="copyToClipboard('text_element55')"><i></i>
									<p class="copyText" id="text_element55">[Company Location]</p></li>
								<li onclick="copyToClipboard('text_element56')"><i></i>
									<p class="copyText" id="text_element56">[Company Name]</p></li>
								<li onclick="copyToClipboard('text_element63')"><i></i>
									<p class="copyText" id="text_element63">[State Of Jurisdiction]</p></li>
								<li onclick="copyToClipboard('text_element95')"><i></i>
									<p class="copyText" id="text_element95">[Billing Amount]</li>
								<li onclick="copyToClipboard('text_element96')"><i></i>
									<p class="copyText" id="text_element96">[Overtime Hours]</li>
								<li onclick="copyToClipboard('text_element97')"><i></i>
									<p class="copyText" id="text_element97">[Payment Frequency]</li>

							</ul>
							<ul id="nsa" style="display: none">
								<li onclick="copyToClipboard('text_element57')"><i></i>
									<p class="copyText" id="text_element57">[Vendor Name]</li>
								<li onclick="copyToClipboard('text_element58')"><i></i>
									<p class="copyText" id="text_element58">[Vendor Contact]</li>
								<li onclick="copyToClipboard('text_element59')"><i></i>
									<p class="copyText" id="text_element59">[Vendor Location]</li>
								<li onclick="copyToClipboard('text_element60')"><i></i>
									<p class="copyText" id="text_element60">[Company Location]</p></li>
								<li onclick="copyToClipboard('text_element61')"><i></i>
									<p class="copyText" id="text_element61">[Company Name]</p></li>
								<li onclick="copyToClipboard('text_element62')"><i></i>
									<p class="copyText" id="text_element62">[State Of Jurisdiction]</p></li>
								<li onclick="copyToClipboard('text_element98')"><i></i>
									<p class="copyText" id="text_element98">[Billing Amount]</li>
								<li onclick="copyToClipboard('text_element99')"><i></i>
									<p class="copyText" id="text_element99">[Overtime Hours]</li>
								<li onclick="copyToClipboard('text_element100')"><i></i>
									<p class="copyText" id="text_element100">[Payment Frequency]</li>
							</ul>
							<ul id="w-4Form" style="display: none">
								<li onclick="copyToClipboard('text_element64')"><i></i>
									<p class="copyText" id="text_element64">[First Name]</li>
								<li onclick="copyToClipboard('text_element65')"><i></i>
									<p class="copyText" id="text_element65">[Last Name]</li>
								<li onclick="copyToClipboard('text_element66')"><i></i>
									<p class="copyText" id="text_element66">[Social Security Number]</li>
								<li onclick="copyToClipboard('text_element67')"><i></i>
									<p class="copyText" id="text_element67">[Address]</li>
								<li onclick="copyToClipboard('text_element68')"><i></i>
									<p class="copyText" id="text_element68">[Country]</li>
								<li onclick="copyToClipboard('text_element69')"><i></i>
									<p class="copyText" id="text_element69">[State]</li>
								<li onclick="copyToClipboard('text_element70')"><i></i>
									<p class="copyText" id="text_element70">[City]</li>
								<li onclick="copyToClipboard('text_element71')"><i></i>
									<p class="copyText" id="text_element71">[Zipcode]</li>
								<li onclick="copyToClipboard('text_element101')"><i></i>
									<p class="copyText" id="text_element101">[Billing Amount]</li>
								<li onclick="copyToClipboard('text_element102')"><i></i>
									<p class="copyText" id="text_element102">[Overtime Hours]</li>
								<li onclick="copyToClipboard('text_element103')"><i></i>
									<p class="copyText" id="text_element103">[Payment Frequency]</li>
								<li onclick="copyToClipboard('text_element109')"><i></i>
									<p class="copyText" id="text_element109">[State Of Jurisdiction]</p></li>
							</ul>

							<ul id="I-9Form" style="display: none">
								<li onclick="copyToClipboard('text_element72')"><i></i>
									<p class="copyText" id="text_element72">[First Name]</li>
								<li onclick="copyToClipboard('text_element73')"><i></i>
									<p class="copyText" id="text_element73">[Last Name]</li>
								<li onclick="copyToClipboard('text_element117')"><i></i>
									<p class="copyText" id="text_element117">[Phone]</p></li>
								<li onclick="copyToClipboard('text_element118')"><i></i>
									<p class="copyText" id="text_element118">[Email]</p></li>
								<li onclick="copyToClipboard('text_element119')"><i></i>
									<p class="copyText" id="text_element119">[Social Security Number]</p></li>
								<li onclick="copyToClipboard('text_element74')"><i></i>
									<p class="copyText" id="text_element74">[Address]</li>
								<li onclick="copyToClipboard('text_element75')"><i></i>
									<p class="copyText" id="text_element75">[Country]</li>
								<li onclick="copyToClipboard('text_element76')"><i></i>
									<p class="copyText" id="text_element76">[State]</li>
								<li onclick="copyToClipboard('text_element77')"><i></i>
									<p class="copyText" id="text_element77">[City]</li>
								<li onclick="copyToClipboard('text_element78')"><i></i>
									<p class="copyText" id="text_element78">[Zipcode]</li>
								<li onclick="copyToClipboard('text_element79')"><i></i>
									<p class="copyText" id="text_element79">[Registration Number]</li>
								<li onclick="copyToClipboard('text_element80')"><i></i>
									<p class="copyText" id="text_element80">[Admission Number]</li>
								<li onclick="copyToClipboard('text_element81')"><i></i>
									<p class="copyText" id="text_element81">[Foreign Passport Number]</li>
								<li onclick="copyToClipboard('text_element82')"><i></i>
									<p class="copyText" id="text_element82">[Authorized to work]</li>
								<li onclick="copyToClipboard('text_element83')"><i></i>
									<p class="copyText" id="text_element83">[Document Title]</li>
								<li onclick="copyToClipboard('text_element84')"><i></i>
									<p class="copyText" id="text_element84">[Issuing Authority]</li>
								<li onclick="copyToClipboard('text_element85')"><i></i>
									<p class="copyText" id="text_element85">[Document Number]</li>
								<li onclick="copyToClipboard('text_element86')"><i></i>
									<p class="copyText" id="text_element86">[Expiration Date]</li>
								<li onclick="copyToClipboard('text_element87')"><i></i>
									<p class="copyText" id="text_element87">[Join Date]</li>
								<li onclick="copyToClipboard('text_element104')"><i></i>
									<p class="copyText" id="text_element104">[Billing Amount]</li>
								<li onclick="copyToClipboard('text_element105')"><i></i>
									<p class="copyText" id="text_element105">[Overtime Hours]</li>
								<li onclick="copyToClipboard('text_element106')"><i></i>
									<p class="copyText" id="text_element106">[Payment Frequency]</li>
								<li onclick="copyToClipboard('text_element110')"><i></i>
									<p class="copyText" id="text_element110">[State Of Jurisdiction]</p></li>
							</ul>
							<ul id="fullTimeOffer" style="display: none">
								<li onclick="copyToClipboard('text_element120')"><i></i>
									<p class="copyText" id="text_element120">[Company Name]</li>
								<li onclick="copyToClipboard('text_element121')"><i></i>
									<p class="copyText" id="text_element121">[Company Address]</li>
								<li onclick="copyToClipboard('text_element122')"><i></i>
									<p class="copyText" id="text_element122">[Company City]</li>
								<li onclick="copyToClipboard('text_element123')"><i></i>
									<p class="copyText" id="text_element123">[Company State]</li>
								<li onclick="copyToClipboard('text_element124')"><i></i>
									<p class="copyText" id="text_element124">[Company Country]</li>
								<li onclick="copyToClipboard('text_element125')"><i></i>
									<p class="copyText" id="text_element125">[Employee Name]</li>
								<li onclick="copyToClipboard('text_element126')"><i></i>
									<p class="copyText" id="text_element126">[Join Date]</li>
								<li onclick="copyToClipboard('text_element127')"><i></i>
									<p class="copyText" id="text_element127">[Rate]</li>
								<li onclick="copyToClipboard('text_element128')"><i></i>
									<p class="copyText" id="text_element128">[Rate Per]</li>
								<li onclick="copyToClipboard('text_element129')"><i></i>
									<p class="copyText" id="text_element129">[Rate Type]</li>
								<li onclick="copyToClipboard('text_element130')"><i></i>
									<p class="copyText" id="text_element130">[Employee Location]</li>
								<li onclick="copyToClipboard('text_element131')"><i></i>
									<p class="copyText" id="text_element131">[Work Location]</p></li>
								<li onclick="copyToClipboard('text_element132')"><i></i>
									<p class="copyText" id="text_element132">[State of Jurisdiction]</p></li>
							</ul>
						</div>
					</div>
				</div>

				<div class="form-footer clearfix contrFoot">
					<div class="col-lg-12">
						<div class="pull-right">
							<!-- <input type="button" value="Preview" id="save_Preview" class="btn btn-success" onclick="generatePDF();">  -->
							<input type="button" value="Save" id="save_template"
								onclick="saveTemplate(); " class="btn btn-primary"> <input
								type="button" value="Cancel"
								onClick="javascript:window.location.href = '${SourceLeadWeb_vmsUrl}/orgContractTemplate'"
								class="btn btn-default ml10">
							<!--  <input type="button" value="Back" class="btn btn-danger">
			<a href="add-contract-template-preview.html"><input type="button" value="Next" class="btn btn-primary ml10"></a> -->
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
	<%-- 	<embed id="iFrame" src="<c:url value='/testController'/>"></embed> --%>
</div>


<!-- 
<script src="/resources/js/editor.js"></script>
    <link href="/resources/css/editor.css" type="text/css" rel="stylesheet"/>
		<script>
			$(document).ready(function() {
				$("#txtEditor").Editor();
			});
</script>  
-->


<!-- <script src="/resources/js/summernote.js"></script>
<script>
	$('#txtEditor').summernote({
  toolbar: [
    // [groupName, [list of button]]
    ['style', ['bold', 'italic', 'underline', 'clear']],
    ['font', ['strikethrough', 'superscript', 'subscript']],
    ['fontsize', ['fontsize']],
    ['color', ['color']],
    ['para', ['ul', 'ol', 'paragraph']],
	['link',['link','insert','unlink']],
    ['height', ['height']]
  ],
  callbacks:{
	  onImageUpload: function (data) {
		             	data.pop();
                   	}
	  
  	}
});
</script> -->


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
	function contractTemplateType() {
		var contractType = "";
		var type = $("#contractType").val();
		if (type != '') {
			contractType = type;
		} else {
			contractType = "${contractsTemplate.templateType}";
		}

		if (contractType == "Client MSA") {
			document.getElementById("vendormsa").style.display = "none";
			document.getElementById("clientsow").style.display = "none";
			document.getElementById("vendorsow").style.display = "none";
			document.getElementById("nda").style.display = "none";
			document.getElementById("nsa").style.display = "none";
			document.getElementById("clientmsa").style.display = "block";
			document.getElementById("fullTimeOffer").style.display = "none";
			document.getElementById("I-9Form").style.display = "none";
			document.getElementById("w-4Form").style.display = "none";
		}
		if (contractType == "Vendor MSA") {
			document.getElementById("clientmsa").style.display = "none";
			document.getElementById("clientsow").style.display = "none";
			document.getElementById("vendorsow").style.display = "none";
			document.getElementById("nda").style.display = "none";
			document.getElementById("nsa").style.display = "none";
			document.getElementById("vendormsa").style.display = "block";
			document.getElementById("fullTimeOffer").style.display = "none";
			document.getElementById("I-9Form").style.display = "none";
			document.getElementById("w-4Form").style.display = "none";
		}
		if (contractType == "Client SOW") {
			document.getElementById("clientmsa").style.display = "none";
			document.getElementById("vendormsa").style.display = "none";
			document.getElementById("vendorsow").style.display = "none";
			document.getElementById("nda").style.display = "none";
			document.getElementById("nsa").style.display = "none";
			document.getElementById("clientsow").style.display = "block";
			document.getElementById("fullTimeOffer").style.display = "none";
			document.getElementById("I-9Form").style.display = "none";
			document.getElementById("w-4Form").style.display = "none";
		}

		if (contractType == "Vendor SOW") {
			document.getElementById("clientmsa").style.display = "none";
			document.getElementById("vendormsa").style.display = "none";
			document.getElementById("clientsow").style.display = "none";
			document.getElementById("nda").style.display = "none";
			document.getElementById("nsa").style.display = "none";
			document.getElementById("vendorsow").style.display = "block";
			document.getElementById("fullTimeOffer").style.display = "none";
			document.getElementById("I-9Form").style.display = "none";
			document.getElementById("w-4Form").style.display = "none";
		}
		if (contractType == "NDA") {
			document.getElementById("clientmsa").style.display = "none";
			document.getElementById("vendormsa").style.display = "none";
			document.getElementById("clientsow").style.display = "none";
			document.getElementById("vendorsow").style.display = "none";
			document.getElementById("nsa").style.display = "none";
			document.getElementById("nda").style.display = "block";
			document.getElementById("fullTimeOffer").style.display = "none";
			document.getElementById("I-9Form").style.display = "none";
			document.getElementById("w-4Form").style.display = "none";
		}
		if (contractType == "NSA") {
			document.getElementById("clientmsa").style.display = "none";
			document.getElementById("vendormsa").style.display = "none";
			document.getElementById("clientsow").style.display = "none";
			document.getElementById("vendorsow").style.display = "none";
			document.getElementById("nda").style.display = "none";
			document.getElementById("nsa").style.display = "block";
			document.getElementById("I-9Form").style.display = "none";
			document.getElementById("w-4Form").style.display = "none";
			document.getElementById("fullTimeOffer").style.display = "none";
		}
		if (contractType == "W-4 Form") {
			document.getElementById("clientmsa").style.display = "none";
			document.getElementById("vendormsa").style.display = "none";
			document.getElementById("clientsow").style.display = "none";
			document.getElementById("vendorsow").style.display = "none";
			document.getElementById("nda").style.display = "none";
			document.getElementById("nsa").style.display = "none";
			document.getElementById("I-9Form").style.display = "none";
			document.getElementById("w-4Form").style.display = "block";
			document.getElementById("fullTimeOffer").style.display = "none";
		}

		if (contractType == "I-9 Form") {
			document.getElementById("clientmsa").style.display = "none";
			document.getElementById("vendormsa").style.display = "none";
			document.getElementById("clientsow").style.display = "none";
			document.getElementById("vendorsow").style.display = "none";
			document.getElementById("nda").style.display = "none";
			document.getElementById("nsa").style.display = "none";
			document.getElementById("I-9Form").style.display = "block";
			document.getElementById("w-4Form").style.display = "none";
			document.getElementById("fullTimeOffer").style.display = "none";
		}

		if (contractType == "Full Time Offer") {
			document.getElementById("clientmsa").style.display = "none";
			document.getElementById("vendormsa").style.display = "none";
			document.getElementById("clientsow").style.display = "none";
			document.getElementById("vendorsow").style.display = "none";
			document.getElementById("nda").style.display = "none";
			document.getElementById("nsa").style.display = "none";
			document.getElementById("I-9Form").style.display = "none";
			document.getElementById("w-4Form").style.display = "none";
			document.getElementById("fullTimeOffer").style.display = "block";
		}
	}
</script>



<script type="text/javascript">
	function generatePDF(id) {
		var ee = $("#txtEditor").summernote('code');
		var frm = $('#template_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		//It will escape all the special characters
		var jsonData = encodeURIComponent(con);
		window.open('${SourceLeadWeb_sourceleadUrl}/testController/' + id
				+ '', '_newtab');

	}
</script>

<script type="text/javascript">
	function saveTemplate() {
		var ee = $("#txtEditor").val($('.Editor-editor').html());
		//	var content = $('textarea[name="templateContent"]').html($('#txtEditor').code());
		if ($('#template_form').validate(false, validationSettings)) {
			/* var frm = $('#template_form').serializeFormJSON();
			var jsonData = JSON.stringify(frm);
			/* var con = JSON.stringify(frm);
			 con = con.replace(/[{}]/g, ""); 
			
			var form = $('#template_form')[0];
			var formData = new FormData(form);
			formData.append("json",jsonData); */

			//It will escape all the special characters
			// var jsonData = encodeURIComponent(con);
			$("#save_template").addClass('button').val('Processing..');

			var formData = getFormData("template_form");
			var vmsUrl ='${SourceLeadWeb_vmsUrl}';
			// 			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.post(vmsUrl
					+ "/saveTemplate?${_csrf.parameterName}=${_csrf.token}",
					formData, function(data) {
						if (data != null) {
							location.replace(vmsUrl
									+ "/orgContractTemplate");
						}
					});

			/* $.ajax({
						type : "POST",
			    	    data: formData,
			    	    contentType : 'application/json; charset=utf-8',
				        processData: false,
				        contentType:false, 
			    	    dataType : 'json',
						url : sourceleadUrl
							+ "/saveTemplate?${_csrf.parameterName}=${_csrf.token}", 
						success : function(result) {
							 /* generatePDF(result);  
							location.replace(sourceleadUrl+ "/orgContractTemplate");
						},
					});  */
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#template_form', function() {
		$("#template_form").showHelpOnFocus();
		$("#template_form").validateOnBlur(false, validationSettings);
	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		var templateId = "${templateId}";
		editContractTemplate(templateId);
	});
</script>

<script>
	function editContractTemplate(templateId) {

		contractTemplateType();
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		if (templateId != '') {
			$
					.ajax({
						type : "GET",
						url : vmsUrl
								+ "/getEditContractTemplateDetails?${_csrf.parameterName}=${_csrf.token}&templateId="
								+ templateId,
						success : function(data) {
							var json = JSON.parse(data);
							$(".Editor-container").find('.Editor-editor').html(
									json['templateContent']);
						},
					});
		}

	}
</script>
<script>
	function validateContractTemplateName() {
		var templateName = $('#templateName').val();
		var contractType = $('#contractType').val();
		var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		
		var templateUUID = '${contractsTemplate.templateUUID}';
		
		if (templateName != null && templateName != "" && contractType != null
				&& contractType != "") {
			$
					.ajax({
						type : "POST",
						url : vmsUrl
								+ "/validateContractTemplateName?${_csrf.parameterName}=${_csrf.token}&templateName="
								+ templateName + "&contractType="
								+ contractType + "&templateUUID="
								+templateUUID,
						success : function(data) {
							if (data == 'true') {

								$("input#templateName").after(
										"<p class='jquery_form_error_message'>"
												+ templateName
												+ " is Already Existed</span>");
								$("#save_template")
										.attr("disabled", "disabled");
								//  $("#save_template").addClass('button').val('Processing..');
							} else {
								$('input#templateName').removeAttr(
										"record-exist record-exist-errormsg");
								$('#templateDiv').find(
										'p.jquery_form_error_message').remove();
								$("#save_template").addClass('button').val(
										'Save');
								$("#save_template").removeAttr("disabled");
						}
						}
					});
		}
	}
</script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->


<script>
	$(document).ready(
			function() {
				$("#txtEditor").Editor();
				$("#txtEditor").find(".Editor-editor").data
						- validation('validate_length length2-5000');
				$("#txtEditor").find(".Editor-editor").data - validation
						- optional('rule-required', true);
			});
</script>

