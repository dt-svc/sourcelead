<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(function() { 
	 getSourceleadTicketList();
	 var loginRoleName = '${SourceLeadWeb_loginUserRole}'
	  if(loginRoleName != 'ROLE_SOURCELEAD_ADMIN'){
		  $('#dynamicTicketListView').DataTable().column(0).visible(false);
		  $('#dynamicTicketListView').DataTable().column(6).visible(false);
	  }
	 
	 $("#filter1ApprovePop").click(function(){
			$("#filter1ApprovedPop").slideToggle(200);
			 $("#filter2ApprovedPop,.action-pop").slideUp(200);
		});
	    $("html").click(function() {
	        $("#filter1ApprovedPop").slideUp(200);
	    });
	    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
	        e.stopPropagation();
	    });	
	
});

</script>
<script>
function getTicketCustomDates(){
	 var dateSelection = $("#dateSelection").val(); 
	 if(dateSelection == 'custom'){
		 document.getElementById("customSearch").style.display="block";
		 $("#jobReportList_custome").attr("disabled", "disabled");
		 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormaTicket = '';
		  if(locationDate.match("yyyy-MM-dd")){
			  finalDateFormaTicket = 'yy-mm-dd';
		  }if(locationDate.match("dd-MM-yyyy")){
			  finalDateFormaTicket = 'dd-mm-yy'; 
		  }if(locationDate.match("MM-dd-yyyy")){
			  finalDateFormaTicket = 'mm-dd-yy'; 
		  }if(locationDate.match("yyyy-dd-MM")){
			  finalDateFormaTicket = 'yy-dd-mm'; 
		  }
   
	  $('#fromDate').datepicker({
			dateFormat : finalDateFormaTicket,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '+1d');
				dt.setDate(dt.getDate());
				$("#toDate").datepicker("option", "minDate", dt);
				removeErrsgFrom();
				
			}
		});
	  
	  $('#toDate').datepicker({
			dateFormat : finalDateFormaTicket,
			changeMonth: true,
			changeYear: true, 
			onSelect : function(selected) {
				var dt = $(this).datepicker('getDate', '-1d');
				dt.setDate(dt.getDate());
				$("#fromDate").datepicker("option", "maxDate", dt);
				removeErrTo();
			}
		});

	 }else{
		 document.getElementById("customSearch").style.display="none";
		 $("#jobReportList_custome").removeAttr("disabled");
		 $("#fromDate").datepicker("destroy");
		 $("#toDate").datepicker("destroy");
		 $("#fromDate").val('');
		 $("#toDate").val('');
		 getSourceleadTicketList();
		 document.getElementById("fromDateDiv").style.display="none";
		 document.getElementById("toDateDiv").style.display="none";
	 }
}

