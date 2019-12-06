<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- client details View starts-->

<style>
.profilerefresh-icon {
	position: absolute;
    right: 0px;
    top: 24px;
    height: 28px;
    line-height: 14px;
}
.NewStyleFullWidth {
	width: 100%; padding: 0px 0px; margin: 0px auto;
}
.NewStyleFullWidth .col-md-9 {
	width: 100%; padding: 0px 0px;
}
.NewStyleFullWidth .form-group {
	width: 50%; margin-bottom: 15px; float: left; padding: 0px 2%;
}
	
.checkBox input[type="checkbox"], .radioIput input[type="radio"] {
width: auto; height: auto; margin-bottom: 15px;
}
.checkboxLable {
margin-bottom: 15px;
}
</style>
 
<script type="text/javascript">
	function clientDetails(clientLocationId,orgRelationID) {
		var vmsUrl = '${SourceLeadWeb_vmsUrl}';
		$.ajax({
					type : "POST",
					url : vmsUrl+"/getClientAndVendorOrgDeatilsById?"+tokenParamName+"="+tokenValue+"&vendorLocationId="+clientLocationId+"&orgRelationId="+orgRelationID+"&requestType=Client",
					success : function(json) {
					//	var json = JSON.parse(data);
						getClientNeworgname(json.orgName);
						var result = "";
						var city = "";
						var state = "";
						var country = "";
						
					/* if(json.msg != null && json.msg != "" && json.msg != 'undefined'){
							result = result+ '<div class="success_pop" id="selector1" style="display: block;">';
							result = result+ ' <i class="fa fa-check-circle"></i><span>'+json.msg+'</span>';
							result = result+ '</div>';
						}  */
						if(json.msg != null && json.msg != "" && json.msg != 'undefined'){
							result  = result + '<div class="success_pop" id="selector8123">';
						    result  = result + '<i class="fa fa-check-circle"></i><span id="clientDetailsMsgDiv">'+json.msg+'</span>';
						    result  = result + '</div>';
						}
						
						result = result+ '<div class="project_tabs_inn_table">';
                        
						result = result + '<div class="project_details">';
						'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_EDIT_CLIENT\')">';
							result = result + ' <div class="useredit useredit_a">';
							result = result + '<a id="edit-client" href="javascript:editClientDetails('+clientLocationId+','+orgRelationID+')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
							result = result + ' </div>';
						'</sec:authorize>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client Name :</strong>';
						if(json.orgName !=null && json.orgName !='' && json.orgName !='undefined'){
						result = result + '<span title='+json.orgName+'>'+json.orgName+'</span>';
						}
						
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Web Url:</strong>';
						if(json.webSite !=null && json.webSite !='' && json.webSite !='undefined'){
						result = result + '<span title='+json.webSite+'>'+json.webSite+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
					
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client Location :</strong>';
						if((json.country || json.state || json.city) != null && (json.country || json.state || json.city)!='' && (json.country || json.state || json.city) !='undefined'){
						if(json.city != null &&  json.city!='' && json.city !='undefined'){
						result=result+'<span>'+json.city;
						}
						if((json.city != null &&  json.city!='' && json.city !='undefined')&&(json.state != null &&  json.state!='' && json.state !='undefined')){
							result=result+' , ';
						}
						
						
						if(json.state != null &&  json.state!='' && json.state !='undefined'){
						result=result+json.state;
						}
						if((json.country != null &&  json.country!='' && json.country !='undefined')&&((json.city != null &&  json.city!='' && json.city !='undefined')||(json.state != null &&  json.state!='' && json.state !='undefined'))){
							result=result+' , ';
						}
						
						
						if(json.country != null &&  json.country!='' && json.country !='undefined')
						{
						result=result+json.country+'</span><div class="clearfix"></div>';
						}
					
						}
						
					   else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client Country :</strong>';
						if(json.country != null && json.country !='' && json.country !='undefined'){
							result = result + '<span>'+json.country+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client State :</strong>';
						if(json.state != null && json.state !='' && json.state !='undefined'){
							result = result + '<span>'+json.state+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client City :</strong>';
						if(json.city != null && json.city !='' && json.city !='undefined'){
							result = result + '<span>'+json.city+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Client Zip Code :</strong>';
						if(json.zipcode != null && json.zipcode !='' && json.zipcode !='undefined'){
							result = result + '<span>'+json.zipcode+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Created By :</strong>';
						if(json.vendorCreateduserName != null && json.vendorCreateduserName !='' && json.vendorCreateduserName !='undefined'){
							result = result + '<span title='+json.vendorCreateduserName+'>'+json.vendorCreateduserName+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Street Address :</strong>';
						if(json.address != null && json.address !='' && json.address !='undefined'){
							result = result + '<span title="'+json.address+'">'+json.address+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Billing Address :</strong>';
						if(json.billingAddress != null && json.billingAddress !='' && json.billingAddress !='undefined'){
							result = result + '<span title="'+json.billingAddress+'">'+json.billingAddress+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Payment Terms :</strong>';
						if(json.paymentTerms != null && json.paymentTerms !='' && json.paymentTerms !='undefined' && json.paymentTerms != 'SelectPaymentTerms'){
							result = result + '<span title="'+json.paymentTerms+'">'+json.paymentTerms+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						result = result + '<div class="project_details_cta">';
						result = result + '<strong>Tier :</strong>';
						if(json.level != null && json.level !='' && json.level !='undefined'){
							result = result + '<span>'+json.level+'</span><div class="clearfix"></div>';
						}else{
							result = result + '<span> --- </span><div class="clearfix"></div>';
						}
						result = result + '</div>';
						
						if(json.clientCustomFieldlabelNameAndValue != null && json.clientCustomFieldlabelNameAndValue != "" && json.clientCustomFieldlabelNameAndValue != 'undefined'){
				              $.each(json.clientCustomFieldlabelNameAndValue, function(key, value) {
				            	  var labelNameAndlabelData = value.split("$");
				            	  result = result + '	<div class="project_details_cta">';
					            	  if(labelNameAndlabelData[1] != null && labelNameAndlabelData[1] != '' && labelNameAndlabelData[1] !='undefined'){
					            	  	result = result + '	<strong>'+labelNameAndlabelData[0]+':</strong><span class="fix">'+labelNameAndlabelData[1]+'</span><div class="clearfix"></div>';
					            	  }
				            	  result = result + '	</div>';
				              });
			              }
						
						
						
						result = result + '<div class="clearfix"></div>';
						result = result + ' <div class="user_summary_edit user_summary_edit_a" id="editClientDetailsDiv"></div>';
						result = result + '</div>';
						result = result + '<div class="clearfix"></div>';
						result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						
						$("#tab-4-addTemplate").empty();
			    		$("#tab-4").empty();
			    		$("#tab-5").empty();
			    		$("#tab-3").empty();
			    		
						$("#tab-1").empty();
						$("#tab-1").append(result);
						$("#tab-1").show();
						$("#testDiv").hide();
						
						$(".project-tabs-menu a").click(
								function(event) {
									event.preventDefault();
									$(this).parent().addClass("current");
									$(this).parent().siblings().removeClass(
											"current");
									var tab = $(this).attr("href");
									$(".project_tabs_inn").not(tab).css(
											"display", "none");
									$(tab).fadeIn();
								});
// 						msgFadeIn();

						$("#selector8123").fadeIn(200);
						$('#selector8123').delay(2000).fadeOut('slow');
					}
				});
	}
</script>
<!--  client details ends -->



<script type="text/javascript">
	function editClientDetails(clientLocationId,orgRelationID) {
		var vmsUrl = '${SourceLeadWeb_vmsUrl}';
		      $.ajax({
						type : "POST",
						url : vmsUrl+"/getClientAndVendorOrgDeatilsById?"+tokenParamName+"="+tokenValue+"&vendorLocationId="+clientLocationId+"&orgRelationId="+orgRelationID+"&requestType=Client",
						success : function(json) {
					//	var json = JSON.parse(data);
					
						var result = "";
						
					    var selected1 = '';
						var selected2 = '';
						var selected3 = '';
						var selected4 = '';
						var selected5 = '';
						
						var paymentTerms0 = '';
						var paymentTerms1 = '';
						var paymentTerms2 = '';
						var paymentTerms3 = '';
						var paymentTerms4 = '';
						var paymentTerms5 = '';
						var paymentTerms6 = '';
						
						var generateInvoice = '';
						var generateInvoice1 = '';
						
						<c:forEach var="section" items="${sourceleadSectionList}" varStatus="sectionOrder">
						  var sectionId = '${section.id}';
						  preapareClientCustomFieldDetails(clientLocationId,orgRelationID,sectionId);
						</c:forEach> 
						
						
						if(json.level == 'Tier-1'){
								selected1 = 'selected';
							} 
						
						if(json.level == 'Tier-2'){
								selected2 = 'selected';
							} 
						
						if(json.level == 'Tier-3'){
								selected3 = 'selected';
							}
						
						if(json.level == 'Tier-4'){
								selected4 = 'selected';
						}
						
						if(json.level == 'Tier-5'){
								selected5 = 'selected';
						}
						
					if(json.paymentTerms == '10 days'){
						paymentTerms1 = 'selected';
						} 
					
					if(json.paymentTerms == '15 days'){
						paymentTerms2 = 'selected';
						} 
					
					if(json.paymentTerms == '30 days'){
						paymentTerms3 = 'selected';
						}
					
					if(json.paymentTerms == '45 days'){
						paymentTerms4 = 'selected';
					}
					
					if(json.paymentTerms == '60 days'){
						paymentTerms5 = 'selected';
					}
					if(json.paymentTerms == '90 days'){
						paymentTerms6 = 'selected';
					}
					
					if(json.generateInvoice == 'Bulk'){
						generateInvoice = 'selected';
					}else if(json.generateInvoice == 'Individual'){
						generateInvoice1 = 'selected';
					} 
						
						 var country = '';
						 var state = '';
						 var city = '';
						 var website ='';
						 var zipCode = '';
						 var location = '';
						 var appendComa1='';
						 var appendComa2='';
						 
						if(json.country != null && json.country !='' && json.country !='undefined'){
							country = json.country;
						}
						if(json.state != null && json.state !='' && json.state !='undefined'){
							state = json.state;
						}
						if(json.city != null && json.city !='' && json.city !='undefined'){
							city = json.city;
						}
						if(json.webSite != null && json.webSite !='' && json.webSite !='null' && json.webSite !='undefined'){
							website = json.webSite;
						}
						if(json.zipcode != null && json.zipcode !='' && json.zipcode !='undefined'){
							zipCode = json.zipcode;
						}
						
						if(json.location != null && json.location !='' && json.location !='undefined'){
							location = json.location;
						}else if(json.country != null && json.country !='' && json.country !='undefined'){
							location = json.country;
						}
						
						result = result + ' <form id="editClient_form"  name="editClient_form" method="post">';
						if(json.orgType == 'Company'){
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Client Name<span class="text-danger">&nbsp; *</span> </label>';
								result = result + '<input type="text"  id="clientName" name="orgName" value="'+json.orgName+'" readOnly="readOnly" field-name="client name" placeholder="client name" >';
								result = result + '</div>';
								
								result = result + '<div class="user_summary_edit_cta" id="companyWebsiteDiv">';
								result = result + '<label>Website</label>';
								result = result + '<input type="text"  id="companyWebsite" name="webSite" field-name="Website"  value="'+website+'" readOnly="readOnly" placeholder="client website" ></div>';
								result = result + '</div>';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Address<span class="text-danger">&nbsp; *</span></label>';
								if((json.city != null &&  json.city!='' && json.city !='undefined')&&(json.state != null &&  json.state!='' && json.state !='undefined')){
									appendComa1=' , ';
								}
								if((json.country != null &&  json.country!='' && json.country !='undefined')&&((json.city != null &&  json.city!='' && json.city !='undefined')||(json.state != null &&  json.state!='' && json.state !='undefined'))){
									appendComa2=' , ';
								}
								result = result + ' <input type="text" class="form-control" name="location" id="existingLocation" readOnly="readOnly" value="'+city+appendComa1+state+appendComa2+country+'" readonly="readonly" field-name="Address" placeholder="Address" data-validation="required">';
// 								result = result + '<i class="fa fa-refresh btn btn-primary profilerefresh-icon" title="Refresh" onclick="refreshGoogleApiLocation(\'existingLocation\',\'existingCountry\',\'existingState\',\'existingCity\',\'existingZipCode\',\'addressId\');"></i>';
								result = result + '</div>';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Country</label>';
								result = result + '<input type="text" name="country" readonly="readonly" id="existingCountry" value="'+country+'" data-validation="required"  data-placeholder="Select Country" >';
								/* result = result + ' <select class="form-control chosen-select" name="country" id="existingCountry"  field-name="Country" data-placeholder="Select Country"  data-validation="required" >';
								result = result + '<option  '+ (country == "Australia" ? "selected" : '') +'>Australia</option>';
								result = result + '<option  '+ (country == "France" ? "selected" : '') +'>France</option>';
								result = result + '<option  '+ (country == "India" ? "selected" : '') +'>India</option>';
								result = result + '<option  '+ (country == "Singapore" ? "selected" : '') +'>Singapore</option>';
								result = result + '<option  '+ (country == "United Kingdom" ? "selected" : '') +'>United Kingdom</option>';
								result = result + '<option  '+ (country == "United States" ? "selected" : '') +'>United States</option>';
				                result = result + ' </select>'; */
								result = result + '</div>  ';
								
								result = result + '<div class="user_summary_edit_cta" id="stateDiv">';
								result = result + '<label>State</label>';
								result = result + '<input type="text" name="state" readonly="readonly" id="existingState" value="'+state+'" >';
								result = result + '</div> ';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>City</label>';
								result = result + '<input type="text" name="city" readonly="readonly" id="existingCity" value="'+city+'" >';
								result = result + '</div> ';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Zip Code</label>';
								result = result + '<input type="text"  class="chosen-select form-control" readonly="readonly" name="zipcode" id="existingZipCode" value="'+zipCode+'" field-name="Zip Code"  > ';
								result = result + '</div> ';
																							
						}else{
								result = result + ' <form id="editClient_form"  name="editClient_form" method="post">';
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Client Name<span class="text-danger">&nbsp; *</span> </label>';
								result = result + '<input type="text"  id="clientName" name="orgName" value="'+json.orgName+'" data-validation="required validate_Space validate_length length2-250" field-name="client name" placeholder="client name">';
								result = result + '</div>';
								
								result = result + '<div class="user_summary_edit_cta" id="companyWebsiteDiv">';
								result = result + '<label>Website</label>';
								result = result + '<input type="text"  id="companyWebsite" name="webSite" field-name="Website" data-validation="validate_Space validate_domain validate_length length2-100 " onblur="validateWebsite()" data-validation-optional="true"  value="'+website+'" placeholder="client website" ></div>';
								result = result + '</div>';
								
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Address<span class="text-danger">&nbsp; *</span></label>';

								if((json.city != null &&  json.city!='' && json.city !='undefined')&&(json.state != null &&  json.state!='' && json.state !='undefined')){
									appendComa1=' , ';
								}
								if((json.country != null &&  json.country!='' && json.country !='undefined')&&((json.city != null &&  json.city!='' && json.city !='undefined')||(json.state != null &&  json.state!='' && json.state !='undefined'))){
									appendComa2=' , ';
								}
								
								result = result + ' <input type="text" class="form-control" name="location" id="clientLocation" value="'+city+appendComa1+state+appendComa2+country+'" field-name="Address" placeholder="Address" data-validation="required">';

// 								result = result + '<i class="fa fa-refresh btn btn-primary profilerefresh-icon" title="Refresh" onclick="refreshGoogleApiLocation(\'clientLocation\',\'clientCountry\',\'clientState\',\'clientCity\',\'clientZipCode\',\'addressId\');"></i>';
								result = result + '</div>';
								
								
								result = result + '<div class="user_summary_edit_cta" id="errCountryMsg">';
								result = result + '<label>Country</label>';
								result = result + ' <select class="form-control chosen-select" name="country" id="clientCountry" field-name="Country" data-placeholder="Select Country" data-validation="required" onchange="changeCountry();">';
								result = result + '<option  '+ (country == "Australia" ? "selected" : '') +'>Australia</option>';
								result = result + '<option  '+ (country == "France" ? "selected" : '') +'>France</option>';
								result = result + '<option  '+ (country == "India" ? "selected" : '') +'>India</option>';
								result = result + '<option  '+ (country == "Singapore" ? "selected" : '') +'>Singapore</option>';
								result = result + '<option  '+ (country == "United Kingdom" ? "selected" : '') +'>United Kingdom</option>';
								result = result + '<option  '+ (country == "United States" ? "selected" : '') +'>United States</option>';
				                result = result + ' </select>';
								result = result + '</div>  ';
								
								result = result + '<div class="user_summary_edit_cta" id="stateDiv">';
								result = result + '<label>State</label>';
								result = result + '<input type="text"  class="form-control" name="state" value="'+state+'" id="clientState" field-name="State"  > ';
								result = result + '</div> ';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>City</label>';
								result = result + '<input type="text"  class="form-control" name="city" id="clientCity" value="'+city+'" field-name="City"  > ';
								result = result + '</div> ';
								
								result = result + '<div class="user_summary_edit_cta">';
								result = result + '<label>Zip Code</label>';
								result = result + '<input type="text"  class="chosen-select form-control" name="zipcode" id="clientZipCode" value="'+zipCode+'" field-name="Zip Code"  > ';
								result = result + '</div> ';
								
						}
						
						
						//level
                        result = result + ' <div class="user_summary_edit_cta">';
                        result = result + ' <label>Tier</label>';
                        result = result + ' <select id="level" name="level" >';
                        result = result + ' <option '+selected1+'>Tier-1</option>';
                        result = result + ' <option '+selected2+'>Tier-2</option>';
                        result = result + ' <option '+selected3+'>Tier-3</option>';
                        result = result + ' <option '+selected4+'>Tier-4</option>';
                        result = result + ' <option '+selected5+'>Tier-5</option>';
                        result = result + ' </select>';
                        result = result + ' </div>';   
                        
                        result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Street Address</label>';
						if(json.address != null && json.address !='' && json.address != 'undefined'){
						result = result + '<textarea id="addressId" name="address" rows="4" field-name="Address" class="form-control" data-validation="validate_length length2-250" data-validation-optional="true">'+json.address+'</textarea>';
						}else{
							result = result + '<textarea id="addressId" name="address" rows="4" field-name="Address" class="form-control" data-validation="validate_length length2-250" data-validation-optional="true"></textarea>';	
						}
						result = result + '</div>';
						
						result = result + '<div class="user_summary_edit_cta">';
						result = result + '<label>Billing Address</label>';
						if(json.billingAddress != null && json.billingAddress !='' && json.billingAddress != 'undefined'){
						result = result + '<textarea id="addressId" name="billingAddress" rows="4" field-name="Address" class="form-control" data-validation="validate_length length2-250" data-validation-optional="true">'+json.billingAddress+'</textarea>';
						}else{
							result = result + '<textarea id="addressId" name="billingAddress" rows="4" field-name="Address" class="form-control" data-validation="validate_length length2-250" data-validation-optional="true"></textarea>';	
						}
						result = result + '</div>';
						
						result = result + ' <div class="user_summary_edit_cta">';
                        result = result + ' <label>Payment Terms</label>';
                        result = result + ' <select id="paymentTermsId" name="paymentTerms" >';
                        result = result + ' <option value="">SelectPaymentTerms</option>';
                        result = result + ' <option '+paymentTerms1+'>10 days</option>';
                        result = result + ' <option '+paymentTerms2+'>15 days</option>';
                        result = result + ' <option '+paymentTerms3+'>30 days</option>';
                        result = result + ' <option '+paymentTerms4+'>45 days</option>';
                        result = result + ' <option '+paymentTerms5+'>60 days</option>';
                        result = result + ' <option '+paymentTerms6+'>90 days</option>';
                        result = result + ' </select>';
                        result = result + ' </div>'; 
                        
                        result = result + ' <div class="user_summary_edit_cta">';
                        result = result + ' <label>Generate Invoice</label>';
                        result = result + ' <select id="generateInvoice" name="generateInvoice" >';
                        result = result + ' <option '+generateInvoice+'>Bulk</option>';
                        result = result + ' <option '+generateInvoice1+'>Individual</option>';
                        result = result + ' </select>';
                        result = result + ' </div>';
						
                        result = result + ' <div class="user_summary_edit_cta NewStyleFullWidth"  id="customFieldDataId"></div>';
                        
                        
						result = result + '<div class="clearfix"></div>';
						result = result + '<div class="user_summary_edit_cta pull-right">';
						result = result + '<input class="us-btn" id="save_client" type="button" value="Save" onclick="storeClientDetails('+orgRelationID+','+clientLocationId+')"/> <input class="us-btn us-btn_a" type="button" value="Cancel" onclick="clientDetails('+clientLocationId+','+orgRelationID+')"/>';
						result = result + '</div>';		
						result = result + '<div class="clearfix"></div>';
						result = result + ' </form>';
						
						$(".user_summary_edit").slideToggle(100);
						$("#editClientDetailsDiv").empty();
				        $("#editClientDetailsDiv").append(result);
// 				        initLocationAuto('clientLocation','clientCountry','clientState','clientCity','clientZipCode','clientLatitude','clientLongitude','invite','addressId');
// 				        zipcodeMask(country,'clientZipCode');
				        $(function(){
				            var config = {
				        		  '.chosen-select'           : {},
				        		  '.chosen-select-deselect'  : {allow_single_deselect:true},
				        		  '.chosen-select-no-single' : {disable_search_threshold:10},
				        		  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
				        		  '.chosen-select-width'     : {width:"95%"}
				        		}
				            
				            $("#clientCountry").chosen();
 
				           
				            $('.chosen-select').each(function() {
				        			var $this = $(this);
				        			$this.next().css({'width': '100%'});
				        	  })
				        });
					}
				});
		      getAllCountries();
		      
		      
	}
	
	function changeCountry(){
		 $("#errCountryMsg").find("p.jquery_form_error_message").remove();
	}
</script>



<script type="text/javascript">
function preapareClientCustomFieldDetails(clientLocationId,orgRelationID,sectionId){
	var sourceleadUrl = '${sourceleadUrl}';
	$.ajax({
        type : "POST",
         url: sourceleadUrl+"/getAllASectionCustomFields?&clientLocationId="+clientLocationId+"&orgRelationID="+orgRelationID+"&requestFrom=clientAndVendor"+"&sectionId="+sectionId,        
         success :function(data) {
        	 $('#customFieldDataId').empty();
        	 $('#customFieldDataId').append(data);
           },
     }); 
}
</script>


<script type="text/javascript">
	function storeClientDetails(orgRelationId,clientLocationId) {
         if ($('#editClient_form').validate(false, validationSettings)){
            // var formData = getFormData("editClient_form");
            //for custom fields changed serilized method.
             var formData = $($('#editClient_form')[0]).serialize();
             var vmsUrl = '${SourceLeadWeb_vmsUrl}';
		 $.post(vmsUrl+"/clientDetailsUpdateUsingId?"+tokenParamName+"="+tokenValue+"&vendorLocationId="+clientLocationId+"&orgRelationId="+orgRelationId, formData, function(data) {
			 clientDetails(clientLocationId,orgRelationId);
			 
			 //location.replace(vmsUrl+ "/clientDashboard");
			});
	     	return true;
         }else{
             return false;
           }
		
    }
	$('body').on('blur', '#editClient_form', function() {
        $("#editClient_form").showHelpOnFocus();
        $("#editClient_form").validateOnBlur(false, validationSettings);  
    });
</script>
<script>
function clientEditMsgFadein(){
  $("#selector8123").fadeIn(200);
  $('#selector8123').delay(2000).fadeOut('slow');
}
  </script> 
<script type="text/javascript">
	function validateWebsite() {
		var website = document.getElementById("companyWebsite").value;
		 var vmsUrl = '${SourceLeadWeb_vmsUrl}';
		if(website.match(/^[^\s-](.*)*$/) != null){
		var sourceleadBLockedwebsites = '${sourceLeadBlockedSocialDomains}';
		$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
		$("#save_client").attr("disabled", "disabled");
		
		if (website != null && website != '') {
			//$("#save_client").attr("disabled", "disabled");
			// $( "#save_client" ).addClass('button').val('Processing..');
			$.ajax({
				type : "GET",
				url : vmsUrl + "/validateClientWebsite?website="+ website + "&sourceleadBLockedwebsites="+ sourceleadBLockedwebsites,
				success : function(data) {
					var result = data;
					if (result == "Website Duplicated" || result == "Invalid Website") {
						//existed website
						$("input#companyWebsite").after("<p class='jquery_form_error_message'>"+ website+ " is "+result+"</span>");
						document.getElementById("companyWebsite").setAttribute('record-exist', 'yes');
						document.getElementById("companyWebsite").setAttribute('record-exist-errorMsg',website + ' is '+result);
						$('input#companyWebsite').removeAttr( "record-exist record-exist-errormsg");
						
					} else {
						//New WebSite class="form-control"
						 $("#save_client").removeAttr("disabled");
						$('input#companyWebsite').removeAttr("class record-exist record-exist-errormsg");
						$('input#companyWebsite').attr('class','valid form-control');
						$('#companyWebsiteDiv').find('p.jquery_form_error_message').remove();
				
					}
				},

			});
		  }
		}
	}
</script>

<script type="text/javascript">
 function validateClientEmail() {
	 var EmailId = document.getElementById("EmailId").value; 
	 $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
	 var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	 if(EmailId != null && EmailId != ""){
		 $.ajax({
		    	type: "POST",
		        url: vmsUrl+"/getClientEmailVerification?"+tokenParamName+"="+tokenValue+"&EmailId="+EmailId, 
		        success: function(data) {
		        	if(data == 'true'){
		        		  $('#EmailIdDiv').find('p.jquery_form_error_message').remove();
						  $("input#EmailId").after("<p class='jquery_form_error_message'>"+EmailId+" is Already Existed</span>");
						  document.getElementById("EmailId").setAttribute('record-exist','yes');
						  document.getElementById("EmailId").setAttribute('record-exist-errorMsg',EmailId+' is Already Existed'); 
		        	}
		        	else{
		        		 $('input#EmailId').removeAttr( "record-exist record-exist-errormsg");
		        		 $('#EmailIdDiv').find('p.jquery_form_error_message').remove(); 
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
function getAllCountries(){
    setTimeout(function(){ 
		var countryName = $("#existingCountry").val();
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/sourceLeadCountryAutoComplete?"+tokenParamName+"="+tokenValue+"",
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#country').empty();
	        	$('#country').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == countryName){
	            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}else if(json[i] == '${loginCountryName}'){
	            		$('#country').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#country').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            	
	            }
	            
	            $('#country').trigger("chosen:updated");
	            getAllStates();
	        },
	        error: function(e){
	        }
	    });
    }, 100);
} 
</script>


<script>
function getAllStates(){
	var countryName = $("#country").val();
	
	//to set existing state value(Auto populate)
	var stateName = $("#state").val();

	if(stateName == null || stateName == 'undefined' || stateName == ''){
		stateName = document.getElementById("existingState").value;
	}
	//end state Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == stateName){
	            		$('#state').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#state').trigger("chosen:updated");
	            getAllCities();
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 


<script>
function getAllCities(){
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	//to set existing city value(Auto populate)
	var cityName = $("#city").val();
	if(cityName == null || cityName == 'undefined' || cityName == ''){
		cityName = document.getElementById("existingCity").value;
	}
	//end city Auto populate
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select---</option>');
	            for(var i=0;i<json.length;i++){
	            	if(json[i] == cityName){
	            		$('#city').append('<option value="'+json[i]+'" selected>'+json[i]+'</option>');
	            	}
	            	else{ 
	            		$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            	} 
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>

<!-- Show success message after form submission script -->
<script>
	function msgFadeIn() {
	    setTimeout(function(){ 
	    	$('#selector1').fadeOut('slow'); }, 500);
	}
</script> 
<script>
  $("#selector1").fadeIn(200);
  $('#selector1').delay(2000).fadeOut('slow');
</script> 




<!-- End -->
