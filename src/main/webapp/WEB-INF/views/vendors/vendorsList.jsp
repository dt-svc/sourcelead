<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<script src="resources/js/formvalidator.js"></script>
<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
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
<div class="timesheet_out">

<div class="vendor_wrap">
	<div class="timesheet_head">
        	<ul>
				<li class="li-vendor"><input type="text" class="vendorfild"><i class="fa fa-search"></i>|</li>
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
                </li>                
				<li><a href="<c:url value='/vendorcontact'/>"><i class="fa fa-plus"></i>Add contact</a></li>	
                       <li><a href="<c:url value='/addVendor'/>"><i class="fa fa-plus"></i>Add Vendor</a></li>		
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
    <div class="clients_wrap_table" id="vendors_list" style="display: none">
    	<table>
        	<tbody>
			<thead>
            	<tr>
                    <th>Name</th>
                    <th>Website</th>
                    <th>Location</th>
                    <th>No.of Contacts</th>
                    <th>Released jobs</th>
                    <th>Level</th>
                    <th>Actions</th>
                </tr>
			</thead>	
			<c:forEach var="report" items="${vandorsListByOrgLocationId}">
                <tr>
                    <c:if test="${report.locationId.id == loginOrgLocId}"> 
                   		<td><div class="proj_show"
									onclick="vendorSideBar('${report.responderLocationId.id}','${report.id}','${report.responderLocationId.org.orgName}');"><span class="tbldata-dotdot-vsm" title="${report.responderLocationId.org.orgName}">${report.responderLocationId.org.orgName}</span><i
										class="fa fa-binoculars"></i>
								</div></td>
					    <td><div><span title="Website" class="td_overflow">
					    <c:if test="${(report.responderLocationId.org.webSite !=null)&&(report.responderLocationId.org.webSite !='')&&(report.responderLocationId.org.webSite !='undefined')}">
					 	  ${report.responderLocationId.org.webSite}
					    </c:if>
					      <c:if test="${(report.responderLocationId.org.webSite ==null)||(report.responderLocationId.org.webSite =='')||(report.responderLocationId.org.webSite =='undefined')}">
					 		--
					    </c:if>
					    </span></div></td>                  
                 		<td><div><span title="Country" class="td_overflow">${report.responderLocationId.country}</span></div></td>
                  </c:if> 
                   <c:if test="${report.responderLocationId.id == loginOrgLocId}"> 
                  	 	<td><div class="proj_show"
									onclick="vendorSideBar('${report.locationId.id}','${report.id}','${report.locationId.org.orgName}');"><span class="tbldata-dotdot-vsm" title="${report.locationId.org.orgName}">${report.locationId.org.orgName}</span><i
										class="fa fa-binoculars"></i>
								</div></td>
                  	 	<td><div><span title="clientdomain.comclientdomain.comclientdomain.com" class="td_overflow">
                  	 		<c:if test="${(report.locationId.org.webSite !=null)&&(report.locationId.org.webSite !='')&&(report.locationId.org.webSite !='undefined')}">
					 	 	 ${report.locationId.org.webSite}
					   		 </c:if>
					      	<c:if test="${(report.locationId.org.webSite ==null)||(report.locationId.org.webSite =='')||(report.locationId.org.webSite =='undefined')}">
					 			--
					    	</c:if>
                  	 	</span></div></td>
                   	    <td><div><span title="Hyderabad, Hyderabad, Hyderabad" class="td_overflow">${report.locationId.country}</span></div></td>
                   </c:if> 
                    <td><div>20</div></td>
                    <td><div>10</div></td>
                    <td><div>${report.level}</div></td>
                    <td><div><i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                 </c:forEach>                                                                                                                                              			
            </tbody>
        </table>
	
