<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="${SourceLeadWeb_sourceleadDomainUrl}/resources/css/chosen.min.css">
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/chosen.jquery.min.js"></script> 
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/jquery-fun.js"></script> 
<script src="${SourceLeadWeb_sourceleadDomainUrl}/resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script> 
var atsUrl ='${SourceLeadWeb_atsUrl}';
	$(document).ready(function(){
		
		$("#onboardCount").html('(${onboardCount})');
		$("#offboardCount").html('(${offboardCount})');
		
		 '<sec:authorize access="hasAnyRole(\'ROLE_CREATE_ON_BOARD_USER\',\'ROLE_VIEW_EDIT_ON_BOARD_USER\') && !hasRole(\'ROLE_CREATE_OFF_BOARD_USER\')">'
		       getOnboardCandidatesList();
		
         '</sec:authorize>';
         
         '<sec:authorize access="!hasRole(\'ROLE_CREATE_ON_BOARD_USER\') && hasAnyRole(\'ROLE_CREATE_OFF_BOARD_USER\',\'ROLE_VIEW_EDIT_OFF_BOARD_USER\')">'
	       getOffboardCandidatesList();
	       document.getElementById("offboard").className = 'active'
         '</sec:authorize>';
         
         '<sec:authorize access="hasRole(\'ROLE_CREATE_ON_BOARD_USER\') && hasRole(\'ROLE_CREATE_OFF_BOARD_USER\')">'
	       getOnboardCandidatesList();
   		'</sec:authorize>';
         
         
	});
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Onboard & Offboard</h2>
      </div>
      <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a>  &raquo;On-board/Off-board</div>
 </section>
 
 <div class="clearfix">
           <ul class="project-tabs-menu" id="tab_bar">
            
            <sec:authorize access="hasAnyRole('ROLE_CREATE_ON_BOARD_USER','ROLE_VIEW_EDIT_ON_BOARD_USER')">
	            <li class="current" id="onboard">
	            	<a href="#" class="onboardCount" onclick="getOnboardCandidatesList();" title="Current onboard count">
	              		Current onboard <span class="count" id="onboardCount"></span>
	              	</a> 
	            </li>
            </sec:authorize>
            
            <sec:authorize access="hasAnyRole(\'ROLE_CREATE_OFF_BOARD_USER\',\'ROLE_VIEW_EDIT_OFF_BOARD_USER\')">
	            <li id="offboard">
	            	<a href="#" class="offboardCount" onclick="getOffboardCandidatesList();" title="Current offboard count">
	              		Current offboard <span class="count" id="offboardCount"></span>
	              	</a> 
	            </li>
            </sec:authorize>
          </ul>
 </div>
 
 <div class="content-area content-new clearfix backWhite">
 
 
 <div class="positionRelative reportHead clearfix">
      <ul class="pull-right list-unstyled list-inline">
      
            <li>
				<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
			           <sec:authorize access="hasRole('ROLE_CREATE_ON_BOARD_USER')">
				          <a href="${SourceLeadWeb_atsUrl}/candidateOnBoardCreation" title="Create onboard">Create onboard</a> 
			          	</sec:authorize>
			          	<sec:authorize access="hasRole('ROLE_CREATE_OFF_BOARD_USER')">
				          <a href="${SourceLeadWeb_atsUrl}/candidateOffBoardCreation" title="Create offboard">Create offboard</a>
			          	</sec:authorize>					
					</span>
				</div>
			</li>
		  <li id="onboardSearch"><input type="text" class="repSearchBar" placeholder='Search' id='onboardDataTableSearch'/><span class="repSearchIcon" onClick="onboardDataTableList();"></span></li>
		  <li id="offboardSearch" style="display: none;"><input type="text" class="repSearchBar" placeholder='Search' id='offboardDataTableSearch'/><span class="repSearchIcon" onClick="offboardDataTableList();"></span></li>
        </ul>
      </div>
          
        <div class="clearfix"></div>  
          
          
          <div class="candidate_wrap_table datatable_filter positionRelative" style='position:relative; top:-20px;'>
	          <div id="onboardingLoading"></div>
	         
		    <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
		    	<div id="onboardingCandidatesList" class="datatable_filter"></div>
			</div>	  
	         
	        <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">
		    	<div id="offboardingCandidatesList" class="datatable_filter"></div>
			</div>	
	          
	          <div class="vendor_wrap_mask"></div>
	          <div id="rejectedReasonDiv"></div>
          </div>
        
      </div>
    </div>
 </section>
 
 
 <script>
 function getOnboardCandidatesList(){
	 
	 $("#onboardingCandidatesList").empty();
	 $("#offboardingCandidatesList").empty();
	 
	 var atsUrl = '${SourceLeadWeb_atsUrl}';
	  var locationCode = '${SourceLeadWeb_locationCode}';
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  candOnBoardListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  candOnBoardListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  candOnBoardListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  candOnBoardListDate = 'YYYY-DD-MM'; 
	  }
	  
	  
	    var result = "";
