<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <script type="text/javascript" src='/pms/resources/js/expenseApproveOrReject.js'></script>
 
 <style>
.moveErrorMsgUp{
    position: absolute !important;
    right: -30px !important;
    top: 5px;
}
</style>

<script>
var pmsUrl ='${SourceLeadWeb_pmsUrl}';
</script>
 
<section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>View Expense</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_pmsUrl}/expensesApprovalList"> Expenses ApprovalList</a> &raquo; View Expense</div>
    </section> 
   <div id='reOpenPopup'></div>
    <div id='reOpenRejectedPopup'></div>
    <div class="dashboard_loading" style="display: none;"></div>
    <div class="project_tabs"> 
            <div class="clearfix">
				<div class="project_details project_detailsExp">
					<div class="appStatusWrap">
						  <c:if test="${submissionStatus == 'NEW'}">
						       <b class='appStatusNewcolor'>${submissionStatus}</b>
						  </c:if>
						 
						  <c:if test="${submissionStatus == 'DRAFT' || submissionStatus == 'REOPENED'}">
						       <b class='appStatusDracolor' >${submissionStatus}</b>
						  </c:if>
						   <c:if test="${submissionStatus == 'SUBMITTED'}">
						       <b class='appStatusSubcolor'>${submissionStatus}</b>
						  </c:if>
						   <c:if test="${submissionStatus == 'APPROVED'}">
						       <b class='appStatusAppcolor'>${submissionStatus}</b>
						  </c:if>
						  <c:if test="${submissionStatus == 'REJECTED'}">
						       <b class='appStatusRejcolor pull-left mr'>${submissionStatus}</b>
						        <i class='glyphicon glyphicon-comment rejectedHoverDiv colorRed pull-left positionRelative mt10'>
						        <div class="clearfix mt10"></div>
					            <div class='clearfix submissionRejectedDiv' id='rejectedMessage1${expenses.id}'>${eah.rejectedReason}</div>
					            <br><p title="${expApproverHierarchy.actionPerformedBy}"></p>
						      </i>
						  </c:if>
						</div>
                
					   <ul class="UiPado clearfix">
							<li title="Export">
							<a href="javascript:void(0)" class="overIcon" title="Actions" id="export-pop"></a>
								<div class="newActPop" style="display: none;">
									<span>
									 	<a href="javascript:void(0)" onclick="exportMyExpenses('PDF',${expenses.id},'APPROVER');" title="PDF">Export PDF</a>
										<a href="javascript:void(0)" onclick="exportMyExpenses('EXCEL',${expenses.id},'APPROVER');" title="Excel">Export Excel</a>
									</span>
								</div>							
							</li>
						</ul>
					
					 
					      <sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_EMPLOYEE_EXPENSES\')">
					     <c:if test="${submissionStatus == 'SUBMITTED' || submissionStatus == 'REJECTED' || submissionStatus == 'REOPENED'}">
					        <a href="${SourceLeadWeb_pmsUrl}/addApprovalExpense?expenseId=${expenses.expenseAutoId}&expenseLocationCode=${expenses.expenseLocationCode}&type=edit"  class="editIcon appEditBtn" title="Edit"></a>
					     </c:if>
					     </sec:authorize>
					     
					      <c:if test="${isReopenButtonVisible && isExpenseTotallyApproved && submissionStatus == 'APPROVED' && empty eah.reopenReason}">
						        <c:if test="${!empty eah.reopenRejectedReason}">
						            <i class='glyphicon glyphicon-comment rejectedHover'>
						               <div class='app_error_mes' id="rejectedMessage${expenses.id}">${eah.reopenRejectedReason}</div>
						             </i>
						        </c:if>
					      
						     <div class="timeNewSe pull-right">
						      <b class='proj_withdraw pull-right' onClick='reOpenMyExpense(${expenses.id});'>Re-open</b>
						     </div>
					     </c:if> 
					     
					     <c:if test="${isReopenButtonVisible && isSahReopened && !isSuperUser && !empty eah.reopenReason}">
						      <p class=' ml' style='width: 165px;'>Re-Open Request sent</p>
						      <i class=' glyphicon glyphicon-comment rejectedHover'>
					            <div class='app_error_mes' id="rejectedMessage${expenses.id}">${eah.reopenReason}</div>
						      </i>
						      <!-- <p class='pull-right ml' style='width: 165px;'>Re-Open Request sent<i class='pull-right glyphicon glyphicon-comment text-danger cursorPointer'></i></p> -->
					     </c:if>
					      <c:if test="${isReopenButtonVisible && submissionStatus != 'SUBMITTED' && isSahReopened && isSuperUser && !empty eah.reopenedUser}">
					           <i class='glyphicon glyphicon-comment rejectedHover'>
					            <div class='app_error_mes' id="rejectedMessage${expenses.id}">${eah.reopenReason}</div>
					            </i>
					            <div id ='acceptReopenExpenseBySuperUserDiv' class='pull-right  sm text-center Expreopen'>
					                  <label class='clearfix reopen-label'>RE-OPEN</label>
					                  <i class='reopen-text-success fa fa-check' title='Accept' alt='Accept'  id ='acceptReopenExpenseBySuperUserId' onclick='acceptReopenExpenseBySuperUser(${expenses.id},${eah.reopenedUser.id});'></i>|
					                  <i class='reopen-text-danger glyphicon glyphicon-remove' title='Reject' alt='Reject' id='rejectReopenExpenseBySuperUserId' onclick='rejectReopenExpenseBySuperUser(${expenses.id},${eah.reopenedUser.id});'></i>
					           </div> 
					      </c:if>
					     
						
						<div class="expNewAppBtn">
						<c:if test="${submissionStatus == 'SUBMITTED'}">
							<i class="fa fa-check" title= "Approve" onclick='approveExpenseByExpenseId("${expenses.id}","-1","approval","summary");'></i>
							<i class="fa fa-times" title ="Reject" onclick='rejectExpenseByExpenseId("${expenses.id}","-1","approval","summary");'></i>
							</c:if>
						</div>

                    <div class="clearfix"></div>
					<div class="project_details_cta">
						<strong>Expense Id:</strong><span>${expenses != null ? expenses.expenseAutoId : ExpensesId}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
                  	<strong>Purpose:</strong><span>
	                    <span title="${expenses.expensePurpose}">${expenses.expensePurpose}</span><div class="clearfix"></div>
                 	</div>
					<div class="project_details_cta">
						<strong>Project Name:</strong><span>${expenses.projectName}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Employee:</strong><span>${expenses.projectMemberName}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Client:</strong><span> 
						
						<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">
						 <c:forEach var="project" items="${expEntry.projectsList}">
						      <c:if test="${project.id == expenses.projectId}">
						      <a href="${SourceLeadWeb_vmsUrl}/clientDashboard?orgRelationUUID=${project.orgRelationUUID}">${project.client}</a>
						      </c:if>
		                  </c:forEach>
		                </sec:authorize>  
		                <sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">
						 <c:forEach var="project" items="${expEntry.projectsList}">
						 
						      <c:if test="${project.id == expenses.projectId}">
						      ${project.client}
						      </c:if>
		                  </c:forEach>
		                </sec:authorize>  
		            </span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Start date:</strong><span>${expenses.startDate}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>End date:</strong><span>${expenses.endDate}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>No of Expenses:</strong><span>${expenses.noOfExpenses}</span><div class="clearfix"></div>
					</div>
					
					<div class="project_details_cta">
						<strong>Submitted Amount:</strong><span>${expenses.totalsubmittedAmount}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
                             <strong>Submitted On:</strong><span>${expenses.submittedDate}</span><div class="clearfix"></div>
                      </div>
					
					 <div class="project_details_cta">
					  <c:if test="${submissionStatus == 'APPROVED'}">
					      <strong>Approved Amount:</strong><span>${expenses.totalAmount}</span><div class="clearfix"></div> 	
					    </c:if>
					    </div>
					<div class="project_details_cta">
						<strong>Created By:</strong><span>${expenses.createdBy}</span><div class="clearfix"></div>
					</div>
					
					<div class="project_details_cta">
						<strong>Billing Status:</strong><span>${expenses.billingStatus}</span><div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
				</div>
           
