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
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Expenses</div>
        </section>
        
		<!-- Timesheet -->

<div class="timesheet_out">

<div class="project_wrap">
	<div class="timesheet_head">
        	<ul>
        		<sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_MY_EXPENSES')">
					<li><i class="fa fa-filter"></i><span>Fliter</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
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
	var expenseStatus = [];
	$('input:checkbox[name=expenseStatus]:checked').each(function() 
    {
		expenseStatus.push($(this).val());
    });
	
	 $.ajax({
         type : "POST",
          url: sourceleadUrl+"/getExpensesList?${_csrf.parameterName}=${_csrf.token}&expenseStatus="+expenseStatus,       
          success :function(data) {
        	  var result = "";
					result = result +" <table id='expensesPagination'>";
					result = result +"<thead>";
					result = result +"<tr>";
					result = result +"<th>Exp Id</th>";
					result = result +"<th>Purpose</th>";
					result = result +"<th>Project</th>";
					result = result +"<th>Client</th>";
					result = result +"<th>Start Date</th>";
					result = result +"<th>End Date</th>";
					result = result +"<th>No of Exp</th>";
					result = result +"<th>Amount</th>";
					result = result +"<th>Status</th>";
					result = result +"</tr>";
					result = result +"</thead>";
					result = result +"<tbody>";
					
					 if(data !=''){
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
								 result = result +"<tr>";
								 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">'
								 result = result +"<td><a href='"+sourceleadUrl+"/addExpense?expenseId="+expenseId+"&type=view'><div class='proj_show'><span>"+expenseId+"</span><i class='fa fa-binoculars'></i></div></a></td>";
								 '</sec:authorize>';
								 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_EDIT_MY_EXPENSES\')">'
								 result = result +"<td><div class='proj_show'><span>"+expenseId+"</span></div></td>";
								 '</sec:authorize>';
								 result = result +"<td><div>"+purpose+"</div></td>";
								 result = result +"<td><div>"+projectMemberId+"</div></td>";
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
// 	$.fn.dataTable.moment( 'DD-MM-YYYY' );
    $('#expensesPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
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
		window.location.href = sourceleadUrl + "/exportAllMyExpensesList?&type="+type+"&expenseStatus="+expenseStatus;
	}else{
		var result = "";
		alert(">>>>>>>>>>>>>")
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