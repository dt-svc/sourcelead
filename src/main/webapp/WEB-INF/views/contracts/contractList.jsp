<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- <script src="resources/js/chosen.jquery.min.js"></script> --> 


<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<!-- Content Area -->
<!-- Content Area -->

<script type="text/javascript">
$(document).ready(function(){
   $("#contractStage").val("DRAFT");
   getTemplateList();
});
</script>

<style>
.CurDiv { position: static !important; float: left;}

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
<li title="Waiting for Approval"><a href="#" id="approve"  onClick="mainTabEnable(this);getTemplateList();"><i class="fa fa-clock-o fa-2"></i>Approve (${contractsCount.totalWTACount})</a></li> 
<li title="Waiting to be sign"><a href="#" id="sign" onClick="mainTabEnable(this);getTemplateList();"><i class="fa fa-clock-o fa-2"></i>Sign (${contractsCount.totalWTSCount})</a></li>
<li title="Waiting for Verify"><a href="#" id="verify" onClick="mainTabEnable(this);getTemplateList();"><i class="fa fa-clock-o fa-2"></i>Verify (${contractsCount.totalWTVCount})</a></li>
<li title="Finalised"><a href="#" id="finalised"  onClick="mainTabEnable(this); getTemplateList();">Finalized (${contractsCount.totalFinalizedCount})</a></li>
<li title="Expired"><a href="#" id="expired" onClick="mainTabEnable(this);">Expired (${contractsCount.totalExpiredCount})</a></li>
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
        <div class="small-widget bgGreen"> <a href="<c:url value="/addNewContract" />" title="Import Candidate">
          <div class="clearfix mb10"> <i class="fa fa-file-o fa-4x"></i> <span class="fa fa-plus"></span> </div>
          <strong class="clearfix">New Contract</strong> </a> </div>
      </div>
 	</sec:authorize>
	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')">
        <div class="col-md-4 mt25">
          <div class="small-widget bg-yellow"> <a href="<c:url value="/addExistingContract" />" title="Add Candidate">
            <div class="clearfix mb10"><i class="fa fa-file-code-o fa-4x"></i> <span class="fa fa-plus"></span> </div>
            <strong class="clearfix">Custom Contract</strong> </a> </div>
        </div>
        </sec:authorize>
	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')">
        <div class="col-md-4 mt25">
          <div class="small-widget bg-purple"> <a href="<c:url value="/addExistingContract" />" title="Add Candidate">
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


	
<div class="content-area content-new clearfix">
 <div class="project_wrap">
	<div class="timesheet_head">
		<div class="ContrBut">
		    <input type="hidden" name="tabType" id="tabType" value='Vendor'>
			<!-- <a href="#" id="Vendor" class="active tabActivation" onclick="tabEnable(this);getTemplateList('pending');">Vendors</a>
			<a href="#" id="Client" class="tabActivation" onclick="tabEnable(this);getTemplateList('pending');">Clients</a> -->
			<a href="#" id="Vendor1" class="active tabActivation" onclick="tabEnable(this);Check('Vendors');">Vendors (${contractsCount.totalDraftVendorCount})</a>
			<a href="#" id="Client1" class="tabActivation" onclick="tabEnable(this);Check('Clients');">Clients (${contractsCount.totalDraftClientCount})</a>
		</div>
		<ul>
			<li id="filterContractTypePop"><i class="fa fa-filter"></i><span>Type</span><i class="glyphicon glyphicon-play"></i>
	           	<div class="statusFiler-1 approved_pop">
					<ul class="list-unstyled">
						<li><input id="c1" type="checkbox" name="contractType" value="MSA" onclick="getTemplateList();"><label for="c1">MSA</label></li>
						<li><input id="c2" type="checkbox" name="contractType" value="SOW" onclick="getTemplateList();"><label for="c2">SOW</label></li>
						<li><input id="c3" type="checkbox" name="contractType" value="NDA" onclick="getTemplateList();"><label for="c3">NDA</label></li>
						<li><input id="c4" type="checkbox" name="contractType" value="NCA" onclick="getTemplateList();"><label for="c4">NCA</label></li>
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
			<li id="export-pop"><i class="glyphicon glyphicon-export"></i>
				<div class="statusFiler-2">
					<ul class="list-unstyled">
						<li>PDF<i class="fa fa-file-pdf-o"></i></li>
						<li>Excel<i class="fa fa-file-excel-o"></i></li>
						<li>Word<i class="fa fa-file-word-o"></i></li>
					</ul>
				</div>
			</li>	
			<div class="clearfix"></div>
		</ul>
		<div class="clearfix"></div>
            
	</div>
	<div id="loading"></div>
    <div class="clients_wrap_table">
    <div id="vendorContractsList"  class=" overflow-data" ></div>
     <div id="clientContractsList"  class=" overflow-data" style="display: none"></div>
	</div>
	
