<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Margin sheet</h2>
      </div>
          <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/financialReportsList'/>">Financial Reports</a> &raquo; Margin sheet</div>
    </section>
    <div class="vendor_wrap mt">
      <div class="clearfix"></div>
      <div class="positionRelative reportHead clearfix">
		  		 <!--  Maring Addon -->
<div class="clearfix mb10 mt5 col-md-7">
	<div class="row mt5">
   <div class="col-xs-12 col-sm-3 col-md-4">
   <div class="input-group payRate">
      <span class="input-group-addon">Pay Rate</span>
      <input type="text" class="form-control input-sm" placeholder="Pay Rate" id="totalPayrate" value="">
    </div>
    </div>
   <div class="col-xs-12 col-sm-3 col-md-4">
   <div class="input-group billRate">
      <span class="input-group-addon">Bill Rate</span>
      <input type="text" class="form-control input-sm" placeholder="Bill Rate" id="totalbilRate" value="">
    </div>
    </div>
   <div class="col-xs-12 col-sm-3 col-md-4">
   <div class="input-group marginWidget">
      <span class="input-group-addon">Margin</span>
      <input type="text" class="form-control input-sm" placeholder="Margin" id="totalMarginRate" value="">
    </div>
    </div>
	</div>
</div>
		  <!-- Maring Addon -->
		  
		  
        <ul class="pull-right list-unstyled list-inline">
        <li>
<!-- 		  <span class="pull-left refreIcon noPad"> -->
<!-- 			  <a href="javascript:void(0)" class="refeIcon" title="Refresh" onclick="resetFilter();"></a> -->
<!-- 		  </span> -->
			<span class="pull-left">
					<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openFilterPop();"></a></li>
			</span>
		</li>
		
		      	  <li>
	      	  <div class="dropdown-select positionRelative repSelWrap pull-left ml">
          <select class="form-control repSelFiled" id="reportTypeId" onChange="showCustomFields(this); flushCustom()">
			  <!-- <option value="today">Today</option>
			  <option value="yesterday">Yesterday</option> -->
			  <option selected="selected" value="today">Today</option>
			  <option value="Last_7_days">Last 7 days</option>
			  <option value="Last_15_days">Last 15 days</option>
			  <option value="1_month">Last 1 month</option>
			  <option value="3_months">Last 3 months</option>
			  <option value='YTD' >YTD</option>
	          <option value='Last_Year'>Last Year</option>
	          <option value="custom">Custom</option> 
			</select>
		<div id="runButtonDiv" class="repSelBtn">
<!-- 			<button type="button" class="btn btn-primary btn-sm" id="customDateApplicant" onclick="getApplicantsJoinedList();" style="display: none">Run</button> -->
	        <button type="button" class="btn btn-primary btn-sm" id="customDateRecurter" onclick="filter(); getMarginSheetList();" style="display: block">Run</button>
        </div> 
          
          <div class="clearfix customLeaves repCustomDate" id="customSearch">
              <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="" id="ReportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date *">
				  <p id="reportsStartDate"></p>
              </div>
              
              <div class="repCustomDateInn">
               <input type="text"  class="form-control calendar" value="" id="ReportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date *">
				 <p id="reportsEndDate"></p>
              </div>
              
              <div class="repCustomDateBtn"><button type="button" id="recurter" class="btn btn-primary btn-sm" onclick="getMarginSheetList();" style="display: block"><i class="fa fa-search"></i></button></div>
