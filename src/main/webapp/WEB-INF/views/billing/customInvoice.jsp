<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

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
			<!-- <li>
				<a href="javascript:void(0)" onclick="showTemplatePreview();"><i class="fa fa-table paddingRight"></i>Preview</a>
			</li> -->
		</ul>
		
			<div id="textSettingsTab" class="project_tabs_inn" style="display: block;">
					
					<div class="clearfix invLeftWrapCta">
						<div class="row mb">
							<div class="col-md-6 mb">
								<label class="clearfix text-muted">Font Family</label>
								<div class="clearfix">
									<select id="textFontFamily" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="font_family" data-prefix="font-family" data-suffix="">
										<option value="arial">Arial</option>
										<option value="sans-serif">Sans Serif</option>
										<option value="courier">Courier</option>
										<option value="georgia">Georgia</option>
										<option value="impact">Impact</option>
										<option value="magneto">Magneto</option>
										<option value="Times New Roman">Times New Roman</option>
										<option value="verdana">Verdana</option>
									</select>
								</div>
							</div>
							<div class="col-md-6 mb">
								<label class="clearfix text-muted">Font Style</label>
								<div class="clearfix">
									<select id="textFontStyle" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="font_style" data-prefix="" data-suffix="">
										<option value="font-weight:400;">Normal</option>
										<option value="font-weight:600;">Semi Bold</option>
										<option value="font-weight:700;">Bold</option>
										<option value="font-style:italic;">Italic</option>
										<option value="font-style:italic;font-weight:600;">Italic Bold</option>
									</select>
								</div>
							</div>
							<div class="col-md-6 mb">
								<label class="clearfix text-muted">Font Size</label>
								<div class="clearfix"></div>
								<div class="input-group">
									<input id="textFontSize" type="text" class="form-control input-sm" name="favcolor" placeholder="12"  onchange="applyTextSettings(false)" data-type="font_size" data-prefix="font-size" data-suffix="px">
									<span class="input-group-addon">px</span>
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
						<div class="row form-group clearfix mb">
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
						</div>
						<div class="row clearfix">
							<label class="col-md-12 mb10 text-muted">Padding (in pixels)</label>
							<div class="col-md-3 paddingRight">
								<input type="text" id="textPaddingTop" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_top" data-prefix="padding-top" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Top</span>
							</div>
							<div class="col-md-3 paddingLeft paddingRight">
								<input type="text" id="textPaddingRight" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_right" data-prefix="padding-right" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Right</span>
							</div>
							<div class="col-md-3 paddingLeft paddingRight">
								<input type="text" id="textPaddingBottom" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_bottom" data-prefix="padding-bottom" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Bottom</span>
							</div>
							<div class="col-md-3 paddingLeft">
								<input type="text" id="textPaddingLeft" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_left" data-prefix="padding-left" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Left</span>
							</div>
						</div>
						
					</div>
					
						<div class="popupFoot">
							<a class="slBtn md colorBtn" href="javascript:void(0)" onclick="applyTextSettings(true)">Save</a>
							<a class="slBtn md" href="javascript:void(0)" onclick="cancelTextSettings()">Cancel</a>
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
										<option value="none" selected>Center</option>
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
						<a class="slBtn md colorBtn" href="javascript:void(0)" onclick="applyImageSettings(true)">Save</a>
						<a class="slBtn md" href="javascript:void(0)" onclick="cancelImageSettings()">Cancel</a>
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
											<input id="tableBorderCB" class="checkbox-custom" name="bgColorhdr" type="checkbox" onclick="applyTableSettings(false)">
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
											<input class="form-control" type="text" id="tableHeadFontSize" onclick="applyTableSettings(false)"> 
											<span class="input-group-addon">px</span>
										</div>
									</div>
								</div>
								<div class="form-group row">
									<label class="col-md-5">Background Color</label>
									
									<div class="col-md-7">
										<div class="input-group">
											<input id="tableHeadBGColor" class="form-control" name="tblBorder" placeholder="#6C7686" type="text">
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
											<input id="tableHeadFontColor" class="form-control" name="tblBorder"
												placeholder="#FFFFFF" type="text">
											<span class="input-group-addon" style="padding: 2px 10px;">
												<input id="tableHeadFontColorSelector" value="#FFFFFF"
												style="width: 20px; height: 20px;" onchange="colorPicker('tableHeadFontColorSelector', 'tableHeadFontColor'); applyTableSettings(false);"
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
											<input class="form-control" type="text" id="tableBodyFontSize" onclick="applyTableSettings(false)">
											<span class="input-group-addon">px</span>
										</div>
									</div>
								</div>
								<div class="form-group row mt">
									<label class="col-md-5">Background Color</label>
									<div class="col-md-7">
										<div class="input-group">
											<input id="tableBodyBGColor" class="form-control" name="itemrowFontclor" placeholder="#EAE6E6" type="text">
											<span class="input-group-addon" style="padding: 2px 10px;">
												<input id="tableBodyBGColorSelector" value="#EAE6E6"
												style="width: 20px; height: 20px;" onchange="colorPicker('tableBodyBGColorSelector', 'tableBodyBGColor'); applyTableSettings(false);" type="color">
											</span>
										</div>
									</div>
								</div>

								<div class="row mt">
									<label class="col-md-5">Font Color</label>
									<div class="col-md-7">
										<div class="input-group">
											<input id="tableBodyFontColor" class="form-control" name="itemrowFontclor" placeholder="#333" type="text">
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
						<a class="slBtn md colorBtn" href="javascript:void(0)" onclick="applyTableSettings(true)">Save</a>
						<a class="slBtn md" href="javascript:void(0)" onclick="cancelTableSettings()">Cancel</a>
					</div>

			</div>
	</div>

	
		<div class="invoicePreview">
			<div class="invoicePreviewInn clearfix">
				<ul class="list-unstyled customInvoiceItems clearfix" id="configItemsList">

					<!-- Logo Part -->
					<li id="invoiceLogo" data-section="image" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
						<span class="">Logo</span>
						<div>
							<input class="checkbox-custom" id="cb1" checked="checked" type="checkbox">
							<img src="/resources/images/logo-sourceld.png" id="logoImagePreview" alt="Logo" onclick="showImageSettingsTab(this)">
						</div>
						<span class="movIcon"></span>
					</li>
					
					<li class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
					<!-- Logo Part -->
					
					<!-- Company Heading -->
					<li data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
						<span class="">Company Heading</span>
						<div class="cusInList">
							<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
							<label onclick="showTextSettingsTab(this)">Company Heading</label>
						</div>
						<span class="movIcon"></span>
					</li>
					<li class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
					<!-- Company Heading -->
					
					<li data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
						<span class="">Client Details</span>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">BIS</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Mahaveer Manoj</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">manu.mahaveer@gmail.com</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">India, Telangana, Hyderabad</label>
							</div>
						
						<span class="movIcon"></span>
					</li>
					
					<!-- Invoice Details -->
					<li data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
						<span class="">Invoice Details</span>
						<div id="invoiceDetails">
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Invoice Id : 12345</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Invoice Date : 09/10/2018</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Due Date : 30/10/2018</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Payment Terms : 18 Days</label>
							</div>
						</div> 
						<span class="movIcon"></span>
					</li>
					<!-- Invoice Details -->
					
					<!-- To -->
					<li data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
						<span class="">To</span>
						<div id="invoiceTo">
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Snigita</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">snigitha@businessintelli.com</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Client Name</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">India, Hyderabad, Telangana</label>
							</div>
						</div> 
						<span class="movIcon"></span>
					</li>
					<!-- To -->
					
					<!-- Bank Details -->
					<li data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
						<span class="">Banking Details</span>
						<div id="bankingDetails">
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Account Type : Savings</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Bank Name : ICICI</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Acc No : 915010023154645</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">IFSC Code : SBIN007170</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">First Name : Mahaveer</label>
							</div>
							<div class="cusInList">
								<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
								<label onclick="showTextSettingsTab(this)">Last Name : Inumarthy</label>
							</div>
						</div> 
						
						<span class="movIcon"></span>
					</li>
					<!-- Banking Details -->
					
					<!-- Invoice Table -->
					<li data-section="table" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid" style="width: 98%;">
						<span class="">Invoice Table</span>
						<div class="project_tabs_inn_table">
							<table onclick="showTableSettingsTab(this)" id="invoiceTable">
								<thead>
									<tr>
										<th>Id</th>
										<th>Name</th>
										<th>Period</th>
										<th>Brief Description</th>
										<th>Qty</th>
										<th>Rate</th>
										<th>Total</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>0076</td>
										<td>Mahaveer</td>
										<td>08/10/2018 to 18/10/2018</td>
										<td>Description</td>
										<td>08</td>
										<td>2800</td>
										<td>45000.00</td>
									</tr>
								</tbody>
							</table>
						</div>
						<span class="movIcon"></span>
					</li>
					<!-- Invoice Table -->
					
					<!-- Discount -->
					<li class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
					<li data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
						<span class="">Discount</span>
						<div class="cusInList">
							<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
							<label onclick="showTextSettingsTab(this)">Discount (2 %) : 320.00</label>
						</div>
						<span class="movIcon"></span>
					</li>
					<!-- Discount -->
					
					<!-- Total -->
					<li class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
					<li data-section="text" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid">
						<span class="">Total</span>
						<div class="cusInList">
							<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
							<label onclick="showTextSettingsTab(this)">Grand Total :INR 15680.00</label>
						</div>
						<span class="movIcon"></span>
					</li>
					<!-- Total -->
					
					<!-- Footer -->
					<li data-section="footer" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid previewGrid" style="width: 98%;">
						<span class="">Footer</span>
						<div>
							<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
							<label onclick="showTextSettingsTab(this)">Address</label>
						</div>
						<span class="movIcon"></span>
					</li>
					<!-- Footer -->
				</ul>
				
				<div class="invBtn">
					<input class="slBtn md" value="Preview" id="update_user" onclick="showTemplatePreview();" type="button">
					<input class="slBtn md colorBtn" value="Save" onclick="saveTemplate();" type="button">
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
	
	<div class="popupContent clearfix" id="previewItems">
		<div class="previewInnerCta">
			<img src="/resources/images/logo-sourceld.png" style="float:center;width:50%;text-align: center;">
		</div>
		<div class="previewInnerCta">
			Company Heading 
		</div>
		<div class="previewInnerCta">
			<h3>Client Details</h3>
			BIS <br/>
			Mahaveer Manoj <br/>
			manu.mahaveer@gmail.com <br/>
			India, Hyderabad <br/>
		</div>
		<div class="previewInnerCta" style="float:right; text-align: right;">
		<h3>Invoice Details</h3>
			BIS <br/>
			Mahaveer Manoj <br/>
			manu.mahaveer@gmail.com <br/>
			India, Hyderabad <br/>			
		</div>
		<div class="previewInnerCta">
		<h3>To</h3>
			BIS <br/>
			Mahaveer Manoj <br/>
			manu.mahaveer@gmail.com <br/>
			India, Hyderabad <br/>				
		</div>
		<div class="previewInnerCta" style="float:right; text-align: right;">
			<h3>Banking Details</h3>
			BIS <br/>
			Mahaveer Manoj <br/>
			manu.mahaveer@gmail.com <br/>
			India, Hyderabad <br/>				
		</div>
		<div class="previewInnerCta previewInnerCtaFull">
		<div class="project_tabs_inn_table borBot">
			<table>
				<thead>
					<tr>
						<th>One</th>
						<th>Two</th>
						<th>Three</th>
						<th>Four</th>
						<th>Five</th>
						<th>Six</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>One</td>
						<td>One</td>
						<td>One</td>
						<td>One</td>
						<td>One</td>
						<td>One</td>
					</tr>
					<tr>
						<td>One</td>
						<td>One</td>
						<td>One</td>
						<td>One</td>
						<td>One</td>
						<td>One</td>
					</tr>					
				</tbody>
			</table>
			</div>
		</div>
		<div class="previewInnerCta" style="float:right; text-align: right; display: block; width: 100%;">
			Discount : type(text)
		</div>
		<div class="previewInnerCta" style="float:right; text-align: right; display: block; width: 100%;" >
			Total : type(text)
		</div>
		<div class="previewInnerCta previewInnerCtaFull">
			Footer : type(footer)
		</div>
		
	</div>
		<div class="popupFoot text-right">
          <button type="button" class="slBtn md" onclick="closePreviewPopup()"> Cancel</button>
        </div>	
