<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
  
  <style type="text/css">
@media print{
.col-md-10, .payslip-employeedetails {
	width:100%; background: #f60;
}
.col-md-6 {
width: 50%; float: left;position: relative; min-height: 1px; padding-right: 15px; padding-left: 15px;
}
.col-md-7 {
	width: 58.3%; float: left;
}
.table {
width: 100%; max-width: 100%; margin-bottom: 20px;
}
.col-md-5 {
	width: 41.6%; float: left;
}
.padding_right {
padding-left: 5px !important;
}
.paddingLeft {
padding-left: 5px !important;
}
.padding_right { padding-right: 0 !important;}
.padding_left { padding-left: 0 !important; }
.main-header, aside, .content-header, .footer-area, .optionalIcons {
display: none;
}
.contentareaInner-styles {
border-top: none; padding-top: 0;
}
.content-wraper {
padding-top: 0; 
}
#payslipPreview {
padding: 20px;
}
.media-object {
display: inline-block;
}
.payslipTable table td, .payslipTable table th  {
	border-collapse:border; border:1px solid #d6d6d6 !important;
}
.payslipTable table thead {
	border:1px solid #6C7686 !important; background: #6C7686 !important;  -webkit-print-color-adjust: exact;
}
.payslipTable table thead th {
	  color: #fff !important; padding: 3px 10px !important; background: #6C7686 !important; -webkit-print-color-adjust: exact;
}
.payslipTable table tbody th, .payslipTable table tbody td, .payslipTable table tfoot td, .payslipTable table tfoot th	 {
	padding: 3px 10px  !important; vertical-align: middle;
}
.payslipTable table tbody td, .payslipTable table tfoot td  {
	text-align: right !important;
}
.payslipTable table tfoot {
	background-color: #ededed; color: #333; font-weight: 600;  -webkit-print-color-adjust: exact;
}
.media-body, .media-left, .media-right { display: table-cell; vertical-align: top; }
.media-object {display: block; }

.media-object img { width: 60px; border: 1px solid red; display: inline-block;}
.img {
    -webkit-print-color-adjust: exact;
}
@page { size: auto; margin: 0mm; }
}
@media print {
	body {
/* 	-webkit-print-color-adjust: economy; */
-webkit-print-color-adjust: exact;

/* Global values */
/* -webkit-print-color-adjust: inherit; */
/* -webkit-print-color-adjust: initial; */
/* -webkit-print-color-adjust: unset; */
}
}

.fontSize18 {
	font-size: 18px;
}
.fontSize17 {
	font-size: 17px;
}
</style>
   <!-- Content Area -->
