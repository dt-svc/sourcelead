<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="/resources/js/indPayrollReports.js"></script>

<script>
$(document).ready(function(){
   getDepartmentList();
});
</script>
<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Salary Register </h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/indPayrollReportList'/>">India Payroll Reports List</a> &raquo; Salary Register</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    <div id="payrollReportLoading"></div>
    <form:form id="payrollregister_form" name="apply_leave_form" method="post">
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Select Department<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
					<select class="form-control sel" id="payrollDepartment" name="departmentId" onchange="getEmployeesBasedOnDepartment('employeeId')">
					</select>
				</div>
            </div>
            <div class="form-group">
              <label class="col-md-12">From Date <span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                <input type="text" class="form-control calendar" id="startDate" name="startDate" readonly placeholder="From Date">
              </div>
            </div>
			</div>
            <div class="col-md-5">
             <div class="form-group">
              <label class="col-md-12">Select Associate <span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                <select class="form-control chosen-select" multiple id="employeeId" name="employeeIds">
					</select>
              </div>
            </div>
             <div class="form-group">
              <label class="col-md-12">To Date</label>
              <div class="col-md-12">
                <input type="text" class="form-control calendar" id="endDate" name="endDate" readonly placeholder="To Date">
              </div>
            </div>
        </div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onclick="runIndPayrollSalaryRegisterReport();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onclick="resetForm();">
          </div>
        </div>
      </div>      
	</div>
    </form:form>
     <div class="clearfix"></div>
    	<div id="popUp"></div>	
  	<div class="timesheet_out" id="payrollRegister" style="display: none;">
      <div class="clearfix">
        <div class="positionRelative reportHead clearfix"> 
  			<ul class="pull-right list-unstyled list-inline">
				<li class="">
				<a class="overIcon" title="Actions" onclick="newActions();"></a>
      				<div class="newActPop" style="display: block;">
       					<span><a onclick="payrollSalaryRegisterExport('pdf');" title="PDF">Export PDF</a>
       					 <a title="Excel" onclick="payrollSalaryRegisterExport('excel');">Export Excel</a></span> </div>
   				</li>
			    <li class="">
			      <input class="repSearchBar" placeholder="Search" id="payrollSalarySearch" type="text">
			      <span class="repSearchIcon" onclick="runIndPayrollSalaryRegisterReport();"></span>
			    </li>
  			</ul>
		</div>
        <div class="require_wrap_table datatable_filter">
        	<div id="payrollSalaryRegisterTableDiv"></div>
        </div>
      </div>
    </div>
     		
      <!-- Content Area Ends--> 
    </div>
    
  </section>
<script>
function getDepartmentList(){
	$.ajax({
    	type: "GET",
        url: sourceleadUrl+"/getDepartmentList?&departmentStatus=1", 
        success: function(json) {
        var output = "";
       output = output + "<option value=''>Select Department</option>";
		for (var i=0; i<json.data.length; i++)
		{
			var opt = json.data[i];
			output = output + "<option value='" +opt.id+ "'>"+opt.departmentName+"</option>";
		}
		$("#payrollDepartment").empty();
		$("#payrollDepartment").append(output);
		$('#payrollDepartment').trigger("chosen:updated"); 
        },
    });	
}

var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
var finalDateFormatProjectSave = '';
if(locationDate.match("yyyy-MM-dd")){
	  finalDateFormatProjectSave = 'yy-mm-dd';
}if(locationDate.match("dd-MM-yyyy")){
	  finalDateFormatProjectSave = 'dd-mm-yy'; 
}if(locationDate.match("MM-dd-yyyy")){
	  finalDateFormatProjectSave = 'mm-dd-yy'; 
}if(locationDate.match("yyyy-dd-MM")){
	  finalDateFormatProjectSave = 'yy-dd-mm'; 
}

$('#startDate').datepicker({
	dateFormat : finalDateFormatProjectSave,
	changeMonth: true,
	changeYear: true, 
	maxDate: 0,
	onSelect : function(selected) {
		var dt = $(this).datepicker('getDate', '+1d');
		dt.setDate(dt.getDate() + 1);
		$("#endDate").datepicker("option", "minDate", dt);
	}
});

$('#endDate').datepicker({
	dateFormat : finalDateFormatProjectSave,
	changeMonth: true,
	changeYear: true, 
	maxDate: 0,
	onSelect : function(selected) {
		var dt = $(this).datepicker('getDate', '-1d');
		dt.setDate(dt.getDate() - 1);
		$("#startDate").datepicker("option", "maxDate", dt);
	}
});


</script>       
<script>
function resetForm(){
	 $("#payrollRegister").hide();
	 $("#endDate").val('');
	 $("#startDate").val('');
	 $("#payrollDepartment").val('');
	 $("#payrollDepartment").trigger("chosen:updated"); 
	 
	 $("#employeeId").val('');
	 $("#employeeId").trigger("chosen:updated");
	 
}
</script>

