	<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
$(document).ready(function(){
   getDepartmentList();
});
</script>
<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>PF Report </h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/indPayrollReportList'/>">India Payroll Reports List</a> &raquo; PF Report</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    <div id="payrollReportLoading"></div>
    <form:form id="payrollregister_form" name="apply_leave_form" method="post">
    <div class="clearfix minHeight">
        <div class="clearfix row">
        <div class="col-md-5">
          <div class="form-group">
              <label class="col-md-12 paddingRight">Financial Year<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
					<select class="form-control sel" id="financialYear" name="financialYear" onchange="selectFromMonths();">
					   <option value ="">Select Year</option>
				       <c:forEach var="year" items="${finYearList}">
					       <option value="${year}">${year}</option>
			            </c:forEach>
					</select>
				</div>
            </div>
            
             <div class="form-group">
              <label class="col-md-12">From Month<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                  <select class="form-control chosen-select" id="fromMonth" name="fromMonth" onchange="selectToMonths();">
				 </select>
              </div>
            </div>
        </div>
        <div class="col-md-5">
          <div class="form-group">
              <label class="col-md-12 paddingRight">Select Department<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
					<select class="form-control sel" id="payrollDepartment" name="departmentId" >
					</select>
				</div>
            </div>
            
              <label class="col-md-12">To Month<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                  <select class="form-control chosen-select" id="toMonth" name="toMonth">
				 </select>
              </div>
            </div>
			</div>
            
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onclick="runIndPayrollESIReport();">
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
       					<span><a onclick="payrollPFExport('pdf');" title="PDF">Export PDF</a>
       					 <a title="Excel" onclick="payrollPFExport('excel');">Export Excel</a></span> </div>
   				</li>
			    <li class="">
			      <input class="repSearchBar" placeholder="Search" id="payrollPFSearch" type="text">
			      <span class="repSearchIcon" onclick="runIndPayrollTYDReport();"></span>
			    </li>
  			</ul>
		</div>
        <div class="require_wrap_table datatable_filter">
        	<div id="payrollPFSummaryTableDiv"></div>
        </div>
      </div>
    </div>
     		
      <!-- Content Area Ends overflow_inherint--> 
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

</script>       
<script>
function resetForm(){
	 $("#payrollRegister").hide();
	 $("#financialYear").val('');
	 $("#payrollDepartment").val('');
	 $("#payrollDepartment").trigger("chosen:updated"); 
	 
	 $("#fromMonth").val('');
	 $("#fromMonth").trigger("chosen:updated"); 
	 
	 $("#toMonth").val('');
	 $("#toMonth").trigger("chosen:updated"); 
}
</script>

<script>
function payrollPFExport(type){
	var searchValue = $("#payrollPFSearch").val();
	var department = $('#payrollDepartment').val();
	var financialYear = $("#financialYear").val();
	var fromMonth = $('#fromMonth').val();
	var toMonth = $('#toMonth').val();
    window.location.href = sourceleadUrl+"/indPayrollPFExport?&departmentId="+department+"&financialYear="+financialYear+"&fromMonth="+fromMonth+"&toMonth="+toMonth+"&type=PF&searchValue="+searchValue+"&exportType="+type;
}
</script>