/* 	 	result = result +" <div class='dynamic_search pull-right mr'>";
	 	result = result +" <input type='text' placeholder='Search' class='repSearchBar' id='onboardDataTableSearch'/>";
	 	result = result +" <span class='repSearchIcon' onClick='onboardDataTableList();'></span>";
	 	result = result +"</div>"; */
	 	
	 	result = result + '<table id="onboardListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'	<th>Candidate Name</th>';
	  	result = result +'     <th>Date Of Join<br>(<small class="custom-small">'+candOnBoardListDate+'</small>)</th>';
	  	result = result +'      <th>Onboard Documents</th>';
	  	result = result +'      <th>Onboard Status</th>';
	  	result = result +'       <th>Actions</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#onboardingCandidatesList").empty();
	    $("#onboardingCandidatesList").append(result);
	    $("#onboard").addClass("current");
	    $("#offboard").removeClass("current");
	    $("#onboardSearch").show();
	    $("#offboardSearch").hide();
	    
	    onboardDataTableList();
	  
 }
 </script>
 
 <script>
function onboardDataTableList(){
	 var searchValue = $("#onboardDataTableSearch").val();
	 var locationCode = '${SourceLeadWeb_locationCode}';
	 searchValue = searchValue ? searchValue : "";
	 var url = atsUrl+"/getOnboardCandidateList?searchValue="+searchValue+"&locationCode="+locationCode;
	 onboardPagination(url,atsUrl);
	 $("#onboardingLoading").removeAttr("style");
}
</script>


