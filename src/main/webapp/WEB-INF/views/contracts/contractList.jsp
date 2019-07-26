<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="/resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">

$(document).ready(function(){ 
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	getIndividualCounts('DRAFT');              
	var status='${status}'
	var contractType='${contractType}'
	if(status == "waiting"){
		$("#draft").removeClass("draft"); 
	    $("#approve").addClass("wApp");
	    $("#contractStage").val("WAITING_FOR_APPROVER");
	   if(contractType == "Vendor MSA" || contractType == "Vendor SOW" || contractType == "Vendor NDA" || contractType == "Vendor NSA"){
		   $("#Vendor1").addClass("active"); 
			Check('Vendors');
		}
		if(contractType == "Client MSA" || contractType == "Client SOW"){
			$("#Vendor1").removeClass("active"); 
			$("#Client1").addClass("active"); 
			Check('Clients');
		}
		if(contractType == "Full time offer"){
			$("#Vendor1").removeClass("active"); 
			$("#Client1").removeClass("active");
			$("#Applicants1").addClass("active");
			Check('Applicants');
		}
	}
	else if(status == "rejected"){
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").removeClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").addClass("rej");
        
        $("#contractStage").val("REJECTED");
        if(contractType == "Vendor MSA" || contractType == "Vendor SOW" || contractType == "Vendor NDA" || contractType == "Vendor NSA"){
			$("#Vendor1").addClass("active"); 
			Check('Vendors');
		}
		if(contractType == "Client MSA" || contractType == "Client SOW"){
			$("#Vendor1").removeClass("active"); 
			$("#Client1").addClass("active"); 
			Check('Clients');
		}
		if(contractType == "Full time offer"){
			$("#Vendor1").removeClass("active"); 
			$("#Client1").removeClass("active");
			$("#Applicants1").addClass("active");
			Check('Applicants');
		}
	}
	else if(status == "verify"){
		$("#draft").removeClass("draft"); 
        $("#approve").removeClass("wApp");
        $("#sign").removeClass("wSig");
        $("#verify").addClass("wVer");
        $("#finalised").removeClass("fin");
        $("#expired").removeClass("exp");
        $("#rejected").removeClass("rej");
        
        $("#contractStage").val("WAITING_TO_BE_VERIFY");
        if(contractType == "Vendor MSA" || contractType == "Vendor SOW" || contractType == "Vendor NDA" || contractType == "Vendor NSA"){
			$("#Vendor1").addClass("active"); 
			Check('Vendors');
		}
		if(contractType == "Client MSA" || contractType == "Client SOW"){
			$("#Vendor1").removeClass("active"); 
			$("#Client1").addClass("active"); 
			Check('Clients');
		}
		/* if(contractType == "Vendor NDA" || contractType == "Vendor NSA"){
			$("#Vendor1").removeClass("active"); 
			$("#Client1").addClass("active"); 
			Check('Vendors');
		} */
		if(contractType == "Full time offer"){
			$("#Vendor1").removeClass("active"); 
			$("#Client1").removeClass("active");
			$("#Applicants1").addClass("active");
			Check('Applicants');
		}
	}
	
	else if(status == "sign"){
		$("#draft").removeClass("draft"); 
	    $("#approve").removeClass("wApp");
	    $("#sign").addClass("wSig");
	    $("#contractStage").val("WAITING_TO_BE_SIGNED");
	   if(contractType == "Vendor MSA" || contractType == "Vendor SOW"){
		   $("#Vendor1").addClass("active"); 
			Check('Vendors');
		}
	   if(contractType == "Vendor NDA" || contractType == "Vendor NSA"){
		   $("#Vendor1").removeClass("active"); 
			$("#Client1").addClass("active"); 
			Check('Clients');
		}
		if(contractType == "Client MSA" || contractType == "Client SOW"){
			$("#Vendor1").addClass("active"); 
// 			$("#Client1").addClass("active"); 
			Check('Vendors');
		}
		if(contractType == "Full time offer"){
			$("#Vendor1").removeClass("active"); 
			$("#Client1").removeClass("active");
			$("#Applicants1").addClass("active");
			Check('Applicants');
		}
		
	}
	else{
		 $("#contractStage").val("DRAFT");
	}
   getTemplateList();
   //getStageCounts();
});
</script>


<section class="content-wraper">
<section class="content-header clearfix">
 <div class="success_pop" id="contractSelector" style="display: none;"> 
     <i class="fa fa-check-circle"></i><span id="candMsgDiv"></span> 
   </div>
   
   <c:if test="${!empty msg}">
	        <div class="success_pop" id="sucesspop"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
  <div class="pull-left">
    <h2>Contracts</h2>
  </div>
   <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; Contracts</div>
</section>
<%-- <section class="contrHead">  --%>
<!-- <ul>  -->
<%-- <li title="Draft"><a href="#" id="draft" class="draft" onClick="mainTabEnable(this);getTemplateList('main');">Draft (${contractsCount.totalDraftCount})</a></li> --%>
<%-- <li title="Waiting for Approval"><a href="#" id="approve"  onClick="mainTabEnable(this);getTemplateList('main');"><i class="fa fa-clock-o fa-2"></i>Waiting (${contractsCount.totalWTACount})</a></li>  --%>
<%-- <li title="Waiting to be sign"><a href="#" id="sign" onClick="mainTabEnable(this);getTemplateList('main');"><i class="fa fa-clock-o fa-2"></i>Sign (${contractsCount.totalWTSCount})</a></li> --%>
<%-- <li title="Waiting for Verify"><a href="#" id="verify" onClick="mainTabEnable(this);getTemplateList('main');"><i class="fa fa-clock-o fa-2"></i>Counter sign (${contractsCount.totalWTVCount})</a></li> --%>
<%-- <li title="Finalized"><a href="#" id="finalised"  onClick="mainTabEnable(this); getTemplateList('main');">Completed (${contractsCount.totalFinalizedCount})</a></li> --%>
<%-- <li title="Expired"><a href="#" id="expired" onClick="mainTabEnable(this);getTemplateList('main');">Expired (${contractsCount.totalExpiredCount})</a></li> --%>
<%-- <li title="Rejected"><a href="#" id="rejected"  onClick="mainTabEnable(this);getTemplateList('main');">Rejected (${contractsCount.totalRejectedCount})</a></li> --%>
<!-- <input type="hidden" name="contractStage" id="contractStage" > -->
<!-- <div class="conBtn"> -->
<!--   <span onclick="addContracts();">Add<i class="fa fa-caret-down"></i></span> -->
<%-- <%--   <ul>   --%>
<%-- 	  <sec:authorize access="hasAnyRole('ROLE_ADD_NEW_CONTRACT')"> --%>
<%-- 	  <li><a href="<c:url value="/addNewContract" />"><i class="fa fa-caret-right"></i>New Contract</a></li> --%>
<%-- 	  </sec:authorize> --%>
<%-- 	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')"> --%>
<%-- 	  <li><a href="<c:url value="/addExistingContract" />"><i class="fa fa-caret-right"></i>Existing Contract</a></li> --%>
<%--   	  </sec:authorize> --%>
<%--   </ul> --%> 
<!-- </div> -->

<!-- </ul> -->
<%-- </section> --%>

<!-- Add candidate Popup -->
  <div class="addcandidate-popup">
    <div class="row">
      <div class="col-md-12"> <a href="javascript:void(0)" class="close-popdiv" onClick="closeContract()"><i class="fa fa-close"></i></a>
      <sec:authorize access="hasAnyRole('ROLE_ADD_NEW_CONTRACT')">
      <div class="col-md-4 mt25">
        <div class="small-widget bgGreen"> <a href="<c:url value="${SourceLeadWeb_vmsUrl}/addNewContract" />" title="New Contract">
          <div class="clearfix mb10"> <i class="fa fa-file-o fa-4x"></i> <span class="fa fa-plus"></span> </div>
          <strong class="clearfix">New Contract</strong> </a> </div>
      </div>
 	</sec:authorize>
	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')">
        <div class="col-md-4 mt25">
          <div class="small-widget bg-yellow"> <a href="<c:url value="${SourceLeadWeb_vmsUrl}/addCustomContract" />" title="Custom Contract">
            <div class="clearfix mb10"><i class="fa fa-file-code-o fa-4x"></i> <span class="fa fa-plus"></span> </div>
            <strong class="clearfix">Custom Contract</strong> </a> </div>
        </div>
        </sec:authorize>
	  <sec:authorize access="hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')">
        <div class="col-md-4 mt25">
          <div class="small-widget bg-purple"> <a href="<c:url value="${SourceLeadWeb_vmsUrl}/addExistingContract" />" title="Existing Contract">
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

<div class="clearfix"></div>	
<div class="content-area content-new clearfix mt">
 <div class="project_wrap">
 
 
 <section class="contrHead"> 
<ul> 
<li title="Draft"><a href="#" id="draft" class="draft" onClick="mainTabEnable(this);getIndividualCounts('DRAFT');filterContractsList();">Draft (${contractsCount.totalDraftCount})</a></li>
<li title="Waiting for Approval"><a href="#" id="approve"  onClick="mainTabEnable(this);getIndividualCounts('WAITING_TO_SEND');filterContractsList();"><i class="fa fa-clock-o fa-2"></i>Waiting (${contractsCount.totalWTACount})</a></li> 
<li title="Waiting to be sign"><a href="#" id="sign" onClick="mainTabEnable(this);getIndividualCounts('WAITING_TO_BE_SIGNED');filterContractsList();"><i class="fa fa-clock-o fa-2"></i>Sign (${contractsCount.totalWTSCount})</a></li>
<li title="Waiting for Verify"><a href="#" id="verify" onClick="mainTabEnable(this);getIndividualCounts('WAITING_TO_BE_VERIFY');filterContractsList();"><i class="fa fa-clock-o fa-2"></i>Counter sign (${contractsCount.totalWTVCount})</a></li>
<li title="Finalized"><a href="#" id="finalised"  onClick="mainTabEnable(this);getIndividualCounts('FINALIZED');filterContractsList();">Completed (${contractsCount.totalFinalizedCount})</a></li>
<li title="Expired"><a href="#" id="expired" onClick="mainTabEnable(this);getIndividualCounts('EXPIRED');filterContractsList();">Expired (${contractsCount.totalExpiredCount})</a></li>
<li title="Rejected"><a href="#" id="rejected"  onClick="mainTabEnable(this);getIndividualCounts('REJECTED');filterContractsList();">Rejected (${contractsCount.totalRejectedCount})</a></li>
<input type="hidden" name="contractStage" id="contractStage" >
<input type="hidden" name="contractAnotherStage" id="contractAnotherStage" >
<div class="conBtn">
  <span onclick="addContracts();">+ Contracts</span>
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
 
 
	<div class="timesheet_head timesheet_head_customize">
		<div class="ContrBut">
<!-- 		    <input type="hidden" name="tabType" id="tabType" value='Vendor'> -->
			<!-- <a href="#" id="Vendor" class="active tabActivation" onclick="tabEnable(this);getTemplateList('pending');">Vendors</a>
			<a href="#" id="Client" class="tabActivation" onclick="tabEnable(this);getTemplateList('pending'); ">Clients</a> -->
			<button type="button" value="Vendor" id="Vendor1" class="tabActivation btn btn-primary btn-sm" onclick="tabEnable(this);Check('Vendors');getTemplateList('Vendor');"> Vendors(${contractsCount.totalDraftVendorCount})</button>
			<button type="button" value="Client" id="Client1" class="tabActivation btn btn-default btn-sm" onclick="tabEnable(this);Check('Clients');getTemplateList('Client');">Clients (${contractsCount.totalDraftClientCount})</button>
			<button type="button" value = "Full time offer" id="Applicants1" class="tabActivation btn btn-default btn-sm" onclick="tabEnable(this);Check('Applicants');getTemplateList('Full time offer');">Applicants (${contractsCount.totalDraftApplicantCount})</button>
		    <input type="hidden" id="errorDiv" value=""/>
		</div>
		<ul>
		
		<%--  <li id="filterContractTagPop"><i class="fa fa-filter"></i><span>Tags</span><i class="glyphicon glyphicon-play"></i>
	           	<div class="statusFiler-4 approved_pop">
					<ul class="list-unstyled">
					<c:forEach var="tag" items="${contractTagsList}">
						<li><input type="checkbox" name="contractType" value="${tag}" onclick="getTemplateList();"><label for="c1">${tag}</label></li>
					</c:forEach>
					</ul>
				</div>	                	
			</li> --%>
			
			
			<li id="filterContractTypePop"><i class="fa fa-filter"></i><span>Type</span><i class="glyphicon glyphicon-play"></i>
	           	<div class="statusFiler-1 approved_pop">
					<ul class="list-unstyled">
						<li><input id="c1" type="checkbox" name="contractType" value="MSA" onclick="filterContractsList();"><label for="c1">MSA</label></li>
						<li><input id="c2" type="checkbox" name="contractType" value="SOW" onclick="filterContractsList();"><label for="c2">SOW</label></li>
						<li><input id="c3" type="checkbox" name="contractType" value="NDA" onclick="filterContractsList();"><label for="c3">NDA</label></li>
						<li><input id="c4" type="checkbox" name="contractType" value="NSA" onclick="filterContractsList();"><label for="c4">NSA</label></li>
						<li><input id="c5" type="checkbox" name="contractType" value="Full Time Offer" onclick="filterContractsList();"><label for="c5">Full Time Offer</label></li>
					</ul>
				</div>	                	
			</li>
			
			<li id="filterContractTypePop-2" style="display:none"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play"></i>
	           	<div class="statusFiler-3 approved_pop">
	           		<ul class="list-unstyled" style="display:none" id="waitingLiDiv">
						<li><input id="status5" type="checkbox" name="contractStatus" value="WAITING_TO_SEND" onclick="filterContractsList();"><label for="status5">Waiting to Send</label></li>
					</ul>
					
					<ul class="list-unstyled" style="display:none" id="signedDiv">
						<li><input id="status1" type="checkbox" name="contractStatus" value="PENDING" onclick="filterContractsList();"><label for="status1">Pending</label></li>
						<li><input id="status3" type="checkbox" name="contractStatus" value="WAITING_TO_BE_SIGNED" onclick="filterContractsList();"><label for="status3">Waiting to be Sign</label></li>
					</ul>
					
					<ul class="list-unstyled" style="display:none" id="verifyDiv">
						<li><input id="status2" type="checkbox" name="contractStatus" value="SEND_FOR_VERIFICATION" onclick="filterContractsList();"><label for="status2">Send For Verification</label></li>
						<li><input id="status4" type="checkbox" name="contractStatus" value="WAITING_TO_BE_VERIFY" onclick="filterContractsList();"><label for="status4">Waiting to be Verify</label></li>
					</ul>
				</div>	                	
			</li>
			
			<li id="addCustomContractFromWTV" style="display:none">
				<a href="<c:url value="${SourceLeadWeb_vmsUrl}/addCustomContract?contractFromType=WTVStage"/>" title="Custom Contract"><i class="fa fa-plus"></i>&nbsp;Add Custom Contact</a>
			</li>
	</ul>
		<div class="clearfix" id="vendorDataTable" style="display: block;">
		    <div class="remove_search mt5" > 
		    	<input type="text" placeholder="Vendor Search" id="vendorDataTableSearch"/>
		    </div>
	    </div>
	    <div class="clearfix" id="clinetDataTable" style="display: none;">
		    <div class="remove_search mt5" >
		    	<input type="text" placeholder="Client Search" id="clinetDataTableSearch"/>
		    </div>
	    </div>
	    <div class="clearfix" id="applicantDataTable" style="display: none;">
		    <div class="remove_search mt5" > 
		    	<input type="text" placeholder="Applicant Search" id="applicantDataTableSearch"/>
		    </div>
	    </div>
		
		<div class="clearfix"></div>
            
	</div>
	<div id="loading"></div>
    <div class="candidate_wrap_table repoPositiNoCount datatable_filter">
    <div id="vendorContractsList"  class=" overflow-data vendorContractsList" ></div>
     <div id="clientContractsList"  class=" overflow-data clientContractsList" ></div>
     <div id="applicantsContractsList"  class=" overflow-data applicantsContractsList" ></div>
	</div>
	
