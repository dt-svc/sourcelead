<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
window.onload = function () {
	getCategoriesForStaffingFiltered();
}
</script>
<section class="content-wraper">
<section class="content-header clearfix">
  <div class="pull-left">
    <h2>Staffing Filtered</h2>
  </div>
  <div class="success_pop" id="selector1" style="display: none;">
		<i class="fa fa-check-circle"></i><span id="msgDiv"></span>
  </div>
  <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Payroll</div>
</section>


<div class="content-area content-new clearfix">
    <div class="clearfix">
     <div id="loading"></div>
     <div class="minHeight40 clearfix mt5">
     	<div class="col-md-5">
     	<div class="row">
	    	<div class="col-md-10">
	     		<select class="chosen-select" id="categoryList" name="categoryList"  field-name="Category List"></select>
	     	</div>
	    	<div class="col-md-2">
	     		<button type="button" class="btn btn-primary btn-block" onClick="getStaffingFilteredList();">Run</button>
	     	</div>
	    </div>
        </div>
     </div>
      
     	<div class="clients_wrap_table datatable_filter">
     		<div class="col-md-12 mt mb" id="selectRecords">
     		<div class="clearfix dataTables_empty"></div>
     		</div>
        	<div id="staffingFilteredListDiv"></div>
        </div>
    </div>
</div>
</section>

<script>
function getCategoriesForStaffingFiltered(){
	$.get(sourceleadUrl+ "/getCategoriesForStaffingFiltered?${_csrf.parameterName}=${_csrf.token}", function(data) {
		var map = data.object;
		var result = "";
		result = result +  '<option value="">---Select Category---</option>';
		$.each(map, function(key, value) {
       		 result = result +  '<option value="'+key+'"> '+value+'</option>';
     		});
		$("#categoryList").empty();
		$("#categoryList").append(result);
		$('#categoryList').trigger("chosen:updated");
	}); 
}
</script>

<!-- Modal pop for details -->
<div class="modal" id="detailsPop" style="display:none;">
    <div class="modal-dialog modal-lg">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" onClick="closeDetailFun();">&times;</button>
          <h4 class="modal-title">Details</h4>
        </div>
        <div class="modal-body">
          
          <div class="row">
          	
          	<div class="col-md-6 mb">
	          	<label class="col-md-4">Vendor Id</label>
	          	<div class="col-md-8">
	          		<span class="text-muted" id="detailsVenId">0003333000</span>
	          	</div>
          	</div>
          	
          	<div class="col-md-6 mb">
	          	<label class="col-md-4">Vendor Name</label>
	          	<div class="col-md-8">
	          		<span class="text-muted" id="detailVenName">ssssssss </span>
	          	</div>
          	</div>
          	<div class="clearfix"></div>
          	<div class="col-md-6 mb">
	          	<label class="col-md-4">Clients</label>
	          	<div class="col-md-8">
	          		<span class="text-muted" id="detailClients"> ssssssss </span>
	          	</div>
          	</div>
          	
          	<div class="col-md-6 mb">
	          	<label class="col-md-4">HLCC</label>
	          	<div class="col-md-8">
	          		<span class="text-muted" id="detailHlcc">Pavankumar Srinivas Rajasekhar</span>
	          	</div>
          	</div>
          	
          	<div class="clearfix"></div>
          	<div class="col-md-6 mb">
	          	<label class="col-md-4">Jobs</label>
	          	<div class="col-md-8">
	          		<span class="text-muted" id="detailJobs">0003333000</span>
	          	</div>
          	</div>
          	
          	<div class="col-md-6 mb">
	          	<label class="col-md-4">Rating</label>
	          	<div class="col-md-8">
	          		<span class="text-muted" id="detailRating">Pavankumar Srinivas Rajasekhar</span>
	          	</div>
          	</div>
          	
          	<div class="clearfix"></div>
          	<div class="col-md-6 mb">
	          	<label class="col-md-4">reviews</label>
	          	<div class="col-md-8">
	          		<span class="text-muted" id="reviews">0003333000</span>
	          	</div>
          	</div>
            </div>
          
        </div>
      </div>
      
    </div>
  </div>
<!--  modal pop for details ends -->

