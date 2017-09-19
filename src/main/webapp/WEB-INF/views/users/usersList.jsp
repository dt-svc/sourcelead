<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style type="text/css">
#loadMoreDiv{
text-align: center;
font-size: 18px;
font-weight: bold;
vertical-align: middle;
padding: 20px 15px;
clear: both;
display: inline-block;
margin: auto;
width: 100%;

}
#loadMoreDiv .fa-spinner{
margin-right: 15px;
}
</style>
<script type="text/javascript">
window.onload = function () {
//getUsersList('manual');
	usersListCheck("${searchValue}","${searchResult}");
	var view = $("#listView").val('GRIDVIEW');
 };
</script>

<script type="text/javascript">
function usersListCheck(searchValue,searchResult){
	if(searchResult == 'searchresult'){
		getUsersSearchResult(searchValue);
	}else{
		getUsersList('manual');
		//searchUser("manual");
	}
}
</script>

<script>
function getUsersSearchResult(searchValue){
	 $.ajax({
        type : "GET",
         url: sourceleadUrl+"/getUniversalSearchUsersList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue,       
         success :function(data) {
        	 prepareUsersList(data);
        },
         
    }); 
}
</script>

<script>
	function predefineProfileImage(imageId){
		$(imageId).attr('src','resources/images/userprofileImg.jpg');
	}
</script>
<input type="hidden" id="noUsers" name="noUsers"/>
<div class="success_pop" id="mailMsg" style="display: none;">
	<i class="fa fa-check-circle"></i><span>Invitation sent successfully</span>
