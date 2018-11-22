<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
											<input class="form-control" type="text" id="tableHeadFontSize" onchange="applyTableSettings(false)"> 
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
											<input class="form-control" type="text" id="tableBodyFontSize" onchange="applyTableSettings(false)">
											<span class="input-group-addon">px</span>
										</div>
									</div>
								</div>
								<div class="form-group row mt">
									<label class="col-md-5">Background Color</label>
									<div class="col-md-7">
										<div class="input-group">
											<input id="tableBodyBGColor" class="form-control" name="itemrowFontclor" placeholder="#f9f9f9" type="text">
											<span class="input-group-addon" style="padding: 2px 10px;">
												<input id="tableBodyBGColorSelector" value="#f9f9f9"
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
						<a class="slBtn md colorBtn configureBtn disabled" id="applyTableSettingsBtn" href="javascript:void(0)" onclick="applyTableSettings(true)">Save</a>
						<a class="slBtn md configureBtn disabled" id="cancelTableSettingsBtn" href="javascript:void(0)" onclick="cancelTableSettings()">Cancel</a>
					</div>
			</div>
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
					<input class="slBtn md" value="Preview" id="update_user" onclick="showTemplatePreview();" type="button">
					<input class="slBtn md colorBtn" value="${invoiceTemplate != null ? 'Update' : 'Save'}" onclick="saveTemplate();" type="button">
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
		
		if(activeElement === null) {
			flag = true;
		} else if(activeElement !== null && (activeElement === ele)) {
			flag = false;
		} else {
			if(confirm("cancel previous edit and continue?")) {
				flag = true;
				cancelTextSettings();
			}
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
			$("#textSettingsTabHead a").addClass("Act");
			$("#imageSettingsTabHead a").removeClass("Act");
			$("#tableSettingsTabHead a").removeClass("Act");
			$(".cusInList label").removeClass("activeElement");
			activeElement = ele;
			$(ele).addClass("activeElement");
			
			if(ele !== null) {
				$(".configureBtn").addClass("disabled");
				$("#applyTextSettingsBtn").removeClass("disabled");
				$("#cancelTextSettingsBtn").removeClass("disabled");
			}
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
		
		$(".configureBtn").addClass("disabled");
	}
	
	function refreshElementTextStyles(ele) {
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

			//console.log("------------apply values----------")
			//console.log(fontFamily + ";" + fontStyle + ";" + fontSize + ";" + textAlign + ";" + textColor + ";" + textBGColorCB + ";" + bgColor + ";" + marginTop + ";" + marginRight + ";" + marginBottom + ";" + marginLeft + ";" + paddingTop + ";" + paddingRight + ";" + paddingBottom + ";" + paddingLeft + ";");
		} else {
			$(ele).removeAttr("style");
		}
	}
	
	function cancelTextSettings() {
		var ele = activeElement;
		activeElement = null;
		refreshTextSettings();
		if($(ele).data("id") && $(ele).data("id") === "invoiceLogo") {
			cancelImageSettings();
		} else if($(ele).data("id") && $(ele).data("id") === "invoiceTable") {
			cancelTableSettings();
		} else {
			refreshElementTextStyles(ele);
		}
	}
	
	function showImageSettingsTab(ele) {
		var flag = false;
		if(ele === null && activeElement !== null) {
			activeElement = $("#logoImagePreview");
			flag = true;
		} else {
			flag = false;
		}
		
		if(!flag) {
			if(activeElement === null) {
				activeElement = $("#logoImagePreview");
				flag = true;
			} else {
				if(activeElement === ele) {
					flag = false;
				} else if($(activeElement).data("id") !== undefined) {
					flag = $(activeElement).data("id") === $(ele).data("id");
				}
			}
		}
		if(!flag){
			if(confirm("cancel previous edit and continue?")) {
				flag = true;
				cancelImageSettings();
			}
		}
		
		if(flag) {
			//hiding other tabs and showing image-settings tab
			$("#textSettingsTab").hide();
			$("#imageSettingsTab").fadeIn();
			$("#tableSettingsTab").hide();
			$("#textSettingsTabHead a").removeClass("Act");
			$("#imageSettingsTabHead a").addClass("Act");
			$("#tableSettingsTabHead a").removeClass("Act");
			
			$(".configureBtn").addClass("disabled");
			$("#applyImageSettingsBtn").removeClass("disabled");
			$("#cancelImageSettingsBtn").removeClass("disabled");

			var top = $("#invoiceLogoGrid").offset().top;
			window.scrollTo(0, (top-70));
			if(ele !== null)
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
			//original values
			var name = $("#logoImage").val();
			var src = $("#logoImagePreview").attr("src");
			var imagePosition = $("#imagePosition").val();
			var imageSize = $("#imageSize").val();
			
			//converted to data attribute values
			var dataName = name;
			var dataSrc = src;
			var dataImagePosition = "float:" + imagePosition;
			var dataImageSize = "width:" + imageSize;
			
			$("#logoImagePreview").attr("src", src);
			$("#logoImagePreview").attr("style", "float:" + imagePosition + ";width : " + imageSize);
			
			if(saveFlag) {
				//saving actual values as "data" attributes to active element
				$(activeElement).data("name", name);
				$(activeElement).data("src", src);
				$(activeElement).data("image_position", imagePosition);
				$(activeElement).data("image_size", imageSize);
				
				//saving full-named values as "data-prev" attributes to active element
				$(activeElement).data("prev_name", dataName);
				$(activeElement).data("prev_src", dataSrc);
				$(activeElement).data("prev_image_position", dataImagePosition);
				$(activeElement).data("prev_image_size", dataImageSize);
				
				$(activeElement).data("style_saved", true);
				
				activeElement = null;
				/* refreshImageSettings(); */
			}
		} else 
			alert("Please select target image");
	}
	
	function cancelImageSettings() {
		var ele = activeElement;
		activeElement = null;
		refreshImageSettings();
		
		if($(ele).data("id") && $(ele).data("id") === "invoiceLogo") {
			refreshElementImageStyles(ele);
		} else if($(ele).data("id") && $(ele).data("id") === "invoiceTable") {
			cancelTableSettings();
		} else {
			cancelTextSettings();
		}
	}
	
	function refreshElementImageStyles(ele) {
		if($(ele).data("style_saved")) {
			var name = $(activeElement).data("prev_name");
			var src = $(activeElement).data("prev_src");
			var imagePosition = $(activeElement).data("prev_image_position");
			var imageSize = $(activeElement).data("prev_image_size");
			
			$("#logoImagePreview").attr("src", src);
			$(ele).attr("style", imagePosition + ";" + imageSize + ";");
		} else {
			$(ele).removeAttr("style");
		}
	}
	
	function refreshImageSettings() {
		$("#logoImagePreview").attr("src", "/resources/images/logo-sourceld.png");
		$("#logoImage").val("");
		$("#imagePosition").val("center");
		$("#imageSize").val("50%");
	}
	
	function showTableSettingsTab(ele) {
		var flag = false;
		if(ele === null && activeElement !== null) {
			activeElement = $("#invoiceTableGrid");
			flag = true;
		} else {
			flag = false;
		}
		
		if(!flag) {
			if(activeElement === null) {
				activeElement = $("#invoiceTableGrid");
				flag = true;
			} else {
				if(activeElement === ele) {
					flag = false;
				} else if($(activeElement).data("id") !== undefined) {
					flag = $(activeElement).data("id") === $(ele).data("id");
				}
			}
		}
		if(!flag){
			if(confirm("cancel previous edit and continue?")) {
				flag = true;
				cancelTableSettings();
			}
		}
		
		if(flag) {
			$("#textSettingsTab").hide();
			$("#imageSettingsTab").hide();
			$("#tableSettingsTab").fadeIn();
			$("#textSettingsTabHead a").removeClass("Act");
			$("#imageSettingsTabHead a").removeClass("Act");
			$("#tableSettingsTabHead a").addClass("Act");
			
			$(".configureBtn").addClass("disabled");
			$("#applyTableSettingsBtn").removeClass("disabled");
			$("#cancelTableSettingsBtn").removeClass("disabled");
			
			var top = $("#invoiceTableGrid").offset().top;
			window.scrollTo(0, (top-70));
			if(ele !== null)
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
				
				activeElement = null;
				/* refreshTableSettings(); */
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
		$("#tableBodyBGColor").val("#f9f9f9");
		$("#tableBodyBGColorSelector").val("#f9f9f9");
		$("#tableBodyFontColor").val("#333");
		$("#tableBodyFontColorSelector").val("#333");
	}
	
	function showTemplatePreview() {
		$("#previewItems").empty();
		var text = "";
		$("#configItemsList li").each(function () {
			var index = 1;
			if($(this).hasClass("sortable-hidden")) {
				text += "<div class='previewInnerCta' data-type='hidden' data-section='item'></div>";
				index++;
			} else {
				var style = "";
				if($(this).data("section") === "image") {
					style = $(this).find("img").attr("style");
					text += "<div class='previewInnerCta' data-name='" + $(this).data("name") + "' data-type='image' data-section='item'>";
					var itemChecked = $(this).find("input[type='checkbox']").is(":checked");
					text += "	<span " + (itemChecked ? "class='active'" : "") + ">";
					text += "		<img src='" + $(this).find("img").attr("src") + "' style='" + style + "'>";
					text += "	</span>";
					text += "</div>";
				} else if($(this).data("section") === "table") {
					text += "<div class='previewInnerCta previewInnerCtaFull' data-name='" + $(this).data("name") + "' data-type='table' data-section='item'>";
					text += "		<div class='project_tabs_inn_table borBot'>";
					text += "			<table>";
					text += "				<thead>";
					text += "					<tr>";
					var thIndex = 1;
					$(this).find("table thead th").each(function() {
						style = $(this).attr("style");
						text += "						<th style='" + style + "'>" + $(this).html() + "</th>";
						if(thIndex === 1) {
							/* style += "display:none;"; */
							text += "					<span class='hide' style='" + style + "'>th</span>";
						}
						thIndex++;
					});
					text += "					</tr>";
					text += "				</thead>";
					text += "				<tbody>";
					text += "					<tr>";
					var trIndex = 1;
					$(this).find("table tbody td").each(function() {
						style = $(this).attr("style");
						text += "						<td style='" + style + "'>" + $(this).html() + "</td>";
						if(trIndex === 1) {
							/* style += "display:none;"; */
							text += "					<span class='hide' style='" + style + "'>td</span>";
						}
						trIndex++;
					});
					text += "					</tr>";
					text += "				</tbody>";
					text += "			</table>";
					text += "		</div>";
					text += "</div>";
				} else if($(this).data("section") === "footer") {
					text += "<div class='previewInnerCta previewInnerCtaFull' data-name='" + $(this).data("name") + "' data-type='footer' data-section='item'>";
					var itemChecked = $(this).find("input[type='checkbox']").is(":checked");
					text += "	<span " + (itemChecked ? "class='active'" : "") + " style='" + $(this).find("label").attr("style") + "'>" + $(this).find("label").html() + "</span>";
					text += "</div>";
				} else {
					if($(this).hasClass("eleRight")) {
						style = "text-align: right";
					} else {
						style = "text-align: left";
					}
					
					var headText = "";
					var bodyText = "";
					headText += "<div class='previewInnerCta' style='" + style + "' data-name='" + $(this).data("name") + "' data-type='text' data-section='item'>";
					/* headText += "	<h3>" + $(this).find("span").html() + "</h3>"; */
					$(this).find("div[class='cusInList']").each(function() {
						var itemChecked = $(this).find("input[type='checkbox']").is(":checked");
						bodyText += "<span " + (itemChecked ? "class='active'" : "") + " style='" + $(this).find("label").attr("style") + "'>" + $(this).find("label").html() + "</span>";
					});
					headText += bodyText;
					headText += "</div>";
					if(bodyText !== "") {
						text += headText;
					} else {
						text += "<div class='previewInnerCta'></div>";
					}
				}
				index++;
			}
		});
		$("#previewItems").append(text);
		$("#templatePreview").show();
		$("body").css("overflow","hidden");
	}
	
	function closePreviewPopup() {
		$("#templatePreview").hide();
		$("body").css("overflow","auto");
	}
	function saveTemplate() {
		var orgLocationId = "${orgLocationId}";
		var orgRelationId = "${orgRelationId}";
		var index = 1;
		var items = [];
		$("#previewItems div[data-section='item']").each(function() {	
			var subItems = [];
			$(this).find("span").each(function() {
				subItems.push({
					checked : $(this).hasClass("active"),
					value : $(this).parent().data("type") === "image" 
									? $(this).find("img").attr("src")
									: ($(this).parent().data("type") !== "table" ? $(this).html() : ""),
					style : $(this).parent().data("type") === "image" 
									? ($(this).find("img").attr("style") !== "undefined" ? $(this).find("img").attr("style") : "")
									: ($(this).attr("style") !== "undefined" ? $(this).attr("style") : "")
				});
			});
			
			items.push({
				name : $(this).data("name"),
				type : $(this).data("type"),
				order : index,
				subItems : subItems
			});
			index++;
		});
		
		console.log(JSON.stringify(items));
		
 		var id = $("#invoiceTemplateId").val();
		var data = {"id":id, "jsonData":JSON.stringify(items)};
		$.ajax({
	    	type: "POST",
	    	data: data,
	        url: sourceleadUrl+"/saveCustomInvoiceTemplate?orgLocationId=" + orgLocationId + "&orgRelationId=" + orgRelationId,
	        success: function(response) {
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "INVALID_DATA" || response.response === "INVALID_USER") {
					showError(response.responseMessage);
	        		closeConfigurePopup();
	        	} else if (response.response === "SUCCESS") {
	        		$(".success_pop").show();
	        		location.reload();
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        }
		});
	}
</script>
<script type="text/javascript">
 	$(function() {
		$("#configItemsList").sortable({
			cancel: ".sortable-hidden" 
		});
		$("#configItemsList").on("sortactivate", function(event , ui) {
			$(".sortable-hidden").addClass("sortable-hidden-show");
		});
		$("#configItemsList").on("sortdeactivate", function(event, ui) {
			$(".sortable-hidden").removeClass("sortable-hidden-show");
			// alert("sortdeactivate");
			var index = 1;
			var testIndex = 1;
			var firstHiddenItemFlag = false;
			var secondHiddenItemFlag = false;
			var thirdHiddenItemFlag = false;
			$("#configItemsList li").each(function() {
				$(this).find("input").data("order", index);
				index++;
				if($(this).data("section") === "text" || $(this).data("section") === "hidden" || $(this).data("section") === "image") {
					if($(this).data("section") === "hidden" && testIndex === 1) {
						firstHiddenItemFlag = true;
						testIndex++;
						return;
					}
					if($(this).data("section") === "hidden" && testIndex === 2) {
						secondHiddenItemFlag = true;
						testIndex++;
						return;
					}
					if($(this).data("section") === "hidden" && testIndex === 3) {
						thirdHiddenItemFlag = true;
						testIndex++;
						return;
					}
					/* var rightFlag = ((firstHiddenItemFlag && secondHiddenItemFlag) || (firstHiddenItemFlag && !secondHiddenItemFlag) || (!firstHiddenItemFlag && secondHiddenItemFlag))
									? testIndex % 2 > 0 
									: testIndex % 2 === 0; */
					var rightFlag = ((firstHiddenItemFlag && secondHiddenItemFlag && thirdHiddenItemFlag) ||
									(firstHiddenItemFlag && !secondHiddenItemFlag && thirdHiddenItemFlag) ||
									(firstHiddenItemFlag && secondHiddenItemFlag && !thirdHiddenItemFlag) ||
									(firstHiddenItemFlag && !secondHiddenItemFlag && !thirdHiddenItemFlag) ||
									(!firstHiddenItemFlag && secondHiddenItemFlag && thirdHiddenItemFlag) ||
									(!firstHiddenItemFlag && secondHiddenItemFlag && !thirdHiddenItemFlag) ||
									(!firstHiddenItemFlag && !secondHiddenItemFlag && thirdHiddenItemFlag) ||
									(!firstHiddenItemFlag && !secondHiddenItemFlag && !thirdHiddenItemFlag))
									? testIndex % 2 === 0 
									:testIndex % 2 > 0;
					$(this).find("div[class='cusInList']").css("text-align", rightFlag ? "right" : "left");
					/* $(this).find("div[class='cusInList']").html(testIndex + " " + firstHiddenItemFlag + " " + secondHiddenItemFlag + " " + thirdHiddenItemFlag); */
					if(rightFlag)
						$(this).addClass("eleRight");
					else
						$(this).removeClass("eleRight");
					testIndex++;
				}
			});
		});
		$("#configItemsList").disableSelection();
	});
</script>