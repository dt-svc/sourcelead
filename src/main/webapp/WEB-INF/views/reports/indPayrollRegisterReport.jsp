<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="/resources/js/indPayrollReports.js"></script>

<script>
$(document).ready(function(){
   getIndPayGroupList("payGroup");
   getDepartments("payrollDepartment");
});
</script>
<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Payroll Register</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/indPayrollReportList'/>">India Payroll Reports List</a> &raquo;  Payroll Register</div>
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
            <div class="form-group">
              <label class="col-md-12">Payroll Type</label>
              <div class="col-md-12">
                <select class="form-control sel" id="payRollType" name="payRollType">
					    <option>Select Type</option>
						<option selected>Regular</option>
						<option>Additional</option>
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
             <div class="form-group">
              <label class="col-md-12">Department</label>
              <div class="col-md-12">
                <select class="form-control chosen-select" multiple id="payrollDepartment" name="department">
					</select>
              </div>
            </div>
        </div>
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onclick="runIndPayrollregisterReport();">
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
				<li>
				<a class="overIcon" title="Actions" onclick="newActions();"></a>
      				<div class="newActPop" style="display: block;">
       					<span><a onclick="exportPayrollRegisterReportPdf();" title="PDF">Export PDF</a> <!-- <a title="Excel">Export Excel</a> --> </span> </div>
   				</li>
			    <li class="">
			      <input class="repSearchBar" placeholder="Search" id="payrollRegisterSearch" type="text">
			      <span class="repSearchIcon" onclick="runIndPayrollregisterReport();"></span>
			    </li>
  			</ul>
		</div>
        <div class="require_wrap_table datatable_filter">
        	<div id="payrollRegisterTableDiv"></div>
        </div>
      </div>
    </div>
     		
      <!-- Content Area Ends--> 
    </div>
    
  </section>
  

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
function exportPayrollRegisterReportPdf(){
	var searchValue = $("#payrollRegisterSearch").val();
	var payGroup = $('#payGroup').val();
	var department = $('#payrollDepartment').val();
	var payRollType = $('#payRollType').val();
	var freqStartDate = $('#freqStartDate').val();
	var freqEndDate = $('#freqEndDate').val();
    window.location.href = sourceleadUrl+"/exportIndPayrollRegisterReportPdf?&payGroup="+payGroup+"&department="+department+"&payRollType="+payRollType+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&searchValue="+searchValue;
}
</script>
<script type="text/javascript">
function runIndPayrollregisterReport(){
	var searchValue = $("#payrollRegisterSearch").val();
	var payGroup = $('#payGroup').val();
	var department = $('#payrollDepartment').val();
	var payRollType = $('#payRollType').val();
	var freqStartDate = $('#freqStartDate').val();
	var freqEndDate = $('#freqEndDate').val();
	if(payGroup != null && payGroup != '' && payGroup != 'undefined' && freqStartDate != null && freqStartDate != '' && freqStartDate != 'undefined' && freqEndDate != null && freqEndDate != '' && freqEndDate != 'undefined'){
		$("#payrollReportLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		var url = sourceleadUrl+ "/runIndPayrollregister?${_csrf.parameterName}=${_csrf.token}&payGroup="+payGroup+"&department="+department+"&payRollType="+payRollType+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&searchValue="+searchValue;
		$("#payrollRegister").show();
		preparePayRollRegisterList(url);
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
function preparePayRollRegisterList(url){
	 $("#payrollReportLoading").removeAttr("style");
	 $("#payrollRegisterTableDiv").empty();
	 var result = "";
		result = result +" <div class='remove_search'>";
		result = result +"</div>";
		result = result + '<table id="payrollRegisterTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee ID</th>';
        result = result + '<th>Employee Name</th>';
        result = result + '<th>Department</th>';
        result = result + '<th>Current Hours</th>';
        result = result + '<th>Gross Pay</th>';
        result = result + '<th>YTD Taxes</th>';
        result = result + '<th>Deductions</th>';
        result = result + '<th>Net Pay</th>';
        result = result + '<th>YTD Net Pay</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
        result = result +"</table>";
     $("#payrollRegisterTableDiv").append(result);
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
            { "data": "regularHours" },
            { "data": "grossPay" },
            { "data": "ytdTaxes"},
            { "data": "deductions" },
            { "data": "netPay" },
            { "data": "ytdNetPay" },
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "empId",
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
		    	  		if(opt.employeeid !=null && opt.employeeid != '' && opt.employeeid !='undefined'){
		    	  			result = result + '     <div class="tblminwidth-75" title="'+opt.employeeid+'" >'+opt.employeeid+'</div>';
		        		}else{
		        			result = result + '     <div class="tblminwidth-75">---</div>';
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
              "data": "regularHours",
              "sorting":false,
              "render": function ( data, type, opt, meta ) {
            	   var result = "";
	    	  		result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.currentHours+'" >'+opt.currentHours+'</div>';
            	    return result; 
              }
            },{
	    	  	"targets": colIndex++,
	    	  	"data": "grossPay",
	    	  	"sorting":false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		var result = "";
	    	  		if(opt.grossPay !=null && opt.grossPay != '' && opt.grossPay !='undefined'){
	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.grossPay+'" ><i class="fa fa-inr"style="color:#454545;"></i> '+opt.grossPay+'</div>';
	        		}else{
	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
	        		}
  	                return result; 
	    	  	}
	    	  }, {
                  "targets": colIndex++,
                  "data": "ytdTaxes",
                  "sorting":false,
                  "render": function ( data, type, opt, meta ) {
                	var result = "";
  	    	  		result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.ytdTaxes+'" ><i class="fa fa-inr"style="color:#454545;"></i> '+opt.ytdTaxes+'</div>';
  	    	  		return result; 
                  }
               }, {
                    "targets": colIndex++,
                    "data": "deductions",
                    "sorting":false,
                    "render": function ( data, type, opt, meta ) {
                  		var result = "";
    	    	  		result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.deducationAmount+'" ><i class="fa fa-inr"style="color:#454545;"></i> '+opt.deducationAmount+'</div>';
    	    	  		return result; 
                    }
                }, {
                      "targets": colIndex++,
                      "data": "netPay",
                      "sorting":false,
                      "render": function ( data, type, opt, meta ) {
                   		var result = "";
      	    	  		if(opt.netPay !=null && opt.netPay != '' && opt.netPay !='undefined'){
      	    	  			result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.netPay+'" ><i class="fa fa-inr"style="color:#454545;"></i> '+opt.netPay+'</div>';
      	        		}else{
      	        			result = result + '     <div class="tbldata-dotdot-vsm" title="" >---</div>';
      	        		}
      	    	  		return result; 
                      }
                }, {
                    "targets": colIndex++,
                    "data": "ytdNetPay",
                    "sorting":false,
                    "render": function ( data, type, opt, meta ) {
                 		var result = "";
    	    	  		result = result + '     <div class="tbldata-dotdot-vsm" title="'+opt.ytdNetPay+'" ><i class="fa fa-inr"style="color:#454545;"></i> '+opt.ytdNetPay+'</div>';
    	    	  		return result; 
                    }
                }],
                "scrollCollapse": false,
    	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
//     	    	"aaSorting": [[1,'desc']],
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
       $("#payrollRegisterSearch").keyup(function(e) {
       	   if(e.which === 13) {
       		runIndPayrollregisterReport();
       	   }
       	});
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
 

<jsp:include page="../masterFooter.jsp" />