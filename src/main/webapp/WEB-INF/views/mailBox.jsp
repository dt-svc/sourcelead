<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
 window.onload = function() {
	 getMails('Inbox');
};
</script>

<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
        <h2>Mailbox</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Mailbox</div>
    </section>
    <div class="content-dashboard clearfix">
      <section class="action-btns clearfix hidden">
        <ul class="pull-right list-unstyled list-inline">
          <li><a href="#" class="faclose"><i aria-hidden="true" class="fa fa-close"></i></a></li>
          <li><a href="#" class="collpase-div"><i aria-hidden="true" class="fa fa-angle-down"></i></a></li>
        </ul>
      </section>
      <div class="clearfix form-area form-horizontal">
        <div class="row">
		<!-- Mail view area -->
			<!-- Mail side-bar -->
			<div class="col-md-3">
           <%-- <a class="btn btn-primary btn-block mb compose" href="${sourceleadUrl}/hotlistMail">
		  <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
			Compose
		  </a>  --%>
		  
		  <div class="dropdown mb10 mailBtn">
					<button class="btn-block btn btn-primary dropdown-toggle display-block" type="button" data-toggle="dropdown"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
					Compose
						&nbsp;&nbsp;<span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
					 <sec:authorize access="hasRole('ROLE_MAIL_BOX')">
					  <li><a href="${sourceleadUrl}/composeMail">Mail</a></li>
					  </sec:authorize>
					  <sec:authorize access="hasRole('ROLE_HOTLIST_CAMPAIGN')">
					  	<li><a href="${sourceleadUrl}/hotlistMail">Hotlist Campaign</a></li>
					  </sec:authorize>
					  <!-- <li><a href="#">Requirements</a></li> -->
					</ul>
				  </div> 

          <div class="mail-box">
            <div class="mail-box-header">
              <h3>Folders</h3>
            </div>
            <div>
              <ul class="nav nav-pills nav-stacked">
                <li id="inboxActive" class=""><a href="#inbox" onclick="getMails('Inbox');"><i class="fa fa-inbox"></i> Inbox <span class="label label-primary pull-right" id="inboxUnread">${inboxUnreadMailsCount}</span>
                <li id="sentActive" class=""><a href="#sent" onclick="getMails('Sent');"><i class="fa fa-envelope-o"></i> Sent</a></li>
                <li id="starredActive" class=""><a href="#starred" onclick="getMails('Starred');"><i class="fa fa-star"></i> Starred</a></li>
                <li id="requestedActive" class=""><a href="#requested" onclick="getMails('Requested');"><i class="fa fa-file-text-o"></i> Requested <span class="label label-info pull-right" id="requestedUnread">${requestedUnreadMailsCount}</span></a></li> 
                <li id="trashActive" class=""><a href="#trash" onclick="getMails('Trash');"><i class="fa fa-trash-o"></i> Trash</a></li>
              </ul>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /. box -->
         <!--  <div class="mail-box">
            <div class="mail-box-header">
              <h3>Labels</h3>
            </div>
            <div class="box-body no-padding">
              <ul class="nav nav-pills nav-stacked">
                <li><a href="#"><i class="fa fa-circle-o text-danger"></i> Important</a></li>
                <li><a href="#"><i class="fa fa-circle-o text-warning"></i> Promotions</a></li>
                <li><a href="#"><i class="fa fa-circle-o text-primary"></i> Social</a></li>
              </ul>
            </div>
            /.box-body
          </div> -->
          <!-- /.box -->
        </div>
			<!-- mail side-bar ends -->
			<div class="col-md-9" id="inbox">
			<div id="mailLoading"></div>
			 <div class="mail-box mail-content">
			 <div class="mail-box-header">
              <h3 class="box-title" id="mailboxLabel"></h3>
              <!-- <div class="box-tools pull-right">
                <div class="has-feedback">
                  <input type="text" placeholder="Search Mail" class="form-control input-sm">
                  <span class="glyphicon glyphicon-search form-control-feedback"></span>
                </div>
              </div> -->
              <!-- /.box-tools -->
            </div>
			
			<!-- /.box-tools -->
			<div class="mailbox-controls">
			<div>
			<input id="checkAllEmails" class="checkbox-custom" onclick="toggleEmails(this);deleteMail();" name="checkAllEmails" type="checkbox">
			<label for="checkAllEmails" class="checkbox-custom-label"></label>
			</div>
				<button class="btn btn-danger mailbtn-custom" type="button" onclick="getMailBoxIds();" id="trashBtn" style="display: none;"><i class="fa fa-trash-o" id="trashtype"></i></button>
                <button class="btn btn-default mailbtn-custom" type="button" onclick="refreshList();"><i class="fa fa-refresh"></i></button>
               
               <!--  <div class="pull-right">
                  1-50/200
                  <div class="btn-group">
                    <button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-left"></i></button>
                    <button class="btn btn-default btn-sm" type="button"><i class="fa fa-chevron-right"></i></button>
                  </div>
                  /.btn-group
                </div> -->
                <!-- /.pull-right -->
              </div>
			  <!-- /.box-tools -->
			
	<!-- mail box table -->
			<div class="table-responsive mailbox-messages">
                 <div id="mailBoxList"></div>
              </div>
	<!-- mail box table ends -->   

			</div>
			</div>
			
			<div class="col-md-9" id="details">
			 <div class="mail-box mail-content">
			 <div id="mailContentLoading"></div>
			 <div class="mail-widget">
              <div id="mailDetails"></div>
              </div>
              </div>
              </div>
              

		<!-- Mail view area -->

			

			</div>
		   </div>
		</div>
  </section>

  
  <script>
  function getMails(type){
	  var sourceleadUrl ='${sourceleadUrl}';
	  
	  document.getElementById("inbox").style.display="";
	  document.getElementById("details").style.display="none";
	  document.getElementById("trashBtn").style.display="none";
	  $('#checkAllEmails').removeAttr('checked');
	  $('#mailboxLabel').html(type);
	  
	  if(type == "Trash"){
		  $('#trashtype').html(' <b>Delete For Ever</b>');
	  }else{
		  $('#trashtype').html('');
	  }
	  
	  setActiveTab(type)
	  
	  
	  $("#mailLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	  
	  $.ajax({
          type : "POST",
          url: sourceleadUrl+"/getUserMails?${_csrf.parameterName}=${_csrf.token}&type="+type,    
           success :function(data) {
           	
                if(data != null && data != 'undefined' && data != "" && data != '[]'){
              		 if( data.indexOf('Sign In') >= 0){
			    		location.replace(sourceleadUrl);
			    	 }
              		 else if (data.indexOf('exception') >= 0) {
              			 location.replace(sourceleadUrl+"/errorPage");
              		 }
              		 else{
              			 
              			var result = '';
              			result = result +'<input type="hidden" id="emailListType" value='+type+'>';
              			result = result +'<table id="mails" class="table table-hover table-striped">';
              			
                		result = result +'<thead>';
                		result = result +'<tr>';
                		result = result +'<th></th>';
                		result = result +'<th></th>';
                		result = result +'<th></th>';
                		result = result +'<th></th>';
                		result = result +'</tr>';
                		result = result +'</thead>';
                		
              			result = result +'<tbody>';
              			
              			 var json = JSON.parse(data);
		                    for (var i = 0; i < json.length; i++) { 
		                    	 var opt = json[i]; 
		                    	 
		                    	 var isFavourite = "";
		                    	 var isFavClass = "";
		                    	 var isMailRead = "";
		                    	 
	                    		 if(opt.isFavourite == 1){
				            		isFavourite = "checked";
				            		isFavClass = "activestar-rating";
				            	 }
	                    		 
	                    		 if(opt.isMailRead == 1){
	                    			 isMailRead = "mail-read"
	                    		 }else{
	                    			 isMailRead = "mail-unread"
	                    		 }
		                    	 
		                    	result = result +' <tr id="checkbox'+opt.id+'">';
		              			result = result +'<td style="width : 20px;">';
		              			result = result +'<div class="mrleft8">';
		              			result = result +'<input id="check'+opt.id+'" value="'+opt.id+'" class="checkbox-custom" onclick="deleteMail();" name="mailIds[]" type="checkbox">';
		              			result = result +'<label for="check'+opt.id+'" class="checkbox-custom-label"></label>';
		              			result = result +'</div>';
		              			result = result +'</td>';
		              			
	              			    if(opt.mailStatus != 'Trash'){
	              			    	result = result +"<td style='width : 25px;'>";
			                        result = result +"<div class='star-rating mailbox-rating positionRelative "+isFavClass+"' id='favourite"+opt.id+"'>";
			                        result = result +"<input type='checkbox' id='fav"+opt.id+"' "+isFavourite+" onclick='addFavourite("+opt.id+")'></input>";
		              			
		                        result = result +" <label></label>";
		                        result = result +"</div>";
		                        result = result +"</td>";
	              			  }
		                        
		                        if(opt.mailStatus == 'Trash'){
		                        	result = result +'<td></td>';
		              			 }
			              			
		              			
		              			result = result +'<td class="mailbox-subject '+isMailRead+'"><a href="#" onclick="getEmailDetails(\''+opt.uuid+'\',\''+type+'\')">'+opt.subject+'</a></td>';
		              			result = result +'<td class="mailbox-date">'+opt.calDays+'</td>';
		              			result = result +'</tr>';
		                    }
		                    
		                     result = result +"</tbody>";
		                     result = result +"</table>"; 
		                     result = result +'</div>'; 
              			
	                 	 $("#mailBoxList").empty(); 
	                 	 $("#mailDetails").empty(); 
	                     $("#mailBoxList").append(result); 
	                     pagination(); 
	                     $("#mailLoading").removeAttr("style"); 
			    	} 
                }else{
 	    	         
               	var result ="";
               	    result = result +'<input type="hidden" id="emailListType" value='+type+'>';
	    	        result = result +'<div class="modal-body clearfix">';
	           		result = result +'     </div>';
	           		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter">'; 
	           		result = result +'<table class="table table-hover table-striped">';
	           		result = result +'           <thead>';
	           		result = result +'           <p class="text-center text-muted"><i class="fa fa-envelope fa-3x"></i><br/><br/>No Mails In Your '+type+' </p>';
	           		result = result +'           </thead>';
	        		result = result +'           </table>';
	        		result = result +'     </div>';
	        		result = result +'     </div>';
					$("#mailBoxList").empty();
					$("#mailDetails").empty(); 
				    $("#mailBoxList").append(result); 
				    $("#mailLoading").removeAttr("style"); 
               }
         },
           
      }); 
	  
  }
  
  </script>
  
  <script>
function pagination(){
    $("#mails").DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[20, 10, 15, -1], [20, 10, 15, "All"]],
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
function setActiveTab(type){
	
	if(type == 'Inbox'){
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("requestedActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').className = 'active'
	}
	
	else if(type == 'Sent'){
		  document.getElementById("sentActive").className = 'active'
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("requestedActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Starred'){
		  document.getElementById("starredActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("requestedActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Requested'){
		  document.getElementById("requestedActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Trash'){
		  document.getElementById("trashActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("requestedActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	 
}
</script>


<script>
function addFavourite(mailboxId){
	
	var checked = document.getElementById("fav"+mailboxId).checked;
	var sourceleadUrl = '${sourceleadUrl}';
	
	
	  	$.ajax({
			type : "POST",
			url : sourceleadUrl+"/saveMailAsFavourite?${_csrf.parameterName}=${_csrf.token}&mailboxId="+mailboxId,
			success : function(result) {
				if(checked){
					 document.getElementById("favourite"+mailboxId).removeAttribute("class")
					 document.getElementById("favourite"+mailboxId).className = 'star-rating mailbox-rating positionRelative activestar-rating' 
				}else{
					 document.getElementById("favourite"+mailboxId).removeAttribute("class")
					 document.getElementById("favourite"+mailboxId).className = 'star-rating mailbox-rating positionRelative' 
				}
				
			},
		});  
		 
		
	
}
</script>



<script>
function toggleEmails(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
        
    }
$('input:checkbox:not(#checkAllEmails)').click(function() {
    if(!this.checked) {
        $('#checkAllEmails').removeAttr('checked');
    }
});
}
</script>

<script>
function deleteMail(){
	
	var mailIds = [];
	 $("input[name='mailIds[]']:checked").each( function (i) {
		 mailIds[i] = $(this).val();
	});
	 
	 if(mailIds.length > 0){
		 document.getElementById("trashBtn").style.display="";
	 }else{
		 document.getElementById("trashBtn").style.display="none";
	 }
	
}
</script>

<script>
function getMailBoxIds(){
	var sourceleadUrl = '${sourceleadUrl}';
	var mailIds = [];
	 $("input[name='mailIds[]']:checked").each( function (i) {
		 mailIds[i] = $(this).val();
		 $("#checkbox"+mailIds[i]).html("<td colspan='4'  id='loading"+mailIds[i]+"'></td>");
	     $("#loading"+mailIds[i]).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		 
	});
	 
	for (var i=0; i< mailIds.length; i++) {
		 document.getElementById("checkbox"+mailIds[i]).remove();					 
	}
	 
	
	 
		 $.ajax({
			type : "POST",
			url : sourceleadUrl+"/sendMailsToTrash?${_csrf.parameterName}=${_csrf.token}&mailIds="+mailIds,
			success : function(result) {
				
				for (var i=0; i< mailIds.length; i++) {
					 document.getElementById("checkbox"+mailIds[i]).remove();					 
				}
				 
				 
				
			},
		});   
		
		
}
</script>


<script>
function refreshList(){
	var type =  document.getElementById("emailListType").value;
	getMails(type);
}
</script>


<script>
function getEmailDetails(uuid,type){
	
	
	var sourceleadUrl = '${sourceleadUrl}';
	
	 document.getElementById("details").style.display="";
	 document.getElementById("inbox").style.display="none";
	 
	 $("#mailContentLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	 
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/getEmailDetails?${_csrf.parameterName}=${_csrf.token}&UUID="+uuid+"&type="+type,
		success : function(data) {
			
			 var json = JSON.parse(data);
             for (var i = 0; i < json.length; i++) { 
             	 var opt = json[i]; 
             	 
           	     $('#inboxUnread').html(opt.unreadInboxCount);
           	     $('#requestedUnread').html(opt.unreadRequestedCount);
           	     $('#unreadMailsCount').html(opt.totalUnReadMailsCount);
            	     
                 var result =""; 
                 
             	 
              	result = result +'<div class="box-tools pull-right">';
              	result = result +'<div class="btn-group">';
              	if(opt.previousMailUUID != null && opt.previousMailUUID != '' && opt.previousMailUUID != 'undefined'){
              		result = result +'<button class="btn btn-default btn-sm" type="button" onclick="getEmailDetails(\''+opt.previousMailUUID+'\',\''+type+'\')"><i class="fa fa-chevron-left"></i></button>';
              	}else{
              		result = result +'<button class="btn btn-default btn-sm" type="button" ><i class="fa fa-chevron-left mail-next-previous-muted"></i></button>';
              	}
              	
              	if(opt.nextMailUUID != null && opt.nextMailUUID != '' && opt.nextMailUUID != 'undefined'){
              		result = result +'<button class="btn btn-default btn-sm" type="button" onclick="getEmailDetails(\''+opt.nextMailUUID+'\',\''+type+'\')"><i class="fa fa-chevron-right"></i></button>';
              	}else{
              		result = result +'<button class="btn btn-default btn-sm" type="button" ><i class="fa fa-chevron-right mail-next-previous-muted"></i></button>';
              	}
              	
              	
              	result = result +'</div>'; 
                result = result +'</div>';
                result = result +'</div>';
			    result = result +'<div class="mailbox-read-info">';
			    result = result +'<h3>'+opt.subject+'</h3>';
			    result = result +'<h5>From: '+opt.fromMail+'' 
			    result = result +'<h5>To: '+opt.toMail+'' 
			    result = result +'<span class="mailbox-read-time pull-right">'+opt.calDays+'</span></h5>';
			    result = result +'</div>	';

	   			result = result +'<div class="mailbox-controls clearfix">';
	   			result = result +'<div class="pull-left">';
	   			result = result +'<div class="btn-group">';
	   			result = result +'<button class="btn btn-danger" type="button" title="Delete" onclick="deleteReadMail(\''+uuid+'\',\''+opt.nextMailUUID+'\',\''+type+'\');"><i class="fa fa-trash-o"></i></button>';
	   		 /* result = result +'<button class="btn btn-warning" type="button" title="Reply"><i class="fa fa-reply"></i></button>';
	   			result = result +'<button class="btn btn-info" type="button" title="Forward"><i class="fa fa-share"></i></button>'; */
	   			result = result +'</div>';
	   			result = result +'</div>';
	   			/* result = result +'<div class="pull-right">';
	   			result = result +'<button class="btn btn-default" type="button" title="Refresh"><i class="fa fa-refresh"></i></button>';
	   			result = result +'<button class="btn btn-default" type="button" title="Print"><i class="fa fa-print"></i></button>';
	   			result = result +'</div>'; */
	   			result = result +'</div>'; 
	   			  
	   			result = result +'<div class="mailbox-read-message clearfix borderbottm-none">';
	   			
	   			result = result +'<p>Dear,</p><br/>';
	   			
	   			if(opt.comments != null && opt.comments != 'undefined' && opt.comments != ""){
	   				result = result +'<p>'+opt.comments+'</p>';
	   			}else if(opt.body != null && opt.body != 'undefined' && opt.body != ""){
	   				result = result +'<p>'+opt.body+'</p><br/>';
	   			}
	   			
	   			
				if(opt.candidatesList.length > 0 ){
	   			
				result = result +'<div class="clearfix project_wrap_table mail-table  mb">';
	   			result = result +'<table>';
			  	
	   			result = result +'<thead>';
	   			result = result +'<tr>';
	   			result = result +'<th>Name</th>';
	   			result = result +'<th>Email</th>';
	   			result = result +'<th>Ph</th>';
	   			result = result +'<th>Experience</th>';
	   			result = result +'<th>Postion Type</th>';
	   			result = result +'</tr>';
	   			result = result +'<tbody>';
	   			
	   			
	   		 for (var i = 0; i < opt.candidatesList.length; i++) { 
	   			 
	   			var cand = opt.candidatesList[i]; 
	   			
	   			var mobile = '';
	   			var designation = '';
	   			
	   			if(cand.mobilePhone != null && cand.mobilePhone != 'undefined' && cand.mobilePhone != ""){
	   				mobile = cand.mobilePhone;
	   			}else{
	   				mobile = '------';
	   			}
	   			
	   			if(cand.designation != null && cand.designation != 'undefined' && cand.designation != ""){
	   				designation = cand.designation;
	   			}else{
	   				designation = '------';
	   			}
	   			
	   			result = result +'<tr>';
	   			result = result +'<td>'+cand.candidateName+'</td>';
	   			result = result +'<td>'+cand.email+'</td>';
	   			result = result +'<td>'+mobile+'</td>';
	   			result = result +'<td>'+cand.totalExp+'</td>';
	   			result = result +'<td>'+designation+'</td>';
	   			result = result +'</tr>';
	   		 } 	
	   			result = result +'</tbody>';
	   			result = result +'</thead>';
	   			result = result +'</table>';
	   			result = result +'</div>';
				}
	   			
	   			if(opt.fileName != null && opt.fileName != "" && opt.fileName != 'undefined'){
	   				result = result +'<div class="require_wrap_details_cta require_uploadfile"><b>Attachment:</b> <span>'+opt.fileName+'</span><a class="glyphicon glyphicon-download-alt" href=\"'+sourceleadUrl+'/downloadMailAttachment?UUID='+opt.uuid+'\"></a> ';
	   			    result = result +'<div class="clearfix"></div>';
	   				result = result +'</div>';
	   				result = result +'<div class="clearfix"></div>';
	   			}
	            
                
              if(opt.signature != null && opt.signature != "" && opt.signature != 'undefined'){
            	  result = result +'<p>'+opt.signature+'</p>';
              }else{
            	  result = result +'<p>Thanks,<br>'+opt.fromMail+'</p>';
              }
	   			 

  			  
            
            $("#mailBoxList").empty(); 
            $("#mailDetails").empty(); 
            $("#mailDetails").append(result);
            $("#mailContentLoading").removeAttr("style"); 
   				  

             }
			
		},
	});  
	
}

</script>

<script>
function deleteReadMail(uuid,nextMailUUID,type){
	var sourceleadUrl = '${sourceleadUrl}';
	
	 $("#mailContentLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	 
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/deleteReadMail?${_csrf.parameterName}=${_csrf.token}&UUID="+uuid+"&type="+type,
		success : function(data) {
			if(nextMailUUID != null && nextMailUUID != '' && nextMailUUID != 'undefined'){
				getEmailDetails(nextMailUUID,type);
			}else{
				getMails(type);
			}
		},
	});   
}
</script>



<jsp:include page="masterFooter.jsp" />