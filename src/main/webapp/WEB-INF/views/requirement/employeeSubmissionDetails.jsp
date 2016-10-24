
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="resources/js/jquery-1.11.3.min.js"></script> 
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script>
	function employeeSubmissionDetails(candidateId){
		var result = "";
		
		result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result = result	+ '<div class="project_tabs_inn_head"> <input class="vendorfild" ></div>';
		result = result + '   <div id="employeeSubmissionDetailsList" ></div>';
		result = result + '   </div>';
		
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
		var sourceleadUrl = '${sourceleadUrl}';
		
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/getCandidatesEmployersListByCandidateIdAndOrgLocationID?${_csrf.parameterName}=${_csrf.token}&candidateId="
							+ candidateId,
					success : function(data) {
						if (data != null && data != 'undefined' && data != "" && data != '[]') {
							if (data.indexOf('Sign In') >= 0) {
								location.replace(sourceleadUrl);
							} else if (data.indexOf('exception') >= 0) {
								location.replace(sourceleadUrl + "/errorPage");
							} else {
								$("#employeeSubmissionDetailsList").empty();
								var result = "";
								result = result	+ "<table id='employersSubmittionPagination' class='display' cellspacing='0' width='100%' >";
								result = result + "<thead>";
								result = result + "  <tr>";
								result = result	+ '           <th>Employer</th>';
								result = result	+ '           <th>Designation</th>';
								result = result + '           <th>Address</th>';
								result = result	+ '           <th>Start date</th>';
								result = result	+ '           <th>End date</th>';
								result = result + '           <th>Action</th>';
								result = result + "  </tr>";
								result = result + "</thead>";
								result = result + "<tbody>";
								var json = JSON.parse(data);
								for (var i = 0; i < json.length; i++) {
									var opt = json[i];
									result = result + '         <tr>';
									if (opt.name != null && opt.name != ''
											&& opt.name != 'undefined') {
										result = result + '<td><div><span>'+ opt.name+ '</span></div></td>';
									} else {
										result = result+ '<td><div><span>--</span></div></td>';
									}
									if (opt.designation != null	&& opt.designation && opt.designation != 'undefined') {
										result = result + '<td><div><span>'+ opt.designation+ '</span></div></td>';
									} else {
										result = result	+ '<td><div><span>----</span></div></td>';
									}
									if (opt.address != null && opt.address	&& opt.address != 'undefined') {
										result = result + '<td><div><span>'								+ opt.address
												+ '</span></div></td>';
									} else {
										result = result	+ '<td><div><span>----</span></div></td>';
									}

									if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
										result = result + '<td><div><span>'	+ opt.startMonth + ' -'	+ opt.startYear	+ '</span></div></td>';
									} else {
										result = result		+ '<td><div><span>----</span></div></td>';
									}

									if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
										result = result + ' <td><div><span>'+ opt.endMonth + '-'+ opt.endYear+ '</span></div></td>';
									} else {
										result = result	+ '<td><div><span>----</span></div></td>';
									}
									if(window.candidateStatusGlobal == '0'){
				                   result = result+'<td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)"></a>';
									}else{
										result = result+'<td><div class="tblicons"><a class="glyphicon glyphicon-pencil" href="javascript:void(0)" onClick="editEmployer('+ opt.id+ ','+ candidateId+ ');"></a>';	
									}if(window.candidateStatusGlobal == '0'){
										result = result+'<i class="glyphicon glyphicon-trash"></i></div></td>';
									}else{
										result = result+'<i class="glyphicon glyphicon-trash" onclick="removeEmployer('+ opt.id + ',' + candidateId+ ');"></i></div></td>';
									}
									result = result + '</tr>';
								}
								result = result + " </tbody>";
								result = result + "</table>";
								$("#employeeSubmissionDetailsList").append(result);
								employeeSubmittionPagination();
							}
						} else {
							var result = "";
							result = result + "   <div class='row'>";
							result = result + "	<div class='col-lg-12'>";
							result = result
									+ "		<div class='content-area clearfix'>";
							result = result + "			<div class='blank-pages'>";
							result = result
									+ "			        <p><i class='fa fa-5x fa-bar-chart'></i></p>";
							result = result
									+ "			        <p>No CandidatesCertifications Found</p>";
							result = result + "			 </div>";
							result = result + "		</div>";
							result = result + "	</div>";
							result = result + "  </div> ";
							$("#employeeSubmissionDetailsList").empty();
							$("#employeeSubmissionDetailsList").append(result);
						}
					},

				});
	}
</script>

<script>
function employeeSubmittionPagination(){  
    $('#employersSubmittionPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
 
 }
</script>

