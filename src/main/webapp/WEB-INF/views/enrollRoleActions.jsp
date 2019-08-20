<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="/resources/css/jquery.tree-multiselect.css?staticTime=${staticTime}">

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
      <div class="pull-right brud-crum"><a href="${appPath}/orgRolesList">Roles List</a> &raquo; Role Actions </div>
    </section>
    
   <!-- Role Persmission -->
    <section class="clearfix vendor_wrap">
     	 <%-- <div class="clearfix">
			<h3 id="role"></h3>
			<select id="test-select" multiple="multiple">
				<c:forEach var="action" items="${actionsList}">
					<option value="${action.id}" data-section="${action.tabs.tabName == 'My Timesheets' || action.tabs.tabName == 'Timesheet Approval' ? 'Timesheets' : action.tabs.tabName == 'Associates List' ? 'Associates' : action.tabs.tabName == 'Receivable' || action.tabs.tabName == 'Payables' ? 'Billing' : action.tabs.tabName == 'My Expenses' || action.tabs.tabName == 'Expenses Approval' ? 'Expenses' : action.tabs.tabName == 'Blogs' || action.tabs.tabName == 'User Manuals' ? 'Resources' : action.tabs.tabName == 'Immigrations' || action.tabs.tabName == 'My Immigration' ? 'Immigration'  : action.tabs.tabName == 'Run Payroll' || action.tabs.tabName == 'My Payroll' ? 'Pay Roll' : action.tabs.tabName}" ${action.isActionConfigured  == 'Y' ? 'selected' : ''} >${action.actionName}</option>
				</c:forEach>
		    </select>
		</div> --%>
		
		<div class="positionRelative reportHead clearfix"> 
			<h2 class="newRolName" id="role"></h2>
            <ul class="pull-right list-unstyled list-inline">
				<li class="roleSeleAll">
                	<input id="selectAll" value="" class="checkbox-custom" type="checkbox">
                	<label for="selectAll" class="checkbox-custom-label paddingright">Select All</label>
                </li>              
				<li class="">
	            	<input class="repSearchBar" placeholder="Search" id="roleSearch" type="text" onkeyup='roleSearch();'>
	                <span class="repSearchIcon" onclick="roleSearch();"></span>
				</li>
            </ul>
          </div>
		
		<div class="newRolWrap clearfix">
			<div id="loading"></div>
		    <div class="newRolLeft">
		        <ul id="nav" class="clearfix">
		        	<c:set value="1" var="tabIndex"></c:set>
		        	<c:forEach items="${tabsMap}" var="tab">
		        		<li data-name="${tab.key}">
		                    <a href="javascript:void(0)" ${tabIndex == 1 ? "class='active'" : ""}>${tab.key}<b class="pull-right">0/0</b></a>
		                    <span>
		                        <input id="tab${tabIndex}" value="" class="checkbox-custom" name="tabName" type="checkbox">
		                        <label for="tab${tabIndex}" class="checkbox-custom-label paddingright"></label>                         
		                    </span>
		                    <c:set value="${tabIndex + 1}" var="tabIndex"></c:set>
		                </li>
		        	</c:forEach>
		        </ul>
		    </div>

		    <div class="newRolLRight">
		        <div class="clearfix" id="tab-content">
					<c:forEach items="${tabsMap}" var="tab">
						<c:set var="actionIndex" value="0"></c:set>
		                <div name="actionTabsDiv" data-name="${tab.key}">
		                    <ul class="clearfix">
								<c:forEach items="${tab.value}" var="subTab">
									<c:forEach var="action" items="${actionsList}">
										<c:if test="${subTab == action.tabs.tabName}">
											<li class="clearfix">
					                            <input id="actionCB${action.id}" value="${action.id}" class="checkbox-custom" name="actionsList[]" type="checkbox" ${action.isActionConfigured  == 'Y' ? 'checked' : ''}>
					                            <label for="actionCB${action.id}" class="checkbox-custom-label paddingright">${action.actionName}</label>
					                        </li>
					                        <c:if test="${action.isActionConfigured  == 'Y'}">
						                        <c:set var="actionIndex" value="${actionIndex + 1}"></c:set>
					                        </c:if>
										</c:if>
									</c:forEach>
		                    	</c:forEach>
		                    </ul>
		                </div>
					</c:forEach>
		        </div>
		        
		        <div class="footerstyelsNew clearfix padding15">
	                <div class="pull-right">
	                    <input type="button" value="Save" id="save_client" onclick="saveActions();" class="btn btn-primary"> 
			     		<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/orgRolesList'/>'" class="btn btn-default ml">
	                </div>
	            </div>
		        
		    </div>
		</div>

        <div id="actionsPopUp"></div>
    </section>
   <!-- Role Persmission Ends -->

  </section>
  <!-- Content Area Ends--> 

