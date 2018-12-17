<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:if test="${staticTime == null || staticTime == 0 }">
    <c:set var='staticTime' value="<%=new java.util.Date().getTime() %>" scope="application"></c:set>
 </c:if>
 <link href="/resources/css/jquery-tagit.css?staticTime=${staticTime}" rel="stylesheet">
 
<style type="text/css">
.edit-details {
text-align:left;
}
.edit-details ul {
float:none;
}
.edit-details ul li {
float:none;
}

.addUser-org {
    overflow-x: auto;
}
.mrTop {
    margin: 3px 4px 0px 0px !important;
}

#uploadFileGroup p.jquery_form_error_message{
	position: absolute;
	left:0;
	bottom:-16px;
	}
	.tblicons a{
	font-size:13px;
	}
	.tblicons a:first-child{
	margin-left: 0;
	}
	.tblicons .glyphicon-trash{
	margin-left: 5px;
	}
	.tblicons a{
	margin:0px 8px;
	padding: 0 !important;
	} 
	.vendor_wrap_inn_table table tr th:last-child{
	width:100px;
	}
	.removeIcon{
	font-size:22px; position:relative; top:-5px; margin-left:15px; color:#F22613; cursor: pointer;
	}
	.mask_time {
	z-index: 100000;
}
.anchor_show span {
color:#1d6cb5; cursor: pointer;
}
</style>

<div id="deleteDocpopUp"></div>
<div class="mask_time"></div> 
<section class="contPopup" style="min-height:300px;">
	<div id="viewPopupDiv"></div>
</section>

<section class="DocAccPopup">
	<div class="DocAccPopupHead clearfix"><h4>Requested Documents</h4><span><i class="fa fa-times" onclick="contPopClose();"></i></span></div>
	<form:form id="contractDoc_form" name="contractDoc_form" method="post">
	<div id ="loadingDoc"></div>
		<div id="DocAccPopupDiv" class="clearfix rd_table"></div>
        
	<div class="clearfix DocAccWrapFoot" id="saveAction">
		<!-- <input type="button" value="Submit" class="btn btn-primary padSm"> -->
		 <input type="button" value="Submit" id="save_Doc" onclick="saveDocContract();" class="btn btn-primary padSm">
		<input type="button" value="Cancel" class="btn btn-default padSm" onclick="contPopClose();">
      </div>
      </form:form>
</section>

<script>
function clientContracts(orgRelationId){
	
	var result = "";
	result = result  +'<div class="timesheet_head minHeight">';
	result = result  +'    <div class="project_tabs_inn_head"> ';  
	    result = result + '<ul>';
	    result = result + '<li class="dropdown mt10"><div class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i></div>';
	    result = result + '<div class="dropdown-menu bootstrap_dropdownMenu dropdown-menu_width">                                                                                                                                       ';
	    result = result + '<ul class="paddingLi">';
	    result = result + '<li>';
	    result = result + '<input type="checkbox" value="DRAFT" name="contractStatus" id="contractStatus" onclick="clientContractsList('+orgRelationId+');" >';
	    result = result + '<label class="paddingLeft">Draft <b class="badge" id="draftCount">0</b></label>';
	    result = result + '</li>';
	    
	    result = result + '<li>';
	    result = result + '<input type="checkbox" value="WAITING_FOR_APPROVER" name="contractStatus" id="contractStatus" onclick="clientContractsList('+orgRelationId+');" >';
	    result = result + '<label class="paddingLeft" for="onboard1">Waiting For Approval <b class="badge" id="WFACount">0</b></label>';
	    result = result + '</li>';
	    
	    result = result + '<li>';
	    result = result + '<input type="checkbox" value="WAITING_TO_SEND" name="contractStatus" id="contractStatus" onclick="clientContractsList('+orgRelationId+');" >';
	    result = result + '<label class="paddingLeft" for="onboard1">Waiting To Send <b class="badge" id="WTOSCount">0</b></label>';
	    result = result + '</li>';
	    
	    result = result + '<li>';
	    result = result + '<input type="checkbox" value="WAITING_TO_BE_SIGNED" name="contractStatus" id="contractStatus" onclick="clientContractsList('+orgRelationId+');" >';
	    result = result + '<label class="paddingLeft" for="onboard1">Waiting to be Signed <b class="badge" id="WTSCount">0</b></label>';
	    result = result + '</li>';
	    
	    result = result + '<li>';
	    result = result + '<input type="checkbox" value="PENDING" name="contractStatus" id="contractStatus" onclick="clientContractsList('+orgRelationId+');" >';
	    result = result + '<label class="paddingLeft" for="onboard1">Pending <b class="badge" id="pendingCount">0</b></label>';
	    result = result + '</li>';
	    
	    result = result + '<li>';
	    result = result + '<input type="checkbox" value="SEND_FOR_VERIFICATION" name="contractStatus" id="contractStatus" onclick="clientContractsList('+orgRelationId+');" >';
	    result = result + '<label class="paddingLeft" for="onboard1">Send for Verification <b class="badge" id="STVCount">0</b></label>';
	    result = result + '</li>';
	    
	    result = result + '<li>';
	    result = result + '<input type="checkbox" value="Finalized" name="contractStatus" id="contractStatus" onclick="clientContractsList('+orgRelationId+');" >';
	    result = result + '<label class="paddingLeft" for="onboard1">Finalized <b class="badge" id="finalizedCount">0</b></label>';
	    result = result + '</li>';
	    
	    result = result + '<li>';
	    result = result + '<input type="checkbox" value="REJECTED" name="contractStatus" id="contractStatus" onclick="clientContractsList('+orgRelationId+');" >';
	    result = result + '<label class="paddingLeft" for="onboard1">Rejected <b class="badge" id="rejectedCount">0</b></label>';
	    result = result + '</li>';
	    
	    result = result + '<li>';
	    result = result + '<input type="checkbox" value="EXPIRED" name="contractStatus" id="contractStatus" onclick="clientContractsList('+orgRelationId+');" >';
	    result = result + '<label class="paddingLeft" for="onboard1">Expired <b class="badge" id="expiredCount">0</b></label>';
	    result = result + '</li>';
	    
	    result = result + '</ul>';
/* 	    '<sec:authorize access="hasAnyRole(\'ROLE_CREATE_CLIENT_DOCUMENT\')">';
		result = result  +'    	<a href="javascript:void(0)" onClick="addDocument('+clientLocationId+','+orgRelationId+'); getSpocsList('+orgRelationId+');" class="addUser-org" ><i class="glyphicon glyphicon-plus"></i>Add Documents</a>                                                            ';
		'</sec:authorize>';
 */		result = result  +'     <div id="popUpDiv" class="edit-details col-md-4"> ';
		result = result  +'     </div>  ';
		result = result  +'  <div class="clearfix"></div> ';
		result = result + '</ul>';
		result  = result + '   <div class="project_tabs_inn_table vendor_wrap_inn_table">';
		result  = result + '<input type="hidden" value = "'+orgRelationId+'" id="contractRelationId"/>';
		
		result  = result + '   <div id="contractloading" ></div>';
		result  = result + '   <div id="clientContractsList" ></div>';
		result  = result + '   </div>';
	result = result+'	    </div>';
	result = result+'	    </div>';

	$("#tab-1").empty();
    $("#tab-2").empty(); 
    $("#tab-3").empty();  
    $("#tab-4").empty(); 
	$("#tab-5").empty(); 
	$("#tab-6").empty(); 	
    $("#tab-7").empty();  
    $("#tab-7").append(result);  
	
    clientContractsList(orgRelationId);
    $('.dropdown-menu').click(function(event){
    	event.stopPropagation();
    	});
}
</script>

