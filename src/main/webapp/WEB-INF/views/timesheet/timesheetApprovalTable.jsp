<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="/resources/js/jquery.simplePagination.js"></script>


<!--############### Table Content with data  ###############-->
<c:if test="${!empty timesheetApprovalDTO.timesheetSubmission}">
	<div id="loadingCotentDiv"></div>
	<div id="tableContent" class="timesheetRep-table timesheet_appr_table timesheet_apprTable" style="overflow: auto;">
	 <table id='exampleApprover' class='display'>
	    <!-- Header content  -->
		<thead> 
	    <tr>
	    <th><input type="checkbox" id="selectAllTimeSheetCheckBoxForSubmitForApproval"></th>
	    <th>Employee</th>
		<th>Employment Type</th>
		<th>Project</th>
		<th>Client</th>
			<c:forEach var="weekFrequency" items="${timesheetApprovalDTO.dataTableWeekDateList}" >
				<th>${weekFrequency}</th>
			</c:forEach>
		<th>Total</th>
		<th>Stage</th>
		<th>Action</th>
	    </tr> 
	    </thead>
	    <!-- timesheet details of individual projectmembrs  -->
	    <tbody id="approvalTableData">
	    	 <jsp:include page="timesheetApprovalTableData.jsp"></jsp:include>
	   </tbody>
	  </table>
	</div>
</c:if>

<!-- ###############Table Content with an empty data  ###############-->
<c:if test="${empty timesheetApprovalDTO.timesheetSubmission}">
      <div class="dataTables_empty">   </div>
</c:if>


  <!-- ############### Commonscripts used for both timesheetApproval  & timesheetdashboard list pages ###############--> 
 <jsp:include page="timesheetApprovalCommonScripts.jsp"></jsp:include>



<!-- ############### Pagination div,implemented based on simple-pagination plugin ###############-->
<div id= "paginationDiv" class="clearfix">
	<div class="col-md-6">
		<div class="pull-left mt5">
			<label class="pull-left"><span class="pull-left mt10 mr5">Show</span>
				<div class="dropdown-select pull-left mr5 tblminwidth-50">
					<select id="pageSize" onchange="getTableDataByPageSize()">
						<option value="5" >5</option>
						<option value="10" >10</option>
						<option value="15" >15</option>
						<option value="20" >20</option>
					</select>
				</div> <span class="pull-left mt10">entries</span> </label>
				 <span class="pull-left mt10 ml" id="pageMsg"></span>
		</div>
	</div>
	<div id="paginationholder" class="col-md-6 pagiPad">
		<ul id="pagination-demo" class="pagination-sm pull-right pagination"></ul>
	</div>
</div>


<script>
function exportTimeSheetToPdfOrExcel(exportType){
	 	var pmsUrl ='${SourceLeadWeb_pmsUrl}';
		
	    var frequencyMonth = $('input[id=frequencyMonth]:checked').val();
        var timesheetFrequency = $('input[id=timesheetFrequency]:checked').val();
          
		var stage = $('input[id=stage]:checked').val();
		var client ='';
		if(client!= null && client !='' && client !='undefined'){
		   client = $('input[id=client]:checked').val();
		}
		var project = '';
		if(project!= null && project !='' && project !='undefined'){
			project = $('input[id=project]:checked').val();
		}
	    var searchValue = $('#dataTableSearchTimesheets').val();
	    if(searchValue == null ||  searchValue =='' || searchValue =='undefined'){
	    	searchValue = '';
         }
		var weekFrequency = $('input[id=weekFrequency]:checked').val();
		if(weekFrequency != null && weekFrequency !='undefined'){
		var date = weekFrequency.split("To");
		var startDate = date[0];
		var endDate = date[1];
		}
		window.location.href = pmsUrl
				+ "/getExportTimeSheetForApprovalMechanizm?&startDate="
				+ startDate + "&endDate=" + endDate + "&client=" + client+ "&project=" + project
				+ "&stage=" + stage + "&timesheetFrequency="
				+ timesheetFrequency +"&frequencyMonth="+frequencyMonth+"&weekFrequency="+weekFrequency+"&exportType=" + exportType+"&searchValue="+searchValue;
	}
</script>

<script>
	 function dropDown(){
		$("#exporrt").slideToggle(200);
	} 
	 $("html").click(function() {
	     $("#exporrt").slideUp(100);
	 });
	 $("#exporrt, .dropexport").click(function(e) {
	     e.stopPropagation();
	 });
</script>  

<script>
 var selectedRecords = {};
  $(document).ready(function () {
	    $("#selectAllTimeSheetCheckBoxForSubmitForApproval").click(function () {
	        $(".checkBoxForSubmitForApproval").prop('checked', $(this).prop('checked'));
	        $(".checkBoxForSubmitForApproval").each(function() {
	        	selectedRecords[$(this).val()] = $(this).is(":checked");
	        });
	        displayAcceptRejectButtonOnHedder();
	    });
	});
 </script>
 
 <!--################# show/hide accept and reject buttons while performing bulk action #################-->
 <script type="text/javascript">
function displayAcceptRejectButtonOnHedder() {
	if($('[name="checkBoxForSubmitForApproval"]:checked').length > 0){
		$('#hedderAcceptRejectButton').removeAttr('style');
	}else{
		$('#hedderAcceptRejectButton').attr('style','display:none');
	}
	
}
</script>	

