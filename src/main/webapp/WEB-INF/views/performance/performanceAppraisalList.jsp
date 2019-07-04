<jsp:include page="../masterHeader.jsp" />
<jsp:include page="../masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
$(document).ready(function() {
	getAppraisalList('self');
});
</script>

<section class="content-wraper">
	<section class="content-header clearfix">
		<div class="pull-left">
			<h2>Appraisal</h2>
		</div>
		<div class="pull-right brud-crum">
			<a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a>
			&raquo; Appraisal
		</div>
	</section>
	
	<div class="project_wrap">
			<div class="perfTabs">
		    	<ul class="clearfix">
		    		<li><a href="#" id="selfAppraisal" onclick="getAppraisalList('self');" class="Act">Self</a></li>
		    		<li><a href="#" id="peerAppraisal" onclick="getAppraisalList('peers');">Peers</a></li>
		    	</ul>
		    </div> 
	    
			 <div id="appraisalAssignLoading"></div>
			         <div class="clearfix">
			         <div class="positionRelative reportHead clearfix"> 
			            <ul class="pull-right list-unstyled list-inline">
					    <li id="appraisalAssignListSearch"><input type="text" class="repSearchBar"  id='appraisalAssignDataTableSearch'/><span class="repSearchIcon" id="appraisalType" ></span></li>
			        	</ul>
			         </div>
			        <div class="clearfix"></div>  
			          <div class="candidate_wrap_table datatable_filter positionRelative">
					    <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
					    	<div id="appraisalAssignList" class="datatable_filter"></div>
						</div>	  
					
			          </div>
			        
			 </div>
	</div>
	
</section>

<script>
function getAppraisalList(appraisalType){
	$("#appraisalAssignLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	$("#appraisalType").attr("onclick", "getAppraisalTableList('"+appraisalType+"')");
	    var result = "";
	 	result = result + '<table id="appraisalAssignListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		if(appraisalType == 'peers'){
			result = result + '<th>Employee</th>';
		}
	  	result = result + '<th>Appraisal Name</th>';
	  	result = result + '<th>Appraisal Period</th>';
	  	result = result + '<th>Process Period</th>';
	  	result = result + '<th>Due Date</th>';
	  	result = result + '<th>Status</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#appraisalAssignList").empty();
	    $("#appraisalAssignList").append(result);
	    $("#appraisalAssignDataTableSearch").val('');
	    getAppraisalTableList(appraisalType);
}
</script>

<script>
function getAppraisalTableList(appraisalType){
	 $("#appraisalAssignLoading").removeAttr("style");
	 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	 var searchValue = $("#appraisalAssignDataTableSearch").val();
	 searchValue = searchValue ? searchValue : "";
	 
	 if(appraisalType == 'self'){
    	 $("#peerAppraisal").removeClass("Act");
 	     $("#selfAppraisal").addClass("Act");
 	     var url = pmsUrl+"/getAssignedMyAppraisalList?searchValue="+searchValue;
 		 appraisalAssignPagination(url,appraisalType);
    }else{
    	$("#selfAppraisal").removeClass("Act");
 	    $("#peerAppraisal").addClass("Act");
 	    var url = pmsUrl+"/getAssignedPeersAppraisalList?searchValue="+searchValue;
		appraisalAssignPeersPagination(url,appraisalType);
    }
	 
	 
	
}
</script>


<script>
function appraisalAssignPagination(url,appraisalType){
	
	var colIndex = 0;
    $('#appraisalAssignListTable').DataTable( { 
      "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
    	  url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
            { "data": "name" },
            { "data": "appraisalStartDate"},
            { "data": "appraisalProcessStartDate"},
            { "data": "selfAppraisalEndDate"},
            { "data": "status"}
        ],
        "columnDefs": [ {
	    	  	"targets": colIndex++,
	    	  	"data": "name",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		
	    	  		 var pmsUrl ='${SourceLeadWeb_pmsUrl}';
	    	  		
	    	  		 var result = "";
	             	 result = result +'<td>';
	            	 result = result +"<div class='width300px'>";
	           	     result = result +'<a href="'+pmsUrl+'/selfAppraisalDetails?assignedUUID='+opt.templateUUId+'" >';
	           	     result = result +'<div class="proj_show tbldata-dotdot_140 pull-left"><span class="pull-left" title="'+opt.name+'">'+opt.name+'</span>';
	           	     result = result +'<i class="fa fa-binoculars pull-right"></i></div></a>';
	           	     result = result +"</div>";
	           	     result = result +'</td>';
             	     
             	    
	    	  		 
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "appraisalStartDate",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	     var result = "";
            	     result = result +"<td>";
            	     result = result +"<div>";
            	     result = result + opt.appraisalStartDate+" to "+opt.appraisalEndDate;
            	     result = result +"</div>";
            	     result = result +"</td>";
		             return result;
              		
              }
	      
          },{
		    	  "targets": colIndex++,
	              "data": "appraisalProcessStartDate",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	     result = result +"<td>";
	            	     result = result +"<div>";
	            	     result = result + opt.appraisalProcessStartDate+" to "+opt.appraisalProcessEndDate;
	            	     result = result +"</div>";
	            	     result = result +"</td>";
			             return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "selfAppraisalEndDate",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	     var result = "";
	            	     result = result +"<td>";
	            	     
	            	     result = result +"<div>";
	            	     result = result + opt.selfAppraisalStartDate+" to "+opt.selfAppraisalEndDate;
	            	     result = result +"</div>";
	            	     result = result +"</td>";
			             return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result	+ '<td>--</td>';
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"aaSorting": [[1,'desc']],
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#appraisalAssignListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#appraisalAssignDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#appraisalAssignDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#appraisalAssignDataTableSearch').val();
   	    	
   	 	if(searchData.endsWith("-") || searchData.endsWith("\\") || searchData.endsWith("//") || searchData.endsWith("%")){
	    		$("#appraisalAssignDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
	    	}else{
	    		getAppraisalTableList(appraisalType);
	    	}
   	    	$('#appraisalAssignDataTableSearch').val(searchData);
    	   }
    	});
}
</script>


