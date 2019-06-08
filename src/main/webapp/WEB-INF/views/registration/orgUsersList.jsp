
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="users" class="tab-pane fade">
			 
			 <div class="success_pop" id="mailSendSuccessMsg"  style="display: none;">
	         		<i class="fa fa-check-circle"></i><span>Mail Send Successfully.</span>
		      </div>
			 
			 <div class="clearfix">
			 <!-- Popup Div -->
                    <div class="collapse-div-adduser orgsummaryuser-pop orgsummary-adduser orgsummary-adduser-custom col-md-4 zindex" id="addNewUserPopup"> <i class="fa fa-close close-div closediv-pop" onclick="flushUser();"></i>
                          <div class="form-horizontal clearfix">
                          <div id="userLoading"></div> 
                        <form:form id="adduser_form"  name="adduser_form" method="post">                             
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="subscribedCountryDiv">
                                <select class="form-control chosen-select" data-validation="required" field-name="Country" id="subscribedCountry"  onchange="getLocationRegisteredCompanies();">
                                	 <option value="">-Select Country-</option>
                                </select>
                              </div>
                            </div>
                            
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="companiesDiv">
                                <select class="form-control chosen-select" data-validation="required" field-name="Company" onchange="getRegisteredOrgRoles();" id="registeredLocation" name="registeredLocation">
                                	 <option value="">-Select Company-</option>
                                </select>
                              </div>
                            </div>
                            
                            <div class="clearfix mb10">
	                              <div class="col-md-12" id="userEmailDiv">
	                                <input type="text" data-validation="required validate_Space validate_length length2-100 validate_email"  field-name="Email" onblur="getUserByUserEmail(); validateUserEmail();"  name="email" id="email" class="form-control input-sm"  placeholder="Email*">
	                              	<input type="hidden" name="registredOrgId" value="${orgId}">
	                              </div>
                              </div>
                              
                             <div class="clearfix mb10">
	                              <div class="col-md-12" id="firstNameDiv">
	                                <input type="text" data-validation="required validate_Space validate_Alphanumeric validate_length length1-100" field-name="First Name" name="firstName" id="firstName" class="form-control input-sm"  placeholder="First Name*">
	                              </div>
                              </div>
                              
                              <div class="clearfix mb10">
	                              <div class="col-md-12" id="lastNameDiv">
	                                <input type="text" data-validation="validate_Space validate_Alphanumeric validate_length length1-100" data-validation-optional="true"  field-name="Last Name"  name="lastName" id="lastName" class="form-control input-sm"  placeholder="Last Name">
	                              </div>
                              </div>
                              
                            
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="userRoleDiv">
                                <select class="form-control chosen-select" data-validation="required" field-name="Role" id="roleTypeId" name="roleTypeId">
                                	 <option value="">-Select Role-</option>
                                </select>
                              </div>
                            </div>
                            <div class="clearfix mr">
                              <button type="submit" id="add_user"  class="btn btn-sm btn-primary pull-right">Add</button>
                              <button type="button" onclick="removeAddUserPopUp();flushUser();" class="btn btn-sm btn-primary pull-right mr">Cancel</button>
                            </div>
                          </form:form>  
                            
                          </div>
                        </div>
				
			 </div>
			
			
	<div class="timesheet_out timesheet_background">
		
        <div class="timesheet_head timesheetHead topfilters">
			 <div class="pull-left cand-advsrch advsrch_chosen">
			     <button type="button" class="btn btn-primary dropdown-toggle btn-sm mr" onclick="resetUsersSearchFilter();" title="Refresh">
				    <span class="fa fa-refresh"></span>
				</button>
				
				<a href="javascript:void(0)" class="btn btn-default btn-sm anchorBlack text-center mr" title="Filters" onclick="openFilterPop();"><i class="fa fa-filter faFilter padding_right"></i>
				</a>
				
				<a href="javascript:void(0)" class="addcon-pop btn btn-sm btn-primary anchorWhite" onclick="getOrgSubscribedCountries();"><i class="glyphicon glyphicon-plus"></i>&nbsp;Add User</a>
				
	       </div> 
        	
        	<ul>
        	<li class="positionRelative">
				  <div class="dynamic_search">
		   			<input type='text' placeholder='Search' id='usersListDataTableSearch'/>
		   			<span class="cursorPointer faSearch" title="Search"><i class="fa fa-search" onClick="getUserList();"></i></span>
				  </div>
			  </li>
	             
                <div class="clearfix"></div>
            </ul>
            <div class="clearfix"></div>
        </div>
			
	<div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew">
    	<div id="userList" class="datatable_filter"></div>
    	 <div id="loading"></div>
	</div>	   
				
