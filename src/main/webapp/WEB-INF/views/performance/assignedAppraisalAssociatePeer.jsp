
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="popupWrap popupWrapFixed datatable_filter" id="peerModaltype" style="width: 750px;">
      <div class="popupHead"><span>Add Peers</span><i class="closePop pull-right popClose" onclick="closePeerPopup();"></i></div>
         <div id="peersChoicepopUp"></div>
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


  <div class="popupWrap popupWrapFixed" id="assignedPeersModaltype" style="width: 750px;">
      <div class="popupHead"><span>Peer Reviewers</span><i class="closePop pull-right popClose" onclick="closeAssignedPeerPopup();"></i></div>
      	<div class="require_wrap_table tableFirstSecond clearfix padd10">
      	  <div id="assignedPeersListLoading"></div>
          <div id="associateAssignPeerDiv"></div>
        </div>
        <div class="popupFoot text-right">
          <button type="button" class="slBtn md popClose" onclick="closeAssignedPeerPopup();"> Cancel</button>
        </div>
        
  </div>


<script>
  function closeAssignedPeerPopup(){
	  $(".mask, #assignedPeersModaltype").fadeOut();
  }
 </script>
  

  <script>
  function associateAssignedPeers(assignedUUID){
	  $(".mask, #assignedPeersModaltype").fadeIn();
	  $("#assignedPeersListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
      $.ajax({
	    	type: "GET",
	    	 url: sourceleadUrl+"/getAssociateAssignedPeersListForAppraisal?assignedUUID="+assignedUUID,
	        success: function(response) {
	        	$("#assignedPeersListLoading").removeAttr('style');
	        	if (response.response === "AWKWARD") {
	        	    location.href = "/errorPage";
	        	} else if (response.response === "SUCCESS") {
	        		 var result = "";
	        		 
	        		 result = result + ' <table>';
	        		 result = result + ' <thead>';
	        		 result = result + ' <tr>';
	        		 result = result + ' <th>Associate Id</th>';
	        		 result = result + ' <th>Associate Name</th>';
	        		 result = result + ' <th>Role</th>';  
	        		 result = result + ' <th>Email</th>';             
	        		 result = result + ' </tr>';
	        		 result = result + ' </thead>';
	        		 result = result + ' <tbody>';
	        		
	        		 if(response.data != null){
		        			for (var i=0; i<response.data.length; i++) {
		    	        		var report = response.data[i];
		    	        		
		    			        		    
				        		result = result + '<tr>';
	   			        		result = result + '<td>';
	   			        		result = result + '<div class="UserTextLeft tabImg">';
	   			        	 	result = result +'<img class="m-t-xs pull-left" onerror="predefinePeerImage(this);" src="'+report.userImgUrl+'">';
	   			        		result = result + '<div class="tbldata-dotdot-vsm">'+report.associateId+'</div>';
	   			        		result = result + '</div>';
	   			        		result = result + '</td>';
   	                  
   			        		    result = result + '<td><div class="tbldata-dotdot-vsm pull-left">'+report.associateName+'</div></td>';
   			        		    result = result + '<td><div class="tbldata-dotdot-vsm">'+report.associateRole+'</div></td>';
   			        		    result = result + '<td><div class="tbldata-dotdot-me">'+report.associateEmail+'</div></td>';
   			        		   
   			        		    result = result + '</tr>';
		    		  				
		    				}
		        		 }else{
		        			 result = result + '<tr class="odd"><td valign="top" colspan="4" class="dataTables_empty"></td></tr>';
			    		  }
		        			
		        			 result = result + '</tbody>';
			        		 result = result + '</table>';
		        			
	        			    $("#assignedPeersListLoading").removeAttr('style');
				            $("#associateAssignPeerDiv").empty();
			    			$("#associateAssignPeerDiv").append(result);
		        			  
	        		
	        		
	        	} else if (response.indexOf('Sign In') !== -1) {
	        		location.replace(sourceleadUrl);
	        	} else {
	        	    showError("Operation failed, please try again");
	        	}
	        },
	    });
	  
  }
  </script>

  
  
  
  <script>
  function closePeerPopup(){
	  peerUsers = {};
	  $(".mask, #peerModaltype").fadeOut();
  }
  </script>
  
  <script>
  function editPeer(userId,userRoleId,uuid,assignedUUID){
	  $(".mask, #peerModaltype").fadeIn();
	   
	    $("#peersListLoading").attr('style','position:absolute; width:100%; height:350px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
	    var result = "";
	    result = result +"<div class='pull-right' id='peersSearchDiv'>";
	    result = result +" <input type='hidden' id='appraisalAssignedUUId' value='"+assignedUUID+"'/>";
	    result = result +" <input type='text' placeholder='Search' id='peersDataTableSearch' class='repSearchBar'/>";
	 	result = result +' <span class="cursorPointer faSearch" title="Search"><i class="fa fa-search" onClick="getPeersList(\''+userId+'\',\''+userRoleId+'\',\''+uuid+'\',\''+assignedUUID+'\');"></i></span>';
	 	result = result +"</div>";
	 	
	 	result = result +" <div class='require_wrap_table tableFirstSecond clearfix'>";
	    result = result + '<table id="peersListTable" class="display mt10" cellspacing="0" width="100%" >';
		result = result + '<thead>';
		result = result + '<tr>';
		//result = result +'  <th class="text-center"><input type="checkbox" id="peerscheckbox" onclick="togglePeers(this);"/></th>'; 
		result = result + '<th>&nbsp;</th>';
		result = result + '<th>Associate Id</th>';
	  	result = result + '<th>Associate Name</th>';
	  	result = result + '<th>Email</th>';
		result = result + '</tr>';
		result = result + '</thead>	';
	    result = result +"</table>";
	    result = result +"</div>";
	    $("#assignPeerDiv").empty();
	    $("#assignPeerDiv").append(result);
	    getPeersList(userId,userRoleId,uuid,assignedUUID);
  }
  
  
  function getPeersList(userId,userRoleId,uuid,assignedUUID){
	  
	     var sourceleadUrl = '${SourceLeadWeb_sourceleadUrl}';
		 var searchValue = $("#peersDataTableSearch").val();
		 searchValue = searchValue ? searchValue : "";
		 var url = sourceleadUrl+"/getAppraisalAssignedPeersList?appraisalTemplateUUId="+uuid+"&userId="+userId+"&userRoleId="+userRoleId+"&appraisalAssignedUUId="+assignedUUID+"&searchValue="+searchValue;
		 appraisalPeerPagination(url,userId,userRoleId);
		 $("#peersListLoading").removeAttr("style");
  }
  
  
  function appraisalPeerPagination(url,userId,userRoleId){
	  
	  var colIndex = 0;
	    $('#peersListTable').DataTable( { 
	      "processing": true,
	      "serverSide": true,
	      "searching": false,
	      "ajax": $.fn.dataTable.pipeline( {
	    	  url: url,
	          method: 'GET',
	          pages: 2
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
		    	  		
		    	  		   var peerUsersList 
		    	  		   var assignedPeerflag = false;
		    	  		   
		    	  		  for(var e in peerUsers) {
		   	     	    	   if(!e || !peerUsers[e] || peerUsers[e] === "")
		   	     				   continue;
		   	     	    	   if(e === opt.peerUserId+"") {
		   	     	    		 	checkOrUncheckParent("peersListTable",peerUsers); 
		   	     	    		assignedPeerflag = true;
		   	     	    	   	break;
		   	     	    	   }
		   	        		 }
		    	  		 
		    	  		  if(opt.peerUsers != null){
		    	  			peerUsersList = opt.peerUsers.split(",");
		    	  			 for (var i=0; i<peerUsersList.length; i++)
								{
					        		if(peerUsersList[i] == opt.peerUserId){
					        			assignedPeerflag = true
					        			peerUsers[peerUsersList[i]] = peerUsersList[i];
					        		}
						        		
								}
		    	  		  }
		    	  		  
		    	  		   var result = "";
		    	  		   result = result +"<div class='tblminwidth-30 pull-left ml'>";
		        		   result = result +'<div class="pull-left mt3 ml5"><td><input '+(assignedPeerflag ? " checked='checked'" : "")+' class="peerscheckbox" name="peerscheckbox"  type="checkbox" id="'+opt.peerUserId+'" data-id="'+opt.peerUserId+'" value="'+opt.peerUserId+'" onchange="checkPeersCandidates(this)"/></td></div>';
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
		    	  	  	 result = result +"<div class='UserTextLeft tabImg'>";
		    	  		 result = result +"<img class='m-t-xs pull-left' onerror='predefinePeerImage(this);' src='"+opt.userImgUrl+"'>";
		    	  		 result = result +"<div class='tbldata-dotdot-vsm' title='"+opt.associateId+"'>"+opt.associateId+"</div>";
		    	  		 result = result +"</div>";
		    	  		 result = result +"</td>";
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
		    	"lengthMenu": [[5], [5]],
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
	    
	    /* $("#peersDataTableSearch").keyup(function(e) {
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
	    	}); */
  }
  
  
</script>

 <script>
$(document).keypress(
		  function(event){
		    if (event.which == '13') {
		      event.preventDefault();
		     /*  $('#peersDataTableSearch').parent().find('p.jquery_form_error_message').remove();
		      $("#peersDataTableSearch").after("<p class='jquery_form_error_message'><span><font color='red'>Click on search</font></span>"); */
		    }
		});
</script> 
  
 <script>
var peerUsers = {};
function checkPeersCandidates(elem) {
	if($(elem).is(":checked")) {
		peerUsers[$(elem).data("id")] = $(elem).val();
	} else{
		$("#peerscheckbox").removeProp('checked');
		peerUsers[$(elem).data("id")] = "";
	}
}


function togglePeers(source){
	  var checkboxes = document.querySelectorAll('input[class="peerscheckbox"]');
	  if($(source).is(":checked"))
		  $('input[class="peerscheckbox"]').prop('checked', 'checked');
	  else 
		  $('input[class="peerscheckbox"]').removeProp('checked');
	  
	  $('input[class="peerscheckbox"]').each(function(i) {
		  if($(this).is(":checked")) {
		      peerUsers[$(this).data("id")] = $(this).val();
		  } else {
			  peerUsers[$(this).data("id")] = "";
		  }
	  });
		 
}

function checkOrUncheckParent(tableId, selectedItems) {
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
}
</script>


<script>
function savePeers(){
	 
	 var appraisalAssignedUUId = $("#appraisalAssignedUUId").val();
	 
	 var peerUserIds = new Array();
	 for(var e in peerUsers) {
		 if(!e || !peerUsers[e] || peerUsers[e] === "")
			   continue;
		 peerUserIds.push(peerUsers[e]);
	   } 
	 
	 
	 if(peerUserIds.length > 0){
		 
		  $("#peersListLoading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:1px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:75px;');
			   $.ajax({
		          type : "POST",
		          url: sourceleadUrl+"/updatePeersToAssignedAppraisal?&peerUserIds="+peerUserIds+"&appraisalAssignedUUId="+appraisalAssignedUUId,    
		           success :function(data) {
		        	   if (data.response === "FAILED") {
		        		   $("#peersListLoading").removeAttr("style");
				    		showError(data.responseMessage);
				    	} else if (data.response === "AWKWARD") {
				    		location.href = "/errorPage";
				    	} else if (data.response === "SUCCESS") {
				    		$("#peersListLoading").removeAttr("style");
				    		peerUsers = {};
				    		closePeerPopup();
			       			
				    	}else if (data.indexOf('Sign In') !== -1) {
				    		location.replace(sourceleadUrl);
				    	} else {
				    		showError("Operation failed, please try again");
				    	}
		           },
		   });    
		 
		 
	 }else{
		 var result = "";
			result = result +"	<div class='comMask'></div>";
			result = result +"	   <div id='remove_user_pop' class='remove_user_pop remove_user_pop_a'>";
			result = result +"		<p>Select at least one peer</p>";
			result = result +"		<div>&nbsp;&nbsp;&nbsp;</div>";
			result = result +"		<b href='javascript:void(0)'><input id='confirm-id' type='button' value='Ok' onclick='okPopUp();'><input class='u_can_color' type='button'  value='Cancel' onClick='cancelPopUp();'></b>";
			result = result +"	</div>";
			$("#peersChoicepopUp").append(result);
			$("#remove_user_pop").slideDown(200);
			$(".comMask").fadeIn(200);
	 }
	 
}
</script>

<script>
	function predefinePeerImage(imageId){
		$(imageId).attr('src','/resources/images/userprofileImg.jpg');
	}
</script>

<script type="text/javascript">
function okPopUp(){
	$("#peersChoicepopUp").empty();
	$(".comMask").fadeOut(200);
}

function cancelPopUp(){
	$("#peersChoicepopUp").empty();
	$(".comMask").fadeOut(200);
}
</script>