<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- <script>
$(document).ready(function(){
	prepareUSPayrollList("NO_RECORDS");
});
</script> -->






!-- Content Area -->
<section class="content-wraper contentWraper">
<section class="content-header clearfix">
<div class="pull-left">
<h2>Payroll</h2>
</div>
<div class="pull-right brud-crum">Home &raquo; Payroll</div>
</section>


<div class="clearfix"></div>
<div class="timesheet_out">

<div class="tablelist-wrap">
<div class="timesheet_head minHeight">
<form:form id="generate_payroll_form" name="generate_payroll_form" method="post">
<div class="col-md-7 mt">
	<div class="row">
		<div class="col-md-3">
			<div class="dropdown-select">
			<select id="groupId" name="groupId" onchange="getGroupFrequency();" data-validation="required" field-name="User Group">
				<option value="">--Select User Group--</option>
                    <c:forEach var="ug" items="${userGroupList}">
					<option value="${ug.groupUUID}">${ug.groupName}</option>
				</c:forEach>
			</select>
			</div>
		</div>
		<div class="col-md-4 paddingLeft paddingRight">
			<div class="dropdown-select">
			<select id="payPeriod" name="payPeriod" data-validation="required" field-name="Pay Period">
			</select>
			</div>
		</div>
		<div class="col-md-3 paddingRight">
			<input name="" class="form-control calendar" placeholder="Check Date" id="payrollPayDate" name="payrollPayDate" type="text" data-validation="required" field-name="Pay Date">
		</div>
		<div class="col-md-2">
			<a  class="btn btn-success btnSuccess btn-sm anchorWhite" id="generateUSPayroll" onclick="runUSPayroll();">Run Payroll</a>
		</div>
	</div>						
</div>
<div class="clearfix"></div>
<div class="remove_search">
	<input placeholder="Search" type="text">
</div>              
</form:form>
</div>

<div class="tableScroll">
 <div class="styleTables clearfix mt">
 <div id="USPayrollDiv"></div>
 <div id="testDiv"></div>
</div> 
</div>
<div class="clearfix mt"></div>
<nav class="clearfix">
	
<div class="col-md-2">
			<a href="javascript:void(0)" class="btn anchorBlue">Exclude</a>
</div>
	<ul class="pull-right mr list-unstyled list-inline">
		<li><a href="#" class="btn btn-primary btnSuccess">Sent for Approve</a></li>
		<li><a href="#" class="btn btn-default ml mr">Confirm</a></li>
		<li><a href="#" class="btn btn-default">Cancel</a></li>
	</ul>
<div class="clearfix"></div>
</nav>	



</div>


</div>  


<!-- New Code Here -->

<script>
function getGroupFrequency(){
	var groupUUID = $("#groupId").val();
	 $.ajax({
	    	type: "GET",
	        url: sourceleadUrl+"/getGroupPayPeriod?"+tokenParamName+"="+tokenValue+"&groupUUID="+groupUUID                  , 
	        success: function(json) {
	        var output = "";
			for (var i=0; i<json.length; i++)
			{
				var date = json[i].replace(",", " to ");
				output = output + "<option value='" +json[i]+ "'>"+date+"</option>";
			}
			$("#payPeriod").empty();
			$("#payPeriod").append(output);
			$('#payPeriod').trigger("chosen:updated"); 
	        },
	    });
}
</script>
<!-- New Code End Here -->

<!--   Error Message   -->
<div class="vendor_wrap_mask"></div>
<div class="vendor_error">
<textarea placeholder="Enter message"></textarea>
<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>

<!--   Error Message   --> 


</section>

 

  
  <script>
  $( function() {
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  // written common method in slead-common.js for date formats start
	  var finalDateFormat = getJSDateFormat(locationDate);
	  //end
	  $('#DateofJoin').datepicker({
	      dateFormat: finalDateFormat,
	      changeMonth: true,
	  	  changeYear: true, 
	      //minDate: 0  this line for to restrict selection of previous dates
	  });
	  
  } );
</script>