<script>
function getStaffingFilteredList(){
	document.getElementById("selectRecords").style.display="none";
	var categoryId = $("#categoryList").val();
	$.get(sourceleadUrl+ "/getStaffingFilteredListByCategoryId?${_csrf.parameterName}=${_csrf.token}&categoryId="+categoryId, function(data) {
		var response = data.data;
		var result = "";				        	
		result = result + '<table id="staffingFilteredListTable" class="display table table-bordered" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'<th style="vertical-align: middle;">Category Name</th>';
		result = result + '<th style="vertical-align: middle;">Office Country</th>';
		result = result + '<th style="vertical-align: middle;">Office State</th>';
		result = result + '<th style="vertical-align: middle;">Office City</th>';
		result = result + '<th style="vertical-align: middle;">Office MetroArea</th>';
		result = result + '<th style="vertical-align: middle;">Office Phone</th>';
		result = result + '<th style="vertical-align: middle;">Office Street</th>';
		result = result + '<th style="vertical-align: middle;">Office Website</th>';
		result = result + '<th style="vertical-align: middle;">Office Zipcode</th>';
		result = result + '<th style="vertical-align: middle;">Vendor Id</th>';
		result = result + '<th style="vertical-align: middle;">Vendor Name</th>';
		result = result + '<th style="vertical-align: middle;">Vendor Website</th>';
		result = result + '<th style="vertical-align: middle;">Details</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
		result = result + '<tbody>';
		if(response !=null && response !=''){
			 for (var i=0; i<response.length; i++)
				{
	        		 var opt = response[i];
        			 
	        		 result = result + ' <tr>';
	        		 
// 	        		 result = result + ' <td style="vertical-align: middle;"><div class="tblminwidth-75" style="vertical-align: middle;">'+opt.categoryName+'</div></td>';
	        		 
	        		 if(opt.categoryName !=null && opt.categoryName !='' && opt.categoryName !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.categoryName+'"><div class="tbldata-dotdot-100">'+opt.categoryName+'</div></td>'; 
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
	        		 if(opt.vendorId !=null && opt.vendorId !='' && opt.vendorId !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.vendorId+'"><div class="tblminwidth-150">'+opt.vendorId+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 if(opt.vendorName !=null && opt.vendorName !='' && opt.vendorName !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.vendorName+'"><div class="tblminwidth-150">'+opt.vendorName+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 if(opt.vendorWebsite !=null && opt.vendorWebsite !='' && opt.vendorWebsite !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;" title="'+opt.vendorWebsite+'"><div class="tblminwidth-150">'+opt.vendorWebsite+'</div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;">---</td>';
	        		 }
	        		 if(opt.details !=null && opt.details !='' && opt.details !='undefined'){
	        			 result = result + '<td style="vertical-align: middle;"><div class="tblminwidth-50 text-center"><a onclick="getDetails(\''+opt.details+'\'); detailsFun();"><i class="fa fa-info-circle"></i></a></div></td>'; 
	        		 }else{
	        			 result = result + '<td style="vertical-align: middle;"><div class="tblminwidth-50 text-center cursorPointer"><i class="fa fa-info-circle" title="Details"></i></div></td>';
	        		 }
	        		 
	        		  result = result + '  </tr>';
				}
		}
    	
		result = result + '  </tbody>';
		result = result + ' </table>';
		$("#staffingFilteredListDiv").empty();
		$("#staffingFilteredListDiv").append(result);
		$("#loading").removeAttr("style");
		staffingFilteredListTablePagination();
	}); 
}
</script>
<script>
function getDetails(data){
	$("#detailsVenId").html(data.vendorId);
	$("#detailVenName").html(data.vendorName);
	$("detailClients").html(data.clients);
	$("detailHlcc").html(data.Hlcc);
	$("detailJobs").html(data.Jobs);
	$("detailRating").html(data.Rating);
	$("reviews").html(data.Reviews);
}
</script>
<script>
function detailsFun(){
	$(".mask, #detailsPop").fadeIn();
}
function closeDetailFun() {
	$(".mask, #detailsPop").fadeOut();
}
</script>
<script>
function staffingFilteredListTablePagination(){ 
    $('#staffingFilteredListTable').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
            "sSearch": "",
            "sZeroRecords": "", "sEmptyTable": ""
       }
    });
    $('#staffingFilteredListTable').wrap("<div class='overflow_Auto width100percnt'></div>")
}
</script>
<jsp:include page="../masterFooter.jsp" />