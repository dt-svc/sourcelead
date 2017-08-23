<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="editClientSpocDetails.jsp" />
<jsp:include page="clientDetails.jsp" />	
<jsp:include page="clientContacts.jsp" />
<jsp:include page="clientDocuments.jsp" />	
<jsp:include page="clientSpocDocuments.jsp" />	
<jsp:include page="submissionTemplateList.jsp" /> 
<jsp:include page="clientContracts.jsp" />
<jsp:include page="clientBilling.jsp" /> 
<jsp:include page="requirementReleasedList.jsp" /> 
<jsp:include page="clientNotes.jsp" /> 
<jsp:include page="clientListDatatable.jsp" />
<jsp:include page="clientContactsDataTable.jsp" />
<style type="text/css">
clients_wrap {
overflow-x: auto;
}
</style>
<body>

 <div id="loading"></div>
	<!-- Content Area -->
	<section class="content-wraper">
		<section class="content-header clearfix">
		  <c:if test="${!empty msg}">
	        <div class="success_pop" id="selector"  style="display: none;">
		         <i class="fa fa-check-circle"></i><span>${msg}</span>
			</div>
	     </c:if>
	    <div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
       </div>
			<div class="pull-left">
				<h2>Clients<span class="select-fild">
				    <select id="listOfVendors" onchange="listChange(this); flushCheckBoxVal();">
                  		 <option value="listbyVendor" selected="selected">List by client</option>
                        <option value="listbyContact" >List by contact</option> 
                    </select>
				</span>
				</h2>
			</div>

			<div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>&raquo;Client List</div>

		</section>

		<!-- Project -->
		
			
			<!-- Advanced Search -->
  <div class="timesheet_out">
  <div class="cand-advsrch pull-left candAdvsrch">
            <a href="javascript:void(0)" class="btn btn-primary btn-sm" title="Advanced Search" onClick="openPop();loadChosenScripts();"> Advanced Search&nbsp;&nbsp; <i class="fa fa-search"></i> </a>
            <form:form id="clientlist_form" name="clientlist_form"  enctype='multipart/form-data' >
          <div class="cand-advsrch-popup"> <a href="javascript:void(0)" class="close-div" onClick="closePop()"><i class="fa fa-close"></i></a>
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
				  	 <select class="chosen-select form-control input-sm" multiple data-placeholder="Exclude Keyword" id="excludeKeyWord" name="excludeKeyWord"></select>
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
		                  <label>Client Name</label>
		                  <div class="clearfix">
		                    <select class="chosen-select form-control input-sm" multiple data-placeholder="Client Name" id="orgNameAdvanced" name="orgNameAdvanced">
		                    	<c:forEach var="client" items="${clientsList}">
                            	    <option value="${client.clientName}">${client.clientName}</option>
                         		</c:forEach>
		                    </select>
                	  </div>
		                </div>
		                
		              </div>
		              <div class="clearfix">
		                
		              </div>
		              <div class="clearfix">
		                <div class="col-md-4">
		                  <label>Country</label>
		                  <div class="clearfix">
		                    <select class="chosen-select" name="country" id="countrySearch" field-name="Country"  >
						     <c:forEach var="cntry" items="${countryList}">
					                      <option value="${cntry.name}" ${cntry.name  == loginCountryName ? 'selected' : ''}>${cntry.name}</option>
 
				             </c:forEach>
				           </select>
		                  </div>
		                </div>
		                 <div class="col-md-4"> 
							  <label>State</label>
							  <div class="clearfix">
								  <select  class="chosen-select" name="state" id="stateSearch" field-name="State"  >
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
								  <select  class="chosen-select" name="city" id="citySearch" field-name="City"  >
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
		                <h4>Client Contact:</h4>
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
		                   <select class="chosen-select form-control input-sm" data-placeholder="Owner" id="ownerAdvanced" name="ownerAdvanced"  multiple>
                           </select>
		                  </div>
		                </div>
		                
		                <div class="col-md-4">
		                  <label>Client</label>
		                  <div class="clearfix">
		                     <select class="chosen-select form-control input-sm" data-placeholder="Client" id="orgAdvanced" name="orgAdvanced" multiple>
                             	<c:forEach var="client" items="${clientsList}">
                            	    <option value="${client.clientName}">${client.clientName}</option>
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
									</select>
						 </div>  
              	</div>
				</div>
             
                <div class="col-md-4">
                  <label>Invitation</label>
                  <div class="clearfix">
                		 <select class="chosen-select form-control input-sm" multiple data-placeholder="Invitation" id="invitationAdvanced" name="invitationAdvanced">
											<option value="Sent">Resend</option>
											<option value="New">Send</option>
											<option value="Accepted">Accepted</option>
											<option value="Rejected">Rejected</option>
						</select>
                  </div>
                </div>
            </div>  
              
            </div>
            <div class="row form-footer mt">
              <div class="pull-right mr"> 
              	<input type="button" value="Search" id="search_client" onclick="getClientList();" class="btn btn-sm btn-success"/>
              	   <input type="button" value="Clear" id="clear_search" onclick="clearSearch();" class="btn btn-sm btn-primary"/>
            </div>
          </div>
          </form:form>
        </div>
        </div>
		<!-- Advanced Search Ends -->
		<div class="clients_wrap vendor_wrap_shadownone minHeight_225">
