
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<script>
	function requirementAssignedMembersList(url, requirementId) {
		var colIndex = 0;
		$('#requirementAssignedToPagination')
				.DataTable(
						{
							"processing" : true,
							"serverSide" : true,
							"searching" : false,
							"ajax" : $.fn.dataTable.pipeline({
								url : url,
								method : 'POST',
								pages : 10
							}),
							"columns" : [ {
								"data" : "name"
							},{
								"data" : "role"
							}, {
								"data" : "team"
							}, {
								"data" : "noOfApplicants"
							}],
							"columnDefs" : [
									{
										"targets" : colIndex++,
										"data" : "name",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.assignedUserName != null
													&& opt.assignedUserName != ''
													&& opt.assignedUserName != 'undefined') {
												result = result
														+ '<td><div class="tbldata-dotdot">'+opt.assignedUserName+'</div></td>';
											} else {
												result = result
														+ '<td><div>-----</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "role",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.assignedUserRole != null
													&& opt.assignedUserRole != ''
													&& opt.assignedUserRole != 'undefined') {
												result = result
												+ '<td><div class="tbldata-dotdot">'+opt.assignedUserRole+'</div></td>';
									} else {
										result = result
												+ '<td><div>-----</div></td>';
									}
									return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "team",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											if (opt.teamName != null
													&& opt.teamName != ''
													&& opt.teamName != 'undefined') {
												result = result
												+ '<td><div class="tbldata-dotdot-vsm">'+opt.teamName+'</div></td>';
											} else {
												result = result
														+ '<td><div>-----</div></td>';
											}
											return result;
										}
									},
									{
										"targets" : colIndex++,
										"data" : "noOfApplicants",
										"render" : function(data, type, opt,
												meta) {
											var result = "";
											result = result + '<td><div class="tbldata-dotdot-vsm">'+opt.applicantCount+'</div></td>';
											return result;
										}
									}],
							"scrollCollapse" : false,
							"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
							"aaSorting" : [ [ 3, 'desc' ] ],
							"bLengthChange" : true,
							"bDestroy" : true,
							"ordering" : false,
							"initComplete": function( settings, json ) {
					    		 $('#assignedToDataTableSearch').removeAttr('disabled');
					    	  },
							"oLanguage" : {
								"sSearch" : "",
								"sZeroRecords" : "",
								"sEmptyTable" : "",
								"sProcessing" : "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
							}
						});
		$("#requirementAssignedToPagination").wrap(
				"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
		$("input[type=search]").hide();
		$("#assignedToDataTableSearch").keyup(function(e) {
			if (e.which === 13) {
				assignedMemberList(requirementId);
			}
		});
	}
</script>