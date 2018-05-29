<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content Area -->
<script>
$(document).ready(function(){
   getMappedUserList();
});
</script>

<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>User Mapping</h2>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo; User Mapping</div>
</section>
	<div id="loading"></div>
	<div id="popUp"></div>
<form:form id="userMapping_form" name="userMapping_form" method="post">

<div class="content-area content-new clearfix">
            <div class="clearfix mt col-md-12 mb">
             <div class="col-md-5 dropdown-select">
               <label class="col-md-3">Imported Users<span class="text-danger paddingLeft">*</span></label>
               <div class="col-md-9" id="errImportedUserIdDiv">
               <select  class="chosen-select" class="form-control" id="importedUserId" name="importedUserId" field-name="Imported User" data-validation="required" onchange="removeImportValidation();">
                <option value="" selected>-Select User-</option>
	            <c:forEach var="masterUsers" items="${masterUsersList}">
					<option value="${masterUsers.employeeId}">${masterUsers.employeeName}</option>
				</c:forEach>
                 <!-- <option value="Select Month">---Select User---</option>
                 <option value="January">Type 1</option>
                 <option value="February">Type 2</option>
                 <option value="March">Type 3</option>
                 <option value="April">Type 4</option> -->
               </select>
               </div>
             </div>
             <div class="col-md-5">
             <label class="col-md-3">Org Users<span class="text-danger paddingLeft">*</span></label>
               <div class="col-md-9" id="errOrgUserIdDiv">
               <select class="chosen-select" data-placeholder="Select Org User" id="slUserId" name="slUserId" field-name="Org User" data-validation="required" onchange="removeOrgValidation();">
                <option value="" selected>-Select Org User-</option>
	            <c:forEach var="users" items="${usersList}">
					<option value="${users.id}">${users.firstName} ${users.lastName}</option>
				</c:forEach>
                 <!-- <option value="Select Month">---Select Org Users---</option>
                 <option value="January">Type 1</option>
                 <option value="February">Type 2</option>
                 <option value="March">Type 3</option>
                 <option value="April">Type 4</option> -->
               </select>
               </div>
             </div>
          <div class="col-md-2">
          <button type="button" class="btn  btn-primary" onclick="mergeUser();" title="Merge User">Merge User</button>
          </div>
          </div>
      <div class="mt mb clearfix"></div>


<div class="col-md-12 mb">

 <div class="project_wrap">
    <div class="project_tabs_inn_table vendor_wrap_inn_table">
    	<div id="userMappingDiv" class="datatable_filter"></div>
	</div>
	
	
</div>
</div>
  
</div>
</form:form>
</section>

<script type="text/javascript">
	function mergeUser() {
		if ($('#userMapping_form').validate(false, validationSettings)) {
			var importedUser = $('#importedUserId :selected').text().trim();
			var orgUser = $('#slUserId :selected').text().trim();
			if(importedUser != orgUser){
				showAlertMessage();
			}else{
				mergeDiffUsers();
			}
			
		} else {
			return false;
		}
	}
	$('body').on('blur', '#userMapping_form', function() {
		$("#userMapping_form").showHelpOnFocus();
		$("#userMapping_form").validateOnBlur(false, validationSettings);
	});
</script>

<script>
	function getMappedUserList() {
		var sourceleadUrl = '${sourceleadUrl}';
		var orgLocationId = '${SourceLeadWeb_orgLocationId}';
		$.ajax({
					type : "GET",
					url : sourceleadUrl+ "/getMappedUserList?${_csrf.parameterName}=${_csrf.token}&orgLocationId="+orgLocationId,
					success : function(data) {
						var result = "";				        	
						result = result + '<table id="userMappingTable" class="display" cellspacing="0" width="100%" >';
						result = result + '<thead>';
						result = result + '<tr>';
						result = result + '<th>Employee Id</th>';
						result = result + '<th>Employee Name</th>';
						result = result + '<th>Status</th>';
						result = result + '</tr>';
						result = result + '</thead>	';
						result = result + '<tbody>';
			        	 for (var i=0; i<data.length; i++)
						{ 
			        		 var opt = data[i];
			        		    result = result + ' <tr>';
								result = result + ' <td><div class="tblminwidth-75" title="'+opt.employeeId+'">'+opt.employeeId+'</div></td>';
								result = result + '<td><div class="tblminwidth-75" title="'+opt.employeeName+'">'+opt.employeeName+'</div></td>';
								result = result + '<td><div class="tblminwidth-75">Merged</div></td>';
								result = result + ' </tr>';
						}
						result = result + '  </tbody>';
						result = result + ' </table>';
						$("#userMappingDiv").empty();
						$("#userMappingDiv").append(result);
						userMappingTablePagination();

					},
				});
		return true;
	}
</script>

<script>
function userMappingTablePagination(){ 
    $('#userMappingTable').DataTable( { 
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
    $("#WagesAndTaxListTable").wrap("<div class='overflow_Auto'></div>");
    $('.dataTables_filter input').attr("placeholder", "Search");
}
</script>

<script type="text/javascript">
function showAlertMessage(){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>Two Users are Different.! Do you want to merge these two users?</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input class='u_can_color' id='confirm-id' type='button' value='Ok' onclick='mergeDiffUsers();'><input class='width50percnt' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script type="text/javascript">
function mergeDiffUsers(){ 
	var frm = $('#userMapping_form').serializeFormJSON();
	var con = JSON.stringify(frm);
	con = con.replace(/[{}]/g, "");
	  //It will escape all the special characters
    var jsonData = encodeURIComponent(con);
	var sourceleadUrl = '${sourceleadUrl}';
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
		$.ajax({
				type : "POST",
				url : sourceleadUrl+ "/mergeUser?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
				success : function(result) {
					if (result == true) {
						location.replace(sourceleadUrl+ "/userMapping");
					}
				},
			}); 
	return true;
}
</script>

<script type="text/javascript">
function cancel(){ 
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
	$("#importedUserId").val('').trigger("chosen:updated");
	$("#slUserId").val('').trigger("chosen:updated");
}
</script>

<script>
function removeImportValidation(){
	$('#errImportedUserIdDiv').find('p.jquery_form_error_message').remove();
}
</script>

<script>
function removeOrgValidation(){
	$('#errOrgUserIdDiv').find('p.jquery_form_error_message').remove();
}
</script>

<jsp:include page="../masterFooter.jsp" />