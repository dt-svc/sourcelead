<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="content-area clearfix">
      <div id="accordion-sl" class="release-jobs">
        
      <%--   <c:if test="${SourceLeadWeb_vms == 'Y'}"> --%>
        <div class="clearfix">

          <h2 id="jobTitle" onclick="openVendors();">Profile</h2>

          <div class="content clearfix">
            <div class="vendor_wrap">
              <div class="col-md-7 radioInline zIndex">
                <!-- <select class="form-control selectpicker"  id="sele-rel"  onchange="listChange(this);">
                  <option id="cont" value ="releaseByContact">Release By Contact</option>
                  <option id="ven"  value ="releaseByVendors" selected="selected">Release By Vendors</option>
                </select> -->
                <div class="pull-left mt10">
                	<label class="mr"><input class="myCheckbox valid" type="radio" name="group1" id="ven" onchange="listChange(this);" value="releaseByVendors" checked> Release to Vendor</label>
                	<label class="mr"><input class="myCheckbox valid" type="radio" name="group1" id="cont" onchange="listChange(this);" value="releaseByContact"> Release to Contact</label>
              	</div>
              </div>
              <div class="timesheet_head">
                <ul>
                  <li class="li-vendor hidden">
                    <input type="text" class="vendorfild">
                    <i class="fa fa-search"></i>|</li>
                  <li id="vendorStatus" class="zIndex"><i class="fa fa-filter"></i><span>Tier</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
                    <div class="approved_pop">
                      <ul>
                        <li><input id="c5" type="checkbox"  name="vendorStatus"  value ="Tier-1" onclick="getVendorLists();"><label>&nbsp;&nbsp; Tier 1</label></li>
						<li><input id="c6" type="checkbox"  name="vendorStatus"  value ="Tier-2" onclick="getVendorLists();"><label>&nbsp;&nbsp; Tier 2</label></li>
						<li><input id="c7" type="checkbox"  name="vendorStatus"  value ="Tier-3" onclick="getVendorLists();"><label>&nbsp;&nbsp; Tier 3</label></li>
						<li><input id="c8" type="checkbox"  name="vendorStatus"  value ="Tier-4" onclick="getVendorLists();"><label>&nbsp;&nbsp; Tier 4</label></li>
              			<li><input id="c9" type="checkbox"  name="vendorStatus"  value ="Tier-5" onclick="getVendorLists();"><label>&nbsp;&nbsp; Tier 5</label></li>
                       </ul>
                    </div>
                  </li>
                  <li id="vendorSelected" style="display:none" class="zIndex"><i class="fa fa-filter"></i><span>Vendor</span><i class="glyphicon glyphicon-play" id="export-popp"></i>
                    <div class="approved_pop">
                       <ul>
