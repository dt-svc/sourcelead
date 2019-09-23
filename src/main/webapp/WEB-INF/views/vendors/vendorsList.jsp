<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

    <!-- Content Area -->
      <section class="content-wraper">
        <section class="content-header clearfix">
        <div id="msgDiv" ></div>
		<c:if test="${!empty msg}">
	        <div class="success_pop" id="selector" style="display: none;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
	     <div class="success_pop" id="venselector" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="venmsgDiv"></span>
       </div>
       <div id="loading"></div>
          <div class="pull-left">
            <h2>Vendors<span class="select-fild">
                	<select id="listOfVendors" onchange="listChange(this);">
                  		 <option value="listbyVendor" selected="selected">List by vendor</option>
                         <option value="listbyContact" >List by contact</option> 
                    </select>
                </span>            	
            </h2>
          </div>
            <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home &raquo;</a> Vendors List </div>
        
        </section>
		<!-- Project -->
		 <div id="loading"></div>


<div class="timesheet_out timesheet_background">

  <div class="clearfix">
          
    <!-- Advanced Search -->       
          <div class="cand-advsrch-popup advancedsearch-popup">
          <h5 class="headerAdsrch">Advanced Search <a href="javascript:void(0)" class="close-div" onClick="closePop();"><i class="fa fa-close"></i></a></h5>
          <form:form id="vendorList_form" name="vendorList_form"  enctype='multipart/form-data' >
          <div class="addInner"> 
            <div class="row">
              <div class="col-md-4">
                <label>Any of Keyword</label>
	                 <div class="clearfix">
					        <select class="chosen-select form-control input-sm" multiple data-placeholder="Any Keyword" id="anyOfKeyword" name="anyOfKeyword">
	                        </select>
				    </div>
              </div>
              <div class="col-md-4">
                <label>All Keywords</label>
                <div class="clearfix">
                  <div class="clearfix">
					  	 <select class="chosen-select form-control input-sm" multiple data-placeholder="All Keywords" id="allKeywords" name="allKeywords">
	                     </select>
				 </div>
                </div>
              </div>
              <div class="col-md-4">
                <label>Exclude Keyword</label>
                <div class="clearfix">
				  	 <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Keyword" id="excludeKeyWord" name="excludeKeyWord">
                     </select>
	            </div>
              </div>
            </div>
            <div class="row">
            <div id="vendorListAdvanced">
              <div class="col-md-12">
              
                <h4>Details:</h4>
              </div>
              <div class="clearfix">
                <div class="col-md-4">
                  <label>Vendor Name</label>
                  <div class="clearfix">
                    <select class="chosen-select form-control input-sm" multiple data-placeholder="Vendor Name" id="orgNameAdvanced" name="orgNameAdvanced">
                        <c:forEach var="vendorList" items="${vendor_list}">
                                <option value="${vendorList.vendorNameForList}">${vendorList.vendorNameForList}</option>
                         </c:forEach>
                    </select>
                  </div>
                </div>
                
                <div class="col-md-4">
                  <label>Tier</label>
                  <div class="clearfix">
                    <select class="chosen-select" data-placeholder="Tier" name="tierAdvanced" id="tierAdvanced"  multiple>
	                     <option value="Tier-1">Tier-1</option>
	                     <option value="Tier-2">Tier-2</option>
	                     <option value="Tier-3">Tier-3</option>
	                     <option value="Tier-4">Tier-4</option>
	                     <option value="Tier-5">Tier-5</option>
                    </select>
                  </div>
                </div>
                
                <div class="clearfix">
                <div class="col-md-4">
                  <label>Created By</label>
                  <div class="clearfix">
                     <select class="chosen-select form-control input-sm" multiple data-placeholder="Created By" id="createdByAdvanced" name="createdByAdvanced"></select>
                  </div>
                </div>
                
              </div>
             
               </div>
               
              <div class="clearfix">
                
              </div>
              <div class="clearfix">
                <div class="col-md-4">
                  <label>Country </label>
                  <div class="clearfix">
                    <select class="chosen-select" name="searchCountry" id="country" field-name="Country">
                          <option value="" selected>---select country---</option>
						     <c:forEach var="cntry" items="${countryList}">
					                     <option value="${cntry.name}">${cntry.name}</option>
 
				             </c:forEach>
				     </select>
                  </div>
                </div>
             <div class="col-md-4"> 
							  <label>State</label>
							  <div class="clearfix">
								  <select  class="chosen-select" name="searchState" id="state" field-name="State"  >
								         <option value="">---select state---</option>
								       <%-- <c:forEach var="state" items="${statesList}">
							                     <option value="${state.stateName}">${state.stateName}</option>
						                </c:forEach> --%>
                                 </select>
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>City</label>
							  <div class="clearfix">
								  <select  class="chosen-select" name="searchCity" id="city" field-name="City"  >
								      <option value="">---select city---</option>
                                  </select>
							  </div>
						  </div>
              </div>
              
                
                <div class="clearfix">
               
                 </div>
             </div> 
              <div id="vendorContactListAdvanced">   
               <div class="col-md-12">
                <h4>Vendor Contact:</h4>
              </div>
              <div class="clearfix">
                <div class="col-md-4">
                  <label>Contact Name</label>
                  <div class="clearfix">
                 	  <select class="chosen-select form-control input-sm" multiple data-placeholder="Contact Name" id="contactNameAdvanced" name="contactNameAdvanced">
                      </select>
                      
                  </div>
                </div>
                
                <div class="col-md-4">
                  <label>Email</label>
                  <div class="clearfix">
                        <select class="chosen-select form-control input-sm" multiple data-placeholder="Email" id="emailAdvanced" name="emailAdvanced">
                        </select>
                  </div>
                </div>
                
                 <div class="col-md-4">
                  <label>Phone Number</label>
                  <div class="clearfix">
                      <select class="chosen-select form-control input-sm" multiple data-placeholder="Phone Number" id="phoneNumberAdvanced" name="phoneNumberAdvanced">
                     </select>
                  </div>
                </div>
                
              </div>
              <div class="clearfix">
                
              </div>
                
                 <div class="clearfix">
                <div class="col-md-4">
                  <label>Owner</label>
                  <div class="clearfix">
                     <select class="chosen-select form-control input-sm" multiple data-placeholder="Owner" id="ownerAdvanced" name="ownerAdvanced">
                     </select>
                  </div>
                </div>
                
                <div class="col-md-4">
                  <label>Vendor</label>
                  <div class="clearfix">
                     <select class="chosen-select form-control input-sm" multiple data-placeholder="Vendor" id="orgAdvanced" name="orgAdvanced">
                        <c:forEach var="vendor" items="${vendor_list}">
                                <option value="${vendor.vendorNameForList}">${vendor.vendorNameForList}</option>
                         </c:forEach>
                    </select>
                  </div>
                </div>
                
               	<div class="col-md-4">
						<label>Status</label>
								<div class="clearfix">
										<select class="chosen-select form-control input-sm" multiple data-placeholder="Status" id="statusAdvanced" name="statusAdvanced">
											<option value="Active">Active</option>
											<option value="In-Active">In-Active</option>
											<!-- <option value="New">New</option>
											<option value="Negotiation">Negotiation</option>
											<option value="Contract">Contract</option> -->
									</select>
						 </div>  
              	</div>
				</div>
             
                <div class="col-md-4">
                  <label>Invitation</label>
                  <div class="clearfix">
                		 <select class="chosen-select form-control input-sm" multiple data-placeholder="Invitation" id="invitationAdvanced" name="invitationAdvanced">
											<option value="Sent">Resend</option>
											<option value="New">send</option>
											<option value="Accepted">Accepted</option>
											<option value="Rejected">Rejected</option>
						</select>
                  </div>
                </div>
            </div>  
              
            </div>
            </div>
            <div class="advancedsearch-footer clearfix">
              <div class="pull-right mr"> 
               <!--  <input type="button" value="Search" id="search_vendor" onclick="getVendorList();" class="btn btn-sm btn-success"/>
                 <input type="button" value="Clear" id="clear_search" onclick="clearSearch();" class="btn btn-sm btn-primary"/> -->
              <div id="enableVendorAdvancedSearch"> 
              	<input type="button" value="Search" id="search_client" onclick="getVendorList();" class="btn btn-sm btn-success"/>
              	<input type="button" value="Reset" id="clear_search" onclick="clearSearch();" class="btn btn-sm btn-primary"/> 
              	</div>
              	<div id="enableVendorContactAdvancedSearch"> 
              	<input type="button" value="Search" id="search_client" onclick="getVendorContactList();" class="btn btn-sm btn-success"/>
              	<input type="button" value="Reset" id="clear_search" onclick="clearSearch();" class="btn btn-sm btn-primary"/> 
              	</div>
              
            </div>
            </div>
            </form:form>
          </div>
<!-- Advanced Search Ends -->

<div class="vendor_wrap_shadownone minHeight_225">

<div class="positionRelative reportHead clearfix">
      <span class="pull-left refreIcon" id="advanceSearchDiv">
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendor" title="Refresh" onclick="resetAdvSearchFilter();"></a>
      		<a href="javascript:void(0)" class="refeIcon" id="resetVendorContact" style="display: none;" title="Refresh" onclick="resetVendorContactFilter();"></a>
      		<a href="javascript:void(0)" class="newAdBtn pull-left" title="Advanced Search" onClick="openPop();loadChosenScripts();"> Advanced Search &nbsp; <i class="fa fa-search"></i> </a>
      </span>
      
      <ul class="pull-right list-unstyled list-inline">
      
            <li>
				<a class="action newAdBtn mr5" onclick="getActions();" href="javascript:void(0)">+ Add <i class="fa fa-caret-down"></i></a>
				<div class="action-pop">
					<span> 					
						   <sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR')"> 
                       			<a href="<c:url value='${appPath}/addVendor'/>">Add Vendor</a>	
                           </sec:authorize>
						   <sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR_CONTACT')">
					         <a href="<c:url value='${appPath}/addVendorContact'/>">Add Contact</a>
						  </sec:authorize>	
		               		 <sec:authorize access="hasAnyRole('ROLE_VENDOR_BULK_IMPORTS','ROLE_VENDOR_CONTACT_BULK_IMPORTS')">
		                 		<a href="<c:url value='${appPath}/uploadVendorForm'/>">Import</a>
		           	   		</sec:authorize> 						  				
					</span>
				</div>
			</li>
				<li id="vendorFiletPopup"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="vendorFileterPopUp();"></a></li>
				<li id="vendorContactFiletPopup" style="display: none;"><a href="javascript:void(0)" class="filtIcon" title="Filters" onclick="vendorContactFilterPopUp();"></a></li>
				
				<li><a href="javascript:void(0)" class="overIcon" title="Actions" onclick="newActions();"></a>
					<div class="newActPop">
						<span>
	                	<sec:authorize access="hasRole('ROLE_EXPORT_VENDORS')"> 
							 <div id="vendorImports">
							 	<a href="javascript:void(0)" onclick="exportVendorToPdf();" title="PDF">Export PDF</a>
								<a href="javascript:void(0)" onclick="exportVendorToExcel();" title="Excel">Export Excel</a>
							 </div>                 
			          	</sec:authorize>
		           
		         		 <sec:authorize access="hasRole('ROLE_EXPORT_VENDOR_CONTACTS')"> 
							 <div id="VendorContactImports" style="display:none">
							 	<a href="javascript:void(0)" onclick="exportVendorContactToPdf();" title="PDF">Export PDF</a>
								<a href="javascript:void(0)" onclick="exportVendorContactToExcel();" title="Excel">Export Excel</a>
							 </div>			          
		          		</sec:authorize>	                    
						</span>
					</div>
				</li>
		    
		  <li class=""><input type="text" class="repSearchBar" placeholder='Search' id='dataTableSearch'/><span class="repSearchIcon" onClick="getVendorList();"></span></li>
        </ul>
      </div>		
        
    <div class="clients_wrap_table datatable_filter">
	    <input type="hidden" name="emptyVendorcontactList" id="emptyVendorcontactList">
	    <input type="hidden" name="emptyVendorList" id="emptyVendorList">
    
    	<table id="vendorList" class="display" cellspacing="0" width="100%"> </table>
    	<table id="vendorContactList" class="display" cellspacing="0" width="100%"> </table> 
   </div>	
   
