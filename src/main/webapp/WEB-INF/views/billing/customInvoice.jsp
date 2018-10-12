<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<section class="customInvoiceTemplate" style="left: 38px !important; top: 24px; z-index: 999;">

	<span class="close">
		<a href="javascript:void(0)">&times;</a>
	</span>

	<div class="clearfix col-md-12">
		<ul class="project-tabs-menu projecttabs-custom clearfix">
			<li class="current" id="textSettingsTabHead">
				<a href="javascript:void(0)" onclick="showTextSettingsTab(null)"><i class="fa fa-file-text-o paddingRight"></i>Text Settings</a>
			</li>
			<li id="imageSettingsTabHead">
				<a href="javascript:void(0)" onclick="showImageSettingsTab(null)"><i class="fa fa-file-image-o paddingRight"></i>Image Settings</a>
			</li>
			<li id="tableSettingsTabHead">
				<a href="javascript:void(0)" onclick="showTableSettingsTab(null)"><i class="fa fa-table paddingRight"></i>Table Settings</a>
			</li>
		</ul>
	</div>
	<div class="clearfix"></div>
	<div class="col-md-4">
		<div class="clearfix project_tabs">
		
			<div id="textSettingsTab" class="project_tabs_inn" style="display: block;">
				<div class="project_tabs_inn_table" style="height: 575px;">
					<div class="modal-footer"
						style="background-color: #f8f7f7; position: absolute; bottom: 0; width: 100%; z-index: 99;">
						<div class="pull-right">
							<a class="btn btn-sm btn-default" href="javascript:void(0)" onclick="cancelTextSettings()">Cancel</a>
							<a class="btn btn-sm btn-primary ml" href="javascript:void(0)" onclick="applyTextSettings(true)">Save</a>
						</div>
					</div>
					<div class="clearfix customtemlate-inner">
						<div class="form-group row">
							<div class="col-md-6 mb">
								<label class="clearfix text-muted">Font Family</label>
								<div class="clearfix">
									<select id="textFontFamily" class="form-control" onchange="applyTextSettings(false)" data-type="font_family" data-prefix="font-family" data-suffix="">
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
									<select id="textFontStyle" class="form-control" onchange="applyTextSettings(false)" data-type="font_style" data-prefix="" data-suffix="">
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
									<input id="textFontSize" type="text" class="form-control" name="favcolor" placeholder="12"  onchange="applyTextSettings(false)" data-type="font_size" data-prefix="font-size" data-suffix="px">
									<span class="input-group-addon">px</span>
								</div>
							</div>
							<div class="col-md-6 mb">
								<label class="clearfix text-muted">Text Align</label>
								<div class="clearfix">
									<select id="textFontAlign" class="form-control" onchange="applyTextSettings(false)" data-type="text_align" data-prefix="text-align" data-suffix="">
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
									<input id="textTextColor" type="text" class="form-control" name="favcolor" placeholder="#545454" onchange="applyTextSettings(false)" data-type="color"  data-prefix="color" data-suffix="">
									<span class="input-group-addon" style="padding: 2px 10px;">
										<input type="color" id="textTextColorSelector" value="#545454" 
										style="width: 20px; height: 20px;" onChange="colorPicker('textTextColorSelector', 'textTextColor');">
									</span>
								</div>
							</div>
							<div class="col-md-6 mb">
								<label class="clearfix text-muted">Background Color</label>
								<div class="clearfix"></div>
								<div class="input-group">
									<input id="textBGColor" type="text" class="form-control" name="favcolor" placeholder="#545454" onchange="applyTextSettings(false)" data-type="background_color" data-prefix="background-color" data-suffix="">
									<span class="input-group-addon" style="padding: 2px 10px;">
										<input type="color" id="textBGColorSelector" value="#545454"
										style="width: 20px; height: 20px;" onChange="colorPicker('textBGColorSelector', 'textBGColor');">
									</span>
								</div>
							</div>
							
						</div>
						<div class="row form-group clearfix mb">
							<label class="col-md-12 mb10 text-muted">Margin (in pixels)</label>
							<div class="col-md-3 paddingRight text-center">
								<input type="text" id="textMarginTop" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="margin_top" data-prefix="margin-top" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Top</span>
							</div>
							<div class="col-md-3 paddingLeft paddingRight text-center">
								<input type="text" id="textMarginRight" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="margin_right" data-prefix="margin-right" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Right</span>
							</div>
							<div class="col-md-3 paddingLeft paddingRight text-center">
								<input type="text" id="textMarginBottom" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="margin_bottom" data-prefix="margin-bottom" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Bottom</span>
							</div>
							<div class="col-md-3 paddingLeft text-center">
								<input type="text" id="textMarginLeft" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="margin_left" data-prefix="margin-left" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Left</span>
							</div>
						</div>
						<div class="row form-group clearfix mb">
							<label class="col-md-12 mb10 text-muted">Padding (in pixels)</label>
							<div class="col-md-3 paddingRight text-center">
								<input type="text" id="textPaddingTop" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_top" data-prefix="padding-top" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Top</span>
							</div>
							<div class="col-md-3 paddingLeft paddingRight text-center">
								<input type="text" id="textPaddingRight" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_right" data-prefix="padding-right" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Right</span>
							</div>
							<div class="col-md-3 paddingLeft paddingRight text-center">
								<input type="text" id="textPaddingBottom" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_bottom" data-prefix="padding-bottom" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Bottom</span>
							</div>
							<div class="col-md-3 paddingLeft text-center">
								<input type="text" id="textPaddingLeft" placeholder="0px" class="form-control input-sm" onchange="applyTextSettings(false)" data-type="padding_left" data-prefix="padding-left" data-suffix="px">
								<span class="text-muted" style="font-size: 10px;">Left</span>
							</div>
						</div>
						
					</div>
				</div>
			</div>
			
			
			<div id="imageSettingsTab" class="project_tabs_inn">
				<div class="project_tabs_inn_table" style="height: 575px;">
					<div class="modal-footer"
						style="background-color: #f8f7f7; position: absolute; bottom: 0; width: 100%; z-index: 99;">
						<div class="pull-right">
							<a class="btn btn-sm btn-default" href="javascript:void(0)" onclick="cancelImageSettings()">Cancel</a>
							<a class="btn btn-sm btn-primary ml" href="javascript:void(0)" onclick="applyImageSettings(true)">Save</a>
						</div>
					</div>
					<div class="clearfix customtemlate-inner">
						<div class="form-group row mt">
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
				</div>
			</div>

			<div id="tableSettingsTab" class="project_tabs_inn">

				<div class="project_tabs_inn_table" style="height: 575px;">
					<div class="modal-footer"
						style="background-color: #f8f7f7; position: absolute; bottom: 0; width: 100%; z-index: 99;">
						<div class="pull-right">
							<a class="btn btn-sm btn-default" href="javascript:void(0)" onclick="cancelTableSettings()">Cancel</a>
							<a class="btn btn-sm btn-primary ml" href="javascript:void(0)" onclick="applyTableSettings(true)">Save</a>
						</div>
					</div>
					<div class="clearfix customtemlate-inner">
												
						<div id="innerTab2" class="templatetab_inn">
							<div class="project_tabs_inn_table">

								<div class="form-group mt row">
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
								<div class="form-group mt row">
									<label class="col-md-5">Font Size </label>
									<div class="col-md-7">
										<div class="input-group">
											<input class="form-control" type="text" id="tableHeadFontSize" onclick="applyTableSettings(false)"> 
											<span class="input-group-addon">px</span>
										</div>
									</div>
								</div>
								<div class="form-group mt row">
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
								<div class="form-group row mt">
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
								<div class="clearfix mt">
									<h5>Table Row</h5>
								</div>
								<div class="form-group row mt">
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

								<div class="form-group row mt">
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
				</div>

			</div>
		</div>
	</div>
	<div class="col-md-8">
		<div class="letterhead-preview overflow_Auto" style="height: 580px; min-height: auto;">
			<div>
				<ul class="list-unstyled customInvoiceItems clearfix" id="configItemsList">

					<!-- Logo Part -->
					<li id="invoiceLogo" class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid">
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
					<li class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid">
						<span class="">Company Heading</span>
						<div class="cusInList">
							<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
							<label onclick="showTextSettingsTab(this)">Company Heading</label>
						</div>
						<span class="movIcon"></span>
					</li>
					<li class="positionRelative ui-state-default customInvoiceGrid ui-sortable-handle sortable-hidden"></li>
					<!-- Company Heading -->
					
					<li class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid">
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
					<li class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid">
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
					<li class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid">
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
					<li class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid">
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
					<li class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid" style="width: 98%;">
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
					<li class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid">
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
					<li class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid">
						<span class="">Total</span>
						<div class="cusInList">
							<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
							<label onclick="showTextSettingsTab(this)">Grand Total :INR 15680.00</label>
						</div>
						<span class="movIcon"></span>
					</li>
					<!-- Total -->
					
					<!-- Footer -->
					<li class="positionRelative ui-state-default ui-sortable-handle customInvoiceGrid" style="width: 98%;">
						<span class="">Footer</span>
						<div>
							<input class="checkbox-custom" id="cb10" checked="checked" type="checkbox">
							<label onclick="showTextSettingsTab(this)">Address</label>
						</div>
						<span class="movIcon"></span>
					</li>
					<!-- Footer -->
				</ul>
			</div>
		</div>
	</div>
