<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style>
.show_Empdetails.showjoblist {
top:0;
}
</style>

<section class="content-wraper">
<div id="listLoading"></div>
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>License Subscription List</h2>
          </div>
           <div class="pull-right brud-crum">
				<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/goToSettings'/>">Settings</a>&raquo; Subscription List
			</div>
        </section>
         
        <div class="project_wrap">
		<div class="project_tabs_inn_table vendor_wrap_inn_table overflow_Auto">
		
		<table id='example' class='display' cellspacing='0' width='100%' >
                   <thead> 
	                     <tr> 
	               		<th>Country</th>
	               	    <th>Subscription Type</th>
	               	    <th>User Subscription</th>
	               		<th>Invoice Amount</th>
	               		<th>Amount Paid</th>
	               		<th>From Date <br/><span>(${SourceLeadWeb_orgLocations.dateFormat})</span></th>
	               		<th>To Date<br/><span>(${SourceLeadWeb_orgLocations.dateFormat})</span></th>
	               		<th>Storage</th>
	               		<th>Modules</th>
	               		<th>Actions</th>
		                 </tr>
                    </thead>
                    <tbody>
                    
                    <c:forEach var="orgSubscription" items="${orgLicenseSubscriptionList}">
						 <tr id="subscription${orgSubscription.uuid}">
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.country}</div></td>
	                    	 <td><div class="tbldata-dotdot_80">${orgSubscription.subscriptionType.name}</div></td>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.userSubscriptionType.name}</div></td>
	                    	 <c:if test="${!empty orgSubscription.invoiceAmount}">
	                    	 	<td><div class="tbldata-dotdot-100">${orgSubscription.currency} ${orgSubscription.invoiceAmount}</div></td>
	                    	 </c:if>
	                    	 <c:if test="${empty orgSubscription.invoiceAmount}">
	                    	 	<td><div class="tbldata-dotdot-100">--</div></td>
	                    	 </c:if>
	                    	 <td><div class="tbldata-dotdot-80">${orgSubscription.isInvoiceAmountPaid == 'Y' ? "Yes" : "No"}</div></td>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.fromDateStr}</div></td>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.toDateStr}</div></td>
	                    	 <td><div class="tbldata-dotdot_80">${orgSubscription.storageSubscriptionType.name}</div></td>
	                    	 
	                    	<td>
		   		        		<div class="positionRelative">
					   		        <c:forEach var="modules" begin="0" end="1" items="${orgSubscription.modulesList}">
					   		              <p class="tbldata-dotdot-100">${modules}</p>
					   		        </c:forEach>
					   		        	
					   		        <div class="show_Empdetails showjoblist">
				   		        	<i class="fa fa-ellipsis-v"></i>
			
				   		        	<div class="emailvalues emailvaluesNew">
				   		        	
		 							 <c:forEach var="modules" items="${orgSubscription.modulesList}">
		 							 <p><i class="fa fa-check-square-o"></i>${modules}</p>
		 							 </c:forEach>
		 			   		        	
				   		        	</div>
				   		        	</div>
			   		        	</div>
		   		        	</td>
		   		        	<td class="tbldata-dotdot-100 mt">
		   		        	<c:if test="${orgSubscription.isInvoiceAmountPaid == 'N' && orgSubscription.invoiceAmount > 0}">
		   		        	   <a href='#' onclick='initPayment("${orgSubscription.uuid}","pay")'>Pay</a> |
		   		        	</c:if>
		   		        	<a href='<c:url value="/app/upgradeOrgLicenseSubscription/${orgSubscription.uuid}"/>'>Upgrade</a> 
		   		        	<c:if test="${orgSubscription.showRenewal == 'Y' && orgSubscription.isInvoiceAmountPaid == 'Y'}">
		   		        	   | <a href='#' onclick='initPayment("${orgSubscription.uuid}","renewal")'>Renewal</a>
		   		        	</c:if>
		   		        	</td>
	                     </tr>
		  			</c:forEach>
			  			
	                    
                    </tbody>
           </table>
		</div>
		</div>
		
 </section>

 <jsp:include page="masterFooter.jsp" />	
 
 
 <script type="text/javascript">
    function initPayment(uuid,actionType) {
        
    	$("#subscription"+uuid).html("<td colspan='10'  id='loading"+uuid+"'></td>");
		$("#loading"+uuid).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
    	
    	 $.post("/app/init-payment", {type: actionType, subscriptionUUID: uuid}, function (data) {
            if (data) {
                location.href = data;
            }
        }); 
    }
</script>