<!-- <div class="require_wrap_table datatable_filter">
  <div id="vendorClientApplicantDiv"></div>
	
</div> -->
  
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
		 <input type="button" value="Save" id="save_Doc" onclick="saveDocContract();" class="btn btn-primary padSm">
		<input type="button" value="Cancel" class="btn btn-default padSm" onclick="contPopClose();">
      </div>
</form:form>
</section>

<script type="text/javascript">
	function saveDocContract() {
		if ($('#contractDoc_form').validate(false, validationSettings)) {
			var formData = getFormData("contractDoc_form");
			var vmsUrl = '${SourceLeadWeb_vmsUrl}';
			var orgLocationId = '${SourceLeadWeb_orgLocationId}';
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
            $.post( vmsUrl+ "/saveDocContracts?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId, formData, function(data) {
            		location.replace(vmsUrl+"/contractListbyStatus");
                    });
			
			
			/* $.ajax({
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
					});  */
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	localStorage.setItem("stage",stage);
 	window.location.replace(vmsUrl+'/viewContract?contractUUID='+contractUUID);
}


function previewTemplatePDF(uuid, templateName){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(uuid != null && uuid != ""){
		window.open(vmsUrl+'/viewContractTemplatePdf/'+uuid+'/'+templateName+'','_newtab');
	}
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
        $(".statusFiler-2, .statusFiler-1,.statusFiler-4").hide();
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
	$("#filterContractTypePop").click(function(){
		$(".statusFiler-1").toggle();
		$(".statusFiler-2, .statusFiler-4, .statusFiler-3").hide();
	});
	
	$("#filterContractTagPop").click(function(){
		$(".statusFiler-4").toggle();
		$(".statusFiler-2,.statusFiler-3, .statusFiler-1").hide();
	});
	
	
	$("#filterContractTypePop-2").click(function(){
		$(".statusFiler-3").toggle();
		$(".statusFiler-2, .statusFiler-1,.statusFiler-4").hide();
	});
	
    $("html").click(function() {
        $(".filterContractType_pop").hide();
    });	
    $(".filterContractType_pop, #filterContractTypePop, #filterContractTagPop, #filterContractTypePop-2, .statusFiler-1, .statusFiler-2, .statusFiler-3").click(function(e) {
        e.stopPropagation();
    });	

</script>

<script>
function filterContractsList(){
	var listType = $("#errorDiv").val(); 
	getTemplateList(listType);
}
</script>

<script type="text/javascript">
function getTemplateList(listType){
	
	var contractSubmissionType = $("#contractStage").val();
	if(contractSubmissionType == 'WAITING_TO_BE_SIGNED'){
		$("#filterContractTypePop-2").css("display", "block");
		$("#verifyDiv").css("display", "none");
		$("#addCustomContractFromWTV").css("display", "none");
		$("#signedDiv").css("display", "block");
		$("#waitingLiDiv").css("display", "none");
		if(listType == "main"){
			$("#status1").prop("checked", true);
			$("#status3").prop("checked", true);
			$("#status5").removeAttr("checked");
			$("#status2").removeAttr("checked");
			$("#status4").removeAttr("checked");
		}
	}else if(contractSubmissionType == 'WAITING_TO_BE_VERIFY'){
		$("#filterContractTypePop-2").css("display", "block");
		$("#signedDiv").css("display", "none");
		$("#verifyDiv").css("display", "block");
		$("#addCustomContractFromWTV").css("display", "block");
		$("#waitingLiDiv").css("display", "none");
		if(listType == "main"){
			$("#status5").removeAttr("checked");
			$("#status1").removeAttr("checked");
			$("#status3").removeAttr("checked");
			$("#status2").prop("checked", true);
			$("#status4").prop("checked", true);
		}
	}else if(contractSubmissionType == 'WAITING_FOR_APPROVER'){
		$("#filterContractTypePop-2").css("display", "block");
		$("#signedDiv").css("display", "none");
		$("#waitingLiDiv").css("display", "block");
		$("#verifyDiv").css("display", "none");
		$("#addCustomContractFromWTV").css("display", "none");
		if(listType == "main"){
			$("#status5").prop("checked", "checked");
			$("#status1").removeAttr("checked");
			$("#status3").removeAttr("checked");
			$("#status2").removeAttr("checked");
			$("#status4").removeAttr("checked");
		}
	}else{
		$("#filterContractTypePop-2").css("display", "none");
		$("#waitingLiDiv").css("display", "none");
		$("#status5").removeAttr("checked");
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

	/* $('input:checkbox[name=tags]:checked').each(function() 
     {
		tags.push($(this).val());
	 }); */

	var tabType = "";
	if(listType == null || listType == '' || listType == 'undefined' || listType == 'main'){
		tabType = 'Vendor';
	}else{
		tabType = listType;
	}
// 	var tabType = $("#tabType").val();
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var searchValue = "";
	if(tabType == "Vendor"){
		 searchValue = $("#vendorDataTableSearch").val();
	}else if(tabType == "Client"){
		 searchValue = $("#clinetDataTableSearch").val();
	 }else if(tabType == "Full time offer"){
		 searchValue = $("#applicantDataTableSearch").val();
	} 
	
	var selectedContractType = "";
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
// 	 $.ajax({
//       type : "GET",
         var url = '';
         if(tabType == 'Vendor'){
        	 url = vmsUrl+"/getAllContractsByOrgLocationIdandStatus?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue+"&contractSubmissionType="+contractSubmissionType+"&tabType="+tabType+"&contractsType="+contractsType+"&contractStatus="+contractStatus;
         }else if(tabType == 'Client'){
        	 url = vmsUrl+"/getAllClientContractsByOrgLocationIdandStatus?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue+"&contractSubmissionType="+contractSubmissionType+"&tabType="+tabType+"&contractsType="+contractsType+"&contractStatus="+contractStatus;
         }else if(tabType == 'Full time offer'){
        	 url = vmsUrl+"/getAllApplicantContractsByOrgLocationIdandStatus?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue+"&contractSubmissionType="+contractSubmissionType+"&tabType="+tabType+"&contractsType="+contractsType+"&contractStatus="+contractStatus;
         }
//        success :function(data) {
    	   $("#loading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
    	   if(contractSubmissionType == 'DRAFT' || contractSubmissionType == 'REJECTED' || contractSubmissionType == 'EXPIRED' || contractSubmissionType == 'Finalized'){
    		   if(tabType == 'Vendor'){
    		      preparedContractListVendorDraft(url,tabType);
    		   }else if(tabType == 'Client'){
    			  preparedContractListClientDraft(url,tabType);
    		   }else if(tabType == 'Full time offer'){
    			  preparedContractListApplicantDraft(url,tabType);
    		   }
    	   }else if(contractSubmissionType == 'WAITING_FOR_APPROVER'){
    		   if(tabType == 'Vendor'){
     		      preparedContractListVendorWFA(url,tabType);
     		   }else if(tabType == 'Client'){
     			  preparedContractListClientWFA(url,tabType);
     		   }else if(tabType == 'Full time offer'){
     			  preparedContractListApplicantWFA(url,tabType);
     		   }
    	   }else if(contractSubmissionType == 'WAITING_TO_BE_SIGNED'){
    		   if(tabType == 'Vendor'){
      		      preparedContractListVendorWTS(url,tabType);
      		   }else if(tabType == 'Client'){
      			  preparedContractListClientWTS(url,tabType);
      		   }else if(tabType == 'Full time offer'){
      			  preparedContractListApplicantWTS(url,tabType);
      		   }
    	   }else if(contractSubmissionType == 'WAITING_TO_BE_VERIFY'){
    		   if(tabType == 'Vendor'){
       		      preparedContractListVendorWTV(url,tabType);
       		   }else if(tabType == 'Client'){
       			  preparedContractListClientWTV(url,tabType);
       		   }else if(tabType == 'Full time offer'){
       			  preparedContractListApplicantWTV(url,tabType);
       		   }
    	   }
   // 	   getStageCounts();
}
</script>

<script>
function preparedContractListVendorDraft(url,tabType){
	SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	  preparedContractVendorListDraft(url,tabType);
}
	  
function preparedContractVendorListDraft(url,tabType){
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
		$("#vendorContractsList").empty();
		$("#vendorContractsList").append(result);
		preparedContractVendorListDraftPagination(url,tabType);
}

function preparedContractVendorListDraftPagination(url,tabType){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#contractListTableDraft').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "vendorContact" }, 
         		   { "data": "vendor" }, 
         		   { "data": "startDate/endDate"}, 
         		   { "data": "reqDocCount"}, 
         		   { "data": "action"}
            
        ],
         "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, report, meta) {
        	var result = "";
            if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
            	result = result + '<div class="proj_show tblminwidth-150" onclick="PopUp(\''+report.contractUUID+'\',\'DRAFT\');"><div class="pull-left tbldata-dotdot-big inlineBlock text-left" title="'+report.autoGenId+' - '+report.contractTitle+'">'+report.autoGenId+' - '+report.contractTitle+'</div><i class="fa fa-binoculars" style="position: absolute; right: 0; top: 0;"></i></div>';
	        }else{
	        	result = result + '<div class="proj_show tblminwidth-150"><div class="pull-left tbldata-dotdot-big inlineBlock text-left" title="'+report.autoGenId+' - '+report.contractTitle+'">'+report.autoGenId+' - '+report.contractTitle+'</div></div>';
	        }
             return result; 
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, report, meta) {
		         var result = "";
		        result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         return result; 
		          }
		        },{	
		        	"targets": colIndex++,
		    	    "data": "owner",
		    	    "render": function(data, type, report, meta) {
		    	         var result = "";
		    	        result = result + '<div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
		    	         return result; 
		    	         }
		    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "vendorContact",
	    	    	    "render": function(data, type, report, meta) {
	    	    	         var result = "";
	    	    	        result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
	    	    	         return result; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "vendor",
	    	    	    	     "render": function(data, type, report, meta) {
	    	    	    	    	  var result = "";
	    	    	    	    	 if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
	    	    			        	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    			        	 result = result + '<div><span title="'+report.contractWith+'"><a href="'+vmsUrl+'/vendorsList?orgRelationUUID='+report.orgRelationUUID+'" title="'+report.contractWith+'">'+report.contractWith+'</a></span></div>';
	    	    			        	 '</sec:authorize>';
	    	    			        	 
	    	    			        	 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    			        	 result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
	    	    			        	 '</sec:authorize>';
	    	    			        	 
	    	    			         }
	    	    	    	    	  return result; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "startDate/endDate",
	    	    	    	    	     "render": function(data, type, report, meta) {
	    	    	    	    	    	var result = "";
	    	    	    	    	    	 result = result + '<div class="tblminwidth-75 mt5 mb5">'+report.startDateStr+'';
	    	    	    			         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
	    	    	    			        	 result = result + '</br>' + report.endDateStr;
	    	    	    			         }
	    	    	    			         result = result +'</div>';
	    	    	    	    	    	 return result;
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, report, meta) {
	    	    	    	    	    	      var result = "";
	    	    	    	    	    	     if(report.reqDocCount!="0"){
	    	    	    	    		        	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+report.contractUUID+'\',\''+report.parentId+'\');">'+report.reqDocCount+'</div>'; 
	    	    	    	    		         }
	    	    	    	    		         else{
	    	    	    	    		        	 result = result + '<div class="tblminwidth-75 ">'+report.reqDocCount+'</div>';
	    	    	    	    		         }
	    	    	    	    	    	     return result; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "action",
		    	    	    	    	    	    "render": function(data, type, report, meta) {
		    	    	    	    	    	    	var result = "";
				    	    	    	    			     	if(report.templateSubmissionStatus == "DRAFT"){
				    	    	    	    			     		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
				    	    	    	    			     		if(report.typeOfContract == "New"){
				    	    	    	    			     			if(report.templateId != null && report.templateId != '' && report.templateId != 'undefined'){
				    	    	    	    			     				result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a>';
				    	    	    	    			     			}
				    	    	    	    			     		}else{
				    	    	    	    			     		result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewContractPDF(\''+report.fileURL+'\', \''+report.fileName+'\', \''+report.fileType+'\')"></i></a>';
				    	    	    	    			     		}
				    	    	    	    			     		'</sec:authorize>';
				    	    	    	    			     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
				    	    	    	    			     		if(report.typeOfContract == "New"){
				    	    	    	    			     			result = result + ' <a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+report.contractUUID+'"  title="Edit"><i class="fa fa-pencil editAA"></i></a>';
				    	    	    	    			     		}
				    	    	    	    			     		if(report.typeOfContract == "Custom"){
				    	    	    	    			     			result = result + ' <a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+report.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
				    	    	    	    			     		}
				    	    	    	    			     		'</sec:authorize>';
		    	    	    	    	    	    	}else if(report.templateSubmissionStatus == "FINALIZED"){
		    	    	    	    	    	    		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    	   		         if(report.typeOfContract == "New" || report.typeOfContract == "Custom"){
		    	    	    	    	   		        	 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye mr10" onClick="viewFile(\''+report.templateFileURL+'\', \''+report.contractType+'\', \'application/pdf\')"></i></a></div>';
		    	    	    	    	   		         }else{
		    	    	    	    	   		         result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="viewFile(\''+report.fileURL+'\', \''+report.fileName+'\', \''+report.fileType+'\')"></i></a></div>';
		    	    	    	    	   		         }
		    	    	    	    	   		         '</sec:authorize>';
		    	    	    	    	    	    	}else if(report.templateSubmissionStatus == "EXPIRED"){
		    	    	    	    	    	    		result = result + '<div class="tbldata-dotdot_80">';
		    	    	    	    	   		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
//		    	    	    	    	    		         if(report.typeOfContract == "New"){
		    	    	    	    	   		        	 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a></div>'; 
//		    	    	    	    	    		         }else{
//		    	    	    	    	    		     		 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.fileURL+'\', \''+report.fileName+'\', \''+report.fileType+'\')"></i></a></div>';
//		    	    	    	    	    		         }
		    	    	    	    	   		     		 '</sec:authorize>';
		    	    	    	    	   			         result = result + '</div></td>';
		    	    	    	    	    	    	}else if(report.templateSubmissionStatus == "REJECTED"){
		    	    	    	    	    	    		result = result + '<div class="tbldata-dotdot_80">';
		    	    	    	    	   		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    	   		         if(report.typeOfContract == "New"){
		    	    	    	    	   		        	 result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a>';
		    	    	    	    	   		         }else{
		    	    	    	    	   		     		result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewContractPDF(\''+report.fileURL+'\', \''+report.fileName+'\', \''+report.fileType+'\')"></i></a>';
		    	    	    	    	   		         }
		    	    	    	    	   		     		'</sec:authorize>';
		    	    	    	    	   	     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		    	    	    	    	   		     		if(report.typeOfContract == "New"){
		    	    	    	    	   		     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+report.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    	   		     		}
		    	    	    	    	   		     		if(report.typeOfContract == "Custom"){
		    	    	    	    	   		     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+report.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    	   		     		}
		    	    	    	    	        			'</sec:authorize>';
		    	    	    	    	   		         result = result + '</div></td>';
		    	    	    	    	    	    	}
		    	    	    	    	    	     
		    	    	    	    	    	     return result;
		    	    	    	    	    	    } 
		    	    	    	    	    	    
		    	    	    	 }],
		    	    	    	 "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
	     	   	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
// 		    	    	 	 if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
// 		    	    	 	} 
		    	    	 	$("#contractListTableDraft").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#vendorDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
		    	    	    	 
    
  }


function preparedContractListClientDraft(url,tabType){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	  preparedContractClientListDraft(url,tabType);
}

function preparedContractClientListDraft(url,tabType){
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
	$("#clientContractsList").empty();
	$("#clientContractsList").append(opt); 
	preparedContractClientListDraftPagination(url,tabType);
}

function preparedContractClientListDraftPagination(url,tabType){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#contractListTableDraft1').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "clientContact" }, 
         		   { "data": "client" }, 
         		   { "data": "startDate/endDate"}, 
         		   { "data": "reqDocCount"}, 
         		   { "data": "action"} 
            
        ],
        "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, output, meta) {
            var opt = "";
            if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="proj_show tblminwidth-150" onclick="PopUp(\''+output.contractUUID+'\',\'DRAFT\');"><div class="tbldata-dotdot-big inlineBlock text-left" title="'+output.autoGenId+' - '+output.contractTitle+'">'+output.autoGenId+' - '+output.contractTitle+'</div><i class="fa fa-binoculars"  style="position: absolute; right: 0; top: 0;"></i></div>';
	        }else{
	        	opt = opt + '<div class="proj_show tblminwidth-150"><div class="tbldata-dotdot-big inlineBlock text-left" title="'+output.autoGenId+' - '+output.contractTitle+'">'+output.autoGenId+' - '+output.contractTitle+'</div></div>';
	        }
             return opt;
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, output, meta) {
		        var opt = "";
		        opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
		         return opt; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, output, meta) {
	    	         var opt = "";
	    	        opt = opt + '<div><span title="'+output.ownerName+'">'+output.ownerName+'</span></div>';
	    	         return opt; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "clientContact",
	    	    	    "render": function(data, type, output, meta) {
	    	    	         var opt = "";
	    	    	        opt = opt + '<div><span title="'+output.vendorContactName+'">'+output.vendorContactName+'</span></div>';
	    	    	         return opt; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "client",
	    	    	    	     "render": function(data, type, output, meta) {
	    	    	    	    	  var opt = "";
	    	    	    	    	 if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	    	    	    	        	 
	    	    	    	        	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    	    	        	   opt = opt + '<div><span title="'+output.contractWith+'"><a href="'+vmsUrl+'/clientDashboard?orgRelationUUID='+output.orgRelationUUID+'" title="'+output.contractWith+'">'+output.contractWith+'</a></span></div>';
	    	    	    	        	 '</sec:authorize>';
	    	    	    	        	 
	    	    	    	        	 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    	    	        	   opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	    	    	    	        	 '</sec:authorize>';
	    	    	    	        	 
	    	    	    	         }
	    	    	    	    	  return opt; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "startDate/endDate",
	    	    	    	    	     "render": function(data, type, output, meta) {
	    	    	    	    	    	  var opt = "";
	    	    	    	    	    	 opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'';
	    	    	    	    	         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	    	    	    	    	        	 opt = opt +'</br>'+output.endDateStr+'</div>';
	    	    	    	    	         }
	    	    	    	    	    	 return opt; 
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, output, meta) {
	    	    	    	    	    	      var opt = "";
	    	    	    	    	    	     if(output.reqDocCount!="0"){
	    	    	    	    	 	         	opt = opt + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+output.contractUUID+'\',\''+output.parentId+'\');">'+output.reqDocCount+'</div>';
	    	    	    	    	 	         }
	    	    	    	    	 	         else{
	    	    	    	    	 	        	 opt = opt + '<div class="tblminwidth-75 ">'+output.reqDocCount+'</div>';
	    	    	    	    	 	         }	
	    	    	    	    	    	     return opt; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "action",
		    	    	    	    	    	    "render": function(data, type, output, meta) {
		    	    	    	    	    	      var opt = "";
		    	    	    	    		     	  if(output.templateSubmissionStatus == "DRAFT" ){
		    	    	    	    		     		  if(output.typeOfContract == "New"){
		    	    	    	    		     			 if(output.templateId != null && output.templateId != '' && output.templateId != 'undefined'){
		    	    	    	    		     			 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a>';
		    	    	    	    		     			 }
		    	    	    	    		     		  }else{
		    	    	    	    		     		  opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewContractPDF(\''+output.fileURL+'\', \''+output.fileName+'\', \''+output.fileType+'\')"></i></a>';
		    	    	    	    		     		  }
		    	    	    	    		     		 '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		    	    	    	    			     		if(output.typeOfContract == "New"){
		    	    	    	    			     			opt = opt + '| <a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+output.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    			     		}
		    	    	    	    			     		if(output.typeOfContract == "Custom"){
		    	    	    	    			     			opt = opt + '| <a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+output.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    			     		}
		    	    	    	    		     		'</sec:authorize>';
		    	    	    	    	    	     }else if(output.templateSubmissionStatus == "FINALIZED"){
		    	    	    	    	    	    	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    	    	         if(output.typeOfContract == "New" || output.typeOfContract == "Custom"){
		    	    	    	    	    	        	 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye mr10" onClick="viewFile(\''+output.templateFileURL+'\', \''+output.contractType+'\', \'application/pdf\')"></i></a></div>';
		    	    	    	    	    	         }else{
		    	    	    	    	         		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="viewFile(\''+output.fileURL+'\', \''+output.fileName+'\', \''+output.fileType+'\')"></i></a></div>';

		    	    	    	    	    	         }
		    	    	    	    	         		 '</sec:authorize>';
		    	    	    	    	    	     }else if(output.templateSubmissionStatus == "EXPIRED"){
		    	    	    	    	    	    	 opt = opt + '<div class="tbldata-dotdot_80">';
		    	    	    	    	    	         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
//		    	    	    	    	     	         if(output.typeOfContract == "New"){
		    	    	    	    	    	        	 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a></div>';
//		    	    	    	    	     	         }else{
//		    	    	    	    	     	     		 opt = opt + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.fileURL+'\', \''+output.fileName+'\', \''+output.fileType+'\')"></i></a></div>';
//		    	    	    	    	     	         }
		    	    	    	    	    	         '</sec:authorize>';
		    	    	    	    	    	     		 opt = opt + '</div></td>';
		    	    	    	    	    	     }else if(output.templateSubmissionStatus == "REJECTED"){
		    	    	    	    	    	    	 opt = opt + '<div class="tbldata-dotdot_80">';
		    	    	    	    	    	         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    	    	         if(output.typeOfContract == "New"){
		    	    	    	    	    	        	 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a>';
		    	    	    	    	    	         }else{
		    	    	    	    	    	     		 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewContractPDF(\''+output.fileURL+'\', \''+output.fileName+'\', \''+output.fileType+'\')"></i></a>';
		    	    	    	    	    	         }
		    	    	    	    	    	     		 '</sec:authorize>';
		    	    	    	    	    	     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		    	    	    	    	    		     		if(output.typeOfContract == "New"){
		    	    	    	    	    		     			opt = opt + '| <a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+output.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    	    		     		}
		    	    	    	    	    		     		if(output.typeOfContract == "Custom"){
		    	    	    	    	    		     			opt = opt + '| <a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+output.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    	    		     		}
		    	    	    	    	     				'</sec:authorize>';
		    	    	    	    	    	     		opt = opt + '</div>';
		    	    	    	    	    	     }
		    	    	    	    	    	     return opt; 
		    	    	    	    	    	    }
		    	    	    	 }],
		    	    	    	 "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableDraft1").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#clinetDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
        

}

function preparedContractListApplicantDraft(url,tabType){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	  preparedContractApplicantListDraft(url,tabType);
}

function preparedContractApplicantListDraft(url,tabType){
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
		$("#applicantsContractsList").empty();
		$("#applicantsContractsList").append(res); 
		preparedContractApplicantListDraftPagination(url,tabType);
}

function preparedContractApplicantListDraftPagination(url,tabType){
	var colIndex = 0;
    $('#contractListTableDraft2').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [
		   { "data": "contractTitle" }, 
		   { "data": "contractType"}, 
		   { "data": "owner"}, 
		   { "data": "employeeName" }, 
		   { "data": "joinDate" }, 
		   { "data": "billingAmount"}, 
		   { "data": "reqDocCount"}, 
		   { "data": "action"}
            
        ],
        "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, res, meta) {
            var result = "";
            result = result + '<div class="proj_show tblminwidth-150" onclick="PopUp(\''+res.contractUUID+'\',\'DRAFT\');"><span title="'+res.contractTitle+'">'+res.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
             return result;
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, res, meta) {
		         var result = "";
		        result = result + '<div class="tblminwidth-75">'+res.contractType+'</div>';
		         return result; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, res, meta) {
	    	         var result = "";
	    	        result = result + '<div><span title="'+res.ownerName+'">'+res.ownerName+'</span></div>';
	    	         return result; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "employeeName",
	    	    	    "render": function(data, type, res, meta) {
	    	    	         var result = "";
	    	    	        result = result + '<div><span title="'+res.empName+'">'+res.empName+'</span></div>';
	    	    	         return result; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "joinDate",
	    	    	    	     "render": function(data, type, res, meta) {
	    	    	    	    	  var result = "";
	    	    	    	    	 result = result + '<div class="tblminwidth-75">'+res.joinedDateStr+'</div>';
	    	    	    	    	  return result; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "billingAmount",
	    	    	    	    	     "render": function(data, type, res, meta) {
	    	    	    	    	    	  var result = "";
	    	    	    	    	    	 if(res.billingAmount != null && res.billingAmount != '' && res.billingAmount != 'undefined'){
	    	    	    	    	    		 result = result + '<div class="tblminwidth-75">'+res.billingAmount+'</div>';
	    	    	    	  		         }
	    	    	    	  		         else{
	    	    	    	  		        	result = result + '<div class="tblminwidth-75">--</div>'; 
	    	    	    	  		         }
	    	    	    	    	    	 return result; 
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, res, meta) {
	    	    	    	    	    	      var result = "";
	    	    	    	    	    	     if(res.reqDocCount!="0"){
	    	    	    	    	    	    	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+res.contractUUID+'\',\''+res.parentId+'\');">'+res.reqDocCount+'</div>'; 
	    	    	    	    		         }
	    	    	    	    		         else{
	    	    	    	    		        	 result = result + '<div class="tblminwidth-75 ">'+res.reqDocCount+'</div>';
	    	    	    	    		         }	
	    	    	    	    	    	     return result; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "action",
		    	    	    	    	    	    "render": function(data, type, res, meta) {
		    	    	    	    	    	      var result = "";
		    	    	    	    			     	if(res.templateSubmissionStatus == "DRAFT"){
		    	    	    	    			     		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    			     		if(res.typeOfContract == "New"){
		    	    	    	    			     			if(res.templateId != null && res.templateId != '' && res.templateId != 'undefined'){
		    	    	    	    			     				result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+res.contractUUID+'\', \''+res.contractTitle+'\')"></i></a>';
		    	    	    	    			     			}
		    	    	    	    			     		}else{
		    	    	    	    			     			result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewContractPDF(\''+res.fileURL+'\', \''+res.fileName+'\', \''+res.fileType+'\')"></i></a>';
		    	    	    	    			     		}
		    	    	    	    			     		'</sec:authorize>';
		    	    	    	    			     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		    	    	    	    			     		if(res.typeOfContract == "New"){
		    	    	    	    			     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+res.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    			     		}
		    	    	    	    			     		if(res.typeOfContract == "Custom"){
		    	    	    	    			     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+res.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    			     		}
		    	    	    	    			     		'</sec:authorize>';
		    	    	    	    	    	      }else if(res.templateSubmissionStatus == "FINALIZED"){
		    	    	    	    	    	    	  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    	 		         if(res.typeOfContract == "New"){
		    	    	    	    	 		        	result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+res.contractUUID+'\', \''+res.contractTitle+'\')"></i></a></div>';
		    	    	    	    	 		         }else{
		    	    	    	    	 		        	result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewContractPDF(\''+res.fileURL+'\', \''+res.fileName+'\', \''+res.fileType+'\')"></i></a></div>'; 
		    	    	    	    	 		         }
		    	    	    	    	 		         '</sec:authorize>';
		    	    	    	    	    	      }else if(res.templateSubmissionStatus == "EXPIRED"){
		    	    	    	    	    	    	  result = result + '<div class="tbldata-dotdot_80">';
		    	    	    	    	 		         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    	 		         if(res.typeOfContract == "New"){
		    	    	    	    	 		        	result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+res.contractUUID+'\', \''+res.contractTitle+'\')"></i></a>';
		    	    	    	    	 		         }else{
		    	    	    	    	 		        	result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewContractPDF(\''+res.fileURL+'\', \''+res.fileName+'\', \''+res.fileType+'\')"></i></a>';
		    	    	    	    	 		         }
		    	    	    	    	 		         '</sec:authorize>';
		    	    	    	    	 		        result = result + '</div></td>';
		    	    	    	    	    	      }else if(res.templateSubmissionStatus == "REJECTED"){
		    	    	    	    	    	    	  result = result + '<div class="tbldata-dotdot_80">';
		    	    	    	    			     		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    			     		 if(res.typeOfContract == "New"){
		    	    	    	    			     			result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewNewContractPDF(\''+res.contractUUID+'\', \''+res.contractTitle+'\')"></i></a>';
		    	    	    	    			     		 }else{
		    	    	    	    			     			result = result + '<a href="#" title="Preview"><i class="fa fa-eye mr10" onclick="previewContractPDF(\''+res.fileURL+'\', \''+res.fileName+'\', \''+res.fileType+'\')"></i></a>';
		    	    	    	    			     		 }
		    	    	    	    			     		 '</sec:authorize>';
		    	    	    	    			     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		    	    	    	    				     		if(res.typeOfContract == "New"){
		    	    	    	    				     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+res.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    				     		}
		    	    	    	    				     		if(appReport.typeOfContract == "Custom"){
		    	    	    	    				     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+res.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    				     		}
		    	    	    	    		 				'</sec:authorize>';
		    	    	    	    		 				result = result + '</div>';
		    	    	    	    	    	      }
		    	    	    	    	    	     return result; 
		    	    	    	    	    	    }
		    	    	    	   }],
		    	    	    	   
		    	    	    	   "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
// 		    	    	 	 if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
// 		    	    	 	} 
		    	    	 	$("#contractListTableDraft2").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#applicantDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
        
        
	
}

</script>

 

<script>
function preparedContractListVendorWFA(url,tabType){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  }
	  preparedVendorContractListWFA(url,tabType);
}	  
function preparedVendorContractListWFA(url,tabType){	  
// 	 var json = JSON.parse(data);
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
		$("#vendorContractsList").empty();
	    $("#vendorContractsList").append(result);
	    preparedVendorContractListWFAPagination(url,tabType);
}
		
function preparedVendorContractListWFAPagination(url,tabType){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#contractListTableWFA').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "vendorContact" }, 
         		   { "data": "vendor" }, 
         		   { "data": "startDate/endDate"}, 
         		   { "data": "reqDocCount"}, 
         		   { "data": "approverUserName"}, 
         		   { "data": "action"} 
         ], 
         "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, report, meta) {
              var result = "";
              if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'APPROVE\');"><div class="tbldata-dotdot-big inlineBlock text-left" title="'+report.contractTitle+'">'+report.contractTitle+'</div><i class="fa fa-binoculars" style="position: absolute; right: 0; top: 0;"></i></div>';
		      }else{
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" ><div class="tbldata-dotdot-big inlineBlock text-left" title="'+report.contractTitle+'">'+report.contractTitle+'</div></div>';
		        }
             return result; 
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, report, meta) {
		         var result = "";
		         result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         return result; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, report, meta) {
	    	        var result = "";
	    	        result = result + '<div class="tbldata-dotdot-vsm"><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
	    	         return result; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "vendorContact",
	    	    	    "render": function(data, type, report, meta) {
	    	    	         var result = "";
	    	    	         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
	    	    	         return result; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "vendor",
	    	    	    	     "render": function(data, type, report, meta) {
	    	    	    	    	  var result = "";
	    	    	    	    	  if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
	    	    	 		        	 
	    	    	  		        	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_VENDOR\')">';
	    	    	  		        	 result = result + '<div><span title="'+report.contractWith+'"><a href="'+vmsUrl+'/vendorsList?orgRelationUUID='+report.orgRelationUUID+'" title="'+report.contractWith+'">'+report.contractWith+'</a></span></div>';
	    	    	  		        	 '</sec:authorize>';
	    	    	  		        	 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_VENDOR\')">';
	    	    	  		        	 result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
	    	    	  		        	 '</sec:authorize>';
	    	    	 		        	 
	    	    	  		         }
	    	    	    	    	  return result; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "startDate/endDate",
	    	    	    	    	     "render": function(data, type, report, meta) {
	    	    	    	    	    	var result = "";
	    	    	    	    	    	result = result + '<div class="tblminwidth-75">'+report.startDateStr+'</br>';
	    	    	    	 		         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
	    	    	    	 		        	 result = result + report.endDateStr+'</div>';
	    	    	    	 		         }else{
	    	    	    	 		        	 result = result + '--</div>';
	    	    	    	 		         }
	    	    	    	    	    	 return result;
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, report, meta) {
	    	    	    	    	    	      var result = "";
	    	    	    	    	    	      if(report.reqDocCount!="0"){
	    	    	    	    	 		        	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+report.contractUUID+'\',\''+report.parentId+'\');">'+report.reqDocCount+'</div>'; 
	    	    	    	    	    	      }else{
	    	    	    	    	 		        	 result = result + '<div class="tblminwidth-75 ">'+report.reqDocCount+'</div>';
	    	    	    	    	 		         }	
	    	    	    	    	    	     return result; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "approverUserName",
		    	    	    	    	    	    "render": function(data, type, report, meta) {
		    	    	    	    	    	    	var result = "";
		    	    	    	    	    	    	if(report.approverUserName != null && report.approverUserName != "" && report.approverUserName != 'undefined'){
		    	    	    	    	 		        	 result = result + '<div class="tbldata-dotdot_80"><span title="'+report.approverUserName+'">'+report.approverUserName+'</span></div>';
		    	    	    	    	 		         }else{
		    	    	    	    	 		        	 result = result + '<div><span>---</span></div>';
		    	    	    	    	 		         }
		    	    	    	    	    	     
		    	    	    	    	    	     return result;
		    	    	    	    	    	    }
		    	    	    	    	    	    },{
		    	    	    	    	    	    	"targets": colIndex++,
			    	    	    	    	    	    "data": "action",
			    	    	    	    	    	    "render": function(data, type, report, meta) {
			    	    	    	    	    	    	var result = "";
			    	    	    	    	    	    	result = result + '<div class="tbldata-dotdot-vsm">';
			    	    	    	    	 	        	  if(report.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
			    	    	    	    	 	        		  if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == report.approverUserId){
			    	    	    	    	 		      	        	 result = result +' <div><div class="CurDiv" id="removeStatus'+report.id+'"><i class="fa fa-check" title="Approve" alt="Approve" onclick="approveContract('+report.id+',\'WAITING_FOR_APPROVER\',\'Vendor\');"></i>|<i class="fa fa-times" title="Reject" alt="Rejected" onclick="rejectedContractReason('+report.id+',\'REJECTED\',\'Vendor\');"></i></div></div>';
			    	    	    	    	 		      	         }
			    	    	    	    	 	        	  }else if(report.templateSubmissionStatus == "WAITING_TO_SEND" && (report.isOwner == "Y" || '${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN")){
			    	    	    	    	 	        		  result = result + '<a title="Send Contract"><i class="fa fa-share-square" onclick="sendContractFromOwner(\''+report.contractUUID+'\',\'Vendor\');"></i></a>';
			    	    	    	    	 	        	  }
			    	    	    	    		        		  
			    	    	    	    	 	      	         '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
			    	    	    	    	 	      	     		if(report.typeOfContract == "New"){
			    	    	    	    	 	      	     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+report.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
			    	    	    	    	 	      	     		}
			    	    	    	    	 	      	     		if(report.typeOfContract == "Custom"){
			    	    	    	    	 	      	     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+report.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
			    	    	    	    	 	      	     		}
			    	    	    	    	 	        			'</sec:authorize>';
			    	    	    	    	 	        			'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
			    	    	    	    	 	        			if(report.typeOfContract  == "New"){
			    	    	    	    	 	        				result = result + '|<a href="#" title="Preview"><i class="fa fa-eye paddingLeft" onclick="previewNewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a>';
			    	    	    	    	 	        			}else{
			    	    	    	    	 	        			result = result + '|<a href="#" title="Preview"><i class="fa fa-eye paddingLeft" onclick="previewContractPDF(\''+report.fileURL+'\', \''+report.fileName+'\', \''+report.fileType+'\')"></i></a>';
			    	    	    	    	 	        			}
			    	    	    	    	 	        			'</sec:authorize>';
			    	    	    	    		        			
			    	    	    	    		        	  
			    	    	    	    		        	  
			    	    	    	    	 		     		 //result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.contractUUID+'\')"></i></a></div>';
			    	    	    	    			     	  
			    	    	    	    	 		         result = result + '</div></td>';
			    	    	    	    	    	     
			    	    	    	    	    	     return result;
			    	    	    	    	    	    }  
		    	    	    	    	    	    
		    	    	    	 }],
		    	    	    	 "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableWFA").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#vendorDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
	
	
}

function preparedContractListClientWFA(url,tabType){
		var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
		$("#vendorContractsList").empty();
	    $("#clientContractsList").empty();
	    $("#applicantsContractsList").empty();
	    var locationList ='${SourceLeadWeb_dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  locationDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  locationDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  locationDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  locationDate = 'YYYY-DD-MM'; 
		  }
		  preparedClientContractListWFA(url,tabType);
	}	  	
	
function preparedClientContractListWFA(url,tabType){
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
	$("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
    preparedClientContractListWFAPagination(url,tabType)
}
	
function preparedClientContractListWFAPagination(url,tabType){	
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#contractListTableWFA1').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "clientContact" }, 
         		   { "data": "client" }, 
         		   { "data": "startDate/endDate"}, 
         		   { "data": "reqDocCount"}, 
         		   { "data": "approverUserName"},
         		   { "data": "action"} 
         		],
         		   
        "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, output, meta) {
            var opt = "";
            if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="proj_show tblminwidth-175" onclick="PopUp(\''+output.contractUUID+'\',\'APPROVE\');"><div class="tbldata-dotdot-big inlineBlock text-left" title="'+output.contractTitle+'">'+output.contractTitle+'</div><i class="fa fa-binoculars" style="position: absolute; right: 0; top: 0;"></i></div>';
	        }else{
	        	opt = opt + '<div class="proj_show tblminwidth-175" ><div class="tbldata-dotdot-big inlineBlock text-left" title="'+output.contractTitle+'">'+output.contractTitle+'</div></div>';
	        }
             return opt;
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, output, meta) {
		        var opt = "";
		        opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
		         return opt; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, output, meta) {
	    	         var opt = "";
	    	         opt = opt + '<div><span title="'+output.ownerName+'">'+output.ownerName+'</span></div>';
	    	         return opt; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "clientContact",
	    	    	    "render": function(data, type, output, meta) {
	    	    	         var opt = "";
	    	    	         opt = opt + '<div><span title="'+output.vendorContactName+'">'+output.vendorContactName+'</span></div>';
	    	    	         return opt; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "client",
	    	    	    	     "render": function(data, type, output, meta) {
	    	    	    	    	  var opt = "";
	    	    	    	    	  if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	    	    	    		        	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    	    		        	 opt = opt + '<div><span title="'+output.contractWith+'"><a href="'+vmsUrl+'/clientDashboard?orgRelationUUID='+output.orgRelationUUID+'" title="'+output.contractWith+'">'+output.contractWith+'</a></span></div>';
	    	    	    		        	 '</sec:authorize>';
	    	    	    		        	 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    	    		        	 opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	    	    	    		        	 '</sec:authorize>';
	    	    	    		         }
	    	    	    	    	  return opt; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "startDate/endDate",
	    	    	    	    	     "render": function(data, type, output, meta) {
	    	    	    	    	    	  var opt = "";
	    	    	    	    	    	  opt = opt + '<div class="tblminwidth-75">'+output.startDateStr+'</br>';
	    	    	    	    		         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	    	    	    	    		        	 opt = opt +output.endDateStr+'</div>';
	    	    	    	    		         }else{
	    	    	    	    		        	 opt = opt + '--</div>';
	    	    	    	    		         }
	    	    	    	    	    	 return opt; 
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, output, meta) {
	    	    	    	    	    	      var opt = "";
	    	    	    	    	    	      if(output.reqDocCount!="0"){
	    	    	    	    	  	         	opt = opt + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+output.contractUUID+'\',\''+output.parentId+'\');">'+output.reqDocCount+'</div>';
	    	    	    	    	  	         }
	    	    	    	    	  	         else{
	    	    	    	    	  	        	 opt = opt + '<div class="tblminwidth-75 ">'+output.reqDocCount+'</div>';
	    	    	    	    	  	         }	
	    	    	    	    	    	     return opt; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
		    	    	    	    	    	    "targets": colIndex++,
		    	    	    	    	    	    "data": "approverUserName",
		    	    	    	    	    	    "render": function(data, type, output, meta) {
		    	    	    	    	    	      var opt = "";
		    	    	    	    	    	      if(output.approverUserName != null && output.approverUserName != "" && output.approverUserName != 'undefined'){
		    	    	    	    	 	        	 opt = opt + '<div><span title="'+output.approverUserName+'">'+output.approverUserName+'</span></div>';
		    	    	    	    	 	         }else{
		    	    	    	    	 	        	 opt = opt + '<div><span>---</span></div>';
		    	    	    	    	 	         }
		    	    	    	    	    	     return opt; 
		    	    	    	    	    	    }
		    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "action",
		    	    	    	    	    	    "render": function(data, type, output, meta) {
		    	    	    	    	    	      var opt = "";
		    	    	    	    	    	      opt = opt + '<div class="tbldata-dotdot-vsm">';
		    	    	    	    	        	  if(output.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
		    	    	    	    	        		  if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_userId}' == output.approverUserId){
		    	    	    	    	      	        	 opt = opt +' <div><div class="CurDiv" id="removeStatus'+output.id+'"><i class="fa fa-check" title="Approve" alt="Approve" onclick="approveContract('+output.id+',\'WAITING_FOR_APPROVER\',\'Client\');"></i>|<i class="fa fa-times" title="Reject" alt="Rejected" onclick="rejectedContractReason('+output.id+',\'REJECTED\',\'Client\');"></i></div></div>';
		    	    	    	    	      	         }
		    	    	    	    	        	  }else if(output.templateSubmissionStatus == "WAITING_TO_SEND" && (output.isOwner == "Y" || '${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN")){
		    	    	    	    	        		  opt = opt + '<a title="Send Contract"><i class="fa fa-share-square" onclick="sendContractFromOwner(\''+output.contractUUID+'\',\'Client\');"></i></a>';
		    	    	    	    	        	  }
		    	    	    	    	        		 

		    	    	    	    	     	         '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		    	    	    	    	     	     		if(output.typeOfContract == "New" && output.templateSubmissionStatus != "WAITING_TO_SEND"){
		    	    	    	    	     	     			opt = opt + '| <a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+output.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    	     	     		}
		    	    	    	    	     	     		if(output.typeOfContract == "Custom" && output.templateSubmissionStatus != "WAITING_TO_SEND"){
		    	    	    	    	     	     			opt = opt + '| <a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+output.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    	     	     		}
		    	    	    	    	       			'</sec:authorize>';
		    	    	    	    	       			'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    	       			if(output.typeOfContract == "New" || output.typeOfContract == "Custom"){
		    	    	    	    	       				opt = opt + '|<a href="#" title="Preview"><i class="fa fa-eye paddingLeft" onclick="previewNewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a>';
		    	    	    	    	       			}else{
		    	    	    	    	       			opt = opt + '|<a href="#" title="Preview"><i class="fa fa-eye paddingLeft" onclick="previewContractPDF(\''+output.fileURL+'\', \''+output.fileName+'\', \''+output.fileType+'\')"></i></a>';
		    	    	    	    	       			}
		    	    	    	    	       			'</sec:authorize>';
		    	    	    	    	        	 
		    	    	    	    		     	 
		    	    	    	    		     		opt = opt + '</div></td>';
		    	    	    	    	    	     return opt; 
		    	    	    	    	    	    }
		    	    	    	 }],
		    	    	    	 "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableWFA1").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#clinetDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
	
	
}
	