<script>
function onboardPagination(url,atsUrl){
	var colIndex = 0;
    $('#onboardListTable').DataTable( { 
      "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
    	  url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
            { "data": "firstName" },
            { "data": "startDate"},
            { "data": "onboardDocuments"},
            { "data": "onboardStatus" },
            { "data": "actions"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "firstName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  	   	 result = result +"<td>";
                	 result = result +"<div class='width300px'>";
                	 result = result +"<div class='tbldata-dotdot-big pull-left' title='"+opt.candidateName+"'>"+opt.candidateName+"</div>";
                	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_ON_BOARD_USER\')">'	 
                	 if(opt.candidateOnBoardStatus == 'Draft'){
                		
                    		 result = result +"<div class='pull-right proj_show'> <a href='"+atsUrl+"/viewOnboardCandidateDetails?onboardUUID="+opt.onboardCandidateUUID+"'> <i class='fa fa-binoculars'></i> </a> </div> </div>";
                    	 }
                    	 else if(opt.candidateOnBoardStatus == 'Finished' || opt.candidateOnBoardStatus == 'Submitted' || opt.candidateOnBoardStatus == 'Rejected' || opt.candidateOnBoardStatus == 'Accepted'){
                    		 result = result +"<div class='pull-right proj_show'> <a href='"+atsUrl+"/viewOnboardCandidateSummary?onboardUUID="+opt.onboardCandidateUUID+"'> <i class='fa fa-binoculars'></i> </a> </div> </div>";
                    	 }
                    	 '</sec:authorize>'
                	 
                	 result = result +"</td>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "startDate",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  var joiningDate = '';
             	 
             	 if(opt.joiningDateStr != null && opt.joiningDateStr != '' && opt.joiningDateStr != 'undefined'){
             		 joiningDate = opt.joiningDateStr;
             	 }else{
             		 joiningDate = "----";
             	 }
            	  
             	 result = result +"<td><div><span>"+joiningDate+"</span></div></td>";
	             return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "onboardDocuments",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div><span>"+opt.documentsAcceptedCount+" / "+opt.documentsCount+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "onboardStatus",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	           		   if(opt.onboardSubmitted != 'Y'){
            			 if(opt.candidateOnBoardStatus == 'Finished'){
                			 result = result +"<td><div><span>Sent</span></div></td>";
                		 }else{
                			 if(opt.candidateOnBoardStatus == 'Rejected'){
                				 if(opt.rejectedReason != null && opt.rejectedReason != '' && opt.rejectedReason != 'undefined'){
                				 result = result +"<td><div><span>"+opt.candidateOnBoardStatus+"</span></div><i class='glyphicon glyphicon-comment edit-reason'><div class='edit-status'><p>"+opt.rejectedReason+"</p></div></i></td>";
                				 }
                			 }else if(opt.candidateOnBoardStatus == 'Accepted'){
                    			 result = result +"<td><div>Completed</div></td>";
                    		 }else{
                				 result = result +"<td><div><span>"+opt.candidateOnBoardStatus+"</span></div></td>";
                			 }
                			 
                		 }
            		 }else{
            			 if(opt.candidateOnBoardStatus == 'Accepted'){
            				 result = result +"<td><div>Completed</div></td>"
            			 }else{
            				 result = result +"<td><div><span>"+opt.candidateOnBoardStatus+"</span></div></td>";
            			 }
            			 
            			
            		 }
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            		 if(opt.candidateOnBoardStatus == 'Accepted'){
                			 result = result +"<td><div><span class='text-success'>"+opt.candidateOnBoardStatus+"</span></div></td>";
                		 }else  if(opt.candidateOnBoardStatus == 'Rejected'){
                			 result = result +"<td><div><span class='text-danger'>"+opt.candidateOnBoardStatus+"</span></div></td>";
                		 }else{
                			 if(opt.onboardSubmitted == 'Y'){
                				 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_ON_BOARD_USER\')">'
                    			 result = result	+ '<td><div class="tblminwidth-50 pull-left ml"><div class="pull-left"><a><i class="fa fa-check text-success" onclick="acceptOnboardingCandiate(\''+opt.onboardCandidateUUID+'\');"></i></a></div> <div class="pull-left mt2 ml5"><a><i class="fa fa-close text-danger" onclick="rejectedReason(\''+opt.onboardCandidateUUID+'\',\''+opt.candidateName+'\',\''+opt.userEmail+'\');"></i></a></div></div></td>';
                    			 '</sec:authorize>';
                			 }else{
                    			 result = result	+ '<td><div>-----</div></td>';
                    		 }
                		 }
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#onboardListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#onboardDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#onboardDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#onboardDataTableSearch').val();
   	    	
   	 	if(searchData.endsWith("-") || searchData.endsWith("\\") || searchData.endsWith("//") || searchData.endsWith("%")){
	    		$("#onboardDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
	    	}else{
	   	    	 onboardDataTableList();
	    	}
   	    	$('#onboardDataTableSearch').val(searchData);
    	   }
    	});
}

</script>
 
 <script>
 function getOffboardCandidatesList(){
	 
	 $("#onboardingCandidatesList").empty();
	 $("#offboardingCandidatesList").empty();
	 
	  var atsUrl ='${SourceLeadWeb_atsUrl}';
	  var locationCode = '${SourceLeadWeb_locationCode}'
	  $("#onboardingLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  candOffBoardListDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  candOffBoardListDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  candOffBoardListDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  candOffBoardListDate = 'YYYY-DD-MM'; 
	  }
	  
	  
	    var result = "";
/* 	 	result = result +" <div class='dynamic_search pull-right mr'>";
	 	result = result +" <input type='text' placeholder='Search' id='offboardDataTableSearch'/>";
	 	result = result +" <span class='cursorPointer faSearch' title='Search'><i class='fa fa-search' onClick='offboardDataTableList();'></i></span>";
	 	result = result +"</div>"; */
	 	
	 	result = result + '<table id="offboardListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'	<th>Candidate Name</th>';
	  	result = result +'     <th>End Date<br>(<small class="custom-small">'+candOffBoardListDate+'</small>)</th>';
	  	result = result +'     <th>Effective Date<br>(<small class="custom-small">'+candOffBoardListDate+'</small>)</th>';
	  	result = result +'      <th>Stage</th>';
	  	result = result +'      <th>Offboard Stage</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#offboardingCandidatesList").empty();
	    $("#offboardingCandidatesList").append(result);
	    $("#onboard").removeClass("current");
	    $("#offboard").addClass("current");
	    $("#onboardSearch").hide();
	    $("#offboardSearch").show();	    
	    offboardDataTableList();
 }
 </script>
 
 <script>
 function offboardDataTableList(){
	 var atsUrl ='${SourceLeadWeb_atsUrl}';
	 var searchValue = $("#offboardDataTableSearch").val();
	 var locationCode = '${SourceLeadWeb_locationCode}';
	 searchValue = searchValue ? searchValue : "";
	 var url = atsUrl+"/getOffboardCandidateList?searchValue="+searchValue+"&locationCode="+locationCode;
	 offboardPagination(url,atsUrl);
	 $("#onboardingLoading").removeAttr("style");
 }
 </script>
 
 
 <script>