<div class="project_wrap_table expensTableView">
	<table id="categoryTable">
	    <thead>
			<tr id="divEntityTsRow0">
			    <th>Date</th>
				<th>Exp Category</th>
				<th>Description</th>
				<th>Submitted Amount</th>
				<th>Approved Amount</th>
				<th>Currency</th>
				<th>Payment Mode</th>
				<th>Paid By</th>
				 <th>Billable</th>
			</tr>
			
		</thead>	
			<tbody>
			     <c:forEach var="entry" items="${expenses.expenseEntry}" varStatus="entryCount">
			        <tr id="divEntityTsRow${(2*(entryCount.count))-1}">
			           <td><div class="ExpShow" id="categoryDateDiv${entryCount.count}"><i class="fa fa-plus-square-o" aria-hidden="true" onclick="hideAndShowDetailsRow(${2*(entryCount.count)},'Edit');"></i>${entry.dateOccurred}</div></td>
			           <td><div id="categoryTypeDiv${entryCount.count}">${entry.categoryName}</div></td>
			       <td><div id="entryDesDiv${entryCount.count}" class="tbldata-dotdot_50" title="${entry.entryDescription}">${entry.entryDescription}</div></td>
			             <td><div id="expenseSubmittedAmountDiv${entryCount.count}">${entry.submittedamount}</div></td>
		               <c:choose>
		               	<c:when test="${submissionStatus == 'APPROVED'}">
		           			<td><div id="expenseAmountDiv${entryCount.count}">${entry.amountRoundOff}</div></td>    	
		               	</c:when>
		               	<c:otherwise>
		               		<td><div id="expenseAmountDiv${entryCount.count}">---</div></td>
		               	</c:otherwise>
		               </c:choose>
			           <td><div id="expenseRateType${entryCount.count}">${entry.categoryAmountType}</div></td>
			           <td><div id="paymentModeDiv${entryCount.count}">${entry.paymentMode}</div></td>
			           <td><div id="paidByDiv${entryCount.count}">${entry.paidBy}</div></td>
			            <c:if test="${submissionStatus == 'SUBMITTED' || submissionStatus == 'REJECTED' || submissionStatus == 'REOPENED'}">
					        <td><div class=""><input type="checkbox" ${entry.billable ? 'checked' : ''} name="expenses" data-id="${entry.id}" onclick="showPopUp(this,${entry.id});"></div></td>
					    </c:if>
					    
			           
			           
			        </tr>
			        <tr id="divEntityTsRow${2*(entryCount.count)}" style="display:none;">
				        <td colspan="10">
						<div class="expDetails">
							<ul>
							    <li><strong>Date :</strong><span>${entry.dateOccurred}</span><div class="clearfix"></div></li>
								<li><strong>Exp Category :</strong><span>${entry.categoryName}</span><div class="clearfix"></div></li>
								<li><strong>Description :</strong><span title="${entry.entryDescription}">${entry.entryDescription}</span>
								<li><strong>Receipt Issued by :</strong><span title="${entry.receiptIssuedBy}">${entry.receiptIssuedBy}</span><div class="clearfix"></div></li>
								<li><strong>Submitted Amount :</strong><span>${entry.submittedamount}</span><div class="clearfix"></div></li>
								<c:if test="${submissionStatus == 'APPROVED'}">
								<li><strong>Approved Amount :</strong><span>${entry.amountRoundOff}</span><div class="clearfix"></div></li>
								</c:if>
								<li><strong>Currency :</strong><span>${entry.categoryAmountType}</span><div class="clearfix"></div></li>
								<li><strong>Payment Mode :</strong><span>${entry.paymentMode}</span><div class="clearfix"></div></li>
								<li><strong>Paid By :</strong><span>${entry.paidBy}</span><div class="clearfix"></div></li>
								<<c:if test="${entry.billable}">
								<li><strong>Billable :</strong><span>YES</span><div class="clearfix"></div></li>
								</c:if> 
								<div class="clearfix"></div>
								</li>
								<div class="clearfix">
								<div id='attachmentSection' class='col-md-4 noPad mt10'>
				                   <div class="uploadFile timesheet_week_comm edit-Expense-upload">
					                <div id='timesheetDocumentsId' class=''>
					                       <c:forEach var="entryDoc" items="${entry.fileInfo}" varStatus="entryDocCount">
					                        <c:if test = '${entryDoc.status == 1}'>
						                         <strong class='proDown' id='${entryDocCount.index}document${entryCount.count}'>
						                               <a class='downicon' href='${entryDoc.fileURL}' download>
							                                 <i class='glyphicon glyphicon-save'></i>
												             <input type='button' value='${entryDoc.fileName}'/>
												        </a>
											           <a class="fa fa-eye expAttView" onclick="viewFile('${entryDoc.fileURL}','${entryDoc.fileName}','${entryDoc.contentType}');"></a>
						                         </strong>
					                         </c:if>
					                       </c:forEach>
					                 </div> 
					             </div>
					             </div>
					             </div>   				
								<div class="clearfix"></div>
							</ul>
						</div>
					  </td>			
			        </tr>
			     </c:forEach> 
		   </tbody>
	</table>
   </div>