<script>
function clientContractsList(orgRelationId){
	var contractStatus = [];
	$('input:checkbox[name=contractStatus]:checked').each(function() 
    {
		contractStatus.push($(this).val());
    });
	
	 var sourceleadUrl ='${sourceleadUrl}';
	 var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  contractClientDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  contractClientDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  contractClientDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  contractClientDate = 'YYYY-DD-MM'; 
	  } 
	 $.ajax({  
          type : "POST",
          url: sourceleadUrl+"/getClientOrVendorContracts?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&responderType=Client&contractStatus="+contractStatus,        
          success :function(data) {
             			var json = JSON.parse(data);
	                	 $("#clientContractsList").empty();
	                	 var draftCount = 0;
	                	 var waitingForApprovalCount = 0;
	                	 var waitingToBeSignedCount = 0;
	                	 var pendingCount = 0;
	                	 var sendForVerificationCount = 0;
	                	 var finalizedCount = 0;
	                	 var rejectedCount = 0;
	                	 var expiredCount = 0;
	                	 
	                	 var opt="";
	                		opt = opt +  '<table id="contractListTableDraft1" class="display" cellspacing="0" width="100%" >';
	                		opt = opt + '	<thead>';
	                		opt = opt + '   <tr>';
	                		opt = opt + '    <th>Title</th>';
	                		opt = opt + '    <th>Type</th>';
	                		opt = opt + '    <th>Owner</th>';
	                		opt = opt + '    <th>Client Contact</th>';
	                		opt = opt + '    <th>Duration <br>(<small class="custom-small">'+contractClientDate+'</small>)</th>';
	                		opt = opt + '    <th>Req.Docs</th>'; 
	                		opt = opt + '    <th>Status</th>'; 
	                		opt = opt + '    <th>Action</th>';
	                		opt = opt + '   </tr>';
	                		opt = opt + '	</thead>';
	                		opt = opt + '	<tbody>';
	                		if(json.length>0){
	                		    for (var i = 0; i < json.length; i++) {
	                		    	var output= json[i];
	                		    	
	                		    		opt = opt + '<tr id="row'+output.id+'">';
		                		        opt = opt + '<td>';
//	 	                		        if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
		                		        	opt = opt + '<div class="tbldata-dotdot-sm anchor_show" onclick="PopUp(\''+output.contractUUID+'\');"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
		                		        /* }else{
		                		        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" ><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
		                		        } */
		                		         opt = opt + '</td>';
		                		         opt = opt + '<td>';
		                	       			 opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
		                	       		 opt = opt + '</td>';
		                		    	 opt = opt + '<td>';
		                		       	 opt = opt + '<div><span title="'+output.ownerName+'">'+output.ownerName+'</span></div>';
		                		         opt = opt + '</td>';
		                		         
		                		         opt = opt + '<td>';
		                		         opt = opt + '<div><span title="'+output.vendorContactName+'">'+output.vendorContactName+'</span></div>';
		                		         opt = opt + '</td>';
		                		         
		                		         opt = opt + '<td>';
		                		         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr;
		                		         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
		                		        	 opt = opt + ' | '+output.endDateStr+'</div>';
		                		         }else{
		                		        	 opt = opt + '</div>';
		                		         }
		                		         opt = opt + '</td>';
		                		         
		                		         opt = opt + '<td>';
		                		         if(output.reqDocCount!="0"){
		                			         	opt = opt + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+output.contractUUID+'\',\''+output.parentId+'\');">'+output.reqDocCount+'</div>';
		                			         }
		                			         else{
		                			        	 opt = opt + '<div class="tblminwidth-75 ">'+output.reqDocCount+'</div>';
		                			         }
		                			         opt = opt + '</td>';
		                		         
		                		         opt = opt + '<td>';
		                		         if(output.templateSubmissionStatus !=null && output.templateSubmissionStatus != '' && output.templateSubmissionStatus != 'undefined'){
		                		        	 if(output.templateSubmissionStatus == "DRAFT"){
		                		        		 draftCount = draftCount+1;       
		                		        	 }else if(output.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
		                		        		 waitingForApprovalCount = waitingForApprovalCount +1;    
		                		        	 }else if(output.templateSubmissionStatus == "WAITING_TO_BE_SIGNED"){
		                		        		 waitingToBeSignedCount = waitingToBeSignedCount +1; 
		                		        	 }else if(output.templateSubmissionStatus == "PENDING"){
		                		        		 pendingCount = pendingCount +1;   
		                		        	 }else if(output.templateSubmissionStatus == "SEND_FOR_VERIFICATION"){
		                		        		 sendForVerificationCount = sendForVerificationCount +1;   
		                		        	 }else if(output.templateSubmissionStatus == "FINALIZED"){
		                		        		 finalizedCount = finalizedCount +1;   
		                		        	 }else if(output.templateSubmissionStatus == "REJECTED"){
		                		        		 rejectedCount = rejectedCount +1;   
		                		        	 }else if(output.templateSubmissionStatus == "EXPIRED"){
		                		        		 expiredCount = expiredCount +1;     
		                		        	 }
		                		        	 if(output.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
		                		        		 opt = opt + '<div class="tblminwidth-75">WAITING_FOR_APPROVAL</div>';
		                		        	 }else{
		                		        		 opt = opt + '<div class="tblminwidth-75">'+output.templateSubmissionStatus+'</div>';
		                		        	 }
		                		        	 
		                		         }
		                		         else{
		                		        	 opt = opt + '<div class="tblminwidth-75 ">---</div>';
		                		         }
		                		         opt = opt + '</td>';
		                		         
		                		         opt = opt + '<td class="noPad">';
		                		         opt = opt + '<div class="tblminwidth-75">';
		                		     		  if(output.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
		                		     			 if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == output.approverUserId){
			                	     	        	 opt = opt +'<a class="pull-left CurDiv" id="removeStatus'+output.id+'"><i class="fa fa-check" title="Approve" alt="Approve" onclick="approveContract('+output.id+',\'WAITING_FOR_APPROVER\','+orgRelationId+');"></i><i class="fa fa-times" title="Reject" alt="Rejected" onclick="approveContract('+output.id+',\'REJECTED\','+orgRelationId+');"></i></a>';
			                	     	         }
		                		     		  }
		                		     		  
		                		     		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		                		     		 if((output.typeOfContract == "New" || output.typeOfContract == "Custom") && (output.templateSubmissionStatus == "WAITING_TO_BE_SIGNED") && (null != output.parentContractId && '' != output.parentContractId && 'undefined' != output.parentContractId)){
		                		     			 opt = opt + '<a class="mrTop" href="#" title="Preview"><small class="fa fa-eye" onclick="previewNewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></small></a><a href="<c:url value="/app/docusign?contractUUID='+output.contractUUID+'" />" title="Signature"><i class="fa fa-pencil-square-o"></i></a>'; 
		                			         }else if((output.typeOfContract == "New" || output.typeOfContract == "Custom") && (output.templateSubmissionStatus == "PENDING") && (null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId)){
		                			        	 opt = opt + '<a class="mrTop" href="#" title="Preview"><small class="text-primary fa fa-eye" onclick="previewNewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></small></a>'; 
		                			         }else if(output.typeOfContract == "Existing"){
		                			        	 opt = opt + '<a class="mrTop" href="#" title="Preview"><small class="fa fa-eye" onclick="viewFile(\''+output.fileURL+'\', \''+output.fileName+'\', \''+output.fileType+'\')"></small></a>';
		                			         }else if(output.templateSubmissionStatus == "WAITING_TO_BE_VERIFY" || output.templateSubmissionStatus == "FINALIZED"){
		                			        	 opt = opt + '<a class="mrTop" href="#" title="Preview"><small class="fa fa-eye" onClick="viewFile(\''+output.templateFileURL+'\', \''+output.contractType+'\', \'application/pdf\')"></small></a>';
		                			         }else{
		                			        	 opt = opt + '<a class="mrTop" href="#" title="Preview"><small class="fa fa-eye" onclick="viewFile(\''+output.fileURL+'\', \''+output.fileName+'\', \''+output.fileType+'\')"></small></a>';
		                			         }
		                		     		'</sec:authorize>';
		                		     		  
// 	                	     	        	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
// 		                	       				opt = opt + '<div class="pull-left" href="#" title="Preview"><small class="text-primary fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></small></div>';
// 		                	       			'</sec:authorize>';
		                	       			
		                	       			if(output.templateSubmissionStatus == "DRAFT" || output.templateSubmissionStatus == "WAITING_FOR_APPROVER" || output.templateSubmissionStatus == "REJECTED" ){
		                	       				'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		                	     	     		if(output.typeOfContract == "New"){
		                	     	     			opt = opt + '<a class="mrTop" href="<c:url value="/app/addNewContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="glyphicon glyphicon-pencil editAA text-danger"></i></a>';
		                	     	     		}else if(output.typeOfContract == "Custom"){
		                	     	     			opt = opt + '<a href="<c:url value="/app/addCustomContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="glyphicon glyphicon-pencil editAA text-danger"></i></a>';
		                	     	     		}
		                	       			'</sec:authorize>';
		                	       			}
		                	     	         
		                	       			if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == output.ownerUser){
			                	   	        	 if(output.templateSubmissionStatus == "WAITING_TO_BE_VERIFY" && output.templateSubmissionStatus != "FINALIZED"){
			                	   		     			opt = opt +  '<a id="removeStatus'+output.id+'" class="fa fa-thumbs-o-up" title="finalize" onclick="finalizeContract('+output.id+',\'WAITING_TO_BE_VERIFY\');"></a>';
			                	   		     		}
		                	   	         	}
		                		     		
		                		     		opt = opt + '</div></td>';
		                		     	
		                		     	opt = opt + '</tr>';
	                		    }
                		    }
	                		
	                		opt = opt + '	</tbody>';
	                		opt = opt + '</table>';
	                    
	                		$("#draftCount").html(draftCount);
	                		$("#WFACount").html(waitingForApprovalCount);
	                		$("#WTSCount").html(waitingToBeSignedCount);
	                		$("#finalizedCount").html(finalizedCount);
	                		$("#rejectedCount").html(rejectedCount);
	                		$("#SFVCount").html(sendForVerificationCount);
	                		$("#pendingCount").html(pendingCount);
	                		$("#expiredCount").html(expiredCount);
	                		
	                     $("#clientContractsList").append(opt); 
	                     contractpagination(); 
	 },  
	 }); 
}
</script>