<!-- ############### Pagination scripts,implemented based on simple-pagination plugin ###############-->
 <script>
      function getTableDataByPageSize(){
    	  getTableData(1,0);
      }
	  function getTableData(pageNo,noOfRecords) {
		  var pmsUrl ='${SourceLeadWeb_pmsUrl}';
          var locationCode ='${SourceLeadWeb_locationCode}';
          var userId = '${SourceLeadWeb_user.id}';
          
          var frequencyMonth = $('input[id=frequencyMonth]:checked').val();
          var timesheetFrequency = $('input[id=timesheetFrequency]:checked').val();
          var weekFrequency = $('input[id=weekFrequency]:checked').val();
          var client = $('input[id=client]:checked').val();
          var project = $('input[id=project]:checked').val();
          var searchValue = $('#dataTableSearchTimesheets').val();
  	   	  if(searchValue == null ||  searchValue =='' || searchValue =='undefined'){
  	    	searchValue = '';
           }
  	    
          if(frequencyMonth == null || frequencyMonth == '' || frequencyMonth == 'undefined')
       	      frequencyMonth = '';
	       if(timesheetFrequency == null || timesheetFrequency == '' || timesheetFrequency == 'undefined')
	         timesheetFrequency = '';
          if(weekFrequency == null || weekFrequency == '' || weekFrequency == 'undefined')
       	      weekFrequency = '';
          if(stage == null || stage == '' || stage == 'undefined')
       	       stage = '';
          if(client == null || client == '' || client == 'undefined')
       	      client = '';
          if(project == null || project == '' || project == 'undefined')
       	      project = '';
          
	      if(weekFrequency == null || weekFrequency == '' || weekFrequency == 'undefined')
	    	  weekFrequency = '';
          var stage = $('input[id=stage]:checked').val();
          var pageSize = $("#pageSize").val();
          var start = parseInt(pageNo*pageSize)-parseInt(pageSize);
          var frequencyStartDate = '${timesheetApprovalDTO.frequencyStartDate}';
          var frequencyEndDate = '${timesheetApprovalDTO.frequencyEndDate}';
          $("#loadTime1").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
          $.ajax({
          	          type : "POST",
          	        url: pmsUrl+"/timesheetEmployeeSubmissionDetails?frequencyMonth="+frequencyMonth+"&timesheetFrequency="+timesheetFrequency+"&weekFrequency="+weekFrequency+"&client="+client+"&project="+project+"&stage="+stage+"&frequencyStartDate="+frequencyStartDate+"&frequencyEndDate="+frequencyEndDate+"&locationCode="+locationCode+"&userId="+userId+"&pageSize="+pageSize+"&pageNo="+pageNo+"&start="+start+"&totalRows="+noOfRecords+"&searchValue="+searchValue,		
        		      success :function(data) {
        		    	  $("#approvalTableData").empty();
        		    	  $("#approvalTableData").append(data);
        		    	  $("#selectAllTimeSheetCheckBoxForSubmitForApproval").removeProp('checked');
		                     $(".checkBoxForSubmitForApproval").each(function() {
		                    	 for(var e in selectedRecords) {
		                    		 if($(this).val() === e) {
		                    			 if(selectedRecords[e])
		                    			 	$(this).prop('checked', selectedRecords[e]);
		                    			 break;
		                    		 }
		                    	 }
		         	        });

		             	    $(".checkBoxForSubmitForApproval").click(function () {
		             	    	selectedRecords[$(this).val()] = $(this).is(":checked");
		             	    });
        		    	  var totalRows =  $('#totalRows').val();
        		    		  $("#loadTime1").removeAttr('style');
            		    	  preparePagination(pageNo);
        		    	 
        		      }
        	     });  
      }
	  function preparePagination(pageNo) {
		  var totalRows =  $('#totalRows').val();
		  var pageMsg = '';
		  var searchData = '${timesheetApprovalDTO.searchValue}';
		  var notifyLink =$('#notifyLink').val(); 
		  if(totalRows !=null && totalRows != '' && totalRows != 'undefined'){
			  $('#paginationDiv').show();
			  var pageCount = $('#pageCount').val();
			  var pageSize = $("#pageSize").val();
			  $('#dataTableSearchTimesheets').val('') ;

			  $('#dataTableSearchTimesheets').val(searchData);
			  if (notifyLink != null && notifyLink != '' && notifyLink != 'undefined' && notifyLink){
				  $('#dataTableSearchTimesheets').val('') ;
			  }
			  var startingRecord = parseInt((pageNo*pageSize-pageSize)+1);
			  var endingRecord = parseInt(pageNo*pageSize);
			  if(endingRecord > totalRows){
				  endingRecord = totalRows;
			  }
			  $('#timesheetApproverCurrentPageNumber').val(pageNo);
			  var options = {
					      currentPage:pageNo,
					      items: totalRows,
			              itemsOnPage: pageSize,
			              ellipsePageSet :false,
			              cssStyle: 'light-theme',
			               onPageClick: function (pageNumber, event) {
			                 getTableData(""+pageNumber+"",""+totalRows+"");
			              }
		      };
		      $("#pagination-demo").pagination(options);
		      pageMsg = " Showing "+startingRecord+" to "+endingRecord+" of "+totalRows+" entries ";
		      if (notifyLink != null && notifyLink != '' && notifyLink != 'undefined' && notifyLink){
		    	  $("#popuplink").click();
		      } 
		  }
		  else{
			  $("#tableContent").empty();
	    	  $("#tableContent").append('<div class="dataTables_empty">  <input type="hidden" id="hideExportApproval" value="EmptyList">  </div>');
			  $('#dataTableSearchTimesheets').val('') ;
			  $('#dataTableSearchTimesheets').val(searchData);
			  $('#paginationDiv').hide();
			  
			  
		  }
	      $('#pageMsg').html('');
	      $('#pageMsg').html(pageMsg);
	 }
  </script> 










 
