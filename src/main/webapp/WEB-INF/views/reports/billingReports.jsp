<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

<script src="resources/js/pie.js"></script> 
<script src="resources/js/amcharts.js"></script> 
<script src="resources/js/serial.js"></script> 

<style>
#billing_chartdiv {
  	width:100%; height:300px;
}																		
</style>

<script> 
$(document).ready(function(){
	var invoiceTYpe = "Receivable";
	getBillingReports(invoiceTYpe);
});
</script>


<section class="content-wraper">

<section class="content-header clearfix">
			<div class="pull-left">
				<h2>Billing Reports </h2>
			</div>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/userReports'/>">Dashboard Reports</a> &raquo; Billing Reports </div>
</section>


<div class="vendor_wrap clearfix">

   <div class="timesheet_head">
<div class="pull-left reportBtns reportBtns_custom mt5 mb10">
<h4 class="pull-left mr">Billing Report</h4>
	  <div class="reportBtn reportBtn_custom_btn select-field pull-left">
		<select class="form-control" id="billingReportType" onChange="flushBillingCustom(); getBillingReports('Receivable');custBillingDates();">
		  <option value="THIS_MONTH">This Month</option>
		  <option value="THIS_QUARTER">This quarter</option>
		  <option value="THIS_YEAR">This Year</option>
		  <option value="LAST_30DAYS">Last 30 days</option>
		  <option value="LAST_MONTH">Last Month</option>
		  <option value="LAST_QUARTER">Last quarter</option>
		  <option value="LAST_YEAR">Last Year</option>
		   <option value="CUSTOM">Custom</option> 
		</select>
	 </div>	
	 
	  <span class="dashboard-datepkr cusShowBilling">
	 <input type="text" class="form-control" value="" id="billingReportStartDate" name="billingReportStartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date"> <i class="fa fa-calendar"></i>
	 <p id="billingErrorReportStartDate" class="ReError"></p>
	 </span>
	<span class="dashboard-datepkr cusShowBilling">
	<input type="text"  class="form-control" value="" id="billingReportEndDate" name="billingReportEndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date"><i class="fa fa-calendar"></i>
	<p id="billingErrorReportEndDate" class="ReError"></p>
	</span>
	<span class="cusShowBilling"><input type="button" value="Submit" class="btn btn-primary repBtn" onclick="getCustomBillingReports('Receivable');" /></span>
        <div class="clearfix"></div>
      </div>
 </div>
 
 <div class="clearfix"></div>
          <div class="clearfix mt10">
              <div class="col-md-3">
                  
                  <div class="clearfix billing_count">
                      <h5>${SourceLeadWeb_currencyCode}</h5><span class="rupee_usd" id="netIncome"></span>
                      <span class="text-info">Net Income</span>
                  </div>
                  
                  <div class="clearfix billingCount borderGreen">
                      <h5>${SourceLeadWeb_currencyCode}</h5><span class="rupee_usd" id="receivedAmount"></span>
                      <span class="text-info">Income</span>
                  </div>
                  <div class="clearfix billingCount borderBlue">
                      <h5>${SourceLeadWeb_currencyCode}</h5><span class="rupee_usd" id="payableAmount"></span>
                      <span class="text-info">Expenses</span>
                  </div>
                  
              </div>
<!--               <div class="col-md-9">
                  <div id="billing_chartdiv">
                    
                  </div>
              </div> -->
              
              <div class="col-md-9 RepoPad">
		<div class="project_side_wrap_inn_tabs">
          <div class="clearfix">
            <div id="billing_chartdiv">
            
            <!-- <div id="tab-1" class="project_tabs_inn datatable_filter">
            </div> -->

			<div id="tab-2" class="project_tabs_inn datatable_filter">
          </div>
        </div> 
              </div>
              
          </div>
          </div>
          <!-- Report Chart desing -->

<!--  Report Div starts here Ends: Rajashekar -->  
  
  
  
  
  </div>
  </div>
 </section>
 
 <script type="text/javascript"> 
	function addBillingValidClass(){
		 var StartDate = document.getElementById("billingReportStartDate").value;
		 if(StartDate){
			 $('#billingErrorReportStartDate').empty() 
		 }  
		 
		 var EndDate = document.getElementById("billingReportEndDate").value;
		 if(EndDate){
			 $('#billingErrorReportEndDate').empty(); 
		 }
}
</script>

