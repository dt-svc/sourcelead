<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <!-- Content Area -->
<section class="content-wraper contentWraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Run Payroll</h2>
		</div>
		<div class="pull-right brud-crum">
			<a
				href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; <a href="<c:url value='${appPath}/runIndianPayRoll'/>">Run
				Payroll</a> &raquo; Review Payroll
		</div>
	</section>
	<div class="tabsMenu clearfix">
		runIndianPayRoll
		<ul class="list-unstyled">
			<li class="current"><a>Review Payroll</a></li>
			<li><a>Approve Payroll</a></li>
		</ul>
	</div>


	<div class="clearfix"></div>
	<div class="clearfix"></div>
	<div class="timesheet_out">
		<div class="tablelist-wrap">
			<div class="timesheet_head minHeight">
				<div class="col-md-12 mt">
					<div class="row">
						<div class="col-md-2">
							<select class="sel selheight30">
								<option selected>Group</option>
								<option>Group-1</option>
								<option>Group-2</option>
								<option>Group-3</option>
							</select>
						</div>
						<div class="col-md-2">
							<select class="sel selheight30">
								<option selected>Pay Period</option>
								<option>01/02/2018</option>
								<option>01/02/2018</option>
								<option title="09/02/2018 - 15/01/2018"
									value="09/02/2018 - 15/01/2018">09/02/2018 - 15/01/2018</option>
							</select>
						</div>
						<div class="col-md-2">
							<input name="" class="form-control calendar input-sm"
								placeholder="Check Date" type="text">
						</div>
						<div class="col-md-2">
							<select class="sel selheight30">
								<option selected>Rate</option>
								<option>LCA Wages</option>
								<option>Salary</option>
								<option>Project</option>
							</select>
						</div>
						<div class="col-md-2">
							<select class="sel selheight30">
								<option selected>Payroll Type</option>
								<option>Worksheet</option>
								<option>General</option>
							</select>
						</div>
						<div class="col-md-2">
							<a class="btn btn-success btnSuccess btn-sm anchorWhite"
								onclick="runIndPayroll();">Run Payroll</a>
						</div>
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
								<th>Exclude</th>
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
									<td>
										<div class="checkbox-new pull-left ml">
											<input id="emp1" class="checkbox-custom" name="paidComponent"
												type="checkbox"> <label for="emp1"
												class="checkbox-custom-label"></label>
										</div>
									</td>
									<td><div class="tblminwidth-50">${empPayroll.employee.employeeId}</div></td>
									<td><div class="tbldata-dotdot-vsm">${empPayroll.employee.user.fullName}</div></td>
									<td><div class="tblminwidth-50">${empPayroll.regularHours}</div>
										<div class="tblminwidth-50 clearfix text-danger">${empPayroll.regularHoursAmount}<span class="paddingLeft">&#8377;</span>
										</div></td>
									<td><div class="tblminwidth-50">${empPayroll.overTimeHours}</div>
										<div class="tblminwidth-50 clearfix text-danger">${empPayroll.overTimeAmount}<span class="paddingLeft">&#8377;</span>
										</div></td>
									<td><div class="tblminwidth-50 clearfix">
											${empPayroll.noOfLeaves}<span class="paddingLeft"></span>
										</div></td>
									<td><div class="tblminwidth-50 clearfix">
											${empPayroll.grossPay}<span class="paddingLeft">&#8377;</span>
										</div></td>
									<td><div class="tblminwidth-50 clearfix">
											${empPayroll.tds}<span class="paddingLeft">&#8377;</span>
										</div></td>
									<td>
										<div class="tblminwidth-50 clearfix">
											<a onClick="netPay('${empPayroll.indEmpPayrollUUID}', '${empPayroll.indPaysheetFrequency.paySheetFrequencyUUID}');"> ${empPayroll.netPay}<span class="paddingLeft">&#8377;</span> </a>
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
					<li><a href="#" class="btn btn-primary btnSuccess">Sent for Approve</a></li>
					<li><a href="#" class="btn btn-default ml mr">Confirm</a></li>
					<li><a href="#" class="btn btn-default">Cancel</a></li>
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
	$.ajax({
        type : "GET",
        url: sourceleadUrl+"/calculateAssociateSalaryInd?"+tokenParamName+"="+tokenValue+"&indEmpPayrollUUID="+indEmpPayrollUUID+"&paySheetFrequencyUUID="+paySheetUUID,    
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
}
function confirmPayroll() {
	$(".confirmPayroll, .comMask").fadeIn();
}
function closeCPR() {
	$(".confirmPayroll, .comMask").fadeOut();
}
</script>
  
<jsp:include page="../../masterFooter.jsp" />