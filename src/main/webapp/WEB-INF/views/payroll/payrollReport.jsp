<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
window.onload = function () {
	getWagesTaxSummaryByEmpId();
}
</script>

 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Associate(s) ROI</h2></div>

<%--       <a href="<c:url value='/payrollAllBalanceReport'/>" class="ml10 mt5" id=generateReportId title="Generate Report">All Balance Report</a> --%>
 <%--  <a href="<c:url value='/searchBasedReport'/>" class="ml10 mt5" id=generateReportId title="Generate Report">Search Based Report</a>--%>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/associateReportsList'/>">Associate Reports</a> &raquo; Associate(s) ROI</div>
    </section>
    
    <section class="project_side_wrap_inn_tabs">
    
    
<div class="reportHead">
<div class="clearfix mt"></div>
    			<div id="payrollReportForm" class="ml mt mb">
    				<form:form id="payroll_report_form" name="payroll_report_form"  enctype='multipart/form-data' >
	        			<div class="positionRelative clearfix col-md-6 col-md-offset-3 mb">
	        			<div class="col-md-10">
			                <select class="chosen-select" id="empId" name="empName">
			                  	<option value=" " selected >-Select Employee-</option>
					            <c:forEach var="wage" items="${wageTaxSummaryList}">
									<option value="${wage.employeeId}">${wage.employeeName}</option>
								</c:forEach>
			                </select>
			                </div>			
							<span id="runButtonDiv" class="col-md-2"><button type="button" class="btn btn-primary" id="customize_btn" onclick="getWagesTaxSummaryByEmpId();">Run</button></span> 
	        			</div>  
       				</form:form>  
    			</div>
    	<div class="clearfix"></div>
    </div>      
        
<div class="backWhite pt5">        
        
<div class="clearfix">
      <ul class="project-tabs-menu" id="menuli">
	        <li class="current" id="wtsli"><a href="#tab-1" onclick="getWagesTaxSummaryByEmpId();">Wages & Tax Summary</a></li>
	        <li><a href="#tab-2" onclick="getIncomeData();">Income</a></li>
	        <li><a href="#tab-3" onclick="getChecks();">Manual checks</a></li>
	        <li><a href="#tab-4" onclick="getAccontDeductions();">Account Deductions</a></li>
	        <li><a href="#tab-5" onclick="getHRDeductions();">HR Deductions</a></li>
	        <li><a href="#tab-6" onclick="finalBalance();">Balance</a></li>
        	<div class="clearfix"></div>
      </ul>
</div>
      
      <div id="loading"></div>
        
      <div class="project_tabs clearfix pdtop">
	        <div id="tab-1" class="project_tabs_inn  require_wrap_table datatable_filter filter_position" style="display: block"></div>
	        <div id="tab-2" class="project_tabs_inn require_wrap_table datatable_filter filter_position"></div>
	        <div id="tab-3" class="project_tabs_inn require_wrap_table datatable_filter filter_position"></div>
	      	<div id="tab-4" class="project_tabs_inn  require_wrap_table datatable_filter filter_position"></div>
	        <div id="tab-5" class="project_tabs_inn  require_wrap_table datatable_filter filter_position"></div>
	        <div id="tab-6" class="project_tabs_inn"></div>          
      </div>
</div>
     <!--  </div> -->
    </section>
    
    <div class="modal modal_Expenses AccountDedu" id="one">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" onClick="closeModalExpenses();">&times;</button>
        <h4 class="modal-title" id="empName"></h4>
      </div>
      <div class="modal-body project_tabs_inn_table">
        <div id="accountdeDuctiondetails"></div>
      </div>
    </div>

</div>
</div>

<div class="modal modal_Expenses hrDedu" id="two">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" onClick="closeModalExpenseshr();">&times;</button>
        <h4 class="modal-title" id="hrEmpName"></h4>
      </div>
      <div class="modal-body project_tabs_inn_table">
        <div id="hrDeductiondetails"></div>
      </div>
    </div>

</div>
</div>
    
  </section>
  

  <script type="text/javascript">
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
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script>
 		<script> 
$(document).ready(function() {
    $("#tab-1").show();
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $("#wtsli").addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});

function redirect() {
	 $("#tab-1").show();
//   $(".project-tabs-menu a").click(function(event) {
      event.preventDefault();
      $("#menuli li").removeClass("current");
      $("#wtsli").addClass("current");
      
//   });
}
    </script>
    
    
    <script type="text/javascript">
function getIncomeData(){
		$("#customize_btn").attr("onClick","getIncomeData();");
	    var empId = $("#empId").val();
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getInvoiceDetailsByEmpId?orgLocationId="+orgLocationId+"&searchValue="+searchValue+"&empId="+empId;
		/* url += "&"+$($('#payroll_list_form')[0]).serialize(); */
	    prepareInvoiceData(url);
}
</script> 

<script>
function prepareInvoiceData(url){
	$("#tab-3").empty();
	$("#tab-4").empty();
	$("#tab-5").empty();
	$("#tab-6").empty();
	$("#tab-1").empty();
	$("#tab-2").empty();
 	 var result = "";
 	 
 		result = result +" <div class='reportHead'>";
		result = result + " <ul class='pull-right'>";
		result = result + " <li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick='dataTableSearch()'></span></li>";
		result = result + "</ul>";
		result = result + "</div>";  		
 		
 	  	result = result +"<table id='incometable' class='display' cellspacing='0' width='100%' >"; 
      	result = result +"<thead>"; 
      	result = result + '<tr>';
		result = result + '<th>SKU</th>';
		result = result + '<th>Product/Service</th>';
		result = result + '<th>Name</th>';
		result = result + '<th>Num</th>';
		result = result + '<th>Memo/Description</th>';
		result = result + '<th>Date</th>';
		result = result + '<th>Last Modified</th>';
		result = result + '<th>Due Date</th>';
		result = result + '<th>Rate</th>';
		result = result + '<th>Qty</th>';
		result = result + '<th>Amount</th>';
		result = result + '<th>A/R Paid</th>';
		result = result + '<th>Old/New</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#tab-2").append(result);
  	   /* $("#loading").removeAttr("style"); */
       incomeDataPagination(url); 
}
</script>