</div>

<div class="side_mask" style="z-index:10000;"><div class="proj_close" onclick="refresh();"></div></div>

<!-- Client Side Nav Div -->
<div class="clients_side_wrap clientspagedrop">
			<div class="clients_side_wrap_inn">
				<h1 id="vendorNameDiv"></h1>
				<div class="proj_close">
					<i></i>
				</div>
				<div id="vendorSideDiv"></div>
			</div>
		</div>

 </div>
 
</div>
<!-- Client Side Nav Div Ends -->
</section>

 <script type="text/javascript">	
	function clearSearchKeys(){
	 
	    $("#tierAdvanced").val('').trigger("chosen:updated");
	    $("#phoneNumberAdvanced").val('').trigger("chosen:updated");
	    $("#ownerAdvanced").val('').trigger("chosen:updated");
	    $("#clientsAdvanced").val('').trigger("chosen:updated");
	    $("#orgAdvanced").val('').trigger("chosen:updated");
	    $("#statusAdvanced").val('').trigger("chosen:updated");
	    $("#invitationAdvanced").val('').trigger("chosen:updated");
	    $("#contactNameAdvanced").val('').trigger("chosen:updated");
	    $("#emailAdvanced").val('').trigger("chosen:updated");
	    $("#orgNameAdvanced").val('').trigger("chosen:updated");
	    $("#createdByAdvanced").val('').trigger("chosen:updated");
		   /*  $("#country").val('').trigger("chosen:updated"); */
		    $("#state").val('').trigger("chosen:updated");
		    $("#city").val('').trigger("chosen:updated");
	}
</script>




<script>
function clearSearch(){
	document.getElementById("vendorList_form").reset();
	$('#anyOfKeyword').val('').trigger('chosen:updated');
	$('#allKeywords').val('').trigger('chosen:updated');
	$('#excludeKeyWord').val('').trigger('chosen:updated');
	$('#orgNameAdvanced').val('').trigger('chosen:updated');
 	$('#country').val('').trigger('chosen:updated'); 
	//$('#state').val('').trigger('chosen:updated');
	 getStates();
	//$('#city').val('').trigger('chosen:updated');
	 getCities();
	$('#tierAdvanced').val('').trigger('chosen:updated');
	$('#createdByAdvanced').val('').trigger('chosen:updated');
	$('#emailAdvanced').val('').trigger('chosen:updated');
	$('#ownerAdvanced').val('').trigger('chosen:updated');
	$('#orgAdvanced').val('').trigger('chosen:updated');
	$('#statusAdvanced').val('').trigger('chosen:updated');
	$('#invitationAdvanced').val('').trigger('chosen:updated');
	$('#contactNameAdvanced').val('').trigger('chosen:updated');
	$('#phoneNumberAdvanced').val('').trigger('chosen:updated');
}
</script>




<!-- <script type="text/javascript">
	function searchVendor() {
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		$("#search_vendor").addClass('button').val('Search');
		$("#search_vendor").removeAttr("disabled");
		var responderType = "Vendor";
		 if ($('#vendorList_form').validate(false, validationSettings)){ 
			
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#vendorList_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#vendorList_form')[0];
      		var formData = new FormData(form); 
      		
			//It will escape all the special characters
			$("#search_vendor").attr("disabled", "disabled");
			$("#search_vendor").addClass('button').val('Processing..');
			
			 var selectedList = document.getElementById('listOfVendors').value;
			if(selectedList == "listbyVendor" ){
				$.ajax({
					type : "POST",
	        	    data: formData,
	        	    contentType : 'application/json; charset=utf-8',
	        	    enctype: 'multipart/form-data',
	    	        processData: false,
	    	        contentType:false,
	        	    dataType : 'json',
					url : sourceleadUrl+"/searchVendorThroughElasticSearch?"+tokenParamName+"="+tokenValue+"&responderType="+responderType+"&json="+jsonData,
					success : function(data) {
						$("#search_vendor").addClass('button').val('Search');
						$("#search_vendor").removeAttr("disabled");
						closePop();
						clearSearchKeys();
						//prepareCandidateList(data);
						preparedVendorsListFromElk(data);
					},
				}); 
		      return true;
			}else if(selectedList == "listbyContact"){
				
				$.ajax({
					type : "POST",
	        	    data: formData,
	        	    contentType : 'application/json; charset=utf-8',
	        	    enctype: 'multipart/form-data',
	    	        processData: false,
	    	        contentType:false,
	        	    dataType : 'json',
					url : sourceleadUrl+"/searchVendorContactsThroughElasticSearch?"+tokenParamName+"="+tokenValue+"&responderType="+responderType+"&json="+jsonData,
					success : function(data) {
						$("#search_vendor").addClass('button').val('Search');
						$("#search_vendor").removeAttr("disabled");
						document.getElementById("vendorList_form").reset();
						closePop();
						clearSearchKeys();
						preparedVendorContactListFromElk(data);
					},
				}); 
		     return true;
			} 
			
		 }else{ 
				return false;
		 } 
	} 
	
	$('body').on('blur', '#vendorList_form', function() {
		$("#vendorList_form").showHelpOnFocus();
		$("#vendorList_form").validateOnBlur(false, validationSettings);
	});
</script> -->



<script>
  $(function() { 
	       //Adding Customized Any KeyWord Title list this method is slead-common.js
		   tagIt("anyOfKeyword", 'Press Enter-Key Or Comma(,) Or Tab  To Add Keyword:', 'tagItBykeyForSkills','skills','Any Keyword');
	       
		 //Adding Customized All KeyWord  list this method is slead-common.js
		   tagIt("allKeywords", 'Press Enter-Key Or Comma(,) Or Tab  To Add Keyword:', 'tagItBykeyForSkills','skills','All Keywords');
		 
		 //Adding Customized All KeyWord  list this method is slead-common.js
		   tagIt("excludeKeyWord", 'Press Enter-Key Or Comma(,) Or Tab  To Add Keyword:', 'tagItBykeyForSkills','skills','Exclude Keyword');
		 
		 //Adding Customized Vendor Name list this method is slead-common.js
		   tagIt("orgNameAdvanced", 'Press Enter-Key Or Comma(,) Or Tab  To Add Vendor Name:', 'tagItBykeyForSkills','skills','Vendor Name');
		 
		 //Adding Customized createdBy Name list this method is slead-common.js
		   tagIt("createdByAdvanced", 'Press Enter-Key Or Comma(,) Or Tab To Add Document Type:', 'tagItBykeyForSkills','skills','Created By');
		 
		 
		 //vendor contact customize details
		 
		  //Adding Customized vendor contact Name list this method is slead-common.js
		   tagIt("contactNameAdvanced", 'Press Enter-Key Or Comma(,) Or Tab To Add Document Type:', 'tagItBykeyForSkills','skills','Contact Name');
		 
		   //Email
		   tagIt("emailAdvanced", 'Press Enter-Key Or Comma(,) To Add Email:', 'tagItBykeyForSkills','skills','Email');
		   
		   //Phone Number
		   tagIt("phoneNumberAdvanced", 'Press Enter-Key Or Comma(,) To Add Phone Number:', 'tagItBykeyForSkills','skills','Phone Number');
		   
		 //Owner Name
		   tagIt("ownerAdvanced", 'Press Enter-Key Or Comma(,) To Add Owner:', 'tagItBykeyForSkills','skills','Owner');
		   
		   //Owner Name
		   tagIt("orgAdvanced", 'Press Enter-Key Or Comma(,) To Add Vendor:', 'tagItBykeyForSkills','skills','Vendor');
		   
  });
  </script>
  
  	       
<script type="text/javascript">
     $("#country").on('change', function (e) {
  	   getStates();
     });
     $("#state").on('change', function (e) {
  	   getCities();
     });
				 
</script> 



<script> 
$(document).ready(function(){
	//getVendorContactLists();
	loadVendorDatatable("${searchValue}","${universalVendorList}","${searchType}","${responderLocationId}","${orgRelationId}","${orgName}","${vendorKeyword}");
// 	 vendorListCheck("${searchValue}","${universalVendorList}","${searchType}","${responderLocationId}","${orgRelationId}","${orgName}","${vendorKeyword}");
//     listChange("listbyVendor");
// 	document.getElementById('listOfVendors').value = 'listbyVendor';
});
</script>

<!-- PLEASE DO NOT DELETE COMMENTED SCRIPTS, IF YOU HAVE ANY QUERIES CONTACT RAJASHEKAR -->

