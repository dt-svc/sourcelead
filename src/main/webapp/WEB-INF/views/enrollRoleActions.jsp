<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="resources/js/jquery-fun.js"></script> 
<script src="resources/js/formvalidator.js"></script>



<link rel="stylesheet" href="resources/css/chosen.min.css">
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link href="resources/css/timesheet.css" rel="stylesheet">
<link href="resources/css/sourcelead-main.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery.tree-multiselect.css">

<script type="text/javascript">
window.onload = function () {
	
	var roleDisplayName = '${roleDisplayName}';
	$("#role").val(roleDisplayName);
	$("#role").append(roleDisplayName);
 };
</script>

<!-- Content Area -->
  <section class="content-wraper">
    <section class="content-header clearfix">
    <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector"  style="display: block;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
      <div class="pull-left">
        <h2>Role Actions</h2>
      </div>
      <div class="pull-right brud-crum"><a href="orgRolesList">Roles List</a> &raquo; Role Actions </div>
    </section>
    
   <!-- Role Persmission -->
    <section class="clearfix">
     <div class="select-role clearfix section-action-btns">
     <div id="loading"></div>
     <div class="clearfix mt">
     <h3 id="role"></h3>
	<select id="test-select" multiple="multiple">
		 <c:forEach var="action" items="${actionsList}">
		  <option value="${action.id}" data-section="${action.tabs.tabName == 'My Timesheets' || action.tabs.tabName == 'Timesheet Approval' ? 'Timesheets' : action.tabs.tabName}" ${action.isActionConfigured  == 'Y' ? 'selected' : ''} >${action.actionName}</option>
	     </c:forEach>
    </select>
     </div>

        <div class="form-footer clearfix mt">
            <div class="col-lg-12">
                <div class="pull-right">
                 <input type="button" value="Save" id="save_client" onclick="saveActions();" class="btn btn-primary"> 
			     <input type="button" value="Cancel" onClick="javascript:location.href = '<c:url value='/orgRolesList'/>'" class="btn btn-default ml">
                </div>
            </div>
        </div>
        </div>
        <div id="actionsPopUp"></div>
    </section>
   <!-- Role Persmission Ends -->

  </section>
  <!-- Content Area Ends--> 


<script src="resources/js/jquery-1.11.3.min.js"></script> 
<script src="resources/js/jquery-ui.min.js"></script>
<script src="resources/js/jquery.tree-multiselect.js"></script>



<jsp:include page="masterFooter.jsp" />	
<div class="backtotop" style="display: none;"><img src="resources/images/backtotop.png"></div>  



<script type="text/javascript">
function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#actionsPopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#actionsPopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>


<!-- Include all compiled plugins (below), or include individual files as needed --> 

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

 <script type="text/javascript">
 // Multiselect script
      var roleName = '${roleName}';
      if(roleName == 'ROLE_ADMIN'){
    	  $("#test-select").treeMultiselect({ enableSelectAll: false},roleName);
      }else{
    	  $("#test-select").treeMultiselect({ enableSelectAll: true },roleName);
      }
      
      
    </script>
    
    
    
    <script type="text/javascript">
	function saveActions() {
		
		var roleId = '${roleId}';
		var sourceleadUrl = '${sourceleadUrl}';
		var actionIds = [];
		  
		 $("input[name='actionsList[]']:checked").each( function (i) {
			 actionIds[i] = $(this).val();
		});
		 
		if(actionIds.length > 0){
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
			$.ajax({ 
		    	type: "POST",
		    	url: sourceleadUrl+"/saveRoleActions?${_csrf.parameterName}=${_csrf.token}&roleId="+roleId+"&actionIds="+actionIds,
		        success: function(result) {
		        	if((result == 'success')){
		        		location.replace(sourceleadUrl+"/enrollRoleActions?roleId="+roleId); 
			        } 
		        },
		 }); 
		}else{
			alertMessage("Select atleast one action");
		}
		 
		
	}

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

<script>
function isCheckboxChecked(id){
	if($("#treemultiselect-"+id.value).is(':checked') == false){
		$("#treemultiselect-"+id.value).prop('checked', true);
	}
}
</script>
    
    
</body>
</html>