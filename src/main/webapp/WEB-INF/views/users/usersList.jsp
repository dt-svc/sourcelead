<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<style type="text/css">
#loadMoreDiv{
text-align: center;
font-size: 18px;
font-weight: bold;
vertical-align: middle;
padding: 30px 15px;
}
#loadMoreDiv .fa-spinner{
margin-right: 15px;
}
</style>
<script type="text/javascript">
window.onload = function () {
	getUsersList('manual');
 };
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
        <h2>Users List</h2>
      </div>
      
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Users List </div>
    </section>
    <div class="timesheet_out">
    <div class="vendor_wrap" style="overflow-x: auto;">
    
     <div class="pull-left cand-advsrche">
			  <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="Advanced Search" onClick="openPop();loadChosenScripts();">
			  Advanced Search&nbsp;&nbsp;
			  <i class="fa fa-search"></i>
			  </a> 
			  <form id="user_forms" name="user_forms"  >
				  <div class="cand-advsrch-popup" >
					  <a href="javascript:void(0)" class="close-div" onClick="closePop()"><i class="fa fa-close"></i></a>
					  <div class="row">
						  <div class="col-md-4">
							  <label>Any of Keyword <i class="fa fa-info-circle" title="Any of Keyword"></i></label>
							  <div class="clearfix">
							        <!-- <select class="chosen-select form-control input-sm" multiple data-placeholder="Any Keyword" id="anyOfKeyword" name="anyOfKeyword">
                                    </select> -->
							  	 <input type="text" class="form-control input-sm" placeholder="Any of Keyword" id="anyOfKeyword" name="anyOfKeyword" value="">
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>All Keywords <i class="fa fa-info-circle" title="All Keywords"></i></label>
							  <div class="clearfix">
							  	 <!-- <select class="chosen-select form-control input-sm" multiple data-placeholder="All Keywords" id="allKeywords" name="allKeywords">
                                 </select> -->
                                 <input type="text" class="form-control input-sm" placeholder="All Keywords" id="allKeywords" name="allKeywords" value="">
                                 
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>Exclude Keyword <i class="fa fa-info-circle" title="Exclude Keyword"></i></label>
							  <div class="clearfix">
							  	 <!-- <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Keyword" id="excludeKeyWord" name="excludeKeyWord">
                                 </select> -->
                                 <input type="text" class="form-control input-sm" placeholder="Exclude Keyword" id="excludeKeyWord" name="excludeKeyWord" value="">
                                 
							  </div>
						  </div>
					  </div>
					  <div class="row">
					  <div class="col-md-12">
						  <h4>Details:</h4>
					  </div>
						
						  <div class="clearfix">
						  <div class="col-md-4">
							  <label>Employers<i class="fa fa-info-circle" title="Employers"></i></label>
							  <div class="clearfix">
								  <select class="chosen-select form-control input-sm" multiple data-placeholder="Employers" id="employers" name="employers">
                                  </select>
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>Exclude Employers<i class="fa fa-info-circle" title="Employers"></i></label>
							  <div class="clearfix">
								 <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Employers" id="excludeEmployers" name="excludeEmployers">
                                  </select>
							  </div>
						  </div>
						  
						   
						  </div>
						
					  </div>
					  <div class="row form-footer mt">
						  <div class="pull-right mr">
							 <!--  <a href="javascript:void(0)" title="Submit" class="btn btn-sm btn-primary">Submit</a>
							  <a href="javascript:void(0)" title="Cancel" class="btn btn-sm btn-default ml10">Cancel</a> -->
							   <input type="button" value="Search" id="search_user" onclick="searchUser();" class="btn btn-sm btn-primary"/>
                               <input type="button" value="Cancel" onClick="closePop();" class="btn btn-sm btn-default ml10"/>
						  </div>
					  </div>
				  </div>
			  </form>
      </div>
      
      <div class="candidate_wrap_table">
        <div id="candidateList"></div>
        <div id="emptyPopup"></div>
      </div>
    </div>
    
    <!-- Compnay details -->
    <div class="side_mask">
       <div class="proj_close"></div>
    </div>
    <div class="clients_side_wrap">
	    <div class="clients_side_wrap_inn">
		             <div id="candidateSideDiv">
				     </div>  
	    </div>			     
    </div>
    <!-- Compnay details Ends --> 
  </div>
      <section class="clearfix section-action-btns">
        <ul class="pull-right list-unstyled list-inline">
        <input type="hidden" name="usersOffset" id="usersOffset" value="0"/>
          <li><i class="fa fa-filter"></i>&nbsp;
               <select id="userState" onchange="getUsersList('manual');">
				<option value="">--Select Status--</option>
				<option value="ACTIVE" >Active</option>
				<option value="INACTIVE" >In Active</option>
				<option value="NOT_LOGGEDIN">Not Logged IN</option>
			   </select>
          
          </li>
          
		   <li><i class="fa fa-filter"></i>&nbsp;
				<select id="userRole" onchange="getUsersList('manual');">
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
				<span class="action-search"><input type="text" placeholder="Name / Email" id="searchByName"></span>
				<span class="action-search" onclick="getUsersList('manual');"><i class="fa fa-search"></i></span>
		  </li> 
		
		   <sec:authorize access="hasAnyRole('ROLE_CREATE_USER')"> 
	           <li><a href="<c:url value='/userCreation'/>"><i class="fa fa-user-plus"></i>&nbsp;Create</a></li>
	       </sec:authorize>
		  
		   <sec:authorize access="hasRole('ROLE_EXPORT_USERS')"> 
		   <li><a  id="export-pop"><i class="glyphicon glyphicon-export"></i>&nbsp;Export</a>
			    <div class="export_pop">
				   <ul>
				   <li onclick="exportToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp; PDF</li>
				   <li onclick="exportUserData();"><i class="fa fa-file-excel-o"></i>&nbsp; Excel</li>
				   </ul>
				</div>
		   </li>
		    </sec:authorize>
		   
		     <sec:authorize access="hasAnyRole('ROLE_USER_BULK_IMPORTS')"> 
	                      <li><a href="<c:url value='/uploadForm'/>"><i class="fa fa-upload"></i>&nbsp;Bulk Imports</a></li>
	         </sec:authorize>
        </ul>
      </section>
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
	var sourceleadUrl = '${sourceleadUrl}';
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    var userRole = document.getElementById("userRole").value;
	var searchByName = document.getElementById("searchByName").value;
	var userState = document.getElementById("userState").value;
	if(userListEmpty != 'nousers'){
		window.location.href = sourceleadUrl + "/getExportUsers?&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&exportType=xls";
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
  
 <!--  Rajashkar
  SCript is used to export user data as pdf format --> 
<script>
function exportToPdf(){
	var userListEmpty = $("#noUsers").val();
	var exportType = "pdf";
	var sourceleadUrl = '${sourceleadUrl}';
	var loginUserRole = '${SourceLeadWeb_loginUserRole}';
    var userRole = document.getElementById("userRole").value;
	var searchByName = document.getElementById("searchByName").value;
	var userState = document.getElementById("userState").value;
	if(userListEmpty != 'nousers'){
	window.location.href = sourceleadUrl + "/getExportUsers?&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&exportType=pdf";
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
	    
	   
	    $("#anyOfKeyword").val('').trigger("chosen:updated");
	    
	    
	    $("#anyOfKeyword").empty();
	    
	}
</script>
   
 <!--  David
  Script is used scroll based userList -->   
<script>
	$(document).scroll(function(e){
	    // grab the scroll amount and the window height
	    var scrollAmount = $(window).scrollTop();
	    var documentHeight = $(document).height();
	    offset = $("#usersOffset").val();
	    var UsersCount = $("#totalUsersCount").val(); 
	    // calculate the percentage the user has scrolled down the page
	    if($(window).scrollTop() == $(document).height() - $(window).height()) {
	    	var avilableCount = $("#usersOffset").val();
	    	
	    	avilableCount = parseInt(avilableCount) + parseInt(10); 
	    	$("#usersOffset").val(avilableCount);
	    	getUsersList("scroll");
	    }
	});
</script>


<!--  David
  Script is used search  based on search key if Enter Key is Pressed -->
  
 <script type="text/javascript">
   
    $("#searchByName").keydown(function (e) {
			  if (e.keyCode == 13) {
			    $('input[name = searchByName]').click();
			    getUsersList("manual");
			  }
			});
  </script> 
 
 <!--  David
  Script is used search  based on search key if Enter Key is Pressed -->    
<script type="text/javascript">
	function getUsersList(type){
		
		var sourceleadUrl = '${sourceleadUrl}';
		var loginUserRole = '${SourceLeadWeb_loginUserRole}';
	    var userRole = document.getElementById("userRole").value;
		
		var searchByName = document.getElementById("searchByName").value;
		var userState = document.getElementById("userState").value;
		
		
		if(type == "scroll"){
			offset = $("#usersOffset").val();
    	}
		
		if(type == "manual"){
    		$('#usersListBody').empty();
    		$("#usersOffset").val(0);
    		offset = 0;
    	}
		
		type
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
		
		 $.ajax({ 
	    	type: "POST",
	    	url: sourceleadUrl+"/getUsersList?${_csrf.parameterName}=${_csrf.token}&userRole="+userRole+"&searchByName="+searchByName+"&userState="+userState+"&loginUserRole="+loginUserRole+"&offset="+offset,
	    			 
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
			       	        
			       	    	if(userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'N' && userRole.userState != 'INACTIVE'){
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
	         	result = result + '<div id="loadMoreDiv" ><i class="fa fa-spinner"></i>No More Records..</div>';
	         	$('#loadMoreDiv').append(result);
            }else{
            	$("#noUsers").val("nousers")
            	$('#loadMoreDiv').empty(); 
            	$("#loading").removeAttr("style");
	         	result = result + '<div id="loadMoreDiv" ><i class="fa fa-spinner"></i>No  Records..</div>';
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
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != ""){
		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendInvitationEmail?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
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
function getUserSummary(userId){
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != ""){
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/userSummary?${_csrf.parameterName}=${_csrf.token}&userId="+userId, 
			        success: function(result) {
			        	 
			        },
			    });
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
  $('#selector').delay(2000).fadeOut('slow');
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
</script> 


<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
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


<script type="text/javascript">
	function searchUser() {
      		
      		var sourceleadUrl = '${sourceleadUrl}';
      		var frm = $('#user_forms').serializeFormJSON();
			var con = JSON.stringify(frm);
		 	con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
		    var form = $('#user_forms')[0];
      		var formData = new FormData(form);  
			 $.ajax({
						 type : "POST",
		        	     data: formData,
		        	     processData: false,
		    	         contentType:false,
		        	     dataType : 'json',
						 url : sourceleadUrl+"/searchUser?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						 success : function(data) {
							$("#search_user").addClass('button').val('Search');
							$("#search_user").removeAttr("disabled");
							closePop();
							prepareUsersList(data);
						},
					}); 
			return true;
		 
	} 
	
	$('body').on('blur', '#user_forms', function() {
		$("#user_forms").showHelpOnFocus();
		$("#user_forms").validateOnBlur(false, validationSettings);
	});
</script>

<script>
  function prepareUsersList(data){
	  var sourceleadUrl = '${sourceleadUrl}';
	  if(data != null && data != 'undefined' && data != "" && data != '[]'){
		  $('#usersListBody').empty();
		  var result = "";
		  var json = JSON.parse(data);
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
			       	        
			       	    	if(userRole.isMailSent != null && userRole.isMailSent != "" && userRole.isMailSent == 'N' && userRole.userState != 'INACTIVE'){
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
			       	    	$('#usersListBody').append(result);
		            		$("#searchByName").val("");
			       	    	/* if(type == "manual"){
			            		$('#usersListBody').append(result);
			            		 $("#searchByName").val("");
			            	}else{
			            		$('#usersListBody').append(result);
			            		 $("#searchByName").val("");
			            	} */

				   }
	 			result = result + '<div class="clear"></div>';
	 			$("#noUsers").val("")
	   		    }
	  }
  }
</script>    