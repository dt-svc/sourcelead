<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<script> 
$(document).ready(function(){
		expensesCount("myExpenses");
});
</script>
<div class="col-md-6">
        <div class="dashboard-widget dashboard-widget-icons">
          <h4 class="clearfix">Expenses &nbsp;&nbsp;</h4>
          <div class="clearfix dashboard-data mt">
            <ul class="nav nav-tabs">
              <li class="active"><a data-toggle="tab" onclick="expensesCount('myExpenses');" href="#today-myExpense">My Expenses</a></li>
              <li><a data-toggle="tab" onclick="expensesCount('employeeExpenses');" href="#prev-employeeExpense">Employee Expenses</a></li>
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
                  <div class="col-xs-12 col-sm-3">
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
      
<script type="text/javascript">
function expensesCount(expenseType){
    var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getExpensesCount?${_csrf.parameterName}=${_csrf.token}&expenseType="+expenseType, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	 
		        	 $("#expensesDraftCount").empty();
	        		 $("#expensesSubmittedForApprovalCount").empty();
	        		 $("#expensesApprovedCount").empty();
	        		 $("#expensesRejectCount").empty();
	        		 
	        		 $("#dial1txt").empty();
	        		 $("#dial2txt").empty();
	        		 $("#dial3txt").empty();
	        		 $("#dial4txt").empty();
	        		 
	        		 var  expensesDraftCount = json.expensesDraftCount;
		        	 var  expensesSubmittedForApprovalCount =json.expensesSubmittedForApprovalCount;
		        	 var  expensesApprovedCount =json.expensesApprovedCount;
		        	 var  expensesRejectCount = json.expensesRejectCount;
		        	 
		        	 var  empExpensesSubmittedForApprovalCount = json.empExpensesSubmittedForApprovalCount;
		        	 var  empExpensesApprovedCount =json.empExpensesApprovedCount;
		        	 var  empExpensesRejectedCount =json.empExpensesRejectedCount;
		        	 var  empExpensesReopenCount = json.empExpensesReopenCount;
		        	 
		        	 if(expensesDraftCount<1 || expenseType == "myExpenses"){
		        		  $("#dial1txt").text("Draft");
		        		  $("#expensesDraftCount").html(expensesDraftCount);
	                  }else{
	                	  $("#dial1txt").text("Sub for Appr");
	                	  $("#expensesDraftCount").html(empExpensesSubmittedForApprovalCount);
	                  }
		        	if(expensesSubmittedForApprovalCount<1 || expenseType == "myExpenses"){
		        		  $("#dial2txt").text("Submitted");
		        		  $("#expensesSubmittedForApprovalCount").html(expensesSubmittedForApprovalCount);
	                  }else{
	                	  $("#dial2txt").text("Approved");
	                	  $("#expensesSubmittedForApprovalCount").html(empExpensesApprovedCount);
	                  }
		        	if(expensesApprovedCount<1 || expenseType == "myExpenses"){
		        		  $("#dial3txt").text("Approved");
		        		  $("#expensesApprovedCount").html(expensesApprovedCount);
	                }else{
	                	  $("#dial3txt").text("Rejected");
	                	  $("#expensesApprovedCount").html(empExpensesRejectedCount);
	                  }
		        	if(expensesRejectCount<1 || expenseType == "myExpenses"){
		        		  $("#dial4txt").text("Rejected");
		        		  $("#expensesRejectCount").html(expensesRejectCount);
	                 }else{
	                	 $("#dial4txt").text("Reopen");
	                	 $("#expensesRejectCount").html(empExpensesReopenCount);
	                  }
		 		}
		  });
	}
</script>