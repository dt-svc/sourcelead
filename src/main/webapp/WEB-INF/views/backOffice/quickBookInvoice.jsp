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
	                	 result = result +"<table id='invoicePagination1' class='display' cellspacing='0' width='100%' >"; 
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
	                     pagination1(); 
			    	     
          },
       });  
  } 
</script>




<script>
function getAllCustomers(){  
	  var sourceleadUrl ='${sourceleadUrl}';
	  $.ajax({
           type : "POST",
           url: sourceleadUrl+"/getAllCustomers?${_csrf.parameterName}=${_csrf.token}",    
            success :function(data) {
			    	     $("#requirementList").removeAttr("style");
	                	 $("#requirementList").empty();
	                	 var result = "";
	                	 result = result +"<table id='invoicePagination' class='display' cellspacing='0' width='100%' >"; 
	                     result = result +"<thead>"; 
	                     result = result +"  <tr>"; 
	                     result = result +"    <th>Id</th>"; 
	                     result = result +"    <th>Customer Name</th>"; 
	                     result = result +"    <th>Balance</th>"; 
	                     result = result +"    <th>Mobile</th>"; 
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
		                    	 result = result +"<td><div>"+opt.DisplayName+"</div></td>";
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

<script>
function pagination1(){ 
    $('#invoicePagination1').DataTable( { 
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


<script>
function check(){
	var errormsg = '${errorMessage}';
		$("#errorFileDiv").hide();
		$("#fileExist").empty();
		$("#success").empty();
		$('#fileDiv').find('p.jquery_form_error_message').remove();
}

</script>
<script language="javascript" type="text/javascript">
 function checkFile()
    {
     var fsize = $('#uploadBtn')[0].files[0].size;
     $('#browseDiv').find('p.jquery_form_error_message').remove();
     var res_field = document.upload_form.elements["uploadFile"].value;   
     var extension = res_field.substr(res_field.lastIndexOf('.') + 1).toLowerCase();
     var allowedExtensions = ['csv'];
     if (res_field.length > 0)
        {
             if (allowedExtensions.indexOf(extension) === -1) 
                {
                   $("input#uploadBtn").after("<p class='jquery_form_error_message'>");
               	   document.getElementById("uploadBtn").setAttribute('record-exist', 'yes');
               	   document.getElementById("uploadBtn").setAttribute('record-exist-errorMsg',"Invalid file Format. Only " + allowedExtensions.join(', ') + " is allowed.");
                    return false;
                }
                	
                
       }
		$('input#uploadBtn').removeAttr("class record-exist record-exist-errormsg");
		$('input#uploadBtn').attr('class','valid form-control');
		$('#browseDiv').find('p.jquery_form_error_message').remove();
		$("#abc_form").click(function() {
			if ($(this).validate(false, validationSettings)) {
			}
		});
	   
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
		 var sourceleadUrl = '${sourceleadUrl}';
		 var form = $('#abc_form')[0];
   		 var formData = new FormData(form); 
		 alert("abv"+sourceleadUrl);
		 $.ajax({
				    type : "POST",
				    data: formData,
	        	    contentType : 'application/json; charset=utf-8',
	        	    enctype: 'multipart/form-data',
	    	        processData: false,
	    	        contentType:false,
	        	    dataType : 'json',
					url : sourceleadUrl+"/storeImport?${_csrf.parameterName}=${_csrf.token}&json="+jsonData,
						success :function(data) {  
			                
			      }, 
	    });
				
			 } 
		
</script>

<section class="content-wraper">
  <section class="content-header clearfix">
    <div class="pull-left">
      <h2>Requirement List</h2>
    </div>
    <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Requirement List</div>
  </section>
  <form id="abc_form" name="abc_form"  enctype='multipart/form-data' method="post">
  <div class="timesheet_out">
    <div class="vendor_wrap">
      <div class="timesheet_head">
          	<a href="#" onClick="getAllCustomers();">getAllCustomers</a></li>
          	
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