<!--                       <ul id="vendorReleaseCheckList"> -->
                      	  <c:forEach var="venReport" items="${vendor_list}">  
	                      	<li id="VN${venReport.id}"><input id="vendorName${venReport.id}" name="vendorSelected" value ="${venReport.id}" onclick="getContactsLists();" type="checkbox"><label title="${venReport.vendorName}">&nbsp;&nbsp; ${venReport.vendorName}</label></li>
						  </c:forEach> 
                      </ul>
                    </div>
                  </li>
                  <div class="clearfix"></div>
                </ul>
              </div>
              <!-- Contact Table -->
              <div id="vendorContactsLoaderDiv"></div>
              <div id="vendorContacts" class="positionRelative datatable_filter">
              </div>
             
              <!-- Contact Table Ends --> 
              
              <!-- Vendor Table -->
              <div id="vendorLoaderDiv"></div>
              <div id="vendor" class="positionRelative datatable_filter">
              </div>
              
              <!-- Vendor Table Ends --> 
            </div>
          </div>
        </div>
	  <!-- Accordion First Tab Ends -->
	  <%-- </c:if> --%>

      <!-- Accordion second Tab -->
         <form:form id="releaseToCrowdSourcing_form"  name="releaseToCrowdSourcing_form" method="post">
         <div class="success_pop" id="relTocro" style="display: none;">
	    	<i class="fa fa-check-circle"></i><span id="relMsgDiv"></span>
	    </div>
         <div class="clearfix">
          <h2 onclick="opennetwork();">Release To Other Networks</h2>
          <div class="content clearfix" >
            <div class="row">
                <div class="col-md-12">
                <div class="panel panel-success">
                  <div class="panel-heading ">Select Job Board(s)</div>
                  <div class="panel-body">
                    <ul class="list-unstyled sl-jobboard">
                        <c:forEach items="${jobBoardList}" var="j">
                            <li class="bg-${j.name}">
                                    <label class="checkbox-inline">
                                    <input type="checkbox" data-check="jobBoardCheckBox" name="optradio" value="${j.id}">
                                    <span><img src="${j.logoUrl}" alt="${j.name}" title="${j.name}"></span>
                                </label>
                                <label id="jobBoardOption${j.id}" class="radio-inline hidden" >
                                    <select id='jobBoardAccount${j.id}' class="selectpicker">
                                        <option selected="selected" value='0'>--Select Account--</option>
                                        <c:forEach items="${jobBoardAccountList}" var="j1">
                                            <c:if test="${j.id == j1.orgJobBoardId && j1.active}">
                                                <option value='${j1.id}'>${j1.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </label>
                            </li>
                        </c:forEach>
                    </ul>
                  </div>
                </div> 
                </div>
              <div class="col-md-6">
                <div class="row">
                  <div class="form-group clearfix toggle-button-lbl">
                    <label class="col-md-5">Public</label>
                    <input type="hidden" name="isPublic" id="isPublicGlobel" value="N">
                    <div class="col-md-7 toggle-button">
                      <label class="switch">
                      <input type="checkbox" id="isPublicId"  value="N" onclick="ispublic();">
                      <div class="slider round"></div>
                      </label>
                    </div>
                  </div>
                  <div class="form-group clearfix toggle-button-lbl">
                    <label class="col-md-5">Crowd Sourcing</label>
                    <input type="hidden" name="isCrowdScourcing" id="isCrowdScourcingGlobel" value="N">
                    <div class="col-md-7 toggle-button">
                      <label class="switch">
                      <input type="checkbox"  value="N" id="isCrowdScourcingId" onclick="isCrowd();" >
                      <div class="slider round"></div>
                      </label>
                    </div>
                  </div>
                  
                  <div class="form-group clearfix" id="crowd_rate" style="display: none">
                    <div class="clearfix">
                      <label class="col-md-5"><strong>Job Details</strong></label>
                      <div class="col-md-7">
                        <p><i class="fa fa-suitcase text-muted paddingRight"></i>
                          <label id="jobType"></label>
                        </p>
                        <p><i class="fa fa-money text-muted paddingRight"></i>
                          <label id="jobRate"></label>
                        </p>
                      </div>
                    </div>
                    <div class="clearfix">
                      <label class="col-md-12"><strong>Reward Price</strong></label>
                    </div>
                    <div class="clearfix">
                      <div class="col-md-3 paddingRight">
                        <div class="btn-group btn_Block">
                          <button type="button" class="btn btn-sm btn-primary" id="percentageBtn" value="percentage" onClick="getRewardType('Percentage')">%</button>
                          <button type="button" class="btn btn-sm btn-default" id="flatBtn" value="flat" onClick="getRewardType('Flat')">Flat</button>
                          <input type="hidden" name="rewardType" id="rewardType" value=""/>
                        </div>
                      </div>
                      <div class="col-md-7 paddingLeft">
                        <div class="form-group positionRelative iconHolder percentage" id="percentageReward"  style="display: none">
                          <input type="text" class="form-control input-sm" field-name="Percentage" id="percentageRewardValue" name="percentageReward" onblur="getPercentageValue()" placeholder="Percentage">
                          <i class="fa fa-percent"></i> 
                        </div>
                        
                        <div class="row flat">
                          <div class="form-group col-md-8 paddingRight positionRelative iconHolder" id="flatReward" style="display: none">
                            <input type="text" class="form-control input-sm" field-name="Flat" id="flatRewardValue"  name="flatReward" placeholder="Flat">
                            <i class="fa fa-money" id="flatRewardIcon"></i> </div>
                          <div class="form-group select-field col-md-4 paddingLeft">
                            <select class="form-control input-sm" id="flatRateType" name="flatRateType"  style="display: none">
                              <option  ${SourceLeadWeb_currencyCode == 'INR' ? 'selected' : ''}><i class="fa fa-rupee" title="INR"></i>INR</option>
                              <option  ${SourceLeadWeb_currencyCode == 'USD' ? 'selected' : ''}><i class="fa fa-dollar" title="USD"></i>USD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'AUD' ? 'selected' : ''}><i class="fa fa-aud" title="AUD"></i>AUD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'EUR' ? 'selected' : ''}><i class="fa fa-eur" title="Eur"></i>EUR</option>
                              <option  ${SourceLeadWeb_currencyCode == 'SGD' ? 'selected' : ''}><i class="fa fa-sgd" title="SGD"></i>SGD</option>
                              <option  ${SourceLeadWeb_currencyCode == 'GBP' ? 'selected' : ''}><i class="fa fa-gdp" title="GDP"></i>GBP</option>
                            </select>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="clearfix">
                      <label class="col-md-3">Reward guarantee period</label>
                      <div class="select-field col-md-7 paddingLeft">
                        <select class="form-control input-sm" id="guaranteePeriod" name="guaranteePeriod">
                          <option value="120 days">120 Days</option>
                          <option value="90 days">90 Days</option>
                          <option value="60 days" selected="selected">60 Days</option>
                          <option value="30 days">30 Days</option>
                          <option value="15 days">15 Days</option>
                          <option value="7 days">7 Days</option>
                          <option value="Immediately">Immediately</option>
                        </select>
                      </div>
                    </div>

                    <div class="clearfix col-lg-12 hidden"> <span class="text-muted">Note:</span> <span class="text-info"><b>5%</b> will be sourcelead fund and remaining....</span> </div>
                  </div>
                  
                  
                </div>
              </div>
            </div>
            
            <label class="col-md-3 row"> Referral Link</label>
            <div class="select-field col-md-6 paddingLeft row">
            <input type="text" class="form-control input-sm" field-name="RefeeralLink" id="referralLink" name="referralLink" placeholder="Referral Link" readonly="readonly"> 
            <i id="copyButton" class="fa fa-clipboard" aria-hidden="true"></i>
        

            <div class="share mt10"> 
			<div class="addthis_inline_share_toolbox_g28q" data-url="${sourceleadShareUrl}/job/${requirement.url}-${requirement.id}"></div>
			</div>
            </div>

          </div>
        </div> 
        <div class="form-footer clearfix row mt" id="releaseToCrowdSourcing" style="display: none">
        <div class="col-lg-12">
          <div class="pull-right">
            <span id="openNetworkRelease" class="text-danger small" style="font-size: 11px; display: none;">Job Board / Public / Crowd Sourcing is mandatory</span>
	           <c:if test="${!empty jobRecommendations}">
	              <input type="button" value="Skip" class="btn btn-primary ml" onClick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/jobRecommendations?requirementUUID=${requirementUUID}'"></a>
	           </c:if>
           	<input type="button" class="btn btn-primary ml" onclick="releaseToCrowdSourcing();" id="releaseToCWBtn" value="Release">
            <input type="button" class="btn btn-default ml" id="releaseCancelBtn" value="Cancel" onClick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/jobsList'">
          </div>
        </div>
      </div>
        </form:form>
      <!-- Accordion second Tab Ends -->
      </div>
      <div class="form-footer clearfix row mt" id="releaseToVendors" style="display: block">
        <div class="col-lg-12">
          <div class="pull-right">
               <c:if test="${!empty jobRecommendations}">
	              <input type="button" value="Skip" class="btn btn-primary ml" onClick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/jobRecommendations?requirementUUID=${requirementUUID}'"></a>
	           </c:if>
            <sec:authorize access="hasAnyRole('ROLE_RELEASE_REQUIREMENT')">
            <span class="jquery_form_error_message" style="display:none;">Vendor/Contact selection is mandatory</span>
            	<input type="button" class="btn btn-primary ml" id="releaseBtn" onclick="releaseTo();" value="Release">
			</sec:authorize>
			
            <input type="button" class="btn btn-default ml" id="releaseCancelBtn" value="Cancel" onClick="javascript:window.location.href = '${SourceLeadWeb_atsUrl}/jobsList'">
          </div>
        </div>
      </div>
    </div>
    
    
    <script>
        
        $(function() {
            $("[data-check=jobBoardCheckBox]").click(function() {
                $("#jobBoardOption"+$(this).val()).toggleClass('hidden');
            });
            
            <c:forEach items="${requirement.requirementJobBoardInfoList}" var="r">
                $("[data-check=jobBoardCheckBox][value=${r.orgJobBoardAccount.orgJobBoard.id}]").click();
                $("#jobBoardAccount${r.orgJobBoardAccount.orgJobBoard.id}").val(${r.orgJobBoardAccount.id});
            </c:forEach>
        });
