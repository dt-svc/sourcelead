<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/payrollReports.js"></script>

<script>
$(document).ready(function(){
	getPayGroupList("payGroup");
});
</script>

<!-- Content Area -->
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Direct Deposit</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard'/>" title="Home">Home</a>&raquo;<a
				href="<c:url value='${appPath}/usPayrollReport'/>"> US Payroll Reports List</a>&raquo;Direct Deposit
		</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
    <form:form id="direct_deposit_report_form" name="direct_deposit_report_form" method="post">
    <div class="clearfix minHeight">
        <div class="clearfix row">
          <div class="col-md-5">
            <div class="form-group">
              <label class="col-md-12 paddingRight">Pay Group<span class="text-danger">&nbsp;*</span></label>
				<div class="col-md-12">
					<select class="form-control chosen-select" id="payGroup" name="groupId" field-name="Pay Group" data-validation="required" onchange="getPariodDates();">
                	</select>
				</div>
            </div>
			</div>
            <div class="col-md-5">
             <div class="form-group">
              <label class="col-md-12">Pay Period<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-12">
                <select class="form-control chosen-select" id="payPeriod" name="payPeriod" field-name="Pay Period" data-validation="required">
                </select>
                <input type="hidden" id="freqStartDate" name="freqStartDate"/>
                 <input type="hidden" id="freqEndDate" name="freqEndDate"/>
              </div>
            </div>
       </div>
       
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="getDirectDepositReport();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onclick="resetData();">
          </div>
        </div>
      </div>      
      </div>
        </form:form>
     
     	
     </div>
     
     <div class="clearfix"></div>
      <div class="timesheet_out" id="payrollRegister" style="display: none;">
       
<!--        <div class="clearfix">
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">No.of Check:</label>
                  			<span class="col-md-5" id="noOfChecks">0</span>
                  		</div>
                  	</div>
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Employee Live Checks:</label>
                  			<span class="col-md-5" id="liveChecks">0</span>
                  		</div>
                  	</div>
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Void Checks:</label>
                  			<span class="col-md-5">0</span>
                  		</div>
                  	</div>
                  	<div class="col-md-12">
                  		<div class="row">
                  			<label class="col-md-2">Manual Checks:</label>
                  			<span class="col-md-5">0</span>
                  		</div>
                  	</div>

                  	<div class="clearfix mb mt"></div>
       </div> -->
       
           <div class="styleTables clearfix mt">
        <div class='reportHead'>
		<ul class='pull-right'>
		  <li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
      <div class="newActPop" style="">
       <span><a href="javascript:void(0)" onclick="exportdirectDepositPdf();" title="PDF">Export PDF</a> <!-- <a href="javascript:void(0)" onclick="exportUserData();" title="Excel">Export Excel</a> --> </span> </div>
    </li>
		<li><input type='text' placeholder='Search' class='repSearchBar' id='directDataTableSearch'/><span class='repSearchIcon' onClick='checkDirectDepositReports();'></span></li>
		</ul>
		</div>
		
		<div class="candidate_wrap_table datatable_filter">
	                 <div id="directDepositReportDIV"></div>
	                 <div id="popUp"></div>
	                 <input type="hidden" id="directDepositDataLength" value="0"/>
         </div>
       
       </div>
       </div>
    		
    		
    		
<!--     		
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
       <div class="clearfix"></div>
        <div class="require_wrap_table tableFirstSecond clearfix">
              	<div class="clearfux row mt mb">
              <label class="col-md-4">No. of Employees</label>
              <div class="col-md-2">
              20000
              </div>
            </div>
          <table>
            <thead>
              <tr>
                <th>Employee ID</th>
                <th>Employee Name</th>
                <th>Department</th>
                <th>Account Number</th>
                <th>Amount</th>
                <th>YTD<br/>Amount</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><div class="tblminwidth-75">MIS00414</div></td>
                <td><div class="tbldata-dotdot">Karan Kumar Sahoo</div></td>
                <td><div class="tbldata-dotdot-vsm">Depatment</div></td>
                <td><div class="tbldata-dotdot-vsm">Department Type</div></td>
                <td><div class="tblminwidth-50">Amount</div></td>
                <td><div class="tblminwidth-50">QTD</div></td>
                <td><div class="tblminwidth-50">YTD</div></td>
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
    </div> -->
      <!-- Content Area Ends--> 
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
function getPariodDates(){
	getPayPeriodByGroupId("payGroup","payPeriod","");
}
</script>

<script>
function getDirectDepositReport(){
	if ($('#direct_deposit_report_form').validate(false, validationSettings)){ 
		$("#payrollRegister").show();
		checkDirectDepositReports();
			return true;
	}else{ 
			return false;
	}   
}


function checkDirectDepositReports(){
	    var groupId = $('#payGroup').val();
	    getFreqdates("freqStartDate", "freqEndDate");
	    var  freqStartDate = $("#freqStartDate").val();
	    var freqEndDate = $("#freqEndDate").val();
		var searchValue = $("#directDataTableSearch").val();
		searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getCheckRegisterReports?searchValue="+searchValue+"&groupId="+groupId+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&depositType=Direct Deposit";
		checkDirectDepositReportsList(url);	
}

