<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.fa-plus-circle.fa-2x {
font-size: 1.5em;
}
</style>


<script type="text/javascript">
$(document).ready(function(){
	getDepartmentList();
});
</script>

<script>
$(document).ready(function() {
	$("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
		$("#filter1ApprovedPop").slideUp(200);
		$("#department_form").find('.collapse-div-adduser').removeClass("show-div");
	});
	$("html").click(function() {
	    $("#filter2ApprovedPop").slideUp(200);
	});
	$("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
	    e.stopPropagation();
	});
	
	
}); 
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Department List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>"> Home </a>&raquo;<a href="${appPath}/orgRolesList"> Roles List </a> &raquo;  Department List </div>
    </section>


<div class="timesheet_out">
      <div class="vendor_wrap">
        
<div class="reportHead reportHeadDataTab clearfix">
      <ul class="pull-right list-unstyled list-inline">
      
			<li>
				<a href="javascript:void(0)" class="action newAdBtn addcon-pop" title="" onclick="flushDepartment();">+ Department</a>
            </li>      
	       
            <li id="filter2ApprovePop"><a href="javascript:void(0)" class="filtIcon" title="Filters"></a>
               	<div id="filter2ApprovedPop" class="approved_pop">
                   	<ul>
                       	<li><input id="c5" type="radio" name="departmentStatus" value ="1" checked onclick="getDepartmentList();"  ><label for="c5">Active</label></li>
						<li><input id="c6" type="radio" name="departmentStatus" value ="0"  onclick="getDepartmentList();" ><label for="c6" title="Remove Departments">Remove Departments</label></li>
                    </ul>
                </div>                	
            </li>	       
        </ul>
      </div>        
        
        <div class="candidate_wrap_table">
          <div id="departmentresultId" class="datatable_filter dataSeaTop"></div>
        </div>
        
        <div class="vendor_wrap_mask"></div>
		<div id="editDepartmentId"></div>
         
      </div>
    </div>
		<div id="popUp"></div>
			<form:form id="department_form" name="department_form" method="post">
				<div class="popupWrap" id="departmentCloseDiv">
				<div class="popupHead">Add Department <i class="closePop pull-right popClose" onclick="flushRole();"></i></div>
				<div  id="departmentDivLoading"></div>
                    <div class="popupContent">
                      <div class="clearfix mb">
                        <div class="" id="departmentNameDiv">
                        	<label class="noPad">Department Name <b class="valStar">*</b></label>
                          <input type="text" class="form-control input-sm" value="" placeholder="Name *" name="departmentName" id="departmentName" data-validation="required validate_length length2-100" field-name="Name" onkeyup="validDepartmentname();" onkeydown="return (event.keyCode!=13);">
                        </div>
                      </div>
                      <div class="clearfix mb">
                        <div class="">
                        <label class="noPad">Description</label>
                          <textarea rows="2" placeholder="Description" class="form-control" name="departmentDesc" id="departmentDesc" data-validation="validate_length length2-250"
									data-validation-optional="true"></textarea>
                        </div>
                      </div>
                    </div>
                    
					<div class="popupFoot text-right">
						<button type="button" class="slBtn md colorBtn" onclick="saveDepartment();">Add</button>
						<input type="button" value="Cancel" class="slBtn md popClose">
					</div>                    
				</div>
            </form:form>		

  </section>
  <!-- Content Area Ends-->
 
 <script>
 function getDepartmentList(){
		  var candFilterRadio = $('input[name=departmentStatus]');
		  var status = candFilterRadio.filter(':checked').val(); 
		
	 var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "GET",
			url : sourceleadUrl+ "/getDepartmentList?${_csrf.parameterName}=${_csrf.token}&departmentStatus="+status,
			success : function(json) {
// 				var json = JSON.parse(data);
   	        	var result = "";
   	        	result = result +'<table id="departmentPagination">';
   		    	result = result +'<thead>';
   		    	result = result +'	<tr>';
   		    	result = result +'	<th>Department Name</th>';
   		    	result = result +'    <th>Description</th>'; 
   		    	result = result +'     <th>Action</th>';
   		    	result = result +'    </tr>';
   		    	result = result +'</thead>	';
   		    	result = result +'<tbody>';
   		    	for (var i=0; i<json.data.length; i++)
   				{
   		    		var report = json.data[i];
   		    		if(report.description != null && report.description !='' && report.description != 'undefined'){
   		    			description = report.description
   		    		}else{
   		    			description = "---";
   		    		}
   		    		
   		    		result = result +'<tr>';
   		    		result = result +'<td>'
   		    		result = result +'<div class="tbldata-dotdot-big" title="'+report.departmentName+'">'+report.departmentName+'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
   		    		result = result +'<div class="tbldata-dotdot-big" title="'+description+'" >'+description+'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'<td>'
   		    		result = result +'  <div class="tbldata-dotdot-100 text-center">';
   		    		
   		    		if(report.status == 1){
   		    			result = result +'<a class="glyphicon glyphicon-edit" title="Edit" onclick="editDepartment('+report.id+');"></a>';	
   		    		}
   		    		if(report.status == 1){
   		    			result = result +'<a class="glyphicon glyphicon-trash text-danger ml" title="Delete" onclick="deleteProject('+report.id+');"></a>';	
   		    		}else{
   		    			result = result +'<a class="glyphicon ml re-Assign" title="Add Department List" onclick="editDepartment('+report.id+');"><i class="fa-2x fa fa-reply" aria-hidden="true"></i></a>';
   		    		}
   		    		
   		    		
   		    		result = result +'</div>';
   		    		result = result +'</td>';
   		    		
   		    		result = result +'</tr>';
   		    		
   				}
   		    	
   		    	$("#departmentresultId").empty();  
   	    	    $("#departmentresultId").append(result);
   	    	    departmentPagination();
			},
		});
 }
 </script>
 
 <script>