function getPercentageValue(){
	var percentageValue = document.getElementById("percentageRewardValue").value;
	if(percentageValue > 100 || percentageValue < 0){
		$("#releaseToCWBtn").attr("disabled", "disabled");
	    $('#percentageReward').find('p.jquery_form_error_message').remove(); 
	   
	    if(percentageValue < 0){
	    	$("input#percentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value should not in negitive</span>");
	    }else{
	    	$("input#percentageRewardValue").after("<p class='jquery_form_error_message'>Percentage value not more than 100</span>");
	    }
	    
		$('input#percentageRewardValue').attr('record-exist','yes');
		 if(percentageValue < 0){
			 $('input#percentageRewardValue').attr('record-exist-errorMsg','Percentage value should not in negitive'); 
		 }else{
			 $('input#percentageRewardValue').attr('record-exist-errorMsg','Percentage value not more than 100'); 
		 }
		
	}else{
		 $("#releaseToCWBtn").removeAttr("disabled");
		 $('input#percentageRewardValue').removeAttr( "record-exist record-exist-errormsg");
		 $('#percentageRewardValue').find('p.jquery_form_error_message').remove(); 
		 $('#percentageReward').find('p.jquery_form_error_message').remove(); 
		 $( "#releaseToCWBtn" ).removeAttr("style");
		 $( "#releaseToCWBtn" ).click(function() {
		 if ($(this).validate(false, validationSettings)){
		 }
		});
	}
	
}
</script>



<script> 
$(document).ready(function(){
	getVendorLists();
	 var sourceleadShareUrl ='${sourceleadShareUrl}';
	 
	var referLink = "${sourceleadShareUrl}/job/${requirement.url}-${requirement.id}"
	//alert(sourceleadShareUrl);		
	$("#referralLink").val(referLink);
	if('${SourceLeadWeb_isVendorsTabAccessable}'  == 'Y'){
		openVendors();
	}else{
		opennetwork();
	}
    
	
	if('${requirementType}' != null && '${requirementType}' != '' && '${requirementType}' != 'undefined'){
		$("#jobType").html('${requirementType}');
	}else{
		$("#jobType").html('No Data');
	}
	 
	if('${jobRate}' != null && '${jobRate}' != '' && '${jobRate}' != 'jobRate'){
		$("#jobRate").html('${jobRate}');
	}else{
		$("#jobRate").html('No Data');
	}
	
	$("#jobTitle").html("Release to Vendors (${jobTitle})");
	
	socialInviterScripts();
	//getVendorContactLists();
});
</script>

<script>
document.getElementById("copyButton").addEventListener("click", function() {
    copyToClipboard(document.getElementById("referralLink"));
});

function copyToClipboard(elem) {
    var targetId = "_hiddenCopyText_";
    var isInput = elem.tagName === "INPUT" || elem.tagName === "TEXTAREA";
    var origSelectionStart, origSelectionEnd;
    if (isInput) {
        target = elem;
        origSelectionStart = elem.selectionStart;
        origSelectionEnd = elem.selectionEnd;
    } else {
        target = document.getElementById(targetId);
        target.textContent = elem.textContent;
    }
    var currentFocus = document.activeElement;
    target.focus();
    target.setSelectionRange(0, target.value.length);
    var succeed;
    try {
    	  succeed = document.execCommand("copy");
    } catch(e) {
        succeed = false;
    }
    return succeed;
}
</script>

<script>
function releaseTo(){
		var contactsIds = new Array();
		 for(var e in checkedVendorContacts) {
			 if(!e || !checkedVendorContacts[e] || checkedVendorContacts[e] === "")
				   continue;
			 contactsIds.push(checkedVendorContacts[e]);
			 $("#releaseToClientIconDiv"+checkedVendorContacts[e]).empty();
			 $("#releaseToClientIconDiv"+checkedVendorContacts[e]).prepend('<i class="fa fa-cog fa-spin"></i>');
		 }
		 
		var requirementUUID = '${requirementUUID}';
	    var vendorIds = new Array();
	    for(var e in checkedVendors) {
			 if(!e || !checkedVendors[e] || checkedVendors[e] === "")
				   continue;
			 vendorIds.push(checkedVendors[e]);
			 $("#releaseToVendorIconDiv"+checkedVendors[e]).empty();
			 $("#releaseToVendorIconDiv"+checkedVendors[e]).prepend('<i class="fa fa-cog fa-spin"></i>');
		 }
	    
	    var isPublic = $("#isPublicId").val();
	    var isCrowdScourcing = $("#isCrowdScourcingId").val();
	    
	    var isPublicGlobel = $("#isPublicGlobel").val();
	    var isCrowdScourcingGlobel = $("#isCrowdScourcingGlobel").val();
	    var releaseRequirement = true;
	    var percentageRewardValue = '';
	    var jobRecommendations = '${jobRecommendations}';
	    var requirementUUID = '${requirementUUID}';
            
            if(contactsIds.length > 0 || vendorIds.length > 0 || isPublic == "Y" || isCrowdScourcing == "Y" || (isPublicGlobel != isPublic && isPublicGlobel != "") || (isCrowdScourcingGlobel != isCrowdScourcing && isCrowdScourcingGlobel != "")){
	 	    	$('span.jquery_form_error_message').hide();
	 	    	$('#vendorRelease').find('span.jquery_form_error_message').remove();
	 	    	$("#releaseBtn").prop('disabled', true);
	 	    	$("#releaseCancelBtn").prop('disabled', true);
	 			$("#releaseBtn").prop('value', 'Processing');
	 			var atsUrl = '${SourceLeadWeb_atsUrl}';
	 		    var requirementId = '${requirementId}';
	 		    
	 		 	$.ajax({
	 		           type : "POST",
	 		           url: atsUrl+"/releaseJobsToClientsOrVendors?"+tokenParamName+"="+tokenValue+"&requirementId="+requirementId+"&contactsIds="+contactsIds+"&vendorIds="+vendorIds,    
	 		            success :function(result) {
	 		            	if (result.response === "SESSION_TIMEDOUT") {
				  				location.replace(sourceleadUrl);
			                } else if (result.response === "AWKWARD") {
			                    location.href = "/errorPage";
			                } else if (result.response === "SUCCESS") {
			                	if(jobRecommendations){
			 		       			   location.replace(atsUrl+"/jobRecommendations?requirementUUID="+requirementUUID);	
		 		     		    }else{
		 		     		    	   location.replace(atsUrl+"/jobsList");
		 	     		    	 } 	
			                } else if (result.indexOf('Sign In') !== -1) {
								location.replace(sourceleadUrl);
							} else {
			                    showError("Operation failed, please try again");
			                }
	 		            },
	 		    });  
	 	    }else{
	 	    	$('span.jquery_form_error_message').show();
	 	    } 
	   
}

