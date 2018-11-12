<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
$(document).ready(function(){
	getAllEmployeesList();
});
</script>


<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Employment Verification Report</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;<a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/hrReportsList'/>">HR Reports</a> &raquo; Employment Verification Report</div>
    </section>
    <div class="content-area content-new clearfix">
      <div class="clearfix form-horizontal">
        <div class="project_wrap">
          <div class="timesheetHead minHeight40">
            <ul class="list-unstyled mb0 pull-right list-inline mr">
              <li>
                <div class="clearfix pull-lefet col-md-12 paddingRight mt10" id="custom-select" style="width: 346px; display: block;">
                  <div class="pull-left">
                    <div class="posiRela pull-left mr5" style="width: 140px">
                      <input value="" id="fromDate" name="fromDate" placeholder="From" class="form-control calendar input-sm margin-top0" type="text">
                    	<p id="fromDateDiv" class="ReError"></p>
                    </div>
                    <div class="posiRela pull-left mr5" style="width: 140px">
                      <input value="" id="toDate" name="toDate" placeholder="To" class="form-control calendar input-sm margin-top0" type="text">
                    	<p id="toDateDiv" class="ReError"></p>
                    </div>
                    <button type="button" class="btn btn-primary btn-sm margin-top0"><i class="fa fa-search" onclick="searchThroughDates();"></i></button>
                  </div>
                </div>
              </li>
              <li id="employementveriFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="employementveriFileterPopUp();"></a></li>
				
              <li>
              <div class="dynamic_search" style="top:-10px;">
        		          <input type="text" placeholder="Search" id="employeeDataTableSearch">
        		          <span class="cursorPointer" title="Search"><i class="fa fa-search" onclick="getAllEmployeesList();"></i></span>    
                </div>
              </li>
              <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
          </div>
        </div>
        
          <div class="candidate_wrap_table datatable_filter">
	           <div id="employeeList"></div>
          </div>
        <!-- Content Area Ends--> 
      </div>
    </div>
  </section>

<div class="sideMask"></div>
<div class="modal printEVR" style="display: none">
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
		<a href="javascript:void(0)" class="close" onClick="closePOP()">&times;</a>
			<h4>Print</h4>
		</div>
		<div class="modal-body">
			<div class="form-group">
				<label>Choose Template <span class="text-danger paddingRight">*</span></label>
				<div class="clearfix">
					<select class="chosen-select form-control chose-all-template" data-live-search="true" id="empTemplateId">
					</select>
				</div>
			</div>
			<div class="form-group">
				<label>To whom it may concern</label>
				<div class="clearfix">
					<textarea rows="2" id="toAddress" class="form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="clearfix">
				<button type="button" class="btn btn-primary mr" onClick="viewCompleteTemplate('print');">Print</button>
				<button type="button" class="btn btn-default" onClick="closePOP()">Cancel</button>
			</div>
		</div>
		</div>
	</div>
</div>
<!-- <script>
function printPage()
  {
  viewCompleteTemplate('print');
  }
</script> -->
<div class="modal viewEVR" style="display: none">
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
		<a href="javascript:void(0)" class="close" onClick="closePOP()">&times;</a>
			<h4>View</h4>
		</div>
		<div class="modal-body">
			
			<div class="form-group">
				<label>Choose Template <span class="text-danger paddingRight">*</span></label>
				<div class="clearfix">
					<select class="chosen-select form-control chose-all-template" data-live-search="true" id="empTemplateId">
					</select>
				</div>
				<input type="hidden" name="hiddenUserId" id="hiddenUserId" value=""/>
			</div>
			<div class="form-group">
				<label>To whom it may concern</label>
				<div class="clearfix">
					<textarea rows="2" id="toAddress" class="form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="clearfix">
				<button type="button" class="btn btn-primary mr" onclick="viewCompleteTemplate();">View</button>
				<button type="button" class="btn btn-default" onClick="closePOP()">Cancel</button>
			</div>
		</div>
		</div>
	</div>
</div>

