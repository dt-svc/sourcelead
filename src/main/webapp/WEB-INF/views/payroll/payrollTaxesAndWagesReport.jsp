<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="/resources/js/payrollReports.js"></script>

<script>
$(document).ready(function(){
   getPayGroupList("payGroup","Y");
});
</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Taxable Wages</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/usPayrollReport'/>">US Payroll Reports List</a> &raquo;  Taxable Wages</div>
    </section>
    <div class="contentareaInner-styles clearfix">
    
    <form:form id="payroll_Taxes_Wages_form"  name="payroll_Taxes_Wages_form" method="post">
    <input type="hidden" id="groupId" name="groupId" value="" />
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Pay Group</label>
				<div class="col-md-12">
					<select class="form-control sel" id="payGroup" name="payGroup" field-name="Pay Group" onchange="getPayPeriodByGroupId('payGroup','payPeriod','','customtaxablewages');" data-validation="required">
					</select>
				</div>
            </div>
			</div>
			
			<div id="customtaxablewages" style="display: none">
			  	<div class="col-md-5">
		            <div class="form-group" id="fromDateDiv">
		              <label class="col-md-12 paddingRight">From Date</label>
						<div class="col-md-12">
							<input type="text" class="form-control calendar" id="fromDate">
						</div>
		            </div>
	            </div>
	            <div class="col-md-5">
		            <div class="form-group" id="toDateDiv">
		              <label class="col-md-12">To Date</label>
		              <div class="col-md-12">
		                <input type="text" class="form-control calendar" id="toDate">
		              </div>
		            </div>
				</div>
			</div>
	
            <div class="col-md-5" id="payPeriodDiv">
            <div class="form-group">
              <label class="col-md-12">Pay Period</label>
              <div class="col-md-12">
                <select class="form-control sel" id="payPeriod" name="payPeriod" field-name="Pay Period" onchange="getFreqdates('freqStartDate','freqEndDate');">
					</select>
					<input type="hidden" name="freqStartDate" id="freqStartDate" value=""/>
					<input type="hidden" name="freqEndDate" id="freqEndDate" value=""/>
              </div>
            </div>
        </div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="runPayrollTaxAndWagesReport();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onClick="resetData();">
          </div>
        </div>
      </div>      
	</div>
	</form:form>
    
    
    
    
     <div class="clearfix"></div>
     <div id="popUp"></div>	
  	<div class="timesheet_out" id="payrollTaxAndWagesDiv" style="display: none;">
      <div class="clearfix">
        <div class="positionRelative reportHead clearfix">
  			<ul class="pull-right list-unstyled list-inline">
				<li>
					<a class="overIcon" title="Actions" onclick="newActions();"></a>
      				<div class="newActPop" style="display: block;">
					<span><a onclick="exportTaxAndWageReportPdf('pdf');" title="PDF">Export PDF</a> <a title="Excel" onclick="exportTaxAndWageReportPdf('xls');" >Export Excel</a> </span> </div>
   				</li>
			    <li class="">
			      <input class="repSearchBar" placeholder="Search" id="payrollTaxWageSearch" type="text">
			      <span class="repSearchIcon" onclick="runPayrollTaxAndWagesReport();"></span>
			    </li>
  			</ul>
		</div>
        <div class="require_wrap_table tableFirstSecond clearfix datatable_filter">
        <div id="payrollTaxAndWagesList"></div>
        </div>
      </div>
     </div>
      <!-- Content Area Ends--> 
    </div>
  </section> 

<script>
function exportTaxAndWageReportPdf(exportType){
	var searchValue = $("#payrollTaxWageSearch").val();
//		var formData = getFormData("payroll_Taxes_Wages_form");
	var payGroup = $("#payGroup").val();
	var freqStartDate = $("#freqStartDate").val();
	var freqEndDate = $("#freqEndDate").val();
	if(payGroup == "custom"){
		payGroup = 0;
	}
	if(freqStartDate == ""){
		freqStartDate = $("#fromDate").val();
	}else{
		freqStartDate = $("#freqStartDate").val();
	}
	
	if(freqEndDate == ""){
		freqEndDate = $("#toDate").val();
	}else{
		freqEndDate = $("#freqEndDate").val();
	}
	
   	window.location.href = sourceleadUrl+"/getpayrollTaxAndWagesReportPdf?&searchValue="+searchValue+"&payGroup="+payGroup+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&exportType="+exportType;
}
</script>

