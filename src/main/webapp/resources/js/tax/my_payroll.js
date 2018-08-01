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
	$.post(sourceleadUrl + "/myPayroll/forms/all", {financialYear: $("#financialYear").val()}, function(response) {
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
				$("#pagination-demo").pagination(options);
				$("#pageMsg").html("Showing " + ((paginator.pageNo * paginator.pageSize) + 1) + " to " + (((paginator.pageNo + 1) * paginator.pageSize) > paginator.totalRows ? paginator.totalRows : ((paginator.pageNo + 1) * paginator.pageSize)) + " of " + paginator.totalRows + " results");
			
				$("#submissionTable").show();
				$("#paginationDiv").show();
				$("#noResultsTable").hide();
			} else {
				$("#submissionTable").hide();
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