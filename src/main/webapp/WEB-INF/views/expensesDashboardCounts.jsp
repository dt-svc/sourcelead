<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../views/expenses/expensesCommonList.jsp" />

<script> 
$(document).ready(function(){
		expensesCount("myExpenses");
});
</script>
<sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES', 'ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">
<div class="col-md-6 mb10">

        <div class="dashboardWidget-new dashNewWrap mb">
            
        	<div class="dashNewWrapHead clearfix">
				<a class="pull-left" id="expensesLink" href="<c:url value="/app/expenses"/>">Expenses &nbsp;&nbsp;</a>
	            <ul class="nav nav-tabs pull-right">
	              <li class="active"><a data-toggle="tab" onclick="expensesCount('myExpenses');" >Self</a></li>
	             <sec:authorize access="hasAnyRole('ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">
	              <li><a data-toggle="tab" onclick="expensesCount('employeeExpenses');" >Employees</a></li>
	             </sec:authorize>
	              
	            </ul>
        	</div> 
            
              <div id="today-req" class="tab-pane fade in active">
                <div class="db-widget-sm">
                
                <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount">
                    	 <p id="dial1txt">Draft</p>
                        <p class="NewCount"><span id="expensesDraftCount"></span></p>
                    </div>
                  </div>
                  
                  <div class="col-xs-12 col-sm-3" id="submittedId">
                    <div class="dashNewCount">
                    	<p id="dial2txt"> Submitted</p>
                        <p class="NewCount suColor"><span id="expensesSubmittedForApprovalCount"></span></p>
                    </div>
                  </div>
                  
                  <div class="col-xs-12 col-sm-3">
                    <div class="dashNewCount">
                    	<p id="dial3txt">Approved </p>
                        <p class="NewCount apColor"><span id="expensesApprovedCount"></span></p>
                    </div>
                  </div>
                  
                  <div class="col-xs-12 col-sm-3" id="dial4txtDial">
                    <div class="dashNewCount">
                    	<p id="dial4txt">Rejected </p>
                        <p class="NewCount reColor"><span id="expensesRejectCount"></span></p>
                    </div>
                  </div>
                  
                </div>
              </div>
              
            </div>
          </div>
    </sec:authorize>