function releaseToOneContact(contactscheckboxId){
	$('input:checkbox[name="contactscheckbox"]').prop('checked',false);
	$("#" + contactscheckboxId).prop('checked', true);
	$("#releaseToClientIconDiv"+contactscheckboxId).empty();
	$("#releaseToClientIconDiv"+contactscheckboxId).prepend('<i class="fa fa-cog fa-spin"></i>');
	checkedVendorContacts[contactscheckboxId] = contactscheckboxId;
	releaseTo();
}

function releaseToOneVendor(vendorcheckboxId){
	$('input:checkbox[name="vendorcheckbox"]').prop('checked',false);
	$("#" + vendorcheckboxId).prop('checked', true);
	
	$("#releaseToVendorIconDiv"+vendorcheckboxId).empty();
	$("#releaseToVendorIconDiv"+vendorcheckboxId).prepend('<i class="fa fa-cog fa-spin"></i>');
	checkedVendors[vendorcheckboxId] = vendorcheckboxId;
	releaseTo();
}
</script>


<script type="text/javascript">
 function releaseToCrowdSourcing() {
	   if ($('#releaseToCrowdSourcing_form').validate(false, validationSettings)){ 
		    var frm = $('#releaseToCrowdSourcing_form').serializeFormJSON();
	        var con = JSON.stringify(frm);
	        con = con.replace(/[{}]/g, "");
	        //It will escape all the special characters
	        var jsonData = encodeURIComponent(con);
	        
	        var atsUrl = '${SourceLeadWeb_atsUrl}';
	        var requirementUUID = '${requirementUUID}';
       	    var requirementId = '${requirementId}';
	        
     		var isPublicGlobel = document.getElementById("isPublicGlobel").value;
     		var isCrowdScourcingGlobel = document.getElementById("isCrowdScourcingGlobel").value;
     		var isContinue = false;
     		
     		if(isPublicGlobel == 'Y' || isCrowdScourcingGlobel == 'Y'){
     			 isContinue = true;
     			 document.getElementById("openNetworkRelease").style.display="none";
     			 $("#releaseToCWBtn").attr("disabled", "disabled");
     			 $("#releaseToCWBtn" ).addClass('button').val('Processing..');
     		}else{
     			 document.getElementById("openNetworkRelease").style.display="";
     		}
     		  var jobRecommendations = '${jobRecommendations}';
     		  var requirementUUID = '${requirementUUID}';
                  
                  var jobBoardAccounts = [];
                  var i = 0;
            var valid = true;
            $("[data-check=jobBoardCheckBox]").each(function() {
                if($(this).is(":checked")) {
                    var val = $("#jobBoardAccount"+$(this).val()).val();
                    if(val === "0" || val === 0) {
                        valid = false;
                    } else {
                        jobBoardAccounts[i++]= val;
                    }
                }
            });
//            jsonData["jobBoardAccounts"] = jobBoardAccounts;
	        if((jobBoardAccounts.length > 0 && valid) || isContinue){
	       	     $.ajax({
			    	type: "POST",
			    	 url: atsUrl+"/releaseToCrowdSourcingNetwork?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&requirementId="+requirementId+"&jobBoardAccounts="+jobBoardAccounts, 
			        success: function(result) {
			        	$("#relTocro").removeAttr("style");
		    			$("#relTocro").css({ display: "block" });
						$("#relMsgDiv").html("Success!! Job Release To Open Network");
		       			relMsgFadeinFunction();
		       			
		       		 if(jobRecommendations){
		       			   location.replace(atsUrl+"/jobRecommendations?requirementUUID="+requirementUUID);	
		     		    }else{
		     		    	location.replace(atsUrl+"/jobsList");
	     		     }
			        	
			        },
			    });  
		        return true;
	        } 
		
	  }else{
		return false;
	  }
    }
 
   $('body').on('blur', '#releaseToCrowdSourcing_form', function() {
		$("#releaseToCrowdSourcing_form").showHelpOnFocus();
		$("#releaseToCrowdSourcing_form").validateOnBlur(false, validationSettings);  
   });
  
 
</script>  

<!-- Show success message after form submission script -->
<script>
	function relMsgFadeinFunction() {
		$("#relTocro").fadeIn(200);
        $('#relTocro').delay(2000).fadeOut('slow');
	}
</script> 
<!-- End -->

<script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
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


<%-- 
     @author : David Raju.
     this script is for get Contacts Lists.
     1.usage     : listChange at line number 409.
     2.parameter : csrf token .
     3.ajax url implementation in requirementListRestController.
--%>


