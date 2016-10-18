<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidatelist.jsp function  line 264
     2.parameter : candidateId .
--%>
<script>
	function candidateExperience(candidateId) {

		var result = "";
		result = result	+ '    <div class="project_tabs_inn_head addUser-org">  ';
		if(window.candidateStatusGlobal == '0'){
		result = result	+ '   	<a href="javascript:void(0)" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Employer</a>                                                            ';
		}else{
			result = result	+ '   	<a href="javascript:void(0)"  onClick="addExperience('+ candidateId+ ');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Employer</a>                                                            ';	
		}
		result = result	+ '     <div id="popUpDivEmp" class="edit-details col-md-4"> ';
		result = result + '     </div>  ';
		result = result	+ '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result = result + '   <div id="candidateEmployersList" ></div>';
		result = result + '   </div>';

		$("#tab-1").empty();
		$("#tab-2").empty();
		$("#tab-3").empty();
		$("#tab-4").empty();
		$("#tab-5").empty();
		$("#tab-6").empty();
		$("#tab-3").append(result);

		candidateEmployersList(candidateId);

	}
</script>

<script>
function closeCertificatePopup(){
	 $("#popUpDivEmp").removeClass("edit-details-active");
}
</script>

<%-- 
     @author : David Sammeta.
     this script is to Store  candidateEmployer.
     1.usage     :  used in addExperience function  line 187
     2.parameter : candidateId,csrf.token
     3.ajax url implementation in CandidateEmployersRestController.
--%>
<script type="text/javascript">
	function candidateEmployersStore(candidateId) {
		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#employers_form').validate(false, validationSettings)) {
			//To disable addButton 
			$("#candidateEmployersAdd").attr("disabled", "disabled");
			var frm = $('#employers_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			//It will escape all the special characters
			var jsonData = encodeURIComponent(con);
			var sourceleadUrl = '${sourceleadUrl}';

			$
					.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/storeCandidateEmployers?${_csrf.parameterName}=${_csrf.token}&json="
								+ jsonData + "&candidateId=" + candidateId,
						success : function(data) {

							if (data != null && data != 'undefined'
									&& data != "") {
								if (data.indexOf('Sign In') >= 0) {
									location.replace(sourceleadUrl);
								} else if (data.indexOf('exception') >= 0) {
									location.replace(sourceleadUrl  + "/errorPage");
								} else {
									$("#popUpDivEmp").removeClass(
											"edit-details-active");
									candidateEmployersList(candidateId);
								}
							}

						},
					});
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#employers_form', function() {
		$("#employers_form").showHelpOnFocus();
		$("#employers_form").validateOnBlur(false, validationSettings);
	});
</script>



<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in candidateExperience  function  line 17
     2.parameter : candidateId .
--%>

