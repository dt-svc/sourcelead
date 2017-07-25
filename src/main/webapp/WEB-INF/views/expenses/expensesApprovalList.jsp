<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript">
		window.onload = function () {
			if("" != localStorage.getItem("empExpensesListGrid") && null != localStorage.getItem("empExpensesListGrid")){
				$("#approverDataExpensesDiv").empty();
	       		$("#approverDataExpensesDiv").append(localStorage.getItem("empExpensesListGrid"));
	       		pagination();
	       		/* localStorage.removeItem("empExpensesListGrid"); */
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
			}else{
				getExpensesApprovalDataList();
			}
		 };
		 
</script>
<style type="text/css">



.glyphicon-none:before {
    content: "\2122";
    color: transparent !important;
}
.timesheet-content {
background: #fff;
min-height: 100px;
clear:both;
display: inline-block;
width:100%;
}
.timesheet-content #example_filter {
position: relative !important;
top:0px !important;
}
.timesheet_appr_table.overflowVisible {
overflow: visible !important;
margin-top: 45px;
}
.timesheet_appr_table.overflow_Visible {
overflow: visible !important;
margin-top: 3px;
}
.time_pop {
 z-index: 99999;
}
</style>
<div id='rejectedPopup'></div>
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
			<div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Expenses Approval</div>
		</section>
		<!-- Project -->
		<div class="timesheet_out">
			<div class="vendor_wrap">
			
			
				<div class="timesheet_head">
							
				<ul>
				<sec:authorize access="hasAnyRole('ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES')">

					<div class="pull-left mt5 mr">   		
				     <span class="dashboard-datepkr pull-left">
					 <input type="text" class="form-control input-sm calendar" value="" id="filterStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date">
					 <p id="reportStartDate" class="ReError"></p>
					 </span>
					 <span class="dashboard-datepkr pull-left">
					 <input type="text"  class="calendar form-control input-sm" value="" id="filterEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date">
					 <p id="reportEndDate" class="ReError"></p>
					 </span>
					 <span class="pull-left"><input type="button" value="Submit" class="btn-sm btn btn-primary" onclick="getExpensesApprovalDataList();" /></span>
					</div>

					<li><i class="fa fa-filter"></i><span>Filter</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
	                	<div class="approved_pop">
	                    	<ul>
	                        	<li><input id="expenseAll" type="checkbox" name="expenseStatus" value="ALL" checked onclick="getExpensesApprovalDataList();"><label >All</label></li>
								<li><input id="expenseSubmitForApproval" type="checkbox" name="expenseStatus" value ="SUBMITTEDFORAPPROVAL" onclick="getExpensesApprovalDataList();"><label >Sub for Approval</label></li>
								<li><input id="expenseApproved" type="checkbox" name="expenseStatus" value ="APPROVED" onclick="getExpensesApprovalDataList();"><label >Approved</label></li>
								<li><input id="expenseRejected" type="checkbox" name="expenseStatus" value ="REJECTED" onclick="getExpensesApprovalDataList();"><label >Rejected</label></li>
								<!-- <li><input id="expenseReopned" type="checkbox" name="expenseStatus" value ="REOPENED" onclick="getExpensesApprovalDataList();"><label >Re-Opened</label></li> -->
	                        </ul>
	                    </div>                	
	                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADD_EMPLOYEE_EXPENSES')">
		   			<li><a href="<c:url value='/addApprovalExpense'/>"><i class="glyphicon glyphicon-plus"></i>&nbsp;Add Expense</a></li>
		   		</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_EXPORT_EMPLOYEE_EXPENSES')">			
					<li title="Export"><a  id="export-pop"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>
					    <div id="exportPop" class="user_exp">
						   <ul class="ul_padding">
						   <li onclick="exportToPdf('pdf');" title="PDF"><i class="fa fa-file-pdf-o"></i>&nbsp; PDF</li>
						   <li onclick="exportToPdf('excel');" title="Excel"><i class="fa fa-file-excel-o"></i>&nbsp; Excel</li>
						   </ul>
						</div>
				   	</li>
		   		</sec:authorize>
		   		<div class="clearfix"></div>
		   		<input type="hidden" id="temp"/>
		   		</ul>
							
					
		
					<!-- <div class="remove_search">
						<input placeholder="search" type="text">
					</div> -->
					<div class="clearfix"></div>
				</div>
				<div class="timesheet_appr_table datatable_filter overflow_inherint">
					<div id="approverDataExpensesDiv"></div>

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
function getExpensesApprovalDataList(){
var sourceleadUrl = '${sourceleadUrl}';
var startDate = $("#filterStartDate").val();
var endDate = $("#filterEndDate").val();
var expenseStatus = [];
$('input:checkbox[name=expenseStatus]:checked').each(function() 
{
	expenseStatus.push($(this).val());
});
$.ajax({
    type : "POST",
     url: sourceleadUrl+"/getExpensesApprovalList?${_csrf.parameterName}=${_csrf.token}&expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate,      
     success :function(data) {
    var result = "";
	result = result+' <table id="example">';
	result = result+' <thead>';
	result = result+' <tr>';
	result = result+' <th>Employee</th>';
	result = result+' <th>EmploymentType</th>';
	result = result+' <th>Project</th>';
	result = result+' <th>Start Date</th>';
	result = result+' <th>End Date</th>';
	result = result+' <th>No of Items</th>';
	result = result+' <th>Amount</th>';
	result = result+' <th>Status</th>';
	result = result+' <th>Action</th>';
	result = result+' </tr>';
	result = result+' </thead>';
	result = result+' <tbody>';
	
	if(data !=''){
	var json = JSON.parse(data);
	 for (var i = 0; i < json.length; i++) { 
	 var data = json[i];
	result = result+' <tr id = "row'+data.expenseId.id+'">';
	result = result+' <td><div class="time_appr_user"><img  id="userImage" onerror="predefineProfileImage(this);" src="'+sourceleadUrl+'/displayUserImage/'+data.expensesUserid+'" alt="Profile Image" class="img-circle img-responsive">';
	result = result+' <span>';
	result = result+' <b title="'+data.expenseEmpUserFirstNameLastName+'">'+data.expenseEmpUserFirstNameLastName+'</b>';
	result = result+' <b title="'+data.expenseEmpUserEmail+'">'+data.expenseEmpUserEmail+'</b>';
	result = result+' <b title="'+data.expensesEmployeeUserId+'">'+data.expensesEmployeeUserId+'</b>';
	result = result+' </span></div>';
	result = result+' </td>';
	if(data.userTypeVendorName != null && data.userTypeVendorName != '' && data.userTypeVendorName !='undefined'){
		result = result+' <td><div><span title="'+data.expenseEmpUserType+'">'+data.expenseEmpUserType+'</span><br><span title="'+data.userTypeVendorName+'">'+data.userTypeVendorName+'</span></div></td>';	
	}else{
		result = result+' <td><div><span title="'+data.expenseEmpUserType+'">'+data.expenseEmpUserType+'</span></div></td>';	
	}
	
	if(data.expenseId.clientName != null && data.expenseId.clientName != '' && data.expenseId.clientName !='undefined'){
		result = result+' <td><div><span title="'+data.expensesProjectName+'">'+data.expensesProjectName+'</span><br><span title="'+data.expenseId.clientName+'">'+data.expenseId.clientName+'</span></div></td>';	
	}else{
		result = result+' <td><div><span title="'+data.expensesProjectName+'">'+data.expensesProjectName+'</span></div></td>';	
	}
	result = result+' <td><div class="sm" title="'+data.expenseStartDateString+'">'+data.expenseStartDateString+'</div></td>';
	
	if(data.expenseEndDateString != null && data.expenseEndDateString != '' && data.expenseEndDateString !='undefined'){
		result = result+' <td><div class="sm" title="'+data.expenseEndDateString+'">'+data.expenseEndDateString+'</div></td>';	
	}else{
		result = result+' <td><div class="sm" title="--">--</div></td>';
	}
	

	result = result+' <td><div class="sm">'+data.expenseId.noOfExpenses+'</div></td>';

	result = result+' <td><div class="sm" title="'+data.expenseId.totalAmount+'">';
	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">'
	result = result+'<a  href="'+sourceleadUrl+'/addApprovalExpense?expenseId='+data.expenseId.expenseId+'&type=view" class="ExpAmount">';
	'</sec:authorize>';
	
	result = result+ data.expenseId.totalAmount;
	
	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">'
	result = result+'</a>';
	'</sec:authorize>';
	result = result+'</div></td>';
	result = result+' <td><div id="status'+data.expenseId.id+'" title="'+data.expenseApprovalStatus+'">'+data.expenseApprovalStatus+'</div></td>';
	
	result = result+' <td>';
	var tdSpanLength = 9;
	 if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus == 'NEW'){
		 result = result+'<div class="sm text-center">--</div>';
     }
     if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus == 'SUBMITTEDFORAPPROVAL'){
        result = result+"<div class='sm text-center'id='action"+data.expenseId.id+"'>"
        '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_EMPLOYEE_EXPENSES\')">' 
        result = result+ " <i class='fa fa-check' title='Approve' alt='Approve'  onclick='approveExpenseByExpenseId(\""+data.expenseId.id+"\","+tdSpanLength+ ",\""+"approval"+"\");'></i>"
        result = result+ "|<i class='fa fa-times' title='Reject'  alt='Rejected' onclick='rejectExpenseByExpenseId(\""+data.expenseId.id+"\","+tdSpanLength+ ",\""+"approval"+"\");'></i>"
        '</sec:authorize>';
        result = result+"</div>"; 
 	                    
    }
    if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus  == 'REJECTED'){
    	  result = result+" <div class='sm text-center'><h3 class='reje_color'>Rejected</h3>";
    	 result = result+"  <i class='glyphicon glyphicon-comment' id='"+data.expenseId.id+"' onclick='rejectedMessage(\"rejectedMessage"+data.expenseId.id+"\");'>";
	 	 result = result+" <div class='app_error_mes' id='rejectedMessage"+data.expenseId.id+"'>"+data.rejectedReason+"</div>";
	 	 result = result+" </i><br>By : "+data.actionPerformedBy;
	 	 result = result+" </div>";
    }
    if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus  == 'APPROVED'){
         result = result+" <div class='sm text-center' title='"+data.actionPerformedBy+"'> <h3>Approved</h3><br>By : "+data.actionPerformedBy+"</div>"; 
    }
    if(data.expenseApprovalStatus !=null && data.expenseApprovalStatus  == 'REOPEN'){
    	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">'
    	result = result+'<a  href="'+sourceleadUrl+'/addApprovalExpense?expenseId='+data.expenseId.expenseId+'&type=view" class="ExpAmount">';
    	'</sec:authorize>'; 
   }

     result = result+' </td>'; 
/* 	 result = result+' <td><div class="sm"><i class="fa fa-check" alt="Approve"></i>|<i class="fa fa-times" alt="Rejected"></i></div></td>';       */        
	 result = result+' </tr>';
	 }
	 $("#temp").val("temp");
	  }else{
	  }
	result = result+' </tbody>';
	result = result+' </table>';
	$("#approverDataExpensesDiv").empty();
	$("#approverDataExpensesDiv").append(result);
	pagination();
  }
});
}
</script>

