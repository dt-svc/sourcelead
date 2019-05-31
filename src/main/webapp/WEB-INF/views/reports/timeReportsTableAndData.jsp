<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="/resources/js/jquery.simplePagination.js"></script>


<script>
var sourceleadUrl ='${sourceleadUrl}';
</script>

<!-- <script type="text/javascript" src="/resources/js/ColReorderWithResize.js"></script> -->
<div id="reportTableData"></div>
<div class="timesheetRepTab timesheetRep-table">
   	<table id="fixTable" class="display">
		<thead>
           	<tr>
           	   <th>Employee</th>
               <th>Project</th>
               <th>Frequency</th>
               <th>Rate</th>
               		<c:forEach var="dateFrequency" items="${timesheetReportsDTO.dataTableDateList}">
               			<th class="tblminwidth-125">${dateFrequency}</th>
               		</c:forEach>
               <th>Total</th>
               <th>Stage</th>
               <th class="tblminwidth-125">Project To Date</th>
               <th class="tblminwidth-125">Last Month</th>
            </tr>
		</thead>	
                <tbody id="tableData">
                	<jsp:include page="timeReportsSubmissionData.jsp"></jsp:include>
            	</tbody>
      </table>
</div>
 <div id="paginationDiv" class="clearfix">
  <div class="col-md-6">
  <div class="pull-left mt5">
  <label class="pull-left"><span class="pull-left mt10 mr5">Show</span>
  <div class="dropdown-select pull-left mr5 tblminwidth-50">
  <select id="pageSize" onchange="getTableDataByPageSize()">
  	<option value="10" ${timesheetReportsDTO.pageSize == 10 ? 'selected' : ''}>10</option>
  	<option value="15" ${timesheetReportsDTO.pageSize == 15 ? 'selected' : ''}>15</option>
  	<option value="20" ${timesheetReportsDTO.pageSize == 20 ? 'selected' : ''}>20</option>
  </select>
  </div>
  <span class="pull-left mt10">entries</span>
  </label> 
  <span class="pull-left mt10 ml" id="pageMsg"></span>
  </div>
  </div>
  <div id="paginationholder" class="col-md-6 pagiPad">
		<ul id="pagination-demo" class="pagination-sm pull-right pagination"></ul>
	</div>
</div>
<script>
 var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
$(document).ready(function(){
	datatableBody();
});
</script> 
<script>
function datatableBody(){ 
	 $('#fixTable').DataTable( { 
		   /*  "sDom": "Rlfrtip", */
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
		    var table = $('#fixTable').DataTable();
		    var body = $( table.table().body() );
		    body.unhighlight();
	        body.highlight(searchData);
	 } 
 }
 </script>

 <script>
      function getTableDataByPageSize(){
    	  getTableData(1);
      }
	  function getTableData(pageNo) {
		  var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
		  $("#reportTableData").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
          var frequencyYear = $("#frequencyYear").val();
          var pageSize = $("#pageSize").val();
          if(frequencyYear != null && frequencyYear != '' &&  frequencyYear != 'undefined'){
        	  frequencyMonth = $(".time_months[data-year="+frequencyYear+"]").find("select").val();
        	  if(frequencyMonth != null && frequencyMonth != '' && frequencyMonth != 'undefined'){
        		  var start = parseInt(pageNo*pageSize)-parseInt(pageSize);
        		  $.ajax({
          	          type : "POST",
        	      	  url: sourceleadUrl+"/getMemberReports?frequencyYear="+frequencyYear+"&frequencyMonth="+frequencyMonth+"&pageSize="+pageSize+"&pageNo="+pageNo+"&start="+start+"&searchValue="+searchValue,		
        		      success :function(data) {
        		    	  $("#tableData").empty();
        		    	  $("#tableData").append(data);
        		    	  $("#reportTableData").removeAttr('style');
        		    	  preparePagination(pageNo);
        		      }
        	     });  
        	  }
          }
      }
	  function preparePagination(pageNo) {
		  var totalRows =  $('#totalRows').val();
		  var pageCount = $('#pageCount').val();
		  var pageSize = $("#pageSize").val();
		  var searchData = '${timesheetReportsDTO.searchValue}';
		  
		  if(totalRows !=null && totalRows != '' && totalRows != 'undefined'){
		  $('#paginationDiv').show();
		  $('#dataTableSearchTimesheetReports').val('') ;
		  $('#dataTableSearchTimesheetReports').val(searchData); 
		  searchHighlight(searchData);
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
	               onPageClick: function (pageNumber, event) {
	                 getTableData(""+pageNumber+"");
	              }
	      };
	      $("#pagination-demo").pagination(options);
	      var pageMsg = " Showing "+startingRecord+" to "+endingRecord+" of "+totalRows+" entries ";
	  }else{
		  $("#fixTable").empty();
    	  $("#fixTable").append('<div class="dataTables_empty">  <input type="hidden" id="hideExportApproval" value="EmptyList">  </div>');
		  $('#dataTableSearchTimesheetReports').val('') ;
		  $('#dataTableSearchTimesheetReports').val(searchData);
		  $('#paginationDiv').hide();
	  }
	      $('#pageMsg').html('');
	      $('#pageMsg').html(pageMsg);
	 }
  </script>  
      

    