<!-- Vendors list preparation from ELK result -->
<!-- <script>
function preparedVendorsListFromElk(data){
	var json = JSON.parse(data);
		   	var result = "";
		   	result = result +'<thead>';
		   	result = result +'<tr>';
		   	result = result +' <th>Vendor Name</th>';
		   	result = result +' <th>Website</th>';
		   	result = result +'<th>Location</th>';
		   	result = result +'<th>Tier</th>';
		 	result = result +'<th>Created By</th>';
		   	result = result +'<th>Released Jobs</th>';
		   	result = result +'<th>No.of Contacts</th>';
		   	/* result = result +'<th>Actions</th>'; */
		   	result = result +'</tr>';
		   	result = result +'</thead>';
		   	result = result +'<tbody>';
		   	
		   	for (var i=0; i<json.length; i++)
				{  
		   		
		   		var report = json[i];
		   		
		   		
		   		
		   		var location = "";
		   		if(report.city != null && report.city != '' && report.city != 'undefined'){
		   			location = report.city+", "+report.state+", "+report.country;
		   		}else if(report.state != null && report.state != '' && report.state != 'undefined'){
		   			location = report.state+", "+report.country;
		   		}else{
		   			location = report.country;
		   		}
		   		
		   		var pattern = new RegExp(/["'"]/g);
		   		var orgName = (report.orgName).replace(pattern, "\\'");
		   		
		   		result = result +'<tr>';
		   		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR\",\"ROLE_VIEW_EDIT_VENDOR\")">';
		   			result = result +'<td>';
		   			if(report.orgRelationId !=null && report.orgRelationId !='' && report.orgRelationId !='undefined'){
		   			result = result +'<div class="proj_show" onclick="vendorSideBar(\''+report.responderLocationId+'\',\''+report.orgRelationId+'\',\''+orgName+'\');"><span class="tbldata-dotdot-me pull-left" title="'+report.orgName+'">'+report.orgName+'</span><i class="fa fa-binoculars pull-right"></i>';
		   			}else{
		   				result = result +'<div class="proj_show" onclick="vendorSideBar(\''+report.responderLocationId.id+'\',\''+report.id+'\',\''+orgName+'\');"><span class="tbldata-dotdot-me pull-left" title="'+report.orgName+'">'+report.orgName+'</span><i class="fa fa-binoculars pull-right"></i>';
		   			}
		   			result = result +'</div>';
		   			result = result +'</td>';
		   		'</sec:authorize>';
		   		
		   		'<sec:authorize access="!hasAnyRole(\"ROLE_VIEW_VENDOR\",\"ROLE_VIEW_EDIT_VENDOR\")">';
					result = result +'<td>';
					result = result +'<div class="proj_show"><span class="tbldata-dotdot-me" title="${report.orgName}">'+report.orgName+'</span>';
					result = result +'</div>';
					result = result +'</td>';
				'</sec:authorize>';
				
				
		   		if( report.website != null && report.website != '' && report.website != 'undefined'){
		   			result = result +'<td>';
		   			result = result +'<div>';
		   			result = result +'<span class="tbldata-dotdot-vsm" title='+report.website+'>'+report.website+'</span>';
		   			result = result +'</div>';
		   			result = result +'</td>';
		   		}else{
		   			result = result +'<td><div>--</div></td>';
		   		}
		   		
		   		if(location != null && location  != '' && location  != 'undefined'){
		   			result = result +'<td>';
		   			result = result +'<div>';
		   			result = result +'<span class="tbldata-dotdot" title="'+location+'">'+location+'</span>';
		   			result = result +'</div>';
		   			result = result +'</td>';
		   		}else{
		   			result = result +'<td><div>--</div></td>';
		   		}
				
		   	    result = result +' <td><div><span class="tbldata-dotdot_80" title="'+report.initiatorLevel+'">'+report.initiatorLevel+'</span>';
		   	    
		   	 if(null != report.createdBy && report.createdBy  != '' && report.createdBy  != 'undefined'){
					result = result +' <td><div class="tbldata-dotdot-vsm">'+report.createdBy+'</div></td>';
			   	}else{
			   		result = result +'<td><div>---</div></td>';
			   	}
				result = result +' <td><div id="releasedJobsCount'+report.orgRelationId+'" class="tbldata-dotdot_80">---</div></td>';
				
				if(null != report.noContacts && report.noContacts  != '' && report.noContacts  != 'undefined'){
					result = result +'<td><div class="tbldata-dotdot_80">'+report.noContacts+'</div></td>';
				}else{
					result = result +'<td><div >---</div></td>';
				}
				
				/* result = result +' <td><div><i class="glyphicon glyphicon-trash"></i></div></td>'; */
		   		result = result +'</tr>';
				}
		   	
		    	result = result +'</tbody>';
			    $("#vendorList").empty();  
			    $("#vendorList").append(result)
			    for (var i=0; i<json.length; i++)
				{  
		   		
		   		var report = json[i];
		   		
			    releasedJobsCountByOrgRelationId(report.orgRelationId);
			    
				}
			    $("#emptyVendorList").val(json.length)
			    pagination123456();
}
</script> -->

<!-- <script>
function preparedVendorsLists(data){
	
			var json = JSON.parse(data);
		   	var result = "";
		   	
		   	result = result +'<thead>';
		   	result = result +'<tr>';
		   	result = result +' <th>Vendor Name</th>';
		   	result = result +' <th>Website</th>';
		   	result = result +'<th>Location</th>';
		   	result = result +'<th>Tier</th>';
		   	result = result +'<th>Released Jobs</th>';
		   	result = result +'<th>No.of Contacts</th>';
		   	/* result = result +'<th>Actions</th>'; */
		   	result = result +'</tr>';
		   	result = result +'</thead>';
		   	result = result +'<tbody>';
		   	
		   	for (var i=0; i<json.length; i++)
				{  
		   		
		   		var report = json[i];
		   		
		   		var location = "";
		   		if(report.city != null && report.city != '' && report.city != 'undefined'){
		   			location = report.city+", "+report.state+", "+report.country;
		   		}else if(report.state != null && report.state != '' && report.state != 'undefined'){
		   			location = report.state+", "+report.country;
		   		}else{
		   			location = report.country;
		   		}
		   		
		   		var pattern = new RegExp(/["'"]/g);
		   		var orgName = (report.orgName).replace(pattern, "\\'");
		   		
		   		result = result +'<tr>';
		   		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR\",\"ROLE_VIEW_EDIT_VENDOR\")">';
		   			result = result +'<td>';
		   			result = result +'<div class="proj_show" onclick="vendorSideBar(\''+report.vendorlocationId+'\',\''+report.id+'\',\''+orgName+'\');"><span class="tbldata-dotdot-vsm" title="'+report.orgName+'">'+report.orgName+'</span><i class="fa fa-binoculars"></i>';
		   			result = result +'</div>';
		   			result = result +'</td>';
		   		'</sec:authorize>';
		   		
		   		'<sec:authorize access="!hasAnyRole(\"ROLE_VIEW_VENDOR\",\"ROLE_VIEW_EDIT_VENDOR\")">';
					result = result +'<td>';
					result = result +'<div class="proj_show" ><span class="tbldata-dotdot-vsm" title="${report.responderLocationId.org.orgName}">'+report.orgName+'</span>';
					result = result +'</div>';
					result = result +'</td>';
				'</sec:authorize>';
				
				
		   		if( report.webSite != null && report.webSite != '' && report.webSite != 'undefined'){
		   			result = result +'<td>';
		   			result = result +'<div>';
		   			result = result +'<span class="tbldata-dotdot-sm" title='+report.webSite+'>'+report.webSite+'</span>';
		   			result = result +'</div>';
		   			result = result +'</td>';
		   		}else{
		   			result = result +'<td><div>--</div></td>';
		   		}
		   		
		   		if(location != null && location  != '' && location  != 'undefined'){
		   			result = result +'<td>';
		   			result = result +'<div>';
		   			result = result +'<span class="tbldata-dotdot-me" title="'+location+'">'+location+'</span>';
		   			result = result +'</div>';
		   			result = result +'</td>';
		   		}else{
		   			result = result +'<td><div>--</div></td>';
		   		}
				
		   	result = result +' <td><div><span class="tbldata-dotdot-vsm" title="'+report.level+'">'+report.level+'</span>';
				result = result +' <td><div>'+report.noOfReleasedJobs+'</div></td>';
				result = result +'<td><div>'+report.noOfContatcs+'</div></td>';
				/* result = result +' <td><div><i class="glyphicon glyphicon-trash"></i></div></td>'; */
		   		result = result +'</tr>';
				}
		   	
		   	result = result +'</tbody>';
			    $("#vendorList").empty();  
			    $("#vendorList").append(result)
			    $("#emptyVendorList").val(json.length)
			    pagination1();
	    
}
</script> -->


<!-- <script>
function getVendorLists(){
	closePop();
	var values = [];
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
    });
	var sourceleadUrl ='${sourceleadUrl}';
	//var loginOrgLocId = '${loginOrgLocId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorFilterLists?"+tokenParamName+"="+tokenValue+"&values="+values, 	   
   		    success: function(data) {
   		    	//preparedVendorsLists(data);
   		    	preparedVendorsListFromElk(data);
   		    }
	 });
	
	
}	
</script>  -->


<!-- <script>
function getVendorListsElk(){
	closePop();
	var values = [];
	var responderType = "Vendor";
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
    });
	var sourceleadUrl ='${sourceleadUrl}';
	//var loginOrgLocId = '${loginOrgLocId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorFilterListsElK?"+tokenParamName+"="+tokenValue+"&responderType="+responderType+"&values="+values, 	   
   		    success: function(data) {
   		    	//preparedVendorsLists(data);
   		 	    preparedVendorsListFromElk(data);
   		    }
	 });
	
}	
</script> 
 -->
 
 
 <!-- PLEASE DO NOT DELETE COMMENTED SCRIPTS, IF YOU HAVE ANY QUERIES CONTACT RAJASHEKAR -->


 <script>