<script>
function incomeDataPagination(url){
	var colIndex = 0;
    $('#incometable').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "sku" },
            { "data": "productService"},
            { "data": "empName"},
            { "data": "number" },
            { "data": "memoDescription"},
            { "data": "dateStr" },
            { "data": "lastModifiedStr" },
            { "data": "dueDateStr"},
            { "data": "rate"},
            { "data": "qty"},
            { "data": "amount"},
            { "data": "arPaid"},
            { "data": "oldNew"} 
        ],
        "columnDefs": [  {
	    	  	"targets": colIndex++,
	    	  	"data": "sku",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
		    	  		if(opt.sku !=null && opt.sku !='' && opt.sku !='undefined'){
	        		    	result = result + '<td><div class="tblminwidth-75">'+opt.sku+'</div></td>';
	        		    }else{
	        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
	        		    }
		             return result;
	    	  	}
        }, {
	    	  "targets": colIndex++,
              "data": "productService",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	            	  if(opt.productService !=null && opt.productService !='' && opt.productService !='undefined'){
	      		    	result = result + '<td><div>'+opt.productService+'</div></td>';
	      		    }else{
	      		    	result = result + '<td><div>---</div></td>';
	      		    }
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "empName",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
	            	if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.empName+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
	            return result;
            }
          }, {
              "targets": colIndex++,
              "data": "number",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	            	  if(opt.number !=null && opt.number !='' && opt.number !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.number+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "memoDescription",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
	                		if(opt.memoDescription !=null && opt.memoDescription !='' && opt.memoDescription !='undefined'){
								result = result + ' <td><div class="tblminwidth-75">'+opt.memoDescription+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "dateStr",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
		    	  		if(opt.dateStr !=null && opt.dateStr !='' && opt.dateStr !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.dateStr+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "lastModifiedStr",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
		    	  		if(opt.lastModifiedStr !=null && opt.lastModifiedStr !='' && opt.lastModifiedStr !='undefined'){
							result = result + '<td><div class="tblminwidth-75">'+opt.lastModifiedStr+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "dueDateStr",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
	                	  if(opt.dueDateStr !=null && opt.dueDateStr !='' && opt.dueDateStr !='undefined'){
								result = result + '<td><div class="tblminwidth-75">'+opt.dueDateStr+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
                	  return result;
                  }
                },{
                    "targets": colIndex++,
                    "data": "rate",
                    "render": function ( data, type, opt, meta ) {
                    	var result = "";
	                    	if(opt.rate !=null && opt.rate !='' && opt.rate !='undefined'){
								result = result + '<td><div class="tblminwidth-75">$ '+opt.rate+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">0.00</div></td>';
							}
                    	return result;
                    }
                  },{
                      "targets": colIndex++,
                      "data": "qty",
                      "render": function ( data, type, opt, meta ) {
                    	  var result = "";
	                    	  if(opt.qty !=null && opt.qty !='' && opt.qty !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.qty+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
                    	  return result;
                      }
                    },{
                        "targets": colIndex++,
                        "data": "amount",
                        "render": function ( data, type, opt, meta ) {
                        	var result = "";
	                        	if(opt.amount !=null && opt.amount !='' && opt.amount !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.amount+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
                        	return result;
                        }
                      },{
                          "targets": colIndex++,
                          "data": "arPaid",
                          "render": function ( data, type, opt, meta ) {
                        	  var result = "";
	                        	  if(opt.arPaid !=null && opt.arPaid !='' && opt.arPaid !='undefined'){
										result = result + '<td><div class="tblminwidth-75">'+opt.arPaid+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
                        	  return result;
                          }
                        }, {
                            "targets": colIndex++,
                            "data": "oldNew",
                            "render": function ( data, type, opt, meta ) {
                            	var result = "";
	                            	if(opt.oldNew !=null && opt.oldNew !='' && opt.oldNew !='undefined'){
										result = result + '<td><div class="tblminwidth-75">'+opt.oldNew+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
                            	return result;
                            }
                          }], 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#incometable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getIncomeData();
    	   }
    	});
}
function dataTableSearch(){
	getIncomeData();
}
</script>
    
    
    <!-- <script>
    function getIncomeData(){
    	var empId = $("#empId").val();
    	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    		  var sourceleadUrl ='${sourceleadUrl}';
    				  $.ajax({
    				    	type: "GET",
    				        url: sourceleadUrl+"/getInvoiceDetailsByEmpId?${_csrf.parameterName}=${_csrf.token}&empId="+empId+"&orgLocationId="+orgLocationId,
    				        success: function(data) {
    						 var result = "";				        	
    						result = result + '<table id="incometable" class="display" cellspacing="0" width="100%" >';
    						result = result + '<thead>';
    						result = result + '<tr>';
    						result = result + '<th>SKU</th>';
    						result = result + '<th>Product/Service</th>';
    						result = result + '<th>Name</th>';
    						result = result + '<th>Num</th>';
    						result = result + '<th>Memo/Description</th>';
    						result = result + '<th>Date</th>';
    						result = result + '<th>Last Modified</th>';
    						result = result + '<th>Due Date</th>';
    						result = result + '<th>Rate</th>';
    						result = result + '<th>Qty</th>';
    						result = result + '<th>Amount</th>';
    						result = result + '<th>A/R Paid</th>';
    						result = result + '<th>Old/New</th>';
    						result = result + '</tr>';
    						result = result + '</thead>	';
    						result = result + '<tbody>';
    						if(data != null && data != ''){
    							 for (var i=0; i<data.length; i++)
    	    						{
    	    			        		 var opt = data[i];
    	    			        		    result = result + ' <tr>';
    	    			        		    
    	    			        		    if(opt.sku !=null && opt.sku !='' && opt.sku !='undefined'){
    	    			        		    	result = result + '<td><div class="tblminwidth-75">'+opt.sku+'</div></td>';
    	    			        		    }else{
    	    			        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
    	    			        		    }
    	    			        		    
    	    			        		    if(opt.productService !=null && opt.productService !='' && opt.productService !='undefined'){
    	    			        		    	result = result + '<td><div>'+opt.productService+'</div></td>';
    	    			        		    }else{
    	    			        		    	result = result + '<td><div>---</div></td>';
    	    			        		    }
    	    			        		    
    	    			        		    if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
    	    									result = result + ' <td><div class="tblminwidth-75">'+opt.empName+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
    	    								}
    	    			        		    
    	    			        		    if(opt.number !=null && opt.number !='' && opt.number !='undefined'){
    	    									result = result + ' <td><div class="tblminwidth-75">'+opt.number+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
    	    								}
    	    								
    	    								if(opt.memoDescription !=null && opt.memoDescription !='' && opt.memoDescription !='undefined'){
    	    									result = result + ' <td><div class="tblminwidth-75">'+opt.memoDescription+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
    	    								}
    	    								
    	    								if(opt.dateStr !=null && opt.dateStr !='' && opt.dateStr !='undefined'){
    	    									result = result + ' <td><div class="tblminwidth-75">'+opt.dateStr+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
    	    								}
    	    								
    	    								if(opt.lastModifiedStr !=null && opt.lastModifiedStr !='' && opt.lastModifiedStr !='undefined'){
    	    									result = result + '<td><div class="tblminwidth-75">'+opt.lastModifiedStr+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
    	    								}
    	    								
    	    								if(opt.dueDateStr !=null && opt.dueDateStr !='' && opt.dueDateStr !='undefined'){
    	    									result = result + '<td><div class="tblminwidth-75">'+opt.dueDateStr+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
    	    								}
    	    								if(opt.rate !=null && opt.rate !='' && opt.rate !='undefined'){
    	    									result = result + '<td><div class="tblminwidth-75">$ '+opt.rate+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">0.00</div></td>';
    	    								}
    	    								
    	    								if(opt.qty !=null && opt.qty !='' && opt.qty !='undefined'){
    	    									result = result + '<td><div class="tblminwidth-75">$ '+opt.qty+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
    	    								}
    	    								
    	    								if(opt.amount !=null && opt.amount !='' && opt.amount !='undefined'){
    	    									result = result + '<td><div class="tblminwidth-75">$ '+opt.amount+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
    	    								}
    	    								
    	    								if(opt.arPaid !=null && opt.arPaid !='' && opt.arPaid !='undefined'){
    	    									result = result + '<td><div class="tblminwidth-75">'+opt.arPaid+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
    	    								}
    	    								
    	    								if(opt.oldNew !=null && opt.oldNew !='' && opt.oldNew !='undefined'){
    	    									result = result + '<td><div class="tblminwidth-75">'+opt.oldNew+'</div></td>';
    	    								}else{
    	    									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
    	    								}
    	    								
    	    								
    	    								result = result + ' </tr>';
    	    						}
    						}
    			        	 
    						result = result + '  </tbody>';
    						result = result + ' </table>';
    						$("#tab-2").empty();
    						$("#tab-2").append(result);
    						$("#loading").removeAttr("style");
    						empInvoiceDetails(); 
    				        	
    				        },
    				        
    				    });
    }
    </script> -->
    
    
  
  
 <script type="text/javascript">
function getChecks(){
		$("#customize_btn").attr("onClick","getChecks();");
	    var empId = $("#empId").val();
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getChecksListByEmployeeId?orgLocationId="+orgLocationId+"&searchValue="+searchValue+"&empId="+empId;
		/* url += "&"+$($('#payroll_list_form')[0]).serialize(); */
	    prepareChecks(url);
}
</script> 

<script>
function prepareChecks(url){
	$("#tab-4").empty();
	$("#tab-5").empty();
	$("#tab-6").empty();
	$("#tab-1").empty();
	$("#tab-2").empty();
	$("#tab-3").empty();
 	 var result = "";
 	 
 		result = result +" <div class='reportHead'>";
		result = result + " <ul class='pull-right'>";
		result = result + " <li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick='dataTableSearch()'></span></li>";
		result = result + "</ul>";
		result = result + "</div>";  		
 		
 	  	result = result +"<table id='empchecksListTable' class='display' cellspacing='0' width='100%' >"; 
      	result = result +"<thead>"; 
      	result = result + '<tr>';
      	result = result + '<th>Emp Id</th>';
		result = result + '<th>Emp Name</th>';
		result = result + '<th>Date</th>';
		result = result + '<th>Type of Expense</th>';
		result = result + '<th>Bank</th>';
		result = result + '<th>Month</th>';
		result = result + '<th>Legal Fees</th>';
		result = result + '<th>Advance</th>';
		result = result + '<th>General Expenses</th>';
		result = result + '<th>Vendor Payments</th>';
		result = result + '<th>Electronic Withdrawals</th>';
		result = result + '<th>Payroll Chks</th>';
		result = result + '<th>Amount</th>';
		result = result + '<th>Check No</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#tab-3").append(result);
  	   /* $("#loading").removeAttr("style"); */
       checksPagination(url); 
}
</script>


<script>
function checksPagination(url){
	var colIndex = 0;
    $('#empchecksListTable').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "empId" },
            { "data": "empName"},
            { "data": "dateStr"},
            { "data": "typeOfExpense" },
            { "data": "bankName"},
            { "data": "month" },
            { "data": "legalFees" },
            { "data": "advanceStr"},
            { "data": "generalExpensesStr"},
            { "data": "vendorPaymentsStr"},
            { "data": "electronicWithdrawals"},
            { "data": "payrollChecksStr"},
            { "data": "amountStr"},
            { "data": "checkNo"}
        ],
        "columnDefs": [  {
	    	  	"targets": colIndex++,
	    	  	"data": "empId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
		    	  		if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
	        		    	result = result + '<td><div class="tblminwidth-75">'+opt.empId+'</div></td>';
	        		    }else{
	        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
	        		    }
		             return result;
	    	  	}
        }, {
	    	  "targets": colIndex++,
              "data": "empName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	            	  if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
	      		    	result = result + '<td><div><a>'+opt.empName+'</a></div></td>';
	      		    }else{
	      		    	result = result + '<td><div>---</div></td>';
	      		    }
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "dateStr",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
	            	if(opt.dateStr !=null && opt.dateStr !='' && opt.dateStr !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.dateStr+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
	            return result;
            }
          }, {
              "targets": colIndex++,
              "data": "typeOfExpense",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	            	  if(opt.typeOfExpense !=null && opt.typeOfExpense !='' && opt.typeOfExpense !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.typeOfExpense+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "bankName",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
	                		if(opt.bankName !=null && opt.bankName !='' && opt.bankName !='undefined'){
								result = result + ' <td><div class="tblminwidth-75">'+opt.bankName+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "month",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
		    	  		if(opt.month !=null && opt.month !='' && opt.month !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.month+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "legalFees",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
		    	  		if(opt.legalFees !=null && opt.legalFees !='' && opt.legalFees !='undefined'){
							result = result + '<td><div class="tblminwidth-75">'+opt.legalFees+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "advanceStr",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
	                	  if(opt.advanceStr !=null && opt.advanceStr !='' && opt.advanceStr !='undefined'){
								result = result + '<td><div class="tblminwidth-75">$ '+opt.advanceStr+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
							}
                	  return result;
                  }
                },{
                    "targets": colIndex++,
                    "data": "generalExpensesStr",
                    "render": function ( data, type, opt, meta ) {
                    	var result = "";
	                    	if(opt.generalExpensesStr !=null && opt.generalExpensesStr !='' && opt.generalExpensesStr !='undefined'){
								result = result + '<td><div class="tblminwidth-75">'+opt.generalExpensesStr+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">0.00</div></td>';
							}
                    	return result;
                    }
                  },{
                      "targets": colIndex++,
                      "data": "vendorPaymentsStr",
                      "render": function ( data, type, opt, meta ) {
                    	  var result = "";
	                    	  if(opt.vendorPaymentsStr !=null && opt.vendorPaymentsStr !='' && opt.vendorPaymentsStr !='undefined'){
									result = result + '<td><div class="tblminwidth-75">$ '+opt.vendorPaymentsStr+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
								}
                    	  return result;
                      }
                    },{
                        "targets": colIndex++,
                        "data": "electronicWithdrawals",
                        "render": function ( data, type, opt, meta ) {
                        	var result = "";
	                        	if(opt.electronicWithdrawals !=null && opt.electronicWithdrawals !='' && opt.electronicWithdrawals !='undefined'){
									result = result + '<td><div class="tblminwidth-75">'+opt.electronicWithdrawals+'</div></td>';
								}else{
									result = result + ' <td><div class="tblminwidth-75">--</div></td>';
								}
                        	return result;
                        }
                      },{
                          "targets": colIndex++,
                          "data": "payrollChecksStr",
                          "render": function ( data, type, opt, meta ) {
                        	  var result = "";
	                        	  if(opt.payrollChecksStr !=null && opt.payrollChecksStr !='' && opt.payrollChecksStr !='undefined'){
										result = result + '<td><div class="tblminwidth-75">$ '+opt.payrollChecksStr+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
									}
                        	  return result;
                          }
                        }, {
                            "targets": colIndex++,
                            "data": "amountStr",
                            "render": function ( data, type, opt, meta ) {
                            	var result = "";
	                            	if(opt.amountStr !=null && opt.amountStr !='' && opt.amountStr !='undefined'){
										result = result + '<td><div class="tblminwidth-75">$ '+opt.amountStr+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
									}
                            	return result;
                            }
                          },{
                              "targets": colIndex++,
                              "data": "checkNo",
                              "render": function ( data, type, opt, meta ) {
                              	var result = "";
	                              	if(opt.checkNo !=null && opt.checkNo !='' && opt.checkNo !='undefined'){
										result = result + '<td><div class="tblminwidth-75">'+opt.checkNo+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
                              	return result;
                              }
                            }], 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#empchecksListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getChecks();
    	   }
    	});
}
function dataTableSearch(){
	getChecks();
}
</script>
  <!-- <script>
  function getChecks(){
	     var empId = $("#empId").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			  var sourceleadUrl ='${sourceleadUrl}';
					  $.ajax({
					    	type: "GET",
					        url: sourceleadUrl+"/getChecksListByEmployeeId?empId="+empId+"&orgLocationId="+orgLocationId,
					        success: function(data) {
							 var result = "";				        	
							result = result + '<table id="empchecksListTable" class="display" cellspacing="0" width="100%" >';
							result = result + '<thead>';
							result = result + '<tr>';
							result = result + '<th>Emp Id</th>';
							result = result + '<th>Emp Name</th>';
							result = result + '<th>Date</th>';
							result = result + '<th>Type of Expense</th>';
							result = result + '<th>Bank</th>';
							result = result + '<th>Month</th>';
							result = result + '<th>Legal Fees</th>';
							result = result + '<th>Advance</th>';
							result = result + '<th>General Expenses</th>';
							result = result + '<th>Vendor Payments</th>';
							result = result + '<th>Electronic Withdrawals</th>';
							result = result + '<th>Payroll Chks</th>';
							result = result + '<th>Amount</th>';
							result = result + '<th>Check No</th>';
							result = result + '</tr>';
							result = result + '</thead>	';
							result = result + '<tbody>';
				        	if(data !=null && data !='' && data !='undefined'){
				        		for (var i=0; i<data.length; i++)
								{
					        		 var opt = data[i];
					        		    result = result + ' <tr>';
					        		    
					        		    if(opt.empId !=null && opt.empId !='' && opt.empId !='undefined'){
					        		    	result = result + '<td><div class="tblminwidth-75">'+opt.empId+'</div></td>';
					        		    }else{
					        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
					        		    }
					        		    
					        		    if(opt.empName !=null && opt.empName !='' && opt.empName !='undefined'){
					        		    	result = result + '<td><div><a>'+opt.empName+'</a></div></td>';
					        		    }else{
					        		    	result = result + '<td><div>---</div></td>';
					        		    }
					        		    
					        		    if(opt.dateStr !=null && opt.dateStr !='' && opt.dateStr !='undefined'){
											result = result + ' <td><div class="tblminwidth-75">'+opt.dateStr+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
					        		    
					        		    if(opt.typeOfExpense !=null && opt.typeOfExpense !='' && opt.typeOfExpense !='undefined'){
											result = result + ' <td><div class="tblminwidth-75">'+opt.typeOfExpense+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										if(opt.bankName !=null && opt.bankName !='' && opt.bankName !='undefined'){
											result = result + ' <td><div class="tblminwidth-75">'+opt.bankName+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										if(opt.month !=null && opt.month !='' && opt.month !='undefined'){
											result = result + ' <td><div class="tblminwidth-75">'+opt.month+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										if(opt.legalFees !=null && opt.legalFees !='' && opt.legalFees !='undefined'){
											result = result + '<td><div class="tblminwidth-75">'+opt.legalFees+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										if(opt.advanceStr !=null && opt.advanceStr !='' && opt.advanceStr !='undefined'){
											result = result + '<td><div class="tblminwidth-75">$ '+opt.advanceStr+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
										}
										if(opt.generalExpensesStr !=null && opt.generalExpensesStr !='' && opt.generalExpensesStr !='undefined'){
											result = result + '<td><div class="tblminwidth-75">'+opt.generalExpensesStr+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">0.00</div></td>';
										}
										
										if(opt.vendorPaymentsStr !=null && opt.vendorPaymentsStr !='' && opt.vendorPaymentsStr !='undefined'){
											result = result + '<td><div class="tblminwidth-75">$ '+opt.vendorPaymentsStr+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
										}
										
										if(opt.electronicWithdrawals !=null && opt.electronicWithdrawals !='' && opt.electronicWithdrawals !='undefined'){
											result = result + '<td><div class="tblminwidth-75">'+opt.electronicWithdrawals+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										if(opt.payrollChecksStr !=null && opt.payrollChecksStr !='' && opt.payrollChecksStr !='undefined'){
											result = result + '<td><div class="tblminwidth-75">$ '+opt.payrollChecksStr+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
										}
										
										if(opt.amountStr !=null && opt.amountStr !='' && opt.amountStr !='undefined'){
											result = result + '<td><div class="tblminwidth-75">$ '+opt.amountStr+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">$ 0.00</div></td>';
										}
										
										if(opt.checkNo !=null && opt.checkNo !='' && opt.checkNo !='undefined'){
											result = result + '<td><div class="tblminwidth-75">'+opt.checkNo+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										result = result + ' </tr>';
								} 
				        	}
							
							result = result + '  </tbody>';
							result = result + ' </table>';
							$("#tab-2").empty();
							$("#tab-3").empty();
							$("#tab-3").append(result);
							$("#loading").removeAttr("style");
							empChecksListPagination(); 
					        	
					        },
					        
					    });
  }
  </script> -->  
  
  
    
    <!-- <script>
function empInvoiceDetails(){ 
    $('#incometable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#incometable").wrap("<div class='overflow_Auto  width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script> -->

<!-- <script>
function empChecksListPagination(){ 
    $('#empchecksListTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#empchecksListTable").wrap("<div class='overflow_Auto  width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script> -->





 <script type="text/javascript">
function getWagesTaxSummaryByEmpId(){
		$("#customize_btn").attr("onClick","getWagesTaxSummaryByEmpId();");
	    var empId = $("#empId").val();
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getWagesTaxSummaryByEmpId?empId="+empId+"&orgLocationId="+orgLocationId+"&searchValue="+searchValue;
	    prepareWagesAndTaxList(url);
}
</script> 

<script>
function prepareWagesAndTaxList(url){
	$("#tab-2").empty();
	$("#tab-3").empty();
	$("#tab-4").empty();
	$("#tab-5").empty();
	$("#tab-6").empty();
	$("#tab-1").empty();
 	 var result = "";
 	 
 		result = result +" <div class='reportHead'>";
		result = result + " <ul class='pull-right'>";
		result = result + " <li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick='dataTableSearch()'></span></li>";
		result = result + "</ul>";
		result = result + "</div>";  		
 		
 	  	result = result +"<table id='wageTaxPagination' class='display' cellspacing='0' width='100%' >"; 
      	result = result +"<thead>"; 
      	result = result + '<tr>';
		result = result + '<th>From Date</th>';
		result = result + '<th>To Date</th>';
		result = result + '<th>Employee Id</th>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>SSN</th>';
		result = result + '<th>Check Date</th>';
		result = result + '<th>Check No</th>';
		result = result + '<th>Gross</th>';
		result = result + '<th>FWT</th>';
		result = result + '<th>SSEC</th>';
		result = result + '<th>MED</th>';
		result = result + '<th>SWT Locals</th>';
		result = result + '<th>SDI/SUI</th>';
		result = result + '<th>local</th>';
		result = result + '<th>DEDS</th>';
		result = result + '<th>NET</th>';
		result = result + '<th>Batch Number</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
      result = result +"</thead>";
      result = result +"<tbody>"; 
      result = result +" </tbody>";
      result = result +"</table>";
      $("#tab-1").show();
      $("#tab-1").append(result);
      wagesTaxpagination(url); 
}
</script>


<script>
function wagesTaxpagination(url){
	var colIndex = 0;
    $('#wageTaxPagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "fromDate" },
            { "data": "toDate"},
            { "data": "employeeId"},
            { "data": "employeeName" },
            { "data": "ssnNumber"},
            { "data": "checkDate" },
            { "data": "checkNo" },
            { "data": "gross"},
            { "data": "fwt"},
            { "data": "ssec"},
            { "data": "med"},
            { "data": "swt"},
            { "data": "sdiSui"},
            { "data": "local"},
            { "data": "deds"},
            { "data": "net"},
            { "data": "batchNumber" },
            { "data": "action" }
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "fromDate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "toDate",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "employeeId",
            "render": function ( data, type, opt, meta ) {
            	return  '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
            }
          }, {
              "targets": colIndex++,
              "data": "employeeName",
              "render": function ( data, type, opt, meta ) {
            	  return '<td><div class="tbldata-dotdot-vsm" title="'+opt.employeeName+'">'+opt.employeeName+'</div></td>';
              }
            }, {
                "targets": colIndex++,
                "data": "ssnNumber",
                "sortable": false,
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.ssnNumber !=null && opt.ssnNumber !='' && opt.ssnNumber !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-sm"">'+opt.ssnNumber+'</div></td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "checkDate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.checkDateStr !=null && opt.checkDateStr !='' && opt.checkDateStr !='undefined'){
						result = result + '<td><div class="tblminwidth-75">'+opt.checkDateStr+'</div></td>';
					}else{
						result = result + ' <td><div>--</div></td>';
					}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "checkNo",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.checkNo != null && opt.checkNo !='' && opt.checkNo != 'undefined'){
	    	  			result = result + ' <td><div class="tblminwidth-75">'+opt.checkNo+'</div></td>';
	    	  		}else{
	    	  			result = result + ' <td><div class="tblminwidth-75">--</div></td>';
	    	  		}
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "gross",
                  "render": function ( data, type, opt, meta ) {
                	  return '<td><div class="tblminwidth-75">'+opt.gross.toFixed(2)+'</div></td>';
                  }
                },{
                    "targets": colIndex++,
                    "data": "fwt",
                    "render": function ( data, type, opt, meta ) {
                    	return '<td><div class="tblminwidth-75">'+opt.fwt.toFixed(2)+'</div></td>';
                    }
                  },{
                      "targets": colIndex++,
                      "data": "ssec",
                      "render": function ( data, type, opt, meta ) {
                    	  return ' <td><div class="tblminwidth-75">'+opt.ssec.toFixed(2)+'</div></td>';
                      }
                    },{
                        "targets": colIndex++,
                        "data": "med",
                        "render": function ( data, type, opt, meta ) {
                        	return '<td><div class="tblminwidth-75">'+opt.med.toFixed(2)+'</div></td>';
                        }
                      },{
                          "targets": colIndex++,
                          "data": "swt",
                          "render": function ( data, type, opt, meta ) {
                        	 return' <td><div class="tblminwidth-75">'+opt.swt.toFixed(2)+'</div></td>';
                          }
                        },{
                            "targets": colIndex++,
                            "data": "sdiSui",
                            "render": function ( data, type, opt, meta ) {
                            	return '<td><div class="tblminwidth-75">'+opt.sdiSui.toFixed(2)+'</div></td>';
                            }
                          },{
                              "targets": colIndex++,
                              "data": "local",
                              "render": function ( data, type, opt, meta ) {
                            	  return '<td><div class="tblminwidth-75">'+opt.local.toFixed(2)+'</div></td>';
                              }
                            },{
                                "targets": colIndex++,
                                "data": "deds",
                                "render": function ( data, type, opt, meta ) {
                                	var result = "";
                                	result = result + '<td><div class="deeds tblminwidth-125">'+opt.deds.toFixed(2)+'</div>';
                                	result = result + '<div class="edit-input tblminwidth-125" style="display:none;"><input type="text" class="form-control input-sm tbldata-dotdot-100 pull-left" value="" id=""><small class="fa fa-floppy-o ml10 pull-right mt5"  onClick="saveEdit(this)"></small></div></td>';
                                    return result;
                                }
                              },{
                                  "targets": colIndex++,
                                  "data": "net",
                                  "render": function ( data, type, opt, meta ) {
                                	 return '<td><div class="tblminwidth-75">'+opt.net.toFixed(2)+'</div></td>';
                                  }
                                }, {
                  "targets": colIndex++,
                  "data": "batchNumber",
                  "sortable": false,
                  "className": "tblminwidth-75",
                  "render": function ( data, type, opt, meta ) {
                  		var result = "";
                  		if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
								result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
							}else{
								result = result + ' <td><div>--</div></td>';
							}
      	              return result;
                  }
                } ,
				{
					"targets" : colIndex++,
					"data" : "action",
					"sortable": false,
					"render" : function(data, type, opt, meta) {
						var result = "";
						result = result + '<td><div><i class="glyphicon glyphicon-trash" onclick="deletePayrollEntity(\''+opt.uuid+'\',\'WageTaxSummary\')"></i></div></td>';
						return result;
					}
				}],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#wageTaxPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getWagesTaxSummaryByEmpId();
    	   }
    	});
}
function dataTableSearch(){
	getWagesTaxSummaryByEmpId();
}
</script>

<!-- <script>
function getWagesTaxSummaryByEmpId(){
	 var empId = $("#empId").val();
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			  var sourceleadUrl ='${sourceleadUrl}';
					  $.ajax({
					    	type: "GET",
					    	url: sourceleadUrl+"/getWagesTaxSummaryByEmpId?empId="+empId+"&orgLocationId="+orgLocationId,
					        success: function(data) {
							var result = "";				        	
							result = result + '<table id="WagesAndTaxEmpTable" class="display" cellspacing="0" width="100%" >';
							result = result + '<thead>';
							result = result + '<tr>';
							result = result + '<th>From Date</th>';
							result = result + '<th>To Date</th>';
							result = result + '<th>Employee Id</th>';
							result = result + '<th>Employee Name</th>';
							result = result + '<th>SSN</th>';
							result = result + '<th>Check Date</th>';
							result = result + '<th>Check No</th>';
							result = result + '<th>Gross</th>';
							result = result + '<th>FWT</th>';
							result = result + '<th>SSEC</th>';
							result = result + '<th>MED</th>';
							result = result + '<th>SWT Locals</th>';
							result = result + '<th>SDI/SUI</th>';
							result = result + '<th>local</th>';
							result = result + '<th>DEDS</th>';
							result = result + '<th>NET</th>';
							result = result + '<th>Batch Number</th>';
							result = result + '</tr>';
							result = result + '</thead>	';
							result = result + '<tbody>';
							
							if(data !=null && data !=''){
								 for (var i=0; i<data.length; i++)
									{
						        		 var opt = data[i];
						        		    result = result + ' <tr>';
						        		    if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
												result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
											}else{
												result = result + ' <td><div>--</div></td>';
											}
						        		    
						        		    if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
												result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
											}else{
												result = result + ' <td><div>--</div></td>';
											}
											result = result + '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
											result = result + '<td><div class="tbldata-dotdot-vsm">'+opt.employeeName+'</div></td>';
											if(opt.ssnNumber !=null && opt.ssnNumber !='' && opt.ssnNumber !='undefined'){
												result = result + ' <td><div class="tbldata-dotdot-sm"">'+opt.ssnNumber+'</div></td>';
											}else{
												result = result + ' <td><div>--</div></td>';
											}
											if(opt.checkDateStr !=null && opt.checkDateStr !='' && opt.checkDateStr !='undefined'){
												result = result + '<td><div class="tblminwidth-75">'+opt.checkDateStr+'</div></td>';
											}else{
												result = result + ' <td><div>--</div></td>';
											}
											result = result + ' <td><div class="tblminwidth-75">'+opt.checkNo+'</div></td>';
											result = result + '<td><div class="tblminwidth-75">'+opt.gross+'</div></td>';
											result = result + '<td><div class="tblminwidth-75">'+opt.fwt+'</div></td>';
											result = result + ' <td><div class="tblminwidth-75">'+opt.ssec+'</div></td>';
											result = result + '<td><div class="tblminwidth-75">'+opt.med+'</div></td>';
											result = result + ' <td><div class="tblminwidth-75">'+opt.swt+'</div></td>';
											result = result + '<td><div class="tblminwidth-75">'+opt.sdiSui+'</div></td>';
											result = result + '<td><div class="tblminwidth-75">'+opt.local+'</div></td>';
											result = result + '<td><div class="deeds tblminwidth-125">'+opt.deds+'</div>';
											result = result + '<div class="edit-input tblminwidth-125" style="display:none;"><input type="text" class="form-control input-sm tbldata-dotdot-100 pull-left" value="" id=""><small class="fa fa-floppy-o ml10 pull-right mt5"  onClick="saveEdit(this)"></small></div></td>';
											
											result = result + '<td><div class="tblminwidth-75">'+opt.net+'</div></td>';
											if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
												result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
											}else{
												result = result + ' <td><div>--</div></td>';
											}
											result = result + ' </tr>';
									}
							}
				        	
							result = result + '  </tbody>';
							result = result + ' </table>';
							$("#tab-2").empty();
							$("#tab-3").empty();
							$("#tab-4").empty();
							$("#tab-5").empty();
							$("#tab-6").empty();
							$("#tab-1").empty();
							$("#tab-1").append(result);
							$("#loading").removeAttr("style");
							wagesAndTaxEmpTablePagination();
					        	
					        },
					        
					    });
}
</script>

<script>
function wagesAndTaxEmpTablePagination(){ 
    $('#WagesAndTaxEmpTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    
    });
    $("#WagesAndTaxEmpTable").wrap("<div class='overflow_Auto width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script> 
 -->
 
     <script type="text/javascript">
function getAccontDeductions(){
	$("#customize_btn").attr("onClick","getAccontDeductions();");
	    var empId = $("#empId").val();
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getAccontDeductionsByEmpId?orgLocationId="+orgLocationId+"&employeeId="+empId+"&searchValue="+searchValue;
		/* url += "&"+$($('#payroll_list_form')[0]).serialize(); */
	    prepareAccountDeductions(url);
}
</script> 

<script>
function prepareAccountDeductions(url){
	$("#tab-5").empty();
	$("#tab-6").empty();
	$("#tab-1").empty();
	$("#tab-2").empty();
	$("#tab-3").empty();
	$("#tab-4").empty();
 	 var result = "";
 	 
 		result = result +" <div class='reportHead'>";
		result = result + " <ul class='pull-right'>";
		result = result + " <li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick='dataTableSearch()'></span></li>";
		result = result + "</ul>";
		result = result + "</div>";  		
 		
 	  	result = result +"<table id='accontDeductionsEmpTable' class='display' cellspacing='0' width='100%' >"; 
      	result = result +"<thead>"; 
      	result = result + '<tr>';
      	result = result + '<th>Employee Id</th>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>From Date</th>';
		result = result + '<th>To Date</th>';
		result = result + '<th>Ded. Amount</th>';
		result = result + '<th>Batch Number</th>';
		result = result + '<th>Request Sub.Date</th>';
		result = result + '<th>Request Approved Date</th>';
		result = result + '<th>Repaid Status</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#tab-4").append(result);
  	   /* $("#loading").removeAttr("style"); */
       accountDeductionsPagination(url); 
}
</script>


<script>
function accountDeductionsPagination(url){
	var colIndex = 0;
    $('#accontDeductionsEmpTable').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "employeeId" },
            { "data": "employeeName"},
            { "data": "fromDateStr"},
            { "data": "toDateStr" },
            { "data": "dedAmount"},
            { "data": "batchNumber" },
            { "data": "requestSubDate" },
            { "data": "requestApprovedDate"},
            { "data": "repaidStatus"}
            
        ],
        "columnDefs": [  {
	    	  	"targets": colIndex++,
	    	  	"data": "empId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
		    	  		if(opt.employeeId !=null && opt.employeeId !='' && opt.employeeId !='undefined'){
	        		    	result = result + '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
	        		    }else{
	        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
	        		    }
		             return result;
	    	  	}
        }, {
	    	  "targets": colIndex++,
              "data": "employeeName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	            	  if(opt.employeeName !=null && opt.employeeName !='' && opt.employeeName !='undefined'){
	      		    	result = result + '<td><div class="tblminwidth-75" onclick="getEmpAccountDeductionDetails('+opt.id+',\''+opt.employeeName+'\');"><a>'+opt.employeeName+'</a></div></td>';
	      		    }else{
	      		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
	      		    }
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "fromDateStr",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
	            	if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
	            return result;
            }
          }, {
              "targets": colIndex++,
              "data": "toDateStr",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	            	  if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "dedAmount",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
	                		 if(opt.dedAmount !=null && opt.dedAmount !='' && opt.dedAmount !='undefined'){
				        		    //result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'">'+opt.dedAmount+'<small class="fa fa-pencil ml10 pull-right" onClick="showEdit('+opt.id+','+opt.dedAmount+')"></small></div>';
									result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><small class="fa fa-dollar expenses_symbol text-muted"></small>'+opt.dedAmount+'</div>';
				        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
									result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"></div></td>';
			        		    }
			        		    else{
			        		    	result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><small class="fa fa-dollar expenses_symbol text-muted"></small>0</div>';
				        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
									result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"></div></td>';
								}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "batchNumber",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
		    	  		if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "requestSubDate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
		    	  		if(opt.requestSubDate !=null && opt.requestSubDate !='' && opt.requestSubDate !='undefined'){
							result = result + '<td><div class="tblminwidth-75">'+opt.requestSubDate+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "requestApprovedDate",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
	                	  if(opt.requestApprovedDate !=null && opt.requestApprovedDate !='' && opt.requestApprovedDate !='undefined'){
								result = result + '<td><div class="tblminwidth-75">'+opt.requestApprovedDate+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
                	  return result;
                  }
                },{
                    "targets": colIndex++,
                    "data": "repaidStatus",
                    "render": function ( data, type, opt, meta ) {
                    	var result = "";
	                    	if(opt.repaidStatus !=null && opt.repaidStatus !='' && opt.repaidStatus !='undefined'){
								result = result + '<td><div class="tblminwidth-75">'+opt.repaidStatus+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
                    	return result;
                    }
                  }], 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#accontDeductionsEmpTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getAccontDeductions();
    	   }
    	});
}
function dataTableSearch(){
	getAccontDeductions();
}
</script>
 
 
 
