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
          <div class="pull-right brud-crum">Home &raquo; Expenses</div>
        </section>
        
		<!-- Timesheet -->

<div class="timesheet_out">

<div class="project_wrap">
	<div class="timesheet_head">
        	<ul>
				<li>Pradeep</li>
                <li><i class="fa fa-filter"></i><span>Fliter</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input value="None" id="c5" type="checkbox"><label for="c5">All</label></li>
							<li><input id="c6" type="checkbox"><label for="c6">Approved</label></li>
							<li><input id="c7" type="checkbox"><label for="c7">Draft</label></li>
							<li><input id="c8" type="checkbox"><label for="c8">Rejected</label></li>
                        </ul>
                    </div>                	
                </li>
				<li><a href="<c:url value='/addExpense'/>"><i class="fa fa-plus"></i>Add Expenses</a></li>		
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
            
            <div class="remove_search">
            	<input placeholder="search" type="text">
            </div>              
            
        </div>
    <div class="project_wrap_table">
    	<div id="expensesListDiv">	</div>
	</div>
	
	
</div>




<div class="side_mask"><div class="proj_close"></div></div>
<div class="project_side_wrap">
	<div class="project_side_wrap_inn">
    	<h1>Expense name here</h1>
        
<div class="project_side_wrap_inn_tabs">
    <ul class="project-tabs-menu">
        <li class="current"><a href="#tab-1">Details</a></li>
        <li><a href="#tab-2">History</a></li>
        <div class="clearfix"></div>
    </ul>
    
    <div class="project_tabs">
        <div id="tab-1" class="project_tabs_inn">
            <div class="project_tabs_inn_table">
				<div class="project_details project_detailsExp">
                
					<div class="Expdit">
						<a href="#" class="btn btn-primary">Status</a>
						<a href="add-expense.html" class="btn btn-default ml10"><i class="fa fa-pencil-square-o"></i> &nbsp;Edit</a>
						<!--<a href="add-expense.html"><i class="glyphicon glyphicon-pencil"></i>Edit</a>-->
					</div>                

                    <div class="clearfix"></div>
					<div class="project_details_cta">
						<strong>Expenses Id:</strong><span>EXP0001</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Project Name:</strong><span>Project 1</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Client:</strong><span>Client 1</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Start date:</strong><span>20/09/2017</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>End date:</strong><span>20/09/2017</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>No of Expenses:</strong><span>50</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Amount:</strong><span>20,000</span><div class="clearfix"></div>
					</div>	                    			
					<div class="clearfix"></div>
				</div>
           
