<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <jsp:include page="expensesCommonList.jsp"></jsp:include>

 <script type="text/javascript">
		window.onload = function () {
			if("" != localStorage.getItem("myExpensesListGrid") && null != localStorage.getItem("myExpensesListGrid")){
				$("#expensesListDiv").empty();
	       		$("#expensesListDiv").append(localStorage.getItem("myExpensesListGrid"));
	       		expensesPagination();
	       		/* localStorage.removeItem("myExpensesListGrid"); */
	       		localStorage.setItem("myExpensesListGrid","");
	       		$(":checkbox[value=ALL]").prop("checked",false);
	       		if("draft" == $("#countType").val()){
	       			$(":checkbox[value=DRAFT]").prop("checked",true);
	       		}else if("submitted" == $("#countType").val()){
	       			$(":checkbox[value=SUBMITTED]").prop("checked",true);
	       		}else if("approved" == $("#countType").val()){
	       			$(":checkbox[value=APPROVED]").prop("checked",true);
	       		}else if("rejected" == $("#countType").val()){
	       			$(":checkbox[value=REJECTED]").prop("checked",true);
	       		}
			}else{
				getExpensesList();
			}
		 };
</script>
	
<!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>My Expenses</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; My Expenses</div>
        </section>
        
		<!-- Timesheet -->

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
			   		<li><a href="<c:url value='/addExpense'/>"><i class="fa fa-plus"></i>Add Expenses</a></li>
			   </sec:authorize>	
			   <li title="Export"><a  id="export-pop"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>
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
	var sourceleadUrl = '${sourceleadUrl}';
	var startDate = $("#filterStartDate").val();
	var endDate = $("#filterEndDate").val();
	/* if(startDate == null || startDate == '' || startDate == 'undefined'){
		$("#reportStartDate").empty().append("Start Date is Required") ; 
	}
	if(endDate == null || endDate == '' || endDate == 'undefined'){
		$("#reportEndDate").empty().append("End Date is Required") ; 
	} */
	
	var expenseStatus = [];
	$('input:checkbox[name=expenseStatus]:checked').each(function() 
    {
		expenseStatus.push($(this).val());
    });
	var obj = {}
	obj['expenseStatus']=expenseStatus;
	obj['startDate']=startDate;
	obj['endDate']=endDate;
	obj['userId']='${SourceLeadWeb_user.id}';
	obj['locationCode']='${SourceLeadWeb_orgLocations.locationCode}';
	var jsonData= JSON.stringify(obj);
	
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  expensesReqListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  expensesReqListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  expensesReqListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  expensesReqListDate = 'YYYY-DD-MM'; 
	  }
	
	 $.ajax({
		 type : "POST",
		  data: jsonData,
  	      contentType : 'application/json; charset=utf-8',
  	      dataType : 'json',
          url: sourceleadUrl+"/getExpensesList?${_csrf.parameterName}=${_csrf.token}",     
          success :function(json) {
        	  var result = "";
					result = result +" <table id='expensesPagination'>";
					result = result +"<thead>";
					result = result +"<tr>";
					result = result +"<th>Exp Id</th>";
					result = result +"<th>Purpose</th>";
					result = result +"<th>Project</th>";
					result = result +"<th>Client</th>";
					result = result +"<th>Start Date<br>(<small class='custom-small'>"+expensesReqListDate+"</small>)</th>";
					result = result +"<th>End Date<br>(<small class='custom-small'>"+expensesReqListDate+"</small>)</th>";
					result = result +"<th>No of Items</th>";
					result = result +"<th>Amount</th>";
					result = result +"<th>Status</th>";
					result = result +"</tr>";
					result = result +"</thead>";
					result = result +"<tbody>";
					
					 if(json !=''){
			        	  for (var i = 0; i < json.length; i++) { 
			        		  var report = json[i]; 
			        		  if(report.expenseId != null && report.expenseId != '' && report.expenseId != 'undefined'){
			        			  expenseId = report.expenseId;
			        		  }else{
			        			  expenseId = "--";
			        		  }
			        		  if(report.purpose != null && report.purpose != '' && report.purpose != 'undefined'){
			        			  purpose = report.purpose;
			        		  }else{
			        			  purpose = "--";
			        		  }
			        		  if(report.projectName != null && report.projectName != '' && report.projectName != 'undefined'){
			        			  projectName = report.projectName;
			        		  }else{
			        			  projectName = "--";
			        		  }
			        		  if(report.clientName != null && report.clientName != '' && report.clientName != 'undefined'){
			        			  clientName = report.clientName;
			        		  }else{
			        			  clientName = "--";
			        		  }
			        		  if(report.startDateStr != null && report.startDateStr != '' && report.startDateStr != 'undefined'){
			        			  startDateStr = report.startDateStr;
			        		  }else{
			        			  startDateStr = "--";
			        		  }
			        		  if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
			        			  endDateStr = report.endDateStr;
			        		  }else{
			        			  endDateStr = "--";
			        		  }
			        		  if(report.noOfExpenses != null && report.noOfExpenses != '' && report.noOfExpenses != 'undefined'){
			        			  noOfExpenses = report.noOfExpenses;
			        		  }else{
			        			  noOfExpenses = "--";
			        		  }
			        		  if(report.totalAmount != null && report.totalAmount != '' && report.totalAmount != 'undefined'){
			        			  totalAmount = report.totalAmount;
			        		  }else{
			        			  totalAmount = "--";
			        		  }
								 result = result +"<tr>";
								 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">'
								 result = result +"<td><a href='"+sourceleadUrl+"/addExpense?expenseId="+expenseId+"&type=view'><div class='proj_show'><span>"+expenseId+"</span><i class='fa fa-binoculars'></i></div></a></td>";
								 '</sec:authorize>';
								 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">'
								 result = result +"<td><div class='proj_show'><span>"+expenseId+"</span></div></td>";
								 '</sec:authorize>';
								 result = result +"<td><div title="+purpose+">"+purpose+"</div></td>";
								 result = result +"<td><div title="+projectName+">"+projectName+"</div></td>";
								 result = result +"<td><div>"+clientName+"</div></td>";
								 result = result +"<td><div>"+startDateStr+"</div></td>";
								 result = result +"<td><div>"+endDateStr+"</div></td>";
								 result = result +"<td><div>"+noOfExpenses+"</div></td>";
								 result = result +"<td><div>"+totalAmount+"</div></td>";
								 result = result +"<td> <div>"+report.expenseStatus+"</div></td>";
								 result = result +"</tr>"; 
			        	  }
		        		  $("#temp").val("temp");
		        	  }else{
		        		  /* $("#temp").val(''); */
		        	  }
	        	 
   			
        	  result = result +" </tbody>";
        	  result = result +" </table>";
        	  $("#expensesListDiv").empty();
	          $("#expensesListDiv").append(result);
	          expensesPagination();
          },
          
     }); 
	
	}

</script>      

<script>
function expensesPagination(){ 
    $('#expensesPagination').DataTable( { 
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
    $("#expensesPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
}
</script> 

<script>

function expenseView(expenseId){
var sourceleadUrl = '${sourceleadUrl}';
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
	        		  var totalAmount="";
	        		  if(reportExpense.expenseIndList[0].totalAmount != null && reportExpense.expenseIndList[0].totalAmount != '' && reportExpense.expenseIndList[0].totalAmount != 'undefined'){
	        			  totalAmount = reportExpense.expenseIndList[0].totalAmount;
	        		  }else{
	        			  totalAmount = "--";
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
	        	result = result +'<strong>Amount:</strong><span>'+totalAmount+'</span><div class="clearfix"></div>';
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
		var sourceleadUrl = '${sourceleadUrl}';
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
		result = result +"		<p>Candidate List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
		result = result +"	</div>";
		$("#emptyPopup").append(result);
		$("#expensesListDiv").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	
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
<jsp:include page="../masterFooter.jsp" />