function departmentPagination(){  
    $('#departmentPagination').DataTable( { 
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
	  $(".addcon-pop").click(function(){
			 $("#departmentCloseDiv").fadeIn(100);
			 $(".mask").show();
		 });
		 $(".popClose").click(function(){
			 $("#departmentCloseDiv").fadeOut(100);
			 $(".mask").hide();
			 });
  </script>

<script>
function saveDepartment(){
	if ($('#department_form').validate(false, validationSettings)) {
		var sourceleadUrl = '${sourceleadUrl}';
		var formData = getFormData1("department_form");
		$("#departmentDivLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		
	 	$.ajax({
			type: "POST",
			data: formData,
		    processData: false, 
	 		contentType: false,
			url : sourceleadUrl+ "/storeDepartment?${_csrf.parameterName}=${_csrf.token}",
			success : function(data) {
				if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
		        		showError(data.responseMessage);
  	    		    $("#hierarchyLoading").removeAttr('style');
  	    		}
            		else if (data.response === "AWKWARD") {
            			location.href = "/errorPage";
  	    		}
            		else if(data.response === "SUCCESS"){
            			 $("#departmentCloseDiv").toggleClass('show-div');
        				 location.reload();
		        	    }
            		else if (data.indexOf('Sign In') !== -1) {
            			location.replace(sourceleadUrl);
  				} else {
  					showError("Operation failed, please try again");
  				    $("#hierarchyLoading").removeAttr('style');
  				}
				
				
			},
		}); 
		return true;
	}else {
		return false;
	}
}
$('body').on('blur', '#department_form', function() {
	$("#department_form").showHelpOnFocus();
	$("#department_form").validateOnBlur(false, validationSettings);
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
<script>
function flushDepartment(){
	 $("#departmentName").val('');
	 $("#departmentDesc").val('');
	 $("department_form").validate().resetForm();

}
</script>
<script>
function editDepartment(departmentId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/getEditDepartment?${_csrf.parameterName}=${_csrf.token}&departmentId="+ departmentId,
		success : function(data) {
			var json = JSON.parse(data);
			 var result = "";
			 if(json.description != null && json.description != '' && json.description != 'undefined'){
				 description = json.description;
			 }else{
				 description = "";
			 }
				result = result + '	<div class="settPop popupWrap popupWrapFixed">';
				
				result = result + '<form id="editDepartment_form" name="editDepartment_form">';
				if(json.status == 1){
					result = result + '	<div class="popupHead">Edit Department <i class="closePop popClose" onclick="editCancel()"></i></div>  ';
				}else{
					result = result + '	<div class="popupHead">Add Department <i class="closePop popClose" onclick="editCancel()"></i></div>';
				}
				
				result = result + '	<div class="popupContent"> ';
				result = result + '		<div class="mb"> ';
				result = result + '		  <label for="Project Id">Department Name <b class="valStar">*</b></label> ';
				result = result + '		  <div class="" id="eidtdepartmentNameDIV"> ';
				result = result + '			<input type="text" class="form-control" id="eidtdepartmentName" name="departmentName"  value="'+json.departmentName+'" data-validation="required validate_length length2-100" field-name="Department Name" onkeyup="validateDepartmentname('+departmentId+');" onkeydown="return (event.keyCode!=13);"> ';
				result = result + '		   </div>';
				result = result + '		</div>  ';
				result = result + '		<div class="mb"> ';
				result = result + '		  <label for="Project Id">Description</label> ';
				result = result + '		  <div class="taskdesp">   ';
				result = result + '			<textarea class="form-control" name="departmentDesc"  value="'+description+'" colspan="5">'+description+'</textarea>';
				result = result + '			<span class="hidden">Enter Project Id</span> </div>  ';
				result = result + '		</div>   ';
				result = result + '		<div class="clearfix"></div>';
				result = result + '		</div>';
				
				
				result = result + '	<div class="popupFoot text-right">';
				if(json.status == 1){
					result = result + '		  <input type="button" value="Save" class="slBtn md colorBtn" onclick="saveEditDepartment('+departmentId+');">';	
				}else{
					result = result + '		  <input type="button" value="Add" class="slBtn md colorBtn" onclick="updateDepartment('+departmentId+');">';
				}
				
				result = result + '		  <input type="button" value="Cancel" class="slBtn md popClose" onclick="editCancel();">';
				result = result + '		</div>';
				
				
				result = result + '</form>';
				result = result + '	</div>';
			    $("#editDepartmentId").empty();
			    $("#editDepartmentId").append(result);
			    
				$(".settPop").fadeIn(100);
				$(".mask").show(); 
		},
	});
	
}

</script>
<script>
function editCancel(){
	$(".settPop").fadeOut(200);
	$(".mask").hide();
}
</script>

<script>
function saveEditDepartment(departmentId){
	if ($('#editDepartment_form').validate(false, validationSettings)) {
		
		var sourceleadUrl = '${sourceleadUrl}';
		var formData = getFormData1("editDepartment_form");
		$.ajax({
			type : "POST",
			data: formData,
		    processData: false, 
	 		contentType: false,
	 		url : sourceleadUrl+ "/updateDepartment?${_csrf.parameterName}=${_csrf.token}&departmentId="+departmentId,
			success : function(data) {
				
				if (data && (data.response === "INVALID_DATA" || data.response === "INVALID_USER")) {
	        		showError(data.responseMessage);
	    		}
        		else if (data.response === "AWKWARD") {
        			location.href = "/errorPage";
	    		}
        		else if(data.response === "SUCCESS"){
        			cancelTermination();
    				location.reload();
	        	    }
        		else if (data.indexOf('Sign In') !== -1) {
        			location.replace(sourceleadUrl);
				} else {
					showError("Operation failed, please try again");
				}
				
				
			},
		});
		return true;
		
	}else {
		return false;
	}
}
$('body').on('blur', '#editDepartment_form', function() {
	$("#editDepartment_form").showHelpOnFocus();
	$("#editDepartment_form").validateOnBlur(false, validationSettings);
});
</script>

<script>
function updateDepartment(departmentId){
	if ($('#editDepartment_form').validate(false, validationSettings)) {
		var frm = $('#editDepartment_form').serializeFormJSON();
		var con = JSON.stringify(frm);
		con = con.replace(/[{}]/g, "");
		var jsonData = encodeURIComponent(con);
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
			type : "GET",
			url : sourceleadUrl+ "/updateDelateDepartment?${_csrf.parameterName}=${_csrf.token}&json="+ jsonData+"&departmentId="+departmentId,
			success : function(result) {
				cancelTermination();
				location.reload();
			},
		});
		return true;
		
	}else {
		return false;
	}
}
$('body').on('blur', '#editDepartment_form', function() {
	$("#editDepartment_form").showHelpOnFocus();
	$("#editDepartment_form").validateOnBlur(false, validationSettings);
});
</script>



<script>
function deleteProject(departmentId){ 
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>"; 
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Do you want to delete this department ?</p>";
	result = result + ' <div id="reasonForProjectDiv"/>';
	result = result +'		<b href="javascript:void(0)"><input id="confirm-id" type="button" value="Delete" onclick="deleteDepartment('+departmentId+')"><input class="u_can_color" type="button"  value="Cancel" onClick="cancelTermination();"></b>';
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}

function cancelTermination(){
	$("#popUp").empty();
	 $(".project_inn_mask").fadeOut(200);
}

function deleteDepartment(departmentId){
	var sourceleadUrl = '${sourceleadUrl}';
			 $.ajax({
				   	type: "GET",
				    url: sourceleadUrl+"/saveDepartmentReason?${_csrf.parameterName}=${_csrf.token}&departmentId="+departmentId,
		   		    success: function(data) {
		   		    	$("#popUp").empty();
		   			 	$(".project_inn_mask").fadeOut(200);
		   			 location.reload();
		   		    }
			     });
	
}
</script>


<script>
function validateDepartmentname(departmentId){
	var DepartmentName = document.getElementById("eidtdepartmentName").value.trim();
	var sourceleadUrl = '${sourceleadUrl}';
	$('#eidtdepartmentNameDIV').find('p.jquery_form_error_message').remove();
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/validateDepartmentname?${_csrf.parameterName}=${_csrf.token}&departmentName="+DepartmentName+"&departmentId="+departmentId,
		success : function(data) {
			 if (data == true ) {
				$("input#eidtdepartmentName").after("<p class='jquery_form_error_message'>"+ DepartmentName+ " is Already Existed</span>");
				document.getElementById("eidtdepartmentName").setAttribute('record-exist', 'yes');
				document.getElementById("eidtdepartmentName").setAttribute('record-exist-errorMsg',DepartmentName+ ' is Already Existed');
				//$('input#eidtdepartmentName').removeAttr( "record-exist record-exist-errormessage");
			} else {
				$('input#eidtdepartmentName').removeAttr("record-exist record-exist-errormsg");
			} 
		},
	});
}
</script>



<script>
function validDepartmentname(){
	var DepartmentName = document.getElementById("departmentName").value.trim();
	var sourceleadUrl = '${sourceleadUrl}';
	$('#departmentNameDiv').find('p.jquery_form_error_message').remove();
	$.ajax({
		type : "GET",
		url : sourceleadUrl+ "/validateDepartmentname?${_csrf.parameterName}=${_csrf.token}&departmentName="+DepartmentName,
		success : function(data) {
			 if (data == true) {
				$("input#departmentName").after("<p class='jquery_form_error_message'>"+ DepartmentName+ " is Already Existed</span>");
				document.getElementById("departmentName").setAttribute('record-exist', 'yes');
				document.getElementById("departmentName").setAttribute('record-exist-errorMsg',DepartmentName+ ' is Already Existed');
			} else {
				$('input#departmentName').removeAttr("record-exist record-exist-errormsg");
			} 
		},
	});
}
</script>



 <jsp:include page="masterFooter.jsp" />	
 
  