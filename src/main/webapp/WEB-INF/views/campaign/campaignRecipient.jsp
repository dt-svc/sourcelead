<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="/resources/js/campaignRecipient.js"></script>
<link rel="stylesheet" href="/resources/css/bootstrap-multiselect.css?staticTime=${staticTime}" type="text/css">
<link rel="stylesheet" href="/resources/css/bootstrap3-wysihtml5.min.css?staticTime=${staticTime}" type="text/css">

<script type="text/javascript">
    var staticTime='${staticTime}'
    
    	var tokenParamName = "${_csrf.parameterName}";
    var tokenValue = "${_csrf.token}";
 </script>

<script src="/resources/js/formvalidator.js?staticTime=${staticTime}"></script>

<!--   Popup Code  -->
	<div class="success_pop" id="selector1" style="display: none;">
 			<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
     </div>
     <div id="loadingRecipient"></div>
	<form:form id="capn_add_Recipient_List" name="capn_add_Recipient_List" method="post">
	
		<div class="popupHead">
		<c:if test="${empty campaignList.id}">
			<span>Add Recipient</span><a onclick="recipientClose();" href="javascript:void(0)" class="closePop pull-right popClose"></a>
		</c:if>
		
		<c:if test="${!empty campaignList.id}">
			<span>Edit Recipient</span><a onclick="recipientClose();" href="javascript:void(0)" class="closePop pull-right popClose"></a>
		</c:if>
		</div>
		
		<div class="popupContent">

          <div class="clearfix mb">
				<label class="noPad">Choose Campaign <b class="valStar">*</b></label>
				
              	<div class="clearfix">
              		
              		<sec:authorize access="hasRole('ROLE_HOTLIST_CAMPAIGN') and hasRole('ROLE_JOB_CAMPAIGN')">
              		<div class="col-md-6 padding_left">
              			<input class="pull-left" type="radio" id="hotlistRadio" value="hotlistCampaign" onclick="clearRecipantsList(this);"  name="campaignType"><label class="col-md-10 padRadio" for="hotlistRadio">Hot List Campaign</label>
              		</div>
              		<div class="col-md-6">
              			<input class="pull-left" type="radio" id="jobRadio" value="jobCampaign" onclick="clearRecipantsList(this);"  name="campaignType"><label class="col-md-10 padRadio" for="jobRadio">Job Campaign</label>
              		</div>   
              		</sec:authorize>  
              		
              		<sec:authorize access="!hasRole('ROLE_HOTLIST_CAMPAIGN') and hasRole('ROLE_JOB_CAMPAIGN')">
              		<div class="col-md-6 padding_left">
              			<input class="pull-left" type="radio" id="jobRadio" value="jobCampaign" checked="checked" onclick="clearRecipantsList(this);"  name="campaignType"><label class="col-md-10 padRadio" for="jobRadio">Job Campaign</label>
              		</div>
              		</sec:authorize>
              		
              		<sec:authorize access="hasRole('ROLE_HOTLIST_CAMPAIGN') and !hasRole('ROLE_JOB_CAMPAIGN')">
              		<div class="col-md-6 padding_left">
              			<input class="pull-left" type="radio" id="hotlistRadio" value="hotlistCampaign" checked="checked" onclick="clearRecipantsList(this);"  name="campaignType"><label class="col-md-10 padRadio" for="hotlistRadio">Hot List Campaign</label>
              		</div>
              		</sec:authorize>    
              		         		
				</div>
			</div>
			
          <div class="clearfix mb">
				<label class="noPad">List Name <b class="valStar">*</b></label>
              	<div class="clearfix" id="cpnListNameDiv">
                	<input type="text" class="form-control" id="cpnListName" name="cpnListName" value="${campaignList.listName}"  onblur="validateCampaignListName();" data-validation="required validate_length length1-100" field-name="List Name">
				    <input type="hidden" value="${campaignList.id}" name="contactListId" id="contactListId" >
				</div>
			</div>			
			
          <div class="clearfix mb">
              <label class="noPad">Recipient(Email) <b class="valStar">*</b></label>
              <div class="col-md-10 noPad" id="recipientEmailDiv">
                <select multiple class="chosen-select form-control" id="recipientEmail" name="recipientEmail" data-validation="required validate_length length1-250" field-name="Recipient Email">
               <c:if test="${not empty recipientList}"></c:if>
               <c:forEach items="${recipientList}" var="recipient">
   						<option value='${recipient.email}' selected>${recipient.email}</option>
				</c:forEach>
                </select>
                
              </div>
              	<div class="col-md-2">
					<a href="javascript:void(0)"  data-target="#multiplemail" onclick="selectRadio();" data-toggle="modal" class="btn btn-default"><i class="fa fa-list-alt"></i></a>
					<div role="dialog" id="multiplemail" class="modal fade">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title" id="receipantCategoryType"></h4>
                    <label class="paddingLeft paddingRight">
               	    <input  type="radio" id="vendorCheck" name="users" checked onclick="selectRadio()"><strong class="paddingLeft" id="categoryListType"></strong></label>
               	    <label class="paddingLeft">
               	    <input type="radio" id="contactsCheck" name="users" onclick="getContactsList();"><strong class="paddingLeft">My Contacts</strong></label>
                  </div>
                  
                   <div class="modal-body clearfix">
                   <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100 select-field paddingLeft mb10" id="vendorsListDiv" style="display: none">
                          <select class="form-control" id="vendorListType" onchange="vendorListChange();">
                           <option value="listbyVendor" selected>List By Vendor</option>
                           <option value="listbyContact">List By Contact</option>
                          </select>
                    </div>
                    <div class="clearfix"></div>
                    <div class="clearfix">
                     <div id="mailBoxVendorsList" class="datatable_filter"></div>
                    </div>
                    
                  </div>
                  
                  
                  <div class="modal-body clearfix">
                    <div class="col-xs-12 col-md-4 col-lg-4 selectpicker100 select-field paddingLeft mb10" id="clientsListDiv" style="display: none">
                          <select class="form-control" id="clientListType" onchange="clientListChange();">
                           <option value="listbyClient" selected>List By Client</option>
                           <option value="listbyClientContact">List By Contact</option>
                          </select>
                    </div>
                    <div class="clearfix"></div>
                    <div class="clearfix">
                     <div id="mailBoxClientsList" class="datatable_filter"></div>
                    </div>
                    
                  </div> 
                  
                </div>
              </div>
            </div>
				</div>              
			</div>	
		
		</div>
		<div class="popupFoot text-right">
		<p id="errorMessage" class="noPad noMar"></p>
            <input value="Save" class="slBtn md colorBtn" type="button" id="addList">
             <input value="Reset" class="slBtn md" type="button" id="resetForm">
            <input value="Cancel" class="slBtn md popClose" type="button" onclick="recipientClose();">
          </div>		
		</form:form>
		
	
	<script>
	function clearRecipantsList(id){
// 		checkRespValues(id);
		  $("#recipientEmail").empty();
		  $("#recipientEmail").trigger("chosen:updated");
		  $("#cpnListName").val('');
		  $("#contactListId").val('');
	}
	
	function checkRespValues(id){
		var contactListId = $("#contactListId").val();
		<c:forEach items="${recipientList}" var="recipient">
		</c:forEach>
		
	}
	</script>	
		
	<script>
  function selectRadio(){
	  $("#mailBoxVendorsList").empty();
	  $("#mailBoxClientsList").empty();
	
	  
	  var campaignType = $( 'input[name="campaignType"]:checked' ).val();
	  if(campaignType == 'hotlistCampaign'){
		  $('#clientsListDiv').find('p.jquery_form_error_message').remove();
	  		$("#clientbtnId").removeAttr("disabled");
		 $("#receipantCategoryType").html("Select Client");
		 document.getElementById("vendorCheck").checked = true;
		  document.getElementById("contactsCheck").checked = false;
		 $("#categoryListType").html("Client");
		 clientListChange();
	  }else{
		  $("#receipantCategoryType").html("Select Vendors");
		  $("#categoryListType").html("Vendor")
		  document.getElementById("vendorCheck").checked = true;
		  document.getElementById("contactsCheck").checked = false;
		  $("#vendorListType").val('listbyVendor');
		  vendorListChange();
	  }
	  
	 
  }
  </script>
  
  
  
  <script>
	  function vendorListChange(){
		  document.getElementById("vendorsListDiv").style.display="";
		  document.getElementById("clientsListDiv").style.display="none";
		  var listType = document.getElementById("vendorListType").value;
		  if(listType == 'listbyContact'){
			  vendorContactList();
		  }else{
			  vendorsList();
		  }
		  
	  }
  </script>
  
  
  <script>
	  function clientListChange(){
		  $('#clientsListDiv').find('p.jquery_form_error_message').remove();
		  document.getElementById("vendorsListDiv").style.display="none";
		  document.getElementById("clientsListDiv").style.display="";
		  var listType = document.getElementById("clientListType").value;
		  if(listType == 'listbyClientContact'){
			  clientContactList();
		  }else{
			  clientsList();
		  }
		  
	  }
  </script>
  
  
  <script>
