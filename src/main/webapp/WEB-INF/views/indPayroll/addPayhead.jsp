<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Content Area -->
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Add Payhead</h2>
		</div>
		<div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/payrollConfiguration">Payroll Configure</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/payheadList">Payhead List</a> &raquo; Add Payhead</div>
	</section>
<form:form id="payhead_form" name="payhead_form">
	<div class="contentareaInner-styles clearfix">
		<div class="clearfix form-horizontal minHeight_225 minHeightcol5">
			<div class="row min">

				<div class="col-md-5" id="payheadNameDiv">
					<label class="clearfix">Name<span class="text-danger">&nbsp;*</span></label>
					<div class="clearfix">
						<input type="text" class="form-control" id="payheadName" name="name" value="${payrollPayhead.name}"
							field-name="Name"  data-validation="required validate_Space validate_length length1-100">
						<input id="payheadNameDummy" hidden value="${payrollPayhead.name}">
					</div>
				</div>
				<div class="col-md-5" id="payheadShortCodeDiv">
					<label class="clearfix">Short Code<span class="text-danger">&nbsp;*</span></label>
					<div class="clearfix">
					<c:if test="${!empty payrollPayhead && payrollPayhead.shortCode != ''}">
						<input type="text" class="form-control" readonly id="shortCodeEdit" name="shortCode" value="${payrollPayhead.shortCode}"
							field-name="Short Code"  data-validation="required validate_Space validate_length length1-10">
					</c:if>
					<c:if test="${empty payrollPayhead || payrollPayhead.shortCode == ''}">
						<input type="text" class="form-control"  id="shortCode" name="shortCode" value="${payrollPayhead.shortCode}"
							field-name="Short Code"  data-validation="required validate_Space validate_length length1-10">
					</c:if>
						<%-- <input type="text" class="form-control" ${!empty payrollPayhead && payrollPayhead.shortCode != '' ? 'readonly' : ''} id="shortCode" name="shortCode" value="${payrollPayhead.shortCode}"
							field-name="Short Code"  data-validation="required validate_Space validate_length length1-10"> --%>
					</div>
				</div>
				<div class="col-md-5">
					<label class="clearfix">Effective Date</label>
					<div class="clearfix">
						<div class="input-group">
							<input type="text" class="form-control calendar" id="effectiveDate" name="effectiveDate"value="${payrollPayhead.effectiveDateStr}" field-name="Effective Date">
								<span class="input-group-addon" onClick="clearEffectiveDate();"><i class="colorRed fa fa-times cursorPointer"></i></span>
						</div>
					</div>
				</div>
				<div class="col-md-5">
					<label class="clearfix">Type<span class="text-danger">&nbsp;*</span></label>
					<div class="clearfix dropdown-select">
						<c:if test="${!empty payrollPayhead && !empty payrollPayhead.type && payrollPayhead.type != ''}">
							<select class="form-control sel" id="editType" field-name="Type" data-validation="required" ${!empty payrollPayhead && !empty payrollPayhead.type  ? 'disabled' :''}>
								<option value="Earnings" ${payrollPayhead.type  == 'Earnings'  ? 'selected' :''}>Earning</option>
								<option value="Deduction" ${payrollPayhead.type  == 'Deduction'  ? 'selected' :''}>Deduction</option>
							</select>
							<input type="text" name="type" value="${payrollPayhead.type}" style="display: none;">
						</c:if>
						<c:if test="${empty payrollPayhead || payrollPayhead.type == ''}">
							<select class="form-control chosen-select" id="type" name="type" field-name="Type" data-validation="required" ${!empty payrollPayhead && !empty payrollPayhead.type  ? 'disabled' :''}  onchange = "unCheck(this.value);">
								<option value="Earnings" ${payrollPayhead.type  == 'Earnings'  ? 'selected' :''}>Earning</option>
								<option value="Deduction" ${payrollPayhead.type  == 'Deduction'  ? 'selected' :''}>Deduction</option>
							</select>
						</c:if>
					</div>
				</div>
				<%-- <div class="col-md-5">
					<label class="clearfix">Pay Type</label>
					<div class="clearfix">
						<div class="row">
							<div class="col-md-10">
								<select class="form-control chosen-select" id="payType" name="payType" field-name="Pay Type">
									<option value="">--Select Pay Type--</option>
		                			<c:forEach var="list" items="${payTypeList}">
				                    	<option value="${list.id}" ${list.id  == payrollPayhead.payType.id  ? 'selected' : list.id == 1 ? 'selected' : ''} >${list.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2">
								<a href="javascript:void(0)" class="btn btn-default" onClick="addPaytypeFun();"><i
									class="fa fa-plus"></i></a>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-5">
					<label class="clearfix">Form-16</label>
					<div class="clearfix dropdown-select">
						<select class="form-control chosen-select" id="form16" name="form16">
							<option value="">--Select Form-16--</option>
							<option value="None" ${payrollPayhead.form16  == 'None'  ? 'selected' :''}>None</option>
							<option value="Salary u/s 17(1)" ${payrollPayhead.form16  == 'Salary u/s 17(1)'  ? 'selected' :''}>Salary u/s 17(1)</option> 
							<option value="Prerequisites u/s 17(2)" ${payrollPayhead.form16  == 'Prerequisites u/s 17(2)'  ? 'selected' :''}>Prerequisites u/s 17(2)</option>
							<option value="Salary u/s(3)" ${payrollPayhead.form16  == 'Salary u/s(3)'  ? 'selected' :''}>Salary u/s(3)</option>
							<option value="Allowances" ${payrollPayhead.form16  == 'Allowances'  ? 'selected' :''}>Allowances</option>
							<option value="Deduction u/s 16" ${payrollPayhead.form16  == 'Deduction u/s 16'  ? 'selected' :''}>Deduction u/s 16</option>
							<option value="Deduction under chapter VI-A" ${payrollPayhead.form16  == 'Deduction under chapter VI-A'  ? 'selected' :''}>Deduction under chapter VI-A</option>
							<option value="TDS u/s 192(1)" ${payrollPayhead.form16  == 'TDS u/s 192(1)'  ? 'selected' :''}>TDS u/s 192(1)</option>
							<option value="Tax paid by Employer Preqs" ${payrollPayhead.form16  == 'Tax paid by Employer Preqs'  ? 'selected' :''}>Tax paid by Employer Preqs</option>
							<option value="Section 80C" ${payrollPayhead.form16  == 'Section 80C'  ? 'selected' :''}>Section 80C</option>
							<option value="Advanced" ${payrollPayhead.form16  == 'Advanced'  ? 'selected' :''}>Advanced</option>
							<option value="HRA Exempt" ${payrollPayhead.form16  == 'HRA Exempt'  ? 'selected' :''}>HRA Exempt</option>
							<option value="PF Voluntary" ${payrollPayhead.form16  == 'PF Voluntary'  ? 'selected' :''}>PF Voluntary</option>
							<option value="Incentive(Quarter)" ${payrollPayhead.form16  == 'Incentive(Quarter)'  ? 'selected' :''}>Incentive(Quarter)</option>
							<option value="Incentive (Semi Annually)" ${payrollPayhead.form16  == 'Incentive (Semi Annually)'  ? 'selected' :''}>Incentive (Semi Annually)</option>
							<option value="Incentive(Yearly)" ${payrollPayhead.form16  == 'Incentive(Yearly)'  ? 'selected' :''}>Incentive(Yearly)</option>
							<option value="PF Loan" ${payrollPayhead.form16  == 'PF Loan'  ? 'selected' :''}>PF Loan</option>
							<option value="Salary for Bonus" ${payrollPayhead.form16  == 'Salary for Bonus'  ? 'selected' :''}>Salary for Bonus</option>
							<option value="Bonus" ${payrollPayhead.form16  == 'Bonus'  ? 'selected' :''}>Bonus</option>
							<option value="HRA Arrear" ${payrollPayhead.form16  == 'HRA Arrear'  ? 'selected' :''}>HRA Arrear</option>
							<option value="Labour Welfare Fund" ${payrollPayhead.form16  == 'Labour Welfare Fund'  ? 'selected' :''}>Labour Welfare Fund</option>
							<option value="Professional Tax Arrear" ${payrollPayhead.form16  == 'Professional Tax Arrear'  ? 'selected' :''}>Professional Tax Arrear</option>
							<option value="Section 80CCD1(Employee)" ${payrollPayhead.form16  == 'Section 80CCD1(Employee)'  ? 'selected' :''}>Section 80CCD1(Employee)</option>
							<option value="Section 80CCD(Employer)" ${payrollPayhead.form16  == 'Section 80CCD(Employer)'  ? 'selected' :''}>Section 80CCD(Employer)</option>
						</select>
					</div>
				</div> --%>
						<div class="clearfix"></div>
				<div class="col-md-2">
					<label class="clearfix">Tax</label>
					<div class="clearfix">
						<div class="radioStyles clearfix"> 
							<input type="radio" ${payrollPayhead.tax  == 'Taxable'  ? 'checked' : payrollPayhead  == null  ? 'checked' :''} id="taxable" name="tax" value="Taxable"> <label for="taxable">Taxable</label>
						</div> 
						<div class="radioStyles clearfix">  
							<input type="radio" ${payrollPayhead.tax  == 'NonTaxable'  ? 'checked' :''} id="nonTaxable" name="tax" value="NonTaxable"> <label for="nonTaxable">Non Taxable</label>
						</div>
					</div>
				</div>

				<div class="col-md-2">
					<label class="clearfix">Component</label>
					<div class="clearfix">
						<div class="radioStyles clearfix"> 
							<input type="radio" ${payrollPayhead.component  == 'Variable'  ? 'checked' : payrollPayhead  == null  ? 'checked' :''} id="variable" name="component" value="Variable"> <label for="variable">Variable</label>
						</div> 
						<div class="radioStyles clearfix">  
							<input type="radio" ${payrollPayhead.component  == 'Fixed'  ? 'checked' :''} id="fixed" name="component" value="Fixed"> <label for="fixed">Fixed</label>
						</div>
					</div>
				</div>
				
				<div class="col-md-2">
					<label class="clearfix">Applicable For</label>
					<div class="clearfix">
						<div class="radioStyles clearfix"> 
							<input type="radio" ${payrollPayhead.applicableFor  == 'All'  ? 'checked' : payrollPayhead  == null  ? 'checked' :''} id="all" name="applicableFor" value="All" onchange="experienceDiv();"> <label for="all">All</label>
						</div> 
						<div class="radioStyles clearfix">  
							<input type="radio" ${payrollPayhead.applicableFor  == 'Experienced'  ? 'checked' :''} id="experienced" name="applicableFor" value="Experienced" onchange="experienceDiv();"> <label for="experienced">Experienced</label>
						</div>
					</div>
				</div>
				
				<div class="col-md-3" id="divExperience" ${payrollPayhead.applicableFor  == 'Experienced'  ? '' : 'style="display: none;"'}>
		            <label class="clearfix">Experience<span class="text-danger">&nbsp;*</span></label>
		            <div class="clearfix">
	              		<div class="row exp-drop">
							<div class="col-xs-4 col-md-4">
								<select class="form-control selectpicker" id="experienceYears" name="experienceYears" field-name="Years" onchange="yearAndMonthValidation()">
									<option value="">Years</option>
									<c:forEach begin="0" end="50" varStatus="loop">
										<option ${payrollPayhead.experienceYears  == loop.index  ? 'selected' :''}>${loop.index}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-xs-4 col-md-4 padding_left">
								<select class="form-control selectpicker experienceMonths" id="experienceMonths" name="experienceMonths" field-name="Months" onchange="yearAndMonthValidation()">
									<option value="">Months</option>
									<c:forEach begin="0" end="11" varStatus="loop">
										<option ${payrollPayhead.experienceMonths  == loop.index  ? 'selected' :''}>${loop.index}</option>
									</c:forEach>
								</select>
							</div>
						</div>
		            </div>
		         </div>
		         <div class="clearfix"></div>
		         
		         <c:if test="${isFormulaEditable eq 'Y' || empty payrollPayhead.uuid}">
		         <div class="clearfix mt">
		         <div class="col-md-5">
					<label class="clearfix">Method</label>
					<div class="clearfix">
						<select class="sel" id="method" name="method">
							<!-- <option value="">--Select method--</option> -->
							<option ${payrollPayhead.method == 'Flat' ? 'selected' : ''}>Flat</option>
							<option ${payrollPayhead.method == '% of CTC' ? 'selected' : ''}>% of CTC</option>
							<%-- <option ${payrollPayhead.method == '% of Gross pay' ? 'selected' : ''}>% of Gross pay</option> --%>
							<option ${payrollPayhead.method == '% of Payheads' ? 'selected' : ''}>% of Payheads</option>
							<%-- <option ${payrollPayhead.method == 'Formula' ? 'selected' : ''}>Formula</option> --%>
						</select>
					</div>
				</div>
				
				<div class="col-md-5">
				
				<div class="clearfix" id="formulaInputDiv" ${empty payrollPayhead || empty payrollPayhead.method || payrollPayhead.method == '% of CTC' || payrollPayhead.method == '% of Gross pay' || payrollPayhead.method == 'Flat' ? '' : 'hidden'}>
					<label class="clearfix" id="formulaLabel">${payrollPayhead.method == '% of Payheads' || payrollPayhead.method == 'Formula' ? 'Formula' : payrollPayhead.method == '% of CTC' || payrollPayhead.method == '% of Gross pay' ? 'Percentage' :'Amount'}</label>
					<div class="clearfix">
						<input type="text" class="form-control" id="formula" ${payrollPayhead.method == '% of CTC' || payrollPayhead.method == '% of Gross pay' || payrollPayhead.method == 'Flat' || empty payrollPayhead.method ? 'name="formula"' : ''} placeholder="Enter amount" data-validation-optional="true" value="${payrollPayhead.formula}">
					</div>
				</div>
				
				<div class="row" id="payheadsFormulaGroup" ${payrollPayhead.method == '% of Payheads' || payrollPayhead.method == 'Formula' ? '' : 'hidden'}>
					<div id="payheadsFormulaGroupOpt" ${payrollPayhead.method == 'Formula' ? 'hidden' : ''}>
					<div class="col-md-3">
		              <label>Percentage (%)</label>
		              <div class="clearfix">
							<select class="form-control chosen-select" id="formulaPercentage" name="formulaPercentage">
								<option value="">Select</option>
		                			<c:forEach begin="1" end="100" varStatus="percent">
											<option >${percent.index}%</option>
									</c:forEach>
							</select>
		              </div>
		            </div>
		            <div class="col-md-5 padding_left">
		              <label>Payhead</label>
		              <div class="clearfix">
							<select class="form-control chosen-select" id="formulaPayhead" name="formulaPayhead">
								<option value="">Select</option>
									<%-- <option value="(CTC)">CTC (CTC)</option>
									<option value="(GROSS)">Gross (GROSS)</option> --%>
		                			<c:forEach var="list" items="${payheadList}">
			                			<c:if test="${payrollPayhead.id != list.id}">
			                				<option value="(${list.shortCode})">${list.name} (${list.shortCode})</option>
			                			</c:if>
									</c:forEach>
							</select>
		              </div>
		            </div>
		            <div class="col-md-2 padding_left">
		              <label>Operator</label>
		              <div class="clearfix">
		                <select class="form-control chosen-select" id="formulaOperator" name="formulaOperator">
		                  <option value="">Select</option>
		                  <option>&#43;</option>
		                  <option>&#42;</option>
		                  <option>&#47;</option>
		                  <option>&#45;</option>
		               <!--    <option>&#61;</option>
 -->		              <option>&#37;</option>
		                  <%--<option>&#40;</option>
		                  <option>&#41;</option> --%>
		                  <option>&#91;</option>
		                  <option>&#93;</option>
		                  <option>&#123;</option>
		                  <option>&#125;</option>
		                </select>
		              </div>
		            </div>
		            <div class="col-md-2">
		              <label>Action</label>
		              <button type="button" class="btn btn-success btnSuccess btn-block" onclick="insertAtCursor('formulaTextareaId');return false;">Add</button>
		            </div>
	            </div>
		            <div class="clearfix"></div>
		            <div class="clearfix col-md-12" id="formulaTextareaDiv">
		              <textarea id="formulaTextareaId" ${payrollPayhead.method == '% of Payheads' || payrollPayhead.method == 'Formula' ? 'name="formula"' : ''}  class="form-control" rows="2">${payrollPayhead.formula}</textarea>
		              <div class="pull-right mt5" id="payheadsFormulaValidateBtn" ${payrollPayhead.method == 'Formula' ? 'hidden' : ''}>
		                <button type="button" class="btn btn-success btnSuccess" onClick="validateFormula();">Validate</button>
		              </div>
		              <!-- <p class="text-success mt5">Valid expression</p>
		              <p class="text-danger mt5">Invalid expression</p> -->
		            </div>
	          </div>
				
				
				</div>
				
				
				
				<div class="clearfix"></div>
				</div>
				</c:if>
				
		         
				<div class="clearfix"></div>
				<div class="col-md-10 mt">
					<label class="clearfix">Other</label>
					<div class="clearfix">
						<div class="checkbox-new pull-left">
							<input id="pc" class="checkbox-custom" name="paidComponent"
								type="checkbox" ${payrollPayhead != null ? payrollPayhead.paidComponent  == 'Y' ? "checked" : '' : "checked"} value="Y"> <label for="pc"
								class="checkbox-custom-label"><span class="ml10">Paid
									Component</span></label>
						</div>
						<%-- <div class="checkbox-new ml mr pull-left">
							<input id="partOfGratitutiy" class="checkbox-custom" name="partOfGratitutiy"
								type="checkbox" ${payrollPayhead.partOfGratitutiy  == 'Y' ? "checked" : ''} value="Y"> <label for="partOfGratitutiy"
								class="checkbox-custom-label"><span class="ml10">Part
									of Gratitutiy</span></label>
						</div>
						<div class="checkbox-new pull-left ml mr">
							<input id="fbpComponent" class="checkbox-custom" name="fbpComponent"
								type="checkbox" ${payrollPayhead.fbpComponent  == 'Y' ? "checked" : ''} value="Y"> <label for="fbpComponent"
								class="checkbox-custom-label"><span class="ml10">FBP
									Component</span></label>
						</div> --%>
						<div class="checkbox-new  pull-left ml mr">
							<input id="leaveDependent" class="checkbox-custom" name="leaveDependent"
								type="checkbox" ${payrollPayhead.leaveDependent  == 'Y' ? "checked" : ''} value="Y"> <label for="leaveDependent"
								class="checkbox-custom-label"><span class="ml10">Leave Dependent</span></label>
						</div>
						<div class="checkbox-new ml mr pull-left">
							<input id="showInPayslip" class="checkbox-custom" name="showInPayslip"
								type="checkbox" ${payrollPayhead.showInPayslip  == 'Y' ? "checked" : ''} value="Y"> <label for="showInPayslip"
								class="checkbox-custom-label"><span class="ml10">Show in Payslip</span></label>
						</div>
						<div class="checkbox-new pull-left ml mr">
							<input id="pfApplicable" class="checkbox-custom" name="pfApplicable"
								type="checkbox" ${payrollPayhead.pfApplicable  == 'Y' ? "checked" : ''} value="Y"> <label for="pfApplicable"
								class="checkbox-custom-label"><span class="ml10">PF Applicable</span></label>
						</div>
						<div class="checkbox-new pull-left ml mr">
							<input id="esiApplicable" class="checkbox-custom" name="esiApplicable"
								type="checkbox" ${payrollPayhead.esiApplicable  == 'Y' ? "checked" : ''} value="Y"> <label for="esiApplicable"
								class="checkbox-custom-label"><span class="ml10">ESI Applicable</span></label>
						</div>
						<div class="checkbox-new pull-left">
							<input id="ptApplicable" class="checkbox-custom" name="ptApplicable"
								type="checkbox" ${payrollPayhead != null ? payrollPayhead.ptApplicable  == 'Y' ? "checked" : '' : "checked"} value="Y"> <label for="ptApplicable"
								class="checkbox-custom-label"><span class="ml10">PT Applicable
									</span></label>
						</div>
						
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<div class="clearfix footerstyelsNew">
		<div class="col-lg-12">
			<div class="pull-right">
				<input value="Save" class="btn btn-success btnSuccess" id="savePayheadBtn" type="button" onclick="storePayhead();">
				<input value="Cancel" onclick="javascript:window.location.href = '${SourceLeadWeb_pmsUrl}/payheadList'" class="btn btn-default ml10" type="button">
			</div>
		</div>
	</div>
 </form:form>
	<!-- Content Area Ends-->

<div class="comMask"></div>
<div class="modal addPaytype" id="addPaytype">
	<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" id="closePaytypePopup" onClick="closePaytypeFun();">&times;</button>
			<h4>Add Paytype</h4>
		</div>
		<form:form id="paytype_form" name="paytype_form" method="post">
			<div class="modal-body">
				<div class="minHeight50 mb" id="paytypeNameDiv">
					<label class="clearfix">Name<span class="text-danger">&nbsp;*</span></label>
					<div class="clearfix">
						<input type="text" class="form-control" id="paytypeName" name="name" field-name="Name"  data-validation="required validate_Space validate_Alphanumeric validate_length length1-100">
					</div>
				</div>
				<div class="minHeight50 mt">
					<label class="clearfix">Description</label>
					<div class="clearfix">
						<textarea class="form-control" rows="3" id="paytypeDescription" name="description" field-name="Description" data-validation-optional="true" data-validation="validate_Space validate_length length1-500"></textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="pull-right">
					<button class="btn btn-success btnSuccess mr" type="button" id="savePaytypeBtn">Save</button>
					<button class="btn btn-default" type="button" data-dismiss="modal" id="cancelPaytypeBtn" onClick="closePaytypeFun();">Cancel</button>
				</div>
			</div>
		</form:form>
		</div>
	</div>
</div>


<script type="text/javascript">
	var config = {
	  '.chosen-select'           : {},
	  '.chosen-select-deselect'  : {allow_single_deselect:true},
	  '.chosen-select-no-single' : {disable_search_threshold:10},
	  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	  '.chosen-select-width'     : {width:"95%"}
	}
	for (var selector in config) {
	  $(selector).chosen(config[selector]);
	}
	$('.chosen-select').each(function() {
		var $this = $(this);
		$this.next().css({'width': '100%'});
		})
		$(document).ready(function(){
			$("#cd").click(function(){
				$(".clientDetails").toggleClass('hidden', 500);
			});
	});
	
	
	$(function () {
		  		$("#paytypeName").on('focus', function() {
			  			$('#savePaytypeBtn').attr("disabled", true);
			  	})
			  	.on("blur", function () {
			  	var name = $("#paytypeName").val();
			  	var pmsUrl ='${SourceLeadWeb_pmsUrl}';

		    	$.ajax({
				 type : "POST",
				 url : pmsUrl+"/findPayrollPaytypeByNameAndLocationId?name="+name,
				 success : function(result) {
					 	if (result.response === "SESSION_TIMEDOUT") {
			  				location.replace(pmsUrl);
		                } else if (result.response === "AWKWARD") {
		                    location.href = "/errorPage";
		                } else if (result.response === "SUCCESS") {
		                	if(result.error == "true"){
		                		$('#paytypeNameDiv').find('p.jquery_form_error_message').remove();
		                		$("input#paytypeName").after("<p class='jquery_form_error_message'>Paytype already existed</p>");
		                	}else{
		                		$('#savePaytypeBtn').removeAttr("disabled");
		                	}
		                } else if (result.indexOf('Sign In') !== -1) {
							location.replace(pmsUrl);
						} else {
		                    showError("Operation failed, please try again");
		                }
				  	},
			});
		    
		  });

		});
	
	
	$(function () {
  		$("#payheadName").on('focus', function() {
	  			$('#savePayheadBtn').attr("disabled", true);
	  	})
	  	.on("blur", function () {
	  		var name = $("#payheadName").val();
	  	  	var shortCode = "";
	  	  	var listType = "ByName";
	  	  	var dummyName = $("#payheadNameDummy").val();
	  	  	if(name != dummyName){
	  	  		findPayheadByNameOrShortCode(name, shortCode, listType);
	  	  	}else{
	  	  		$('#savePayheadBtn').removeAttr("disabled");
	  	  	}
  		});
	});
	
	$(function () {
  		$("#shortCode").on('focus', function() {
	  			$('#savePayheadBtn').attr("disabled", true);
	  	})
	  	.on("blur", function () {
	  		var name = "";
	  	  	var shortCode = $("#shortCode").val();
	  	  	var listType = "ByShortCode";
	  		findPayheadByNameOrShortCode(name, shortCode, listType);
  		});
	});
	
function findPayheadByNameOrShortCode(name, shortCode, listType){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	$.ajax({
	 type : "POST",
	 url : pmsUrl+"/findPayrollPayheadByNameOrShortCodeAndLocationId?name="+name+"&shortCode="+shortCode+"&listType="+listType,
	 success : function(result) {
		 	if (result.response === "SESSION_TIMEDOUT") {
  				location.replace(pmsUrl);
            } else if (result.response === "AWKWARD") {
                location.href = "/errorPage";
            } else if (result.response === "SUCCESS") {
            	if(result.error == "true"){
            		if(listType == "ByName"){
            			$('#payheadNameDiv').find('p.jquery_form_error_message').remove();
            			$("input#payheadName").after("<p class='jquery_form_error_message'>"+name+" payhead is already existed</p>");
            			$("#payheadName").val(y);
            		}if(listType == "ByShortCode"){
            			$('#payheadShortCodeDiv').find('p.jquery_form_error_message').remove();
            			$("input#shortCode").after("<p class='jquery_form_error_message'>"+shortCode+" short code already existed</p>");
            			$("#shortCode").val("");
            	 }
            	}else{
            		$('#savePayheadBtn').removeAttr("disabled");
            	}
            } else if (result.indexOf('Sign In') !== -1) {
				location.replace(pmsUrl);
			} else {
                showError("Operation failed, please try again");
            }
	  	},
});
}
</script>
        
<script type="text/javascript">
	$(document).ready(function(e) {
		$("#savevendor").click(function(){
			$('#myform').validate({
			rules:{
				things:{
					required:true
				}
			}
			});
		});
	});
</script>
<script>	
	$("#aa").click(function() {
	  $(".subMenuView").slideToggle(100);
	});
</script>


<script type="text/javascript">
	function storePayhead() {
		var validationSettings = {
		        errorMessagePosition : 'element',
		       borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd' 
	         }; 
			 
		     if ($('#payhead_form').validate(false, validationSettings)){
		    	 $('#savePayheadBtn').prop('disabled', true);
      			var formData = getFormData1("payhead_form");
      			var uuid = '${uuid}';
      			var pmsUrl ='${SourceLeadWeb_pmsUrl}';
				$.ajax({
					 type : "POST",
					 data: formData,
	 		         processData: false,
	 		         contentType:false, 
					  url : pmsUrl+"/storePayhead?"+tokenParamName+"="+tokenValue+"&uuid="+uuid,
					  	success : function(result) {
					  		if(result.object === 'MissingData'){
					  			showError("Invalid data");
					  		}else{
					  			if (result.response === "SESSION_TIMEDOUT") {
					  				location.replace(pmsUrl);
				                } else if (result.response === "AWKWARD") {
				                    location.href = "/errorPage";
				                } else if (result.response === "SUCCESS") {
								  	location.replace(pmsUrl+"/payheadList");
				                } else if (result.indexOf('Sign In') !== -1) {
									location.replace(pmsUrl);
								} else {
				                    showError("Operation failed, please try again");
				                }
					  		}
					  	},
				});
			
				return true;
		   	}else{ 
				return false;
		 }   
	} 
	
	$('body').on('blur', '#payhead_form', function() {
		$("#payhead_form").showHelpOnFocus();
		$("#payhead_form").validateOnBlur(false, validationSettings);
	});
	
</script>

<script type="text/javascript">
	$(function(){
	    $('#savePaytypeBtn').click(function() {
	    	var validationSettings = {
			        errorMessagePosition : 'element',
			       borderColorOnError : '',
					scrollToTopOnError : true,
					dateFormat : 'yyyy/mm/dd' 
		         }; 
				 
			     if ($('#paytype_form').validate(false, validationSettings)){
			    	 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	      			var formData = getFormData1("paytype_form");
					$.ajax({
						 type : "POST",
						 data: formData,
		 		         processData: false,
		 		         contentType:false, 
						 url : pmsUrl+"/storePaytype?"+tokenParamName+"="+tokenValue,
						 success : function(result) {
							 if(result.object === 'MissingData'){
						  			showError("Invalid data");
						  		}else{
						  			if (result.response === "SESSION_TIMEDOUT") {
						  				location.replace(pmsUrl);
					                } else if (result.response === "AWKWARD") {
					                    location.href = "/errorPage";
					                } else if (result.response === "SUCCESS") {
					                	refreshPaytypeList();
					                } else if (result.indexOf('Sign In') !== -1) {
										location.replace(pmsUrl);
									} else {
					                    showError("Operation failed, please try again");
					                }
						  		}
						  	},
					});
					$('#paytypeName').val('');
			    	$('#paytypeDescription').val('');
			    	$(".addPaytype").hide();
			    	$(".comMask").fadeOut();
					return true;
			   	}else{
					return false;
			 	}
	    });
	});
	
	$('body').on('blur', '#paytype_form', function() {
		$("#paytype_form").showHelpOnFocus();
		$("#paytype_form").validateOnBlur(false, validationSettings);
	});
	
	
	$(function(){
	    $('#cancelPaytypeBtn, #closePaytypePopup').click(function() {
	    	$('#paytypeName').val('');
	    	$('#paytypeName').siblings('p').remove();
	    	$('#paytypeDescription').val('');
	    	$('#paytypeDescription').siblings('p').remove();
	    	$('#savePaytypeBtn').removeAttr("disabled");
	    });
	});
	
	function refreshPaytypeList(){
   	 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
		$.ajax({
			 type : "POST",
			 url : pmsUrl+"/getPaytypeList",
			 success : function(result) {
				 	if (result.response === "SESSION_TIMEDOUT") {
		  				location.replace(pmsUrl);
	                } else if (result.response === "AWKWARD") {
	                    location.href = "/errorPage";
	                } else if (result.response === "SUCCESS") {
	                	var json = result.data;
	    	    		var output = "";
	    	    		output = output + "<option value=''>--Select Pay Type--</option>";
	    	    		for (var i = 0; i < json.length; i++) {
	                   	    var opt = json[i];
	    	        		output = output + "<option value='"+opt.id+"'>"+opt.name+"</option>";
	    	    		}
	    	    		$("#payType").empty();
				        $("#payType").append(output);
				        $('#payType').trigger("chosen:updated");
	                } else if (result.indexOf('Sign In') !== -1) {
						location.replace(pmsUrl);
					} else {
	                    showError("Operation failed, please try again");
	                }
			  	},
		});
	}
</script>

<script>
function experienceDiv(){
	var uuid = "${payrollPayhead.uuid}";
	var all = $('#all:checked').val()?true:false;
	var experienced = $('#experienced:checked').val()?true:false;
	if(experienced){
		$('#divExperience').show();
		$('#experienceYears').removeAttr('data-validation-optional');
        $('#experienceYears').attr("data-validation", "required");
        $('#experienceMonths').removeAttr('data-validation-optional');
        $('#experienceMonths').attr("data-validation", "required");
	}else if(all){
		$('#divExperience').hide();
		$('#experienceYears').removeAttr('data-validation');
        $('#experienceYears').attr("data-validation-optional", "true");
        $('#experienceMonths').removeAttr('data-validation');
        $('#experienceMonths').attr("data-validation-optional", "true");
        
        if(uuid == null || uuid == ""){
        	$("#experienceYears").val('default');
    		$("#experienceYears").selectpicker("refresh");
    		$("#experienceMonths").val('default');
    		$("#experienceMonths").selectpicker("refresh");
        }
	}else{
		$('#divExperience').hide();
		$('#experienceYears').removeAttr('data-validation');
        $('#experienceYears').attr("data-validation-optional", "true");
        $('#experienceMonths').removeAttr('data-validation');
        $('#experienceMonths').attr("data-validation-optional", "true");
		
        if(uuid == null || uuid == ""){
        	$("#experienceYears").val('default');
    		$("#experienceYears").selectpicker("refresh");
    		$("#experienceMonths").val('default');
    		$("#experienceMonths").selectpicker("refresh");
        }
	}
}

function yearAndMonthValidation(){
	var experienced = $('#experienced:checked').val()?true:false;
	if(experienced){
		var years = $('#experienceYears').val();
		var months = $('#experienceMonths').val();
		if(years != null){
			$('#experienceMonths').removeAttr('data-validation');
	        $('#experienceMonths').attr("data-validation-optional", "true");
		}
		if(months != null){
			$('#experienceYears').removeAttr('data-validation');
	        $('#experienceYears').attr("data-validation-optional", "true");
		}
		if(years == "" && months == ""){
			$('#experienceYears').removeAttr('data-validation-optional');
	        $('#experienceYears').attr("data-validation", "required");
	        $('#experienceMonths').removeAttr('data-validation-optional');
	        $('#experienceMonths').attr("data-validation", "required");
		}
	}
}
</script>
		
<script>
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var jsDateFormat= '';
	  if(locationDate.match("yyyy-MM-dd")){
		  jsDateFormat = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  jsDateFormat = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  jsDateFormat = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  jsDateFormat = 'yy-dd-mm'; 
	  }
	  $('#effectiveDate').datepicker({
	      dateFormat: jsDateFormat,
	      changeMonth: true,
	  	  changeYear: true,
	  	  /* minDate: 1, */
	  	  onSelect : function(selected) {
	  	  }
	  });
	  
	  function clearEffectiveDate(){
			$("#effectiveDate").val("");
	  }
</script>
<script>
function addPaytypeFun() {
	$(".addPaytype, .comMask").show();
}
function closePaytypeFun() {
	$(".addPaytype, .comMask").hide();
}
function unCheck(a)
{
	if(a == "Deduction")
		$('#ptApplicable')[0].checked = false
	else
		$('#ptApplicable')[0].checked = true	}

</script>



<!-- formula script -->
<script>
$('#method').on('change', function() {
	  $("#formulaTextareaId").val("");
	  $("#formula").val("");
	  $('#formulaPercentage').trigger("chosen:updated");
	  $('#formulaPayhead').trigger("chosen:updated");
	  $('#formulaOperator').trigger("chosen:updated");
	  $("#formulaInputDiv").show();
	  $("#payheadsFormulaValidateBtn").hide();
      $('#formula').removeAttr("data-validation");
      $('#formulaTextareaId').removeAttr("data-validation");
      
	  if($('#method :selected').text() == 'Flat'){
		  $("#formula").attr("placeholder", "Enter amount");
		  $("#formulaLabel").text("Amount");
		  $("#formula").attr("name", "formula");
		  $("#payheadsFormulaGroup").hide();
		  $("#formulaTextareaId").removeAttr("name");
		  $('#formula').attr("data-validation", "validate_Space validate_length length1-10 validate_int");
		  
	  }else if($('#method :selected').text() == '% of CTC' || $('#method :selected').text() == '% of Gross pay'){
		  $("#formula").attr("placeholder", "Enter percentage");
		  $("#formulaLabel").text("Percentage");
		  $("#formula").attr("name", "formula");
		  $("#payheadsFormulaGroup").hide();
		  $("#formulaTextareaId").removeAttr("name");
		  $('#formula').attr("data-validation", "validate_Space validate_length length1-5 validate_float");
		  
	  }else if($('#method :selected').text() == '% of Payheads'){
		  $("#formula").removeAttr("name");
		  $("#formulaInputDiv").hide();
		  $("#payheadsFormulaGroup").show();
		  $("#formulaTextareaId").attr("name", "formula");
		  $("#payheadsFormulaGroupOpt").show();
		  $("#payheadsFormulaValidateBtn").show();
	      $('#formulaTextareaId').attr("data-validation", "validate_Space validate_length length1-200");
		  
	  }else if($('#method :selected').text() == 'Formula'){
		  $("#formula").removeAttr("name");
		  $("#formulaInputDiv").hide();
		  $("#payheadsFormulaGroup").show();
		  $("#formulaInputDiv").hide();
		  $("#formulaTextareaId").attr("name", "formula");
		  $("#payheadsFormulaGroupOpt").hide();
	      $('#formulaTextareaId').attr("data-validation", "validate_Space validate_length length1-200");
		  
	  }
})



	function insertAtCursor(areaId) {
		var percentage = $("#formulaPercentage").val();
		var payhead = $("#formulaPayhead").val();
		var operator = $("#formulaOperator").val();
		
		var text = percentage+payhead+operator;
		  var txtarea = document.getElementById(areaId);
		  if (!txtarea) {
		    return;
		  }

		  var scrollPos = txtarea.scrollTop;
		  var strPos = 0;
		  var br = ((txtarea.selectionStart || txtarea.selectionStart == '0') ?
		    "ff" : (document.selection ? "ie" : false));
		  if (br == "ie") {
		    txtarea.focus();
		    var range = document.selection.createRange();
		    range.moveStart('character', -txtarea.value.length);
		    strPos = range.text.length;
		  } else if (br == "ff") {
		    strPos = txtarea.selectionStart;
		  }

		  var front = (txtarea.value).substring(0, strPos);
		  var back = (txtarea.value).substring(strPos, txtarea.value.length);
		  txtarea.value = front + text + back;
		  strPos = strPos + text.length;
		  if (br == "ie") {
		    txtarea.focus();
		    var ieRange = document.selection.createRange();
		    ieRange.moveStart('character', -txtarea.value.length);
		    ieRange.moveStart('character', strPos);
		    ieRange.moveEnd('character', 0);
		    ieRange.select();
		  } else if (br == "ff") {
		    txtarea.selectionStart = strPos;
		    txtarea.selectionEnd = strPos;
		    txtarea.focus();
		  }

		  txtarea.scrollTop = scrollPos;
		  
		  $('#formulaPercentage').val('').trigger("chosen:updated");
		  $('#formulaPayhead').val('').trigger("chosen:updated");
		  $('#formulaOperator').val('').trigger("chosen:updated");
		}
	
	function validateFormula1(){
		var formula = $("#formulaTextareaId").val();
		
		for (var i = 0; i < formula.length; i++) {
			  
			//alert(formula.charAt(i));
			  
			  if(/^[a-zA-Z]$/.test(formula.charAt(i))){
				//	alert(i);
				}
			}
	}
</script>
	<jsp:include page="../masterFooter.jsp" />