 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<section class="content-wraper" id="themeroller">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>View Expense</h2>
      </div>
      
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/expenses'/>">My Expenses</a> &raquo; View Expense</div>
    </section>  
    <div class="project_tabs"> 
            <div class="project_tabs_inn_table">
				<div class="project_details project_detailsExp">
                
					<div class="Expdit pull-right">
					     <c:if test="${submissionStatus == 'DRAFT' || submissionStatus == 'REJECTED' || submissionStatus == 'REOPENED'}">
					        <a href="${sourceleadUrl}/addExpense?expenseId=${expenses.expenseAutoId}&expenseLocationCode=${expenses.expenseLocationCode}&type=edit"  class="btn btn-default mr10" style='cursor: pointer !important'><i class="fa fa-pencil-square-o" ></i> &nbsp;Edit</a>
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
						   <c:if test="${submissionStatus == 'REOPENED'}">
						       <b class='proj_draft pull-right' >${submissionStatus}</b>
						  </c:if>
						   <c:if test="${submissionStatus == 'SUBMITTED'}">
						       <b class='proj_process pull-right' >${submissionStatus}</b>
						  </c:if>
						   <c:if test="${submissionStatus == 'APPROVED'}">
						       <b class='proj_active pull-right'>${submissionStatus}</b>
						  </c:if>
						  <c:if test="${submissionStatus == 'REJECTED'}">
						        <i class='pull-right glyphicon glyphicon-comment rejectedHover moveErrorMsgUp colB'>
					            <div class='app_error_mes' id="rejectedMessage1${expenses.id}">${expenseApproverHierarchy}</div>
						      </i>
						      <b class='proj_closed pull-right' style="width:75% !important;">${submissionStatus}</b>
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
						<strong>ExpenseId:</strong><span>${expenses != null ? expenses.expenseAutoId : ExpensesId}</span><div class="clearfix"></div>
					</div>
					 <div class="project_details_cta">
						<strong>Purpose:</strong><span>
							<span title="${expenses.expensePurpose}">${expenses.expensePurpose}</span><div class="clearfix"></div>
					</div> 
					<div class="project_details_cta">
						<strong>Project Name:</strong><span>${expenses.projectName}</span><div class="clearfix"></div>
					</div>
					 <div class="project_details_cta">
						<strong>Client:</strong><span> 
						
						<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\')">
						 <c:forEach var="pm" items="${expEntry.projectMembersList}">
						  <c:if test="${pm.projectId == expenses.projectId}">
						   <a href="<c:url value="/app/clientDashboard?orgRelationUUID=${pm.orgRelationUUID}"/>"> ${pm.clientName}</a>
						  </c:if>
						</c:forEach>
						</sec:authorize>
						
						<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\')">
						 <c:forEach var="pm" items="${expEntry.projectMembersList}">
						  <c:if test="${pm.projectId == expenses.projectId}">
						    ${pm.clientName}
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
					  <c:if test="${submissionStatus != 'DRAFT' }">
					<div class="project_details_cta">
						<strong>Submitted On:</strong><span>${expenses.submittedDate}</span><div class="clearfix"></div>
					</div>
					 </c:if>
					
					  <c:if test="${submissionStatus == 'APPROVED'}">
					   <div class="project_details_cta">
					      <strong>Approved Amount:</strong><span>${expenses.totalAmount}</span><div class="clearfix"></div> 	
					    </div>
					  </c:if>
					
					<div class="project_details_cta">
						<strong>Created By:</strong><span>${expenses.createdBy}</span><div class="clearfix"></div>
					</div>
					<c:if test="${expenses.billingStatus != null}">
					<div class="project_details_cta">
						<strong>Billing Status:</strong><span>${expenses.billingStatus}</span><div class="clearfix"></div>
					</div>
					</c:if>
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
			</tr>
		</thead>	
			<tbody>
			
			
			
			
			
			<c:forEach var="entry" items="${expenses.expenseEntry}" varStatus="entryCount">
			        <tr id="divEntityTsRow${(2*(entryCount.count))-1}">
			           <td><div class="ExpShow" id="categoryDateDiv${entryCount.count}"><i class="fa fa-plus-square-o" aria-hidden="true" onclick ="hideAndShowDetailsRow(${2*(entryCount.count)},'Edit');"></i>${entry.dateOccurred}</div></td>
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
			
			
			   <%--   <c:forEach var="entry" items="${expenses.expenseEntry}" varStatus="entryCount">
			        <tr id="divEntityTsRow${(2*(entryCount.count))-1}">
			           <td><div class="ExpShow" id="categoryDateDiv${entryCount.count}"><i class="fa fa-plus-square-o" aria-hidden="true" onclick="hideAndShowDetailsRow(${2*(entryCount.count)},'Edit');"></i>${entry.dateOccurred}</div></td>
			           <td><div id="categoryTypeDiv${entryCount.count}">${entry.categoryName}</div></td>
			           <td><div id="entryDesDiv${entryCount.count}" class="tbldata-dotdot_50" title="${entry.entryDescription}">${entry.entryDescription}</div></td>
			           <td><div id="expenseAmountDiv${entryCount.count}">${entry.submittedamount}</div></td>
			           
			              <c:choose>
		               	<c:when test="${submissionStatus == 'APPROVED'}">
		           			<td><div id="expenseAmountDiv${entryCount.count}">${entry.amount}</div></td>    	
		               	</c:when>
		               	<c:otherwise>
		               		<td><div id="expenseAmountDiv${entryCount.count}">---</div></td>
		               	</c:otherwise>
		               </c:choose>
			         
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
								<li><strong>Amount :</strong><span>${entry.submittedamount}</span><div class="clearfix"></div></li>
								<li><strong>Currency :</strong><span>${entry.currencyType}</span><div class="clearfix"></div></li>
								<li><strong>Payment Mode :</strong><span>${entry.paymentMode}</span><div class="clearfix"></div></li>
								<li><strong>Paid By :</strong><span>${entry.paidBy}</span><div class="clearfix"></div></li>
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
			     </c:forEach>  --%>
		   </tbody>
	</table>
   </div>
</div>
</div>
    </section>
    
    <script>
$("#export-pop").click(function(){
	$(".export_pop").toggle();
});
$("html").click(function() {
    $(".export_pop").slideUp();
});
$(".export_pop, #export-pop").click(function(e) {
    e.stopPropagation();
});	
</script>

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

<script>
function withdrawMyExpense(id){
	$("#loadTime").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
	$.ajax({
	      type : "POST",
   	       url: sourceleadUrl+"/withDrawExpense?"+tokenParamName+"="+tokenValue+"&expenseId="+'${expenses.id}',		
	      success :function(result) {
	    	  location.replace(sourceleadUrl+"/addExpense?expenseId="+'${expenses.expenseAutoId}'+"&expenseLocationCode="+'${expenses.expenseLocationCode}'+"&type=view"); 
	      }
 }); 
}
</script>