<script>
	$(function() {
		
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  // written common method in slead-common.js for date formats start
		  var finalDateFormat = getJSDateFormat(locationDate);
		  //end
		  $('#payrollStartDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		      //minDate: 0  this line for to restrict selection of previous dates
		  });
		  
		  $('#payrollEndDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		      //minDate: 0  this line for to restrict selection of previous dates
		  });
		  
		  $('#payrollPayDate').datepicker({
		      dateFormat: finalDateFormat,
		      changeMonth: true,
		  	  changeYear: true, 
		      //minDate: 0  this line for to restrict selection of previous dates
		  });
		
		
		
	});
</script>






<script>
function runUSPayroll(){
	if ($('#generate_payroll_form').validate(false, validationSettings)){  
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var groupId = $("#groupId").val();
		var payPeriod = $("#payPeriod").val();
		var payrollPayDate = $("#payrollPayDate").val();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/generateUSPayroll?searchValue="+searchValue+"&groupId="+groupId+"&payPeriod="+payPeriod+"&payrollPayDate="+payrollPayDate,                 
		        success: function(data) {
		            var json = data.data;
		        	var result = "";
		        	result += '<table>';
		        	result += '<thead>';
		        	result += '<tr>';
		        	result += '<th>';
		        	result += '<div class="checkbox-new pull-left">';
		        	result += '<input id="all" class="checkbox-custom" name="paidComponent" type="checkbox">';
		        	result += '<label for="all" class="checkbox-custom-label"></label>';
		        	result += '</div>';
		        	result += '</th>';
		        	result += '<th>Emp ID</th>';
		        	result += '<th>Emp Name</th>';
		        	result += '<th>Regular<br>Hours</th>';
		        	result += '<th>Payrate</th>';
		        	result += '<th>OT<br>Hours</th>';
		        	result += '<th>Gross<br>Pay</th>';
		        	result += '<th>Pretax<br>Deduction</th>';
		        	result += '<th>Post<br>Deduction</th>';
		        	result += '<th>Taxes</th>';
		        	result += '<th>Net<br>Pay</th>';
		        	result += '</tr>';
		        	result += '</thead>	';
		        	result += '<tbody>';
		        	alert("json"+json.length)
		        	if(json != null && json.length > 0){
		        		for (var i=0; i<json.length; i++){
		        			var opt = json[i];
		        			alert(opt.empId);
		        			result += '<tr>';
				        	result += '<td>';
				        	result += '<div class="checkbox-new pull-left ml5">';
				        	result += '<input id="emp1" class="checkbox-custom" name="paidComponent" type="checkbox">';
				        	result += '<label for="emp1" class="checkbox-custom-label"></label>';
				        	result += '</div>';
				        	result += '</td>';
				        	
				        	result += '<td><div class="tblminwidth-50">'+opt.empId+'</div></td>';
				        	 result += '<td><div class="tbldata-dotdot-vsm">'+opt.empName+'</div></td>';
				        	
				        	result += '<td><div class="tblminwidth-50">'+opt.regularHours+'</div><div class="tblminwidth-50 clearfix text-danger">$ '+opt.regularHrsTotalAmt.toFixed(2)+'</div></td>';	
				        	result += '<td><div class="tbldata-dotdot-vsm">'+opt.payRate+'</div></td>';
				        	
				        	if(opt.overtimeHours != null && opt.overtimeHours != '' && opt.overtimeHours != 'undefined'){
				        		result += '<td><div class="tblminwidth-50">'+opt.overtimeHours+'</div><div class="tblminwidth-50 clearfix text-danger">$ '+opt.otHoursTotalAmt.toFixed(2)+'</div></td>';
				        	}else{
				        		result += '<td><div class="tblminwidth-50">0.00</div><div class="tblminwidth-50 clearfix text-danger">$ 0.00</div></td>';
				        	}
				        	
				        	result += '<td><div class="tblminwidth-50 clearfix">$ '+opt.grossPay.toFixed(2)+'</div>';
				        	if(opt.preTaxDeduction != null && opt.preTaxDeduction != '' && opt.preTaxDeduction != 'undefined'){
				        		result += '</td><td><div class="tblminwidth-50 clearfix">$ '+opt.preTaxDeduction.toFixed(2)+'</div></td>';
				        	}else{
				        		result += '</td><td><div class="tblminwidth-50 clearfix">$ 0.000</td>';
				        	}
				        	if(opt.postTaxDeduction != null && opt.postTaxDeduction != '' && opt.postTaxDeduction != 'undefined'){
				        		result += '<td><div class="tblminwidth-50 clearfix">$ '+opt.postTaxDeduction.toFixed(2)+'</div></td>';	
				        	}else{
				        		result += '<td><div class="tblminwidth-50 clearfix">$ 0.00</div></td>';
				        	}
				        	
				        	result += '<td><div class="tblminwidth-50 clearfix">$ '+opt.taxes.toFixed(2)+'</div></td>';
				        	result += '<td><div class="tblminwidth-50 clearfix"><a onClick="payAdjustment();">$ '+opt.netPay.toFixed(2)+'</a></div></td>';
				        	result += '</tr>';  
			        	}
			        	
		        	}
		        	result += '</tbody>';
		        	result += '</table>';
		        	$("#testDiv").empty();
		        	$("#testDiv").append(result);
		        },
		    });
		 
		return true; 
    }else{
   return false;
   	  } 
	}
	
