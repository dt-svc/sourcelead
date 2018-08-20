<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employee Payroll</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Employee Payroll</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Select Employee</label>
				<div class="col-md-12">
					<select class="form-control chosen-select">
					<option>Select Employee</option>
						<option>Employee1</option>
						<option>Employee2</option>
						<option>Employee3</option>
						<option>Employee4</option>
						<option>Employee5</option>
						<option>Employee6</option>
					</select>
				</div>
            </div>
            <div class="form-group">
              <label class="col-md-12">From Date</label>
              <div class="col-md-12">
                	<input type="text" class="form-control calendar">
              </div>
            </div>
			</div>
            <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12">Select Department</label>
              <div class="col-md-12">
                <select class="form-control chosen-select">
					    <option>Select Type</option>
						<option>Regular</option>
						<option>Additional</option>
					</select>
              </div>
            </div>
             
             <div class="form-group">
              <label class="col-md-12">To Date</label>
              <div class="col-md-12">
                	<input type="text" class="form-control calendar">
              </div>
            </div>
             
        </div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="runPayrollregister();">
            <input value="Reset" class="btn btn-defualt ml10" type="button">
          </div>
        </div>
      </div>      
	</div>
    
     <div class="clearfix"></div>
    		
    		
    		
    		
  <div class="timesheet_out" id="payrollRegister" style="display: block;">
      
      <div class="clearfix">
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Total Employees:</label>
                  			<span class="col-md-5">10</span>
                  		</div>
                  	</div>
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Net Pay:</label>
                  			<span class="col-md-5">8</span>
                  		</div>
                  	</div>
                  	<div class="clearfix mb mt"></div>
       </div>
      
       <div class="clearfix">
        
        <div class="positionRelative reportHead clearfix"> <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span>
  <ul class="pull-right list-unstyled list-inline">
    <li><a class="newAdBtn mr5 hidden" href="/app/userCreation">+  Associate</a></li>
    <li><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a></li>
    <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
       <span><a href="javascript:void(0)" onclick="exportToPdf();" title="PDF">Export PDF</a> <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> </span> </div>
    </li>
    <li class="">
      <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
      <span class="repSearchIcon" onclick="getDynamciUserList();"></span></li>
  </ul>
</div>
        <div class="require_wrap_table tableFirstSecond clearfix">
          <table>
            <thead>
              <tr>
                <th>Employee ID</th>
                <th>Employee Name</th>
                <th>RagbatdXXXX</th>
                <th>Gross Pay</th>
                <th>Pre Tax Deduction</th>
				<th>Federal Tax</th>
                <th>With Holding</th>
                <th>Social Securities</th>
                <th>Medicare withholding</th>
                <th>State with holding</th>
                <th>Other</th>
                <th>SUI / SDI</th>
                <th>Post Tax</th>
                <th>Pay Items</th>
                <th>Net Pay</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><div class="tblminwidth-75">0001</div></td>
                <td><div class="tbldata-dotdot">Karan Kumar Sahoo</div></td>
                <td><div class="tbldata-dotdot-vsm">01/01/2018</div></td>
                <td><div class="tbldata-dotdot-vsm">4000</div></td>
                <td><div class="tblminwidth-50">Pretax</div></td>
                <td><div class="tblminwidth-50">Federal Tax</div></td>
                <td><div class="tblminwidth-50">Withholding</div></td>
                <td><div class="tblminwidth-50">Social Security</div></td>
                <td><div class="tblminwidth-50">Medicare withholding</div></td>
                <td><div class="tblminwidth-50">State withholding</div></td>
                <td><div class="tblminwidth-50">Other</div></td>
                <td><div class="tblminwidth-50">SUI/SDI</div></td>
                <td><div class="tblminwidth-50">Post Tax</div></td>
                <td><div class="tblminwidth-50">Pay Tax</div></td>
                <td><div class="tblminwidth-50">Net Pay</div></td>
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
    		
     		
      <!-- Content Area Ends--> 
    </div>
    
  </section>
  
  <script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
        </script> 

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
        
<script type="text/javascript">
	$(document).ready(function(e) {
		$("#savevendor").click(function(){
			$('#myform').validate({
			rules:{
				things:{
					required:true
				}
			}
			});
		});
	});
	$(function () {

	});
</script>
<script>	
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});
function runPayrollregister() {
	$("#payrollRegister").toggle();
}
</script>
  
<jsp:include page="../masterFooter.jsp" />