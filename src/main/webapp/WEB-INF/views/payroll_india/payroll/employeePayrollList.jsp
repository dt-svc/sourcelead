<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 <!-- Content Area -->
 <div id="popUp" ></div>
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Org Payroll</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>  &raquo; <a onclick="cancelIndPayroll();">Payroll</a> &raquo; Review Payroll
		</div>
	</section>
	<div class="tabsMenu clearfix">
		<ul class="list-unstyled">
			<li class="current" id="reviewTab"><a>Review Payroll</a></li>
			<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_SOURCELEAD_ADMIN' || SourceLeadWeb_loginUserRole == 'ROLE_ADMIN' || isPayrollApprover}">
				<li id="approveTab"><a>Approve Payroll</a></li>
			</c:if>
		</ul>
	</div>


	<div class="clearfix"></div>
	<div class="timesheet_out" id="reviewAndApproveDiv">
		<div class="tablelist-wrap" id="reviewDiv">
			<div class="timesheet_head minHeight">
				<div class="col-md-12 mt">
					<div class="row">
						<div class="col-md-2">
							<select class="sel selheight30">
								<option selected>${freqResponse.groupName}</option>
							</select>
						</div>
						<div class="col-md-2">
							<select class="sel selheight30" id="paySheetFrequency">
								<option selected value="${freqResponse.paySheetFrequencyUUID}">${freqResponse.frequencyStartDateStr} - ${freqResponse.frequencyEndDateStr}</option>
							</select>
						</div>
						<div class="col-md-2">
							<input name="" class="form-control calendar input-sm" placeholder="Check Date" type="text" readonly="readonly" value="${freqResponse.payCheckDateStr}">
						</div>
						<div class="col-md-2">
							<select class="sel selheight30">
								<option selected>Salary</option>
							</select>
						</div>
						
						<c:if test="${freqResponse.approverStatus != 'APPROVED'}">
							<div class="col-md-4">
								<a class="pull-right" onClick="bulkAdjustment('${freqResponse.paySheetFrequencyUUID}');" title="Payhead Bulk Adjustment"><i class="fa fa-object-group paddingRight"></i>Bulk Actions</a>
							</div>
						</c:if>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>

			<div class="tableScroll styleTables">
				<div class="clearfix mt">
					<table id="example" class="stripe row-border order-column"
						style="width: 100%">
						<thead>
							<tr>
								<c:if test="${fn:length(employeePayrollList) gt 1 && freqResponse.approverStatus != 'SENT' && freqResponse.approverStatus != 'APPROVED'}">
									<th>Exclude</th>
								</c:if>
								<th>Emp ID</th>
								<th>Emp Name</th>
								<th>Regular Hours</th>
								<th>OT Hours</th>
								<th>No. of Leaves</th>
								<th>Gross Pay</th>
								<th>TDS</th>
								<th>Net Pay</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="empPayroll" items="${employeePayrollList}">
								<tr>
									<c:if test="${fn:length(employeePayrollList) gt 1 && freqResponse.approverStatus != 'SENT' && freqResponse.approverStatus != 'APPROVED'}"> 
										 <td>
												<div class="checkbox-new pull-left ml">
													<input id="${empPayroll.indEmpPayrollUUID}" class="checkbox-custom" name="empPayroll"
														value="${empPayroll.indEmpPayrollUUID}" type="checkbox"> <label for="${empPayroll.indEmpPayrollUUID}"
														class="checkbox-custom-label"></label>
												</div>
									     </td>
								   </c:if> 
									<td><div class="tblminwidth-50">${empPayroll.empId}</div></td>
									<td><div class="tbldata-dotdot-vsm">${empPayroll.empName}</div></td>
									<td><div class="tblminwidth-50"><fmt:formatNumber value="${empPayroll.regularHours}" pattern="0"/></div></td>
									<td><div class="tblminwidth-50"><fmt:formatNumber value="${empPayroll.overtimeHours}" pattern="0"/></div></td>
									<td><div class="tblminwidth-50 clearfix">
											<fmt:formatNumber value="${empPayroll.noOfLeaves}" pattern="0"/><span class="paddingLeft"></span>
										</div></td>
									<td><div class="tblminwidth-50 clearfix">
											${empPayroll.grossPay}<span class="paddingLeft">&#8377;</span>
										</div></td>
									<td><div class="tblminwidth-50 clearfix">
											${empPayroll.tds}<span class="paddingLeft">&#8377;</span>
										</div></td>
									<td>
										<div class="tblminwidth-50 clearfix">
											<a onClick="netPay('${empPayroll.indEmpPayrollUUID}', '${empPayroll.paySheetFrequencyUUID}');"> ${empPayroll.netPay}<span class="paddingLeft">&#8377;</span> </a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<div class="clearfix mt"></div>
			<nav class="clearfix">
				<ul class="pull-right mr list-unstyled list-inline">
				    <c:if test="${freqResponse.approverStatus == 'NEW'}">
						<li><a class="btn btn-primary btnSuccess" onclick="sentForApprove('draft');">Save As Draft</a></li>
					</c:if>
					<c:if test="${freqResponse.approverStatus != 'SENT' && freqResponse.approverStatus != 'APPROVED'}">
						<li><a class="btn btn-primary btnSuccess" onclick="sentForApprove('submitted');">Submit for Approval</a></li>
					</c:if>
					<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_SOURCELEAD_ADMIN' || SourceLeadWeb_loginUserRole == 'ROLE_ADMIN' || isPayrollApprover}">
						<c:if test="${freqResponse.approverStatus != 'APPROVED'}">
							<li id="cnfmPayrollBtn"><a class="btn btn-default ml mr">Confirm</a></li>
						</c:if>
					</c:if>
					<li><a  class="btn btn-default"  onclick="cancelIndPayroll();">Cancel</a></li>
				</ul>
				<div class="clearfix"></div>
			</nav>
		</div>
	</div>