<script>
	function addExperience(candidateId) {

		var result = "";
		result = result + '   <!-- Popup Div --> ';
		result = result	+ '	         <form id="employers_form" name="employers_form" method="post">';
		result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
		result = result	+ '	             <div class="form-horizontal clearfix">';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="col-md-12">';
		result = result	+ '	                  <input type="text" class="form-control"  placeholder="Employer *" name="name" data-validation="required validate_AlphaNumber validate_length length2-100" field-name="Employer Name" value=""> <span class="hidden">Employer Name</span>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="clearfix checkbox">';
		result = result + '	                   <label>';
		result = result	+ '	                  <label> <input type="checkbox" id="isCurrentPosition" value="Y" onClick ="iscurrentPosition();"name="isCurrentPosition"> Is Current Position</label>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="col-md-12">';
		result = result	+ '	                    <input type="text" class="form-control"  placeholder="Employer Designation" name="designation" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" field-name="Employer Designation" value=""> <span class="hidden">Employer Designation</span>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <div class="col-md-12">';
		result = result + '	                  <input type="text" class="form-control input-sm" placeholder="Address" name="address" data-validation="required validate_Space validate_length length2-250"  data-validation-optional="true" field-name="Address" value=""> <span 	class="hidden">Address</span>';
		result = result + '	                 </div>';
		result = result + '	               </div>';
		result = result + '	               <div class="clearfix mb10">';
		result = result + '	                 <p class="ml">Start Date</p>';
		result = result + '	                 <div class="col-md-6">';
		result = result	+ '	                   <select class="chosen-select"  name="startMonth" id="startMonth" onChange="validYear();">';
		result = result	+ '	                       <option value="Select Month ">Select Month</option>';
		result = result	+ ' 					   <option value="1&January">January</option>';
		result = result	+ '	                       <option value="2&February">February</option>';
		result = result	+ '	                       <option value="3&March">March</option>';
		result = result	+ '	                       <option value="4&April">April</option>';
		result = result	+ '	                       <option value="5&May">May</option>';
		result = result + '	                       <option value="6&June">June</option> ';
		result = result + '	                       <option value="7&July">July</option>';
		result = result	+ '	                       <option value="8&August">August</option>';
		result = result	+ '	                       <option value="9&September">September</option>';
		result = result	+ '	                       <option value="10&October">October</option>';
		result = result	+ '	                       <option value="11&November">November</option>';
		result = result	+ '	                       <option value="12&December">December</option>';
		result = result + '	                   </select>';
		result = result + '	                   </div>';
		result = result + '	                   <div class="col-md-6" id="startYearDiv">';
		result = result	+ '	                   <input type="text" max class="form-control" value="" id="startYear" name="startYear" onblur="validYear();"  placeholder="Enter Year"  field-name="Start Date" data-validation="validate_Space validate_int validate_length length4" data-validation-optional="true"  >';
		result = result + '	                 </div>';
		result = result + '	                 </div>';
		result = result + '	                 <div id="endDateDiv">';
		result = result + '	                 <div class="clearfix mb10 endDate">';
		result = result + '	                 <p class="ml">End Date</p>';
		result = result	+ '	                 <div class="col-md-6 id = "endMR"">';
		result = result	+ '	                    <select class="chosen-select" class="required" name="endMonth" id="endMonth" onChange="validYear();">';
		result = result	+ '	                       <option value="Select Month">Select Month</option>';
		result = result	+ '	                       <option value="1&January">January</option>';
		result = result	+ '	                       <option value="2&February">February</option>';
		result = result	+ '	                       <option value="3&March">March</option>';
		result = result	+ '	                       <option value="4&April">April</option>';
		result = result	+ '	                       <option value="5&May">May</option>';
		result = result + '	                       <option value="6&June">June</option> ';
		result = result + '	                       <option value="7&July">July</option>';
		result = result	+ '	                       <option value="8&August">August</option>';
		result = result	+ '	                       <option value="9&September">September</option>';
		result = result	+ '	                       <option value="10&October">October</option>';
		result = result	+ '	                       <option value="11&November">November</option>';
		result = result	+ '	                       <option value="12&December">December</option>';
		result = result + '	                   </select>';
		result = result + '	                   </div>';
		result = result + '	                   <div class="col-md-6" >';
		result = result	+ '	                   <input type="text" max class="form-control" id="endYear" name="endYear" field-name="End Year" onblur="validYear();" placeholder="Enter Year" data-validation="validate_Space validate_int validate_length length4" data-validation-optional="true" >';
		result = result + '	                 </div>';
		result = result + '	                 </div> ';
		result = result + '	                 </div> ';
		result = result + '	               <div class="clearfix mr">';
		result = result	+ '	                 <button type="button" id ="candidateEmployersAdd" onClick ="candidateEmployersStore('+ candidateId+ ');"class="btn btn-sm btn-primary pull-right">Add</button>';
		result = result + '	               </div>';
		result = result + '	             </div>';
		result = result + '	        </form> ';
		$("#popUpDivEmp").empty();
		$("#popUpDivEmp").append(result);

		$(".edit-details").toggleClass("edit-details-active");
		$(".close-div").click(
				function() {
					$(this).parent("div.edit-details").removeClass(
							"edit-details-active");
				});
		$(
				".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details")
				.click(function(e) {
					e.stopPropagation();
				});
		$('html').click(function() {
			$(".edit-details").removeClass("edit-details-active");
		});

	}
</script>

