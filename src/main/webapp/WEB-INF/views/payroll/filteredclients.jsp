<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
window.onload = function () {
	loadFilteredClientList();
}
</script>
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Filtered Clients</h2>
  </div>
  <div class="success_pop" id="selector1" style="display: none;">
		<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
                           <a href="<c:url value='${appPath}/payrollConfiguration'/>">Payroll Configuration</a> &raquo; Payroll</div>
</section>


<div class="content-area content-new clearfix">
    <div class="clearfix">
     <div id="loading"></div>
     <div class="minHeight40 clearfix mt5"></div>
     	<div class="clients_wrap_table datatable_filter">
        	<div id="filteredClientListtDiv"></div>
        </div>
    </div>
</div>
</section>


<script type="text/javascript">
function loadFilteredClientList(){
	var orgLocationId = '${SourceLeadWeb_orgLocationId}';
	var userId = '${SourceLeadWeb_user.id}';
	$("#loading").attr('style','position:absolute; width:100%; height:95%; background-color:rgba(255,255,255,0.8); top:34px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$.get(sourceleadUrl+ "/getFilteredClientList?${_csrf.parameterName}=${_csrf.token}", function(data) {
		var response = data.data;
		var result = "";				        	
		result = result + '<table id="filteredClientListTable" class="display table table-bordered" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th style="vertical-align: middle;">Company Id</th>';
		result = result +'<th style="vertical-align: middle;">Company Name</th>';
		result = result +'<th style="vertical-align: middle;">Company Website</th>';
		result = result + '<th style="vertical-align: middle;">Office Country</th>';
		result = result + '<th style="vertical-align: middle;">Office State</th>';
		result = result + '<th style="vertical-align: middle;">Office City</th>';
		result = result + '<th style="vertical-align: middle;">Office MetroArea</th>';
		result = result + '<th style="vertical-align: middle;">Office Phone</th>';
		result = result + '<th style="vertical-align: middle;">Office Street</th>';
		result = result + '<th style="vertical-align: middle;">Office Website</th>';
		result = result + '<th style="vertical-align: middle;">Office Zipcode</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + '<tbody>';
		if(response !=null && response !=''){
			 for (var i=0; i<response.length; i++)
				{
	        		 var opt = response[i];
        			 
	        		 result = result + ' <tr>';
	        		 
	        		 result = result + ' <td style="vertical-align: middle;"><div class="tblminwidth-75" style="vertical-align: middle;">'+opt.companyId+'</div></td>';
	        		 
	        		 if(opt.companyName !=null && opt.companyName !='' && opt.companyName !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.companyName+'"><div class="tbldata-dotdot-100">'+opt.companyName+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.companyWebsite !=null && opt.companyWebsite !='' && opt.companyWebsite !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.companyWebsite+'"><div class="tblminwidth-150">'+opt.companyWebsite+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 if(opt.officeCity !=null && opt.officeCity !='' && opt.officeCity !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.officeCity+'"><div class="tblminwidth-150">'+opt.officeCity+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.officeCountry !=null && opt.officeCountry !='' && opt.officeCountry !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.officeCountry+'"><div class="tblminwidth-150">'+opt.officeCountry+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.officeMetroArea !=null && opt.officeMetroArea !='' && opt.officeMetroArea !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.officeMetroArea+'"><div class="tblminwidth-150">'+opt.officeMetroArea+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.officePhone !=null && opt.officePhone !='' && opt.officePhone !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.officePhone+'"><div class="tblminwidth-150">'+opt.officePhone+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.officeState !=null && opt.officeState !='' && opt.officeState !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.officeState+'"><div class="tblminwidth-150">'+opt.officeState+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.officeStreet !=null && opt.officeStreet !='' && opt.officeStreet !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.officeStreet+'"><div class="tblminwidth-150">'+opt.officeStreet+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		 if(opt.officeWebsite !=null && opt.officeWebsite !='' && opt.officeWebsite !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.officeWebsite+'"><div class="tblminwidth-150">'+opt.officeWebsite+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 if(opt.officeZip !=null && opt.officeZip !='' && opt.officeZip !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.officeZip+'"><div class="tblminwidth-150">'+opt.officeZip+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 
	        		  result = result + '  </tr>';
				}
		}
    	
		result = result + '  </tbody>';
		result = result + ' </table>';
		$("#filteredClientListtDiv").empty();
		$("#filteredClientListtDiv").append(result);
		$("#loading").removeAttr("style");
		filteredClientListTable();
		
	}); 
		
}
</script>
<script>
function filteredClientListTable(){ 
    $('#filteredClientListTable').DataTable( { 
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
    $('#filteredClientListTable').wrap("<div class='overflow_Auto width100percnt'></div>")
}

//.css({"vertical-align":"middle"});
</script>
<jsp:include page="../masterFooter.jsp" />