</section>

<div class="comMask"></div>
<div class="modal netPay" style="display: none;" id="netPay">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"  onclick="closeNetPay();">&times;</button>
	     <h4 class="modal-title">Employee Net Pay</h4>
	 </div>
	<div class="modal-body" style="min-height: 150px; max-height: 470px; overflow-y: auto;" id="associateSalaryIndDiv">
		<!--  container  -->
	</div>
			<div class="modal-footer">
        <button type="button" class="btn btn-default" onclick="closeNetPay();">Close</button>
      </div></div>
	 </div>
</div>
<!-- Content Area Ends--> 

<script>
$(document).ready(function() {
	$("#runPay").click(function(){
		$(".popMask").fadeIn(200);
		$("#popPassView").fadeIn(100);
	});		
	$(".popCan").click(function(){
		$(".popMask").fadeOut(200);
		$("#popPassView").fadeOut(100);
	});
});
	
function netPay(indEmpPayrollUUID, paySheetUUID){
	$("#netPay").fadeIn();
	$(".comMask").fadeIn();
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	$.ajax({
        type : "GET",
        url: pmsUrl+"/calculateAssociateSalaryInd?"+tokenParamName+"="+tokenValue+"&indEmpPayrollUUID="+indEmpPayrollUUID+"&paySheetFrequencyUUID="+paySheetUUID,    
         success :function(result) { 
        	 
        	 $("#associateSalaryIndDiv").empty(); 
             $("#associateSalaryIndDiv").append(result);
         },
    }); 
};

function closeNetPay(){
	$("#netPay").fadeOut();
	$(".comMask").fadeOut();
};
//Net payedit
function editNetpay(){
	$("#tableNetpay").find(".formControl").toggleClass("activeEdit");
	var x = document.getElementsByClassName("formControl"), i;
	for (i=0; i<x.length; i++) {
		if(x[i].readOnly) {
			x[i].readOnly = false;
		}else {
			x[i].readOnly = true;
		}
	}
	$(".editNetpay").hide();
	$(".saveNetpay").show();
}
function saveNetpay(){
	calculateGrossSalary();
	
	saveAdjustedPayheads();
}

function saveAdjustedPayheads(){
	if ($('#associatePayheadAdjust_form').validate(false, validationSettings)) {
		
		$("#tableNetpay").find(".formControl").toggleClass("activeEdit");
		var x = document.getElementsByClassName("formControl"), i;
		for (i=0; i<x.length; i++) {
			if(x[i].readOnly) {
				x[i].readOnly = false;
			}else {
				x[i].readOnly = true;
			}
		}
		$(".editNetpay").show();
		$(".saveNetpay").hide();
		
		var formData = getFormData1("associatePayheadAdjust_form");
		var psfUUID = $('#paySheetFrequencyUUID').val();
		var pmsUrl ='${SourceLeadWeb_pmsUrl}';
		$.ajax({
			 type : "POST",
			 data: formData,
		     processData: false,
		     contentType:false, 
			 url : pmsUrl+"/saveAssociatePayheadAdjustInd?"+tokenParamName+"="+tokenValue,
			 success : function(result) {
				 closeNetPay();
				 $(location).attr('href', 'employeePayrollList?paySheetFrequencyUUID='+psfUUID);
			 }
		});
	}
}

function calculateGrossSalary(){
	var grossTotal = 0.00;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	$('.earningPayheadTbady #earningPayheadAmount').each(function() {
		if($(this).val() != '' && floatRegex.test($(this).val())){
			grossTotal += parseFloat($(this).val());
		}
	});
	$('#earningPayheadAmountTotal').html(grossTotal.toFixed(2));
	$('#totalGrossPay').html($('#earningPayheadAmountTotal').html());
	$("input[name='grossPay']").val($('#totalGrossPay').html());
	$('#totalNetPay').html((parseFloat($('#earningPayheadAmountTotal').html()) - parseFloat($('#deductionPayheadAmountTotal').html())).toFixed(2));
	$("input[name='netPay']").val($('#totalNetPay').html());
}

