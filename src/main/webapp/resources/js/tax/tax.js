var tax = new Tax();
$(document).ready(function() {
	if (activeTaxTab === "state") {
		tax.getStateTaxList(0);
	} else if (activeTaxTab === "central") {
		alert("Central Tab");
	} else if(activeTaxTab === "createStateTax") {
		if(stateTaxId === 0)
			tax.addDynamicPTRow(true);
	} else if(activeTaxTab === "forms") {
		tax.getTaxFormList();
		if(formId && formId > 0) {
			tax.getDynamicTaxForm(formId, isEditable);
		}
		$("#formType").change(function() {
			tax.getDynamicTaxForm($(this).val(), isEditable);
		});
		
		$("#aa").click(function() {
			$(".subMenuView").slideToggle(100);
		});
		var cloneIndex = $(".clonedInput").length;
		$("#clonediv").click(function(){
		    $(".clonedInput").clone().appendTo("#cloneArea");
		    cloneIndex++;
		});
		
		var config = {
				  '.chosen-select'           : {},
				  '.chosen-select-deselect'  : {allow_single_deselect:true},
				  '.chosen-select-no-single' : {disable_search_threshold:10},
				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				  '.chosen-select-width'     : {width:"95%"}
				}
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
					var $this = $(this);
					$this.next().css({'width': '100%'});

			});
	}
	
	$("#saveStateTax").click(function() {
		if($('#addStateTaxForm').validate(false, validationSettings)) {
			var flag = true;
			var rowData = [];
			var monthData = [];
			$("div[data-name='ptRow']").each(function() {
				var amountFrom = parseFloat($(this).find("input[data-name='amountFrom']").val());
				var amountTo = parseFloat($(this).find("input[data-name='amountTo']").val());
				var amount = parseFloat($(this).find("input[data-name='amount']").val());
				var monthApplicable = $(this).find("select[data-name='monthApplicable']").val();
				var result = amountFrom + "<>" + amountTo + "<>" + amount + "<>" + monthApplicable;
				if(!rowData.includes(result)) {
					rowData.push(result);
				} else {
					$(this).append("<p class='jquery_form_error_message'>Same tax row already exists....</p>");
					flag = false;
					return;
				}
				if(!monthData.includes(monthApplicable)) {
					monthData.push(monthApplicable);
				} else {
					$(this).find("select[data-name='monthApplicable']").parent().append("<p class='jquery_form_error_message'>Month already exists....</p>");
					flag = false;
					return;
				}
				if(amountFrom > amountTo) {
					$(this).find("input[data-name='amountTo']").parent().append("<p class='jquery_form_error_message'>To amount should be less than From amount</p>");
					flag = false;
					return;
				}
			});
			if(flag){
				$("#saveStateTax").attr("disabled","disabled");
				$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
				var formData = getFormData("addStateTaxForm");
				$.ajax({
					type : "POST",
	        	    data: formData,
					url : sourceleadUrl+"/stateTax/add",
					success : function(response) {
						if (response.response === "AWKWARD") {
			        	    location.href = "/errorPage";
			        	} else if (response.response === "SUCCESS") {
			        		if (response.object > 0) {
			        			$(".success_pop").css("display","block");
				        		$(".success_pop span").html("StateTax " + (stateTaxId === 0 ? "added" : "updated" ) + " successfully....");
				        		setTimeout(function() {
				        			location.href=sourceleadUrl+"/stateTax/list";					 			
						 		},100);
			        		} else if (response.object === -1) {
			        			showError("Tax record already exists for the selected fields");
			        		}
			        	} else if (response.response === "INVALID_DATA") {
			        		showError(response.responseMessage);
			        	} else if (response.indexOf('Sign In') !== -1) {
			        		location.replace(sourceleadUrl);
			        	} else {
			        	    showError("Operation failed, please try again");
			        	}
						$("#saveStateTax").removeAttr("disabled","disabled");
						$("#loading").removeAttr("style");
					},
				});	
			}
			return true;
		} else {
			return false;
		}
	});
});
function Tax() {

	this.getStateTaxList = function(pageNo) {
		var pageSize = $("#pageSize").val();
		var selectedYear = $("#stateTaxYearList").val();
		var selectedState = $("#stateTaxStateList").val();
		$("#stateList").empty();
		$.post(sourceleadUrl + "/getStateTaxes", {yearFrequency:selectedYear, stateId:selectedState, pageNo:pageNo, pageSize:pageSize}, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var stList = response.data;
				if(stList.length > 0) {
					for(var e in stList) {
						var result = "<tr><td>";
						result += "<div>";
						result += "<a href='taxSummary?stateTaxId=" + stList[e].id + "'>" + stList[e].stateName + "</a>";
						result += "</div>";
						result += "</td>";
						result += "<td><div>" + stList[e].taxType + "</div></td>";
						result += "<td>";
						result += "<div>";
						result += "<ul class='list-unstyled list-inline mb0'>";
						result += "<li " + (stList[e].active ? "" : "class='disableBtn'") + "><a href='create?stateTaxId=" + stList[e].id + "' title='Edit' class='anchor-black'><i class='fa fa-pencil'></i></a></li>";
						if(stList[e].active) {
							result += "<li><a href='javascript:void()' onclick='tax.showChangeStatusPopup(" + stList[e].id + ", 1, 1, " + pageNo + ")' title='Make InActive' class='text-danger'><i class='fa fa-trash'></i></a></li>";
						} else {
							result += "<li><a href='javascript:void()' onclick='tax.showChangeStatusPopup(" + stList[e].id + ", 1, 0, " + pageNo + ")' title='Make Active' class='text-success'><i class='fa fa-edit'></i></a></li>";
						}
						result += "</ul>";
						result += "</div>";
						result += "</td></tr>";
						$("#stateList").append(result);
					}
					var paginator = response.paginator;
					var options = {
							currentPage : paginator.pageNo + 1,
							items : paginator.totalRows,
							itemsOnPage : paginator.pageSize,
							ellipsePageSet : false,
							cssStyle : 'light-theme',
							onPageClick : function(pageNumber, event) {
								tax.getStateTaxList(pageNumber-1);
							}
					};
					$("#pagination-demo").pagination(options);
//					pageMsg = " Showing "+startingRecord+" to "+endingRecord+" of "+totalRows+" entries ";
					
					$("#pageMsg").html("Showing " + ((paginator.pageNo * paginator.pageSize) + 1) + " to " + (((paginator.pageNo + 1) * paginator.pageSize) > paginator.totalRows ? paginator.totalRows : ((paginator.pageNo + 1) * paginator.pageSize)) + " of " + paginator.totalRows + " results");
				
					$("#stateListTable").show();
					$("#paginationDiv").show();
					$("#noResultsTable").hide();
				} else {
					$("#stateListTable").hide();
					$("#paginationDiv").hide();
					$("#noResultsTable").show();
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
	
	this.addDynamicPTRow = function (isFirst) {
		var result = "<div data-name='ptRow'><div class='col-md-3'>";
		result += "<label class='clearfix'>" + (isFirst ? "From Amount" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' data-name='amountFrom' name='professionalTaxList["+pfRowId+"].amountFrom' value='' placeholder='Enter Amount' field-name='From Amount' data-validation='required validate_float'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-3'>";
		result += "<label class='clearfix'>" + (isFirst ? "To Amount" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' data-name='amountTo' name='professionalTaxList["+pfRowId+"].amountTo' value='' placeholder='Enter Amount' field-name='To Amount' data-validation='required validate_float'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-3'>";
		result += "<label class='clearfix'>" + (isFirst ? "Amount" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' data-name='amount' name='professionalTaxList["+pfRowId+"].amount' value='' placeholder='Enter Amount' field-name='Amount' data-validation='required validate_float'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2'>";
		result += "<label class='clearfix'>" + (isFirst ? "Month" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<select class='width100percnt selectpicker' data-live-search='true' title='Select Month' data-name='monthApplicable' name='professionalTaxList["+pfRowId+"].monthApplicable' field-name='Month' data-validation='required'>";
		result += "<option>All</option>";
		result += "<option>April</option>";
		result += "<option>May</option>";
		result += "<option>June</option>";
		result += "<option>July</option>";
		result += "<option>August</option>";
		result += "<option>September</option>";
		result += "<option>October</option>";
		result += "<option>November</option>";
		result += "<option>December</option>";
		result += "<option>January</option>";
		result += "<option>February</option>";
		result += "<option>March</option>";
		result += "</select>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-1'>";
		result += "<label class='clearfix'>" + (isFirst ? "Actions" : "" ) + "</label>";
		result += "<ul class='list-unstyled list-inline mt5 text-center'>";
		if(isFirst) {
			result +="<li><a href='javascript:void(0)' class='cursorPointer'" +
					" onclick='tax.addDynamicPTRow();' title='Add Row' style='font-size: 20px;'> + </a></li>";
		} else {
			result += "<li><a title='Delete Row' class='fa fa-trash text-danger deleteRow' onClick='tax.deletePTRow(this)'></a></li>";
		}
		result += "</ul>";
		result += "</div>";
		result += "<div class='clearfix'></div>";
		result += "</div>";
		$("#dynamicPTList").append(result);
		$(".selectpicker").selectpicker('refresh');
		pfRowId++;
	}
	
	this.deletePTRow = function(ele) {
		var removalRow = $(ele).closest("div[data-name='ptRow']");
		$("#removeIds").val(($("#removeIds").val()!==""? $("#removeIds").val() + "," : "") + $(removalRow).data("id"));
		$(removalRow).remove();
		tax.refreshPTRows();
	}
	
	this.refreshPTRows = function() {
		pfRowId = 0;
		$("div[data-name='ptRow']").each(function() {
			$(this).find("input[data-name='amountFrom']").attr("name","professionalTaxList["+pfRowId+"].amountFrom");
			$(this).find("input[data-name='amountTo']").attr("name","professionalTaxList["+pfRowId+"].amountTo");
			$(this).find("input[data-name='amount']").attr("name","professionalTaxList["+pfRowId+"].amount");
			$(this).find("select[data-name='monthApplicable']").attr("name","professionalTaxList["+pfRowId+"].monthApplicable");
			pfRowId++;
		});
	}
	
	this.changeTaxStatus = function() {
		var type = $("#confirmId").data("type");
		var status = $("#confirmId").data("status");
		if(type===1) {
			var pageNo = $("#confirmId").data("pageNo");
		}
		$.post(sourceleadUrl + "/" + (type === 1 ? "stateTax" : "centralTax") + "/changeStatus", {taxId: $("#confirmId").data("id")}, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				tax.closeChangeStatusPopup();
				$(".success_pop").css("display","block");
        		$(".success_pop span").html("StateTax made as " + (status === 1 ? "inactive" : "active" ) + " successfully....");
        		setTimeout(function() {
        			if(type === 1) {
        				tax.getStateTaxList(pageNo);	
        			} else if(type === 2) {
        				
        			}
        			$(".success_pop").css("display","none");
		 		},1000);
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
	
	this.changeFormStatus = function() {
		var type = $("#confirmId").data("type");
		var status = $("#confirmId").data("status");
		$.post(sourceleadUrl + "/form/changeStatus", {id: $("#confirmId").data("id")}, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				tax.closeChangeStatusPopup();
				$(".success_pop").css("display","block");
				$(".success_pop span").html((type === 3 ? "Form Section" : "Form Section Item")+" made as " + (status === 1 ? "inactive" : "active" ) + " successfully....");
        		setTimeout(function() {
        			tax.getTaxFormList();
        			$(".success_pop").css("display","none");
		 		},1000);
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
	
	this.changeFormSectionStatus = function() {
		var type = $("#confirmId").data("type");
		var status = $("#confirmId").data("status");
		$.post(sourceleadUrl + "/form/itDeclaration/changeStatus", {id: $("#confirmId").data("id"),type:type}, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				tax.closeChangeStatusPopup();
				$(".success_pop").css("display","block");
				$(".success_pop span").html((type === 3 ? "Form Section" : "Form Section Item")+" made as " + (status === 1 ? "inactive" : "active" ) + " successfully....");
        		setTimeout(function() {
        			tax.getDynamicTaxForm($("#taxFormId").val(), true);
        			$(".success_pop").css("display","none");
		 		},1000);
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
	
	this.showChangeStatusPopup = function(id, type, status, pageNo) {
		// type : 1-State, 2-Central
		$("#remove_user_pop p").html("You are choosing to make this " + (type === 3 ? "section" : type === 4 ? "sectionItem" : "Tax") + " as "+ (status === 1 ? "Inactive": "Active"));
		$("#confirmId").data("id", id);
		$("#confirmId").data("type", type);
		$("#confirmId").data("status", status);
		$("#confirmId").data("pageNo", pageNo);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	
	this.closeChangeStatusPopup = function() {
		$("#remove_user_pop").slideUp(200);
		$(".project_inn_mask").fadeOut(200);
	}
	
	this.getTaxFormList = function() {
		$("#formList").empty();
		$.post(sourceleadUrl + "/form/list", {}, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var formList = response.data;
				if(formList.length > 0) {
					for(var e in formList) {
						var result = "<tr><td>";
						result += "<div>";
						result += "<a href='create?formId=" + formList[e].id + "'>" + formList[e].name + "</a>";
						result += "</div>";
						result += "</td>";
						var sectionNames = "";
						for(var i=0; i < formList[e].sourceleadTaxSectionList.length; i++){
							sectionNames += formList[e].sourceleadTaxSectionList[i].name + (i < (formList[e].sourceleadTaxSectionList.length - 1) ? ",<br>" : "");
						}
						/*for(var t in formList[e].sourceleadTaxSectionList) {
							sectionNames += "<br>" + formList[e].sourceleadTaxSectionList[t].name;
						}*/
						result += "<td><div>" + (sectionNames === "" ? "--------" : sectionNames) + "</div></td>";
						result += "<td>";
						result += "<div>";
						result += "<ul class='list-unstyled list-inline mb0'>";
						result += "<li><a href='view?formId=" + formList[e].id + "' title='Edit' class='anchor-black'><i class='fa fa-eye'></i></a></li>";
						result += "<li " + (formList[e].active ? "" : "class='disableBtn'") + "><a href='create?formId=" + formList[e].id + "' title='Edit' class='anchor-black'><i class='fa fa-pencil'></i></a></li>";
						if(formList[e].active) {
							result += "<li><a href='javascript:void()' onclick='tax.showChangeStatusPopup(" + formList[e].id + ", 1, 1)' title='Make InActive' class='text-danger'><i class='fa fa-trash'></i></a></li>";
						} else {
							result += "<li><a href='javascript:void()' onclick='tax.showChangeStatusPopup(" + formList[e].id + ", 1, 0)' title='Make Active' class='text-success'><i class='fa fa-edit'></i></a></li>";
						}
						result += "</ul>";
						result += "</div>";
						result += "</td></tr>";
						$("#formList").append(result);
					}
					
					$("#formListTable").show();
					$("#noResultsTable").hide();
				} else {
					$("#formListTable").hide();
					$("#noResultsTable").show();
				}
			} else if (response.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
	
	this.getDynamicTaxForm = function(formId, isEditable) {
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$("#dynamicForm").load(sourceleadUrl+"/form/getDynamicForm?formId=" + formId + "&isEditable=" + isEditable, function(response, status, xhr){
			if(status === "success") {
				$("#loading").removeAttr("style");
				
				$(".edit_sec_name").click(function(){
					$(this).parents("tr").find(".editElementDiv").hide();
					$(this).parents("tr").find(".saveElementDiv").show();
				});
				$(".saveSectionName").click(function(){
					var id = $(this).parents("th").find("input[name='id']").val();
					if($("#section_" + id + "_name_form").validate(false, validationSettings)) {
						var formData = getFormData("section_" + id + "_name_form");
						callAddITDeclarationSectionRestService(formData, 2);
						return true;
					} else {
						return false;
					}
				});
				$(".cancelSectionNameEdit").click(function() {
					$(this).parents("th").find("input[name='name']").val($(this).parents("th").find("input[name='name']").data("old_value"));
					$(this).parents("th").find(".editElementDiv").show();
					$(this).parents("th").find(".saveElementDiv").hide();
				});
				$(".editRow").click(function(){
					$(this).parents("tr").find(".editElementDiv").hide();
					$(this).parents("tr").find(".saveElementDiv").show();
					$(this).parent().hide();
					$(this).parents("tr").find(".deleteRow").parent().hide();
					$(this).parents("tr").find(".cancelEditRow").parent().show();
					$(this).parents("tr").find(".saveRow").parent().show();
				});
				$(".cancelEditRow").click(function(){
					$(this).parents("tr").find(".editElementDiv").show();
					$(this).parents("tr").find(".saveElementDiv").hide();
					$(this).parent().hide();
					$(this).parents("tr").find(".saveRow").parent().hide();
					$(this).parents("tr").find(".editRow").parent().show();
					$(this).parents("tr").find(".deleteRow").parent().show();
					
					//resetting the edited values to their original old values
					$(this).parents("tr").find("input[name='name']").val($(this).parents("tr").find("input[name='name']").data("old_value"));
					$(this).parents("tr").find("input[name='maxLimit']").val($(this).parents("tr").find("input[name='maxLimit']").data("old_value"));
					$(this).parents("tr").find("textarea[name='description']").val($(this).parents("tr").find("textarea[name='description']").data("old_value"));
				});
				$(".saveRow").click(function() {
					var id = $(this).parents("tr").find("input[name='id']").val();
					if($("#sectionItem_" + id + "_name_form").validate(false, validationSettings) &&
							$("#sectionItem_" + id + "_maxLimit_form").validate(false, validationSettings) &&
							$("#sectionItem_" + id + "_description_form").validate(false, validationSettings)) {
						var nameFormData = getFormData("sectionItem_" + id + "_name_form");
						var maxLimitFormData = getFormData("sectionItem_" + id + "_maxLimit_form");
						var descriptionFormData = getFormData("sectionItem_" + id + "_description_form");
						var formData = $.extend(true, nameFormData, maxLimitFormData, descriptionFormData);
						callAddITDeclarationSectionItemRestService(formData, 2);	
						return true;
					} else {
						return false;
					}
				});
			}
		});
	}
}
//IT Declaration Form events
function addSection() {
	$(".comMask, .section").fadeIn();
}
function modalClose() {
	$(".comMask, .section, .addSectionItemPopup").fadeOut();
}
function addSectionItem(sectionId, sectionName){
	$(".addSectionItemPopup").data("sectionId", sectionId);
	$(".addSectionItemPopup").find("h4").html(sectionName);
	$("#taxSectionId").val(sectionId);
	$(".comMask, .addSectionItemPopup").fadeIn();
}
function addITDeclarationSection() {
	if($('#itDeclarationSectionForm').validate(false, validationSettings)) {
		$("#addITDeclarationSectionBtn").attr("disabled","disabled");
		var formData = getFormData("itDeclarationSectionForm");
		callAddITDeclarationSectionRestService(formData, 1);
		return true;
	} else {
		return false;
	}
}

function callAddITDeclarationSectionRestService(formData, type) {
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	$.ajax({
		type : "POST",
	    data: formData,
		url : sourceleadUrl+"/form/addITDeclarationSection",
		success : function(response) {
			if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
    			modalClose();
				$(".success_pop").css("display","block");
        		$(".success_pop span").html("Tax Section " + (type === 1 ? "added" : "name updated") + " successfully....");
        		setTimeout(function() {
        			tax.getDynamicTaxForm($("#taxFormId").val(), true);
        			$(".success_pop").css("display","none");
		 		},1000);
        	} else if (response.response === "INVALID_DATA" || response.response === "FAILED") {
        		showError(response.responseMessage);
        	} else if (response.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
			$("#addITDeclarationSectionBtn").removeAttr("disabled","disabled");
			$("#loading").removeAttr("style");
		},
	});
}

function addITDeclarationSectionItem() {
	if($('#itDeclarationSectionItemForm').validate(false, validationSettings)) {
		$("#addITDeclarationSectionItemBtn").attr("disabled","disabled");
		var formData = getFormData("itDeclarationSectionItemForm");
		callAddITDeclarationSectionItemRestService(formData, 1)
		return true;
	} else {
		return false;
	}
}

function callAddITDeclarationSectionItemRestService(formData, type) {
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	$.ajax({
		type : "POST",
	    data: formData,
		url : sourceleadUrl+"/form/addITDeclarationSectionItem",
		success : function(response) {
			if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
    			modalClose();
				$(".success_pop").css("display","block");
        		$(".success_pop span").html("Tax Section Item " + (type === 1 ? "added" : "updated") + " successfully....");
        		setTimeout(function() {
        			tax.getDynamicTaxForm($("#taxFormId").val(), true);
        			$(".success_pop").css("display","none");
		 		},1000);
        	} else if (response.response === "INVALID_DATA" || response.response === "FAILED") {
        		showError(response.responseMessage);
        	} else if (response.indexOf('Sign In') !== -1) {
        		location.replace(sourceleadUrl);
        	} else {
        	    showError("Operation failed, please try again");
        	}
			$("#addITDeclarationSectionBtn").removeAttr("disabled","disabled");
			$("#loading").removeAttr("style");
		},
	});
}