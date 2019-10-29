<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script>
$(document).ready(function(){
	var custmConfUUID = '${customDashBoardConfUUID}';
	 getRoleBasedModules(custmConfUUID);
	});
</script>


<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left"><h2>Add Dashboard Modules</h2></div>
      <div class="pull-right brud-crum"><a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${appPath}/configureCustomDashBoard">Dashboarrd list </a> &raquo; Add Dashboard Modules</div>
    </section>
    
    <div id="loading"></div>
    <div class="project_wrap newDashSection newDashSectionMdu clearfix">
    <form:form id="module_conf_form"  name="module_conf_form" method="post">
        <div class="newDashLeft">
        <div id="tabsListDiv"></div>
        </div>

        <div class="newDashRight">
            <div class="clearfix" id="tab-content">
        </div>
        
    <div class="footerstyelsNew">
        <div class="pull-right mr">
            <input value="Add" class="btn btn-primary mr" type="button" onclick="saveCustomModuleConfiguration();">
        </div>
    </div>

        </div>
	</form:form>
	</div>
	<div id="actionsPopUp"></div>
  </section>
  
  
  <script>
  function saveCustomModuleConfiguration(){
	  var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
	  var customDashBoardConfUUID = '${customDashBoardConfUUID}';
	  var subModuleIds = [];
		 $("input[name='subModule[]']:checked").each( function (i) {
			 subModuleIds[i] = $(this).val();
		});
		 alert("Length >>>> "+subModuleIds.length)
		 if(subModuleIds.length  > 0){
			 if(subModuleIds.length <= 15){
					$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:1000;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
					$.ajax({ 
				    	type: "POST",
				    	url: sourceleadUrl+"/saveModuleDashBoardConfiguration?${_csrf.parameterName}=${_csrf.token}&customDashBoardConfUUID="+customDashBoardConfUUID+"&subModuleIds="+subModuleIds,
				        success: function(json) {
				        	if((json.response == 'SUCCESS')){
				        		alert("Sucess");
				        		//location.replace(sourceleadUrl+"/enrollRoleActions?uuid="+uuid); 
				        		location.replace(sourceleadUrl+"/configureCustomDashBoard"); 
					        } 
				        },
				 }); 
				}else{
					alertMessage("Your Sub moudlues exceeded");
				}
		 }else{
				alertMessage("Select atleast one Sub Module");
		 }
		
	  
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

<script>
function getRoleBasedModules(custmConfUUID){
	    var sourceleadUrl ='${SourceLeadWeb_sourceleadUrl}';
		$.get(sourceleadUrl+"/getRoleBasedModules?"+tokenParamName+"="+tokenValue+"&custmDashBoardConfUUID="+custmConfUUID, function(res) {
		
			var result = "";
			var output = "";
			result = result + '<ul id="menuTabs" class="clearfix">';
			for(var i=0;i<res.length;i++){
				var json = res[i];
				if(i==0){
					result = result + '<li rel="'+json.tabName+'" ><a href="#" id="menuTabs'+json.tabName+'" onclick="activeTabClick(\''+json.tabName+'\');" class="active">'+json.tabName+'</a></li>';
				}else{
					result = result + '<li  rel="'+json.tabName+'" ><a href="#" id="menuTabs'+json.tabName+'" onclick="activeTabClick(\''+json.tabName+'\');">'+json.tabName+'</a></li>';
				}
				
				
				output = output + '<div id="tab'+i+'">';
				output = output + '<ul>';
				for(var j=0;j<json.subTabsDTO.length;j++){
					var opt = json.subTabsDTO[j];
					output = output + '<li class="clearfix">';
					if(opt.isSubTabConfigured == 'NO'){
						output = output + '<input  id="sTab'+opt.subTabId+'" value="'+opt.subTabId+'" class="checkbox-custom" name="subModule[]" type="checkbox">';
						output = output + '<label for="sTab'+opt.subTabId+'" class="checkbox-custom-label paddingright">'+opt.subTabDisplayName+'</label>';
					}else{
						output = output + '<input checked id="sTab'+opt.subTabId+'" value="'+opt.subTabId+'" class="checkbox-custom" name="subModule[]" type="checkbox">';
						output = output + '<label for="sTab'+opt.subTabId+'" class="checkbox-custom-label paddingright">'+opt.subTabDisplayName+'</label>';
					}
					
					output = output + '</li>';
						
				}
				output = output + '</ul>'
				output = output + '</div>';
				
			}
			
			result = result + '</ul>';
			
			$("#tabsListDiv").empty();
			$("#tabsListDiv").append(result);
			$("#tab-content").append(output);
			$('#tab-content div').hide();
			
			
			
		
		}); 
}
</script>


<script>
$('#tab-content div').hide();
$('#tab-content div:first').show();

function activeTabClick(tabName){
	
	$('#menuTabs li').each(function(){
		document.getElementById("menuTabs"+$(this).attr('rel')).removeAttribute("class");	
	});
	document.getElementById("menuTabs"+tabName).className = 'active';
	$('#tab-content div').hide();
	
	
	$('#menuTabs li').click(function() {
        var indexer = $(this).index(); 
        $('#tab-content div:eq(' + indexer + ')').fadeIn(); 
    });  
	
}

</script>


<jsp:include page="../masterFooter.jsp" />