<!--                        <div class="repCustomDateBtn"><button type="button" id="applicant" class="btn btn-primary btn-sm" onclick="getApplicantsJoinedList();" style="display: none"><i class="fa fa-search"></i></button></div> -->
          </div>
          
        </div>
	      	  </li>
					<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="exportOptions();"></a>
						<div class="newActPop" style="display: none;">
							<span>
			           	   		<a href="javascript:void(0)" onclick="exportSheet('Pdf');" title="PDF">Export PDF</a>
								<a href="javascript:void(0)" onclick="exportSheet('Excel');" title="Excel">Export Excel</a>            
							</span>
						</div>
					</li>
          <li class="">
            <input class="repSearchBar" placeholder="Search" id="dynamicUserDataTableSearch" type="text">
            <span class="repSearchIcon" onclick="getMarginSheetList();"></span></li>
        </ul>
        <div id="emptyPopup"></div>
	 <div class="clearfix"></div>	  
		  

		  <input type="hidden" id="noMarginReportList" name="noMarginReportList"  value="0"/>
      </div>
   <div id="previousOfferDiv" style="display: block"></div>
    </div>
  </section>
      <script type="text/javascript">
      window.onload = function(e){ 
    		 getClientList();
    		 getMarginSheetList();
    		 
    	}
      
 </script>
 <script>
 function exportOptions(){
		$(".newActPop").slideToggle(200);
		$(".action-pop").slideUp(200);
	}
 function candidtaeCancel(){
		$("#emptyPopup").empty();
		$(".project_inn_mask").fadeOut(200);
	}
	$(function() {
		var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
		  var finalDateFormatRepo = getJSDateFormat(locationDate);
		  
		$('#ReportStartDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			onSelect : function(selected) {
				/* var dt = new Date(selected);
				//var dt = toDate("#ReportStartDate");
				dt.setDate(dt.getDate() + 1);
				$("#ReportEndDate").datepicker("option", "minDate", dt); */
				$("#ReportEndDate").datepicker("option", "minDate", selected);
				addValidClass();
			}
		
		});
		
		$('#ReportEndDate').datepicker({
			changeMonth: true,
			changeYear: true, 
			dateFormat : finalDateFormatRepo,
			beforeShow: function(){
		           this.value='';
		     },
			onSelect : function(selected) {
				var dt = new Date(selected);
				/* //var dt = toDate("#ReportEndDate");
				dt.setDate(dt.getDate() - 1);
				$("#ReportStartDate").datepicker("option", "maxDate", dt);
				 $( "#ReportStartDate" ).datepicker({  maxDate: new Date() }); */
				 $("#ReportStartDate").datepicker("option", "maxDate", selected);
				addValidClass();
			}
		});
		
	});
	function filter(){
		$("#dynamicUserDataTableSearch").val('');
	}
</script>	
<script type="text/javascript">
	function flushCustom() {
		$('#ReportStartDate').val('');
		$('#ReportEndDate').val('');
		$.datepicker._clearDate('#ReportStartDate');
		$.datepicker._clearDate('#ReportEndDate'); 
	}
