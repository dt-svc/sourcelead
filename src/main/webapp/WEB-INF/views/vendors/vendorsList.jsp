<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/chosen.min.css">
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<script src="resources/js/chosen.jquery.min.js"></script> 
<script src="resources/js/bootstrap-select.min.js"></script>
<link rel="stylesheet" href="resources/css/bootstrap-select.min.css">

<!-- these files for autocomplete with tagit -->
<link href="resources/css/jquery-tagit.css" rel="stylesheet">
<!-- end files for autocomplete with tagit -->

<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Vendors<span class="select-fild">
                	<select id="listOfVendors" onchange="listChange(this);">
                  		 <option value="listbyVendor">List by vendor</option>
                        <option value="listbyContact" selected="selected">List by contact</option> 
                    </select>
                </span>            	
            </h2>
          </div>
          <div class="pull-right brud-crum"><a href="dashboard">Home</a> &raquo; Vendors list </div>
        </section>
        
       
       
		<!-- Project -->
		 <div id="loading"></div>
<div class="timesheet_out">

<div class="vendor_wrap">
	<div class="timesheet_head datatbl-filters">
        	<ul>
        	
        	<li id="vendorsList" ><i class="fa fa-filter"></i><span>Vendor</span><i class="glyphicon glyphicon-play"  id="filter1ApprovePop"></i>|
		                	<div id="filter1ApprovedPop" class="approved_pop">
		                    	<ul>
		                    	  <c:forEach var="report" items="${vendor_list}">  
		                        	<li><input  id="vendorName${report.id}" name="vendorNameById" value ="${report.id}"  onclick="getVendorContactLists();"  type="checkbox"><label>${report.clientName}</label></li>
								  </c:forEach>
		                        </ul>
		                    </div>                	
		                </li>
	               
		                <li id="vendorSatatus"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" id="filter3ApprovePop"></i>|
	                	<div id="filter3ApprovedPop" class="approved_pop">
	                    	<ul>
	                        	<li><input id="c5" type="checkbox"  name="vendorStatus"  value ="Active"      onclick="getVendorContactLists();"  ><label>Active</label></li>
								<li><input id="c6" type="checkbox"  name="vendorStatus"  value ="In-Active"   onclick="getVendorContactLists();"><label>In-Active</label></li>
								<li><input id="c7" type="checkbox"  name="vendorStatus"  value ="New"         onclick="getVendorContactLists();"><label>New</label></li>
								<li><input id="c8" type="checkbox"  name="vendorStatus"  value ="Negotiation" onclick="getVendorContactLists();"><label>Negotiation</label></li>
	               				<li><input id="c9" type="checkbox"  name="vendorStatus"  value ="Contracted"  onclick="getVendorContactLists();"><label>Contracted</label></li>
	                        </ul>
	                      </div>                	
	                  </li>
	                  <li id="vendorInvitation"><i class="fa fa-filter"></i><span>Invitation</span><i class="glyphicon glyphicon-play" id="filter4ApprovePop"></i>|
	                	<div id="filter4ApprovedPop" class="approved_pop">
	                    	<ul>
	                        	<li><input id="c5" type="checkbox"  name="vendorInvitationStatus"  value ="Sent"      onclick="getVendorContactLists();"  ><label>Resend</label></li>
								<li><input id="c6" type="checkbox"  name="vendorInvitationStatus"  value ="New"   onclick="getVendorContactLists();"><label>Send</label></li>
								<li><input id="c7" type="checkbox"  name="vendorInvitationStatus"  value ="Accepted"         onclick="getVendorContactLists();"><label>Accepted</label></li>
								<li><input id="c8" type="checkbox"  name="vendorInvitationStatus"  value ="Rejected" onclick="getVendorContactLists();"><label>Rejected</label></li>
	                        </ul>
	                      </div>                	
	                  </li>
                  
	                
						<li id="contactLevel" style="display:none"><i class="fa fa-filter"></i><span>Level</span><i class="glyphicon glyphicon-play" id="filter2ApprovePop"></i>
		                	<div id="filter2ApprovedPop" class="approved_pop">
		                    	<ul>
									<li><input id="c6"  type="checkbox" name="clinetLevel" value ="Level-1"  onclick="getVendorLists();"><label>Level-1</label></li>
									<li><input id="c7"  type="checkbox" name="clinetLevel" value ="Level-2"  onclick="getVendorLists();"><label>Level-2</label></li>
									<li><input id="c8"  type="checkbox" name="clinetLevel" value ="Level-3"  onclick="getVendorLists();"><label>Level-3</label></li>
		               				<li><input id="c8"  type="checkbox" name="clinetLevel" value ="Level-4"  onclick="getVendorLists();"><label>Level-4</label></li>
		               				<li><input id="c9"  type="checkbox" name="clinetLevel" value ="Level-5"  onclick="getVendorLists();"><label>Level-5</label></li>
		                        </ul>
		                    </div>                	
		                </li>          
				<li><a href="<c:url value='/addVendorContact'/>"><i class="fa fa-plus"></i>Contact</a></li>	
                       <li><a href="<c:url value='/addVendor'/>"><i class="fa fa-plus"></i>Vendor</a></li>	
                       <li><a href="<c:url value='/uploadVendorForm'/>"><i class="fa fa-upload"></i>&nbsp;Imports</a></li>	
            </ul>
            <div class="clearfix"></div>
        </div>
    <div class="clients_wrap_table">
    	<table id="vendorList" class="display" cellspacing="0" width="100%"> </table>
    	<table id="vendorContactList" class="display" cellspacing="0" width="100%"> </table> 
   </div>	
