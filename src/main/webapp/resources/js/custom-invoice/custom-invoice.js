$(document).ready(function() {
	$(".project-tabs-menu li a").click(function(event) {
		event.preventDefault();
		$(this).parent().addClass("current");
		$(this).parent().siblings().removeClass("current");
		var tab = $(this).attr("href");
		$(".project_tabs_inn").not(tab).css("display", "none");
		$(tab).fadeIn();
	});
	
	$(".invoicetabsmenu li a").click(function(event) {
		event.preventDefault();
		$(this).parent().addClass("current");
		$(this).parent().siblings().removeClass("current");
		var inner = $(this).attr("href");
		$(".templatetab_inn").not(inner).css("display", "none");
		$(inner).fadeIn();
	});

	$("#configItemsList").sortable({
		cancel: ".sortable-hidden, .disable-drag" 
	});
	$("#configItemsList").on("sortactivate", function(event , ui) {
		$(".sortable-hidden").addClass("sortable-hidden-show");
	});
	$("#configItemsList").on("sortdeactivate", function(event, ui) {
		$(".sortable-hidden").removeClass("sortable-hidden-show");
		var index = 1;
		var testIndex = 1;
		var position = 1;
		$("#configItemsList li").each(function() {
			$(this).find("input").data("order", index);
			index++;
			
			if($(this).data("section") === "text" || $(this).data("section") === "hidden" || $(this).data("section") === "image") {
				var rightFlag = position % 2 === 0;
				
				$(this).find("div[class='cusInList']").each(function() {
					$(this).attr("style", "text-align:" + (rightFlag ? "right" : "left"));
					
					var labelCSS = $(this).find("label").attr("style");
					if(labelCSS !== undefined) {
						var textAlign = labelCSS.substring(labelCSS.lastIndexOf("text-align"));
						var newCSS = labelCSS.replace(/text-align/g,"").replace(/:left;/g,"").replace(/: left;/g,"").replace(/:right;/g,"").replace(/: right;/g,"");
						if(textAlign.indexOf("left") !== -1) {
							newCSS += "text-align:left;";
						} else if(textAlign.indexOf("right") !== -1) {
							newCSS += "text-align:right;";
						}
						$(this).find("label").attr("style", newCSS);
//						console.log(labelCSS);
					}
				});
				
				/*$(this).find("div[class='cusInList']").html("position : " + position);*/
				if(rightFlag)
					$(this).addClass("eleRight");
				else
					$(this).removeClass("eleRight");
				testIndex++;
//				position++;
			}
			$(this).data("position", position);
			if(($(this).data("section") !== "table" && $(this).data("section") !== "footer") 
					|| ($(this).data("section") === "table" && (position % 2 === 0))
					|| ($(this).data("section") === "footer" && (position % 2 === 0))) {
				position++;
			}
		});
	});
	$("#configItemsList").disableSelection();
});