function preparedContractListApplicantWFA(url,tabType){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  }
	  preparedApplicantContractListWFA(url,tabType);
}	  		
	
function preparedApplicantContractListWFA(url,tabType){
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
		$("#applicantsContractsList").empty();
	    $("#applicantsContractsList").append(res);
	    preparedApplicantContractListWFAPagination(url,tabType)
}

function preparedApplicantContractListWFAPagination(url,tabType){
	var colIndex = 0;
    $('#contractListTableWTA2').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "employeeName" }, 
         		   { "data": "joinDate" }, 
         		   { "data": "billingAmount"}, 
         		   { "data": "reqDocCount"},
         		   { "data": "approverUserName"},
         		   { "data": "action"} 
         		   ], 
         		   
        "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, res, meta) {
            var result = "";
            result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+res.contractUUID+'\',\'APPROVE\');"><span title="'+res.contractTitle+'">'+res.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
             return result;
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, res, meta) {
		         var result = "";
		         result = result + '<div class="tblminwidth-75">'+res.contractType+'</div>';
		         return result; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, res, meta) {
	    	         var result = "";
	    	         result = result + '<div><span title="'+res.ownerName+'">'+res.ownerName+'</span></div>';
	    	         return result; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "employeeName",
	    	    	    "render": function(data, type, res, meta) {
	    	    	         var result = "";
	    	    	         result = result + '<div><span title="'+res.empName+'">'+res.empName+'</span></div>';
	    	    	         return result; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "joinDate",
	    	    	    	     "render": function(data, type, res, meta) {
	    	    	    	    	  var result = "";
	    	    	    	    	  result = result + '<div class="tblminwidth-75">'+res.joinedDateStr+'</div>';
	    	    	    	    	  return result; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "billingAmount",
	    	    	    	    	     "render": function(data, type, res, meta) {
	    	    	    	    	    	  var result = "";
	    	    	    	    	    	  if(res.billingAmount != null && res.billingAmount != '' && res.billingAmount != 'undefined'){
	    	    	    	    	    		  result = result + '<div class="tblminwidth-75">'+res.billingAmount+'</div>';
	    	    	    	   		         }
	    	    	    	   		         else{
	    	    	    	   		        	result = result + '<div class="tblminwidth-75">--</div>'; 
	    	    	    	   		         }
	    	    	    	    	    	 return result; 
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, res, meta) {
	    	    	    	    	    	      var result = "";
	    	    	    	    	    	      if(res.reqDocCount!="0"){
	    	    	    	    	    	    	  result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+res.contractUUID+'\',\''+res.parentId+'\');">'+res.reqDocCount+'</div>'; 
	    	    	    	    			         }
	    	    	    	    			         else{
	    	    	    	    			        	 result = result + '<div class="tblminwidth-75 ">'+res.reqDocCount+'</div>';
	    	    	    	    			         }	 
	    	    	    	    	    	     return result; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
		    	    	    	    	    	    "targets": colIndex++,
		    	    	    	    	    	    "data": "approverUserName",
		    	    	    	    	    	    "render": function(data, type, res, meta) {
		    	    	    	    	    	      var result = "";
		    	    	    	    	    	      result = result + '<div><span title="'+res.approverUserName+'">'+res.approverUserName+'</span></div>';	 
		    	    	    	    	    	     return result; 
		    	    	    	    	    	    }
		    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "action",
		    	    	    	    	    	    "render": function(data, type, res, meta) {
		    	    	    	    	    	      var result = "";
		    	    	    	    	    	      result = result + '<div class="tbldata-dotdot_80">';
		    	    	    	    			     	if(res.templateSubmissionStatus == "WAITING_FOR_APPROVER"){
		    	    	    	    						if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_userId}' == res.approverUserId){
		    	    	    	    							result = result +' <div><div class="CurDiv" id="removeStatus'+res.id+'"><i class="fa fa-check" title="Approve" alt="Approve" onclick="approveContract('+res.id+',\'WAITING_FOR_APPROVER\',\'Full time offer\');"></i>|<i class="fa fa-times" title="Reject" alt="Rejected" onclick="rejectedContractReason('+res.id+',\'REJECTED\',\'Full time offer\');"></i></div></div>';
		    	    	    	    						}
		    	    	    	    			     	 }else if(res.templateSubmissionStatus == "WAITING_TO_SEND" && (res.isOwner == "Y" || '${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_userId}' == res.approverUserId)){
		    	    	    	    	 	        		  result = result + '<a title="Send Contract"><i class="fa fa-share-square" onclick="sendContractFromOwner(\''+res.contractUUID+'\',\'Full time offer\');"></i></a>';
		    	    	    	    	 	        	  }
		    	    	    	    			     		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    			     		if(res.typeOfContract == "New"){
		    	    	    	    			     			result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewNewContractPDF(\''+res.contractUUID+'\', \''+res.contractTitle+'\')"></i></a>';
		    	    	    	    			     		}else{
		    	    	    	    			     			result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+res.fileURL+'\', \''+res.fileName+'\', \''+res.fileType+'\')"></i></a>';
		    	    	    	    			     		}
		    	    	    	    			     		'</sec:authorize>';
		    	    	    	    			     		'<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		    	    	    	    			     		if(res.typeOfContract == "New"){
		    	    	    	    			     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+res.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    			     		}
		    	    	    	    			     		if(res.typeOfContract == "Custom"){
		    	    	    	    			     			result = result + '| <a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+res.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    			     		}
		    	    	    	    			     		'</sec:authorize>';
		    	    	    	    			     		//res = res + '</div>';
		    	    	    	    			     	result = result + '</div></td>';
		    	    	    	    	    	     return result; 
		    	    	    	    	    	    }
		    	    	    	   }],
		    	    	    	   
		    	    	    	   "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableWTA2").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#applicantDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
	
	
	
}
</script>

