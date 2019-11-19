<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
$(document).ready(function() {
	getFrequencyYears();
	
	var country = '${SourceLeadWeb_country}';
	if(country == 'India'){
		$('#revenueCurrency').html('INR');
		$('#empSalCurrency').html('INR');
		$('#contractorsSalCurrency').html('INR');
		$('#grossCurrency').html('INR');
	}else if(country == 'Australia'){
		$('#revenueCurrency').html('AUD');
		$('#empSalCurrency').html('AUD');
		$('#contractorsSalCurrency').html('AUD');
		$('#grossCurrency').html('AUD');
		
	}else if(country == 'United States'){
		$('#revenueCurrency').html('USD');
		$('#empSalCurrency').html('USD');
		$('#contractorsSalCurrency').html('USD');
		$('#grossCurrency').html('USD');
		
	}else if(country == 'Singapore'){
		$('#revenueCurrency').html('SGD');
		$('#empSalCurrency').html('SGD');
		$('#contractorsSalCurrency').html('SGD');
		$('#grossCurrency').html('SGD');
		
	}else if(country == 'United Kingdom'){
		$('#revenueCurrency').html('GBP');
		$('#empSalCurrency').html('GBP');
		$('#contractorsSalCurrency').html('GBP');
		$('#grossCurrency').html('GBP');
		
	}else if(country == 'France'){
		$('#revenueCurrency').html('EUR');
		$('#empSalCurrency').html('EUR');
		$('#contractorsSalCurrency').html('EUR');
		$('#grossCurrency').html('EUR');
	}
	
	closureReport();
});
</script>



