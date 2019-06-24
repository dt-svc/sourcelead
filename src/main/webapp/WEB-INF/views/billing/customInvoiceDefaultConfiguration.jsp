<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Logo Part -->
<%-- <input type="hidden" id="invoiceTemplateId" value="${invoiceTemplate.id}"/> --%>
<c:set var="position" value="1"></c:set>
<li id="invoiceLogoGrid" data-name="Logo" data-section="image" data-position="${position}" data-id="invoiceLogo" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
	<c:set var="position" value="${position + 1}"></c:set>
	<span class="">Logo (Default)</span>
	<div>
		<input class="checkbox-custom" id="cb1" checked="checked" type="checkbox">

		<c:if test="${!empty SourceLeadWeb_org.fileContentType}">
			<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
				<img src="${SourceLeadWeb_org.fileUrl}" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)" data-id="invoiceLogo" style="width:50%;float:left;">
			</c:if>
			<c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
				<img src="${SourceLeadWeb_org.fileUrl}" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)" data-id="invoiceLogo" style="width:50%;float:left;">
			</c:if>
		</c:if>

		<c:if test="${empty SourceLeadWeb_org.fileContentType}">
			<div class="text-logo">
				<c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
					<c:if test="${!empty SourceLeadWeb_orgName}">
						<img src="/resources/images/logo-sourceld.png" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)" data-id="invoiceLogo" style="width:50%;float:left;">
					</c:if>
				</c:if>
				<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
					<img src="/resources/images/logo-sourceld.png" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)" data-id="invoiceLogo" style="width:50%;float:left;">
				</c:if>
			</div>
		</c:if>

	</div>
	<span class="movIcon"></span>
</li>

<li data-section="hidden" data-position="${position}" class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
<c:set var="position" value="${position + 1}"></c:set>
<!-- Logo Part -->

<%-- 
<!-- Company Heading -->
<li data-name="Organisation Name" data-section="text" data-position="${position}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
	<c:set var="position" value="${position + 1}"></c:set>
	<span class="">Organisation Name</span>
	<div class="cusInList">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)">Organisation Name</label>
	</div>
	<span class="movIcon"></span>
</li>
<li data-section="hidden" data-position="${position}" class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
<c:set var="position" value="${position + 1}"></c:set>
<!-- Company Heading -->
 --%>
 
<li data-name="Client Details" data-section="text" data-position="${position}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
	<c:set var="position" value="${position + 1}"></c:set>
	<span class="">Client Details</span>
		<div class="cusInList">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Organisation Name</label>
		</div>
		<div class="cusInList">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Sender Name</label>
		</div>
		<div class="cusInList">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Sender Email Id</label>
		</div>
		<div class="cusInList">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Address</label>
		</div>
	
	<span class="movIcon"></span>
</li>

<!-- Invoice Details -->
<li data-name="Invoice Details" data-section="text" data-position="${position}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid eleRight">
	<c:set var="position" value="${position + 1}"></c:set>
	<span class="">Invoice Details</span>
	<div id="invoiceDetails">
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">Invoice Id</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">Invoice Date</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">Due Date</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">Payment Terms</label>
		</div>
	</div> 
	<span class="movIcon"></span>
</li>
<!-- Invoice Details -->

<!-- To -->
<li data-name="To" data-section="text" data-position="${position}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
	<c:set var="position" value="${position + 1}"></c:set>
	<span class="">To</span>
	<div id="invoiceTo">
		<div class="cusInList">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Recipient Name</label>
		</div>
		<div class="cusInList">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Recipient Email Id</label>
		</div>
		<div class="cusInList">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Client Name</label>
		</div>
		<div class="cusInList">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Address</label>
		</div>
		<div class="cusInList">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Mobile Number</label>
		</div>
	</div> 
	<span class="movIcon"></span>
</li>
<!-- To -->

<!-- Bank Details -->
<li data-name="Banking Details" data-section="text" data-position="${position}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid eleRight">
	<c:set var="position" value="${position + 1}"></c:set>
	<span class="">Banking Details</span>
	<div id="bankingDetails">
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">Account Type</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">Bank Name</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">Account No</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">IFSC Code</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">First Name</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">Last Name</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)" style="text-align: right;">Email</label>
		</div>
	</div> 
	
	<span class="movIcon"></span>
</li>
<!-- Banking Details -->

<!-- Invoice Table -->
<li data-name="Invoice Table" id="invoiceTableGrid" data-section="table" data-id="invoiceTable" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid" style="width: 98%;">
	<span class="">Invoice Table</span>
	<div class="project_tabs_inn_table">
		<table onclick="showTableSettingsTab(this)" id="invoiceTable" data-id="invoiceTable">
			<thead data-style="font-size: 12px;">
				<tr>
					<th style="font-size: 12px;">Column 1</th>
					<th style="font-size: 12px;">Column 2</th>
					<th style="font-size: 12px;">Column 3</th>
					<th style="font-size: 12px;">...</th>
					<th style="font-size: 12px;">...</th>
					<th style="font-size: 12px;">Column n</th>
				</tr>
			</thead>
			<tbody data-style="font-size: 10px;">
				<tr>
					<td style="font-size: 10px;">Value 1</td>
					<td style="font-size: 10px;">Value 2</td>
					<td style="font-size: 10px;">Value 3</td>
					<td style="font-size: 10px;">...</td>
					<td style="font-size: 10px;">...</td>
					<td style="font-size: 10px;">Value n</td>
				</tr>
			</tbody>
		</table>
	</div>
	<span class="movIcon"></span>
</li>
<!-- Invoice Table -->

<!-- Memo / Description -->
<li data-section="hidden" data-position="${position}" class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
<c:set var="position" value="${position + 1}"></c:set>
<li data-name="Memo / Description" data-section="text" data-position="${position}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid eleRight">
	<c:set var="position" value="${position + 1}"></c:set>
	<span class="">Memo / Description</span>
	<div class="cusInList" style="text-align: right;">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)" style="text-align: right;">Memo / Description</label>
	</div>
	<span class="movIcon"></span>
</li>
<!-- Memo / Description -->

<!-- Total -->
<li data-section="hidden" data-position="${position}" class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
<c:set var="position" value="${position + 1}"></c:set>
<li data-name="Total" data-section="text" data-position="${position}" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid eleRight">
	<c:set var="position" value="${position + 1}"></c:set>
	<span class="">Total</span>
	<div class="cusInList" style="text-align: right;">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)" style="text-align: right;">Discount</label>
	</div>
	<div class="cusInList" style="text-align: right;">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)" style="text-align: right;">Grand Total</label>
	</div>
	<span class="movIcon"></span>
</li>
<!-- Total -->

<!-- Footer -->
<li data-name="Footer" data-section="footer" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid disable-drag" style="width: 98%;">
	<span class="">Footer</span>
	<div class="cusInList">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)">Org Name</label>
	</div>
	<div class="cusInList">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)">Address</label>
	</div>
	<div class="cusInList">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)">ZipCode</label>
	</div>
	<!-- <span class="movIcon"></span> -->
</li>
<!-- Footer -->