<script>
function preparedContractListVendorWTS(url,tabType){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  }
	  preparedVendorContractListWTS(url,tabType);
}

function preparedVendorContractListWTS(url,tabType){
// 	 var json = JSON.parse(data);
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
		$("#vendorContractsList").empty();
	    $("#vendorContractsList").append(result);
		preparedVendorContractListWTSPagination(url,tabType);
		
}
		
function preparedVendorContractListWTSPagination(url,tabType){	
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#contractListTableWTS').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "vendorContact" }, 
         		   { "data": "vendor" }, 
         		   { "data": "startDate/endDate"}, 
         		   { "data": "reqDocCount"}, 
         		   { "data": "templateSubmissionStatus"}, 
         		   { "data": "action"} 
         ],
         
         "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, report, meta) {
              var result = "";
//               if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
		        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'SIGN\');"><div class="tbldata-dotdot-big inlineBlock text-left" title="'+report.contractTitle+'">'+report.contractTitle+'</div><i class="fa fa-binoculars" style="position: absolute; right: 0; top: 0;"></i></div>';
//		        }else{
//		        	result = result + '<div class="tbldata-dotdot-sm proj_show" ><span title="'+report.contractTitle+'">'+report.contractTitle+'</span></div>';
//		        }
             return result; 
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, report, meta) {
		         var result = "";
		         result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         return result; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, report, meta) {
	    	        var result = "";
	    	        result = result + '<div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
	    	         return result; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "vendorContact",
	    	    	    "render": function(data, type, report, meta) {
	    	    	         var result = "";
	    	    	         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
	    	    	         return result; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "vendor",
	    	    	    	     "render": function(data, type, report, meta) {
	    	    	    	    	  var result = "";
	    	    	    	    	  if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
	    	    	 		        	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_VENDOR\')">';
	    	    	 		        	   result = result + '<div><span title="'+report.contractWith+'"><a href="'+vmsUrl+'/vendorsList?orgRelationUUID='+report.orgRelationUUID+'" title="'+report.contractWith+'">'+report.contractWith+'</a></span></div>';
	    	    	 		        	 '</sec:authorize>';
	    	    	 		        	 
	    	    	 		        	 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_VENDOR\')">';
	    	    	 		        	   result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
	    	    	 		        	 '</sec:authorize>';
	    	    	 		        	 
	    	    	 		         }
	    	    	    	    	  return result; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "startDate/endDate",
	    	    	    	    	     "render": function(data, type, report, meta) {
	    	    	    	    	    	var result = "";
	    	    	    	    	    	result = result + '<div class="tblminwidth-150">'+report.startDateStr+'';
	    	    	    			         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
	    	    	    			        	 result = result +'</br>'+ report.endDateStr+'</div>';
	    	    	    			         }
	    	    	    	    	    	 return result;
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, report, meta) {
	    	    	    	    	    	      var result = "";
	    	    	    	    	    	      if(report.reqDocCount!="0"){
	    	    	    	    			        	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+report.contractUUID+'\',\''+report.parentId+'\');">'+report.reqDocCount+'</div>'; 
	    	    	    	    			         }
	    	    	    	    			         else{
	    	    	    	    			        	 result = result + '<div class="tblminwidth-75 ">'+report.reqDocCount+'</div>';
	    	    	    	    			         }		
	    	    	    	    	    	     return result; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "templateSubmissionStatus",
		    	    	    	    	    	    "render": function(data, type, report, meta) {
		    	    	    	    	    	    	var result = "";
		    	    	    	    	    	    	result = result + '<div><span>'+report.templateSubmissionStatus+'</span></div>';
		    	    	    	    	    	     return result;
		    	    	    	    	    	    }
		    	    	    	    	    	    },{
		    	    	    	    	    	    	"targets": colIndex++,
			    	    	    	    	    	    "data": "action",
			    	    	    	    	    	    "render": function(data, type, report, meta) {
			    	    	    	    	    	    	var result = "";
			    	    	    	    	    	    	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
			    	    	    	    			         result += '<div class="tbldata-dotdot_80">';
			    	    	    	    			         if((report.typeOfContract == "New" || report.typeOfContract == "Custom") && (null != report.parentContractId && '' != report.parentContractId && 'undefined' != report.parentContractId)){
			    	    	    	    			        	 result = result + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewNewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a><a href="${SourceLeadWeb_vmsUrl}/docusign?contractUUID='+report.contractUUID+'" title="Signature"><i class="fa fa-pencil-square-o"></i></a>';
			    	    	    	    			        	 result += ' <a title="Revise Contract" onclick="reviseContract(\''+report.contractUUID+'\')"><i class="fa fa-retweet"></i></a>';
			    	    	    	    			         }else if(report.typeOfContract == "Existing"){
			    	    	    	    			         result = result + ' <a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.fileURL+'\', \''+report.fileName+'\', \''+report.fileType+'\')"></i></a>';
			    	    	    	    			         }else{
			    	    	    	    			        	 result = result + ' <a href="#" title="Preview"><i class="fa fa-eye" onclick="previewNewContractPDF(\''+report.contractUUID+'\', \''+report.contractTitle+'\')"></i></a>';
			    	    	    	    			         }
			    	    	    	    			         
			    	    	    	    			         '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
			    	    	    	    			         if(report.templateSubmissionStatus == "PENDING"){
			    	    	    	    			        	 if(report.typeOfContract == "New"){
			    	    	    	    			  	     			result = result + '<a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+report.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
			    	    	    	    			  	     		}
			    	    	    	    			  	     		if(report.typeOfContract == "Custom"){
			    	    	    	    			  	     			result = result + '<a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+report.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
			    	    	    	    			  	     		}
			    	    	    	    			         }
			    	    	    	    	    			'</sec:authorize>';
			    	    	    	    			         
			    	    	    	    			         if(report.revisedReason != null && report.revisedReason != '' && report.revisedReason != 'undefined'){
			    	    	    	    			        	 result += ' <a><i class="fa fa-commenting-o edit-reason" aria-hidden="true"><div class="edit-status edit-status-top"><p><font color="red">Revise request reason</font>: <br>'+report.revisedReason+'</p></div></i></a>'; 
			    	    	    	    			         }
			    	    	    	    			         result += '</div>';
			    	    	    	    			         '</sec:authorize>';
			    	    	    	    	    	     
			    	    	    	    	    	     return result;
			    	    	    	    	    	    }  
		    	    	    	    	    	    
		    	    	    	 }],
		    	    	    	 "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableWTS").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#vendorDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
	
}
	