<script src="/resources/js/jquery.tree-multiselect.js"></script>

<jsp:include page="masterFooter.jsp" />	
<div class="backtotop" style="display: none;"><img src="/resources/images/backtotop.png"></div>  

<script type="text/javascript">
$(document).ready(function() {
	
	var roleDisplayName = '${roleDisplayName}';
	$("#role").val(roleDisplayName);
	$("#role").append(roleDisplayName);
	
	$("div[class='newRolLRight']").find("div[name='actionTabsDiv']").each(function() {
		var total = $(this).find("input[name='actionsList[]']").length;
		var selected = 0;
		$(this).find("input[name='actionsList[]']").each(function() {
			if($(this).is(":checked")) {
				selected++;
			}
		})
		if(selected === total) {
			$("div[class='newRolLeft']").find("li[data-name='" + $(this).data("name") + "']").find("input[name='tabName']").prop("checked", true);
		}
		$("div[class='newRolLeft']").find("li[data-name='" + $(this).data("name") + "']").find("b").data("total", total);
		$("div[class='newRolLeft']").find("li[data-name='" + $(this).data("name") + "']").find("b").html(selected + "/" + total);
	});
	
	$("input[name='tabName']").click(function() {
		var checkedAll = $(this).is(":checked");
		var tabName = $(this).parents("li").data("name");
		$("div[data-name='" + tabName + "']").find("input[name='actionsList[]']").prop("checked", checkedAll);
		var total = $(this).parents("li").find("b").data("total");
		$(this).parents("li").find("b").html((checkedAll ? total : 0) + "/" + total);
	});
	
	$("#selectAll").click(function() {
		var checkedAll = $(this).is(":checked");
		$("input[name='tabName']").each(function () {
			$(this).prop("checked", checkedAll);
			var total = $(this).parents("li").find("b").data("total");
			$(this).parents("li").find("b").html((checkedAll ? total : 0) + "/" + total);
		});
		$("input[name='actionsList[]']").prop("checked", checkedAll);
	});
	
    $('#tab-content div').hide();
    $('#tab-content div:first').show();

    $('#nav li').click(function() {
        $('#nav li a').removeClass("active");
        $(this).find('a').addClass("active");
        $('#tab-content div').hide();

        var indexer = $(this).index(); 
        $('#tab-content div:eq(' + indexer + ')').fadeIn(); 
    });  
});

function roleSearch() {
	var activeMenu = $(".newRolLeft a[class='active']").parent().data("name");
	var visibleMenuDiv = $("div[data-name='" + activeMenu + "']");
	var search = $("#roleSearch").val().toLowerCase();
	$(visibleMenuDiv).find("li").each(function() {
		if($(this).find("label").html().toLowerCase().indexOf(search) !== -1) {
			$(this).show();
		} else {
			$(this).hide();
		}
	});
}

function alertMessage(message){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a pofix'>";
	result = result +"		<p>"+message+"</p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' style='width:100%' type='button' value='Ok' onclick='cancel();'></b>";
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

 <!-- <script type="text/javascript">
 // Multiselect script
      var roleName = '${roleName}';
      $("#test-select").treeMultiselect({ enableSelectAll: true},roleName);
    </script> -->
    
    <script type="text/javascript">
	function saveActions() {
		
		var uuid = '${uuid}';
		var sourceleadUrl = '${sourceleadUrl}';
		var actionIds = [];
		  
		 $("input[name='actionsList[]']:checked").each( function (i) {
			 actionIds[i] = $(this).val();
		});
		 
		if(actionIds.length > 0){
			$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			$.ajax({ 
		    	type: "POST",
		    	url: sourceleadUrl+"/saveRoleActions?${_csrf.parameterName}=${_csrf.token}&uuid="+uuid+"&actionIds="+actionIds,
		        success: function(result) {
		        	if((result == 'success')){
		        		location.replace(sourceleadUrl+"/enrollRoleActions?uuid="+uuid); 
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
	function isCheckboxChecked(id) {
		if($("#treemultiselect-"+id.value).is(':checked') == false){
			$("#treemultiselect-"+id.value).prop('checked', true);
		}
	}
</script>
    
    
</body>
</html>