<script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatRepo = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatRepo = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatRepo = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatRepo = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatRepo = 'yy-dd-mm'; 
		  }
		$('#billingReportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			onSelect : function(selected) {
				var dt = new Date(selected);
				//var dt = toDate("#billingReportStartDate");
				/* dt.setDate(dt.getDate() + 1); */
				$("#billingReportEndDate").datepicker("option", "minDate", selected);
				addBillingValidClass();
			}
		
		});
		
		$('#billingReportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				//var dt = toDate("#billingReportEndDate");
				//dt.setDate(dt.getDate() - 1);
				$("#billingReportStartDate").datepicker("option", "maxDate", selected);
				// $( "#billingReportStartDate" ).datepicker({  maxDate: new Date() });
				addBillingValidClass();
			}
		});
		
	});
</script>

 <script>
function getCustomBillingReports(invoiceType){
	var billingReportType = $("#billingReportType").val();  
	var startDate = $("#billingReportStartDate").val();
	var endDate = $("#billingReportEndDate").val();
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#billingErrorReportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#billingErrorReportEndDate").empty().append("End Date is Required") ; 
	}else{
		$.ajax({
	         type : "GET",
	         url: sourceleadUrl+"/getBillingReportByFrequency?"+tokenParamName+"="+tokenValue+"&billingReportType="+billingReportType+"&invoiceType="+invoiceType+"&startDate="+startDate+"&endDate="+endDate,     
	         success :function(data) {
	        	 var json = JSON.parse(data);
	        	 var result = "";
	        	 result = result +'<div class="project_tabs_inn_head">';
	        	 result = result +'</div>';
	        	 result = result +'<div class="require_wrap_table dataFilterNew">';
	        	 result = result +"<table id='customBillingReportPagination' class='display' cellspacing='0' width='99.9%' >"; 
	        	 result = result +'<thead>';
	        	 result = result +'<tr>';
	        	 result = result +'<th>Invoice Id</th>';
	        	 result = result +'<th>Client</th>';
	        	 result = result +'<th>Invoice Date</th>';
	        	 result = result +'<th>Toatl</th>';
	        	 result = result +'<th>Due Date</th>';                    
	        	 result = result +'<th>Due Amount</th>';
	        	 result = result +'</tr>';
	        	 result = result +'</thead>';	
	        	 result = result +'<tbody>';
	        	 for (var i = 0; i < json.length; i++) { 
	        		 var opt = json[i]; 
	        		 $("#netIncome").html(opt.netIncome);
		        	 $("#receivedAmount").html(opt.recievableAmount);
		        	 $("#payableAmount").html(opt.payableAmount); 
	        		 result = result +'<tr>';
		        	 result = result +'<td><div>'+opt.invoiceId+'</div></td>';
		        	 result = result +'<td><div>'+opt.orgName+'</div></td>';
		        	 result = result +'<td><div>'+opt.invoiceDateStr+'</div></td>';
		        	 result = result +'<td><div>'+opt.total+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueDateStr+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueAmount+'</div></td> ';
		        	 result = result +'</tr>'; 
	        	 }
	        	 
	                result = result +'</tbody>';
	                result = result +'</table>';
	                result = result +'</div>';
	                $("#tab-1").empty();
	        	   $("#tab-1").append(result);
	        	   customBillingReportPagination();
	        	   getCustomPayableBillingReports('payables');
	        },
	          
	     });  
	}
}
</script>