<div class="project_wrap_table expensTableView">
	<table>
	<thead>
			<tr>
				<th>Exp Category</th>
				<th>Date</th>
				<th>Payment Mode</th>
				<th>Currency</th>
				<th>Description</th>
				<th>Amount</th>
			</tr>
		</thead>	
			<tbody>
			<tr>
				<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div class="tbldata-dotdot-me">Description Description</div></td>
				<td><div>5055</div></td>				
			</tr>
			<tr class="ExpDe">
				<td colspan="6">
					<div class="expDetails">
						<ul>
							<li><strong>Exp Category :</strong><span> Category 1</span><div class="clearfix"></div></li>
							<li><strong>Date :</strong><span> 20/09/2017</span><div class="clearfix"></div></li>
							<li><strong>Payment Mode :</strong><span> Cash</span><div class="clearfix"></div></li>
							<li><strong>Currency :</strong><span> USD</span><div class="clearfix"></div></li>
							<li><strong>Amount :</strong><span> 20,000</span><div class="clearfix"></div></li>
							<li><strong>Purpose :</strong><span> Purpose text</span><div class="clearfix"></div></li>
							<li><strong>Description :</strong><p> Description Description Description Description</p>
							<div class="clearfix"></div>
							</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</td>			
			</tr>						
			<tr>
				<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
			</tr>
			<tr class="ExpDe">
				<td colspan="6">
					<div class="expDetails">
						<ul>
							<li><strong>Exp Category :</strong><span> Category 1</span><div class="clearfix"></div></li>
							<li><strong>Date :</strong><span> 20/09/2017</span><div class="clearfix"></div></li>
							<li><strong>Payment Mode :</strong><span> Cash</span><div class="clearfix"></div></li>
							<li><strong>Currency :</strong><span> USD</span><div class="clearfix"></div></li>
							<li><strong>Amount :</strong><span> 20,000</span><div class="clearfix"></div></li>
							<li><strong>Purpose :</strong><span> Purpose text</span><div class="clearfix"></div></li>
							<li><strong>Description :</strong><p> Description Description Description Description</p>
							<div class="clearfix"></div>
							</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</td>			
			</tr>				
			<tr>
				<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
			</tr>
			<tr class="ExpDe">
				<td colspan="6">
					<div class="expDetails">
						<ul>
							<li><strong>Exp Category :</strong><span> Category 1</span><div class="clearfix"></div></li>
							<li><strong>Date :</strong><span> 20/09/2017</span><div class="clearfix"></div></li>
							<li><strong>Payment Mode :</strong><span> Cash</span><div class="clearfix"></div></li>
							<li><strong>Currency :</strong><span> USD</span><div class="clearfix"></div></li>
							<li><strong>Amount :</strong><span> 20,000</span><div class="clearfix"></div></li>
							<li><strong>Purpose :</strong><span> Purpose text</span><div class="clearfix"></div></li>
							<li><strong>Description :</strong><p> Description Description Description Description</p>
							<div class="clearfix"></div>
							</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</td>			
			</tr>				
			<tr>
				<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
			</tr>
			<tr class="ExpDe">
				<td colspan="6">
					<div class="expDetails">
						<ul>
							<li><strong>Exp Category :</strong><span> Category 1</span><div class="clearfix"></div></li>
							<li><strong>Date :</strong><span> 20/09/2017</span><div class="clearfix"></div></li>
							<li><strong>Payment Mode :</strong><span> Cash</span><div class="clearfix"></div></li>
							<li><strong>Currency :</strong><span> USD</span><div class="clearfix"></div></li>
							<li><strong>Amount :</strong><span> 20,000</span><div class="clearfix"></div></li>
							<li><strong>Purpose :</strong><span> Purpose text</span><div class="clearfix"></div></li>
							<li><strong>Description :</strong><p> Description Description Description Description</p>
							<div class="clearfix"></div>
							</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</td>			
			</tr>				
			<tr>
				<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>Category 1</div></td>
				<td><div>20/09/2017</div></td>
				<td><div>Cash</div></td>
				<td><div>USD</div></td>
				<td><div>Description</div></td>
				<td><div>5055</div></td>
			</tr>
		</tbody>
	</table>
</div>
           
            </div>
        </div>        
        <div id="tab-2" class="project_tabs_inn">		
			<div class="hisWrap">
			  <div class="hisWrapCta">
					<img src="images/23.jpg" alt="">
					<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>
					<span><i class="fa fa-clock-o"></i>2 min ago</span>
			  </div>
			  <div class="hisWrapCta">
					<img src="images/23.jpg" alt="">
					<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>
					<span><i class="fa fa-clock-o"></i>2 min ago</span>
			  </div>
			  <div class="hisWrapCta">
					<img src="images/23.jpg" alt="">
					<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>
					<span><i class="fa fa-clock-o"></i>2 min ago</span>
			  </div>
			  <div class="hisWrapCta">
					<img src="images/23.jpg" alt="">
					<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>
					<span><i class="fa fa-clock-o"></i>2 min ago</span>
			  </div>
			  <div class="hisWrapCta">
					<img src="images/23.jpg" alt="">
					<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>
					<span><i class="fa fa-clock-o"></i>2 min ago</span>
			  </div>
			  <div class="hisWrapCta">
					<img src="images/23.jpg" alt="">
					<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>
					<span><i class="fa fa-clock-o"></i>2 min ago</span>
			  </div>
			  <div class="hisWrapCta">
					<img src="images/23.jpg" alt="">
					<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>
					<span><i class="fa fa-clock-o"></i>2 min ago</span>
			  </div>
			  <div class="hisWrapCta">
					<img src="images/23.jpg" alt="">
					<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>
					<span><i class="fa fa-clock-o"></i>2 min ago</span>
			  </div>
			  <div class="hisWrapCta">
					<img src="images/23.jpg" alt="">
					<p><a href="#">Muneendra</a> has uploaded a new attachment in <a href="#">#4134 Registration should proceed only when</a> professional accept/select terms and conditions.</p>
					<span><i class="fa fa-clock-o"></i>2 min ago</span>
			  </div>					  					  					    
			</div>
        </div>       
    </div>