</div>

<div class="cand-advsrch-popup advancedsearch-popup filterDynamic-Search">
	<div id="searchFilterLoading"></div>
				<h5 class="headerAdsrch">Filters
					<a href="javascript:void(0)" class="close-div" onClick="closeFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				
				<div class="addInner">
					<div class="row">
						<div class="col-md-2 mb positionRelative">
								<div class="col-md-12 mb">
								<label><strong>Country</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="AllTypeFilter">
										    <c:forEach var="countries" items="${orgSubscribedCountriesList}">
												<li class="radioIput positionRelative"><input type="radio" id="country${countries}"  ${registeredCountry == countries ? 'checked' : ''} value="${countries}"  name="subscribedOrgCountry" onclick="resetOrgLocationRadio();getSearchLocationRegisteredCompanies();"><label>${countries}</label></li>
											</c:forEach>
									    
				                    </ul>
								 </div>
								 </div> 
								 
                            
						</div>
						
						
			   <div class="col-md-2">
					<div class="row">
						<div class="col-md-12 mb">
							<label><strong>Status</strong></label>
							 <div class="clearfix">
								<ul class="list-unstyled" id="userLoggedInStatusFilter">
		                    	    <li class="checkBox positionRelative" ><input  type="checkbox"  name="userLoggedInStatus"  value ="Active"  ><label>Active</label></li>
									<li class="checkBox positionRelative" ><input  type="checkbox"  name="userLoggedInStatus"  value ="In-Active" ><label>In Active</label></li>
									<li class="checkBox positionRelative" ><input  type="checkbox"  name="userLoggedInStatus"  value ="Not-Logged-In" ><label>Not Logged In</label></li>
		                        </ul>
							 </div>
							 </div> 
					     </div>
				</div> 
						
					
					<div class="col-md-3 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="OrgLocCompaniesInput" onkeyup="subscribedOrgLocCompanies()" placeholder="Company" title="Type in a company">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="companyFilterDiv">
										</ul>
										<input type="hidden" name="filteredOrgLocationId" id="filteredOrgLocationId">
									 </div> 
					 </div> 
					 
					 
					 <div class="col-md-3 mb positionRelative">
									<div class="clearfix dynamic_search filterDynamicsearch">
										<input type="text" id="OrgLocRolesInput" onkeyup="subscribedOrgLocRoles()" placeholder="Roles" title="Type in a company">
										<span class="cursorPointer" title="Search"><i class="fa fa-search"></i></span>
									</div>
									 <div class="clearfix filterSearch-ul">
										<ul class="list-unstyled positionRelative filterSearch mt5" id="rolesFilterDiv">
										</ul>
									 </div> 
					 </div>
					 
				 
					
				</div>
					 
				</div>
				
				<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet"
								onclick="closeFilterPop();getUserList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search"
								onclick="resetUsersSearchFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
	</div>
			
			
	</div>
	
	
	
			

<script>

function resetUsersSearchFilter(){
	$("#filteredOrgLocationId").val('');
	$("ul#companyFilterDiv > li input:checkbox").prop("checked",false);
	$("ul#userLoggedInStatusFilter > li input:checkbox").prop("checked",false);
	document.getElementById("country"+'${registeredCountry}').checked = true;
	getSearchLocationRegisteredCompanies(); 
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$("#searchFilterLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	setTimeout(function () {
		getUserList();
		 $("#loading").removeAttr('style');
		 $("#searchFilterLoading").removeAttr('style');
			}, 2000);
	
	
}

function subscribedOrgLocCompanies() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("OrgLocCompaniesInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("companyFilterDiv");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

function subscribedOrgLocRoles() {
    var input, filter, ul, li, a, i;
    input = document.getElementById("OrgLocRolesInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("rolesFilterDiv");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("label")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";

        }
    }
}

</script>			
			
<script>

