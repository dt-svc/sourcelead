<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
window.onload = function () {
	getDynamicOrgList();
 };
</script>

<script>
	function predefineOrgImage(imageId){
		$(imageId).attr('src','/resources/images/office-image.jpg');
	}
</script>

<!--success msg -->

<div class="success_pop" id="mailMsg" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Invitation sent successfully</span>
</div>
  <!-- Content Area -->
  
  <section class="content-wraper">
  <div class="content-dashboard clearfix">
   <div id="loading"></div>
    <section class="content-header clearfix">
         <c:if test="${not empty msg}">
	        <div class="success_pop" id="selector" style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
      <div class="pull-left">
        <h2>Orgs List</h2>
      </div>
       <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard">Home</a> &raquo; Orgs List </div>
      <div class="clearfix"></div> 
      <div class="reportsCount req_Crowd req_crowd clearfix">
          <ul class="clearfix">
          <li id="crowd"><label class="mr10"><span><i class="fa fa-registered"></i></span><b id="crowdActive"><input class="myCheckbox valid" type="radio" checked name="sourceleadOrgType" id="sourceleadOrg" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/enrolledOrgsList'" >&nbsp;Registered Companies</label></b></li>
          <li id="crowd"><label class="mr10"><span><i class="fa fa-cubes"></i></span><b id="savedActive"><input class="myCheckbox valid" type="radio" name="sourceleadOrgType" id="sourceleadOrg" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/requestedOrgs'" >&nbsp;Registration Requests</label></b></li>
          <li id="crowd"><label class="mr10"><span><i class="fa fa-laptop"></i></span><b id="savedActive"><input class="myCheckbox valid" type="radio" name="sourceleadOrgType" id="sourceleadOrg" onClick="javascript:window.location.href = '${SourceLeadWeb_sourceleadUrl}/demoRequests'">&nbsp;Demo Requests</label></b></li>
          </ul>
      </div> 
      
    <input id="searchOrgElement" type="hidden"></input>
    </section>
    <div class="project_wrap_table datatable_filter backgroundWhite">
       <section class="clearfix section-action-btns">
        <ul class="pull-right list-unstyled list-inline">
        
          <li><i class="fa fa-filter"></i>&nbsp;
               <select id="userState" onchange="getDynamicOrgList();">
				<option value="">--Select Status--</option>
				<option value="ALL" >All</option>
				<option value="ACTIVE" selected>Active</option>
				<option value="INACTIVE" >In Active</option>
				<option value="NOT_LOGGEDIN">Not Logged IN</option>
			   </select>
          
          </li>
          <li><a href="${SourceLeadWeb_sourceleadUrl}/enrollOrg"><i class="fa fa-cubes"></i>&nbsp;Create</a></li>
		 <li class="positionRelative">
		 <div class="dynamic_search">
	   			<input type='text' placeholder='Search' id='dynamicOrgDataTableSearch'/>
	   			<span class="cursorPointer" title="Search"><i class="fa fa-search" onClick="getDynamicOrgList();"></i></span>
			  </div>
		  </li> 
		  
        </ul>
      </section> 
            <div id="dynamicOrgList"></div>
      </div>
      </div>
  </section>
  
      <!-- Content Area Ends--> 
	<div class="backtotop"><img src="/resources/images/backtotop.png"></div>  
      <jsp:include page="../masterFooter.jsp" />
      
      





<!-- ============================ Laxman Dynamic OrgList functionality ================================= -->

