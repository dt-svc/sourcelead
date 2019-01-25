<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script>
	function loadVendorContactDataTable(vendorSearchResult) {
		if (vendorSearchResult == "universalVendorList") {
			getVendorContactList();
			$("#dataTableSearch").val(vendorSearchResult);
		 }else{
			 getVendorContactList();
		 } 
	}
	
	

function getVendorContactList(){
	var clientIds = [];
	$('input:checkbox[name=vendorNameById]:checked').each(function() 
    {
		clientIds.push($(this).val());
    });
	var status = [];
	$('input:checkbox[name=vendorStatus]:checked').each(function() 
    {
		status.push($(this).val());
    });
	
	var invitationStatus = [];
	$('input:checkbox[name=vendorInvitationStatus]:checked').each(function() 
    {
		invitationStatus.push($(this).val());
    });
	
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	var loginOrgLocId = '${loginOrgLocId}';
	var responderType = "Vendor";
	var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
	 if ($('#vendorList_form').validate(false, validationSettings)){ 
			var frm = $('#vendorList_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#vendorList_form')[0];
	  		var formData = new FormData(form); 
	  		var searchValue = $("#dataTableSearch").val();
	  		searchValue = searchValue ? searchValue : "";
	  		var url = vmsUrl+"/searchClientContactDetails?responderType="+responderType+"&searchValue="+searchValue+"&clientIds="+clientIds+"&status="+status+"&invitationStatus="+invitationStatus;
	  	    url += "&"+$($('#vendorList_form')[0]).serialize();  
	  	    prepareVendorContactList(url);
	  		closePop();
			return true;
		 }else{ 
				return false;
		 } 
	
	 $('body').on('blur', '#vendorList_form', function() {
			$("#vendorList_form").showHelpOnFocus();
			$("#vendorList_form").validateOnBlur(false, validationSettings);
		}); 
}	

	function prepareVendorContactList(url) {
		$("#vendorContactList").removeAttr("style");
		$("#vendorContactList").empty();
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
		$("#vendorList").empty();
		$("#vendorList_wrapper").hide();
		$("#vendorList").hide();
		$("#vendorContactList").append(result);
		vendorContactpagination(url);
	}
	
	function vendorContactpagination(url){
		var colIndex = 0;
		 $('#vendorContactList').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'POST',
	            pages: 10
	        }),
	        "columns": [
	            { "data": "orgName" },
	            { "data": "spocName"},
	            { "data": "spocEmail" },
	            { "data": "spocMobilePhone"},
	            { "data": "ownerName" },
	            { "data": "spocStatus" },
	            { "data": "invitationStatus" },
	            { "data": "action" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "orgName",
		    	  	"render": function(data, type, report, meta) {
		    	  	   $("#emptyVendorcontactList").val(1);
		    	  		 var result = "";
		    	  		 result = result +'<input type="hidden" value="row'+report.id+'" id="row'+report.id+'">';
		    	  		result = result +'<td><div class="tbldata-dotdot-100" title="'+report.orgName+'">'+report.orgName+'</div></td>';
			             return  result;
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "spocName",
	            "render": function ( data, type, report, meta ) {
	            	     var result = "";
	            	     '<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR_CONTACT\",\"ROLE_VIEW_EDIT_VENDOR_CONTACT\")">';
			   				result = result +'<td><div class="proj_show"onclick="vendorSpocSideBar(\''+report.id+'\',\''+report.ownerUserId+'\',\''+report.spocUserId+'\',\''+report.spocName+'\',\''+report.tempOwnerId+'\',\''+report.spocLocationId+'\',\''+report.orgRelationId+'\',\''+report.userId+'\');"><span title="'+report.spocName+'">'+report.spocName+'</span><i class="fa fa-binoculars"></i></div></td>';
			   		'</sec:authorize>';
			   		
			   		'<sec:authorize access="!hasAnyRole(\"ROLE_VIEW_VENDOR_CONTACT\",\"ROLE_VIEW_EDIT_VENDOR_CONTACT\")">';
			   				result = result +'<td><div class="proj_show" ><span title="'+report.spocName+'">'+report.spocName+'</span></div></td>';
					'</sec:authorize>';
		              return result; 
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "spocEmail",
	              "render": function ( data, type, report, meta ) {
	            	   var result = "";
	            	   result = result +'<td><div class="tbldata-dotdot-vsm" title='+report.spocEmail+'>'+report.spocEmail+'</div></td>';
	  	              return result; 
	              }
	            }, {
	                "targets": colIndex++,
	                "data": "spocMobilePhone",
	                "sortable": false,
	                "render": function ( data, type, report, meta ) {
	                	   var result = "";
	                	   if(report.spocMobilePhone != null && report.spocMobilePhone  != '' && report.spocMobilePhone  != 'undefined'){
	       		   			result = result +'<td><div class="tbldata-dotdot-100" title="'+report.spocMobilePhone+'">'+report.spocMobilePhone+'</div></td>';
	       		   		}else{
	       		   			result = result +'<td><div>--</div></td>';
	       		   		}
	    	              return result; 
	                }
	          },{
		    	  	"targets": colIndex++,
		    	  	"data": "ownerName",
		    	  	"sortable": false,
		    	  	"render": function(data, type, report, meta) {
		    	  	 	var result = "";
		    	  	 	if(null != report.ownerNames  &&  '' != report.ownerNames  && 'undefined' != report.ownerNames){
				   			result = result +'<td><div class="tbldata-dotdot-100" title="'+report.ownerNames+'">'+report.ownerNames+'</div></td>';
				   		}
		    	  	   return result; 
		    	  	}
		    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "spocStatus",
		    	  	"sortable": false,
		    	  	"render": function(data, type, report, meta) {
		    	  		 var result = "";
		    	  		result = result + ' <td><div class="tblminwidth-30 dropdown-select paddingNone">';
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
		    	  		 return result; 
		    	  	}
		    	  },{
			    	  	"targets": colIndex++,
			    	  	"data": "invitationStatus",
			    	  	"sortable": false,
			    	  	"render": function(data, type, report, meta) {
			    	  		 var result = "";
			    	  		result = result +' <td><div id="invitationStatus'+report.id+'" class="tblminwidth-30">';	
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
			    	  		 return result; 
			    	  	}
		    	  },{
				    	  	"targets": colIndex++,
				    	  	"data": "action",
				    	  	"sortable": false,
				    	  	"render": function(data, type, report, meta) {
				    	  		 var result = "";
				    	  		'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_VENDOR_CONTACT\')">';
						   	    result = result + '<td><div class="tblminwidth-50"><i title="Remove vendor contact" class="glyphicon glyphicon-trash" onClick="terminateSpoc1('+report.id+'\,\''+report.orgRelationId+'\',\''+report.spocEmail+'\')"; title="Remove vendor contact"></i></div></td>';
						   	 '</sec:authorize>';
						   	 
						   	'<sec:authorize access="!hasAnyRole(\'ROLE_DELETE_VENDOR_CONTACT\')">';
							    result = result + '<td><div>---</div></td>';
							 '</sec:authorize>';
				    	  		 return result; 
				    	  	}
		    	  }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[0,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
	    } );
	    $("input[type=search]").hide();
	    $("#dataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getVendorContactList();
	    	   }
	    	});
}
</script>