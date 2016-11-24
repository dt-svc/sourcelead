<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <link href="resources/css/bootstrap-select.min.css" rel="stylesheet">
 <link href="resources/css/jquery-ui.css" rel="stylesheet">
 <script src="resources/js/bootstrap-select.min.js"></script>
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
   window.onload = function () {
	   getFinancialTransactionsList();
 };
</script> 

<style type="text/css">
.require_wrap_table.chosen-table{
overflow: visible;
}
</style>

<body>

  
  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Account Summary</h2>
      </div>
      <div class="pull-right brud-crum">Bank Office &raquo; Account Summary</div>
    </section>
    <div class="content-area clearfix">
      <div class="clearfix form-horizontal mt">
        <div class="row">
          <div class="edit-button hidden"><i class="fa fa-edit"></i></div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 dynamic-view">
          <form:form id="financialSummary_from"  name="financialSummary_from" method="post"> 
			 <div class="row">
			  	<div class="clearfix instsearch mb">
		  		  <div class="col-md-4 timesheet_head summary_head">
			  		  <ul>
                          <li><i class="fa fa-filter"></i><span>Payment Type</span>
                           <i class="glyphicon glyphicon-play" id="transaction-pop"></i>|
                            <div class="approved_pop">
                              <ul>
								<c:forEach var="report" items="${boPaymentTypesList}">  
		                        	<li><input id="paymentType${report.id}" name="paymentTypeId" value ="${report.id}" type="checkbox"><label>${report.paymentType}</label></li>
								  </c:forEach>
                              </ul>
                            </div>
                          </li>
                          
                          <li>
                           <i class="fa fa-filter"></i><span>Amount</span><i class="glyphicon glyphicon-play" id="pop-dbcr"></i>
                            <div class="approved_pop">
                              <ul>
                                <li><input type="checkbox" id="c1" name="transType" value ="DEBIT" >&nbsp;Debit</li>
                                <li><input type="checkbox" id="c2" name="transType" value ="CREDIT" >&nbsp;Credit</li>
                              </ul>
                            </div>
                          </li>
                    </ul>
			     </div>
			    
			    <div class="col-md-6">
				    <div class="row">
					  <div class="col-md-5">
					     <input type="text" value="" placeholder="From Date" class="form-control input-sm calendar" id="dpfromdate" name="fromTransDate">
					  </div>
			  		  <div class="col-md-5">
					     <input type="text" value="" placeholder="To Date" class="form-control input-sm calendar" id="dptodate" name="toTransDate">
					  </div>
					  <div class="col-md-2">
						  <button type="button" class="btn btn-sm btn-primary" onclick="getFinancialTransactionsList();"><i class="fa fa-search"></i>&nbsp;&nbsp;Search</button>
					  </div>
				  </div>
		  		 </div>
			    </div>
			  </div>
			</form:form>
			  
            <div class="row summar">
              <div class="col-md-3">
                <label>Financial Institutions</label>
                <input type="text" class="form-control input-sm" id="financialInstitutionsName" readonly>
              </div>
             <!--  <div class="col-md-3">
                <label>Account Number</label>
                <input type="text" class="form-control input-sm" value="998844889978" readonly>
              </div> -->
              <div class="col-md-3">
                <label>Credits Total (USD)</label>
                <input type="text" class="form-control input-sm" id="totalCreditAmount" readonly>
              </div>
              <div class="col-md-3">
                <label>Debits Total (USD)</label>
                <input type="text" class="form-control input-sm" id="totalDebitAmount" readonly>
              </div>
              <div class="col-md-3">
                <label>Balance (USD)</label>
                <input type="text" class="form-control input-sm" id="totalBalanceAmount" readonly>
              </div>
				<!-- <span title="Edit" class="edit"><i class="fa fa-pencil"></i></span> -->
            </div>
          </div>
          <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 mt clearfix"> 
            <!-- Tab for Acount Transaction area -->
            <div class="accountsettings-tab account-tab">
              <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#transaction">Transaction</a></li>
                <li><a data-toggle="tab" href="#transactiontype">Transaction Imports</a></li>
              </ul>
              <div class="tab-content panel-body">
                <div id="transaction" class="tab-pane fade in active">
                  <div class="timesheet_out">
                    <div class="vendor_wrap">
                      <div class="timesheet_head">
                        <ul>
                          <li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
                            <div class="export_pop">
                              <ul>
                                <li><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;PDF</li>
                                <li><i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;Excel</li>
                                <li><i class="fa fa-file-word-o"></i>&nbsp;&nbsp;Word</li>
                              </ul>
                            </div>
                          </li>
                          <li> <a href="javascript:void(0)" class="addcon-pop"><i class="fa fa-plus"></i>Add Transcation</a>
                            <div class="collapse-div-adduser pop-div col-md-4 transaction-div">
                             <i class="fa fa-close close-div"></i>
                              <div class="form-horizontal clearfix">
                                <div class="clearfix mb10">
                                  <div class="col-md-12">
                                    <input type="text" class="form-control input-sm" value="" placeholder="Id">
                                  </div>
                                </div>
                                <div class="clearfix mb10">
                                  <div class="col-md-12">
                                    <input type="text" class="form-control input-sm calendar" value="" placeholder="Date *" id="transaction-date">
                                  </div>
                                </div>
                                <div class="clearfix mb10">
                                  <div class="col-md-7">
                                    <input type="text" class="form-control input-sm" value="" placeholder="Amount *">
                                  </div>
                                  <div class="col-md-5 select-field">
                                    <select class="form-control input-sm">
                                      <option>Debit</option>
                                      <option>Credit</option>
                                    </select>
                                  </div>
                                </div>
                                <div class="clearfix mb10">
                                  <div class="col-md-12">
                                    <textarea class="form-control" rows="2" placeholder="Description"></textarea>
                                  </div>
                                </div>
                                <div class="clearfix mb10">
                                  <div class="col-md-12 chosen-a">
                                    <select class="form-control chosen-select">
                                      <option selected>-Select Type-</option>
                                      <option>Payroll Expense</option>
                                      <option>Payroll Net Payment</option>
                                      <option>Payroll Tax Withholding</option>
                                    </select>
                                  </div>
                                </div>
                                <div class="clearfix mb10">
                                  <div class="col-md-12">
                                    <input type="text" class="form-control input-sm" value="" placeholder="Cheque / Slip">
                                  </div>
                                </div>
                                <div class="clearfix mr">
                                  <div class="pull-right">
                                    <button type="submit" class="btn btn-sm btn-primary">Add</button>
                                    <button type="submit" class="btn btn-sm btn-default ml10">Cancel</button>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </li>
                          <div class="clearfix"></div>
                        </ul>
                        <div class="clearfix"></div>
                        <!-- <div class="remove_search">
                          <input type="text" placeholder="search" />
                        </div> -->
                      </div>
                      
                      <div class="require_wrap_table table-wrap chosen-table">
                      <div id="loading"></div> 
                         <table id="financialTransactionsList" class="display" cellspacing="0" width="100%"> </table>
						<!--<td>
                            <div class="editdiv-summary">
                             <i class="fa fa-close close-div"></i>
							   <p class="ml"><strong>Edit Transaction</strong></p>
                              <div class="form-horizontal clearfix">
                                <div class="clearfix mb10">
                                  <div class="col-md-6">
                                    <input type="text" class="form-control input-sm" value="" placeholder="Id">
                                  </div>
                                  <div class="col-md-6">
                                    <input type="text" class="form-control input-sm calendar" value="" placeholder="Date *" id="transaction-date">
                                  </div>
                                </div>
                                <div class="clearfix mb10">
                                  <div class="col-md-6">
                                    <input type="text" class="form-control input-sm" value="" placeholder="Amount *">
                                  </div>
                                  <div class="col-md-6 select-field">
                                    <select class="form-control">
                                      <option>Debit</option>
                                      <option>Credit</option>
                                    </select>
                                  </div>
                                </div>
                                <div class="clearfix mb10">
                                  <div class="col-md-12">
                                    <textarea class="form-control" rows="2" placeholder="Description"></textarea>
                                  </div>
                                </div>
                                <div class="clearfix mb10">
                                  <div class="col-md-6 chosen-a">
                                    <select class="form-control chosen-select">
                                      <option selected>-Select Type-</option>
                                      <option>Payroll Expense</option>
                                      <option>Payroll Net Payment</option>
                                      <option>Payroll Tax Withholding</option>
                                    </select>
                                  </div>
                                  <div class="col-md-6">
                                    <input type="text" class="form-control input-sm" value="" placeholder="Cheque / Slip">
                                  </div>
                                </div>
                                <div class="clearfix mr">
                                  <div class="pull-right">
                                    <button type="submit" class="btn btn-sm btn-primary">Add</button>
                                    <button type="submit" class="btn btn-sm btn-default ml10">Cancel</button>
                                  </div>
                                </div>
                              </div>
                            </div> 
                           </td>-->
                      </div>
                    </div>
                    <!-- Project --> 
                  </div>
                </div>
                
                
                
                <!-- Transaction Type Tab-->
                <div id="transactiontype" class="tab-pane fade">
                	 <div class="timesheet_out">
                    <div class="vendor_wrap">
                      <div class="timesheet_head">
                        <div class="clearfix"></div>
                        <div class="remove_search templeasearch">
                          <input type="text" placeholder="search" />
                        </div>
                      </div>
                      <div class="require_wrap_table table-wrap">
                        <table>
                          <thead>
                            <tr>
                              <th>S.No</th>
								<th>File</th>
                              <th>By</th>
                              <th>Date</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
								<td><div class="tbldata-dotdot-vsm">0012558899</div></td>
                              <td>
                              <div class="tbldata-dotdot-big pull-left">addrequirement-transaction-exmple.html</div>
								  <span class="pull-left ml10" title="Download File"><a href="javscript:void(0)"><i class="fa fa-download"></i></a></span>
                              </td>
                              <td><div class="tbldata-dotdot-big">Karanjohnsmith Abhiram</div></td>
							 <td><div class="tbldata-dotdot-vsm">23/05/2016</div></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <nav>
                        <ul id="paging" class="pagination">
                          <li><a href="#">Previous</a></li>
                          <li><a href="#">2</a></li>
                          <li><a href="#">3</a></li>
                          <li><a href="#">4</a></li>
                          <li><a href="#">5</a></li>
                          <li class="disabled"><a href="#">6</a></li>
                          <li data-page="last"><a href="#">Next</a></li>
                        </ul>
                        <div class="clearfix"></div>
                      </nav>
                    </div>
                    <!-- Project --> 
                  </div>
                	
                	
                </div>
              <!-- Transaction type tab ends -->
              </div>
            </div>
            <!-- Tab for Acount Transaction area ends --> 
          </div>
        </div>
      </div>
    </div>
  </section>
  
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
	function getFinancialTransactionsList(){
		var sourceleadUrl ='${sourceleadUrl}';
		var financialInstitute ='${financialInstitute}';
		var boPaymentTypesList ='${boPaymentTypesList}';
		var boPaymentTypesListSize = '${boPaymentTypesListSize}';
		var totalDebitAmount = 0;
		var totalCreditAmount = 0;
		var totalBalanceAmount = 0;
		
		 var frm = $('#financialSummary_from').serializeFormJSON();
	     var con = JSON.stringify(frm);
	     con = con.replace(/[{}]/g, "");
	     var jsonData = encodeURIComponent(con);
	     $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		  $.ajax({
			   	type: "POST",
			    url: sourceleadUrl+"/getFinancialTransactionsList?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&financialInstitute="+financialInstitute, 	   
	   		    success: function(data) {
	   		    	
	   		    	
	   		    	var json = JSON.parse(data);
	   	        	var result = "";
	   	        	result = result +'<thead>';
	   	        	result = result +'<tr>';
	   	        	result = result +'<th>Id</th>';
	   	        	result = result +'<th>Date</th>';
	   	        	result = result +'<th>Description</th>';
	   	        	result = result +'<th>Debit (USD)</th>';
	   	        	result = result +'<th>Credit (USD)</th>';
	   	        	result = result +'<th>Payment Type</th>';
	   	        	/* result = result +'<th>Actions</th>'; */
	   	        	result = result +'</tr>';
	   	        	result = result +'	</thead>';
	   	        	result = result +'<tbody>';
		   	     	for (var i=0; i<json.finalcialInstituesList.length; i++)
	   				{  
		   	     	    var report = json.finalcialInstituesList[i];
			   	     	var debitAmount = 0;
			   			var creditAmount = 0;
			   			var balanceAmount = 0;
			   			var transDesc = "---"
			   	     	if(report.debitAmount != null && report.debitAmount != '' && report.debitAmount != 'undefined'){
			   	     	   debitAmount = report.debitAmount;
	   	        		}
			   	     	
			   	        if(report.creditAmount != null && report.creditAmount != '' && report.creditAmount != 'undefined'){
			   	        	creditAmount = report.creditAmount;
	   	        		}
			   	        
			   	        if(report.transDesc != null && report.transDesc != '' && report.transDesc != 'undefined'){
			   	        	transDesc = report.transDesc;
	   	        		}
			   	        
			   	        totalDebitAmount = totalDebitAmount + debitAmount;
		   	     	    totalCreditAmount = totalCreditAmount + creditAmount;
		   	     	    totalBalanceAmount = totalCreditAmount - totalDebitAmount;
			   	        
			   	     	result = result+'<tr>';
			   	        result = result +'<td><div class="tblminwidth-75">'+(i+1)+'</div></td>';
			   	        result = result +'<td><div class="tblminwidth-75">'+report.transDateStr+'</div></td>';
					   	result = result +'<td><div class="tbldata-dotdot-me">'+transDesc+'</div></td>';
					   	result = result +'<td><div class="tbldata-dotdot-100">'+debitAmount+'</div></td>';
					   	result = result +'<td><div class="tbldata-dotdot-100">'+creditAmount+'</div></td>';
					   	result = result +'<td>';
					   	result = result +'<div class="tblminwidth-150">';
						   	result = result +'<select class="form-control chosen-select" id="payId'+report.id+'" onchange="updatePaymentType(\''+report.id+'\')">';
						   	result = result +'<option value="">-Select Payment Type-</option>';
						   	for (var j = 0; j < json.boPaymentTypesList.length; j++) {
								   var payType = json.boPaymentTypesList[j];
								   var selected = '';
								   if(report.paymentId != null && report.paymentId != "" && report.paymentId != 'undefined' && report.paymentId == payType.id){
			    		 	    		 selected = 'selected';
			    		 	       }
			    		 	    	result = result+"<option value='"+payType.id+"' "+selected+">"+payType.paymentType+"</option>"; 
							 }   
						   	result = result +'</select>';
					   	result = result +'</div>';
					   	result = result +'</td>';
					   	/* result = result +'<td><span class="text-danger"><i class="fa fa-trash"></i></span>&nbsp;&nbsp;<span><i class="fa fa-pencil" onClick="editFun()" title="Edit"></i></span></td>'; */
			   	        result = result +'</tr>';
	   				}
	   	        	
	   	        	result = result +'</tbody>';
	   	        	$("#loading").removeAttr("style"); 
	   	    	    $("#financialTransactionsList").empty();  
	   	    	    $("#financialTransactionsList").append(result)
	   	    	    
	   	    	    $("#totalDebitAmount").val(totalDebitAmount+" Cr");  
	   	    	    $("#totalCreditAmount").val(totalCreditAmount+" Cr");  
	   	    	    $("#totalBalanceAmount").val(totalBalanceAmount+" Cr");  
	   	    	    if(financialInstitute != null && financialInstitute !="" && financialInstitute !="undefined"){
	   	    	    	if(financialInstitute == 'amex'){
	   	    	    		$("#financialInstitutionsName").val('American Exchange Credit Card');  
	   	    	    	}else if(financialInstitute == 'comerica'){
	   	    	    		$("#financialInstitutionsName").val('Comerica Bank');  
	   	    	    	}else if(financialInstitute == 'jmpc'){
	   	    	    		$("#financialInstitutionsName").val('Chase Bank');  
	   	    	    	}
	   	    	    }
	   	    	    
	   	    	   var config = {
	   				  '.chosen-select'           : {},
	   				  '.chosen-select-deselect'  : {allow_single_deselect:true},
	   				  '.chosen-select-no-single' : {disable_search_threshold:10},
	   				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
	   				  '.chosen-select-width'     : {width:"95%"}
	   				}
	   				for (var selector in config) {
	   				  $(selector).chosen(config[selector]);
	   				}
	   	    	 
	   				$('.chosen-select').each(function() {
	   					var $this = $(this);
	   					$this.next().css({'width': '100%'});
	   				})
	   				pagination();
	   		    }
		  });
	}