</div>
</div> 



<script type="text/javascript">
	$(function() {
		$(".project-tabs-menu li a").click(function(event) {
			event.preventDefault();
			$(this).parent().addClass("current");
			$(this).parent().siblings().removeClass("current");
			var tab = $(this).attr("href");
			$(".project_tabs_inn").not(tab).css("display", "none");
			$(tab).fadeIn();
		});
	});

	$(document).ready(function() {
		$(".invoicetabsmenu li a").click(function(event) {
			event.preventDefault();
			$(this).parent().addClass("current");
			$(this).parent().siblings().removeClass("current");
			var inner = $(this).attr("href");
			$(".templatetab_inn").not(inner).css("display", "none");
			$(inner).fadeIn();
		});
	});

	function colorPicker(source, target) {
		var favColor = document.getElementById(source).value;
		document.getElementById(target).value = favColor;
	}
	
	var activeElement = null;
	
	function showTextSettingsTab(ele) {
		var flag = false;
		if(activeElement !== null) {
			if(confirm("cancel previous edit and continue?")) {
				flag = true;
				cancelTextSettings();
			}
		} else {
			flag = true;
		}
		if(flag) {
			//getting pre-filled values of activeElement
			var fontFamily = $(ele).data("font_family") === undefined ? "arial" : $(ele).data("font_family");
			var fontStyle = $(ele).data("font_style") === undefined ? "font-weight:400;" : $(ele).data("font_style");
			var fontSize = $(ele).data("font_size");
			var textAlign = $(ele).data("text_align") === undefined ? "left" : $(ele).data("text_align");
			var textColor = $(ele).data("color") === undefined ? "#545454" : $(ele).data("color");
			var bgColorCheck = $(ele).data("background_color_cb") === undefined ? false : $(ele).data("background_color_cb");
			var bgColor = $(ele).data("background_color") === undefined ? "#545454" : $(ele).data("background_color");
			var marginTop = $(ele).data("margin_top");
			var marginRight = $(ele).data("margin_right");
			var marginBottom = $(ele).data("margin_bottom");
			var marginLeft = $(ele).data("margin_left");
			var paddingTop = $(ele).data("padding_top");
			var paddingRight = $(ele).data("padding_right");
			var paddingBottom = $(ele).data("padding_bottom");
			var paddingLeft = $(ele).data("padding_left");

			//auto-fill of pre-filled values to edit components
			$("#textFontFamily").val(fontFamily);
			$("#textFontStyle").val(fontStyle);
			$("#textFontSize").val(fontSize);
			$("#textFontAlign").val(textAlign);
			$("#textTextColor").val(textColor);
			$("#textTextColorSelector").val(textColor);
			$("#textBGColorCB").prop("checked", bgColorCheck);
			$("#textBGColor").val(bgColor);
			$("#textBGColorSelector").val(bgColor);
			$("#textMarginTop").val(marginTop);
			$("#textMarginRight").val(marginRight);
			$("#textMarginBottom").val(marginBottom);
			$("#textMarginLeft").val(marginLeft);
			$("#textPaddingTop").val(paddingTop);
			$("#textPaddingRight").val(paddingRight);
			$("#textPaddingBottom").val(paddingBottom);
			$("#textPaddingLeft").val(paddingLeft);
			
			//hiding other tabs and showing text-settings tab
			$("#textSettingsTab").fadeIn();
			$("#imageSettingsTab").hide();
			$("#tableSettingsTab").hide();
			$("#textSettingsTabHead").addClass("current");
			$("#imageSettingsTabHead").removeClass("current");
			$("#tableSettingsTabHead").removeClass("current");
			$(".cusInList label").removeClass("activeElement");
			$(ele).addClass("activeElement");
			activeElement = ele;
		}
	}
	
	function applyTextSettings(saveFlag) {
		if(activeElement !== null) {
			//original values
			var textBGColorCB = $("#textBGColorCB").is(":checked");
			
			var fontFamily = $("#textFontFamily").val();
			var fontStyle = $("#textFontStyle").val();
			var fontSize = $("#textFontSize").val() === "" ? "14" : $("#textFontSize").val();
			var textAlign = $("#textFontAlign").val();
			var textColor = $("#textTextColorSelector").val();
			var bgColor = "";
			if(textBGColorCB)
				bgColor = $("#textBGColorSelector").val();
			else
				bgColor = "transparent";
			var marginTop = $("#textMarginTop").val() === "" ? "0" : $("#textMarginTop").val();
			var marginRight = $("#textMarginRight").val() === "" ? "0" : $("#textMarginRight").val();
			var marginBottom = $("#textMarginBottom").val() === "" ? "0" : $("#textMarginBottom").val();
			var marginLeft = $("#textMarginLeft").val() === "" ? "0" : $("#textMarginLeft").val();
	
			var paddingTop = $("#textPaddingTop").val() === "" ? "0" : $("#textPaddingTop").val();
			var paddingRight = $("#textPaddingLeft").val() === "" ? "0" : $("#textPaddingLeft").val();
			var paddingBottom = $("#textPaddingBottom").val() === "" ? "0" : $("#textPaddingBottom").val();
			var paddingLeft = $("#textPaddingLeft").val() === "" ? "0" : $("#textPaddingLeft").val();
			
			//converted to data attribute values
			var dataFontFamily = ($("#textFontFamily").data("prefix") !== "" ? $("#textFontFamily").data("prefix") + ":" : "") + fontFamily + "" + $("#textFontFamily").data("suffix");
			var dataFontStyle = ($("#textFontStyle").data("prefix") !== "" ? $("#textFontStyle").data("prefix") + ":" : "") + fontStyle + "" + $("#textFontFamily").data("suffix");
			var dataFontSize = ($("#textFontSize").data("prefix") !== "" ? $("#textFontSize").data("prefix") + ":" : "") + fontSize + "" + $("#textFontSize").data("suffix");
			var dataTextAlign = ($("#textFontAlign").data("prefix") !== "" ? $("#textFontAlign").data("prefix") + ":" : "") + textAlign + "" + $("#textFontAlign").data("suffix");
			var dataTextColor = ($("#textTextColorSelector").data("prefix") !== "" ? $("#textTextColorSelector").data("prefix") + ":" : "") + textColor + "" + $("#textTextColorSelector").data("suffix");
			var dataTextBGColorCB = textBGColorCB;
			var dataBgColor = "";
			if(textBGColorCB)
				dataBgColor = ($("#textBGColorSelector").data("prefix") !== "" ? $("#textBGColorSelector").data("prefix") + ":" : "") + bgColor + "" + $("#textBGColorSelector").data("suffix");
			else
				dataBgColor = "background-color:transparent";
			var dataMarginTop = ($("#textMarginTop").data("prefix") !== "" ? $("#textMarginTop").data("prefix") + ":" : "") + marginTop + "" + $("#textMarginTop").data("suffix");
			var dataMarginRight = ($("#textMarginRight").data("prefix") !== "" ? $("#textMarginRight").data("prefix") + ":" : "") + marginRight + "" + $("#textMarginRight").data("suffix");
			var dataMarginBottom = ($("#textMarginBottom").data("prefix") !== "" ? $("#textMarginBottom").data("prefix") + ":" : "") + marginBottom + "" + $("#textMarginBottom").data("suffix");
			var dataMarginLeft = ($("#textMarginLeft").data("prefix") !== "" ? $("#textMarginLeft").data("prefix") + ":" : "") + marginLeft + "" + $("#textMarginLeft").data("suffix");
	
			var dataPaddingTop = ($("#textPaddingTop").data("prefix") !== "" ? $("#textPaddingTop").data("prefix") + ":" : "") + paddingTop + "" + $("#textPaddingTop").data("suffix");
			var dataPaddingRight = ($("#textPaddingLeft").data("prefix") !== "" ? $("#textPaddingLeft").data("prefix") + ":" : "") + paddingRight + "" + $("#textPaddingLeft").data("suffix");
			var dataPaddingBottom = ($("#textPaddingBottom").data("prefix") !== "" ? $("#textPaddingBottom").data("prefix") + ":" : "") + paddingBottom + "" + $("#textPaddingBottom").data("suffix");
			var dataPaddingLeft = ($("#textPaddingLeft").data("prefix") !== "" ? $("#textPaddingLeft").data("prefix") + ":" : "") + paddingLeft + "" + $("#textPaddingLeft").data("suffix");
	
			$(activeElement).attr("style", dataFontFamily + ";" + dataFontStyle + ";" + dataFontSize + ";" + dataTextAlign + ";" + dataTextColor + ";" + dataTextBGColorCB + ";" + dataBgColor + ";" + dataMarginTop + ";" + dataMarginRight + ";" + dataMarginBottom + ";" + dataMarginLeft + ";" + dataPaddingTop + ";" + dataPaddingRight + ";" + dataPaddingBottom + ";" + dataPaddingLeft + ";");
		
			if(saveFlag) {
				//saving actual values as "data" attributes to active element
				$(activeElement).data("font_family", fontFamily);
				$(activeElement).data("font_size", fontSize);
				$(activeElement).data("font_style", fontStyle);
				$(activeElement).data("text_align", textAlign);
				$(activeElement).data("color", textColor);
				$(activeElement).data("background_color_cb", textBGColorCB);
				$(activeElement).data("background_color", bgColor);
				$(activeElement).data("margin_top", marginTop);
				$(activeElement).data("margin_right", marginRight);
				$(activeElement).data("margin_bottom", marginBottom);
				$(activeElement).data("margin_left", marginLeft);
				$(activeElement).data("padding_top", paddingTop);
				$(activeElement).data("padding_right", paddingRight);
				$(activeElement).data("padding_bottom", paddingBottom);
				$(activeElement).data("padding_left", paddingLeft);
				
				//saving full-named values as "data-prev" attributes to active element
				$(activeElement).data("prev_font_family", dataFontFamily);
				$(activeElement).data("prev_font_style", dataFontStyle);
				$(activeElement).data("prev_font_size", dataFontSize);
				$(activeElement).data("prev_text_align", dataTextAlign);
				$(activeElement).data("prev_color", dataTextColor);
				$(activeElement).data("prev_background_color_cb", dataTextBGColorCB);
				$(activeElement).data("prev_background_color", dataBgColor);
				$(activeElement).data("prev_margin_top", dataMarginTop);
				$(activeElement).data("prev_margin_right", dataMarginRight);
				$(activeElement).data("prev_margin_bottom", dataMarginBottom);
				$(activeElement).data("prev_margin_left", dataMarginLeft);
				$(activeElement).data("prev_padding_top", dataPaddingTop);
				$(activeElement).data("prev_padding_right", dataPaddingRight);
				$(activeElement).data("prev_padding_bottom", dataPaddingBottom);
				$(activeElement).data("prev_padding_left", dataPaddingLeft);
				
				$(activeElement).data("style_saved", true);
				activeElement = null;
				refreshTextSettings();
			}
		} else {
			alert("please select a text element");
		}
	}
	
	function refreshTextSettings() {
		$("#textFontFamily").val("arial");
		$("#textFontStyle").val("font-weight:400;");
		$("#textFontSize").val("");
		$("#textFontAlign").val("left");
		$("#textTextColor").val("");
		$("#textTextColorSelector").val("");
		$("#textBGColorCB").prop("checked", false);
		$("#textBGColor").val("");
		$("#textBGColorSelector").val("");
		$("#textMarginTop").val("");
		$("#textMarginRight").val("");
		$("#textMarginBottom").val("");
		$("#textMarginLeft").val("");
		$("#textPaddingTop").val("");
		$("#textPaddingRight").val("");
		$("#textPaddingBottom").val("");
		$("#textPaddingLeft").val("");
	}
	
	function refreshElementStyles(ele) {
		if($(ele).data("style_saved")) {
			var fontFamily = $(ele).data("prev_font_family");
			var fontSize = $(ele).data("prev_font_size");
			var fontStyle = $(ele).data("prev_font_style");
			var textAlign = $(ele).data("prev_text_align");
			var textColor = $(ele).data("prev_color");
			var textBGColorCB = $(ele).data("prev_background_color_cb");
			var bgColor = $(ele).data("prev_background_color");
			var marginTop = $(ele).data("prev_margin_top");
			var marginRight = $(ele).data("prev_margin_right");
			var marginBottom = $(ele).data("prev_margin_bottom");
			var marginLeft = $(ele).data("prev_margin_left");
			var paddingTop = $(ele).data("prev_padding_top");
			var paddingRight = $(ele).data("prev_padding_right");
			var paddingBottom = $(ele).data("prev_padding_bottom");
			var paddingLeft = $(ele).data("prev_padding_left");
			$(ele).attr("style", fontFamily + ";" + fontStyle + ";" + fontSize + ";" + textAlign + ";" + textColor + ";" + textBGColorCB + ";" + bgColor + ";" + marginTop + ";" + marginRight + ";" + marginBottom + ";" + marginLeft + ";" + paddingTop + ";" + paddingRight + ";" + paddingBottom + ";" + paddingLeft + ";");

			console.log("------------apply values----------")
			console.log(fontFamily + ";" + fontStyle + ";" + fontSize + ";" + textAlign + ";" + textColor + ";" + textBGColorCB + ";" + bgColor + ";" + marginTop + ";" + marginRight + ";" + marginBottom + ";" + marginLeft + ";" + paddingTop + ";" + paddingRight + ";" + paddingBottom + ";" + paddingLeft + ";");
		} else {
			$(ele).removeAttr("style");
		}
		
		/* var dataAttr = $(ele).data();
		for(var e in dataAttr) {
			$(ele).removeAttr("data-" + e);
		} */
	}
	
	function cancelTextSettings() {
		var ele = activeElement;
		activeElement = null;
		refreshTextSettings();
		refreshElementStyles(ele);
	}
	
	function showImageSettingsTab(ele) {
		var flag = false;
		if(activeElement !== null) {
			if(confirm("cancel previous edit and continue?")) {
				flag = true;
				refreshTextSettings();
			}
		} else {
			flag = true;
		}
		if(flag) {
			
			//hiding other tabs and showing image-settings tab
			$("#textSettingsTab").hide();
			$("#imageSettingsTab").fadeIn();
			$("#tableSettingsTab").hide();
			$("#textSettingsTabHead").removeClass("current");
			$("#imageSettingsTabHead").addClass("current");
			$("#tableSettingsTabHead").removeClass("current");
			activeElement = ele;
		}
	}
	
	function uploadLogo() {
		var files = document.getElementById("logoImageSelector").files;

		var data = new FormData();
		for(var e in files) {
			data.append("customInvoice",files[e]);
		}
		data.append("type","customInvoice");
		$.ajax({
			url : sourceleadUrl + "/ws/uploadTaxDocs",
			data : data,
			processData : false,
			type : 'POST',
			contentType : false,
			success : function(data) {
				if (data && data.length > 0) {
					$("#logoImage").val(data[0].fileName);
					$("#logoImagePreview").attr("src", data[0].fileURL.replace("customInvoice","temp"));
					applyImageSettings();
				}
			},
			error : function(err) {
				alert(err);
			}
		});
	}
	
	function applyImageSettings(saveFlag) {
		if(activeElement !== null) {
			var name = $("#logoImage").val();
			var src = $("#logoImagePreview").attr("src");
			var imagePosition = $("#imagePosition").val();
			var imageSize = $("#imageSize").val();
			
			$("#logoImagePreview").attr("src", src);
			$("#logoImagePreview").attr("style", "float:" + imagePosition + ";width : " + imageSize);
			
			if(saveFlag) {
				$(activeElement).data("name", name);
				$(activeElement).data("src", src);
				$(activeElement).data("image_position", imagePosition);
				$(activeElement).data("image_size", imageSize);
			}
		} else 
			alert("Please select target image");
	}
	
	function cancelImageSettings() {
		activeElement = null;
		refreshImageSettings();
	}
	
	function refreshImageSettings() {
		$("#logoImagePreview").attr("src", "/resources/images/logo-sourceld.png");
		$("#logoImage").val("");
		$("#imagePosition").val("center");
		$("#imageSize").val("50%");
	}
	
	function showTableSettingsTab(ele) {
		var flag = false;
		if(activeElement !== null) {
			if(confirm("cancel previous edit and continue?")) {
				flag = true;
				refreshTextSettings();
			}
		} else {
			flag = true;
		}
		if(flag) {
			$("#textSettingsTab").hide();
			$("#imageSettingsTab").hide();
			$("#tableSettingsTab").fadeIn();
			$("#textSettingsTabHead").removeClass("current");
			$("#imageSettingsTabHead").removeClass("current");
			$("#tableSettingsTabHead").addClass("current");
			activeElement = ele;
		}
	}
	
	function applyTableSettings(saveFlag) {
		if(activeElement !== null) {
			var tableBorderCB = $("#tableBorderCB").is(":checked");
			var tableBorderColor = $("#tableBorderColor").val();
			var tableBorderColorSelector = $("#tableBorderColorSelector").val();
			var tableHeadFontSize = $("#tableHeadFontSize").val() !== "" ? $("#tableHeadFontSize").val() : "14";
			var tableHeadBGColor = $("#tableHeadBGColor").val();
			var tableHeadBGColorSelector = $("#tableHeadBGColorSelector").val();
			var tableHeadFontColor = $("#tableHeadFontColor").val();
			var tableHeadFontColorSelector = $("#tableHeadFontColorSelector").val();
			var tableBodyFontSize = $("#tableBodyFontSize").val() !== "" ? $("#tableBodyFontSize").val() : "14";
			var tableBodyBGColor = $("#tableBodyBGColor").val();
			var tableBodyBGColorSelector = $("#tableBodyBGColorSelector").val();
			var tableBodyFontColor = $("#tableBodyFontColor").val();
			var tableBodyFontColorSelector = $("#tableBodyFontColorSelector").val();
			
			var thStyle = "";
			var tdStyle = "";
			
			if(tableBorderCB) {
				thStyle += "border:1px solid " + tableBorderColorSelector + ";";
				tdStyle += "border:1px solid " + tableBorderColorSelector + ";";
			} else {
				thStyle += "border:none;";
				tdStyle += "border:none;";
			}
			
			thStyle += "font-size:" + tableHeadFontSize + "px;";
			thStyle += "background-color:" + tableHeadBGColorSelector + "!important" + ";";
			thStyle += "color:" + tableHeadFontColorSelector + ";";
			$("#invoiceTable tr th").attr("style", thStyle);
			
			tdStyle += "font-size:" + tableBodyFontSize + "px;";
			tdStyle += "background-color:" + tableBodyBGColorSelector + "!important" + ";";
			tdStyle += "color:" + tableBodyFontColorSelector + ";";
			$("#invoiceTable tr td").attr("style", tdStyle);
			
			if(saveFlag) {
				$(activeElement).data("tableBorderCB", tableBorderCB);
				$(activeElement).data("tableHeadFontSize", tableHeadFontSize);
				$(activeElement).data("tableHeadBGColor", tableHeadBGColor);
				$(activeElement).data("tableHeadBGColorSelector", tableHeadBGColorSelector);
				$(activeElement).data("tableHeadFontColor", tableHeadFontColor);
				$(activeElement).data("tableHeadFontColorSelector", tableHeadFontColorSelector);
				$(activeElement).data("tableBodyFontSize", tableBodyFontSize);
				$(activeElement).data("tableBodyBGColor", tableBodyBGColor);
				$(activeElement).data("tableBodyBGColorSelector", tableBodyBGColorSelector);
				$(activeElement).data("tableBodyFontColor", tableBodyFontColor);
				$(activeElement).data("tableBodyFontColorSelector", tableBodyFontColorSelector);
			}
		} else 
			alert("Please select target table");
	}
	
	function cancelTableSettings() {
		activeElement = null;
		refreshTableSettings();
	}
	
	function refreshTableSettings() {
		$("#tableBorderCB").prop("checked", true);
		$("#tableBorderColor").val("#989898");
		$("#tableBorderColorSelector").val("#989898");
		$("#tableHeadFontSize").val("14");
		$("#tableHeadBGColor").val("#6c7686");
		$("#tableHeadBGColorSelector").val("#6C7686");
		$("#tableHeadFontColor").val("#FFFFFF");
		$("#tableHeadFontColorSelector").val("#FFFFFF");
		$("#tableBodyFontSize").val("14");
		$("#tableBodyBGColor").val("#EAE6E6");
		$("#tableBodyBGColorSelector").val("#EAE6E6");
		$("#tableBodyFontColor").val("#333");
		$("#tableBodyFontColorSelector").val("#333");
	}
	
	function showTemplatePreview() {
		/* var text = "";
		$("#configItemsList .previewGrid").each(function() {
			var section = $(this).data("section");
			var heading = $(this).find("span").html();
			text += "<div>";
			if(section === "image") {
				var style = "float:center;width:50%;";
				text += "<img src='/resources/images/logo-sourceld.png' style= " + style + " />";
			} else if (section === "table" || section === "footer") {
				text += heading + " : type(" + section + ")";
			} else {
				text += heading + " : type(" + section + ")";
			}
			text += "</div>";
		});
		$("#previewItems").append(text); */
		$("#templatePreview").show();
		$("body").css("overflow","hidden");
	}
	
	function closePreviewPopup() {
		$("#templatePreview").hide();
		$("body").css("overflow","auto");
	}
	function saveTemplate() {
		
	}
</script>
<!-- <script type="text/javascript" src="/resources/js/custom-invoice/drag.js"></script> -->
<script type="text/javascript">

 	$(function() {
		$("#configItemsList").sortable({
			cancel: ".sortable-hidden" 
		});
		/* $("#invoiceDetails div").sortable(); */
		/* $("#invoiceDetails div").draggable({
			containment : "#invoiceDetails"
		}); */ 
		/*
		$(".customInvoiceGrid").draggable({
			iframeFix : true,
			refreshPositions : true,
			addClasses : false,
			containment : "#configItemsList",
			obstacle : "#invoiceLogo",
			preventCollision : true
		});
			*/
		$("#configItemsList").on("sortactivate", function(event , ui) {
			$(".sortable-hidden").addClass("sortable-hidden-show");
		});
					
		$("#configItemsList").on("sortdeactivate", function(event, ui) {
			$(".sortable-hidden").removeClass("sortable-hidden-show");
			// alert("sortdeactivate");
			var index = 1;
			$("#configItemsList li").each(function() {
				$(this).find("input").data("order", index++);
			});
		});
		$("#configItemsList").disableSelection();
	});
</script>