function clientContactList(){
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='clientContactsDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
	 	
  		result = result +'<table id="clientconDataTable"   class="display table table-stripped table-bordered" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		 result = result +' <th><input type="checkbox" id="checkAllClientContacts" onclick="toggleClientContacts(this);"></th>'; 
		result = result +'  <th>Client Name</th>';
	   	result = result +'	<th>Contact Name</th>';
	   	result = result +'  <th>Email</th>';
	   	result = result +' <th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="clientContactbtnId" onclick="getClientContactEmails();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    $("#mailBoxClientsList").empty();
	    $("#mailBoxClientsList").append(result);
	    clientcontactsList = {}
	    clientContactDataTableList();
}
</script>

<script>
function clientContactDataTableList(){
	 var searchValue = $("#clientContactsDataTableSearch").val();
	 var responderType ="ClientHotListCampaign";
	 var recipientsEmails = '';
	 if($("#recipientEmail").val() != null){
		  recipientsEmails = $("#recipientEmail").val();
	 }
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/searchClientContactDetails?searchValue="+searchValue+"&responderType="+responderType+"&recipientsEmails="+recipientsEmails;
	 clientContactpagination(url);
}
</script>


<script>
	function clientContactpagination(url){
		var colIndex = 0;
		 $('#clientconDataTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'GET',
	            pages: 10
	        }),
	        "columns": [
	        	{ "data": "" },
	            { "data": "orgName" },
	            { "data": "spocName"},
	            { "data": "spocEmail" },
	            { "data": "spocStatus" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "ischeck",
		    	  	"render": function(data, type, report, meta) {
		    	  		 var result = "";
		    	  		 var flag = false;
		    	  		
		    	  	   for(var e in clientcontactsList) {
		    	  		   if(report.spocEmail+"" === clientcontactsList[e]) {
		    	  				 flag = true;
		    	  				 break;
		    	  			 }
		    	  		 }  
		    	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+report.id+'" data-id="'+report.id+'" value="'+report.spocEmail+'" onchange="checkClientsContacts(this);"  type="checkbox" name="contactEmails[]"  class="myClientContactsCheckbox" ></td>'; 
		    	  		
		    	  		  return result;
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "orgName",
	            "render": function ( data, type, report, meta ) {
	            	     var result = "";
		    	  		 result = result +'<input type="hidden" value="row'+report.id+'" id="row'+report.id+'">';
		    	  		result = result +'<td><div class="tbldata-dotdot-100" title="'+report.orgName+'">'+report.orgName+'</div></td>';
			             return  result;
	            }
	          },{
		            "targets": colIndex++,
		            "data": "spocName",
		            "render": function ( data, type, report, meta ) {
		            	     
		            	    var spocName ="";
		            	    if(report.spocName != null && report.spocName !='' && report.spocName !='undefined'){
		            	    	spocName = report.spocName;
		            	    }else{
		            	    	spocName = "----";
		            	    }
		            	     var result = "";
				   			 result = result +'<td><div class="proj_show" ><span title="'+spocName+'">'+spocName+'</span></div></td>';
			                 return result; 
		            }
		          }, {
	              "targets": colIndex++,
	              "data": "spocEmail",
	              "render": function ( data, type, report, meta ) {
	            	    var spocEmail="";
	            	     var result = "";
	            	     if(report.spocEmail != null && report.spocEmail !='' && report.spocEmail !='undefined'){
	            	    	 spocEmail=report.spocEmail;
	            	     }else{
	            	    	 spocEmail = "----";
	            	     }
	            	     result = result +'<td><div class="tbldata-dotdot-vsm" title='+spocEmail+'>'+spocEmail+'</div></td>'; 
	  	                 return result; 
	              }
	            },{
		    	  	"targets": colIndex++,
		    	  	"data": "spocStatus",
		    	  	"sortable": false,
		    	  	"render": function(data, type, report, meta) {
		    	  		 var result = "";
		    	  		result = result + ' <td><div class="tblminwidth-30 dropdown-select paddingNone">';
		    	  		result = result + '<span title='+report.spocStatus+'>'+report.spocStatus+'</span>';
	   	        		result = result + ' </div></td>'; 
		    	  		 return result; 
		    	  	}
		    	  }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[7, 15, 20], [7, 15, 20]],
		    	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": false,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
	    } );
		$("#clientconDataTable").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>");
	    $("input[type=search]").hide();
	    $("#clientContactsDataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   clientContactDataTableList();
	    	   }
	    	});
}
</script>
  
  
  
  
  <script>
