<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="/resources/js/jquery.simplePagination.js"></script>
<script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script>

<div id="tableContent" class="timesheetRep-table timesheet_appr_table" style="overflow: auto;">
<input type="hidden" id="tempVar" value="tempVar">
<input type="hidden" value="${expenseStatus}" id="expenseStatusDivId">
<div id="loadingId"></div>
<table id='example'>                     
	<thead>                                                                                
		<tr>        
			<th>Employee</th>  
			<th>Expense Id</th>                                                                    
			<th>Emp Type</th>                                                                
			<th>Project</th>                                                                       
			<th>Purpose</th> 
            <th>Amount</th>                                                                        
			<th>Status</th>                                                                        
			<th>Action</th>                                                          
			<th>Start Date<br>(<small class="custom-small">${orgLocationDateFormat}</small>)</th> 
			<th>End Date<br>(<small class="custom-small">${orgLocationDateFormat}</small>)</th>   
			<th>No of Items</th>    
			<th>Billing Status</th>                                                                  
			                                                                       
		</tr>                                                                                  
	</thead>                                                                               
			<tbody>                                                                                
			   <jsp:include page="expensesApprovalTableData.jsp"></jsp:include>                                                                                      
			</tbody>                                                                               
</table> 
</div>
<div id= "paginationDiv" class="clearfix">
	<div class="col-md-6">
		<div class="pull-left mt5">
			<label class="pull-left"><span class="pull-left mt10 mr5">Show</span>
				<div class="dropdown-select pull-left mr5 tblminwidth-50">
					<select id="pageSize" onchange="getTableDataByPageSize()">
						<option value="5"  ${pageSize == 5 ? 'selected' : ''} >5</option>
						<option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
						<option value="15" ${pageSize == 15 ? 'selected' : ''}>15</option>
						<option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
					</select>
				</div> <span class="pull-left mt10">entries</span> </label>
				 <span class="pull-left mt10 ml" id="pageMsg"></span>
		</div>
	</div>
	<div id="paginationholder" class="col-md-6 pagiPad">
		<ul id="pagination-demo" class="pagination-sm pull-right pagination"></ul>
	</div>
</div> 


<!-- <script>
$(document).ready(function(){
	datatableBody();
});
</script> 
<script>
function datatableBody(){ 
	 $('#example').DataTable( { 
		    "sDom": "Rlfrtip",
		    "bPaginate": false,
		    "bInfo" : false,
		    "ordering": false,
		    "searchHighlight": true
	    } );
}
</script> 
<script>
 function searchHighlight(searchData){
	 if(searchData != null && searchData != 'undefined' && searchData != ''){
		    var table = $('#example').DataTable();
		    var body = $( table.table().body() );
		    body.unhighlight();
	        body.highlight(searchData);
	 } 
 }
 </script> -->
<script>
$(document).ready(function(){
	if('${AWKWARD}' == "true"){
		   window.location.href =  "/errorPage";
	 }
});
</script>

<script>
function searchExpensesDataIcon(){
	$('#SearchExpensesData').parent().find('p.jquery_form_error_message').remove();
	var searchData = $('#SearchExpensesData').val();
	var regex =  new RegExp(/[\\]/g);
	if(regex.test(searchData) == false){
		getExpensesApprovalDataList();
	}else{
		$("#SearchExpensesData").after("<p class='jquery_form_error_message'><span>Enter valid Search Data</span>");
	}
	$('#SearchExpensesData').val(searchData);
}
</script>

<script>
      function getTableDataByPageSize(){
    	  getTableData(1);
      }
	  function getTableData(pageNo) {
		  var startDate = $("#filterStartDate").val();
		  var endDate = $("#filterEndDate").val();
		  var searchData = $('#SearchExpensesData').val();
		  var expenseStatus = $('#expenseStatusDivId').val();
		  if(expenseStatus != null && expenseStatus != '' && expenseStatus !='undefined'){
		  	expenseStatus = expenseStatus
		  }else{
		  	var expenseStatus = [];
		  	$('input:checkbox[name=expenseStatus]:checked').each(function() 
		  	{
		  		expenseStatus.push($(this).val());
		  	});	
		  }
		  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
		  var expensesApprvListDate = getJSDateFormat(locationList);
          var pageSize = $("#pageSize").val();
          var start = parseInt(pageNo*pageSize)-parseInt(pageSize);
          var searchvalue = '';
          if(searchData != null && searchData !='' && searchData!='undefined'){
          	searchValue = searchData;
          }else{
          	searchValue = '';
           }
          $("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
		  $.ajax({
		       type : "POST",
		       url: sourceleadUrl+"/getExpensesApprovalList?"+tokenParamName+"="+tokenValue+"&expenseStatus="+expenseStatus+"&startDate="+startDate+"&endDate="+endDate+"&pageSize="+pageSize+"&pageNo="+pageNo+"&start="+start+"&searchValue="+searchValue,      
		       success :function(data) {
		      	  $("#approverDataExpensesDiv").empty();
		  		  $("#approverDataExpensesDiv").append(data);
		  		  $("#loadingId").removeAttr('style');
		  		  var totalRows =  $('#totalRows').val();
		  		  preparePagination(pageNo);
		    }
		  });
      }
	  function preparePagination(pageNo) {
		  var totalRows =  $('#totalRows').val();
		  var pageMsg = '';
		  var searchData = '${searchValue}';
		  if(totalRows !=null && totalRows != '' && totalRows != 'undefined' && totalRows > 0){
			  $('#paginationDiv').show();
			  var pageCount = $('#pageCount').val();
			  var pageSize = $("#pageSize").val();
			  $('#SearchExpensesData').val('') ;
			  $('#SearchExpensesData').val(searchData);
			  /* searchHighlight(searchData); */
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
			  $("#tableContent").empty();
	    	  $("#tableContent").append('<div class="dataTables_empty">  <input type="hidden" id="hideExportApproval" value="EmptyList">  </div>');
			  $('#SearchExpensesData').val('') ;
			  $('#SearchExpensesData').val(searchData);
			  $('#paginationDiv').hide();
			  
			  
		  } 
	      $('#pageMsg').html('');
	      $('#pageMsg').html(pageMsg);
	 }
  </script>  
  
   <script>
function changeBillingStatus(id, ele){
	 var billingStatus = $(ele).val();
	  $.ajax({
		    type : "POST",
		     url: sourceleadUrl+"/updateBillingStatus?billingStatus="+billingStatus+"&expenseId="+id,      
		     success: function(data) {
			    	if(data.response === "SESSION_TIMEDOUT") {
			    		location.replace(sourceleadUrl);
			    	 } else if (data.response === "AWKWARD") {
	                        location.href = "/errorPage";
			    	 } else if (data.response === "SUCCESS"){  
			    	if(from != null && from != '' && from != 'undefined' && from == 'dashboard'){
				    	 var table = $('#example').DataTable();
				    	 table.row('#row'+expenseId).remove().draw( false );
				    	 
				    	 $(ele).find("option[data-name='select']").hide();
				    	 
		        	 }
		        	 else{
		        		 $("#row"+expenseId).empty();
				    	 $("#row"+expenseId).html(rowHtmlContent);
		        	   }
			    	}else if (result.indexOf('Sign In') !== -1) {
	  					location.replace(sourceleadUrl);
					 }
			    	 
			    }
	 }); 
}


</script>
                                                                             