function closeFilterPop(){
	$("div.cand-advsrch-popup").hide();
	$(".advancedsearch-mask").fadeOut();
}
function openFilterPop(){
	$("div.cand-advsrch-popup").toggle();
	 $(".advancedsearch-mask").fadeIn();
	
	  var subscribedOrgLocationRoles = [];
		$('input:checkbox[name=subscribedOrgLocationRoles]:checked').each(function() {
			subscribedOrgLocationRoles.push($(this).val());
	    });
		
	 getSearchLocationRegisteredCompanies();
	 
	 if(subscribedOrgLocationRoles != null && subscribedOrgLocationRoles != '' && subscribedOrgLocationRoles != 'undefined'){ 
		 $("#searchFilterLoading").attr('style','position:absolute; width:100%; height:450px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		 setTimeout(function () {
			 
			   for(var i=0; i<subscribedOrgLocationRoles.length; i++){
					document.getElementById("role"+subscribedOrgLocationRoles[i]).checked = true;
				 } 
				   $("#searchFilterLoading").removeAttr("style");
			
		}, 1000);
	 }
	
}

function resetUsersFilter(){
	
	$("#usersListDataTableSearch").val('');
	getUserList();
}

function getOrgSubscribedCountries(){
	 var orgId = '${orgId}';
	 if(orgId != null && orgId != ""){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getOrgSubscribedCountries?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Country --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt+ "'>"+opt+"</option>";
					}
		        	$("#subscribedCountry").empty();
			        $("#subscribedCountry").append(output);
			        $('#subscribedCountry').trigger("chosen:updated");
		        },
		    });
	}
	 
}


function getLocationRegisteredCompanies(){
	 var country = document.getElementById("subscribedCountry").value; 
	 var orgId = '${orgId}';
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 if(orgId != null && orgId != ""){
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getLocationEnrolledCompanies?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId+"&country="+country, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Company --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.orgName+"</option>";
					}
		        	$("#registeredLocation").empty();
			        $("#registeredLocation").append(output);
			        $('#registeredLocation').trigger("chosen:updated");
			        
					$("#email").val('');
			        
				    $("#firstName").removeAttr("readonly");
	       			$("#firstName").val('');
	       			 
	       			$("#lastName").removeAttr("readonly");
	       			$("#lastName").val('');
		        },
		    });
	}
}

	function resetOrgLocationRadio(){
		$("#filteredOrgLocationId").val('');
	}	
		
	function getSearchLocationRegisteredCompanies(){
		
		var country =   $('input[name=subscribedOrgCountry]:checked').val();
		 var orgId = '${orgId}';
		 var sourceleadUrl ='${sourceleadUrl}';
		 if(orgId != null && orgId != ""){
			 $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getLocationEnrolledCompanies?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId+"&country="+country, 
			        success: function(json) {
			        	var output = "";
			        	for (var i=0; i<json.length; i++)
						{
				        		var opt = json[i];
				        		var checked = '';
				        		var filteredOrgLocationId = document.getElementById("filteredOrgLocationId").value;
				        		if(filteredOrgLocationId != null && filteredOrgLocationId != '' && filteredOrgLocationId != 'undefined'){
				        			if(opt.id == filteredOrgLocationId){
					        			checked = 'checked';
					        		}
				        		}else{
						        		if(i == 0){
						        			checked = 'checked';
						        		}
				        		}
				        		
				        		output = output + "<li class='radioIput positionRelative'><input type='radio' value='" +opt.id+ "' "+checked+"  name='subscribedOrgLocation' onclick='getRegisteredOrgLocationRoles(" +opt.id+ ");'><label>"+opt.orgName+"</label></li>";
				        		
						}
			        	
			        	$("#companyFilterDiv").empty();
				        $("#companyFilterDiv").append(output);
				        getRegisteredOrgLocationRoles();
			        	
			        },
			    });
		}
	}
</script>			
			