function vendorContactList(){
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='vendorContactsDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
	 	
  		result = result +'<table id="vendorconDataTable"   class="display table table-stripped table-bordered" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		 result = result +' <th><input type="checkbox" id="checkAllVendorContacts" onclick="toggleVendorContacts(this);"></th>'; 
		result = result +'  <th>Vendor Name</th>';
	   	result = result +'	<th>Contact Name</th>';
	   	result = result +'  <th>Email</th>';
	   	result = result +' <th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="vendorContactbtnId" onclick="getVendorContactEmails();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    $("#mailBoxVendorsList").empty();
	    $("#mailBoxVendorsList").append(result);
	    vendorcontactsList = {}
	    vendorContactDataTableList();
	    
	   
}
</script>

<script>
function vendorContactDataTableList(){
	 var searchValue = $("#vendorContactsDataTableSearch").val();
	 var responderType ="JobCampaignVendor";
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/searchClientContactDetails?searchValue="+searchValue+"&responderType="+responderType;
	 vendorContactpagination(url);
}
</script>

<script>
	function vendorContactpagination(url){
		var colIndex = 0;
		 $('#vendorconDataTable').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'GET',
	            pages: 10
	        }),
	        "columns": [
	        	{ "data": "" },
	            { "data": "orgName" },
	            { "data": "spocName"},
	            { "data": "spocEmail" },
	            { "data": "spocStatus" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "ischeck",
		    	  	"render": function(data, type, report, meta) {
		    	  		 var result = "";
		    	  		 var flag = false;
		    	  		
		    	  	   for(var e in vendorcontactsList) {
		    	  		   if(report.spocEmail+"" === vendorcontactsList[e]) {
		    	  				 flag = true;
		    	  				 break;
		    	  			 }
		    	  		 }  
		    	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+report.id+'" data-id="'+report.id+'" value="'+report.spocEmail+'" onchange="checkVendorsContacts(this);"  type="checkbox" name="contactEmails[]"  class="myVendorContactsCheckbox" ></td>'; 
		    	  		
		    	  		  return result;
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "orgName",
	            "render": function ( data, type, report, meta ) {
	            	     var result = "";
		    	  		 result = result +'<input type="hidden" value="row'+report.id+'" id="row'+report.id+'">';
		    	  		result = result +'<td><div class="tbldata-dotdot-100" title="'+report.orgName+'">'+report.orgName+'</div></td>';
			             return  result;
	            }
	          },{
		            "targets": colIndex++,
		            "data": "spocName",
		            "render": function ( data, type, report, meta ) {
		            	     
		            	    var spocName ="";
		            	    if(report.spocName != null && report.spocName !='' && report.spocName !='undefined'){
		            	    	spocName = report.spocName;
		            	    }else{
		            	    	spocName = "----";
		            	    }
		            	     var result = "";
				   			 result = result +'<td><div class="proj_show" ><span title="'+spocName+'">'+spocName+'</span></div></td>';
			                 return result; 
		            }
		          }, {
	              "targets": colIndex++,
	              "data": "spocEmail",
	              "render": function ( data, type, report, meta ) {
	            	    var spocEmail="";
	            	     var result = "";
	            	     if(report.spocEmail != null && report.spocEmail !='' && report.spocEmail !='undefined'){
	            	    	 spocEmail=report.spocEmail;
	            	     }else{
	            	    	 spocEmail = "----";
	            	     }
	            	     result = result +'<td><div class="tbldata-dotdot-vsm" title='+spocEmail+'>'+spocEmail+'</div></td>'; 
	  	                 return result; 
	              }
	            },{
		    	  	"targets": colIndex++,
		    	  	"data": "spocStatus",
		    	  	"sortable": false,
		    	  	"render": function(data, type, report, meta) {
		    	  		 var result = "";
		    	  		result = result + ' <td><div class="tblminwidth-30 dropdown-select paddingNone">';
		    	  		result = result + '<span title='+report.spocStatus+'>'+report.spocStatus+'</span>';
	   	        		result = result + ' </div></td>'; 
		    	  		 return result; 
		    	  	}
		    	  }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[1,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": false,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
	    } );
		$("#vendorconDataTable").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>");
	    $("input[type=search]").hide();
	    $("#vendorContactsDataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   vendorContactDataTableList();
	    	   }
	    	});
}
</script>



