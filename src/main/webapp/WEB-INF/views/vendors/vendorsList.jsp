<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style type="text/css">
vendor_wrap {
overflow-x: auto;
}
.dropdown-select.paddingNone select {
border:none; background-color: none !important; padding:0 5px !important;
}
</style>
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
          <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Vendors list </div>
        </section>
		<!-- Project -->
		 <div id="loading"></div>

<div class="timesheet_out timesheet_background">
 <!-- Advanced Search -->
  <div class="clearfix">
  <div class="cand-advsrch pull-left candAdvsrch" id="advanceSearchDiv">
            <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="Advanced Search" onClick="openPop();loadChosenScripts();">Advanced Search&nbsp;&nbsp; <i class="fa fa-search"></i> </a>
          <div class="cand-advsrch-popup"> <a href="javascript:void(0)" class="close-div" onClick="closePop();"><i class="fa fa-close"></i></a>
          <form:form id="vendorList_form" name="vendorList_form"  enctype='multipart/form-data' >
           
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
                        <c:forEach var="vendor" items="${vendor_list}">
                                <option value="${vendor.clientName}">${vendor.clientName}</option>
                         </c:forEach>
                    </select>
                  </div>
                </div>
                
              </div>
              <div class="clearfix">
                
              </div>
              <div class="clearfix">
                <div class="col-md-4">
                  <label>Country </label>
                  <div class="clearfix">
                    <select class="chosen-select" name="country" id="country" field-name="Country"  >
						     <c:forEach var="cntry" items="${countryList}">
					                     <option value="${cntry.name}" ${cntry.name  == loginCountryName ? 'selected' : ''}>${cntry.name}</option>
 
				             </c:forEach>
				     </select>
                  </div>
                </div>
             <div class="col-md-4"> 
							  <label>State</label>
							  <div class="clearfix">
								  <select  class="chosen-select" name="state" id="state" field-name="State"  >
								        <option value="">---select state---</option>
								        <c:forEach var="state" items="${statesList}">
							                     <option value="${state.stateName}">${state.stateName}</option>
						                </c:forEach>
                                 </select>
							  </div>
						  </div>
						  <div class="col-md-4">
							  <label>City</label>
							  <div class="clearfix">
								  <select  class="chosen-select" name="city" id="city" field-name="City"  >
								      <option value="">---select city---</option>
                                  </select>
							  </div>
						  </div>
              </div>
              <div class="clearfix">
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
                                <option value="${vendor.clientName}">${vendor.clientName}</option>
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
            <div class="row form-footer custom_formfooter">
              <div class="pull-right mr"> 
               <!--  <input type="button" value="Search" id="search_vendor" onclick="getVendorList();" class="btn btn-sm btn-success"/>
                 <input type="button" value="Clear" id="clear_search" onclick="clearSearch();" class="btn btn-sm btn-primary"/> -->
              <div id="enableVendorAdvancedSearch"> 
              	<input type="button" value="Search" id="search_client" onclick="getVendorList();" class="btn btn-sm btn-success"/>
              	<input type="button" value="Clear" id="clear_search" onclick="closePop();" class="btn btn-sm btn-primary"/>
              	</div>
              	<div id="enableVendorContactAdvancedSearch"> 
              	<input type="button" value="Search" id="search_client" onclick="getVendorContactList();" class="btn btn-sm btn-success"/>
              	<input type="button" value="Clear" id="clear_search" onclick="closePop();" class="btn btn-sm btn-primary"/>
              	</div>
              
            </div>
            </form:form>
          </div>
        </div>
        </div>
<!-- Advanced Search Ends -->