<script>
function contractpagination(){  
    $('#contractListTableDraft1').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"aoColumnDefs": [{
	           'bSortable': false,
	           'aTargets': 7
			}],
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $("#contractListTableDraft1").wrap("<div class='overflow_Auto'></div>"); 
}
</script>

<script type="text/javascript">
function previewContractPDF(uuid, templateName) {
	var sourceleadUrl ='${sourceleadUrl}';
	window.open(sourceleadUrl+'/viewContractPdf/'+uuid+'/'+templateName+'','_newtab');
}
function previewNewContractPDF(uuid, templateName) {
 	window.open(sourceleadUrl+'/viewContractPdf/'+uuid+'/'+templateName+'','_newtab');
}
</script>

<script>
function approveContract(recordId,status,orgRelationId){
	 var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	$("#contractloading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/approveContract?"+tokenParamName+"="+tokenValue+"&recordId="+recordId+"&status="+status,       
        success :function(data) {
        	if(data == 'REJECTED'){
        		$("#row"+recordId).empty();
    	    	$("#row"+recordId).html(rowHtmlContent);
    	    	 $("#removeStatus"+recordId).html("");
    	        $("#removeReject"+recordId).html("<span>Rejected</span>").addClass('text-success');
        	}else{
        		$("#row"+recordId).empty();
    	    	$("#row"+recordId).html(rowHtmlContent);
    	    	 $("#removeReject"+recordId).html("");
    	        $("#removeStatus"+recordId).html("<span>Approved</span>").addClass('text-success');
        	}
        	$("#contractloading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        	clientContracts(orgRelationId);
       },
   });  
}

