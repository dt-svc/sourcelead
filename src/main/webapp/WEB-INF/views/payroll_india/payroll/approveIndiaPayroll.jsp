<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="tablelist-wrap" id="approveDiv">
      <div class="clearfix mt row mb">
				  <div class="clearfix col-md-8">
					  <div class="row">
					  <label class="col-md-2 text-muted">Total Earnings</label>
					  <div class="col-md-4 clearfix">
					  <input type="text" class="form-control input-sm" placeholder="Earnings">
					  </div>
				  </div>
		  		</div>
		  </div>
          <div class="clearfix approveTabelesStyles inlineBlock">
				<div class="col-md-4 mb">
					<h5>Earnings</h5>
					<div class="clearfix approveTabelesStyles-inner">
						<table class="table">
							<tbody>
								<tr>
									<td>Basic Pay</td>
									<td><span class="paddingRight">&#8377;</span>2,700.00</td>
									</tr>
									<tr>
									<td>House Rent</td>
										<td><span class="paddingRight">&#8377;</span>2,700.00</td>
									</tr>
							</tbody>
							<tfoot>
								<tr>
									<th>Total</th>
									<td><span class="paddingRight">&#8377;</span>2,700.00</td>
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
								<tr>
									<td>Medical</td>
									<td><span class="paddingRight">&#8377;</span>150</td>
								</tr>
								<tr>
									<td>401(K)</td>
									<td><span class="paddingRight">&#8377;</span>108</td>
								</tr>
								<tr>
									<td>401(K) Loan Repay</td>
									<td><span class="paddingRight">&#8377;</span>50</td>
								</tr>
								<tr>
									<td><strong>Total Deductions</strong></td>
									<td><span class="paddingRight">&#8377;</span>308</td>
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
            <li><a href="javascript:void(0)" class="btn btn-default" onclick="window.location='/app/runIndianPayRoll?stab=payroll'">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
</div>



<script>
function sentForApprove(){
	var feqUUID = $("#paySheetFrequency").val();
	$.ajax({
        type : "POST",
        url: sourceleadUrl+"/approveIndPayroll?"+tokenParamName+"="+tokenValue+"&paySheetFrequencyUUID="+feqUUID,    
         success :function(result) { 
        	 
        	 $(location).attr('href', 'runIndianPayRoll?stab=payroll');
         },
    });
}
</script>