</script>
<script type="text/javascript">
function exportSheet(exportType){
	   var sourceleadUrl ='${sourceleadUrl}';
		var searchValue = $("#dynamicUserDataTableSearch").val();
		var checkEmptyList = $("#noMarginReportList").val();
		searchValue = searchValue ? searchValue : "";
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		var timePeriod = $("#reportTypeId").val();
		var startDate = '';
		 var endDate = '';
	    if(timePeriod == 'custom'){
	    	startDate = $("#ReportStartDate").val();
			  endDate = $("#ReportEndDate").val();
			  if(startDate == null || startDate == '' || endDate == null || endDate == ''){
				  if(startDate == null || startDate == ''){
					  document.getElementById("reportsStartDate").style.display="block";
					  $("#reportsStartDate").empty().append("Start Date is Required") ; 
				  }
				  
				  if(endDate == null || endDate == ''){
					  document.getElementById("reportsEndDate").style.display="block";
					  $("#reportsEndDate").empty().append("End Date is Required") ; 
				  }
				  flag = false;
			  }
		 }else{
			 document.getElementById("reportsStartDate").style.display="none";
			 document.getElementById("reportsEndDate").style.display="none";
		 } 
		var clients = [];
		$('input:checkbox[name=clientNameById]:checked').each(function() {
			clients.push($(this).val());
		});
		if(checkEmptyList !=0){
		window.location.href = sourceleadUrl+"/getMarginSheetListExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate+"&clientId="+clients+"&exportType="+exportType;
		}else{
			var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='candidate_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='candidtaeCancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='candidtaeCancel();'></b>";
			result = result +"	</div>";
			$("#emptyPopup").append(result);
			$("#candidate_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
		}
		
}
</script>
 <script type="text/javascript">
       function getMarginSheetList(){
    	   var sourceleadUrl ='${sourceleadUrl}';
			var searchValue = $("#dynamicUserDataTableSearch").val();
			searchValue = searchValue ? searchValue : "";
			var orgLocationId = '${SourceLeadWeb_orgLocationId}';
			var timePeriod = $("#reportTypeId").val();
			var startDate = '';
			 var endDate = '';
		    if(timePeriod == 'custom'){
		    	startDate = $("#ReportStartDate").val();
				  endDate = $("#ReportEndDate").val();
				  if(startDate == null || startDate == '' || endDate == null || endDate == ''){
					  if(startDate == null || startDate == ''){
						  document.getElementById("reportsStartDate").style.display="block";
						  $("#reportsStartDate").empty().append("Start Date is Required") ; 
					  }
					  
					  if(endDate == null || endDate == ''){
						  document.getElementById("reportsEndDate").style.display="block";
						  $("#reportsEndDate").empty().append("End Date is Required") ; 
					  }
					  flag = false;
				  }
			 }else{
				 document.getElementById("reportsStartDate").style.display="none";
				 document.getElementById("reportsEndDate").style.display="none";
			 } 
			var clients = [];
			$('input:checkbox[name=clientNameById]:checked').each(function() {
				clients.push($(this).val());
			});
		    var url =  sourceleadUrl+"/getMarginSheetList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&timePeriod="+timePeriod+"&customStartDate="+startDate+"&customEndDate="+endDate+"&clientId="+clients;
		    marginSheetList(url);
}
 function marginSheetList(url){
	 var result = "";
     result = result +'<div id="marginSheet" class="require_wrap_table tableFirstSecond clearfix datatable_filter">';
     result = result +'<table id="marginSheetPagination" class="display" cellspacing="0" width="100%" >'; 
     result = result +'<thead>'; 
     result = result + '<tr>';
		result = result + '<th>Associate ID</th>';
		result = result + '<th>Associate Name</th>';
		result = result + '<th>Assoicate Join Date</th>';
		result = result + '<th>Project Name</th>';
		result = result + '<th>Project Duration</th>';
		result = result + '<th>Client Name</th>';
		result = result + '<th>Pay Rate</th>';
		result = result + '<th>Bill Rate</th>';
		result = result + '<th>Margin</th>'; 
		result = result + '</tr>';
     result = result +'</thead>';
     result = result +'<tbody>'; 
     result = result +' </tbody>';
     result = result +'</table>';
     result = result +'</div>';
     $("#previousOfferDiv").empty();
     $("#previousOfferDiv").append(result);
      marginpagination(url);
	 
 }
</script>

<!-- <script>
function marginpagination(url){
	var colIndex = 0;
    $('#marginSheetPagination').DataTable({ 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline({
          url: url,
          method: 'POST',
          pages: 10
        }),
});
    }
</script> -->


<script>
function marginpagination(url){
	$("#totalPayrate").val("");
	$("#totalbilRate").val("");
	$("#totalMarginRate").val("");
	
	var colIndex = 0;
    $('#marginSheetPagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "associateId" },
            { "data": "associateName"},
            { "data": "associateJoinedDate"},
            { "data": "projectName"},
            { "data": "projectDuration"},
            { "data": "clientName"},
            { "data": "payRate"},
            { "data": "billRate"},
            { "data": "margin"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "associateId",
	    	  	"render": function(data, type, opt, meta) {
// 	    	  		 $("#noApplicantJoinedList").val(1); 
	    	  		var result = "";
	    	  		$("#noMarginReportList").val(1);
	    	  		 if(opt.employeeId !=null && opt.employeeId !='' && opt.employeeId !='undefined'){
							result = result + ' <div title='+opt.employeeId+' class="clearfix mt5 mb5">'+opt.employeeId+'</div>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "associateName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.employeeName!=null && opt.employeeName !='' && opt.employeeName !='undefined'){
            		
                		  result = result + '<td><div><span title='+opt.employeeName+'>'+opt.employeeName+'</span></div></td>';
            		  }
            		else{
						result = result + ' <td><div>--</div></td>';
					}
            	  return result;
              		
              }
	          },{
		    	  "targets": colIndex++,
	              "data": "associateJoinedDate",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.associateJoinedDate!=null && opt.associateJoinedDate !='' && opt.associateJoinedDate !='undefined'){
	            		
	                		  result = result + '<td><div><span title='+opt.associateJoinedDate+'>'+opt.associateJoinedDate+'</span></div></td>';
	            		  }
	            		else{
							result = result + ' <td><div>--</div></td>';
						}
	            	  return result;
	              		
	              }
		          },{
			    	  "targets": colIndex++,
		              "data": "projectName",
		              "render": function ( data, type, opt, meta ) {
		            	  var result = "";
		            	  if(opt.projectName!=null && opt.projectName !='' && opt.projectName !='undefined'){
		            		
		                		  result = result + '<td><div><span title='+opt.projectName+'>'+opt.projectName+'</span></div></td>';
		            		  }
		            		else{
								result = result + ' <td><div>--</div></td>';
							}
		            	  return result;
		              		
		              }
			          },{
				    	  "targets": colIndex++,
			              "data": "projectDuration",
			              "render": function ( data, type, opt, meta ) {
			            	  var result = "";
			            	  if(opt.projectDuration!=null && opt.projectDuration !='' && opt.projectDuration !='undefined'){
			            		
			                		  result = result + '<td><div><span title='+opt.projectDuration+'>'+opt.projectDuration+'</span></div></td>';
			            		  }
			            		else{
									result = result + ' <td><div>--</div></td>';
								}
			            	  return result;
			              		
			              }
				          },
	      
	      {
	    	  "targets": colIndex++,
              "data": "clientName",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.clinetName !=null && opt.clinetName !='' && opt.clinetName !='undefined'){
                		  result = result + '<td><div><span title='+opt.clinetName+'>'+opt.clinetName+'</span></div></td>';
            		}else{
						result = result + ' <td><div>--</div></td>';
					}
            	  return result;
              		
              }
	          },
	          {
		    	  "targets": colIndex++,
	              "data": "payRate",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  if(opt.payRate !=null && opt.payRate !='' && opt.payRate !='undefined'){
	                		  result = result + '<td><div><span title='+opt.payRate+'>'+opt.payRateCurrency+' '+opt.payRate+' '+opt.payRateType+'</span></div></td>';
	            		}else{
							result = result + ' <td><div>--</div></td>';
						}
	            	  return result;
	              		
	              }
		          },
	          {
            "targets": colIndex++,
            "data": "billRate",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	
   				if(opt.billRate != null && opt.billRate != '' && opt.billRate !='undefined'){
   					result = result + '<td><div><span>'+opt.billRateTypeCurrency+' '+opt.billRate+' '+opt.billRateType+'</span></div></td>';
            	 }else{
            		 result = result + '<td><div><span>---</span></div></td>';
            	 }
   				return result;
            }
          } ,
          {
              "targets": colIndex++,
              "data": "margin",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	    	  		   if(opt.marginRate != null && opt.marginRate !='' && opt.marginRate != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.marginRate+'</span></div></td>';
	    	  			
	    	  		   }else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		   }
	    	  		   if(opt.totalPayRate != null && opt.totalPayRate !='' && opt.totalPayRate != 'undefined'){
	    	  			 $("#totalPayrate").val(opt.totalPayRate);		    	  			
		    	  		   }
	    	  		   if(opt.totalbilRate != null && opt.totalbilRate !='' && opt.totalbilRate != 'undefined'){
		    	  			 $("#totalbilRate").val(opt.totalbilRate);		    	  			
			    	  		   }
	    	  		   if(opt.totalMarginRate != null && opt.totalMarginRate !='' && opt.totalMarginRate != 'undefined'){
		    	  			 $("#totalMarginRate").val(opt.totalMarginRate);		    	  			
			    	  		   }
	    	  		   
	    	  		return result;
              }
            }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
