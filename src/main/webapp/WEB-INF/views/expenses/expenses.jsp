<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <jsp:include page="expensesCommonList.jsp"></jsp:include>

 <script type="text/javascript">
		window.onload = function () {
			if("" != localStorage.getItem("myExpensesListGrid") && null != localStorage.getItem("myExpensesListGrid")){
				$("#expensesListDiv").empty();
	       		$("#expensesListDiv").append(localStorage.getItem("myExpensesListGrid"));
	       		preparePaginationEmployee(1);
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
	       		$("#temp").val("temp");
	       		$("#appro-pop").hide();
			}else{
				getExpensesList();
				$("#appro-pop").show();
			}
		 };
</script>
	

<!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>My Expenses</h2>
          </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; My Expenses</div>
        </section>
        
        
		<!-- Timesheet -->
<div id="emptyPopup"></div>

<div class="timesheet_out">
	
<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon">
      			<sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_MY_EXPENSES')">
				    <div class="input-group pull-left">  		
					     <span class="dashboard-datepkr pull-left mr5">
						 <input type="text" class="form-control input-sm calendar noMr heiNew" value="" id="filterStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date">
						 <p id="reportStartDate" class="noPad"></p>
						 </span>
						 <span class="dashboard-datepkr pull-left mr5">
						 <input type="text"  class="calendar form-control input-sm noMr heiNew" value="" id="filterEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date">
						 <p id="reportEndDate" class="noPad"></p>
						 </span>
					 	<span class="pull-left"><input type="button" value="Submit" class="newAdBtn" onclick="getExpensesList('dateSearch');" /></span>
					</div>
				</sec:authorize>        
      </span>
      <ul class="pull-right list-unstyled list-inline">
               <sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES')">
			   		<li><a href="<c:url value='${appPath}/addExpense'/>" class="newAdBtn mr5">+ Expenses</a></li>
			   </sec:authorize>	      		
			
        		<sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_MY_EXPENSES')">
					<li><i class="filtIcon"  id="appro-pop"></i>
	                	<div class="approved_pop">
	                    	<ul>
	                        	<li><input id="expenseAll" type="checkbox" name="expenseStatus" value="ALL" checked onclick="getExpensesList();"><label >All</label></li>
								<li><input id="expenseApproved" type="checkbox" name="expenseStatus" value ="APPROVED" onclick="getExpensesList();"><label >Approved</label></li>
								<li><input id="expenseSubmitted" type="checkbox" name="expenseStatus" value ="SUBMITTED" onclick="getExpensesList();"><label >Submitted</label></li>
								<li><input id="expenseDraft" type="checkbox" name="expenseStatus" value ="DRAFT" onclick="getExpensesList();"><label >Draft</label></li>
								<li><input id="expenseRejected" type="checkbox" name="expenseStatus" value ="REJECTED" onclick="getExpensesList();"><label >Rejected</label></li>
								<li><input id="expenseReopened" type="checkbox" name="expenseStatus" value ="Reopened" onclick="getExpensesList();"><label >Reopened</label></li>
	                        </ul>
	                    </div>                	
	                </li>
               </sec:authorize>			
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
						 	<a href="javascript:void(0)" onclick="exportToPdf('pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportToPdf('excel');" title="Excel">Export Excel</a>
						</span>
					</div>
				</li>
		    
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='SearchExpensesData'/><span class="repSearchIcon" onClick="searchExpensesDataIcon();"></span></li>
		  <input type="hidden" id="temp"/>
        </ul>
      </div>
		
    <div class="project_wrap_table datatable_filter overflow_Auto">
    	<div id="expensesListDiv"><jsp:include page="expensesEmployeeRedefined.jsp"></jsp:include></div>
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
function getExpensesList(dateSearch){
	var startDate = '';
	var endDate = '';
	 if(dateSearch != null && dateSearch != '' && dateSearch != 'undefined' && dateSearch == 'dateSearch' ){ 
		var startDateFilter = $("#filterStartDate").val();
		var endDateFilter = $("#filterEndDate").val();
		if(startDateFilter != null && startDateFilter != '' && startDateFilter !='undefined' &&  endDateFilter != null && endDateFilter != '' && endDateFilter !='undefined' ){
			 startDate = startDateFilter;
			 endDate = endDateFilter;
			$('#reportStartDate').parent().find('p.jquery_form_error_message').remove();
			$('#reportEndDate').parent().find('p.jquery_form_error_message').remove();
		}else if(startDateFilter != null && startDateFilter != '' && startDateFilter !='undefined' &&  (endDateFilter == null || endDateFilter == '' || endDateFilter =='undefined')){
			$('#reportStartDate').parent().find('p.jquery_form_error_message').remove(); 
			$('#reportEndDate').parent().find('p.jquery_form_error_message').remove(); 
			$("#reportEndDate").after("<p class='jquery_form_error_message'><span>End Date Is Required</span>");
		}else if((startDateFilter == null || startDateFilter == ''|| startDateFilter !='undefined') &&  endDateFilter != null && endDateFilter != '' && endDateFilter !='undefined'){
			$('#reportStartDate').parent().find('p.jquery_form_error_message').remove(); 
			$('#reportEndDate').parent().find('p.jquery_form_error_message').remove(); 
			$("#reportStartDate").after("<p class='jquery_form_error_message'><span>Start Date Is Required</span>");
		}else if((endDateFilter == null || endDateFilter == '' || endDateFilter =='undefined') && (startDateFilter == null || startDateFilter == ''|| startDateFilter !='undefined')){
			$('#reportStartDate').parent().find('p.jquery_form_error_message').remove(); 
			$('#reportEndDate').parent().find('p.jquery_form_error_message').remove(); 
			$("#reportEndDate").after("<p class='jquery_form_error_message'><span>End Date Is Required</span>");
			$("#reportStartDate").after("<p class='jquery_form_error_message'><span>Start Date Is Required</span>");
		}		
	 } 

	var url = '';
    var searchValue = $("#SearchExpensesData").val();
	searchValue = searchValue ? searchValue : "";
	
	var expenseStatus = '';
	expenseStatus = $('#expenseStatusDivId').val();
	var expensesStatusFromDashboard = '${expensesStatusFormDashBoard}';
	if(expensesStatusFromDashboard != null && expensesStatusFromDashboard != '' && expensesStatusFromDashboard !='undefined'){
		$(":checkbox[value=ALL]").prop("checked",false);
		$(":checkbox[value="+expensesStatusFromDashboard+"]").prop("checked",true);
	}
	
	if(expenseStatus != null && expenseStatus != '' && expenseStatus !='undefined'){
		  	expenseStatus = expenseStatus.toUpperCase();
		  	$(":checkbox[value="+expenseStatus+"]").prop("checked","true");
	}else{
		var expenseStatus = [];
		$('input:checkbox[name=expenseStatus]:checked').each(function() 
		{
			expenseStatus.push($(this).val());
		});
	}
	
	var userId = '${SourceLeadWeb_user.id}';
	var locationCode = '${SourceLeadWeb_orgLocations.locationCode}'
	$.ajax({
	    type : "POST",
	     url: sourceleadUrl+"/getExpensesList?&expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate+"&searchValue="+searchValue+"&userId="+userId+"&locationCode="+locationCode,      
	     success :function(data) {
	    	 $("#expensesListDiv").empty();
			 $("#expensesListDiv").append(data);
			 $("#loadingId").removeAttr('style');
			 $("#temp").val('temp');
			 preparePaginationEmployee(1);
	  		}
		});
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
			showOn: 'focus',
            showButtonPanel: true,
            closeText: 'Clear',
            onClose : function(selected) {
				$("#reportEndDate").datepicker("option", "minDate", selected);
				var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
			}
		
		});
		
		$('#filterEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatExpenses,
			showOn: 'focus',
            showButtonPanel: true,
            closeText: 'Clear',
            onClose : function(selected) {
				$("#reportStartDate").datepicker("option", "maxDate", selected);
				var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
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
		
		 var expenseStatus = $('#expenseStatusDivId').val();
		 if(expenseStatus != null && expenseStatus != '' && expenseStatus !='undefined'){
				expenseStatus = expenseStatus
			}else{
				var expenseStatus = [];
				$('input:checkbox[name=expenseStatus]:checked').each(function() 
				{
					expenseStatus.push($(this).val());
				});	
			}
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
			$("#exportPop").toggle(1);
	    });
	   $("html").click(function() {
	       $("#exportPop").hide();
	   });
	   $("#exportPop, #export-pop").click(function(e) {
	       e.stopPropagation();
	   });        
</script> 

<script>
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
	$(".approved_pop").hide();	
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	


$("#appro-pop").click(function(){
	$(".newActPop").hide();
	$(".approved_pop").toggle();
});
$("html").click(function(){
	$(".approved_pop").hide();
}); 

$("#appro-pop").click(function(e){
	e.stopPropagation();
});	


$(document).ready(function(){
	$("#SearchExpensesData").keyup(function(event) {
		$('#SearchExpensesData').parent().find('p.jquery_form_error_message').remove();
	    if (event.keyCode === 13) {
	    	$('#SearchExpensesData').parent().find('p.jquery_form_error_message').remove();
	    	var searchData = $('#SearchExpensesData').val();
	    	var regex =  new RegExp(/[\\]/g);
	    	if(regex.test(searchData) == false){
	    		getExpensesList();
	    	}else{
	    		$("#SearchExpensesData").after("<p class='jquery_form_error_message'><span>Enter valid Search Data</span>");
	    	}
	    	$('#SearchExpensesData').val(searchData);
	    }
	});
});

</script>


<jsp:include page="../masterFooter.jsp" />
<%-- <jsp:include page="expensesEmployeeTable.jsp" /> --%>