<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
function calculateTotalPay(formData,confirmType){
		      $.post(sourceleadUrl+"/confirmPayroll?"+tokenParamName+"="+tokenValue+"&confirmType="+confirmType, formData, function(json) {
		    	  $("#totRegularHours").text(json.regularHours.toFixed(2));
		    	  $("#totRegularEarningsAmt").text(json.regularHoursAmount.toFixed(2));
		    	  $("#totOverTimeHours").text(json.overTimeHours.toFixed(2));
		    	  $("#totOverTimeEarningAmt").text(json.overTimeHoursAmount.toFixed(2));
		    	  $("#grosspay").text(json.totalEarnings.toFixed(2));
		    	  // setting Employee Taxes
		    	  $("#federal").text(json.federalWithholding.toFixed(2));
		    	  $("#fica").text(json.ficaWithholding.toFixed(2));
		    	  $("#stateWithholding").text(json.stateWithholding.toFixed(2));
		    	  $("#aeic").text(json.earnedIncCredit.toFixed(2));
		    	  $("#medicare").text(json.medicareWithholding.toFixed(2));
		    	  $("#county").text(json.countyWithholding.toFixed(2));
		    	  $("#localTax").text(json.localTax.toFixed(2));
		    	  $("#totalTax").text(json.totalTax.toFixed(2));
		    	  $("#netPay").text(json.netPay.toFixed(2));
		    	  // setting employer taxes
		    	  
		    	  $("#emplrFICA").text(json.employerFICA.toFixed(2));
		    	  $("#emplrMedicare").text(json.employerMedicare.toFixed(2))
		    	  $("#emplrFUTA").text(json.employerFUTA.toFixed(2));
		    	  $("#emplrSUTA").text(json.employerSUTA.toFixed(2));
		    	  $("#emplrRUIA").text(json.employerRUIA.toFixed(2));
		    	  $("#emplrSurchargeSUTATax").text(json.employerSutaSurchargeTax.toFixed(2));
		    	  $("#emplrSDI").text(json.employerSDI.toFixed(2));
		    	  $("#emplrTransitTaxes").text(json.employerTransitTaxes.toFixed(2));
		    	  $("#emplrWyomingWC").text(json.employerWyomingWcTax.toFixed(2));
		    	  $("#emplrOregonWC").text(json.employerOregonWcTax.toFixed(2));
		    	  $("#emplrTotalTaxes").text(json.employerTotalPay.toFixed(2));
				  $("#pfUUID").val(json.pfUUID); 
				  $("#payrollIds").val(json.payrollIds);
				  $("#pid").val(json.pid);
				  $("#confirmType").val(json.confirmType); 
				  $("#ppn").val(json.ppn); 
		    	  
		    	  var dedJson = json.deduction;
		    	  var dedResult = "";
		    	   if(dedJson != null && dedJson.length >0){ 
		    		  for (var i=0; i<dedJson.length; i++){
			    		  var ded = dedJson[i];
			    		  dedResult = dedResult + '<tr>';
			    		  dedResult = dedResult + '<td>'+ded.deductionType+'</td>';
			    		  dedResult = dedResult + '<td>$ '+ded.amount.toFixed(2)+'</td>';
			    		  dedResult = dedResult + '</tr>';
			    	  }
		    	  } else{
		    		  dedResult = dedResult + '<tr>';
		    		  dedResult = dedResult + '<td>&nbsp</td>';
		    		  dedResult = dedResult + '<td>$ 0.00</td>';
		    		  dedResult = dedResult + '</tr>';
		    	  }
		    	$("#deductionDIV").empty();
		    	$("#deductionDIV").append(dedResult);
		    	
		    	 var contJson = json.contribution;
		    	  var contResult = "";
		    	   if(contJson != null && contJson.length >0){ 
		    		  for (var i=0; i<contJson.length; i++){
			    		  var cont = contJson[i];
			    		  contResult = contResult + '<tr>';
			    		  contResult = contResult + '<td>'+cont.contributionType+'</td>';
			    		  contResult = contResult + '<td>$ '+cont.contributionAmount.toFixed(2)+'</td>';
			    		  contResult = contResult + '</tr>';
			    	  }
		    	  } else{
		    		  contResult = contResult + '<tr>';
		    		  contResult = contResult + '<td>&nbsp</td>';
		    		  contResult = contResult + '<td>$ 0.00</td>';
		    		  contResult = contResult + '</tr>';
		    	  }
		    	$("#contributionDIV").empty();
		    	$("#contributionDIV").append(contResult);
		      });   
	
}
</script>
    
    
    <div class="clearfix"></div>
    <div class="timesheet_out" id="approveDiv">
      <div class="clearfix mt">
      <form:form id="approve_payroll_form" name="approve_payroll_form" method="post">
               <div class="clearfix approveTabelesStyles">
               		<div class="col-md-4 mb">
               			<h5>Hours &amp; Earnings</h5>
               			<div class="clearfix approveTabelesStyles-inner">
               			<input type="hidden" name ="pfUUID" id="pfUUID"/>
               			<input type="hidden" name ="pid" id="pid"/>
               			<input type="hidden" name ="payrollIds" id="payrollIds"/>
               			<input type="hidden" name ="confirmType" id="confirmType"/>
               			<input type="hidden" name ="ppn" id="ppn"/>
               				<table class="table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Hours</th>
               						<th>Earnings</th>
               						</tr>
               					</thead>
               					<tbody>
               						<tr>
               							<td>Regular</td>
               							<td><span id="totRegularHours">00.00</span></td>
               							<td>$ <span id="totRegularEarningsAmt">0.00</span></td>
               						</tr>
               						<tr>
               							<td>Over Time</td>
               							<td><span id="totOverTimeHours">00.00</span></td>
               							<td>$ <span id="totOverTimeEarningAmt">$ 0.00</span></td>
               						</tr>
               					</tbody>
								<tfoot>
									<tr>
										<td><strong>Gross Pay</strong></td>
										<td>&nbsp;</td>
										<td>$ <span id="grosspay">$ 0.00</span></td>
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
               					<tbody id="deductionDIV">
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-4 mb">
               			<h5>Employee Taxes</h5>
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
               							<td>Federal Withholding</td>
               							<td>$ <span id="federal">0.00</span><td>
               						</tr>
               						<tr>
               						 
               							<td>Federal Insurance Contributions Act (FICA)</td>
               							<td><div class="tblminwidth-90">$ <span id="fica">0.00</span></div></td>
               						</tr>
               						<tr>
               							<td>State Withholding</td>
               							<td>$ <span id="stateWithholding">0.00</span></td>
               						</tr>
               						<tr>
               						<td>Medicare</td>
               							<td>$ <span id="medicare">0.00</span></td>
               						</tr>
               						<tr>
               							<td><strong>Advanced Earned Income Credit</strong></td>
               							<td>$ <span id="aeic">0.00</span></td>
               						</tr>
               						<tr> 
               							<td><strong>Conty Withholding</strong></td>
               							<td>$ <span id="county">0.00</span></td>
               						</tr>
               						<tr>
               							<td><strong>Local Tax</strong></td>
               							<td>$ <span id="localTax">0.00</span></td>
               						</tr>
               						<tr>
               							<td><strong>Total Employee Tax	100$</strong></td>
               							<td>$ <span id="totalTax">0.00</span></td>
               						</tr>
               						<tr>
               							<td><strong>Net Pay</strong></td>
               							<td>$ <span id="netPay">0.00</span></td>
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
		  </div>
               		<div class="clearfix approveTabelesStyles">
               		<div class="col-md-4 mb">
               			<h5>Direct Deposit / Check Details</h5>
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
               							<td colspan="2"><div class="text-right">No Direct Deposits</div></td>
               						</tr>
               						<tr>
               							<td>Live Check</td>
               							<td>$2,063.31</td>
               						</tr>
               						<tr>
               							<td><strong>Total Net</strong></td>
               							<td>$2,063.31</td>
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-4 mb">
               			<h5>Employer Contributions</h5>
               			<div class="clearfix approveTabelesStyles-inner">
               				<table class="table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Amount</th>
               						</tr>
               					</thead>
               					<tbody id="contributionDIV">
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-4 mb">
               			<h5>Employer Taxes</h5>
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
									<td>Employer FICA </td>
									<td><div class="tblminwidth-90">$ <span id="emplrFICA">0.00</span></div></td>
									</tr>
               						<tr>
									<td>Employer Medicare</td>
									<td>$ <span id="emplrMedicare">0.00</span></td>
              						</tr>
               						<tr>
									<td>Employer FUTA</td>
									<td>$ <span id="emplrFUTA">0.00</span></td>
              						</tr>
              						<tr>
									<td>Employer SUTA</td>
									<td>$ <span id="emplrSUTA">0.00</span></td>
              						</tr>
               						<tr>
									<td>Employer Railroad Unemployment Insurance Authority (RUIA)</td>
									<td>$ <span id="emplrRUIA">0.00</span></td>
              						</tr>
               						<tr>
									<td>Employer SUTA Surcharge Taxes</td>
									<td>$ <span id="emplrSurchargeSUTATax">0.00</span></td>
              						</tr>
               						<tr>
									<td>Employer SDI</td>
              						<td>$ <span id="emplrSDI">0.00</span></td>
               						</tr>
               						<tr>
									<td>Employer Transit Taxes</td>
              						<td>$ <span id="emplrTransitTaxes">0.00</span></td>
               						</tr>
               						<tr>
									<td>Employer Wyoming Workers Compensation Tax (WC)</td>
              						<td>$ <span id="emplrWyomingWC">0.00</span></td>
              						</tr>
               						<tr>
               						<td>Employer Oregon Workers Compensation Tax WC)</td>
              						<td>$ <span id="emplrOregonWC">0.00</span></td>
               						</tr>
               						<tr>
               						<td>Employer Total Taxes</td>
              						<td>$ <span id="emplrTotalTaxes">0.00</span></td>
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
               </div>
 		<div class="clearfix mt"></div>
        <nav class="clearfix">
          <ul class="pull-right mr10 list-unstyled list-inline">
            <li><a href="javascript:void(0)" class="btn btn-primary btnSuccess" onClick="approvePayroll();">Approve</a></li>
            <li><a href="javascript:void(0)" class="btn btn-default" onClick="rejectModal();">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
        </form:form>
      </div>
    </div>
  <!-- </section> -->
  <!-- Content Area Ends--> 



<script type="text/javascript">
function approvePayroll(){
      var formData = $($('#approve_payroll_form')[0]).serialize();
      $.post(sourceleadUrl+"/approvePayroll?"+tokenParamName+"="+tokenValue, formData, function(result) {
  		if (result.response === "INVALID_DATA") {
 		    showError(result.responseMessage);
 		} else if (result.response === "AWKWARD") {
 		    location.href = "/errorPage";
 		} else if (result.response === "SUCCESS") {
		   location.replace(sourceleadUrl+ "/payrollList"); 
 		} else if (result.indexOf('Sign In') !== -1) {
 			location.replace(sourceleadUrl);
 		} else {
 		    showError("Operation failed, please try again");
 		}
      });   
    
}
</script> 


 		