
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="popupWrap popupWrapFixed datatable_filter" id="peerModaltype" style="width: 750px;">
      <div class="popupHead"><span>Add Peers</span><i class="closePop pull-right popClose" onclick="closePeerPopup();"></i></div>
         <div id="peersListLoading"></div>
        <form:form id="competence_form" name="competence_form" method="post">
	        <div class="popupContent noPad ml mr mt">
	        
					<div id="assignPeerDiv"></div>
	        </div>
        
	        <div class="popupFoot text-right">
	           <button type="button" class="slBtn md colorBtn" id="associatePeers" onclick="savePeers();" >Save</button>
	           <button type="button" class="slBtn md popClose"  onclick="closePeerPopup();" >Cancel</button>
	        </div>
	 </form:form>
</div>


  
  <script>
  
  function closePeerPopup(){
	  $(".mask, #peerModaltype").fadeOut();
  }
  </script>
  
  <script>
  function assignToPeer(userId,userRoleId,uuid){
	  $(".mask, #peerModaltype").fadeIn();
	   
	    $("#peersListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    var result = "";
	    result = result +"<div class='pull-right'>";
	    result = result +" <input type='text' placeholder='Search' id='peersDataTableSearch' class='repSearchBar'/>";
	 	result = result +' <span class="cursorPointer faSearch" title="Search"><i class="fa fa-search" onClick="getPeersList(\''+userId+'\',\''+userRoleId+'\',\''+uuid+'\');"></i></span>';
	 	result = result +"</div>";
	 	
	 	result = result +" <div class='require_wrap_table tableFirstSecond clearfix'>";
	    result = result + '<table id="peersListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		result = result +'  <th class="text-center"><input type="checkbox" id="peerscheckbox" onclick="togglePeers(this);"/></th>'; 
		result = result + '<th>Associate Id</th>';
	  	result = result + '<th>Associate Name</th>';
	  	result = result + '<th>Email</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +"</div>";
	    $("#assignPeerDiv").empty();
	    $("#assignPeerDiv").append(result);
	    getPeersList(userId,userRoleId,uuid);
  }
  
  
  function getPeersList(userId,userRoleId,uuid){
	  
	     var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
		 var searchValue = $("#peersDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getAppraisalPeersList?appraisalTemplateUUId="+uuid+"&userId="+userId+"&userRoleId="+userRoleId+"&searchValue="+searchValue;
		 appraisalPeerPagination(url,userId,userRoleId);
		 $("#peersListLoading").removeAttr("style");
  }
  
  
  function appraisalPeerPagination(url,userId,userRoleId,uuid){
	  
	  var colIndex = 0;
	    $('#peersListTable').DataTable( { 
	      "processing": true,
	      "serverSide": true,
	      "searching": false,
	      "ajax": $.fn.dataTable.pipeline( {
	    	  url: url,
	          method: 'GET',
	          pages: 1
	        }),
	        "columns": [
	        	{ "data": "action" },
	            { "data": "associateId" },
	            { "data": "associateName"},
	            { "data": "associateEmail"}
	        ],
	        "columnDefs": [ {
	    	  	
	        	"targets": colIndex++,
	    	  	"sortable": false,
		    	  	"render": function(data, type, opt, meta) {
		    	  		   var result = "";
		    	  		   result = result +"<div class='tblminwidth-30 pull-left ml'>";
		        		   result = result +'<div class="pull-left mt3 ml5"><td><input  class="contactscheckbox" name="peerscheckbox"  type="checkbox" id="'+opt.id+'" data-id="'+opt.id+'" value="'+opt.id+'" onchange="checkPeersCandidates(this)"/></td></div>';
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
	                	 result = result +"<div class='tblminwidth-75' title='"+opt.associateId+"'>"+opt.associateId+"</div>";
			             return result;
		    	  	}
		      },{
		    	  "targets": colIndex++,
	              "data": "associateName",
	              "sortable": false,
	              "render": function ( data, type, opt, meta ) {
	            	     var result = "";
		    	  	   	 result = result +"<td>";
	             	     result = result +"<div>";
	             	     result = result +"<div class='tbldata-dotdot pull-left' title='"+opt.associateName+"'>"+opt.associateName+"</div>";
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
		             	     result = result +"<div class='tbldata-dotdot-me' title='"+opt.associateEmail+"'>"+opt.associateEmail+"</div>";
		             	     result = result +"</td>";
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
	    $("#peersListTable").wrap("<div style='overflow-x: auto; overflow-y: hidden;'></div>"); 
	    $("input[type=search]").hide();
	    $("#peersDataTableSearch").keyup(function(e) {
	    	   if(e.which === 13) {
	   		    $('#peersDataTableSearch').parent().find('p.jquery_form_error_message').remove();
	   	    	var searchData = $('#peersDataTableSearch').val();
	   	    	
	   	 	if(searchData.endsWith("-") || searchData.endsWith("\\") || searchData.endsWith("//") || searchData.endsWith("%")){
		    		$("#peersDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Enter valid Search Data</font></span>");
		    	}else{
		    		getPeersList(userId,userRoleId,uuid);
		    	}
	   	    	$('#peersDataTableSearch').val(searchData);
	    	   }
	    	});
  }
  
  </script>