<div class="modal shareEVR" style="display: none">
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
		<a href="javascript:void(0)" class="close" onClick="closePOP()">&times;</a>
			<h4>Share</h4>
		</div>
		<div class="modal-body">
			<div class="form-group">
				<label>To Whom<span class="text-danger paddingRight">*</span></label>
				<div class="clearfix">
					<label class="radioIput mr">
						<input type="radio" name="whom" id="employee" checked>Employee
					</label>
					<label class="radioIput">
						<input type="radio" name="whom" id="others">Others
					</label>
				</div>
				<div class="clearfix mt10" id="othersEmail" style="display: none;">
				<input type="text" placeholder="Enter email id .." class="form-control">
				</div>
			</div>
			<div class="form-group">
				<label>Choose Template <span class="text-danger paddingRight">*</span></label>
				<div class="clearfix">
					<select class="chosen-select form-control chose-all-template" data-live-search="true">
					</select>
				</div>
			</div>
			<div class="form-group">
				<label>To whom it may concern</label>
				<div class="clearfix">
					<textarea rows="2" class="form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="clearfix">
				<button type="button" class="btn btn-primary mr">Share</button>
				<button type="button" class="btn btn-default" onClick="closePOP()">Cancel</button>
			</div>
		</div>
		</div>
	</div>
</div>

<div class="modal downloadEVR" style="display: none">
	<div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
		<a href="javascript:void(0)" class="close" onClick="closePOP()">&times;</a>
			<h4>Download</h4>
		</div>
		<div class="modal-body">
			<div class="form-group">
				<label>Choose Template <span class="text-danger paddingRight">*</span></label>
				<div class="clearfix">
					<select class="chosen-select form-control chose-all-template" data-live-search="true">
					</select>
				</div>
			</div>
			<div class="form-group">
				<label>To whom it may concern</label>
				<div class="clearfix">
					<textarea rows="2" class="form-control"></textarea>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="clearfix">
				<button type="button" class="btn btn-primary mr" onClick="downloadReport()">Download</button>
				<button type="button" class="btn btn-default" onClick="closePOP()">Cancel</button>
			</div>
		</div>
		</div>
	</div>
</div>


<script>
 $( function() {
   /*  $("#fromDate").datepicker(
		{changeMonth: true});
	 $("#toDate").datepicker(
	 {changeMonth: true}); */
	 
	 
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	 var finalDateFormatEndDate = '';
	 if(locationDate.match("yyyy-MM-dd")){
	     finalDateFormatEndDate = 'yy-mm-dd';
	 }if(locationDate.match("dd-MM-yyyy")){
	     finalDateFormatEndDate = 'dd-mm-yy'; 
	 }if(locationDate.match("MM-dd-yyyy")){
	     finalDateFormatEndDate = 'mm-dd-yy'; 
	 }if(locationDate.match("yyyy-dd-MM")){
	     finalDateFormatEndDate = 'yy-dd-mm'; 
	 }
	 
	 $( "#fromDate" ).datepicker({
	     dateFormat : finalDateFormatEndDate,
	      changeMonth: true,
	      changeYear: true,
// 	      minDate: new Date() ,
	      onSelect : function(selected) {
	      var dt = $(this).datepicker('getDate', '+1d');
	      dt.setDate(dt.getDate());
	      $("#toDate").datepicker("option", "minDate", dt);
	       removeStartValid();
	      } 

	});
	 
	 
	 $( "#toDate" ).datepicker({
	     dateFormat : finalDateFormatEndDate,
	      changeMonth: true,
	      changeYear: true,
// 	    maxDate: new Date() ,
		onSelect : function(selected) {
	      var dt = $(this).datepicker('getDate', '+1d');
	      dt.setDate(dt.getDate());
	      $("#fromDate").datepicker("option", "maxDate", dt);
	       removeEndValid();
	      }});
	 
  });
 
</script> 
<script>
//Print, download and share links script
	function printEVR() {
		$(".sideMask").fadeIn();
		$(".printEVR").fadeIn();
	}
	function closePOP() {
		$(".sideMask").fadeOut();
		$(".printEVR").fadeOut();
		$(".downloadEVR").fadeOut();
		$(".shareEVR").fadeOut();
		$(".viewEVR").fadeOut();
	}
	function downloadEVR() {
		$(".sideMask").fadeIn();
		$(".downloadEVR").fadeIn();
		
	}
	function shareEVR() {
		$(".sideMask").fadeIn();
		$(".shareEVR").fadeIn();
	}
	function viewEVR() {
		$(".sideMask").fadeIn();
		$(".viewEVR").fadeIn();
	}
	
	function commonview(employeeId) {
		$("#hiddenUserId").val(employeeId);
		$.ajax({
	         type : "GET",
	          url: sourceleadUrl+"/getAllEmploymentVerificationListByOrgLocationId?"+tokenParamName+"="+tokenValue,    
	          success :function(json) {
		        	var output = "";
		        	for (var i = 0;i<json.data.length;i++)
					{
	        		 	var opt = json.data[i];
		        		output = output + "<option value="+opt.templateUUID+" selected>"+opt.templateName+"</option>";
					}

		        	$('.chose-all-template').empty();
		        	$('.chose-all-template').append(output);
		        	$('.chose-all-template').trigger("chosen:updated");
	          }
	          
	     });
	}
	
	
	$("#others").click(function(){
		$("#othersEmail").show();
	});
	$("#employee").click(function(){
		$("#othersEmail").hide();
	});
	</script>
	
