<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <!-- Header Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
       <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; <a href="<c:url value='${appPath}/reportsDashboard'/>">Reports Dashboard</a> &raquo; <a href="<c:url value='${appPath}/performanceReportList'/>">Performance Reports</a> &raquo; Applicants Joined Report  </div>
    </section>
       
    <!-- Project -->
    <div class="timesheet_out">
      <div class="clients_wrap">
	      <div class="positionRelative reportHead clearfix">
	      
	      
	      
	      <span class="pull-left refreIcon" id="advanceSearchDiv">
<!-- 	      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetApplicantFilter();"></a> -->
	      </span>
	      <ul class="pull-right list-unstyled list-inline">
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
		<div id="runButtonDiv" class="repSelBtn"><button type="button" class="btn btn-primary btn-sm" id="customize_btn" onclick="requirementJoinedList();">Run</button></div> 
          
          <div class="clearfix customLeaves repCustomDate" id="customSearch">
              <div class="repCustomDateInn">
                <input type="text" class="form-control calendar" value="" id="ReportStartDate" name="StartDate" field-name="Start Date" data-validation="required" readonly="readonly" placeholder="Start Date *">
				  <p id="reportsStartDate"></p>
              </div>
              
              <div class="repCustomDateInn">
               <input type="text"  class="form-control calendar" value="" id="ReportEndDate" name="EndDate" field-name="End Date" data-validation="required" readonly="readonly" placeholder="End Date *">
				 <p id="reportsEndDate"></p>
              </div>
              
              <div class="repCustomDateBtn"><button type="button" class="btn btn-primary btn-sm" onclick="requirementJoinedList();"><i class="fa fa-search"></i></button></div>
          </div>
          
        </div>
	      	  </li>
					<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="openFilterPop();"></a></li>
					
					<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
						<div class="newActPop" style="display: none;">
							<span>
			           	   		<a href="javascript:void(0)" onclick="projectExport('Pdf');" title="PDF">Export PDF</a>
								<a href="javascript:void(0)" onclick="projectExport('Excel');" title="Excel">Export Excel</a>            
							</span>
						</div>
					</li>
			  <li class=""><input type="text" class="repSearchBar" placeholder="Search" id="dataTableSearch"><span class="repSearchIcon" onclick="filter();"></span></li>
	        </ul>
	      </div>
        <div class="timesheet_head">
      
       </div>
        <div class="clients_wrap_table datatable_filter">
        
        <div id="previousOfferDiv"></div>
        
        
        </div>
      </div>
      <div class="side_mask">
        <div class="proj_close"></div>
      </div>
    </div>
  </section>
  
  <script>
 $(document).ready(function() {
	 requirementJoinedList();
	 getClientList();
	 getTeamsList();
 });
 
 </script>
 
 <script type="text/javascript">
       function requirementJoinedList(){

    	   var sourceleadUrl ='${sourceleadUrl}';
   		   var tab = '${tab}';
    	   var frequency=$("#reportTypeId").val();
    	    var fromDate='';
    	    var toDate='';
    		var searchValue = $("#dataTableSearch").val();
    		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
    		searchValue = searchValue ? searchValue : "";
    		var employmentType = [];
    		 $('input:checkbox[name=employmentType]:checked').each(function() 
			 {
    			employmentType.push($(this).val());
			 });
    		 var clientNameById = [];
    		 $('input:checkbox[name=clientNameById]:checked').each(function() 
			 {
    			 clientNameById.push($(this).val());
			 });
    		 var teamNameById = [];
    		 $('input:checkbox[name=teamNameById]:checked').each(function() 
			 {
    			 teamNameById.push($(this).val());
			 });
    		 var incentivizedType = [];
    		 $('input:checkbox[name=incentivizedType]:checked').each(function() 
    		 {
    			 incentivizedType.push($(this).val());
    		 });
    		 var searchType = $('input[name=searchType]:checked').val();
    	    if(frequency == "custom"){
    	        var fromDate = $("#ReportStartDate").val();
    	  		  var toDate = $("#ReportEndDate").val();
    	    	if(fromDate == ''){
    	    		  document.getElementById("reportsStartDate").style.display="block";
    				  $("#reportsStartDate").empty().append("Start Date is Required") ; 
    	        }
    	        if(toDate == ''){
    	        	 document.getElementById("reportsEndDate").style.display="block";
   				  $("#reportsEndDate").empty().append("End Date is Required") ; 
    	        }
    	        if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && (toDate != '' && toDate != 'undefined' && toDate != null)){
        	        var url = sourceleadUrl+"/applicantList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType;
        		    prepareOfferList(url);
    	        }
    	    }else{
    	    	 document.getElementById("reportsStartDate").style.display="none";
    			 document.getElementById("reportsEndDate").style.display="none";
    	        var url = sourceleadUrl+"/applicantList?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType;
    		    prepareOfferList(url);
    	    } 
 
}
</script> 