<script type="text/javascript">
 function getUserByUserEmail() {
	 var EmailId = document.getElementById("email").value; 
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/getUserByUserEmail?${_csrf.parameterName}=${_csrf.token}&EmailId="+EmailId, 
		        success: function(json) {
		        	
		        		if(json.firstName != null && json.firstName != ''){
		        			$("#firstName").val(json.firstName);
				        	$("#firstName").attr("readonly", "readonly");
				        	$('#firstNameDiv').find('p.jquery_form_error_message').remove();
			        		
			        		if(json.lastName != null && json.lastName != ''){
			        			$("#lastName").val(json.lastName);
					        	$("#lastName").attr("readonly", "readonly");
			        		}else{
			        			 $("#lastName").removeAttr("readonly");
			        			 $("#lastName").val('');
			        		}
			        		
			        		
		        		}else{
		        			 $("#firstName").removeAttr("readonly");
		        			 $("#firstName").val('');
		        			 
		        			 $("#lastName").removeAttr("readonly");
		        			 $("#lastName").val('');
		        		}
		        		
		        },
		    });
	 }else{
		 $("#FirstName").attr("disabled", false);
		 $("#FirstName").val('');
		 
		 $("#lastName").attr("disabled", false);
		 $("#lastName").val('');
		 
		
	 }
 }
 </script> 
 
 
 
 <script>
function getUserList(){
	   flushUser(); 
	   removeAddUserPopUp();
	   
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +"</div>";
	 	
	 	result = result + '<table id="usersListTable" class="display" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th>Name</th>';
 		result = result +'<th>Email</th>';
 		result = result +'<th>Phone</th>';
 		result = result +'<th>Role</th>';
 		result = result +'<th>Status</th>';
 		result = result +'<th>Location Admin</th>';
 		result = result +'<th>Org Admin</th>';
 		result = result +'<th>Action</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#userList").empty();
	    $("#userList").append(result);
	    
	    usersListDataTableList();
	  
}

</script>


