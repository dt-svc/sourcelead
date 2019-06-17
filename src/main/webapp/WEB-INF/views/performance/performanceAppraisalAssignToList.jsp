<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<section class="content-header clearfix">
      <div class="pull-left">
        <h2>Appraisal Assign List</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; 
      <a href="<c:url value='${SourceLeadWeb_sourceleadUrl}/settings'/>">Settings</a> &raquo;
      <a href="${SourceLeadWeb_sourceleadUrl}/performanceManagementConfiguration">Performance Management</a> &raquo;<a onclick="performanceManagement.viewAppraisalCycle();"> Appraisal Cycle List</a> &raquo; Performance Assign List <span id = "fname"></a> </div>
</section>


<div class="content-area content-new clearfix backWhite">
         <div class="clearfix">
         <div class="positionRelative reportHead clearfix"> 
            <ul class="pull-right list-unstyled list-inline">
   			<li>
				<a class="action newAdBtn mr5"  href="javascript:void(0)" onclick="assignAssociatesToAppraisal();"> Assign </a>
			</li>
		    <li id="appraisalAssignListSearch"><input type="text" class="repSearchBar"  id='appraisalAssignDataTableSearch'/><span class="repSearchIcon" onClick="appraisalAssignTableList('${uuid}');"></span></li>
        	</ul>
         </div>
      
          
        <div class="clearfix"></div>  
          
          
          <div class="candidate_wrap_table datatable_filter positionRelative">
	          <div id="appraisalAssignLoading"></div>
	         
		    <div class="project_tabs_inn_table vendor_wrap_inn_table dataFilterNew pdtop">
		    	<div id="appraisalAssignList" class="datatable_filter"></div>
			</div>	  
		
          </div>
        
 </div>
 </div>
 
 <jsp:include page="../performance/assignAssociateToPeer.jsp" />  
 
 <script>
 function getAppraisalAssignToList(uuid){
	 $("#appraisalAssignLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	  var result = "";
	    result = result +" <input type='hidden' id='appraisalTemplateUUId' value='"+uuid+"'/>";
	 	result = result + '<table id="appraisalAssignListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result + '<th class="text-center"><input type="checkbox" id="sharedcheckbox" onclick="toggleAssignList(this);"/></th>'; 
		result = result + '<th>Associate Id</th>';
	  	result = result + '<th>Associate Name</th>';
	  	result = result + '<th>Email</th>';
	  	result = result + '<th>Role</th>';
	  	result = result + '<th>Reporting Manager</th>';
	  	result = result + '<th>Peer Reviews</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    $("#appraisalAssignList").empty();
	    $("#appraisalAssignList").append(result);
	    appraisalAssignTableList(uuid);
 }
 </script>
 
 <script>
	function appraisalAssignTableList(uuid){
		 var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
		 var searchValue = $("#appraisalAssignDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getAppraisalAssignToList?appraisalTemplateUUId="+uuid+"&searchValue="+searchValue;
		 appraisalAssignPagination(url,uuid);
		 $("#appraisalAssignLoading").removeAttr("style");
	}
</script>

<script>
function appraisalAssignPagination(url,uuid){
	
	var colIndex = 0;
    $('#appraisalAssignListTable').DataTable( { 
      "processing": true,
      "serverSide": true,
      "searching": false,
      "ajax": $.fn.dataTable.pipeline( {
    	  url: url,
          method: 'GET',
          pages: 1
        }),
        "columns": [
            { "data": "associateId" },
            { "data": "associateName"},
            { "data": "associateEmail"},
            { "data": "associateRole"},
            { "data": "reportingManager"},
            { "data": "actions"}
        ],
        "columnDefs": [ {
        	"targets": colIndex++,
    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		  var result = "";
	    	  		 var shareflag = false;
	        		 for(var e in assignAssociates) {
   	     	    	   if(!e || !assignAssociates[e] || assignAssociates[e] === "")
   	     				   continue;
   	     	    	   if(e === opt.associateId+"") {
   	     	    		    if(!$("#sharedcheckbox").is(":checked"))
   	     	    		 	checkOrUncheckParent("appraisalAssignListTable",assignAssociates, "sharedcheckbox"); 
   	     	    		    shareflag = true;
   	     	    	   	break;
   	     	    	   }
   	        		 }
	        		 if(!shareflag)
   	        			$("#sharedcheckbox").removeAttr('checked');
	        		    result = result +"<div class='tblminwidth-30 pull-left ml'>";
	        			result = result +'<div class="pull-left mt3"><td><input'+(shareflag ? " checked='checked'" : "")+' class="associatescheckbox" name="sharedcheckbox"  type="checkbox" id="'+opt.associateId+'" data-id="'+opt.associateId+'" value="'+opt.userId+'" onchange="checkAssociates(this)"/></td></div>';
	        			result = result +"</div>";
	        			return result; 
	    	  	}
	      },{
	    	  	"targets": colIndex++,
	    	  	"data": "associateId",
	    	  	"sortable": false,
	    	  	"render": function(data, type, opt, meta) {
	    	  		 var result = "";
	    	  	   	 result = result +"<td>";
                	 result = result +"<div class='tbldata-dotdot-sm' title='"+opt.associateId+"'>"+opt.associateId+"</div>";
		             return result;
	    	  	}
	      },{
	    	  "targets": colIndex++,
              "data": "associateName",
              "sortable": false,
              "render": function ( data, type, opt, meta ) {
            	     var result = "";
	    	  	   	 result = result +"<td>";
             	     result = result +"<div class='width300px'>";
             	     result = result +"<div class='tbldata-dotdot-big pull-left' title='"+opt.associateName+"'>"+opt.associateName+"</div>";
             	     result = result +"</div>";
             	     result = result +"</td>";
		             return result;
              		
              }
	      
          },{
		    	  "targets": colIndex++,
	              "data": "associateEmail",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	  var result = "";
	            	     result = result +"<td>";
	            	     result = result +"<div class=''>";
	             	     result = result +"<div class='tbldata-dotdot-me pull-left' title='"+opt.associateEmail+"'>"+opt.associateEmail+"</div>";
	             	     result = result +"</div>";
	             	     result = result +"</td>";
			             return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "associateRole",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	     var result = "";
	            	     result = result +"<td>";
	             	     result = result +"<div class='tbldata-dotdot-sm' title='"+opt.associateRole+"'>"+opt.associateRole+"</div>";
	             	     result = result +"</td>";
			             return result;
	              }
		     }, {
		    	  "targets": colIndex++,
	              "data": "reportingManager",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	     var result = "";
	            	     result = result +"<td>";
	             	     result = result +"<div class='tbldata-dotdot-sm' title='"+opt.reportingManager+"'>"+opt.reportingManager+"</div>";
	             	     result = result +"</td>";
			             return result;
	              }
		     },{
		    	  "targets": colIndex++,
	              "data": "actions",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	    var result = "";
	            	    var peersCount = 0;
	            	    if(opt.peersCount != null){
	            	    	peersCount = opt.peersCount;
	            	    }
	            	    result = result	+ '<td><div class="tblminwidth-50 ml"><a href="javascript:vois(0)" title="Peer Count" onclick="associateAssignedPeers(\''+opt.userId+'\');">'+peersCount+'</a>';
	            	    result = result	+ '<a><i class="fa fa-user-plus cursorPointer text-primary ml10" onclick="assignToPeer(\''+opt.userId+'\',\''+opt.userRoleId+'\',\''+uuid+'\');"></i></a></div></td>';
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
	    		appraisalAssignTableList(uuid);
	    	}
   	    	$('#appraisalAssignDataTableSearch').val(searchData);
    	   }
    	});
}
</script>