function calculateNetSalary(){
	var netTotal = 0.00;
	var floatRegex = /^((\d+(\.\d *)?)|((\d*\.)?\d+))$/;
	$('.deductionPayheadTbady #deductionPayheadAmount').each(function() {
		if($(this).val() != '' && floatRegex.test($(this).val())){
			netTotal += parseFloat($(this).val());
		}
	});
	$('#deductionPayheadAmountTotal').html(netTotal.toFixed(2));
	$('#totalNetPay').html((parseFloat($('#earningPayheadAmountTotal').html()) - parseFloat($('#deductionPayheadAmountTotal').html())).toFixed(2));
	$("input[name='grossPay']").val($('#totalGrossPay').html());
	$("input[name='netPay']").val($('#totalNetPay').html());
}

function confirmPayroll() {
	$(".confirmPayroll, .comMask").fadeIn();
}
function closeCPR() {
	$(".confirmPayroll, .comMask").fadeOut();
}
</script>

<script>
function sentForApprove(approverStatus){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var totalCheckboxes = $('input:checkbox[name=empPayroll]').length;
	var numberOfChecked  = $('input:checkbox[name=empPayroll]:checked').length;
	if(totalCheckboxes > 0 && totalCheckboxes == numberOfChecked){
		alertPopUp();
	}else{
		var feqUUID = $("#paySheetFrequency").val();
		var excludeEmpArr = [];
		$("input:checkbox[name=empPayroll]:checked").each(function(){
			excludeEmpArr.push($(this).val());
		});
		$.ajax({
	        type : "POST",
	        url: pmsUrl+"/sentForApproveIndPayroll?approverStatus="+approverStatus+"&paySheetFrequencyUUID="+feqUUID+"&excludeEmpPayrollUUID="+excludeEmpArr,    
	         success :function(result) { 
	        	 $(location).attr('href', 'runIndianPayRoll?stab=payroll');
	         },
	    });
	}
}
</script>

<script>
function alertPopUp(){
	    var result = "";
		result = result +"	<div class='project_inn_mask'></div>"; 
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Excluding all Employees is illegal</p>";
		result = result + ' <div id="reasonForInvoiceDiv"/>';
		result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Ok" onclick="cancelTermination();"></b>';
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
}
function cancelTermination(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function cancelIndPayroll(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var feqUUID = $("#paySheetFrequency").val();
	$.ajax({
        type : "POST",
        url: pmsUrl+"/removePaysheetFrequencyFromSession?paySheetFrequencyUUID="+feqUUID,    
         success :function(result) { 
        	 $(location).attr('href', 'runIndianPayRoll?stab=payroll');
         },
    });
}
</script>


<script>
$('#approveTab , #cnfmPayrollBtn').click(function(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var totalCheckboxes = $('input:checkbox[name=empPayroll]').length;
	var numberOfChecked  = $('input:checkbox[name=empPayroll]:checked').length;
	if(totalCheckboxes > 0 && totalCheckboxes == numberOfChecked){
		alertPopUp();
	}else{
		var feqUUID = $("#paySheetFrequency").val();
		var excludeEmpArr = [];
		$("input:checkbox[name=empPayroll]:checked").each(function(){
			excludeEmpArr.push($(this).val());
		});
		$.ajax({
	        type : "GET",
	        url: pmsUrl+"/getApproveInfoIndPayroll?"+tokenParamName+"="+tokenValue+"&paySheetFrequencyUUID="+feqUUID+"&excludeEmpPayrollUUID="+excludeEmpArr,   
	         success :function(result) {
	        	 $("#reviewTab").removeClass("current");
	        	 $("#approveTab").addClass("current");
	        	 $("#reviewDiv").hide();
	        	 $("#approveDiv").remove();
	             $("#reviewAndApproveDiv").append(result);
	        	 
	         },
	    });
	}
});

$('#reviewTab').click(function(){
	 $("#reviewTab").addClass("current");
	 $("#approveTab").removeClass("current");
	 $("#reviewDiv").show();
	 $("#approveDiv").remove();
});
</script>

 <div class="modal" style="display: none;" id="bulkimportPOP"></div>
 <script>
 function bulkAdjustment(){
	 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	 $("#bulkimportPOP, .mask").fadeIn();
	 var feqUUID = $("#paySheetFrequency").val();
	 var excludeEmpArr = [];
		$("input:checkbox[name=empPayroll]:checked").each(function(){
			excludeEmpArr.push($(this).val());
		});
	 $.ajax({
	        type : "GET",
	        url: pmsUrl+"/bulkPayAdjustmentsForFrequencyInd?"+tokenParamName+"="+tokenValue+"&paySheetFrequencyUUID="+feqUUID+"&excludeEmpPayrollUUID="+excludeEmpArr,    
	         success :function(result) {
	        	 $("#bulkimportPOP").empty();
	             $("#bulkimportPOP").append(result);
	         },
	    });
 };
 function bulkAdjustmentClose(){
	 $("#bulkimportPOP, .mask").fadeOut();
	 $("#bulkimportPOP").empty();
 }
 </script>
  
<jsp:include page="../../masterFooter.jsp" />