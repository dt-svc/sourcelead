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
        </section>

        
		<div class="project_tabs_inn_table vendor_wrap_inn_table mt overflow_Auto">
		
		<table id='example' class='display' cellspacing='0' width='100%' >
                   <thead> 
	                     <tr> 
	               		<th>Country</th>
	               	    <th>Subscription Type</th>
	               	    <th>User Subscription</th>
	               		<th>Invoice Amount</th>
	               		<th>From Date <br/><span>(${SourceLeadWeb_orgLocations.dateFormat})</span></th>
	               		<th>To Date<br/><span>(${SourceLeadWeb_orgLocations.dateFormat})</span></th>
	               		<th>Storage</th>
	               		<th>Modules</th>
	               		<th>Actions</th>
		                 </tr>
                    </thead>
                    <tbody>
                    
                    <c:forEach var="orgSubscription" items="${orgLicenseSubscriptionList}">
						 <tr>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.country}</div></td>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.subscriptionType.name}</div></td>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.userSubscriptionType.name}</div></td>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.currency} ${orgSubscription.invoiceAmount}</div></td>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.fromDateStr}</div></td>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.toDateStr}</div></td>
	                    	 <td><div class="tbldata-dotdot-100">${orgSubscription.storageSubscriptionType.name}</div></td>
	                    	 
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
		   		        	<td><a href='#'>Subscribe</a></td>
	                     </tr>
		  			</c:forEach>
			  			
	                    
                    </tbody>
           </table>
		</div>
 </section>

 <jsp:include page="masterFooter.jsp" />	