function finalizeContract(recordId,status){
	 var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	$("#contractloading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
      type : "POST",
       url: sourceleadUrl+"/updateContractStatus?"+tokenParamName+"="+tokenValue+"&recordId="+recordId+"&status="+status,       
       success :function(data) {
       	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#removeStatus"+recordId).html("<span>Finalized</span>").addClass('text-success');
	        $("#contractloading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	        clientContracts(orgRelationId);
      },
  });  
}
</script>

<script>
function CountClick(contractUUID,parentId){
	$(".mask_time").fadeIn(200);
	$(".DocAccPopup").fadeIn(100);	
	var sourceleadUrl ='${sourceleadUrl}';
	$("#DocAccPopupDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getDocsByContractUUID?contractUUID="+contractUUID,       
       success :function(data) {
    	   $("#DocAccPopupDiv").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    	    var json = JSON.parse(data);
    	    var result= "";
    	    
    	    result = result +'		    <div class="col-xs-12 col-sm-12 col-md-12 clearfix DocAccWrap">                                                            ';
			result = result +'	     <div class="form-group">                                                                                                             ';
			result = result +'	       <label class="col-md-4 text-center"><strong>Title</strong></label>                                                 ';
			result = result +'	       <div class="col-md-8 text-center">                                                                                                             ';
				  result = result +'	       <div><strong>Action</strong></div>                                                 ';
				  result = result +'	       </div>';
				  result = result +'	       </div>';
				  result = result +'	       </div>';
			result = result + ' <input type="hidden" id="maxDocCount" name="maxDocCount" value="'+json.length+'" />';
    	    for (var i = 0; i < json.length; i++) {
    	      var opt=json[i];
			  result = result +'		    <div class="col-xs-12 col-sm-12 col-md-12 clearfix DocAccWrap">                                                            ';
			  result = result +'	     <div class="form-group">                                                                                                             ';
			  result = result + ' <input type="hidden" id="documentName" name="docfiles['+i+'].documentName" value="'+opt.fileName+'" />';
				  result = result +'	       <label class="col-md-4 text-center">'+opt.fileName+'</a></label>                                                 ';

				  /* if(opt.docFile != null && opt.docFile !="" && parentId == "--"){
				  result = result +'	       <div class="col-md-8 text-center">                                                                                                             ';
				  result = result +'	       <label class="text-center">--</label>                                                 ';
				  result = result +'	       </div>			                                                                                                                  ';
				  $("#saveAction").empty(); 
			  }
			  else{ 
				   if(parentId != "--"){ */
						  if(opt.fileURL != null && opt.fileURL !=""){
							  result = result +'	       <div class="col-md-8 text-center" id="removeDiv'+opt.id+'">                                                                                                             ';
							  result = result+'	                      &nbsp;&nbsp;<span id="delete_resume" onclick="removeResumeNameDiv('+opt.id+');"><i class="fa fa-trash text-danger removeIcon"></i></span>';
							  result = result+' <a download class="glyphicon glyphicon-download-alt" href="'+opt.fileURL+'" title="Download"></a>';
			         		  result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="viewFile(\''+opt.fileURL+'\', \''+opt.docFileName+'\', \''+opt.fileType+'\')"title="preview"></a>';
							  result = result +'	       </div>			                                                                                                                  ';
							  result = result +'	       <div class="col-md-8" id="uploadDocumentDiv'+opt.id+'" style="display: none;">                                                                                                             ';
							  result = result +'				<div class="input-group positionRelative ratetype" id="uploadFileGroup'+opt.id+'" title="Browse files..">                  ';
							  result = result +'					<label class="input-group-btn"> <span class="btn btn-primary padSm" onchange="uploadRequestedDocuments('+opt.id+');"><i class="fa fa-folder-open-o"></i>&nbsp;Browse           ';
							  result = result +'	             <input name="uploadBtn'+opt.id+'" id="uploadBtn'+opt.id+'" style="display: none;" class="upload" type="file" onclick="check('+opt.id+');">                            ';
							  result = result +'	             </span> </label>                                                                                                                 ';
							  result = result +'					<input class="form-control padSm"  id="uploadFile'+opt.id+'" name="docfiles['+i+'].docFileName"  placeholder="Upload File" readonly type="text">  ';
							  result = result +'				</div>                                                                                                                            ';
							  result = result +'	       </div>			                                                                                                                  ';
						  }
						  else{
							  result = result +'	       <div class="col-md-8" id="uploadDocumentDiv'+opt.id+'">                                                                                                             ';
							  result = result +'				<div class="input-group positionRelative ratetype" id="uploadFileGroup'+opt.id+'" title="Browse files..">                  ';
							  result = result +'					<label class="input-group-btn"> <span class="btn btn-primary padSm" onchange="uploadRequestedDocuments('+opt.id+');"><i class="fa fa-folder-open-o"></i>&nbsp;Browse           ';
							  result = result +'	             <input id="uploadBtn'+opt.id+'" style="display: none;" class="upload" type="file" onclick="check('+opt.id+');">                            ';
							  result = result +'	             </span> </label>                                                                                                                 ';
							  result = result +'					<input class="form-control padSm"  id="uploadFile'+opt.id+'" name="docfiles['+i+'].docFileName"  placeholder="Upload File" readonly type="text">  ';
							  result = result +'				</div>                                                                                                                            ';
							  result = result +'	       </div>			                                                                                                                  ';
					  }
				/*   }
				  else{
					  $("#saveAction").empty(); 
					  result = result +'	       <div class="col-md-8 text-center">                                                                                                             ';
					  result = result +'	       <label class="text-center">--</label>                                                 ';
					  result = result +'	       </div>			                                                                                                                  ';
					  $("#saveAction").empty(); 
				  } */
			 /*  } */
			  result = result +'			</div>                                                                                                                                ';
			  result = result +'			<div class="clearfix"></div>                                                                                                          ';
			  result = result +'	 </div>  ';     
			  result = result + ' <input type="hidden" id="docId'+(i+1)+'" name="docfiles['+i+'].docId" value="'+opt.id+'" />';
    	    }
			  $("#DocAccPopupDiv").empty();
			  $("#DocAccPopupDiv").append(result);
       },
	  }); 
}