function preparedContractListClientWTS(url,tabType){
		var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
		$("#vendorContractsList").empty();
	    $("#clientContractsList").empty();
	    $("#applicantsContractsList").empty();
	    var locationList ='${SourceLeadWeb_dateFormat}';	
		  if(locationList.match("yyyy-MM-dd")){
			  locationDate = 'YYYY-MM-DD';
		  }if(locationList.match("dd-MM-yyyy")){
			  locationDate = 'DD-MM-YYYY'; 
		  }if(locationList.match("MM-dd-yyyy")){
			  locationDate = 'MM-DD-YYYY'; 
		  }if(locationList.match("yyyy-dd-MM")){
			  locationDate = 'YYYY-DD-MM'; 
		  }
		  preparedClientContractListWTS(url,tabType);
	}
	
function preparedClientContractListWTS(url,tabType){	
	var opt='';
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
	$("#clientContractsList").empty();
    $("#clientContractsList").append(opt);
	preparedClientContractListWTSPagination(url,tabType);
}

function preparedClientContractListWTSPagination(url,tabType){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#contractListTableWTS1').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "clientContact" }, 
         		   { "data": "client" }, 
         		   { "data": "startDate/endDate"}, 
         		   { "data": "reqDocCount"}, 
         		   { "data": "templateSubmissionStatus"},
         		   { "data": "action"} 
         ], 
         		   
        "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, output, meta) {
            var opt = "";