<script>
function getCustomPayableBillingReports(invoiceType){
	var billingReportType = $("#billingReportType").val();  
	var startDate = $("#billingReportStartDate").val();
	var endDate = $("#billingReportEndDate").val();
	if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#billingErrorReportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#billingErrorReportEndDate").empty().append("End Date is Required") ; 
	}else{
		$.ajax({
	         type : "GET",
	         url: sourceleadUrl+"/getBillingReportByFrequency?"+tokenParamName+"="+tokenValue+"&billingReportType="+billingReportType+"&invoiceType="+invoiceType+"&startDate="+startDate+"&endDate="+endDate,     
	         success :function(data) {
	        	 var json = JSON.parse(data);
	        	 var result = "";
	        	 result = result +'<div class="project_tabs_inn_head">';
	        	 result = result +'</div>';
	        	 result = result +'<div class="require_wrap_table dataFilterNew">';
	        	 result = result +"<table id='customPayableReportPagination' class='display' cellspacing='0' width='99.9%' >"; 
	        	 result = result +'<thead>';
	        	 result = result +'<tr>';
	        	 result = result +'<th>Invoice Id</th>';
	        	 result = result +'<th>Client</th>';
	        	 result = result +'<th>Invoice Date</th>';
	        	 result = result +'<th>Toatl</th>';
	        	 result = result +'<th>Due Date</th>';                    
	        	 result = result +'<th>Due Amount</th>';
	        	 result = result +'</tr>';
	        	 result = result +'</thead>';	
	        	 result = result +'<tbody>';
	        	 for (var i = 0; i < json.length; i++) { 
	        		 var opt = json[i]; 
	        		 $("#netIncome").html(opt.netIncome);
		        	 $("#receivedAmount").html(opt.recievableAmount);
		        	 $("#payableAmount").html(opt.payableAmount); 
	        		 result = result +'<tr>';
		        	 result = result +'<td><div>'+opt.invoiceId+'</div></td>';
		        	 result = result +'<td><div>'+opt.orgName+'</div></td>';
		        	 result = result +'<td><div>'+opt.invoiceDateStr+'</div></td>';
		        	 result = result +'<td><div>'+opt.total+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueDateStr+'</div></td> ';
		        	 result = result +'<td><div>'+opt.dueAmount+'</div></td> ';
		        	 result = result +'</tr>'; 
	        	 }
	        	 
	                result = result +'</tbody>';
	                result = result +'</table>';
	                result = result +'</div>';
	                $("#tab-1").empty();
	        	   $("#tab-1").append(result);
	        	   customPayableReportPagination();
	        },
	          
	     });  
	}
}
</script>
<script>
var chart = AmCharts.makeChart( "billing_chartdiv", {
  "type": "serial",
  "theme": "light",
  "marginRight": 40,
  "marginLeft": 40,
  "autoMarginOffset": 20,
  "dataDateFormat": "YYYY-MM-DD",
  "valueAxes": [ {
    "id": "v1",
    "axisAlpha": 0,
    "position": "left",
    "ignoreAxisWidth": true
  } ],
  "balloon": {
    "borderThickness": 1,
    "shadowAlpha": 0
  },
  "graphs": [ {
    "id": "g1",
    "balloon": {
      "drop": true,
      "adjustBorderColor": false,
      "color": "#ffffff",
      "type": "smoothedLine"
    },
    "fillAlphas": 0.2,
    "bullet": "round",
    "bulletBorderAlpha": 1,
    "bulletColor": "#FFFFFF",
    "bulletSize": 5,
    "hideBulletsCount": 50,
    "lineThickness": 2,
    "title": "red line",
    "useLineColorForBulletBorder": true,
    "valueField": "value",
    "balloonText": "<span style='font-size:18px;'>[[value]]</span>"
  } ],
  "chartCursor": {
    "valueLineEnabled": true,
    "valueLineBalloonEnabled": true,
    "cursorAlpha": 0,
    "zoomable": false,
    "valueZoomable": true,
    "valueLineAlpha": 0.5
  },
  "valueScrollbar": {
    "autoGridCount": true,
    "color": "#000000",
    "scrollbarHeight": 50
  },
  "categoryField": "date",
  "categoryAxis": {
    "parseDates": true,
    "dashLength": 1,
    "minorGridEnabled": true
  },
  "export": {
    "enabled": true
  },
  "dataProvider": [ {
    "date": "2012-01-27",
    "value": 13
  }, {
    "date": "2012-08-01",
    "value": 13
  },  {
    "date": "2012-09-04",
    "value": 47
  }, {
    "date": "2012-10-07",
    "value": 21
  }, {
    "date": "2012-11-03",
    "value": 68
  }, {
    "date": "2012-11-04",
    "value": 65
  }, {
    "date": "2012-11-05",
    "value": 71
  }, {
    "date": "2012-11-06",
    "value": 75
  }, {
    "date": "2012-11-07",
    "value": 74
  },{
    "date": "2012-12-03",
    "value": 65
  }, {
    "date": "2013-01-02",
    "value": 67
  }, ]
} );
</script>
<script>
function getBillingReportsChart(invoiceType,json){
	/* ;;; */
	 $("#netIncome").html(json.netAmount);
	 $("#receivedAmount").html(json.receivedAmount);
	 $("#payableAmount").html(json.payableAmount); 
	 
	 //startatatat============
	 var chartData = [];
	 

  var firstDate = new Date();
  for (var i = 0; i < json.billingGraphBean.length; i++) { 
		 var opt = json.billingGraphBean[i]; 
        chartData.push({
        	country: opt.date,
        	year2004: opt.income,
        	year2005: opt.expenses
      });
  }
	var chart = AmCharts.makeChart("billing_chartdiv", {
		"theme": "light",
	    "type": "serial",
	    "dataProvider":chartData ,
	    "valueAxes": [{
	        "unit": "",
	        "position": "left",
	        "title": "Amount",
	    }],
	    "startDuration": 1,
	    "graphs": [{
	        "balloonText": "Income in [[category]] (2004): <b>[[value]]</b>",
	        "fillAlphas": 0.9,
	        "lineAlpha": 0.2,
	        "title": "2004",
	        "type": "column",
	         "columnWidth":0.5,
	        "valueField": "year2004"
	    }, {
	        "balloonText": "Expenses in [[category]] (2005): <b>[[value]]</b>",
	        "fillAlphas": 0.9,
	        "lineAlpha": 0.2,
	        "title": "2005",
	        "type": "column",
	        "clustered":true,
	        "columnWidth":0.5,
	        "valueField": "year2005"
	    }],
	    "plotAreaFillAlphas": 0.1,
	    "categoryField": "country",
	    "categoryAxis": {
	        "gridPosition": "start"
	    },
	    "export": {
	        "enabled": true
	     }

	});
	
}
</script>

