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
	alert(groupUUID);
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

 <!-- Content Area -->
 <%--  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Generate Payroll</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Generate Payroll</div>
    </section>
    <section class="contrHead">
      <ul>
        <li title="Draft"><a href="us-payrollgenerate.html" class="wVer">Generate Payroll</a></li>
        <li title="Finalised"><a href="javascript:void(0)">Advance</a></li>
        <div class="clearfix"></div>
      </ul>
    </section>
    
    <!-- Timesheet -->
    <div class="timesheet_out">
      <div class="project_wrap">
        <div class="clearfix mt"></div>
        <div class="timesheet_head minHeight40 mb10 mt">
        <form:form id="generate_payroll_form" name="generate_payroll_form" method="post">
          <div id="payrollCycleDiv" class="col-md-7 payrollFilter paddingRight">
            <div class="form-inline"> <span class="form-group">
              <input type="text" class="form-control calendar" value="" id="payrollStartDate" name="payrollStartDate" data-validation="required" field-name="Payroll Start Date" placeholder="Start Date">
              </span>
              <span class="form-group">
              <input type="text" class="form-control calendar" value="" id="payrollEndDate" name="payrollEndDate" data-validation="required" field-name="Payroll End Date" placeholder="End Date">
              </span>
              <span class="form-group">
              <input type="text" class="form-control calendar" value="" id="payrollPayDate" name="payrollPayDate" data-validation="required" field-name="Payroll Pay Date" readonly placeholder="Pay Date">
              </span>
              <div class="form-group">
                <button type="button" class="btn btn-primary btn-block" id="generateUSPayroll" onclick="runUSPayroll();">Run Payroll</button>
              </div>
            </div>
          </div>
          <div class="col-md-2">
          <a href="javascript:void(0)" class="selectEmployee mt5 pull-left">Select Employee</a>
			</div>
          <div class="col-md-3 searchStyles">
            <input placeholder="" type="text" class="">
          </div>
          </form:form>
          
        </div>
        <div class="tableStyles-new">
        <div class='remove_search'>
 	 <input type='text' placeholder='Search' id='dataTableSearch'/>
 	</div>
        <div id="USPayrollDiv" class="clients_wrap_table datatable_filter"></div>
        
         
        </div>
     
      </div>
    </div>
  </section> --%>

  
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
	alert("Hiu");
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
		        	if(json != null && json.length > 0){
		        		for (var i=0; i<json.length; i++){
		        			var opt = json[i];
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
				        	result += '</td><td><div class="tblminwidth-50 clearfix">$ '+opt.preTaxDeduction.toFixed(2)+'</div></td>';
				        	result += '<td><div class="tblminwidth-50 clearfix">$ '+opt.postTaxDeduction.toFixed(2)+'</div></td>';
				        	result += '<td><div class="tblminwidth-50 clearfix">$ '+opt.taxes.toFixed(2)+'</div></td>';
				        	result += '<td><div class="tblminwidth-50 clearfix"><a href="javascript:void(0)">$ '+opt.netPay.toFixed(2)+'</a></div></td>';
				        	result += '</tr>';  	
			        	}
			        	
		        	}
		        	result += '</tbody>';
		        	result += '</table>';
		        	alert(result);
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


<jsp:include page="../masterFooter.jsp" />