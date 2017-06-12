<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 <script type="text/javascript">
		window.onload = function () {
			getExpensesList();
		 };
</script>
	
<!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>My Expenses</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; Expenses</div>
        </section>
        
		<!-- Timesheet -->

<div class="timesheet_out">

<div class="project_wrap">
	<div class="timesheet_head">
        	<ul>
				<li>Pradeep</li>
<!--                 <li id="filterToApprovePop"><i class="fa fa-filter"></i><span id="expensesListFilterDiv">Status</span><i class="glyphicon glyphicon-play"></i>| -->
<!--                 	<div id="filterToApprovedPop" class="approved_pop"> -->
						<li><i class="fa fa-filter"></i><span>Fliter</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input id="expenseAll" type="checkbox" name="expenseStatus" value="3" checked onclick="getExpensesList();"><label for="c5">All</label></li>
							<li><input id="expenseApproved" type="checkbox" name="expenseStatus" value ="2" onclick="getExpensesList();"><label for="c6">Approved</label></li>
							<li><input id="expenseDraft" type="checkbox" name="expenseStatus" value ="1" onclick="getExpensesList();"><label for="c7">Draft</label></li>
							<li><input id="expenseRejected" type="checkbox" name="expenseStatus" value ="0" onclick="getExpensesList();"><label for="c8">Rejected</label></li>
                        </ul>
                    </div>                	
                </li>
				<li><a href="<c:url value='/addExpense'/>"><i class="fa fa-plus"></i>Add Expenses</a></li>		
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
            
        </div>
        
        
        
    <div class="project_wrap_table">
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
	
	//For filter
	var expenseFilterCheckbox = $('input[name=expenseStatus]');
	  var expenseStatus = expenseFilterCheckbox.filter(':checked').val();
	  
	  if(expenseStatus == 3){
        $("#expenseListFilterDiv").text("All Expenses");
    }else if(expenseStatus == 2){
  	  $("#expenseListFilterDiv").text("Approved Expenses");
    }else if(expenseStatus == 1){
  	  $("#expenseListFilterDiv").text("Draft Expenses");
    }else if(expenseStatus == 0){
  	  $("#expenseListFilterDiv").text("Rejected Expenses");
    }else{
        $("#expenseListFilterDiv").removeAttr("onClick");
    }
	
	 $.ajax({
         type : "POST",
          url: sourceleadUrl+"/getExpensesList?${_csrf.parameterName}=${_csrf.token}&expenseStatus="+expenseStatus,       
          success :function(data) {
        	  var result = "";
					result = result +" <table id='expensesPagination'>                            ";
					result = result +"	<thead>                   ";
					result = result +"   	<tr>                          ";
					result = result +"       	<th>Exp Id</th>           ";
					result = result +"           <th>Purpose</th>         ";
					result = result +"           <th>Project</th>         ";
					result = result +"           <th>Client</th>          ";
					result = result +"           <th>Start Date</th>      ";
					result = result +"           <th>End Date</th>        ";
					result = result +"           <th>No of Exp</th>       ";
					result = result +"           <th>Amount</th>          ";
					result = result +"           <th>Action</th>          ";
					result = result +"       </tr>                        ";
					result = result +"	</thead>	                      ";
					result = result +"       <tbody>                      ";
        	  
	        	  var json = JSON.parse(data);
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
	        		  if(report.projectMemberId != null && report.projectMemberId != '' && report.projectMemberId != 'undefined'){
	        			  projectMemberId = report.projectMemberId;
	        		  }else{
	        			  projectMemberId = "--";
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
						 result = result +"        <tr>";
						 result = result +"        	<td><a href='"+sourceleadUrl+"/addExpense?expenseId="+expenseId+"&type=view'><div class='proj_show'><span>"+expenseId+"</span><i class='fa fa-binoculars'></i></div></a></td>             ";
						 result = result +"            <td><div>"+purpose+"</div></td>                                                                    ";
						 result = result +"            <td><div>"+projectMemberId+"</div></td>                                                                       ";
						 result = result +"            <td><div>"+clientName+"</div></td>                                                                        ";
						 result = result +"            <td><div>"+startDateStr+"</div></td>                                                                      ";
						 result = result +"            <td><div>"+endDateStr+"</div></td>                                                                      ";
						 result = result +"            <td><div>"+noOfExpenses+"</div></td>                                                                              ";
						 result = result +"            <td><div>"+totalAmount+"</div></td>                                                                            ";
						 result = result +"            <td>                                                                                                ";
						 result = result +"            	<div class='tbldata-dotdot_50'>                                                                    ";
						 result = result +"           		 <a href='"+sourceleadUrl+"/addExpense?expenseId="+expenseId+"&type=edit'><i class='fa fa-pencil-square-o' title='Edit'></i></a>";
						 result = result +"            		 <i class='glyphicon glyphicon-trash'></i>                                                     ";
						 result = result +"				</div>                                                                                             ";
						 result = result +"            </td>                                                                                               ";
						 result = result +"        </tr>               			                                     "; 
	                 
	        	  }
   			
        	  result = result +" </tbody>";
        	  result = result +" </table>";
        	  $("#expensesListDiv").val('');
	          $("#expensesListDiv").append(result);
	          expensesPagination();
          },
          
     }); 
	
	}

