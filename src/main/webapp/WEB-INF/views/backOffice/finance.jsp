<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript" src="jquery-1.2.6.min.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>


 <!-- <script type="text/javascript">
   window.onload = function () {
	   getAccountsList();
	/* getamexTransAmount();
	getComericaTransAmount();
	getChaseTransAmount(); */
 };
</script>  -->

<script>
$(document).ready(function(){
	getAccountsList();
});
</script> 

 

<section class="content-wraper">
  <div class="content-header clearfix">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Account List</h2>
      </div>
      
    </section>
  <div class="timesheet_out">
       <div class="vendor_wrap">
        <div class="table_head clearfix accountlist-head"> 
          <!-- From date to date picker -->
          <div class="col-md-12 clearfix">
            <ul>
              <li> <a href="javascript:void(0)" onClick="advancedsearch()"> <i class="glyphicon glyphicon-search"></i>&nbsp;&nbsp;Advanced Search </a> </li>
              <li> <a href="<c:url value='/transactionsImports'/>"> <i class="fa fa-upload"></i>&nbsp;&nbsp;Import</a> </li>
              <li> <a href="paymenttype.html"><i class="glyphicon glyphicon-plus"></i>&nbsp;&nbsp;Payment Type</a> </li>
            </ul>
          </div>
          <!-- Advanced Search -->
          <div class="col-md-12 clearfix inst-search advanced-search">
            <div class="pull-left">
              <form class="form-inline">
                <div class="form-group">
                  <input type="email" class="form-control input-sm calendar" id="dpfromdate" placeholder="From Date">
                </div>
                <div class="form-group">
                  <input type="text" class="form-control input-sm calendar" id="dptodate" placeholder="To Date">
                </div>
                <button type="submit" class="btn btn-sm btn-primary"><i class="fa fa-search"></i></button>
              </form>
            </div>
            <div class="col-md-4 ml10">
              <label class="col-md-3">Institute</label>
              <div class="col-md-9">
                <select class="form-control chosen-select">
                  <option selected>-Select Type-</option>
                  <option>American Exchange Credit Card</option>
                  <option>Comerica Bank</option>
                  <option>Chage Bank</option>
                </select>
              </div>
            </div>
            <div class="col-md-2 timesheet_head transaction-hd ml10"> <i class="fa fa-filter"></i><span>Payment</span> <i class="glyphicon glyphicon-play" id="transaction-pop"></i>
              <div class="approved_pop">
                <ul>
                  <li>
                    <label>
                      <input type="checkbox" name="payroll">
                      &nbsp;Payroll Expense</label>
                  </li>
                  <li>
                    <label>
                      <input type="checkbox" name="payroll">
                      &nbsp;Payroll Net Payment</label>
                  </li>
                  <li>
                    <label>
                      <input type="checkbox" name="payroll">
                      &nbsp;Payroll Tax Withholding</label>
                  </li>
                  <li>
                    <label>
                      <input type="checkbox" name="payroll">
                      &nbsp;Amex Payment</label>
                  </li>
                  <li>
                    <label>
                      <input type="checkbox" name="payroll">
                      &nbsp;Car Payment</label>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <!-- Advanced Search --> 
        </div>
        
        <!-- <div class="require_wrap_table table-wrap">
          <table>
            <thead>
              <tr>
                <th>Financial Institute</th>
                <th>Debit (USD)</th>
                <th>Credit (USD)</th>
                <th>Balance (USD)</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><a href="account-view.html" class="tbldata-dotdot-big">
                  <div class="proj_show"> <span class="tbldata-dotdot-me">American Exchange Credit Card</span> <i class="fa fa-binoculars"></i> </div>
                  </a></td>
                <td id="amexDebitAmount"><div class="tbldata-dotdot-vsm" id="amexDebitAmountValue"></div></td>
                <td id="amexCreditAmount"><div class="tbldata-dotdot-vsm" id="amexCreditAmountValue"></div></td>
                <td id="amexBalanceAmount"><div class="tblminwidth-150" id="amexBalanceAmountValue"></div></td>
              </tr>
              <tr>
                <td><a href="account-view.html" class="tbldata-dotdot-big">
                  <div class="proj_show"> <span class="tbldata-dotdot-me">Comerica Bank</span> <i class="fa fa-binoculars"></i> </div>
                  </a></td>
                <td id="comericaDebitAmount"><div class="tbldata-dotdot-vsm" id="comericaDebitAmountValue"></div></td>
                <td id="comericaCreditAmount"><div class="tbldata-dotdot-vsm" id="comericaCreditAmountValue"></div></td>
                <td id="comericaBalanceAmount"><div class="tblminwidth-150" id="comericaBalanceAmountValue"></div></td>
              </tr>
              <tr>
                <td><a href="account-view.html" class="tbldata-dotdot-big">
                  <div class="proj_show"> <span class="tbldata-dotdot-me">Chase Bank</span> <i class="fa fa-binoculars"></i> </div>
                  </a></td>
                <td id="chaseDebitAmount"><div class="tbldata-dotdot-vsm" id="chaseDebitAmountValue"></div></td>
                <td id="chaseCreditAmount"><div class="tbldata-dotdot-vsm" id="chaseCreditAmountValue"></div></td>
                <td id="chaseBalanceAmount"><div class="tblminwidth-150" id="chaseBalanceAmountValue"></div></td>
              </tr>
            <tfoot>
              <tr>
                <td colspan="1">Total</td>
                <td id="debitAmountTotal"></td>
                <td id="creditAmountTotal"></td>
                <td id="balanceAmountTotal"></td>
              </tr>
            </tfoot>
              </tbody>
            
          </table>
        </div> -->
      </div> 
        
         <div class="require_wrap_table table-wrap">
        	<table id="financialInstituesList" class="display" cellspacing="0" width="100%"> </table>
        </div> 
      </div>
  </section>
  
  
  <jsp:include page="../masterFooter.jsp" />
  
  <script>
		function advancedsearch(){
			$('.advanced-search').toggle();
		}
  </script>
  
  
  <script>
  function getamexTransAmount(){
	  $("#amexDebitAmount").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  $("#amexCreditAmount").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  $("#amexBalanceAmount").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getAmexTransactions?${_csrf.parameterName}=${_csrf.token}", 	   
 		    success: function(data) {
 		    	var json = JSON.parse(data);
 		    	 $("#amexDebitAmountValue").append(json.debitAmount)
 		    	 $("#amexCreditAmountValue").append(json.creditAmount)
 		    	 $("#amexBalanceAmountValue").append(json.balanceAmount)
 		    	
 		    }
	  });
		    
	  
	  $("#amexDebitAmount").removeAttr("style");
	  $("#amexCreditAmount").removeAttr("style");
	  $("#amexBalanceAmount").removeAttr("style");
  }
  </script>
  
  
  <script>
  function getComericaTransAmount(){
	  $("#comericaDebitAmount").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  $("#comericaCreditAmount").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  $("#comericaBalanceAmount").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getComericaTransactions?${_csrf.parameterName}=${_csrf.token}", 	   
 		    success: function(data) {
 		    	var json = JSON.parse(data);
 		    	 $("#comericaDebitAmountValue").append(json.debitAmount)
 		    	 $("#comericaCreditAmountValue").append(json.creditAmount)
 		    	 $("#comericaBalanceAmountValue").append(json.balanceAmount)
 		    	
 		    }
	  });
		    
	  
	  $("#comericaDebitAmount").removeAttr("style");
	  $("#comericaCreditAmount").removeAttr("style");
	  $("#comericaBalanceAmount").removeAttr("style");
  }
  </script>
  
  <script>
  function getChaseTransAmount(){
	  $("#chaseDebitAmount").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  $("#chaseCreditAmount").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  $("#chaseBalanceAmount").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getChaseTransactions?${_csrf.parameterName}=${_csrf.token}", 	   
 		    success: function(data) {
 		    	var json = JSON.parse(data);
 		    	 $("#chaseDebitAmountValue").append(json.debitAmount)
 		    	 $("#chaseCreditAmountValue").append(json.creditAmount)
 		    	 $("#chaseBalanceAmountValue").append(json.balanceAmount)
 		    	
 		    }
	  });
		    
	  
	  $("#chaseDebitAmount").removeAttr("style");
	  $("#chaseCreditAmount").removeAttr("style");
	  $("#chaseBalanceAmount").removeAttr("style");
	 
  }
  </script>
  
  <script>
  function getTotal(){
	  $("#debitAmountTotal").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  $("#creditAmountTotal").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  $("#balanceAmountTotal").attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	  alert("hi");
	  //var amexDebitAmount = document.getElementById('amexDebitAmountValue').value;
  }
  </script>
  
  
