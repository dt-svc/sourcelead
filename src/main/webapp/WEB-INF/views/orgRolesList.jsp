<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
window.onload = function () {
	getRolesList();
 };
</script>


<style type="text/css">
.dataTables_wrapper .dataTables_filter input {
background-color: #f5f5f5;
}
.role_header {
padding-right: 254px; padding-top: 10px;
}
</style>
<section class="content-wraper">
<div id="listLoading"></div>
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Roles List</h2>
          </div>
        </section>

        
	   <div class="timesheet_out">
			<div class="project_wrap">
			
			<div class="project_tabs_inn_head">
			  <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')"> 
			  	 <div class="role_header">
			  	 <sec:authorize access="hasAnyRole('ROLE_ADD_TEAM')">
						<a href="<c:url value='${appPath}/team'/>"><i
								class="fa fa-plus"></i>Team</a>
					</sec:authorize>
			  	 <a href="<c:url value='${appPath}/addDepartment'/>"><i class="fa fa-plus"></i>Department</a>
			     <a href="<c:url value='${appPath}/roleHierarchy'/>"><i class="fa fa-plus"></i>Hierarchy</a>
			     <!-- <a href="javascript:void(0)" onClick="showGradePopUp();" ><i class="glyphicon glyphicon-plus"></i>Add Grade</a> -->
                 <a href="javascript:void(0)" class="addcon-pop"><i class="glyphicon glyphicon-plus"></i>Add Role</a>
                 </div>
              </sec:authorize>
              
				<!-- Popup Div -->
                    <div class="collapse-div-adduser addrole col-md-4"> <i class="fa fa-close close-div" onclick="flushRole();"></i>
                          <div class="form-horizontal clearfix">
                          <div id="loading"></div> 
                        <form:form id="addRole_form"  name="addRole_form" method="post">                             
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="roleDiv">
                                <input type="text" data-validation="required" field-name="Role Name" onblur="validateRoleName();" name="roleName" id="roleName" class="form-control input-sm"  placeholder="Role Name">
                              </div>
                            </div>
                            <div class="clearfix mb10">
                              <div class="col-md-12" id="levelDiv">
                                <select class="form-control chosen-select" data-validation="required" field-name="Level" id="level" name="level">
                                	 <option value="">-Select Level-</option>
			                         <c:forEach var="option" items="${hierarchyList}">
										<option value="${option.id}">${option.hierarchyName}</option>
									 </c:forEach>
                                </select>
                              </div>
                            </div>
                            <div class="clearfix mr">
                              <button type="submit" id="add_role" class="btn btn-sm btn-primary pull-right">Add</button>
                            </div>
                          </form:form>  
                            
                          </div>
                        </div>
                        
				<!-- Popup Div -->              
			</div>

     </div>
 </div>
			    <div class="project_wrap_table datatable_filter">
			    				<table id="rolesList" class="display" cellspacing="0" width="100%">
								</table>
				</div>	   
					
	   <div class="vendor_wrap_mask"></div>
	   <div id="updateRoleDiv"></div>	
	   
	   <div class="comMask"></div>
  <div class="modal paymentaccount-modal" id="gradePopup" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onclick="closeGradePopUp();" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Add/Edit Grade</h4>
        </div>
        <div class="modal-body">
        	<form:form class="clearfix"  id="addgrade_form"  name="addgrade_form" method="POST">
	      		<div class="form-group clearfix minHeight40">
		           <label class="col-md-4">Grade<span class="text-danger">&nbsp;*</span></label>
		           <div class="col-md-8">
		                 <input type="text" id="grade" name="grade" class="form-control" data-required="required"/>
		           </div>
	           </div>
	           
	           <div class="form-group clearfix minHeight40">
		           <label class="col-md-4">Designation</label>
		           <div class="col-md-8">
		                 <input type="text" id="designation" name="designation" class="form-control" data-required="required"/>
		           </div>
	           </div>
	           
	           <div class="form-group clearfix minHeight40">
		           <label class="col-md-4">Min Wage/Salary</label>
		           
		          <div class="col-md-2 padding_right dropdown-select drpdownCurrency">
						<select id="minSalRateCurrency" name="minSalRateCurrency">
						<option ${SourceLeadWeb_currencyCode  == 'INR' ? 'selected' : ''} value="INR">INR</option>
						<option ${SourceLeadWeb_currencyCode  == 'AUD' ? 'selected' : ''} value="AUD">AUD</option>
						<option ${SourceLeadWeb_currencyCode  == 'USD' ? 'selected' : ''} value="USD">USD</option>
						<option ${SourceLeadWeb_currencyCode  == 'GBP' ? 'selected' : ''} value="GBP">GBP</option>
						<option ${SourceLeadWeb_currencyCode  == 'SGD' ? 'selected' : ''} value="SGD">SGD</option>
						<option ${SourceLeadWeb_currencyCode  == 'EUR' ? 'selected' : ''} value="EUR">EUR</option>
						</select>
				</div>
	
		          <div class="col-md-3 padding_right padding_left inputRate">
		                 <input type="text" id="minSalary" name="minSalary" class="form-control" data-validation-optional="true"/>
		           </div>
		           
		          <div class="col-md-3 padding_left drpdownMonthly dropdown-select">
					<select id="minSalRateType" name="minSalRateType">
					<option value="Hourly">Hourly</option>
					<option value="Weekly">Weekly</option>
					<option value="Bi-weekly">Bi-weekly</option>
					<option value="Semi-Monthly">Semi-Monthly</option>
					<option value="Monthly">Monthly</option>
					<option value="Bi-Monthly">Bi-Monthly</option>
					<option value="Yearly">Yearly</option>
					</select>
				</div>
			</div>
			
			
			<div class="form-group clearfix minHeight40">
		           <label class="col-md-4">Mid Wage/Salary</label>
		           
		          <div class="col-md-2 padding_right dropdown-select drpdownCurrency">
					<select id="midSalRateCurrency" name="midSalRateCurrency">
						<option ${SourceLeadWeb_currencyCode  == 'INR' ? 'selected' : ''} value="INR">INR</option>
						<option ${SourceLeadWeb_currencyCode  == 'AUD' ? 'selected' : ''} value="AUD">AUD</option>
						<option ${SourceLeadWeb_currencyCode  == 'USD' ? 'selected' : ''} value="USD">USD</option>
						<option ${SourceLeadWeb_currencyCode  == 'GBP' ? 'selected' : ''} value="GBP">GBP</option>
						<option ${SourceLeadWeb_currencyCode  == 'SGD' ? 'selected' : ''} value="SGD">SGD</option>
						<option ${SourceLeadWeb_currencyCode  == 'EUR' ? 'selected' : ''} value="EUR">EUR</option>
					</select>
				</div>
	
		          <div class="col-md-3 padding_right padding_left inputRate">
		                 <input type="text" id="midSalary" name="midSalary" class="form-control" data-validation-optional="true"/>
		           </div>
		           
		          <div class="col-md-3 padding_left drpdownMonthly dropdown-select">
					<select id="midSalRateType" name="midSalRateType">
					<option value="Hourly">Hourly</option>
					<option value="Weekly">Weekly</option>
					<option value="Bi-weekly">Bi-weekly</option>
					<option value="Semi-Monthly">Semi-Monthly</option>
					<option value="Monthly">Monthly</option>
					<option value="Bi-Monthly">Bi-Monthly</option>
					<option value="Yearly">Yearly</option>
					</select>
				</div>
			</div>
			
			
			<div class="form-group clearfix minHeight40">
		           <label class="col-md-4">Max Wage/Salary</label>
		           
		          <div class="col-md-2 padding_right dropdown-select drpdownCurrency">
					<select id="maxSalRateCurrency" name="maxSalRateCurrency">
						<option ${SourceLeadWeb_currencyCode  == 'INR' ? 'selected' : ''} value="INR">INR</option>
						<option ${SourceLeadWeb_currencyCode  == 'AUD' ? 'selected' : ''} value="AUD">AUD</option>
						<option ${SourceLeadWeb_currencyCode  == 'USD' ? 'selected' : ''} value="USD">USD</option>
						<option ${SourceLeadWeb_currencyCode  == 'GBP' ? 'selected' : ''} value="GBP">GBP</option>
						<option ${SourceLeadWeb_currencyCode  == 'SGD' ? 'selected' : ''} value="SGD">SGD</option>
						<option ${SourceLeadWeb_currencyCode  == 'EUR' ? 'selected' : ''} value="EUR">EUR</option>
					</select>
				</div>
	
		          <div class="col-md-3 padding_right padding_left inputRate">
		                 <input type="text" id="maxSalary" name="maxSalary" class="form-control" data-validation-optional="true"/>
		           </div>
		           
		          <div class="col-md-3 padding_left drpdownMonthly dropdown-select">
					<select id="maxSalRateType" name="maxSalRateType">
					<option value="Hourly">Hourly</option>
					<option value="Weekly">Weekly</option>
					<option value="Bi-weekly">Bi-weekly</option>
					<option value="Semi-Monthly">Semi-Monthly</option>
					<option value="Monthly">Monthly</option>
					<option value="Bi-Monthly">Bi-Monthly</option>
					<option value="Yearly">Yearly</option>
					</select>
				</div>
			</div>
			
			</form:form>
		</div>
			<div class="clearfix"></div>
	        <div class="modal-footer">
	         <button type="button" class="btn btn-danger"  onclick="closeGradePopUp();" >Cancel</button>
	          <button type="button" class="btn btn-primary" id="saveGradeButton" onclick="saveGrade();" >Save</button>
	        </div>
         </div>
        	
        </div>
        </div>
 </section>
 
 
 
 <jsp:include page="masterFooter.jsp" />	
 
 <script>
 function showGradePopUp() {
	    $(".modal.paymentaccount-modal").fadeIn();
	    $(".comMask").fadeIn();
	}
 
 function closeGradePopUp() {
	    $(".modal.paymentaccount-modal").fadeOut();
	    $(".comMask").fadeOut();
	}
 </script>
 
 <script type="text/javascript">
