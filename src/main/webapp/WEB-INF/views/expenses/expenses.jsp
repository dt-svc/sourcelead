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
                <li><i class="fa fa-filter"></i><span>Fliter</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input value="None" id="c5" type="checkbox"><label for="c5">All</label></li>
							<li><input id="c6" type="checkbox"><label for="c6">Approved</label></li>
							<li><input id="c7" type="checkbox"><label for="c7">Draft</label></li>
							<li><input id="c8" type="checkbox"><label for="c8">Rejected</label></li>
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
	 $.ajax({
         type : "POST",
          url: sourceleadUrl+"/getExpensesList?${_csrf.parameterName}=${_csrf.token}",       
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
						 result = result +"        	<td><div class='proj_show' onclick='expenseView("+report.id+");'><span>"+expenseId+"</span><i class='fa fa-binoculars'></i></div></td>             ";
						 result = result +"            <td><div>"+purpose+"</div></td>                                                                    ";
						 result = result +"            <td><div>"+projectMemberId+"</div></td>                                                                       ";
						 result = result +"            <td><div>"+clientName+"</div></td>                                                                        ";
						 result = result +"            <td><div>"+startDateStr+"</div></td>                                                                      ";
						 result = result +"            <td><div>"+endDateStr+"</div></td>                                                                      ";
						 result = result +"            <td><div>"+noOfExpenses+"</div></td>                                                                              ";
						 result = result +"            <td><div>"+totalAmount+"</div></td>                                                                            ";
						 result = result +"            <td>                                                                                                ";
						 result = result +"            	<div class='tbldata-dotdot_50'>                                                                    ";
						 result = result +"           		 	<i class='fa fa-pencil-square-o'></i>                                                      ";
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
	          	result = result +'					<div class="project_side_wrap_inn">                                                                                               ';                 
	        	result = result +'				    	<h1>Expense name here</h1>                                                                                                    ';
	        	result = result +'				                                                                                                                                      ';
	        	result = result +'				<div class="project_side_wrap_inn_tabs">                                                                                              ';
	        	result = result +'				    <ul class="project-tabs-menu">                                                                                                    ';
	        	result = result +'				        <li class="current"><a href="#tab-1">Details</a></li>                                                                         ';
	        	//result = result +'				        <li><a href="#tab-2">History</a></li>                                                                                         ';
	        	result = result +'				        <div class="clearfix"></div>                                                                                                  ';
	        	result = result +'				    </ul>                                                                                                                             ';

	        	result = result +'				    <div class="project_tabs">                                                                                                        ';
	        	result = result +'				        <div id="tab-1" class="project_tabs_inn">                                                                                     ';
	        	result = result +'				            <div class="project_tabs_inn_table">                                                                                      ';
	        	result = result +'								<div class="project_details project_detailsExp">                                                                      ';

	        	result = result +'									<div class="Expdit">                                                                                              ';
	        	result = result +'										<a href="#" class="btn btn-primary">Status</a>                                                                ';
	        	result = result +'										<a href="add-expense.html" class="btn btn-default ml10"><i class="fa fa-pencil-square-o"></i> &nbsp;Edit</a>  ';
	        	result = result +'										<!--<a href="add-expense.html"><i class="glyphicon glyphicon-pencil"></i>Edit</a>-->                          ';
	        	result = result +'									</div>                                                                                                            ';

	        	 var json = JSON.parse(data);
	        	 
	        		  var report = json[0];
	        		  var expenseId="";
	        		  if(report.expenseId != null && report.expenseId != '' && report.expenseId != 'undefined'){
	        			  expenseId = report.expenseId;
	        		  }else{
	        			  expenseId = "--";
	        		  }
	        		  var projectMemberId="";
	        		  if(report.projectMemberId != null && report.projectMemberId != '' && report.projectMemberId != 'undefined'){
	        			  projectMemberId = report.projectMemberId;
	        		  }else{
	        			  projectMemberId = "--";
	        		  }
	        		  var clientName="";
	        		  if(report.clientName != null && report.clientName != '' && report.clientName != 'undefined'){
	        			  clientName = report.clientName;
	        		  }else{
	        			  clientName = "--";
	        		  }
	        		  var startDateStr="";
	        		  if(report.startDateStr != null && report.startDateStr != '' && report.startDateStr != 'undefined'){
	        			  startDateStr = report.startDateStr;
	        		  }else{
	        			  startDateStr = "--";
	        		  }
	        		  var endDateStr="";
	        		  if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
	        			  endDateStr = report.endDateStr;
	        		  }else{
	        			  endDateStr = "--";
	        		  }
	        		  var noOfExpenses="";
	        		  if(report.noOfExpenses != null && report.noOfExpenses != '' && report.noOfExpenses != 'undefined'){
	        			  noOfExpenses = report.noOfExpenses;
	        		  }else{
	        			  noOfExpenses = "--";
	        		  }
	        		  var totalAmount="";
	        		  if(report.totalAmount != null && report.totalAmount != '' && report.totalAmount != 'undefined'){
	        			  totalAmount = report.totalAmount;
	        		  }else{
	        			  totalAmount = "--";
	        		  }
	        	result = result +'				                    <div class="clearfix"></div>                                                                                      ';
	        	result = result +'									<div class="project_details_cta">                                                                                 ';
	        	result = result +'										<strong>Expenses Id:</strong><span>'+expenseId+'</span><div class="clearfix"></div>                                 ';
	        	result = result +'									</div>                                                                                                            ';
	        	result = result +'									<div class="project_details_cta">                                                                                 ';
	        	result = result +'										<strong>Project Name:</strong><span>'+projectMemberId+'</span><div class="clearfix"></div>                              ';
	        	result = result +'									</div>                                                                                                            ';
	        	result = result +'									<div class="project_details_cta">                                                                                 ';
	        	result = result +'										<strong>Client:</strong><span>'+clientName+'</span><div class="clearfix"></div>                                     ';
	        	result = result +'									</div>                                                                                                            ';
	        	result = result +'									<div class="project_details_cta">                                                                                 ';
	        	result = result +'										<strong>Start date:</strong><span>'+startDateStr+'</span><div class="clearfix"></div>                               ';
	        	result = result +'									</div>                                                                                                            ';
	        	result = result +'									<div class="project_details_cta">                                                                                 ';
	        	result = result +'										<strong>End date:</strong><span>'+endDateStr+'</span><div class="clearfix"></div>                                 ';
	        	result = result +'									</div>                                                                                                            ';
	        	result = result +'									<div class="project_details_cta">                                                                                 ';
	        	result = result +'										<strong>No of Expenses:</strong><span>'+noOfExpenses+'</span><div class="clearfix"></div>                                   ';
	        	result = result +'									</div>                                                                                                            ';
	        	result = result +'									<div class="project_details_cta">                                                                                 ';
	        	result = result +'										<strong>Amount:</strong><span>'+totalAmount+'</span><div class="clearfix"></div>                                       ';
	        	result = result +'									</div>	                    			                                                                          ';
	        	result = result +'									<div class="clearfix"></div>                                                                                      ';
	        	result = result +'								</div>                                                                                                                ';
	        	
	        	result = result +'				<div class="project_wrap_table expensTableView">                                                                                      ';
	        	result = result +'					<table>                                                                                                                           ';
	        	result = result +'					<thead>                                                                                                                           ';
	        	result = result +'							<tr>                                                                                                                      ';
	        	result = result +'								<th>Exp Category</th>                                                                                                 ';
	        	result = result +'								<th>Date</th>                                                                                                         ';
	        	result = result +'								<th>Payment Mode</th>                                                                                                 ';
	        	result = result +'								<th>Currency</th>                                                                                                     ';
	        	result = result +'								<th>Description</th>                                                                                                  ';
	        	result = result +'								<th>Amount</th>                                                                                                       ';
	        	result = result +'							</tr>                                                                                                                     ';
	        	result = result +'						</thead>	                                                                                                                  ';
	        	result = result +'							<tbody>                                                                                                                   ';

	        	alert("report.expenseEntriesList.length"+report.expenseEntriesList.length); 
	        	var expensesCategoryId= "";
	        	var paymentMode= "";
	        	var expenseDateStr= "";
	        	var currencyType= "";
	        	var description= "";
	        	var amount= "";
	       for (var i = 0; i < report.expenseEntriesList.length; i++) {	
	    	   alert("report.expenseEntriesList.length"+report.expenseEntriesList.length);
	    	   alert("report.expenseEntriesList[i]"+report.expenseEntriesList[i].expenseDateStr);
	    	   alert("report.expenseEntriesList[i]"+report.expenseEntriesList[i++].expenseDateStr);
	        	if(report.expenseEntriesList[i].expensesCategoryId != null && report.expenseEntriesList[i].expensesCategoryId != '' && report.expenseEntriesList[i].expensesCategoryId != 'undefined'){
	        		expensesCategoryId = report.expenseEntriesList[i].expensesCategoryId;
      		  }else{
      			expensesCategoryId = "--";
      		  }
	        	if(report.expenseEntriesList[i].paymentMode != null && report.expenseEntriesList[i].paymentMode != '' && report.expenseEntriesList[i].paymentMode != 'undefined'){
	        		paymentMode = report.expenseEntriesList[i].paymentMode;
      		  }else{
      			paymentMode = "--";
      		  }
	        	if(report.expenseEntriesList[i].expenseDateStr != null && report.expenseEntriesList[i].expenseDateStr != '' && report.expenseEntriesList[i].expenseDateStr != 'undefined'){
	        		expenseDateStr = report.expenseEntriesList[i].expenseDateStr;
      		  }else{
      			expenseDateStr = "--";
      		  }
	        	if(report.expenseEntriesList[i].currencyType != null && report.expenseEntriesList[i].currencyType != '' && report.expenseEntriesList[i].currencyType != 'undefined'){
	        		currencyType = report.expenseEntriesList[i].currencyType;
      		  }else{
      			currencyType = "--";
      		  }
	        	if(report.expenseEntriesList[i].description != null && report.expenseEntriesList[i].description != '' && report.expenseEntriesList[i].description != 'undefined'){
	        		description = report.expenseEntriesList[i].description;
      		  }else{
      			description = "--";
      		  }
	        	if(report.expenseEntriesList[i].amount != null && report.expenseEntriesList[i].amount != '' && report.expenseEntriesList[i].amount != 'undefined'){
	        		amount = report.expenseEntriesList[i].amount;
      		  }else{
      			amount = "--";
      		  }
	        	
	        	result = result +'							<tr>                                                                                                                      ';
	        	result = result +'								<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>'+expensesCategoryId+'</div></td>                  ';
	        	result = result +'								<td><div>'+expenseDateStr+'</div></td>                                                                                        ';
	        	result = result +'								<td><div>'+paymentMode+'</div></td>                                                                                              ';
	        	result = result +'								<td><div>'+currencyType+'</div></td>                                                                                               ';
	        	result = result +'								<td><div class="tbldata-dotdot-me">'+description+'</div></td>                                                 ';
	        	result = result +'								<td><div>'+amount+'</div></td>				                                                                              ';
	        	result = result +'							</tr>                                                                                                                     ';
	       }
	        	result = result +'						</tbody>                                                                                                                      ';
	        	result = result +'					</table>                                                                                                                          ';
	        	result = result +'				</div>                                                                                                                                ';
	        	result = result +'				            </div>                                                                                                                                                                                              ';
	        	result = result +'				        </div>                                                                                                                                                                                                     ';
	        	
	        	
// 	        	result = result +'				        <div id="tab-2" class="project_tabs_inn">		                                                                                                                                                           ';
// 	        	result = result +'							<div class="hisWrap">                                                                                                                                                                                  ';
// 	        	result = result +'							  <div class="hisWrapCta">                                                                                                                                                                             ';
// 	        	result = result +'									<img src="images/23.jpg" alt="">                                                                                                                                                               ';
// 	        	result = result +'									<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>                 ';                       
// 	        	result = result +'									<span><i class="fa fa-clock-o"></i>2 min ago</span>                                                                                                                                            ';
// 	        	result = result +'							  </div>                                                                                                                                                                                               ';
// 	        	result = result +'							</div>                                                                                                                                                                                                 ';
// 	        	result = result +'				        </div>                                                                                                                                                                                                     ';
	        	
	        	result = result +'				    </div>                                                                                                                                                                                                         ';
	        	result = result +'				</div>                                                                                                                                                                                                             ';

	        	result = result +'					<div class="project_inn_mask"></div>                                                                                                                                                                           ';
	        	result = result +'					<div class="project_error">                                                                                                                                                                                    ';
	        	result = result +'						<p>Delete conformation message....</p>                                                                                                                                                                     ';
	        	result = result +'						<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a>                                                                                                                ';
	        	result = result +'					</div>	                                                                                                                                                                                                       ';
	        	result = result +'				    </div>                                                                                                                                                                                                         ';
 
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
}
</script>