<script>
function usersListDataTableList(){
	
	  var subscribedOrgCountry =  $('input[name=subscribedOrgCountry]:checked').val();
	  var registeredOrgLocationId = $('input[name=subscribedOrgLocation]:checked').val();
	  if(registeredOrgLocationId == null || registeredOrgLocationId == '' || registeredOrgLocationId == 'undefined'){
		  registeredOrgLocationId = '${registeredOrgLocationId}'
		 }
	  var subscribedOrgLocationRoles = [];
		$('input:checkbox[name=subscribedOrgLocationRoles]:checked').each(function() {
			subscribedOrgLocationRoles.push($(this).val());
	    });
	 var userloggedInStatus = [];
		$('input:checkbox[name=userLoggedInStatus]:checked').each(function() {
			userloggedInStatus.push($(this).val());
	    });
	
	  var sourceleadUrl ='${sourceleadUrl}';
      var orgId = '${orgId}';
      
	 var searchValue = $("#usersListDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getOrgRegisteredUsers?searchValue="+searchValue+"&registeredOrgLocationId="+registeredOrgLocationId+"&subscribedOrgLocationRoles="+subscribedOrgLocationRoles+"&userloggedInStatus="+userloggedInStatus;
	 usersListPagination(url,sourceleadUrl,registeredOrgLocationId);
}
</script>


<script>
function usersListPagination(url,sourceleadUrl,registeredOrgLocationId){
	

	var colIndex = 0;
    $('#usersListTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'POST',
          pages: 1
        }),
        "columns": [
            { "data": "userName" },
            { "data": "email"},
            { "data": "mobilePhone"},
            { "data": "role" },
            { "data": "status" },
            { "data": "primaryAdmin" },
            { "data": "superAdmin" }
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "userName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  var name = "";
	    	  		 if(opt.lastName && opt.lastName != '' && opt.lastName != 'undefined'){
                		 name = opt.firstName +" "+ opt.lastName;
                	 }
                	 else{
                		 name = opt.firstName;
                	 }
	    	  		 
	    	  		 result = result + '<td><div class="tblminwidth-75"><span title="'+name+'">'+name+'</span></div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "email",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  
            	  result = result + '<td><div class="tblminwidth-75"><span title="'+opt.email+'">'+opt.email+'</span></div></td>';
            	  
	              return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "mobilePhone",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  var mobilePhone = "";
	            	  if(opt.mobilePhone != null && opt.mobilePhone != '' && opt.mobilePhone != 'undefined' ){
                 		 mobilePhone = opt.mobilePhone;
                 	 }else{
                 		 mobilePhone = '------'
                 	 }
	            		
	            	   result = result + '<td><div class="tblminwidth-75"><span title="'+mobilePhone+'">'+mobilePhone+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "role",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result + '<td><div class="tblminwidth-75"><span title="'+opt.role+'">'+opt.role+'</span></div></td>';
	            		return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "status",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	 var result = "";
	            	   
	               	 if(opt.userState == 'NOT_LOGGEDIN'){
	 			    	 result = result +"<td><i class='fa fa-envelope' aria-hidden='true'></i><a href='javascript:void(0)' onClick='resendInvitation("+opt.id+");' >&nbsp;&nbsp;Resend</a></td>";
 			          }else if(opt.userState == 'ACTIVE'){
 			    	  result = result +'<td>Active</td>';
	 			      }else if(opt.userState == 'INACTIVE'){
	 			    	result = result +'<td>In-active</td>';
	 			      } 
	            	    
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "primaryAdmin",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  
	            	     var name = "";
	            	     var checked = "";
		    	  		 if(opt.lastName && opt.lastName != '' && opt.lastName != 'undefined'){
	                		 name = opt.firstName +" "+ opt.lastName;
	                	 }
	                	 else{
	                		 name = opt.firstName;
	                	 }
		    	  		 
		    	  		if(opt.isPrimaryAdmin == 'Y'){
							checked = "checked"+" "+"value = 'Y'"+" "+"disabled = 'disabled'";
						}else{
							checked = "value = 'N'";
						}
	            	 
	               	 if((opt.userState == 'ACTIVE' || opt.userState == 'NOT_LOGGEDIN') && opt.role == 'Admin'){
                    	     result = result +'<td>'
	                    	 result = result +'<div class="col-md-7 orgrole-switch">';
	                    	 result = result +'<label class="switch">';
	                    	 result = result +'<input type="checkbox" onclick="checkUser('+opt.id+',\''+name+'\')"  name="check'+opt.id+'"  id="check'+opt.id+'" '+checked+'  >';
	                    	 result = result +'<div class="slider round" title="Make as location primary admin"></div>';
	                    	 result = result +'</label>';
	                    	 result = result +'</div>';
                    	
                         result = result +'</td>'; 
                	 }else{
                		 result = result +'<td>-----</td>';
                	 }
	               	 
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "superAdmin",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	  
	            	     var name = "";
	            	     var checked = "";
		    	  		 if(opt.lastName && opt.lastName != '' && opt.lastName != 'undefined'){
	                		 name = opt.firstName +" "+ opt.lastName;
	                	 }
	                	 else{
	                		 name = opt.firstName;
	                	 }
		    	  		 
		    	  		if(opt.isSuperAdmin == 'Y'){
							checked = "checked"+" "+"value = 'Y'"+" "+"disabled = 'disabled'";
						}else{
							checked = "value = 'N'";
						}
	            	 
	               	 if((opt.userState == 'ACTIVE' || opt.userState == 'NOT_LOGGEDIN') && opt.role == 'Admin'){
                    	     result = result +'<td>'
	                    	 result = result +'<div class="col-md-7 orgrole-switch">';
	                    	 result = result +'<label class="switch">';
	                    	 result = result +'<input type="checkbox" onclick="checkSuperUser('+opt.id+',\''+name+'\')"  name="checkSuper'+opt.id+'"  id="checkSuper'+opt.id+'" '+checked+'  >';
	                    	 result = result +'<div class="slider round" title="Make as org super admin"></div>';
	                    	 result = result +'</label>';
	                    	 result = result +'</div>';
                    	
                         result = result +'</td>'; 
                	 }else{
                		 result = result +'<td>-----</td>';
                	 }
	               	 
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "action",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	 var result = "";
	               	 if(opt.userState == 'ACTIVE'){
	 			    	 result = result +"<td><i class='fa fa-sign-in' aria-hidden='true'></i><a href='<c:url value='/app/productionSupportDashboard/"+registeredOrgLocationId+"/"+opt.id+"/${SourceLeadWeb_userId}'/>' >&nbsp;&nbsp;Switch</a></td>";
 			          }else{
 			    	     result = result +'<td>-----</td>';
	 			      }
	            	    
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[0,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": true,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#usersListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#usersListDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   getUserList();
    	   }
    	});
}
</script>
    
 
 


<script>
function checkUser(userId,name){
	var checked = document.getElementById("check"+userId).checked
	if(checked == true){
		alertMessage("Do you want to make "+name+" as primary admin?",userId);
	}
}

function checkSuperUser(userId,name){
	var checked = document.getElementById("checkSuper"+userId).checked
	if(checked == true){
		alertSuperAdminMessage("Do you want to make "+name+" as super admin?",userId);
	}
}

</script>

