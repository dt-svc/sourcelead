<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


 <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Generate Payroll</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Generate Payroll</div>
    </section>
    <section class="contrHead">
      <ul>
        <li title="Draft"><a href="us-payrollgenerate.html" class="wVer">Generate Payroll</a></li>
        <li title="Finalised"><a href="javascript:void(0)">Advance</a></li>
        <div class="clearfix"></div>
      </ul>
    </section>
    
    <!-- Timesheet -->
    <div class="timesheet_out">
      <div class="project_wrap">
        <div class="clearfix mt"></div>
        <div class="timesheet_head minHeight40 mb10 mt">
        <form:form id="generate_payroll_form" name="generate_payroll_form" method="post">
          <div id="payrollCycleDiv" class="col-md-7 payrollFilter paddingRight">
            <div class="form-inline"> <span class="form-group">
              <input type="text" class="form-control calendar" value="" id="payrollStartDate" name="StartDate" field-name="Start Date" placeholder="Start Date">
              </span>
              <span class="form-group">
              <input type="text" class="form-control calendar" value="" id="payrollEndDate" name="EndDate" placeholder="End Date">
              </span>
              <span class="form-group">
              <input type="text" class="form-control calendar" value="" id="payrollPayDate" name="EndDate" readonly placeholder="Pay Date">
              </span>
              <div class="form-group">
                <button type="button" class="btn btn-primary btn-block" id="runPayrollCycle" onclick="runPayrollCycles();">Run
                Payroll</button>
              </div>
            </div>
          </div>
          <div class="col-md-2">
          <a href="javascript:void(0)" class="selectEmployee mt5 pull-left">Select Employee</a>
			</div>
          <div class="col-md-3 searchStyles">
            <input placeholder="" type="text" class="">
          </div>
          </form:form>
          
        </div>
        <div class="tableStyles-new">
          <table>
            <tbody>
            </tbody>
            <thead>
              <tr>
                <th>Emp Id</th>
                <th>Employee Name</th>
                <th>Regular<br>Hours</th>
                <th>Overtime<br>Hours</th>
                <th>Gross Pay</th>
                <th>Pre Tax <br> Deduction</th>
                <th>Post Tax <br> Deduction</th>
                <th>Taxes</th>
                <th>Benefits</th>
                <th>Net Pay</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>
                <div class="proj_show"><span>Emp0001</span><i class="fa fa-binoculars"></i></div></td>
                <td><div>Jaden Smith</div></td>
				<td>
              		<div class="tblminwidth-75">40</div>
              		<div class="tblminwidth-75">2000 $</div>
                </td>
				<td>
              		<div class="tblminwidth-75">40</div>
              		<div class="tblminwidth-75">2000$</div>
                </td>				<td>
              		<div class="tblminwidth-75">2000$</div>
                </td>
                <td><div class="tblminwidth-75"><a href="javascript:void(0)" onClick="preTax();">50$</a></div></td>
                <td><div class="tblminwidth-75"><a href="javascript:void(0)" onClick="postTax();">100$</a></div></td>
                <td><div class="tblminwidth-75"><a href="javascript:void(0)" onClick="taxes();">200$</a></div></td>
                <td><div>200$</div></td>
                <td><div class="tblminwidth-75">3000$</div></td>
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
    </div>
  </section>
  <!-- Content Area Ends--> 

<script>
	$(function() {
		
		$('#payrollStartDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate() + 1);
				$("#payrollEndDate").datepicker("option", "minDate", dt);
				addValidClass();
			}
		});
		
		$('#payrollEndDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate() - 1);
				$("#startDate").datepicker("option", "maxDate", dt);
				addValidClass();
			}
		});
		
		$('#payrollPayDate').datepicker({
			dateFormat : 'dd-mm-yy',
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate() - 1);
				$("#payrollPayDate").datepicker("option", "maxDate", dt);
				addValidClass();
			}
		});
		
	});
</script>

<script>
function saveContribution(){
	if ($('#generate_payroll_form').validate(false, validationSettings)){  
 	 	    var formData = getFormData("generate_payroll_form");
		        	  $.post(sourceleadUrl+"/generateUSPayroll?"+tokenParamName+"="+tokenValue, formData, function(data) {
		        		 alert("success");
		  			   }); 
		    	         return true; 
         }else{
	    		return false;
	    	  } 
}

$('body').on('blur', '#add_deduction_form', function() {
	$("#add_contribution_form").showHelpOnFocus();
	$("#add_contribution_form").validateOnBlur(false, validationSettings);  
}); 
</script>
<jsp:include page="../masterFooter.jsp" />