function removeResumeNameDiv(id){
	$("#removeDiv"+id).hide();
	
		 var sourceleadUrl ='${sourceleadUrl}';
		 $.ajax({
	     type : "POST",
	      url: sourceleadUrl+"/updateDocument?${_csrf.parameterName}=${_csrf.token}&docuId="+id,       
	      success :function(data) {
	      	  //  $("#tr"+id).empty();
	    	  $("#uploadDocumentDiv"+id).show();
	     },
	 });   
//	$("#uploadFile"+id).attr("data-validation", "required validate_Space validatedoc");
}
function contPopClose(){
	$("#viewPopupDiv").empty();
	$(".mask_time").fadeOut(200);
	$(".contPopup").fadeOut(100);
	$(".DocAccPopup").fadeOut(100);	
}
function check(id){
document.getElementById("uploadBtn"+id).onchange = function () {
    document.getElementById("uploadFile"+id).value = this.value.split( '\\' ).pop();;
    };
    
    /* upload file script for size validation 2MB*/
    var selectedFile = document.getElementById("uploadBtn"+id);
    selectedFile.addEventListener("change", function() {
      var docSize = this.files[0].size;
      $("#uploadFileGroup"+id).find('p.jquery_form_error_message').remove();
      if(docSize > 2097152){
    	  document.getElementById("uploadFile"+id).value = '';
    	  $("#uploadFileGroup"+id).append("<p class='jquery_form_error_message'>Limit is exceeded than 2MB </p>");
      }
    });
}