<!-- 				<div class="timesheet_head datatbl-filters customtimesheet-head hdr-styles hdrStyles">
 -->				<div class="timesheet_head datatbl-filters topfilters">
					<ul>
	               
		                <li  id="filter3ApprovePop" style="display:none"><i class="fa fa-filter"></i><span>Status</span><i class="glyphicon glyphicon-play" id="clientSatatus"></i>|
	                	<div id="filter3ApprovedPop" class="approved_pop">
	                    	<ul>
	                        	<li><input id="c5" type="checkbox"  name="clientStatus"  value ="Active"      onclick="getVendorContactLists();"  ><label>Active</label></li>
								<li><input id="c6" type="checkbox"  name="clientStatus"  value ="In-Active"   onclick="getVendorContactLists();"><label>In-Active</label></li>
	                        </ul>
	                      </div>                	
	                  </li>
	                  <li  id="filter4ApprovePop" style="display:none"><i class="fa fa-filter"></i><span>Invitation</span><i class="glyphicon glyphicon-play" id="clientInvitation"></i>|
	                	<div id="filter4ApprovedPop" class="approved_pop">
	                    	<ul>
	                        	<li><input id="c5" type="checkbox"  name="clientInvitationStatus"  value ="Sent"      onclick="getVendorContactLists();"  ><label>Resend</label></li>
								<li><input id="c6" type="checkbox"  name="clientInvitationStatus"  value ="New"   onclick="getVendorContactLists();"><label>Send</label></li>
								<li><input id="c7" type="checkbox"  name="clientInvitationStatus"  value ="Accepted"         onclick="getVendorContactLists();"><label>Accepted</label></li>
								<li><input id="c8" type="checkbox"  name="clientInvitationStatus"  value ="Rejected" onclick="getVendorContactLists();"><label for="c8">Rejected</label></li>
	                        </ul>
	                      </div>                	
	                  </li>
	                  
		                  <li id="contactLevel" >
		                  <div id="filter2ApprovePop"><i class="fa fa-filter"></i><span>Tier</span><i class="glyphicon glyphicon-play" style="right:20px;"></i></div>
		                	<div id="filter2ApprovedPop" class="approved_pop">
		                    	<ul>
									<!-- <li><input id="c5"  type="checkbox" name="clinetLevel" value ="All" onclick="getClientDetails();"  ><label for="c5">All</label></li> -->
									<li><input id="c6"  type="checkbox" name="clinetLevel" value ="Tier-1"  onclick="getClientList();"><label>Tier-1</label></li>
									<li><input id="c7"  type="checkbox" name="clinetLevel" value ="Tier-2"  onclick="getClientList();"><label>Tier-2</label></li>
									<li><input id="c8"  type="checkbox" name="clinetLevel" value ="Tier-3"  onclick="getClientList();"><label>Tier-3</label></li>
		               				<li><input id="c8"  type="checkbox" name="clinetLevel" value ="Tier-4"  onclick="getClientList();"><label>Tier-4</label></li>
		               				<li><input id="c9"  type="checkbox" name="clinetLevel" value ="Tier-5"  onclick="getClientList();"><label>Tier-5</label></li>
		                        </ul>
		                    </div>                	
		                </li>
                		<sec:authorize access="hasAnyRole('ROLE_CREATE_CLIENT_CONTACT')"> 
                        <li><a href="<c:url value='/addClientContact'/>"><i class="fa fa-plus"></i>Contact</a></li>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_CREATE_CLIENT')"> 
							<li><a href="<c:url value='/client'/>"><i class="fa fa-plus"></i>Client</a></li>
						</sec:authorize>
						 <sec:authorize access="hasAnyRole('ROLE_CLIENT_BULK_IMPORTS','ROLE_CLIENT_CONTACT_BULK_IMPORTS')"> 
						 <li title="Import"><a href="<c:url value='/uploadClientForm'/>"><i class="glyphicon glyphicon-import expIcon"></i>&nbsp;</a></li>
						 </sec:authorize>
						
						 <sec:authorize access="hasRole('ROLE_EXPORT_CLIENTS')"> 
						   <li id="clientImports"  title="Export">
		                      <a  id="client-imports"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>
			                    <div class="export_pop">
				                   <ul class="widScr">
				                     <li onclick="exportClientToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;PDF</li>
				                     <li onclick="exportClientToExcel();"><i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;Excel </li>
				                   </ul>
				              </div>
		                  </li> 
		            </sec:authorize>
		            
		             <sec:authorize access="hasRole('ROLE_EXPORT_CLIENT_CONTACTS')"> 
		            <li id="ContactImports" title="Export" style="display:none">
		                     <a  id="export-popcon"><i class="glyphicon glyphicon-export expIcon"></i>&nbsp;</a>
			                    <div class="export_pop">
				                <ul class="widScr">
				                <li onclick="exportContactToPdf();"><i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp; PDF</li>
				                <li onclick="exportContactToExcel();"><i class="fa fa-file-excel-o"></i>&nbsp;&nbsp; Excel</li>
				       </ul>
				    </div>
		            </li> 
                    </sec:authorize>
                              
						<div class="clearfix"></div>
					</ul>
					<div class="clearfix"></div>
					 <div class='remove_search'>
        		          <input type='text' placeholder='search' id='dataTableSearch'/>
                </div> 
				</div>
				
				<div class="clients_wrap_table datatable_scroll datatable_filter" id ="vendorListFilter">
			      <input type="hidden" name="clientContactLength" id="clientContactLength" >
			      <input type="hidden" name="emptyClientList" id="emptyClientList" >
			      
		     	  <table id="clientList" class="display" cellspacing="0" width="100%"> </table>
		     	 <table id="vendorContactList" class="display" cellspacing="0" width="100%"> </table> 
				</div>
				<div id="popUp"></div>
			</div>
		</div>


       <div class="side_mask"><div class="proj_close"></div></div>
		<div class="clients_side_wrap">
			<div class="clients_side_wrap_inn">
				<h1 id="clientNameDiv"></h1>
				<div class="proj_close">
					<i></i>
				</div>
				<div id="clientSideDiv"></div>
			</div>
         </div>
	</section>
</body>



<script type="text/javascript">
window.onload = function () {
	/* clientOnLoad("${clientSearchResult}","${clientKeyword}","${responderLocationId}","${orgRelationId}","${orgName}");  */
	 loadClientDatatable("${clientSearchResult}","${clientKeyword}","${responderLocationId}","${orgRelationId}","${orgName}"); 
 };
</script>



<script type="text/javascript">
function clientOnLoad(clientSearchResult,clientKeyword,responderLocationId,orgRelationId,orgName){
	if(clientSearchResult =="clientSearchResult"){
		getClientUniversalSearchList("${searchValue}","${searchType}");
	}else{
		if(clientKeyword=="clientSearch"){
			clientSideBar(responderLocationId,orgRelationId,orgName);
			//getVendorContactListsELK();
			getClientDetailsELK();
		}
		
		/* getVendorContactLists(); */
		else{
			//getVendorContactListsELK();
			getClientDetailsELK();
		}
		listChange("listbyVendor");
		document.getElementById('listOfVendors').value = 'listbyVendor';
	}
	
}
</script>
<script type="text/javascript">
function getClientUniversalSearchList(searchValue,searchType){
	var sourceleadUrl ='${sourceleadUrl}';
	 $.ajax({
        type : "POST",
         url: sourceleadUrl+"/getUniversalClientList?${_csrf.parameterName}=${_csrf.token}&searchValue="+searchValue+"&searchType="+searchType,       
         success :function(data) {
        	 preparedClientListFromElk(data);
        },
    }); 

}
</script>

