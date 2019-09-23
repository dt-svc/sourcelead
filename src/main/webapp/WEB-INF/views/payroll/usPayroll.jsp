<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<div id="paysheetLoading"></div>
		<div class="tablelist-wrap">
		<form:form id="generate_paysheet_form" name="generate_paysheet_form" method="post">
			<div class="timesheet_head minHeight">
				
					<div class="col-md-7 mt">
						<div class="row">
							<div class="col-md-3">
								<div class="dropdown-select">
									<select id="groupId" name="groupId"
										onchange="getGroupFrequency();" data-validation="required"
										field-name="User Group">
										<option value="">--Select User Group--</option>
										<c:forEach var="ug" items="${userGroupList}">
											<option value="${ug.groupUUID},${ug.id}">${ug.groupName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-4 paddingLeft paddingRight">
								<div class="dropdown-select">
									<select id="payPeriod" name="payPeriod"
										data-validation="required" field-name="Pay Period">
									</select>
								</div>
							</div>
							<!-- <div class="col-md-3 paddingRight">
								<input name="" class="form-control calendar"
									placeholder="Check Date" id="payrollPayDate"
									name="payrollPayDate" type="text" data-validation="required"
									field-name="Pay Date">
							</div> -->
							<div class="col-md-2">
								<a class="btn btn-success btnSuccess btn-sm anchorWhite"
									id="generateUSPaysheet" onclick="runUSPaysheet();">Run
									Payroll</a>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="remove_search">
						<input placeholder="Search" type="text">
					</div>
				
			</div>

			<div class="tableScroll">
				<div class="styleTables clearfix mt">
					<div id="USPayrollDiv"></div>
					<div id="payheetDIV"></div>
				</div>
			</div>
			<div class="clearfix mt"></div>
			<nav class="clearfix">

				<!-- <div class="col-md-2">
					<a href="javascript:void(0)" class="btn anchorBlue">Exclude</a>
				</div> -->
				<ul class="pull-right mr list-unstyled list-inline">
					<li><a href="#" class="btn btn-primary btnSuccess" id="paysheetSubmitDiv"  onclick="submitPaysheet();">Submit for Approve</a></li>
					<li><a href="#" class="btn btn-default ml mr">Confirm</a></li>
					<li><a href="#" class="btn btn-default">Cancel</a></li>
				</ul>
				<div class="clearfix"></div>
			</nav>


       </form:form>
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
		<a class="close_color" href="javascript:void(0)">Yes</a><a
			href="javascript:void(0)">No</a>
	</div>

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
function runUSPaysheet(){
	if ($('#generate_paysheet_form').validate(false, validationSettings)){  
		var searchValue = $("#dataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var groupId = $("#groupId").val();
		var payPeriod = $("#payPeriod").val();
		var payrollPayDate = $("#payrollPayDate").val();
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/generatePaySheet?searchValue="+searchValue+"&groupId="+groupId+"&payPeriod="+payPeriod+"&payrollPayDate="+payrollPayDate,                 
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
		        	result += '<th>EMPID</th>';
		        	result += '<th>Status</th>';
		        	result += '<th>Emp NAME</th>';
		        	result += '<th>Status</th>';
		        	result += '<th>Hours</th>';
		        	result += '<th>Proj Bill Rate</th>';
		        	result += '<th>Proj %</th>';
		        	result += '<th>Proj Pay Rate</th>';
		        	result += '<th>Exp. Remb</th>';
		        	result += '<th>PreDeduction/Health Insurance</th>';
		        	result += '<th>LCA Salary Month</th>';
		        	result += '<th>Additional Pay</th>';
		        	result += '<th>Gross Pay</th>';
		        	result += '<th>Bonus Accrual Pay Period</th>';
		        	result += '<th>Bonus Accrual YTD</th>';
		        	result += '<th>Advance/Post Deduction</th>';
		        	result += '<th>Vendor Last Paid Date</th>';
		        	result += '<th>Mode of Pay</th>';
		        	result += '<th>Notes</th>';
		        	result += '</tr>';
		        	result += '</thead>	';
		        	result += '<tbody>';
		        	if(json != null && json.length > 0){
		        		for (var i=0; i<json.length; i++){
		        			var opt = json[i];
		        			result += '<tr>';
		        			
		        			result += ' <td>';
		        			result += ' <div class="tblminwidth-50"></div>';
		        			result += ' </td>';
		        			
		        			result += ' <td>';
		        			result += ' <div class="width150px"><input type="text" class="form-control noboderInput"  id="'+i+'employeeId" name="uSPaysheetDTO['+i+'].employeeId" value="'+opt.employeeId+'" readonly></div>';
		        			result += ' </td>';
		        			
		        			result += ' <td>';
		        			result += ' <div class="width100px">';
		        			result += ' <select class="sel" id="'+i+'processreview" name="'+i+'processreview">';
		        			result += ' <option value="">Select</option>';
		        			result += ' <option value="Review">Review</option>';
		        			result += ' <option value="Process">Process</option>';
		        			result += ' </select>';
		        			result += ' </div>';
		        			result += ' </td>';
		        			
		        			result += ' <td>';
		        			result += ' <div class="tbldata-dotdot"><input type="text" class="form-control noboderInput" id="'+i+'employeeName" name="uSPaysheetDTO['+i+'].employeeName" value="'+opt.employeeName+'" readonly></div>';
		        			result += ' <div class="tbldata-dotdot"><input type="hidden" id="'+i+'userId" name="uSPaysheetDTO['+i+'].userId" value="'+opt.userId+'"></div>';
		        			result += ' <div class="tbldata-dotdot"><input type="hidden" id="'+i+'projectMemberId" name="uSPaysheetDTO['+i+'].projectMemberId" value="'+opt.userId+'" readonly></div>';
		        			result += '  </td>';
		        			
		        			result += '  <td>';
		        			result += ' 	<div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'immiStatus" name="uSPaysheetDTO['+i+'].immiStatus" value="OPT" readonly></div>';
		        			result += '  </td>';
		        			
		        			result += ' <td>';
		        			result += ' <div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'hours" name="uSPaysheetDTO['+i+'].hours" value="'+opt.hours+'" readonly></div>';
		        			result += '  </td>';
		        			
		        			result += '    <td>';
		        			if(opt.projBillRate != null){
		        				result += '   <div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'projBillRate" name="uSPaysheetDTO['+i+'].projBillRate" value="'+opt.projBillRate+'" readonly></div>';	
		        			}else{
		        				result += '   <div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'projBillRate" name="uSPaysheetDTO['+i+'].projBillRate" value="0" readonly></div>';
		        			}
		        			
		        			result += ' </td>';
		        			
		        			result += '  <td>';
		        			result += '  <div class="tblminwidth-100">';
		        			result += ' 	<input type="text" class="form-control input-sm" id="'+i+'projPayPercent"  name="uSPaysheetDTO['+i+'].projPayPercent" onblur="calGrossByPaypercent('+i+');" value="" placeholder="%">';
		        			result += ' 	</div>';
		        			result += '   </td>';
		        			
		        			result += '   <td>';
		        			result += '  <div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'projPayRate" name="uSPaysheetDTO['+i+'].projPayRate" value="0" readonly></div>';
		        			result += ' </td>';
		        			result += '  <td>';
		        			
		        			result += '  <div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'expensesReimbursement" name="uSPaysheetDTO['+i+'].expensesReimbursement" value="00.00" readonly></div>';
		        			result += ' </td>';
		        			result += '  <td>';
		        			if(opt.preDeduction != null){
		        				result += ' <div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'preDeduction" name="uSPaysheetDTO['+i+'].preDeduction"  value="'+opt.preDeduction+'" readonly></div>';
		        			}else{
		        				result += ' <div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'preDeduction" name="uSPaysheetDTO['+i+'].preDeduction"  value="'+0+'" readonly></div>';
		        			}
		        			result += '  </td>';
		        			
		        			result += '  <td>';
		        			result += '  <div class="tblminwidth-100">';
		        			result += ' <div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'lcaSalaryMonth" name="uSPaysheetDTO['+i+'].lcaSalaryMonth"  value="0" readonly></div>';
		        			result += ' 	</div>';
		        			result += '   </td>';
		        			
		        			result += '   <td>';
		        			result += '   <div class="tblminwidth-100">';
		        			result += '   	<input type="text" class="form-control input-sm" value="" id="'+i+'additionalPay" name="uSPaysheetDTO['+i+'].additionalPay" onblur = addAdditiobalPayToSal('+i+'); placeholder="Enter">';
		        			result += ' 	</div>';
		        			result += '</td>';
		        			
		        			result += '<td>';
		        			result += '	<div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'grossPay" name="uSPaysheetDTO['+i+'].grossPay" value="0" readonly></div>';
		        			result += '  </td>';
		        			
		        			result += '  <td>';
		        			result += '	<div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'bonusAccrualPayPeriod" name="uSPaysheetDTO['+i+'].bonusAccrualPayPeriod"  value="0" readonly></div>';
		        			result += '  </td>';
		        			
		        			result += '   <td>';
		        			result += '		<div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'bonusAccrualYTD" name="uSPaysheetDTO['+i+'].bonusAccrualYTD" value="0" readonly></div>';
		        			result += '    </td>';
		        			
		        			result += '    <td>';
		        			if(opt.postDeduction != null){
		        			result += '   	<div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'postDeduction" name="uSPaysheetDTO['+i+'].postDeduction" value="'+opt.postDeduction+'" readonly></div>';
		        			}else{
		        				result += '   	<div class="width100px"><input type="text" class="form-control noboderInput" id="'+i+'postDeduction" name="uSPaysheetDTO['+i+'].postDeduction" value="0" readonly></div>';
		        				}
		        			result += '    </td>';
		        			
		        			result += '    <td>';
		        			result += '    <div class="tblminwidth-125">';
		        			result += ' 	<input type="text" class="form-control input-sm calendar" id="'+i+'vendorLastpaidDate" name="uSPaysheetDTO['+i+'].vendorLastpaidDate" value="" placeholder="Enter">';
		        			result += '	</div>';
		        			result += '   </td>';
		        			
		        			result += '   <td>';
		        			result += '     <div class="width150px">';
		        			result += '      <select class="sel" id="'+i+'modeOfPay" name="uSPaysheetDTO['+i+'].modeOfPay">';
		        			result += '     <option value="">Select</option>';
		        			result += '   	<option value="Check">Check</option>';
		        			result += '   	<option value="Direct Deposit">Direct Deposit</option>';
		        			result += '       </select>';
		        			result += '   </div>';
		        			result += '  </td>';
		        			
		        			result += '  <td>';
		        			result += ' <div class="tbldata-dotdot-big">';
		        			result += '  	<input type="text" class="form-control input-sm" id="notes" name="uSPaysheetDTO['+i+'].notes" value="" placeholder="Enter">';
		        			result += '	</div>';
		        			result += '  </td>';
		        			
		        			result += '</tr>';
			        	}
			        	
		        	}
		        	result += '</tbody>';
		        	result += '</table>';
		        	$("#payheetDIV").empty();
		        	$("#payheetDIV").append(result);
		        },
		    });
		 
		return true; 
    }else{
   return false;
   	  } 
	}
	
