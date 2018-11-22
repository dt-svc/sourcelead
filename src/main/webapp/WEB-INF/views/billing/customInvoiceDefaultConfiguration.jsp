<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Logo Part -->
<input type="hidden" id="invoiceTemplateId" value="0"/>
<li id="invoiceLogoGrid" data-name="Logo" data-section="image" data-id="invoiceLogo" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
	<span class="">Logo (Default)</span>
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
<li data-name="Organisation Name" data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
	<span class="">Organisation Name</span>
	<div class="cusInList">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)">Organisation Name</label>
	</div>
	<span class="movIcon"></span>
</li>
<li data-section="hidden" class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
<!-- Company Heading -->

<li data-name="Client Details" data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
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
<li data-name="Invoice Details" data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid eleRight">
	<span class="">Invoice Details</span>
	<div id="invoiceDetails">
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Invoice Id</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Invoice Date</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Due Date</label>
		</div>
		<div class="cusInList" style="text-align: right;">
			<input class="checkbox-custom" checked="checked" type="checkbox">
			<label onclick="showTextSettingsTab(this)">Payment Terms</label>
		</div>
	</div> 
	<span class="movIcon"></span>
</li>
<!-- Invoice Details -->

<!-- To -->
<li data-name="To" data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
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
	</div> 
	<span class="movIcon"></span>
</li>
<!-- To -->

<!-- Bank Details -->
<li data-name="Banking Details" data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid eleRight">
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
	</div> 
	
	<span class="movIcon"></span>
</li>
<!-- Banking Details -->

<!-- Invoice Table -->
<li data-name="Invoice Table" id="invoiceTableGrid" data-section="table" data-id="invoiceTable" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid" style="width: 98%;">
	<span class="">Invoice Table</span>
	<div class="project_tabs_inn_table">
		<table onclick="showTableSettingsTab(this)" id="invoiceTable" data-id="invoiceTable">
			<thead>
				<tr>
					<th>Column 1</th>
					<th>Column 2</th>
					<th>Column 3</th>
					<th>...</th>
					<th>...</th>
					<th>Column n</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Value 1</td>
					<td>Value 2</td>
					<td>Value 3</td>
					<td>...</td>
					<td>...</td>
					<td>Value n</td>
				</tr>
			</tbody>
		</table>
	</div>
	<span class="movIcon"></span>
</li>
<!-- Invoice Table -->

<!-- Discount -->
<li data-section="hidden" class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
<li data-name="Discount" data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid eleRight">
	<span class="">Discount</span>
	<div class="cusInList" style="text-align: right;">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)" style="text-align: right;">Discount</label>
	</div>
	<span class="movIcon"></span>
</li>
<!-- Discount -->

<!-- Total -->
<li data-section="hidden" class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
<li data-name="Total" data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid eleRight">
	<span class="">Total</span>
	<div class="cusInList" style="text-align: right;">
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)" style="text-align: right;">Grand Total</label>
	</div>
	<span class="movIcon"></span>
</li>
<!-- Total -->

<!-- Footer -->
<li data-name="Footer" data-section="footer" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid" style="width: 98%;">
	<span class="">Footer</span>
	<div>
		<input class="checkbox-custom" checked="checked" type="checkbox">
		<label onclick="showTextSettingsTab(this)">Address</label>
	</div>
	<span class="movIcon"></span>
</li>
<!-- Footer -->