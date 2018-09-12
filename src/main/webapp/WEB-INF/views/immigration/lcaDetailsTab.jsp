<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	  	<div class="immigraTable overflow_Auto">
		  <table id ="visaTbl">
			<tbody>
			  <tr>
				<th>LCA Number</th>
				<th>LCA Title</th>
				<th>Location</th>
				<th>Prevailing Wage</th>
				<th>Valid From</th>
				<th>Valid To</th>
				<th>LCA Status</th>
				<th>Status</th>
			  </tr>
			  <c:forEach var="lcadetails" items="${lcaList}" varStatus="rows">
			  <tr>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.lcaNo}"></div>${lcadetails.lcaNo}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.lcaTitle}"></div>${lcadetails.lcaTitle}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.location}"></div>${lcadetails.location}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.prevailingWage}"></div>${lcadetails.prevailingWage}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.startDate}"></div>${lcadetails.startDate}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.endDate}"></div>${lcadetails.endDate}</td>
				<td><div class="tbldata-dotdot-sm" title = "${lcadetails.lcaStatus}"></div>${lcadetails.lcaStatus}</td>
				<c:if test="${lcadetails.status == '1'}">
				 <td><div class="tbldata-dotdot-sm" title = "${lcadetails.status}"></div>Active</td>
				</c:if>
				<c:if test="${lcadetails.status == '0'}">
				 <td><div class="tbldata-dotdot-sm" title = "${lcadetails.status}"></div>In-Active</td>
				</c:if>
			 </tr>
			</c:forEach>
		</tbody>
		</table>
	</div>
	
	<div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#visasTab');" class="btn btn-default btn-sm">
              	<input type="button" value="Continue" onclick="goToSummaryNextTab('#employersTab');" class="btn btn-primary btn-sm ml10">
            </div>
		</div>