<script>
function appraisalAssignPeersPagination(url,appraisalType){
	
	var colIndex = 0;
    $('#appraisalAssignListTable').DataTable( { 
      "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
    	  url: url,
          method: 'GET',
          pages: 2
        }),
        "columns": [
        	{ "data": "appraisalAssignedUser" },
            { "data": "name" },
            { "data": "appraisalStartDate"},
            { "data": "appraisalProcessStartDate"},
            { "data": "peerReviewStartDate"},
            { "data": "status"}
        ],
        "columnDefs": [ {
    	  	"targets": colIndex++,
    	  	"data": "appraisalAssignedUser",
    	  	"sortable": false,
    	  	"render": function(data, type, opt, meta) {
    	  		 var result = "";
    	  		 
	       	     result = result +"<td>";
	       	     result = result +"<div class='clearfix tbldata-dotdot-big'>";
	   	  	  	 result = result +"<div class='selfAppraisalImg pull-left'>";
	   	  		 result = result +"<img onerror='predefineAssociateImage(this);' src='"+opt.appraisalAssignedUserImgUrl+"'> </div>";
	   	  		 result = result +"<div class='UserTextLeft pull-left' title='"+opt.appraisalAssignedUser+"'>"+opt.appraisalAssignedUser+"</div>";
	   	  	     result = result +"</div>";
	   	  		 result = result +"</td>";
    	  		 
	             return result;
    	  	}
      },{
	    	  	"targets": colIndex++,
	    	  	"data": "name",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  		 result = result +"<td>";
             	     result = result +"<div class='tbldata-dotdot' title='"+opt.name+"'>"+opt.name+"</div>";
             	     result = result +"</td>";
	    	  		 
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "appraisalStartDate",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	     var result = "";
            	     result = result +"<td>";
            	     result = result +"<div>";
            	     result = result + opt.appraisalStartDate+" to "+opt.appraisalEndDate;
            	     result = result +"</div>";
            	     result = result +"</td>";
		             return result;
              		
              }
	      
          },{
		    	  "targets": colIndex++,
	              "data": "appraisalProcessStartDate",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	     result = result +"<td>";
	            	     result = result +"<div>";
	            	     result = result + opt.appraisalProcessStartDate+" to "+opt.appraisalProcessEndDate;
	            	     result = result +"</div>";
	            	     result = result +"</td>";
			             return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "peerReviewStartDate",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	     var result = "";
	            	     result = result +"<td>";
	            	     result = result +"<div>";
	            	     result = result + opt.peerReviewStartDate+" to "+opt.peerReviewEndDate;
	            	     result = result +"</div>";
	            	     result = result +"</td>";
			             return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    result = result	+ '<td>--</td>';
	            		return result;
	              }
		     }],
		     
	    	"scrollCollapse": false,
	    	"lengthMenu": [[10, 15, 20], [10, 15, 20]],
	    	"bLengthChange": true,
	    	"bDestroy": true,
	    	"aaSorting": [[2,'desc']],
	    	"ordering": false,
	    	"oLanguage": {
	     		"sSearch": "",
	     		 "sZeroRecords": "", "sEmptyTable": "", 
	     		 "sProcessing": "<img src='/resources/images/loading.gif' style='width: 75px; height: 75px;'/>"
	     }
    } );
    $("#appraisalAssignListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
    $("input[type=search]").hide();
    $("#appraisalAssignDataTableSearch").keyup(function(e) {
    	   if(e.which === 13) {
   		    $('#appraisalAssignDataTableSearch').parent().find('p.jquery_form_error_message').remove();
   	    	var searchData = $('#appraisalAssignDataTableSearch').val();
   	    	
   	 	if(searchData.endsWith("-") || searchData.endsWith("\\") || searchData.endsWith("//") || searchData.endsWith("%")){
	    		$("#appraisalAssignDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
	    	}else{
	    		getAppraisalTableList(appraisalType);
	    	}
   	    	$('#appraisalAssignDataTableSearch').val(searchData);
    	   }
    	});
}
</script>

<script>
	function predefineAssociateImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>