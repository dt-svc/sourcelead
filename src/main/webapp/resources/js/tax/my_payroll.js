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