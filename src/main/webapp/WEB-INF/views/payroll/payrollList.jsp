<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Expenses New</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; Expenses New</div>
</section>

<section class="contrHead">
		<ul>
			<li title=""><a href="#" class="wVer" onclick="loadEmployeePayrollCost();">Employer Payroll Cost</a></li>
			<li title=""><a href="#">Tab 2</a></li>
			<li title=""><a href="#">Tab 3</a></li>
			<li title=""><a href="#">Tab 4</a></li>
			<div class="clearfix"></div>
		</ul>
	</section>
	
<div class="content-area content-new clearfix">
 <div class="project_wrap">
	<div class="project_tabs_inn_head">
	<form:form id="upload_payroll_form" name="upload_payroll_form" method="post">
                  <input class="vendorfild" type="text">
                   <i class="fa fa-search"></i> | <a href="javascript:void(0)" class="addcon-pop"> <i class="glyphicon glyphicon-plus"></i>Add Document</a>
                  
                  <!-- Popup Div -->
                  <div class="collapse-div-adduser col-md-4"> <i class="fa fa-close close-div"></i>
                    <div class="form-horizontal clearfix">
                     
                      <div class="clearfix mb10">
                        <div class="col-md-6"><input type="text" class="form-control calendar" id="fromDate" name="fromDate" value="" placeholder="From Date*"></div>
                        <div class="col-md-6"><input type="text" class="form-control calendar" id="toDate" name="toDate" value="" placeholder="To Date*"></div>
                      </div>                     
                     
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <input type="text" class="form-control" value="" placeholder="Batch Name" id="batchNumber" name="batchNumber">
                        </div>
                      </div>
                      <div class="clearfix mb10">
                        <div class="col-md-12">
                          <select class="chosen-select" id="payrollType" name="payrollType">
                            <option value="">Select Type</option>
                            <option value="EMPLOYEE_PAYROLL">Employee Payroll</option>
                            <option value="WAGE_TAX">Wage & Tax Summary</option>EMPLOYEE_PAYROLL WAGE_TAX
                            <option value="March">Type 3</option>
                            <option value="April">Type 4</option>
                          </select>
                        </div>
                        <div class="col-md-12 mt10">
                          <div class="input-group" id="uploadFileGroup">
	                        <label class="input-group-btn"> <span class="btn btn-primary"><i class="fa fa-folder-open-o"></i>&nbsp;Browse File
	                          <input type="file" name="uploadBtn" id="uploadBtn" style="display: none;"  multiple="" class="upload">
	                            </span> </label>
		                          <input type="text" class="form-control" readonly id="uploadFile" name="docFile" placeholder="Upload .pdf File." field-name="Attachment" data-validation="required validate_Space validatepdf">
	                        </div>
                          
                        </div>
                      </div>
                      <div class="clearfix mr">
                        <button type="button" class="btn btn-sm btn-primary pull-right" id="uploadPayroll" onclick="uploadPayrollDocument();">Upload</button>
                      </div>
                    </div>
                  </div>
                  <!-- Popup Div --> 
                  </form:form>
                </div>
    <div class="project_tabs_inn_table vendor_wrap_inn_table">
    	<div id="employerCostDiv"></div>
       
	</div>
	
<nav>
<ul id="paging" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">3</a></li>
	<li><a href="#">4</a></li>
	<li><a href="#">5</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
	</ul>
	<div class="clearfix"></div>
</nav>	
 </div>
</div>
</section>


