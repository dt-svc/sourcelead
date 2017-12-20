<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <script type="text/javascript">
		window.onload = function () {
				getHolidaysList();
		 };
</script>
	
<!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Holidays List</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Holidays List</div>
        </section>
        
		<!-- Timesheet -->
<div id="emptyPopup"></div>
<div class="timesheet_out">
<div class="project_wrap">
	<div class="timesheet_head">
        	<ul>
        		<sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_MY_EXPENSES')">
				    <div class="pull-left mt5 mr">   		
				     <span class="dashboard-datepkr pull-left">
					 <input type="text" class="form-control input-sm calendar" value="" id="filterStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date">
					 <p id="reportStartDate" class="ReError"></p>
					 </span>
					 <span class="dashboard-datepkr pull-left">
					 <input type="text"  class="calendar form-control input-sm" value="" id="filterEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date">
					 <p id="reportEndDate" class="ReError"></p>
					 </span>
					 <span class="pull-left"><input type="button" value="Submit" class="btn-sm btn btn-primary" onclick="getExpensesList();" /></span>
					</div>
					
					<li class="ml"><i class="fa fa-filter"></i><span>Filter</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
	                	<div class="approved_pop">
	                    	<ul>
	                        	<li><input id="expenseAll" type="checkbox" name="expenseStatus" value="ALL" checked onclick="getExpensesList();"><label >All</label></li>
								<li><input id="expenseApproved" type="checkbox" name="expenseStatus" value ="APPROVED" onclick="getExpensesList();"><label >Approved</label></li>
								<li><input id="expenseSubmitted" type="checkbox" name="expenseStatus" value ="SUBMITTED" onclick="getExpensesList();"><label >Submitted</label></li>
								<li><input id="expenseDraft" type="checkbox" name="expenseStatus" value ="DRAFT" onclick="getExpensesList();"><label >Draft</label></li>
								<li><input id="expenseRejected" type="checkbox" name="expenseStatus" value ="REJECTED" onclick="getExpensesList();"><label >Rejected</label></li>
	                        </ul>
	                    </div>                	
	                </li>
               </sec:authorize>
               <sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES')">
			   		<li><a href="<c:url value='${appPath}/addExpense'/>"><i class="fa fa-plus"></i>Add Expenses</a></li>
			   </sec:authorize>	
			   <li title="Export"  id="hideExportExpenseLink"><a  id="export-pop"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>
					    <div id="exportPop" class="user_exp">
						   <ul class="ul_padding">
						   <li onclick="exportToPdf('pdf');" title="PDF"><i class="fa fa-file-pdf-o"></i>&nbsp; PDF</li>
						   <li onclick="exportToPdf('excel');" title="Excel"><i class="fa fa-file-excel-o"></i>&nbsp; Excel</li>
						   </ul>
						</div>
				   	</li>
               <div class="clearfix"></div>
               <input type="hidden" id="temp"/>
            </ul>
            <div class="clearfix"></div>
            
        </div>
        
    <div class="project_wrap_table datatable_filter">
    	<div id="expensesListDiv">	</div>
	</div>
	</div>
</div>  
	
  <!--   Error Message   -->
      <div class="side_mask"><div class="proj_close" onclick="activeTab();"></div></div>
  
  
  <div class="project_side_wrap">
  <div id="expensesViewDiv"></div>
  </div>
  <div class="vendor_error">
    <textarea placeholder="Enter message"></textarea>
    <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>
  
  <!--   Error Message   --> 

		
      </section>
      <!-- Content Area Ends--> 
<script>