// 	        if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+output.contractUUID+'\',\'SIGN\');"><div class="tbldata-dotdot-big inlineBlock text-left" title="'+output.contractTitle+'">'+output.contractTitle+'</div><i class="fa fa-binoculars" style="position: absolute; right: 0; top: 0;"></i></div>';
//	        }else{
//	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show"><span title="'+output.contractTitle+'">'+output.contractTitle+'</span></div>';
//	        }
             return opt;
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, output, meta) {
		        var opt = "";
		         opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
		         return opt; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, output, meta) {
	    	         var opt = "";
	    	         opt = opt + '<div><span title="'+output.ownerName+'">'+output.ownerName+'</span></div>';
	    	         return opt; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "clientContact",
	    	    	    "render": function(data, type, output, meta) {
	    	    	         var opt = "";
	    	    	         opt = opt + '<div><span title="'+output.vendorContactName+'" > '+output.vendorContactName+' </span></div>';
	    	    	         return opt; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "client",
	    	    	    	     "render": function(data, type, output, meta) {
	    	    	    	    	  var opt = "";
	    	    	    	    	  if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	    	    	    		        	
	    	    	    		        	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    	    		        	 	opt = opt + '<div><span title="'+output.contractWith+'"><a href="'+vmsUrl+'/clientDashboard?orgRelationUUID='+output.orgRelationUUID+'" title="'+output.contractWith+'">'+output.contractWith+' </a></span></div>';
	    	    	    		        	 '</sec:authorize>';
	    	    	    		        	 
	    	    	    		        	 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    	    		        	 	opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+' </span></div>';
	    	    	    		        	 '</sec:authorize>';
	    	    	    		        	 
	    	    	    		         }
	    	    	    	    	  return opt; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "startDate/endDate",
	    	    	    	    	     "render": function(data, type, output, meta) {
	    	    	    	    	    	  var opt = "";
	    	    	    	    	    	  opt = opt + '<div class="tblminwidth-150">'+output.startDateStr+'';
	    	    	    	    		         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	    	    	    	    		        	 opt = opt +'</br>'+output.endDateStr+'</div>';
	    	    	    	    		         }
	    	    	    	    	    	 return opt; 
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, output, meta) {
	    	    	    	    	    	      var opt = "";
	    	    	    	    	    	      if(output.reqDocCount!="0"){
	    	    	    	    	  	         	opt = opt + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+output.contractUUID+'\',\''+output.parentId+'\');">'+output.reqDocCount+'</div>';
	    	    	    	    	  	         }
	    	    	    	    	  	         else{
	    	    	    	    	  	        	 opt = opt + '<div class="tblminwidth-75 ">'+output.reqDocCount+'</div>';
	    	    	    	    	  	         }	
	    	    	    	    	    	     return opt; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
		    	    	    	    	    	    "targets": colIndex++,
		    	    	    	    	    	    "data": "templateSubmissionStatus",
		    	    	    	    	    	    "render": function(data, type, output, meta) {
		    	    	    	    	    	      var opt = "";
		    	    	    	    	    	      opt = opt + '<div><span>'+output.templateSubmissionStatus+'</span></div>';
		    	    	    	    	    	      return opt;
		    	    	    	    	    	    }
		    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "action",
		    	    	    	    	    	    "render": function(data, type, output, meta) {
		    	    	    	    	    	      var opt = "";
		    	    	    	    	    	      '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    	 	         opt = opt + '<div class="tbldata-dotdot_80">';
		    	    	    	    	 	         if((output.typeOfContract == "New" || output.typeOfContract == "Custom") && (null != output.parentContractId && '' != output.parentContractId && 'undefined' != output.parentContractId)){
		    	    	    	    	 	        	 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewNewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a><a href="${SourceLeadWeb_vmsUrl}/docusign?contractUUID='+output.contractUUID+'" title="Signature"><i class="fa fa-pencil-square-o"></i></a>'; 
		    	    	    	    	 	        	 opt += '<a title="Revise Contract" onclick="reviseContract(\''+output.contractUUID+'\')"><i class="fa fa-retweet"></i></a>';
		    	    	    	    	 	         }else if(output.typeOfContract == "Existing"){
		    	    	    	    	 	        	 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.fileURL+'\', \''+output.fileName+'\', \''+output.fileType+'\')"></i></a>';
		    	    	    	    	 	         }else{
		    	    	    	    	 	        	 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewNewContractPDF(\''+output.contractUUID+'\', \''+output.contractTitle+'\')"></i></a>';
		    	    	    	    	 	         }
		    	    	    	    	 	         
		    	    	    	    	 	         '<sec:authorize access="hasAnyRole(\'ROLE_EDIT_CONTRACT\')">';
		    	    	    	    	 	         if(output.templateSubmissionStatus == "PENDING"){
		    	    	    	    	 	        	 if(output.typeOfContract == "New"){
		    	    	    	    	 		     			opt = opt + '<a href="${SourceLeadWeb_vmsUrl}/addNewContract?contractUUID='+output.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    	 		     		}
		    	    	    	    	 		     		if(output.typeOfContract == "Custom"){
		    	    	    	    	 		     			opt = opt + '<a href="${SourceLeadWeb_vmsUrl}/addCustomContract?contractUUID='+output.contractUUID+'" title="Edit"><i class="fa fa-pencil editAA"></i></a>';
		    	    	    	    	 		     		}
		    	    	    	    	 	         }
		    	    	    	    	  			'</sec:authorize>';
		    	    	    	    	 	         
		    	    	    	    	 	         if(output.revisedReason != null && output.revisedReason != '' && output.revisedReason != 'undefined'){
		    	    	    	    	 	        	 opt += '<a><i class="fa fa-commenting-o" aria-hidden="true"><div class="edit-status"><p><font color="red">Revise request reason</font>: <br>'+output.revisedReason+'</p></div></i></a>'; 
		    	    	    	    	 	         }
		    	    	    	    	 	         opt = opt + '</div>';
		    	    	    	    	      		'</sec:authorize>';
		    	    	    	    	    	  return opt; 
		    	    	    	    	    	  }
		    	    	    	 }],
		    	    	    	 "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableWTS1").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#clinetDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
	
	
}

function preparedContractListApplicantWTS(url,tabType){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  }
	  preparedApplicantContractListWTS(url,tabType);
}
	
function preparedApplicantContractListWTS(url,tabType){	
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
		$("#applicantsContractsList").empty();
	    $("#applicantsContractsList").append(res);
		preparedApplicantContractListWTSPagination(url,tabType);
}

function preparedApplicantContractListWTSPagination(url,tabType){
	var colIndex = 0;
    $('#contractListTableWTS2').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "employeeName" }, 
         		   { "data": "joinDate" }, 
         		   { "data": "billingAmount"}, 
         		   { "data": "reqDocCount"},
         		   { "data": "templateSubmissionStatus"},
         		   { "data": "action"} 
         		   ], 
         		   
        "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, res, meta) {
            var result = "";
            result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+res.contractUUID+'\',\'SIGN\');"><span title="'+res.contractTitle+'">'+res.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
             return result;
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, res, meta) {
		         var result = "";
		         result = result + '<div class="tblminwidth-75">'+res.contractType+'</div>';
		         return result; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, res, meta) {
	    	         var result = "";
	    	         result = result + '<div><span title="'+res.ownerName+'">'+res.ownerName+'</span></div>';
	    	         return result; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "employeeName",
	    	    	    "render": function(data, type, res, meta) {
	    	    	         var result = "";
	    	    	         result = result + '<div><span title="'+res.empName+'">'+res.empName+'</span></div>';
	    	    	         return result; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "joinDate",
	    	    	    	     "render": function(data, type, res, meta) {
	    	    	    	    	  var result = "";
	    	    	    	    	  result = result + '<div class="tblminwidth-75">'+res.joinedDateStr+'</div>';
	    	    	    	    	  return result; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "billingAmount",
	    	    	    	    	     "render": function(data, type, res, meta) {
	    	    	    	    	    	  var result = "";
	    	    	    	    	    	  if(res.billingAmount != null && res.billingAmount != '' && res.billingAmount != 'undefined'){
	    	    	    	    	    		  result = result + '<div class="tblminwidth-75">'+res.billingAmount+'</div>';
	    	    	    	   		         }
	    	    	    	   		         else{
	    	    	    	   		        	result = result + '<div class="tblminwidth-75">--</div>'; 
	    	    	    	   		         }
	    	    	    	    	    	 return result; 
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, res, meta) {
	    	    	    	    	    	      var result = "";
	    	    	    	    	    	      if(res.reqDocCount!="0"){
	    	    	    	    	    	    	  result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+res.contractUUID+'\',\''+res.parentId+'\');">'+res.reqDocCount+'</div>'; 
	    	    	    	    			         }
	    	    	    	    			         else{
	    	    	    	    			        	 result = result + '<div class="tblminwidth-75 ">'+res.reqDocCount+'</div>';
	    	    	    	    			         }		 
	    	    	    	    	    	     return result; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
		    	    	    	    	    	    "targets": colIndex++,
		    	    	    	    	    	    "data": "templateSubmissionStatus",
		    	    	    	    	    	    "render": function(data, type, res, meta) {
		    	    	    	    	    	      var result = "";
		    	    	    	    	    	      result = result + '<div><span title="'+res.templateSubmissionStatus+'">'+res.templateSubmissionStatus+'</span></div>';	 
		    	    	    	    	    	     return result; 
		    	    	    	    	    	    }
		    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "action",
		    	    	    	    	    	    "render": function(data, type, res, meta) {
		    	    	    	    	    	      var result = "";
		    	    	    	    	    	      '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    			         if(res.typeOfContract == "New"){
		    	    	    	    			        	 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewNewContractPDF(\''+res.contractUUID+'\', \''+res.contractTitle+'\')"></i></a></div>'; 
		    	    	    	    			         }else{
		    	    	    	    			        	 result = result + '<div class="tbldata-dotdot_80"><a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+res.fileURL+'\', \''+res.fileName+'\', \''+res.fileType+'\')"></i></a></div>';
		    	    	    	    			         }
		    	    	    	    			         '</sec:authorize>';
		    	    	    	    	    	     return result; 
		    	    	    	    	    	    }
		    	    	    	   }],
		    	    	    	   
		    	    	    	   "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableWTS2").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#applicantDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
	
	
}
</script>


<script>
function preparedContractListVendorWTV(url,tabType){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	  preparedVendorContractListWTV(url,tabType);
}

function preparedVendorContractListWTV(url,tabType){
// 	 var json = JSON.parse(data);
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
		$("#vendorContractsList").empty();
	    $("#vendorContractsList").append(result);
		preparedVendorContractListWTVPagination(url,tabType);
}