<script>
function getContactsLists(){
	
	var vendorSelectedIds = [];
	$('input:checkbox[name=vendorSelected]:checked').each(function() 
    {
		vendorSelectedIds.push($(this).val());
    });
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var requirementId = '${requirementId}';
	$("#vendorContactsLoaderDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var searchValue = $("#vendorContactDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";

	var url = atsUrl	+"/getAllContactsbyOrgLocationIdAndUserIdForRequirement?searchValue="+searchValue+"&requirementId="+requirementId+"&vendorSelectedIds="+vendorSelectedIds;
	prepareReleaseVendorContactList(url);
	 
}	
function prepareReleaseVendorContactList(url){
	var result = "";
   	var resultVendors = "";
    result = result	+ '<div class="remove_search ">';
    result = result +" <input type='text' placeholder='Search' id='vendorContactDataTableSearch'/>";
	result = result	+ '</div>';
    result = result +'<div class="require_wrap_table release-jobs-tbl overflow_inherint">';
    result = result +'<table id="vendorContactPagination" class="display" cellspacing="0" width="100%">';
   	result = result +'<thead>';
   	result = result +'<tr>';
    result = result +'<th class="text-center"><input type="checkbox" id="contactscheckbox" onclick="toggleVendorContacts(this);"/></th>'; 
   	result = result +'  <th>Name</th>';
   	result = result +'  <th>Email</th>';
   	result = result +' <th>Phone</th>';
   	result = result +' <th>Owner</th>';
   	result = result +' <th>Released On</th>';
   	result = result +' <th>Vendor</th>';
   	result = result +' <th>Release</th>';
   	result = result +'</tr>';
   	result = result +'</thead>';
   	
   	result = result +'</tbody>';
   	result = result +'</table>';
   	result = result +' </div>';
    $("#vendor").empty();
   	$("#vendorContacts").empty();
	$("#vendorContacts").append(result);
	$("#vendorContactsLoaderDiv").removeAttr("style");
	//checkedVendorContacts = {};
	vendorContactPaginationUrl(url);
}
function vendorContactPaginationUrl(url){
	var colIndex = 0;
    $('#vendorContactPagination').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 10
        }),
        "columns": [
        	{ "data": "" },
            { "data": "" },
            { "data": "spocEmail"},
            { "data": "spocMobilePhone"},
            { "data": "" },
            { "data": "stringDate" },
            { "data": "" },
            { "data": "release" }
        ],
	        "columnDefs": [  {
	    	  	"targets": colIndex++,
	    	  	"data": "",
	    	  	"render": function(data, type, report, meta) {
	    	  		$("#vendorStatus").hide();
	   	        	$("input[name='vendorStatus']:checkbox").attr('checked',false);
	   	        	$("#vendorSelected").show();
	    	  		var result = "";
	    	  		var flag = false;
	    	  		if(report.releasedRequirement =="true"){
   	        			result = result +'  <td><i class="text-muted fa fa-check-square" aria-hidden="true"></i></td>';
   	        		}
   	        		if(report.releasedRequirement =="false"){
   	        			
   	        		 for(var e in checkedVendorContacts) {
	   	     	    	   if(!e || !checkedVendorContacts[e] || checkedVendorContacts[e] === "")
	   	     				   continue;
	   	     	    	   if(e === report.id+"") {
	   	     	    		    if(!$("#contactscheckbox").is(":checked"))
	   	     	    		 	checkOrUncheckParent("vendorContactPagination",checkedVendorContacts, "contactscheckbox"); 
	   	     	    	   	flag = true;
	   	     	    	   	break;
	   	     	    	   }
	   	        		 }
	   	        		 if(!flag)
	   	        			$("#contactscheckbox").removeAttr('checked');
	   	        		 
   	        			result = result +'  <td><input'+(flag ? " checked='checked'" : "")+' class="contactscheckbox" name="contactscheckbox"  type="checkbox" id="'+report.id+'" data-id="'+report.id+'" value="'+report.id+'" onchange="checkVendorContacts(this)"/></td>';
   	        		}
   	        		 
	    	  		 return result;
	    	  	}
	      } ,{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		result = result +'<td>';
    			result = result +'<div title="'+report.spocName+'"><span>'+report.spocName+'</span></div>';
    			result = result +'</td>';
		  		 return result;
		     	}
  		  },{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		result = result +'<td>';
    			result = result +'<div title="'+report.spocEmail+'"><span>'+report.spocEmail+'</span></div>';
    			result = result +'</td>';
		  		 return result;
		     	}
  		  },{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		result = result +'<td>';
    			if(null != report.spocMobilePhone && '' != report.spocMobilePhone && 'undefined' != report.spocMobilePhone){
    				result = result +'<div title="'+report.spocMobilePhone+'"><span>'+report.spocMobilePhone+'</span></div>';
    			}else{
    				result = result +'<div title=""><span>---</span></div>';
    			}
		  		 return result;
		     	}
  		  },{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		result = result +'<td>';
    			result = result +'<div title="'+report.ownerName+'"><span>'+report.ownerName+'</span></div>';
    			result = result +'</td>';
		  		 return result;
		     	}
  		  },{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		result = result +'<td>';
    			if(null != report.stringDate && '' != report.stringDate && 'undefined' != report.stringDate){
    				result = result +'<div title="'+report.stringDate+'"><span>'+report.stringDate+'</span></div>';
    			}else{
    				result = result +'<div title=""><span>---</span></div>';
    			}
    			result = result +'</td>';
		  		 return result;
		     	}
  		  },{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		result = result +'<td>';
    			result = result +'<div title="'+report.orgName+'"><span>'+report.orgName+'</span></div>';
    			result = result +'</td>';
		  		 return result;
		     	}
  		  },{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, report, meta) {
		  		var result = "";
		  		result = result +'<td>';
        		if(report.releasedRequirement =="true"){
        			result = result +'<div><b class="proj_active">Released</b></div>';
        		}
				if(report.releasedRequirement =="false"){
					'<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
						result = result +'<div id="releaseToClientIconDiv'+report.id+'"><a href="javascript:void(0)"><i class="fa fa-bullhorn" onclick="releaseToOneContact('+report.id+');"></i></a></div>';
					'</sec:authorize>';
					'<sec:authorize access="!hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
						result = result +'<div class="fa fa-bullhorn"></div>';
					'</sec:authorize>';
        		}
        			result = result +'</td>';
		  		 return result;
		     	}
  		  }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#vendorContactPagination").wrap("<div class='overflow_Auto width100percnt'></div>"); 
    $("input[type=search]").hide();
    $("#vendorContactDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getContactsLists();
    	   }
    	});
}
</script>

