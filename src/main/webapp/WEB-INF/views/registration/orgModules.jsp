<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="tab-b" class="newPrTabs_inn">
  <div id="choicepopUp"></div>
  <div id="orgModulesLoading"></div>
<form:form id="orgModules_form" name="orgModules_form" method="post">
	<div class="project_tabs_inn_head">
		<div class="clearfix mb10"></div>
		<div id="modulesListLoading"></div>
		 
		 <div class="tab-pane">
      	 <div class="form-horizontal">
      	 <div class="panel-group mt orgmodeulsAccordion" id="accordion">
		 <div id="modulesList"></div> 
		 <div class="clearfix"></div>
        	 <div class="clearfix colmd-12 modal-footer">
	        	 <!-- <div class="pull-left">
	        	 <label class="checkBox"><input type="checkbox" name="generateInvoice"><strong>Generate Invoice</strong></label>
	        	 </div> -->
	        	 <div class="clearfix"></div>
	        	 <div class="pull-right">
	        	<h4><strong>Total: <span class="ml" id="subscriptionRate"></span></strong>$</h4>
	        	<input type="hidden" name="invoiceAmount" id="invoiceAmount" value=""/>
	        	 </div>
        	 </div>
       	 </div>
        	 <div class="clearfix"></div>
       	 </div>
       	 </div>
		
	</div>
	
<div class="form-footer clearfix miniFoot">
  <div class="col-lg-12">
	<div class="pull-right">
	  <input type="submit" value="Register" onClick="registerCompanyModules()" class="btn btn-primary ml10">
	   <input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/enrolledOrgsList'/>'"  class="btn btn-default ml">
	</div>
  </div>
</div>
</form:form>
</div> 




<script type="text/javascript">
 
         $('body').on('submit', '#orgModules_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
         		var sourceleadUrl ='${sourceleadUrl}';
         		
         		var registeredModules = [];
        		$('input:checkbox[name=registeredModules]:checked').each(function() 
        	    {
        			registeredModules.push($(this).val());
        	    });
        		
        		var orgLinceseSubscriptionId = $("#orgLinceseSubscriptionId").val();
        		var invoiceAmount = $("#invoiceAmount").val();
        		var orgLocationId = $("#orgLocationId").val();
         		
        		$("#enrollOrgLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		 	
         		$.ajax({
          	          type: "POST",
                	  url: sourceleadUrl+"/updateOrgLicenseSubscription?${_csrf.parameterName}=${_csrf.token}&registeredModules="+registeredModules+"&orgLinceseSubscriptionId="+orgLinceseSubscriptionId+"&invoiceAmount="+invoiceAmount+"&orgLocationId="+orgLocationId,
                  	success: function(orglocations) {
                  		location.replace(sourceleadUrl+"/enrolledOrgsList");
			        	
			        },  
               });    
                
            	    return true;
            }
            else{
            	return false;	
               }
            });
            
        
     
	</script>