</div>        
      

	<div class="project_inn_mask"></div>
	<div class="project_error">
		<p>Delete conformation message....</p>
		<a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> 
	</div>	
    </div>
</div> 		
		<!-- Project -->
		
      </div>  
	
  <!--   Error Message   -->
  <div class="vendor_wrap_mask"></div>
  <div class="vendor_error">
    <textarea placeholder="Enter message"></textarea>
    <a class="close_color" href="javascript:void(0)">Yes</a><a href="javascript:void(0)">No</a> </div>
  
  <!--   Error Message   --> 

		
      </section>
      <!-- Content Area Ends--> 
<script>

function getExpensesList(){
	var sourceleadUrl = '${sourceleadUrl}';
	 $.ajax({
         type : "POST",
          url: sourceleadUrl+"/getExpensesList?${_csrf.parameterName}=${_csrf.token}",       
          success :function(data) {
        	  var result = "";
					result = result +" <table id='expensesPagination'>                            ";
					result = result +"	<tbody>                           ";
					result = result +"	</tbody><thead>                   ";
					result = result +"   	<tr>                          ";
					result = result +"       	<th>Exp Id</th>           ";
					result = result +"           <th>Project</th>         ";
					result = result +"           <th>Client</th>          ";
					result = result +"           <th>Start Date</th>      ";
					result = result +"           <th>End Date</th>        ";
					result = result +"           <th>No of Exp</th>       ";
					result = result +"           <th>Amount</th>          ";
					result = result +"           <th>Action</th>          ";
					result = result +"       </tr>                        ";
					result = result +"	</thead>	                      ";
					result = result +"       <tbody>                      ";
        	  
        	  
        	  
        	  
        	  var json = JSON.parse(data);
        	  for (var i = 0; i < json.length; i++) { 
        		  var report = json[i]; 
        		  
					 result = result +"        <tr>                                                                                                    ";
					 result = result +"        	<td><div class='proj_show'><span>"+report.expenseId+"</span><i class='fa fa-binoculars'></i></div></td>             ";
					 result = result +"            <td><div>"+report.projectMembers+"</div></td>                                                                       ";
					 result = result +"            <td><div>"+report.clientName+"</div></td>                                                                        ";
					 result = result +"            <td><div>"+report.startDate+"</div></td>                                                                      ";
					 result = result +"            <td><div>"+report.endDate+"</div></td>                                                                      ";
					 result = result +"            <td><div>"+report.noOfExpenses+"</div></td>                                                                              ";
					 result = result +"            <td><div>"+report.totalAmount+"</div></td>                                                                            ";
					 result = result +"            <td>                                                                                                ";
					 result = result +"            	<div class='tbldata-dotdot_50'>                                                                    ";
					 result = result +"           		 	<i class='fa fa-pencil-square-o'></i>                                                      ";
					 result = result +"            		 <i class='glyphicon glyphicon-trash'></i>                                                     ";
					 result = result +"				</div>                                                                                             ";
					 result = result +"            </td>                                                                                               ";
					 result = result +"        </tr>               			                                     ";
                 
        	  }
   			
        	  result = result +" </tbody>";
        	  result = result +" </table>";
        	  $("#expensesListDiv").val('');
	          $("#expensesListDiv").append(result);
	          expensesPagination();
          },
          
     }); 
	
	}

</script>      

<script>
function expensesPagination(){ 
	$.fn.dataTable.moment( 'DD-MM-YYYY' );
    $('#expensesPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 0
			}],
// 		"aaSorting": [[7,'desc']],
    	"bLengthChange": false,
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