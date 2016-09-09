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


<body>
 <div id="loading"></div>
	<!-- Content Area -->
	<section class="content-wraper">
		<section class="content-header clearfix">
		<c:if test="${not empty msg}">
	        <div class="success_pop" id="selector">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
			<div class="pull-left">
				<h2>Clients
				<span class="select-fild">
				    <select id="listOfVendors" onchange="listChange(this);">
                  		 <option value="listbyVendor">Search by list</option>
                        <option value="listbyContact" selected="selected">Search by Contact</option> 
                    </select>
				</span>
				</h2>
			</div>

			<div class="pull-right brud-crum"><a href="dashboard">Home</a>&raquo;Client List</div>

		</section>

		<!-- Project -->
		<div class="timesheet_out">
			<div class="clients_wrap">
				<div class="timesheet_head">
					<ul>

						<!--  <li><i class="fa fa-filter"></i><span>Client</span><i class="glyphicon glyphicon-play" id="appro-pop"></i>|
                	<div class="approved_pop">
                    	<ul>
                        	<li><input type="checkbox" value="None" id="c1"><label for="c1">All</label></li>
							<li><input type="checkbox" id="c2"><label for="c2">Approved</label></li>
							<li><input type="checkbox" id="c3"><label for="c3">Draft</label></li>
							<li><input type="checkbox" id="c4"><label for="c4">Rejected</label></li>
                        </ul>
                    </div>                	
                </li> -->
                        <li><a href="<c:url value='/addClientContact'/>"><i class="fa fa-plus"></i>Add contact</a></li>
						<li><a href="<c:url value='/client'/>"><i class="fa fa-plus"></i>Add client</a></li>
						<div class="clearfix"></div>
					</ul>
					<div class="clearfix"></div>
				</div>
				
				<div class="clients_wrap_table " id ="vendorListFilter">
			
		     	<table id="clientList" class="display" cellspacing="0" width="100%"> </table>
		     	<table id="vendorContactList" class="display" cellspacing="0" width="100%"> </table> 
				
				</div>
				
			</div>
			<%-- <c:if test="${empty clientsList}">
				<div class="row mt">
					<div class="col-lg-12">
						<div class="content-area clearfix">
							<div class="blank-pages">
								<p>
									<i class="fa fa-5x fa-users"></i>
								</p>
								<p>No Client for organisation... Add Clients</p>
							</div>
						</div>
					</div>
				</div>
			</c:if> --%>
		</div>


       <div class="side_mask"><div class="proj_close"></div></div>
		<div class="clients_side_wrap">
			<div class="clients_side_wrap_inn">
				<h1 id="clientNameDiv"></h1>
				<div class="proj_close">
					<i></i>
				</div>
				<div id="clientSideDiv"></div>
			</div>
         </div>
	</section>
</body>

<script type="text/javascript">
window.onload = function () {
	//getClientDetails();
	getVendorContactLists();
 };
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
   	        	if(json.length != null && json.length != ''){
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
   	        		
   	        			result = result +'<td>';
   	        			result = result +'<div class="proj_show" onclick="clientSideBar(\''+report.vendorlocationId+'\',\''+report.orgName+'\',\''+report.id+'\');"><span class="tbldata-dotdot-vsm" title="'+report.orgName+'">'+report.orgName+'</span><i class="fa fa-binoculars"></i>';
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
   	        		if(report.level != null && report.level != '' && report.level != 'undefined'){
   	        			result = result +'<td><div><span class="tbldata-dotdot-vsm" title='+report.level+'>'+report.level+'</span></div></td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		result = result +'<td><div><span class="tblminwidth-125">'+report.vendorCreateduserName+'</span></div></td>';
   	        		result = result +'<td><div>'+report.noOfContatcs+'</div></td>';
   	        		result = result +'<td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
   	        		result = result +'</tr>';
   	        		
   				}
   	        	
   	        	result = result +'</tbody>';
   	        	}else{
   	        		result = result +'<div class="row mt">';
   	        		result = result +'<div class="col-lg-12">';
   	        		result = result +'<div class="content-area clearfix">';
   	        		result = result +'<div class="blank-pages">';
   	        		result = result +'<p>';
   	        		result = result +'<i class="fa fa-5x fa-users"></i>';
   	        		result = result +'</p>';
   	        		result = result +'<p>No Clients for organisation... Add Clients</p>';
   	        		result = result +'</div>';
   	        		result = result +'</div>';
   	        		result = result +'</div>';
   	        		result = result +'</div>';
   	        	}
   	    	    $("#clientList").empty();  
   	    	    $("#clientList").append(result)
   	    	    pagination();
   	    	    
   		    }
	 }); 
	
	
}	
</script> 

