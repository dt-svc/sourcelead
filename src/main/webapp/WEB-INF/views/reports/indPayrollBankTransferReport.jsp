<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="/resources/js/indPayrollReports.js"></script>

<script>
$(document).ready(function(){
   getIndPayGroupList("payGroup");
});
</script>
<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll Bank Transfer</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/indPayrollReportList">India Payroll Reports List</a> &raquo;  Payroll Bank Transfer</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    <div id="payrollReportLoading"></div>
    <form:form id="payrollregister_form" name="apply_leave_form" method="post">
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Pay Group<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
				
					<select class="form-control sel" id="payGroup" name="payGroup" onchange="getPayPeriodByGroupId('payGroup','payPeriod')">
					</select>
				</div>
            </div>
             
			</div>
            <div class="col-md-5">
             <div class="form-group">
              <label class="col-md-12">Pay Period<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                <select class="form-control sel" id="payPeriod" name="payPeriod" onchange="getFreqdates('freqStartDate','freqEndDate');">
					</select>
					<input type="hidden" name="freqStartDate" id="freqStartDate" value=""/>
					<input type="hidden" name="freqEndDate" id="freqEndDate" value=""/>
              </div>
            </div>
              
        </div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onclick="runIndPayrollBankTransferReport();">
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
       					<span><a onclick="payrollBankTransferExport('pdf');" title="PDF">Export PDF</a>
       					 <a title="Excel" onclick="payrollBankTransferExport('excel');">Export Excel</a></span> </div>
   				</li>
			    <li class="">
			      <input class="repSearchBar" placeholder="Search" id="payrollRegisterSearch" type="text">
			      <span class="repSearchIcon" onclick="runIndPayrollBankTransferReport();"></span>
			    </li>
  			</ul>
		</div>
        <div class="require_wrap_table datatable_filter">
        	<div id="payrollBankTransferTableDiv"></div>
        </div>
      </div>
    </div>
     		
      <!-- Content Area Ends--> 
    </div>
    
  </section>
        
<script>
function resetForm(){
	 $("#payrollRegister").hide();
	 $("#payGroup").val('');
	 $("#payPeriod").empty();
	 $("#payrollDepartment").val('');
	 $("#payrollRegisterSearch").val('');
	 $("#payrollDepartment").trigger("chosen:updated"); 
}
</script>

<script>
function payrollBankTransferExport(type){
	var searchValue = $("#payrollRegisterSearch").val();
	var payGroup = $('#payGroup').val();
	var freqStartDate = $('#freqStartDate').val();
	var freqEndDate = $('#freqEndDate').val();
    window.location.href = sourceleadUrl+"/indPayrollBankTransferReports?&payGroup="+payGroup+"&exportType="+type+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&searchValue="+searchValue;
}
</script>
<script type="text/javascript">
function runIndPayrollBankTransferReport(){
// 	$("#payrollRegisterSearch").val('');
	var searchValue = $("#payrollRegisterSearch").val();
	var payGroup = $('#payGroup').val();
	var freqStartDate = $('#freqStartDate').val();
	var freqEndDate = $('#freqEndDate').val();
	if(payGroup != null && payGroup != '' && payGroup != 'undefined' && freqStartDate != null && freqStartDate != '' && freqStartDate != 'undefined' && freqEndDate != null && freqEndDate != '' && freqEndDate != 'undefined'){
		$("#payrollReportLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var url = sourceleadUrl+ "/indPayrollBankTranseferReport?${_csrf.parameterName}=${_csrf.token}&payGroup="+payGroup+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&searchValue="+searchValue;
		$("#payrollRegister").show();
		preparePayRollBankTransferList(url);
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Please select PayGroup and PayPeriod</p>";
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
function preparePayRollBankTransferList(url){
	 $("#payrollReportLoading").removeAttr("style");
	 $("#payrollBankTransferTableDiv").empty();
	 var result = "";
		result = result +" <div class='remove_search'>";
		result = result +"</div>";
		result = result + '<table id="payrollRegisterTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee ID</th>';
        result = result + '<th>Employee Name</th>';
        result = result + '<th>Department</th>';
        result = result + '<th>Account Number</th>';
        result = result + '<th>Bank Name</th>';
        result = result + '<th>IFSC Code</th>';
        result = result + '<th>Amount</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
     $("#payrollBankTransferTableDiv").append(result);
     payrollRegisterPagination(url); 
}

function payrollRegisterPagination(url){
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
            { "data": "accountNumber" },
            { "data": "bankName" },
            { "data": "ifscCode"},
            { "data": "amount" },
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
              "data": "accountNumber",
              "sorting":false,
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
            	   if(opt.accountNumber != null && opt.accountNumber != "" && opt.accountNumber != "undefined"){
   	    	  		result = result + '<div class="tbldata-dotdot-vsm" title="'+opt.accountNumber+'" >'+opt.accountNumber+'</div>';
            	   }else{
   	    	  		result = result + '<div class="tbldata-dotdot-vsm">--</div>';
            	   }
            	    return result; 
              }
            },{
	    	  	"targets": colIndex++,
	    	  	"data": "bankName",
	    	  	"sorting":false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.bankName !=null && opt.bankName != '' && opt.bankName !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.bankName+'" >'+opt.bankName+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
  	                return result; 
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "ifscCode",
                  "sorting":false,
                  "render": function ( data, type, opt, meta ) {
                	var result = "";
                	if(opt.ifscCode != null && opt.ifscCode != "" && opt.ifscCode != "undefined"){
      	    	  		result = result + ' <div class="tbldata-dotdot-vsm" title="'+opt.ifscCode+'" >'+opt.ifscCode+'</div>';
                	}else{
      	    	  		result = result + ' <div class="tbldata-dotdot-vsm">--</div>';
                	}
  	    	  		return result; 
                  }
               }, {
                    "targets": colIndex++,
                    "data": "amount",
                    "sorting":false,
                    "render": function ( data, type, opt, meta ) {
                  		var result = "";
    	    	  		result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.netPay+'" ><i class="fa fa-inr"style="color:#454545;"></i> '+opt.netPay+'</div>';
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
$("#payrollRegisterSearch").keyup(function(e) {
	   if(e.which === 13) {
		runIndPayrollBankTransferReport();
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