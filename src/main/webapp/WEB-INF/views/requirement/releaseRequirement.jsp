<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Content Area Starts -->
  <section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Release Jobs</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Release Jobs</div>
    </section>
    <div class="content-area clearfix">
      <div id="accordion-sl" class="release-jobs">
      <!-- Accordion First Tab -->
        <div class="clearfix">
          <h2>Profile</h2>
          <div class="content clearfix">
            <div class="vendor_wrap">
              <div class="col-md-2 custom-selectpicker mt10 mb10">
                <select class="form-control selectpicker"  id="sele-rel"  onchange="listChange(this);">
                  <option id="cont" value ="releaseByContact">Release By Contact</option>
                  <option id="ven"  value ="releaseByVendors" selected="selected">Release By  Vendors</option>
                </select>
              </div>
              <div class="timesheet_head">
                <ul>
                  <li class="li-vendor hidden">
                    <input type="text" class="vendorfild">
                    <i class="fa fa-search"></i>|</li>
                  <li id="vendorStatus"><i class="fa fa-filter"></i><span>Tier</span><i class="glyphicon glyphicon-play" id="export-pop"></i>
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
                  <li id="vendorSelected" style="display:none"><i class="fa fa-filter"></i><span>Vendor</span><i class="glyphicon glyphicon-play" id="export-popp"></i>
                    <div class="approved_pop">
                      <ul>
                      	  <c:forEach var="report" items="${vendor_list}">  
	                      	<li><input id="vendorName${report.id}" name="vendorSelected" value ="${report.id}" onclick="getContactsLists();" type="checkbox"><label title="${report.clientName}">&nbsp;&nbsp; ${report.clientName}</label></li>
						  </c:forEach>
                      </ul>
                    </div>
                  </li>
                  <div class="clearfix"></div>
                </ul>
                <div class="clearfix"></div>
              </div>
              <!-- Contact Table -->
              
              <div id="vendorContacts"> </div>
             
              <!-- Contact Table Ends --> 
              
              <!-- Vendor Table -->
              
              <div id="vendor"></div>
              
              <!-- Vendor Table Ends --> 
            </div>
          </div>
        </div>
	  <!-- Accordion First Tab Ends -->

      <!-- Accordion second Tab -->
        <!-- <div class="clearfix">
          <h2 onclick="opennetwork();">Open Network</h2>
          <div class="content clearfix" >
            <div class="row">
              <div class="col-md-6">
                <div class="row">
                  <div class="form-group clearfix toggle-button-lbl">
                    <label class="col-md-5">Public</label>
                    <input type="hidden" id="isPublicGlobel" value="">
                    <div class="col-md-7 toggle-button">
                      <label class="switch">
                      <input type="checkbox" name="isPublic" id="isPublicId"  value="N" onclick="ispublic();">
                      <div class="slider round"></div>
                      </label>
                    </div>
                  </div>
                  <div class="form-group clearfix toggle-button-lbl">
                    <label class="col-md-5">Crowd Sourcing</label>
                    <input type="hidden" id="isCrowdScourcingGlobel" value="">
                    <div class="col-md-7 toggle-button">
                      <label class="switch">
                      <input type="checkbox" name="isCrowdScourcing" value="N" id="isCrowdScourcingId" onclick="isCrowdScourcing();" >
                      <div class="slider round"></div>
                      </label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div> -->
      <!-- Accordion second Tab Ends -->
      </div>
      <div class="form-footer clearfix row mt">
        <div class="col-lg-12">
          <div class="pull-right">
            <span class="jquery_form_error_message" style="display:none;">Vendor/Contact/Open Network selection is mandatory</span>
            <sec:authorize access="hasAnyRole('ROLE_RELEASE_REQUIREMENT')">
            	<input type="button" class="btn btn-primary ml" id="releaseBtn" onclick="releaseTo();" value="Release">
			</sec:authorize>
            <input type="button" class="btn btn-default ml" id="releaseCancelBtn" value="Cancel" onClick="javascript:location.href = '<c:url value='requirementList'/>'">
          </div>
        </div>
      </div>
    </div>
  </section>
<!-- Content Area Starts -->