<script>
function clearSearch(){
	document.getElementById("clientlist_form").reset();
	$('#anyOfKeyword').val('').trigger('chosen:updated');
	$('#allKeywords').val('').trigger('chosen:updated');
	$('#excludeKeyWord').val('').trigger('chosen:updated');
	$('#orgNameAdvanced').val('').trigger('chosen:updated');
	/* $('#countrySearch').val('').trigger('chosen:updated'); */
	$('#stateSearch').val('').trigger('chosen:updated');
	$('#citySearch').val('').trigger('chosen:updated');
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
		    select.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:' });
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
		    selectone.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:' });
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
		    selecttwo.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) Or Tab To Add Keyword:' });
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
		    selectthree.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Client Name:' });
		    chosenthree = selectthree.data('chosen');
		    chosenthree.search_field.on('keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
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
		    chosencreatedByAdvanced.search_field.on('keydown keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if (tagItBykeyForSkills(e3)  && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	preventTabkey(e3); 
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
		    var selectclientAdvanced, chosenclientAdvanced;
		    selectclientAdvanced = $("#orgAdvanced");
		    selectclientAdvanced.chosen({ no_results_text: 'Press Enter-Key Or Comma(,) To Add Client:' });
		    chosenclientAdvanced = selectclientAdvanced.data('chosen');
		    chosenclientAdvanced.search_field.on('keyup', function(e3)
		    {
		    	var desg = this.value.replace(",","");
		        if ((e3.which == 13 || e3.which == 188) && (desg != null  && desg != '' && desg != 'undefined'))
		        {
		        	var par = this.parent;
		            var option = $("<option>").val(desg).text(desg);
		            selectclientAdvanced.append(option);
		            selectclientAdvanced.find(option).prop('selected', true);
                    $("#orgAdvanced option").each(function(){
                          $(this).siblings("[value='"+ this.value+"']").remove();
                    });
	        		$("#orgAdvanced").trigger("chosen:updated");
	        		
		        }
		    });
		  

		      $("#countrySearch").on('change', function (e) {
		    	   getStates();
		       });
		       $("#stateSearch").on('change', function (e) {
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



<script type="text/javascript">	
	function loadChosenScripts(){
		
		var selectedList = document.getElementById('listOfVendors').value;
		
		if(selectedList == "listbyContact" ){
			
			 document.getElementById("vendorContactListAdvanced").style.display="block";
			 document.getElementById("vendorListAdvanced").style.display="none";
			 
		}else if(selectedList == "listbyVendor" ){
			
			 document.getElementById("vendorListAdvanced").style.display="block";
			 document.getElementById("vendorContactListAdvanced").style.display="none";
		}
	    
	    
	    $("#citySearch").empty();
		 $("#citySearch").val('').trigger("chosen:updated");
		 
	    $('#orgNameAdvanced').val(''); 
	    
	    $("#anyOfKeyword").val('').trigger("chosen:updated");
	    $("#allKeywords").val('').trigger("chosen:updated");
	    $("#excludeKeyWord").val('').trigger("chosen:updated");
	    $("#orgNameAdvanced").val('').trigger("chosen:updated");

	    $("#anyOfKeyword").empty();
	    $("#allKeywords").empty();
	    $("#excludeKeyWord").empty();
	    $("#orgNameAdvanced").empty();
	}
</script>   



<script type="text/javascript">
 function preparedClientDetails(data){
	 
	 var json = JSON.parse(data);
    	var result = "";
    	/* if(json.length != null && json.length != ''){ */
    	result = result +'<thead>';
    	result = result +'<tr>';
    	result = result +'	<th>Client Name</th>';
    	result = result +'	<th>Website</th>';
    	result = result +'	<th>Location</th>';
    	result = result +'	<th>Tier</th>';
    	result = result +'	<th>Created By</th>';
    	result = result +'<th>No.of contacts</th>';
    	/* result = result +'<th>Action</th>'; */
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
    		
    			result = result +'<td>';
    		    '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
    			result = result +'<div class="proj_show" onclick="clientSideBar(\''+report.vendorlocationId+'\',\''+report.id+'\',\''+orgName+'\');">';
				result = result +'<span class="tbldata-dotdot-vsm" title="'+report.orgName+'">'+report.orgName+'</span><i class="fa fa-binoculars"></i>';
    			
    			result = result +'</div>';
    			'</sec:authorize>';
    			
    			'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
    			result = result +'<div class="proj_show" ';
				result = result +'<span class="tbldata-dotdot-vsm" title="'+report.orgName+'">'+report.orgName+'</span>';
    			result = result +'</div>';
    			'</sec:authorize>';
    			
    				
    			result = result +'</td>';
				
    	
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
    		result = result +'<td><div><span class="tblminwidth-125" title='+report.vendorCreateduserName+'>'+report.vendorCreateduserName+'</span></div></td>';
    		result = result +'<td><div>'+report.noOfContatcs+'</div></td>';
    		result = result +'</tr>';
    		
		}
    	
    	result = result +'</tbody>';
	    $("#clientList").empty();  
	    $("#clientList").append(result)
	    $("#emptyClientList").val(json.length);
	    pagination();
	    
 }
</script>

<script>
function getClientDetails(){
	closePop();
	var values = [];
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
    });
	
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getClientDetails?${_csrf.parameterName}=${_csrf.token}&values="+values, 	   
   		    success: function(data) {
   		    	preparedClientDetails(data); 
   		    	/* preparedClientListFromElk(data); */
   		    }
	 }); 
	
	
}	
</script>

<script>
function getClientDetailsELK(){
	closePop();
	var values = [];
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
    });
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var responderType = "Client";
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorFilterListsElK?${_csrf.parameterName}=${_csrf.token}&responderType="+responderType+"&values="+values, 	   
   		    success: function(data) {
   		    	preparedClientListFromElk(data);
   		    }
	 }); 
	
	
}	
</script> 

<script>
	function msgFadein() {
		$("#selector1").fadeIn(200);
        $('#selector1').delay(2000).fadeOut('slow');
	}
</script> 

<script>
function pagination(){  
    $('#clientList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
	function clientSideBar(clientLocationId,orgRelationId,orgName) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		
		//for client details tab
		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
		result = result + '<li class="current"><a href="#tab-1" onClick="clientDetails('+clientLocationId+','+orgRelationId+');">Details</a></li>';
		'</sec:authorize>';
		
		//for client contatcs tab
		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT_CONTACT\')">';
		result = result + '<li><a href="#tab-2" onclick="clientContacts('+orgRelationId+','+clientLocationId+');">Contacts</a></li>';
		'</sec:authorize>';
		
		//for client documents tab
		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT_DOCUMENT\')">';
		result = result+'  <li><a href="#tab-3" onclick="clientDocuments('+orgRelationId+','+clientLocationId+');">Documents</a></li>';		
		'</sec:authorize>';	
		
		
		
		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_SUBMISSION_TEMPLATE\')">';
		result = result + '<li><a href="#tab-4" onclick="clientSubmissionTemplate('+clientLocationId+');">Submission Templates</a></li>';	
		'</sec:authorize>';
		
		//for client requirement tab
		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_REQUIREMENT\")">';
		result = result + '<li><a href="#tab-5" onclick="clientRequirementList('+orgRelationId+');">Jobs</a></li>';	
		'</sec:authorize>';
		
		result = result + '<li><a href="#tab-6" onclick="getClientInvoiceList('+orgRelationId+','+clientLocationId+');">Billing</a></li>';
		
		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CONTRACT\')">';
		result = result + '<li><a href="#tab-7" onclick="clientContracts('+orgRelationId+');">Contracts</a></li>';	
		'</sec:authorize>';
	
		// client notes
		
		result = result + '<li><a href="#tab-8" onclick="clientNotesList('+orgRelationId+','+clientLocationId+');">Notes</a></li>';	
	
		
		result = result + '<div class="clearfix"></div>'
		result = result + '</ul>';
		result = result + '<div class="project_tabs">';

		//for client details tab
		result = result + '<div id="tab-1" class="project_tabs_inn">';
		result = result + '</div>';
		
		//for client contatcs tab
        result = result + '<div id="tab-2" class="project_tabs_inn">';
		result = result + '</div>';
        
		//for client documents tab
        result = result + '<div id="tab-3" class="project_tabs_inn">';
		result = result + '</div>';
		
    	//for client submission template tab
	    result = result + '<div id="tab-4" class="project_tabs_inn">';
		result = result + '</div>';
		
		//for client requirement list
		   result = result + '<div id="tab-5" class="project_tabs_inn">';
			result = result + '</div>';

		//for billing tab
		 result = result + '<div id="tab-6" class="project_tabs_inn">';
		 result = result + '</div>';
		 
		 //for client contracts
		result = result + '<div id="tab-7" class="project_tabs_inn">';
		result = result + '</div>';
		
		//for notes tab
		 result = result + '<div id="tab-8" class="project_tabs_inn">';
		 result = result + '</div>';

		result = result + '</div>';
		result = result + '</div>';
		$("#clientSideDiv").empty();
		$("#clientSideDiv").append(result);
		
		/* $("#clientNameDiv").empty();
		$("#clientNameDiv").append(orgName); */
		
		 $(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');
		 
 
		clientDetails(clientLocationId,orgRelationId);
	}
</script>

<script>
function getClientNeworgname(clientNewOrgname){
	$("#clientNameDiv").empty();
	$("#clientNameDiv").append(clientNewOrgname);
}
</script>

<script>
function preparedClientContacts(data){
			var json = JSON.parse(data);
		   	var result = "";
		   	/* if(json.length != null && json.length != ''){ */
		   	result = result +'<thead>';
		   	result = result +'<tr>';
		   	result = result +'  <th>Client</th>';
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
		   		
		   		result = result +'<td><div><span title='+report.orgName+'>'+report.orgName+'</span></div></td>';
		   		
		   		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT_CONTACT\')">';
		   			result = result +'<td><div class="proj_show"onclick="clientSpocSideBar(\''+report.id+'\',\''+report.ownerUserId+'\',\''+report.spocUserId+'\',\''+report.spocName+'\',\''+report.spocLocationId+'\',\''+report.orgRelationId+'\',\''+report.userId+'\','+report.tempOwnerId+');"><span title='+report.spocName+'>'+report.spocName+'</span><i class="fa fa-binoculars"></i></div></td>';
		   		'</sec:authorize>';
		   		
		   		'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT_CONTACT\')">';
		   			result = result +'<td><div class="proj_show"><span title='+report.spocName+'>'+report.spocName+'</span></i></div></td>';
		   		'</sec:authorize>';
		   		
		   		result = result +'<td><div><span title='+report.spocEmail+'>'+report.spocEmail+'</span></div></td>';
		   		if(report.spocMobilePhone != null && report.spocMobilePhone  != '' && report.spocMobilePhone  != 'undefined'){
		   			result = result +'<td><div><span>'+report.spocMobilePhone+'</span></div></td>';
		   		}else{
		   			result = result +'<td><div>--</div></td>';
		   		}
		   		result = result +'<td><div><span title='+report.ownerName+'>'+report.ownerName+'</span></div></td>';
		   		
		   		result = result + ' <td><div>';
		   		if(report.invitationStatus == 'Rejected'){
        			result = result + '<span>'+report.spocStatus+'</span>';
	    		}else{
	    			result = result + ' <select id="spocState'+report.id+'" name="spocStatus" onChange="updateSpocStatus(\''+report.id+'\');">';
					result = result + ' <option '+(report.spocStatus == "Active" ? "selected" : '') +'>Active</option>';
					result = result + ' <option '+(report.spocStatus == "In-Active" ? "selected" : '') +'>In-Active</option>';
					result = result + ' </select>';
					
					result = result + '<span id="spocSpanState'+report.id+'"> </span>';
	    		}
				result = result + ' </div></td>';
				
				result = result + ' <td><div id="invitationStatus'+report.id+'">';
		   		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
		     		  result = result +' <i class="fa fa-check" alt="Accept" onclick="acceptInvitation('+report.id+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejectInvitation('+report.id+');"></i>';
		     		}
		   		
		       		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		                result = result +' ---';
		          	}
		     		
		     		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		     			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendClientSpocInvitation('+report.spocUserId+','+report.id+','+report.orgRelationId+');">Send</a></b>';
		     		}
		     		
		     		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		     			result = result +' ---';
		     		}
		     		
		     		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){ 
		     			result = result +' <b class="proj_process">Received</b>';
		     		}
		     		
		     		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
		     			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendClientSpocInvitation('+report.spocUserId+','+report.id+','+report.orgRelationId+');">Resend</a></b></div></td>';
		     		}
		     		
		     		if(report.invitationStatus == 'Accepted'){ 
		     			result = result +' <b class="proj_active">'+report.invitationStatus+'</b>';
		     		}
		   		
		   		   if(report.invitationStatus == 'Rejected'){
		   		      result = result +' <b class="proj_closed">'+report.invitationStatus+'</b>';
		   		   }
		   		result = result + ' </div></td>';
		   		
		   		'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CLIENT_CONTACT\')">';
		   		result = result + '<td><div><i class="glyphicon glyphicon-trash" onClick="terminateSpoc1('+report.id+'\,\''+report.orgRelations.id+'\')"; title="Remove client contact"></i></div></td>';
		   		'</sec:authorize>';
		   		
		   		'<sec:authorize access="!hasAnyRole(\'ROLE_DELETE_CLIENT_CONTACT\')">';
		   		result = result + '<td><div>--</div></td>';
		   		'</sec:authorize>';
		   		
		   		result = result +'</tr>';
				}
		   	result = result +'</tbody>';
			    $("#vendorContactList").empty();  
			    $("#vendorContactList").append(result)
			    $("#clientContactLength").val(json.length);
			    pagination1(); 
   
   	
}
</script>