function getExpensesList(){
	
	 $.ajax({
		 type : "POST",
		  data: jsonData,
  	      contentType : 'application/json; charset=utf-8',
  	      dataType : 'json',
          url: sourceleadUrl+"/getHolidaysList?"+tokenParamName+"="+tokenValue+"",     
          success :function(json) {
        	  var result = "";
					result = result +" <table id='holidaysPagination'>";
					result = result +"<thead>";
					result = result +"<tr>";
					result = result +"<th>Holiday Name</th>";
					result = result +"<th>Date</th>";
					result = result +"<th>Status</th>";
					result = result +"</tr>";
					result = result +"</thead>";
					result = result +"<tbody>";
					
					 if(json !=''){
			        	  for (var i = 0; i < json.length; i++) { 
			        		  var report = json[i]; 
			        		  if(report.holidayName != null && report.holidayName != '' && report.holidayName != 'undefined'){
			        			  holidayName = report.holidayName;
			        		  }else{
			        			  holidayName = "--";
			        		  }
			        		  if(report.fromDate != null && report.fromDate != '' && report.fromDate != 'undefined'){
			        			  fromDate = report.fromDate;
			        		  }else{
			        			  fromDate = "--";
			        		  }
			        		  if(report.holidayStatus != null && report.holidayStatus != '' && report.holidayStatus != 'undefined'){
			        			  holidayStatus = report.holidayStatus;
			        		  }else{
			        			  holidayStatus = "--";
			        		  }
								 result = result +"<tr>";
								 result = result +"<td><div title="+holidayName+">"+holidayName+"</div></td>";
								 result = result +"<td><div title="+fromDate+">"+fromDate+"</div></td>";
								 result = result +"<td><div>"+holidayStatus+"</div></td>";
								 result = result +"</tr>"; 
			        	  }
		        		  $("#temp").val("temp");
		        	  }else{
		        		   $("#temp").val(''); 
		        	  }
	        	 
   			
        	  result = result +" </tbody>";
        	  result = result +" </table>";
        	  $("#expensesListDiv").empty();
	          $("#expensesListDiv").append(result);
	          holidaysPagination();
          },
          
     }); 
	
	}

</script>      