<script>
function prepareOfferList(url){
        var result = "";
        result = result +'<table id="offerpagination" class="display" cellspacing="0" width="100%" >'; 
        result = result +'<thead>'; 
        result = result + '<tr>';
		result = result + '<th>Applicant name</th>';
		result = result + '<th>Contact Number</th>';
		result = result + '<th>Job Title</th>';
		result = result + '<th>Client</th>';
		result = result + '<th>Source</th>';
		result = result + '<th>Date Of Join</th>';
		result = result + '<th>Payment Term</th>';
		result = result + '<th>Generate Invoice</th>';
		result = result + '</tr>';
        result = result +'</thead>';
        result = result +'<tbody>'; 
        result = result +' </tbody>';
        result = result +'</table>';
        $("#previousOfferDiv").empty();
        $("#previousOfferDiv").append(result);
         offerpagination(url);
}
</script>

<script>
function offerpagination(url){
	var colIndex = 0;
    $('#offerpagination').DataTable( { 
    		"processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'POST',
            pages: 10
        }),
        "columns": [
            { "data": "applicantName" },
            { "data": "contactNumber"},
            { "data": "job"},
            { "data": "client"},
            { "data": "souorce"},
            { "data": "joinDate"},
            { "data": "paymentTerm"},
            { "data": "generateInvoice"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "applicantName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 if(opt.candidateName !=null && opt.candidateName !='' && opt.candidateName !='undefined'){
		    	  			result = result + ' <td>';
							result = result + ' <div title='+opt.candidateName+' class="clearfix mt5 proj_show"><span><a href="'+sourceleadUrl+'/requirementSummary?type=applicants&requirementUUID='+opt.requirementUUID+'&candidateUUID='+opt.candidateUUID+'&submissionId='+opt.submissionId+'" >'+opt.candidateName+'<i class="fa fa-binoculars"></i></a></span></div>';
							result = result + ' <div title='+opt.email+' class="clearfix mt5 mb5">'+opt.email+'</div>';
							result = result + ' </td>';
						}else{
							result = result + ' <td><div>--</div></td>';
						}
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "contactNumber",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.mobilePhone !=null && opt.mobilePhone !='' && opt.mobilePhone !='undefined'){
            		
                		  result = result + '<td><div><span title='+opt.mobilePhone+'>'+opt.mobilePhone+'</span></div></td>';
            		  }
            		else{
						result = result + ' <td><div>--</div></td>';
					}
            	  return result;
              		
              }
	          },
	      
	      {
	    	  "targets": colIndex++,
              "data": "job",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  if(opt.requirementUUID !=null && opt.requirementUUID !='' && opt.requirementUUID !='undefined'){
            		  if(opt.jobGeneratedId != null && opt.jobGeneratedId != ''  && opt.jobGeneratedId !="undefined"){
                		  result = result + '<td><div><span title= '+opt.jobGeneratedId+ '-' +opt.jobTitle+'>'+opt.jobGeneratedId+' - '+opt.jobTitle+'</span></div></td>';
            		  }else{
                		  result = result + '<td><div><span title='+opt.jobTitle+'>'+opt.jobTitle+'</span></div></td>';
            		  }
            		}else{
						result = result + ' <td><div>--</div></td>';
					}
            	  return result;
              		
              }
	          },{
            "targets": colIndex++,
            "data": "client",
            "render": function ( data, type, opt, meta ) {
            	var result = "";
            	
   				if(opt.orgLocationName != null && opt.orgLocationName != '' && opt.orgLocationName !='undefined'){
   					result = result + '<td><div><span>'+opt.orgLocationName+' </span></div></td>';
            	 }else{
            		 result = result + '<td><div><span>---</span></div></td>';
            	 }
   				return result;
            }
          } ,{
              "targets": colIndex++,
              "data": "source",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
	    	  		   if(opt.createdUser != null && opt.createdUser !='' && opt.createdUser != 'undefined'){
	    	  			result = result + ' <td><div><span>'+opt.createdUser+'</span></div></td>';
	    	  		   }else{
	    	  			result = result + ' <td><div><span>---</span></div></td>';
	    	  		   }
	    	  		return result;
              }
            },
          {
                "targets": colIndex++,
                "data": "joinDate",
                "render": function ( data, type, opt, meta ) {
                		var result = "";
                		if(opt.joinDate !=null && opt.joinDate !='' && opt.joinDate !='undefined'){
							result = result + ' <td><div><span>'+opt.joinDate+'</span></div></td>';
						}else{
							result = result + ' <td><div><span>---</span></div></td>';
						}
    	              return result;
                }
          } ,{
                      "targets": colIndex++,
                      "data": "paymentTerm",
                      "render": function ( data, type, opt, meta ) {
                    	  var result = "";
      	    	  		   if(opt.payOutPeriod != null && opt.payOutPeriod !='' && opt.payOutPeriod != 'undefined'){
      	    	  			result = result + ' <td><div><span>'+opt.payOutPeriod+'</span></div></td>';
      	    	  		   }else{
      	    	  			result = result + ' <td><div><span>---</span></div></td>';
      	    	  		   }
      	    	  		return result;
                      }
               },{
                     "targets": colIndex++,
                     "data": "generateInvoice",
                     "render": function ( data, type, opt, meta ) {
                   	  var result = "";
     	    	  		   if(opt.generateInvoice != null && opt.generateInvoice !='' && opt.generateInvoice != 'undefined'){
     	    	  			result = result + ' <td><div><span>'+opt.generateInvoice+'</span></div></td>';
     	    	  		   }else{
     	    	  			result = result + ' <td><div><span>---</span></div></td>';
     	    	  		   }
     	    	  		return result;
                     }
                   } ],
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
    $("#dataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   requirementJoinedList();
    	   }
    	});
}

