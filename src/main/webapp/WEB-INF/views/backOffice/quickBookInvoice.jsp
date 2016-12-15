<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="resources/js/formvalidator.js"></script>


<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script> 


<script>
 window.onload = function() { 
 	 getInvoiceDetails();
};
</script>
 


<script>
function getInvoiceDetails(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllInvoice?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='invoicePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Date</th>"; 
	                     result = result +"    <th>Type</th>"; 
	                     result = result +"    <th>No</th>"; 
	                     result = result +"    <th>Due date</th>"; 
	                     result = result +"    <th>Balance</th>"; 
	                     result = result +"    <th>Total</th>"; 
	                     result = result +"    <th>Status</th>"; 
	                     result = result +"    <th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.QueryResponse.Invoice.length; i++) { 
	                    	 var opt = json.QueryResponse.Invoice[i]; 
	                    	 result = result +"<tr>"; 
		                     
	                    	 if(opt.MetaData != null && opt.MetaData != '' && opt.MetaData != 'undefined'){
		                    	 result = result +"<td><div>"+opt.MetaData.CreateTime+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.CustomerRef != null && opt.CustomerRef != '' && opt.CustomerRef != 'undefined'){
		                    	 result = result +"<td><div>"+opt.CustomerRef.name+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.BillEmail != null && opt.BillEmail != '' && opt.BillEmail != 'undefined'){
		                    	 result = result +"<td><div>"+opt.BillEmail.Address+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.DueDate != null && opt.DueDate != '' && opt.DueDate != 'undefined'){
		                    	 result = result +"<td><div>"+opt.DueDate+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.Balance != null && opt.Balance != '' && opt.Balance != 'undefined'){
		                    	 result = result +"<td><div>"+opt.Balance+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.TotalAmt != null && opt.TotalAmt != '' && opt.TotalAmt != 'undefined'){
		                    	 result = result +"<td><div>"+opt.TotalAmt+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                     if(opt.TotalAmt != null && opt.TotalAmt != '' && opt.TotalAmt != 'undefined'){
		                    	 result = result +"<td><div>"+opt.TotalAmt+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                     if(opt.TotalAmt != null && opt.TotalAmt != '' && opt.TotalAmt != 'undefined'){
		                    	 result = result +"<td><div>"+opt.TotalAmt+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                     
	                     } 
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#noReq").val('');
	                     $("#requirementList").append(result); 
	                     pagination(); 
			    	     
          },
       });  
  } 
</script>



<script>
function getInvoiceByInvoiceId(){  
	alert("hii");
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getInvoiceByInvoiceId?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='invoicePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Date</th>"; 
	                     result = result +"    <th>Type</th>"; 
	                     result = result +"    <th>No</th>"; 
	                     result = result +"    <th>Due date</th>"; 
	                     result = result +"    <th>Balance</th>"; 
	                     result = result +"    <th>Total</th>"; 
	                     result = result +"    <th>Status</th>"; 
	                     result = result +"    <th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.QueryResponse.Invoice.length; i++) { 
	                    	 var opt = json.QueryResponse.Invoice[i]; 
	                    	 result = result +"<tr>"; 
		                     
	                    	 if(opt.MetaData != null && opt.MetaData != '' && opt.MetaData != 'undefined'){
		                    	 result = result +"<td><div>"+opt.MetaData.CreateTime+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.CustomerRef != null && opt.CustomerRef != '' && opt.CustomerRef != 'undefined'){
		                    	 result = result +"<td><div>"+opt.CustomerRef.name+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.BillEmail != null && opt.BillEmail != '' && opt.BillEmail != 'undefined'){
		                    	 result = result +"<td><div>"+opt.BillEmail.Address+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.DueDate != null && opt.DueDate != '' && opt.DueDate != 'undefined'){
		                    	 result = result +"<td><div>"+opt.DueDate+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.Balance != null && opt.Balance != '' && opt.Balance != 'undefined'){
		                    	 result = result +"<td><div>"+opt.Balance+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.TotalAmt != null && opt.TotalAmt != '' && opt.TotalAmt != 'undefined'){
		                    	 result = result +"<td><div>"+opt.TotalAmt+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                     if(opt.TotalAmt != null && opt.TotalAmt != '' && opt.TotalAmt != 'undefined'){
		                    	 result = result +"<td><div>"+opt.TotalAmt+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                     if(opt.TotalAmt != null && opt.TotalAmt != '' && opt.TotalAmt != 'undefined'){
		                    	 result = result +"<td><div>"+opt.TotalAmt+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                     
	                     } 
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#noReq").val('');
	                     $("#requirementList").append(result); 
	                     pagination(); 
			    	     
          },
       });  
  } 
