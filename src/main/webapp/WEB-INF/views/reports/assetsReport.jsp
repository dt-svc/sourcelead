<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<!-- this is for mask -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="/resources/js/jquery.simplePagination.js"></script>
 
  <section class="content-wraper contentWraper">
    <section class="content-header clearfix">
	    <div class="pull-left">
	    	<h2>Asset Assigned History</h2>
	    </div>
		<div class="pull-right brud-crum">
         	<a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
         	<a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; 
         	<a href="<c:url value='${appPath}/hrReportsList'/>">HR Reports</a> &raquo;
         	<span id="breadCrum1">
         		Asset Assigned History
         	</span>
         	<span id="breadCrum2" style="display: none;">
	         	<a href="<c:url value='${appPath}/assetsReport'/>">Asset Assigned History</a> &raquo;
    	     	Asset Assigned History List
         	</span>
		</div>
    </section>
    
    <div class="contentareaInner-styles clearfix">
     <form id="assetForm" name="assetForm">
    <div class="clearfix">
        <div class="clearfix row minHeightcol5">
                  <div class="col-md-5">
             <div class="row">
            	  <div class="col-md-6">
              <label class="clearfix">From Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" readonly class="form-control calendar" name="fromDate" value="" field-name="From Date" id="fromDate"  data-validation="required">
                 <span class="input-group-addon">
                 	<i class="colorRed fa fa-times cursorPointer" onclick="clearDate('from');"></i>
                 </span>
              </div> 
              </div>
            	  <div class="col-md-6">
              <label class="clearfix">To Date<span class="text-danger">&nbsp;*</span></label>
              <div class="clearfix input-group errorInptgroup">
                <input type="text" readonly class="form-control calendar" name="toDate" value="" field-name="To Date" id="toDate"  data-validation="required">
                 <span class="input-group-addon">
                 	<i class="colorRed fa fa-times cursorPointer" onclick="clearDate('to');"></i>
                 </span>
              </div>
              </div>
            </div>
            </div>
			
			<div class="col-md-5">
             <div class="form-group">
              <label class="col-md-12">Asset Date</label>
              <div class="col-md-12">
				  <div class="clearfix mt5">
				<span class="radioStyles mr pull-left">
					<input id="assigned" name="dateType" value="1" type="radio" checked>
					<label for="assigned" class="mt2 pull-left"></label>Assigned Date
                </span>
				<span class="radioStyles">
					<input id="return" name="dateType" value="2" type="radio">
					<label for="return" class="mt2 pull-left"></label>Returned Date
                </span>
				  </div>
              </div>
            </div>
        </div>
			
          <div class="col-md-5">
            <div class="form-group">
              <label class="clearfix paddingRight">Categories</label>
				<div class="clearfix">
					<select class="chosen-select" name ="categories" id="categoryId" data-placeholder="Select category" multiple>
			<c:forEach var="option" items="${assetCategoriesList}">
				<option value="${option.id}">${option.categoryName}</option>
			</c:forEach>
		  </select>
				</div>
            </div>
			  
			</div>
			
			  <div class="col-md-5">
             <div class="form-group">
              <label class="clearfix">Shift</label>
              <div class="clearfix">
               		<select class="chosen-select" name="shiftsIds" id="shiftId" data-placeholder="Select shift" multiple>
						<c:forEach var="shift" items="${shiftList}">
							<option value="${shift.id}">${shift.shiftName}</option>
						</c:forEach>
		          </select>
              </div>
            </div>
		 </div>
				
			  <div class="col-md-5">
            <div class="form-group">
              <label class="clearfix">Asset Name</label>
              <div class="clearfix">
                   		<select class="chosen-select" name ="assetNames" id="assetId" data-placeholder="Select asset" multiple>
							<c:forEach var="option" items="${assetList}">
								<option value="${option.id}">${option.name}</option>
							</c:forEach>
						  </select>
              </div>
            </div>
			</div>
          
      </div>
	<div class="clearfix footerstyelsNew">
        <div class="col-lg-12">
          <div class="pull-right">
            <input value="Run" class="btn btn-success btnSuccess" type="button" onClick="assetReportDataTableList();">
            <input value="Reset" class="btn btn-defualt ml10" type="button" onClick="resetAssetReportDataTableList();">
          </div>
        </div>
      </div>      
	</div>
     </form>
     <div class="clearfix"></div>
   
     
    		
  <div class="timesheet_out" id="payrollRegister" style="display: none;">
  <div class="pull-right mt10">
    
       
       
   </div>
      
          
      <div class="clearfix timesheet_head">
      <ul class="list-unstyled list-inline">
                
             <li><a href="javascript:void(0)" class="overIcon" id="exportOptions" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
		           	   		<a href="javascript:void(0)" onclick="aseetsDataExport('Pdf');" title="PDF">Export PDF</a>
							<a href="javascript:void(0)" onclick="aseetsDataExport('Excel');" title="Excel">Export Excel</a>            
						</span>
					</div>
				</li>
            </ul>
    </div>
  <div class="require_wrap_table tableFirstSecond clearfix overflow_inherint datatable_filter"></div>
  </section>
 
