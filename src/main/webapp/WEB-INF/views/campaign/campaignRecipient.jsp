<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>
.modal-backdrop{
z-index: 1040 !important;
}
</style>

<script type="text/javascript">
    var staticTime='${staticTime}'
    
    	var tokenParamName = "${_csrf.parameterName}";
    var tokenValue = "${_csrf.token}";
 </script>

<script src="resources/js/formvalidator.js?staticTime=${staticTime}"></script>
<!--   Popup Code  -->
	<form:form id="capn_add_Recipient_List" name="capn_add_Recipient_List" method="post">
		<div class="popupHead">
			<h2>Add Recipient</h2><a onclick="recipientClose();" href="javascript:void(0)" class="fa fa-close popCan"></a>
		</div>
		<div id="loading"></div>
		<div class="popupMid popupMidPad clearfix">

          <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-3 padding_left">List Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-9 padding_right">
                	<input type="text" class="form-control" id="cpnListName" name="cpnListName" value="" data-validation="required validate_length length1-250" field-name="List Name">
				</div>
			</div>
			<!-- <div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-3 padding_left">First Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-9 padding_right">
                	<input type="text" class="form-control" id="recipientFirstName" name="recipientFirstName" value="">
				</div>
			</div>
			<div class="col-sm-12 noPad mt clearfix">
				<label class="col-md-3 padding_left">Last Name<span class="text-danger">&nbsp;*</span></label>
              	<div class="col-md-9 padding_right">
                	<input type="text" class="form-control" id="recipientLastName" name="recipientLastName" value="">
				</div>
			</div> -->
          <div class="col-sm-12 noPad mt clearfix">
              <label class="col-md-3 padding_left">Recipient(Email)<span class="text-danger">&nbsp;*</span></label>
              <div class="col-md-8">
                <select multiple class="chosen-select form-control" id="recipientEmail" name="recipientEmail" data-validation="required validate_length length1-250" field-name="Recipient Email">
                </select>
              </div>
              	<div class="col-md-1 noPad">
					<a href="javascript:void(0)"  data-target="#multiplemail" onclick="selectRadio();vendorListChange()" data-toggle="modal" class="btn btn-default"><i class="fa fa-list-alt"></i></a>
					<div role="dialog" id="multiplemail" class="modal fade">
              <div class="modal-dialog modal-lg">
                <div class="modal-content">
                  <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">Select Vendors</h4>
                    <label class="paddingLeft paddingRight">
               	    <input  type="radio" id="vendorCheck" name="users" checked onclick="vendorListChange()"><strong class="paddingLeft">Vendors</strong></label>
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
                  
                </div>
              </div>
            </div>
				</div>              
			</div>	
			
		
		</div>
		
		<div class="popupFoot">
            <input value="Save" class="btn btn-primary" type="button" id="addList">
            <input value="Cancel" class="btn btn-default ml popCan" type="button" onclick="recipientClose();">
          </div>		
		</form:form>
		
		
		
		<script>
  function selectRadio(){
	  document.getElementById("vendorCheck").checked = true;
	  document.getElementById("contactsCheck").checked = false;
	 $("#vendorListType").val('listbyVendor');
  }
  </script>
  
  
  
  <script>
	  function vendorListChange(){
		  document.getElementById("vendorsListDiv").style.display="";
		  var listType = document.getElementById("vendorListType").value;
		  if(listType == 'listbyContact'){
			  $("#mailBoxVendorsList").empty(); 
			  vendorContactList();
		  }else{
			  $("#mailBoxVendorsList").empty(); 
			  vendorsList();
		  }
		  
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
}
</script>