<script>
function pagination(){  
    $('#clientList').DataTable( { 
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
	function clientSideBar(clientLocationId,orgName,orgRelationId) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		result = result + '<li class="current"><a href="#tab-1" onClick="clientDetails('+clientLocationId+','+orgRelationId+');">Details</a></li>';
		//for client contatcs tab
		result = result + '<li><a href="#tab-2" onclick="clientContactList('+orgRelationId+','+clientLocationId+');">Contacts</a></li>';
				
				
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';
		
		//for client contatcs tab
		result = result + '<div class="clients_wrap_table " >';
	   	result = result + '<div id="tab-2-addContact"> </div>';
        result = result + '<table id="tab-2" class="display" cellspacing="0" width="100%"> </table>';
        result = result + '</div>';

		result = result + '</div>';
		result = result + '</div>';
		$("#clientSideDiv").empty();
		$("#clientSideDiv").append(result);
		
		$("#clientNameDiv").empty();
		$("#clientNameDiv").append(orgName);
		
		 $(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		 
 
		clientDetails(clientLocationId,orgRelationId);
	}
</script>

<script>
function getVendorContactLists(){
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getClientContactLists?${_csrf.parameterName}=${_csrf.token}", 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   	        	if(json.length != null && json.length != ''){
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
   	        		result = result +'<td><div class="proj_show"onclick="clientSpocSideBar(\''+report.id+'\',\''+report.ownerUserId+'\',\''+report.spocUserId+'\',\''+report.spocName+'\');"><span>'+report.spocName+'</span><i class="fa fa-binoculars"></i></div></td>';
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
     	        			result = result +' <td><div><b class="proj_process"><a href="javascript:void(0)" onClick="resendClientSpocInvitation('+report.spocUser.id+','+report.orgRelationId+');">Send</a></b></div></td>';
     	        		}
     	        		
     	        		if(report.spocUser.id != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
     	        			result = result +' <td><div>---</div></td>';
     	        		}
     	        		
     	        		if(report.spocUser.id != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){ 
     	        			result = result +' <td><div><b class="proj_process">Received</b></div></td>';
     	        		}
     	        		
     	        		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
     	        			result = result +' <td><div><b class="proj_process"><a href="javascript:void(0)" onClick="resendClientSpocInvitation('+report.spocUser.id+','+report.orgRelationId+');">Resend</a></b></div></td>';
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
   	    	    pagination1(); 
   	        	}else{
   	        		result = result +'<div class="row mt">';
   	        		result = result +'<div class="col-lg-12">';
   	        		result = result +'<div class="content-area clearfix">';
   	        		result = result +'<div class="blank-pages">';
   	        		result = result +'<p>';
   	        		result = result +'<i class="fa fa-5x fa-users"></i>';
   	        		result = result +'</p>';
   	        		result = result +'<p>No Client Contacts for organisation... Add Client Contacts</p>';
   	        		result = result +'</div>';
   	        		result = result +'</div>';
   	        		result = result +'</div>';
   	        		result = result +'</div>';
   	        	    $("#vendorContactList").empty();  
    	    	    $("#vendorContactList").append(result)
   	        	}
   	        	
   	    	    
   		    }
	 });
	
	
}	
</script> 

<!-- This script is for resending the invitation mail -->
<script type="text/javascript">
function resendClientSpocInvitation(spocUserId,orgRelationId){
	var sourceleadUrl ='${sourceleadUrl}';
	if(spocUserId != null && spocUserId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		$.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendSpocInvitationEmail?${_csrf.parameterName}=${_csrf.token}&spocUserId="+spocUserId+"&responderType=Client&orgRelationID="+orgRelationId, 
			        success: function(result) {
			        	$("#loading").removeAttr("style"); 
			        	getVendorContactLists();
			        },
			    });
		 }
	}
  
</script>

<script>
function pagination1(){  
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

<script>
function updateSpocStatus(spocId){
    var spocStatus = $('#spocState'+spocId).val();
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeSpocStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	//clientContactList(orgRelationID);
	    }
   });
}
</script>