</script>  

<script>
function pagination(){ 
    $('#financialTransactionsList').DataTable( { 
    	"scrollCollapse": false,
    	"chosen-select": true,
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
  
 <jsp:include page="../masterFooter.jsp" />
  <script>

// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	

	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});
</script> 
  <script>
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
   });
	  
	  //Transaction-pop
$(document).ready(function() {
    $("#transaction-pop").click(function(){
		$(this).next('div.approved_pop').toggle()
	});
    $("html").click(function() {
        $(".approved_pop").hide();
    });
    $(".approved_pop, #transaction-pop").click(function(e) {
        e.stopPropagation();
    });	
});

// Approved Popup
$(document).ready(function() {
    $("#pop-dbcr").click(function(){
    	$(this).next('div.approved_pop').toggle();
		$("#transaction-pop").next('div.approved_pop').fadeOut(100);
	});
    $("html").click(function() {
        $(".approved_pop").hide();
    });
    $(".approved_pop, #pop-dbcr").click(function(e) {
        e.stopPropagation();
    });	
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(this).next('div.export_pop').toggle()
		//$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});
	  
//Table edit pop
function editFun(){
	alert('hi');
}
</script>


<script type="text/javascript">
  $( function() {
	  
	  $( "#transaction-date" ).datepicker({
	      changeMonth: true,
	      changeYear: true
	  });  
	  
      $('#dpfromdate').datepicker({
    	 changeMonth: true,
         changeYear: true,
	      dateFormat: 'mm/dd/yy',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	            $("#dptodate").datepicker("option", "minDate", dt);
	        }
	  });
	  
	  $('#dptodate').datepicker({
		  changeMonth: true,
	      changeYear: true,
	      dateFormat: 'mm/dd/yy',
	        onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() - 1);
	            $("#dpfromdate").datepicker("option", "maxDate", dt);
	        }
	  }); 
  });
</script>


<script type="text/javascript">
 function updatePaymentType(id) {
	 var financialInstitute ='${financialInstitute}';
	 var paymentType = $('#payId'+id).val();
	 var sourceleadUrl = '${sourceleadUrl}';
	 $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updatePaymentType?${_csrf.parameterName}=${_csrf.token}&id="+id+"&paymentType="+paymentType+"&financialInstitute="+financialInstitute,	    	   
		    success: function(data) {
		    	getFinancialTransactionsList();
		    }
	   });
	
 }
 </script>
 
  <div class="theme-color"> <span class="icon-theme" title="click here"><i class="fa fa-gears"></i></span>
    <ul class="list-unstyled">
      <li><a href="#" class="" id="btn1"><span class="dark-blue"></span><span class="light-grey"></span></a></li>
      <li><a href="#" class="" id="btn2"><span class="flat-green"></span><span class="dark-purle"></span></a></li>
      <li><a href="#" class="" id="btn3"><span class="hdr-blue"></span><span class="sidenav-blue"></span></a></li>
    </ul>
  </div>
</div>
</body>
</html>