<script>
var assignAssociates = {};
function checkAssociates(elem){
	if($(elem).is(":checked")) {
		assignAssociates[$(elem).data("id")] = $(elem).val();
	} else{
		$("#sharedcheckbox").removeProp('checked');
		assignAssociates[$(elem).data("id")] = "";
	}
}

function toggleAssignList(source){
	  var checkboxes = document.querySelectorAll('input[class="associatescheckbox"]');
	  if($(source).is(":checked"))
		  $('input[class="associatescheckbox"]').prop('checked', 'checked');
	  else 
		  $('input[class="associatescheckbox"]').removeProp('checked');
	  
	  $('input[class="associatescheckbox"]').each(function(i) {
		  if($(this).is(":checked")) {
			  assignAssociates[$(this).data("id")] = $(this).val();
		  } else {
			  assignAssociates[$(this).data("id")] = "";
		  }
	  });
}

function checkOrUncheckParent(tableId, selectedItems, checkboxId) {
	$("#"+checkboxId).removeProp("checked");
	   var count = 0;
	   var data = $("#"+tableId).DataTable().data();
	 for(var e in selectedItems) {
	   if(!e || !selectedItems[e] || selectedItems[e] === "")
		   continue;
	   
	   for(var f in data) {
	   if(e === data[f].id+"") {
		count ++;
	   }
		}
	 }
	 if(count == data.count()) {
		$("#"+checkboxId).prop("checked", "checked");
	 }
}
</script>

<script>
function assignAssociatesToAppraisal(){
	var appraisalTemplateUUId = $('#appraisalTemplateUUId').val();
	 var associateIds = new Array();
	 for(var e in assignAssociates) {
		 if(!e || !assignAssociates[e] || assignAssociates[e] === "")
			   continue;
		 associateIds.push(assignAssociates[e]);
	   } 
	 
	 alert(associateIds);
	 
}
</script> 
 