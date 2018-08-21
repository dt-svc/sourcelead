<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script>
$(document).ready(function(){
	$("#myPayrollFormDiv").hide();
	$("#loansAdvancesDiv").hide();
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
}

function loansAdvances(){
	$("#loansAdvancesDiv").show();
	$("#myPayrollFormDiv").hide();
	$("#myPayrollDiv").hide();
}
</script>

<!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>My Forms</h2>
  </div>
  <div class="pull-right brud-crum">Home &raquo; My Forms</div>
</section>
 
   <div class="tabsMenu">
   	<ul class="list-unstyled">
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
                  			<span class="col-md-7"><strong onClick="totalCountFun();" class="cursorPointer">60,0000 $</strong></span>
                  		</div>
                  	</div>
                  	<div class="col-md-3">
                  		<div class="row">
                  			<label class="col-md-5"><strong>No. of Hours:</strong></label>
                  			<span class="col-md-7"><strong>200 Hrs</strong></span>
                  		</div>
                  	</div>
                  </div>
                 <div class="clearfix mb5"></div>
                 <div class="clearfix" id="totalCount" style="display:none;">
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
               					<tbody>
               						<tr>
               							<td>Regular</td>
               							<td>200</td>
               							<td>50,000</td>
               						</tr>
               						<tr>
               							<td>Overtime</td>
               							<td>50</td>
               							<td>10,000</td>
               						</tr>
               					</tbody>
								<tfoot>
									<tr>
										<td><strong>Total</strong></td>
										<td>&nbsp;</td>
										<td>60,000$</td>
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
               					<tbody>
               						<tr>
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
               						</tr>
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
               					<tbody>
               						<tr>
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
               						</tr>
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
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
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
               					<tbody>
               						<tr>
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
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-3 mb">
               			<h5>Pay Times</h5>
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
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
               		<div class="col-md-3 mb">
               			<h5>Advances</h5>
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
               						</tr>
               					</tbody>
               				</table>
               		</div>
               		</div>
               	
               </div>
                                                 
                 <div class="clearfix col-md-12 styleTables">
                 <div class="row">
                 	<table>
                 		<thead>
                 			<tr>
                 				<th>Pay Period</th>
                 				<th>Pay Date</th>
                 				<th>Account Type</th>
                 				<th>Gross Pay</th>
                 				<th>Taxes</th>
                 				<th>Deductions</th>
                 				<th>Net Pay</th>
                 				<th>Action</th>
                 			</tr>
                 		</thead>
                 		<tbody>
                 			<tr>
                 				<td>
									<div class="tblminwidth-100 text-center">
										<div class="clearfix">01/01/2018</div>
										<div class="clearfix">15/02/2018</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-125">
										<div class="clearfix">15/02/2018</div>
									</div>
                 				</td>
                 				<td>
								<div class="tblminwidth-125">
									<div>Direct Deposit</div>
								</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
									<div>7000$</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
										<div>500$</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
										<div>200$</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-50">
										<div>6800$</div>
									</div>
                 				</td>
                 				<td>
									<div class="tblminwidth-30">
										<div class="fa fa-eye"></div>
									</div>
                 				</td>
                 			</tr>
                 		</tbody>
                 	</table>
                 </div>
                 </div>					
               <div class="clearfix"></div>
               
               
                <div class="clearfix"></div>
                 <div class="clearfix footerstyelsNew">
                 <div class="col-md-6">
                 <a href="javascript:void(0)" class="anchorBlack">Skip</a>
			     </div>
                  <div class="col-md-6">
                   <div class="pull-right">
                    <input value="Save & Continue" class="btn btn-success btnSuccess" type="button">
                    <input value="Cancel" class="btn btn-default ml10" type="button">
                    </div>
                  </div>
                </div>
              
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
 <script type="text/javascript">
	function uploaddocument() {
		$(".uploaddocument, .sideMask").fadeIn();
	}
	function clsoerequestPop() {
		$(".uploaddocument, .sideMask").fadeOut();
	}
		$("#uploadBtn").change(function () {
			//var m = $(this).val();
		$('#uploadFile').val($(this).val().replace(/C:\\fakepath\\/i, ''))
	})
</script> 

<jsp:include page="../masterFooter.jsp" />