<script type="text/javascript">
	function flushBillingCustom() { 
		$('#billingReportStartDate').val('');
		$('#billingReportEndDate').val('');
		$.datepicker._clearDate('#billingReportStartDate');
		$.datepicker._clearDate('#billingReportEndDate'); 
		addBillingValidClass();
	}
</script>
 <script>
function getBillingReports(invoiceType){
	 $("#netIncome").html(0);
	 $("#receivedAmount").html(0);
	 $("#payableAmount").html(0);
	$('#billingErrorReportStartDate').empty();
	$('#billingErrorReportEndDate').empty(); 
		var billingReportType = $("#billingReportType").val();
		var startDate = null;
		var endDate = null;
		if(billingReportType!="CUSTOM"){
			$.ajax({
		    	type: "GET",
		    	 url: sourceleadUrl+"/getBillingReportByFrequency?"+tokenParamName+"="+tokenValue+"&billingReportType="+billingReportType+"&invoiceType="+invoiceType+"&startDate="+startDate+"&endDate="+endDate, 
		         success: function(data) {
		        	 /* alert(data); */
		        	 var json = JSON.parse(data);
		        	 getBillingReportsChart(invoiceType,json);
		         }
			});	
		}
		
}

</script>  


<script>
function customBillingReportPagination(){ 
    $('#customBillingReportPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
}
</script> 

<script>
function customPayableReportPagination(){ 
    $('#customPayableReportPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
}
</script> 
<script>
function custBillingDates(){
	var billingReportType = $("#billingReportType").val();
	if(billingReportType=="CUSTOM"){
	    $(".cusShowBilling").show();
	}
	if(billingReportType!="CUSTOM"){
	    $(".cusShowBilling").hide();
	}
}
</script>

<jsp:include page="../masterFooter.jsp" />