<div class="vendor_wrap vendor_wrap_shadownone minHeight_225">

     

	<!-- <div class="timesheet_head datatbl-filters customtimesheet-head"> -->
	<div class="timesheet_head datatbl-filters topfilters">
        	<ul>
        	
        	<%-- <li  id="filter1ApprovePop"><i class="fa fa-filter"></i><span>Vendor</span><i class="glyphicon glyphicon-play" id="vendorsList" ></i>|
		                	<div id="filter1ApprovedPop" class="approved_pop">
		                    	<ul>
		                    	  <c:forEach var="report" items="${vendor_list}">  
		                        	<li><input  id="vendorName${report.id}" name="vendorNameById" value ="${report.id}"  onclick="getVendorContactLists();"  type="checkbox"><label title="${report.clientName}">${report.clientName}</label></li>
								  </c:forEach>
		                        </ul>
		                    </div>                	
		                </li> --%>
	               
		                <li id= "filter3ApprovePop" style="display:none"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" id="vendorSatatus"></i>|
	                	<div id="filter3ApprovedPop" class="approved_pop">
	                    	<ul>
	                        	<li><input id="c5" type="checkbox"  name="vendorStatus"  value ="Active"      onclick="getVendorContactList();"  ><label>Active</label></li>
								<li><input id="c6" type="checkbox"  name="vendorStatus"  value ="In-Active"   onclick="getVendorContactList();"><label>In-Active</label></li>
								<!-- <li><input id="c7" type="checkbox"  name="vendorStatus"  value ="New"         onclick="getVendorContactLists();"><label>New</label></li>
								<li><input id="c8" type="checkbox"  name="vendorStatus"  value ="Negotiation" onclick="getVendorContactLists();"><label>Negotiation</label></li>
	               				<li><input id="c9" type="checkbox"  name="vendorStatus"  value ="Contract"  onclick="getVendorContactLists();"><label>Contract</label></li> -->
	                        </ul>
	                      </div>                	
	                  </li>
	                  <li  id="filter4ApprovePop" style="display:none"><i class="fa fa-filter"></i><span>Invitation</span><i class="glyphicon glyphicon-play" id="vendorInvitation"></i>|
	                	<div id="filter4ApprovedPop" class="approved_pop">
	                    	<ul>
	                        	<li><input id="c5" type="checkbox"  name="vendorInvitationStatus"  value ="Sent"      onclick="getVendorContactList();"  ><label>Resend</label></li>
								<li><input id="c6" type="checkbox"  name="vendorInvitationStatus"  value ="New"   onclick="getVendorContactList();"><label>Send</label></li>
								<li><input id="c7" type="checkbox"  name="vendorInvitationStatus"  value ="Accepted"         onclick="getVendorContactList();"><label>Accepted</label></li>
								<li><input id="c8" type="checkbox"  name="vendorInvitationStatus"  value ="Rejected" onclick="getVendorContactList();"><label>Rejected</label></li>
	                        </ul>
	                      </div>                	
	                  </li>
                  
	                	 
						<li id="contactLevel" >
					   <div id="filter2ApprovePop"><i class="fa fa-filter"></i><span>Tier</span><i class="glyphicon glyphicon-play" style="right:20px;"></i></div>
		                	<div id="filter2ApprovedPop" class="approved_pop">
		                    	<ul>
									<li><input id="c6"  type="checkbox" name="clinetLevel" value ="Tier-1"  onclick="getVendorList();"><label>Tier-1</label></li>
									<li><input id="c7"  type="checkbox" name="clinetLevel" value ="Tier-2"  onclick="getVendorList();"><label>Tier-2</label></li>
									<li><input id="c8"  type="checkbox" name="clinetLevel" value ="Tier-3"  onclick="getVendorList();"><label>Tier-3</label></li>
		               				<li><input id="c9"  type="checkbox" name="clinetLevel" value ="Tier-4"  onclick="getVendorList();"><label>Tier-4</label></li>
		               				<li><input id="c10"  type="checkbox" name="clinetLevel"value ="Tier-5"  onclick="getVendorList();"><label>Tier-5</label></li>
		                        </ul>
		                    </div>                	
		                </li>  
		                    
		          <sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR_CONTACT')">
				       <li><a href="<c:url value='/addVendorContact'/>"><i class="fa fa-plus"></i>Contact</a></li>
					</sec:authorize>
					
				<sec:authorize access="hasAnyRole('ROLE_CREATE_VENDOR')"> 
                       <li><a href="<c:url value='/addVendor'/>"><i class="fa fa-plus"></i>Vendor</a></li>	
                       </sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_VENDOR_BULK_IMPORTS','ROLE_VENDOR_CONTACT_BULK_IMPORTS')">
                       <li title="Import"><a href="<c:url value='/uploadVendorForm'/>"><i class="glyphicon glyphicon-import expIcon"></i>&nbsp;</a></li>
                 	   </sec:authorize> 
                 
                 <sec:authorize access="hasRole('ROLE_EXPORT_VENDORS')"> 
                       	 <li title="Export" id="vendorImports" >
		                     <a  id="vendor-imports"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>
			                    <div class="export_pop">
				                <ul>
				                <li onclick="exportVendorToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp; PDF </li>
				                <li onclick="exportVendorToExcel();"><i class="fa fa-file-excel-o"></i> &nbsp;&nbsp; Excel</li>
				       </ul>
				    </div>
		            </li> 
		          </sec:authorize>
		           
		          <sec:authorize access="hasRole('ROLE_EXPORT_VENDOR_CONTACTS')"> 
		            <li title="Export" id="VendorContactImports" style="display:none">
		                     <a  id="export-popcon"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>
			                    <div class="export_pop">
				                <ul>
				                <li onclick="exportVendorContactToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp; PDF</li>
				                <li onclick="exportVendorContactToExcel();"><i class="fa fa-file-excel-o"></i>&nbsp;&nbsp; Excel</li>
				       </ul>
				    </div>
		            </li> 
		          </sec:authorize>
		            
            </ul>
            <div class="clearfix"></div>
             <div class='remove_search'>
        		          <input type='text' placeholder='search' id='dataTableSearch'/>
                </div> 
        </div>
        
    <div class="clients_wrap_table datatable_filter">
	    <input type="hidden" name="emptyVendorcontactList" id="emptyVendorcontactList">
	    <input type="hidden" name="emptyVendorList" id="emptyVendorList">
    
    	<table id="vendorList" class="display" cellspacing="0" width="100%"> </table>
    	<table id="vendorContactList" class="display" cellspacing="0" width="100%"> </table> 
   </div>	
   
