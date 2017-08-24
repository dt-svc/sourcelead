<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
$(document).ready(function(){
   $("#contractStage").val("DRAFT");
   getTemplateList();
   getStageCounts();
});
</script>

<style>
.CurDiv { position: static !important; float: left;}
.timesheet_head ul {
padding: 2px 300px 2px 0px;
}
</style>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Contracts</h2>
  </div>
   <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; Contracts</div>
</section>

<section class="contrHead"> 
<ul> 
<li title="Draft"><a href="#" id="draft" class="draft" onClick="mainTabEnable(this);getTemplateList();">Draft (${contractsCount.totalDraftCount})</a></li>
<li title="Waiting for Approval"><a href="#" id="approve"  onClick="mainTabEnable(this);getTemplateList();"><i class="fa fa-clock-o fa-2"></i>Waiting (${contractsCount.totalWTACount})</a></li> 
<li title="Waiting to be sign"><a href="#" id="sign" onClick="mainTabEnable(this);getTemplateList();"><i class="fa fa-clock-o fa-2"></i>Sign (${contractsCount.totalWTSCount})</a></li>
<li title="Waiting for Verify"><a href="#" id="verify" onClick="mainTabEnable(this);getTemplateList();"><i class="fa fa-clock-o fa-2"></i>Counter sign (${contractsCount.totalWTVCount})</a></li>
<li title="Finalized"><a href="#" id="finalised"  onClick="mainTabEnable(this); getTemplateList();">Completed (${contractsCount.totalFinalizedCount})</a></li>
<li title="Expired"><a href="#" id="expired" onClick="mainTabEnable(this);getTemplateList();">Expired (${contractsCount.totalExpiredCount})</a></li>
<li title="Rejected"><a href="#" id="rejected"  onClick="mainTabEnable(this);getTemplateList();">Rejected (${contractsCount.totalRejectedCount})</a></li>
<input type="hidden" name="contractStage" id="contractStage" >
<div class="conBtn">
  <span onclick="addContracts();">Add<i class="fa fa-caret-down"></i></span>
<%--   <ul>  
	  <sec:authorize access="hasAnyRole('ROLE_ADD_NEW_CONTRACT')">
	  <li><a href="<c:url value="/addNewContract" />"><i class="fa fa-caret-right"></i>New Contract</a></li>
	  </sec:authorize>
	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')">
	  <li><a href="<c:url value="/addExistingContract" />"><i class="fa fa-caret-right"></i>Existing Contract</a></li>
  	  </sec:authorize>
  </ul> --%>
</div>
<div class="clearfix"></div>
</ul>
</section>

<!-- Add candidate Popup -->
  <div class="addcandidate-popup">
    <div class="row">
      <div class="col-md-12"> <a href="javascript:void(0)" class="close-popdiv" onClick="closeContract()"><i class="fa fa-close"></i></a>
      <sec:authorize access="hasAnyRole('ROLE_ADD_NEW_CONTRACT')">
      <div class="col-md-4 mt25">
        <div class="small-widget bgGreen"> <a href="<c:url value="/addNewContract" />" title="New Contract">
          <div class="clearfix mb10"> <i class="fa fa-file-o fa-4x"></i> <span class="fa fa-plus"></span> </div>
          <strong class="clearfix">New Contract</strong> </a> </div>
      </div>
 	</sec:authorize>
	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')">
        <div class="col-md-4 mt25">
          <div class="small-widget bg-yellow"> <a href="<c:url value="/addCustomContract" />" title="Custom Contract">
            <div class="clearfix mb10"><i class="fa fa-file-code-o fa-4x"></i> <span class="fa fa-plus"></span> </div>
            <strong class="clearfix">Custom Contract</strong> </a> </div>
        </div>
        </sec:authorize>
	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')">
        <div class="col-md-4 mt25">
          <div class="small-widget bg-purple"> <a href="<c:url value="/addExistingContract" />" title="Existing Contract">
            <div class="clearfix mb10"><i class="fa fa-file-text-o fa-4x"></i> <span class="fa fa-plus"></span> </div>
            <strong class="clearfix">Existing Contract</strong> </a> </div>
        </div>
        </sec:authorize>        
       </div>
    </div>
  </div>
  <div class="pop_mask"></div>
  <script>
    //addcandidate-popup script
    function addContracts(){
        $('.addcandidate-popup').fadeIn(200);
        $('.pop_mask').fadeIn(200);
    };
       function closeContract(){
        $('.addcandidate-popup').fadeOut(200);
        $('.pop_mask').fadeOut(200);
       }
    </script> 


<div id="popUp"></div>

	
<div class="content-area content-new clearfix">
 <div class="project_wrap">
	<div class="timesheet_head">
		<div class="ContrBut">
		    <input type="hidden" name="tabType" id="tabType" value='Vendor'>
			<!-- <a href="#" id="Vendor" class="active tabActivation" onclick="tabEnable(this);getTemplateList('pending');">Vendors</a>
			<a href="#" id="Client" class="tabActivation" onclick="tabEnable(this);getTemplateList('pending');">Clients</a> -->
			<a href="#" id="Vendor1" class="active tabActivation" onclick="tabEnable(this);Check('Vendors');">Vendors (${contractsCount.totalDraftVendorCount})</a>
			<a href="#" id="Client1" class="tabActivation" onclick="tabEnable(this);Check('Clients');">Clients (${contractsCount.totalDraftClientCount})</a>
			<a href="#" id="Applicants1" class="tabActivation" onclick="tabEnable(this);Check('Applicants');">Applicants (${contractsCount.totalDraftClientCount})</a>
		</div>
		<ul>
			<li id="filterContractTypePop"><i class="fa fa-filter"></i><span>Type</span><i class="glyphicon glyphicon-play"></i>
	           	<div class="statusFiler-1 approved_pop">
					<ul class="list-unstyled">
						<li><input id="c1" type="checkbox" name="contractType" value="MSA" onclick="getTemplateList();"><label for="c1">MSA</label></li>
						<li><input id="c2" type="checkbox" name="contractType" value="SOW" onclick="getTemplateList();"><label for="c2">SOW</label></li>
						<li><input id="c3" type="checkbox" name="contractType" value="NDA" onclick="getTemplateList();"><label for="c3">NDA</label></li>
						<li><input id="c4" type="checkbox" name="contractType" value="NSA" onclick="getTemplateList();"><label for="c4">NSA</label></li>
					</ul>
				</div>	                	
			</li>
			<li id="filterContractTypePop-2" style="display:none"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i>
	           	<div class="statusFiler-3 approved_pop">
					<ul class="list-unstyled" style="display:none" id="signedDiv">
						<li><input id="status1" type="checkbox" name="contractStatus" value="PENDING" onclick="getTemplateList();"><label for="status1">Pending</label></li>
						<li><input id="status3" type="checkbox" name="contractStatus" value="WAITING_TO_BE_SIGNED" onclick="getTemplateList();"><label for="status3">Waiting to be Sign</label></li>
					</ul>
					
					<ul class="list-unstyled" style="display:none" id="verifyDiv">
						<li><input id="status2" type="checkbox" name="contractStatus" value="SEND_FOR_VERIFICATION" onclick="getTemplateList();"><label for="status2">Send For Verification</label></li>
						<li><input id="status4" type="checkbox" name="contractStatus" value="WAITING_TO_BE_VERIFY" onclick="getTemplateList();"><label for="status4">Waiting to be Verify</label></li>
					</ul>
				</div>	                	
			</li>
			<div class="clearfix"></div>
		</ul>
		<div class="clearfix"></div>
            
	</div>
	<div id="loading"></div>
    <div class="clients_wrap_table repoPositiNoCount">
    <div id="vendorContractsList"  class=" overflow-data" ></div>
     <div id="clientContractsList"  class=" overflow-data" style="display: none"></div>
     <div id="applicantsContractsList"  class=" overflow-data" style="display: none"></div>
	</div>
	
</div>
  
</div>
</section>
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
	
<!-- 	<div class="col-xs-12 col-sm-12 col-md-12 clearfix myForm DocAccWrap">
            <div class="form-group">
              <label class="col-md-4">Attachment<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
				<div class="input-group positionRelative ratetype" id="uploadFileGroup" title="Document Type: .pdf format only">
					<label class="input-group-btn"> <span class="btn btn-primary padSm"><i class="fa fa-folder-open-o"></i>&nbsp;Browse
	                <input name="uploadBtn" id="uploadBtn" style="display: none;" multiple="" class="upload" type="file">
	                </span> </label>
					<input class="form-control padSm" readonly="" id="uploadFile" name="docFile" value="" placeholder="Upload File" type="text">
				</div>
              </div>			
			</div>
			
			<div class="clearfix"></div>
        </div> -->
        
<div class="clearfix DocAccWrapFoot" id="saveAction">
		<!-- <input type="button" value="Submit" class="btn btn-primary padSm"> -->
		 <input type="button" value="Submit" id="save_Doc" onclick="saveDocContract();" class="btn btn-primary padSm">
		<input type="button" value="Cancel" class="btn btn-default padSm" onclick="contPopClose();">
      </div>
</form:form>
</section>