<script>
	function clientSpocSideBar(spocId,ownerId,spocUserId,spocName) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		result = result
				+ '<li class="current"><a href="#tab-1" onClick="clientDetailsbySpocId('+spocId+','+ownerId+','+spocUserId+');">Details</a></li>';
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
		
		$("#clientSideDiv").empty();
		$("#clientSideDiv").append(result);
		
		$("#clientNameDiv").empty();
		$("#clientNameDiv").append(spocName);
		
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');

		clientDetailsbySpocId(spocId,ownerId,spocUserId);
	}
</script>



<script>
function listChange(sel){
	 if(sel.value=="listbyVendor"){
		 document.getElementById("clientList").style.display="block";
		 document.getElementById("clientList").style.display="table";
		 document.getElementById("vendorContactList").style.display="none";
		 document.getElementById("vendorContactList_wrapper").style.display="none";
		 
		 getClientDetails();
	 }
	 if(sel.value=="listbyContact"){
		 document.getElementById("vendorContactList").style.display="block";
		 document.getElementById("vendorContactList").style.display="table";
		 document.getElementById("clientList").style.display="none";
		 document.getElementById("clientList_wrapper").style.display="none";
		 document.getElementById("vendorContactList_wrapper").style.display="block";
		 
		 getVendorContactLists();
	 }
	 
}
</script>

<script>
	function clientProjectDetails(clientId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/getAllClientProjectsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="
							+ clientId,
					success : function(data) {

						var json = JSON.parse(data);
						var result = "";
						result = result+ '<div class="project_tabs_inn_head">';
						/* result = result + '					<a href="create-project.html"><i class="glyphicon glyphicon-plus"></i>Add project</a>'; */
						result = result + '</div>';
						result = result+ '<div class="project_tabs_inn_table">';
						if(json.length!=null && json.length!=''){
						result = result + '<table>';
						result = result + '<tbody>';
						result = result + '<tr>';
						result = result + '<th>Project Id</th>';
						result = result + '<th>Projects name</th>';
						result = result + '<th>Start Date</th>';
						result = result + '<th>End Date</th>';
						result = result + '<th>Manager</th>';
						result = result + '<th>Team</th>';
						result = result + '<th>Status</th>';
						result = result + '</tr>';
						result = result + '<tr>';
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							var endDateStr ="";
							if(opt.endDateStr != null && opt.endDateStr != '' & opt.endDateStr != 'undefined'){
								endDateStr = opt.endDateStr;
							}else{
								endDateStr = "NO DATA";
							}
							result = result + '<td><div class="tbldata-dotdot-sm" title="'+opt.id+'"><input type="text"  onBlur="inlineEditClientProjects('+opt.id+',\'id\');" id="id'+opt.id+'"  value="'+opt.id+'" class="project_inline_field"></div></td>';
							 result = result + '<td><div class="tbldata-dotdot-sm" title="'+opt.projectName+'"><input type="text"  onBlur="inlineEditClientProjects('+opt.id+',\'projectName\');" id="projectName'+opt.id+'"  value="'+opt.projectName+'" class="project_inline_field"></div></td>'; 
							/* result = result + '<td><div><a href="<c:url value="/projectDashboard/'+opt.id+'#tab-3"/>""> '+opt.projectName+' </a></div></td>'; */
							result = result + '<td>'+opt.startDateStr+'</td>';
							
							result = result+ '<td><div><input type="text" value="'+endDateStr+'"  id="endDate'+opt.id+'" class="project_inline_field" readonly="readonly"></div></td>';
							result = result + ' <td><div><select id="clientId'+opt.id+'" onBlur="inlineEditClientProjects('+opt.id+',\'clientId\');">';
									
							for (var j = 0; j < opt.projectManagerName.length; j++) {
										
										var managers = opt.projectManagerName[j];
										var projrctmanager = managers.split('&');
								    	var name = projrctmanager[0];
								    	var id = projrctmanager[1];
								    	var selected = '';
								    	
					   		 	    	 if(opt.relationId==id){
					   		 	    		 selected = 'selected';
					   		 	    		
					   		 	    		result = result+"<option value="+id+" "+selected+" >"+name+"</option>";
					   		 	    	    
					   		 	    	 }else{
					   		 	    		result = result+"<option value="+id+" >"+name+"</option>";
					   		 	    	 }
					   		 	    	 
									} 
							
							result = result + '	</select></div></td>';
							result = result+ '<td><div>'+opt.assignedEmpCount+'</div></td>';
							result = result + '<td><div><select id="projectStatusId'+opt.id+'" onBlur="inlineEditClientProjects('+opt.id+',\'projectStatusId\');">';
							if(opt.projectStatus == 'STARTED'){
								result = result+'<option selected>Started</option>';
							}else{
								result = result+'<option>Started</option>';
							}
							
							if(opt.projectStatus == 'INPROGRESS'){
								result = result+'<option selected>In-Progress</option>';
							}else{
								result = result+'<option>In-Progress</option>';
							}
							
							if(opt.projectStatus == 'COMPLETED'){
								result = result+'<option selected>Completed</option>';
							}else{
								result = result+'<option>Completed</option>';
							}
						
							if(opt.projectStatus == 'ONHOLD'){
								result = result+'<option selected>On-Hold</option>';
							}else{
								result = result+'<option>On-Hold</option>';
							}
							result = result + '</select></div></td>';
							result = result + '</tr>';
						}
						rresult = result + '	     </tbody>';
						result = result + '	 </table>   ';
						}else{
				    		
			    			 result = result + '<div class="table_nodata">';
			    			 result = result + '<div class="table_nodata_inn">';
			    			 result = result + '<img src="resources/images/no-data.png" />';
			    			 result = result + '<span>Nothing is here to show ';
			    			 result = result + '</span>';
			    			 result = result + '<div class="clearfix"></div>';
			    			 result = result + '</div>';
			    			 result = result + '</div>';
			    		 } 
						result = result + '	     </div>';
						$("#tab-2").empty();
						$("#tab-2").append(result);

						
						if(json.length > 0)
				   		{
			    			 for(var i=0; i<json.length; i++){
								 var jsonData = json[i];
								 
								 //Assign date of project member
								 var selected = jsonData.startDateStr;
								 var dt = new Date(selected);
			 		             dt.setDate(dt.getDate());
			 		             
								 $('#endDate'+jsonData.id).datepicker({
						    		      dateFormat: 'yy-mm-dd',
						    		      minDate: dt,
						    		      onClose: function() {
						    		        	  var id = $(this).attr('id');
												 var projectMemberId = $(this).attr('id').split('endDate')[1];
												 inlineEditClientProjects(jsonData.id,'endDate'); 
						    		      }
						    		  }); 
			    			 }
				   		}
					},
				});
	}