</script>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Issues List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home </a> &raquo; Issues List</div>
    </section>
    
			<div class="project_wrap">
						
			 <div class="positionRelative reportHead clearfix">
			      <ul class="pull-right list-unstyled list-inline">
					<li>
						<div class="col-md-12 dropdown-select tblminwidth-175 paddingLeft paddingRight">
				        <div class="dropdown-select positionRelative">
				        <select class="form-control" name="dateSelection" id="dateSelection" onchange="getTicketCustomDates();">
				            <option value=''>Select Date</option>
				            <option value='Last_7_days' >Last 7 days</option>
				            <option value='Last_15_days'>Last 15 days</option>
				            <option value='1_month' selected>1 month</option>
				            <option value='3_months'>3 months</option>
				            <option value='YTD' >YTD</option>
				            <option value='Last_Year'>Last Year</option>
				            <option value="custom">Custom</option>
				          </select>
				    <!-- Top Leave Takers Custom Date -->
				          <div class="clearfix pull-lefet col-md-12 paddingRight customLeaves" id="customSearch" style="display: none; width: 346px;">
				            <div class="pull-left">
				              <div class="pull-left mr5" style="width: 140px" id="errMsgFrm">
				                <input type="text" class="form-control calendar margin-top0" value="" id="fromDate" name="fromDate"  placeholder="From Date"  readonly="readonly" >
				                <p id="fromDateDiv" style="display: none; font-size: 12px;"><font color="red">From Date Required</font></p>
				              </div>
				              
				              <div class="pull-left mr5" style="width: 140px" id="errMsgTo">
				                <input type="text" class="form-control calendar margin-top0" value="" id="toDate" name="toDate"  placeholder="To Date" field-name="From Date" readonly="readonly" >
				                <p id="toDateDiv" style="display: none; font-size: 12px;"><font color="red">To Date Required</font></p>
				              </div>
				              <button type="button" class="btn btn-primary btn-sm" onclick="getSourceleadTicketList();"><i class="fa fa-search"></i></button>
				            </div>
				          </div>
				      </div>
				      </div>
					</li>  
											
					<li>
						<a href="<c:url value='${appPath}/addSourceleadTicket'/>"  title="Add Issue" class="action newAdBtn mr5">+ Issue</a>
					</li>	
						
					<c:if test="${SourceLeadWeb_orgLocationId == 1}">
					<li id="filter1ApprovePop"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openPop();"></a>
			           </li>
			           </c:if> 		
					    
					  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='ticketDataTableSearch'/><span class="repSearchIcon" onClick=""></span></li>
			        </ul>
			      </div>						
                
                 <div class="project_wrap_table datatable_filter backgroundWhite">
                     <div id="sourceleadTicketListDivId"></div>
                </div>    
				</div>     
		      
 <div class="sideMask"></div>
 <div id="ticketcommentPopup"></div>
  </section>
  
  <script>
  function removeErrsgFrom (){
	  document.getElementById("fromDateDiv").style.display="none";
	  
  }
  function removeErrTo(){
	  document.getElementById("toDateDiv").style.display="none";
  }
  </script>
  
  <script>
  function getSourceleadTicketList(){
	  var flag = true;
	  var dateSelection = $("#dateSelection").val(); 
	  var fromDate = '';
	  var toDate = '';
	  if(dateSelection == 'custom'){
		   fromDate = $("#fromDate").val();
		   toDate = $("#toDate").val();
		  if(fromDate == '' || toDate == ''){
			  flag = false;
			  if(fromDate == ''){
				  document.getElementById("fromDateDiv").style.display="block";
			  }else{
				  document.getElementById("fromDateDiv").style.display="none";
			  }
			  if(toDate == ''){
				  document.getElementById("toDateDiv").style.display="block";
			  }else{
				  document.getElementById("toDateDiv").style.display="none";
			  }
		  }else{
			  document.getElementById("fromDateDiv").style.display="none";
			  document.getElementById("toDateDiv").style.display="none";
		  }
		  
	  }
	  var selectCompanyIds = [];
		$('input:checkbox[name=selectCompanyById]:checked').each(function() 
	     {
			selectCompanyIds.push($(this).val());
		 });
		  var ticketStatus = [];
			$('input:checkbox[name=ticketStatus]:checked').each(function() 
		     {
				ticketStatus.push($(this).val());
			 });
	  if(flag){
	  var sourceleadUrl = '${sourceleadUrl}';
		var searchValue = $("#ticketDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		var url = sourceleadUrl+"/getSourceleadTicketList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&dateSelection="+dateSelection+"&selectCompanyIds="+selectCompanyIds+"&fromDate="+fromDate+"&toDate="+toDate+"&ticketStatus="+ticketStatus;
		prepareSourceleadTicketList(url);
	  }
	  $("#customSearch").hide();
  }
  </script>
<script>
function prepareSourceleadTicketList(url){
	 	var result = "";
		result = result	+ '<div class="clearfix minHeight35 hidden">';
	    result = result	+ '<div class="remove_search">';
		result = result	+ '</div>';
		result = result	+ '</div>';
		result = result	+ '<div class="project_tabs_inn_table project_tabs_inn_table_a">';
		result = result + '<table id="dynamicTicketListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		 result = result + '<th>Issue Id</th>';
	    result = result + '	<th>Title</th>';
        result = result + ' <th>Type</th>';
        result = result + ' <th>Module</th>';
        result = result + ' <th>Requested on</th>';
        result = result + ' <th>Requested by</th>';
        result = result + ' <th>Company Name</th>';
        result = result + ' <th>Status</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#sourceleadTicketListDivId").empty();
		$("#sourceleadTicketListDivId").append(result);
		dynamicTicketListPagination(url);
}

function dynamicTicketListPagination(url){
	 var sourceleadUrl = '${sourceleadUrl}';
	 var loginRoleName = '${SourceLeadWeb_loginUserRole}'
		 var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var colIndex = 0;
   table =  $('#dynamicTicketListView').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columnDefs": [{
		  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
				result = result + ' <td><div class="tbldata-dotdot-100" title = "'+report.ticketGeneratedId+'">'+report.ticketGeneratedId+' </div></td>';
	             return result;
		  	}
	  },{
		  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		 if(loginRoleName == 'ROLE_SOURCELEAD_ADMIN'){
						result = result + ' <td><a href="<c:url value="'+sourceleadUrl+'/sourceleadTicketView?ticketUUID='+report.ticketUUID+'"/>"><div class="tbldata-dotdot proj_show" title = "'+report.ticketTitle+'">'+report.ticketTitle+' <i class="fa fa-binoculars"></i></div></td>';
		  		 }else{
						result = result + ' <td><a href="<c:url value="'+sourceleadUrl+'/sourceleadTicketView?ticketUUID='+report.ticketUUID+'"/>"><div class="tbldata-dotdot-me proj_show" title = "'+report.ticketTitle+'">'+report.ticketTitle+' <i class="fa fa-binoculars"></i></div></td>';
		  		 }
	             return result;
		  	}
	  },
	  {
		  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		if(loginRoleName == 'ROLE_SOURCELEAD_ADMIN'){
					result = result + ' <td><div class="tbldata-dotdot_80" title = "'+report.titcketType+'">'+report.titcketType+'</div></td>';
		  		}else{
					result = result + ' <td><div class="tbldata-dotdot-vsm" title = "'+report.titcketType+'">'+report.titcketType+'</div></td>';
		  		}
	             return result;
		  	}
	  },
	  {
		  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
				result = result + ' <td><div class="tbldata-dotdot-sm" title = "'+report.ticketTabs+'">'+report.ticketTabs+'</div></td>';
	             return result;
		  	}
	  },
	  {
		  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
				result = result + ' <td><div class="tbldata-dotdot_80" title = "'+report.requestedOn+'">'+report.requestedOn+'</div></td>';
	             return result;
		  	}
	  },
	  {
		  	"targets": colIndex++,
		  	"data": "",
		  	"sortable": false,
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		if(loginRoleName == 'ROLE_SOURCELEAD_ADMIN'){
					result = result + ' <td><div class="tbldata-dotdot-100" title = "'+report.requestedBy+'">'+report.requestedBy+'</div></td>';
		  		}else{
					result = result + ' <td><div class="tbldata-dotdot-sm" title = "'+report.requestedBy+'">'+report.requestedBy+'</div></td>';
		  		}
	             return result;
		  	}
	  },
	  {
		  	"targets": colIndex++,
		  	"data": "",
		  	"sortable": false,
		  	"render": function(data, type, report, meta) {
		  		var result = "";
				result = result + ' <td><div class="tbldata-dotdot-100" title = "'+report.companyName+'">'+report.companyName+'</div></td>';
	             return result;
		  	}
	  },
	  {
		  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		 if(loginRoleName == 'ROLE_SOURCELEAD_ADMIN' || orgLocationId == '1' ){
		  			result = result + ' <td><div class="tbldata-dotdot-100" style="width:100px">';
			  		result = result + ' <select id="ticketStatus" name="ticketStatus" onchange="changeStatus(\''+report.ticketUUID+'\',this);">';
					result = result + '<option value="Open"  '+ (report.ticketStatus == "Open" ? "selected" : '') +'>Open</option>';
					result = result + '<option value="On-Hold"  '+ (report.ticketStatus == "On-Hold" ? "selected" : '') +'>On-Hold</option>';
					result = result + '<option value="Closed"  '+ (report.ticketStatus == "Closed" ? "selected" : '') +'>Closed</option>';
					result = result + '<option value="In-progress"  '+ (report.ticketStatus == "In-progress" ? "selected" : '') +'>In-progress</option>';
					result = result + '<option value="Invalid"  '+ (report.ticketStatus == "Invalid" ? "selected" : '') +'>Invalid</option>';
					result = result + '<option value="Duplicate"  '+ (report.ticketStatus == "Duplicate" ? "selected" : '') +'>Duplicate</option>';

					result = result + ' </select>';
			  		result = result + '</div></td>';
		  		 }else{
		  			result = result + ' <td><div class="tbldata-dotdot-100"> '+report.ticketStatus+'</div></td>';
		  		 }
		  		
	             return result;
		  	}
	  },
	  {
		  	"targets": colIndex++,
		  	"data": "",
		  	"sortable": false,
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		var loginUserId = '${SourceLeadWeb_user.id}'
				result = result + ' <td><div class="tblminwidth-75 text-center">';
				  result = result + '<ul class="list-unstyled list-inline clearfix actionList-vdse">';
				  if(loginRoleName == 'ROLE_ADMIN' || loginUserId == report.userId || loginUserId == report.createdByUserId){
					  result = result + ' <li><a href="<c:url value="'+sourceleadUrl+'/addSourceleadTicket?ticketUUID='+report.ticketUUID+'"/>"  title="Add Ticket" class="fa fa-pencil" ></a></li>';
				  }
				  result = result + ' <li><a href="javascript:void(0)" title="Comment" class="fa fa-comments" onClick="ticketComment(\''+report.ticketUUID+'\')"></a></li>';
				  result = result + ' </ul>';
				  result = result + ' </div>';
				  result = result + ' </td>';
	             return result;
		  	}
	  }
        ],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[4,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
	    	 
    } );
    $("#dynamicTicketListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
	
}