</div>

  <!-- Content Area -->
  
  <section class="content-wraper">
  <div class="content-dashboard clearfix">
   <div id="loading"></div>
    <section class="content-header clearfix">
         <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
      <div class="pull-left">
        <h2>Users </h2>
      </div>
      
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Users List </div>
    </section>
    <div class="timesheet_out">
    <div class="vendor_wrap" style="overflow-x: auto;">
    
    </div>
  </div>
      <section class="clearfix usersHeader positionRelative">
      
      <div class="pull-left cand-advsrche onboardBtn">
       <input id="searchElement" type="hidden"></input>
        <input type="hidden" name="listView" id="listView"/>
			  <%--<a href="javascript:void(0)" class="btn btn-primary btn-sm" title="Advanced Search" onClick="openPop();loadChosenScripts();">
			  Advanced Search&nbsp;&nbsp;
			  <i class="fa fa-search"></i>
			  </a> 
			   <form id="user_forms" name="user_forms"  >
				  <div class="cand-advsrch-popup userAdvsrch" >
					  <a href="javascript:void(0)" class="close-div" onClick="closePop()"><i class="fa fa-close"></i></a>
					  <div class="row">
						  <div class="col-md-4">
							  <label>Any of Keyword <i class="fa fa-info-circle" title="Any of Keyword"></i></label>
							  <div class="clearfix">
							        <select class="chosen-select form-control input-sm" multiple data-placeholder="Any Keyword" id="anyOfKeyword" name="anyOfKeyword">
                                    </select>
							  	 <!-- <input type="text" class="form-control input-sm" placeholder="Any of Keyword" id="anyOfKeyword" name="anyOfKeyword" value=""> -->
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>All Keywords <i class="fa fa-info-circle" title="All Keywords"></i></label>
							  <div class="clearfix">
							  	 <select class="chosen-select form-control input-sm" multiple data-placeholder="All Keywords" id="allKeywords" name="allKeywords">
                                 </select> 
                                 <!-- <input type="text" class="form-control input-sm" placeholder="All Keywords" id="allKeywords" name="allKeywords" value=""> -->
                                 
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>Exclude Keyword <i class="fa fa-info-circle" title="Exclude Keyword"></i></label>
							  <div class="clearfix">
							  	 <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Keyword" id="excludeKeyWord" name="excludeKeyWord">
                                 </select>
                                <!--  <input type="text" class="form-control input-sm" placeholder="Exclude Keyword" id="excludeKeyWord" name="excludeKeyWord" value=""> -->
							  </div>
						  </div>
					  </div>
					  <div class="row">
						
	
						  <div class="col-md-4">
							  <label>Email<i class="fa fa-info-circle" title="Employers"></i></label>
							  <div class="clearfix" id = "emailDiv">
								  <select class="chosen-select form-control input-sm" multiple data-placeholder="Email" id="email" name="email" data-validation="validate_Space validate_email" data-validation-optional="true" onclick = "validationMessage();">
                                  </select>
							  </div>
							  </div>
						  <div class="col-md-4">
							  <label>Phone Number<i class="fa fa-info-circle" title="Employers"></i></label>
							  <div class="clearfix" id = "mobilePhoneDiv">
								 <select class="chosen-select form-control input-sm" multiple data-placeholder="Phone Number" data-validation="validate_Space validate_phone" data-validation-optional="true" id="mobilePhone" name="mobilePhone"onclick = "validationMessage();">
                                  </select>
							  </div>
						  </div>
						  
						   <div class="col-md-4">
							  <label>Role<i class="fa fa-info-circle" title="Employers"></i></label>
							  <div class="clearfix">
								 <select class="chosen-select form-control input-sm" multiple data-placeholder="Role" id="userType" name="userType">
                                  <c:forEach var="option" items="${rolesList}">
	                                   <option value="${option.displayName}">${option.displayName}</option>
                                  </c:forEach>
                                  </select>
							  </div>
						  </div>
						  </div>
						  <div class="row">
						  <div class="col-md-4">
							  <label>Employee Id<i class="fa fa-info-circle" title="Employers"></i></label>
							  <div class="clearfix">
								 <select class="chosen-select form-control input-sm" multiple data-placeholder="Employee Id" id="employeeId" name="employeeId">
                                  </select>
							  </div>
						  </div>
						  
						  <div class="col-md-4">
							  <label>User Status<i class="fa fa-info-circle" title="Employers"></i></label>
							  <div class="clearfix">
								  <select class="chosen-select form-control input-sm" multiple data-placeholder="User Status" id="userStatus" name="userStatus">
                                     <option value="ACTIVE" >Active</option>
									 <option value="INACTIVE" >In Active</option>
									 <option value="NOT_LOGGEDIN">Not Logged IN</option>
                                  </select>
							  </div>
						  </div>
						  
						  
						 <div class="col-md-4">
								<label>Rate</label> 
								<div class="input-group error-field">
								<div id="ratePerDiv" class="input-group-btn selp-custom-min custommin">
									      <select class="selectpicker" name="ratePer" id="ratePer" field-name="Rate Per"  >
									            <option value="">-Rate Per-</option>
						                        <option>Hourly</option>
						                        <option>Daily</option>
						                        <option>Weekly</option>
						                        <option>Bi-Weekly</option>
						                        <option>Semi-Monthly</option>
						                        <option>Monthly</option>
						                        <option>Yearly</option>
				                            </select> 
									</div>
								    <div id="rateDiv">
									  <input type="text" class="form-control input-sm" id="rate"  name="rate"  field-name="Rate"  data-validation="validate_Space validate_float" data-validation-optional="true"  placeholder="Rate">
									</div>
									
								</div>
							</div>
						
					  </div>
					  <div class="form-footer row mt">
						  <div class="pull-right mr">
							 <!--  <a href="javascript:void(0)" title="Submit" class="btn btn-sm btn-primary">Submit</a>
							  <a href="javascript:void(0)" title="Cancel" class="btn btn-sm btn-default ml10">Cancel</a> -->
							   <input type="button" value="Search" id="search_user" onclick="searchUser('manual');" class="btn btn-sm btn-primary"/>
                               <input type="button" value="Cancel" onClick="closePop();" class="btn btn-sm btn-default ml10"/>
						  </div>
					  </div>
				  </div>
			  </form> --%>
      </div>
       
       
       <div class="section-action-btns clearfix">
       <div class="positionRelative clearfix">
       <ul class="pull-right list-unstyled list-inline">
        <input type="hidden" name="usersOffset" id="usersOffset" value="0"/>
         <li><div class="gridFil grid-fil">
					<i class="fa fa-th-list"  title="List" onclick="showList('LISTVIEW');" id="ShowGrid"></i>
					<i class="fa fa-th-large" title="Grid" onclick="showGrid();" id="ShowList"></i>
			</div></li>	
          <li><i class="fa fa-filter"></i>&nbsp;
               <select id="userState" onchange="getUsersView('manual','LISTVIEW');">
				<option value="ACTIVE" >Active</option>
				<option value="">All</option>
				<option value="INACTIVE" >In Active</option>
				<option value="NOT_LOGGEDIN">Not Logged in</option>
			   </select>
          
          </li>
          
		   <li><i class="fa fa-filter"></i>&nbsp;
				<select id="userRole" onchange="getUsersViewBuRole('manual','LISTVIEW');">
				<option value="">&nbsp;--Select Role--&nbsp;&nbsp;&nbsp;</option>
				<!-- <option value="All" >All Roles</option> -->
				<c:forEach var="roles" items="${rolesList}">
	                 <option value="${roles.id}">${roles.displayName}</option>
                </c:forEach>
				</select>
		   </li>
		   
		 <li>
			  <a href="javascript:void(0)">
			  <i class="fa fa-user"></i></a>
				<span class="action-search"><input type="text" placeholder="Email/Name" id="searchByName"></span>
				<span class="action-search" onclick="getUsersList('manual');" title="Search"><i class="fa fa-search"></i></span>
		  </li> 
		
		   <sec:authorize access="hasAnyRole('ROLE_CREATE_USER')"> 
	           <li><a href="<c:url value='/userCreation'/>"><i class="fa fa-user-plus"></i>&nbsp;Add User</a></li>
	       </sec:authorize>

		     <sec:authorize access="hasAnyRole('ROLE_USER_BULK_IMPORTS')"> 
	                      <li title="Import"><a href="<c:url value='/uploadForm'/>"><i class="glyphicon glyphicon-import expIcon"></i>&nbsp;</a></li>
	         </sec:authorize>		  
		  
		   <sec:authorize access="hasRole('ROLE_EXPORT_USERS')"> 
		   <li title="Export"><a  id="export-pop"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>
			    <div id="exportPop" class="user_exp">
				   <ul class="ul_padding">
				   <li onclick="exportToPdf();" title="PDF"><i class="fa fa-file-pdf-o"></i>&nbsp; PDF</li>
				   <li onclick="exportUserData();" title="Excel"><i class="fa fa-file-excel-o"></i>&nbsp; Excel</li>
				   </ul>
				</div>
		   </li>
		    </sec:authorize>
        </ul>
      </div>
      </div>
      </section>
      <div class="project_wrap_table datatable_filter" id="userListGrid">
              <div id="userListViewDiv" class="filter_position backgroundWhite pdtop mt" style="display:none;"></div> 
          </div>  
      <div class="clearfix form-area form-horizontal toggle-div remove-div">
      <div class="row">
          <div id="usersListBody"></div> 

          <div id="loadMoreDiv"></div> 
      </div>
      
               
      
      <div id="usersListEmptyBody"></div>
      <div id="popUp"></div>
      </div>
      </div>
  </section>
  
      <!-- Content Area Ends--> 

      <jsp:include page="../masterFooter.jsp" />
      <div class="backtotop" style="display: none;"><img src="resources/images/backtotop.png"></div>  
  
 <!--  Rajashkar
  SCript is used to export user data as xls format -->    