function validateRoleName(){
	var roleName = document.getElementById("roleName").value.trim();
	var sourceleadUrl = '${sourceleadUrl}';
	if(roleName.match(/^[^\s-](.*)*$/) !== null) {
		$.ajax({
			type : "GET",
			url: sourceleadUrl+"/validateRoleName?${_csrf.parameterName}=${_csrf.token}&roleName="+roleName, 
			success : function(data) {
				var result = data;
				if (result == "invalid") {
			        $("#add_role").attr("disabled", "disabled");
					$("input#roleName").after( "<p class='jquery_form_error_message'>" + roleName + " is already existed</span>");
					document.getElementById("roleName").setAttribute( 'record-exist', 'yes');
					document.getElementById("roleName").setAttribute( 'record-exist-errorMsg', roleName +"  already existed");
					$('input#roleName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else{
					$("#add_role").removeAttr("disabled");
					$('input#roleName').removeAttr("class record-exist record-exist-errormsg");
					$('input#roleName').attr('class','valid form-control');
					$('#roleDiv').find('p.jquery_form_error_message').remove();
					$("#add_role").click(function() {
						
					});
				}
			},

		});
	}
	}
</script>



<script>
function validateEditRoleName(){
	var displayName = document.getElementById("displayName").value;
	var roleId = document.getElementById("roleId").value;
	var sourceleadUrl = '${sourceleadUrl}';
	if(displayName != null && displayName != '') {
		$.ajax({
			type : "GET",
			url: sourceleadUrl+"/validateExistingRoleName?${_csrf.parameterName}=${_csrf.token}&roleName="+displayName+"&roleId="+roleId, 
			success : function(data) {
				var result = data;
				if (result == "invalid") {
			        $("#update_role").attr("disabled", "disabled");
					$("input#displayName").after( "<p class='jquery_form_error_message'>" + displayName + " is already existed</span>");
					document.getElementById("displayName").setAttribute( 'record-exist', 'yes');
					document.getElementById("displayName").setAttribute( 'record-exist-errorMsg', displayName +"  already existed");
					$('input#displayName').removeAttr( "record-exist record-exist-errorMsg");
					
				}else{
					$("#update_role").removeAttr("disabled");
					$('input#displayName').removeAttr("class record-exist record-exist-errormsg");
					$('input#displayName').attr('class','valid form-control');
					$('#editRoleDiv').find('p.jquery_form_error_message').remove();
					$("#update_role").click(function() {
						
					});
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
         $('body').on('submit', '#updateRole_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var formData = getFormData("updateRole_form");
	          		var sourceleadUrl ='${sourceleadUrl}';
	          		
	          		 $("#updateRole_form").attr("disabled", "disabled");
	          		$("#updateLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          		
	        		
	           		 $.ajax({
	          	      type: "POST",
	          	    	data: formData,
	                	  url: sourceleadUrl+"/updateOrgLocationRole?${_csrf.parameterName}=${_csrf.token}",
	                  	success: function(result) {
				         	if(result == 'success'){
				         		 location.replace(sourceleadUrl+ "/orgRolesList");
				         	}
				        },  
	               });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#updateRole_form').showHelpOnFocus();
         $('#updateRole_form').validateOnBlur(false, validationSettings); 
            
     
</script> 


<script type="text/javascript">
	function flushRole() {
		$("#add_role").removeAttr("disabled");
		$('#roleName').val('');
		$('#level').val('');
		$(".error").removeClass("error");
		$('#roleDiv').find('p.jquery_form_error_message').remove();
		$('#levelDiv').find('p.jquery_form_error_message').remove();
		
	}
</script>
 
 
 <script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#addRole_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		    e.preventDefault(); 
	          		
        		    var formData = getFormData1("addRole_form");
        		    
        			var sourceleadUrl ='${sourceleadUrl}';
	          		
	          		 $("#add_role").attr("disabled", "disabled");
	          		$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	          	     $.ajax({
	     		    	type: "POST",
	     		    	data: formData,
	     		    	processData: false, 
	    		 		contentType: false,
	     		    	 url: sourceleadUrl+"/addNewRole?${_csrf.parameterName}=${_csrf.token}", 
	     		    			 success: function(data) {
	     		        	if (data && (data === "INVALID_DATA" || data === "INVALID_USER")) {
	     		        		showError(data);
	          	    		    $("#loading").removeAttr('style');
	          	    		}
	                    		else if (data === "AWKWARD") {
	                    			location.href = "/errorPage";
	          	    		}
	                    		else if(data != 'INVALID_DATA' && data != 'AWKWARD'){
	                    			$("#loading").removeAttr('style');
	                    		   location.replace(sourceleadUrl+ "/roleActions/"+data+"");  
	    			        	    }
	                    		else if (data.indexOf('Sign In') !== -1) {
	                    			location.replace(sourceleadUrl);
	          				} else {
	          					showError("Operation failed, please try again");
	          				    $("#loading").removeAttr('style');
	          				}
	     		        	
	     			        
	     		        },
	     		    });
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
         $('#addRole_form').showHelpOnFocus();
         $('#addRole_form').validateOnBlur(false, validationSettings); 
            
     
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
 
 <script type="text/javascript">
        var validationSettings = {
            errorMessagePosition : 'element',
            borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd' 
            };

        
</script> 
 <script>
function rolepagination(){ 
	$('#rolePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script> 
 

<script>
function getRolesList(){
	
	var sourceleadUrl ='${sourceleadUrl}';
	$("#listLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getOrgEnrolledList?${_csrf.parameterName}=${_csrf.token}", 	   
   		    success: function(json) {
   	        	var result = "";
   	         	result = result +"<table id='rolePagination' class='display' cellspacing='0' width='100%' >"; 
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Role Name</th>';
   		    	result = result +'    <th>Level</th>'; 
   		    	result = result +'     <th>Number Of Actions</th>';
   		    	result = result +'     <th>Action</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.length; i++)
   				{   
   		    		var report = json[i];
   		    		
   		    		result = result +'<tr>';
   		    		
   		    		result = result +'<td align="center"><div class="proj_show" onclick="getRoleDeatils(\''+report.displayName+'\','+report.hierarchyId+','+report.id+')"><span  title="'+report.displayName+'">'+report.displayName+'</span><i class="fa fa-edit"></i></div></a></td>';
   		    		result = result +'<td align="center"><div class="tbldata-dotdot-sm" title="'+report.hierarchyName+'">'+report.hierarchyName+'</div></td>';
   		    		result = result +'<td align="right"><div class="tbldata-dotdot-vsm" title="'+report.actionsCount+'">'+report.actionsCount+'</div></td>';
   		    		result = result +'<td>';
   		    		result = result +'<div>';
   		    		result = result +'<label class="label label-success">';
   		    		result = result +'<a class="anchorWhite" href="<c:url value="/app/roleActions/'+report.id+'"/>""><i class="fa fa-key"></i>&nbsp;&nbsp;Configure</a>';
   		    		result = result +'</label>';
   		    		result = result +'</div>';
   		    		result = result +'</td>';
   		    		
                    result = result +'</tr>';
   				}
                result = result +'  </tbody>';
                result = result +'</div>';
                $("#rolesList").empty();  
                $("#listLoading").removeAttr("style");
   	    	    $("#rolesList").append(result);
   	    	 	rolepagination();
   		    }
	 }); 
	
	
}	
</script> 

<script>
function getRoleDeatils(displayName,roleHierarchyId,roleId){
	
	var result = "";
	result = result + '	<div class="settPop">';
	result = result + '	<div id="updateLoading"></div>';
	result = result + '<form id="updateRole_form" name="updateRole_form" method="post">';
	result = result + '<input type="hidden" name="roleId" value="'+roleId+'"  id="roleId">';
	result = result + '	<h3>Edit Role<i class="fa fa-times" onclick="cancelUpdate();"></i></h3>  ';
	result = result + '	<div class="settPopInn"> ';
	
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="col-md-12 errorInptgroup" id="editRoleDiv">  ';
	result = result + '		  <label class="col-md-2 noPad" for="Role Name">Role Name :</label> ';
	result = result + '			<input type="text" class="form-control col-md-8 paddingRight" onblur="validateEditRoleName();" name="displayName" value="'+displayName+'" id="displayName" field-name="Role Name" data-validation="required  validate_length length1-100">';
	result = result + '		</div>  ';

	result = result + '		  <label class="col-md-12 mt" for="Project Id">Level</label> ';
	result = result + '		  <div class="col-md-12 taskdesp">   ';
	
	result = result + '   <select class="form-control chosen-select" name="hierarchyId" id="hierarchyId" field-name="Level" data-validation="required">';
 	result = result + '     <option value="">--Level--</option>';
 	result = result +'		<c:forEach var="hierarchy" items="${hierarchyList}">';
 	result = result + '       <option value="${hierarchy.id}">${hierarchy.hierarchyName}</option>';
    result = result +'		</c:forEach>';
    result = result + '    </select>';
	
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '		<div class="col-md-12 mt5 clearfix"> ';
	result = result + '		<div class="pull-right">  ';
	result = result + '		  <input type="submit" value="Save" id="update_role"  onclick="updateRole();" class="btn btn-primary" >';
	result = result + '		  <input type="button" value="Cancel" class="btn btn-default ml10" onclick="cancelUpdate();">';
	result = result + '		</div>';
	result = result + '		</div>';
	result = result + '		<div class="clearfix"></div>   ';
	result = result + '	</div>  ';
	result = result + '</form>';
	result = result + '	</div>';
    $("#updateRoleDiv").empty();
    $("#updateRoleDiv").append(result);
    
   
	
    var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
  
  $("select[name=hierarchyId]").val(roleHierarchyId);
  $("#hierarchyId").chosen();
  $("#hierarchyId").trigger("chosen:updated");
  
  $(".settPop").slideDown(200);
	$(".vendor_wrap_mask").fadeIn(200);
}
</script>

<script>
function cancelUpdate(){
	$(".settPop").slideUp(200);
	$(".vendor_wrap_mask").fadeOut(200);
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