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
            <h2>Vendors list
            	<span class="select-fild">
                	<select id="listOfVendors" onchange="listChange(this);">
                  		 <option value="listbyVendor">Search by list</option>
                        <option value="listbyContact" selected="selected">Search by Contact</option> 
                    </select>
                </span>            	
            </h2>
          </div>
          <div class="pull-right brud-crum">Home &raquo; Vendors list </div>
        </section>
        
       
       
		<!-- Project -->
		 <div id="loading"></div>
<div class="timesheet_out">

<div class="vendor_wrap">
	<div class="timesheet_head">
        	<ul>
				<!-- <li class="li-vendor"><input type="text" class="vendorfild"><i class="fa fa-search"></i>|</li>
				<li><i class="fa fa-filter"></i><span>Approved<b>30</b></span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c1"><label for="c1">All</label><b>10</b></li>
							<li><input type="checkbox" id="c2"><label for="c2">Approved</label><b>10</b></li>
							<li><input type="checkbox" id="c3"><label for="c3">Draft</label><b>10</b></li>
							<li><input type="checkbox" id="c4"><label for="c4">Rejected</label><b>10</b></li>
                        </ul>
                    </div>                 	
                </li>
				<li><i class="glyphicon glyphicon-export"></i><span>Export</span><i class="glyphicon glyphicon-play" id="export-pop"></i>|
                	<div class="export_pop">
                    	<ul>
                        	<li>PDF<i class="fa fa-file-pdf-o"></i></li>
                            <li>Excel<i class="fa fa-file-excel-o"></i></li>
                            <li>Word<i class="fa fa-file-word-o"></i></li>
                        </ul>
                    </div>
                </li>   -->              
				<li><a href="<c:url value='/addVendorContact'/>"><i class="fa fa-plus"></i>Add contact</a></li>	
                       <li><a href="<c:url value='/addVendor'/>"><i class="fa fa-plus"></i>Add Vendor</a></li>		
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
	//getVendorLists();
	getVendorContactLists();
});
</script>

<script>
function getVendorLists(){
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorLists?${_csrf.parameterName}=${_csrf.token}", 	   
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
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorContactLists?${_csrf.parameterName}=${_csrf.token}", 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   	        	
   	        	result = result +'<thead>';
   	        	result = result +'<tr>';
   	        	result = result +'	<th>Name</th>';
   	        	result = result +'  <th>Email</th>';
   	        	result = result +'  <th>Ph. No</th>';
   	        	result = result +' <th>Owner</th>';
   	        	result = result +'  <th>Client</th>';
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
   	        		result = result +'<td><div class="proj_show"onclick="vendorSpocSideBar(\''+report.id+'\',\''+report.ownerUserId+'\',\''+report.spocUserId+'\',\''+report.spocName+'\');"><span>'+report.spocName+'</span><i class="fa fa-binoculars"></i></div></td>';
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
               			
   	        		if(report.spocUser.id == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
   	        		  result = result +' <td><div><i class="fa fa-check" alt="Accept" onclick="acceptInvitation('+report.id+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejectInvitation('+report.id+');"></i></div></td>';
   	        		}
   	        		
   	        		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
   	        			result = result +' <td><div><b class="proj_process"><a href="javascript:void(0)" onClick="resendVendorSpocInvitation('+report.spocUser.id+','+report.orgRelationId+');">Send</a></b></div></td>';
   	        		}
   	        		
   	        		if(report.spocUser.id != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
   	        			result = result +' <td><div>---</div></td>';
   	        		}
   	        		
   	        		if(report.spocUser.id != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){ 
   	        			result = result +' <td><div><b class="proj_process">Received</b></div></td>';
   	        		}
   	        		
   	        		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
   	        			result = result +' <td><div><b class="proj_process"><a href="javascript:void(0)" onClick="resendVendorSpocInvitation('+report.spocUser.id+','+report.orgRelationId+');">Resend</a></b></div></td>';
   	        		}
   	        		
   	        		if(report.invitationStatus == 'Accepted'){ 
   	        			result = result +' <td><div><b class="proj_active">'+report.invitationStatus+'</b></div></td>';
   	        		}
             		
             		if(report.invitationStatus == 'Rejected'){
             			result = result +' <td><div><b class="proj_closed">'+report.invitationStatus+'</b></div></td>';
             		}
   	        		
   	        		/* result = result +'<td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a>';
   	        		result = result +'<i class="glyphicon glyphicon-trash"></i></div></td>'; */
   	        	    result = result + '<td><div><i class="glyphicon glyphicon-trash" onClick="terminateSpoc('+report.id+'\,\''+report.orgRelations.id+'\')";></i></div></td>';
   	        		
   	        		
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
function terminateSpoc(spocId, orgRelationID){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>User delete message here</p>";
	result = result +"		<textarea  id='reasonForSpoc' name='reasonForSpoc'  placeholder='Comments here'></textarea>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Submit' onclick='saveReason("+spocId+","+orgRelationID+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>

<script>
function saveReason(spocId, orgRelationID){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForSpoc").value;
	if(reason){
		$("#reasonForSpocDiv").empty();
		$.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/terminateSpoc?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&reason="+reason,	    	   
		    success: function(data) {
		    	$("#popUp").empty();
   			 	$(".project_inn_mask").fadeOut(200);
   			    //vendorContactList(orgRelationID);
   			 getVendorContactLists();
		    }
	   });
	}else{
		$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
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
	function vendorSpocSideBar(spocId,ownerId,spocUserId,spocName) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		result = result
				+ '<li class="current"><a href="#tab-1" onClick="vendorDetailsbySpocId('+spocId+','+ownerId+','+spocUserId+');">Details</a></li>';
		result = result + '<li><a href="#tab-2">Documents</a></li>'; 
		result = result + '<li><a href="#tab-3">Activity</a></li>'; 
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';

		/* result = result + '<div id="tab-2" class="project_tabs_inn">';
		result = result + '</div>'; */

		result = result + '</div>';
		result = result + '</div>';
		$("#vendorSideDiv").empty();
		$("#vendorSideDiv").append(result);
		
		$("#vendorNameDiv").empty();
		$("#vendorNameDiv").append(spocName);
		
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');

		vendorDetailsbySpocId(spocId,ownerId,spocUserId);
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
		result = result + '<li><a href="#tab-2" onclick="vendorContactList('+ orgRelationId + ','+vendorLocationId+');">Contacts</a></li>'; 
		result = result + '<li><a href="#tab-3">Documents</a></li>'; 
		result = result + '<li><a href="#tab-4">Activity</a></li>'; 
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '<div class="clients_wrap_table project_tabs_inn" >';
	   	result = result + '<div id="tab-2-addContact"> </div>';
        result = result + '<table id="tab-2" class="display" cellspacing="0" width="100%"> </table>';
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


<script>
function contactPagination(){  
    $('#tab-2').DataTable( { 
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
		 /* document.getElementById("vendorContactList_info").style.display="none"; 
		 document.getElementById("vendorList_paginate").style.display="none"; */
		 
		 getVendorLists();
	 }
	 if(sel.value=="listbyContact"){
		 document.getElementById("vendorContactList").style.display="block";
		 document.getElementById("vendorContactList").style.display="table";
		 document.getElementById("vendorList").style.display="none";
		 document.getElementById("vendorList_wrapper").style.display="none";
		 document.getElementById("vendorContactList_wrapper").style.display="block";
		/*  document.getElementById("vendorList_info").style.display="none"; 
		 document.getElementById("vendorList_paginate").style.display="none"; */
		 
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

