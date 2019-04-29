/**
 * @author Mahaveer Manoj
 */	
function getOutstandingLoans() {
	$(".cardModel-widget").removeClass("currentActive");
	$("#outstandingWidget").addClass("currentActive");

	$(".loan_and_advance_table").hide();
	$("#outStadingTable").show();
}
function getCompletedLoans() {
	$(".cardModel-widget").removeClass("currentActive");
	$("#completedWidget").addClass("currentActive");

	$(".loan_and_advance_table").hide();
	$("#completedTable").show();
}
function getRequestedLoans() {
	$(".cardModel-widget").removeClass("currentActive");
	$("#requestWidget").addClass("currentActive");

	$(".loan_and_advance_table").hide();
	$("#requestTable").show();
}
function requestLoanPop() {
	$(".requestPop, .sideMask").fadeIn();
}
function closeRequestLoanPop() {
	$(".requestPop, .sideMask").fadeOut();
}
function getFormSubmissionMasterList() {
	$.post(sourceleadUrl + "/forms/all", {financialYear: $("#financialYear").val()}, function(response) {
		if (response && response.response === "INVALID_DATA") {
			showError(response.responseMessage);
		} else if (response.response === "AWKWARD") {
			location.href = "/errorPage";
		} else if (response.response === "SUCCESS") {
			$("#submissionList").empty();
			var submissionList = response.data;
			if(submissionList.length > 0) {
				for(var e in submissionList) {
					var text = "<tr>";
					text += "	<td><div class='tblminwidth-150'><span class='mr'>" + submissionList[e].financialYear + "</span></div></td>";
					text += "	<td><div class='tblminwidth-150 clearfix'>" + submissionList[e].formType + "</div></td>";
					text += "	<td><span class='tblminwidth-100'>" + submissionList[e].fillingDate + "</span></td>";
					text += "	<td><div class='tblminwidth-150 clearfix'>" + submissionList[e].statusText + "</div></td>";
					text += "	<td>";
					text += "	<div class='tblminwidth-75'>";
					text += "		<ul class='list-unstyled list-inline mb0'>";
					text += "			<li><a href='javascript:void()' class='text-danger' title='Export' onclick='exportFormToPDF(" + submissionList[e].id + ")'><i class='fa fa-file-pdf-o'></i></a></li>";
					text += "			<li><a href=" + sourceleadUrl + "/forms/viewForm?uuid=" + submissionList[e].taxFormUUID + "&formUUID=" + submissionList[e].formUUID + " title='View' class='anchor-black'><i class='fa fa-eye'></i></a></li>";
					if(submissionList[e].status === 0) {
						text += "				<li><a href=" + sourceleadUrl + "/forms/editForm?uuid=" + submissionList[e].taxFormUUID + "&formUUID=" + submissionList[e].formUUID + " title='Edit' class='anchor-black'><i class='fa fa-pencil'></i></a></li>";	
					}
					text += "		</ul>";
					text += "	</div>";
					text += "	</td>";
					text += "</tr>";
					$("#submissionList").append(text);
				}
				var paginator = response.paginator;
				var options = {
						currentPage : paginator.pageNo + 1,
						items : paginator.totalRows,
						itemsOnPage : paginator.pageSize,
						ellipsePageSet : false,
						cssStyle : 'light-theme',
						onPageClick : function(pageNumber, event) {
							getItDeclarationSubmissionList(pageNumber-1);
						}
				};
				/*$("#pagination-demo").pagination(options);
                $("#pageMsg").html("Showing " + ((paginator.pageNo * paginator.pageSize) + 1) + " to " + (((paginator.pageNo + 1) * paginator.pageSize) > paginator.totalRows ? paginator.totalRows : ((paginator.pageNo + 1) * paginator.pageSize)) + " of " + paginator.totalRows + " results");*/
                payrollFormListTablePagination();
                $("#submissionTable").show();
                /*$("#paginationDiv").show();*/
                $("#noResultsTable").hide();
            } else {
                $("#submissionTable").hide();
                /*$("#paginationDiv").hide();*/
                $("#noResultsTable").show();
            }
		} else if (response.indexOf('Sign In') !== -1) {
			location.replace(sourceleadUrl);
		} else {
			showError("Operation failed, please try again");
		}
	});
}

function payrollFormListTablePagination(){ 
    $('#submissionTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#submissionTable").wrap("<div class='overflow_Auto  width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}

function exportFormToPDF(formId) {
	/*alert("Form Id " + formId);*/
	var exportType = "pdf";
	window.location.href = sourceleadUrl+"/itDeclartionSubmissionExport?formId="+formId+"&exportType="+exportType;
}