<script>
function acceptOrRejectReopenExpenseBySuperUser(expenseId,tdSpanSize,from){
		 var msg = $("#reject"+expenseId+"message").val(); 
		 var encodeMsg = msg;
		 if(encodeMsg.match(/^[^\s-](.*)*$/) != null){
			 cancelRejectExpense(expenseId);
			 var encodeMsg = encodeURIComponent(msg);
			 $("#rejectReasonDiv").empty();
			 var sourceleadUrl ='${sourceleadUrl}';
		     var rowHtmlContent =  $("#row"+expenseId).html();
		     
		     var firstName =  '${SourceLeadWeb_user.firstName}';
			 if(firstName == '' || firstName == 'undefined' || firstName == null ){
				firstName = '';
			 }
			 var lastName = '${SourceLeadWeb_user.lastName}';
			 if(lastName == '' || lastName == 'undefined' || lastName == null ){
				  lastName = '';
		     }
			 $("#row"+expenseId).empty();
			 $("#row"+expenseId).html("<td colspan='"+tdSpanSize+"'  id='loading"+expenseId+"'></td>");
		     $("#loading"+expenseId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;'); 
		    $.ajax({
		    type: "POST",
		    url: sourceleadUrl+"/saveExpenseApprovalStatusByExpenseId?${_csrf.parameterName}=${_csrf.token}&expenseId="+expenseId+"&approvalStatus=REJECTED"+"&rejectedReason="+encodeMsg,       
		    success: function(data) {    
		   	   // approvalList();
		       if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
		    	   var table = $('#example').DataTable();
			   		 table.row('#row'+expenseId).remove().draw( false );
			   }
		   	   else{
		   		  	  $("#row"+expenseId).empty();
		 	    	  $("#row"+expenseId).html(rowHtmlContent);
		 	    	  $("#status"+expenseId).html("REJECTED");
		 	    	  $("#action"+expenseId).empty();
		 	    	  $("#action"+expenseId).append("<h3 class='reje_color'>Rejected</h3><i class='glyphicon glyphicon-comment' id='"+expenseId+"' onclick='rejectedMessage(\"rejectedMessage"+expenseId+"\");'><div class='app_error_mes' id='rejectedMessage"+expenseId+"'>"+msg+"</div></i><b class='Rname'>By :"+firstName+" "+lastName+"</b>");
		   	   }
		      }
		    }); 
		 }
		 else{
			 if(msg){
					$("#rejectReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
				}else{
					$("#rejectReasonDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
				}
			}
	    
	   
	}
    $(document).on("mouseover", ".glyphicon-comment", function() { 
		var localId = $(this).attr('id');
		$("#rejectedMessage"+localId).addClass('app_error_mes_active');
		});	
		$(document).on("mouseleave", ".glyphicon-comment", function() { 
		var localId = $(this).attr('id');
		$("#rejectedMessage"+localId).removeClass('app_error_mes_active');
    });	
	function rejectedMessage(local){ 
	   $("#"+local).slideToggle(200);
	   $(".timesheet_appr_table .glyphicon strong, .timesheet_appr_table .glyphicon").click(function(e) {
	       e.stopPropagation();
	   });	
	   
	}
	
	function cancelRejectExpense(expenseId){
			 $(".mask_time").fadeOut(200);
			 $("#reject"+expenseId+"panel").slideUp(200);
	}
	function rejectExpenseByExpenseId(expenseId,tdSpanSize,from){ 
	    $("#rejectReasonDiv").empty();
	    $("#reject"+expenseId+"message").val(''); 
	    var result = "";
	    result = result +'<div class="time_appr_rejct_pop" id="reject'+expenseId+'panel" style="top:25%">';
		result = result +'<textarea placeholder="Enter comment here" id="reject'+expenseId+'message" onkeydown="return (event.keyCode!=13);"></textarea>';
		result = result + ' <div id="rejectReasonDiv"/>';
		result = result +'<a href="#" onClick="acceptOrRejectReopenExpenseBySuperUser(\''+expenseId+'\','+tdSpanSize+',\''+from+'\');" >Submit</a><a class="close_color" href="#" onClick="cancelRejectExpense(\''+expenseId+'\');">Cancel</a>';
		result = result +'</div>';
		result = result +'</div>';
		$("#rejectedPopup").append(result);
	    $(".mask_time").fadeIn(200); 
		$("#reject"+expenseId+"panel").slideToggle(200);
		
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
  function approveExpenseByExpenseId(expenseId,tdSpanSize,from){
		//$("#timesheetApprList").empty();
		var rowHtmlContent =  $("#row"+expenseId).html();
		 var firstName =  '${SourceLeadWeb_user.firstName}';
		 if(firstName == '' || firstName == 'undefined' || firstName == null ){
			firstName = '';
		 }
		 var lastName = '${SourceLeadWeb_user.lastName}';
		 if(lastName == '' || lastName == 'undefined' || lastName == null ){
			  lastName = '';
	     }
		$("#row"+expenseId).empty();
		$("#row"+expenseId).html("<td colspan='"+tdSpanSize+"'  id='loading"+expenseId+"'></td>");
	    $("#loading"+expenseId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; '); 
	    var rejectedReason = '' ;
        var sourceleadUrl ='${sourceleadUrl}';
	     $.ajax({
	    type: "POST",
	    url: sourceleadUrl+"/saveExpenseApprovalStatusByExpenseId?${_csrf.parameterName}=${_csrf.token}&expenseId="+expenseId+"&approvalStatus=APPROVED&rejectedReason="+rejectedReason,        
		    success: function(data) {
	        	 if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
			    	 var table = $('#example').DataTable();
			    	 table.row('#row'+expenseId).remove().draw( false );
	        	 }
	        	 else{
	        		 $("#row"+expenseId).empty();
			    	 $("#row"+expenseId).html(rowHtmlContent);
			    	 $("#status"+expenseId).html("APPROVED");
			    	 $("#action"+expenseId).html("<h3>Approved</h3><b class='Rname'>By :"+firstName+" "+lastName+"</b>"); 
	        	   }
		    	 
		    }
	    });   
}
</script>
    
<script>
function prepareCategoryDivForApprovalPopUp(expenseId){
	 var sourceleadUrl = '${sourceleadUrl}';
	    var locationCode ='${SourceLeadWeb_locationCode}'; 
	 $.ajax({
         type : "POST",
         url: sourceleadUrl+"/getExpensesApprovalCategoryByExpenseId?${_csrf.parameterName}=${_csrf.token}&expenseId="+expenseId+"&locationCode="+locationCode,       
         success :function(data) {
        		var jsonList = JSON.parse(data);
        		var result = '';
        		result = result +'<table>';
        		result = result +'<thead>';
        		result = result +'<tr>';
        		result = result +'<th>Date</th>';
        		result = result +'<th>Exp Category</th>';
        		result = result +'<th>Description</th>';
        		result = result +'<th>Amount</th>';
        		result = result +'<th>Currency</th>';
        		result = result +'<th>Payment Mode</th>';
        		result = result +'</tr>';
        		result = result +'</thead>';	
        		result = result +'<tbody>';
        		for(var i=0;i<jsonList.length;i++){
            	var json = jsonList[i];
        		result = result +'<tr>';
        		if(json.expenseDateString != null && json.expenseDateString !='' && json.expenseDateString !='undefined'){
           		result = result +'<td><div>'+json.expenseDateString+'</div></td>';
           		}else{
           		result = result +'<td><div>--</div></td>';
           		}
        		result = result +'<td><div class="ExpShow"><i class="fa fa-plus-square-o" aria-hidden="true"></i>Category 1</div></td>';
        		if(json.description != null && json.description !='' && json.description !='undefined'){
           		result = result +'<td><div class="tbldata-dotdot-me">'+json.description+'</div></td>';
           		}else{
           		result = result +'<td><div>--</div></td>';	
           		}
           		if(json.amount != null && json.amount !='' && json.amount !='undefined'){
           		result = result +'<td><div>'+json.amount+'</div></td>';
           		}else{
           		result = result +'<td><div>--</div></td>';	
           		}
           		if(json.currencyType != null && json.currencyType !='' && json.currencyType !='undefined'){
           		result = result +'<td><div>'+json.currencyType+'</div></td>';
           		}else{
           		result = result +'<td><div>--</div></td>';	
           		}
        		if(json.paymentMode != null && json.paymentMode !='' && json.paymentMode !='undefined'){
        		result = result +'<td><div>'+json.paymentMode+'</div></td>';
        		}else{
        		result = result +'<td><div>--</div></td>';	
        		}
        		result = result +'</tr>';
        		}
        		result = result +'<tr class="ExpDe">';
        		result = result +'<td colspan="6">';
        		result = result +'<div class="expDetails">';
        		result = result +'<ul>';
        		result = result +'<li><strong>Date :</strong><span> 20/09/2017</span><div class="clearfix"></div></li>';
        		result = result +'<li><strong>Exp Category :</strong><span> Category 1</span><div class="clearfix"></div></li>';
        		result = result +'<li><strong>Amount :</strong><span> 20,000</span><div class="clearfix"></div></li>';
        		result = result +'<li><strong>Purpose :</strong><span> Purpose text</span><div class="clearfix"></div></li>';
        		result = result +'<li><strong>Description :</strong><p> Description Description Description Description</p>';
        		result = result +'<li><strong>Currency :</strong><span> USD</span><div class="clearfix"></div></li>';
        		result = result +'<li><strong>Payment Mode :</strong><span> Cash</span><div class="clearfix"></div></li>';
        		result = result +'<div class="clearfix"></div>';
        		result = result +'</li>';
        		result = result +'<div class="clearfix"></div>';
        		result = result +'</ul>';
        		result = result +'</div>';
        		result = result +'</td>';			
        		result = result +'</tr>';						
        		result = result +'</tbody>';
        		result = result +'</table>';
        		$("#expensesApprovalCategoryDiv").val('');
           	    $("#expensesApprovalCategoryDiv").append(result);
       }
	 });
}    
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
	var temp = $("#temp").val();
	if(temp != null && temp != ''){
		var sourceleadUrl = '${sourceleadUrl}';
		var expenseStatus = [];
		$('input:checkbox[name=expenseStatus]:checked').each(function() 
		{
			expenseStatus.push($(this).val());
		});
		window.location.href = sourceleadUrl + "/exportAllMyExpensesList?&type="+type+"&expenseStatus="+expenseStatus;
	}else{
	}
	var sourceleadUrl = '${sourceleadUrl}';
	var expenseStatus = [];
	$('input:checkbox[name=expenseStatus]:checked').each(function() 
	{
		expenseStatus.push($(this).val());
	});
	var startDate = $("#filterStartDate").val();
	var endDate = $("#filterEndDate").val();
	window.location.href = sourceleadUrl + "/getExpensesApprovalListForExports?&type="+type+"&expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate;
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
		  var finalDateFormatexpApprvList = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormatexpApprvList = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormatexpApprvList = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormatexpApprvList = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormatexpApprvList = 'yy-dd-mm'; 
		  }
		$('#filterStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatexpApprvList,
			onSelect : function(selected) {
				$("#filterEndDate").datepicker("option", "minDate", selected);
			}
		
		});
		
		$('#filterEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatexpApprvList,
			onSelect : function(selected) {
				$("#filterStartDate").datepicker("option", "maxDate", selected);
			}
		});
		
	});
</script>

<jsp:include page="../masterFooter.jsp" />