function offboardPagination(url,atsUrl){
	var colIndex = 0;
    $('#offboardListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
            { "data": "firstName" },
            { "data": "endDate"},
            { "data": "effectiveDate"},
            { "data": "stage" },
            { "data": "offboardStage"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "firstName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  	   	 result = result +"<td>";
                	 result = result +"<div class='width300px'>";
                	 result = result +"<div class='tbldata-dotdot-big pull-left' title='"+opt.candidateName+"'>"+opt.candidateName+"</div>";
                	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_OFF_BOARD_USER\')">'	 
                	 if(opt.offboardSubmitted == 'N'){
                    		 result = result +"<div class='pull-right proj_show'> <a href='"+atsUrl+"/viewOffboardCandidateDetails?offboardUUID="+opt.offboardUUID+"'> <i class='fa fa-binoculars'></i> </a> </div> </div>";
                    	 }
                    	 else if(opt.offboardSubmitted == 'Y'){
                    		 result = result +"<div class='pull-right proj_show'> <a href='"+atsUrl+"/viewOffboardCandidateSummaryDetails?offboardUUID="+opt.offboardUUID+"'> <i class='fa fa-binoculars'></i> </a> </div> </div>";
                    	 }
                    	 '</sec:authorize>'
                	 
                	 result = result +"</td>";
                	 
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "endDate",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result +"<td><div><span>"+opt.endDateStr+"</span></div></td>";
	             return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "effectiveDate",
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  result = result +"<td><div><span>"+opt.effectiveDateStr+"</span></div></td>";
	            	  return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "stage",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result +"<td><div><span>"+opt.offBoardStatus+"</span></div></td>";
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "offboardStage",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.offboardSubmitted == 'Y'){
               			 result = result +"<td><div><span class='text-success'>Completed</span></div></td>";
	               		 }else{
	               			 result = result +"<td><div><span class='text-warning'>Pending</span></div></td>";
	               		 }
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#offboardListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#offboardDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   $('#offboardDataTableSearch').parent().find('p.jquery_form_error_message').remove();
      	    	var searchData = $('#offboardDataTableSearch').val();
      	    	if(searchData.endsWith("-") || searchData.endsWith("\\") || searchData.endsWith("//") || searchData.endsWith("%")){
      	    		$("#offboardDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
    	    	}else{
      	    		offboardDataTableList();
    	    	}
      	    	$('#offboardDataTableSearch').val(searchData);
       	   }
    	});
}

</script>


