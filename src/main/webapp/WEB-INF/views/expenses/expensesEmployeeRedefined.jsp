<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="/resources/js/jquery.simplePagination.js"></script>

<input type="hidden" id="tempVar" value="tempVar">
<input type="hidden" value="${expenseDashBoardStatus}" id="expenseStatusDivId">
<table id='example' class='display' cellspacing='0' width='100%' >                
		<thead>                                                                           
			<tr>                                                                              
				<th>Exp Id</th>                                                                   
				<th>Purpose</th>                                                                  
				<th>Project</th>                                                                  
				<th>Client</th>                                                                   
				<th>Start Date<br>(<small class='custom-small'>${orgLocationDateFormat}</small>)</th>
				<th>End Date<br>(<small class='custom-small'>${orgLocationDateFormat}</small>)</th>
				<th>No of Items</th>                                                              
				<th>Amount</th>                                                                   
				<th>Status</th>                                                                   
			</tr>                                                                             
		</thead>                                                                          
	<tbody>
		<jsp:include page="expensesEmployeeTableData.jsp"></jsp:include>  
	</tbody>  
</table>
<div id= "paginationDiv" class="borTop clearfix">
	<div class="col-md-6">
		<div class="pull-left mt5">
			<label class="pull-left"><span class="pull-left mt10 mr5">Show</span>
				<div class="dropdown-select pull-left mr5 tblminwidth-50">
					<select id="pageSize" onchange="getTableDataByPageSizeEmployee()">
						<option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
						<option value="15" ${pageSize == 15 ? 'selected' : ''}>15</option>
						<option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
					</select>
				</div> 
				 <span class="pull-left mt10">entries</span> </label>
				 <span class="pull-left mt10 ml" id="pageMsg"></span>
		</div>
	</div>
	<div id="paginationholder" class="col-md-6 pagiPad">
		<ul id="pagination-demo" class="pagination-sm pull-right pagination"></ul>
	</div>
</div> 
		
<script>
$("#SearchExpensesData").keyup(function(event) {
    if (event.keyCode === 13) {
    	var searchData = $('#SearchExpensesData').val();
    	getExpensesList();
    	$('#SearchExpensesData').val(searchData);
    }
});
</script>		
		
<script>
function getTableDataByPageSizeEmployee(){
	getTableData(1);
}

function getTableData(pageNo){
	 var startDate = '';
	 var endDate = '';
	 var expenseStatus = '';
	 expenseStatus = $('#expenseStatusDivId').val();
	 if(expenseStatus != null && expenseStatus != '' && expenseStatus !='undefined'){
		  	expenseStatus = expenseStatus.toUpperCase();
		  	$(":checkbox[value="+expenseStatus+"]").prop("checked","true");
	 }else{
		var expenseStatus = [];
		$('input:checkbox[name=expenseStatus]:checked').each(function() 
		{
			expenseStatus.push($(this).val());
		});
	 }
	var userId = '${SourceLeadWeb_user.id}';
	var locationCode = '${SourceLeadWeb_orgLocations.locationCode}'
	var pageSize = $("#pageSize").val();
    var start = parseInt(pageNo*pageSize)-parseInt(pageSize);
    var searchData = $("#SearchExpensesData").val();
    if(searchData != null && searchData !='' && searchData!='undefined'){
    	searchValue = searchData;
    }else{
    	searchValue = '';
    }
	$.ajax({
	    type : "POST",
	     url: sourceleadUrl+"/getExpensesList?&expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate+"&pageSize="+pageSize+"&pageNo="+pageNo+"&start="+start+"&searchValue="+searchValue+"&userId="+userId+"&locationCode="+locationCode,
	    	success :function(data) {
	    	 $("#expensesListDiv").empty();
			 $("#expensesListDiv").append(data);
			 $("#loadingId").removeAttr('style');
			 preparePaginationEmployee(pageNo);
	  		}
		});
	}
	function preparePaginationEmployee(pageNo) {
		var totalRows =  $('#totalRows').val();
		  var pageMsg = '';
		  var searchData = '${searchValue}';
		  if(totalRows !=null && totalRows != '' && totalRows != 'undefined' && totalRows > 0){
			  $('#paginationDiv').show();
			  var pageCount = $('#pageCount').val();
			  var pageSize = $("#pageSize").val();
			  $('#SearchExpensesDataEmployee').val('') ;
			  $('#SearchExpensesDataEmployee').val(searchData); 
			  var startingRecord = parseInt((pageNo*pageSize-pageSize)+1);
			  var endingRecord = parseInt(pageNo*pageSize);
			  if(endingRecord > totalRows){
				  endingRecord = totalRows;
			  }
			  var options = {
					      currentPage:pageNo,
					      items: totalRows,
			              itemsOnPage: pageSize,
			              ellipsePageSet :false,
			              cssStyle: 'light-theme',
			              onPageClick: function (pageNumber, event) {
			              getTableData(""+pageNumber+"");
			              }
		      };
		      $("#pagination-demo").pagination(options);
		      pageMsg = " Showing "+startingRecord+" to "+endingRecord+" of "+totalRows+" entries ";
		  }else{
			  $("#expensesListDiv").empty();
	  	  	  $("#expensesListDiv").append('<div class="dataTables_empty">  <input type="hidden" id="hideExportApproval" value="EmptyList">  </div>');
			  $('#SearchExpensesDataEmployee').val('') ;
			  $('#SearchExpensesDataEmployee').val(searchData);
			  $('#paginationDiv').hide();
		  } 
	    $('#pageMsg').html('');
	    $('#pageMsg').html(pageMsg);
	}
</script>