<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- <script>
window.onload = function () {
	loadEmployeePayrollCost();
}
</script>
 -->
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Add Payment</h2>
  </div>
  <div class="success_pop" id="selector1" style="display: none;">
		<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Payroll</div>
</section>


<div class="content-area content-new clearfix">
    <!--  import div -->
    <div class="clearfix">
     <div id="loading"></div>
		 <form:form id="add_cheques_form" name="add_cheques_form" method="post">
                  <div class="col-md-12 mt" id="adddocDiv">
                    <div class="form-horizontal clearfix myForm">
                     
                        <div class="form-group" id="fromDateDiv">
                        	<label class="col-md-3">Employee Name<span class="text-danger paddingLeft">*</span></label>
                        	<div class="col-md-9">
                        	<select class="chosen-select" id="empId" name="empId"  titl="Employee" field-name="Employee" data-validation="required">
                        	 <option value="" selected >-Select Employee-</option>
						            <c:forEach var="wage" items="${wageTaxSummaryList}">
										<option value="${wage.employeeId}">${wage.employeeName}</option>
									</c:forEach>
                        	</select>
                        	</div>
                        </div>
                        
                        <div class="form-group" id="batchNumberDiv">
                          <label class="col-md-3">Type Of Expenses</label>
                          	<div class="col-md-9">
                          	<select class="chosen-select" id="typeOfExpense" name="typeOfExpense" data-validation="required" field-name="Type Of Expense">
                          	<option value=''>---Select Type Of Expenses-</option>
                        	<option value="LEGAL_FEES">Legal Fees</option>
                        	<option value="ADVANCE">Advance</option>
                        	<option value="GENERAL_EXPENSES">General Expenses</option>
                        	<option value="VENDOR_PAYMENTS">Vendor Payments</option>
                        	<option value="ELECTRONIC_WITHDRAWLS">Electronic withdrawls</option>
                        	<option value="PAYROLL_CHKS">Payroll chks</option>	
                        	</select>
                        	</div>
                        </div>
                        
                        
                        <div class="form-group" id="toDateDiv">
	                        <label class="col-md-3">Cheque Number<span class="text-danger paddingLeft">*</span></label>
	                        <div class="col-md-9">
	                        <input type="text" class="form-control" id="checkNo" name="checkNo" value="" placeholder="Cheque Number" field-name="Cheque Number" data-validation="required">
	                        </div>
                        </div>
                        
                        <div class="form-group" id="toDateDiv">
	                        <label class="col-md-3">Date<span class="text-danger paddingLeft">*</span></label>
	                        <div class="col-md-9">
	                        <input type="text" class="form-control calendar" id="date" name="date" value="" placeholder="Date*" field-name="Date" data-validation="required">
	                        </div>
                        </div>
                        
                        <div class="form-group" id="amount">
	                        <label class="col-md-3">Amount</label>
	                        <div class="col-md-9">
	                       <input type="text" class="form-control" placeholder="Amount" id="amount" name="amount">
	                        </div>
                        </div>
                        
                        <div class="form-group" id="memo">
	                        <label class="col-md-3">Memo</label>
	                        <div class="col-md-9">
	                        <textarea rows="3" class="form-control" placeholder="Memo" id="memo" name="memo"></textarea>
	                        </div>
                        </div>
                     
                       
                      </div>
                      <div class="clearfix  form-footer row mt">
                      <div class="pull-right">
                        <button type="button" class="btn btn-sm btn-primary mr" onclick="saveChecks()">Add Check</button>
                        <button type="button" class="btn btn-sm btn-default mr" onclick="cancelChecks()">Cancel Check</button>
                        </div>
                      </div>
                    </div>
                  </div>
                   </form:form>
		
		
    </div>
    <!--  import div -->
    </div>
</section>


<script type="text/javascript">
function saveChecks(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_user.id}';
	 if ($('#add_cheques_form').validate(false, validationSettings)) { 
            var sourceleadUrl = '${sourceleadUrl}';
			$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			var formData = getFormData("add_cheques_form");
			var sourceleadUrl = '${sourceleadUrl}';
			$.post(sourceleadUrl+ "/addCheques?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&userId="+userId, formData, function(data) {
				if(data == true){
					location.replace(sourceleadUrl+'/payrollList');
				}
			}); 
		
		}   
}
</script>

<script type="text/javascript">
function cancelChecks(){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+'/payrollList');
}
</script>

<!-- <script>
function runPayrollCycles(type){
	var payrollValue = $('#payrollCycle').val();
	if(type != "main" || (payrollValue != "" && payrollValue != 'undefined' && payrollValue != null)){
		if ($('#run_payroll_cycle').validate(false, validationSettings)) { 
			$('#addPayrollDiv').removeClass("show-div");
			var payrollCycle = $('#payrollCycle :selected').text();
		var frm = $('#run_payroll_cycle').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var jsonData = encodeURIComponent(con);
		var form = $('#run_payroll_cycle')[0];
		var formData = new FormData(form);
		$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
					type : "GET",
					data : formData,
					contentType : 'application/json; charset=utf-8',
					enctype : 'multipart/form-data',
					processData : false,
					contentType : false,
					dataType : 'json',
					url : sourceleadUrl+ "/runPayrollCycle?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
					success : function(result) {
						generatrPayroll(result,payrollCycle);
						$("#loading").removeAttr("style");
					},
				});
		
		} 
	}else if(type == "main"){
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Please Select Payroll Cycle</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input class='width100percnt' id='confirm-id' type='button' value='Ok' onclick='cancel();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	 
}

function flushPayrollCycle(){
	$("#payrollCycleDiv").find('p.jquery_form_error_message').remove();
	$('#payrollCycle').val('').trigger("chosen:updated");
}

</script> -->

<!-- <script>
function flushValues(){
	$("#fromDate").val("");
	$("#toDate").val("");
	$("#payrollType").val('').trigger("chosen:updated");
	$("#batchNumber").val("");
	$("#uploadFile").val("");
	
	$("#fromDateDiv").find('p.jquery_form_error_message').remove();
	$("#toDateDiv").find('p.jquery_form_error_message').remove();
	$("#payrollTypeDiv").find('p.jquery_form_error_message').remove();
	$("#batchNumberDiv").find('p.jquery_form_error_message').remove();
	$("#uploadFileGroup").find('p.jquery_form_error_message').remove();
}

</script>

<script>
$("ul#parentUl").on("click","li", function(){
	$(this).find('a').addClass("wVer");
	$(this).siblings('li').find('a').removeClass("wVer");
});
</script>

<script>
function flush(){
	$("#uploadBulkBtn").val("");	
}
</script> -->




<script>
	$(function() {
		$('#date').datepicker({
			changeMonth : true,
			changeYear : true,
			dateFormat : 'dd-mm-yy',
			onSelect : function(selected) {
				$("#date").datepicker("option", "minDate", selected);
			}

		});

	});
</script>


<script>
function clearDate(){
	$("#date").val("");
	};
	
	
</script>





<jsp:include page="../masterFooter.jsp" />