</script>
  
<script>
function viewInterviewListResumes(resumeUUID,title){
	if(resumeUUID != null && resumeUUID != ""){
		window.open(sourceleadUrl+'/viewInterviewListResumes/'+title+'?resumeUUID='+resumeUUID,title,"height=600,width=600");
	}  
}
</script>

<script>
function viewOffersListAttachment(offerId,title){
	if(offerId != null && offerId != ""){
		window.open(sourceleadUrl+'/viewOffersListAttachment/'+title+'?offerId='+offerId,title,"height=600,width=600");
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
<script type="text/javascript">
	function flushCustom() {
		$('#ReportStartDate').val('');
		$('#ReportEndDate').val('');
		$.datepicker._clearDate('#ReportStartDate');
		$.datepicker._clearDate('#ReportEndDate'); 
	}
</script>

 
   <!-- ========================== Filter popup Written By Laxman ===============================  -->
<script>
function closeApplicantFilterPop(){
	$("#invoiceFilterId").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openFilterPop(){
	 $("#invoiceFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
function resetApplicantFilter(){
	$("#myClientInput").val('');
	$("ul#empFilter > li input:checkbox").prop("checked",false);
	$("ul#searchTypeFilter > li input:radio").prop("checked",false);
	$("ul#durationFilter > li input:checkbox").prop("checked",false);
	$("ul#myClinetListUL > li input:checkbox").prop("checked",false);
	$("ul#myTeamsListUL > li input:checkbox").prop("checked",false);
	$("ul#incentivizedFilter  > li input:checkbox").prop("checked",false);
	
	requirementJoinedList();
}
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
   
   <div class="advancedsearch-mask"></div>
          <div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search" id="invoiceFilterId">
				<h5 class="headerAdsrch">Filter Search
					<a href="javascript:void(0)" class="close-div" onClick="closeApplicantFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
					  <div class="col-md-3 hidden">
						<div class="row">
						<!-- <div class="col-md-12 mb">
								<label><strong>Search for</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="searchTypeFilter">
									     <li class="radioIput positionRelative" ><input  type="radio" name="searchType"  value ="MY" ><label for="Yes">My Jobs</label></li>
										 <li class="radioIput positionRelative" ><input  type="radio" name="searchType" value ="ALL" checked ><label for="No">All Jobs</label></li>
				                    </ul>
								 </div>
						   </div> -->
						  <%--   <div class="col-md-12 mb">
								<label><strong>Duration</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="durationFilter">
									<c:if test="${tab == 'today'}">
									<li class="radioIput positionRelative" ><input type="radio"  value="today" checked  name="customDates" checked onclick="showCustomFields(this);" ><label for="applicant">Today</label></li>
									</c:if>
									<c:if test="${tab != 'today'}">
									<li class="radioIput positionRelative" ><input type="radio"  value="today"  name="customDates" checked onclick="showCustomFields(this);" ><label for="applicant">Today</label></li>
									</c:if>
									<li class="radioIput positionRelative" ><input type="radio"  value="Last_7_days"   name="customDates" onclick="showCustomFields(this);" ><label for="applicant">Last 7 days</label></li>
	                                <li class="radioIput positionRelative" ><input type="radio"  value="Last_15_days" name="customDates" onclick="showCustomFields(this);"><label for="applicant">Last 15 days</label></li>
	                                <c:if test="${tab == 'previous'}">
	                                <li class="radioIput positionRelative" ><input type="radio"  value="1_month" name="customDates" checked onclick="showCustomFields(this);"><label for="applicant">1 month</label></li>
	                                </c:if>
	                                <c:if test="${tab != 'previous'}">
	                                <li class="radioIput positionRelative" ><input type="radio"  value="1_month" name="customDates" onclick="showCustomFields(this);"><label for="applicant">1 month</label></li>
	                                </c:if>
	                                <li class="radioIput positionRelative" ><input type="radio" value="3_months" name="customDates" onclick="showCustomFields(this);"><label for="applicant">3 months</label></li>
   	                                <li class="radioIput positionRelative" ><input type="radio" value="Last_Year" name="customDates" onclick="showCustomFields(this);"><label for="applicant">Last Year</label></li>
   	                                <li class="radioIput positionRelative" ><input type="radio" value="Custom" name="customDates" onclick="showCustomFields(this);"><label for="applicant">Custom</label></li>
	                                
			                        </ul>
							      </div>
						     </div> --%> 
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
						
						 
						<div class="col-md-3 hidden">
						<div class="row">
						  <!--   <div class="col-md-12 mb">
								<label><strong>Employment Type</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="empFilter">
									<li class="checkBox positionRelative" ><input type="checkbox" value="C2C" name="employmentType"><label for="applicant">C2C</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" value="W-2" name="employmentType"><label for="applicant">W-2</label></li>
                                    <li class="checkBox positionRelative" ><input type="checkbox" value="C2H" name="employmentType"><label for="applicant">C2H</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox"  value="Contract" name="employmentType"><label for="applicant">Contract</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="Part Time" name="employmentType"><label for="applicant">Part Time</label></li>
	                                <li class="checkBox positionRelative" ><input type="checkbox" value="Full Time" name="employmentType"><label for="applicant">Full Time</label></li>
			                        </ul>
							      </div>
						     </div> -->
						 <!--     <div class="col-md-12 mb">
								<label><strong>Incentivized</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="incentivizedFilter">
									     <li class="checkBox positionRelative" ><input  type="checkbox" name="incentivizedType"  value ="Y" ><label for="Yes">Yes</label></li>
										 <li class="checkBox positionRelative" ><input  type="checkbox" name="incentivizedType" value ="N" ><label for="No">No</label></li>
				                    </ul>
								 </div>
						   </div>  -->
						  </div>
						</div>
	                 
						<div class="col-md-3 positionRelative mb">
							<div class="clearfix dynamic_search filterDynamicsearch">
																<input type="text" id="myTeamsInput" onkeyup="myTeamsFunction()" placeholder="Teams" title="Search teams">
								<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
							</div>
							 <div class="clearfix filterSearch-ul">
									<ul class="list-unstyled positionRelative filterSearch mt5" id="myTeamsListUL">
			                        </ul>
							 </div> 
						</div>
						
						<div class="col-md-3 positionRelative mb">
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
							<input type="button" value="Apply" id="search_requiremet" onclick="closeApplicantFilterPop();requirementJoinedList();" class="btn btn-sm btn-success" />
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

function getTeamsList(){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+ "/getTeamsListForLoginUser?${_csrf.parameterName}=${_csrf.token}",
		success : function(data) {
			var result = "";
			  for (var i=0; i<data.length; i++)
				{
					var report = data[i];
					result = result + '<li class="checkBox positionRelative">';
					result = result + '<input  type="checkbox" name="teamNameById" value ='+report.id+'><label>'+report.name+'</label>';
					result = result + '</li>';
				}
			  $("#myTeamsListUL").empty();
			  $("#myTeamsListUL").append(result);
		}
	});
}

function myTeamsFunction() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("myTeamsInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myTeamsListUL");
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


<!-- =============== Pdf and  Excel Export scripts writen by Laxman =================== -->
<script>
function projectExport(type){
	if(type == "Pdf"){
		exportType = "pdf";
	}else{
		exportType = "xls";
	}
	 var sourceleadUrl ='${sourceleadUrl}';
	 var frequency=$("#reportTypeId").val();
	   var tab = '${tab}';
    	    var fromDate='';
    	    var toDate='';
		var searchValue = $("#dataTableSearch").val();
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		searchValue = searchValue ? searchValue : "";
		var employmentType = [];
		 $('input:checkbox[name=employmentType]:checked').each(function() 
		 {
			employmentType.push($(this).val());
		 });
		 var clientNameById = [];
		 $('input:checkbox[name=clientNameById]:checked').each(function() 
		 {
			 clientNameById.push($(this).val());
		 });
		 var teamNameById = [];
		 $('input:checkbox[name=teamNameById]:checked').each(function() 
		 {
			 teamNameById.push($(this).val());
		 });
		 var incentivizedType = [];
		 $('input:checkbox[name=incentivizedType]:checked').each(function() 
		 {
			 incentivizedType.push($(this).val());
		 });
		 var searchType = $('input[name=searchType]:checked').val();

		    if(frequency == "custom"){
    	        var fromDate = $("#ReportStartDate").val();
    	  		  var toDate = $("#ReportEndDate").val();
    	    	if(fromDate == ''){
    	    		  document.getElementById("reportsStartDate").style.display="block";
    				  $("#reportsStartDate").empty().append("Start Date is Required") ; 
    	        }
    	        if(toDate == ''){
    	        	 document.getElementById("reportsEndDate").style.display="block";
   				  $("#reportsEndDate").empty().append("End Date is Required") ; 
    	        }
    	        if((fromDate != '' && fromDate != 'undefined' && fromDate != null) && (toDate != '' && toDate != 'undefined' && toDate != null)){
   	    		 window.location.href = sourceleadUrl+"/applicantsJoinedListExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&exportType="+exportType;
    	        }
    	    }else{
    	    	 document.getElementById("reportsStartDate").style.display="none";
    			 document.getElementById("reportsEndDate").style.display="none";
   	 		 window.location.href = sourceleadUrl+"/applicantsJoinedListExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&exportType="+exportType;
    	    } 
// 		 window.location.href = sourceleadUrl+"/applicantsJoinedListExport?searchValue="+searchValue+"&orgLocationId="+orgLocationId+"&tabType="+tab+"&submissionStatus=JOINED&frequency="+frequency+"&fromDate="+fromDate+"&toDate="+toDate+"&employmentType="+employmentType+"&clientId="+clientNameById+"&teamId="+teamNameById+"&incentivizedType="+incentivizedType+"&searchType="+searchType+"&exportType="+exportType;
	     
}
</script>
<script>
function addValidClass(){
	 var StartDate = document.getElementById("ReportStartDate").value;
	 if(StartDate){
		 document.getElementById("reportsStartDate").style.display="block";
	 }
	 
	 var EndDate = document.getElementById("ReportEndDate").value;
	
	 if(EndDate){
		 document.getElementById("reportsEndDate").style.display="block";
	 }
}
</script>
<script>
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
</script>	
<jsp:include page="../masterFooter.jsp" />
