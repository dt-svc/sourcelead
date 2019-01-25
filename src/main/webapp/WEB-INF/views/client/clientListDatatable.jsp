<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script>
	function loadClientDatatable(clientSearchResult, clientKeyword,responderLocationId, orgRelationId, orgName) {
		if (clientSearchResult == "clientSearchResult") {
			getClientList();
// 			$("#dataTableSearch").val(clientSearchResult);
		} else {
			if (clientKeyword == "clientSearch") {
				clientSideBar(responderLocationId, orgRelationId, orgName);
				getClientList();
			} else {
				getClientList();
			}
			listChange("listbyVendor");
			document.getElementById('listOfVendors').value = 'listbyVendor';
		}

	}
	
function getClientList(){
	var responderType = "Client";
	var values = [];
	$('input:checkbox[name=clinetLevel]:checked').each(function() 
    {
			values.push($(this).val());
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
	  		var url = vmsUrl+"/searchClientDetails?responderType="+responderType+"&searchValue="+searchValue+"&values="+values;
	  	    url += "&"+$($('#clientlist_form')[0]).serialize();  
	  		prepareClientList(url);
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

	function prepareClientList(url) {
		$("#clientList").removeAttr("style");
		$("#clientList").empty();
		var result = "";
		result = result + "<table id='clientListPagination' class='display' cellspacing='0' width='100%' >";
		result = result + "<thead>";
		result = result + "  <tr>";
		result = result + "    <th>Client Name</th>";
		result = result + "    <th>Website</th>";
		result = result + "    <th>Location</th>";
		result = result + "    <th>Tier</th>";
		result = result + "    <th>Created By</th>";
		result = result + "    <th>No.of Contacts</th>";
		result = result + "  </tr>";
		result = result + "</thead>";
		result = result + "<tbody>";
		result = result + " </tbody>";
		result = result + "</table>";
		$("#vendorContactList").hide();
		$("#clientList").append(result);
		clientpagination(url);
	}
	
	function clientpagination(url){
		var colIndex = 0;
		 $('#clientListPagination').DataTable( { 
	    	"processing": true,
	        "serverSide": true,
	        "searching": false,
	        "ajax": $.fn.dataTable.pipeline( {
	            url: url,
	            method: 'POST',
	            pages: 10
	        }),
	        "columns": [
	            { "data": "responderOrgName" },
	            { "data": "initiatorWebSite"},
	            { "data": "location" },
	            { "data": "responderLevel"},
	            { "data": "createdBy" },
	            { "data": "noContacts" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "responderOrgName",
		    	  	"render": function(data, type, report, meta) {
		    	  		$("#emptyClientList").val(1);
		    	  		var result = "";
		    	  		  '<sec:authorize access="hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
			   			result = result +'<td>';
			   			result = result +'<div class="proj_show" onclick="clientSideBar(\''+report.responderLocationId+'\',\''+report.orgRelationId+'\',\''+escape(report.orgName)+'\');"><span class="tbldata-dotdot-vsm" title="'+report.orgName+'"><a>'+report.orgName+'</a></span><i class="fa fa-binoculars"></i>';
			   			result = result +'</div>';
			   			result = result +'</td>';
			   		   '</sec:authorize>';
			   		
					'<sec:authorize access="!hasAnyRole(\'ROLE_VIEW_CLIENT\',\'ROLE_VIEW_EDIT_CLIENT\')">';
						result = result +'<td>';
						result = result +'<div class="proj_show" ><span class="tbldata-dotdot-vsm" title="${report.orgName}">'+report.orgName+'</span>';
						result = result +'</div>';
						result = result +'</td>';
					'</sec:authorize>';  
			              return  result;
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "initiatorWebSite",
	        	"sortable": false,
	            "render": function ( data, type, report, meta ) {
	            	    var result = "";
	            		if( report.website != null && report.website != '' && report.website != 'undefined'){
	            			result = result +'<td>';
	    		   			result = result +'<div>';
	    		   			result = result +'<span class="tbldata-dotdot-sm" title='+report.website+'>'+report.website+'</span>';
	    		   			result = result +'</div>';
	    		   			result = result +'</td>';
	    		   		}else{
	    		   			result = result +'<td><div>--</div></td>';
	    		   		}
		              return result;
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "location",
	              "render": function ( data, type, report, meta ) {
	            	  var result = "";
	              		var location = "";
	    		   		if(report.city != null && report.city != '' && report.city != 'undefined' && report.state != null && report.state != '' && report.state != 'undefined'){
	    		   			location = report.city+", "+report.state+", "+report.country;
	    		   		}else if(report.state != null && report.state != '' && report.state != 'undefined'){
	    		   			location = report.state+", "+report.country;
	    		   		}else{
	    		   			location = report.country;
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
	  	              return result;
	              }
	            }, {
	                "targets": colIndex++,
	                "data": "responderLevel",
	                "sortable": false,
	                "render": function ( data, type, report, meta ) {
	                	  var result = "";
	                		result = result +' <td><div><span class="tbldata-dotdot-vsm" title="'+report.initiatorLevel+'">'+report.initiatorLevel+'</span>';
	    	              return result;
	                }
	          },{
		    	  	"targets": colIndex++,
		    	  	"data": "createdBy",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		if(null != report.createdBy && report.createdBy  != '' && report.createdBy  != 'undefined'){
		    				result = result +' <td><div class="tbldata-dotdot">'+report.createdBy+'</div></td>';
		    		   	}else{
		    		   		result = result +'<td><div>---</div></td>';
		    		   	}
		    	  	   return result;
		    	  	}
		    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "noContacts",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		result = result +'<td><div class="tblminwidth-75">'+report.noContacts+'</div></td>';
		    	  		 return result;
		    	  	}
		    	  }],
		    	"scrollCollapse": false,
		    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
		    	"aaSorting": [[4,'desc']],
		    	"bLengthChange": true,
		    	"bDestroy": true,
		    	"ordering": true,
		    	"oLanguage": {
		     		"sSearch": "",
		     		 "sZeroRecords": "", "sEmptyTable": ""
		     		
		     }
	    } );
	    $("#clientListPagination").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#dataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getClientList();
	    	   }
	    	});
}
</script>