<script>
function exportUserData(){
	var userListEmpty = $("#noUsers").val();
	var exportType = "xls";
	var viewType = "LISTVIEW";
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    var userRole = document.getElementById("userRole").value;
	var searchByName = document.getElementById("searchByName").value;
	var userState = document.getElementById("userState").value;
	if(userListEmpty != 'nousers'){
		window.location.href = sourceleadUrl + "/getExportUsers?&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&exportType=xls&viewType=LISTVIEW";
	}else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Users List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	

}
</script> 
<script type="text/javascript">
function cancel(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>
<!-- script for flush advanced search form -->
<script>
function flushAdvancedData(){
	 $("#user_forms")[0].reset();
	 getUsersList("manual");
}
</script>
  
 <!--  Rajashkar
  SCript is used to export user data as pdf format --> 
  <script type="text/javascript">
  function validationMessage() {
	   var email = document.getElementById("email").value;
	   var mobilePhone = document.getElementById("mobilePhone").value;
	   $("#email").attr("disabled", "disabled");
	   $("#search_user").removeAttr("disabled");
	   
}
</script>
<script>
function exportToPdf(){
	var userListEmpty = $("#noUsers").val();
	var exportType = "pdf";
	var viewType = "LISTVIEW";
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    var userRole = document.getElementById("userRole").value;
	var searchByName = document.getElementById("searchByName").value;
	var userState = document.getElementById("userState").value;
	if(userListEmpty != 'nousers'){
	window.location.href = sourceleadUrl + "/getExportUsers?&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&exportType=pdf&viewType=LISTVIEW";
	}else{
		 var result = "";
			result = result +"	<div class='project_inn_mask'></div>";
			result = result +"	   <div id='empty_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Users List is Empty</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
			result = result +"	</div>";
			$("#popUp").append(result);
			$("#empty_user_pop").slideDown(200);
			$(".project_inn_mask").fadeIn(200);
	}
	
}
</script>
   
   <script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
		}
		function openPop(){
		  $("div.cand-advsrch-popup").toggle();
		  
		}
	</script>
	<script type="text/javascript">	
	function loadChosenScripts(){
	    $('#search_user').removeAttr('disabled');
	    $('#rate').val(''); 
	    $('#ratePer').val('');
	    $('#mobilePhone').val('');

	   
	    $('#rateDiv').find('p.jquery_form_error_message').remove();
	    $('#mobilePhoneDiv').find('p.jquery_form_error_message').remove();
	    $('#ratePerDiv').find('p.jquery_form_error_message').remove();
	    $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated");
	    $("#email").val('').trigger("chosen:updated");
	    $("#mobilePhone").val('').trigger("chosen:updated");
	    $("#userType").val('').trigger("chosen:updated");
	    $("#employeeId").val('').trigger("chosen:updated");
	    $("#userStatus").val('').trigger("chosen:updated");
	    
	    $("#anyOfKeyword").empty();
	    $("#allKeywords").empty();
	    $("#excludeKeyWord").empty();
	    $("#email").empty();
	    $("#mobilePhone").empty();
	    $("#employeeId").empty();
	    
	}
</script>
   
 <!--  David
  Script is used scroll based userList -->   
<script>
	$(document).scroll(function(e){
	    // grab the scroll amount and the window height
	    
	    var view = $("#listView").val();
	    if(view == "GRIDVIEW"){
	    	var scrollAmount = $(window).scrollTop();
		    var documentHeight = $(document).height();
		    offset = $("#usersOffset").val();
		    var UsersCount = $("#totalUsersCount").val(); 
		    // calculate the percentage the user has scrolled down the page
		    if($(window).scrollTop()+1 >= $(document).height() - $(window).height()) {
		    	var avilableCount = $("#usersOffset").val();
		    	
		    	avilableCount = parseInt(avilableCount) + parseInt(10); 
		    	$("#usersOffset").val(avilableCount);
		    	//searchUser("scroll");
		    	 getUsersList("scroll");
		    }
	    }else{
	    	$('#loadMoreDiv').empty(); 
	    }
	});
