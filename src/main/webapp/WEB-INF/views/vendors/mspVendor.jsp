<meta http-equiv="Pragma" content="private">
<meta http-equiv="Cache-Control" content="private">
<meta http-equiv="Expires" content="0">
<link href="/resources/css/jquery-tagit.css" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script>
function mspVendor(orgRelationId,vendorLocationId){
	        	 var result = "";
			        result = result +'  <div class="clearfix">  ';
			        result = result +'  <div class="positionRelative clearfix">  ';
			        result = result +'  <div class="col-md-12 mt">  ';
			        result = result +'  <label class="clearfix">Select Vendor </label>  ';
			        result = result +'  <div class="clearfix mt5">  ';
			        result = result +'  <select data-placeholder="Select Vendor" id="mspVendors" multiple class="chosen-select">  ';
			        result = result +'  </select>  ';
			        result = result +'  </div>  ';
			        result = result +'  <div class="clearfix"></div>  ';
			        result = result +'  </div>  ';
			        result = result +'  <div class="clearfix col-md-12 mb"> '; 
			        result = result +'  <div class="pull-right">  ';
			        result = result +'  <input value="Save" class="btn btn-primary" type="button" onclick="saveMSPVendor('+orgRelationId+','+vendorLocationId+');">  ';
			        result = result +'  <input value="Cancel" class="btn btn-default ml" type="button" onclick="getMspVendorList('+orgRelationId+')">  ';
			        result = result +'  </div>  ';
			        result = result +'  </div>  ';
			        
			        
			        result = result +'  </div>  ';
		
			        result = result +'  <div class="clearfix mt25"></div>  ';
			        result = result +'  <div class="require_wrap_table tableFirstSecond datatable_filter filter-position dataSeaNew" id="mspVendorListId">  ';
			        result = result +'  </div>  ';
			        result = result +'  </div> ';
		        	 
	        	    $("#tab-1").empty();
				    $("#tab-2").empty(); 
				    $("#tab-3").empty();  
				    $("#tab-4").empty();  
				    $("#tab-6").empty();  
				    $("#tab-7").empty(); 
				    $("#tab-8").empty(); 
				    $("#tab-9").empty(); 
				    $("#tab-10").empty(); 
				    $("#tab-11").empty(); 
				    $("#tab-11").append(result);  
		    		getMspVendorList(orgRelationId);
		    		getMspVendorTable(vendorLocationId,orgRelationId);
				   
} 
</script>
<script>
function getMspVendorList(orgRelationId){
var selectedMspVendorIds = [];
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$.ajax({
	   	type: "POST",
	    url: vmsUrl+"/getMspVendorList?&orgRelationId="+orgRelationId,	    	   
	    success: function(data) {
	    	console.log(data);
	    	if(data[0].mspVendorIds != null && data[0].mspVendorIds != "" && data[0].mspVendorIds != 'undefined'){
		    	selectedMspVendorIds = data[0].mspVendorIds.split(",");
	    	}
	    	var result = "";
	    	 for(var i=0;i<data.length;i++){
	    	       	var report = data[i];
	    	       if(selectedMspVendorIds.length > 0){
	    	       	 var selected = "";
	    	       	for(var j = 0; j < selectedMspVendorIds.length; j++){
	    	       		 if(report.id == selectedMspVendorIds[j]){
                       	 selected = "selected";
                       	 break;
                        }else{
                       	 selected = "";
                        } 
	    	       	}
			        result = result +'  <option  value='+report.id+' '+selected+'  >'+report.orgName+'</option>';  
	    	     }else{ 
			        result = result +'  <option  value='+report.id+' >'+report.orgName+'</option>';
	    	     } 
	    	 }
	    	 $("#mspVendors").append(result);
	    	 $("#mspVendors").chosen();
			 $("#mspVendors").trigger("chosen:updated");
	    }
	   }); 
}
function saveMSPVendor(orgRelationId,vendorLocationId){
	var mspVendorIds = $("#mspVendors").val();
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$.ajax({
	   	type: "POST",
	    url: vmsUrl+"/saveMSPVendor?&mspVendorIds="+mspVendorIds+"&mspOrgRelationId="+orgRelationId+"&mspVendorOrgLocationId="+vendorLocationId,	    	   
	    success: function(data) {
	    	mspVendor(orgRelationId,vendorLocationId)
	    }
	});
}

function getMspVendorTable(vendorLocationId,orgRelationId){
	var vmsUrl ='${SourceLeadWeb_vmsUrl}';
	$.ajax({
	   	type: "POST",
	    url: vmsUrl+"/selectedMspVendorList?&mspVendorOrgLocationId="+vendorLocationId+"&orgRelationId="+orgRelationId,	    	   
	    success: function(data) {
	     var result = "";
	     result = result +'  <table  id="mspVendorPagenation">  ';
	        result = result +'  <thead>  ';
	        result = result +'  <tr>  ';
	        result = result +'  <th>Vendor Name</th>  ';
	        result = result +'  <th>Location</th>  ';
	        result = result +'  <th>Number of Counts</th>  ';
// 	        result = result +'  <th>Action</th>  ';
	        result = result +'  </tr>  ';
	        result = result +'  </thead>  ';
	        result = result +'  <tbody>  ';
	        for (var i=0; i<data.length; i++)
			{
	        	var report = data[i];
	        	var location = "";
	        		if(report.city != null && report.city != '' && report.city != 'undefined'){
	        			location = report.country+", "+report.state+", "+report.city;
	        		}else if(report.state != null && report.state != '' && report.state != 'undefined'){
	        			location = report.country+", "+report.state;
	        		}else{
	        			location = report.country;
	        		}
	        		
	        	result = result +'  <tr>  ';
		        result = result +'  <td><div class="tbldata-dotdot-big">'+report.orgName+'</div></td>  ';
		        result = result +'  <td><div class="tbldata-dotdot-verybig">'+location+'</div></td> '; 
		        result = result +'  <td><div class="tblminwidth-150">'+report.noOfContatcs+'</div></td>  ';
// 		        result = result +'  <td>  ';
		      /*   result = result +'  <div class="tblminwidth-50">  ';
		        result = result +'  <span title="Delete" class="paddingLeft"><i class="fa fa-trash text-danger cursorPointer" onclick="deleteMsoVendor();"></i></span>  ';
		        result = result +'  </div>  '; */
// 		        result = result +'  </td>  ';
		        result = result +'  </tr>  ';
			}
	        
	        result = result +'  </tbody>  ';
	        result = result +'  </table>  ';
	        
	        $("#mspVendorListId").empty();
	        $("#mspVendorListId").append(result);
	        mspVendorPagenation();
	    }
	});
}
function deleteMsoVendor(){
	alert("delte MSP VENDOR")
}
</script>


 <script>
function mspVendorPagenation(){ 
    $('#mspVendorPagenation').DataTable({ 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		"sZeroRecords": "", "sEmptyTable": ""
     	}
    });
}
</script>