$(function() {
    $("#ticketDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getSourceleadTicketList();
    		   var loginRoleName = '${SourceLeadWeb_loginUserRole}'
    				  if(loginRoleName != 'ROLE_SOURCELEAD_ADMIN'){
    					  $('#dynamicTicketListView').DataTable().column(0).visible(false);
    				  } 
    	   }
    	});
});
function changeStatus(ticketUUID,e){
	var ticketStatus = e.value;
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		 type : "POST",
		 url : sourceleadUrl+ "/changeTicketStatus?${_csrf.parameterName}=${_csrf.token}&ticketStatus="+ticketStatus+"&ticketUUID="+ticketUUID,
		 success : function(result) {
			 
			 if (result && result.response === "INVALID_DATA") {
	    		    showError(result.responseMessage);
	    		} else if (result.response === "AWKWARD") {
	    		    location.href = "/errorPage";
	    		} else if (result.response === "SUCCESS") {
	    			location.replace(sourceleadUrl+"/sourceleadTicketList");			 			
	    		} else if (result.indexOf('Sign In') !== -1) {
	    			location.replace(sourceleadUrl);
	    		} else {
	    		    showError("Operation failed, please try again");
	    		}
			 
		},
	});
	
	
}
function ticketComment(ticketUUID) {
	var result = "";
	result = result + '<div class="modal ticketComments" style="display: none">';
	result = result + '<div class="modal-dialog">';
	result = result + '<div class="modal-content">';
	result = result + '<div class="modal-header">';
	result = result + '<a href="javascript:void(0)" class="close" onClick="closeComment()">&times;</a>';
	result = result + '<h4>Issue Comment</h4>';
	result = result + '</div>';
	result = result  +'<form  id="sourceleadTicketComment_form" name="sourceleadTicketComment_form" method="post" > ';
	result = result + '<div class="modal-body">';
	result = result + '<div class="form-group">';
	result = result + '<label>Comment <span class="text-danger paddingLeft">&nbsp;*</span> </label>';
	result = result + '<div class="clearfix">';
	result = result + '<textarea rows="3" class="form-control" id="ticketComment" name="ticketComment" field-name="Comment" data-validation="required validate_length length1-500"></textarea>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';
	
	result = result + '<div class="modal-footer">';
	result = result + '<div class="clearfix">';
	result = result + '<button type="button" class="btn btn-primary mr" onclick="saveTicketComments(\''+ticketUUID+'\');">Save</button>';
	result = result + '<button type="button" class="btn btn-default" onClick="closeComment()">Cancel</button>';
	result = result + '</div>';
	result = result + '</div>';
	result = result  +' </form>';
	result = result + '</div>';
	result = result + '</div>';
	result = result + '</div>';
	
	$("#ticketcommentPopup").empty();
	$("#ticketcommentPopup").append(result);
	$(".sideMask").fadeIn();
	$(".ticketComments").fadeIn();
}
function closeComment() {
	$(".sideMask").fadeOut();
	$(".ticketComments").fadeOut();
}
</script>
<script>
function saveTicketComments(ticketUUID){
	if ($('#sourceleadTicketComment_form').validate(false, validationSettings)) {
		var sourceleadUrl = '${sourceleadUrl}';
		var formData = getFormData("sourceleadTicketComment_form");
		$.ajax({
			 type : "POST",
			 data: formData,
			 url : sourceleadUrl+ "/saveSourceleadTicketComment?${_csrf.parameterName}=${_csrf.token}&ticketUUID="+ticketUUID,
			 success : function(result) {
				location.replace(sourceleadUrl+"/sourceleadTicketList");
				closeComment(); 
			},
		});
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#sourceleadTicketComment_form', function() {
	$("#sourceleadTicketComment_form").showHelpOnFocus();
	$("#sourceleadTicketComment_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	
</script>
<script>
function closeTicketFilterPop(){
	$("#invoiceFilterId").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openPop(){
	 $("#invoiceFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
function resetTicketFilter(){
	$("ul#myTicketUL > li input:checkbox").prop("checked",false);
	getSourceleadTicketList();
	
}
function myTicketFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTicketInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTicketUL");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

</script>

<div class="advancedsearch-mask"></div>
      <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-small" id="invoiceFilterId">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeTicketFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
					  <div class="col-md-4">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Status</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="paymnetFilter">
									<li class="checkBox positionRelative" ><input type="checkbox"  value="Open" name="ticketStatus"><label for="onboard3">Open</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox"  value="On-Hold" name="ticketStatus"><label for="onboard2">On-Hold</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox"  value="Closed" name="ticketStatus"><label for="onboard4">Closed</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="In-progress" name="ticketStatus"><label for="onboard1">In-progress</label></li>
                        	        <li class="checkBox positionRelative" ><input type="checkbox"  value="Invalid" name="ticketStatus"><label for="onboard4">Invalid</label></li>
                        	        <li class="checkBox positionRelative" ><input type="checkbox"  value="Duplicate" name="ticketStatus"><label for="onboard4">Duplicate</label></li>
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
						<c:if test="${!empty orgLocationsList}">
						<div class="col-md-4 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myTicketInput" onkeyup="myTicketFunction()" placeholder="Companies" title="Type in a tag">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myTicketUL">
									<c:forEach var="orgList" items="${orgLocationsList}">  
				                      	<li class="checkBox positionRelative"><input  type="checkbox" name="selectCompanyById" value ="${orgList.id}" ><label>${orgList.orgLocationName}</label></li>
								 	 </c:forEach>
								</ul>
							 </div> 
						</div>
						</c:if>
					  </div>
					  
					  
					  
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="closeTicketFilterPop();getSourceleadTicketList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetTicketFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
 <jsp:include page="masterFooter.jsp" />	