<script>
function holidaysPagination(){ 
    $('#holidaysPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
		"aaSorting": [[0,'desc']],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
    $("#holidaysPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
}
</script> 

<script>

function expenseView(expenseId){
	$(".project_side_wrap").addClass("project_side_wrap_active");
	        $(".side_mask").fadeIn(200);
	        $("body").css('overflow-y', 'hidden');
	        $.ajax({
	            type : "GET",
	             url: sourceleadUrl+"/getIndividualExpense?expenseId="+expenseId,       
	             success :function(data) {
	           	  var result = "";
	           	  
	          	result = result +'<div class="project_side_wrap_inn">';                 
	        	result = result +'<h1>Expense name here</h1>';
	        	result = result +'<div class="project_side_wrap_inn_tabs">';
	        	result = result +'<ul class="project-tabs-menu">';
	        	result = result +'<li class="current"><a href="#tab-1">Details</a></li>';
	        	result = result +'				        <li><a href="#tab-2">History</a></li>                                                                                         ';
	        	result = result +'<div class="clearfix"></div>';
	        	result = result +'</ul>';

	        	result = result +'<div class="project_tabs">';
	        	result = result +'<div id="tab-1" class="project_tabs_inn">';
	        	result = result +'<div class="project_tabs_inn_table">';
	        	result = result +'<div class="project_details project_detailsExp">';

	        	result = result +'<div class="Expdit">';
	        	result = result +'<a href="#" class="btn btn-primary">Status</a>';
	        	result = result +'<a href="add-expense.html" class="btn btn-default ml10"><i class="fa fa-pencil-square-o"></i> &nbsp;Edit</a>';
	        	result = result +'<!--<a href="add-expense.html"><i class="glyphicon glyphicon-pencil"></i>Edit</a>-->';
	        	result = result +'</div>';

	        	 var json = JSON.parse(data);
	        		  var reportExpense = json[0];
	        		  var reportExpenseEntries = json;
	        		  var expenseId="";
	        		  if(reportExpense.expenseIndList[0].expenseId != null && reportExpense.expenseIndList[0].expenseId != '' && reportExpense.expenseIndList[0].expenseId != 'undefined'){
	        			  expenseId = reportExpense.expenseIndList[0].expenseId;
	        		  }else{
	        			  expenseId = "--";
	        		  }
	        		  var projectMemberId="";
	        		  if(reportExpense.expenseIndList[0].projectMemberId != null && reportExpense.expenseIndList[0].projectMemberId != '' && reportExpense.expenseIndList[0].projectMemberId != 'undefined'){
	        			  projectMemberId = reportExpense.expenseIndList[0].projectMemberId;
	        		  }else{
	        			  projectMemberId = "--";
	        		  }
	        		  var clientName="";
	        		  if(reportExpense.expenseIndList[0].clientName != null && reportExpense.expenseIndList[0].clientName != '' && reportExpense.expenseIndList[0].clientName != 'undefined'){
	        			  clientName = reportExpense.expenseIndList[0].clientName;
	        		  }else{
	        			  clientName = "--";
	        		  }
	        		  var startDateStr="";
	        		  if(reportExpense.expenseIndList[0].startDateStr != null && reportExpense.expenseIndList[0].startDateStr != '' && reportExpense.expenseIndList[0].startDateStr != 'undefined'){
	        			  startDateStr = reportExpense.expenseIndList[0].startDateStr;
	        		  }else{
	        			  startDateStr = "--";
	        		  }
	        		  var endDateStr="";
	        		  if(reportExpense.expenseIndList[0].endDateStr != null && reportExpense.expenseIndList[0].endDateStr != '' && reportExpense.expenseIndList[0].endDateStr != 'undefined'){
	        			  endDateStr = reportExpense.expenseIndList[0].endDateStr;
	        		  }else{
	        			  endDateStr = "--";
	        		  }
	        		  var noOfExpenses="";
	        		  if(reportExpense.expenseIndList[0].noOfExpenses != null && reportExpense.expenseIndList[0].noOfExpenses != '' && reportExpense.expenseIndList[0].noOfExpenses != 'undefined'){
	        			  noOfExpenses = reportExpense.expenseIndList[0].noOfExpenses;
	        		  }else{
	        			  noOfExpenses = "--";
	        		  }
	        		  var holidayStatus="";
	        		  if(reportExpense.expenseIndList[0].holidayStatus != null && reportExpense.expenseIndList[0].holidayStatus != '' && reportExpense.expenseIndList[0].holidayStatus != 'undefined'){
	        			  holidayStatus = reportExpense.expenseIndList[0].holidayStatus;
	        		  }else{
	        			  holidayStatus = "--";
	        		  }
	        	result = result +'<div class="clearfix"></div>';
	        	result = result +'<div class="project_details_cta">';
	        	result = result +'<strong>Expenses Id:</strong><span>'+expenseId+'</span><div class="clearfix"></div>';
	        	result = result +'</div>';
	        	result = result +'<div class="project_details_cta">';
	        	result = result +'<strong>Project Name:</strong><span>'+projectMemberId+'</span><div class="clearfix"></div>';
	        	result = result +'</div>';
	        	result = result +'<div class="project_details_cta">';
	        	result = result +'<strong>Client:</strong><span>'+clientName+'</span><div class="clearfix"></div>';
	        	result = result +'</div>';
	        	result = result +'<div class="project_details_cta">';
	        	result = result +'<strong>Start date:</strong><span>'+startDateStr+'</span><div class="clearfix"></div>';
	        	result = result +'</div>';
	        	result = result +'<div class="project_details_cta">';
	        	result = result +'<strong>End date:</strong><span>'+endDateStr+'</span><div class="clearfix"></div>';
	        	result = result +'</div>';
	        	result = result +'<div class="project_details_cta">';
	        	result = result +'<strong>No of Expenses:</strong><span>'+noOfExpenses+'</span><div class="clearfix"></div>';
	        	result = result +'</div>';
	        	result = result +'<div class="project_details_cta">';
	        	result = result +'<strong>Amount:</strong><span>'+holidayStatus+'</span><div class="clearfix"></div>';
	        	result = result +'</div>';
	        	result = result +'<div class="clearfix"></div>';
	        	result = result +'</div>';
	        		  
	        	result = result +'<div class="project_wrap_table expensTableView">';
	        	result = result +'<table>';
	        	result = result +'<thead>';
	        	result = result +'<tr>';
	        	result = result +'<th>Date</th>';
	        	result = result +'<th>Exp Category</th>';
	        	result = result +'<th>Description</th>';
	        	result = result +'<th>Amount</th>';
	        	result = result +'<th>Currency</th>';
	        	result = result +'<th>Payment Mode</th>';
	        	result = result +'</tr>';
	        	result = result +'</thead>';
	        	result = result +'<tbody>';

	        	
	        	var expensesCategoryId= "";
	        	var paymentMode= "";
	        	var expenseDateStr= "";
	        	var currencyType= "";
	        	var description= "";
	        	var amount= "";
	        	
	       for (var i = 0; i < reportExpenseEntries.length; i++) {	
	    	   
	          if(reportExpenseEntries[i].paymentMode != null && reportExpenseEntries[i].paymentMode != '' && reportExpenseEntries[i].paymentMode != 'undefined'){
	        		paymentMode = reportExpenseEntries[i].paymentMode;
      		  }else{
      			paymentMode = "--";
      		  }
	        	if(reportExpenseEntries[i].expenseDateStr != null && reportExpenseEntries[i].expenseDateStr != '' && reportExpenseEntries[i].expenseDateStr != 'undefined'){
	        		expenseDateStr = reportExpenseEntries[i].expenseDateStr;
      		  }else{
      			expenseDateStr = "--";
      		  }
	        	if(reportExpenseEntries[i].currencyType != null && reportExpenseEntries[i].currencyType != '' && reportExpenseEntries[i].currencyType != 'undefined'){
	        		currencyType = reportExpenseEntries[i].currencyType;
      		  }else{
      			currencyType = "--";
      		  }
	        	if(reportExpenseEntries[i].description != null && reportExpenseEntries[i].description != '' && reportExpenseEntries[i].description != 'undefined'){
	        		description = reportExpenseEntries[i].description;
      		  }else{
      			description = "--";
      		  }
	        	if(reportExpenseEntries[i].amount != null && reportExpenseEntries[i].amount != '' && reportExpenseEntries[i].amount != 'undefined'){
	        		amount = reportExpenseEntries[i].amount;
      		  }else{
      			amount = "--";
      		  }
	        	
	        	result = result +'<tr>';
	        	result = result +'<td><div>'+expenseDateStr+'</div></td>';
	        	result = result +'<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>'+expensesCategoryId+'</div></td>';
	        	result = result +'<td><div class="tbldata-dotdot-me">'+description+'</div></td>';
	        	result = result +'<td><div>'+amount+'</div></td>';
	        	result = result +'<td><div>'+currencyType+'</div></td>';
	        	result = result +'<td><div>'+paymentMode+'</div></td>';
	        	result = result +'</tr>';
	       }
	        	result = result +'</tbody>';
	        	result = result +'</table>';
	        	result = result +'</div>';
	        	result = result +'</div>';
	        	result = result +'</div>';
	        	
	        	result = result +'</div>';
	        	result = result +'</div>';

	        	result = result +'<div class="project_inn_mask"></div>';
	        	result = result +'<div class="project_error">';
	        	result = result +'<p>Delete conformation message....</p>';
	        	result = result +'<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a>';
	        	result = result +'</div>';
	        	result = result +'</div>';
 
	$("#expensesViewDiv").val('');
    $("#expensesViewDiv").append(result);
    $("#tab-1").show();
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
	             }
	        });          
	}                                                                                                                                                                                                                                                  
                                                                                                                                                                                                                                                       
</script>


<script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatExpenses = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatExpenses = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatExpenses = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatExpenses = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatExpenses = 'yy-dd-mm'; 
		  }
		$('#filterStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatExpenses,
			onSelect : function(selected) {
				$("#filterEndDate").datepicker("option", "minDate", selected);
			}
		
		});
		
		$('#filterEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatExpenses,
			onSelect : function(selected) {
				$("#filterStartDate").datepicker("option", "maxDate", selected);
			}
		});
		
	});