<script type="text/javascript">
function cancel(userId){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	document.getElementById("check"+userId).checked = false;
}

function cancelSuperPopUp(userId){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	document.getElementById("checkSuper"+userId).checked = false;
}
</script>

<script type="text/javascript">
function alertMessage(message,userId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='makeAsPrimaryAdmin("+userId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel("+userId+");'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}

function alertSuperAdminMessage(message,userId){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='makeAsSuperAdmin("+userId+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelSuperPopUp("+userId+");'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script>
function makeAsPrimaryAdmin(userId){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	
	var orgLocationId =   $('input[name=subscribedOrgLocation]:checked').val();
	 if(orgLocationId == null || orgLocationId == '' || orgLocationId == 'undefined'){
		 orgLocationId = '${registeredOrgLocationId}'
	 }
	var orgCode = '${orgCode}';
	
 	$("#loading").attr('style','position:absolute; width:100%; height:450px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != "" && orgLocationId != null && orgLocationId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/changePrimaryAdmin?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId, 
		        success: function(result) {
		        	cancel(userId);
		        	usersListDataTableList();
		        	$("#loading").removeAttr("style");
		        },
		    });
	 }
	 
}


function makeAsSuperAdmin(userId){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
	
	var orgLocationId =   $('input[name=subscribedOrgLocation]:checked').val();
	 if(orgLocationId == null || orgLocationId == '' || orgLocationId == 'undefined'){
		 orgLocationId = '${registeredOrgLocationId}'
	 }
	var orgCode = '${orgCode}';
	
 	$("#loading").attr('style','position:absolute; width:100%; height:450px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	var sourceleadUrl ='${sourceleadUrl}';
	if(userId != null && userId != "" && orgLocationId != null && orgLocationId != ""){
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/changeSuperAdmin?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgLocationId="+orgLocationId, 
		        success: function(result) {
		        	cancelSuperPopUp(userId);
		        	usersListDataTableList();
		        	$("#loading").removeAttr("style");
		        },
		    });
	 }
	 
}
</script>



<script type="text/javascript">
function resendInvitation(userId){
	var sourceleadUrl ='${sourceleadUrl}';
	var orgId = '${orgId}';
	var orgLocationId =   $('input[name=subscribedOrgLocation]:checked').val();
	 if(orgLocationId == null || orgLocationId == '' || orgLocationId == 'undefined'){
		 orgLocationId = '${registeredOrgLocationId}'
	 }
	if(userId != null && userId != ""){
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendInvitationEmail?${_csrf.parameterName}=${_csrf.token}&userId="+userId+"&orgId="+orgId+"&orgLocationId="+orgLocationId, 
			        success: function(result) {
			        	
			        	document.getElementById("mailSendSuccessMsg").style.display = 'block';
			         	$("#mailSendSuccessMsg").fadeIn(150);
			        	$('#mailSendSuccessMsg').delay(1000).fadeOut('slow'); 
			        },
			    });
		 }
	}
  
</script>


