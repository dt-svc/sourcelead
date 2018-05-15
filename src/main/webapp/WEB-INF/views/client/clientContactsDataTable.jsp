<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script>
	function loadClientContactDataTable(clientSearchResult) {
		if (clientSearchResult == "clientSearchResult") {
			getClientContactList();
// 			$("#dataTableSearch").val(clientSearchResult);
		 }else{
			 getClientContactList();
		 }
	}
	
	

function getClientContactList(){
	var responderType = "Client";
	var sourceleadUrl ='${sourceleadUrl}';
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
	var validationSettings = {
			errorMessagePosition : 'element',
			borderColorOnError : '',
			scrollToTopOnError : true,
			dateFormat : 'yyyy/mm/dd'
		};
	 if ($('#clientlist_form').validate(false, validationSettings)){ 
			var frm = $('#clientlist_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#clientlist_form')[0];
	  		var formData = new FormData(form); 
	  		var searchValue = $("#dataTableSearch").val();
	  		searchValue = searchValue ? searchValue : "";
	  		var url = sourceleadUrl+"/searchClientContactDetails?responderType="+responderType+"&searchValue="+searchValue+"&clientIds="+clientIds+"&status="+status+"&invitationStatus="+invitationStatus;
	  	    url += "&"+$($('#clientlist_form')[0]).serialize();  
	  		prepareClientContactList(url);
	  		closePop();
			return true;
		 }else{ 
				return false;
		 } 
	
	 $('body').on('blur', '#clientlist_form', function() {
			$("#clientlist_form").showHelpOnFocus();
			$("#clientlist_form").validateOnBlur(false, validationSettings);
		}); 
}	

	function prepareClientContactList(url) {
		$("#vendorContactList").removeAttr("style");
		$("#vendorContactList").empty();
		var result = "";
		result = result + "<table id='clientContactListPagination' class='display' cellspacing='0' width='100%' >";
		result = result + "<thead>";
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
		result = result + "</thead>";
		result = result + "</table>";
		$("#clientList").hide();
		$("#vendorContactList").append(result);
		clientContactpagination(url);
	}
	
	function clientContactpagination(url){
		var colIndex = 0;
		 $('#clientContactListPagination').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'POST',
	            pages: 10
	        }),
	        "columns": [
	            { "data": "clientLocationId" },
	            { "data": "clientUserFirstName"},
	            { "data": "clientUserEmail" },
	            { "data": "clientUserPhNo"},
	            { "data": "ownerUserId" },
	            { "data": "spocStatus" },
	            { "data": "invitationStatus" },
	            { "data": "action" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "clientLocationId",
		    	  	"render": function(data, type, report, meta) {
		    	  	   $("#clientContactLength").val(1);
		    	  		 var result = "";
		    	  		 result = result +'<input type="hidden" value="row'+report.id+'" id="row'+report.id+'">';
		    	  		result = result +'<td><div class="tbldata-dotdot-sm" title="'+report.orgName+'">'+report.orgName+'</div></td>';
			             return  result;
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "spocName",
	            "render": function ( data, type, report, meta ) {
	            	     var result = "";
	            	     '<sec:authorize access="hasAnyRole(\"ROLE_VIEW_CLIENT_CONTACT\")">';
	            	     result = result +'<td><div class="proj_show"onclick="clientSpocSideBar(\''+report.id+'\',\''+report.ownerUserId+'\',\''+report.spocUserId+'\',\''+report.spocName+'\',\''+report.spocLocationId+'\',\''+report.orgRelationId+'\',\''+report.userId+'\','+report.tempOwnerId+');"><span title="'+report.spocName+'">'+report.spocName+'</span><i class="fa fa-binoculars"></i></div></td>';
			   			'</sec:authorize>';
			   		
			   			'<sec:authorize access="!hasAnyRole(\"ROLE_VIEW_CLIENT_CONTACT\")">';
			   			result = result +'<td><div class="proj_show" ><span title="'+report.spocName+'">'+report.spocName+'</span></div></td>';
						'</sec:authorize>';
		              return result; 
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "spocEmail",
	              "render": function ( data, type, report, meta ) {
	            	   var result = "";
	            	   result = result +'<td><div><span title='+report.spocEmail+'>'+report.spocEmail+'</span></div></td>';
	  	              return result; 
	              }
	            }, {
	                "targets": colIndex++,
	                "data": "spocMobilePhone",
	                "sortable": false,
	                "render": function ( data, type, report, meta ) {
	                	   var result = "";
	                	   if(report.spocMobilePhone != null && report.spocMobilePhone  != '' && report.spocMobilePhone  != 'undefined'){
	       		   			result = result +'<td><div><span>'+report.spocMobilePhone+'</span></div></td>';
	       		   			}else{
	       		   			result = result +'<td><div>--</div></td>';
	       		   		}
	    	              return result; 
	                }
	          },{
		    	  	"targets": colIndex++,
		    	  	"data": "ownerUserId",
		    	  	"render": function(data, type, report, meta) {
		    	  	 	var result = "";
		    	  	 	if(null != report.ownerNames  &&  '' != report.ownerNames  && 'undefined' != report.ownerNames){
							 result = result +'<td><div><span title="'+report.ownerNames+'">'+report.ownerNames+'</span></div></td>';
						}
		    	  	   return result; 
		    	  	}
		    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "spocStatus",
		    	  	"sortable": false,
		    	  	"render": function(data, type, report, meta) {
		    	  		 var result = "";
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
		    	  		 return result; 
		    	  	}
		    	  },{
			    	  	"targets": colIndex++,
			    	  	"data": "invitationStatus",
			    	  	"sortable": false,
			    	  	"render": function(data, type, report, meta) {
			    	  		 var result = "";
			    	  		result = result + ' <td><div id="invitationStatus'+report.id+'">';
					   		if(report.spocUserId == "${SourceLeadWeb_userId}" && report.invitationStatus == 'Sent'){
					   		  result = result +' <a class="fa fa-check" alt="Accept" href="'+sourceleadUrl+'/acceptVendorOnboardInvitation?onboardUUID='+report.onboardUUID+'&notificationUUID='+report.notificationUUID+'"></a>|<a class="fa fa-times" alt="Rejected" href="'+sourceleadUrl+'/rejectVendorOnboardInvitation?onboardUUID='+report.onboardUUID+'&notificationUUID='+report.notificationUUID+'&orgSpocUUID='+report.orgSpocUUUID+'"></a>';
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
			    	  		 return result; 
			    	  	}
		    	  },{
				    	  	"targets": colIndex++,
				    	  	"data": "action",
				    	  	"sortable": false,
				    	  	"render": function(data, type, report, meta) {   
				    	  		 var result = "";
				    	  		'<sec:authorize access="hasAnyRole(\'ROLE_DELETE_CLIENT_CONTACT\')">';
						   	    result = result + '<td><div><i class="glyphicon glyphicon-trash" onClick="terminateSpoc1('+report.id+'\,\''+report.orgRelationId+'\',\''+report.spocEmail+'\')"; title="Remove client contact"></i></div></td>';
						   	 '</sec:authorize>';
						   	 
						   //	'<sec:authorize access="!hasAnyRole(\'ROLE_DELETE_CLIENT_CONTACT\')">';
							 //   result = result + '<td><div>---</div></td>';
							 //'</sec:authorize>';
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
	    $("#clientContactListPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#dataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getClientContactList();
	    	   }
	    	});
}
</script>