</script>

<script>
function activeTab(){
	$(".project_side_wrap").removeClass("project_side_wrap_active");
    $(".side_mask").fadeOut(200);
    $("body").css('overflow-y', 'scroll');
    $("#expensesViewDiv").empty();
}
</script>

<script>
function getEditExpense(eId){
	window.location.replace(sourceleadUrl+'/addExpense?expenseId='+eId);
}
</script>

<script>
function exportToPdf(type){                                                                        
	var temp = $("#temp").val();
	if(temp != null && temp != ''){
		var expenseStatus = [];
		$('input:checkbox[name=expenseStatus]:checked').each(function() 
		{
			expenseStatus.push($(this).val());
		});
		var startDate = $("#filterStartDate").val();
		var endDate = $("#filterEndDate").val();
		window.location.href = sourceleadUrl + "/exportAllMyExpensesList?&type="+type+"&expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate;
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='expensesListDiv' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Expenses List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' class='text-center width100percnt' value='Ok' onclick='expensesCancel();'></b>";
		result = result +"	</div>";
		$("#emptyPopup").append(result);
		$("#expensesListDiv").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
</script>



<script type="text/javascript">
function expensesCancel(){
	$("#emptyPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<script>
  $("#selector").fadeIn(200);
  $('#selector').delay(2000).fadeOut('slow');
	   $("#export-pop").click(function(){
	               $("#exportPop").toggle();
	       });
	   $("html").click(function() {
	       $("#exportPop").hide();
	   });
	   $("#exportPop, #export-pop").click(function(e) {
	       e.stopPropagation();
	   });        
</script> 
