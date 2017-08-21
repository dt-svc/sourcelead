<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


 <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee Payroll Invoice</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Employee Payroll Invoice</div>
    </section>
    <section class="project_side_wrap_inn_tabs payroll-invoice">
        <div class="clearfix mb">
            <label class="col-md-2 col-md-offset-3">
                Employee Name
            </label>
            <div class="col-md-4">
                <select class="chosen-select" id="empId" name="empName">
                  <option value=" " selected >-Select Employee-</option>
	            <c:forEach var="wage" items="${wageTaxSummaryList}">
					<option value="${wage.employeeId}">${wage.employeeName}</option>
				</c:forEach>
                </select>
            </div>
        </div>
      <ul class="project-tabs-menu">
        <li class="current"><a href="#tab-1">Payroll</a></li>
        <li><a href="#tab-2" onclick="getIncomeData();">Income</a></li>
        <li><a href="#tab-3" onclick="getChecks();">Manual checks</a></li>
         <li><a href="#tab-4">HR & Immigration</a></li>
        <li><a href="#tab-5">Balance</a></li>
        <div class="clearfix"></div>
      </ul>
        <div id="loading"></div>
      <div class="project_tabs clearfix pdtop">
        <div id="tab-1" class="project_tabs_inn" style="display: block">
          <div class="clearfix">
            <div class="timesheet_head minHeight">
              <div class="remove_search">
                <input placeholder="Search" type="text">
              </div>
            </div>
            <div class="require_wrap_table">
              <table>
                <thead>
                  <tr>
                    <th>Tranfer ID</th>
                    <th>Baseline</th>
                    <th>From Location</th>
                    <th>To Location</th>
                    <th>No. of Emp.</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>
                      <div class="tbldata-dotdot-100"> BIS10041 </div>
                    </td>
                    <td>
                      <div title="Rajeshkumar Behara" class="tbldata-dotdot-100"> Exmi project resource request </div>
                    </td>
                    <td>
                      <div title="TCS, Hyderabad" class="tbldata-dotdot-100"> TCS, Hyderabad </div>
                    </td>
                    <td>
                      <div title="TCS, Dalls" class="tbldata-dotdot-100"> TCS, Dalls </div>
                    </td>
                    <td>
                      <div title="10" class="tbldata-dotdot_80"> 10 </div>
                    </td>
                    <td>
                      <div class="tblminwidth-75"> <b>Draft</b> </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div id="tab-2" class="project_tabs_inn require_wrap_table datatable_filter filter_position">
          </div>
        <div id="tab-3" class="project_tabs_inn require_wrap_table datatable_filter filter_position">
          </div>
     
         <div id="tab-4" class="project_tabs_inn">
          <div class="clearfix">
            <div class="timesheet_head minHeight">
              <div class="remove_search">
                <input placeholder="Search" type="text">
              </div>
            </div>
            <div class="require_wrap_table">
              <table>
                <thead>
                  <tr>
                    <th>Tranfer ID</th>
                    <th>Baseline</th>
                    <th>From Location</th>
                    <th>To Location</th>
                    <th>No. of Emp.</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>
                      <div class="tbldata-dotdot-100"> BIS10041 </div>
                    </td>
                    <td>
                      <div title="Rajeshkumar Behara" class="tbldata-dotdot-100"> Exmi project resource request </div>
                    </td>
                    <td>
                      <div title="TCS, Hyderabad" class="tbldata-dotdot-100"> TCS, Hyderabad </div>
                    </td>
                    <td>
                      <div title="TCS, Dalls" class="tbldata-dotdot-100"> TCS, Dalls </div>
                    </td>
                    <td>
                      <div title="10" class="tbldata-dotdot_80"> 10 </div>
                    </td>
                    <td>
                      <div class="tblminwidth-75"> <b>Draft</b> </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div id="tab-5" class="project_tabs_inn">
          <div class="payrollinvoice-generate clearfix col-md-4 col-md-offset-4">
              <h4 class="clearfix text-center">Income</h4>
            <ul class="list-unstyled">
              <li>
                <dl>
                  <dt class="mb10">Payroll</dt>
                    <dd>Line1:<span class="pay-val">2,3456.00</span></dd>
                  <dd>Line2:<span class="pay-val">2,3456.00</span></dd>
                  <dd>Line3:<span class="pay-val">2,3456.00</span></dd>
                  <dd>Line4:<span class="pay-val">2,3456.00</span></dd>
                </dl>
              </li>
              <li>Adanced:<span class="pay-val">2,3456.00</span></li>
              <li>Genral Employee:<span class="pay-val">2,3456.00</span></li>
              <li>Vendor Payment:<span class="pay-val">2,3456.00</span></li>
              <li>Healthcare:<span class="pay-val">2,3456.00</span></li>
              <li>Abcabcabcabc:<span class="pay-val">2,3456.00</span></li>
              <li>Balace:<span class="pay-val balance">2,3456.00</span></li>
            </ul>
          </div>
        </div>
      </div>
    </section>
    
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
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});
    </script>
    
    <script>
    function getIncomeData(){
    	var empId = $("#empId").val();
    	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
    </script>
    
    
  
  <script>
  function getChecks(){
	     var empId = $("#empId").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
  </script>
    
    <script>
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
    $("#incometable").wrap("<div class='overflow_Auto  width100percnt'></div>")
}
</script>

<script>
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
    $("#empchecksListTable").wrap("<div class='overflow_Auto  width100percnt'></div>")
}
</script>