<script type="text/javascript">
	function saveDocContract() {
		if ($('#contractDoc_form').validate(false, validationSettings)) {
			var frm = $('#contractDoc_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, "");
	        var jsonData = encodeURIComponent(con);
	        var form = $('#contractDoc_form')[0];
 	 	    var formData = new FormData(form); 
 	 	 	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
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
						url : sourceleadUrl+ "/saveDocContracts?${_csrf.parameterName}=${_csrf.token}&json="+jsonData+"&orgLocationId="+orgLocationId,
						success : function(result) {
							location.replace(sourceleadUrl+"/contractListbyStatus");
						},
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
function PopUp(contractUUID,stage){
	var sourceleadUrl ='${sourceleadUrl}';
	localStorage.setItem("stage",stage);
 	window.location.replace(sourceleadUrl+'/viewContract?contractUUID='+contractUUID);
}


function previewTemplatePDF(uuid, templateName){
	var sourceleadUrl ='${sourceleadUrl}';
	if(uuid != null && uuid != ""){
		window.open(sourceleadUrl+'/viewContractTemplatePdf/'+uuid+'/'+templateName+'','_newtab');
	}
}
</script>

<script type="text/javascript">
            $(function () {
                $('#datetimepicker3').datetimepicker({
                    format: 'LT'
                });
            });
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
				$(document).ready(function(){
					$("#cd").click(function(){
						$(".clientDetails").toggleClass('hidden', 500);
					});
	
	});
 		</script> 
<script>
    $("#uasrch").selectpicker('refresh');
	
// Export Popup
    $("#export-pop").click(function(){
		$(".statusFiler-2").toggle();
		$(".statusFiler-1, .statusFiler-3").hide();
	});
    $("html").click(function() {
        $(".statusFiler-2, .statusFiler-1").hide();
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
	$("#filterContractTypePop").click(function(){
		$(".statusFiler-1").toggle();
		$(".statusFiler-2, .statusFiler-3").hide();
	});
	$("#filterContractTypePop-2").click(function(){
		$(".statusFiler-3").toggle();
		$(".statusFiler-2, .statusFiler-1").hide();
	});
	
    $("html").click(function() {
        $(".filterContractType_pop").hide();
    });	
    $(".filterContractType_pop, #filterContractTypePop, #filterContractTypePop-2, .statusFiler-1, .statusFiler-2, .statusFiler-3").click(function(e) {
        e.stopPropagation();
    });	

</script>

<script type="text/javascript">
function getTemplateList(){
	var contractSubmissionType = $("#contractStage").val();
	if(contractSubmissionType == 'WAITING_TO_BE_SIGNED'){
		$("#filterContractTypePop-2").css("display", "block");
		$("#verifyDiv").css("display", "none");
		$("#signedDiv").css("display", "block");
	}else if(contractSubmissionType == 'WAITING_TO_BE_VERIFY'){
		$("#filterContractTypePop-2").css("display", "block");
		$("#signedDiv").css("display", "none");
		$("#verifyDiv").css("display", "block");
	}else{
		$("#filterContractTypePop-2").css("display", "none");
	}
	
	
	var contractStatus = [];
	$('input:checkbox[name=contractStatus]:checked').each(function() 
    {
		contractStatus.push($(this).val());
    });
	
	var contractsType = [];
	$('input:checkbox[name=contractType]:checked').each(function() 
    {
		contractsType.push($(this).val());
    });
	var tabType = $("#tabType").val();
	var sourceleadUrl ='${sourceleadUrl}';
	var selectedContractType = "";
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getAllContractsByOrgLocationIdandStatus?${_csrf.parameterName}=${_csrf.token}&contractSubmissionType="+contractSubmissionType+"&tabType="+tabType+"&contractsType="+contractsType+"&contractStatus="+contractStatus,       
       success :function(data) {
    	   $("#loading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    	   if(contractSubmissionType == 'DRAFT'){
    		   preparedContractListDraft(data);
    	   }else if(contractSubmissionType == 'WAITING_FOR_APPROVER'){
    		   preparedContractListWFA(data);
    	   }else if(contractSubmissionType == 'WAITING_TO_BE_SIGNED'){
    		   preparedContractListWTS(data);
    	   }else if(contractSubmissionType == 'WAITING_TO_BE_VERIFY'){
    		   preparedContractListWTV(data);
    	   }else if(contractSubmissionType == 'Finalized'){
    		   preparedContractListFinalize(data);
    	   }else if(contractSubmissionType == 'EXPIRED'){
    		   preparedContractListExpired(data);
    	   }else if(contractSubmissionType == 'REJECTED'){
    		   preparedContractListRejected(data);
    	   }
    	   getStageCounts();
      },
  }); 
}
</script>

<script>
function preparedContractListDraft(data){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableDraft" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Signing Authority</th>';
		result = result + '    <th>Contact</th>';
		result = result + '    <th>Vendor</th>';
		result = result + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		result = result + '    <th>Req Docs</th>'; 
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		        if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'DRAFT\');"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		        }else{
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" ><span title="'+report.contractTitle+'">'+report.contractTitle+'</span></div>';
		        }
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
		        	 result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result +' | '+ report.endDateStr+'</div>';
		         }
		         result = result + '</td>';
		         /* result = result + '<td>';
		        	 result = result + '<div class="tblminwidth-75">'+report.reqDocCount+'</div>';
		         result = result + '</td>'; */
		         
		         result = result + '<td>';
		         if(report.reqDocCount!="0"){
		        	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+report.contractUUID+'\',\''+report.parentId+'\');">'+report.reqDocCount+'</div>'; 
		         }
		         else{
		        	 result = result + '<div class="tblminwidth-75 ">'+report.reqDocCount+'</div>';
		         }	 
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tbldata-dotdot_80">';
		     	if(report.templateSubmissionStatus == "DRAFT"){
		     		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		     		result = result + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a>';
		     		'</sec:authorize>';
		     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		     		if(report.typeOfContract == "New"){
		     			result = result + '| <a href="<c:url value="/addNewContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
		     		if(report.typeOfContract == "Custom"){
		     			result = result + '| <a href="<c:url value="/addCustomContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
		     		'</sec:authorize>';
	     	  }
		         result = result + '</div></td>';
		     	 
		        result = result + '</tr>';
		    }
			
	}
	result = result + '	</tbody>';
	result = result + '</table>';
	var opt="";
	opt = opt +  '<table id="contractListTableDraft1" class="display" cellspacing="0" width="100%" >';
	opt = opt + '	<thead>';
	opt = opt + '   <tr>';
	opt = opt + '    <th>Title</th>';
	opt = opt + '    <th>Type</th>';
	opt = opt + '    <th>Signing Authority</th>';
	opt = opt + '    <th>Contact</th>';
	opt = opt + '    <th>Client</th>';
	opt = opt + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
	opt = opt + '    <th>Req Docs</th>'; 
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	        if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+output.contractUUID+'\',\'DRAFT\');"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
	        }else{
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" ><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
	        }
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
	         if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	        	 opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	         }
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt +' | '+output.endDateStr+'</div>';
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
	         opt = opt + '<div class="tbldata-dotdot_80">';
	     	  if(output.templateSubmissionStatus == "DRAFT" ){
	     		 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a>';
	     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		     		if(output.typeOfContract == "New"){
		     			opt = opt + '| <a href="<c:url value="/addNewContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
		     		if(output.typeOfContract == "Custom"){
		     			opt = opt + '| <a href="<c:url value="/addCustomContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
	     		'</sec:authorize>';
	     	  }
	     		opt = opt + '</div></td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	var res = '';
	 res = res +  '<table id="contractListTableDraft2" class="display" cellspacing="0" width="100%" >';
		res = res + '	<thead>';
		res = res + '   <tr>';
		res = res + '    <th>Title</th>';
		res = res + '    <th>Type</th>';
		res = res + '    <th>Signing Authority</th>';
		res = res + '    <th>Employee Name</th>';
		res = res + '    <th>Join date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		res = res + '    <th>Rate</th>'; 
		res = res + '    <th>Req Docs</th>'; 
		res = res + '    <th>Action</th>';
		res = res + '   </tr>';
		res = res + '	</thead>';
		res = res + '	<tbody>';
	if(json.fullTimeoffercontractsList.length>0){
		    for (var i = 0; i < json.fullTimeoffercontractsList.length; i++) {
		    	var appReport= json.fullTimeoffercontractsList[i];
		    	res = res + '<tr id="row'+appReport.id+'">';
		        res = res + '<td>';
		        	res = res + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+appReport.contractUUID+'\',\'DRAFT\');"><span title="'+appReport.contractTitle+'">'+appReport.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		         res = res + '</td>';
		         res = res + '<td>';
		           	res = res + '<div class="tblminwidth-75">'+appReport.contractType+'</div>';
		         res = res + '</td>';
		    	 res = res + '<td>';
		       	 res = res + '<div><span title="'+appReport.ownerName+'">'+appReport.ownerName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.empName+'">'+appReport.empName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tblminwidth-75">'+appReport.joinedDateStr+'</div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         if(appReport.billingAmount != null && appReport.billingAmount != '' && appReport.billingAmount != 'undefined'){
		      	   res = res + '<div class="tblminwidth-75">'+appReport.billingAmount+'</div>';
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75">--</div>'; 
		         }
		         res = res + '</td>';
		         /* res = res + '<td>';
		        	 res = res + '<div class="tblminwidth-75">'+appReport.reqDocCount+'</div>';
		         res = res + '</td>'; */
		         
		         res = res + '<td>';
		         if(appReport.reqDocCount!="0"){
		        	 res = res + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+appReport.contractUUID+'\',\''+appReport.parentId+'\');">'+appReport.reqDocCount+'</div>'; 
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75 ">'+appReport.reqDocCount+'</div>';
		         }	 
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tbldata-dotdot_80">';
		     	if(appReport.templateSubmissionStatus == "DRAFT"){
		     		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		     		res = res + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+appReport.contractUUID+'\', \''+appReport.contractTitle+'\')"></i></a>';
		     		'</sec:authorize>';
		     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		     		if(appReport.typeOfContract == "New"){
		     			res = res + '| <a href="<c:url value="/addNewContract?contractUUID='+appReport.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
		     		if(appReport.typeOfContract == "Custom"){
		     			res = res + '| <a href="<c:url value="/addCustomContract?contractUUID='+appReport.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
		     		'</sec:authorize>';
		     		//res = res + '</div>';
	     	  }
		         res = res + '</div></td>';
		     	 
		        res = res + '</tr>';
		    }
			
	}
	res = res + '	</tbody>';
	res = res + '</table>';
	
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var fullTimeCount = json.fullTimeoffercontractsList.length;
	var draftCount = parseInt(vendorsCount) + parseInt(clientsCount)+parseInt(fullTimeCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#Applicants1").html('Applicants ('+fullTimeCount+')');
	$("#draft").html('Draft ('+draftCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    $("#applicantsContractsList").empty();
    $("#applicantsContractsList").append(res); 
    listContractsPagination(SourceLeadWeb_loginOrg);	
    vendorListContractsPagination(SourceLeadWeb_loginOrg);
    appListContractsPagination(SourceLeadWeb_loginOrg);
	
}
</script>

<script>
function listContractsPagination(SourceLeadWeb_loginOrg){
	$('#contractListTableDraft1').DataTable({ 
		dom: 'Bfrtip',
        buttons: [
        	{
                extend: 'excel',
                exportOptions: {
                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                },
                title: SourceLeadWeb_loginOrg +' - Draft',
            },
            {
                extend: 'pdf',
                exportOptions: {
                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                },
                customize: function(doc) {
                    doc.defaultStyle.alignment = 'center';
                  },
                title: SourceLeadWeb_loginOrg +' - Draft',
            }
        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
	$(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableDraft1").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  
}
function vendorListContractsPagination(SourceLeadWeb_loginOrg){ 
    $('#contractListTableDraft').DataTable( { 
    	dom: 'Bfrtip',
        buttons: [
        	{
                extend: 'excel',
                exportOptions: {
                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                },
                title: SourceLeadWeb_loginOrg +' - Draft',
            },
            {
                extend: 'pdf',
                exportOptions: {
                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                },
                customize: function(doc) {
                    doc.defaultStyle.alignment = 'center';
                  },               
                title: SourceLeadWeb_loginOrg +' - Draft',
            }
        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableDraft").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  
}
function appListContractsPagination(SourceLeadWeb_loginOrg){ 
    $('#contractListTableDraft2').DataTable( { 
    	dom: 'Bfrtip',
        buttons: [
        	{
                extend: 'excel',
                exportOptions: {
                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                },
                title: SourceLeadWeb_loginOrg +' - Draft',
            },
            {
                extend: 'pdf',
                exportOptions: {
                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
                },
                customize: function(doc) {
                    doc.defaultStyle.alignment = 'center';
                  },
                title: SourceLeadWeb_loginOrg +' - Draft',
            }
        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
    $("#contractListTableDraft2").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  
}
</script>

<script>
function preparedContractListWFA(data){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#clientContractsList").empty();
    var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  }
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableWFA" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Signing Authority</th>';
		result = result + '    <th>Contact</th>';
		result = result + '    <th>Vendor</th>';
		result = result + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		result = result + '    <th>Req Docs</th>'; 
		result = result + '    <th>Approver</th>';
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		        if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'APPROVE\');"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		        }else{
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" ><span title="'+report.contractTitle+'">'+report.contractTitle+'</span></div>';
		        }
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div class="tbldata-dotdot-vsm"><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
		        	 result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+' | ';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result + report.endDateStr+'</div>';
		         }else{
		        	 result = result + '--</div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.reqDocCount!="0"){
		        	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+report.contractUUID+'\',\''+report.parentId+'\');">'+report.reqDocCount+'</div>'; 
		         }
		         else{
		        	 result = result + '<div class="tblminwidth-75 ">'+report.reqDocCount+'</div>';
		         }	 
		         result = result + '</td>';
		         /* result = result + '<td>';
		         result = result + '<div><span>'+report.contractType+'</span></div>';
		         result = result + '</td>'; */
		         
// 		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.approverUserName != null && report.approverUserName != "" && report.approverUserName != 'undefined'){
		        	 result = result + '<div class="tbldata-dotdot_80"><span title="'+report.approverUserName+'">'+report.approverUserName+'</span></div>';
		         }else{
		        	 result = result + '<div><span>---</span></div>';
		         }
		         
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tbldata-dotdot-vsm">';
	        	  if(report.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
	        		  
	        		  if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == report.approverUserId){
	      	        	 result = result +' <div><div class="CurDiv" id="removeStatus'+report.id+'"><i class="fa fa-check" title="Approve" alt="Approve" onclick="approveContract('+report.id+',\'WAITING_FOR_APPROVER\');"></i>|<i class="fa fa-times" title="Reject" alt="Rejected" onclick="rejectedContractReason('+report.id+',\'REJECTED\');"></i></div></div>';
	      	         }
	      	         '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
	      	     		if(report.typeOfContract == "New"){
	      	     			result = result + '| <a href="<c:url value="/addNewContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
	      	     		}
	      	     		if(report.typeOfContract == "Custom"){
	      	     			result = result + '| <a href="<c:url value="/addCustomContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
	      	     		}
	        			'</sec:authorize>';
	        			'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
	        			result = result + '|<a href="#" title="Preview"><i class="fa fa-eye paddingLeft" onclick="previewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a>';
	        			'</sec:authorize>';
	        			
	        	  }
	        	  
		     		 //result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a></div>';
		     	  
		         result = result + '</div></td>';
		     	 
		        result = result + '</tr>';
		    }
			
	}
	result = result + '	</tbody>';
	result = result + '</table>';
	var opt="";
	opt = opt +  '<table id="contractListTableWFA1" class="display" cellspacing="0" width="100%" >';
	opt = opt + '	<thead>';
	opt = opt + '   <tr>';
	opt = opt + '    <th>Title</th>';
	opt = opt + '    <th>Type</th>';
	opt = opt + '    <th>Signing Authority</th>';
	opt = opt + '    <th>Contact</th>';
	opt = opt + '    <th>Client</th>';
	opt = opt + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
	opt = opt + '     <th>Req Docs</th>'; 
	opt = opt + '    <th>Approver</th>';
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	        if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+output.contractUUID+'\',\'APPROVE\');"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
	        }else{
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" ><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
	        }
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
	         if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	        	 opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	         }
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+' | ';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt +output.endDateStr+'</div>';
	         }else{
	        	 opt = opt + '--</div>';
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
	         
	         /* opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.contractType+'</span></div>';
	         opt = opt + '</td>'; */
	         
// 	         opt = opt + '</td>';

	         opt = opt + '<td>';
	         if(output.approverUserName != null && output.approverUserName != "" && output.approverUserName != 'undefined'){
	        	 opt = opt + '<div><span title="'+output.approverUserName+'">'+output.approverUserName+'</span></div>';
	         }else{
	        	 opt = opt + '<div><span>---</span></div>';
	         }
	         
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';//Waiting to be signed
	         opt = opt + '<div class="tbldata-dotdot-vsm">';
	         
	         
        	  if(output.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
        		  if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == output.approverUserId){
     	        	 opt = opt +' <div><div class="CurDiv" id="removeStatus'+output.id+'"><i class="fa fa-check" title="Approve" alt="Approve" onclick="approveContract('+output.id+',\'WAITING_FOR_APPROVER\');"></i>|<i class="fa fa-times" title="Reject" alt="Rejected" onclick="rejectedContractReason('+output.id+',\'REJECTED\');"></i></div></div>';
     	         }
     	         '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
     	     		if(output.typeOfContract == "New"){
     	     			opt = opt + '| <a href="<c:url value="/addNewContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
     	     		}
     	     		if(output.typeOfContract == "Custom"){
     	     			opt = opt + '| <a href="<c:url value="/addCustomContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
     	     		}
       			'</sec:authorize>';
       			'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
       			opt = opt + '|<a href="#" title="Preview"><i class="fa fa-eye paddingLeft" onclick="previewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a>';
       			'</sec:authorize>';
        	  }
	     	 
	     		opt = opt + '</div></td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	
	var res = '';
	 res = res +  '<table id="contractListTableWTA2" class="display" cellspacing="0" width="100%" >';
		res = res + '	<thead>';
		res = res + '   <tr>';
		res = res + '    <th>Title</th>';
		res = res + '    <th>Type</th>';
		res = res + '    <th>Signing Authority</th>';
		res = res + '    <th>Employee Name</th>';
		res = res + '    <th>Join date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		res = res + '    <th>Rate</th>'; 
		res = res + '    <th>Req Docs</th>'; 
		res = res + '    <th>Approver</th>';
		res = res + '    <th>Action</th>';
		res = res + '   </tr>';
		res = res + '	</thead>';
		res = res + '	<tbody>';
	if(json.fullTimeoffercontractsList.length>0){
		    for (var i = 0; i < json.fullTimeoffercontractsList.length; i++) {
		    	var appReport= json.fullTimeoffercontractsList[i];
		    	res = res + '<tr id="row'+appReport.id+'">';
		        res = res + '<td>';
		        	res = res + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+appReport.contractUUID+'\',\'APPROVE\');"><span title="'+appReport.contractTitle+'">'+appReport.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		         res = res + '</td>';
		         res = res + '<td>';
		           	res = res + '<div class="tblminwidth-75">'+appReport.contractType+'</div>';
		         res = res + '</td>';
		    	 res = res + '<td>';
		       	 res = res + '<div><span title="'+appReport.ownerName+'">'+appReport.ownerName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.empName+'">'+appReport.empName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tblminwidth-75">'+appReport.joinedDateStr+'</div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         if(appReport.billingAmount != null && appReport.billingAmount != '' && appReport.billingAmount != 'undefined'){
		      	   res = res + '<div class="tblminwidth-75">'+appReport.billingAmount+'</div>';
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75">--</div>'; 
		         }
		         res = res + '</td>';
		         /* res = res + '<td>';
		        	 res = res + '<div class="tblminwidth-75">'+appReport.reqDocCount+'</div>';
		         res = res + '</td>'; */
		         
		         res = res + '<td>';
		         if(appReport.reqDocCount!="0"){
		        	 res = res + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+appReport.contractUUID+'\',\''+appReport.parentId+'\');">'+appReport.reqDocCount+'</div>'; 
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75 ">'+appReport.reqDocCount+'</div>';
		         }	 
		         res = res + '</td>';
		         
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.approverUserName+'">'+appReport.approverUserName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tbldata-dotdot_80">';
		     	if(appReport.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
					if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == appReport.approverUserId){
					res = res +' <div><div class="CurDiv" id="removeStatus'+appReport.id+'"><i class="fa fa-check" title="Approve" alt="Approve" onclick="approveContract('+appReport.id+',\'WAITING_FOR_APPROVER\');"></i>|<i class="fa fa-times" title="Reject" alt="Rejected" onclick="rejectedContractReason('+appReport.id+',\'REJECTED\');"></i></div></div>';
					}
		     		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		     		res = res + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+appReport.contractUUID+'\', \''+appReport.contractTitle+'\')"></i></a>';
		     		'</sec:authorize>';
		     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		     		if(appReport.typeOfContract == "New"){
		     			res = res + '| <a href="<c:url value="/addNewContract?contractUUID='+appReport.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
		     		if(appReport.typeOfContract == "Custom"){
		     			res = res + '| <a href="<c:url value="/addCustomContract?contractUUID='+appReport.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
		     		'</sec:authorize>';
		     		//res = res + '</div>';
	     	  }
		         res = res + '</div></td>';
		     	 
		        res = res + '</tr>';
		    }
			
	}
	res = res + '	</tbody>';
	res = res + '</table>';
	
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var fullTimeCount = json.fullTimeoffercontractsList.length;
	var approveCount = parseInt(vendorsCount) + parseInt(clientsCount)+parseInt(fullTimeCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#Applicants1").html('Applicants ('+fullTimeCount+')');
	$("#approve").html('<i class="fa fa-clock-o fa-2"></i>Waiting ('+approveCount+')');
	
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    $("#applicantsContractsList").empty();
    $("#applicantsContractsList").append(res); 
    listContractsPaginationWFA(SourceLeadWeb_loginOrg);	
    vendorListContractsPaginationWFA(SourceLeadWeb_loginOrg);
    appListContractsPaginationWFA(SourceLeadWeb_loginOrg);
	
}
</script>

<script>
function listContractsPaginationWFA(SourceLeadWeb_loginOrg){
	$('#contractListTableWFA1').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                title: SourceLeadWeb_loginOrg +' - Approve',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg +' - Approve',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableWFA1").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  

}
function vendorListContractsPaginationWFA(SourceLeadWeb_loginOrg){ 
    $('#contractListTableWFA').DataTable( { 
    	dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                title: SourceLeadWeb_loginOrg +' - Approve',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg +' - Approve',
	            }
	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableWFA").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  

}

function appListContractsPaginationWFA(SourceLeadWeb_loginOrg){ 
    $('#contractListTableWTA2').DataTable( { 
    	dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                title: SourceLeadWeb_loginOrg +' - Approve',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg +' - Approve',
	            }
	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableWFA2").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  

}
</script>

<script>
function preparedContractListWTS(data){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableWTS" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Signing Authority</th>';
		result = result + '    <th>Contact</th>';
		result = result + '    <th>Vendor</th>';
		result = result + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		result = result + '    <th>Req Docs</th>'; 
		result = result + '    <th>Status</th>';
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		        if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'SIGN\');"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		        }else{
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" ><span title="'+report.contractTitle+'">'+report.contractTitle+'</span></div>';
		        }
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
		        	 result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result +' | '+ report.endDateStr+'</div>';
		         }
		         result = result + '</td>';
		         result = result + '<td>';
		         if(report.reqDocCount!="0"){
		        	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+report.contractUUID+'\',\''+report.parentId+'\');">'+report.reqDocCount+'</div>'; 
		         }
		         else{
		        	 result = result + '<div class="tblminwidth-75 ">'+report.reqDocCount+'</div>';
		         }	 
		         result = result + '</td>';
		         //to be change for Received On
		         
		         /* result = result + '<td>';
		         result = result + '<div><span>'+report.templateSubmissionStatus+'</span></div>';
		         result = result + '</td>'; */
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.templateSubmissionStatus+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';//Waiting to be signed
		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		         result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a></div>';
		         '</sec:authorize>';
		         result = result + '</td>';
		     	 
		        result = result + '</tr>';
		    }
			
	}
	result = result + '	</tbody>';
	result = result + '</table>';
	var opt="";
	opt = opt +  '<table id="contractListTableWTS1" class="display" cellspacing="0" width="100%" >';
	opt = opt + '	<thead>';
	opt = opt + '   <tr>';
	opt = opt + '    <th>Title</th>';
	opt = opt + '    <th>Type</th>';
	opt = opt + '    <th>Signing Authority</th>';
	opt = opt + '    <th>Contact</th>';
	opt = opt + '    <th>Client</th>';
	opt = opt + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
	opt = opt + '    <th>Req Docs</th>';
	opt = opt + '    <th>Status</th>'; 
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	        if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+output.contractUUID+'\',\'SIGN\');"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
	        }else{
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
	        }
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
	         if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	        	 opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	         }
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt +' | '+output.endDateStr+'</div>';
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
	         

	         //to be changed for Received on
	         /* opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.templateSubmissionStatus+'</span></div>';
	         opt = opt + '</td>'; */
	         
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.templateSubmissionStatus+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';//Waiting to be signed
	         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a></div>';
     		'</sec:authorize>';
     		 opt = opt + '</td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	
	var res = '';
	 res = res +  '<table id="contractListTableWTS2" class="display" cellspacing="0" width="100%" >';
		res = res + '	<thead>';
		res = res + '   <tr>';
		res = res + '    <th>Title</th>';
		res = res + '    <th>Type</th>';
		res = res + '    <th>Signing Authority</th>';
		res = res + '    <th>Employee Name</th>';
		res = res + '    <th>Join date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		res = res + '    <th>Rate</th>'; 
		res = res + '    <th>Req Docs</th>'; 
		res = res + '    <th>Status</th>';
		res = res + '    <th>Action</th>';
		res = res + '   </tr>';
		res = res + '	</thead>';
		res = res + '	<tbody>';
	if(json.fullTimeoffercontractsList.length>0){
		    for (var i = 0; i < json.fullTimeoffercontractsList.length; i++) {
		    	var appReport= json.fullTimeoffercontractsList[i];
		    	res = res + '<tr id="row'+appReport.id+'">';
		        res = res + '<td>';
		        	res = res + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+appReport.contractUUID+'\',\'SIGN\');"><span title="'+appReport.contractTitle+'">'+appReport.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		         res = res + '</td>';
		         res = res + '<td>';
		           	res = res + '<div class="tblminwidth-75">'+appReport.contractType+'</div>';
		         res = res + '</td>';
		    	 res = res + '<td>';
		       	 res = res + '<div><span title="'+appReport.ownerName+'">'+appReport.ownerName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.empName+'">'+appReport.empName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tblminwidth-75">'+appReport.joinedDateStr+'</div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         if(appReport.billingAmount != null && appReport.billingAmount != '' && appReport.billingAmount != 'undefined'){
		      	   res = res + '<div class="tblminwidth-75">'+appReport.billingAmount+'</div>';
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75">--</div>'; 
		         }
		         res = res + '</td>';
		         /* res = res + '<td>';
		        	 res = res + '<div class="tblminwidth-75">'+appReport.reqDocCount+'</div>';
		         res = res + '</td>'; */
		         
		         res = res + '<td>';
		         if(appReport.reqDocCount!="0"){
		        	 res = res + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+appReport.contractUUID+'\',\''+appReport.parentId+'\');">'+appReport.reqDocCount+'</div>'; 
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75 ">'+appReport.reqDocCount+'</div>';
		         }	 
		         res = res + '</td>';
		         
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.templateSubmissionStatus+'">'+appReport.templateSubmissionStatus+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';//Waiting to be signed
		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		         res = res + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+appReport.contractUUID+'\', \''+appReport.contractTitle+'\')"></i></a></div>';
		         '</sec:authorize>';
		         res = res + '</td>';
		     	 
		        res = res + '</tr>';
		    }
			
	}
	res = res + '	</tbody>';
	res = res + '</table>';
	
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var fullTimeCount = json.fullTimeoffercontractsList.length;
	var signCount = parseInt(vendorsCount) + parseInt(clientsCount)+parseInt(fullTimeCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#Applicants1").html('Applicants ('+fullTimeCount+')');
	$("#sign").html('<i class="fa fa-clock-o fa-2"></i>Sign ('+signCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    $("#applicantsContractsList").empty();
    $("#applicantsContractsList").append(res);
    listContractsPaginationWTS(SourceLeadWeb_loginOrg);	
    vendorListContractsPaginationWTS(SourceLeadWeb_loginOrg);
    appListContractsPaginationWTS(SourceLeadWeb_loginOrg);
}
</script>

<script>
function listContractsPaginationWTS(SourceLeadWeb_loginOrg){
	$('#contractListTableWTS1').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Sign',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg + ' - Sign',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableWTS1").wrap("<div class='overflow_Auto' style='width:100%;'></div>");  
}
function vendorListContractsPaginationWTS(SourceLeadWeb_loginOrg){ 
    $('#contractListTableWTS').DataTable( { 
    	dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Sign',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg + ' - Sign',
	            }
	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableWTS").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
function appListContractsPaginationWTS(SourceLeadWeb_loginOrg){
	$('#contractListTableWTS2').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Sign',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg + ' - Sign',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableWTS2").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
</script>

<script>
function preparedContractListWTV(data){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    
    var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableWTV" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Signing Authority</th>';
		result = result + '    <th>Contact</th>';
		result = result + '    <th>Vendor</th>';
		result = result + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		result = result + '    <th>Req Docs</th>'; 
		result = result + '    <th>Billing Cycle</th>';
		result = result + '    <th>Status</th>';
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		        if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'VERIFY\');"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		        }else{
		        	result = result + '<div class="tbldata-dotdot-sm proj_show"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span></div>';
		        }
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
		        	 result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
		         }
		         result = result + '</td>';
		         
		         //need to change for signed on
		         /* result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>'; */
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result +' | '+ report.endDateStr+'</div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.reqDocCount!="0"){
		        	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+report.contractUUID+'\',\''+report.parentId+'\');">'+report.reqDocCount+'</div>'; 
		         }
		         else{
		        	 result = result + '<div class="tblminwidth-75 ">'+report.reqDocCount+'</div>';
		         }	 
		         
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.invoiceFrequency != null && report.invoiceFrequency != '' && report.invoiceFrequency != 'undefined'){
			         result = result + '<div><span>'+report.invoiceFrequency+'</span></div>';
		         }else{
		        	 result = result + '<div><span>---</span></div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.templateSubmissionStatus+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';//Waiting to be signed
		         result = result + '<div class="tbldata-dotdot_80">';
 		         if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == report.ownerUser){
		        	 if(report.contractStatus == "completed" && report.templateSubmissionStatus == "WAITING_TO_BE_VERIFY" && report.templateSubmissionStatus != "FINALIZED"){
			     			result = result + '<a id="removeStatus'+report.id+'" class="fa fa-thumbs-o-up" title="finalize" onclick="finalizeContract('+report.id+',\'WAITING_TO_BE_VERIFY\');"></a> | ';
	            	 }
		         }
		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
	     		 result = result + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a>';
	     		'</sec:authorize>';
		         result = result + '</div></td>';
		        result = result + '</tr>';
		    }
			
	}
	result = result + '	</tbody>';
	result = result + '</table>';
	var opt="";
	opt = opt +  '<table id="contractListTableWTV1" class="display" cellspacing="0" width="100%" >';
	opt = opt + '	<thead>';
	opt = opt + '   <tr>';
	opt = opt + '    <th>Title</th>';
	opt = opt + '    <th>Type</th>';
	opt = opt + '    <th>Signing Authority</th>';
	opt = opt + '    <th>Contact</th>';
	opt = opt + '    <th>Client</th>';
	opt = opt + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
	opt = opt + '    <th>Req Docs</th>'; 
	opt = opt + '    <th>Billing Cycle</th>';
	opt = opt + '    <th>Status</th>';
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	        if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+output.contractUUID+'\',\'VERIFY\');"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
	        }else{
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" ><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
	        }
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
	         if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	        	 opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	         }
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt +' | '+output.endDateStr+'</div>';
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
	         if(output.invoiceFrequency != null && output.invoiceFrequency != '' && output.invoiceFrequency != 'undefined'){
	        	 opt = opt + '<div><span>'+output.invoiceFrequency+'</span></div>';
	         }else{
	        	 opt = opt + '<div><span>---</span></div>';
	         }
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.templateSubmissionStatus+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';//Waiting to be signed
	         opt = opt + '<div class="tbldata-dotdot_80">';
 	         if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == output.ownerUser){
	        	 if(output.contractStatus == "completed" && output.templateSubmissionStatus == "WAITING_TO_BE_VERIFY" && output.templateSubmissionStatus != "FINALIZED"){
		     			opt = opt +  '<a id="removeStatus'+output.id+'" class="fa fa-thumbs-o-up" title="finalize" onclick="finalizeContract('+output.id+',\'WAITING_TO_BE_VERIFY\');"></a> | ';
		     		}
 	         }
	         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
     		 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a>';
     		'</sec:authorize>';
     		 opt = opt + '</div></td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	
	var res = '';
	 res = res +  '<table id="contractListTableWTV2" class="display" cellspacing="0" width="100%" >';
		res = res + '	<thead>';
		res = res + '   <tr>';
		res = res + '    <th>Title</th>';
		res = res + '    <th>Type</th>';
		res = res + '    <th>Signing Authority</th>';
		res = res + '    <th>Employee Name</th>';
		res = res + '    <th>Join date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		res = res + '    <th>Rate</th>'; 
		res = res + '    <th>Req Docs</th>'; 
		res = res + '    <th>Status</th>';
		res = res + '    <th>Action</th>';
		res = res + '   </tr>';
		res = res + '	</thead>';
		res = res + '	<tbody>';
	if(json.fullTimeoffercontractsList.length>0){
		    for (var i = 0; i < json.fullTimeoffercontractsList.length; i++) {
		    	var appReport= json.fullTimeoffercontractsList[i];
		    	res = res + '<tr id="row'+appReport.id+'">';
		        res = res + '<td>';
		        	res = res + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+appReport.contractUUID+'\',\'VERIFY\');"><span title="'+appReport.contractTitle+'">'+appReport.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		         res = res + '</td>';
		         res = res + '<td>';
		           	res = res + '<div class="tblminwidth-75">'+appReport.contractType+'</div>';
		         res = res + '</td>';
		    	 res = res + '<td>';
		       	 res = res + '<div><span title="'+appReport.ownerName+'">'+appReport.ownerName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.empName+'">'+appReport.empName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tblminwidth-75">'+appReport.joinedDateStr+'</div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         if(appReport.billingAmount != null && appReport.billingAmount != '' && appReport.billingAmount != 'undefined'){
		      	   res = res + '<div class="tblminwidth-75">'+appReport.billingAmount+'</div>';
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75">--</div>'; 
		         }
		         res = res + '</td>';
		         /* res = res + '<td>';
		        	 res = res + '<div class="tblminwidth-75">'+appReport.reqDocCount+'</div>';
		         res = res + '</td>'; */
		         
		         res = res + '<td>';
		         if(appReport.reqDocCount!="0"){
		        	 res = res + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+appReport.contractUUID+'\',\''+appReport.parentId+'\');">'+appReport.reqDocCount+'</div>'; 
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75 ">'+appReport.reqDocCount+'</div>';
		         }	 
		         res = res + '</td>';
		         
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.templateSubmissionStatus+'">'+appReport.templateSubmissionStatus+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';//Waiting to be signed
		         res = res + '<div class="tbldata-dotdot_80">';
 		         if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == appReport.ownerUser){
		        	 if(appReport.contractStatus == "completed" && appReport.templateSubmissionStatus == "WAITING_TO_BE_VERIFY" && appReport.templateSubmissionStatus != "FINALIZED"){
			        	 res = res +  '<a id="removeStatus'+appReport.id+'" class="fa fa-thumbs-o-up" title="finalize" onclick="finalizeContract('+appReport.id+',\'WAITING_TO_BE_VERIFY\');"></a> | ';
			     		}
		         }
		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		         res = res + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+appReport.contractUUID+'\', \''+appReport.contractTitle+'\')"></i></a>';
		         '</sec:authorize>';
		         res = res + '</div></td>';
		     	 
		        res = res + '</tr>';
		    }
			
	}
	res = res + '	</tbody>';
	res = res + '</table>';
	
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var fullTimeCount = json.fullTimeoffercontractsList.length;
	var verifyCount = parseInt(vendorsCount) + parseInt(clientsCount)+parseInt(fullTimeCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#Applicants1").html('Applicants ('+fullTimeCount+')');
	$("#verify").html('<i class="fa fa-clock-o fa-2"></i>Counter sign ('+verifyCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    $("#applicantsContractsList").empty();
    $("#applicantsContractsList").append(res);
    listContractsPaginationWTV(SourceLeadWeb_loginOrg);	
    vendorListContractsPaginationWTV(SourceLeadWeb_loginOrg);
    applicantsContractsPaginationWTV(SourceLeadWeb_loginOrg);	
	
}
</script>

<script>
function listContractsPaginationWTV(SourceLeadWeb_loginOrg){
	$('#contractListTableWTV1').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                title: SourceLeadWeb_loginOrg+ ' - Verify',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg+ ' - Verify',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableWTV1").wrap("<div class='overflow_Auto'></div>");
}
function vendorListContractsPaginationWTV(SourceLeadWeb_loginOrg){ 
    $('#contractListTableWTV').DataTable( { 
    	dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                title: SourceLeadWeb_loginOrg+ ' - Verify',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg+ ' - Verify',
	            }
	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableWTV").wrap("<div class='overflow_Auto'></div>");
}

function applicantsContractsPaginationWTV(SourceLeadWeb_loginOrg){ 
    $('#contractListTableWTV2').DataTable( { 
    	dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                title: SourceLeadWeb_loginOrg+ ' - Verify',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6, 7, 8 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg+ ' - Verify',
	            }
	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableWTV2").wrap("<div class='overflow_Auto'></div>");
}
</script>

<script>
function preparedContractListFinalize(data){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableFinalize" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Signing Authority</th>';
		result = result + '    <th>Contact</th>';
		result = result + '    <th>Vendor</th>';
		result = result + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		result = result + '    <th>Req Docs</th>'; 
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		        if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'FINALIZE\');"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		        }else{
		        	result = result + '<div class="tbldata-dotdot-sm proj_show"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span></div>';
		        }
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
		        	 result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result +' | '+ report.endDateStr+'</div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.reqDocCount!="0"){
		        	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+report.contractUUID+'\',\''+report.parentId+'\');">'+report.reqDocCount+'</div>'; 
		         }
		         else{
		        	 result = result + '<div class="tblminwidth-75 ">'+report.reqDocCount+'</div>';
		         }	 
		         result = result + '</td>';
		         
		         //need to change for finalized on
		         /* result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>'; */
		         
		         result = result + '<td>';
		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		         result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a></div>';
		         '</sec:authorize>';
		         result = result + '</td>';
		        result = result + '</tr>';
		    }
			
	}
	result = result + '	</tbody>';
	result = result + '</table>';
	var opt="";
	opt = opt +  '<table id="contractListTableFinalize1" class="display" cellspacing="0" width="100%" >';
	opt = opt + '	<thead>';
	opt = opt + '   <tr>';
	opt = opt + '    <th>Title</th>';
	opt = opt + '    <th>Type</th>';
	opt = opt + '    <th>Signing Authority</th>';
	opt = opt + '    <th>Contact</th>';
	opt = opt + '    <th>Client</th>';
	opt = opt + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
	opt = opt + '    <th>Req Docs</th>'; 
