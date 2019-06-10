<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>


<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Contract Employee Payreport</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/usPayrollReport">US Payroll Reports List</a> &raquo; Contract Employee Pay Report</div>
    </section>
    <div class="contentareaInner-styles clearfix">
      <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">From Date</label>
              <div class="col-md-12">
                <input type="text" id="fromDate" name="fromDate" class="form-control calendar">
              </div>
            </div>
          </div>
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12">To Date</label>
              <div class="col-md-12">
				  <input type="text" id="toDate" name="toDate" class="form-control calendar">
              </div>
            </div>
          </div>
        </div>
        <div class="clearfix footerstyelsNew">
          <div class="col-lg-12">
            <div class="pull-right">
              <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="getContractEmployeeReport();">
              <input value="Reset" class="btn btn-defualt ml10" type="button" onClick="resetData();">
            </div>
          </div>
        </div>
      </div>
      <div id="emptyPopup"></div>
      <div class="clearfix"></div>
      <div id="payrollReportLoading"></div>
      <div class="timesheet_out" id="contractEmployeeReportDiv" style="display: none;">
       
        <div class="clearfix">
          <div class="positionRelative reportHead clearfix"> <span class="pull-left refreIcon"><a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a></span>
            <ul class="pull-right list-unstyled list-inline">
              <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
                <div class="newActPop" style=""> <span><a onclick="exportPayrollRegisterReportPdf();" title="PDF">Export PDF</a> <a onclick="exportPayrollRegisterReportExcel();" title="Excel">Export Excel</a> </span> </div>
              </li>
              <li class="">
                <input class="repSearchBar" placeholder="Search" id="payrollRegisterSearch" type="text">
                <span class="repSearchIcon" onclick="getContractEmployeeReport();"></span></li>
            </ul>
          </div>
          <div class="require_wrap_table tableFirstSecond clearfix datatable_filter">
            <div id="conEmpReportTableDiv"></div>
          </div>
        </div>
      </div>
      
      <!-- Content Area Ends--> 
    </div>
  </section>

