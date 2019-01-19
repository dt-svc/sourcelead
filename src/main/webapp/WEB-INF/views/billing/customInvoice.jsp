<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resources/js/custom-invoice/custom-invoice.js"></script>

<div class="success_pop" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Invoice Template Saved</span>
</div>

<section class="content-wraper">
	<div class="invLeftWrap">
		<ul class="invTabs clearfix">
			<li class="current" id="textSettingsTabHead">
				<a href="javascript:void(0)" onclick="showTextSettingsTab(null)" class="Act"><i class="fa fa-file-text-o paddingRight"></i>Text Settings</a>
			</li>
			<li id="imageSettingsTabHead">
				<a href="javascript:void(0)" onclick="showImageSettingsTab(null)"><i class="fa fa-file-image-o paddingRight"></i>Image Settings</a>
			</li>
			<li id="tableSettingsTabHead">
				<a href="javascript:void(0)" onclick="showTableSettingsTab(null)"><i class="fa fa-table paddingRight"></i>Table Settings</a>
			</li>
		</ul>
		<div id="textSettingsTab" class="project_tabs_inn" style="display: block;">
			<div class="clearfix invLeftWrapCta">
				<div class="row mb">
					<div class="col-md-6 mb">
						<label class="clearfix text-muted">Font Family</label>
						<div class="clearfix">
							<select id="textFontFamily" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="font_family" data-prefix="font-family" data-suffix="">
								<!-- <option value="Arial">Arial</option>
								<option value="SansSerif">Sans Serif</option>
								<option value="Courier New">Courier</option>
								<option value="Georgia">Georgia</option>
								<option value="Impact">Impact</option>
								<option value="Magneto">Magneto</option>
								<option value="Times New Roman">Times New Roman</option>
								<option value="Verdana">Verdana</option> -->
								
								<!-- QA Fonts -->
								<!-- <option value="DejaVu Sans">DejaVu Sans</option>
								<option value="DejaVu Sans Condensed">DejaVu Sans Condensed</option>
								<option value="DejaVu Sans Light">DejaVu Sans Light</option>
								<option value="DejaVu Sans Mono">DejaVu Sans Mono</option>
								<option value="DejaVu Serif">DejaVu Serif</option>
								<option value="DejaVu Serif Condensed">DejaVu Serif Condensed</option>
								<option value="SansSerif">SansSerif</option> -->
								
								
								<!-- Production Fonts -->
								<!-- <option value="Lucida Bright">Lucida Bright</option>
								<option value="Lucida Sans">Lucida Sans</option>
								<option value="Lucida Sans Typewriter">Lucida Sans Typewriter</option>
								<option value="SansSerif">SansSerif</option> -->
								
								<c:forEach items="${fonts}" var="font">
									<option value="${font}" ${font == systemDefaultFont ? "selected" : ""}>${font}</option>
								</c:forEach>
								
								
							</select>
						</div>
					</div>
					<div class="col-md-6 mb">
						<label class="clearfix text-muted">Font Style</label>
						<div class="clearfix">
							<select id="textFontStyle" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="font_style" data-prefix="" data-suffix="">
								<option value="font-weight:400;">Normal</option>
								<!-- <option value="font-weight:600;">Semi Bold</option> -->
								<option value="font-weight:700;">Bold</option>
								<option value="font-style:italic;">Italic</option>
								<option value="font-style:italic;font-weight:600;">Italic Bold</option>
							</select>
						</div>
					</div>
					<div class="col-md-6 mb">
						<label class="clearfix text-muted">Font Size</label>
						<div class="clearfix">
							<select id="textFontSize" class="form-control input-sm" onchange="applyTextSettings(false)" data-prefix="font-size" data-suffix="">
								<option value="small">Small</option>
								<option value="medium" selected="selected">Medium</option>
								<option value="large">Large</option>
								<option value="extraLarge">Extra Large</option>							
							</select>
						</div>
					</div>
					<div class="col-md-6 mb">
						<label class="clearfix text-muted">Text Align</label>
						<div class="clearfix">
							<select id="textFontAlign" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="text_align" data-prefix="text-align" data-suffix="">
								<option value="left">Left</option>
								<option value="center">Center</option>
								<option value="right">Right</option>
							</select>
						</div>
					</div>
					<div class="col-md-6 mb">
						<label class="clearfix text-muted">Text Color</label>
						<div class="clearfix"></div>
						<div class="input-group">
							<input id="textTextColor" type="text" class="form-control input-sm" name="favcolor" placeholder="#545454" data-type="color">
							<span class="input-group-addon" style="padding: 2px 10px;">
								<input type="color" id="textTextColorSelector" value="#545454" data-prefix="color" data-suffix=""
								style="width: 20px; height: 20px;" onChange="colorPicker('textTextColorSelector', 'textTextColor'); applyTextSettings(false);">
							</span>
						</div>
					</div>
					<div class="col-md-6 mb">
						<label class="clearfix text-muted">Background Color</label>
						<div class="clearfix">
						
						<div class="col-md-3 mt5 checkbox-new paddingLeft">
							<input class="checkbox-custom" id="textBGColorCB" type="checkbox" onclick="applyTextSettings(false);">
							<label for="textBGColorCB" class="checkbox-custom-label"></label>									
						</div>
						<div class="input-group col-md-9">
							<input id="textBGColor" type="text" class="form-control input-sm" name="favcolor" placeholder="#545454" data-type="background_color">
							<span class="input-group-addon" style="padding: 2px 10px;">
								<input type="color" id="textBGColorSelector" value="#545454" data-prefix="background-color" data-suffix=""
								style="width: 20px; height: 20px;" onChange="colorPicker('textBGColorSelector', 'textBGColor'); applyTextSettings(false);">
							</span>
						</div>
						</div>
					</div>
				</div>
				<!-- <div class="row form-group clearfix mb">
					<label class="col-md-12 mb10 text-muted">Margin (in pixels)</label>
					<div class="col-md-3 paddingRight">
						<input type="text" id="textMarginTop" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="margin_top" data-prefix="margin-top" data-suffix="px">
						<span class="text-muted" style="font-size: 10px;">Top</span>
					</div>
					<div class="col-md-3 paddingLeft paddingRight">
						<input type="text" id="textMarginRight" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="margin_right" data-prefix="margin-right" data-suffix="px">
						<span class="text-muted" style="font-size: 10px;">Right</span>
					</div>
					<div class="col-md-3 paddingLeft paddingRight">
						<input type="text" id="textMarginBottom" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="margin_bottom" data-prefix="margin-bottom" data-suffix="px">
						<span class="text-muted" style="font-size: 10px;">Bottom</span>
					</div>
					<div class="col-md-3 paddingLeft">
						<input type="text" id="textMarginLeft" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="margin_left" data-prefix="margin-left" data-suffix="px">
						<span class="text-muted" style="font-size: 10px;">Left</span>
					</div>
				</div> -->
				<form id="paddingForm">
					<div class="row clearfix">
						<label class="col-md-12 mb10 text-muted">Padding (in pixels)</label>
						<div class="col-md-3 paddingRight">
							<input type="text" id="textPaddingTop" placeholder="0" value="0" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_top" data-prefix="padding-top" data-suffix="px" data-validation="validate_number validate_length length1-2" field-name="Top">
							<span class="text-muted" style="font-size: 10px;">Top</span>
						</div>
						<div class="col-md-3 paddingLeft paddingRight">
							<input type="text" id="textPaddingRight" placeholder="0" value="0" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_right" data-prefix="padding-right" data-suffix="px" data-validation="validate_number validate_length length1-2" field-name="Right">
							<span class="text-muted" style="font-size: 10px;">Right</span>
						</div>
						<div class="col-md-3 paddingLeft paddingRight">
							<input type="text" id="textPaddingBottom" placeholder="0" value="0" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_bottom" data-prefix="padding-bottom" data-suffix="px" data-validation="validate_number validate_length length1-2" field-name="Bottom">
							<span class="text-muted" style="font-size: 10px;">Bottom</span>
						</div>
						<div class="col-md-3 paddingLeft">
							<input type="text" id="textPaddingLeft" placeholder="0" value="0" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_left" data-prefix="padding-left" data-suffix="px" data-validation="validate_number validate_length length1-2" field-name="Left">
							<span class="text-muted" style="font-size: 10px;">Left</span>
						</div>
					</div>
				</form>
			</div>
			<div class="popupFoot">
				<a class="slBtn md colorBtn configureBtn disabled" id="applyTextSettingsBtn" href="javascript:void(0)" onclick="applyTextSettings(true)">Save</a>
				<a class="slBtn md configureBtn disabled" id="cancelTextSettingsBtn" href="javascript:void(0)" onclick="cancelTextSettings()">Cancel</a>
			</div>					
		</div>
		<div id="imageSettingsTab" class="project_tabs_inn">
			<div class="clearfix invLeftWrapCta">
				<div class="form-group row">
					<div class="col-md-12">
						<label class="control-label text-muted">Background Image</label>
						<div class="clearfix">
							<div class="input-group positionRelative">
								<label class="input-group-btn">
								<span class="btn btn-info">Upload Image 
									<input style="display: none;" multiple type="file" id="logoImageSelector" onchange="uploadLogo()">
								</span>
								</label>
								<input class="form-control input-custom" readonly type="text" id="logoImage">
								<i class="refreshinputfile"></i>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group row mt">
					<div class="col-md-12 mb">
						<label class="control-label text-muted">Image Position</label>
						<div class="clearfix">
							<select class="sel" id="imagePosition" onchange="applyImageSettings(false)">
								<option value="left">Left</option>
								<!-- <option value="none" selected>Center</option> -->
								<option value="right">Right</option>
							</select>
						</div>
									</div>
					<div class="col-md-12 mb">
						<label class="control-label text-muted">Image Size</label>
						<div class="clearfix">
							<select class="sel" id="imageSize" onchange="applyImageSettings(false)">
								<option value="25%">25%</option>
								<option value="50%" selected>50%</option>
								<option value="75%">75%</option>
								<option value="100%">100%</option>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="popupFoot">
				<a class="slBtn md colorBtn configureBtn disabled" id="applyImageSettingsBtn" href="javascript:void(0)" onclick="applyImageSettings(true)">Save</a>
				<a class="slBtn md configureBtn disabled" id="cancelImageSettingsBtn" href="javascript:void(0)" onclick="cancelImageSettings()">Cancel</a>
			</div>	
		</div>	
		<div id="tableSettingsTab" class="project_tabs_inn">
			<div class="clearfix invLeftWrapCta">
				<div id="innerTab2" class="templatetab_inn">
					<div class="project_tabs_inn_table">
						<div class="row">
							<label class="col-md-5">Table Border</label>
							<div class="col-md-1">
								<div class="checkbox-new pull-left mt5">
									<input id="tableBorderCB" class="checkbox-custom" name="bgColorhdr" type="checkbox" onclick="applyTableSettings(false)" checked="checked">
									<label for="tableBorderCB" class="checkbox-custom-label">
										<span class="ml10"></span>
									</label>
								</div>
							</div>
							<div class="col-md-6">
								<div class="input-group">
									<input id="tableBorderColor" class="form-control" name="tblBorder" placeholder="#989898" type="text">
									<span class="input-group-addon" style="padding: 2px 10px;">
										<input id="tableBorderColorSelector" value="#989898"
										style="width: 20px; height: 20px;" onchange="colorPicker('tableBorderColorSelector', 'tableBorderColor'); applyTableSettings(false);" type="color">
									</span>
								</div>
							</div>
						</div>
						<div class="clearfix">
							<h5>Table Header</h5>
						</div>
						<div class="form-group row">
							<label class="col-md-5">Font Size </label>
							<div class="col-md-7">
								<div class="input-group">
									<select class="form-control" id="tableHeadFontSize" onchange="applyTableSettings(false)">
										<option value="small">Small</option>
										<option value="medium">Medium</option>
										<option value="large">Large</option>
										<option value="extraLarge" selected="selected">Extra Large</option>								
									</select>
								</div>
							</div>
						</div>
						<div class="form-group row">
							<label class="col-md-5">Background Color</label>
							<div class="col-md-7">
								<div class="input-group">
									<input id="tableHeadBGColor" class="form-control" name="tblBorder" value="#6C7686" placeholder="#6C7686" type="text">
									<span class="input-group-addon" style="padding: 2px 10px;">
										<input id="tableHeadBGColorSelector" value="#6C7686" style="width: 20px; height: 20px;" 
										onchange="colorPicker('tableHeadBGColorSelector', 'tableHeadBGColor'); applyTableSettings(false);" type="color">
									</span>
								</div>
							</div>
						</div>
						<div class="row mt">
							<label class="col-md-5">Font Color</label>
							<div class="col-md-7">
								<div class="input-group">
									<input id="tableHeadFontColor" class="form-control" name="tblBorder" value="#FFFFFF" placeholder="#FFFFFF" type="text">
									<span class="input-group-addon" style="padding: 2px 10px;">
										<input id="tableHeadFontColorSelector" value="#FFFFFF" style="width: 20px; height: 20px;" 
										onchange="colorPicker('tableHeadFontColorSelector', 'tableHeadFontColor'); applyTableSettings(false);"
										type="color">
									</span>
								</div>
							</div>
						</div>
						<div class="clearfix">
							<h5>Table Row</h5>
						</div>
						<div class="form-group row">
							<label class="col-md-5">Font Size</label>
							<div class="col-md-7">
								<div class="input-group">
									<select class="form-control" id="tableBodyFontSize" onchange="applyTableSettings(false)">
										<option value="small">Small</option>
										<option value="medium">Medium</option>
										<option value="large" selected="selected">Large</option>
										<option value="extraLarge">Extra Large</option>								
									</select>
								</div>
							</div>
						</div>
						<div class="form-group row mt">
							<label class="col-md-5">Background Color</label>
							<div class="col-md-7">
								<div class="input-group">
									<input id="tableBodyBGColor" class="form-control" name="itemrowFontclor" value="#F9F9F9" placeholder="#F9F9F9" type="text">
									<span class="input-group-addon" style="padding: 2px 10px;">
										<input id="tableBodyBGColorSelector" value="#F9F9F9"
										style="width: 20px; height: 20px;" onchange="colorPicker('tableBodyBGColorSelector', 'tableBodyBGColor'); applyTableSettings(false);" type="color">
									</span>
								</div>
							</div>
						</div>
						<div class="row mt">
							<label class="col-md-5">Font Color</label>
							<div class="col-md-7">
								<div class="input-group">
									<input id="tableBodyFontColor" class="form-control" name="itemrowFontclor" value="#333" placeholder="#333" type="text">
									<span class="input-group-addon" style="padding: 2px 10px;">
										<input id="tableBodyFontColorSelector" value="#333" style="width: 20px; height: 20px;"
										 onchange="colorPicker('tableBodyFontColorSelector', 'tableBodyFontColor'); applyTableSettings(false);" type="color">
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="popupFoot">
				<a class="slBtn md colorBtn configureBtn disabled" id="applyTableSettingsBtn" href="javascript:void(0)" onclick="applyTableSettings(true)">Save</a>
				<a class="slBtn md configureBtn disabled" id="cancelTableSettingsBtn" href="javascript:void(0)" onclick="cancelTableSettings()">Cancel</a>
			</div>
		</div>
	</div>
	<div class="invoicePreview invoicePreviewInn clearfix">
		<form id="invoiceTemplateForm">
			<input type="hidden" value="${invoiceTemplate != null ? invoiceTemplate.id : 0}" id="templateId">
			<div class="col-md-12 mb">
				<label class="clearfix text-muted">Name<span class="text-danger">&nbsp;*</span></label>
				<div class="clearfix"></div>
				<div class="input-group">
					<input id="templateName" value="${invoiceTemplate.name}" class="form-control" placeholder="Template Name" type="text" title="Template Name" field-name="Name" data-validation="required validate_length length1-100">
				</div>
			</div>
			<div class="col-md-12 mb">
				<label class="clearfix text-muted">Description</label>
				<div class="clearfix"></div>
				<textarea id="templateDescription" class="form-control" placeholder="Template Description" title="Template Description" field-name="Description" data-validation="validate_length length0-1000">${invoiceTemplate.description}</textarea>
			</div>
		</form>
	</div>
	<div class="invoicePreview">
		<div class="invoicePreviewInn clearfix">
			<ul class="list-unstyled customInvoiceItems clearfix" id="configItemsList">
				<c:choose>
					<c:when test="${invoiceTemplate != null}">
						<jsp:include page="./customInvoiceConfigured.jsp"></jsp:include>
					</c:when>
					<c:otherwise>
						<jsp:include page="./customInvoiceDefaultConfiguration.jsp"></jsp:include>
					</c:otherwise>
				</c:choose>
			</ul>
			<div class="invBtn">
				<input class="slBtn md" value="Preview" id="update_user" onclick="showTemplatePreview(true);" type="button">
				<input class="slBtn md colorBtn" value="${invoiceTemplate != null ? 'Update' : 'Save'}" onclick="saveTemplate(false);" type="button">
				<%-- <input class="slBtn md colorBtn" value="${invoiceTemplate != null ? 'Update' : 'Save'}" onclick="saveTemplate();" type="button"> --%>
			</div>
			<jsp:include page="../masterFooter.jsp" />
		</div>
	</div>
</section>
 
<div class="cusPerPop" id="templatePreview">
	<div class="cusPerPopInn">
		<div class="popupHead"><span>Template Preview</span>
			<i class="closePop pull-right popClose" onclick="closePreviewPopup()"></i> 
		</div>
		<div class="popupContent clearfix" id="previewItems"></div>
		<div class="popupFoot text-right">
          <button type="button" class="slBtn md" onclick="closePreviewPopup()"> Cancel</button>
          <input class="slBtn md colorBtn" value="${invoiceTemplate != null ? 'Update' : 'Save'}" onclick="saveTemplate(true);" type="button">
        </div>	
	</div>
</div>
<script type="text/javascript">
	var activeElement = null;
	var orgLocationId = "${orgLocationId}";
	var orgRelationId = "${orgRelationId}";
	var systemDefaultFont = "${systemDefaultFont}";
</script>