</div>

<div class="side_mask"><div class="proj_close"></div></div>

<!-- Client Side Nav Div -->
<div class="clients_side_wrap">
			<div class="clients_side_wrap_inn">
				<h1 id="vendorNameDiv"></h1>
				<div class="proj_close">
					<i></i>
				</div>
				<div id="vendorSideDiv"></div>
			</div>
		</div>

 </div>
<!-- Client Side Nav Div Ends -->
</section>

<script> 
$(document).ready(function(){
	getVendorContactLists();
    listChange("listbyContact");
	document.getElementById('listOfVendors').value = 'listbyContact';
});
</script>

<script>
function getVendorLists(){
	var values = [];
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
    });
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorLists?${_csrf.parameterName}=${_csrf.token}&values="+values, 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   	        	
   	        	result = result +'<thead>';
   	        	result = result +'<tr>';
   	        	result = result +' <th>Name</th>';
   	        	result = result +' <th>Website</th>';
   	        	result = result +'<th>Location</th>';
   	        	result = result +'<th>No.of Contacts</th>';
   	        	result = result +'<th>Released jobs</th>';
   	        	result = result +'<th>Level</th>';
   	        	/* result = result +'<th>Actions</th>'; */
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
   	        			
   	        			result = result +'<td>';
   	        			result = result +'<div class="proj_show" onclick="vendorSideBar(\''+report.vendorlocationId+'\',\''+report.id+'\',\''+report.orgName+'\');"><span class="tbldata-dotdot-vsm" title="${report.responderLocationId.org.orgName}">'+report.orgName+'</span><i class="fa fa-binoculars"></i>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		
   	        		if( report.webSite != null && report.webSite != '' && report.webSite != 'undefined'){
   	        			result = result +'<td>';
   	        			result = result +'<div>';
   	        			result = result +'<span class="tbldata-dotdot-sm" title='+report.webSite+'>'+report.webSite+'</span>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		
   	        		if(location != null && location  != '' && location  != 'undefined'){
   	        			result = result +'<td>';
   	        			result = result +'<div>';
   	        			result = result +'<span class="tbldata-dotdot-me" title="'+location+'">'+location+'</span>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
	       		
	       		result = result +'<td><div>20</div></td>';
	       		result = result +' <td><div>10</div></td>';
	       		result = result +' <td><div><span class="tbldata-dotdot-vsm" title="'+report.level+'">'+report.level+'</span>';
	       		/* result = result +' <td><div><i class="glyphicon glyphicon-trash"></i></div></td>'; */
   	        		result = result +'</tr>';
   				}
   	        	
   	        	result = result +'</tbody>';
   	    	    $("#vendorList").empty();  
   	    	    $("#vendorList").append(result)
   	    	    pagination1();
   	    	    
   		    }
	 });
	
	
}	
</script> 

 <script>