<script>
var table="";
function resetForm(){
	 $("#contractEmployeeReportDiv").hide();
	 $("#payGroup").val('');
// 	 $("#payGroup").trigger("chosen:updated"); 
	 $("#payPeriod").val('');
// 	 $("#payPeriod").trigger("chosen:updated"); 
	 $("#payRollType").val('');
	 $("#payrollDepartment").val('');
	 $("#payrollDepartment").trigger("chosen:updated"); 
}
</script>
<script>
function exportPayrollRegisterReportExcel(){
	var searchValue = $("#payrollRegisterSearch").val();
	var freqStartDate = $('#fromDate').val();
	var freqEndDate = $('#toDate').val();
	 if (!table.data().count()) {
		  popupDisplay();
	 }else{
		  if(freqStartDate != null && freqStartDate != '' && freqStartDate != 'undefined' && freqEndDate != null && freqEndDate != '' && freqEndDate != 'undefined'){
			window.location.href = pmsUrl+ "/getContractEmployeeReportExport?${_csrf.parameterName}=${_csrf.token}&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&exportType=excel&searchValue="+searchValue;
		  }
	   }
}
</script>
<script>
function exportPayrollRegisterReportPdf(){
	var searchValue = $("#payrollRegisterSearch").val();
	var freqStartDate = $('#fromDate').val();
	var freqEndDate = $('#toDate').val();
	  if (!table.data().count()) {
		  popupDisplay();
	  }else{
          if(freqStartDate != null && freqStartDate != '' && freqStartDate != 'undefined' && freqEndDate != null && freqEndDate != '' && freqEndDate != 'undefined'){
	           window.location.href = pmsUrl+ "/getContractEmployeeReportExport?${_csrf.parameterName}=${_csrf.token}&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&exportType=pdf&searchValue="+searchValue;
          }		 
       }
}
</script>
<script type="text/javascript">
function getContractEmployeeReport(){
	var searchValue = $("#payrollRegisterSearch").val();
	var freqStartDate = $('#fromDate').val();
	var freqEndDate = $('#toDate').val();
	if(freqStartDate != null && freqStartDate != '' && freqStartDate != 'undefined' && freqEndDate != null && freqEndDate != '' && freqEndDate != 'undefined'){
		$("#payrollReportLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var url = pmsUrl+ "/getContractEmployeeReportList?${_csrf.parameterName}=${_csrf.token}&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&searchValue="+searchValue;
		$("#contractEmployeeReportDiv").show();
		getContractEmployeeReportList(url);
	}
}
function removePopUp(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script type="text/javascript">
function getContractEmployeeReportList(url){
	 $("#payrollReportLoading").removeAttr("style");
	 $("#conEmpReportTableDiv").empty();
	 var result = "";
		result = result +" <div class='remove_search'>";
		result = result +"</div>";
		result = result + '<table id="conEmpReportTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Associate ID</th>';
        result = result + '<th>Associate Name</th>';
        result = result + '<th>Vendor</th>';
        result = result + '<th>Pay Date</th>';
        result = result + '<th>Invoice Id</th>';
        result = result + '<th>Amount</th>';
        result = result + '<th>Paid Amount</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
     $("#conEmpReportTableDiv").append(result);
     conEmpReportPagination(url); 
}

function conEmpReportPagination(url){
	var colIndex = 0;
	 table = $('#conEmpReportTable').DataTable( { 
    	"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
            { "data": "assId" },
            { "data": "assName"},
            { "data": "vendor"},
            { "data": "payDate" },
            { "data": "invoiceId" },
            { "data": "amount" },
            { "data": "paidAmount"},
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "assId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
		    	  		if(opt.empId !=null && opt.empId != '' && opt.empId !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.empId+'" >'+opt.empId+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
		        		}
		              return result; 
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "assName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.empName !=null && opt.empName != '' && opt.empName !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.empName+'" >'+opt.empName+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
            	  return result; 
              }
	          },{
            "targets": colIndex++,
            "data": "vendor",
            "render": function ( data, type, opt, meta ) {
            	 var result = "";
            	 if(opt.vendorName !=null && opt.vendorName != '' && opt.vendorName !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.vendorName+'" >'+opt.vendorName+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
            	return result; 
            }
          }, {
              "targets": colIndex++,
              "data": "payDate",
              "sorting":false,
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
            	   if(opt.payCheckDate !=null && opt.payCheckDate != '' && opt.payCheckDate !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.payCheckDate+'" >'+opt.payCheckDate+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
            	    return result; 
              }
            },{
	    	  	"targets": colIndex++,
	    	  	"data": "invoiceId",
	    	  	"sorting":false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.invoiceId !=null && opt.invoiceId != '' && opt.invoiceId !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.invoiceId+'" >'+opt.invoiceId+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
  	                return result; 
	    	  	}
	    	  },{
	    	  	"targets": colIndex++,
	    	  	"data": "amount",
	    	  	"sorting":false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.assAmount !=null && opt.assAmount != '' && opt.assAmount !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.assAmount+'" >'+opt.assAmount+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
	    	  		return result; 
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "paidAmount",
                  "sorting":false,
                  "render": function ( data, type, opt, meta ) {
                	var result = "";
  	    	  		if(opt.invPaidAmount !=null && opt.invPaidAmount != '' && opt.invPaidAmount !='undefined'){
  	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.invPaidAmount+'" >'+opt.invPaidAmount+'</div>';
  	        		}else{
  	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
  	        		}
  	    	  		return result; 
                  }
               }],
                "scrollCollapse": false,
    	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
//     	    	"aaSorting": [[1,'desc']],
    	    	"bLengthChange": true,
    	    	"bDestroy": true,
    	    	"ordering": false,
    	    	"oLanguage": {
    	     		"sSearch": "",
    	     		 "sZeroRecords": "", "sEmptyTable": "", 
    	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
    	     }
                });
    	$("#payrollRegisterSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getContractEmployeeReport();
    	   }
    	});
       $("#conEmpReportTable").wrap("<div class='overflow_Auto width100percnt'></div>")
}
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
	$(function() {
		 var locationDate ='${SourceLeadWeb_dateFormat}'; 
		  var finalDateFormatEndDate = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatEndDate = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatEndDate = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatEndDate = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatEndDate = 'yy-dd-mm'; 
		  }
		//to set from date to current month
	       var currentTime = new Date()
	       var minDate = new Date(currentTime.getFullYear(), currentTime.getMonth(), +1);
		$('#fromDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true,
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '+1d');
			dt.setDate(dt.getDate());
			$("#toDate").datepicker("option", "minDate", dt);
			}
		});
		
	  $('#toDate').datepicker({
			dateFormat : finalDateFormatEndDate,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
			var dt = $(this).datepicker('getDate', '-1d');
			dt.setDate(dt.getDate());
			$("#fromdate").datepicker("option", "maxDate", dt);
			}
		});
	});
</script>

<script>
function resetData(){
	 $("#contractEmployeeReportDiv").hide();
	 $("#fromDate").val('');
	 $('#toDate').attr('value', '');
		$('#toDate').datepicker( "option" , {
	        minDate: null,
	        maxDate: null} );
	 $("#toDate").val('');
	 $("#payrollRegisterSearch").val('');
}
</script>

<script>
 function resetFilter(){
 	 $("#payrollRegisterSearch").val('');
 	getContractEmployeeReport();
 }
</script>

<script>
  function popupDisplay(){
		$(".project_inn_mask").remove();
		var result = "";
		result = result +" <div class='project_inn_mask'></div>";
		result = result +" <div id='remove_ContractEmployeePayreport_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +" <div class='modal-body'><p id='popupMsg'></p></div>";
		result = result +" <div class='clearfix'><input class='u_can_color width100percnt' type='button'  value='Ok' onClick='cancelContractEmployeePayreportPopup();'></div>";
		result = result +" </div>";
		$("#emptyPopup").empty().append(result);
		$("#popupMsg").empty().append("No Records To Export in Contract Employee Payreport.");
		$("#remove_ContractEmployeePayreport_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
  
  function cancelContractEmployeePayreportPopup(){
		$("#remove_ContractEmployeePayreport_pop").fadeOut(200);
		$(".project_inn_mask").fadeOut(200);
	}
</script>

 -->

<jsp:include page="../masterFooter.jsp" />