<script>
	function searchThroughDates(){
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		
		$('#fromDateDiv').empty();
		$('#toDateDiv').empty(); 
		
		if(fromDate == null || fromDate == '' || fromDate == 'undefined'){
			$("#fromDateDiv").append("Start Date is Required") ; 
		}
		
		if(toDate == null || toDate == '' || toDate == 'undefined'){
			$("#toDateDiv").append("End Date is Required") ; 
		}
		
		if(fromDate != null && fromDate != '' && fromDate != 'undefined' && toDate != null && toDate != '' && toDate != 'undefined'){
			getAllEmployeesList(fromDate,toDate);
		}
	}
	
	
	function removeStartValid(){
		$('#fromDateDiv').empty();
	}
	function removeEndValid(){
		$('#toDateDiv').empty(); 
	}

</script>
	
<script>
function getAllEmployeesList(fromDate,toDate){
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var searchValue = $("#employeeDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getAllEmployeesList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&fromDate="+fromDate+"&toDate="+toDate;
	prepareAllEmployeesList(url);
}
</script>	

<script>
function prepareAllEmployeesList(url){
	 	var result = "";
		result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
		result = result + '<table id="dynamicEmployeeListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Employee ID</th>';
		result = result + '<th>Name</th>';
		result = result + '<th>Email</th>';
		result = result + '<th>Phone</th>';
		result = result + '<th>Emp. Type</th>';
		result = result + '<th>From Date</th>';
		result = result + '<th>To Date</th>';
		result = result + '<th>Designation</th>';
		result = result + '<th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#employeeList").empty();
		$("#employeeList").append(result);
		emplyoeesListPagination(url);
}
</script>

<script>
var table = '';
function emplyoeesListPagination(url){
	var colIndex = 0;
	table = $('#dynamicEmployeeListView').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "searchHighlight": true,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 10
        }),
        "columns": [
        	{ "data": "employeeID" },
            { "data": "name" },
            { "data": "email"},
            { "data": "mobilePhone"},
            { "data": "employmentType"},
            { "data": "fromDate"},
            { "data": "toDate"},
            { "data": "designation"},
            { "data": "actions"}
        ],
		 "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "employeeID",
		    	  	"render": function(data, type, user, meta) {
		    	  		var result = '';
		    	  		 if(user.employeeId !=null && user.employeeId !='' && user.employeeId !='undefined'){
								result = result + ' <td><div class="tbldata-dotdot-100" title = "'+user.employeeId+'">'+user.employeeId+'</div></td>';
							}else{
								result = result + ' <td><div class="tbldata-dotdot-100">--</div></td>';
							}
			             return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
		          "data": "name",
		          "render": function ( data, type, user, meta ) {
		        	  var result = '';
		          if(user.userName !=null && user.userName !='' && user.userName !='undefined'){
						 result = result +" <a href='<c:url value='/app/userSummary/"+user.userCode+"'/>'' title = '"+user.userName+"' class='UserTextLeft'>"+user.userName+"</a>";
		          }else{
						result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
					}
		              return result;
		          		
		          }
		          },{
		        "targets": colIndex++,
		        "data": "email",
		        "render": function ( data, type, user, meta ) {
		        	  var result = '';
		        	  if(user.email !=null && user.email !='' && user.email !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot-vsm" title = "'+user.email+'">'+user.email+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot-vsm">--</div></td>';
						}
		        	 return result;
		        }
		      }, {
		          "targets": colIndex++,
		          "data": "mobilePhone",
		          "render": function ( data, type, user, meta ) {
		        	  var result = '';
		        	  if(user.mobilePhone !=null && user.mobilePhone !='' && user.mobilePhone !='undefined'){
							result = result + ' <td><div class="tblminwidth-100" title = "'+user.mobilePhone+'">'+user.mobilePhone+'</div></td>';
						}else{
							result = result + ' <td><div class="tblminwidth-100">--</div></td>';
						}

		        	  return result;
		          }
		        }, {
		            "targets": colIndex++,
		            "data": "employmentType",
		            "sortable": false,
		            "render": function ( data, type, user, meta ) {
		            	var result = '';
 		            	if(user.employmentType !=null && user.employmentType !='' && user.employmentType !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot" title = "'+user.employmentType+'">'+user.employmentType+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						}
 
			              return result;
		            }
		      },
		      {
		          "targets": colIndex++,
		          "data": "fromDate",
		          "sortable": false,
		          "render": function ( data, type, user, meta ) {
		          	var result = '';
		          	if(user.dateofJoin !=null && user.dateofJoin !='' && user.dateofJoin !='undefined'){
						result = result + ' <td><div class="tblminwidth-100" title = "'+user.dateofJoin+'">'+user.dateofJoin+'</div></td>';
					}else{
						result = result + ' <td><div class="tblminwidth-100">--</div></td>';
					}

			              return result;
		          }
		    },
		    {
		        "targets": colIndex++,
		        "data": "toDate",
		        "sortable": false,
		        "render": function ( data, type, user, meta ) {
		        	var result = '';
		        	 if(user.dateOfTermination !=null && user.dateOfTermination !='' && user.dateOfTermination !='undefined'){
						result = result + ' <td><div class="tbldata-dotdot" title = "'+user.dateOfTermination+'">'+user.dateOfTermination+'</div></td>';
					}else{
						result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
					}
		              return result;
		        }
		    },
		    {
			          "targets": colIndex++,
			          "data": "designation",
			          "sortable": false,
			          "render": function ( data, type, user, meta ) {
			          	var result = '';
			         	if(user.designation !=null && user.designation !='' && user.designation !='undefined'){
							result = result + ' <td><div class="tbldata-dotdot" title = "'+user.designation+'">'+user.designation+'</div></td>';
						}else{
							result = result + ' <td><div class="tbldata-dotdot">--</div></td>';
						} 
				        return result;
			          }
			},{
			          "targets": colIndex++,
			          "data": "actions",
			          "sortable": false,
			          "render": function ( data, type, user, meta ) {
			          	var result = '';
					       result = result + '      <td><div class="tblminwidth-75 text-center">             ';
				           result = result + '      <div class="positionRelative actionList-vdse-div">';
				           result = result + '        <ul class="list-unstyled list-inline clearfix actionList-vdse">';
				           result = result + '          <li><a href="javascript:void(0)" title="Print" class="fa fa-print" onClick="printEVR();commonview('+user.employeeId+');"></a></li>';
				           result = result + '          <li><a href="javascript:void(0)" title="Download" class="fa fa-download" onClick="downloadEVR();commonview('+user.employeeId+');"></a></li>';
				           result = result + '          <li><a href="javascript:void(0)" title="Share" class="fa fa-share-alt" onClick="shareEVR();commonview('+user.employeeId+');"></a></li>';
				           result = result + '          <li><a href="javascript:void(0)" title="View" class="fa fa-eye" onClick="viewEVR();commonview('+user.employeeId+')"></a></li>';
				           result = result + '        </ul>';
				           result = result + '      </div>';
				           result = result + '    </div>';
				           result = result + '    </td>';
				        return result;
			          }
		  }],
		    "scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     },
    } );
    $("#dynamicEmployeeListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
}

$("#employeeDataTableSearch").keydown(function(e) {
	   if(e.which === 13) {
		   getAllEmployeesList();
	   }
	});
</script>


<script>
function viewCompleteTemplate(temp){
	var templateUUID = $("#empTemplateId").val();
	var toAddress = $("#toAddress").val();
	var employeeId = $("#hiddenUserId").val();
	var winPrint = window.open(sourceleadUrl+'/previewCompleteTemplateByTemplateUUID?&templateUUID='+templateUUID+'&toAddress='+toAddress+'&employeeId='+employeeId,'_newtab');
	if(temp == 'print')
		winPrint.print();
}
</script> 


<script type="text/javascript">
			var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
			for (var selector in config) {
			  $(selector).chosen(config[selector]);
			}
			$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
			})
			
	//pop function for employeementVarification filter
	
	function employementveriFileterPopUp(){
		alert("helllllllllllo");
	 $("#vendorFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
			
</script>

<jsp:include page="../masterFooter.jsp" />