</div>
  
</div>
</section>

<div class="mask_time"></div> 
<section class="contPopup">
	<div class="contPopupHead clearfix">
    	<h4>Contract Details Contract Details Contract Details Contract Details Contract Details Contract Details</h4>
    	<span>
    		<i class="fa fa-pencil"></i><i class="fa fa-times" onclick="contPopClose();"></i>
    	</span>
    </div>
	
	<div class="modal-body clearfix">
		<div class="col-xs-12 col-md-12 col-lg-12 noPad clearfix mt10">
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>
			<div class="col-md-6 col-sm-6 noPad clearfix mb">
				<div class="col-md-4 col-sm-4">Heading</div>
				<div class="col-md-8 col-sm-8">: Deatils deatils</div>
			</div>			
		</div>
		<div class="col-xs-12 col-md-12 col-lg-12 noPad clearfix mb">
			<div class="col-md-2 col-sm-2">Descrption</div>
			<div class="col-md-10 col-sm-10">: Descrption Descrption Descrption Descrption DescrptionDescrption 
			Descrption DescrptionDescrption DescrptionDescrption Descrption Descrption Descrption Descrption
			DescrptionDescrption Descrption DescrptionDescrption DescrptionDescrption </div>		
		</div>
	</div>    
</section>

<script>
function PopUp(){
	$(".mask_time").fadeIn(200);
	$(".contPopup").fadeIn(100);
}
function contPopClose(){
	$(".mask_time").fadeOut(200);
	$(".contPopup").fadeOut(100);
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
	}
	if(contractSubmissionType == 'WAITING_TO_BE_VERIFY'){
		$("#filterContractTypePop-2").css("display", "block");
		$("#signedDiv").css("display", "none");
		$("#verifyDiv").css("display", "block");
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
	 $.ajax({
      type : "GET",
       url: sourceleadUrl+"/getAllContractsByOrgLocationIdandStatus?${_csrf.parameterName}=${_csrf.token}&contractSubmissionType="+contractSubmissionType+"&tabType="+tabType+"&contractsType="+contractsType+"&contractStatus="+contractStatus,       
       success :function(data) {
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
    	   
      },
  }); 
}
</script>

