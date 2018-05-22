<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function(){
	prepareUSPayrollList("NO_RECORDS");
});
</script>

 <!-- Content Area -->
  <section class="content-wraper">
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
         <!--  <table>
            <tbody>
            </tbody>
            <thead>
              <tr>
                <th>Emp Id</th>
                <th>Employee Name</th>
                <th>Regular<br>Hours</th>
                <th>Overtime<br>Hours</th>
                <th>Gross Pay</th>
                <th>Pre Tax <br> Deduction</th>
                <th>Post Tax <br> Deduction</th>
                <th>Taxes</th>
                <th>Benefits</th>
                <th>Net Pay</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>
                <div class="proj_show"><span>Emp0001</span><i class="fa fa-binoculars"></i></div></td>
                <td><div>Jaden Smith</div></td>
				<td>
              		<div class="tblminwidth-75">40</div>
              		<div class="tblminwidth-75">2000 $</div>
                </td>
				<td>
              		<div class="tblminwidth-75">40</div>
              		<div class="tblminwidth-75">2000$</div>
                </td>				<td>
              		<div class="tblminwidth-75">2000$</div>
                </td>
                <td><div class="tblminwidth-75"><a href="javascript:void(0)" onClick="preTax();">50$</a></div></td>
                <td><div class="tblminwidth-75"><a href="javascript:void(0)" onClick="postTax();">100$</a></div></td>
                <td><div class="tblminwidth-75"><a href="javascript:void(0)" onClick="taxes();">200$</a></div></td>
                <td><div>200$</div></td>
                <td><div class="tblminwidth-75">3000$</div></td>
               </tr>
            </tbody>
          </table> -->
        </div>
     <!--    <nav>
          <ul id="paging" class="pagination">
            <li><a href="#">Previous</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li class="disabled"><a href="#">6</a></li>
            <li data-page="last"><a href="#">Next</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav> -->
      </div>
    </div>
  </section>
  <!-- Content Area Ends--> 
  
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
		
		/* $('#payrollStartDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$("#payrollEndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		}); */
		
		/* $('#payrollEndDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate() - 1);
				$("#startDate").datepicker("option", "maxDate", dt);
				addValidClass();
			}
		}); */
		
		
	/* 	$('#payrollPayDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate() - 1);
				$("#payrollPayDate").datepicker("option", "maxDate", dt);
				addValidClass();
			}
		}); */
		
	});
</script>






<script type="text/javascript">
function runUSPayroll(){
		if ($('#generate_payroll_form').validate(false, validationSettings)){  
			var searchValue = $("#dataTableSearch").val();
			searchValue = searchValue ? searchValue : "";
			var payrollStartDate = $("#payrollStartDate").val();
			var payrollEndDate = $("#payrollEndDate").val();
			var payrollPayDate = $("#payrollPayDate").val();
			var url = sourceleadUrl+"/generateUSPayroll?searchValue="+searchValue+"&payrollStartDate="+payrollStartDate+"&payrollEndDate="+payrollEndDate+"&payrollPayDate="+payrollPayDate;
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
     if(url != 'NO_RECORDS'){
    	 prepareUSPayrollPagination(url); 
     }
      
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

<!-- <script>
function runUSPayroll(){
	if ($('#generate_payroll_form').validate(false, validationSettings)){  
		alert("Hi-1");    
		var formData = getFormData("generate_payroll_form");
		        	  $.post(sourceleadUrl+"/generateUSPayroll?"+tokenParamName+"="+tokenValue, formData, function(data) {
		        		 alert("success");
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
</script> -->
<jsp:include page="../masterFooter.jsp" />