</section>

<script type="text/javascript">

/* 	jQuery(function($) {
		var $fbEditor = document.getElementById('fb-editor');
		var $formContainer = $(document.getElementById('fb-rendered-form'));
		fbOptions = {
				onSave : function() {
					$fbEditor.toggle();
					$formContainer.toggle();
					$('form', $formContainer).formRender({
						formData : formBuilder.formData
					});
				}
			};
		
		var formBuilder = $($fbEditor).formBuilder(fbOptions);
		
		document.addEventListener('fieldAdded', function() {
			alert(formBuilder.formData);
		});
	}); */

/* 	jQuery(function($) {
		var $fbEditor = $(document.getElementById('fb-editor')), $formContainer = $(document
				.getElementById('fb-rendered-form')), fbOptions = {
			onSave : function() {
				$fbEditor.toggle();
				$formContainer.toggle();
				$('form', $formContainer).formRender({
					formData : formBuilder.formData
				});
			}
		}, formBuilder = $fbEditor.formBuilder(fbOptions);

		$('.edit-form', $formContainer).click(function() {
			$fbEditor.toggle();
			$formContainer.toggle();
		});
	}); */

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
				refreshTextSettings();
			}
		} else {
			flag = true;
		}
		if(flag) {
			//getting pre-filled values of activeElement and auto-fill to edit elements
			$("#textFontFamily").val($(ele).data("font_family") === undefined ? "arial" : $(ele).data("font_family"));
			$("#textFontStyle").val($(ele).data("font_style") === undefined ? "font-weight:400;" : $(ele).data("font_style"));
			$("#textFontSize").val($(ele).data("font_size"));
			$("#textFontAlign").val($(ele).data("text_align") === undefined ? "left" : $(ele).data("text_align"));
			$("#textTextColor").val($(ele).data("color") === undefined ? "#000000" : $(ele).data("color"));
			$("#textTextColorSelector").val($(ele).data("color") === undefined ? "#000000" : $(ele).data("color"));
			$("#textBGColor").val($(ele).data("background_color") === undefined ? "#000000" : $(ele).data("background_color"));
			$("#textBGColorSelector").val($(ele).data("background_color") === undefined ? "#000000" : $(ele).data("background_color"));
			$("#textMarginTop").val($(ele).data("margin_top"));
			$("#textMarginRight").val($(ele).data("margin_right"));
			$("#textMarginBottom").val($(ele).data("margin_bottom"));
			$("#textMarginLeft").val($(ele).data("margin_left"));
			$("#textPaddingTop").val($(ele).data("padding_top"));
			$("#textPaddingRight").val($(ele).data("padding_right"));
			$("#textPaddingBottom").val($(ele).data("padding_bottom"));
			$("#textPaddingLeft").val($(ele).data("padding_left"));
			
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
			var fontFamily = ($("#textFontFamily").data("prefix") !== "" ? $("#textFontFamily").data("prefix") + ":" : "") + $("#textFontFamily").val() + "" + $("#textFontFamily").data("suffix");
			var fontStyle = ($("#textFontStyle").data("prefix") !== "" ? $("#textFontStyle").data("prefix") + ":" : "") + $("#textFontStyle").val() + "" + $("#textFontFamily").data("suffix");
			var fontSize = ($("#textFontSize").data("prefix") !== "" ? $("#textFontSize").data("prefix") + ":" : "") + ($("#textFontSize").val() === "" ? "14" : $("#textFontSize").val()) + "" + $("#textFontSize").data("suffix");
			var textAlign = ($("#textFontAlign").data("prefix") !== "" ? $("#textFontAlign").data("prefix") + ":" : "") + $("#textFontAlign").val() + "" + $("#textFontAlign").data("suffix");
			var textColor = ($("#textTextColorSelector").data("prefix") !== "" ? $("#textTextColorSelector").data("prefix") + ":" : "") + $("#textTextColorSelector").val() + "" + $("#textTextColorSelector").data("suffix");
			var bgColor = ($("#textBGColorSelector").data("prefix") !== "" ? $("#textBGColorSelector").data("prefix") + ":" : "") + $("#textBGColorSelector").val() + "" + $("#textBGColorSelector").data("suffix");
			var marginTop = ($("#textMarginTop").data("prefix") !== "" ? $("#textMarginTop").data("prefix") + ":" : "") + ($("#textMarginTop").val() === "" ? "0" : $("#textMarginTop").val()) + "" + $("#textMarginTop").data("suffix");
			var marginRight = ($("#textMarginRight").data("prefix") !== "" ? $("#textMarginRight").data("prefix") + ":" : "") + ($("#textMarginRight").val() === "" ? "0" : $("#textMarginRight").val()) + "" + $("#textMarginRight").data("suffix");
			var marginBottom = ($("#textMarginBottom").data("prefix") !== "" ? $("#textMarginBottom").data("prefix") + ":" : "") + ($("#textMarginBottom").val() === "" ? "0" : $("#textMarginBottom").val()) + "" + $("#textMarginBottom").data("suffix");
			var marginLeft = ($("#textMarginLeft").data("prefix") !== "" ? $("#textMarginLeft").data("prefix") + ":" : "") + ($("#textMarginLeft").val() === "" ? "0" : $("#textMarginLeft").val()) + "" + $("#textMarginLeft").data("suffix");
	
			var paddingTop = ($("#textPaddingTop").data("prefix") !== "" ? $("#textPaddingTop").data("prefix") + ":" : "") + ($("#textPaddingTop").val() === "" ? "0" : $("#textPaddingTop").val()) + "" + $("#textPaddingTop").data("suffix");
			var paddingRight = ($("#textPaddingLeft").data("prefix") !== "" ? $("#textPaddingLeft").data("prefix") + ":" : "") + ($("#textPaddingLeft").val() === "" ? "0" : $("#textPaddingLeft").val()) + "" + $("#textPaddingLeft").data("suffix");
			var paddingBottom = ($("#textPaddingBottom").data("prefix") !== "" ? $("#textPaddingBottom").data("prefix") + ":" : "") + ($("#textPaddingBottom").val() === "" ? "0" : $("#textPaddingBottom").val()) + "" + $("#textPaddingBottom").data("suffix");
			var paddingLeft = ($("#textPaddingLeft").data("prefix") !== "" ? $("#textPaddingLeft").data("prefix") + ":" : "") + ($("#textPaddingLeft").val() === "" ? "0" : $("#textPaddingLeft").val()) + "" + $("#textPaddingLeft").data("suffix");
	
			$(activeElement).attr("style", fontFamily + ";" + fontStyle + ";" + fontSize + ";" + textAlign + ";" + textColor + ";" + bgColor + ";" + marginTop + ";" + marginRight + ";" + marginBottom + ";" + marginLeft + ";" + paddingTop + ";" + paddingRight + ";" + paddingBottom + ";" + paddingLeft + ";");
		
			if(saveFlag) {
				$(activeElement).data("font_family", fontFamily);
				$(activeElement).data("font_size", fontSize);
				$(activeElement).data("font_style", fontStyle);
				$(activeElement).data("text_align", textAlign);
				$(activeElement).data("color", textColor);
				$(activeElement).data("background_color", bgColor);
				$(activeElement).data("margin_top", marginTop);
				$(activeElement).data("margin_right", marginRight);
				$(activeElement).data("margin_bottom", marginBottom);
				$(activeElement).data("margin_left", marginLeft);
				$(activeElement).data("padding_top", paddingTop);
				$(activeElement).data("padding_right", paddingRight);
				$(activeElement).data("padding_bottom", paddingBottom);
				$(activeElement).data("padding_left", paddingLeft);
			}
		} else {
			alert("please select a text element");
		}
	}
	