// 	    	"aaSorting": [[3,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#offerpagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dynamicUserDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getMarginSheetList();
    	   }
    	});
}

</script>

 <script>
 function closeApplicantFilterPop(){
		$("#invoiceFilterId").hide();
		$(".advancedsearch-mask").fadeOut();
	}
 function openFilterPop(){
	 $("#invoiceFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
 </script>
    <div class="advancedsearch-mask"></div>
          <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search-medium" id="invoiceFilterId">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeApplicantFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
					  <div class="col-md-3 hidden">
						<div class="row">
				
						  </div>
						  <div class="clearfix pull-left" id="custom-selects" style="display: none; width: 346px;" >
							<div class="pull-left">
							<div class="pull-left mr5" style="width: 140px" id="custFromDateDiv">
							<input value="" name="Date" id="custFromDate" placeholder="From" class="form-control calendar input-sm margin-top0" type="text">
							</div>
							<div class="pull-left mr5" style="width: 140px" id="custToDateDiv">
							<input value="" name="Date" id="custToDate" placeholder="To" class="form-control calendar input-sm margin-top0" type="text">
							</div>
							</div>
					    </div>
						</div>
						
						 
	                 
						<div class="col-md-12 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
								<input type="text" id="myClientInput" onkeyup="myClientFunction()" placeholder="Clients" title="Type in a client">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
								<ul class="list-unstyled positionRelative filterSearch mt5" id="myClinetListUL">
								</ul>
							 </div> 
						</div>
					  </div>
					  
					  
					  
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
<!-- 							<input type="button" value="Apply" id="applicant_search_requiremet" onclick="closeApplicantFilterPop();getApplicantsJoinedList();" style="display: none" class="btn btn-sm btn-success mr" /> -->
							<input type="button" value="Apply" id="recruiter_search_requiremet" onclick="closeApplicantFilterPop();getMarginSheetList();" style="display: inline-block" class="btn btn-sm btn-success mr" />
							<input type="button" value="Reset" id="clear_search" onclick="resetApplicantFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
				
				<script>
function getClientList(){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/getClientList?${_csrf.parameterName}=${_csrf.token}",
		success : function(data) {
			var result = "";
			  for (var i=0; i<data.length; i++)
				{
				var report = data[i];
				result = result + '<li class="checkBox positionRelative">';
				result = result + '<input  type="checkbox" name="clientNameById" value ='+report.orgRelId+'><label>'+report.clientName+'</label>';
				result = result + '</li>';
				}
			  $("#myClinetListUL").empty();
			  $("#myClinetListUL").append(result);

		}
	});
}
function myClientFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myClientInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myClinetListUL");
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