<script type="text/javascript">
function getRewardType(rewardType){
	
	 $("#rewardType").val(rewardType);
	 
	 $("#percentageRewardValue").val('');
	 $("#flatRewardValue").val('');
	 
	 $('#percentageReward').find('p.jquery_form_error_message').remove();
	 $('#flatReward').find('p.jquery_form_error_message').remove();
	 
	if(rewardType == 'Percentage'){
		document.getElementById("crowd_rate").style.display="";
		document.getElementById("percentageReward").style.display="";
		document.getElementById("flatReward").style.display="none";
		document.getElementById("flatRateType").style.display="none";
		
		document.getElementById("percentageBtn").className = 'btn btn-sm btn-primary active'
	    document.getElementById("flatBtn").className = 'btn btn-sm btn-primary'
    	$('#percentageRewardValue').attr("data-validation","required validate_int");  
		$('#percentageRewardValue').removeAttr('data-validation-optional');
	    $('#flatRewardValue').removeAttr('data-validation');
	    $('#flatRewardValue').attr("data-validation-optional","true"); 
	    	
	    
	}
	
	if(rewardType == 'Flat'){
		document.getElementById("crowd_rate").style.display="";
		document.getElementById("flatReward").style.display="";
		document.getElementById("flatRateType").style.display="";
		document.getElementById("percentageReward").style.display="none";
		
		document.getElementById("percentageBtn").className = 'btn btn-sm btn-primary'
		document.getElementById("flatBtn").className = 'btn btn-sm btn-primary active'
		$('#flatRewardValue').attr("data-validation","required validate_int");
		$('#flatRewardValue').removeAttr('data-validation-optional');
		$('#percentageRewardValue').removeAttr('data-validation');
		$('#percentageRewardValue').attr("data-validation-optional","true"); 
		
	}
	
	 $("#releaseToCWBtn").removeAttr("disabled");
}
</script>


<%-- 
     @author : David Raju.
     this script is for get VendorLists
     1.usage     : listChange at line number 421.
     2.parameter : csrf token .
     3.ajax url implementation in VendorRestController line number 94.
--%>

<script>
function getVendorLists(){
	var vendorStatus = [];
	$('input:checkbox[name=vendorStatus]:checked').each(function() 
    {
		vendorStatus.push($(this).val());
    });
	
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var requirementId = '${requirementId}';
	$("#vendorLoaderDiv").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var searchValue = $("#vendorDataTableSearch").val();
	searchValue = searchValue ? searchValue : "";
	var url = atsUrl	+"/getVendorListsForReleaseRequirement?searchValue="+searchValue+"&requirementId="+requirementId+"&vendorStatus="+vendorStatus;
	prepareReleaseVendorList(url);
	
}
function prepareReleaseVendorList(url){
	var result = "";
	 result = result	+ '<div class="remove_search ">';
     result = result +" <input type='text' placeholder='Search' id='vendorDataTableSearch'/>";
	result = result	+ '</div>';
    result = result +'<div class="require_wrap_table release-jobs-tbl overflow_inherint datatable_filter">';
    result = result +'<table id="vendorPagination" class="display" cellspacing="0" width="100%">';
   	result = result +'<thead>';
   	result = result +'<tr>';
    result = result +'<th class="text-center"><input type="checkbox" id="checkbox1" onclick="toggleVendors(this);"/></th>'; 
   	result = result +'  <th>Name</th>';
   	result = result +'  <th>Location</th>';
   	result = result +' <th>Released On</th>';
   	result = result +' <th>No. of contacts</th>';
   	result = result +' <th>Level</th>';
   	result = result +' <th>Release</th>';
   	result = result +'</tr>';
   	result = result +'</thead>';
   	result = result +'</table>';
   	result = result +' </div>';
   	$("#vendor").empty();
    $("#vendor").append(result);
	$("#vendorLoaderDiv").removeAttr("style");
	//checkedVendors = {};
	
	vendorPaginationUrl(url);
	
}
function vendorPaginationUrl(url){
	var colIndex = 0;
	var vendorCheckListResult = "";
    $('#vendorPagination').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columns": [
        	{ "data": "" },
            { "data": "orgName" },
            { "data": "location"},
            { "data": "releasedOn"},
            { "data": "noOfContacts" },
            { "data": "level" },
            { "data": "release" }
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "",
	    	  	"render": function(data, type, report, meta) {
	    	  		
	    	  		 /* if(report.noOfContatcs != 0){
   	        			vendorCheckListResult = vendorCheckListResult + '<li><input id="vendorName'+report.orgRelationId+'" name="vendorSelected" value ="'+report.orgRelationId+'" onclick="getContactsLists();" type="checkbox"><label title="'+report.orgName+'">&nbsp;&nbsp; '+report.orgName+'</label></li>';
   	        		}
	    	  		 $("#vendorReleaseCheckList").empty();
           	    	 $("#vendorReleaseCheckList").append(vendorCheckListResult);  */
           	     
           	    	$("#vendorStatus").show();
           	     	$("#vendorSelected").hide();
           	     	$("input[name='vendorSelected']:checkbox").attr('checked',false);
           	     	
           	  	    $("#vendorContacts").empty();  
	    	  		var result = "";
	    	  		var flag = false;
	    	  		
	    	  		if(report.noOfNonReleasedContacts == 0){
   	        	 		result = result +'  <td><i class="text-muted fa fa-check-square" aria-hidden="true"></i></td>';
	        		}else{
	        			
	        			 for(var e in checkedVendors) {
	   	     	    	   if(!e || !checkedVendors[e] || checkedVendors[e] === "")
	   	     				   continue;
	   	     	    	   if(e === report.orgRelationId+"") {
	   	     	    		    if(!$("#checkbox1").is(":checked"))
	   	     	    		 	checkOrUncheckParent("vendorPagination",checkedVendors, "checkbox1"); 
	   	     	    	   	flag = true;
	   	     	    	   	break;
	   	     	    	   }
	   	        		 }
	   	        		 if(!flag)
	   	        			$("#checkbox1").removeAttr('checked');
	        			
	        			result = result +'  <td><input'+(flag ? " checked='checked'" : "")+' class = "vendorcheck" name="vendorcheckbox" type="checkbox" id="'+report.orgRelationId+'" data-id="'+report.orgRelationId+'" value="'+report.orgRelationId+'" onchange="checkVendors(this)"/></td>';
	        		}
	    	  		 return result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "orgName",
	    	  	"render": function(data, type, report, meta) {
	    	  		var result = "";
	    	  		if( report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
   	        			result = result +'<td>';
   	        			result = result +'<div  class="tbldata-dotdot-sm">';
   	        			result = result +'<span title='+report.orgName+'>'+report.orgName+'</span>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
	    	  		return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "location",
              "render": function ( data, type, report, meta ) {
            	    var location = "";
 	        		if(report.city != null && report.city != '' && report.city != 'undefined'){
 	        			location = report.country+", "+report.state+", "+report.city;
 	        		}else if(report.state != null && report.state != '' && report.state != 'undefined'){
 	        			location = report.country+", "+report.state;
 	        		}else{
 	        			location = report.country;
 	        		}
 	        		
            	    var result = "";
            	    if(location != null && location  != '' && location  != 'undefined'){
 	        			result = result +'<td>';
 	        			result = result +'<div  class="tbldata-dotdot-sm">';
 	        			result = result +'<span title="'+location+'">'+location+'</span>';
 	        			result = result +'</div>';
 	        			result = result +'</td>';
 	        		}else{
 	        			result = result +'<td><div>--</div></td>';
 	        		}
            		
        			return result;
              }
	          },{
            "targets": colIndex++,
            "data": "releasedOn",
            "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  result = result +'<td>';
      			if(null != report.stringDate && '' != report.stringDate && 'undefined' != report.stringDate){
      				result = result +'<div title="'+report.stringDate+'"><span>'+report.stringDate+'</span></div>';
      			}else{
      				result = result +'<div title=""><span>---</span></div>';
      			}
      			result = result +'</td>';
      			return result;
            }
          }, {
              "targets": colIndex++,
              "data": "noOfContacts",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  result = result +'<td><div class="tbldata-dotdot-sm">'+report.noOfContatcs+'</div></td>';
            	  return result; 
              }
            }, {
                "targets": colIndex++,
                "data": "level",
                "render": function ( data, type, report, meta ) {
                	 var result = "";
                	result = result +' <td><div class="tbldata-dotdot-sm"><span  title="'+report.level+'">'+report.level+'</span></td>';
    	       		result = result +'<td>';
    	       	 return result; 
                }
          }, {
              "targets": colIndex++,
              "data": "release",
              "render": function ( data, type, report, meta ) {
            	  var result = "";
            	  if(report.noOfNonReleasedContacts == 0){
          			result = result +'<div><b class="proj_active">Released</b></div>';
          		  }else if(report.noOfNonReleasedContacts > 0){
							'<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
								result = result +'<div id="releaseToVendorIconDiv'+report.orgRelationId+'"><a href="javascript:void(0)"><i class="fa fa-bullhorn" onclick="releaseToOneVendor('+report.orgRelationId+');"></i></a></div>';
							'</sec:authorize>';
							'<sec:authorize access="!hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
								result = result +'<div class="fa fa-bullhorn"></div>';
							'</sec:authorize>';
          		}
            	return result;
              }
        }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     } 
    } );
    $("#vendorPagination").wrap("<div class='overflow_Auto width100percnt'></div>"); 
    $("input[type=search]").hide();
    $("#vendorDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getVendorLists();
    	   }
    	});
}


