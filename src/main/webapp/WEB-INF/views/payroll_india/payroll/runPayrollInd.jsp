<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <!-- Content Area -->
  <section class="content-wraper contentWraper">
<section class="content-header clearfix">
<div class="pull-left">
<h2>Payroll</h2>
</div>
<div class="pull-right brud-crum">Home &raquo; Payroll</div>
</section>
<div class="clearfix"></div>
<div class="timesheet_out">

<div class="tablelist-wrap">
<div class="timesheet_head">
<ul>
	<li id="runPay"><a href="javascript:void(0)">Select Emp </a></li>	
	<div class="clearfix"></div>
</ul>
<div class="clearfix"></div>

<div class="remove_search"><input placeholder="Search" type="text"></div>              

</div>
<div class="styleTables">
<table>
	<thead>
		<tr>
			<th>Emp Name</th>
			<th>No of Days</th>
			<th>Leaves</th>
			<th>Rate</th>
			<th>Deductions</th>
			<th>Net Pay</th>
			<th>Action</th>
		</tr>
	</thead>	
<tbody>
	<tr>
		<td><div>Mr Jhon</div></td>
		<td><div>25</div></td>
		<td><div>2</div></td>
		<td><div>--</div></td>
		<td><div>--</div></td>
		<td>
			<div><a href="javascript:void(0)" onClick="netPay();">30000</a></div>
		</td>
		<td>
			<div class="tbldata-dotdot_50">
				<i class="glyphicon glyphicon-pencil"></i>
				<i class="glyphicon glyphicon-trash"></i>
			</div>
		</td>
	</tr>
	<tr>
		<td><div>Mr Jhon</div></td>
		<td><div>25</div></td>
		<td><div>2</div></td>
		<td><div>--</div></td>
		<td><div>--</div></td>
		<td><div>30000</div></td>
		<td>
			<div class="tbldata-dotdot_50">
				<i class="glyphicon glyphicon-pencil"></i>
				<i class="glyphicon glyphicon-trash"></i>
			</div>
		</td>
	</tr>
	<tr>
		<td><div>Mr Jhon</div></td>
		<td><div>25</div></td>
		<td><div>2</div></td>
		<td><div>--</div></td>
		<td><div>--</div></td>
		<td><div>30000</div></td>
		<td>
			<div class="tbldata-dotdot_50">
				<i class="glyphicon glyphicon-pencil"></i>
				<i class="glyphicon glyphicon-trash"></i>
			</div>
		</td>
	</tr>
	<tr>
		<td><div>Mr Jhon</div></td>
		<td><div>25</div></td>
		<td><div>2</div></td>
		<td><div>--</div></td>
		<td><div>--</div></td>
		<td><div>30000</div></td>
		<td>
			<div class="tbldata-dotdot_50">
				<i class="glyphicon glyphicon-pencil"></i>
				<i class="glyphicon glyphicon-trash"></i>
			</div>
		</td>
	</tr>
	<tr>
		<td><div>Mr Jhon</div></td>
		<td><div>25</div></td>
		<td><div>2</div></td>
		<td><div>--</div></td>
		<td><div>--</div></td>
		<td><div>30000</div></td>
		<td>
			<div class="tbldata-dotdot_50">
				<i class="glyphicon glyphicon-pencil"></i>
				<i class="glyphicon glyphicon-trash"></i>
			</div>
		</td>
	</tr>              		
</tbody>
</table>
</div>
<div class="clearfix mt mb">
	<a href="javascript:void(0)" class="btn btn-success btnSuccess pull-right mr" onClick="confirmPayroll();">Confirm</a>
</div>


</div>


</div>  
</section>
<!-- Content Area Ends--> 

 

  

<jsp:include page="../../masterFooter.jsp" />