<script>
function pagination(){
    $("#example").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>


<script>
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
</script>			
			
<script type="text/javascript">
 function validateUserEmail() {
	 var emailId = document.getElementById("email").value; 
	 var orgLocationId = document.getElementById("registeredLocation").value;
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(emailId != null && emailId != "" && orgLocationId != "" && orgLocationId != null){
		 $("#add_user").attr("disabled", "disabled");
		 $.ajax({
		    	type: "POST",
		        url: sourceleadUrl+"/validateRegisteredUserEmail?${_csrf.parameterName}=${_csrf.token}&emailId="+emailId+"&orgLocationId="+orgLocationId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#userEmailDiv').find('p.jquery_form_error_message').remove();
						  $("input#email").after("<p class='jquery_form_error_message'>"+emailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',emailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#email').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#userEmailDiv').find('p.jquery_form_error_message').remove(); 
                         $("#add_user").removeAttr("disabled");
		        	}
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
         $('body').on('submit', '#adduser_form', function(e){
        	  if ($('#adduser_form').validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var formData = getFormData('adduser_form');
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		var registeredOrgLocationId = document.getElementById("registeredLocation").value;
	          		
	          		 $("#add_user").attr("disabled", "disabled");
	          		 $("#userLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
	          	      $.ajax({
	     		    	type: "POST",
	     		    	data : formData,
	     		    	 url: sourceleadUrl+"/createUesrBySourceleadAdmin?${_csrf.parameterName}=${_csrf.token}"+"&registeredOrgLocationId="+registeredOrgLocationId, 
	     		        success: function(data) {
	     		        	if(data != null && data != '' && data == 'success'){
	     		        		$("#userLoading").removeAttr("style");
	     	   	        		getUserList();
	     		        	}
	     			        
	     		        },
	     		    }); 
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         
         $('body').on('blur', '#adduser_form', function() {
     		$("#adduser_form").showHelpOnFocus();
     		$("#adduser_form").validateOnBlur(false, validationSettings);
     	});
      
</script> 



<script type="text/javascript">
 function getRegisteredOrgRoles() {
	 var sourceleadUrl ='${sourceleadUrl}';
	 var orgLocationId = document.getElementById("registeredLocation").value;
	 if(orgLocationId != null && orgLocationId != '' && orgLocationId != 'undefined'){
	 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getRegisteredOrgRoles?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId, 
		        success: function(json) {
		        	var output = "";
		        	output = output + "<option value=''>-- Select Role --</option>"; 
		        	for (var i=0; i<json.length; i++)
					{
			        		var opt = json[i];
			        		output = output + "<option value='" +opt.id+ "'>"+opt.displayName+"</option>";
					}
		        	$("#roleTypeId").empty();
			        $("#roleTypeId").append(output);
			        $('#roleTypeId').trigger("chosen:updated");
		        },
		    });
	 }
 }
 
 
 function getRegisteredOrgLocationRoles(orgLocationId){
	
	 if(orgLocationId != null && orgLocationId != '' && orgLocationId != 'undefined'){
		 $("#filteredOrgLocationId").val(orgLocationId);
	 }else{
		 orgLocationId =   $('input[name=subscribedOrgLocation]:checked').val();
		 if(orgLocationId == null || orgLocationId == '' || orgLocationId == 'undefined'){
			 orgLocationId = '${registeredOrgLocationId}'
		 }
	 }
	 
	 var sourceleadUrl ='${sourceleadUrl}';
	 if(orgLocationId != null && orgLocationId != '' && orgLocationId != 'undefined'){
		 $.ajax({
			    	type: "GET",
			        url: sourceleadUrl+"/getRegisteredOrgRoles?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId, 
			        success: function(json) {
			        	var output = "";
			        	for (var i=0; i<json.length; i++)
						{
				        		var opt = json[i];
				        		output = output + "<li class='radioIput positionRelative'><input type='checkbox' id='role"+opt.id+"' value='" +opt.id+ "'  name='subscribedOrgLocationRoles'><label>"+opt.displayName+"</label></li>";
						}
			        	$("#rolesFilterDiv").empty();
				        $("#rolesFilterDiv").append(output);
			        },
			    });
		 }
	 
 }
</script>



<script type="text/javascript">
	function flushUser() {
		$("#add_user").removeAttr("disabled");
		$('#firstName').val('');
		$('#lastName').val('');
		$('#email').val('');
		
		$('#roleTypeId').empty();
		$('#roleTypeId').append("<option value=''>-- Select Company --</option>");
		$('#roleTypeId').val('');
		$('#roleTypeId').trigger("chosen:updated");
		
		
		$('#subscribedCountry').empty();
		$('#subscribedCountry').trigger("chosen:updated");
		
		$("#registeredLocation").empty();
		$('#registeredLocation').append("<option value=''>-- Select Company --</option>");
		$("#registeredLocation").val()
		$('#registeredLocation').trigger("chosen:updated");
		
		
		$(".error").removeClass("error");
		$('#firstNameDiv').find('p.jquery_form_error_message').remove();
		$('#lastNameDiv').find('p.jquery_form_error_message').remove();
		$('#userEmailDiv').find('p.jquery_form_error_message').remove();
		$('#userRoleDiv').find('p.jquery_form_error_message').remove();
		$('#subscribedCountryDiv').find('p.jquery_form_error_message').remove();
		$('#companiesDiv').find('p.jquery_form_error_message').remove();
		
	}
</script>


<script>
function removeAddUserPopUp(){
	 document.getElementById("addNewUserPopup").removeAttribute("class")
	 document.getElementById('addNewUserPopup').className = 'collapse-div-adduser orgsummaryuser-pop orgsummary-adduser col-md-4'
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

});
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