function pagination1(){  
	document.getElementById("vendorContactList").style.display="none";
    $('#vendorList').DataTable( { 
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
function getVendorContactLists(){
	var vendorId = [];
	$('input:checkbox[name=vendorNameById]:checked').each(function() 
    {
		vendorId.push($(this).val());
    });
	var vendorStatus = [];
	$('input:checkbox[name=vendorStatus]:checked').each(function() 
    {
		vendorStatus.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=vendorInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorContactLists?${_csrf.parameterName}=${_csrf.token}&vendorId="+vendorId+"&vendorStatus="+vendorStatus+"&invitationStatus="+invitationStatus, 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   	        	
   	        	result = result +'<thead>';
   	        	result = result +'<tr>';
   	        	result = result +'	<th>Name</th>';
   	        	result = result +'  <th>Email</th>';
   	        	result = result +'  <th>Ph. No</th>';
   	        	result = result +' <th>Owner</th>';
   	        	result = result +'  <th>Vendor</th>';
   	        	result = result +' <th>Status</th>';
   	        	result = result +' <th>Invitation</th>';
   	        	result = result +'<th>Action</th>';
   	        	result = result +'</tr>';
   	        	result = result +'	</thead>';
   	        	
   	        	result = result +'<tbody>';
   	        	
   	        	for (var i=0; i<json.length; i++)
   				{  
   	        		var report = json[i];
   	        		var selected1 = '';
   					var selected2 = '';
   					var selected3 = '';
   					var selected4 = '';
   					var selected5 = '';
   	        		
   	        		if(report.spocStatus == 'Active'){
						selected1 = 'selected';
					} 
				
					if(report.spocStatus == 'In-Active'){
							selected2 = 'selected';
						} 
					
					if(report.spocStatus == 'New'){
							selected3 = 'selected';
						}
					
					if(report.spocStatus == 'Negotiation'){
							selected4 = 'selected';
						} 
					
					if(report.spocStatus == 'Contracted'){
						selected5 = 'selected';
					} 
   	        		
   	        		result = result +'<tr>';
   	        		result = result +'<td><div class="proj_show"onclick="vendorSpocSideBar(\''+report.id+'\',\''+report.ownerUserId+'\',\''+report.spocUserId+'\',\''+report.spocName+'\',\''+report.tempOwnerId+'\',\''+report.spocLocationId+'\',\''+report.orgRelationId+'\',\''+report.userId+'\');"><span>'+report.spocName+'</span><i class="fa fa-binoculars"></i></div></td>';
   	        		result = result +'<td><div><span>'+report.spocEmail+'</span></div></td>';
   	        		if(report.spocMobilePhone != null && report.spocMobilePhone  != '' && report.spocMobilePhone  != 'undefined'){
   	        			result = result +'<td><div><span>'+report.spocMobilePhone+'</span></div></td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		result = result +'<td><div><span>'+report.ownerName+'</span></div></td>';
   	        		result = result +'<td><div><span>'+report.orgName+'</span></div></td>';
   	        		
   	        		
   	        		result = result + ' <td><div>';
					result = result + ' <select id="spocState'+report.id+'" name="spocStatus" onChange="updateSpocStatus(\''+report.id+'\');">';
					result = result + ' <option '+selected1+'>Active</option>';
					result = result + ' <option '+selected2+'>In-Active</option>';
					result = result + ' <option '+selected3+'>New</option>';
					result = result + ' <option '+selected4+'>Negotiation</option>';
					result = result + ' <option '+selected5+'>Contracted</option>';
					result = result + ' </select>';
					result = result + ' </div></td>';
               			
   	        		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
   	        		  result = result +' <td><div><i class="fa fa-check" alt="Accept" onclick="acceptInvitation('+report.id+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejectInvitation('+report.id+');"></i></div></td>';
   	        		}
   	        		
   	        		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
                        result = result +' <td><div>---</div></td>';
                  	}
   	        		
   	        		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
   	        			result = result +' <td><div><b class="proj_process"><a href="javascript:void(0)" onClick="resendVendorSpocInvitation('+report.spocUserId+','+report.orgRelationId+');">Send</a></b></div></td>';
   	        		}
   	        		
   	        		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
   	        			result = result +' <td><div>---</div></td>';
   	        		}
   	        		
   	        		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){ 
   	        			result = result +' <td><div><b class="proj_process">Received</b></div></td>';
   	        		}
   	        		
   	        		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
   	        			result = result +' <td><div><b class="proj_process"><a href="javascript:void(0)" onClick="resendVendorSpocInvitation('+report.spocUserId+','+report.orgRelationId+');">Resend</a></b></div></td>';
   	        		}
   	        		
   	        		if(report.invitationStatus == 'Accepted'){ 
   	        			result = result +' <td><div><b class="proj_active">'+report.invitationStatus+'</b></div></td>';
   	        		}
             		
             		if(report.invitationStatus == 'Rejected'){
             			result = result +' <td><div><b class="proj_closed">'+report.invitationStatus+'</b></div></td>';
             		}
   	        		
   	        		/* result = result +'<td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a>';
   	        		result = result +'<i class="glyphicon glyphicon-trash"></i></div></td>'; */
   	        	    result = result + '<td><div><i class="glyphicon glyphicon-trash" onClick="terminateSpoc1('+report.id+'\,\''+report.orgRelations.id+'\')";></i></div></td>';
   	        		
   	        		
   	        		result = result +'</tr>';
   				}
   	        	
   	        	result = result +'</tbody>';
   	    	    $("#vendorContactList").empty();  
   	    	    $("#vendorContactList").append(result)
   	    	    pagination(); 
   	    	    
   		    }
	 });
	
	
}	
</script> 
<div id="popUp"></div>

