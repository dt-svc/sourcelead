<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-b" class="newPrTabs_inn">
  <div id="choicepopUp"></div>
  <div id="orgModulesLoading"></div>
<form:form id="orgModules_form" name="orgModules_form" method="post">
	<div class="project_tabs_inn_head">
		<div class="clearfix mb10"></div>
		<div id="modulesListLoading"></div>
		 <div id="modulesList"></div> 
		
	</div>
	
<div class="form-footer clearfix miniFoot">
  <div class="col-lg-12">
	<div class="pull-right">
	  <input type="button" value="Cancel"  onClick="javascript:window.location.href = '<c:url value='${appPath}/enrolledOrgsList'/>'"  class="btn btn-danger">
	  <input type="button" value="Save & Continue" onClick="registerCompanyModules()" class="btn btn-primary ml10">
	</div>
  </div>
</div>
</form:form>
</div> 

<script>
function getOrgModules(){
	var orgLocationId = document.getElementById("orgLocationId").value;
	 var result = '';
	
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getRegistrationTabs?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(json) {
	        	
	        	 result = result +'<div class="tab-pane">';
	        	 result = result +'<div class="form-horizontal">';
	        	 result = result +'<div class="panel-group mt orgmodeulsAccordion" id="accordion">';
	        	 result = result +'<ul class="list-unstyled list-inline">';
	        	 
	        	
	        	for(var i=0; i<json.length; i++){
        			var opt = json[i];
        			var checked = '';
        			var disabled = '';
        			var selectedTab = '';
        			if(opt.tabName === 'Associates'){
        				checked = 'checked';
        				disabled = 'disabled';
        				selectedTab = 'activeOrgbox';
        			}
        			
        			 result = result +'<li class="col-md-4">';
        			 result = result +'<div class="panelDefault clearfix '+selectedTab+'" id="module'+opt.id+'">';
        			 result = result +'<div class="mediaLeft pull-left">';
        			 result = result +'<i class="'+opt.tabIcon+'"></i>';
        			 result = result +'</div>';
        			 result = result +'<div class="pull-left checkBox mt10">';
        			 result = result +'<input type="checkbox" id="chekbox'+opt.id+'" '+checked+' '+disabled+' value="'+opt.id+'" onclick="checkModule('+opt.id+')">';
        			 result = result +'<a data-toggle="collapse" data-parent="#accordion" href="#collapse1" class="checkBox">';
        			 result = result +''+opt.tabName+'</a>';
        			 result = result +'</div>';
        			 result = result +'</div>';
        			 result = result +'</li>';
        			 
	        	}
	        	
	        	 result = result +'</ul>';
	        	 
	        	 result = result +'<div class="clearfix"></div>';
	        	 result = result +'<div class="clearfix colmd-12 modal-footer">';
	        	 result = result +'<div class="pull-left">';
	        	 result = result +'<label class="checkBox"><input type="checkbox" name="generateInvoice"><strong>Generate Invoice</strong></label>';
	        	 result = result +'</div>';
	        	 result = result +'<div class="clearfix"></div>';
	        	 result = result +'<div class="pull-right">';
	        	 result = result +'<h4><strong>Total: <span class="ml">2000$</span></strong></h4>';
	        	 result = result +'</div>';
	        	 result = result +'</div>';
	        	 result = result +'</div>';
	        	 result = result +'<div class="clearfix"></div>';
	               
	        	 result = result +'</div>';
	        	 result = result +'</div>';
	        	 $("#modulesList").empty();
	             $("#modulesList").append(result);
		        
	        },
	    }); 
	  
	
}
</script>

<script>
function checkModule(id){
	
	var isChecked = document.getElementById("chekbox"+id).checked
	if(isChecked == true){
		 document.getElementById("module"+id).removeAttribute("class")
		 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
	}else{
		document.getElementById("module"+id).removeAttribute("class")
		 document.getElementById("module"+id).className = 'panelDefault clearfix'
	}
	
}
</script>