function uploadRequestedDocuments(id){
	var sourceleadUrl ='${sourceleadUrl}';
	fileUpload("uploadBtn"+id+"", "Requested Documents", "contractDoc_form");
}
</script>

<script type="text/javascript">
	function saveDocContract() {
		var orgRelationId = $("#contractRelationId").val();
		if ($('#contractDoc_form').validate(false, validationSettings)) {
			/* var frm = $('#contractDoc_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
	        var jsonData = encodeURIComponent(con);
	        var form = $('#contractDoc_form')[0];
 	 	    var formData = new FormData(form); 
			var sourceleadUrl = '${sourceleadUrl}';
			$("#save_Doc").attr("disabled", "disabled");
			$("#contractDoc_form").attr("disabled", "disabled");
			$("#loadingDoc").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 		$.ajax({
	 			 type: "POST",
		    	 data: formData,
			     contentType : 'application/json; charset=utf-8',
			     enctype: 'multipart/form-data',
		         processData: false,
		         contentType:false,
			     dataType : 'json',
						url : sourceleadUrl+ "/saveDocContracts?"+tokenParamName+"="+tokenValue+"&json="+jsonData,
						success : function(result) { */
							
			var formData = getFormData("contractDoc_form");
			var orgLocationId = '${SourceLeadWeb_orgLocationId}';
			$("#loadingDoc").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            $.post( sourceleadUrl+ "/saveDocContracts?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId, formData, function(data) {
				clientContracts(orgRelationId);
				contPopClose();
			}); 
			return true;
		} else {
			return false;
		}
	}
	$('body').on('blur', '#contractDoc_form', function() {
		$("#contractDoc_form").showHelpOnFocus();
		$("#contractDoc_form").validateOnBlur(false, validationSettings);
	});
</script>