function pagination123456(){  
	document.getElementById("vendorContactList").style.display="none";
    $('#vendorList').DataTable( { 
    	"scrollCollapse": false,
    	//"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
		
    });
    $('#vendorList').wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>


<!-- PLEASE DO NOT DELETE COMMENTED SCRIPTS, IF YOU HAVE ANY QUERIES CONTACT RAJASHEKAR -->

<!-- <script>
function getVendorContactLists(){
	closePop();
	var vendorId = [];
	$('input:checkbox[name=vendorNameById]:checked').each(function() 
    {
		vendorId.push($(this).val());
    });
	var vendorStatus = [];
	$('input:checkbox[name=vendorStatus]:checked').each(function() 
    {
		vendorStatus.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=vendorInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorContactLists?"+tokenParamName+"="+tokenValue+"&vendorId="+vendorId+"&vendorStatus="+vendorStatus+"&invitationStatus="+invitationStatus, 	   
   		    success: function(data) {
   		    	preparedVendorContactList(data);
   		    	//preparedVendorContactListFromElk(data);
   		    }
   		    	
	 });
	
	
}	
</script>  -->

<!-- This VendorContactList method  is for getting veendorContacts from ELK  on Windows OnLoad--------- -->

<!-- <script>
function getVendorContactListsELK(){
	closePop();
	var vendorId = [];
	$('input:checkbox[name=vendorNameById]:checked').each(function() 
    {
		vendorId.push($(this).val());
    });
	var vendorStatus = [];
	$('input:checkbox[name=vendorStatus]:checked').each(function() 
    {
		vendorStatus.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=vendorInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var responderType = "Vendor";
	
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorContactListsELK?"+tokenParamName+"="+tokenValue+"&vendorId="+vendorId+"&vendorStatus="+vendorStatus+"&invitationStatus="+invitationStatus+"&responderType="+responderType, 	   
   		    success: function(data) {
   		    	//preparedVendorContactList(data);
   		    	preparedVendorContactListFromElk(data);
   		    }
   		    	
	 });
	
}	
</script> -->

<!-- contact list preparation from ELK result -->
<!-- <script>
function preparedVendorContactListFromElk(data){
			var json = JSON.parse(data);
		   	var result = "";
		   	
		   	result = result +'<thead>';
		   	result = result +'<tr>';
			result = result +'  <th>Vendor</th>';
		   	result = result +'	<th>Contact Name</th>';
		   	result = result +'  <th>Email</th>';
		   	result = result +'  <th>Phone</th>';
		   	result = result +' <th>Owner</th>';
		   	result = result +' <th>Status</th>';
		   	result = result +' <th>Invitation</th>';
		   	result = result +'<th>Action</th>';
		   	result = result +'</tr>';
		   	result = result +'	</thead>';
		   	
		   	result = result +'<tbody>';
		   	
		   	for (var i=0; i<json.length; i++)
				{  
		   		var report = json[i];
		   		
		   		
		   		result = result +'<tr id="row'+report.id+'">';
		   		
		   		
		   		result = result +'<td><div class="tbldata-dotdot-100" title="'+report.orgName+'">'+report.orgName+'</div></td>';
		   		
		   		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR_CONTACT\",\"ROLE_VIEW_EDIT_VENDOR_CONTACT\")">';
		   				result = result +'<td><div class="proj_show"onclick="vendorSpocSideBar(\''+report.id+'\',\''+report.ownerUserId+'\',\''+report.spocUserId+'\',\''+report.spocName+'\',\''+report.tempOwnerId+'\',\''+report.spocLocationId+'\',\''+report.orgRelationId+'\',\''+report.userId+'\');"><span title="'+report.spocName+'">'+report.spocName+'</span><i class="fa fa-binoculars"></i></div></td>';
		   		'</sec:authorize>';
		   		
		   		'<sec:authorize access="!hasAnyRole(\"ROLE_VIEW_VENDOR_CONTACT\",\"ROLE_VIEW_EDIT_VENDOR_CONTACT\")">';
		   				result = result +'<td><div class="proj_show" ><span title="'+report.spocName+'">'+report.spocName+'</span></div></td>';
				'</sec:authorize>';
		   		
		   		
		   		
		   		result = result +'<td><div class="tbldata-dotdot-vsm" title='+report.spocEmail+'>'+report.spocEmail+'</div></td>';
		   		if(report.spocMobilePhone != null && report.spocMobilePhone  != '' && report.spocMobilePhone  != 'undefined'){
		   			result = result +'<td><div class="tbldata-dotdot-100" title="'+report.spocMobilePhone+'">'+report.spocMobilePhone+'</div></td>';
		   		}else{
		   			result = result +'<td><div>--</div></td>';
		   		}
		   		// from ELK ownerNames
		   		if(null != report.ownerNames  &&  '' != report.ownerNames  && 'undefined' != report.ownerNames){
		   			result = result +'<td><div class="tbldata-dotdot-100" title="'+report.ownerNames+'">'+report.ownerNames+'</div></td>';
		   		}
		   		//from db ownerName
		   		else if(null != report.ownerName && '' != report.ownerName && 'undefined' != report.ownerName){
		   			result = result +'<td><div class="tbldata-dotdot-100" title="'+report.ownerName+'">'+report.ownerName+'</div></td>';
		   		}
		   		
		   		
		   		
		   		result = result + ' <td><div class="tblminwidth-30 dropdown-select paddingNone">';
   	        		if(report.invitationStatus == 'Rejected'){
   	        			result = result + '<span>'+report.spocStatus+'</span>';
   	        		}else{
   	        			result = result + ' <select id="spocState'+report.id+'" name="spocStatus" onChange="updateSpocStatus(\''+report.id+'\');">';
   						result = result + ' <option '+(report.spocStatus == "Active" ? "selected" : '') +'>Active</option>';
   						result = result + ' <option '+(report.spocStatus == "In-Active" ? "selected" : '') +'>In-Active</option>';
   						/* result = result + ' <option '+(report.spocStatus == "New" ? "selected" : '') +'>New</option>';
   						result = result + ' <option '+(report.spocStatus == "Negotiation" ? "selected" : '') +'>Negotiation</option>';
   						result = result + ' <option '+(report.spocStatus == "Contract" ? "selected" : '') +'>Contract</option>'; */
   						result = result + ' </select>';
   						
   						result = result + '<span id="spocSpanState'+report.id+'"> </span>';
   	        		}
   	        		result = result + ' </div></td>';
		   			
   	        	result = result +' <td><div id="invitationStatus'+report.id+'" class="tblminwidth-30">';	
		   		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
		   		  result = result +' <i class="fa fa-check" alt="Accept" onclick="acceptInvitation('+report.id+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejectInvitation('+report.id+');"></i>';
		   		}
		   		
		   		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		            result = result +' ---11';
		      	}
		   		
		   		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		   			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendVendorSpocInvitation('+report.spocUserId+','+report.id+','+report.orgRelationId+');">Send</a></b>';
		   		}
		   		
		   		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		   			result = result +' ---';
		   		}
		   		
		   		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){ 
		   			result = result +' <b class="proj_process">Received</b>';
		   		}
		   		
		   		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
		   			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendVendorSpocInvitation('+report.spocUserId+','+report.id+','+report.orgRelationId+');">Resend</a></b>';
		   		}
		   		if(report.invitationStatus == 'Accepted'){ 
		   			result = result +' <b class="proj_active">'+report.invitationStatus+'</b>';
		   		}
		 		
		 		if(report.invitationStatus == 'Rejected'){
		 			result = result +' <b class="proj_closed">'+report.invitationStatus+'</b>';
		 		}
		 		result = result + ' </div></td>';
		   		/* result = result +'<td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a>';
		   		result = result +'<i class="glyphicon glyphicon-trash"></i></div></td>'; */
		   		'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_VENDOR_CONTACT\')">';
		   	    result = result + '<td><div class="tblminwidth-50"><i title="Remove vendor contact" class="glyphicon glyphicon-trash" onClick="terminateSpoc1('+report.id+'\,\''+report.orgRelationId+'\')"; title="Remove vendor contact"></i></div></td>';
		   	 '</sec:authorize>';
		   	 
		   	'<sec:authorize access="!hasAnyRole(\'ROLE_DELETE_VENDOR_CONTACT\')">';
			    result = result + '<td><div>---</div></td>';
			 '</sec:authorize>';
		   		
		   		
		   		result = result +'</tr>';
				}
		   	
		   	result = result +'</tbody>';
			    $("#vendorContactList").empty();  
			    $("#vendorContactList").append(result)
			    $("#emptyVendorcontactList").val(json.length);
			    pagination(); 
			    
   }
</script>  -->


<!-- <script>
function preparedVendorContactList(data){
	  
			var json = JSON.parse(data);
		   	var result = "";
		   	
		   	result = result +'<thead>';
		   	result = result +'<tr>';
			result = result +'  <th>Vendor</th>';
		   	result = result +'	<th>Contact Name</th>';
		   	result = result +'  <th>Email</th>';
		   	result = result +'  <th>Phone</th>';
		   	result = result +' <th>Owner</th>';
		   	result = result +' <th>Status</th>';
		   	result = result +' <th>Invitation</th>';
		   	result = result +'<th>Action</th>';
		   	result = result +'</tr>';
		   	result = result +'	</thead>';
		   	
		   	result = result +'<tbody>';
		   	
		   	for (var i=0; i<json.length; i++)
				{  
		   		var report = json[i];
		   		result = result +'<tr id="row'+report.id+'">';
		   		
		   		result = result +'<td><div><span title="'+report.orgName+'">'+report.orgName+'</span></div></td>';
		   		
		   		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR_CONTACT\",\"ROLE_VIEW_EDIT_VENDOR_CONTACT\")">';
		   				result = result +'<td><div class="proj_show"onclick="vendorSpocSideBar(\''+report.id+'\',\''+report.ownerUserId+'\',\''+report.spocUserId+'\',\''+report.spocName+'\',\''+report.tempOwnerId+'\',\''+report.spocLocationId+'\',\''+report.orgRelationId+'\',\''+report.userId+'\');"><span title="'+report.spocName+'">'+report.spocName+'</span><i class="fa fa-binoculars"></i></div></td>';
		   		'</sec:authorize>';
		   		
		   		'<sec:authorize access="!hasAnyRole(\"ROLE_VIEW_VENDOR_CONTACT\",\"ROLE_VIEW_EDIT_VENDOR_CONTACT\")">';
		   				result = result +'<td><div class="proj_show" ><span title="'+report.spocName+'">'+report.spocName+'</span></div></td>';
				'</sec:authorize>';
		   		
		   		
		   		
		   		result = result +'<td><div><span title='+report.spocEmail+'>'+report.spocEmail+'</span></div></td>';
		   		if(report.spocMobilePhone != null && report.spocMobilePhone  != '' && report.spocMobilePhone  != 'undefined'){
		   			result = result +'<td><div><span>'+report.spocMobilePhone+'</span></div></td>';
		   		}else{
		   			result = result +'<td><div>--</div></td>';
		   		}
		   		result = result +'<td><div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div></td>';
		   		
		   		
		   		result = result + ' <td><div>';
	        		if(report.invitationStatus == 'Rejected'){
	        			result = result + '<span>'+report.spocStatus+'</span>';
	        		}else{
	        			result = result + ' <select id="spocState'+report.id+'" name="spocStatus" onChange="updateSpocStatus(\''+report.id+'\');">';
						result = result + ' <option '+(report.spocStatus == "Active" ? "selected" : '') +'>Active</option>';
						result = result + ' <option '+(report.spocStatus == "In-Active" ? "selected" : '') +'>In-Active</option>';
						/* result = result + ' <option '+(report.spocStatus == "New" ? "selected" : '') +'>New</option>';
						result = result + ' <option '+(report.spocStatus == "Negotiation" ? "selected" : '') +'>Negotiation</option>';
						result = result + ' <option '+(report.spocStatus == "Contract" ? "selected" : '') +'>Contract</option>'; */
						result = result + ' </select>';
						
						result = result + '<span id="spocSpanState'+report.id+'"> </span>';
	        		}
	        		result = result + ' </div></td>';
		   		
	        	result = result +' <td><div id="invitationStatus'+report.id+'">';	
		   		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
		   		  result = result +' <i class="fa fa-check" alt="Accept" onclick="acceptInvitation('+report.id+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejectInvitation('+report.id+');"></i>';
		   		}
		   		
		   		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		            result = result +' ---';
		      	}
		   		
		   		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		   			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendVendorSpocInvitation('+report.spocUserId+','+report.id+','+report.orgRelationId+');">Send</a></b>';
		   		}
		   		
		   		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		   			result = result +' ---';
		   		}
		   		
		   		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){ 
		   			result = result +' <b class="proj_process">Received</b>';
		   		}
		   		
		   		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
		   			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendVendorSpocInvitation('+report.spocUserId+','+report.id+','+report.orgRelationId+');">Resend</a></b>';
		   		}
		   		
		   		if(report.invitationStatus == 'Accepted'){ 
		   			result = result +' <b class="proj_active">'+report.invitationStatus+'</b>';
		   		}
		 		
		 		if(report.invitationStatus == 'Rejected'){
		 			result = result +' <b class="proj_closed">'+report.invitationStatus+'</b>';
		 		}
		 		result = result + ' </div></td>';
		 		
		   		/* result = result +'<td><div><a href="add-vendor.html"><i class="fa fa-edit"></i></a>';
		   		result = result +'<i class="glyphicon glyphicon-trash"></i></div></td>'; */
		   		'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_VENDOR_CONTACT\')">';
		   	    result = result + '<td><div><i title="Remove vendor contact" class="glyphicon glyphicon-trash" onClick="terminateSpoc1('+report.id+'\,\''+report.orgRelations.id+'\')"; title = "Remove vendor contact"></i></div></td>';
		   	 '</sec:authorize>';
		   	 
		   	'<sec:authorize access="!hasAnyRole(\'ROLE_DELETE_VENDOR_CONTACT\')">';
			    result = result + '<td><div>---</div></td>';
			 '</sec:authorize>';
		   		
		   		
		   		result = result +'</tr>';
				}
		   	
		   	result = result +'</tbody>';
			    $("#vendorContactList").empty();  
			    $("#vendorContactList").append(result)
			    $("#emptyVendorcontactList").val(json.length);
			    pagination(); 
			    
   }
</script> 
 -->
 
 <!-- PLEASE DO NOT DELETE COMMENTED SCRIPTS, IF YOU HAVE ANY QUERIES CONTACT RAJASHEKAR -->

<div id="popUp"></div>

<!-- This script is for resending the invitation mail -->
<script type="text/javascript">
function resendVendorSpocInvitation(spocUserId,orgSpocId,orgRelationId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 
	 var rowHtmlContent =  $("#row"+orgSpocId).html();
	 $("#row"+orgSpocId).empty();
	 $("#row"+orgSpocId).html("<td colspan='8'  id='loading"+orgSpocId+"'></td>");
	if(spocUserId != null && spocUserId != ""){
		$("#loading"+orgSpocId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
			 $.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendVendorSpocInvitationEmail?"+tokenParamName+"="+tokenValue+"&spocUserId="+spocUserId+"&orgSpocId="+orgSpocId+"&responderType=Vendor&orgRelationID="+orgRelationId, 
			        success: function(result) {
			        	 $("#row"+orgSpocId).empty();
			    	     $("#row"+orgSpocId).html(rowHtmlContent);
			    	     $("#venselector").removeAttr("style");
			    		 $("#venselector").css({ display: "block" });
			    	     $("#venmsgDiv").html("Invitation Sent Successfully..");
			    	     $("#invitationStatus"+orgSpocId).html("<b class='proj_process'><a href='javascript:void(0)' onClick='resendVendorSpocInvitation("+spocUserId+','+orgSpocId+','+orgRelationId+");'>Resend</a></b>");
			    	     msgFadein();
			        },
			    });
		 } 
	}
  