</script>      

<script>
function expensesPagination(){ 
// 	$.fn.dataTable.moment( 'DD-MM-YYYY' );
    $('#expensesPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
		"aaSorting": [[0,'desc']],
    	"bLengthChange": false,
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
	        	result = result +'<th>Exp Category</th>';
	        	result = result +'<th>Date</th>';
	        	result = result +'<th>Payment Mode</th>';
	        	result = result +'<th>Currency</th>';
	        	result = result +'<th>Description</th>';
	        	result = result +'<th>Amount</th>';
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
	    	   var raja = json[i];
	    	   
	        	/* if(reportExpenseEntries[i].expensesCategoryId != null && reportExpenseEntries[i].expensesCategoryId != '' && reportExpenseEntries[i].expensesCategoryId != 'undefined'){
	        		expensesCategoryId = reportExpenseEntries[i].expensesCategoryId;
      		  }else{
      			expensesCategoryId = "--";
      		  } */
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
	        	result = result +'<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>'+expensesCategoryId+'</div></td>';
	        	result = result +'<td><div>'+expenseDateStr+'</div></td>';
	        	result = result +'<td><div>'+paymentMode+'</div></td>';
	        	result = result +'<td><div>'+currencyType+'</div></td>';
	        	result = result +'<td><div class="tbldata-dotdot-me">'+description+'</div></td>';
	        	result = result +'<td><div>'+amount+'</div></td>';
	        	result = result +'</tr>';
	       }
	        	result = result +'</tbody>';
	        	result = result +'</table>';
	        	result = result +'</div>';
	        	result = result +'</div>';
	        	result = result +'</div>';
	        	
// 	        	result = result +'				        <div id="tab-2" class="project_tabs_inn">		                                                                                                                                                           ';
// 	        	result = result +'							<div class="hisWrap">                                                                                                                                                                                  ';
// 	        	result = result +'							  <div class="hisWrapCta">                                                                                                                                                                             ';
// 	        	result = result +'									<img src="images/23.jpg" alt="">                                                                                                                                                               ';
// 	        	result = result +'									<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>                 ';                       
// 	        	result = result +'									<span><i class="fa fa-clock-o"></i>2 min ago</span>                                                                                                                                            ';
// 	        	result = result +'							  </div>                                                                                                                                                                                               ';
// 	        	result = result +'							</div>                                                                                                                                                                                                 ';
// 	        	result = result +'				        </div>                                                                                                                                                                                                     ';
	        	
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
function activeTab(){
	$(".project_side_wrap").removeClass("project_side_wrap_active");
    $(".side_mask").fadeOut(200);
    $("body").css('overflow-y', 'scroll');
    $("#expensesViewDiv").empty();
}
</script>

<script>
function getEditExpense(eId){
	alert("fgdfg");
	window.location.replace(sourceleadUrl+'/addExpense?expenseId='+eId);
	alert("fgdfg");
}
</script>