// 	opt = opt + '    <th>Finalized On</th>'; 
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	        if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+output.contractUUID+'\',\'FINALIZE\');"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
	        }else{
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
	        }
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
	         if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	        	 opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	         }
	         opt = opt + '</td>';
	         
	        /*  opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>'; */
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt +' | '+output.endDateStr+'</div>';
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
	         
	         
	       //need to change for Finalized on
	         /* opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>'; */

	         opt = opt + '<td>';
	         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a></div>';
     		 '</sec:authorize>';
     		 opt = opt + '</td>';
     		 
	     	 opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	var res = '';
	 res = res +  '<table id="contractListTableFinalize2" class="display" cellspacing="0" width="100%" >';
		res = res + '	<thead>';
		res = res + '   <tr>';
		res = res + '    <th>Title</th>';
		res = res + '    <th>Type</th>';
		res = res + '    <th>Signing Authority</th>';
		res = res + '    <th>Employee Name</th>';
		res = res + '    <th>Join date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		res = res + '    <th>Rate</th>'; 
		res = res + '    <th>Req Docs</th>'; 
		res = res + '    <th>Action</th>';
		res = res + '   </tr>';
		res = res + '	</thead>';
		res = res + '	<tbody>';
	if(json.fullTimeoffercontractsList.length>0){
		    for (var i = 0; i < json.fullTimeoffercontractsList.length; i++) {
		    	var appReport= json.fullTimeoffercontractsList[i];
		    	res = res + '<tr id="row'+appReport.id+'">';
		        res = res + '<td>';
		        	res = res + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+appReport.contractUUID+'\',\'FINALIZE\');"><span title="'+appReport.contractTitle+'">'+appReport.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		         res = res + '</td>';
		         res = res + '<td>';
		           	res = res + '<div class="tblminwidth-75">'+appReport.contractType+'</div>';
		         res = res + '</td>';
		    	 res = res + '<td>';
		       	 res = res + '<div><span title="'+appReport.ownerName+'">'+appReport.ownerName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.empName+'">'+appReport.empName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tblminwidth-75">'+appReport.joinedDateStr+'</div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         if(appReport.billingAmount != null && appReport.billingAmount != '' && appReport.billingAmount != 'undefined'){
		      	   res = res + '<div class="tblminwidth-75">'+appReport.billingAmount+'</div>';
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75">--</div>'; 
		         }
		         res = res + '</td>';
		         /* res = res + '<td>';
		        	 res = res + '<div class="tblminwidth-75">'+appReport.reqDocCount+'</div>';
		         res = res + '</td>'; */
		         
		         res = res + '<td>';
		         if(appReport.reqDocCount!="0"){
		        	 res = res + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+appReport.contractUUID+'\',\''+appReport.parentId+'\');">'+appReport.reqDocCount+'</div>'; 
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75 ">'+appReport.reqDocCount+'</div>';
		         }	 
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		         res = res + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+appReport.contractUUID+'\', \''+appReport.contractTitle+'\')"></i></a></div>';
		         '</sec:authorize>';
		         res = res + '</td>';
		     	 
		        res = res + '</tr>';
		    }
			
	}
	res = res + '	</tbody>';
	res = res + '</table>';
	

	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var fullTimeCount = json.fullTimeoffercontractsList.length;
	var finalisedCount = parseInt(vendorsCount) + parseInt(clientsCount)+parseInt(fullTimeCount);;
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#Applicants1").html('Applicants ('+fullTimeCount+')');
	$("#finalised").html('Completed ('+finalisedCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt);
    $("#applicantsContractsList").empty();
    $("#applicantsContractsList").append(res);
    listContractsPaginationFinalize(SourceLeadWeb_loginOrg);	
    vendorListContractsPaginationFinalize(SourceLeadWeb_loginOrg);
    applicantsContractsPaginationFinalize(SourceLeadWeb_loginOrg);	
	
}
</script>