<script>
	function getAccountsList(){
		
		var sourceleadUrl ='${sourceleadUrl}';
		var debitAmount = 0;
		var creditAmount = 0;
		var balanceAmount = 0;
		
		
		  $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getAccountsList?${_csrf.parameterName}=${_csrf.token}", 	   
	   		    success: function(data) {
	   		    	
	   		    	
	   		    	var json = JSON.parse(data);
	   	        	var result = "";
	   	        	result = result +'<thead>';
	   	        	result = result +'<tr>';
	   	        	result = result +'	<th>Financial Institute</th>';
	   	        	result = result +'  <th>Debit (USD)</th>';
	   	        	result = result +'  <th>Credit (USD)</th>';
	   	        	result = result +' <th>Balance (USD)</th>';
	   	        	result = result +'</tr>';
	   	        	result = result +'	</thead>';
	   	        	result = result +'<tbody>';
	   	        	
		   	     	for (var i=0; i<json.length; i++)
	   				{  
		   	     	    var report = json[i];
		   	     	    debitAmount = debitAmount + report.debitAmount;
		   	     	    creditAmount = creditAmount + report.creditAmount;
		   	     	    balanceAmount = balanceAmount + report.balanceAmount;
		   	     	    
			   	     	result = result+'<tr>';
			   	        result = result +'<td><a href="<c:url value="/financialInstituteSummary?financialInstitute='+report.identity+'"/>" class="tbldata-dotdot-big">';
					   	result = result +'<div class="proj_show"> <span class="tbldata-dotdot-me">'+report.financialInstituteName+'</span> <i class="fa fa-binoculars"></i> </div>';
					   	result = result +'</a></td>';
					   	result = result +'<td><div class="tbldata-dotdot-vsm">'+report.debitAmount+'</div></td>';
					   	result = result +'<td><div class="tbldata-dotdot-vsm">'+report.creditAmount+'</div></td>';
					   	result = result +'<td><div class="tblminwidth-150">'+report.balanceAmount+'</div></td>';
			   	        result = result +'</tr>';
	   				}
		   	     	
		   	        result = result +'<tfoot>';
	   	        	result = result +' <tr>';
	   	        	result = result +' <td colspan="1">Total</td>';
	   	        	result = result +' <td>'+debitAmount+' <b class="text-danger">Dr</b></td>';
	   	        	result = result +' <td>'+creditAmount+' <b class="text-success">Cr</b></td>';
	   	        	result = result +'<td>'+balanceAmount+' Cr</td>';
	   	        	result = result +'</tr>';
	   	        	result = result +'</tfoot>';
	   	        	
	   	        	result = result +'</tbody>';
	   	    	    $("#financialInstituesList").empty();  
	   	    	    $("#financialInstituesList").append(result)
	   	    	   
	   		    	
	   		    }
		  });
	}
</script>


 <script>
$(document).ready(function() {
    $("#transaction-pop").click(function(){
		$(this).next('div.approved_pop').toggle()
	});
    $(".approved_pop, #transaction-pop").click(function(e) {
        e.stopPropagation();
    });	
});
	
</script> 


<script>
function pagination(){ 
    $('#financialInstituesList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    })
}
</script>

<script>
  $( function() {
    $( "#dpfromdate, #dptodate" ).datepicker({
      changeMonth: true,
      changeYear: true
    });
  } );
</script> 

<script>
		function advancedsearch(){
			$('.advanced-search').toggle();
		}
</script>
  
  