function colorPicker(source, target) {
	var favColor = document.getElementById(source).value;
	document.getElementById(target).value = favColor;
}

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
		var savedFontFamily = "";
		var savedFontStyle = "";
		var savedFontWeight = "";
		var savedFontSize = "";
		var savedTextAlign = "";
		var savedTextColor = "";
		var savedBgColor = "";
		var savedPaddingTop = "";
		var savedPaddingRight = "";
		var savedPaddingBottom = "";
		var savedPaddingLeft = "";
		
		var savedStyle = $(ele).data("style");
		if(savedStyle && savedStyle !== "") {
			for(var i in savedStyle.split(";")) {
				var style = savedStyle.split(";")[i];
				if(style.split(":").length > 1) {
					if(style.indexOf("font-family") !== -1) {
						savedFontFamily = style.split(":")[1];
					} else if(style.indexOf("font-style") !== -1) {
						savedFontStyle = style + ";";
					} else if(style.indexOf("font-weight") !== -1) {
						savedFontWeight = style + ";";
					} else if(style.indexOf("font-size") !== -1) {
						savedFontSize = style.split(":")[1];
					} else if(style.indexOf("text-align") !== -1) {
						savedTextAlign = style.split(":")[1];
					} else if(style.indexOf("background-color") !== -1) {
						savedBgColor = style.split(":")[1];
					} else if(style.indexOf("color") !== -1) {
						savedTextColor = style.split(":")[1];
					} else if(style.indexOf("padding-top") !== -1) {
						savedPaddingTop = style.split(":")[1].replace(/px/g,"");
					} else if(style.indexOf("padding-right") !== -1) {
						savedPaddingRight = style.split(":")[1].replace(/px/g,"");
					} else if(style.indexOf("padding-bottom") !== -1) {
						savedPaddingBottom = style.split(":")[1].replace(/px/g,"");
					} else if(style.indexOf("padding-left") !== -1) {
						savedPaddingLeft = style.split(":")[1].replace(/px/g,"");
					}
				}
//				console.log(savedStyle.split(";")[i]);
			}
		}
		
		//getting pre-filled values of activeElement
		var fontFamily = savedFontFamily === "" ? systemDefaultFont : savedFontFamily;
		var fontStyle = savedFontStyle === "" ? "" : savedFontStyle;
		var fontWeight = savedFontWeight === "" ? "font-weight:400;" : savedFontWeight;
		
		var fontSize = savedFontSize === "" ? "14px" : savedFontSize;
		
		var textAlign = savedTextAlign === "" ? "left" : savedTextAlign;
		var textColor = savedTextColor === "" ? "#545454" : savedTextColor;
		var bgColorCheck = savedBgColor === "" || savedBgColor === "transparent" ? false : true;
		var bgColor = savedBgColor === "" || savedBgColor === "transparent" ? "#545454" : savedBgColor;
		/* var marginTop = $(ele).data("margin_top");
		var marginRight = $(ele).data("margin_right");
		var marginBottom = $(ele).data("margin_bottom");
		var marginLeft = $(ele).data("margin_left"); */
		var paddingTop = savedPaddingTop === "" ? "0" : savedPaddingTop;
		var paddingRight = savedPaddingRight === "" ? "0" : savedPaddingRight;
		var paddingBottom = savedPaddingBottom === "" ? "0" : savedPaddingBottom;
		var paddingLeft = savedPaddingLeft === "" ? "0" : savedPaddingLeft;

		//auto-fill of pre-filled values to edit components
		$("#textFontFamily").val(fontFamily);
		$("#textFontStyle").val(fontStyle + fontWeight);
//		$("#textFontSize").val(fontSize);
		if(fontSize === "18px") {
			$("#textFontSize").val("extraLarge");
		} else if(fontSize === "16px") {
			$("#textFontSize").val("large");
		} else if(fontSize === "14px") {
			$("#textFontSize").val("medium");
		} else if(fontSize === "12px") {
			$("#textFontSize").val("small");
		}
		$("#textFontAlign").val(textAlign);
		$("#textTextColor").val(textColor);
		$("#textTextColorSelector").val(textColor);
		$("#textBGColorCB").prop("checked", bgColorCheck);
		$("#textBGColor").val(bgColor);
		$("#textBGColorSelector").val(bgColor);
