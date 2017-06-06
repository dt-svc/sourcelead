<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

  <section class="content-header clearfix">
    <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Expenses Approval</h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; Expenses Approval</div>
        </section>
        
		<!-- Project -->
<div class="timesheet_out">
	
	<div class="timesheet_appr_wrap">
			
<div class="timesheetNewHead">
<!--			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>Select Project</option>
                  <option>Project 1</option>
                  <option>Project 2</option>
                  <option>Project 3</option>
                  <option>Project 4</option>
                </select>
            </div>           
			<div class="timeNewSe timeNewSe_a addicon">
                <select class="form-control chosen-select">
                  <option>Select Frequency</option>
                  <option>28/11/2016 to 04/12/2016</option>
                  <option>05/12/2016 to 11/12/2016</option>
                  <option>12/12/2016 to 18/12/2016</option>
                  <option>19/12/2016 to 25/12/2016</option>
                  <option>26/12/2016 to 01/01/2017</option>
                  <option>02/01/2017 to 08/01/2017</option>
                  <option>09/01/2017 to 15/01/2017</option>
                  <option>16/12/2017 to 22/12/2017</option>
                  <option>23/12/2016 to 29/12/2017</option>
                 
                  </select>
            </div>
			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>Select Client</option>
                  <option>Client 1</option>
                  <option>Client 2</option>
                  <option>Client 3</option>
                  <option>Client 4</option>
                </select>
            </div>             
			<div class="timeNewSe">
                <select class="form-control chosen-select">
                  <option>Stages</option>
                  <option>New</option>
                  <option>Submit for approval</option>
                  <option>Rejected</option>
                </select>
            </div>   -->     
	<div class="pull-right expBtn">
		<a href="<c:url value='/addApprovalExpense'/>"><i class="glyphicon glyphicon-plus"></i>&nbsp;Add Expense</a>
	</div>   
	<div class="remove_search">
		<input placeholder="search" type="text">
	</div>    
	<div class="clearfix"></div>          
</div>	
		
<div class="timesheet_appr_table">
    	<table>
        	<tbody>
			<thead>
            	<tr>
                	<th>Employee</th>
                    <th>Email id</th>
                    <th>Project</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>No of Exp</th>
					<th>Amount</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
			</thead>	
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div>
					</td>
                   	<td><div>emailid@domain.com</div></td>
                   	<td><div><span>Project 1</span><br><span>Client 1</span></div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">50</div></td>
                    <td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">5000</a></div></td>
               		<td><div>Submit for Approval</div></td>
                    <td><div class="sm"><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div></td>               
                </tr>
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div>
					</td>
                   	<td><div>emailid@domain.com</div></td>
                   	<td><div><span>Project 1</span><br><span>Client 1</span></div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">50</div></td>
                   	<td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">5000</a></div></td>
               		<td><div>Approved</div></td>
                    <td><div class="sm"><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div></td>               
                </tr>                
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div>
					</td>
                   	<td><div>emailid@domain.com</div></td>
                   	<td><div><span>Project 1</span><br><span>Client 1</span></div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">50</div></td>
                    <td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">5000</a></div></td>
               		<td><div>New</div></td>
                    <td><div class="sm"><h3>Approved</h3></div></td>               
                </tr>
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div>
					</td>
                   	<td><div>emailid@domain.com</div></td>
                   	<td><div><span>Project 1</span><br><span>Client 1</span></div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">50</div></td>
                    <td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">5000</a></div></td>
               		<td><div>Saved</div></td>
                    <td><div class="sm"><h3 class="reje_color">Rejected</h3>
						<i class="glyphicon glyphicon-comment ml10 edit-reason"><span class="edit-status">Wrong job selected for UI Developer</span></i></div>
               </td>               
                </tr>
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div>
					</td>
                   	<td><div>emailid@domain.com</div></td>
                   	<td><div><span>Project 1</span><br><span>Client 1</span></div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">50</div></td>
                    <td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">5000</a></div></td>
               		<td><div>New</div></td>
                    <td><div class="sm"><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div></td>
                </tr>
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div>
					</td>
                   	<td><div>emailid@domain.com</div></td>
                   	<td><div><span>Project 1</span><br><span>Client 1</span></div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">50</div></td>
                    <td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">5000</a></div></td>
               		<td><div>Submit for Approval</div></td>
                    <td><div class="sm"><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div></td>
                </tr>  
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div>
					</td>
                   	<td><div>emailid@domain.com</div></td>
                   	<td><div><span>Project 1</span><br><span>Client 1</span></div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">50</div></td>
                    <td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">5000</a></div></td>
               		<td><div>New</div></td>
               		<td><div class="sm"><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div></td>
                </tr>
                <tr>
                	<td><div class="time_appr_user"><img src="images/pratap-bis.jpg">
						<span><b>Pratap Reddy</b><b>Admin</b></span></div>
					</td>
                   	<td><div>emailid@domain.com</div></td>
                   	<td><div><span>Project 1</span><br><span>Client 1</span></div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">05/26/2017</div></td>
                    <td><div class="sm">50</div></td>
                    <td><div class="sm"><a href="javascript:void(0)" class="ExpAmount">5000</a></div></td>
               		<td><div>New</div></td>
               		<td><div class="sm"><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div></td>
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
    <div class="clearfix"></div>
	</ul>
	<div class="clearfix"></div>
</nav>
	
	</div>
	
</div>
			
		<!-- Project -->
		
      </section>
    </section>
 
<jsp:include page="../masterFooter.jsp" />