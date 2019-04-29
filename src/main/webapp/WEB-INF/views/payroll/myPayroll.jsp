<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script>
$(document).ready(function(){
	$("#myPayrollFormDiv").hide();
	$("#loansAdvancesDiv").hide();
	getMyPayrollList();
});
</script>

<script>
function payDiv(){
	$("#myPayrollDiv").show();
	$("#myPayrollFormDiv").hide();
	$("#loansAdvancesDiv").hide();
}

function formsDiv(){
	$("#myPayrollFormDiv").show();
	$("#myPayrollDiv").hide();
	$("#loansAdvancesDiv").hide();
	getUsMyPayrollForms();
}

function loansAdvances(){
	$("#loansAdvancesDiv").show();
	$("#myPayrollFormDiv").hide();
	$("#myPayrollDiv").hide();
	EmployeeLoansAndAdvances('outstanding');
	getLoansCountList();
}
</script>

<!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>My Forms</h2>
  </div>
   <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;MyForms</div>
</section>
 
   <div class="tabsMenu">
   	<ul class="list-unstyled uspay-myforms">
	   <li class="current"><a onclick="payDiv();" class="active">Pays</a></li>
	   <li><a onclick="formsDiv();">Forms</a></li>
	   <li><a onclick="loansAdvances();" class="active">Loans &amp; Advances</a></li>
	 </ul>
   </div> 
 
    <div class="clearfix"></div>
    <div class="timesheet_out" id="myPayrollDiv">
     
     <div class="tablelist-wrap">
      <div class="clearfix mt">
                  <div class="clearfix"></div>
                  <div class="clearfix">
                  	<div class="col-md-3">
                  		<div class="row">
                  			<label class="col-md-5"><strong>Total:</strong></label>
                  			<span class="col-md-7"><strong onClick="totalCountFun();" class="cursorPointer" id="totalNet">$ 0</strong></span>
                  		</div>
                  	</div>
                  	<div class="col-md-3">
                  		<div class="row">
                  			<label class="col-md-5"><strong>No. of Hours:</strong></label>
                  			<span class="col-md-7" id="totalHors"><strong>0.0</strong></span>
                  		</div>
                  	</div>
                  </div>
                 <div class="clearfix mb5"></div>
                 <div class="clearfix" id="totalCount" style="display:none;">
                 <div class="tableEqualHeight-pay">
               		<div class="col-md-3 mb">
               			<h5>Hours &amp; Earnings</h5>
               			<div class="clearfix tableStyles">
               				<table class="table-bordered table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Hours</th>
               						<th>Amount</th>
               						</tr>
               					</thead>
               					<tbody id="hoursDIV">
               						<tr>
               							<td>Regular</td>
               							<td><span id="regularHours"></span></td>
               							<td>$ <span id="regularAmount"> 0</span></td>
               						</tr>
               						<tr>
               							<td>Overtime</td>
               							<td><span id="overTimeHours"></span></td>
               							<td>$ <span id="overTimeHoursAmount">0</span></td>
               						</tr>
               					</tbody>
								<tfoot>
									 <tr>
										<td><strong>Total</strong></td>
										<td>&nbsp;</td>
										<td>$ <span id="totalHorsAmount">0</span></td>
									</tr>
								</tfoot>
               				</table>
               			</div>
               		</div>
               		<div class="col-md-3 mb">
               			<h5>Pre-Tax Deductions</h5>
               			<div class="clearfix tableStyles">
               				<table class="table-bordered table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Amount</th>
               						</tr>
               					</thead>
               					<tbody id="preTaxDeductioDIV">
               						<!-- <tr>
               							<td>Medical</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td>401(K)</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td><strong>Total</strong></td>
               							<td>100$</td>
               						</tr> -->
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-3 mb">
               			<h5>Post-Tax Deductions</h5>
               			<div class="clearfix tableStyles">
               				<table class="table-bordered table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Amount</th>
               						</tr>
               					</thead>
               					<tbody id="postTaxDeductioDIV">
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-3 mb">
               			<h5>Employee Taxes</h5>
               			<div class="clearfix tableStyles">
               				<table class="table-bordered table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Amount</th>
               						</tr>
               					</thead>
               					<tbody>
               						<tr>
               							<td>Federal Withholding</td>
               							<td><span id="federal">$ 0</span></td>
               						</tr>
               						<tr>
               							<td><abbr title="Federal Insurance Contributions Act ">(FICA)</abbr></td>
               							<td><span id="fica">$ 0</span></td>
               						</tr>
               						<tr>
               							<td>State Withholding</td>
               							<td><span id="stateWithHolding">$ 0</span></td>
               						</tr>
               						<tr>
               							<td>Medicare </td>
               							<td><span id="medicare">$ 0</span></td>
               						</tr>
               						 <tr>
               							<td>Advanced Earned Income Credit </td>
               							<td><span id="aeic">$ 0</span></td>
               						</tr>
               						<tr>
               							<td>Conty Withholding</td>
               							<td><span id="county">$ 0</span></td>
               						</tr>
               						<tr>
               							<td>Local Tax</td>
               							<td><span id="localTax">$ 0</span></td>
               						</tr> 
               						<tr>
               							<td><strong>Total</strong></td>
               							<td><span id="totalEmpTax">$ 0</span></td>
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-3 mb">
               			<h5>Employer Taxes</h5>
               			<div class="clearfix tableStyles">
               				<table class="table-bordered table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Amount</th>
               						</tr>
               					</thead>
               					<tbody>
               						 <tr>
									<td>FICA</td>
									<td><div class="tblminwidth-90">$ <span id="emplrFICA">0.00</span></div></td>
									</tr>
               						<tr>
									<td>Medicare</td>
									<td>$ <span id="emplrMedicare">0.00</span></td>
              						</tr>
               						<tr>
									<td>FUTA</td>
									<td>$ <span id="emplrFUTA">0.00</span></td>
              						</tr>
              						<tr>
									<td>SUTA</td>
									<td>$ <span id="emplrSUTA">0.00</span></td>
              						</tr>
               						<tr>
									<td><abbr title="Employer Railroad Unemployment Insurance Authority">RUIA</abbr></td>
									<td>$ <span id="emplrRUIA">0.00</span></td>
              						</tr>
               						<tr>
									<td>SUTA Surcharge Taxes</td>
									<td>$ <span id="emplrSurchargeSUTATax">0.00</span></td>
              						</tr>
               						<tr>
									<td>SDI</td>
              						<td>$ <span id="emplrSDI">0.00</span></td>
               						</tr>
               						<tr>
									<td>Transit Taxes</td>
              						<td>$ <span id="emplrTransitTaxes">0.00</span></td>
               						</tr>
               						<tr>
									<td>Workers Compensation Tax</td>
              						<td>$ <span id="emplrWyomingWC">0.00</span></td>
              						</tr>
               						<tr>
               						<td>Oregon Workers Compensation Tax</td>
              						<td>$ <span id="emplrOregonWC">0.00</span></td>
               						</tr>
               						<tr>
               						<td>Total Taxes</td>
              						<td>$ <span id="emplrTotalTaxes">0.00</span></td>
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
               		</div>
               		
               		<div class="tableEqualHeight-pay mt mb">
               		<div class="col-md-3 mb">
               			<h5>Employer Contributions</h5>
               			<div class="clearfix tableStyles">
               				<table class="table-bordered table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Amount</th>
               						</tr>
               					</thead>
               					<tbody id="contributionDIV">
               						<!-- <tr>
               							<td>401(R)</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td>Post Tax</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td><strong>Total</strong></td>
               							<td>100$</td>
               						</tr> -->
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-3 mb">
               			<h5>Pay Items</h5>
               			<div class="clearfix tableStyles">
               				<table class="table-bordered table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Amount</th>
               						</tr>
               					</thead>
               					<tbody id="payItemDIV">
               					<!-- 	<tr>
               							<td>401(R)</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td>Post Tax</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td><strong>Total</strong></td>
               							<td>100$</td>
               						</tr> -->
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-3 mb">
               			<h5>Loan and Advances</h5>
               			<div class="clearfix tableStyles">
               				<table class="table-bordered table">
               					<thead>
               						<tr>
               						<th>&nbsp;</th>
               						<th>Amount</th>
               						</tr>
               					</thead>
               					<tbody id="loanAdvDiv">
               						
               					</tbody>
               				</table>
               		</div>
               		</div>
               	
               </div></div>
                                                 
                 <div class="clearfix col-md-12 styleTables">
                 <div class="project_wrap_table datatable_filter mt40">
                 <div id="payrollDataDIV"></div>
                 </div>
                 </div>					
               <div class="clearfix"></div>
            </div>
      </div>
      
      
    </div>
 <jsp:include page="myPayrollForm.jsp" />
  <jsp:include page="loansAndAdvances.jsp" />
  </section>
  
  <script type="text/javascript">
  function totalCountFun() {
		$("#totalCount").toggle();
	}
	</script>

