var tax = new Tax();
$(document).ready(function() {
	if (activeTaxTab === "state") {
		tax.getStateTaxList();
	} else if (activeTaxTab === "central") {
		alert("Central Tab");
	}
});
function Tax() {

	this.getStateTaxList = function() {
		var selectedYear = $("#stateTaxYearList").val();
		var selectedState = $("#stateTaxStateList").val();
		$("#stateList").empty();
		$("#stateListTable").show();
		$("#noResultsTable").hide();
		$.post(sourceleadUrl + "/getStateTaxes", {yearFrequency:selectedYear, stateId:selectedState}, function(response) {
			if (response && response.response === "INVALID_DATA") {
				showError(response.responseMessage);
			} else if (response.response === "AWKWARD") {
				location.href = "/errorPage";
			} else if (response.response === "SUCCESS") {
				var stList = response.data;
				if(stList.length > 0) {
					for(var e in stList){
						var result = "<tr><td>";
						result += "<div>";
						result += "<a href='taxSummary'>"+stList[e].stateName+"</a>";
						result += "</div>";
						result += "</td>";
						result += "<td><div>"+stList[e].taxType+"</div></td>";
						result += "<td>";
						result += "<div>";
						result += "<ul class='list-unstyled list-inline mb0'>";
						result += "<li><a href='javascript:void()' title='Edit' class='anchor-black'><i class='fa fa-pencil'></i></a></li>";
						result += "<li><a href='javascript:void()' title='Delete' class='text-danger'><i class='fa fa-trash'></i></a></li>";
						result += "</ul>";
						result += "</div>";
						result += "</td></tr>";
						$("#stateList").append(result);
					}
				} else {
					$("#stateListTable").hide();
					$("#noResultsTable").show();
				}
			} else if (result.indexOf('Sign In') !== -1) {
				location.replace(sourceleadUrl);
			} else {
				showError("Operation failed, please try again");
			}
		});
	}
}