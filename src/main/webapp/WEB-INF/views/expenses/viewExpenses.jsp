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
      
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='/expenses'/>">My Expenses</a> &raquo; View Expense</div>
    </section>  
    <div class="project_tabs"> 
            <div class="project_tabs_inn_table">
				<div class="project_details project_detailsExp">
                
					<div class="Expdit pull-right">
					     <c:if test="${submissionStatus == 'DRAFT' || submissionStatus == 'REJECTED'}">
					        <a href="${sourceleadUrl}/addExpense?expenseId=${expenses.expenseId}&type=edit"  class="btn btn-default mr10" style='cursor: pointer !important'><i class="fa fa-pencil-square-o" ></i> &nbsp;Edit</a>
					     </c:if>
					     
					     <c:if test="${!isSahApproved && submissionStatus == 'SUBMITTED'}">
						     <div class="timeNewSe pull-right" style="margin: 0px;">
						      <b class='proj_withdraw pull-right' onClick='withdrawMyExpense(${expenses.id});'>Modify</b>
						     </div>
					     </c:if>
					     
					     <div class="timeNewSe pull-right" >
						  <c:if test="${submissionStatus == 'NEW'}">
						       <b class='proj_new pull-right'>${submissionStatus}</b>
						  </c:if>
						  <c:if test="${submissionStatus == 'DRAFT'}">
						       <b class='proj_draft pull-right' >${submissionStatus}</b>
						  </c:if>
						   <c:if test="${submissionStatus == 'SUBMITTED'}">
						       <b class='proj_process pull-right' >${submissionStatus}</b>
						  </c:if>
						   <c:if test="${submissionStatus == 'APPROVED'}">
						       <b class='proj_active pull-right'>${submissionStatus}</b>
						  </c:if>
						  <c:if test="${submissionStatus == 'REJECTED'}">
						        <b class='proj_closed pull-right'>${submissionStatus}</b>
						        <i class='pull-right glyphicon glyphicon-comment rejectedHover'>
					            <div class='app_error_mes' id="rejectedMessage1${expenses.id}">${expenseApproverHierarchy.rejectedReason}</div>
						      </i>
						  </c:if>
						</div>
				</div>                
					<ul class="UiPado">
						<li title="Export"><i class="glyphicon glyphicon-export"
							id="export-pop"></i>
							<div class="export_pop" style="display: none;">
								<ul>
									<li onclick="exportMyExpenses('PDF',${expenses.id},'USER');"><i class="fa fa-file-pdf-o"></i>&nbsp;PDF</li>
									<li onclick="exportMyExpenses('EXCEL',${expenses.id},'USER');"><i class="fa fa-file-excel-o"></i>&nbsp;Excel</li>
								</ul>
							</div></li>
						<div class="clearfix"></div>
					</ul>

                    <div class="clearfix"></div>
					<div class="project_details_cta">
						<strong>ExpenseId:</strong><span>${expenses != null ? expenses.expenseId : ExpensesId}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Purpose:</strong><span>${expenses.purpose}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Project Name:</strong><span>${expenses.projectName}</span><div class="clearfix"></div>
					</div>
					<div class="project_details_cta">
						<strong>Client:</strong><span>${expenses.clientName}</span><div class="clearfix"></div>
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
						<strong>Amount:</strong><span>${expenses.totalAmount}</span><div class="clearfix"></div>
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
			</tr>
		</thead>	
			<tbody>
			     <c:forEach var="entry" items="${expenses.expensesEntries}" varStatus="entryCount">
			        <tr id="divEntityTsRow${(2*(entryCount.count))-1}">
			           <td><div class="ExpShow" id="categoryDateDiv${entryCount.count}"><i class="fa fa-plus-square-o" aria-hidden="true" onclick="hideAndShowDetailsRow(${2*(entryCount.count)},'Edit');"></i>${entry.expenseDate}</div></td>
			           <td><div id="categoryTypeDiv${entryCount.count}">${entry.expensesCategoryId.expensesName}</div></td>
			           <td><div id="entryDesDiv${entryCount.count}" class="tbldata-dotdot_50" title="${entry.description}">${entry.description}</div></td>
			           <td><div id="expenseAmountDiv${entryCount.count}">${entry.amount}</div></td>
			           <td><div id="expenseRateType${entryCount.count}">${entry.currencyType}</div></td>
			           <td><div id="paymentModeDiv${entryCount.count}">${entry.paymentMode.value}</div></td>
			        </tr>
			        <tr id="divEntityTsRow${2*(entryCount.count)}" style="display:none;">
				        <td colspan="6">
						<div class="expDetails">
							<ul>
							    <li><strong>Date :</strong><span>${entry.expenseDate}</span><div class="clearfix"></div></li>
								<li><strong>Exp Category :</strong><span>${entry.expensesCategoryId.expensesName}</span><div class="clearfix"></div></li>
								<li><strong>Description :</strong><p title="${entry.description}">${entry.description}</p>
								<li><strong>Receipt Issued by :</strong><span title="${entry.receiptIssuedBy}">${entry.receiptIssuedBy}</span><div class="clearfix"></div></li>
								<li><strong>Amount :</strong><span>${entry.amount}</span><div class="clearfix"></div></li>
								<li><strong>Currency :</strong><span>${entry.currencyType}</span><div class="clearfix"></div></li>
								<li><strong>Payment Mode :</strong><span>${entry.paymentMode.value}</span><div class="clearfix"></div></li>
								<div class="clearfix"></div>
								</li>
								<div class="clearfix"></div>
								<div id='attachmentSection' class='col-md-4 noPad'>
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
										        <a class="fa fa-eye expAttView" onclick="viewDocument('${entryDoc.fileName}','${entryDoc.id}');"></a>
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

<script>
function viewDocument(title,id){
	var sourceleadUrl ='${sourceleadUrl}';
		window.open(sourceleadUrl+'/viewExpensesEntryDocument/'+title+'?entryDocumentId='+id,title,"height=600,width=600");
}
</script>    
    
<script>
function exportMyExpenses(type,Expid,ApproverType){
	var sourceleadUrl = '${sourceleadUrl}';
	window.location.href = sourceleadUrl + "/exportMyExpensesList?&type="+type+"&Expid="+Expid+"&ApproverType="+ApproverType;
}
</script>