<section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payslip Preview</h2>
      </div>
            <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}"> Home </a>&raquo;<a href="${SourceLeadWeb_pmsUrl}/myPayrollInd"> My Payroll </a> &raquo;  View Payslip </div>
    </section>
		    <div class="contentareaInner-styles clearfix payslip-generation" id="payslipPreview">
		        <div class="row">
		        <div class="col-md-10 col-md-offset-1">
					<section class="payslip-companydetails">
						<div class="clearfix">
								<div class="media">
									<div class="row">
								<div class="col-md-10">
								<div class="media-left">
								 <c:if test="${ppsi.companyLogo eq 'Y'}">
									<c:if test="${!empty copanyImage}">
									<img src="${copanyImage}" class="media-object" style="width:60px" alt="Img">
									</c:if>
									<c:if test="${empty copanyImage}">
									<img src="/resources/images/office-image.jpg" class="media-object" style="width:60px" alt="Img">
									</c:if>
								 </c:if>	
								</div>
								<div class="media-body">
								   <c:if test="${ppsi.companyName eq 'Y'}">
									   <h4 class="media-heading">${companyName}</h4>
								   </c:if>
								 <span class="text-muted">
								    <c:if test="${ppsi.companyAddress eq 'Y'}">
								     <p class="col-md-8 padding_left mt2 clearfix lineHeight mb0">${companyAddress}</p>
								    </c:if>
								 </span>
								</div>
								</div>
									<div class="col-md-2">
								<ul class="pull-right list-unstyled list-inline noPad">
									<li>
										<a href="${SourceLeadWeb_pmsUrl}/getDownloadPayslipInd?indEmpPayrollUUID=${empPayrollUUID}" class="downloadIC" title="Download"></a>
									</li>
									<!-- <li>
										<a href="javascript:void(0)" title="Print" class="printIC" onclick="window.print();"></a>
									</li> -->
								</ul>
									</div>
										</div>
								</div>
								<div class="clerfix"></div>
						</div>
					</section>
					<div class="clearfix"></div>
					<section class="payslip-employeedetails">
								<div class="clearfix text-center mt10">
									<h4>Pay Slip for : <span>${payslipDate}</span></h4>
									<c:if test="${ppsi.employeeName eq 'Y'}">
									   <h4 class="text-muted mb mt5">${candidate.fullName}</h4>
									</c:if>
								</div>
								<div class="mt10 clearfix"></div>
						<div class="row">	
							<div class="col-md-6">
									<ul class="list-unstyled clearfix lineHeight row">
									
										<c:if test="${!empty employeeId}">
										   <c:if test="${ppsi.employeeId eq 'Y'}">
											  <li>
												<label class="col-md-6 padding_right">Employee Number :</label>
													<div class="col-md-6 paddingLeft">${employeeId}</div>
												<div class="clearfix"></div>
											  </li>
										   </c:if>
										</c:if>
										
										<c:if test="${!empty candidate.designationType}">
										  <c:if test="${ppsi.employeeType eq 'Y'}">
											  <li>
												<label class="col-md-6 padding_right">Employee Type :</label>
													<div class="col-md-6 paddingLeft">${candidate.designationType}</div>
												<div class="clearfix"></div>
											  </li>
										  </c:if>
										</c:if>
										
										<c:if test="${!empty candidate.designation}">
										<c:if test="${ppsi.employeeDesignation eq 'Y'}">
										   <li>
											<label class="col-md-6 padding_right">Designation :</label>
												<div class="col-md-6 paddingLeft">${candidate.designation}</div>
											<div class="clearfix"></div>
										   </li>
										  </c:if>
										</c:if>
										
										<c:if test="${ppsi.employeeBankDetails eq 'Y'}">
										  <c:if test="${!empty paymentAccount}">
											<li>
												<label class="col-md-6 padding_right">Bank Details :</label>
													<div class="col-md-6 paddingLeft">${paymentAccount.accountNumber}, ${paymentAccount.bankName}</div>
												<div class="clearfix"></div>
											</li>
										  </c:if>
										</c:if>
										
										<c:if test="${ppsi.employeeLocation eq 'Y'}">
											<li>
												<label class="col-md-6 padding_right">Location :</label>
													<div class="col-md-6 paddingLeft">
													<c:if test="${!empty candidate.country}">
													${candidate.country}
													</c:if>
													<c:if test="${!empty  candidate.state}">
													, ${candidate.state}
													</c:if>
													<c:if test="${!empty  candidate.city}">
													, ${candidate.city}
													</c:if>
													</div>
												<div class="clearfix"></div>
											</li>
										</c:if>
										
										<c:if test="${ppsi.employeeContactDetails eq 'Y'}">
										    <li>
												<label class="col-md-6 padding_right">Contact Details :</label>
													<div class="col-md-6 paddingLeft">${candidate.mobilePhone} </br> ${candidate.email}</div>
												<div class="clearfix"></div>
											</li>
										</c:if>
										
										<c:if test="${!empty joinDate}">
										<c:if test="${ppsi.employeeJoinDate eq 'Y'}">
										   <li>
											 <label class="col-md-6 padding_right">Date of Joining :</label>
												<div class="col-md-6 paddingLeft">${joinDate}</div>
											 <div class="clearfix"></div>
										    </li>
										 </c:if>
										</c:if>
									</ul>
								
							</div>
						<div class="col-md-6">
									<ul class="list-unstyled clearfix lineHeight row">
										<c:if test="${!empty candidate.panNumber}">
										<c:if test="${ppsi.employeePanNumber eq 'Y'}">
										   <li>
											<label class="col-md-6 padding_right">Income Tax Number <small>(PAN)</small> :</label>
												<div class="col-md-6 paddingLeft">${candidate.panNumber}</div>
											<div class="clearfix"></div>
										   </li>
										  </c:if>
										</c:if>
										<c:if test="${pfEsi.eligibleForPf eq 'Y'}">
										    <c:if test="${ppsi.employeeUanNumber eq 'Y'}">
											  <li>
												<label class="col-md-6 padding_right">Universal Account Number (UAN) :</label>
													<div class="col-md-6 paddingLeft">${pfEsi.uanNumber}</div>
												<div class="clearfix"></div>
											  </li>
									        </c:if>		
											<c:if test="${ppsi.employeePfNumber eq 'Y'}">
											   <li>
												<label class="col-md-6 padding_right">PF Account Number :</label>
													<div class="col-md-6 paddingLeft">${pfEsi.pfNumber}</div>
												<div class="clearfix"></div>
											   </li>
											</c:if>
										</c:if>
										<c:if test="${pfEsi.eligibleForEsi eq 'Y'}">
										<c:if test="${ppsi.employeeEsiNumber eq 'Y'}">
											<li>
											<label class="col-md-6 padding_right">ESI Number :</label>
												<div class="col-md-6 paddingLeft">${pfEsi.esinumber}</div>
											<div class="clearfix"></div>
											</li>
										</c:if>
										</c:if>
										<c:if test="${ppsi.leaveAttended eq 'Y'}">
										   <li>
											<label class="col-md-6 padding_right">Attended :</label>
												<div class="col-md-6 paddingLeft"><fmt:formatNumber>${indPayrollDTO.noOfDays}</fmt:formatNumber> Days</div>
											<div class="clearfix"></div>
										   </li>
										</c:if>
										
										<c:if test="${ppsi.leaveLeaves eq 'Y'}">
										   <li>
											<label class="col-md-6 padding_right">Leaves :</label>
												<div class="col-md-6 paddingLeft"><fmt:formatNumber>${indPayrollDTO.noOfLeaves}</fmt:formatNumber></div>
											<div class="clearfix"></div>
										   </li>
										</c:if>
										<c:if test="${ppsi.leaveLop eq 'Y' && !empty indEmployeePayroll.lopAmount}">
										  <li>
											<label class="col-md-6 padding_right">LOP :</label>
												<div class="col-md-6 paddingLeft">${indEmployeePayroll.lopAmount}</div>
											<div class="clearfix"></div>
										   </li>
										</c:if>
										
										<c:if test="${ppsi.regularHours eq 'Y'}">
										  <li>
											<label class="col-md-6 padding_right">Regular hours :</label>
												<div class="col-md-6 paddingLeft">${indEmployeePayroll.regularHours}</div>
											<div class="clearfix"></div>
										   </li>
										</c:if>
										
										
										<c:if test="${ppsi.overtimeHours eq 'Y'}">
										  <li>
											<label class="col-md-6 padding_right">Overtime hours :</label>
												<div class="col-md-6 paddingLeft">${indEmployeePayroll.overTimeHours}</div>
											<div class="clearfix"></div>
										   </li>
										</c:if>
									</ul>
							   </div>
							</div>
							<div class="clearfix"></div>
					</section>
		     <section>
		     	<div class="payslipTable">
		     	<div class="row">
		     	<div class="col-md-7 padding_right">
		     	<table class="table table1">
		     		 
		     		<thead>
						<tr>
		     			<th>Earnings</th><th>Amount</th><th>Gross Salary</th>
		     			</tr>
		     		</thead>
		     			<tbody>
		     			<c:set var="earningTotal" value="${0.00}"/>
		     			<c:forEach var="earning" items="${earningPayhead}" varStatus="loop1">
		     			  <c:if test="${earning.taxStatus eq 'Taxable'}">
		     			   <tr>
			     			   <th>${earning.payrollPayheadName}</th>
			     			   <td>${earning.monthly}</td>
			     			   <td>${earning.monthly}</td>
		     			   </tr>
		     			   <c:set var="earningTotal" value="${earningTotal + earning.monthly}"/>
		     			   </c:if>
		     			</c:forEach>
		     		    </tbody>
		     		    <tfoot>
		     		    <tr class="fontSize17"><th><strong>Gross Salary</strong></th><td>${earningTotal}</td><td>${earningTotal}</td></tr>
		     		    </tfoot>
		     	</table>
		     	
		     	<table class="table table1">
		     		 
		     		<thead>
						<tr>
		     			<th>Employer Contribution</th><th>Amount</th><th>Gross Salary</th>
		     			</tr>
		     		</thead>
		     			<tbody>
		     			<c:set var="enployerEarningTotal" value="${0.00}"/>
		     			<c:forEach var="earning" items="${earningPayhead}" varStatus="loop1">
		     			<c:if test="${earning.taxStatus eq 'NonTaxable'}">
		     			   <tr>
			     			   <th>${earning.payrollPayheadName}</th>
			     			   <td>${earning.monthly}</td>
			     			   <td>${earning.monthly}</td>
		     			   </tr>
		     			   <c:set var="enployerEarningTotal" value="${enployerEarningTotal + earning.monthly}"/>
		     			   </c:if>
		     			</c:forEach>
		     		    </tbody>
		     		    <tfoot>
		     		    <tr><th><strong>Total Contribution</strong></th><td>${enployerEarningTotal}</td><td>${enployerEarningTotal}</td></tr>
		     		    <tr><th><strong>Cost to Company (CTC)</strong></th><td>${indPayrollDTO.grossPay + enployerEarningTotal}</td><td>${indPayrollDTO.grossPay + enployerEarningTotal}</td></tr>
		     		    </tfoot>
		     	</table>
		     	</div>
		     	<div class="col-md-5">
		     	<table class="table table2">
		     		 
		     		<thead>
						<tr>
		     				<th>Deduction</th><th>Amount</th>
		     			</tr>
		     		</thead>
		     			<tbody>
		     			<c:set var="decuctionTotal" value="${0.00}"/>
		     			<c:forEach var="decuction" items="${decuctionPayhead}" varStatus="loop2">
		     				<tr><th>${decuction.payrollPayheadName}</th><td>${decuction.monthly}</td></tr>
		     			    <c:set var="decuctionTotal" value="${decuctionTotal + decuction.monthly}"/>
		     			</c:forEach>
		     		    </tbody>
		     		    <tfoot>
		     		    <tr><th><strong>Total Deductions</strong></th><td>${decuctionTotal}</td></tr>
		     		    <tr class="fontSize18"><th><strong>Net Salary</strong></th><td>${indPayrollDTO.netPay}</td></tr>
		     		    </tfoot>
		     	</table>
		     	</div>
		     	<div class="clearfix"></div>
		     <%-- 	 <div class="col-md-7 padding_right hiden">
		     	<table class="table mb0" style="width:100%">
		     		<tbody>
		     		<tr>
		     			<th style="width:186px;"><div class=""><strong>Gross Salary</strong></div></th><td style="width:174px;"><strong>${indPayrollDTO.grossPay}</strong></td><td><strong>${indPayrollDTO.grossPay}</strong></td>
		     		</tr>
		     		</tbody>
		     	</table>
		     	</div> 
		    	<div class="col-md-5 padding_left hiden">
		     	<table class="table" style="width:100%">
		     		<tbody>
		     	<th style="width:310px;"><div class=""><strong>Net Salary</strong></div>	</th>
		     			<td><strong>${indPayrollDTO.netPay}</strong>
		     			</td>
		     			</tbody>
		     	</table>
		     	</div>  --%>
		     	</div>
		     	
		     	</div>
		     	<div class="clerfix"></div>
		     	<div class="row">
		     	<div class="col-md-6">
		     		<p class="small mb0">
		     			Amount (in words) : <span class="text-muted ml5">${numbersToWords}</span>
		     		</p>
		     	</div>
		     	</div>
		     	<div class="text-center clearfix mt25">
		     	<p class="text-muted">This is a Computer Generated Pay Slip</p>
		     	</div>
		     </section>
		      </div>
		</div>
		</div>
  </section>
  
  <script> 
$(function() {
document.getElementById("mediaObject").src = "../images/bis-logo.jpg";
})
function printDoc() {
	alert("216139");
/* var toPrint = document.getElementById('payslipPreview');
var popupWin = window.open('', '_blank', 'width=350,height=150,location=no,left=200px');
popupWin.document.open();
popupWin.window.print('<html><link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css" media="print"/><link rel="stylesheet" type="text/css" href="/resources//css/sourcelead-main.css" media="print"/><link rel="stylesheet" type="text/css" href="/resources//css/timesheet.css" media="print"/></head><body style="width: 80%; display: block; margin: 10px auto; background:#fff !important; padding: 15px;" ">')
popupWin.document.write(toPrint.innerHTML);
window.print(toPrint.innerHTML);
popupWin.document.write('</html>');
popupWin.document.close(); */
window.print();
//print();
}
</script>
<!-- Content Area Ends--> 
<jsp:include page="../masterFooter.jsp" />


 		