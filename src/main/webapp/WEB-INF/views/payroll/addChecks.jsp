<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Add Payment</h2>
  </div>
  <div class="success_pop" id="selector1" style="display: none;">
		<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
  </div>
  <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; Payroll</div>
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
                        <button type="button" class="btn tblminwidth-90 btn-primary mr" onclick="saveChecks()">Add Check</button>
                        <button type="button" class="btn tblminwidth-90 btn-default mr" onclick="cancelChecks()">Cancel Check</button>
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
	var userId = '${SourceLeadWeb_userId}';
	 if ($('#add_cheques_form').validate(false, validationSettings)) { 
			$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			var formData = getFormData("add_cheques_form");
			$.post(pmsUrl+ "/addCheques?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId+"&userId="+userId, formData, function(data) {
				if(data == true){
					location.replace(pmsUrl+'/payrollImports');
				}
			}); 
		
		}   
}
</script>

<script type="text/javascript">
function cancelChecks(){
	location.replace(pmsUrl+'/payrollImports');
}
</script>


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