<script> 
$(document).ready(function(){
	getVendorLists();
	//getVendorContactLists();
});
</script>
<script>
function releaseTo(){
	    var contactsIds = new Array();
	    $('input:checkbox[name=contactscheckbox]:checked').each(function() 
	    {
	    	contactsIds.push($(this).attr('id'));
	    });
	    
	    var vendorIds = new Array();
	    $('input:checkbox[name=vendorcheckbox]:checked').each(function() 
	    {
	    	vendorIds.push($(this).attr('id'));
	    });
	    var isPublic = $("#isPublicId").val();
	    var isCrowdScourcing = $("#isCrowdScourcingId").val();
	    
	    var isPublicGlobel = $("#isPublicGlobel").val();
	    var isCrowdScourcingGlobel = $("#isCrowdScourcingGlobel").val();
	    
	    if(contactsIds.length > 0 || vendorIds.length > 0 || isPublic == "Y" || isCrowdScourcing == "Y" || (isPublicGlobel != isPublic && isPublicGlobel != "") || (isCrowdScourcingGlobel != isCrowdScourcing && isCrowdScourcingGlobel != "")){
	    	$('span.jquery_form_error_message').hide();
	    	$("#releaseBtn").prop('disabled', true);
	    	$("#releaseCancelBtn").prop('disabled', true);
			$("#releaseBtn").prop('value', 'Processing');
		    var sourceleadUrl ='${sourceleadUrl}';
		    var requirementId = '${requirementId}';
			$.ajax({
		           type : "POST",
		           url: sourceleadUrl+"/releaseJobsToClientsOrVendors?${_csrf.parameterName}=${_csrf.token}&requirementId="+requirementId+"&contactsIds="+contactsIds+"&vendorIds="+vendorIds+"&isCrowdScourcing="+isCrowdScourcing+"&isPublic="+isPublic,    
		            success :function(data) {
							location.replace(sourceleadUrl+"/requirementList");
		            },
		    }); 
	    }else{
	    	$('span.jquery_form_error_message').show();
	    }
}