<script>
function PopUp(contractUUID){
	$(".mask_time").fadeIn(200);
	$(".contPopup").fadeIn(100);
	var sourceleadUrl ='${sourceleadUrl}';
	$("#viewPopupDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getContractByContractUUID?contractUUID="+contractUUID,       
       success :function(data) {
    	   $("#viewPopupDiv").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    	   
    	    var result = '';
    	    var json = JSON.parse(data);
			result = result +	'<div class="contPopupHead clearfix">';
			result = result +	'<h4>'+json.contractTitle+'</h4>';
			result = result +	'<span>';
			
			if(json.templateSubmissionStatus == 'DRAFT' || json.templateSubmissionStatus == 'REJECTED' || json.templateSubmissionStatus == 'WAITING_FOR_APPROVER'){
				if('New' == json.typeOfContract){
					result = result +	'	<a href="/app/addNewContract?contractUUID='+contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>  <i class="fa fa-times" onclick="contPopClose();"></i>';
				}else if('Existing' == json.typeOfContract){
					result = result +	'	<i class="fa fa-times" onclick="contPopClose();"></i>';
				}else if('Custom' == json.typeOfContract){
					result = result +	'	<a href="/app/addCustomContract?contractUUID='+contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>  <i class="fa fa-times" onclick="contPopClose();"></i>';
				}
			}else{
				result = result +	'	<i class="fa fa-times" onclick="contPopClose();"></i>';
			}
			
			result = result +	'</span>';
			result = result +	'</div>';
		
			result = result +	'<div class="modal-body clearfix">';
			result = result +	'<div class="col-xs-12 col-md-12 col-lg-12 noPad clearfix mt10">';
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Contract Title</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contractTitle+'</div>';
			result = result +	'	</div>';
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Contract Type</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contractType+'</div>';
			result = result +	'	</div>';
			
			if(json.templateName != null && 'Custom' != json.typeOfContract && 'Existing' != json.typeOfContract){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Template Title</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.templateName+' <a href="#" title="Preview"><i  title="View Template" class="fa fa-eye" onclick="previewTemplatePDF(\''+json.tempUUID+'\', \''+json.templateName+'\')"></i></a></div>';
				result = result +	'	</div>';
			}
			if(null != json.startDateStr && "" != json.startDateStr && 'undefined' != json.startDateStr){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Start Date</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.startDateStr+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.joinedDateStr && "" != json.joinedDateStr && 'undefined' != json.joinedDateStr){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Join Date</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.joinedDateStr+'</div>';
				result = result +	'	</div>';
			}
			if(null != json.workLocation && "" != json.workLocation && 'undefined' != json.workLocation){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Work Location</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.workLocation+'</div>';
				result = result +	'	</div>';
			}
			
			if(json.contractType!="Full time offer"){
				if(null != json.endDateStr && "" != json.endDateStr && 'undefined' != json.endDateStr){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">End Date </div>';
					result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: '+json.endDateStr+' <i class="fa fa-pencil popEdCo"  onclick="editOne(\''+json.startDateStr+'\',\''+json.endDateStr+'\');"></i>';
					result = result +	'<div class="editNewDat"><input type="text" class="form-control calendar" value="'+json.endDateStr+'" id="endDate" name="endDate" placeholder="End date" readonly="readonly"><i class="fa fa-floppy-o" onclick="editClo('+json.id+');"></i></div>';
					result = result +	'	</div>';
					result = result +	'	</div>';
				}
				else{
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">End Date </div>';
					result = result +	'		<div class="col-md-8 col-sm-8 popRela" id="endVal">: --  <i class="fa fa-pencil popEdCo"  onclick="editOne(\''+json.startDateStr+'\',\''+json.endDateStr+'\');"></i>';
					result = result +	'<div class="editNewDat"><input type="text" class="form-control calendar" value="" id="endDate" name="endDate" placeholder="End date" readonly="readonly"><i class="fa fa-floppy-o" onclick="editClo('+json.id+');"></i></div>';
					result = result +	'	</div>';
					result = result +	'	</div>';
				}
				
			}
			
			if(json.contractType =="Full time offer"){
				if(null != json.billingAmount && "" != json.billingAmount && 'undefined' != json.billingAmount){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Offer Rate</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.billingRateType+' '+json.billingAmount+' / '+json.billingRatePer+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(json.ownerName != null && json.ownerName != '' && json.ownerName != 'undefined'){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Owner</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.ownerName+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.approverUserName && "" != json.approverUserName && 'undefined' != json.approverUserName){
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Approver</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.approverUserName+'</div>';
			result = result +	'	</div>';
			}
			
			if((json.requirementName != '' && json.requirementName != 'undefined' && json.requirementName != null) && (json.contractType.includes("SOW") || json.contractType =="Full time offer")){
				
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Job Title</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.requirementName+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.typeOfContract && (json.contractType.includes("SOW") || json.contractType =="Full time offer")){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Applicant Name</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.applicantName+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.offerValue && "" != json.offerValue && 'undefined' != json.offerValue){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Offer Rate</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.offerValue+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.duration && "" != json.duration && 'undefined' != json.duration){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Duration</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: </div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.initiatorType && "Client" == json.initiatorType && json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Vendor</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contractWith+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.initiatorType && "Vendor" == json.initiatorType && json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Client</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contractWith+'</div>';
				result = result +	'	</div>';
			}
			if(json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Contact</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.contactUserName+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.stateJurisdiction && "" != json.stateJurisdiction && 'undefined' != json.stateJurisdiction){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">State of Jurisdiction</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.stateJurisdiction+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.description && "" != json.description && 'undefined' != json.description && json.contractType!="Full time offer"){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Description</div>';
				result = result +	'		<div class="col-md-8 col-sm-8"><i style="display: inline-block; font-style:normal; float:left;">:</i><span style=" padding: 0px 0px 0px 3px; display: inline-block; width:90%; float:left;">'+json.description+'</span></div>';
				result = result +	'	</div>';
			}
			
			if(null != json.rejectedReason && "" != json.rejectedReason && 'undefined' != json.rejectedReason && json.templateSubmissionStatus == 'REJECTED' ){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Reject Reason</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.rejectedReason+'</div>';
				result = result +	'	</div>';
			}
			
			result = result +	'<div class="clearfix"/>';
			
		if(json.contractType != "Vendor NDA" && json.contractType != "Vendor NSA" && json.contractType!="Full time offer"){
			result = result +	'<h4>Billing Details</h4>';
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.hoursPerWeek && "" != json.hoursPerWeek && 'undefined' != json.hoursPerWeek){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Hours Per Week</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.hoursPerWeek+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.overTimeHours && "" != json.overTimeHours && 'undefined' != json.overTimeHours){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Overtime Hours</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.overTimeHours+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.overtimeRate && "" != json.overtimeRate && 'undefined' != json.overtimeRate){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Overtime rate</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.overtimeRate+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.typeOfContract && json.contractType.includes("SOW")){
				if(null != json.totalHours && "" != json.totalHours && 'undefined' != json.totalHours){
					result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
					result = result +	'		<div class="col-md-4 col-sm-4">Total Estimated Hours</div>';
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.totalHours+'</div>';
					result = result +	'	</div>';
				}
			}
			
			if(null != json.billingAmount && "" != json.billingAmount && 'undefined' != json.billingAmount){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Billing Amount</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.billingRateType+' '+json.billingAmount+' / '+json.billingRatePer+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.invoiceFrequency && "" != json.invoiceFrequency && 'undefined' != json.invoiceFrequency){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Billing Cycle</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.invoiceFrequency+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.paymentTerm && "" != json.paymentTerm && 'undefined' != json.paymentTerm){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Payment Term</div>';
				result = result +	'		<div class="col-md-8 col-sm-8">: '+json.paymentTerm+'</div>';
				result = result +	'	</div>';
			}
			
			if(null != json.tax && "" != json.tax && 'undefined' != json.tax){
				result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
				result = result +	'		<div class="col-md-4 col-sm-4">Tax</div>';
				var abc = json.taxType;
				if(abc == "Percentage"){
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.tax+' %</div>';
				}else{
					result = result +	'		<div class="col-md-8 col-sm-8">: '+json.tax+' '+json.taxType+'</div>';
				}
				
				//result = result +	'		<div class="col-md-8 col-sm-8">: '+json.tax+' '+json.taxType+'</div>';
				result = result +	'	</div>';
			}
			
			result = result +	'<div class="clearfix"/>';
		}
			result = result +	'<h4>History</h4>';
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Created On</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.createdDateStr+'</div>';
			result = result +	'	</div>';
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Created By</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.createdByName+'</div>';
			result = result +	'	</div>';
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Updated On</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.updatedDateStr+'</div>';
			result = result +	'	</div>';
			
			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Updated By</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.updatedByName+'</div>';
			result = result +	'	</div>';

			result = result +	'	<div class="col-md-6 col-sm-6 noPad clearfix mb">';
			result = result +	'		<div class="col-md-4 col-sm-4">Added From</div>';
			result = result +	'		<div class="col-md-8 col-sm-8">: '+json.typeOfContract+'</div>';
			result = result +	'	</div>';
			
			result = result +	'</div>';
			result = result +	'</div>';
			$("#viewPopupDiv").empty();
			$("#viewPopupDiv").append(result);
       },
	  }); 
}