<script>

	function editEmployer(candidateEmpId, candidateId) {

		
		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/getCandidateEmployerByCandidateIdAndCandidateEmpoyerId?${_csrf.parameterName}=${_csrf.token}&candidateId="
							+ candidateId + "&candidateEmpId=" + candidateEmpId,
					success : function(data) {

						var json = JSON.parse(data);

						if (data != null && data != 'undefined' && data != "") {

							if (data.indexOf('Sign In') >= 0) {
								location.replace(sourceleadUrl);
							} else if (data.indexOf('exception') >= 0) {
								location.replace(sourceleadUrl + "/errorPage");
							} else {

								var result = "";
								result = result + '      <!-- Popup Div --> ';
								result = result	+ '         <form id="candidateExperianceEdit_form" name="candidateExperianceEdit_form" method="post">';
								result = result+'	           <i class="fa fa-close close-div" onClick="closeCertificatePopup();"></i>';
								result = result	+ '	             <div class="form-horizontal clearfix">';
								result = result + '	               <div class="clearfix mb10">';
								result = result + '	                 <div class="col-md-12">';
								result = result	+ '	                  <input type="text" class="form-control"  placeholder="Employer *" name="name" data-validation="required validate_AlphaNumber validate_length length2-100" field-name="Employer Name" value="'+json.name+'"> <span class="hidden">Employer Name</span>';
								result = result + '	                 </div>';
								result = result + '	               </div>';
								result = result	+ '	               <div class="clearfix mb10">';
								result = result	+ '	                 <div class="col-md-12 checkbox">';
								result = result + '	                   <label>';
								if (json.isCurrentPosition == 'Y' && json.isCurrentPosition != '' && json.isCurrentPosition !== 'undefined') {
									result = result	+ '	 <label> <input type="checkbox" id="isCurrentPositionEdit"  value="'+ json.isCurrentPosition+ '"   checked="checked"    onClick ="iscurrentPositionEdit();" name = "isCurrentPosition"> Is Current Position</label>';
								} else {
									result = result	+ '	     <label> <input type="checkbox" id="isCurrentPositionEdit"  value="'+ json.isCurrentPosition+ '" onClick ="iscurrentPositionEdit();" name= "isCurrentPosition"> Is Current Position</label>';
								}
								result = result + '	                 </div>';
								result = result + '	               </div>';
								result = result	+ '	               <div class="clearfix mb10">';
								result = result	+ '	                 <div class="col-md-12">';
								result = result + '	                    <input type="text" class="form-control"  placeholder="Employer Designation" name="designation"  value="'+json.designation+'" data-validation="required validate_Space validate_length length2-100" data-validation-optional="true" field-name="Employer Designation" > <span class="hidden">Employer Designation</span>';
								result = result + '	                 </div>';
								result = result + '	               </div>';
								result = result	+ '	               <div class="clearfix mb10">';
								result = result	+ '	                 <div class="col-md-12">';
								result = result	+ '	                  <input type="text" class="form-control input-sm" placeholder="Address" name="address" data-validation="required validate_Space validate_length length2-250"  data-validation-optional="true" field-name="Address" value="'+json.address+'"> <span 	class="hidden">Address</span>';
								result = result + '	                 </div>';
								result = result + '	               </div>';
								result = result	+ '	               <div class="clearfix mb10">';
								result = result + '	                 <p class="ml">Start Date</p>';
								result = result	+ '	                 <div class="col-md-6">';
								result = result	+ '	                   <select class="chosen-select"  value="'+json.startMonth+'" name="startMonth" id="startMonth">';
								result = result	+ '	                       <option value="Select Month ">Select Month</option>';
								if (json.startMonth == "January") {
									result = result + '<option selected>January</option>';
								} else {
									result = result	+ '<option>January</option>';
								}
								if (json.startMonth == "February") {
									result = result	+ '<option selected>February</option>';
								} else {
									result = result	+ '<option>February</option>';
								}
								if (json.startMonth == "March") {
									result = result	+ '<option selected>March</option>';
								} else {
									result = result + '<option>March</option>';
								}
								if (json.startMonth == "April") {
									result = result	+ '<option selected>April</option>';
								} else {
									result = result + '<option>April</option>';
								}
								if (json.startMonth == "May") {
									result = result	+ '<option selected>May</option>';
								} else {
									result = result + '<option>May</option>';
								}
								if (json.startMonth == "June") {
									result = result	+ '<option selected>June</option>';
								} else {
									result = result + '<option>June</option>';
								}
								if (json.startMonth == "July") {
									result = result	+ '<option selected>July</option>';
								} else {
									result = result + '<option>July</option>';
								}
								if (json.startMonth == "August") {
									result = result + '<option selected>August</option>';
								} else {
									result = result + '<option>August</option>';
								}
								if (json.startMonth == "September") {
									result = result	+ '<option selected>September</option>';
								} else {
									result = result	+ '<option>September</option>';
								}
								if (json.startMonth == "October") {
									result = result + '<option selected>October</option>';
								} else {
									result = result + '<option>October</option>';
								}
								if (json.startMonth == "November") {
									result = result + '<option selected>November</option>';
								} else {
									result = result	+ '<option>November</option>';
								}
								if (json.startMonth == "December") {
									result = result	+ '<option selected>December</option>';
								} else {
									result = result	+ '<option>December</option>';
								}
								result = result	+'</select>';
								result = result + '</div>';
								result = result	+ '<div class="col-md-6">';
								result = result	+ '<input type="text" max class="form-control" value="'+ json.startYear+ '" id="startYear" name="startYear" onclick="validYear();"  placeholder="Enter Year"  field-name="Start Date" data-validation="required" data-validation-optional="true" >';
								result = result + '</div>';
								result = result + '</div>';
								
								result = result	+ '<div id="endDateEditDiv">';
								result = result	+ '<div class="clearfix mb10 endDate >';
								result = result	+ '<p class="ml">End Date</p>';
								result = result	+ '<div class="col-md-6 id = "endMR"">';
								result = result	+ '<select class="chosen-select" selected  class="required" name="endMonth" id="endMonthEdit">';
								result = result	+ '<option value="Select Month">Select Month</option>';
								if (json.endMonth == "January") {
									result = result
											+ '<option selected>January</option>';
								} else {
									result = result
											+ '<option>January</option>';
								}
								if (json.endMonth == "February") {
									result = result
											+ '<option selected>February</option>';
								} else {
									result = result
											+ '<option>February</option>';
								}
								if (json.endMonth == "March") {
									result = result
											+ '<option selected>March</option>';
								} else {
									result = result
											+ '<option>March</option>';
								}
								if (json.endMonth == "April") {
									result = result
											+ '<option selected>April</option>';
								} else {
									result = result
											+ '<option>April</option>';
								}
								if (json.endMonth == "May") {
									result = result
											+ '<option selected>May</option>';
								} else {
									result = result
											+ '<option>May</option>';
								}
								if (json.endMonth == "June") {
									result = result
											+ '<option selected>June</option>';
								} else {
									result = result
											+ '<option>June</option>';
								}
								if (json.endMonth == "July") {
									result = result
											+ '<option selected>July</option>';
								} else {
									result = result
											+ '<option>July</option>';
								}
								if (json.endMonth == "August") {
									result = result
											+ '<option selected>August</option>';
								} else {
									result = result
											+ '<option>August</option>';
								}
								if (json.endMonth == "September") {
									result = result
											+ '<option selected>September</option>';
								} else {
									result = result
											+ '<option>September</option>';
								}
								if (json.endMonth == "October") {
									result = result
											+ '<option selected>October</option>';
								} else {
									result = result
											+ '<option>October</option>';
								}
								if (json.endMonth == "November") {
									result = result
											+ '<option selected>November</option>';
								} else {
									result = result
											+ '<option>November</option>';
								}
								if (json.endMonth == "December") {
									result = result
											+ '<option selected>December</option>';
								} else {
									result = result
											+ '<option>December</option>';
								}
								
								result = result	+ '	                   </select>';
								result = result	+ '	                   </div>';
								result = result	+ '	                   <div class="col-md-6" >';
								if (json.endYear != '' && json.endYear != 'undefined') {
									
								result = result + '	                   <input type="text" max class="form-control" value="'+json.endYear+'" id="endYearEdit" name="endYear" field-name="End Year"  placeholder="Enter Year" data-validation="validate_Space validate_int validate_length length4" data-validation-optional="true" >';
								}else{
								
									result = result + '	             <input type="text" max class="form-control" value = "" id="endYearEdit" name="endYear" field-name="End Year"  placeholder="Enter Year" data-validation="validate_Space validate_int validate_length length4" data-validation-optional="true" >';

								}
								result = result	+ '	                 </div>';
						
								result = result + '	                 </div> ';
								result = result + '	                 </div> ';
								result = result	+ '	               <div class="clearfix mr">';
								result = result	+ '	                   <input type="hidden"  value="'+candidateEmpId+'" id="candidateEmpId"  name="candidateEmployeeId" >';
								result = result	+ '	                   <input type="hidden"  value="'+candidateId+'" id="candidateId" >';
								result = result + '	                 <button type="button" id ="candidateEmployersAdd" onClick ="updateCandidateEmployers('+ candidateId+ ','+ candidateEmpId+ ');"class="btn btn-sm btn-primary pull-right">Add</button>';
								result = result + '	               </div>';
								result = result + '	             </div>';
								result = result + '	        </form> ';
								$("#popUpDivEmp").empty();
								$("#popUpDivEmp").append(result);
								iscurrentPositionEdit();

								$(".edit-details").toggleClass("edit-details-active");
								$(".close-div")	.click(function() {
													$(this).parent("div.edit-details")
															.removeClass("edit-details-active");
												});
								$(".addUser-org, .assign-org, .collapse-div-adduser, .collapse-div, .edit-details")
										.click(function(e) {
											e.stopPropagation();
										});
								$('html').click(function() {
											$(".edit-details").removeClass(
													"edit-details-active");
										});
								
							}
						}
					},

				});

	}