/* 	function applyPreFilledValuesToTextEditElements(ele) {
		$("#textFontFamily").val($(ele).data("font_family") === undefined ? "arial" : $(ele).data("font_family"));
		$("#textFontStyle").val($(ele).data("font_style") === undefined ? "font-weight:400;" : $(ele).data("font_style"));
		$("#textFontSize").val($(ele).data("font_size"));
		$("#textFontAlign").val($(ele).data("text_align") === undefined ? "left" : $(ele).data("text_align"));
		$("#textTextColor").val($(ele).data("color") === undefined ? "#000000" : $(ele).data("color"));
		$("#textTextColorSelector").val($(ele).data("color") === undefined ? "#000000" : $(ele).data("color"));
		$("#textBGColor").val($(ele).data("background_color") === undefined ? "#000000" : $(ele).data("background_color"));
		$("#textBGColorSelector").val($(ele).data("background_color") === undefined ? "#000000" : $(ele).data("background_color"));
		$("#textMarginTop").val($(ele).data("margin_top"));
		$("#textMarginRight").val($(ele).data("margin_right"));
		$("#textMarginBottom").val($(ele).data("margin_bottom"));
		$("#textMarginLeft").val($(ele).data("margin_left"));
		$("#textPaddingTop").val($(ele).data("padding_top"));
		$("#textPaddingRight").val($(ele).data("padding_right"));
		$("#textPaddingBottom").val($(ele).data("padding_bottom"));
		$("#textPaddingLeft").val($(ele).data("padding_left"));
	} */
	
	function refreshTextSettings() {
		$("#textFontFamily").val("arial");
		$("#textFontStyle").val("font-weight:400;");
		$("#textFontSize").val("");
		$("#textFontAlign").val("left");
		$("#textTextColor").val("");
		$("#textTextColorSelector").val("");
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
	
	function cancelTextSettings() {
		activeElement = null;
		refreshTextSettings();
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
			var tableHeadFontSize = $("#tableHeadFontSize").val();
			var tableHeadBGColor = $("#tableHeadBGColor").val();
			var tableHeadBGColorSelector = $("#tableHeadBGColorSelector").val();
			var tableHeadFontColor = $("#tableHeadFontColor").val();
			var tableHeadFontColorSelector = $("#tableHeadFontColorSelector").val();
			var tableBodyFontSize = $("#tableBodyFontSize").val();
			var tableBodyBGColor = $("#tableBodyBGColor").val();
			var tableBodyBGColorSelector = $("#tableBodyBGColorSelector").val();
			var tableBodyFontColor = $("#tableBodyFontColor").val();
			var tableBodyFontColorSelector = $("#tableBodyFontColorSelector").val();
			
			if(tableBorderCB)
				$("#invoiceTable tr th, #invoiceTable tr td").attr("style", "border:1px solid " + tableBorderColorSelector);
			else {
				$("#invoiceTable tr th, #invoiceTable tr td").attr("style", "border:none");
			}
			var thStyle = "";
			thStyle += "font-size:" + tableHeadFontSize + "px;";
			thStyle += "background-color:" + tableHeadBGColorSelector + "!important" + ";";
			thStyle += "color:" + tableHeadFontColorSelector + ";";
			$("#invoiceTable tr th").attr("style", thStyle);
			var tdStyle = "";
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