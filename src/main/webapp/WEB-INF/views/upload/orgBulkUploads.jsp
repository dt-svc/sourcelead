<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
window.onload = function () {
	getBulkUploadsList();
 };
</script>
<section class="content-wraper">
<div id="listLoading"></div>
        <section class="content-header clearfix">
          <div class="pull-left">
            <h2>Bulk Uploads List</h2>
          </div>
          <div class="pull-right brud-crum"><a href="${appPath}/dashboard/${SourceLeadWeb_orgLocationId}">Home</a> &raquo; <a href="${appPath}/settings">Settings</a>&raquo; Roles List </div>
        </section>

        
<div class="project_wrap">
 	  <div class="reportHead reportHeadDataTab  clearfix">
	     	 <ul class="pull-right list-unstyled list-inline">
				<li>
				</li>
	        </ul>
      </div>			
		     
	   <div class="project_wrap_table datatable_filter dataSeaTop">
	    	<table id="uploadsList" class="display" cellspacing="0" width="100%"></table>
	  </div>	   
		     
</div>
		 
  
 </section>
 
 <jsp:include page="../masterFooter.jsp" />	
 
 
 
 <script>
function getBulkUploadsList(){
	
	var sourceleadUrl ='${sourceleadUrl}';
	$("#listLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	
	 $.ajax({
		   	type: "POST",
		    url: sourceleadUrl+"/getAllOrgsUploadsList?${_csrf.parameterName}=${_csrf.token}", 	   
   		    success: function(json) {
   		     var dateFormat ='${SourceLeadWeb_orgLocations.dateFormat}';			
   		    	
   		     if(dateFormat.match("yyyy-MM-dd")){
	   			  uploadedDateStr = 'YYYY-MM-DD';
	   		  }if(dateFormat.match("dd-MM-yyyy")){
	   			uploadedDateStr = 'DD-MM-YYYY'; 
	   		  }if(dateFormat.match("MM-dd-yyyy")){
	   			uploadedDateStr = 'MM-DD-YYYY'; 
	   		  }if(dateFormat.match("yyyy-dd-MM")){
	   			uploadedDateStr = 'YYYY-DD-MM'; 
	   		  }
	        	
	        	   var result = "";
	        	   result = result + '   <div class="clearfix"></div>';
		           result = result + '   </div>';
		           result = result + '   <div class="">';
		           result = result + '     <table id="uploadsPagination" class="display" cellspacing="0" width="100%">';
		           result = result + '       <thead>';
		           result = result + '         <tr>';
		           result = result + '           <th>Org Name</th>';
		           result = result + '           <th>Date<br>(<small class="custom-small">'+uploadedDateStr+'</small>)</th>';
		           result = result + '           <th>Time</th>';
		           result = result + '           <th>Type</th>';
		           result = result + '           <th>Action</th>';
		           result = result + '         </tr>';
		           result = result + '       </thead>';
		           result = result + '       <tbody>';
	        	
	  				if(json != null && json != '' && json.length > 0){
		        	   
		        	   for(var i=0; i<json.length; i++){
		        		   var report = json[i];
		        		   var url = sourceleadUrl+"/dumpBulkImportsFailureList?&documentKey="+report.documentKey;
		        		    
		        		    result = result + '<tr id="upload'+report.documentKey+'">';
		        		    result = result + '<td><div class="tbldata-dotdot-me" title="'+report.orgName+'">'+report.orgName+'</div></td>';
		        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.date+'</div></td>';
		        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.time+'</div></td>';
		        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.uploadEntityType+'</div></td>';
		        		    result = result+'<td>';
		        		    if(report.uploadStatus == 'New'){
		        			    result = result+'<div>'; 
			        		    result = result+'<a href="#" onclick="dumpUploadedRecords(\''+report.documentKey+'\');" ><i class="fa fa-cloud-upload text-success" aria-hidden="true" alt="Upload" title="Upload"></i></a>';
			        		    result = result+'</div>';
		        		    }else{
		        		    	 result = result+'<div class="tbldata-dotdot-me">In Progress</div>';
		        		    }
		        		    result = result + '</td>';
		        		    result = result + '</tr>';
			    		}
				          
	  				}else{
		    			 result = result + '<tr class="odd"><td valign="top" colspan="5" class="dataTables_empty"></td></tr>';
		    		}
	  				
                $("#uploadsList").empty();  
                $("#listLoading").removeAttr("style");
   	    	    $("#uploadsList").append(result);
   	    	 	uploadsPagination();
   		    }
	 }); 
	
	
}	
</script> 

 <script>
function uploadsPagination(){ 
	$('#uploadsPagination').DataTable( { 
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
function dumpUploadedRecords(documentKey){

	$("#upload"+documentKey).html("<td colspan='5'  id='loading"+documentKey+"'></td>");
	$("#loading"+documentKey).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
	$.ajax({
    	type: "POST",
        url: sourceleadUrl+"/saveOrgImportedBulkUploads?documentKey="+documentKey, 
        success: function(result) {
        	if(result && result === "Success") {
        		getBulkUploadsList();
        	}
        	
        },
        error: function(e){
        }
    });

}
</script> 
 