</script>

<%-- 
     @author : David Sammeta.
     this script is for serializeFormJSON.
     1.usage     :  used in candidateEmployersStore function  line 57
--%>
<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>

<%-- 
     @author : David Sammeta.
     this script is getting candidateEmployers List .
     1.usage     :  used in candidateExperience  function  line 34
     2.parameter : candidateId ,csrf.token.
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
	function candidateEmployersList(candidateId) {
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
								$("#candidateEmployersList").empty();
								var result = "";
								result = result	+ "<table id='candidateCertificatePagination' class='display' cellspacing='0' width='100%' >";
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
										result = result	+ '<td><div><span>--</span></div></td>';
									}
									if (opt.address != null && opt.address	&& opt.address != 'undefined') {
										result = result + '<td><div><span>'								+ opt.address
												+ '</span></div></td>';
									} else {
										result = result	+ '<td><div><span>--</span></div></td>';
									}

									if (opt.startYear != null && opt.startYear != '' && opt.startYear != 'undefined') {
										result = result + '<td><div><span>'	+ opt.startMonth + ' -'	+ opt.startYear	+ '</span></div></td>';
									} else {
										result = result		+ '<td><div><span>--</span></div></td>';
									}

									if (opt.endYear != null	&& opt.endYear != '' && opt.endYear != 'undefined') {
										result = result + ' <td><div><span>'+ opt.endMonth + '-'+ opt.endYear+ '</span></div></td>';
									} else {
										result = result	+ '<td><div><span>--</span></div></td>';
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
								$("#candidateEmployersList").append(result);
								pagination();
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
							$("#candidateEmployersList").empty();
							$("#candidateEmployersList").append(result);
						}
					},

				});
	}