/* 			$("#textMarginTop").val(marginTop);
		$("#textMarginRight").val(marginRight);
		$("#textMarginBottom").val(marginBottom);
		$("#textMarginLeft").val(marginLeft); */
		$("#textPaddingTop").val(paddingTop);
		$("#textPaddingRight").val(paddingRight);
		$("#textPaddingBottom").val(paddingBottom);
		$("#textPaddingLeft").val(paddingLeft);
		
		
		var dataFontFamily = ($("#textFontFamily").data("prefix") !== "" ? $("#textFontFamily").data("prefix") + ":" : "") + fontFamily + "" + $("#textFontFamily").data("suffix");
		var dataFontStyle = ($("#textFontStyle").data("prefix") !== "" ? $("#textFontStyle").data("prefix") + ":" : "") + fontStyle + "" + $("#textFontFamily").data("suffix");
		var dataFontSize = ($("#textFontSize").data("prefix") !== "" ? $("#textFontSize").data("prefix") + ":" : "") + fontSize + "" + $("#textFontSize").data("suffix");
		var dataTextAlign = ($("#textFontAlign").data("prefix") !== "" ? $("#textFontAlign").data("prefix") + ":" : "") + textAlign + "" + $("#textFontAlign").data("suffix");
		var dataTextColor = ($("#textTextColorSelector").data("prefix") !== "" ? $("#textTextColorSelector").data("prefix") + ":" : "") + textColor + "" + $("#textTextColorSelector").data("suffix");
		var dataTextBGColorCB = bgColorCheck;
		var dataBgColor = "";
		if(bgColorCheck)
			dataBgColor = ($("#textBGColorSelector").data("prefix") !== "" ? $("#textBGColorSelector").data("prefix") + ":" : "") + bgColor + "" + $("#textBGColorSelector").data("suffix");
		else
			dataBgColor = "background-color:transparent";
		/* var dataMarginTop = ($("#textMarginTop").data("prefix") !== "" ? $("#textMarginTop").data("prefix") + ":" : "") + marginTop + "" + $("#textMarginTop").data("suffix");
		var dataMarginRight = ($("#textMarginRight").data("prefix") !== "" ? $("#textMarginRight").data("prefix") + ":" : "") + marginRight + "" + $("#textMarginRight").data("suffix");
		var dataMarginBottom = ($("#textMarginBottom").data("prefix") !== "" ? $("#textMarginBottom").data("prefix") + ":" : "") + marginBottom + "" + $("#textMarginBottom").data("suffix");
		var dataMarginLeft = ($("#textMarginLeft").data("prefix") !== "" ? $("#textMarginLeft").data("prefix") + ":" : "") + marginLeft + "" + $("#textMarginLeft").data("suffix");
 		*/
		var dataPaddingTop = ($("#textPaddingTop").data("prefix") !== "" ? $("#textPaddingTop").data("prefix") + ":" : "") + paddingTop + "" + $("#textPaddingTop").data("suffix");
		var dataPaddingRight = ($("#textPaddingRight").data("prefix") !== "" ? $("#textPaddingRight").data("prefix") + ":" : "") + paddingRight + "" + $("#textPaddingRight").data("suffix");
		var dataPaddingBottom = ($("#textPaddingBottom").data("prefix") !== "" ? $("#textPaddingBottom").data("prefix") + ":" : "") + paddingBottom + "" + $("#textPaddingBottom").data("suffix");
		var dataPaddingLeft = ($("#textPaddingLeft").data("prefix") !== "" ? $("#textPaddingLeft").data("prefix") + ":" : "") + paddingLeft + "" + $("#textPaddingLeft").data("suffix");

		
		$(ele).data("prev_font_family", dataFontFamily);
		$(ele).data("prev_font_style", dataFontStyle);
		$(ele).data("prev_font_size", dataFontSize);
		$(ele).data("prev_text_align", dataTextAlign);
		$(ele).data("prev_color", dataTextColor);
		$(ele).data("prev_background_color_cb", dataTextBGColorCB);
		$(ele).data("prev_background_color", dataBgColor);
		/* $(ele).data("prev_margin_top", dataMarginTop);
		$(ele).data("prev_margin_right", dataMarginRight);
		$(ele).data("prev_margin_bottom", dataMarginBottom);
		$(ele).data("prev_margin_left", dataMarginLeft); */
		$(ele).data("prev_padding_top", dataPaddingTop);
		$(ele).data("prev_padding_right", dataPaddingRight);
		$(ele).data("prev_padding_bottom", dataPaddingBottom);
		$(ele).data("prev_padding_left", dataPaddingLeft);
		
		$(ele).data("style_saved", true);
		
