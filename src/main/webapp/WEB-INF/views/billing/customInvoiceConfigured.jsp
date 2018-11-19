<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Logo Part -->
<li id="invoiceLogoGrid" data-section="image" data-id="invoiceLogo" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
	<span class="">Logo</span>
	<div>
		<input class="checkbox-custom" id="cb1" checked="checked" type="checkbox">

		<c:if test="${!empty SourceLeadWeb_org.fileContentType}">
			<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
				<img src="${SourceLeadWeb_org.fileUrl}" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)" data-id="invoiceLogo" style="width: 50%;">
			</c:if>
			<c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
				<img src="${SourceLeadWeb_org.fileUrl}" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)" data-id="invoiceLogo" style="width: 50%;">
			</c:if>
		</c:if>

		<c:if test="${empty SourceLeadWeb_org.fileContentType}">
			<div class="text-logo">
				<c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
					<c:if test="${!empty SourceLeadWeb_orgName}">
						<img src="/resources/images/logo-sourceld.png" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)" data-id="invoiceLogo" style="width: 50%;">
					</c:if>
				</c:if>
				<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
					<img src="/resources/images/logo-sourceld.png" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)" data-id="invoiceLogo" style="width: 50%;">
				</c:if>
			</div>
		</c:if>

	</div>
	<span class="movIcon"></span>
</li>

<li data-section="hidden" class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
<!-- Logo Part -->

<!-- Company Heading -->
<li data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
	<span class="">Organisation Name</span>
	<div class="cusInList">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)">Organisation Name</label>
	</div>
	<span class="movIcon"></span>
</li>
<li data-section="hidden" class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
<!-- Company Heading -->
