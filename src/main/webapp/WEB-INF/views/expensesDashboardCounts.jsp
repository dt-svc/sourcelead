<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="../views/expenses/expensesCommonList.jsp" />

<script> 
$(document).ready(function(){
		expensesCount("myExpenses");
});
</script>
<sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES', 'ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">
<div class="col-md-6">
        <div class="dashboard-widget dashboard-widget-icons">
          <h4 class="clearfix">Expenses &nbsp;&nbsp;</h4>
          <div class="clearfix dashboard-data mt">
            <ul class="nav nav-tabs">
            
            <%-- <sec:authorize access="hasAnyRole('ROLE_ADD_MY_EXPENSES','ROLE_VIEW_EDIT_MY_EXPENSES','ROLE_EXPORT_MY_EXPENSES')"> --%>
              <li class="active"><a data-toggle="tab" onclick="expensesCount('myExpenses');" href="#today-myExpense">Self</a></li>
            <%--   </sec:authorize>  --%>
             
             <sec:authorize access="hasAnyRole('ROLE_ADD_EMPLOYEE_EXPENSES','ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES','ROLE_EXPORT_EMPLOYEE_EXPENSES')">
              <li><a data-toggle="tab" onclick="expensesCount('employeeExpenses');" href="#prev-employeeExpense">Employees</a></li>
             </sec:authorize>
              
            </ul>
            <div class="tab-content clearfix">
              <div id="today-req" class="tab-pane fade in active">
                <div class="db-widget-sm row">
                <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-green"> <i class="fa fa-black-tie" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="expensesDraftCount"></span></p>
                        <p class="info-box-text"id="dial1txt">Draft </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-red"> <i class="fa fa-check-square" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="expensesSubmittedForApprovalCount"></span></p>
                        <p class="info-box-text"id="dial2txt"> Submitted</p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-blue"> <i class="fa fa-graduation-cap" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="expensesApprovedCount"></span></p>
                        <p class="info-box-text" id="dial3txt">Approved </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3" id="dial4txtDial">
                    <div class="info-box"> <span class="info-box-icon bg-orange"> <i class="fa fa-tags" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="expensesRejectCount"></span></p>
                        <p class="info-box-text" id="dial4txt">Rejected </p>
                      </div>
                    </div>
                  </div>
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
		        		if(expensesSubmittedForApprovalCount > 0){
			        		  $("#dial2txt").html("<a onclick='myExpensesListByStage(\"submitted\");'>Submitted</a>");
			        		  $("#expensesSubmittedForApprovalCount").html("<a onclick='myExpensesListByStage(\"submitted\");'>"+expensesSubmittedForApprovalCount+"</a>");
		        		}else{
		        			 $("#dial2txt").html("Submitted");
			        		 $("#expensesSubmittedForApprovalCount").html(expensesSubmittedForApprovalCount);
		        		}
		        		
	                  }else{
	                	  if(empExpensesApprovedCount > 0){
		                	  $("#dial2txt").html("<a onclick='employeeExpensesListByStage(\"approved\");'>Approved</a>");
		                	  $("#expensesSubmittedForApprovalCount").html("<a onclick='employeeExpensesListByStage(\"approved\");'>"+empExpensesApprovedCount+"</a>");
	                	  }else{
	                		  $("#dial2txt").html("Approved");
		                	  $("#expensesSubmittedForApprovalCount").html(empExpensesApprovedCount);
	                	  }
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
	                	if(empExpensesRejectedCount > 0){
	                	   $("#dial3txt").html("<a onclick='employeeExpensesListByStage(\"rejected\");'>Rejected</a>");
	                	   $("#expensesApprovedCount").html("<a onclick='employeeExpensesListByStage(\"rejected\");'>"+empExpensesRejectedCount+"</a>");
	                	}else{
	                		$("#dial3txt").html("Rejected");
		                	$("#expensesApprovedCount").html(empExpensesRejectedCount);
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
	                	 $("#dial4txtDial").hide();
	                	 /* $("#dial4txt").html("<a onclick='employeeExpensesListByStage(\"reopen\");'>Reopen</a>");
	                	 $("#expensesRejectCount").html(empExpensesReopenCount); */
	                  }
		        	
		        	  
		 		}
		  });
	}
</script>

<!-- <script type="text/javascript">
	function forEachCountOfExpense(expenseType){
	location.replace(sourceleadUrl+ "/expenses");
	}
</script> -->