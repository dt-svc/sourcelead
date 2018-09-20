<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	
});
</script>


 <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Approve Payroll</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Approve Payroll</div>
    </section>
    
    <div class="tabsMenu clearfix">
   		<ul class="list-unstyled">
		   <li><a href="us-runpayroll-list.html">Review Payroll</a></li>
		   <li class="current"><a href="us-approvepayroll.html">Approve Payroll</a></li>
	   </ul>
   </div>
    
    
    <div class="clearfix"></div>
    <div class="timesheet_out">
      <div class="clearfix mt">
               <div class="clearfix approveTabelesStyles">
               		<div class="col-md-4 mb">
               			<h5>Hours &amp; Earnings</h5>
               			<div class="clearfix approveTabelesStyles-inner">
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
               							<td>120</td>
               							<td>$2,700.00</td>
               						</tr>
               						<tr>
               							<td>Totals</td>
               							<td>120</td>
               							<td>$2,700.00</td>
               						</tr>
               					</tbody>
								<tfoot>
									<tr>
										<td><strong>Gross Pay</strong></td>
										<td>&nbsp;</td>
										<td>$2,700.00</td>
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
               							<td>150$</td>
               						</tr>
               						<tr>
               							<td>401(K)</td>
               							<td>108$</td>
               						</tr>
               						<tr>
               							<td>401(K) Loan Repay</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td><strong>Total Deductions</strong></td>
               							<td>308$</td>
               						</tr>
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
               							<td>Federal Income Tax</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               						 
               							<td>Social Security</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td>Medicare</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               						<td>Delaware State Tax</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td><strong>Total Employee Tax</strong></td>
               							<td>100$</td>
               						</tr>
               						<tr>
               							<td><strong>Net Pay</strong></td>
               							<td>100$</td>
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
               					<tbody>
               						<tr>
               							<td>Medical</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td>401(K) Match</td>
               							<td>50$</td>
               						</tr>
               						<tr>
               							<td>Total Contributions</td>
               							<td>50$</td>
               						</tr>
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
									<td>Employer Social Security </td>
									<td>50$</td>
									</tr>
               						<tr>
									<td>Employer Medicare Tax</td>
									<td>50$</td>
              						</tr>
               						<tr>
									<td>Delaware State Unemployment (SUTA)</td>
									<td>50$</td>
              						</tr>
               						<tr>
									<td>Federal Unemployment Tax</td>
									<td>50$</td>
              						</tr>
               						<tr>
									<td>Delaware Training Tax</td>
									<td>50$</td>
              						</tr>
               						<tr>
									<td>Total Employer Taxes</td>
              						<td>50$</td>
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
               </div>
 		<div class="clearfix mt"></div>
        <nav class="clearfix">
          <ul class="pull-right mr10 list-unstyled list-inline">
            <li><a href="javascript:void(0)" class="btn btn-primary btnSuccess" onClick="successmodal();">Approve</a></li>
            <li><a href="javascript:void(0)" class="btn btn-danger" onClick="rejectModal();">Reject</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
      </div>
    </div>
  </section>
  <!-- Content Area Ends--> 
  
  
  <script type="text/javascript">
function successmodal() {
	$(".sideMask, .success-modal").fadeIn();
}
function closesuccessmodal() {
	$(".sideMask, .success-modal").fadeOut();
}
function rejectModal() {
	$(".sideMask, .rejectModal").fadeIn();
}
function closerejectModal() {
	$(".sideMask, .rejectModal").fadeOut();
}
</script>
  
<jsp:include page="../masterFooter.jsp" />