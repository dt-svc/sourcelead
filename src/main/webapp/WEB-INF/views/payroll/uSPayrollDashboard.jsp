<%-- <jsp:include page="../masterHeader.jsp" /> --%>
<%-- <jsp:include page="../masterSideNav.jsp" /> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/amcharts.js?staticTime=${staticTime}"></script>
<script src="/resources/js/serial.js?staticTime=${staticTime}"></script>
<script src="/resources/js/pie.js?staticTime=${staticTime}"></script>

<script>
$(document).ready(function(){
	nextPayrollDate();
});

</script>

 <!-- Content Area -->
<!-- <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Total Pays</h2></div>

      <div class="pull-right brud-crum">Home &raquo; Total Pays</div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Total Pays</div>
    </section>  -->
    
	<div class="clearfix project_wrap" id="dashboardDiv">
	<div class="mt mb clearfix">
		<div class="col-md-8">
		  <div class="positionRelative clearfix"> 
            <div class="col-md-4 positionRelative noPad col-md-offset-8">
        <select class="chosen-select" id="payFrequency" name = "payFrequency" onchange="nextPayrollDate();">

         <c:if test="${not empty payFrequency}">
         <option selected value="${payFrequency}">${payFrequency}</option>
        </c:if>
        <c:if test="${empty payFrequency}"> 
            <option selected value="Weekly">Weekly</option>
   			<option value="Bi-Weekly">Bi-Weekly</option>
			<option value="Monthly">Monthly</option>
			<option value="Annually">Annually</option>
		</c:if> 
			
			<!-- <option value="Bi-Weekly">Bi-Weekly</option>
			<option value="Monthly">Monthly</option>
			<option value="Annually">Annually</option> -->
        </select>
				
	
    <div class="clearfix pull-lefet col-md-12 paddingRight" id="customLeaves" style="display: none; width: 346px; position: absolute; right: 0px; background: #fff; padding: 10px; padding-right: 10px; box-shadow: 0px 0px 0px 1px #ccc; top:auto; z-index: 99;">
    <div class="pull-left">
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="Date" id="datepicker4" placeholder="From" class="form-control calendar input-sm" type="text">
    </div>
    <div class="pull-left mr5" style="width: 140px">
    <input value="" name="Date" id="datepicker2" placeholder="To" class="form-control calendar input-sm" type="text">
    </div>
    <button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
    </div>
    </div>
      </div>
          </div>
		<div id="agWeekdays" style="width: 100%; height: 250px; display: none;" class="amchartAnchor-Hide"></div>
			<div id="fifteenDays" style="width: 100%; height: 250px; display: none;" class="amchartAnchor-Hide"></div>
			<div id="oneMonth" style="width: 100%; height: 250px; display: none;" class="amchartAnchor-Hide"></div>
			<div id="threeMonths" style="width: 100%; height: 250px; display: none;" class="amchartAnchor-Hide"></div>
			<div id="agYear" style="width: 100%; height: 250px;" class="amchartAnchor-Hide"></div>
		<div class="clearfix"></div>
		</div>
		<div class="col-md-4">
			
			<div class="clearfix row">
				<h4 class="text-muted col-md-12 margin-top0">Next Payroll</h4>
			</div>
			
			<div class="clearfix mt">
				<div class="media">
				  <div class="media-left">
					<i class="calendarFrequency"></i>
				  </div>
				  <div class="media-body">
					<h5 class="media-heading text-muted">Frequency :</h5>
					<p class="fontSmall hidden">01/11-2018 - 30/11/2018</p>
					<div class="clearfix mt5">
						<input type="text" id="payrollFrequency" value="" class="inputDisable" readonly>
					</div>
				  </div>
				</div>
			</div>
			<div class="clearfix mt mb">
				<div class="media">
				  <div class="media-left">
					<i class="calendarRundate"></i>
				  </div>
				  <div class="media-body">
					<h5 class="media-heading text-primary">Run Date :</h5>
					<p class="fontSmall hidden">29/11/2018</p>
					<div class="clearfix mt5">
						<input type="text" id="payRunDate" value="" class="inputDisable" readonly>
					</div>
				  </div>
				</div>
			</div>
			<div class="clearfix">
				<div class="media">
				  <div class="media-left">
					<i class="calendarPaydate"></i>
				  </div>
				  <div class="media-body">
					<h5 class="media-heading text-green">Pay Date :</h5>
					<p class="fontSmall hidden">02/12/2018</p>
					<div class="clearfix mt5">
						<input type="text" id="payCheckDate" value="" class="inputDisable" readonly>
					</div>
				  </div>
				</div>
			</div>
