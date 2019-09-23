<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
     
    <div class="mb clearfix col-md-12"> 
      <div class=" dashNewWrap hdrPosition minHeight_225 mb10">
      
        	<div class="dashNewWrapHead noBor clearfix">
				<h4 class="pull-left">Candidates for Invoice</h4>
        	</div>
        
        <div class="clearfix project_tabs_inn_table dashboard-data newDashPad dataSeaNew"> 
                 <div id="candidateInvoiceId"></div>
        		 <div id="candidateInvoiceLoadingId"></div>
        </div>
      </div>
    </div>
    
<script> 
$(document).ready(function(){
	candidateInvoiceList();
});
</script>


<script>
function candidateInvoiceList(){  
	
	  var sourceleadUrl ='${sourceleadUrl}';
	  $("#candidateInvoiceLoadingId").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	 
	  var locationDate ='${SourceLeadWeb_orgLocations.dateFormat}';	
	  var finalDateFormat = getJSDateFormat(locationDate);
	  
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getCandidateInvoiceForDashboard?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
			    	     $("#candidateInvoiceLoadingId").removeAttr("style");
	                	 $("#candidateInvoiceId").empty();
	                	 var result = "";
	                	 result = result +"<table id='candiInvoicePaginationId' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Candidate Name</th>";
	                     result = result +"    <th>Job Title</th>";
	                     result = result +"    <th>Client Name</th>";
	                     result = result +"    <th>Rate</th>"; 
	                     result = result +"    <th>Join Date<br>(<small class='custom-small'>"+finalDateFormat+"</small>)</th>"; 
	                     result = result +"    <th>Invoice Date<br>(<small class='custom-small'>"+finalDateFormat+"</small>)</th>"; 
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
		                    	 if(opt.requirementName !=null && opt.requirementName !='' && opt.requirementName !='undefined'){
		                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.requirementName+"'>"+opt.requirementName+"</span></div></td>";
		                    	 }else{
			                    	 result = result +"<td><div class='tbldata-dotdot-sm'>---</div></td>";
			                     }
		                    	 if(opt.clientOrVendorName !=null && opt.clientOrVendorName !='' && opt.clientOrVendorName !='undefined'){
		                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.clientOrVendorName+"'>"+opt.clientOrVendorName+"</span></div></td>";
		                    	 }else{
			                    	 result = result +"<td><div class='tbldata-dotdot-sm'>---</div></td>";
			                     }
		                    	 if(opt.rate != null && opt.rate != '' && opt.rate != 'undefined' ){
		                    		 result = result +"<td><div class='tbldata-dotdot-sm'><span title='"+opt.rate+"'>"+opt.rate+"</span></div></td>";
			                     }else{
				                     result = result +"<td><div>---</div></td>";
			                     }
		                    	 if(opt.joinedDate != null && opt.joinedDate != '' && opt.joinedDate != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.joinedDate+"'>"+opt.joinedDate+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     if(opt.invoiceGenerateDate != null && opt.invoiceGenerateDate != '' && opt.invoiceGenerateDate != 'undefined'){
			                    	 result = result +"<td><div><span title='"+opt.invoiceGenerateDate+"'>"+opt.invoiceGenerateDate+"</span></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     if(opt.generateDate == true){
			                    	 result = result +"<td><div><a onClick='genarateInvoiceFromDashboard("+opt.orgRelationId+","+opt.submissionId+","+opt.id+");'>Generate Invoice</a></div></td>";
			                     }else{
			                    	 result = result +"<td><div>---</div></td>";
			                     }
			                     result = result +"</tr>";
	                    	 }
// 	                     }
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#candidateInvoiceId").append(result); 
	                     $("#candidateInvoiceListDiv").css("display", "block");
	                	 candiInvoicePagination(); 
          },
            
       }); 
  } 
</script>   

 <script>
function candiInvoicePagination(){ 
	$('#candiInvoicePaginationId').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[5, 15, 20, -1], [5, 15, 20, "All"]],
    	"bLengthChange": false,
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
function genarateInvoiceFromDashboard(orgRelationId,submissionId,candidateId){
	 $.ajax({
    	 type: "POST",
	     dataType : 'json',
	    	 url: sourceleadUrl+"/generateInvoiceForJobClosureDashboard?"+tokenParamName+"="+tokenValue+"&orgRelationId="+orgRelationId+"&submissionId="+submissionId+"&candidateId="+candidateId+"&invoiceType=Receivable&contractType=clientSow", 
             success: function(result) {
            	 location.reload();
        },
    });  
}
</script>
