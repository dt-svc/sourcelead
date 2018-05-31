<jsp:include page="masterHeader.jsp" />
<jsp:include page="masterSideNav.jsp" />

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- <script>
 window.onload = function() {
	 getMails('Inbox');
};
</script> -->

 <script>
		$(document).ready(function() {
			 getMails('Inbox');
		});
	</script>

 <div id="loading"></div>
<section class="content-wraper">
    <section class="content-header clearfix">
      <div class="pull-left">
       <div id="loading"></div>
        <h2>Mailbox</h2>
      </div>
      <div class="pull-right brud-crum"><a href="<c:url value='${appPath}/dashboard/${SourceLeadWeb_orgLocationId}'/>">Home</a> &raquo; Mailbox</div>
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
					<c:if test="${SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_PROFESSIONAL' && SourceLeadWeb_loginUserRole != 'ROLE_CROWDSOURCING_RECRUITER'}">
					
					<c:if test="${mailType == 'mails'}">
					<button class="btn-block btn btn-primary dropdown-toggle display-block" type="button" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/composeMail'/>'"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
						Compose
						</button>
					</c:if>
					
					<c:if test="${mailType == 'hotcampaigns'}">
					<button class="btn-block btn btn-primary dropdown-toggle display-block" type="button" data-toggle="dropdown"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
						Campaigns
							&nbsp;&nbsp;<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
						  <sec:authorize access="hasRole('ROLE_HOTLIST_CAMPAIGN')">
						  	<li><a href="${sourceleadUrl}/hotlistMail">Hotlist Campaign</a></li>
						  	 </sec:authorize>
						  	<sec:authorize access="hasRole('ROLE_JOB_CAMPAIGN')">
						  	<li><a href="${sourceleadUrl}/joblistMail">Job Campaign</a></li>
						  </sec:authorize>
						</ul>
					</c:if>
					</c:if>
					
					<c:if test="${SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_PROFESSIONAL' || SourceLeadWeb_loginUserRole == 'ROLE_CROWDSOURCING_RECRUITER'}">
					<button class="btn-block btn btn-primary dropdown-toggle display-block" type="button" onClick="javascript:window.location.href = '<c:url value='${sourceleadUrl}/composeMail'/>'"> <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
						Compose
						</button>
					</c:if>
			  </div> 

		<div class="mail-box" id="loading">
            <div class="box-body no-padding newMailSet mailInboxli">
              <ul class="nav nav-pills nav-stacked">
              <c:forEach var="emailAccountList" items="${emailAccountSettingsServiceList}">
              <c:if test="${emailAccountList.emailType == 'gmail'}">
                      <li class="aa"><a href="#" id="activeClass${emailAccountList.id}" class="" onclick="activeEmail(${emailAccountList.id});"><i class="fa fa-google cGmail"></i> ${emailAccountList.email} <span></span></a>
                       <div class='mail-actionicons text-center'>
                      <%--  <b class="fa fa-pencil" onclick="editConMail(${emailAccountList.id});"></b> --%>
                       <b class="fa fa-trash" onclick="deleteConMail1(${emailAccountList.id});"></b>
                       </div>
                       </li>
                
              </c:if>
              <c:if test="${emailAccountList.emailType == 'yahoo'}">
                  <li class="aa"><a href="#" id="activeClass${emailAccountList.id}" class="" onclick="activeEmail(${emailAccountList.id});"><i class="fa fa-yahoo cYahoo"></i> ${emailAccountList.email} <span></span></a>
                  <div class='mail-actionicons text-center'>
                      <%--  <b class="fa fa-pencil" onclick="editConMail(${emailAccountList.id});"></b> --%>
                       <b class="fa fa-trash" onclick="deleteConMail1(${emailAccountList.id});"></b>
                       </div>
                  </li>
              </c:if>
             <c:if test="${emailAccountList.emailType == 'other'}">
                            <li class="aa"><a href="#" id="activeClass${emailAccountList.id}" class="" onclick="activeEmail(${emailAccountList.id});"><i class="fa fa-envelope-o cOut"></i> ${emailAccountList.email} <span></span></a>
                            <div class='mail-actionicons text-center'>
                      <%--  <b class="fa fa-pencil" onclick="editConMail(${emailAccountList.id});"></b> --%>
                       <b class="fa fa-trash" onclick="deleteConMail1(${emailAccountList.id});"></b>
                       </div>
                            </li>
             </c:if>
             
              </c:forEach>
              </ul>
            </div>
          </div>


          <div class="mail-box" id="dynamicEmailFolder" style="display:block">
            <div class="mail-box-header">
              <h3>Folders</h3>
            </div>
            <div>
              <ul class="nav nav-pills nav-stacked">
                <li id="inboxActive" class=""><a href="#inbox" onclick="getMails('Inbox');"><i class="fa fa-inbox"></i> Inbox <c:if test="${inboxUnreadMailsCount > 0}"><span class="label label-primary pull-right" id="inboxUnread">${inboxUnreadMailsCount}</span></c:if>
                <li id="sentActive" class=""><a href="#sent" onclick="getMails('Sent');"><i class="fa fa-envelope-o"></i> Sent</a></li>
                <li id="starredActive" class=""><a href="#starred" onclick="getMails('Starred');"><i class="fa fa-star"></i> Starred</a></li>
                <c:if test="${mailType == 'hotcampaigns'}">
                <li id="requestedActive" class=""><a href="#requested" onclick="getMails('Requested');"><i class="fa fa-file-text-o"></i> Requested <c:if test="${requestedUnreadMailsCount > 0}"><span class="label label-info pull-right" id="requestedUnread">${requestedUnreadMailsCount}</span></c:if></a></li> 
                </c:if>
                <li id="trashActive" class=""><a href="#trash" onclick="getMails('Trash');"><i class="fa fa-trash-o"></i> Trash</a></li>
                <c:if test="${mailType == 'mails'}">
                <li id="settingsActive" class=""><a href="#settings" onclick="getEmialSettings();"><i class="fa fa-cog"></i> Settings</a></li>
                </c:if>
                
              </ul>
            </div>
            <!-- /.box-body -->
          </div>



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
			<label for="checkAllEmails" id="checkAllBtn" class="checkbox-custom-label" style="display: none;"></label>
			</div>
				<button class="btn btn-danger mailbtn-custom" type="button" onclick="getMailBoxIds();" id="trashBtn" style="display: none;"><i class="fa fa-trash-o" id="trashtype"></i></button>
                <button class="btn btn-default mailbtn-custom" type="button" id="refreshBtn" style="display: none;" onclick="refreshList();"><i class="fa fa-refresh"></i></button>
               
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
			<div class="mailbox-messages datatable_filter mt25 mailbox-table">
                 <div id="mailBoxList"></div>
              </div>
	<!-- mail box table ends -->   

			</div>
			</div>
			
			<div class="col-md-9" id="details">
			 <div class="mail-box mail-content clearfix">
			 <div id="mailContentLoading"></div>
			 <div class="mail-widget">
              <div id="mailDetails"></div>
              </div>
              </div>
              </div>
              
             <div id="body-details" style="display: none;">
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
	  var mailType = '${mailType}';
	  
	  $("#mailLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	  
	  $.ajax({
          type : "POST",
          url: sourceleadUrl+"/getUserMailsFromELK?${_csrf.parameterName}=${_csrf.token}&type="+type+"&mailType="+mailType,    
           success :function(data) {
           	
                if(data != null && data != 'undefined' && data != "" && data != '[]'){
              		
                	 document.getElementById("checkAllBtn").style.display="";
                	 document.getElementById("refreshBtn").style.display="";
              			 
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
		                    	 
		                    	result = result +' <tr id="checkbox'+opt.mailBoxId+'">';
		              			result = result +'<td style="width : 20px;">';
		              			result = result +'<div class="mrleft8">';
		              			result = result +'<input id="check'+opt.mailBoxId+'" value="'+opt.mailBoxId+'" class="checkbox-custom" onclick="deleteMail();" name="mailIds[]" type="checkbox">';
		              			result = result +'<label for="check'+opt.mailBoxId+'" class="checkbox-custom-label"></label>';
		              			result = result +'</div>';
		              			result = result +'</td>';
		              			
	              			    if(opt.mailStatus != 'Trash'){
	              			    	result = result +"<td style='width : 25px;'>";
			                        result = result +"<div class='star-rating mailbox-rating positionRelative "+isFavClass+"' id='favourite"+opt.mailBoxId+"'>";
			                        result = result +"<input type='checkbox' id='fav"+opt.mailBoxId+"' "+isFavourite+" onclick='addFavourite("+opt.mailBoxId+")'></input>";
		              			
		                        result = result +" <label></label>";
		                        result = result +"</div>";
		                        result = result +"</td>";
	              			  }
		                        
		                        if(opt.mailStatus == 'Trash'){
		                        	result = result +'<td></td>';
		              			 }
			              			
		              			
		              			result = result +'<td class="mailbox-subject '+isMailRead+'"><a href="#" onclick="getEmailDetails(\''+opt.uuid+'\',\''+type+'\',\''+mailType+'\')">'+opt.subject+'</a></td>';
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
                }else{
 	    	         
               	var result ="";
               	    result = result +'<input type="hidden" id="emailListType" value='+type+'>';
	    	        result = result +'<div class="modal-body clearfix">';
	           		result = result +'     </div>';
	           		result = result +'     <div class="modal-bodyCustom mt clearfix popupFilter">'; 
	           		result = result +'<table class="table table-hover table-striped">';
	           		result = result +'           <thead>';
	           		result = result +'           <p class="text-center text-muted"><i class="noMailImg"></i><br/>No mails in your '+type+' </p>';
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
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>

<script>
function setActiveTab(type){
	
	var mailType = '${mailType}';
	
	if(type == 'Inbox'){
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")
		  }
		  document.getElementById("trashActive").removeAttribute("class")
		  if(mailType == 'mails'){
		  	document.getElementById("settingsActive").removeAttribute("class")
		   }
		  document.getElementById('inboxActive').className = 'active'
	}
	
	else if(type == 'Sent'){
		  document.getElementById("sentActive").className = 'active'
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class") 
		  }
		  document.getElementById("trashActive").removeAttribute("class")
		  if(mailType == 'mails'){
			  document.getElementById("settingsActive").removeAttribute("class")
		  }
		  
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Starred'){
		  document.getElementById("starredActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")  
		  }
		  document.getElementById("trashActive").removeAttribute("class")
		  if(mailType == 'mails'){
			  document.getElementById("settingsActive").removeAttribute("class")
		  }
		  
		  document.getElementById('inboxActive').removeAttribute("class")
	}
	
	else if(type == 'Requested'){
		 if(mailType == 'hotcampaigns'){
			 document.getElementById("requestedActive").className = 'active' 
		 }
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById('inboxActive').removeAttribute("class")
		  if(mailType == 'mails'){
			  document.getElementById("settingsActive").removeAttribute("class")
		  }
		   
	}
	
	else if(type == 'Trash'){
		  document.getElementById("trashActive").className = 'active'
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")
		  }
		  document.getElementById('inboxActive').removeAttribute("class")
		   if(mailType == 'mails'){
			   document.getElementById("settingsActive").removeAttribute("class")
		   }
		  
	}
	else if(type == 'Settings'){
		  document.getElementById("settingsActive").className = 'active'
		  document.getElementById("trashActive").removeAttribute("class")
		  document.getElementById("sentActive").removeAttribute("class")
		  document.getElementById("starredActive").removeAttribute("class")
		  if(mailType == 'hotcampaigns'){
			  document.getElementById("requestedActive").removeAttribute("class")
		  }
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
					 document.getElementById("favourite"+mailboxId).removeAttribute("class");
					 document.getElementById("favourite"+mailboxId).className = 'star-rating mailbox-rating positionRelative activestar-rating' 
				}else{
					 document.getElementById("favourite"+mailboxId).removeAttribute("class");
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
	     $("#loading"+mailIds[i]).attr('style',' height:40px; background-color:rgba(255,255,255,0.8); top:0px; left:0px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat;');
		 
	});
	 
	for (var i=0; i< mailIds.length; i++) {
		 document.getElementById("checkbox"+mailIds[i]).remove();					 
	}
	 
	
	 $("#mailLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
		 $.ajax({
			type : "POST",
			url : sourceleadUrl+"/sendMailsToTrash?${_csrf.parameterName}=${_csrf.token}&mailIds="+mailIds,
			success : function(data) {
				
				var json = JSON.parse(data);
				
				 $('#inboxUnread').html(json['unreadInboxCount']);
         	     $('#requestedUnread').html(json['unreadRequestedCount']);
         	     
         	     $("#mailLoading").removeAttr("style");
				
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
	 
	 $("#mailContentLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	 var mailType = '${mailType}';
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/getEmailDetails?${_csrf.parameterName}=${_csrf.token}&UUID="+uuid+"&type="+type+"&mailType="+mailType,
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
			    result = result +'<h3 class="col-md-4 padding_left">'+opt.subject+'</h3>';
// 			    result = result +' <a href="<c:url value='/campaignStats'/>" class="pull-left btn btn-sm btn-primary">Stats</a>';
			    result = result +'<div class="clearfix"></div>';
			    result = result +'<h5>From: '+opt.fromMail+'' 
			    result = result +'<h5 class="mail-height">To: '+opt.toMail+'' 
			    result = result +'<span class="mailbox-read-time pull-right">'+opt.calDays+'</span></h5>';
			    result = result +'</div>	';

	   			result = result +'<div class="mailbox-controls clearfix">';
	   			result = result +'<div class="pull-left">';
	   			result = result +'<div class="btn-group">';
	   			result = result +'<button class="btn btn-danger" type="button" title="Delete" onclick="deleteReadMail(\''+uuid+'\',\''+opt.nextMailUUID+'\',\''+type+'\');"><i class="fa fa-trash-o"></i></button>';
	   		    result = result +'<button class="btn btn-warning" type="button" title="Reply" onclick="replayToMail(\''+uuid+'\');"><i class="fa fa-reply"></i></button>';
	   		    if(type == 'Sent' && opt.candidateId != null){
	   		    	result = result +'<button class="btn btn-info" type="button" title="Forward" onclick="forwardEmailToHotMail(\''+uuid+'\');"><i class="fa fa-share"></i></button>';
	   		    }else{
	   		    	result = result +'<button class="btn btn-info" type="button" title="Forward" onclick="forwardEmail(\''+uuid+'\');"><i class="fa fa-share"></i></button>';
	   		    }
	   		     
	   			result = result +'</div>';
	   			result = result +'</div>';
	   			result = result +'</div>'; 
	   			  
	   			result = result +'<div class="mailbox-read-message clearfix borderbottm-none wordWrap-breakword">';
	   			
	   		 if(opt.salutation != null && opt.salutation != 'undefined' && opt.salutation != ""){
	   			result = result +'<p>'+opt.salutation+',</p>';
	   		 }
	   			
	   			 if(opt.comments != null && opt.comments != 'undefined' && opt.comments != ""){
	   				result = result +'<p>'+opt.comments+'</p>';
	   			}
	   			
	   			if(opt.body != null && opt.body != 'undefined' && opt.body != "" && opt.mailType == '1'){
	   				result = result +'<p>'+opt.body+'</p><br/>';
	   			}  
	   			if(opt.campaignTableData != null && opt.campaignTableData != 'undefined' && opt.campaignTableData != ""){
	   				result = result +'<p>'+opt.campaignTableData+'</p><br/>';
	   			}
	   			
	   			if(opt.fileName != null && opt.fileName != "" && opt.fileName != 'undefined'){
	   				result = result +'<div class="require_wrap_details_cta require_uploadfile"><b>Attachment:</b> <span>'+opt.fileName+'</span><a download class="glyphicon glyphicon-download-alt" href=\"'+opt.fileUrl+'\"></a> ';
	   			    result = result +'<div class="clearfix"></div>';
	   				result = result +'</div>';
	   				result = result +'<div class="clearfix"></div>';
	   			}
	            
              
	   		if(opt.mailType == '1'){
	   			if(opt.signature != null && opt.signature != "" && opt.signature != 'undefined'){
	            	  result = result +'<p>'+opt.signature+'</p>';
	              }else{
	            	  result = result +'<p>Thanks,<br>'+opt.fromMail+'</p>';
	              } 
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
function getEmialSettings(){
	setActiveTab('Settings');
	
	 var result ="";
	 
	 result = result +'<div class="mail-box mail-content">';
	 result = result +'<div class="mail-box-header"><h3 class="box-title">Mail account setting</h3></div>';
	 result = result +'<div class="newMailCon">';
	 result = result +'<h4>Setup email account for sending</h4>';
	 result = result +'<p>Please select your provider</p>';
	 result = result +'<ul>';
	 result = result +'  <li>';
	 result = result +'<a href="<c:url value="/app/emailSettings"/>">';
	 result = result +'  <img src="/resources/images/gmail-logo.jpg" alt=""/>Gmail</a>';
	 result = result +'  </li>';
	 result = result +' <li>';
	 result = result +'	  <a href="<c:url value="/app/emailSettings"/>">';
	 result = result +' <img src="/resources/images/yahoo-logo.jpg" alt=""/>Yahoo</a>';
	 result = result +' </li>';
	 result = result +' <li>';
	 result = result +' <a href="<c:url value="/app/emailSettings"/>">';
	 result = result +' <img src="/resources/images/mail-icon.jpg" alt="" />Other</a>';
	 result = result +'</li>	';				  					  
	 result = result +' <div class="clear"></div>';
	 result = result +'</ul>';
	 result = result +'</div>';
	 result = result +'</div>';
	 
	 
	$("#mailboxLabel").html('Settings');
	$("#mailBoxList").empty(); 
	$("#mailDetails").empty(); 
	$("#mailBoxList").append(result);
	
	
}
</script>

<script>
function deleteReadMail(uuid,nextMailUUID,type){
	var sourceleadUrl = '${sourceleadUrl}';
	
	 $("#mailContentLoading").attr('style','position:absolute; width:97%; height:99%; background-color:rgba(255,255,255,0.8); top:-1px; left:14px; z-index:100;background-image:url("/resources/images/linear-loader.gif"); background-position:center; background-repeat:no-repeat; background-size:100px;');
	 
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/deleteReadMail?${_csrf.parameterName}=${_csrf.token}&UUID="+uuid+"&type="+type,
		success : function(data) {
			getMails(type);
		},
	});   
}
</script>
<script>
function activeEmail(id){
	$("#activeClass"+id).removeClass("Active");
	$("#activeClass"+id).addClass("Active");
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/emailAccountRead?${_csrf.parameterName}=${_csrf.token}&id="+id,
		success : function(data) {
			if(data != null && data != 'undefined' && data != "" && data != '[]'){
			 var result = "";
			 for(var i=0; i<data.length; i++){
				 var report = data[i];
				 result = result + "<div>";
				 result = result + "<ul class='nav nav-pills nav-stacked  inBox-list'>";
				 result = result + "<li onClick='readFolder(\""+report+"\","+id+");'>"+report+"</li>";
				 result = result + "</ul>";
				 result = result + "</div>";
			 }
			 $("#loading").removeAttr("style"); 
			 $("#dynamicEmailFolder").empty();
			 $("#dynamicEmailFolder").append(result);
			}else{
				location.replace(sourceleadUrl+"/mailBox/mails");
				alert("Configured credentials were wrong,Please configure with right credentials")
			}
		},
	}); 
}
</script>
<script type="text/javascript">
function readFolder(folderName,id){
	$('#mailboxLabel').html(folderName);
	$("#loading").attr('style','position:absolute; width:100%; height:100%; background-color:rgba(255,255,255,0.8); top:0px; left:70px; z-index:100;background-image:url("/resources/images/loading.gif"); background-position:center; background-repeat:no-repeat; background-size:50px;');
	
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/emailFolderRead?${_csrf.parameterName}=${_csrf.token}&folderName="+folderName+"&id="+id,
		success : function(data) {
			
					var result = '';
		  			result = result +'<table id="emailMailDetailsPagination" class="table table-hover table-striped">';
		    		result = result +'<thead>';
		    		result = result +'<tr>';
		    		result = result +'<th></th>';
		    		result = result +'<th></th>';
		    		result = result +'<th></th>';
		    		result = result +'</tr>';
		    		result = result +'</thead>';
		    		result = result +'<tbody>';
		    		for(var i=0; i<data.length; i++){
						 var opt = data[i];
						 result = result +' <tr id="checkbox'+opt.messageId+'">';
						 
			       			result = result +'<td style="width : 20px;">';
			       			result = result +'<div class="mrleft8">';
			       			result = result +'<input id="check" value=" " class="checkbox-custom"  name="mailIds[]" type="checkbox">';
			       			result = result +'<label for="check" class="checkbox-custom-label"></label>';
			       			result = result +'</div>';
			       			result = result +'</td>';
			       			
			       			result = result +'<td class="mailbox-subject" onclick="getEmailBody('+opt.messageId+')" data-id="'+i+'">'+opt.subject+'</td>';
			       			
			       			result = result +'<td class="mailbox-date" data-id="'+i+'">'+opt.sentDate+'</td>';
			       			
			       			result = result +'</tr>';
			       			
			       			var body ="";
		       				
			       			body+="<div class='mailbox-read-info' id='mailBody"+opt.messageId+"'>";
			       			body+="<h4 class='clearfix' style='border-bottom:1px solid #ddd; padding-bottom:10px;'>"+opt.subject+"</h4>";
			       			
			       			body+="<div class='row'>";
			       			body+="<p class='clearfix mb10 col-md-8'><i class='text-muted fa fa-user paddingRight'></i>"+opt.from+"</p>";
			       			body+="<p class='clearfix mb10 col-md-4 text-right'>"+opt.sentDate+"</p>";
			       			body+="</div>";
			       			
			   				body+="<p class='col-md-12 clearfix'>"+opt.body+"</p>";
			   				body+="<p class='col-md-10 '>"+opt.filesList+"</p>";
			   				body+="<button type='button' class='btn btn-sm btn-default pull-right' onClick='backFun();'>Back</button>";
			       			body+="</div>";
			             $("#body-details").append(body);
			             
		    		}
		    		
		    		result = result +'</tbody>';
		    		result = result +"</table>";
		    		$("#mailBoxList").empty();
		    		$("#mailDetails").empty(); 
				    $("#mailBoxList").append(result); 
				    emailMailDetailsPagination(); 
				    $("#loading").removeAttr("style"); 
    		
    		
		},
	}); 
	
}

</script>
<<script type="text/javascript">
function deleteConMail1(id){
	alert("hello")
	$.ajax({
		type : "POST",
		url : sourceleadUrl+"/deleteEmailAccount?${_csrf.parameterName}=${_csrf.token}&id="+id,
		success : function(data) {
				location.replace(sourceleadUrl+"/mailBox/mails");
		},
	}); 
}
</script>



<!-- <script src="resources/js/jquery.dataTables.min.js" type="text/javascript" charset="utf-8"></script> -->

<script>
function emailMailDetailsPagination(){ 
    $('#emailMailDetailsPagination').DataTable( { 
    	"scrollCollapse": false,
    	"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "All"]],
    	"bLengthChange": true,
    	"bDestroy": true,
    	"ordering": true,
    	"oLanguage": {
     		"sSearch": "",
     		 "sZeroRecords": "", "sEmptyTable": ""
     	}
    } );
}
</script>
<script>
function backFun(){
	$("#inbox").show();
    $("#details").hide();
}

</script>

<script type="text/javascript">

function getEmailBody(id,filesList){
	
	    $("#mailDetails").html($("#mailBody"+id).html());
	    $("#inbox").hide();
	    $("#details").show();
	     
}

</script>

<script>
$(document).ready(function(){
    $("#one").click(function(){
        $(".inboxShow").hide();
        $(".newmailShow").show();
    });
    $(".aa").click(function(){
        $(".inboxShow").show();
        $(".newmailShow").hide();
    });    
});        
    
    </script>

<script>
function replayToMail(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/composeMail?UUID="+uuid);  
}
</script>

<script>
function forwardEmail(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/forwardEmail?UUID="+uuid+"&mailType=composeMail");  
}
</script>

<script>
function forwardEmailToHotMail(uuid){
	var sourceleadUrl = '${sourceleadUrl}';
	location.replace(sourceleadUrl+"/forwardEmail?UUID="+uuid+"&mailType=composeHotMail");  
}
</script>

<jsp:include page="masterFooter.jsp" />