function releaseToOneContact(contactscheckboxId){
	$('input:checkbox[name="contactscheckbox"]').prop('checked',false);
	$("#" + contactscheckboxId).prop('checked', true);
	releaseTo();
}
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
	alert("sss");
	var vendors = "Client";
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var requirementId = '${requirementId}';
	  $.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/getAllContactsbyOrgLocationIdAndUserIdForRequirement?requirementId="+requirementId+"&vendors="+vendors+"&status="+status, 	   
   		    success: function(data) {
   		    	if(data != null && data != 'undefined' && data != "" && data != '[]'){
   		    		if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
               		 else if (data.indexOf('exception') >= 0) {
               			 location.replace(sourceleadUrl+"/errorPage");
               		 }
               		 else{
			   		    	var json = JSON.parse(data);
			   	        	var result = "";
			   	        	var resultVendors = "";
			                result = result +'<div class="require_wrap_table release-jobs-tbl">';
			                result = result +'<table id="candidatePagination" class="display" cellspacing="0" width="100%">';
			   	        	result = result +'<thead>';
			   	        	result = result +'<tr>';
			   	            result = result +'<th><input type="checkbox" id="contactscheckbox" onclick="toggle(this);"/></th>'; 
			   	        	result = result +'  <th>Name</th>';
			   	        	result = result +'  <th>Email</th>';
			   	        	result = result +' <th>Phone</th>';
			   	        	result = result +' <th>Owner</th>';
			   	        	result = result +' <th>Vendor</th>';
			   	        	result = result +' <th>Release</th>';
			   	        	result = result +'</tr>';
			   	        	result = result +'</thead>';
			   	        	result = result +'<tbody>';
			   	        	for (var i=0; i<json.length; i++)
			   				{
			   	        		var report = json[i];
				   	        		resultVendors = resultVendors + '';
				   	        		result = result +'<tr>';
				   	        		if(report.releasedRequirement =="true"){
				   	        			result = result +'  <td><input type="checkbox" checked disabled readonly id="'+report.id+'"/></td>';
				   	        		}
				   	        		if(report.releasedRequirement =="false"){
				   	        			result = result +'  <td><input name="contactscheckbox" type="checkbox" id="'+report.id+'"/></td>';
				   	        		}
					        			result = result +'<td>';
					        			result = result +'<div title="'+report.spocName+'"><span>'+report.spocName+'</span></div>';
					        			result = result +'</td>';
					        			result = result +'<td>';
					        			result = result +'<div title="'+report.spocEmail+'"><span>'+report.spocEmail+'</span></div>';
					        			result = result +'</td>';
					        			result = result +'<td>';
					        			result = result +'<div title="'+report.spocMobilePhone+'"><span>'+report.spocMobilePhone+'</span></div>';
					        			result = result +'</td>';
					        			result = result +'<td>';
					        			result = result +'<div title="'+report.ownerName+'"><span>'+report.ownerName+'</span></div>';
					        			result = result +'</td>';
					        			result = result +'<td>';
					        			result = result +'<div title=""><span>'+report.orgName+'</span></div>';
					        			result = result +'</td>';
					        			result = result +'<td>';
					        		if(report.releasedRequirement =="true"){
					        			result = result +'<div><b class="proj_active">Released</b></div>';
					        		}
									if(report.releasedRequirement =="false"){
										'<sec:authorize access="hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
											result = result +'<div><a href="javascript:void(0)"><i class="fa fa-bullhorn" onclick="releaseToOneContact('+report.id+');"></i></a></div>';
										'</sec:authorize>';
										'<sec:authorize access="!hasAnyRole(\'ROLE_RELEASE_REQUIREMENT\')">';
											result = result +'<div class="fa fa-bullhorn"></div>';
										'</sec:authorize>';
					        		}
					        			result = result +'</td>';
				   	        		    result = result +'</tr>';
			   				}
			   	        	result = result +'</tbody>';
			   	        	result = result +'</table>';
			   	        	result = result +' </div>';
			   	        	
			   	        	$("#vendorStatus").hide();
			   	        	$("#vendorSelected").show();
			   	        
			   	    	    $("#vendor").empty();
			   	    	 	$("#vendorContacts").empty();
			   	    	    $("#vendorContacts").append(result);
			   	    	 	candidatePagination();
               		}
	   		    	}else{
	               	 $("#vendorContacts").removeAttr("style");
		    	     var result ="";
					 result = result+"	<div class='blank-pages'>";
					 result = result+"		<p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"		<p>No Contacts Found</p>";
					 result = result+"	</div>";
					$("#vendorStatus").hide();
					$("#vendorContacts").empty();
				    $("#vendorContacts").append(result); 
	            }
   		    },
	 });
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
	
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var requirementId = '${requirementId}';
	
	  $.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/getVendorListsForReleaseRequirement?requirementId="+requirementId+"&vendorStatus="+vendorStatus, 	   
   		    success: function(data) {
   		    	/* if(data != null && data != 'undefined' && data != "" && data != '[]'){ */
	               			var json = JSON.parse(data);
	           	        	var result = "";
	                        result = result +'<div class="require_wrap_table release-jobs-tbl">';
	                        result = result +'<table id="vendorPagination" class="display" cellspacing="0" width="100%">';
	           	        	result = result +'<thead>';
	           	        	result = result +'<tr>';
	           	            result = result +'<th><input type="checkbox" id="checkbox1" onclick="toggle(this);"/></th>'; 
	           	        	result = result +'  <th>Name</th>';
	           	        	result = result +'  <th>Location</th>';
	           	        	result = result +' <th>No. of contacts</th>';
	           	        	result = result +' <th>Level</th>';
	           	        	result = result +' <th>Release</th>';
	           	        	result = result +'</tr>';
	           	        	result = result +'</thead>';
	           	        	result = result +'<tbody>';
	           	        	for (var i=0; i<json.length; i++)
	           				{  
	           	        		var report = json[i];
	           	        		var location = "";
	           	        		if(report.city != null && report.city != '' && report.city != 'undefined'){
	           	        			location = report.country+", "+report.state+", "+report.city;
	           	        		}else if(report.state != null && report.state != '' && report.state != 'undefined'){
	           	        			location = report.country+", "+report.state;
	           	        		}else{
	           	        			location = report.country;
	           	        		}
	           	        		
	           	        		result = result +'<tr>';
	           	        	    
	           	        	 	if(report.noOfNonReleasedContacts == 0){
	           	        	 		result = result +'  <td><input type="checkbox" checked disabled readonly id="'+report.id+'" /></td>';
	        	        		}else{
	        	        			result = result +'  <td><input name="vendorcheckbox" type="checkbox" id="'+report.id+'" /></td>';
	        	        		}
	           	        	 
	           	        		if( report.orgName != null && report.orgName != '' && report.orgName != 'undefined'){
	           	        			result = result +'<td>';
	           	        			result = result +'<div  class="tbldata-dotdot-sm text-left">';
	           	        			result = result +'<span title='+report.orgName+'>'+report.orgName+'</span>';
	           	        			result = result +'</div>';
	           	        			result = result +'</td>';
	           	        		}else{
	           	        			result = result +'<td><div>--</div></td>';
	           	        		}
	           	        		
	           	        		if(location != null && location  != '' && location  != 'undefined'){
	           	        			result = result +'<td>';
	           	        			result = result +'<div  class="tbldata-dotdot-sm text-left">';
	           	        			result = result +'<span title="'+location+'">'+location+'</span>';
	           	        			result = result +'</div>';
	           	        			result = result +'</td>';
	           	        		}else{
	           	        			result = result +'<td><div>--</div></td>';
	           	        		}
	        	       		result = result +'<td><div class="tbldata-dotdot-sm text-left">'+report.noOfContatcs+'</div></td>';
	        	       		result = result +' <td><div class="tbldata-dotdot-sm text-left"><span  title="'+report.level+'">'+report.level+'</span></td>';
	        	       		result = result +'<td>';
	        	       		if(report.noOfNonReleasedContacts == 0){
	                			result = result +'<div><b class="proj_active">Released</b></div>';
	                		}
	                		result = result +'</td>';
	           	        	result = result +'</tr>';
	           				}
	           	        	result = result +'</tbody>';
	           	        	result = result +'</table>';
	           	        	result = result +' </div>';
	           	        	
	           	        	$("#vendorStatus").show();
	           	        	$("#vendorSelected").hide();
	           	        	
	           	    	    $("#vendorContacts").empty();  
	           	    	 	$("#vendor").empty();
	           	    	    $("#vendor").append(result);
	           	    	 	vendorPagination();
   		    		
	   		    	/* }else{
	               	 $("#vendor").removeAttr("style");
		    	     var result ="";
					 result = result+"	<div class='blank-pages'>";
					 result = result+"		<p><i class='fa fa-5x fa-bar-chart'></i></p>";
					 result = result+"		<p>No Vendors Found</p>";
					 result = result+"	</div>";
					 $("#vendorSelected").hide();
					 $("#vendorStatus").hide();
					 $("#vendor").empty();
				     $("#vendor").append(result); 
	            } */
   		    },
	 });
}	
</script>