function preparedVendorContractListWTVPagination(url,tabType){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#contractListTableWTV').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "vendorContact" }, 
         		   { "data": "vendor" }, 
         		   { "data": "startDate/endDate"}, 
         		   { "data": "reqDocCount"}, 
         		   { "data": "billingCycle"}, 
         		   { "data": "templateSubmissionStatus"}, 
         		   { "data": "action"} 
         ], 
         
         "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, report, meta) {
              var result = "";
              if(null == report.parentContractId || '' == report.parentContractId || 'undefined' == report.parentContractId){
	        	result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+report.contractUUID+'\',\'VERIFY\');"><div class="tbldata-dotdot-big inlineBlock text-left" title="'+report.contractTitle+'">'+report.contractTitle+'</div><i class="fa fa-binoculars" style="position: absolute; right: 0; top: 0;"></i></div>';
            }else{
        	result = result + '<div class="tbldata-dotdot-sm proj_show"><div class="tbldata-dotdot-big inlineBlock text-left" title="'+report.contractTitle+'">'+report.contractTitle+'</div></div>';
            }
             return result; 
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, report, meta) {
		         var result = "";
		         result = result + '<div class="tblminwidth-75">'+report.contractType+'</div>';
		         return result; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, report, meta) {
	    	        var result = "";
	    	        result = result + '<div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div>';
	    	         return result; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "vendorContact",
	    	    	    "render": function(data, type, report, meta) {
	    	    	         var result = "";
	    	    	         result = result + '<div><span title="'+report.vendorContactName+'">'+report.vendorContactName+'</span></div>';
	    	    	         return result; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "vendor",
	    	    	    	     "render": function(data, type, report, meta) {
	    	    	    	    	  var result = "";
	    	    	    	    	  if(report.contractWith != null && report.contractWith != '' && report.contractWith != 'undefined'){
	    	    	 		        	 if(report.orgRelationUUID != null && report.orgRelationUUID != '' && report.orgRelationUUID != 'undefined'){
	    	    	 		        	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_VENDOR\')">';
	    	    	 		        	   result = result + '<div><span title="'+report.contractWith+'"><a href="'+vmsUrl+'/vendorsList?orgRelationUUID='+report.orgRelationUUID+'" title="'+report.contractWith+'">'+report.contractWith+'</a></span></div>';
	    	    	 		        	 '</sec:authorize>';
	    	    	 		        	 }else{
	    	    	 	        		 '<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_VENDOR\')">';
	    	    	 		        	   result = result + '<div><span title="'+report.contractWith+'">'+report.contractWith+'</span></div>';
	    	    	 		        	 '</sec:authorize>';
	    	    	 		        	 }
	    	    	 		         }
	    	    	    	    	  return result; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "startDate/endDate",
	    	    	    	    	     "render": function(data, type, report, meta) {
	    	    	    	    	    	var result = "";
	    	    	    	    	    	result = result + '<div class="tblminwidth-150">'+report.startDateStr+'';
	    	    	    			         if(report.endDateStr != null && report.endDateStr != '' && report.endDateStr != 'undefined'){
	    	    	    			        	 result = result +'</br>'+ report.endDateStr+'</div>';
	    	    	    			         }
	    	    	    	    	    	 return result;
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, report, meta) {
	    	    	    	    	    	      var result = "";
	    	    	    	    	    	      if(report.reqDocCount!="0"){
	    	    	    	    			        	 result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+report.contractUUID+'\',\''+report.parentId+'\');">'+report.reqDocCount+'</div>'; 
	    	    	    	    			         }
	    	    	    	    			         else{
	    	    	    	    			        	 result = result + '<div class="tblminwidth-75 ">'+report.reqDocCount+'</div>';
	    	    	    	    			         }	
	    	    	    	    	    	     return result; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "billingCycle",
		    	    	    	    	    	    "render": function(data, type, report, meta) {
		    	    	    	    	    	    	var result = "";
		    	    	    	    	    	    	if(report.billingCycle != null && report.billingCycle != '' && report.billingCycle != 'undefined'){
		    	    	    	    				         result = result + '<div><span>'+report.billingCycle+'</span></div>';
		    	    	    	    			         }else{
		    	    	    	    			        	 result = result + '<div><span>---</span></div>';
		    	    	    	    			         }
		    	    	    	    	    	     return result;
		    	    	    	    	    	    }
		    	    	    	    	    	    },{
		    	    	    	    	    	    	"targets": colIndex++,
			    	    	    	    	    	    "data": "templateSubmissionStatus",
			    	    	    	    	    	    "render": function(data, type, report, meta) {
			    	    	    	    	    	    	var result = "";
			    	    	    	    	    	    	result = result + '<div><span>'+report.templateSubmissionStatus+'</span></div>';
			    	    	    	    	    	     return result;
			    	    	    	    	    	    }
			    	    	    	    	    	    },{
		    	    	    	    	    	    	"targets": colIndex++,
			    	    	    	    	    	    "data": "action",
			    	    	    	    	    	    "render": function(data, type, report, meta) {
			    	    	    	    	    	    	var result = "";
			    	    	    	    	    	    	result = result + '<div class="tbldata-dotdot_80">';
			    	    	    	    	 		         if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_user.id}' == report.ownerUser){
			    	    	    	    			        	 if(report.contractStatus != "completed" && report.templateSubmissionStatus == "WAITING_TO_BE_VERIFY" && report.templateSubmissionStatus != "FINALIZED"){
			    	    	    	    				     			result = result + '<a id="removeStatus'+report.id+'" class="fa fa-thumbs-o-up" title="finalize" onclick="finalizeContract('+report.id+',\'WAITING_TO_BE_VERIFY\',\'Vendor\');"></a> | ';
			    	    	    	    		            	 }
			    	    	    	    			         }
			    	    	    	    			         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
			    	    	    	    			         if(report.typeOfContract == "New" || report.typeOfContract == "Custom"){
			    	    	    	    			        	 result = result + '<a href="#" title="Preview"><i class="fa fa-eye" onClick="viewFile(\''+report.templateFileURL+'\', \''+report.contractType+'\', \'application/pdf\')"></i></a>';
			    	    	    	    			         }else{
			    	    	    	    		     		 result = result + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+report.fileURL+'\', \''+report.fileName+'\', \''+report.fileType+'\')"></i></a>';
			    	    	    	    			         }
			    	    	    	    		     		 '</sec:authorize>';
			    	    	    	    			         result = result + '</div></td>';
			    	    	    	    	    	     
			    	    	    	    	    	     return result;
			    	    	    	    	    	    }  
		    	    	    	    	    	    
		    	    	    	 }],
		    	    	    	 "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableWTV").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#vendorDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
	
	
	
}

function preparedContractListClientWTV(url,tabType){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	  preparedContractClientListWTV(url,tabType);
}

function preparedContractClientListWTV(url,tabType){
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
	$("#clientContractsList").empty();
    $("#clientContractsList").append(opt); 
	preparedClientContractListWTVPagination(url,tabType);
}

function preparedClientContractListWTVPagination(url,tabType){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var colIndex = 0;
    $('#contractListTableWTV1').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "clientContact" }, 
         		   { "data": "client" }, 
         		   { "data": "startDate/endDate"}, 
         		   { "data": "reqDocCount"},
         		   { "data": "billingCycle"},
         		   { "data": "templateSubmissionStatus"},
         		   { "data": "action"} 
         ], 
         		   
        "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, output, meta) {
            var opt = "";
            if(null == output.parentContractId || '' == output.parentContractId || 'undefined' == output.parentContractId){
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+output.contractUUID+'\',\'VERIFY\');"><div class="tbldata-dotdot-big inlineBlock text-left" title="'+output.contractTitle+'">'+output.contractTitle+'</div><i class="fa fa-binoculars" style="position: absolute; right: 0; top: 0;"></i></div>';
	        }else{
	        	opt = opt + '<div class="tbldata-dotdot-sm proj_show" ><div class="tbldata-dotdot-big inlineBlock text-left" title="'+output.contractTitle+'">'+output.contractTitle+'</div></div>';
	        }
             return opt;
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, output, meta) {
		        var opt = "";
		        opt = opt + '<div class="tblminwidth-75">'+output.contractType+'</div>';
		         return opt; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, output, meta) {
	    	         var opt = "";
	    	         opt = opt + '<div><span title="'+output.ownerName+'">'+output.ownerName+'</span></div>';
	    	         return opt; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "clientContact",
	    	    	    "render": function(data, type, output, meta) {
	    	    	         var opt = "";
	    	    	         opt = opt + '<div><span title="'+output.vendorContactName+'">'+output.vendorContactName+'</span></div>';
	    	    	         return opt; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "client",
	    	    	    	     "render": function(data, type, output, meta) {
	    	    	    	    	  var opt = "";
	    	    	    	    	  if(output.contractWith != null && output.contractWith != '' && output.contractWith != 'undefined'){
	    	    	    		        	 if(output.orgRelationUUID != null && output.orgRelationUUID != '' && output.orgRelationUUID != 'undefined'){
	    	    	    		        	 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    	    		        	  opt = opt + '<div><span title="'+output.contractWith+'"><a href="'+vmsUrl+'/clientDashboard?orgRelationUUID='+output.orgRelationUUID+'" title="'+output.contractWith+'">'+output.contractWith+'</a></span></div>';
	    	    	    		        	 '</sec:authorize>';
	    	    	    		        	 }else{
	    	    	    	        		 '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
	    	    	    		        	  opt = opt + '<div><span title="'+output.contractWith+'">'+output.contractWith+'</span></div>';
	    	    	    		        	 '</sec:authorize>';
	    	    	    		        	 }
	    	    	    		         }
	    	    	    	    	  return opt; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "startDate/endDate",
	    	    	    	    	     "render": function(data, type, output, meta) {
	    	    	    	    	    	  var opt = "";
	    	    	    	    	    	  opt = opt + '<div class="tblminwidth-150">'+output.startDateStr+'';
	    	    	    	    		         if(output.endDateStr != null && output.endDateStr != '' && output.endDateStr != 'undefined'){
	    	    	    	    		        	 opt = opt +'</br>'+output.endDateStr+'</div>';
	    	    	    	    		         }
	    	    	    	    	    	 return opt; 
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, output, meta) {
	    	    	    	    	    	      var opt = "";
	    	    	    	    	    	      if(output.reqDocCount!="0"){
	    	    	    	    			         	opt = opt + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+output.contractUUID+'\',\''+output.parentId+'\');">'+output.reqDocCount+'</div>';
	    	    	    	    			         }
	    	    	    	    			         else{
	    	    	    	    			        	 opt = opt + '<div class="tblminwidth-75 ">'+output.reqDocCount+'</div>';
	    	    	    	    			         }	
	    	    	    	    	    	     return opt; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
		    	    	    	    	    	    "targets": colIndex++,
		    	    	    	    	    	    "data": "billingCycle",
		    	    	    	    	    	    "render": function(data, type, output, meta) {
		    	    	    	    	    	      var opt = "";
		    	    	    	    	    	      if(output.billingCycle != null && output.billingCycle != '' && output.billingCycle != 'undefined'){
		    	    	    	    	 	        	 opt = opt + '<div><span>'+output.billingCycle+'</span></div>';
		    	    	    	    	 	         }else{
		    	    	    	    	 	        	 opt = opt + '<div><span>---</span></div>';
		    	    	    	    	 	         }
		    	    	    	    	    	      return opt; 
		    	    	    	    	    	    }
		    	    	    	    	    	    },{
			    	    	    	    	    	    "targets": colIndex++,
			    	    	    	    	    	    "data": "templateSubmissionStatus",
			    	    	    	    	    	    "render": function(data, type, output, meta) {
			    	    	    	    	    	      var opt = "";
			    	    	    	    	    	      opt = opt + '<div><span>'+output.templateSubmissionStatus+'</span></div>';
			    	    	    	    	    	      return opt; 
			    	    	    	    	    	    }
			    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "action",
		    	    	    	    	    	    "render": function(data, type, output, meta) {
		    	    	    	    	    	      var opt = "";
		    	    	    	    	    	      opt = opt + '<div class="tbldata-dotdot_80">';
		    	    	    	    	  	         if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || '${SourceLeadWeb_loginUserRole}' == "ROLE_MANAGER" || output.isOwner == "Y"){
		    	    	    	    	 	        	 if(output.contractStatus != "completed" && output.templateSubmissionStatus == "WAITING_TO_BE_VERIFY" && output.templateSubmissionStatus != "FINALIZED"){
		    	    	    	    	 		     			opt = opt +  '<a id="removeStatus'+output.id+'" class="fa fa-thumbs-o-up" title="finalize" onclick="finalizeContract('+output.id+',\'WAITING_TO_BE_VERIFY\',\'Client\');"></a> | ';
		    	    	    	    	 		     		}
		    	    	    	    	  	         }
		    	    	    	    	 	         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    	 	         if(output.typeOfContract == "New" || output.typeOfContract == "Custom"){
		    	    	    	    	 	        	 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye" onClick="viewFile(\''+output.templateFileURL+'\', \''+output.contractType+'\', \'application/pdf\')"></i></a>';
		    	    	    	    	 	         }else{
		    	    	    	    	      		 opt = opt + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+output.fileURL+'\', \''+output.fileName+'\', \''+output.fileType+'\')"></i></a>';
		    	    	    	    	 	         }
		    	    	    	    	 	        '</sec:authorize>';
		    	    	    	    	      		 opt = opt + '</div></td>';
		    	    	    	    	    	  return opt; 
		    	    	    	    	    	  }
		    	    	    	 }],
		    	    	    	 "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
//		    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableWTV1").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#clinetDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
	
}
	
function preparedContractListApplicantWTV(url,tabType){
	var SourceLeadWeb_loginOrg= '${SourceLeadWeb_loginOrg}';
	$("#vendorContractsList").empty();
    $("#clientContractsList").empty();
    $("#applicantsContractsList").empty();
    var locationList ='${SourceLeadWeb_dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  locationDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  locationDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  locationDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  locationDate = 'YYYY-DD-MM'; 
	  } 
	  preparedContractApplicantListWTV(url,tabType);
}	
	
function preparedContractApplicantListWTV(url,tabType){	
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
		$("#applicantsContractsList").empty();
	    $("#applicantsContractsList").append(res);
		preparedContractApplicantListWTVPagination(url,tabType);
}