<script>
function payrollSalaryRegisterExport(type){
	var searchValue = $("#payrollSalarySearch").val();
	var department = $('#payrollDepartment').val();
	var employeeId = $('#employeeId').val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
    window.location.href = sourceleadUrl+"/indPayrollSalaryRegisterExport?&departmentId="+department+"&employeeIds="+employeeId+"&startDate="+startDate+"&endDate="+endDate+"&searchValue="+searchValue+"&exportType="+type;
}
</script>
<script type="text/javascript">
function runIndPayrollSalaryRegisterReport(){
	var searchValue = $("#payrollSalarySearch").val();
	var department = $('#payrollDepartment').val();
	var employeeId = $('#employeeId').val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if(department != null && department != '' && department != 'undefined' && employeeId != null && employeeId != '' && employeeId != 'undefined' && startDate != null && startDate != '' && startDate != 'undefined'){
		$("#payrollReportLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var url = sourceleadUrl+ "/indPayrollSalaryRegisterReport?${_csrf.parameterName}=${_csrf.token}&departmentId="+department+"&employeeIds="+employeeId+"&startDate="+startDate+"&endDate="+endDate+"&searchValue="+searchValue;
		$("#payrollRegister").show();
		preparePayRollSalaryList(url);
		$("#payrollSalarySearch").val('');
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Please select Department and Employee and From Date</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='removePopUp();' style='background-color: #FF0000; width : 100%'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#remove_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
function removePopUp(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script type="text/javascript">
function preparePayRollSalaryList(url){
	 $("#payrollReportLoading").removeAttr("style");
	 $("#payrollSalaryRegisterTableDiv").empty();
	 var result = "";
		result = result +" <div class='remove_search'>";
		result = result +"</div>";
		result = result + '<table id="payrollRegisterTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Associate Id</th>';
        result = result + '<th>Associate Name</th>';
        result = result + '<th>Department</th>';
        result = result + '<th>Effective Date</th>';
        result = result + '<th>Increase %</th>';
        result = result + '<th>Annual CTC</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
     $("#payrollSalaryRegisterTableDiv").append(result);
     payrollSalaryPagination(url); 
}

function payrollSalaryPagination(url){
	var colIndex = 0;
    $('#payrollRegisterTable').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "empId" },
            { "data": "empName"},
            { "data": "department"},
            { "data": "effectiveDate" },
            { "data": "increase" },
            { "data": "annualCTC"},
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "empId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
		    	  		if(opt.employeeid !=null && opt.employeeid != '' && opt.employeeid !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-150" title="'+opt.employeeid+'" >'+opt.employeeid+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-150">---</div>';
		        		}
		              return result; 
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "empName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.employeeName !=null && opt.employeeName != '' && opt.employeeName !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.employeeName+'" >'+opt.employeeName+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
            	  return result; 
              }
	          },{
            "targets": colIndex++,
            "data": "department",
            "render": function ( data, type, opt, meta ) {
            	 var result = "";
            	 if(opt.departmentName !=null && opt.departmentName != '' && opt.departmentName !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.departmentName+'" >'+opt.departmentName+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
            	return result; 
            }
          }, {
              "targets": colIndex++,
              "data": "effectiveDate",
              "sorting":false,
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
   	    	  		result = result + '<div class="tbldata-dotdot-vsm" title="'+opt.effectiveDate+'" >'+opt.effectiveDate+'</div>';
            	    return result; 
              }
            },{
	    	  	"targets": colIndex++,
	    	  	"data": "increase",
	    	  	"sorting":false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.incresePercentage+'" >'+opt.incresePercentage+'</div>';
  	                return result; 
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "annualCTC",
                  "sorting":false,
                  "render": function ( data, type, opt, meta ) {
                	var result = "";
      	    	  		result = result + ' <div class="tbldata-dotdot-vsm" title="'+opt.annualSalary+'" ><i class="fa fa-inr"style="color:#454545;"></i> '+opt.annualSalary+'</div>';
  	    	  		return result; 
                  }
               } ],
                "scrollCollapse": false,
    	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	    	"bLengthChange": true,
    	    	"bDestroy": true,
    	    	"ordering": true,
    	    	"oLanguage": {
    	     		"sSearch": "",
    	     		 "sZeroRecords": "", "sEmptyTable": "", 
    	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
    	     }
                });
       $("#payrollRegisterTable").wrap("<div class='overflow_Auto width100percnt'></div>")
       $("input[type=search]").hide();
      
}
$("#payrollSalarySearch").keyup(function(e) {
	   if(e.which === 13) {
		   runIndPayrollSalaryRegisterReport();
	   }
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
</script>
 

<jsp:include page="../masterFooter.jsp" />