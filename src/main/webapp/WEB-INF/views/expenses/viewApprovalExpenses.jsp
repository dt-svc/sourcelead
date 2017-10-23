<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 <style>
.moveErrorMsgUp{
    position: absolute !important;
    right: -30px !important;
    top: 5px;
}
</style>
 
<section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>View Expense</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/expensesApprovalList'/>">Expenses Approval</a> &raquo; View Expense</div>
    </section> 
   <div id='reOpenPopup'></div>
    <div id='reOpenRejectedPopup'></div>
    <div class="project_tabs"> 
            <div class="project_tabs_inn_table">
				<div class="project_details project_detailsExp">
                
                
					<div class="Expdit pull-right mr">
					
					     <c:if test="${submissionStatus == 'SUBMITTED' || submissionStatus == 'REJECTED'}">
					        <a href="${sourceleadUrl}/addApprovalExpense?expenseId=${expenses.expenseAutoId}&expenseLocationCode=${expenses.expenseLocationCode}&type=edit"  class="btn btn-default ml10 mr10" style='cursor: pointer !important'><i class="fa fa-pencil-square-o" ></i> &nbsp;Edit</a>
					     </c:if>
					     
					      <c:if test="${isExpenseTotallyApproved && submissionStatus == 'APPROVED' && empty eah.reopenReason}">
						        <c:if test="${!empty eah.reopenRejectedReason}">
						            <i class='glyphicon glyphicon-comment rejectedHover'>
						               <div class='app_error_mes' id="rejectedMessage${expenses.id}">${eah.reopenRejectedReason}</div>
						             </i>
						        </c:if>
					      
						     <div class="timeNewSe pull-right">
						      <b class='proj_withdraw pull-right' onClick='reOpenMyExpense(${expenses.id});'>Re-open</b>
						     </div>
					     </c:if> 
					     
					     <c:if test="${isSahReopened && !isSuperUser && !empty eah.reopenReason}">
					          <i class='pull-right glyphicon glyphicon-comment rejectedHover moveErrorMsgUp'>
					            <div class='app_error_mes' id="rejectedMessage${expenses.id}">${eah.reopenReason}</div>
						      </i>
						      <p class='pull-right ml' style='width: 146px;'>Re-Open Request sent</p>
					     </c:if>
					      <c:if test="${submissionStatus != 'SUBMITTED' && isSahReopened && isSuperUser && !empty eah.reopenedUser}">
					           <i class='pull-right  glyphicon glyphicon-comment rejectedHover'>
					            <div class='app_error_mes' id="rejectedMessage${expenses.id}">${eah.reopenReason}</div>
					            </i>
					            <div id ='acceptReopenExpenseBySuperUserDiv' class='pull-right  sm text-center Expreopen'>
					                  <label class='clearfix reopen-label'>RE-OPEN</label>
					                  <i class='reopen-text-success fa fa-check' title='Accept' alt='Accept'  id ='acceptReopenExpenseBySuperUserId' onclick='acceptReopenExpenseBySuperUser(${expenses.id},${eah.reopenedUser.id});'></i>|
					                  <i class='reopen-text-danger fa fa-times' title='Reject' alt='Reject' id='rejectReopenExpenseBySuperUserId' onclick='rejectReopenExpenseBySuperUser(${expenses.id},${eah.reopenedUser.id});'></i>
					           </div> 
					      </c:if>
					     
					     <div class="timeNewSe pull-right">
						  <c:if test="${submissionStatus == 'NEW'}">
						       <b class='proj_new pull-right'>${submissionStatus}</b>
						  </c:if>
						  <c:if test="${submissionStatus == 'DRAFT'}">
						       <b class='proj_draft pull-right' >${submissionStatus}</b>
						  </c:if>
						   <c:if test="${submissionStatus == 'SUBMITTED'}">
						       <b class='proj_process pull-right' style='width: 90% !important'>${submissionStatus}</b>
						  </c:if>
						   <c:if test="${submissionStatus == 'APPROVED'}">
						       <b class='proj_active pull-right'>${submissionStatus}</b>
						  </c:if>
						  <c:if test="${submissionStatus == 'REJECTED'}">
						       <b class='proj_closed pull-right'>${submissionStatus}</b>
						        <i class='pull-right glyphicon glyphicon-comment rejectedHover moveErrorMsgUp'>
					            <div class='app_error_mes' id="rejectedMessage1${expenses.id}">${eah.rejectedReason}</div>
						      </i>
						  </c:if>
						</div>
					</div>                

					   <ul class="UiPado">
							<li title="Export"><i class="glyphicon glyphicon-export"
								id="export-pop"></i>
								<div class="export_pop" style="display: none;">
									<ul>
										<li onclick="exportMyExpenses('PDF',${expenses.id},'APPROVER');"><i class="fa fa-file-pdf-o"></i>&nbsp;PDF</li>
										<li onclick="exportMyExpenses('EXCEL',${expenses.id},'APPROVER');"><i class="fa fa-file-excel-o"></i>&nbsp;Excel</li>
									</ul>
								</div></li>
							<div class="clearfix"></div>
						</ul>

                    <div class="clearfix"></div>
					<div class="project_details_cta">
						<strong>Expense Id:</strong><span>${expenses != null ? expenses.expenseAutoId : ExpensesId}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Purpose:</strong><span>${expenses.expensePurpose}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Project Name:</strong><span>${expenses.projectName}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Employee:</strong><span>${expenses.projectMemberName}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Client:</strong><span> 
						 <c:forEach var="project" items="${expEntry.projectsList}">
						      <c:if test="${project.id == expenses.projectId}">
						      ${project.client}
						      </c:if>
		                  </c:forEach>
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
						<strong>Amount:</strong><span>${expenses.expenseAmountDivId}</span><div class="clearfix"></div>
					</div>
					
					<div class="project_details_cta">
						<strong>Exp Created By:</strong><span>${expenses.createdBy}</span><div class="clearfix"></div>
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
				<th>Amount</th>
				<th>Currency</th>
				<th>Payment Mode</th>
				<th>Paid By</th>
			</tr>
		</thead>	
			<tbody>
			     <c:forEach var="entry" items="${expenses.expenseEntry}" varStatus="entryCount">
			        <tr id="divEntityTsRow${(2*(entryCount.count))-1}">
			           <td><div class="ExpShow" id="categoryDateDiv${entryCount.count}"><i class="fa fa-plus-square-o" aria-hidden="true" onclick="hideAndShowDetailsRow(${2*(entryCount.count)},'Edit');"></i>${entry.dateOccurred}</div></td>
			           <td><div id="categoryTypeDiv${entryCount.count}">${entry.categoryName}</div></td>
			           <td><div id="entryDesDiv${entryCount.count}" class="tbldata-dotdot_50" title="${entry.entryDescription}">${entry.entryDescription}</div></td>
			           <td><div id="expenseAmountDiv${entryCount.count}">${entry.amountRoundOff}</div></td>
			           <td><div id="expenseRateType${entryCount.count}">${entry.categoryAmountType}</div></td>
			           <td><div id="paymentModeDiv${entryCount.count}">${entry.paymentMode}</div></td>
			           <td><div id="paidByDiv${entryCount.count}">${entry.paidBy}</div></td>
			        </tr>
			        <tr id="divEntityTsRow${2*(entryCount.count)}" style="display:none;">
				        <td colspan="7">
						<div class="expDetails">
							<ul>
							    <li><strong>Date :</strong><span>${entry.dateOccurred}</span><div class="clearfix"></div></li>
								<li><strong>Exp Category :</strong><span>${entry.categoryName}</span><div class="clearfix"></div></li>
								<li><strong>Description :</strong><span title="${entry.entryDescription}">${entry.entryDescription}</span>
								<li><strong>Receipt Issued by :</strong><span title="${entry.receiptIssuedBy}">${entry.receiptIssuedBy}</span><div class="clearfix"></div></li>
								<li><strong>Amount :</strong><span>${entry.amountRoundOff}</span><div class="clearfix"></div></li>
								<li><strong>Currency :</strong><span>${entry.categoryAmountType}</span><div class="clearfix"></div></li>
								<li><strong>Payment Mode :</strong><span>${entry.paymentMode}</span><div class="clearfix"></div></li>
								<li><strong>Paid By :</strong><span>${entry.paidBy}</span><div class="clearfix"></div></li>
								<div class="clearfix"></div>
								</li>
								<div class="clearfix">
								<div id='attachmentSection' class='col-md-4 noPad mt10'>
				                   <div class="uploadFile timesheet_week_comm">
					                <div id='timesheetDocumentsId' class=''>
					                       <c:forEach var="entryDoc" items="${entry.fileInfo}" varStatus="entryDocCount">
					                        <c:if test = '${entryDoc.status == 1}'>
						                         <strong class='proDown' id='${entryDocCount.index}document${entryCount.count}'>
						                               <a class='downicon' href='${entryDoc.fileURL}'>
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
    
<script>
function viewDocument(title,id){
		window.open(sourceleadUrl+'/viewExpensesEntryDocument/'+title+'?entryDocumentId='+id,title,"height=600,width=600");
}
</script>     
    
<script>
function exportMyExpenses(type,Expid,ApproverType){
	window.location.href = sourceleadUrl + "/exportMyExpensesList?&type="+type+"&Expid="+Expid+"&ApproverType="+ApproverType;
}
</script>