</script>


<!--  David
  Script is used search  based on search key if Enter Key is Pressed -->
  
 <script type="text/javascript">
   
    $("#searchByName").keydown(function (e) {
			  if (e.keyCode == 13) {
			    $('input[name = searchByName]').click();
				 $("#user_forms")[0].reset();
				 getUsersList("manual");
			  }
			});
  </script> 
 
 <!--  David
  this script is not used -->    
<script type="text/javascript">
	function getUsersList(type){
		var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	    var userRole = document.getElementById("userRole").value;
		
		var searchByName = document.getElementById("searchByName").value;
		var userState = document.getElementById("userState").value;
		
		
		if(type == "scroll"){
			offset = $("#usersOffset").val();
			var searchByName =  document.getElementById("searchElement").innerHTML
    	}
		
		if(type == "manual"){
			$('#usersListBody').empty();
    		$("#usersOffset").val(0);
    		$("#searchElement").val("");
    		var searchByName = document.getElementById("searchByName").value;
   		     $('#searchElement').append(searchByName);
    		 offset = 0;
    	}
	
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		
		 $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getUsersList?"+tokenParamName+"="+tokenValue+"&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&offset="+offset,
	    			 
            success: function(data) {
            	 var json = JSON.parse(data);
            	 var result = "";
          if(data != null && data !='undefined' && data !=null &&  data != [] && json.length >0){ 	
	       	
	    	if(json.length > 0)	
	   		{
	    		$('#loadMoreDiv').empty(); 
	 			for(var i=0; i<json.length; i++){
	 				
			 				var userRole = json[i];
			 				var mobilePhone = ""
			 			    
			 			    if(userRole.user.mobilePhone != null && userRole.user.mobilePhone != ''){
			 			    	mobilePhone = userRole.user.mobilePhone;
			 			    }else{
			 			    	mobilePhone = "No Data"
			 			    }
			 				
			       	    	result = "<div class='col-xs-12 col-sm-12 col-md-6 col-lg-4'>";
			       	    	result = result +"<div class='user-profile'>";
			       	    	result = result +"<div class='col-xs-4 col-sm-4'>";
			       	    	result = result +"<div class='text-center image-parent'>";
			       	    	result = result +"<div class='imageParent'>";
			       	    	result = result +"<img onerror='predefineProfileImage(this);' class='m-t-xs'  src='"+sourceleadUrl+"/displayUserImage/"+userRole.userId+"' alt='image'>";
			       	    	result = result +"</div>";
			       	    	
			       	    	
			       	    	result = result +"</div>";
			       	    	result = result +"</div>";
			       	    	result = result +"<div class='col-xs-8 col-sm-8'>";
			       	    	
			       	    	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_USER\',\'ROLE_VIEW_EDIT_USER\',\'ROLE_VIEW_DELETE_USER\')">'
			       	         	result = result +"<h4><a href='<c:url value='/userSummary/"+userRole.userId+"'/>''> <span title='"+userRole.userName+"'> "+userRole.userName+"</span> </a> </h4>";
			       	    	'</sec:authorize>';
			       	    	
			       	    	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_USER\',\'ROLE_VIEW_EDIT_USER\',\'ROLE_VIEW_DELETE_USER\')">'
		       	         	   result = result +"<h4><a href='#'> "+userRole.userName+" </a> </h4>";
		       	    	    '</sec:authorize>';
			       	    	
			       	    	result = result +"<p><label><i class='fa fa-envelope-o' aria-hidden='true'></i>&nbsp;<span title='"+userRole.userEmail+"'> "+userRole.userEmail+"</span></lable></p>";
			       	    	result = result +"<p><label><i class='fa fa-phone'  aria-hidden='true'></i>&nbsp;<span title='"+mobilePhone+"'> "+mobilePhone+"</span> </label></p>";
			       	    	result = result +"<p><label><i class='fa fa-user'  aria-hidden='true'></i>&nbsp;<span title='"+userRole.userRoleName+"'> "+userRole.userRoleName+"</span></label></p>";
			       	        
			       	    	if(userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'N'){
			       	    	  result = result +"<p><label id='user"+userRole.userId+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Invite</a></label></p>";
			 			    }else if((userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'Y') &&
			 			    		(userRole.userState != null && userRole.userState != "" && userRole.userState == 'NOT_LOGGEDIN') ){
			 			    	 result = result +"<p><label id='user"+userRole.userId+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Resend</a></label></p>";
			 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'ACTIVE'){
			 			    	result = result +"<p><label class='label label-success'><i class='fa fa-check'></i>&nbsp;&nbsp;Active</label></p>";
			 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'INACTIVE'){
			 			    	result = result +"<p><label class='label label-danger'><i class='fa fa-ban'></i>&nbsp;&nbsp;In-active</label></p>";
			 			    } 
			       	    	
			       	    	result = result +"</div>";
			       	    	result = result +"<div class='clearfix'></div>";
			       	    	result = result +"</div>";
			       	    	result = result +"</div>";
			       	    	$("#loading").removeAttr("style"); 
			       	    	if(type == "manual"){
			            		$('#usersListBody').append(result);
			            		 $("#searchByName").val("");
			            	}else{
			            		$('#usersListBody').append(result);
			            		 $("#searchByName").val("");
			            	}
			
	       	    	

				   }
	 			result = result + '<div class="clear"></div>';
	 			$("#noUsers").val("")
	   		    }
            }else if(type == "scroll"){
            	$("#noUsers").val("nousers")
            	$('#loadMoreDiv').empty(); 
            	$("#loading").removeAttr("style");
            	var result ="";
	         	result = result + '<div id="loadMoreDiv" >No More Records..</div>';
	         	$('#loadMoreDiv').append(result);
	         	$("#noUsers").val("")
            }else{
            	$("#noUsers").val("nousers")
            	$('#loadMoreDiv').empty(); 
            	$("#loading").removeAttr("style");
            	var result ="";
	         	result = result + '<div id="loadMoreDiv" >No  Records..</div>';
	         	$('#loadMoreDiv').append(result);
            }
	    	
			 },
	        error: function(e){
	        }
	    });  

 }