<!-- <script>
function getAccontDeductions(){
	 var empId = $("#empId").val();
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				    	url: sourceleadUrl+"/getAccontDeductionsByEmpId?empId="+empId+"&orgLocationId="+orgLocationId,
				        success: function(data) {
						var result = "";				        	
						result = result + '<table id="accontDeductionsEmpTable" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>Employee Id</th>';
						result = result + '<th>Employee Name</th>';
						result = result + '<th>From Date</th>';
						result = result + '<th>To Date</th>';
						result = result + '<th>Ded. Amount</th>';
						result = result + '<th>Batch Number</th>';
						result = result + '<th>Request Sub.Date</th>';
						result = result + '<th>Request Approved Date</th>';
						result = result + '<th>Repaid Status</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
						if(data != null && data !=''){
							 for (var i=0; i<data.length; i++)
								{
					        		 var opt = data[i];
					        		    result = result + ' <tr>';
					        		    
					        		    if(opt.employeeId !=null && opt.employeeId !='' && opt.employeeId !='undefined'){
					        		    	result = result + '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
					        		    }else{
					        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
					        		    }
					        		    
					        		    if(opt.employeeName !=null && opt.employeeName !='' && opt.employeeName !='undefined'){
					        		    	result = result + '<td><div class="tblminwidth-75" onclick="getEmpAccountDeductionDetails('+opt.id+',\''+opt.employeeName+'\');"><a>'+opt.employeeName+'</a></div></td>';
					        		    }else{
					        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
					        		    }
					        		    
					        		    if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
											result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
					        		    
					        		    if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
											result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
					        		    if(opt.dedAmount !=null && opt.dedAmount !='' && opt.dedAmount !='undefined'){
						        		    //result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'">'+opt.dedAmount+'<small class="fa fa-pencil ml10 pull-right" onClick="showEdit('+opt.id+','+opt.dedAmount+')"></small></div>';
											result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><small class="fa fa-dollar expenses_symbol text-muted"></small>'+opt.dedAmount+'</div>';
						        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
											result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"></div></td>';
					        		    }
					        		    else{
					        		    	result = result + '<td><div class="deeds tblminwidth-125" id="preDedDiv'+opt.id+'"><small class="fa fa-dollar expenses_symbol text-muted"></small>0</div>';
						        		    result = result + '<div class="tblminwidth-125"><p id="preDedError'+opt.id+'" class="re_Error"></p></div>';
											result = result + '<div class="edit-input tblminwidth-125" id="editDedDiv'+opt.id+'" style="display:none;"><input type="text" id ="preDed'+opt.id+'" class="form-control input-sm tbldata-dotdot-100 pull-left"></div></td>';
										}
										
										if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
											result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										if(opt.requestSubDate !=null && opt.requestSubDate !='' && opt.requestSubDate !='undefined'){
											result = result + '<td><div class="tblminwidth-75">'+opt.requestSubDate+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										if(opt.requestApprovedDate !=null && opt.requestApprovedDate !='' && opt.requestApprovedDate !='undefined'){
											result = result + '<td><div class="tblminwidth-75">'+opt.requestApprovedDate+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										if(opt.repaidStatus !=null && opt.repaidStatus !='' && opt.repaidStatus !='undefined'){
											result = result + '<td><div class="tblminwidth-75">'+opt.repaidStatus+'</div></td>';
										}else{
											result = result + ' <td><div class="tblminwidth-75">--</div></td>';
										}
										
										result = result + ' </tr>';
								} 	
						}
			        	
						result = result + '  </tbody>';
						result = result + ' </table>';
						
						$("#tab-1").empty();
						$("#tab-2").empty();
						$("#tab-3").empty();
						$("#tab-5").empty();
						$("#tab-6").empty();
						$("#tab-4").empty();
						$("#tab-4").append(result);
						$("#loading").removeAttr("style");
						accountDeductionsEmpTablePagination();
				        	
				        },
				        
				    });
}
</script> -->