function checkDirectDepositReportsList(url) {
	$("#directDepositReportDIV").empty();
	var result = "";
	result = result + '<table id="directDepositReportTable" class="display" cellspacing="0" width="100%" >';
	result = result + '<thead>';
	result = result + '<tr>';
	result = result + '<th>Employee Id</th>';
	result = result + '<th>Employee Name</th>';
	result = result + '<th>Department</th>';
	result = result + '<th>Amount</th>';
	result = result + '<th>Total Amount</th>';
	result = result + '</tr>';
	result = result + '</thead>	';
	result = result + "</table>";
	$("#noOfChecks").text(0);
	$("#liveChecks").text(0);
	$("#directDepositReportDIV").append(result);
	directDepositData(url);
	$("#directDepositDataLength").val(0);

}

function directDepositData(url) {
	var colIndex = 0;
	$('#directDepositReportTable')
			.DataTable(
					{
						"processing" : true,
						"serverSide" : true,
						"searching" : false,
						"ajax" : $.fn.dataTable.pipeline({
							url : url,
							method : 'POST',
							pages : 10
						}),
						"columns" : [ {
							"data" : "empName"
						}, {
							"data" : "empId"
						}, {
							"data" : "department"
						}, {
							"data" : "netPay"
						},{
							"data" : "ytdNet"
						}],
						"columnDefs" : [
								{
									"targets" : colIndex++,
									"data" : "empId",
									"sortable": false,
									"render" : function(data, type, opt,meta) {
										$("#directDepositDataLength").val(1);
										var result = "";
										if(opt.employeeId != null && opt.employeeId != '' && opt.employeeId != 'undefined'){
											result = result+ ' <td><div class="tblminwidth-75">'+opt.employeeId+'</div></td>';
										}else{
											result = result+ ' <td><div class="tblminwidth-75">--</div></td>';
										}
										$("#noOfChecks").text(opt.totalChecks);
										$("#liveChecks").text(opt.totalChecks);
										return result;
									}
								},
								{
									"targets" : colIndex++,
									"data" : "empName",
									"render" : function(data, type, opt,meta) {
										var result = "";
										if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
											result = result+ '<td><div class="tblminwidth-75">'+opt.firstName+' '+opt.lastName+'</div></td>';
										}else{
											result = result+ '<td><div class="tblminwidth-75">'+opt.firstName+'</div></td>';
										}
										
										return result;

									}
								},
								{
									"targets" : colIndex++,
									"data" : "department",
									"render" : function(data, type, opt,meta) {
										var result = "";
										if(opt.departmentId != null && opt.departmentId != '' && opt.departmentId != 'undefined'){
											result = result + '<td class="tblminwidth-75"><div>'+opt.departmentId+'</div></td>';
										}else{
											result = result + '<td class="tblminwidth-75"><div>--</div></td>';
										}
										
										return result;
									}
								},
								{
									"targets" : colIndex++,
									"data" : "netPay",
									"render" : function(data, type, opt,meta) {
										var result = "";
										result = result+ '<td><div class="tbldata-dotdot-vsm" title=""><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.netPay+'</div></td>';
										return result;
									}
								},
								{
									"targets" : colIndex++,
									"data" : "netPay",
									"render" : function(data, type, opt,meta) {
										var result = "";
										result = result+ '<td><div class="tbldata-dotdot-vsm" title=""><i class="fa fa-dollar"style="color:#454545;"></i> '+opt.ytdNet+'</div></td>';
										return result;
									}
								}],
						"scrollCollapse" : false,
						"lengthMenu" : [ [ 10, 15, 20 ], [ 10, 15, 20 ] ],
						"aaSorting" : [ [ 3, 'desc' ] ],
						"bLengthChange" : true,
						"bDestroy" : true,
						"ordering" : true,
						"oLanguage" : {
							"sSearch" : "",
							"sZeroRecords" : "",
							"sEmptyTable" : "",
							"sProcessing" : "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
						}
					});
	$("#directDepositReportTable").wrap(
			"<div style='overflow-x: auto; overflow-y: hidden;'></div>");
	$("input[type=search]").hide();
	$("#directDataTableSearch").keyup(function(e) {
		if (e.which === 13) {
			checkDirectDepositReports();
		}
	});
	
}


</script>

 <script>
 function resetData(){
	 $("#payrollRegister").hide();
	 $("#payGroup").val('');
	 $("#payGroup").trigger("chosen:updated"); 
	 $("#payPeriod").empty();
	 $("#payPeriod").trigger("chosen:updated"); 
 }
 </script>
 
 <script>
 function exportdirectDepositPdf(){
	 var groupId = $('#payGroup').val();
	 var searchValue = $("#directDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
     getFreqdates("freqStartDate", "freqEndDate");
     var  freqStartDate = $("#freqStartDate").val();
     var freqEndDate = $("#freqEndDate").val();
     
     var directDepositLength = $("#directDepositDataLength").val();
     
     if(directDepositLength > 0){
    	 window.location.href = sourceleadUrl+"/exportToPdfCheckRegisterReport?"+tokenParamName+"="+tokenValue+"&groupId="+groupId+"&freqStartDate="+freqStartDate+"&freqEndDate="+freqEndDate+"&searchValue="+searchValue+"&depositType=Direct Deposit";
     }else{
    	 
    	 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='direct_deposit_popup' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Direct Deposit List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelList();' style='width : 100%'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#direct_deposit_popup").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
    	 
     }
	 
 }
 
 function cancelList(){
 	$("#popUp").empty();
 	$(".project_inn_mask").fadeOut(200);
 }
 
 </script>
 
 
<jsp:include page="../masterFooter.jsp" />