<!-- This script is for resending the invitation mail -->
<script type="text/javascript">
function resendVendorSpocInvitation(spocUserId,orgRelationId){
	var sourceleadUrl ='${sourceleadUrl}';
	
	if(spocUserId != null && spocUserId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendSpocInvitationEmail?${_csrf.parameterName}=${_csrf.token}&spocUserId="+spocUserId+"&responderType=Vendor&orgRelationID="+orgRelationId, 
			        success: function(result) {
			        	$("#loading").removeAttr("style"); 
			        	getVendorContactLists();
			        },
			    });
		 }
	}
  
</script>

<script>
function terminateSpoc1(spocId, orgRelationID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>User delete message here</p>";
	result = result +"		<textarea  id='reasonForSpoc' name='reasonForSpoc'  placeholder='Comments here'></textarea>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='saveReason1("+spocId+","+orgRelationID+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function saveReason1(spocId, orgRelationID){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForSpoc").value;
	if(reason.match(/^[^\s-](.*)*$/) != null){
		$("#reasonForSpocDiv").empty();
		$.ajax({
		   	type: "POST",
		   // url: sourceleadUrl+"/terminateSpoc?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&reason="+reason,	    	   
		    success: function(data) {
		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
   			    //vendorContactList(orgRelationID);
   			 getVendorContactLists();
		    }
	   });
	}else{
		if(reason){
			$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
		
	}
	  
}
</script>
<script>
function updateSpocStatus(spocId){
    var spocStatus = $('#spocState'+spocId).val();
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeSpocStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	 getVendorContactLists();
	    }
   });
}
</script>

