<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<script src="resources/js/csc.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
<script src="resources/js/tag-it.min.js" type="text/javascript" charset="utf-8"></script>
<!-- end files for autocomplete with tagit -->

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
		<select class="form-control input-sm" id="userPayId" onchange="getUserPayrollList();">
                <option selected value="">Select Year</option>
                <option value="2025">2025</option>
                <option value="2024">2024</option>
                <option value="2023">2023</option>
                <option value="2022">2022</option>
                <option value="2021">2021</option>
                <option value="2020">2020</option>
                <option value="2019">2019</option>
                <option value="2018">2018</option>
                <option value="2017">2017</option>
                <option value="2016">2016</option>
                <option value="2015">2015</option>
                <option value="2014">2014</option>
                <option value="2013">2013</option>
                <option value="2012">2012</option>
                <option value="2011">2011</option>
                <option value="2010">2010</option>
                <option value="2009">2009</option>
                <option value="2008">2008</option>
                <option value="2007">2007</option>
                <option value="2006">2006</option>
                <option value="2005">2005</option>
                <option value="2004">2004</option>
                <option value="2003">2003</option>
                <option value="2002">2002</option>
                <option value="2001">2001</option>
                <option value="2000">2000</option>
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



<jsp:include page="../masterFooter.jsp" />