</div>

</body>
</html>


<script>

function assetReportDataTableList(refreshFlag){         
	  if ($('#assetForm').validate(false, validationSettings)){
// 		  $("#loadingId").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:60% center; background-repeat:no-repeat; background-size:75px;');
		  var formData = getFormData1("assetForm");
		  $.ajax({
	            url: sourceleadUrl+"/getAssetReport",
	            type: "POST",
	            data: formData,
 		        processData: false,
 		        contentType:false, 
	            success: function (data) {
	    			 prepareAssetReportData(data);
	            }
	        });
		  
		 return true;
	 }else{ 
			return false;
	 }  
}


function resetAssetReportDataTableList(){
	$("#fromDate").val('');
	$('#fromDate').attr('value', '');
	$('#fromDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#toDate").val('');
	$('#toDate').attr('value', '');
	$('#toDate').datepicker( "option" , {
        minDate: null,
        maxDate: null} );
	$("#categoryId").val('');
	$('#categoryId').trigger("chosen:updated");
	$("#shiftId").val('');
	$('#shiftId').trigger("chosen:updated");
	$("#assetId").val('');
	$('#assetId').trigger("chosen:updated");
	
}
	
	function prepareAssetReportData(result){
		$(".tableFirstSecond").empty();
		$("#exportOptions").hide();
		var text = "";
		if(result.data && result.data !== null) {
			text += "  <table class='table' id='projectReportTableId'>";
			text += " 	<thead>";
			text += " 	  <tr>";
			text += " 	   	<th>Employee ID</th>";
			text += " 	   	<th>Employee Name</th>";
			text += " 	   	<th>Asset Name</th>";
			text += " 		<th>Category</th>";
			text += " 		<th>Serial Number</th>";
			text += " 		<th>Assigned On</th>";
			text += " 		<th>Returned On</th>";
			text += " 		<th>Shift</th>";
			text += " 		<th>Document</th>";
			text += " 		<th>Assigned By</th>";
			text += " 	  </tr>";
			text += " 	</thead>";
			text += " 	<tbody>";
			
			if(result.data !== null && result.data.length > 0) {
				for(var e in result.data) {
					var report = result.data[e];
					text += "    <tr>";
					text += "		<td><div class='tbldata-dotdot'>" + (report.employeeId !== null && report.employeeId !== "" ? report.employeeId : "----") + "</div></td>";
					text += "		<td><div class='tbldata-dotdot'>" + (report.employeeName != null && report.employeeName !== "" ? report.employeeName : "----") + "</div></td>";
					text += "		<td><div class='tbldata-dotdot'>" + (report.assetName != null && report.assetName !== "" ? report.assetName : "----") + "</div></td>";
					text += "		<td><div class='tbldata-dotdot-vsm'>" + (report.category != null && report.category !== "" ? report.category :"----") + "</div></td>";
					text += "		<td><div class='tbldata-dotdot'>" + (report.serialNo != null && report.serialNo !== "" ? report.serialNo : "----") + "</div></td>";
					text += "		<td><div class='tbldata-dotdot-100'>" + (report.assignedOn != null && report.assignedOn !== "" ? report.assignedOn : "----") + "</div></td>";
					text += "		<td><div class='tblminwidth-75'>" + (report.returnedOn !== null && report.returnedOn !== "" ? report.returnedOn : "----") + "</div></td>";
					text += "		<td><div class='tblminwidth-75'>" + (report.shift !== null && report.shift !== "" ? report.shift : "----") + "</div></td>";
					if(report.fileUrl !== null && report.fileUrl !== "") {
						text += "		<td><div class='clearfix downloadLinks'>" + (report.fileUrl !== null && report.fileUrl !== "" ? "<a class='tbldata-dotdot'>" + report.document + "</a>" : "----") + " <ul class='list-unstyled list-inline mb0 downloadUL'><li><a href='" + report.fileUrl + "' class='fa fa-download cursorPointer'></a></li> <li><a onclick='viewFile(\""+report.fileUrl+"\",\""+report.document+"\",\""+report.fileContentType+"\");' class='fa fa-eye ml cursorPointer'></a></li></ul></div></td>";
					} else {
						text += "		<td><div class='clearfix downloadLinks'>" + (report.fileUrl !== null && report.fileUrl !== "" ? "<a class='tbldata-dotdot'>" + report.document + "</a>" : "----") + " </div></td>";
					}
					text += "		<td><div class='tblminwidth-75'>" +(report.assignBy !== null && report.assignBy !== "" ? report.assignBy : "----") + "</div></td>";
					text += "	</tr>";
				}
				text += " 	</tbody>";
				text += "   </table>";

				$(".tableFirstSecond").append(text);

				$("#exportOptions").show();
				dynamicAssetReportPagination();  
			} else {
				$(".tableFirstSecond").append("<div class='dataTables_empty'></div>");
			}
			$("#payrollRegister").show();
			$("#assetForm").hide();
			$("#breadCrum2").show();
			$("#breadCrum1").hide();
		} else {
			$(".tableFirstSecond").append("<div class='dataTables_empty'></div>");
		}
	}
</script>

<script>
// function datePickeerTest(){
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatProjectSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatProjectSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatProjectSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatProjectSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatProjectSave = 'yy-dd-mm'; 
	  }
	
	  $('#fromDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate());
				$("#toDate").datepicker("option", "minDate", dt);
			}
		});
		$('#toDate').datepicker({
			dateFormat : finalDateFormatProjectSave,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate());
				$("#fromDate").datepicker("option", "maxDate", dt);
			}
		});
