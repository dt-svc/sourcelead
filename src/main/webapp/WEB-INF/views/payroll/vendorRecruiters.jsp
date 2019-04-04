<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
window.onload = function () {
	loadVendorRecruiters();
}
</script>
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Vendor Recruiters</h2>
  </div>
  <div class="success_pop" id="selector1" style="display: none;">
		<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo;
  <a href="<c:url value='${appPath}/payrollConfiguration'/>"> Payroll Configuration</a>&raquo; Payroll</div>
</section>


<div class="content-area content-new clearfix">
    <div class="clearfix">
     <div id="loading"></div>
     <div class="minHeight40 clearfix mt5"></div>
     	<div class="clients_wrap_table datatable_filter">
        	<div id="vendorRecruitersdiv"></div>
        </div>
    </div>
</div>
</section>


<script type="text/javascript">
function loadVendorRecruiters(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_user.id}';
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.get(sourceleadUrl+ "/getVendorRecruiter?${_csrf.parameterName}=${_csrf.token}", function(data) {
		var response = data.data;
		var result = "";				        	
		result = result + '<table id="VendorRecruitersTable" class="display table table-bordered" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th style="vertical-align: middle;">Company Name</th>';
		result = result +'<th style="vertical-align: middle;">Experience As</th>';
		result = result +'<th style="vertical-align: middle;">Experience Detail</th>';
		result = result + '<th style="vertical-align: middle;">Experience Details </th>';
		result = result + '<th style="vertical-align: middle;">Experience From </th>';
		result = result + '<th style="vertical-align: middle;">Experience Id </th>';
		result = result + '<th style="vertical-align: middle;">Experience Till Present </th>';
		result = result + '<th style="vertical-align: middle;">Experience To </th>';
		result = result + '<th style="vertical-align: middle;">User Fname </th>';
		result = result + '<th style="vertical-align: middle;">User Id </th>';
		result = result + '<th style="vertical-align: middle;">User Lname </th>';
		result = result + '<th style="vertical-align: middle;">Vendor Name </th>';

		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + '<tbody>';
		if(response !=null && response !=''){
			 for (var i=0; i<response.length; i++)
				{
	        		 var opt = response[i];
        			 
	        		 result = result + ' <tr>';
	        		 
	      		 if(opt.companyName !=null && opt.companyName !='' && opt.companyName !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.companyName+'"><div class="tbldata-dotdot-100">'+opt.companyName+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.experienceAs !=null && opt.experienceAs !='' && opt.experienceAs !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.experienceAs+'"><div class="tbldata-dotdot">'+opt.experienceAs+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 if(opt.experienceDetail !=null && opt.experienceDetail !='' && opt.experienceDetail !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.experienceDetail+'"><div class="tbldata-dotdot-verybig">'+opt.experienceDetail+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.experienceDetails !=null && opt.experienceDetails !='' && opt.experienceDetails !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.experienceDetails+'"><div class="tbldata-dotdot-100">'+opt.experienceDetails+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.experienceFrom !=null && opt.experienceFrom !='' && opt.experienceFrom !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.experienceFrom+'"><div class="tblminwidth-75">'+opt.experienceFrom+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.experienceId !=null && opt.experienceId !='' && opt.experienceId !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.experienceId+'"><div class="tblminwidth-75">'+opt.experienceId+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.experienceTillPresent !=null && opt.experienceTillPresent !='' && opt.experienceTillPresent !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.experienceTillPresent+'"><div class="tblminwidth-100">'+opt.experienceTillPresent+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.experienceTo !=null && opt.experienceTo !='' && opt.experienceTo !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.experienceTo+'"><div class="tblminwidth-75">'+opt.experienceTo+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.userFname !=null && opt.userFname !='' && opt.userFname !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.userFname+'"><div class="tbldata-dotdot-vsm">'+opt.userFname+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 if(opt.userId !=null && opt.userId !='' && opt.userId !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.userId+'"><div class="tblminwidth-75">'+opt.userId+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 if(opt.userLname !=null && opt.userLname !='' && opt.userLname !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.userLname+'"><div class="tbldata-dotdot-vsm">'+opt.userLname+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 if(opt.vendorName !=null && opt.vendorName !='' && opt.vendorName !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.vendorName+'"><div class="tbldata-dotdot-vsm">'+opt.vendorName+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		  result = result + '  </tr>';
				}
		}
    	
		result = result + '  </tbody>';
		result = result + ' </table>';
		$("#vendorRecruitersdiv").empty();
		$("#vendorRecruitersdiv").append(result);
		$("#loading").removeAttr("style");
		VendorRecruitersTable();
		
	}); 
		
}
</script>
<script>
function VendorRecruitersTable(){ 
    $('#VendorRecruitersTable').DataTable( { 
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
    $('#VendorRecruitersTable').wrap("<div class='overflow_Auto width100percnt'></div>")
}

//.css({"vertical-align":"middle"});
</script>
<jsp:include page="../masterFooter.jsp" />