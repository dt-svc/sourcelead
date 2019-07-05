<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="tablelist-wrap" id="approveDiv">
      <div class="clearfix mt row mb">
				  <div class="clearfix col-md-8">
					  <div class="row">
					  <label class="col-md-2 text-muted">Total Earnings</label>
					  <div class="col-md-4 clearfix">
					  	<span class="paddingRight">&#8377;</span><fmt:formatNumber value="${earningTotal}" pattern="0.00"/>
					  </div>
				  </div>
		  		</div>
		  </div>
          <div class="clearfix approveTabelesStyles inlineBlock">
				<div class="col-md-4 mb">
					<h5>Earnings</h5>
					<div class="clearfix approveTabelesStyles-inner">
						<table class="table">
							<thead>
								<tr>
								<th>&nbsp;</th>
								<th>Amount</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="payheadErn" items="${orgLevelPayheads}">
									<c:if test="${payheadErn.type == 'Earnings' && payheadErn.totalForfrequencyLevel != '0' && payheadErn.totalForfrequencyLevel != '0.0' && payheadErn.totalForfrequencyLevel != '0.00' && payheadErn.totalForfrequencyLevel != '00.00'}">
										<tr>
											<td>${payheadErn.name}</td>
											<td><span class="paddingRight">&#8377;</span><fmt:formatNumber value="${payheadErn.totalForfrequencyLevel}" pattern="0.00"/></td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>Total</th>
									<td><span class="paddingRight">&#8377;</span><fmt:formatNumber value="${earningTotal}" pattern="0.00"/></td>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
				<div class="col-md-4 mb">
					<h5>Deductions</h5>
					<div class="clearfix approveTabelesStyles-inner">
						<table class="table">
							<thead>
								<tr>
								<th>&nbsp;</th>
								<th>Amount</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="payheadDed" items="${orgLevelPayheads}">
									<c:if test="${payheadDed.type == 'Deduction' && payheadDed.totalForfrequencyLevel != '0' && payheadDed.totalForfrequencyLevel != '0.0' && payheadDed.totalForfrequencyLevel != '0.00' && payheadDed.totalForfrequencyLevel != '00.00'}">
										<tr>
											<td>${payheadDed.name}</td>
											<td><span class="paddingRight">&#8377;</span><fmt:formatNumber value="${payheadDed.totalForfrequencyLevel}" pattern="0.00"/></td>
										</tr>
									</c:if>
								</c:forEach>
								<tr>
									<td><strong>Total Deductions</strong></td>
									<td><span class="paddingRight">&#8377;</span><fmt:formatNumber value="${deductionTotal}" pattern="0.00"/></td>
								</tr>
							</tbody>
						</table>
				</div>
				</div>
		  </div>
 		<div class="clearfix mt"></div>
        <nav class="clearfix">
          <ul class="pull-right mr10 list-unstyled list-inline">
          <c:if test="${freqResponse.approverStatus != 'APPROVED'}">
          	<li><a href="javascript:void(0)" class="btn btn-primary btnSuccess" onClick="sentForApprove();">Approve</a></li>
          </c:if>
           <li><a  class="btn btn-default"  onclick="cancelIndPayroll();">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
</div>



<script>
function sentForApprove(){
	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	var feqUUID = $("#paySheetFrequency").val();
	var excludeEmpArr = [];
	$("input:checkbox[name=empPayroll]:checked").each(function(){
		excludeEmpArr.push($(this).val());
	});
	$.ajax({
        type : "POST",
        url: pmsUrl+"/approveIndPayroll?"+tokenParamName+"="+tokenValue+"&paySheetFrequencyUUID="+feqUUID+"&excludeEmpPayrollUUID="+excludeEmpArr,    
         success :function(result) { 
        	 
        	 $(location).attr('href', 'runIndianPayRoll?stab=payroll');
         },
    });
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