function checkOrUncheckParent(tableId, selectedItems, checkboxId) {
	$("#"+checkboxId).removeProp("checked");
	   var count = 0;
	   var data = $("#"+tableId).DataTable().data();
	 for(var e in selectedItems) {
	   if(!e || !selectedItems[e] || selectedItems[e] === "")
		   continue;
	   
	   for(var f in data) {
   	   if(e === data[f].id+"") {
   		count ++;
   	   }
		}
	 }
	 if(count == data.count()) {
		$("#"+checkboxId).prop("checked", "checked");
	 }
}

</script>



<script>
var checkedVendors = {};
function checkVendors(elem) {
	if($(elem).is(":checked")) {
		checkedVendors[$(elem).data("id")] = $(elem).val();
	} else{
		$("#checkbox1").removeProp('checked');
		checkedVendors[$(elem).data("id")] = "";
	}
}
</script>

<script>
var checkedVendorContacts = {};
function checkVendorContacts(elem) {
	if($(elem).is(":checked")) {
		checkedVendorContacts[$(elem).data("id")] = $(elem).val();
	} else{
		$("#contactscheckbox").removeProp('checked');
		checkedVendorContacts[$(elem).data("id")] = "";
	}
}
</script>



<script>
// accordion script
$(document).ready(function(){
    $(function() {
    $('#accordion-sl .content').hide();
    $('#accordion-sl h2:first').addClass('active').next().slideDown('slow');
    $('#accordion-sl h2').click(function() {
        if($(this).next().is(':hidden')) {
            $('#accordion-sl h2').removeClass('active').next().slideUp('slow');
            $(this).toggleClass('active').next().slideDown('slow');
        }
    });
});
});
</script>



<%-- 
     @author : David Raju.
     this script is for get VendorLists
     1.usage     : listChange at line number 33.
--%>


<script type="text/javascript">
 function listChange(sel) {
    	if(sel.value=="releaseByVendors"){
    		document.getElementById("vendor").style.display="block";
			document.getElementById("vendorContacts").style.display="none";
			getVendorLists();
			$('span.jquery_form_error_message').hide();
        }
		 if(sel.value=="releaseByContact"){
			document.getElementById("vendorContacts").style.display="block";
			document.getElementById("vendor").style.display="none";
			getContactsLists();
			$('span.jquery_form_error_message').hide();
        }
    }
</script>

<script>
function toggle(source) {
    var checkboxes = [];
    checkboxes = document.querySelectorAll('input[name="vendorcheckbox"]');
    if(checkboxes.length < 1){
    	checkboxes = [];
    }
    var contactCheckboxes = [];
    contactCheckboxes = document.querySelectorAll('input[name="contactscheckbox"]');
    $.merge( checkboxes, contactCheckboxes)
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
    }
$('input:checkbox:not(#contactscheckbox)').click(function() {
    if(!this.checked) {
        $('#contactscheckbox').removeAttr('checked');
    }
});

$('input:checkbox:not(#checkbox1)').click(function() {
    if(!this.checked) {
        $('#checkbox1').removeAttr('checked');
    }
});
}
</script>


<script>
function toggleVendors(source) {
  var checkboxes = document.querySelectorAll('input[class="vendorcheck"]');
  if($(source).is(":checked"))
	  $('input[class="vendorcheck"]').prop('checked', 'checked');
  else 
	  $('input[class="vendorcheck"]').removeProp('checked');
  
  $('input[class="vendorcheck"]').each(function(i) {
	  if($(this).is(":checked")) {
	      checkedVendors[$(this).data("id")] = $(this).val();
	  } else {
		  checkedVendors[$(this).data("id")] = "";
	  }
  });
}


