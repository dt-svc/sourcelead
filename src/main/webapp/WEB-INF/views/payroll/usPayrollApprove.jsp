<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script type="text/javascript">
function calculateTotalPay(formData,confirmType,payPerionNum,payType){
			approvePayrollDiv();
		      $.post(sourceleadUrl+"/confirmPayroll?"+tokenParamName+"="+tokenValue+"&confirmType="+confirmType+"&payPerionNum="+payPerionNum+"&payType="+payType, formData, function(json) {
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
		    	  
		    	  var dedResult = "";
		    	   if(json.deduction != null){ 
		    		   for(var e in json.deduction){
			    		  dedResult = dedResult + '<tr>';
			    		  dedResult = dedResult + '<td>'+e+'</td>';
			    		  dedResult = dedResult + '<td>$ '+json.deduction[e].toFixed(2)+'</td>';
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
		    	
		    	  var contResult = "";
		    	   if(json.contribution != null){ 
		    		   for(var e in json.contribution){
			    		  contResult = contResult + '<tr>';
			    		  contResult = contResult + '<td>'+e+'</td>';
			    		  contResult = contResult + '<td>$ '+json.contribution[e].toFixed(2)+'</td>';
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
    <div class="timesheet_out" id="approveDiv" style="display:none">
    <div id="approvePayrollDivLoding"></div>
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
               							<td><i class="fa fa-clock-o paddingRight"></i><span id="totRegularHours">00.00</span></td>
               							<td>$ <span id="totRegularEarningsAmt">0.00</span></td>
               						</tr>
               						<tr>
               							<td>Over Time</td>
               							<td><i class="fa fa-clock-o paddingRight"></i><span id="totOverTimeHours">00.00</span></td>
               							<td>$ <span id="totOverTimeEarningAmt">$ 0.00</span></td>
               						</tr>
               					</tbody>
								<tfoot>
									<tr>
										<td><strong>Gross Pay</strong></td>
										<td>&nbsp;</td>
										<td><strong>$ <span id="grosspay">$ 0.00</span></strong></td>
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
               							<td>Advanced Earned Income Credit</td>
               							<td>$ <span id="aeic">0.00</span></td>
               						</tr>
               						<tr> 
               							<td>Conty Withholding</td>
               							<td>$ <span id="county">0.00</span></td>
               						</tr>
               						<tr>
               							<td>Local Tax</td>
               							<td>$ <span id="localTax">0.00</span></td>
               						</tr>
               						<tr>
               							<td>Total Employee Tax</td>
               							<td>$ <span id="totalTax">0.00</span></td>
               						</tr>
               						<tr>
               							<td><strong>Net Pay</strong></td>
               							<td><strong>$ <span id="netPay">0.00</span></strong></td>
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
               							<td>$ 2,063.31</td>
               						</tr>
               						<tr>
               							<td><strong>Total Net</strong></td>
               							<td><strong>$ 2,063.31</strong></td>
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
               						<td><strong>Employer Total Taxes</strong></td>
              						<td><strong>$ <span id="emplrTotalTaxes">0.00</span></strong></td>
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
            <li><a class="btn btn-default" onclick="cancelRedirect();">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
        </form:form>
      </div>
    </div>
  <!-- </section> -->
  <!-- Content Area Ends--> 

<script>
function cancelRedirect(){
	var status = getUrlParameter("approverStatus");
	if(status != null && status != "" && status != 'undefined'){
		if(status == "SENT"){
			window.location.href = sourceleadUrl + "/payrollList?&redirect=history";
		}else if(status == "DRAFT"){
			window.location.href = sourceleadUrl + "/payrollList?&redirect=draft";
		}
	}else{
		window.location.href = sourceleadUrl + "/payrollList";
	}
}

function getUrlParameter(paramName){
	var url = window.location.search.substring(1);
	var urlVars = url.split('&');
	var paramval = "";
	for (var i = 0; i < urlVars.length; i++){
		var values = urlVars[i].split('=');
		var param = values[0];
		if(param === paramName){
			paramval = values[1];
		}
	}
	return paramval;
}
</script>

<script type="text/javascript">
function approvePayroll(){
      var formData = $($('#approve_payroll_form')[0]).serialize();
      $("#approvePayrollDivLoding").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      $.post(sourceleadUrl+"/approvePayroll?"+tokenParamName+"="+tokenValue, formData, function(result) {
    	  $("#approvePayrollDivLoding").removeAttr('style');
    	  if (result.response === "INVALID_DATA") {
 		    showError(result.responseMessage);
 		} else if (result.response === "AWKWARD") {
 		    location.href = "/errorPage";
 		} else if (result.response === "SUCCESS") {
		   location.replace(sourceleadUrl+ "/payrollList?&redirect=history"); 
 		} else if (result.indexOf('Sign In') !== -1) {
 			location.replace(sourceleadUrl);
 		} else {
 		    showError("Operation failed, please try again");
 		}
      });   
    
}


function approvePayrollDiv(){
	$("#reviewPayrollDiv").hide();
	$("#approveDiv").show();
}
</script> 


 		