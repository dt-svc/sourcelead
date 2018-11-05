<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="modal paymentaccount-modal" id="paymentAccountPopup" role="dialog">
    <div class="modal-dialog modal-lg" style="width: 950px;">
      <!-- Modal content-->
      <div class="modal-content"  style="height: 550px; overflow: auto;">
        <div class="modal-header">
          <button type="button" class="close" onclick="closeCandiatesSubmitedJobsList();" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Jobs List</h4>
        </div>
        
 <div>   
   <div class="require_wrap_table datatable_filter mt40 overflow_inherint">
	<table id="submittedJobsPagination" width="100%">
		<thead>
			<tr>
			  <th>Generated ID</th>   
			  <th>Title</th>   
			  <th>Status</th>         
			  <th>Submitted On<br>(<small class="custom-small">${orglocationDateFormat}</small>)</th> 
			  <th>Submitted By</th>    
			</tr>
		</thead>
		
		<tbody>
			 <c:forEach var="job" items="${jobsList}">
				 <tr>
				   <td><div class="tbldata-dotdot_100" title="${job.generatedId}">${job.generatedId}</div></td>
				   <td><div class="tbldata-dotdot-big text-center" title="${job.requirementName}">${job.requirementName}</div></td>
				   <td><div class="tbldata-dotdot_100" title="${job.submissionStatus}">${job.submissionStatus}</div></td>
				   <td><div class="tbldata-dotdot-me">${job.submissionDate} / ${job.submissionTime}</div></td>
				   <td><div class="tblminwidth-150" title="${job.submittedBy}">${job.submittedBy}</div></td>
				 </tr>
			 </c:forEach>
		</tbody>
	</table>
	
</div>
</div>
    </div>
  </div>
 </div>
 
 
 <script>
function closeCandiatesSubmitedJobsList() {
    $(".modal.paymentaccount-modal").fadeOut();
    $(".comMask").fadeOut();
}

$('#submittedJobsPagination').DataTable( { 
	"scrollCollapse": false,
	"bPaginate": true,
	"bLengthChange": true,
	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	"bDestroy": true,
	"ordering": false,
	"aoColumnDefs": [{
           'bSortable': false,
           'aTargets': 0
		}],
	"oLanguage": {
 	"sSearch": "",
 	 "sZeroRecords": "", "sEmptyTable": ""
 	}
	
} );
</script>