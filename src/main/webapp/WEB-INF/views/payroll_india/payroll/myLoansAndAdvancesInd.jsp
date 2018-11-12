<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 
<div class="tablelist-wrap">
			<div class="col-md-12">
				<ul class="list-unstyled mt row">
					<li class="col-md-3" title="Outstanding" onclick="getMyLoansAdvList('Outstanding');">
						<div class="cardModel-widget text-center cursorPointer ${loanStatus == 'Outstanding' ? 'currentActive' : ''}" id="outstandingWidget">
							<h5 class="margin-top0">Outstanding</h5>
							<p class="text-muted mb0">
								<!-- <i class="fa fa-rupee paddingRight"></i><span>1000</span> -->
							</p>
						</div>
					</li>
					<li class="col-md-3" title="Completed" onclick="getMyLoansAdvList('Completed');">
						<div class="cardModel-widget text-center cursorPointer ${loanStatus == 'Completed' ? 'currentActive' : ''}" id="completedWidget">
							<h5 class="margin-top0">Completed</h5>
							<p class="text-muted mb0">
								<!-- <i class="fa fa-rupee paddingRight"></i><span>1000</span> -->
							</p>
						</div>
					</li>
					<li class="col-md-3" title="Request" onclick="getMyLoansAdvList('Requested');">
						<div class="cardModel-widget text-center cursorPointer ${loanStatus == 'Requested' ? 'currentActive' : ''}" id="requestWidget">
							<h5 class="margin-top0">Requested</h5>
							<p class="text-muted mb0">
								<!-- <i class="fa fa-rupee paddingRight"></i><span>1000</span> -->
							</p>
						</div>
					</li>
					<li class="col-md-3"><a href="javascript:void(0)"
						class="pull-right" onClick="requestLoanPop();">Request</a></li>
				</ul>
			</div>
			<div class="clearfix"></div>
			
			<div class="clearfix project_tabs_inn_table overflow_Auto dataFilterNew" id="myLoansAdvListDivId">
				<%-- <table width="100%">
					<thead>
						<tr>
						  <th>Name</th>
						  <th>Type</th>   
						  <th>Total Amount</th>
						  <c:if test="${loanStatus == 'Outstanding'}">
							  <th>Paid Amount</th>
			                  <th>Due Amount</th>
		                  </c:if>
		                  <c:if test="${loanStatus == 'Requested'}">
		                  	<th>Requested To</th>
		                  	<th>Requested On</th>
		                  </c:if>
		                  <c:if test="${loanStatus == 'Outstanding' || loanStatus == 'Completed'}">
		                  	<th>No. of Schedules</th>
		                  </c:if>
		                  <c:if test="${loanStatus == 'Outstanding'}">
		                  	<th>Next Schedule</th>
		                  </c:if>
		                  <c:if test="${loanStatus == 'Completed'}">
		                  	<th>Last Paid On</th>
		                  </c:if>
						  <c:if test="${loanStatus == 'Outstanding' || loanStatus == 'Completed'}">
		                  	<th>Action</th>
		                  </c:if>
		                  <c:if test="${loanStatus == 'Requested'}">
		                  	<th>Status</th>
		                  </c:if>        
						</tr>
					</thead>
					
					<tbody>
						<c:if test="${loanAdvListSize > 0}">
						 <c:forEach var="loanAdv" items="${loanAdvHistoryList}">
							 <tr>
							   <td><div class="tblminwidth-50" title="${loanAdv.associateLoanAndAdvances.payrollLoanAndAdvanceId.name}">${loanAdv.associateLoanAndAdvances.payrollLoanAndAdvanceId.name}</div></td>
							   <td><div class="tblminwidth-50" title="${loanAdv.associateLoanAndAdvances.loanAdvanceType}">${loanAdv.associateLoanAndAdvances.loanAdvanceType}</div></td>
							   <td><div class="tblminwidth-50" title="${loanAdv.associateLoanAndAdvances.amount}"><span class="paddingLeft">&#8377; ${loanAdv.associateLoanAndAdvances.amount}</span></div></td>
							   <c:if test="${loanStatus == 'Outstanding'}">
								   <td><div class="tblminwidth-50" title="">--</div></td>
								   <td><div class="tblminwidth-30" title="">--</div></td>
							   </c:if>
							   <c:if test="${loanStatus == 'Requested'}">
			                  		<td><div class="tblminwidth-50" title="">--</div></td>
			                  		<td><div class="tblminwidth-50" title="">--</div></td>
			                   </c:if>
			                   <c:if test="${loanStatus == 'Outstanding' || loanStatus == 'Completed'}">
			                  		<td><div class="tblminwidth-50" title="${loanAdv.associateLoanAndAdvances.noOfSchedules}">${loanAdv.associateLoanAndAdvances.noOfSchedules}</div></td>
			                   </c:if>
							   <c:if test="${loanStatus == 'Outstanding'}">
			                  		<td><div class="tblminwidth-50" title="">--</div></td>
			                   </c:if>
			                   <c:if test="${loanStatus == 'Completed'}">
			                  		<td><div class="tblminwidth-50" title="">--</div></td>
			                   </c:if>
							   <c:if test="${loanStatus == 'Outstanding' || loanStatus == 'Completed'}">
			                  		<td><div class="tblminwidth-50"><a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a></div></td>
			                   </c:if>
			                   <c:if test="${loanStatus == 'Requested'}">
			                  		<td><div class="tblminwidth-30 text-center text-danger">Pending</div></td>
			                   </c:if> 
							 </tr>
						 </c:forEach>
						</c:if>
					</tbody>
				</table> --%>
				<%-- <c:if test="${loanAdvListSize == 0}">
					 <div class='row'> 
						 <div class='col-lg-12'> 
							 <div class='dataTables_empty'>
							 </div>
						 </div>
					 </div>
				</c:if> --%>
			</div>
		</div>
