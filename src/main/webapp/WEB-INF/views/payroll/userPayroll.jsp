<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
window.onload = function () {
	getUserPayrollList();
}
</script>

  <!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>User Payroll</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; User Payroll</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal">

<div class="project_wrap">
	<div class="timesheet_head">
		<div class="payRoDate clearfix col-md-2 dropdown-select">
		<select class="form-control input-sm" id="userPayId" onchange="getUserPayrollList();getYearsDropdown();">
                <option selected value="">Select Year</option>
              </select>
		</div>    	           
        </div>
        <div id="loading"></div>
    <div class="project_wrap_table datatable_filter">
    <div id="userPayrollListDiv"></div>
   </div>
	
	
</div>     
      
      <!-- Content Area Ends--> 
    </div>
</div>
 
  </section>

<script>
function getUserPayrollList(){
	var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var selectedYear = $("#userPayId").val();
	
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
				  $.ajax({
				    	type: "GET",
				        url: sourceleadUrl+"/getUserPayrollList?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId+"&selectedYear="+selectedYear,
				        success: function(json) {
				        var result = "";				        	
						result = result + '<table id="userPayrollList" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>Pay Cycle Start Date</th>';
						result = result + '<th>Pay Cycle End Date</th>';
						result = result + '<th>Amount</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
			        	  for (var i=0; i<json.length; i++)
						{
			        		 var opt = json[i];
			        		    result = result + ' <tr>';
			        		    if(opt.payrollStartDateStr !=null && opt.payrollStartDateStr !='' && opt.payrollStartDateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.payrollStartDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
			        		    
			        		    if(opt.payrollEndDateStr !=null && opt.payrollEndDateStr !='' && opt.payrollEndDateStr !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+opt.payrollEndDateStr+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
								result = result + '<td><div class="tblminwidth-75">'+opt.amount+'</div></td>';
								result = result + ' </tr>';
						} 
						result = result + '  </tbody>';
						result = result + ' </table>';
						$("#userPayrollListDiv").empty();
						$("#userPayrollListDiv").append(result);
						$("#loading").removeAttr("style");
						userPayrollListPagination();
				        	
				        },
				        
				    });
		 
}
</script>

<script>
function userPayrollListPagination(){ 
    $('#userPayrollList').DataTable( { 
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
    $("#WagesAndTaxListTable").wrap("<div class='overflow_Auto'></div>")
}
</script>

<script>
$(function getYearsDropdown() {
	  var start_year = new Date().getFullYear();
	  for (var i = start_year; i > start_year - 118; i--) {
	    $('select').append('<option value="' + i + '">' + i + '</option>');
	  }
	});
</script>


<jsp:include page="../masterFooter.jsp" />