// }

</script> 
<script>
function clearDate(requestFrom){
	if(requestFrom == 'from'){
		$('#fromDate').val('');
		$('#toDate').val('');
	}else if(requestFrom == 'to'){
		$('#toDate').val('');
	}
}
</script>
<script>
function dynamicAssetReportPagination(){  
    $('#projectReportTableId').DataTable({
    	//"dom": 'Bfrtip',
    	"responsive": true,
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"buttons": ['excel', 'pdf'],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#pendingForApprovalTimesheetDiv").find('a.buttons-excel').prop("title", "Export to Excel");
	$("#pendingForApprovalTimesheetDiv").find('a.buttons-pdf').prop("title", "Export to PDF");
	$('#projectReportTableId').wrap('<div class="overflow_Auto width100percnt"></div>');
}
</script>
<script>

function aseetsDataExport(type){
	
       var locationValue = sourceleadUrl+"/getAssetsReportExport?type="+type+"&fromDate=" + $("#fromDate").val() + "&toDate=" + $("#toDate").val() + "&dateType=" + $("[name='dateType']:checked").val();
       if($("#categoryId").val() !== null) {
       	locationValue += "&categories=" + $("#categoryId").val();
       }
       if($("#shiftId").val() !== null) {
       	locationValue += "&shiftsIds=" + $("#shiftId").val();
       }
       if($("#assetId").val() !== null) {
	    locationValue += "&assetNames=" + $("#assetId").val();
       }
       window.location.href = locationValue;
	  
	
	
	
}
</script>


<script>

function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	

</script>
<script src="/resources/js/chosen.jquery.min.js"></script>
 <script type="text/javascript">
    $(document).ready(function() {
   		$('#categoryId, #shiftId, #assetId').chosen();
    });
 </script>