//		$(ele).attr("style", "font-family:" + fontFamily + ";" + "font-style:" + fontStyle + ";" + "font-weight:" + fontWeight + ";" + "font-size:" + fontSize + ";" + "text-align:" + textAlign + ";" + "color:" + textColor + ";" + "background-color:" + bgColor);
		
		$(ele).attr("style", dataFontFamily + ";" + dataFontStyle + ";" + dataFontSize + ";" + dataTextAlign + ";" + dataTextColor + ";" + dataBgColor + ";" + dataPaddingTop + ";" + dataPaddingRight + ";" + dataPaddingBottom + ";" + dataPaddingLeft);
		
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
		if($('#paddingForm').validate(false, validationSettings)) {
			//original values
			var textBGColorCB = $("#textBGColorCB").is(":checked");
			
			var fontFamily = $("#textFontFamily").val();
			var fontStyle = $("#textFontStyle").val();
//			var fontSize = $("#textFontSize").val() === "" ? "14" : $("#textFontSize").val();
			var fontSize = "14px";
			if($("#textFontSize").val() === "small") {
				fontSize = "12px";
			} else if($("#textFontSize").val() === "medium") {
				fontSize = "14px";
			} else if($("#textFontSize").val() === "large") {
				fontSize = "16px";
			} else if($("#textFontSize").val() === "extraLarge") {
				fontSize = "18px";
			}
			
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
			var paddingRight = $("#textPaddingRight").val() === "" ? "0" : $("#textPaddingRight").val();
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
			/* var dataMarginTop = ($("#textMarginTop").data("prefix") !== "" ? $("#textMarginTop").data("prefix") + ":" : "") + marginTop + "" + $("#textMarginTop").data("suffix");
			var dataMarginRight = ($("#textMarginRight").data("prefix") !== "" ? $("#textMarginRight").data("prefix") + ":" : "") + marginRight + "" + $("#textMarginRight").data("suffix");
			var dataMarginBottom = ($("#textMarginBottom").data("prefix") !== "" ? $("#textMarginBottom").data("prefix") + ":" : "") + marginBottom + "" + $("#textMarginBottom").data("suffix");
			 var dataMarginLeft = ($("#textMarginLeft").data("prefix") !== "" ? $("#textMarginLeft").data("prefix") + ":" : "") + marginLeft + "" + $("#textMarginLeft").data("suffix");
			 */
			var dataPaddingTop = ($("#textPaddingTop").data("prefix") !== "" ? $("#textPaddingTop").data("prefix") + ":" : "") + paddingTop + "" + $("#textPaddingTop").data("suffix");
			var dataPaddingRight = ($("#textPaddingRight").data("prefix") !== "" ? $("#textPaddingRight").data("prefix") + ":" : "") + paddingRight + "" + $("#textPaddingRight").data("suffix");
			var dataPaddingBottom = ($("#textPaddingBottom").data("prefix") !== "" ? $("#textPaddingBottom").data("prefix") + ":" : "") + paddingBottom + "" + $("#textPaddingBottom").data("suffix");
			var dataPaddingLeft = ($("#textPaddingLeft").data("prefix") !== "" ? $("#textPaddingLeft").data("prefix") + ":" : "") + paddingLeft + "" + $("#textPaddingLeft").data("suffix");

			$(activeElement).parent().attr("style", dataTextAlign);
			$(activeElement).attr("style", dataFontFamily + ";" + dataFontStyle + ";" + dataFontSize + ";" + dataTextAlign + ";" + dataTextColor + ";" + dataTextBGColorCB + ";" + dataBgColor + ";" + dataPaddingTop + ";" + dataPaddingRight + ";" + dataPaddingBottom + ";" + dataPaddingLeft + ";");
		
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
				/* $(activeElement).data("prev_margin_top", dataMarginTop);
				$(activeElement).data("prev_margin_right", dataMarginRight);
				$(activeElement).data("prev_margin_bottom", dataMarginBottom);
				$(activeElement).data("prev_margin_left", dataMarginLeft); */
				$(activeElement).data("prev_padding_top", dataPaddingTop);
				$(activeElement).data("prev_padding_right", dataPaddingRight);
				$(activeElement).data("prev_padding_bottom", dataPaddingBottom);
				$(activeElement).data("prev_padding_left", dataPaddingLeft);
				
				$(activeElement).data("style_saved", true);
				activeElement = null;
				refreshTextSettings();
			}
			$(".configureBtn").removeClass("disabled");
		} else {
			$(".configureBtn").addClass("disabled");
		}
	} else {
		alert("please select a text element");
	}
}