$('body').on('blur', '#generate_paysheet_form', function() {
	$("#generate_paysheet_form").showHelpOnFocus();
	$("#generate_paysheet_form").validateOnBlur(false, validationSettings);  
});


</script>



<script type="text/javascript">
	function submitPaysheet() {
		 var formData = getFormData("generate_paysheet_form");
		  	$("#paysheetLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.post(sourceleadUrl+"/submitPaysheet?"+tokenParamName+"="+tokenValue, formData, function(result) {
				if (result.response === "INVALID_DATA") {
	    		    showError(result.responseMessage);
	    		} else if (result.response === "AWKWARD") {
	    		    location.href = "/errorPage";
	    		} else if (result.response === "SUCCESS") {
	    			$("#paysheetLoading").removeAttr("style");	
					// location.replace(sourceleadUrl+ "/viewHoliday"); 
	    		} else if (result.indexOf('Sign In') !== -1) {
	    			location.replace(sourceleadUrl);
	    		} else {
	    		    showError("Operation failed, please try again");
	    		}
				 $("#paysheetLoading").hide();
			}); 
	}
		
</script>








<script type="text/javascript">
function runUSPayrollRajashekar(){
	if ($('#generate_payroll_form').validate(false, validationSettings)){  
			var searchValue = $("#dataTableSearch").val();
			searchValue = searchValue ? searchValue : "";
			var groupId = $("#groupId").val();
			var payPeriod = $("#payPeriod").val();
			var payrollPayDate = $("#payrollPayDate").val();
			var url = sourceleadUrl+"/generateUSPayroll?searchValue="+searchValue+"&groupId="+groupId+"&payPeriod="+payPeriod+"&payrollPayDate="+payrollPayDate;
 		    prepareUSPayrollList(url);
			return true; 
	         }else{
		    return false;
		    	  } 
	}