function previewTemplatePDF(uuid, templateName){
	var sourceleadUrl ='${sourceleadUrl}';
	if(uuid != null && uuid != ""){
		window.open(sourceleadUrl+'/viewContractTemplatePdf/'+uuid+'/'+templateName+'','_newtab');
	}
}
</script>

<script>
function editOne(startDate,endDate){
	
	var ClientcontractEffectiveEndDate = startDate.split("-");
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateeditOneSave = '';
	  var d2 = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateeditOneSave = 'yy-mm-dd';
		   d2 = new Date(ClientcontractEffectiveEndDate[0], parseInt(ClientcontractEffectiveEndDate[1])-parseInt(1), parseInt(ClientcontractEffectiveEndDate[2])+parseInt(1));
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateeditOneSave = 'dd-mm-yy'; 
		   d2 = new Date(ClientcontractEffectiveEndDate[2], parseInt(ClientcontractEffectiveEndDate[1])-parseInt(1), parseInt(ClientcontractEffectiveEndDate[0])+parseInt(1));
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateeditOneSave = 'mm-dd-yy'; 
		   d2 = new Date(ClientcontractEffectiveEndDate[2], parseInt(ClientcontractEffectiveEndDate[0])-parseInt(1), parseInt(ClientcontractEffectiveEndDate[1])+parseInt(1));
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateeditOneSave = 'yy-dd-mm';
		   d2 = new Date(ClientcontractEffectiveEndDate[0], parseInt(ClientcontractEffectiveEndDate[2])-parseInt(1), parseInt(ClientcontractEffectiveEndDate[1])+parseInt(1));
	  }
	
	 var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}'; 
	  var finalDateFormatEditEndDateSave = '';
	  if(locationDate.match("yyyy-MM-dd")){
		  finalDateFormatEditEndDateSave = 'yy-mm-dd';
	  }if(locationDate.match("dd-MM-yyyy")){
		  finalDateFormatEditEndDateSave = 'dd-mm-yy'; 
	  }if(locationDate.match("MM-dd-yyyy")){
		  finalDateFormatEditEndDateSave = 'mm-dd-yy'; 
	  }if(locationDate.match("yyyy-dd-MM")){
		  finalDateFormatEditEndDateSave = 'yy-dd-mm'; 
	  }
	
	$('.editNewDat').show();
	$('#endDate').datepicker({
        dateFormat: finalDateFormatEditEndDateSave,
        changeMonth: true,
          changeYear: true,
          minDate: d2 ,
          onSelect: function (selected) {
              var dt = toDate("#endDate");
              dt.setDate(dt.getDate() - 1);
          }
    });
}
function toDate(selector) {
    var from = $(selector).val().split("-");
    return new Date(from[2], from[1] - 1, from[0]);
}

function editClo(contractId){
	$('.editNewDat').hide();
	var orgRelationId = $("#contractRelationId").val();
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#endDate").val();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    type : "POST",
     url: sourceleadUrl+"/updateContractEndDate?"+tokenParamName+"="+tokenValue+"&contractId="+contractId+"&endDate="+endDate,       
     success :function(data) {
    	 $("#endVal").empty();
		  $("#endVal").append(": "+endDate);
		  clientContractsList(orgRelationId);
     	 //   $("#tr"+id).empty();
    },
});  
	
}
</script>
