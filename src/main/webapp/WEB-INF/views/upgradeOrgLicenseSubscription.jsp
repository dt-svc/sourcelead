<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

 
 <section class="content-wraper">
	<div id="listLoading"></div>
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Upgrade License Subscription</h2>
          </div>
          <div class="pull-right brud-crum"><a href="${appPath}/dashboard">Home</a> &raquo; <a href="${appPath}/orgLicenseSubscriptionList">License Subscription List</a> &raquo;  Upgrade License Subscription </div>
        </section>
        
        <div class="content-area clearfix">
         <div id="choicepopUp"></div>
         <div id="subscriptionLoading"></div>
         
        	 <div class="clearfix form-area form-horizontal toggle-div remove-div mt">
        	 	 <form:form id="upgrade_license_form"  name="upgrade_license_form" method="post">
        	 	 	<div class="row">
				        <div class="mb col-xs-12 col-sm-12 col-md-6 col-lg-6">
				          	<div class="form-group">
					            <label class="col-md-5">Country<span class="text-danger">&nbsp;*</span></label>
					            <div class="col-md-7">
					              <input type="text" name="country" id="country"  value="${orgLicenseSubscription.country}" class="form-control" readonly="readonly" />
					            </div>
				            </div>
				            
				            <div class="form-group">
					          <label class="col-md-5">Subscription Type<span class="text-danger">&nbsp;*</span></label>
					          <div class="col-md-7">
					               <select class="form-control" name="subscriptionType" id="subscriptionType" field-name="Subscription Type" onchange="getInvoiceRate();" data-validation="required" >
					                    <option value="">Select Enrollment Type</option>
					                    <c:forEach var="subscription" items="${subscriptionTypeList}">
											<option value="${subscription.id}" ${orgLicenseSubscription.subscriptionType.id  == subscription.id ? 'selected' : ''}>${subscription.name}</option>
							  			</c:forEach>
						          </select>
					          </div>
					        </div>
					        
					        
					        <div class="form-group">
					          <label class="col-md-5">No.Of Users<span class="text-danger">&nbsp;*</span></label>
					          <div class="col-md-7">
					               <select class="form-control" name="userSubscription" id="userSubscription" field-name="Users" onchange="getInvoiceRate();" data-validation="required" >
					                    <option value="">Select Users</option>
					                    <c:forEach var="userSubscription" items="${userSubscriptionTypeList}">
											<option value="${userSubscription.id}" ${orgLicenseSubscription.userSubscriptionType.id  == userSubscription.id ? 'selected' : ''}>${userSubscription.name}</option>
							  			</c:forEach>
						          </select>
					          </div>
					        </div>
				            
				          </div>
				          
				          
				            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
				            
				            <div class="form-group">
					          <label class="col-md-5">Data Storage<span class="text-danger">&nbsp;*</span></label>
					          <div class="col-md-7">
					               <select class="form-control" name="storageType" id="dataStorage" field-name="Data Storage" data-validation="required" >
					                    <option value="">Select Data Storage</option>
					                    <c:forEach var="dataStorage" items="${dataStorageTypeList}">
											<option value="${dataStorage.id}" ${orgLicenseSubscription.storageSubscriptionType.id  == dataStorage.id ? 'selected' : ''}>${dataStorage.name}</option>
							  			</c:forEach>
						          </select>
					          </div>
					        </div>
					        
					        <div class="form-group">
					          <label class="col-md-5">Time Zone<span class="text-danger">&nbsp;*</span></label>
					          <div class="col-md-7">
					               <select class="chosen-select form-control" name="timezone" id="timezone" field-name="Time Zone" data-validation="required" >
					                    <option value="">Select Time Zone</option>
					                    <c:forEach var="timezone" items="${timezoneList}">
											<option value="${timezone.id}" ${timezone.id  == orgLicenseSubscription.timezone.id ? 'selected' : ''}>${timezone.timeZoneId}</option>
							  			</c:forEach>
						          </select>
					          </div>
					        </div>
				            
			              <div class="form-group">
					            <label class="col-md-5">Subscription Date<span class="text-danger">&nbsp;*</span></label>
					            <div class="col-md-7">
					              <input type="text" name="subscriptionDate" id="subscriptionDate"  value="${orgLicenseSubscription.fromDateStr}" class="form-control" readonly="readonly" />
					            </div>
				            </div>
				            
		            </div>
		            
			        <div class="tab-pane col-md-12">
			      	 <div class="form-horizontal">
			      	 <div class="panel-group mt orgmodeulsAccordion" id="accordion">
					
					 <ul class="list-unstyled list-inline">
						
						<c:forEach var="tabs" items="${orgLicenseSubscription.tabsDTOList}">
						 <li class="col-md-4">
						 	<div class="panelDefault clearfix ${tabs.isTabSubscribed  == 'Y' ? 'activeOrgbox' : ''}" id="module${tabs.id}">
						 		<div class="mediaLeft pull-left">
						 		    <i class="${tabs.tabIcon}"></i>
						 		</div>
						 		<div class="pull-left checkBox mt10">
						 		<input type="checkbox" id="checkbox${tabs.id}"  value="${tabs.id}" ${tabs.isTabSubscribed  == 'Y' ? 'checked' : ''} ${tabs.isTabSubscribed  == 'Y' ? 'disabled' : ''} name="registeredModules" onclick="checkModule(${tabs.id})">
						 		<a data-toggle="collapse" data-parent="#accordion" href="#collapse1" class="checkBox">${tabs.tabName}</a>
						 		</div>
						 	</div>
						 </li>
						</c:forEach>
						
					 </ul>
					
					
					 <div class="clearfix"></div>
			        	 <div class="clearfix colmd-12 modal-footer">
				        	 <div class="clearfix"></div>
				        	 <div class="pull-right">
				        	<h4><strong>Total: <span class="ml"></span></strong>$ ${orgLicenseSubscription.invoiceAmount}</h4>
				        	<h4><strong>Upgraded Invoice: <span class="ml"></span></strong>$<span id="subscriptionRate">0</span></h4>
				        	<input type="hidden" name="invoiceAmount" id="invoiceAmount" value="${orgLicenseSubscription.invoiceAmount}"/>
				        	 </div>
			        	 </div>
			       	 </div>
			        	 <div class="clearfix"></div>
			       	 </div>
			       	 </div>
			       	 
		         </div> 
		         
		         
	       	<div class="form-footer clearfix row">
				<div class="col-lg-12">
					<div class="pull-right">
						<input type="submit" value="Upgrade" class="btn btn-primary ml10">
						<input type="button" value="Cancel" onClick="javascript:window.location.href = '<c:url value='${appPath}/orgLicenseSubscriptionList'/>'"   class="btn btn-default ml">
					</div>
				</div>
			</div>  
		            
        	 	 </form:form>
        	 </div>
        </div>