<script type="text/javascript">
function runIndPayrollESIReport(){
	var searchValue = $("#payrollPFSearch").val();
	var department = $('#payrollDepartment').val();
	var financialYear = $("#financialYear").val();
	var fromMonth = $('#fromMonth').val();
	var toMonth = $('#toMonth').val();
	if(department != null && department != '' && department != 'undefined' && fromMonth != null && fromMonth != '' && fromMonth != 'undefined' && financialYear != null && financialYear != "" && financialYear !="undefined" && toMonth != null && toMonth != "" && toMonth != "undefined"){
		 $("#payrollReportLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var url = sourceleadUrl+ "/indPayrollESIAndPFReport?${_csrf.parameterName}=${_csrf.token}&departmentId="+department+"&financialYear="+financialYear+"&fromMonth="+fromMonth+"&toMonth="+toMonth+"&type=PF&searchValue="+searchValue;
	 	$("#payrollRegister").show();
		preparePayRollPFList(url);
		$("#payrollPFSearch").val(''); 
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Please select Department,Financial Year,From Month and To Month</p>";
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
function preparePayRollPFList(url){
	 $("#payrollReportLoading").removeAttr("style");
	 $("#payrollPFSummaryTableDiv").empty();
	 var result = "";
		result = result +" <div class='remove_search'>";
		result = result +"</div>";
		result = result + '<table id="payrollPFTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Associate Id</th>';
       result = result + '<th>Associate Name</th>';
       result = result + '<th>Department</th>';
       result = result + '<th>PF Number</th>';
       result = result + '<th>UAN Number</th>';
       result = result + '<th>Associate Contribution</th>';
       result = result + '<th>Employer Contribution</th>';
       result = result + '<th>Total</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
       result = result +"</table>";
    $("#payrollPFSummaryTableDiv").append(result);
    payrollESIPagination(url); 
}
function payrollESIPagination(url){
	var colIndex = 0;
    $('#payrollPFTable').DataTable( { 
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
            { "data": "pfNumber"},
            { "data": "uanNumber"},
            { "data": "associateContribution"},
            { "data": "employerContribution" },
            { "data": "total"},
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
		          },{
            "targets": colIndex++,
            "data": "pfNumber",
            "render": function ( data, type, opt, meta ) {
            	 var result = "";
            	 if(opt.pfNumber !=null && opt.pfNumber != '' && opt.pfNumber !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.pfNumber+'" >'+opt.pfNumber+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
            	return result; 
            }
          },{
              "targets": colIndex++,
              "data": "uanNumber",
              "render": function ( data, type, opt, meta ) {
              	 var result = "";
              	 if(opt.uanNumber !=null && opt.uanNumber != '' && opt.uanNumber !='undefined'){
  	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.uanNumber+'" >'+opt.uanNumber+'</div>';
  	        		}else{
  	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
  	        		}
              	return result; 
              }
            }, {
              "targets": colIndex++,
              "data": "associateContribution",
              "sorting":false,
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
   	    	  		result = result + '<div class="tbldata-dotdot-vsm" title="'+opt.employeePf+'" > <i class="fa fa-inr"style="color:#454545;"></i> '+opt.employeePf+'</div>';
            	    return result; 
              }
            },{
	    	  	"targets": colIndex++,
	    	  	"data": "employerContribution",
	    	  	"sorting":false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.employerPf+'" > <i class="fa fa-inr"style="color:#454545;"></i> '+opt.employerPf+'</div>';
  	                return result; 
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "total",
                  "sorting":false,
                  "render": function ( data, type, opt, meta ) {
                	var result = "";
      	    	  		result = result + ' <div class="tbldata-dotdot-vsm" title="'+opt.totalPF+'" ><i class="fa fa-inr"style="color:#454545;"></i> '+opt.totalPF+'</div>';
  	    	  		return result; 
                  }
               }],
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
       $("#payrollPFTable").wrap("<div class='overflow_Auto width100percnt'></div>")
       $("input[type=search]").hide();
      
}
$("#payrollPFSearch").keyup(function(e) {
	   if(e.which === 13) {
		   runIndPayrollESIReport();
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
 

 
 <script>
 function selectFromMonths(){
	  
	 var financialYear = $("#financialYear").val();
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getFromMonthNames?financialYear="+financialYear, 
	        success: function(json) {
	        var output = "";
			for (var i=0; i<json.length; i++)
			{
				var opt = json[i];
				output = output + "<option value='" +opt+ "'>"+opt+"</option>";
			}
			$("#fromMonth").empty();
			$("#fromMonth").append(output);
			$('#fromMonth').trigger("chosen:updated"); 
			selectToMonths();
	        },
	    });	
	 
 }
 
 function selectToMonths(){
	 var financialYear = $("#financialYear").val();
	 var fromMonth = $("#fromMonth").val();
	 $.ajax({
	    	type: "POST",
	        url: sourceleadUrl+"/getToMonthNames?financialYear="+financialYear+"&fromMonth="+fromMonth, 
	        success: function(json) {
	        var output = "";
			for (var i=0; i<json.length; i++)
			{
				var opt = json[i];
				output = output + "<option value='" +opt+ "'>"+opt+"</option>";
			}
			$("#toMonth").empty();
			$("#toMonth").append(output);
			$('#toMonth').trigger("chosen:updated"); 
	        },
	    });	
	 
 }
 </script>
<jsp:include page="../masterFooter.jsp" />