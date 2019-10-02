<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>

<script type="text/javascript">
		window.onload = function () {
			if("" != localStorage.getItem("empExpensesListGrid") && null != localStorage.getItem("empExpensesListGrid")){
				$("#approverDataExpensesDiv").empty();
	       		$("#approverDataExpensesDiv").append(localStorage.getItem("empExpensesListGrid"));
	       		preparePagination(1);
	       		localStorage.setItem("empExpensesListGrid","");
	       		$(":checkbox[value=ALL]").prop("checked",false);
	       		if("reopen" == $("#countType").val()){
	       			$(":checkbox[value=REOPENED]").prop("checked",true);
	       		}else if("submitted" == $("#countType").val()){
	       			$(":checkbox[value=SUBMITTEDFORAPPROVAL]").prop("checked",true);
	       		}else if("approved" == $("#countType").val()){
	       			$(":checkbox[value=APPROVED]").prop("checked",true);
	       		}else if("rejected" == $("#countType").val()){
	       			$(":checkbox[value=REJECTED]").prop("checked",true);
	       		}
	       		$("#temp").val("temp");
	       		$("#appro-pop").hide();
			}else{
				getExpensesApprovalDataList();
				$("#appro-pop").show();
			}
		 };
		 
</script>

<script>
$(document).ready(function(){
	if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	 }
});
</script>
<div id='emptyPopup'></div>
<div id='rejectedPopup' class='time_appr_rejct_pop'></div>
<div id='reOpenPopup'></div>
<div id='reOpenRejectedPopup'></div>
<div class="side_mask"><div class="proj_close"></div></div>	
<div class="time_appr_side_wrap">
	<div class="time_appr_side_wrap_inn">
	 	<div id="timesheetPopUp"></div>
	</div>
</div>	
<div class="mask_time"></div>	


<section class="content-header clearfix">
	<section class="content-wraper">
		<section class="content-header clearfix">
			<div class="pull-left">
				<h2>Expenses Approval</h2>
			</div>
			<div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Expenses Approval</div>
		</section>
		
		<!-- Project -->
		<div class="timesheet_out">
			<div class="vendor_wrap">
			
<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon">
      			<sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES')">
				    <div class="input-group pull-left">  		
					     <span class="dashboard-datepkr pull-left mr5">
						 <input type="text" class="form-control input-sm calendar noMr heiNew" value="" id="filterStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date">
						 <p id="reportStartDate" class="noPad"></p>
						 </span>
						 <span class="dashboard-datepkr pull-left mr5">
						 <input type="text"  class="calendar form-control input-sm noMr heiNew" value="" id="filterEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date">
						 <p id="reportEndDate" class="noPad"></p>
						 </span>
					 	<span class="pull-left"><input type="button" value="Submit" class="newAdBtn" onclick="getExpensesApprovalDataList('dateSearch');" /></span>
					</div>
				</sec:authorize>        
      </span>
      <ul class="pull-right list-unstyled list-inline">
      
               <sec:authorize access="hasAnyRole('ROLE_ADD_EMPLOYEE_EXPENSES')">
			   		<li><a href="/pms/addApprovalExpense" class="newAdBtn mr5">+ Expenses</a></li>
			   </sec:authorize>	   
			
				<sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES')">
					<li><i class="filtIcon"  id="appro-pop"></i>
	                	<div class="approved_pop">
	                    	<ul>
	                        	<li><input id="expenseAll" type="checkbox" name="expenseStatus" value="ALL" checked onclick="getExpensesApprovalDataList();"><label >All</label></li>     <!-- 'dateSearch' -->
								<li><input id="expenseSubmitForApproval" type="checkbox" name="expenseStatus" value ="SUBMITTEDFORAPPROVAL" onclick="getExpensesApprovalDataList();"><label >Sub for Approval</label></li>
								<li><input id="expenseApproved" type="checkbox" name="expenseStatus" value ="APPROVED" onclick="getExpensesApprovalDataList();"><label >Approved</label></li>
								<li><input id="expenseRejected" type="checkbox" name="expenseStatus" value ="REJECTED" onclick="getExpensesApprovalDataList();"><label >Rejected</label></li>
								<li><input id="expenseReopened" type="checkbox" name="expenseStatus" value ="REOPENED" onclick="getExpensesApprovalDataList();"><label >Reopened</label></li>
								<!-- <li><input id="expenseReopned" type="checkbox" name="expenseStatus" value ="REOPENED" onclick="getExpensesApprovalDataList();"><label >Re-Opened</label></li> -->
	                        </ul>
	                    </div>                	
	                </li>        		
               </sec:authorize>			
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
						<sec:authorize access="hasAnyRole('ROLE_EXPORT_EMPLOYEE_EXPENSES')">	
						 	<a href="javascript:void(0)" onclick="exportToPdf('pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="exportToPdf('excel');" title="Excel">Export Excel</a>
						</sec:authorize>
						</span>
					</div>
				</li>
		    
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='SearchExpensesData'/><span class="repSearchIcon" onClick="searchExpensesDataIcon();"></span></li>
		  <input type="hidden" id="temp"/>
        </ul>
      </div>			
				<div class="overflow_inherint">
				<!-- <div class="timesheet_appr_table datatable_filter overflow_inherint"> -->
					<div id="approverDataExpensesDiv">
						<jsp:include page="expensesApprovalListDataTable.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
		<!-- Project -->
	</section>