</script>

<script>
function prepareUSPayrollList(url){
	$("#USPayrollDiv").empty();
 	 var result = ""; // empId empName regularHours overtimeHours  grossPay  preTaxDeduction postTaxDeduction taxes benefits
 	  result = result +"<table id='USPayrollPagination' class='display' cellspacing='0' width='100%' >"; 
 	  result = result +"<thead>"; 
      result = result + '<tr>';
		result = result + '<th>Employee Id</th>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>Regular Hours</th>';
		result = result + '<th>Overtime Hours</th>';
		result = result + '<th>Gross Pay</th>';
		result = result + '<th>PreTax Deduction</th>';
		result = result + '<th>postTax Deduction</th>';
		result = result + '<th>Taxes</th>';
		result = result + '<th>Benefits</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
      $("#USPayrollDiv").append(result);
      prepareUSPayrollPagination(url); 
     /* if(url != 'NO_RECORDS'){
    	
     } */
      
}
</script>


<script>
function prepareUSPayrollPagination(url){
	var colIndex = 0;
    $('#USPayrollPagination').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10         
        }),
        "columns": [
            { "data": "empId" },
            { "data": "empName"},
            { "data": "regularHours"},
            { "data": "overtimeHours" },
            { "data": "grossPay"},
            { "data": "preTaxDeduction" },
            { "data": "postTaxDeduction" },
            { "data": "taxes"},
            { "data": "netPay"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "empId",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		 if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.empId+'</div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "empName",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.empName+'</div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "regularHours",
            "render": function ( data, type, opt, meta ) {
            	return  '<td><div class="tblminwidth-75">'+opt.regularHours+'</div></td>';
            }
          }, {
              "targets": colIndex++,
              "data": "overtimeHours",
              "render": function ( data, type, opt, meta ) {
            	  return '<td><div class="tbldata-dotdot-vsm" title="'+opt.overtimeHours+'">'+opt.overtimeHours+'</div></td>';
              }
            }, {
                "targets": colIndex++,
                "data": "grossPay",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.grossPay !=null && opt.grossPay !='' && opt.grossPay !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-sm"">'+opt.grossPay+'</div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "preTaxDeduction",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.preTaxDeduction !=null && opt.preTaxDeduction !='' && opt.preTaxDeduction !='undefined'){
						result = result + '<td><div class="tblminwidth-50">'+opt.preTaxDeduction+'</div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "postTaxDeduction",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.taxes != null && opt.taxes !='' && opt.taxes != 'undefined'){
	    	  			result = result + ' <td><div class="tblminwidth-75">'+opt.taxes+'</div></td>';
	    	  		}else{
	    	  			result = result + ' <td><div class="tblminwidth-75">--</div></td>';
	    	  		}
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "netPay",
                  "sortable": false,
                  "render": function ( data, type, opt, meta ) {
                	  return '<td><div class="tblminwidth-75">'+opt.netPay+'</div></td>';
                  }
                } ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#USPayrollPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   runUSPayroll();
    	   }
    	});
}
</script>