<script type="text/javascript">
function runPayrollTaxAndWagesReport(){
// 	 if ($('#payroll_Taxes_Wages_form').validate(false, validationSettings)){
// 		 $("#payrollRegister").toggle();
		var searchValue = $("#payrollTaxWageSearch").val();
// 		var formData = getFormData("payroll_Taxes_Wages_form");
		var payGroup = $("#payGroup").val();
		var freqStartDate = $("#freqStartDate").val();
		var freqEndDate = $("#freqEndDate").val();
		if(payGroup == "custom"){
			payGroup = 0;
		}
		if(freqStartDate == ""){
			freqStartDate = $("#fromDate").val();
		}else{
			freqStartDate = $("#freqStartDate").val();
		}
		
		if(freqEndDate == ""){
			freqEndDate = $("#toDate").val();
		}else{
			freqEndDate = $("#freqEndDate").val();
		}
		var url = sourceleadUrl+"/getpayrollTaxAndWagesReport?&searchValue="+searchValue+"&payGroup="+payGroup+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate;
		
		if($("#payGroup").val() == "custom"){
			var fromDate = $("#fromDate").val();
			var toDate = $("#toDate").val();
			if(fromDate == null || fromDate == '' || fromDate == 'undefined'){
				$('#fromDateDiv').find('p.jquery_form_error_message').remove();
			  	$("input#fromDate").after("<p class='jquery_form_error_message'>From Date is required.</span>");
			}else{
				$('#fromDateDiv').find('p.jquery_form_error_message').remove(); 
			}
			if(toDate == null || toDate == '' || toDate == 'undefined'){
				$('#toDateDiv').find('p.jquery_form_error_message').remove();
			  	$("input#toDate").after("<p class='jquery_form_error_message'>To Date is required.</span>");
			}else{
				$('#toDateDiv').find('p.jquery_form_error_message').remove(); 
			}
			if(fromDate != null && fromDate != '' && fromDate != 'undefined' && toDate != null && toDate != '' && toDate != 'undefined'){
				payrollTaxAndWagesList(url);
				$("#payrollTaxAndWagesDiv").show();
			}
		}else{
			if(freqStartDate != null && freqStartDate != '' && freqStartDate != 'undefined' && freqEndDate != null && freqEndDate != '' && freqEndDate != 'undefined'){
				$("#fromDate").val("");
				$("#toDate").val("");
				payrollTaxAndWagesList(url);
				$("#payrollTaxAndWagesDiv").show();
			}else{
				var result = "";
				result = result +"	<div class='project_inn_mask'></div>";
				result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
				result = result +"		<p>Please select PayGroup and PayPeriod</p>";
				result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
				result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='removePopUp();' style='background-color: #FF0000; width : 100%'></b>";
				result = result +"	</div>";
				$("#popUp").append(result);
				$("#remove_user_pop").slideDown(200);
				$(".project_inn_mask").fadeIn(200);
			}
		}
}

function removePopUp(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}

function payrollTaxAndWagesList(url){
	var result = "";
	result += '<table id="payrollTaxAndWagesListTable" class="display" cellspacing="0" width="100%" >';
    result += '<thead>';
    result += '<tr>';
    result += '<th>Emp ID</th>';
    result += '<th>Emp Name</th>';
    result += '<th>SSN</th>';
    result += '<th>Gross Wages</th>';
    result += '<th>Federal</th>';
    result += '<th>SOC Sec</th>';
    result += '<th>Medicare</th>';
    result += '<th>FUTA</th>';
    result += '<th>State</th>';
    result += '<th>ST</th>';
    result += '<th>Local Tax</th>';
    result += '<th>Suta</th>';
    result += '<th>YTD</th>';
    result += '</tr>';
    result += '</thead>';
    result += '<tbody>';
    result += '</tbody>';
    result += '</table>';
    $("#payrollTaxAndWagesList").empty();
    $("#payrollTaxAndWagesList").append(result);
    payrollTaxAndWagesPagination(url); 
}