function preparedContractApplicantListWTVPagination(url,tabType){
	var colIndex = 0;
    $('#contractListTableWTV2').DataTable( {
            "processing": true,
        "serverSide": true,
        "searching": false,
        "ajax": $.fn.dataTable.pipeline( {
            url: url,
            method: 'GET',
            pages: 10
        }),
        "columns": [ 
         		   { "data": "contractTitle" }, 
         		   { "data": "contractType"}, 
         		   { "data": "owner"}, 
         		   { "data": "employeeName" }, 
         		   { "data": "joinDate" }, 
         		   { "data": "billingAmount"}, 
         		   { "data": "reqDocCount"},
         		   { "data": "templateSubmissionStatus"},
         		   { "data": "action"} 
         		   ], 
         		   
        "columnDefs": [ {
        "targets": colIndex++,
        "data": "contractTitle",
        "render": function(data, type, res, meta) {
            var result = "";
            result = result + '<div class="tbldata-dotdot-sm proj_show" onclick="PopUp(\''+res.contractUUID+'\',\'VERIFY\');"><span title="'+res.contractTitle+'">'+res.contractTitle+'</span><i class="fa fa-binoculars"></i></div>';
             return result;
            }
        },{
	        "targets": colIndex++,
		    "data": "contractType",
		    "render": function(data, type, res, meta) {
		         var result = "";
		         result = result + '<div class="tblminwidth-75">'+res.contractType+'</div>';
		         return result; 
		          }
		        },{	
	        	"targets": colIndex++,
	    	    "data": "owner",
	    	    "render": function(data, type, res, meta) {
	    	         var result = "";
	    	         result = result + '<div><span title="'+res.ownerName+'">'+res.ownerName+'</span></div>';
	    	         return result; 
	    	         }
	    	        },{
	    	        	"targets": colIndex++,
	    	    	    "data": "employeeName",
	    	    	    "render": function(data, type, res, meta) {
	    	    	         var result = "";
	    	    	         result = result + '<div><span title="'+res.empName+'">'+res.empName+'</span></div>';
	    	    	         return result; 
	    	    	         }
	    	    	        },{
	    	    	    	    "targets": colIndex++,
	    	    	    	     "data": "joinDate",
	    	    	    	     "render": function(data, type, res, meta) {
	    	    	    	    	  var result = "";
	    	    	    	    	  result = result + '<div class="tblminwidth-75">'+res.joinedDateStr+'</div>';
	    	    	    	    	  return result; 
	    	    	    	    	  }
	    	    	    	    	  },{
	    	    	    	    	     "targets": colIndex++,
	    	    	    	    	     "data": "billingAmount",
	    	    	    	    	     "render": function(data, type, res, meta) {
	    	    	    	    	    	  var result = "";
	    	    	    	    	    	  if(res.billingAmount != null && res.billingAmount != '' && res.billingAmount != 'undefined'){
	    	    	    	    	    		  result = result + '<div class="tblminwidth-75">'+res.billingAmount+'</div>';
	    	    	    	   		         }
	    	    	    	   		         else{
	    	    	    	   		        	result = result + '<div class="tblminwidth-75">--</div>'; 
	    	    	    	   		         }
	    	    	    	    	    	 return result; 
	    	    	    	    	    	 }
	    	    	    	    	    	 },{
	    	    	    	    	    	    "targets": colIndex++,
	    	    	    	    	    	    "data": "reqDocCount",
	    	    	    	    	    	    "render": function(data, type, res, meta) {
	    	    	    	    	    	      var result = "";
	    	    	    	    	    	      if(res.reqDocCount!="0"){
	    	    	    	    	    	    	  result = result + '<div class="tblminwidth-75 Cursor" onclick="CountClick(\''+res.contractUUID+'\',\''+res.parentId+'\');">'+res.reqDocCount+'</div>'; 
	    	    	    	    			         }
	    	    	    	    			         else{
	    	    	    	    			        	 result = result + '<div class="tblminwidth-75 ">'+res.reqDocCount+'</div>';
	    	    	    	    			         }		 
	    	    	    	    	    	     return result; 
	    	    	    	    	    	    }
	    	    	    	    	    	    },{
		    	    	    	    	    	    "targets": colIndex++,
		    	    	    	    	    	    "data": "templateSubmissionStatus",
		    	    	    	    	    	    "render": function(data, type, res, meta) {
		    	    	    	    	    	      var result = "";
		    	    	    	    	    	      result = result + '<div><span title="'+res.templateSubmissionStatus+'">'+res.templateSubmissionStatus+'</span></div>';	 
		    	    	    	    	    	     return result; 
		    	    	    	    	    	    }
		    	    	    	    	    	    },{
	    	    	    	    	    	    	"targets": colIndex++,
		    	    	    	    	    	    "data": "action",
		    	    	    	    	    	    "render": function(data, type, res, meta) {
		    	    	    	    	    	    	var result = "";
		    	    	    	    	    	    	result = result + '<div class="tbldata-dotdot_80">';
		    	    	    	    	 		         if('${SourceLeadWeb_loginUserRole}' == "ROLE_ADMIN" || res.IsOwner == 'Y'){
		    	    	    	    	 		        	 if(res.contractStatus != "completed" && res.templateSubmissionStatus == "WAITING_TO_BE_VERIFY" && res.templateSubmissionStatus != "FINALIZED"){
		    	    	    	    			        		 result = result +  '<a id="removeStatus'+res.id+'" class="fa fa-thumbs-o-up" title="finalize" onclick="finalizeContract('+res.id+',\'WAITING_TO_BE_VERIFY\',\'Full time offer\');"></a> | ';
		    	    	    	    				     		}
		    	    	    	    			         }
		    	    	    	    			         '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		    	    	    	    			         if(res.typeOfContract == "New"){
		    	    	    	    			        	 result = result + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewNewContractPDF(\''+res.contractUUID+'\', \''+res.contractTitle+'\')"></i></a>';
		    	    	    	    			         }else{
		    	    	    	    			        	 result = result + '<a href="#" title="Preview"><i class="fa fa-eye" onclick="previewContractPDF(\''+res.fileURL+'\', \''+res.fileName+'\', \''+res.fileType+'\')"></i></a>';
		    	    	    	    			         }
		    	    	    	    			         '</sec:authorize>';
		    	    	    	    			         result = result + '</div></td>';
		    	    	    	    	    	     return result; 
		    	    	    	    	    	    }
		    	    	    	   }],
		    	    	    	   
		    	    	    	   "scrollCollapse": false,
		    	    	            "lengthMenu": [[10, 15, 20], [10, 15, 20]],
//		    	    	             "aaSorting": [[3,'desc']],
		    	    	            "bLengthChange": true,
		    	    	            "bDestroy": true,
		    	    	            "ordering": false,
		    	    	            "oLanguage": {
		    	    	                 "sSearch": "",
		    	    	                  "sZeroRecords": "", "sEmptyTable": "",
		    	    	                  "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
		    	    	         }
		    	    	     });
		    	    	     $(".repoPositiNoCount").find('a.buttons-excel').prop("title", "Excel");
		    	    	 	$(".repoPositiNoCount").find('a.buttons-pdf').prop("title", "PDF");
		    	    	 	/* if(listLength == 0){
		    	    	 		$(".vendorContractsList").find('.dt-buttons').hide();
		    	    	 	} */
		    	    	 	$("#contractListTableWTV2").wrap("<div class='overflow_Auto' style='width:100%;'></div>");
		    	    	 	$("#applicantDataTableSearch").keyup(function(e) {
		    	    	           if(e.which === 13) {
		    	    	        	   getTemplateList(tabType);
		    	    	           }
		    	    	        });
	
	
	
	
}
</script>



<script type="text/javascript">
function previewContractPDF(fileURL, fileName, fileType) {
	viewFile(fileURL, fileName, fileType);
// 	window.open(sourceleadUrl+'/viewContractPdf/'+uuid+'/'+templateName+'','_newtab');
}

function previewNewContractPDF(uuid, templateName) {
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
 	window.open(vmsUrl+'/viewContractPdf/'+uuid+'/'+templateName+'','_newtab');
}
</script>

<script>
$('#sucesspop').delay(2000).fadeOut('slow');
function removeRecord(recordId,status){
	var rowHtmlContent =  $("#row"+recordId).html();
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 $.ajax({
       type : "POST",
        url: vmsUrl+"/updateContractStatus?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,        
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#removeStatus"+recordId).html("<span>Send For Verification</span>").addClass('text-primary');
       },
   }); 
}


function finalizeContract(recordId,status,tabType){
	 var rowHtmlContent =  $("#row"+recordId).html();
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 $.ajax({
       type : "POST",
        url: vmsUrl+"/updateContractStatus?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,       
        success :function(data) {
        	$("#row"+recordId).empty();
	    	$("#row"+recordId).html(rowHtmlContent);
	        $("#removeStatus"+recordId).html("<span>Finalized</span>").addClass('text-success');
	        getTemplateList(tabType);
       },
   });  
}
</script>
<script>
	   function msgFadein() {
			$("#contractSelector").fadeIn(200);
		    $('#contractSelector').delay(2000).fadeOut('slow');
		}
   </script>
<script>
function sendContractFromOwner(contractUUID,tabType){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 $.ajax({
      type : "POST",
       url: vmsUrl+"/sendContractFromOwnertoReceiver?contractUUID="+contractUUID,       
       success :function(data) {
    	   if(data)
	        getTemplateList(tabType);
      },
  }); 
}
</script>

<script>
function approveContract(recordId,status,tabType){
	var rejectReason =  $("#rejectContractId").val();
	var rowHtmlContent =  $("#row"+recordId).html();
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
       type : "POST",
        url: vmsUrl+"/approveContract?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status+"&rejectReason="+rejectReason,       
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
    	        $("#contractAnotherStage").val("WAITING_TO_SEND");
        	}
        	$("#loading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
        	getTemplateList(tabType);
       },
   });  
}
</script>

<script>
function rejectedContractReason(recordId,status,tabType){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Do you want to reject this contract?</p>";
	result = result +"		<textarea  id='rejectContractId' name='rejectContractId'  placeholder='Reason here'></textarea>";
	result = result + ' <div id="rejectContractIdDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='validateReason("+recordId+",\""+status+"\",\""+tabType+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function validateReason(recordId,status,tabType){
	
	var rejectReason =  $("#rejectContractId").val();
	if(rejectReason != null && rejectReason !='undefined' && rejectReason !=''){
		$("#rejectContractIdDiv").find('p.jquery_form_error_message').remove();
		approveContract(recordId,status,tabType);
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
		 $("#"+localId).removeClass("btn-primary").addClass("btn-default");
    });
	var tabId = local.id;
	var aa = $("#"+tabId).val();
	$("#errorDiv").val(aa);
	$("#tabType").val(tabId);
	$("#"+tabId).removeClass("btn-default").addClass("btn-primary");
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 $.ajax({
      type : "POST",
       url: vmsUrl+"/getContractStageCounts?${_csrf.parameterName}=${_csrf.token}&recordId="+recordId+"&status="+status,success :function(data) {
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
function getIndividualCounts(status){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$.ajax({
     type : "GET",
      url: vmsUrl+"/getIndividualCounts?${_csrf.parameterName}=${_csrf.token}&contractStatus="+status,success :function(data) {
    	  $("#Vendor1").text('Vendors('+data.totalDraftVendorCount+')');
	      $("#Client1").text('Clients('+data.totalDraftClientCount+')');
	      $("#Applicants1").text('Applicants('+data.totalDraftApplicantCount+')');
      }
	 });
}
</script>

<script>
function Check(val){
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 if(val=="Vendors"){
			 document.getElementById("clientContractsList").style.display="none";
			 document.getElementById("applicantsContractsList").style.display="none"; 
			 document.getElementById("vendorContractsList").style.display="block";
			 
			 document.getElementById("vendorDataTable").style.display = "block";
			 document.getElementById("clinetDataTable").style.display = "none";
			 document.getElementById("applicantDataTable").style.display = "none";
			 
			 $("#clinetDataTableSearch").val('');
			 $("#applicantDataTableSearch").val('');
		 }
		 if(val=="Clients"){
			 document.getElementById("vendorContractsList").style.display="none";
			 document.getElementById("applicantsContractsList").style.display="none"; 
			 document.getElementById("clientContractsList").style.display="block";
			 
			 document.getElementById("vendorDataTable").style.display = "none";
			 document.getElementById("clinetDataTable").style.display = "block";
			 document.getElementById("applicantDataTable").style.display = "none";
			 $("#vendorDataTableSearch").val('');
			 $("#applicantDataTableSearch").val('');
		 }
		 if(val=="Applicants"){
			 document.getElementById("clientContractsList").style.display="none";
			 document.getElementById("vendorContractsList").style.display="none";
			 document.getElementById("applicantsContractsList").style.display="block";
			 
			 document.getElementById("vendorDataTable").style.display = "none";
			 document.getElementById("clinetDataTable").style.display = "none";
			 document.getElementById("applicantDataTable").style.display = "block";
			 
			 $("#vendorDataTableSearch").val('');
			 $("#clinetDataTableSearch").val('');
		 }
	 $("#loading").removeAttr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$("#DocAccPopupDiv").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 $.ajax({
      type : "GET",
       url: vmsUrl+"/getDocsByContractUUID?contractUUID="+contractUUID,       
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

function uploadRequestedDocuments(id){
	var sourceleadUrl ='${sourceleadUrl}';
	fileUpload("uploadBtn"+id+"", "Requested Documents", "contractDoc_form");
}


function removeResumeNameDiv(id){
	$("#removeDiv"+id).hide();
	
	     var vmsUrl ='${SourceLeadWeb_vmsUrl}';
		 $.ajax({
	     type : "POST",
	      url: vmsUrl+"/updateDocument?${_csrf.parameterName}=${_csrf.token}&docuId="+id,       
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	if(docId != null && docId != ""){
		window.open(vmsUrl+'/viewReqContractDocPdf/'+docId+'/'+fileName+'',fileName,"height=600,width=600");
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
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 $.ajax({
    type : "POST",
     url: vmsUrl+"/updateContractEndDate?${_csrf.parameterName}=${_csrf.token}&contractId="+contractId+"&endDate="+endDate,       
     success :function(data) {
    	 $("#endVal").empty();
		  $("#endVal").append(": "+endDate);
     	 //   $("#tr"+id).empty();
    },
});  
	
}
</script>
<script type="text/javascript" src="/resources/js/contractCommonScripts.js"></script>
<!-- Footer Area -->
<jsp:include page="../masterFooter.jsp" />
<!-- Footer Area -->