<script>
function pagination(){  
	/* document.getElementById("vendorList").style.display="none"; */
	/*  document.getElementById("vendorList_info").style.display="none"; 
	 document.getElementById("vendorList_paginate").style.display="none";  */
    $('#vendorContactList').DataTable( { 
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
function getClientDetails(){
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getClientDetails?${_csrf.parameterName}=${_csrf.token}", 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   	        	result = result +'<thead>';
   	        	result = result +'<tr>';
   	        	result = result +'	<th>Client name</th>';
   	        	result = result +'	<th>Website</th>';
   	        	result = result +'	<th>Location</th>';
   	        	result = result +'	<th>Level</th>';
   	        	result = result +'	<th>Created By</th>';
   	        	result = result +'<th>No.of contacts</th>';
   	        	result = result +'<th>Action</th>';
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
   	        		if(report.locationID == loginOrgLocId){
   	        			result = result +'<td>';
   	        			result = result +'<div class="proj_show" onclick="clientSideBar(\''+report.responderId+'\',\''+report.orgName+'\',\''+report.id+'\');"><span class="tbldata-dotdot-vsm" title="'+report.orgName+'">'+report.orgName+'</span><i class="fa fa-binoculars"></i>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
						
   	        		}
   	        		if( report.webSite != null && report.webSite != '' && report.webSite != 'undefined'){
   	        			result = result +'<td>';
   	        			result = result +'<div>';
   	        			result = result +'<span class="tbldata-dotdot-sm" title='+report.webSite+'>'+report.webSite+'</span>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		
   	        		if(location != null && location  != '' && location  != 'undefined'){
   	        			result = result +'<td>';
   	        			result = result +'<div>';
   	        			result = result +'<span class="tbldata-dotdot-me" title="'+location+'">'+location+'</span>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		if(report.level != null && report.level != '' && report.level != 'undefined'){
   	        			result = result +'<td><div><span class="tbldata-dotdot-vsm" title='+report.level+'>'+report.level+'</span></div></td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		result = result +'<td><div><span class="tblminwidth-125">'+report.vendorCreateduserName+'</span></div></td>';
   	        		result = result +'<td><div>'+report.noOfContatcs+'</div></td>';
   	        		result = result +'<td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
   	        		
   	        		
   	        		
   	        		if(report.responderId == loginOrgLocId){
   	        			result = result +'<td><div class="proj_show" onclick="clientSideBar('+report.locationID+','+report.locationOrgname+','+report.id+');"><span class="tbldata-dotdot-vsm" title='+report.locationOrgname+'>"'+report.locationOrgname+'"</span><i class="fa fa-binoculars"></i>';
   	        			result = result +'</div></td>';
   	        			
   	        			if(report.locationWebsite != null && report.locationWebsite != '' && report.locationWebsite != 'undefined'){
   	        				result = result +'<td><div><span class="tbldata-dotdot-sm" title='+report.locationWebsite+'>'+report.locationWebsite+'</span></div></td>';
   	        			}else{
   	        				result = result +'<td><div>--</div></td>';
   	        			}
   	        			
   	        			if(report.locationCountry != null & report.locationCountry != '' && report.locationCountry != 'undefined'){
   	        				result = result +'<td><div><span class="tbldata-dotdot-me" title="'+report.locationCity+'&nbsp;,'+report.locationState+'&nbsp;,'+report.locationCountry+'">'+report.locationCity+'&nbsp;,'+report.locationState+'&nbsp;,'+report.locationCountry+'</span></div></td>';
   	        			}else{
   	        				result = result +'<td><div>--</div></td>';
   	        			}
   	        			
   	        			if(report.level != null && report.level != '' && report.level != 'undefined'){
   	        				result = result +'<td><div><span class="tbldata-dotdot-vsm" title='+report.level+'>'+report.level+'</span></div></td>';
   	        			}else{
   	        				result = result +'<td><div>--</div></td>';
   	        			}
   	        			
   	        			result = result +'<td><div><span class="tblminwidth-125">'+report.vendorCreateduserName+'</span></div></td>';
   	        			result = result +'<td><div>'+report.noOfContatcs+'</div></td>';
   						
   	        		} 
   	        		
   	        		result = result +'</tr>';
   	        		
   				}
   	        	
   	        	result = result +'</tbody>';
   	    	    $("#vendorList").empty();  
   	    	    $("#vendorList").append(result)
   	    	  pagination1();
   	    	    
   		    }
	 }); 
	
	
}	
</script> 

<script>
function pagination1(){  
	/* document.getElementById("vendorList").style.display="none"; */
	/*  document.getElementById("vendorList_info").style.display="none"; 
	 document.getElementById("vendorList_paginate").style.display="none";  */
    $('#vendorList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": ""
     	}
		
    } );
	 
	 
   /*  $(document).ready(function() {
    	var oTable = $('#example').dataTable( {
    	"sScrollY": "200px",
    	"bPaginate": false
    	} );
    	$(window).bind('resize', function () {
    	oTable.fnAdjustColumnSizing();
    	} ); document.getElementById("example").style.display="block";

    	} ); */
}
</script>
<script>
	function vendorSpocSideBar(spocId,ownerId,spocUserId,spocName,tempOwnerId,spocLocationId,orgRelationId,userId) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		result = result	+ '<li class="current"><a href="#tab-1" onClick="vendorDetailsbySpocId('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+');">Details</a></li>';
		result = result + '<li><a href="#tab-2" onClick="vendorSpocDocuments('+orgRelationId+','+spocLocationId+',\''+spocName+'\','+userId+');">Documents</a></li>'; 
		/* result = result + '<li><a href="#tab-3">Activity</a></li>';  */
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '<div id="tab-2" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '</div>';
		result = result + '</div>';
		$("#vendorSideDiv").empty();
		$("#vendorSideDiv").append(result);
		
		$("#vendorNameDiv").empty();
		$("#vendorNameDiv").append(spocName);
		
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');

		vendorDetailsbySpocId(spocId,ownerId,spocUserId,tempOwnerId);
	}