</section>

<div class="side_mask"><div class="proj_close" onclick="activeTabExpenseApproval();"></div></div>

<div class="time_appr_side_wrap noPad">
	<div id="expensesApprovalViewDiv"></div>
</div>



<script type="text/javascript">
function getExpensesApprovalDataList(dateSearch){
var startDate = '';
var endDate = '';

if(dateSearch != null && dateSearch != '' && dateSearch != 'undefined' && dateSearch == 'dateSearch' ){
	var startDateFilter = $("#filterStartDate").val();
	var endDateFilter = $("#filterEndDate").val();
	if(startDateFilter != null && startDateFilter != '' && startDateFilter !='undefined' &&  endDateFilter != null && endDateFilter != '' && endDateFilter !='undefined' ){
		 startDate = startDateFilter;
		 endDate = endDateFilter;
		$('#reportStartDate').parent().find('p.jquery_form_error_message').remove();
		$('#reportEndDate').parent().find('p.jquery_form_error_message').remove();
	}else if(startDateFilter != null && startDateFilter != '' && startDateFilter !='undefined' &&  (endDateFilter == null || endDateFilter == '' || endDateFilter =='undefined')){
		$('#reportStartDate').parent().find('p.jquery_form_error_message').remove(); 
		$('#reportEndDate').parent().find('p.jquery_form_error_message').remove(); 
		$("#reportEndDate").after("<p class='jquery_form_error_message'><span>End Date Is Required</span>");
	}else if((startDateFilter == null || startDateFilter == ''|| startDateFilter !='undefined') &&  endDateFilter != null && endDateFilter != '' && endDateFilter !='undefined'){
		$('#reportStartDate').parent().find('p.jquery_form_error_message').remove(); 
		$('#reportEndDate').parent().find('p.jquery_form_error_message').remove(); 
		$("#reportStartDate").after("<p class='jquery_form_error_message'><span>Start Date Is Required</span>");
	}else if((endDateFilter == null || endDateFilter == '' || endDateFilter =='undefined') && (startDateFilter == null || startDateFilter == ''|| startDateFilter !='undefined')){
		$('#reportStartDate').parent().find('p.jquery_form_error_message').remove(); 
		$('#reportEndDate').parent().find('p.jquery_form_error_message').remove(); 
	   $("#reportEndDate").after("<p class='jquery_form_error_message'><span>End Date Is Required</span>");
		$("#reportStartDate").after("<p class='jquery_form_error_message'><span>Start Date Is Required</span>");  
		
	}		
}


var searchData = $('#SearchExpensesData').val();


var expenseStatus = '';
var expensesStatusFromDashboard = '${expensesStatusFormDashBoard}';
if(expensesStatusFromDashboard != null && expensesStatusFromDashboard != '' && expensesStatusFromDashboard !='undefined'){
	$(":checkbox[value=ALL]").prop("checked",false);
	$(":checkbox[value="+expensesStatusFromDashboard+"]").prop("checked",true);
		if(expensesStatusFromDashboard === 'SUBMITTED' || expensesStatusFromDashboard === 'submitted'){
			$(":checkbox[value=SUBMITTEDFORAPPROVAL]").prop("checked",true);	
		}else{
			$(":checkbox[value=ALL]").prop("checked",false);
		}
}

if(expenseStatus != null && expenseStatus != '' && expenseStatus !='undefined'){
	  	expenseStatus = expenseStatus.toUpperCase();
	  	$(":checkbox[value="+expenseStatus+"]").prop("checked","true");
}else{
	var expenseStatus = [];
	$('input:checkbox[name=expenseStatus]:checked').each(function() 
	{
		expenseStatus.push($(this).val());
	});
}




var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
 var expensesApprvListDate = getJSDateFormat(locationList);
 
var searchvalue = '';
if(searchData != null && searchData !='' && searchData!='undefined'){
	searchValue = encodeURIComponent(searchData);
}else{
	searchValue = '';
 }
$("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
$.ajax({
    type : "POST",
     url: pmsUrl+"/getExpensesApprovalList?"+tokenParamName+"="+tokenValue+"&expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate+"&searchValue="+searchValue,      
     success :function(data) {
    	 $("#approverDataExpensesDiv").empty(); 
		$("#approverDataExpensesDiv").append(data);
		$("#loadingId").removeAttr('style');
		preparePagination(1);
  }
});
}
</script> 

<script>	
	// close popup
	$(document).ready(function() {
	    $(".timesheet_appr_table .fa-times").click(function(){
			$(".mask_time").fadeIn(200);
			$(".time_appr_rejct_pop").slideToggle(200);
		});
	    $(".time_appr_rejct_pop a").click(function(){
			$(".mask_time").fadeOut(200);
			$(".time_appr_rejct_pop").slideUp(200);
		});	
	});
</script>
   

    
    
 <script>
function activeTabExpenseApproval(){
	$(".project_side_wrap").removeClass("project_side_wrap_active");
    $(".side_mask").fadeOut(200);
    $("body").css('overflow-y', 'scroll');
    $("#expensesApprovalViewDiv").empty();
    $("#expensesApprovalCategoryDiv").empty();
}
</script>   
    
<script>
//  Timesheet approval side bar
$(document).ready(function() {
    $(".timesheet_appr_table a").click(function(){
		$(".time_appr_side_wrap").addClass("time_appr_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .time_cancel").click(function(){
		$(".time_appr_side_wrap").removeClass("time_appr_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});
	
$(document).ready(function() {
    $("#hover").mouseenter(function(){
		$(".app_error_mes").addClass("app_error_mes_active");
	});
    $("#hover").mouseleave(function(){
		$(".app_error_mes").removeClass("app_error_mes_active");
	});	
});
</script>     
    
<script>
// Error pop ExpShow
$(document).ready(function() {
    $(".ExpShow").click(function(){
		/*$(".ExpDe").slideToggle(100); fa-plus-square-o*/
		$(this).parent().parent().next().slideToggle(100);
		$(this).find("i").toggleClass("closExp");
	});
});	
</script>   

    <script>
function pagination(){ 
    $('#example').DataTable({ 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"aaSorting": [[7,'desc']],
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $("#example").wrap("<div class='overflow_Auto'></div>");
}
</script> 
<script>
function exportToPdf(type){
	var temp = $("#tempVar").val();
	 if(temp != null && temp != ''){ 
		 var expenseStatus = $('#expenseStatusDivId').val();
		 if(expenseStatus != null && expenseStatus != '' && expenseStatus !='undefined'){
				expenseStatus = expenseStatus
			}else{
				var expenseStatus = [];
				$('input:checkbox[name=expenseStatus]:checked').each(function() 
				{
					expenseStatus.push($(this).val());
				});	
			}
		var startDate = $("#filterStartDate").val();
		var endDate = $("#filterEndDate").val();
		window.location.href = pmsUrl + "/getExpensesApprovalListForExports?&type="+type+"&expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate;
	 }else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='expensesListDiv' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Expenses List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' class='text-center width100percnt' value='Ok' onclick='expensesCancel();'></b>";
		result = result +"	</div>";
		$("#emptyPopup").append(result);
		$("#expensesListDiv").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	
	} 
	
}
</script>


<script type="text/javascript">
function expensesCancel(){
	$("#emptyPopup").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<script>
  $("#selector").fadeIn(200);
  $('#selector').delay(2000).fadeOut('slow');
	   $("#export-pop").click(function(){
	               $("#exportPop").toggle();
	       });
	   $("html").click(function() {
	       $("#exportPop").hide();
	   });
	   $("#exportPop, #export-pop").click(function(e) {
	       e.stopPropagation();
	   });        
</script> 

<script>
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		var finalDateFormatexpApprvList = getJSDateFormat(locationDate);
		$('#filterStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatexpApprvList,
			showOn: 'focus',
            showButtonPanel: true,
            closeText: 'Clear',
            onClose: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '+1d');
	           // dt.setDate(dt.getDate() + 1);
	            $("#filterEndDate").datepicker("option", "minDate", dt);
				var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
	        }
		
		});
		
		$('#filterEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatexpApprvList,
			showOn: 'focus',
            showButtonPanel: true,
            closeText: 'Clear',
            onClose: function (selected) {
	            //var dt = new Date(selected);
	            var dt = $(this).datepicker('getDate', '-1d');
	           // dt.setDate(dt.getDate() - 1);
	            $("#filterStartDate").datepicker("option", "maxDate", dt);
	            var event = arguments.callee.caller.caller.arguments[0];
                // If "Clear" gets clicked, then really clear it
                if ($(event.delegateTarget).hasClass('ui-datepicker-close')) {
                    $(this).val('');
                }
	        }
		});
		
	});
</script>

<script>

function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
	$(".approved_pop").hide();	
}
$("html").click(function(){
	$(".newActPop, .approved_pop").slideUp(200);
});
$(".overIcon, .newActPop, .approved_pop, #appro-pop").click(function(e){
 	e.stopPropagation();
});	

$("#appro-pop").click(function(){
	$(".newActPop").hide();
	$(".approved_pop").toggle();
	
});

$(document).ready(function() {
	$("#SearchExpensesData").keyup(function(event) {
	    if (event.keyCode === 13) {
	    	$('#SearchExpensesData').parent().find('p.jquery_form_error_message').remove();
	    	var searchData = $('#SearchExpensesData').val();
	    	var regex =  new RegExp(/[\\]/g);
	    	if(regex.test(searchData) == false){
	    		getExpensesApprovalDataList();
	    	}else{
	    		$("#SearchExpensesData").after("<p class='jquery_form_error_message'><span>Enter valid Search Data</span>");
	    	}
	    	$('#SearchExpensesData').val(searchData);
	    }
	});	
});
</script>


<%-- <jsp:include page="expensesApproverTable.jsp" /> --%>
<jsp:include page="../masterFooter.jsp" />