</script>


<%-- 
     @author : David Sammeta.
     this script is getting removing Employer in the  List .
     1.usage     :  used in candidateEmployersList  function  line 286
     2.parameter : candidateId ,csrf.token,candidateemployerId
     3.ajax url implementation in CandidateListRestController.
--%>
<script>
	function removeEmployer(candidateemployerId, candidateId) {

		var sourceleadUrl = '${sourceleadUrl}';
		
				$.ajax({
					type : "POST",
					url : sourceleadUrl	+ "/removeCandidateEmployerBycandidateIdandCandidateEmployerID?${_csrf.parameterName}=${_csrf.token}&candidateId="+ candidateId + "&candidateemployerId="+candidateemployerId,
					success : function(data) {

						if (data != null && data != 'undefined' && data != "") {
							if (data.indexOf('Sign In') >= 0) {
								location.replace(sourceleadUrl);
							} else if (data.indexOf('exception') >= 0) {
								location.replace(sourceleadUrl + "/errorPage");
							} else {
								$("#popUpDivEmp").removeClass(
										"edit-details-active");
								candidateEmployersList(candidateId);
							}
						}
					},

				});
	}
</script>

<%-- 
     @author : David Sammeta.
     this script is for upDate Candidate Details .
     1.usage     :  used in editCandidate() function  line 78
     2.parameter : csrf token .
     3.ajax url implementation in CandidateListRestController.