<script>
function showCustomFields(e){
    var frequency = e.value;
    if(frequency=="custom"){
    	document.getElementById("customSearch").style.display="block";
		 document.getElementById("runButtonDiv").style.display="none";
	}
	if(frequency != "custom"){
		document.getElementById("customSearch").style.display="none";
	    document.getElementById("reportsStartDate").style.display="none";
		document.getElementById("reportsEndDate").style.display="none";
		document.getElementById("runButtonDiv").style.display="block";
	}
    
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

      $( "#custFromDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
            maxDate: new Date() ,
            onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custToDate").datepicker("option", "minDate", dt);
             removeStartValid();
            } 
      
      });
      
      $( "#custToDate" ).datepicker({
           dateFormat : finalDateFormatEndDate,
            changeMonth: true,
            changeYear: true,
          	maxDate: new Date() ,
      		onSelect : function(selected) {
            var dt = $(this).datepicker('getDate', '+1d');
            dt.setDate(dt.getDate());
            $("#custFromDate").datepicker("option", "maxDate", dt);
             removeEndValid();
            }});
}
function removeStartValid(){
    $('#custFromDateDiv').find('p.jquery_form_error_message').remove();
}
function removeEndValid(){
    $('#custToDateDiv').find('p.jquery_form_error_message').remove();
}

</script>
<script>
function resetApplicantFilter(){
	$("#myClientInput").val('');
	$("ul#myClinetListUL > li input:checkbox").prop("checked",false);
	getMarginSheetList();
}
</script>
				<!-- 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 
<script src="../js/bootstrap-datetimepicker.min.js"></script> 
<script src="../js/jquery.timepicker.js"></script> 
<script src="../js/bootstrap.min.js"></script> 
<script src="../js/bootstrap-select.min.js"></script> 
<script src="../js/masterHeaderAction.js"></script> 
<script src="../js/jquery-ui.min.js"></script> 
<script src="../js/chosen.jquery.min.js"></script> 
<script type="text/javascript" src="../js/jquery.mousewheel.js"></script> 
<script type="text/javascript" src="../js/jquery.jscrollpane.min.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/amcharts.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/serial.js"></script> 
<script type="text/javascript" src="https://www.amcharts.com/lib/3/pie.js"></script>  -->
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
	});
</script>
<jsp:include page="../masterFooter.jsp" />