</script>



<script type="text/javascript">
function resendInvitation(userId){
	if(userId != null && userId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendInvitationEmail?"+tokenParamName+"="+tokenValue+"&userId="+userId, 
			        success: function(result) {
			        	$("#user"+userId).html("<i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)'  onClick='resendInvitation("+userId+");'>&nbsp;Resend</a>");
			        	$("#loading").removeAttr("style"); 
			        	$("#mailMsg").fadeIn(200);
		   		        $('#mailMsg').delay(2000).fadeOut('slow');
			        },
			    });
		 }
	}
  
</script>
<script type="text/javascript">
var validationSettings = {
		errorMessagePosition : 'element',
		borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd'
	};
</script>
<script type="text/javascript">

    
    
	function searchUser(type) {
		if(type == "scroll"){
			offset = $("#usersOffset").val();
			var searchByName =  document.getElementById("searchElement").innerHTML 
    	}
		
		if(type == "manual"){
    		$('#usersListBody').empty();
    		$("#usersOffset").val(0);
    	 	$("#searchElement").val("");
    		var searchByName = document.getElementById("searchByName").value;
   		     $('#searchElement').append(searchByName);
    		 offset = 0;
    	}
		var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	    var userRole = document.getElementById("userRole").value;
		/* var searchByName = document.getElementById("searchByName").value; */
		var userState = document.getElementById("userState").value;
		
		if(loginUserRole != ''){
		 loginUserRole = '${SourceLeadWeb_loginUserRole}';
		}else{
			loginUserRole = "";	
		}
		if(userRole != ''){
			userRole  =  userRole;
		}else{
			userRole ="";
		}
		if(searchByName != ''){
			 searchByNames = searchByName;
		}else{
			searchByName = "";
		}
		if(userState != ''){
			userState = userState;
		}else{
			userState = "";
		}
		 if ($('#user_forms').validate(false, validationSettings)){
			 
      		var frm = $('#user_forms').serializeFormJSON();
			var con = JSON.stringify(frm);
		 	con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
		    var form = $('#user_forms')[0];
      		var formData = new FormData(form);  
			$("#search_user").attr("disabled", "disabled");
			 $.ajax({
						 type : "POST",
		        	     data: formData,
		        	     processData: false,
		    	         contentType:false,
		        	     dataType : 'json',
						 url : sourceleadUrl+"/searchUser?"+tokenParamName+"="+tokenValue+"&json="+jsonData+"&offset="+offset+"&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole,
						 success : function(data) {
							$("#search_user").addClass('button').val('Search');
							$("#search_user").removeAttr("disabled");
							closePop();
							prepareUsersList(data,type);
						},
					}); 
			return true;
		 }else{ 
				return false;
		 } 
	} 
	
	$('body').on('blur', '#user_forms', function() {
		$("#user_forms").showHelpOnFocus();
		$("#user_forms").validateOnBlur(false, validationSettings);
	});
</script>

<script type="text/javascript">
function getUserSummary(userId){
	if(userId != null && userId != ""){
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/userSummary?"+tokenParamName+"="+tokenValue+"&userId="+userId, 
			        success: function(result) {
			        	 
			        },
			    });
		 }
	}
  