<script>
function calGrossByPaypercent(rowId){
	var projPayPercent = $("#"+rowId+"projPayPercent").val();
	var percentValue = projPayPercent /100;
	var projBillRate = $("#"+rowId+"projBillRate").val();
	var payRate = parseFloat(projBillRate) * parseFloat(percentValue);
	$("#"+rowId+"projPayRate").val(payRate)
	
	calculateGross(payRate,rowId);
}
</script>

<script>
function calculateGross(payRate,rowId){
	     var hours = $("#"+rowId+"hours").val();
	     var split = hours.split('.');
	     var splitHours = split[0];
	     var minute = split[1];
	     var hourminits = splitHours * 60;
		 var finalmin = parseInt(hourminits) + parseInt(minute);
	     var perMinit = payRate / 60;
	     var totlalAmount = (perMinit * finalmin);
	     $('#'+rowId+"grossPay").val(totlalAmount.toFixed(2));
	     calculateBonusAccuralPayPeriod(totlalAmount,rowId);
}
</script>

<script>
function calculateBonusAccuralPayPeriod(grossPay,rowId){
	var preDeduction = $("#"+rowId+"preDeduction").val();
	var  resultValue = grossPay - preDeduction;
	var gross = $('#'+rowId+"grossPay").val();
	var bonusAccuralPay = resultValue - gross ;
	$("#"+rowId+"bonusAccrualPayPeriod").val(bonusAccuralPay);
	
}
</script>


<script>
function addAdditiobalPayToSal(rowId){
	var additionalPay = $("#"+rowId+"additionalPay").val();
	var gross = $('#'+rowId+"grossPay").val();
	var pay = parseFloat(additionalPay) + parseFloat(gross) ;
	$('#'+rowId+"grossPay").empty();
	$('#'+rowId+"grossPay").val(pay);
}
</script>

<jsp:include page="../masterFooter.jsp" />