$('body').on('blur', '#generate_payroll_form', function() {
	$("#generate_payroll_form").showHelpOnFocus();
	$("#generate_payroll_form").validateOnBlur(false, validationSettings);  
});


</script>

<script>
	function payAdjustment(){
		$(".usRunpayroll").fadeIn();
		$(".sideMask").fadeIn();
	}
	function closeusRunpayrol(){
		$(".usRunpayroll").fadeOut();
		$(".sideMask").fadeOut();
	};
</script>

 <!-- Net Edit popup -->

<div class="sideMask"></div>
<div class="modal usRunpayroll" style="display: none;" id="usRunpayroll">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close hidden" data-dismiss="modal"  onclick="closeusRunpayrol();">&times;</button>
        <h4 class="modal-title hidden">Pay Stub</h4>
        <div class="row">
          <div class="col-md-3">
            <label class="clearfix"><strong>Name</strong></label>
            <div class="clearfix">
              <select class="selectpicker width100percnt" title="Select Name" data-live-search="true">
                <option selected></option>
                <option>Suresh Kumar</option>
                <option>Sarath Kumar</option>
                <option>Muralimanohar Kumar</option>
                <option>Sarath Kumar</option>
              </select>
            </div>
          </div>
          <div class="col-md-3">
            <label class="clearfix"><strong>Address</strong></label>
            <div class="clearfix">
              <p class="mb0">Vertex Corporate, Hyderabad, Telangana 500081</p>
            </div>
          </div>
          <div class="col-md-3">
            <label class="clearfix"><strong>Deposit Type</strong></label>
            <div class="clearfix">
              <select class="sel" title="Select Name">
                <option selected>Direct Deposit</option>
                <option>By Check</option>
              </select>
            </div>
          </div>
          
          <div class="col-md-2 text-center">
            <label class="clearfix"><strong>No. Leaves</strong></label>
            <div class="clearfix">
              <p class="mt5 mb0">30</p>
            </div>
          </div>
          <div class="col-md-1 text-center">
            <div class="pull-right">
              <button type="button" class="btn btn-success btnSuccess editUspayslip btn-sm" onClick="editUspayslip(event)" id="editUspaysliptext">Edit</button>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-body" style="min-height: 150px; max-height: 425px; overflow-y: auto; padding: 5px 15px;">
        <div class="row">
          <div class="col-md-12 tablePayslip" id="tablePayslip">
            <table width="100%" class="table">
              <tbody>
                <tr>
                  <td colspan="2">
                     <table width="100%" class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="4">Hours &amp; Earnings</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Description</th>
                          <th>No. of Hours</th>
                          <th>Earning</th>
                        </tr>
                        <tr>
                          <td>Regular</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>Overtime</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><span>124,783.00</span></td>
                          <td><span>124,783.00</span></td>
                        </tr>
                      </tfoot>
                    </table>
                    </td>
                </tr>
                <tr>
                  <td colspan="2">
                     <table width="100%" class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="4">Pay Items</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Item Name</th>
                          <th>Amount</th>
                          <th>
							   <div class="checkbox-new ml mr pull-left">
									<input id="blockAll" class="checkbox-custom" name="PartofaCTC" type="checkbox">
									<label for="blockAll" class="checkbox-custom-label"><span class="ml10">Block</span></label>
								</div>
                          </th>
                        </tr>
                        <tr>
                          <td>Cash Tips</td>
                          <td><input type="text" class="formControl" readOnly value="200$"></td>
                          <td>
                          		<div class="checkbox-new ml mr pull-left">
									<input id="cashTips" class="checkbox-custom" name="PartofaCTC" type="checkbox">
									<label for="cashTips" class="checkbox-custom-label"></label>
								</div>
                          </td>
                        </tr>
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><span>200$</span></td>
                          <td></td>
                        </tr>
                      </tfoot>
                    </table>
                    </td>
                </tr>
                 
                 <tr>
                  <td>
                     <table width="100%" class="table table-bordered" >
                      <thead>
                        <tr>
                          <th colspan="2">Post Tax Deduction</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Description</th>
                          <th>Amount</th>
                        </tr>
                        <tr>
                          <td>Description1</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><span>124,783.00</span></td>
                        </tr>
                      </tfoot>
                    </table></td>
                  <td><table width="100%" class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="2">Pre-Tax Deduction</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Description</th>
                          <th>Amount</th>
                        </tr>
                        <tr>
                          <td>Description</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td colspan="2"><span>124,783.00 </span></td>
                        </tr>
                      </tfoot>
                    </table></td>
                </tr>
                <tr>
                  <td><table width="100%" class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="2">Taxes</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Description</th>
                          <th>Amount</th>
                        </tr>
                        <tr>
                          <td>Federal with holding</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>Medicare</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>FICA</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>State Withholding</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><span>124,783.00</span></td>
                        </tr>
                      </tfoot>
                    </table></td>
                  <td><table width="100%" class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="2">Employer-Paid Benefits</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Description</th>
                          <th>Current</th>
                        </tr>
                        <tr>
                          <td>Healthcare</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>Dental</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                        <tr>
                          <td>ABC LIfe Insurance</td>
                          <td><input type="text" value="124,783.00" class="formControl" readOnly></td>
                        </tr>
                      </tbody>
                      <tfoot>
                        <tr>
                          <th>Total</th>
                          <td><span>124,783.00</span></td>
                        </tr>
                      </tfoot>
                    </table></td>
                </tr>
                <tr>
                  <td colspan="2"><table class="table table-bordered">
                      <thead>
                      <th>&nbsp;</th>
                        <th>Total Gross</th>
                        <th>Fed Taxable Gross</th>
                        <th>FICA Gross</th>
                        <th>Total Taxes</th>
                        <th>Total Deductions</th>
                        <th>Net Pay</th>
                          </thead>
                      <tbody>
                        <tr>
                          <td>Current</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                        </tr>
                        <tr>
                          <td>YTD</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                          <td>2,062.53</td>
                        </tr>
                      </tbody>
                    </table></td>
                </tr>
                <tr>
                  <td><table class="table table-bordered">
                      <tbody>
                        <tr>
                          <th>Total Gross</th>
                          <td>2,062.53</td>
                        </tr>
                        <tr>
                          <th>Before-Tax Deductions</th>
                          <td>2,062.53</td>
                        </tr>
                        <tr>
                          <th>Life Insurance Premiums</th>
                          <td>2,062.53</td>
                        </tr>
                        <tr>
                          <th>Fed Taxable and FICA</th>
                          <td>2,062.53</td>
                        </tr>
                      </tbody>
                    </table></td>
                  <td><table class="table table-bordered">
                      <thead>
                        <tr>
                          <th colspan="2">Net Pay Disctibution</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th>Advice#1006795</th>
                          <td>2,062.53</td>
                        </tr>
                        <tr>
                          <th>Total</th>
                          <td>2,062.53</td>
                        </tr>
                      </tbody>
                    </table></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" onClick="closeusRunpayrol();">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- Net Edit popup Ends -->
 
 


<jsp:include page="../masterFooter.jsp" />