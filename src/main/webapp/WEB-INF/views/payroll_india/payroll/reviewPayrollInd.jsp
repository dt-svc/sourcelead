<jsp:include page="../../masterHeader.jsp" />
<jsp:include page="../../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Review Payroll</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Review Payroll</div>
    </section>
    
    <div class="tabsMenu clearfix">
   		<ul class="list-unstyled">
		   <li class="current"><a href="reviewpayroll.html">Review Payroll</a></li>
		   <li><a href="approvepayroll.html">Approve Payroll</a></li>
	   </ul>
   </div>
    
    
    <div class="clearfix"></div>
        <div class="clearfix"></div>
    <div class="timesheet_out">
      <div class="tablelist-wrap">
        <div class="timesheet_head minHeight">
          <div class="col-md-12 mt">
            <div class="row">
              <div class="col-md-2">
                  <select class="sel selheight30">
                    <option selected>Group</option>
                    <option>Group-1</option>
                    <option>Group-2</option>
                    <option>Group-3</option>
                  </select>
              </div>
              <div class="col-md-2">
                   <select class="sel selheight30">
                    <option selected>Pay Period</option>
                    <option>01/02/2018</option>
                    <option>01/02/2018</option>
                    <option title="09/02/2018 - 15/01/2018" value="09/02/2018 - 15/01/2018">09/02/2018 - 15/01/2018</option>
                  </select>
              </div>
              <div class="col-md-2">
                <input name="" class="form-control calendar input-sm" placeholder="Check Date" type="text">
              </div>
              <div class="col-md-2">
                   <select class="sel selheight30">
                    <option selected>Rate</option>
                    <option>LCA Wages</option>
                    <option>Salary</option>
                    <option>Project</option>
                  </select>
              </div>
              <div class="col-md-2">
                   <select class="sel selheight30">
                    <option selected>Payroll Type</option>
                    <option>Worksheet</option>
                    <option>General</option>
                  </select>
              </div>
              <div class="col-md-2">
					<a  class="btn btn-success btnSuccess btn-sm anchorWhite" onclick="runIndPayroll();">Run Payroll</a>
			  </div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
        
		
		  
         <div class="tableScroll styleTables">
          <div class="clearfix mt">
            <table id="example" class="stripe row-border order-column" style="width:100%">
              <thead>
                <tr>
					<th>Exclude</th>
					<th>Emp ID</th>
					<th>Emp Name</th>
					<th>Regular Hours</th>
					<th>OT Hours</th>
					<th>No. of Leaves</th>
					<th>Gross Pay</th>
					<th>Benefits</th>
					<th>Post Tax</th>
					<th>TDS</th>
					<th>Net Pay</th>
                </tr>
              </thead>
              <tbody>
					<tr>
					<td>
					<div class="checkbox-new pull-left ml">
						<input id="emp1" class="checkbox-custom" name="paidComponent" type="checkbox">
						<label for="emp1" class="checkbox-custom-label"></label>
					</div></td>
					<td><div class="tblminwidth-50">0022338790 </div></td>
					<td><div class="tbldata-dotdot-vsm">Rajkumarvinnokota Animolkumar </div></td>
					<td><div class="tblminwidth-50">40Hrs</div>
					<div class="tblminwidth-50 clearfix text-danger">200<span class="paddingLeft">&#8377;</span></div></td>
					<td><div class="tblminwidth-50"> 3Hrs </div>
					<div class="tblminwidth-50 clearfix text-danger">100<span class="paddingLeft">&#8377;</span></div></td>
					<td><div class="tblminwidth-50 clearfix"> 300<span class="paddingLeft">&#8377;</span></div></td>
					<td><div class="tblminwidth-50 clearfix"> 50<span class="paddingLeft">&#8377;</span></div></td>
					<td><div class="tblminwidth-50 clearfix"> 50<span class="paddingLeft">&#8377;</span></div></td>
					<td><div class="tblminwidth-50 clearfix"> 100<span class="paddingLeft">&#8377;</span></div></td>
					<td><div class="tblminwidth-50 clearfix"> 100<span class="paddingLeft">&#8377;</span></div></td>
					<td>
						<div class="tblminwidth-50 clearfix">200<span class="paddingLeft">&#8377;</span></div></td>
					</tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="clearfix mt"></div>
        <nav class="clearfix">
          <ul class="pull-right mr list-unstyled list-inline">
            <li><a href="#" class="btn btn-primary btnSuccess">Sent for Approve</a></li>
            <li><a href="#" class="btn btn-default ml mr">Confirm</a></li>
            <li><a href="#" class="btn btn-default">Cancel</a></li>
          </ul>
          <div class="clearfix"></div>
        </nav>
      </div>
    </div>
  </section>
  <!-- Content Area Ends--> 

 <script>
function runIndPayroll(){
	location.replace(sourceleadUrl+"/runPayrollInd");
}
</script>

  

<jsp:include page="../../masterFooter.jsp" />