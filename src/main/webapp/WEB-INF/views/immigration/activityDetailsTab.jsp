<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="immigraTabsHead">
		</div>
		
	  	<div class="immigraTable">
	  	 <c:forEach var="activiesItems" items="${immigrationActivitiesList}" varStatus="rows"> 
		 <table>
			<tbody>
			  <tr>
				<td><div class="tbldata-dotdot-custom">${activiesItems.actionBy}</div></td>
				<td><div class="tbldata-dotdot-custom">${activiesItems.actionDesc}</div></td>
				<td><div class="tbldata-dotdot-custom">${activiesItems.createdDateString}</div></td>
				<!-- <td class="immigraAction"><a href="javascript:void(0)" class="fa fa-trash"></a></td -->
			  </tr>  			  			  
			</tbody>
		  </table> 
		  <!-- <div class="immFoot clearfix">
			<div class="pull-right text-right">
				<input type="button" value="Back" onclick="goToSummaryTab('#notesTab');" class="btn btn-default btn-sm">
            </div>
		</div> -->
		
		<%-- <div class="immiNote clearfix">
				<p>
					<b>By <span>${activiesItems.actionBy}</span><u>${activiesItems.createdDateString}</u></b>
					${activiesItems.actionDesc}
				</p>
 			</div>  --%>
 			</c:forEach> 
 			 <c:if test="${empty immigrationActivitiesList}">
				<tr>
				  <td colspan="5" valign="top" class="dataTables_empty"></td>
				</tr>
			 </c:if>			
		</div>