<div class="clearfix mt col-md-8 padding_left">
    <a class="btn btn-default btn-block btnBgColor" onclick="overviewRedirection();">Initiate Payroll</a>
 </div>

		</div>
  <div class="clearfix"></div>

  <div class="col-md-12">
    <h4 class="clearfix">Previous Payroll</h4>
    <div class="require_wrap_table clearfix tableHDRBgColor">
           <div id="recentPayrollDataGrid"></div>
          <div class="clearfix mt" id="recentPayrollDataForViewMore">
          	<!-- <a href="#" class="btn pull-right btn-sm btnBgColor anchorBlack">View more...</a> -->
          </div>
          </div>
  </div>

	</div>
		
		<div class="clearfix"></div>
	</div>
<!--   </section> -->
  
  
  <!-- amCharts javascript sources --> 
<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
				});
	});
</script>
<script>
$(function() {
        $('#customDates').change(function(){
			var optval = $(this).val();
			if(optval == "custom") {
				document.getElementById("customLeaves").style.display="block";
			}else{
				document.getElementById("customLeaves").style.display="none";
			}
        });
    });
	
$("html").click(function() {
	document.getElementById("customLeaves").style.display="none";
})
$("#customLeaves, #customDates, .customDates").click(function(e) {
	e.stopPropagation();
})
$("#datepicker4, #datepicker2").datepicker();
</script>
<script>
$(function() {
    $("#payslipCalendar").datepicker({
	  showOtherMonths: true,
      selectOtherMonths: true
	});
});
</script>

<script>
  function nextPayrollDate(){
	  var payFrequency = $("#payFrequency").val();
		  $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getNextPayrollDate?"+tokenParamName+"="+tokenValue+"&payFrequency="+payFrequency, 
		        success: function(data) {
		        	if(data.payrollFrequency != null && data.payrollFrequency != ''){
		        		$("#payrollFrequency").val(data.payrollFrequency);
		        	}else{
		        		$("#payrollFrequency").val('--');
		        	}
		        	
		        	if(data.payrollCheckDate != null && data.payrollCheckDate != ''){
		        		$("#payRunDate").val(data.payrollCheckDate);
		        	}else{
		        		$("#payRunDate").val('--');
		        	}
		        	
		        	if(data.payrollRunDate != null && data.payrollRunDate != ''){
		        		$("#payCheckDate").val(data.payrollRunDate);
		        	}else{
		        		$("#payCheckDate").val('--');
		        	}
		        	
		        	/* var result = "";
		        	var frequency =$("#payrollFrequency").val();
		        	var runDate = $("#payRunDate").val();
		        	var checkDate = $("#payCheckDate").val();
		        	if(frequency != "--" && runDate != "--" && checkDate != "--")
		        	{
			        	result = result + '<a href="';
			        	result = result + '<c:url value="';
			        	result = result + '/app/payrollList?Frequency=' + frequency + '&RunDate=' + runDate + '&CheckDate=' + checkDate;
			        	result = result + '"/>"';
			        	result = result + 'class="btn btn-default btn-block btnBgColor">Initiate Payroll</a>';
		        	}
		        	else{
		        		result = result + '<a href="#" class="btn btn-default btn-block btnBgColor">Initiate Payroll</a>';
		        	}
		        	$("#initPayroll").empty();
		        	$("#initPayroll").append(result); */
		        	populateRecentRunPayrollDataGraph(data.payrollGraphData);
		        	populateRecentRunPayrollDataGrid(data.payrollGridData);
		        },
		    });
	}
  </script>