<script>
function clientsList(){
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='clientDataTableSearch'/>";
	 	result = result +"</div>";
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
  		result = result +'<table id="clientDataTable" class="display table table-stripped table-bordered" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'             <th><input type="checkbox" id="checkAllClients" onclick="toggleClients(this);"></th>';
 		result = result +'             <th>Client Name</th>';
 		result = result +'             <th>Website</th>';
 		result = result +'             <th>Location</th>';
 		result = result +'             <th>Created By</th>';
 		result = result +'             <th>No.of Contacts</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="clientbtnId" onclick="getClientEmails();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    $("#mailBoxClientsList").empty();
	    $("#mailBoxClientsList").append(result);
	    clientList = {};
	    clientsDataTableList();
}
</script>

<script>
function clientsDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#clientDataTableSearch").val();
	 var responderType ="ClientHotListCampaign";
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/searchClientDetails?searchValue="+searchValue+"&responderType="+responderType;
	 clientsPagination(url);
	 
}
</script>


<script>
function clientsPagination(url){
	var colIndex = 0;
    $('#clientDataTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "ischeck" },
            { "data": "responderOrgName" },
            { "data": "website"},
            { "data": "createdBy"},
            { "data": "responderCity"},
            { "data": "noContacts"}
        ],
         "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "ischeck",
    	  	"render": function(data, type, opt, meta) {
    	  		 var result = "";
    	  		   var flag = false;
    	  		   
	   	  		   for(var e in clientList) {
	   	  			 if(opt.orgRelationId+"" === clientList[e]) {
	   	  				 flag = true;
	   	  				 break;
	   	  			 }
	   	  		 } 
    	  		
    	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+opt.orgRelationId+'" data-id="'+opt.orgRelationId+'" value="'+opt.orgRelationId+'" onchange="checkClients(this)"   type="checkbox" name="orgRelationIds[]" class="myClientCheckbox" ></td>';
    	  		  return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "orgName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  var orgName = "";
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+opt.orgName+'">'+opt.orgName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "website",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  var website = "";
	    	  		 if(opt.website != null && opt.website != 'undefined' && opt.website != ""){
	    	  			website = opt.website ;
		           	 }else{
		           		website = "----";
		           	 }
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+website+'">'+website+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "responderCity",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  var responderCity="";
            	  if(opt.responderCity != null && opt.responderCity != 'undefined' && opt.responderCity != ""){
            		  responderCity = opt.responderCity ;
		           	 }else{
		           		responderCity = "----";
		           	 }
            	  result = result + '<td data-title="Id"><div class="tblminwidth-75" title="'+responderCity+'">'+responderCity+'</div></td>';
	              return result;
              		
              }
          },{
		    	  "targets": colIndex++,
	              "data": "createdBy",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var createdBy = "";
	            	    if(opt.createdBy != null && opt.createdBy != 'undefined' && opt.createdBy != ""){
	            	    	createdBy = opt.createdBy ;
	   	           	     }else{
	   	           	   createdBy = "----";
	   	           	     }
	            	    
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+createdBy+'">'+createdBy+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "noContacts",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var noContacts = "";
	            		 if(opt.noContacts != null && opt.noContacts != 'undefined' && opt.noContacts != ""){
	            			 noContacts = opt.noContacts ;
	    	           	 }else{
	    	           		noContacts = "&nbsp;&nbsp;----";
	    	           	 }
	            	    
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+noContacts+'">'+noContacts+'</span></div></td>';
	            		return result;
	              }
		     }], 
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[7, 15, 20], [7, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     	"sSearch": "",
	     	"sZeroRecords": "", "sEmptyTable": "", 
	     	"sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#clientDataTable").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>"); 
    $("input[type=search]").hide();
    $("#clientDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   clientsDataTableList();
    	   }
    	});
}

