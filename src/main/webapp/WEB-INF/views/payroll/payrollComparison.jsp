<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


 
 <!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll Comparision Report</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; Payhead List &raquo; Payroll Comparision Report</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Pay Group</label>
				<div class="col-md-12">
					<select class="form-control sel">
					<option>Select Group</option>
						<option>Monthly</option>
						<option>Weekly</option>
					</select>
				</div>
            </div>
            <div class="form-group">
              <label class="col-md-12">Payroll Type</label>
              <div class="col-md-12">
                <select class="form-control sel">
					    <option>Select Type</option>
						<option>Regular</option>
						<option>Additional</option>
					</select>
              </div>
            </div>
			</div>
            <div class="col-md-5">
             <div class="form-group">
              <label class="col-md-12">Compare Between</label>
              <div class="col-md-12">
               <div class="row">
               		<div class="col-md-6">
               			<select class="form-control sel">
					    <option>Select Pay Period</option>
						<option>01-02-2018 28-02-2018</option>
						<option>01-03-2018 28-03-2018</option>
						<option>01-02-2018 28-02-2018</option>
					</select>
               		</div>
               		<div class="col-md-6">
               			<select class="form-control sel">
					    <option>Select Pay Period</option>
						<option>01-02-2018 28-02-2018</option>
						<option>01-03-2018 28-03-2018</option>
						<option>01-02-2018 28-02-2018</option>
					</select>
               		</div>
               </div>
                
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-12">Department</label>
              <div class="col-md-12">
                <select class="form-control chosen-select">
					    <option>Select Department</option>
						<option>Department-1</option>
						<option>Department-2</option>
						<option>Department-3</option>
					</select>
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
    		
    		
    		
    		
  <div class="timesheet_out" id="payrollRegister" style="display: none;">
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
        <div class="clearfix tableNtable">
        <table width="100%" cellpadding="0" cellspacing="0">
            <thead>
              <tr>
                <th>Header</th>
                <th>01/02/2018 08/02/2018</th>
                <th>09/02/2018 14/02/2018</th>
              </tr>
            </thead>
		</table>
           <table width="100%" cellpadding="0" cellspacing="0">
            <tbody>
						<tr>
							<td colspan="3"><h4>Hours</h4></td>
					</tr>
						<tr>
							<td>Regular</td>
							<td>20 hrs</td>
							<td>30 hrs</td>
						</tr>
						<tr>
							<td>OT</td>
							<td>20 hrs</td>
							<td>30 hrs</td>
						</tr>
					</tbody>
			</table>
           
            <table width="100%" cellpadding="0" cellspacing="0" class="clearfix">
					<tbody>
					<tr>
						<td colspan="3"><h4>Wages</h4></td>
					</tr>
						<tr>
							<td>Gross Wage</td>
							<td>400 $</td>
                <td>500 $</td>
						</tr>
					</tbody>
				</table>
            
				<table width="100%" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td colspan="3"><h4>Contribution</h4></td>
						</tr>
						<tr>
							<td>Contribution1</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
						<tr>
				<td>Contribution2</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
					</tbody>
				</table>

            

				<table width="100%" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td colspan="3"><h4>Taxes</h4></td>
						</tr>
						<tr>
							<td>FDI</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
						<tr>
							<td>SUI</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
					</tbody>
				</table>

             
             

				<table width="100%" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td colspan="3"><h4>Deductions</h4></td>
						</tr>
						<tr>
							<td>Deduction1</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
						<tr>
							<td>Deduction2</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
					</tbody>
				</table>
              

				<table width="100%" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td colspan="3"><h4>Contribution</h4></td>
						</tr>
						<tr>
							<td>Contribution1</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
						<tr>
				<td>Contribution2</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
					</tbody>
				</table>

				<table width="100%" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td colspan="3"><h4>Pay Items</h4></td>
						</tr>
						<tr>
							<td>Item1</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
						<tr>
							<td>Item2</td>
                <td>20 hrs</td>
                <td>30 hrs</td>
						</tr>
					</tbody>
				</table>
              
				<table width="100%" cellpadding="0" cellspacing="0">
					<tbody>
						<tr>
							<td><h4>Net Pay</h4></td>
                <td>400$</td>
                <td>500$</td>
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