<!-- <script>
function accountDeductionsEmpTablePagination(){ 
    $('#accontDeductionsEmpTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#accontDeductionsEmpTable").wrap("<div class='overflow_Auto width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script> -->

<script>
function getEmpAccountDeductionDetails(accountDedId,empName){
	$(".sideMask").fadeIn("slow");
	$(".AccountDedu").fadeIn("slow");
	  var sourceleadUrl ='${sourceleadUrl}';
			  $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getDeductionsDetailsbyId?${_csrf.parameterName}=${_csrf.token}&deductionId="+accountDedId,
			        success: function(data) {
					var result = "";				        	
					result = result + '<table id="employeeDeductionsListTable" class="display" cellspacing="0" width="100%" >';
					result = result + '<thead>';
					result = result + '<tr>';
					result = result + '<th>Pay Cycle Name</th>';
					result = result + '<th>Ded. Amount per Cycle</th>';
					result = result + '<th>Paid Status</th>';
					result = result + '</tr>';
					result = result + '</thead>	';
					result = result + '<tbody>';
					var json = data.data;
		        	 for (var i=0; i<json.length; i++)
					{
		        		 var opt = json[i];
		        		    result = result + ' <tr>';
		        		    
		        		    if(opt.payCycleName !=null && opt.payCycleName !='' && opt.payCycleName !='undefined'){
		        		    	result = result + '<td><div>'+opt.payCycleName+'</div></td>';
		        		    }else{
		        		    	result = result + '<td><div>---</div></td>';
		        		    }
		        		    
		        		    if(opt.dedAmountPerCycle !=null && opt.dedAmountPerCycle !='' && opt.dedAmountPerCycle !='undefined'){
		        		    	result = result + '<td><div onclick="getEmpAccountDeductionDetails('+opt.id+');">'+opt.dedAmountPerCycle+'</div></td>';
		        		    }else{
		        		    	result = result + '<td><div>---</div></td>';
		        		    }
		        		    
		        		    if(opt.paidStatus !=null && opt.paidStatus !='' && opt.paidStatus !='undefined'){
								result = result + ' <td><div>'+opt.paidStatus+'</div></td>';
							}else{
								result = result + ' <td><div>--</div></td>';
							}
		        		    
							result = result + ' </tr>';
					} 
					result = result + '  </tbody>';
					result = result + ' </table>';
					$("#accountdeDuctiondetails").empty();
					$("#empName").html(empName);
					$("#accountdeDuctiondetails").append(result);
					employeeDeductionsListTablePagination();
			        	
			        },
			        
			    });
			  
}
</script>