<script type="text/javascript">
function expensesCount(expenseType){
    var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getExpensesCount?${_csrf.parameterName}=${_csrf.token}&expenseType="+expenseType, 
		        success: function(json) {
		        	//var json = JSON.parse(data);
		        	 
		        	 $("#expensesDraftCount").empty();
	        		 $("#expensesSubmittedForApprovalCount").empty();
	        		 $("#expensesApprovedCount").empty();
	        		 $("#expensesRejectCount").empty();
	        		 
	        		 $("#dial1txt").empty();
	        		 $("#dial2txt").empty();
	        		 $("#dial3txt").empty();
	        		 $("#dial4txt").empty();
	        		 
	        		 var  expensesDraftCount = 0;
	        		 if(json.expensesDraftCount != null && json.expensesDraftCount != "" && json.expensesDraftCount != 'undefined'){
	        			 expensesDraftCount = json.expensesDraftCount;
	        		 }
	        		 
		        	 var  expensesSubmittedForApprovalCount = 0;
		        	 if(json.expensesSubmittedForApprovalCount != null && json.expensesSubmittedForApprovalCount != "" && json.expensesSubmittedForApprovalCount != 'undefined'){
		        		 expensesSubmittedForApprovalCount =json.expensesSubmittedForApprovalCount;
	        		 }
		        	 
		        	 var  expensesApprovedCount = 0;
		        	 if(json.expensesApprovedCount != null && json.expensesApprovedCount != "" && json.expensesApprovedCount != 'undefined'){
		        		 expensesApprovedCount =json.expensesApprovedCount;
	        		 }
		        	 
		        	 var  expensesRejectCount = 0;
		        	 if(json.expensesRejectCount != null && json.expensesRejectCount != "" && json.expensesRejectCount != 'undefined'){
		        		 expensesRejectCount = json.expensesRejectCount;
	        		 }
		        	 
		        	 var  empExpensesSubmittedForApprovalCount = 0;
		        	 if(json.empExpensesSubmittedForApprovalCount != null && json.empExpensesSubmittedForApprovalCount != "" && json.empExpensesSubmittedForApprovalCount != 'undefined'){
		        		 empExpensesSubmittedForApprovalCount = json.empExpensesSubmittedForApprovalCount;
	        		 }
		        	 
		        	 var  empExpensesApprovedCount = 0;
		        	 if(json.empExpensesApprovedCount != null && json.empExpensesApprovedCount != "" && json.empExpensesApprovedCount != 'undefined'){
		        		 empExpensesApprovedCount =json.empExpensesApprovedCount;
	        		 }
		        	 
		        	 var  empExpensesRejectedCount = 0;
		        	 if(json.empExpensesRejectedCount != null && json.empExpensesRejectedCount != "" && json.empExpensesRejectedCount != 'undefined'){
		        		 empExpensesRejectedCount =json.empExpensesRejectedCount;
	        		 }
		        	 
		        	// var  empExpensesReopenCount = json.empExpensesReopenCount;
		        	 
		        	 if(expenseType == "myExpenses"){
		        		 if(expensesDraftCount >0){
		        			 $("#dial1txt").html("<a onclick='myExpensesListByStage(\"draft\");'>Draft</a>");
		        			 $("#expensesDraftCount").html("<a onclick='myExpensesListByStage(\"draft\");'>"+expensesDraftCount+"</a>");
		        		   }else{
		        			   $("#dial1txt").html("Draft");
		        			   $("#expensesDraftCount").html(expensesDraftCount);
		        		   }
		        		 
	                  }else{
	                	  if(empExpensesSubmittedForApprovalCount >0){
		                	  $("#dial1txt").html("<a onclick='employeeExpensesListByStage(\"submitted\");'>Waiting</a>");
		                	  $("#expensesDraftCount").html("<a onclick='employeeExpensesListByStage(\"submitted\");'>"+empExpensesSubmittedForApprovalCount+"</a>");
	                	  }else{
	                		  $("#dial1txt").html("Waiting");
		                	  $("#expensesDraftCount").html(empExpensesSubmittedForApprovalCount); 
	                	  }
	                	  
	                  }
		        	 
		        	if(expenseType == "myExpenses"){
		        		$("#submittedId").show(); 
		        		if(expensesSubmittedForApprovalCount > 0){
		        			$("#dial2txt").html("<a onclick='myExpensesListByStage(\"submitted\");'>Submitted</a>");
			        		  $("#expensesSubmittedForApprovalCount").html("<a onclick='myExpensesListByStage(\"submitted\");'>"+expensesSubmittedForApprovalCount+"</a>");
		        		}else{
		        			 $("#dial2txt").html("Submitted");
			        		 $("#expensesSubmittedForApprovalCount").html(expensesSubmittedForApprovalCount);
		        		}
		        		
	                  }else{
	                	  $("#submittedId").hide();
	                  }
		        	
		        	if(expenseType == "myExpenses"){
		        		  if(expensesApprovedCount > 0){
			        		  $("#dial3txt").html("<a onclick='myExpensesListByStage(\"approved\");'>Approved</a>");
			        		  $("#expensesApprovedCount").html("<a onclick='myExpensesListByStage(\"approved\");'>"+expensesApprovedCount+"</a>");
		        		  }else{
		        			  $("#dial3txt").html("Approved");
			        		  $("#expensesApprovedCount").html(expensesApprovedCount);
		        		  }
		        		  
	                }else{
	                	  if(empExpensesApprovedCount > 0){
		                	  $("#dial3txt").html("<a onclick='employeeExpensesListByStage(\"approved\");'>Approved</a>");
		                	  $("#expensesApprovedCount").html("<a onclick='employeeExpensesListByStage(\"approved\");'>"+empExpensesApprovedCount+"</a>");
	                	  }else{
	                		  $("#dial3txt").html("Approved");
		                	  $("#expensesApprovedCount").html(empExpensesApprovedCount);
	                	  }
	                  }
		        	
		        	if(expenseType == "myExpenses"){
		        		  $("#dial4txtDial").show();
		        		  if(expensesRejectCount > 0){
		        		          $("#dial4txt").html("<a onclick='myExpensesListByStage(\"rejected\");'>Rejected</a>");
		        		          $("#expensesRejectCount").html("<a onclick='myExpensesListByStage(\"rejected\");'>"+expensesRejectCount+"</a>");
		        		  }else{
		        			  $("#dial4txt").html("Rejected");
	        		          $("#expensesRejectCount").html(expensesRejectCount);
		        		  }
	                 }else{
		                	if(empExpensesRejectedCount > 0){
			                	   $("#dial4txt").html("<a onclick='employeeExpensesListByStage(\"rejected\");'>Rejected</a>");
			                	   $("#expensesRejectCount").html("<a onclick='employeeExpensesListByStage(\"rejected\");'>"+empExpensesRejectedCount+"</a>");
			                	}else{
			                		$("#dial4txt").html("Rejected");
				                	$("#expensesRejectCount").html(empExpensesRejectedCount);
			               }
			           }
		        	
		        	  
		 		}
		  });
		 
		 if(expenseType == "myExpenses"){
			 $("#expensesLink").attr("href", "<c:url value='/app/expenses'/>");
		 }else{
			 $("#expensesLink").attr("href", "<c:url value='/app/expensesApprovalList'/>");
		 }
	}
</script>

<!-- <script type="text/javascript">
	function forEachCountOfExpense(expenseType){
	location.replace(sourceleadUrl+ "/expenses");
	}
</script> -->