function toggleVendorContacts(source){
	  var checkboxes = document.querySelectorAll('input[class="contactscheckbox"]');
	  if($(source).is(":checked"))
		  $('input[class="contactscheckbox"]').prop('checked', 'checked');
	  else 
		  $('input[class="contactscheckbox"]').removeProp('checked');
	  
	  $('input[class="contactscheckbox"]').each(function(i) {
		  if($(this).is(":checked")) {
		      checkedVendorContacts[$(this).data("id")] = $(this).val();
		  } else {
			  checkedVendorContacts[$(this).data("id")] = "";
		  }
	  });
}
</script>






<script>

//dropdown script

$(document).ready(function() {
    $("#export-pop").click(function(){
		//$(".approved_pop").toggle();
	    $(this).next(".approved_pop").slideToggle(200);
	});
    $("#export-popp").click(function(){
		//$(".approved_pop").slideToggle(200);
    	 $(this).next(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #export-pop, #export-popp").click(function(e) {
        e.stopPropagation();
    });	
});	
</script>

<script>
function ispublic(){
	 if(document.getElementById('isPublicId').checked) {
	        $("#isPublicId").val('Y');
	        $("#isPublicGlobel").val('Y');
	    }else{
	        $("#isPublicId").val('N');
	        $("#isPublicGlobel").val('N');
	    }
}
</script>

<script>
function isCrowd(){
	 if(document.getElementById('isCrowdScourcingId').checked) {
	        $("#isCrowdScourcingId").val('Y');
	        $("#isCrowdScourcingGlobel").val('Y');
	        
	        document.getElementById("crowd_rate").style.display="";
	        
	        
	        $('#percentageRewardValue').removeAttr("data-validation-optional"); 
		    $('#percentageRewardValue').attr("data-validation","required");
		       
	        var rewardType = '${rewardType}';
	        if('${rewardType}' != null && '${rewardType}' != '' && '${rewardType}' != 'undefined'){
	        	 getRewardType('${rewardType}')
	        }else{
	        	 getRewardType('Percentage')
	        }
	       
	    }else{
	       
	    	$("#isCrowdScourcingId").val('N');
	        $("#isCrowdScourcingGlobel").val('N');
	        // here validation remove and data-validation-optional equal to true.
	        $('#percentageRewardValue').find('jquery_form_error_message').remove();
	        $('#percentageRewardValue').attr("data-validation-optional","true"); 
	        $('#percentageRewardValue').removeAttr('data-validation');
	        
	        document.getElementById("crowd_rate").style.display="none";
	        
	    }
}
</script>

<script>
function opennetwork(){
	
	document.getElementById("releaseToCrowdSourcing").style.display="";
	document.getElementById("releaseToVendors").style.display="none";
	
	var atsUrl = '${SourceLeadWeb_atsUrl}';
	var requirementId = '${requirementId}';
	  $.ajax({
		   	type: "GET",
		    url: atsUrl+"/getOpenNetworkStatus?requirementId="+requirementId, 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   		    	if(json.isPublic == "Y"){
   		    	 $("#isPublicId").prop('checked', true).val('Y');
   		    	}
   		    	if(json.isCrowdSourcing == "Y"){
      		    	 $("#isCrowdScourcingId").prop('checked', true).val('Y');
      		    	 var rewardType = '${rewardType}';
	      		       if('${rewardType}' != null && '${rewardType}' != '' && '${rewardType}' != 'undefined'){
	      		       	 getRewardType('${rewardType}')
	      		       	 
	      		        $("#percentageBtn").attr("disabled", "disabled");
	      		        $("#flatBtn").attr("disabled", "disabled");
	      		       	 
	      		       	 
	      		       if('${rewardPercentage}' != null && '${rewardPercentage}' != '' && '${rewardPercentage}' != 'rewardPercentage'){
	      		    	 $("#percentageRewardValue").val('${rewardPercentage}');
	      		    	 $("#percentageRewardValue").attr("disabled", "disabled");
	      		       }
	      		       
	      		     if('${rewardFlat}' != null && '${rewardFlat}' != '' && '${rewardFlat}' != 'rewardFlat'){
	      		    	 $("#flatRewardValue").val('${rewardFlat}');
	      		    	 $("#flatRewardValue").attr("disabled", "disabled");
	      		    	 
	      		    	 $("#flatRateType").val('${rewardFlatRateType}');
	      		    	 $("#flatRateType").attr("disabled", "disabled");
	      		       }
	      		       
		      		     $("#guaranteePeriod").val('${rewardGauranteePeriod}');
		      		     $("#guaranteePeriod").attr("disabled", "disabled");
	      		       	 
	      		       	 
	      		       	 
	      		       }else{
	      		       	 getRewardType('Percentage')
	      		       }
      		    }
   		      $("#isPublicGlobel").val(json.isPublic);
   		      $("#isCrowdScourcingGlobel").val(json.isCrowdSourcing);
   		      if(json.isCrowdSourcing == 'Y'){
   		    	$("#isCrowdScourcingId").attr("disabled", "disabled");
   		    	
   		      }
   		    }
	  });
	  
	 
}
</script>

<script>
function openVendors(){
	document.getElementById("releaseToCrowdSourcing").style.display="none";
	document.getElementById("releaseToVendors").style.display="";
}
</script>

<script>
 function socialPostSucess(service,status){
	 msgFadein();
     $("#msgDiv").html(" Job is Successfully Posted on Your "+service+" Wall.");
 }
</script>

<script type="text/javascript">
var postdatavalues = { title: "", link: "", picture: "", description: "", comment: "" };
function setPostValues(title,link,picture,description,comment){

	 if (title) {
         postdatavalues.title = title;
     }
     if (link) {
         postdatavalues.link = link;
     }
     if (picture) {
         postdatavalues.picture = picture;
     }
     if (description) {
         postdatavalues.description = description;
     }
     if (description) {
         postdatavalues.comment = description;
     }
};

function getPostValueDetails(){
	 return postdatavalues;
  }

</script>



<script>
$("#selectorReleaseMsg").fadeIn(200);
$('#selectorReleaseMsg').delay(2000).fadeOut('slow');
	function msgFadein() {
		$("#selector").fadeIn(200);
        $('#selector').delay(2000).fadeOut('slow');
}
</script> 
<style>
.footer-area {
display: none;}
</style>
<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5a60382a93e935c2"></script>
<jsp:include page="../socialinviterscripts.jsp" />