--%>
<script type="text/javascript">
	function updateCandidateEmployers(candidateId, candidateExperianceId) {

		var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
		if ($('#candidateExperianceEdit_form').validate(false,
				validationSettings)) {

			var frm = $('#candidateExperianceEdit_form').serializeFormJSON();

			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
			var jsonData = encodeURIComponent(con);
			//It will escape all the special characters
			var sourceleadUrl = '${sourceleadUrl}';

			$.ajax({
						type : "POST",
						url : sourceleadUrl
								+ "/upDateCandidateEmployerByCandidateIdAndCandidateExperianceId?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData + "&candidateId=" + candidateId	+ "&candidateExperianceId"+ candidateExperianceId,
						success : function(result) {
							candidateEmployersList(candidateId)
						},
					});
			return true;
		} else {
			return false;
		}
		$('body').on('blur','#candidateExperianceEdit_form',
				function() {
					$("#candidateExperianceEdit_form").showHelpOnFocus();
					$("#candidateExperianceEdit_form").validateOnBlur(false,
							validationSettings);
				});
	}
</script>


<%-- 
     @author : David Sammeta.
     this script is for onclick function iscurrentPosition  addExperience  .
     1.usage     :  used in addExperience  function  line 124
--%>
<script>
	function iscurrentPosition() {

		var currentposition = document.getElementById("isCurrentPosition").checked;
			 
		if (currentposition) {
			document.getElementById("endDateDiv").style.display = 'none';
			$('#endYear').removeAttr('data-validation');
			$('#endMonth').removeAttr('data-validation');
		} else if (document.getElementById("endDateDiv").style.display == 'none') {
			
			document.getElementById("endDateDiv").style.display = 'block';
			$("#endYear").attr("data-validation", "required");
			$("#endMonth").attr("data-validation", "required");
		}

	}
</script>

<%-- 
     @author : David Sammeta.
     this script is for onclick function   iscurrentPositionEdit  .
     1.usage     :  used in editEmployer  function  line 248
--%>
<script>
	function iscurrentPositionEdit() {

		var currentposition = document.getElementById("isCurrentPositionEdit").checked;
		
		if (currentposition) {
			document.getElementById("endDateEditDiv").style.display = 'none';
			$('#endYearEdit').removeAttr('data-validation');
			$('#endMonthEdit').removeAttr('data-validation');
		}else {
			$( "#endYearEdit" ).val( '' );
			document.getElementById("endDateEditDiv").style.display = '';
			$("#endYearEdit").attr("data-validation", "required");
			$("#endMonthEdit").attr("data-validation", "required");
		}

	}
</script>

<script>
function validYear(){
	
	var startYear= document.getElementById("startYear").value;
	var startMonth= document.getElementById("startMonth").value;
	
	var endYear= document.getElementById("endYear").value;
	var endMonth= document.getElementById("endMonth").value;
	
	var SMdata = startMonth.split('&');
	var sMonthNumber = SMdata[0];
	
	var EMdata = endMonth.split('&');
	var eMonthNumber = EMdata[0];
	
	 if(startYear == endYear && sMonthNumber > eMonthNumber){
		$("input#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Month Should be less then end Month</span></p>");
		
	}else if((startYear > endYear )&&(endYear != '' && endYear!=null && endYear!='undefined')){
		$("input#startYear").parent().after("<p class='jquery_form_error_message'><span>Start Date Should be less then end Date</span></p>");
	}else{
		$('#startYearDiv').parent().find('p.jquery_form_error_message').remove(); 
	}
}

</script>