<script>
function listContractsPaginationFinalize(SourceLeadWeb_loginOrg){
	$('#contractListTableFinalize1').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Finalized',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg + ' - Finalized',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableFinalize1").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
function vendorListContractsPaginationFinalize(SourceLeadWeb_loginOrg){ 
    $('#contractListTableFinalize').DataTable( { 
    	dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Finalized',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  },  
	                title: SourceLeadWeb_loginOrg + ' - Finalized',
	            }
	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableFinalize").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
function applicantsContractsPaginationFinalize(SourceLeadWeb_loginOrg){
	$('#contractListTableFinalize2').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Finalized',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  }, 
	                title: SourceLeadWeb_loginOrg + ' - Finalized',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableFinalize2").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
</script>
<!-- preparedContractListExpired -->
<script>
function preparedContractListExpired(data){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    
    var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableEXP" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Signing Authority</th>';
		result = result + '    <th>Contact</th>';
		result = result + '    <th>Vendor</th>';
		result = result + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		result = result + '    <th>Req Docs</th>';  
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		        if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'EXPIRE\');"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		        }else{
		        	result = result + '<div class="tbldata-dotdot-sm proj_show"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span></div>';
		        }
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
		        	 result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result +' | '+ report.endDateStr+'</div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		        	 result = result + '<div class="tblminwidth-75">'+report.reqDocCount+'</div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tbldata-dotdot_80">';
		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		     		 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a></div>';
	     		 '</sec:authorize>';
			         result = result + '</div></td>';
		     	 
		        result = result + '</tr>';
		    }
			
	}
	result = result + '	</tbody>';
	result = result + '</table>';
	var opt="";
	opt = opt +  '<table id="contractListTableEXP1" class="display" cellspacing="0" width="100%" >';
	opt = opt + '	<thead>';
	opt = opt + '   <tr>';
	opt = opt + '    <th>Title</th>';
	opt = opt + '    <th>Type</th>';
	opt = opt + '    <th>Signing Authority</th>';
	opt = opt + '    <th>Contact</th>';
	opt = opt + '    <th>Client</th>';
	opt = opt + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
	opt = opt + '    <th>Req Docs</th>'; 
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	        if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+output.contractUUID+'\',\'EXPIRE\');"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
	        }else{
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" ><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
	        }
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
	         if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	        	 opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	         }
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt +' | '+output.endDateStr+'</div>';
	         }
	         opt = opt + '</td>';
	         opt = opt + '<td>';
	        	 opt = opt + '<div class="tblminwidth-75">'+output.reqDocCount+'</div>';
	         opt = opt + '</td>';
	         
	         
	         opt = opt + '<td>';
	         
	         opt = opt + '<div class="tbldata-dotdot_80">';
	         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
	     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a></div>';
     		 '</sec:authorize>';
	     		 opt = opt + '</div></td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	
	var res = '';
	 res = res +  '<table id="contractListTableEXP2" class="display" cellspacing="0" width="100%" >';
		res = res + '	<thead>';
		res = res + '   <tr>';
		res = res + '    <th>Title</th>';
		res = res + '    <th>Type</th>';
		res = res + '    <th>Signing Authority</th>';
		res = res + '    <th>Employee Name</th>';
		res = res + '    <th>Join date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		res = res + '    <th>Rate</th>'; 
		res = res + '    <th>Req Docs</th>'; 
		res = res + '    <th>Action</th>';
		res = res + '   </tr>';
		res = res + '	</thead>';
		res = res + '	<tbody>';
	if(json.fullTimeoffercontractsList.length>0){
		    for (var i = 0; i < json.fullTimeoffercontractsList.length; i++) {
		    	var appReport= json.fullTimeoffercontractsList[i];
		    	res = res + '<tr id="row'+appReport.id+'">';
		        res = res + '<td>';
		        	res = res + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+appReport.contractUUID+'\',\'EXPIRE\');"><span title="'+appReport.contractTitle+'">'+appReport.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		         res = res + '</td>';
		         res = res + '<td>';
		           	res = res + '<div class="tblminwidth-75">'+appReport.contractType+'</div>';
		         res = res + '</td>';
		    	 res = res + '<td>';
		       	 res = res + '<div><span title="'+appReport.ownerName+'">'+appReport.ownerName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.empName+'">'+appReport.empName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tblminwidth-75">'+appReport.joinedDateStr+'</div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         if(appReport.billingAmount != null && appReport.billingAmount != '' && appReport.billingAmount != 'undefined'){
		      	   res = res + '<div class="tblminwidth-75">'+appReport.billingAmount+'</div>';
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75">--</div>'; 
		         }
		         res = res + '</td>';
		        
		         res = res + '<td>';
		       	 res = res + '<div class="tblminwidth-75 ">'+appReport.reqDocCount+'</div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tbldata-dotdot_80">';
		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		         res = res + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a>';
		         '</sec:authorize>';
		         res = res + '</div></td>';
		     	 
		        res = res + '</tr>';
		    }
			
	}
	res = res + '	</tbody>';
	res = res + '</table>';
	

	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var fullTimeCount = json.fullTimeoffercontractsList.length;
	var expiredCount = parseInt(vendorsCount) + parseInt(clientsCount) + parseInt(fullTimeCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#Applicants1").html('Applicants ('+fullTimeCount+')');
	$("#expired").html('Expired ('+expiredCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    $("#applicantsContractsList").empty();
    $("#applicantsContractsList").append(res);
    listContractsPaginationEXP(SourceLeadWeb_loginOrg);	
    vendorListContractsPaginationEXP(SourceLeadWeb_loginOrg);
    applicantsContractsPaginationEXP(SourceLeadWeb_loginOrg);
	
}
</script>

<script>
function listContractsPaginationEXP(SourceLeadWeb_loginOrg){
	$('#contractListTableEXP1').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Expired',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  }, 
	                title: SourceLeadWeb_loginOrg + ' - Expired',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableEXP1").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
function vendorListContractsPaginationEXP(SourceLeadWeb_loginOrg){ 
    $('#contractListTableEXP').DataTable( { 
    	dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Expired',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  }, 
	                title: SourceLeadWeb_loginOrg + ' - Expired',
	            }
	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableEXP").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
function applicantsContractsPaginationEXP(SourceLeadWeb_loginOrg){
	$('#contractListTableEXP2').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Expired',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  }, 
	                title: SourceLeadWeb_loginOrg + ' - Expired',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableEXP2").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
</script>

<script>
function preparedContractListRejected(data){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    
    var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableREJ" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Signing Authority</th>';
		result = result + '    <th>Contact</th>';
		result = result + '    <th>Vendor</th>';
		result = result + '    <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		result = result + '    <th>Req Docs</th>'; 
// 		result = result + '    <th>Rejected By</th>'; 
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		        if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'REJECTED\');"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		        }else{
		        	result = result + '<div class="tbldata-dotdot-sm proj_show"><span title="'+report.contractTitle+'">'+report.contractTitle+'</span></div>';
		        }
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
		        	 result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result +' | '+ report.endDateStr+'</div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		        	 result = result + '<div class="tblminwidth-75">'+report.reqDocCount+'</div>';
		         result = result + '</td>';
		         
		         //need to be changed for Rejected by
		         /* result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>'; */
		         
		         result = result + '<td>';
		         result = result + '<div class="tbldata-dotdot_80">';
		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		     		result = result + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a>';
	     		 '</sec:authorize>';
	     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		     		if(report.typeOfContract == "New"){
		     			result = result + '| <a href="<c:url value="/addNewContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
		     		if(report.typeOfContract == "Custom"){
		     			result = result + '| <a href="<c:url value="/addCustomContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
     			'</sec:authorize>';
		         result = result + '</div></td>';
		     	 
		        result = result + '</tr>';
		    }
			
	}
	result = result + '	</tbody>';
	result = result + '</table>';
	var opt="";
	opt = opt +  '<table id="contractListTableREJ1" class="display" cellspacing="0" width="100%" >';
	opt = opt + '	<thead>';
	opt = opt + '   <tr>';
	opt = opt + '    <th>Title</th>';
	opt = opt + '    <th>Type</th>';
	opt = opt + '    <th>Signing Authority</th>';
	opt = opt + '    <th>Contact</th>';
	opt = opt + '    <th>Client</th>';
	opt = opt + '   <th>Start | End date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
	opt = opt + '    <th>Req Docs</th>';
// 	opt = opt + '    <th>Rejected By</th>'; 
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	        if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+output.contractUUID+'\',\'REJECTED\');"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
	        }else{
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
	        }
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
	         if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	        	 opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	         }
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt +' | '+output.endDateStr+'</div>';
	         }
	         opt = opt + '</td>';
	         opt = opt + '<td>';
	        	 opt = opt + '<div class="tblminwidth-75">'+output.reqDocCount+'</div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         // need to be changed for Rejected By
	         /* opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>'; */
	         
	         opt = opt + '<div class="tbldata-dotdot_80">';
	         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
	     		 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a>';
     		 '</sec:authorize>';
	     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		     		if(output.typeOfContract == "New"){
		     			opt = opt + '| <a href="<c:url value="/addNewContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
		     		if(output.typeOfContract == "Custom"){
		     			opt = opt + '| <a href="<c:url value="/addCustomContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		}
 				'</sec:authorize>';
	     		opt = opt + '</div>';
	     		 opt = opt + '</td>';
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	
	var res = '';
	 res = res +  '<table id="contractListTableREJ2" class="display" cellspacing="0" width="100%" >';
		res = res + '	<thead>';
		res = res + '   <tr>';
		res = res + '    <th>Title</th>';
		res = res + '    <th>Type</th>';
		res = res + '    <th>Signing Authority</th>';
		res = res + '    <th>Employee Name</th>';
		res = res + '    <th>Join date <br>(<small class="custom-small">'+locationDate+'</small>)</th>';
		res = res + '    <th>Rate</th>'; 
		res = res + '    <th>Req Docs</th>'; 
		res = res + '    <th>Action</th>';
		res = res + '   </tr>';
		res = res + '	</thead>';
		res = res + '	<tbody>';
	if(json.fullTimeoffercontractsList.length>0){
		    for (var i = 0; i < json.fullTimeoffercontractsList.length; i++) {
		    	var appReport= json.fullTimeoffercontractsList[i];
		    	res = res + '<tr id="row'+appReport.id+'">';
		        res = res + '<td>';
		        	res = res + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+appReport.contractUUID+'\',\'REJECTED\');"><span title="'+appReport.contractTitle+'">'+appReport.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		         res = res + '</td>';
		         res = res + '<td>';
		           	res = res + '<div class="tblminwidth-75">'+appReport.contractType+'</div>';
		         res = res + '</td>';
		    	 res = res + '<td>';
		       	 res = res + '<div><span title="'+appReport.ownerName+'">'+appReport.ownerName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div><span title="'+appReport.empName+'">'+appReport.empName+'</span></div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tblminwidth-75">'+appReport.joinedDateStr+'</div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         if(appReport.billingAmount != null && appReport.billingAmount != '' && appReport.billingAmount != 'undefined'){
		      	   res = res + '<div class="tblminwidth-75">'+appReport.billingAmount+'</div>';
		         }
		         else{
		        	 res = res + '<div class="tblminwidth-75">--</div>'; 
		         }
		         res = res + '</td>';
		        
		         res = res + '<td>';
		       	 res = res + '<div class="tblminwidth-75 ">'+appReport.reqDocCount+'</div>';
		         res = res + '</td>';
		         
		         res = res + '<td>';
		         res = res + '<div class="tbldata-dotdot_80">';
		     		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		     		 res = res + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+appReport.contractUUID+'\', \''+appReport.contractTitle+'\')"></i></a>';
		     		'</sec:authorize>';
		     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
			     		if(appReport.typeOfContract == "New"){
			     			res = res + '| <a href="<c:url value="/addNewContract?contractUUID='+appReport.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
			     		}
			     		if(appReport.typeOfContract == "Custom"){
			     			res = res + '| <a href="<c:url value="/addCustomContract?contractUUID='+appReport.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
			     		}
	 				'</sec:authorize>';
		     	 res = res + '</div>';
		     	res = res + '</td>';
		     	 
		        res = res + '</tr>';
		    }
			
	}
	res = res + '	</tbody>';
	res = res + '</table>';
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var fullTimeCount = json.fullTimeoffercontractsList.length;
	var rejectedCount = parseInt(vendorsCount) + parseInt(clientsCount)+ parseInt(fullTimeCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#Applicants1").html('Applicants ('+fullTimeCount+')');
	$("#rejected").html('Rejected ('+rejectedCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    $("#applicantsContractsList").empty();
    $("#applicantsContractsList").append(res);
    listContractsPaginationREJ(SourceLeadWeb_loginOrg);	
    vendorListContractsPaginationREJ(SourceLeadWeb_loginOrg);
    applicantsContractsPaginationREJ(SourceLeadWeb_loginOrg);
	
}
</script>

<script>
function listContractsPaginationREJ(SourceLeadWeb_loginOrg){
	$('#contractListTableREJ1').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Rejected',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  }, 
	                title: SourceLeadWeb_loginOrg + ' - Rejected',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableREJ1").wrap("<div class='overflow_Auto' style='width:100%;'></div>"); 
}
function vendorListContractsPaginationREJ(SourceLeadWeb_loginOrg){ 
    $('#contractListTableREJ').DataTable( { 
    	dom: 'Bfrtip', 
    	buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Rejected',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  }, 
	                title: SourceLeadWeb_loginOrg + ' - Rejected',
	            }
	        ],
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableREJ").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
function applicantsContractsPaginationREJ(SourceLeadWeb_loginOrg){
	$('#contractListTableREJ2').DataTable({ 
		dom: 'Bfrtip', 
		buttons: [
	        	{
	                extend: 'excel',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                title: SourceLeadWeb_loginOrg + ' - Rejected',
	            },
	            {
	                extend: 'pdf',
	                exportOptions: {
	                    columns: [ 0, 1, 2, 3, 4, 5, 6 ]
	                },
	                customize: function(doc) {
	                    doc.defaultStyle.alignment = 'center';
	                  }, 
	                title: SourceLeadWeb_loginOrg + ' - Rejected',
	            }
	        ],
		"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
	$("#contractListTableREJ2").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>

<script type="text/javascript">
function previewContractPDF(uuid, templateName) {
	var sourceleadUrl ='${sourceleadUrl}';
	window.open(sourceleadUrl+'/viewContractPdf/'+uuid+'/'+templateName+'','_newtab');
}</script>

<script>
function removeRecord(recordId,status){
	var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/updateContractStatus?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,        
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#removeStatus"+recordId).html("<span>Send For Verification</span>").addClass('text-primary');
       },
   }); 
}


function finalizeContract(recordId,status){
	 var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/updateContractStatus?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,       
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#removeStatus"+recordId).html("<span>Finalized</span>").addClass('text-success');
	        getTemplateList();
       },
   });  
}
</script>


<script>
function approveContract(recordId,status){
	var rejectReason =  $("#rejectContractId").val();
	var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/approveContract?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status+"&rejectReason="+rejectReason,       
        success :function(data) {
        	if(data == 'REJECTED'){
        		$("#row"+recordId).empty();
        		$("#popUp").remove();
    	    	$("#row"+recordId).html(rowHtmlContent);
    	    	 $("#removeStatus"+recordId).html("");
    	        $("#removeReject"+recordId).html("<span>Rejected</span>").addClass('text-success');
        	}else{
        		$("#row"+recordId).empty();
    	    	$("#row"+recordId).html(rowHtmlContent);
    	    	 $("#removeReject"+recordId).html("");
    	        $("#removeStatus"+recordId).html("<span>Approved</span>").addClass('text-success');
        	}
        	$("#loading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        	getTemplateList();
       },
   });  
}
</script>

<script>
function rejectedContractReason(recordId,status){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Do you want to reject this contract?</p>";
	result = result +"		<textarea  id='rejectContractId' name='rejectContractId'  placeholder='Reason here'></textarea>";
	result = result + ' <div id="rejectContractIdDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='validateReason("+recordId+",\""+status+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function validateReason(recordId,status){
	
	var rejectReason =  $("#rejectContractId").val();
	if(rejectReason != null && rejectReason !='undefined' && rejectReason !=''){
		$("#rejectContractIdDiv").find('p.jquery_form_error_message').remove();
		approveContract(recordId,status);
		cancelTermination();
	}else{
		$("#rejectContractIdDiv").append("<p class='jquery_form_error_message'>Reason Is Required</p>");
	}
}
</script>

<script>
function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}
</script>

<script>
function tabEnable(local){
	$('.tabActivation').each(function() {
		 var localId = this.id;
		 $("#"+localId).removeClass("active");
    });
	var tabId = local.id;
	$("#tabType").val(tabId);
	$("#"+tabId).addClass("active");
}

</script>

<script>
function mainTabEnable(local){
	var arrMarkChk = document.getElementsByName("contractType");
	   for (var i = 0; i < arrMarkChk.length; i++) {
		   arrMarkChk[i].checked = false;
	   }
	   
	   $('#signedDiv').find('input[type=checkbox]:checked').removeAttr('checked');
	   $('#verifyDiv').find('input[type=checkbox]:checked').removeAttr('checked');
	var tabId = local.id;
    if(tabId == 'draft'){
	$("#draft").addClass("draft"); 
    $("#approve").removeClass("wApp");
    $("#sign").removeClass("wSig");
    $("#verify").removeClass("wVer");
    $("#finalised").removeClass("fin");
    $("#expired").removeClass("exp");
    $("#rejected").removeClass("rej");
    
    $("#contractStage").val("DRAFT");
    }
    if(tabId == 'approve'){
    	$("#draft").removeClass("draft"); 
        $("#approve").addClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        
        $("#contractStage").val("WAITING_FOR_APPROVER");
        }
    if(tabId == 'sign'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").addClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        
        $("#contractStage").val("WAITING_TO_BE_SIGNED");
        }
    if(tabId == 'verify'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").addClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        
        $("#contractStage").val("WAITING_TO_BE_VERIFY");
        }
    if(tabId == 'finalised'){  
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").addClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        
        $("#contractStage").val("Finalized");
        }
    if(tabId == 'expired'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").addClass("exp");
        $("#rejected").removeClass("rej");
        
        $("#contractStage").val("EXPIRED");
        }
    if(tabId == 'rejected'){
    	$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").addClass("rej");
        
        $("#contractStage").val("REJECTED");
        }
	
}

</script>


<script>
function getStageCounts(){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
      type : "POST",
       url: sourceleadUrl+"/getContractStageCounts?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,       
       success :function(data) {
    	   var json = JSON.parse(data);
    	   
    	   if(json.totalDraftCount != null && json.totalDraftCount != "" && json.totalDraftCount != 'undefined'){
    		   $("#draft").html('Draft ('+json.totalDraftCount+')');
    	   }
    	   if(json.totalWTACount != null && json.totalWTACount != "" && json.totalWTACount != 'undefined'){
    		   $("#approve").html('Waiting ('+json.totalWTACount+')');
    	   }
    	   if(json.totalWTSCount != null && json.totalWTSCount != "" && json.totalWTSCount != 'undefined'){
    		   $("#sign").html('Sign ('+json.totalWTSCount+')');
    	   }
    	   if(json.totalWTVCount != null && json.totalWTVCount != "" && json.totalWTVCount != 'undefined'){
    		   $("#verify").html('Counter sign ('+json.totalWTVCount+')');
    	   }
    	   if(json.totalFinalizedCount != null && json.totalFinalizedCount != "" && json.totalFinalizedCount != 'undefined'){
    		   $("#finalised").html('Completed ('+json.totalFinalizedCount+')');
    	   }
    	   if(json.totalExpiredCount != null && json.totalExpiredCount != "" && json.totalExpiredCount != 'undefined'){
    		   $("#expired").html('Expired ('+json.totalExpiredCount+')');
    	   }
    	   if(json.totalRejectedCount != null && json.totalRejectedCount != "" && json.totalRejectedCount != 'undefined'){
    		   $("#rejected").html('Rejected ('+json.totalRejectedCount+')');
    	   }
      },
  });  
}
</script>