</script>
<script>
  function prepareUsersList(data,type){
	  if(data != null && data != 'undefined' && data != "" && data != '[]'){
		  var result = "";
		  var json = JSON.parse(data);
		  if(json.length > 0)	
	   		{
	    		$('#loadMoreDiv').empty(); 
	 			for(var i=0; i<json.length; i++){
	 				
			 				var userRole = json[i];
			 				var mobilePhone = ""
			 				var lastName = ""
			 			    if(userRole.mobilePhone != null && userRole.mobilePhone != ''){
			 			    	mobilePhone = userRole.mobilePhone;
			 			    }else{
			 			    	mobilePhone = "No Data"
			 			    }
			 				if(userRole.lastName != null && userRole.lastName != ''){
			 					lastName = userRole.lastName;
			 			    }else{
			 			    	lastName = " "
			 			    }
			       	    	result = "<div class='col-xs-12 col-sm-12 col-md-6 col-lg-4'>";
			       	    	result = result +"<div class='user-profile'>";
			       	    	result = result +"<div class='col-xs-4 col-sm-4'>";
			       	    	result = result +"<div class='text-center image-parent'>";
			       	    	result = result +"<div class='imageParent'>";
			       	    	result = result +"<img onerror='predefineProfileImage(this);' class='m-t-xs'  src='"+sourceleadUrl+"/displayUserImage/"+userRole.userId+"' alt='image'>";
			       	    	result = result +"</div>";
			       	    	
			       	    	
			       	    	result = result +"</div>";
			       	    	result = result +"</div>";
			       	    	result = result +"<div class='col-xs-8 col-sm-8'>";
			       	    	
			       	    	'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_USER\',\'ROLE_VIEW_EDIT_USER\',\'ROLE_VIEW_DELETE_USER\')">'
			       	         	result = result +"<h4><a href='<c:url value='/userSummary/"+userRole.userId+"'/>''> <span title='"+userRole.firstName+" "+lastName+"'> "+userRole.firstName+" "+lastName+" </span> </a> </h4>";
			       	    	'</sec:authorize>';
			       	    	
			       	    	'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_USER\',\'ROLE_VIEW_EDIT_USER\',\'ROLE_VIEW_DELETE_USER\')">'
		       	         	   result = result +"<h4><a href='#'> "+userRole.firstName+" " " "+lastName+"</a> </h4>";
		       	    	    '</sec:authorize>';
			       	    	
			       	    	result = result +"<p><label><i class='fa fa-envelope-o' aria-hidden='true'></i>&nbsp;<span title='"+userRole.email+"'> "+userRole.email+"</span></lable></p>";
			       	    	result = result +"<p><label><i class='fa fa-phone'  aria-hidden='true'></i>&nbsp;<span title='"+mobilePhone+"'> "+mobilePhone+"</span> </label></p>";
			       	    	result = result +"<p><label><i class='fa fa-user'  aria-hidden='true'></i>&nbsp;<span title='"+userRole.userType+"'> "+userRole.userType+"</span></label></p>";
			       	        
			       	    	if(userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'N' && userRole.userStatus != 'INACTIVE' && userRole.userStatus != 'ACTIVE'){
			       	    		result = result +"<p><label id='user"+userRole.userId+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Invite</a></label></p>";
			 			    }else if((userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'Y') &&
			 			    		(userRole.userStatus != null && userRole.userStatus != "" && userRole.userStatus == 'NOT_LOGGEDIN') ){
			 			    	 result = result +"<p><label id='user"+userRole.userId+"' class='label label-info'><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Resend</a></label></p>";
			 			    }else if(userRole.userStatus != null && userRole.userStatus != "" && userRole.userStatus == 'ACTIVE'){
			 			    	result = result +"<p><label class='label label-success'><i class='fa fa-check'></i>&nbsp;&nbsp;Active</label></p>";
			 			    }else if(userRole.userStatus != null && userRole.userStatus != "" && userRole.userStatus == 'INACTIVE'){
			 			    	result = result +"<p><label class='label label-danger'><i class='fa fa-ban'></i>&nbsp;&nbsp;In-active</label></p>";
			 			    } 
			       	    	
			       	    	result = result +"</div>";
			       	    	result = result +"<div class='clearfix'></div>";
			       	    	result = result +"</div>";
			       	    	result = result +"</div>";
			       	    	$("#loading").removeAttr("style"); 
			       	    	$('#usersListBody').append(result);
		            		$("#searchByName").val("");

				   }
	 			result = result + '<div class="clear"></div>';
	 			$("#noUsers").val("")
	   		    }
			  }else if(type == "scroll"){
		      	$("#noUsers").val("nousers")
		    	$('#loadMoreDiv').empty(); 
		    	$("#loading").removeAttr("style");
		    	var result ="";
		     	result = result + '<div id="loadMoreDiv" >No More Records..</div>';
		     	$('#loadMoreDiv').append(result);
		    }
			  else{
		      	  $("#usersListBody").removeAttr("style");
		      	  $('#usersListBody').empty();
		      	  $('#loadMoreDiv').empty(); 
		          $("#loading").removeAttr("style");
		          var result ="";
		       	  result = result + '<div id="loadMoreDiv" >No  Records..</div>';
		       	  $('#usersListBody').append(result);
		      }
  }
</script>    
<script>
// Back To Top 
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('.backtotop').fadeIn('slow');
        } else {
            $('.backtotop').fadeOut('slow');
        }
    });
    $('.backtotop').click(function() {
        $("html, body").animate({scrollTop: 0}, 500);
        return false;
    });
</script> 
   