<!-- contact list preparation from ELK result -->
<script>
function preparedClientContactListFromElk(data){
			var json = JSON.parse(data);
		   	var result = "";
		   	result = result +'<thead>';
		   	result = result +'<tr>';
		   	result = result +'  <th>Client</th>';
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
		   				result = result +'<td><div class="proj_show"onclick="clientSpocSideBar(\''+report.id+'\',\''+report.ownerUserId+'\',\''+report.spocUserId+'\',\''+report.spocName+'\',\''+report.spocLocationId+'\',\''+report.orgRelationId+'\',\''+report.userId+'\','+report.tempOwnerId+');"><span title="'+report.spocName+'">'+report.spocName+'</span><i class="fa fa-binoculars"></i></div></td>';
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
		   
		   		// from ELK ownerNames
		   		if(null != report.ownerNames  &&  '' != report.ownerNames  && 'undefined' != report.ownerNames){
					 result = result +'<td><div><span title="'+report.ownerNames+'">'+report.ownerNames+'</span></div></td>';
				}
		  		//from db ownerName
		   		else if(null != report.ownerName && '' != report.ownerName && 'undefined' != report.ownerName){
		   			result = result +'<td><div><span title="'+report.ownerName+'">'+report.ownerName+'</span></div></td>';
				}
		   		
		   		result = result + ' <td><div>';
		   		if(report.invitationStatus == 'Rejected'){
        			result = result + '<span>'+report.spocStatus+'</span>';
	    		}else{
	    			result = result + ' <select id="spocState'+report.id+'" name="spocStatus" onChange="updateSpocStatus(\''+report.id+'\');">';
	    			result = result + ' <option '+(report.spocStatus == "Active" ? "selected" : '') +'>Active</option>';
					result = result + ' <option '+(report.spocStatus == "In-Active" ? "selected" : '') +'>In-Active</option>';
					result = result + ' </select>';
					
					result = result + '<span id="spocSpanState'+report.id+'"> </span>';
	    		}
				result = result + ' </div></td>';
				
				result = result + ' <td><div id="invitationStatus'+report.id+'">';
		   		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
		   		  result = result +' <i class="fa fa-check" alt="Accept" onclick="acceptInvitation('+report.id+');"></i>|<i class="fa fa-times" alt="Rejected" onclick="rejectInvitation('+report.id+');"></i>';
		   		}
		   		
		   		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		            result = result +' ---';
		      	}
		   		
		   		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		   			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendClientSpocInvitation('+report.spocUserId+','+report.id+','+report.orgRelationId+');">Send</a></b>';
		   		}
		   		
		   		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'New'){
		   			result = result +' ---';
		   		}
		   		
		   		if(report.spocUserId != "${SourceLeadWeb_userId}" && report.ownerUserId != "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){ 
		   			result = result +' <b class="proj_process">Received</b>';
		   		}
		   		
		   		if(report.ownerUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
		   			result = result +' <b class="proj_process"><a href="javascript:void(0)" onClick="resendClientSpocInvitation('+report.spocUserId+','+report.id+','+report.orgRelationId+');">Resend</a></b>';
		   		}
		   		
		   		if(report.invitationStatus == 'Accepted'){ 
		   			result = result +' <b class="proj_active">'+report.invitationStatus+'</b>';
		   		}
		 		
		 		if(report.invitationStatus == 'Rejected'){
		 			result = result +' <b class="proj_closed">'+report.invitationStatus+'</b>';
		 		}
		 		result = result + ' </div></td>';
		   		'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_VENDOR_CONTACT\')">';
		   	    result = result + '<td><div><i class="glyphicon glyphicon-trash" onClick="terminateSpoc1('+report.id+'\,\''+report.orgRelationId+'\')"; title="Remove client contact"></i></div></td>';
		   	 '</sec:authorize>';
		   	 
		   	'<sec:authorize access="!hasAnyRole(\'ROLE_DELETE_VENDOR_CONTACT\')">';
			    result = result + '<td><div>---</div></td>';
			 '</sec:authorize>';
		   		
		   		
		   		result = result +'</tr>';
				}
		   	
		   	result = result +'</tbody>';
		    $("#vendorContactList").empty();  
		    $("#vendorContactList").append(result)
		    $("#clientContactLength").val(json.length);
		    pagination1(); 
			    
   }