<script>
function getDynamicOrgList(){
	var sourceleadUrl = '${sourceleadUrl}';
	var userState = $("#userState").val();
	var searchValue = $("#dynamicOrgDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	var url = sourceleadUrl+"/getDynamicOrgsList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&userState="+userState;
	prepareDynamicOrgList(url);
    			 
}
</script>
<script>
function prepareDynamicOrgList(url){
	 	var result = "";
		result = result	+ '<div class="clearfix minHeight35 hidden">';
	    result = result	+ '<div class="remove_search">';
		result = result	+ '</div>';
		result = result	+ '</div>';
		result = result	+ '<div class="project_tabs_inn_table">';
		result = result + '<table id="dynamicOrgListView" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th>Company Name</th>';
		result = result + '<th>Email Id</th>';
		result = result + '<th>Phone No.</th>';
		result = result + '<th>Location</th>';
		result = result + '<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>';
		result = result + '</table>';
		result = result	+ '</div>';
		$("#dynamicOrgList").empty();
		$("#dynamicOrgList").append(result);
		var sourceleadUrl ='${sourceleadUrl}';
		dynamicOrgListPagination(url);
}
function dynamicOrgListPagination(url){
	var sourceleadUrl= '${SourceLeadWeb_sourceleadUrl}';
	var colIndex = 0;
    $('#dynamicOrgListView').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 10
        }),
        "columnDefs": [{
    	  	"targets": colIndex++,
    	  	"data": "",
    	  	"render": function(data, type, org, meta) {
    	  		var result = "";
    	  		result = result +"<td><div class='tbldata-dotdot-200 UserTextLeft'>";
       	    	result = result +"<img onerror='predefineOrgImage(this);' class='m-t-xs' data-id='orgLogoImage' src="+org.fileUrl+" alt='image'>";
       	    	if(org.registrationStatus != 'Draft' ){
       	    		result = result +"<a href='${SourceLeadWeb_sourceleadUrl}/orgSummary/"+org.orgCode+"' title='"+org.orgName+"'>"+org.orgName+" </a>";
       	    	}else{
       	    		result = result +'<a href="'+sourceleadUrl+'/enrollOrg?locationCode='+org.locationCode+'">'+org.orgName+'</a>';
       	    	}
       	    	
       	    	result = result + '</div></td>'; 
       	    	return result;
    	  	}
	      },{
	  	  	"targets": colIndex++,
		  	"data": "",
		  	"render": function(data, type, org, meta) {
		  		if(org.email != null && org.email != "" && org.email != "undefined"){
		  			email = org.email;
		  		}else{
		  			email = "--";
		  		}
		  		var result = "";
				result = result + ' <td><div class="tbldata-dotdot" title = "'+email+'">'+email+'</div></td>';
	             return result;
		  	}
		  },{
			  	"targets": colIndex++,
			  	"data": "",
			  	"render": function(data, type, org, meta) {
			  		if(org.mobilePhone != null && org.mobilePhone != "" && org.mobilePhone != "undefined"){
			  			mobilePhone = org.mobilePhone;
			  		}else{
			  			mobilePhone = "--";
			  		}
			  		var result = "";
					result = result + ' <td><div class="tbldata-dotdot-100" title = "'+mobilePhone+'">'+mobilePhone+'</div></td>';

		             return result;
			  	}
		  },{
			  	"targets": colIndex++,
			  	"data": "",
			  	"render": function(data, type, org, meta) {
			  		var result = "";
			  		if(org.location != null && org.location != '' && org.location != 'undefined'){
			  			result = result + ' <td><div class="tbldata-dotdot-big" title = "'+org.location+'">'+org.location+'</div></td>';
			  		}else{
			  			result = result + ' <td><div class="tbldata-dotdot-big">--</div></td>';
			  		}
					

		             return result;
			  	}
		  } 
		  ,{
			  	"targets": colIndex++,
			  	"data": "",
			  	"render": function(data, type, org, meta) {
			  		var result = "";
	    	  		result = result +"<td><div class='tbldata-dotdot_80'>";
			  		
	    	  		if(org.registrationStatus != 'Draft' ){
		    	  		if(org.user != null &&(org.isMailSent != null && org.isMailSent != "" && org.isMailSent == 'Y') &&
		 			    		(org.userState != null && org.userState != "" && org.userState == 'NOT_LOGGEDIN') ){
		 			    	 result = result +"<p><label id='user"+org.userId+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+org.registeredUserId+","+org.id+");'>&nbsp;&nbsp;Resend</a></label></p>";
		 			    }else if(org.user != null && org.userState != null && org.userState != "" && org.userState == 'ACTIVE'){
		 			    	result = result +"<b class='activeGren'>&nbsp;&nbsp;Active</b>";
		 			    }else if(org.user != null && org.userState != null && org.userState != "" && org.userState == 'INACTIVE'){
		 			    	result = result +"<b class='activeGray'>&nbsp;&nbsp;In-active</b>";
		 			    }else if(org.user == null ){
		 			    	result = result +"<b>&nbsp;&nbsp;Guest Org</b>";
		 			    } 
	    	  		}else{
	    	  			result = result +"<b>&nbsp;&nbsp;Draft</b>";
	    	  		}
			  		
			  		
	    	  		result = result +"</td></div>";

		             return result;
			  	}
		  }],
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
// 	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
	    	 
    } );
    $("#dynamicOrgListView").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#dynamicOrgDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getDynamicOrgList();
    	   }
    	});
	
}
</script>

<script type="text/javascript">
function resendInvitation(userId,orgId){
	var atsUrl ='${SourceLeadWeb_atsUrl}';
	if(userId != null && userId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.ajax({
			    	type: "POST",
			        url: atsUrl+"/resendInvitationEmail?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgId="+orgId,  
			        success: function(result) {
			        	$("#user"+userId).html("<i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)'  onClick='resendInvitation("+userId+", "+orgId+");'>&nbsp;Resend</a>");
			        	$("#loading").removeAttr("style"); 
			        	$("#mailMsg").fadeIn(200);
		   		        $('#mailMsg').delay(2000).fadeOut('slow');
			        },
			    });
		 }
	}
  
</script>

 
    