</script>


<script>
var clientList = {};
function checkClients(ele){
	if($(ele).is(":checked")) {
       clientList[$(ele).data("id")] = $(ele).val();
   	 $("#clientbtnId").removeAttr("disabled");
	}
     else
     {
       clientList[$(ele).data("id")] = "";
     }
   
}

</script>



<script>
var clientcontactsList = {};
function checkClientsContacts(ele){
	if($(ele).is(":checked")) {
		clientcontactsList[$(ele).data("id")] = $(ele).val();
	}
	else
	{
	    clientcontactsList[$(ele).data("id")] = "";	
	}
}
</script>


<script>
var contactUsers = {};
function checkContactUsers(ele){
	if($(ele).is(":checked")) {
		contactUsers[$(ele).data("id")] = $(ele).val();
	}
	else
	{
		contactUsers[$(ele).data("id")] = "";	
	}
}
</script>


<script>
function vendorsList(){
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='vendorDataTableSearch'/>";
	 	result = result +"</div>";
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
  		result = result +'<table id="vendorDataTable" class="display table table-stripped table-bordered" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'             <th><input type="checkbox" id="checkAllVendors" onclick="toggleVendors(this);"></th>';
 		result = result +'             <th>Vendor Name</th>';
 		result = result +'             <th>Website</th>';
 		result = result +'             <th>Location</th>';
 		result = result +'             <th>Created By</th>';
 		result = result +'             <th>No.of Contacts</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="vendorbtnId" onclick="getContactEmails();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    $("#mailBoxVendorsList").empty();
	    $("#mailBoxVendorsList").append(result);
	    vendorList = {};
	    vendorsDataTableList();
}
</script>

<script>
function vendorsDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#vendorDataTableSearch").val();
	 var responderType ="JobCampaignVendor";
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/searchClientDetails?searchValue="+searchValue+"&responderType="+responderType;
	 vendorsPagination(url);
	 
}
</script>

<script>
var vendorList = {};
function checkVendors(ele){
	if($(ele).is(":checked")) {
		vendorList[$(ele).data("id")] = $(ele).val();
	}
	else
	{
		vendorList[$(ele).data("id")] = "";
	 }
}
</script>



<script>
var vendorcontactsList = {};
function checkVendorsContacts(ele){
	if($(ele).is(":checked")) {
		vendorcontactsList[$(ele).data("id")] = $(ele).val();
	}
	else
	{
		vendorcontactsList[$(ele).data("id")] = "";
	}
}
</script>


<script>
function vendorsPagination(url){
	var colIndex = 0;
    $('#vendorDataTable').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "ischeck" },
            { "data": "responderOrgName" },
            { "data": "website"},
            { "data": "createdBy"},
            { "data": "responderCity"},
            { "data": "noContacts"}
        ],
         "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "ischeck",
    	  	"render": function(data, type, opt, meta) {
    	  		 var result = "";
    	  		   var flag = false;
    	  		   
	   	  		   for(var e in vendorList) {
	   	  			 if(opt.orgRelationId+"" === vendorList[e]) {
	   	  				 flag = true;
	   	  				 break;
	   	  			 }
	   	  		 } 
    	  		
    	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+opt.orgRelationId+'" data-id="'+opt.orgRelationId+'" value="'+opt.orgRelationId+'" onchange="checkVendors(this)"   type="checkbox" name="orgRelationIds[]" class="myVendorCheckbox" ></td>';
    	  		  return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "orgName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  var orgName = "";
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+opt.orgName+'">'+opt.orgName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "website",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  var website = "";
	    	  		 if(opt.website != null && opt.website != 'undefined' && opt.website != ""){
	    	  			website = opt.website ;
		           	 }else{
		           		website = "----";
		           	 }
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+website+'">'+website+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "responderCity",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  var responderCity="";
            	  if(opt.responderCity != null && opt.responderCity != 'undefined' && opt.responderCity != ""){
            		  responderCity = opt.responderCity ;
		           	 }else{
		           		responderCity = "----";
		           	 }
            	  result = result + '<td data-title="Id"><div class="tblminwidth-75" title="'+responderCity+'">'+responderCity+'</div></td>';
	              return result;
              		
              }
          },{
		    	  "targets": colIndex++,
	              "data": "createdBy",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var createdBy = "";
	            	    if(opt.createdBy != null && opt.createdBy != 'undefined' && opt.createdBy != ""){
	            	    	createdBy = opt.createdBy ;
	   	           	     }else{
	   	           	   createdBy = "----";
	   	           	     }
	            	    
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+createdBy+'">'+createdBy+'</span></div></td>';
	            		return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "noContacts",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var noContacts = "";
	            		 if(opt.noContacts != null && opt.noContacts != 'undefined' && opt.noContacts != ""){
	            			 noContacts = opt.noContacts ;
	    	           	 }else{
	    	           		noContacts = "&nbsp;&nbsp;----";
	    	           	 }
	            	    
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+noContacts+'">'+noContacts+'</span></div></td>';
	            		return result;
	              }
		     }], 
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[7, 15, 20], [7, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     	"sSearch": "",
	     	"sZeroRecords": "", "sEmptyTable": "", 
	     	"sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#vendorDataTable").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>"); 
    $("input[type=search]").hide();
    $("#vendorDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   vendorsDataTableList();
    	   }
    	});
}

