<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>View Expense</h2>
      </div>
      <div class="pull-right brud-crum">Home &raquo; My Expenses &raquo; View Expense</div>
    </section> 
   <div id='reOpenPopup'></div>
    <div id='reOpenRejectedPopup'></div>
    <div class="project_tabs"> 
            <div class="project_tabs_inn_table">
				<div class="project_details project_detailsExp">
                
					<div class="Expdit pull-right">
					     <c:if test="${submissionStatus == 'SUBMITTED'}">
					        <a href="${sourceleadUrl}/addApprovalExpense?expenseId=${expenses.expenseId}&type=edit"  class="btn btn-default ml10" style='cursor: pointer !important'><i class="fa fa-pencil-square-o" ></i> &nbsp;Edit</a>
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
					          <i class='pull-right glyphicon glyphicon-comment rejectedHover'>
					            <div class='app_error_mes' id="rejectedMessage${expenses.id}">${eah.reopenReason}</div>
						      </i>
						      <p class='pull-right ml' style='width: 146px;'>Re-Open Request sent</p>
					     </c:if>
					      <c:if test="${isSahReopened && isSuperUser && !empty eah.reopenedUser}">
					           <i class='pull-right  glyphicon glyphicon-comment rejectedHover'>
					            <div class='app_error_mes' id="rejectedMessage${expenses.id}">${eah.reopenReason}</div>
					            </i>
					            <div class='pull-right  sm text-center Expreopen'>
					                  <label class='clearfix reopen-label'>RE-OPEN</label>
					                  <i class='reopen-text-success fa fa-check' title='Accept' alt='Accept'  onclick='acceptReopenExpenseBySuperUser(${expenses.id},${eah.reopenedUser.id});'></i>|
					                  <i class='reopen-text-danger fa fa-times' title='Reject' alt='Reject' onclick='rejectReopenExpenseBySuperUser(${expenses.id},${eah.reopenedUser.id});'></i>
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
						  </c:if>
						</div>
					</div>                

                    <div class="clearfix"></div>
					<div class="project_details_cta">
						<strong>ExpenseId:</strong><span>${expenses != null ? expenses.expenseId : ExpensesId}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Purpose:</strong><span>${expenses.purpose}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Project Name:</strong><span>${expenses.projectMembers.projects.projectName}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Employee:</strong><span>${expenses.projectMembers.employee.user.fullName}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Client:</strong><span>${expenses.clientName}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Start date:</strong><span>${expenses.startDateStr}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>End date:</strong><span>${expenses.endDateStr}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>No of Expenses:</strong><span>${expenses.noOfExpenses}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Amount:</strong><span>${expenses.totalAmount}</span><div class="clearfix"></div>
					</div>	                    			
					<div class="clearfix"></div>
				</div>
           
<div class="project_wrap_table expensTableView">
	<table id="categoryTable">
	    <thead>
			<tr id="divEntityTsRow0">
				<th>Exp Category</th>
				<th>Date</th>
				<th>Payment Mode</th>
				<th>Currency</th>
				<th>Amount</th>
				<th>Description</th>
			</tr>
		</thead>	
			<tbody>
			     <c:forEach var="entry" items="${expenses.expensesEntries}" varStatus="entryCount">
			        <tr id="divEntityTsRow${(2*(entryCount.count))-1}">
			           <td><div class="ExpShow" id="categoryTypeDiv${entryCount.count}"><i class="fa fa-plus-square-o" aria-hidden="true" onclick="hideAndShowDetailsRow(${2*(entryCount.count)});"></i>${entry.expensesCategoryId.expensesName}</div></td>
			           <td><div id="categoryDateDiv${entryCount.count}">${entry.expenseDateStr}</div></td>
			           <td><div id="paymentModeDiv${entryCount.count}">${entry.paymentMode.value}</div></td>
			           <td><div id="expenseRateType${entryCount.count}">${entry.currencyType}</div></td>
			           <td><div id="expenseAmountDiv${entryCount.count}">${entry.amount}</div></td>
			           <td><div id="entryDesDiv${entryCount.count}" class="tbldata-dotdot_50">${entry.description}</div></td>
			        </tr>
			        <tr id="divEntityTsRow${2*(entryCount.count)}" style="display:none;">
				        <td colspan="6">
						<div class="expDetails">
							<ul>
								<li><strong>Exp Category :</strong><span>${entry.expensesCategoryId.expensesName}</span><div class="clearfix"></div></li>
								<li><strong>Date :</strong><span>${entry.expenseDateStr}</span><div class="clearfix"></div></li>
								<li><strong>Receipt Issued by :</strong><span>${entry.receiptIssuedBy}</span><div class="clearfix"></div></li>
								<li><strong>Payment Mode :</strong><span>${entry.paymentMode.value}</span><div class="clearfix"></div></li>
								<li><strong>Currency :</strong><span>${entry.currencyType}</span><div class="clearfix"></div></li>
								<li><strong>Amount :</strong><span>${entry.amount}</span><div class="clearfix"></div></li>
								<li><strong>Description :</strong><p>${entry.description}</p>
								<div class="clearfix"></div>
								</li>
								<div class="clearfix"></div>
								<div id='attachmentSection' class='col-md-4 mb10'>
				                   <div class="uploadFile timesheet_week_comm">
				                     <input type='hidden' name='dbAttachmentMaxRowNumber${entryCount.count}'  id='dbAttachmentMaxRowNumber${entryCount.count}' value='${entry.expensesEntryDocuments.size()}' />
					                 <input type='hidden' name='attcahmentMaxRowNumber${entryCount.count}'  id='attcahmentMaxRowNumber${entryCount.count}' value='1' />
					                 <div id='timesheetDocumentsId' class=''>
					                       <c:forEach var="entryDoc" items="${entry.expensesEntryDocuments}" varStatus="entryDocCount">
					                         <strong class='proDown' id='${entryDocCount.index}document${entryCount.count}'>
					                          <input type='hidden' name='${entryDocCount.index}DbdocFileId${entryCount.count}' id='${entryDocCount.index}DbdocFileId${entryCount.count}' value='${entryDoc.id}'/>
					                          <input type='hidden' name='${entryDocCount.index}DbdocFileIsDeleted${entryCount.count}' id='${entryDocCount.index}DbdocFileIsDeleted${entryCount.count}' value='N'/>
					                             <a class='downicon' href='<c:url value="/downloadExpensesEntryDocument?entryDocumentId=${entryDoc.id}"/>'>
					                                 <i class='glyphicon glyphicon-save'></i>
										             <input type='button' value='${entryDoc.fileName}'/>
										        </a>
					                         </strong>
					                       </c:forEach>
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