<script>
function getMyLoansAdvList(loanStatus){
	var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/getMyLoansAdvList?${_csrf.parameterName}=${_csrf.token}&loanStatus="+loanStatus,
		success : function(json) {
			var result = "";
			result = result +'<table width="100%" id="myLoansAdvPagination">';
			result = result +'<thead>';
			result = result +'<tr>';
			result = result +'<th>Name</th>';
			result = result +'<th>Type</th> '; 
			result = result +'<th>Total Amount</th>';
			 if(loanStatus === "Outstanding"){
			    result = result +'<th>Paid Amount</th>';
			    result = result +'<th>Due Amount</th>';
		    }	         
		    if(loanStatus === "Requested"){
			    result = result +'<th>Requested To</th>';
			    result = result +'<th>Requested On</th>';
		    } 
		    if(loanStatus === "Outstanding" || loanStatus === "Completed"){
				result = result +'<th>No. of Schedules</th>';
		    } 
		     if(loanStatus === "Outstanding"){
				result = result +'<th>Next Schedule</th>';
		    } 
		    if(loanStatus === "Completed"){
				result = result +'<th>Last Paid On</th>';
		    } 
		     if(loanStatus === "Outstanding" || loanStatus === "Completed"){
				result = result +'<th>Action</th>';
		    } 
		     if(loanStatus === "Requested"){
				result = result +'<th>Status</th>';
		    }        
			result = result +'</tr>';
			result = result +'</thead>';
			result = result +'<tbody>';
			for (var i=0; i<json.length; i++){	
				var report = json[i];
					result = result +'	<tr>';
					result = result +'	   <td><div class="tblminwidth-50" title="'+report.name+'">'+report.name+'</div></td>';
					result = result +'	   <td><div class="tblminwidth-50" title="'+report.type+'">'+report.type+'</div></td>';
					result = result +'	   <td><div class="tblminwidth-50" title="'+report.totalAmount+'"><span class="paddingLeft">&#8377; '+report.totalAmount+'</span></div></td>';
					if(loanStatus === "Outstanding"){ 
						result = result +'		   <td><div class="tblminwidth-50" title="">--</div></td>';
						result = result +'		   <td><div class="tblminwidth-30" title="">--</div></td>';
					}
					if(loanStatus === "Requested"){
						result = result +'   		<td><div class="tblminwidth-50" title="">--</div></td>';
						result = result +'   		<td><div class="tblminwidth-50" title="">--</div></td>';
					}
					if(loanStatus === "Outstanding" || loanStatus === "Completed"){
						result = result +'   		<td><div class="tblminwidth-50" title="${loanAdv.associateLoanAndAdvances.noOfSchedules}">${loanAdv.associateLoanAndAdvances.noOfSchedules}</div></td>';
					}
					if(loanStatus === "Outstanding"){ 
						result = result +'   		<td><div class="tblminwidth-50" title="">--</div></td>';
					}
					if(loanStatus === "Completed"){ 
						result = result +'   		<td><div class="tblminwidth-50" title="">--</div></td>';
					}
					if(loanStatus === "Outstanding" || loanStatus === "Completed"){ 
						result = result +'   		<td><div class="tblminwidth-50"><a href="javascript:void(0)" class="anchorBlack"><i class="fa fa-eye"></i></a></div></td>';
					}
					if(loanStatus === "Requested"){ 
						result = result +'   		<td><div class="tblminwidth-30 text-center text-danger">Pending</div></td>';
					}
					result = result +'	 </tr>';
			}		
			result = result +'</tbody>';
			result = result +'</table>'; 
			$("#myLoansAdvListDivId").empty();
			$("#myLoansAdvListDivId").append(result);
			myLoansAdvPagenation();
		}
	});
}
 
</script>

 <script>
function myLoansAdvPagenation(){  
    $('#myLoansAdvPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>