</div>

<div class="side_mask"><div class="proj_close"></div></div>

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
/* 	$('#country').val('').trigger('chosen:updated'); */
	$('#state').val('').trigger('chosen:updated');
	$('#city').val('').trigger('chosen:updated');
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


<script type="text/javascript">
    	   var config = {
    				  '.chosen-select'           : {},
    				  '.chosen-select-deselect'  : {allow_single_deselect:true},
    				  '.chosen-select-no-single' : {disable_search_threshold:10},
    				  '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    				  '.chosen-select-width'     : {width:"95%"}
    	   }
			//Adding Customized Any Of Keyword to the list 
		    var select, chosen;
		    select = $("#anyOfKeyword");
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab  To Add Keyword:' });
		    chosen = select.data('chosen');
		    chosen.search_field.on('keydown keyup', function(e)
		    {
		    	var anyKeyword = this.value.replace(",","");
		        if (tagItBykeyForSkills(e) && (anyKeyword != null  && anyKeyword != '' && anyKeyword != 'undefined'))
		        {
		        	preventTabkey(e); 
		        	var par = this.parent;
		            var option = $("<option>").val(anyKeyword).text(anyKeyword);
		            select.append(option);
		            select.find(option).prop('selected', true);
                    $("#anyOfKeyword option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#anyOfKeyword").trigger("chosen:updated");
	        		
		        }
		    });
		    
		  //Adding Customized Any Of Keyword to the list
		    var selectone, chosenone;
		    selectone = $("#allKeywords");
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab  To Add Keyword:' });
		    chosenone = selectone.data('chosen');
		    chosenone.search_field.on('keydown keyup', function(e1)
		    {
		    	var allKeyword = this.value.replace(",","");
		        if (tagItBykeyForSkills(e1) && (allKeyword != null  && allKeyword != '' && allKeyword != 'undefined'))
		        {
		        	preventTabkey(e1); 
		        	var par = this.parent;
		            var option = $("<option>").val(allKeyword).text(allKeyword);
		            selectone.append(option);
		            selectone.find(option).prop('selected', true);
                    $("#allKeywords option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#allKeywords").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    var selecttwo, chosentwo;
		    selecttwo = $("#excludeKeyWord");
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab  To Add Keyword:' });
		    chosentwo = selecttwo.data('chosen');
		    chosentwo.search_field.on('keydown keyup', function(e2)
		    {
		    	var excludeKeyword = this.value.replace(",","");
		        if (tagItBykeyForSkills(e2) && (excludeKeyword != null  && excludeKeyword != '' && excludeKeyword != 'undefined'))
		        {
		        	preventTabkey(e2); 
		        	var par = this.parent;
		            var option = $("<option>").val(excludeKeyword).text(excludeKeyword);
		            selecttwo.append(option);
		            selecttwo.find(option).prop('selected', true);
                    $("#excludeKeyWord option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#excludeKeyWord").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    // These are choosen fields for vendor List Multiselect
		    
		    
		    // This is for vendorName 
		    
		    var selectthree, chosenthree;
		    selectthree = $("#orgNameAdvanced");
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab  To Add Vendor Name:' });
		    chosenthree = selectthree.data('chosen');
		    chosenthree.search_field.on('keydown keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if (tagItBykeyForSkills(e3) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	preventTabkey(e3); 
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectthree.append(option);
		            selectthree.find(option).prop('selected', true);
                    $("#orgNameAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#orgNameAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		   // This is for Created By 
		    var selectcreatedByAdvanced, chosencreatedByAdvanced;
		    selectcreatedByAdvanced = $("#createdByAdvanced");
		    selectcreatedByAdvanced.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Document Type:' });
		    chosencreatedByAdvanced = selectcreatedByAdvanced.data('chosen');
		    chosencreatedByAdvanced.search_field.on('keydown keyup', function(e4)
		    {
		    	var desg = this.value.replace(",","");
		        if (tagItBykeyForSkills(e4) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	preventTabkey(e4); 
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectcreatedByAdvanced.append(option);
		            selectcreatedByAdvanced.find(option).prop('selected', true);
                    $("#createdByAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#createdByAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    
		    
		    //  This is for contact Name
		    var selectcontactNameAdvanced, chosencontactNameAdvanced;
		    selectcontactNameAdvanced = $("#contactNameAdvanced");
		    selectcontactNameAdvanced.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Contact Name:' });
		    chosencontactNameAdvanced = selectcontactNameAdvanced.data('chosen');
		    chosencontactNameAdvanced.search_field.on('keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectcontactNameAdvanced.append(option);
		            selectcontactNameAdvanced.find(option).prop('selected', true);
                    $("#contactNameAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#contactNameAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    //  This is for Email
		    var selectemailAdvanced, chosenemailAdvanced;
		    selectemailAdvanced = $("#emailAdvanced");
		    selectemailAdvanced.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Email:' });
		    chosenemailAdvanced = selectemailAdvanced.data('chosen');
		    chosenemailAdvanced.search_field.on('keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectemailAdvanced.append(option);
		            selectemailAdvanced.find(option).prop('selected', true);
                    $("#emailAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#emailAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    //  This is for Phone Number
		    var selectphoneNumberAdvanced, chosenphoneNumberAdvanced;
		    selectphoneNumberAdvanced = $("#phoneNumberAdvanced");
		    selectphoneNumberAdvanced.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Phone Number:' });
		    chosenphoneNumberAdvanced = selectphoneNumberAdvanced.data('chosen');
		    chosenphoneNumberAdvanced.search_field.on('keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectphoneNumberAdvanced.append(option);
		            selectphoneNumberAdvanced.find(option).prop('selected', true);
                    $("#phoneNumberAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#phoneNumberAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    //  This is for Owner
		    var selectownerAdvanced, chosenownerAdvanced;
		    selectownerAdvanced = $("#ownerAdvanced");
		    selectownerAdvanced.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Owner:' });
		    chosenownerAdvanced = selectownerAdvanced.data('chosen');
		    chosenownerAdvanced.search_field.on('keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectownerAdvanced.append(option);
		            selectownerAdvanced.find(option).prop('selected', true);
                    $("#ownerAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#ownerAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		//  This is for Vendor
		    var selectvendorAdvanced, chosenvendorAdvanced;
		    selectvendorAdvanced = $("#orgAdvanced");
		    selectvendorAdvanced.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Vendor:' });
		    chosenvendorAdvanced = selectvendorAdvanced.data('chosen');
		    chosenvendorAdvanced.search_field.on('keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectvendorAdvanced.append(option);
		            selectvendorAdvanced.find(option).prop('selected', true);
                    $("#orgAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#orgAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		    
		    
		    
		  
		      $("#country").on('change', function (e) {
		    	   getStates();
		       });
		       $("#state").on('change', function (e) {
		    	   getCities();
		       });
				for (var selector in config) {
				  $(selector).chosen(config[selector]);
				}
				$('.chosen-select').each(function() {
				var $this = $(this);
				$this.next().css({'width': '100%'});
				})
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
		$("#loading"+orgSpocId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
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
function terminateSpoc1(spocId, orgRelationID){
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<p>Do you want to delete this vendor contact?</p>";
	result = result +"		<textarea id='reasonForSpoc' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Reason' rows='2' onkeypress='vendorDashboardRemoveSpacesForRejectReason();'></textarea>";
	result = result + ' <div id="reasonForVendorSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='saveReason1("+spocId+","+orgRelationID+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelTerminationReson();'></b>";
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
function saveReason1(spocId, orgRelationID){
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
		 	url: sourceleadUrl+"/terminateSpoc?"+tokenParamName+"="+tokenValue+"&spocId="+spocId+"&reason="+reason+"&userId="+userId+"&orgLocationId="+orgLocationId+"&responderType=Vendor",	    	   
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
	 $("#loading"+spocId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	
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
		   			result = result	+ '<li class="current"><a href="#tab-1" id="spocDetailsId" onClick="vendorDetailsbySpocId('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+');">Details</a></li>';
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

		vendorDetailsbySpocId(spocId,ownerId,spocUserId,tempOwnerId);
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
		result = result + '<li><a href="#tab-2" onclick="vendorContacts('+ orgRelationId + ','+vendorLocationId+');">Contacts</a></li>'; 
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
		
		
		 result = result + '<li><a href="#tab-4" onClick="getVendorInvoiceList('+orgRelationId+','+vendorLocationId+');">Billing</a></li>';
		
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
		
		vendorDetailsbyLocationId(vendorLocationId,orgRelationId);
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
					url : sourceleadUrl+"/getClientAndVendorOrgDeatilsById?"+tokenParamName+"="+tokenValue+"&vendorLocationId="+vendorLocationId+"&orgRelationId="+orgRelationId,
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
								result = result + '<strong>Website:</strong>';
								if(json.webSite !=null && json.webSite !='' && json.webSite !='undefined'){
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
							if(json.zipCode != null && json.zipCode !='' && json.zipCode !='undefined'){
								result = result + '<span>'+json.zipCode+'</span><div class="clearfix"></div>';
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
							result = result + '<strong>Address :</strong>';
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
		 document.getElementById("contactLevel").style.display="block";
// 		 document.getElementById("vendorsList").style.display="none";
		 document.getElementById("vendorSatatus").style.display="none";
		 document.getElementById("vendorInvitation").style.display="none";
         '<sec:authorize access="hasRole(\'ROLE_EXPORT_VENDORS\')">'; 
		 document.getElementById("vendorImports").style.display="block";
		 '</sec:authorize> ';
		'<sec:authorize access="hasRole(\'ROLE_EXPORT_VENDOR_CONTACTS\')">'; 
		 document.getElementById("VendorContactImports").style.display="none";
		 '</sec:authorize> ';
	//	 document.getElementById("filter1ApprovePop").style.display="none";
		 document.getElementById("filter3ApprovePop").style.display="none";
		 document.getElementById("filter4ApprovePop").style.display="none";
		 //getVendorLists();
		 document.getElementById("advanceSearchDiv").style.display="block";
		 $("#dataTableSearch").val("");
// 		 getVendorListsElk();
		 getVendorList();
	 }
	 if(sel.value=="listbyContact"){
		 document.getElementById("vendorContactList").style.display="block";
		 document.getElementById("vendorContactList").style.display="table";
		 document.getElementById("vendorList").style.display="none";
		 document.getElementById("vendorList_wrapper").style.display="none";
		 //document.getElementById("vendorContactList_wrapper").style.display="block";
		 document.getElementById("contactLevel").style.display="none";
// 		 document.getElementById("vendorsList").style.display="block";
		 document.getElementById("vendorSatatus").style.display="block";
		 document.getElementById("vendorInvitation").style.display="block";
		 '<sec:authorize access="hasRole(\'ROLE_EXPORT_VENDORS\')">'; 
		 document.getElementById("vendorImports").style.display="none";
		 '</sec:authorize> ';
		 '<sec:authorize access="hasRole(\'ROLE_EXPORT_VENDOR_CONTACTS\')">'; 
		 document.getElementById("VendorContactImports").style.display="block";
		 '</sec:authorize> ';
	//	 document.getElementById("filter1ApprovePop").style.display="block";
		 document.getElementById("filter3ApprovePop").style.display="block";
		 document.getElementById("filter4ApprovePop").style.display="block";
		 //getVendorContactLists();
// 		 getVendorContactListsELK();
document.getElementById("advanceSearchDiv").style.display="block";
		$("#dataTableSearch").val("");
		loadVendorContactDataTable("${universalVendorList}");
	 }
	 
}
</script>

<jsp:include page="../masterFooter.jsp" />
<script src="resources/js/jquery-fun.js"></script> 
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
    
    $("#filter2ApprovePop").click(function(){
		$("#filter2ApprovedPop").slideToggle(200);
		$(".export_pop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter2ApprovedPop").slideUp(200);
    });
    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    $("#filter3ApprovePop").click(function(){
		$("#filter3ApprovedPop").slideToggle(200);
		$("#filter1ApprovedPop, #filter4ApprovedPop, .export_pop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter3ApprovedPop").slideUp(200);
    });
    $("#filter3ApprovedPop, #filter3ApprovePop").click(function(e) {  
        e.stopPropagation();
    });	
    
    $("#filter4ApprovePop").click(function(){
		$("#filter4ApprovedPop").slideToggle(200);
		$("#filter3ApprovedPop, #filter1ApprovedPop, .export_pop").slideUp(200);
	});
    $("html").click(function() {
        $("#filter4ApprovedPop").slideUp(200);
    });
    $("#filter4ApprovedPop, #filter4ApprovePop").click(function(e) {  
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
		$(".side_mask").fadeIn(200);
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
			getVendorListsElk();
   		}
	});	
});

// project side bar tabs
$(document).ready(function() {
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
	 $("#loading"+spocId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
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
	 $("#loading"+spocId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
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
	    $("#filter2ApprovedPop").slideUp();
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

<script>
    // Close Advanced Search
    function closePop(){
    $("div.cand-advsrch-popup").hide();
    };
    function openPop(){
    $("div.cand-advsrch-popup").toggle();
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
		
	    $('#country').val('${loginCountryName}'); 
	    $('#state').val(''); 
	    $('#city').val(''); 
	    
	    $("#orgAdvanced").val('').trigger("chosen:updated");
	    $("#orgNameAdvanced").val('').trigger("chosen:updated");
	    $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated");

	    $("#anyOfKeyword").empty();
	    $("#allKeywords").empty();
	    $("#excludeKeyWord").empty();
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