<script>
function preparedContractListDraft(data){
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableDraft" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Owner</th>';
		result = result + '    <th>Vendor Contact</th>';
		result = result + '    <th>Start date</th>';
		result = result + '    <th>End date</th>'; 
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		           	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp();"><span>'+report.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span>'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>';
		         result = result + '<td>';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result + '<div class="tblminwidth-75">'+report.endDateStr+'</div>';
		         }else{
		        	 result = result + '<div class="tblminwidth-75">--</div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tbldata-dotdot_80">';
		     	if(report.templateSubmissionStatus == "DRAFT"){
		     		result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a>';
		     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		     			result = result + '| <a href="<c:url value="/addNewContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		'</sec:authorize>';
		     		result = result + '</div>';
	     	  }
	     	  else{
	     		 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a></div>';
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
	opt = opt + '    <th>Owner</th>';
	opt = opt + '    <th>Client Contact</th>';
	opt = opt + '    <th>Start date</th>';
	opt = opt + '    <th>End date</th>'; 
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	       		 opt = opt + '<div class="tbldata-dotdot-sm"><span>'+output.contractTitle+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
       			 opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
       		 opt = opt + '</td>';
	    	 opt = opt + '<td>';
	       	 opt = opt + '<div><span>'+output.ownerName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt + '<div class="tblminwidth-75">'+output.endDateStr+'</div>';
	         }else{
	        	 opt = opt + '<div class="tblminwidth-75">--</div>';
	         }
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tbldata-dotdot_80">';
	     	  if(output.templateSubmissionStatus == "DRAFT" ){
	     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a>';
	     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
	     			opt = opt + '| <a href="<c:url value="/addNewContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
	     		'</sec:authorize>';
	     		opt = opt + '</div>';
	     	  }
	     	  else{
	     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a></div>';
	     	  }
	     		opt = opt + '</div></td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var draftCount = parseInt(vendorsCount) + parseInt(clientsCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#draft").html('Draft ('+draftCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    listContractsPagination();	
    vendorListContractsPagination();
	
}
</script>

<script>
function listContractsPagination(){
	$('#contractListTableDraft1').DataTable({ 
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
}
function vendorListContractsPagination(){ 
    $('#contractListTableDraft').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<script>
function preparedContractListWFA(data){
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableWFA" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Owner</th>';
		result = result + '    <th>Vendor Contact</th>';
		result = result + '    <th>Start date</th>';
		result = result + '    <th>End date</th>'; 
// 		result = result + '    <th>Contract Type</th>';
		result = result + '    <th>Approver</th>';
		result = result + '    <th>Action</th>';
// 		result = result + '    <th>Doc View</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		           	result = result + '<div class="tbldata-dotdot-sm"><span>'+report.contractTitle+'</span></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span>'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>';
		         result = result + '<td>';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result + '<div class="tblminwidth-75">'+report.endDateStr+'</div>';
		         }else{
		        	 result = result + '<div class="tblminwidth-75">--</div>';
		         } 
		         result = result + '</td>';
		         /* result = result + '<td>';
		         result = result + '<div><span>'+report.contractType+'</span></div>';
		         result = result + '</td>'; */
		         
// 		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.approverUserName != null && report.approverUserName != "" && report.approverUserName != 'undefined'){
		        	 result = result + '<div><span>'+report.approverUserName+'</span></div>';
		         }else{
		        	 result = result + '<div><span>---</span></div>';
		         }
		         
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tbldata-dotdot_80">';
	        	  if(report.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
	        		 '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_CONTRACT\')">';
	            		//result = result + '<div class="CurDiv" id="removeStatus'+report.id+'"><a class="btn btn-sm btn-success pull-left" title="approve" onclick="approveContract('+report.id+',\'WAITING_FOR_APPROVER\');">Approve</a></div><div class="CurDiv" id="removeReject'+report.id+'"><a class="btn btn-sm btn-danger" title="REJECTED" onclick="approveContract('+report.id+',\'REJECTED\');">Reject</a></div>'; 
	            	  result = result +' <div><div class="CurDiv" id="removeStatus'+report.id+'"><i class="fa fa-check" alt="Approve" onclick="approveContract('+report.id+',\'WAITING_FOR_APPROVER\');"></i>|<i class="fa fa-times" alt="Rejected" onclick="approveContract('+report.id+',\'REJECTED\');"></i></div></div>';
	            		'</sec:authorize>';
	        	  }
	        	  else{
	         	 	result = result + '<div><span>--</span></div>';
	        	  }
	        	  
		     		 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a></div>';
		     	  
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
	opt = opt + '    <th>Owner</th>';
	opt = opt + '    <th>Client Contact</th>';
	opt = opt + '    <th>Start date</th>';
	opt = opt + '    <th>End date</th>'; 
// 	opt = opt + '    <th>Contract Type</th>';
	opt = opt + '    <th>Approver</th>';
	opt = opt + '    <th>Action</th>';
// 	opt = opt + '    <th>Doc View</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	       		 opt = opt + '<div class="tbldata-dotdot-sm"><span>'+output.contractTitle+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
       			 opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
       		 opt = opt + '</td>';
	    	 opt = opt + '<td>';
	       	 opt = opt + '<div><span>'+output.ownerName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt + '<div class="tblminwidth-75">'+output.endDateStr+'</div>';
	         }else{
	        	 opt = opt + '<div class="tblminwidth-75">--</div>';
	         } 
	         result = result + '</td>';
	         /* opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.contractType+'</span></div>';
	         opt = opt + '</td>'; */
	         
// 	         opt = opt + '</td>';

	         opt = opt + '<td>';
	         if(output.approverUserName != null && output.approverUserName != "" && output.approverUserName != 'undefined'){
	        	 opt = opt + '<div><span>'+output.approverUserName+'</span></div>';
	         }else{
	        	 opt = opt + '<div><span>---</span></div>';
	         }
	         
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';//Waiting to be signed
	         opt = opt + '<div class="tbldata-dotdot_80">';
	         
        	  if(output.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
        		   '<sec:authorize access="hasAnyRole(\'ROLE_APPROVE_CONTRACT\')">';
            		//opt = opt + '<div><div class="CurDiv" id="removeStatus'+output.id+'"><a class="btn btn-sm btn-success pull-left" title="approve" onclick="approveContract('+output.id+',\'WAITING_FOR_APPROVER\');">Approve</a></div><div class="CurDiv" id="removeReject'+output.id+'"><a class="btn btn-sm btn-danger ml" title="REJECTED" onclick="approveContract('+output.id+',\'REJECTED\');">Reject</a></div></div>'; 
            		opt = opt +' <div><div class="CurDiv" id="removeStatus'+output.id+'"><i class="fa fa-check" alt="Approve" onclick="approveContract('+output.id+',\'WAITING_FOR_APPROVER\');"></i>|<i class="fa fa-times" alt="Rejected" onclick="approveContract('+output.id+',\'REJECTED\');"></i></div></div>';
            		'</sec:authorize>';
        	  }
        	  else{
         	 	opt = opt + '<div><span>--</span></div>';
        	  }
        	  opt = opt + '<div class="tbldata-dotdot_80">';
	     	 
	     		 opt = opt + '<div><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a></div>';
	     	 
	     		opt = opt + '</div></td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var approveCount = parseInt(vendorsCount) + parseInt(clientsCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#approve").html('<i class="fa fa-clock-o fa-2"></i>Approve ('+approveCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    listContractsPaginationWFA();	
    vendorListContractsPaginationWFA();
	
}
</script>

<script>
function listContractsPaginationWFA(){
	$('#contractListTableWFA1').DataTable({ 
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
}
function vendorListContractsPaginationWFA(){ 
    $('#contractListTableWFA').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>


<script>
function preparedContractListWTS(data){
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableWTS" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Owner</th>';
		result = result + '    <th>Vendor Contact</th>';
		result = result + '    <th>Start date</th>';
		result = result + '    <th>End date</th>'; 
// 		result = result + '    <th>Received On</th>';
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
		           	result = result + '<div class="tbldata-dotdot-sm"><span>'+report.contractTitle+'</span></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span>'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>';
		         result = result + '<td>';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result + '<div class="tblminwidth-75">'+report.endDateStr+'</div>';
		         }else{
		        	 result = result + '<div class="tblminwidth-75">--</div>';
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
		         result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a></div>';
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
	opt = opt + '    <th>Owner</th>';
	opt = opt + '    <th>Client Contact</th>';
	opt = opt + '    <th>Start date</th>';
	opt = opt + '    <th>End date</th>'; 
// 	opt = opt + '    <th>Received On</th>';
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
	       		 opt = opt + '<div class="tbldata-dotdot-sm"><span>'+output.contractTitle+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
       			 opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
       		 opt = opt + '</td>';
	    	 opt = opt + '<td>';
	       	 opt = opt + '<div><span>'+output.ownerName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt + '<div class="tblminwidth-75">'+output.endDateStr+'</div>';
	         }else{
	        	 opt = opt + '<div class="tblminwidth-75">--</div>';
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
     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a></div>';
     		 opt = opt + '</td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var signCount = parseInt(vendorsCount) + parseInt(clientsCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#sign").html('<i class="fa fa-clock-o fa-2"></i>Sign ('+signCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    listContractsPaginationWTS();	
    vendorListContractsPaginationWTS();
	
}
</script>

<script>
function listContractsPaginationWTS(){
	$('#contractListTableWTS1').DataTable({ 
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
}
function vendorListContractsPaginationWTS(){ 
    $('#contractListTableWTS').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<script>
function preparedContractListWTV(data){
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableWTV" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Owner</th>';
		result = result + '    <th>Vendor Contact</th>';
// 		result = result + '    <th>Signed On</th>';
		result = result + '    <th>Start date</th>';
		result = result + '    <th>End date</th>'; 
		result = result + '    <th>Billing Cycle</th>';
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		           	result = result + '<div class="tbldata-dotdot-sm"><span>'+report.contractTitle+'</span></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span>'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         //need to change for signed on
		         /* result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>'; */
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result + '<div class="tblminwidth-75">'+report.endDateStr+'</div>';
		         }else{
		        	 result = result + '<div class="tblminwidth-75">--</div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.invoiceFrequency+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';//Waiting to be signed
		         result = result + '<div class="tbldata-dotdot_80">';
	     		 if(report.contractStatus == "completed" && report.templateSubmissionStatus != "FINALIZED"){
	     			result = result + '<div id="removeStatus'+report.id+'"><a class="btn btn-sm btn-success" title="finalize" onclick="finalizeContract('+report.id+',\'WAITING_TO_BE_VERIFY\');">Finalize</a></div>';
	            	 }
	     		 result = result + '<div><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a></div>';
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
	opt = opt + '    <th>Owner</th>';
	opt = opt + '    <th>Client Contact</th>';
// 	opt = opt + '    <th>Signed On</th>';
	opt = opt + '    <th>Start date</th>';
	opt = opt + '    <th>End date</th>'; 
	opt = opt + '    <th>Billing Cycle</th>';
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	       		 opt = opt + '<div class="tbldata-dotdot-sm"><span>'+output.contractTitle+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
       			 opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
       		 opt = opt + '</td>';
	    	 opt = opt + '<td>';
	       	 opt = opt + '<div><span>'+output.ownerName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>';
	         
	         //need to change for signed on
	         /* opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>'; */
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt + '<div class="tblminwidth-75">'+output.endDateStr+'</div>';
	         }else{
	        	 opt = opt + '<div class="tblminwidth-75">--</div>';
	         } 
	         opt = opt + '</td>';

	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.invoiceFrequency+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';//Waiting to be signed
	         opt = opt + '<div class="tbldata-dotdot_80">';
	         if(output.contractStatus == "completed" && output.templateSubmissionStatus != "FINALIZED"){
	     			opt = opt +  '<div id="removeStatus'+output.id+'"><a class="btn btn-sm btn-success" title="finalize" onclick="finalizeContract('+output.id+',\'WAITING_TO_BE_VERIFY\');">Finalize</a></div>';
	     		}
     		 opt = opt + '<div ><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a></div>';
     		 opt = opt + '</div></td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var verifyCount = parseInt(vendorsCount) + parseInt(clientsCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#verify").html('<i class="fa fa-clock-o fa-2"></i>Verify ('+verifyCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    listContractsPaginationWTV();	
    vendorListContractsPaginationWTV();
	
}
</script>

<script>
function listContractsPaginationWTV(){
	$('#contractListTableWTV1').DataTable({ 
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
}
function vendorListContractsPaginationWTV(){ 
    $('#contractListTableWTV').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<script>
function preparedContractListFinalize(data){
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableFinalize" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Owner</th>';
		result = result + '    <th>Vendor Contact</th>';
		result = result + '    <th>Start date</th>';
		result = result + '    <th>End date</th>';
// 		result = result + '    <th>Finalized On</th>'; 
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		           	result = result + '<div class="tbldata-dotdot-sm"><span>'+report.contractTitle+'</span></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span>'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result + '<div class="tblminwidth-75">'+report.endDateStr+'</div>';
		         }else{
		        	 result = result + '<div class="tblminwidth-75">--</div>';
		         }
		         result = result + '</td>';
		         
		         //need to change for finalized on
		         /* result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>'; */
		         
		         result = result + '<td>';
		         result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a></div>';
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
	opt = opt + '    <th>Owner</th>';
	opt = opt + '    <th>Client Contact</th>';
	opt = opt + '    <th>Start date</th>';
	opt = opt + '    <th>End date</th>';
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
	       		 opt = opt + '<div class="tbldata-dotdot-sm"><span>'+output.contractTitle+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
       			 opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
       		 opt = opt + '</td>';
	    	 opt = opt + '<td>';
	       	 opt = opt + '<div><span>'+output.ownerName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt + '<div class="tblminwidth-75">'+output.endDateStr+'</div>';
	         }else{
	        	 opt = opt + '<div class="tblminwidth-75">--</div>';
	         }
	         opt = opt + '</td>';
	         
	       //need to change for Finalized on
	         /* opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>'; */

	         opt = opt + '<td>';
     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a></div>';
     		 opt = opt + '</td>';
     		 
	     	 opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var finalisedCount = parseInt(vendorsCount) + parseInt(clientsCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#finalised").html('Finalized ('+finalisedCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    listContractsPaginationFinalize();	
    vendorListContractsPaginationFinalize();
	
}
</script>

<script>
function listContractsPaginationFinalize(){
	$('#contractListTableFinalize1').DataTable({ 
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
}
function vendorListContractsPaginationFinalize(){ 
    $('#contractListTableFinalize').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<script>
function preparedContractListExpired(data){
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableEXP" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Owner</th>';
		result = result + '    <th>Vendor Contact</th>';
		result = result + '    <th>Start date</th>';
		result = result + '    <th>End Date</th>'; 
		result = result + '    <th>Action</th>';
		result = result + '   </tr>';
		result = result + '	</thead>';
		result = result + '	<tbody>';
	if(json.vendorcontractsList.length>0){
		    for (var i = 0; i < json.vendorcontractsList.length; i++) {
		    	var report= json.vendorcontractsList[i];
		    	result = result + '<tr id="row'+report.id+'">';
		        result = result + '<td>';
		           	result = result + '<div class="tbldata-dotdot-sm"><span>'+report.contractTitle+'</span></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span>'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result + '<div class="tblminwidth-75">'+report.endDateStr+'</div>';
		         }else{
		        	 result = result + '<div class="tblminwidth-75">--</div>';
		         }
		         result = result + '</td>';
		         
		         result = result + '<div class="tbldata-dotdot_80">';
			     	if(report.templateSubmissionStatus == "EXPIRED"){
			     		result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a>';
			     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
			     			result = result + '| <a href="<c:url value="/addNewContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
			     		'</sec:authorize>';
			     		result = result + '</div>';
		     	  }
		     	  else{
		     		 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a></div>';
		     	  }
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
	opt = opt + '    <th>Owner</th>';
	opt = opt + '    <th>Client Contact</th>';
	opt = opt + '    <th>Start date</th>';
	opt = opt + '    <th>End Date</th>'; 
	opt = opt + '    <th>Action</th>';
	opt = opt + '   </tr>';
	opt = opt + '	</thead>';
	opt = opt + '	<tbody>';
	if(json.clientcontractsList.length>0){
	    for (var i = 0; i < json.clientcontractsList.length; i++) {
	    	var output= json.clientcontractsList[i];
	    	opt = opt + '<tr id="row'+output.id+'">';
	        opt = opt + '<td>';
	       		 opt = opt + '<div class="tbldata-dotdot-sm"><span>'+output.contractTitle+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
       			 opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
       		 opt = opt + '</td>';
	    	 opt = opt + '<td>';
	       	 opt = opt + '<div><span>'+output.ownerName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt + '<div class="tblminwidth-75">'+output.endDateStr+'</div>';
	         }else{
	        	 opt = opt + '<div class="tblminwidth-75">--</div>';
	         } 
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         
	         opt = opt + '<div class="tbldata-dotdot_80">';
	     	  if(output.templateSubmissionStatus == "EXPIRED" ){
	     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a>';
	     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
	     			opt = opt + '| <a href="<c:url value="/addNewContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
	     		'</sec:authorize>';
	     		opt = opt + '</div>';
	     	  }
	     	  else{
	     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a></div>';
	     	  }
	     		opt = opt + '</div></td>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var expiredCount = parseInt(vendorsCount) + parseInt(clientsCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#expired").html('Expired ('+expiredCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    listContractsPaginationEXP();	
    vendorListContractsPaginationEXP();
	
}
</script>

<script>
function listContractsPaginationEXP(){
	$('#contractListTableEXP1').DataTable({ 
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
}
function vendorListContractsPaginationEXP(){ 
    $('#contractListTableEXP').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<script>
function preparedContractListRejected(data){
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
	 var json = JSON.parse(data);
	 var result = '';
	 result = result +  '<table id="contractListTableREJ" class="display" cellspacing="0" width="100%" >';
		result = result + '	<thead>';
		result = result + '   <tr>';
		result = result + '    <th>Title</th>';
		result = result + '    <th>Type</th>';
		result = result + '    <th>Owner</th>';
		result = result + '    <th>Contact</th>';
		result = result + '    <th>Start date</th>';
		result = result + '    <th>End Date</th>'; 
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
		           	result = result + '<div class="tbldata-dotdot-sm"><span>'+report.contractTitle+'</span></div>';
		         result = result + '</td>';
		         result = result + '<td>';
		           	result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         result = result + '</td>';
		    	 result = result + '<td>';
		       	 result = result + '<div><span>'+report.ownerName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</div>';
		         result = result + '</td>';
		         
		         result = result + '<td>';
		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
		        	 result = result + '<div class="tblminwidth-75">'+report.endDateStr+'</div>';
		         }else{
		        	 result = result + '<div class="tblminwidth-75">--</div>';
		         }
		         result = result + '</td>';
		         
		         //need to be changed for Rejected by
		         /* result = result + '<td>';
		         result = result + '<div><span>'+report.vendorContactName+'</span></div>';
		         result = result + '</td>'; */
		         
		         result = result + '<td>';
		         result = result + '<div class="tbldata-dotdot_80">';
		     	if(report.templateSubmissionStatus == "REJECTED"){
		     		result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a>';
		     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		     			result = result + '| <a href="<c:url value="/addNewContract?contractUUID='+report.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		     		'</sec:authorize>';
		     		result = result + '</div>';
	     	  }
	     	  else{
	     		 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a></div>';
	     	  }
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
	opt = opt + '    <th>Owner</th>';
	opt = opt + '    <th>Client Contact</th>';
	opt = opt + '    <th>Start date</th>';
	opt = opt + '    <th>End Date</th>';
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
	       		 opt = opt + '<div class="tbldata-dotdot-sm"><span>'+output.contractTitle+'</span></div>';
	         opt = opt + '</td>';
	         opt = opt + '<td>';
       			 opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
       		 opt = opt + '</td>';
	    	 opt = opt + '<td>';
	       	 opt = opt + '<div><span>'+output.ownerName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</div>';
	         opt = opt + '</td>';
	         
	         opt = opt + '<td>';
	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	        	 opt = opt + '<div class="tblminwidth-75">'+output.endDateStr+'</div>';
	         }else{
	        	 opt = opt + '<div class="tblminwidth-75">--</div>';
	         } 
	         opt = opt + '</td>';
	         
	         // need to be changed for Rejected By
	         /* opt = opt + '<td>';
	         opt = opt + '<div><span>'+output.vendorContactName+'</span></div>';
	         opt = opt + '</td>'; */
	         
	         opt = opt + '<div class="tbldata-dotdot_80">';
	     	  if(output.templateSubmissionStatus == "REJECTED" ){
	     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a>';
	     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
	     			opt = opt + '| <a href="<c:url value="/addNewContract?contractUUID='+output.contractUUID+'" />" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
	     		'</sec:authorize>';
	     		opt = opt + '</div>';
	     	  }
	     	  else{
	     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.contractUUID+'\')"></i></a></div>';
	     	  }
	     		opt = opt + '</div>';
	     	
	     	opt = opt + '</tr>';
	    }
		
	
	}
	opt = opt + '	</tbody>';
	opt = opt + '</table>';
	
	var vendorsCount = json.vendorcontractsList.length;
	var clientsCount = json.clientcontractsList.length;
	var rejectedCount = parseInt(vendorsCount) + parseInt(clientsCount);
	
	$("#Vendor1").html('Vendors ('+vendorsCount+')');
	$("#Client1").html('Clients ('+clientsCount+')');
	$("#rejected").html('Rejected ('+rejectedCount+')');
	
    $("#vendorContractsList").empty();
    $("#vendorContractsList").append(result);
    $("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    listContractsPaginationREJ();	
    vendorListContractsPaginationREJ();
	
}
</script>

<script>
function listContractsPaginationREJ(){
	$('#contractListTableREJ1').DataTable({ 
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
}
function vendorListContractsPaginationREJ(){ 
    $('#contractListTableREJ').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<script type="text/javascript">
function previewContractPDF(id) {
	var sourceleadUrl ='${sourceleadUrl}';
	window.open(sourceleadUrl+'/viewContractPdf/'+id+'','_newtab');
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
       },
   });  
}
</script>


<script>
function approveContract(recordId,status){
	 var rowHtmlContent =  $("#row"+recordId).html();
	var sourceleadUrl ='${sourceleadUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	 $.ajax({
       type : "POST",
        url: sourceleadUrl+"/approveContract?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,       
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
        	$("#loading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
       },
   });  
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
        $("#expired").removeClass("expi");
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
function Check(val){
			 if(val=="Vendors"){
				 document.getElementById("vendorContractsList").style.display="block";
				 document.getElementById("clientContractsList").style.display="none";
			 }
			 if(val=="Clients"){
				 document.getElementById("clientContractsList").style.display="block";
				 document.getElementById("vendorContractsList").style.display="none";
			 }
}

</script>


<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->