</script>

<script>
function cancelTerminationReson(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
	$("#reasonForSpoc").val('');
	$("#reasonForVendorSpocDiv").find("p.jquery_form_error_message").remove();
	$("#reasonForVendorSpocDiv").empty(); 
}
</script>

<script>
function terminateSpoc1(spocId, orgRelationID,spocEmail){
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>Do you want to delete this vendor contact?</p>";
	result = result +"		<textarea id='reasonForSpoc' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Reason' rows='2' onkeypress='vendorDashboardRemoveSpacesForRejectReason();'></textarea>";
	result = result + ' <div id="reasonForVendorSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='saveReason1("+spocId+","+orgRelationID+",\""+spocEmail+"\");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTerminationReson();'></b>";
	result = result +"	</div>";
	$("#popUp").append(result);
	$("#remove_user_pop").slideDown(200);
	$(".project_inn_mask").fadeIn(200);
}
</script>
<script>
   function clientDashboardRemoveSpacesForRejectReason() {
	    var output = document.getElementById("reasonForSpoc").value;
	    output = output.replace(/\s/g, "") 
	    if(output.length != 0){
	    $("#reasonForSpoc").removeAttr("data-validation");
	    }
	}
</script>
<script>
function saveReason1(spocId, orgRelationID,spocEmail){
	var sourceleadUrl = '${sourceleadUrl}';
	var reason = document.getElementById("reasonForSpoc").value;
	var userId = '${SourceLeadWeb_userId}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	 reason = reason.replace(/\s/g, "")
	 var reason1 = document.getElementById("reasonForSpoc").value;
	 var userId = '${SourceLeadWeb_user.id}';
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	if(reason.match(/^[^\s-](.*)*$/) != null){
		$("#reasonForVendorSpocDiv").empty();
		$.ajax({
		   	type: "POST",
		 	url: sourceleadUrl+"/terminateSpoc?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&reason="+reason+"&userId="+userId+"&orgLocationId="+orgLocationId+"&spocEmail="+spocEmail+"&responderType=Vendor+",	    	   
		    success: function(data) {
		    	getVendorContactList();
		    	 $("#venselector").removeAttr("style");
	  			 $("#venselector").css({ display: "block" });
	  			 $("#venmsgDiv").html("vendor contact removed");
	  			 $("#popUp").empty();
	 			 $(".project_inn_mask").fadeOut(200);
	 			 
// 	 			 var table = $('#vendorContactList').DataTable();
// 	  		     table.row("#row"+spocId).remove().draw( false );
   			     
	 			 msgFadein();
		    }
	   });
	}else{
		if(reason.length == 0 && reason1){
			// $("#reasonForVendorSpocDiv").find('p.jquery_form_error_message').remove();
			$("#reasonForVendorSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			 $("#reasonForVendorSpocDiv").find('p.jquery_form_error_message').remove();
			$("#reasonForVendorSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
	  
}
</script>


<script>
function updateSpocStatus(spocId){
    var spocStatus = $('#spocState'+spocId).val();
	var sourceleadUrl = '${sourceleadUrl}';
	
	 var rowHtmlContent =  $("#row"+spocId).html();
	 $("#row"+spocId).empty();
	 $("#row"+spocId).html("<td colspan='8'  id='loading"+spocId+"'></td>");
	 $("#loading"+spocId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	
	$.ajax({
	   	type: "POST",
	    url: sourceleadUrl+"/changeSpocStatus?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	$("#row"+spocId).empty();
	    	$("#row"+spocId).html(rowHtmlContent);
	        $("#spocState"+spocId).val(spocStatus);
	    }
   });
}
</script>

<script>
function pagination(){  
	/* document.getElementById("vendorList").style.display="none"; */
	/*  document.getElementById("vendorList_info").style.display="none"; 
	 document.getElementById("vendorList_paginate").style.display="none";  */
    $('#vendorContactList').DataTable( { 
    	"scrollCollapse": false,
    	//"scrollX": true,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    });
    $('#vendorContactList').wrap("<div class='overflow_Auto' style='width:100%;'></div>");
}
</script>

<!-- PLEASE DO NOT DELETE COMMENTED SCRIPTS, IF YOU HAVE ANY QUERIES CONTACT RAJASHEKAR -->

<!-- <script>
function getClientDetails(){
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getClientDetails?"+tokenParamName+"="+tokenValue+"", 	   
   		    success: function(data) {
   		    	var json = JSON.parse(data);
   	        	var result = "";
   	        	result = result +'<thead>';
   	        	result = result +'<tr>';
   	        	result = result +'	<th>Client name</th>';
   	        	result = result +'	<th>Website</th>';
   	        	result = result +'	<th>Location</th>';
   	        	result = result +'	<th>Level</th>';
   	        	result = result +'	<th>Created By</th>';
   	        	result = result +'<th>No.of contacts</th>';
   	        	result = result +'<th>Action</th>';
   	        	result = result +'</tr>';
   	        	result = result +'</thead>';
   	        	result = result +'<tbody>';
   	        	
   	        	for (var i=0; i<json.length; i++)
   				{  
   	        		
   	        		var report = json[i];
   	        		
   	        		var location = "";
   	        		if(report.city != null && report.city != '' && report.city != 'undefined'){
   	        			location = report.city+", "+report.state+", "+report.country;
   	        		}else if(report.state != null && report.state != '' && report.state != 'undefined'){
   	        			location = report.state+", "+report.country;
   	        		}else{
   	        			location = report.country;
   	        		}
   	        		
   	        		
   	        		result = result +'<tr>';
   	        		if(report.locationID == loginOrgLocId){
   	        			result = result +'<td>';
   	        			result = result +'<div class="proj_show" onclick="clientSideBar(\''+report.responderId+'\',\''+report.orgName+'\',\''+report.id+'\');"><span class="tbldata-dotdot-vsm" title="'+report.orgName+'">'+report.orgName+'</span><i class="fa fa-binoculars"></i>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
						
   	        		}
   	        		if( report.webSite != null && report.webSite != '' && report.webSite != 'undefined'){
   	        			result = result +'<td>';
   	        			result = result +'<div>';
   	        			result = result +'<span class="tbldata-dotdot-sm" title='+report.webSite+'>'+report.webSite+'</span>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		
   	        		if(location != null && location  != '' && location  != 'undefined'){
   	        			result = result +'<td>';
   	        			result = result +'<div>';
   	        			result = result +'<span class="tbldata-dotdot-me" title="'+location+'">'+location+'</span>';
   	        			result = result +'</div>';
   	        			result = result +'</td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		if(report.level != null && report.level != '' && report.level != 'undefined'){
   	        			result = result +'<td><div><span class="tbldata-dotdot-vsm" title='+report.level+'>'+report.level+'</span></div></td>';
   	        		}else{
   	        			result = result +'<td><div>--</div></td>';
   	        		}
   	        		result = result +'<td><div><span class="tblminwidth-125">'+report.vendorCreateduserName+'</span></div></td>';
   	        		result = result +'<td><div>'+report.noOfContatcs+'</div></td>';
   	        		result = result +'<td><div><i class="glyphicon glyphicon-trash"></i></div></td>';
   	        		
   	        		
   	        		
   	        		if(report.responderId == loginOrgLocId){
   	        			result = result +'<td><div class="proj_show" onclick="clientSideBar('+report.locationID+','+report.locationOrgname+','+report.id+');"><span class="tbldata-dotdot-vsm" title='+report.locationOrgname+'>"'+report.locationOrgname+'"</span><i class="fa fa-binoculars"></i>';
   	        			result = result +'</div></td>';
   	        			
   	        			if(report.locationWebsite != null && report.locationWebsite != '' && report.locationWebsite != 'undefined'){
   	        				result = result +'<td><div><span class="tbldata-dotdot-sm" title='+report.locationWebsite+'>'+report.locationWebsite+'</span></div></td>';
   	        			}else{
   	        				result = result +'<td><div>--</div></td>';
   	        			}
   	        			
   	        			if(report.locationCountry != null & report.locationCountry != '' && report.locationCountry != 'undefined'){
   	        				result = result +'<td><div><span class="tbldata-dotdot-me" title="'+report.locationCity+'&nbsp;,'+report.locationState+'&nbsp;,'+report.locationCountry+'">'+report.locationCity+'&nbsp;,'+report.locationState+'&nbsp;,'+report.locationCountry+'</span></div></td>';
   	        			}else{
   	        				result = result +'<td><div>--</div></td>';
   	        			}
   	        			
   	        			if(report.level != null && report.level != '' && report.level != 'undefined'){
   	        				result = result +'<td><div><span class="tbldata-dotdot-vsm" title='+report.level+'>'+report.level+'</span></div></td>';
   	        			}else{
   	        				result = result +'<td><div>--</div></td>';
   	        			}
   	        			
   	        			result = result +'<td><div><span class="tblminwidth-125">'+report.vendorCreateduserName+'</span></div></td>';
   	        			result = result +'<td><div>'+report.noOfContatcs+'</div></td>';
   						
   	        		} 
   	        		
   	        		result = result +'</tr>';
   	        		
   				}
   	        	
   	        	result = result +'</tbody>';
   	    	    $("#vendorList").empty();  
   	    	    $("#vendorList").append(result)
   	    	  pagination1();
   	    	    
   		    }
	 }); 
	
	
}	
</script>  -->

<!-- PLEASE DO NOT DELETE COMMENTED SCRIPTS, IF YOU HAVE ANY QUERIES CONTACT RAJASHEKAR -->

<script>
	function vendorSpocSideBar(spocId,ownerId,spocUserId,spocName,tempOwnerId,spocLocationId,orgRelationId,userId) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		
		  '<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR\",\"ROLE_VIEW_EDIT_VENDOR\")">';
		   			result = result	+ '<li class="current"><a href="#tab-1" id="spocDetailsId" onClick="vendorDetailsbySpocId('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+','+spocLocationId+','+orgRelationId+');">Details</a></li>';
		   '</sec:authorize>';
		 '<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR_DOCUMENT\")">';
		  result = result + '<li><a href="#tab-2" onClick="vendorSpocDocuments('+orgRelationId+','+spocLocationId+',\''+spocName+'\','+userId+');">Documents</a></li>';
		'</sec:authorize>';
		/* result = result + '<li><a href="#tab-3">Activity</a></li>';  */
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '<div id="tab-2" class="project_tabs_inn">';
		result = result + '</div>';

		result = result + '</div>';
		result = result + '</div>';
		$("#vendorSideDiv").empty();
		$("#vendorSideDiv").append(result);
		
		/* $("#vendorNameDiv").empty();
		$("#vendorNameDiv").append(spocName);
		 */
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');

		vendorDetailsbySpocId(spocId,ownerId,spocUserId,tempOwnerId,spocLocationId,orgRelationId);
	}
</script>


<script>
function getVendorDashboardName(vendorDashboardName){
	$("#vendorNameDiv").empty();
	$("#vendorNameDiv").append(vendorDashboardName);
}

</script>



<script>
	function vendorSideBar(vendorLocationId,orgRelationId,orgName) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR\",\"ROLE_VIEW_EDIT_VENDOR\")">';
		result = result + '<li class="current"><a href="#tab-1" onClick="vendorDetailsbyLocationId(' + vendorLocationId + ','+ orgRelationId + ');">Details</a></li>';
		'</sec:authorize>';
		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR_CONTACT\",\"ROLE_VIEW_EDIT_VENDOR_CONTACT\")">';
		result = result + '<li id="vendorContactsLi"><a href="#tab-2" onclick="vendorContacts('+ orgRelationId + ','+vendorLocationId+');">Contacts</a></li>'; 
		'</sec:authorize>';
		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR_DOCUMENT\")">';
		result = result + '<li><a href="#tab-3" onclick="vendorDocuments('+orgRelationId+','+vendorLocationId+');">Documents</a></li>'; 
		'</sec:authorize>';
		
		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_REQUIREMENT\")">';
		result = result + '<li><a href="#tab-6" onclick="vendorRequirementListInclude('+ orgRelationId + ');">Jobs</a></li>'; 
		'</sec:authorize>';
		/* result = result + '<li><a href="#tab-4">Activity</a></li>';  */
		
		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		result = result + '<li><a href="#tab-5" onclick="vendorContracts('+orgRelationId+');">Contracts</a></li>'; 
		'</sec:authorize>';
		
		
		 result = result + '<li><a href="#tab-4" onClick="getVendorInvoiceList('+orgRelationId+','+vendorLocationId+');">Bills</a></li>';
		
		 // vendors associates list
		 '<sec:authorize access="hasAnyRole(\"ROLE_VIEW_ASSOCIATE\")">';
		 result = result + '<li><a href="#tab-7" onClick="vendorAssociatesList('+orgRelationId+');">Associates</a></li>';
		 '</sec:authorize>';
		 
		 // vendors notes list
		
		 result = result + '<li><a href="#tab-8" onClick="vendorNotesList('+orgRelationId+','+vendorLocationId+');">Notes</a></li>';
		 
		 result = result + '<li><a href="#tab-9" onClick="vendorOnboardDocumentsList('+orgRelationId+');">Onboard Documents</a></li>';
		
		 result = result + '<li><a href="#tab-10" onClick="vendorOnboardTasksList('+orgRelationId+');">Onboard Tasks</a></li>';


		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		//for vendor details tab
		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';

		//for vendor contacts tab
		result = result + '<div id="tab-2" class="project_tabs_inn">';
		result = result + '</div>';
		
		//for vendor documents tab
		result = result + '<div id="tab-3" class="project_tabs_inn">';
		result = result + '</div>';
		
		//for vendor billing
		result = result + '<div id="tab-4" class="project_tabs_inn">';
		result = result + '</div>';
		
		//for vendor Contracts
		result = result + '<div id="tab-5" class="project_tabs_inn">';
		result = result + '</div>';

		//for vendor requirement list tab
		result = result + '<div id="tab-6" class="project_tabs_inn">';
		result = result + '</div>';
		
		//for vendor associate list tab
		result = result + '<div id="tab-7" class="project_tabs_inn">';
		result = result + '</div>';
		
		
		//for vendor notes list tab
		result = result + '<div id="tab-8" class="project_tabs_inn">';
		result = result + '</div>';
		
		
		//for vendor onboard Documents list tab
		  result = result + '<div class="tab-pane clearfix emp_onboarddocs" id="vendorDocuments">';
		  result = result + '<div class="col-md-12 clearfix mt">';
		  result = result + '<div class="form-group">';
		  result = result + '<div id="tab-9" class="project_tabs_inn">';
		  result = result + '</div>';
		  result = result + '</div>';
		  result = result + '</div>';
		  result = result + '</div>';
		  
		  result = result + '<div class="tab-panes" id="tasks"> ';
		  result = result + '<div id="tab-10" class="project_tabs_inn">';
          result = result + '</div>';

		result = result + '</div>';
		result = result + '</div>'
		$("#vendorSideDiv").empty();
		$("#vendorSideDiv").append(result);
		
		/* $("#vendorNameDiv").empty();
		$("#vendorNameDiv").append(orgName); */
		
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		
		if ('${contactsTab}' == "contactsTab") {
			$(".project-tabs-menu li").removeClass("current");
			$("#vendorContactsLi").addClass("current");
			$("#tab-2").show();
			vendorContacts(orgRelationId, vendorLocationId);
		} else {
			vendorDetailsbyLocationId(vendorLocationId, orgRelationId);
		}

	}
</script>

<script>
function getVendorOrgname(vendorOrgname){
	$("#vendorNameDiv").empty();
	$("#vendorNameDiv").append(vendorOrgname);	
}

</script>


<script type="text/javascript">
	function vendorDetailsbyLocationId(vendorLocationId,orgRelationId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$.ajax({
					type : "POST",
					url : sourceleadUrl+"/getClientAndVendorOrgDeatilsById?"+tokenParamName+"="+tokenValue+"&vendorLocationId="+vendorLocationId+"&orgRelationId="+orgRelationId+"&requestType=Vendor",
					success : function(json) {
						
						getVendorOrgname(json.orgName);
						var result = "";
						var city = "";
						var state = "";
						var country = "";
						result = result+ '<div class="project_tabs_inn_table">';
						result = result + '<div class="project_details">';
// 						result  = result + '<div class="success_pop" id="editVendorMsgDiv"></div>';
						
	 					if(json.msg != null && json.msg != '' && json.msg != 'undefined'){
	 						result  = result + '<div class="success_pop" id="editVendorMsg" style="display: block;">';
	 						result  = result + '<i class="fa fa-check-circle"></i><span>'+json.msg+'</span>';
	 						result  = result + '</div>';
	 						}
	 					
							result = result + ' <div class="useredit useredit_a">';
							'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_EDIT_VENDOR\")">';
								result = result + '<a id="edit-client" href="javascript:editVendorDetails('+vendorLocationId+','+orgRelationId+')"><i class="glyphicon glyphicon-pencil"></i>Edit</a>';
							'</sec:authorize>';
								result = result + ' </div>'; 
							result = result + '<div class="project_details_cta">';
								result = result + '<strong>Vendor Name:</strong>';
								if(json.orgName !=null && json.orgName !='' && json.orgName !='undefined'){
									result = result + '<span title="'+json.orgName+'">'+json.orgName+'</span>';
								}
								result = result + '<div class="clearfix"></div>';
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
								result = result + '<strong>Web Url:</strong>';
								if(json.webSite !=null && json.webSite !=''  && json.webSite !='null' && json.webSite !='undefined'){
								result = result + '<span title="'+json.webSite+'">'+json.webSite+'</span><div class="clearfix"></div>';
								}else{
									result = result + '<span> --- </span><div class="clearfix"></div>';
								}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Vendor Location:</strong>';
							if(json.location != null && json.location !='' && json.location !='undefined'){
								result = result + '<span>'+json.location+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Vendor Country:</strong>';
							if(json.country != null && json.country !='' && json.country !='undefined'){
								result = result + '<span>'+json.country+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Vendor State:</strong>';
							if(json.state != null && json.state !='' && json.state !='undefined'){
								result = result + '<span>'+json.state+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Vendor City:</strong>';
							if(json.city != null && json.city !='' && json.city !='undefined'){
								result = result + '<span>'+json.city+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Vendor Zip Code:</strong>';
							if(json.zipcode != null && json.zipcode !='' && json.zipcode !='undefined'){
								result = result + '<span>'+json.zipcode+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							/* result = result + '<div class="project_details_cta">';
							result = result + '<strong>Email:</strong>';
							if(json.email != null && json.email !='' && json.email !='undefined'){
							result = result + '<span>'+json.email+'</span><div class="clearfix"></div>';
							}else{
								
								result = result + '<span>No Data</span><div class="clearfix"></div>';	
							}
							result = result + '</div>'; */

							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Tier:</strong>';
							if(json.level != null && json.level !='' && json.level !='undefined'){
							result = result + '<span>'+json.level+'</span><div class="clearfix"></div>';
							}else{
								
								result = result + '<span> --- </span><div class="clearfix"></div>';	
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Created By:</strong>';
							if(json.vendorCreateduserName != null && json.vendorCreateduserName !='' && json.vendorCreateduserName !='undefined'){
								result = result + '<span title="'+json.vendorCreateduserName+'">'+json.vendorCreateduserName+'</span><div class="clearfix"></div>';
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
							if(json.paymentTerms != null && json.paymentTerms !='' && json.paymentTerms !='undefined' && json.paymentTerms !='SelectPaymentTerms'){
								result = result + '<span title="'+json.paymentTerms+'">'+json.paymentTerms+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							
							
							if(json.isVendorOnboardSubmitted == 'Y'){
							result = result + '<div class="clearfix mt10"></div>';
							result = result +'<h4 class="pull-left">Onboard Details</h4>';
							result = result + '<div class="clearfix"></div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Nature Of Business :</strong>';
							if(json.industryName != null && json.industryName !='' && json.industryName !='undefined'){
								result = result + '<span title="'+json.industryName+'">'+json.industryName+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Currency :</strong>';
							if(json.currency != null && json.currency !='' && json.currency !='undefined'){
								result = result + '<span title="'+json.currency+'">'+json.currency+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>SIC Code :</strong>';
							if(json.sicCode != null && json.sicCode !='' && json.sicCode !='undefined'){
								result = result + '<span title="'+json.sicCode+'">'+json.sicCode+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>NAICS Code :</strong>';
							if(json.naicsCode != null && json.naicsCode !='' && json.naicsCode !='undefined'){
								result = result + '<span title="'+json.naicsCode+'">'+json.naicsCode+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>EIN Code :</strong>';
							if(json.einCode != null && json.einCode !='' && json.einCode !='undefined'){
								result = result + '<span title="'+json.einCode+'">'+json.einCode+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Tax Reg Number :</strong>';
							if(json.taxRegistrationNumber != null && json.taxRegistrationNumber !='' && json.taxRegistrationNumber !='undefined'){
								result = result + '<span title="'+json.taxRegistrationNumber+'">'+json.taxRegistrationNumber+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>DUNS Number :</strong>';
							if(json.dunsNumber != null && json.dunsNumber !='' && json.dunsNumber !='undefined'){
								result = result + '<span title="'+json.dunsNumber+'">'+json.dunsNumber+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Account Name :</strong>';
							if(json.accountName != null && json.accountName !='' && json.accountName !='undefined'){
								result = result + '<span title="'+json.accountName+'">'+json.accountName+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Account Number :</strong>';
							if(json.accountNumber != null && json.accountNumber !='' && json.accountNumber !='undefined'){
								result = result + '<span title="'+json.accountNumber+'">'+json.accountNumber+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Bank Name :</strong>';
							if(json.bankName != null && json.bankName !='' && json.bankName !='undefined'){
								result = result + '<span title="'+json.bankName+'">'+json.bankName+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Branch Number :</strong>';
							if(json.branchNumber != null && json.branchNumber !='' && json.branchNumber !='undefined'){
								result = result + '<span title="'+json.branchNumber+'">'+json.branchNumber+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>SWIFT Bank Identifier Code (BIC) :</strong>';
							if(json.bankIdentifierCode != null && json.bankIdentifierCode !='' && json.bankIdentifierCode !='undefined'){
								result = result + '<span title="'+json.bankIdentifierCode+'">'+json.bankIdentifierCode+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Email Address For PO :</strong>';
							if(json.poEmail != null && json.poEmail !='' && json.poEmail !='undefined'){
								result = result + '<span title="'+json.poEmail+'">'+json.poEmail+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Email Address For Payment Remittance :</strong>';
							if(json.paymentRemittanceEmail != null && json.paymentRemittanceEmail !='' && json.paymentRemittanceEmail !='undefined'){
								result = result + '<span title="'+json.paymentRemittanceEmail+'">'+json.paymentRemittanceEmail+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							
							result = result + '<div class="project_details_cta">';
							result = result + '<strong>Building No/Name & Street  :</strong>';
							if(json.bankAddress != null && json.bankAddress !='' && json.bankAddress !='undefined'){
								result = result + '<span title="'+json.bankAddress+'">'+json.bankAddress+'</span><div class="clearfix"></div>';
							}else{
								result = result + '<span> --- </span><div class="clearfix"></div>';
							}
							result = result + '</div>';
							}
							
							if(json.clientCustomFieldlabelNameAndValue != null && json.clientCustomFieldlabelNameAndValue != "" && json.clientCustomFieldlabelNameAndValue != 'undefined'){
					              $.each(json.clientCustomFieldlabelNameAndValue, function(key, value) {
					            	  var labelNameAndlabelData = value.split("$");
					            	  result = result + '	<div class="project_details_cta">';
					            	  result = result + '	<strong>'+labelNameAndlabelData[0]+':</strong><span class="fix">'+labelNameAndlabelData[1]+'</span><div class="clearfix"></div>';
					            	  result = result + '	</div>';
					              });
				              }
							
							result = result + ' <div class="user_summary_edit user_summary_edit_a" id="editVendorDetailsDiv"></div>';
							result = result + '</div>';
						result = result + '</div>';
						result = result + '</div>';
						
						$("#tab-1").empty();
						$("#tab-1").append(result);
						$("#tab-1").show();
						editVendorMsgMsgFadein();
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

					}
				});
	}
</script>

<script>
function listChange(sel){
	 if(sel.value=="listbyVendor"){
		 document.getElementById("vendorList").style.display="block";
		 document.getElementById("vendorList").style.display="table";
		 document.getElementById("vendorContactList").style.display="none";
		 document.getElementById("vendorContactList_wrapper").style.display="none";
// 		 document.getElementById("vendorsList").style.display="none";
         '<sec:authorize access="hasRole(\'ROLE_EXPORT_VENDORS\')">'; 
		 document.getElementById("vendorImports").style.display="block";
		 '</sec:authorize> ';
		'<sec:authorize access="hasRole(\'ROLE_EXPORT_VENDOR_CONTACTS\')">'; 
		 document.getElementById("VendorContactImports").style.display="none";
		 '</sec:authorize> ';
	//	 document.getElementById("filter1ApprovePop").style.display="none";
		 //getVendorLists();
		 document.getElementById("advanceSearchDiv").style.display="block";
		 $("#dataTableSearch").val("");
// 		 getVendorListsElk();

         document.getElementById("resetVendor").style.display="inline-block";
		 document.getElementById("resetVendorContact").style.display="none";
		 document.getElementById("vendorFiletPopup").style.display="block";
		 document.getElementById("vendorContactFiletPopup").style.display="none";
		 
		 getVendorList();
	 }
	 if(sel.value=="listbyContact"){
		 document.getElementById("vendorContactList").style.display="block";
		 document.getElementById("vendorContactList").style.display="table";
		 document.getElementById("vendorList").style.display="none";
		 document.getElementById("vendorList_wrapper").style.display="none";
		 '<sec:authorize access="hasRole(\'ROLE_EXPORT_VENDORS\')">'; 
		 document.getElementById("vendorImports").style.display="none";
		 '</sec:authorize> ';
		 '<sec:authorize access="hasRole(\'ROLE_EXPORT_VENDOR_CONTACTS\')">'; 
		 document.getElementById("VendorContactImports").style.display="block";
		 '</sec:authorize> ';
        document.getElementById("advanceSearchDiv").style.display="block";
		$("#dataTableSearch").val("");
		
		document.getElementById("resetVendor").style.display="none";
		 document.getElementById("resetVendorContact").style.display="inline-block";
		 document.getElementById("vendorFiletPopup").style.display="none";
		 document.getElementById("vendorContactFiletPopup").style.display="block";
		 
		loadVendorContactDataTable("${universalVendorList}");
		
	 }
	 
}
</script>

<jsp:include page="../masterFooter.jsp" />
<script src="/resources/js/jquery-fun.js"></script> 
<script>

// Approved Popup
$(document).ready(function() {
    $("#appro-pop").click(function(){
		$(".approved_pop").slideToggle(200);
	});
    $("html").click(function() {
        $(".approved_pop").slideUp(200);
    });
    $(".approved_pop, #appro-pop").click(function(e) {
        e.stopPropagation();
    });	
    
    $("#filter1ApprovePop").click(function(){
		$("#filter1ApprovedPop").slideToggle(200);
		$("#filter3ApprovedPop, #filter4ApprovedPop, .export_pop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter1ApprovedPop").slideUp(200);
    });
    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
});

// Export Popup
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
	
	
// Error pop
$(document).ready(function() {
    $(".glyphicon-trash").click(function(){
		$(".vendor_error").slideToggle(200);
		$(".vendor_wrap_mask").fadeIn(200);
	});
	$(".vendor_error a").click(function(){
		$(".vendor_error").slideUp(200);
		$(".vendor_wrap_mask").fadeOut(200);
	});
});	
	
// project side bar
$(document).ready(function() {
    $(".proj_show").click(function(){
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		//$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
	});
    $(".proj_close, .side_mask").click(function(){
		$(".clients_side_wrap").removeClass("clients_side_wrap_active");
		$(".side_mask").fadeOut(200);
		$("body").css('overflow-y', 'auto');
		//update the details in list.
		var vendorCont = document.getElementById('listOfVendors').value;
		if(vendorCont == "listbyContact"){
			getVendorContactListsELK();
		}if(vendorCont == "listbyVendor" ){
			
   		}
	});	
});

function refresh(){
	if($("#listOfVendors").val() == 'listbyContact')
		getVendorContactListsELK();
	else
		getVendorListsElk();
}

// project side bar tabs
$(document).ready(function() {
	
	if('${orgRelationUUID}' != null && '${orgRelationUUID}' != '' && '${orgRelationUUID}' != 'undefined'){
		vendorSideBar('${vendorLocationId}','${orgRelationId}','${vendorName}');
	}
	
	
	$("#tab-1").show()
    $(".project-tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".project_tabs_inn").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
});


	// pop head pop
	
$(".addcon-pop").click(function(){
	 $(".collapse-div-adduser").toggleClass("show-div");
	 $(".collapse-div").removeClass("show-div");
 });
 $(".close-div").click(function(){
	 $(this).parent().removeClass("show-div");
	 });
 $(".addcon-pop, .adddoc-pop, .collapse-div-adduser, .collapse-div").click(function(e){
	 e.stopPropagation();
 });
 $('html').click(function(){
    $(".collapse-div-adduser").removeClass("show-div");
	$(".collapse-div").removeClass("show-div");
   });


</script> 


<jsp:include page="editVendor.jsp" />
<jsp:include page="editSpocDetails.jsp" />
<jsp:include page="vendorContacts.jsp" />
<jsp:include page="vendorDocuments.jsp" />
<jsp:include page="vendorContracts.jsp" />
<jsp:include page="vendorSpocDocuments.jsp" />

<jsp:include page="vendorRequirmentList.jsp" />

<jsp:include page="vendorBilling.jsp" /> 
<jsp:include page="vendorAssociatesList.jsp" /> 
<jsp:include page="vendorNotesList.jsp" />
<jsp:include page="vendorListDatatable.jsp" />
<jsp:include page="vendorContactsDataTable.jsp" />
<jsp:include page="vendorOnboardSubmittedDocuments.jsp" />
<jsp:include page="vendorOnboardSubmittedTasks.jsp" />


<!-- Scripts for invitations  -->
<script>
function acceptInvitation(spocId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var rowHtmlContent =  $("#row"+spocId).html();
	 $("#row"+spocId).empty();
	 $("#row"+spocId).html("<td colspan='8'  id='loading"+spocId+"'></td>");
	 $("#loading"+spocId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/saveInvitationStatus?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&invitationStaus=Accepted",	    	   
		    success: function(data) {
		    	$("#venselector").removeAttr("style");
    			$("#venselector").css({ display: "block" });
    			$("#venmsgDiv").html("Accept Invitation Successfully");
    			
    			$("#row"+spocId).empty();
    	    	$("#row"+spocId).html(rowHtmlContent);
    	        $("#invitationStatus"+spocId).html("<b class='proj_active'>Accepted</b>");
		    	msgFadein();
		    }
	   });
}
</script>

<script>
function rejectInvitation(spocId){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var rowHtmlContent =  $("#row"+spocId).html();
	 $("#row"+spocId).empty();
	 $("#row"+spocId).html("<td colspan='8'  id='loading"+spocId+"'></td>");
	 $("#loading"+spocId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/saveInvitationStatus?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&invitationStaus=Rejected",
		    success: function(data) {
		    	$("#venselector").removeAttr("style");
    			$("#venselector").css({ display: "block" });
    			$("#venmsgDiv").html("Reject Invitation Successfully");
    			
    			$("#row"+spocId).empty();
    	    	$("#row"+spocId).html(rowHtmlContent);
    	        $("#invitationStatus"+spocId).html("<b class='proj_closed'>Rejected</b>");
    	        
    	        $("#spocState"+spocId).remove();
    	        $("#spocSpanState"+spocId).html("<span>In-Active</span>");
    	        
		    	msgFadein();
		    }
	   });
}
</script>
<!-- Scripts for invitations  -->

<script>
  $('#selector').delay(2000).fadeOut('slow');
  $(document).ready(function() {
	   $("#export-popcon").click(function(){
	               $(".export_pop").toggle();
	               $("#filter4ApprovedPop, #filter1ApprovedPop, #filter3ApprovedPop").slideUp();
	       });
	   $("html").click(function() {
	       $(".export_pop").slideUp();
	   });
	   $(".export_pop, #export-popcon").click(function(e) {
	       e.stopPropagation();
	   });        
	});  
</script> 	
<script>
  $('#selector').delay(2000).fadeOut('slow');
  $(document).ready(function() {
	   $("#vendor-imports").click(function(){
	    $(".export_pop").toggle();
	       });
	   $("html").click(function() {
	       $(".export_pop").slideUp();
	   });
	   $(".export_pop, #vendor-imports").click(function(e) {
	       e.stopPropagation();
	   });        
	});  
</script> 	

<script>
function exportVendorToPdf(){
	var values = [];
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
    });
	var sourceleadUrl ='${sourceleadUrl}';
	var emptyVendorList  = $("#emptyVendorList").val()
	if(emptyVendorList > 0 ){
		window.location.href = sourceleadUrl+"/exportVendorDetails?"+tokenParamName+"="+tokenValue+"&values="+values+"&exportType=pdf";	
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Vendor List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelTermination();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#client_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
	
}
</script>

<script>
function exportVendorToExcel(){
	var values = [];
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
    });
	var sourceleadUrl ='${sourceleadUrl}';
	var emptyVendorList  = $("#emptyVendorList").val()
	if(emptyVendorList > 0 ){
	window.location.href = sourceleadUrl+"/exportVendorDetails?"+tokenParamName+"="+tokenValue+"&values="+values+"&exportType=xls";
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Vendor List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelTermination();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#client_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
</script>

<script>
function exportVendorContactToPdf(){
	var vendorId = [];
	$('input:checkbox[name=vendorNameById]:checked').each(function() 
    {
		vendorId.push($(this).val());
    });
	var vendorStatus = [];
	$('input:checkbox[name=vendorStatus]:checked').each(function() 
    {
		vendorStatus.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=vendorInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	
	var sourceleadUrl ='${sourceleadUrl}';
	var emptyVendorcontactList = $("#emptyVendorcontactList").val();
	if(emptyVendorcontactList  > 0 ){
	window.location.href = sourceleadUrl+"/exportVendorContactLists?"+tokenParamName+"="+tokenValue+"&vendorId="+vendorId+"&vendorStatus="+vendorStatus+"&invitationStatus="+invitationStatus+"&exportType=pdf";
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Vendor Contacts List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelTermination();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#client_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
</script>

<script>
function exportVendorContactToExcel(){
	var vendorId = [];
	$('input:checkbox[name=vendorNameById]:checked').each(function() 
    {
		vendorId.push($(this).val());
    });
	var vendorStatus = [];
	$('input:checkbox[name=vendorStatus]:checked').each(function() 
    {
		vendorStatus.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=vendorInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	var emptyVendorcontactList = $("#emptyVendorcontactList").val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(emptyVendorcontactList  > 0 ){
		window.location.href = sourceleadUrl+"/exportVendorContactLists?"+tokenParamName+"="+tokenValue+"&vendorId="+vendorId+"&vendorStatus="+vendorStatus+"&invitationStatus="+invitationStatus+"&exportType=xls";	
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Vendor Contacts List is Empty</p>";
		result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
		result = result +"		<b href='javascript:void(0)'><input type='button' value='Ok' onclick='cancelTermination();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTermination();'></b>";
		result = result +"	</div>";
		$("#popUp").append(result);
		$("#client_user_pop").slideDown(200);
		$(".project_inn_mask").fadeIn(200);
	}
}
</script>

<script type="text/javascript">
function cancelTermination(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<!-- Show success message after form submission script -->
<script>
	function msgFadein() {
		$("#venselector").fadeIn(200);
    setTimeout(function(){ 
	    	$('#venselector').fadeOut('slow'); }, 300);
	}
</script> 
<!-- End -->

<script>
  $("#selector").fadeIn(200);
  $('#selector').delay(2000).fadeOut('slow');
</script> 

<script>
	function editVendorMsgMsgFadein() {
		$("#editVendorMsg").fadeIn(200);
        $('#editVendorMsg').delay(2000).fadeOut('slow');
	}
</script> 

 <div class="advancedsearch-mask" onClick="closeAdsrch()"></div>

<script>
    // Close Advanced Search
    function closePop(){
    $("div.cand-advsrch-popup").hide();
    $(".mask").fadeOut();
    };
    function openPop(){
    $("div.cand-advsrch-popup").toggle();
    $(".mask").fadeIn();
    };
	function closeAdsrch(){
		$("div.cand-advsrch-popup").fadeOut();
		$(".mask").fadeOut();
	};
</script> 

<script>
function getStates(){
	var countryName = $("#country").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#state').empty();
	        	$('#state').append('<option value="">---select state---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#state').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#state').trigger("chosen:updated");
	            
	            $('#city').empty();
	            $('#city').append('<option value="">---select city---</option>');
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#state').empty();
    	$('#state').append('<option value="">---select state---</option>');
    	$('#state').trigger("chosen:updated");
    	$('#city').empty();
    	$('#city').append('<option value="">---select city---</option>');
        $('#city').trigger("chosen:updated");
	}
}
</script> 

<script>
function getCities(){
	var countryName = $("#country").val();
	var stateName = $("#state").val();
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?"+tokenParamName+"="+tokenValue+"&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#city').empty();
	        	$('#city').append('<option value="">---select city---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#city').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#city').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#city').empty();
    	$('#city').append('<option value="">---select city---</option>');
    	$('#city').trigger("chosen:updated");
	}
}
</script>  

<script type="text/javascript">	
function releasedJobsCountByOrgRelationId(orgRelationId){
	
	$.ajax({
    	type: "POST",
    	dataType: "json",
    	 url: '${sourceleadUrl}'+"/getReleasedJobsCountByOrgRelationId?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId, 
        success: function(data) {
        	var json = JSON.parse(data);
        	$('#releasedJobsCount'+orgRelationId).empty();
        	$('#releasedJobsCount'+orgRelationId).append(data);
        	
        },
        error: function(e){
        }
    });
}
</script> 

<script type="text/javascript">	
	function loadChosenScripts(){
		
		var selectedList = document.getElementById('listOfVendors').value;
		if(selectedList == "listbyContact" ){
			
			 document.getElementById("vendorContactListAdvanced").style.display="block";
			 document.getElementById("vendorListAdvanced").style.display="none";
			 
			 document.getElementById("enableVendorContactAdvancedSearch").style.display="block";
			 document.getElementById("enableVendorAdvancedSearch").style.display="none";
			 
		}else if(selectedList == "listbyVendor" ){
			
			 document.getElementById("vendorListAdvanced").style.display="block";
			 document.getElementById("vendorContactListAdvanced").style.display="none";
			 document.getElementById("enableVendorAdvancedSearch").style.display="block";
			 document.getElementById("enableVendorContactAdvancedSearch").style.display="none";
		}
		
/* 	    $('#country').val('${loginCountryName}'); 
	    $('#state').val(''); 
	    $('#city').val(''); 
	    
	    $("#ownerAdvanced").empty();
		 $("#ownerAdvanced").val('').trigger("chosen:updated");
	    
	    $("#orgAdvanced").val('').trigger("chosen:updated");
	    $("#orgNameAdvanced").val('').trigger("chosen:updated");
	    $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated");

	    $("#anyOfKeyword").empty();
	    $("#allKeywords").empty();
	    $("#excludeKeyWord").empty(); */
	}
</script>      


<script>
function vendorListCheck(searchValue,universalvendorSearchList,searchType,responderLocationId,orgRelationId,orgName,vendorKeyword){
    if(universalvendorSearchList=="universalVendorList")	 {
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
         type : "POST",
          url: sourceleadUrl+"/getUniversalSearchVendorList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&searchType="+searchType,   
          success :function(data) {
        	  preparedVendorsListFromElk(data);
         },
          
     }); 
  }else{
	  if(vendorKeyword == "vendorSearch" && vendorKeyword != null && vendorKeyword !="" && vendorKeyword != 'undefined'){
	    	vendorSideBar(responderLocationId,orgRelationId,orgName);
	    	//getVendorContactListsELK();
	    	getVendorListsElk();
	    }
	else{
		//getVendorContactListsELK();
		getVendorListsElk();
		}
  }
    
  }
  </script>
  
<script>
	function getVendorActions(){
		$(".action-pop").slideToggle(200);
	}	
	$("html").click(function(){
		$(".action-pop").slideUp(200);
	});
	$(".action, .action-pop").click(function(e){
	 	e.stopPropagation();
 	});	
</script>

  <script>
//Vendor
function closeVendorFilterPop(){
	$("#vendorFilterId").hide();
	$(".mask").fadeOut();
}
function vendorFileterPopUp(){
	 $("#vendorFilterId").toggle();
	 $(".mask").fadeIn();
}

function resetVendorFilter(){
	$("ul#tierFilter > li input:checkbox").prop("checked",false);
	
}

function resetAdvSearchFilter(){
	
	    $("#dataTableSearch").val('');
	    clearSearch();
	    getVendorList();
	 
}



//Vendor Contact
function vendorClientContactFilterPop(){
	$("#vendorContactFilterId").hide();
	$(".advancedsearch-mask").fadeOut();
}
function vendorContactFilterPopUp(){
	 $("#vendorContactFilterId").toggle();
	 $(".advancedsearch-mask").fadeIn();
}
function resetVendorContactFilter(){
	$("ul#statusFilter > li input:checkbox").prop("checked",false);
	$("ul#invitationFilter > li input:checkbox").prop("checked",false);
	$("#dataTableSearch").val('');
	clearSearch();
	getVendorContactList();
}
</script>
<!-- clinet related Fileter popup -->
          <div class="filter-popup advancedsearch-popup filterDynamic-Search-medium" id="vendorFilterId" style="display: none">
				<h5 class="headerAdsrch">Filters
					<a href="javascript:void(0)" class="close-div" onClick="closeVendorFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
					  <div class="col-md-6">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Tier</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="tierFilter">
									<li class="checkBox positionRelative" ><input class="mb0" id="c6"  type="checkbox" name="clinetLevel" value ="Tier-1"><label>Tier-1</label></li>
									<li class="checkBox positionRelative" ><input class="mb0" id="c7"  type="checkbox" name="clinetLevel" value ="Tier-2"><label>Tier-2</label></li>
									<li class="checkBox positionRelative" ><input class="mb0" id="c8"  type="checkbox" name="clinetLevel" value ="Tier-3"><label>Tier-3</label></li>
		               				<li class="checkBox positionRelative" ><input class="mb0" id="c8"  type="checkbox" name="clinetLevel" value ="Tier-4"><label>Tier-4</label></li>
		               				<li class="checkBox positionRelative" ><input class="mb0" id="c9"  type="checkbox" name="clinetLevel" value ="Tier-5"><label>Tier-5</label></li>
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
					  </div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="closeVendorFilterPop();getVendorList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetVendorFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>



          <div class="filter-popup advancedsearch-popup filterDynamic-Search-medium" id="vendorContactFilterId" style="display: none">
				<h5 class="headerAdsrch">Filters
					<a href="javascript:void(0)" class="close-div" onClick="vendorClientContactFilterPop()"><i
						class="fa fa-close"></i></a>
				</h5>
				<div class="addInner">
					<div class="row">
					  <div class="col-md-6">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Status</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="statusFilter">
									   <li class="checkBox positionRelative"><input id="c5" type="checkbox" class="mb0" name="vendorStatus"  value ="Active"><label>Active</label></li>
								       <li class="checkBox positionRelative"><input id="c6" type="checkbox" class="mb0" name="vendorStatus"  value ="In-Active"><label>In-Active</label></li>
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
						
						<div class="col-md-6">
						<div class="row">
						    <div class="col-md-12 mb">
								<label><strong>Invitation</strong></label>
								 <div class="clearfix">
									<ul class="list-unstyled" id="invitationFilter">
										<li class="checkBox positionRelative"><input id="c5" type="checkbox"  name="vendorInvitationStatus" class="mb0" value ="Sent"><label>Resend</label></li>
										<li class="checkBox positionRelative"><input id="c6" type="checkbox"  name="vendorInvitationStatus" class="mb0" value ="New"><label>Send</label></li>
										<li class="checkBox positionRelative"><input id="c7" type="checkbox"  name="vendorInvitationStatus" class="mb0" value ="Accepted"><label>Accepted</label></li>
										<li class="checkBox positionRelative"><input id="c8" type="checkbox"  name="vendorInvitationStatus" class="mb0" value ="Rejected"><label for="c8">Rejected</label></li>
			                        </ul>
							      </div>
						     </div> 
						  </div>
						</div>
					  </div>
					</div>
					<div class="clearfix advancedsearch-footer">
						<div class="pull-right mr">
							<input type="button" value="Apply" id="search_requiremet" onclick="vendorClientContactFilterPop();getVendorContactList();" class="btn btn-sm btn-success" />
							<input type="button" value="Reset" id="clear_search" onclick="resetVendorContactFilter();" class="btn btn-sm btn-primary" />
						</div>
					</div>
				</div>
  
  <script>
function getActions(){
	$(".action-pop").slideToggle(200);
	$(".newActPop").slideUp(200);
}	
$("html").click(function(){
	$(".action-pop").slideUp(200);
});
$(".action, .action-pop").click(function(e){
 	e.stopPropagation();
});	


function newActions(){
	$(".newActPop").slideToggle(200);
	$(".action-pop").slideUp(200);
}
$("html").click(function(){
	$(".newActPop").slideUp(200);
});
$(".overIcon, .newActPop").click(function(e){
 	e.stopPropagation();
});	


</script>
  