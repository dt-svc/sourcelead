var tax = new Tax();
var pfRowId = 0;
var tdsRowId = 0;

$(document).ready(function() {
	if (activeTaxTab === "state") {
		tax.getStateTaxList(0);
	} else if (activeTaxTab === "central") {
		tax.getCentralTaxList();
	} else if(activeTaxTab === "createStateTax") {
		if(stateTaxId === 0)
			tax.addDynamicPTRow(true);
	} else if(activeTaxTab === "createCentralTax") {
		if(centralTaxId === 0)
			tax.addDynamicTDSRow(true);
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
	
	$("#saveCentralTax").click(function() {
		if($('#createCentralTaxform').validate(false, validationSettings)) {
			$("#saveCentralTax").attr("disabled","disabled");
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			var formData = getFormData("createCentralTaxform");
			$.ajax({
				type : "POST",
        	    data: formData,
				url : sourceleadUrl+"/centralTax/add",
				success : function(response) {
					if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		if (response.object > 0) {
			        		$(".success_pop").css("display","block");
			        		$(".success_pop span").html("CentralTax " + (centralTaxId === 0 ? "added" : "updated" ) + " successfully....");
			        		setTimeout(function() {
			        			location.href=sourceleadUrl+"/centralTax/list";					 			
					 		},100);    
		        		} else if (response.object === -1) {
		        			showError("Tax record already exists for the selected fields");
		        		}
		        	} else if(response.response === "FAILED") {
		        		showError(response.responseMessage);
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
					$("#saveCentralTax").removeAttr("disabled","disabled");
					$("#loading").removeAttr("style");
				},
			});
			return true;
		} else {
			return false;
		}
	});
	
});
function Tax() {
	this.getCentralTaxList = function() {
		var selectedYear = $("#centralTaxYearList").val();
		$.post(sourceleadUrl + "/getCentralTaxes", {yearFrequency:selectedYear}, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var stList = response.data;
				console.log(stList.length);
				if(stList.length > 0) {
					for(var e in stList){
						var result = "<tr>";
						result += "<td>";
						result += "<div>";
						result += "<a href='taxSummary?sourceleadTaxId=" + stList[e].id + "'>"+ stList[e].sourceleadTaxType + "</div></td>";
						result += "</div>";
						result += "</td>";
						result += "<td>";
						result += "<div>";
						result += "<ul class='list-unstyled list-inline mb0'>";
						result += "<li " + (stList[e].active ? "" : "class='disableBtn'") + "><a href='javascript:void()' title='Edit' class='anchor-black'><i class='fa fa-pencil'></i></a></li>";
						if(stList[e].active) {
							result += "<li><a href='javascript:void()' onclick='tax.showChangeStatusPopup(" + stList[e].id + ", 1," + pageNo + ")' title='Make InActive' class='text-danger'><i class='fa fa-trash'></i></a></li>";
						} else {
							result += "<li><a href='javascript:void()' onclick='tax.showChangeStatusPopup(" + stList[e].id + ", 2," + pageNo + ")' title='Make Active' class='text-success'><i class='fa fa-edit'></i></a></li>";
						}
						result += "</ul>";
						result += "</div>";
						result += "</td>";
						result += "</tr>";
					}
					$("#noResultsTable").hide();
					$("#centralListTable").show();
					$("#centralList").empty();
					$("#centralList").append(result);
				} else {
					$("#centralListTable").hide();
					$("#noResultsTable").show();
				}
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}	
	
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

	this.addDynamicTDSRow = function(isFirst) {
		var result = "<div data-id=tdsRow" + tdsRowId + ">";
		result += "<div class='col-md-2'>";
		result += "<label class='clearfix'>" + (isFirst ? "Age" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<select class='width100percnt selectpicker' data-live-search='true' title='Select Age' name='tdsTaxList["+tdsRowId+"].age' field-name='Age' data-validation='required'>";
		result += "<option>0 - 60 Years</option>";
		result += "<option>60 - 80 Years</option>";
		result += "<option>Above 80</option>";
		result += "</select>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2'>";
		result += "<label class='clearfix'>" + (isFirst ? "From" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' name='tdsTaxList["+tdsRowId+"].fromamount' value='' placeholder='Enter' field-name='From ' data-validation='required validate_float'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2'>";
		result += "<label class='clearfix'>" + (isFirst ? "To" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' name='tdsTaxList["+tdsRowId+"].toamount' value='' placeholder='Enter' field-name='To' data-validation='required validate_float'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-1'>";
		result += "<label class='clearfix'>" + (isFirst ? "Percentage" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' name='tdsTaxList["+tdsRowId+"].percentage' value='' placeholder='Enter' field-name='Percentage' data-validation='required validate_float'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2'>";
		result += "<label class='clearfix'>" + (isFirst ? "Education Ceiss" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' name='tdsTaxList["+tdsRowId+"].educationceiss' value='' placeholder='Enter' field-name='Education Ceiss' data-validation='required validate_float'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2'>";
		result += "<label class='clearfix'>" + (isFirst ? "Surcharge" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' name='tdsTaxList["+tdsRowId+"].surcharge' value='' placeholder='Enter' field-name='Surcharge' data-validation='required validate_float'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-1'>";
		result += "<label class='clearfix'>Actions</label>";
		result += "<ul class='list-unstyled list-inline mt5 text-center'>";
		if(isFirst) {
			result +="<li><a href='javascript:void(0)' class='cursorPointer'" +
					" onclick='tax.addDynamicTDSRow();' title='Add Row' style='font-size: 20px;'> + </a></li>";
		} else {
			result += "<li><a title='Delete Row' class='fa fa-trash text-danger deleteRow' onClick='tax.deleteTDSRow("+tdsRowId+")'></a></li>";
		}
		
		result += "</ul>";
		result += "</div>";
		result += "<div class='clearfix'></div>";
		result += "</div>";
		$("#dynamicTDSList").append(result);
		$(".selectpicker").selectpicker('refresh');
		tdsRowId++;
	}
	
	/*this.deleteTDSRow = function(rowId) {
		$("div[data-id='tdsRow"+rowId+"']").remove();
	}
	*/
	
	this.deleteTDSRow = function(ele) {
		var removalRow = $(ele).closest("div[data-name='tdsRow']");
		$("#removeIds").val(($("#removeIds").val()!==""? $("#removeIds").val() + "," : "") + $(removalRow).data("id"));
		$(removalRow).remove();
		tax.refreshTDSRows();
	}
	
	this.refreshTDSRows = function() {
		tdsRowId = 0;
		$("div[data-name='tdsRow']").each(function() {
			$(this).find("input[data-name='fromamount']").attr("name","tdsTaxList["+tdsRowId+"].fromamount");
			$(this).find("input[data-name='toamount']").attr("name","tdsTaxList["+tdsRowId+"].toamount");
			$(this).find("input[data-name='percentage']").attr("name","tdsTaxList["+tdsRowId+"].percentage");
			$(this).find("select[data-name='educationceiss']").attr("name","tdsTaxList["+tdsRowId+"].educationceiss");
			$(this).find("select[data-name='surcharge']").attr("name","tdsTaxList["+tdsRowId+"].surcharge");
			tdsRowId++;
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
	
	this.showChangeStatusPopup = function(taxId, type, status, pageNo) {
		$("#remove_user_pop p").html("You are choosing to make the Tax as "+ (type === 1 ? "Inactive": "Active"));
		$("#confirmId").data("id", taxId);
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
	
	this.deletePTRow = function(ele) {
		var removalRow = $(ele).closest("div[data-name='tdsRow']");
		$("#removeIds").val(($("#removeIds").val()!==""? $("#removeIds").val() + "," : "") + $(removalRow).data("id"));
		$(removalRow).remove();
		tax.refreshTDSRows();
	}
	
	this.refreshTDSRows = function() {
		tdsRowId = 0;
		$("div[data-name='tdsRow']").each(function() {
			$(this).find("input[data-name='fromamount']").attr("name","tdsTaxList["+tdsRowId+"].fromamount");
			$(this).find("input[data-name='toamount']").attr("name","tdsTaxList["+tdsRowId+"].toamount");
			$(this).find("input[data-name='percentage']").attr("name","tdsTaxList["+tdsRowId+"].percentage");
			$(this).find("select[data-name='educationceiss']").attr("name","tdsTaxList["+tdsRowId+"].educationceiss");
			$(this).find("select[data-name='surcharge']").attr("name","tdsTaxList["+tdsRowId+"].surcharge");
			pfRowId++;
		});
	}

}