</script> 


<!-- Client list preparation from ELK result -->
<script>
function preparedClientListFromElk(data){
			var json = JSON.parse(data);
		   	var result = "";
		   	
		   	result = result +'<thead>';
		   	result = result +'<tr>';
		   	result = result +' <th>Client Name</th>';
		   	result = result +' <th>Website</th>';
		   	result = result +'<th>Location</th>';
		   	result = result +'<th>Tier</th>';
		   	result = result +'<th>Created By</th>';
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
				'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
		   			result = result +'<td>';
		   			result = result +'<div class="proj_show" onclick="clientSideBar(\''+report.responderLocationId+'\',\''+report.orgRelationId+'\',\''+orgName+'\');"><span class="tbldata-dotdot-vsm" title="'+report.orgName+'">'+report.orgName+'</span><i class="fa fa-binoculars"></i>';
		   			result = result +'</div>';
		   			result = result +'</td>';
		   		'</sec:authorize>';
		   		
				'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
					result = result +'<td>';
					result = result +'<div class="proj_show" ><span class="tbldata-dotdot-vsm" title="${report.orgName}">'+report.orgName+'</span>';
					result = result +'</div>';
					result = result +'</td>';
				'</sec:authorize>';
		   		if( report.website != null && report.website != '' && report.website != 'undefined'){
		   			result = result +'<td>';
		   			result = result +'<div>';
		   			result = result +'<span class="tbldata-dotdot-sm" title='+report.website+'>'+report.website+'</span>';
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
				
		   	result = result +' <td><div><span class="tbldata-dotdot-vsm" title="'+report.initiatorLevel+'">'+report.initiatorLevel+'</span>';
		   	
		   	if(null != report.createdBy && report.createdBy  != '' && report.createdBy  != 'undefined'){
				result = result +' <td><div>'+report.createdBy+'</div></td>';
		   	}else{
		   		result = result +'<td><div>---</div></td>';
		   	}

		   	result = result +'<td><div >'+report.noContacts+'</div></td>';

		   	
		   		result = result +'</tr>';
				}
		   	
		   	result = result +'</tbody>';
		    $("#clientList").empty();  
		    $("#clientList").append(result)
		    $("#emptyClientList").val(json.length);
		    pagination();
}
</script>



<script>
function getVendorContactLists(){
	closePop();
	var clientIds = [];
	$('input:checkbox[name=clientNameById]:checked').each(function() 
    {
		clientIds.push($(this).val());
    });
	var status = [];
	$('input:checkbox[name=clientStatus]:checked').each(function() 
    {
		status.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=clientInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	
	
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getClientContactLists?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clientIds="+clientIds+"&invitationStatus="+invitationStatus, 	   
   		    success: function(data) {
   		    	preparedClientContacts(data);
   		    }
	 });
	
	
}	
</script> 


<script>
function getVendorContactListsELK(){
	closePop();
	var clientIds = [];
	$('input:checkbox[name=clientNameById]:checked').each(function() 
    {
		clientIds.push($(this).val());
    });
	var status = [];
	$('input:checkbox[name=clientStatus]:checked').each(function() 
    {
		status.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=clientInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	
	
	var sourceleadUrl ='${sourceleadUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var responderType = "Client";
	
	  $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getVendorContactListsELK?${_csrf.parameterName}=${_csrf.token}&responderType="+responderType+"&status="+status+"&clientIds="+clientIds+"&invitationStatus="+invitationStatus, 	   
   		    success: function(data) {
   		    	preparedClientContactListFromElk(data);
   		    }
	 });
	
	
}	
</script> 

<!-- This script is for resending the invitation mail -->
<script type="text/javascript"><%-- <jsp:include page="submissionTemplate.jsp" />	 --%>
function resendClientSpocInvitation(spocUserId,orgSpocId,orgRelationId){
	var sourceleadUrl ='${sourceleadUrl}';
	
	 var rowHtmlContent =  $("#row"+orgSpocId).html();
	 $("#row"+orgSpocId).empty();
	 $("#row"+orgSpocId).html("<td colspan='8'  id='loading"+orgSpocId+"'></td>");
	if(spocUserId != null && spocUserId != ""){
		$("#loading"+orgSpocId).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		$.ajax({
			    	type: "POST",
			        url: sourceleadUrl+"/resendSpocInvitationEmail?${_csrf.parameterName}=${_csrf.token}&spocUserId="+spocUserId+"&orgSpocId="+orgSpocId+"&responderType=Client&orgRelationID="+orgRelationId, 
			        success: function(result) {
			        	 $("#row"+orgSpocId).empty();
			    	     $("#row"+orgSpocId).html(rowHtmlContent);
			    	     $("#invitationStatus"+orgSpocId).html("<b class='proj_process'><a href='javascript:void(0)' onClick='resendClientSpocInvitation("+spocUserId+','+orgSpocId+','+orgRelationId+");'>Resend</a></b>");
			        },
			    });
		 }
	}
  
</script>

<script>
function pagination1(){ 
    $('#vendorContactList').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    })
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
	    url: sourceleadUrl+"/changeSpocStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&spocStatus="+spocStatus,	    	   
	    success: function(data) {
	    	$("#row"+spocId).empty();
	    	$("#row"+spocId).html(rowHtmlContent);
	        $("#spocState"+spocId).val(spocStatus);
	    }
   });
}
</script>



<script>
	function clientSpocSideBar(spocId,ownerId,spocUserId,spocName,spocLocationId,orgRelationId,userId,tempOwnerId) {
		var result = "";
		result = result + '<div class="project_side_wrap_inn_tabs">';
		result = result + '<ul class="project-tabs-menu">';
		
		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT_CONTACT\')">';
		result = result
				+ '<li class="current"><a href="#tab-1"  id="spocDetailsId" onClick="clientDetailsbySpocId('+spocId+','+ownerId+','+spocUserId+','+tempOwnerId+');">Details</a></li>';
		'</sec:authorize>';
		
		'<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT_DOCUMENT\')">';
		result = result
				+ '<li><a href="#tab-2" onClick="clientSpocDocuments('+orgRelationId+','+spocLocationId+',\''+spocName+'\','+userId+');">Documents</a></li>';
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
		
		$("#clientSideDiv").empty();
		$("#clientSideDiv").append(result);
		
		
		$(".clients_side_wrap").addClass("clients_side_wrap_active");
		$(".side_mask").fadeIn(200);
		$("body").css('overflow-y', 'hidden');

		clientDetailsbySpocId(spocId,ownerId,spocUserId,tempOwnerId);
	}
</script>

<script>

function getClientSpocname(clientNewSpocname){
	$("#clientNameDiv").empty();
	$("#clientNameDiv").append(clientNewSpocname);
}
</script>