function refreshTextSettings() {
	$("#textFontFamily").val(systemDefaultFont);
	$("#textFontStyle").val("font-weight:400;");
	$("#textFontSize").val("medium");
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
		$(ele).parent().attr("style", textAlign);
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
		var savedImageName = $(ele).data("name");
		var savedFloat = "";
		var savedWidth = "";
		
		var savedStyle = $(ele).data("style");
		if(savedStyle && savedStyle !== "") {
			for(var i in savedStyle.split(";")) {
				var style = savedStyle.split(";")[i];
				if(style.split(":").length > 1) {
					if(style.indexOf("float") !== -1) {
						savedFloat = style.split(":")[1];
					} else if(style.indexOf("width") !== -1) {
						savedWidth = style.split(":")[1];
					}
				}
//				console.log(savedStyle.split(";")[i]);
			}
		}
		var imageName = savedImageName !== "" ? savedImageName : "";
		var imageFloat = savedFloat !== "" ? savedFloat : "left";
		var imageWidth = savedWidth !== "" ? savedWidth : "50%";
		
		$("#logoImage").val(imageName);
		$("#imagePosition").val(imageFloat);
		$("#imageSize").val(imageWidth);
		
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

		/*var top = $("#invoiceLogoGrid").offset().top;
		window.scrollTo(0, (top-70));*/
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
		$("#logoImagePreview").attr("style", "float:" + imagePosition + ";width:" + imageSize);
		
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
		activeElement = $("#invoiceTable");
		flag = true;
	} else {
		flag = false;
	}
	
	if(!flag) {
		if(activeElement === null) {
			activeElement = $("#invoiceTable");
			ele = activeElement;
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
		var savedTHStyle = $(ele).find("th").attr("style");
		var savedTDStyle = $(ele).find("td").attr("style");
		
		var thBorder = "";
		var thFontSize = "";
		var thBGColor = "";
		var thTextColor = "";
		if(savedTHStyle && savedTHStyle !== "") {
			for(var i in savedTHStyle.split(";")) {
				var style = savedTHStyle.split(";")[i];
				if(style.split(":").length > 1) {
					if(style.indexOf("border") !== -1) {
						thBorder = style.split(":")[1];
					} else if(style.indexOf("font-size") !== -1) {
						thFontSize = style.split(":")[1];
					} else if(style.indexOf("background-color") !== -1) {
						thBGColor = style.split(":")[1];
					} else if(style.indexOf("color") !== -1) {
						thTextColor = style.split(":")[1];
					} 
				}
			}
		}
		var tdBorder = "";
		var tdFontSize = "";
		var tdBGColor = "";
		var tdTextColor = "";
		if(savedTDStyle && savedTDStyle !== "") {
			for(var i in savedTDStyle.split(";")) {
				var style = savedTDStyle.split(";")[i];
				if(style.split(":").length > 1) {
					if(style.indexOf("border") !== -1) {
						tdBorder = style.split(":")[1];
					} else if(style.indexOf("font-size") !== -1) {
						tdFontSize = style.split(":")[1];
					} else if(style.indexOf("background-color") !== -1) {
						tdBGColor = style.split(":")[1];
					} else if(style.indexOf("color") !== -1) {
						tdTextColor = style.split(":")[1];
					} 
				}
			}
		}
		if(thBorder) {
			$("#tableBorderCB").attr("checked", true);
			$("#tableBorderColor").val(thBorder.split(" ")[2]);
			$("#tableBorderColorSelector").val(thBorder.split(" ")[2]);
		}
		if(thFontSize === "12px") {
			$("#tableHeadFontSize").val("extraLarge");
		} else if(thFontSize === "10px") {
			$("#tableHeadFontSize").val("large");
		} else if(thFontSize === "8px") {
			$("#tableHeadFontSize").val("medium");
		} else if(thFontSize === "6px") {
			$("#tableHeadFontSize").val("small");
		}
		
		if(thBGColor !== "") {
			$("#tableHeadBGColor").val(thBGColor.replace(/!important/g,""));
			$("#tableHeadBGColorSelector").val(thBGColor.replace(/!important/g,""));
		}
		if(thTextColor !== "") {
			$("#tableHeadFontColor").val(thTextColor.replace(/!important/g,""));
			$("#tableHeadFontColorSelector").val(thTextColor.replace(/!important/g,""));
		}
		
		if(tdFontSize === "12px") {
			$("#tableBodyFontSize").val("extraLarge");
		} else if(tdFontSize === "10px") {
			$("#tableBodyFontSize").val("large");
		} else if(tdFontSize === "8px") {
			$("#tableBodyFontSize").val("medium");
		} else if(tdFontSize === "6px") {
			$("#tableBodyFontSize").val("small");
		}
		/*$("#tableBodyBGColor").val(tdBGColor.replace(/!important/g,""));
		$("#tableBodyBGColorSelector").val(tdBGColor.replace(/!important/g,""));
		$("#tableBodyFontColor").val(tdTextColor.replace(/!important/g,""));
		$("#tableBodyFontColorSelector").val(tdTextColor.replace(/!important/g,""));*/
//		console.log("--------TH--------" + thBorder + "--------" + thFontSize + "--------" + thBGColor + "--------" + thTextColor);
//		console.log("--------TD--------" + tdBorder + "--------" + tdFontSize + "--------" + tdBGColor + "--------" + tdTextColor);
		$("#textSettingsTab").hide();
		$("#imageSettingsTab").hide();
		$("#tableSettingsTab").fadeIn();
		$("#textSettingsTabHead a").removeClass("Act");
		$("#imageSettingsTabHead a").removeClass("Act");
		$("#tableSettingsTabHead a").addClass("Act");
		
		$(".configureBtn").addClass("disabled");
		$("#applyTableSettingsBtn").removeClass("disabled");
		$("#cancelTableSettingsBtn").removeClass("disabled");
		
		var top = $("#invoiceTable").offset().top;
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
//		var tableHeadFontSize = $("#tableHeadFontSize").val() !== "" ? $("#tableHeadFontSize").val() : "14";
		var tableHeadFontSize = "12";
		if($("#tableHeadFontSize").val() === "small") {
			tableHeadFontSize = "6";
		} else if($("#tableHeadFontSize").val() === "medium") {
			tableHeadFontSize = "8";
		} else if($("#tableHeadFontSize").val() === "large") {
			tableHeadFontSize = "10";
		} else if($("#tableHeadFontSize").val() === "extraLarge") {
			tableHeadFontSize = "12";
		}
		var tableHeadBGColor = $("#tableHeadBGColor").val();
		var tableHeadBGColorSelector = $("#tableHeadBGColorSelector").val();
		var tableHeadFontColor = $("#tableHeadFontColor").val();
		var tableHeadFontColorSelector = $("#tableHeadFontColorSelector").val();
//		var tableBodyFontSize = $("#tableBodyFontSize").val() !== "" ? $("#tableBodyFontSize").val() : "14";
		var tableBodyFontSize = "12";
		if($("#tableBodyFontSize").val() === "small") {
			tableBodyFontSize = "6";
		} else if($("#tableBodyFontSize").val() === "medium") {
			tableBodyFontSize = "8";
		} else if($("#tableBodyFontSize").val() === "large") {
			tableBodyFontSize = "10";
		} else if($("#tableBodyFontSize").val() === "extraLarge") {
			tableBodyFontSize = "12";
		}
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
//	activeElement = null;
	refreshTableSettings();
}

function refreshTableSettings() {
	$("#tableBorderCB").prop("checked", true);
	$("#tableBorderColor").val("#989898");
	$("#tableBorderColorSelector").val("#989898");
	$("#tableHeadFontSize").val("extraLarge");
	$("#tableHeadBGColor").val("#6C7686");
	$("#tableHeadBGColorSelector").val("#6C7686");
	$("#tableHeadFontColor").val("#FFFFFF");
	$("#tableHeadFontColorSelector").val("#FFFFFF");
	$("#tableBodyFontSize").val("large");
	$("#tableBodyBGColor").val("#F9F9F9");
	$("#tableBodyBGColorSelector").val("#F9F9F9");
	$("#tableBodyFontColor").val("#333");
	$("#tableBodyFontColorSelector").val("#333");
	
	if(activeElement !== null && activeElement !== undefined) {
		var thStyle = $(activeElement).find("thead").data("style");
		if(thStyle === undefined || thStyle === "") {
			thStyle += "border:1px solid #989898;";
			thStyle += "font-size:12px;";
			thStyle += "background-color:#6C7686;";
			thStyle += "color:#ffffff;";
		}
		$(activeElement).find("th").attr("style", thStyle);
		
		var tdStyle = $(activeElement).find("tbody").data("style");
		if(tdStyle === undefined || tdStyle === "") {
			tdStyle += "border:1px solid #989898;";
			tdStyle += "font-size:10px;";
			tdStyle += "background-color:#F9F9F9;";
			tdStyle += "color:#000;";
		}
		$(activeElement).find("td").attr("style", tdStyle);
	}
	
	activeElement = null;
}

function showTemplatePreview(showPreview) {
	$("#previewItems").empty();
	var text = "";
	$("#configItemsList li").each(function () {
		var index = 1;
		if($(this).hasClass("sortable-hidden")) {
			text += "<div class='previewInnerCta' data-type='hidden' data-section='item' data-position=" + $(this).data("position") + "></div>";
			index++;
		} else {
			var style = "";
			if($(this).data("section") === "image") {
				style = $(this).find("img").attr("style");
				text += "<div class='previewInnerCta' data-name='" + $(this).data("name") + "' data-type='image' data-section='item' data-position=" + $(this).data("position") + ">";
				var itemChecked = $(this).find("input[type='checkbox']").is(":checked");
				text += "	<span " + (itemChecked ? "class='active'" : "") + " style='" + style + "'>";
				text += "		<img src='" + $(this).find("img").attr("src") + "' data-name='" +  $(this).find("img").data("name") + "' style='" + style + "'>";
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
				var headText = "";
				var bodyText = "";
				
				headText += "<div class='previewInnerCta previewInnerCtaFull' data-name='" + $(this).data("name") + "' data-type='footer' data-section='item'>";
				$(this).find("div[class='cusInList']").each(function() {
					var itemChecked = $(this).find("input[type='checkbox']").is(":checked");
					bodyText += "	<span " + (itemChecked ? "class='active'" : "") + " style='" + $(this).find("label").attr("style") + "'>" + $(this).find("label").html() + "</span>";
				});
				
				headText += bodyText;
				headText += "</div>";
				if(bodyText !== "") {
					text += headText;
				} else {
					text += "<div class='previewInnerCta'></div>";
				}
			} else {
				if($(this).hasClass("eleRight")) {
					style = "text-align:right";
				} else {
					style = "text-align:left";
				}
				var headText = "";
				var bodyText = "";
				headText += "<div class='previewInnerCta' style='" + style + "' data-name='" + $(this).data("name") + "' data-type='text' data-section='item' data-position=" + $(this).data("position") + ">";
				$(this).find("div[class='cusInList']").each(function() {
					var itemChecked = $(this).find("input[type='checkbox']").is(":checked");
					var textElementStyle = $(this).find("label").attr("style");

					var parentStyle = $(this).attr("style");
					textElementStyle += parentStyle+";";
					
					bodyText += "<div style='"+parentStyle+"'><span " + (itemChecked ? "class='active'" : "") + " style='" + textElementStyle + "'>" + $(this).find("label").html() + "</span></div>";
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
	if(showPreview) {
		$("#templatePreview").show();
		$("body").css("overflow","hidden");
	}
	return true;
}

function closePreviewPopup() {
	$("#templatePreview").hide();
	$("body").css("overflow","auto");
}
function saveTemplate(previewFlag) {
	var saveFlag = false;
	if(!previewFlag) {
		saveFlag = showTemplatePreview();
	} else {
		saveFlag = true;
	}
	if(saveFlag) {
		var templateId = $("#templateId").val();
		var templateName = $("#templateName").val();
		var templateDescription = $("#templateDescription").val();
		if ($('#invoiceTemplateForm').validate(false, validationSettings)) {
			var index = 1;
			var items = [];
			$("#previewItems div[data-section='item']").each(function() {	
				var subItems = [];
				$(this).find("span").each(function() {
					if($(this).parent().data("type") === "image") {
						subItems.push({
							checked : $(this).hasClass("active"),
							imageName : $(this).find("img").data("name"),
							value : $(this).find("img").attr("src"),
							style : $(this).find("img").attr("style") !== "undefined" ? $(this).find("img").attr("style") : ""
						});
					} else {
						subItems.push({
							checked : $(this).hasClass("active"),
							value : $(this).parent().data("type") !== "table" ? $(this).html() : "",
							style : $(this).attr("style") !== "undefined" ? $(this).attr("style") : ""
						});
					}
				});
				items.push({
					name : $(this).data("name"),
					type : $(this).data("type"),
					position : $(this).data("position"),
					order : index,
					subItems : subItems
				});
				index++;
			});
//			console.log(JSON.stringify(items));
			var data = {"id":templateId, "name":templateName, "description":templateDescription, "jsonData":JSON.stringify(items)};
			$.ajax({
		    	type: "POST",
		    	data: data,
		        url: vmsUrl+"/saveCustomInvoiceTemplate?orgLocationId=" + orgLocationId + "&orgRelationId=" + orgRelationId,
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "INVALID_DATA" || response.response === "INVALID_USER" || response.response === "DUPLICATE_NAME") {
						showError(response.responseMessage);
						closePreviewPopup();
		        	} else if (response.response === "SUCCESS") {
		        		closePreviewPopup();
		        		$(".success_pop").show();
		        		setTimeout(function() {
		        			location.href = vmsUrl + "/customizeInvoice?orgLocationId=" + orgLocationId + "&orgRelationId=" + orgRelationId;				 			
		    	 		},1000);
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        }
			});
		}
	}
}