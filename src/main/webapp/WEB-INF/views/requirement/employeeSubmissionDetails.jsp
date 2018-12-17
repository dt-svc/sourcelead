<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<script>
	function employeeSubmissionDetails(candidateId){
		var result = "";
		
		result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">';
		/* result = result	+ '<div class="project_tabs_inn_head"> <input class="vendorfild" ></div>'; */
		result = result + '   <div id="employeeSubmissionDetailsList" class="datatable_filter mt5"></div>';
		result = result + '   </div>';
		result = result + '<div class="clearfix"></div>';
		
		$("#tabb-1").empty();
		$("#tabb-2").empty();
		$("#tabb-3").empty();
		$("#tabb-4").empty();
		$("#tabb-5").empty();
		$("#tabb-6").empty();
		$("#tabb-4").append(result);
		
		employeeSubmissionDetailsList(candidateId);
	}
</script>



<script>
	function employeeSubmissionDetailsList(candidateId) {
		
		 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  empSubListDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  empSubListDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  empSubListDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  empSubListDate = 'YYYY-DD-MM'; 
		  }  
		
		
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?"+tokenParamName+"="+tokenValue+"&candidateId="
							+ candidateId,
					success : function(response) {
						if (response && response.response === "INVALID_DATA") {
						    showError(response.responseMessage);
						} else if (response && response.response === "INVALID_USER") {
						    showError(response.responseMessage);
						} else if (response.response === "AWKWARD") {
						    location.href = "/errorPage";
						} else if (response.response === "SUCCESS") {
							$("#employeeSubmissionDetailsList").empty();
							var result = "";
							result = result	+ "<table id='employersSubmittionPagination' class='display' cellspacing='0' width='100%' >";
							result = result + "<thead>";
							result = result + "  <tr>";
							result = result	+ '           <th>Employer</th>';
							result = result	+ '           <th>Designation</th>';
							result = result + '           <th>Address</th>';
							result = result	+ '           <th>Start date <br>(<small class="custom-small">'+empSubListDate+'</small>)</th>';
							result = result	+ '           <th>End date <br>(<small class="custom-small">'+empSubListDate+'</small>)</th>';
							result = result + "  </tr>";
							result = result + "</thead>";
							result = result + "<tbody>";
//								var json = JSON.parse(data);
							for (var i = 0; i < response.data.length; i++) {
								var opt = response.data[i];
								result = result + '         <tr>';
								if (opt.name != null && opt.name != ''
										&& opt.name != 'undefined') {
									result = result + '<td><div><span title = "'+opt.name+'">'+ opt.name+ '</span></div></td>';
								} else {
									result = result+ '<td><div><span>--</span></div></td>';
								}
								if (opt.designation != null	&& opt.designation && opt.designation != 'undefined') {
									result = result + '<td><div><span title = "'+opt.designation+'">'+ opt.designation+ '</span></div></td>';
								} else {
									result = result	+ '<td><div><span>----</span></div></td>';
								}
								if (opt.address != null && opt.address	&& opt.address != 'undefined') {
									result = result + '<td><div><span title = "'+opt.address+'">'+ opt.address+'</span></div></td>';
								} else {
									result = result	+ '<td><div><span>----</span></div></td>';
								}

								if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
									result = result + '<td><div><span>'+ opt.startYear+'</span></div></td>';
								} else {
									result = result		+ '<td><div><span>----</span></div></td>';
								}

								if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
									result = result + ' <td><div><span>'+opt.endYear+'</span></div></td>';
								} else {
									result = result	+ '<td><div><span>----</span></div></td>';
								}
								result = result + '</tr>';
							}
							result = result + " </tbody>";
							result = result + "</table>";
							$("#employeeSubmissionDetailsList").append(result);
							employeeSubmittionPagination();
						} else if (response.indexOf('Sign In') !== -1) {
							location.replace(sourceleadUrl);
						} else {
						    showError("Operation failed, please try again");
						}
					}
				});
	}
</script>

<script>
function employeeSubmittionPagination(){  
    $('#employersSubmittionPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
 }
</script>