<script>
function listChange(sel){
	 if(sel.value=="listbyVendor"){
		 document.getElementById("clientList").style.display="block";
		 document.getElementById("clientList").style.display="table";
		 document.getElementById("vendorContactList").style.display="none";
		 document.getElementById("vendorContactList_wrapper").style.display="none";
		 document.getElementById("contactLevel").style.display="block";
// 		 document.getElementById("clientsList").style.display="none";
		 document.getElementById("clientSatatus").style.display="none";
		 document.getElementById("clientInvitation").style.display="none";
		 '<sec:authorize access="hasRole(\'ROLE_EXPORT_CLIENTS\')">'; 
		 document.getElementById("clientImports").style.display="block";
		 '</sec:authorize> ';
		'<sec:authorize access="hasRole(\'ROLE_EXPORT_CLIENT_CONTACTS\')">'; 
		 document.getElementById("ContactImports").style.display="none";
		'</sec:authorize> ';	 
		 document.getElementById("vendorContactList_paginate").style.display="none";
		 document.getElementById("vendorContactList_info").style.display="none";
		 document.getElementById("vendorContactList_filter").style.display="none";
// 		 document.getElementById("filter1ApprovePop").style.display="none";
		 document.getElementById("filter3ApprovePop").style.display="none";
		 document.getElementById("filter4ApprovePop").style.display="none";
		 
		/*  getClientDetails(); */
		 /* getClientDetailsELK(); */
		 getClientList();
	 }
	 if(sel.value=="listbyContact"){
		 document.getElementById("vendorContactList").style.display="block";
		 document.getElementById("vendorContactList").style.display="table";
		 document.getElementById("clientList").style.display="none";
		/*  document.getElementById("clientList_wrapper").style.display="none"; */
// 		 document.getElementById("vendorContactList_wrapper").style.display="block";
		 document.getElementById("contactLevel").style.display="none";
// 		 document.getElementById("clientsList").style.display="block";
		 document.getElementById("clientSatatus").style.display="block";
		 document.getElementById("clientInvitation").style.display="block";
		 '<sec:authorize access="hasRole(\'ROLE_EXPORT_CLIENTS\')">'; 
		 document.getElementById("clientImports").style.display="none";
		 '</sec:authorize> ';
		'<sec:authorize access="hasRole(\'ROLE_EXPORT_CLIENT_CONTACTS\')">'; 
		 document.getElementById("ContactImports").style.display="block";
		'</sec:authorize> ';	 
// 		document.getElementById("filter1ApprovePop").style.display="block";
		 document.getElementById("filter3ApprovePop").style.display="block";
		 document.getElementById("filter4ApprovePop").style.display="block";
		 
		/*  getVendorContactLists(); */
		 getVendorContactListsELK();
		loadVendorDataTable();
	 }
	 
}
</script>

<script>
	function clientProjectDetails(clientId) {
		var sourceleadUrl = '${sourceleadUrl}';
		$
				.ajax({
					type : "POST",
					url : sourceleadUrl
							+ "/getAllClientProjectsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="
							+ clientId,
					success : function(data) {

						var json = JSON.parse(data);
						var result = "";
						result = result+ '<div class="project_tabs_inn_head">';
						/* result = result + '					<a href="create-project.html"><i class="glyphicon glyphicon-plus"></i>Add project</a>'; */
						result = result + '</div>';
						result = result+ '<div class="project_tabs_inn_table">';
						if(json.length!=null && json.length!=''){
						result = result + '<table>';
						result = result + '<tbody>';
						result = result + '<tr>';
						result = result + '<th>Project Id</th>';
						result = result + '<th>Projects name</th>';
						result = result + '<th>Start Date</th>';
						result = result + '<th>End Date</th>';
						result = result + '<th>Manager</th>';
						result = result + '<th>Team</th>';
						result = result + '<th>Status</th>';
						result = result + '</tr>';
						result = result + '<tr>';
						for (var i = 0; i < json.length; i++) {
							var opt = json[i];
							var endDateStr ="";
							if(opt.endDateStr != null && opt.endDateStr != '' & opt.endDateStr != 'undefined'){
								endDateStr = opt.endDateStr;
							}else{
								endDateStr = "NO DATA";
							}
							result = result + '<td><div class="tbldata-dotdot-sm" title="'+opt.id+'"><input type="text"  onBlur="inlineEditClientProjects('+opt.id+',\'id\');" id="id'+opt.id+'"  value="'+opt.id+'" class="project_inline_field"></div></td>';
							 result = result + '<td><div class="tbldata-dotdot-sm" title="'+opt.projectName+'"><input type="text"  onBlur="inlineEditClientProjects('+opt.id+',\'projectName\');" id="projectName'+opt.id+'"  value="'+opt.projectName+'" class="project_inline_field"></div></td>'; 
							/* result = result + '<td><div><a href="<c:url value="/projectDashboard/'+opt.id+'#tab-3"/>""> '+opt.projectName+' </a></div></td>'; */
							result = result + '<td>'+opt.startDateStr+'</td>';
							
							result = result+ '<td><div><input type="text" value="'+endDateStr+'"  id="endDate'+opt.id+'" class="project_inline_field" readonly="readonly"></div></td>';
							result = result + ' <td><div><select id="clientId'+opt.id+'" onBlur="inlineEditClientProjects('+opt.id+',\'clientId\');">';
									
							for (var j = 0; j < opt.projectManagerName.length; j++) {
										
										var managers = opt.projectManagerName[j];
										var projrctmanager = managers.split('&');
								    	var name = projrctmanager[0];
								    	var id = projrctmanager[1];
								    	var selected = '';
								    	
					   		 	    	 if(opt.relationId==id){
					   		 	    		 selected = 'selected';
					   		 	    		
					   		 	    		result = result+"<option value="+id+" "+selected+" >"+name+"</option>";
					   		 	    	    
					   		 	    	 }else{
					   		 	    		result = result+"<option value="+id+" >"+name+"</option>";
					   		 	    	 }
					   		 	    	 
									} 
							
							result = result + '	</select></div></td>';
							result = result+ '<td><div>'+opt.assignedEmpCount+'</div></td>';
							result = result + '<td><div><select id="projectStatusId'+opt.id+'" onBlur="inlineEditClientProjects('+opt.id+',\'projectStatusId\');">';
							if(opt.projectStatus == 'STARTED'){
								result = result+'<option selected>Started</option>';
							}else{
								result = result+'<option>Started</option>';
							}
							
							if(opt.projectStatus == 'INPROGRESS'){
								result = result+'<option selected>In-Progress</option>';
							}else{
								result = result+'<option>In-Progress</option>';
							}
							
							if(opt.projectStatus == 'COMPLETED'){
								result = result+'<option selected>Completed</option>';
							}else{
								result = result+'<option>Completed</option>';
							}
						
							if(opt.projectStatus == 'ONHOLD'){
								result = result+'<option selected>On-Hold</option>';
							}else{
								result = result+'<option>On-Hold</option>';
							}
							result = result + '</select></div></td>';
							result = result + '</tr>';
						}
						rresult = result + '	     </tbody>';
						result = result + '	 </table>   ';
						}else{
				    		
			    			 result = result + '<div class="table_nodata">';
			    			 result = result + '<div class="table_nodata_inn">';
			    			 result = result + '<img src="resources/images/no-data.png" />';
			    			 result = result + '<span>Nothing is here to show ';
			    			 result = result + '</span>';
			    			 result = result + '<div class="clearfix"></div>';
			    			 result = result + '</div>';
			    			 result = result + '</div>';
			    		 } 
						result = result + '	     </div>';
						$("#tab-2").empty();
						$("#tab-2").append(result);

						
						if(json.length > 0)
				   		{
			    			 for(var i=0; i<json.length; i++){
								 var jsonData = json[i];
								 
								 //Assign date of project member
								 var selected = jsonData.startDateStr;
								 var dt = new Date(selected);
			 		             dt.setDate(dt.getDate());
			 		             
								 $('#endDate'+jsonData.id).datepicker({
						    		      dateFormat: 'yy-mm-dd',
						    		      minDate: dt,
						    		      onClose: function() {
						    		        	  var id = $(this).attr('id');
												 var projectMemberId = $(this).attr('id').split('endDate')[1];
												 inlineEditClientProjects(jsonData.id,'endDate'); 
						    		      }
						    		  }); 
			    			 }
				   		}
					},
				});
	}
