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
              <!-- <li> <a href="paymenttype.html"><i class="glyphicon glyphicon-plus"></i>&nbsp;&nbsp;Payment Type</a> </li> -->
            </ul>
          </div>
          <!-- Advanced Search -->
          <div class="col-md-12 clearfix inst-search advanced-search">
          <form:form id="financial_from"  name="financial_from" method="post"> 
            <div class="form-inline pull-left">
                <div class="form-group">
                  <input type="text" class="form-control input-sm calendar" id="dpfromdate" name="fromTransDate" placeholder="From Date">
                </div>
                <div class="form-group">
                  <input type="text" class="form-control input-sm calendar"  name="toTransDate" id="dptodate" placeholder="To Date" >
                </div>
            </div>
            
            <div class="col-md-2 timesheet_head transaction-hd ml10"> <i class="fa fa-filter"></i><span>Payment</span> <i class="glyphicon glyphicon-play" id="transaction-pop"></i>
              <div class="approved_pop" id="paymentType_popUp">
                <ul>
                  <c:forEach var="report" items="${paymentTypesList}">
                  <li><input id="paymentType${report.id}" name="paymentTypeId" value ="${report.id}" type="checkbox"><label>${report.paymentType}</label></li>
                  </c:forEach>
                </ul>
              </div>
            </div>
            &nbsp; &nbsp; &nbsp;<button type="button" class="btn btn-sm btn-primary" onclick="getAccountsList('advSearch');"><i class="fa fa-search"></i></button>
          </form:form>
          </div>
          </div>
          
          <!-- Advanced Search --> 
        </div>
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
  
  
 <script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
</script> 

  
<script>
	function getAccountsList(advSearch){
		
		var sourceleadUrl ='${sourceleadUrl}';
		var debitAmount = 0;
		var creditAmount = 0;
		var balanceAmount = 0;
		
		var advanceSearch = advSearch;
		
		 var frm = $('#financial_from').serializeFormJSON();
	     var con = JSON.stringify(frm);
	     con = con.replace(/[{}]/g, "");
	     var jsonData = encodeURIComponent(con);
		
		  $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getAccountsList?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 	   
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
	   	        	result = result +'<td>'+balanceAmount+'</td>';
	   	        	result = result +'</tr>';
	   	        	result = result +'</tfoot>';
	   	        	
	   	        	result = result +'</tbody>';
	   	    	    $("#financialInstituesList").empty();  
	   	    	    $("#financialInstituesList").append(result)
	   	    	   
	   	    	    if(advanceSearch != 'undefined' && advanceSearch != null){
	   	    	    	advancedsearch();
	   	    	    	$("#paymentType_popUp").hide();
	   	    	    }
	   		    	
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
  
  