<script>
function loadEmployeePayrollCost(){
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/loadEmployerPayrollCostByOrgLocationId?${_csrf.parameterName}=${_csrf.token}",
				        success: function(data) {
						var result = "";				        	
						result = result + '<table>';
						result = result + '<tbody>';
						result = result + '</tbody><thead>';
						result = result + '<tr>';
						//Employee	Emp ID	SSN	Total Hrs	Gross Wages	FICA	FUTA	SUI	ER Locals	WC Cost	Admin Fee	Total ER Cost	
						result = result + '<th>From Date</th>';
						result = result + '<th>To Date</th>';
						result = result + '<th>Employee Id</th>';
						result = result + '<th>Employee Name</th>';
						result = result + '<th>SSN</th>';
						result = result + '<th>Total Hrs</th>';
						result = result + '<th>Gross Wages</th>';
						result = result + '<th>FICA</th>';
						result = result + '<th>FUTA</th>';
						result = result + '<th>SUI</th>';
						result = result + '<th>ER Locals</th>';
						result = result + '<th>WC Cost</th>';
						result = result + '<th>Admin Fee</th>';
						result = result + '<th>Total ER Cost</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
						 var json = JSON.parse(data);
			        	 for (var i=0; i<json.length; i++)
						{
			        		 var opt = json[i];
			        		    result = result + ' <tr>';
								result = result + '<td><div>'+opt.fromDateStr+'</div></td>';
								result = result + ' <td><div>'+opt.toDateStr+'</div></td>';
								result = result + '<td><div>'+opt.employeeId+'</div></td>';
								result = result + '<td><div>'+opt.employeeName+'</div></td>';
								if(opt.ssn !=null && opt.ssn !='' && opt.ssn !='undefined'){
									result = result + ' <td><div>'+opt.ssn+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								if(opt.totalHrs !=null && opt.totalHrs !='' && opt.totalHrs !='undefined'){
									result = result + '<td><div>'+opt.totalHrs+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								result = result + ' <td><div>'+opt.grossWages+'</div></td>';
								result = result + '<td><div>'+opt.fica+'</div></td>';
								result = result + '<td><div>'+opt.futa+'</div></td>';
								result = result + ' <td><div>'+opt.sui+'</div></td>';
								result = result + '<td><div>'+opt.erLocals+'</div></td>';
								result = result + ' <td><div>'+opt.wcCost+'</div></td>';
								result = result + '<td><div>'+opt.adminFee+'</div></td>';
								result = result + '<td><div>'+opt.totalErCost+'</div></td>';
								result = result + ' </tr>';
						}
						
						
						
						
				                                          
						result = result + '  </table>';
						result = result + ' </tbody>';
						$("#employerCostDiv").empty();
						$("#employerCostDiv").append(result);
						
				        	
				        },
				        
				    });
		 
}
</script>


<script>
	$(function() {
		$('#fromDate').datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				$("#toDate").datepicker("option", "minDate", selected);
			}

		});

		$('#toDate').datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				$("#fromDate").datepicker("option", "maxDate", selected);
			}
		});

	});
</script>

<script>
	$(".addcon-pop").click(function() {
		$(".collapse-div-adduser").toggleClass("show-div");
		$(".collapse-div").removeClass("show-div");
	});
	$(".close-div").click(function() {
		$(this).parent().removeClass("show-div");
	});
	$(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(
			function(e) {
				e.stopPropagation();
			});
	$('html').click(function() {
		$(".collapse-div-adduser").removeClass("show-div");
		$(".collapse-div").removeClass("show-div");
	});
</script>

<!-- Added By Rajashekar  -->
<script type="text/javascript">
	function uploadPayrollDocument() {
		alert("hiii")
		/* if ($('#upload_payroll_form').validate(false, validationSettings)) { */

		var frm = $('#upload_payroll_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var jsonData = encodeURIComponent(con);
		var form = $('#upload_payroll_form')[0];
		var formData = new FormData(form);

		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					data : formData,
					contentType : 'application/json; charset=utf-8',
					enctype : 'multipart/form-data',
					processData : false,
					contentType : false,
					dataType : 'json',
					url : sourceleadUrl
							+ "/uploadPayrollDocument?${_csrf.parameterName}=${_csrf.token}&json="
							+ jsonData,
					success : function(result) {
						location.replace(sourceleadUrl + "/payrollList");
					},
				});
		/* return true; */
		/* } else {
			return false;
		} */
	}
	/* $('body').on('blur', '#upload_payroll_form', function() {
		$("#upload_payroll_form").showHelpOnFocus();
		$("#upload_payroll_form").validateOnBlur(false, validationSettings);
	}); */
</script>
<!-- End By Rajashekar -->

<
<script type="text/javascript">
	$(document.body)
			.on(
					'change',
					'#uploadBtn',
					function() {
						$('#uploadFile').val(this.value.split('\\').pop());
						$("#uploadFile").focus();
						if (validate_fileupload(this.value)) {
							var docFileSize = $('#uploadFile');
							var docSize = this.files[0].size;
							$("#uploadFileGroup").find(
									'p.jquery_form_error_message').remove();
							if (docSize > 2097152) {
								document.getElementById("uploadFile").value = '';
								$('#uploadFileGroup').find(
										'p.jquery_form_error_message').remove();
								$("input#uploadFile")
										.after(
												"<p class='jquery_form_error_message'>Limit is exceeded than 2MB </span>");
								document.getElementById("uploadFile")
										.setAttribute('record-exist', 'yes');
								document.getElementById("uploadFile")
										.setAttribute('record-exist-errorMsg',
												'Limit is exceeded than 2MB');
							} else {
								$('input#uploadFile').removeAttr(
										"record-exist record-exist-errormsg");
								$('#uploadFileGroup').find(
										'p.jquery_form_error_message').remove();
							}
						}
					});
</script>


<jsp:include page="../masterFooter.jsp" />