</script>





<script> 
function  inlineEditClientProjects(projectId,localFieldName){
	var sourceleadUrl ='${sourceleadUrl}';
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName+""+projectId).val();
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/inlineEditClientProjects?${_csrf.parameterName}=${_csrf.token}&projectId="+projectId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   		    }
	 }); 
}
</script> 
<div id="popUp"></div>
<script> 
function  inlineEditForProjects(orgRelationId,localFieldName){
	var sourceleadUrl ='${sourceleadUrl}';
	var fieldName = localFieldName;
	var fieldValue = $("#"+localFieldName).val();
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/updateClientProjectById?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId+"&fieldName="+fieldName+"&fieldValue="+fieldValue, 	   
   		    success: function(data) {
   		    }
	 }); 
}
</script> 

<script>
function getEndDatePickerById(projectId,localFieldName){
	$('#endDate'+projectId).datepicker({
	      dateFormat: 'yy-mm-dd',
	      onSelect: function (selected) {
	            var dt = new Date(selected);
	            dt.setDate(dt.getDate() + 1);
	        },
	        onClose: function() {
	        	inlineEditClientProjects(projectId,localFieldName);
	     }
	  });
}
</script>

<script>
	// Approved Popup
	$(document).ready(function() {
		//$("#listOfVendors").show('#contactLevel');
		$("#appro-pop").click(function() {
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
			$(".export_pop, #filter3ApprovedPop, #filter4ApprovedPop").slideUp(200);
		});
	    $("html").click(function() {
	        $("#filter1ApprovedPop").slideUp(200);
	    });
	    $("#filter1ApprovedPop, #filter1ApprovePop").click(function(e) {  
	        e.stopPropagation();
	    });	
		
		$("#filter2ApprovePop").click(function(){
			$("#filter2ApprovedPop").slideToggle(200);
			$(" .export_pop").slideUp(200);
		});
	    $("html").click(function() {
	        $("#filter2ApprovedPop").slideUp(200);
	    });
	    $("#filter2ApprovedPop, #filter2ApprovePop").click(function(e) {  
	        e.stopPropagation();
	    });	
	    
	    $("#filter3ApprovePop").click(function(){
			$("#filter3ApprovedPop").slideToggle(200);
			$("#filter1ApprovedPop, .export_pop, #filter4ApprovedPop").slideUp(200);
		});
	    $("html").click(function() {
	        $("#filter3ApprovedPop").slideUp(200);
	    });
	    $("#filter3ApprovedPop, #filter3ApprovePop").click(function(e) {  
	        e.stopPropagation();
	    });	
	    
	    $("#filter4ApprovePop").click(function(){
			$("#filter4ApprovedPop").slideToggle(200);
			$("#filter1ApprovedPop, .export_pop, #filter3ApprovedPop").slideUp(200);
		});
	    $("html").click(function() {
	        $("#filter4ApprovedPop").slideUp(200);
	    });
	    $("#filter4ApprovedPop, #filter4ApprovePop").click(function(e) {  
	        e.stopPropagation();
	    });	
	    
	});
	// project side bar
	$(document).ready(function() {
		$(".proj_show").click(function() {
			$(".clients_side_wrap").addClass("clients_side_wrap_active");
			$(".side_mask").fadeIn(200);
			$("body").css('overflow-y', 'hidden');
		});
		$(".proj_close, .side_mask").click(function() {
			$(".clients_side_wrap").removeClass("clients_side_wrap_active");
			$(".side_mask").fadeOut(200);
			$("body").css('overflow-y', 'auto');
			//update the details in list.
			var vendorCont = document.getElementById('listOfVendors').value;
			if(vendorCont == "listbyContact"){
				getVendorContactListsELK();
			}if(vendorCont == "listbyVendor" ){
				/* getClientDetailsELK(); */
				getClientList();
	   		}
		});
	});

	//project side bar tabs
	$(document).ready(function() {
		$("#tab-1").show();
		$(".project-tabs-menu a").click(function(event) {
			event.preventDefault();
			$(this).parent().addClass("current");
			$(this).parent().siblings().removeClass("current");
			var tab = $(this).attr("href");
			$(".project_tabs_inn").not(tab).css("display", "none");
			$(tab).fadeIn();
		});
	});
</script>


<!-- Show success message after form submission script -->
<script>

$("#selector").fadeIn(200);
  $('#selector').delay(2000).fadeOut('slow');
</script> 
<!-- End -->


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
		    url: sourceleadUrl+"/saveInvitationStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&invitationStaus=Accepted",	    	   
		    success: function(data) {
		    	$("#selector1").removeAttr("style");
    			$("#selector1").css({ display: "block" });
    			$("#msgDiv").html("Accept Invitation Successfully");
    			
    			$("#row"+spocId).empty();
    	    	$("#row"+spocId).html(rowHtmlContent);
    	        $("#invitationStatus"+spocId).html("<b class='proj_active'>Accepted</b>");
		    	msgFadein();
		    }
	   });
}
</script>

<script>
function cancelRejectPopupClient(){
	$("#remove_user_pop, .project_inn_mask").fadeOut();
	$("#reasonForSpoc").val('');
	$("#reasonForSpocDiv").find("p.jquery_form_error_message").remove();
	$("#reasonForSpocDiv").empty(); 

}
</script>

