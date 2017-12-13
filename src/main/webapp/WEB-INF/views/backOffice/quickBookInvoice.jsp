<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

 <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
 window.onload = function() { 
 	 getInvoiceDetails();
};
</script>
 


<script>
function getAllInvoice(){  
	  $("#requirementList").empty();
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllInvoice?"+tokenParamName+"="+tokenValue+"",    
            success :function(data) {
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<h4 class='ml'>Invoice List</h4>";
	                	 result = result +"<table id='invoicePagination1' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Date</th>"; 
	                     result = result +"    <th>Name</th>"; 
	                     result = result +"    <th>Email</th>"; 
	                     result = result +"    <th>Due date</th>"; 
	                     result = result +"    <th>Balance</th>"; 
	                     result = result +"    <th>Total Balance</th>"; 
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
		                     
	                     } 
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#noReq").val('');
	                     $("#requirementList").append(result); 
	                     pagination1(); 
			    	     
          },
       });  
  } 
</script>




<script>
function getAllCustomers(){  
	  $("#requirementList").empty();
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllCustomers?"+tokenParamName+"="+tokenValue+"",    
            success :function(data) {
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<h4 class='ml'>Customer List</h4>";
	                	 result = result +"<table id='invoicePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Id</th>"; 
	                     result = result +"    <th>Customer Name</th>"; 
	                     result = result +"    <th>Balance</th>"; 
	                     result = result +"    <th>Mobile</th>"; 
	                     result = result +"    <th>Created Date</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.QueryResponse.Customer.length; i++) {
	                    	 var opt = json.QueryResponse.Customer[i]; 
	                    	 result = result +"<tr>"; 
	                    	 
	                    	 if(opt.Id != null && opt.Id != '' && opt.Id != 'undefined'){
		                    	 result = result +"<td><div>"+opt.Id+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 if(opt.DisplayName != null && opt.DisplayName!= '' && opt.DisplayName!= 'undefined'){
		                    	 result = result +"<td><div> <a href='#' onClick='customerInvoice();' >"+opt.DisplayName+"</a></div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 if(opt.Balance != null && opt.Balance != '' && opt.Balance != 'undefined'){
		                    	 result = result +"<td><div>$"+opt.Balance+"</div></td>"; 
		                     }else{
		                    	 result = result +"<td><div>$0.0</div></td>";
		                     }
	                    	 if(opt.PrimaryPhone != null && opt.PrimaryPhone != '' && opt.PrimaryPhone != 'undefined'){
		                    	 result = result +"<td><div>"+opt.PrimaryPhone.FreeFormNumber+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 if(opt.MetaData != null && opt.MetaData != '' && opt.MetaData != 'undefined'){
		                    	 result = result +"<td><div>"+opt.MetaData.CreateTime+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 result = result +"</tr>"; 
 							
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
function customerInvoice(){  
}
</script>

<script>
function getAllVendors(){  
	  $("#requirementList").empty();
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllvendors?"+tokenParamName+"="+tokenValue+"",    
            success :function(data) {
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<h4 class='ml'>Vendors List</h4>";
	                	 result = result +"<table id='invoicePagination2' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Id</th>"; 
	                     result = result +"    <th>Vendor Name</th>"; 
	                     result = result +"    <th>Balance</th>"; 
	                     result = result +"    <th>Mobile</th>"; 
	                     result = result +"    <th>Created Date</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.QueryResponse.Vendor.length; i++) {
	                    	 var opt = json.QueryResponse.Vendor[i]; 
	                    	 result = result +"<tr>"; 
	                    	 
	                    	 if(opt.Id != null && opt.Id != '' && opt.Id != 'undefined'){
		                    	 result = result +"<td><div>"+opt.Id+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 if(opt.DisplayName != null && opt.DisplayName!= '' && opt.DisplayName!= 'undefined'){
		                    	 result = result +"<td><div> <a href='#' onClick='customerInvoice();''>"+opt.DisplayName+"</a></div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 if(opt.Balance != null && opt.Balance != '' && opt.Balance != 'undefined'){
		                    	 result = result +"<td><div>$"+opt.Balance+"</div></td>"; 
		                     }else{
		                    	 result = result +"<td><div>$0.0</div></td>";
		                     }
	                    	 if(opt.PrimaryPhone != null && opt.PrimaryPhone != '' && opt.PrimaryPhone != 'undefined'){
		                    	 result = result +"<td><div>"+opt.PrimaryPhone.FreeFormNumber+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 if(opt.MetaData != null && opt.MetaData != '' && opt.MetaData != 'undefined'){
		                    	 result = result +"<td><div>"+opt.MetaData.CreateTime+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 result = result +"</tr>"; 
 							
 						 } 
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#noReq").val('');
	                     $("#requirementList").append(result); 
	                     pagination2(); 
			    	     
          },
       });  
  } 
</script>

<script>
function getAllEmployees(){  
	  $("#requirementList").empty();
	  $("#requirementList").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllEmoloyees?"+tokenParamName+"="+tokenValue+"",    
            success :function(data) {
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<h4 class='ml'>Employee List</h4>";
	                	 result = result +"<table id='invoicePagination3' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Id</th>"; 
	                     result = result +"    <th>Employee Name</th>"; 
	                     result = result +"    <th>HiredDate</th>"; 
	                     result = result +"    <th>Mobile</th>"; 
	                     result = result +"  </tr>"; 
	                     result = result +"</thead>";
	                     result = result +"<tbody>"; 
	                     
	                     var json = JSON.parse(data);
	                     for (var i = 0; i < json.QueryResponse.Employee.length; i++) {
	                    	 var opt = json.QueryResponse.Employee[i]; 
	                    	 result = result +"<tr>"; 
	                    	 
	                    	 if(opt.Id != null && opt.Id != '' && opt.Id != 'undefined'){
		                    	 result = result +"<td><div>"+opt.Id+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 if(opt.DisplayName != null && opt.DisplayName!= '' && opt.DisplayName!= 'undefined'){
		                    	 result = result +"<td><div> <a href='#' onClick='customerInvoice();''>"+opt.DisplayName+"</a></div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 if(opt.HiredDate != null && opt.HiredDate != '' && opt.HiredDate != 'undefined'){
		                    	 result = result +"<td><div>"+opt.HiredDate+"</div></td>"; 
		                     }else{
		                    	 result = result +"<td><div>---</div></td>";
		                     }
	                    	 if(opt.PrimaryPhone != null && opt.PrimaryPhone != '' && opt.PrimaryPhone != 'undefined'){
		                    	 result = result +"<td><div>"+opt.PrimaryPhone.FreeFormNumber+"</div></td>";
		                     }else{
		                    	 result = result +"<td><div>-----</div></td>";
		                     }
	                    	 result = result +"</tr>"; 
 							
 						 } 
	                     result = result +" </tbody>";
	                     result = result +"</table>"; 
	                     $("#noReq").val('');
	                     $("#requirementList").append(result); 
	                     pagination3(); 
			    	     
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
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>

<script>
function pagination1(){ 
    $('#invoicePagination1').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>


<script>
function pagination2(){ 
    $('#invoicePagination2').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>


<script>
function pagination3(){ 
    $('#invoicePagination3').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [2, 15, 20, "All"]],
    	"bLengthChange": false,
    	"bDestroy": true,
    	"ordering": false,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    } );
}
</script>


<script>
function check(){
	var errormsg = '${errorMessage}';
		$("#errorFileDiv").hide();
		$("#fileExist").empty();
		$("#success").empty();
		$('#fileDiv').find('p.jquery_form_error_message').remove();
}

</script>

<script type="text/javascript">
	(function($) {
		$.fn.serializeFormJSON = function() {

			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name]) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};
	})(jQuery);
</script>


<script type="text/javascript">
	function storeImport(){
		 var frm = $('#abc_form').serializeFormJSON();
		 var con = JSON.stringify(frm);
		 con = con.replace(/[{}]/g, "");
	     var jsonData = encodeURIComponent(con);
		 var form = $('#abc_form')[0];
   		 var formData = new FormData(form); 
		 $.ajax({
				    type : "POST",
				    data: formData,
	        	    contentType : 'application/json; charset=utf-8',
	        	    enctype: 'multipart/form-data',
	    	        processData: false,
	    	        contentType:false,
	        	    dataType : 'json',
					url : sourceleadUrl+"/storeImport?"+tokenParamName+"="+tokenValue+"&json="+jsonData,
						success :function(data) {  
			                
			      }, 
	    });
				
			 } 
		
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>QuickBooks</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; QuickBooks List</div>
  </section>
  <form id="abc_form" name="abc_form"  enctype='multipart/form-data' method="post">
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head quickBooks-head">
          		<a href="#" onClick="getAllCustomers();">Customers</a> || 
          		
          		<a href="#" onClick="getAllInvoice();">Invoices</a> ||
          		
          		<a href="#" onClick="getAllVendors();">Vendors</a> ||
          		
          		<a href="#" onClick="getAllEmployees();">Employees</a> 
          		
          		<!-- <a href="#" onClick="getAllEmployees();">Transactions</a>  -->
          		
          		
          		<!--  <li> <a href="#" onClick="getPayroll();">payroll</a></li>-->
           <div class="clearfix"></div>
        	<div class="clearfix"></div>
      </div>
      
      <!-- list requirement -->
      <div class="require_wrap_table">
          <div id="requirementList"></div>
                          
      </div>
    </div>
     
     
    <!-- Project --> 
  </div>
  </form>
</section>

 
<jsp:include page="../masterFooter.jsp" />