</section>


<jsp:include page="masterFooter.jsp" />	  



<script type="text/javascript">
var validationSettings = {
        errorMessagePosition : 'element',
       borderColorOnError : '',
		scrollToTopOnError : true,
		dateFormat : 'yyyy/mm/dd' 
   };  
         $('body').on('submit', '#upgrade_license_form', function(e){
        	  if ($(this).validate(false, validationSettings)){
        		e.preventDefault();
        		var formData = getFormData("upgrade_license_form");
         		var sourceleadUrl ='${sourceleadUrl}';
         		var subscriptionUUID = '${orgSubscriptionUUID}';
         		var orgId = '${SourceLeadWeb_org.id}';
         		
         		$("#subscriptionLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
         		 
         		
         		var registeredModules = [];
        		$('input:checkbox[name=registeredModules]:checked').each(function() 
        	    {
        			registeredModules.push($(this).val());
        	    });
        		
        		var invoiceAmount = document.getElementById("invoiceAmount").value;
        		if(invoiceAmount > 0){
        			 $.ajax({
                	      type: "POST",
                	    	data: formData,
                      	   url: "/app/init-payment?${_csrf.parameterName}=${_csrf.token}&type=upgrade&subscriptionUUID="+subscriptionUUID+"&registeredModules="+registeredModules,
                        	success: function(data) {
                        		 if (data) {
                	                location.href = data;
                	            }
       			        	
       			        },  
                     }); 
        		}else{
           		 
         		 $.ajax({
             	      type: "POST",
             	    	data: formData,
                   	    url: sourceleadUrl+"/upgradeOrgLocationLicenseSubscription?${_csrf.parameterName}=${_csrf.token}&orgId="+orgId+"&registeredModules="+registeredModules+"&licenseUUID="+subscriptionUUID,
                     	success: function(result) {
   			         	if(result == 'subscribeList'){
   			         	    location.replace(sourceleadUrl+"/orgLicenseSubscriptionList");
   			         	}
   			        	else if(orglocations.registrationStatusStr == 'DataMissing'){
   			        		$("#subscriptionLoading").removeAttr('style');
   			        	} 
   			        	
   			        },  
                  }); 
           		 
        		}
                
         	    return true;
            }
            else{
            	return false;	
               }
            });
            
         $('body').on('blur', '#upgrade_license_form', function() {
     		$("#upgrade_license_form").showHelpOnFocus();
     		$("#upgrade_license_form").validateOnBlur(false, validationSettings);
     	});
     
	</script>
	
	
	<script>
	function getInvoiceRate(){
		var subscriptionType = $("#subscriptionType").val();
		var userSubscription = $("#userSubscription").val();
		var sourceleadUrl ='${sourceleadUrl}';
		var subscriptionUUID = '${orgSubscriptionUUID}'; 
		if(subscriptionType && userSubscription){
			
			 $.ajax({
			    	type: "POST",
			    	 url: sourceleadUrl+"/getUpgradeInvoiceRateBySubscriptionRateAndUserSubscription?${_csrf.parameterName}=${_csrf.token}&subscriptionType="+subscriptionType+"&userSubscription="+userSubscription+"&subscriptionUUID="+subscriptionUUID, 
			        success: function(invoiceAmount) {
			        	
			        	 $("#subscriptionRate").html(invoiceAmount);
			        	 $("#invoiceAmount").val(invoiceAmount)
				        
			        },
			    }); 
		}
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
				alertMessage1(tab1,tab2);
			}
			
		}else{
			document.getElementById("module"+id).removeAttribute("class")
			document.getElementById("module"+id).className = 'panelDefault clearfix'
		}
		
	}
	
	
}
</script>


<script type="text/javascript">
function alertMessage1(tab1,tab2){
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
$(function(){
	       var config = {
			  '.chosen-select'           : {},
			  '.chosen-select-deselect'  : {allow_single_deselect:true},
			  '.chosen-select-no-single' : {disable_search_threshold:10},
			  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			  '.chosen-select-width'     : {width:"95%"}
			}
	    
           
	       $('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
		  })
		
});
</script>

<script type="text/javascript">
function cancel(){
	$("#choicepopUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>