<script>
function terminateSpoc1(spocId, orgRelationID){
	$(".project_inn_mask").remove();
	var result = "";
	result = result +"	<div class='project_inn_mask'></div>";
	result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
	//result = result +"		<textarea  id='reasonForSpoc' name='reasonForSpoc'  placeholder='Comments here'></textarea>";
	result = result +"		<textarea id='reasonForSpoc' class='form-control' field-name='Reject Reason'  data-validation='required validate_Space validate_Description validate_length length2-250' name='reasonForSpoc' value='' placeholder='Reason' rows='2' onkeypress='clientDashboardRemoveSpacesForRejectReason();'></textarea>";
	result = result + ' <div id="reasonForSpocDiv"/>';
	result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Delete' onclick='saveReason1("+spocId+","+orgRelationID+");'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelRejectPopupClient();'></b>";
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
	var reason1 = document.getElementById("reasonForSpoc").value;
	 reason = reason.replace(/\s/g, "")
	if(reason.match(/^[^\s-](.*)*$/) != null){
		$("#reasonForSpocDiv").empty(); 
	
		$.ajax({
		   	type: "POST",
		   url: sourceleadUrl+"/terminateSpoc?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&reason="+reason,	    	   
		    success: function(data) {
   			   
	   			 $("#selector1").removeAttr("style");
	  			 $("#selector1").css({ display: "block" });
	  			 $("#msgDiv").html("Client contact removed");
	  			 $("#popUp").empty();
	 			 $(".project_inn_mask").fadeOut(200);
	 			 
	 			 var table = $('#vendorContactList').DataTable();
	 		     table.row("#row"+spocId).remove().draw( false );
				 	
	             msgFadein();
		    }
	   });
	}else{
		if(reason.length == 0 && reason1){
			$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Must not Start With Space</font>") ;
		}else{
			$("#reasonForSpocDiv").empty().append("<font color='red' style='padding: 5px 10px; '>Reason is required</font>") ; 
		}
	}
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
		    url: sourceleadUrl+"/saveInvitationStatus?${_csrf.parameterName}=${_csrf.token}&spocId="+spocId+"&invitationStaus=Rejected",
		    success: function(data) {
		    	$("#selector1").removeAttr("style");
    			$("#selector1").css({ display: "block" });
    			$("#msgDiv").html("Reject Invitation Successfully");
    			
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
function exportClientToPdf(){
	var values = [];
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
    });
	var emptyClientList = $("#emptyClientList").val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(emptyClientList > 0){
		window.location.href = sourceleadUrl+"/exportClientDetails?${_csrf.parameterName}=${_csrf.token}&values="+values+"&exportType=pdf";	
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Clients List is Empty</p>";
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
function exportClientToExcel(){
	var values = [];
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
    });
	var emptyClientList = $("#emptyClientList").val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(emptyClientList > 0){
		window.location.href = sourceleadUrl+"/exportClientDetails?${_csrf.parameterName}=${_csrf.token}&values="+values+"&exportType=xls";
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Clients List is Empty</p>";
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
function exportContactToPdf(){
	var clientIds = [];
	$('input:checkbox[name=clientNameById]:checked').each(function() 
    {
		clientIds.push($(this).val());
    });
	var status = [];
	$('input:checkbox[name=clientStatus]:checked').each(function() 
    {
		status.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=clientInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	var vendorContactListEmpty = $("#clientContactLength").val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(vendorContactListEmpty > 0){
	window.location.href = sourceleadUrl+"/exportClientContactDetails?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clientIds="+clientIds+"&invitationStatus="+invitationStatus+"&exportType=pdf";
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Client Contacts List is Empty</p>";
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
function exportContactToExcel(){
	var clientIds = [];
	$('input:checkbox[name=clientNameById]:checked').each(function() 
    {
		clientIds.push($(this).val());
    });
	var status = [];
	$('input:checkbox[name=clientStatus]:checked').each(function() 
    {
		status.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=clientInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	var vendorContactListEmpty = $("#clientContactLength").val();
	var sourceleadUrl ='${sourceleadUrl}';
	if(vendorContactListEmpty > 0){
		window.location.href = sourceleadUrl+"/exportClientContactDetails?${_csrf.parameterName}=${_csrf.token}&status="+status+"&clientIds="+clientIds+"&invitationStatus="+invitationStatus+"&exportType=xls";	
	}else{
		var result = "";
		result = result +"	<div class='project_inn_mask'></div>";
		result = result +"	   <div id='client_user_pop' class='remove_user_pop remove_user_pop_a'>";
		result = result +"		<p>Client Contacts List is Empty</p>";
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
// Edit pop
$(document).ready(function() {	
	
    $("#edit-client, #btnClose").click(function() {
        $(".user_summary_edit").slideUp(100);
    });
		
});
</script> 


<script type="text/javascript">
function cancelTermination(){
	$("#popUp").empty();
	$(".project_inn_mask").fadeOut(200);
}
</script>

<script>
 $('#selector').delay(2000).fadeOut('slow');
	   $("#client-imports").click(function(){
	       $(".export_pop").toggle();
	       $("#filter2ApprovedPop").slideUp();
	       });
	   $("html").click(function() {
	       $(".export_pop").hide();
	   });
	   $(".export_pop, #client-imports").click(function(e) {
	       e.stopPropagation();
	   });        
</script> 

<script>
$('#selector').delay(2000).fadeOut('slow');
  $(document).ready(function() {
	   $("#export-popcon").click(function(){
		   $(".export_pop").toggle();
		   $("#filter1ApprovedPop, #filter4ApprovedPop, #filter3ApprovedPop").slideUp();
	       });
	   $("html").click(function() {
	       $(".export_pop").hide(200);
	   });
	   $(".export_pop, #export-popcon").click(function(e) {
	       e.stopPropagation();
	   });        
	});
</script> 

<script>
	// Close Advanced Search
		function closePop(){
			$("div.cand-advsrch-popup").hide();
		}
		function openPop(){
		  $("div.cand-advsrch-popup").toggle();
		  
		}
</script>
	
 
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
	    $('#createdByAdvanced').val('').trigger('chosen:updated');
 	    $('#stateSearch').val('').trigger('chosen:updated');
	    $('#citySearch').val('').trigger('chosen:updated');
	    $("#citySearch").empty();
	   
	}
</script>



<script type="text/javascript">
	function searchClient() {
		var validationSettings = {
				errorMessagePosition : 'element',
				borderColorOnError : '',
				scrollToTopOnError : true,
				dateFormat : 'yyyy/mm/dd'
			};
		$("#search_client").addClass('button').val('Search');
		$("#search_client").removeAttr("disabled");
		
		var responderType = "Client";
		
		 if ($('#clientlist_form').validate(false, validationSettings)){ 
			
			var sourceleadUrl = '${sourceleadUrl}';
			var frm = $('#clientlist_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#clientlist_form')[0];
      		var formData = new FormData(form); 
      		
      		
      		
			//It will escape all the special characters
			$("#search_client").attr("disabled", "disabled");
			$("#search_client").addClass('button').val('Processing..');
			
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
					url : sourceleadUrl+"/searchVendorThroughElasticSearch?${_csrf.parameterName}=${_csrf.token}&responderType="+responderType+"&json="+jsonData,
					success : function(data) {
						$("#search_client").addClass('button').val('Search');
						$("#search_client").removeAttr("disabled");
						document.getElementById("clientlist_form").reset();
						closePop();
						clearSearchKeys();
						preparedClientListFromElk(data);
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
					url : sourceleadUrl+"/searchVendorContactsThroughElasticSearch?${_csrf.parameterName}=${_csrf.token}&responderType="+responderType+"&json="+jsonData,
					success : function(data) {
						$("#search_client").addClass('button').val('Search');
						$("#search_client").removeAttr("disabled");
						/*  $('#clientlist_form').closest('form').find("input[type=text], textarea").val(""); */
						/* document.getElementById("search_client").reset(); */
						closePop(); 
						clearSearchKeys();
					
						preparedClientContactListFromElk(data);
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
</script>


<script>
function getStates(){
	var countryName = $("#countrySearch").val();
	if(countryName != null && countryName != "" && countryName !="undefined"){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getStatesByCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#stateSearch').empty();
	        	$('#stateSearch').append('<option value="">---select state---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#stateSearch').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#stateSearch').trigger("chosen:updated");
	            
	            $('#citySearch').empty();
	            $('#citySearch').append('<option value="">---select city---</option>');
	            $('#citySearch').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#stateSearch').empty();
    	$('#stateSearch').append('<option value="">---select state---</option>');
    	$('#stateSearch').trigger("chosen:updated");
    	$('#citySearch').empty();
    	$('#citySearch').append('<option value="">---select city---</option>');
        $('#citySearch').trigger("chosen:updated");
	}
}
</script> 


<script>
function getCities(){
	var countryName = $("#countrySearch").val();
	var stateName = $("#stateSearch").val();
	
	if(countryName != null && countryName != "" && countryName !="undefined" && stateName != null && stateName != "" && stateName !="undefined" ){
		$.ajax({
	    	type: "POST",
	    	dataType: "json",
	    	 url: '${sourceleadUrl}'+"/getCitiesByStateAndCountry?${_csrf.parameterName}=${_csrf.token}&countryName="+countryName+"&stateName="+stateName, 
	        success: function(data) {
	        	var json = JSON.parse(data);
	        	$('#citySearch').empty();
	        	$('#citySearch').append('<option value="">---select city---</option>');
	            for(var i=0;i<json.length;i++){
	            	$('#citySearch').append('<option value="'+json[i]+'">'+json[i]+'</option>');
	            }
	            $('#citySearch').trigger("chosen:updated");
	        },
	        error: function(e){
	        }
	    });
	}else{
		$('#citySearch').empty();
    	$('#citySearch').append('<option value="">---select city---</option>');
    	$('#citySearch').trigger("chosen:updated");
	}
}
</script>  

<script>
function flushCheckBoxVal(){
	$("input[type=checkbox]").each(function() { this.checked=false; });
	
}
 </script>
<jsp:include page="../masterFooter.jsp" />	