<script>
var vendorcontactsList = {};
function checkVendorsContacts(ele){
	if($(ele).is(":checked")) {
		vendorcontactsList[$(ele).data("id")] = $(ele).val();
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
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"aaSorting": [[1,'desc']],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"ordering": false,
	    	"oLanguage": {
	     	"sSearch": "",
	     	"sZeroRecords": "", "sEmptyTable": "", 
	     	"sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
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
	$("#mailBoxVendorsList").empty();
	
	    var result = "";
	 	result = result +" <div class='remove_search'>";
	 	result = result +" <input type='text' placeholder='Search' id='contactsDataTableSearch'/>";
	 	result = result +"</div>";
	 	
	    result = result +'<div class="clearfix popupFilter">';
  		result = result +'<div class="mt clearfix popupFilter"><br/><br/>'; 
	 	
  		result = result +'<table id="example" class="display table table-stripped table-bordered" cellspacing="0" width="100%"  >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +' <th><input type="checkbox" id="checkAllContacts" onclick="toggleMyContacts(this);"></th>';
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
     	result = result +'<button class="btn btn-primary"  type="button" data-dismiss="modal" id="assignbtnId" onclick="getAllEmailContacts();">Add</button>';
     	result = result +' <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>';
     	result = result +' </div>'; 
	    
     	$("#mailBoxVendorsList").append(result);
     	contactUsers = [];
	    contactsDataTableList();
}
</script>

<script>
function contactsDataTableList(){
	 var sourceleadUrl ='${sourceleadUrl}';
	 var searchValue = $("#contactsDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 var url = sourceleadUrl+"/getUserContactsListForMalling?searchValue="+searchValue;
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
	            		result = result + '<td><div class="tblminwidth-75"><span title="'+opt.source+'">'+opt.source+'</span></div></td>';
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
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
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
var contactUsers = [];
function checkContactUsers(ele) {
	if($(ele).is(":checked")) {
		contactUsers[contactUsers.length] = $(ele).val();
	}
}
</script>


<script>
function toggleMyContacts(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
            checkContactUsers(checkboxes[i])
        
    }
$('input:checkbox:not(#checkAllContacts)').click(function() {
    if(!this.checked) {
        $('#checkAllContacts').removeAttr('checked');
    }
});
}
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
function getContactEmails(){
	var orgRelationIds = new Array();
	var internalOutput = "";
	
	 for(var e in vendorList) {
		 if(vendorList[e] == 'Email' || vendorList[e] == 'Notification' || vendorList[e] == 'on')
				continue;
		 orgRelationIds.push(vendorList[e])
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
				 $('#checkAllClients').removeAttr('checked');
				
          },
          
     }); 
	 
}
</script>



<script>
function getVendorContactEmails(){
	
	
	var internalOutput = "";
	
	 for(var e in vendorcontactsList) {
		 if(vendorcontactsList[e] == 'Email' || vendorcontactsList[e] == 'Notification' || vendorcontactsList[e] == 'on')
				continue;
		 internalOutput = internalOutput+"<option value='" +vendorcontactsList[e]+ "' selected>"+vendorcontactsList[e]+"</option>";
	  }
	
	
	    $("#recipientEmail").append(internalOutput);
		$("#recipientEmail").trigger("chosen:updated");
		
		$("input[name='contactEmails[]']:checked").removeAttr('checked');
		$('#checkAllVendorContacts').removeAttr('checked');
		
}
</script>

<script>
function getAllEmailContacts(){
	var internalOutput = "";
	
	for(var e in contactUsers) {
		if(contactUsers[e] == 'Email' || contactUsers[e] == 'Notification' || contactUsers[e] == 'on')
			continue;
		internalOutput = internalOutput+"<option value='" +contactUsers[e]+ "' selected>"+contactUsers[e]+"</option>";
	}
	
	    $("#recipientEmail").append(internalOutput);
		$("#recipientEmail").trigger("chosen:updated");
		
		$("input[name='emails[]']:checked").removeAttr('checked');
		$('#checkAllVendors').removeAttr('checked');
		
		
}
</script>

<script>
function getContactEmails(){
	var orgRelationIds = new Array();
	var internalOutput = "";
	
	 for(var e in vendorList) {
		 if(vendorList[e] == 'Email' || vendorList[e] == 'Notification' || vendorList[e] == 'on')
				continue;
		 orgRelationIds.push(vendorList[e])
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
				 $('#checkAllClients').removeAttr('checked');
				
          },
          
     }); 
	 
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
<!--   Popup Code  -->   