<script>
 function rejectedReason(onboardCandidateUUID,candidateName,candidateEmail){
	
	    var result = "";
		result = result + '	<div class="settPop sett_pop">';
		result = result + '	<div id="loading"></div>';
		result = result + '<form id="rejectedReason_form" name="rejectedReason_form" method="post">';
		result = result + '<input type="hidden" name="onboardCandidateUUID" value="'+onboardCandidateUUID+'">';
		result = result + '	<h3>Onboard Reject Reason<i class="fa fa-times" onclick="cancelRejectedReason();"></i></h3>  ';
		result = result + '	<div class="settPopInn"> ';
		
		result = result + '		<div class="clearfix">  ';
		result = result + '		<div class="col-md-12">  ';
		result = result + '		  <label class="col-md-2 noPad" for="Candidate Name">Name :</label> ';
		result = result + '		  <label class="col-md-8 paddingRight">'+candidateName+'</label> ';
		result = result + '		</div>  ';

		result = result + '		<div class="col-md-12">  ';
		result = result + '		  <label class="col-md-2 noPad" for="Email">Email :</label> ';
		result = result + '		  <label class="col-md-8 paddingRight">'+candidateEmail+'</label> ';
		result = result + '		</div>  ';
		
		
		result = result + '		  <label class="col-md-12 mt10" for="Project Id">Reason</label> ';
		result = result + '		  <div class="col-md-12 taskdesp">   ';
		result = result + '			<textarea class="form-control" name="rejectedReason" id="rejectedReason" field-name="Reason" data-validation="required  validate_length length2-500" rows="2"></textarea>';
		result = result + '			<span class="hidden"></span> </div>  ';
		result = result + '		</div>';
		result = result + '		<div class="col-md-12 mt5 clearfix"> ';
		result = result + '		<div class="pull-right">  ';
		result = result + '		  <input type="submit" value="Save" id="submit_rejectedReason"  class="btn btn-primary" >';
		result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelRejectedReason();">';
		result = result + '		</div>';
		result = result + '		</div>';
		result = result + '		<div class="clearfix"></div>   ';
		result = result + '	</div>  ';
		result = result + '</form>';
		result = result + '	</div>';
	    $("#rejectedReasonDiv").empty();
	    $("#rejectedReasonDiv").append(result);
	    
	    $(".settPop").slideDown(200);
		$(".vendor_wrap_mask").fadeIn(200);
	    
 }
 </script> 
 
 
<script>
function cancelRejectedReason(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
}
</script>
 
 
<script type="text/javascript">
$(document).ready(function() {
    $(".glyphicon-edit").click(function(){
		$(".settPop").slideDown(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".settPop i, .btn-default").click(function(){
		$(".settPop").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});		
	
</script> 


<script type="text/javascript">
$('body').on('submit', '#rejectedReason_form', function(e){
	  if ($(this).validate(false, validationSettings)){
      e.preventDefault();
     
      var frm = $('#rejectedReason_form').serializeFormJSON();
      var con = JSON.stringify(frm);
      con = con.replace(/[{}]/g, "");
      
      //It will escape all the special characters
      var jsonData = encodeURIComponent(con);
      var atsUrl ='${SourceLeadWeb_atsUrl}';
        $("#submit_rejectedReason").attr("disabled", "disabled");
        $("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 	 $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/rejectOnboardedCandidate?${_csrf.parameterName}=${_csrf.token}&json="+jsonData, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		cancelRejectedReason();
			        	getOnboardCandidatesList();
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    }); 
      return true; 
    } else {
    	return false;	
    }
  });

	$('body').on('blur', '#rejectedReason_form', function() {
		$("#rejectedReason_form").showHelpOnFocus();
		$("#rejectedReason_form").validateOnBlur(false, validationSettings);
	});
  </script>
  
  
  
  <script>
  function acceptOnboardingCandiate(onboardCandidateUUID){
	  var atsUrl ='${SourceLeadWeb_atsUrl}';
	 	 $.ajax({
		    	type: "POST",
		    	 url: atsUrl+"/acceptOnboardingCandiate?${_csrf.parameterName}=${_csrf.token}&onboardCandidateUUID="+onboardCandidateUUID, 
		        success: function(response) {
		        	if (response.response === "AWKWARD") {
		        	    location.href = "/errorPage";
		        	} else if (response.response === "SUCCESS") {
		        		getOnboardCandidatesList();
		        	} else if (response.indexOf('Sign In') !== -1) {
		        		location.replace(sourceleadUrl);
		        	} else {
		        	    showError("Operation failed, please try again");
		        	}
		        },
		    }); 
  }
  </script>
  
 <script type="text/javascript">
(function ($) {
   $.fn.serializeFormJSON = function () {

       var o = {};
       var a = this.serializeArray();
       $.each(a, function () {
           if (o[this.name]) {
               if (!o[this.name].push) {
                   o[this.name] = [o[this.name]];
               }
               o[this.name].push(this.value || '');
           } else {
               o[this.name] = this.value || '';
           }
       });
       return o;
   };
})(jQuery);
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
    
 <jsp:include page="../masterFooter.jsp" />