<script>
function employeeDeductionsListTablePagination(){ 
    $('#employeeDeductionsListTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#employeeDeductionsListTable").wrap("<div class='overflow_Auto width100percnt'></div>");
}
</script>


<script>
function closeModalExpenses(){
	$(".sideMask").fadeOut("slow");
	$("#one").fadeOut("slow");	
}
</script>

 <script type="text/javascript">
function getHRDeductions(){
	$("#customize_btn").attr("onClick","getHRDeductions();");
	    var empId = $("#empId").val();
		var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getHRDeductionsByEmployeeId?orgLocationId="+orgLocationId+"&employeeId="+empId+"&searchValue="+searchValue;
		/* url += "&"+$($('#payroll_list_form')[0]).serialize(); */
	    prepareHRDeductions(url);
}
</script> 

<script>
function prepareHRDeductions(url){
	$("#tab-6").empty();
	$("#tab-1").empty();
	$("#tab-2").empty();
	$("#tab-3").empty();
	$("#tab-4").empty();
	$("#tab-5").empty();
 	 var result = "";
 	 
 		result = result +" <div class='reportHead'>";
		result = result + " <ul class='pull-right'>";
		result = result + " <li><input type='text' placeholder='Search' class='repSearchBar' id='dataTableSearch'/><span class='repSearchIcon' onClick='dataTableSearch()'></span></li>";
		result = result + "</ul>";
		result = result + "</div>";  		
 	  	result = result +"<table id='hrDeductionsEmpTable' class='display' cellspacing='0' width='100%' >"; 
      	result = result +"<thead>"; 
      	result = result + '<tr>';
      	result = result + '<th>Employee Id</th>';
		result = result + '<th>Employee Name</th>';
		result = result + '<th>From Date</th>';
		result = result + '<th>To Date</th>';
		result = result + '<th>Ded. Amount</th>';
		result = result + '<th>Batch Number</th>';
		result = result + '<th>Request Sub.Date</th>';
		result = result + '<th>Request Approved Date</th>';
		result = result + '<th>Repaid Status</th>';
		result = result + '</tr>';
        result = result +"</thead>";
        result = result +"<tbody>"; 
        result = result +" </tbody>";
        result = result +"</table>";
        $("#tab-5").append(result);
  	   /* $("#loading").removeAttr("style"); */
       hRDeductionsPagination(url); 
}
</script>


<script>
function hRDeductionsPagination(url){
	var colIndex = 0;
    $('#hrDeductionsEmpTable').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "employeeId" },
            { "data": "employeeName"},
            { "data": "fromDateStr"},
            { "data": "toDateStr" },
            { "data": "dedAmount"},
            { "data": "batchNumber" },
            { "data": "requestSubDate" },
            { "data": "requestApprovedDate"},
            { "data": "repaidStatus"}
            
        ],
        "columnDefs": [  {
	    	  	"targets": colIndex++,
	    	  	"data": "empId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
		    	  		if(opt.employeeId !=null && opt.employeeId !='' && opt.employeeId !='undefined'){
	        		    	result = result + '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
	        		    }else{
	        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
	        		    }
		             return result;
	    	  	}
        }, {
	    	  "targets": colIndex++,
              "data": "employeeName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	            	  if(opt.employeeName !=null && opt.employeeName !='' && opt.employeeName !='undefined'){
	      		    	result = result + '<td><div onclick="getHrDeductionDetails('+opt.id+',\''+opt.employeeName+'\');"><a>'+opt.employeeName+'</a></div></td>';
	      		    }else{
	      		    	result = result + '<td><div>---</div></td>';
	      		    }
	              return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "fromDateStr",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
	            	if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
						result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-75">--</div></td>';
					}
	            return result;
            }
          }, {
              "targets": colIndex++,
              "data": "toDateStr",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	            	  if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
            	  return result;
              }
            }, {
                "targets": colIndex++,
                "data": "dedAmount",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
	                		if(opt.dedAmount !=null && opt.dedAmount !='' && opt.dedAmount !='undefined'){
								result = result + ' <td><div class="tblminwidth-75">'+opt.dedAmount+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
    	              return result;
                }
          },{
	    	  	"targets": colIndex++,
	    	  	"data": "batchNumber",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
		    	  		if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
							result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
  	              return result;
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "requestSubDate",
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
		    	  		if(opt.requestSubDate !=null && opt.requestSubDate !='' && opt.requestSubDate !='undefined'){
							result = result + '<td><div class="tblminwidth-75">'+opt.requestSubDate+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-75">--</div></td>';
						}
	    	  		return result;
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "requestApprovedDate",
                  "render": function ( data, type, opt, meta ) {
                	  var result = "";
	                	  if(opt.requestApprovedDate !=null && opt.requestApprovedDate !='' && opt.requestApprovedDate !='undefined'){
								result = result + '<td><div class="tblminwidth-75">'+opt.requestApprovedDate+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
                	  return result;
                  }
                },{
                    "targets": colIndex++,
                    "data": "repaidStatus",
                    "render": function ( data, type, opt, meta ) {
                    	var result = "";
	                    	if(opt.repaidStatus !=null && opt.repaidStatus !='' && opt.repaidStatus !='undefined'){
								result = result + '<td><div class="tblminwidth-75">'+opt.repaidStatus+'</div></td>';
							}else{
								result = result + ' <td><div class="tblminwidth-75">--</div></td>';
							}
                    	return result;
                    }
                  }], 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#hrDeductionsEmpTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getHRDeductions();
    	   }
    	});
}
function dataTableSearch(){
	getHRDeductions();
}
</script>