</script>



<script>
function getContactsList(){
	
	document.getElementById("vendorsListDiv").style.display="none";
	document.getElementById("clientsListDiv").style.display="none";
	
	$("#mailBoxVendorsList").empty();
	$("#mailBoxClientsList").empty();
	
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='contactsDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	 	result = result +'       <div class="col-xs-12 col-md-4 paddingLeft selectpicker100 mb10 mt" id="groupPopUpId">';
 		result = result +'     <select id="groupsFilter" name="groupsFilter" class="selectpicker" multiple data-actions-box="true" onchange="contactsDataTableList();" style="display:block !important" title="--Select Group--">';
		
 		<c:forEach var="conGroup" items="${contactsGroupList}">
		result = result +' <option value="${conGroup.id}">${conGroup.groupName}</option>';
        </c:forEach>
 		
 		result = result +'          </select>';
 		result = result +'       </div>';
	 	
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
	 	
  		result = result +'<table id="example" class="display table table-stripped table-bordered" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +' <th><input type="checkbox" name="checkAllContacts" onclick="toggleMyContacts(this);"></th>';
		result = result +'<th>Contact Name</th>';
		result = result +'<th>Email</th>';
		result = result +'<th>Source</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    
	    result = result +'</div>'; 
    	result = result +'<div class="clearfix">';
    	result = result +'</div>'; 
    	result = result +'</div>';
    	
    	result = result +'<div class="clearfix pull-right mr mb">';
     	result = result +'<button class="btn btn-primary"  type="button" id="assignbtnId" onclick="getAllEmailContacts();">Add</button>';
     	result = result +' <button class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
     	
     	$("#mailBoxVendorsList").append(result);
     	contactUsers = [];
	    contactsDataTableList();
	    $("#groupsFilter").selectpicker("refresh");
}
</script>

<script>
function contactsDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#contactsDataTableSearch").val();
	 
	 var contactsGroups = '';
	 if($("#groupsFilter").val() != null){
		 contactsGroups = $("#groupsFilter").val();
	 }
	 
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getUserContactsListForMalling?searchValue="+searchValue+"&groupIdsList="+contactsGroups;
	 contactsPagination(url);
}
</script>

<script>
function contactsPagination(url){
	var colIndex = 0;
    $('#example').DataTable( { 
	  "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
          url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "ischeck" },
            { "data": "contactName" },
            { "data": "email"},
            { "data": "source"}
           
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "ischeck",
    	  	"render": function(data, type, opt, meta) {
    	  		var result = "";
    	  		var flag = false;
   	  		 
	   	  		   for(var e in contactUsers) {
	   	  			 if(opt.email+"" === contactUsers[e]) {
	   	  				 flag = true;
	   	  				 break;
	   	  			 }
	   	  		 } 
   	  		 
   	  		  result = result +'<td>&nbsp;&nbsp;<input'+(flag ? " checked='checked'" : "")+' id="'+opt.id+'" data-id="'+opt.id+'" value="'+opt.email+'" onchange="checkContactUsers(this)"  type="checkbox" name="emails[]"  class="myCheckbox" ></td>';
	              return result;
    	  	}
         },{
	    	  	"targets": colIndex++,
	    	  	"data": "contactName",
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		  result = result + '<td data-title="Id"><div class="tblminwidth-75"  title="'+opt.fullName+'">'+opt.fullName+'</div></td>';
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "email",
              "render": function ( data, type, opt, meta ) {
            	  var result = "";
            	  result = result + '<td data-title="Id"><div class="tblminwidth-75" title="'+opt.email+'">'+opt.email+'</div></td>';
	              return result;
              		
              }
	      
	          },{
		    	  "targets": colIndex++,
	              "data": "source",
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    if(opt.source != null && opt.source !="" && opt.source != "undefined"){
		            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.source+'">'+opt.source+'</span></div></td>';
	            	    }else{
		            		result = result + '<td><div class="tblminwidth-75">--</div></td>';
	            	    }
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[7, 15, 7], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#example").wrap("<div class='modal-body-custom modalAlign mailerpopup' style='overflow-x: auto;'></div>"); 
    $("input[type=search]").hide();
    $("#contactsDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
    		   contactsDataTableList();
    	   }
    	});
}

</script>

<script>
/*var contactUsers = [];
function checkContactUsers(ele) {
	if($(ele).is(":checked")) {
		contactUsers[contactUsers.length] = $(ele).val();
	}
 }*/

