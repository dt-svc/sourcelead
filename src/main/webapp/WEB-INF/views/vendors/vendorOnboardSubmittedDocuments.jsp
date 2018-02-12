<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>




<script>
function vendorOnboardDocumentsList(orgRelationId){
	
	  $.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getVendorOnboardDocumentByOrgRelationId?${_csrf.parameterName}=${_csrf.token}&orgRelationId="+orgRelationId, 
	        success: function(json) {
	        	var result = "";
	        	result = result +'	 <div class="row">';
	        	if(json.length > 0){
		        	for (var i = 0; i < json.length; i++) { 
             		 var report = json[i]; 
             		   
						result = result +'    <div class="col-md-2 text-center mb10">';
						result = result +'    <span class="tbldata-dotdot-sm" title="'+report.documentName+'">'+report.documentName+'</span>';
						if(report.uploadedFileUrl != null && report.uploadedFileUrl != 'undefined' && report.uploadedFileUrl != ''){
							result = result +'<a target="_blank" href=\"'+report.uploadedFileUrl+'\"><div class="uploaded_file uploaded_wizet mt5"> <i class="fa fa-file-text-o fa-4x"></i>  ';
							result = result +'<p class="small mt tbldata-dotdot-100" title="'+report.uploadedFileName+'">'+report.uploadedFileName+'</p>';
							result = result +'</div></a>';
						}else{
							result = result +'<div class="uploaded_file uploaded_wizet mt5"><i class="fa fa-times-circle text-danger fa-4x"></i><p class="small mt5">No Document Found</p>';
							result = result +'</div>';
						}
					
						result = result +'</div>';
		        	}
	        	}else{
	        		result = result +'    <div class="col-md-12 text-center mb10">  ';
	        		result = result +'<p class="dataTables_empty"></p>';
	        		result = result +'    </div> ';
	        	}
				result = result +'    </div>';
			
				    $("#tab-1").empty();
				    $("#tab-2").empty(); 
				    $("#tab-3").empty();  
				    $("#tab-4").empty();  
				    $("#tab-6").empty();  
				    $("#tab-7").empty(); 
				    $("#tab-8").empty(); 
				    $("#tab-9").empty(); 
				    $("#tab-9").append(result);  
	        },
	    });
}
</script>