</div>
</div>
    </section>
  <div class="mask_time"></div>
<div class="exp_rejct_pop" id="" style="top: 25%;">
	 <p id="reject2message" class="modal-body text-center">Are you sure <br/> you want to make this expense item as billable ?</p> 
	 <div id="rejectReasonDiv"></div>
	 <a href="javascript:void(0)" class="pull-left" id="changeBillableStatus">Ok</a>
	 <a class="close_color" class="pull-left" id="cancelBillingStatus">Cancel</a>
</div> 

<div id='rejectedPopup' class='time_appr_rejct_pop'></div>


<script>
$("#export-pop").click(function(){
	$(".newActPop").toggle();
});
$("html").click(function() {
    $(".newActPop").hide();
});
$(".newActPop, #export-pop").click(function(e) {
    e.stopPropagation();
});	
</script>
  
<script type="text/javascript">

	$("#changeBillableStatus").click(function () {
		var id = $("#changeBillableStatus").data("id");
		var billableStatus = $("#changeBillableStatus").data("billableStatus");
		changeStatusToBillable(id,billableStatus);
	});
	$("#cancelBillingStatus").click(function() {
		$("#changeBillableStatus").data("id", 0);
	});
	// close popup
	$(document).ready(function() {
	    $(".exp_rejct_pop a").click(function(){
			$(".mask_time").fadeOut(200);
			$(".exp_rejct_pop").slideUp(200);
		});	
	});
	 function showPopUp(element, ExpenseEntriesId){
		var checked = $(element).is(":checked");
		if(checked) {
			$(element).prop("checked", false);
		} else {
			$(element).prop("checked", true);
		}
	    $("#reject2message").html("Are you sure <br/> you want to make this expense item as " + (checked ? "billable ? " : "non-billable ? ")); 
		$("#changeBillableStatus").data("id", ExpenseEntriesId);
		$("#changeBillableStatus").data("billableStatus", checked ? "billable": "non-billable");
		$(".mask_time").fadeIn(200);
		$(".exp_rejct_pop").fadeIn(100);
	} 
</script> 
    
<script>
function viewDocument(title,id){
		window.open(pmsUrl+'/viewExpensesEntryDocument/'+title+'?entryDocumentId='+id,title,"height=600,width=600");
}
</script>     
    
<script>
function exportMyExpenses(type,Expid,ApproverType){
	window.location.href = pmsUrl + "/exportMyExpensesList?&type="+type+"&Expid="+Expid+"&ApproverType="+ApproverType;
}

function performAction(action) {
	sessionStorage.setItem("actionPerformed", action);
	}
</script>

<script>
function changeStatusToBillable(ExpenseEntriesId,billableStatus){
	 $.ajax({
         type : "POST",
         url: pmsUrl+"/changeStatusToBillable?&ExpenseEntriesId="+ExpenseEntriesId+"&billableStatus="+billableStatus,     
          success :function(result) {
			var checkboxes = $("input[name='expenses']");
			$(checkboxes).each(function(){
				if($(this).data("id") === ExpenseEntriesId) {
					if($(this).is(":checked")) {
						$(this).prop("checked", false);
					} else {
						$(this).prop("checked", true);
					}
				}
			});
          }
	
})
}
</script>