<script>
function vendorPagination(){  
   	$('#vendorPagination').DataTable( { 
       	"scrollCollapse": false,
       	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
       	"bLengthChange": false,
       	"bDestroy": true,
       	"ordering": false,
       	"oLanguage": {
        		"sSearch": ""
        	}
       } );
}
</script>

<script>
function candidatePagination(){ 
    $('#candidatePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
    } );
}
</script>
<!-- 
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
</script>  -->



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
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
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
	    }else{
	        $("#isPublicId").val('N');
	    }
}
</script>

<script>
function isCrowdScourcing(){
	 if(document.getElementById('isCrowdScourcingId').checked) {
	        $("#isCrowdScourcingId").val('Y');
	    }else{
	        $("#isCrowdScourcingId").val('N');
	    }
}
</script>

<script>
function opennetwork(){
	var sourceleadUrl ='${sourceleadUrl}';
	var requirementId = '${requirementId}';
	  $.ajax({
		   	type: "GET",
		    url: sourceleadUrl+"/getOpenNetworkStatus?requirementId="+requirementId, 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   		    	if(json.isPublic == "Y"){
   		    	 $("#isPublicId").prop('checked', true).val('Y');
   		    	}
   		    	if(json.isCrowdSourcing == "Y"){
      		    	 $("#isCrowdScourcingId").prop('checked', true).val('Y');
      		    }
   		      $("#isPublicGlobel").val(json.isPublic);
   		      $("#isCrowdScourcingGlobel").val(json.isCrowdSourcing);
   		    }
	  });
}
</script>
<jsp:include page="../masterFooter.jsp" />