</script>




<script>
	function vendorSideBar(vendorLocationId,orgRelationId,orgName) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		result = result
				+ '<li class="current"><a href="#tab-1" onClick="vendorDetailsbyLocationId('
				+ vendorLocationId + ','+ orgRelationId + ');">Details</a></li>';
		result = result + '<li><a href="#tab-2" onclick="vendorContacts('+ orgRelationId + ','+vendorLocationId+');">Contacts</a></li>'; 
		result = result + '<li><a href="#tab-3" onclick="vendorDocuments('+orgRelationId+','+vendorLocationId+');">Documents</a></li>'; 
		/* result = result + '<li><a href="#tab-4">Activity</a></li>';  */
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		//for vendor details tab
		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';

		//for vendor contacts tab
		result = result + '<div id="tab-2" class="project_tabs_inn">';
		result = result + '</div>';
		
		//for vendor documents tab
		result = result + '<div id="tab-3" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '</div>';
		result = result + '</div>'
		$("#vendorSideDiv").empty();
		$("#vendorSideDiv").append(result);
		
		$("#vendorNameDiv").empty();
		$("#vendorNameDiv").append(orgName);
		
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		
		vendorDetailsbyLocationId(vendorLocationId,orgRelationId);
	}
</script>

<script type="text/javascript">
	function vendorDetailsbyLocationId(vendorLocationId,orgRelationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
					type : "POST",
					url : sourceleadUrl+"/getClientAndVendorOrgDeatilsById?${_csrf.parameterName}=${_csrf.token}&vendorLocationId="+vendorLocationId+"&orgRelationId="+orgRelationId,
					success : function(data) {
						
						var json = JSON.parse(data);
						var result = "";
						var city = "";
						var state = "";
						var country = "";
						result = result+ '<div class="project_tabs_inn_table">';
						result = result + '<div class="project_details">';
							result = result + ' <div class="useredit useredit_a">';
								result = result + '<a id="edit-client" href="javascript:editVendorDetails('+vendorLocationId+','+orgRelationId+')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
							result = result + ' </div>'; 
							result = result + '<div class="project_details_cta">';
								result = result + '<strong>Name:</strong>';
								if(json.orgName !=null && json.orgName !='' && json.orgName !='undefined'){
									result = result + '<span>'+json.orgName+'</span>';
								}
								result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
								result = result + '<strong>Website:</strong>';
								if(json.webSite !=null && json.webSite !='' && json.webSite !='undefined'){
								result = result + '<span>'+json.webSite+'</span><div class="clearfix"></div>';
								}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Client City:</strong>';
							if(json.city != null && json.city !='' && json.city !='undefined'){
								result = result + '<span>'+json.city+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span>No Data</span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Client State:</strong>';
							if(json.state != null && json.state !='' && json.state !='undefined'){
								result = result + '<span>'+json.state+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span>No Data</span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Client Country:</strong>';
							if(json.country != null && json.country !='' && json.country !='undefined'){
								result = result + '<span>'+json.country+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span>No Data</span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							/* result = result + '<div class="project_details_cta">';
							result = result + '<strong>Email:</strong>';
							if(json.email != null && json.email !='' && json.email !='undefined'){
							result = result + '<span>'+json.email+'</span><div class="clearfix"></div>';
							}else{
								
								result = result + '<span>No Data</span><div class="clearfix"></div>';	
							}
							result = result + '</div>'; */
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Level:</strong>';
							if(json.level != null && json.level !='' && json.level !='undefined'){
							result = result + '<span>'+json.level+'</span><div class="clearfix"></div>';
							}else{
								
								result = result + '<span>No Data</span><div class="clearfix"></div>';	
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Created By:</strong>';
							if(json.vendorCreateduserName != null && json.vendorCreateduserName !='' && json.vendorCreateduserName !='undefined'){
								result = result + '<span>'+json.vendorCreateduserName+'</span><div class="clearfix"></div>';
							}else{
								
								result = result + '<span>No Data</span><div class="clearfix"></div>';	
							}
							result = result + '</div>';
							
							result = result + '<div class="clearfix"></div>';
							result = result + ' <div class="user_summary_edit user_summary_edit_a" id="editVendorDetailsDiv"></div>';
							result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						
						$("#tab-1").empty();
						$("#tab-1").append(result);
						$("#tab-1").show();
						$(".project-tabs-menu a").click(
								function(event) {
									event.preventDefault();
									$(this).parent().addClass("current");
									$(this).parent().siblings().removeClass(
											"current");
									var tab = $(this).attr("href");
									$(".project_tabs_inn").not(tab).css(
											"display", "none");
									$(tab).fadeIn();
								});

					}
				});
	}
</script>

<script>
function listChange(sel){
	 if(sel.value=="listbyVendor"){
		 document.getElementById("vendorList").style.display="block";
		 document.getElementById("vendorList").style.display="table";
		 document.getElementById("vendorContactList").style.display="none";
		 document.getElementById("vendorContactList_wrapper").style.display="none";
		 document.getElementById("contactLevel").style.display="block";
		 document.getElementById("vendorsList").style.display="none";
		 document.getElementById("vendorSatatus").style.display="none";
		 document.getElementById("vendorInvitation").style.display="none";
		 
		 getVendorLists();
	 }
	 if(sel.value=="listbyContact"){
		 document.getElementById("vendorContactList").style.display="block";
		 document.getElementById("vendorContactList").style.display="table";
		 document.getElementById("vendorList").style.display="none";
		 document.getElementById("vendorList_wrapper").style.display="none";
		 document.getElementById("vendorContactList_wrapper").style.display="block";
		 document.getElementById("contactLevel").style.display="none";
		 document.getElementById("vendorsList").style.display="block";
		 document.getElementById("vendorSatatus").style.display="block";
		 document.getElementById("vendorInvitation").style.display="block";
		 
		 getVendorContactLists();
	 }
	 
}
</script>

<jsp:include page="../masterFooter.jsp" />
<script src="resources/js/jquery-fun.js"></script> 
<script>

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
    
    $("#filter1ApprovePop").click(function(){
		$("#filter1ApprovedPop").slideToggle(200);
		$("#filter2ApprovedPop, #filter3ApprovedPop, #filter4ApprovedPop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
		$("#filter1ApprovedPop, #filter3ApprovedPop, #filter4ApprovedPop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    $("#filter3ApprovePop").click(function(){
		$("#filter3ApprovedPop").slideToggle(200);
		$("#filter1ApprovedPop, #filter2ApprovedPop, #filter4ApprovedPop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter3ApprovedPop").slideUp(200);
    });
    $("#filter3ApprovedPop, #filter3ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    $("#filter4ApprovePop").click(function(){
		$("#filter4ApprovedPop").slideToggle(200);
		$("#filter2ApprovedPop, #filter3ApprovedPop, #filter1ApprovedPop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter4ApprovedPop").slideUp(200);
    });
    $("#filter4ApprovedPop, #filter4ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
});

// Export Popup
$(document).ready(function() {
    $("#export-pop").click(function(){
		$(".export_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".export_pop").slideUp(200);
    });
    $(".export_pop, #export-pop").click(function(e) {
        e.stopPropagation();
    });	
});	
	
	
// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	
	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
	});	
});

// project side bar tabs
$(document).ready(function() {
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});


	// pop head pop
	
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
   });


</script> 


<jsp:include page="editVendor.jsp" />
<jsp:include page="editSpocDetails.jsp" />
<jsp:include page="vendorContacts.jsp" />
<jsp:include page="vendorDocuments.jsp" />
<jsp:include page="vendorSpocDocuments.jsp" />



<!-- Scripts for invitations  -->
<script>
function acceptInvitation(spocId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/saveInvitationStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&invitationStaus=Accepted",	    	   
		    success: function(data) {
		    	getVendorContactLists();
		    }
	   });
}
</script>

<script>
function rejectInvitation(spocId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/saveInvitationStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&invitationStaus=Rejected",
		    success: function(data) {
		    	getVendorContactLists();
		    }
	   });
}
</script>
<!-- Scripts for invitations  -->	