<!-- <script>
function getHRDeductions(){
	 var empId = $("#empId").val();
	 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		  var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				    	url: sourceleadUrl+"/getHRDeductionsByEmployeeId?empId="+empId+"&orgLocationId="+orgLocationId,
				        success: function(data) {
						var result = "";				        	
						result = result + '<table id="hrDeductionsEmpTable" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>Employee Id</th>';
						result = result + '<th>Employee Name</th>';
						result = result + '<th>From Date</th>';
						result = result + '<th>To Date</th>';
						result = result + '<th>Ded. Amount</th>';
						result = result + '<th>Batch Number</th>';
						result = result + '<th>Request Sub.Date</th>';
						result = result + '<th>Request Approved Date</th>';
						result = result + '<th>Repaid Status</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
			        	if(data !=null && data !=''){
			        		for (var i=0; i<data.length; i++)
							{
				        		 var opt = data[i];
				        		    result = result + ' <tr>';
				        		    
				        		    if(opt.employeeId !=null && opt.employeeId !='' && opt.employeeId !='undefined'){
				        		    	result = result + '<td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
				        		    }else{
				        		    	result = result + '<td><div class="tblminwidth-75">---</div></td>';
				        		    }
				        		    
				        		    if(opt.employeeName !=null && opt.employeeName !='' && opt.employeeName !='undefined'){
				        		    	result = result + '<td><div onclick="getHrDeductionDetails('+opt.id+',\''+opt.employeeName+'\');"><a>'+opt.employeeName+'</a></div></td>';
				        		    }else{
				        		    	result = result + '<td><div>---</div></td>';
				        		    }
				        		    
				        		    if(opt.fromDateStr !=null && opt.fromDateStr !='' && opt.fromDateStr !='undefined'){
										result = result + ' <td><div class="tblminwidth-75">'+opt.fromDateStr+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
				        		    
				        		    if(opt.toDateStr !=null && opt.toDateStr !='' && opt.toDateStr !='undefined'){
										result = result + ' <td><div class="tblminwidth-75">'+opt.toDateStr+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
									
									if(opt.dedAmount !=null && opt.dedAmount !='' && opt.dedAmount !='undefined'){
										result = result + ' <td><div class="tblminwidth-75">'+opt.dedAmount+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
									
									if(opt.batchNumber !=null && opt.batchNumber !='' && opt.batchNumber !='undefined'){
										result = result + ' <td><div class="tblminwidth-75">'+opt.batchNumber+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
									
									if(opt.requestSubDate !=null && opt.requestSubDate !='' && opt.requestSubDate !='undefined'){
										result = result + '<td><div class="tblminwidth-75">'+opt.requestSubDate+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
									
									if(opt.requestApprovedDate !=null && opt.requestApprovedDate !='' && opt.requestApprovedDate !='undefined'){
										result = result + '<td><div class="tblminwidth-75">'+opt.requestApprovedDate+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
									
									if(opt.repaidStatus !=null && opt.repaidStatus !='' && opt.repaidStatus !='undefined'){
										result = result + '<td><div class="tblminwidth-75">'+opt.repaidStatus+'</div></td>';
									}else{
										result = result + ' <td><div class="tblminwidth-75">--</div></td>';
									}
									
									result = result + ' </tr>';
							} 	
			        	}
						
						result = result + '  </tbody>';
						result = result + ' </table>';
						$("#tab-1").empty();
						$("#tab-2").empty();
						$("#tab-3").empty();
						$("#tab-4").empty();
						$("#tab-6").empty();
						$("#tab-5").empty();
						$("#tab-5").append(result);
						$("#loading").removeAttr("style");
						hrDeductionsEmpTablePagination();
				        	
				        },
				        
				    });
}
</script> -->

<!-- <script>
function hrDeductionsEmpTablePagination(){ 
    $('#hrDeductionsEmpTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#hrDeductionsEmpTable").wrap("<div class='overflow_Auto width100percnt'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script> -->



<script>
function getHrDeductionDetails(hrDedId,empName){
	$(".sideMask").fadeIn("slow");
	$(".hrDedu").fadeIn("slow");
	  var sourceleadUrl ='${sourceleadUrl}';
			  $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getEmpHrDeductionsDetailsbyId?${_csrf.parameterName}=${_csrf.token}&hrDeductionId="+hrDedId,
			        success: function(json) {
					var result = "";				        	
					result = result + '<table id="employeeHrDeductionsListTable" class="display" cellspacing="0" width="100%" >';
					result = result + '<thead>';
					result = result + '<tr>';
					result = result + '<th>Pay Cycle Name</th>';
					result = result + '<th>Ded. Amount per Cycle</th>';
					result = result + '<th>Paid Status</th>';
					result = result + '</tr>';
					result = result + '</thead>	';
					result = result + '<tbody>';
// 					var json = JSON.parse(data);
					var data = json.data;
		        	 for (var i=0; i<data.length; i++)
					{
		        		 var opt = data[i];
		        		    result = result + ' <tr>';
		        		    
		        		    if(opt.payCycleName !=null && opt.payCycleName !='' && opt.payCycleName !='undefined'){
		        		    	result = result + '<td><div>'+opt.payCycleName+'</div></td>';
		        		    }else{
		        		    	result = result + '<td><div>---</div></td>';
		        		    }
		        		    
		        		    if(opt.dedAmountPerCycle !=null && opt.dedAmountPerCycle !='' && opt.dedAmountPerCycle !='undefined'){
		        		    	result = result + '<td><div onclick="getEmpAccountDeductionDetails('+opt.id+');">'+opt.dedAmountPerCycle+'</div></td>';
		        		    }else{
		        		    	result = result + '<td><div>---</div></td>';
		        		    }
		        		    
		        		    if(opt.paidStatus !=null && opt.paidStatus !='' && opt.paidStatus !='undefined'){
								result = result + ' <td><div>'+opt.paidStatus+'</div></td>';
							}else{
								result = result + ' <td><div>--</div></td>';
							}
		        		    
							result = result + ' </tr>';
					} 
					result = result + '  </tbody>';
					result = result + ' </table>';
					$("#hrDeductiondetails").empty();
					$("#hrEmpName").html(empName);
					$("#hrDeductiondetails").append(result);
					employeeHrDeductionsListTablePagination();
			        	
			        },
			        
			    });
			  
}
function closeModalExpenseshr(){
	$(".sideMask").fadeOut("slow");
	$("#two").fadeOut("slow");	
	
}
</script>


<script>
function employeeHrDeductionsListTablePagination(){ 
    $('#employeeHrDeductionsListTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#employeeHrDeductionsListTable").wrap("<div class='overflow_Auto width100percnt'></div>");
}
</script>

<script>
function finalBalance(){
	$("#customize_btn").attr("onClick","finalBalance();");
		var empId = $("#empId").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var sourceleadUrl ='${sourceleadUrl}';
		$.ajax({
		type: "GET",
		url: sourceleadUrl+"/getfinalBalanceByEmpId?empId="+empId+"&orgLocationId="+orgLocationId,
		success: function(data) {
		var result = "";
		result = result + ' <div class="clearfix col-md-10 col-md-offset-1">';
		result = result + ' <div class="payrollinvoice-generate">';
		result = result + ' <h4 class="clearfix text-center">Balance Sheet</h4>';
		result = result + ' <div class="payrollinvoice-header">';
		result = result + '<table style="width:100%" class="table-responsive table-bordered table-BLsheet mb">';
		result = result + '<tr>';
		result = result + '<th rowspan="2" class="bg-info">Income</th>';
		result = result + '<th class="bg-orange">Billed</th>';
		result = result + '<th class="bgGReen">Paid</th>';
		result = result + '<th class="bg-red">Due</th>';
		result = result + '</tr>';
		result = result + '<tr>';
		result = result + '<td class="bg-success">$ '+data.billed+'</td>';
		result = result + '<td class="bg-success">$ '+data.income+'</td>';
		result = result + '<td class="bg-success">$ '+data.due+'</td>';
		result = result + '</tr>';
		result = result + '</table>';
		result = result + ' </div>';
		
		
		
		result = result + '<div class="table-payroll">';
		result = result + '<table style="width:100%" class="table">';
		result = result + '<thead>';
		
		result = result + '<tr>';
		result = result + '<th class="timesheetHead">&nbsp;</th>';
// 		result = result + '<th class="timesheetHead" colspan="2" style="padding-left:70px; font-weight:700">Admin View</th>';
// 		result = result + '<th class="timesheetHead" colspan="2" style="padding-left:70px; font-weight:700">Employee View</th>';
		result = result + '</tr>';
		
		result = result + '<tr>';
		result = result + '<th>Name</th>';
		result = result + '<th class="text-center">Gross</th>';
		result = result + '<th class="text-center">Net</th>';
// 		result = result + '<th class="text-center">Gross</th>';
// 		result = result + '<th class="text-center">Net</th>';
		result = result + '</tr>';
		result = result + '	</thead>';
		result = result + '<tbody>';
		result = result + ' <tr>';
		result = result + ' <th colspan="5">Payroll</th>';
		result = result + ' </tr>';
		result = result + '<tr> ';
		result = result + ' <th><span class="ml">Direct Deposit</span></th>';
		result = result + ' <td>$ '+data.directDepositGross+'</td>';
		result = result + ' <td>$ '+data.directDepositNet+'</td>';
// 		result = result + ' <td>$ '+data.directDepositGross+'</td>';
// 		result = result + ' <td>$ '+data.directDepositNet+'</td>';
		result = result + '</tr>';
		result = result + '<tr>';
		result = result + ' <th><span class="ml">Processed with Check Option</span></th>';
		result = result + ' <td>$ '+data.processedWithCheckOptionGross+'</td>';
		result = result + ' <td>$ '+data.processedWithCheckOptionNet+'</td>';
// 		result = result + ' <td>$ '+data.processedWithCheckOptionGross+'</td>';
// 		result = result + ' <td>$ '+data.processedWithCheckOptionNet+'</td>';
		result = result + ' </tr>';
		result = result + ' <tr>';
		result = result + ' <th><span class="ml">Actual Checks Deposited</span></th>';
		result = result + ' <td>$ '+data.actualChecksDepositedNet+'</td>';
		result = result + ' <td>$ 0.00</td>';
// 		result = result + ' <td>$ '+data.actualChecksDepositedNet+'</td>';
// 		result = result + ' <td>$ 0.00</td>';
		result = result + '</tr>';
		result = result + '<tr>';
		result = result + ' <th>Manual Advance</th>';
		result = result + ' <td>$ '+data.manualAdvance+'</td>';
		result = result + ' <td>$ 0.00</td>';
// 		result = result + ' <td>$ '+data.manualAdvance+'</td>';
// 		result = result + ' <td>$ 0.00</td>';
		result = result + ' </tr> ';
		result = result + ' <tr>';
		result = result + ' <th>General Expenses</th>';
		result = result + ' <td>$ '+data.generalExpenses+'</td>';
		result = result + ' <td>$ 0.00</td>';
// 		result = result + ' <td>$ '+data.generalExpenses+'</td>';
// 		result = result + ' <td>$ 0.00</td>';
		result = result + ' </tr>';
		result = result + '<tr>';
		result = result + ' <th>Vendor Payment</th>';
		result = result + ' <td>$ '+data.vendorPayment+'</td> ';
		result = result + ' <td>$ 0.00</td>';
// 		result = result + ' <td>$ '+data.vendorPayment+'</td> ';
// 		result = result + ' <td>$ 0.00</td>';
		result = result + ' </tr>';
		result = result + ' <tr>';
		result = result + ' <th>Healthcare/Insurance</th>';
		result = result + ' <td>$ '+data.healthcareInsurance+'</td>';
		result = result + ' <td>$ 0.00</td>';
// 		result = result + ' <td>$ '+data.healthcareInsurance+'</td>';
// 		result = result + ' <td>$ 0.00</td>';
		result = result + '</tr>';
		result = result + ' <tr>';
		result = result + ' <th>Immigration</th>';
		result = result + ' <td>$ '+data.immigration+'</td>';
		result = result + ' <td>$ 0.00</td>';
// 		result = result + ' <td>$ '+data.immigration+'</td>';
// 		result = result + ' <td>$ 0.00</td>';
		result = result + '</tr>';
		result = result + ' <tr>';
		result = result + ' <th>Direct Deposit Advance</th>';
		result = result + ' <td>$ '+data.directDepositAdvanceNegetive+'</td>';
		result = result + ' <td>$ 0.00</td>';
// 		result = result + ' <td>$ '+data.directDepositAdvanceNegetive+'</td>';
// 		result = result + ' <td>$ 0.00</td>';
		result = result + '</tr>';
		result = result + ' <tr>';
		result = result + ' <th>Direct Deposit Deduction</th>';
		result = result + ' <td>$ '+data.directDepositDeductionPositive+'</td>';
		result = result + ' <td>$ 0.00</td>';
// 		result = result + ' <td>$ '+data.directDepositDeductionPositive+'</td>';
// 		result = result + ' <td>$ 0.00</td>';
		result = result + '</tr>';
		result = result + ' </tbody>';
		result = result + ' <tfoot>';
		result = result + '<tr>';
		result = result + ' <th>Balance</th>';
		result = result + ' <td colspan="2">$ '+data.balance+'</td>';
// 		result = result + ' <td colspan="2">$ '+data.empBalance+'</td>';
		result = result + '</tr>';
		result = result + ' </tfoot>';
		result = result + '</table>';
		result = result + ' </div>';
		result = result + ' </div>';
		result = result + '</div>';
		
		$("#tab-1").empty();
		$("#tab-2").empty();
		$("#tab-3").empty();
		$("#tab-4").empty();
		$("#tab-5").empty();
		$("#tab-6").empty();
		$("#tab-6").append(result);
		$("#loading").removeAttr("style");
		},
		
		});
}
</script>

<script>
//project side bar tabs
$(document).ready(function() {
	$("#tab-1").show();
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});
</script>

<jsp:include page="../masterFooter.jsp" />