<script>
function Check(val){
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 if(val=="Vendors"){
			 document.getElementById("clientContractsList").style.display="none";
			 document.getElementById("applicantsContractsList").style.display="none"; 
			 document.getElementById("vendorContractsList").style.display="block";
		 }
		 if(val=="Clients"){
			 document.getElementById("vendorContractsList").style.display="none";
			 document.getElementById("applicantsContractsList").style.display="none"; 
			 document.getElementById("clientContractsList").style.display="block";
		 }
		 if(val=="Applicants"){
			 document.getElementById("clientContractsList").style.display="none";
			 document.getElementById("vendorContractsList").style.display="none";
			 document.getElementById("applicantsContractsList").style.display="block"; 
		 }
	 $("#loading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
}

</script>
<script>
function rejectContract(recordId,status){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Document delete message here</p>";
	result = result +"		<textarea  id='terminateReason' name='terminateReason'  placeholder='Comments here'></textarea>";
	result = result + ' <div id="terminateReasonDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' ><input class='u_can_color' type='button'  value='Cancel' ></b>";
	result = result +"	</div>";
	$("#deleteDocpopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function CountClick(contractUUID,parentId){
	$(".mask_time").fadeIn(200);
	$(".DocAccPopup").fadeIn(100);	
	var sourceleadUrl ='${sourceleadUrl}';
	$("#DocAccPopupDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getDocsByContractUUID?contractUUID="+contractUUID,       
       success :function(data) {
    	   $("#DocAccPopupDiv").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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

				  result = result +'	       <label class="col-md-4 text-center">'+opt.fileName+'</a></label>                                                 ';

				  /* if(opt.docFile != null && opt.docFile !="" && parentId == "--"){
				  result = result +'	       <div class="col-md-8 text-center">                                                                                                             ';
				  result = result +'	       <label class="text-center">--</label>                                                 ';
				  result = result +'	       </div>			                                                                                                                  ';
				  $("#saveAction").empty(); 
			  }
			  else{ 
				   if(parentId != "--"){ */
						  if(opt.docFile != null && opt.docFile !=""){
							  result = result +'	       <div class="col-md-8 text-center" id="removeDiv'+opt.id+'">                                                                                                             ';
							  result = result+'	                      &nbsp;&nbsp;<span id="delete_resume" onclick="removeResumeNameDiv('+opt.id+');"><i class="fa fa-trash text-danger removeIcon"></i></span>';
							  result = result+' <a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadContractDocument?contractUUID='+contractUUID+'\"title="Download"></a>';
			         		  result = result+' <a class="fa fa-eye" href="javascript:void(0)" onClick="previewContractDoc(\''+opt.id+'\',\''+opt.fileName+'\');"title="preview"></a>';
							  result = result +'	       </div>			                                                                                                                  ';
							  result = result +'	       <div class="col-md-8" id="uploadDocumentDiv'+opt.id+'" style="display: none;">                                                                                                             ';
							  result = result +'				<div class="input-group positionRelative ratetype" id="uploadFileGroup'+opt.id+'" title="Document Type: .pdf format only">                  ';
							  result = result +'					<label class="input-group-btn"> <span class="btn btn-primary padSm"><i class="fa fa-folder-open-o"></i>&nbsp;Browse           ';
							  result = result +'	             <input name="uploadBtn'+opt.id+'" id="uploadBtn'+opt.id+'" style="display: none;" multiple="" class="upload" type="file" onclick="check('+opt.id+');">                            ';
							  result = result +'	             </span> </label>                                                                                                                 ';
							  result = result +'					<input class="form-control padSm"  id="uploadFile'+opt.id+'" name="docFile'+opt.id+'"  placeholder="Upload File" readonly type="text">  ';
							  result = result +'				</div>                                                                                                                            ';
							  result = result +'	       </div>			                                                                                                                  ';
						  }
						  else{
							  result = result +'	       <div class="col-md-8" id="uploadDocumentDiv'+opt.id+'">                                                                                                             ';
							  result = result +'				<div class="input-group positionRelative ratetype" id="uploadFileGroup'+opt.id+'" title="Document Type: .pdf format only">                  ';
							  result = result +'					<label class="input-group-btn"> <span class="btn btn-primary padSm"><i class="fa fa-folder-open-o"></i>&nbsp;Browse           ';
							  result = result +'	             <input name="uploadBtn'+opt.id+'" id="uploadBtn'+opt.id+'" style="display: none;" multiple="" class="upload" type="file" onclick="check('+opt.id+');">                            ';
							  result = result +'	             </span> </label>                                                                                                                 ';
							  result = result +'					<input class="form-control padSm"  id="uploadFile'+opt.id+'" name="docFile'+opt.id+'"  placeholder="Upload File" readonly type="text">  ';
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
			  result = result +'	 </div>                                                                                                                                   ';
			  result = result + ' <input type="hidden" id="docId'+(i+1)+'" name="docId'+(i+1)+'" value="'+opt.id+'" />';
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
</script>
<script type="text/javascript">
function previewContractDoc(docId,fileName){
	  var sourceleadUrl ='${sourceleadUrl}';
	if(docId != null && docId != ""){
		window.open(sourceleadUrl+'/viewReqContractDocPdf/'+docId+'/'+fileName+'',fileName,"height=600,width=600");
	}  
}
</script>
<script>
function editOne(startDate,endDate){
	$('.editNewDat').show();
	$('#endDate').datepicker({
        dateFormat: 'dd-mm-yy',
        changeMonth: true,
          changeYear: true,
          minDate: startDate,
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
	var startDate = $("#ExportStartDate").val();
	var endDate = $("#endDate").val();
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
    type : "POST",
     url: sourceleadUrl+"/updateContractEndDate?${_csrf.parameterName}=${_csrf.token}&contractId="+contractId+"&endDate="+endDate,       
     success :function(data) {
    	 $("#endVal").empty();
		  $("#endVal").append(": "+endDate);
     	 //   $("#tr"+id).empty();
    },
});  
	
}
</script>

<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->