<script>
  $("#selector").fadeIn(200);
  $('#selector').delay(2000).fadeOut('slow');
	   $("#export-pop").click(function(){
	               $("#exportPop").toggle();
	       });
	   $("html").click(function() {
	       $("#exportPop").hide();
	   });
	   $("#exportPop, #export-pop").click(function(e) {
	       e.stopPropagation();
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
			//Adding Customized Any Of Keyword to the list 
		    var select, chosen;
		    select = $("#anyOfKeyword");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keyup', function(e)
		    {
		    	var anyKeyword = this.value.replace(",","");
		        if ((e.which == 13 || e.which == 188) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(anyKeyword).text(anyKeyword);
		            select.append(option);
		            select.find(option).prop('selected', true);
                    $("#anyOfKeyword option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#anyOfKeyword").trigger("chosen:updated");
	        		
		        }
		    });
		    
		  //Adding Customized Any Of Keyword to the list
		    var selectone, chosenone;
		    selectone = $("#allKeywords");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keyup', function(e1)
		    {
		    	var allKeyword = this.value.replace(",","");
		        if ((e1.which == 13 || e1.which == 188) && (allKeyword != null  && allKeyword != '' && allKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(allKeyword).text(allKeyword);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
                    $("#allKeywords option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#allKeywords").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selecttwo, chosentwo;
		    selecttwo = $("#excludeKeyWord");
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Keyword:' });
		    chosentwo = selecttwo.data('chosen');
		    chosentwo.search_field.on('keyup', function(e2)
		    {
		    	var excludeKeyword = this.value.replace(",","");
		        if ((e2.which == 13 || e2.which == 188) && (excludeKeyword != null  && excludeKeyword != '' && excludeKeyword != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(excludeKeyword).text(excludeKeyword);
		            selecttwo.append(option);
		            selecttwo.find(option).prop('selected', true);
                    $("#excludeKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#excludeKeyWord").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selectthree, chosenthree;
		    selectthree = $("#email");
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Email:' });
		    chosenthree = selectthree.data('chosen');
		    chosenthree.search_field.on('keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectthree.append(option);
		            selectthree.find(option).prop('selected', true);
                    $("#email option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#email").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selectfour, chosenfour;
		    selectfour = $("#mobilePhone");
		    selectfour.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Mobile Phone:' });
		    chosenfour = selectfour.data('chosen');
		    chosenfour.search_field.on('keyup', function(e6)
		    {
		    	var cert = this.value.replace(",","");
		        if ((e6.which == 13 || e6.which == 188) && (cert != null  && cert != '' && cert != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(cert).text(cert);
		            selectfour.append(option);
		            selectfour.find(option).prop('selected', true);
                    $("#mobilePhone option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#mobilePhone").trigger("chosen:updated");
	        		
		        }
		    });
		    
		     var selectfive, chosenfive;
		    selectfive = $("#employeeId");
		    selectfive.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add EmployeeId:' });
		    chosenfive = selectfive.data('chosen');
		    chosenfive.search_field.on('keyup', function(e6)
		    {
		    	var cert = this.value.replace(",","");
		        if ((e6.which == 13 || e6.which == 188) && (cert != null  && cert != '' && cert != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(cert).text(cert);
		            selectfive.append(option);
		            selectfive.find(option).prop('selected', true);
                    $("#employeeId option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#employeeId").trigger("chosen:updated");
	        		
		        }
		    });
		    
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
</script> 

<!-- Added By Rajashekar -->
<script>
	$("#ShowList, #listView").hide();
	$("#ShowGrid").click(function(){
		$("#ShowList").show();
		$("#ShowGrid").hide();
		$("#listView").show();
		$("#gridView").hide();		
	});
	$("#ShowList").click(function(){
		$("#ShowGrid").show();
		$("#ShowList").hide();
		$("#gridView").show();
		$("#listView").hide();			
	});	
</script>

  <script>
  function getUsersView(type,viewFormat){
	  var viewType = $("#listView").val();
	  if(viewType != 'LISTVIEW'){
		  getUsersList(type);
	  }else{
		  getUsersListView('manual',viewFormat);  
	  }
	   
  } 
  </script>
  
  <script>
function getUsersViewBuRole(type,viewForm){
	var viewType = $("#listView").val();
	  if(viewType != 'LISTVIEW'){
		  getUsersList(type);
	  }else{
		  getUsersListView('manual',viewForm);  
	  }
} 
</script>

<script>
function showGrid(){
	$("#listView").val('GRIDVIEW');
	$("#userListViewDiv").hide();
	getUsersList('manual');
	$("#usersListBody").show();
	
}

function showList(viewType){
	var val = $("#listView").val("LISTVIEW");
	$("#usersListBody").hide();
	getUsersListView('manual',viewType);
	$("#userListViewDiv").show();
	$('#loadMoreDiv').empty();
	
}
</script>


<script type="text/javascript">
	function getUsersListView(type,viewType){
		var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	    var userRole = document.getElementById("userRole").value;
		
		var searchByName = document.getElementById("searchByName").value;
		var userState = document.getElementById("userState").value;
		
		/* if(type == "scroll"){
			offset = $("#usersOffset").val();
			var searchByName =  document.getElementById("searchElement").innerHTML
    	} */
		
		if(type == "manual"){
			$('#usersListBody').empty();
    		$("#usersOffset").val(0);
    		$("#searchElement").val("");
    		var searchByName = document.getElementById("searchByName").value;
   		     $('#searchElement').append(searchByName);
    		 offset = 0;
    	}
		 $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getUsersList?"+tokenParamName+"="+tokenValue+"&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&offset="+offset+"&viewType="+viewType,
	    			 
            success: function(data) {
            	 var json = JSON.parse(data);
            	 var result = "";
	    		    var result = "";				        	
					result = result + '<table id="userListView" class="display" cellspacing="0" width="100%" >';
					result = result + '<thead>';
					result = result + '<tr>';
					result = result + '<th>User Name</th>';
					result = result + '<th>Email Id</th>';
					result = result + '<th>Phone No.</th>';
					result = result + '<th>Role</th>';
					result = result + '<th>Manager</th>';
					result = result + '<th>Employment Type</th>';
					result = result + '<th>Status</th>';
					/* result = result + '<th>Action</th>'; */
					result = result + '</tr>';
					result = result + '</thead>	';
					result = result + '<tbody>';
			if(data != null && data !='undefined' && data != ''){ 
	 			for(var i=0; i<json.length; i++){
	 				
			 				var userRole = json[i];
			 				var mobilePhone = ""
			 			    
			 			    if(userRole.user.mobilePhone != null && userRole.user.mobilePhone != ''){
			 			    	mobilePhone = userRole.user.mobilePhone;
			 			    }else{
			 			    	mobilePhone = "--"
			 			    }
			 				
			 				  result = result + ' <tr>';
			        		   if(userRole.userName !=null && userRole.userName !='' && userRole.userName !='undefined'){
			        			   '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_USER\',\'ROLE_VIEW_EDIT_USER\',\'ROLE_VIEW_DELETE_USER\')">' 
			        			   result = result +"<td>"    
			        			    result = result +" <a href='<c:url value='/userSummary/"+userRole.userId+"'/>'' class='UserTextLeft'>";
					       	    	result = result +"<img onerror='predefineProfileImage(this);' class='m-t-xs'  src='"+sourceleadUrl+"/displayUserImage/"+userRole.userId+"' alt='image'>";
									result = result + ''+userRole.userName+'</a></td>';
									'</sec:authorize>';
								}else{
								result = result + ' <td><div>--</div></td>';
								}
			        		    
			        		    if(userRole.userEmail !=null && userRole.userEmail !='' && userRole.userEmail !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+userRole.userEmail+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
			        		    if(mobilePhone !=null && mobilePhone !='' && mobilePhone !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+mobilePhone+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
			        		    result = result + ' <td><div class="tblminwidth-75">'+userRole.userRoleName+'</div></td>';
			        		    if(userRole.reportingManagerName !=null && userRole.reportingManagerName !='' && userRole.reportingManagerName !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+userRole.reportingManagerName+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
			        		    if(userRole.employeMentType !=null && userRole.employeMentType !='' && userRole.employeMentType !='undefined'){
									result = result + ' <td><div class="tblminwidth-75">'+userRole.employeMentType+'</div></td>';
								}else{
									result = result + ' <td><div>--</div></td>';
								}
			        		    if(userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'N'){
					       	    	  result = result +"<td><p><label id='user"+userRole.userId+"' class='label label-info'><b class='fa fa-envelope' aria-hidden='true'></b><a class='color_white' href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Invite</a></label></p></td>";
					 			    }else if((userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'Y') &&
					 			    		(userRole.userState != null && userRole.userState != "" && userRole.userState == 'NOT_LOGGEDIN') ){
					 			    	 result = result +"<td><p><label id='user"+userRole.userId+"' class='label label-info'><b class='fa fa-envelope' aria-hidden='true'></b><a class='color_white' href='javascript:void(0)' onClick='resendInvitation("+userRole.userId+");'>&nbsp;&nbsp;Resend</a></label></p></td>";
					 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'ACTIVE'){
					 			    	result = result +"<td><p><label class='label label-success'><b class='fa fa-check'></b>&nbsp;&nbsp;Active</label></p></td>";
					 			    }else if(userRole.userState != null && userRole.userState != "" && userRole.userState == 'INACTIVE'){
					 			    	result = result +"<td><p><label class='label label-danger'><b class='fa fa-ban'></b>&nbsp;&nbsp;In-active</label></p></td>";
					 			    } 
					       	    	
			        		    /* result = result + ' <td><div class="tblminwidth-75">Remove</div></td>'; */
			        		   /*  result = result + '<td><div><i class="glyphicon glyphicon-trash" onClick="terminateUSer('+userRole.id+')"; title="Terminate User"></i></div></td>'; */
								result = result + ' </tr>';
				   }
	   		    
            }
            result = result + '  </tbody>';
			result = result + ' </table>';
			$("#userListViewDiv").empty();
			$("#userListViewDiv").append(result);
			userListViewPagination();
			 },
	        error: function(e){
	        }
	    });  

 }
</script>

<script>
function userListViewPagination(){
	 $('#userListView').DataTable( { 
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	            "sSearch": "",
	            "sZeroRecords": "", "sEmptyTable": ""
	       }
	    });
	    $("#WagesAndTaxListTable").wrap("<div class='overflow_Auto'></div>")
}
</script>


<!-- End By Rajashekar -->

    