<script>
//project side bar tabs
$(document).ready(function() {
	payDiv();
    $(".uspay-myforms a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
//         var tab = $(this).attr("href");
//         $(".list-unstyled").not(tab).css("display", "none");
//         $(tab).fadeIn();
    });
});
</script>

<script>
function getMyPayrollList(){
	var totalNetPay = 0;
	var preDedTotal = 0;
	var postDedTotal = 0;
	var contrTotal = 0;
	var payItemTotal = 0;
	var loanAmountTotal = 0;
	var hrsTotalAmount = 0;
	$.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getMyPayrollList?"+tokenParamName+"="+tokenValue                 , 
	        success: function(result) {
	        var data = result.myPayroll;
	        
	        
	        
	        // Employee Taxes
	        		var json = result.confirmPayTotalSum;
	              $("#federal").text(json.federalWithholding.toFixed(2));
		    	  $("#fica").text(json.ficaWithholding.toFixed(2));
		    	  $("#stateWithholding").text(json.stateWithholding.toFixed(2));
		    	  $("#aeic").text(json.earnedIncCredit.toFixed(2));
		    	  $("#medicare").text(json.medicareWithholding.toFixed(2));
		    	  $("#county").text(json.countyWithholding.toFixed(2));
		    	  $("#localTax").text(json.localTax.toFixed(2));
		    	  $("#totalEmpTax").text(json.totalTax.toFixed(2));
		    	  
		   // Employer Taxes
		   
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
		    	  
		   // Hours       
		     if(result.hours != null && result.hours != 'undefined'){
		    	 $("#overTimeHours").text(result.hours.otHours);
			     $("#regularAmount").text(result.hours.regularTimeAmount);
			     $("#overTimeHoursAmount").text(result.hours.overTimeAmount);
			     $("#totalHors").text(result.hours.totalHours+" Hrs");
		     }else{
		    	 $("#overTimeHours").text("0.00");
			     $("#regularAmount").text("0.00");
			     $("#overTimeHoursAmount").text("0.00");
			     $("#totalHors").text("0.00 Hrs");
		     }
		     
		    
	        
	        // Pre Deductions
	        var preDed = "";
	        for(var e in result.preDed){
	        	preDedTotal = preDedTotal + result.preDed[e];
	 	        preDed = preDed + '<tr>';
	 	        preDed = preDed + '<td>'+e+'</td>';
	 	        preDed = preDed + '<td>$ '+result.preDed[e]+'</td>';
	 	        preDed = preDed + '</tr>';
	        }
	        preDed = preDed + '<tr>';
	        preDed = preDed + '<td><strong>Total</strong></td>';
	        preDed = preDed + '<td>$ '+preDedTotal+'</td>';
	        preDed = preDed + '</tr>';
	        $("#preTaxDeductioDIV").empty();
	        $("#preTaxDeductioDIV").append(preDed);
	        
	        // Post Deductions
	        var postDed = "";
	        for(var e in result.postDed){
	        	postDedTotal = postDedTotal + result.postDed[e];
	        	postDed = postDed + '<tr>';
	        	postDed = postDed + '<td>'+e+'</td>';
	        	postDed = postDed + '<td>$ '+result.postDed[e]+'</td>';
	        	postDed = postDed + '</tr>';
	        }
	        postDed = postDed + '<tr>';
	        postDed = postDed + '<td><strong>Total</strong></td>';
	        postDed = postDed + '<td>$ '+postDedTotal+'</td>';
	        postDed = postDed + '</tr>';
	        $("#postTaxDeductioDIV").empty();
	        $("#postTaxDeductioDIV").append(postDed);
	        
	        // Contributions
	         var contr = "";
	         for(var e in result.contribution){
	        	 contrTotal = contrTotal + result.contribution[e];
	        	 contr = contr + '<tr>';
	        	 contr = contr + '<td>'+e+'</td>';
	        	 contr = contr + '<td>$ '+result.contribution[e]+'</td>';
	        	 contr = contr + '</tr>';
	         }
	         contr = contr + '<tr>';
	         contr = contr + '<td><strong>Total</strong></td>';
	         contr = contr + '<td>$ '+contrTotal+'</td>';
	         contr = contr + '</tr>';
	         $("#contributionDIV").empty();
	         $("#contributionDIV").append(contr);
	         
	         // PayItem 
	         
	         var payItem = "";
	         for(var e in result.payItem){
	        	 payItemTotal = payItemTotal + result.payItem[e];
	        	 payItem = payItem + '<tr>';
	        	 payItem = payItem + '<td>'+e+'</td>';
	        	 payItem = payItem + '<td>$ '+result.payItem[e]+'</td>';
	        	 payItem = payItem + '</tr>';
	         }
	         payItem = payItem + '<tr>';
	         payItem = payItem + '<td><strong>Total</strong></td>';
	         payItem = payItem + '<td>$ '+payItemTotal+'</td>';
	         payItem = payItem + '</tr>';
	         $("#payItemDIV").empty();
	         $("#payItemDIV").append(payItem);
	         
	         
	         //Loan and Advance
	         var loan = "";
	         if(result.loanAdvance != null && result.loanAdvance != '' && result.loanAdvance != 'undefined'){
	         for (var i=0; i< result.loanAdvance.length; i++){
	             var ln = result.loanAdvance[i];
	        	 loan = loan + '<tr>';
	        	 loan = loan + '<td>'+ln.loanAndAdvName+'</td>';
	        	 loan = loan + '<td>$ '+ln.loanAmount+'</td>';
	        	 loan = loan + '</tr>';
	         }
	         loan = loan + '<tr>';
	         loan = loan + '<td><strong>Total</strong></td>';
	         loan = loan + '<td>$ '+result.loanAmountTotal+'</td>';
	         loan = loan + '</tr>';
	         }
	         $("#loanAdvDiv").empty();
	         $("#loanAdvDiv").append(loan);
	         
	        
	        var result = "";
	        result = result +'<table id="myPayrollTableDiv" class="display" cellspacing="0" width="100%" >';
	        result = result +'<thead>';
	        result = result +'<tr>';
	        result = result +'<th>Pay Period</th>';
	        result = result +'<th>Pay Date</th>';
	        result = result +'<th>Account Type</th>';
	        result = result +'<th>Gross Pay</th>';
	        result = result +'<th>Taxes</th>';
	        result = result +'<th>Deductions</th>';
	        result = result +'<th>Net Pay</th>';
	        result = result +'<th>Action</th>';
	        result = result +'</tr>';
	        result = result +'</thead>';
	        result = result +'<tbody>';
	        if(data != null && data.length >0){
	        	for(var i =0 ; i<data.length;i++){
	        	var json = data[i];
	        	/* totalNetPay = parseFlaot(totalNetPay) + parseFloat(json.netPay); */
	        	totalNetPay = totalNetPay + json.netPay; 
	        	result = result +'<tr>';
	 	        result = result +'<td>';
	 	        result = result +'<div class="tblminwidth-100 text-center">';
	 	        result = result +'<div class="clearfix">'+json.payPeriodStartDate+'</div>';
	 	        result = result +'<div class="clearfix">'+json.payPeriodEndDate+'</div>';
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'<td>';
	 	        result = result +'	<div class="tblminwidth-125">';
	 	        result = result +'<div class="clearfix">'+json.payDate+'</div>';
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'<td>';
	 	        result = result +'<div class="tblminwidth-125">';
	 	        if(json.accountType != null){
	 	        	result = result +'<div>'+json.accountType+'</div>';	
	 	        }else{
	 	        	result = result +'<div>--</div>';
	 	        }
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'<td>';
	 	        result = result +'<div class="tblminwidth-50">';
	 	        result = result +'<div>$ '+json.grossPay+'</div>';
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'<td>';
	 	        result = result +'<div class="tblminwidth-50">';
	 	        result = result +'<div>$ '+json.taxes+'</div>';
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'<td>';
	 	        result = result +'<div class="tblminwidth-50">';
	 	        result = result +'<div>$ '+json.deductions+'</div>';
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'<td>';
	 	        result = result +'<div class="tblminwidth-50">';
	 	        result = result +'<div>$ '+json.netPay+'</div>';
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'<td>';
	 	        result = result +'<div class="tblminwidth-30">';
	 	        result = result +'<a href="#" class="anchorBlack" title="View"><i class="fa fa-eye" onclick="payAdjustment(\''+json.payrollUUID+'\',\'APPROVED\');"></i></a>';
	 	        if(json.enablePayslip == true){
		 	        result = result +'<a download href="'+json.paySlipFileURL+'" class="anchorBlack ml5" title="Download"><i class="fa fa-download"></i></a>';
	
	 	        }
	 	        
	 	        result = result +'</div>';
	 	        result = result +'</td>';
	 	        result = result +'</tr>';
	 	        
	        	}
	        	$("#totalNet").text('$ '+totalNetPay.toFixed(2));
	        }
	       
	        result = result +'</tbody>';
	        result = result +'</table>';	
	        $("#payrollDataDIV").empty();
	        $("#payrollDataDIV").append(result);
	        mayPayrollPagination();
	        
	        
	       
			
	        },
	    });
	
}
</script>


<script>
function mayPayrollPagination(){ 
    $('#myPayrollTableDiv').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#myPayrollTableDiv").wrap("<div class='overflow_Auto width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script>

<jsp:include page="./viewPayRollSummary.jsp" /> 
<jsp:include page="../masterFooter.jsp" />