<section class="content-wraper">
	
		<section class="content-header clearfix">
			<div class="pull-left"><h2 class="pull-left">Closure Report</h2></div>
			<div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/reportsDashboard'/>">Reports	Dashboard</a> &raquo; <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/financialReportsList'/>">Financial Reports</a> &raquo; Closure Report</div>
		</section>
		
		<div class="timesheet_container datatable_filter">
			<div class='content-area content-new clearfix'>
			   <div  id="reportsDiv">
								<div class="reportHead">
							    	<ul class="clearfix pull-right">
							    		<li>
											<div class="timeReFi noPad mr10">
												<select id="frequencyYear" name="frequencyYear"	class="form-control chosen-select">
												
												</select>
											</div>
											
												<div class="timeReFi time_months noPad">
													<select id="frequencyMonth" name="frequencyMonth" class="form-control chosen-select">
													      <option value="January" ${currentMonth == 'January' ? 'selected' : '' } >January</option>
														  <option value="February" ${currentMonth == 'February' ? 'selected' : '' }>February</option>
													      <option value="March" ${currentMonth == 'March' ? 'selected' : '' }>March</option>
													      <option value="April" ${currentMonth == 'April' ? 'selected' : '' }>April</option>
													      <option value="May" ${currentMonth == 'May' ? 'selected' : '' }>May</option>
													      <option value="June" ${currentMonth == 'June' ? 'selected' : '' }>June</option>
													      <option value="July" ${currentMonth == 'July' ? 'selected' : '' }>July</option>
													      <option value="August" ${currentMonth == 'August' ? 'selected' : '' }>August</option>
													      <option value="September" ${currentMonth == 'September' ? 'selected' : '' }>September</option>
													      <option value="October" ${currentMonth == 'October' ? 'selected' : '' }>October</option>
													      <option value="November" ${currentMonth == 'November' ? 'selected' : '' }>November</option>
													      <option value="December" ${currentMonth == 'December' ? 'selected' : '' }>December</option>
													</select>
												</div>
											<div id="" class="repSelBtn"> <button type="button" class="btn btn-primary btn-sm" onclick="closureReport();">Run</button></div> 
							    		</li>
							    	</ul>
							    </div> 
			   </div>
			   	
			
	<div class="clearfix"></div>
	<div id="closureReportDiv"></div>
	<div class="clearfix mt10">
		<div class="col-md-4">
			<div class="colmd4 bgWhite">
				<div class="innerHours">
					<h4><span id="empBillableHours"> 0 </span> <span>Hrs</span></h4>
				</div>
				<div class="footerFlex clearfix">
					<div class="col-md-6 text-left">
					Employees (<span id="employersCountDiv"> 0 </span>)
					</div>
				<div class="col-md-6 text-right">
					Billable Hours
				</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="colmd4 bgWhite">
				<div class="innerHours">
					<h4><span id="contractorsBillableHours"> 0 </span> <span>Hrs</span></h4>
				</div>
				<div class="footerFlex clearfix">
					<div class="col-md-6 text-left">
					Contractors (<span id="contractorsCountDiv"> 0 </span>)
					</div>
				<div class="col-md-6 text-right">
					Billable Hours
				</div>
				</div>
			</div>
		</div>
		
		
		<div class="col-md-4">
			<div class="colmd4 bgWhite">
				<div class="innerHours">
					<h4><span id="totalBillableHours"> 0 </span> <span>Hrs</span></h4>
				</div>
				<div class="footerFlex clearfix">
					<div class="col-md-6 text-left">
					Total Billable Hours
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="colmd4 bgWhite">
				<div class="innerHours">
					<h4><span id="revenueCurrency"></span> <span id="revenueAmount">0</span></h4>
				</div>
				<div class="footerFlex clearfix">
					<div class="col-md-6 text-left">
					Revenue
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="colmd4 bgWhite">
				<div class="innerHours">
					<h4><span id="empSalCurrency"></span> <span id="empSalaryAmount">0</span></h4>
				</div>
				<div class="footerFlex clearfix">
					<div class="col-md-6 text-left">
					Employee's Salary
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="colmd4 bgWhite">
				<div class="innerHours">
					<h4><span id="contractorsSalCurrency"></span> <span id="contractorsPaymentsAmount">0</span></h4>
				</div>
				<div class="footerFlex clearfix">
					<div class="col-md-6 text-left">
					Contractor's Payments
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="colmd4 bgWhite">
				<div class="innerHours">
					<h4><span id="grossCurrency"></span> <span id="grossMarginAmount">0</span></h4>
				</div>
				<div class="footerFlex clearfix">
					<div class="col-md-6 text-left">
					Gross Margin
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
	
	
	</div>
		</div>

	</section>
	
	<script type="text/javascript">
	function getFrequencyYears(){
		var currentDate = new Date().getFullYear();
	    var minDate = currentDate - 10;
	    var select = document.getElementById('frequencyYear');

		for (var i = currentDate; i >= minDate; i--){
		    var opt = document.createElement('option');
		    opt.value = i;
		    opt.innerHTML = i;
		    select.appendChild(opt);
		}
  	}
	</script>
	
	
	<script>  
	function closureReport(){ 
		       var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
               var frequencyYear = $("#frequencyYear").val();
               var frequencyMonth = $("#frequencyMonth").val();
               $("#closureReportDiv").attr('style','position:absolute; width:100%; height:430px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
                 $.ajax({
	      	          type : "POST",
        	      	  url: sourceleadUrl+"/getMonthlyClosureReportDetails?frequencyYear="+frequencyYear+"&frequencyMonth="+frequencyMonth,		
        		      success :function(result) {
        		    	   
        		    	      $("#empBillableHours").html(result.employeeBillableHours);
        		    	      $("#employersCountDiv").html(result.employeeCount);
        		    	      
        		    	      $("#contractorsBillableHours").html(result.contractorsBillableHours);
        		    	      $("#contractorsCountDiv").html(result.contractorsCount);
        		    	      
        		    	      $("#totalBillableHours").html(result.totalBillableHours);
        		    	      
        		    	      $("#revenueAmount").html(result.revenue);
        		    	      
        		    	      $("#empSalaryAmount").html(result.employeeSalary);
        		    	      $("#contractorsPaymentsAmount").html(result.contractorsPayment);
        		    	      $("#grossMarginAmount").html(result.grossAmount);
        		    	      
	        		    	  $("#closureReportDiv").removeAttr('style');
	        		    	  
        		      }
        	    });   
	}
</script>
	