<script>
function getOrgModules(){
	var orgLocationId = document.getElementById("orgLocationId").value;
	 var result = '';
	
	 $.ajax({
	    	type: "POST",
	    	 url: sourceleadUrl+"/getRegistrationTabs?${_csrf.parameterName}=${_csrf.token}", 
	        success: function(json) {
	        	
	        	 result = result +'<ul class="list-unstyled list-inline">';
	        	 
	        	
	        	for(var i=0; i<json.length; i++){
        			var opt = json[i];
        			var checked = '';
        			var disabled = '';
        			var selectedTab = '';
        			if(opt.tabName === 'Associates' || opt.tabName === 'Settings'){
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
        			 result = result +'<input type="checkbox" id="checkbox'+opt.id+'" '+checked+' '+disabled+' value="'+opt.id+'" name="registeredModules" onclick="checkModule('+opt.id+')">';
        			 result = result +'<a data-toggle="collapse" data-parent="#accordion" href="#collapse1" class="checkBox">';
        			 result = result +''+opt.tabName+'</a>';
        			 result = result +'</div>';
        			 result = result +'</div>';
        			 result = result +'</li>';
        			 
	        	}
	        	
	        	 result = result +'</ul>';
	        	 
	        	 
	        	 $("#modulesList").empty();
	             $("#modulesList").append(result);
		        
	        },
	    }); 
	  
	
}
</script>

<script>
function checkModule(id){
	
	var isChecked = document.getElementById("checkbox"+id).checked
	if(isChecked == true){
		if(id == '19'){
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
				 
			 document.getElementById("checkbox10").checked = true;
		     document.getElementById("module10").removeAttribute("class")
		     document.getElementById("module10").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '4'){
			
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox3").checked = true;
			 document.getElementById("module3").removeAttribute("class")
		     document.getElementById("module3").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '23'){
			
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox3").checked = true;
			 document.getElementById("module3").removeAttribute("class")
		     document.getElementById("module3").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '16'){
			
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox7").checked = true;
			 document.getElementById("module7").removeAttribute("class")
		     document.getElementById("module7").className = 'panelDefault clearfix activeOrgbox'
		     
	    	 document.getElementById("checkbox8").checked = true;
			 document.getElementById("module8").removeAttribute("class")
		     document.getElementById("module8").className = 'panelDefault clearfix activeOrgbox'
		     
	    	 document.getElementById("checkbox9").checked = true;
			 document.getElementById("module9").removeAttribute("class")
		     document.getElementById("module9").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '20'){
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox7").checked = true;
			 document.getElementById("module7").removeAttribute("class")
		     document.getElementById("module7").className = 'panelDefault clearfix activeOrgbox'
		     
	    	 document.getElementById("checkbox9").checked = true;
			 document.getElementById("module9").removeAttribute("class")
		     document.getElementById("module9").className = 'panelDefault clearfix activeOrgbox'
		}else if(id == '30'){
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
			 
			 document.getElementById("checkbox8").checked = true;
			 document.getElementById("module8").removeAttribute("class")
		     document.getElementById("module8").className = 'panelDefault clearfix activeOrgbox'
		     
	    	 document.getElementById("checkbox10").checked = true;
			 document.getElementById("module10").removeAttribute("class")
		     document.getElementById("module10").className = 'panelDefault clearfix activeOrgbox'
		}else{
			 document.getElementById("module"+id).removeAttribute("class")
			 document.getElementById("module"+id).className = 'panelDefault clearfix activeOrgbox'
		}
		
		
		
	}else{
		
		var isdependentTabChecked = false;
		dependentTabId = '';
		var tab1 = '';
		var tab2 = '';
		var tab3 = '';
		var tab4 = '';
		if(id == '10'){
			dependentTabId = '19';
			tab1 = 'On & Off Boarding'
			tab2 = 'Candidate';
			
			var financeTabChecked = document.getElementById("checkbox30").checked;
			if(financeTabChecked){
				var jobsTab = document.getElementById("checkbox8").checked;
				if(jobsTab == false){
					dependentTabId = '30';
					tab1 = 'Campaigns'
					tab2 = 'Candidates';
					tab3 = 'Jobs'
				}
			}
			
		}else if(id == '3'){
			var tsTabChecked = document.getElementById("checkbox4").checked;
			var expencesTabChecked = document.getElementById("checkbox23").checked;
			if(tsTabChecked == true){
				dependentTabId = '4';
				tab1 = 'Time Sheets'
				tab2 = 'Projects';
			}else if(expencesTabChecked == true){
				dependentTabId = '23';
				tab1 = 'Expences'
				tab2 = 'Projects';
			}
		}else if(id == '7'){
			var contractsTabChecked = document.getElementById("checkbox16").checked;
			if(contractsTabChecked == true){
				var jobsTab = document.getElementById("checkbox8").checked;
				var vendorTab = document.getElementById("checkbox9").checked;
				if(jobsTab == false && vendorTab == false){
					dependentTabId = '16';
					tab1 = 'Contracts'
					tab2 = 'Clients';
					tab3 = 'Vendors';
					tab4 = 'Jobs';
				}
			}
			
			
			var financeTabChecked = document.getElementById("checkbox20").checked;
			if(financeTabChecked){
				var vendorTab = document.getElementById("checkbox9").checked;
				if(vendorTab == false){
					dependentTabId = '20';
					tab1 = 'Finance'
					tab2 = 'Clients';
					tab3 = 'Vendors'
				}
			}
			
			
		}else if(id == '8'){
			var contractsTabChecked = document.getElementById("checkbox16").checked;
			if(contractsTabChecked == true){
				var clientTab = document.getElementById("checkbox7").checked;
				var vendorTab = document.getElementById("checkbox9").checked;
				if(clientTab == false && vendorTab == false){
					dependentTabId = '16';
					tab1 = 'Contracts'
					tab2 = 'Jobs';
					tab3 = 'Clients';
					tab4 = 'Vendors';
				}
			}
			
			var financeTabChecked = document.getElementById("checkbox30").checked;
			if(financeTabChecked){
				var candidatesTab = document.getElementById("checkbox10").checked;
				if(candidatesTab == false){
					dependentTabId = '30';
					tab1 = 'Campaigns'
					tab2 = 'Jobs';
					tab3 = 'Candidates'
				}
			}
			
			
		}else if(id == '9'){
			var contractsTabChecked = document.getElementById("checkbox16").checked;
			if(contractsTabChecked == true){
				var clientTab = document.getElementById("checkbox7").checked;
				var jobTab = document.getElementById("checkbox8").checked;
				if(clientTab == false && jobTab == false){
					dependentTabId = '16';
					tab1 = 'Contracts'
					tab2 = 'Vendor';
					tab3 = 'Clients';
					tab4 = 'Jobs';
				}
			}
			
			var financeTabChecked = document.getElementById("checkbox20").checked;
			if(financeTabChecked){
				var clientTab = document.getElementById("checkbox7").checked;
				if(clientTab == false){
					dependentTabId = '20';
					tab1 = 'Finance'
					tab2 = 'Vendors';
					tab3 = 'Clients'
				}
			}
		}
		
		if(dependentTabId != null && dependentTabId != '' && dependentTabId != 'undefined'){
			isdependentTabChecked = document.getElementById("checkbox"+dependentTabId).checked;
		}
		
		if(isdependentTabChecked == true){
			document.getElementById("checkbox"+id).checked = true;
			if(tab3 != '' && tab4 != ''){
				alertMessage2(tab1,tab2,tab3,tab4);
			}else if(tab3 != '' && tab4 == ''){
				alertMessage3(tab1,tab2,tab3,tab4);
			}else{
				alertMessage(tab1,tab2);
			}
			
		}else{
			document.getElementById("module"+id).removeAttribute("class")
			document.getElementById("module"+id).className = 'panelDefault clearfix'
		}
		
	}
	
	
}
</script>


<script type="text/javascript">
function alertMessage(tab1,tab2){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p><strong>"+tab2+"</strong> should must for <strong>"+tab1+"</strong></p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>


<script type="text/javascript">
function alertMessage2(tab1,tab2,tab3,tab4){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p><strong>"+tab2+"</strong> or <strong>"+tab3+"</strong> or <strong>"+tab4+"</strong>  should must for <strong>"+tab1+"</strong></p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function alertMessage3(tab1,tab2,tab3){
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	result = result +"		<p><strong>"+tab2+"</strong> or <strong>"+tab3+"</strong> should must for <strong>"+tab1+"</strong></p>";
	result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='cancel();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancel();'></b>";
	result = result +"	</div>";
	$("#choicepopUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);

}
</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>