<script>
function populateRecentRunPayrollDataGraph(json){
	 var chartData = [];
	 
		 for(var i=0;i<json.length;i++){
			 var opt = json[i];
			 chartData.push({
						"column-1": opt.netPay,
						"category": opt.payrollCheckDate
					});
		 }
	
		 var chart = AmCharts.makeChart("agYear",
			{
				"type": "serial",
				"categoryField": "category",
				"startDuration": 1,
				"categoryAxis": {
					"autoRotateAngle": 0,
					"autoWrap": true,
					"gridPosition": "start",
					"axisAlpha": 0,
					"axisColor": "#FFFFFF",
					"axisThickness": 0,
					"gridAlpha": 0,
					"gridColor": "#6FC9FD",
					"gridThickness": 0
				},
				"trendLines": [],
				"graphs": [
					{
						"bullet": "round",
						"fillAlphas": 0.1,
						"id": "totalPays",
						"lineColor": "#6FC9FD",
						"title": "Totalpays",
						"valueField": "column-1"
					}
				],
				"guides": [],
				"valueAxes": [
					{
						"id": "ValueAxis-1",
						"axisAlpha": 0.5,
						"axisColor": "#6FC9FD",
						"gridAlpha": 0.5,
						"gridColor": "#6FC9FD",
						"gridThickness": 0
					}
				],
				"allLabels": [],
				"balloon": {},
				"titles": [],
				"dataProvider": chartData
			});
	
	AmCharts.checkEmptyData = function (chart) {
	    if ( 0 == chart.dataProvider.length ) {
	        chart.valueAxes[0].minimum = 0;
	        chart.valueAxes[0].maximum = 100;
	        var dataPoint = {
	            dummyValue: 0
	        };
	        dataPoint[chart.categoryField] = '';
	        chart.dataProvider = [dataPoint];
	        chart.addLabel(0, '50%', 'The Chart Contains No Data', 'center');
	        chart.chartDiv.style.opacity = 0.8;
	        chart.validateNow();
	    }
	}
	AmCharts.checkEmptyData(chart);
}
</script>

<script>
function populateRecentRunPayrollDataGrid(json){
	var result = "";
	result = result + '<table>';
	result = result + '<thead>';
	result = result + '<tr>';
	result = result + '<th>Frequency</th>';
	result = result + '<th>Pay Group</th>';
	result = result + '<th>Gross Pay</th>';
	result = result + '<th>Run Date</th>';
	result = result + '<th>Check Date</th>';
	result = result + '<th>No. Employees</th>';
	result = result + '<th>No. D.D</th>';
	result = result + '<th>No. Checks</th>';
	result = result + '<th>Net Pay</th>';
	result = result + '</tr>';
	result = result + '</thead>';
	result = result + '<tbody>';
	
	if(json != null && json != "" && json != "undefined")
	{
		for(var i=0;i<json.length;i++){
			var opt = json[i];
			var freqUUIDArgs = opt.paysheetFreqUUID;
			var periodNumArgs = opt.payPeriodNum;
			result = result + '<tr>';
			result = result + '<td>';
			result = result + '<div class="tblminwidth-175"> <a href="<c:url value="/app/viewPaySheetFrequencyData?paySheetFrequencyUUID='+freqUUIDArgs+'&approverStatus=APPROVED&payPeriodNum='+periodNumArgs+'"/>">'+opt.payFrequency+'</a></div>';
			result = result + '</td>';
			result = result + '<td><div class="tblminwidth-75">'+opt.payGroupName+'</div></td>';
			result = result + '<td><div class="tblminwidth-75">$  '+opt.grossPay+'</div></td>';
			result = result + '<td><div class="tblminwidth-75">'+opt.payrollRunDate+'</div></td>';
			result = result + ' <td><div class="tblminwidth-75">'+opt.payrollCheckDate+'</div></td>';
			result = result + '<td><div class="tblminwidth-30">'+opt.noOfEmployees+'</div></td>';
			result = result + ' <td><div class="tblminwidth-30">'+opt.noOfDirectDeposit+'</div></td>';
			result = result + '<td><div class="tblminwidth-30">'+opt.noOfCheques+'</div></td>';
			result = result + '<td><div class="tblminwidth-125 text-right">$  '+opt.netPay+'</div></td>';
			result = result + ' </tr>';
		}
	}
	else
	{
		$("#recentPayrollDataForViewMore").hide();
	}
	result = result + ' </tbody>';
	result = result + '</table>';
	$("#recentPayrollDataGrid").empty();
	$("#recentPayrollDataGrid").append(result);
}

</script>

<script>
function overviewRedirection(){
	
	$("#dashboardDiv").hide();
  	$('#payrollPayDateDivId').find('p.jquery_form_error_message').remove();
  	$('#payTypeIdDiv').find('p.jquery_form_error_message').remove();
  	$('#payPeriodDiv').find('p.jquery_form_error_message').remove();
  	$('#groupIdDiv').find('p.jquery_form_error_message').remove();
  	
  	$("#dashId").removeClass("current");
  	$("#overId").addClass("current");
	$("#overViewDiv").show();
	$("#reviewPayrollDiv").show();
	
}
</script>

<%-- <jsp:include page="../masterFooter.jsp" /> --%>