</script>

<script>
function toggleVendors(source) {
var checkboxes = document.querySelectorAll('input[class="myVendorCheckbox"]');
if($(source).is(":checked"))
	  $('input[class="myVendorCheckbox"]').prop('checked', 'checked');
else 
	  $('input[class="myVendorCheckbox"]').removeProp('checked');

$('input[class="myVendorCheckbox"]').each(function(i) {
	  if($(this).is(":checked")) {
		  vendorList[$(this).data("id")] = $(this).val();
	  } else {
		  vendorList[$(this).data("id")] = "";
	  }
});
}
</script>


<script>
function toggleVendorContacts(source) {

if($(source).is(":checked"))
	  $('input[class="myVendorContactsCheckbox"]').prop('checked', 'checked');
else 
	  $('input[class="myVendorContactsCheckbox"]').removeProp('checked');

$('input[class="myVendorContactsCheckbox"]').each(function(i) {
	  if($(this).is(":checked")) {
		  vendorcontactsList[$(this).data("id")] = $(this).val();
	  } else {
		  vendorcontactsList[$(this).data("id")] = "";
	  }
});

}
</script>




<script>
function toggleClients(source) {
var checkboxes = document.querySelectorAll('input[class="myClientCheckbox"]');
if($(source).is(":checked"))
	{
	  $('input[class="myClientCheckbox"]').prop('checked', 'checked');
	  $("#clientbtnId").removeAttr("disabled");
	}
else 
	{
	  $('input[class="myClientCheckbox"]').removeProp('checked');
	}

$('input[class="myClientCheckbox"]').each(function(i) {
	  if($(this).is(":checked")) {
		  clientList[$(this).data("id")] = $(this).val();
	  } else {
		  clientList[$(this).data("id")] = "";
	  }
});
}
</script>


<script>
function toggleClientContacts(source) {

if($(source).is(":checked"))
	  $('input[class="myClientContactsCheckbox"]').prop('checked', 'checked');
else 
	  $('input[class="myClientContactsCheckbox"]').removeProp('checked');

$('input[class="myClientContactsCheckbox"]').each(function(i) {
	  if($(this).is(":checked")) {
		  clientcontactsList[$(this).data("id")] = $(this).val();
	  } else {
		  clientcontactsList[$(this).data("id")] = "";
	  }
});

}
</script>
<script>
function toggleMyContacts(source) {

if($(source).is(":checked"))
{
	$('input[name="checkAllContacts"]').attr('checked', 'checked');
	 $("#multiplemail").modal('hide');
	 $('#groupPopUpId').parent().find('p.jquery_form_error_message').remove();
}
else 
{  $('input[name="checkAllContacts"]').removeAttr('checked');
$("#groupPopUpId").after("<p class='jquery_form_error_message'><span>Select Atleast One Vendor Contact</span>");
}
$('input[name="checkAllContacts"]').each(function(i) {
	  if($(this).is(":checked")) {
		  contactUsers[$(this).data("id")] = $(this).val();
	  } else {
		  contactUsers[$(this).data("id")] = "";
	  }
});

}
</script>

<script>
function getContactEmails(){
	var orgRelationIds = new Array();
	var internalOutput = "";
	
	 for(var e in vendorList) {
		 if(vendorList[e] == 'Email' || vendorList[e] == 'Notification' || vendorList[e] == 'on')
				continue;
		 if(vendorList[e] != '')
		 {
		  orgRelationIds.push(vendorList[e])
		 }
	  }
	 
	 var elist=$("#recipientEmail").chosen().find("option:selected" ).text();
	 if(elist == '' && orgRelationIds.length == 0)
	 {
		 $('#vendorsListDiv').find('p.jquery_form_error_message').remove();
		  $("#vendorbtnId").attr("disabled", "disabled");
		  $("#vendorListType").after( "<p class='jquery_form_error_message'>Select atleast one vendor</p>");
	 }
	 else
	  {
	 var recipientsEmails = '';
	 if($("#recipientEmail").val() != null){
		  recipientsEmails = $("#recipientEmail").val();
	 }
	 
	 var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
         type : "POST",
         url: sourceleadUrl+"/getVendorContactsEmailsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationIds="+orgRelationIds,    
          success :function(data) {
       	    var str_array = data.replace(/\"/g, "").split(',');
				for(var i = 0; i < str_array.length; i++) {
				   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
				   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
				}
				
				 $("#recipientEmail").append(internalOutput);
				 $("#recipientEmail").trigger("chosen:updated");
				 
				 $("input[name='orgRelationIds[]']:checked").removeAttr('checked');
				 $('#checkAllVendors').removeAttr('checked');
				
          },
          
     }); 
	 
}
}
</script>


<script>
function getClientContactEmails(){
	
	var internalOutput = "";
	
	 for(var e in clientcontactsList) {
		 if(clientcontactsList[e] == 'Email' || clientcontactsList[e] == 'Notification' || clientcontactsList[e] == 'on')
				continue;
		 if(clientcontactsList[e] != '')
		 {
			 
		 internalOutput = internalOutput+"<option value='" +clientcontactsList[e]+ "' selected>"+clientcontactsList[e]+"</option>";
		 }
	  }
	
	
	    $("#recipientEmail").append(internalOutput);
		$("#recipientEmail").trigger("chosen:updated");
		
		$("input[name='contactEmails[]']:checked").removeAttr('checked');
		$('#checkAllClientContacts').removeAttr('checked');
		
}
</script>