</script>


<script>
function getCustomerByCustomerId(){  
	alert("hello");
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getCustomerByCustomerId?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='invoicePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Date</th>"; 
	                     result = result +"    <th>Type</th>"; 
	                     result = result +"    <th>No</th>"; 
	                     result = result +"    <th>Due date</th>"; 
	                     result = result +"    <th>Balance</th>"; 
	                     result = result +"    <th>Total</th>"; 
	                     result = result +"    <th>Status</th>"; 
	                     result = result +"    <th>Action</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.QueryResponse.Invoice.length; i++) { 
	                    	 var opt = json.QueryResponse.Invoice[i]; 
	                    	 result = result +"<tr>"; 
		                     
	                    	 if(opt.MetaData != null && opt.MetaData != '' && opt.MetaData != 'undefined'){
		                    	 result = result +"<td><div>"+opt.MetaData.CreateTime+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.CustomerRef != null && opt.CustomerRef != '' && opt.CustomerRef != 'undefined'){
		                    	 result = result +"<td><div>"+opt.CustomerRef.name+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.BillEmail != null && opt.BillEmail != '' && opt.BillEmail != 'undefined'){
		                    	 result = result +"<td><div>"+opt.BillEmail.Address+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.DueDate != null && opt.DueDate != '' && opt.DueDate != 'undefined'){
		                    	 result = result +"<td><div>"+opt.DueDate+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.Balance != null && opt.Balance != '' && opt.Balance != 'undefined'){
		                    	 result = result +"<td><div>"+opt.Balance+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     if(opt.TotalAmt != null && opt.TotalAmt != '' && opt.TotalAmt != 'undefined'){
		                    	 result = result +"<td><div>"+opt.TotalAmt+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                     if(opt.TotalAmt != null && opt.TotalAmt != '' && opt.TotalAmt != 'undefined'){
		                    	 result = result +"<td><div>"+opt.TotalAmt+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                     if(opt.TotalAmt != null && opt.TotalAmt != '' && opt.TotalAmt != 'undefined'){
		                    	 result = result +"<td><div>"+opt.TotalAmt+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
		                     
		                     
		                     
	                     } 
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#noReq").val('');
	                     $("#requirementList").append(result); 
	                     pagination(); 
			    	     
          },
       });  
  } 
</script>



<%-- 
     @author : sarath kumar.
     this script is for prepare pagination to requirementList.
     1.usage     : used in candidateList() function.
--%>

<script>
function pagination(){ 
    $('#invoicePagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": ""
       }
    } );
}
</script>




<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement List</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Requirement List</div>
  </section>
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head">
          	<li> <a href="#" onClick="getInvoiceByInvoiceId();" >getInvoiceByInvoiceId</a></li>
          	<li> <a href="#" onClick="getCustomerByCustomerId();">getCustomerByCustomerId</a></li>
          <div class="clearfix"></div>
        </ul>
        <div class="clearfix"></div>
      </div>
      
      <!-- list requirement -->
      <div class="require_wrap_table">
          <div id="requirementList"></div>
      </div>
    </div>
     
     
    <!-- Project --> 
  </div>
</section>

 
<jsp:include page="../masterFooter.jsp" />