function payrollTaxAndWagesPagination(url){
	var colIndex = 0;
    $('#payrollTaxAndWagesListTable').DataTable( { 
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
            { "data": "ssn"},
            { "data": "grossWages" },
            { "data": "federal" },
            { "data": "socSec" },
            { "data": "medicare"},
            { "data": "futa" },
            { "data": "stateCode" },
            { "data": "stateTax" },
            { "data": "localTax" },
            { "data": "suta" },
            { "data": "ytd" },
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "empId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
		    	  		if(opt.empId !=null && opt.empId != '' && opt.empId !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.empId+'" >'+opt.empId+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		}
		              return result; 
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "empName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.empName !=null && opt.empName != '' && opt.empName !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.empName+'" >'+opt.empName+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
            	  return result; 
              }
	          },{
            "targets": colIndex++,
            "data": "ssn",
            "render": function ( data, type, opt, meta ) {
            	 var result = "";
            	 if(opt.ssnNumber !=null && opt.ssnNumber != '' && opt.ssnNumber !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.ssnNumber+'" >'+opt.ssnNumber+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
            	return result; 
            }
          }, {
              "targets": colIndex++,
              "data": "grossWages",
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
            	   if(opt.grossPay !=null && opt.grossPay != '' && opt.grossPay !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.grossPay+'" >$ '+opt.grossPay+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
            	    return result; 
              }
            },{
	    	  	"targets": colIndex++,
	    	  	"data": "federal",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.federalTax !=null && opt.federalTax != '' && opt.federalTax !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.federalTax+'" >$ '+opt.federalTax+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
  	                return result; 
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "socSec",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.socialSecTax !=null && opt.socialSecTax != '' && opt.socialSecTax !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.socialSecTax+'" >$ '+opt.socialSecTax+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
	    	  		return result; 
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "medicare",
                  "render": function ( data, type, opt, meta ) {
                	var result = "";
  	    	  		if(opt.medicare !=null && opt.medicare != '' && opt.medicare !='undefined'){
  	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.medicare+'" >$ '+opt.medicare+'</div>';
  	        		}else{
  	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
  	        		}
  	    	  		return result; 
                  }
               }, {
                    "targets": colIndex++,
                    "data": "futa",
                    "render": function ( data, type, opt, meta ) {
                  		var result = "";
    	    	  		if(opt.futa !=null && opt.futa != '' && opt.futa !='undefined'){
    	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.futa+'" >$ '+opt.futa+'</div>';
    	        		}else{
    	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
    	        		}
    	    	  		return result; 
                    }
                }, {
                      "targets": colIndex++,
                      "data": "stateCode",
                      "render": function ( data, type, opt, meta ) {
                   		var result = "";
      	    	  		if(opt.resState !=null && opt.resState != '' && opt.resState !='undefined'){
      	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.resState+'" >'+opt.resState+'</div>';
      	        		}else{
      	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
      	        		}
      	    	  		return result; 
                      }
                }, {
                    "targets": colIndex++,
                    "data": "stateTax",
                    "render": function ( data, type, opt, meta ) {
                 		var result = "";
    	    	  		if(opt.stateTax !=null && opt.stateTax != '' && opt.stateTax !='undefined'){
    	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.stateTax+'" >$ '+opt.stateTax+'</div>';
    	        		}else{
    	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
    	        		}
    	    	  		return result; 
                    }
                }, {
                    "targets": colIndex++,
                    "data": "localTax",
                    "render": function ( data, type, opt, meta ) {
                 		var result = "";
    	    	  		if(opt.localTax !=null && opt.localTax != '' && opt.localTax !='undefined'){
    	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.localTax+'" >$ '+opt.localTax+'</div>';
    	        		}else{
    	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
    	        		}
    	    	  		return result; 
                    }
                }, {
                    "targets": colIndex++,
                    "data": "suta",
                    "render": function ( data, type, opt, meta ) {
                 		var result = "";
    	    	  		if(opt.suta !=null && opt.suta != '' && opt.suta !='undefined'){
    	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.suta+'" >'+opt.suta+'</div>';
    	        		}else{
    	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
    	        		}
    	    	  		return result; 
                    }
                }, {
                    "targets": colIndex++,
                    "data": "ytd",
                    "render": function ( data, type, opt, meta ) {
                 		var result = "";
    	    	  		if(opt.ytdTaxes !=null && opt.ytdTaxes != '' && opt.ytdTaxes !='undefined'){
    	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.ytdTaxes+'" >$ '+opt.ytdTaxes+'</div>';
    	        		}else{
    	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
    	        		}
    	    	  		return result; 
                    }
                }],
                "scrollCollapse": false,
    	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
//     	    	"aaSorting": [[1,'desc']],
    	    	"bLengthChange": true,
    	    	"bDestroy": true,
    	    	"ordering": true,
    	    	"oLanguage": {
    	     		"sSearch": "",
    	     		 "sZeroRecords": "", "sEmptyTable": "", 
    	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
    	     }
                });
       $("#payrollTaxAndWagesListTable").wrap("<div class='overflow_Auto width100percnt'></div>")
//        $("input[type=search]").hide();
       $("#payrollTaxWageSearch").keyup(function(e) {
    	   if(e.which === 13) {
    	   runPayrollTaxAndWagesReport();
    	   }
    	});
}

</script>
<script>	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});
</script>

 <script>
	$(function() {
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatEndDate = '';
		  /* if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatEndDate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatEndDate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatEndDate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatEndDate = 'yy-dd-mm'; 
		  } */
		//to set from date to current month
	       var currentTime = new Date()
	       var minDate = new Date(currentTime.getFullYear(), currentTime.getMonth(), +1);
		$('#fromDate').datepicker({
			dateFormat : "yy-mm-dd",
			changeMonth: true,
			changeYear: true,
// 			minDate: 0,
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate());
			$("#toDate").datepicker("option", "minDate", dt);
// 			$("#fromDateHid").val(dt);
			}
		});
		
	  $('#toDate').datepicker({
			dateFormat : "yy-mm-dd",
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate());
			$("#fromdate").datepicker("option", "maxDate", dt);
// 			$("#toDateHid").val(dt);
			}
		});
	});
</script>

<script>
function resetData(){
	document.getElementById("payroll_Taxes_Wages_form").reset(); 
	$("#payrollTaxAndWagesDiv").hide();
	$("#fromDate").val('');
	$("#toDate").val('');
	$("#freqStartDate").val('');
	$("#freqEndDate").val('');
	$("#payPeriod").empty();
	
// 	document.getElementById("payrollTaxAndWagesList").reset();
}
</script>

<jsp:include page="../masterFooter.jsp" />