</div>	


 <div class="clients_wrap_table" id ="vendors_contact_list" >
    	<table>
        	<tbody>
			<thead>
				<tr>
                	<th>Name</th>
                    <th>Email</th>
                    <th>Ph. No</th>
                    <th>Owner</th>
                    <th>Client</th>
                    <th>Status</th>
                    <th>Invitation</th>
                    <th>Action</th>
                </tr>
			</thead>	
			<c:forEach var="report" items="${vandorsContactListByOrgLocationId}">
				<tr>
                	<td><div class="proj_show"onclick="vendorSpocSideBar('${report.id}','${report.ownerUser.id}','${report.spocName}');"><span>${report.spocName}</span><i class="fa fa-binoculars"></i></div></td>
                    <td><div><span>${report.spocEmail}</span></div></td>
                    <td><div><span>${report.spocMobilePhone}</span></div></td>
                    <td><div><span>${report.ownerName}</span></div></td>
                    <td><div><span>${report.orgName}</span></div></td>
                    <td><div><select class="form-control selectpicker" id="spocStatus" name="spocStatus" onChange="changeSpocStatus(${report.id});">
                     	  <option value="Active" ${report.spocStatus == 'Active' ? 'selected' : ' '}>Active</option>
                     	  <option value="In-Active" ${report.spocStatus == 'In-Active' ? 'selected' : ' '} >In-Active</option>
                     	  <option value="New" ${report.spocStatus == 'New' ? 'selected' : ' '} >New</option>
                     	  <option value="Negotiation" ${report.spocStatus == 'Negotiation' ? 'selected' : ' '}>Negotiation</option>
                     	  <option value="Contracted" ${report.spocStatus == 'Contracted' ? 'selected' : ' '}>Contracted</option>
               			 </select></div></td>
               			
                    <td><div><b class="proj_closed">${report.invitationStatus}</b></div></td>
                    <td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a>
                    <i class="glyphicon glyphicon-trash"></i></div></td>
                </tr>
                	</c:forEach>			
            </tbody>
        </table>
	
</div>	


<nav>
<ul id="paging" class="pagination">
	<li><a href="#">Previous</a></li>
	<li><a href="#">2</a></li>
	<li><a href="#">3</a></li>
	<li><a href="#">4</a></li>
	<li><a href="#">5</a></li>
	<li class="disabled"><a href="#">6</a></li>
	<li data-page="last"><a href="#">Next</a></li>
	</ul>
	<div class="clearfix"></div>
</nav>		
		
	
	
	
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
	function vendorSpocSideBar(spocId,ownerId,spocName) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		result = result
				+ '<li class="current"><a href="#tab-1" onClick="vendorDetailsbySpocId('+spocId+','+ownerId+');">Details</a></li>';
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

		vendorDetailsbySpocId(spocId,ownerId);
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
		result = result + '<li><a href="#tab-2" onclick="vendorContactList('+ orgRelationId + ');">Contacts</a></li>'; 
		result = result + '<li><a href="#tab-3">Documents</a></li>'; 
		result = result + '<li><a href="#tab-4">Activity</a></li>'; 
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '<div id="tab-2" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '</div>';
		result = result + '</div>'
		$("#vendorSideDiv").empty();
		$("#vendorSideDiv").append(result);
		
		$("#vendorNameDiv").empty();
		$("#vendorNameDiv").append(orgName);
		
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
function changeSpocStatus(spocId){
	alert("spocId"+spocId);
	var spocStatus = document.getElementById("spocStatus").value;
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeSpocStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	clientContactList(orgRelationID);
	    }
   });
}


</script>

<script>
function listChange(sel){
	 if(sel.value=="listbyVendor"){
		 document.getElementById("vendors_list").style.display="block";
		 document.getElementById("vendors_contact_list").style.display="none";
	 }
	 if(sel.value=="listbyContact"){
		 document.getElementById("vendors_contact_list").style.display="block";
		 document.getElementById("vendors_list").style.display="none";
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
<%-- <jsp:include page="editSpocDetails.jsp" /> --%>
<jsp:include page="vendorContacts.jsp" />




	
