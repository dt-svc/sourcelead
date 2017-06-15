<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<script> 
$(document).ready(function(){
	
	expensesCount("today");
	
});
</script>
<div class="col-md-6">
        <div class="dashboard-widget dashboard-widget-icons">
          <h4 class="clearfix">Expenses &nbsp;&nbsp;</h4>
          <div class="clearfix dashboard-data mt">
            <ul class="nav nav-tabs">
              <li class="active"><a data-toggle="tab" onclick="expensesCount('today');" href="#today-myExpense">My Expenses</a></li>
              <li><a data-toggle="tab" onclick="expensesCount('previous');" href="#prev-employeeExpense">Employee Expenses</a></li>
            </ul>
            <div class="tab-content clearfix">
              <div id="today-req" class="tab-pane fade in active">
                <div class="db-widget-sm row">
                <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-green"> <i class="fa fa-black-tie" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="applicantsCount">123</span></p>
                        <p class="info-box-text"> Draft  </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-red"> <i class="fa fa-check-square" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="submissionsCount">12</span></p>
                        <p class="info-box-text"> Submitted</p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-blue"> <i class="fa fa-graduation-cap" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="interviewsCount">9</span></p>
                        <p class="info-box-text">Approved </p>
                      </div>
                    </div>
                  </div>
                  <div class="col-xs-12 col-sm-3">
                    <div class="info-box"> <span class="info-box-icon bg-orange"> <i class="fa fa-tags" aria-hidden="true"></i> </span>
                      <div class="info-box-content text-center">
                        <p class="info-box-number"><span id="offersCount">3</span></p>
                        <p class="info-box-text">Rejected </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              
            </div>
          </div>
        </div>
        <!-- Requirement ends --> 
      </div>
      
      
      
      <script type="text/javascript">


function expensesCount(daterange){
    var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getExpensesCount?${_csrf.parameterName}=${_csrf.token}&daterange="+daterange, 
		        success: function(data) {
		        	var json = JSON.parse(data);
		        	 
		        	 var  expensesDraftCount = "";
		        	 var  expensesSubmittedForApprovalCount ="";
		        	 var  expensesApprovedCount ="";
		        	 var  expensesRejectCount = ""; 
		        	 
		        	
		        	
		 		}
		  });
		 
	}

</script>