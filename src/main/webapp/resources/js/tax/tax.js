var tax = new Tax();
var pfRowId = 0;
var tdsRowId = 0;
var formId = 0;

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
			var fromAmountToAmountData = [];
			var monthData = [];
			$("div[data-name='ptRow']").each(function() {
				var amountFrom = parseFloat($(this).find("input[data-name='amountFrom']").val());
				var amountTo = parseFloat($(this).find("input[data-name='amountTo']").val());
				var amount = parseFloat($(this).find("input[data-name='amount']").val());
				var monthApplicable = $(this).find("select[data-name='monthApplicable']").val();
				var rowResult = amountFrom + "<>" + amountTo + "<>" + amount + "<>" + monthApplicable;
				var fromAmountToAmountResult = amountFrom + "<>" + amountTo + "<>" + monthApplicable;
				if(!rowData.includes(rowResult)) {
					rowData.push(rowResult);
				} else {
					$(this).append("<p class='jquery_form_error_message'>Same tax row already exists....</p>");
					flag = false;
					return;
				}
				if(!fromAmountToAmountData.includes(fromAmountToAmountResult)){
					fromAmountToAmountData.push(fromAmountToAmountResult);
				} else {
					$(this).find("input[data-name='amountFrom']").parent().append("<p class='jquery_form_error_message'>From amount already exists for the selected Month (" + monthApplicable + ")....</p>");
					$(this).find("input[data-name='amountTo']").parent().append("<p class='jquery_form_error_message'>To amount already exists for the selected Month (" + monthApplicable + ")....</p>");
					flag = false;
					return;
				}
				if(flag) {
					monthData.push(monthApplicable);
				} else {
					$(this).find("select[data-name='monthApplicable']").parent().append("<p class='jquery_form_error_message'>Month already exists....</p>");
					flag = false;
					return;
				}
				if(amountFrom > amountTo) {
					$(this).find("input[data-name='amountTo']").parent().append("<p class='jquery_form_error_message'>'To amount' should be greater than 'From amount'</p>");
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
			var flag = true;
			var rowData = [];
			$("div[data-name='tdsRow']").each(function() {
				var age = $(this).find("select[data-name='age']").val();
				var fromamount = parseFloat($(this).find("input[data-name='fromamount']").val());
				var toamount = parseFloat($(this).find("input[data-name='toamount']").val());
				var percentage = parseFloat($(this).find("input[data-name='percentage']").val());
				var educationceiss = parseFloat($(this).find("input[data-name='educationceiss']").val());
				var surcharge = parseFloat($(this).find("input[data-name='surcharge']").val());
				var result = fromamount + "<>" + toamount + "<>" + percentage + "<>" +  educationceiss + "<>" + surcharge + "<>" + age;
				if(!rowData.includes(result)) {
					rowData.push(result);
				} else {
					$(this).append("<p class='jquery_form_error_message'>Same tax row already exists....</p>");
					flag = false;
					return;
				}
			});
			if(flag){
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
						result += "<a href='taxSummary?centraltaxuuid=" + stList[e].centraltaxuuid + "'>"+ stList[e].sourceleadTaxType + "</div></td>";
						result += "</div>";
						result += "</td>";
						result += "<td>";      
						result += "<div>";
						result += "<ul class='list-unstyled list-inline mb0'>";
						result += "<li " + (stList[e].active ? "" : "class='disableBtn'") + "><a href='create?centraltaxuuid=" + stList[e].centraltaxuuid + "' title='Edit' class='anchor-black'><i class='fa fa-pencil'></i></a></li>";
						if(stList[e].active) {
							result += "<li><a href='javascript:void()' onclick='tax.showChangeStatusPopup(" + stList[e].id + ", 2, 1, 0)' title='Make InActive' class='text-danger'><i class='fa fa-trash'></i></a></li>";
						} else {
							result += "<li><a href='javascript:void()' onclick='tax.showChangeStatusPopup(" + stList[e].id + ", 2, 0, 0)' title='Make Active' class='text-success'><i class='fa fa-edit'></i></a></li>";
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
						result += "<a href='taxSummary?statetaxuuid=" + stList[e].statetaxuuid + "'>" + stList[e].stateName + "</a>";
						result += "</div>";
						result += "</td>";
						result += "<td><div>" + stList[e].taxType + "</div></td>";
						result += "<td>";
						result += "<div>";
						result += "<ul class='list-unstyled list-inline mb0'>";
						result += "<li " + (stList[e].active ? "" : "class='disableBtn'") + "><a href='create?statetaxuuid=" + stList[e].statetaxuuid + "' title='Edit' class='anchor-black'><i class='fa fa-pencil'></i></a></li>";
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
		if($(removalRow).data("id")!== undefined){
			$("#removeIds").val(($("#removeIds").val()!==""? $("#removeIds").val() + "," : "") + $(removalRow).data("id"));
		}
		$(removalRow).remove();
		tax.refreshPTRows();
	}
	
/*	this.deletePTRow = function(ele) {
		var removalRow = $(ele).closest("div[data-name='ptRow']");
		$("#removeIds").val(($("#removeIds").val()!==""? $("#removeIds").val() + "," : "") + $(removalRow).data("id"));
		$(removalRow).remove();
		tax.refreshPTRows();
	}*/
	
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
		var result = "<div data-name='tdsRow' class='colmd2Custom'>";
		result += "<div class='col-md-2 mb'>";
		result += "<label class='clearfix'>" + (isFirst ? "Age" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<select class='width100percnt selectpicker' data-live-search='true' data-name='age' title='Select Age' name='tdsTaxList["+tdsRowId+"].age' field-name='Age' data-validation='required'>";
		result += "<option>0 - 60 Years</option>";
		result += "<option>60 - 80 Years</option>";
		result += "<option>Above 80</option>";
		result += "</select>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2 mb'>";
		result += "<label class='clearfix'>" + (isFirst ? "From" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='number' class='form-control' name='tdsTaxList["+tdsRowId+"].fromamount' data-name='fromamount' value='' placeholder='Enter' field-name='From ' data-validation='required validate_Space validate_int validate_length length1-10'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2 mb'>";
		result += "<label class='clearfix'>" + (isFirst ? "To" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='number' class='form-control' name='tdsTaxList["+tdsRowId+"].toamount' data-name='toamount' value='' placeholder='Enter' field-name='To' data-validation='required validate_Space validate_int validate_length length1-10'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2 mb width150px'>";
		result += "<label class='clearfix'>" + (isFirst ? "Percentage" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' name='tdsTaxList["+tdsRowId+"].percentage' data-name='percentage' value='' placeholder='Enter' field-name='Percentage' data-validation='required validate_Space validate_number validate_between between0-99'>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2 mb width150px'>";
		result += "<label class='clearfix'>" + (isFirst ? "Education Ceiss" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' name='tdsTaxList["+tdsRowId+"].educationceiss' data-name='educationceiss' value='' placeholder='Enter' field-name='Education Cess' data-validation='validate_length length1-3 '>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-2 mb'>";
		result += "<label class='clearfix'>" + (isFirst ? "Surcharge" : "" ) + "</label>";
		result += "<div class='clearfix'>";
		result += "<input type='text' class='form-control' name='tdsTaxList["+tdsRowId+"].surcharge' data-name='surcharge' value='' placeholder='Enter' field-name='Surcharge' data-validation='validate_length length1-10 '>";
		result += "</div>";
		result += "</div>";
		result += "<div class='col-md-1 mb'>";
		result += "<label class='clearfix'>" + (isFirst ? "Actions" : "" ) + "</label>";
		result += "<ul class='list-unstyled list-inline mt5 text-center'>";
		if(isFirst) {
			result +="<li><a href='javascript:void(0)' class='cursorPointer'" +
					" onclick='tax.addDynamicTDSRow();' title='Add Row' style='font-size: 20px;'> + </a></li>";
		} else {
			result += "<li><a title='Delete Row' class='fa fa-trash text-danger deleteRow' onClick='tax.deleteTDSRow(this)'></a></li>";
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
		if($(removalRow).data("id")!== undefined) {
			$("#removeIds").val(($("#removeIds").val()!==""? $("#removeIds").val() + "," : "") + $(removalRow).data("id"));
		}
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
        				tax.getCentralTaxList();
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
        			tax.getDynamicTaxForm($("#sectionTaxFormId").val(), true);
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
						result += "<a href='create?uuid=" + formList[e].uuid + "'>" + formList[e].name + "</a>";
						result += "</div>";
						result += "</td>";
						var sectionNames = "";
						if(formList[e].sourceleadTaxSectionList != null) {
							for(var i=0; i < formList[e].sourceleadTaxSectionList.length; i++){
								sectionNames += formList[e].sourceleadTaxSectionList[i].name + (i < (formList[e].sourceleadTaxSectionList.length - 1) ? ",<br>" : "");
							}
						}
						result += "<td><div>" + (sectionNames === "" ? "--------" : sectionNames) + "</div></td>";
						result += "<td>";
						result += "<div>";
						result += "<ul class='list-unstyled list-inline mb0'>";
						result += "<li><a href='view?uuid=" + formList[e].uuid + "' title='View' class='anchor-black'><i class='fa fa-eye'></i></a></li>";
						result += "<li " + (formList[e].active ? "" : "class='disableBtn'") + "><a href='create?uuid=" + formList[e].uuid + "' title='Edit' class='anchor-black'><i class='fa fa-pencil'></i></a></li>";
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
				
				/*$(".edit_sec_name").click(function(){
					$(this).parents("tr").find(".editElementDiv").hide();
					$(this).parents("tr").find(".saveElementDiv").show();
				});*/
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
	$("#taxFormTitle").html("New Section");
	$("#sectionId").val(0);
	$("#sectionTaxFormId").val(0);
	$("#sectionName").val("");
	$("#sectionMaxLimit").val("");
	$("#sectionDescription").val("");
	$(".comMask, .section").fadeIn();
}
function editSection(id, taxFormId, name, maxLimit, description) {
	$("#taxFormTitle").html("Edit Section (" + name + ")");
	$("#sectionId").val(id);
	$("#sectionTaxFormId").val(taxFormId);
	$("#sectionName").val(name);
	$("#sectionMaxLimit").val(maxLimit);
	$("#sectionDescription").val(description);
	$(".comMask, .section").fadeIn();
}
function modalClose() {
	$(".comMask, .section, .addSectionItemPopup, .addSectionChallanItemPopup").fadeOut();
}
function addSectionItem(sectionId, sectionName){
	$(".addSectionItemPopup").data("sectionId", sectionId);
	$(".addSectionItemPopup").find("h4").html("New Section Item (" + sectionName + ")");
	$("#taxSectionId").val(sectionId);
	$(".comMask, .addSectionItemPopup").fadeIn();
}
function addITDeclarationSection() {
	if($('#itDeclarationSectionForm').validate(false, validationSettings)) {
		$("#addITDeclarationSectionBtn").attr("disabled","disabled");
		var formData = getFormData("itDeclarationSectionForm");
		callAddITDeclarationSectionRestService(formData, $("#sectionId").val() === "0" ? 1 : 2);
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
        		$(".success_pop span").html("Tax Section " + (type === 1 ? "added" : "updated") + " successfully....");
        		setTimeout(function() {
        			tax.getDynamicTaxForm($("#sectionTaxFormId").val(), true);
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
        			tax.getDynamicTaxForm($("#sectionTaxFormId").val(), true);
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




function addITChallanSection() {
	if($('#itChallanSectionForm').validate(false, validationSettings)) {
		$("#addITChallanSectionBtn").attr("disabled","disabled");
		var formData = getFormData("itChallanSectionForm");
		callAddITChallanSectionRestService(formData, 1);
		return true;
	} else {
		return false;
	}
}

function editITChallanSection(taxFormId, sectionId, name, description){
	$(".comMask, .section").fadeIn();
	$("#taxFormId").val(taxFormId);
	$("#sectionId").val(sectionId);
	$("#sectionName").val(name);
	$("#sectionDescription").val(description);
}



function callAddITChallanSectionRestService(formData, type) {
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	$.ajax({
		type : "POST",
	    data: formData,
		url : sourceleadUrl+"/form/addITChallanSection",
		success : function(response) {
			if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
    			modalClose();
				$(".success_pop").css("display","block");
        		$(".success_pop span").html("Tax Section " + (type === 1 ? "added" : "name updated") + " successfully....");
        		setTimeout(function() {
        			tax.getDynamicTaxForm($("#sectionTaxFormId").val(), true);
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




function addITChallanSectionItem() {
	/*if($('#itChallanSectionItemForm').validate(false, validationSettings)) {*/
		$("#addITChallanSectionItemBtn").attr("disabled","disabled");
		var formData = getFormData("AddItChallanSectionItemForm");
		callAddITChallanSectionItemRestService(formData, 2)
		return true;
	/*} else {
		return false;
	}*/
}

function editChallanSectionItems(){
	var formData = getFormData("editItChallanSectionItemForm");
	callAddITChallanSectionItemRestService(formData, 2);
}

function callAddITChallanSectionItemRestService(formData, type) {
	document.getElement
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	$.ajax({
		type : "POST",
	    data: formData,
		url : sourceleadUrl+"/form/addITChallanSectionItem",
		success : function(response) {
			if (response.response === "AWKWARD") {
        	    location.href = "/errorPage";
        	} else if (response.response === "SUCCESS") {
    			modalClose();
				$(".success_pop").css("display","block");
        		$(".success_pop span").html("Tax Section Item " + (type === 1 ? "added" : "updated") + " successfully....");
        		setTimeout(function() {
        			tax.getDynamicTaxForm($("#sectionTaxFormId").val(), true);
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
			flushPopupData();
		},
	});
}

function flushPopupData(){
	$("#customFieldId").val('');
	$("#addITChallanSectionItemBtn").removeAttr('onclick');
	$("#addITChallanSectionItemBtn").attr('onclick','saveAddFormField("")');
	$("#labelName").val('');
	$("#isRequired").val('');
	$('#isRequired').removeAttr('checked');
	$("#selectOptionId").val('');
	$("#placeHolder").val(''); 
	$("#status").val('');
	$("#description").val('');
	$('#addITChallanSectionItemBtn').find('p.jquery_form_error_message').remove();
	$('#fieldtype').val('textfield');
	$('#fieldtype').trigger('chosen:updated');
	getSelectedFieldDetails();
	
	
}

function addChallaItem(sectionId, sectionName){
	$(".addSectionChallanItemPopup").data("sectionId", sectionId);
	$(".addSectionChallanItemPopup").find("h4").html(sectionName);
	$("#taxChallanId").val(sectionId);
	$(".comMask, .addSectionChallanItemPopup").fadeIn();
}

function editItemDetails(itemRowId){
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/form/editChallanItem?&itemRowId="+itemRowId,
		success : function(data) {
			$("#itChallanCustomPopup").empty();
			$("#itChallanCustomPopup").append(data);
		}
	})
	
}

function changeChallanItemStatus(itemId){
       $.ajax({
           type : "POST",
           url : sourceleadUrl+"/form/changeChallanItemStatus?&itemId="+itemId,
           success : function(response) {
        	   
        	   if (response.response === "AWKWARD") {
           	    location.href = "/errorPage";
           	} else if (response.response === "SUCCESS") {
   				$(".success_pop").css("display","block");
           		$(".success_pop span").html("Updated successfully");
           		setTimeout(function() {
           		    location.reload();
		 		},100);
           	} else if (response.response === "INVALID_DATA" || response.response === "FAILED") {
           		showError(response.responseMessage);
           	} else if (response.indexOf('Sign In') !== -1) {
           		location.replace(sourceleadUrl);
           	} else {
           	    showError("Operation failed, please try again");
           	}
        	
   		}
   })
	
}


function changeChallanSectionStatus(sectionId){
	$.ajax({
		 type : "POST",
		 url : sourceleadUrl+"/form/changeChallanSectionStatus?&sectionId="+sectionId,
         success : function(response) {
        	 
        	   if (response.response === "AWKWARD") {
              	    location.href = "/errorPage";
              	} else if (response.response === "SUCCESS") {
      				$(".success_pop").css("display","block");
              		$(".success_pop span").html("Updated successfully");
              		setTimeout(function() {
              		    location.reload();
   		 		  },100);
              	} else if (response.response === "INVALID_DATA" || response.response === "FAILED") {
              		showError(response.responseMessage);
              	} else if (response.indexOf('Sign In') !== -1) {
              		location.replace(sourceleadUrl);
              	} else {
              	    showError("Operation failed, please try again");
              	}
           	
      		}
	})
	
	
	
	
}