<script>
function getVendorContactEmails(){
	
	
	var internalOutput = "";
	
	 for(var e in vendorcontactsList) {
		 if(vendorcontactsList[e] == 'Email' || vendorcontactsList[e] == 'Notification' || vendorcontactsList[e] == 'on')
				continue;
		 if(vendorcontactsList[e] != '')
		 {
		 internalOutput = internalOutput+"<option value='" +vendorcontactsList[e]+ "' selected>"+vendorcontactsList[e]+"</option>";
		 }
	  }
	
	
	    $("#recipientEmail").append(internalOutput);
		$("#recipientEmail").trigger("chosen:updated");
		
		$("input[name='contactEmails[]']:checked").removeAttr('checked');
		$('#checkAllVendorContacts').removeAttr('checked');
		
}
</script>

<script>
function getAllEmailContacts(){
	$('#groupPopUpId').parent().find('p.jquery_form_error_message').remove();
	var internalOutput = "";
	
	for(var e in contactUsers) {
		
		if(contactUsers[e] == 'Email' || contactUsers[e] == 'Notification' || contactUsers[e] == 'on')
			continue;
	   var elist=$("#recipientEmail").chosen().find("option:selected" ).text();
	   if(contactUsers[e] != '')
	  {
	   var searchString=elist.search(contactUsers[e]);
		 if(searchString==-1)
			{
		internalOutput = internalOutput+"<option value='" +contactUsers[e]+ "' selected>"+contactUsers[e]+"</option>";
			}
	  }
	}
	
	    $("#recipientEmail").append(internalOutput);
		$("#recipientEmail").trigger("chosen:updated");
		
		$("input[name='emails[]']:checked").removeAttr('checked');
		$('#checkAllVendors').removeAttr('checked');
		
		
}
</script>

<script>
function getClientEmails(){
    var orgRelationIds = new Array();
	var internalOutput = "";
	
	 for(var e in clientList) {
	
		 if(clientList[e] == 'Email' || clientList[e] == 'Notification' || clientList[e] == 'on')
				continue;
		 if(clientList[e]!='')
			 {
		 orgRelationIds.push(clientList[e])
			 }
	  }
	
	 var elist=$("#recipientEmail").chosen().find("option:selected" ).text();
	 if(elist == '' && orgRelationIds.length == 0)
	 {
		 $('#clientsListDiv').find('p.jquery_form_error_message').remove();
		  $("#clientbtnId").attr("disabled", "disabled");
		  $("#clientListType").after( "<p class='jquery_form_error_message'>Select atleast one client</p>");
	 }
	 else
	  {
	 var recipientsEmails = '';
	 if($("#recipientEmail").val() != null){
		  recipientsEmails = $("#recipientEmail").val();
	 }
	 var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
         type : "POST",
         url: sourceleadUrl+"/getClientContactsEmailsByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationIds="+orgRelationIds+"&recipientsEmails="+recipientsEmails,    
          success :function(data) {
        	  if(data != null){
        		  var str_array = data.replace(/\"/g, "").split(',');
        	     for(var i = 0; i < str_array.length; i++) {
  				   str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");
  				   internalOutput = internalOutput+"<option value='" +str_array[i]+ "' selected>"+str_array[i]+"</option>";
  				}
        	  }
				
				 $("#recipientEmail").append(internalOutput);
				 $("#recipientEmail").trigger("chosen:updated");
				 
				 $("input[name='orgRelationIds[]']:checked").removeAttr('checked');
				 $('#checkAllClients').removeAttr('checked');
				
          },
          
     }); 
		 }
	 
	
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
    for (var selector in config) {
    $(selector).chosen(config[selector]);
    }
    $('.chosen-select').each(function() {
    var $this = $(this);
    $this.next().css({'width': '100%'});
    })
    $(document).ready(function(){
    $("#cd").click(function(){
    $(".clientDetails").toggleClass('hidden', 500);
    });
});
</script>

 <script>
function validateCampaignListName(){
	var locationId = '${SourceLeadWeb_orgLocationId}';
	var name = $("#cpnListName").val();
	if(name != null && name != '' && name != 'undefined'){
		if(name.length < 100){
			$.get(sourceleadUrl+"/validateCampaignListName?&orgLocationId="+locationId+"&from=CampaignList&name="+name, function(data) {
	          	if(data == true){
	          		$('#cpnListNameDiv').find('p.jquery_form_error_message').remove();
	          		$("#addList").removeAttr("disabled");
			        $("#addList").attr("disabled", "disabled");
					$("input#cpnListName").after( "<p class='jquery_form_error_message'>" + name + " is already existed</span>");
	                 }else{
	                	$("#addList").removeAttr("disabled");
	                	$('#cpnListNameDiv').find('p.jquery_form_error_message').remove();
	                 }
	            });
		}
		}
		
}
</script>

 <script>
 function msgFadein() {
	$("#selector1").fadeIn(300);
    $('#selector1').delay(3000).fadeOut('slow');
}
</script>
<!--   Popup Code  -->   