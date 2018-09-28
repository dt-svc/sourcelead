


<div class="col-md-12 mb clearfix noPad" id="contractListDiv" style="display: none;"> 

      <div class="dashNewWrap mb10" >
        	<div class="dashNewWrapHead noBor clearfix">
				<h4 class="pull-left">Contracts</h4>
        	</div>   
        	<div class="newDashPad">     
	        	<div class="clearfix project_tabs_inn_table dashboard-data filter_position"> 
	        		 <div id="contractList"></div>
	       	 	</div>
       	 	</div>
      </div>
      
    </div>
    
<script> 
$(document).ready(function(){
	contractList();
});
</script>


<script>
function contractList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  var locationId = '${SourceLeadWeb_orgLocationId}';
	  $("#contractList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 
	  var locationList ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  if(locationList.match("yyyy-MM-dd")){
		  DashboardContractDate = 'YYYY-MM-DD';
	  }if(locationList.match("dd-MM-yyyy")){
		  DashboardContractDate = 'DD-MM-YYYY'; 
	  }if(locationList.match("MM-dd-yyyy")){
		  DashboardContractDate = 'MM-DD-YYYY'; 
	  }if(locationList.match("yyyy-dd-MM")){
		  DashboardContractDate = 'YYYY-DD-MM'; 
	  }  
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getDashboardContracts?${_csrf.parameterName}=${_csrf.token}&locationId="+locationId,    
            success :function(data) {
			    	     $("#contractList").removeAttr("style");
	                	 $("#contractList").empty();
	                	 var result = "";
	                	 result = result +"<table id='contractListPagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Applicant Name</th>";
	                     result = result +"    <th>Job</th>";
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Join Date<br>(<small class='custom-small'>"+DashboardContractDate+"</small>)</th>"; 
	                     result = result +"    <th>Vendor</th>"; 
	                     result = result +"    <th>Contact</th>"; 
	                     result = result +"    <th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     for (var i = 0; i < data.length; i++) { 
	                    	 var opt = data[i];
// 	                    	 if(opt.contractUUID != null && opt.contractUUID !='' && opt.contractUUID !='undefined'){
	                    		 result = result +"<tr>";
		                    	 if(opt.candidateName !=null && opt.candidateName !='' && opt.candidateName !='undefined'){
		                    		 result = result +"<td><div class=''><span title='"+opt.candidateName+"'>"+opt.candidateName+"</span></a></div></td>";
		                    	 }
		                    	 else{
		                    		 result = result +"<td><div>---</div></td>";
		                    	 }
		                    	 if(opt.jobTitle !=null && opt.jobTitle !='' && opt.jobTitle !='undefined'){
		                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.jobTitle+"'>"+opt.jobTitle+"</span></div></td>";
		                    	 }else{
			                    	 result = result +"<td><div class='tbldata-dotdot-sm'>---</div></td>";
			                     }
		                    	 if(opt.amount != null && opt.amount != '' && opt.amount != 'undefined' && opt.paymentMode != null && opt.paymentMode != '' && opt.paymentMode != 'undefined'){
				                     result = result +"<td><div><span  title='"+opt.paymentType+"&nbsp;"+opt.amount+"/"+opt.paymentMode+"&nbsp;'>"+opt.paymentType+"&nbsp;"+opt.amount+"/"+opt.paymentMode+"</span></div></td>";
			                     }else if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined'){
				                     result = result +"<td><div>---</div></td>";
			                     }
		                    	 if(opt.dateOfJoiningStr != null && opt.dateOfJoiningStr != '' && opt.dateOfJoiningStr != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.dateOfJoiningStr+"'>"+opt.dateOfJoiningStr+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     if(opt.clientName != null && opt.clientName != '' && opt.clientName != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.clientName+"'>"+opt.clientName+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     if(opt.vendorContact != null && opt.vendorContact != '' && opt.vendorContact != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.vendorContact+"'>"+opt.vendorContact+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     result = result +"<td><div><a href='/app/addNewContract?contractUUID="+opt.contractUUID+"&orgRelationId="+opt.orgRelationId+"&spocUserCode="+opt.spocUserCode+"&type=Sow' title='Create Sow'>SOW</a>";
			                     if(opt.vendorMsaCreated != true){
			                    	 result = result +"<a href='/app/addNewContract?orgRelationId="+opt.orgRelationId+"&spocUserCode="+opt.spocUserCode+"&type=Msa' title='Create Msa'> / MSA</a>";
			                     }
			                     result = result +"</div></td>";
			                     
			                     result = result +"</tr>";
	                    	 }
// 	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#contractList").append(result); 
	                     $("#contractListDiv").css("display", "block");
	                	 contractListPagination(); 
          },
            
       }); 
  } 
</script>   

 <script>
function contractListPagination(){ 
    $('#contractListPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"bFilter": false,
    	"bPaginate":false,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script> 
