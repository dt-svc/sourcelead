<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script>
	function loadVendorDatatable(searchValue,universalvendorSearchList,searchType,responderLocationId,orgRelationId,orgName,vendorKeyword) {
		if(universalvendorSearchList=="universalVendorList")	 {
			var vmsUrl ='${SourceLeadWeb_vmsUrl}';
				 $.ajax({
			         type : "POST",
			          url: vmsUrl+"/getUniversalSearchVendorList?"+tokenParamName+"="+tokenValue+"&searchValue="+searchValue+"&searchType="+searchType,   
			          success :function(data) {
			        	  getVendorList();
			         },
			          
			     }); 
			  }else{
				  if(vendorKeyword == "vendorSearch" && vendorKeyword != null && vendorKeyword !="" && vendorKeyword != 'undefined'){
					  vendorSideBar(responderLocationId,orgRelationId,orgName);
				    	getVendorList();
				    }
				else{
					getVendorList();
					}
			  }

	}
	
	

function getVendorList(){
	var responderType = "Vendor";
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
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

	 if ($('#vendorList_form').validate(false, validationSettings)){ 
			var frm = $('#vendorList_form').serializeFormJSON();
			var con = JSON.stringify(frm);
			con = con.replace(/[{}]/g, ""); 
		    var jsonData = encodeURIComponent(con);
			var form = $('#vendorList_form')[0];
	  		var formData = new FormData(form); 
	  		var searchValue = $("#dataTableSearch").val();
	  		searchValue = searchValue ? searchValue : "";
	  		var url = vmsUrl+"/searchClientDetails?responderType="+responderType+"&searchValue="+searchValue+"&values="+values;
	  	    url += "&"+$($('#vendorList_form')[0]).serialize();  
	  		prepareVendorList(url);
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

	function prepareVendorList(url) {
		$("#vendorList").removeAttr("style");
		$("#vendorList").empty();
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
	   	result = result +'</tr>';
	   	result = result +'</thead>';
		$("#vendorContactList").hide();
		$("#vendorList").append(result);
		vendorpagination(url);
	}
	
	function vendorpagination(url){
		var colIndex = 0;
		 $('#vendorList').DataTable( { 
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
	            { "data": "initiatorWebSite"},
	            { "data": "initiatorCountry" },
	            { "data": "initiatorLevel"},
	            { "data": "createdBy" },
	            { "data": "releasedJobsCount" },
	            { "data": "noContacts" }
	        ],
	        "columnDefs": [ {
		    	  	"targets": colIndex++,
		    	  	"data": "orgName",
		    	  	"render": function(data, type, report, meta) {
		    	  		$("#emptyVendorList").val(1);
		    	  		var pattern = new RegExp(/["'"]/g);
				   		var orgName = (report.orgName).replace(pattern, "\\'");
		    	  		var result = "";
		    	  		var vmsUrl = '${SourceLeadWeb_vmsUrl}';
		    	  		
	    	  		'<sec:authorize access="hasAnyRole(\"ROLE_VIEW_VENDOR\",\"ROLE_VIEW_EDIT_VENDOR\")">';
			   			result = result +'<td>';
			   			if(report.vendorRelationStatus == 'Draft'){
			   				result = result +'<div class="proj_show"><a href="'+vmsUrl+'/addVendor?orgRelationUUID='+report.orgRelationUUID+'" class="pull-left"><span  class="tbldata-dotdot-me pull-left" title="'+report.orgName+'">'+report.orgName+'</span><i class="fa fa-binoculars pull-right"></a></i>';
			   			}else if(report.vendorRelationStatus == null || report.vendorRelationStatus != 'Draft' ){
			   				result = result +'<div class="proj_show" onclick="vendorSideBar(\''+report.responderLocationId+'\',\''+report.orgRelationId+'\',\''+orgName+'\',\''+report.isMSPVendor+'\');"><span class="tbldata-dotdot-me pull-left" title="'+report.orgName+'"><a>'+report.orgName+'</a></span><i class="fa fa-binoculars pull-right"></i>';
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
			              return  result;
		    	  	}
		      },{
	            "targets": colIndex++,
	            "data": "initiatorWebSite",
	        	"sortable": false,
	            "render": function ( data, type, report, meta ) {
	            	    var result = "";
	            	    
	            	    var webSite = "";
	              		if(report.website != 'null' && report.website != null && report.website != '' && report.website != 'undefined'){
	              			webSite = report.website;
	    		   		}
	            	   
	            	    if( webSite != null && webSite != '' && webSite != 'undefined'){
	    		   			result = result +'<td>';
	    		   			result = result +'<div>';
	    		   			result = result +'<span class="tbldata-dotdot-vsm" title='+webSite+'>'+webSite+'</span>';
	    		   			result = result +'</div>';
	    		   			result = result +'</td>';
	    		   		}else{
	    		   			result = result +'<td><div>--</div></td>';
	    		   		}
	            	    return  result;
	            }
	          }, {
	              "targets": colIndex++,
	              "data": "initiatorCountry",
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
	    		   			result = result +'<span class="tbldata-dotdot" title="'+location+'">'+location+'</span>';
	    		   			result = result +'</div>';
	    		   			result = result +'</td>';
	    		   		}else{
	    		   			result = result +'<td><div>--</div></td>';
	    		   		}
	  	              return result;
	              }
	            }, {
	                "targets": colIndex++,
	                "data": "initiatorLevel",
	            	"sortable": false,
	                "render": function ( data, type, report, meta ) {
	                	  var result = "";
	                	  result = result +' <td><div><span class="tbldata-dotdot_80" title="'+report.initiatorLevel+'">'+report.initiatorLevel+'</span>';
	    	              return result;
	                }
	          },{
	        	  "targets": colIndex++,
		    	  	"data": "createdBy",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
		    	  		if(null != report.createdBy && report.createdBy  != '' && report.createdBy  != 'undefined'){
							result = result +' <td><div class="tbldata-dotdot-vsm">'+report.createdBy+'</div></td>';
					   	}else{
					   		result = result +'<td><div>---</div></td>';
					   	}
		    	  	   return result;
		    	  	}
	                
	          },{
	        	  "targets": colIndex++,
	        	  "sortable": false,
	                "data": "releasedJobsCount",
	                "render": function ( data, type, report, meta ) {
	                	  var result = "";
	                	  result = result +' <td><div class="tbldata-dotdot_80">'+report.releasedJobsCount+'</div></td>';
	    	              return result;
	                } 	
		    	  },{
		    	  	"targets": colIndex++,
		    	  	"data": "noContacts",
		    	  	"render": function(data, type, report, meta) {
		    	  		var result = "";
							result = result +'<td><div class="tbldata-dotdot_80">'+report.noContacts+'</div></td>';
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
	    $("input[type=search]").hide();
	    $("#dataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	    		   getVendorList();
	    	   }
	    	});
}
</script>


