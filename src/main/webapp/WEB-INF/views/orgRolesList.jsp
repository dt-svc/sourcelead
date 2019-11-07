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
          <div class="pull-right brud-crum"><a href="${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${SourceLeadWeb_sourceleadUrl}/settings">Settings</a>&raquo; Roles List </div>
        </section>

        
			<div class="project_wrap">
			
			
 <div class="reportHead reportHeadDataTab  clearfix">
      <ul class="pull-right list-unstyled list-inline">
			<li>
				<a class="action newAdBtn" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 
					  <sec:authorize access="hasAnyRole('ROLE_ENROLL_NEW_ROLE')"> 
					  	 <sec:authorize access="hasAnyRole('ROLE_ADD_TEAM')">
								<a href="${SourceLeadWeb_sourceleadUrl}/team">Add Team</a>
							</sec:authorize>
					  	 <a href="${SourceLeadWeb_sourceleadUrl}/addDepartment">Add Department</a>
					     <a href="${SourceLeadWeb_sourceleadUrl}/roleHierarchy">Add Hierarchy</a>
					     <a href="${SourceLeadWeb_sourceleadUrl}/addGrade">Add Grade</a>
		                 <a href="javascript:void(0)" class="addcon-pop">Add Role</a>
		              </sec:authorize>					
					</span>
				</div>
			</li>
        </ul>
				<!-- Popup Div -->
				<div class="popupWrap" id="addRolePop">
					<div class="popupHead">Add Role <i class="closePop pull-right popClose" onclick="flushRole();"></i></div>
					<form:form id="addRole_form"  name="addRole_form" method="post"> 
					<div class="popupContent">
					<div id="loading"></div> 
                         <div class="clearfix mb">
                           <div class="" id="roleDiv">
                           		<label class="noPad" for="Role Name">Role Name <b class="valStar">*</b></label>
                             <input type="text"  field-name="Role Name" onblur="validateRoleName();" name="roleName" id="roleName" class="form-control"  placeholder="Role Name" data-validation="required validate_length length2-50" field-name="Role Name" >
                           </div>
                         </div>
                         <div class="clearfix mb">
                           <div class="" id="levelDiv">
                           		<label class="noPad" for="Role Name">Level <b class="valStar">*</b></label>
                             <select class="form-control chosen-select" data-validation="required" field-name="Level" id="level" name="level">
                             	 <option value="">-Select Level-</option>
		                         <c:forEach var="option" items="${hierarchyList}">
									<option value="${option.id}">${option.hierarchyName}</option>
								 </c:forEach>
                             </select>
                           </div>
                         </div>					
					</div>
					<div class="popupFoot text-right">
						<button type="submit" id="add_role" class="slBtn md colorBtn">Add</button>					
						<input type="button" value="Cancel" class="slBtn md popClose">
					</div>
					</form:form>
				</div>
                        
				<!-- Popup Div -->          
        
      </div>			
		     
	    <div class="project_wrap_table datatable_filter dataSeaTop">
	    	<table id="rolesList" class="display" cellspacing="0" width="100%"></table>
		</div>	   
					
	   <div class="vendor_wrap_mask"></div>
	   <div id="updateRoleDiv"></div>			     
		     
		 </div>
		 
  
 </section>
 
 
 
 <jsp:include page="masterFooter.jsp" />	
 
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
	                    		   $(".mask").hide();
	                    		   $("#addRolePop").fadeOut(100);
	                    		}else if (data.indexOf('Sign In') !== -1) {
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
     		"sSearchPlaceholder":"Search",
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
   		    		result = result +'<a class="anchorWhite" href="${SourceLeadWeb_sourceleadUrl}/roleActions/'+report.uuid+'"><i class="fa fa-key"></i>&nbsp;&nbsp;Configure</a>';
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
	result = result + '	<div class="popupWrap settPop">';
	result = result + '	<div id="updateLoading"></div>';
	result = result + '<form id="updateRole_form" name="updateRole_form" method="post">';
	result = result + '<input type="hidden" name="roleId" value="'+roleId+'"  id="roleId">';
	result = result + '<div class="popupHead">Edit Role <i class="closePop popClose" onclick="cancelUpdate()"></i></div>';
	
	result = result + '	<div class="popupContent"> ';
	result = result + '		<div class="clearfix">  ';
	result = result + '		<div class="" id="editRoleDiv">  ';
	result = result + '		  <label class="noPad" for="Role Name">Role Name <b class="valStar">*</b></label> ';
	result = result + '			<input type="text" class="form-control" onblur="validateEditRoleName();" name="displayName" value="'+displayName+'" id="displayName" field-name="Role Name" data-validation="required  validate_length length2-50">';
	result = result + '		</div> ';

	result = result + '		  <label class="mt" for="Project Id">Level <b class="valStar">*</b></label> ';
	result = result + '		  <div class=" taskdesp">   ';
	result = result + '   <select class="form-control chosen-select" name="hierarchyId" id="hierarchyId" field-name="Level" data-validation="required">';
 	result = result + '     <option value="">--Level--</option>';
 	result = result +'		<c:forEach var="hierarchy" items="${hierarchyList}">';
 	result = result + '       <option value="${hierarchy.id}">${hierarchy.hierarchyName}</option>';
    result = result +'		</c:forEach>';
    result = result + '    </select>';
	
	result = result + '			<span class="hidden"></span> </div>  ';
	result = result + '		</div>';
	result = result + '	</div>  ';
	
	result = result + '	<div class="popupFoot text-right">';
	result = result + '	<input type="submit" value="Save" id="update_role"  onclick="updateRole();" class="slBtn md colorBtn" >';	
	result = result + '	<input type="button" value="Cancel" class="slBtn md popClose" onclick="cancelUpdate();">';
	result = result + '	</div>';
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
  
  $(".settPop").fadeIn(100);
	$(".mask").show();
}
</script>

<script>
function cancelUpdate(){
	$(".settPop").fadeOut(100);
	$(".mask").hide();
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
	 $(".mask").show();
	 $("#addRolePop").fadeIn(100);
 });
 $(".popClose").click(function(){
	 $(".mask").hide();
	 $("#addRolePop").fadeOut(100);
	 });
 
 function getActions(){
		$(".action-pop").slideToggle(200);
		$(".newActPop").slideUp(200);
	}	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
	});	
</script>