</script>





<script> 
function  inlineEditClientProjects(projectId,localFieldName){
	var sourceleadUrl ='${sourceleadUrl}';
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName+""+projectId).val();
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/inlineEditClientProjects?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   		    }
	 }); 
}
</script> 
<div id="popUp"></div>
<script> 
function  inlineEditForProjects(orgRelationId,localFieldName){
	var sourceleadUrl ='${sourceleadUrl}';
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName).val();
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateClientProjectById?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   		    }
	 }); 
}
</script> 

<script>
function getEndDatePickerById(projectId,localFieldName){
	$('#endDate'+projectId).datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	        },
	        onClose: function() {
	        	inlineEditClientProjects(projectId,localFieldName);
	     }
	  });
}
</script>

<script>
	// Approved Popup
	$(document).ready(function() {
		$("#appro-pop").click(function() {
			$(".approved_pop").slideToggle(200);
		});
		$("html").click(function() {
			$(".approved_pop").slideUp(200);
		});
		$(".approved_pop, #appro-pop").click(function(e) {
			e.stopPropagation();
		});
	});
	// project side bar
	$(document).ready(function() {
		$(".proj_show").click(function() {
			$(".clients_side_wrap").addClass("clients_side_wrap_active");
			$(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
		});
		$(".proj_close, .side_mask").click(function() {
			$(".clients_side_wrap").removeClass("clients_side_wrap_active");
			$(".side_mask").fadeOut(200);
			$("body").css('overflow-y', 'auto');
		});
	});

	//project side bar tabs
	$(document).ready(function() {
		$("#tab-1").show();
		$(".project-tabs-menu a").click(function(event) {
			event.preventDefault();
			$(this).parent().addClass("current");
			$(this).parent().siblings().removeClass("current");
			var tab = $(this).attr("href");
			$(".project_tabs_inn").not(tab).css("display", "none");
			$(tab).fadeIn();
		});
	});
</script>


<!-- Show success message after form submission script -->
<script>
  $('#selector').delay(2000).fadeOut('slow');
</script> 
<!-- End -->


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


<script>
// Edit pop
$(document).ready(function() {	
	
    $("#edit-client, #btnClose").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
		
});
</script> 


<jsp:include page="editClientSpocDetails.jsp" />
<jsp:include page="clientDetails.jsp" />	
<jsp:include page="clientContacts.jsp" />	
<jsp:include page="../masterFooter.jsp" />	
	