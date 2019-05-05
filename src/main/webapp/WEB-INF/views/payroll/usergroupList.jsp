<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/jquery.maskedinput-1.3.min.js?staticTime=${staticTime}"></script>

<script>
$(document).ready(function(){
	getUserGroupList();
// 	getUsersByDepartment();
});
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Batches</h2>
    </div>
     <div class="pull-right brud-crum">
                <a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;  <a href="<c:url value='${appPath}/settings'/>">Settings</a>&raquo; <a
				href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo; Batches
      </div>
  </section>
  <div class="timesheet_out">
    <div class="tablelist-wrap backgroundWhite">
      <div class="reportHead">
	<div class="pull-right">
		<ul class="list-unstyled list-inline clearfix">
		<li>
			<a onClick="addbatches()" class="newAdBtn mr5" id="createbatches">+ Add Batch</a>
		</li>
			<li>
			<div class="dynamic_search mt10">
			<input type="text" placeholder="Search" id='dataTableSearch' />
			<i class="fa fa-search"></i>
			</div>              
			</li>
		</ul>
	</div>
</div>
<!-- <div class="dynamic_search">
			<input type="text" placeholder="Search" id='dataTableSearch' onclick="getAllDeductionType();"/>
			<i class="fa fa-search"></i>
		 </div> -->
      <div class="require_wrap_table datatable_filter">
      <div id="userGroupDiv"></div>
               
    <div class="clearfix"></div>
    </div>
  </div>
      <div class="side_mask"><div class="proj_close"></div></div>
    <div class="clients_side_wrap">
      <div class="clients_side_wrap_inn">
			
		</div> 
   </div> 
   </div>
   <jsp:include page="usPayrollDashboardEmployees.jsp" />
    </section>
    
    <!-- Content Area End -->
    
    <!--  Popups -->
    <div id="addUserGroupFormDiv"> </div>



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
function closeBatch() {
	$(".addPayrollGroupWrap, .mask").fadeOut();
	 document.getElementById("add_user_group_form").reset(); 
	 $('#add_user_group_form').find('p.jquery_form_error_message').remove();
	 $("#userId").empty();
}
 
</script> 

<script>
 function getUsersByDepartment(){
	var department = $("#department").val();
	if(groupUUID == null || groupUUID == '' || groupUUID == 'undefined'){
		groupUUID = "";
	}
		 $.ajax({
		    	type: "GET",
		        url: sourceleadUrl+"/getUsersByDepartment?"+tokenParamName+"="+tokenValue+"&departmentId="+department                  , 
		        success: function(json) {
		       var output = "";
				for (var i=0; i<json.length; i++)
				{
				    var opt = json[i];
				    if(opt.lastName != null && opt.lastName != '' && opt.lastName != 'undefined'){
				    	output = output + "<option value='" +opt.userId+ "'>"+opt.firstName+" "+opt.lastName+" ("+opt.displayName+")</option>";
				    }else{
				    	output = output + "<option value='" +opt.userId+ "'>"+opt.firstName+" ("+opt.displayName+")</option>";
				    }
				}
				$("#assocByDept").empty();
				$("#assocByDept").append(output);
				$('#assocByDept').trigger("chosen:updated");
				$(".addPayrollGroupWrap, .mask").fadeIn();
		        },
		    });
	 
} 
</script>
<jsp:include page="../masterFooter.jsp" />

